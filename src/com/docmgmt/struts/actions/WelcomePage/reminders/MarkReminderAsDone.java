package com.docmgmt.struts.actions.WelcomePage.reminders;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class MarkReminderAsDone extends ActionSupport
{
	public String vWorkspaceId;
	public int iNodeId;
	public int iAttrId;
	public int userCode;
	public String htmlContent;
	public DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute() throws Exception 
	{
		boolean result=docMgmtImpl.markReminderAsDone(vWorkspaceId,iNodeId,iAttrId,userCode);
		if (result)
		{
			htmlContent="<b>Done</b>";
			return SUCCESS;
		}
		else
		{
			htmlContent="<b><font color='red'>Error!</font></b>";
			return "input";
		}
	}
}