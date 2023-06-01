package com.docmgmt.server.webinterface.beans;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.ClientRequestID;




public class WorkspaceAttachAttributeTree 
{
	
	private Object docMgmtLog;
	private int userCode;
	private Vector nodeInfoVector;
	private String userType;
	private long nodeSize;
	
	DocMgmtImpl docMgmt;
	public WorkspaceAttachAttributeTree() 
	{
		 docMgmt = new DocMgmtImpl();
	}

	   
	//public String getWorkspaceTreeHtml(String workspaceID,int userGroupCode,int userCode,File file)
	public String getWorkspaceTreeHtml(String workspaceID,int userGroupCode,int userCode)
		   {
			StringBuffer htmlSB=new StringBuffer();
			//System.out.println("Creating tree for workspace id  " + workspaceID);
			Vector workspaceTreeVector = getTreeNodes(workspaceID,userGroupCode,userCode);
			
			
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
					
					htmlSB.append("    <DIV class=trigger onclick=\"showBranch('branch" + firstNode.intValue() + "','"+firstNode.intValue()+"');swapFolder('folder" + firstNode.intValue() + "')\">\n");
					htmlSB.append("<input type= \"checkbox\" name ='CHK' value='CHK_" +firstNode.intValue()+"'   style= \"border:0px\" >");
	                htmlSB.append("        <IMG id='folder" + firstNode.intValue() + "' src=\"images/closed.gif\" border=\"0\">"+ ((String)firstNodeObj[0]).replaceAll(" ","&nbsp;") + "\n");
					htmlSB.append("    </DIV>\n");
					htmlSB.append("    <SPAN class=\"branch\" id='branch" + firstNode.intValue() + "'>\n");
	                
					List childList = (List) childNodeHS.get(firstNode);
					for(int i=0;i<childList.size();i++){

						StringBuffer sb = getChildStructure((Integer)childList.get(i),childNodeHS,nodeDtlHS); 
						htmlSB.append(sb);
					}
					htmlSB.append("    </SPAN>\n");					
				}
				
			}
			if(htmlSB != null){
		
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
			
			if(childList.size() > 0){
				htmlStringBuffer.append("<SPAN class=\"trigger\" onclick=\"showBranch('branch" + nodeId.intValue() + "','"+nodeId.intValue()+"');swapFolder('folder" + nodeId.intValue() + "')\">\n");
	            htmlStringBuffer.append("<input type= \"checkbox\" name = 'CHK' value='CHK_" +nodeId.intValue()+"' style= \"border:0px\">");
	            //htmlStringBuffer.append("<IMG id='folder" + nodeId.intValue() + "' src=\"images/closed.gif\">" + NodeName.replaceAll(" ","&nbsp;") + "<BR>\n");
	            
	            if(getUserType().equalsIgnoreCase("0003")) {
	            	htmlStringBuffer.append("<IMG id='folder" + nodeId.intValue() + "' src=\"images/closed.gif\"><a  href=\"workspaceNodeAttrAction.do?requestId="+ ClientRequestID.WORKSPACE_NODE_ATTR +"&nodeId="+nodeId+"&displayName="+displayName+"&leaf=\"yes\"\" target=\"attributeFrame\" >" + NodeName.replaceAll(" ","&nbsp;") +"(" + commentCount.intValue() + ")"+ "</a><BR>\n");
	            }else if(getUserType().equalsIgnoreCase("0002")) {
	            	htmlStringBuffer.append("<IMG id='folder" + nodeId.intValue() + "' src=\"images/closed.gif\">" + NodeName.replaceAll(" ","&nbsp;") + "<BR>\n");
	            }else if(getUserType().equalsIgnoreCase("0001")) {
	            	htmlStringBuffer.append("<IMG id='folder" + nodeId.intValue() + "' src=\"images/closed.gif\">" + NodeName.replaceAll(" ","&nbsp;") + "<BR>\n");
	            }
	            
				htmlStringBuffer.append("</SPAN>\n");
				htmlStringBuffer.append("<SPAN class=\"branch\" id='branch" + nodeId.intValue() + "'>\n");
				for(int i=0;i<childList.size();i++){

					htmlStringBuffer.append(getChildStructure((Integer)childList.get(i),childNodeHS,nodeDtlHS));
				}
				htmlStringBuffer.append("</SPAN>\n");
			}
			else{
				htmlStringBuffer.append("<input type= \"checkbox\" name ='CHK' value='CHK_" +nodeId.intValue()+"'   style= \"border:0px\" >");
				
				//htmlStringBuffer.append("<IMG  src=\"images/empty.gif\"><a  href=\"workspaceNodeAttrAction.do?requestId="+ ClientRequestID.WORKSPACE_NODE_ATTR +"&nodeId="+nodeId+"&displayName="+NodeName+"&leaf=\"yes\"\" target=\"attributeFrame\" >" + NodeName.replaceAll(" ","&nbsp;") +"(" + commentCount.intValue() + ")"+ "</a><BR>\n");
				
	            if(getUserType().equalsIgnoreCase("0003")) {
	           		htmlStringBuffer.append("<IMG  src=\"images/empty.gif\"><a  href=\"workspaceNodeAttrAction.do?requestId="+ ClientRequestID.WORKSPACE_NODE_ATTR +"&nodeId="+nodeId+"&displayName="+displayName+"&leaf=\"yes\"\" target=\"attributeFrame\" >" + FolderName.replaceAll(" ","&nbsp;") +"(" + commentCount.intValue() + ")"+ "</a><BR>\n");
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
