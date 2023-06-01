package com.docmgmt.struts.actions.workspace;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class UpdateESignDocWorkspaceAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	public String htmlContent="";

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute(){

		this.UpdateWorkspace();
		return SUCCESS;
	}
	
	public void UpdateWorkspace()
	{
			int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			DTOWorkSpaceMst dto = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
			dto.setWorkSpaceDesc(workSpaceDesc);
			dto.setClientCode(clientCode);
			dto.setDeptCode(deptCode);
			dto.setLocationCode(locationCode);
			dto.setProjectCode(projectCode);
			dto.setRemark(remark);
			dto.setWorkSpaceId(workSpaceId);
			dto.setModifyBy(userId);
			
			docMgmtImpl.AddUpdateWorkspace(dto,1,"","" ,2);
	}
	public String WorkSpaceExists() 
	{	
		boolean workspaceexist = docMgmtImpl.workspaceNameExist(workSpaceDesc);
		if (workspaceexist== true || workSpaceDesc=="")
		{
			htmlContent = workSpaceDesc+" already exists.";
		}
		else
		{
			htmlContent = "<font color=\"green\">"+workSpaceDesc+" is available. </font>";			
		}
		return SUCCESS;
	}

	
	public String clientCode;
	public String deptCode;
	public String locationCode;
	public String  projectCode;
	public String remark;
	public String workSpaceDesc;
	public String workSpaceId;
	

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}

	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

}

