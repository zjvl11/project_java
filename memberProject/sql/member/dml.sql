select id as username, password, enabled from member_tbl; 

-- 회원 정보 생성
INSERT INTO member_tbl VALUES (
'abcd1234',
'$2a$10$1t3vaIa5jtsMp2RY9y7xhuJz0xDRNEl0csvPYvgCbyKuKeyOVucES',
'홍 길동',
'm',
'abcd@abcd.com',
'010-1234-5678',
'02-1111-2222',
'12345',
'서울시 구로구 구로동',
'이젠 컴퓨터학원 신도림점',
'2000-01-01',
sysdate,
1);

-- 회원 정보 롤(등급) 생성
INSERT INTO user_roles VALUES (
user_roles_seq.nextval,
'abcd1234',
'ROLE_USER'
);