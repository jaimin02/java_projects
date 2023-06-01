package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.docmgmt.dto.DTOPdfPublishDtl;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class PdfPublishDtl {
	DataTable datatable;
	
	public PdfPublishDtl()
	{
		datatable=new DataTable();
	}
	public boolean insertIntoPdfPublishDtl(DTOPdfPublishDtl dto,int DATAMODE)
	{
		try 
		{
			boolean returnValue;	
			Connection connection=ConnectionManager.ds.getConnection();
			CallableStatement statement=connection.prepareCall("{ call Proc_PdfPublishDtl(?,?,?,?,?,?,?,?) }");						
			statement.setString(1,dto.getWorkspaceId());
			statement.setString(2,dto.getProductname());
			statement.setString(3,dto.getVersion());
			statement.setString(4,dto.getCoverpage_productname());
			statement.setString(5,dto.getCoverpage_submittedby());
			statement.setString(6,dto.getCoverpage_submittedto());
			
			statement.setInt(7,dto.getModifyBy());
			statement.setInt(8,DATAMODE); // 1=insert,2=update
			returnValue=statement.execute();
			statement.close();
			connection.close();
			return returnValue;
		} 
		catch (SQLException e) 
		{			
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updatePdfPublishDtl(DTOPdfPublishDtl dto)
	{
		try 
		{
			boolean returnValue;	
			Connection connection=ConnectionManager.ds.getConnection();
			CallableStatement statement=connection.prepareCall("{ call Proc_PdfPublishDtl(?,?,?,?) }");					
			statement.setString(1,dto.getWorkspaceId());
			statement.setString(2,dto.getProductname());
			statement.setInt(3,dto.getModifyBy());
			statement.setInt(4,2); // 2=Update
			returnValue=statement.execute();
			statement.close();
			connection.close();
			return returnValue;
		} 
		catch (SQLException e) 
		{			
			e.printStackTrace();
		}
		return false;
	}
	public DTOPdfPublishDtl getPdfPublishDtlById (String WorkspaceId)
	{
		DTOPdfPublishDtl dto=null;
		try 
		{
			Connection connection=ConnectionManager.ds.getConnection();
			ResultSet rs;
			rs=datatable.getDataSet(connection,"*","PdfPublishDtl","cStatusIndi<>'D' and vWorkspaceId=" +WorkspaceId,"");
			if(rs.next())
			{				
				
				dto=new DTOPdfPublishDtl();
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setProductname(rs.getString("tProductName"));
				dto.setVersion(rs.getString("vVersion"));
				dto.setCoverpage_productname(rs.getString("vCoverPageProductName"));
				dto.setCoverpage_submittedby(rs.getString("vCoverPageSubmittedBy"));
				dto.setCoverpage_submittedto(rs.getString("vCoverPageSubmittedTo"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				
			}
			rs.close();
			connection.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return dto;
	}
}
