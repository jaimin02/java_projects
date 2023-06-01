package com.docmgmt.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class RandomAccessFileDemo {

	public static void main(String args[]) {

		try {
			TreeMap<String, String> map = new TreeMap<String, String>();

			BufferedReader br = new BufferedReader(new FileReader(
					"D:/vijay/temp.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				// process the line.
				// System.out.println(line);
				
				line = line.trim();
				String number = line.substring(0, line.indexOf(" "));
				String qlty = line.substring(line.lastIndexOf(" "));
				
				
				int found=0;
				for (int i = 0; i < map.size(); i++) {

					if (map.containsKey(number.trim())) {

						String oldQlt=map.get(number.trim());
						
					
						
						int totalqlt=Integer.parseInt(oldQlt) + Integer.parseInt(qlty.trim());
						
						
						map.remove(number.trim());
						map.put(number, ""+totalqlt);
						found=1;
						break;
						
					}

				}
				if(found==0)
				{
					map.put(number.trim(), qlty.trim());
					
				}

	
			}
			br.close();
		
			for (Map.Entry<String, String> e : map.entrySet()) {
			    //to get key
			  System.out.println(e.getKey() +" ---- " + e.getValue());
			    //and to get value
			  
			}
			

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
