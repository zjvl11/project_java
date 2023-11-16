-- 게시글의 댓글이 존재하는지 여부 검색
-- ex) 게시글 삭제시 댓글이 있으면 삭제하지 못하도록 조치
-- ex) 게시글 원글 아이디 : 126
SELECT count(*) FROM board_tbl 
WHERE board_re_ref = 126;

-- 조회수 갱신
UPDATE board_tbl SET
board_readcount = board_readcount + 1
WHERE board_num = 107;