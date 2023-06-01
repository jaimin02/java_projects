package com.docmgmt.struts.actions.util;

import java.io.ByteArrayInputStream;
import java.net.SocketException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;

public class WEbServiceAccess {

	private WebServiceConfig objWSConfig = new WebServiceConfig();
	private String ws_Namespace;
	private String ws_Method;
	private String ws_SOAPAction;
	private String ws_URL;
	private String ws_MethodReturnString; 
	

	private static final String TAG = "WebServiceAccess";
	public String ERRORMSG = "" ;

	public String getws_MethodReturnString() {
		return ws_MethodReturnString;
	}

	public void setws_MethodReturnString(String errorString) {
		ws_MethodReturnString = errorString;
	}

	public String getNamespace()
	{
		return objWSConfig.getNamespace();
	}

	public String getURL()
	{
		return objWSConfig.getURL();
	}

	public String getSOAPAction()
	{
		return objWSConfig.getSOAPAction();
	}

	public String getMethod()
	{
		return objWSConfig.getMethod();
	}

	public void SetMethod(String Method_1)
	{
		objWSConfig.SetMethod(Method_1);
	}

	public String GetMethodData(String URL , String SoapAction , String WebService ,String MethodName,String eStr_Retu)
	{
		String ResultData;
		WebServiceConfig objWSConfig = new WebServiceConfig();
		try
		{
			String MethodErrorString = "";
			objWSConfig.SetURL(URL, WebService);
			objWSConfig.SetSOAPAction(SoapAction, MethodName);
			objWSConfig.SetWebService(WebService);
			objWSConfig.SetMethod(MethodName);

			ws_Method = objWSConfig.getMethod();
			ws_Namespace = objWSConfig.getNamespace();
			ws_URL = objWSConfig.getURL(); 
			ws_SOAPAction = objWSConfig.getSOAPAction();

			SoapObject request = new SoapObject(ws_Namespace,ws_Method);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			//	envelope.dotNet = true;
			envelope.encodingStyle = SoapSerializationEnvelope.XSD;
			envelope.setOutputSoapObject(request);
			
			HttpTransportSE androidHttpTransport = new HttpTransportSE(ws_URL);

			androidHttpTransport.call(ws_SOAPAction, envelope);
			androidHttpTransport.debug=true;
			androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

			try {
				byte[] bytes = request.toString().getBytes();

				ByteArrayInputStream inputStream = new ByteArrayInputStream(
						bytes);
				XmlPullParser p = new KXmlParser();
				p.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);

				p.setInput(inputStream, "UTF-8");

				envelope.parse(p);
				inputStream.close();

			}
			catch (Exception e) 
			{
				System.out.println(e.getLocalizedMessage());
			}

			SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
			MethodErrorString = resultsRequestSOAP.getProperty(0).toString();

			setws_MethodReturnString(MethodErrorString);

			ResultData = resultsRequestSOAP.getProperty(1).toString();
		}
		catch (Exception e) 
		{
			ResultData = e.getMessage();
		}

		return ResultData;
	}

	public SoapObject GetMethodDataWithParameter(String URL, String SoapAction, String WebService, String MethodName, 
												 String ParameterName[], String ParameterValue[])
	{
		WebServiceConfig objWSConfig = new WebServiceConfig(); 
		int ArryIndex = 0;
		ERRORMSG = "";
//		String UTF8_Encoding = "UTF-8"; 

		SoapObject resultsRequestSOAP = null ;

		
		try
		{
			objWSConfig.SetURL(URL, WebService);
			objWSConfig.SetSOAPAction(SoapAction, MethodName);
			objWSConfig.SetWebService(WebService); 
			objWSConfig.SetMethod(MethodName);

			ws_Method = objWSConfig.getMethod();
			ws_Namespace = objWSConfig.getNamespace(); 
			ws_URL = objWSConfig.getURL(); 
			ws_SOAPAction = objWSConfig.getSOAPAction();

			SoapObject request = new SoapObject(ws_Namespace, ws_Method);
			

			for(ArryIndex = 0 ; ArryIndex < ParameterName.length ; ArryIndex ++)
			{
				PropertyInfo ParaObj = new PropertyInfo();
				ParaObj.type = PropertyInfo.OBJECT_CLASS;
				ParaObj.namespace = ws_Namespace;
				ParaObj.setName(ParameterName[ArryIndex].toString());
				ParaObj.setValue(ParameterValue[ArryIndex].toString());
				request.addProperty(ParaObj);     
			}

			System.setProperty("http.keepAlive", "false"); 
			

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			envelope.dotNet = true;
			//envelope.encodingStyle = SoapSerializationEnvelope.XSD;
			//envelope.setAddAdornments(true);   

			HttpTransportSE androidHttpTransport = new HttpTransportSE(ws_URL);
			androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

			try
			{
				androidHttpTransport.call(ws_SOAPAction , envelope);
			}
			catch(SocketException se) 
			{
				ERRORMSG = "Network not available..";
				System.out.println("Error in network connection.");
				return resultsRequestSOAP;  
			}
			androidHttpTransport.debug = true;   

			/*try 
			{
				byte[] bytes = request.toString().getBytes();

				ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
				XmlPullParser p = new KXmlParser();
				p.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true); 

				p.setInput(inputStream,UTF8_Encoding);  

				envelope.parse(p);

			}
			catch (Exception e) 
			{
				e.printStackTrace();   
			}*/

			resultsRequestSOAP = (SoapObject) envelope.bodyIn;  

			/*ResultData[0] = resultsRequestSOAP.getProperty(0).toString();   
			// setws_MethodReturnString(MethodErrorString);
			ResultData[1] = resultsRequestSOAP.getProperty(1).toString(); 

			ResultData[1] = ResultData[1].trim();   
			ResultData[1] = ResultData[1].replace("\n","");

			String MethodErrorString = (String) resultsRequestSOAP.getProperty("eStr_Retu");
			setws_MethodReturnString(MethodErrorString);

			//ResultData = RecureviceFunction4XMLReading(MainNode1,ResultData);    
			 */	
		}
		catch (OutOfMemoryError e) 
		{
			e.printStackTrace();
			ERRORMSG = "" ;
			System.out.println(e.getLocalizedMessage());
			return resultsRequestSOAP;
		}
		catch(SocketException e) 
		{
			e.printStackTrace();
			ERRORMSG = "Network not available..";
			System.out.println(e.getLocalizedMessage());
			return resultsRequestSOAP;
		} 
		catch (Exception e)  
		{
			ERRORMSG = "Something went wrong...";
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return resultsRequestSOAP;  
	} 



}
