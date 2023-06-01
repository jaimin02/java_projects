package com.docmgmt.server.webinterface.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import com.docmgmt.dto.DTOForumDtl;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;

public class CommentSnapShotBean { 

	private DTOWorkSpaceMst dtows;
	private ArrayList<DTOWorkSpaceNodeHistory> allNodesLastHistory;
	public static final short TABLE_HTML = 2;
	
	public String str ;
	public String result1;
	public String finalString;
	private int mode;
	private boolean isOddRow = true;	
	DocMgmtImpl docMgmtImpl;
	public CommentSnapShotBean(){ 
		docMgmtImpl = new DocMgmtImpl();
	} 
	
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	
	
	public String getWorkspaceHtml(String workspaceID,int userGroupCode,int userCode,short reportType,int mode){
		
		this.mode=0;  // hard code for lambda eTMF requirement
		
		/*Getting data for all nodes required for the tree display*/
		dtows = docMgmtImpl.getWorkSpaceDetail(workspaceID);
		allNodesLastHistory = docMgmtImpl.getAllNodesLastHistory(dtows.getWorkSpaceId(),null);//Get all nodes last history
		
		StringBuffer htmlDataStringBuffer=new StringBuffer();
		
		//Get all nodes from database as user rights wise.
		Vector<Object []> workspaceTreeVector = docMgmtImpl.getNodeAndRightDetail(workspaceID,userGroupCode,userCode);
		
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
	
			 if(reportType == TABLE_HTML){
				
				/*htmlDataStringBuffer.append("<TABLE  width='100%' cellspacing=\"0\">");
				htmlDataStringBuffer.append("<tr class=\"none\"><Td class=\"title\" width=\"40%\" align='right'  ><font size=\"4\"> Project Name : </Td>");
				htmlDataStringBuffer.append("<Td align='left' style=\"padding-left:8px;\" colspan=\"6\"><font size=\"4\">"+dtows.getWorkSpaceDesc()+"</font></Td></tr></table>");
				htmlDataStringBuffer.append("<div style\"width:100%;border:1px solid;\"><TABLE class=\"datatable\" border=\"1px\" width='100%' cellspacing=\"0\" style=\"border:0px solid black\">");
				htmlDataStringBuffer.append("<TR align=\"left\" class=\"title\"><TH width=\"40%\">Node Name</TH>" +
						"<TH width=\"9%\">Sender Name</TH><TH width=\"9%\">Receiver Name</TH><TH width=\"12%\">Comment</TH><TH width=\"12%\">Modify On</TH>");
				htmlDataStringBuffer.append("<TH width=\"9%\">Status</TH><TH width=\"9%\">Resolver Name</TH></TR>");*/
				 
				 if(countryCode.equalsIgnoreCase("IND")){
					 htmlDataStringBuffer.append("<TABLE  width='100%' cellspacing=\"0\">");
					 htmlDataStringBuffer.append("<tr class=\"none\"><Td class=\"title\" width=\"40%\" align='right'  ><font size=\"4\"> Project Name : </Td>");
					 htmlDataStringBuffer.append("<Td align='left' style=\"padding-left:8px;\" colspan=\"6\"><font size=\"4\">"+dtows.getWorkSpaceDesc()+"</font></Td></tr></table>");
					 htmlDataStringBuffer.append("<div style\"width:100%;border:1px solid;\"><TABLE class=\"datatable\" border=\"1px\" width='100%' cellspacing=\"0\" style=\"border:0px solid black\">");
					 htmlDataStringBuffer.append("<TR align=\"left\" class=\"title\"><TH width=\"25%\">Node Name</TH><TH width=\"25%\">File Name</TH>" +
							 "<TH width=\"8%\">Sender Name</TH><TH width=\"9%\">Receiver Name</TH><TH width=\"7%\">Comment</TH><TH width=\"11%\">Modify On</TH><TH width=\"8.5%\">User Profile</TH>");
					 htmlDataStringBuffer.append("<TH width=\"8%\">Status</TH><TH width=\"10%\">Resolver Name</TH></TR>");
				 }
				 else{
						htmlDataStringBuffer.append("<TABLE  width='100%' cellspacing=\"0\">");
						htmlDataStringBuffer.append("<tr class=\"none\"><Td class=\"title\" width=\"40%\" align='right'  ><font size=\"4\"> Project Name : </Td>");
						htmlDataStringBuffer.append("<Td align='left' style=\"padding-left:8px;\" colspan=\"6\"><font size=\"4\">"+dtows.getWorkSpaceDesc()+"</font></Td></tr></table>");
						htmlDataStringBuffer.append("<div style\"width:100%;border:1px solid;\"><TABLE class=\"datatable\" border=\"1px\" width='100%' cellspacing=\"0\" style=\"border:0px solid black\">");
						htmlDataStringBuffer.append("<TR align=\"left\" class=\"title\"><TH width=\"25%\">Node Name</TH><TH width=\"25%\">File Name</TH>" +
								"<TH width=\"9%\">Sender Name</TH><TH width=\"8%\">Receiver Name</TH><TH width=\"7%\">Comment</TH><TH width=\"11%\">Modify On</TH><TH width=\"12%\">Eastern Standard Time</TH><TH width=\"13%\">User Profile</TH>");
						htmlDataStringBuffer.append("<TH width=\"10%\">Status</TH><TH width=\"9%\">Resolver Name</TH></TR>");
				 }
				 
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
	
	/**
	 * 
	 * @param nodeAttrList
	 * @return Returns Filtered Attribute List which has no Attributes with AttrForIndi = '0001' (eCTD Leaf Attributes) 
	 */
	private ArrayList<DTOForumDtl> filterListForLeafAttrs(Vector<?> nodeAttrList){
		ArrayList<DTOForumDtl> nodeAttrListFiltered = new ArrayList<DTOForumDtl>();
		if(nodeAttrList == null || nodeAttrList.size() == 0){
			return nodeAttrListFiltered;
		}
		for (Object dtoObject : nodeAttrList) {
		
			if(dtoObject instanceof DTOForumDtl){
				DTOForumDtl commentdata = (DTOForumDtl)dtoObject;
				DTOForumDtl commentdtl = new DTOForumDtl();
				commentdtl.setSenderName(commentdata.getSenderName());
				commentdtl.setUserTypeName(commentdata.getuserTypeName());
				commentdtl.setReceiverName(commentdata.getReceiverName());
				commentdtl.setSubjectDesc(commentdata.getSubjectDesc());
				commentdtl.setForumHdrModifyOn(commentdata.getForumHdrModifyOn());
				commentdtl.setTypeFlag(commentdata.getTypeFlag());
				commentdtl.setResolverName(commentdata.getResolverName());
				commentdtl.setUuid(commentdata.getUuid());
				commentdtl.setResolverFlag(commentdata.getResolverFlag());
				commentdtl.setSubjectId(commentdata.getSubjectId());
				nodeAttrListFiltered.add(commentdtl);
			}
		}
		
		return nodeAttrListFiltered;
	}
	private StringBuffer getTableChildStructure(Integer nodeId, TreeMap<Integer, ArrayList<Integer>> childNodeHS, Hashtable<Integer, Object[]> nodeDtlHS){
		
		StringBuffer tableHtml = new StringBuffer();
		Object[] nodeDtl = nodeDtlHS.get(nodeId);
		ArrayList<Integer> childList = childNodeHS.get(nodeId);
		String nodeName = "";
		String fileName="";
		boolean isFileUploaded = false;
		DTOWorkSpaceNodeHistory lastHistory = null;
		
		//This is for parent node
		if (childList.size()>0){
			finalString = (String)nodeDtl[0];
			if(finalString.contains("{")){
			int startingIndex = finalString.indexOf("{");
			int closingIndex = finalString.indexOf("}");
			result1 = finalString.substring(startingIndex , closingIndex+1);
			
			System.out.println(result1);
			finalString=finalString.replace(result1, "  ");
			System.out.println(finalString);
			}
			else{
				finalString=(String)nodeDtl[0];
				fileName="-";
			}
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
				if(mode == 0){
					//nodeName+= "<a style=\"text-decoration: underline;color:blue;\" href = \"openfile.do?fileWithPath="+dtows.getBaseWorkFolder()+lastHistory.getFolderName()+"/"+lastHistory.getFileName()+"\" target=\"_blank\">"+ finalString + "</a>";
					nodeName+=finalString;
					fileName =  "<a style=\"text-decoration: underline;color:blue;\" href = \"openfile.do?fileWithPath="+dtows.getBaseWorkFolder()+lastHistory.getFolderName()+"/"+lastHistory.getFileName()+"\" target=\"_blank\">"+ lastHistory.getFileName() + "</a>"; 
				}
			
			}else{
				nodeName =finalString;
				fileName ="-";
			}
		}
		
		if (isFileUploaded)
			nodeName ="*&nbsp;" + finalString;
		if(nodeName.length()!=0){
			tableHtml.append("<TR class=\"none\">");
		tableHtml.append("<TD align='left' width=\"25%\" class=\"title\" valign=\"middle\" >"+nodeName+"</TD>");
		tableHtml.append("<TD align='left' width=\"25%\" class=\"title\" valign=\"middle\" >"+fileName+"</TD>");
		//tableHtml.append("<TD  colspan=\"6\" style=\"padding-top:2px;padding-bottom:2px;\" valign=\"middle\">");
			if(countryCode.equalsIgnoreCase("IND")){
				tableHtml.append("<TD  colspan=\"7\" style=\"padding-top:2px;padding-bottom:2px;\" valign=\"middle\">");
			}
			else{
				tableHtml.append("<TD  colspan=\"8\" style=\"padding-top:2px;padding-bottom:2px;\" valign=\"middle\">");
			}
		}
		
		Vector<DTOForumDtl> leafNodeAttrss = null;
		
			if(nodeName.length()!=0){
				leafNodeAttrss = docMgmtImpl.showFullCommentHistoryForLambda(dtows.getWorkSpaceId(), nodeId);
				String userTypeName="";
			Collections.reverse(leafNodeAttrss);
		for (Iterator<DTOForumDtl> iterator = leafNodeAttrss
					.iterator(); iterator.hasNext();) {
				DTOForumDtl dtocommentHistory = iterator.next();
				System.out.println(dtocommentHistory.getuserTypeName());
			}
			Collections.reverse(leafNodeAttrss);
			/*********************************************************************************/
		}
		/*}*/
		
		ArrayList<DTOForumDtl> filteredAttrList = filterListForLeafAttrs(leafNodeAttrss);
		StringBuffer nodeAttrTable=createNodeAttrTRsForTable(filteredAttrList);
		if (nodeAttrTable.length() == 0){
			if(nodeName.length()!=0){
			tableHtml.append("<center>No Comment history found.</center>");
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
	
	private StringBuffer createNodeAttrTRsForTable(ArrayList<DTOForumDtl> attrList) {
		StringBuffer tableHtml=new StringBuffer();
		
		if(attrList.size()>0){
			DTOForumDtl it = (DTOForumDtl) attrList.get(0);
			
				tableHtml.append("<table border=\"1px\"  width=\"100%\" style=\"border:0px solid black\" cellspacing=\"0\">");
				for (Iterator<DTOForumDtl> iterator = attrList.iterator(); iterator.hasNext();) {
					DTOForumDtl dtocommentdata = iterator.next();
					if(dtocommentdata.getResolverFlag() !=null && dtocommentdata.getResolverFlag().toString().equals("Y")  && it.getSubjectId()!=null && dtocommentdata.getSubjectId()!=null && !it.getSubjectId().equals(dtocommentdata.getSubjectId()))
					{
						if(countryCode.equalsIgnoreCase("IND")){
							tableHtml.append("<tr bgcolor=\"#5B89AA\"><td colspan=\"7\"></td></tr>");
						}
						else{
							tableHtml.append("<tr bgcolor=\"#5B89AA\"><td colspan=\"8\"></td></tr>");
						}
					}
					tableHtml.append("<tr class=\""+((isOddRow)?"odd":"even")+"\">");
					//String modifydate = new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(dtocommentdata.getForumHdrModifyOn());
					 String ISTDateTime="";
					 String ESTDateTime="";
					if(countryCode.equalsIgnoreCase("IND")){
						time = docMgmtImpl.TimeZoneConversion(dtocommentdata.getForumHdrModifyOn(),locationName,countryCode);
						dtocommentdata.setISTDateTime(time.get(0));
						ISTDateTime=dtocommentdata.getISTDateTime();
					}
					else{
						time = docMgmtImpl.TimeZoneConversion(dtocommentdata.getForumHdrModifyOn(),locationName,countryCode);
						dtocommentdata.setISTDateTime(time.get(0));
						ISTDateTime=dtocommentdata.getISTDateTime();
						dtocommentdata.setESTDateTime(time.get(1));
						ESTDateTime=dtocommentdata.getESTDateTime();
					}
					//System.out.println(dtocommentdata.getResolverFlag());
					
					//tableHtml.append("<TD valign=\"middle\" width=\"20.5%\">"+dtocommentdata.getuserTypeName() +"</TD>");
					if(countryCode.equalsIgnoreCase("IND")){
						tableHtml.append("<TD valign=\"middle\" width=\"14.5%\">"+dtocommentdata.getSenderName() +"</TD>");
						tableHtml.append("<TD valign=\"middle\" width=\"16%\">"+dtocommentdata.getReceiverName() +"</TD>");
						tableHtml.append("<TD valign=\"middle\" width=\"14%\">"+dtocommentdata.getSubjectDesc() +"</TD>");
						tableHtml.append("<TD valign=\"middle\" width=\"20%\">"+ISTDateTime +"</TD>");
						tableHtml.append("<TD valign=\"middle\" width=\"13%\">"+dtocommentdata.getuserTypeName() +"</TD>");					
						tableHtml.append("<TD valign=\"middle\" width=\"11%\">"+((dtocommentdata.getTypeFlag() == null)?"Open":(dtocommentdata.getTypeFlag().equalsIgnoreCase("C"))?"Close":"Resolved")+"</TD>");					
						tableHtml.append("<TD valign=\"middle\" width=\"15%\">"+((dtocommentdata.getTypeFlag() != null)?dtocommentdata.getResolverName():"-")+"</TD>");
					}
					else{
						tableHtml.append("<TD valign=\"middle\" width=\"12%\">"+dtocommentdata.getSenderName() +"</TD>");
						tableHtml.append("<TD valign=\"middle\" width=\"13.5%\">"+dtocommentdata.getReceiverName() +"</TD>");
						tableHtml.append("<TD valign=\"middle\" width=\"14%\">"+dtocommentdata.getSubjectDesc() +"</TD>");
						tableHtml.append("<TD valign=\"middle\" width=\"15.5%\">"+ISTDateTime +"</TD>");
						tableHtml.append("<TD valign=\"middle\" width=\"16%\">"+ESTDateTime +"</TD>");
						tableHtml.append("<TD valign=\"middle\" width=\"9%\">"+dtocommentdata.getuserTypeName() +"</TD>");					
						tableHtml.append("<TD valign=\"middle\" width=\"9%\">"+((dtocommentdata.getTypeFlag() == null)?"Open":(dtocommentdata.getTypeFlag().equalsIgnoreCase("C"))?"Close":"Resolved")+"</TD>");					
						tableHtml.append("<TD valign=\"middle\" width=\"15%\">"+((dtocommentdata.getTypeFlag() != null)?dtocommentdata.getResolverName():"-")+"</TD>");
					}
						tableHtml.append("</tr>");
				
				if(isOddRow)
					isOddRow=false;
				else
					isOddRow=true;
			}
			tableHtml.append("</table>");
		}
		return tableHtml;
	}

}
