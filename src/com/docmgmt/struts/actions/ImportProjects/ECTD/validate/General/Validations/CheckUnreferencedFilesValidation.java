package com.docmgmt.struts.actions.ImportProjects.ECTD.validate.General.Validations;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdError;
import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdErrorType;

public class CheckUnreferencedFilesValidation extends Validation {

	void checkFilesAllSubfolders(File checkObj,ArrayList<String> fileList,String[] exceptions,ArrayList<EctdError> errorList)
    {    	
    	if (checkObj!=null && checkObj.isFile())
    	{    		
    		String filename=checkObj.getName();
    		
    		boolean found=false;
    		for (int i=0;fileList!=null && i<fileList.size();i++)
    		{    			
    			if (fileList.get(i).endsWith(filename))
    			{
    				found=true;
    				break;
    			}    			
    		}
    		filename=checkObj.getAbsolutePath();
    		for (int i=0;exceptions!=null && i<exceptions.length;i++)
    		{
    			if (filename.matches(exceptions[i]))
    			{
    				found=true;
    				break;
    			}
    		}
    		if (!found)
    			errorList.add(new EctdError(EctdErrorType.ECTD_ERROR,prefix + checkObj.getName(),checkObj.getAbsolutePath()));
    	}
    	else
    	{
    		for (int i=0;i<checkObj.listFiles().length;i++)
    		{
    			checkFilesAllSubfolders(checkObj.listFiles()[i],fileList,exceptions,errorList);
    		}
    	}
    }
	
	@Override
	public void validate(Node tagData, File dossier, ArrayList<EctdError> errorList) 
	{
		if (!init(tagData, dossier, errorList))
			return;
		String xmlfilename=getNodeAttribute(tagData,"xmlfile");
		File indexXml=find(dossier, xmlfilename,true);		
		if (indexXml==null)
			return;
		Document document;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
		try 
		{
			db = dbf.newDocumentBuilder();
			document = db.parse(indexXml);
			NodeList leafList=document.getElementsByTagName("leaf");
			ArrayList<String> fileList=new ArrayList<String>();
			for (int i=0;i<leafList.getLength();i++)
			{
				Node leaf=leafList.item(i);
				String hrefAttribute=getNodeAttribute(leaf,"xlink:href");				
				if (hrefAttribute!=null)
					fileList.add(hrefAttribute);				
			}
			Node node=getChildNode(tagData,"exceptions");
    		String exceptions=(node!=null && !node.getTextContent().equals(""))?node.getTextContent():null;
			checkFilesAllSubfolders(dossier, fileList,exceptions!=null?exceptions.split(","):null,errorList);			
		} 		
		catch (Exception e) 
		{
			e.printStackTrace();
		}                    
	}

}
