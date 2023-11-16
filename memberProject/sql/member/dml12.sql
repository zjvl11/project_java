-- 회원 정보 삭제

-- 개인 회원 롤들(1인 다수롤(보통 2개) 가능) 삭제 : 선행(先行)  처리
-- 참조하는 테이블 레코드 먼저 삭제
DELETE FROM user_roles WHERE username='ezen1092';

-- 개인 회원 정보 삭제 : 후(後) 처리
-- 참고 당하는 테이블 레코드 나중 삭제
DELETE member_tbl WHERE id='ezen1092';

-- 삭제 후 레코드 확인
SELECT count(*) FROM member_tbl
WHERE id = 'ezen1092'; 

SELECT count(*) FROM user_roles
WHERE username = 'ezen1092'; 