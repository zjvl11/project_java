package com.javateam.memberProject.service;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;

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
class MemberServiceInsertMemberTest {
	
	@Autowired
	MemberService memberService;
	
	MemberDTO memberDTO;

	@BeforeEach
	void setUp() throws Exception {
		
		memberDTO = MemberDTO.builder()
	  			 .id("jsp123456")
	  			 .password("$2a$10$1t3vaIa5jtsMp2RY9y7xhuJz0xDRNEl0csvPYvgCbyKuKeyOVucES")
	  			 .name("황 선우")
	  			 .gender("m")
	  			 .email("swim100m@abcd.com")
	  			 .mobile("010-5777-7979")
	  			 .phone("02-777-8888")
	  			 .zip("08290")
	  			 .address1("서울 구로구 공원로 83 (구로동)")
	  			 .address2("이젠아카데미 신도림점")
	  			 .birthday(new SimpleDateFormat("yyyy-MM-dd").parse("2001-11-30"))
	  			 .build();
	}

	@Transactional
	@Rollback(true)
	@Test
	void testInsertMemberRole() {
		
		log.info("MemberServiceInsertMemberTest");
		assertTrue(memberService.insertMemberRole(memberDTO));
	}

}
