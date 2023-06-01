package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.docmgmt.dto.DTOCreateManualProject;
import com.docmgmt.dto.DTOManualModeSeqZipDtl;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class ManualModeSeqZipDtl {
	
	//ConnectionManager conMgr;
	static DataTable datatable = new DataTable();
	
	public ManualModeSeqZipDtl()
	{
		//ConnectionManager conMgr=new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}
	
	public static void insertManualModeSeqZipDtl(ArrayList<DTOManualModeSeqZipDtl> seqZipDtlList)
	{
		System.out.println("..Inserted....");
		try{
			Connection con=ConnectionManager.ds.getConnection();
	  		CallableStatement proc = con.prepareCall("{ Call Insert_ManualModeSeqZipDtl(?,?,?,?,?,?,?,?)}");
	  		for (Iterator<DTOManualModeSeqZipDtl> iterator = seqZipDtlList.iterator(); iterator.hasNext();) {
	  			DTOManualModeSeqZipDtl dto = iterator.next();
	  			proc.setLong(1,0);//@iManualModeSeqZipId
		  		proc.setString(2,dto.getWorkspaceId());
		  		proc.setString(3,dto.getSeqNo());
		  		proc.setString(4,dto.getZipFileName());
		  		proc.setString(5,dto.getZipFilePath());
		  		proc.setInt(6,dto.getUploadedBy());
		  		proc.setInt(7,dto.getModifyBy());
		  		proc.setInt(8,1);//@DATAOPMODE 
		  		proc.executeUpdate();
		  	}
	  		proc.close();
	  		con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public static ArrayList<DTOManualModeSeqZipDtl> getManualModeSeqZipDtl(String workspaceId){
		
		ArrayList<DTOManualModeSeqZipDtl> data = new ArrayList<DTOManualModeSeqZipDtl>();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			String fields= "iManualModeSeqZipId,vWorkspaceId,vWorkSpaceDesc,vSeqNo,vZipFileName,vZipFilePath,iUploadedBy,UploadedBy,iModifyBy,ModifyBy,dModifyOn,cStatusIndi";
			String whr ="vWorkspaceId = '"+workspaceId+"' ";
			ResultSet rs = datatable.getDataSet(con, fields, "View_ManualModeSeqZipDtl", whr, "vSeqNo");
			while(rs.next())
			{
				DTOManualModeSeqZipDtl dto = new DTOManualModeSeqZipDtl();
				dto.setManualModeSeqZipId(rs.getLong("iManualModeSeqZipId"));
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setWorkspaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setSeqNo(rs.getString("vSeqNo"));
				dto.setZipFileName(rs.getString("vZipFileName"));
				dto.setZipFilePath(rs.getString("vZipFilePath"));
				dto.setUploadedBy(rs.getInt("iUploadedBy"));
				dto.setUploadedByName(rs.getString("UploadedBy"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyByName(rs.getString("ModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				data.add(dto);
			}
			rs.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return data;
	}
	
	
public static ArrayList<DTOManualModeSeqZipDtl> getManualModeSeqZipDtlBySequenceNo(String workspaceId,String sequenceNo){
		
		ArrayList<DTOManualModeSeqZipDtl> data = new ArrayList<DTOManualModeSeqZipDtl>();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			String fields= "iManualModeSeqZipId,vWorkspaceId,vWorkSpaceDesc,vSeqNo,vZipFileName,vZipFilePath,iUploadedBy,UploadedBy,iModifyBy,ModifyBy,dModifyOn,cStatusIndi";
			String whr ="vWorkspaceId = '"+workspaceId+"' and vSeqNo='"+ sequenceNo+"' ";
			ResultSet rs = datatable.getDataSet(con, fields, "View_ManualModeSeqZipDtl", whr, "vSeqNo");
			while(rs.next())
			{
				DTOManualModeSeqZipDtl dto = new DTOManualModeSeqZipDtl();
				dto.setManualModeSeqZipId(rs.getLong("iManualModeSeqZipId"));
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setWorkspaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setSeqNo(rs.getString("vSeqNo"));
				dto.setZipFileName(rs.getString("vZipFileName"));
				dto.setZipFilePath(rs.getString("vZipFilePath"));
				dto.setUploadedBy(rs.getInt("iUploadedBy"));
				dto.setUploadedByName(rs.getString("UploadedBy"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyByName(rs.getString("ModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				data.add(dto);
			}
			rs.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static String  getMaxId(String tableName, String columnNmae)
	{
		String maxId = "";
		try
		{ 
			Connection connection = ConnectionManager.ds.getConnection();
			Statement statement = connection.createStatement();
			String query = "Select max(" + columnNmae + ") from " + tableName;
			ResultSet rs = statement.executeQuery(query);
		
			while(rs.next())
			{
				maxId = rs.getString(1);
			}
			connection.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return maxId;
	}

	public void createManualProject
	(
		DTOCreateManualProject objDtoCreateManualProject) {
		try 
		{
			CallableStatement proc = null;
			Connection connection = ConnectionManager.ds.getConnection();

			proc = connection.prepareCall("{call Proc_createManualProject(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setString(1, objDtoCreateManualProject.getWorkspaceId());
			proc.setString(2, objDtoCreateManualProject.getNodeName());
			proc.setString(3, objDtoCreateManualProject.getParentNodeName());

			proc.setLong(4, objDtoCreateManualProject.getParentNodeId());
			proc.setString(5, objDtoCreateManualProject.getFileName());
			proc.setString(6, objDtoCreateManualProject.getDisplayName());
			proc.setString(7, objDtoCreateManualProject.getSequenceNumber());
			
			proc.setString(8, objDtoCreateManualProject.getOperation());
			proc.setInt(9, objDtoCreateManualProject.getUserId());
			proc.setLong(10, objDtoCreateManualProject.getUserGroupCode());
			proc.setString(11, objDtoCreateManualProject.getLocationName());
			proc.setString(12, objDtoCreateManualProject.getLatestSequence());
			proc.setString(13, objDtoCreateManualProject.getSubmissionId());
			proc.setString(14, objDtoCreateManualProject.getIndexId());

			proc.execute();
			proc.close();
			connection.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
