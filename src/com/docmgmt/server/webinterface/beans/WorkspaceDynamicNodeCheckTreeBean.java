package com.docmgmt.server.webinterface.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;

public class WorkspaceDynamicNodeCheckTreeBean { 

	private int userCode;
	private Vector<Object[]> nodeInfoVector;
	private String userType;
	private ArrayList<Integer> selectedNodes;
	private boolean chkBoxForParents = false;
	private boolean chkBoxForAllNodes = false;	
	private boolean selectedNodeTree = false;
	private Map<Integer, String> htmlAfterNodes = new HashMap<Integer, String>();
	private Map<Integer, String> htmlAfterNodesData = new HashMap<Integer, String>();
	
	
	public WorkspaceDynamicNodeCheckTreeBean(){ 
		nodeInfoVector = new Vector<Object[]>(); 
	} 

   DocMgmtImpl docMgmt = new DocMgmtImpl();

    public String getWorkspaceTreeHtml(String workspaceID,int userGroupCode,int userCode,Vector<DTOWorkSpaceNodeHistory> dynamicNodesCheckList){
		this.selectedNodes = getNodeIdsInList(dynamicNodesCheckList);
		return getWorkspaceTreeHtml(workspaceID,userGroupCode,userCode);
	}
    
    
    //method added by dharmendra jadav on 21-4-2015
    
    public String getWorkspaceTreeHtmlData(String workspaceID,int userGroupCode,int userCode,Vector<DTOWorkSpaceNodeHistory> dynamicNodesCheckList){
		this.selectedNodes = getNodeIdsInList(dynamicNodesCheckList);
		return getWorkspaceTreeHtmlData(workspaceID,userGroupCode,userCode);
	}
    
    public String getSelectedNodeTreeHtml(String workspaceID,int userGroupCode,int userCode,ArrayList<Integer> selectedNodes){
		   
		this.selectedNodes = selectedNodes;
		return getWorkspaceTreeHtml(workspaceID,userGroupCode,userCode);
	}
    
    // method added by dharmendra jadav on 21-4-2015
    
    public String getSelectedNodeTreeHtmlData(String workspaceID,int userGroupCode,int userCode,ArrayList<Integer> selectedNodes){
		   
		this.selectedNodes = selectedNodes;
		return getWorkspaceTreeHtmlData(workspaceID,userGroupCode,userCode);
	}
    			
