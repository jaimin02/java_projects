/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.docmgmt.struts.actions.ImportProjects.ECTD.validate.General.Validations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdError;
import com.docmgmt.struts.resources.MD5;



/**
 *
 * @author nagesh
 */
public abstract class Validation
{   
	ArrayList<EctdError> errorList;
	String prefix;
	final int REGION_ARRAY_LENGTH = 7;
	final int REGION_NAME=0;
	final int REGION_CHECKSUM=1;
	final int REGION_DTDFILENAME=2;
	final int REGION_XMLFILENAME = 3;
	final int REGION_STORED_DTDFILENAME = 4;
	final int REGION_XMLFILE_LOCATION = 5;
	final int REGION_DTDFILE_LOCATION = 6;
	
	PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	final String REGIONAL_IDENFICATION_XML=propertyInfo.getValue("ECTD_VALIDATOR_REGIONAL_IDENFICATION");
	final String DTDs_FOLDER = propertyInfo.getValue("ECTD_VALIDATOR_DTDs_FOLDER");
	final String ECTD_BACKBONE_DTD = "ich-ectd-3-2.dtd";
	final String ECTD_BACKBONE_XML = "index.xml";
	
	String[] dossierRegionDetails = null;
	static MD5 md5 = new MD5(); 
	
	void findDossierRegionDetails(File dossier)
	{
		
		System.out.println("Inside");
		Document document;
        try 
        {            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            FileInputStream regionFile = new FileInputStream(REGIONAL_IDENFICATION_XML);
            document = db.parse(regionFile);
            NodeList regionList = document.getElementsByTagName("regionname");
            for (int i = 0; i < regionList.getLength() ; i++)
            {
            	Node regionNode = regionList.item(i);
            	String regionName = regionNode.getTextContent();
            	System.out.println("Region Name="+regionName);
            	
            	String regionDetails[] = getRegionDetails(regionName);
            	String dossierPath = dossier.getAbsolutePath() + 
            	((dossier.getAbsolutePath().endsWith("/"))? "" : "/") + 
            	regionDetails[REGION_DTDFILE_LOCATION] + "/" + regionDetails[REGION_DTDFILENAME];
            	File file = new File(dossierPath);
            	if (file.exists())
            	{
            		if (md5.getMd5HashCode(file.getAbsolutePath()).equals(regionDetails[REGION_CHECKSUM]))
            		{
            			this.dossierRegionDetails = regionDetails;
            			break;
            		}
            	}
            }
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
		}
	}
	
	boolean checkValidateAttribute(Node tagData)
    {
        if (tagData.hasAttributes())
        {
            Node validateAttr = tagData.getAttributes().getNamedItem("validate");
            if (validateAttr==null)
                return false;
            else if (validateAttr.getTextContent().equals("false"))
                return false;
            else if (!validateAttr.getTextContent().equals("true"))
                return false;
        }
        else
        	return false;
        return true;
    }

    String getRegionChecksum(String region)
    {
        String checksum = null;
        Document document;
        try {            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            FileInputStream regionFile = new FileInputStream(REGIONAL_IDENFICATION_XML);
            document = db.parse(regionFile);
            NodeList regionList=document.getElementsByTagName("regionname");
            
         
            for (int i=0;i<regionList.getLength();i++)
            {
                Node node=regionList.item(i);
                if (node.getTextContent().equals(region))
                {
                    regionFile.close();
                    return node.getNextSibling().getTextContent();
                }
            }
        } 
        catch (SAXException ex)
        {
            ex.printStackTrace();
        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
        } 
        catch (ParserConfigurationException ex)
        {
            ex.printStackTrace();
        }
        return checksum;
    }

    String [] getRegionDetails(String region)
    {
    	Document document;
    	String[] retArr=null;
        try 
        {            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            FileInputStream regionFile = new FileInputStream(REGIONAL_IDENFICATION_XML);
            document = db.parse(regionFile);
            NodeList regionList=document.getElementsByTagName("regionname");
            for (int i=0;i<regionList.getLength();i++)
            {
                Node node=regionList.item(i);
                if (node.getTextContent().equals(region))
                {
                	retArr=new String[REGION_ARRAY_LENGTH];
                	retArr[REGION_NAME]=region;
                	node=node.getParentNode();
                    for (int j=0;j<node.getChildNodes().getLength();j++)
                    {
                    	if (node.getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE)
                    	{
                    		
                    		
	                    	String eleName = node.getChildNodes().item(j).getNodeName();
	                    	
	                    	
	                    	if (eleName.equals("filename"))
	                    		retArr[REGION_DTDFILENAME] = node.getChildNodes().item(j).getTextContent();                    		
	                    	else if (eleName.equals("checksum"))
	                    		retArr[REGION_CHECKSUM] = node.getChildNodes().item(j).getTextContent();                    		
	                    	else if (eleName.equals("xmlname"))
	                    		retArr[REGION_XMLFILENAME] = node.getChildNodes().item(j).getTextContent();
	                    	else if (eleName.equals("storeddtdname"))
	                    		retArr[REGION_STORED_DTDFILENAME] = node.getChildNodes().item(j).getTextContent();
	                    	else if (eleName.equals("xmlLocation"))
	                    		retArr[REGION_XMLFILE_LOCATION] = node.getChildNodes().item(j).getTextContent();
	                    	else if (eleName.equals("dtdLocation"))
	                    		retArr[REGION_DTDFILE_LOCATION] = node.getChildNodes().item(j).getTextContent();
                    	}
                    }
                    if (retArr[REGION_STORED_DTDFILENAME] == null || retArr[REGION_STORED_DTDFILENAME].equals(""))
                    	retArr[REGION_STORED_DTDFILENAME] = retArr[REGION_DTDFILENAME];
                    break;
                }
            }
            regionFile.close();
        } 
        catch (SAXException ex)
        {
            ex.printStackTrace();
        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
        } 
        catch (ParserConfigurationException ex)
        {
            ex.printStackTrace();
        }
        return retArr;
    }
    
