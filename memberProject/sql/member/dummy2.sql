----------------------------------------------------------------------

-- 회원 정보 테이블 회원명(이름)/성별 현실화

-- 참고)
-- 임의의 수(random) : 1 => 'm', 2 => 'f'
/*
select round(DBMS_RANDOM.VALUE(1,2),0) from dual;  
select decode((select round(DBMS_RANDOM.VALUE(1,2),0) from dual), 1, 'm', 2, 'f') from dual;  
*/


CREATE OR REPLACE PROCEDURE project_name_update_gen_proc  
IS  
      TYPE first_name_array      IS VARRAY(20) OF CLOB;  
      TYPE middle_name_array      IS VARRAY(12) OF CLOB;  
      TYPE last_name_array      IS VARRAY(12) OF CLOB;  
      first_names   first_name_array;  
      middle_names   middle_name_array;  
      last_names   last_name_array;        
      v_firstName CLOB;  
      v_middleName CLOB;  
      v_lastName CLOB;  
      totalName CLOB;  
      temp_num NUMBER(2); 
      temp_random_gender NUMBER(1);
      temp_gender CHAR(1);
BEGIN  
      first_names := first_name_array('김','이','박','최','주','임','엄','성','남궁','독고','황','황보','송','오','유','류','윤','장','정','추');  
      middle_names := middle_name_array('숙','갑','영','순','선','원','우','이','운','성','정','희');  
      last_names := last_name_array('영','수','희','빈','민','정','순','주','연','영','철','석');  
       
      FOR i IN 1..100 LOOP  
         
        temp_num := round(DBMS_RANDOM.VALUE(1,20),0);  
        v_firstName :=  first_names(temp_num);  
        temp_num := round(DBMS_RANDOM.VALUE(1,12),0);  
        v_middleName :=  middle_names(temp_num);  
        temp_num := round(DBMS_RANDOM.VALUE(1,12),0);  
        v_lastName :=  last_names(temp_num);  
        totalName := v_firstName || ' ' || v_middleName || v_lastName;  
                         
        UPDATE member_tbl SET name = totalName  
        WHERE id = 'ezen' || (1000+i);
        
        -- 성별 random 적용
        temp_random_gender := round(DBMS_RANDOM.VALUE(1,2),0);
        
        select decode(temp_random_gender, 1, 'm', 2, 'f') into temp_gender from dual;  
        
        UPDATE member_tbl SET gender = temp_gender
        WHERE id = 'ezen' || (1000+i); 
        
    END LOOP;  

    COMMIT;         
  END;  
/  
 
EXECUTE project_name_update_gen_proc;