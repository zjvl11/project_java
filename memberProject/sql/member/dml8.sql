-- 회원 롤 점검
SELECT role FROM user_roles WHERE username = 'abcd1234';

-- 회원 롤 (관리자 권한) 삭제
DELETE FROM user_roles WHERE username = 'abcd1234' AND role = 'ROLE_ADMIN';

