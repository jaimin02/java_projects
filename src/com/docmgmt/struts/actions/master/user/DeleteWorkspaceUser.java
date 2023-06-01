package com.docmgmt.struts.actions.master.user;

import java.util.Map;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteWorkspaceUser extends ActionSupport
{
	public String workspaceid;
	public String userId;
	public String userGroupId;
	public Vector workspaceUserHistory;
	public Vector deletedWorkspaceUserHistory;
	public Vector allWorkspaceUserHistory;
	public String remark;
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		Map session = ActionContext.getContext().getSession();
		//String sessionDefaultWorkSpaceId = session.get("defaultWorkspace").toString();
		int sessionUserCode=Integer.parseInt(session.get("userid").toString());
		
		DTOWorkSpaceUserMst objtmp=new DTOWorkSpaceUserMst();
		objtmp.setWorkSpaceId(workspaceid);
		objtmp.setUserCode(Integer.parseInt(userId));
		objtmp.setUserGroupCode(Integer.parseInt(userGroupId));
		
		DTOWorkSpaceUserMst obj =docMgmtImpl.getUserDetail(objtmp);
		
		obj.setWorkSpaceId(workspaceid);
		obj.setUserCode(Integer.parseInt(userId));
		obj.setUserGroupCode(Integer.parseInt(userGroupId));
		obj.setRemark(remark);
		
		obj.setModifyBy(sessionUserCode);
		
		String workspaceId=obj.getWorkSpaceId();
		int userCode,usrGroupCode;
		
		userCode = obj.getUserCode();
		usrGroupCode = obj.getUserGroupCode();
		
		Vector stageIds= docMgmtImpl.checkWorkspacemsthistory(workspaceId,userCode,usrGroupCode);
		
		String stageDesc="";
		
		//ArrayList<DTOWorkSpaceUserRightsMst> userRightsListForhistory = new ArrayList<DTOWorkSpaceUserRightsMst>();
	
		String stage;
		int stageid;
		for(int i=0;i<stageIds.size();i++){
			stageid=(int) stageIds.get(i);
			stage= docMgmtImpl.getStageDesc(stageid);
			stageDesc+=stage+",";
		}
		System.out.println("StageId="+stageDesc);
		  if (stageDesc != null && stageDesc.length() > 0){
			  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
		  }
		  obj.setStages(stageDesc);
		/*else{
			dtoWorkSpaceUserMst.setStages("-");
		}*/
		 int [] usrcode = new int[1];
		 usrcode[0]=obj.getUserCode(); 
		 obj.setMode(3);
		docMgmtImpl.insertUpdateUsertoWorkspaceHistory(obj,usrcode);
		
		docMgmtImpl.inactiveuserfromproject(obj);
		docMgmtImpl.DeletemodulespecRights(obj);
		
		addActionMessage(obj.getUsername() +" is removed from the project.");
		
		//if user deleted the default project of the current user then it will be deleted in session.
		/*if(sessionDefaultWorkSpaceId.equalsIgnoreCase(workspaceid) && sessionUserCode == Integer.parseInt(userId))
		{	
			session.put("defaultWorkspace","");
		}*/
		
		return SUCCESS;
	}
	
	public String workspaceUserHistory(){
        
		workspaceUserHistory = docMgmtImpl.getWorkspaceUserHistory(workspaceid,userId,userGroupId);
		return SUCCESS;
		
	}
	
	public String deletedWorkspaceUserHistory(){
		deletedWorkspaceUserHistory = docMgmtImpl.getDeletedWorkspaceUserHistory(workspaceid);
		return SUCCESS;	
	}
	
public String allWorkspaceUserHistory(){
		deletedWorkspaceUserHistory = docMgmtImpl.getAllWorkspaceUserHistory(workspaceid);
		return SUCCESS;	
	}
	
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}
	public String getWorkspaceid() {
		return workspaceid;
	}
	public void setWorkspaceid(String workspaceid) {
		this.workspaceid = workspaceid;
	}
	
	
	
}
