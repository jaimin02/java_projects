package com.docmgmt.struts.actions.workspace;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOForumDtl;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowAllComments extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public ArrayList<DTOForumDtl> sentComments;
	public String workspaceId;
	
	public String nodeId;
	public String confirmFlag;
	public String showSentCommentDocPath;
	public String showCommentDocPath;
	
	Vector comments=new Vector();
	
	@Override
	public String execute()
	{
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		int usergrpcode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int usercode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		showCommentDocPath=propertyInfo.getValue("CommentDoc");
		
		DTOWorkSpaceNodeDetail objWorkSpaceNodeDetail = new DTOWorkSpaceNodeDetail();
		objWorkSpaceNodeDetail.setWorkspaceId(workspaceID);
		objWorkSpaceNodeDetail.setNodeId(new Integer(nodeId).intValue());
		objWorkSpaceNodeDetail.setUserCode(usercode);
		
		objWorkSpaceNodeDetail.setUserGroupCode(usergrpcode);
		
		comments=docMgmtImpl.showNodeComments(objWorkSpaceNodeDetail);
		
		return SUCCESS;
	}

	public String showSentComments()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		showSentCommentDocPath=propertyInfo.getValue("CommentDoc");
		sentComments=docMgmtImpl.getSentComments(userId, workspaceId,Integer.parseInt(nodeId));
		
		//System.out.println("allComments"+allComments.size());
		return SUCCESS;
		
	}
	
	public String showCommentHistory()
	{
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		showSentCommentDocPath=propertyInfo.getValue("CommentDoc");
		sentComments=docMgmtImpl.getAllComments(workspaceId,Integer.parseInt(nodeId));
		
		//System.out.println("allComments"+allComments.size());
		return SUCCESS;
		
	}
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Vector getComments() {
		return comments;
	}

	public void setComments(Vector comments) {
		this.comments = comments;
	}

	public String getConfirmFlag() {
		return confirmFlag;
	}

	public void setConfirmFlag(String confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

}
