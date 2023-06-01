package com.docmgmt.struts.actions.search;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteHotSearchAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	@Override
	public String execute()
	{
	//	int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	
		docMgmtImpl.deleteHotSearch(hotSearchId);
		return SUCCESS;
	}
	
	public String hotSearchId;
	
	public String getHotSearchId() {
		return hotSearchId;
	}

	public void setHotSearchId(String hotSearchId) {
		this.hotSearchId = hotSearchId;
	}
}

