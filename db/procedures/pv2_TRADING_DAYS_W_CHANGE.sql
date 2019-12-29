drop PROCEDURE pv2_TRADING_DAYS_W_CHANGE;
delimiter $$
create procedure pv2_TRADING_DAYS_W_CHANGE(ccompany varchar(10), start date, end date)
BEGIN

declare ccid int;
set ccid = (select f_companyID(ccompany));

select *,
  0 as 'change',
  0 as 'changePercent',
  0 as 'changeOverTime',
  0 as 'volumeChange',
  0 as 'volumeChangePercent',
  0 as 'volumeChangeOverTime'

from v2_TRADING_DAYS

where date=start AND
	  cid=ccid

union

SELECT *,
  fv2_change(f_companySTR(cid),f_NEAREST_TO(date)) as 'change',
  fv2_changePercent(f_companySTR(cid),f_NEAREST_TO(date)) as 'changePercent',
  fv2_changeOverTime(f_companySTR(cid),f_NEAREST_TO(date),f_NEAREST_TO(start)) as 'changeOverTime',
  fv2_vchange(f_companySTR(cid),f_NEAREST_TO(date)) as 'volumeChange',
  fv2_vchangePercent(f_companySTR(cid),f_NEAREST_TO(date)) as 'volumeChangePercent',
  fv2_vchangeOverTime(f_companySTR(cid),f_NEAREST_TO(date),f_NEAREST_TO(start)) as 'volumeChangeOverTime'

from v2_TRADING_DAYS

where date between DATE_ADD(f_NEAREST_TO(start),INTERVAL 1 DAY) and f_NEAREST_TO(end) and
      cid=ccid;

END $$

delimiter ;
