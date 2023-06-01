package com.docmgmt.struts.actions.master.attribute;

import com.docmgmt.dto.DTOAttributeValueMatrix;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EditAttributeMatrixValue extends ActionSupport
{
	public String iattrid;
	public String vattrvalue;
	public String newValue;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
		
	public String getIattrid() {
		return iattrid;
	}


	public void setIattrid(String iattrid) {
		this.iattrid = iattrid;
	}


	public String getVattrvalue() {
		return vattrvalue;
	}


	public void setVattrvalue(String vattrvalue) {
		this.vattrvalue = vattrvalue;
	}


	public String getNewValue() {
		return newValue;
	}


	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}


	@Override
	public String execute() throws Exception 
	{		
		DTOAttributeValueMatrix dto=new DTOAttributeValueMatrix();
		String uid=ActionContext.getContext().getSession().get("userid").toString();		
		dto.setAttrId(Integer.parseInt(iattrid));
		dto.setAttrValue(newValue);
		dto.setAttrOldValue(vattrvalue);
		docMgmtImpl.editAttributeMatrix(dto, Integer.parseInt(uid));
		return SUCCESS;
	}
}