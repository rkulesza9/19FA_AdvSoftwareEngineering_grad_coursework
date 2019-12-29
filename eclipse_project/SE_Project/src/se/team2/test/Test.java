package se.team2.test;

import java.util.Arrays; 

import se.team2.controllers.ServerRequestHandler;
import se.team2.utils.Table;

public class Test {

	public static void main(String[] args) throws Exception {
		ServerRequestHandler srh = new ServerRequestHandler();
		srh.setParam("symbol", "rfem");
		srh.makeRequest(ServerRequestHandler.COMPANY_INFO);
		System.out.println(companyInfo_output(srh,"RFEM"));
	}
	
	private static String companyInfo_output(ServerRequestHandler srh, String symbol) {
		String str = symbol+"\n\n";
		String a = companyInfo_id_table(srh).output();
		if(!a.isBlank()) str += "IDENTIFICATION\n" + a +"\n";
		
		a =  companyInfo_descr_table(srh);
		if(!a.isBlank()) str += "DESCRIPTION\n" + a +"\n";
		
		a = companyInfo_siInfo(srh).output();
		if(!a.isBlank()) str += "STOCK AND INDUSTRY INFO\n" + a  + "\n";
		
		a = companyInfo_contactInfo(srh).output();
		if(!a.isBlank()) str += "CONTACT INFO" + a + "\n";
		
		return str;
	}
	
	private static Table companyInfo_contactInfo(ServerRequestHandler srh) {
		Table t = new Table();
		t.setShowVerticalLines(true);
		
		String[] fields = new String[] { "website" , "address" , "address2" , "city" , "state" , "zip" , "country" , "phone" };
		fields = companyInfo_dropNullFields(srh,fields);
		
		String[] row = new String[fields.length];
		for(int x = 0; x < row.length; x++) {
			row[x] = srh.getString(fields[x], 0);
		}
		t.setHeaders(fields);
		t.addRow(row);
		
		return t;
	}
	
	private static Table companyInfo_siInfo(ServerRequestHandler srh) {
		Table t = new Table();
		t.setShowVerticalLines(true);
		
		String[] fields = new String[] { "exchange" , "industry" , "issueType" , "sector" , "employees" };
		fields = companyInfo_dropNullFields(srh,fields);
		
		String[] row = new String[fields.length];
		for(int x = 0; x < row.length; x++) {
			row[x] = srh.getString(fields[x], 0);
		}
		t.setHeaders(fields);
		t.addRow(row);
		
		return t;
	}

	public static String companyInfo_descr_table(ServerRequestHandler srh) {
		int line_length = 25;
		Table t = new Table();
		t.setShowVerticalLines(true);
		
		String field = "description";
		String value = srh.getString(field, 0);
		
		String text = "";
		if(value.equalsIgnoreCase("null")) return "";
		for(int x = 0; x < value.length(); x+=line_length) {
			if(x+line_length > value.length()) text += value.substring(x,value.length());
			else text += value.substring(x,x+line_length) + "\n";
		}
		
		return text;
	}
	
	public static Table companyInfo_id_table(ServerRequestHandler srh) {
		Table t = new Table();
		t.setShowVerticalLines(true);
		
		String[] fields = companyInfo_dropNullFields(srh,new String[] { "companyName", "securityName", "primarySicCode", "ceo" });
		
		String[] row = new String[fields.length];
		for(int x = 0; x < row.length; x++) {
			row[x] = srh.getString(fields[x], 0);
		}
		t.setHeaders(fields);
		t.addRow(row);
		
		return t;
	}
	
	public static String[] companyInfo_dropNullFields(ServerRequestHandler srh,String[] fields) {
		boolean[] drop_column = new boolean[fields.length];
		int count = fields.length;
		
		for(int x = 0; x < fields.length; x++) {
			if (srh.getString(fields[x],0).equalsIgnoreCase("null")) {
				drop_column[x] = true;
				count = count - 1;
			} else {
				drop_column[x] = false;
			}
		}
		
		String[] fields2 = new String[count];
		int index = 0;
		for(int x = 0; x < fields.length; x++) {
			if(!drop_column[x]) {
				fields2[index] = fields[x];
				index++;
			}
		}
		
		return fields2;
		
	}
	
}
