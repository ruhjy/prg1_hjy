use Board;
select * from Board;
select * from Member;

update Board set writer = 'update';

alter table Board add foreign key (writer) references Member(id);

select * from Member;

-- 권한 테이블 생성
create table MemberAuthority (
	memberId varchar(20) not null,
    authority varchar(30) not null,
    foreign key (memberId) references Member(id),
    primary key (memberId, authority)
);

insert into MemberAuthority values ('god', 'admin');
insert into MemberAuthority values ('while', 'manager');

select * from MemberAuthority;