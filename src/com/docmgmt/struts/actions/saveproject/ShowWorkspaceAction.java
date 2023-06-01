package com.docmgmt.struts.actions.saveproject;



import java.util.Vector;

import com.docmgmt.dto.DTODepartmentMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowWorkspaceAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String workSpaceId;
	public String workspaceDetail="";
	@Override
	public String execute()
	{
		String defaultWorkSpaceId = ActionContext.getContext().getSession().get("defaultWorkspace").toString();
		if(defaultWorkSpaceId != null && !defaultWorkSpaceId.equals("")){
			workSpaceId = defaultWorkSpaceId;
		}
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userTypeNam=ActionContext.getContext().getSession().get("usertypename").toString();
		getLocationDtl = docMgmtImpl.getLocationDtl();
		getClientDtl = docMgmtImpl.getClientDetail();
		//getDeptDtl = docMgmtImpl.getDepartmentDetail();
		//getDeptDtl = docMgmtImpl.getDepartmentInfo(docMgmtImpl.getDeptCode(userId,userGroupCode));
		if(userTypeNam.equalsIgnoreCase("BD")){
			
			getDeptDtl = docMgmtImpl.getDepartmentDetailByDeptCode("");
		}
		else {
			
			getDeptDtl = docMgmtImpl.getDepartmentDetailByDeptCode(docMgmtImpl.getDeptCode(userId,userGroupCode));	
		}
		getProjectDtl = docMgmtImpl.getProjectType();
		//User will be able to see all the admin users of the same group only...
		getUserDtl = docMgmtImpl.getuserDetailsByUserGrp(userGroupCode);
//		getDocTypeDtl = docMgmtImpl.getDocTypeDetail();
		getWorkspace = docMgmtImpl.getUserWorkspace(userGroupCode, userId);	
		
		String actionName = ActionContext.getContext().getName();
		
			
			if(actionName.contains("Skeleton")){
				for (int iWs = 0; iWs < getWorkspace.size(); iWs++) {
					DTOWorkSpaceMst dtoWorkSpaceMst = (DTOWorkSpaceMst)getWorkspace.get(iWs);
					if(dtoWorkSpaceMst.getProjectType() != ProjectType.DMS_SKELETON){
						getWorkspace.remove(iWs);
						iWs--;
					}
				}
			}else{
				for (int iWs = 0; iWs < getWorkspace.size(); iWs++) {
					DTOWorkSpaceMst dtoWorkSpaceMst = (DTOWorkSpaceMst)getWorkspace.get(iWs);
					if(dtoWorkSpaceMst.getProjectType() == ProjectType.DMS_SKELETON){
						getWorkspace.remove(iWs);
						iWs--;
					}
				}
			}
		
		return SUCCESS;
		
	}	/**********************************************/
	
	public String getWorkSpaceDetail()
	{
		DTOWorkSpaceMst dtoWorkSpaceMst=docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		workspaceDetail=dtoWorkSpaceMst.getLocationCode()+":"+
						dtoWorkSpaceMst.getDeptCode()+":"+
						dtoWorkSpaceMst.getClientCode()+":"+
						dtoWorkSpaceMst.getProjectCode();		
		return SUCCESS;
	}
	public Vector getLocationDtl;
	public Vector getClientDtl;
	public Vector<DTODepartmentMst> getDeptDtl;
	public Vector getProjectDtl;
	public Vector getUserDtl;
	public Vector getDocTypeDtl;
	public Vector getWorkspace;
	public int noOfFilesCopied;
	public Vector getGetDocTypeDtl() {
		return getDocTypeDtl;
	}

	public void setGetDocTypeDtl(Vector getDocTypeDtl) {
		this.getDocTypeDtl = getDocTypeDtl;
	}

	public Vector getGetUserDtl() {
		return getUserDtl;
	}

	public void setGetUserDtl(Vector getUserDtl) {
		this.getUserDtl = getUserDtl;
	}

	public Vector getGetProjectDtl() {
		return getProjectDtl;
	}

	public void setGetProjectDtl(Vector getProjectDtl) {
		this.getProjectDtl = getProjectDtl;
	}

	public Vector<DTODepartmentMst> getGetDeptDtl() {
		return getDeptDtl;
	}

	public void setGetDeptDtl(Vector<DTODepartmentMst> getDeptDtl) {
		this.getDeptDtl = getDeptDtl;
	}

	public Vector getGetClientDtl() {
		return getClientDtl;
	}

	public void setGetClientDtl(Vector getClientDtl) {
		this.getClientDtl = getClientDtl;
	}

	public Vector getGetLocationDtl() {
		return getLocationDtl;
	}

	public void setGetLocationDtl(Vector getLocationDtl) {
		this.getLocationDtl = getLocationDtl;
	}

	public Vector getGetWorkspace() {
		return getWorkspace;
	}

	public void setGetWorkspace(Vector getWorkspace) {
		this.getWorkspace = getWorkspace;
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
 	
}
