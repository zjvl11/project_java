package com.javateam.memberProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.memberProject.domain.BoardVO;
import com.javateam.memberProject.domain.UploadFile;
import com.javateam.memberProject.service.BoardService;
import com.javateam.memberProject.service.FileUploadService;
import com.javateam.memberProject.service.ImageService;
import com.javateam.memberProject.service.ImageStoreService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("board")
@Slf4j
public class BoardDeleteController {

	@Autowired
	BoardService boardService;

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	ImageService imageService;

	@Autowired
	ImageStoreService imageStoreService;

	@GetMapping("/deleteProc.do")
	public String updateProc(@RequestParam("boardNum") int boardNum, 
						     @RequestParam("boardPass") String boardPass,
						     Model model) {

		log.info("------ deleteProc.do : boardNum : {}, boardPass : {}", boardNum, boardPass);

		// 개별 게시글 보기로 이동(movePage)
		// 게시글 삭제 성공시에는 게시글 목록으로 이동(이미 삭제되었으므로 이동할 게시글이 없음)
		// 게시글 삭제 실패시에는 게시글 보기로 이동
		String returnPath; // 글삭제 "성공/실패" 모두 "/error/error"로 가도록 재설정
		String movePage = "/board/view.do/" + boardNum; // 리턴(이동) 페이지
		
		String msg = ""; // 메시지
		
		BoardVO boardVO = boardService.selectBoard(boardNum); // 기존 정보 읽어오기
		
		log.info("기존 정보 : boardVO : {}", boardVO);
		
		// 댓글들 현황 점검
		// 댓글들이 있다면 삭제 못하도록 차단 !
		// 댓글이 없을 경우에 삭제 허용 !
		if (boardService.selectBoardsCountWithReplies(boardNum) > 0) { // 댓글(들)이 있을 경우
			
			msg = "댓글이 있는 원글은 삭제할 수 없습니다.";
			
		} else { // 댓글이 없을 경우 (삭제 가능)
			
			log.info("댓글 없는 원글(삭제 가능한 글)");
			
			log.info("전송 패쓰워드 : {}", boardPass);
			log.info("DB 패쓰워드 : {}", boardVO.getBoardPass());
			
			// 게시글 패쓰워드 검증
			if (boardPass.trim().equals(boardVO.getBoardPass())) {
				
				log.info("패쓰워드 점검 성공");
			
				// 삭제할 삽입 이미지 점검
				List<Integer> deleteImgList = boardService.getImageList(boardVO.getBoardContent().trim(),
						"/board/image/");
	
				for (int s : deleteImgList) {
					log.info("--- 삭제할 업로드  이미지 : " + s);
				} //
				
				// 삽입 이미지들 삭제
				if (deleteImgList.size() > 0) { // 삭제할 이미지들이 있다면...
					
					for (int imageId : deleteImgList) {
						
						UploadFile uploadFile = imageService.load(imageId); // 삭제할 이미지 파일 경로 확보 :
						// 삽입 이미지 삭제 삭제
						log.info("삭제 메시지 : {}", fileUploadService.deleteImageFile(uploadFile.getFilePath()));
						// 삽입 이미지 테이블(upload_file_tbl)에서도 해당 이미지 수록 내용 삭제
						imageStoreService.deleteById(imageId);
						
					} // for
					
				} // if (deleteImgList.size() > 0)
				
				log.info("첨부 파일 삭제 직전 !");
				
				// 첨부 파일 삭제
				if (boardVO.getBoardOriginalFile() != null) { // 첨부 파일이 있다면
					
					log.info("첨부 파일 존재 !");

					// 기존 첨부 파일 삭제
					msg += fileUploadService.deleteUploadFile(boardVO.getBoardFile());
					log.info("파일 삭제 msg : {}", msg);

				} // if 
				
				// 게시글 테이블(board_tbl)에서 게시글 자체를 삭제
				if (boardService.deleteById(boardNum) == true) {
					
					msg = "게시글 삭제에 성공하였습니다.";
					movePage = "/board/list.do"; // 게시글 목록으로 인동
					
				} else {
					
					msg = "게시글 삭제에 실패하였습니다.";
					
				} //

			} else { // 패쓰워드 오류시 
				
				msg = "게시글 패쓰워드가 틀렸습니다. 다시 입력하십시오.";
				
			} // if (boardPass.trim().equals(boardVO.getBoardPass()))	
			
		
		} // (boardService.selectBoardsCountWithReplies(boardNum) > 0) 
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		model.addAttribute("errMsg", msg);

		// 초기값은 메서드 초기에 언급된 지역 변수에서 변경(movePage)
		model.addAttribute("movePage", movePage);
		returnPath = "/error/error"; // 에러 페이지로 이동

		return returnPath;
	} //

} //