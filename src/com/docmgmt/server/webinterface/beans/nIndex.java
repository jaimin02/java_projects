package com.docmgmt.server.webinterface.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;


public class nIndex { 

	private Object docMgmtLog;
	private int userCode;
	private Vector nodeInfoVector;
	private String userType;   
	public String str ;
	public String result1;
	public String finalString;
	
	
	public nIndex(){ 
		nodeInfoVector = new Vector();
		
	} 
/**
 * 
 * @return
 * @throws Exception
 * This methods ges the remote reference by looking up to the registry
 */
DocMgmtImpl docMgmt = new DocMgmtImpl();

/**
 * 
 * @param workspaceID
 * @param userGroupCode
 * @param userCode
 * @return String
 * This method generates the html code to generate the tree structure
 * its called from workspacenodes.jsp 
 */
	private int SearchForChild(String workspaceId,int parentNodeId,Vector nodeDtl,Vector finalVector) throws Exception
	{
	    Integer parentId;
	    Integer currentId;
	    String filePath = "";
	    int Space=0;
	    ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	    
	    
	    
	    for(int i=0;i<nodeDtl.size();i++)
	    {
	         //System.out.println("SearchForChild for i = "+i);
	    	 Object[] nodeRec = (Object[])nodeDtl.get(i);
	    	 finalString = (String)nodeRec[1];
				if(finalString.contains("{")){
				int startingIndex = finalString.indexOf("{");
				int closingIndex = finalString.indexOf("}");
				result1 = finalString.substring(startingIndex , closingIndex+1);
				
				System.out.println(result1);
				finalString=finalString.replace(result1, "  ");
				System.out.println(finalString);
				}
				else{
					finalString=(String)nodeRec[1];
				}
	         parentId = (Integer) nodeRec[2];
			 currentId = (Integer) nodeRec[0];
			 Vector fileDetail = new Vector();
			 
			 if(parentId.intValue()==0)
			 {   
			     if(PresentOrNotInFinalVector(finalVector,currentId.intValue())==0)
			         finalVector.addElement(new Object[]{nodeRec[0],finalString,nodeRec[2],"-","-","-","-",new Integer(Space),"P",parentId,"-"});
			 }    
			 else if(parentId.intValue()==parentNodeId)
			 {
			     Space = getSpace(parentId.intValue(),finalVector);
	             Integer iSpace = new Integer(Space);
//	             System.out.println(iSpace);
	     
	             if(PresentOrNotInFinalVector(finalVector,currentId.intValue())==0)
			     {    
			             if(docMgmt.isLeafNodes(workspaceId,currentId.intValue())==1)
			             {  
			            // 	System.out.println("Node  " + currentId + " is a leafnode.....");
			                fileDetail = docMgmt.getFileDetailByWorkspaceIdAndNodeId(workspaceId,currentId.intValue());
			          //      System.out.println("fileDetail size is  "+ fileDetail.size() + ".....");
			                if(fileDetail.size()>0)
				            {
			                	
				                DTOWorkSpaceNodeDetail dto = (DTOWorkSpaceNodeDetail)fileDetail.elementAt(0);
				                
				                String rootPath="";
				                
				                rootPath=generateRootPath(docMgmt.getNodeDetail(dto.getWorkspaceId(),dto.getNodeId()).get(0),80,"");
				    			String targetStr="";
				    			String str=rootPath;
				    			String appendStr="<br>&nbsp;";
				    			int nextInd;
				    			System.out.println("str="+str);
				    			if (str.indexOf(';')!=-1)
				    			{
				    				while(true)
				    				{
				    					nextInd=str.indexOf(';');
				    					if (nextInd==-1)
				    						break;
				    					if (targetStr.equals(""))
				    						targetStr=str.substring(0,nextInd);
				    					else
				    						targetStr+=appendStr+"-- "+str.substring(0,nextInd);
				    					appendStr+="&nbsp;&nbsp;";
				    					str=str.substring(nextInd+1);
				    				}
				    				//Added for Last node in the hierarchy (i.e. commented node itself)
				    				targetStr+=appendStr+"-- "+str;
				    				rootPath=targetStr;
				    				System.out.println("rootpath"+targetStr);				
				    			}
				                
				                // Vector fileNameSubDtl = (Vector)fileDetail.elementAt(0);
				                //String fileName = (String)fileNameSubDtl.elementAt(2);
				                
				                String fileName = dto.getFileName();
				                String folderName=dto.getFolderName();
				                //String version = dto.getFileVersionId();
				                String version=dto.getUserDefineVersionId();
				                String userName = dto.getUserName();
				                String mDate="";
				                String ISTDateTime="";
				                String ESTDateTime="";
				                if(dto.getModifyOn()!=null){
				                	mDate = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(dto.getModifyOn());
				                }
				                if(dto.getModifyOn()!=null){
				                	mDate = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(dto.getModifyOn());
				                }
				                if(countryCode.equalsIgnoreCase("IND")){
				    				time = docMgmt.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				    				dto.setISTDateTime(time.get(0));
				    				ISTDateTime = dto.getISTDateTime();
				    			}
				    			else{
				    				time = docMgmt.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				    				dto.setISTDateTime(time.get(0));
				    				ISTDateTime = dto.getISTDateTime();
				    				dto.setESTDateTime(time.get(1));
				    				ESTDateTime = dto.getESTDateTime();
				    			}
				              //  System.out.println("dto.getModifyOn() : " + dto.getModifyOn());
				              //  System.out.println("dto.getModifyOn().toString() : " + mDate);
				                
				                String effectiveDate=dto.getEffecitveDate();
				               
				                String status = dto.getStageDesc();
				                int tranno = dto.getTranNo();
				                String workspaceid = dto.getWorkspaceId();
				                int nodeid = dto.getNodeId();
				                String baseworkfolder = dto.getBaseWorkFolder();
				                
				           //     System.out.println("Base Work Folder : " + baseworkfolder);
				                
	//			                String bwf[]=baseworkfolder.split("\\");
	//			                String folderpath="";
	//			                for(int k=0;k<bwf.length;k++){
	//			                	if(i==bwf.length-1)
	//			                		folderpath += bwf[k];
	//			                	else
	//			                		folderpath += bwf[k]+"\\\\";
	//			                }
				                
				                
				                //filePath = baseworkfolder + "/" + workspaceid + "/" + nodeid + "/" + tranno + "/" + fileName;
				                
				                //
				                filePath = baseworkfolder + folderName+ "/" + fileName;
				                
				           //     System.out.println("File Path : " + filePath);
				                
	//			              	currentId - NodeDispalyName - ParentId - FileName - C (for child)
				               	//finalVector.addElement(new Object[]{nodeRec[0],nodeRec[1],nodeRec[2],fileName,version,userName,mDate,iSpace,"C",status,filePath,parentId,effectiveDate});
				                finalVector.addElement(new Object[]{nodeRec[0],finalString,nodeRec[2],fileName,version,userName,mDate,iSpace,"C",status,filePath,parentId,effectiveDate,ISTDateTime,ESTDateTime});
				            }
			                else
			                {    
			                	
			           
//			              		currentId - NodeDispalyName - ParentId - FileName - C (for child)
			                	finalVector.addElement(new Object[]{nodeRec[0],finalString,nodeRec[2],"-","-","-","-",iSpace,"C","-",filePath,parentId,"-"});
			            
			                }
			             }
			             else
				         {    
			                 // currentId - NodeDispalyName - ParentId - FileName - P (for parent)
			                 
			           
			                 finalVector.addElement(new Object[]{nodeRec[0],finalString,nodeRec[2],"F","V","U","D",iSpace,"P","-",filePath,parentId,"-"});
			                 
				         }
				         
			     }
			     SearchForChild(workspaceId,currentId.intValue(),nodeDtl,finalVector);
			 }
	    }
	    return 0;   
	}
	
