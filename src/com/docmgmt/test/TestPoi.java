package com.docmgmt.test;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;


public class TestPoi {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
    	
    		JSONParser parser=new JSONParser();
    		
    		
    		try {
    		//	JSONObject jsonObject=(JSONObject) parser.parse("D://jsontest.json");
    			
    			JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("D://jsontest.txt")));
    			
    			JSONArray lang= (JSONArray) jsonObject.get("obj");
    			for(int i=0; i<lang.size(); i++){
    				 JSONObject obj=(JSONObject) lang.get(i);
    				 System.out.println(obj.get("brand_family"));
    	          }

    		}catch(Exception e){
    			
    			e.printStackTrace();
    		}
			 
			
			
	
    	
    }

   
   
    public boolean isLinearized( PdfReader reader ) { 
    	 final PdfName LINEARIZED = new PdfName("Linearized"); 
      for( int i = 0; i < reader.getXrefSize(); ++i) { 
         PdfObject testObj = reader .getPdfObject( i ); 
    //getPdfObjectRelease()? 
         if (testObj.type() == PdfObject.DICTIONARY) { 
           if (((PdfDictionary)testObj).contains(LINEARIZED)) { 
             return true; 
           } 
         } 
      } 
      return false; 
    } 
}