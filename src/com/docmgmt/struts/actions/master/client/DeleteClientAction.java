package com.docmgmt.struts.actions.master.client;

import com.docmgmt.dto.DTOClientMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteClientAction extends ActionSupport   {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		usercode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("userid").toString());
		DTOClientMst dto =docMgmtImpl.getClientInfo(clientCode);
		dto.setStatusIndi(statusIndi.charAt(0));
		dto.setRemark(remark);
		dto.setModifyBy(usercode);
		docMgmtImpl.ClientMstOp(dto, 2,true);
	    return SUCCESS;
	}	
	
	public String statusIndi;
	public String clientCode;
	public String remark;
	public int usercode;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	
   
	
	
	/**********************************************/
	
	
	
}
