package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class WorkSpaceNodeAttributeHistory 
{
	
	DataTable dataTable; 
	
	public WorkSpaceNodeAttributeHistory()
	{
		dataTable = new DataTable();
	}

	

public Vector<DTOWorkSpaceNodeAttrHistory> getUserDefinedWorkspaceNodeAttrHistory(String workspaceId,int NodeId)
{
	Vector<DTOWorkSpaceNodeAttrHistory> nodeAttrHistroy = new Vector<DTOWorkSpaceNodeAttrHistory>();
	try
	{
		Connection con=ConnectionManager.ds.getConnection();
		String Fields="Distinct vworkspaceId,iNodeId,iAttrid,vAttrName,vAttrValue";
		String Where =" vWorkspaceId='"+workspaceId+"'  and iattrid !=-999 and iNodeId="+NodeId;
		ResultSet resultSet=dataTable.getDataSet(con,Fields,"View_CommonWorkspaceDetail", Where,"iAttrId");
		while(resultSet.next())
		{
			DTOWorkSpaceNodeAttrHistory workSpaceNodeAttrHistory = new DTOWorkSpaceNodeAttrHistory();
			workSpaceNodeAttrHistory.setWorkSpaceId(resultSet.getString("vworkspaceId"));
			workSpaceNodeAttrHistory.setNodeId(resultSet.getInt("inodeId"));
			workSpaceNodeAttrHistory.setAttrId(resultSet.getInt("iattrId"));
			workSpaceNodeAttrHistory.setAttrName(resultSet.getString("vattrName"));
			workSpaceNodeAttrHistory.setAttrValue(resultSet.getString("vattrValue"));
			nodeAttrHistroy.addElement(workSpaceNodeAttrHistory);
			workSpaceNodeAttrHistory = null;
		}	        	
		resultSet.close();
		con.close();
	
	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return 	nodeAttrHistroy;
}
	
public void InsertUpdateNodeAttrHistory(Vector<DTOWorkSpaceNodeAttrHistory> attrHistoryVect) 
{
	
	try 
	{	
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs=con.prepareCall("{call Insert_NodeAttrHistory(?,?,?,?,?,?,?)}");
		for(int i = 0; i < attrHistoryVect.size(); i++) 
		{
			DTOWorkSpaceNodeAttrHistory dto  = attrHistoryVect.elementAt(i);
			cs.setString(1, dto.getWorkSpaceId());
			cs.setInt(2, dto.getNodeId());
			cs.setInt(3, dto.getTranNo());
			cs.setInt(4, dto.getAttrId());
			cs.setString(5, dto.getAttrValue());
			cs.setInt(6,dto.getModifyBy());
			cs.setString(7,"N");
			cs.execute();
			dto=null;
		}
		cs.close();
		con.close();
	
	}catch(SQLException e){
		e.printStackTrace();
	}
}
 
public Vector<DTOWorkSpaceNodeAttrHistory> getAttributesForNodeForPublish(int NodeId, String workSpaceId,int labelno, String AttrForIndiId)
{
	Vector<DTOWorkSpaceNodeAttrHistory> nodeAttributesDtl = new Vector<DTOWorkSpaceNodeAttrHistory>();
 	try
 	{
 		Connection con = ConnectionManager.ds.getConnection();
 		CallableStatement cs=con.prepareCall("{call Proc_AttributesForNodeForPublish(?,?,?,?)}");
 		cs.setString(1,workSpaceId);
 		cs.setInt(2,NodeId);
 		cs.setInt(3, labelno);
 		cs.setString(4, AttrForIndiId);
 		ResultSet resultSet=cs.executeQuery();
 		while (resultSet.next())  
 		{
 			DTOWorkSpaceNodeAttrHistory workSpaceNodeAttrHistory=new DTOWorkSpaceNodeAttrHistory();
 			workSpaceNodeAttrHistory.setWorkSpaceId(resultSet.getString("vWorkspaceId"));
 			workSpaceNodeAttrHistory.setNodeId(resultSet.getInt("iNodeId"));
 			workSpaceNodeAttrHistory.setTranNo(resultSet.getInt("iTranNo"));
 			workSpaceNodeAttrHistory.setAttrId(resultSet.getInt("iAttrId"));
 			workSpaceNodeAttrHistory.setAttrName(resultSet.getString("vAttrName"));
 			workSpaceNodeAttrHistory.setAttrValue(resultSet.getString("vAttrValue"));
 			workSpaceNodeAttrHistory.setRemark(resultSet.getString("vRemark"));
 			nodeAttributesDtl.addElement(workSpaceNodeAttrHistory);
	    }             
 		resultSet.close();
 		cs.close();
 		con.close();
	
 	}catch(SQLException e){
		e.printStackTrace();
 	}		
		
 	return nodeAttributesDtl;
} 
 
public void insertNodeAttrHistory(Vector<DTOWorkSpaceNodeAttrHistory> attrHistoryVect) 
{
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement proc=con.prepareCall("{call Insert_workspaceNodeAttrHistory(?,?,?,?,?,?)}");
		for(int i = 0; i < attrHistoryVect.size(); i++) 
		{
			DTOWorkSpaceNodeAttrHistory dto  = attrHistoryVect.elementAt(i);
			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			proc.setInt(3, dto.getTranNo());
			proc.setInt(4, dto.getAttrId());
			proc.setString(5, dto.getAttrValue());
			proc.setInt(6, dto.getModifyBy());
			proc.execute();	 			
		}
		proc.close();
		con.close();
   		
	}catch(SQLException e){
		e.printStackTrace();
	}
}
 
public Vector<DTOWorkSpaceNodeAttrHistory> getWorkspaceNodeAttrHistorybyTranNo(String workspaceId,int nodeId,int tranNo) 
{
	 DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
     Vector<DTOWorkSpaceNodeAttrHistory> nodeAttrHistory = new Vector<DTOWorkSpaceNodeAttrHistory>();
     Connection con = null;
     ResultSet rs = null;
     ArrayList<String> time = new ArrayList<String>();
 	 String locationName = ActionContext.getContext().getSession().get("locationname").toString();
   	 String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
     try
     {
    	 String Where = "WorkspaceId ='"+workspaceId+"' and NodeId = "+nodeId+" and TranNo = "+tranNo;
    	 con = ConnectionManager.ds.getConnection();
    	 rs = dataTable.getDataSet(con,"WorkspaceId,NodeId,AttrId,AttrforIndiId,TranNo,AttrValue,ModifyBy,AttrName,ModifyOn", "view_NodeVersionHistoryDetail", Where, "attrname");
    	 while(rs.next())
    	 {
    		 DTOWorkSpaceNodeAttrHistory dto = new DTOWorkSpaceNodeAttrHistory();
    		 dto.setWorkSpaceId(rs.getString("WorkspaceId"));		
    		 dto.setNodeId(rs.getInt("NodeId"));				
    		 dto.setTranNo(rs.getInt("TranNo"));					
    		 dto.setAttrId(rs.getInt("AttrId"));				
    		 dto.setAttrValue(rs.getString("AttrValue"));			
    		 dto.setModifyBy(rs.getInt("ModifyBy"));
    		 dto.setAttrName(rs.getString("AttrName"));
    		 dto.setModifyOn(rs.getTimestamp("ModifyOn"));	
    	if(countryCode.equalsIgnoreCase("IND")){
  			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
  			dto.setISTDateTime(time.get(0));
  		}
  		else{
  			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
  			dto.setISTDateTime(time.get(0));
  			dto.setESTDateTime(time.get(1));
  		}
    		 dto.setAttrForIndiId(rs.getString("AttrforIndiId"));
    		 nodeAttrHistory.addElement(dto);
    	}
     }catch(Exception e) {
    	 e.printStackTrace();
     }finally{
    	 try{if(rs != null){rs.close();}}catch(Exception e){e.printStackTrace();}
    	 try{if(con != null){con.close();}}catch(Exception e){e.printStackTrace();}
     }
     return nodeAttrHistory;    
} 
 
public void UpdateTranNoForStageInAttrHistory(String WorkspaceId,int NodeId,int TranNo)
{
 	try
 	{
 		Connection con = ConnectionManager.ds.getConnection();
 		CallableStatement proc=con.prepareCall("{call Proc_UpdateTranNoForStageInAttrHistory(?,?,?)}");
 		proc.setString(1, WorkspaceId);
 		proc.setInt(2, NodeId);
 		proc.setInt(3, TranNo);
 		proc.execute();	 			
 		proc.close();
 		con.close();
	}
 	
 	catch(SQLException e){
		e.printStackTrace();
	}
	
}

public void  copyLeafAttributes(String srcWsId,String destWsId,int srcNodeId,int destNodeId,int userId,int TranNo)
{
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
 		CallableStatement proc=con.prepareCall("{call Proc_CopyAttrForRepository(?,?,?,?,?,?)}");
 		proc.setString(1, srcWsId);
 		proc.setInt(2,srcNodeId);
 		proc.setString(3, destWsId);
 		proc.setInt(4, destNodeId);
 		proc.setInt(5, TranNo);
 		proc.setInt(6, userId);
 		proc.execute();
 		proc.close();
 		con.close();
 	}
	catch(SQLException e){
		e.printStackTrace();
	}
}


