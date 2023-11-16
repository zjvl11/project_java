-- 회원 정보 수정시 메일 중복 점검
-- : 자신의 기존 메일과는 중복 허용 하면서, 다른 회원 메일과는 중복 불허

-- 자신의 기존 메일로 재사용
SELECT COUNT(*) FROM (
        SELECT EMAIL FROM member_tbl
        WHERE ID != 'abcd1234'
    )
WHERE EMAIL='abcd@abcd.com';
    
-- 신규 메일 사용 : 다른 회원과 메일 정보 중복
SELECT COUNT(*) FROM (
        SELECT EMAIL FROM member_tbl
        WHERE ID != 'abcd1234'
    )
WHERE EMAIL='spring@abcd.com';
    
-- 신규 메일 사용 : 다른 회원과 메일 정보 중복(X)
SELECT COUNT(*) FROM (
        SELECT EMAIL FROM member_tbl
        WHERE ID != 'abcd1234'
    )
WHERE EMAIL='security@abcd.com';

-----------------------------------------------

-- 회원 정보 수정시 연락처(휴대폰) 중복 점검
-- : 자신의 기존 연락처(휴대폰)와는 중복 허용 하면서, 다른 회원 연락처(휴대폰)와는 중복 불허

-- 자신의 기존 연락처(휴대폰)로 재사용
SELECT COUNT(*) FROM (
        SELECT mobile FROM member_tbl
        WHERE ID != 'abcd1234'
    )
WHERE MOBILE='010-1234-5678';
    
-- 신규 연락처(휴대폰) 사용 : 다른 회원과 연락처(휴대폰) 정보 중복
SELECT COUNT(*) FROM (
        SELECT MOBILE FROM member_tbl
        WHERE ID != 'abcd1234'
    )
WHERE MOBILE='010-1111-6789';
    
-- 신규 연락처(휴대폰) 사용 : 다른 회원과 연락처(휴대폰) 정보 중복(X)
SELECT COUNT(*) FROM (
        SELECT MOBILE FROM member_tbl
        WHERE ID != 'abcd1234'
    )
WHERE MOBILE='010-2222-3434';