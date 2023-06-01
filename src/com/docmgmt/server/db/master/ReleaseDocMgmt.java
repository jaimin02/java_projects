package com.docmgmt.server.db.master;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.docmgmt.dto.DTOReleaseDocMgmt;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class ReleaseDocMgmt implements Serializable
{
	private static final long serialVersionUID = 204708515759252431L;
DataTable datatable;
	
	public ReleaseDocMgmt() 
	{
		datatable = new DataTable();
	}
	
	public void insertReleaseDocMgmt(ArrayList<DTOReleaseDocMgmt> releaseDocMgmtList)
	{
		
		Connection con = null;
		CallableStatement proc = null;
		try{
			con = ConnectionManager.ds.getConnection();
			/*
				vWorkspaceId,
				iNodeId,
				cStatusIndi,
				iModifyBy,
				vComments
			 */
			proc = con.prepareCall("{ Call Insert_ReleaseDocMgmt(?,?,?,?,?,?,?)}");
			if(releaseDocMgmtList != null)
			{
				for (DTOReleaseDocMgmt dtoReleaseDocMgmt : releaseDocMgmtList) 
				{
					proc.setString(1, dtoReleaseDocMgmt.getWorkspaceId());
					proc.setInt(2, dtoReleaseDocMgmt.getNodeId());
					proc.setString(3,Character.toString(dtoReleaseDocMgmt.getStatusIndi()));
					proc.setInt(4, dtoReleaseDocMgmt.getModifyBy());
					proc.setString(5, dtoReleaseDocMgmt.getComments());
					proc.setString(6, dtoReleaseDocMgmt.getStage());
					proc.setString(7, dtoReleaseDocMgmt.getDocVersion());
					proc.execute();
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try{if (proc != null)proc.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
	}
	
	public ArrayList<DTOReleaseDocMgmt> getReleaseDocDtl(String workspaceId,ArrayList<Integer> nodeIdList)
	{
		ArrayList<DTOReleaseDocMgmt> releaseDocMgmtList = new ArrayList<DTOReleaseDocMgmt>();
		Connection con = null;
		ResultSet rs = null;
		String whereCond="";
		int para = 0;
		whereCond = " ";
		String ndIds="";
		for (int itrRelDocMgmt = 0; itrRelDocMgmt < nodeIdList.size() ; itrRelDocMgmt++)
		{
			int ndId = nodeIdList.get(itrRelDocMgmt);
			if (ndId > 0) 
				ndIds +=nodeIdList.get(itrRelDocMgmt)+ ",";		
		}
		if (ndIds.endsWith(","))
			ndIds = ndIds.substring(0, ndIds.length()-1);
		if (workspaceId != null) 
		{
			whereCond += " vWorkspaceId = '"+workspaceId+"'";
			para = 1;
		}
		if (ndIds.length() > 0) 
		{
			if (para == 1) 
				whereCond += " AND ";
			whereCond += " iNodeId in ("+ndIds+") ";
		}
		try{
			con = ConnectionManager.ds.getConnection();
			String select = "nAutoID,vWorkspaceId,iNodeId,cStatusIndi,iModifyBy,vComments,dModifyOn,vStage,vDocVersion," +
							"vWorkspaceDesc,vRemark,vNodeName,vNodeDisplayName,vFolderName,iParentNodeId," +
							"ModifyByUser,vUserGroupName";
			
			rs = datatable.getDataSet(con, select, " View_ReleaseDocMgmt ",whereCond,"nAutoID");
			while(rs.next())
			{
				DTOReleaseDocMgmt dtoReleaseDocMgmt = new DTOReleaseDocMgmt();
				dtoReleaseDocMgmt.setAutoID(rs.getInt("nAutoID"));
				dtoReleaseDocMgmt.setWorkspaceDesc(rs.getString("vWorkspaceId"));
				dtoReleaseDocMgmt.setNodeId(rs.getInt("iNodeId"));
				dtoReleaseDocMgmt.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				dtoReleaseDocMgmt.setModifyBy(rs.getInt("iModifyBy"));
				dtoReleaseDocMgmt.setComments(rs.getString("vComments"));
				dtoReleaseDocMgmt.setModifyOn(rs.getTimestamp("dModifyOn"));
				dtoReleaseDocMgmt.setStage(rs.getString("vStage"));
				dtoReleaseDocMgmt.setDocVersion(rs.getString("vDocVersion"));
				dtoReleaseDocMgmt.setWorkspaceDesc(rs.getString("vWorkspaceDesc"));
				dtoReleaseDocMgmt.setRemark(rs.getString("vRemark"));
				dtoReleaseDocMgmt.setNodeName(rs.getString("vNodeName"));
				dtoReleaseDocMgmt.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dtoReleaseDocMgmt.setFolderName(rs.getString("vFolderName"));
				dtoReleaseDocMgmt.setParentNodeId(rs.getInt("iParentNodeId"));
				dtoReleaseDocMgmt.setModifyByUser(rs.getString("ModifyByUser"));
				dtoReleaseDocMgmt.setUserGroupName(rs.getString("vUserGroupName"));
				releaseDocMgmtList.add(dtoReleaseDocMgmt);
			}
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		finally{
			try{if (rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
		return releaseDocMgmtList;
	}
}