/**
 * 
 */
package com.docmgmt.struts.actions.master.user;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOUserLoginFailureDetails;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author nagesh
 *
 */
public class UnblockUserAction extends ActionSupport 
{
	public String userName;
	public String htmlContent;
	public int usercode;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public int userCodeToUnblock;
	
	@Override
	public String execute() throws Exception 
	{
		ArrayList<DTOUserLoginFailureDetails> arrDto=DocMgmtImpl.getFailureUserDetail(userName);
		
		Vector<DTOUserMst> userDetail=new Vector<DTOUserMst>();
		
		usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		//userDetail = docMgmtImpl.getUserDetail(userName);
		userDetail = docMgmtImpl.getUserDetailByUserCode(userName,userCodeToUnblock);
		
		if (arrDto.size()<=0)
		{
			htmlContent="<font color='red'>Error !!!</font>";
			return SUCCESS;
		}
		DTOUserLoginFailureDetails dtoUserLoginFailureDetails=arrDto.get(0);
		char bFlag = dtoUserLoginFailureDetails.getBlockedFlag();
		if (bFlag=='B')
		{
			dtoUserLoginFailureDetails.setBlockedFlag('A');
			dtoUserLoginFailureDetails.setUserName(userDetail.get(0).getUserName());
			dtoUserLoginFailureDetails.setUserCode(userDetail.get(0).getUserCode());
			dtoUserLoginFailureDetails.setUserGroupCode(userDetail.get(0).getUserGroupCode());
			dtoUserLoginFailureDetails.setUserTypeCode(userDetail.get(0).getUserTypeCode());
			dtoUserLoginFailureDetails.setLoginName(userDetail.get(0).getLoginName());
			dtoUserLoginFailureDetails.setModifyBy(usercode);
			DTOUserMst dtoUser = docMgmtImpl.getUserByCode(usercode);
			dtoUserLoginFailureDetails.setRoleCode(dtoUser.getRoleCode());
			dtoUserLoginFailureDetails.setLocationCode(dtoUser.getLocationCode());
			//dtoUserLoginFailureDetails.setLocationCode(userDetail.get(0).getLocationCode());
			//if (DocMgmtImpl.insertFailureUserDtls(dtoUserLoginFailureDetails,3))
			if (DocMgmtImpl.insertFailureUserDtlsForBlockUser(dtoUserLoginFailureDetails,3))	
			htmlContent="Unblocked";
			else
				htmlContent="<font color='red'>Error !!!</font>";
		}
		else
		{
			htmlContent="<font color='red'>Error !!!</font>";
			return SUCCESS;
		}
		//Thread.sleep(3000);
		return SUCCESS;
	}
}
