package com.docmgmt.struts.actions.master.attribute;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOImportedXLSMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class AttributeAction extends ActionSupport implements Preparable{
	
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public ArrayList<DTOImportedXLSMst> xlsTableList;
	
	public String getAddAttributeForm()
	{
		xlsTableList = docMgmtImpl.getXLSMstList();
		return SUCCESS;
	}
	
	public void prepare() throws Exception {
		usertypelist=docMgmtImpl.getAllUserType();
		attrForIndi=docMgmtImpl.getAllAtrrForIndi();
		attributelist=docMgmtImpl.getAllAttrib();
			
	}

	
	/**********************************************/
	private Vector attrForIndi;
	private Vector attributelist;
	private Vector usertypelist;

	
	public Vector getUsertypelist() {
		return usertypelist;
	}


	public void setUsertypelist(Vector usertypelist) {
		this.usertypelist = usertypelist;
	}


	public Vector getAttrForIndi() {
		return attrForIndi;
	}


	public void setAttrForIndi(Vector attrForIndi) {
		this.attrForIndi = attrForIndi;
	}


	public Vector getAttributelist() {
		return attributelist;
	}


	public void setAttributelist(Vector attributelist) {
		this.attributelist = attributelist;
	}
	
	/**********************************************/
	
	
	
}
