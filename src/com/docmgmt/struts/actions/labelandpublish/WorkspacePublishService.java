package com.docmgmt.struts.actions.labelandpublish;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.docmgmt.dto.DTOSTFCategoryMst;
import com.docmgmt.dto.DTOSTFStudyIdentifierMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeLabelHistory;
import com.docmgmt.dto.PublishAttrForm;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.beans.GenerateDTDBean;
import com.docmgmt.struts.resources.MD5;
import com.docmgmt.struts.resources.XmlWriter;
import com.opensymphony.xwork2.ActionContext;

public class WorkspacePublishService
{ 
   
   
    public Writer writer;
    public XmlWriter xmlwriter;
    
    public BufferedWriter out;
    public BufferedWriter out1; // Second buffered writer required to when with eu-regional selected. 
    
    public Vector childNodeForParent; 
    public Vector childAttrForNode; 
    public Vector parentNodes; 
    public Vector childAttrId; 
    public Vector attrDetail; 
    public Vector attrNameForNode; 
    public Vector folderDtl;
    public Vector folderPathDtl;
    public Vector fileNameDtl;
    public Vector fileNameSubDtl;
    public String fileName;
    public String folderStructure;
    public String sourceFolderName;
    public String publishDestFolderName;
    public Integer attrId;
    public String attrValue,attrName;
    public Integer nodeId,nodeAttrId;
    public int childNode;
    public int childNodeSize = 0;
    public int iParentId = 1;
    public String wsId;   
    public String stype="";
    public String nodeName,nodeDisplayName;
    public StringBuffer folderName;
    public File folderStruct;
    public File createBaseFolder;
    public String LastPublishedVersion;
    
    private String relativePathToCreate;
	private String absolutePathToCreate;
	private String nodetypeindi;
	private MD5 md5 = null;
	private Vector STFXMLLocation;
	//Added For giving space
    private Vector Space = new Vector();
    String newPath = "";
    String studyId;
    private int upCount=0;
    public String workspaceDesc;
    public String baseLocation;
    public String workspaceLabelId;
    public Integer userId;
    
    public Vector pathlst;
    public Vector nodeidlst;
    public Vector trannolst;
    public int tranno;
    public String folder;
    
    //addded by hardik shah
    
    Vector AllNodesofHistory=new Vector();
    
    DocMgmtImpl docMgmtInt = new DocMgmtImpl();
    GenerateDTDBean generateDTDBean = new GenerateDTDBean();
    
    HttpServletRequest request;      
    
