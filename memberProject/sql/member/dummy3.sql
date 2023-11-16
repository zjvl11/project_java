--------------------------------------------------------------

-- 회원 정보 테이블 우편번호/주소 정보 현실화

-- 08291
-- 서울특별시 구로구 새말로9길 45 (구로동, 신도림현대아파트) 
-- 서울특별시 구로구 구로동 560 신도림현대아파트
-- 상세주소 범위 : 101~103동 1~19층 ex) 1901호

-- 08288
-- 서울특별시 구로구 새말로 93 (구로동, 신도림태영타운)
-- 서울특별시 구로구 구로동 1267 신도림태영타운
-- 상세주소 범위 : 101~113동 1~16층 ex) 1601호

-- 07376
-- 서울특별시 영등포구 도신로 31 (대림동, 현대3차아파트)
-- 서울특별시 영등포구 대림동 608-1 현대3차아파트
-- 상세주소 범위 : 301~307동 1~30층 ex) 2801호

-- 08208 
-- 서울특별시 구로구 경인로65길 16-15 (신도림동, 신도림4차 e-편한세상)
-- 서울특별시 구로구 신도림동 646 신도림4차 e-편한세상
-- 상세주소 범위 : 1101~1115동 1~25층 ex) 2501호

set SERVEROUTPUT ON

----------------------------------------------------

DECLARE
   
    TYPE apt_address_record IS RECORD (
        zip_code CHAR(5),
        road_address NVARCHAR2(200),
        jibun_address NVARCHAR2(200),
        detail_address NVARCHAR2(200)
    );
    
    TYPE apt_address_tbl IS TABLE OF apt_address_record;
    
    apt_addresses apt_address_tbl;
    
    temp_num NUMBER(1);
    
    temp_detail_address NVARCHAR2(200);
    
    temp_dong NVARCHAR2(5);
    
    temp_ho NVARCHAR2(5);
    
    FUNCTION apt_address_record_constructor (
            zip_code CHAR,
            road_address NVARCHAR2,
            jibun_address NVARCHAR2,
            detail_address NVARCHAR2
        ) RETURN apt_address_record IS
        apt apt_address_record;
    BEGIN
        apt.zip_code := zip_code;
        apt.road_address := road_address;
        apt.jibun_address := jibun_address;
        apt.detail_address := detail_address;
        RETURN apt;
    END apt_address_record_constructor;          
 
