create procedure p_TRADING_DAYS_W_CHANGE(ccompany varchar(10), start date, end date)
BEGIN

select *,
  0 as 'change',
  0 as 'changePercent',
  0 as 'changeOverTime',
  0 as 'volumeChange',
  0 as 'volumeChangePercent',
  0 as 'volumeChangeOverTime'

from TRADING_DAYS

where date=start AND
	  company=ccompany

union

SELECT *,
  f_change(company,date) as 'change',
  f_changePercent(company,date) as 'changePercent',
  f_changeOverTime(company,date,start) as 'changeOverTime',
  f_vchange(company,date) as 'volumeChange',
  f_vchangePercent(company,date) as 'volumeChangePercent',
  f_vchangeOverTime(company,date,start) as 'volumeChangeOverTime'

from TRADING_DAYS

where date between DATE_ADD(start,INTERVAL 1 DAY) and end and
      company=ccompany;

END
