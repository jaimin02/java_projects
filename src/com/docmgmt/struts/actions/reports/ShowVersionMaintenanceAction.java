package com.docmgmt.struts.actions.reports;

import java.util.Vector;

import com.opensymphony.xwork2.ActionSupport;

import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.server.db.DocMgmtImpl;

public class ShowVersionMaintenanceAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		DTOWorkSpaceNodeVersionHistory wsnodeversionhistory = new DTOWorkSpaceNodeVersionHistory();
		wsnodeversionhistory.setNodeId(nodeId);
		wsnodeversionhistory.setWorkspaceId(workSpaceId);
		workspaceNodeVersionDetail = docMgmtImpl.getWorkSpaceNodeVersionDetail(wsnodeversionhistory);
		
		return SUCCESS;
	}
	
	public String workSpaceId;
	public int nodeId;
	public Vector workspaceNodeVersionDetail;

	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public Vector getWorkspaceNodeVersionDetail() {
		return workspaceNodeVersionDetail;
	}
	public void setWorkspaceNodeVersionDetail(Vector workspaceNodeVersionDetail) {
		this.workspaceNodeVersionDetail = workspaceNodeVersionDetail;
	}
	
	
	
}
	
	