    String [] getRegionDetails(Node tagData)
    {
        boolean found=false;
        String[] regDetails=null;
        for (int i=0;i<tagData.getChildNodes().getLength();i++)
        {
            Node child=tagData.getChildNodes().item(i);
            if (child.getNodeType()==Node.ELEMENT_NODE && child.getNodeName().equals("regionname"))
            {                
                String region=child.getTextContent();                
                if (region==null || region.equals(""))
                    return null;
                found=true;
                regDetails=getRegionDetails(region);
                break;
            }
        }
        if (!found)
            return null;        
        return regDetails;
    }
    
    File find(File obj,String name,boolean isfile)
    {    	    
    	if (obj==null)
    		return null;
    	if (obj.isFile())
    	{    		
    		if (isfile && obj.getName().equals(name))
    		{      			
    			return obj;
    		}
    	}
    	if (obj.isDirectory())
    	{
    		if (!isfile && obj.getName().equals(name))
    		{    			
    			return obj;
    		}
    	}
    	File[] childs=null;
    	if (obj.isDirectory())
    	{
    		childs=obj.listFiles();
    	}
    	for (int i=0;childs!=null && i<childs.length;i++)
    	{
    		obj=childs[i]; 
    		obj=find(obj,name,isfile);
    		if (obj!=null)
    		{
    			return obj;
    		}
    	}
    	return null;
    }

    File find(File obj,String name,boolean isfile,ITask specificTask,ITask overallTask,boolean order)
    {    	    
    	if (obj==null)
    		return null;
    	
    	//order = true --> overallTask before specificTask
    	if (overallTask!=null && order)
		{
			overallTask.performTask(errorList,obj);    				
		}
    	
    	if (obj.isFile())
    	{    		
    		if (isfile && obj.getName().equals(name))
    		{      			
    			if (specificTask!=null)
    			{
    				specificTask.performTask(errorList,obj);    				
    			}
    			return obj;
    		}    		
    	}
    	if (obj.isDirectory())
    	{
    		if (!isfile && obj.getName().equals(name))
    		{  
    			if (specificTask!=null)
    			{
    				specificTask.performTask(errorList,obj);    				
    			}
    			return obj;
    		}
    	}
    	
    	//order = false --> specificTask before overallTask
    	if (overallTask!=null && !order)
		{
			overallTask.performTask(errorList,obj);    				
		}
    	
    	File[] childs=null;
    	if (obj.isDirectory())
    	{
    		childs=obj.listFiles();
    	}
    	for (int i=0;childs!=null && i<childs.length;i++)
    	{
    		obj=childs[i]; 
    		obj=find(obj,name,isfile);
    		if (obj!=null)
    		{
    			return obj;
    		}
    	}
    	return null;
    }
    
    File find(File obj,String name)
    {
		String[] folders=name.split("/");
		for (int i=0;i<folders.length;i++)
		{
			File[] childs=obj.listFiles();
			boolean found=false;
			for (int j=0;j<childs.length;j++)
			{
				if (childs[j].getName().equals(folders[i]))
				{
					obj=childs[j];
					found=true;
					break;
				}
			}
			if (!found)
				return null;
		}
		return obj;    	   		
    }
    
    File find(File obj,String name,ITask task)
    {
		String[] folders=name.split("/");
		for (int i=0;i<folders.length;i++)
		{
			File[] childs=obj.listFiles();
			boolean found=false;
			for (int j=0;j<childs.length;j++)
			{
				if (childs[j].getName().equals(folders[i]))
				{
					obj=childs[j];
					found=true;
					break;
				}
			}
			if (!found)
				return null;
		}
		if (task!=null)
			task.performTask(errorList,obj);
		return obj;    	   		
    }
    
    File findAll(File obj,ITask overallTask)
    {
    	if (obj==null)
    		return null;

    	if (overallTask!=null)
		{
			overallTask.performTask(errorList,obj);    				
		}
    	
    	File[] childs=null;
    	if (obj.isDirectory())
    	{
    		childs=obj.listFiles();
    	}
    	for (int i=0;childs!=null && i<childs.length;i++)
    	{
    		obj=childs[i]; 
    		obj=findAll(obj,overallTask);
    		if (obj!=null)
    		{
    			return obj;
    		}
    	}
    	return null;
    }
    
