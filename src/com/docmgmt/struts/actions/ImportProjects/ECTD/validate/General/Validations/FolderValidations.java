/**
 * 
 */
package com.docmgmt.struts.actions.ImportProjects.ECTD.validate.General.Validations;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.Node;

import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdError;
import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdErrorType;

/**
 * @author nagesh
 *
 */
public class FolderValidations extends Validation 
{	
	@Override
	public void validate(Node tagData, File dossier, ArrayList<EctdError> errorList) 
	{
		if (!init(tagData,dossier,errorList))
			return;
		
		ArrayList<ITask> folderTaskList=new ArrayList<ITask>();
		Node node=getChildNode(tagData, "checkemptyfolders");
		if (node!=null)
		{		
			String str=getNodeAttribute(node,"message");
			folderTaskList.add(new CheckEmptyFolderTask((str==null || str.equals(""))?prefix:str));
		}				
		findAll(dossier,null,folderTaskList);
	}

}
class CheckEmptyFolderTask implements ITask
{
	String prefix;
	
	public CheckEmptyFolderTask(String prefix)
	{
		this.prefix=prefix;
	}

	public void performTask(ArrayList<EctdError> errorList, Object... argObject) 
	{
		if (argObject.length<=0)
		{
			return;
		}
		else
		{
			if (argObject[0]!=null && argObject[0] instanceof File)
			{
				File file=(File)argObject[0];				
				if (file.isDirectory() && file.list().length==0)
					errorList.add(new EctdError(EctdErrorType.ECTD_ERROR,prefix + file.getName() + " is an empty folder.",file.getAbsolutePath()));
			}
		}	
	}	
}