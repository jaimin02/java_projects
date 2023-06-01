package com.docmgmt.struts.actions.workspace;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class WorkspaceNodeRightsAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
			if(nodeId !=null)
		{
			leafNode = docMgmtImpl.isLeafNodes(workspaceID,Integer.parseInt(nodeId));
		}
		return SUCCESS;
	}

	public String nodeId;
	public String workspaceID;
	public int leafNode;
	
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
	public int getLeafNode() {
		return leafNode;
	}
	public void setLeafNode(int leafNode) {
		this.leafNode = leafNode;
	}
	
}
