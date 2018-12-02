
create table JBXJ_RATE_report(
    id varchar2(32) not null primary key,
    report_type varchar2(10) not null,
    year int,
    month int,
    start_time varchar2(24) not null,
    end_time varchar2(24) not null,
    mat_type varchar2(20) not null,
    matcode varchar2(20),
    tj_date varchar2(24) not null,
    total_num int not null,
    status varchar2(4)
);

create table JBXJ_RATE_brand(
    id varchar2(32) not null primary key,
    mat_type varchar2(20) not null,
    matcode varchar2(20),
    brand varchar2(60) not null,
    grade varchar2(20) not null,
    sort int not null,
    year int,
    is_show varchar2(2),
    is_show2 varchar2(2),
    data_source varchar2(2)
);

comment on column JBXJ_RATE_BRAND.data_source is '数据来源';

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show)
values('0','Product','XJ0135','总批次','TOTAL_NUM',0,2017,'1');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show)
values('1','Product','XJ0135','IIR1953合格品','OOS-B',1,2017,'1');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show)
values('2','Product','XJ0135','IIR1953优等品','Done',2,2017,'1');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show)
values('3','Product','XJ0135','副牌胶','OOS-A',3,2017,'0');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show)
values('4','Product','XJ0135','1953合格率','OOS-B_RATE',4,2017,'1');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show)
values('5','Product','XJ0135','1953优级率','Done_RATE',5,2017,'1');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show)
values('6','Product','XJ0135','回切批次','N/A',6,2017,'1');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show)
values('7','Product','XJ0135','最终入库合格率','FINAL_RATE',7,2017,'1');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show,data_source)
values('8','Product','XJ0135','回切合格率','ROLL_BACK_RATE',7,2017,'1','1');

update JBXJ_RATE_brand set sort=8 where id='7';

update JBXJ_RATE_BRAND set data_source='0' where id in ('0','1','2','3','4','5');
update JBXJ_RATE_BRAND set data_source='1' where id in ('6','7')

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show,data_source)
values('11','Product','XJ0111','总批次','TOTAL_NUM',1,2017,'1','0');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show,data_source)
values('12','Product','XJ0111','BIIR2435','OOS-B',2,2017,'0','0');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show,data_source)
values('13','Product','XJ0111','BIIR2835H','OOS-B',3,2017,'0','0');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show,data_source)
values('14','Product','XJ0111','BIIR2430','OOS-B',4,2017,'0','0');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show,data_source)
values('15','Product','XJ0111','BIIR2835','OOS-B',5,2017,'0','0');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show,data_source)
values('16','Product','XJ0111','开工胶','OOS-B',6,2017,'0','0');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show,data_source)
values('17','Product','XJ0111','BIIR2127','OOS-B',7,2017,'0','0');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show,data_source)
values('18','Product','XJ0111','BIIR2827','OOS-B',8,2017,'0','0');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show,data_source)
values('19','Product','XJ0111','BIIR2835F','OOS-B',9,2017,'0','0');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show,data_source)
values('20','Product','XJ0111','BIIR2828','OOS-B',10,2017,'0','0');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show,data_source)
values('21','Product','XJ0111','合格批次','OOS-B_NUM',11,2017,'1','0');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show,data_source)
values('22','Product','XJ0111','合格率','OOS-B_RATE',12,2017,'1','0');

insert into JBXJ_RATE_brand(ID,MAT_TYPE,MATCODE,BRAND,GRADE,SORT,YEAR,is_show,data_source)
values('23','Product','XJ0111','副牌胶','OOS-A',13,2017,'0','0');


create table JBXJ_RATE_Detail(
	id varchar2(32) not null primary key,
    report_id varchar2(32) not null,
    brand varchar2(60) not null,
    total_num number(8,2) not null,
    grade varchar2(20),
    data_source varchar2(10)
);

comment on column JBXJ_RATE_DETAIL.data_source is '数据来源';

create table JBXJ_MONITOR_DETAIL(
    id varchar2(32) not null primary key,
    report_id varchar2(32) not null,
    matcode varchar2(32) not null,
    testcode number(8,0) not null,
    analyte varchar2(60) not null,
    sinonym varchar2(60) not null,
    done_num number(8,0) not null,
    oosB_num number(8,0) not null,
    oosa_num number(8,0) not null,
    total_num number(8,0) not null,
    sort number(8,0)
);


create table JBXJ_PROCESS_report(
    id varchar2(32) not null primary key,
    report_type varchar2(10) not null,
    year int,
    month int,
    start_time varchar2(24) not null,
    end_time varchar2(24) not null,
    mat_type varchar2(20) not null,
    matcode varchar2(20),
    tj_date varchar2(24) not null,
    total_num int not null,
    status varchar2(4)
);

create table JBXJ_PROCESS_MONITOR_DETAIL(
    id varchar2(32) not null primary key,
    report_id varchar2(32) not null,
    matcode varchar2(32) not null,
    testcode number(8,0) not null,
    analyte varchar2(60) not null,
    sinonym varchar2(60),
    done_num number(8,0) not null,
    oosB_num number(8,0) not null,
    oosa_num number(8,0) not null,
    total_num number(8,0) not null,
    sort number(8,0),
    area varchar2(60) not null
);