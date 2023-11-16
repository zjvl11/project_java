-- 프로젝트 전용 계정 생성
create user project identified by project;

grant connect, resource, create view to project;