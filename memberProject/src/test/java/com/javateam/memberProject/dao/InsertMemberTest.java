package com.javateam.memberProject.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.memberProject.domain.MemberDTO;
import com.javateam.memberProject.domain.Role;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class InsertMemberTest {
	
	@Autowired
	MemberDAO memberDAO;
	
	MemberDTO memberDTO;
	
	@BeforeEach
	public void setUp() throws ParseException {
		
		memberDTO = MemberDTO.builder()
	  			 .id("jsp12345")
	  			 .password("$2a$10$1t3vaIa5jtsMp2RY9y7xhuJz0xDRNEl0csvPYvgCbyKuKeyOVucES")
	  			 .name("오 상욱")
	  			 .gender("m")
	  			 .email("pen@abcd.com")
	  			 .mobile("010-4343-3232")
	  			 .phone("02-777-8888")
	  			 .zip("08290")
	  			 .address1("서울 구로구 공원로 83 (구로동)")
	  			 .address2("이젠아카데미 신도림점")
	  			 .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-12"))
	  			 .build();
	}
	
//	@Test
//	@Transactional
//	@Rollback(false)
//	public void test() {
//		
//		log.info("memberDTO : {}", memberDTO);
//		boolean result = false;
//		
//		try {
//			memberDAO.insertMember(memberDTO);
//			result = true;
//		} catch (Exception e) {
//			log.error("InsertMemberTest.test : {}", e);
//			e.printStackTrace();
//		}
//		
//		assertTrue(result);
//		
//	} //
	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void test2() {
//		
//		// memberDAO.insertMember(memberDTO);
//		memberDAO.insertRole(new Role(memberDTO.getId(), "ROLE_USER"));
//	} //
	
	@Test
	@Transactional
	@Rollback(true)
	public void test() {
		
		log.info("memberDTO : {}", memberDTO);
		boolean result = false;
		
		try {
			memberDAO.insertMember(memberDTO);
			result = true;
		} catch (Exception e) {
			log.error("InsertMemberTest.insertMember : {}", e);
			e.printStackTrace();
		}
		
		assertTrue(result);
		
		result = false;
		
		try {
			memberDAO.insertRole(new Role(memberDTO.getId(), "ROLE_USER"));
			result = true;
		} catch (Exception e) {
			log.error("InsertMemberTest.insertRole : {}", e);
			e.printStackTrace();
		}
		
		assertTrue(result);
		
	} //

}
