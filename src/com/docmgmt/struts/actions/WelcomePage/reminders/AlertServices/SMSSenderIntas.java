package com.docmgmt.struts.actions.WelcomePage.reminders.AlertServices;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.docmgmt.server.prop.KnetProperties;

public class SMSSenderIntas implements ISMSSender {
	private KnetProperties prop;
	private String  auth;

	public void init() 
	{
		prop= KnetProperties.getPropInfo("Alert.properties");
		auth="?"+prop.getValue("SMS_UserName_Key")+"="+prop.getValue("SMS_UserName_Value")+"&";
		auth +=prop.getValue("SMS_Password_Key")+"="+prop.getValue("SMS_Password_Value")+"&";
		auth +=prop.getValue("SMS_SenderId_Key")+"="+prop.getValue("SMS_SenderId_Value")+"&";
		auth +=prop.getValue("SMS_CDMSHeader_Key")+"="+prop.getValue("SMS_CDMSHeader_Value")+"&";
		auth +=prop.getValue("SMS_IsFlash_Key")+"="+prop.getValue("SMS_IsFlash_Value")+"&";	
	}
	
	public boolean sendSMSAlerts(String msgBody, ArrayList<String> sendTo) {
		try {
			String msgURL;
			int charLength = Integer.parseInt(prop.getValue("SMS_Character_Limit"));
			if(charLength <= msgBody.length())
				msgURL = prop.getValue("SMS_Short_URL");
			else
				msgURL = prop.getValue("SMS_Long_URL");
			System.out.println(msgBody);
			try 
			{
				msgBody = URLEncoder.encode(msgBody, "UTF-8");
				//System.out.println(msgBody);
			} 
			catch (UnsupportedEncodingException e) 
			{
				e.printStackTrace();
			}
			String smsReceiver = sendTo.toString().substring(1, sendTo.toString().length()-1);
			smsReceiver=smsReceiver.replaceAll(" ", "");
			String generateAuth = auth + prop.getValue("SMS_MobileNo_Key")+"="+smsReceiver+"&";
				   generateAuth += prop.getValue("SMS_Message_Key")+"="+msgBody; 
			
			URL urlForSMS = new URL(msgURL+generateAuth);
			URLConnection conn = urlForSMS.openConnection();
			conn.setDoInput(true);
            InputStream inStream = conn.getInputStream();
	        BufferedReader input = new BufferedReader(new InputStreamReader(inStream));
	        //if(input.readLine().matches("message sent"))
            System.out.println(input.readLine());
			System.out.println("sms url:="+urlForSMS.toString());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false;
	}

	

}
