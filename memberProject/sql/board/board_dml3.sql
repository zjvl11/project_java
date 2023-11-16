-- 게시글 조회 (댓글들 제외)
-- 게시글 목록에서 댓글들 제외하고 원글들만 출력
-- 페이징 : 페이지 단위로 일부분 레코들을 추출
-- 현재 페이지(page) = 1, 5개의 레코드씩 조회
SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT *
			 FROM board_tbl
			 WHERE board_re_ref = 0
             ORDER BY board_num DESC
           ) m  
      )  
WHERE page = 1;

-- 전체 게시글 수 조회 (댓글들 배제한 전체 원글 게시글 수)
SELECT count(*) 
FROM board_tbl
WHERE board_re_ref = 0;


------------------------------------------------------------
------------------------------------------------------------

-- 주의) 댓글 원글 모두 포함할 것인지 여부에 따라 구문 차이 !

------------------------------------------------------------
------------------------------------------------------------

-- 검색 : 게시글 조회
-- 페이징 : 페이지 단위로 일부분 레코들을 추출
-- 현재 페이지(page) = 1, 5개의 레코드씩 조회
-- 글 제목(키워드) 검색
SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT *
			 FROM board_tbl
			 WHERE board_subject LIKE '%날씨%'
             ORDER BY board_num DESC
           ) m  
      )  
WHERE page = 1;


-- 글 내용(키워드) 검색
SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT *
			 FROM board_tbl
			 WHERE board_content LIKE '%과일%'
             ORDER BY board_num DESC
           ) m  
      )  
WHERE page = 1;

-- 작성자(키워드) 검색
SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT *
			 FROM board_tbl
			 WHERE board_writer = 'abcd1234'
             ORDER BY board_num DESC
           ) m  
      )  
WHERE page = 1;

-- 검색 레코드 수
-- ex) 글 내용(키워드) 검색
SELECT count(*)  
FROM board_tbl
WHERE board_content LIKE '%과일%';


-- 검색 예시
-- 검색키 : 내용
-- 검색어 : 과일
-- 정렬 기준 : 조회수
-- 정렬 순서 : 오름차순
SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT *
			 FROM board_tbl
			 WHERE board_content LIKE '%과일%'
             ORDER BY board_readcount ASC
           ) m  
      )  
WHERE page = 3;


-- 검색 예시
-- 검색키 : 제목
-- 검색어 : 운동
-- 정렬 기준 : 작성일
-- 정렬 순서 : 내림차순
SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 5 + 1) page  
      FROM (
             SELECT *
			 FROM board_tbl
			 WHERE board_subject LIKE '%운동%'
             ORDER BY board_date DESC
           ) m  
      )  
WHERE page = 1;
