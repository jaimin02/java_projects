package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOCheckedoutFileDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class CheckedOutFileDetails 
{
	DataTable dataTable;
	public CheckedOutFileDetails()
	{
		dataTable=new DataTable();
	}
	
	
public Vector<DTOCheckedoutFileDetail> getLockedFileDetail(String wsId,int nodeId,int userId)
{
	Vector<DTOCheckedoutFileDetail> data = new Vector<DTOCheckedoutFileDetail>();
    try
    {
    	StringBuffer query = new StringBuffer();
		query.append("isNodeLocked ='Y'");
   		if(wsId != null) {
   			query.append(" and WorkspaceId ='"+wsId+"'");
   		}
   		if(userId != 0) {
   			query.append(" and ModifyBy ="+userId);
   		}
   		if(nodeId != 0) {
   			query.append(" and NodeId ="+nodeId);
   		}
   		Connection con = ConnectionManager.ds.getConnection();
   		ResultSet rs=dataTable.getDataSet(con,"*","view_CheckedoutFileDetail", query.toString(),"");
    	while(rs.next())
    	{
			DTOCheckedoutFileDetail dto = new DTOCheckedoutFileDetail();
			dto.setWorkSpaceId(rs.getString("WorkSpaceId"));//workspaceId
			dto.setNodeId(rs.getInt("nodeID"));//nodeId
			dto.setTranNo(rs.getInt("tranNo"));//tranNo
			dto.setPrevTranNo(rs.getInt("prevTranNo"));//prevTranNo
			dto.setFileName(rs.getString("fileName"));//fileName
			dto.setModifyOn(rs.getTimestamp("modifyOn"));//modifyOn
			dto.setUserName(rs.getString("userName"));//userName
			dto.setModifyBy(rs.getInt("modifyBy"));//modifyBy
			dto.setStatusIndi(rs.getString("statusIndi").charAt(0));//statusIndi
			dto.setWorkSpaceDesc(rs.getString("WorkspaceDesc"));//workspaceName
			dto.setBaseWorkFolder(rs.getString("BaseWorkFolder"));//baseWorksFolderName
			dto.setClientName(rs.getString("ClientName"));//clientName
			dto.setLocationName(rs.getString("LocationName"));//locationName
			dto.setProjectName(rs.getString("ProjectName"));//projectName
			dto.setIsNodeLocked(rs.getString("isNodeLocked").charAt(0));//nodeLocked
			data.addElement(dto);
			dto = null;
    		
    	}
    	rs.close();
    	con.close();
    		
    }catch (SQLException e) {
		e.printStackTrace();
	}
    return data;
}

public Vector<DTOCheckedoutFileDetail> nodeCheckedOutBy(String wsId, int nodeId)
{
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
    Vector<DTOCheckedoutFileDetail> data = new Vector<DTOCheckedoutFileDetail>();
    ArrayList<String> time = new ArrayList<String>();
    String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
    try
    {
        String whr="WorkSpaceId ='"+wsId+"' and NodeId ="+nodeId+" and isNodeLocked = 'Y' ";
        Connection con = ConnectionManager.ds.getConnection();
        ResultSet rs=dataTable.getDataSet(con,"*","view_CheckedoutFileDetail",whr,"");    
        while(rs.next())
        {
        	DTOCheckedoutFileDetail checkedoutFileDetail = new DTOCheckedoutFileDetail();
        	checkedoutFileDetail.setModifyOn(rs.getTimestamp("modifyOn"));
        if(countryCode.equalsIgnoreCase("IND")){
			time = docMgmtImpl.TimeZoneConversion(checkedoutFileDetail.getModifyOn(),locationName,countryCode);
			checkedoutFileDetail.setISTDateTime(time.get(0));
		}
		else{
			time = docMgmtImpl.TimeZoneConversion(checkedoutFileDetail.getModifyOn(),locationName,countryCode);
			checkedoutFileDetail.setISTDateTime(time.get(0));
			checkedoutFileDetail.setESTDateTime(time.get(1));
		}
        	checkedoutFileDetail.setModifyBy(rs.getInt("modifyBy"));
        	checkedoutFileDetail.setUserName(rs.getString("userName"));
        	data.addElement(checkedoutFileDetail);
        	checkedoutFileDetail = null;
        }
        
        rs.close();
        con.close();
    }           
    catch(SQLException e){
        e.printStackTrace();
    }
 
    return data;
} 

public void CheckedOutFiledetailOp(DTOCheckedoutFileDetail dto,int Mode)
{
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs=con.prepareCall("{ Call Insert_checkedoutfiledetail(?,?,?,?,?,?,?,?,?)}");
		cs.setString(1,dto.getWorkSpaceId());
		cs.setInt(2,dto.getNodeId());
		cs.setInt(3,dto.getTranNo());
		cs.setInt(4,dto.getPrevTranNo());
		if(dto.getFileName()==null)
		{
			dto.setFileName("NULL");
		}
		cs.setString(5,dto.getFileName());
		cs.setInt(6,dto.getModifyBy());
		cs.setString(7,Character.toString(dto.getIsNodeLocked()));
		cs.setString(8,Character.toString(dto.getStatusIndi()));
		cs.setInt(9,Mode);
		cs.execute();
		cs.close();
		con.close();
	}
	catch (SQLException e){
		e.printStackTrace();
	}
	
}

