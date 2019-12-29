create function f_vchange(ccompany varchar(10),adate date) returns double deterministic

begin
  declare bdate date;
  declare changeee double;
  set bdate = (select max(date) from TRADING_DAYS where date < adate);
  set changeee = (select a.volume-b.volume from TRADING_DAYS a, TRADING_DAYS b where a.date=adate and b.date=bdate and a.company=ccompany and b.company=ccompany);

  return changeee;
end
