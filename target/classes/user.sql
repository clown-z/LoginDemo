use mybatis;
Drop TABLE if exists user;
create table user(
	id int primary key auto_increment,
    username varchar(20),
    password varchar(20)
);