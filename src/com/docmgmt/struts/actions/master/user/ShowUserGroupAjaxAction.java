package com.docmgmt.struts.actions.master.user;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class ShowUserGroupAjaxAction extends ActionSupport
{
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String userGroupId;
	public String htmlContent;
	Vector <DTOUserMst> workspaceuserdtl=new Vector();
	DTOUserMst dto = new DTOUserMst();
	public Vector<DTOWorkSpaceUserMst> WorkspaceUserDetailList;
	public int count;
	public String workspaceId;
	
	@Override
	public String execute()
	{
		//workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		workspaceuserdtl=docMgmtImpl.getuserDetailsByUserGrp(Integer.parseInt(userGroupId));
		//workspaceuserdtl=docMgmtImpl.getuserDetailsByusermst(Integer.parseInt(userGroupId), workspaceId);
		return SUCCESS;
	}
	public String getUserDtl(){
	
		htmlContent = "";
		workspaceuserdtl=docMgmtImpl.getuserDetailsByUserGrp(Integer.parseInt(userGroupId));
		WorkspaceUserDetailList=docMgmtImpl.getWorkspaceUserDetailList(workspaceId);
		for(int i=0;i<workspaceuserdtl.size();i++){
			for(int j=0;j<WorkspaceUserDetailList.size();j++){
				if(workspaceuserdtl.size()>0){
					if(WorkspaceUserDetailList.size()!=0){
						//if(workspaceuserdtl.get(i).getUserName().equals(WorkspaceUserDetailList.get(j).getUsername())){
						//if(workspaceuserdtl.get(i).getUserName().equals(userName)){
						if(workspaceuserdtl.get(i).getUserName().equals(WorkspaceUserDetailList.get(j).getUsername()) &&
								workspaceuserdtl.get(i).getUserGroupCode() == WorkspaceUserDetailList.get(j).getUserGroupCode()){
							count++;
							workspaceuserdtl.remove(i);
							if(i==0){
								continue;
							}else{
								i--;}
				//workspaceuserDetails.add(workspaceuserdtl.get(i));
							continue;
							}
						}
					  }
				else{
					DTOUserMst dto=new DTOUserMst();
					dto.setUserCode(WorkspaceUserDetailList.get(j).getUserCode());
					dto.setUserName(WorkspaceUserDetailList.get(j).getUsername());
					workspaceuserdtl.add(dto);
				}
					}
				}
		ArrayList<DTOUserMst> arraylist = new ArrayList<DTOUserMst>(workspaceuserdtl);
		
		for (DTOUserMst userList : arraylist) {
			if(!htmlContent.equals("")){
				htmlContent += ","; 
			}
			htmlContent += userList.getUserCode()+"::"+userList.getUserName();
		}
		
		return "html";
	}

	public Vector getWorkspaceuserdtl() {
		return workspaceuserdtl;
	}

	public void setWorkspaceuserdtl(Vector workspaceuserdtl) {
		this.workspaceuserdtl = workspaceuserdtl;
	}

	public String getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	

}
