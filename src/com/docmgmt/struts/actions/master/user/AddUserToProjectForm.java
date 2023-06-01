package com.docmgmt.struts.actions.master.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddUserToProjectForm extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String workspaceid;
	public int lastAdminCode;
	
	
	private Date now;
	
	 public Date getNow() {
	        return now;
	    }

	public Vector<DTOWorkSpaceUserMst> WorkspaceUserDetail;
	public Vector workspaceUserGroupsDetails;
	public Vector<DTOWorkSpaceUserMst> WorkspaceUserDetailList;
	public ArrayList<DTOWorkSpaceNodeHistory> WorkspaceUserHistory;
	public Vector<DTOStageMst> getStageDetail;
	public String userTypeNam;
	public String htmlContent= "";
	public int usergroupId;
	public String userTypeName;
	public String countForUser;
	
	public String getWorkspaceid() {
		return workspaceid;
	}

	public void setWorkspaceid(String workspaceid) {
		this.workspaceid = workspaceid;
	}

	
	@Override
	public String execute()
	{
		Calendar cal = Calendar.getInstance();
        now = cal.getTime();
        int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
        int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
        userTypeNam=ActionContext.getContext().getSession().get("usertypename").toString();
        String FosunChanges=PropertyInfo.getPropInfo().getValue("FosunCustomization");
        if(FosunChanges.equalsIgnoreCase("yes")){
			String clientCodeGroup=docMgmtImpl.getUserGroupClientCode(userGroupCode);
			workspaceUserGroupsDetails=docMgmtImpl.getAllUserGroupsByClientCode(clientCodeGroup);
		}
		else{
			workspaceUserGroupsDetails=docMgmtImpl.getAllUserGroups();
			/*if(userTypeNam.equalsIgnoreCase("BD")){
				workspaceUserGroupsDetails=docMgmtImpl.getAllUserGroupsByDeptCode("");
			}else{
				workspaceUserGroupsDetails=docMgmtImpl.getAllUserGroupsByDeptCode(docMgmtImpl.getDeptCode(userId,userGroupCode));
			}*/
			
			
			
		}
		WorkspaceUserDetail=docMgmtImpl.getAllWorkspaceUserDetail(workspaceid);
		WorkspaceUserDetailList=docMgmtImpl.getWorkspaceUserDetailList(workspaceid);
		
		for(int j = 0; j<WorkspaceUserDetailList.size(); j++){
			WorkspaceUserHistory=docMgmtImpl.showNodeHistory(workspaceid,WorkspaceUserDetailList.get(j).getUserCode());
			if(WorkspaceUserHistory.size()>0){
			WorkspaceUserDetailList.get(j).setUserFlag('Y');
			WorkspaceUserDetailList.set(j, WorkspaceUserDetailList.get(j));
			}
			else{
				WorkspaceUserDetailList.get(j).setUserFlag('N');
				WorkspaceUserDetailList.set(j, WorkspaceUserDetailList.get(j));
				//countForUser="false";
			}
		}
		//getStageDetail = docMgmtImpl.getStageDetail();
		getStageDetail = docMgmtImpl.getStageDetailCSV();
		
		ArrayList<Integer> assignedStageIds = docMgmtImpl.getStageIdsRightsWise(workspaceid,1);
		if(!assignedStageIds.contains(30)){
			for(int i = 0;i<getStageDetail.size();i++){
				if(getStageDetail.get(i).getStageId() == 30){
					getStageDetail.remove(i);
					break;
				}
			}
			
		}
		
		//this loop is to count the no. of admins attached to the workspace
		int adminCount=lastAdminCode=0;
		for (int usrIndex=0;usrIndex<WorkspaceUserDetailList.size();usrIndex++)
		{
			DTOWorkSpaceUserMst dto=(DTOWorkSpaceUserMst)WorkspaceUserDetailList.get(usrIndex);
			if (docMgmtImpl.getUserTypeName(dto.getUserCode()).equals("WA"))
			{
				adminCount++;
				lastAdminCode=dto.getUserCode();
			}
		}
		if (adminCount>1)
			lastAdminCode=-1;	
		return SUCCESS;
	}
	
	public String checkUserType(){
		userTypeName = docMgmtImpl.getUserType(usergroupId);
		
		htmlContent = userTypeName;
		return "html";
	}
	


	public Vector getWorkspaceUserDetail() {
		return WorkspaceUserDetail;
	}

	public void setWorkspaceUserDetail(Vector workspaceUserDetail) {
		WorkspaceUserDetail = workspaceUserDetail;
	}

	public Vector getWorkspaceUserGroupsDetails() {
		return workspaceUserGroupsDetails;
	}

	public void setWorkspaceUserGroupsDetails(Vector workspaceUserGroupsDetails) {
		this.workspaceUserGroupsDetails = workspaceUserGroupsDetails;
	}

	public Vector getWorkspaceUserDetailList() {
		return WorkspaceUserDetailList;
	}

	public void setWorkspaceUserDetailList(Vector workspaceUserDetailList) {
		WorkspaceUserDetailList = workspaceUserDetailList;
	}

	public Vector<DTOStageMst> getGetStageDetail() {
		return getStageDetail;
	}

	public void setGetStageDetail(Vector<DTOStageMst> getStageDetail) {
		this.getStageDetail = getStageDetail;
	}

	
}
