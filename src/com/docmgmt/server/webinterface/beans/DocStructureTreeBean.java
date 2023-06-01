package com.docmgmt.server.webinterface.beans;

	import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.ClientRequestID;


	public class DocStructureTreeBean { 

		private Object docMgmtLog;
		private int userCode;
		private Vector nodeInfoVector;
		private String userType;
		
		
		public DocStructureTreeBean(){ 
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
	 * @param templateID
	 * @param userGroupCode
	 * @param userCode
	 * @return String
	 * This method generates the html code to generate the tree structure
	 * its called from workspacenodes.jsp 
	 */
		public String getTemplateTreeHtml(String templateID,int userGroupCode,int userCode){
			StringBuffer htmlSB = null;//new StringBuffer();
			Vector templateTreeVector = getTreeNodes(templateID,userGroupCode,userCode);
			
			if(templateTreeVector.size() > 0){
				htmlSB = new StringBuffer();
				TreeMap childNodeHS = new TreeMap();
				Hashtable nodeDtlHS = new Hashtable();
				Integer firstNode = null;
//				Hashtable parentNodeHS = new Hashtable();
				for(int i=0; i < templateTreeVector.size(); i++){
					Object[] nodeRec = (Object[])templateTreeVector.get(i);
					Integer parentNodeId = (Integer) nodeRec[2]; //parent Id
					Integer currentNodeId = (Integer) nodeRec[0]; // node Id
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
					nodeDtlHS.put(currentNodeId,new Object[]{nodeRec[1],nodeRec[2],nodeRec[4],nodeRec[5]});
					//Object[] = Display Name,Parent Id
				}
				
				if(firstNode != null){
					Object[] firstNodeObj = (Object[]) nodeDtlHS.get(firstNode);
					
					htmlSB.append("    <DIV class=trigger onclick=\"showBranch('branch" + firstNode.intValue() + "','"+firstNode.intValue()+"');swapFolder('folder" + firstNode.intValue() + "')\">\n");
					htmlSB.append("<input type= \"checkbox\" Id ='CHK_" +firstNode.intValue()+"' name ='CHK_" +firstNode.intValue()+"' value='CHK_" +firstNode.intValue()+"'   style= \"border:0px\" >");
					htmlSB.append("<IMG id='folder" + firstNode.intValue() + "' src=\"images/closed.gif\" border=\"0\">" + "<a  href=\"DocStructureNodeAttrAction_b.do?requestId="+ ClientRequestID.WORKSPACE_NODE_RIGHTS +"&nodeId="+firstNode+"&displayName="+((String)firstNodeObj[0])+"&leaf=\"yes\"\" target=\"attributeFrame1\" >" + ((String)firstNodeObj[0]).replaceAll(" ","&nbsp;") + "</a><BR>\n");
//	                htmlSB.append("        <IMG id='folder" + firstNode.intValue() + "' src=\"images/closed.gif\" border=\"0\">"+ ((String)firstNodeObj[0]).replaceAll(" ","&nbsp;") + "\n");
					htmlSB.append("    </DIV>\n");
					htmlSB.append("    <SPAN class=\"branch\" id='branch" + firstNode.intValue() + "'>\n");
	                
					List childList = (List) childNodeHS.get(firstNode);
					for(int i=0;i<childList.size();i++){

						StringBuffer sb = getChildStructure((Integer)childList.get(i),childNodeHS,nodeDtlHS); 
						htmlSB.append(sb);
					}
					htmlSB.append("    </SPAN>\n");					
				}
				
	/*
				Set keySet = childNodeHS.keySet();
				Iterator itr = keySet.iterator();
				while(itr.hasNext()){
					Integer nodeId = (Integer) itr.next();
					List childList = (List) childNodeHS.get(nodeId);
					for(int i=0;i<childList.size();i++){

					}
				}
	*/			
			}
			if(htmlSB != null){
//				System.out.println("htmlSB " + htmlSB.toString());
	            return htmlSB.toString();
	        }
	        
	        return null;
		}
		
		private StringBuffer getChildStructure(Integer nodeId, TreeMap childNodeHS, Hashtable nodeDtlHS) {
			StringBuffer htmlStringBuffer = new StringBuffer(); 
			List childList = (List) childNodeHS.get(nodeId);
			Object[] nodeDtlObj = (Object[])nodeDtlHS.get(nodeId);
			String displayName = (String) nodeDtlObj[0];
			String NodeName = (String) nodeDtlObj[2];
			String FolderName = (String) nodeDtlObj[3];
			
			if(childList.size() > 0){
				htmlStringBuffer.append("<SPAN class=\"trigger\" onclick=\"showBranch('branch" + nodeId.intValue() + "','"+nodeId.intValue()+"');swapFolder('folder" + nodeId.intValue() + "')\">\n");
	            htmlStringBuffer.append("<input type= \"checkbox\" Id = 'CHK_" +nodeId.intValue()+"' name = 'CHK_" +nodeId.intValue()+"' value='CHK_" +nodeId.intValue()+"' style= \"border:0px\">");
	            htmlStringBuffer.append("<IMG id='folder" + nodeId.intValue() + "' src=\"images/closed.gif\">" + "<a  href=\"DocStructureNodeAttrAction_b.do?requestId="+ ClientRequestID.WORKSPACE_NODE_RIGHTS +"&nodeId="+nodeId+"&displayName="+displayName+"&leaf=\"yes\"\" target=\"attributeFrame1\" >" + NodeName.replaceAll(" ","&nbsp;") + "</a><BR>\n");
				htmlStringBuffer.append("</SPAN>\n");
				htmlStringBuffer.append("<SPAN class=\"branch\" id='branch" + nodeId.intValue() + "'>\n");
				for(int i=0;i<childList.size();i++){
					htmlStringBuffer.append(getChildStructure((Integer)childList.get(i),childNodeHS,nodeDtlHS));
				}
				htmlStringBuffer.append("</SPAN>\n");
			}
			else{
				 
	            htmlStringBuffer.append("<input type= \"checkbox\" Id ='CHK_" +nodeId.intValue()+"' name ='CHK_" +nodeId.intValue()+"' value='CHK_" +nodeId.intValue()+"'   style= \"border:0px\" >");
//	            if(getUserType().equalsIgnoreCase("WU")) {
//	           		htmlStringBuffer.append("<IMG  src=\"images/empty.gif\"><a  href=\"workspaceNodeAttrAction.do?requestId="+ ClientRequestID.WORKSPACE_NODE_ATTR +"&nodeId="+nodeId+"&leaf=\"yes\"\" target=\"attributeFrame\" >" + displayName.replaceAll(" ","&nbsp;") + "</a><BR>\n");
//	            }else if(getUserType().equalsIgnoreCase("WA")) {
	            	htmlStringBuffer.append("<IMG  src=\"images/empty.gif\"><a  href=\"DocStructureNodeAttrAction_b.do?requestId="+ ClientRequestID.WORKSPACE_NODE_RIGHTS +"&nodeId="+nodeId+"&displayName="+displayName+"&leaf=\"yes\"\" target=\"attributeFrame1\" >" + FolderName.replaceAll(" ","&nbsp;") + "</a><BR>\n");
//	            }
			}
//			System.out.println("htmlStringBuffer " + htmlStringBuffer.toString());
			return htmlStringBuffer;
		}
		
		public Vector getTreeNodes(String templateId,int userGroupCode,int userCode){
			try {
			
					nodeInfoVector = docMgmt.getTemplateForTreeDisplay(templateId);				
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
