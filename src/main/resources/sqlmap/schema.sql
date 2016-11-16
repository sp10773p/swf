CREATE TABLE CM_MENU(
  MENU_ID VARCHAR(15) NOT NULL ,
  MENU_NM VARCHAR(50),
  MENU_LVL INT,
  MENU_TYPE VARCHAR(5),
  P_MENU_ID VARCHAR(10),
  SEQ INT,
  URL VARCHAR(50),
  USEYN VARCHAR(1) DEFAULT 'Y',
  LEAFYN VARCHAR(1)
);

CREATE TABLE CM_CODE_MASTER(
  GROUP_CODE VARCHAR(10) NOT NULL ,
  CODE VARCHAR(50) NOT NULL,
  NAME VARCHAR(100),
  SEQ INT,
  `DESC` VARCHAR(500),
  USEYN VARCHAR(1) DEFAULT 'Y'
);

CREATE TABLE CM_CODE_DETAIL(
  P_CODE VARCHAR(10) NOT NULL ,
  CODE VARCHAR(20) NOT NULL,
  NAME VARCHAR(100),
  SEQ INT,
  `DESC` VARCHAR(500),
  USEYN VARCHAR(1) DEFAULT 'Y'
);

CREATE TABLE TB_CRUD (
  C_DUE_DATE VARCHAR(8),
  C_SELECT VARCHAR(1),
  C_CHECK VARCHAR(20),
  C_RADIO VARCHAR(1),
  C_AUTOCOMPLETE VARCHAR(50),
  C_DATE DATE,
  C_TEXT1 VARCHAR(50),
  C_TEXT2 VARCHAR(50),
  C_TEXT3 INT
);