-- 댓글 아닌 순수한 원글만 조회
-- 게시글 조회
-- 페이징 : 페이지 단위로 일부분 레코들을 추출
-- 현재 페이지(page) = 1, 10개의 레코드씩 조회
SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 10 + 1) page  
      FROM (
             SELECT *
			 FROM board_tbl
			 WHERE board_re_ref = 0
             ORDER BY board_num DESC
           ) m  
      )  
WHERE page = 1;

-- 댓글 아닌 순수한 원글만 조회
-- 원글만의 전체 게시글 수 조회
SELECT count(*) FROM board_tbl WHERE board_re_ref = 0;