use Board;

select * from Board order by id desc;
select * from Member;
select * from MemberAuthority;
select * from Board order by id desc;
select * from FileName;
select * from Board order by id desc;

select * from Board b
	join FileName f on b.id = f.boardId;
    
    
desc Board;
desc FileName;
desc Member;

update Board set hit = hit + 1 where id = 2;
alter table Board add column hit int after writer;

select * from Member order by inserted desc;
select id, password from Member where id = 'test2';

delete from Board where writer = 'qqq';

update Board set writer = 'while';

select * from BoardLike;




