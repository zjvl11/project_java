package com.javateam.memberProject.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class SelectMembersWithRolesByPagingTest {
	
	@Autowired
	MemberDAO memberDAO;
	
	int page;
	int limit;

	@BeforeEach
	void setUp() throws Exception {
		page = 1;
		limit = 10;
	}
	
	@Test
	void testSelectMembersWithRolesByPaging() {
	
		List<Map<String, Object>> members = memberDAO.selectMembersWithRolesByPaging(page, limit);
		
		for (Map<String, Object> map : members) {
			
			log.info("-- : {}, {}, {}", map.get("ID"), map.get("ROLE"), map.get("NAME"));
			// map.entrySet().forEach(x->{ log.info("-- {}", x);});
		}
		
	} //

}
