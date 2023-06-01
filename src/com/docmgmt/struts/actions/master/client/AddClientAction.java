package com.docmgmt.struts.actions.master.client;



import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class AddClientAction extends ActionSupport implements Preparable {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
/*
		TrackingTableGenerater tt = new TrackingTableGenerater(new File("D:/xml/10-cover/common"),"0081");
		tt.generateXml();
*/		
		
		return SUCCESS;
	}	
	
	
	public void prepare() throws Exception {
		
		clientDetail=docMgmtImpl.getClientDetail();
	}

    public Vector clientDetail;
    public String clientName;

	public Vector getClientDetail() {
		return clientDetail;
	}

	public void setClientDetail(Vector clientDetail) {
		this.clientDetail = clientDetail;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	
	/**********************************************/
	
	
	
}
