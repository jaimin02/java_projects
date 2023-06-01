package com.docmgmt.struts.actions.attributegroup;



import com.docmgmt.dto.DTOAttributeGroupMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;



public class UpdateAttrGroupAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		DTOAttributeGroupMst dto = new DTOAttributeGroupMst();
		dto.setAttrGroupId(attrGroupId);
		dto.setAttrGroupName(attrGroupName);
    	dto.setProjectCode(projectCode);
    	dto.setModifyBy(userId);
    	dto.setStatusIndi('E');
    
    	docMgmtImpl.InsertAttributeGroupMst(dto, 2);
		return SUCCESS;
	}	
		
	public String attrGroupName;
	public String projectCode;
	public String attrGroupId;
	
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
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
}
