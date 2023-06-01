package com.docmgmt.struts.actions.search;

import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class HighLevelSearchResultAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute(){
		
		getSearchResult = docMgmtImpl.getGenericSearchResult(locationCode, clientCode, projectCode, workSpaceId, userCode, 0);
		
		return SUCCESS;
	}
	
	public String locationCode;
	public String projectCode;
	public String clientCode;
	public String workSpaceId;
	public String userCode;
	public Vector getSearchResult;

	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public Vector getGetSearchResult() {
		return getSearchResult;
	}
	public void setGetSearchResult(Vector getSearchResult) {
		this.getSearchResult = getSearchResult;
	}
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
}

