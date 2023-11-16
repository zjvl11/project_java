package com.javateam.memberProject.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.memberProject.domain.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class UpdateMemberTest {
	
	@Autowired
	MemberDAO memberDAO;
	
	MemberDTO memberDTO;
	
	@BeforeEach
	public void setUp() throws ParseException {
		
		memberDTO = MemberDTO.builder()
	  			 .id("abcd12345")
	  			 .password("$2a$10$lhx31tqVr9IDrG8NMrtdX.Q..cd1CDd4gRHPxaXZo47aJUOIdrZxK")
	  			 .email("springjava@abcd.com")
	  			 .mobile("010-7878-9090")
	  			 .phone("02-1111-3333")
	  			 .zip("08285")
	  			 .address1("서울 구로구 가마산로19길 30 (구로동, 일동행복세상아파트)")
	  			 .address2("101동 1002호")
	  			 .build();
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void test() {
		
		log.info("memberDTO : {}", memberDTO);
		boolean result = false;
		
		try {
			memberDAO.updateMember(memberDTO);
			result = true;
		} catch (Exception e) {
			log.error("UpdateMemberTest.updateMember : {}", e);
			e.printStackTrace();
		}
		
		assertTrue(result);		
	} //

}
