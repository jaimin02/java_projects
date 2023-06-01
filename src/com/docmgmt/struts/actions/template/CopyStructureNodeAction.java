package com.docmgmt.struts.actions.template;



import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;

import java.util.Date;

public class CopyStructureNodeAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		docMgmtImpl.CopyPasteStructure(srctempId,srcnodeId,desttempId,destnodeId,userCode,new Date());
		return SUCCESS;
	}	
	
	public int srcnodeId;
	public int destnodeId;
	public String srctempId;
	public String desttempId;


	public int getSrcnodeId() {
		return srcnodeId;
	}
	public void setSrcnodeId(int srcnodeId) {
		this.srcnodeId = srcnodeId;
	}
	public int getDestnodeId() {
		return destnodeId;
	}
	public void setDestnodeId(int destnodeId) {
		this.destnodeId = destnodeId;
	}
	public String getSrctempId() {
		return srctempId;
	}
	public void setSrctempId(String srctempId) {
		this.srctempId = srctempId;
	}
	public String getDesttempId() {
		return desttempId;
	}
	public void setDesttempId(String desttempId) {
		this.desttempId = desttempId;
	}
	
	
}
