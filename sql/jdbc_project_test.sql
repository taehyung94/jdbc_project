select * from member;
select * from board;
exec board_package.write_board(142, 121, 61, 'test', 'test');
delete from member where id = 142;
exec member_package.member_signup('bobo4', 'bobo', 'test');
exec member_package.member_information_change(21 ,'test2', 'test2');
select * from board_category;
select * from groups;
exec group_package.make_group(162, 'testgroup', '0112');

select * from groups;

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