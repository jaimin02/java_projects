package com.docmgmt.struts.actions.workspace;

import java.util.Vector;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class WorkspaceEctdSetAttribute extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	Vector attrDtl=new Vector();
	
	public String nodeId;
	
	public String requestId;
	
	public String leaf;
	
	public String displayName;
	
	public String workspaceID;
	
	Vector attributevalueList=new Vector();
	
	@Override
	public String execute()
	{
		workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		
		
		if(nodeId !=null)
		{
			attrDtl=docMgmtImpl.getAttributeDetailForDisplay(workspaceID,Integer.parseInt(nodeId));
			attributevalueList=docMgmtImpl.getNodeAttrDetail(workspaceID,Integer.parseInt(nodeId));
		
		}
		
		return SUCCESS;
	}
	
	
	
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Vector getAttrDtl() {
		return attrDtl;
	}

	public void setAttrDtl(Vector attrDtl) {
		this.attrDtl = attrDtl;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getWorkspaceID() {
		return workspaceID;
	}

	public void setWorkspaceID(String workspaceID) {
		this.workspaceID = workspaceID;
	}
	
	

	public Vector getAttributevalueList() {
		return attributevalueList;
	}



	public void setAttributevalueList(Vector attributevalueList) {
		this.attributevalueList = attributevalueList;
	}
}
