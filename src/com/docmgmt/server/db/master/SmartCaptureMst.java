package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOSmartCaptureMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class SmartCaptureMst {

	DataTable datatable;
	public SmartCaptureMst()
	{
		 datatable=new DataTable();
	}
	

public Vector<DTOSmartCaptureMst> getSmartCapDetail() {

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOSmartCaptureMst> clientVector = new Vector<DTOSmartCaptureMst>();
	Connection con = null;
	ResultSet rs = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","SmartcaptureUtility" ,"","");
		while(rs.next())
		{
			DTOSmartCaptureMst dto = new DTOSmartCaptureMst();
			dto.setSrNo(rs.getInt("iSrNo"));
			dto.setSmartCaptureVersion(rs.getString("vSmartCaptureVersion"));
			dto.setDocStackVersion(rs.getString("vDocStackVersion"));
			dto.setDescription(rs.getString("vDescription"));
			dto.setFileName(rs.getString("vFileName"));
			dto.setFilePath(rs.getString("vFilePath"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			clientVector.addElement(dto);
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return clientVector;
}


public DTOSmartCaptureMst getSmartCapDetailDTO(String srID) {

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	DTOSmartCaptureMst clientVector = new DTOSmartCaptureMst();
	Connection con=null;
	ResultSet rs = null;
	
	ArrayList<String> time = new ArrayList<String>();
	 String locationName = "Ahmedabad";
   	 String countryCode = "IND";
   	DTOSmartCaptureMst dto = new DTOSmartCaptureMst();
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","SmartcaptureUtility" ,"iSrNo='" + srID + "' ","");
		while(rs.next())
		{
			
			dto.setSrNo(rs.getInt("iSrNo"));
			dto.setSmartCaptureVersion(rs.getString("vSmartCaptureVersion"));
			dto.setDocStackVersion(rs.getString("vDocStackVersion"));
			dto.setDescription(rs.getString("vDescription"));
			dto.setFileName(rs.getString("vFileName"));
			dto.setFilePath(rs.getString("vFilePath"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			//clientVector.addElement(dto);
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return dto;
}

public void insertIntoSmartCapturedownloadhistory(DTOSmartCaptureMst dto) {
	CallableStatement proc = null;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		proc = con.prepareCall("{call Insert_SmartCapturedownloadhistory(?,?,?,?)}");

		proc.setInt(1, dto.getSrNo());
		proc.setString(2, dto.getSmartCaptureVersion());
		proc.setString(3, dto.getRemark());
		proc.setInt(4, dto.getModifyBy());
		proc.execute();
		proc.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
}

    
}
