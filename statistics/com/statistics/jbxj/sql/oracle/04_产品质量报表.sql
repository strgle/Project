
select * from  mate_setting2 where areaname='聚合单元' order by matecode,testcode;

--统计记录
create table JBXJ_QREPORT_report(
     id varchar2(32) not null primary key,
     report_type varchar2(10) not null,
     start_time varchar2(24) not null,
     end_time varchar2(24) not null,
     tj_time varchar2(24) not null,
     status varchar2(10) not null
);


create table JBXJ_QREPORT_PROCESS(
     REPORT_ID VARCHAR2(32) NOT NULL,
     matcode varchar2(60) not null,
     done_num number(8,0) not null,
     total_num number(8,0) not null
);

create table JBXJ_QREPORT_PRODUCT(
     REPORT_ID VARCHAR2(32) NOT NULL,
     matcode varchar2(60),
     done_num number(8,0),
     oosb_num number(8,0),
     oosa_num number(8,0),
     total_num number(8,0)
     back_num number(8,0),
     back_rate number(8,2),
     line_type varchar2(2)
);

create table JBXJ_QREPORT_PRODUCT(
     REPORT_ID VARCHAR2(32) NOT NULL,
     MATCODE VARCHAR2(60),
     TESTCODE NUMBER(8,0),
     ANALYTE VARCHAR2(60),
     DONE_NUM number(8,0),
     total_num number(8,0)
);
