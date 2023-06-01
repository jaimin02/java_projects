package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionInfoDMSDtl;
import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class SubmissionInfoDMSDtl {
	ConnectionManager conMgr;
	DataTable datatable;
	
	public SubmissionInfoDMSDtl(){
		 conMgr=new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}
	
	public ArrayList<DTOWorkSpaceMst> getDMSSubmissionInfo(ArrayList<String> workspaceIdList)
	{
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOWorkSpaceMst> wsMstList = new ArrayList<DTOWorkSpaceMst>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		Connection con = null;
		ResultSet rs = null;
		String selected;
		String whrCond="";
		try
		{
			if(workspaceIdList.size() != 0)
			{
				whrCond ="vWorkspaceId IN ";
				String wsid = workspaceIdList.toString();
				wsid = wsid.replaceAll(" ", "");
				wsid = wsid.replaceAll(",", "','");
				wsid ="('"+ wsid.substring(1, wsid.length()-1)+"')";
				whrCond+= wsid;
			}
			selected ="nSubInfoDMSDtlId,vWorkspaceid,vCurrentSeqNumber,vRelatedSeqNumber,vLabelId,vSubmissionMode,"+
					  "vSubmissionType,vSubmissionDesc,dDateOfSubmission,dSubmittedOn,cConfirm,iConfirmBy,vRemark,"+
					  "iModifyBy,vSubmissionPath,dModifyOn,cStatusIndi,iLabelNo,vConfirmedBy,vModifiedBy";
					  
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, selected,"View_SubmissionInfoDMSDtl" ,whrCond , "vWorkspaceid,vCurrentSeqNumber");
			DTOWorkSpaceMst dtoWsMst= new DTOWorkSpaceMst();
			DTOSubmissionMst dtoSubMst = new DTOSubmissionMst();
			ArrayList<DTOSubmissionInfoDMSDtl> dmsDtlsList= new ArrayList<DTOSubmissionInfoDMSDtl>();
			String oldWorkspaceId="0000";
			String newWorkspaceId=null;
			while(rs.next())
			{		
				newWorkspaceId = rs.getString("vWorkspaceid");
				if(!oldWorkspaceId.equals(newWorkspaceId))
				{
					dtoWsMst = new DTOWorkSpaceMst();
					dtoWsMst.setWorkSpaceId(newWorkspaceId);
					dtoSubMst = new DTOSubmissionMst();	
					dtoWsMst.setSubmissionMst(dtoSubMst);
					dmsDtlsList = new ArrayList<DTOSubmissionInfoDMSDtl>();
					dtoSubMst.setSubmissionInfoDMSDtlsList(dmsDtlsList);
					wsMstList.add(dtoWsMst);
				}
				DTOSubmissionInfoDMSDtl dtoSubInfoDMSDtl = new DTOSubmissionInfoDMSDtl();
				dtoSubInfoDMSDtl.setSubInfoDMSDtlId(rs.getLong("nSubInfoDMSDtlId"));
				dtoSubInfoDMSDtl.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
				dtoSubInfoDMSDtl.setRelatedSeqNumber(rs.getString("vRelatedSeqNumber"));
				dtoSubInfoDMSDtl.setLabelId(rs.getString("vLabelId"));
				dtoSubInfoDMSDtl.setSubmissionMode(rs.getString("vSubmissionMode"));
				dtoSubInfoDMSDtl.setSubmissionType(rs.getString("vSubmissionType"));
				dtoSubInfoDMSDtl.setSubmissionDesc(rs.getString("vSubmissionDesc"));
				dtoSubInfoDMSDtl.setDateOfSubmission(rs.getTimestamp("dDateOfSubmission"));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dtoSubInfoDMSDtl.getDateOfSubmission(),locationName,countryCode);
					dtoSubInfoDMSDtl.setISTDateTime(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(dtoSubInfoDMSDtl.getDateOfSubmission(),locationName,countryCode);
					dtoSubInfoDMSDtl.setISTDateTime(time.get(0));
					dtoSubInfoDMSDtl.setESTDateTime(time.get(1));
				}
				dtoSubInfoDMSDtl.setSubmittedOn(rs.getTimestamp("dSubmittedOn"));
				dtoSubInfoDMSDtl.setConfirm(rs.getString("cConfirm").charAt(0));
				dtoSubInfoDMSDtl.setConfirmBy(rs.getInt("iConfirmBy"));
				dtoSubInfoDMSDtl.setRemark(rs.getString("vRemark"));
				dtoSubInfoDMSDtl.setModifyBy(rs.getInt("iModifyBy"));
				dtoSubInfoDMSDtl.setSubmissionPath(rs.getString("vSubmissionPath"));
				dtoSubInfoDMSDtl.setModifyOn(rs.getTimestamp("dModifyOn"));
				dtoSubInfoDMSDtl.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				dtoSubInfoDMSDtl.setLabelNo(rs.getInt("iLabelNo"));
				dtoSubInfoDMSDtl.setConfirmedBy(rs.getString("vConfirmedBy"));
				dtoSubInfoDMSDtl.setModifiedBy(rs.getString("vModifiedBy"));

				dmsDtlsList.add(dtoSubInfoDMSDtl);

				oldWorkspaceId = newWorkspaceId;
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
		return wsMstList;
	}
	
 public Vector<DTOSubmissionInfoDMSDtl> getDMSSubmissionInfoforarchive(String workspaceId)
 {
		 Vector<DTOSubmissionInfoDMSDtl> data = new Vector<DTOSubmissionInfoDMSDtl>();
		 ResultSet rs = null;
			Connection con = null;
			try {
				con = ConnectionManager.ds.getConnection();

				String Where = "vWorkspaceId = '" + workspaceId + "' and cStatusIndi='L'";
				rs = datatable.getDataSet(con, "*", "SubmissionInfoDMSDtl", Where,
						"");

				while (rs.next()) {
					DTOSubmissionInfoDMSDtl dto = new DTOSubmissionInfoDMSDtl();
					dto.setWorkspaceid(rs.getString("vWorkspaceId"));
					dto.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
					data.addElement(dto);
					dto = null;
				}
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		 return data;
	}	 
	
	public void insertSubmissionInfoDMSDtl(DTOSubmissionInfoDMSDtl dto,int opMode){
		Connection con = null;
		CallableStatement cs = null;
		try 
		{
			con=ConnectionManager.ds.getConnection();
			cs=con.prepareCall("{call INSERT_SUBMISSIONINFODMSDTL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setLong(1,dto.getSubInfoDMSDtlId());
			cs.setString(2, dto.getWorkspaceid());
			cs.setString(3, dto.getCurrentSeqNumber());
			cs.setString(4, dto.getRelatedSeqNumber());
			cs.setString(5, dto.getLabelId());
			cs.setString(6, dto.getSubmissionMode());
			cs.setString(7, dto.getSubmissionType());
			cs.setString(8, dto.getSubmissionDesc());
			cs.setTimestamp(9, dto.getDateOfSubmission());
			cs.setTimestamp(10, dto.getSubmittedOn());
			cs.setString(11, Character.toString(dto.getConfirm()));
			cs.setInt(12, dto.getConfirmBy());
			cs.setString(13, dto.getRemark());
			cs.setInt(14, dto.getModifyBy());
			cs.setString(15, dto.getSubmissionPath());
			cs.setInt(16,opMode); //datamode 1 for insert 2 for confirm the sequence (update)
			cs.execute();
		}   
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
			try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
		}				
	}
	
	public DTOSubmissionInfoDMSDtl getDMSSubmissionInfoBySubId(long subDtlId)
	{
		DTOSubmissionInfoDMSDtl dtoSubInfoDMSDtl = new DTOSubmissionInfoDMSDtl();;
		Connection con = null;
		ResultSet rs = null;
		String selected;
		String whrCond="nSubInfoDMSDtlId ="+subDtlId;
		try
		{
			selected ="nSubInfoDMSDtlId,vWorkspaceid,vCurrentSeqNumber,vRelatedSeqNumber,vLabelId,vSubmissionMode,"+
					  "vSubmissionType,vSubmissionDesc,dDateOfSubmission,dSubmittedOn,cConfirm,iConfirmBy,vRemark,"+
					  "iModifyBy,vSubmissionPath,dModifyOn,cStatusIndi,iLabelNo,vConfirmedBy,vModifiedBy";
					  
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, selected,"View_SubmissionInfoDMSDtl" ,whrCond , "vWorkspaceid,vCurrentSeqNumber");

			if(rs.next())
			{		
				dtoSubInfoDMSDtl.setSubInfoDMSDtlId(rs.getLong("nSubInfoDMSDtlId"));
				dtoSubInfoDMSDtl.setWorkspaceid(rs.getString("vWorkspaceid"));
				dtoSubInfoDMSDtl.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
				dtoSubInfoDMSDtl.setRelatedSeqNumber(rs.getString("vRelatedSeqNumber"));
				dtoSubInfoDMSDtl.setLabelId(rs.getString("vLabelId"));
				dtoSubInfoDMSDtl.setSubmissionMode(rs.getString("vSubmissionMode"));
				dtoSubInfoDMSDtl.setSubmissionType(rs.getString("vSubmissionType"));
				dtoSubInfoDMSDtl.setSubmissionDesc(rs.getString("vSubmissionDesc"));
				dtoSubInfoDMSDtl.setDateOfSubmission(rs.getTimestamp("dDateOfSubmission"));
				dtoSubInfoDMSDtl.setSubmittedOn(rs.getTimestamp("dSubmittedOn"));
				dtoSubInfoDMSDtl.setConfirm(rs.getString("cConfirm").charAt(0));
				dtoSubInfoDMSDtl.setConfirmBy(rs.getInt("iConfirmBy"));
				dtoSubInfoDMSDtl.setRemark(rs.getString("vRemark"));
				dtoSubInfoDMSDtl.setModifyBy(rs.getInt("iModifyBy"));
				dtoSubInfoDMSDtl.setModifyOn(rs.getTimestamp("dModifyOn"));
				dtoSubInfoDMSDtl.setSubmissionPath(rs.getString("vSubmissionPath"));
				dtoSubInfoDMSDtl.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				dtoSubInfoDMSDtl.setLabelNo(rs.getInt("iLabelNo"));
				dtoSubInfoDMSDtl.setConfirmedBy(rs.getString("vConfirmedBy"));
				dtoSubInfoDMSDtl.setModifiedBy(rs.getString("vModifiedBy"));
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
		return dtoSubInfoDMSDtl;
	}
	
	public int getMaxLabelNoForDMSPublish(String wsId,int mode)
	{
		int labelNo=0;
		Connection con = null;
		ResultSet rs = null;
		String selected;
		String whrCond="";
		try
		{
			whrCond = "vworkspaceid = '"+wsId+"'";
			if (mode == 1)
				whrCond+= " and cconfirm = 'Y'";
			//Here mode 1: get confirm project record 
			selected ="max(iLabelNo) as iLabelNo";
					  
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, selected,"View_SubmissionInfoDMSDtl" ,whrCond , "");
			if(rs.next())
			{
				labelNo = rs.getInt("iLabelNo");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally
		{
			if (rs!=null) try {rs.close();} catch (SQLException e) {e.printStackTrace();}
			if (con!=null) try {con.close();} catch (SQLException e) {e.printStackTrace();}			
		}
		return labelNo;
	}
	
	public Vector<DTOSubmissionInfoDMSDtl> getDetailsforPublishPath(String workspaceId)
	{
		Vector<DTOSubmissionInfoDMSDtl> dto = new Vector<DTOSubmissionInfoDMSDtl>();
		
		Connection con = null;
		ResultSet rs = null;
		String fields;
		
		try {
			con = conMgr.ds.getConnection();
			fields = "nSubInfoDMSDtlId,vWorkspaceid,vCurrentSeqNumber,vRelatedSeqNumber,vLabelId,vSubmissionMode,"
					+ "vSubmissionType,vSubmissionDesc,dDateOfSubmission,dSubmittedOn,"
					+ "cConfirm,iConfirmBy,vRemark";
			rs = datatable.getDataSet(con,fields,"View_SubmissionInfoDMSDtl","vWorkspaceId='"+workspaceId+"'" , "nSubInfoDMSDtlId desc");
			
			
			while(rs.next())
			{
				DTOSubmissionInfoDMSDtl dtoSubmissionMst = new DTOSubmissionInfoDMSDtl();
				dtoSubmissionMst.setSubInfoDMSDtlId(rs.getLong("nSubInfoDMSDtlId"));
				dtoSubmissionMst.setWorkspaceid(rs.getString("vWorkspaceId"));	
				dtoSubmissionMst.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
				dtoSubmissionMst.setRelatedSeqNumber(rs.getString("vRelatedSeqNumber"));
				dtoSubmissionMst.setLabelId(rs.getString("vLabelId"));
				dtoSubmissionMst.setSubmissionMode(rs.getString("vSubmissionMode"));
				dtoSubmissionMst.setSubmissionType(rs.getString("vSubmissionType"));
				dtoSubmissionMst.setSubmissionDesc(rs.getString("vSubmissionDesc"));
				dtoSubmissionMst.setDateOfSubmission(rs.getTimestamp("dDateOfSubmission"));
				dtoSubmissionMst.setSubmittedOn(rs.getTimestamp("dSubmittedOn"));
				dtoSubmissionMst.setConfirm('N');
				dtoSubmissionMst.setConfirmBy(rs.getInt("iConfirmBy"));
				dtoSubmissionMst.setRemark(rs.getString("vRemark"));
				dto.addElement(dtoSubmissionMst);
				dtoSubmissionMst=null;
				
			}
			
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dto;

	}
	public int getCountForArchivleSequence(String wsId)
	{
		Connection con = null;
		ResultSet rs = null;
		int count=0;
		
		try {
			con = conMgr.ds.getConnection();
			rs = datatable.getDataSet(con,"Count(*) AS Count","WorkspaceMst","vWorkspaceId='"+wsId+"'"+"and cStatusIndi='L'","");					
			while(rs.next())
			{
				count=rs.getInt("Count");				
			}
			
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}

	  // create by Virendra Barad for Get PDFPublish History
		public Vector<DTOSubmissionInfoDMSDtl> getPublishDetailHistory(String wsId, String subDtlId, String currSeqNo) {
			
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			Vector<DTOSubmissionInfoDMSDtl> publishDetailHistory = new Vector<DTOSubmissionInfoDMSDtl>();
			ResultSet rs = null;
			Connection con = null;
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			try {
				con = ConnectionManager.ds.getConnection();

				String Where = "vWorkspaceId = '" + wsId +"'"+" And nSubInfoDMSDtlId = '" + subDtlId +"'"
							 + " And vCurrentSeqNumber = '" + currSeqNo +"'";
				rs = datatable.getDataSet(con, "*","View_SubmissionInfoDMSDtlHistory", Where,	"iTranNo");

				while (rs.next()) {
					DTOSubmissionInfoDMSDtl dto = new DTOSubmissionInfoDMSDtl();
					dto.setSubInfoDMSDtlId(rs.getLong("nSubInfoDMSDtlId")); // SubInfoDMSDtlId
					dto.setWorkspaceid(rs.getString("vWorkspaceId")); // WorkspaceId
					dto.setWorkspaceDesc(rs.getString("vWorkspaceDesc")); // WorkspaceDesc
					dto.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber")); // CurrentSeqNumber
					dto.setSubmissionMode(rs.getString("vSubmissionMode")); // SubmissionMode
					dto.setSubmissionDesc(rs.getString("vSubmissionDesc")); // SubmissionDesc
					dto.setConfirm(rs.getString("cConfirm").charAt(0)); // Confirm
					dto.setConfirmedBy(rs.getString("vConfirmedBy")); // ConfirmedBy
					dto.setModifiedBy(rs.getString("vModifiedBy")); // ModifiedBy
					dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
					dto.setRemark(rs.getString("vRemark")); // remark
					dto.setSubmissionPath(rs.getString("vSubmissionPath")); // SubmissionPath
					dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
					dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
					publishDetailHistory.addElement(dto);
					dto = null;
				}

				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 

			return publishDetailHistory;	
		}
		
		public Vector<DTOSubmissionInfoDMSDtl> getmaxsubinfoDMSDtlId()
		{
			Vector<DTOSubmissionInfoDMSDtl> dto = new Vector<DTOSubmissionInfoDMSDtl>();
			
			Connection con = null;
			ResultSet rs = null;
			String fields;
			
			try {
				con = conMgr.ds.getConnection();
				fields = "max(nsubinfoDMSDtlId) as nsubinfoDMSDtlId";
				rs = datatable.getDataSet(con,fields,"View_SubmissionInfoDMSDtl","","");
				
				
				while(rs.next())
				{
					DTOSubmissionInfoDMSDtl dtoSubmissionMst = new DTOSubmissionInfoDMSDtl();
					dtoSubmissionMst.setSubInfoDMSDtlId(rs.getLong("nsubinfoDMSDtlId"));
					dto.addElement(dtoSubmissionMst);
					dtoSubmissionMst=null;
					
				}
				
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return dto;

		}
}