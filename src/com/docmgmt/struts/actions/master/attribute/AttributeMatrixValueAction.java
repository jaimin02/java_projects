package com.docmgmt.struts.actions.master.attribute;


import java.util.Vector;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.server.db.*;

import com.opensymphony.xwork2.ActionSupport;

public class AttributeMatrixValueAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	public Vector attribComboValue;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String iattrid;
	public DTOAttributeMst attribdetail; 

	public DTOAttributeMst getAttribdetail() {
		return attribdetail;
	}

	public void setAttribdetail(DTOAttributeMst attribdetail) {
		this.attribdetail = attribdetail;
	}

	
	public Vector getAttribComboValue() {
		return attribComboValue;
	}

	public void setAttribComboValue(Vector attribComboValue) {
		this.attribComboValue = attribComboValue;
	}

	public String getIattrid() {
		return iattrid;
	}

	public void setIattrid(String iattrid) {
		this.iattrid = iattrid;
	}
	
	
	
	@Override
	public String execute()
	{
		
		int iattr=Integer.parseInt(iattrid);
		attribComboValue=docMgmtImpl.getAttributeValueByAttrId(iattr);
		attribdetail=docMgmtImpl.getAttribute(iattr);
		return SUCCESS;
		
	}
	
}
