package com.docmgmt.struts.actions.attributegroup;



import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Vector;


public class ShowAttributesAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	@Override
	public String execute()
	{
		getAttrDetail = docMgmtImpl.getAttrForAttrGroupBySpecifiedAttrType(attrForIndiId);
		return SUCCESS;
	}	
	
	public String attrForIndiId;
	public Vector getAttrDetail;
	
	public Vector getGetAttrDetail() {
		return getAttrDetail;
	}

	public void setGetAttrDetail(Vector getAttrDetail) {
		this.getAttrDetail = getAttrDetail;
	}

	public String getAttrForIndiId() {
		return attrForIndiId;
	}

	public void setAttrForIndiId(String attrForIndiId) {
		this.attrForIndiId = attrForIndiId;
	}
	
}
