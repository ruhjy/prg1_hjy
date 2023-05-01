create database Board;
use Board;

-- drop table Board;
-- 테이블명 : UpperCamelCase
-- 컬럼명 : lowerCamelCase
create table Board (
	id int primary key auto_increment,
	title varchar(100) not null, -- 제목
    body varchar(1000) not null, -- 본문
    writer varchar(50), -- 작성자
    inserted datetime default now() -- 등록날짜
);

desc Board;
select * from Board order by id desc;
select count(*) from Board;

insert into Board (title, body, writer)
values 
	('샘플 제목1', '샘플 본문1', 'user01'),
	('샘플 제목2', '샘플 본문2', 'user02'),
	('샘플 제목3', '샘플 본문3', 'user11'),
	('샘플 제목4', '샘플 본문4', 'user15');
    
insert into Board (title, body, writer)
values 
	('안녕001', '샘플 본문1', '익명01'),
	('안녕002', '샘플 본문2', '익명02'),
	('안녕003', '샘플 본문3', '익명11'),
	('안녕004', '샘플 본문4', '익명15');
    
create table Member(
	id int primary key auto_increment,
    member_name varchar(50),
    member_age int);