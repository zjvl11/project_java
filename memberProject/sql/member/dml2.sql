-- 아이디 존재(중복) 여부
SELECT count(*) FROM member_tbl WHERE id='abcd1234';

-- 이메일 존재(중복) 여부
SELECT count(*) FROM member_tbl WHERE email='ezen8@abcd.com';

-- 연락처(휴대폰) 존재(중복) 여부
SELECT count(*) FROM member_tbl WHERE mobile='010-1234-1036';

-- 회원 정보 조회
SELECT * FROM member_tbl WHERE id='abcd1234';

