create table JBXJ_DS_MAT(
     matcode varchar2(32) not null primary key,
     sort int not null,
     dept varchar2(120) not null
);

comment on table JBXJ_DS_MAT is '日汇总样品设置';
comment on column  JBXJ_DS_MAT.matcode is '样品编码';
comment on column  JBXJ_DS_MAT.sort is '排序';
comment on column  JBXJ_DS_MAT.dept is '部门';

insert into JBXJ_DS_MAT(matcode,sort,dept) values('XJ0111',0,'京博石油化工有限公司橡胶分公司');
insert into JBXJ_DS_MAT(matcode,sort,dept) values('XJ0135',1,'京博石油化工有限公司橡胶分公司');
commit;

create table JBXJ_DS_ANALYTE(
      MATCODE VARCHAR2(32) NOT NULL,
      testcode int not null,
      analyte varchar2(120) not null,
      sinonym varchar2(120) not null,
      sort int not null
);

alter table JBXJ_DS_ANALYTE add charlimits varchar(120);
alter TABLE JBXJ_DS_ANALYTE ADD CONSTRAINT ui_JBXJ_DS_ANALYTE UNIQUE (MATCODE, testcode, analyte);
alter table JBXJ_DS_ANALYTE add is_monitor varchar(2);
alter table JBXJ_DS_ANALYTE add IS_XJ varchar2(2);

comment on table JBXJ_DS_ANALYTE is '日汇总样品对应的分析项目';
comment on column  JBXJ_DS_ANALYTE.matcode is '样品编码';
comment on column  JBXJ_DS_ANALYTE.sort is '排序';
comment on column  JBXJ_DS_ANALYTE.testcode is '测试代码';
comment on column  JBXJ_DS_ANALYTE.analyte is '分析项';
comment on column  JBXJ_DS_ANALYTE.sinonym is '分析项别名';
alter table JBXJ_DS_ANALYTE add status varchar2(10);
comment on column JBXJ_DS_ANALYTE.status is '状态,1:有效、0：无效';
alter table JBXJ_DS_ANALYTE  add constraint UI_JBXJ_DS_ANALYTE unique (MATCODE, TESTCODE, ANALYTE);



insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',5748,'门尼粘度','门尼粘度',1,'RN01');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',1083,'挥发分','挥发分',2,'RN02');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',323,'平均值','灰分',3,'RN03');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',5809,'Fh','Fh',4,'RN04');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',5809,'Fl','Fl',5,'RN05');

--TS1
insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
select 'XJ0111',testcode,analyte,sinonym,6,'RN06' from analytes where testcode=5809 and origrec=41235;

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',5809,'t´c(50)','t´c(50)',7,'RN07');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',5809,'t´c(90)','t´c(90)',8,'RN08');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',5815,'硬脂酸钙','硬脂酸钙',9,'RN09');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',500,'抗氧剂','防老剂',10,'RN10');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',5814,'溴含量','溴含量',11,'RN11');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',329,'100%定伸(中位数)','100%',13,'RN13');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',329,'300%定伸(中位数)','300%',14,'RN14');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',329,'拉伸强度(中位数)','拉伸强度',15,'RN15');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',329,'断裂伸长率(中位数)','断裂伸长率',16,'RN16');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',329,'撕裂强度(中位数)','撕裂强度',17,'RN17');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0111',5848,'颜色','外观',18,'RN18');

--XJ0135

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',5748,'门尼粘度','门尼粘度',1,'RN01');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',1083,'挥发分','挥发分',2,'RN02');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',5749,'不饱和度','不饱和度',3,'RN03');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',323,'平均值','灰分',4,'RN04');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',5810,'Fh','Fh',5,'RN05');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',5810,'Fl','Fl',6,'RN06');

--TS1
insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
select 'XJ0135',testcode,analyte,sinonym,7,'RN07' from analytes where testcode=5810 and origrec=41255;

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',5810,'t´c(50)','t´c(50)',8,'RN08');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',5810,'t´c(90)','t´c(90)',9,'RN09');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',5810,'t5','t5',10,'RN10');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',5810,'t35','t35',11,'RN11');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',5815,'硬脂酸钙','硬脂酸钙',12,'RN12');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',500,'抗氧剂','抗氧剂',13,'RN13');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',329,'100%定伸(中位数)','100%',14,'RN14');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',329,'300%定伸(中位数)','300%',15,'RN15');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',329,'拉伸强度(中位数)','拉伸强度',16,'RN16');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',329,'断裂伸长率(中位数)','断裂伸长率',17,'RN17');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',329,'撕裂强度(中位数)','撕裂强度',18,'RN18');

