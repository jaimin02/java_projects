package com.docmgmt.struts.actions.template;



import java.util.Vector;

import com.docmgmt.dto.DTOTemplateNodeAttr;
import com.docmgmt.dto.DTOTemplateNodeAttrDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetDetailForEditStructureAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String userTypeName;

	@Override
	public String execute()
	{
		userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		getNodeDetail=docMgmtImpl.getTemplateNodeDetailByNodeId(templateId,nodeId);
		DTOTemplateNodeAttrDetail tnad = new DTOTemplateNodeAttrDetail();
		tnad.setTemplateId(templateId);
		tnad.setNodeId(nodeId);
		getNodeAttrDetail=docMgmtImpl.getTemplateNodeAttrDtl(tnad);		
		DTOTemplateNodeAttr tna = new DTOTemplateNodeAttr();
		//tna.setTemplateId(templateId);
		//tna.setNodeId(nodeId);
		//getNodeAttr=docMgmtImpl.getTemplateNodeAttribute(tna);
		return SUCCESS;
	}

	public String templateId;
	public String nodeName;
	public int nodeId;
	public Vector getNodeDetail;
	public Vector getNodeAttrDetail;
	public Vector getNodeAttr;
	
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Vector getGetNodeDetail() {
		return getNodeDetail;
	}

	public void setGetNodeDetail(Vector getNodeDetail) {
		this.getNodeDetail = getNodeDetail;
	}

	public Vector getGetNodeAttrDetail() {
		return getNodeAttrDetail;
	}

	public void setGetNodeAttrDetail(Vector getNodeAttrDetail) {
		this.getNodeAttrDetail = getNodeAttrDetail;
	}

	public Vector getGetNodeAttr() {
		return getNodeAttr;
	}

	public void setGetNodeAttr(Vector getNodeAttr) {
		this.getNodeAttr = getNodeAttr;
	}

}
