package com.docmgmt.struts.actions.master.user;

import java.util.Vector;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteUser extends ActionSupport
{

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public int userCode;
	public String statusIndi;		
	public String message="ok";
	public String remark;
	public String loginName;
	
@Override
public String execute()
{
	int userid= Integer.parseInt(ActionContext.getContext().getSession()
			.get("userid").toString());
	if (statusIndi.trim().charAt(0)=='D')
	{
		Vector userdata=docMgmtImpl.getAllUserDetail();
		Vector temp=new Vector();
		for (int i=0;i<userdata.size();i++)
		{
			if (((DTOUserMst)userdata.get(i)).getStatusIndi()!='D')
				temp.add(userdata.get(i));
		}
		userdata=temp;
		KnetProperties knetProperties=KnetProperties.getPropInfo();
		String string=knetProperties.getValue("MaxUsers");
		int maxUsers = Integer.parseInt((string==null || string.equals(""))?"-1":string);
		if (maxUsers!=-1 && (userdata.size()-1)>=maxUsers)
		{			
			message="error";			
			return SUCCESS;
		}
	}
	DTOUserMst dto=docMgmtImpl.getUserByCode(userCode);
	
	dto.setStatusIndi(statusIndi.trim().charAt(0));
	dto.setRemark(remark);
	dto.setModifyBy(userid);
	docMgmtImpl.UserMstOp(dto, 2, true);
	
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
public String getStatusIndi() {
	return statusIndi;
}
public void setStatusIndi(String statusIndi) {
	this.statusIndi = statusIndi;
}
}
