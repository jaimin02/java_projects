package com.docmgmt.struts.actions.template;



import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.DocStructureTreeBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DocStructureNodesAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	public String htmlCode;
	public String templateId;
	
	@Override
	public String execute()
	{
		templateId=ActionContext.getContext().getSession().get("templateId").toString();
		DocStructureTreeBean treeBean = new DocStructureTreeBean();
		int userGroupCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		htmlCode = treeBean.getTemplateTreeHtml(templateId,userGroupCode,userCode);
		return SUCCESS;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getHtmlCode() {
		return htmlCode;
	}

	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}
	
}
