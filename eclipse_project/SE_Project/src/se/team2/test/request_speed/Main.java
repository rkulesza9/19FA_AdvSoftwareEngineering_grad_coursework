package se.team2.test.request_speed;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import se.team2.controllers.ServerRequestHandler;

public class Main {
	
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		
		ServerRequestHandler srh2 = new ServerRequestHandler2();
		srh2.setParam("symbol", "rfem");
		srh2.makeRequest(ServerRequestHandler.DAYS_30_REQUEST);
		
		long end = System.currentTimeMillis();
		long change = end-start;
		long change_s = Math.round(1.0*change/1000);
		
		System.out.println("Completed in "+change+" ms!");
		System.out.println("Completed in "+change_s+" s!");
		
		
    }
	
}