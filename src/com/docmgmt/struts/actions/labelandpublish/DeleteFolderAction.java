package com.docmgmt.struts.actions.labelandpublish;

import java.io.File;

import com.opensymphony.xwork2.ActionSupport;

public class DeleteFolderAction extends ActionSupport
{
	public String folderPath;
	public String htmlContent;
	
	@Override
	public String execute()
	{
		String error="<font style='color:red;'>Error</font>";
		String success="<font style='color:red;'>Deleted</font>";
		htmlContent=error;		
		if (folderPath!=null && !folderPath.equals(""))
		{
			File folder=new File(folderPath);
			if (folder.exists())
			{
				if (FileManager.deleteDir(folder)==true)
					htmlContent=success;				
			}
		}
		return SUCCESS;
	}
	
}
