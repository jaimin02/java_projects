package com.docmgmt.struts.actions.template;


import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteStructureNodeAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	
	@Override
	public String execute()
	{

		String  templateId = ActionContext.getContext().getSession().get("templateId").toString();
		docMgmtImpl.deleteTemplateNode(templateId,nodeId);
		
		
		return SUCCESS;
	}
	
	public int nodeId;



	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

}
