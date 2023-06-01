package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOForumDtl;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;

public class ForumMst 
{
	DataTable dataTable;
	public String alloweTMFCustomization;
	
	PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	
	public ForumMst()
	{
		dataTable=new DataTable();
		
	}
	
	public Vector<DTOForumDtl> showNodeComments(DTOWorkSpaceNodeDetail dto)
	{
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOForumDtl> nodeCommentVector = new Vector<DTOForumDtl>();
		Connection con = null;
		ResultSet rs = null;
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		try 
		{
			String whr="vWorkSpaceId = '"+dto.getWorkspaceId()+"' and iNodeId="+dto.getNodeId()+" and " +
						"ReceiverUserGroupCode="+dto.getUserGroupCode()+" and ReceiverUserCode="+dto.getUserCode();
			con = ConnectionManager.ds.getConnection();
			rs=dataTable.getDataSet(con,"vWorkSpaceId,iNodeId,vSubjectId,SenderUserName,vSubjectDesc,vFileName,dCreatedDate ","View_ShowNodeComments", whr, " dCreatedDate desc");			
			if (rs!=null)
			{
				while(rs.next())
				{
					DTOForumDtl fdtl = new DTOForumDtl();
					fdtl.setWorkspaceId(rs.getString("vWorkSpaceId"));
					fdtl.setNodeId(rs.getInt("iNodeId"));
					fdtl.setSubjectId(rs.getString("vSubjectId"));
					fdtl.setUserName(rs.getString("SenderUserName"));
					fdtl.setSubjectDesc(rs.getString("vSubjectDesc"));
					fdtl.setFileName(rs.getString("vFileName"));
					fdtl.setModifyOn(rs.getTimestamp("dCreatedDate"));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(fdtl.getModifyOn(),locationName,countryCode);
					fdtl.setISTDateTime(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(fdtl.getModifyOn(),locationName,countryCode);
					fdtl.setISTDateTime(time.get(0));
					fdtl.setESTDateTime(time.get(1));
				}
					nodeCommentVector.addElement(fdtl);
				}			
				
			}
				
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
			try {if(con != null){con.close();}} catch (Exception e) {e.printStackTrace();}
		}
		return nodeCommentVector;
	}
	public String getupdatedNodedetails(int nodeid,String WorksapceId,String SubjectId)
	{
		String SubmissionDesc ="";
		Connection con = null;
		ResultSet rs = null;
		try 
		{
			String whr="vWorkSpaceId = '"+WorksapceId+"' and iNodeId="+nodeid+" and " +
						"vSubjectId="+SubjectId;
			con = ConnectionManager.ds.getConnection();
			rs=dataTable.getDataSet(con,"*","View_ShowNodeComments", whr, "");			
			if (rs!=null)
			{
				while(rs.next())
				{
					SubmissionDesc=rs.getString("vSubjectDesc");
				}			
				
			}
				
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
			try {if(con != null){con.close();}} catch (Exception e) {e.printStackTrace();}
		}
		return SubmissionDesc;
	}
	public Vector<DTOForumDtl> getActivityCommentsReport(DTOWorkSpaceNodeDetail objWorkSpaceNodeDetail)
	{
		Vector<DTOForumDtl> data = new Vector<DTOForumDtl>();
		Connection con = null;
		ResultSet rs = null;
		try 
		{
			StringBuffer query = new StringBuffer();
			query.append(" WorkSpaceId = '"+objWorkSpaceNodeDetail.getWorkspaceId()+"' " );
			if(objWorkSpaceNodeDetail.getNodeId()> 0)
				query.append("and NodeId = "+objWorkSpaceNodeDetail.getNodeId());
			if(objWorkSpaceNodeDetail.getUserCode() > 0)
				query.append("and UserCode = "+objWorkSpaceNodeDetail.getUserCode());
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "*", "view_GetActivityCommentsReport", query.toString(), "modifyOn");
			while (rs.next()) 
			{
			    DTOForumDtl dto = new DTOForumDtl();
			    dto.setWorkSpaceDesc(rs.getString("workSpaceDesc"));
			    dto.setNodeName(rs.getString("nodeDisplayName"));
			    dto.setSubjectDesc(rs.getString("subjectDesc"));
			    dto.setSubjectId(rs.getString("subjectId"));
			    dto.setSenderName(rs.getString("sender"));
			    dto.setReceiverName(rs.getString("receiver"));
			    dto.setModifyOn(rs.getTimestamp("modifyOn"));				
			    dto.setWorkspaceId(rs.getString("workSpaceId"));
			    dto.setNodeId(rs.getInt("nodeId"));
			    dto.setUserCode(rs.getInt("userCode"));
			    data.addElement(dto);
			}
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
			try {if(con != null){con.close();}} catch (Exception e) {e.printStackTrace();}
		}
		return data;
	}

	public ArrayList<DTOForumDtl> getComments(int iReceiverUserCode)
	{
		ArrayList<DTOForumDtl> comments=new ArrayList<DTOForumDtl>();
		Connection connection = null;
		ResultSet rs = null;
		try 
		{
			connection=ConnectionManager.ds.getConnection();
			rs=dataTable.getDataSet(connection, "*","View_ShowNodeComments","ReceiverUserCode="+iReceiverUserCode+"and ForumHdrStatusIndi<>'D'","cReadFlag,dCreatedDate desc");
			while(rs.next())
			{
				DTOForumDtl comment=new DTOForumDtl();
				comment.setSenderName(rs.getString("SenderUserName"));
				comment.setSubjectDesc(rs.getString("vSubjectDesc"));
				comment.setWorkspaceId(rs.getString("vWorkSpaceId"));
				comment.setNodeId(rs.getInt("iNodeId"));
				comment.setReceiverUserCode(rs.getInt("ReceiverUserCode"));
				comment.setReceiverGroupCode(rs.getInt("ReceiverUserGroupCode"));
				comment.setSenderUserCode(rs.getInt("SenderUserCode"));
				comment.setSubjectId(rs.getString("vSubjectId"));
				comment.setNodeName(rs.getString("vNodeName"));
				comment.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				comment.setFolderName(rs.getString("vFolderName"));
				comment.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				comment.setForumhdrstatusindi(rs.getString("ForumHdrStatusIndi"));
				comment.setReadFlag(rs.getString("cReadFlag").charAt(0));				
				comment.setCreatedOn(rs.getTimestamp("dCreatedDate"));
				comment.setTypeFlag(rs.getString("cTypeFlag"));
				comment.setRefSubjectId(rs.getString("vRefSubjectId"));
				comment.setForumHdrModifyOn(rs.getTimestamp("ForumHdrModifyOn"));
				comment.setForumHdrModifyBy(rs.getInt("ForumHdrModifyBy"));
				comments.add(comment);
			}
		} 
		catch (Exception e) 
		{		
			e.printStackTrace();
		}
		finally{
			try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
			try {if(connection != null){connection.close();}} catch (Exception e) {e.printStackTrace();}
		}
		return comments;
	}
	
	public ArrayList<DTOForumDtl> getSentComments(int userCode,String workspaceID,int nodeId)
	{
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOForumDtl> comments=new ArrayList<DTOForumDtl>();
		Connection connection = null;
		ResultSet rs = null;
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		try 
		{
			connection=ConnectionManager.ds.getConnection();
			rs=dataTable.getDataSet(connection, " * "," View_ShowNodeComments "," SenderUserCode="+userCode+" and ForumHdrStatusIndi<>'D' and vWorkSpaceId = '"+workspaceID+"' and iNodeId = "+nodeId," cReadFlag,dCreatedDate desc");
			while(rs.next())
			{
				
				DTOForumDtl comment=new DTOForumDtl();
				comment.setSenderName(rs.getString("SenderUserName"));
				comment.setReceiverName(rs.getString("ReceiverUserName"));
				comment.setSubjectDesc(rs.getString("vSubjectDesc"));
				comment.setFileName(rs.getString("vFileName"));									
				comment.setWorkspaceId(rs.getString("vWorkSpaceId"));
				comment.setNodeId(rs.getInt("iNodeId"));
				comment.setReceiverUserCode(rs.getInt("ReceiverUserCode"));
				comment.setReceiverGroupCode(rs.getInt("ReceiverUserGroupCode"));
				comment.setSenderUserCode(rs.getInt("SenderUserCode"));
				comment.setSubjectId(rs.getString("vSubjectId"));
				comment.setNodeName(rs.getString("vNodeName"));
				comment.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				comment.setFolderName(rs.getString("vFolderName"));
				comment.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				comment.setForumhdrstatusindi(rs.getString("ForumHdrStatusIndi"));
				comment.setReadFlag(rs.getString("cReadFlag").charAt(0));				
				comment.setCreatedOn(rs.getTimestamp("dCreatedDate"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(comment.getCreatedOn(),locationName,countryCode);
				comment.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(comment.getCreatedOn(),locationName,countryCode);
				comment.setISTDateTime(time.get(0));
				comment.setESTDateTime(time.get(1));
			}
				comment.setTypeFlag(rs.getString("cTypeFlag"));
				comment.setRefSubjectId(rs.getString("vRefSubjectId"));
				comment.setForumHdrModifyOn(rs.getTimestamp("ForumHdrModifyOn"));	
				comment.setForumHdrModifyBy(rs.getInt("ForumHdrModifyBy"));				
				comments.add(comment);
			}
		} 
		catch (Exception e) 
		{		
			e.printStackTrace();
		}
		finally{
			try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
			try {if(connection != null){connection.close();}} catch (Exception e) {e.printStackTrace();}
		}
		return comments;
		
	}
	public ArrayList<DTOForumDtl> getAllComments(String workspaceID,int nodeId)
	{
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOForumDtl> comments=new ArrayList<DTOForumDtl>();
		Connection connection = null;
		ResultSet rs = null;
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		try 
		{
			connection=ConnectionManager.ds.getConnection();
			rs=dataTable.getDataSet(connection, " * "," View_ShowNodeComments ","ForumHdrStatusIndi<>'D' and vWorkSpaceId = '"+workspaceID+"' and iNodeId = "+nodeId," vRefSubjectId,vSubjectId desc");
			while(rs.next())
			{
				
				DTOForumDtl comment=new DTOForumDtl();
				comment.setSenderName(rs.getString("SenderUserName"));
				comment.setReceiverName(rs.getString("ReceiverUserName"));
				comment.setSubjectDesc(rs.getString("vSubjectDesc"));
				comment.setFileName(rs.getString("vFileName"));									
				comment.setWorkspaceId(rs.getString("vWorkSpaceId"));
				comment.setNodeId(rs.getInt("iNodeId"));
				comment.setReceiverUserCode(rs.getInt("ReceiverUserCode"));
				comment.setReceiverGroupCode(rs.getInt("ReceiverUserGroupCode"));
				comment.setSenderUserCode(rs.getInt("SenderUserCode"));
				comment.setSubjectId(rs.getString("vSubjectId"));
				comment.setNodeName(rs.getString("vNodeName"));
				comment.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				comment.setFolderName(rs.getString("vFolderName"));
				comment.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				comment.setForumhdrstatusindi(rs.getString("ForumHdrStatusIndi"));
				comment.setReadFlag(rs.getString("cReadFlag").charAt(0));				
				comment.setCreatedOn(rs.getTimestamp("dCreatedDate"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(comment.getCreatedOn(),locationName,countryCode);
				comment.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(comment.getCreatedOn(),locationName,countryCode);
				comment.setISTDateTime(time.get(0));
				comment.setESTDateTime(time.get(1));
			}
				comment.setTypeFlag(rs.getString("cTypeFlag"));
				comment.setRefSubjectId(rs.getString("vRefSubjectId"));
				comment.setForumHdrModifyOn(rs.getTimestamp("ForumHdrModifyOn"));	
				comment.setForumHdrModifyBy(rs.getInt("ForumHdrModifyBy"));	
				comment.setResolverName(rs.getString("ResolverUserName"));
				comment.setResolverFlag(rs.getString("cResolverFlag"));
				comments.add(comment);
			}
		} 
		catch (Exception e) 
		{		
			e.printStackTrace();
		}
		finally{
			try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
			try {if(connection != null){connection.close();}} catch (Exception e) {e.printStackTrace();}
		}
		return comments;
		
	}
	
	public int getNumOfComments(int iReceiverUserCode,int noOfRecords,int nodeId,String workspaceId)
	{		
		alloweTMFCustomization = propertyInfo.getValue("ETMFCustomization");
		alloweTMFCustomization= alloweTMFCustomization.toLowerCase();
		Connection connection = null;
		ResultSet rs=null;
		int numOfPages=0;
		//String where="ReceiverUserCode="+iReceiverUserCode+" and ForumHdrStatusIndi<>'D'";
		String where="(ReceiverUserCode="+iReceiverUserCode+" OR SenderUserCode="+iReceiverUserCode+") and ForumHdrStatusIndi<>'D'";
		
		if(alloweTMFCustomization.equals("yes"))
		{
			where += " and cTypeFlag IS null ";
		}
		if(nodeId!=0 && workspaceId!=null)
		{
			where += "and iNodeId="+nodeId+"and vWorkSpaceId="+workspaceId;
		}
		try 
		{
			connection=ConnectionManager.ds.getConnection();
			rs=dataTable.getNumberOfPages(connection,"View_ShowNodeComments",where,"cReadFlag,dCreatedDate desc","cReadFlag,dCreatedDate desc",noOfRecords);
			if (rs.next())
				numOfPages=rs.getInt("noOfPages");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
			try {if(connection != null){connection.close();}} catch (Exception e) {e.printStackTrace();}
		}
		return numOfPages;
	}
	
	public int getNumOfSentComments(int iReceiverUserCode,int noOfRecords)
	{		
		Connection connection=null;
		ResultSet rs = null;
		int numOfPages=0;
		try 
		{
			connection=ConnectionManager.ds.getConnection();
			rs=dataTable.getNumberOfPages(connection,"View_ShowNodeComments","SenderUserCode="+iReceiverUserCode+" and ForumHdrStatusIndi<>'D'","cReadFlag,dCreatedDate desc","cReadFlag,dCreatedDate desc",noOfRecords);
			if (rs.next())
				numOfPages=rs.getInt("noOfPages");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
			try {if(connection != null){connection.close();}} catch (Exception e) {e.printStackTrace();}
		}
		return numOfPages;
	}
	
	/*public ArrayList<Integer> checkResolvedComments(int nodeId, String workspaceId){
		
		ArrayList<Integer> numOfResolvedComments =new ArrayList<Integer>();
		Connection connection = null;
		ResultSet rs = null;
		ArrayList<Integer> resolvedCommentList = new ArrayList<Integer>();
		String Where;
		
		Where = "iNodeId="+nodeId+" and vWorkSpaceId="+workspaceId;
		
		try 
		{
			connection=ConnectionManager.ds.getConnection();
			rs=dataTable.getDataSet(connection, "Distinct vRefSubjectId ","forumhdr",Where,"");
			while(rs.next())
				resolvedCommentList.add(rs.getInt("vRefSubjectId"));
			  	System.out.println(resolvedCommentList);
			if(resolvedCommentList!=null){
				for(int i=0;i<resolvedCommentList.size();i++){
					Where = "iNodeId="+nodeId+" and vWorkSpaceId="+workspaceId+" and vRefSubjectId="+resolvedCommentList.get(i)+" and cTypeFlag='R'";
					rs=dataTable.getDataSet(connection, "*","forumhdr",Where,"");
					while(rs.next())
						numOfResolvedComments.add(rs.getInt("vRefSubjectId"));
						System.out.println(numOfResolvedComments);
				}
				
			}
		} 
		catch (Exception e) 
		{		
			e.printStackTrace();
		}
		finally{
			try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
			try {if(connection != null){connection.close();}} catch (Exception e) {e.printStackTrace();}
		}
		return numOfResolvedComments;
	}*/
	
	
	public ArrayList<DTOForumDtl> getComments(int iReceiverUserCode,int pageNo,int noOfRecords,int nodeId,String workspaceId)
	{
		alloweTMFCustomization = propertyInfo.getValue("ETMFCustomization");
		alloweTMFCustomization= alloweTMFCustomization.toLowerCase();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOForumDtl> comments=new ArrayList<DTOForumDtl>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		Connection connection = null;
		ResultSet rs = null;
		//String where = "ReceiverUserCode="+iReceiverUserCode+" and ForumHdrStatusIndi<>'D' and cTypeFlag<>'R'";
		//String where = "ReceiverUserCode="+iReceiverUserCode+" and ForumHdrStatusIndi<>'D'";
		String where = "(ReceiverUserCode="+iReceiverUserCode+" OR SenderUserCode="+iReceiverUserCode+")"+" and ForumHdrStatusIndi<>'D' and WsStatusIndi<>'A'";
		
		if(alloweTMFCustomization.equals("yes"))
		{
			where += " and cTypeFlag IS null ";
		}
		if(nodeId!=0 && workspaceId!=null)
		{
			where += "and iNodeId="+nodeId+" and vWorkSpaceId="+workspaceId;
		}
		
		try 
		{
			connection=ConnectionManager.ds.getConnection();
			rs=dataTable.getDataSet(connection, "*","View_ShowNodeComments",where,"cReadFlag,dCreatedDate desc","cReadFlag,dCreatedDate desc",pageNo,noOfRecords);			
			while(rs.next())
			{
				DTOForumDtl comment=new DTOForumDtl();
				comment.setSenderName(rs.getString("SenderUserName"));
				comment.setSubjectDesc(rs.getString("vSubjectDesc"));
				comment.setFileName(rs.getString("vFileName"));
				comment.setWorkspaceId(rs.getString("vWorkSpaceId"));
				comment.setNodeId(rs.getInt("iNodeId"));
				comment.setReceiverUserCode(rs.getInt("ReceiverUserCode"));
				comment.setReceiverGroupCode(rs.getInt("ReceiverUserGroupCode"));
				comment.setSenderUserCode(rs.getInt("SenderUserCode"));
				comment.setSubjectId(rs.getString("vSubjectId"));
				comment.setNodeName(rs.getString("vNodeName"));
				comment.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				comment.setFolderName(rs.getString("vFolderName"));
				comment.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				comment.setForumhdrstatusindi(rs.getString("ForumHdrStatusIndi"));
				comment.setReadFlag(rs.getString("cReadFlag").charAt(0));				
				comment.setCreatedOn(rs.getTimestamp("dCreatedDate"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(comment.getCreatedOn(),locationName,countryCode);
				comment.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(comment.getCreatedOn(),locationName,countryCode);
				comment.setISTDateTime(time.get(0));
				comment.setESTDateTime(time.get(1));
			}
				comment.setTypeFlag(rs.getString("cTypeFlag"));
				comment.setRefSubjectId(rs.getString("vRefSubjectId"));
				comment.setForumHdrModifyOn(rs.getTimestamp("ForumHdrModifyOn"));				
				comment.setForumHdrModifyBy(rs.getInt("ForumHdrModifyBy"));
				comment.setLockSeq(rs.getString("lockseqflag").charAt(0));
				comments.add(comment);
			}
		} 
		catch (Exception e) 
		{		
			e.printStackTrace();
		}
		finally{
			try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
			try {if(connection != null){connection.close();}} catch (Exception e) {e.printStackTrace();}
		}
		return comments;
	}
	
	public ArrayList<DTOForumDtl> getSentComments(int iReceiverUserCode)
	{
		ArrayList<DTOForumDtl> comments=new ArrayList<DTOForumDtl>();
		Connection connection =null;
		ResultSet rs = null;
		try 
		{
			connection=ConnectionManager.ds.getConnection();
			rs=dataTable.getDataSet(connection, "*","View_ShowNodeComments","SenderUserCode="+iReceiverUserCode,"cReadFlag,dCreatedDate desc");
			while(rs.next())
			{
				DTOForumDtl comment=new DTOForumDtl();
				comment.setSenderName(rs.getString("SenderUserName"));
				comment.setSubjectDesc(rs.getString("vSubjectDesc"));
				comment.setFileName(rs.getString("vFileName"));
				comment.setWorkspaceId(rs.getString("vWorkSpaceId"));
				comment.setNodeId(rs.getInt("iNodeId"));
				comment.setReceiverUserCode(rs.getInt("ReceiverUserCode"));
				comment.setReceiverName(new UserMst().getUserInfo(comment.getReceiverUserCode()).getUserName());
				comment.setReceiverGroupCode(rs.getInt("ReceiverUserGroupCode"));
				comment.setSenderUserCode(rs.getInt("SenderUserCode"));
				comment.setSubjectId(rs.getString("vSubjectId"));
				comment.setNodeName(rs.getString("vNodeName"));
				comment.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				comment.setFolderName(rs.getString("vFolderName"));
				comment.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				comment.setForumhdrstatusindi(rs.getString("ForumHdrStatusIndi"));
				comment.setReadFlag(rs.getString("cReadFlag").charAt(0));				
				comment.setCreatedOn(rs.getTimestamp("dCreatedDate"));
				comment.setTypeFlag(rs.getString("cTypeFlag"));
				comment.setRefSubjectId(rs.getString("vRefSubjectId"));
				comment.setForumHdrModifyOn(rs.getTimestamp("ForumHdrModifyOn"));
				comment.setForumHdrModifyBy(rs.getInt("ForumHdrModifyBy"));
				comments.add(comment);
			}
		} 
		catch (Exception e) 
		{		
			e.printStackTrace();
		}
		finally{
			try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
			try {if(connection != null){connection.close();}} catch (Exception e) {e.printStackTrace();}
		}
		return comments;
	}
	
	public ArrayList<DTOForumDtl> getDeletedComments(int iReceiverUserCode)
	{
		ArrayList<DTOForumDtl> comments=new ArrayList<DTOForumDtl>();
		Connection connection = null;
		ResultSet rs = null;
		try 
		{
			connection=ConnectionManager.ds.getConnection();
			rs=dataTable.getDataSet(connection, "*","View_ShowNodeComments","ReceiverUserCode="+iReceiverUserCode+"and ForumHdrStatusIndi='D'","cReadFlag,dCreatedDate desc");
			while(rs.next())
			{
				DTOForumDtl comment=new DTOForumDtl();
				comment.setSenderName(rs.getString("SenderUserName"));
				comment.setSubjectDesc(rs.getString("vSubjectDesc"));
				comment.setWorkspaceId(rs.getString("vWorkSpaceId"));
				comment.setNodeId(rs.getInt("iNodeId"));
				comment.setReceiverUserCode(rs.getInt("ReceiverUserCode"));
				comment.setReceiverGroupCode(rs.getInt("ReceiverUserGroupCode"));
				comment.setSenderUserCode(rs.getInt("SenderUserCode"));
				comment.setSubjectId(rs.getString("vSubjectId"));
				comment.setNodeName(rs.getString("vNodeName"));
				comment.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				comment.setFolderName(rs.getString("vFolderName"));
				comment.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				comment.setForumhdrstatusindi(rs.getString("ForumHdrStatusIndi"));
				comment.setReadFlag(rs.getString("cReadFlag").charAt(0));				
				comment.setCreatedOn(rs.getTimestamp("dCreatedDate"));
				comment.setTypeFlag(rs.getString("cTypeFlag"));
				comment.setRefSubjectId(rs.getString("vRefSubjectId"));
				comment.setForumHdrModifyOn(rs.getTimestamp("ForumHdrModifyOn"));
				comment.setForumHdrModifyBy(rs.getInt("ForumHdrModifyBy"));
				comments.add(comment);
			}
		} 
		catch (Exception e) 
		{		
			e.printStackTrace();
		}
		finally{
			try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
			try {if(connection != null){connection.close();}} catch (Exception e) {e.printStackTrace();}
		}
		return comments;
	}
	
	public String insertForumComment(String workspaceId,int nodeId,int rUserCode,String message,
			String replyFlag,String refSubId,String Uuid)
	{
		DTOForumDtl comment=new DTOForumDtl();
		comment.setWorkspaceId(workspaceId);
		comment.setNodeId(nodeId);
		comment.setReceiverUserCode(rUserCode);
		comment.setSubjectDesc(message);
		comment.setTypeFlag(replyFlag);
		comment.setRefSubjectId(refSubId);
		comment.setUuid(Uuid);
		UserMst userMst=new UserMst();
		comment.setSenderUserCode(Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString()));
		comment.setCommentSender(userMst.getUserInfo(comment.getSenderUserCode()));
		comment.setForumHdrModifyBy(comment.getSenderUserCode());		
		comment.setCommentReceiver(userMst.getUserInfo(rUserCode));
		comment.setReceiverGroupCode(comment.getCommentReceiver().getUserGroupCode());
		return insertForumDetails(comment,1);
	}
	
	public String insertForumComment(DTOWorkSpaceNodeDetail dto,String mess,int modifyBy)
	{
		DTOForumDtl comment = new DTOForumDtl();
		comment.setWorkspaceId(dto.getWorkspaceId());
		comment.setNodeId(dto.getNodeId());
		comment.setReceiverGroupCode(dto.getUserGroupCode());
		comment.setReceiverUserCode(dto.getUserCode());
		comment.setSubjectDesc(mess);
		comment.setForumHdrModifyBy(modifyBy);			
		return insertForumDetails(comment,1);
	}
	
	/**
	 * @param mode
	 * mode=1 -> new comment
	 * mode=2 -> mark comment as read
	 * mode=3 -> mark comment as deleted
	 * mode=4 -> mark comment as unread
	 * */
	public String insertForumDetails(DTOForumDtl comment,int mode)
	{
		String retString="";
		try
		{
			/*
				@vWorkSpaceId VARCHAR(50),          
				@inodeId INT,
				@iUserGroupCode INT,
				@iUserCode INT,
				@Message TEXT,"
				@cStatusIndi CHAR(2),
				@iModifyBy INT,
				@dataopmode INT,
				@cReplyFlag CHAR(1),
				@vRefSubId VARCHAR(50),
				@vFileName VARCHAR(500),
			*/
			Connection connection=ConnectionManager.ds.getConnection();
			CallableStatement cs=connection.prepareCall("{ Call Insert_forumHdr(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setString(1,comment.getWorkspaceId());
			cs.setInt(2,comment.getNodeId());
			cs.setInt(3,comment.getReceiverGroupCode());          
			cs.setInt(4,comment.getReceiverUserCode());  
			cs.setString(5,comment.getSubjectDesc());
			cs.setString(6,"");
			cs.setInt(7,comment.getForumHdrModifyBy());
			cs.setInt(8,mode);
			cs.setString(9,comment.getTypeFlag());
			cs.setString(10,comment.getRefSubjectId());
			cs.setString(11, comment.getFileName());
			cs.setString(12,comment.getUuid());
			cs.setString(13,comment.getSubjectId());
			cs.registerOutParameter(13,java.sql.Types.VARCHAR);
			cs.execute();
			comment.setSubjectId(cs.getString(13));
			cs.close();
			connection.close();
			retString=comment.getSubjectId();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return retString;		
	}	
	
	public void updateForumDetails(String SubjectId,String WorkspaceId,int NodeId,int usercode)
	{
		try
		{
			
			Connection connection=ConnectionManager.ds.getConnection();
			CallableStatement cs=connection.prepareCall("{ Call proc_updateForumHdr(?,?,?,?)}");
			cs.setString(1,SubjectId);
			cs.setString(2,WorkspaceId);
			cs.setInt(3, NodeId);
			cs.setInt(4,usercode);          
			cs.executeUpdate();
			cs.close();
			connection.close();
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
	
	public Vector<DTOForumDtl> showFullCommentHistoryForLambda(
			String workspaceid, int nodeId) {

		Vector<DTOForumDtl> commentHistory = new Vector<DTOForumDtl>();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = "
					+ nodeId + " ";

			rs = dataTable.getDataSet(con, "*", "View_ShowNodeComments",
					whr, "vRefSubjectId,vSubjectId desc");

			while (rs.next()) {
				DTOForumDtl comment = new DTOForumDtl();
				comment.setSenderName(rs.getString("SenderUserName"));
				
				comment.setReceiverName(rs.getString("ReceiverUserName"));
				comment.setSubjectDesc(rs.getString("vSubjectDesc"));
				comment.setWorkspaceId(rs.getString("vWorkSpaceId"));
				comment.setNodeId(rs.getInt("iNodeId"));
				comment.setReceiverUserCode(rs.getInt("ReceiverUserCode"));
				comment.setReceiverGroupCode(rs.getInt("ReceiverUserGroupCode"));
				comment.setSenderUserCode(rs.getInt("SenderUserCode"));
				comment.setSubjectId(rs.getString("vSubjectId"));
				comment.setNodeName(rs.getString("vNodeName"));
				comment.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				comment.setFolderName(rs.getString("vFolderName"));
				comment.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				comment.setForumhdrstatusindi(rs.getString("ForumHdrStatusIndi"));
				comment.setReadFlag(rs.getString("cReadFlag").charAt(0));				
				comment.setCreatedOn(rs.getTimestamp("dCreatedDate"));
				comment.setTypeFlag(rs.getString("cTypeFlag"));
				comment.setRefSubjectId(rs.getString("vRefSubjectId"));
				comment.setForumHdrModifyOn(rs.getTimestamp("ForumHdrModifyOn"));
				comment.setForumHdrModifyBy(rs.getInt("ForumHdrModifyBy"));
				comment.setResolverName(rs.getString("ResolveruserName"));
				comment.setResolverFlag(rs.getString("cResolverFlag"));
				comment.setUuid(rs.getString("vUuid"));
				comment.setUserTypeName(rs.getString("vUserGroupName"));
				commentHistory.add(comment);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return commentHistory;

	}
	public int getCommentsCount(int iReceiverUserCode)
	{
		
		int commentsCount=0;
		Connection connection = null;
		ResultSet rs = null;
		//String where = "ReceiverUserCode="+iReceiverUserCode+" and ForumHdrStatusIndi<>'D' and cTypeFlag<>'R'";
		String where = "(ReceiverUserCode="+iReceiverUserCode+" OR SenderUserCode="+iReceiverUserCode+") and ForumHdrStatusIndi<>'D' and WsStatusIndi<>'A' and cTypeFlag IS null ";
		
		try 
		{
			connection=ConnectionManager.ds.getConnection();
			rs=dataTable.getDataSet(connection, " COUNT(*) AS Count ","View_ShowNodeComments",where,"");			
			while(rs.next())
			{				
				commentsCount=rs.getInt("Count");
			}
		} 
		catch (Exception e) 
		{		
			e.printStackTrace();
		}
		finally{
			try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
			try {if(connection != null){connection.close();}} catch (Exception e) {e.printStackTrace();}
		}
		System.out.println("commentsCount:"+commentsCount);
		return commentsCount;
	}

}//main class end

