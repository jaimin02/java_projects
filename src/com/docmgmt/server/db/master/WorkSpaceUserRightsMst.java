package com.docmgmt.server.db.master;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOStageWiseMailReport;
import com.docmgmt.dto.DTOTimelineWorkspaceUserRightsMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class WorkSpaceUserRightsMst 
{
	DataTable dataTable;
	
	public WorkSpaceUserRightsMst()
	{
		dataTable=new DataTable();
	}
	
public void insertMultipleUserRights(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList)
{
	Connection con = null;
	CallableStatement cs = null;
	try
	{
		/*Sort Array List by usercode*/
		Collections.sort(userRightsList, new Comparator<DTOWorkSpaceUserRightsMst>() {
			public int compare(DTOWorkSpaceUserRightsMst arg0, DTOWorkSpaceUserRightsMst arg1) {
				if(arg0.getUserCode() == arg1.getUserCode()){ 
					return arg0.getWorkSpaceId().compareTo(arg1.getWorkSpaceId());
				}
				else if(arg0.getUserCode() < arg1.getUserCode())
					return -1;
				else //if(arg0.getUserCode() > arg1.getUserCode())
					return 1;
			}
		});
		
		con=ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_MultipleUserRights(?,?,?,?,?,?,?,?,?,?,?,?)}");
		int prevUserCode = 0;
		String prevWorkspaceId = "";
		
		for(int i=0;i<userRightsList.size();i++)
		{
			DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst=userRightsList.get(i);
			cs.setString(1, dtoWorkSpaceUserRightsMst.getWorkSpaceId());
			cs.setInt(2, dtoWorkSpaceUserRightsMst.getUserGroupCode());
			cs.setInt(3, dtoWorkSpaceUserRightsMst.getUserCode());
			cs.setString(4,Character.toString(dtoWorkSpaceUserRightsMst.getCanReadFlag()));
			cs.setString(5, Character.toString(dtoWorkSpaceUserRightsMst.getCanAddFlag()));
			cs.setString(6, Character.toString(dtoWorkSpaceUserRightsMst.getCanEditFlag()));
			cs.setString(7, Character.toString(dtoWorkSpaceUserRightsMst.getCanDeleteFlag()));
			cs.setString(8, dtoWorkSpaceUserRightsMst.getAdvancedRights());
			cs.setString(9, dtoWorkSpaceUserRightsMst.getRemark());
			cs.setInt(10, dtoWorkSpaceUserRightsMst.getModifyBy());
			cs.setInt(11, dtoWorkSpaceUserRightsMst.getStageId());
			//Delete all rights first of the user on the project.
			if(prevUserCode != dtoWorkSpaceUserRightsMst.getUserCode()
					|| !prevWorkspaceId.equals(dtoWorkSpaceUserRightsMst.getWorkSpaceId())){
				cs.setInt(12,3);
				cs.execute();
			}
			
			cs.setInt(12,1);//Insert userrights for one stage.
			cs.execute();
			
			prevUserCode = dtoWorkSpaceUserRightsMst.getUserCode();
			prevWorkspaceId = dtoWorkSpaceUserRightsMst.getWorkSpaceId();
		}
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}
public void insertFolderSpecificMultipleUserRights(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList)
{
	Connection con = null;
	CallableStatement cs = null;
	try
	{
		/*Sort Array List by usercode*/
		Collections.sort(userRightsList, new Comparator<DTOWorkSpaceUserRightsMst>() {
			public int compare(DTOWorkSpaceUserRightsMst arg0, DTOWorkSpaceUserRightsMst arg1) {
				if(arg0.getUserCode() == arg1.getUserCode()){ 
					return arg0.getWorkSpaceId().compareTo(arg1.getWorkSpaceId());
				}
				else if(arg0.getUserCode() < arg1.getUserCode())
					return -1;
				else //if(arg0.getUserCode() > arg1.getUserCode())
					return 1;
			}
		});
		
		con=ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_FolderSpecificMultipleUserRights(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		int prevUserCode = 0;
		String prevWorkspaceId = "";
		
		for(int i=0;i<userRightsList.size();i++)
		{
			DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst=userRightsList.get(i);
			cs.setString(1, dtoWorkSpaceUserRightsMst.getWorkSpaceId());
			cs.setInt(2, dtoWorkSpaceUserRightsMst.getUserGroupCode());
			cs.setInt(3, dtoWorkSpaceUserRightsMst.getUserCode());
			cs.setInt(4, dtoWorkSpaceUserRightsMst.getNodeId());
			cs.setString(5,Character.toString(dtoWorkSpaceUserRightsMst.getCanReadFlag()));
			cs.setString(6, Character.toString(dtoWorkSpaceUserRightsMst.getCanAddFlag()));
			cs.setString(7, Character.toString(dtoWorkSpaceUserRightsMst.getCanEditFlag()));
			cs.setString(8, Character.toString(dtoWorkSpaceUserRightsMst.getCanDeleteFlag()));
			cs.setString(9, dtoWorkSpaceUserRightsMst.getAdvancedRights());
			cs.setString(10, dtoWorkSpaceUserRightsMst.getRemark());
			cs.setInt(11, dtoWorkSpaceUserRightsMst.getModifyBy());
			cs.setInt(12, dtoWorkSpaceUserRightsMst.getStageId());
			//Delete all rights first of the user on the project.
			/*if(prevUserCode != dtoWorkSpaceUserRightsMst.getUserCode()
					|| !prevWorkspaceId.equals(dtoWorkSpaceUserRightsMst.getWorkSpaceId())){
				cs.setInt(13,3);
				cs.execute();
			}*/
			
			cs.setInt(13,1);//Insert userrights for one stage.
			cs.execute();
			
			prevUserCode = dtoWorkSpaceUserRightsMst.getUserCode();
			prevWorkspaceId = dtoWorkSpaceUserRightsMst.getWorkSpaceId();
		}
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}
public void insertFolderSpecificMultipleUserRightsWithRoleCode(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList)
{
	Connection con = null;
	CallableStatement cs = null;
	try
	{
		/*Sort Array List by usercode*/
		Collections.sort(userRightsList, new Comparator<DTOWorkSpaceUserRightsMst>() {
			public int compare(DTOWorkSpaceUserRightsMst arg0, DTOWorkSpaceUserRightsMst arg1) {
				if(arg0.getUserCode() == arg1.getUserCode()){ 
					return arg0.getWorkSpaceId().compareTo(arg1.getWorkSpaceId());
				}
				else if(arg0.getUserCode() < arg1.getUserCode())
					return -1;
				else //if(arg0.getUserCode() > arg1.getUserCode())
					return 1;
			}
		});
		
		con=ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_FolderSpecificMultipleUserRightsWithRoleCode(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		int prevUserCode = 0;
		String prevWorkspaceId = "";
		
		for(int i=0;i<userRightsList.size();i++)
		{
			DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst=userRightsList.get(i);
			cs.setString(1, dtoWorkSpaceUserRightsMst.getWorkSpaceId());
			cs.setInt(2, dtoWorkSpaceUserRightsMst.getUserGroupCode());
			cs.setInt(3, dtoWorkSpaceUserRightsMst.getUserCode());
			cs.setInt(4, dtoWorkSpaceUserRightsMst.getNodeId());
			cs.setString(5,Character.toString(dtoWorkSpaceUserRightsMst.getCanReadFlag()));
			cs.setString(6, Character.toString(dtoWorkSpaceUserRightsMst.getCanAddFlag()));
			cs.setString(7, Character.toString(dtoWorkSpaceUserRightsMst.getCanEditFlag()));
			cs.setString(8, Character.toString(dtoWorkSpaceUserRightsMst.getCanDeleteFlag()));
			cs.setString(9, dtoWorkSpaceUserRightsMst.getAdvancedRights());
			cs.setString(10, dtoWorkSpaceUserRightsMst.getRemark());
			cs.setInt(11, dtoWorkSpaceUserRightsMst.getModifyBy());
			cs.setInt(12, dtoWorkSpaceUserRightsMst.getStageId());
			cs.setString(13, dtoWorkSpaceUserRightsMst.getRoleCode());			
			cs.setInt(14,dtoWorkSpaceUserRightsMst.getMode());//1 - Insert , 2-Update,3-Delete Wsuserrightsmst for one stage.
			cs.execute();
			
			prevUserCode = dtoWorkSpaceUserRightsMst.getUserCode();
			prevWorkspaceId = dtoWorkSpaceUserRightsMst.getWorkSpaceId();
		}
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}
public boolean RemoveFolderSpecificMultipleUserRights(String wsId,String[] users,int[] stageId,String nodeIdsCSV) {
	try {
		String userCodes="";
		String stageIds="";
		for(int i=0;i<users.length;i++){
			userCodes+=users[i]+",";
		}
		for(int i=0;i<stageId.length;i++){
			stageIds+=stageId[i]+",";
		}
		userCodes= userCodes.substring(0, userCodes.length() - 1);
		stageIds= stageIds.substring(0, stageIds.length() - 1);
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs = con
				.prepareCall("{call Proc_DeleteFolderSpecificMultipleUserRights(?,?,?,?)}");
		cs.setString(1, wsId);
		cs.setString(2,userCodes);
		cs.setString(3,stageIds);
		cs.setString(4,nodeIdsCSV);
		cs.execute();
		cs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return true;
}
public Vector<DTOWorkSpaceUserRightsMst> getWorkspaceNodeRightsDetail(DTOWorkSpaceUserRightsMst obj)
{		
	Vector<DTOWorkSpaceUserRightsMst> workspaceNodeRightsVector = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{		  
		StringBuffer query = new StringBuffer();
		query.append("vWorkspaceId='"+obj.getWorkSpaceId()+"' and iNodeId="+obj.getNodeId()+"");
		if(obj.getUserCode() != 0){
			query.append(" and iUserGroupCode="+obj.getUserGroupCode()+" and iUserCode="+obj.getUserCode()+"");
		}
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*", "workspaceUserRightsMst",query.toString(),"");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
			dto.setUserCode(rs.getInt("iUserCode"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setCanReadFlag(rs.getString("cCanReadFlag").charAt(0));
			dto.setCanAddFlag(rs.getString("cCanAddFlag").charAt(0));
			dto.setCanEditFlag(rs.getString("cCanEditFlag").charAt(0));
			dto.setCanDeleteFlag(rs.getString("cCanDeleteFlag").charAt(0));
			dto.setAdvancedRights(rs.getString("vAdvancedRights"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			workspaceNodeRightsVector.addElement(dto);
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return workspaceNodeRightsVector;
}

public Vector<Object []> getNodeAndRightDetail(String workspaceID,int userGroupCode,int userCode)
{
	Vector<Object []> nodeInfo = new Vector<Object []>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		con=ConnectionManager.ds.getConnection();
		String Fields="Distinct WorkspaceId,usergroupcode,usercode,NodeNo,NodeId,NodeDisplayName,NodeName,ParentNodeId,Foldername," +
		"canReadFlag,canAddFlag,canEditFlag,statusIndi,canDeleteFlag,iformNo";
		String Where="WorkspaceId='"+workspaceID+"' and usergroupcode="+userGroupCode+" and usercode="+userCode+" and StatusIndi<>'D'";
		rs=dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail" , Where,"ParentNodeId,NodeNo" ); 
		while (rs.next())  
		{
			Object [] record =  
			{
				new Integer(rs.getInt("NodeId")),    	// Node id
				rs.getString("NodeDisplayName"),				// Display Name
				new Integer(rs.getInt("ParentNodeId")),		// Parent Id
				rs.getString("FolderName"),				// Folder Name
				rs.getString("CanReadFlag"),				// Can Read Flag
				rs.getString("canAddFlag"),				// Can Add Flag
				rs.getString("CanEditFlag"),				// Can Edit Flag
				rs.getString("CanDeleteFlag"),				// Can Delete Flag
				rs.getString("StatusIndi"),				// cStatusIndi
				new Integer(rs.getInt("iformNo")),		// No of comments
				new Integer(rs.getInt("NodeNo")),		// inodeno
				rs.getString("NodeName")				//Node Name
			};
			nodeInfo.addElement(record);	
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}

public Vector<Object []> getNodeAndRightDetailList(String workspaceID,int userGroupCode,int userCode)
{
	Vector<Object []> nodeInfo = new Vector<Object []>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		con=ConnectionManager.ds.getConnection();
		String Fields="Distinct WorkspaceId,usergroupcode,usercode,NodeNo,NodeId,NodeDisplayName,NodeName,ParentNodeId,Foldername," +
		"canReadFlag,canAddFlag,canEditFlag,statusIndi,canDeleteFlag,iformNo";
		String Where="WorkspaceId='"+workspaceID+"' and usergroupcode="+userGroupCode+" and usercode="+userCode+" and StatusIndi<>'D'";
		rs=dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail_WSList" , Where,"ParentNodeId,NodeNo" ); 
		while (rs.next())  
		{
			Object [] record =  
			{
				new Integer(rs.getInt("NodeId")),    	// Node id
				rs.getString("NodeDisplayName"),				// Display Name
				new Integer(rs.getInt("ParentNodeId")),		// Parent Id
				rs.getString("FolderName"),				// Folder Name
				rs.getString("CanReadFlag"),				// Can Read Flag
				rs.getString("canAddFlag"),				// Can Add Flag
				rs.getString("CanEditFlag"),				// Can Edit Flag
				rs.getString("CanDeleteFlag"),				// Can Delete Flag
				rs.getString("StatusIndi"),				// cStatusIndi
				new Integer(rs.getInt("iformNo")),		// No of comments
				new Integer(rs.getInt("NodeNo")),		// inodeno
				rs.getString("NodeName")				//Node Name
			};
			nodeInfo.addElement(record);	
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}

//added on  8-5-2015 By Dharmendra jadav
//start

public Vector<Object []> getNodeDetails(String workspaceID)
{
	Vector<Object []> nodeInfo = new Vector<Object []>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		con=ConnectionManager.ds.getConnection();
		String Fields="Distinct WorkspaceId,NodeNo,NodeId,NodeDisplayName,NodeName,ParentNodeId,Foldername," +
		"canReadFlag,canAddFlag,canEditFlag,statusIndi,canDeleteFlag,iformNo";
		String Where="WorkspaceId='"+workspaceID+"' ";
		rs=dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail" , Where,"ParentNodeId,NodeNo" ); 
		while (rs.next())  
		{
			Object [] record =  
			{
				new Integer(rs.getInt("NodeId")),    	// Node id
				rs.getString("NodeDisplayName"),				// Display Name
				new Integer(rs.getInt("ParentNodeId")),		// Parent Id
				rs.getString("FolderName"),				// Folder Name
				rs.getString("CanReadFlag"),				// Can Read Flag
				rs.getString("canAddFlag"),				// Can Add Flag
				rs.getString("CanEditFlag"),				// Can Edit Flag
				rs.getString("CanDeleteFlag"),				// Can Delete Flag
				rs.getString("StatusIndi"),				// cStatusIndi
				new Integer(rs.getInt("iformNo")),		// No of comments
				new Integer(rs.getInt("NodeNo")),		// inodeno
				rs.getString("NodeName")				//Node Name
			};
			nodeInfo.addElement(record);	
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}

//end


/******************************************/
public Vector<DTOWorkSpaceNodeDetail> getNodeAndRightDetailNewTree(String workspaceID,int userGroupCode,int userCode)
{
	Vector<DTOWorkSpaceNodeDetail> nodeInfo = new Vector<DTOWorkSpaceNodeDetail>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		DocMgmtImpl docmgmt=new DocMgmtImpl();
		con=ConnectionManager.ds.getConnection();
		String Fields="Distinct WorkspaceId,usergroupcode,usercode,NodeNo,NodeId,NodeDisplayName,NodeName,ParentNodeId,Foldername," +
		"canReadFlag,canAddFlag,canEditFlag,statusIndi,nodeTypeIndi,requiredFlag,canDeleteFlag,iformNo";
		String Where="WorkspaceId='"+workspaceID+"' and usergroupcode="+userGroupCode+" and usercode="+userCode+" and statusIndi<>'D'";
		rs=dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail" , Where,"ParentNodeId,NodeNo" ); 
		while (rs.next())    
		{
			
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setNodeId(rs.getInt("NodeId"));
			dto.setNodeDisplayName(rs.getString("NodeDisplayName"));
			dto.setParentNodeId(rs.getInt("ParentNodeId"));
			dto.setFolderName(rs.getString("FolderName"));
			dto.setCanReadFlag(rs.getString("CanReadFlag"));
			dto.setCanAddFlag(rs.getString("canAddFlag"));
			dto.setCanEditFlag(rs.getString("CanEditFlag"));
			dto.setCanDeleteFlag(rs.getString("CanDeleteFlag"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
			dto.setNodeTypeIndi(rs.getString("nodeTypeIndi").charAt(0));
			dto.setRequiredFlag(rs.getString("requiredFlag").charAt(0));
			dto.setIformNo(rs.getInt("iformNo"));
			dto.setNodeNo(rs.getInt("NodeNo"));
			dto.setNodeName(rs.getString("NodeName"));
			/*dto.setPublishFlag(docmgmt.submittedNodeIdDetail(workspaceID,rs.getInt("NodeId")));
			dto.setLokedNodeFlag(docmgmt.isCheckOut(workspaceID, rs.getInt("NodeId"),userCode));*/
			dto.setPublishFlag(true);
			dto.setLokedNodeFlag(true);
			nodeInfo.addElement(dto);	
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}
public Vector<DTOWorkSpaceNodeDetail> getNodeAndRightDetailNewTreeList(String workspaceID,int userGroupCode,int userCode)
{
	Vector<DTOWorkSpaceNodeDetail> nodeInfo = new Vector<DTOWorkSpaceNodeDetail>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		DocMgmtImpl docmgmt=new DocMgmtImpl();
		con=ConnectionManager.ds.getConnection();
		String Fields="Distinct WorkspaceId,usergroupcode,usercode,NodeNo,NodeId,NodeDisplayName,NodeName,ParentNodeId,Foldername," +
		"canReadFlag,canAddFlag,canEditFlag,statusIndi,nodeTypeIndi,requiredFlag,canDeleteFlag,iformNo";
		
		String uType=ActionContext.getContext().getSession().get("usertypename").toString();
		String Where="";
		if(uType.equalsIgnoreCase("WA")){
			Where="WorkspaceId='"+workspaceID+"' and usergroupcode="+userGroupCode+" and usercode="+userCode;
		} 
		else{
		Where="WorkspaceId='"+workspaceID+"' and usergroupcode="+userGroupCode+" and usercode="+userCode+" and statusIndi<>'D'";
		}
		rs=dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail_WSList" , Where,"ParentNodeId,NodeNo" ); 
		while (rs.next())    
		{
			
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setNodeId(rs.getInt("NodeId"));
			dto.setNodeDisplayName(rs.getString("NodeDisplayName"));
			dto.setParentNodeId(rs.getInt("ParentNodeId"));
			dto.setFolderName(rs.getString("FolderName"));
			dto.setCanReadFlag(rs.getString("CanReadFlag"));
			dto.setCanAddFlag(rs.getString("canAddFlag"));
			dto.setCanEditFlag(rs.getString("CanEditFlag"));
			dto.setCanDeleteFlag(rs.getString("CanDeleteFlag"));
			dto.setStatusIndi(rs.getString("statusIndi").charAt(0));
			dto.setNodeTypeIndi(rs.getString("nodeTypeIndi").charAt(0));
			dto.setRequiredFlag(rs.getString("requiredFlag").charAt(0));
			dto.setIformNo(rs.getInt("iformNo"));
			dto.setNodeNo(rs.getInt("NodeNo"));
			dto.setNodeName(rs.getString("NodeName"));
			/*dto.setPublishFlag(docmgmt.submittedNodeIdDetail(workspaceID,rs.getInt("NodeId")));
			dto.setLokedNodeFlag(docmgmt.isCheckOut(workspaceID, rs.getInt("NodeId"),userCode));*/
			dto.setPublishFlag(true);
			dto.setLokedNodeFlag(true);
			nodeInfo.addElement(dto);	
			dto=null;
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}
/******************************************/

public Vector<DTOWorkSpaceUserRightsMst> getUserRightsReport(DTOWorkSpaceUserRightsMst dto)
{
	Vector<DTOWorkSpaceUserRightsMst> wsUserRightsMst = new Vector<DTOWorkSpaceUserRightsMst>();
	wsUserRightsMst = getUserRightsReport(dto,false);
	return wsUserRightsMst;
}
public Vector<DTOWorkSpaceUserRightsMst> getUserRightsReport(DTOWorkSpaceUserRightsMst dto,boolean includeStageId)
{
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		String Fields="Distinct WorkspaceId,Workspacedesc,NodeId,NodeName,NodeDisplayName,FolderName,UserName,userGroupName,canReadFlag," +
		"canEditFlag,UserCode,NodeId,advancedRights";
		StringBuffer query = new StringBuffer();
		query.append("WorkspaceId='"+dto.getWorkSpaceId()+"'");
		if(dto.getNodeId() > 0)
			query.append(" and NodeId = " + dto.getNodeId());
		if(dto.getParentNodeId() > 0)
			query.append(" and ParentNodeId = " + dto.getParentNodeId());
		if(dto.getUserCode() > 0)
			query.append(" and UserCode = " + dto.getUserCode());
		if(dto.getFolderName() != null && !dto.getFolderName().trim().equals(""))
			query.append(" and FolderName like '"+dto.getFolderName()+"%' ");
		
		if (dto.getFrom() != null && dto.getFrom().trim() != "" && dto.getTo() != null && dto.getTo().trim() != "")
		{
			Fields = Fields+",DocIdCount ";
			query.append(" and DocIdCount between "+dto.getFrom() +" and "+dto.getTo());
		}
		con = ConnectionManager.ds.getConnection();

		if(includeStageId == true)
		{
			Fields+=",stageid";
		}
		String orderBy = "";
		if(dto.getSortOn() != null && !dto.getSortOn().equals(""))
			orderBy = dto.getSortOn()+" "+dto.getSortBy();
		rs=dataTable.getDataSet(con,Fields," View_WorkSpaceNodeRightsDetail ",query.toString(),orderBy);
		while (rs.next()) 
		{
			DTOWorkSpaceUserRightsMst dtowurmst = new DTOWorkSpaceUserRightsMst();
			dtowurmst.setWorkSpaceId(rs.getString("WorkspaceId"));
			dtowurmst.setWorkSpaceDesc(rs.getString("WorkspaceDesc"));	//workSpaceDesc from workspaceMst
			dtowurmst.setNodeName(rs.getString("NodeName"));
			dtowurmst.setNodeId(rs.getInt("NodeId"));
			dtowurmst.setNodeDisplayName(rs.getString("NodeDisplayName"));	//nodeDisplayName from workSpaceNodeDetail
			dtowurmst.setFolderName(rs.getString("FolderName"));
			dtowurmst.setUserName(rs.getString("Username"));			//userName from userMst
			dtowurmst.setUserGroupName(rs.getString("userGroupName"));	//userGroupName from userGroupMst
			dtowurmst.setReadRights(rs.getString("canReadFlag"));		//readRights from workspaceUserRights
			dtowurmst.setEditRights(rs.getString("canEditFlag"));		//editRights from workspaceUserRights
			dtowurmst.setUserCode(rs.getInt("UserCode"));			//userCode from workspaceUserRights
			dtowurmst.setNodeId(rs.getInt("NodeId"));				//nodeId from workspaceUserRights
			dtowurmst.setAdvancedRights(rs.getString("advancedRights"));	//advanceRights from workspaceUserRights
			if(includeStageId)
			{
				dtowurmst.setStageId(rs.getInt("stageid"));
			}
			data.add(dtowurmst);
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
public void insertNodeintoWorkSpaceUserRights(String wsId,int nodeId)
{
	Connection con = null;
	CallableStatement cs = null;
	try
    {
    	 con = ConnectionManager.ds.getConnection();
		 cs=con.prepareCall("{call Proc_updateWorkSpaceUserRights(?,?,?,?,?,?,?,?)}");
		 cs.setString(1, wsId);
		 cs.setInt(2, nodeId);
		 cs.setInt(3,1); //not in use 
		 cs.setInt(4, 1); //not in use 
		 cs.setString(5, "A");//not in use
		 cs.setString(6, "A");//not in use
		 cs.setString(7, "A");//not in use
		 cs.setInt(8,10);
		 cs.execute();	
		
    }catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}

public boolean updateWorkSpaceUserRights(DTOWorkSpaceUserRightsMst dto)  
{
	Connection con = null;
	CallableStatement cs = null;
	try 
	{
		 con = ConnectionManager.ds.getConnection();
		 cs=con.prepareCall("{call Proc_updateWorkSpaceUserRights(?,?,?,?,?,?,?,?)}");
		 cs.setString(1, dto.getWorkSpaceId());
		 cs.setInt(2, dto.getNodeId());
		 cs.setInt(3,dto.getUserCode());
		 cs.setInt(4, dto.getUserGroupCode());
		 cs.setString(5, Character.toString(dto.getCanDeleteFlag()));
		 cs.setString(6, Character.toString(dto.getCanAddFlag()));
		 cs.setString(7, Character.toString(dto.getCanEditFlag()));
		 cs.setInt(8, dto.getStageId());
		 cs.execute();	
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return true;
}

public Vector<Object []> getNodeDetailForDTD(String workspaceID,int userGroupCode,int userCode)
{
	Vector<Object []> nodeInfo = new Vector<Object []>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{
		con=ConnectionManager.ds.getConnection();
		String Fields="Distinct NodeId,NodeDisplayName,ParentNodeId,Foldername," +
				"canReadFlag,canAddFlag,canEditFlag,canDeleteFlag,iformNo";
		
		String Where="vWorkspaceId='"+workspaceID+"' and usergroupcode="+userGroupCode+" and usercode="+userCode;
		rs=dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail" , Where,"ParentNodeId,NodeNo" ); 
		while (rs.next())  
		{
			Integer nodeno = new Integer(rs.getInt("NodeId"));
			String displayname = rs.getString("NodeDisplayName");
			boolean status = false;
			String newDisplay = "";
			for(int i=1;i<9;i++){
				if(displayname.startsWith("m"+i))
					status = true;
			}	
			
			if(status==false)
				newDisplay = "m1" + displayname;
			else
				newDisplay=displayname;
			
			Object [] record =  {
				nodeno,    	// Node no
				newDisplay,				// Display Name
				new Integer(rs.getInt("ParentNodeId")),		// Parent Id
				rs.getString("Foldername"),				// Folder Name
				rs.getString("canReadFlag"),				// Can Read Flag
				rs.getString("canAddFlag"),				// Can Add Flag
				rs.getString("canEditFlag"),				// Can Edit Flag
				rs.getString("canDeleteFlag"),				// Can Delete Flag
				new Integer(rs.getInt("iformNo"))				// No of comments
			};
			nodeInfo.addElement(record);	
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
}

public Vector<Object []> getNodeAndRightDetailForIndexView(String workspaceID,int userGroupCode,int userCode)
{
	
	Vector<Object []> nodeInfo = new Vector<Object []>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		con=ConnectionManager.ds.getConnection();
		String Fields=" Distinct WorkspaceId,NodeId,ParentNodeId,NodeDisplayName,FolderName,CanAddFlag,"+
					  "canReadFlag,canEditFlag,canDeleteFlag,iformNo,FileName,UserCode,"+
					  "UserGroupCode,UserDefineVersionId,ModifyOn,NodeNo";
		//String Where=" WorkspaceId='"+workspaceID+"' and usercode="+userCode+" and usergroupcode="+userGroupCode;
		String Where = "";// Modified by : Ashmak Shah
		if(userCode == 0)
		{
			Where=" WorkspaceId='"+workspaceID+"'"+" and statusindi<>'D'";
		}
		else
		{
			Where=" WorkspaceId='"+workspaceID+"' and usercode="+userCode+" and usergroupcode="+userGroupCode+" and statusindi<>'D'";
		}
		
		rs=dataTable.getDataSet(con, Fields,"View_WorkSpaceNodeRightsDetail" , Where,"ParentNodeId,NodeNo" );
		
		while (rs.next())  
		{
			Object [] record =  
			{
				new Integer(rs.getInt("NodeId")),    	// Node Id
				rs.getString("NodeDisplayName"),		// Display Name
				new Integer(rs.getInt("ParentNodeId")),	// Parent Id
				rs.getString("FolderName"),				// Folder Name
				rs.getString("CanReadFlag"),			// Can Read Flag
				rs.getString("CanAddFlag"),				// Can Add Flag
				rs.getString("CanEditFlag"),			// Can Edit Flag
				rs.getString("canDeleteFlag"),			// Can Delete Flag
				new Integer(rs.getInt("iformNo")),		// No of comments
				rs.getString("Filename"),				//filename
				rs.getDate("ModifyOn"),					//file last modified on		
				rs.getString("UserDefineVersionId")		//userDefinedVersionId
			};
			nodeInfo.addElement(record);	
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;
}

public Vector<DTOWorkSpaceUserRightsMst> getStageUserDetail(String WorkspaceId,int NodeId,int usercode,int usergroupcode)
{
	Vector<DTOWorkSpaceUserRightsMst> stageDtl=new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		String Where="WorkspaceId='"+WorkspaceId+"' and NodeId="+NodeId+" and usercode="+usercode+" and usergroupcode="+usergroupcode+" and StageId<>10";
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*","View_stageUserDetail" , Where,"stageId");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto=new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("workspaceId"));
			dto.setNodeId(rs.getInt("NodeId"));
			dto.setUserCode(rs.getInt("usercode"));
			dto.setUserGroupCode(rs.getInt("usergroupcode"));
			dto.setStageId(rs.getInt("stageId"));
			dto.setStageDesc(rs.getString("stageDesc"));
			stageDtl.addElement(dto); 
			dto=null;
		}
	}
	catch (Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return stageDtl;
}

public boolean iscreatedRights(String WorkspaceId,int NodeId,int usercode,int usergroupcode)
{
	boolean flag=false;
	Connection con = null;
	ResultSet rs = null;
	try
	{
		String Where="WorkspaceId='"+WorkspaceId+"' and NodeId="+NodeId+" and usercode="+usercode+" and usergroupcode="+usergroupcode+" and StageId=10";
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*","View_stageUserDetail" , Where,"stageId");
		if(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto=new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("workspaceId"));
			dto.setNodeId(rs.getInt("NodeId"));
			dto.setUserCode(rs.getInt("usercode"));
			dto.setUserGroupCode(rs.getInt("usergroupcode"));
			dto.setStageId(rs.getInt("stageId"));
			dto.setStageDesc(rs.getString("stageDesc"));
			flag=true;
		}
	}
	catch (Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return flag;
}
public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail(String wsId, int nodeId)
{
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*",
				"view_userRightsDetail","vWorkspaceId = '"+wsId+"' and vUserTypeCode<>'0001' and vUserTypeCode<>'0002' and vUserTypeCode<>'0005' and vUserTypeCode<>'0006' and iNodeId ="+nodeId, "istageId,id");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserName(rs.getString("vUserName"));
			dto.setCanEditFlag(rs.getString("cCanEditFlag").charAt(0));
			dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
			dto.setNodeId(rs.getInt("inodeid"));
			dto.setUserTypeCode(rs.getString("vUserTypeCode"));
			dto.setHours(rs.getInt("iHours"));
			dto.setRoleName(rs.getString("vRoleName"));
			dto.setRoleCode(rs.getString("vRoleCode"));
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

public String getUserRightsDetailForNextStageUser(String wsId,int nodeId,int stageId) 
{
	String data="";
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*",
				"view_userRightsDetail","vWorkspaceId = '"+wsId+"' and vUserTypeCode<>'0001' and vUserTypeCode<>'0002' "
				+ "and vUserTypeCode<>'0005' and vUserTypeCode<>'0006' and iNodeId ="+nodeId+ "and istageid="+stageId, "istageId,id");
		while(rs.next())
		{
			data=data+rs.getString("vUserName")+",";			
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}

	return data;
}

public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForEsignature(String wsId, int nodeId)
{
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*",
				"view_userRightsDetailForESignature","vWorkspaceId = '"+wsId+"' and vUserTypeCode<>'0001' and vUserTypeCode<>'0002' and vUserTypeCode<>'0005' and vUserTypeCode<>'0006' and iNodeId ="+nodeId, "istageId,id");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserName(rs.getString("vUserName"));
			dto.setCanEditFlag(rs.getString("cCanEditFlag").charAt(0));
			dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
			dto.setNodeId(rs.getInt("inodeid"));
			dto.setUserTypeCode(rs.getString("vUserTypeCode"));
			dto.setHours(rs.getInt("iHours"));
			dto.setRoleName(rs.getString("vRoleName"));
			dto.setRoleCode(rs.getString("vRoleCode"));
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

public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForSingleDoc(String wsId, int nodeId)
{
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*",
				"view_userRightsDetailWithRoleName","vWorkspaceId = '"+wsId+"' and vUserTypeCode<>'0001' and "
				+ "vUserTypeCode<>'0002' and vUserTypeCode<>'0005' and vUserTypeCode<>'0006' and iNodeId ="+nodeId, "istageId,id");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserName(rs.getString("vUserName"));
			dto.setLoginName(rs.getString("vLoginName"));
			dto.setCanEditFlag(rs.getString("cCanEditFlag").charAt(0));
			dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
			dto.setNodeId(rs.getInt("inodeid"));
			dto.setUserTypeCode(rs.getString("vUserTypeCode"));
			dto.setHours(rs.getInt("iHours"));
			dto.setRoleName(rs.getString("vRoleName"));
			dto.setRoleCode(rs.getString("vRoleCode"));
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

public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailDraft(String wsId, int nodeId)
{
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*",
				"view_userRightsDetailWithRoleName","vWorkspaceId = '"+wsId+"' and vUserTypeCode<>'0001' and "
				+ "vUserTypeCode<>'0002' and vUserTypeCode<>'0005' and vUserTypeCode<>'0006' and iNodeId ="+nodeId 
				+ " and iStageId<>40", "istageId,id");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserName(rs.getString("vUserName"));
			dto.setLoginName(rs.getString("vLoginName"));
			dto.setCanEditFlag(rs.getString("cCanEditFlag").charAt(0));
			dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
			dto.setNodeId(rs.getInt("inodeid"));
			dto.setUserTypeCode(rs.getString("vUserTypeCode"));
			dto.setHours(rs.getInt("iHours"));
			dto.setRoleName(rs.getString("vRoleName"));
			dto.setRoleCode(rs.getString("vRoleCode"));
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


public void addOrUpdateRights(String templateId,int userCode)
{
	Connection con = null;
	CallableStatement cs = null;
	
	try 
	{
		 con = ConnectionManager.ds.getConnection();
		 cs=con.prepareCall("{call Insert_UpdateWorkspaceRights(?,?)}");
		 cs.setString(1, templateId);
		 cs.setInt(2,userCode);
		 cs.executeUpdate();	

	}catch(Exception e){
			e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
}

public int getMaxStageId(String wsId, int nodeId, int userCode)
{
	int stageId =0;
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		String Where = "vWorkspaceId = '"+wsId+"' and iNodeId = "+nodeId+" and iUserCode ="+userCode;
		rs = dataTable.getDataSet(con, "max(iStageId) as stageId", "workspaceUserRightsMst", Where, "");
		if(rs.next())
		{
			stageId = rs.getInt("stageId");
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return stageId;
}

public Vector<Object []> getUserWisePublishableNodes(String workspaceID,int userGroupCode,int userCode)
{
	Vector<Object []> nodeInfo = new Vector<Object []>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		con=ConnectionManager.ds.getConnection();
		String Fields="Distinct WorkspaceId,usergroupcode,usercode,NodeNo,NodeId,NodeDisplayName,NodeName,ParentNodeId,Foldername," +
		"canReadFlag,canAddFlag,canEditFlag,statusIndi,canDeleteFlag,iformNo";
		String Where="WorkspaceId='"+workspaceID+"' and usergroupcode="+userGroupCode+" and usercode="+userCode+" and PublishFlag = 'Y'";
		rs=dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail" , Where,"ParentNodeId,NodeNo" ); 
		while (rs.next())  
		{
			
			
			
			
			Object [] record =  
			{
				new Integer(rs.getInt("NodeId")),    	// Node id
				rs.getString("NodeDisplayName"),				// Display Name
				new Integer(rs.getInt("ParentNodeId")),		// Parent Id
				rs.getString("FolderName"),				// Folder Name
				rs.getString("CanReadFlag"),				// Can Read Flag
				rs.getString("canAddFlag"),				// Can Add Flag
				rs.getString("CanEditFlag"),				// Can Edit Flag
				rs.getString("CanDeleteFlag"),				// Can Delete Flag
				rs.getString("StatusIndi"),				// cStatusIndi
				new Integer(rs.getInt("iformNo")),		// No of comments
				new Integer(rs.getInt("NodeNo")),		// inodeno
				rs.getString("NodeName")				//Node Name
			};
			nodeInfo.addElement(record);	
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}
public Vector<Object []> getUserWisePublishableNodesList(String workspaceID,int userGroupCode,int userCode)
{
	Vector<Object []> nodeInfo = new Vector<Object []>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		con=ConnectionManager.ds.getConnection();
		String Fields="Distinct WorkspaceId,usergroupcode,usercode,NodeNo,NodeId,NodeDisplayName,NodeName,ParentNodeId,Foldername," +
		"canReadFlag,canAddFlag,canEditFlag,statusIndi,canDeleteFlag,iformNo";
		String Where="WorkspaceId='"+workspaceID+"' and usergroupcode="+userGroupCode+" and usercode="+userCode+" and PublishFlag = 'Y'";
		rs=dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail_WSList" , Where,"ParentNodeId,NodeNo" ); 
		while (rs.next())  
		{
			
			
			
			
			Object [] record =  
			{
				new Integer(rs.getInt("NodeId")),    	// Node id
				rs.getString("NodeDisplayName"),				// Display Name
				new Integer(rs.getInt("ParentNodeId")),		// Parent Id
				rs.getString("FolderName"),				// Folder Name
				rs.getString("CanReadFlag"),				// Can Read Flag
				rs.getString("canAddFlag"),				// Can Add Flag
				rs.getString("CanEditFlag"),				// Can Edit Flag
				rs.getString("CanDeleteFlag"),				// Can Delete Flag
				rs.getString("StatusIndi"),				// cStatusIndi
				new Integer(rs.getInt("iformNo")),		// No of comments
				new Integer(rs.getInt("NodeNo")),		// inodeno
				rs.getString("NodeName")				//Node Name
			};
			nodeInfo.addElement(record);	
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}

//Added by Butani vijay
public Vector<Object []> getUserWisePublishableNodesForPdfPublish(String workspaceID)
{
	Vector<Object []> nodeInfo = new Vector<Object []>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		con=ConnectionManager.ds.getConnection();
		String Fields="Distinct WorkspaceId,usergroupcode,usercode,NodeNo,NodeId,NodeDisplayName,NodeName,ParentNodeId,Foldername," +
		"canReadFlag,canAddFlag,canEditFlag,statusIndi,canDeleteFlag,iformNo";
		String Where="WorkspaceId='"+workspaceID+"' and PublishFlag = 'Y'";
		rs=dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail" , Where,"ParentNodeId,NodeNo" ); 
		while (rs.next())  
		{
				Object [] record =  
			{
				new Integer(rs.getInt("NodeId")),    	// Node id
				rs.getString("NodeDisplayName"),				// Display Name
				new Integer(rs.getInt("ParentNodeId")),		// Parent Id
				rs.getString("FolderName"),				// Folder Name
				rs.getString("CanReadFlag"),				// Can Read Flag
				rs.getString("canAddFlag"),				// Can Add Flag
				rs.getString("CanEditFlag"),				// Can Edit Flag
				rs.getString("CanDeleteFlag"),				// Can Delete Flag
				rs.getString("StatusIndi"),				// cStatusIndi
				new Integer(rs.getInt("iformNo")),		// No of comments
				new Integer(rs.getInt("NodeNo")),		// inodeno
				rs.getString("NodeName")				//Node Name
			};
			nodeInfo.addElement(record);	
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}

public ArrayList<DTOStageMst> getUserStageDetail(String wsId,int userId)
{
	ArrayList<DTOStageMst> userStages = new ArrayList<DTOStageMst>();
	
	Connection con = null;
	ResultSet rs = null;
	
	StageMst stageMst = new StageMst();
	Vector<DTOStageMst> allStages =  stageMst.getStageDetail();
	
	try {
		con = ConnectionManager.ds.getConnection();
		String whereCon1 = "vWorkspaceId='"+wsId+"' AND iUserCode="+userId;
		
		for (Iterator<DTOStageMst> iterator = allStages.iterator(); iterator.hasNext();) {
			DTOStageMst dtoStageMst = iterator.next();
			String where = whereCon1 +" AND iStageId = "+dtoStageMst.getStageId();
			
			rs = dataTable.getDataSet(con, "COUNT(1)", "WorkspaceUserRightsMst", where, "");
			while(rs.next())
			{
				if(rs.getInt(1) > 0){
					userStages.add(dtoStageMst);
				}
			}
		}
	
	} catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return userStages;
	
}
/*public static void main(String[] args) {
	WorkSpaceUserRightsMst mst = new WorkSpaceUserRightsMst();
	ArrayList<DTOStageMst> userStages =  mst.getUserStageDetail("0009", 20);
	for (Iterator iterator = userStages.iterator(); iterator.hasNext();) {
		DTOStageMst dtoStageMst = (DTOStageMst) iterator.next();
		System.out.println("dtoStageMst"+dtoStageMst.getStageId()+" ::: " +dtoStageMst.getStageDesc());
	}
}*/

public void insertUpdateMultipleUserRights(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList,int mode)
{
	Connection con = null;
	CallableStatement cs = null;
	try
	{
		con=ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_WorkspaceUserRightsMst(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
	
		for(int i=0;i<userRightsList.size();i++)
		{
			DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst=userRightsList.get(i);
			cs.setString(1, dtoWorkSpaceUserRightsMst.getWorkSpaceId());
			cs.setInt(2, dtoWorkSpaceUserRightsMst.getUserGroupCode());
			cs.setInt(3, dtoWorkSpaceUserRightsMst.getUserCode());
			cs.setInt(4, dtoWorkSpaceUserRightsMst.getNodeId());
			cs.setString(5,Character.toString(dtoWorkSpaceUserRightsMst.getCanReadFlag()));
			cs.setString(6, Character.toString(dtoWorkSpaceUserRightsMst.getCanAddFlag()));
			cs.setString(7, Character.toString(dtoWorkSpaceUserRightsMst.getCanEditFlag()));
			cs.setString(8, Character.toString(dtoWorkSpaceUserRightsMst.getCanDeleteFlag()));
			cs.setString(9, dtoWorkSpaceUserRightsMst.getAdvancedRights());
			cs.setInt(10, dtoWorkSpaceUserRightsMst.getStageId());
			cs.setString(11, dtoWorkSpaceUserRightsMst.getRemark());
			cs.setInt(12, dtoWorkSpaceUserRightsMst.getModifyBy());
			cs.setInt(13, mode);// 1 - insert, 3 - delete
			cs.execute();
		}
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}
public ArrayList<Integer> getStageIdsRightsWise(String wsId,int userCode,int userGrpCode,int nodeId){
	ArrayList<Integer> stageIds = new ArrayList<Integer>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		String where="vWorkSpaceId="+wsId+" and iUserCode="+userCode+" and iUserGroupCode="+userGrpCode+" and iNodeId="+nodeId;
		ResultSet rs=dataTable.getDataSet(con, "DISTINCT iStageId ","WorkspaceUserRightsMst", where, "");
		while(rs.next())
		{
			stageIds.add(rs.getInt("iStageId"));
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return stageIds;
}
public Vector<DTOWorkSpaceUserRightsMst> getNewStageId(String WorkspaceId,int NodeId,int usercode,int usergroupcode)
{
	Vector<DTOWorkSpaceUserRightsMst> stageDtl=new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		String Where="WorkspaceId='"+WorkspaceId+"' and NodeId="+NodeId+" and StageId=0";
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"TOP 1 *","View_stageUserDetail" , Where,"stageId");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto=new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("workspaceId"));
			dto.setNodeId(rs.getInt("NodeId"));
			dto.setUserCode(rs.getInt("usercode"));
			dto.setUserGroupCode(rs.getInt("usergroupcode"));
			dto.setStageId(rs.getInt("stageId"));
			dto.setStageDesc(rs.getString("stageDesc"));
			stageDtl.addElement(dto); 
			dto=null;
		}
	}
	catch (Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return stageDtl;
}

//Added By Virendra Barad for Insert Audit trail ModuleWiseUSerMst.
public void insertModuleWiseUserHistory(DTOWorkSpaceUserRightsMst userRightsForModuleHistory)
{
	Connection con = null;
	CallableStatement cs = null;
	try
	{
		con=ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_moduleWiseUserMstHistory(?,?,?,?,?,?,?,?)}");
		
			DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst=userRightsForModuleHistory;
			cs.setString(1, dtoWorkSpaceUserRightsMst.getWorkSpaceId());
			cs.setInt(2, dtoWorkSpaceUserRightsMst.getUserGroupCode());
			cs.setInt(3, dtoWorkSpaceUserRightsMst.getUserCode());
			cs.setInt(4, dtoWorkSpaceUserRightsMst.getNodeId());
			cs.setString(5,dtoWorkSpaceUserRightsMst.getStages());
			cs.setString(6, dtoWorkSpaceUserRightsMst.getRemark());
			cs.setInt(7, dtoWorkSpaceUserRightsMst.getModifyBy());
			cs.setInt(8, dtoWorkSpaceUserRightsMst.getMode());
			
			cs.execute();
			
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public void insertModuleWiseUserHistoryWithRoleCode(DTOWorkSpaceUserRightsMst userRightsForModuleHistory)
{
	Connection con = null;
	CallableStatement cs = null;
	try
	{
		con=ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_moduleWiseUserMstHistoryWithRoleCode(?,?,?,?,?,?,?,?,?)}");
		
			DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst=userRightsForModuleHistory;
			cs.setString(1, dtoWorkSpaceUserRightsMst.getWorkSpaceId());
			cs.setInt(2, dtoWorkSpaceUserRightsMst.getUserGroupCode());
			cs.setInt(3, dtoWorkSpaceUserRightsMst.getUserCode());
			cs.setInt(4, dtoWorkSpaceUserRightsMst.getNodeId());
			cs.setString(5,dtoWorkSpaceUserRightsMst.getStages());
			cs.setString(6, dtoWorkSpaceUserRightsMst.getRemark());
			cs.setInt(7, dtoWorkSpaceUserRightsMst.getModifyBy());
			cs.setString(8, dtoWorkSpaceUserRightsMst.getRoleCode());
			cs.setInt(9, dtoWorkSpaceUserRightsMst.getMode());
						
			cs.execute();
			
	} catch (Exception e) {
		e.printStackTrace();
	}
}

//Add By Virendra Barad For Get audit trail of module history
	public Vector<DTOWorkSpaceUserRightsMst> getmoduleUserDetailHistory(String wsId, int nodeId) {
	
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceUserRightsMst> moduleUserDetail = new Vector<DTOWorkSpaceUserRightsMst>();
		ResultSet rs = null;
		Connection con = null;
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		try {
			con = ConnectionManager.ds.getConnection();

			String Where = "WorkspaceId = '" + wsId +"' and NodeId = "+ nodeId;
			rs = dataTable.getDataSet(con, "*", "view_modulewiseusermsthistory", Where,"TranNo");

			while (rs.next()) {
				DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
				dto.setWorkSpaceDesc(rs.getString("WorkspaceDesc")); // WorkspaceDesc
				dto.setNodeId(rs.getInt("NodeId"));
				dto.setUserGroupName(rs.getString("userGroupname")); // UserGroupName
				dto.setUserName(rs.getString("Username")); // UserName
				dto.setNodeDisplayName(rs.getString("NodeDisplayName"));   //NodeDisplayName
				dto.setFolderName(rs.getString("FolderName"));   // FolderName
				dto.setNodeName(rs.getString("NodeName"));   // NodeName
				dto.setModifyBy(rs.getInt("ModifyBy")); // modifyBy
				dto.setModifyByName(rs.getString("ModifyByName"));
				dto.setRemark(rs.getString("Remark")); // remark
				dto.setModifyOn(rs.getTimestamp("ModifyOn")); // modifyOn
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
				dto.setStatusIndi(rs.getString("StatusIndi").charAt(0)); // statusIndi
				dto.setStages(rs.getString("Stages"));    //Stages
				dto.setRoleName(rs.getString("RoleName"));
				moduleUserDetail.addElement(dto);
				dto = null;
			}

			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	
		return moduleUserDetail;	
	}
	//Added by Harsh Shah for managing life cycle without authorized stage for CT-Department
		public ArrayList<Integer> getStageIdsRightsWise(String wsId,int nodeId){
			ArrayList<Integer> stageIds = new ArrayList<>();
			try 
			{		  
				Connection con = ConnectionManager.ds.getConnection();
				String Where = "vWorkSpaceId = '" + wsId +"' and iNodeId = "+ nodeId;
				Where+=" and iUserCode NOT IN(select iUserCode from workspaceusermst where  vWorkSpaceId = '"+ wsId +"' and cStatusIndi = 'D')";
				ResultSet rs = dataTable.getDataSet(con, "DISTINCT iStageId", "WorkspaceUserRightsMst", Where,"");
				while(rs.next())
				{
					stageIds.add(rs.getInt("iStageId"));		
				}
				rs.close();
				con.close();
			}   
			catch(SQLException e){
				e.printStackTrace();
			}
			return stageIds;
		}
		
		public ArrayList<Integer> getStageIdsRightsWiseForESignture(String wsId,int nodeId){
			ArrayList<Integer> stageIds = new ArrayList<>();
			try 
			{		  
				Connection con = ConnectionManager.ds.getConnection();
				String Where = "vWorkSpaceId = '" + wsId +"' and iNodeId = "+ nodeId;
				Where+=" and iUserCode NOT IN(select iUserCode from workspaceusermst where  vWorkSpaceId = '"+ wsId +"' and cStatusIndi = 'D')";
				ResultSet rs = dataTable.getDataSet(con, "DISTINCT iStageId", "WorkspaceUserRightsMstForESignature", Where,"");
				while(rs.next())
				{
					stageIds.add(rs.getInt("iStageId"));		
				}
				rs.close();
				con.close();
			}   
			catch(SQLException e){
				e.printStackTrace();
			}
			return stageIds;
		}
		
		
		//Create By Virendra Barad for Show user WorkspaceNodeRights 
				public Vector<DTOWorkSpaceUserRightsMst> getUserNodeRightsDetail(String wsId, int nodeId)
				{
					DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
					Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
					ArrayList<String> time = new ArrayList<String>();
					String locationName = ActionContext.getContext().getSession().get("locationname").toString();
					String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
					Connection con = null;
					ResultSet rs = null;
					try
					{
						con = ConnectionManager.ds.getConnection();
						rs = dataTable.getDataSet(con, "vWorkspaceId,iNodeId,vUserName,vUserTypeName,dModifyOn,vModifyBy,cCanEditFlag,vStageDesc", "view_WorkspaceUserRightsDetail","vWorkspaceId = '"+wsId+"' and iNodeId ="+nodeId, "vWorkspaceId,iNodeId,vUserName");
						while(rs.next())
						{
							DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
							dto.setUserName(rs.getString("vUserName"));
							dto.setCanEditFlag(rs.getString("cCanEditFlag").charAt(0));
							dto.setStageDesc(rs.getString("vStageDesc"));
							dto.setUserTypeName(rs.getString("vUserTypeName"));
							dto.setModifyByName(rs.getString("vModifyBy"));
							dto.setModifyOn(rs.getTimestamp("dModifyOn"));
						if(countryCode.equalsIgnoreCase("IND")){
							time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
							dto.setISTDateTime(time.get(0));
						}
						else{
							time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
							dto.setISTDateTime(time.get(0));
							dto.setESTDateTime(time.get(1));
						}
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
//Added by freelancer Harsh Shah
public Vector<DTOWorkSpaceUserRightsMst> getTotalUsersByStage(String wsId, int nodeId,int stageId)
{		
		Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
		Connection con = null;
		ResultSet rs = null;
		try
		{
			con = ConnectionManager.ds.getConnection();
			//exclude admin from this
			String where = "vWorkSpaceId = '"+wsId+"' and iNodeId = "+nodeId+" and iStageId = "+stageId+" and iUserGroupCode = 3";
			rs = dataTable.getDataSet(con,"*","workspaceuserrightsmst",where,"iUserCode");
			while(rs.next())
			{
				DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setUserCode(rs.getInt("iUserCode"));
				dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
				dto.setNodeId(rs.getInt("iNodeId"));
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
//Get stage dropdown incase of same rights given to multi users
public Vector<DTOWorkSpaceUserRightsMst> getStageIds(String WorkspaceId,int NodeId,int usercode,int usergroupcode,int stageId)
{
	Vector<DTOWorkSpaceUserRightsMst> stageDtl=new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		String fields = "TOP 1 WSURM.vWorkSpaceId,WSURM.iNodeId,WSURM.iUserCode,WSURM.iUserGroupCode,WSURM.iStageId,SMST.vStageDesc ";
		String from = " workspaceuserrightsmst as WSURM INNER JOIN stageMst as SMST ON SMST.iStageId = WSURM.iStageId";
		String Where="WSURM.vWorkSpaceId ='"+WorkspaceId+"' and WSURM.iNodeId="+NodeId;
		Where+=" and WSURM.iUserCode = "+usercode+" and WSURM.iUserGroupCode = "+usergroupcode+" and WSURM.iStageId <> 0";
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,fields,from , Where,"");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto=new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setUserCode(rs.getInt("iUsercode"));
			dto.setUserGroupCode(rs.getInt("iUsergroupcode"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setStageDesc(rs.getString("vStageDesc"));
			stageDtl.addElement(dto); 
			dto=null;
		}
	}
	catch (Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return stageDtl;
}
//Get stage dropdown incase of multi rights given to  users
public Vector<DTOWorkSpaceUserRightsMst> getNextStageIds(String WorkspaceId,int NodeId,int usercode,int usergroupcode)
{
	Vector<DTOWorkSpaceUserRightsMst> stageDtl=new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		String fields = "TOP 1 WSURM.vWorkSpaceId,WSURM.iNodeId,WSURM.iUserCode,WSURM.iUserGroupCode,WSURM.iStageId,SMST.vStageDesc ";
		String from = " workspaceuserrightsmst as WSURM INNER JOIN stageMst as SMST ON SMST.iStageId = WSURM.iStageId";
		String Where="WSURM.vWorkSpaceId ='"+WorkspaceId+"' and WSURM.iNodeId="+NodeId;
		Where+=" and WSURM.iUserCode = "+usercode+" and WSURM.iUserGroupCode = "+usergroupcode;
		Where+=" and WSURM.iStageId NOT IN (select iStageId from assignNodeRights Where vWorkSpaceId ='"+WorkspaceId;
		Where+=" ' and iNodeId="+NodeId+")" ;
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,fields,from , Where,"");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto=new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setUserCode(rs.getInt("iUsercode"));
			dto.setUserGroupCode(rs.getInt("iUsergroupcode"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setStageDesc(rs.getString("vStageDesc"));
			stageDtl.addElement(dto); 
			dto=null;
		}
	}
	catch (Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return stageDtl;
}
public Vector<DTOWorkSpaceUserRightsMst> getAllStageIdsForAdmin(String WorkspaceId,int NodeId,int usercode,int usergroupcode)
{
	Vector<DTOWorkSpaceUserRightsMst> stageDtl=new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		String fields = "WSURM.vWorkSpaceId,WSURM.iNodeId,WSURM.iUserCode,WSURM.iUserGroupCode,WSURM.iStageId,SMST.vStageDesc ";
		String from = " workspaceuserrightsmst as WSURM INNER JOIN stageMst as SMST ON SMST.iStageId = WSURM.iStageId";
		String Where="WSURM.vWorkSpaceId ='"+WorkspaceId+"' and WSURM.iNodeId="+NodeId;
		Where+=" and WSURM.iUserCode = "+usercode+" and WSURM.iUserGroupCode = "+usergroupcode;
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,fields,from , Where,"");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto=new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setUserCode(rs.getInt("iUsercode"));
			dto.setUserGroupCode(rs.getInt("iUsergroupcode"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setStageDesc(rs.getString("vStageDesc"));
			stageDtl.addElement(dto); 
			dto=null;
		}
	}
	catch (Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return stageDtl;
}
public Vector<DTOWorkSpaceUserRightsMst> getAllRightsUserWise(String WorkspaceId,int NodeId,int usercode,int usergroupcode)
{
	Vector<DTOWorkSpaceUserRightsMst> stageDtl=new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		//String fields = "WSURM.vWorkSpaceId,WSURM.iNodeId,WSURM.iUserCode,WSURM.iUserGroupCode,WSURM.iStageId,SMST.vStageDesc ";
		//String from = " workspaceuserrightsmst as WSURM INNER JOIN stageMst as SMST ON SMST.iStageId = WSURM.iStageId";
		String Where="vWorkSpaceId ='"+WorkspaceId+"' and iNodeId="+NodeId;
		Where+=" and iUserCode = "+usercode+" and iUserGroupCode = "+usergroupcode;
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*","workspaceuserrightsmst" , Where,"");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto=new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));	
			dto.setUserGroupCode(rs.getInt("iUsergroupcode"));
			dto.setUserCode(rs.getInt("iUsercode"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setStageId(rs.getInt("iStageId"));			
			stageDtl.addElement(dto); 
			dto=null;
		}
	}
	catch (Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return stageDtl;
}
public Vector<DTOWorkSpaceUserRightsMst> getNextStageRightsWise(String WorkspaceId,int NodeId,int usercode,int usergroupcode,int stageId,int mode)
{
	Vector<DTOWorkSpaceUserRightsMst> stageDtl=new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		String fields = "WSURM.vWorkSpaceId,WSURM.iNodeId,WSURM.iUserCode,WSURM.iUserGroupCode,WSURM.iStageId,SMST.vStageDesc ";
		String from = " workspaceuserrightsmst as WSURM INNER JOIN stageMst as SMST ON SMST.iStageId = WSURM.iStageId";
		String Where="WSURM.vWorkSpaceId ='"+WorkspaceId+"' and WSURM.iNodeId="+NodeId;
		Where+=" and WSURM.iUserCode = "+usercode+" and WSURM.iUserGroupCode = "+usergroupcode;		
		if(mode == 1){
			Where+=" and SMST.iStageId>"+stageId+ " and SMST.iStageId<>40 ";
		}
		else if(mode == 2){
			Where+=" and SMST.iStageId = "+stageId;
		}
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,fields,from, Where,"iStageId");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto=new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));	
			dto.setUserGroupCode(rs.getInt("iUsergroupcode"));
			dto.setUserCode(rs.getInt("iUsercode"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setStageId(rs.getInt("iStageId"));		
			dto.setStageDesc(rs.getString("vStageDesc"));
			stageDtl.addElement(dto); 
			dto=null;
		}
	}
	catch (Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return stageDtl;
}
public boolean flagReturnForNxtStage(String wsId, int nodeId,int stageId)
{		
		Vector<DTOWorkSpaceUserRightsMst> data1 = new Vector<DTOWorkSpaceUserRightsMst>();
		Vector<DTOWorkSpaceUserRightsMst> data2 = new Vector<DTOWorkSpaceUserRightsMst>();
		Connection con1 = null;
		ResultSet rs1 = null;
		Connection con2 = null;
		ResultSet rs2 = null;		
		try
		{
			con1 = ConnectionManager.ds.getConnection();
			con2 = ConnectionManager.ds.getConnection();
			//exclude admin from this
			String where1 = "vWorkSpaceId = '"+wsId+"' and iNodeId = "+nodeId+" and iStageId = "+stageId+" and iUserGroupCode <> 2";
			String where2 = "vWorkSpaceId = '"+wsId+"' and iNodeId = "+nodeId+" and iStageId = "+stageId;
			
			rs1 = dataTable.getDataSet(con1,"*","workspaceuserrightsmst",where1,"iUserCode");
			rs2 = dataTable.getDataSet(con2,"*","assignNodeRights",where2,"iUserCode");
			
			while(rs1.next())
			{
				DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
				dto.setWorkSpaceId(rs1.getString("vWorkSpaceId"));
				dto.setStageId(rs1.getInt("iStageId"));
				dto.setUserCode(rs1.getInt("iUserCode"));
				dto.setUserGroupCode(rs1.getInt("iUserGroupCode"));
				dto.setNodeId(rs1.getInt("iNodeId"));
				data1.addElement(dto);
			}
			
			while(rs2.next())
			{
				DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
				dto.setWorkSpaceId(rs2.getString("vWorkSpaceId"));
				dto.setStageId(rs2.getInt("iStageId"));
				dto.setUserCode(rs2.getInt("iUserCode"));
				dto.setUserGroupCode(rs2.getInt("iUserGroupCode"));
				dto.setNodeId(rs2.getInt("iNodeId"));
				data2.addElement(dto);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {if(rs1 != null){rs1.close();}} catch (Exception ex) {ex.printStackTrace();}
			try {if(con1 != null){con1.close();}} catch (Exception ex) {ex.printStackTrace();}
			try {if(rs2 != null){rs2.close();}} catch (Exception ex) {ex.printStackTrace();}
			try {if(con2 != null){con2.close();}} catch (Exception ex) {ex.printStackTrace();}
		}
		if(data1.size() == data2.size())
			return true;
		return false;
}
public ArrayList<DTOStageWiseMailReport> getNodeStageDetail(String wsId, int nodeId,int stageId)
{
	ArrayList<DTOStageWiseMailReport> data = new ArrayList<DTOStageWiseMailReport>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*", "view_StageWiseMailReportHistory","vWorkspaceId = '"+wsId+"' and iNodeId ="+nodeId + "and iStageId ="+stageId,"");
		while(rs.next())
		{
			DTOStageWiseMailReport dto = new DTOStageWiseMailReport();
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setWorkspaceDesc(rs.getString("vWorkspaceDesc"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setUserName(rs.getString("vUserName"));
			dto.setLoginName(rs.getString("vLoginName"));
			data.add(dto);
		}
	}catch(Exception e){
		e.printStackTrace();
	}

	return data;
}
public boolean getCompletedNodeStageDetail(String wsId,int nodeId,int stageId) {
	
	boolean completedStage=true;
	
	ArrayList<DTOStageWiseMailReport> data = new ArrayList<DTOStageWiseMailReport>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*", "view_workspaceUserTrackingDetail","vWorkspaceId = '"+wsId+"' and iNodeId ="+nodeId 
				                      +" and iStageId ="+stageId+" and iCompletedStageId = 0","iStageId");
		while(rs.next())
		{
			DTOStageWiseMailReport dto = new DTOStageWiseMailReport();
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setUserName(rs.getString("vUserName"));
			dto.setUserGroupName(rs.getString("vUserGroupName"));
			dto.setCompletedStageId(rs.getInt("iCompletedStageId"));
			data.add(dto);
		}
		if(data.size()>0){
			completedStage=false;
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	return completedStage;
}
public boolean getNextStageFlag(String wsId,int nodeId,int userCode) {
	boolean nextStageFlag=true;
	String flag = "";
	try {
		Connection con = ConnectionManager.ds.getConnection();
		String where = " vWorkspaceId = '" + wsId + "' and iNodeId = "+nodeId+" and iModifyby="+userCode+" and iStageId=10";
		ResultSet rs = dataTable.getDataSet(con, "vFlag","assignNodeRights", where, "");
		if (rs.next()) {
			flag = rs.getString("vFlag");
		}
		if(flag.equalsIgnoreCase("SR")) {
			nextStageFlag=false;
		}
		rs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return nextStageFlag;
}

public boolean getNextStageFlagByUser(String wsId,int nodeId,int userCode) {
	boolean nextStageFlag=true;
	int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	int modifyUser = 0;
	String flag = "";
	try {
		Connection con = ConnectionManager.ds.getConnection();
		String where = " vWorkspaceId = '" + wsId + "' and iNodeId = "+nodeId+" and iModifyby="+userCode+" and iStageId=10";
		ResultSet rs = dataTable.getDataSet(con, "vFlag,iModifyBy","assignNodeRights", where, "");
		if (rs.next()) {
			flag = rs.getString("vFlag");
			modifyUser = rs.getInt("iModifyBy");
		}
	   if(flag.equals("") && usercode==modifyUser){
			nextStageFlag=false;
		}
		rs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return nextStageFlag;
}

public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForCSV(String wsId, int nodeId,int userCode)
{
	//DataTable dataTable = new DataTable();
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
	
		String where="iUserCode = "+userCode;
		 System.out.println(where);
		
	
		rs = dataTable.getDataSet(con, "*", "workspaceuserrightsmst","vWorkspaceId = '"+wsId+"' and iNodeId ="+nodeId+" and "+where, "istageid");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setUserName(rs.getString("vworkspaceid"));
			dto.setNodeId(rs.getInt("inodeid"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setRoleCode(rs.getString("vRoleCode"));
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
public boolean RemoveFolderSpecificMultipleUserRightsForCSV(String wsId,int users,int[] stageId,String nodeIdsCSV) {
	try {
		String userCodes= String.valueOf(users);
		String stageIds="";
		stageIds= String.valueOf(stageId[0]);
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs = con
				.prepareCall("{call Proc_DeleteFolderSpecificMultipleUserRights(?,?,?,?)}");
		cs.setString(1, wsId);
		cs.setString(2,userCodes);
		cs.setString(3,stageIds);
		cs.setString(4,nodeIdsCSV);
		cs.execute();
		cs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return true;
}
public void insertMultipleUserRightsForCSV(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList)
{
	Connection con = null;
	CallableStatement cs = null;
	try
	{
		/*Sort Array List by usercode*/
		Collections.sort(userRightsList, new Comparator<DTOWorkSpaceUserRightsMst>() {
			public int compare(DTOWorkSpaceUserRightsMst arg0, DTOWorkSpaceUserRightsMst arg1) {
				if(arg0.getUserCode() == arg1.getUserCode()){ 
					return arg0.getWorkSpaceId().compareTo(arg1.getWorkSpaceId());
				}
				else if(arg0.getUserCode() < arg1.getUserCode())
					return -1;
				else //if(arg0.getUserCode() > arg1.getUserCode())
					return 1;
			}
		});
		
		con=ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_MultipleUserRights(?,?,?,?,?,?,?,?,?,?,?,?)}");
		
		for(int i=0;i<userRightsList.size();i++)
		{
			DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst=userRightsList.get(i);
			cs.setString(1, dtoWorkSpaceUserRightsMst.getWorkSpaceId());
			cs.setInt(2, dtoWorkSpaceUserRightsMst.getUserGroupCode());
			cs.setInt(3, dtoWorkSpaceUserRightsMst.getUserCode());
			cs.setString(4,Character.toString(dtoWorkSpaceUserRightsMst.getCanReadFlag()));
			cs.setString(5, Character.toString(dtoWorkSpaceUserRightsMst.getCanAddFlag()));
			cs.setString(6, Character.toString(dtoWorkSpaceUserRightsMst.getCanEditFlag()));
			cs.setString(7, Character.toString(dtoWorkSpaceUserRightsMst.getCanDeleteFlag()));
			cs.setString(8, dtoWorkSpaceUserRightsMst.getAdvancedRights());
			cs.setString(9, dtoWorkSpaceUserRightsMst.getRemark());
			cs.setInt(10, dtoWorkSpaceUserRightsMst.getModifyBy());
			cs.setInt(11, dtoWorkSpaceUserRightsMst.getStageId());
		
			cs.setInt(12,1);//Insert userrights for one stage.
			cs.execute();
		}
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}
public boolean RemoveUserRightsfromTimeline(String wsId,String[] users,int[] stageId,String nodeIdsCSV) {
	try {
		String userCodes="";
		String stageIds="";
		for(int i=0;i<users.length;i++){
			userCodes+=users[i]+",";
		}
		for(int i=0;i<stageId.length;i++){
			stageIds+=stageId[i]+",";
		}
		userCodes= userCodes.substring(0, userCodes.length() - 1);
		stageIds= stageIds.substring(0, stageIds.length() - 1);
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs = con
				.prepareCall("{call Proc_DeleteFolderSpecificMultipleUserRightsForTimeLine(?,?,?,?)}");
		cs.setString(1, wsId);
		cs.setString(2,userCodes);
		cs.setString(3,stageIds);
		cs.setString(4,nodeIdsCSV);
		cs.execute();
		cs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return true;
}
public void insertFolderSpecificMultipleUserRightsForTimeLine(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList)
{
	Connection con = null;
	CallableStatement cs = null;
	try
	{
		/*Sort Array List by usercode*/
		Collections.sort(userRightsList, new Comparator<DTOWorkSpaceUserRightsMst>() {
			public int compare(DTOWorkSpaceUserRightsMst arg0, DTOWorkSpaceUserRightsMst arg1) {
				if(arg0.getUserCode() == arg1.getUserCode()){ 
					return arg0.getWorkSpaceId().compareTo(arg1.getWorkSpaceId());
				}
				else if(arg0.getUserCode() < arg1.getUserCode())
					return -1;
				else //if(arg0.getUserCode() > arg1.getUserCode())
					return 1;
			}
		});
		
		con=ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_FolderSpecificUserRightsForTimeLine(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		int prevUserCode = 0;
		String prevWorkspaceId = "";
		
		for(int i=0;i<userRightsList.size();i++)
		{
			DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst=userRightsList.get(i);
			cs.setString(1, dtoWorkSpaceUserRightsMst.getWorkSpaceId());
			cs.setInt(2, dtoWorkSpaceUserRightsMst.getUserGroupCode());
			cs.setInt(3, dtoWorkSpaceUserRightsMst.getUserCode());
			cs.setInt(4, dtoWorkSpaceUserRightsMst.getNodeId());
			cs.setInt(5, dtoWorkSpaceUserRightsMst.getExistUserCode());
			cs.setString(6,Character.toString(dtoWorkSpaceUserRightsMst.getCanReadFlag()));
			cs.setString(7, Character.toString(dtoWorkSpaceUserRightsMst.getCanAddFlag()));
			cs.setString(8, Character.toString(dtoWorkSpaceUserRightsMst.getCanEditFlag()));
			cs.setString(9, Character.toString(dtoWorkSpaceUserRightsMst.getCanDeleteFlag()));
			cs.setString(10, dtoWorkSpaceUserRightsMst.getAdvancedRights());
			cs.setString(11, dtoWorkSpaceUserRightsMst.getRemark());
			cs.setInt(12, dtoWorkSpaceUserRightsMst.getDuration());
			cs.setInt(13, dtoWorkSpaceUserRightsMst.getModifyBy());
			cs.setInt(14, dtoWorkSpaceUserRightsMst.getStageId());
			
			cs.setInt(15,dtoWorkSpaceUserRightsMst.getMode());//Insert userrights for one stage.
			cs.execute();
			
			prevUserCode = dtoWorkSpaceUserRightsMst.getUserCode();
			prevWorkspaceId = dtoWorkSpaceUserRightsMst.getWorkSpaceId();
		}
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}

public void AttachUserRightsForTimeLine(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList)
{
	Connection con = null;
	CallableStatement cs = null;
	try
	{
		/*Sort Array List by usercode*/
		Collections.sort(userRightsList, new Comparator<DTOWorkSpaceUserRightsMst>() {
			public int compare(DTOWorkSpaceUserRightsMst arg0, DTOWorkSpaceUserRightsMst arg1) {
				if(arg0.getUserCode() == arg1.getUserCode()){ 
					return arg0.getWorkSpaceId().compareTo(arg1.getWorkSpaceId());
				}
				else if(arg0.getUserCode() < arg1.getUserCode())
					return -1;
				else //if(arg0.getUserCode() > arg1.getUserCode())
					return 1;
			}
		});
		
		con=ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_AttachUserRightsForTimeLine(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		int prevUserCode = 0;
		String prevWorkspaceId = "";
		
		for(int i=0;i<userRightsList.size();i++)
		{
			DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst=userRightsList.get(i);
			cs.setString(1, dtoWorkSpaceUserRightsMst.getWorkSpaceId());
			cs.setInt(2, dtoWorkSpaceUserRightsMst.getUserGroupCode());
			cs.setInt(3, dtoWorkSpaceUserRightsMst.getUserCode());
			cs.setInt(4, dtoWorkSpaceUserRightsMst.getNodeId());
			cs.setInt(5, dtoWorkSpaceUserRightsMst.getExistUserCode());
			cs.setString(6,Character.toString(dtoWorkSpaceUserRightsMst.getCanReadFlag()));
			cs.setString(7, Character.toString(dtoWorkSpaceUserRightsMst.getCanAddFlag()));
			cs.setString(8, Character.toString(dtoWorkSpaceUserRightsMst.getCanEditFlag()));
			cs.setString(9, Character.toString(dtoWorkSpaceUserRightsMst.getCanDeleteFlag()));
			cs.setString(10, dtoWorkSpaceUserRightsMst.getAdvancedRights());
			cs.setString(11, dtoWorkSpaceUserRightsMst.getRemark());
			cs.setInt(12, dtoWorkSpaceUserRightsMst.getDuration());
			cs.setInt(13, dtoWorkSpaceUserRightsMst.getModifyBy());
			cs.setInt(14, dtoWorkSpaceUserRightsMst.getStageId());
			cs.setTimestamp(15, dtoWorkSpaceUserRightsMst.getStartDate());
			cs.setTimestamp(16, dtoWorkSpaceUserRightsMst.getEndDate());
			cs.setTimestamp(17, dtoWorkSpaceUserRightsMst.getAdjustDate());
			cs.setInt(18,dtoWorkSpaceUserRightsMst.getMode());//Insert userrights for one stage.
			cs.execute();
			
			prevUserCode = dtoWorkSpaceUserRightsMst.getUserCode();
			prevWorkspaceId = dtoWorkSpaceUserRightsMst.getWorkSpaceId();
		}
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}
public boolean RemoveFolderSpecificMultipleUserRightsForTimeLine(String wsId,int users,int[] stageId,String nodeIdsCSV) {
	try {
		String userCodes= String.valueOf(users);
		String stageIds="";
		stageIds= String.valueOf(stageId[0]);
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs = con
				.prepareCall("{call Proc_DeleteFolderSpecificMultipleUserRightsForTimeLine(?,?,?,?)}");
		cs.setString(1, wsId);
		cs.setString(2,userCodes);
		cs.setString(3,stageIds);
		cs.setString(4,nodeIdsCSV);
		cs.execute();
		cs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return true;
}
public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForCSVforTimeLine(String wsId, int nodeId,int userCode)
{
	//DataTable dataTable = new DataTable();
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
	
		String where="iUserCode = "+userCode;
		 System.out.println(where);
		
	
		rs = dataTable.getDataSet(con, "*", "timelineworkspaceuserrightsmst","vWorkspaceId = '"+wsId+"' and iNodeId ="+nodeId+" and "+where, "istageid");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setUserName(rs.getString("vworkspaceid"));
			dto.setNodeId(rs.getInt("inodeid"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setDuration(rs.getInt("iHours"));
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
public Vector<DTOWorkSpaceUserRightsMst> getStageUserDetailForDocSign(String WorkspaceId,int NodeId,int usercode)
{
	Vector<DTOWorkSpaceUserRightsMst> stageDtl=new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		String Where="vWorkspaceId='"+WorkspaceId+"' and iNodeId="+NodeId+" and iusercode="+usercode;
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*","view_MyPendingWorkActivityCSV" , Where,"nextStageId");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto=new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vworkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setStageId(rs.getInt("nextStageId"));
			dto.setStageDesc(rs.getString("nextStageDesc"));
			stageDtl.addElement(dto); 
			dto=null;
		}
	}
	catch (Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return stageDtl;
}
public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineData(String workspaceID,int userGroupCode,int userCode)
{
	Vector<DTOWorkSpaceUserRightsMst> nodeInfo = new Vector<>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		
		con=ConnectionManager.ds.getConnection();
		String Fields="Distinct WorkspaceId,usergroupcode,usercode,NodeNo,NodeId,NodeDisplayName,NodeName,ParentNodeId,Foldername,Username,userGroupName," +
		"stageid,canReadFlag,canAddFlag,canEditFlag,statusIndi,canDeleteFlag,iformNo";
		String Where="WorkspaceId='"+workspaceID+"' and usergroupcode="+userGroupCode+" and usercode="+userCode+" and StatusIndi<>'D'";
		rs=dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail" , Where,"ParentNodeId,NodeNo" ); 
		while (rs.next())  
		{
			DTOWorkSpaceUserRightsMst dtowurmst = new DTOWorkSpaceUserRightsMst();
			dtowurmst.setWorkSpaceId(rs.getString("WorkspaceId"));
			//dtowurmst.setWorkSpaceDesc(rs.getString("WorkspaceDesc"));	//workSpaceDesc from workspaceMst
			dtowurmst.setNodeName(rs.getString("NodeName"));
			dtowurmst.setNodeId(rs.getInt("NodeId"));
			dtowurmst.setNodeDisplayName(rs.getString("NodeDisplayName"));	//nodeDisplayName from workSpaceNodeDetail
			dtowurmst.setFolderName(rs.getString("FolderName"));
			dtowurmst.setUserName(rs.getString("Username"));			//userName from userMst
			dtowurmst.setUserGroupName(rs.getString("userGroupName"));	//userGroupName from userGroupMst
			dtowurmst.setReadRights(rs.getString("canReadFlag"));		//readRights from workspaceUserRights
			dtowurmst.setEditRights(rs.getString("canEditFlag"));		//editRights from workspaceUserRights
			dtowurmst.setUserCode(rs.getInt("UserCode"));			//userCode from workspaceUserRights
			dtowurmst.setNodeId(rs.getInt("NodeId"));				//nodeId from workspaceUserRights
			dtowurmst.setStageId(rs.getInt("stageid"));
			nodeInfo.add(dtowurmst);
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}

public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineDetails(String workspaceID)
{
	Vector<DTOWorkSpaceUserRightsMst> nodeInfo = new Vector<>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		
		con=ConnectionManager.ds.getConnection();
		String Fields="Distinct vWorkspaceId,iusercode,iusergroupcode,iNodeId," +
		"istageid,iHours,dStartDate,dEndDate,dAdjustDate";
		String Where="vWorkspaceId='"+workspaceID+"'  and cStatusIndi<>'D'";
		rs=dataTable.getDataSet(con,Fields,"timelineworkspaceuserrightsmst" , Where,"inodeId" ); 
		while (rs.next())  
		{
			DTOWorkSpaceUserRightsMst dtowurmst = new DTOWorkSpaceUserRightsMst();
			dtowurmst.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dtowurmst.setUserCode(rs.getInt("iusercode"));
			dtowurmst.setUserGroupCode(rs.getInt("iusergroupcode"));
			dtowurmst.setNodeId(rs.getInt("iNodeId"));
			dtowurmst.setStageId(rs.getInt("istageid"));
			dtowurmst.setHours(rs.getInt("iHours"));
			dtowurmst.setStartDate(rs.getTimestamp("dStartDate"));
			dtowurmst.setStartDate(rs.getTimestamp("dEndDate"));
			dtowurmst.setStartDate(rs.getTimestamp("dAdjustDate"));
			nodeInfo.add(dtowurmst);
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}

public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineDetailsForHours(String workspaceID)
{
	Vector<DTOWorkSpaceUserRightsMst> nodeInfo = new Vector<>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		
		con=ConnectionManager.ds.getConnection();
		/*String Fields="vWorkspaceId,iusercode,iusergroupcode,iNodeId," +
		"istageid,iHours,dStartDate,dEndDate,dAdjustDate";
		String Where="vWorkspaceId='"+workspaceID+"'  and cStatusIndi<>'D'";*/
		
		

		String Fields="tdtl.vWorkspaceId,tdtl.iusercode,tdtl.iusergroupcode,tdtl.iNodeId,tdtl.istageid,tdtl.iHours,tdtl.dStartDate,tdtl.dEndDate,tdtl.dAdjustDate";
		
		String From="Proc_getProjectTrackingeCSVChildNodesHours('"+workspaceID+"') as ptdtl inner join"+ 
			" view_timelineworkspaceuserrightsmstdetail as tdtl on ptdtl.vWorkspaceId=tdtl.vWorkspaceId and ptdtl.iNodeId=tdtl.iNodeId";
	
		String Where="tdtl.vWorkspaceId='"+workspaceID+"' and tdtl.cStatusIndi<>'D'"; 
		
		
		rs=dataTable.getDataSet(con,Fields,From, Where,"ptdtl.nRID,tdtl.istageid,tdtl.id" ); 
		while (rs.next())  
		{
			DTOWorkSpaceUserRightsMst dtowurmst = new DTOWorkSpaceUserRightsMst();
			dtowurmst.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dtowurmst.setUserCode(rs.getInt("iusercode"));
			dtowurmst.setUserGroupCode(rs.getInt("iusergroupcode"));
			dtowurmst.setNodeId(rs.getInt("iNodeId"));
			dtowurmst.setStageId(rs.getInt("istageid"));
			dtowurmst.setHours(rs.getInt("iHours"));
			dtowurmst.setStartDate(rs.getTimestamp("dStartDate"));
			dtowurmst.setEndDate(rs.getTimestamp("dEndDate"));
			dtowurmst.setAdjustDate(rs.getTimestamp("dAdjustDate"));
			nodeInfo.add(dtowurmst);
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}

public void updateTimelineDatesValue(DTOWorkSpaceUserRightsMst dto)  
{
	Connection con = null;
	CallableStatement cs = null;
	try 
	{
		 con = ConnectionManager.ds.getConnection();
		 cs=con.prepareCall("{call Proc_updateTimelineDatesValue(?,?,?,?,?,?,?,?)}");
		 cs.setString(1, dto.getWorkSpaceId());
		 cs.setInt(2, dto.getNodeId());
		 cs.setInt(3,dto.getUserCode());
		 cs.setInt(4, dto.getUserGroupCode());
		 cs.setInt(5, dto.getStageId());
		 cs.setTimestamp(6, dto.getStartDate());
		 cs.setTimestamp(7, dto.getEndDate());
		 cs.setTimestamp(8, dto.getAdjustDate());
		 
		 cs.execute();	
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}

}

public void updateTimelineAdjustDate(DTOWorkSpaceUserRightsMst dto)  
{
	Connection con = null;
	CallableStatement cs = null;
	try 
	{
		 con = ConnectionManager.ds.getConnection();
		 cs=con.prepareCall("{call Proc_updateTimelineAdjustDate(?,?,?,?,?,?)}");
		 cs.setString(1, dto.getWorkSpaceId());
		 cs.setInt(2, dto.getNodeId());
		 cs.setInt(3,dto.getUserCode());
		 cs.setInt(4, dto.getUserGroupCode());
		 cs.setInt(5, dto.getStageId());
		 cs.setTimestamp(6, dto.getAdjustDate());
		 
		 cs.execute();	
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}

}

public void UpdateHoursInTimeWorkspaceRightsMst(String wsId,int nodeId,int userCode,int userGrpCode,int duration,int stageId) {
	Connection con = null;
	int rs=0;
	
	try {
		con = ConnectionManager.ds.getConnection();
		
		String query = "UPDATE timelineworkspaceuserrightsmst SET " + "iHours = "+duration
						+ " WHERE vWorkspaceId='"+ wsId + "' AND iNodeId="+ nodeId+ " AND iUserCode="+userCode
						+ " AND iUserGroupCode="+userGrpCode+" AND iStageId="+stageId;
		
		rs =dataTable.getDataSet1(con,query);
		
		con.close();
	}   
	
	catch(SQLException e){
		e.printStackTrace();
	}
}

public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineSRFlagData(String workspaceID)
{
	Vector<DTOWorkSpaceUserRightsMst> nodeInfo = new Vector<>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		
		con=ConnectionManager.ds.getConnection();
		String Fields="vWorkspaceId,iusercode,iusergroupcode,iNodeId," +
		"istageid,iHours,dStartDate,dEndDate,dAdjustDate";
		String Where="vWorkspaceId='"+workspaceID+"' and vFlag='SR' and cStatusIndi<>'D'";
		rs=dataTable.getDataSet(con,Fields,"view_timelineworkspaceuserrightsmstdetail " , Where,"iparentnodeid,inodeno,istageid" ); 
		while (rs.next())  
		{
			DTOWorkSpaceUserRightsMst dtowurmst = new DTOWorkSpaceUserRightsMst();
			dtowurmst.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dtowurmst.setUserCode(rs.getInt("iusercode"));
			dtowurmst.setUserGroupCode(rs.getInt("iusergroupcode"));
			dtowurmst.setNodeId(rs.getInt("iNodeId"));
			dtowurmst.setStageId(rs.getInt("istageid"));
			dtowurmst.setHours(rs.getInt("iHours"));
			dtowurmst.setStartDate(rs.getTimestamp("dStartDate"));
			dtowurmst.setEndDate(rs.getTimestamp("dEndDate"));
			dtowurmst.setAdjustDate(rs.getTimestamp("dAdjustDate"));
			nodeInfo.add(dtowurmst);
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}

public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineAdjustDateInfo(String wsId,int nodeId,int parentNodeId,int userCode,int stageId)
{
	Vector<DTOWorkSpaceUserRightsMst> nodeInfo = new Vector<>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		
		con=ConnectionManager.ds.getConnection();
		String Fields="vWorkspaceId,iusercode,iusergroupcode,iNodeId,istageid,iHours,dStartDate,dEndDate,dAdjustDate";
		
		String From="view_timelineworkspaceuserrightsmstdetail";
		
		String Where="vWorkspaceId='"+wsId+"' and iParentnodeId >="+parentNodeId+" and cStatusIndi<>'D' "
				+ " and inodeid not in (select inodeId from workspacenodedetail where  vWorkspaceId ='"+wsId+"' and iparentnodeid="+parentNodeId+"  "
				+" and iNodeNo <=(select iNodeNo from workspacenodedetail"
									+" where vworkspaceid='"+wsId+"' and iparentnodeid="+parentNodeId+" and iNodeId="+nodeId+"))"
				+ " or iNodeId="+nodeId+" and id not in ( Select id from workspacenodedetail where inodeid ="+nodeId+" and inodeno = 1 and "
				+ "iusercode="+userCode+" and istageid="+stageId+") AND dModifyOn =Convert(datetime, '1900-01-01' )";
		
		rs=dataTable.getDataSet(con,Fields,From , Where,"iParentNodeId,inodeno,istageid" ); 
		while (rs.next())  
		{
			DTOWorkSpaceUserRightsMst dtowurmst = new DTOWorkSpaceUserRightsMst();
			dtowurmst.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dtowurmst.setUserCode(rs.getInt("iusercode"));
			dtowurmst.setUserGroupCode(rs.getInt("iusergroupcode"));
			dtowurmst.setNodeId(rs.getInt("iNodeId"));
			dtowurmst.setStageId(rs.getInt("istageid"));
			dtowurmst.setHours(rs.getInt("iHours"));
			dtowurmst.setStartDate(rs.getTimestamp("dStartDate"));
			dtowurmst.setEndDate(rs.getTimestamp("dEndDate"));
			dtowurmst.setAdjustDate(rs.getTimestamp("dAdjustDate"));
			nodeInfo.add(dtowurmst);
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}
public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineAdjustDateRepeatNodeandChildNodeInfo(String wsId,int nodeId,int parentNodeId)
{
	Vector<DTOWorkSpaceUserRightsMst> nodeInfo = new Vector<>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		
		con=ConnectionManager.ds.getConnection();
		/*String Fields="vWorkspaceId,iusercode,iusergroupcode,iNodeId,istageid,iHours,dStartDate,dEndDate,dAdjustDate";
		
		String From="view_timelineworkspaceuserrightsmstdetail";
		
		String Where="vWorkspaceId='"+wsId+"' and iParentnodeId >="+parentNodeId+" and cStatusIndi<>'D' "
				+ " and inodeid not in (select inodeId from workspacenodedetail where  vWorkspaceId ='"+wsId+"' and iparentnodeid="+parentNodeId+"  "
				+" and iNodeNo <=(select iNodeNo from workspacenodedetail"
									+" where vworkspaceid='"+wsId+"' and iparentnodeid="+parentNodeId+" and iNodeId="+nodeId+"))"
				+ " or iNodeId="+nodeId+"  AND dModifyOn =Convert(datetime, '1900-01-01' )";*/
		String Fields="tdtl.vWorkspaceId,tdtl.iusercode,tdtl.iusergroupcode,tdtl.iNodeId,tdtl.istageid,tdtl.iHours,tdtl.dStartDate,tdtl.dEndDate,tdtl.dAdjustDate";
		
		String From="Proc_getProjectTrackingeCSVChildNodes('"+wsId+"',"+nodeId+") as ptdtl inner join"+ 
			" view_timelineworkspaceuserrightsmstdetail as tdtl on ptdtl.vWorkspaceId=tdtl.vWorkspaceId and ptdtl.iNodeId=tdtl.iNodeId";
	
		String Where="tdtl.vWorkspaceId='"+wsId+"' and tdtl.cStatusIndi<>'D' AND tdtl.dModifyOn =Convert(datetime, '1900-01-01' )"; 
		
		rs=dataTable.getDataSet(con,Fields,From , Where,"ptdtl.nRID,tdtl.istageid,tdtl.id" ); 
		while (rs.next())  
		{
			DTOWorkSpaceUserRightsMst dtowurmst = new DTOWorkSpaceUserRightsMst();
			dtowurmst.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dtowurmst.setUserCode(rs.getInt("iusercode"));
			dtowurmst.setUserGroupCode(rs.getInt("iusergroupcode"));
			dtowurmst.setNodeId(rs.getInt("iNodeId"));
			dtowurmst.setStageId(rs.getInt("istageid"));
			dtowurmst.setHours(rs.getInt("iHours"));
			dtowurmst.setStartDate(rs.getTimestamp("dStartDate"));
			dtowurmst.setEndDate(rs.getTimestamp("dEndDate"));
			dtowurmst.setAdjustDate(rs.getTimestamp("dAdjustDate"));
			nodeInfo.add(dtowurmst);
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}
public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineAdjustDateRepeatNodeInfo(String wsId,int nodeId,int parentNodeId)
{
	Vector<DTOWorkSpaceUserRightsMst> nodeInfo = new Vector<>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		
		con=ConnectionManager.ds.getConnection();
		/*String Fields="vWorkspaceId,iusercode,iusergroupcode,iNodeId,istageid,iHours,dStartDate,dEndDate,dAdjustDate";
		
		String From="view_timelineworkspaceuserrightsmstdetail";
		
		String Where="vWorkspaceId='"+wsId+"' and iParentnodeId >="+parentNodeId+" and cStatusIndi<>'D' "
				+ " and inodeid not in (select inodeId from workspacenodedetail where  vWorkspaceId ='"+wsId+"' and iparentnodeid="+parentNodeId+"  "
				+" and iNodeNo <=(select iNodeNo from workspacenodedetail"
				+" where vworkspaceid='"+wsId+"' and iparentnodeid="+parentNodeId+" and iNodeId="+nodeId+")) AND dModifyOn =Convert(datetime, '1900-01-01' )";
			*/
	String Fields="tdtl.vWorkspaceId,tdtl.iusercode,tdtl.iusergroupcode,tdtl.iNodeId,tdtl.istageid,tdtl.iHours,tdtl.dStartDate,tdtl.dEndDate,tdtl.dAdjustDate";
		
		String From="Proc_getProjectTrackingeCSVChildNodes('"+wsId+"',"+nodeId+") as ptdtl inner join"+ 
			" view_timelineworkspaceuserrightsmstdetail as tdtl on ptdtl.vWorkspaceId=tdtl.vWorkspaceId and ptdtl.iNodeId=tdtl.iNodeId";
	
		String Where="tdtl.vWorkspaceId='"+wsId+"' and tdtl.cStatusIndi<>'D' AND tdtl.dModifyOn =Convert(datetime, '1900-01-01' )"; 
		
		rs=dataTable.getDataSet(con,Fields,From , Where,"ptdtl.nRID,tdtl.istageid,tdtl.id" ); 
		while (rs.next())  
		{
			DTOWorkSpaceUserRightsMst dtowurmst = new DTOWorkSpaceUserRightsMst();
			dtowurmst.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dtowurmst.setUserCode(rs.getInt("iusercode"));
			dtowurmst.setUserGroupCode(rs.getInt("iusergroupcode"));
			dtowurmst.setNodeId(rs.getInt("iNodeId"));
			dtowurmst.setStageId(rs.getInt("istageid"));
			dtowurmst.setHours(rs.getInt("iHours"));
			dtowurmst.setStartDate(rs.getTimestamp("dStartDate"));
			dtowurmst.setEndDate(rs.getTimestamp("dEndDate"));
			dtowurmst.setAdjustDate(rs.getTimestamp("dAdjustDate"));
			nodeInfo.add(dtowurmst);
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;			
}
public Vector<DTOWorkSpaceUserRightsMst> getTimeLineRightsMst(String wsId,int nodeId,int userCode,int userGrpCode, int stageId){
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		String where = "vWorkspaceId='"+ wsId + "' AND iNodeId="+ nodeId+ " AND iUserCode="+userCode
						+ " AND iUserGroupCode="+userGrpCode+" AND iStageId="+stageId;
		rs = dataTable.getDataSet(con,"*","timelineworkspaceuserrightsmst",where,"");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));	
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setUserGroupCode(rs.getInt("iusergroupcode"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setStageId(rs.getInt("iStageid"));
			dto.setHours(rs.getInt("iHours"));
			dto.setStartDate(rs.getTimestamp("dStartDate"));
			dto.setEndDate(rs.getTimestamp("dEndDate"));
			dto.setAdjustDate(rs.getTimestamp("dAdjustDate"));
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
public Vector<DTOWorkSpaceUserRightsMst> checkRights(String wsId, int nodeId,int userId,int userGroupCode)
{		
	Vector<DTOWorkSpaceUserRightsMst> workspaceNodeRightsVector = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{		  
		
		String where ="vWorkspaceId='"+wsId+"' and iNodeId="+nodeId+" and iUSerCode="+userId
				      +" and iUserGroupCode="+userGroupCode;
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*", "workspaceUserRightsMst",where,"");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
			dto.setUserCode(rs.getInt("iUserCode"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setCanReadFlag(rs.getString("cCanReadFlag").charAt(0));
			dto.setCanAddFlag(rs.getString("cCanAddFlag").charAt(0));
			dto.setCanEditFlag(rs.getString("cCanEditFlag").charAt(0));
			dto.setCanDeleteFlag(rs.getString("cCanDeleteFlag").charAt(0));
			dto.setAdvancedRights(rs.getString("vAdvancedRights"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			workspaceNodeRightsVector.addElement(dto);
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return workspaceNodeRightsVector;
}
public void insertintoWSUserRightsMst(DTOWorkSpaceUserRightsMst dto)
{
	Connection con = null;
	CallableStatement cs = null;
	try
	{
		con=ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_WorkspaceUserRightsMstForDocSign(?,?,?,?,?,?)}");
		
		cs.setString(1, dto.getWorkSpaceId());
		cs.setInt(2, dto.getUserGroupCode());
		cs.setInt(3, dto.getUserCode());
		cs.setInt(4, dto.getNodeId());
		cs.setString(5,dto.getRemark());
		cs.setInt(6, dto.getModifyBy());
		cs.execute();
		
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}
public Vector<DTOTimelineWorkspaceUserRightsMst> getTimelineDataForSectionRemove(String vWorkspaceId, int nodeId) {
	Connection con;
	Vector<DTOTimelineWorkspaceUserRightsMst> data= new Vector<DTOTimelineWorkspaceUserRightsMst>();
	try {
		con = ConnectionManager.ds.getConnection();

		//String Fields="vWorkspaceId,iusercode,iusergroupcode,iNodeId,istageid,iHours,dStartDate,dEndDate,dAdjustDate";
		
		String From="workspaceNodeDetail inner join view_timelineworkspaceuserrightsmstdetail on " 
					+"workspaceNodeDetail.vWorkspaceId = view_timelineworkspaceuserrightsmstdetail.vWorkspaceId and "
					+"workspaceNodeDetail.iNodeId = view_timelineworkspaceuserrightsmstdetail.iNodeId ";
		
		String Where="workspaceNodeDetail.vWorkspaceId='"+vWorkspaceId+"' and workspaceNodeDetail.cStatusIndi<>'D' and " 
					 +"workspaceNodeDetail.crequiredflag in ('n','f') and workspaceNodeDetail.inodeid not in "
					 + "(select distinct iparentnodeid from workspacenodedetail where  crequiredflag in ('n','f')  and  vWorkspaceId ='"+vWorkspaceId+"')"
					 +"and workspaceNodeDetail.inodeid in(select iNodeId from Proc_getAllChildNodes('" + vWorkspaceId + "'," + nodeId + "))";
		
		ResultSet rs=dataTable.getDataSet(con,"*",From , Where,"workspaceNodeDetail.iparentnodeId,workspaceNodeDetail.inodeno,"
								+"view_timelineworkspaceuserrightsmstdetail.istageid" ); 
		while (rs.next()) {
			DTOTimelineWorkspaceUserRightsMst dto=new DTOTimelineWorkspaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
			dto.setUserCode(rs.getInt("iUserCode"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setStartDate(rs.getTimestamp("dStartDate"));
			dto.setEndDate(rs.getTimestamp("dEndDate"));
			dto.setAdjustDate(rs.getTimestamp("dAdjustDate"));
			data.addElement(dto);
			dto=null;
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return data;
}
public Vector<DTOTimelineWorkspaceUserRightsMst> getTimelineDataForRemove(String vWorkspaceId, int nodeId) {
	Connection con;
	Vector<DTOTimelineWorkspaceUserRightsMst> data= new Vector<DTOTimelineWorkspaceUserRightsMst>();
	try {
		con = ConnectionManager.ds.getConnection();

		ResultSet rs = dataTable.getDataSet(con, "*",
				"timelineworkspaceuserrightsmst", "vWorkspaceId = '"
						+ vWorkspaceId + "' and iNodeId = " + nodeId + " and cStatusindi <> 'D'", "iStageId asc,ID asc");
		while (rs.next()) {
			DTOTimelineWorkspaceUserRightsMst dto=new DTOTimelineWorkspaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
			dto.setUserCode(rs.getInt("iUserCode"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setStartDate(rs.getTimestamp("dStartDate"));
			dto.setEndDate(rs.getTimestamp("dEndDate"));
			dto.setAdjustDate(rs.getTimestamp("dAdjustDate"));
			data.addElement(dto);
			dto=null;
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return data;
}

public void insertNodeintoWorkSpaceUserRightsForEDocSign(String wsId,int nodeId)
{
	Connection con = null;
	CallableStatement cs = null;
	try
    {
    	 con = ConnectionManager.ds.getConnection();
		 cs=con.prepareCall("{call Proc_updateWorkSpaceUserRights(?,?,?,?,?,?,?,?)}");
		 cs.setString(1, wsId);
		 cs.setInt(2, nodeId);
		 cs.setInt(3,1); //not in use 
		 cs.setInt(4, 1); //not in use 
		 cs.setString(5, "A");//not in use
		 cs.setString(6, "A");//not in use
		 cs.setString(7, "A");//not in use
		 cs.setInt(8,10);
		 cs.execute();	
		
    }catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}
public boolean RemoveRightsFromWorkspaceUserRightsMst(String wsId,int nodeId) {
	try {
		
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs = con
				.prepareCall("{call Proc_DeleteRightsFromWSUserRightsMst(?,?)}");
		cs.setString(1, wsId);
		cs.setInt(2,nodeId);
		cs.execute();
		cs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return true;
}
public ArrayList<DTOTimelineWorkspaceUserRightsMst>  getProjectStageDetail()
{
	//DataTable dataTable = new DataTable();
	 ArrayList<DTOTimelineWorkspaceUserRightsMst> data = new  ArrayList<DTOTimelineWorkspaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	try
	{
		
		con = ConnectionManager.ds.getConnection();
		
		String select ="wsmst.vWorkspaceId,wsmst.vWorkSpaceDesc, wsndattrdtl.vAttrName, wsndattrdtl.vAttrValue";
		String from ="workspacemst as wsmst inner join workspacenodeattrdetail as wsndattrdtl on wsmst.vWorkSpaceId = wsndattrdtl.vWorkspaceId";
		String where=" wsndattrdtl.vAttrName='Project Start Date' and wsndattrdtl.vAttrValue<>''";
	
		rs = dataTable.getDataSet(con, select, from,where, "vWorkspaceId");
		while(rs.next())
		{
			DTOTimelineWorkspaceUserRightsMst dto = new DTOTimelineWorkspaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setWorkspaceDesc(rs.getString("vWorkspaceDesc"));
			
			Date date = new Date();  
            Timestamp currentDate=new Timestamp(date.getTime());  //2021-04-27 10:34:52.323
            
			String from1 =" workspaceNodeDetail inner join timelineworkspaceuserrightsmst on "
						+ "workspaceNodeDetail.vworkspaceid = timelineworkspaceuserrightsmst.vworkspaceid "
						+ "and workspaceNodeDetail.inodeid=timelineworkspaceuserrightsmst.inodeid inner join view_workspaceTimelineUserRights on "
						+" workspaceNodeDetail.vworkspaceid=view_workspaceTimelineUserRights.vworkspaceid and workspaceNodeDetail.inodeid=view_workspaceTimelineUserRights.inodeid";
			
			String were ="timelineworkspaceuserrightsmst.ihours >0 and timelineworkspaceuserrightsmst.cStatusIndi<>'D' and workspaceNodeDetail.vWorkspaceId ='"+dto.getWorkSpaceId()+"'"
						+ " and workspaceNodeDetail.cStatusIndi<>'D' and workspaceNodeDetail.crequiredflag in ('n','f') and workspaceNodeDetail.inodeid not in "
						+ "( select distinct iparentnodeid from workspacenodedetail	where crequiredflag in ('n','f') and vWorkspaceId='"+dto.getWorkSpaceId()+"')"
						+ "  and workspaceNodeDetail.cNodetypeIndi<>'K' and view_workspaceTimelineUserRights.iCompletedStageId<=10 and view_workspaceTimelineUserRights.vFlag<>'SR'"
						+ " and view_workspaceTimelineUserRights.dAdjustDate<'"+currentDate+"' and view_workspaceTimelineUserRights.dmodifyon>'1900-01-01 00:00:00.000'";
			
			rs1 = dataTable.getDataSet(con, "top 1 *", from1,were, "workspaceNodeDetail.vWorkspaceId");
			
			while(rs1.next())
			{
			  DTOTimelineWorkspaceUserRightsMst getData = new DTOTimelineWorkspaceUserRightsMst();
			  getData.setWorkSpaceId(rs1.getString("vWorkspaceId"));
			  getData.setWorkspaceDesc(dto.getWorkspaceDesc());
			  getData.setNodeId(rs1.getInt("iNodeId"));
			  getData.setUserCode(rs1.getInt("iUserCode"));
			  getData.setUserName(rs1.getString("vUserName"));
			  getData.setFileName(rs1.getString("vFolderName"));			 
			  
			  System.out.println("WsId:"+getData.getWorkSpaceId());
			  System.out.println("WsDesc:"+getData.getWorkspaceDesc());
			  System.out.println("NodeId:"+getData.getNodeId());
			  System.out.println("userCode:"+getData.getUserCode());
			  System.out.println("UserName:"+getData.getUserName());
			  System.out.println("Document Name:"+getData.getFileName());
			  
			  data.add(getData);
			  getData=null;				
			}
			dto=null;
			
		}
		rs.close();
		rs1.close();
		con.close();
		
	}catch(Exception e){
		e.printStackTrace();
	}

	return data;
}
public void insertNodeintoWorkSpaceUserRightseCSV(String wsId,int nodeId,int selectedNodeId)
{
	Connection con = null;
	CallableStatement cs = null;
	try
    {
    	 con = ConnectionManager.ds.getConnection();
		 cs=con.prepareCall("{call Proc_updateWorkSpaceUserRightseCSV(?,?,?,?,?,?,?,?,?)}");
		 cs.setString(1, wsId);
		 cs.setInt(2, nodeId);
		 cs.setInt(3, selectedNodeId);
		 cs.setInt(4,1); //not in use 
		 cs.setInt(5, 1); //not in use 
		 cs.setString(6, "A");//not in use
		 cs.setString(7, "A");//not in use
		 cs.setString(8, "A");//not in use
		 cs.setInt(9,10);
		 cs.execute();	
		
    }catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}
public Vector<DTOWorkSpaceUserRightsMst> getAttachedUserList(String wsId, int nodeId, int userCode)
{
	//DataTable dataTable = new DataTable();
	Vector<DTOWorkSpaceUserRightsMst> data = new  Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		
		String select ="distinct wsuserrightsmst.vWorkspaceId, wsuserrightsmst.iNodeId,wsuserrightsmst.iUserCode, usermst.vUserName,usermst.vloginname";
		
		String from ="workspaceuserrightsmst as wsuserrightsmst inner join usermst on wsuserrightsmst.iUserCode = usermst.iUserCode";
		
		String where = " wsuserrightsmst.vworkspaceid='"+wsId+"' and wsuserrightsmst.inodeid="+nodeId+" and wsuserrightsmst.iuserCode<>"+userCode
						+" And usermst.vUserTypeCode<>'0001' and usermst.vUserTypeCode<>'0002'" ; 
		
		rs = dataTable.getDataSet(con, select,from, where,"");
		while (rs.next()) {
			DTOWorkSpaceUserRightsMst dto=new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setUserCode(rs.getInt("iUserCode"));
			dto.setUserName(rs.getString("vUserName"));
			dto.setLoginName(rs.getString("vLoginName"));
			data.addElement(dto);
			dto=null;
		}
	}
	catch (SQLException e) {
		e.printStackTrace();
	}
	return data;
}
public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineAdjustHoursUpdate(String wsId,int nodeId,int parentNodeId,int userCode,int stageId)
{
	Vector<DTOWorkSpaceUserRightsMst> nodeInfo = new Vector<>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		
		con=ConnectionManager.ds.getConnection();
		/*String Fields="vWorkspaceId,iusercode,iusergroupcode,iNodeId,istageid,iHours,dStartDate,dEndDate,dAdjustDate";
		
		String From="view_timelineworkspaceuserrightsmstdetail";
		
		String Where="vWorkspaceId='"+wsId+"' and iParentnodeId >="+parentNodeId+" and cStatusIndi<>'D' "
				+ " and inodeid not in (select inodeId from workspacenodedetail where  vWorkspaceId ='"+wsId+"' and iparentnodeid="+parentNodeId+"  "
				+" and iNodeNo <=(select iNodeNo from workspacenodedetail"
									+" where vworkspaceid='"+wsId+"' and iparentnodeid="+parentNodeId+" and iNodeId="+nodeId+"))"
				+ " or iNodeId="+nodeId+" and vworkspaceid='"+wsId
				+"' and id not in (Select id from view_timelineworkspaceuserrightsmstdetail where  vWorkspaceId ='"+wsId+"' and inodeid ="+nodeId+" and id <= ( Select id from view_timelineworkspaceuserrightsmstdetail where  vWorkspaceId ='"+wsId+"' and inodeid ="+nodeId+" and istageid="+stageId+" and iusercode="+userCode+"))"
				+ "AND dModifyOn =Convert(datetime, '1900-01-01' )";*/
		
		String Fields="tdtl.vWorkspaceId,tdtl.iusercode,tdtl.iusergroupcode,tdtl.iNodeId,tdtl.istageid,tdtl.iHours,tdtl.dStartDate,tdtl.dEndDate,tdtl.dAdjustDate";
		
		String From="Proc_getProjectTrackingeCSVChildNodes('"+wsId+"',"+nodeId+") as ptdtl inner join"+ 
			" view_timelineworkspaceuserrightsmstdetail as tdtl on ptdtl.vWorkspaceId=tdtl.vWorkspaceId and ptdtl.iNodeId=tdtl.iNodeId";
	
		String Where="tdtl.vWorkspaceId='"+wsId+"' and tdtl.cStatusIndi<>'D' and  tdtl.id not in (Select id from view_timelineworkspaceuserrightsmstdetail where  vWorkspaceId ='"+wsId+"' and inodeid="+nodeId+ 
				 " and id <= ( Select id from view_timelineworkspaceuserrightsmstdetail where  vWorkspaceId ='"+wsId+"' and inodeid ="+nodeId+" and istageid="+stageId+" and iusercode="+userCode+"))"+
				 "AND tdtl.dModifyOn =Convert(datetime, '1900-01-01' )"; 
		
		rs=dataTable.getDataSet(con,Fields,From , Where,"ptdtl.nRID,tdtl.istageid,tdtl.id"); 
		while (rs.next())  
		{
			DTOWorkSpaceUserRightsMst dtowurmst = new DTOWorkSpaceUserRightsMst();
			dtowurmst.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dtowurmst.setUserCode(rs.getInt("iusercode"));
			dtowurmst.setUserGroupCode(rs.getInt("iusergroupcode"));
			dtowurmst.setNodeId(rs.getInt("iNodeId"));
			dtowurmst.setStageId(rs.getInt("istageid"));
			dtowurmst.setHours(rs.getInt("iHours"));
			dtowurmst.setStartDate(rs.getTimestamp("dStartDate"));
			dtowurmst.setEndDate(rs.getTimestamp("dEndDate"));
			dtowurmst.setAdjustDate(rs.getTimestamp("dAdjustDate"));
			nodeInfo.add(dtowurmst);
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}
public Vector<DTOWorkSpaceUserRightsMst> UserOnNodeTimelineTracking(String wsId,int nodeId)
{
	Vector<DTOWorkSpaceUserRightsMst> nodeInfo = new Vector<>();
	Connection con = null;
	ResultSet rs = null;
	try  
	{
		
		con=ConnectionManager.ds.getConnection();
		/*String Fields="vWorkspaceId,iusercode,iusergroupcode,iNodeId,istageid,iHours,dStartDate,dEndDate,dAdjustDate";
		
		String From="view_timelineworkspaceuserrightsmstdetail";
		
		String Where="vWorkspaceId='"+wsId+"' and iParentnodeId >="+parentNodeId+" and cStatusIndi<>'D' "
				+ " and inodeid not in (select inodeId from workspacenodedetail where  vWorkspaceId ='"+wsId+"' and iparentnodeid="+parentNodeId+"  "
				+" and iNodeNo <=(select iNodeNo from workspacenodedetail"
									+" where vworkspaceid='"+wsId+"' and iparentnodeid="+parentNodeId+" and iNodeId="+nodeId+"))"
				+ " or iNodeId="+nodeId+" and vworkspaceid='"+wsId
				+"' and id not in (Select id from view_timelineworkspaceuserrightsmstdetail where  vWorkspaceId ='"+wsId+"' and inodeid ="+nodeId+" and id <= ( Select id from view_timelineworkspaceuserrightsmstdetail where  vWorkspaceId ='"+wsId+"' and inodeid ="+nodeId+" and istageid="+stageId+" and iusercode="+userCode+"))"
				+ "AND dModifyOn =Convert(datetime, '1900-01-01' )";*/
		
		String Fields="tdtl.vWorkspaceId,tdtl.iusercode,tdtl.iusergroupcode,tdtl.iNodeId,tdtl.istageid,tdtl.iHours,tdtl.dStartDate,tdtl.dEndDate,tdtl.dAdjustDate";
		
		String From="Proc_getProjectTrackingeCSVParentNodes('"+wsId+"',"+nodeId+") as ptdtl inner join"+ 
			" view_timelineworkspaceuserrightsmstdetail as tdtl on ptdtl.vWorkspaceId=tdtl.vWorkspaceId and ptdtl.iNodeId=tdtl.iNodeId";
	
		String Where="tdtl.vWorkspaceId='"+wsId+"' and tdtl.cStatusIndi<>'D'"; 
		
		rs=dataTable.getDataSet(con,Fields,From , Where,"ptdtl.nRID,tdtl.istageid,tdtl.id"); 
		while (rs.next())  
		{
			DTOWorkSpaceUserRightsMst dtowurmst = new DTOWorkSpaceUserRightsMst();
			dtowurmst.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dtowurmst.setUserCode(rs.getInt("iusercode"));
			dtowurmst.setUserGroupCode(rs.getInt("iusergroupcode"));
			dtowurmst.setNodeId(rs.getInt("iNodeId"));
			dtowurmst.setStageId(rs.getInt("istageid"));
			dtowurmst.setHours(rs.getInt("iHours"));
			dtowurmst.setStartDate(rs.getTimestamp("dStartDate"));
			dtowurmst.setEndDate(rs.getTimestamp("dEndDate"));
			dtowurmst.setAdjustDate(rs.getTimestamp("dAdjustDate"));
			nodeInfo.add(dtowurmst);
		}
	}
	catch(Exception e)  {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	
	return nodeInfo;		
			
}
public DTOWorkSpaceUserRightsMst getTimeLineRightsMstdtl(String wsId,int nodeId,int userCode,int userGrpCode, int stageId){
	DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		String where = "vWorkspaceId='"+ wsId + "' AND iNodeId="+ nodeId+ " AND iUserCode="+userCode
						+ " AND iUserGroupCode="+userGrpCode+" AND iStageId="+stageId+" and cStatusIndi<>'D'";
		rs = dataTable.getDataSet(con,"*","timelineworkspaceuserrightsmst",where,"");
		while(rs.next())
		{
			
			dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));	
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setUserGroupCode(rs.getInt("iusergroupcode"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setStageId(rs.getInt("iStageid"));
			dto.setHours(rs.getInt("iHours"));
			dto.setStartDate(rs.getTimestamp("dStartDate"));
			dto.setEndDate(rs.getTimestamp("dEndDate"));
			dto.setAdjustDate(rs.getTimestamp("dAdjustDate"));
			
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}

	return dto;
}
public void updateTimelineHoursAdjustDate(DTOWorkSpaceUserRightsMst dto)  
{
	Connection con = null;
	CallableStatement cs = null;
	try 
	{
		 con = ConnectionManager.ds.getConnection();
		 cs=con.prepareCall("{call Proc_updateTimelineHoursAdjustDate(?,?,?,?,?,?,?)}");
		 cs.setString(1, dto.getWorkSpaceId());
		 cs.setInt(2, dto.getNodeId());
		 cs.setInt(3,dto.getUserCode());
		 cs.setInt(4, dto.getUserGroupCode());
		 cs.setInt(5, dto.getStageId());
		 cs.setInt(6, dto.getHours());
		 cs.setTimestamp(7, dto.getAdjustDate());
		 
		 cs.execute();	
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}

}
public Vector<DTOTimelineWorkspaceUserRightsMst> getTimelineDataForRepeatSection(String vWorkspaceId, int nodeId) {
	Connection con;
	Vector<DTOTimelineWorkspaceUserRightsMst> data= new Vector<DTOTimelineWorkspaceUserRightsMst>();
	try {
		con = ConnectionManager.ds.getConnection();

		ResultSet rs = dataTable.getDataSet(con, "*",
				"timelineworkspaceuserrightsmst", "vWorkspaceId = '"
						+ vWorkspaceId + "' and iNodeId = " + nodeId + " and cStatusindi <> 'D'", "iStageId desc,ID desc");
		while (rs.next()) {
			DTOTimelineWorkspaceUserRightsMst dto=new DTOTimelineWorkspaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
			dto.setUserCode(rs.getInt("iUserCode"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setStartDate(rs.getTimestamp("dStartDate"));
			dto.setEndDate(rs.getTimestamp("dEndDate"));
			dto.setAdjustDate(rs.getTimestamp("dAdjustDate"));
			data.addElement(dto);
			dto=null;
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return data;
}
public Vector<DTOWorkSpaceUserRightsMst> getLastRightsRecordDtlForAdjustDate(String wsId,int nodeId,int stageId){
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		String where = "vWorkspaceId='"+ wsId + "' AND iNodeId="+ nodeId+ " AND iStageId<="+stageId+" and cStatusIndi<>'D'";
		rs = dataTable.getDataSet(con,"*","timelineworkspaceuserrightsmst",where,"iStageId,ID");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));	
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setUserGroupCode(rs.getInt("iusergroupcode"));
			dto.setStageId(rs.getInt("iStageid"));
			dto.setHours(rs.getInt("iHours"));
			dto.setStartDate(rs.getTimestamp("dStartDate"));
			dto.setEndDate(rs.getTimestamp("dEndDate"));
			dto.setAdjustDate(rs.getTimestamp("dAdjustDate"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
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
public DTOWorkSpaceUserRightsMst getTimeLineRightsDtl(String wsId,int nodeId,int userCode, int stageId){
	DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		String where = "vWorkspaceId='"+ wsId + "' AND iNodeId="+ nodeId+ " AND iUserCode="+userCode
						+ " AND iStageId="+stageId+" and cStatusIndi<>'D'";
		rs = dataTable.getDataSet(con,"*","timelineworkspaceuserrightsmst",where,"");
		while(rs.next())
		{
			
			dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));	
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setUserGroupCode(rs.getInt("iusergroupcode"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setStageId(rs.getInt("iStageid"));
			dto.setHours(rs.getInt("iHours"));
			dto.setStartDate(rs.getTimestamp("dStartDate"));
			dto.setEndDate(rs.getTimestamp("dEndDate"));
			dto.setAdjustDate(rs.getTimestamp("dAdjustDate"));
			
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}

	return dto;
}
public Vector<DTOWorkSpaceUserRightsMst> checkUserRights(String wsId, int nodeId,String Users)
{		
	Vector<DTOWorkSpaceUserRightsMst> workspaceNodeRightsVector = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{		  
		
		String where ="vWorkspaceId='"+wsId+"' and iNodeId="+nodeId+" and iUserCode in("+Users+") and cStatusIndi<>'D'";
		con = ConnectionManager.ds.getConnection();
		rs=dataTable.getDataSet(con,"*", "workspaceUserRightsMst",where,"");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
			dto.setUserCode(rs.getInt("iUserCode"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setCanReadFlag(rs.getString("cCanReadFlag").charAt(0));
			dto.setCanAddFlag(rs.getString("cCanAddFlag").charAt(0));
			dto.setCanEditFlag(rs.getString("cCanEditFlag").charAt(0));
			dto.setCanDeleteFlag(rs.getString("cCanDeleteFlag").charAt(0));
			dto.setAdvancedRights(rs.getString("vAdvancedRights"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			workspaceNodeRightsVector.addElement(dto);
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
	return workspaceNodeRightsVector;
}
public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForsrcDocRmd(String wsId, int nodeId,int userCode)
{
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*",
				"view_userRightsDetail","vWorkspaceId = '"+wsId+"' and vUserTypeCode<>'0001' and vUserTypeCode<>'0002' and vUserTypeCode<>'0005' and vUserTypeCode<>'0006' and iuserCode<>"+userCode+" and iNodeId ="+nodeId, "istageId,id");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserName(rs.getString("vUserName"));
			dto.setCanEditFlag(rs.getString("cCanEditFlag").charAt(0));
			dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
			dto.setNodeId(rs.getInt("inodeid"));
			dto.setUserTypeCode(rs.getString("vUserTypeCode"));
			dto.setHours(rs.getInt("iHours"));
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

public boolean RemoveFolderSpecificMultipleUserRightsForESignature(String wsId,int users,int[] stageId,String nodeIdsCSV) {
	try {
		String userCodes= String.valueOf(users);
		String stageIds="";
		stageIds= String.valueOf(stageId[0]);
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs = con
				.prepareCall("{call Proc_DeleteFolderSpecificMultipleUserRightsForESignature(?,?,?,?)}");
		cs.setString(1, wsId);
		cs.setString(2,userCodes);
		cs.setString(3,stageIds);
		cs.setString(4,nodeIdsCSV);
		cs.execute();
		cs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return true;
}


public void insertFolderSpecificMultipleUserRightsWithRoleCodeForESignature(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList)
{
	Connection con = null;
	CallableStatement cs = null;
	try
	{
		/*Sort Array List by usercode*/
		Collections.sort(userRightsList, new Comparator<DTOWorkSpaceUserRightsMst>() {
			public int compare(DTOWorkSpaceUserRightsMst arg0, DTOWorkSpaceUserRightsMst arg1) {
				if(arg0.getUserCode() == arg1.getUserCode()){ 
					return arg0.getWorkSpaceId().compareTo(arg1.getWorkSpaceId());
				}
				else if(arg0.getUserCode() < arg1.getUserCode())
					return -1;
				else //if(arg0.getUserCode() > arg1.getUserCode())
					return 1;
			}
		});
		
		con=ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_FolderSpecificMultipleUserRightsWithRoleCodeForESignature(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		int prevUserCode = 0;
		String prevWorkspaceId = "";
		
		for(int i=0;i<userRightsList.size();i++)
		{
			DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst=userRightsList.get(i);
			cs.setString(1, dtoWorkSpaceUserRightsMst.getWorkSpaceId());
			cs.setInt(2, dtoWorkSpaceUserRightsMst.getUserGroupCode());
			cs.setInt(3, dtoWorkSpaceUserRightsMst.getUserCode());
			cs.setInt(4, dtoWorkSpaceUserRightsMst.getNodeId());
			cs.setString(5,Character.toString(dtoWorkSpaceUserRightsMst.getCanReadFlag()));
			cs.setString(6, Character.toString(dtoWorkSpaceUserRightsMst.getCanAddFlag()));
			cs.setString(7, Character.toString(dtoWorkSpaceUserRightsMst.getCanEditFlag()));
			cs.setString(8, Character.toString(dtoWorkSpaceUserRightsMst.getCanDeleteFlag()));
			cs.setString(9, dtoWorkSpaceUserRightsMst.getAdvancedRights());
			cs.setString(10, dtoWorkSpaceUserRightsMst.getRemark());
			cs.setInt(11, dtoWorkSpaceUserRightsMst.getModifyBy());
			cs.setInt(12, dtoWorkSpaceUserRightsMst.getStageId());
			cs.setString(13, dtoWorkSpaceUserRightsMst.getRoleCode());
			cs.setInt(14, dtoWorkSpaceUserRightsMst.getSeqNo());
			cs.setInt(15,dtoWorkSpaceUserRightsMst.getMode());//1 - Insert , 2-Update,3-Delete Wsuserrightsmst for one stage.
			cs.execute();
			
			prevUserCode = dtoWorkSpaceUserRightsMst.getUserCode();
			prevWorkspaceId = dtoWorkSpaceUserRightsMst.getWorkSpaceId();
		}
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}



public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForESignautre(String wsId, int nodeId)
{
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*",
				"view_userRightsDetailForESignature","vWorkspaceId = '"+wsId+"' and vUserTypeCode<>'0001' and vUserTypeCode<>'0002' and "
						+ "vUserTypeCode<>'0005' and vUserTypeCode<>'0006' and iNodeId ="+nodeId, "iseqno"); 
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserName(rs.getString("vUserName"));
			dto.setCanEditFlag(rs.getString("cCanEditFlag").charAt(0));
			dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
			dto.setNodeId(rs.getInt("inodeid"));
			dto.setUserTypeCode(rs.getString("vUserTypeCode"));
			dto.setHours(rs.getInt("iHours"));
			dto.setRoleName(rs.getString("vRoleName"));
			dto.setRoleCode(rs.getString("vRoleCode"));
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


public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForESignature(String wsId, int nodeId,int userCode)
{
	//DataTable dataTable = new DataTable();
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
	
		String where="iUserCode = "+userCode;
		 System.out.println(where);
	
		rs = dataTable.getDataSet(con, "*", "WorkspaceUserRightsMstForESignature","vWorkspaceId = '"+wsId+"' and iNodeId ="+nodeId+" and "+where, "istageid");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setUserName(rs.getString("vworkspaceid"));
			dto.setNodeId(rs.getInt("inodeid"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setRoleCode(rs.getString("vRoleCode"));
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

public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForEsignatureByUsercode(String wsId, int nodeId,int usercode)
{
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*",
				"view_userRightsDetailForESignature","vWorkspaceId = '"+wsId+"' and vUserTypeCode<>'0001' and vUserTypeCode<>'0002' and "
						+ "vUserTypeCode<>'0005' and vUserTypeCode<>'0006' and iNodeId ="+nodeId+" and iUsercode="+usercode, "istageId,id");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserName(rs.getString("vUserName"));
			dto.setCanEditFlag(rs.getString("cCanEditFlag").charAt(0));
			dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
			dto.setNodeId(rs.getInt("inodeid"));
			dto.setUserTypeCode(rs.getString("vUserTypeCode"));
			dto.setHours(rs.getInt("iHours"));
			dto.setRoleName(rs.getString("vRoleName"));
			dto.setRoleCode(rs.getString("vRoleCode"));
			dto.setSeqNo(rs.getInt("iSeqNo"));
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


public Vector<DTOWorkSpaceUserRightsMst> getUserFromSeqNo(String wsId, int nodeId,int seqNo)
{
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*",
				"view_userRightsDetailForESignature","vWorkspaceId = '"+wsId+"' and vUserTypeCode<>'0001' and vUserTypeCode<>'0002' and "
						+ "vUserTypeCode<>'0005' and vUserTypeCode<>'0006' and iNodeId ="+nodeId+" and iseqNo="+seqNo, "");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserName(rs.getString("vUserName"));
			dto.setCanEditFlag(rs.getString("cCanEditFlag").charAt(0));
			dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
			dto.setNodeId(rs.getInt("inodeid"));
			dto.setUserTypeCode(rs.getString("vUserTypeCode"));
			dto.setHours(rs.getInt("iHours"));
			dto.setRoleName(rs.getString("vRoleName"));
			dto.setRoleCode(rs.getString("vRoleCode"));
			dto.setSeqNo(rs.getInt("iSeqNo"));
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

public ArrayList<DTOStageWiseMailReport> getNodeStageDetailFoESignature(String wsId, int nodeId,int stageId)
{
	ArrayList<DTOStageWiseMailReport> data = new ArrayList<DTOStageWiseMailReport>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*", "view_StageWiseMailReportHistoryForESignature","vWorkspaceId = '"+wsId+"' and iNodeId ="+nodeId,"");
		while(rs.next())
		{
			DTOStageWiseMailReport dto = new DTOStageWiseMailReport();
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setWorkspaceDesc(rs.getString("vWorkspaceDesc"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setStageId(rs.getInt("iStageId"));
			//dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setUserName(rs.getString("vUserName"));
			dto.setLoginName(rs.getString("vLoginName"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setNextStageId(rs.getInt("iSeqNo"));
			data.add(dto);
		}
	}catch(Exception e){
		e.printStackTrace();
	}

	return data;
}


public void insertModuleWiseUserHistoryForESignature(DTOWorkSpaceUserRightsMst userRightsForModuleHistory)
{
	Connection con = null;
	CallableStatement cs = null;
	try
	{
		con=ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_moduleWiseUserMstHistoryForESignature(?,?,?,?,?,?,?,?)}");
		
			DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst=userRightsForModuleHistory;
			cs.setString(1, dtoWorkSpaceUserRightsMst.getWorkSpaceId());
			cs.setInt(2, dtoWorkSpaceUserRightsMst.getUserGroupCode());
			cs.setInt(3, dtoWorkSpaceUserRightsMst.getUserCode());
			cs.setInt(4, dtoWorkSpaceUserRightsMst.getNodeId());
			cs.setString(5,dtoWorkSpaceUserRightsMst.getStages());
			cs.setString(6, dtoWorkSpaceUserRightsMst.getRemark());
			cs.setInt(7, dtoWorkSpaceUserRightsMst.getModifyBy());
			cs.setInt(8, dtoWorkSpaceUserRightsMst.getMode());
			
			cs.execute();
			
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public void insertFolderSpecificMultipleUserRightsForESignature(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList)
{
	Connection con = null;
	CallableStatement cs = null;
	try
	{
		/*Sort Array List by usercode*/
		Collections.sort(userRightsList, new Comparator<DTOWorkSpaceUserRightsMst>() {
			public int compare(DTOWorkSpaceUserRightsMst arg0, DTOWorkSpaceUserRightsMst arg1) {
				if(arg0.getUserCode() == arg1.getUserCode()){ 
					return arg0.getWorkSpaceId().compareTo(arg1.getWorkSpaceId());
				}
				else if(arg0.getUserCode() < arg1.getUserCode())
					return -1;
				else //if(arg0.getUserCode() > arg1.getUserCode())
					return 1;
			}
		});
		
		con=ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_FolderSpecificMultipleUserRightsForESignature(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		int prevUserCode = 0;
		String prevWorkspaceId = "";
		
		for(int i=0;i<userRightsList.size();i++)
		{
			DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst=userRightsList.get(i);
			cs.setString(1, dtoWorkSpaceUserRightsMst.getWorkSpaceId());
			cs.setInt(2, dtoWorkSpaceUserRightsMst.getUserGroupCode());
			cs.setInt(3, dtoWorkSpaceUserRightsMst.getUserCode());
			cs.setInt(4, dtoWorkSpaceUserRightsMst.getNodeId());
			cs.setString(5,Character.toString(dtoWorkSpaceUserRightsMst.getCanReadFlag()));
			cs.setString(6, Character.toString(dtoWorkSpaceUserRightsMst.getCanAddFlag()));
			cs.setString(7, Character.toString(dtoWorkSpaceUserRightsMst.getCanEditFlag()));
			cs.setString(8, Character.toString(dtoWorkSpaceUserRightsMst.getCanDeleteFlag()));
			cs.setString(9, dtoWorkSpaceUserRightsMst.getAdvancedRights());
			cs.setString(10, dtoWorkSpaceUserRightsMst.getRemark());
			cs.setInt(11, dtoWorkSpaceUserRightsMst.getModifyBy());
			cs.setInt(12, dtoWorkSpaceUserRightsMst.getStageId());
			cs.setInt(13, dtoWorkSpaceUserRightsMst.getSeqNo());
			cs.setString(14, dtoWorkSpaceUserRightsMst.getRoleCode());
			//Delete all rights first of the user on the project.
			/*if(prevUserCode != dtoWorkSpaceUserRightsMst.getUserCode()
					|| !prevWorkspaceId.equals(dtoWorkSpaceUserRightsMst.getWorkSpaceId())){
				cs.setInt(13,3);
				cs.execute();
			}*/
			
			cs.setInt(15,1);//Insert userrights for one stage.
			cs.execute();
			
			prevUserCode = dtoWorkSpaceUserRightsMst.getUserCode();
			prevWorkspaceId = dtoWorkSpaceUserRightsMst.getWorkSpaceId();
		}
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}


public ArrayList<DTOStageWiseMailReport> getNodeStageDetailForESignatureNextUser(String wsId, int nodeId,int stageId)
{
	ArrayList<DTOStageWiseMailReport> data = new ArrayList<DTOStageWiseMailReport>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*", "view_StageWiseMailReportHistoryForESignature","vWorkspaceId = '"+wsId+"' "
				+ "and iNodeId ="+nodeId+" and iuserCode="+stageId,"");
		while(rs.next())
		{
			DTOStageWiseMailReport dto = new DTOStageWiseMailReport();
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setWorkspaceDesc(rs.getString("vWorkspaceDesc"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setStageId(rs.getInt("iStageId"));
			//dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setUserName(rs.getString("vUserName"));
			dto.setLoginName(rs.getString("vLoginName"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setNextStageId(rs.getInt("iSeqNo"));
			data.add(dto);
		}
	}catch(Exception e){
		e.printStackTrace();
	}

	return data;
}
public int checkCreateRights(String wsId, int nodeId) {
	int userCode = -1;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		String where = " vWorkspaceId = '" + wsId + "' And iNodeId =" + nodeId + " AND iStageId=10 AND vUserTypeCode='0003'";
		ResultSet rs = dataTable.getDataSet(con, "iUserCode",	"view_WorkspaceUserRightsDetail", where, "");
		if (rs.next()) {
			userCode = rs.getInt("iUserCode");
		}
		rs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userCode;
}

public Vector<DTOWorkSpaceUserRightsMst> getProjectForUserDetail(int userCode,int UserGroupCode,String wsId)
{
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "vworkspaceid,vWorkSpaceDesc,vBaseWorkFolder,filePath,fName,vFolderName,vNodeDisplayName,inodeid,itranno,iuserCode,vusername,"
				+ "vstagedesc,iCompletedStageId,vFileType,dModifyOn",
				"view_workspaceDetailUserWise","vworkspaceid = '"+wsId+"' AND cStatusIndi<>'D' and  crequiredflag in ('n','f')"
				+ "and wsNodeId not in ( select distinct iparentnodeid from workspacenodedetail where  crequiredflag in ('n','f') "
				+ "and  vWorkspaceId='"+wsId+"')"+"And iUserCode="+userCode+" ORDER By iStageId,iCompletedStageId DESC ","");
				//+ "not in(select * from workspacenodevoid where vWorkspaceId='"+wsId+"'","");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vworkspaceid"));
			dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
			dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
			dto.setFilePath(rs.getString("filePath"));
			dto.setFolderName(rs.getString("vFolderName"));
			dto.setfName(rs.getString("fName"));
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
			dto.setNodeId(rs.getInt("inodeid"));
			dto.setTranNo(rs.getInt("itranno"));
			dto.setUserCode(rs.getInt("iuserCode"));
			dto.setUserName(rs.getString("vusername"));
			dto.setStageDesc(rs.getString("vstagedesc"));
			dto.setCompletedStageId(rs.getInt("iCompletedStageId"));
			dto.setFileType(rs.getString("vFileType"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
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

public Vector<DTOWorkSpaceUserRightsMst> UserDataForBulkAllocation(String wsId)
{
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*",
				"view_workspaceUserDetail","WorkspaceId = '"+wsId+"' "
						+ "AND StatusIndi<>'D' AND StatusIndi<>'L' AND StatusIndi<>'V' AND StatusIndi<>'W' AND StatusIndi<>'A' AND UserTypeCode='0003'"
						, "");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("WorkspaceId"));
			dto.setUserName(rs.getString("UserName"));
			dto.setUserGroupCode(rs.getInt("UserGroupCode"));
			dto.setUserCode(rs.getInt("usercode"));
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

public Vector<DTOWorkSpaceUserRightsMst> getUserAllocationDetails(int userCode,String wsId)
{
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	int getRights;
	Vector<DTOStageMst> getStages;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "wnd.vWorkSpaceId,wnd.iNodeId,wnd.vNodeName,wnd.vFolderName,wsurm.iStageId,wsurm.iUserCode,twsurm.iHours",
				"Proc_getProjectTrackingeCSVChildNodesHours('"+wsId+"') AS wnd"
				+" LEFT JOIN WorkspaceUserrightsMst wsurm on wnd.vWorkspaceId=wsurm.vWorkspaceId AND wnd.iNodeId=wsurm.iNodeId "
				+"AND wsurm.iUserCode=('"+userCode+"')"
				+"LEFT JOIN timelineworkspaceuserrightsmst twsurm on wnd.vWorkspaceId=twsurm.vWorkspaceId AND "
				+ "wnd.iNodeId=twsurm.iNodeId AND twsurm.iUserCode=('"+userCode+"')","wnd.vWorkspaceId ='"+wsId+"' " ,"");
							
				//+ "not in(select * from workspacenodevoid where vWorkspaceId='"+wsId+"'","");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vworkspaceid"));
			dto.setNodeId(rs.getInt("inodeId"));
			getRights = checkCreateRights(dto.getWorkSpaceId(),dto.getNodeId());
			getStages = docMgmtImpl.getStageDetailCSV();
			if(getRights>0){
				for(int i = 0;i<getStages.size();i++){
					if(getStages.get(i).getStageId() == 10){
						getStages.remove(i);
						break;
					}
				 }
			}
			dto.setGetStageDetail(getStages);
			
			dto.setNodeName(rs.getString("vNodeName"));
			dto.setFolderName(rs.getString("vFolderName"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setUserCode(rs.getInt("iuserCode"));
			dto.setHours(rs.getInt("iHours"));
			
			/*if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}*/
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

public boolean RemoveFolderSpecificMultipleUserRightsForBulkDeletion(String wsId,int user,int stageId,int nodeIdsCSV) {
	try {
		
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs = con
				.prepareCall("{call Proc_DeleteFolderSpecificMultipleUserRights(?,?,?,?)}");
		cs.setString(1, wsId);
		cs.setInt(2,user);
		cs.setInt(3,stageId);
		cs.setInt(4,nodeIdsCSV);
		cs.execute();
		cs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return true;
}

public boolean RemoveUserRightsfromTimelineForBulkDeletion(String wsId,int user,int stageId,int nodeIdsCSV) {
	try {
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs = con
				.prepareCall("{call Proc_DeleteFolderSpecificMultipleUserRightsForTimeLine(?,?,?,?)}");
		cs.setString(1, wsId);
		cs.setInt(2,user);
		cs.setInt(3,stageId);
		cs.setInt(4,nodeIdsCSV);
		cs.execute();
		cs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return true;
}

public void AttachUserRightsForTimeLineWithExludedDate(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList)
{
	Connection con = null;
	CallableStatement cs = null;
	try
	{
		/*Sort Array List by usercode*/
		Collections.sort(userRightsList, new Comparator<DTOWorkSpaceUserRightsMst>() {
			public int compare(DTOWorkSpaceUserRightsMst arg0, DTOWorkSpaceUserRightsMst arg1) {
				if(arg0.getUserCode() == arg1.getUserCode()){ 
					return arg0.getWorkSpaceId().compareTo(arg1.getWorkSpaceId());
				}
				else if(arg0.getUserCode() < arg1.getUserCode())
					return -1;
				else //if(arg0.getUserCode() > arg1.getUserCode())
					return 1;
			}
		});
		
		con=ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Insert_AttachUserRightsForTimeLineWithExcludedDate(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		int prevUserCode = 0;
		String prevWorkspaceId = "";
		
		for(int i=0;i<userRightsList.size();i++)
		{
			DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst=userRightsList.get(i);
			cs.setString(1, dtoWorkSpaceUserRightsMst.getWorkSpaceId());
			cs.setInt(2, dtoWorkSpaceUserRightsMst.getUserGroupCode());
			cs.setInt(3, dtoWorkSpaceUserRightsMst.getUserCode());
			cs.setInt(4, dtoWorkSpaceUserRightsMst.getNodeId());
			cs.setInt(5, dtoWorkSpaceUserRightsMst.getExistUserCode());
			cs.setString(6,Character.toString(dtoWorkSpaceUserRightsMst.getCanReadFlag()));
			cs.setString(7, Character.toString(dtoWorkSpaceUserRightsMst.getCanAddFlag()));
			cs.setString(8, Character.toString(dtoWorkSpaceUserRightsMst.getCanEditFlag()));
			cs.setString(9, Character.toString(dtoWorkSpaceUserRightsMst.getCanDeleteFlag()));
			cs.setString(10, dtoWorkSpaceUserRightsMst.getAdvancedRights());
			cs.setString(11, dtoWorkSpaceUserRightsMst.getRemark());
			cs.setInt(12, dtoWorkSpaceUserRightsMst.getModifyBy());
			cs.setInt(13, dtoWorkSpaceUserRightsMst.getStageId());
			cs.setTimestamp(14, dtoWorkSpaceUserRightsMst.getStartDate());
			cs.setTimestamp(15, dtoWorkSpaceUserRightsMst.getEndDate());
			cs.setTimestamp(16, dtoWorkSpaceUserRightsMst.getFromDate());
			cs.setTimestamp(17, dtoWorkSpaceUserRightsMst.getToDate());
			cs.setTimestamp(18, dtoWorkSpaceUserRightsMst.getAdjustDate());
			cs.setInt(19,dtoWorkSpaceUserRightsMst.getMode());//Insert userrights for one stage.
			cs.execute();
			
			prevUserCode = dtoWorkSpaceUserRightsMst.getUserCode();
			prevWorkspaceId = dtoWorkSpaceUserRightsMst.getWorkSpaceId();
		}
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
		try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
	}
}

public Vector<DTOWorkSpaceUserRightsMst> getSeqNoForSignoffSequence(String wsId, int nodeId,int usercode)
{
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		String field =  "urs.vWorkSpaceId,urs.iNodeId,urs.vUserName,urs.iUserCode,wnh.vFileName,urs.iseqNo,wnh.vCoordinates";
		String Table = "view_userRightsDetailForESignature AS urs INNER JOIN dbo.workspacenodehistory AS wnh ON wnh.vWorkSpaceId = urs.vWorkSpaceId"
				+ " AND wnh.iNodeId = urs.iNodeId";
		String where = "urs.vWorkspaceId = '"+wsId+"' and urs.iNodeId ="+nodeId+" AND urs.iUserCode = "+usercode+"";
		rs = dataTable.getDataSet(con, field ,
				Table,where, "urs.iseqNo");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setUserName(rs.getString("vUserName"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setNodeId(rs.getInt("inodeid"));
			dto.setfName(rs.getString("vFileName"));
			dto.setCoordiNates(rs.getString("vCoordinates"));
			dto.setSeqNo(rs.getInt("iSeqNo"));
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

public Vector<DTOWorkSpaceUserRightsMst> getUserlistForReplicateRights(String wsId, int nodeId)
{
	Vector<DTOWorkSpaceUserRightsMst> data = new Vector<DTOWorkSpaceUserRightsMst>();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		String field =  "wsum.vworkspaceid,wsum.inodeid, um.iUserCode, um.iUserGroupCode, wsum.istageid,um.vUserName, um.vLoginName, "
						+ "um.vUsertypeCode, ut. vUsertypeNAme, ug.vUserGroupName";
		String Table = "workspaceuserrightsmst as wsum inner join usermst as um on wsum.iUserCode = um.IuserCode "
						+ "inner join usertypemst as ut on um.vUserTypeCode = ut.vUserTypeCode "
						+ "inner join usergroupmst as ug on um.iUserGroupCode = ug.IuserGroupCode";
		String where = "ut.vUserTypeName<>'SU' and ut.vUsertypeName<>'WA' and  wsum.vworkspaceid= '"+wsId+"' and wsum.inodeid="+nodeId;
		rs = dataTable.getDataSet(con, field ,Table,where, "");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("inodeid"));
			dto.setUserCode(rs.getInt("iusercode"));
			dto.setStageId(rs.getInt("istageId"));
			dto.setUserGroupCode(rs.getInt("iuserGroupcode"));
			dto.setUserName(rs.getString("vUserName"));
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

}//main class ended

