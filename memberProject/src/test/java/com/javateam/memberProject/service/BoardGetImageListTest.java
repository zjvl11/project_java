package com.javateam.memberProject.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class BoardGetImageListTest {

	@Autowired
	BoardService boardService;
	
	@Test
	public void test() {
		
		String content = boardService.selectBoard(141).getBoardContent();
		List<Integer> imgList = boardService.getImageList(content, "/board/image/");
		
		for (int img : imgList) {
			log.info("img : {}", img);
		} //
		
	}
}
