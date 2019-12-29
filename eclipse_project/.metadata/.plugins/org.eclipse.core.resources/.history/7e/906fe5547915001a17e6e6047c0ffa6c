package se.team2.test.request_speed;
import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

import com.google.gson.*;

import se.team2.controllers.RequestHandler;

public class ServerRequestHandler implements RequestHandler 
{
	//Instance variable is an array of JSON objects right?
	JsonArray jsonArray;
	Hashtable<String,Object> ht = new Hashtable<String,Object>();
	
	public boolean status() {
		if(jsonArray == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int getInteger(String key, int record_index) 
	{
		return jsonArray.get(record_index).getAsJsonObject().get(key).getAsInt();
	}

	@Override
	public double getDouble(String key, int record_index) 
	{
		return jsonArray.get(record_index).getAsJsonObject().get(key).getAsDouble();
	}

	@Override
	public String getString(String key, int record_index) 
	{
		return jsonArray.get(record_index).getAsJsonObject().get(key).getAsString();
	}

	@Override
	public String[] keys() 
	{
		JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
		Object[] objs = jsonObject.keySet().toArray();
		String[] strs = new String[objs.length];
		for(int x = 0; x < objs.length; x++) {
			strs[x] = (String) objs[x];
		}
		
		return strs;
	}

	@Override
	public int size() 
	{
		return jsonArray.size();
	}

	@Override
	public void setParam(String key, Object obj)
	{
		ht.put(key,obj);
	}

	@Override
	public void makeRequest(int REQUEST_TYPE) throws Exception
	{
		//Template url
                String urlString = "http://fproject-se.ddns.net/?symbol=W&date_start=X&date_end=Y";

		//Get the Strings for the dates.
		String current = getDate(0l);
		String daysAgo30  = getDate(MILLI_30_DAYS);

		//Switch case to the string
		switch(REQUEST_TYPE)
		{
			case APPLE_30_DAYS:
				urlString = urlString.replace("W","aapl");
				break;
			case GOOGLE_30_DAYS:
				urlString = urlString.replace("W","googl");
				break;
			case RFM_30_DAYS:
				urlString = urlString.replace("W","rfem");
				break;
			case AGGREGATE_REQUEST:
				urlString += "&mode=aggregate";
				urlString = urlString.replace("W", (String)ht.get("symbol"));
				urlString = urlString.replace("X",(String)ht.get("date_start"));
				urlString = urlString.replace("Y",(String)ht.get("date_end"));
				break;
			case DAYS_30_REQUEST:
				urlString = "http://fproject-se.ddns.net?symbol=" + ht.get("symbol") + "&mode=30DayReport";
				break;
			case COMPANY_INFO:
				urlString = "http://fproject-se.ddns.net?symbol=" + ht.get("symbol") + "&mode=CompanyInfo";
				break;
			case POP_SEARCH:
				urlString = "http://fproject-se.ddns.net?mode=popSearch&symbol=" + ht.get("symbol");
				break;
			default:
				System.out.println("ERROR"); //Throw error here later
		}
		
		//System.out.println(urlString);

		//Update the dates in the string: HERE! Only do this if I it wasn't Aggregate request?
		//Otherwise I'll overwrite it.
		if(REQUEST_TYPE != AGGREGATE_REQUEST)
		{
			urlString = urlString.replace("X",daysAgo30);
			urlString = urlString.replace("Y",current);
		}

		//Do the HTTP Get request
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while((inputLine = in.readLine()) != null) content.append(inputLine);
		in.close();
		con.disconnect();
		String jsonString = content.toString();

		//Parse to Json element
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonString);

		//Get as Json Array
		jsonArray = element.getAsJsonArray();


	}

	//Returns the date formatted. Not the correct format, how can I change it?
	//I see no other options in the Java Doc.
	public String getDate(long diff)
	{
		//Set the date of the Calender object based on diff given.
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(cal.getTimeInMillis() - diff);

		//Format the date
		Date date = cal.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formatted = df.format(date);
		return formatted;	
	}

	//Static stuff
	public static final long MILLI_30_DAYS = 2592000000l;
	public static final int APPLE_30_DAYS = 0;
	public static final int GOOGLE_30_DAYS = 1;
	public static final int RFM_30_DAYS = 2;
	public static final int AGGREGATE_REQUEST = 3;
	public static final int DAYS_30_REQUEST = 4;
	public static final int COMPANY_INFO = 5;
	public static final int POP_SEARCH = 6;

}
