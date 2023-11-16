CREATE SEQUENCE board_seq
START WITH 1
INCREMENT BY 1
MAXVALUE 99999
NOCYCLE;
​
CREATE TABLE board_tbl (
	board_num NUMBER DEFAULT 0,
	board_writer VARCHAR2(30) NOT NULL,
	board_pass VARCHAR2(20) NOT NULL,
	board_subject VARCHAR2(100 char) NOT NULL,
	board_content CLOB NOT NULL,
	board_original_file VARCHAR2(200 char),
	board_file VARCHAR2(200 char),
	board_re_ref NUMBER NOT NULL,
	board_re_lev NUMBER NOT NULL,
	board_re_seq NUMBER NOT NULL,
	board_readcount NUMBER DEFAULT 0,
	board_date DATE,
	PRIMARY KEY(board_num)
);

comment ON COLUMN board_tbl.board_num IS '게시글 번호';
comment ON COLUMN board_tbl.board_writer IS '게시글 작성자';
comment ON COLUMN board_tbl.board_pass IS '게시글 비밀번호';
comment ON COLUMN board_tbl.board_subject IS '게시글 제목';
comment ON COLUMN board_tbl.board_content IS '게시글 내용';
comment ON COLUMN board_tbl.board_original_file IS '게시글 첨부 파일(원본)';
comment ON COLUMN board_tbl.board_file IS '게시글 첨부 파일(암호화)';
comment ON COLUMN board_tbl.board_re_ref IS '게시글 답글의 원 게시글(관련글) 번호';
comment ON COLUMN board_tbl.board_re_lev IS '게시글 답글 레벨';
comment ON COLUMN board_tbl.board_re_seq IS '게시글 답글 순서';
comment ON COLUMN board_tbl.board_readcount IS '게시글 조회수';
comment ON COLUMN board_tbl.board_date IS '게시글 작성일자';
​