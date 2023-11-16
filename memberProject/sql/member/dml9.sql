-- 검색 대상
-- : 아이디,이름, 이메일, 휴대폰, 집전화, 기본주소/상세주소, 생일, 가입일, 등급
SELECT COUNT(*) FROM member_tbl
WHERE id = 'abcd1234';

SELECT COUNT(*) FROM member_tbl
WHERE name like '류 이순';

SELECT COUNT(*) FROM member_tbl
WHERE name like '%이순%';

SELECT COUNT(*) FROM member_tbl
WHERE email = 'abcd@abcd.com';

SELECT COUNT(*) FROM member_tbl
WHERE email like '%abcd@abcd.com%';

SELECT COUNT(*) FROM member_tbl
WHERE mobile like '%010-1234-5678%';

SELECT COUNT(*) FROM member_tbl
WHERE phone like '%02-1111-2222%';

SELECT COUNT(*) FROM member_tbl
WHERE address1 like '%구로%'
OR address2 like '%구로%';

SELECT COUNT(*) FROM member_tbl
WHERE TO_CHAR(birthday, 'YYYY-MM-DD') like '%2000%';

SELECT COUNT(*) FROM member_tbl
WHERE TO_CHAR(joindate, 'YYYY-MM-DD HH24:MI:SS') like '%2010%';


