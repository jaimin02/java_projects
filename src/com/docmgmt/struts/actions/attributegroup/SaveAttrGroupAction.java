package com.docmgmt.struts.actions.attributegroup;



import com.docmgmt.dto.DTOAttributeGroupMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;



public class SaveAttrGroupAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		DTOAttributeGroupMst dto = new DTOAttributeGroupMst();
		dto.setAttrGroupName(attributeName);
    	dto.setProjectCode(projectCode);
    	dto.setModifyBy(userId);
    	docMgmtImpl.InsertAttributeGroupMst(dto, 1);
		
    	return SUCCESS;
	}	
		
	public String attributeName;
	public String projectCode;

	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
}
