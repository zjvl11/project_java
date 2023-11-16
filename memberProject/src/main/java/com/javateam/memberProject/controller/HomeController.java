package com.javateam.memberProject.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	@GetMapping("/")
	public String home(Model model) {
		
		log.info("home");
		model.addAttribute("arg", "인자");
		
		// 10.17
		return "home"; 
		// return "redirect:/member/join.do";
	} //
	
	// 10.17
	@GetMapping("/content1")
	public String content1() {
		
		log.info("content1");
		
		return "content1";
	} //
	
	// 10.17
	@GetMapping("/content2")
	public String content2() {
		
		log.info("content2");
		
		return "content2";
	} //
	
	@GetMapping("/login")
	public String login() {
		
		log.info("login");
		return "login";
	} //
	
	@GetMapping("/welcome")
	public String welcome() {
		
		log.info("welcome");
		return "welcome";
	} //
	
	@GetMapping("/loginError")
    public String loginError(Model model, HttpSession session) {
    	
		// Spring CustomProvider 인증(Auth) 에러 메시지 처리
		Object secuSess = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
	
		log.info("#### 인증 오류 메시지-1 : " + secuSess);
		log.info("#### 인증 오류 메시지-2 : " + secuSess.toString());
	
		model.addAttribute("error", "true");
		model.addAttribute("msg", secuSess);
	
		return "login";
	}	
	
	// 10.11 : /403 : 접근 권한 문제시 이동 페이지 : SecurityConfig.java
	/*
	   .and()
           .exceptionHandling().accessDeniedPage("/403");     
	 */
	@GetMapping("/403")
    public String acessDenided(Model model, HttpSession session) {
    	
		model.addAttribute("errMsg", "페이지 접근 권한이 없습니다.");
		model.addAttribute("movePage", "/welcome");
	
		return "/error/error";
	}	

}
