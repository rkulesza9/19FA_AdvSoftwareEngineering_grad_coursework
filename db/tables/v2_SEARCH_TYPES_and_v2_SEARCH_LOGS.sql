create table v2_SEARCH_TYPES
  (
    id int auto_increment primary key,
    short_name char(5) not null,
    full_name varchar(25) not null
  );

  insert into v2_SEARCH_TYPES
    (short_name, full_name) values
    ("30DAY","30 Day Report"),
    ("AGRGT","Aggregate Report"),
    ("CINFO","Company Information"),
    ("SRCHP","Search Popularity Report");

CREATE TABLE v2_SEARCH
(
  id int auto_increment primary key,
  date date,
  type_id int,
  company_id int
);
