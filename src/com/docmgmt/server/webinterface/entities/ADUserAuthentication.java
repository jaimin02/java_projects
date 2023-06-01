package com.docmgmt.server.webinterface.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.struts.actions.authentic.AuthenticSupport;

public class ADUserAuthentication  extends AuthenticSupport  {
	
	String output ;
	public String authenticateADUser(String userName, String userPass) throws IOException {
		String outputOfAPI;
		
		KnetProperties knetProperties = KnetProperties.getPropInfo();
   	 	String AdUrl = knetProperties.getValue("ADUrl");
		URL url = new URL(AdUrl+userName+"&password_1="+userPass);
		HttpURLConnection conn = null;
		
		conn = (HttpURLConnection) url.openConnection();
		Map<String, String> headers = new HashMap<>();
		headers.put("appName", "KNET");
		 for (String headerKey : headers.keySet()) {
		   conn.setRequestProperty(headerKey, headers.get(headerKey));
		}
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		System.out.println(url);
		
		if (conn.getResponseCode() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}
		if (conn.getResponseCode() == 200) {
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String op=null;
			System.out.println("Output from Server ....");
			while ((op = br.readLine()) != null) {
				output=op;
				System.out.println(output);
			}
		}
		conn.disconnect();
		outputOfAPI=output;
		return outputOfAPI;
	}

}
