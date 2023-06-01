package com.docmgmt.struts.actions.workspace.ProjectCompletionReport;

import java.util.ArrayList;

import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ProjectCompletionReportAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	public DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public ArrayList<DTOWorkSpaceUserMst> getProjectCompletionDetail = new ArrayList<DTOWorkSpaceUserMst>();
	
	@Override
	public String execute() {

		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());			
		
		getProjectCompletionDetail = docMgmtImpl.getCompelteWSDetail(userCode,userGroupCode);
				
		return SUCCESS;
	}
}
