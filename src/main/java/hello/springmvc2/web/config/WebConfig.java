package hello.springmvc2.web.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hello.springmvc2.web.argumentresolver.LoginMemberArgumentResolver;
import hello.springmvc2.web.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final LoginMemberArgumentResolver loginMemberArgumentResolver;
	private final LoginCheckInterceptor loginCheckInterceptor;
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }
    
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//    	registry.addInterceptor(loginCheckInterceptor)
//    			.order(1)
//    			.addPathPatterns("/**")
//    			.excludePathPatterns(
//                        "/", "/members/new", "/login", "/logout", "/members/add",
//                        "/css/**", "/*.ico", "/error"
//                );
//    }
    
}
