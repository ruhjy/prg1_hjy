use Board;

desc Board;

create table FileName (
	id int auto_increment primary key,
	boardId int not null,
    fileName varchar(300) not null,
    foreign key (boardId) references Board(id)
);

select * from FileName;

desc FileName;

select * from Board b
	join FileName f on b.id = f.boardId
where b.id = 2102; -- file 있는 게시물

select * from Board b
	join FileName f on b.id = f.boardId
where b.id = 1688; -- file 없는 게시물

select * from Board b
	left join FileName f on b.id = f.boardId
where b.id = 1688;

select * from Board where id = 1688;

--
use Board;
select 
	*, count(f.id) fileCount 
from Board b
	left join FileName f on b.id = f.boardId
group by b.id
order by b.id desc limit 0, 10;
