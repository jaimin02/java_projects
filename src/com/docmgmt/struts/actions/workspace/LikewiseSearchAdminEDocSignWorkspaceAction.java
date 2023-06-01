package com.docmgmt.struts.actions.workspace;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LikewiseSearchAdminEDocSignWorkspaceAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public boolean dossStatPresent;
	public String mode;
	public String eTMFCustomization;
	
	@Override
	public String execute()
	{		
			getDetailOfWorkspace();
		return SUCCESS;
	}

	public void getDetailOfWorkspace(){
		
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		//String defaultWorkSpaceId = ActionContext.getContext().getSession().get("defaultWorkspace").toString(); 
		String userType = ActionContext.getContext().getSession().get("usertypename").toString();
		
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		eTMFCustomization = knetProperties.getValue("ETMFCustomization");
		eTMFCustomization = eTMFCustomization.toLowerCase();
		
		DTOWorkSpaceMst defaultWsMst = null;
		
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserGroupCode(userGroupCode);
		userMst.setUserCode(userId);
		userMst.setUserType(userType);
		DTOWorkSpaceMst dto=new DTOWorkSpaceMst();
		dto.setClientCode(clientCode);
		//dto.setLocationCode(locationCode);
		dto.setLocationCode("");
		dto.setProjectCode(projectCode);
		dto.setWorkSpaceDesc(workSpaceDesc);
		
		if(mode != null && !mode.trim().equals("") && ( mode.charAt(0) == ProjectType.DMS_SKELETON )) 
		{			
			ArrayList<Character> projectTypeList=new ArrayList<Character>();
			projectTypeList.add(ProjectType.DMS_SKELETON);
			workspaceSummaryDetails=docMgmtImpl.searchWorkspaceByProjectType(dto,userMst,projectTypeList);
		}
		else{
			//workspaceSummaryDetails=docMgmtImpl.searchWorkspace(dto,userMst);
			//workspaceSummaryDetails=docMgmtImpl.searchWorkspaceForESignDoc(dto,userMst);
			workspaceSummaryDetails=docMgmtImpl.searchWorkspaceForESignDocList(dto,userMst);
		}
				
		dossStatPresent=docMgmtImpl.checkDossierStatusAttribute();		
		if (dossStatPresent)
		{
			for (int i=0;i<workspaceSummaryDetails.size();i++)
			{
				DTOWorkSpaceMst dtoWsMst = workspaceSummaryDetails.get(i);
				dtoWsMst.setIsdossierStatus(true);
				dtoWsMst.setDossierStatus(docMgmtImpl.getDossierStatusAttribute(dtoWsMst.getWorkSpaceId()));
			}
		}
		
		/*if(defaultWorkSpace != null && defaultWorkSpace.equals("true")){
			if(defaultWorkSpaceId != null && !defaultWorkSpaceId.trim().equals("")){
				for (int i = 0; i < workspaceSummaryDetails.size(); i++) {
					DTOWorkSpaceMst dtowsmst = workspaceSummaryDetails.get(i);
					if(dtowsmst.getWorkSpaceId().equals(defaultWorkSpaceId)){
						defaultWsMst = dtowsmst;
						break;
					}
				}
			}
			workspaceSummaryDetails = new Vector<DTOWorkSpaceMst>();
			System.gc();
			if(defaultWsMst != null){
				workspaceSummaryDetails.add(defaultWsMst);				
			}

		}*/
	}
	public String projectDetailHistory(){
		//System.out.println("******************************");
		getProjectDetailHistory = docMgmtImpl.getProjectDetailHistory(workspaceId);
		
		return SUCCESS;
	}
	
	
	public Vector getProjectDetailHistory;
	public String workspaceId;
	public String projectCode;
	public String clientCode;
	public String locationCode;
	public String workSpaceDesc;
	public Vector<DTOWorkSpaceMst> workspaceSummaryDetails;
	public String vopcode;
	public String defaultWorkSpace;
	
	public Vector<DTOWorkSpaceMst> getWorkspaceSummaryDetails() {
		return workspaceSummaryDetails;
	}
	public void setWorkspaceSummaryDetails(Vector<DTOWorkSpaceMst> workspaceSummaryDetails) {
		this.workspaceSummaryDetails = workspaceSummaryDetails;
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
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}
	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}
	public String getVopcode() {
		return vopcode;
	}
	public void setVopcode(String vopcode) {
		this.vopcode = vopcode;
	}
	public String getDefaultWorkSpace() {
		return defaultWorkSpace;
	}
	public void setDefaultWorkSpace(String defaultWorkSpace) {
		this.defaultWorkSpace = defaultWorkSpace;
	}
	
}