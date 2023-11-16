--------------------------------------------------------------------------------

-- 회원 정보 가입일 정보 현실화

set serveroutput on;

declare
    v_year CHAR(4);
    v_month CHAR(2);
    v_date CHAR(2);   
    temp_joindate CHAR(10);    
    temp_month NUMBER(2);
    temp_date NUMBER(2);
    curr_year NUMBER(4);
    curr_month NUMBER(2);
    curr_date NUMBER(2);
begin

    FOR i IN 1..100 LOOP
    
        -- 사이트 개설일이 2010년이라는 전제로 2010년 부터 생성.
        v_year := TO_CHAR(round(DBMS_RANDOM.VALUE(2010,2023),0));
        
        -- 년도가 2023년도(올해)일 경우는 현재 날짜보다 이전으로 생성될 수 있도록 조치. 
        -- 단, 사이트 개설일 부터 2022년까지의 다른 해에 대한 월/일 기본값은 모든 월/일이 포함하도록 조치.
        
        -- 현재 날짜에 대한 년/월/일 조회
        -- select TO_CHAR(sysdate, 'YYYY') from dual;
        -- select REPLACE(TO_CHAR(sysdate, 'MM'), '0', '') from dual; -- 08 => 8 변환
        -- select REPLACE(TO_CHAR(sysdate, 'DD'), '0', '') from dual; -- 09 => 9 변환
                
        select TO_CHAR(sysdate, 'YYYY') into curr_year from dual;        
        select REPLACE(TO_CHAR(sysdate, 'MM'), '0', '') into curr_month from dual;
        select REPLACE(TO_CHAR(sysdate, 'DD'), '0', '') into curr_date from dual;
        
        DBMS_OUTPUT.put_line('올해 : ' || curr_year);
        DBMS_OUTPUT.put_line('이번 달 : ' || curr_month);
        DBMS_OUTPUT.put_line('오늘 : ' || curr_date);
        
        -- 추출된 년도가 올해인 경우
        IF v_year = curr_year THEN
            temp_month := round(DBMS_RANDOM.VALUE(1,curr_month),0);
        -- 다른 해의 경우
        ELSE        
            temp_month := round(DBMS_RANDOM.VALUE(1,12),0);
        END IF;
        
        -- 올해 이번 달인 경우는 오늘까지만 가입일로 할당
        IF v_year = curr_year AND v_month = curr_month THEN
            temp_date := round(DBMS_RANDOM.VALUE(1,curr_date),0);
        ELSE    
            temp_date := round(DBMS_RANDOM.VALUE(1,31),0);
        END IF;    
        
        -- 10월 보다 적으면 '0' 부착 처리        
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
        
        temp_joindate := TRIM(v_year || '-' || v_month || '-' || v_date);
            
        DBMS_OUTPUT.put_line(temp_joindate);        
        
        UPDATE member_tbl SET joindate = TO_DATE(temp_joindate)    
        WHERE id = 'ezen' || (1000+i);
        
        COMMIT;
          
    END LOOP;

end;
/