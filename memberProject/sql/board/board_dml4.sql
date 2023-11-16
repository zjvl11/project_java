-- 게시글 수정
UPDATE board_tbl SET 
board_subject = '갱신한 글제목',
board_content = '갱신한 글내용',
board_original_file = '',
board_file = '',
board_date = sysdate
WHERE board_num = 15;


UPDATE board_tbl SET 
board_subject = '갱신한 글제목2',
board_content = '갱신한 글내용2',
board_original_file = '',
board_file = '',
board_date = sysdate
WHERE board_num = 234;

-- 댓글 수정
UPDATE board_tbl SET 
board_content = '갱신한 댓글내용',
board_date = sysdate
WHERE board_num = 141 
and board_writer = 'proiject11';

-- 댓글 삭제
DELETE board_tbl WHERE board_num=375;

-- 원글/댓글 동시 삭제
DELETE board_tbl WHERE board_num=126 OR board_re_ref=126;