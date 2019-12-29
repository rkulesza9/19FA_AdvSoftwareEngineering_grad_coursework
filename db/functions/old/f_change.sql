create function f_change(ccompany varchar(10), adate date) returns double DETERMINISTIC
begin
  declare bdate date;
  declare changeee double;
  set bdate = (select max(date) from TRADING_DAYS where date < adate);
  set changeee = (select a.close-b.close from TRADING_DAYS a, TRADING_DAYS b where a.date=adate and b.date=bdate and a.company=ccompany and b.company=ccompany);

  return changeee;
end
