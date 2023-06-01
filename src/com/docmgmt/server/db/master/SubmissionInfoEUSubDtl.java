package com.docmgmt.server.db.master;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.docmgmt.dto.DTOSubmissionInfoEUSubDtl;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfoEUSubDtl {

	ConnectionManager conMgr;
	DataTable datatable;
	public SubmissionInfoEUSubDtl()
	{
		 conMgr=new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}
	

public void insertSubmissionInfoEUSubDtl(DTOSubmissionInfoEUSubDtl dto,int Mode)
{
	try
	{	    	
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call Insert_SubmissionInfoEUSubDtl(?,?,?,?,?,?,?,?,?)}");
		proc.setInt(1, dto.getSubmissionInfoEUSubDtlId());
		proc.setInt(2, dto.getWorkspaceCMSId());
		proc.setString(3,dto.getSubmissionInfoEUDtlId());
		proc.setTimestamp(4,dto.getDateOfSubmission());
		proc.setString(5,dto.getSubmissionDescription());
		proc.setInt(6,dto.getModifyBy());
		proc.setString(7,Character.toString(dto.getStatusIndi()));
		proc.setString(8, dto.getPublishCMSTrackingNo());
		proc.setInt(9,Mode); 
		proc.executeUpdate();
		proc.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
   }

}
   
public ArrayList<DTOSubmissionInfoEUSubDtl> getWorkspaceCMSSubmissionInfo(String submissionId)
{
	
	ArrayList<DTOSubmissionInfoEUSubDtl> lstCMSSubInfo = new ArrayList<DTOSubmissionInfoEUSubDtl>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		
		String where = " SubmissionId = '"+submissionId+"' ";
		ResultSet rs=datatable.getDataSet(con,"*","View_WorkspaceCMSSubmissionDtl" ,where,"CountryCodeName");
		while(rs.next())
		{
			DTOSubmissionInfoEUSubDtl dto = new DTOSubmissionInfoEUSubDtl();
			dto.setWorkspaceCMSId(rs.getInt("iWorkspaceCMSId"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setWaveNo(rs.getInt("iWaveNo"));
			dto.setCountryName(rs.getString("vCountryName"));
			dto.setCountryCodeName(rs.getString("CountryCodeName"));
			dto.setAgencyName(rs.getString("vAgencyName"));
			dto.setSubmissionInfoEUDtlId(rs.getString("SubmissionId"));
			dto.setSubmissionDescription(rs.getString("vSubmissionDescription"));
			dto.setDateOfSubmission(rs.getTimestamp("dDateOfSubmission"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setCMSTrackingNo(rs.getString("vCMSTrackingNo"));
			
			lstCMSSubInfo.add(dto);
			
		}
		rs.close();
		con.close();
		
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return lstCMSSubInfo;
}

//get workspaceCMSsubmission details by DTDversio of EU added by Dharmendra Jadav on 04-july-2016 
public ArrayList<DTOSubmissionInfoEUSubDtl> getWorkspaceCMSSubmissionInfo(String submissionId,String dtdversion)
{
	
	ArrayList<DTOSubmissionInfoEUSubDtl> lstCMSSubInfo = new ArrayList<DTOSubmissionInfoEUSubDtl>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=null;
		String where = " SubmissionId = '"+submissionId+"' ";
		if(dtdversion.equals("13")){
			rs=datatable.getDataSet(con,"*","View_WorkspaceCMSSubmissionDtl" ,where,"CountryCodeName");	
		}
		else if(dtdversion.equals("14")){
			rs = datatable.getDataSet(con, "*","View_WorkspaceCMSSubmissionDtlForEU14", where,"CountryCodeName");	
		}
		else if(dtdversion.equals("20") || dtdversion.equals("301") ){
			rs = datatable.getDataSet(con, "*","View_WorkspaceCMSSubmissionDtlForEU20", where,"CountryCodeName");	
		}
		
		while(rs.next())
		{
			DTOSubmissionInfoEUSubDtl dto = new DTOSubmissionInfoEUSubDtl();
			dto.setWorkspaceCMSId(rs.getInt("iWorkspaceCMSId"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setWaveNo(rs.getInt("iWaveNo"));
			dto.setCountryName(rs.getString("vCountryName"));
			dto.setCountryCodeName(rs.getString("CountryCodeName"));
			dto.setAgencyName(rs.getString("vAgencyName"));
			dto.setSubmissionInfoEUDtlId(rs.getString("SubmissionId"));
			dto.setSubmissionDescription(rs
					.getString("vSubmissionDescription"));
			dto.setDateOfSubmission(rs.getTimestamp("dDateOfSubmission"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setCMSTrackingNo(rs.getString("vCMSTrackingNo"));
			dto.setPublishCMSTrackingNo(rs
					.getString("vPublishCMSTrackingNo"));
			dto.setInventedName(rs.getString("vInventedName"));
			
			lstCMSSubInfo.add(dto);
			
		}
		rs.close();
		con.close();
		
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return lstCMSSubInfo;
}
public DTOSubmissionInfoEUSubDtl getWorkspaceRMSSubmissionInfo(String submissionId)
{
	
	DTOSubmissionInfoEUSubDtl dto = new DTOSubmissionInfoEUSubDtl();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		
		String where = " vSubmissionInfoEUDtlId = '"+submissionId+"' AND iWorkspaceCMSId = 0 ";
		ResultSet rs=datatable.getDataSet(con,"*","SubmissionInfoEUSubDtl" ,where,"");
		while(rs.next())
		{
			
			dto.setWorkspaceCMSId(rs.getInt("iWorkspaceCMSId"));
			
			dto.setSubmissionInfoEUDtlId(rs.getString("vSubmissionInfoEUDtlId"));
			dto.setSubmissionDescription(rs.getString("vSubmissionDescription"));
			dto.setDateOfSubmission(rs.getTimestamp("dDateOfSubmission"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			
		}
		rs.close();
		con.close();
		
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return dto;
}

}//main class ended
