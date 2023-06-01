package com.docmgmt.struts.actions.search;

import java.util.Vector;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowSavedSearchAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		getSavedSearch = docMgmtImpl.getAllDetailAboutSavedSearch(userId);
	
		return SUCCESS;
	}
	
	public Vector getSavedSearch;
	
	public Vector getGetSavedSearch() {
		return getSavedSearch;
	}

	public void setGetSavedSearch(Vector getSavedSearch) {
		this.getSavedSearch = getSavedSearch;
	}
}

