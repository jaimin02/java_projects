package com.docmgmt.struts.actions.labelandpublish;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
public class WorkspaceSubmissionAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
	
		Vector wsDetail = docMgmtImpl.getWorkspaceDesc(workSpaceId);
		if(wsDetail != null){
			Object[] record = new Object[wsDetail.size()];
			record = (Object[])wsDetail.elementAt(0);
			if(record != null)
			{ 
				client_name=record[4].toString();
				project_type=record[5].toString();
				project_name=record[1].toString();
				
			}
		}
		Calendar cal = Calendar.getInstance();
        now = cal.getTime();
        
        DTOWorkSpaceMst wmst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
        lastPublishedVersion = wmst.getLastPublishedVersion();
		relatedSeqNo = lastPublishedVersion;
		getCountryDetail = docMgmtImpl.getAllCountryDetail();
        getAgencyDetail = docMgmtImpl.getAllAgency();
        
        /** ---------------- get details of original submission for US-------------------- **/
        
        if(!lastPublishedVersion.equals("-999"))
        {
        	DTOSubmissionMst dtosub = new DTOSubmissionMst(); 
            dtosub = docMgmtImpl.getOriginalSubmissionDetail(workSpaceId);
            agency = dtosub.getAgencyName();
            applNo = dtosub.getApplicationNo();
        	companyName = dtosub.getCompanyName();
        	prodName = dtosub.getProductName();
        	prodType = dtosub.getProductType();
        	applicationType = dtosub.getApplicationType();
        	
        	String countryCode = docMgmtImpl.getCountry(dtosub.getCountrycode()).getCountryCode();
        	if(countryCode.equals("us"))
        	{
        		UsSubmission = true;
        	}
        }
    	/** ------------------------------------------------------------------------------ **/
        
        
		return SUCCESS;
	}
	
	public String lastPublishedVersion;
	public Date now;
	public String client_name;
	public String project_type;
	public String project_name;
	public String labelId;
	public int labelNo;
	public String workSpaceId;
	public Vector getCountryDetail;
	public Vector getAgencyDetail;
	public String relatedSeqNo;
	
	public String agency;
	public String applNo;
	public String companyName;
	public String prodName;
	public String prodType;
	public String applicationType;
	public boolean UsSubmission = false;
	
	

	public DocMgmtImpl getDocMgmtImpl() {
		return docMgmtImpl;
	}
	public void setDocMgmtImpl(DocMgmtImpl docMgmtImpl) {
		this.docMgmtImpl = docMgmtImpl;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getApplNo() {
		return applNo;
	}
	public void setApplNo(String applNo) {
		this.applNo = applNo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getRelatedSeqNo() {
		return relatedSeqNo;
	}
	public void setRelatedSeqNo(String relatedSeqNo) {
		this.relatedSeqNo = relatedSeqNo;
	}
	public Vector getGetAgencyDetail() {
		return getAgencyDetail;
	}
	public void setGetAgencyDetail(Vector getAgencyDetail) {
		this.getAgencyDetail = getAgencyDetail;
	}
	public Vector getGetCountryDetail() {
		return getCountryDetail;
	}
	public void setGetCountryDetail(Vector getCountryDetail) {
		this.getCountryDetail = getCountryDetail;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	public int getLabelNo() {
		return labelNo;
	}
	public void setLabelNo(int labelNo) {
		this.labelNo = labelNo;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getProject_type() {
		return project_type;
	}
	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	 public Date getNow() {
	        return now;
	   }
	public String getLastPublishedVersion() {
		return lastPublishedVersion;
	}
	public void setLastPublishedVersion(String lastPublishedVersion) {
		this.lastPublishedVersion = lastPublishedVersion;
	}
	public boolean isUsSubmission() {
		return UsSubmission;
	}
	public void setUsSubmission(boolean usSubmission) {
		UsSubmission = usSubmission;
	}

	
}
	
	


