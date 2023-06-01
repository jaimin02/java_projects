package com.docmgmt.struts.actions.labelandpublish;

import java.sql.SQLException;
import java.util.Map;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class GetActiveInactiveHistoryAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String workSpaceId;
	public String mode;
	public String userTypeName;
	public int attrId;
	public String wsId;
	public String htmlContent;
	public String lbl_folderName;
	public String lbl_nodeName;
	public String lbl_nodeDisplayName;
	
	
	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		//String defaultWorkSpaceId = ActionContext.getContext().getSession().get("defaultWorkspace").toString();
		userTypeName = ActionContext.getContext().getSession().get("usertypename").toString();
		/*if(defaultWorkSpaceId != null && !defaultWorkSpaceId.equals("")){
			workSpaceId = defaultWorkSpaceId;
		}*/
		//getWorkspaceDetail = docMgmtImpl.getUserWorkspace(userGroupCode, userId);
		getWorkspaceDetail = docMgmtImpl.getUserWorkspaceList(userGroupCode, userId);
		//System.out.println("(original)no. of workspaces: " + getWorkspaceDetail.size());
		
		//filter the list of workspace shown in various publish forms
		String actionName = ActionContext.getContext().getActionInvocation().getInvocationContext().getName();
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
	
		/*workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		getDeletedNodeDetail = docMgmtImpl.getDeletedNodeDetail(workspaceId);
		return SUCCESS;*/
	}
	
	public String displaySectionwiseActiveInactiveHistory() throws SQLException{
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		lbl_folderName = knetProperties.getValue("ForlderName");
		lbl_nodeName = knetProperties.getValue("NodeName");
		DocMgmtImpl docmgmptl=new DocMgmtImpl();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		userName = ActionContext.getContext().getSession().get("username").toString();
		wsDesc = docMgmtImpl.getWorkSpaceDetailWSList(workSpaceId).getWorkSpaceDesc();
		getDeletedNodeDetail = docMgmtImpl.getDeletedNodeDetailForActivity(workSpaceId);
		//sectionName = docmgmptl.getWorkspaceNodeDetailByNodeId(workspaceId, nodeId).get(0).getNodeDisplayName();
		
		return SUCCESS;
	}
	
	
	public String userName;
	public String sectionName;
	public String wsDesc;
	public Vector getDeletedNodeDetail;
	public Vector<DTOWorkSpaceUserRightsMst>workspaceTreeVector;
	public Map<Integer ,Vector<DTOWorkSpaceUserRightsMst>> map; 
	public Vector<DTOWorkSpaceUserRightsMst>userRightsData;
	public Vector<DTOWorkSpaceUserRightsMst>userRightsDataToShow;
	public String workspaceId;
	public int nodeId;
	
	public Vector<DTOWorkSpaceMst> getWorkspaceDetail;
	
	public Vector<DTOWorkSpaceMst> getGetWorkspaceDetail() {
		return getWorkspaceDetail;
	}

	public void setGetWorkspaceDetail(Vector<DTOWorkSpaceMst> getWorkspaceDetail) {
		this.getWorkspaceDetail = getWorkspaceDetail;
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
}
	
