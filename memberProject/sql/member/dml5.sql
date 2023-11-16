-- 전체 회원 조회 : 페이징
-- 페이지 당 10명 씩 조회
SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 10 + 1) page  
      FROM (
             SELECT *
			 FROM member_tbl
             ORDER BY id DESC
           ) m  
      )  
WHERE page = 1;