package com.docmgmt.struts.actions.workspace.PendingSignOff;

import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;



public class UserAcknowledgement extends ActionSupport{
	
	public DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public int userCode;
	public Vector<DTOWorkSpaceUserMst> userAcknowledge;
	public Vector<DTOWorkSpaceUserMst> acknowledgeUpdate;
	public String htmlContent;
	public String workspaceID;
	public String execute()
	{
		DTOWorkSpaceUserMst wsum = new DTOWorkSpaceUserMst();
		 userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		 userAcknowledge = docMgmtImpl.userAcknowledgement(userCode);
	
		 
		 return SUCCESS;
	}
	
	public String checkAcknowledge(){
		
		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int usergrpcode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		dto.setUserCode(userCode);
		dto.setUserGroupCode(usergrpcode);
		dto.setWorkSpaceId(workspaceID);
		String updateUserFlag = docMgmtImpl.updateUserAcknowledgement(dto);				
		
		if(updateUserFlag.equalsIgnoreCase("false")){
			htmlContent="false";
		}
		else{
			htmlContent="true";
		}
		return "html";
	}
}
