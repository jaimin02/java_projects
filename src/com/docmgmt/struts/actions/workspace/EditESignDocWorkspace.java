package com.docmgmt.struts.actions.workspace;

import java.util.Vector;

import com.docmgmt.dto.DTOClientMst;
import com.docmgmt.dto.DTODepartmentMst;
import com.docmgmt.dto.DTOLocationMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EditESignDocWorkspace extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute(){
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String userTypeNam=ActionContext.getContext().getSession().get("usertypename").toString();
		projectTypes = docMgmtImpl.getProjectType();
		locations = docMgmtImpl.getLocationDtl();
		clients = docMgmtImpl.getClientDetail();
		//department = docMgmtImpl.getDepartmentDetail();
		//department = docMgmtImpl.getDepartmentInfo(docMgmtImpl.getDeptCode(userId,userGroupCode));
		if(userTypeNam.equalsIgnoreCase("BD")){
			
			department = docMgmtImpl.getDepartmentDetailByDeptCode("");
		}
		else {
			
			department = docMgmtImpl.getDepartmentDetailByDeptCode(docMgmtImpl.getDeptCode(userId,userGroupCode));	
		}
		 dto = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		 
		/*for(int j=0; j< department.size();j++)
		{
			DTODepartmentMst dtoDepartment=(DTODepartmentMst) department.get(j);			
			if(dtoDepartment.getStatusIndi()=='D')
				department.remove(j);
		}*/
		
		for(int j=0; j< clients.size();j++)
		{
			DTOClientMst dtoClient=(DTOClientMst) clients.get(j);			
			if(dtoClient.getStatusIndi()=='D')
				clients.remove(j);
		}
		
		for(int j=0; j< locations.size();j++)
		{
			DTOLocationMst dtoLocation=(DTOLocationMst) locations.get(j);			
			if(dtoLocation.getStatusIndi()=='D')
				locations.remove(j);
		}
		
		if(dto !=null)
		{	
			workSpaceDesc = dto.getWorkSpaceDesc();
			
		}
		return SUCCESS;
	}
	
	public DTOWorkSpaceMst dto ;
	public String workSpaceDesc;
	public Vector<DTODepartmentMst> department;
	public Vector projectTypes;
	public Vector locations;
	public Vector clients;
	public Vector getWorkspaceDetail;
	public String workSpaceId;
	
	public Vector getGetWorkspaceDetail() {
		return getWorkspaceDetail;
	}

	public void setGetWorkspaceDetail(Vector getWorkspaceDetail) {
		this.getWorkspaceDetail = getWorkspaceDetail;
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public Vector getProjectTypes() {
		return projectTypes;
	}

	public void setProjectTypes(Vector projectTypes) {
		this.projectTypes = projectTypes;
	}

	public Vector getLocations() {
		return locations;
	}

	public void setLocations(Vector locations) {
		this.locations = locations;
	}

	public Vector getClients() {
		return clients;
	}

	public void setClients(Vector clients) {
		this.clients = clients;
	}

	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}

	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}

	public Vector<DTODepartmentMst> getDepartment() {
		return department;
	}

	public void setDepartment(Vector<DTODepartmentMst> department) {
		this.department = department;
	}

	public DTOWorkSpaceMst getDto() {
		return dto;
	}

	public void setDto(DTOWorkSpaceMst dto) {
		this.dto = dto;
	}
	
	
 	
}
