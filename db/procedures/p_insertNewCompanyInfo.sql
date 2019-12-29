-- set @str="";select @str := concat(@str,", x_",column_name," ",column_type) from columns where table_name="v2_COMPANY_INFO";select @str;
delimiter $$

create procedure p_insertNewCompanyInfo(x_symbol varchar(255), x_companyName varchar(255), x_exchange varchar(255), x_industry varchar(255), x_website varchar(255), x_description varchar(515), x_ceo varchar(255), x_securityName varchar(255), x_issueType varchar(255), x_sector varchar(255), x_primarySicCode int(11), x_employees int(11), x_address varchar(255), x_address2 varchar(255), x_state varchar(255), x_city varchar(255), x_zip varchar(255), x_country varchar(255), x_phone varchar(255))
begin
-- get x_cid
  declare x_cid int;
  set x_cid = (select cid from v2_COMPANY where company=x_symbol);

-- insert statement
  insert into v2_COMPANY_INFO
    (
      cid,
      companyName,
      exchange,
      industry,
      website,
      description,
      ceo,
      securityName,
      issueType,
      sector,
      primarySicCode,
      employees,
      address,
      address2,
      state,
      city,
      zip,
      country,
      phone
    )
    values
    (
      x_cid,
      x_companyName,
      x_exchange,
      x_industry,
      x_website,
      x_description,
      x_ceo,
      x_securityName,
      x_issueType,
      x_sector,
      x_primarySicCode,
      x_employees,
      x_address,
      x_address2,
      x_state,
      x_city,
      x_zip,
      x_country,
      x_phone
    );
end $$

delimiter ;

-- insert tags
delimiter $$

create procedure p_addCompanyTag(x_symbol varchar(255), x_tag varchar(255))
  begin
    declare x_cid int;
    declare tag_exists boolean;
    declare x_tagid int;
    declare tagged_exists boolean;

    set x_cid = (select cid from v2_COMPANY where company=x_symbol);
    set tag_exists = (select x_tag in (select tag from v2_TAG));

    if not tag_exists then
      insert into v2_TAG (tag) values(x_tag);
    end if;

    set x_tagid = (select tid from v2_TAG where tag=x_tag);
    create temporary table company_tags as select c.tag from v2_TAGGED b, v2_TAG c where c.tid=b.tid and b.cid=x_cid;

    set tagged_exists = (x_tag in (select tag from company_tags));
    drop table company_tags;

    if not tagged_exists then
      insert into v2_TAGGED (cid,tid) values (x_cid,x_tagid);
    end if;

  end $$

  delimiter ;
