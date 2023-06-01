package com.docmgmt.struts.actions.template;

import com.docmgmt.dto.DTOTemplateNodeDetail;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;


public class TemplateAssignRightsAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		templateId = ActionContext.getContext().getSession().get("templateId").toString();
		int userGroupCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		nodeDetail = docMgmtImpl.getTemplateNodeDetailByNodeId(templateId, nodeId);
		DTOTemplateNodeDetail dto =(DTOTemplateNodeDetail)nodeDetail.elementAt(0);		
		nodeName = dto.getNodeDisplayName();
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserCode(userCode);
		userMst.setUserGroupCode(userGroupCode);
	
		assignTemplateRightsDetails = docMgmtImpl.getTemplateUserDetail(templateId, userMst);
		getStageDetail = docMgmtImpl.getStageDetail();
		getUserRightsDetail = docMgmtImpl.getTemplateUserRightsDetail(templateId, nodeId);
		
		return SUCCESS;
	}
	
	public int nodeId;
	public Vector assignTemplateRightsDetails;
	public Vector nodeDetail;
	public String nodeName;
	public Vector getStageDetail;
	public Vector getUserRightsDetail;
	public String templateId;
	
	

	public String getTemplateId() {
		return templateId;
	}


	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}


	public Vector getGetUserRightsDetail() {
		return getUserRightsDetail;
	}


	public void setGetUserRightsDetail(Vector getUserRightsDetail) {
		this.getUserRightsDetail = getUserRightsDetail;
	}


	public Vector getGetStageDetail() {
		return getStageDetail;
	}


	public void setGetStageDetail(Vector getStageDetail) {
		this.getStageDetail = getStageDetail;
	}


	public int getNodeId() {
		return nodeId;
	}


	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}



	public Vector getAssignTemplateRightsDetails() {
		return assignTemplateRightsDetails;
	}


	public void setAssignTemplateRightsDetails(Vector assignTemplateRightsDetails) {
		this.assignTemplateRightsDetails = assignTemplateRightsDetails;
	}


	public Vector getNodeDetail() {
		return nodeDetail;
	}


	public void setNodeDetail(Vector nodeDetail) {
		this.nodeDetail = nodeDetail;
	}


	public String getNodeName() {
		return nodeName;
	}


	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}
