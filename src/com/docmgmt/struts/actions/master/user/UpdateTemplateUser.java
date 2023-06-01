package com.docmgmt.struts.actions.master.user;



import java.sql.Timestamp;
import java.util.Date;

import com.docmgmt.dto.DTOTemplateUserMst;
import com.docmgmt.dto.DTOTemplateUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateTemplateUser extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		int uid=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		Date frmDate = new Date(fromDt);
		Date toDate = new Date(toDt);
		
		
		
		DTOTemplateUserMst objtmp=new DTOTemplateUserMst();
		objtmp.setTemplateId(templateId);
		objtmp.setUserCode(userId);
		objtmp.setUserGroupCode(userGroupCode);
		
		
		
		DTOTemplateUserMst dto =docMgmtImpl.getUserDetails(objtmp);
		objtmp.setTemplateId(templateId);
    	dto.setUserGroupCode(userGroupCode);
    	dto.setUserCode(userId);
    	dto.setModifyBy(uid);
    	dto.setFromDt(frmDate);
    	dto.setToDt(toDate);
    	
    	DTOTemplateUserRightsMst dtowsurm = new DTOTemplateUserRightsMst();
    	dtowsurm.setTemplateId(templateId);
    	Timestamp ts = new Timestamp(new Date().getTime());
    	dtowsurm.setUserGroupCode(userGroupCode);
    	dtowsurm.setUserCode(userId);
	
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
		dtowsurm.setModifyBy(uid);
		dtowsurm.setModifyOn(ts);
		dtowsurm.setStatusIndi('E');
		
				
		int nodeid = docMgmtImpl.getmaxTemplateNodeId(templateId);
		int i;
		for(i=1; i <= nodeid; i++)
		{	
			dtowsurm.setNodeId(i);
			docMgmtImpl.updateUserRightsForTemplate(dtowsurm);
		}
		int [] usercd={userId};
		
		docMgmtImpl.insertUpdateUsertoTemplate(dto,usercd);
    	
    	return SUCCESS;
	}
	
	
	
	public String toDt;
	public String fromDt;
	public int userGroupCode;
	public int userId;
	public String templateId;
	public String accessrights;
	public String remark;


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

	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getAccessrights() {
		return accessrights;
	}
	public void setAccessrights(String accessrights) {
		this.accessrights = accessrights;
	}
	
	

}
