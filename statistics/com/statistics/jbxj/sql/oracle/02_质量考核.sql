--车间样品分类
create table JBXJ_Quality_mattype(
       id varchar2(32) not null primary key,
       area_name varchar2(60) not null,
       mat_type varchar2(60) not null,
       sort int 
);

insert into JBXJ_Quality_mattype(id,area_name,mat_type,sort) 
values('01','合成车间','丁基',1);
insert into JBXJ_Quality_mattype(id,area_name,mat_type,sort) 
values('02','合成车间','溴化丁基',2);
commit;

insert into JBXJ_Quality_mattype(id,area_name,mat_type,sort) 
values('03','精制车间','丁基',3);
insert into JBXJ_Quality_mattype(id,area_name,mat_type,sort) 
values('04','精制车间','溴化丁基',4);
commit;

--考核关键点
create table JBXJ_Quality_keypoint(
       parent_id varchar2(60) not null,
       point_id varchar2(60),
       matcode varchar2(60) not null,
       testcode int not null,
       analyte varchar2(60) not null,
       sinonym varchar2(60) not null,
       sort int not null
);

insert into JBXJ_Quality_keypoint(parent_id,point_id,matcode,testcode,analyte,sinonym,sort)
values('01','SP-A13501','XJ0035',315,'门尼粘度','门尼粘度',1);

insert into JBXJ_Quality_keypoint(parent_id,point_id,matcode,testcode,analyte,sinonym,sort)
values('01','SP-A14501','XJ0035',315,'门尼粘度','门尼粘度',2);

insert into JBXJ_Quality_keypoint(parent_id,point_id,matcode,testcode,analyte,sinonym,sort)
values('01','SP-A15501','XJ0035',315,'门尼粘度','门尼粘度',3);

insert into JBXJ_Quality_keypoint(parent_id,point_id,matcode,testcode,analyte,sinonym,sort)
values('01','SP-A11001','XJ0148',2454,'EADC含量','浓度',4);

insert into JBXJ_Quality_keypoint(parent_id,point_id,matcode,testcode,analyte,sinonym,sort)
values('02','SP-A50701','XJ0079',467,'橡胶浓度','丁基橡胶浓度',1);
commit;

insert into JBXJ_Quality_keypoint(parent_id,point_id,matcode,testcode,analyte,sinonym,sort)
values('03',null,'XJ0135',5748,'门尼粘度','门尼粘度',1);

insert into JBXJ_Quality_keypoint(parent_id,point_id,matcode,testcode,analyte,sinonym,sort)
values('03',null,'XJ0135',5815,'硬脂酸钙','硬脂酸钙',2);

insert into JBXJ_Quality_keypoint (parent_id,point_id,matcode,testcode,analyte,sinonym,sort)
select '03',null,'XJ0135',5810,analyte,sinonym,3 from JBXJ_DS_ANALYTE where matcode='XJ0135' and testcode=5810 and sort=9;
commit;


insert into JBXJ_Quality_keypoint(parent_id,point_id,matcode,testcode,analyte,sinonym,sort)
values('04',null,'XJ0111',5748,'门尼粘度','门尼粘度',1);

insert into JBXJ_Quality_keypoint(parent_id,point_id,matcode,testcode,analyte,sinonym,sort)
values('04',null,'XJ0111',500,'抗氧剂','抗氧剂',2);

insert into JBXJ_Quality_keypoint (parent_id,point_id,matcode,testcode,analyte,sinonym,sort)
select '04',null,'XJ0111',5809,analyte,sinonym,3 from JBXJ_DS_ANALYTE where matcode='XJ0111' and testcode=5809 and sort=8;
commit;

--月度考核激励统计记录
create table JBXJ_quality_kpreport(
     id varchar2(32) not null primary key,
     area_name varchar2(60) not null,
     start_date varchar2(24) not null,
     end_date varchar2(24) not null,
     tj_date varchar2(24) not null,
     status varchar2(2) not null
);

create table JBXJ_Quality_kpdetail(
       report_id varchar2(32) not null,
       point_id varchar2(60) not null,
       matcode varchar2(60) not null,
       testcode int not null,
       analyte varchar2(60) not null,
       sinonym varchar2(60) not null,
       charlimts varchar2(60),
       sort int not null,
       done_num int not null,
       total_num int not null,
       mat_type varchar2(60)
);

create table JBXJ_quality_klreport(
     id varchar2(32) not null primary key,
     area_name varchar2(60) not null,
     start_date varchar2(24) not null,
     end_date varchar2(24) not null,
     tj_date varchar2(24) not null,
     status varchar2(2) not null
);


create table JBXJ_Quality_kldetail(
       report_id varchar2(32) not null,
       matcode varchar2(60) not null,
       testcode int not null,
       analyte varchar2(60) not null,
       sinonym varchar2(60) not null,
       charlimts varchar2(60),
       sort int not null,
       done_num int not null,
       total_num int not null,
       mat_type varchar2(60),
       abline varchar2(10) not null
);

--产品入库合格率
create table JBXJ_DS_ANALYTE_detail
(
  matcode    VARCHAR2(32) not null,
  testcode   INTEGER not null,
  analyte    VARCHAR2(120) not null,
  sinonym    VARCHAR2(120) not null,
  sort       INTEGER not null,
  status     VARCHAR2(10),
  rn_map     VARCHAR2(20) not null,
  charlimits VARCHAR2(120),
  month      varchar2(24) not null,
  tj_date    varchar2(24) not null
)
