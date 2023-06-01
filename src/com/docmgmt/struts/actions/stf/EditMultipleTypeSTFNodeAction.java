package com.docmgmt.struts.actions.stf;



import java.util.Vector;

import com.docmgmt.dto.DTOSTFStudyIdentifierMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EditMultipleTypeSTFNodeAction extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		
		return SUCCESS;
	}
	
	public String updateSTFNode() {
	
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		//updating STF Node Name
		DTOWorkSpaceNodeDetail nodeDetail = docMgmtImpl.getNodeDetail(workspaceID, editNodeId).get(0);
		nodeDetail.setNodeDisplayName(multipleTypeSTFNodeDisplayName);
		nodeDetail.setFolderName(multipleTypeSTFNodeName);
		nodeDetail.setModifyBy(userId);
		docMgmtImpl.insertWorkspaceNodeDetail(nodeDetail,2);//edit mode
		
		//updating Site Identifier
		DTOSTFStudyIdentifierMst studyIdentifier = docMgmtImpl.getSTFIdentifierByNodeId(workspaceID, editNodeId).get(0);
		studyIdentifier.setNodeContent(siteId);
		docMgmtImpl.insertIntoSTFStudyIdentifierMst(studyIdentifier, 2);//edit mode
		
		return SUCCESS;
	}	
	
	public int editNodeId;
	public String multipleTypeSTFNodeName;
	public String multipleTypeSTFNodeDisplayName;
	public String siteId;
	public Vector STFCatValues;

	
}
