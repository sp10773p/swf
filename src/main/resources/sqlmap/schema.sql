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

CREATE TABLE CM_MENU(
  MENU_ID VARCHAR(10) NOT NULL,
  MENU_NM VARCHAR(50) NOT NULL,
  MENU_LVL INTEGER NOT NULL,
  MENU_TYPE VARCHAR(5),
  P_MENU_ID VARCHAR(10),
  SEQ INTEGER,
  URL VARCHAR(50),
  USEYN VARCHAR(1) DEFAULT 'Y',
  LEAFYN VARCHAR(1)
);

CREATE TABLE TB_DETAIL_TEST (
  ID varchar(10) NOT NULL,
  COL1 varchar(10) NOT NULL,
  COL2 varchar(10) NOT NULL,
  COL3 varchar(8),
  COL4 varchar(10),
  COL5 varchar(10),
  COL6 varchar(10),
  COL7 varchar(10),
  COL8 varchar(10),
  COL9 varchar(10),
  COL10 varchar(10),
  COL11 varchar(10),
  COL12 varchar(10),
  COL13 varchar(10),
  COL14 varchar(10),
  COL15 varchar(10),
  COL16 varchar(10),
  COL17 INTEGER,
  COL18 INTEGER,
  COL19 INTEGER,
  COL20 varchar(500)
);