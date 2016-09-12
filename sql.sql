select format(12345.6789, 2);
--displays two digits after decimal point
select concat(last_name,', ',first_name) as 'Name', format(gpa,2)
as 'GPA' from student;

--trim spaces around concat, also ltrim and rtrim
select concat('[',trim('     ssa     '),']');

--cast to other Type
select cast('2016-09-08 10:45:00' as datetime);

--add 1 second to date
select date_add('1957-08-26 23:59:59', interval 1 second);
select date_add('1957-08-26 23:59:59', interval 3 day 1 second);

--Pull only ids 1, 3 and 5
select * from customer where id in (1,3,5);

create table orders (
id int primary key auto_increment,
description varchar(50),
amount decimal(7,2) not null default 0,
customer_id int,
foreign key (customer_id) references customer(id)
--if record deleted, set the foreign key to null, on update cascade update
on delete set null on update cascade);

--show full table details
show create table orders;

--Procedure (like a method)
CREATE DEFINER=`root`@`localhost` PROCEDURE `Insert_Student`(
  IN FirstName varchar(30),
  IN LastName varchar(30),
  IN SAT int,
  IN GPA Decimal(5,1),
  IN MajorDescription varchar(50)
)
BEGIN
 DECLARE MajorId int;
 -- Check SAT between 400 and 1600 inclusive
 IF SAT < 400 OR SAT > 1600 THEN
  SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'SAT is out of range (400 <= x <= 1600)';
 END IF;
 --Check GPA between 0 <= x <= 5.0
 IF GPA < 0.0 OR GPA > 5.0 THEN
  SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'SAT is out of range (0 <= x <= 5.0)';
 END IF;
 SELECT id into MajorId from major where description = MajorDescription;
 INSERT student (first name, last_name, sat, gpa, major_id)
  values (FirstName, LastName, SAT, GPA, MajorId);
END

--Simple procedure for a select all
DELIMITER //
create procedure display_orders()
  BEGIN
   select * from orders;
   END//
DELIMITER ;
