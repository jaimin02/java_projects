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
 * @author Administrator
 *
 */

class CheckPermissionTask implements ITask
{
	ArrayList<String> permList;
	String prefix;
	
	public CheckPermissionTask(ArrayList<String> permList,String prefix) 
	{
		this.permList=permList;
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
				boolean read=file.canRead();
				boolean write=file.canWrite();
				boolean hidden=file.isHidden();
				if (read && permList.get(0).equals("no"))
				{
					errorList.add(new EctdError(EctdErrorType.ECTD_ERROR,prefix + file.getName() + " is readable.",file.getAbsolutePath()));
				}
				if (!read && permList.get(0).equals("yes"))
				{
					errorList.add(new EctdError(EctdErrorType.ECTD_ERROR,prefix + file.getName() + " is not readable.",file.getAbsolutePath()));
				}
				if (write && permList.get(1).equals("no"))
				{
					errorList.add(new EctdError(EctdErrorType.ECTD_ERROR,prefix + file.getName() + " is writable.",file.getAbsolutePath()));
				}
				if (!write && permList.get(1).equals("yes"))
				{
					errorList.add(new EctdError(EctdErrorType.ECTD_ERROR,prefix + file.getName() + " is not writable.",file.getAbsolutePath()));
				}
				if (hidden && permList.get(2).equals("no"))
				{
					errorList.add(new EctdError(EctdErrorType.ECTD_ERROR,prefix + file.getName() + " is hidden.",file.getAbsolutePath()));
				}
				if (!hidden && permList.get(2).equals("yes"))
				{
					errorList.add(new EctdError(EctdErrorType.ECTD_ERROR,prefix + file.getName() + " is not hidden.",file.getAbsolutePath()));
				}
			}
		}
	}	
}

class CheckExtensionsTask implements ITask
{
	String[] extensions;
	String prefix;
	
	public CheckExtensionsTask(String[] extensions,String prefix) 
	{
		this.extensions=extensions;
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
				String filename=file.getName();
				boolean found=false;
				for (int i=0;i<extensions.length;i++)
				{
					if (filename.matches(extensions[i]))
					{
						found=true;
						break;
					}
				}
				if (!found)
					errorList.add(new EctdError(EctdErrorType.ECTD_ERROR,prefix + filename + " has invalid extension.",file.getAbsolutePath()));
			}
		}
	}
	
}

class CheckPathLengthTask implements ITask
{
	int limit;
	String prefix;
	
	public CheckPathLengthTask(int limit,String prefix) 
	{
		this.limit=limit;
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
				String filepath=file.getAbsolutePath();
				if (filepath.length()>limit)
					errorList.add(new EctdError(EctdErrorType.ECTD_ERROR,prefix + file.getName() + " has invalid path length.",file.getAbsolutePath()));
			}
		}
	}	
}

class CheckFileSizeTask implements ITask
{
	int limit;
	String prefix;
	
	public CheckFileSizeTask(int limit,String prefix) 
	{
		this.limit=limit;
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
				long fileSize = file.length();
				if (fileSize > (limit*1024*1024))
					errorList.add(new EctdError(EctdErrorType.ECTD_ERROR,prefix + file.getName() + " has invalid file size.",file.getAbsolutePath()));
			}
		}
	}	
}

public class FileValidations extends Validation {

	/* (non-Javadoc)
	 * @see Validations.Validation#validate(org.w3c.dom.Node, java.io.File, java.util.ArrayList)
	 */
	@Override
	public void validate(Node tagData, File dossier, ArrayList<EctdError> errorList) 
	{		
		
		
		System.out.println("Inside Validator--:"+tagData.getNodeName());
		if (!init(tagData,dossier,errorList))
			return;		

		ArrayList<ITask> fileTaskList=new ArrayList<ITask>();
		
		Node extList=getChildNode(tagData, "extensions");
		String exts[]=getNodeAttribute(extList, "value").split(",");
		if (exts!=null && exts.length>0)
		{			
			String str=getNodeAttribute(extList,"message");
			fileTaskList.add(new CheckExtensionsTask(exts,(str==null || str.equals(""))?prefix:str));
		}
		
		ArrayList<Node> pathlength=getChildNodes(tagData, "pathlength");
		int limit=1000;
		for (int i=0;i<pathlength.size();i++)
		{
			Node node=pathlength.get(i);
			String region=getNodeAttribute(node, "type");			
			String str=getNodeAttribute(node, "limit");			
			if (region!=null && region.equals("") && isRegionValid(region, dossier))
			{
				limit=Integer.parseInt(str);
				break;
			}
			else
			{
				limit=Integer.parseInt(str);
			}
		}
		limit+=(dossier.getParentFile().getParentFile().getAbsolutePath().length());
		if(limit < 1000)
		{
			fileTaskList.add(new CheckPathLengthTask(limit,prefix));
		}
				
		ArrayList<Node> permNodeList=getChildNodes(getChildNode(tagData,"permissions"),"permission");
		ArrayList<String> permValueList=new ArrayList<String>();
		permValueList.add(getNodeAttribute(permNodeList.get(0),"value"));
		permValueList.add(getNodeAttribute(permNodeList.get(1),"value"));
		permValueList.add(getNodeAttribute(permNodeList.get(2),"value"));
		
		if (permValueList!=null && permValueList.size()>0 && permValueList.size()==3)
		{
			String str=getNodeAttribute(getChildNode(tagData,"permissions"),"message");
			fileTaskList.add(new CheckPermissionTask(permValueList,(str==null || str.equals(""))?prefix:str));
		}
		
		ArrayList<Node> filesizeList = getChildNodes(tagData, "filesize");
		int filesize=100;
		for (int i=0;i<filesizeList.size();i++)
		{
			Node node=filesizeList.get(i);
			String region=getNodeAttribute(node, "type");			
			String str=getNodeAttribute(node, "limit");			
			if (region!=null && region.equals("") && isRegionValid(region, dossier))
			{
				filesize=Integer.parseInt(str);
				break;
			}
			else
				filesize=Integer.parseInt(str);
		}
		fileTaskList.add(new CheckFileSizeTask(filesize,prefix));
		
		findAll(dossier,fileTaskList,null);
	}

}
