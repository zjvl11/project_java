package com.javateam.memberProject.controller;

import java.util.ArrayList;
import java.sql.Date; // 10.26
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.javateam.memberProject.domain.BoardDTO;
import com.javateam.memberProject.domain.BoardVO;
import com.javateam.memberProject.domain.UploadFile;
import com.javateam.memberProject.service.BoardService;
import com.javateam.memberProject.service.FileUploadService;
import com.javateam.memberProject.service.ImageService;
import com.javateam.memberProject.service.ImageStoreService;
import com.javateam.memberProject.util.FileUploadUtil;

import lombok.extern.slf4j.Slf4j;

// 10.25
@SessionAttributes("boardUpdateDTO")
@Controller
@RequestMapping("board")
@Slf4j
public class BoardUpdateController {

	@Autowired
	BoardService boardService;

	@Autowired
	FileUploadService fileUploadService;

	@Autowired
	ImageService imageService;

	@Autowired
	ImageStoreService imageStoreService;

	@GetMapping("/update.do")
	public String update(@RequestParam("boardNum") int boardNum, Model model, HttpSession session) {

		BoardVO boardVO = boardService.selectBoard(boardNum);
		// BoardDTO boardDTO = new BoardDTO(boardVO);
		// model.addAttribute("boardDTO", boardDTO);

		log.info("boardVO(update) : {}", boardVO);

		// 기존 정보 세션 생성
		// if (session.getAttribute("boardUpdateSess") == null) {
		session.setAttribute("boardUpdateSess", boardVO);
		// }

		log.info("BoardVO : {}", boardVO);
		model.addAttribute("board", boardVO);
		// model.addAttribute("boardUpdateDTO", new BoardDTO());

		return "/board/update";
	} //

