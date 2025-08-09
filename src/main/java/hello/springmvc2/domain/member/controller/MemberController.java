package hello.springmvc2.domain.member.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.springmvc2.domain.member.controller.form.MemberSaveForm;
import hello.springmvc2.domain.member.controller.form.MemberUpdateForm;
import hello.springmvc2.domain.member.controller.mapper.MemberMapper;
import hello.springmvc2.domain.member.dto.MemberDto;
import hello.springmvc2.domain.member.entry.Member;
import hello.springmvc2.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;
	
	@GetMapping
	public String list(Model model) {
		List<MemberDto> members = memberService.findAllMembers();
		model.addAttribute("members", members);
		return "members/list";
	}
	
	@GetMapping("/{memberId}")
	public String detail(@PathVariable("memberId") Long memberId, Model model) {
		MemberDto member = findMemberById(memberId);
		model.addAttribute("member", member);
		return "members/detail";
	}
	
	@GetMapping("/register")
	public String registerForm(@ModelAttribute("form") MemberSaveForm form) {
		return "members/registerForm";
	}
	
	@PostMapping("/register")
	public String registerForm(@ModelAttribute("form") MemberSaveForm form, 
							   BindingResult bindingResult,
							   RedirectAttributes redirectAttributes) {
		
		MemberDto savedMember = memberService.registerMember(form, bindingResult);
		if (bindingResult.hasErrors()) {
			log.warn("회원가입 유효성 실패: {}", bindingResult);
			return "members/registerForm";
		}
		
		redirectAttributes.addAttribute("memberId", savedMember.getId());
		redirectAttributes.addAttribute("status", true);
		return "redirect:/members/{memberId}";
	}
	
	@GetMapping("/{memberId}/edit")
	public String editForm(@PathVariable("memberId") Long memberId, Model model) {
		Member member = memberService.findMemberById(memberId);
		model.addAttribute("form", MemberMapper.toForm(member));
		return "members/editForm";
	}
	
	 @PostMapping("/{memberId}/edit")
	    public String update(@PathVariable("memberId") Long memberId,
	                         @Validated @ModelAttribute("form") MemberUpdateForm form,
	                         BindingResult bindingResult) {
	        if (bindingResult.hasErrors()) {
	            log.warn("회원 수정 유효성 실패: {}", bindingResult);
	            return "members/editForm";
	        }

	        memberService.updateMember(memberId, form, bindingResult);
	        return "redirect:/members/{memberId}";
	    }
	

	 @PostMapping("/{memberId}/delete")
	public String delete(@PathVariable Long memberId) {
		memberService.deleteMember(memberId);
		return "redirect:/members";
	}
	 
	private MemberDto findMemberById(Long memberId) {
		Member member = this.memberService.findMemberById(memberId);
		return MemberMapper.toViewDto(member);
	}
	
}
