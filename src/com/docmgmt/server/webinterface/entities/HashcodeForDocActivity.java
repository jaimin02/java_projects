package com.docmgmt.server.webinterface.entities;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;

public class HashcodeForDocActivity {

	public String generateHashCode(String workspaceID,int nodeId,int stageId ) throws MalformedURLException{
		String output="";
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		
		String HashcodeURL = propertyInfo.getValue("HashcodeURL");
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		Vector<DTOWorkSpaceNodeHistory> getNodeHistory=docMgmtImpl.getFirstNodeHistoryByTranNoAndCountryCode(workspaceID, nodeId,locationName,countryCode);
		String uuId=getNodeHistory.get(0).getUuId().toString();
		Vector<DTOWorkSpaceNodeHistory> getMaxNodeHistory=docMgmtImpl.getMaxNodeHistoryByTranNo(workspaceID, nodeId);
		
		String fileType=getMaxNodeHistory.get(0).getFileType();
		if(fileType==null || fileType.equalsIgnoreCase(""))
			fileType="U";
		else
			fileType=getMaxNodeHistory.get(0).getFileType();
		
		String remark="test";
		int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		BufferedReader reader = null ;
		StringBuilder result = new StringBuilder();
		 URL url = new URL(HashcodeURL+"?docid="+uuId+"&wsid="+workspaceID+"&nodeid="+nodeId+"&tranno="+getMaxNodeHistory.get(0).getTranNo()+
				   "&stageid="+stageId+"&trantype="+fileType+"&userid="+usercode+"&remark="+remark+"&modifyby="+usercode);
		    		
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
	
	
	public String generateHashCodeForVoid(String workspaceID,int nodeId,int stageId ) throws MalformedURLException{
		String output="";
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		
		String HashcodeURL = propertyInfo.getValue("HashcodeURL");
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		Vector<DTOWorkSpaceNodeHistory> getNodeHistory=docMgmtImpl.getFirstNodeHistoryByTranNoAndCountryCode(workspaceID, nodeId,locationName,countryCode);
		String uuId=getNodeHistory.get(0).getUuId().toString();
		Vector<DTOWorkSpaceNodeHistory> getMaxNodeHistory=docMgmtImpl.getMaxNodeHistoryByTranNo(workspaceID, nodeId);
		
		String fileType="V";
		/*if(fileType==null || fileType=="")
			fileType="U";
		else
			fileType=getMaxNodeHistory.get(0).getFileType();*/
		
		String remark="test";
		int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		BufferedReader reader = null ;
		StringBuilder result = new StringBuilder();
		 URL url = new URL(HashcodeURL+"?docid="+uuId+"&wsid="+workspaceID+"&nodeid="+nodeId+"&tranno="+getMaxNodeHistory.get(0).getTranNo()+
				   "&stageid="+stageId+"&trantype="+fileType+"&userid="+usercode+"&remark="+remark+"&modifyby="+usercode);
		    		
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
