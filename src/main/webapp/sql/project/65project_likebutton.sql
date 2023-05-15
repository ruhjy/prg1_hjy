select * from Board;
select * from Member;

create table BoardLike (
    boardId int not null,
    memberId varchar(20) not null,
    primary key (boardId, memberId),
    foreign key (boardId) references Board(id),
    foreign key (memberId) references Member(id)
);