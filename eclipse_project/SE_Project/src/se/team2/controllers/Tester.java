package se.team2.controllers;

import java.util.Arrays;

public class Tester
{
	public static void main(String[] args) throws Exception
	{
		ServerRequestHandler srh = new ServerRequestHandler();
		srh.makeRequest(0);
		String[] keys = srh.keys();
		System.out.println(Arrays.deepToString(keys));
		
	}
}
		

