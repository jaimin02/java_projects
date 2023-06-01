package com.docmgmt.server.webinterface.entities;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;

public class ConvertPdfToDoc {

	
	public String convertPdfToDoc(String SrcDoc, String DestDoc){
		String output="";
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		
		String docToPdfURL=propertyInfo.getValue("docToPdfUrl");
		
		BufferedReader reader = null ;
		StringBuilder result = new StringBuilder();
		 URL url = null;
		try {
			url = new URL(docToPdfURL+"inputPath="+SrcDoc+"&outputPath="+DestDoc);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		    		
		 System.out.println("URL------------:::::::::::::::::::"+url);
		 
		 try{
			 
		 System.out.println("Inside try ------------:::::::::::::::::::");
		 HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");

			InputStream in = new BufferedInputStream(con.getInputStream());
			
			System.out.println("con.getInputStream()------------:::::::::::::::::::"+con.getInputStream());

			reader = new BufferedReader(new InputStreamReader(in));

			System.out.println(con.getResponseMessage());	
			
			String line;

			 while ((line = reader.readLine()) != null)
		     {
				 result.append(line + '\n');
		     }

			 System.out.println("result-------:::::::::::::::::::"+result);
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		
		return result.toString();
	}
	
}
