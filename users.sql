drop sequence seq_users_no;
drop table users;

create table users(
    no          number,
    id          varchar2(20)    unique not null,
    password    varchar2(20)    not null,
    name        varchar2(20),
    gender      varchar2(10),
    PRIMARY KEY (no)
);

select *
from users;


create sequence seq_users_no
increment by 1
start with 1
nocache;


insert into users
values (seq_users_no.nextval, 'jnnd24', '1234', '장성찬', '남');

insert into users
values (seq_users_no.nextval, 'jiyuuun', '1234', '김지윤', '여');


select no, id, password, name, gender
from users;

select no, id, password, name, gender
from users
where id = 'aaaa'
and password = '1234';

update users
set name = '장성찬'
    , password = '1234'
    , gender = 'male'
where no = 2;



