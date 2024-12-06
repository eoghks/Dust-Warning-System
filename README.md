# 요구 사항

1. 아래 정보를 활용하여 미세먼지(PM-10) 및 초미세먼지(PM-2.5) 데이터를 사용하여 경보 단계를 발령한다.
    - 단계별 발령 기준
        
        
        | 물질 | 경보 단계 | 발령 기준 |
        | --- | --- | --- |
        | 미세먼지(PM-10) | 주의보 | 시간당 평균농도 150㎍/㎥ 이상 2시간 이상 지속 |
        | 미세먼지(PM-10) | 경보 | 시간당 평균농도 300㎍/㎥ 이상 2시간 이상 지속 |
        | 초미세먼지(PM-2.5) | 주의보 | 시간당 평균농도 75㎍/㎥ 이상 2시간 이상 지속 |
        | 초미세먼지(PM-2.5) | 경보 | 시간당 평균농도 150㎍/㎥ 이상 2시간 이상 지속 |
    - 경보 단계 등급표
        
        
        | 등급 | 경보 단계 | 설명 |
        | --- | --- | --- |
        | 1 | 초미세먼지 경보 | 가장 심각한 상태, 건강에 매우 해로움 |
        | 2 | 미세먼지 경보 | 건강에 매우 해로울 수 있음 |
        | 3 | 초미세먼지 주의보 | 건강에 해로울 수 있음 |
        | 4 | 미세먼지 주의보 | 건강에 약간 해로울 수 있음 |
2. 경보 발령 기준이 충족되면 측정소(구별), 경보 단계, 발령 시간을 기록합니다.
    
    - 이때 심각도에 따라 등급을 부여하며, 가장 높은 순위의 등급(1>2>3>4)으로 발령합니다.
    
    - 이 경보 발령 정보를 데이터베이스에 저장합니다.
    
3. 측정된 데이터가 없는 경우 미세먼지, 초미세먼지 값은 0으로 처리합니다.
    
    - 이는 측정소 점검이 있던 날로 간주하고, 측정소 별 점검 내역을 데이터베이스에 저장합니다.

4. 경보 발령 정보를 전송하는 알람 시스템 구현
    - TCP 소켓 프로그래밍 또는 웹 훅을 사용하여 개발
    - 발생한 시간 순서대로 전송
    - 경보 발령을 수신하는 주체와 발신하는 주체 모두 필요

---

# 제약 사항

- Java 언어를 사용하여 개발하세요.
- 제공된 데이터 형식(CSV 또는 JSON) 중 하나를 선택하여 사용하세요.
- RDBMS(Oracle, PostgreSQL, Mysql, MariaDB 등..)를 사용하여 데이터베이스를 구축하세요.
- DB 선택은 자유롭게 결정할 수 있습니다.
- 데이터베이스 설계는 자유롭게 진행하세요.
- 개발 과정에서 Git(로컬)을 사용하여 코드 버전 관리를 하는 것을 권장합니다.
- 설계한 데이터베이스의 생성 스크립트(SQL)를 제출하거나 프로그램 실행 시 자동으로 생성되도록 개발하세요.


---

# Dust-Warning-System
미세먼지와 초미세먼지 경고 발령 시스템으로 점검 이력과 경보 이력을 DB에 저장하고,  
경보 이력을 미세먼지 경보 수신 서버(Dust-Warning-Receiving-System)로 전송할 수 있다.

--- 

$\bf \large 개발\ 환경$

- Eclipse 2022-12R
- Java 17
- PostgreSQL 15.7

---

$\bf \large 기술\ 스택$

- Java
- Mybatis
- PostgreSQL

---

$\bf \large Database\ 명세$

- WarningHistory(경보 이력 저장 테이블)
    
    
    | Column | Type | 설명 | 비고 |
    | --- | --- | --- | --- |
    | districtName | VarChar(16) | 측정소 | primarykey |
    | date | Timestamp | 발령시간 | primarykey |
    | rate | Integer | 등급 | not null |
- InspectionHistory(점검 이력 저장 테이블)
    
    
    | Column | Type | 설명 | 비고 |
    | --- | --- | --- | --- |
    | districtName | VarChar(16) | 측정소 | primarykey |
    | date | Timestamp | 발령시간 | primarykey |
   
