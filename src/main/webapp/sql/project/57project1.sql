create database Board;
use Board;

-- 테이블명 : UpperCamelCase
-- 컬럼명 : lowerCamelCase
create table Board (
	id int primary key auto_increment,
	title varchar(100) not null, -- 제목
    body varchar(1000) not null, -- 본문
    writer varchar(50), -- 작성자
    inserted datetime default now() -- 등록날짜
);