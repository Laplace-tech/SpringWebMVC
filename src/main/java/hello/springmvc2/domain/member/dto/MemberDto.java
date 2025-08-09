package hello.springmvc2.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberDto {

	private final Long id;
	private final String username;
	private final String name;
	
}
