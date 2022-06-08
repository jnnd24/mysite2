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
    '안녕하세요6',
    '반갑습니다. 테스트내용입니다6.',
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

--조인
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


--조회수증가
update board
set hit = hit + 1
where no = 3;

--수정
update board
set title = '수정하세요'
,content = '수정내용입니다'
where no = 3;



