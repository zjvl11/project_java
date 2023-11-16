-- 회원 활동/휴면 상태 변경 : 완전한 삭제가 아닌 회원 상태(enabled = 0)만 변경
UPDATE member_tbl SET enabled = 0 
WHERE id = 'thyme1234';