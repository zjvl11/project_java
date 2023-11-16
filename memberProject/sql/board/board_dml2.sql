-- 댓글 작성(삽입)
-- 댓글 레벨은 레벨 1 만 작성하도록 하여 댓글의 댓글이 없도록 조치
-- 패쓰워드는 형식적으로 빈란 입력 : 로그인 인증 사람이 작성하도록 하므로 별로 패쓰워드 불필요.
-- 아이디는 로그인한 사람의 아이디 사용  

-- 댓글 순서(board_re_seq)
-- 최근 해당 댓글의 seq 최고값(max) + 1 을 반영

SELECT MAX(board_re_seq) + 1 
FROM board_tbl
WHERE board_re_ref = 15;


INSERT INTO board_tbl VALUES
(board_seq.NEXTVAL, 'project1', ' ', '댓글 제목', '댓글 내용', NULL, NULL, 15, 1, 1, 0, sysdate);


INSERT INTO board_tbl VALUES
(board_seq.NEXTVAL, 'project21', ' ', '댓글 제목2', '댓글 내용2', NULL, NULL, 15, 1, 2, 0, sysdate);

-- 댓글이 없는 상황 : NULL => 0으로 치환
select NVL((SELECT MAX(board_re_seq) + 1 FROM board_tbl WHERE board_re_ref=234), 0) from dual;

-- 댓글이 없는 상황을 대비 : NULL => 0으로 치환
INSERT INTO board_tbl VALUES (board_seq.NEXTVAL, 'project11', ' ', ' ', '날씨가 청명하다 !', NULL, 
NULL, 234, 1, (select NVL((SELECT MAX(board_re_seq) + 1 FROM board_tbl WHERE board_re_ref=234), 0) from dual) , 
0, sysdate); 


-- 해당 게시글의 전체 댓글 조회
-- 게시글(원글) 번호 : 15
SELECT * FROM board_tbl
WHERE board_re_ref = 15; 