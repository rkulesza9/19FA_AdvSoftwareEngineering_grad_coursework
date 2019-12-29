package se.team2.test;

import java.util.Arrays;

import se.team2.controllers.RequestHandler;

public class TestRequestHandler implements RequestHandler {
	
	//Date, open, close, high, low, volume
	private String[] keys = { "date", "open" , "close" , "high", "low", "volume" };
	private String[] dates;
	private double[] open;
	private double[] close;
	private double[] high;
	private double[] low;
	private double[] volume;
	
	public TestRequestHandler() {
		
	}

	@Override
	public int getInteger(String key, int record_index) {
		return (int) getDouble(key,record_index);
	}

	@Override
	public double getDouble(String key, int record_index) {
		if(key.equals("date")) {
		} else if(key.equals("open")){
			return open[record_index];
		} else if(key.equals("close")) {
			return close[record_index];
		}else if(key.equals("high")) {
			return high[record_index];
		}else if(key.equals("low")) {
			return low[record_index];
		}else if(key.equals("volume")) {
			return volume[record_index];
		}
		return -1;
	}

	@Override
	public String getString(String key, int record_index) {
		if(!key.equals("date")) {
			return null;
		} else {
			return dates[record_index];
		}
	}

	@Override
	public String[] keys() {
		return Arrays.copyOf(keys,keys.length);
	}

	@Override
	public int size() {
		return dates.length;
	}

	@Override
	public void makeRequest(int REQUEST_TYPE) {
		//Date, open, close, high, low, volume
		dates = new String[] {"2019-10-16", "2019-10-15", "2019-10-14"};
		open = new double[] { 0.1, 0.1, 0.1 };
		close = new double[] { 0.1, 0.1, 0.1 };
		high = new double[] { 0.1, 0.1, 0.1 };
		low = new double[] { 0.1, 0.1, 0.1 };
		volume = new double[] { 0.1, 0.1, 0.1 };
		
		//this is where the request would be sent and recieved
	}
	
	public static final int REQUEST_TYPE_TEST = 0;

	@Override
	public void setParam(String key, Object obj) {
		// TODO Auto-generated method stub
		
	}

}