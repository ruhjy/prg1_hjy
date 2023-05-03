use w3schools;

select 
	c.CategoryID as id,
    c.CategoryName as name,
    c.Description,
    p.ProductName
from Categories c
	join Products p on c.categoryID = p.categoryID;
    
select * from Categories;

select 
	* 
from Suppliers s 
	join Products p on s.SupplierID = p.SupplierID 
where s.SupplierID = 2;

desc Products;

select * from Products;
select * from OrderDetails;

select 
	p.ProductName, 
    sum(p.Price * od.Quantity) as '제품별 합계'
from Products p
	join OrderDetails od on p.ProductID = od.ProductID
group by p.ProductID
having p.ProductID = 1;

Select 
	s.SupplierID,
    s.SupplierName,
    s.ContactName,
    s.City,
    s.Address,
    s.PostalCode,
    s.Country,
    s.Phone,
    p.ProductName,
    p.Unit,
    p.Price
from Suppliers s
	right join Products p on s.SupplierID = p.ProductID
where s.SupplierID = 5;