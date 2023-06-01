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

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;


public class JQuerySourceTreeBean {

	private Object docMgmtLog;
	private int userCode;
	private Vector nodeInfoVector;
	private String userType;
	private long nodeSize;
	DocMgmtImpl docMgmtImpl;
	DTOWorkSpaceMst wsMst;
	public JQuerySourceTreeBean(){ 
		nodeInfoVector = new Vector(); 
		docMgmtImpl = new  DocMgmtImpl();
	} 
/**
 * 
 * @return
 * @throws Exception
 * This methods ges the remote reference by looking up to the registry
 */
	  

/**
 * 
 * @param workspaceID
 * @param userGroupCode
 * @param userCode
 * @return String
 * This method generates the html code to generate the tree structure
 * its called from workspacenodes.jsp 
 */
	
	/*  comment on 8-5-2015 
	public String getWorkspaceTreeHtml(String workspaceID,int userGroupCode,int userCode){
		wsMst= docMgmtImpl.getWorkSpaceDetail(workspaceID);
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
				//htmlSB.append("<link rel=\"stylesheet\" href=\"<%=request.getContextPath()%>/css/jquery.treeview.css\" />");
				//htmlSB.append("<script src=\"<%=request.getContextPath()%>/js/jquery.js\" type=\"text/javascript\"></script>");
				//htmlSB.append("<script src=\"<%=request.getContextPath()%>/js/jquery.cookie.js\" type=\"text/javascript\"></script>");
				//htmlSB.append("<script src=\"<%=request.getContextPath()%>/js/jquery.treeview.js\" type=\"text/javascript\"></script>");
				htmlSB.append("<script type=\"text/javascript\">$(function() {$(\"#sTree\").treeview({collapsed: true,animated: \"fast\",control:\"#sidetreecontrolsTree\",persist: \"location\"});})</script>");
				htmlSB.append("<div id=\"sidetreecontrolsTree\"><a href=\"?#\"><img src=\"images/jquery_tree_img/colaps.jpg\" alt=\"Collapse All\" title=\"Collapse All\" border=\"0\"/></a> | <a href=\"?#\"><img src=\"images/jquery_tree_img/exp.jpg\" alt=\"Expand All\" title=\"Expand All\" border=\"0\"/></a></div>");
                htmlSB.append("<ul id=\"sTree\"><li><span class='mainNode'>" + ((String)firstNodeObj[0]).replaceAll(" ","&nbsp;"));
                List childList = (List) childNodeHS.get(firstNode);
				htmlSB.append("<ul>");
				for(int i=0;i<childList.size();i++){

					StringBuffer sb = getChildStructure((Integer)childList.get(i),childNodeHS,nodeDtlHS); 
					htmlSB.append(sb);
				}
				htmlSB.append("</ul>");
				htmlSB.append("</span></li></ul>");
									
			}
			
		}
		
		return htmlSB.toString();
	}
	
	*/
	
	// added on 8-5-2015 by Dharmenda jadav
	//start
	
