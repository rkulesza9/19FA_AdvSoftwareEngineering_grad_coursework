create function f_vchangeOverTime(ccompany varchar(10), adate date, bdate date) returns double DETERMINISTIC
begin
declare changeee double;
declare cp double;
set changeee = (select a.volume-b.volume from TRADING_DAYS a, TRADING_DAYS b where a.date=adate and b.date=bdate and a.company=ccompany and b.company=ccompany);
set cp = changeee/(select volume from TRADING_DAYS where company=ccompany and date=bdate);

return cp;
end
