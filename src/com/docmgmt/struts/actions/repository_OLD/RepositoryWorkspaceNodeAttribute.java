package com.docmgmt.struts.actions.repository_OLD;



import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
public class RepositoryWorkspaceNodeAttribute extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		fileExist = docMgmtImpl.copyfilefromsrctodest(srcWsId,srcNodeId);
		attrHistory = docMgmtImpl.getUserDefinedWorkspaceNodeAttrHistory(srcWsId,srcNodeId);
		attrDtl=docMgmtImpl.getAttributeValueByUserType(srcWsId, srcNodeId, "0003");
    	
		return SUCCESS;
	}
	
	public boolean fileExist;
	public int srcNodeId;
	public int destNodeId;
	public String srcWsId;
	public String destWsId;
	public Vector attrDtl;
	public Vector attrHistory;

	public Vector getAttrDtl() {
		return attrDtl;
	}
	public void setAttrDtl(Vector attrDtl) {
		this.attrDtl = attrDtl;
	}
	
	public Vector getAttrHistory() {
		return attrHistory;
	}
	public void setAttrHistory(Vector attrHistory) {
		this.attrHistory = attrHistory;
	}
	public int getSrcNodeId() {
		return srcNodeId;
	}
	public void setSrcNodeId(int srcNodeId) {
		this.srcNodeId = srcNodeId;
	}
	public int getDestNodeId() {
		return destNodeId;
	}
	public void setDestNodeId(int destNodeId) {
		this.destNodeId = destNodeId;
	}
	public String getSrcWsId() {
		return srcWsId;
	}
	public void setSrcWsId(String srcWsId) {
		this.srcWsId = srcWsId;
	}
	public String getDestWsId() {
		return destWsId;
	}
	public void setDestWsId(String destWsId) {
		this.destWsId = destWsId;
	}
	public boolean isFileExist() {
		return fileExist;
	}
	public void setFileExist(boolean fileExist) {
		this.fileExist = fileExist;
	}
	
	
}
	