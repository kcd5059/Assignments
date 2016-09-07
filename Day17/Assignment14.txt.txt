create table major (
  id int primary key auto_increment,
  name varchar(50),
  req_sat_score int,
  CHECK (req_sat_score > 399 AND req_sat_score < 1601)
);

mysql> explain major;
+---------------+-------------+------+-----+---------+----------------+
| Field         | Type        | Null | Key | Default | Extra          |
+---------------+-------------+------+-----+---------+----------------+
| id            | int(11)     | NO   | PRI | NULL    | auto_increment |
| name          | varchar(50) | YES  |     | NULL    |                |
| req_sat_score | int(11)     | YES  |     | NULL    |                |
+---------------+-------------+------+-----+---------+----------------+
3 rows in set (0.06 sec)

insert major (name,req_sat_score) values ('General Business',800);
insert major (name,req_sat_score) values ('Accounting',1000);
insert major (name,req_sat_score) values ('Finance',1100);
insert major (name,req_sat_score) values ('Math',1300);
insert major (name,req_sat_score) values ('Engineering',1350);
insert major (name,req_sat_score) values ('Education',900);
insert major (name,req_sat_score) values ('General Studies',500);

create table instructor (
  id int primary key auto_increment,
  first_name varchar(30),
  last_name varchar(30),
  major_id int,
  foreign key (major_id) references major(id),
  years_of_experience int,
  CHECK (years_of_experience > 0),
  is_tenured tinyint
);

mysql> explain instructor;
+---------------------+-------------+------+-----+---------+----------------+
| Field               | Type        | Null | Key | Default | Extra          |
+---------------------+-------------+------+-----+---------+----------------+
| id                  | int(11)     | NO   | PRI | NULL    | auto_increment |
| first_name          | varchar(30) | YES  |     | NULL    |                |
| last_name           | varchar(30) | YES  |     | NULL    |                |
| major_id            | int(11)     | YES  | MUL | NULL    |                |
| years_of_experience | int(11)     | YES  |     | NULL    |                |
| is_tenured          | tinyint(4)  | YES  |     | NULL    |                |
+---------------------+-------------+------+-----+---------+----------------+
6 rows in set (0.00 sec)

create table class (
  id int primary key auto_increment,
  name varchar(30),
  course_number int,
  instructor_id int,
  foreign key (instructor_id) references instructor(id)
);

mysql> explain class;
+---------------+-------------+------+-----+---------+----------------+
| Field         | Type        | Null | Key | Default | Extra          |
+---------------+-------------+------+-----+---------+----------------+
| id            | int(11)     | NO   | PRI | NULL    | auto_increment |
| name          | varchar(30) | YES  |     | NULL    |                |
| course_number | int(11)     | YES  |     | NULL    |                |
| instructor_id | int(11)     | YES  | MUL | NULL    |                |
+---------------+-------------+------+-----+---------+----------------+
4 rows in set (0.00 sec)

create table major_class_relationship (
  id int primary key auto_increment,
  major_id int NOT NULL,
  foreign key (major_id) references major(id),
  class_id int NOT NULL,
  foreign key (class_id) references class(id)
);

mysql> explain major_class_relationship;
+----------+---------+------+-----+---------+----------------+
| Field    | Type    | Null | Key | Default | Extra          |
+----------+---------+------+-----+---------+----------------+
| id       | int(11) | NO   | PRI | NULL    | auto_increment |
| major_id | int(11) | NO   | MUL | NULL    |                |
| class_id | int(11) | NO   | MUL | NULL    |                |
+----------+---------+------+-----+---------+----------------+
3 rows in set (0.00 sec)

create table student_class_relationship (
  id int primary key auto_increment,
  student_id int NOT NULL,
  foreign key (student_id) references student(id),
  class_id int NOT NULL,
  foreign key (class_id) references class(id)
);

mysql> explain student_class_relationship;
+------------+---------+------+-----+---------+----------------+
| Field      | Type    | Null | Key | Default | Extra          |
+------------+---------+------+-----+---------+----------------+
| id         | int(11) | NO   | PRI | NULL    | auto_increment |
| student_id | int(11) | NO   | MUL | NULL    |                |
| class_id   | int(11) | NO   | MUL | NULL    |                |
+------------+---------+------+-----+---------+----------------+
3 rows in set (0.00 sec)

alter table student
 add gpa decimal(5,1),
 add sat int,
 add major_id int,
 add constraint foreign key (major_id) references major(id),
 drop years_of_experience;

 mysql> explain student;
+------------+--------------+------+-----+---------+-------+
| Field      | Type         | Null | Key | Default | Extra |
+------------+--------------+------+-----+---------+-------+
| id         | int(11)      | NO   | PRI | NULL    |       |
| first_name | varchar(30)  | NO   |     | NULL    |       |
| last_name  | varchar(30)  | NO   |     | NULL    |       |
| start_date | date         | NO   |     | NULL    |       |
| gpa        | decimal(5,1) | YES  |     | NULL    |       |
| sat        | int(11)      | YES  |     | NULL    |       |
| major_id   | int(11)      | YES  | MUL | NULL    |       |
+------------+--------------+------+-----+---------+-------+
7 rows in set (0.00 sec)

alter table assignment
 add class_id int,
 add constraint foreign key (class_id) references class(id);

 mysql> explain assignment;
 +----------------+---------+------+-----+---------+-------+
 | Field          | Type    | Null | Key | Default | Extra |
 +----------------+---------+------+-----+---------+-------+
 | id             | int(11) | NO   | PRI | NULL    |       |
 | student_id     | int(11) | NO   | MUL | NULL    |       |
 | assignment_nbr | int(11) | NO   |     | NULL    |       |
 | grade_id       | int(11) | YES  | MUL | NULL    |       |
 | class_id       | int(11) | YES  | MUL | NULL    |       |
 +----------------+---------+------+-----+---------+-------+
 5 rows in set (0.00 sec)

--triggers for major table
 DELIMITER $$
 CREATE TRIGGER `major_trg_ins` BEFORE INSERT ON `major`
FOR EACH ROW
BEGIN
    IF NOT(New.req_sat_score > 399 AND New.req_sat_score < 1601) THEN
    SIGNAL SQLSTATE '10000'
        SET MESSAGE_TEXT = 'check constraint on major failed during insert';
    END IF;
END;
$$

CREATE TRIGGER `major_trg_upd` BEFORE UPDATE ON `major`
FOR EACH ROW
BEGIN
    IF NOT(New.req_sat_score > 399 AND New.req_sat_score < 1601) THEN
    SIGNAL SQLSTATE '10000'
        SET MESSAGE_TEXT = 'check constraint on major failed during update';
    END IF;
END;
$$
DELIMITER ;

--triggers for instructor table
DELIMITER $$
CREATE TRIGGER `instructor_trg_ins` BEFORE INSERT ON `instructor`
FOR EACH ROW
BEGIN
   IF NOT(New.years_of_experience > 0) THEN
   SIGNAL SQLSTATE '10000'
       SET MESSAGE_TEXT = 'check constraint on instructor failed during insert';
   END IF;
END;
$$

CREATE TRIGGER `instructor_trg_upd` BEFORE UPDATE ON `instructor`
FOR EACH ROW
BEGIN
   IF NOT(New.years_of_experience > 0) THEN
   SIGNAL SQLSTATE '10000'
       SET MESSAGE_TEXT = 'check constraint on instructor failed during update';
   END IF;
END;
$$
DELIMITER ;
