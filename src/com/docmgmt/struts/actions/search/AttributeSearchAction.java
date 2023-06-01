package com.docmgmt.struts.actions.search;

import java.util.Vector;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class AttributeSearchAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	@Override
	public String execute()
	{
		attrDetail=new Vector();
		 	DTOAttributeMst dtoAttrMst = new DTOAttributeMst();
		    dtoAttrMst.setAttrId(0);
		    dtoAttrMst.setAttrName("Select Attributes");
		    dtoAttrMst.setAttrForIndiId(" ");
		    dtoAttrMst.setAttrValue(" ");
		    attrDetail.addElement(dtoAttrMst);
		attrDetail = docMgmtImpl.getAttrDetailForSearch();
		return SUCCESS;
	}
	
	public Vector attrDetail;
	
	public Vector getAttrDetail() {
		return attrDetail;
	}

	public void setAttrDetail(Vector attrDetail) {
		this.attrDetail = attrDetail;
	}
	
}

