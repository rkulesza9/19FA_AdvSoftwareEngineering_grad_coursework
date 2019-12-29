create function f_companyID(ccompany varchar(10)) returns int DETERMINISTIC
BEGIN
	return (select cid from v2_COMPANY where company=ccompany);
end
