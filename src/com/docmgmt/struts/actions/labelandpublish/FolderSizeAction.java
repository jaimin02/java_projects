package com.docmgmt.struts.actions.labelandpublish;

import com.docmgmt.dto.DTOFolderSize;
import com.docmgmt.server.webinterface.services.size.GetFolderSize;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author nagesh
 */
public class FolderSizeAction extends ActionSupport
{
	public String folderPath;	
	public String htmlContent="";
	public DTOFolderSize folderSize;
	
    @Override
	public String execute()
    {
        GetFolderSize gfs=new GetFolderSize();
        gfs.getFolderSize(folderPath);
        folderSize=gfs.getFinalOutput();        
        //htmlContent+=folderSize.getSizeBytes() + " B<br>";
        //htmlContent+=folderSize.getSizeKBytes() + " KB<br>";
        if (folderSize.getSizeMBytes() < 900){
        	htmlContent+=folderSize.getSizeMBytes() + " MB<br>";	
        }else{
        htmlContent+=folderSize.getSizeGBytes() + " GB<br>";
        }
        htmlContent+=folderSize.getNoOfFolders() + " Folders<br>";
        htmlContent+=folderSize.getNoOfFiles() + " Files";
        System.out.println("htmlContent" + htmlContent);
        return SUCCESS;
    }
    
    public static void main(String args[])
    {
    	FolderSizeAction folderSizeAction=new FolderSizeAction();
    	folderSizeAction.folderPath="//90.0.0.15/docmgmtandpub/PublishDestinationFolder";
    	folderSizeAction.execute();    	
    }
}