public void unLockFiles(String wsId, int nodeId, int tranNo) 
{
    try  
    {         	
    	Connection con = ConnectionManager.ds.getConnection();
    	CallableStatement cs=con.prepareCall("{ Call Proc_UnlockFiles(?,?,?)}");
    	cs.setString(1,wsId);
    	cs.setInt(2,nodeId);
    	cs.setInt(3,tranNo);
    	cs.execute();
    	cs.close();
        con.close();
         
    }catch(SQLException e){
    	e.printStackTrace();
    }
   
}

public void insertLockedFileDetail(DTOCheckedoutFileDetail dto)
{
    try 
    {
        Connection con = ConnectionManager.ds.getConnection();
        CallableStatement cs=con.prepareCall("{ Call Insert_checkedoutfiledetail(?,?,?,?,?,?,?,?,?)}");
        cs.setString(1,dto.getWorkSpaceId());
		cs.setInt(2,dto.getNodeId());
		cs.setInt(3,dto.getTranNo());
		cs.setInt(4,dto.getPrevTranNo());
		if(dto.getFileName()==null)
		{
			dto.setFileName("NULL");
		}
		cs.setString(5,dto.getFileName());
		cs.setInt(6,dto.getModifyBy());
		cs.setString(7,"N"); 
		cs.setString(8,"E"); //status indi E
		cs.setInt(9,2); //mode 2 for update
		cs.execute();
		cs.close();
		cs=null;
		
		cs=con.prepareCall("{ Call Insert_checkedoutfiledetail(?,?,?,?,?,?,?,?,?)}");
		cs.setString(1,dto.getWorkSpaceId());
		cs.setInt(2,dto.getNodeId());
		cs.setInt(3,dto.getTranNo());
		cs.setInt(4,dto.getPrevTranNo());
		if(dto.getFileName()==null)
		{
			dto.setFileName("NULL");
		}
		cs.setString(5,dto.getFileName());
		cs.setInt(6,dto.getModifyBy());
		cs.setString(7,"Y"); 
		cs.setString(8,"N"); //status indi N for New
		cs.setInt(9,1); //mode 1 for insert
		cs.execute();
		cs.close();
		con.close();
     }        
     catch(SQLException e) {
         e.printStackTrace();
     }
     
} 

