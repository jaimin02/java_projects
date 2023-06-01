package com.docmgmt.server.db.master;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.docmgmt.dto.DTOWorkspaceNodeDocHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.opensymphony.xwork2.ActionContext;

public class WorkspaceNodeDocHistory {

	ConnectionManager conMgr;
	DataTable datatable;
	public WorkspaceNodeDocHistory()
	{
		 conMgr=new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}
	
 
public int getNewDocTranNo(String workspaceId) {
	
	int tranNo = 0;
	try {
		Connection con = ConnectionManager.ds.getConnection();
	
		String where = "vWorkspaceId = "+workspaceId;
		ResultSet rs = datatable.getDataSet(con, " MAX(iDocTranNo) AS iDocTranNo ", "WorkspaceNodeDocHistory", where, "");
		
		if(rs.next()){
			tranNo = rs.getInt("iDocTranNo");
		}
		
		//Creating new docTranNo;
		tranNo = tranNo + 1;
		rs.close();
		con.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	return tranNo;
}	
	
public void insertNodeDocHistory(ArrayList<DTOWorkspaceNodeDocHistory> WsNodeDocHistoryList,boolean copyFile) 
{	
	try {
		Connection con = ConnectionManager.ds.getConnection();
		
		CallableStatement proc = con.prepareCall("{ Call Insert_WorkspaceNodeDocHistory(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		for (int itrWsNodeDocHistory = 0; itrWsNodeDocHistory < WsNodeDocHistoryList.size(); itrWsNodeDocHistory++) 
		{
			DTOWorkspaceNodeDocHistory dto = WsNodeDocHistoryList.get(itrWsNodeDocHistory);		
			proc.setLong(1, 0);
			proc.setString(2,dto.getWorkspaceId());
			proc.setInt(3, dto.getNodeId());
			proc.setInt(4, dto.getDocTranNo());
			proc.setString(5, dto.getDocName());
			proc.setString(6, dto.getDocContentType());
			proc.setString(7, dto.getDocPath());
			proc.setInt(8, dto.getUploadedBy());
			proc.setString(9, "N");//converted
			proc.setString(10, dto.getRemark());
			proc.setInt(11, dto.getModifyBy());
			proc.setDouble(12, dto.getFileSize());
			proc.setString(13, "N");//statusIndi
			proc.setInt(14, 1);//Insert Mode
			if(copyFile)
			{
				File sourceLocation = dto.getSrcDoc();
				File targetLocation = new File(dto.getBaseSrcDir()+dto.getDocPath()+"/"+dto.getDocName());
				FileManager fileManager = new FileManager();
				fileManager.copyDirectory(sourceLocation,targetLocation);
			}
			proc.execute();
		}
		proc.close();
		con.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public void setConvertFlag(long docHistoryId, int userId) 
{	
	try {
		Connection con = ConnectionManager.ds.getConnection();
		
		CallableStatement proc = con.prepareCall("{ Call Insert_WorkspaceNodeDocHistory(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		proc.setLong(1,docHistoryId);
		proc.setString(2,"");//not required
		proc.setInt(3,0);//not required
		proc.setInt(4,0);//not required
		proc.setString(5,"");//not required
		proc.setString(6,"");//not required
		proc.setString(7,"");//not required
		proc.setInt(8, 0);//not required
		proc.setString(9,"Y");// Setting convert flag
		proc.setString(10,"");//not required
		proc.setInt(11,userId);//ModifyBy
		proc.setString(12,"E");//StatusIndi
		proc.setInt(13, 2);//Update Mode (Used while converting document into pdf)
		
		proc.execute();
		proc.close();
		con.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
 
public ArrayList<DTOWorkspaceNodeDocHistory>  getLatestNodeDocHistory(String workspaceId,int nodeid)
{
	ArrayList<DTOWorkspaceNodeDocHistory> docHistory = new ArrayList<DTOWorkspaceNodeDocHistory>();
	try 
	{		  
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		Connection con = ConnectionManager.ds.getConnection();
		
		String whrCon = " vWorkspaceId = '"+workspaceId+"' AND iNodeId = "+nodeid;
		String whr =whrCon + " AND iDocTranNo = (SELECT MAX(iDocTranNo) FROM View_WorkspaceNodeDocHistory WHERE "+whrCon+")";
		
		
		ResultSet rs=datatable.getDataSet(con,"*","View_WorkspaceNodeDocHistory" ,whr,"");
		if(rs.next())
		{
			DTOWorkspaceNodeDocHistory dto = new DTOWorkspaceNodeDocHistory();
			dto.setDocHistoryId(rs.getLong("iDocHistoryId"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setDocTranNo(rs.getInt("iDocTranNo"));
			dto.setDocName(rs.getString("vDocName"));
			dto.setDocContentType(rs.getString("vDocContentType"));
			dto.setDocPath(rs.getString("vDocPath"));
			dto.setConverted(rs.getString("cConverted").charAt(0));
			dto.setUploadedOn(rs.getTimestamp("dUploadedOn"));
			
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getUploadedOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getUploadedOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			
			dto.setConvertedOn(rs.getTimestamp("dConvertedOn"));
			dto.setRemark(rs.getString("vRemark"));
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
			
			
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			dto.setUploadedByUser(rs.getString("UploadedByUser"));
			dto.setModifyByUser(rs.getString("ModifyByUser"));
			
			docHistory.add(dto);
		}
		rs.close();
		con.close();
	}   
	catch(Exception e){
		e.printStackTrace();
	}
	return docHistory;
}

public ArrayList<DTOWorkspaceNodeDocHistory>  getFullNodeDocHistory(String workspaceId,int nodeid)
{
	ArrayList<DTOWorkspaceNodeDocHistory> docHistory = new ArrayList<DTOWorkspaceNodeDocHistory>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		
		String whr = " vWorkspaceId = '"+workspaceId+"' AND iNodeId = "+nodeid;
		
		
		ResultSet rs=datatable.getDataSet(con,"*","View_WorkspaceNodeDocHistory" ,whr,"");
		while(rs.next())
		{
			DTOWorkspaceNodeDocHistory dto = new DTOWorkspaceNodeDocHistory();
			dto.setDocHistoryId(rs.getLong("iDocHistoryId"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setDocTranNo(rs.getInt("iDocTranNo"));
			dto.setDocName(rs.getString("vDocName"));
			dto.setDocContentType(rs.getString("vDocContentType"));
			dto.setDocPath(rs.getString("vDocPath"));
			dto.setConverted(rs.getString("cConverted").charAt(0));
			dto.setUploadedOn(rs.getTimestamp("dUploadedOn"));
			dto.setConvertedOn(rs.getTimestamp("dConvertedOn"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			dto.setUploadedByUser(rs.getString("UploadedByUser"));
			dto.setModifyByUser(rs.getString("ModifyByUser"));
			docHistory.add(dto);
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return docHistory;
}
public ArrayList<DTOWorkspaceNodeDocHistory>  getWorkspaceNodeDocHistory(String workspaceId,int nodeid,int tranNo)
{
	ArrayList<DTOWorkspaceNodeDocHistory> docHistory = new ArrayList<DTOWorkspaceNodeDocHistory>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		
		String whr = " vWorkspaceId = '"+workspaceId+"' AND iNodeId = "+nodeid+" And iDocTranNo="+tranNo;
		
		
		ResultSet rs=datatable.getDataSet(con,"*","WorkspaceNodeDocHistory" ,whr,"");
		while(rs.next())
		{
			DTOWorkspaceNodeDocHistory dto = new DTOWorkspaceNodeDocHistory();
			dto.setDocHistoryId(rs.getLong("iDocHistoryId"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setDocTranNo(rs.getInt("iDocTranNo"));
			dto.setDocName(rs.getString("vDocName"));
			dto.setDocContentType(rs.getString("vDocContentType"));
			dto.setDocPath(rs.getString("vDocPath"));
			dto.setConverted(rs.getString("cConverted").charAt(0));
			dto.setUploadedOn(rs.getTimestamp("dUploadedOn"));
			dto.setConvertedOn(rs.getTimestamp("dConvertedOn"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			dto.setUploadedByUser(rs.getString("UploadedByUser"));
			dto.setModifyByUser(rs.getString("ModifyByUser"));
			docHistory.add(dto);
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return docHistory;
}
public void insertNodeDocHistoryForSaveAndSendUpload(ArrayList<DTOWorkspaceNodeDocHistory> WsNodeDocHistoryList, int userId) 
{	
	try {
		Connection con = ConnectionManager.ds.getConnection();
		
		CallableStatement proc = con.prepareCall("{ Call Insert_WorkspaceNodeDocHistory(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		proc.setLong(1,0);
		proc.setString(2,WsNodeDocHistoryList.get(0).getWorkspaceId());
		proc.setInt(3, WsNodeDocHistoryList.get(0).getNodeId());
		proc.setInt(4, WsNodeDocHistoryList.get(0).getDocTranNo());
		proc.setString(5,"");//not required
		proc.setString(6,"");//not required
		proc.setString(7,"");//not required
		proc.setInt(8, 0);//not required
		proc.setString(9,"");// Setting convert flag
		proc.setString(10,"");//not required
		proc.setInt(11,WsNodeDocHistoryList.get(0).getModifyBy());//ModifyBy
		proc.setDouble(12,WsNodeDocHistoryList.get(0).getFileSize());//StatusIndi
		proc.setString(13,"");//StatusIndi
		proc.setInt(14, 3);//Update Mode (Used while converting document into pdf)
		
		proc.execute();
		proc.close();
		con.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public String getFileNameForSrcDocUpload(String workspaceId,int nodeId){
	
	String fileName="";
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = null;
		
		String whr = " vWorkspaceId = '" + workspaceId + "' AND iNodeId = "
				+ nodeId + " "+"AND iDocTranNo = (Select max(iDocTranNo) from workspacenodedochistory where  vWorkSpaceId = '"+workspaceId+"' and iNodeId = "+nodeId+")";
		
		rs = datatable.getDataSet(con,"vDocName","workspacenodedochistory", whr.toString(),"");
		while (rs.next()) {			
			fileName=rs.getString("vDocName");				
		}
		rs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return fileName;
}
public int getMaxDocTranNo(String wsId, int nodeId) {
	int tranNo = -1;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		String where = " vWorkspaceId = '" + wsId + "' ";
		if (nodeId > 0)
			where += " and iNodeId =" + nodeId + " ";
		ResultSet rs = datatable.getDataSet(con, "max(iDocTranNo) as iTranNo",
				"WorkspaceNodeHistory", where, "");
		if (rs.next()) {
			tranNo = rs.getInt("iTranNo");
		}
		rs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return tranNo;
}
}//main class ended
