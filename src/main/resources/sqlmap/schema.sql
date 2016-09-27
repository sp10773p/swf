create schema swf AUTHORIZATION DBA;
set schema swf;

drop table users if exists;

CREATE TABLE users (
  id varchar(40) NOT NULL,
  username varchar(45) NOT NULL,
  password varchar(45) NOT NULL
);