public Vector<DTOCheckedoutFileDetail> getLockedFileDetailForAdmin(int userId,int userGroupId) {
	
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOCheckedoutFileDetail> data = new Vector<DTOCheckedoutFileDetail>();
    
    StringBuffer csvWorkspaceIds = new StringBuffer();
    ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	
    ResultSet rs = null;
    Connection con = null;
	try 
	{
		con = ConnectionManager.ds.getConnection();
		
		String where = "iusergroupCode ="+userGroupId +" and "+ " iusercode="+userId;
		rs = dataTable.getDataSet(con, "vWorkspaceId", "workspaceUserMst", where, "");
		while(rs.next())
		{
			csvWorkspaceIds.append("'"+rs.getString("vWorkspaceId")+"'");
			csvWorkspaceIds.append(",");
		}
		csvWorkspaceIds.deleteCharAt(csvWorkspaceIds.lastIndexOf(","));
	
		//Close RS.
		try{if(rs!=null)rs.close();}catch (Exception e) {e.printStackTrace();}
		
		rs = null;
		String query = " ProjectType<>'D' and isNodeLocked ='Y' AND WorkspaceId IN ("+csvWorkspaceIds.toString()+")";
		rs = dataTable.getDataSet(con, "*", "view_CheckedoutFileDetail", query, "");
	
		while(rs.next())
		{
			DTOCheckedoutFileDetail dto = new DTOCheckedoutFileDetail();
			dto.setWorkSpaceId(rs.getString("WorkSpaceId"));
			dto.setNodeId(rs.getInt("nodeID"));
			dto.setTranNo(rs.getInt("tranNo"));
			dto.setPrevTranNo(rs.getInt("prevTranNo"));
			dto.setFileName(rs.getString("fileName"));
			
			String fileExt = dto.getFileName().substring(dto.getFileName().lastIndexOf(".")+1);
			dto.setFileExt(fileExt);
			
			dto.setModifyOn(rs.getTimestamp("modifyOn"));
		if(countryCode.equalsIgnoreCase("IND")){
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
		}
		else{
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
			dto.setESTDateTime(time.get(1));
		}
			dto.setUserName(rs.getString("userName"));
			dto.setModifyBy(rs.getInt("modifyBy"));
			dto.setStatusIndi(rs.getString("statusIndi").charAt(0));
			dto.setWorkSpaceDesc(rs.getString("WorkspaceDesc"));
			dto.setBaseWorkFolder(rs.getString("BaseWorkFolder"));
			dto.setClientName(rs.getString("ClientName"));
			dto.setLocationName(rs.getString("LocationName"));
			dto.setProjectName(rs.getString("ProjectName"));
			dto.setIsNodeLocked(rs.getString("isNodeLocked").charAt(0));
			data.addElement(dto);
		}
			
    }catch (Exception e) {
		e.printStackTrace();
	}
    finally{
    	try{if(rs!=null)rs.close();}catch (Exception e) {e.printStackTrace();}
    	try{if(con!=null)con.close();}catch (Exception e) {e.printStackTrace();}
    }
    
    return data;
}
public Vector<DTOCheckedoutFileDetail> getLockedFileDetailForAdminForCSV(String wsId,int nodeId,int tranNo,int userId,int userGroupId) {
	
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOCheckedoutFileDetail> data = new Vector<DTOCheckedoutFileDetail>();
    
    StringBuffer csvWorkspaceIds = new StringBuffer();
    ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	
    ResultSet rs = null;
    Connection con = null;
	try 
	{
		con = ConnectionManager.ds.getConnection();
		
		String where = "vWorkspaceId= '"+wsId+"' and iusergroupCode ="+userGroupId +" and "+ " iusercode="+userId;
		rs = dataTable.getDataSet(con, "vWorkspaceId", "workspaceUserMst", where, "");
		while(rs.next())
		{
			csvWorkspaceIds.append("'"+rs.getString("vWorkspaceId")+"'");
			csvWorkspaceIds.append(",");
		}
		csvWorkspaceIds.deleteCharAt(csvWorkspaceIds.lastIndexOf(","));
	
		//Close RS.
		try{if(rs!=null)rs.close();}catch (Exception e) {e.printStackTrace();}
		
		rs = null;
		String query = " WorkspaceId= '"+wsId+"' and nodeId="+nodeId+" and tranNo="+tranNo+" and ProjectType<>'D' and isNodeLocked ='Y' AND WorkspaceId IN ("+csvWorkspaceIds.toString()+")";
		rs = dataTable.getDataSet(con, "*", "view_CheckedoutFileDetail", query, "");
	
		while(rs.next())
		{
			DTOCheckedoutFileDetail dto = new DTOCheckedoutFileDetail();
			dto.setWorkSpaceId(rs.getString("WorkSpaceId"));
			dto.setNodeId(rs.getInt("nodeID"));
			dto.setTranNo(rs.getInt("tranNo"));
			dto.setPrevTranNo(rs.getInt("prevTranNo"));
			dto.setFileName(rs.getString("fileName"));
			dto.setModifyOn(rs.getTimestamp("modifyOn"));
		if(countryCode.equalsIgnoreCase("IND")){
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
		}
		else{
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
			dto.setESTDateTime(time.get(1));
		}
			dto.setUserName(rs.getString("userName"));
			dto.setModifyBy(rs.getInt("modifyBy"));
			dto.setStatusIndi(rs.getString("statusIndi").charAt(0));
			dto.setWorkSpaceDesc(rs.getString("WorkspaceDesc"));
			dto.setBaseWorkFolder(rs.getString("BaseWorkFolder"));
			dto.setClientName(rs.getString("ClientName"));
			dto.setLocationName(rs.getString("LocationName"));
			dto.setProjectName(rs.getString("ProjectName"));
			dto.setIsNodeLocked(rs.getString("isNodeLocked").charAt(0));
			data.addElement(dto);
		}
			
    }catch (Exception e) {
		e.printStackTrace();
	}
    finally{
    	try{if(rs!=null)rs.close();}catch (Exception e) {e.printStackTrace();}
    	try{if(con!=null)con.close();}catch (Exception e) {e.printStackTrace();}
    }
    
    return data;
}