	private String getWorkspaceTreeHtml(String workspaceID,int userGroupCode,int userCode){
		StringBuffer htmlSB=new StringBuffer();
		Vector<Object[]> workspaceTreeVector = getTreeNodes(workspaceID,userGroupCode,userCode);
		if(workspaceTreeVector.size() > 0){
			htmlSB = new StringBuffer();
			Map<Integer, List<Integer>> childNodeHS = new HashMap<Integer, List<Integer>>();
			Hashtable<Integer, Object[]> nodeDtlHS = new Hashtable<Integer, Object[]>();
			Integer firstNode = null;
			
			for(int i=0; i < workspaceTreeVector.size(); i++){
				Object[] nodeRec = workspaceTreeVector.get(i);
				Integer parentNodeId = (Integer) nodeRec[2];
				Integer currentNodeId = (Integer) nodeRec[0];
				if(i==0){
					firstNode = currentNodeId; 
				}
				if(childNodeHS.containsKey(parentNodeId)){	
					List<Integer> childList =  childNodeHS.get(parentNodeId); 
					childList.add(currentNodeId);
					//If above is the first child of the parent then add one hardcode child 
					if(childList.size() == 1){
						//Adding One hard code child. It will be used while removing the extra nodes in 'removeExtraNodes'
						childList.add(-9999);
					}
					if(!childNodeHS.containsKey(currentNodeId)){
						childNodeHS.put(currentNodeId,new ArrayList<Integer>());
					}
				}
				else{
					List<Integer> childList = new ArrayList<Integer>();
					childList.add(currentNodeId);
					childNodeHS.put(parentNodeId,childList);
					
					ArrayList<Integer> currNodeChildList = new ArrayList<Integer>();
					//Adding One hard code child. It will be used while removing the extra nodes in 'removeExtraNodes'
					currNodeChildList.add(-9999);
					if(!childNodeHS.containsKey(currentNodeId)){
						childNodeHS.put(currentNodeId,currNodeChildList);
					}else{
						List<Integer> nodeChildList = childNodeHS.get(currentNodeId);
						if (nodeChildList.size() == 0 ) {//if size is 0 then also need to add the new childList.
							childNodeHS.put(currentNodeId,currNodeChildList);
						}
					}
				}
				nodeDtlHS.put(currentNodeId,new Object[]{nodeRec[1],nodeRec[2],nodeRec[3],nodeRec[4],nodeRec[5],nodeRec[6],nodeRec[7],nodeRec[8],nodeRec[9],nodeRec[10],nodeRec[11]});
			}
			
			/****************************************************************************************/
			if(selectedNodeTree){
				removeExtraNodes(childNodeHS,selectedNodes);
			}
			/****************************************************************************************/
			
			if(firstNode != null){
				Object[] firstNodeObj = nodeDtlHS.get(firstNode);
				
				 htmlSB.append("    <DIV class=trigger onclick=\"showBranch('branch" + firstNode.intValue() + "','" + firstNode.intValue() + "');swapFolder('folder" + firstNode.intValue() + "')\">");
				 if(chkBoxForAllNodes || chkBoxForParents){
					 htmlSB.append("<input type= \"checkbox\" id ='CHK_" + firstNode.intValue() + "' name ='CHK_" + firstNode.intValue() + "' value='CHK_" + firstNode.intValue() + "' style= \"border:0px\" onClick = \" return filledHiddenValue('" + firstNode.intValue() + "');\">");
				 }
	             htmlSB.append("        <IMG id='folder" + firstNode.intValue() + "' src=\"images/closed.gif\" border=\"0\">" + ((String)firstNodeObj[0]).replaceAll(" ", "&nbsp;"));
	             if(htmlAfterNodes.get(firstNode) != null){
	            	 htmlSB.append(htmlAfterNodes.get(firstNode));
	             }
	             htmlSB.append("    </DIV>\n");
	             htmlSB.append("    <SPAN class=\"branch\" id='branch" + firstNode.intValue() + "'>\n");
	            
				List<Integer> childList = childNodeHS.get(firstNode);
				if(childList != null){
					for(int i=0;i<childList.size();i++){

						StringBuffer sb = getChildStructure(childList.get(i),childNodeHS,nodeDtlHS); 
						htmlSB.append(sb);
					}
				}
				htmlSB.append("    </SPAN>\n");					
			}
		}
		return htmlSB.toString();
	}
	
	
	//method added by dharmendra jadav on 21-4-2015
	
	
	private String getWorkspaceTreeHtmlData(String workspaceID,int userGroupCode,int userCode){

		StringBuffer htmlSB=new StringBuffer();
		Vector<Object[]> workspaceTreeVector = getTreeNodes(workspaceID,userGroupCode,userCode);
		if(workspaceTreeVector.size() > 0){
			htmlSB = new StringBuffer();
			Map<Integer, List<Integer>> childNodeHS = new HashMap<Integer, List<Integer>>();
			Hashtable<Integer, Object[]> nodeDtlHS = new Hashtable<Integer, Object[]>();
			Integer firstNode = null;
			
			for(int i=0; i < workspaceTreeVector.size(); i++){
				Object[] nodeRec = workspaceTreeVector.get(i);
				Integer parentNodeId = (Integer) nodeRec[2];
				Integer currentNodeId = (Integer) nodeRec[0];
				if(i==0){
					firstNode = currentNodeId; 
				}
				if(childNodeHS.containsKey(parentNodeId)){	
					List<Integer> childList =  childNodeHS.get(parentNodeId); 
					childList.add(currentNodeId);
					//If above is the first child of the parent then add one hardcode child 
					if(childList.size() == 1){
						//Adding One hard code child. It will be used while removing the extra nodes in 'removeExtraNodes'
						childList.add(-9999);
					}
					if(!childNodeHS.containsKey(currentNodeId)){
						childNodeHS.put(currentNodeId,new ArrayList<Integer>());
					}
				}
				else{
					List<Integer> childList = new ArrayList<Integer>();
					childList.add(currentNodeId);
					childNodeHS.put(parentNodeId,childList);
					
					ArrayList<Integer> currNodeChildList = new ArrayList<Integer>();
					//Adding One hard code child. It will be used while removing the extra nodes in 'removeExtraNodes'
					currNodeChildList.add(-9999);
					if(!childNodeHS.containsKey(currentNodeId)){
						childNodeHS.put(currentNodeId,currNodeChildList);
					}else{
						List<Integer> nodeChildList = childNodeHS.get(currentNodeId);
						if (nodeChildList.size() == 0 ) {//if size is 0 then also need to add the new childList.
							childNodeHS.put(currentNodeId,currNodeChildList);
						}
					}
				}
				nodeDtlHS.put(currentNodeId,new Object[]{nodeRec[1],nodeRec[2],nodeRec[3],nodeRec[4],nodeRec[5],nodeRec[6],nodeRec[7],nodeRec[8],nodeRec[9],nodeRec[10],nodeRec[11]});
			}
			
			/****************************************************************************************/
			if(selectedNodeTree){
				removeExtraNodes(childNodeHS,selectedNodes);
			}
			/****************************************************************************************/
			
			if(firstNode != null){
				
				List<Integer> childList = childNodeHS.get(firstNode);
				
				if(childList != null){
				
					for(int i=0;i<childList.size();i++){ 
					
						StringBuffer sb = getChildStructureData(childList.get(i),childNodeHS,nodeDtlHS); 
						htmlSB.append(sb);
					
					}
					
				}
			
			}
		}
		return htmlSB.toString();
	}
	
	
	private StringBuffer getChildStructure(Integer nodeId, Map<Integer, List<Integer>> childNodeHS, Hashtable<Integer, Object[]> nodeDtlHS) {
		if(nodeId == -9999){
			//Cannot create node for hardcode value -9999
			return new StringBuffer();
		}
		
		StringBuffer htmlStringBuffer = new StringBuffer(); 
		List<Integer> childList = childNodeHS.get(nodeId);
		Object[] nodeDtlObj = nodeDtlHS.get(nodeId);
		String NodeName = (String)nodeDtlObj[10];
		String FolderName = (String)nodeDtlObj[2];
		
		if(childList.size() > 0 ){
			htmlStringBuffer.append("<SPAN class=\"trigger\" onclick=\"showBranch('branch" + nodeId.intValue() + "','" + nodeId.intValue() + "');swapFolder('folder" + nodeId.intValue() + "')\">");
	         if(chkBoxForAllNodes || chkBoxForParents || selectedNodes.contains(nodeId)){
	        	 htmlStringBuffer.append("<input type= \"checkbox\" id = 'CHK_" + nodeId.intValue() + "' name = 'CHK_" + nodeId.intValue() + "' value='CHK_" + nodeId.intValue() + "'  style= \"border:0px\" onClick = \" return filledHiddenValue('" + nodeId.intValue() + "');\">");
	         }
			 htmlStringBuffer.append("<IMG id='folder" + nodeId.intValue() + "' src=\"images/closed.gif\">" + NodeName);
			 if(htmlAfterNodes.get(nodeId) != null){
				 htmlStringBuffer.append(htmlAfterNodes.get(nodeId));
             }
			 htmlStringBuffer.append("<BR/>");
	         htmlStringBuffer.append("</SPAN>\n");
	         htmlStringBuffer.append("<SPAN class=\"branch\" id='branch" + nodeId.intValue() + "'>");
	         for(int i=0;i<childList.size();i++){
	        	 if (childNodeHS.containsKey(childList.get(i))) {
	        		 htmlStringBuffer.append(getChildStructure(childList.get(i),childNodeHS,nodeDtlHS));
				}
			}
			htmlStringBuffer.append("</SPAN>\n");
		}
		else{
			if(chkBoxForAllNodes || selectedNodes.contains(nodeId)){
				htmlStringBuffer.append("<input type= \"checkbox\" id = 'CHK_" + nodeId.intValue() + "' name ='CHK_" + nodeId.intValue() + "' value='CHK_" + nodeId.intValue() + "' style= \"border:0px\" onClick = \" return filledHiddenValue('" + nodeId.intValue() + "');\">");
			}
			htmlStringBuffer.append("<IMG  src=\"images/empty2.gif\">" +"<a href = \"#\">"+ FolderName.replaceAll(" ","&nbsp;") + "</a>");
			if(htmlAfterNodes.get(nodeId) != null){
				 htmlStringBuffer.append(htmlAfterNodes.get(nodeId));
            }
			htmlStringBuffer.append("<BR/>");
      }
		
		return htmlStringBuffer;
	}

	
	//method added by dharmendra jadav on 21-4-2015
	
