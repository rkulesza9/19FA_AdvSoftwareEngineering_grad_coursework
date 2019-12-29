create function fv2_change(ccompany varchar(10), adate date) returns double DETERMINISTIC
begin
  declare bdate date;
  declare changeee double;
  declare ccid int;
  set ccid = (select f_companyID(ccompany));
  set bdate = (select max(date) from v2_TRADING_DAYS where date < adate);
  set changeee = (select a.close-b.close from v2_TRADING_DAYS a, v2_TRADING_DAYS b where a.date=adate and b.date=bdate and a.cid=ccid and b.cid=ccid);

  return changeee;
end
