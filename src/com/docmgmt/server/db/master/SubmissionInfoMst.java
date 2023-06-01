package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionInfoMst {
	
	ConnectionManager conMgr;
	DataTable datatable;
	public SubmissionInfoMst()
	{
		 conMgr=new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}


public void insertSubmissionInfoEUMst(DTOSubmissionMst dto)
{
	try
	{
		CallableStatement proc = null;
		Connection con = conMgr.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoEUMst(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1,dto.getWorkspaceId());
			proc.setString(2,dto.getApplicationNo());
			proc.setString(3,dto.getCountrycode());
			proc.setString(4,dto.getApplicant());
			proc.setString(5,dto.getAgencyName());
			proc.setString(6,dto.getAtc());
			proc.setString(7,dto.getProcedureType());
			proc.setString(8,dto.getInventedName());
			proc.setString(9,dto.getInn());
			proc.setString(10,dto.getSubmissionDescription());
			proc.setInt(11,dto.getSubmittedBy());
			proc.setString(12,dto.getTrackingNo());
			proc.setString(13,dto.getHighLvlNo());
			proc.setString(14,dto.getUuid());
			proc.setString(15,dto.getRegionalDTDVersion());
			proc.execute();
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
}

public void insertSubmissionInfoEU14Mst(DTOSubmissionMst dto)
{
	try
	{
		CallableStatement proc = null;
		Connection con = conMgr.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoEU14Mst(?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1,dto.getWorkspaceId());
			proc.setString(2,dto.getTrackingNo());
			proc.setString(3,dto.getHighLvlNo());
			proc.setString(4,dto.getCountrycode());
			proc.setString(5,dto.getApplicant());
			proc.setString(6,dto.getAgencyName());
			proc.setString(7,dto.getProcedureType());
			proc.setString(8,dto.getInventedName());
			proc.setString(9,dto.getInn());
			proc.setInt(10,dto.getSubmittedBy());
			proc.setString(11,dto.getUuid());
			proc.setString(12,dto.getRegionalDTDVersion());
			proc.execute();
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
}

public void insertSubmissionInfoCHMst(DTOSubmissionMst dto)
{
	try
	{
		CallableStatement proc = null;
		Connection con = conMgr.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoCHMst(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1,dto.getWorkspaceId());
			proc.setString(2,dto.getApplicationNo());
			proc.setString(3,dto.getCountrycode());
			proc.setString(4,dto.getApplicant());
			proc.setString(5,dto.getAgencyName());
			proc.setString(6,dto.getInventedName());
			proc.setString(7,dto.getInn());
			proc.setString(8,dto.getDmfNumber());
			proc.setString(9,dto.getDmfHolder());
			proc.setString(10,dto.getPmfNumber());
			proc.setString(11,dto.getPmfHolder());
			proc.setString(12,dto.getSubmissionDescription());
			proc.setInt(13,dto.getSubmittedBy());
			proc.execute();
			proc.close();
			
		con.close();
	}catch(SQLException e){
		e.printStackTrace();
	}
}
public void insertSubmissionInfoEU20Mst(DTOSubmissionMst dto)
{
	try
	{
		CallableStatement proc = null;
		Connection con = conMgr.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoEU20Mst(?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1,dto.getWorkspaceId());
			proc.setString(2,dto.getTrackingNo());
			proc.setString(3,dto.getHighLvlNo());
			proc.setString(4,dto.getCountrycode());
			proc.setString(5,dto.getApplicant());
			proc.setString(6,dto.getAgencyName());
			proc.setString(7,dto.getProcedureType());
			proc.setString(8,dto.getInventedName());
			proc.setString(9,dto.getInn());
			proc.setInt(10,dto.getSubmittedBy());
			proc.setString(11,dto.getUuid());
			proc.setString(12,dto.getRegionalDTDVersion());
			proc.execute();
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
}
public void insertSubmissionInfoAUMst(DTOSubmissionMst dto)
{
	try
	{
		CallableStatement proc = null;
		Connection con = conMgr.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoAUMst(?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1,dto.getWorkspaceId());
			proc.setString(2,dto.geteSubmissionId());
			proc.setString(3,dto.getCountrycode());
			proc.setString(4,dto.getApplicant());
			proc.setString(5,dto.getAgencyName());
			proc.setString(6,dto.getAAN());
			proc.setString(7,dto.getProductName());
			proc.setString(8,dto.getRegActLead());
			proc.setInt(9,dto.getSubmittedBy());
			proc.execute();
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
}
public void insertSubmissionInfoTHMst(DTOSubmissionMst dto)
{
	try
	{
		CallableStatement proc = null;
		Connection con = conMgr.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoTHMst(?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1,dto.getWorkspaceId());
			proc.setString(2,dto.geteSubmissionId());
			proc.setString(3,dto.getCountrycode());
			proc.setString(4,dto.getLicensee());
			proc.setString(5,dto.getAgencyName());
			proc.setString(6,dto.getInn());
			proc.setString(7,dto.getProductName());
			proc.setString(8,dto.getRegActLead());
			proc.setInt(9,dto.getSubmittedBy());
			proc.setString(10,dto.getLicenseeName());
			proc.setString(11,dto.getLicenseeType());
			proc.execute();
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
}
//method added on 29/03/2013 for gcc region
public void insertSubmissionInfoGCCMst(DTOSubmissionMst dto)
{
	

	try
	{
		CallableStatement proc = null;
		Connection con = conMgr.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoGCCMst(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1,dto.getWorkspaceId());
			proc.setString(2,dto.getTrackingNo());
			proc.setString(3,dto.getAtc());
			proc.setString(4,dto.getHighLvlNo());
			proc.setString(5,dto.getCountrycode());
			proc.setString(6,dto.getApplicant());
			proc.setString(7,dto.getAgencyName());
			proc.setString(8,dto.getProcedureType());
			proc.setString(9,dto.getInventedName());
			proc.setString(10,dto.getInn());
			proc.setString(11,dto.getSubmissionDescription());
			proc.setInt(12,dto.getSubmittedBy());
			proc.setString(13,dto.getRegionalDTDVersion());
			proc.execute();
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}

		
}
//method added on 25/06/2013 for ZA region
public void insertSubmissionInfoZAMst(DTOSubmissionMst dto)
{
	

	try
	{
		
		
		CallableStatement proc = null;
		Connection con = conMgr.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoZAMst(?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1,dto.getWorkspaceId());
			proc.setString(2,dto.getApplicationNo());
			proc.setString(3,dto.getDosageForm());
			proc.setString(4,dto.getCountrycode());
			proc.setString(5,dto.getApplicant());
			proc.setString(6,dto.getAgencyName());
			proc.setString(7,"established");
			proc.setString(8,dto.getPropriateryName());
			proc.setString(9,dto.getInn());
		
			proc.setString(10,dto.getSubmissionDescription());
			proc.setInt(11,dto.getSubmittedBy());
			proc.setString(12,dto.getRegionalDTDVersion());
			proc.execute();
			proc.close();
			
		con.close();
			
			
	}catch(SQLException e){
		e.printStackTrace();
	}
	
		
}

public void insertSubmissionInfoUSMst(DTOSubmissionMst dto)
{
	try
	{
		CallableStatement proc = null;
		Connection con = conMgr.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoUSMst(?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1,dto.getWorkspaceId());
			proc.setString(2,dto.getApplicationNo());
			proc.setString(3,dto.getCountrycode());
			proc.setString(4,dto.getAgencyName());
			proc.setString(5,dto.getCompanyName());
			proc.setDate(6,dto.getDateOfSubmission());
			proc.setString(7,dto.getProductName());
			proc.setString(8,dto.getProductType());
			proc.setString(9,dto.getApplicationType());
			proc.setInt(10,dto.getSubmittedBy());
			proc.execute();
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
}


public void insertSubmissionInfoUS23Mst(DTOSubmissionMst dto)
{
	try
	{
		CallableStatement proc = null;
		Connection con = conMgr.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoUS23Mst(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1,dto.getWorkspaceId());
			proc.setString(2,dto.getApplicationNo());
			proc.setString(3,dto.getCountrycode());
			proc.setString(4,dto.getAgencyName());
			proc.setString(5,dto.getCompanyName());
			proc.setDate(6,dto.getDateOfSubmission());
			proc.setString(7,dto.getProductName());
			proc.setString(8,dto.getProductType());
			proc.setString(9,dto.getApplicationType());
			proc.setString(10,dto.getApplicationId());
			proc.setString(11,dto.getSubmissionId());
			proc.setString(12,dto.getIsGroupSubmission());
			proc.setString(13,dto.getSubmissionDescription());
			proc.setInt(14,dto.getSubmittedBy());
			proc.execute();
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
}


public void insertSubmissionInfoCAMst(DTOSubmissionMst dto)
{
	try
	{
		CallableStatement proc = null;
		Connection con = conMgr.ds.getConnection();
		
			proc = con.prepareCall("{ Call Insert_SubmissionInfoCAMst(?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1,dto.getWorkspaceId());
			proc.setString(2,dto.getDossierIdentifier());
			proc.setString(3,dto.getCountrycode());
			proc.setString(4,dto.getAgencyName());
			proc.setString(5,dto.getApplicant());
			proc.setString(6,dto.getProductName());
			proc.setString(7,dto.getDossierType());
			proc.setString(8,dto.getRegActLead());
			proc.setInt(9,dto.getSubmittedBy());
			proc.execute();
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
}
public Vector<DTOSubmissionMst> getAllWorkspaceSubmissionInfo(int userId)
{
		
		Vector<DTOSubmissionMst> data = new Vector<DTOSubmissionMst>();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			String whr = " iUserCode = "+userId+" ";
			rs = datatable.getDataSet(con,"*","view_AllWorkspaceSubmissionInfo" , whr, " vWorkSpaceDesc ");
			while(rs.next())
			{
				DTOSubmissionMst dto = new DTOSubmissionMst();
				dto.setWorkspaceId(rs.getString("vWorkSpaceId"));
				dto.setApplicationNo(rs.getString("vApplicationNo"));
				dto.setCountrycode(rs.getString("vCountryCode"));
				dto.setApplicant(rs.getString("vApplicant"));
				dto.setAgencyName(rs.getString("vAgencyName"));
				dto.setAtc(rs.getString("vAtc"));
				dto.setProcedureType(rs.getString("vProcedureType"));
				dto.setInventedName(rs.getString("vInventedName"));
				dto.setInn(rs.getString("vInn"));
				dto.setSubmissionDescription(rs.getString("vSubmissionDescription"));
				dto.setCompanyName(rs.getString("vCompanyName"));
				dto.setDateOfSubmission(rs.getDate("dDateOfSubmission"));
				dto.setProductName(rs.getString("vProductName"));
				dto.setProductType(rs.getString("vProductType"));
				dto.setApplicationType(rs.getString("vApplicationType"));
				dto.setCountryName(rs.getString("vCountryName"));
				dto.setWorkspaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setLastPublishedVersion(rs.getString("vLastPublishedVersion"));
				dto.setCountryRegion(rs.getString("CountryRegion"));
				dto.setCountryCodeName(rs.getString("CountryCodeName"));
				dto.setSubmittedBy(rs.getInt("iModifyBy"));
				dto.setSubmittedOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				dto.setTrackingNo(rs.getString("vTrackingNo"));
				dto.setHighLvlNo(rs.getString("vHighLvlNo"));
				dto.setRegionalDTDVersion(rs.getString("vRegionalDTDVersion"));
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

public DTOSubmissionMst getSubmissionInfo(String workspaceId)
{
	DTOSubmissionMst dto=new DTOSubmissionMst();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = conMgr.ds.getConnection();
		rs = datatable.getDataSet(con, "*","view_AllWorkspaceSubmissionInfo" ,"vWorkspaceId='"+workspaceId+"'" , "vRegionalDTDVersion desc");
		if(rs.next())
		{
			dto.setWorkspaceId(rs.getString("vWorkSpaceId"));
			dto.setApplicationNo(rs.getString("vApplicationNo"));
			dto.setCountrycode(rs.getString("vCountryCode"));
			dto.setApplicant(rs.getString("vApplicant"));
			dto.setAgencyName(rs.getString("vAgencyName"));
			dto.setAtc(rs.getString("vAtc"));
			dto.setProcedureType(rs.getString("vProcedureType"));
			dto.setInventedName(rs.getString("vInventedName"));
			dto.setInn(rs.getString("vInn"));
			dto.setSubmissionDescription(rs.getString("vSubmissionDescription"));
			dto.setCompanyName(rs.getString("vCompanyName"));
			dto.setDateOfSubmission(rs.getDate("dDateOfSubmission"));
			dto.setProductName(rs.getString("vProductName"));
			dto.setProductType(rs.getString("vProductType"));
			dto.setApplicationType(rs.getString("vApplicationType"));
			dto.setCountryName(rs.getString("vCountryName"));
			dto.setWorkspaceDesc(rs.getString("vWorkSpaceDesc"));
			dto.setLastPublishedVersion(rs.getString("vLastPublishedVersion"));
			dto.setCountryRegion(rs.getString("CountryRegion"));
			dto.setCountryCodeName(rs.getString("CountryCodeName"));
			dto.setSubmittedBy(rs.getInt("iModifyBy"));
			dto.setSubmittedOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			dto.setTrackingNo(rs.getString("vTrackingNo"));
			dto.setUuid(rs.getString("vUUID"));
			dto.setHighLvlNo(rs.getString("vHighLvlNo"));
			dto.setRegionalDTDVersion(rs.getString("vRegionalDTDVersion"));
			dto.setDosageForm(rs.getString("vDosageForm"));
			dto.setDmfHolder(rs.getString("vDMFHolder"));
			dto.setPmfHolder(rs.getString("vPMFHolder"));
			dto.setDmfNumber(rs.getString("vDMFNumber"));
			dto.setPmfNumber(rs.getString("vPMFNumber"));
			dto.setApplicationId(rs.getString("vApplicationId"));
			dto.setSubmissionId(rs.getString("vSubmissionId"));
			dto.setIsGroupSubmission(rs.getString("isGroupSubmission"));
			dto.seteSubmissionId(rs.getString("veSubmissionId"));
			dto.setAAN(rs.getString("vAAN"));
			dto.setRegActLead(rs.getString("vRegActLead"));	
			dto.setLicensee(rs.getString("vLicensee"));	
			dto.setDossierIdentifier(rs.getString("vDossierIdentifier"));
			dto.setDossierType(rs.getString("vDossierType"));
			dto.setLicenseeName(rs.getString("vLicenseeName"));
			dto.setLicenseeType(rs.getString("vLicenseeType"));
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
	return dto;
	
}

public DTOSubmissionMst getSubmissionInfoGCC(String workspaceId)
{
	DTOSubmissionMst dto=new DTOSubmissionMst();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = conMgr.ds.getConnection();
		rs = datatable.getDataSet(con, "*","view_getsubmissionInfoGCCMst" ,"vWorkspaceId='"+workspaceId+"'" , "");
		if(rs.next())
		{
			
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setApplicationNo(rs.getString("vTrackingNo"));
			dto.setAtc(rs.getString("vATC"));
			dto.setHighLvlNo(rs.getString("vHighLvlNo"));
			dto.setCountrycode(rs.getString("vCountryCode"));
			dto.setApplicant(rs.getString("vApplicant"));
			dto.setAgencyName(rs.getString("vAgencyName"));
			dto.setProcedureType(rs.getString("vProcedureType"));
			dto.setInventedName(rs.getString("vInventedName"));
			dto.setInn(rs.getString("vInn"));
			dto.setSubmissionDescription(rs.getString("vSubmissionDescription"));
			
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
	return dto;
	
}

public ArrayList<DTOSubmissionMst> getSubmissionInfoForWorkspace(ArrayList<String> wsIdsList)
{
	ArrayList<DTOSubmissionMst> dtoSubmissionMstList= new ArrayList<DTOSubmissionMst>();
	ResultSet rs = null;
	Connection con = null;
	try
	{
		//here we check that workspaceid list is null or not.
		//if not null then convertion it into where condition
		//else where condition will be blank
		String whrCond =  "";
		if(wsIdsList.size() != 0)
		{
			whrCond ="vWorkspaceId IN ";
			String wsid = wsIdsList.toString();
			wsid = wsid.replaceAll(" ", "");
			wsid = wsid.replaceAll(",", "','");
			wsid ="('"+ wsid.substring(1, wsid.length()-1)+"')";
			whrCond+= wsid;
		}
		con = conMgr.ds.getConnection();
		//System.out.println("where condition size:::"+whrCond.length());
		String selectItem = "DISTINCT vWorkspaceId,vApplicationNo,vCountryCode,vAgencyName,vCompanyName,dDateOfSubmission,vProductName,vProductType," +
							"vApplicationType,vApplicant,vAtc,vProcedureType,vInventedName,vInn,vSubmissionDescription,iModifyBy,dModifyOn,cStatusIndi," +
							"vCountryName,CountryRegion,CountryCodeName,vWorkspaceDesc,vLastPublishedVersion,vTrackingNo,vHighLvlNo,vDossierIdentifier,veSubmissionId,vRegionalDTDVersion";
		rs = datatable.getDataSet(con, selectItem,"view_AllWorkspaceSubmissionInfo" , whrCond, "");
		
		while(rs.next())
		{
			DTOSubmissionMst dto = new DTOSubmissionMst();
			dto.setWorkspaceId(rs.getString("vWorkSpaceId"));
			dto.setApplicationNo(rs.getString("vApplicationNo"));
			dto.setCountrycode(rs.getString("vCountryCode"));
			dto.setApplicant(rs.getString("vApplicant"));
			dto.setAgencyName(rs.getString("vAgencyName"));
			dto.setAtc(rs.getString("vAtc"));
			dto.setProcedureType(rs.getString("vProcedureType"));
			dto.setInventedName(rs.getString("vInventedName"));
			dto.setInn(rs.getString("vInn"));
			dto.setSubmissionDescription(rs.getString("vSubmissionDescription"));
			dto.setCompanyName(rs.getString("vCompanyName"));
			dto.setDateOfSubmission(rs.getDate("dDateOfSubmission"));
			dto.setProductName(rs.getString("vProductName"));
			dto.setProductType(rs.getString("vProductType"));
			dto.setApplicationType(rs.getString("vApplicationType"));
			dto.setCountryName(rs.getString("vCountryName"));
			dto.setWorkspaceDesc(rs.getString("vWorkSpaceDesc"));
			dto.setLastPublishedVersion(rs.getString("vLastPublishedVersion"));
			dto.setCountryRegion(rs.getString("CountryRegion"));
			dto.setCountryCodeName(rs.getString("CountryCodeName"));
			dto.setSubmittedBy(rs.getInt("iModifyBy"));
			dto.setSubmittedOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			dto.setTrackingNo(rs.getString("vTrackingNo"));
			dto.setHighLvlNo(rs.getString("vHighLvlNo"));
			dto.setDossierIdentifier(rs.getString("vDossierIdentifier"));
			dto.seteSubmissionId(rs.getString("veSubmissionId"));
			dto.setRegionalDTDVersion(rs.getString("vRegionalDTDVersion"));
			dtoSubmissionMstList.add(dto);		
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
	return dtoSubmissionMstList;
}
public void updateSubmissionInfoForEU(String workspaceId,String reginoalDTDVersion)
{
	try
	{
		CallableStatement proc = null;
		Connection con = conMgr.ds.getConnection();
		
			proc = con.prepareCall("{ Call Proc_UpdateSubmissionInfoForEU(?,?)}");
			
			proc.setString(1,workspaceId);
			proc.setString(2,reginoalDTDVersion);
			proc.execute();
			proc.close();
			
		con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
		
}
public DTOSubmissionMst getSubmissionInfoEURegion(String workspaceId)
{
	DTOSubmissionMst dto=new DTOSubmissionMst();
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = conMgr.ds.getConnection();
		rs = datatable.getDataSet(con, "*","view_AllWorkspaceSubmissionInfoEURegion" ,"vWorkspaceId='"+workspaceId+"'" , "vRegionalDTDVersion desc");
		if(rs.next())
		{
			dto.setWorkspaceId(rs.getString("vWorkSpaceId"));
			dto.setApplicationNo(rs.getString("vApplicationNo"));
			dto.setCountrycode(rs.getString("vCountryCode"));
			dto.setApplicant(rs.getString("vApplicant"));
			dto.setAgencyName(rs.getString("vAgencyName"));
			dto.setAtc(rs.getString("vAtc"));
			dto.setProcedureType(rs.getString("vProcedureType"));
			dto.setInventedName(rs.getString("vInventedName"));
			dto.setInn(rs.getString("vInn"));
			dto.setSubmissionDescription(rs.getString("vSubmissionDescription"));
			dto.setCompanyName(rs.getString("vCompanyName"));
			dto.setDateOfSubmission(rs.getDate("dDateOfSubmission"));
			dto.setProductName(rs.getString("vProductName"));
			dto.setProductType(rs.getString("vProductType"));
			dto.setApplicationType(rs.getString("vApplicationType"));
			dto.setCountryName(rs.getString("vCountryName"));
			dto.setWorkspaceDesc(rs.getString("vWorkSpaceDesc"));
			dto.setLastPublishedVersion(rs.getString("vLastPublishedVersion"));
			dto.setCountryRegion(rs.getString("CountryRegion"));
			dto.setCountryCodeName(rs.getString("CountryCodeName"));
			dto.setSubmittedBy(rs.getInt("iModifyBy"));
			dto.setSubmittedOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			dto.setTrackingNo(rs.getString("vTrackingNo"));
			dto.setUuid(rs.getString("vUUID"));
			dto.setHighLvlNo(rs.getString("vHighLvlNo"));
			dto.setRegionalDTDVersion(rs.getString("vRegionalDTDVersion"));
			dto.setDosageForm(rs.getString("vDosageForm"));
			dto.setDmfHolder(rs.getString("vDMFHolder"));
			dto.setPmfHolder(rs.getString("vPMFHolder"));
			dto.setDmfNumber(rs.getString("vDMFNumber"));
			dto.setPmfNumber(rs.getString("vPMFNumber"));
			dto.setApplicationId(rs.getString("vApplicationId"));
			dto.setSubmissionId(rs.getString("vSubmissionId"));
			dto.setIsGroupSubmission(rs.getString("isGroupSubmission"));
			dto.seteSubmissionId(rs.getString("veSubmissionId"));
			dto.setAAN(rs.getString("vAAN"));
			dto.setRegActLead(rs.getString("vRegActLead"));	
			dto.setLicensee(rs.getString("vLicensee"));	
			dto.setDossierIdentifier(rs.getString("vDossierIdentifier"));
			dto.setDossierType(rs.getString("vDossierType"));
			dto.setLicenseeName(rs.getString("vLicenseeName"));
			dto.setLicenseeType(rs.getString("vLicenseeType"));
			dto.setEUDtdVersion(rs.getString("vEUDTDVersion"));
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
	return dto;
	
}


}//main class ended