	private int getSpace(int parentId,Vector FinalVector)
	{
	    int Space = 0;
	    if(FinalVector.size() > 0)
	    {
	        for(int i=0;i<FinalVector.size();i++)
	        {
	            Object []getDtl = (Object[])FinalVector.get(i);
	            Integer parent = (Integer)getDtl[2];
	            Integer current = (Integer)getDtl[0];
	            Integer iSpace = (Integer)getDtl[7];
	            if(parent.intValue()==parentId)
	            {
	                Space = iSpace.intValue();
	                return Space;
	            }
	            else if(current.intValue()==parentId)
	            {
	                Space = iSpace.intValue();
	                return Space+1;
	            }
	        }
	    }
	    return Space;
	}
	
	private Vector getFileDetail(String workSpaceId,int currentId)
	{
	 Vector fileDetail = new Vector();   
	 try {
		
			fileDetail = docMgmt.getFileNameForNode(currentId,workSpaceId);
			return fileDetail;
		}
	catch(Exception e){
		e.printStackTrace();
	}
	return fileDetail;
	}
	
	private int PresentOrNotInFinalVector(Vector FinalVector,int nodeId)
	{
	    for(int i=0;i<FinalVector.size();i++)
	    {
	        Object[] nodeRec = (Object[])FinalVector.get(i);
	        Integer node = (Integer)nodeRec[0];
	        
	        if(node.intValue()==nodeId)
	            return 1;
	    }
	    return 0;
	}
	
	
	public Vector getTreeNodes(String workspaceID,int userGroupCode,int userCode){
		try {
			nodeInfoVector = docMgmt.getNodeAndRightDetailForIndexView(workspaceID,userGroupCode,userCode);
				Vector FinalVector = new Vector();
				//System.out.println("nodeInfoVector.size :-"+nodeInfoVector.size());
				if(nodeInfoVector.size()>0)
				{
				    Object [] nodeRec1 = (Object[])nodeInfoVector.get(0);
				    for(int i=0;i<nodeInfoVector.size();i++)
				    {    
				        //System.out.println("getTreeNodes for i = "+i);
				    	Object [] nodeRec = (Object[])nodeInfoVector.get(i);
				        Integer currentId = (Integer)nodeRec[0];
				    	int val = SearchForChild(workspaceID,currentId.intValue(),nodeInfoVector,FinalVector);
				    }	
				}
				//System.out.println("FinalVector size :- " + FinalVector.size());
				return FinalVector;
			}
		catch(Exception e){
			e.printStackTrace();
		}				
		return nodeInfoVector;

	}	
	/*public String getUserName(int userId)
	{
	    String userName=null;	
	    try {

			DTOUserMst dto = docMgmt.getUserInfo(userId);
			userName =  dto.getLoginName();
		}
	    catch(Exception e){
		e.printStackTrace();
	}				
	    return userName;
	}*/
	public String getUserName(int userId)
	{
	    String userName=null;	
	    if(userId > 0)
	    {
	    	try 
	    	{
				DTOUserMst dto = docMgmt.getUserInfo(userId);
				userName =  dto.getLoginName();
			}
		    catch(Exception e){
		    	e.printStackTrace();
		    }
	    }
		else
		{
			userName = "All";
		}
   	
	    return userName;
	}
	
