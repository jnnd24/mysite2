drop table board;
drop sequence seq_board_no;

--���̺� �� ������ ����
create table board(
    no          number,
    title       varchar2(500)   not null,
    content     varchar2(4000),
    hit         number,
    reg_date    date            not null,
    user_no     number          not null,
    PRIMARY KEY (no),
    CONSTRAINT board_fk FOREIGN key (user_no)
    REFERENCES users(no)
);

create sequence seq_board_no
increment by 1
start with 1
nocache;
--//

insert into board
values (
    seq_board_no.nextval,
    '�ȳ��ϼ���6',
    '�ݰ����ϴ�. �׽�Ʈ�����Դϴ�6.',
    0,
    sysdate,
    2   
);


select no
        ,title
        ,content
        ,hit
        ,to_char(reg_date, 'YY-MM-DD HH24:MI') regdate
        ,user_no
from board
order by reg_date asc;

--����
select  b.no
        ,b.title
        ,b.content
        ,b.hit
        ,to_char(b.reg_date, 'YY-MM-DD HH24:MI') regdate
        ,b.user_no
        ,u.name
from board b, users u
where b.user_no = u.no
order by reg_date asc;

delete board
where no = 2;


select no ,name ,password ,content ,reg_date
from guestbook
where no = 3;


--��ȸ������
update board
set hit = hit + 1
where no = 3;

--����
update board
set title = '�����ϼ���'
,content = '���������Դϴ�'
where no = 3;



