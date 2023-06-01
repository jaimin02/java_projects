package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class WorkSpaceNodeVersionHistory 
{
	DataTable dataTable;
	
	public WorkSpaceNodeVersionHistory()
	{
		dataTable = new DataTable();
	}
	
public Vector<DTOWorkSpaceNodeVersionHistory> getMaxWsNodeVersionDetail(DTOWorkSpaceNodeVersionHistory objWSNodeVersionHistory)
{
	Vector<DTOWorkSpaceNodeVersionHistory> wsNodeVersion = new Vector<DTOWorkSpaceNodeVersionHistory>(); 
	Connection con = null;
	ResultSet rs = null;
	try
	{
		String whr=" WorkspaceId ='"+objWSNodeVersionHistory.getWorkspaceId()+"' and NodeId="+objWSNodeVersionHistory.getNodeId()+" and isLastClosed='Y'";
		con = ConnectionManager.ds.getConnection();
		String Fields="WorkspaceId,NodeId,TranNo,UserDefineVersionId,isPublished,isDownloaded," +
		"islastClosed,executedon,ModifyOn,loginName,fileversionid,statusIndi,fileName";
		
		rs=dataTable.getDataSet(con,Fields,"view_NodeVersionHistoryDetail" ,whr,"");
		while(rs.next())
		{
			DTOWorkSpaceNodeVersionHistory dto = new DTOWorkSpaceNodeVersionHistory();
			dto.setWorkspaceId(rs.getString("WorkspaceId"));					//workspaceId from workpsaceNodeVersionHistory
			dto.setNodeId(rs.getInt("NodeId"));									//nodeId from workspaceNodeVersionHistory
			dto.setTranNo(rs.getInt("TranNo"));									//tranNo from workspaceNodeVersionHistory
			dto.setUserDefineVersionId(rs.getString("UserDefineVersionId"));	//userDefineVersionId from workspaceNodeVersionHistory
			dto.setPublished(rs.getString("isPublished").charAt(0));			//published from workspaceNodeVersionHistory
			dto.setDownloaded(rs.getString("isDownloaded").charAt(0));			//downLoaded from workspaceNodeVersionHistory
			dto.setLastClosed(rs.getString("islastClosed").charAt(0));			//lastClosed from workspaceNodeVersionHistory
			dto.setExecutedOn(rs.getTimestamp("executedon"));					//executedOn from workspaceNodeVersionHistory		
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));						//modifyOn from workspaceNodeVersionHistory
			dto.setUserName(rs.getString("loginName"));							//loginName from userMst
			dto.setFileVersionId(rs.getString("fileversionid"));				// fileVersionId from workspaceNodeVersionHistory
			dto.setStatusIndi(rs.getString("statusIndi").charAt(0)); 			//statusIndi from workspaceNodeVersionHistory
			dto.setFileName(rs.getString("fileName")); 
			wsNodeVersion.addElement(dto);
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return wsNodeVersion;
}
	
public void insertWorkspaceNodeVersionHistory(DTOWorkSpaceNodeVersionHistory dto) 
{
	insertWorkspaceNodeVersionHistory(dto, true); //true for autoversion
}

public void insertWorkspaceNodeVersionHistory(DTOWorkSpaceNodeVersionHistory dto,boolean autoVersion)
{
	setLastClosedFlag(dto.getWorkspaceId(),dto.getNodeId(),-1,-1);
	Connection con = null;
	CallableStatement cs = null;
	try 
	{
		con = ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_WorkspaceNodeVersionHistory(?,?,?,?,?,?,?,?,?,?,?)}");
		cs.setString(1,dto.getWorkspaceId());
		cs.setInt(2, dto.getNodeId());
		cs.setInt(3,dto.getTranNo());
		cs.setString(4,Character.toString(dto.getPublished()));
		cs.setString(5, "Y");
		cs.setString(6,Character.toString(dto.getDownloaded()));
		cs.setString(7,dto.getActivityId());
		cs.setInt(8, dto.getModifyBy());
		cs.setInt(9, dto.getExecutedBy());
		cs.setString(10, dto.getUserDefineVersionId());
		if(autoVersion)
		{
			cs.setString(11, "Y"); //autoversion
		}
		else
		{
			cs.setString(11, "N");
		}
		cs.execute();
	}
	catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}

public void setLastClosedFlag(String workspaceId,int nodeId,int versiontranNo,int tranNo)
{
	Connection con = null;
	CallableStatement cs = null;
     try
     {	
    	 con = ConnectionManager.ds.getConnection();   
    	 
    	 if(versiontranNo != -1)
    	 {
    		 cs=con.prepareCall("{call Proc_setLastClosedFlag(?,?,?,?)}");
		 }	
    	 else
    	 {
    		 cs=con.prepareCall("{call Proc_setLastClosedFlag(?,?)}");
    	 }	
    	 cs.setString(1,workspaceId);
    	 cs.setInt(2,nodeId);
    	 if(versiontranNo != -1)
    	 {	
    		 cs.setInt(3,versiontranNo);
    		 cs.setInt(4,tranNo);
		 } 
    	 cs.execute();
     }catch(Exception e){
    	 e.printStackTrace();
     }finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	 }
}		
 		 
