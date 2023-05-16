select * from Board;
select * from Member;

create table BoardLike (
    boardId int not null,
    memberId varchar(20) not null,
    primary key (boardId, memberId),
    foreign key (boardId) references Board(id),
    foreign key (memberId) references Member(id)
);

select * from BoardLike;
desc BoardLike;
select * from Member;
select b.id, bl.boardId, bl.memberId from Board b
	join BoardLike bl on b.id = bl.boardId;
    
SELECT 
	b.id,
	b.title,
	b.body,
	b.inserted,
	b.writer,
	b.hit,
	f.fileName,
	(select count(*) from BoardLike where boardId = b.id) as countLike
FROM Board b LEFT JOIN FileName f ON b.id = f.boardId
where b.id = 2134;