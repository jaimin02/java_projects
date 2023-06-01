package com.docmgmt.struts.actions.labelandpublish;

import java.util.Vector;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetWorkspaceActionTable extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String workSpaceId;
	public String mode;
	public String userTypeName;
	public int attrId;
	public String wsId;
	public String htmlContent;
	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String defaultWorkSpaceId = ActionContext.getContext().getSession().get("defaultWorkspace").toString();
		userTypeName = ActionContext.getContext().getSession().get("usertypename").toString();
		if(defaultWorkSpaceId != null && !defaultWorkSpaceId.equals("")){
			workSpaceId = defaultWorkSpaceId;
		}
		getWorkspaceDetail = docMgmtImpl.getUserWorkspace(userGroupCode, userId);
		//System.out.println("(original)no. of workspaces: " + getWorkspaceDetail.size());
		
		//filter the list of workspace shown in various publish forms
		String actionName = ActionContext.getContext().getActionInvocation().getInvocationContext().getName();
		if (actionName.equals("SiteWiseProjectSnapshot")){
			getSelectedAttrDetail=docMgmtImpl.getSelectedAttrDetail();
		}
		if (actionName.equals("SubmissionInfoMain"))
		{
			char validChars[] = ProjectType.getEctdValidCharacters(); 
			for (int indWorkspace = 0 ; indWorkspace < getWorkspaceDetail.size() ; indWorkspace++)
			{
				DTOWorkSpaceMst dtoWorkSpaceMst = getWorkspaceDetail.get(indWorkspace);
				boolean valid = false;
				int indValidChars;
				for (indValidChars = 0;indValidChars < validChars.length;indValidChars++)
				{
					char validChar = validChars[indValidChars];
					if (dtoWorkSpaceMst.getProjectType() == validChar)
					{
						valid = true;
						break;
					}
				}
				
				if (!valid)
				{
					getWorkspaceDetail.remove(indWorkspace);
					indWorkspace--;
				}
			}
		}
		
		/*System.out.println("(filtered)no. of workspaces: " + getWorkspaceDetail.size());
		for (int indWs = 0 ; indWs < getWorkspaceDetail.size() ; indWs++)
		{
			System.out.print(getWorkspaceDetail.get(indWs).getWorkSpaceId() + ",");			
		}
		System.out.println();*/
		return SUCCESS;
	}
	public String getUserModuleWiseAttributeDetail(){
		
	
		htmlContent = "";
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		Vector<DTOWorkSpaceNodeAttrDetail>UserModuleWiseAttributeDetail = docMgmtImpl.UserModuleWiseAttributeDetail(wsId,attrId,userId);
		for (DTOWorkSpaceNodeAttrDetail UserModulewiseAttrdtlMst : UserModuleWiseAttributeDetail) {
			if(!htmlContent.equals("")){
				htmlContent += ","; 
			}
			htmlContent += UserModulewiseAttrdtlMst.getNodeId()+"::"+UserModulewiseAttrdtlMst.getAttrValue();
		}
		return "html";
	}

	public Vector<DTOWorkSpaceMst> getWorkspaceDetail;
	public Vector<DTOAttributeMst> getSelectedAttrDetail;
	
	
	public Vector<DTOWorkSpaceMst> getGetWorkspaceDetail() {
		return getWorkspaceDetail;
	}

	public void setGetWorkspaceDetail(Vector<DTOWorkSpaceMst> getWorkspaceDetail) {
		this.getWorkspaceDetail = getWorkspaceDetail;
	}

	public Vector<DTOAttributeMst> getGetSelectedAttrDetail() {
		return getSelectedAttrDetail;
	}

	public void setGetSelectedAttrDetail(Vector<DTOAttributeMst> getSelectedAttrDetail) {
		this.getSelectedAttrDetail = getSelectedAttrDetail;
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	
	
}
	
	


