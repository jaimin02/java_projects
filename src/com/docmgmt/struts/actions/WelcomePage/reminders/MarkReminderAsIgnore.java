package com.docmgmt.struts.actions.WelcomePage.reminders;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class MarkReminderAsIgnore extends ActionSupport
{
	public String vWorkspaceId;
	public int iNodeId;
	public int iAttrId;
	public int userCode;
	public String ignoreUpto;
	public String htmlContent;
	public DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute() throws Exception 
	{
		boolean result=docMgmtImpl.markReminderAsIgnore(vWorkspaceId,iNodeId,iAttrId,userCode,ignoreUpto);
		if (result)
		{
			htmlContent="<b>Ignored</b>";
			return SUCCESS;
		}
		else
		{
			htmlContent="<b><font color='red'>Error!</font></b>";
			return "input";
		}
	}
}