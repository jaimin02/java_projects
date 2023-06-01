package com.docmgmt.server.webinterface.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipManager {
	
	/*public static void main(String[] args) {
		ArrayList<String> entryRegexList = new ArrayList<String>();
		entryRegexList.add("^\\d{4}/$");
		entryRegexList.add("^\\d{4}/index\\.xml$");
		boolean valid = isValidZip(new File("D:/0000.zip"), entryRegexList);
		System.out.println(valid);
	}*/
	public static boolean isValidZip(File file,ArrayList<String> entryRegexList) {
		Enumeration entries;
		ZipFile zipFile;
		try {
			//Check If file is valid zip file or not 
			zipFile = new ZipFile(file);
			//throws exception if not a valid archive file...
			
			if(entryRegexList == null || entryRegexList.size() == 0){
				//if no regex given to check zip entries then return true.
				return true;
			}
			
			boolean[] results = new boolean[entryRegexList.size()];
			entries = zipFile.entries();
			while(entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry)entries.nextElement();
	    		
				for (int i = 0; i < entryRegexList.size(); i++) {
					String entryRegex = entryRegexList.get(i);
					if(!results[i])
						results[i] = Pattern.matches(entryRegex, entry.getName());
				}
			}
			for (boolean result : results) {
				if(!result){
					return false;
				}
			}
	    	
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean unZip(File file,String destPath) {
		Enumeration entries;
		ZipFile zipFile;
	    try {
	    	zipFile = new ZipFile(file);
	    	
	    	entries = zipFile.entries();

	    	while(entries.hasMoreElements()) {
	    		ZipEntry entry = (ZipEntry)entries.nextElement();

	    		if(entry.isDirectory()) {
	    			System.out.println("Extracting directory: " + entry.getName());
	    			continue;
	    		}
	    		System.out.println("Extracting file: " + entry.getName());
	    		InputStream in = zipFile.getInputStream(entry);
		        File destFile = new File(destPath+"/"+entry.getName());
		        if(!destFile.getParentFile().exists()){
		        	destFile.getParentFile().mkdirs();
		        }
		        OutputStream out = new FileOutputStream(destFile);
		        // Copy the bits from instream to outstream
		        byte[] buf = new byte[1024];
		        int len;
		        while ((len = in.read(buf)) > 0) 
		        {
		        	out.write(buf, 0, len);
		        }
		        in.close();
		        out.close();
	    	}
	    	zipFile.close();
	    	return true;
	    } catch (IOException ioe) {
	    	System.err.println("Unhandled exception:");
	    	ioe.printStackTrace();
	    	return false;
	    }
	}
}
