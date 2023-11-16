-- 검색 

SELECT COUNT(*) FROM member_tbl
WHERE address1 like '%구로%'
OR address2 like '%구로%';

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
			 and address1 like '%구로%'
		     OR address2 like '%구로%'
             ORDER BY id DESC
          ) m  
      )  
WHERE page = 1;