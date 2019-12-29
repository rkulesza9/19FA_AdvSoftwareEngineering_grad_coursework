create function f_median(ccompany varchar(10),date_start date, date_end date) returns double deterministic
begin

declare median double;
set median = 0;
set @rowindex = 0;

set median = (SELECT AVG(g.close)
FROM 

(SELECT @rowindex:=@rowindex + 1 AS rowindex,
 		close
 FROM TRADING_DAYS
 where company=ccompany and date between date_start and date_end
 ORDER BY close) AS g

 WHERE g.rowindex IN (FLOOR(@rowindex / 2) , CEIL(@rowindex / 2)));

 return median;

end
