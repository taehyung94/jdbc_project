
exec board_category_package.make_board_category(1,'»ùÇÃ');
select * from board_category;
select * from group_enroll;
select * from groups;
select * from member;
select * from board;
exec groups_enroll_package.signup_group(2,4);
exec groups_enroll_package.signout_group(2,4);
exec board_package.write_board(1, 4, 4, 'test', 'test');
delete from member where id = 142;
insert into group_enroll values(9,1,'21/03/31');
select * from group_enroll;
select * from groups;
select * from member;
exec member_package.member_signup('bobo10', 'bobo', 'test');
exec member_package.member_information_change(21 ,'test2', 'test2');
select * from board_category;
select * from groups;
exec group_package.make_group(4, 'testgroup', '1112');

select * from groups_manage_view;

create or replace view groups_manage_view 
as 
select 
    g.id group_id, 
    g.name group_name,
    g.member_cnt group_member_cnt,
    m.id group_host_id, 
    m.name group_host_name, 
    bc.board_count groups_total_board_cnt  
from groups g 
join 
member m 
on g.host_member_id = m.id 
join board_category bc 
on g.id = bc.groups_id;

select * from groups_manage_view;
select * from group_enroll;
function member_get_login_data(p_email_id member.email_id%type) return member_email_and_pw;


select member_package.member_email_id_check('test@test.test') from dual;


exec group_package.update_group(43, 22, 'helloworld2');

select * from groups;

exec group_package.delete_group(101, 61);
select* from groups;
exec groups_enroll_package.signout_group(102,101);
exec groups_enroll_package.signup_group(121,101);
select * from member;
exec member_package.member_signup('tt','t','t');
exec group_package.make_group(102,'t','111');
select * from board_category;
select* from group_enroll;
exec board_category_package.make_board_category(101,'test');
select * from board_category;
select * from board;
set serveroutput on;
exec board_package.delete_board(1, 121, 101, '¤»zz¤»zz', '111');
exec board_package.delete_board(121, 2, 101);
select * from board;
select board_package.read_board_list_with_paging(101, 41, 1) from dual;
select * from group_enroll;


exec board_package.read_board_list_with_searching_and_paging(101,41,1,'content','its test');
select board_package.read_board_list_with_searching_and_paging(101,41,1,'content','its test') from dual;
exec board_package.test_dynamic_query(101,41,1,'content','its');


create table testtable(
    a number primary key
);

create view testview as select * from testtable;

insert into testtable values(1);

select * from testview;

select * from testtable;

select * from groups;

select groups_enroll_package.get_group_enroll_list_within_week(1) from dual;

with week_date(day) as (
    select sysdate - 6 as day
    from dual
    union all
    select day + 1 from week_date where day < sysdate
) select distinct day, decode(enroll_date, null, 0, count(*) over (partition by day)) from week_date left join group_enroll on to_date(day,'YY/MM/DD') = to_date(enroll_date, 'YY/MM/DD') and group_enroll.groups_id=1 order by day desc;
