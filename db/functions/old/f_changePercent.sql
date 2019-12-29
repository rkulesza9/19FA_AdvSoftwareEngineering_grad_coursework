create function f_changePercent(ccompany varchar(10), adate date) returns double deterministic
begin
  declare bdate date;
  declare changeee double;
  declare cp double;
  set bdate = (select max(date) from TRADING_DAYS where date < adate);
  set changeee = (select a.close-b.close from TRADING_DAYS a, TRADING_DAYS b where a.date=adate and b.date=bdate and a.company=ccompany and b.company=ccompany);
  set cp = 100*changeee/(select close from TRADING_DAYS where company=ccompany and date=bdate);

  return cp;
end
