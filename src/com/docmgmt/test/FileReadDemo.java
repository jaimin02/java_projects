package com.docmgmt.test;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileReadDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					"D://vijay/temp1.txt"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				
				if(line.trim().equals("")){
					
					System.out.println("ss");
				}
				System.out.println(line);
				// ...
		}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
