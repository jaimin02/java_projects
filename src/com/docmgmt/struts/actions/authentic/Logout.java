package com.docmgmt.struts.actions.authentic;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;

/**
 * Invokes logout action
 * @author Hardik Shah
 *
 */
public class Logout extends AuthenticSupport
{
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	

	/**
	 * invalidate session and release all connection
	 * @author Hardik Shah
	 */
	     
	@Override
	public String execute() 
	 {
		String loginId = ActionContext.getContext().getSession().get("loginId").toString();
		

		DTOUserMst dtouser=new DTOUserMst();
		int userId=Integer.parseInt((ActionContext.getContext().getSession().get("userid").toString()));
		//there is No Use of dtoUser for Update in master
		DocMgmtImpl.UserLoginDetailsOp(userId, 3);
		docMgmtImpl.LoginHistoryMstOp(dtouser,2);
		
		((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).invalidate();
			((org.apache.struts2.dispatcher.SessionMap) ActionContext.getContext().getSession()).clear();
			
			ActionContext.getContext().getSession().clear();
			
			session=null;
			
			return SUCCESS;
      }
}
