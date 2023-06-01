package com.docmgmt.struts.actions.master.user;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddUserGroupToProject extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String workspaceid;
	public String userGroupYN;
	public int userGroupCode;
	public int userWiseGroupCode;
	public int[] userCode;
	
	public int [] stageIds;
	public String fromDt;
	public String toDt;
	public String accessrights;
	public String remark;
	
	public String unlimited;
	public String modulespecrights;
	public String roleCode;
	@Override
	public String execute()
	{
		insertUsertoWorkspaceAndUserrights();
		return SUCCESS;
	}

	
	public  void  insertUsertoWorkspaceAndUserrights()
	{
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		
		if(modulespecrights== null)
		{
			modulespecrights="N";
		}
		
		if(!modulespecrights.equals("Y"))
		{
		if(stageIds == null)
				return;
		}
		
		DTOWorkSpaceUserMst dtoWorkSpaceUserMst = new DTOWorkSpaceUserMst();
		DTOWorkSpaceUserMst deletewsurmdata = new DTOWorkSpaceUserMst();
		DTOWorkSpaceUserRightsMst dtowsurm = new DTOWorkSpaceUserRightsMst();
		
		dtoWorkSpaceUserMst.setWorkSpaceId(workspaceid);
		dtowsurm.setWorkSpaceId(workspaceid);
		deletewsurmdata.setWorkSpaceId(workspaceid);
		deletewsurmdata.setUserGroupCode(userWiseGroupCode);
		
		
		if(userGroupYN.equals("usergroup"))
		{
			Vector<DTOUserMst> workspaceUserDetails=docMgmtImpl.getuserDetailsByUserGrp(userGroupCode);
			
			userCode=new int[workspaceUserDetails.size()];
			for(int i =0;i<workspaceUserDetails.size();i++)
			{
				DTOUserMst userMstDTO = workspaceUserDetails.get(i);
				userCode[i] = userMstDTO.getUserCode();
			}
			dtoWorkSpaceUserMst.setUserGroupCode(userGroupCode);
			dtowsurm.setUserGroupCode(userGroupCode);
		}
		else
		{
			dtoWorkSpaceUserMst.setUserGroupCode(userWiseGroupCode);
			dtowsurm.setUserGroupCode(userWiseGroupCode);
		}
		
		
		dtoWorkSpaceUserMst.setAdminFlag('N');
		Timestamp ts = new Timestamp(new Date().getTime());
		dtoWorkSpaceUserMst.setLastAccessedOn(ts);
		dtoWorkSpaceUserMst.setRemark(remark);
		int ucd=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dtoWorkSpaceUserMst.setModifyBy(ucd);
		dtoWorkSpaceUserMst.setModifyOn(ts);
		
		DateFormat dateFormat = new SimpleDateFormat("d-MMM-yy");
		
		try {
			if(unlimited !=null)
			{
			//Date date = new Date();
			//System.out.println(dateFormat.format(date));
			Calendar cal = Calendar.getInstance();
			Date today = cal.getTime();
			dtoWorkSpaceUserMst.setFromDt(today);
			cal.add(Calendar.YEAR, 50); // to get previous year add -1
			Date nextYear = cal.getTime();
			dtoWorkSpaceUserMst.setToDt(nextYear);
			}
			else
			{
			Date fromdt = dateFormat.parse(fromDt);
			dtoWorkSpaceUserMst.setFromDt(fromdt);
			Date todt =  dateFormat.parse(toDt);		
			todt.setHours(23);
			todt.setMinutes(59);
			System.out.println(todt);
			dtoWorkSpaceUserMst.setToDt(todt);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(modulespecrights.equals("Y"))
		{
			dtoWorkSpaceUserMst.setRightsType("modulewise");
			deletewsurmdata.setRightsType("modulewise");
		}
		else
		{
			dtoWorkSpaceUserMst.setRightsType("projectwise");
			deletewsurmdata.setRightsType("projectwise");
		}	
		//////insert usertoworkspace/////////
		
		
		//////////////////////
		
		//if(accessrights.equals("read"))
		//{
		//	dtowsurm.setCanReadFlag('Y');
			//dtowsurm.setAdvancedRights("N");
			//dtowsurm.setCanDeleteFlag('N');
			//dtowsurm.setCanAddFlag('N');
			//dtowsurm.setCanEditFlag('N');
		//}else{
		
		// to migrate from project level to module wise rights first delete old rights
		if(modulespecrights.equals("Y"))
		{
			for(int userId=0;userId<userCode.length; userId++)
			{
				deletewsurmdata.setUserCode(userCode[userId]);
				docMgmtImpl.DeleteProjectlevelRights(deletewsurmdata);
			}
		}
		
		// To change entry in workspaceusermst		
		
		//docMgmtImpl.insertUpdateUsertoWorkspace(dtoWorkSpaceUserMst, userCode);
		docMgmtImpl.insertUpdateUsertoWorkspaceForAttachUser(dtoWorkSpaceUserMst, userCode);
		String stageDesc="";
		
		if(!modulespecrights.equals("Y"))
		{
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsListForhistory = new ArrayList<DTOWorkSpaceUserRightsMst>();
		if(userWiseGroupCode==5){
			stageIds = new int[1];
			stageIds[0] = 20;
		}
		
		for (int iUserCode = 0; iUserCode < userCode.length; iUserCode++) {
			for (int iStageIds = 0;iStageIds < stageIds.length; iStageIds++ ) {
				DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMstClone = dtowsurm.clone();
				dtoWorkSpaceUserRightsMstClone.setUserCode(userCode[iUserCode]);
				dtoWorkSpaceUserRightsMstClone.setStageId(stageIds[iStageIds]);
				userRightsListForhistory.add(dtoWorkSpaceUserRightsMstClone);
			}
		}
		String stage;
		int stageid;
		if(userRightsListForhistory.size()>0){
			for(int i=0;i<stageIds.length;i++){
				stageid=userRightsListForhistory.get(i).getStageId();
				stage= docMgmtImpl.getStageDesc(stageid);
				stageDesc+=stage+",";
			}
		}
		  System.out.println("StageId="+stageDesc);
		  if (stageDesc != null && stageDesc.length() > 0){
			  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
		  }
		  dtoWorkSpaceUserMst.setStages(stageDesc);
		}
		else{
			dtoWorkSpaceUserMst.setStages("-");
		}
		dtoWorkSpaceUserMst.setMode(1);
		//docMgmtImpl.insertUpdateUsertoWorkspaceHistory(dtoWorkSpaceUserMst, userCode);
		docMgmtImpl.insertUpdateUsertoWorkspaceHistoryForAttachUser(dtoWorkSpaceUserMst, userCode);
		
		// if Projectlevel rights insert rights in workspaceuserrightsmst
		if(!modulespecrights.equals("Y"))
		{
			
		
			
			dtowsurm.setCanReadFlag('Y');
			dtowsurm.setAdvancedRights("Y");
			dtowsurm.setCanDeleteFlag('Y');
			dtowsurm.setCanAddFlag('Y');
			dtowsurm.setCanEditFlag('Y');
		//}
		dtowsurm.setRemark(remark);
		dtowsurm.setModifyBy(ucd);
		dtowsurm.setModifyOn(ts);
		dtowsurm.setStatusIndi('N');
		
		
			for (int iUserCode = 0; iUserCode < userCode.length; iUserCode++) {
				for (int iStageIds = 0;iStageIds < stageIds.length; iStageIds++ ) {
					DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMstClone = dtowsurm.clone();
					dtoWorkSpaceUserRightsMstClone.setUserCode(userCode[iUserCode]);
					dtoWorkSpaceUserRightsMstClone.setStageId(stageIds[iStageIds]);
					userRightsList.add(dtoWorkSpaceUserRightsMstClone);
					//docMgmtImpl.insertuserrights(dtowsurm, userCode);
				}
			}
	
			docMgmtImpl.insertMultipleUserRights(userRightsList);
			
		}
		
		
		if(userGroupYN.equals("usergroup"))
		{
			addActionMessage(" Selected User Group is added to the project successfully.");
		}
		else
		{
			addActionMessage("User(s) added to the project successfully.");
		}
		

	}
	
	
	//////////////////////////////setter -getters//////////          
	public String getWorkspaceid() {
		return workspaceid;
	}


	public void setWorkspaceid(String workspaceid) {
		this.workspaceid = workspaceid;
	}


	public String getUserGroupYN() {
		return userGroupYN;
	}


	public void setUserGroupYN(String userGroupYN) {
		this.userGroupYN = userGroupYN;
	}


	public int getUserGroupCode() {
		return userGroupCode;
	}


	public void setUserGroupCode(int userGroupCode) {
		this.userGroupCode = userGroupCode;
	}


	public int getUserWiseGroupCode() {
		return userWiseGroupCode;
	}


	public void setUserWiseGroupCode(int userWiseGroupCode) {
		this.userWiseGroupCode = userWiseGroupCode;
	}


	public int[] getUserCode() {
		return userCode;
	}


	public void setUserCode(int[] userCode) {
		this.userCode = userCode;
	}


	public String getFromDt() {
		return fromDt;
	}


	public void setFromDt(String fromDt) {
		this.fromDt = fromDt;
	}


	public String getToDt() {
		return toDt;
	}


	public void setToDt(String toDt) {
		this.toDt = toDt;
	}

	public String getAccessrights() {
		return accessrights;
	}


	public void setAccessrights(String accessrights) {
		this.accessrights = accessrights;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}

}
