package hello.springmvc2.web.login;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import hello.springmvc2.domain.member.entry.Member;
import hello.springmvc2.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

//	public LoginResult loginCheck(String username, String rawPassword) {
//		Optional<Member> memberOpt = memberRepository.findByUsername(username);
//		if(memberOpt.isEmpty()) {
//			return LoginResult.USERNAME_NOT_FOUND;
//		}
//		
//		Member member = memberOpt.get();
//		if(!passwordEncoder.matches(rawPassword, member.getPassword())) {
//			return LoginResult.PASSWORD_MISMATCH;
//		}
//		
//		return LoginResult.SUCCESS;
//	}

}
