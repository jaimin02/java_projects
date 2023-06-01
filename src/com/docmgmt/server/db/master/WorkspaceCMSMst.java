package com.docmgmt.server.db.master;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.docmgmt.dto.DTOWorkspaceCMSMst;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class WorkspaceCMSMst {

	ConnectionManager conMgr;
	DataTable datatable;
	
	public WorkspaceCMSMst()
	{
		 conMgr=new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}
	
public void  insertWorkspaceCMS(DTOWorkspaceCMSMst dto,int Mode)
{
	try
	{	    	
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call Insert_WorkspaceCMSMst(?,?,?,?,?,?,?,?,?,?)}");
		proc.setInt(1, dto.getWorkspaceCMSId());
		proc.setString(2,dto.getWorkspaceId());
		proc.setString(3,dto.getCountryCode());
		proc.setString(4,dto.getAgencyCode());
		proc.setInt(5,dto.getWaveNo());
		proc.setInt(6,dto.getModifyBy());
		proc.setString(7,Character.toString(dto.getStatusIndi()));
		proc.setString(8, dto.getCmsTrackNum());
		proc.setString(9, dto.getInventedName());
		proc.setInt(10,Mode); 
		proc.executeUpdate();
		proc.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
   }

}
 
public ArrayList<DTOWorkspaceCMSMst> getWorkspaceCMSInfo(String workspaceId)
{
	ArrayList<DTOWorkspaceCMSMst> lstCMS = new ArrayList<DTOWorkspaceCMSMst>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=datatable.getDataSet(con,"*","View_WorkspaceCMSDtl" ," vWorkspaceId = '"+workspaceId+"' ","vCountryName");
		while(rs.next())
		{
			DTOWorkspaceCMSMst dto = new DTOWorkspaceCMSMst();
			dto.setWorkspaceCMSId(rs.getInt("iWorkspaceCMSId"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setCountryCode(rs.getString("vCountryCode"));
			dto.setWaveNo(rs.getInt("iWaveNo"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			dto.setCountryName(rs.getString("vCountryName"));
			dto.setCountryRegion(rs.getString("CountryRegion"));
			dto.setCountryCodeName(rs.getString("CountryCodeName"));
			dto.setAgencyCode(rs.getString("vAgencyCode"));
			dto.setAgencyName(rs.getString("vAgencyName"));
			dto.setAgencyFullName(rs.getString("vAgencyFullName"));
			dto.setCmsTrackNum(rs.getString("vCMSTrackingNo"));
			dto.setInventedName(rs.getString("vInventedName"));
			
			lstCMS.add(dto);
			
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return lstCMS;
}
    
    
}//main class ended
