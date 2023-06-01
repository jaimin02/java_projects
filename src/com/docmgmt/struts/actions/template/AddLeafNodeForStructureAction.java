package com.docmgmt.struts.actions.template;

import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddLeafNodeForStructureAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	
	@Override
	public String execute()
	{
		//int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		templateId = ActionContext.getContext().getSession().get("templateId").toString();
		getNodeDetail=docMgmtImpl.getTemplateNodeDetailByNodeId(templateId,nodeId);
		
		System.out.println(getNodeDetail.size());
		return SUCCESS;
	}
	
	public int nodeId;
	public String nodeName;
	public String templateId;
	public Vector getNodeDetail;


	public Vector getGetNodeDetail() {
		return getNodeDetail;
	}
	public void setGetNodeDetail(Vector getNodeDetail) {
		this.getNodeDetail = getNodeDetail;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

}
