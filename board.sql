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
    '�ȳ��ϼ���2',
    '�ݰ����ϴ�. �׽�Ʈ�����Դϴ�2.',
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

delete board
where no = 2;





