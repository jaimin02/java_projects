package com.docmgmt.struts.actions.template;

import com.docmgmt.dto.DTOTemplateUserRightsMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.webinterface.beans.GenerateTree;


import java.util.*;


public class SaveTemplateRightsAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		String templateId=ActionContext.getContext().getSession().get("templateId").toString();
		String selectedUsers [] = multiCheckUser;
		for(int i=0;i<selectedUsers.length;i++)
		{
		
			
			DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(selectedUsers[i]));
			Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
			DTOTemplateUserRightsMst objTemplateUserRightsMst = new DTOTemplateUserRightsMst();
			
			objTemplateUserRightsMst.setTemplateId(templateId);
			objTemplateUserRightsMst.setUserCode(Integer.parseInt(selectedUsers[i]));
			objTemplateUserRightsMst.setUserGroupCode(userGroupCode.intValue());
			objTemplateUserRightsMst.setNodeId(nodeId);
			objTemplateUserRightsMst.setStageId(stageId);
			
			if (rights.equalsIgnoreCase("readonly")) {
				objTemplateUserRightsMst.setCanAddFlag('N');
				objTemplateUserRightsMst.setCanDeleteFlag('N');
				objTemplateUserRightsMst.setCanEditFlag('N');
			}else {
				objTemplateUserRightsMst.setCanAddFlag('Y');
				objTemplateUserRightsMst.setCanDeleteFlag('Y');
				objTemplateUserRightsMst.setCanEditFlag('Y');
			}
		
			docMgmtImpl.updateTemplateUserRights(objTemplateUserRightsMst);
			objTemplateUserRightsMst = null;
		}
		
		DTOUserMst userMst1 = new DTOUserMst();
		int userGroupCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		userMst1.setUserCode(userCode);
		userMst1.setUserGroupCode(userGroupCode);
		Vector assignTemplateDetails= docMgmtImpl.getTemplateUserDetail(templateId,userMst1);

		GenerateTree tree=new GenerateTree();
		tree.deleteTree(templateId);
		return SUCCESS;
	}
	
	public int nodeId;
	public String nodeName;
	public String[] multiCheckUser;
	public String rights;
	public int stageId;
	
	public int getStageId() {
		return stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public String[] getMultiCheckUser() {
		return multiCheckUser;
	}

	public void setMultiCheckUser(String[] multiCheckUser) {
		this.multiCheckUser = multiCheckUser;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}


	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}