insert into JBXJ_DS_ANALYTE(MATCODE,testcode,analyte,sinonym,sort,RN_MAP) 
values('XJ0135',958,'颜色','外观',19,'RN19');
COMMIT;

update JBXJ_DS_ANALYTE set is_xj='2' where testcode=323;
update JBXJ_DS_ANALYTE set is_xj='2' where testcode=5810;
update JBXJ_DS_ANALYTE set is_xj='2' where testcode=500;
update JBXJ_DS_ANALYTE set is_xj='2' where testcode=5815;
update JBXJ_DS_ANALYTE set is_xj='2' where testcode=5749;

update JBXJ_DS_ANALYTE set is_xj='1' where testcode=1083 ;
update JBXJ_DS_ANALYTE set is_xj='1' where testcode=5748;
update JBXJ_DS_ANALYTE set is_xj='1' where testcode=958 or testcode=5848;

create table JBXJ_Daily(
    tj_date varchar2(24) not null,
    matcode varchar2(30) not null,
    ABLINE VARCHAR2(10) not null,
    batchno varchar2(60) not null,
    sort int not null,
    RN01 VARCHAR2(60),
    RN02 VARCHAR2(60),
    RN03 VARCHAR2(60),
    RN04 VARCHAR2(60),
    RN05 VARCHAR2(60),
    RN06 VARCHAR2(60),
    RN07 VARCHAR2(60),
    RN08 VARCHAR2(60),
    RN09 VARCHAR2(60),
    RN10 VARCHAR2(60),
    RN11 VARCHAR2(60),
    RN12 VARCHAR2(60),
    RN13 VARCHAR2(60),
    RN14 VARCHAR2(60),
    RN15 VARCHAR2(60),
    RN16 VARCHAR2(60),
    RN17 VARCHAR2(60),
    RN18 VARCHAR2(60),
    RN19 VARCHAR2(60),
    RN20 VARCHAR2(60),
    RN21 VARCHAR2(60),
    RN22 VARCHAR2(60),
    RN23 VARCHAR2(60),
    RN24 VARCHAR2(60),
    RN25 VARCHAR2(60),
    RN26 VARCHAR2(60),
    RN27 VARCHAR2(60),
    RN28 VARCHAR2(60),
    RN29 VARCHAR2(60),
    RN30 VARCHAR2(60),
    RN31 VARCHAR2(60),
    RN32 VARCHAR2(60),
    RN33 VARCHAR2(60),
    RN34 VARCHAR2(60),
    RN35 VARCHAR2(60),
    RN36 VARCHAR2(60),
    RN37 VARCHAR2(60),
    RN38 VARCHAR2(60),
    RN39 VARCHAR2(60),
    RN40 VARCHAR2(60) 
);
alter table JBXJ_Daily add samp_date varchar2(24);

comment on table JBXJ_Daily is '检测结果每日汇总表';
comment on column JBXJ_Daily.tj_date is '统计时间';
comment on column JBXJ_Daily.matcode is '材料编号';
comment on column JBXJ_Daily.ABLINE is 'A/B线';
comment on column JBXJ_Daily.batchno is '批号';
comment on column JBXJ_Daily.sort is '序号';
alter table JBXJ_Daily add brand varchar2(20); 
alter table JBXJ_Daily add COMMENTS varchar2(200); 
comment on column JBXJ_Daily.brand is '牌号';
comment on column JBXJ_Daily.COMMENTS is '备注';
alter table JBXJ_Daily add start_date varchar2(24) not null; 
alter table JBXJ_Daily add end_date varchar2(24) not null;  
alter table JBXJ_Daily add ordno varchar2(32) not null;
alter table JBXJ_Daily add constraint pk_JBXJ_Daily primary key(ordno);


create table JBXJ_DS_report(
     id varchar2(32) not null primary key,
     start_date varchar2(24) not null,
     end_date varchar2(24) not null,
     matcode  VARCHAR2(32) not null,
     tj_date varchar2(24) not null,
     status varchar2(10) not null
);

create table JBXJ_DS_ANALYTE_report(
     report_id varchar2(32) not null,
     matcode  VARCHAR2(32) not null,
     testcode INTEGER not null,
     analyte  VARCHAR2(120) not null,
     sinonym  VARCHAR2(120) not null
);

alter table jbxj_ds_analyte_report add charlimit varchar2(200);

create table JBXJ_Daily_Detail(
    matcode varchar2(30) not null,
    ABLINE VARCHAR2(10) not null,
    ordno varchar2(60) not null,
    batchno varchar2(60) not null,
    testcode int not null,
    analyte varchar2(60),
    final_num varchar2(60),
    status varchar2(60)
);