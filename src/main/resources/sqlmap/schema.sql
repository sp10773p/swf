create schema swf AUTHORIZATION DBA;
set schema swf;

drop table users if exists;
drop table CM_CODE if exists;
/*drop table TB_TEST if exists;*/

CREATE TABLE users (
  id varchar(40) NOT NULL,
  username varchar(45) NOT NULL,
  password varchar(45) NOT NULL
);

CREATE TABLE CM_CODE (
  CODE varchar(10) NOT NULL,
  CODE_NAME varchar(100) NOT NULL,
  DESCRIPTION varchar(500) NOT NULL
);

CREATE TABLE TB_TEST (
  ID varchar(10) NOT NULL,
  INVDATE varchar(8) NOT NULL,
  NAME varchar(20) NOT NULL,
  AMOUNT INTEGER  ,
  TAX INTEGER  ,
  TOTAL INTEGER  ,
  NOTE varchar(500)
);
