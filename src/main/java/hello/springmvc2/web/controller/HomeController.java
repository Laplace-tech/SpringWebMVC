package hello.springmvc2.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hello.springmvc2.domain.member.dto.MemberDto;
import hello.springmvc2.web.argumentresolver.Login;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(@Login MemberDto loginMember, Model model) {
		
		if(loginMember == null) {
			return "home";
		}
		
		model.addAttribute("member", loginMember);
		return "loginHome";
		
	}
	
}
