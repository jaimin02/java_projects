package com.docmgmt.struts.actions.attributegroup;



import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;


public class ViewAttrGroupAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	@Override
	public String execute()
	{
		getAttrValues= docMgmtImpl.getAttributeGroupMatrixValues(attrGroupId);
		return SUCCESS;
	}	
		
	public String attrGroupId;
	public Vector getAttrValues;
	
	public Vector getGetAttrValues() {
		return getAttrValues;
	}
	public void setGetAttrValues(Vector getAttrValues) {
		this.getAttrValues = getAttrValues;
	}
	public String getAttrGroupId() {
		return attrGroupId;
	}
	public void setAttrGroupId(String attrGroupId) {
		this.attrGroupId = attrGroupId;
	}
	
}
