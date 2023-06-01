package com.docmgmt.struts.actions.attributegroup;



import com.docmgmt.dto.DTOAttributeGroupMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Vector;


public class DeleteAttrGroupAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	@Override
	public String execute()
	{
		
		DTOAttributeGroupMst dto  = docMgmtImpl.getAttributeGroupById(attrGroupId);
		if(statusIndi.equals("N")|| statusIndi.equals("E"))
		{
			dto.setStatusIndi('D');
		}
		else
			{
			dto.setStatusIndi('E');
			}
		dto.setAttrGroupId(attrGroupId);
		docMgmtImpl.InsertAttributeGroupMst(dto,2);
		return SUCCESS;
	}	
	
	public String attrGroupId;
	public String statusIndi;
	public Vector getAllAttributeGroup;
	
	
	public Vector getGetAllAttributeGroup() {
		return getAllAttributeGroup;
	}
	public void setGetAllAttributeGroup(Vector getAllAttributeGroup) {
		this.getAllAttributeGroup = getAllAttributeGroup;
	}
	public String getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}
	public String getAttrGroupId() {
		return attrGroupId;
	}
	public void setAttrGroupId(String attrGroupId) {
		this.attrGroupId = attrGroupId;
	}
	
	
}
