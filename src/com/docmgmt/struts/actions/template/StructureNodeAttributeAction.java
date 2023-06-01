package com.docmgmt.struts.actions.template;



import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class StructureNodeAttributeAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String userTypeName;
	
	
	@Override
	public String execute()
	{
		userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		templateId=ActionContext.getContext().getSession().get("templateId").toString();
		
		getTemplateDetail = docMgmtImpl.getTemplateNodeDetailByNodeId(templateId, nodeId);
		
		return SUCCESS;
	}

	
	public String templateId;
	public int nodeId;
	public String displayName;
	public Vector getTemplateDetail;
	public Vector getAllNodes;

	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Vector getGetTemplateDetail() {
		return getTemplateDetail;
	}
	public void setGetTemplateDetail(Vector getTemplateDetail) {
		this.getTemplateDetail = getTemplateDetail;
	}
	public Vector getGetAllNodes() {
		return getAllNodes;
	}
	public void setGetAllNodes(Vector getAllNodes) {
		this.getAllNodes = getAllNodes;
	}
		
	
}
