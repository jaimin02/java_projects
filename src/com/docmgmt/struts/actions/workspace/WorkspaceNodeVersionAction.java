package com.docmgmt.struts.actions.workspace;



import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.server.db.*;
import java.util.Vector;

public class WorkspaceNodeVersionAction extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		DTOWorkSpaceNodeVersionHistory dto = new DTOWorkSpaceNodeVersionHistory();
		dto.setWorkspaceId(workspaceId);
		dto.setNodeId(nodeId);
		workspaceNodeVersionDetail = docMgmtImpl.getWorkSpaceNodeVersionDetail(dto);
	
		return SUCCESS;
	}
	
	public int nodeId;
	public String workspaceId;
	public Vector workspaceNodeVersionDetail;


	public Vector getWorkspaceNodeVersionDetail() {
		return workspaceNodeVersionDetail;
	}
	public void setWorkspaceNodeVersionDetail(Vector workspaceNodeVersionDetail) {
		this.workspaceNodeVersionDetail = workspaceNodeVersionDetail;
	}

	
}
