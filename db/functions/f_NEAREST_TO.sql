-- returns the date existing in TRADING_DAYS nearest to the parameter
DROP FUNCTION f_NEAREST_TO;
delimiter $$

create function f_NEAREST_TO(x_date date) returns date DETERMINISTIC
BEGIN
  declare x_date_id int;
  declare before_id int;
  declare after_id int;
  declare before_date date;
  declare after_date date;

    -- if x_date exists, return it
    if x_date in (select date from v2_TRADING_DAYS) then
      return x_date;
    end if;

    -- if x_date does not exist, return it
    create temporary table if not exists SORTED_DATES
      (
        id int auto_increment primary key,
        date date
      );

      if (Select count(*) from SORTED_DATES) > 0 then
        delete from SORTED_DATES;
      end if;

      insert into SORTED_DATES (date)
        (select * from (select distinct(date) from v2_TRADING_DAYS
          UNION
          select x_date) as t
          order by date desc);

      -- get date id
      set x_date_id = (select id from SORTED_DATES where date=x_date);
      set before_date = (select date from SORTED_DATES where id=x_date_id-1);
      set after_date = (select date from SORTED_DATES where id=x_date_id+1);

      if before_date is null and after_date is null then
        return "0000-00-00";
      end if;
      if before_date is null then
        return after_date;
      end if;
      if after_date is null then
        return before_date;
      end if;

      -- compare distance between date,before and date,after
      if (select datediff(after_date,x_date) > datediff(x_date,before_date)) then
        return after_date;
      else
        return before_date;
      end if;
end $$

delimiter ;
