package com.docmgmt.struts.actions.master.attribute;

import java.util.Vector;

import com.docmgmt.dto.DTOAttributeValueMatrix;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveAttributeMatrixValue extends ActionSupport
{

	public String attribid;
	public String attribvalue;
	public Vector attribComboValue;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	public String getAttribid() {
		return attribid;
	}

	public void setAttribid(String attribid) {
		this.attribid = attribid;
	}

	public String getAttribvalue() {
		return attribvalue;
	}

	public void setAttribvalue(String attribvalue) {
		this.attribvalue = attribvalue;
	}

	@Override
	public String execute()
	{
		DTOAttributeValueMatrix attrmatrix=new DTOAttributeValueMatrix();
		System.out.println("attributeId"+attribid);
		attrmatrix.setAttrId(Integer.parseInt(attribid));
		attrmatrix.setAttrValue(attribvalue);
		attrmatrix.setAttrMatrixDisplayValue("");
		String uid=ActionContext.getContext().getSession().get("userid").toString();
		docMgmtImpl.insertAttributeMatrix(attrmatrix,Integer.parseInt(uid));
		attribComboValue=docMgmtImpl.getAttributeValueByAttrId(attrmatrix.getAttrId());
		return SUCCESS;
	}

	public Vector getAttribComboValue() {
		return attribComboValue;
	}

	public void setAttribComboValue(Vector attribComboValue) {
		this.attribComboValue = attribComboValue;
	}
}
