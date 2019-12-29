create table v2_COMPANY_INFO
  (

    -- primary key
    ciid int not null auto_increment primary key,

    -- data fields
    cid int not null,
    companyName varchar(255),
    exchange varchar(255),
    industry varchar(255),
    website varchar(255),
    description varchar(255),
    ceo varchar(255),
    securityName varchar(255),
    issueType varchar(255),
    sector varchar(255),
    primarySicCode int,
    employees int,
    address varchar(255),
    address2 varchar(255),
    state varchar(255),
    city varchar(255),
    zip varchar(255),
    country varchar(255),
    phone varchar(255),

    -- foreign key
    foreign key(cid) references v2_COMPANY_INFO(cid)

  );
