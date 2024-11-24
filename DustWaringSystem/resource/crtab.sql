create user dust_warning_system password 'dust_warning_system' SUPERUSER;

create database dust_warning_system  with owner dust_warning_system encoding 'UTF8';

-- 생성한 database에 접속하여 실행하십시오.
--drop table warningHistory;
create table warningHistory(
	districtName VARCHAR(16) NOT NULL,
    date TIMESTAMP NOT NULL,
    rate INTEGER NOT NULL,
    PRIMARY KEY (districtName, date)
);

--drop table inspectionHistory;
create table inspectionHistory(
	districtName Varchar(16) NOT NULL,
	date TIMESTAMP NOT NULL,
	PRIMARY KEY (districtName, date)
);