---

$\bf \large Database\  crtab$

- 유저 및 데이터베이스 생성

```sql
create user dust_warning_system password 'dust_warning_system' SUPERUSER;

create database dust_warning_system  with owner dust_warning_system encoding 'UTF8';
```

- 테이블 생성(dust_warning_system DB에 생성!!)
```sql
--drop table warningHistory
create table warningHistory(
	districtName VARCHAR(16) NOT NULL,
    date TIMESTAMP NOT NULL,
    rate INTEGER NOT NULL,
    PRIMARY KEY (districtName, date)
);

--drop table inspectionHistory
create table inspectionHistory(
	districtName Varchar(16) NOT NULL,
	date TIMESTAMP NOT NULL,
	PRIMARY KEY (districtName, date)
);
```

---

$\bf \large 실행시\ 주의\ 사항$  

1. lib폴더 밑에 존재하는 다음 jar파일을 추가한다.
- jackson-core-2.15.2.jar
- jackson-databind-2.15.2.jar
- jackson-annotations-2.15.2.jar
- jackson-datatype-jsr310-2.15.2.jar
- mybatis-3.5.6.jar
- postgresql-42.7.3.jar
2. DB 테이블을 resource 밑에 crtab 파일을 이용하여 생성한다.
- 유저 및 데이터베이스를 생성하고, 이후 새롭게 생성한 유저 및 데이터베이스에 접속하여 테이블을 생성한다.
3. 한글 깨짐을 방지하기 위해 Project의 encoding은 UTF8로 설정한다.
4. 기존에 생성한 이력은 실행시 삭제되고 새롭게 이력을 저장한다.
5. 응답을 수신하는 서버와 연결이 원할하지 않아 JWT 토큰을 발급 받지 못하거나, 이력 전송에 실패한 경우 프로그램은 종료되지 않고 점검 및 경보 이력을 DB에 저장한다.
6. 그외의 오류 발생시 프로그램이 종료된다.

---
# Dust-Warning-Receiving-System
미세먼지와 초미세먼지 경고 발령 시스템(Dust-Warning-System)에서 송신한 경보 메시지를 수신하여 log에 기록하는 시스템

--- 

$\bf \large 개발\ 환경$

- Eclipse 2022-12R
- Java 17
- PostgreSQL 15.7
- Gradle 7.5

---

$\bf \large 기술\ 스택$

- Java
- Spring Boot
- Spring Sececurity
- JPA
- PostgreSQL
- JWT

---

$\bf \large Database\ 명세$

- Member
    | Column | Type | 설명 | 비고 |
    | --- | --- | --- | --- |
    | memberId | Long | 사용자 Id | primary key, Auto Increment |
    | loginId | VarChar(64) | 로그인 Id | unique, not null |
    | password | VarChar(64) | 비밀번호 | not null |


---

$\bf \large Database\  crtab$

- 유저 및 데이터베이스 생성

```sql
create user dust_warning_receive_server password 'dust_warning_receive_server' SUPERUSER;

create database dust_warning_receive_server  with owner dust_warning_receive_server encoding 'UTF8';
```

- 테이블 생성(dust_warning_receive_server DB에 생성!!)
```sql
create sequence member_id_seq;
--drop table member
create table member(
	memberId int8 primary key default nextval('member_id_seq'),
	loginId  Varchar(64) not null unique,
	password Varchar(64) not null
);

insert into member values(1, 'admin', '$2a$10$REIcDM4o69q5KDab5FpIyOhiLIOm.EMuPFlFCApNptpvDdkS.QJjy');
```

---

$\bf \large 실행시\ 주의\ 사항$  

1. DB 테이블을 resource 밑에 crtab 파일을 이용하여 생성한다.
- 유저 및 데이터베이스를 생성하고, 이후 새롭게 생성한 유저 및 데이터베이스에 접속하여 테이블을 생성한다.
2. 한글 깨짐을 방지하기 위해 encoding은 UTF8로 설정한다.
3. 경보를 받은 경우 log폴더 밑에 존재하는 log 파일에 경보 내역이 작성된다.
