package com.docmgmt.struts.actions.master.client;



import com.docmgmt.dto.DTOClientMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveClientAction extends ActionSupport {//implements Preparable{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		DTOClientMst dto = new DTOClientMst();
		dto.setRemark("New");
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setModifyBy(userId);
	    dto.setClientName(clientName.trim());
	    
	    boolean clientExist = docMgmtImpl.clientNameExist(clientName.trim());
	    if(!clientExist)
	        docMgmtImpl.ClientMstOp(dto, 1,false);
		else{
			addActionError("Client Name Already Exists!");
			return INPUT;
		}
	    return SUCCESS;
	}
	
	public String UpdateClient()
	{
		DTOClientMst dto = new DTOClientMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setRemark(remark);
		dto.setClientCode(clientCode);
		dto.setClientName(clientName.trim());
		dto.setModifyBy(userId);
	    boolean clientExist = docMgmtImpl.clientNameExist(clientName.trim());
	    if(!clientExist)
	    {
	    	docMgmtImpl.ClientMstOp(dto, 2,false);
	    }
	   	return SUCCESS;
	}
	
	public String clientName;
	public String clientCode;
	public String statusIndi;
	public String remark;

	public String getStatusIndi() {
		return statusIndi;
	}

	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	
	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	
}