package se.team2.controllers;
import java.io.*; 
import java.util.*;
public class FileHandler
{
	public static void writeTo(String filePath,String output) throws Exception
	{
		FileWriter fw = new FileWriter(filePath);	
		fw.write(output);
		fw.close();
	}

	public static String readFrom(String filePath) throws Exception
	{
		File file = new File(filePath);
		Scanner input = new Scanner(file);
		String retval = "";
		while(input.hasNextLine()) 
		{
			retval += input.nextLine();
			if(input.hasNextLine()) retval += "\n";
		}
		return retval;
	}
}

