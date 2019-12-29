create function f_companySTR(ccid int) returns varchar(10) deterministic
BEGIN

  return (select company from v2_COMPANY where cid=ccid);

end