    public WorkspacePublishService()
    {
    	
    	PropertyInfo propInfo=PropertyInfo.getPropInfo();
        publishDestFolderName=propInfo.getValue("PublishFolder");
    }
     
     
	 public void workspacePublish(String workspaceId,PublishAttrForm publishForm,HttpServletRequest request1,String wsDesc)
	 {     
	    try {
            this.request=request1;
	        wsId = workspaceId;
	        workspaceDesc=wsDesc;
            writer = new java.io.StringWriter();
            xmlwriter = new XmlWriter(writer);
            
            folderName = new StringBuffer();
            childNodeForParent = new Vector();
            childAttrForNode = new Vector();
            parentNodes = new Vector();
            childAttrId = new Vector();
            attrDetail = new Vector();
            attrNameForNode = new Vector();
            STFXMLLocation = new Vector();
            pathlst = new Vector();
            nodeidlst = new Vector();
            trannolst = new Vector();
            md5 = new MD5();           
           
            int labelNo=publishForm.getLabelNo();
            
            userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
    	   
            publishDestFolderName = publishDestFolderName + File.separator + workspaceDesc  + File.separator + publishForm.getLabelId();
    	    createBaseFolder(publishDestFolderName,publishForm);
            
            stype = publishForm.getSubmissionFlag();
            
            
            //get all nodes and its parent nodes where file attached
            AllNodesofHistory=docMgmtInt.getAllNodesFromHistory(wsId,labelNo);
            
            if(stype.equals("eu") || stype.equals("us"))
            {
                iParentId = 1;
                out = new BufferedWriter(new FileWriter((absolutePathToCreate)+"/"+stype+"-regional.xml"));

                writeToXmlFile(stype,publishForm);
	            
	            parentNodes = docMgmtInt.getChildNodeByParentForPublishForM1(iParentId, wsId);
	            getChildNode(parentNodes, absolutePathToCreate,iParentId,0,stype,publishForm);
	            xmlwriter.close();
	            out.write(writer.toString());
	            
	            if(stype.equals("eu"))
	            	out.write("</eu:eu-backbone>");
	            if(stype.equals("us"))
	            	out.write("</fda-regional:fda-regional>");
	            out.flush();
	            out.close();
	            writer = null;
	            xmlwriter = null;
	            
	            writer = new java.io.StringWriter();
	            xmlwriter = new XmlWriter(writer);
	            out = new BufferedWriter(new FileWriter((absolutePathToCreate)+"/index.xml"));
	            
	            writeToXmlFile(stype + "m2-m5",publishForm);
	            
	            parentNodes = docMgmtInt.getChildNodeByParentForPublishFromM2toM5(iParentId, wsId);
	            
	        	for(int i = 0;i<parentNodes.size();i++)
	        	{
	        	 
	        		DTOWorkSpaceNodeDetail dto=(DTOWorkSpaceNodeDetail)parentNodes.get(i);
	        		nodeId=dto.getNodeId();
	        		nodeName=dto.getNodeName();
	        		nodeDisplayName=dto.getNodeDisplayName();
	        		dto=null;
	        		
	        	}	    
	     
	        	getChildNode(parentNodes, absolutePathToCreate,iParentId,0,stype,publishForm);
	            xmlwriter.close();
	            out.write(writer.toString());
	            out.write("</ectd:ectd>");
	            out.flush();
	            out.close();
            }
            else if(stype.equals("without"))
            {
                iParentId = 1;
	            out = new BufferedWriter(new FileWriter((absolutePathToCreate)+"/index-without-eu.xml"));
	            stype="without";
	            writeToXmlFile(stype,publishForm);
	            parentNodes = docMgmtInt.getChildNodeByParent(iParentId, wsId);
	            getChildNode(parentNodes, absolutePathToCreate,iParentId,0,stype,publishForm);
	            xmlwriter.close();
	            out.write(writer.toString());
	            out.write("</ectd:ectd>");
	            out.flush();
	            out.close();
            }
            
            checkSumForindexFile();
            addutilFolder(stype);
          //copyUSDTDFile(stype);
            copyEuRegionalFile(stype);
            
         
            /*if(STFXMLLocation.size() > 0)
            	generateSTFFile(wsId);*/
       
            
            DTOWorkSpaceNodeLabelHistory dtoLabelHistory;
            DTOWorkSpaceMst dtoWorkspaceMst;
            String node;
         
											  
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * This Method recursivley create Node for Parent Node
     * @param childNodes
     * @throws Exception
     */

	 
public void getChildNode(Vector childNodes,String pathToCreate,int parentId,int IdValue,String stype,PublishAttrForm publishForm)  throws Exception 
{
	
	
	if(childNodes.size() == 0 )
    {
		 
	//if file attached at node or its parent node
	if(AllNodesofHistory.contains(nodeId))
	{
		//System.out.println("NodeId if:"+nodeId);
		
		xmlwriter.writeEntity("leaf");
         // childAttrId = docMgmtInt.getAttributesForNodeForPublish(nodeId.intValue(), wsId);
		childAttrId = docMgmtInt.getAttributesForNodeForPublish(nodeId.intValue(), wsId,publishForm.getLabelNo(),"0001");
          if(childAttrId.size() != 0) 
          {
        	  for(int i = 0;i<childAttrId.size();i++)
              {
        		DTOWorkSpaceNodeAttrHistory dtowsnath=(DTOWorkSpaceNodeAttrHistory)childAttrId.get(i);
              	attrId=dtowsnath.getAttrId();
              	attrValue=dtowsnath.getAttrValue();
              	attrName = dtowsnath.getAttrName();
              	/*attrDetail = docMgmtInt.getAttrDetailById(attrId.intValue());
                if(attrDetail.size() != 0 )
                {
                	 for(int k = 0 ; k < attrDetail.size();k++ )
                     {
                		 DTOAttributeMst dtoatb=(DTOAttributeMst)attrDetail.get(k);
                         attrName=dtoatb.getAttrName();
                */
                         if(attrName.equalsIgnoreCase("xlink:href"))
                         {
                              attrValue = copyFileforPublish(publishForm);
                              attrValue=attrValue.toLowerCase();
                              stype = stype.toLowerCase();
                              String FilePathForCheckSum = publishDestFolderName + File.separator + publishForm.getApplicationNumber() +"/"+ publishForm.getSeqNumber() + "/"+attrValue;
                              	
                              attrValue = attrValue.replaceAll("m1/"+stype+"/","");
                              nodeidlst.add(nodeId.toString());
                              
                              attrValue = attrValue.replaceAll("//","/");
                              xmlwriter.writeAttribute(attrName,attrValue.trim());
      		           		  
                              String absolutePath="";
                              String absolutePathLink = getRootPath(nodeId.intValue(),absolutePath);
                              
                              String FilePath = publishDestFolderName + File.separator + publishForm.getApplicationNumber() +"/"+ publishForm.getSeqNumber() + "/"+attrValue;
                              //System.out.println("FilePath:"+FilePath);
                              
                              String md5HashCodeforFile = md5.getMd5HashCode(FilePathForCheckSum);
                              xmlwriter.writeAttribute("checksum",md5HashCodeforFile);
                              String IDValue = "node-"+nodeId.toString();
                              xmlwriter.writeAttribute("ID",IDValue);
                         }
                         else
                         {
                             if(!attrName.equals("checksum") && !attrName.equals("Keywords") && !attrName.equals("Author") && !attrName.equals("Description") && !attrName.equals("keywords") && !attrName.equals("ID"))
                             {
                                 xmlwriter.writeAttribute(attrName,attrValue.trim());
                             }    
                         }
                         
                    // }
                //}
              	
              	
              }
          }
          
          xmlwriter.writeEntity("title");
          xmlwriter.writeText(nodeDisplayName.trim());
          xmlwriter.endEntity();
          xmlwriter.endEntity();//end entity for <leaf>
   
		}//if end of history check
    
    }//if end
	else
    {  
			
		String MergeAttributeStr="";
       	for(int i = 0;i<childNodes.size();i++)
       	{
       		DTOWorkSpaceNodeDetail dtowsnd=(DTOWorkSpaceNodeDetail)childNodes.get(i);
       		
       		nodeId=dtowsnd.getNodeId();
       		nodeName=dtowsnd.getNodeName();
       		nodeDisplayName=dtowsnd.getNodeDisplayName().trim();
       		String filepathelement=dtowsnd.getFolderName();
       	   String remark = dtowsnd.getRemark().trim();
        
       	//if file attached at node or its parent node	
         if(AllNodesofHistory.contains(nodeId))
       	 {	
        	// System.out.println("NodeId else:"+nodeId);
       		
       		
       	   	int isLeaf = docMgmtInt.isLeafNodes(wsId,nodeId.intValue());

       	   	nodetypeindi=Character.toString(dtowsnd.getNodeTypeIndi());
       		if (isLeaf==0) {
				if(nodetypeindi.equalsIgnoreCase("T"))
           		{
					int intchar = filepathelement.indexOf(".");
           			String finalstr;
           			if(intchar!=-1){ 
           				finalstr=filepathelement.substring(0, intchar);
           			}else{
           				finalstr=filepathelement;
           			}
           			createSubFolders(pathToCreate,finalstr);
           		}else{		
           			createSubFolders(pathToCreate,filepathelement);
           		}	
		   	}    
            
           	if (isLeaf==0)
           	{
           		if(nodetypeindi.equalsIgnoreCase("T"))
           		{	
           			//System.out.println(" STF File location : " + pathToCreate + "\\" + filepathelement);
           			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
           			dto.setNodeId(nodeId.intValue());
           			
           			//As we r adding stf on leaf node folder name of the leaf node would be abc.pdf 
           			//so we need to remove .pdf extension 
           			int intchar = filepathelement.indexOf(".");
           			String finalstr;
           			if(intchar!=-1){ 
           				finalstr=filepathelement.substring(0, intchar);
           			}else{
           				finalstr=filepathelement;
           			}
           			

           			dto.setBaseworkfolder(pathToCreate + "/" + finalstr);
           			
           			//System.out.println("STFXMLLocation:"+dto.getBaseworkfolder());
           			
           			STFXMLLocation.addElement(dto);
           			generateSTFFile(wsId);
           			dto = null; 
           		}	
           	}
			if(!nodeName.trim().equals("m1-administrative-information-and-prescribing-information"))
           	{
				if (isLeaf==0) 
	           	{//This is a parent node
		           	if(!nodetypeindi.trim().equalsIgnoreCase("T"))
		           	{
		           		//System.out.println("node name : " + nodeName.trim() + " NodeTypeIndi: " + nodetypeindi.trim());
		           		
		           		
		           		//change by hardik (For Node-Extension)
		           		
		           		if(nodetypeindi.trim().equals("E"))
		           		{
		           			xmlwriter.writeEntity("node-extension");
		           		    xmlwriter.writeEntity("title");
		           			xmlwriter.writeText(remark.trim());
		           			xmlwriter.endEntity();
		           		}
		           		else
		           		{
		           			xmlwriter.writeEntity(nodeName.trim());
		           		}
		           		
		           		
		           		//xmlwriter.writeEntity(nodeName.trim());
		           		
			           	Vector nodeAttribute = docMgmtInt.getNodeAttributes(wsId,nodeId.intValue());
			           	if(nodeAttribute.size()>0) 
			           	{
		           			for(int k=0;k<nodeAttribute.size();k++) 
		           			{
		           				DTOWorkSpaceNodeAttribute obj = (DTOWorkSpaceNodeAttribute)nodeAttribute.get(k);
		           				String attrname = obj.getAttrName();
		           				String attrvalue = obj.getAttrValue();
		           			
		           				//remove space and give - in attribute value
		           				attrvalue=attrvalue.trim().replaceAll(" ", "-");
		           				
		           				if(k==0)
		           				{
		           					MergeAttributeStr = attrvalue.trim();
		           				}
		           				else
		           				{
		           					MergeAttributeStr = MergeAttributeStr +"-"+ attrvalue.trim();
		           				}
		           				xmlwriter.writeAttribute(attrname,attrvalue);
		           			}
		           			
		           			String PathForAttrFolder = pathToCreate+ "/" + filepathelement;
		           			
		           			createSubFolders(PathForAttrFolder,MergeAttributeStr);

		           			parentNodes = docMgmtInt.getChildNodeByParent(nodeId.intValue(), wsId);
			           	    getChildNode(parentNodes,relativePathToCreate,nodeId.intValue(),IdValue,stype,publishForm);
			           	    xmlwriter.endEntity();
			           	}
			           	else
			           	{
			           		parentNodes = docMgmtInt.getChildNodeByParent(nodeId.intValue(), wsId);
			           		getChildNode(parentNodes,relativePathToCreate,nodeId.intValue(),IdValue,stype,publishForm);
			                xmlwriter.endEntity();
			           	    
			           	}    
		           	}
		           	else
		           	{
		           		parentNodes = docMgmtInt.getChildNodeByParent(nodeId.intValue(), wsId);            	    
		           	    getChildNode(parentNodes,relativePathToCreate,nodeId.intValue(),IdValue,stype,publishForm);

		           	}
	           	}
				else
				{
					parentNodes = docMgmtInt.getChildNodeByParent(nodeId.intValue(), wsId);            	    
					getChildNode(parentNodes,relativePathToCreate,nodeId.intValue(),IdValue,stype,publishForm);

				}
       	
       	}
		else
		{
			parentNodes = docMgmtInt.getChildNodeByParent(nodeId.intValue(), wsId);            	    
       	    getChildNode(parentNodes,relativePathToCreate,nodeId.intValue(),IdValue,stype,publishForm);

		}

	  }//if end of history vector 
		
    }//for loop end
     
    	
       	
     }//main else end
	
}
	 
	 
	 
	 
/* public void getChildNode(Vector childNodes,String pathToCreate,int parentId,int IdValue,String stype,PublishAttrForm publishForm)  throws Exception {
		 
		 	if(childNodes.size() == 0 )
	        {
	            IdValue++;
	            xmlwriter.writeEntity("leaf");
	            childAttrId = docMgmtInt.getAttributesForNodeForPublish(nodeId.intValue(), wsId);
	            
	            if(childAttrId.size() != 0) 
	            {
	           	
	                for(int i = 0;i<childAttrId.size();i++)
	                {

	                	DTOWorkSpaceNodeAttrHistory dtowsnath=(DTOWorkSpaceNodeAttrHistory)childAttrId.get(i);
	                	attrId=dtowsnath.getAttrId();
	                	attrValue=dtowsnath.getAttrValue();
	                	
	                	attrDetail = docMgmtInt.getAttrDetailById(attrId.intValue());
	                	
	                    
	                    if(attrDetail.size() != 0 )
	                    {
	                      
	                        for(int k = 0 ; k < attrDetail.size();k++ )
	                        {
	                            DTOAttributeMst dtoatb=(DTOAttributeMst)attrDetail.get(k);
	                        	
	                            
	                            attrName=dtoatb.getAttrName();
	                            
	                            if(attrName.equalsIgnoreCase("xlink:href"))
	                            {
	                                 attrValue = copyFileforPublish(publishForm);
	                                 attrValue=attrValue.toLowerCase();
	                                 stype = stype.toLowerCase();
	                                 String FilePathForCheckSum = publishDestFolderName + File.separator + publishForm.getApplicationNumber() +"/"+ publishForm.getSeqNumber() + "/"+attrValue;

	                                 attrValue = attrValue.replaceAll("m1/"+stype+"/","");
	                                
	                                 nodeidlst.add(nodeId.toString());
	                                 pathlst.add(attrValue);
	                                 
	                                 attrValue = attrValue.replaceAll("//","/");
	                                 
	                   
	         		           		 xmlwriter.writeAttribute(attrName,attrValue);
	         		           		 	 
	                                 
	                                 String absolutePath="";
	                                 String absolutePathLink = getRootPath(nodeId.intValue(),absolutePath);
	                                 String FilePath = publishDestFolderName + File.separator + publishForm.getApplicationNumber() +"/"+ publishForm.getSeqNumber() + "/"+attrValue;
	                                 
	                                 
	                                 
	                                 String md5HashCodeforFile = md5.getMd5HashCode(FilePathForCheckSum);
	                                 xmlwriter.writeAttribute("checksum",md5HashCodeforFile);
	                                 String IDValue = "node-"+nodeId.toString();
	                                 xmlwriter.writeAttribute("ID",IDValue);
	                                 
	                            }
	                            else
	                            {
	                                if(!attrName.equals("checksum") && !attrName.equals("Keywords") && !attrName.equals("Author") && !attrName.equals("Description") && !attrName.equals("keywords") && !attrName.equals("ID"))
	                                {
	                                    xmlwriter.writeAttribute(attrName,attrValue);
	                                }    
	                            }
	                        }
	                    }
	            	}
	            }
	            xmlwriter.writeEntity("title");
	            xmlwriter.writeText(nodeDisplayName);
	            xmlwriter.endEntity();
	            xmlwriter.endEntity();//end entity for <leaf>
	        }
	        else
	        {  
	        	
	        	String MergeAttributeStr="";
	           	for(int i = 0;i<childNodes.size();i++)
	           	{
	           		
	           		DTOWorkSpaceNodeDetail dtowsnd=(DTOWorkSpaceNodeDetail)childNodes.get(i);
	           		
	           		nodeId=dtowsnd.getNodeId();
	           		nodeName=dtowsnd.getNodeName();
	           		nodeDisplayName=dtowsnd.getNodeDisplayName();
	           		String filepathelement=dtowsnd.getFolderName();
	           	    
	           	   	int isLeaf = docMgmtInt.isLeafNodes(wsId,nodeId.intValue());
	           	   	nodetypeindi=Character.toString(dtowsnd.getNodeTypeIndi());
					if (isLeaf==0) 
					{
		           			createSubFolders(pathToCreate,filepathelement);
		           		
				   	}    
		            
		           
		           	if(!nodeName.trim().equals("m1-administrative-information-and-prescribing-information"))
		           	{
                     
			           	if (isLeaf==0) 
			           	{//This is a parent node
				           	if(!nodetypeindi.trim().equalsIgnoreCase("S"))
				           	{
				           		
				           		xmlwriter.writeEntity(nodeName.trim());
				           		
					           	Vector nodeAttribute = docMgmtInt.getNodeAttributes(wsId,nodeId.intValue());
					           	if(nodeAttribute.size()>0) 
					           	{
				           			for(int k=0;k<nodeAttribute.size();k++) 
				           			{
				           				DTOWorkSpaceNodeAttribute obj = (DTOWorkSpaceNodeAttribute)nodeAttribute.get(k);
				           				String attrname = obj.getAttrName();
				           				String attrvalue = obj.getAttrValue();
				           				if(k==0)
				           				{
				           					MergeAttributeStr = attrvalue.trim();
				           				}
				           				else
				           				{
				           					MergeAttributeStr = MergeAttributeStr +"-"+ attrvalue.trim();
				           				}
				           				xmlwriter.writeAttribute(attrname,attrvalue);
				           			}
				           			System.out.println("filepathelement in Attribute: " + filepathelement);
				           			String PathForAttrFolder = pathToCreate+ "/" + filepathelement;
				           			createSubFolders(PathForAttrFolder,MergeAttributeStr);

				           			parentNodes = docMgmtInt.getChildNodeByParent(nodeId.intValue(), wsId);
					           	    getChildNode(parentNodes,relativePathToCreate,nodeId.intValue(),IdValue,stype,publishForm);
					           	    xmlwriter.endEntity();
					           	}
					           	else
					           	{
					           		parentNodes = docMgmtInt.getChildNodeByParent(nodeId.intValue(), wsId);
					           	    getChildNode(parentNodes,relativePathToCreate,nodeId.intValue(),IdValue,stype,publishForm);
					           	    xmlwriter.endEntity();
					           	}    
				           	}
				           	else
				           	{
				           		
				           		parentNodes = docMgmtInt.getChildNodeByParent(nodeId.intValue(), wsId);            	    
				           	    getChildNode(parentNodes,relativePathToCreate,nodeId.intValue(),IdValue,stype,publishForm);
				           	}
			           	}
			           	else
			           	{
			           		
			           		parentNodes = docMgmtInt.getChildNodeByParent(nodeId.intValue(), wsId);            	    
			           	    getChildNode(parentNodes,relativePathToCreate,nodeId.intValue(),IdValue,stype,publishForm);
			           	} 
		           	}
		           	else
		           	{
		           		parentNodes = docMgmtInt.getChildNodeByParent(nodeId.intValue(), wsId);            	    
		           	    getChildNode(parentNodes,relativePathToCreate,nodeId.intValue(),IdValue,stype,publishForm);

		           	}
		       
	           	}
	        }
	    }*/

	 
	 

    /**
     * Writes The standard tag to the XML file. 
     * 
     */
     
    /*
     *  From workspaceMst 
     *         1) Source Folder Path
     *         2) Publish Folder Name 
     *         3) LastPublished Version
     *  From workspacenodehistory 
     *  	   1) File Name 
     *         2) Folder Structure
     */
    private void createBaseFolder(String bfoldername,PublishAttrForm publishForm)
    {
    	try{
    	   folderDtl = new Vector();
            
			folderDtl = docMgmtInt.getFolderByWorkSpaceId(wsId);
			
			if(folderDtl != null)
			{
				Object[] record = new Object[folderDtl.size()];
				record = (Object[])folderDtl.elementAt(0);
				if(record != null)
				{ 
					// Source Folder Path
					sourceFolderName=record[0].toString();
					
					LastPublishedVersion=record[2].toString();
					
				}
			}
				
			///////////////change "ctd-" + wsId +"/" to applicationno			

			createBaseFolder = new File(bfoldername + File.separator + publishForm.getApplicationNumber() +"/"+ publishForm.getSeqNumber());
			absolutePathToCreate = bfoldername + File.separator + publishForm.getApplicationNumber() +"/"+ publishForm.getSeqNumber();
			
			if(! createBaseFolder.exists()) // Check folder already present or not
			   createBaseFolder.mkdirs();    
			
			//if the publishDestFolderName exist.....  	
			// absolutePathToCreate = "/" + "ctd-" + wsId +"/"+ LastPublishedVersion;
			
			}catch(Exception e){
				e.printStackTrace();
			}
	} 
    
    private String getRootPath(int childNodeId, String path)throws Exception
    {
        
        if (childNodeId!=1){
	    	Vector record = new Vector();
	    	Vector NodeDtl = new Vector();
	    	String ParentfolderName=null;
	    	
	    	record = docMgmtInt.getParentNode(wsId,childNodeId);
	    /*	NodeDtl =(Vector) record.elementAt(0);
	    	
	    	Integer ParentIdForChildNode = (Integer)NodeDtl.elementAt(0);*/
	    	
	    	DTOWorkSpaceNodeDetail dtowsndl=(DTOWorkSpaceNodeDetail)record.get(0);
	    	Integer ParentIdForChildNode=dtowsndl.getParentNodeId();
	    	
	    	
	    	Vector parentNodeDtl = docMgmtInt.getNodeDetail(wsId,ParentIdForChildNode.intValue());
	    	
	    	DTOWorkSpaceNodeDetail obj = (DTOWorkSpaceNodeDetail)parentNodeDtl.get(0);
	    	ParentfolderName = obj.getFolderName();
	    	char NodeIndi = obj.getNodeTypeIndi();
	    	
	    	/******************Merge attribute value in path *****************/
	    	
	    	//If any attribute on the parent node
	    //	System.out.println("ParentNodeId: " + ParentIdForChildNode.intValue());
	    	Vector nodeAttribute = docMgmtInt.getNodeAttributes(wsId,ParentIdForChildNode.intValue());
	    	String MergeAttributeStr = "";
           	if(nodeAttribute.size()>0) {
       			for(int k=0;k<nodeAttribute.size();k++) {
       				DTOWorkSpaceNodeAttribute obj1 = (DTOWorkSpaceNodeAttribute)nodeAttribute.get(k);
       				String attrname = obj1.getAttrName();
       				String attrvalue = obj1.getAttrValue();
       				
       				//remove space and give - in attribute value
       				attrvalue=attrvalue.trim().replaceAll(" ", "-");
       				
       				if(k==0){
       					MergeAttributeStr = attrvalue.trim();
       					
       				}else{
       					MergeAttributeStr = MergeAttributeStr +"-"+ attrvalue.trim();
       				}
       			}
       			ParentfolderName = ParentfolderName+"/"+MergeAttributeStr;
           	}
	    	
	    	if (NodeIndi == 'T' || NodeIndi == 't'){
		    	int intchar = ParentfolderName.indexOf(".");
	   			String finalstr;
	   			if(intchar!=-1){ 
	   				finalstr=ParentfolderName.substring(0, intchar);
	   			}else{
	   				finalstr=ParentfolderName;
	   			}
	   			ParentfolderName=finalstr;
	    	}
	    	
	    	if(ParentIdForChildNode.intValue()!=1) {
	    	    newPath = "/" + ParentfolderName + path;
	    	}    
	    	getRootPath(ParentIdForChildNode.intValue(),newPath);
    	}
        
        
        return newPath;
    }
    
    private String copyFileforPublish(PublishAttrForm publishForm)throws Exception{
        	
        	
        	String absolutePath="";
        	String absolutePathLink="";
        	newPath = "";
        	
        	absolutePathLink = getRootPath(nodeId.intValue(),absolutePath);
        	
        	//System.out.println("absoluePathInCopyFile:"+absolutePathLink);
        	
//        	absolutePath = publishDestFolderName + File.separator + "ctd-" + wsId +"/"+ publishForm.getSeqNumber() + absolutePathLink;
        //	System.out.println("-----------------GetRootPath Done-------------");
        	absolutePath = publishDestFolderName + File.separator + publishForm.getApplicationNumber() +"/"+ publishForm.getSeqNumber() + absolutePathLink;
        	File  folderName;
        	
        	
            fileNameDtl = docMgmtInt.getFileNameForNodeForPublish(nodeId.intValue(), wsId,publishForm.getLabelNo());
            if(fileNameDtl.size()>0) 
            {
            	
            	DTOWorkSpaceNodeHistory dtowsand=(DTOWorkSpaceNodeHistory)fileNameDtl.get(0);
            	
            	fileName=dtowsand.getFileName();
            	folderStructure=dtowsand.getFolderName();
            	
            	          	
            	
	           folderStruct = new File(sourceFolderName.trim()+folderStructure.trim()+"/"+fileName);
	                      
	           folderName = new File(absolutePath);
	           addFiles(folderStruct,folderName); 
	        //   System.out.println("absolutePathLink:"+absolutePathLink.substring(1));
	           return (absolutePathLink.substring(1) + "/" + fileName);
            }
            else
            {
               return(absolutePathLink.substring(1));
            }
    }
    /*
     * Add file at Publish Folder Path 
     *  folderStruct = Source Folder File 
     *  publishFolderStuct = Destination Folder File
     */
    private void addFiles(File folderStruct,File publishFolderStuct){
        try{
            
            FileInputStream fin = new FileInputStream(folderStruct.getCanonicalFile());
            
            if(! publishFolderStuct.exists())
            {
                publishFolderStuct.mkdir();
            }
            
            FileOutputStream fout = new FileOutputStream(publishFolderStuct + "/" + fileName);
            
            byte [] buf = new byte[1024 * 10];
		 	int len;
		 	
		 	while((len = fin.read(buf))> 0){
		 	    fout.write(buf,0,len);
		 	}
		 	fin.close();
		 	fout.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private  void createSubFolders(String pathToCreate, String folderName){
		File createsubfolder ;
		createsubfolder = new File(pathToCreate + "/" + folderName);
		relativePathToCreate = pathToCreate + "/" + folderName; 
		if(! createsubfolder.exists()){
			createsubfolder.mkdirs();
		}	
	}
    private void checkSumForindexFile(){
        try {
            String indexmd5 = md5.getMd5HashCode(absolutePathToCreate + "/index.xml");
            BufferedWriter indexHashFileout = new BufferedWriter(new FileWriter(absolutePathToCreate +  "/index-md5.txt"));
	        indexHashFileout.write(indexmd5);
			indexHashFileout.close();
			indexHashFileout = null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
 
    
    private void addutilFolder(String stype){
        try{
           // System.out.println("Source folder Name :"+sourceFolderName);
           // System.out.println("Absolute Path : " + absolutePathToCreate);
            
        	
            // Create a pattern to match breaks
            Pattern p = Pattern.compile("[/\\\\]+");
            // Split input with the pattern
            String[] result = 
                     p.split(sourceFolderName);
                    
            Pattern p1 = Pattern.compile(result[result.length - 1]);
            String[] rs =
                    p1.split(sourceFolderName);
            
            // changes by jwalin shah
            File indtdFile = new File("");
           
            if(stype.equals("us"))
            	indtdFile = new File(rs[0]+"/util_us/util/dtd");
            else
            	indtdFile = new File(rs[0]+"/util_eu/util/dtd");
         
            System.out.println("Source Folder for Util : "+indtdFile.getCanonicalPath());
            File outdtdFile = new File(absolutePathToCreate+"/util/dtd");
            outdtdFile.mkdirs();
                        
            String[] dtdfileList = indtdFile.list();
            for(int i = 0 ; i< dtdfileList.length;i++){
                FileInputStream fin = new FileInputStream(indtdFile+"/"+dtdfileList[i]);
                FileOutputStream fout = new FileOutputStream(outdtdFile+"/"+dtdfileList[i]);
                byte [] buf = new byte[1024 * 10];
    		 	int len;
    		 	
    		 	while((len = fin.read(buf))> 0){
    		 	    fout.write(buf,0,len);
    		 	}
    		 	fin.close();
    		 	fout.close();
            }
            // changes by jwalin shah
            File instyleFile = new File("");
          
            if(stype.equals("us"))
            	 instyleFile = new File(rs[0]+"/util_us/util/style");
            else
            	 instyleFile = new File(rs[0]+"/util_eu/util/style");
            
            File outstyleFile = new File(absolutePathToCreate+"/util/style");
            outstyleFile.mkdirs();
                        
            String[] outstyleList = instyleFile.list();
            for(int i = 0 ; i< outstyleList.length;i++){
                FileInputStream fin = new FileInputStream(instyleFile+"/"+outstyleList[i]);
                FileOutputStream fout = new FileOutputStream(outstyleFile+"/"+outstyleList[i]);
                byte [] buf = new byte[1024 * 10];
    		 	int len;
    		 	
    		 	while((len = fin.read(buf))> 0){
    		 	    fout.write(buf,0,len);
    		 	}
    		 	fin.close();
    		 	fout.close();
            }    
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void writeToXmlFile(String stype,PublishAttrForm publishForm) throws Exception {
		
        if(stype.equals("eu"))
        {
			String xmlDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
			String docTypeDeclaration ="<!DOCTYPE eu:eu-backbone SYSTEM \"../../util/dtd/eu-regional.dtd\">";
			String xslStyleSheetDeclaration = "<?xml-stylesheet type=\"text/xsl\" href=\"../../util/style/eu-regional.xsl\"?>";
			String XmldtdVersionDeclaration = "<eu:eu-backbone xmlns:eu=\"http://europa.eu.int\" xmlns:xlink=\"http://www.w3c.org/1999/xlink\" xml:lang=\"en\">";
		
			out.write(xmlDeclaration);
			out.write("\n" + docTypeDeclaration);
			out.write("\n" + xslStyleSheetDeclaration);
			out.write("\n" + XmldtdVersionDeclaration);
			out.write("\n");
			
			out.write("\n" + "<eu-envelope>");
			out.write("\n" + "<envelope country=\"" + publishForm.getCountry() +"\">");
			out.write("\n" + 	"<application>");
			out.write("\n" + 		"<number>" + publishForm.getApplicationNumber() +"</number>");
			out.write("\n" + 	"</application>");
			out.write("\n" + 	"<applicant>"+ publishForm.getApplicant() +"</applicant>");
			//agency Code inspite of agency Name like <agency-code=""/> 
			//out.write("\n" + 	"<agency-name>"+publishForm.getAgencyName()+"</agency-name>");
			out.write("\n" + 	"<agency code="+"\""+publishForm.getAgencyName()+"\""+" />");
			out.write("\n" + 	"<atc>"+publishForm.getAtc()+"</atc>");
			
			out.write("\n" + 	"<submission type=\""+ publishForm.getSubmissionType_eu() + "\"/>");
			out.write("\n" + 	"<procedure type=\""+ publishForm.getProcedureType()+"\"/>");
			out.write("\n" +	"<invented-name>"+publishForm.getInventedName()+"</invented-name>");
			out.write("\n" + 	"<inn>"+publishForm.getInn()+"</inn>");
			
			out.write("\n" + 	"<sequence>"+publishForm.getSeqNumber()+"</sequence>");
			//out.write("\n" + 	"<related-sequence country=\""+ publishForm.getRelatedSeqNumber() +"\">"+publishForm.getRelatedSeqNumber()+"</related-sequence>");
			out.write("\n" + 	"<submission-description>"+publishForm.getSubmissionDescription()+"</submission-description>");
			out.write("\n" + "</envelope>");
			out.write("\n" + "</eu-envelope>");
			out.write("\n");
        }
        
        if(stype.equals("us"))
        {
			String xmlDeclaration = "<?xml version=\"1.0\" encoding=\"US-ASCII\" standalone=\"no\"?>";
			String XmldtdVersionDeclaration = "<!DOCTYPE fda-regional:fda-regional SYSTEM \"../../util/dtd/us-regional-v2-01.dtd\">"; 
			String xslStyleSheetDeclaration = "<?xml-stylesheet type=\"text/xsl\" href=\"../../util/style/us-regional.xsl\"?>";
			String XmlBackboneDeclaration = "<fda-regional:fda-regional xmlns:fda-regional=\"http://www.ich.org/fda\" dtd-version=\"2.01\" xmlns:xlink=\"http://www.w3c.org/1999/xlink\">";
			
			//*
			out.write(xmlDeclaration);
			out.write("\n" + XmldtdVersionDeclaration);
			out.write("\n" + xslStyleSheetDeclaration);
			out.write("\n" + XmlBackboneDeclaration);
			out.write("\n");
			
			out.write("\n" + "<admin>");

			out.write("\n" + "<applicant-info>");
			out.write("\n" + "<company-name>" + publishForm.getCompanyName() + "</company-name>");
			String dos = request.getParameter("dos");
			String dos1[] =dos.split("/");
			String dosdt = dos1[0] + dos1[1] + dos1[2];
			
			out.write("\n" + "<date-of-submission><date format=\"yyyymmdd\">" +dosdt+ "</date></date-of-submission>");
			out.write("\n" + "</applicant-info>");

			out.write("\n" + "<product-description>");
			out.write("\n" + "<application-number>"+publishForm.getApplicationNumber()+"</application-number>");
			out.write("\n" + "<prod-name type=\""+publishForm.getProductType()+"\">"+publishForm.getProdName()+"</prod-name>");
			out.write("\n" + "</product-description>");
			
			out.write("\n" + "<application-information application-type=\""+publishForm.getApplicationType()+"\">");
			out.write("\n" + "<submission submission-type=\""+publishForm.getSubmissionType_us()+"\">");
			out.write("\n" + "<sequence-number>"+publishForm.getSeqNumber()+"</sequence-number>");
			out.write("\n"+" </submission>");
			//if submission type original application
			if(!publishForm.getSubmissionType_us().equals("original-application"))
			{
				out.write("\n" + "<related-sequence-number>"+publishForm.getRelatedSeqNumber()+"</related-sequence-number>");
			}
			
			out.write("\n" + "</application-information>");
			out.write("\n" + "</admin>");
			out.write("\n");
        }
        
        else if(stype.equals("without"))
        {
            out.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + "\n");
            out.write("<!DOCTYPE ectd:ectd SYSTEM \"util/dtd/ich-ectd-3-0.dtd\">" + "\n" );
            out.write("<?xml-stylesheet type=\"text/xsl\" href=\"util/style/ectd-2-0.xsl\"?>" + "\n");
            out.write("<ectd:ectd xmlns:ectd=\"http://www.ich.org/ectd\" xmlns:xlink=\"http://www.w3c.org/1999/xlink\" dtd-version=\"3.00\">" + "\n");
        }
        else if(stype.equals("eum2-m5") || stype.equals("usm2-m5")){
            out.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + "\n");
            
            if(stype.equals("eum2-m5")) 
           	out.write("<!DOCTYPE ectd:ectd SYSTEM \"util/dtd/ich-ectd-3-2.dtd\">" + "\n" );
            else // for us
            	out.write("<!DOCTYPE ectd:ectd SYSTEM \"util/dtd/ich-ectd-3-2.dtd\">" + "\n" );
            
            out.write("<?xml-stylesheet type=\"text/xsl\" href=\"util/style/ectd-2-0.xsl\"?>" + "\n");
            if(stype.equals("eum2-m5")) 
            	out.write("<ectd:ectd xmlns:ectd=\"http://www.ich.org/ectd\" xmlns:xlink=\"http://www.w3c.org/1999/xlink\" dtd-version=\"3.2\">" + "\n");
            else
            	out.write("<ectd:ectd xmlns:ectd=\"http://www.ich.org/ectd\" xmlns:xlink=\"http://www.w3c.org/1999/xlink\" dtd-version=\"3.2\">" + "\n");
           
            out.write("<m1-administrative-information-and-prescribing-information>");
            
            if(stype.equals("eum2-m5"))
            {
            	String eumd5 = md5.getMd5HashCode(absolutePathToCreate + "/eu-regional.xml");
            	out.write("<leaf xml:lang=\"en\" checksum-type=\"md5\" modified-file=\"\" operation=\"new\" application-version=\"\" xlink:href=\"m1/eu/eu-regional.xml\" checksum=\""+eumd5+"\" ID=\"node-999\" xlink:type=\"simple\">");
            }
            else
            {
            	String usmd5 = md5.getMd5HashCode(absolutePathToCreate + "/us-regional.xml");
            	out.write("<leaf xml:lang=\"en\" checksum-type=\"md5\" modified-file=\"\" operation=\"new\" application-version=\"\" xlink:href=\"m1/us/us-regional.xml\" checksum=\""+usmd5+"\" ID=\"node-999\" xlink:type=\"simple\">");
            
            	//out.write("<leaf xml:lang=\"en\" checksum-type=\"md5\" modified-file=\"\" operation=\"new\" application-version=\"\" xlink:href=\"m1/us/us-regional.xml\" checksum=\"95d7a2cbfefe3d387d7798d397316429\" ID=\"node-999\" xlink:type=\"simple\">");
            }
            
            
            out.write("<title>");
            out.write("m1-administrative-information-and-prescribing-information</title>");
            out.write("</leaf>");
            out.write("</m1-administrative-information-and-prescribing-information>" +"\n");
        }
	}
    //if the project is not of type publish
    public void workspaceHtml(String workspaceId,PublishAttrForm publishForm,String wsDesc){     
	    try {
            
	        wsId = workspaceId;
            folderName = new StringBuffer();
            childNodeForParent = new Vector();
            childAttrForNode = new Vector();
            parentNodes = new Vector();
            childAttrId = new Vector();
            
            /*
             * Update Publish Version in WorkSpaceMst
             */
            docMgmtInt.updatePublishedVersion(wsId);
            
           // createBaseFolder();
            
            out =  new BufferedWriter(new FileWriter(absolutePathToCreate + "/index.html"));
            out.write("<HTML>");
            out.write("<body>");
            out.write("<p>&nbsp;</p>");
            out.write("<p>&nbsp;</p>");
            out.write("<table width=\"100%\" bgcolor=\"#C0C0C0\">");
	            out.write("<tr>");
	            	out.write("<td>");
	            		out.write("<p align=\"center\"><font face=\"Verdana\" size=\"3\"><b>");
	            			out.write("Index");
	            		out.write("</b></font></p>");
		            out.write("</td>");
	            out.write("</tr>");

	            out.write("<tr>");
            		out.write("<td>");
            			out.write("&nbsp;");
            		out.write("</td>");
	            out.write("</tr>");

	        parentNodes = docMgmtInt.getChildNodeByParent(iParentId, wsId);
            getChildNodeForHtml(parentNodes,absolutePathToCreate,out,iParentId,publishForm);

            out.write("</table>");
            out.write("</body>");
            out.write("</HTML>");
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void getChildNodeForHtml(Vector childNodes,String pathToCreate,BufferedWriter out,int parentId,PublishAttrForm publishForm)  throws Exception {
        int spaceForChild = 0;
        // Search In Space Vector For Parent
        for(int  j=0;j<Space.size();j++)
        {
            Object []getDtl = (Object[])Space.get(j);
            Integer parent = (Integer)getDtl[0];
            Integer space1 = (Integer)getDtl[1];
            spaceForChild = space1.intValue() + 4;
        }
        
        if(childNodes.size() == 0 ){
            	
	            out.write("<tr>");
		            out.write("<td>");
		        	for(int sCount=0;sCount<=spaceForChild;sCount++)
	            	{
	            	    out.write("&nbsp;");
	            	}
		        		out.write("<font face=\"Verdana\" size=\"2\"><b>");
		            	
		            		attrValue = copyFileforPublish(publishForm);
		            		
		            		out.write("<a href=" + attrValue + ">");
		            			out.write(nodeName);
		            		out.write("</a>");
		            	out.write("</b></font>");
		            out.write("</td>");
	            out.write("</tr>");
	            out.flush();
        }
        else{  
           	for(int i = 0;i<childNodes.size();i++){
           	    
           	    childNodeForParent = (Vector)childNodes.elementAt(i);
           	    nodeId  = (Integer)childNodeForParent.elementAt(1);
           	    nodeName = (String)childNodeForParent.elementAt(2);
           	    nodeDisplayName = (String)childNodeForParent.elementAt(3);
           	    
           	    int isLeaf = docMgmtInt.isLeafNodes(wsId,nodeId.intValue());
           	    
           	    if (isLeaf==0) {
           	        
	           	     out.write("<tr>");
			            out.write("<td>");
			            	for(int sCount=0;sCount<=spaceForChild;sCount++)
			            	{
			            	    out.write("&nbsp;");
			            	}
			            	out.write("<font face=\"Verdana\" size=\"2\"><b>");
		            			out.write(nodeDisplayName);
			            	out.write("</b></font>");
			            out.write("</td>");
		            out.write("</tr>");
		            
           	        createSubFolders(pathToCreate,(String)childNodeForParent.elementAt(4));
           	        //Add Node In Vector For Space
           	        Space.addElement(new Object[] {new Integer(parentId),new Integer(spaceForChild)});
           	    
           	    }    
           	    parentNodes = docMgmtInt.getChildNodeByParent(nodeId.intValue(), wsId);
           	    getChildNodeForHtml(parentNodes,relativePathToCreate,out,nodeId.intValue(),publishForm);
           	}
        }
    }
    
   
    public String CreatePathForEuRegional(DTOWorkSpaceNodeDetail dtownd)throws Exception {
    	
    	Vector getData = null;//docMgmtInt.getParentNoAndNodeDisplay(dtownd);
    	DTOWorkSpaceNodeDetail getPathData = (DTOWorkSpaceNodeDetail)getData.elementAt(0);
    	
    	String path = getPathData.getFolderName();
    	dtownd.setParentNodeId(getPathData.getNodeId());
    	
    	getData = null;
    	getPathData = null;
    	
    	getData = null;//docMgmtInt.getParentNoAndNodeDisplay(dtownd);
    	getPathData = (DTOWorkSpaceNodeDetail)getData.elementAt(0);
   
    	path += "//" + getPathData.getFolderName();
    	
    	return path;
    }
 
    public void copyEuRegionalFile(String stype)throws Exception {
    	String sourceFile = absolutePathToCreate + "/"+stype+"-regional.xml";
    	String destFile   = absolutePathToCreate + "/m1/"+stype+"/"+stype+"-regional.xml";
    	try{
            
    		
    		//if m1 has no attached files
    		File usregdir=new File( absolutePathToCreate + "/m1/"+stype);
    		if(!usregdir.exists())
    			usregdir.mkdirs();
    		
    		File usreg=new File(destFile);
    		if(!usreg.exists())
    		{
    			usreg.createNewFile();
    		}
    		
    		
            FileInputStream fin = new FileInputStream(sourceFile);
            FileOutputStream fout = new FileOutputStream(destFile);
            
            byte [] buf = new byte[1024 * 10];
		 	int len;
		 	
		 	while((len = fin.read(buf))> 0){
		 	    fout.write(buf,0,len);
		 	}
		 	fin.close();
		 	fout.close();
		 	File deleteFile = new File(absolutePathToCreate + "/"+stype+"-regional.xml");
		 	deleteFile.delete();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void copyUSDTDFile(String stype)throws Exception {
    	
    	
    	PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String dtdSourcePath = propertyInfo.getValue("DTDGenerated");
		
    	//String sourceFile = dtdSourcePath + "\\"+stype+"m2m5.dtd";
    	//String destFile   = absolutePathToCreate+"/util/dtd/ich-ectd-3-2.dtd";
    	
    	try{
            
         /* FileInputStream fin = new FileInputStream(sourceFile);
            FileOutputStream fout = new FileOutputStream(destFile);
            
            byte [] buf = new byte[1024 * 10];
		 	int len;
		 	
		 	while((len = fin.read(buf))> 0){
		 	    fout.write(buf,0,len);
		 	}
		 	fin.close();
		 	fout.close();*/
		 	if(stype.equals("us"))
		 	{
		 		String sourceFile = dtdSourcePath + "\\"+stype+"m1.dtd";
		 		String destFile   = absolutePathToCreate+"/util/dtd/us-regional-v2-01.dtd";
		    	
		 		FileInputStream fin = new FileInputStream(sourceFile);
		 		FileOutputStream fout = new FileOutputStream(destFile);
	            
		 		byte [] buf = new byte[1024 * 10];
    		 	int len = 0;
			 	while((len = fin.read(buf))> 0){
			 	    fout.write(buf,0,len);
			 	}
			 	fin.close();
			 	fout.close();
			 }
		// 	File deleteFile = new File(absolutePathToCreate + "/"+stype+"-regional.xml");
		// 	deleteFile.delete();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void generateSTFFile(String wsId)
	 {
	 	 
	 	 
	 	 try
		 {
	 	 	Vector getallfirstnode = new Vector();
	 		getallfirstnode = docMgmtInt.getAllSTFFirstNodes(wsId);
	 	 	generateStudyDocument(wsId,getallfirstnode);
	 	 	
	 	 	
	 	 }
	 	 catch(Exception e)
		 {
	 	 	e.printStackTrace();
		 }
	 }
	 
	 public String generateStudyIndetifier(int firstnodeId,String wsId)
	 {
	 	
	 	int previoustagid = 0;
	 	int currenttagid = 0;
	 	String tagname = "";
	 	String attrName = "";
	 	String attrValue = "";
	 	String nodeContent = "";
	 	String previoustagname = "";
	 	String previoustagcontent = "";
	 	String studyidendata = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	 	studyidendata += "<!DOCTYPE ectd:study SYSTEM \"";
	 	
	 	for(int icount=0;icount<upCount;icount++){
	 		studyidendata+="../";
		}
	 	
	 	studyidendata +="util/dtd/ich-stf-v2-2.dtd\">\n";
	 	studyidendata += "<?xml-stylesheet type=\"text/xsl\" href=\"";
	 	for(int icount=0;icount<upCount;icount++){
			
 		studyidendata+="../";
			
	 	}
	 	studyidendata +="util/style/ich-stf-stylesheet-2-2.xsl\"?>\n";
	 	studyidendata += "<ectd:study xmlns:ectd=\"http://www.ich.org/ectd\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xml:lang=\"en\" dtd-version=\"2.2\">\n";
	 	studyidendata += "<study-identifier>\n";
	 	
	 	
	 	Vector studyidentifierdata = docMgmtInt.getSTFIdentifierByNodeId(wsId,firstnodeId);
	 	//System.out.println(" FIRST NODE ID : " + firstnodeId + "  " + wsId);
	 	for(int i=0;i<studyidentifierdata.size();i++)
	 	{
	 		DTOSTFStudyIdentifierMst dto = (DTOSTFStudyIdentifierMst)studyidentifierdata.get(i);
	 		
	 		currenttagid = dto.getTagId();
	 		tagname = dto.getTagName();
		 	attrName = dto.getAttrName();
		 	attrValue = dto.getAttrValue();
		 	nodeContent = dto.getNodeContent();
		 	
		 	if(tagname.equalsIgnoreCase("study-id"))
		 		studyId=nodeContent;
		 	
	
		 	if(previoustagid > 0)
		 	{
		 		if(previoustagid == currenttagid)
		 		{
		 			
		 			// tag continue.
		 			studyidendata += " " + attrName + "=\"" + attrValue + "\"";
		 		}
		 		else
		 		{
		 			// previous tag close with nodecontent.
		 			studyidendata += ">" + previoustagcontent + "</" + previoustagname + ">\n";
		 			// new tag start.
		 	 		// creata tag.
			 		if(attrName!="" && !attrName.equals(""))
			 			studyidendata += "<" + tagname + " " + attrName + "=\"" + attrValue + "\"";
			 		else
			 			studyidendata += "<" + tagname; 
		 		}
		 	}
		 	else
		 	{
		 		// creata tag.
		 		if(attrName!="" && !attrName.equals(""))
		 			studyidendata += "<" + tagname + " " + attrName + "=\"" + attrValue + "\"";
		 		else
		 			studyidendata += "<" + tagname; 
		 	}
		 	previoustagname = tagname;
		 	previoustagcontent = nodeContent;
		 	previoustagid = currenttagid;	
	 	}
		// close last tag.
	 	studyidendata += ">" + previoustagcontent + "</" + previoustagname + ">\n";
		studyidendata += "</study-identifier>";
		
		return studyidendata;
	 	
	 }
	 
	 public String getSTFFileLocation(int nodeId)
	 {
	 	String filelocation="";
	 	//System.out.println("STFXMLLocation Size:"+STFXMLLocation.size());
	 	
	 	for(int i=0;i<STFXMLLocation.size();i++)
	 	{	
	 		DTOWorkSpaceNodeDetail dtostfxmllocation = (DTOWorkSpaceNodeDetail)STFXMLLocation.get(i);
	 		if(dtostfxmllocation.getNodeId()==nodeId)
	 		{	
	 			filelocation = dtostfxmllocation.getBaseworkfolder();
	 			
	 			return filelocation;
	 		}
	 	}
	 	return filelocation;
	 }
	 public void generateStudyDocument(String wsId,Vector stffirstnodes)
	 {
	 	try
		{
	 		String nodeName = "";
	 		int nodeId = 0;
	 		String nodecategory = "";
	 		String stfdata  = "";
	 		int stfparentnodeid=0;
	 		int nodeno=0;
	 		
	 		for(int i=0;i<stffirstnodes.size();i++)
	 		{
	 			DTOWorkSpaceNodeDetail dto = (DTOWorkSpaceNodeDetail)stffirstnodes.get(i);
	 			
		 			stfparentnodeid = dto.getNodeId();
		 			int isLeaf = docMgmtInt.isLeafNodes(wsId,stfparentnodeid);
	 				//System.out.println( " isLeaf"+isLeaf);
	 				String filelocation = getSTFFileLocation(stfparentnodeid);
	 				upCount=getUpCount(filelocation);
	 				if(isLeaf==0) 
	 				{
	 					String studyidendata = "";
	 					stfdata  = "<study-document>";
						BufferedWriter out = null;
						
	 			 	 	Vector getAllChildNodes = docMgmtInt.getAllChildSTFNodes(wsId,stfparentnodeid);
			 	 	 	//System.out.println("STF All Child Nodes Size : " + getAllChildNodes.size());
		 			  for(int j=0;j<getAllChildNodes.size();j++)
		 				{
		 					
		 					DTOSTFCategoryMst dtocategory = (DTOSTFCategoryMst)getAllChildNodes.get(j);
		 					
		 					nodeId = dtocategory.getNodeId();
		 					nodeName = dtocategory.getNodeName();
							nodecategory = dtocategory.getCategoryName();
							nodeno = dtocategory.getNodeNo();
							
							if(nodeno==1)
							{	
			 					studyidendata = generateStudyIndetifier(nodeId,wsId);
			 					fileName="STF-"+studyId + ".xml"; 
			 					//System.out.println("Writing to file  " + fileName);
								//String filelocation = getSTFFileLocation(stfparentnodeid);
								//upCount=getUpCount(filelocation);
				 		 	 	out =  new BufferedWriter(new FileWriter(filelocation+"/"+fileName));
						 	 	out.write(studyidendata);
							}
		 					else
		 					{	
		 						
		 						//if file attached at node or node
		 						if(AllNodesofHistory.contains(nodeId))
		 						{
		 						
			 						stfdata += "<doc-content xlink:href=\"";
			 						for(int icount=0;icount<upCount+1;icount++){
			 							
			 							stfdata+="../";
			 							
			 						}
			 						stfdata+="0000/index.xml" + "#node-" + nodeId + "\">\n";
			 						stfdata += "<file-tag name=\"" + nodeName +"\" info-type=\"" + nodecategory + "\"/>\n" ; 
			 						stfdata += "</doc-content>\n";
		 						
		 						
		 						}
		 					}	
							dtocategory = null;
		 				}
		 				
		 				stfdata += "</study-document></ectd:study>\n";
		 				out.write(stfdata);
		 				out.close();
		 				dto = null;
		 				getAllChildNodes = null;
	 				}		
			}
	 		
			}
			catch(Exception e)
			{
				System.out.println("Exception e : - " );
				e.printStackTrace();
			}
	 }
	 
	 
	 private int getUpCount(String filelocation)
	 {
	 	
		filelocation=filelocation.substring((absolutePathToCreate.length())+1);
	 	int count=1;
	 	for(int j=0;j<filelocation.length();j++){
	 		
	 		if('\\'== filelocation.charAt(j) || '/'== filelocation.charAt(j)){
	 			count++;
	 		}
	 	}
	 	
	 	return count;	
	 }

}//main class end