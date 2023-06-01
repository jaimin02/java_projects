package com.docmgmt.server.db.master;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.docmgmt.dto.DTODocReleaseTrack;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class DocReleaseTrack implements Serializable
{
	private static final long serialVersionUID = 6076075212805755169L;
	
	DataTable datatable;
	
	public DocReleaseTrack() 
	{
		datatable = new DataTable();
	}
	
	public void insertDocReleaseTrack(ArrayList<DTODocReleaseTrack> docReleaseTrackList)
	{
		
		Connection con = null;
		CallableStatement proc = null;
		try{
			con = ConnectionManager.ds.getConnection();
			/*
				nAutoID NUMERIC(18,0),
				vWorkspaceId VARCHAR(50),
				iNodeId INT,
				iQty INT,
				vStartId VARCHAR(50),
				vEndId VARCHAR(50),
				iReleasedBy INT,
				vComments VARCHAR(200)
			 */
			proc = con.prepareCall("{ Call Insert_DocReleaseTrack(?,?,?,?,?,?,?)}");
			if(docReleaseTrackList != null)
			{
				for (DTODocReleaseTrack dtoDocReleaseTrack : docReleaseTrackList) 
				{
					proc.setString(1, dtoDocReleaseTrack.getWorkspaceId());
					proc.setInt(2, dtoDocReleaseTrack.getParentNodeId());
					proc.setInt(3, dtoDocReleaseTrack.getQty());
					proc.setString(4, dtoDocReleaseTrack.getStartId());
					proc.setString(5, dtoDocReleaseTrack.getEndId());
					proc.setInt(6, dtoDocReleaseTrack.getReleasedBy());
					proc.setString(7, dtoDocReleaseTrack.getComments());
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
	
	public ArrayList<DTODocReleaseTrack> getDocReleaseTrack(String workspaceId, int nodeId)
	{
		ArrayList<DTODocReleaseTrack> documentReleaseTrackList = new ArrayList<DTODocReleaseTrack>();
		Connection con = null;
		ResultSet rs = null;
		String whereCond="";
		int para = 0;
		if (workspaceId != null && !workspaceId.trim().equals("") && !workspaceId.trim().equals("0"))
		{
			para = 1;
			whereCond = " vWorkspaceId = '"+workspaceId+"' ";
		}
		if (nodeId != 0) 
		{
			if (para ==1) 
				whereCond +=" AND ";
			whereCond += " iParentNodeId = "+ nodeId;
		}
		
		try{
			con = ConnectionManager.ds.getConnection();
			String select = "nAutoID,vWorkspaceId,iParentNodeId,iQty,vStartId,vEndId,dReleaseDate,"+
							"iReleasedBy,vComments,VworkspaceDesc,vRemark,vNodeName,"+
							"vNodeDisplayName,vFolderName,ReleasedByUser,vUserGroupName";
			rs = datatable.getDataSet(con, select, " View_DocReleaseTrack ",whereCond,"nAutoID");
			while(rs.next())
			{
				DTODocReleaseTrack dtoDocReleaseTrack = new DTODocReleaseTrack();
				dtoDocReleaseTrack.setAutoID(rs.getInt("nAutoID"));
				dtoDocReleaseTrack.setWorkspaceDesc(rs.getString("vWorkspaceId"));
				dtoDocReleaseTrack.setParentNodeId(rs.getInt("iParentNodeId"));
				dtoDocReleaseTrack.setQty(rs.getInt("iQty"));
				dtoDocReleaseTrack.setStartId(rs.getString("vStartId"));
				dtoDocReleaseTrack.setEndId(rs.getString("vEndId"));
				dtoDocReleaseTrack.setReleaseDate(rs.getTimestamp("dReleaseDate"));
				dtoDocReleaseTrack.setReleasedBy(rs.getInt("iReleasedBy"));
				dtoDocReleaseTrack.setComments(rs.getString("vComments"));
				dtoDocReleaseTrack.setWorkspaceDesc(rs.getString("VworkspaceDesc"));
				dtoDocReleaseTrack.setRemark(rs.getString("vRemark"));
				dtoDocReleaseTrack.setNodeName(rs.getString("vNodeName"));
				dtoDocReleaseTrack.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dtoDocReleaseTrack.setFolderName(rs.getString("vFolderName"));
				dtoDocReleaseTrack.setReleasedByUser(rs.getString("ReleasedByUser"));
				dtoDocReleaseTrack.setUserGroupName(rs.getString("vUserGroupName"));
				documentReleaseTrackList.add(dtoDocReleaseTrack);
			}
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		finally{
			try{if (rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
		return documentReleaseTrackList;
	}
}