	File findAll(File obj,ArrayList<ITask> fileTasks,ArrayList<ITask> folderTasks)
	{
		if (obj==null)
			return null;
	
		if (fileTasks!=null && fileTasks.size()>0 && obj.isFile())
		{
			for (int i=0;i<fileTasks.size();i++)    			
				fileTasks.get(i).performTask(errorList,obj);    				
		}
	
		if (folderTasks!=null && folderTasks.size()>0 && obj.isDirectory())
		{
			for (int i=0;i<folderTasks.size();i++)    			
				folderTasks.get(i).performTask(errorList,obj);    				
		}
	
		File[] childs=null;
		if (obj.isDirectory())
		{
			childs=obj.listFiles();
		}
		for (int i=0;childs!=null && i<childs.length;i++)
		{
			obj=childs[i]; 
			obj=findAll(obj,fileTasks,folderTasks);
			if (obj!=null)
			{
				return obj;
			}
		}
		return null;
	}


    String getNodeAttribute(Node node,String attribute)
    {
    	String retString=null;
    	try
    	{
    		retString=node.getAttributes().getNamedItem(attribute).getTextContent();    		
    	}
    	catch(Exception e)
    	{
    		
    	}
    	return retString;    	
    }
    
    ArrayList<Node> getChildNodes(Node node)
    {
    	ArrayList<Node> nodeList=new ArrayList<Node>();
    	try
    	{
    		for (int i=0;i<node.getChildNodes().getLength();i++)
    			nodeList.add(node.getChildNodes().item(i));
    	}
    	catch(Exception e)
    	{
    		
    	}
    	return (nodeList);
    }
    
    ArrayList<Node> getChildNodes(Node node,String child)
    {
    	ArrayList<Node> nodeList=new ArrayList<Node>();
    	try
    	{
    		for (int i=0;i<node.getChildNodes().getLength();i++)
    			if (node.getChildNodes().item(i).getNodeName().equals(child))
    				nodeList.add(node.getChildNodes().item(i));
    	}
    	catch(Exception e)
    	{
    		
    	}
    	return (nodeList);
    }
    
    Node getChildNode(Node node,String child)
    {
    	Node retNode=null;
    	try
    	{
    		for (int i=0;i<node.getChildNodes().getLength();i++)
    			if (node.getChildNodes().item(i).getNodeName().equals(child))
    			{
    				retNode=node.getChildNodes().item(i);
    				break;
    			}
    	}
    	catch(Exception e)
    	{
    		
    	}
    	return (retNode);
    }
    
    boolean isRegionValid(String region,File dossier)
    {
    	String regionDetails[]=getRegionDetails(region);
    	boolean valid=true;
        if (regionDetails!=null && regionDetails.length == REGION_ARRAY_LENGTH)
        {
            File regionalXml=find(dossier,"util/dtd/" + regionDetails[REGION_DTDFILENAME]);                       
            try 
            {
				if (regionalXml!=null)
				{
					if (!regionDetails[REGION_CHECKSUM].equals(md5.getMd5HashCode(regionalXml.getAbsolutePath())))
					{
						valid=false;
					}					
				}
			} 
            catch (Exception e) 
            {
				e.printStackTrace();
				valid=false;
			}            
        }
        return valid;
    }
    
    boolean isRegionValid(Node tagData,File dossier)
    {
    	String region[] = getRegionDetails(tagData);
    	boolean valid=true;
    	/*if (region==null || region.length != REGION_ARRAY_LENGTH)
    		valid=false;*/
        if (region!=null && region.length == REGION_ARRAY_LENGTH)
        {
            File regionalDTD = find(dossier,"util/dtd/" + region[REGION_DTDFILENAME]);                       
            try 
            {
				if (regionalDTD != null)
				{
					if (!region[REGION_CHECKSUM].equals(md5.getMd5HashCode(regionalDTD.getAbsolutePath())))
					{
						valid = false;
					}					
				}
				else{
					valid = false;
				}
			} 
            catch (Exception e) 
            {
				e.printStackTrace();
				valid = false;
			}            
        }
        
        return valid;
    }
	
    boolean isInputValid (Node tagData,File dossier,ArrayList<EctdError> errorList)
    {
    	if (tagData==null || dossier==null || errorList==null)
            return false;        
        if (!checkValidateAttribute(tagData))
            return false;
        if (!dossier.isDirectory())
            return false;
        if (!isRegionValid(tagData,dossier))
        	return false;
        return true;
    }
    
    boolean init(Node tagData,File dossier,ArrayList<EctdError> errorList)
    {
    	this.errorList = errorList;
    	this.prefix = getNodeAttribute(tagData,"message");
    	findDossierRegionDetails(dossier);
    	return(isInputValid(tagData, dossier, errorList));    	
    }
    
    public abstract void validate(Node tagData,File dossier,ArrayList<EctdError> errorList);
}
