package com.docmgmt.server.webinterface.beans;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;

public class ManualPublishTreeBean {
	private int userCode;
	private Vector<Object[]> nodeInfoVector;
	private String userType;
	private String[] allRelatedSeqs;
	private String refIdSuffix;
	private String operationValueOptions;
	private String relativeSeqValueOptions;
	private DTOWorkSpaceMst dtows;
	private ArrayList<DTOWorkSpaceNodeHistory> allNodesLastHistory;
	private ArrayList<DTOWorkSpaceNodeAttribute> allNodesType0002Attrs;
	public String workspaceid="";
	public ManualPublishTreeBean() {
		nodeInfoVector = new Vector<Object[]>();
		docMgmt = new DocMgmtImpl();
		allRelatedSeqs = null;
		refIdSuffix = "";
	}
	DocMgmtImpl docMgmt;
	public String getWorkspaceTreeHtml(String workspaceID, int userGroupCode,
			int userCode) {

		workspaceid=workspaceID;
		/* Getting data for all nodes required for the tree display */
		dtows = docMgmt.getWorkSpaceDetail(workspaceID);
//		allNodesLastHistory = docMgmt.getAllNodesLastHistory(dtows
//				.getWorkSpaceId(), null);// Get all nodes last history
		
		allNodesLastHistory = docMgmt.getPublishableFilesAfterLastConfirmSeq(dtows.getWorkSpaceId()); 
				
		// nodeId = -1 means get all nodes Attrs
		allNodesType0002Attrs = new ArrayList<DTOWorkSpaceNodeAttribute>(
				docMgmt.getNodeAttributes(dtows.getWorkSpaceId(), -1));

		StringBuffer htmlSB = new StringBuffer();
		// System.out.println("Creating tree for workspace id  " + workspaceID);
		Vector<Object[]> workspaceTreeVector = getTreeNodes(workspaceID,
				userGroupCode, userCode);

		if (workspaceTreeVector.size() > 0) {
			htmlSB = new StringBuffer();
			TreeMap<Integer, ArrayList<Integer>> childNodeHS = new TreeMap<Integer, ArrayList<Integer>>();
			Hashtable<Integer, Object[]> nodeDtlHS = new Hashtable<Integer, Object[]>();
			Integer firstNode = null;

			for (int i = 0; i < workspaceTreeVector.size(); i++) {
				Object[] nodeRec = workspaceTreeVector.get(i);
				Integer parentNodeId = (Integer) nodeRec[2];
				Integer currentNodeId = (Integer) nodeRec[0];
				
				if (i == 0) {
					firstNode = currentNodeId;
				}
				if (childNodeHS.containsKey(parentNodeId)) {

					ArrayList<Integer> childList = (ArrayList<Integer>) childNodeHS
							.get(parentNodeId);
					childList.add(currentNodeId);
					if (!childNodeHS.containsKey(currentNodeId)) {
						childNodeHS
								.put(currentNodeId, new ArrayList<Integer>());
					}
				} else {
					ArrayList<Integer> childList = new ArrayList<Integer>();
					childList.add(currentNodeId);
					childNodeHS.put(parentNodeId, childList);
					if (!childNodeHS.containsKey(currentNodeId)) {
						childNodeHS
								.put(currentNodeId, new ArrayList<Integer>());
					}
				}

				/*nodeDtlHS.put(currentNodeId, new Object[] { nodeRec[1],
						nodeRec[2], nodeRec[3], nodeRec[4], nodeRec[5],
						nodeRec[6], nodeRec[7], nodeRec[8], nodeRec[9],
						nodeRec[10], nodeRec[11] });*/
				nodeDtlHS.put(currentNodeId, new Object[] { nodeRec[1],
						 nodeRec[3], nodeRec[4] ,nodeRec[6],nodeRec[2]});

			}

			/************************************************************************************************************************************************************************************/
			/*
			 * Setting operation value options And relative sequence value
			 * options
			 */
			operationValueOptions = "<option value=\"new\">New</option>"
					+ "<option value=\"replace\">Replace</option>"
					+ "<option value=\"append\">Append</option>"
					+ "<option value=\"delete\">Delete</option>";
			relativeSeqValueOptions = "<option value=\"\"></option>";
			// Arrays.sort(allRelatedSeqs, Collections.reverseOrder());
			if (allRelatedSeqs != null) {
				for (int i = allRelatedSeqs.length - 1; i >= 0; i--) {
					relativeSeqValueOptions += "<option value=\""
							+ allRelatedSeqs[i] + "\">" + allRelatedSeqs[i]
							+ "</option>";
				}
			}

			/************************************************************************************************************************************************************************************/

			if (firstNode != null) {
				Object[] firstNodeObj = (Object[]) nodeDtlHS.get(firstNode);

				htmlSB
						.append("    <DIV align=\"left\" style=\"padding-left: 5px;\">\n");
				htmlSB
						.append("    <DIV style=\"color: black;font-weight: bold;font-size: medium;\" >\n");
				htmlSB.append("      "
						+ ((String) firstNodeObj[0]).replaceAll(" ", "&nbsp;")
						+ "\n");
				htmlSB.append("    </DIV>\n");
				htmlSB.append("    <UL class=\"branch\" id='branch"
						+ firstNode.intValue()
						+ "' style=\"padding-left: 30px;\">\n");

				ArrayList<Integer> childList = childNodeHS.get(firstNode);
				for (int i = 0; i < childList.size(); i++) {

					StringBuffer sb = getChildStructure((Integer) childList
							.get(i), childNodeHS, nodeDtlHS);
					htmlSB.append(sb);
				}
				htmlSB.append("    </UL>\n");
				htmlSB.append("    </DIV>\n");
			}

		}
		return htmlSB.toString();
	}
	private StringBuffer getChildStructure(Integer nodeId,
			TreeMap<Integer, ArrayList<Integer>> childNodeHS,
			Hashtable<Integer, Object[]> nodeDtlHS) {
		
		StringBuffer htmlStringBuffer = new StringBuffer();
		ArrayList<Integer> childList = childNodeHS.get(nodeId);
		Object[] nodeDtlObj = (Object[]) nodeDtlHS.get(nodeId);
		String displayName = (String) nodeDtlObj[0];
		String NodeName = (String) nodeDtlObj[2];
		// Integer nodeNo = (Integer)nodeDtlObj[8];
		String FolderName = (String) nodeDtlObj[1];
		String nodeTypeIndi=(String) nodeDtlObj[3];
		Integer parentNodeId=(Integer)nodeDtlObj[4];
		//System.out.println("nodeTypeIndi->"+nodeTypeIndi);
		if(nodeTypeIndi.equalsIgnoreCase("S")){
			//System.out.println("NodeName->"+NodeName);
			Vector<DTOWorkSpaceNodeDetail>  dtoparentDtl=docMgmt.getNodeDetail(workspaceid, parentNodeId);
		
			for(DTOWorkSpaceNodeDetail dto:dtoparentDtl)
			{	
				if(dto.getNodeTypeIndi()=='F'){
					Vector<DTOWorkSpaceNodeDetail>  dtoparentDtl1=docMgmt.getNodeDetail(workspaceid, dto.getParentNodeId());
					
					for(DTOWorkSpaceNodeDetail dto1:dtoparentDtl1)
					{	
						//System.out.println("NodeName parent->"+dto1.getNodeName());
						NodeName=dto1.getNodeName();
					}
				}
				else{
					//System.out.println("NodeName parent->"+dto.getNodeName());
					NodeName=dto.getNodeName();
				}
				
			}
		}
		if (childList.size() > 0) {

			StringBuffer strAttr = new StringBuffer();
			ArrayList lstTemp = new ArrayList();

			strAttr.append("[ ");
			for (Iterator<DTOWorkSpaceNodeAttribute> iterator = allNodesType0002Attrs
					.iterator(); iterator.hasNext();) {
				DTOWorkSpaceNodeAttribute dtoType0002Attr = iterator.next();

				if (dtoType0002Attr.getNodeId() == nodeId.intValue()) {

					if (dtoType0002Attr.getAttrValue() != null
							&& !dtoType0002Attr.getAttrValue().equals("")) {
						strAttr.append(dtoType0002Attr.getAttrName() + ":"
								+ " <SPAN style=\"color: green;\">"
								+ dtoType0002Attr.getAttrValue() + "</SPAN>");
					} else {
						strAttr.append(dtoType0002Attr.getAttrName() + ":"
								+ " <SPAN style=\"color: red;\">" + "No Value"
								+ "</SPAN>");
					}
					strAttr.append(", ");
					lstTemp.add(dtoType0002Attr);

				}
			}
			allNodesType0002Attrs.removeAll(lstTemp);
			if (strAttr.length() > 1
					&& strAttr.charAt(strAttr.length() - 2) == ',')
				strAttr.deleteCharAt(strAttr.length() - 2);

			strAttr.append("]");

			htmlStringBuffer
					.append("<LI style=\"list-style: square; color: black;padding-top: 5px;\" >\n");
			htmlStringBuffer.append("<SPAN title=\"" + displayName
					+ "\" style=\"font-weight: bold;\">" + NodeName);

			if (!strAttr.toString().equals("[ ]")) {
				htmlStringBuffer.append("<SPAN  style=\"color: black;\">"
						+ strAttr + "</SPAN>");
			}
			htmlStringBuffer.append("</SPAN>");
			htmlStringBuffer.append("<DIV>\n");
			htmlStringBuffer.append("<UL style=\"padding-left: 30px;\">\n");
			for (int i = 0; i < childList.size(); i++) {
				htmlStringBuffer.append(getChildStructure((Integer) childList
						.get(i), childNodeHS, nodeDtlHS));
			}
			htmlStringBuffer.append("</UL></DIV></LI>\n");
		} else {
			// Getting the Node History Details into lastHistory;
			String statusColor = "";
			boolean hideDetailDiv = false;
			DTOWorkSpaceNodeHistory lastHistory = null;

			for (Iterator<DTOWorkSpaceNodeHistory> iterator = allNodesLastHistory
					.iterator(); iterator.hasNext();) {
				DTOWorkSpaceNodeHistory nodeHis = iterator.next();

				if (nodeHis.getNodeId() == nodeId) {
					lastHistory = nodeHis;

					allNodesLastHistory.remove(nodeHis);
					break;
				}

			}
			/* Adding HTML For the leaf node. */
			htmlStringBuffer
					.append("<LI style=\"list-style: square;padding-top: 2px;\">");

			
			if (lastHistory != null && lastHistory.getFileName() != null
					&& !lastHistory.getFileName().equals("No File")) {
				// only approved (StageId = 100) nodes will be checked.
				
				Vector<DTOWorkSpaceNodeDetail>  vwsnd = docMgmt.getNodeDetail(lastHistory.getWorkSpaceId(),lastHistory.getNodeId());
				
				DTOWorkSpaceNodeDetail dtowsand = (DTOWorkSpaceNodeDetail) vwsnd
				.get(0);
				
				if (lastHistory.getStageId() == 100) {
					htmlStringBuffer
							.append("<input type= \"checkbox\" id='CHK_"
									+ nodeId.intValue()
									+ "' name ='leafIds' value='"
									+ nodeId.intValue()
									+ "' style= \"border:0px\" checked=\"checked\" onclick=\"hidshowdiv('"
									+ nodeId.intValue() + "');\">");
					statusColor = "green";
					hideDetailDiv = false;
				} else {
					htmlStringBuffer
							.append("<input type= \"checkbox\" id='CHK_"
									+ nodeId.intValue()
									+ "' name ='leafIds' value='"
									+ nodeId.intValue()
									+ "' style= \"border:0px\" title=\"This file is not Approved\" onclick=\"hidshowdiv('"
									+ nodeId.intValue() + "');\">");
					statusColor = "red";
					hideDetailDiv = true;
				}
				htmlStringBuffer.append("<IMG  src=\"images/empty2.gif\">"
						+ "<a href = \"openfile.do?fileWithPath="
						+ dtows.getBaseWorkFolder()
						+ dtowsand.getFolderName() + "/"
						+ dtowsand.getFileName()
						+ "\" target=\"_blank\" id=\"leaf_" + nodeId.intValue()
						+ "\">"
						+ dtowsand.getFolderName().replaceAll(" ", "&nbsp;")
						+ "</a>");
				htmlStringBuffer.append("<span style=\"color: " + statusColor
						+ ";font-weight: bold;\"> [Approved]</span>");

				if (hideDetailDiv) {
					htmlStringBuffer
							.append("<div id=\"userDetail_"
									+ nodeId.intValue()
									+ "\" style=\"padding-left: 30px;padding-top: 5px;display: none;\">");
				} else {
					htmlStringBuffer
							.append("<div id=\"userDetail_"
									+ nodeId.intValue()
									+ "\" style=\"padding-left: 30px;padding-top: 5px;\">");
				}
				// htmlStringBuffer.append("<span style=\"border:1px solid #669;padding: 4px;\">");
				if (allRelatedSeqs != null) {
					htmlStringBuffer
							.append("&nbsp;<span class=\"title\">Operation:</span>");
					htmlStringBuffer.append("<select name=\"operation_"
							+ nodeId.intValue() + "\">");
					htmlStringBuffer.append(operationValueOptions);
					htmlStringBuffer.append("</select>");

					htmlStringBuffer
							.append("&nbsp;<span class=\"title\">Ref Seq:</span>");
				//	System.out.println("NodeName-->"+NodeName);
					
			        String word=NodeName.replaceAll(" ","::");
			      //  System.out.println("word-->"+word);
			        
					htmlStringBuffer
							.append("<select id="
									+ word
									+ " name=\"refSeq_"
									+ nodeId.intValue()
									+ "\" onchange='getRefId(this.id,this.value,this.name);'>");
					htmlStringBuffer.append(relativeSeqValueOptions);
					htmlStringBuffer.append("</select>");
					// htmlStringBuffer.append("<input type=\"text\" name=\"refSeq_"+nodeId.intValue()+"\" value=\""+refSeqNo+"\" size=\"5\" style=\"padding-left: 3px;\">");

					htmlStringBuffer
							.append("&nbsp;<span class=\"title\">Ref ID:</span>");
					htmlStringBuffer.append("<input type=\"text\" id=\"refID_"
							+ nodeId.intValue() + "\"  name=\"refID_"
							+ nodeId.intValue() + "\" value=\"" + refIdSuffix
							+ "\" style=\"padding-left: 5px;\">");
					htmlStringBuffer.append("<span id=\"refLoaderID_"
							+ nodeId.intValue() + "\"></span>");

				} else {
					htmlStringBuffer
							.append("&nbsp;<span class=\"title\">Operation:</span>");
					htmlStringBuffer
							.append("<input type=\"text\" name=\"operation_"
									+ nodeId.intValue()
									+ "\" value=\"new\" size=\"5\" style=\"padding-left: 3px;\" readonly=\"true\">");

					htmlStringBuffer
							.append("&nbsp;<span class=\"title\">Ref Seq:</span>");
					htmlStringBuffer
							.append("<input type=\"text\" name=\"refSeq_"
									+ nodeId.intValue()
									+ "\" value=\"\" size=\"5\" style=\"padding-left: 3px;\" readonly=\"true\">");

					htmlStringBuffer
							.append("&nbsp;<span class=\"title\">Ref ID:</span>");
					htmlStringBuffer
							.append("<input type=\"text\" name=\"refID_"
									+ nodeId.intValue()
									+ "\" value=\"\" style=\"padding-left: 5px;\" readonly=\"true\">");
				}

				// htmlStringBuffer.append("</span>");
				htmlStringBuffer.append("</div>");
			} else {
				htmlStringBuffer
						.append("<input type= \"checkbox\" title=\"There isn't any document to publish.\" name ='leafIds' value='"
								+ nodeId.intValue()
								+ "' style= \"border:0px\" disabled=\"disabled\">");
				htmlStringBuffer.append("<IMG src=\"images/empty2.gif\">"
						+ FolderName);
			}

			htmlStringBuffer.append("</LI>");

		}
		return htmlStringBuffer;
	}
	public Vector<Object[]> getTreeNodes(String workspaceID, int userGroupCode,
			int userCode) {
		try {
//			nodeInfoVector = docMgmt.getNodeAndRightDetail(workspaceID,
//					userGroupCode, userCode);
			nodeInfoVector = docMgmt.getWorkspaceNodeDetails(workspaceID);
			
			return nodeInfoVector;
		} catch (Exception e) {
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
	public String[] getAllRelatedSeqs() {
		return allRelatedSeqs;
	}
	public void setAllRelatedSeqs(String[] allRelatedSeqs) {
		this.allRelatedSeqs = allRelatedSeqs;
	}
	public String getRefIdSuffix() {
		return refIdSuffix;
	}
	public void setRefIdSuffix(String refIdSuffix) {
		this.refIdSuffix = refIdSuffix;
	}
}
