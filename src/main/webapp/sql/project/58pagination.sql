use w3schools;

-- /sub26/link1 ---> boot_hjy-3.0.5/Controller26, sub13/link1
insert into Customers (CustomerName, ContactName, Address, City, Country)
select CustomerName, ContactName, Address, City, Country from Customers;
select count(*) from Customers;


-- 페이지당 20개 레코드 ( n = (p - 1) * 20 )
select * from Customers order by CustomerID desc limit 0, 20; -- 1페이지
select * from Customers order by CustomerID desc limit 20, 20; -- 2페이지
select * from Customers order by CustomerID desc limit 40, 20; -- 3페이지 
select * from Customers order by CustomerID desc limit 60, 20; -- 4페이지

select count(*) from Customers;