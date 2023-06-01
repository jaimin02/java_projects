package com.docmgmt.struts.actions.master.user;

import java.util.Vector;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ResetUserPassword extends ActionSupport
{

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public int userCode;
	public String remark;
	public String loginName;
@Override
public String execute()
{
	
	DTOUserMst dto=new DTOUserMst();
	
	dto=docMgmtImpl.getUserByCode(userCode);
	dto.setLoginPass(dto.getLoginName());
	dto.setModifyBy(Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString()));
	dto.setRemark(remark);
	docMgmtImpl.UserMstOp(dto, 2, false);
	
	String loginName = dto.getLoginName();
	Vector<DTOUserMst> getUserDetail = docMgmtImpl.getUserDetailByLoginNameId(loginName);
	
	for(int k=0;k<getUserDetail.size();k++){
		if(getUserDetail.get(k).getUserCode() != userCode){
		dto=new DTOUserMst();
		dto=docMgmtImpl.getUserByCode(getUserDetail.get(k).getUserCode());
		dto.setLoginPass(getUserDetail.get(k).getLoginName());
		dto.setModifyBy(Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString()));
		dto.setRemark(remark);
		docMgmtImpl.UserMstOp(dto, 2, false);
		}
	}
	
	return SUCCESS;
}

public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public int getUserCode() {
	return userCode;
}
public void setUserCode(int userCode) {
	this.userCode = userCode;
}

}
