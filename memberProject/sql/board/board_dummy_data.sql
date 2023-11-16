-- 게시글 더미 데이터
CREATE OR REPLACE PROCEDURE project_dummy_board_gen_proc
IS
 	TYPE v_subject_array IS VARRAY(10) OF varchar2(100);
    TYPE v_content_array IS VARRAY(10) OF varchar2(4000);
    v_id varchar2(20);
	v_subjects v_subject_array;
	v_subject varchar2(100);
	v_contents v_content_array;
	v_content varchar2(4000);
 	temp_num NUMBER(2); 
BEGIN
	
	-- 임의의 글 제목 후보군 등록
	v_subjects := v_subject_array('다시 독감이 유행하고 있답니다.',
								  '열심히 공부합시다.',
								  '이번 겨울은 얼마나 추울까요?',
								  '물가가 많이 올랐습니다.',
								  '이스라엘/가자 전쟁이 빨리끝났으면 좋겠습니다.',
								  '스프링은 부트 위주로 해야 합니다.',
								  'react도 공부해야 되는데....',
								  'Javascript는 클라이언트 언어입니다.',
								  '항저우에서는 수영이 참 잘했습니다.',
								  '구로디지털단지는 구내식당들이 많습니다.');
								  
  	-- 임의의 글 내용 후보군 등록
  	v_contents := v_content_array('겨울은 역시 공부하기는 춥습니다.',
  								 '항저우 우리 선수들 처럼 열심히 공부합시다.',
  								 '이스라엘/가자 전쟁보다 취업 전쟁이 무서워요',
  								 '오늘도 ....',
  								 '가끔씩 하늘도 쳐다봅시다.',
  								 '공부는 능률적으로 해야겠죠...',
  								 '돈 아껴써야죠, 건강도 중요하고...',
  								 '스프링 공부 쉽지 않지만 열심히 하고 있습니다.',
  								 '할게 참 많습니다.',
  								 '추워도 역시 지치지 말아야 합니다.');
 
    FOR i IN 1..100 LOOP
    
        -- 임의의 회원 아이디 후출
        SELECT m.id into v_id
		FROM (
		        SELECT ROWNUM AS num, id
		        FROM member_tbl
		     ) m
		WHERE m.num = (
						SELECT ROUND(DBMS_RANDOM.VALUE(1, 
								(SELECT count(*) FROM member_tbl)), 0) 
						FROM dual
				      );
				      
		-- 임의의 글제목 선정
		temp_num := round(DBMS_RANDOM.VALUE(1,10),0);  
        v_subject :=  v_subjects(temp_num);  	
        
        -- 임의의 글내용 선정
		temp_num := round(DBMS_RANDOM.VALUE(1,10),0);  
        v_content :=  v_contents(temp_num); 
    
    	-- 게시글 정보 생성
        INSERT INTO board_tbl VALUES
        (board_seq.nextval,
         v_id,
         '#Abcd1234',
         v_subject,
         v_content,
         null,
         null,
         0,
         0,  
         0,         
         0,
         sysdate);         

     END LOOP;
 
    COMMIT;    
END;
/

exec project_dummy_board_gen_proc;