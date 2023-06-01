package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkspaceApplicationDetail;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class WorkspaceApplicationDetailMst {


		DataTable datatable;

		public WorkspaceApplicationDetailMst()
		{
			 datatable=new DataTable();
		}


		public void  insertWorkspaceApplicationDetail(DTOWorkspaceApplicationDetail dto,int Mode)
		{
			try
			{	    	
				
				System.out.println("Mode:"+ Mode);
				
				Connection con=ConnectionManager.ds.getConnection();
				CallableStatement proc =con.prepareCall("{ Call Insert_WorkspaceApplicationDetail(?,?,?,?,?,?,?,?,?)}");
				proc.setInt(1,dto.getApplicationId());
				proc.setString(2,dto.getWorkspaceId());
				proc.setString(3,dto.getSubmissionId());
				proc.setString(4,dto.getSequenceNumber());
				proc.setString(5, dto.getApplicationNumber());
				proc.setString(6,Character.toString(dto.getIsMainApplication()));
				proc.setInt(7,dto.getModifyBy());
				proc.setString(8,Character.toString(dto.getStatusIndi()));
				proc.setInt(9,Mode); 
				proc.executeUpdate();
				proc.close();
				con.close();
			}
			catch(SQLException e){
				e.printStackTrace();
		   }

		}
		 
		
	



	public Vector<DTOWorkspaceApplicationDetail> getWorkspaceApplicationDetail(String workspaceId) 
	{

		Vector<DTOWorkspaceApplicationDetail> workspaceApplicationDetaileVector = new Vector<DTOWorkspaceApplicationDetail>();
		Connection con = null;
		ResultSet rs = null;
	
		try 
		{		  
			
		 con = ConnectionManager.ds.getConnection();
		 rs=datatable.getDataSet(con,"*","workspaceApplicationDetail" ," vWorkspaceId = '"+workspaceId+"' ","iApplicationId");
			while(rs.next())
			{
								
				DTOWorkspaceApplicationDetail dto = new DTOWorkspaceApplicationDetail();
				dto.setApplicationId(rs.getInt("iApplicationId"));
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setSubmissionId(rs.getString("vSubmissionId"));
				dto.setSequenceNumber(rs.getString("vSequenceNumber"));
				dto.setApplicationNumber(rs.getString("vApplicationNumber"));
				dto.setIsMainApplication(rs.getString("cIsMainApplication").charAt(0));
				
				//dto.setRemark(rs.getString("vRemark"));
				
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				workspaceApplicationDetaileVector.addElement(dto);
			}
		}   
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return workspaceApplicationDetaileVector;
	}
	

	
}
