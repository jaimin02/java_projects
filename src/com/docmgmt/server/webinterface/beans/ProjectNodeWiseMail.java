package com.docmgmt.server.webinterface.beans;

	import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;


	public class ProjectNodeWiseMail { 

		private DTOWorkSpaceMst dtows;
		private ArrayList<DTOWorkSpaceNodeHistory> allNodesLastHistory;
		private ArrayList<DTOWorkSpaceNodeAttribute> allNodesType0002Attrs;
		private Vector<DTOWorkSpaceNodeAttribute> projectLvlAttrs;

		public static final short TREE_HTML = 1;
		public static final short TABLE_HTML = 2;
		public int maxStage;
		private int mode;
		public String str ;
		public String result1;
		public String finalString;
		private boolean isOddRow = true;	
		private Map<Integer,DTOUserMst>allUsers;
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
		DateFormat attrDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		public String userTypeName = ActionContext.getContext().getSession().get("usertypename").toString();
		
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		
		public ProjectNodeWiseMail(){ 
			docMgmtImpl = new DocMgmtImpl();
			//Get all users
			allUsers = new HashMap<Integer, DTOUserMst>();
			for (DTOUserMst dtoUserMst : docMgmtImpl.getAllUserDetail()) {
				allUsers.put(dtoUserMst.getUserCode(), dtoUserMst);
			}
		} 
		
		DocMgmtImpl docMgmtImpl;
		
		public String getWorkspaceHtml(String workspaceID,int userGroupCode,int userCode,short reportType,int mode){
			
			//Set the Report Display mode.
			//reportType=2; // hard code for lambda eTMF requirement
			this.mode=0;  // hard code for lambda eTMF requirement
			
			/*Getting data for all nodes required for the tree display*/
			dtows = docMgmtImpl.getWorkSpaceDetail(workspaceID);
			allNodesLastHistory = docMgmtImpl.getAllNodesLastHistory(dtows.getWorkSpaceId(),null);//Get all nodes last history
			
			maxStage = getMaxStageId();
			//nodeId = -1 means get all nodes Attrs
			
			
			allNodesType0002Attrs = new ArrayList<DTOWorkSpaceNodeAttribute>(docMgmtImpl.getNodeAttributes(dtows.getWorkSpaceId(), -1));
			
			//Get Project Level Attributes
			projectLvlAttrs = docMgmtImpl.getAttributeDetailForDisplay(dtows.getWorkSpaceId(), 1);
			
			StringBuffer htmlDataStringBuffer=new StringBuffer();
			
			//Get all nodes from database as user rights wise.
			Vector<DTOUserMst> adminUserDetail = docMgmtImpl.getWorkspaceUserDetailCSV(workspaceID); // Harcoded set to get all node detail for CSV Project
			
			int userGroupCodeCsv=adminUserDetail.get(0).getUserGroupCode();
			int userCodeCsv=adminUserDetail.get(0).getUserCode();
			
			Vector<Object []> workspaceTreeVector = docMgmtImpl.getNodeAndRightDetail(workspaceID,userGroupCodeCsv,userCodeCsv);
			
			if(workspaceTreeVector.size() > 0){
				htmlDataStringBuffer = new StringBuffer();
				TreeMap<Integer, ArrayList<Integer>> childNodeHS = new TreeMap<Integer, ArrayList<Integer>>();
				Hashtable<Integer, Object[]> nodeDtlHS = new Hashtable<Integer, Object[]>();
				Integer firstNode = null;
				
				for(int i=0; i < workspaceTreeVector.size(); i++){
					Object[] nodeRec = (Object[])workspaceTreeVector.get(i);
					Integer parentNodeId = (Integer) nodeRec[2];
					Integer currentNodeId = (Integer) nodeRec[0];
					if(i==0){
						firstNode = currentNodeId; 
					}
					if(childNodeHS.containsKey(parentNodeId)){	
										
						ArrayList<Integer> childList = (ArrayList<Integer>) childNodeHS.get(parentNodeId); 
						childList.add(currentNodeId);
						if(!childNodeHS.containsKey(currentNodeId)){
							childNodeHS.put(currentNodeId,new ArrayList<Integer>());
						}
					}
					else{
						ArrayList<Integer> childList = new ArrayList<Integer>();
						childList.add(currentNodeId);
						childNodeHS.put(parentNodeId,childList);
						if(!childNodeHS.containsKey(currentNodeId)){
							childNodeHS.put(currentNodeId,new ArrayList<Integer>());
						}
					}
					
					nodeDtlHS.put(currentNodeId,new Object[]{nodeRec[1],nodeRec[2],nodeRec[3],nodeRec[4],nodeRec[5],nodeRec[6],nodeRec[7],nodeRec[8],nodeRec[9],nodeRec[10],nodeRec[11]});
				}
		
				/*if(reportType == TREE_HTML){
					if(firstNode != null){
						Object[] firstNodeObj = (Object[]) nodeDtlHS.get(firstNode);
						
						//-SET PROJECT LEVEL ATTRIBUTES-----------------------------------------------------------------------------------------------------------------------
						StringBuffer strProjectLvlAttr = createAttrTable(1,projectLvlAttrs,false);
						//------------------------------------------------------------------------------------------------------------------------				
						htmlDataStringBuffer.append("<DIV align=\"left\" style=\"padding-left: 5px;font: verdana;\">");
						 	htmlDataStringBuffer.append("<DIV style=\"color: black;font-weight: bold;font-size: medium;\" align=\"left\" >");
						 	htmlDataStringBuffer.append(((String)firstNodeObj[0]).replaceAll(" ", "&nbsp;"));
						 	if(strProjectLvlAttr.length() > 0){//if node has attrs then only display the image
						 		htmlDataStringBuffer.append(" <img class=\"AttrToggleImg\" id=\"attrTable_img_"+1+"\" src=\"images/jquery_tree_img/minus.gif\" onclick=\"showAttributes('attrTable_"+1+"',this);\" alt=\"\" title=\"Click To Show/Hide Attributes\">");
						 	}
						 	htmlDataStringBuffer.append("</DIV>");
						 	if(strProjectLvlAttr.length() > 0){//if node has attrs then only create the div 
						 		htmlDataStringBuffer.append("<DIV>"+strProjectLvlAttr+"</DIV>");
						 	}
						 	htmlDataStringBuffer.append("    <UL class=\"branch\" id='branch" + firstNode.intValue() + "' style=\"padding-left: 30px;\">");
			            
						 	ArrayList<Integer> childList = childNodeHS.get(firstNode);
						 	for(int i=0;i<childList.size();i++){
						 		
						 		StringBuffer sb = getChildStructure((Integer)childList.get(i),childNodeHS,nodeDtlHS); 
						 		htmlDataStringBuffer.append(sb);
						 	}
						 	htmlDataStringBuffer.append("    </UL>");		
						 	htmlDataStringBuffer.append("</DIV>");
					}
				}
				
				*/
				 if(reportType == TABLE_HTML){
					
					Object[] firstNodeObj = (Object[]) nodeDtlHS.get(firstNode);
					htmlDataStringBuffer.append("<TABLE  width='100%' cellspacing=\"0\">");
					htmlDataStringBuffer.append("<tr class=\"none\"><Td class=\"title\"style=\"border:1px;\"> Project Name : " +dtows.getWorkSpaceDesc()+"</Td></tr></table><br/>");
	/*				htmlDataStringBuffer.append("<tr class=\"none\"><Td class=\"title\" width=\"40%\" align='right'  ><font size=\"4\"> Project Name : </Td>");
					htmlDataStringBuffer.append("<Td align='left' style=\"padding-left:8px;\" colspan=\"5\"><font size=\"4\">"+dtows.getWorkSpaceDesc()+"</font></Td></tr></table>");*/
					//htmlDataStringBuffer.append("<div style\"width:100%;border:1px solid;\"><TABLE class=\"datatable\" border=\"1px\" width='100%' cellspacing=\"0\" style=\"border:0px solid black\">");
					htmlDataStringBuffer.append("<div style\"width:100%;border:1px solid;\"><TABLE class=\"report\" border=\"1px\" width='100%' cellspacing=\"0\" style=\"border:0px solid black\">");
					
					/*htmlDataStringBuffer.append("<TR align=\"left\" class=\"title\"><TH width=\"40%\">Node Name</TH>" +
							"<TH width=\"8%\">Uploading Version</TH><TH width=\"7%\">Status</TH><TH width=\"13%\">Modify On</TH><TH width=\"13%\">Modify By</TH>");
					htmlDataStringBuffer.append("<TH width=\"8%\">Current Sequence Number</TH></TR>");*/
					
					/*if(countryCode.equalsIgnoreCase("IND")){*/
						htmlDataStringBuffer.append("<TR align=\"left\" class=\"title\"><TH width=\"13.5%\">Node/Document Name</TH>" +
								"<TH width=\"8.5%\">Assigned User</TH><TH width=\"8.3%\">Assigned Stage</TH>");
						htmlDataStringBuffer.append("</TR>");
					//}
					/*else{
						htmlDataStringBuffer.append("<TR align=\"left\" class=\"title\"><TH width=\"25%\">Node/Document Name</TH>" +
								"<TH width=\"5.8%\">Assigned User</TH><TH width=\"3.8%\">Assigned Stage</TH><TH width=\"3.8%\">Sent For</TH>"
								+ "<TH width=\"4.2%\">Status</TH><TH width=\"3.8%\">User Profile</TH><TH width=\"6%\">Modified On</TH>"
								+ "<TH width=\"6%\">Eastern Standard Time</TH><TH width=\"5%\">Voided By</TH><TH width=\"6%\">Voided On</TH>"
								+ "<TH width=\"6%\">Eastern Standard Time</TH>");
						htmlDataStringBuffer.append("</TR>");
					}*/
					
					/*htmlDataStringBuffer.append("<TR class=\"none\">");
						htmlDataStringBuffer.append("<TD align='left'width=\"30%\" class=\"title\"><B>"+(String) firstNodeObj[0] +"</B></TD>");	
					htmlDataStringBuffer.append("</td><td colspan=\"4\" valign=\"middle\" style=\"padding-top:2px;padding-bottom:2px;\">");
					*/
					//ArrayList<DTOWorkSpaceNodeAttrDetail> projectLvlAttrList = filterListForLeafAttrs(projectLvlAttrs);
					//htmlDataStringBuffer.append(createNodeAttrTRsForTable(projectLvlAttrList));
					//htmlDataStringBuffer.append("</td></TR>");
					ArrayList<Integer> childList = childNodeHS.get(firstNode);
				 	for(int i=0;i<childList.size();i++){
				 		StringBuffer sb = getTableChildStructure((Integer)childList.get(i),childNodeHS,nodeDtlHS); 
				 		htmlDataStringBuffer.append(sb);
				 	}
					
					htmlDataStringBuffer.append("</TABLE></div>");
					
				}
			}
			return htmlDataStringBuffer.toString();
		}
		private StringBuffer getChildStructure(Integer nodeId, TreeMap<Integer, ArrayList<Integer>> childNodeHS, Hashtable<Integer, Object[]> nodeDtlHS) {
			StringBuffer htmlStringBuffer = new StringBuffer(); 
			ArrayList<Integer> childList = childNodeHS.get(nodeId);
			Object[] nodeDtlObj = (Object[])nodeDtlHS.get(nodeId);
			String displayName = (String) nodeDtlObj[0];
			String NodeName = (String)nodeDtlObj[10];
			//Integer nodeNo = (Integer)nodeDtlObj[8];
			String FolderName = (String)nodeDtlObj[2];
			
			if(childList.size() > 0){//Parent Node
				
				StringBuffer strAttr=new StringBuffer();
				ArrayList<DTOWorkSpaceNodeAttribute> lstTemp = new ArrayList<DTOWorkSpaceNodeAttribute>();
				
				strAttr.append("[ ");
				for (Iterator<DTOWorkSpaceNodeAttribute> iterator = allNodesType0002Attrs.iterator(); iterator.hasNext();) {
					DTOWorkSpaceNodeAttribute dtoType0002Attr = iterator.next();
					
					if(dtoType0002Attr.getNodeId()==nodeId.intValue()){
						
						if(dtoType0002Attr.getAttrValue() != null && !dtoType0002Attr.getAttrValue().equals("")){
							strAttr.append(dtoType0002Attr.getAttrName()+ ":" +" <SPAN style=\"color: green;\">"+ dtoType0002Attr.getAttrValue()+"</SPAN>");		
						}
						else{
							strAttr.append(dtoType0002Attr.getAttrName()+ ":" + " <SPAN style=\"color: red;\">"+"No Value"+"</SPAN>");		
						}
						strAttr.append(", ");
						lstTemp.add(dtoType0002Attr);
						
					}
				}
				allNodesType0002Attrs.removeAll(lstTemp);
				if(strAttr.length()>1 && strAttr.charAt(strAttr.length()-2) == ',')
					strAttr.deleteCharAt(strAttr.length()-2);
				
				strAttr.append("]");
				
				
				htmlStringBuffer.append("<LI style=\"list-style: square; color: black;padding-top: 5px;\" >\n");
		         htmlStringBuffer.append("<SPAN title=\""+displayName+"\" style=\"font-weight: bold;\">" + NodeName );
		         
		         if(!strAttr.toString().equals("[ ]")){
		        	 htmlStringBuffer.append("<SPAN  style=\"color: black;\">&nbsp;"+strAttr+"</SPAN>");
		         }
		         htmlStringBuffer.append("</SPAN>");
		         htmlStringBuffer.append("<DIV>\n");
		         htmlStringBuffer.append("<UL style=\"padding-left: 30px;\">\n");
		         for(int i=0;i<childList.size();i++){

					htmlStringBuffer.append(getChildStructure((Integer)childList.get(i),childNodeHS,nodeDtlHS));
				}
				htmlStringBuffer.append("</UL></DIV></LI>\n");
			}
			else{//Leaf Node
				int effectiveTranNo = 0;
				
				//Getting the Node History Details into lastHistory;
				DTOWorkSpaceNodeHistory lastHistory = null;
				boolean hasHistory = false;
				for (Iterator<DTOWorkSpaceNodeHistory> iterator = allNodesLastHistory.iterator(); iterator.hasNext();) {
					DTOWorkSpaceNodeHistory nodeHis = iterator.next();
					
					if(nodeHis.getNodeId() == nodeId){
						lastHistory = nodeHis;
						allNodesLastHistory.remove(nodeHis);
						break;
					}
				}
				
				/*Adding HTML For the leaf node.*/
				htmlStringBuffer.append("<LI style=\"list-style: square;padding-top: 2px;\">");
					if(lastHistory != null && lastHistory.getFileName() != null && !lastHistory.getFileName().equals("No File")){
						boolean foundDocInMaxStage = false;
						boolean isFileUploaded = true;
						
						//only approved (StageId = 100) nodes will be displayed in Green color. 
						String statusColor = "",userName="";
						//if mode = 1 then show only max stage docs
						if(mode == 1)
						{	
							ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistory = docMgmtImpl.getNodeHistoryStageWiseUserWise(lastHistory.getWorkSpaceId(),lastHistory.getNodeId(),maxStage,0,true);
						
								if (wsNodeHistory != null && wsNodeHistory.size() > 0) 
								{	
									statusColor = "green";
									if(lastHistory.getTranNo() > wsNodeHistory.get(0).getTranNo())
									{
										htmlStringBuffer.append("*&nbsp;");
									}
									lastHistory = wsNodeHistory.get(0);
									foundDocInMaxStage = true;
								}
								else
								{
									isFileUploaded = false;
								}
								
						}
						else if(mode == 0)//if mode=0 then old code
						{
							foundDocInMaxStage = true;
							if(lastHistory.getStageId()==maxStage)
									statusColor = "green";
							else
								statusColor = "red";
						}
						else if(mode == 2){
							ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistory = docMgmtImpl.getNodeHistoryStageWiseUserWise(lastHistory.getWorkSpaceId(),lastHistory.getNodeId(),maxStage,0,false);
							if (wsNodeHistory != null && wsNodeHistory.size() > 0) 
							{	
								int effectiveVersionHistoryIndex = -1,lastHistoryTranNo = 0;
								effectiveVersionHistoryIndex = getEffectiveVersionHistoryIndex(wsNodeHistory);
								if(effectiveVersionHistoryIndex != -1)
								{
									lastHistoryTranNo = lastHistory.getTranNo();
									lastHistory = wsNodeHistory.get(effectiveVersionHistoryIndex);
									effectiveTranNo = lastHistory.getTranNo();
								}
								else{
									isFileUploaded = false;
								}
								if(lastHistoryTranNo > effectiveTranNo)
								{
									if(isFileUploaded)
										htmlStringBuffer.append("*&nbsp;");
								}
								foundDocInMaxStage = false;
							}
							else
							{
								isFileUploaded = false;
							}
						}
						
						//Get User Name
						userName = allUsers.get(lastHistory.getModifyBy()).getUserName();
						if (isFileUploaded) 
						{
							if(userTypeName.equalsIgnoreCase("Monitor")){
								htmlStringBuffer.append("<IMG src=\"images/jquery_tree_img/file.gif\" " +
										" style=\"cursor: pointer;\" title=\"Click To Open/Save Document - "+lastHistory.getFileName().replaceAll(" ","&nbsp;")+"\"" +
										" onclick=\"return fileOpen('openfile.do?fileWithPath="+dtows.getBaseWorkFolder()+lastHistory.getFolderName()+"/"+lastHistory.getFileName()+"');\">");
								}
								else{
							htmlStringBuffer.append("<IMG src=\"images/jquery_tree_img/file.gif\" " +
									" style=\"cursor: pointer;\" title=\"Click To Open/Save Document - "+lastHistory.getFileName().replaceAll(" ","&nbsp;")+"\"" +
									" onclick=\"window.open('openfile.do?fileWithPath="+dtows.getBaseWorkFolder()+lastHistory.getFolderName()+"/"+lastHistory.getFileName()+"');\">");
									}
						}
						else
						{
							htmlStringBuffer.append("*&nbsp;");
							htmlStringBuffer.append("<IMG src=\"images/empty2.gif\">");
							//htmlStringBuffer.append("<SPAN>"+FolderName+" </SPAN>");
						}
							
						
						htmlStringBuffer.append(FolderName);
						if(foundDocInMaxStage)
						{
							htmlStringBuffer.append("<SPAN>");
							htmlStringBuffer.append("&nbsp; ["+"<span style=\"color: "+statusColor+";\"> "+lastHistory.getStageDesc()+" </span>"+" BY "+userName+", Version : "+(lastHistory.getUserDefineVersionId()!=null?lastHistory.getUserDefineVersionId(): " - ") +" ]");
							htmlStringBuffer.append("</SPAN>");
						}
						
						hasHistory = true;
					}
					else{
						htmlStringBuffer.append("<IMG src=\"images/empty2.gif\">");
						htmlStringBuffer.append("<SPAN>"+FolderName+" </SPAN>");
					}
					
					//-Set Node Attributes HTML--------------------------------------------------------------------------------------
					Vector<DTOWorkSpaceNodeAttrDetail> leafNodeAttrs = null;
					if(mode == 2){//For mode 2 get node attrs from history for the effectiveTranNo.
						if(effectiveTranNo != 0){
							leafNodeAttrs = getNodeAttrsFromHistoryByTranNo(lastHistory.getWorkSpaceId(), lastHistory.getNodeId(), effectiveTranNo);
						}
					}
					else if(mode == 1){//For mode 1 get node attrs from history for the  Last TranNo.
						if(lastHistory != null){
							leafNodeAttrs = getNodeAttrsFromHistoryByTranNo(lastHistory.getWorkSpaceId(), lastHistory.getNodeId(), lastHistory.getTranNo());
						}
					}
					else{//For Mode=0
						if(lastHistory != null){
							leafNodeAttrs = getNodeAttrsFromHistoryByTranNo(lastHistory.getWorkSpaceId(), lastHistory.getNodeId(), lastHistory.getTranNo());
						}else{
							leafNodeAttrs = docMgmtImpl.getNodeAttrDetail(dtows.getWorkSpaceId(), nodeId);
						}
					}
					StringBuffer strNodeAttr = createAttrTable(nodeId,leafNodeAttrs,hasHistory);
					if(strNodeAttr.length() > 0){//if node has attrs then only display the image and create the div
						htmlStringBuffer.append("<img class=\"AttrToggleImg\" id=\"attrTable_img_"+nodeId+"\" src=\"images/jquery_tree_img/minus.gif\" onclick=\"showAttributes('attrTable_"+nodeId+"',this);\" alt=\"\" title=\"Click To Show/Hide Attributes\">");
						htmlStringBuffer.append(strNodeAttr);
					}
					//---------------------------------------------------------------------------------------------------------------
					
				htmlStringBuffer.append("</LI>");
					
			}
			
			return htmlStringBuffer;
		}
		/**
		 * Creates Table Element For Node Attribute Display
		 */
		private StringBuffer createAttrTable(int nodeId,Vector<?> nodeAttrList,boolean hasHistory) {
			
			StringBuffer nodeAttrTableHtml = new StringBuffer();
					
			ArrayList<DTOWorkSpaceNodeHistory> nodeAttrListFiltered = filterListForLeafAttrs(nodeAttrList);
			if(nodeAttrListFiltered == null || nodeAttrListFiltered.size() == 0){
				return nodeAttrTableHtml;
			}
			nodeAttrTableHtml.append("<TABLE class=\"datatable attrTable\" cellspacing=0 width=\"70%\" ID=\"attrTable_"+nodeId+"\">");
			for (int iteratorNodeAttr = 0; iteratorNodeAttr < nodeAttrListFiltered.size(); iteratorNodeAttr++) {
				DTOWorkSpaceNodeHistory dtoNodeAttr = nodeAttrListFiltered.get(iteratorNodeAttr);
				String attrValue="",attrName="";
				Timestamp ts = null;int userCode = 0;
				//int attrId = dtoNodeAttr.getAttrId();
				attrValue = dtoNodeAttr.getFileVersionId();
				attrName = dtoNodeAttr.getStageDesc();
				ts = dtoNodeAttr.getModifyOn();
				userCode = dtoNodeAttr.getModifyBy();
				
				nodeAttrTableHtml.append("<TR class=\""+((iteratorNodeAttr % 2 == 0)?"odd":"even")+"\">");
				
					//Attribute Name
					nodeAttrTableHtml.append("<TD style=\"padding:2px ;padding-left: 5px;padding-right: 5px;\">");
						nodeAttrTableHtml.append(attrName);		
					nodeAttrTableHtml.append("</TD>");
					//Attribute Value
					nodeAttrTableHtml.append("<TD style=\"padding:2px ;padding-left: 5px;padding-right: 5px;\">");
						if(attrValue != null && !attrValue.equals("")){
							//Replace special characters by their UNICODE value for HTML display
							attrValue = attrValue.replaceAll("\r\n","<br>").replaceAll("\t","&#9;").replaceAll("'","&#39;");
							nodeAttrTableHtml.append("<SPAN style=\"color: green;\">"+ attrValue+"</SPAN>");		
						}
						else{
							nodeAttrTableHtml.append("<SPAN style=\"color: red;\">"+"No Value"+"</SPAN>");		
						}
					nodeAttrTableHtml.append("</TD>");
		
					//If user name will be super user then do not display date and user name 
					//i.e.condition --> userCode != 1 for super user.
					
					if(hasHistory){
						//Attribute ModifyOn
						nodeAttrTableHtml.append("<TD style=\"padding:2px ;padding-left: 5px;padding-right: 5px;\">");
							nodeAttrTableHtml.append(((ts !=null && userCode != 1 )?dateFormat.format(ts):"-"));		
						nodeAttrTableHtml.append("</TD>");
						
						//Attribute ModifyBy
						nodeAttrTableHtml.append("<TD style=\"padding:2px ;padding-left: 5px;padding-right: 5px;\">");
							nodeAttrTableHtml.append((userCode != 0 && userCode != 1)?allUsers.get(userCode).getUserName():"-");		
						nodeAttrTableHtml.append("</TD>");
						
						nodeAttrTableHtml.append("<TD style=\"padding:2px ;padding-left: 5px;padding-right: 5px;\">");
							nodeAttrTableHtml.append("<img src=\"images/Common/history.png\" title=\"Show History\"  onclick=\"showHistory('"+dtows.getWorkSpaceId()+"',"+nodeId+","+0+",this)\">");
						nodeAttrTableHtml.append("</TD>");
					}else{//If there is no history for the node.
						//Attribute ModifyOn
						nodeAttrTableHtml.append("<TD style=\"padding:2px ;padding-left: 5px;padding-right: 5px;\">");
							nodeAttrTableHtml.append("-");		
						nodeAttrTableHtml.append("</TD>");
						
						//Attribute ModifyBy
						nodeAttrTableHtml.append("<TD style=\"padding:2px ;padding-left: 5px;padding-right: 5px;\">");
							nodeAttrTableHtml.append("-");		
						nodeAttrTableHtml.append("</TD>");
						
					}
				nodeAttrTableHtml.append("</TR>");
			}
			nodeAttrTableHtml.append("</TABLE>");
			return nodeAttrTableHtml;
		}
		/**
		 * 
		 * @param nodeAttrList
		 * @return Returns Filtered Attribute List which has no Attributes with AttrForIndi = '0001' (eCTD Leaf Attributes) 
		 */
		private ArrayList<DTOWorkSpaceNodeHistory> filterListForLeafAttrs(Vector<?> nodeAttrList){
			ArrayList<DTOWorkSpaceNodeHistory> nodeAttrListFiltered = new ArrayList<DTOWorkSpaceNodeHistory>();
			if(nodeAttrList == null || nodeAttrList.size() == 0){
				return nodeAttrListFiltered;
			}
			for (Object dtoObject : nodeAttrList) {
			
				if(dtoObject instanceof DTOWorkSpaceNodeHistory){
					DTOWorkSpaceNodeHistory dtoNodeAttr = (DTOWorkSpaceNodeHistory)dtoObject;
					DTOWorkSpaceNodeHistory tempDtoNodeDtl = new DTOWorkSpaceNodeHistory();
					tempDtoNodeDtl.setCompletedStageId(dtoNodeAttr.getCompletedStageId());
					tempDtoNodeDtl.setUserTypeName(dtoNodeAttr.getUserTypeName());
					tempDtoNodeDtl.setStageDesc(dtoNodeAttr.getStageDesc());
					tempDtoNodeDtl.setUserName(dtoNodeAttr.getUserName());
					tempDtoNodeDtl.setModifyBy(dtoNodeAttr.getModifyBy());
					tempDtoNodeDtl.setModifyOn(dtoNodeAttr.getModifyOn());
					tempDtoNodeDtl.setFileType(dtoNodeAttr.getFileType());
					tempDtoNodeDtl.setVoidBy(dtoNodeAttr.getVoidBy());
					tempDtoNodeDtl.setVoidISTDateTime(dtoNodeAttr.getVoidISTDateTime());
					tempDtoNodeDtl.setVoidESTDateTime(dtoNodeAttr.getVoidESTDateTime());
					nodeAttrListFiltered.add(tempDtoNodeDtl);
				}
			}
			/*String prevAttrName="$$$";
			for (Object dtoObject : nodeAttrList) {
				String attrForIndiId = null;
				if(dtoObject instanceof DTOWorkSpaceNodeAttribute){
					DTOWorkSpaceNodeAttribute dtoNodeAttr = (DTOWorkSpaceNodeAttribute)dtoObject;
					attrForIndiId = dtoNodeAttr.getAttrForIndiId();
					if(attrForIndiId != null && !attrForIndiId.equals("") && (attrForIndiId.equals("0001") && dtoNodeAttr.getAttrName().equalsIgnoreCase("operation"))){
						DTOWorkSpaceNodeAttrDetail tempDtoNodeDtl = new DTOWorkSpaceNodeAttrDetail();
						tempDtoNodeDtl.setAttrId(dtoNodeAttr.getAttrId());
						tempDtoNodeDtl.setAttrName(dtoNodeAttr.getAttrName());
						tempDtoNodeDtl.setAttrValue(dtoNodeAttr.getAttrValue());
						tempDtoNodeDtl.setModifyBy(dtoNodeAttr.getModifyBy());
						tempDtoNodeDtl.setModifyOn(dtoNodeAttr.getModifyOn());
						
						if(!prevAttrName.equals(tempDtoNodeDtl.getAttrName())){
							nodeAttrListFiltered.add(tempDtoNodeDtl);
						}
						prevAttrName = dtoNodeAttr.getAttrName();
					}
					
				}
				if(dtoObject instanceof DTOWorkSpaceNodeAttrDetail){
					DTOWorkSpaceNodeAttrDetail dtoNodeAttr = (DTOWorkSpaceNodeAttrDetail)dtoObject;
					attrForIndiId = dtoNodeAttr.getAttrForIndi();
					if(attrForIndiId != null && !attrForIndiId.equals("") && !(attrForIndiId.equals("0001") && !dtoNodeAttr.getAttrName().equalsIgnoreCase("operation"))){
						
						if(!prevAttrName.equals(dtoNodeAttr.getAttrName())){
							nodeAttrListFiltered.add(dtoNodeAttr);
						}
						prevAttrName = dtoNodeAttr.getAttrName();
					}
					
				}
			}*/
			return nodeAttrListFiltered;
		}
		private StringBuffer getTableChildStructure(Integer nodeId, TreeMap<Integer, ArrayList<Integer>> childNodeHS, Hashtable<Integer, Object[]> nodeDtlHS){
			
			StringBuffer tableHtml = new StringBuffer();
			Object[] nodeDtl = nodeDtlHS.get(nodeId);
			//System.out.println(nodeDtl[0]);
			finalString = (String)nodeDtl[0];
			finalString+=" ["+(String)nodeDtl[2]+"]";
			/*if(finalString.contains("{")){
			int startingIndex = finalString.indexOf("{");
			int closingIndex = finalString.indexOf("}");
			result1 = finalString.substring(startingIndex , closingIndex+1);
			
			System.out.println(result1);
			finalString=finalString.replace(result1, "  ");
			System.out.println(finalString);
			}
			else{
				finalString=(String)nodeDtl[0];
			}*/
			ArrayList<Integer> childList = childNodeHS.get(nodeId);
			String nodeName = "";
			boolean isFileUploaded = false;
			int effectiveTranNo = 0;
			DTOWorkSpaceNodeHistory lastHistory = null;
			
			//This is for parent node
			if (childList.size()>0){
				//nodeName = (String) nodeDtl[0];
			}
			//This is for child node
			else{
				
				for (Iterator<DTOWorkSpaceNodeHistory> iterator = allNodesLastHistory.iterator(); iterator.hasNext();) {
					DTOWorkSpaceNodeHistory nodeHis = iterator.next();
					
					if(nodeHis.getNodeId() == nodeId){
						lastHistory = nodeHis;
						allNodesLastHistory.remove(nodeHis);
						break;
					}
				}
				
				if(lastHistory != null && lastHistory.getFileName() != null && !lastHistory.getFileName().equals("No File")){
					
					//if mode = 1 then show only max stage docs
					if(mode == 1)
					{	
						ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistory = docMgmtImpl.getNodeHistoryStageWiseUserWise(lastHistory.getWorkSpaceId(),lastHistory.getNodeId(),maxStage,0,true);
							if (wsNodeHistory != null && wsNodeHistory.size() > 0) 
							{	
								if(lastHistory.getTranNo() > wsNodeHistory.get(0).getTranNo())
									nodeName ="*&nbsp;";
								if(userTypeName.equalsIgnoreCase("Monitor")){
								nodeName+= "<a style=\"text-decoration: underline;color:blue;\"  onclick=\"return fileopen('openfile.do?fileWithPath="+dtows.getBaseWorkFolder()+lastHistory.getFolderName()+"/"+lastHistory.getFileName()+"');\">"+ finalString + "</a>";
								}
								else{
									nodeName+= "<a style=\"text-decoration: underline;color:blue;\" href = \"openfile.do?fileWithPath="+dtows.getBaseWorkFolder()+lastHistory.getFolderName()+"/"+lastHistory.getFileName()+"\" target=\"_blank\">"+ finalString + "</a>";
								}
								lastHistory = wsNodeHistory.get(0);
							}
							else
							{
								isFileUploaded = true;
								nodeName = finalString;
							}
					}
					else if(mode == 0){
						if(userTypeName.equalsIgnoreCase("Monitor")){
						nodeName+= "<a style=\"text-decoration: underline;color:blue;\" onclick=\"return fileopen('openfile.do?fileWithPath="+dtows.getBaseWorkFolder()+lastHistory.getFolderName()+"/"+lastHistory.getFileName()+"');\">"+ finalString + "</a>";
						}
						else{
							nodeName+= "<a style=\"text-decoration: underline;color:blue;\" href = \"openfile.do?fileWithPath="+dtows.getBaseWorkFolder()+lastHistory.getFolderName()+"/"+lastHistory.getFileName()+"\" target=\"_blank\">"+ finalString + "</a>";
						}
						
						//isFileUploaded = true;
					}
					else if(mode == 2){
						ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistory = docMgmtImpl.getNodeHistoryStageWiseUserWise(lastHistory.getWorkSpaceId(),lastHistory.getNodeId(),maxStage,0,false);
						if (wsNodeHistory != null && wsNodeHistory.size() > 0) 
						{	
							int effectiveVersionHistoryIndex = -1,lastHistoryTranNo = 0;
							effectiveVersionHistoryIndex = getEffectiveVersionHistoryIndex(wsNodeHistory);
							if(effectiveVersionHistoryIndex != -1)
							{
								lastHistoryTranNo = lastHistory.getTranNo();
								lastHistory = wsNodeHistory.get(effectiveVersionHistoryIndex);
								effectiveTranNo = lastHistory.getTranNo();
								if(userTypeName.equalsIgnoreCase("Monitor")){
									nodeName+= "<a style=\"text-decoration: underline;color:blue;\" onclick=\"return fileopen('openfile.do?fileWithPath="+dtows.getBaseWorkFolder()+lastHistory.getFolderName()+"/"+lastHistory.getFileName()+"');\">"+ finalString + "</a>";
									}
									else{
									nodeName+= "<a style=\"text-decoration: underline;color:blue;\" href = \"openfile.do?fileWithPath="+dtows.getBaseWorkFolder()+lastHistory.getFolderName()+"/"+lastHistory.getFileName()+"\" target=\"_blank\">"+ finalString + "</a>";
									}
							}
							else{
								isFileUploaded = true;
								nodeName = finalString;
							}
							if(lastHistoryTranNo > effectiveTranNo)
							{
								nodeName="*&nbsp;"+finalString;
							}
						}
						else
						{
							isFileUploaded = true;
							nodeName = finalString;
						}
					}
				}else{
					nodeName =finalString;
				}
			}
			
			//tableHtml.append("<TR class=\"none\">");
			if (isFileUploaded)
				nodeName ="*&nbsp;" + finalString;
			if(nodeName.length()!=0){
				tableHtml.append("<TR class=\"none\">");
			tableHtml.append("<TD align='left' width=\"25%\" class=\"title\" valign=\"middle\" >"+nodeName+"</TD>");
			//tableHtml.append("<TD  colspan=\"5\" style=\"padding-top:2px;padding-bottom:2px;\" valign=\"middle\">");
			if(!countryCode.equalsIgnoreCase("IND")){
			   tableHtml.append("<TD  colspan=\"10\" style=\"padding-top:2px;padding-bottom:2px;\" valign=\"middle\">");
			}
			else{
				tableHtml.append("<TD  colspan=\"8\" style=\"padding-top:2px;padding-bottom:2px;\" valign=\"middle\">");
			}
			}
			
			//Add TR for node attributes
			Vector<DTOWorkSpaceNodeAttrDetail> leafNodeAttrs = null;
			Vector<DTOWorkSpaceNodeHistory> leafNodeAttrss = null;
			if(mode == 2){//For mode 2 get node attrs from history for the effectiveTranNo.
				if(effectiveTranNo != 0){
					leafNodeAttrs = getNodeAttrsFromHistoryByTranNo(dtows.getWorkSpaceId(), nodeId, effectiveTranNo);
				}
			}
			else if(mode == 1){//For mode 1 get node attrs from history for the  Last TranNo.
				if(lastHistory != null){
					leafNodeAttrs = getNodeAttrsFromHistoryByTranNo(lastHistory.getWorkSpaceId(), lastHistory.getNodeId(), lastHistory.getTranNo());
				}
			}
			else{//Mode=0
				//leafNodeAttrs = docMgmtImpl.getNodeAttrDetail(dtows.getWorkSpaceId(), nodeId);
				if(nodeName.length()!=0){
					leafNodeAttrss = docMgmtImpl.getProjectTrackingHistory(dtows.getWorkSpaceId(), nodeId);
				String userTypeName="";
				int stageCompleted=0;
				String baseWorkFolder="";
				String folderName="";
				String tempFileVersionId = "", tempUserDefineVer = "";//tempRemark="";
				char tempLastClosed = 'N';
				String voidBy="",voidOn="";

				Collections.reverse(leafNodeAttrss);
				for (Iterator<DTOWorkSpaceNodeHistory> iterator = leafNodeAttrss
						.iterator(); iterator.hasNext();) {
					DTOWorkSpaceNodeHistory dtoHistory = iterator.next();

					userTypeName=dtoHistory.getUserTypeName();
					//System.out.println(userTypeName);
					stageCompleted=dtoHistory.getCompletedStageId();
					voidBy = dtoHistory.getVoidBy();
					voidOn = dtoHistory.getVoidISTDateTime();
					/*if (dtoHistory.getFileVersionId().equals("")) {
						fileName=dtoHistory.getFileName();
						baseWorkFolder=dtoHistory.getBaseWorkFolder();
						folderName=dtoHistory.getFolderName();
						dtoHistory.setFileVersionId(tempFileVersionId);
						dtoHistory.setUserDefineVersionId(tempUserDefineVer);
						dtoHistory.setLastClosed(tempLastClosed);
						//dtoHistory.setRemark(tempRemark);
					} else {
						fileName=dtoHistory.getFileName();
						baseWorkFolder=dtoHistory.getBaseWorkFolder();
						folderName=dtoHistory.getFolderName();
						tempFileVersionId = dtoHistory.getFileVersionId();
						tempUserDefineVer = dtoHistory.getUserDefineVersionId();
						tempLastClosed = dtoHistory.getLastClosed();
						//tempRemark = dtoHistory.getRemark();	
					}*/

					// Fetch node's Attribute History (TranNo Wise)
					/*Vector attrHistory = docMgmtImpl
							.getWorkspaceNodeAttrHistorybyTranNo(dtoHistory
									.getWorkSpaceId(), dtoHistory.getNodeId(),
									dtoHistory.getTranNo());
					dtoHistory.setAttrHistory(attrHistory);*/
				}
				Collections.reverse(leafNodeAttrss);
				/*********************************************************************************/

				//fullNodeHistory = tempHistory;
			}
			}
			
			ArrayList<DTOWorkSpaceNodeHistory> filteredAttrList = filterListForLeafAttrs(leafNodeAttrss);
			StringBuffer nodeAttrTable=createNodeAttrTRsForTable(filteredAttrList);
			if (nodeAttrTable.length() == 0){
				if(nodeName.length()!=0){
				tableHtml.append("<center>No details available.</center>");
				}
			}else{
				tableHtml.append(nodeAttrTable);
			}
			if(nodeName.length()!=0)
			{
			tableHtml.append("</td></TR>");
			}		
		 	for(int i=0;i<childList.size();i++){
		 		StringBuffer sb = getTableChildStructure((Integer)childList.get(i),childNodeHS,nodeDtlHS); 
		 		tableHtml.append(sb);
		 	}
			return tableHtml;
		}
		
		private StringBuffer createNodeAttrTRsForTable(ArrayList<DTOWorkSpaceNodeHistory> attrList) {
			StringBuffer tableHtml=new StringBuffer();
			String ISTDateTime="";
			String ESTDateTime="";
			
			if(attrList.size()>0){
					tableHtml.append("<table class=\"datatable\" border=\"1px\"  width=\"100%\" style=\"border:0px solid black\" cellspacing=\"0\">");
					for (Iterator<DTOWorkSpaceNodeHistory> iterator = attrList.iterator(); iterator.hasNext();) {
						DTOWorkSpaceNodeHistory dtoWorkSpaceNodeAttrDetail = iterator.next();
					
						tableHtml.append("<tr class=\""+((isOddRow)?"odd":"even")+"\">");		
								
						int completedStageId = dtoWorkSpaceNodeAttrDetail.getCompletedStageId();
						String Status="";
						String nextStage ="-";
						String flag= dtoWorkSpaceNodeAttrDetail.getFileType();
						
						
						if(completedStageId==10 && flag.equalsIgnoreCase("SR")){
							nextStage = "Review";
						}else if(completedStageId==20){
							nextStage = "Approve";
						}else if(completedStageId==100){
							nextStage = "-";
						}else{
							nextStage = "-";
						}
						
						if(completedStageId == 0){
							Status ="-";
							ISTDateTime="-";
							ESTDateTime="-";
						}
						else if(completedStageId==10 && flag.equals("")){
							Status ="-";
							ISTDateTime="-";
							ESTDateTime="-";
						}
						else{
							Status="Sign Off Completed";
							
							if(countryCode.equalsIgnoreCase("IND")){
								time = docMgmtImpl.TimeZoneConversion(dtoWorkSpaceNodeAttrDetail.getModifyOn(),locationName,countryCode);
								dtoWorkSpaceNodeAttrDetail.setISTDateTime(time.get(0));
								ISTDateTime = dtoWorkSpaceNodeAttrDetail.getISTDateTime();
							}
							else{
								time = docMgmtImpl.TimeZoneConversion(dtoWorkSpaceNodeAttrDetail.getModifyOn(),locationName,countryCode);
								dtoWorkSpaceNodeAttrDetail.setISTDateTime(time.get(0));
								ISTDateTime = dtoWorkSpaceNodeAttrDetail.getISTDateTime();
								dtoWorkSpaceNodeAttrDetail.setESTDateTime(time.get(1));
								ESTDateTime = dtoWorkSpaceNodeAttrDetail.getESTDateTime();
							}
						}				
						if(countryCode.equalsIgnoreCase("IND")){					
							tableHtml.append("<TD valign=\"middle\" width=\"11.4%\">"+((!dtoWorkSpaceNodeAttrDetail.getUserName().equals("") && dtoWorkSpaceNodeAttrDetail.getUserName()!=null)?dtoWorkSpaceNodeAttrDetail.getUserName():"-")+"</TD>");
							tableHtml.append("<TD valign=\"middle\" width=\"11.7%\">"+ dtoWorkSpaceNodeAttrDetail.getStageDesc() +"</TD>");
							/*tableHtml.append("<TD valign=\"middle\" width=\"11.7%\">"+nextStage +"</TD>");
							tableHtml.append("<TD valign=\"middle\" width=\"11.1%\">"+Status+"</TD>");
							tableHtml.append("<TD valign=\"middle\" width=\"10.6%\">"+dtoWorkSpaceNodeAttrDetail.getUserTypeName() +"</TD>");
							tableHtml.append("<TD valign=\"middle\" width=\"15.6%\">"+ISTDateTime+"</TD>");	
							tableHtml.append("<TD valign=\"middle\" width=\"11.3%\">"+dtoWorkSpaceNodeAttrDetail.getVoidBy()+"</TD>");
							tableHtml.append("<TD valign=\"middle\" width=\"19%\">"+dtoWorkSpaceNodeAttrDetail.getVoidISTDateTime()+"</TD>");*/
							tableHtml.append("</tr>");
							}
							else{
								tableHtml.append("<TD valign=\"middle\" width=\"9.6%\">"+((!dtoWorkSpaceNodeAttrDetail.getUserName().equals("") && dtoWorkSpaceNodeAttrDetail.getUserName()!=null)?dtoWorkSpaceNodeAttrDetail.getUserName():"-")+"</TD>");						
								tableHtml.append("<TD valign=\"middle\" width=\"6.7%\">"+ dtoWorkSpaceNodeAttrDetail.getStageDesc() +"</TD>");
								tableHtml.append("<TD valign=\"middle\" width=\"6.5%\">"+ nextStage +"</TD>");
								tableHtml.append("<TD valign=\"middle\" width=\"7.3%\">"+Status+"</TD>");
								tableHtml.append("<TD valign=\"middle\" width=\"6.6%\">"+dtoWorkSpaceNodeAttrDetail.getUserTypeName() +"</TD>");
								tableHtml.append("<TD valign=\"middle\" width=\"10.4%\">"+ISTDateTime+"</TD>");
								tableHtml.append("<TD valign=\"middle\" width=\"10.4%\">"+ESTDateTime+"</TD>");	
								tableHtml.append("<TD valign=\"middle\" width=\"8.7%\">"+dtoWorkSpaceNodeAttrDetail.getVoidBy()+"</TD>");
								tableHtml.append("<TD valign=\"middle\" width=\"10.5%\">"+dtoWorkSpaceNodeAttrDetail.getVoidISTDateTime()+"</TD>");
								tableHtml.append("<TD valign=\"middle\" width=\"10%\">"+dtoWorkSpaceNodeAttrDetail.getVoidESTDateTime()+"</TD>");
								tableHtml.append("</tr>");
							}
					
					if(isOddRow)
						isOddRow=false;
					else
						isOddRow=true;
				}
				tableHtml.append("</table>");
			}
			return tableHtml;
		}
		
		public String getAttrHistoryTable(String workSpaceId,int nodeId,int attrId) {
			ArrayList<DTOWorkSpaceNodeHistory> nodeHistories = docMgmtImpl.showFullNodeHistory(workSpaceId, nodeId);
			StringBuffer attrHistoryTable = new StringBuffer();
			String prevAttrValue = "";
			attrHistoryTable.append("<TABLE class='datatable' >");
			attrHistoryTable.append("<tr class='title'><th width=50%>Attribute Value</th><th width=25%>Modify By</th><th width=25%>Modify On</th></tr>");
			for (DTOWorkSpaceNodeHistory dtoHistory : nodeHistories) {
				//System.out.println(dtoHistory.getAttrName());
				
				Vector<DTOWorkSpaceNodeAttrHistory> attrHistory = docMgmtImpl.getWorkspaceNodeAttrHistorybyTranNo(dtoHistory.getWorkSpaceId(), dtoHistory.getNodeId(), dtoHistory.getTranNo());
				dtoHistory.setAttrHistory(attrHistory);
				for (int i = 0; i < attrHistory.size(); i++) {
					DTOWorkSpaceNodeAttrHistory dtoAttrHistory = attrHistory.get(i);
					if (dtoAttrHistory.getAttrId() == attrId && !prevAttrValue.equalsIgnoreCase(dtoAttrHistory.getAttrValue())) {
						attrHistoryTable.append("<tr class='even'>");
							attrHistoryTable.append("<td>");
								attrHistoryTable.append((dtoAttrHistory.getAttrValue() != null && !dtoAttrHistory.getAttrValue().equals("")?dtoAttrHistory.getAttrValue():"No Value"));
							attrHistoryTable.append("</td>");
							attrHistoryTable.append("<td>");
								attrHistoryTable.append(((dtoAttrHistory.getModifyBy() != 0 && dtoAttrHistory.getModifyBy() != 1)?allUsers.get(dtoAttrHistory.getModifyBy()).getUserName():"-"));
							attrHistoryTable.append("</td>");
							attrHistoryTable.append("<td>");
								attrHistoryTable.append(new SimpleDateFormat("MMM-dd yyyy hh:mm a").format(dtoAttrHistory.getModifyOn()));
							attrHistoryTable.append("</td>");
						attrHistoryTable.append("</tr>");
						prevAttrValue= dtoAttrHistory.getAttrValue();
					}
				}
			}
			attrHistoryTable.append("</TABLE>");
			return attrHistoryTable.toString();
		}
		
		private int getMaxStageId() {
			Vector<DTOStageMst> stageMstList =  docMgmtImpl.getStageDetail();
			int maxStage = stageMstList.get(0).getStageId();
			for (int itrStageMst = 0; itrStageMst < stageMstList.size(); itrStageMst++) 
			{
				DTOStageMst dtoStageMst = stageMstList.get(itrStageMst);
				if(dtoStageMst.getStatusIndi() != 'D' && maxStage < dtoStageMst.getStageId())
				{
					maxStage = dtoStageMst.getStageId();
				}
			}
			return maxStage;
		}
		
		private int getEffectiveVersionHistoryIndex(ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistory) {
			int historyIndex = -1;
			outterLoop:
			for (int itrHistory = wsNodeHistory.size()-1; itrHistory >= 0 ; itrHistory--) {
				DTOWorkSpaceNodeHistory nodeHistory = wsNodeHistory.get(itrHistory);
				Vector<DTOWorkSpaceNodeAttrHistory> nodeAttrHistory = docMgmtImpl.getWorkspaceNodeAttrHistorybyTranNo(nodeHistory.getWorkSpaceId(), nodeHistory.getNodeId(), nodeHistory.getTranNo());
				
				for (DTOWorkSpaceNodeAttrHistory dtoWorkSpaceNodeAttrHistory : nodeAttrHistory) {
					if(dtoWorkSpaceNodeAttrHistory.getAttrName().equals("Effective Date")){//If node has a attribute named 'Effective Date'
						try {
							Date effectiveDate = attrDateFormat.parse(dtoWorkSpaceNodeAttrHistory.getAttrValue());
							if(effectiveDate.before(new Date(System.currentTimeMillis()))){
								historyIndex = itrHistory;
								break outterLoop;
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return historyIndex;
		}
		
		private Vector<DTOWorkSpaceNodeAttrDetail> getNodeAttrsFromHistoryByTranNo(String workspaceId,int nodeId,int effectiveTranNo) {
			Vector<DTOWorkSpaceNodeAttrHistory> nodeAttrHistory = docMgmtImpl.getWorkspaceNodeAttrHistorybyTranNo(workspaceId, nodeId, effectiveTranNo);
			
			Vector<DTOWorkSpaceNodeAttrDetail> leafNodeAttrs = new Vector<DTOWorkSpaceNodeAttrDetail>();
			for (DTOWorkSpaceNodeAttrHistory dtoWorkSpaceNodeAttrHistory : nodeAttrHistory) {
				DTOWorkSpaceNodeAttrDetail attrDetail = new DTOWorkSpaceNodeAttrDetail();
				attrDetail.setWorkspaceId(dtoWorkSpaceNodeAttrHistory.getWorkSpaceId());
				attrDetail.setAttrId(dtoWorkSpaceNodeAttrHistory.getAttrId());
				attrDetail.setAttrForIndi(dtoWorkSpaceNodeAttrHistory.getAttrForIndiId());
				attrDetail.setAttrName(dtoWorkSpaceNodeAttrHistory.getAttrName());
				attrDetail.setAttrValue(dtoWorkSpaceNodeAttrHistory.getAttrValue());
				attrDetail.setModifyBy(dtoWorkSpaceNodeAttrHistory.getModifyBy());
				attrDetail.setModifyOn(dtoWorkSpaceNodeAttrHistory.getModifyOn());
				leafNodeAttrs.add(attrDetail);
			}//Preparing leafNodeAttrs Vector in the for loop.
			return leafNodeAttrs;
		}
	} 