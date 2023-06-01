package com.docmgmt.struts.actions.master.user;



import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateWorkspaceUser extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String RightsType;
	
	@Override
	public String execute()
	{
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		int uid=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTOWorkSpaceUserRightsMst dtowsurm = new DTOWorkSpaceUserRightsMst();
		
		Date frmDate = new Date(fromDt);
		Date toDate = new Date(toDt);
				
		DTOWorkSpaceUserMst objtmp=new DTOWorkSpaceUserMst();
		objtmp.setWorkSpaceId(workSpaceId);
		objtmp.setUserCode(userId);
		objtmp.setUserGroupCode(userGroupCode);
		
		
		
		DTOWorkSpaceUserMst dto =docMgmtImpl.getUserDetail(objtmp);
		dto.setWorkSpaceId(workSpaceId);
    	dto.setUserGroupCode(userGroupCode);
    	dto.setUserCode(userId);
    	dto.setModifyBy(uid);
    	dto.setFromDt(frmDate);
    	toDate.setHours(23);
    	toDate.setMinutes(59);
    	dto.setToDt(toDate);
    	dto.setRemark(remark);
    	
    if(!RightsType.equals("modulewise"))
    {
    	//DTOWorkSpaceUserRightsMst dtowsurm = new DTOWorkSpaceUserRightsMst();
    	dtowsurm.setWorkSpaceId(workSpaceId);
    	Timestamp ts = new Timestamp(new Date().getTime());
    	dtowsurm.setUserGroupCode(userGroupCode);
    	dtowsurm.setUserCode(userId);
	
//    	if(accessrights.equals("read"))
//		{
//			dtowsurm.setCanReadFlag('Y');
//			dtowsurm.setAdvancedRights("N");
//			dtowsurm.setCanDeleteFlag('N');
//			dtowsurm.setCanAddFlag('N');
//			dtowsurm.setCanEditFlag('N');
//		}else{
			dtowsurm.setCanReadFlag('Y');
			dtowsurm.setAdvancedRights("Y");
			dtowsurm.setCanDeleteFlag('Y');
			dtowsurm.setCanAddFlag('Y');
			dtowsurm.setCanEditFlag('Y');
//		}
		dtowsurm.setRemark(remark);
		dtowsurm.setModifyBy(uid);
		dtowsurm.setModifyOn(ts);
		dtowsurm.setStatusIndi('E');
		
		for (int iStageIds = 0;iStageIds < stageIds.length; iStageIds++ ) {
			DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMstClone = dtowsurm.clone();
			dtoWorkSpaceUserRightsMstClone.setStageId(stageIds[iStageIds]);
			userRightsList.add(dtoWorkSpaceUserRightsMstClone);
			//docMgmtImpl.insertuserrights(dtowsurm, usercd);
		}
		docMgmtImpl.insertMultipleUserRights(userRightsList);
    }
		/*int nodeid = docMgmtImpl.getmaxNodeId(workSpaceId);
		int i;
		for(i=1; i <= nodeid; i++)
		{	
				dtowsurm.setNodeId(i);
				docMgmtImpl.updateUserRightsForWorkSpace(dtowsurm);
		}*/
    String stageDesc="";
    if(!RightsType.equals("modulewise"))
    {
    	ArrayList<DTOWorkSpaceUserRightsMst> userRightsListForhistory = new ArrayList<DTOWorkSpaceUserRightsMst>();
   
    	for (int iStageIds = 0;iStageIds < stageIds.length; iStageIds++ ) {
    		DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMstClone = dtowsurm.clone();
    		dtoWorkSpaceUserRightsMstClone.setStageId(stageIds[iStageIds]);
    		userRightsListForhistory.add(dtoWorkSpaceUserRightsMstClone);
		
    		}
    		String stage;
    		int stageid;
    		for(int i=0;i<stageIds.length;i++){
    			stageid=userRightsListForhistory.get(i).getStageId();
    			stage= docMgmtImpl.getStageDesc(stageid);
    			stageDesc+=stage+",";
    			//stageDesc+=",";
    		}
    		System.out.println("StageId="+stageDesc);
    		if (stageDesc != null && stageDesc.length() > 0){
    			stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
    		}
    		dto.setStages(stageDesc);
	}
	else{
		dto.setStages("-");
	}    
    	dto.setRoleCode(roleCode);
      	dto.setMode(2);
      	/*DTOUserMst getUserDetail = new DTOUserMst();
      	getUserDetail = docMgmtImpl.getUserByCode(userId);
      	getUserDetail.setRoleCode(roleCode);
      	getUserDetail.setModifyBy(uid);
      	getUserDetail.setRemark(remark);
      	docMgmtImpl.updateRole(getUserDetail);*/
		docMgmtImpl.insertUpdateUsertoWorkspace(dto,new int []{userId});
		docMgmtImpl.insertUpdateUsertoWorkspaceHistory(dto, new int []{userId});
		
    	return SUCCESS;
	}
	
	
	public String toDt;
	public String fromDt;
	public int userGroupCode;
	public int userId;
	public String workSpaceId;
	public String accessrights;
	public int [] stageIds;
	public String remark;
	public String roleCode;
	


	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getToDt() {
		return toDt;
	}
	public void setToDt(String toDt) {
		this.toDt = toDt;
	}
	public String getFromDt() {
		return fromDt;
	}
	public void setFromDt(String fromDt) {
		this.fromDt = fromDt;
	}
	public int getUserGroupCode() {
		return userGroupCode;
	}
	public void setUserGroupCode(int userGroupCode) {
		this.userGroupCode = userGroupCode;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public String getAccessrights() {
		return accessrights;
	}
	public void setAccessrights(String accessrights) {
		this.accessrights = accessrights;
	}
	
	

}
