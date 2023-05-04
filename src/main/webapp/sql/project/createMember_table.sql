select * from Member;

drop table Member;

create table Member (
	id varchar(20) primary key,
    password varchar(100) not null,
    nickName varchar(20) not null unique,
    email varchar(100) unique,
    inserted datetime default now()
);

select * from Member;