package com.docmgmt.struts.actions.master.roleMst;



import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class AddRoleAction extends ActionSupport implements Preparable {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{	
		return SUCCESS;
	}	
	
	
	public void prepare() throws Exception {
		
		roleDetail=docMgmtImpl.getRoleDetail();
	}

    public Vector roleDetail;
    public String roleName;

	public Vector getClientDetail() {
		return roleDetail;
	}

	public void setClientDetail(Vector clientDetail) {
		this.roleDetail = clientDetail;
	}

	public String getClientName() {
		return roleName;
	}

	public void setClientName(String clientName) {
		this.roleName = clientName;
	}
	
	
	/**********************************************/
	
	
	
}
