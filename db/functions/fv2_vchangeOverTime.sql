create function fv2_vchangeOverTime(ccompany varchar(10), adate date, bdate date) returns double DETERMINISTIC
begin
declare changeee double;
declare cp double;
declare ccid int;
set ccid = (select f_companyID(ccompany));
set changeee = (select a.volume-b.volume from v2_TRADING_DAYS a, v2_TRADING_DAYS b where a.date=adate and b.date=bdate and a.cid=ccid and b.cid=ccid);
set cp = changeee/(select volume from v2_TRADING_DAYS where cid=ccid and date=bdate);

return cp;
end
