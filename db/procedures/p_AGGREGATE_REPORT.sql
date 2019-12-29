create procedure p_AGGREGATE_REPORT(ccompany varchar(10), date_start date, date_end date)
  BEGIN
    declare median double;
    declare rowID int;

    -- create temporary table (id, close)
    -- use cursor on sorted select to

    select min(close) as 'min',
           max(close) as 'max',
           avg(close) as 'avg',
           f_median(ccompany,date_start,date_end) as 'median'
    from TRADING_DAYS
    where company=ccompany and
          date between date_start and date_end;
  END