	private StringBuffer getChildStructureData(Integer nodeId, Map<Integer, List<Integer>> childNodeHS, Hashtable<Integer, Object[]> nodeDtlHS) {

		if(nodeId == -9999){
			//Cannot create node for hardcode value -9999
			return new StringBuffer();
		}
		
		StringBuffer htmlStringBuffer = new StringBuffer(); 
		List<Integer> childList = childNodeHS.get(nodeId);
		Object[] nodeDtlObj = nodeDtlHS.get(nodeId);
		String NodeName = (String)nodeDtlObj[10];
		String FolderName = (String)nodeDtlObj[2];
		
		if(childList.size() > 0 ){
			
			
	         if(chkBoxForAllNodes || chkBoxForParents || selectedNodes.contains(nodeId)){
	        	
	         }
	         htmlStringBuffer.append("<ul>");
			 if(htmlAfterNodesData.get(nodeId) != null){
				 htmlStringBuffer.append("<li> " + NodeName);
				 htmlStringBuffer.append(htmlAfterNodesData.get(nodeId)+"</li>");
             }
			
	         for(int i=0;i<childList.size();i++){
	        	 
	        	 if (childNodeHS.containsKey(childList.get(i))) {
	        		 htmlStringBuffer.append(getChildStructureData(childList.get(i),childNodeHS,nodeDtlHS));
				}
			}
	         htmlStringBuffer.append("</ul>");

		}
		else{
			if(chkBoxForAllNodes || selectedNodes.contains(nodeId)){

			}
			  htmlStringBuffer.append("<ul>");
			htmlStringBuffer.append("<li><a href = \"#\">"+ FolderName.replaceAll(" ","&nbsp;") + "</a>");
			if(htmlAfterNodesData.get(nodeId) != null){
				 htmlStringBuffer.append(htmlAfterNodesData.get(nodeId)+"</li>");
				 htmlStringBuffer.append("</ul>");
            }

      }
		
		return htmlStringBuffer;
	}

	
	private Vector<Object[]> getTreeNodes(String workspaceID,int userGroupCode,int userCode){
		try {
				nodeInfoVector = docMgmt.getNodeAndRightDetail(workspaceID,userGroupCode,userCode);				
				return nodeInfoVector;
			}
		catch(Exception e){
			e.printStackTrace();
		}				
		return nodeInfoVector;
	}
	private ArrayList<Integer> getNodeIdsInList(Vector<DTOWorkSpaceNodeHistory> dynamicNodesCheckList) 
	{
		ArrayList<Integer> nodeIdList = new ArrayList<Integer>();
		for (int itrNodeChkList = 0; itrNodeChkList < dynamicNodesCheckList.size(); itrNodeChkList++)
		{
			if(!nodeIdList.contains(dynamicNodesCheckList.get(itrNodeChkList).getNodeId()))
				nodeIdList.add(dynamicNodesCheckList.get(itrNodeChkList).getNodeId());
		}
		return nodeIdList;
	}
	private void removeExtraNodes(Map<Integer, List<Integer>> allNodesChildList,ArrayList<Integer> selectedNodes) 
	{
		Set<Integer> keySet = allNodesChildList.keySet();
		List<Integer> nodeIds = new ArrayList<Integer>(); 
		for (Iterator<Integer> itrKeySet = keySet.iterator(); itrKeySet.hasNext();) {
			Integer nodeId = itrKeySet.next();
			nodeIds.add(nodeId);
		}
		while(true){
			boolean nodesRemoved = false;
			for (Iterator<Integer> itrNodeIds = nodeIds.iterator(); itrNodeIds.hasNext();) {
				Integer removeNodeId = itrNodeIds.next();
				
				if((allNodesChildList.get(removeNodeId) !=null) && (allNodesChildList.get(removeNodeId).size() == 0) && !selectedNodes.contains(removeNodeId)){
					
					nodesRemoved = true;
					allNodesChildList.remove(removeNodeId);
					
					for (Iterator<Integer> itrKeySet = keySet.iterator(); itrKeySet.hasNext();) {
						Integer nodeId = itrKeySet.next();
						List<Integer> childList = allNodesChildList.get(nodeId);
						childList.remove(removeNodeId);
						if((childList.size()==1) && (childList.get(0)==-9999) && (!selectedNodes.contains(nodeId))){
							childList.clear();
						}
						
					}
				}
			}
			if(!nodesRemoved){
				break;
			}
		}
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
	
	public boolean isChkBoxForParents() {
		return chkBoxForParents;
	}
	
	public void setChkBoxForParents(boolean chkBoxForParents) {
		this.chkBoxForParents = chkBoxForParents;
	}
	
	public boolean isChkBoxForAllNodes() {
		return chkBoxForAllNodes;
	}
	
	public void setChkBoxForAllNodes(boolean chkBoxForAllNodes) {
		this.chkBoxForAllNodes = chkBoxForAllNodes;
	}
	
	public boolean isSelectedNodeTree() {
		return selectedNodeTree;
	}
	
	public void setSelectedNodeTree(boolean selectedNodeTree) {
		this.selectedNodeTree = selectedNodeTree;
	}
	public Map<Integer, String> getHtmlAfterNodes() {
		return htmlAfterNodes;
	}
	public void setHtmlAfterNodes(Map<Integer, String> htmlAfterNodes) {
		this.htmlAfterNodes = htmlAfterNodes;
	}


	public void setHtmlAfterNodesData(Map<Integer, String> htmlAfterNodesData) {
		this.htmlAfterNodesData = htmlAfterNodesData;
	}


	public Map<Integer, String> getHtmlAfterNodesData() {
		return htmlAfterNodesData;
	}
} 
