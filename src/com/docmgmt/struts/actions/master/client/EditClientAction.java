package com.docmgmt.struts.actions.master.client;



import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class EditClientAction extends ActionSupport {//implements Preparable{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		
	    return SUCCESS;
		
		
	}
	
	
	public String clientDetailHistory(){
		
		clientDetailHistory = docMgmtImpl.getClientDetailHistory(clientCode);
		return SUCCESS;
		
	}
	
	
	public Vector clientDetailHistory;
	
	public String clientName;
	public String clientCode;

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