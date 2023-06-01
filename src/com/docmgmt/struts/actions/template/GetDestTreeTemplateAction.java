package com.docmgmt.struts.actions.template;



import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.PasteStructureTreeBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetDestTreeTemplateAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		
		PasteStructureTreeBean treeBean = new PasteStructureTreeBean();
		htmlCode = treeBean.getTemplateTreeHtml(destTemplateId,userGroupCode,userCode);
		System.out.println(destTemplateId);
		
		return SUCCESS;
	}
	
	public String destTemplateId;
	public String htmlCode;

	public String getHtmlCode() {
		return htmlCode;
	}

	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}

	public String getDestTemplateId() {
		return destTemplateId;
	}

	public void setDestTemplateId(String destTemplateId) {
		this.destTemplateId = destTemplateId;
	}

	

	
	
}
