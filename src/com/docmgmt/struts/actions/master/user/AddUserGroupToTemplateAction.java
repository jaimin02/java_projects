package com.docmgmt.struts.actions.master.user;

import java.util.*;
import java.sql.Timestamp;
import java.util.Vector;

import com.docmgmt.dto.DTOTemplateUserMst;
import com.docmgmt.dto.DTOTemplateUserRightsMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.GenerateTree;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddUserGroupToTemplateAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String templateId;
	public String userGroupYN;
	public int userGroupCode;
	public int userWiseGroupCode;
	public int[] userCode;
	public int stageId;
	public String fromDt;
	public String toDt;
	public String accessrights;
	public String remark;
	
	
	@Override
	public String execute()
	{
		insertUsertoWorkspaceAndUserrights();
		return SUCCESS;
	}

	
	public  void  insertUsertoWorkspaceAndUserrights()
	{
		
		DTOTemplateUserMst dto = new DTOTemplateUserMst();
		DTOTemplateUserRightsMst dtowsurm = new DTOTemplateUserRightsMst();
		
		dto.setTemplateId(templateId);
		dtowsurm.setStageId(stageId);
		dtowsurm.setTemplateId(templateId);
		
		if(userGroupYN.equals("usergroup"))
		{
			Vector workspaceUserDetails=docMgmtImpl.getuserDetailsByUserGrp(userGroupCode);
			
			userCode=new int[workspaceUserDetails.size()];
			for(int i =0;i<workspaceUserDetails.size();i++)
			{
				DTOUserMst userMstDTO = new DTOUserMst(); 
				userMstDTO = (DTOUserMst)workspaceUserDetails.get(i);
				userCode[i] = userMstDTO.getUserCode();
				
			}
		
			dto.setUserGroupCode(userGroupCode);
			dtowsurm.setUserGroupCode(userGroupCode);
		}
		else
		{
			dto.setUserGroupCode(userWiseGroupCode);
			dtowsurm.setUserGroupCode(userWiseGroupCode);
		}
		
		
		dto.setAdminFlag('N');
		Timestamp ts = new Timestamp(new Date().getTime());
		dto.setLastAccessedOn(ts);
		dto.setRemark(remark);
		int ucd=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setModifyBy(ucd);
		dto.setModifyOn(ts);
		
		Date fromdt = new Date(fromDt);
		
		dto.setFromDt(fromdt);
		
		Date todt = new Date(toDt);		
		
		dto.setToDt(todt);
		
	
		
		//////insert usertoworkspace/////////
		
		docMgmtImpl.insertUpdateUsertoTemplate(dto, userCode);
		
		//////////////////////
		
		if(accessrights.equals("read"))
		{
			dtowsurm.setCanReadFlag('Y');
			dtowsurm.setAdvancedRights("N");
			dtowsurm.setCanDeleteFlag('N');
			dtowsurm.setCanAddFlag('N');
			dtowsurm.setCanEditFlag('N');
		}else{
			dtowsurm.setCanReadFlag('Y');
			dtowsurm.setAdvancedRights("Y");
			dtowsurm.setCanDeleteFlag('Y');
			dtowsurm.setCanAddFlag('Y');
			dtowsurm.setCanEditFlag('Y');
		}
		dtowsurm.setRemark(remark);
		dtowsurm.setModifyBy(ucd);
		dtowsurm.setModifyOn(ts);
		dtowsurm.setStatusIndi('N');
		
		
		///insert into userright master////////
		docMgmtImpl.insertTemplateUserRights(dtowsurm, userCode);
		/////////////////////////
		
		if(userGroupYN.equals("usergroup"))
		{
			addActionMessage(" Selected User Group is added to the template successfully.");
		}
		else
		{
			addActionMessage("User(s) added to the template successfully.");
		}
		
		GenerateTree tree=new GenerateTree();
		tree.deleteTree(templateId);
		

	}
	
	
	
	//////////////////////////////setter -getters//////////          
	

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


	public int getStageId() {
		return stageId;
	}


	public void setStageId(int stageId) {
		this.stageId = stageId;
	}


	public String getTemplateId() {
		return templateId;
	}


	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
}
