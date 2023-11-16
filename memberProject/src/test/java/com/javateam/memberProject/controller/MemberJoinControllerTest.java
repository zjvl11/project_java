package com.javateam.memberProject.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javateam.memberProject.domain.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
// @AutoConfigureMockMvc
@Slf4j
public class MemberJoinControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
    private FilterChainProxy springSecurityFilterChain;
		
	@Autowired
	ObjectMapper objectMapper; // fasterxml jackson object(객체) 
	
	MemberDTO memberDTO;
	
	@Autowired
	ServletContext application;
	
	@BeforeEach
	public void setUp() throws ParseException {
	
		// mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		mockMvc = MockMvcBuilders.standaloneSetup(MemberController.class)
					.apply(springSecurity(springSecurityFilterChain))
					.build();
		
		memberDTO = MemberDTO.builder()
				  			 .id("mybatis1234")
				  			 .password("$2a$10$1t3vaIa5jtsMp2RY9y7xhuJz0xDRNEl0csvPYvgCbyKuKeyOVucES")
				  			 .name("지 유찬")
				  			 .gender("m")
				  			 .email("swim50m@abcd.com")
				  			 .mobile("010-1000-2023")
				  			 .phone("02-888-9999")
				  			 .zip("08290")
				  			 .address1("서울 구로구 공원로 83 (구로동)")
				  			 .address2("이젠아카데미 신도림점")
				  			 .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-12"))
				  			 .build();
	} //
	
	@Test
	public void test() {
		
		log.info("회원 가입 REST 테스트 : {}", memberDTO);

		String contextPath = application.getContextPath();
		
		try {
			
			mockMvc.perform(post(contextPath + "/member/joinProcRest.do")
					       		.accept(MediaType.APPLICATION_JSON)
					       		.contentType("application/json; charset=UTF-8")
					       		.content(objectMapper.writeValueAsString(memberDTO))) // JSON 형태의 매개변수로 전환
						  .andExpect(status().isOk())						  
						  .andExpect(content().contentType("application/json; charset=UTF-8"))
				          .andExpect(jsonPath("id").exists())
				          .andExpect(jsonPath("$.name").value("지 유찬"))
				          .andDo(print());
						  
			
		} catch (Exception e) {
			log.error("MemberJoinControllerTest 에러 메시지 : " + e);
			e.printStackTrace();
		} //	
		
	}

}
