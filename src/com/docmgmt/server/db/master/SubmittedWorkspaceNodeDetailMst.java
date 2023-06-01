package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmittedWorkspaceNodeDetail;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;



public class SubmittedWorkspaceNodeDetailMst {

	ConnectionManager conMgr=new ConnectionManager(new Configuration());
	DataTable dataTable = new DataTable();
	

public void insertIntoSubmittedWorkspaceNodeDetail(ArrayList<DTOSubmittedWorkspaceNodeDetail> subNodeDtlLst)
{
	try
	{
		Connection con =ConnectionManager.ds.getConnection();
		
		CallableStatement proc = con.prepareCall("{ Call insert_submittedworkspacenodedetail(?,?,?,?,?)}");
		for(int i=0;i<subNodeDtlLst.size();i++)
		{
			DTOSubmittedWorkspaceNodeDetail dtoswnd = subNodeDtlLst.get(i);
			proc.setString(1, dtoswnd.getWorkspaceId());
			proc.setInt(2, dtoswnd.getNodeId());
			proc.setString(3, dtoswnd.getLastPublishVersion());
			proc.setString(4, dtoswnd.getSubmissionId());
			proc.setString(5,dtoswnd.getIndexId());
		    proc.execute();
		}
		proc.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	
}
public Vector getAttributeValueOfModifiedFile(String wsId, int nodeId)
{
	
	Vector data = new Vector();
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		StringBuffer query = new StringBuffer();
		query.append(" select max(vlastpublishversion) as vLastPublishVersion,indexid from submittedworkspacenodedetail");
		query.append(" where vWorkspaceId = '"+wsId+"' and iNodeId ="+nodeId+" group by indexid");
		PreparedStatement Pstmt=con.prepareStatement(query.toString());
		ResultSet rs=Pstmt.executeQuery();
		while(rs.next())
		{
			DTOSubmittedWorkspaceNodeDetail dto = new DTOSubmittedWorkspaceNodeDetail();
			dto.setLastPublishVersion(rs.getString("vLastPublishVersion"));
			dto.setIndexId(rs.getString("indexId"));
			data.addElement(dto);
			dto= null;
			
		}
		
		Pstmt.close();
		con.close();
		
	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return data;
}

public boolean submittedNodeIdDetail(String wsId, int nodeId)
{
	boolean data = false;
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs= dataTable.getDataSet(con, "iNodeId","SubmittedWorkspaceNodeDetail", "vWorkspaceId = '"+wsId+"' and iNodeId ="+nodeId+"", "");
		if(rs.next())
		{
			data = true;
		}
	
		rs.close();
		con.close();

	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return data;

}
	public ArrayList<DTOSubmittedWorkspaceNodeDetail> getSubmittedWorkspaceNodeList(String workspaceId,String submissionId)
	{
		ArrayList<DTOSubmittedWorkspaceNodeDetail> submittedWsNodeList = new ArrayList<DTOSubmittedWorkspaceNodeDetail>();
		Connection con = null;
		ResultSet rs = null;
		String whrCond="vworkspaceId = '"+ workspaceId +"' and submissionId='"+submissionId+"'";
		try
		{
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con,"*","SubmittedWorkspaceNodeDetail" ,whrCond , "");
			while(rs.next())
			{	
				DTOSubmittedWorkspaceNodeDetail dto = new DTOSubmittedWorkspaceNodeDetail();
				dto.setWorkspaceId(rs.getString("vworkspaceId"));
				dto.setSubmissionId(rs.getString("submissionId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setLastPublishVersion(rs.getString("vLastPublishVersion"));
				dto.setIndexId(rs.getString("indexId"));
				submittedWsNodeList.add(dto);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally
		{
			if ( rs!=null) try { rs.close();} catch (SQLException e) {e.printStackTrace();}
			if (con!=null) try {con.close();} catch (SQLException e) {e.printStackTrace();}			
		}
		return submittedWsNodeList;
	}
}//main class ended