	@PostMapping("/updateProc.do")
	public String updateProc(// @ModelAttribute("boardUpdateDTO") BoardDTO boardUpdateDTO,
			@RequestParam Map<String, Object> map, @RequestParam("boardFile") MultipartFile boardFile, Model model,
			HttpSession session) {

		// log.info("------ updateProc.do : {}", boardUpdateDTO);
		log.info("------ updateProc.do");

		log.info("첨부 파일 : {}", boardFile.isEmpty());
		log.info("첨부 파일명 : {}", boardFile != null ? boardFile.getOriginalFilename() : "");

		// 10.26
		// 수정에 실패했을 때는  글수정 화면으로 이동하고, 성공하였을 때는 개별 게시글 보기로 이동하도록 변경 
		// 성공/실패에 따라 선택적으로 화면 이동하기 위해 변수 활용(movePage)
		// 초기 기본값 변경
		// String returnPath = "redirect:/board/view.do/" + map.get("boardNum").toString(); // 리턴(이동) 페이지
		
		String returnPath; // 글수정 "성공/실패" 모두 "/error/error"로 가도록 재설정
		String movePage = "/board/update.do?boardNum=" + map.get("boardNum").toString(); // 리턴(이동) 페이지
		
		String msg = ""; // 메시지

		map.entrySet().forEach(x -> {
			log.info(x + "");
		});

		BoardVO defaultBoardVO = (BoardVO) session.getAttribute("boardUpdateSess");
		BoardVO updateBoardVO = new BoardVO(map);

		log.info("boardUpdateSession(기존 정보) : {}", defaultBoardVO);
		log.info("수정 정보 : {}", updateBoardVO);

		// 게시글 패쓰워드 검증
		if (defaultBoardVO.getBoardPass().equals(updateBoardVO.getBoardPass().trim())) {

			// 첨부 파일 처리
			if (boardFile.isEmpty() == false) { // 첨부 파일이 있다면

				// 저장용 파일명 암호화
				String actualUploadFilename = FileUploadUtil.encodeFilename(boardFile.getOriginalFilename());
				updateBoardVO.setBoardOriginalFile(boardFile.getOriginalFilename());
				updateBoardVO.setBoardFile(actualUploadFilename);

				// 신규 업로드 파일 저장(업로드)
				msg = fileUploadService.storeUploadFile(updateBoardVO.getBoardNum(), boardFile,
						updateBoardVO.getBoardFile());
				log.info("msg : {}", msg);

				// 기존 첨부 파일 삭제
				msg += fileUploadService.deleteUploadFile(defaultBoardVO.getBoardFile());

				log.info("msg : {}", msg);

			} else { // 첨부 파일이 없다면...

				log.info("첨부 파일이 없다면...");

				// 기존 파일을 입력
				String originalFilename = defaultBoardVO.getBoardOriginalFile() != null
						? defaultBoardVO.getBoardOriginalFile()
						: null;
				updateBoardVO.setBoardOriginalFile(originalFilename);

				String encBoardFilename = defaultBoardVO.getBoardFile() != null ? defaultBoardVO.getBoardFile() : null;
				updateBoardVO.setBoardFile(encBoardFilename);

			} //

			// 글내용(boardContent) 비교 : 변경시에는 기존 삽입 이미지 삭제 등 처리
			log.info("기존 글내용 : {}", defaultBoardVO.getBoardContent());
			log.info("수정 글내용 : {}", updateBoardVO.getBoardContent());

			/*
			 * *****************************************************************************
			 * *******************
			 */

			// 글내용이 실제로 변경되었다면... (서로 내용이 다른 경우)
			if (defaultBoardVO.getBoardContent().trim().equals(updateBoardVO.getBoardContent().trim()) == false) {

				// 기존 데이터의 삽입 이미지 목록(삽입 이미지 테이블(upload_file_tbl)의 PK(기본키)) 확보
				//
				// ex) 글내용중 이미지가 들어간 내용 ex) <img src="/memberProject/board/image/18" .....

				List<Integer> defaultImgList = boardService.getImageList(defaultBoardVO.getBoardContent().trim(),
						"/board/image/");
				List<Integer> updateImgList = boardService.getImageList(updateBoardVO.getBoardContent().trim(),
						"/board/image/");

				log.info("----------------------------------");

				for (int s : defaultImgList) {
					log.info("--- 기존 업로드 이미지 : " + s);
				} //

				for (int s : updateImgList) {
					log.info("--- 신규 업로드 이미지 : " + s);
				} //

				log.info("----------------------------------");

				// 삭제할 글내용에 삽입 이미지 목록
				List<Integer> deleteExpectedImgList = new ArrayList<>();

				/* ----------------------------------------------------------------------- */
				/* ----------------------------------------------------------------------- */

				// 기존에 이미지가 되어 있지만
				// 신규에는 이미지가 없을 때는 기존 이미지 모두 삭제
				// TODO
				
				// 10.26
				if (updateImgList.size() == 0) {
					
					log.info("기존 글내용의 모든 이미지 삭제");
					deleteExpectedImgList.addAll(defaultImgList);
					
				} else { // 신규에 이미지 포함시 선택 삭제
					
					log.info("기존글의 이미지들의 선별적 삭제");
					
					if  (defaultImgList.size() > 0) {
					
						for (int s : defaultImgList) {
							
							if (updateImgList.contains(s) == false) { //
								
								log.info("실제 삭제할 기존 이미지 기본키(PK) : " + s);
								deleteExpectedImgList.add(s);
							} //
							
						} // for
					
					} // if 
					
					// 삭제할 이미지들 출력
					deleteExpectedImgList.forEach(x -> {
						log.info("삭제할 이미지 아이디 : {}", x);
					});
					
					log.info("--------------- 삭제할 이미지들 실제 삭제 -------------------");

					// 대상 삽입 이미지 파일 삭제 : 삭제할 이미지 있으면 삭제
				
					if (deleteExpectedImgList.size() > 0) {
					
						for (int imageId : deleteExpectedImgList) {
							
							// 삽입 이미지 테이블(upload_file_tbl)에서 저장경로/파일명 가져옴(file_path 필드)
							// C:/lsh/works/project/upload/image/2023/10/20/a92e1a28f7e746b39afe7e83eb97a5d2.jpg
							UploadFile uploadFile = imageService.load(imageId); // 삭제할 이미지 파일 경로 확보 :
																				// uploadFile.getFilePath()
							// 삽입 이미지 삭제 삭제
							log.info("삭제 메시지 : {}", fileUploadService.deleteImageFile(uploadFile.getFilePath()));
							// 삽입 이미지 테이블(upload_file_tbl)에서도 해당 이미지 수록 내용 삭제
							imageStoreService.deleteById(imageId);
							
							log.info("이미지를 삭제하였습니다.");
						} // for
						
					} // if (deleteExpectedImgList.size() > 0) {
					

				} // if (updateImgList.size() == 0) {

				/* ----------------------------------------------------------------------- */
				/* ----------------------------------------------------------------------- */

			} // 글내용이 실제로 변경되었다면... (서로 내용이 다른 경우)

			else { // 변경 내용이 없다면...

				msg = "게시글 수정(변경) 내용이 없습니다.";

			} // if
				// (defaultBoardVO.getBoardContent().trim().equals(updateBoardVO.getBoardContent())
				// == false) {

			/*
			 * *****************************************************************************
			 * *******************
			 */
			
			// 10.26
			// 등록일 => 최근 수정일로 변경
			updateBoardVO.setBoardDate(new Date(System.currentTimeMillis()));

			log.info("최종 게시글 수정 내용 : {}", updateBoardVO);

			// 게시글 수정			
			BoardVO resultVO = boardService.updateBoard(updateBoardVO);

			if (resultVO == null) {

				msg = "게시글 수정에 실패하였습니다.";

			} else {

				log.info("최종 저장 결과 : " + resultVO);
				msg = "게시글 수정에 성공하였습니다.";
				
				// 10.26
				// 수정에 실패했을 때는  글수정 화면으로 이동하고, 성공하였을 때는 개별 게시글 보기로 이동하도록 변경 
				// 게시글 수정 성공후 개별 게시글 보기로 이동
				movePage = "/board/view.do/" + map.get("boardNum").toString();
			} //

		} else { // 패쓰워드 틀렸을 때

			msg = "게시글 패쓰워드가 틀렸습니다. 다시 입력하십시오.";

		} //

		model.addAttribute("errMsg", msg);
		// 10.26
		// 수정에 실패했을 때는  글수정 화면으로 이동하고, 성공하였을 때는 개별 게시글 보기로 이동하도록 변경 
		// 초기값은 메서드 초기에 언급된 지역 변수에서 변경(movePage)
		// model.addAttribute("movePage", "/board/update.do?boardNum=" + map.get("boardNum").toString());
		model.addAttribute("movePage", movePage);
		returnPath = "/error/error"; // 에러 페이지로 이동

		return returnPath;
	}

} //