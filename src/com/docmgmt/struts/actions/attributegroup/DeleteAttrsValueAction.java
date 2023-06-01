package com.docmgmt.struts.actions.attributegroup;




import com.docmgmt.dto.DTOAttributeGroupMatrix;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;



public class DeleteAttrsValueAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		DTOAttributeGroupMatrix dto = new DTOAttributeGroupMatrix();
		dto.setId(Id);
		dto.setModifyBy(userId);
		if(statusIndi.equals("N") || statusIndi.equals("E"))
		   	dto.setStatusIndi('D');
		else if(statusIndi.equals("D"))
		   	dto.setStatusIndi('E');
		
		docMgmtImpl.insertIntoAttributeGroupMatrix(dto,2);   
		return SUCCESS;
	}	
	
	public int Id;
	public String statusIndi;
	
	
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}
	
	
}
