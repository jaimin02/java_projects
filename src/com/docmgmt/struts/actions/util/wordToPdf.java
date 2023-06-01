package com.docmgmt.struts.actions.util;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.ksoap2.serialization.SoapObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
public class wordToPdf {
	
	
	public String convertDoc(String SrcDoc, String DestDoc){
		
		WEbServiceAccess objWSAccess = new WEbServiceAccess();
		WebServiceMethods objWSMethod = new WebServiceMethods();
		String response="";
		String resArray[] = new String[3];
		SoapObject soReturn ;
		String ParameterName[] = new String[2];
		String ParameterValues[] = new String[2];

		ParameterName[0]= "sourceFileName";
		ParameterValues[0] = SrcDoc;
		
		ParameterName[1]= "destinationFileName";
		ParameterValues[1] = DestDoc;
		
		soReturn =  objWSAccess.GetMethodDataWithParameter(
				objWSMethod.WS_URL_FFR,
				objWSMethod.WS_SOAPACTION ,
				objWSMethod.WS_MOBILEFFR ,
				objWSMethod.WSMETHOD_GETORDERDETAILS.split("_")[0],
				ParameterName, ParameterValues);
		try
		{
			if(soReturn != null)
			{
				System.out.println(soReturn);
				
				System.out.println(soReturn.getProperty("destinationFileName"));
				response=soReturn.getProperty("destinationFileName").toString();
				
				
				resArray[0]=soReturn.getProperty("ConvertWordToPDFResponse").toString();
				resArray[1]=soReturn.getProperty("destinationFileName").toString();
				if(resArray[0].equalsIgnoreCase("True")){
					resArray[2]="";
				}
				else{
					resArray[2]=soReturn.getProperty("errorMessage").toString();
				}
				/*System.out.println("status"+resArray[0]);
				System.out.println("path"+resArray[1]);
				System.out.println("message"+resArray[2]);*/
				
			}
			else
			{
				resArray[0]="False";
				resArray[1]="";
				resArray[2]="Error while connecting to server";
			}
		}
		catch (Exception e)
		{
			//System.out.println(e.getLocalizedMessage());
			resArray[0]="False";
			resArray[1]="";
			resArray[2]=e.getLocalizedMessage();
			
		}
		response=resArray[0]+"#"+resArray[1]+"#"+resArray[2];
		return response;
	}
	public Document XMLfromString(String xml) {

		Document doc = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
           
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);   

		} catch (ParserConfigurationException e) {
			System.out.println("XML parse error: " + e.getMessage()); 
			return null;
		} catch (SAXException e) {
			System.out.println("Wrong XML file structure: " + e.getMessage());
			return null;
		} catch (IOException e) {
			System.out.println("I/O exeption: " + e.getMessage());
			return null;
		}

		return doc;

	}
	public static void main(String args[]){
		wordToPdf wtp=new wordToPdf();
		String result=wtp.convertDoc("D:/temp/test.docx","D:/temp/test.pdf");
		System.out.println("result"+result);
	}
}
