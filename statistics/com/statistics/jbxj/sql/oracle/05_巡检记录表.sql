create table JBXJ_XJ_MAT(
     ID VARCHAR2(32) NOT NULL,
     MAT_TYPE VARCHAR2(60) NOT NULL,
     AREA VARCHAR2(60),
     PLANT VARCHAR2(60),
     POINT_ID VARCHAR2(60),
     matcode varchar2(60),
     testcode number(8,0),
     analyte varchar2(60),
     sort NUMBER(8,0),
     status varchar2(2)
);

insert into JBXJ_XJ_MAT (ID,MAT_TYPE,AREA,plant,POINT_ID,matcode,testcode,analyte,sort,status) 
values('1','Process','回收单元','回收单元','SP-A19501','XJ0047',619,'异戊二烯二聚物',1,'1');

insert into JBXJ_XJ_MAT (ID,MAT_TYPE,AREA,plant,POINT_ID,matcode,testcode,analyte,sort,status) 
values('3','Process','回收单元','回收单元','SP-A26001','XJ0059',163,'水',3,'1');

insert into JBXJ_XJ_MAT (ID,MAT_TYPE,AREA,plant,POINT_ID,matcode,testcode,analyte,sort,status) 
values('4','Process','回收单元','回收单元','SP-A22501','XJ0053',163,'水',4,'1');

insert into JBXJ_XJ_MAT (ID,MAT_TYPE,AREA,plant,POINT_ID,matcode,testcode,analyte,sort,status) 
values('5','Process','回收单元','回收单元','SP-A22501','XJ0053',1034,'DME',5,'1');

insert into JBXJ_XJ_MAT (ID,MAT_TYPE,AREA,plant,POINT_ID,matcode,testcode,analyte,sort,status) 
values('6','Process','回收单元','回收单元','SP-A27002','XJ0067',163,'水',6,'1');

insert into JBXJ_XJ_MAT (ID,MAT_TYPE,AREA,plant,POINT_ID,matcode,testcode,analyte,sort,status) 
values('8','Process','聚合单元','聚合单元','SP-A10001','XJ0030',163,'水',8,'1');

insert into JBXJ_XJ_MAT (ID,MAT_TYPE,AREA,plant,POINT_ID,matcode,testcode,analyte,sort,status) 
values('9','Process','聚合单元','聚合单元','SP-A10001','XJ0030',604,'异丁烯',9,'1');

alter table JBXJ_XJ_MAT add  flag varchar2(2);

create table JBXJ_XJ_REPORT(
     ID VARCHAR2(32) NOT NULL,
     MAT_TYPE VARCHAR2(60) NOT NULL,
     START_TIME VARCHAR2(60),
     END_TIME VARCHAR2(60),
     status varchar2(60),
     TJ_TIME VARCHAR2(60)
);


create table JBXJ_XJ_DETAIL(
     report_id VARCHAR2(32) NOT NULL,
     AREA VARCHAR2(60),
     PLANT VARCHAR2(60),
     POINT_ID VARCHAR2(60),
     matcode varchar2(60),
     testcode number(8,0),
     analyte varchar2(60),
     sort NUMBER(8,0),
     total_num number(8,0),
     done_num number(8,0),
     oosb_num number(8,0),
     oosa_num number(8,0),
     charlimits varchar2(60)
);