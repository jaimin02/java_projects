package com.docmgmt.struts.actions.attributegroup;



import com.docmgmt.dto.DTOAttributeGroupMatrix;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class InsertAttrIntoGroupAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		if(multiCheckAttr.length > 0)
		    {
			DTOAttributeGroupMatrix dto = new DTOAttributeGroupMatrix();
		    	for(int icount=0;icount<multiCheckAttr.length;icount++)
		    	{
		    		dto=new DTOAttributeGroupMatrix();
		    		dto.setAttrGroupId(attrGroupId);
		    		dto.setAttrId(Integer.parseInt(multiCheckAttr[icount]));
		    		dto.setModifyBy(userId);
		    		docMgmtImpl.insertIntoAttributeGroupMatrix(dto,1);
		    		dto=null;
		    	}
		    } 
		return SUCCESS;
	}	
		
	public String attrGroupId;
	public String[] multiCheckAttr;

	public String getAttrGroupId() {
		return attrGroupId;
	}
	public void setAttrGroupId(String attrGroupId) {
		this.attrGroupId = attrGroupId;
	}
	public String[] getMultiCheckAttr() {
		return multiCheckAttr;
	}
	public void setMultiCheckAttr(String[] multiCheckAttr) {
		this.multiCheckAttr = multiCheckAttr;
	}
}
