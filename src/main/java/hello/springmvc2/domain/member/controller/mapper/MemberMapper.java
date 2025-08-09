package hello.springmvc2.domain.member.controller.mapper;

import java.time.LocalDateTime;

import hello.springmvc2.domain.member.controller.form.MemberSaveForm;
import hello.springmvc2.domain.member.controller.form.MemberUpdateForm;
import hello.springmvc2.domain.member.dto.MemberDto;
import hello.springmvc2.domain.member.entry.Member;

public class MemberMapper {

	
	public static Member toEntity(String encrypedPassword, MemberSaveForm form) {
		return Member.builder()
				.username(form.getUsername())
				.password(encrypedPassword)
				.name(form.getName())
				.registeredAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();
	}
	
	public static Member toEntity(String encryptedPassword, Member existingMember, MemberUpdateForm form) {
		return Member.builder()
				.id(existingMember.getId())
				.username(existingMember.getUsername())
				.registeredAt(existingMember.getRegisteredAt())
				.password(encryptedPassword)
				.name(form.getName())
				.updatedAt(LocalDateTime.now())
				.build();
	}
	
	public static MemberUpdateForm toForm(Member member) {
		return MemberUpdateForm.builder()
				.id(member.getId())
				.name(member.getName())
				.password("")
				.build();
	}
	
	public static MemberDto toViewDto(Member member) {
		return MemberDto.builder()
				.id(member.getId())
				.name(member.getName())
				.username(member.getUsername())
				.build();
	}
	
}
