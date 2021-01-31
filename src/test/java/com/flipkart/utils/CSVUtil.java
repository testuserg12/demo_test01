package com.flipkart.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.opencsv.CSVWriter;

public class CSVUtil {

	public static void writeDataLineByLine(String filePath, Map<String, String> map) 
	{ 
		// first create file object for file placed at location 
		// specified by filepath 
		File file = new File(filePath); 
    try { 
        // create FileWriter object with file as parameter 
        FileWriter outputfile = new FileWriter(file); 
  
        // create CSVWriter object filewriter object as parameter 
        CSVWriter writer = new CSVWriter(outputfile); 
  
           
        
        // adding header to csv 
        String[] header = { "Device Details", "Price", "Ratings" }; 
        writer.writeNext(header); 
        for(Map.Entry m:map.entrySet()){    
            System.out.println(m.getKey()+" "+m.getValue()); 
            String[] data = { m.getValue().toString().split(";")[0], (String) m.getKey(), m.getValue().toString().split(";")[1] }; 
            writer.writeNext(data);
           } 
       
        // closing writer connection 
        writer.close(); 
    } 
    catch (IOException e) { 
        // TODO Auto-generated catch block 
        e.printStackTrace(); 
    } 
	}
}
