package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOInternalLabelMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;



public class InternalLabelMst {

	ConnectionManager conMgr=new ConnectionManager(new Configuration());
	DataTable dataTable = new DataTable();
	
public Vector viewLabelUsingWorkspaceId(String workspaceId)
{
	Vector<DTOInternalLabelMst> viewlabelvector = new Vector<DTOInternalLabelMst>();		
	try
    {	    	
		Connection con =ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ call Proc_viewInternalLabel(?)}");
		proc.setString(1, workspaceId);
		ResultSet rs =proc.executeQuery();
		while(rs.next())
		{
			DTOInternalLabelMst dto = new DTOInternalLabelMst();
			dto.setWorkspaceId(rs.getString("workspaceId"));
			dto.setLabelNo(rs.getInt("labelNo"));
			dto.setLabelId((rs.getString("labelId")));
			dto.setRemark(rs.getString("remark"));
			dto.setModifyBy(rs.getInt("modifyBy"));
			dto.setModifyOn(rs.getTimestamp("modifyOn"));
			dto.setUserName(rs.getString("userName"));
			viewlabelvector.add(dto);
			dto = null;
		}
		proc.close();
		rs.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
   }
		 return viewlabelvector;
}
	
public void  createInternalLabel(DTOInternalLabelMst dto) 
{
	try
	{	    	
		Connection con =ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call Insert_InternalLabel(?,?,?,?)}");
		proc.setString(1, dto.getWorkspaceId());
		proc.setString(2, dto.getLabelId() );
		proc.setString(3, dto.getRemark());
		proc.setInt(4, dto.getModifyBy());
	//	proc.setInt(5, mode);
		
		proc.execute();
		proc.close();
		con.close();
		
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	
}
	
public Vector viewNodeDetailUsingWsIdAndLabelNo(String workspaceId,int labelNo)
{
	Vector viewnodedetailvector = new Vector();		
	try
	{	    	
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ call Proc_test_viewInternalLabelName(?,?)}");
		proc.setString(1, workspaceId);
		proc.setInt(2, labelNo);
		ResultSet rs =proc.executeQuery();
		while(rs.next())
		{
	    	  DTOInternalLabelMst dto = new DTOInternalLabelMst();
	    	  dto.setWorkspaceId(rs.getString("workspaceId"));
	    	  dto.setNodeId(rs.getInt("nodeId"));
	    	  dto.setTranNo(rs.getInt("tranNo"));
	    	  dto.setNodeDisplayName(rs.getString("nodeDisplayName"));
	    	  dto.setWorkspaceDesc(rs.getString("workspaceDesc"));
	    	  dto.setBaseWorkFolder(rs.getString("baseWorkFolder"));
	    	  dto.setBasePublishFolder(rs.getString("basePublishFolder"));
	    	  dto.setFileName(rs.getString("fileName"));
	    	  dto.setFolderName(rs.getString("folderName"));
	    	  dto.setFileVersion(rs.getString("userDefineVersionId"));
	    	  viewnodedetailvector.add(dto);
	    	  dto = null;
		}
      rs.close();
      proc.close();
      con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	return viewnodedetailvector;
	
}
	
public ArrayList<DTOWorkSpaceNodeDetail> getDetailOfSubmission(String wsId, int labelNo)
{
	ArrayList<DTOWorkSpaceNodeDetail> internalLableMstList = new ArrayList<DTOWorkSpaceNodeDetail>();
	Connection con= null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*", "internallabelhistory", "vWorkspaceid = '"+wsId+"' and ilabelNo="+labelNo, "");
		while(rs.next())
		{
			DTOWorkSpaceNodeDetail dtoInternalLabelMst = new DTOWorkSpaceNodeDetail();
			dtoInternalLabelMst.setNodeId(rs.getInt("iNodeId"));
			dtoInternalLabelMst.setTranNo(rs.getInt("iTranNo"));
			internalLableMstList.add(dtoInternalLabelMst);
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
	return internalLableMstList;
}


public String lastLabel(String wsId)
{
	String lastLabelId = "";
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call proc_lastLabelNo(?)}");
		proc.setString(1, wsId);
		ResultSet rs = proc.executeQuery();
		if(rs.next())
		{
			lastLabelId = rs.getString("vLabelId");
		}
		proc.close();
		con.close();
		
	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return lastLabelId;
}

public boolean labelNameExist(String workspaceId, String labelId)
{
	boolean flag = false;
		try 
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs= dataTable.getDataSet(con,"vLabelId","internalLabelMst","vLabelId = '" + labelId +"' and vWorkspaceId = '"+workspaceId+"'","");
			if(rs.next())
			{
				flag = true;
			}
			rs.close();
	    	con.close();
	 	}
		catch(SQLException e){
			e.printStackTrace();
		}    	
	    	
		return flag;
}


public DTOInternalLabelMst getMaxWorkspaceLabel(String workspaceId)
{
	DTOInternalLabelMst maxLabel = new DTOInternalLabelMst();
	try 
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs= dataTable.getDataSet(con,"*","View_GetMaxWorkspaceLabel"," WorkspaceId = '"+workspaceId+"'","");
		if(rs.next())
		{
			maxLabel.setWorkspaceId(rs.getString("WorkspaceId"));
			maxLabel.setLabelNo(rs.getInt("LabelNo"));
			maxLabel.setLabelId(rs.getString("LabelId"));
			maxLabel.setRemark(rs.getString("Remark"));
			maxLabel.setModifyBy(rs.getInt("ModifyBy"));
			maxLabel.setModifyOn(rs.getTimestamp("ModifyOn"));
		}
		
		rs.close();
    	con.close();
 	}
	catch(SQLException e){
		e.printStackTrace();
	} 
	return maxLabel;
}

public void insertInsertInternalLableMst(String vWorkspaceId, int iUserId)
{
	DataTable dataTable = new DataTable();
	Connection con;
	
	try 
	{
		con = ConnectionManager.ds.getConnection();
		dataTable.executeDMLQuery(con, "INSERT INTO internallabelmst VALUES( '" + vWorkspaceId + "',1,'L0001',''," + iUserId + ",GETDATE())");
	}
	catch (SQLException e) 
	{
		e.printStackTrace();
	}

}
}//main class ended
