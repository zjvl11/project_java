package com.javateam.memberProject.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import com.javateam.memberProject.domain.BoardDTO;
import com.javateam.memberProject.domain.BoardVO;
import com.javateam.memberProject.service.BoardService;
import com.javateam.memberProject.service.FileUploadService;
import com.javateam.memberProject.service.ImageService;
import com.javateam.memberProject.util.FileUploadUtil;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardController { 
	
	// 10.20
	@Autowired
	BoardService boardService;
	
	// 10.20
	@Autowired
	FileUploadService fileUPloadService;
	
	// 10.20
	@Value("${spring.servlet.multipart.max-file-size}") 
	String uploadFileMaxSize;
		
	@GetMapping("/write.do")
	public String write(Model model) {
		
		model.addAttribute("boardDTO", new BoardDTO());
		return "/board/write";
	} //
	
	@PostMapping("/writeProc.do")
	public String writeProc(@ModelAttribute("boardDTO") BoardDTO boardDTO, Model model) {
		
		log.info("BoardDTO : {}", boardDTO);
		
		// 10.20
		// case-2 : VO 차원에서 암호화 파일 처리 
		// 첨부 파일 유무 : 없으면 => "", 있으면 => 암호화 
        // this.boardFile = board.getBoardFile().getOriginalFilename().trim().equals("") ?
        //		"" : FileUploadUtil.encodeFilename(board.getBoardFile().getOriginalFilename());
		BoardVO boardVO = new BoardVO(boardDTO);
		
		// case-1
		// 첨부 파일이 있다면...
		if (boardDTO.getBoardFile().getOriginalFilename().trim().equals("") == false) {
			
			log.info("게시글 작성 처리(첨부 파일) : {}", boardDTO.getBoardFile().getOriginalFilename());
			
			String actualUploadFilename = FileUploadUtil.encodeFilename(boardVO.getBoardFile());
			boardVO.setBoardFile(actualUploadFilename);
		
		} 
		
		log.info("BoardVO : {}", boardVO);
		
		boardVO = boardService.insertBoard(boardVO);
		
		log.info("----- 게시글 저장 BoardVO : {}", boardVO);
		
		String msg = "";
		
		// 10.23 : 첨부 파일이 있을 때만 저장
		if (boardDTO.getBoardFile().getOriginalFilename().trim().equals("") == false) {
		
			msg = fileUPloadService.storeUploadFile(boardVO.getBoardNum(), boardDTO.getBoardFile(), boardVO.getBoardFile());
			log.info("msg : {}", msg);
		}
		
		// 10.23
		if (boardVO != null) {
			msg = "게시글 저장에 성공하였습니다.";
		}
			
		// 10.20
		// TODO
		// /error/error
		// errMsg, movePage = write.do
		// 정상 : 파일이 업로드 되었습니다.
		
		model.addAttribute("errMsg", msg);
		// model.addAttribute("movePage", "/board/write.do");
		model.addAttribute("movePage", "/board/list.do"); // 10.23
		
		return "/error/error"; 
		
		// return "/board/write";		
	} //
	
	@GetMapping("/view.do/{boardNum}")
	public String view(@PathVariable("boardNum") int boardNum, Model model) {
		
		BoardVO boardVO =boardService.selectBoard(boardNum);
		log.info("BoardVO : {}", boardVO);
		
		model.addAttribute("board", boardVO);
		
		// 10.30
		// 조회할 때마다 조회수 갱신(+)
		boardService.updateBoardReadcountByBoardNum(boardNum);
		
		return "/board/view";
	}
	
}
