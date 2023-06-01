package com.docmgmt.struts.actions.stf;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;
import java.util.Vector;



public class AddSTFNodeAction extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		stfnode = docMgmtImpl.getAllSTFNodes();
		return SUCCESS;
	}
	
	public Vector stfnode;


	public Vector getStfnode() {
		return stfnode;
	}

	public void setStfnode(Vector stfnode) {
		this.stfnode = stfnode;
	}
	
}