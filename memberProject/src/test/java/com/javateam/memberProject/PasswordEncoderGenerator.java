package com.javateam.memberProject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class PasswordEncoderGenerator {
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Test
	public void test() {
		
		log.info("test");
		int i = 0;
		
		while (i < 10) {
			String password = "#Abcd1234";
			
			String hashedPassword = passwordEncoder.encode(password);
	
			log.info("hashedpassword : "+hashedPassword);
			i++;
		}
		
	} //	

}
