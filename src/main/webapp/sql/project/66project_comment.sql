use Board;

select * from Member;

select * from Comment;
create table Comment(
	id int primary key auto_increment,
	boardId int not null,
    memberId varchar(20) not null,
    content varchar(500) not null,
    inserted datetime not null default now(),
    foreign key (boardId) references Board(id),
    foreign key (memberId) references Member(id)
);

select * from Member;

insert into Comment (boardId, memberId, content) values (2144, 'god', 'ㅎ2');
insert into Comment (boardId, memberId, content) values (2144, 'god', 'ㅎ22');
insert into Comment (boardId, memberId, content) values (2144, 'god', 'ㅎ22');
insert into Comment (boardId, memberId, content) values (2144, 'while', 'dd');