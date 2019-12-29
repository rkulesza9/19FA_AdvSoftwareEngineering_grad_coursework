create procedure pv2_AGGREGATE_REPORT(ccompany varchar(10), date_start date, date_end date)
  BEGIN
    declare median double;
    declare rowID int;
    declare ccid int;
    set ccid = (select f_companyID(ccompany));

    select min(close) as 'min',
           max(close) as 'max',
           avg(close) as 'avg',
           fv2_median(ccompany,date_start,date_end) as 'median'
    from v2_TRADING_DAYS
    where cid=ccid and
          date between date_start and date_end;
  END
