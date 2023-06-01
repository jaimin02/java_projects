package com.docmgmt.struts.actions.attributegroup;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Vector;


public class AddAttributeGroupAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	@Override
	public String execute()
	{
		getProjectType = docMgmtImpl.getProjectType();
		getAttrGroupDetail = docMgmtImpl.getAllAttributeGroup();
		return SUCCESS;
	}	
	
	public Vector getProjectType;
	public Vector getAttrGroupDetail;

	public Vector getGetProjectType() {
		return getProjectType;
	}
	public void setGetProjectType(Vector getProjectType) {
		this.getProjectType = getProjectType;
	}
	public Vector getGetAttrGroupDetail() {
		return getAttrGroupDetail;
	}
	public void setGetAttrGroupDetail(Vector getAttrGroupDetail) {
		this.getAttrGroupDetail = getAttrGroupDetail;
	}
	
}
