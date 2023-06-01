package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ProjectAction extends ActionSupport {

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public ArrayList<DTOWorkSpaceMst> workspaceMstList;
	
	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		Vector<DTOWorkSpaceNodeDetail> workspaceNodeDtls;
		
		workspaceMstList = docMgmtImpl.getWorkspaceDtByProjPublishType(userId, userGroupCode, ProjectType.DMS_DOC_CENTRIC);
		for (int i = 0; i < workspaceMstList.size(); i++) {
			DTOWorkSpaceMst dtoWsMst = workspaceMstList.get(i);
			workspaceNodeDtls = docMgmtImpl.getNodeDetail(dtoWsMst.getWorkSpaceId(), 1);
			dtoWsMst.setRootNodeDtl(workspaceNodeDtls.get(0));
		}
	return SUCCESS;
	}
}
