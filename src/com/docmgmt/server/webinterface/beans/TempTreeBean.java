package com.docmgmt.server.webinterface.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.ClientRequestID;

public class TempTreeBean { 

	private Object docMgmtLog;
	private int userCode;
	private Vector nodeInfoVector;
	private String userType;
	private long nodeSize;
	private int refreshNodeId =-1;
	private Vector<DTOWorkSpaceNodeAttribute> allNodesType0002Attrs;
	
	public TempTreeBean(){ 
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
	
	   
public String getWorkspaceTreeHtml(String workspaceID,int userGroupCode,int userCode,File file)
//public String getWorkspaceTreeHtml(String workspaceID,int userGroupCode,int userCode)
	   {
		StringBuffer htmlSB=new StringBuffer();
		//System.out.println("Creating tree for workspace id  " + workspaceID);
		Vector workspaceTreeVector = getTreeNodes(workspaceID,userGroupCode,userCode);
		
		//Get all parent level attributes of all nodes in the workspace.
		allNodesType0002Attrs = docMgmt.getNodeAttributes(workspaceID,-1);
		
		int srno=0;
		
		
		
		
		if(workspaceTreeVector.size() > 0){
			htmlSB = new StringBuffer();
			TreeMap childNodeHS = new TreeMap();
			Hashtable nodeDtlHS = new Hashtable();
			Integer firstNode = null;
			nodeSize=workspaceTreeVector.size();
			for(int i=0; i < workspaceTreeVector.size(); i++){
				Object[] nodeRec = (Object[])workspaceTreeVector.get(i);
				Integer parentNodeId = (Integer) nodeRec[2];
				Integer currentNodeId = (Integer) nodeRec[0];
				if(i==0){
					firstNode = currentNodeId; 
				}
				if(childNodeHS.containsKey(parentNodeId)){	
									
					List childList = (List) childNodeHS.get(parentNodeId); 
					childList.add(currentNodeId);
					if(!childNodeHS.containsKey(currentNodeId)){
						childNodeHS.put(currentNodeId,new ArrayList());
					}
				}
				else{
					List childList = new ArrayList();
					childList.add(currentNodeId);
					childNodeHS.put(parentNodeId,childList);
					if(!childNodeHS.containsKey(currentNodeId)){
						childNodeHS.put(currentNodeId,new ArrayList());
					}
				}
				
				nodeDtlHS.put(currentNodeId,new Object[]{nodeRec[1],nodeRec[2],nodeRec[3],nodeRec[4],nodeRec[5],nodeRec[6],nodeRec[7],nodeRec[8],nodeRec[9],nodeRec[10],nodeRec[11]});
			}
			
			if(firstNode != null){
				Object[] firstNodeObj = (Object[]) nodeDtlHS.get(firstNode);
				String attributeString = getAttributeString(firstNode.intValue());
				htmlSB.append("    <DIV class=trigger onclick=\"showBranch('branch" + firstNode.intValue() + "','"+firstNode.intValue()+"');swapFolder('folder" + firstNode.intValue() + "')\">\n");
				htmlSB.append("<input type= \"checkbox\" name ='CHK' value='CHK_" +firstNode.intValue()+"'   style= \"border:0px\" >");
                htmlSB.append("        <IMG id='folder" + firstNode.intValue() + "' src=\"images/closed.gif\" border=\"0\">"+ ((String)firstNodeObj[0]).replaceAll(" ","&nbsp;"));
                if (!attributeString.equals("") && attributeString.length() > 0)
        			htmlSB.append("[" + attributeString + "]");
                htmlSB.append("\n");
				htmlSB.append("    </DIV>\n");
				htmlSB.append("    <SPAN  class=\"branch\" id='branch" + firstNode.intValue() + "'>\n");
                
				List childList = (List) childNodeHS.get(firstNode);
				for(int i=0;i<childList.size();i++){

					StringBuffer sb = getChildStructure((Integer)childList.get(i),childNodeHS,nodeDtlHS); 
					htmlSB.append(sb);
				}
				htmlSB.append("    </SPAN>\n");					
			}
			
		}
		if(htmlSB != null){
		/*	try{
				Writer output=new BufferedWriter(new FileWriter(file));
				output.write(htmlSB.toString());
				output.close();
				System.out.println("File  " + file + " created");
			}catch(IOException e){
				System.out.println("Exception while creating file : " + e);
			}*/
			return htmlSB.toString();
        }
		return null;
	}
	private StringBuffer getChildStructure(Integer nodeId, TreeMap childNodeHS, Hashtable nodeDtlHS) {
		
		
		StringBuffer htmlStringBuffer = new StringBuffer(); 
		List childList = (List) childNodeHS.get(nodeId);
		Object[] nodeDtlObj = (Object[])nodeDtlHS.get(nodeId);
		String displayName = (String) nodeDtlObj[0];
		Integer commentCount =(Integer) nodeDtlObj[8];
		Integer nodeNo = (Integer)nodeDtlObj[9];
		String NodeName = (String)nodeDtlObj[10];
		String FolderName = (String)nodeDtlObj[2];
		String attributeString = getAttributeString(nodeId.intValue());
		if(childList.size() > 0){
			htmlStringBuffer.append("<SPAN class=\"trigger\" onclick=\"showBranch('branch" + nodeId.intValue() + "','"+nodeId.intValue()+"');swapFolder('folder" + nodeId.intValue() + "')\">\n");
            htmlStringBuffer.append("<input type= \"checkbox\" name = 'CHK' value='CHK_" +nodeId.intValue()+"' style= \"border:0px\">");
            //htmlStringBuffer.append("<IMG id='folder" + nodeId.intValue() + "' src=\"images/closed.gif\">" + NodeName.replaceAll(" ","&nbsp;") + "<BR>\n");
                if(getUserType().equalsIgnoreCase("0003")) {
	            	htmlStringBuffer.append("<IMG id='folder" + nodeId.intValue() + "' src=\"images/closed.gif\"><a  href=\"workspaceNodeAttrAction.do?requestId="+ ClientRequestID.WORKSPACE_NODE_ATTR +"&nodeId="+nodeId+"&displayName="+displayName+"&leaf=\"yes\"\" target=\"attributeFrame\" title=\""+displayName+"\">" + NodeName.replaceAll(" ","&nbsp;"));
	            	if (!attributeString.equals("") && attributeString.length() > 0)
	            		htmlStringBuffer.append("[" + attributeString + "]");
	            	htmlStringBuffer.append("</a><BR>\n");
	            }else if(getUserType().equalsIgnoreCase("0002")) {
	            	htmlStringBuffer.append("<IMG  id='folder" + nodeId.intValue() + "' src=\"images/closed.gif\"><a  href=\"WorkspaceNodeRights.do?nodeId="+nodeId.intValue()+"\" target=\"attributeFrame\" >" + NodeName.replaceAll(" ","&nbsp;") + "</a><BR>\n");
	            }else if(getUserType().equalsIgnoreCase("0001")) {
	            	htmlStringBuffer.append("<IMG id='folder" + nodeId.intValue() + "' src=\"images/closed.gif\"><a  href=\"WorkspaceNodeRights.do?nodeId="+nodeId.intValue()+"\" target=\"attributeFrame\" >" + NodeName.replaceAll(" ","&nbsp;") + "</a><BR>\n");
	            }
			htmlStringBuffer.append("</SPAN>\n");
			if(refreshNodeId == nodeId.intValue())
			{
				System.out.println(refreshNodeId);
				htmlStringBuffer.append("<SPAN class=\"branch\" id='branch" + nodeId.intValue() + "' style=\"display:block;\">\n");
			}
			else
			{
				htmlStringBuffer.append("<SPAN class=\"branch\" draggable=\"true\" ondragstart=\"alert('ok')\" id='branch" + nodeId.intValue() + "'>\n");
			}
			for(int i=0;i<childList.size();i++){

				htmlStringBuffer.append(getChildStructure((Integer)childList.get(i),childNodeHS,nodeDtlHS));
			}
			htmlStringBuffer.append("</SPAN>\n");
		}
		else{
			htmlStringBuffer.append("<input type= \"checkbox\"  name ='CHK' value='CHK_" +nodeId.intValue()+"'   style= \"border:0px\" >");
			
			//htmlStringBuffer.append("<IMG  src=\"images/empty.gif\"><a  href=\"workspaceNodeAttrAction.do?requestId="+ ClientRequestID.WORKSPACE_NODE_ATTR +"&nodeId="+nodeId+"&displayName="+NodeName+"&leaf=\"yes\"\" target=\"attributeFrame\" >" + NodeName.replaceAll(" ","&nbsp;") +"(" + commentCount.intValue() + ")"+ "</a><BR>\n");
	            if(getUserType().equalsIgnoreCase("0003")) {
	           		htmlStringBuffer.append("<IMG  src=\"images/empty.gif\"><a  href=\"workspaceNodeAttrAction.do?requestId="+ ClientRequestID.WORKSPACE_NODE_ATTR +"&nodeId="+nodeId+"&displayName="+displayName+"&leaf=\"yes\"\" target=\"attributeFrame\" title=\""+displayName+"\">" + FolderName.replaceAll(" ","&nbsp;"));
	           		if(commentCount.intValue() != 0 )
	           		{
	           			htmlStringBuffer.append("(" + commentCount.intValue() + ")");
	           		}
	           		htmlStringBuffer.append("</a><BR>\n");
	            }else if(getUserType().equalsIgnoreCase("0002")) {
	            	htmlStringBuffer.append("<IMG  src=\"images/empty.gif\"><a  href=\"WorkspaceNodeRights.do?requestId="+ ClientRequestID.WORKSPACE_NODE_RIGHTS +"&nodeId="+nodeId+"&displayName="+displayName+"&leaf=\"yes\"\" target=\"attributeFrame\" >" + FolderName.replaceAll(" ","&nbsp;") + "</a><BR>\n");
	            }else if(getUserType().equalsIgnoreCase("0001")) {
	            	htmlStringBuffer.append("<IMG  src=\"images/empty.gif\"><a  href=\"WorkspaceNodeRights.do?requestId="+ ClientRequestID.WORKSPACE_NODE_RIGHTS +"&nodeId="+nodeId+"&displayName="+displayName+"&leaf=\"yes\"\" target=\"attributeFrame\" >" + FolderName.replaceAll(" ","&nbsp;") + "</a><BR>\n");
	            }
		}
		
		return htmlStringBuffer;
	}

	public Vector getTreeNodes(String workspaceID,int userGroupCode,int userCode){
		try {
				nodeInfoVector = docMgmt.getNodeAndRightDetail(workspaceID,userGroupCode,userCode);
				return nodeInfoVector;
			}
		catch(Exception e){
			e.printStackTrace();
		}				
		return nodeInfoVector;

	}
	
	private String getAttributeString(int nodeId)
	{
		String attributeString = "";
		for (int indAttr = 0;indAttr < allNodesType0002Attrs.size();indAttr++)
		{
			DTOWorkSpaceNodeAttribute nodeAttribute = allNodesType0002Attrs.get(indAttr);
			if (nodeAttribute.getNodeId() == nodeId && nodeAttribute.getAttrValue()!=null && !nodeAttribute.getAttrValue().equals(""))
				attributeString += nodeAttribute.getAttrValue() + ",";
		}
		if (!attributeString.equals("") && attributeString.endsWith(","))
			attributeString = attributeString.substring(0,attributeString.length()-1);
		return attributeString;
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
	
	public int getRefreshNodeId() {
		return refreshNodeId;
	}
	public void setRefreshNodeId(int refreshNodeId) {
		this.refreshNodeId = refreshNodeId;
	}
	
	
	public String checkIfFilePresent(String workspaceID,int userGroupCode,int userCode){
		
		String htmlSB;
		String path="";
		PropertyInfo propInfo=PropertyInfo.getPropInfo();
		//path=propInfo.getValue("UserFilePath");
		
		if(getUserType().equalsIgnoreCase("0002")) {
			path=propInfo.getValue("AdminFilePath");
		}else if(getUserType().equalsIgnoreCase("0003")){
			path=propInfo.getValue("UserFilePath");
		}else if(getUserType().equalsIgnoreCase("0001")){
			path=propInfo.getValue("SuperUserFilePath");
		}
		String fileName="tree" + workspaceID + ".txt";
		String filePath=path + "/"+  fileName;
		
		System.out.println("Path is  " + path);
		
		if(new File(filePath).exists()){
			htmlSB=readFile(new File(filePath));
		}else{
			if(!new File(path).exists()){
				new File(path).mkdir();
			}
			htmlSB=getWorkspaceTreeHtml(workspaceID,userGroupCode,userCode,new File(filePath));
		}
		
		return htmlSB;
	}
	
	public String readFile(File file){
		StringBuffer sb=new StringBuffer();
		
		System.out.println("Reading from file  " + file.getName());
		try{
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
	    	in.close();
		}catch(IOException e){
			System.out.println("Exception while reading from file : " + e);
		}
    	return sb.toString();
	}
	
} 
