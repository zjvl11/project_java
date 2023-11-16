--------------------------------------------------------------------------------

-- 회원 정보 테이블 생일 정보 현실화

-- 생일은 미래가 아닌 과거 시점으로 처리
-- 2000-01-01 형식으로 처리

-- ex) round(DBMS_RANDOM.VALUE(19,20),0)

set serveroutput on;

declare
    v_year CHAR(4);
    v_month CHAR(2);
    v_date CHAR(2);   
    temp_birthday CHAR(10);    
    temp_month NUMBER(2);
    temp_date NUMBER(2);
    temp_birthday_output DATE;
begin

    FOR i IN 1..100 LOOP
    
        v_year := TO_CHAR(round(DBMS_RANDOM.VALUE(1900,2022),0));
        
        temp_month := round(DBMS_RANDOM.VALUE(1,12),0);
        temp_date := round(DBMS_RANDOM.VALUE(1,31),0);
        
        IF temp_month < 10 THEN
            v_month := '0' || TO_CHAR(temp_month); 
        ELSE
            v_month := TO_CHAR(temp_month); 
        END IF;
            
        IF temp_date < 10 THEN
            v_date :=  '0' || TO_CHAR(temp_date);
        ELSE 
            v_date := TO_CHAR(temp_date);
        END IF;    
        
        temp_birthday := v_year || '-' || v_month || '-' || v_date;
            
        DBMS_OUTPUT.put_line(temp_birthday);        
        
        UPDATE member_tbl SET birthday = TO_DATE(temp_birthday)    
        WHERE id = 'ezen' || (1000+i);
        
        COMMIT;
        
        SELECT birthday INTO temp_birthday_output FROM member_tbl
        WHERE id = 'ezen' || (1000+i);
        
        DBMS_OUTPUT.put_line(TO_CHAR(temp_birthday_output));
        
    END LOOP;

end;
/