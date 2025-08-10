package hello.springmvc2.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hello.springmvc2.domain.member.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	
	@GetMapping("/login")
	public String loginForm(LoginForm form) {
		return null; 
	}
	
//	@PostMapping("/login")
//	public String login(@Validated LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
//		if (bindingResult.hasErrors()) {
//			return null;
//		}
//		
//		LoginResult loginResult = loginService.loginCheck(form.getUsername(), form.getPassword());
//		
//		if(loginResult == LoginResult.USERNAME_NOT_FOUND) {
//			bindingResult.rejectValue("username", "login.usernameNotFound");
//			return "";
//		} else if (loginResult == LoginResult.PASSWORD_MISMATCH) {
//	        bindingResult.rejectValue("password", "login.passwordMismatch");
//	        return "login/loginForm";
//	    }
//		
//		
//	}
}
