drop table board;
drop sequence seq_board_no;

--테이블 및 시퀀스 생성
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
    '안녕하세요2',
    '반갑습니다. 테스트내용입니다2.',
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





