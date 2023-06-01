package com.docmgmt.struts.actions.labelandpublish.europeansubmission.cms;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.docmgmt.dto.DTOAgencyMst;
import com.docmgmt.dto.DTOCountryMst;
import com.docmgmt.dto.DTOWorkspaceCMSMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ManageCMSAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	public String workSpaceId;
	public String workSpaceDesc;
	public String selectedCountry;
	public String selectedAgency;
	public int waveNo;
	public Vector<DTOCountryMst> countries;
	public Vector<DTOAgencyMst> agencies;
	public String htmlContent;
	public List<DTOWorkspaceCMSMst> lstWorkspaceCMS;
	public int workspaceCMSId;
	public String regionId;
	public String countryId;
	public String cmsTrackNum;
	public String inventedName;
	
	public String dtdVersion="";
	
	@Override
	public String execute()
	{
		countries = docMgmtImpl.getCountriesRegionWise(regionId);
		for (int i = 0; i < countries.size(); i++) {
			DTOCountryMst dtoCountryMst = countries.get(i);
			if(dtoCountryMst.getCountryCode().equals("emea")){
				countries.remove(i);
				i--;
				//break;
			}
			if(dtoCountryMst.getCountryId().equalsIgnoreCase(countryId))
			{
				
				countries.remove(i);
				i--;
			}
		}
		agencies = docMgmtImpl.getAllAgency();
		lstWorkspaceCMS = docMgmtImpl.getWorkspaceCMSInfo(workSpaceId);
		
		return SUCCESS;
	}
	public String getCountryAgencies(){
		
		System.out.println(dtdVersion);
		htmlContent = "";
		ArrayList<DTOAgencyMst> countryAgencies = docMgmtImpl.getAgenciesForCountry(selectedCountry,dtdVersion);
		for (DTOAgencyMst agencyMst : countryAgencies) {
			if(!htmlContent.equals("")){
				htmlContent += ","; 
			}
			htmlContent += agencyMst.getAgencyCode()+"::"+agencyMst.getAgencyName();
		}
		return "html";
	}
	public String save() 
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		ArrayList<DTOWorkspaceCMSMst> workspaceCMSList =docMgmtImpl.getWorkspaceCMSInfo(workSpaceId);
		for (DTOWorkspaceCMSMst workspaceCMSMst : workspaceCMSList) {
			if(workspaceCMSMst.getCountryCode().equals(selectedCountry)){
				htmlContent = "Selected country is already added as CMS. ";
				return "html";
			}
		}
		DTOWorkspaceCMSMst dto = new DTOWorkspaceCMSMst();
		dto.setCountryCode(selectedCountry);
		dto.setAgencyCode(selectedAgency);
		dto.setWorkspaceId(workSpaceId);
		dto.setWaveNo(waveNo);
		dto.setModifyBy(userId);
		dto.setCmsTrackNum(cmsTrackNum);
		//dto.setInventedName(inventedName);
		dto.setInventedName("");
		
		
		docMgmtImpl.insertWorkspaceCMS(dto, 1);//'insert' Mode
		htmlContent = "CMS Details Added Successfully";
		return "html";
	}
	
	public String remove()
	{
		DTOWorkspaceCMSMst dto = new DTOWorkspaceCMSMst();
		
		dto.setWorkspaceCMSId(workspaceCMSId);
		docMgmtImpl.insertWorkspaceCMS(dto, 3);//'delete' Mode
		htmlContent = "CMS Details Removed Successfully";
		return "html";
	}
	
	public String updateCMSTrackNo()
	{
		DTOWorkspaceCMSMst dto = new DTOWorkspaceCMSMst();
		dto.setWorkspaceCMSId(workspaceCMSId);
		dto.setCmsTrackNum(cmsTrackNum);
		dto.setInventedName(inventedName);
		docMgmtImpl.insertWorkspaceCMS(dto, 2);//'update' tracking number Mode
		htmlContent="Tracking Number updated successfully.";
		return  "html";
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}

	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}

	public int getWaveNo() {
		return waveNo;
	}

	public void setWaveNo(int waveNo) {
		this.waveNo = waveNo;
	}

	public Vector<DTOCountryMst> getCountries() {
		return countries;
	}

	public void setCountries(Vector<DTOCountryMst> countries) {
		this.countries = countries;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public List<DTOWorkspaceCMSMst> getLstWorkspaceCMS() {
		return lstWorkspaceCMS;
	}

	public void setLstWorkspaceCMS(List<DTOWorkspaceCMSMst> lstWorkspaceCMS) {
		this.lstWorkspaceCMS = lstWorkspaceCMS;
	}

	public String getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}

	public String getSelectedAgency() {
		return selectedAgency;
	}

	public void setSelectedAgency(String selectedAgency) {
		this.selectedAgency = selectedAgency;
	}

	public Vector<DTOAgencyMst> getAgencies() {
		return agencies;
	}

	public void setAgencies(Vector<DTOAgencyMst> agencies) {
		this.agencies = agencies;
	}

	public int getWorkspaceCMSId() {
		return workspaceCMSId;
	}

	public void setWorkspaceCMSId(int workspaceCMSId) {
		this.workspaceCMSId = workspaceCMSId;
	}
}
	
	


