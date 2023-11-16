package com.javateam.memberProject.domain;

import java.sql.Date;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BoardDTO {
	
	private int boardNum; // 게시글 번호
	private String boardWriter; // 게시글 작성자
	private String boardPass; // 게시글 비밀번호
	private String boardSubject; // 게시글 제목
	private String boardContent; // 게시글 내용
	private MultipartFile boardFile; // 게시글 첨부 파일
	private int boardReRef; // 게시글 답글의 원 게시글(관련글) 번호
	private int boardReLev; // 게시글 답글 레벨
	private int boardReSeq; // 게시글 답글 순서
	private int boardReadCount; // 게시글 조회수
	private Date boardDate; // 게시글 작성일자
	private String textMulti = "text"; // 텍스트 모드(text:기본값) / 멀티미디어 모드(multi)
	
	// 10.18
	// 업로드 파일(파일명을 확인할 수 있도록 파일명 인쇄) : boardFile.getOriginalFilename()
	@Override
	public String toString() {
		return "BoardDTO [boardNum=" + boardNum + ", boardWriter=" + boardWriter + ", boardPass=" + boardPass
				+ ", boardSubject=" + boardSubject + ", boardContent=" + boardContent + ", boardFile=" + boardFile.getOriginalFilename()
				+ ", boardReRef=" + boardReRef + ", boardReLev=" + boardReLev + ", boardReSeq=" + boardReSeq
				+ ", boardReadCount=" + boardReadCount + ", boardDate=" + boardDate + ", textMulti=" + textMulti + "]";
	}

}