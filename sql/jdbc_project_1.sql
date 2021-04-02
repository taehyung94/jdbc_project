-- 멤버 테이블 생성

drop table member cascade constraints;
drop table groups cascade constraints;
drop table group_enroll cascade constraints;
drop table board_category cascade constraints;
drop table board cascade constraints;


create table member(
id number,
email_id varchar2(40),
password varchar2(20),
name varchar2(20),
group_cnt number,
constraint member_email_nn check(email_id is not null),
constraint member_password_nn check(password is not null),
constraint member_name_nn check(name is not null),
constraint member_group_cnt_nn check(group_cnt is not null)
);

create index member_id_pk_idx on member(id);
create index member_email_id_unique_idx on member(email_id);
alter table member add constraint member_id_pk primary key(id);
alter table member add constraint member_email_id_unique unique(email_id);

-- 그룹 테이블

create table groups(
    id number,
    host_member_id number,
    name varchar2(20),
    participate_id varchar(4),
    constraints groups_name_nn check(name is not null),
    constraints groups_host_member_id check(host_member_id is not null),
    constraints groups_participate_id_nn check(participate_id is not null)
);

create index groups_id_pk_idx on groups(id);
create index groups_host_member_id_fk_idx on groups(host_member_id);
create index groups_participate_id_idx on groups(participate_id);

alter table groups add constraint groups_id_pk primary key(id);
alter table groups add constraint groups_host_member_id_fk foreign key (host_member_id) references member(id);
alter table groups add constraint groups_participate_id_unique unique(participate_id);


-- 그룹 인롤 테이블
create table group_enroll(
    member_id number,
    groups_id number,
    enroll_date date,
    constraints member_id_nn check (member_id is not null),
    constraints groups_id_nn check (groups_id is not null),
    constraints enroll_date_nn check (enroll_date is not null)
);

create index group_enroll_member_id_groups_id_pk_idx on group_enroll(member_id, groups_id);
create index group_enroll_member_id_fk_idx on group_enroll(member_id);
create index group_enroll_groups_id_fk_idx on group_enroll(groups_id);
alter table group_enroll add constraint group_enroll_member_id_groups_id_pk primary key(member_id, groups_id);
alter table group_enroll add constraint group_enroll_member_id_fk foreign key(member_id) references member(id);
alter table group_enroll add constraint group_enroll_groups_id_fk foreign key(groups_id) references groups(id);

create table board_category(
    id number,
    groups_id number,
    name varchar2(20),
    board_count number,
    constraints board_category_groups_id check(groups_id is not null),
    constraints board_category_name_nn check(name is not null)
);

create index board_category_id_pk_idx on board_category(id);
create index board_category_groups_id_fk_idx on board_category(groups_id);
create index board_category_groups_id_name_uniuqe_idx on board_category(groups_id,name);
alter table board_category add constraint board_category_id_pk primary key(id);
alter table board_category add constraint board_category_groups_id_fk foreign key(groups_id) references groups(id);
alter table board_category add constraint board_category_groups_id_name_unique unique(groups_id,name);

-- board 테이블 생성

create table board(
    id number,
    writer_id number,
    writer_name varchar2(20),
    groups_id number,
    board_category_id number,
    title varchar2(30),
    content varchar2(2000),
    write_date date,
    constraints board_writer_id_nn check(writer_id is not null),
    constraints board_groups_id_nn check(groups_id is not null),
    constraints board_title_nn check(title is not null),
    constraints board_content_id_nn check(content is not null)
);

create index board_id_pk_idx on board(id);
create index board_writer_id_fk_idx on board(writer_id);
create index board_groups_id_fk_idx on board(groups_id);
create index board_board_category_id_fk_idx on board(board_category_id);
create index board_title_idx on board(title) indextype is ctxsys.context;
create index board_content_idx on board(content) indextype is ctxsys.context;

alter table board add constraint board_id_pk primary key(id);
alter table board add constraint board_writer_id_fk foreign key(writer_id) references member(id) on delete set null;
alter table board add constraint board_groups_id_fk foreign key(groups_id) references groups(id);
alter table board add constraint board_board_category_id_fk foreign key(board_category_id) references board_category(id);

create sequence member_sequence;
create sequence groups_sequence;
create sequence group_enroll_sequence;
create sequence board_category_sequence;
create sequence board_sequence;