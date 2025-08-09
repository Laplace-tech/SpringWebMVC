package hello.springmvc2.domain.member.service;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import hello.springmvc2.domain.member.controller.form.MemberSaveForm;
import hello.springmvc2.domain.member.controller.form.MemberUpdateForm;
import hello.springmvc2.domain.member.controller.mapper.MemberMapper;
import hello.springmvc2.domain.member.dto.MemberDto;
import hello.springmvc2.domain.member.entry.Member;
import hello.springmvc2.domain.member.exception.MemberNotFoundException;
import hello.springmvc2.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public Member findMemberById(Long id) {
		return memberRepository.findById(id)
				.orElseThrow(() -> new MemberNotFoundException("회원이 존재하지 않습니다. id=" + id));
	}
	
	public List<MemberDto> findAllMembers() {
		return memberRepository.findAll().stream()
				.map(member -> MemberMapper.toViewDto(member))
				.toList();
	}

	public MemberDto registerMember(MemberSaveForm form, BindingResult bindingResult) {
		validateDuplication(form, bindingResult);
		if (bindingResult.hasErrors()) {
			return null;
		}

		String encryptedPassword = passwordEncoder.encode(form.getPassword());
		Member member = MemberMapper.toEntity(encryptedPassword, form);
		member = memberRepository.save(member);
		return MemberMapper.toViewDto(member);
	}

	public MemberDto updateMember(Long id, MemberUpdateForm form, BindingResult bindingResult) {
		validateIdMatch(id, form::getId);
		
		Member existingMember = findMemberById(id);
		
		String encryptedPassword = passwordEncoder.encode(form.getPassword());
		Member updatedMember = MemberMapper.toEntity(encryptedPassword, 
													 existingMember, 
													 form);

		boolean updated = memberRepository.update(updatedMember);
		if(!updated) {
			throw new MemberNotFoundException("수정할 회원이 존재하지 않습니다.");
		}
		return MemberMapper.toViewDto(updatedMember);
	}
	
    public void deleteMember(Long id) {
        boolean deleted = memberRepository.delete(id);
        if (!deleted) {
            throw new MemberNotFoundException("삭제할 회원이 존재하지 않습니다. id=" + id);
        }
    }
	
    private void validateDuplication(MemberSaveForm form, BindingResult bindingResult) {
		memberRepository.findByUsername(form.getUsername()).ifPresent(m -> {
			bindingResult.rejectValue("username", "member.duplicatedUsername");
		});
    }
    
	private void validateIdMatch(Long pathId, Supplier<Long> formIdSupplier) {
		if(!pathId.equals(formIdSupplier.get())) {
            throw new IllegalArgumentException("Path variable id와 form의 id가 일치하지 않습니다. pathId: " + pathId + ", formId: " + formIdSupplier.get());
		}
	}
}