BEGIN  
      
      apt_addresses := apt_address_tbl(
    
            apt_address_record_constructor('08291',
                    '서울특별시 구로구 새말로9길 45 (구로동, 신도림현대아파트)',
                    '서울특별시 구로구 구로동 560 신도림현대아파트',
                    ''),
            apt_address_record_constructor('08288',
                    '서울특별시 구로구 새말로 93 (구로동, 신도림태영타운)',
                    '서울특별시 구로구 구로동 1267 신도림태영타운',
                    ''),
            apt_address_record_constructor('07376',
                    '서울특별시 영등포구 도신로 31 (대림동, 현대3차아파트)',
                    '서울특별시 영등포구 대림동 608-1 현대3차아파트',
                    ''),
            apt_address_record_constructor('08208',        
                    '서울특별시 구로구 경인로65길 16-15 (신도림동, 신도림4차 e-편한세상)',
                    '서울특별시 구로구 신도림동 646 신도림4차 e-편한세상',
                    '')
      );
      
      FOR i IN 1..100 LOOP  
         
        temp_num := round(DBMS_RANDOM.VALUE(1,4),0);  
        DBMS_OUTPUT.put_line('temp_num : ' ||  temp_num);
        
        -- 상세 주소 임의(random) 설정
        /*
            -- 1번 :
            -- 08291
            -- 서울특별시 구로구 새말로9길 45 (구로동, 신도림현대아파트) 
            -- 서울특별시 구로구 구로동 560 신도림현대아파트
            -- 상세주소 범위 : 101~103동 1~19층 ex) 1901호
            
            -- 2번 :
            -- 08288
            -- 서울특별시 구로구 새말로 93 (구로동, 신도림태영타운)
            -- 서울특별시 구로구 구로동 1267 신도림태영타운
            -- 상세주소 범위 : 101~113동 1~16층 ex) 1601호
            
            -- 3번 :
            -- 07376
            -- 서울특별시 영등포구 도신로 31 (대림동, 현대3차아파트)
            -- 서울특별시 영등포구 대림동 608-1 현대3차아파트
            -- 상세주소 범위 : 301~307동 1~30층 ex) 2801호
            
            -- 4번 :
            -- 08208 
            -- 서울특별시 구로구 경인로65길 16-15 (신도림동, 신도림4차 e-편한세상)
            -- 서울특별시 구로구 신도림동 646 신도림4차 e-편한세상
            -- 상세주소 범위 : 1101~1115동 1~25층 ex) 2501호
        
           ex) 
           select round(DBMS_RANDOM.VALUE(10,19),0) || 
           round(DBMS_RANDOM.VALUE(0,1),0) || 
           round(DBMS_RANDOM.VALUE(1,9),0) || '동'
           from dual;
        */
            IF temp_num = 1 THEN           
                              
                -- 상세주소 범위 : 101~103동 1~19층 ex) 1901호                
                temp_dong := round(DBMS_RANDOM.VALUE(101,103),0) || '동';
                 
                temp_ho := round(DBMS_RANDOM.VALUE(10,19),0) || 
                           round(DBMS_RANDOM.VALUE(0,1),0) || 
                           round(DBMS_RANDOM.VALUE(1,9),0) || '호';    
                               
             ELSIF temp_num = 2 THEN
                -- 상세주소 범위 : 101~113동 1~16층 ex) 1601호
                temp_dong := round(DBMS_RANDOM.VALUE(101,113),0) || '동';
                
                temp_ho := round(DBMS_RANDOM.VALUE(10,16),0) || 
                           round(DBMS_RANDOM.VALUE(0,1),0) || 
                           round(DBMS_RANDOM.VALUE(1,9),0) || '호';    
                
             ELSIF temp_num = 3 THEN
                    -- 상세주소 범위 : 301~307동 1~30층 ex) 2801호                
                    temp_dong := round(DBMS_RANDOM.VALUE(301,307),0) || '동';
                    
                    temp_ho := round(DBMS_RANDOM.VALUE(10,30),0) || 
                               round(DBMS_RANDOM.VALUE(0,1),0) || 
                               round(DBMS_RANDOM.VALUE(1,9),0) || '호';    
                
             ELSE
                    -- 상세주소 범위 : 1101~1115동 1~25층 ex) 2501호                
                    temp_dong := round(DBMS_RANDOM.VALUE(1101,1115),0) || '동';
                    
                    temp_ho := round(DBMS_RANDOM.VALUE(10,25),0) || 
                               round(DBMS_RANDOM.VALUE(0,1),0) || 
                               round(DBMS_RANDOM.VALUE(1,9),0) || '호';    
            END IF;           
        
            DBMS_OUTPUT.put_line(apt_addresses(temp_num).zip_code);
            DBMS_OUTPUT.put_line(apt_addresses(temp_num).road_address);
            DBMS_OUTPUT.put_line(apt_addresses(temp_num).jibun_address);
        
            -- 상세주소
            temp_detail_address := temp_dong || ' ' || temp_ho;            
            DBMS_OUTPUT.put_line('상세 주소 : ' || temp_detail_address);
            
            -- 주소 현황 업데이트(수정)
            -- 10.10 : 지번 주소는 생략 지번 주소 자리에 상세 주소 업데이트 
            UPDATE member_tbl SET 
            zip = apt_addresses(temp_num).zip_code,
            address1 = apt_addresses(temp_num).road_address,
            address2 = temp_detail_address
            WHERE id = 'ezen' || (1000+i);
        
      END LOOP;  

  COMMIT;         
END;  
/  