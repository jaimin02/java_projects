package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionMst {
	
	ConnectionManager conMgr;
	DataTable datatable;
	public SubmissionMst()
	{
		 conMgr=new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}
	
public void insertSubmissionMst(DTOSubmissionMst dto,int DataMode)
{
	try
	{
		CallableStatement proc = null;
		Connection con = ConnectionManager.ds.getConnection();
		//if(DataMode==1)
		//{
			proc = con.prepareCall("{ Call Insert_SubmissionMst(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1,dto.getWorkspaceId());
			proc.setString(2,dto.getApplicationNo());
			proc.setInt(3,dto.getLabelNo());
			proc.setString(4,dto.getSubmissionpath());
			proc.setInt(5,dto.getSubmittedBy());
			proc.setString(6,dto.getCountrycode());
			proc.setString(7,dto.getApplicant());
			proc.setString(8,dto.getAgencyName());
			proc.setString(9,dto.getAtc());
			proc.setString(10,dto.getProcedureType());
			proc.setString(11,dto.getSubmissionType());
			proc.setString(12,dto.getInventedName());
			proc.setString(13,dto.getInn());
			proc.setString(14,dto.getSubmissionDescription());
			proc.setString(15,dto.getCompanyName());
			proc.setDate(16,dto.getDateOfSubmission());
			proc.setString(17,dto.getProductName());
			proc.setString(18,dto.getProductType());
			proc.setString(19,dto.getApplicationType());
			proc.setString(20,dto.getReletedSeqNo());
			proc.setString(21, dto.getLastPublishedVersion());
			proc.setString(22, dto.getSubmissionId());
			proc.setInt(23,DataMode);
			
				
			/*}
		else
		{
			proc = con.prepareCall("{ Call Insert_SubmissionMst(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setString(1,dto.getWorkspaceId());
			proc.setString(2,dto.getApplicationNo());
			proc.setInt(3,dto.getLabelNo());
			proc.setString(4,"");
			proc.setInt(5,dto.getSubmittedBy());
			proc.setString(6,"");
			proc.setString(7,"");
			proc.setString(8,"");
			proc.setString(9,"");
			proc.setString(10,"");
			proc.setString(11,"");
			proc.setString(12,"");
			proc.setString(13,"");
			proc.setString(14,"");
			proc.setString(15,"");
			proc.setDate(16,null);
			proc.setString(17,"");
			proc.setString(18,"");
			proc.setString(19,"");
			proc.setString(20,"");
			proc.setString(21, dto.getLastPublishedVersion());
			proc.setInt(22,DataMode);
			
		}*/
		proc.execute();
		proc.close();
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
}
	

public Vector getAllSubmissionDetail(String wsId)
{
		
		Vector data = new Vector();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			String fieldNames="vApplicationNo,vWorkSpaceId,vWorkspaceDesc,vAgencyName,vCountryName,vLabelId,iLabelNo,vSubmissionId," +
					"vLastPublishedVersion,vSubmissionDescription,vSubmissionPath,vRelatedSeqNo,cConfirm";
			rs = datatable.getDataSet(con, fieldNames,"view_getSubmissionDetail" , "vWorkSpaceId ='"+wsId+"'", "");
			while(rs.next())
			{
				DTOSubmissionMst dto =new DTOSubmissionMst();
				dto.setSubmissionId(rs.getString("vSubmissionId"));
				dto.setApplicationNo(rs.getString("vApplicationNo"));
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setWorkspaceDesc(rs.getString("vWorkspaceDesc"));
				dto.setAgencyName(rs.getString("vAgencyName"));
				dto.setCountryName(rs.getString("vCountryName"));
				dto.setLabelId(rs.getString("vLabelId"));
				dto.setLabelNo(rs.getInt("iLabelNo"));
				//dto.setSubmissionId(rs.getString("vSubmissionId"));
				char confirmflag = rs.getString("cConfirm").charAt(0);
				dto.setConfirm(rs.getString("cConfirm").charAt(0));
				//for showing +1 in published version
				String subversion=rs.getString("vLastPublishedVersion");
				if(subversion.equals("-999"))
				{
					subversion="1000";
				}else if(!subversion.equals("-999") && confirmflag!='Y'){
					int subversionInt = Integer.parseInt(subversion);
					subversionInt = subversionInt + 1;
					subversion = "100" + subversionInt;
					subversion = subversion.substring(subversion.length()-4,subversion.length());
				}
					
				/*else
				{
					int subversionInt = Integer.parseInt(subversion);
					subversionInt = subversionInt + 1;
					subversion = "000" + subversionInt;
					subversion = subversion.substring(subversion.length()-4,subversion.length());
	  			}*/
                
				dto.setLastPublishedVersion(subversion);
				dto.setSubmissionDescription(rs.getString("vSubmissionDescription"));
				dto.setSubmissionpath(rs.getString("vSubmissionPath"));
				dto.setReletedSeqNo(rs.getString("vRelatedSeqNo"));
				data.addElement(dto);
				dto = null;
			}
			
			rs.close();
			con.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return data;
}
public DTOSubmissionMst getSubmissionDetail(String submissionId)
{
	DTOSubmissionMst dto=new DTOSubmissionMst();
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = datatable.getDataSet(con, "*","view_getSubmissionDetail" ,"vSubmissionId='"+submissionId+"'" , "");
		if(rs.next())
		{
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setApplicationNo(rs.getString("vApplicationNo"));
			dto.setSubmissionId(rs.getString("vSubmissionId"));
			dto.setLabelNo(rs.getInt("iLabelNo"));
			dto.setSubmittedOn(rs.getTimestamp("dSubmitedOn"));
			dto.setSubmittedBy(rs.getInt("iSubmitedBy"));
			dto.setCountrycode(rs.getString("vCountryCode"));
			dto.setApplicant(rs.getString("vApplicant"));
			dto.setAgencyName(rs.getString("vAgencyName"));
			dto.setAtc(rs.getString("vAtc"));
			dto.setProcedureType(rs.getString("vProcedureType"));
			dto.setSubmissionType(rs.getString("vSubmissionType"));
			dto.setInventedName(rs.getString("vInventedName"));
			dto.setInn(rs.getString("vInn"));
			dto.setSubmissionDescription(rs.getString("vSubmissionDescription"));
			dto.setCompanyName(rs.getString("vCompanyName"));
			dto.setDateOfSubmission(rs.getDate("dDateOfSubmission"));
			dto.setSubmissionpath(rs.getString("vSubmissionPath"));
			dto.setLabelId(rs.getString("vLabelId"));
			dto.setCountryName(rs.getString("vCountryName"));
			dto.setWorkspaceDesc(rs.getString("vWorkspaceDesc"));
		
			String subversion=rs.getString("vLastPublishedVersion");
			if(subversion.equals("-999"))
			{
				subversion="0000";
			}
		/*	else
			{
				int subversionInt = Integer.parseInt(subversion);
				subversionInt = subversionInt + 1;
				subversion = "000" + subversionInt;
				subversion = subversion.substring(subversion.length()-4,subversion.length());
  			}
            */
			dto.setLastPublishedVersion(subversion);
			dto.setProductName(rs.getString("vProductName"));
			dto.setProductType(rs.getString("vProductType"));
			dto.setApplicationType(rs.getString("vApplicationType"));
			dto.setReletedSeqNo(rs.getString("vRelatedSeqNo"));
		}

		rs.close();
		con.close();
	}
	catch (SQLException e){
		e.printStackTrace();
	}
	
	return dto;
	
}


public DTOSubmissionMst getOriginalSubmissionDetail(String wsId)
{
	DTOSubmissionMst dto = new DTOSubmissionMst();
	try
	{
		
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = datatable.getDataSet(con, "*", "view_getSubmissionDetail", "vWorkspaceId = '"+wsId+"' and vLastPublishedVersion='0000'", "");
		if(rs.next())
		{
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setApplicationNo(rs.getString("vApplicationNo"));
			dto.setSubmissionId(rs.getString("vSubmissionId"));
			dto.setLabelNo(rs.getInt("iLabelNo"));
			dto.setSubmittedOn(rs.getTimestamp("dSubmitedOn"));
			dto.setSubmittedBy(rs.getInt("iSubmitedBy"));
			dto.setCountrycode(rs.getString("vCountryCode"));
			dto.setApplicant(rs.getString("vApplicant"));
			dto.setAgencyName(rs.getString("vAgencyName"));
			dto.setAtc(rs.getString("vAtc"));
			dto.setProcedureType(rs.getString("vProcedureType"));
			dto.setSubmissionType(rs.getString("vSubmissionType"));
			dto.setInventedName(rs.getString("vInventedName"));
			dto.setInn(rs.getString("vInn"));
			dto.setSubmissionDescription(rs.getString("vSubmissionDescription"));
			dto.setCompanyName(rs.getString("vCompanyName"));
			dto.setDateOfSubmission(rs.getDate("dDateOfSubmission"));
			dto.setSubmissionpath(rs.getString("vSubmissionPath"));
			dto.setLabelId(rs.getString("vLabelId"));
			dto.setCountryName(rs.getString("vCountryName"));
			dto.setWorkspaceDesc(rs.getString("vWorkspaceDesc"));
			dto.setLastPublishedVersion("vLastPublishedVersion");
			dto.setProductName(rs.getString("vProductName"));
			dto.setProductType(rs.getString("vProductType"));
			dto.setApplicationType(rs.getString("vApplicationType"));
			dto.setReletedSeqNo(rs.getString("vRelatedSeqNo"));

		}
		
		rs.close();
		con.close();
		
	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return dto;
}




}//main class ended
