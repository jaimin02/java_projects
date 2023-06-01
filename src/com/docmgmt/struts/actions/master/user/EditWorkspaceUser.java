package com.docmgmt.struts.actions.master.user;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTORoleMst;
import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class EditWorkspaceUser extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public Vector<DTOStageMst> getStageDetail;
	
	public ArrayList<DTOStageMst> userStages;
	
	private DTOWorkSpaceUserMst mst=new DTOWorkSpaceUserMst(); 
	
	public String workSpaceId;
	public String userId;
	public String userGroupId;
	public String RightsType;
	public Vector<DTORoleMst> roleDtl=new Vector<DTORoleMst>();
	
	
	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
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

	public DTOWorkSpaceUserMst getMst() {
		return mst;
	}

	public void setMst(DTOWorkSpaceUserMst mst) {
		this.mst = mst;
	}

	@Override
	public String execute()
	{
	
		DTOWorkSpaceUserMst temp=new DTOWorkSpaceUserMst();
		temp.setWorkSpaceId(workSpaceId);
		temp.setUserCode(Integer.parseInt(userId));
		temp.setUserGroupCode(Integer.parseInt(userGroupId));
		
		mst=docMgmtImpl.getUserDetail(temp);
		RightsType=mst.getRightsType();
		//getStageDetail = docMgmtImpl.getStageDetail();
		getStageDetail = docMgmtImpl.getStageDetailCSV();
		roleDtl = docMgmtImpl.getRoleDtl();
		userStages = docMgmtImpl.getUserStageDetail(workSpaceId, Integer.parseInt(userId));
		
		ArrayList<Integer> assignedStageIds = docMgmtImpl.getStageIdsRightsWise(workSpaceId,1);
		
		if(!assignedStageIds.contains(30)){
			for(int i = 0;i<getStageDetail.size();i++){
				if(getStageDetail.get(i).getStageId() == 30){
					getStageDetail.remove(i);
					break;
				}
			}
			
		}
		
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail;
		ArrayList<DTOWorkSpaceNodeHistory> WorkspaceUserHistory;
		for(int k=0;k<userStages.size();k++){
		WorkspaceUserHistory=docMgmtImpl.showNodeHistoryForWs(workSpaceId,Integer.parseInt(userId),userStages.get(k).getStageId());
		if(WorkspaceUserHistory.size()>0){
			userStages.get(k).setUserFlag('Y');
			}
			else{
				userStages.get(k).setUserFlag('N');
			}
		
		}
		
		return SUCCESS;
	}

	

	public Vector<DTOStageMst> getGetStageDetail() {
		return getStageDetail;
	}

	public void setGetStageDetail(Vector<DTOStageMst> getStageDetail) {
		this.getStageDetail = getStageDetail;
	}

	public DocMgmtImpl getDocMgmtImpl() {
		return docMgmtImpl;
	}

	public void setDocMgmtImpl(DocMgmtImpl docMgmtImpl) {
		this.docMgmtImpl = docMgmtImpl;
	}

	

	
	
	
	
}