public Vector<DTOCheckedoutFileDetail> getLockedFileDetailForUser( int userId)
{
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOCheckedoutFileDetail> data = new Vector<DTOCheckedoutFileDetail>();
	Connection con = null;
	ResultSet rs = null;
	
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	
	try
    {
		con = ConnectionManager.ds.getConnection();
		String query = " isNodeLocked ='Y' and ProjectType<>'D'";
		if(userId != 0) {
			query+=" AND modifyBy ="+userId;
		}
		rs = dataTable.getDataSet(con, "*", "view_CheckedoutFileDetail", query, "");
		while(rs.next())
		{
			DTOCheckedoutFileDetail dto = new DTOCheckedoutFileDetail();
			dto.setWorkSpaceId(rs.getString("WorkSpaceId"));
   			dto.setNodeId(rs.getInt("nodeID"));
   			dto.setTranNo(rs.getInt("tranNo"));
   			dto.setPrevTranNo(rs.getInt("prevTranNo"));
   			dto.setFileName(rs.getString("fileName"));
   			
   			String fileExt = dto.getFileName().substring(dto.getFileName().lastIndexOf(".")+1);
			dto.setFileExt(fileExt);
   			
   			dto.setModifyOn(rs.getTimestamp("modifyOn"));
   		if(countryCode.equalsIgnoreCase("IND")){
   			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
   			dto.setISTDateTime(time.get(0));
   		}
   		else{
   			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
   			dto.setISTDateTime(time.get(0));
   			dto.setESTDateTime(time.get(1));
   		}
   			dto.setUserName(rs.getString("userName"));
   			dto.setModifyBy(rs.getInt("modifyBy"));
   			dto.setStatusIndi(rs.getString("statusIndi").charAt(0));
   			dto.setWorkSpaceDesc(rs.getString("WorkspaceDesc"));
   			dto.setBaseWorkFolder(rs.getString("BaseWorkFolder"));
   			dto.setClientName(rs.getString("ClientName"));
   			dto.setLocationName(rs.getString("LocationName"));
   			dto.setProjectName(rs.getString("ProjectName"));
   			dto.setIsNodeLocked(rs.getString("isNodeLocked").charAt(0));
   			data.addElement(dto);
   		}
	
	}catch (SQLException e) {
		e.printStackTrace();
	}
    finally{
    	try{if(rs!=null)rs.close();}catch (SQLException e) {e.printStackTrace();}
    	try{if(con!=null)con.close();}catch (SQLException e) {e.printStackTrace();}
    }
    return data;
}

public boolean isCheckOut(String wsId, int nodeId, int userId)
{
	boolean retVal = false;
    
	Connection con = null;
	ResultSet rs = null;
	try 
    {
		con = ConnectionManager.ds.getConnection();
        String query ="isNodeLocked  = 'Y' and vWorkspaceId= '"+wsId+"' and iNodeId="+nodeId;
        if(userId == 0) 
        {
        	query +=" AND iModifyBy = "+userId;
        }
        rs = dataTable.getDataSet(con, "dModifyOn,iModifyBy", "checkedoutfiledetail", query, "");
        
        if(rs.next()){
        	retVal = true;
        }else{
        	retVal = false;
        }
    }           
    catch(SQLException e){
    	e.printStackTrace();
    }
    finally{
    	try{if(rs!=null)rs.close();}catch (SQLException e) {e.printStackTrace();}
    	try{if(con!=null)con.close();}catch (SQLException e) {e.printStackTrace();}
    }
    
  return retVal;
} 

public Vector<DTOCheckedoutFileDetail> getMaxcheckOutFileDetail(String wsId,int nodeId)
{
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOCheckedoutFileDetail> data = new Vector<DTOCheckedoutFileDetail>();
	Connection con = null;
	ResultSet rs = null;
	
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	
	try
    {
		con = ConnectionManager.ds.getConnection();
		String where = " vWorkspaceId = '" + wsId + "' ";
		where += "and iNodeId =" + nodeId;
		where+= " and iTranNo = (select max(iTranNo) from checkedoutfiledetail where vworkspaceid='" + wsId + "' and inodeid="+nodeId+") ";
		rs = dataTable.getDataSet(con, "*",
			"checkedoutfiledetail", where, "");
		while(rs.next())
		{
			DTOCheckedoutFileDetail dto = new DTOCheckedoutFileDetail();
			dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
   			dto.setNodeId(rs.getInt("inodeId"));
   			dto.setTranNo(rs.getInt("itranNo"));
   			dto.setPrevTranNo(rs.getInt("iprevTranNo"));
   			dto.setFileName(rs.getString("vfileName"));
   			dto.setModifyOn(rs.getTimestamp("dmodifyOn"));
   		if(countryCode.equalsIgnoreCase("IND")){
   			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
   			dto.setISTDateTime(time.get(0));
   		}
   		else{
   			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
   			dto.setISTDateTime(time.get(0));
   			dto.setESTDateTime(time.get(1));
   		}
   			dto.setIsNodeLocked(rs.getString("isNodeLocked").charAt(0));
   			data.addElement(dto);
   			dto=null;
   		}
	
	}catch (SQLException e) {
		e.printStackTrace();
	}
    finally{
    	try{if(rs!=null)rs.close();}catch (SQLException e) {e.printStackTrace();}
    	try{if(con!=null)con.close();}catch (SQLException e) {e.printStackTrace();}
    }
    return data;
}


}//main class end
