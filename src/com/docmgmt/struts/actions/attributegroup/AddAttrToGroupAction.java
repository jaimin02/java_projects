package com.docmgmt.struts.actions.attributegroup;



import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Vector;


public class AddAttrToGroupAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	@Override
	public String execute()
	{
		attrForIndi = docMgmtImpl.getAttrForIndiDetails();
		return SUCCESS;
	}	
	
	public String attrGroupId;
	public String attrGroupName;
	public Vector attrForIndi;

	public String getAttrGroupId() {
		return attrGroupId;
	}
	public void setAttrGroupId(String attrGroupId) {
		this.attrGroupId = attrGroupId;
	}
	public String getAttrGroupName() {
		return attrGroupName;
	}
	public void setAttrGroupName(String attrGroupName) {
		this.attrGroupName = attrGroupName;
	}
}
