package com.docmgmt.struts.actions.template;



import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.CopyStructureTreeBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetSourceTreeTemplateAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		
		CopyStructureTreeBean treeBean = new CopyStructureTreeBean();
		htmlCode = treeBean.getTemplateTreeHtml(sourceTemplateId,userGroupCode,userCode);
		
		return SUCCESS;
	}
	
	public String sourceTemplateId;
	public String htmlCode;

	public String getHtmlCode() {
		return htmlCode;
	}

	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}

	public String getSourceTemplateId() {
		return sourceTemplateId;
	}

	public void setSourceTemplateId(String sourceTemplateId) {
		this.sourceTemplateId = sourceTemplateId;
	}

	
	
}
