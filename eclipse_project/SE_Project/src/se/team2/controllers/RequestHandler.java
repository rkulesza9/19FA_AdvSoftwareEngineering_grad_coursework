package se.team2.controllers;

public interface RequestHandler {
	/**returns information requested from DB/IEX/Our-Server
	 * as an integer
	 * @param key - this is the label for the data you are requesting. (eg. date, open, close, etc.)
	 * @param record_index - this is the index of the record from the request.
	 * @return - returns the requested data as an integer
	 */
	public int getInteger(String key, int record_index);
	
	/** returns information requested from DB/IEX/Our-Server
	 * as a double.
	 * @param key - this is the label for the data you are requesting. (eg. date, open, close, etc.)
	 * @param record_index - this is the index of the record from the request.
	 * @return - returns the requested data as a double.
	 */
	public double getDouble(String key, int record_index);
	
	/** returns information requested from DB/IEX/Our-Server
	 * as a String.
	 * @param key - this is the label for the data you are requesting. (eg. date, open, close, etc.)
	 * @param record_index - this is the index of the record from the request.
	 * @return - returns the requested data as a String
	 */
	public String getString(String key,int record_index);
	
	/**
	 * returns an array of keys that are handled
	 * by this RequestHandler
	 * @return an array of keys that are handled by this request handler
	 */
	public String[] keys();
	
	/**
	 * returns the number of records in the RequestHandler
	 * @return
	 */
	public int size();
	
	/**
	 * this makes the request that this RequestHandler "handles"
	 * @param request_type - a number representing the type of request to be made. 
	 * Constants should be set in descendent classes. (eg. ServerRequestHandler.3_MONTH_REPORT)
	 * @throws Exception 
	 */
	public void makeRequest(int REQUEST_TYPE) throws Exception;

	void setParam(String key, Object obj);
}
