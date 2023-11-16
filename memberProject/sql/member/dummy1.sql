-- 회원 정보 테이블 회원 더미 정보 생성
-- 패쓰워드 원래 값 : #Abcd1234 

CREATE OR REPLACE PROCEDURE project_dummy_member_gen_proc
IS
BEGIN
 
    FOR i IN 1..100 LOOP
    
    	-- 회원 정보 생성
        INSERT INTO member_tbl VALUES
        ('ezen' || (1000+i),
         '$2a$10$owcQ8pNZJdViGHJks4d2b.gjNIQb5cwbYBs8Yd7O3yuG6Q612dOae',
         '김' || (100+i),
         'm',
         'ezen' || i || '@abcd.com',
         '010-1234-' || (1000+i),
         '02-860-1234',
         '08290',  
         '서울특별시 구로구 공원로 83',         
         '4층 이젠컴퓨터아카데미',
         '2000-01-01',
         SYSDATE,
         1);
         
         -- 회원 롤 정보 생성
     	INSERT INTO user_roles VALUES (
		user_roles_seq.nextval,
		'ezen' || (1000+i),
		'ROLE_USER');

     END LOOP;
 
    COMMIT;    
END;
/

exec project_dummy_member_gen_proc;