-- 개별 회원 정보 roles(등급들) 조회
SELECT LISTAGG(r.role, ',') WITHIN GROUP (ORDER BY m.id) 
FROM member_tbl m, user_roles r  
WHERE m.id = r.username 
AND r.username = 'abcd1234';


-- 전체 회원 정보 roles(등급) 포함 조회
-- distinct를 사용해야 1인당 1개의 레코드가 출력됩니다.
-- role(등급)값이 다수인 경우는 아래와 같이 출력됨
-- 이를 하나의 컬럼에 함쳐서 출력하려면 LISTAGG(r2.role, ',') WITHIN GROUP을 사용합니다.
-- ex) ROLE_ADMIN,ROLE_USER   

-- LISTAGG 함수
-- https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/LISTAGG.html#GUID-B6E50D8E-F467-425B-9436-F7F8BF38D466
-- 참고) SQL 200제 : Oracle(오라클) 연습용 참고 교재 45번 항목

SELECT *  
FROM (SELECT m.*,  
             FLOOR((ROWNUM - 1) / 10 + 1) page  
      FROM (
	      	 SELECT 
	      	 DISTINCT m.*,
			 (
			    SELECT LISTAGG(r2.role, ',') WITHIN GROUP (ORDER BY m2.id) 
			    FROM member_tbl m2, user_roles r2  
			    WHERE r2.username = m2.id
			    AND r2.username = m.id
			 ) AS "ROLE"           
			 FROM member_tbl m, user_roles r
			 WHERE m.id = r.username
			 and m.id = 'abcd1234'	
             ORDER BY id ASC
          ) m  
      )  
WHERE page = 1;