public int getMaxTranNo(DTOWorkSpaceMst dto)
{
	int maxTranNo=0;
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"max(iTranNo) as maxTranNo" ,"workspacenodeversionhistory", "vWorkspaceId ='"+dto.getWorkSpaceId()+"' and iNodeId ="+dto.getNodeId(), "");
		if(rs.next())
		{
			maxTranNo=rs.getInt("maxTranNo");
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return maxTranNo;
}		 
			 
public Vector<DTOWorkSpaceNodeVersionHistory> getFileVersionAndCommentReport(DTOWorkSpaceNodeDetail obj)
{
	Vector<DTOWorkSpaceNodeVersionHistory> data = new Vector<DTOWorkSpaceNodeVersionHistory>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{
		StringBuffer query = new StringBuffer();
		query.append("WorkSpaceId= '"+obj.getWorkspaceId()+"'");
		
		if(obj.getNodeId() > 0)
			query.append(" and NodeId="+obj.getNodeId());
		if(obj.getUserCode() > 0)
			query.append(" and ModifyBy ="+obj.getUserCode());
			
		query.append("   and AttrName='Comments on file' ");
		con = ConnectionManager.ds.getConnection();
		String Fields="workSpaceDesc,nodeDisplayName,userdefineversionid," +
		"filename,attrValue,loginname,tranno,modifyon,workspaceid,nodeid";
		rs=dataTable.getDataSet(con, Fields, "view_NodeVersionHistoryDetail",query.toString(), "");
		while (rs.next()) 
		{				
			DTOWorkSpaceNodeVersionHistory dto = new DTOWorkSpaceNodeVersionHistory();
			dto.setWorkspaceDesc(rs.getString("workSpaceDesc"));
			dto.setNodeDisplayName(rs.getString("nodeDisplayName"));
			dto.setUserDefineVersionId(rs.getString("userdefineversionid"));
			dto.setFileName(rs.getString("filename"));
			dto.setAttrValue(rs.getString("attrValue"));
			dto.setUserName(rs.getString("loginname"));
			dto.setTranNo(rs.getInt("tranno"));
			Timestamp ts = new Timestamp(rs.getTimestamp("modifyon").getTime());
			dto.setModifyOn(ts);				
			dto.setWorkspaceId(rs.getString("workspaceid"));
			dto.setNodeId(rs.getInt("nodeid"));
		    data.addElement(dto);
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return data;
}

public Vector<DTOWorkSpaceNodeVersionHistory> getAuditTrailReport(DTOWorkSpaceNodeDetail obj)
{

	Vector<DTOWorkSpaceNodeVersionHistory> data = new Vector<DTOWorkSpaceNodeVersionHistory>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{
		StringBuffer query = new StringBuffer();
		query.append("  WorkSpaceId =' "+obj.getWorkspaceId()+"'");
		if(obj.getNodeId() > 0)
    		query.append(" and NodeId = "+obj.getNodeId());
    	if(obj.getUserCode() > 0)
    		query.append("  and ModifyBy = "+obj.getUserCode());
    	query.append(" and  AttrforIndiId = '0003' ");
    	con = ConnectionManager.ds.getConnection();
    	rs=dataTable.getDataSet(con, "*", "view_NodeVersionHistoryDetail",query.toString(),"WorkspaceId, NodeId , ModifyBy, TranNo desc" );
		while (rs.next())  
		{
			DTOWorkSpaceNodeVersionHistory dto = new DTOWorkSpaceNodeVersionHistory();
			dto.setTranNo(rs.getInt("TranNo"));
			dto.setFileVersionId(rs.getString("fileVersionId"));
			dto.setUserDefineVersionId(rs.getString("userDefinedVersionId"));
			dto.setFileName(rs.getString("fileName"));
			dto.setAttrValue(rs.getString("attrvalue"));
			dto.setAttrName(rs.getString("attrName"));
			dto.setWorkspaceDesc(rs.getString("workSpaceDesc"));
			dto.setNodeDisplayName(rs.getString("nodeDisplayName"));
			dto.setUserName(rs.getString("loginname"));
			Timestamp ts = new Timestamp(rs.getTimestamp("modifyon").getTime());
			dto.setModifyOn(ts);				
			dto.setWorkspaceId(rs.getString("workspaceid"));
			dto.setNodeId(rs.getInt("nodeid"));
			dto.setModifyBy(rs.getInt("Modifyby"));
		    data.addElement(dto);
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return data;
}

public Vector<DTOWorkSpaceNodeVersionHistory> getWorkSpaceNodeVersionDetail(DTOWorkSpaceNodeVersionHistory objWSNodeVersionHistory)  
{

	Vector<DTOWorkSpaceNodeVersionHistory> wsNodeVersion = new Vector<DTOWorkSpaceNodeVersionHistory>(); 
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		String fields="Distinct WorkspaceId,NodeId,TranNo,UserDefineVersionId,isPublished," +
				"isDownloaded,islastClosed,executedon,ModifyOn,loginName,fileversionid,statusIndi,fileName";
		rs=dataTable.getDataSet(con,fields, "view_NodeVersionHistoryDetail", "WorkspaceId ='"+objWSNodeVersionHistory.getWorkspaceId()+"' and NodeId="+objWSNodeVersionHistory.getNodeId(),"FileVersionId" );
		while(rs.next())
		{
			DTOWorkSpaceNodeVersionHistory dto = new DTOWorkSpaceNodeVersionHistory();
			dto.setWorkspaceId(rs.getString("WorkspaceId"));			//workspaceId from workpsaceNodeVersionHistory
			dto.setNodeId(rs.getInt("NodeId"));					//nodeId from workspaceNodeVersionHistory
			dto.setTranNo(rs.getInt("TranNo"));					//tranNo from workspaceNodeVersionHistory
			dto.setUserDefineVersionId(rs.getString("UserDefineVersionId"));	//userDefineVersionId from workspaceNodeVersionHistory
			dto.setPublished(rs.getString("isPublished").charAt(0));	//published from workspaceNodeVersionHistory
			dto.setDownloaded(rs.getString("isDownloaded").charAt(0));	//downLoaded from workspaceNodeVersionHistory
			dto.setLastClosed(rs.getString("islastClosed").charAt(0));	//lastClosed from workspaceNodeVersionHistory
			dto.setExecutedOn(rs.getTimestamp("executedon"));			//executedOn from workspaceNodeVersionHistory		
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));			//modifyOn from workspaceNodeVersionHistory
			dto.setUserName(rs.getString("loginName"));				//loginName from userMst
			dto.setFileVersionId(rs.getString("fileversionid"));			// fileVersionId from workspaceNodeVersionHistory
			dto.setStatusIndi(rs.getString("statusIndi").charAt(0)); 	//statusIndi from workspaceNodeVersionHistory
			dto.setFileName(rs.getString("fileName")); 				// fileName from workspaceNodeHistory
			wsNodeVersion.addElement(dto);
		}
    }catch(Exception e){
    	e.printStackTrace();
    }finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return wsNodeVersion;
}

public Vector<DTOWorkSpaceNodeVersionHistory> getNodeVersionHistory(DTOWorkSpaceNodeVersionHistory dto) 
{
	Vector<DTOWorkSpaceNodeVersionHistory> data = new Vector<DTOWorkSpaceNodeVersionHistory>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*","view_NodeVersionHistoryDetail" ,"WorkspaceId='"+dto.getWorkspaceId()+"' "+" and NodeId="+dto.getNodeId()+" and FileVersionId='"+dto.getFileVersionId()+"'","");
		while(rs.next())
		{
			dto.setWorkspaceId(rs.getString("WorkspaceId"));
			dto.setNodeId(rs.getInt("NodeId"));
			dto.setTranNo(rs.getInt("TranNo"));
			dto.setFileVersionId(rs.getString("fileversionid"));
			dto.setPublished(rs.getString("isPublished").charAt(0));
			dto.setLastClosed(rs.getString("islastClosed").charAt(0));
			dto.setDownloaded(rs.getString("isDownloaded").charAt(0));
			dto.setActivityId(rs.getString("ActivityId"));
			dto.setModifyBy(rs.getInt("modifyBy"));
			dto.setExecutedBy(rs.getInt("executeBy"));
			dto.setExecutedOn(rs.getTimestamp("executedon"));
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));
			dto.setStatusIndi(rs.getString("statusIndi").charAt(0));
			dto.setUserDefineVersionId(rs.getString("UserDefineVersionId"));
			data.addElement(dto);
		}
	}   
	catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return data;
}

public DTOWorkSpaceNodeVersionHistory getNodeVersionHistoryByTranNo(String workspaceId,int nodeId,int tranNo) 
{
	DTOWorkSpaceNodeVersionHistory dto = new DTOWorkSpaceNodeVersionHistory();
	Connection con = null;
	ResultSet rs = null;
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*","workspaceNodeVersionHistory" ,"vWorkspaceId='"+workspaceId+"' "+" and iNodeId="+nodeId+" and iTranNo="+tranNo+" ","");
		if(rs.next())
		{
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setTranNo(rs.getInt("iTranNo"));
			dto.setFileVersionId(rs.getString("vfileversionid"));
			dto.setPublished(rs.getString("isPublished").charAt(0));
			dto.setLastClosed(rs.getString("islastClosed").charAt(0));
			dto.setDownloaded(rs.getString("isDownloaded").charAt(0));
			dto.setActivityId(rs.getString("vActivityId"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setExecutedBy(rs.getInt("iExecutedBy"));
			dto.setExecutedOn(rs.getTimestamp("dExecutedon"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
		}
	}   
	catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return dto;
}

}//main class ended
