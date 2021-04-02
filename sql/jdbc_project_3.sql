create or replace package groups_enroll_package -- 그룹 가입 관련 패키지
is
    procedure signup_group(p_member_id member.id%type, p_groups_id groups.id%type);
    procedure signout_group(p_member_id member.id%type, p_groups_id groups.id%type);
end;
/


create or replace package body groups_enroll_package -- 그룹 가입 관련 패키지 바디
is
    procedure signup_group(p_member_id member.id%type, p_groups_id groups.id%type)
    is
    begin
        insert into group_enroll values(p_member_id, p_groups_id, sysdate);
    end;
    
    procedure signout_group(p_member_id member.id%type, p_groups_id groups.id%type)
    is
    begin
        delete from group_enroll where member_id = p_member_id and groups_id = p_groups_id;
    end;
end;
/

create or replace package board_category_package
is
    procedure make_board_category(p_groups_id in board_category.groups_id%type, p_name in board_category.name%type);
    procedure get_board_category_list(p_groups_id board_category.groups_id%type, v_board_category_list out sys_refcursor);
end;
/

create or replace package body board_category_package
is
    procedure make_board_category(p_groups_id in board_category.groups_id%type, p_name in board_category.name%type)
    is
    begin
        insert into board_category values(board_category_sequence.nextval, p_groups_id, p_name, 0);
    end make_board_category;
    
    procedure get_board_category_list(p_groups_id board_category.groups_id%type, v_board_category_list out sys_refcursor)
    is
    begin
        open v_board_category_list 
        for
        select * from board_category where groups_id = p_groups_id;
    end get_board_category_list;
end;
/

create or replace package board_package
is
    function board_owner_check(p_board_id board.id%type, p_member_id board.writer_id%type, p_groups_id board.groups_id%type) return number;
    procedure write_board(p_member_id board.writer_id%type, 
                          p_groups_id board.groups_id%type, 
                          p_board_category_id board.board_category_id%type,
                          p_board_title board.title%type,
                          p_board_content board.content%type);
    procedure edit_board(p_board_id board.id%type,
                          p_member_id board.writer_id%type, 
                          p_groups_id board.groups_id%type, 
                          p_board_title board.title%type,
                          p_board_content board.content%type);
    procedure delete_board(p_member_id board.writer_id%type, p_board_id board.id%type, p_groups_id board.groups_id%type);
procedure read_board_detail(p_board_id board.id%type, 
                                v_board_id out board.id%type,
                                v_writer_id out board.writer_id%type,
                                v_writer_name out board.writer_name%type,
                                v_groups_id out board.groups_id%type,
                                v_board_category_id out board.board_category_id%type,
                                v_title out board.title%type,
                                v_content out board.content%type,
                                v_write_date out board.write_date%type
                                );
    function read_board_list_with_paging(p_groups_id board.groups_id%type, p_board_category_id board.board_category_id%type, p_page_number number) return sys_refcursor;
    
    function read_board_list_with_searching_and_paging(p_groups_id board.groups_id%type, 
                                                       p_board_category_id board.board_category_id%type, 
                                                       p_page_number number, 
                                                       p_kind varchar2, 
                                                       p_keyword varchar2) return sys_refcursor;
end;
/

