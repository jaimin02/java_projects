package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.docmgmt.dto.DTOSubmissionQueryDtl;
import com.docmgmt.dto.DTOSubmissionQueryMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class SubmissionQueryDtl {

	DataTable datatable;
	
	public SubmissionQueryDtl(){
		datatable = new DataTable();
	}
	
	public void insertSubmissionQueryDtl(ArrayList<DTOSubmissionQueryDtl> subQueryDtlList,int opMode){
		Connection con = null;
		CallableStatement cs = null;
		try 
		{
			//System.out.println("DSATA OP MODE ::::::::::::::"+opMode);
			con=ConnectionManager.ds.getConnection();
			cs=con.prepareCall("{call Insert_SubmissionQueryDtl(?,?,?,?,?,?,?,?,?,?,?,?)}");
			for(int i=0;i<subQueryDtlList.size();i++)
			{
				DTOSubmissionQueryDtl dtoSubQueryDtl=subQueryDtlList.get(i);
				//System.out.println(dtoSubQueryDtl.getQueryId()+"/////"+dtoSubQueryDtl.getWorkSpaceId()+"/////"+dtoSubQueryDtl.getSeqNo()+"//////"+dtoSubQueryDtl.getSeqNo());
				cs.setLong(1,dtoSubQueryDtl.getQueryId());
				cs.setString(2, dtoSubQueryDtl.getWorkSpaceId());
				cs.setString(3, dtoSubQueryDtl.getSeqNo());
				cs.setInt(4, dtoSubQueryDtl.getNodeId());
				cs.setInt(5, dtoSubQueryDtl.getTranNo());
				cs.setString(6, Character.toString(dtoSubQueryDtl.getResolved()));
				cs.setInt(7, dtoSubQueryDtl.getResolvedBy());
				cs.setTimestamp(8, dtoSubQueryDtl.getResolvedDate());
				cs.setString(9, dtoSubQueryDtl.getQueryStatus());
				cs.setString(10, dtoSubQueryDtl.getRemark());
				cs.setInt(11, dtoSubQueryDtl.getModifyBy());
				cs.setInt(12,opMode); //datamode 1 for insert or (if available then)update
				cs.execute();
			}	
		}   
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
			try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
		}				
	}

	public ArrayList<DTOSubmissionQueryMst> getWorkspaceQueryDtlsByUser(String workspaceId,long usercode){
		 
		 DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	     ArrayList<DTOSubmissionQueryMst> submissionQueryDetails = new ArrayList<DTOSubmissionQueryMst>();
		 ResultSet rs = null;
		 Connection con = null;
		 ArrayList<String> time = new ArrayList<String>();
		 String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		 String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		 
		 System.out.println("Workspce Query Details by userid");
		 try
			{
				con = ConnectionManager.ds.getConnection();
				StringBuffer query = new StringBuffer();
				if(workspaceId!= null)
					query.append(" vWorkspaceId = '"+workspaceId+"' and iMstModifyBy="+usercode+" or iQueryId in(select  iQueryId from SubmissionQueryUserDtl where iUserCode="+usercode+")  ");
				String selectedFields = "iQueryId, vQueryTitle, dStartDate, dEndDate, vRefDocPath, vQueryDesc, vMstRemark, iMstModifyBy,"+ 
										"dMstModifyOn, cMstStatusIndi, vWorkspaceId, vSeqNo, iNodeId, iTranNo, cResolved, iResolvedBy,"+ 
										"dResolvedDate, vQueryStatus, vDtlRemark, iDtlModifyBy, dDtlModifyOn, cDtlStatusIndi,"+
										"vWorkspaceDesc, vLocationName, vDeptName, vClientName, vTemplateDesc, vWorkspaceRemark,"+
										"vNodeName, vNodeDisplayName, cNodeTypeIndi, vFolderName, vWorkspaceRemark";			
				//query.append(" and imodifyby = "+dto.getModifyBy()+"");
				rs = datatable.getDataSet(con,selectedFields, "view_submissionquery",query.toString(),"iQueryId");
				DTOSubmissionQueryMst dtoSubQueryMst = new DTOSubmissionQueryMst();
				long prevQueryId = 0L;
				boolean nxtRecordAvailable = false;
				if(rs!=null)
					nxtRecordAvailable = rs.next();
				
				while(nxtRecordAvailable)
				{
					dtoSubQueryMst = new DTOSubmissionQueryMst();
					dtoSubQueryMst.setQueryId(rs.getLong("iQueryId"));
					dtoSubQueryMst.setQueryTitle(rs.getString("vQueryTitle"));
					dtoSubQueryMst.setQueryDesc(rs.getString("vQueryDesc"));
					dtoSubQueryMst.setStartDate(rs.getTimestamp("dStartDate"));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dtoSubQueryMst.getStartDate(),locationName,countryCode);
					dtoSubQueryMst.setISTDateTime(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(dtoSubQueryMst.getStartDate(),locationName,countryCode);
					dtoSubQueryMst.setISTDateTime(time.get(0));
					dtoSubQueryMst.setESTDateTime(time.get(1));
				}
					dtoSubQueryMst.setEndDate(rs.getTimestamp("dEndDate"));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dtoSubQueryMst.getEndDate(),locationName,countryCode);
					dtoSubQueryMst.setEndISTDateTime(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(dtoSubQueryMst.getEndDate(),locationName,countryCode);
					dtoSubQueryMst.setEndISTDateTime(time.get(0));
					dtoSubQueryMst.setEndESTDateTime(time.get(1));
				}
					dtoSubQueryMst.setRefDoc(rs.getString("vRefDocPath"));
					dtoSubQueryMst.setModifyBy(rs.getInt("iMstModifyBy"));
					dtoSubQueryMst.setModifyOn(rs.getTimestamp("dMstModifyOn"));
					prevQueryId = dtoSubQueryMst.getQueryId();
					ArrayList<DTOSubmissionQueryDtl> submissionQueryDtlList = new ArrayList<DTOSubmissionQueryDtl>();
					do {
						DTOSubmissionQueryDtl dtoSubQueryDtl = new DTOSubmissionQueryDtl();
						
						dtoSubQueryDtl.setWorkSpaceId(rs.getString("vWorkspaceId"));
						dtoSubQueryDtl.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
						dtoSubQueryDtl.setSeqNo(rs.getString("vSeqNo"));
						dtoSubQueryDtl.setNodeId(rs.getInt("iNodeId"));
						dtoSubQueryDtl.setTranNo(rs.getInt("iTranNo"));
						dtoSubQueryDtl.setResolved(rs.getString("cResolved").charAt(0));
						dtoSubQueryDtl.setResolvedBy(rs.getInt("iResolvedBy"));
						dtoSubQueryDtl.setResolvedDate(rs.getTimestamp("dResolvedDate"));
						dtoSubQueryDtl.setQueryStatus(rs.getString("vQueryStatus"));
						dtoSubQueryDtl.setLocationName(rs.getString("vLocationName"));
						dtoSubQueryDtl.setClientName(rs.getString("vClientName"));
						dtoSubQueryDtl.setDeptName(rs.getString("vDeptName"));
						dtoSubQueryDtl.setTemplateDesc(rs.getString("vTemplateDesc"));
						dtoSubQueryDtl.setWorkspaceRemark(rs.getString("vWorkspaceRemark"));
						dtoSubQueryDtl.setNodeName(rs.getString("vNodeName"));
						dtoSubQueryDtl.setNodeDisplayName(rs.getString("vNodeDisplayName"));
						dtoSubQueryDtl.setFolderName(rs.getString("vFolderName"));
						dtoSubQueryDtl.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
						dtoSubQueryDtl.setModifyOn(rs.getTimestamp("dDtlModifyOn"));
						dtoSubQueryDtl.setModifyBy(rs.getInt("iDtlModifyBy"));
						
						submissionQueryDtlList.add(dtoSubQueryDtl);
						
						nxtRecordAvailable = rs.next();
					} while (nxtRecordAvailable && prevQueryId == rs.getLong("iQueryId"));
					dtoSubQueryMst.setSubmissionQueryDtls(submissionQueryDtlList);
					submissionQueryDetails.add(dtoSubQueryMst);
					if(!nxtRecordAvailable)
					{
						break;
					}
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
				try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
			}
		 return submissionQueryDetails;
	 }
	 
	
	 public ArrayList<DTOSubmissionQueryMst> getWorkspaceQueryDtls(String workspaceId){
		 ArrayList<DTOSubmissionQueryMst> submissionQueryDetails = new ArrayList<DTOSubmissionQueryMst>();
		 ResultSet rs = null;
		 Connection con = null;
		 try
			{
				con = ConnectionManager.ds.getConnection();
				StringBuffer query = new StringBuffer();
				if(workspaceId!= null)
					query.append(" vWorkspaceId = '"+workspaceId+"'");
				String selectedFields = "iQueryId, vQueryTitle, dStartDate, dEndDate, vRefDocPath, vQueryDesc, vMstRemark, iMstModifyBy,"+ 
										"dMstModifyOn, cMstStatusIndi, vWorkspaceId, vSeqNo, iNodeId, iTranNo, cResolved, iResolvedBy,"+ 
										"dResolvedDate, vQueryStatus, vDtlRemark, iDtlModifyBy, dDtlModifyOn, cDtlStatusIndi,"+
										"vWorkspaceDesc, vLocationName, vDeptName, vClientName, vTemplateDesc, vWorkspaceRemark,"+
										"vNodeName, vNodeDisplayName, cNodeTypeIndi, vFolderName, vWorkspaceRemark";			
				//query.append(" and imodifyby = "+dto.getModifyBy()+"");
				rs = datatable.getDataSet(con,selectedFields, "view_submissionquery",query.toString(),"iQueryId");
				DTOSubmissionQueryMst dtoSubQueryMst = new DTOSubmissionQueryMst();
				long prevQueryId = 0L;boolean nxtRecordAvailable = false;
				nxtRecordAvailable = rs.next();
				
				while(nxtRecordAvailable)
				{
					dtoSubQueryMst = new DTOSubmissionQueryMst();
					dtoSubQueryMst.setQueryId(rs.getLong("iQueryId"));
					dtoSubQueryMst.setQueryTitle(rs.getString("vQueryTitle"));
					dtoSubQueryMst.setQueryDesc(rs.getString("vQueryDesc"));
					dtoSubQueryMst.setStartDate(rs.getTimestamp("dStartDate"));
					dtoSubQueryMst.setEndDate(rs.getTimestamp("dEndDate"));
					dtoSubQueryMst.setRefDoc(rs.getString("vRefDocPath"));
					dtoSubQueryMst.setModifyBy(rs.getInt("iMstModifyBy"));
					dtoSubQueryMst.setModifyOn(rs.getTimestamp("dMstModifyOn"));
					prevQueryId = dtoSubQueryMst.getQueryId();
					ArrayList<DTOSubmissionQueryDtl> submissionQueryDtlList = new ArrayList<DTOSubmissionQueryDtl>();
					do {
						DTOSubmissionQueryDtl dtoSubQueryDtl = new DTOSubmissionQueryDtl();
						
						dtoSubQueryDtl.setWorkSpaceId(rs.getString("vWorkspaceId"));
						dtoSubQueryDtl.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
						dtoSubQueryDtl.setSeqNo(rs.getString("vSeqNo"));
						dtoSubQueryDtl.setNodeId(rs.getInt("iNodeId"));
						dtoSubQueryDtl.setTranNo(rs.getInt("iTranNo"));
						dtoSubQueryDtl.setResolved(rs.getString("cResolved").charAt(0));
						dtoSubQueryDtl.setResolvedBy(rs.getInt("iResolvedBy"));
						dtoSubQueryDtl.setResolvedDate(rs.getTimestamp("dResolvedDate"));
						dtoSubQueryDtl.setQueryStatus(rs.getString("vQueryStatus"));
						dtoSubQueryDtl.setLocationName(rs.getString("vLocationName"));
						dtoSubQueryDtl.setClientName(rs.getString("vClientName"));
						dtoSubQueryDtl.setDeptName(rs.getString("vDeptName"));
						dtoSubQueryDtl.setTemplateDesc(rs.getString("vTemplateDesc"));
						dtoSubQueryDtl.setWorkspaceRemark(rs.getString("vWorkspaceRemark"));
						dtoSubQueryDtl.setNodeName(rs.getString("vNodeName"));
						dtoSubQueryDtl.setNodeDisplayName(rs.getString("vNodeDisplayName"));
						dtoSubQueryDtl.setFolderName(rs.getString("vFolderName"));
						dtoSubQueryDtl.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
						dtoSubQueryDtl.setModifyOn(rs.getTimestamp("dDtlModifyOn"));
						dtoSubQueryDtl.setModifyBy(rs.getInt("iDtlModifyBy"));
						
						submissionQueryDtlList.add(dtoSubQueryDtl);
						
						nxtRecordAvailable = rs.next();
					} while (nxtRecordAvailable && prevQueryId == rs.getLong("iQueryId"));
					dtoSubQueryMst.setSubmissionQueryDtls(submissionQueryDtlList);
					submissionQueryDetails.add(dtoSubQueryMst);
					if(!nxtRecordAvailable)
					{
						break;
					}
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
				try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
			}
		 return submissionQueryDetails;
	 }
	 
	 public DTOSubmissionQueryMst getWorkspaceQueryDtlsByQueryId(long queryId){
		 DTOSubmissionQueryMst dtoSubQueryMst = new DTOSubmissionQueryMst();
		 ResultSet rs = null;
		 Connection con = null;
		 try
			{
				con = ConnectionManager.ds.getConnection();
				StringBuffer query = new StringBuffer();
				query.append(" iQueryId = '"+queryId+"'");
				String selectedFields = "iQueryId, vQueryTitle, dStartDate, dEndDate, vRefDocPath, vQueryDesc, vMstRemark, iMstModifyBy,"+ 
										"dMstModifyOn, cMstStatusIndi, vWorkspaceId, vSeqNo, iNodeId, iTranNo, cResolved, iResolvedBy,"+ 
										"dResolvedDate, vQueryStatus, vDtlRemark, iDtlModifyBy, dDtlModifyOn, cDtlStatusIndi,"+
										"vWorkspaceDesc, vLocationName, vDeptName, vClientName, vTemplateDesc, vWorkspaceRemark,"+
										"vNodeName, vNodeDisplayName, cNodeTypeIndi, vFolderName, vWorkspaceRemark";			
				//query.append(" and imodifyby = "+dto.getModifyBy()+"");
				rs = datatable.getDataSet(con,selectedFields, "view_submissionquery",query.toString(),"iQueryId");
				long prevQueryId = 0L;boolean nxtRecordAvailable = false;
				nxtRecordAvailable = rs.next();
				while(nxtRecordAvailable)
				{
					dtoSubQueryMst = new DTOSubmissionQueryMst();
					dtoSubQueryMst.setQueryId(rs.getLong("iQueryId"));
					dtoSubQueryMst.setQueryTitle(rs.getString("vQueryTitle"));
					dtoSubQueryMst.setQueryDesc(rs.getString("vQueryDesc"));
					dtoSubQueryMst.setStartDate(rs.getTimestamp("dStartDate"));
					dtoSubQueryMst.setEndDate(rs.getTimestamp("dEndDate"));
					dtoSubQueryMst.setRefDoc(rs.getString("vRefDocPath"));
					dtoSubQueryMst.setModifyBy(rs.getInt("iMstModifyBy"));
					dtoSubQueryMst.setModifyOn(rs.getTimestamp("dMstModifyOn"));
					prevQueryId = dtoSubQueryMst.getQueryId();
					ArrayList<DTOSubmissionQueryDtl> submissionQueryDtlList = new ArrayList<DTOSubmissionQueryDtl>();
					do {
						DTOSubmissionQueryDtl dtoSubQueryDtl = new DTOSubmissionQueryDtl();
						dtoSubQueryDtl.setQueryId(rs.getLong("iQueryId"));
						dtoSubQueryDtl.setWorkSpaceId(rs.getString("vWorkspaceId"));
						dtoSubQueryDtl.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
						dtoSubQueryDtl.setSeqNo(rs.getString("vSeqNo"));
						dtoSubQueryDtl.setNodeId(rs.getInt("iNodeId"));
						dtoSubQueryDtl.setTranNo(rs.getInt("iTranNo"));
						dtoSubQueryDtl.setResolved(rs.getString("cResolved").charAt(0));
						dtoSubQueryDtl.setResolvedBy(rs.getInt("iResolvedBy"));
						dtoSubQueryDtl.setResolvedDate(rs.getTimestamp("dResolvedDate"));
						dtoSubQueryDtl.setQueryStatus(rs.getString("vQueryStatus"));
						dtoSubQueryDtl.setLocationName(rs.getString("vLocationName"));
						dtoSubQueryDtl.setClientName(rs.getString("vClientName"));
						dtoSubQueryDtl.setDeptName(rs.getString("vDeptName"));
						dtoSubQueryDtl.setTemplateDesc(rs.getString("vTemplateDesc"));
						dtoSubQueryDtl.setWorkspaceRemark(rs.getString("vWorkspaceRemark"));
						dtoSubQueryDtl.setNodeName(rs.getString("vNodeName"));
						dtoSubQueryDtl.setNodeDisplayName(rs.getString("vNodeDisplayName"));
						dtoSubQueryDtl.setFolderName(rs.getString("vFolderName"));
						dtoSubQueryDtl.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
						dtoSubQueryDtl.setModifyOn(rs.getTimestamp("dDtlModifyOn"));
						dtoSubQueryDtl.setModifyBy(rs.getInt("iDtlModifyBy"));
						
						submissionQueryDtlList.add(dtoSubQueryDtl);
						
						nxtRecordAvailable = rs.next();
					} while (nxtRecordAvailable && prevQueryId == rs.getLong("iQueryId"));
					dtoSubQueryMst.setSubmissionQueryDtls(submissionQueryDtlList);
					if(!nxtRecordAvailable)
					{
						break;
					}
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
				try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
			}
		 return dtoSubQueryMst;
	 }
	 
	 public ArrayList<String> getWorkspaceIdForSubmittedQuery(){
		 ArrayList<String> wsIdList = new ArrayList<String>();
		 ResultSet rs = null;
		 Connection con = null;
		 try
			{
				con = ConnectionManager.ds.getConnection();
				String selectedFields = "distinct vWorkspaceId";			
				
				rs = datatable.getDataSet(con,selectedFields, "view_submissionquery","","");
				while(rs.next())
				{
					wsIdList.add(rs.getString("vWorkspaceId"));
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
				try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
			}
		 return wsIdList;
	 }
}