	public String getLayout()
	{
		String layout="";
		layout="<layout:treeview>";
		layout +="<layout:menuItem key=\"treeview.menu1\" link=\"link1.html\"/>";
		
		layout +="<layout:menuItem key=\"treeview.menu2\">";
		layout +="<layout:menuItem key=\"treeview.menu2.submenu1\">";
		
		layout +="<layout:menuItem key=\"treeview.menu2.submenu1.item1\" link=\"link2.html\"/>";
		layout +="<layout:menuItem key=\"treeview.menu2.submenu1.item1\" link=\"link3.html\"/>";
		layout +="</layout:menuItem>";
		layout +="<layout:menuItem key=\"treeview.menu2.submenu2\" link=\"link3.html\"/>";
		layout +="<layout:menuItem key=\"treeview.menu2.submenu3\" link=\"link4.html\"/>";		
		layout +="</layout:menuItem>";
		layout +="</layout:treeview>";
		
		return layout;
	}
	
	public String generateRootPath(DTOWorkSpaceNodeDetail dtoCurrNode,int noOfChars,String prefix)
	{
		if (dtoCurrNode.getParentNodeId()==-999 || dtoCurrNode.getParentNodeId()==0)
			return ((dtoCurrNode.getNodeName().length()>noOfChars)?dtoCurrNode.getNodeName().substring(0,noOfChars)+"...":dtoCurrNode.getNodeName());
		DTOWorkSpaceNodeDetail dto=docMgmt.getNodeDetail(dtoCurrNode.getWorkspaceId(), dtoCurrNode.getParentNodeId()).get(0);
		return generateRootPath(dto,noOfChars,prefix+"") + ";" + ((dtoCurrNode.getNodeName().length()>noOfChars)?dtoCurrNode.getNodeName().substring(0,noOfChars)+"...":dtoCurrNode.getNodeName());
	}
	public String getLocDeptAndProject(String loc,String dept,String project)
	{
	    String locdeptProjectDtl = null;
	    
	    return locdeptProjectDtl;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int i) {
		userCode = i;
	}

	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
		
	
} 
