-- 트리거
create or replace trigger member_update_tri
after update on member
for each row
begin
if updating('name') then
    update board
    set writer_name = :new.name
    where writer_id = :new.id;
end if;
end;
/

create or replace trigger board_dml
after insert or delete on board
for each row
begin
if inserting then
    update board_category
    set board_count = board_count + 1
    where groups_id = :new.groups_id and name != '전체' and id = :new.board_category_id;
    update board_category
    set board_count = board_count + 1
    where groups_id = :new.groups_id and name = '전체';
    
elsif deleting then
    update board_category
    set board_count = board_count - 1
    where groups_id = :old.groups_id and id = :old.board_category_id;
    update board_category
    set board_count = board_count - 1
    where groups_id = :old.groups_id and name = '전체';
end if;
end;
/

create or replace trigger groups_dml
after insert or delete on groups
for each row
begin
if inserting then
    insert into board_category values(board_category_sequence.nextval, :new.id, '전체', 0);
    insert into group_enroll values(:new.host_member_id, :new.id, sysdate);
end if;
end;
/

create or replace trigger group_enroll_dml
after insert or delete on group_enroll
for each row
begin
if inserting then
    update member set group_cnt = group_cnt + 1 where id = :new.member_id;
elsif deleting then
    update member set group_cnt = group_cnt - 1 where id = :old.member_id;
end if;

end;
/

-- plsql
create or replace package member_package
is
    function member_email_id_check(p_email_id in member.email_id%type) return member.email_id%type;
    procedure member_signup(p_email_id in member.email_id%type, p_password in member.password%type, p_name in member.name%type);
    procedure member_information_change(p_id in member.id%type ,p_name in member.name%type, p_password in member.password%type);
    procedure member_get_login_data(p_email_id member.email_id%type, v_email_id out member.email_id%type, v_password out member.password%type);
    function member_get_group_list_with_paging(p_member_id member.id%type, p_page_number number) return sys_refcursor;
    function member_get_id(p_member_email_id member.email_id%type) return number;
    function get_member_enroll_group_cnt(p_member_id member.id%type) return number;
end;
/

create or replace package body member_package
is
    function member_get_id(p_member_email_id member.email_id%type) return number
    is
        v_id number;
    begin
        select id into v_id from member where email_id = p_member_email_id;
        return v_id;
    end;
    
    
    function get_member_enroll_group_cnt(p_member_id member.id%type) return number
    is
        cnt number := 0;
    begin
        select group_cnt into cnt from member where id = p_member_id;
        return cnt;
    end;
    
    function member_email_id_check(p_email_id in member.email_id%type) return member.email_id%type --id 중복체크 프로시저
    is
    v_email_id member.email_id%type;
    begin
        select email_id into v_email_id from member where email_id = p_email_id;
        return v_email_id;
        exception 
            when no_data_found THEN
                return 'ok';
    end member_email_id_check;
    
    procedure member_signup(p_email_id in member.email_id%type, p_password in member.password%type, p_name in member.name%type) -- 회원가입 프로시저
    is
    begin
        insert into member values(member_sequence.nextval, p_email_id, p_password, p_name, 0);
        commit;
    end member_signup;
    /*procedure member_find_password(p_email_id in member.email_id%type)
    is
    begin
    end member_find_password;*/
    procedure member_information_change(p_id in member.id%type ,p_name in member.name%type, p_password in member.password%type)
    is
    begin
        update member set name = p_name, password = p_password where id = p_id;
        commit;
    end member_information_change;
    
    procedure member_get_login_data(p_email_id member.email_id%type, v_email_id out member.email_id%type, v_password out member.password%type)
    is
    begin
        select 
        email_id, password 
        into
        v_email_id, v_password
        from member
        where email_id = p_email_id;
    end member_get_login_data;
    
    function member_get_group_list_with_paging(p_member_id member.id%type, p_page_number number) return sys_refcursor
    is
        group_list_cursor sys_refcursor;
    begin
        open group_list_cursor for
            select * from (
                select * from (
                    select 
                    rownum gnum, g.id id, g.name name
                    from group_enroll ge
                    join groups g
                    on ge.groups_id = g.id 
                    where ge.member_id = p_member_id order by ge.groups_id
                    )
                where gnum > (p_page_number-1)*4
            ) where rownum <= 4;
        return group_list_cursor;
    end member_get_group_list_with_paging;
end;
/

create or replace package group_package
is
function groups_participate_id_check(p_participate_id in groups.participate_id%type) return number;
procedure make_group(p_member_id in member.id%type, p_groups_name in groups.name%type, p_participate_id in groups.participate_id%type);
procedure update_group(p_groups_id in groups.id%type, p_groups_host_id in groups.host_member_id%type, p_groups_name in groups.name%type);
procedure delete_group(p_groups_id in groups.id%type, p_groups_host_id in groups.host_member_id%type);
procedure search_group(p_groups_participate_id in groups.participate_id%type, v_groups_id out groups.id%type, v_groups_name out groups.name%type);
end;
/



create or replace package body group_package
is
    function groups_participate_id_check(p_participate_id in groups.participate_id%type) return number
    is
        res number;
    begin
        select 0 into res from groups where participate_id = p_participate_id;
        return res;
        exception
            when no_data_found then
                return 1;
    end groups_participate_id_check;

    procedure make_group(p_member_id in member.id%type, p_groups_name in groups.name%type, p_participate_id in groups.participate_id%type) -- 그룹 생성
    is
    begin
        insert into groups values(groups_sequence.nextval, p_member_id, p_groups_name, p_participate_id);
        commit;
    end make_group;
    
    procedure update_group(p_groups_id in groups.id%type, p_groups_host_id in groups.host_member_id%type, p_groups_name in groups.name%type)
    is
    begin
        update groups set name = p_groups_name where host_member_id= p_groups_host_id and id = p_groups_id;
        if sql%rowcount = 0 then
            raise_application_error(-20000, 'only owner can control');
        end if;
        commit;
    end update_group;
    
    procedure delete_group(p_groups_id in groups.id%type, p_groups_host_id in groups.host_member_id%type)
    is
    begin
        delete from groups where id = p_groups_id and host_member_id = p_groups_host_id;
        if sql%rowcount = 0 then
            raise_application_error(-20000, 'only owner can control');
        end if;
        commit;
    end delete_group;
    
    procedure search_group(p_groups_participate_id in groups.participate_id%type, v_groups_id out groups.id%type, v_groups_name out groups.name%type)
    is
    begin
        select 
        id, name 
        into 
        v_groups_id, v_groups_name
        from groups 
        where 
        participate_id = p_groups_participate_id;
    end search_group;
end;
/