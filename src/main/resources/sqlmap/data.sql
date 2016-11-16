insert into users values('rednics', 'Shin Kwan Young', '1234');

insert into cm_code values('CODE1', 'Banana', 'Banana');
insert into cm_code values('CODE2', 'Apple', 'Apple');

insert into TB_TEST values(1, '20160910', 'Client1', 100000, 10000, 110000, 'tax');
insert into TB_TEST values(2, '20160911', 'Client2', 100000, 10000, 110000, 'tax');
insert into TB_TEST values(3, '20160912', 'Client3', 100000, 10000, 110000, 'tax');
insert into TB_TEST values(4, '20160912', 'Client3', 100000, 10000, 110000, 'tax');
insert into TB_TEST values(5, '20160912', 'Client3', 100000, 10000, 110000, 'tax');
insert into TB_TEST values(6, '20160912', 'Client3', 100000, 10000, 110000, 'tax');
insert into TB_TEST values(7, '20160912', 'Client3', 100000, 10000, 110000, 'tax');
insert into TB_TEST values(8, '20160912', 'Client3', 100000, 10000, 110000, 'tax');
insert into TB_TEST values(9, '20160912', 'Client3', 100000, 10000, 110000, 'tax');
insert into TB_TEST values(10, '20160912', 'Client3', 100000, 10000, 110000, 'tax');
insert into TB_TEST values(11, '20160912', 'Client3', 100000, 10000, 110000, 'tax');
insert into TB_TEST values(12, '20160912', 'Client3', 100000, 10000, 110000, 'tax');

insert into TB_DETAIL_TEST values(1, 'COL1', 'COL2', '20160801', 'A', 'M, S', 'M', 'COL7', 'COL8', '20161001', '20161003', 'COL11', 'COL12', 'COL13', 'COL14', 'COL15', 'COL16', 17, 18, 19, 'COL20');
insert into TB_DETAIL_TEST values(2, '2COL1', '2COL2', '20160903', 'B', 'S', 'S', 'COL7', 'COL8', '20161004', '20161006', 'COL11', 'COL12', 'COL13', 'COL14', 'COL15', 'COL16', 17, 18, 19, 'COL20');

INSERT INTO CM_MENU VALUES('CM'         , 'Setting' , 1, 'A', ''     , 1, NULL        , 'Y', 'N');
INSERT INTO CM_MENU VALUES('CM_SYST'    , 'Admin'   , 2, 'A', 'CM'      , 1, NULL        , 'Y', 'N');
INSERT INTO CM_MENU VALUES('CM_SYST_101', 'Menu', 3, 'A', 'CM_SYST' , 1, 'mainView.do'    , 'Y', 'Y');

INSERT INTO CM_MENU VALUES('SM'         , 'Sample'  , 1, 'A', ''     , 2, NULL        , 'Y', 'N');
INSERT INTO CM_MENU VALUES('SM_SMPL'    , 'Sample Design'  , 2, 'A', 'SM'      , 1, NULL        , 'Y', 'N');

INSERT INTO CM_MENU VALUES('SM_SMPL_101', 'List Sample'     , 3, 'A', 'SM_SMPL', 1,'mainView.do', 'Y', 'Y');
INSERT INTO CM_MENU VALUES('SM_SMPL_201', 'Due List Sample1', 3, 'A', 'SM_SMPL', 2,'mainView.do', 'Y', 'Y');
INSERT INTO CM_MENU VALUES('SM_SMPL_301', 'Detail Sample'   , 3, 'A', 'SM_SMPL', 3,'mainView.do', 'Y', 'Y');


INSERT INTO CM_MENU VALUES('TM'         , 'Test Menu' , 1, 'A', ''     , 3, NULL        , 'Y', 'N');
INSERT INTO CM_MENU VALUES('TM_SMPL_101', 'TEST 1'     , 2, 'A', 'TM', 1, 'mainView.do', 'Y', 'Y');
INSERT INTO CM_MENU VALUES('TM_SMPL_102', 'TEST 2'     , 2, 'A', 'TM', 2, 'mainView.do', 'Y', 'Y');
INSERT INTO CM_MENU VALUES('TM_SMPL_103', 'TEST 3'     , 2, 'A', 'TM', 3, 'mainView.do', 'Y', 'Y');