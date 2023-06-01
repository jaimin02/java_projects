package com.docmgmt.struts.actions.workspace;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class AddLeafNodeAction extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

		
	@Override
	public String execute()
	{
		workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		 KnetProperties knetProperties = KnetProperties.getPropInfo();
		 eTMFCustomization = knetProperties.getValue("ETMFCustomization");
		
		DTOWorkSpaceMst wsDetail = docMgmtImpl.getWorkSpaceDetail(workspaceId);
		
		//appType = ProjectType.getApplicationType(wsDetail.getProjectType());
		appType = wsDetail.getProjectCode();
		
		return SUCCESS;
	}
	
	public int nodeId;
	public String workspaceId;
	public String appType; 
    public String eTMFCustomization;
}
