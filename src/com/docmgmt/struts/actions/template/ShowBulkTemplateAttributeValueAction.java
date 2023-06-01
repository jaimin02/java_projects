package com.docmgmt.struts.actions.template;

import java.util.Vector;


import com.docmgmt.server.db.DocMgmtImpl;

import com.opensymphony.xwork2.ActionSupport;

public class ShowBulkTemplateAttributeValueAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	
	@Override
	public String execute()
	{
		
		getDetails = docMgmtImpl.setBulkAttributeValueOnTemplate(templateId,attrId);
	//	System.out.println("templateId:"+templateId);
		getAttrValueFromMatrix = docMgmtImpl.getAttributeValueByAttrId(attrId);
	//	System.out.println("size of getAttrValueFromMatrix vector:"+getAttrValueFromMatrix.size());
		return SUCCESS;
	}
	
	public int attrId;
	public String templateId;
	public Vector getDetails;
	public Vector getAttrValueFromMatrix;


	public Vector getGetDetails() {
		return getDetails;
	}
	public void setGetDetails(Vector getDetails) {
		this.getDetails = getDetails;
	}
	public int getAttrId() {
		return attrId;
	}
	public void setAttrId(int attrId) {
		this.attrId = attrId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public Vector getGetAttrValueFromMatrix() {
		return getAttrValueFromMatrix;
	}
	public void setGetAttrValueFromMatrix(Vector getAttrValueFromMatrix) {
		this.getAttrValueFromMatrix = getAttrValueFromMatrix;
	}
	
}
