create function fv2_changePercent(ccompany varchar(10), adate date) returns double deterministic
begin
  declare bdate date;
  declare changeee double;
  declare cp double;
  declare ccid int;
  set ccid = (select f_companyID(ccompany));
  set bdate = (select max(date) from v2_TRADING_DAYS where date < adate);
  set changeee = (select a.close-b.close from v2_TRADING_DAYS a, v2_TRADING_DAYS b where a.date=adate and b.date=bdate and a.cid=ccid and b.cid=ccid);
  set cp = 100*changeee/(select close from v2_TRADING_DAYS where cid=ccid and date=bdate);

  return cp;
end
