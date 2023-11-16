-- 회원 정보 수정
-- 패쓰워드 : #Java1234(원형)) => $2a$10$lhx31tqVr9IDrG8NMrtdX.Q..cd1CDd4gRHPxaXZo47aJUOIdrZxK
-- 신규 패쓰워드 생성 : 단위 테스트 활용 => PasswordEncoderGenerator.java
UPDATE member_tbl SET
password = '$2a$10$lhx31tqVr9IDrG8NMrtdX.Q..cd1CDd4gRHPxaXZo47aJUOIdrZxK',
email = 'springjava@abcd.com',
mobile = '010-7878-9090',
phone = '02-1111-3333',
zip = '08285',
address1 = '서울 구로구 가마산로19길 30 (구로동, 일동행복세상아파트)',
address2 = '101동 1002호'
WHERE id = 'abcd1234';