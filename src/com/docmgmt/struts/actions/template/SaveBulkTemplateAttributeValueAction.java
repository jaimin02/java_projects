package com.docmgmt.struts.actions.template;

import com.docmgmt.dto.DTOTemplateNodeAttrDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class SaveBulkTemplateAttributeValueAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		if(Flag == false)
		{
			DTOTemplateNodeAttrDetail dto = new DTOTemplateNodeAttrDetail();
			dto.setTemplateId(templateId);
			dto.setAttrId(attrId);
			dto.setAttrValue(attributeValue);
			dto.setNodeId(-999);
			docMgmtImpl.updateTemplateNodeAttrValue(dto);
			
		}
		else
		{
			
			DTOTemplateNodeAttrDetail dto = new DTOTemplateNodeAttrDetail();
			dto.setTemplateId(templateId);
			dto.setAttrId(attrId);
			dto.setAttrValue(attributeValue);
			dto.setNodeId(nodeId);
			docMgmtImpl.updateTemplateNodeAttrValue(dto);
			
		}
			
	 	return SUCCESS;
	}
	
	public boolean Flag ;
	public String templateId;
	public int attrId;
	public String attributeValue;
	public int nodeId;
	
	
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
	public int getAttrId() {
		return attrId;
	}
	public void setAttrId(int attrId) {
		this.attrId = attrId;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public boolean isFlag() {
		return Flag;
	}
	public void setFlag(boolean flag) {
		Flag = flag;
	}
	
}
