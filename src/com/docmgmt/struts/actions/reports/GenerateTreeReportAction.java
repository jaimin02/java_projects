package com.docmgmt.struts.actions.reports;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GenerateTreeReportAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public Vector<DTOWorkSpaceMst> getAllWorkspace;
	@Override
	public String execute()
	{
		String defaultWorkSpaceId = ActionContext.getContext().getSession().get("defaultWorkspace").toString();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		
		getAllWorkspace = docMgmtImpl.getUserWorkspace(userGroupCode, userId);

		return SUCCESS;
	}
	
}