create or replace package body board_package
is
    function board_owner_check(p_board_id board.id%type, p_member_id board.writer_id%type, p_groups_id board.groups_id%type) return number
    is
        res number;
        v_writer_id board.writer_id%type;
        v_host_id board.writer_id%type;
    begin
        select writer_id into v_writer_id from board where id = p_board_id;
        select host_member_id into v_host_id from groups where id = p_groups_id;
        if p_member_id in (v_writer_id, v_host_id) then
            res := 1;
        else
            res := 0;
        end if;
        return res;
        exception when others then
            return 0;
    end;

    function get_board_cnt_with_category(p_groups_id board.groups_id%type, p_board_category_id board.board_category_id%type) return number
    is
        cnt number := 0;
    begin
        select board_count into cnt from board_category where id = p_board_category_id and groups_id = p_groups_id;
        return cnt;
    end;
    
    function get_groups_host(p_groups_id groups.host_member_id%type) return number
    is
        host_id number := 0;
    begin
        select host_member_id into host_id from groups where id = p_groups_id;
        return host_id;
    end;
       
    procedure write_board(p_member_id board.writer_id%type, 
                          p_groups_id board.groups_id%type, 
                          p_board_category_id board.board_category_id%type,
                          p_board_title board.title%type,
                          p_board_content board.content%type)
    is
        member_name member.name%type;
    begin
        select name into member_name from member where id = p_member_id;
        insert into board values(board_sequence.nextval, p_member_id, member_name, p_groups_id, p_board_category_id, p_board_title, p_board_content, sysdate);
    end write_board;
    
    procedure edit_board(p_board_id board.id%type,
                          p_member_id board.writer_id%type, 
                          p_groups_id board.groups_id%type, 
                          p_board_title board.title%type,
                          p_board_content board.content%type)
    is
         flag_number number;
    begin
        flag_number := board_owner_check(p_board_id, p_member_id, p_groups_id);
        if flag_number = 0 then
            raise_application_error(-20000, 'only owner can control');
        end if;
        update 
        board 
        set title = p_board_title, content = p_board_content
        where id = p_board_id;
    end edit_board;
    
    procedure delete_board(p_member_id board.writer_id%type, p_board_id board.id%type, p_groups_id board.groups_id%type)
    is
        flag_number number;
    begin
        flag_number := board_owner_check(p_board_id, p_member_id, p_groups_id);
        if flag_number = 0 then
            raise_application_error(-20000, 'only owner can control');
        end if;
        delete 
        from 
        board 
        where 
        id = p_board_id;
    end delete_board;
    
    procedure read_board_detail(p_board_id board.id%type, 
                                v_board_id out board.id%type,
                                v_writer_id out board.writer_id%type,
                                v_writer_name out board.writer_name%type,
                                v_groups_id out board.groups_id%type,
                                v_board_category_id out board.board_category_id%type,
                                v_title out board.title%type,
                                v_content out board.content%type,
                                v_write_date out board.write_date%type
                                )
    is
    begin
        select
        id, writer_id, writer_name, groups_id, board_category_id, title, content, write_date
        into v_board_id, v_writer_id, v_writer_name, v_groups_id, v_board_category_id, v_title, v_content, v_write_date
        from board 
        where id = p_board_id;
    end read_board_detail;
    
    function read_board_list_with_paging(p_groups_id board.groups_id%type, p_board_category_id board.board_category_id%type, p_page_number number) return sys_refcursor
    is
        board_list_cursor sys_refcursor;
    begin
        open board_list_cursor for
            select bnum, id, title, writer_name, write_date from (
                select * from (
                    select 
                        rownum bnum, board.*
                    from board 
                    where 
                    groups_id = p_groups_id and board_category_id = p_board_category_id
                    order by id
                    )
                where bnum > (p_page_number-1)*5
            ) where rownum <= 5 order by bnum;
        return board_list_cursor;
    end read_board_list_with_paging;
    
    function read_board_list_with_searching_and_paging(p_groups_id board.groups_id%type, 
                                                       p_board_category_id board.board_category_id%type, 
                                                       p_page_number number, 
                                                       p_kind varchar2, 
                                                       p_keyword varchar2) return sys_refcursor
    is
    board_list_cursor sys_refcursor;
    q1 varchar2(1000) := 'select bnum, id, title, writer_name, write_date from (
                select * from (
                    select 
                        rownum bnum, board.*
                    from board 
                    where 
                    groups_id = :p_groups_id and board_category_id = :p_board_category_id';
    q2 varchar2(100) := ' order by id)
	where bnum > (:p_page_number-1)*5) 
	where rownum <= 5 order by bnum';
    begin
        q1 := q1 || ' and ' || p_kind || ' like ' || '''%' || p_keyword || '%''' || q2;
        open board_list_cursor for q1 using p_groups_id, p_board_category_id, p_page_number;
        return board_list_cursor;
    end read_board_list_with_searching_and_paging;
end;
/