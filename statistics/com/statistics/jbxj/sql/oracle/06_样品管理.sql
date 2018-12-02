--样品类型
create table sample_type(
    id integer not null primary key,
    parent_id integer not null,
    type_name varchar2(30) not null,
    sort integer not null,
    dept varchar2(30) not null
);

select max(id) from sample_type;