	public String getWorkspaceTreeHtml(String workspaceID){
		wsMst= docMgmtImpl.getWorkSpaceDetail(workspaceID);
		StringBuffer htmlSB=new StringBuffer();
		//System.out.println("Creating tree for workspace id  " + workspaceID);
		Vector workspaceTreeVector = getTreeNodes(workspaceID);
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
				//htmlSB.append("<link rel=\"stylesheet\" href=\"<%=request.getContextPath()%>/css/jquery.treeview.css\" />");
				//htmlSB.append("<script src=\"<%=request.getContextPath()%>/js/jquery.js\" type=\"text/javascript\"></script>");
				//htmlSB.append("<script src=\"<%=request.getContextPath()%>/js/jquery.cookie.js\" type=\"text/javascript\"></script>");
				//htmlSB.append("<script src=\"<%=request.getContextPath()%>/js/jquery.treeview.js\" type=\"text/javascript\"></script>");
				htmlSB.append("<script type=\"text/javascript\">$(function() {$(\"#sTree\").treeview({collapsed: true,animated: \"fast\",control:\"#sidetreecontrolsTree\",persist: \"location\"});})</script>");
				htmlSB.append("<div id=\"sidetreecontrolsTree\"><a href=\"?#\"><img src=\"images/jquery_tree_img/colaps.jpg\" alt=\"Collapse All\" title=\"Collapse All\" border=\"0\"/></a> | <a href=\"?#\"><img src=\"images/jquery_tree_img/exp.jpg\" alt=\"Expand All\" title=\"Expand All\" border=\"0\"/></a></div>");
                htmlSB.append("<ul id=\"sTree\"><li><span class='mainNode'>" + ((String)firstNodeObj[0]).replaceAll(" ","&nbsp;"));
                List childList = (List) childNodeHS.get(firstNode);
				htmlSB.append("<ul>");
				for(int i=0;i<childList.size();i++){

					StringBuffer sb = getChildStructure((Integer)childList.get(i),childNodeHS,nodeDtlHS); 
					htmlSB.append(sb);
				}
				htmlSB.append("</ul>");
				htmlSB.append("</span></li></ul>");
									
			}
			
		}
		
		return htmlSB.toString();
	}
	
	//end
	private StringBuffer getChildStructure(Integer nodeId, TreeMap childNodeHS, Hashtable nodeDtlHS) {
		StringBuffer htmlStringBuffer = new StringBuffer(); 
		List childList = (List) childNodeHS.get(nodeId);
		Object[] nodeDtlObj = (Object[])nodeDtlHS.get(nodeId);
		String displayName = (String) nodeDtlObj[0];
		
		Integer nodeNo = (Integer)nodeDtlObj[8];
		String NodeName = (String)nodeDtlObj[10];
		String FolderName = (String)nodeDtlObj[2];
		
		int lastTranNo = docMgmtImpl.getMaxTranNo(wsMst.getWorkSpaceId(), nodeId);
		
		DTOWorkSpaceNodeHistory nodeHis = docMgmtImpl.getWorkspaceNodeHistorybyTranNo(wsMst.getWorkSpaceId(), nodeId, lastTranNo);
		
		if(childList.size() > 0){
            
           	htmlStringBuffer.append("<li><span class='folder' id='src_" +wsMst.getWorkSpaceId()+"_"+ nodeId.intValue() + "'>" + displayName.replaceAll(" ","&nbsp;")+"</span>");

			
			htmlStringBuffer.append("<ul>");
			
			for(int i=0;i<childList.size();i++){

				htmlStringBuffer.append(getChildStructure((Integer)childList.get(i),childNodeHS,nodeDtlHS));
			}
			htmlStringBuffer.append("</ul></li>");
		}
		else{
			
			if(nodeHis.getFileName()==null || nodeHis.getFileName().equals("No File"))
			{
				htmlStringBuffer.append("<li><span class='sNode' id='src_" +wsMst.getWorkSpaceId()+"_"+ nodeId.intValue() + "'><a id=\""+nodeNo+"\" >" + displayName.replaceAll(" ","&nbsp;")+"&nbsp;</a></span></li>");
			}
			else 
			{
				/*htmlStringBuffer.append("<li  ><a href=\"javascript:void(0);\" name=\""+PublishFilePath+"\" onclick=\"popup(this.name);\">" + displayName.replaceAll(" ","&nbsp;")+"</a>&nbsp"+image_a+"</li>");*/
				htmlStringBuffer.append("<li><span class='file'><a id=\""+nodeNo+"\" ><label class='srcTreeItem' id='src_" +wsMst.getWorkSpaceId()+"_"+ nodeId.intValue() + "'>" + displayName.replaceAll(" ","&nbsp;")+"<img class='src_" +wsMst.getWorkSpaceId()+"_"+ nodeId.intValue() + "' style=\"display:none;\"/></label></a></span></li>");
			}	
		    
		}
		return htmlStringBuffer;
		
	}

	/* comment on 8-5-2015
	 public Vector getTreeNodes(String workspaceID,int userGroupCode,int userCode){
		try {
				nodeInfoVector = docMgmtImpl.getNodeAndRightDetail(workspaceID,userGroupCode,userCode);				
				return nodeInfoVector;
			}
		catch(Exception e){
			e.printStackTrace();
		}				
		return nodeInfoVector;

	}*/
	
	
	// added on  8-5-2015 By Dharmendra jadav
	//start

	public Vector getTreeNodes(String workspaceID) {
		try {

			nodeInfoVector = docMgmtImpl.getNodeDetails(workspaceID);
			return nodeInfoVector;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeInfoVector;

	}
	 
	//end

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
