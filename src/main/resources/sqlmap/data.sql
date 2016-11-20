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