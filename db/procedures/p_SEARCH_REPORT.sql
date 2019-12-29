delimiter $$

create procedure pv2_SEARCH_REPORT (symbol varchar(255))
begin
  -- v2_SEARCH and v2_SEARCH_TYPE
  declare num_searches int;
  declare num_searches_percent float default 0;
  declare last_searched_date date;
  declare avg_searches_per_week float default 0;
  declare avg_searches_per_month float default 0;

  create temporary table temp_SEARCHES as
    select a.date, substring(CAST(a.date as char),1,4) as year, substring(CAST(a.date as char),6,2) as month, b.full_name, c.company
      from v2_SEARCH a, v2_SEARCH_TYPES b, v2_COMPANY c
      where a.company_id=c.cid and a.type_id=b.id;

  -- num searches
  set num_searches = (select count(*) from temp_SEARCHES where company=symbol);

  -- num searches %
  set num_searches_percent = 100*num_searches / (select count(*) from temp_SEARCHES);

  -- last searched date
  set last_searched_date = (select max(date) from temp_SEARCHES where company=symbol);

  -- avg searches per month
  set avg_searches_per_month = num_searches/(select count(*) from (select distinct(CONCAT(month,year)) from temp_SEARCHES) t);

  -- avg searches per week
  set avg_searches_per_week = avg_searches_per_month / 4;

  drop table temp_SEARCHES;
  select last_searched_date, num_searches, num_searches_percent, avg_searches_per_week, avg_searches_per_month;

end $$

delimiter ;
