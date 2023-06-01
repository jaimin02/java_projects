package com.docmgmt.struts.actions.workspace;

import com.opensymphony.xwork2.ActionSupport;

import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.DeleteAllTrees;
import com.opensymphony.xwork2.ActionContext;

public class CopyWorkspaceNodeAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		docMgmtImpl.CopyAndPasteWorkSpace(srcWsId, Integer.parseInt(srcnodeId), destWsId, Integer.parseInt(destnodeId), userId, status);
		DeleteAllTrees objDeleteTrees = new DeleteAllTrees();  
		objDeleteTrees.DeleteAllGeneratedTrees(srcWsId);
		objDeleteTrees.DeleteAllGeneratedTrees(destWsId);
		//	addActionMessage("Operation Successfully Done...");
		return SUCCESS;
	}
	
	public String srcnodeId;
	public String destnodeId;
	public String destWsId;
	public String srcWsId;
	public String status;

	public String getSrcnodeId() {
		return srcnodeId;
	}
	public void setSrcnodeId(String srcnodeId) {
		this.srcnodeId = srcnodeId;
	}
	public String getDestnodeId() {
		return destnodeId;
	}
	public void setDestnodeId(String destnodeId) {
		this.destnodeId = destnodeId;
	}
	public String getDestWsId() {
		return destWsId;
	}
	public void setDestWsId(String destWsId) {
		this.destWsId = destWsId;
	}
	public String getSrcWsId() {
		return srcWsId;
	}
	public void setSrcWsId(String srcWsId) {
		this.srcWsId = srcWsId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
	
	


