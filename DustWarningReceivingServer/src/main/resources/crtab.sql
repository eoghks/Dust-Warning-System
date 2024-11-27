create sequence member_id_seq;
--drop table member
create table member(
	memberId int8 primary key default nextval('member_id_seq'),
	loginId  Varchar(64) not null unique,
	password Varchar(64) not null
);

insert into member values(1, 'admin', '$2a$10$REIcDM4o69q5KDab5FpIyOhiLIOm.EMuPFlFCApNptpvDdkS.QJjy');

select * from member;