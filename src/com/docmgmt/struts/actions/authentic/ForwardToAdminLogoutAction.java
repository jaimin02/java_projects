package com.docmgmt.struts.actions.authentic;


import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;


public class ForwardToAdminLogoutAction extends AuthenticSupport
{
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
		     
	@Override
	public String execute() 
	 {
		
		
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userTypeName =ActionContext.getContext().getSession().get("usertypename").toString();
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		
		String tempBaseFolder=propertyInfo.getValue("BASE_TEMP_FOLDER");	
		//tempBaseFolderReplaced = tempBaseFolder.replace('/','\\');		
		
		//if(!userTypeName.equalsIgnoreCase("WA") || !userTypeName.equalsIgnoreCase("SU"))
		if(!userTypeName.equalsIgnoreCase("WA"))
		{
			lockedFileDetails = docMgmtImpl.getLockedFileDetailForUser(userCode);
		}
		else
		{
			lockedFileDetails = docMgmtImpl.getLockedFileDetailForAdmin(userCode, userGroupCode);
		}
		return SUCCESS;
     }
	
	
	public Vector lockedFileDetails;
	public String tempBaseFolderReplaced;

	public String getTempBaseFolderReplaced() {
		return tempBaseFolderReplaced;
	}

	public void setTempBaseFolderReplaced(String tempBaseFolderReplaced) {
		this.tempBaseFolderReplaced = tempBaseFolderReplaced;
	}

	public Vector getLockedFileDetails() {
		return lockedFileDetails;
	}

	public void setLockedFileDetails(Vector lockedFileDetails) {
		this.lockedFileDetails = lockedFileDetails;
	}
}
