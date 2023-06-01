package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOTemplateMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CreateProjectAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	
	public String workSpaceId;
	public String workSpaceDesc;
	
	public Vector<DTOWorkSpaceNodeDetail> workspaceNodeDtls;
	public ArrayList<DTOWorkSpaceMst> workspaceMstList;
	public DTOWorkSpaceMst rootNodeDtl;
	public Vector<DTOTemplateMst> templateList;
	
	@Override
	public String execute()
	{
		templateList = docMgmtImpl.getAllTemplates();
		for (int itrTempList = 0; itrTempList < templateList.size(); itrTempList++) 
		{
			DTOTemplateMst dtoTemplateMst = templateList.get(itrTempList);
			if (dtoTemplateMst.getStatusIndi() == 'D' ) 
			{
				templateList.remove(itrTempList);
				itrTempList--;
			}
		}
		
		workspaceMstList = new ArrayList<DTOWorkSpaceMst>();
		
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		
		workspaceMstList = docMgmtImpl.getWorkspaceDtByProjPublishType(userId, userGroupCode, ProjectType.DMS_DOC_CENTRIC);		
		
		for (int i = 0; i < workspaceMstList.size(); i++) {
			DTOWorkSpaceMst dtoWsMst = workspaceMstList.get(i);
			workspaceNodeDtls = docMgmtImpl.getNodeDetail(dtoWsMst.getWorkSpaceId(), 1);
			dtoWsMst.setRootNodeDtl(workspaceNodeDtls.get(0));
			dtoWsMst.setTemplateDesc(docMgmtImpl.getWorkSpaceDetail(dtoWsMst.getWorkSpaceId()).getTemplateDesc());
		}
		
		
		return SUCCESS;
	}

	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}

	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}
	
}