public void updateSTFVersion(DTOWorkSpaceNodeAttrHistory dto)
{
 	try
 	{
 		Connection con = ConnectionManager.ds.getConnection();
 		CallableStatement proc=con.prepareCall("{call proc_updateSTFVersion(?,?,?)}");
 		proc.setString(1, dto.getWorkSpaceId());
		proc.setInt(2, dto.getNodeId());
		proc.setInt(3, dto.getTranNo());
		proc.execute();	 
		proc.close();
		con.close();
		
	}
 	
 	catch(SQLException e){
		e.printStackTrace();
	}
	
}


public String checkDeleteOperation(String wsId, int nodeId)
{
	String attrValue = "";
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement proc=con.prepareCall("{call proc_checkOperationValue(?,?)}");
		proc.setString(1, wsId);
		proc.setInt(2, nodeId);
		ResultSet rs = proc.executeQuery();	 
		if(rs.next())
		{
			attrValue = rs.getString("vAttrValue");
		}
		
		proc.close();
		rs.close();
		con.close();
		
	}catch(SQLException e){
		e.printStackTrace();
	}
	return attrValue;
}
public Vector<DTOWorkSpaceNodeAttrHistory> getLatestNodeAttrHistory(String workspaceId,int nodeId,String AttrForIndiId) 
{
	Vector<DTOWorkSpaceNodeAttrHistory> nodeAttrHistory = new Vector<DTOWorkSpaceNodeAttrHistory>();
     try
     {
    	 String Where = "WorkspaceId ='"+workspaceId+"' AND NodeId = "+nodeId+" AND AttrForIndiId='"+AttrForIndiId+"' AND TranNo = " +
    	 				"( SELECT MAX(TranNo) AS TranNo FROM view_NodeVersionHistoryDetail " +
    	 					" WHERE WorkspaceId ='"+workspaceId+"' AND NodeId = "+nodeId+" " +
    	 					" GROUP BY WorkspaceId,NodeId )" +
    	 				" AND AttrName <> 'FileLastModified'";// This Attr is not valid at Leaf Node of published eCTD Dossier. 
    	 Connection con = ConnectionManager.ds.getConnection();
    	 ResultSet rs = dataTable.getDataSet(con,"WorkspaceId,NodeId,AttrId,TranNo,AttrValue,ModifyBy,AttrName", "view_NodeVersionHistoryDetail", Where, "");
    	 while(rs.next())
    	 {
    		 DTOWorkSpaceNodeAttrHistory dto = new DTOWorkSpaceNodeAttrHistory();
    		 dto.setWorkSpaceId(rs.getString("WorkspaceId"));		
    		 dto.setNodeId(rs.getInt("NodeId"));				
    		 dto.setTranNo(rs.getInt("TranNo"));					
    		 dto.setAttrId(rs.getInt("AttrId"));				
    		 dto.setAttrValue(rs.getString("AttrValue"));			
    		 dto.setModifyBy(rs.getInt("ModifyBy"));
    		 dto.setAttrName(rs.getString("AttrName"));
    		 nodeAttrHistory.addElement(dto);
    		 dto = null;
     	 }
    	 rs.close();
    	 con.close();
     }catch(SQLException e) {
    	 e.printStackTrace();
     }

     return nodeAttrHistory;    
} 
//create by virendra barad for show attribute in user defined attribute
public Vector<DTOWorkSpaceNodeAttrHistory> getUserDefinedNodeAttrHistory(String workspaceId,int NodeId)
{
	Vector<DTOWorkSpaceNodeAttrHistory> nodeAttrHistroy = new Vector<DTOWorkSpaceNodeAttrHistory>();
	try
	{
		Connection con=ConnectionManager.ds.getConnection();
		String Fields="vworkspaceId,iNodeId,iAttrid,vAttrName,vAttrValue";
		//String Where =" vWorkspaceId='"+workspaceId+"'  and iattrid <>'-999' and iNodeId="+NodeId;
		String Where =" vWorkspaceId='"+workspaceId+"'  and iattrid <>'-999' and iNodeId="+NodeId + "And vAttrForIndiId<>'0001'";
		ResultSet resultSet=dataTable.getDataSet(con,Fields,"workspacenodeattrdetail", Where,"iAttrId");
		while(resultSet.next())
		{
			DTOWorkSpaceNodeAttrHistory workSpaceNodeAttrHistory = new DTOWorkSpaceNodeAttrHistory();
			workSpaceNodeAttrHistory.setWorkSpaceId(resultSet.getString("vworkspaceId"));
			workSpaceNodeAttrHistory.setNodeId(resultSet.getInt("inodeId"));
			workSpaceNodeAttrHistory.setAttrId(resultSet.getInt("iattrId"));
			workSpaceNodeAttrHistory.setAttrName(resultSet.getString("vattrName"));
			workSpaceNodeAttrHistory.setAttrValue(resultSet.getString("vattrValue"));
			nodeAttrHistroy.addElement(workSpaceNodeAttrHistory);
			workSpaceNodeAttrHistory = null;
		}	        	
		resultSet.close();
		con.close();
	
	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return 	nodeAttrHistroy;
}
}///main class ended
