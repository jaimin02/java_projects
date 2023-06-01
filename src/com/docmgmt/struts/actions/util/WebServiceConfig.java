package com.docmgmt.struts.actions.util;

public class WebServiceConfig {
	private String WS_NAMESPACE = "http://tempuri.org/";
	private String WS_URL = "";
	private String WS_SOAPACTION = "";
	private String WS_METHODNAME = "";
	private String WEB_SERVICE = ""; 

	public String getNamespace()
	{
		return WS_NAMESPACE;
	}
	
	public String getURL()     
	{
		return WS_URL;      
	}
  
	public String getSOAPAction()                
	{
		return WS_SOAPACTION;
	}

	public String getMethod()
	{
		return WS_METHODNAME;
	}

	public String getwebService() 
	{
		return WEB_SERVICE; 
	}
 
	public void SetNamespace(String WsNamespace)
	{
		WS_NAMESPACE = WsNamespace;
	}

	public void SetURL(String URL , String WebService)
	{
		WS_URL = URL + WebService;
	}

	public void SetSOAPAction(String NameSpace , String MethodName_1)
	{
		WS_SOAPACTION = NameSpace + MethodName_1;
	}

	public void SetMethod(String Method)
	{
		WS_METHODNAME = Method;
		//SetSOAPAction(Method_1);
	}

	public void SetWebService(String WebService)
	{
		WEB_SERVICE = WebService;
		//SetURL(WebService);
	}
}
