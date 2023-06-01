/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.docmgmt.struts.actions.ImportProjects.ECTD.validate.General.Validations;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.Node;

import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdError;
import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdErrorType;


/**
 *
 * @author nagesh
 */
public class AllowedExtensionsValidation extends Validation
{    
	void checkFileExtensionAllSubfolders(File checkObj,String[] extension,ArrayList<EctdError> errorList)
    {    	
    	if (checkObj!=null && checkObj.isFile())
    	{    		
    		String filename=checkObj.getName();
    		
    		boolean found=false;
    		for (int i=0;extension!=null && i<extension.length;i++)
    		{    			
    			if (filename.endsWith(extension[i]))
    			{
    				found=true;
    				break;
    			}    			
    		}
    		if (!found)
    			errorList.add(new EctdError(EctdErrorType.ECTD_ERROR,prefix + "Presence of " + checkObj.getName() + " invalid.",checkObj.getAbsolutePath()));
    	}
    	else
    	{
    		for (int i=0;i<checkObj.listFiles().length;i++)
    		{
    			checkFileExtensionAllSubfolders(checkObj.listFiles()[i],extension,errorList);
    		}
    	}
    }

    void checkFileExtensionNoSubfolders(File checkObj,String[] extension,ArrayList<EctdError> errorList)
    {    	
    	for (int i=0;checkObj!=null && i<checkObj.listFiles().length;i++)
    	{
    		if (checkObj.listFiles()[i].isFile())
        	{    		
        		String filename=checkObj.listFiles()[i].getName();
        		boolean found=false;
        		for (int j=0;extension!=null && j<extension.length;j++)
        		{
        			if (filename.endsWith(extension[j]))
        			{
        				found=true;
        				break;
        			}
        		}
        		if (!found)
        			errorList.add(new EctdError(EctdErrorType.ECTD_ERROR,prefix + "Presence of " + checkObj.listFiles()[i].getName() + " invalid.",checkObj.listFiles()[i].getAbsolutePath()));        			
        	}
    	}
    }
        
    @Override
	public void validate(Node tagData, File dossier,ArrayList<EctdError> errorArrList) 
    {                      
    	if (!init(tagData, dossier, errorArrList))
    		return;
        ArrayList<Node> childList=getChildNodes(tagData, "folder");
        for (int i=0;i<childList.size();i++)
        {
        	Node node=childList.get(i);
        	String defaultAttribute=getNodeAttribute(node,"default");        	
        	if (defaultAttribute!=null && !defaultAttribute.equals("") && defaultAttribute.equals("true"))
        	{
        		node=getChildNode(node,"extension");
        		String extensions=(node!=null)?node.getTextContent():null;
        		checkFileExtensionAllSubfolders(dossier,extensions!=null?extensions.split(","):null,errorArrList);
        	}
        	else
        	{
        		String folderName=getNodeAttribute(childList.get(i),"name");
        		File folder=find(dossier,folderName);
        		node=getChildNode(node,"extension");
        		String extensions=(node!=null)?node.getTextContent():null;
        		checkFileExtensionNoSubfolders(folder, extensions!=null?extensions.split(","):null,errorArrList);
        	}
        }
    }
}
