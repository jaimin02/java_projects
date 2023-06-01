package com.docmgmt.struts.actions.WelcomePage.DefaultWorkSpace;

import java.util.Vector;

import com.docmgmt.dto.DTODefaultWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DefaultWorkspaceAction extends ActionSupport 
{
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public Vector getWorkSpaceDetail;
	public String workSpaceId;
	public String DefaultworkSpace;
	
	@Override
	public String execute(){

		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		//getWorkSpaceDetail = docMgmtImpl.getUserWorkspace(userGroupCode, userId);
		//getWorkSpaceDetail = docMgmtImpl.getUserWorkspaceForSerachProject(userGroupCode, userId);
		getWorkSpaceDetail = docMgmtImpl.getUserWorkspaceForSerachProjectList(userGroupCode, userId);
		/*String defaultWorkSpaceId = ActionContext.getContext().getSession().get("defaultWorkspace").toString();
		if(defaultWorkSpaceId != null && !defaultWorkSpaceId.equals("")){
			workSpaceId = defaultWorkSpaceId;
		}*/
		return SUCCESS;
	}
	
	public String SaveDefaultWorkSpace(){
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());		
		if(Integer.parseInt(workSpaceId) != -1 && DefaultworkSpace.charAt(0) != 'N'){

				DTODefaultWorkSpaceMst dto = new DTODefaultWorkSpaceMst();
				dto.setUserCode(userId);
				dto.setWorkSpaceID(workSpaceId);
				dto.setRemark("");
				dto.setModifyBy(userId);
				dto.setStatusIndi('N');
			
				DocMgmtImpl.insertIntoDefaultWorkspaceMst(dto, 1);
				ActionContext.getContext().getSession().put("defaultWorkspace", workSpaceId);
				addActionMessage(workSpaceId+ " as Default Project Selected");
		}
		
		if(DefaultworkSpace.charAt(0) == 'N'){
			DTODefaultWorkSpaceMst dto = new DTODefaultWorkSpaceMst();
			dto.setUserCode(userId);
			dto.setWorkSpaceID(workSpaceId);
			dto.setRemark("");
			dto.setModifyBy(userId);
			dto.setStatusIndi('N');
		
			DocMgmtImpl.insertIntoDefaultWorkspaceMst(dto, 3);
			ActionContext.getContext().getSession().put("defaultWorkspace", " ");
		}
		 

		return INPUT;
	}
	public Vector getGetWorkSpaceDetail() {
		return getWorkSpaceDetail;
	}


	public void setGetWorkSpaceDetail(Vector getWorkSpaceDetail) {
		this.getWorkSpaceDetail = getWorkSpaceDetail;
	}


	public String getWorkSpaceId() {
		return workSpaceId;
	}


	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public String getDefaultworkSpace() {
		return DefaultworkSpace;
	}

	public void setDefaultworkSpace(String defaultworkSpace) {
		DefaultworkSpace = defaultworkSpace;
	}

}
