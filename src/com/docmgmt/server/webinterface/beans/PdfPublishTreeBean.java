package com.docmgmt.server.webinterface.beans;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.master.WorkSpaceNodeHistory;
import com.docmgmt.server.prop.PropertyInfo;

public class PdfPublishTreeBean {

	private int userCode;
	private Vector nodeInfoVector;
	private String userType;
	private long nodeSize;
	public String filename;
	Vector<Integer> nodeids = new Vector<Integer>();
	String htmlSubmissionpah;
	PropertyInfo propInfo = PropertyInfo.getPropInfo();

	DocMgmtImpl docMgmtImpl;

	DTOWorkSpaceMst wsMst;
	DTOWorkSpaceNodeHistory nodeHis;
	
	public DTOWorkSpaceNodeHistory getdatatodisplay;

	String allnodeids;
	Vector<DTOWorkSpaceNodeHistory> nodeInfo;
	String wsId;
	public String previousNodeName = "";
	public String currentNodeName = "";
	public String nodeWithAttr = "";
	
	public ArrayList<Integer> nodeidlist;
	public int lastTranNo;
	
	public Vector<DTOWorkSpaceNodeAttribute> prevNodeAttr = new Vector<DTOWorkSpaceNodeAttribute>();
	public Vector<DTOWorkSpaceNodeAttribute> wsNodeAttr = new Vector<DTOWorkSpaceNodeAttribute>();

	public PdfPublishTreeBean() {

		nodeInfoVector = new Vector();
		docMgmtImpl = new DocMgmtImpl();
		nodeInfo = new Vector<DTOWorkSpaceNodeHistory>();
		allnodeids = "";
	}

	public Vector<DTOWorkSpaceNodeHistory> generatePdfTree(String workspaceID,
			int userGroupCode, int userCode, char nonPublishableNode) {

		wsMst = docMgmtImpl.getWorkSpaceDetail(workspaceID);
		WorkSpaceNodeHistory wnh = new WorkSpaceNodeHistory();
		nodeids = wnh.getNodeDetailsForPdfPublish(workspaceID);
		// System.out.println("Size=>" + nodeids.size());

		Vector workspaceTreeVector = getTreeNodes(workspaceID, userGroupCode,
				userCode, nonPublishableNode);
		if (workspaceTreeVector.size() > 0) {
			TreeMap childNodeHS = new TreeMap();
			Hashtable nodeDtlHS = new Hashtable();
			Integer firstNode = null;
			nodeSize = workspaceTreeVector.size();

			for (int i = 0; i < workspaceTreeVector.size(); i++) {
				Object[] nodeRec = (Object[]) workspaceTreeVector.get(i);
				Integer parentNodeId = (Integer) nodeRec[2];
				Integer currentNodeId = (Integer) nodeRec[0];
				if (i == 0) {
					firstNode = currentNodeId;
				}
				if (childNodeHS.containsKey(parentNodeId)) {

					List childList = (List) childNodeHS.get(parentNodeId);
					childList.add(currentNodeId);
					if (!childNodeHS.containsKey(currentNodeId)) {
						childNodeHS.put(currentNodeId, new ArrayList());
					}
				} else {
					List childList = new ArrayList();
					childList.add(currentNodeId);
					childNodeHS.put(parentNodeId, childList);
					if (!childNodeHS.containsKey(currentNodeId)) {
						childNodeHS.put(currentNodeId, new ArrayList());
					}
				}

				nodeDtlHS.put(currentNodeId, new Object[] { nodeRec[1],
						nodeRec[2], nodeRec[3], nodeRec[4], nodeRec[5],
						nodeRec[6], nodeRec[7], nodeRec[8], nodeRec[9],
						nodeRec[10], nodeRec[11] });

			}

			if (firstNode != null) {

				List childList = (List) childNodeHS.get(firstNode);

				for (int i = 0; i < childList.size(); i++) {

					StringBuffer sb = getChildStructure((Integer) childList
							.get(i), childNodeHS, nodeDtlHS);

				}

			}

		}

		return nodeInfo;
	}

	private StringBuffer getChildStructure(Integer nodeId, TreeMap childNodeHS,
			Hashtable nodeDtlHS) {

		StringBuffer htmlStringBuffer = new StringBuffer();
		List childList = (List) childNodeHS.get(nodeId);

		if (childList.size() > 0) {
			for (int i = 0; i < childList.size(); i++) {
				htmlStringBuffer.append(getChildStructure((Integer) childList
						.get(i), childNodeHS, nodeDtlHS));
			}

		} else {
			if (nodeids.contains(nodeId)) {
				
				nodeidlist = docMgmtImpl.getNodeIdFromNodeHistory(wsMst.getWorkSpaceId());
				
				if(nodeidlist.contains(nodeId))
				{
					lastTranNo = docMgmtImpl.getMaxTranNo(wsMst
							.getWorkSpaceId(), nodeId);
					
					
					/* nodeHis = docMgmtImpl
								.getWorkspaceNodeHistorybyTranNo(wsMst
										.getWorkSpaceId(), nodeId, lastTranNo);*/
					nodeHis = docMgmtImpl
							.getWorkspaceNodeHistorybyTranNoForPDF(wsMst
									.getWorkSpaceId(), nodeId, lastTranNo);
					 
					
				}
				else
				{
					nodeHis = docMgmtImpl.getWorkspaceNodeHistoryforAttr(wsMst
							.getWorkSpaceId(), nodeId,1);
				}
				
				/*getdatatodisplay = docMgmtImpl.getdetailstodisplaymergedata(wsMst.getWorkSpaceId(), lastTranNo);*/
				try {
					
													
					ArrayList<DTOWorkSpaceNodeDetail> parentNodeList = docMgmtImpl
							.getAllParentsNodes(wsMst.getWorkSpaceId(), nodeId);

					
					// If stage=100(max stage) only then publish the node
					// document/file

					// Allow only approved files to merge

				/*	if (nodeHis.getStageId() != 100) {
						nodeHis.setFileName(null);
					}*/
					
					/*if(nodeHis.getStageId()==10 && nodeHis.getAttriId()==0)
					{
						nodeHis.setFileName(null);
					}*/

					if (nodeHis.getFileName() != null
							&& !nodeHis.getFileName().equals("")
							/*&& !nodeHis.getFileName().equals("No File")*/) {

						Vector<DTOWorkSpaceNodeAttribute> wsNodeAttr = null;
						boolean isdiffer = false;
						boolean hasAttr = false;
						for (int l = 0; l < parentNodeList.size(); l++) {
							DTOWorkSpaceNodeDetail dto = parentNodeList.get(l);
							//						
							if (dto.getParentID() == 0)
								continue;

							wsNodeAttr = docMgmtImpl.getNodeAttributes(wsMst
									.getWorkSpaceId(), dto.getNodeId());
							if (wsNodeAttr.size() >= 1) {
								currentNodeName = dto.getNodeName();
								hasAttr = true;

								if (!currentNodeName.equals(previousNodeName)) {
									previousNodeName = currentNodeName;
									prevNodeAttr = wsNodeAttr;
									isdiffer = true;
								} else {
									if (prevNodeAttr.size() != wsNodeAttr
											.size()) {
										previousNodeName = currentNodeName;
										prevNodeAttr = wsNodeAttr;
										isdiffer = true;

									} else if (!checkAttr(prevNodeAttr,
											wsNodeAttr)) {
										previousNodeName = currentNodeName;
										prevNodeAttr = wsNodeAttr;
										isdiffer = true;

									}
								}

								break;
							}
						}

						DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();

						WorkSpaceNodeHistory wnh = new WorkSpaceNodeHistory();
						if(nodeHis.getFileName() == null)
						{
							
						}
						else
						{
							
							dto = wnh.getNodeDetailsForPdfPublish(wsMst
									.getWorkSpaceId(), nodeHis.getNodeId());
						}
						if (wsNodeAttr != null)
							dto.setAttrHistory(wsNodeAttr);

						if (isdiffer) {
							dto.setAttrNodeName(currentNodeName);
						} else if (hasAttr) {
							dto.setAttrNodeName("yes");
						} else {
							dto.setAttrNodeName("no");
						}
					
						nodeInfo.addElement(dto);
					}else if (nodeHis.getFileName() == null)
					{
						nodeHis = docMgmtImpl.getWorkspaceNodeHistoryforAttr(wsMst
								.getWorkSpaceId(), nodeId,0);
						
						nodeHis.setNodeName(nodeHis.getNodeDisplayName());
						if(nodeHis.getAttrName()!=null)
						{
						nodeInfo.addElement(nodeHis);
						}
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();

				}
			}

		}

		return htmlStringBuffer;
	}

	public Vector getTreeNodes(String workspaceID, int userGroupCode,
			int userCode, char nonPublishableNode) {
		try {

			if (nonPublishableNode == 'Y')
				nodeInfoVector = docMgmtImpl.getNodeAndRightDetail(workspaceID,
						userGroupCode, userCode);
			else
				nodeInfoVector = docMgmtImpl.getUserWisePublishableNodes(
						workspaceID, userGroupCode, userCode);

			return nodeInfoVector;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeInfoVector;

	}

	private boolean checkAttr(Vector<DTOWorkSpaceNodeAttribute> prevNodeAttr2,
			Vector<DTOWorkSpaceNodeAttribute> wsNodeAttr2) {
		// TODO Auto-generated method stub
		for (int prevNodeIndex = 0; prevNodeIndex < prevNodeAttr2.size(); prevNodeIndex++) {
			String prevAttrName = prevNodeAttr2.get(prevNodeIndex)
					.getAttrName();
			String prevAttrValue = prevNodeAttr2.get(prevNodeIndex)
					.getAttrValue();
			boolean innerFlag = false;
			for (int currNodeIndex = 0; currNodeIndex < wsNodeAttr2.size(); currNodeIndex++) {
				String currAttrName = wsNodeAttr2.get(currNodeIndex)
						.getAttrName();
				String currAttrValue = wsNodeAttr2.get(currNodeIndex)
						.getAttrValue();
				if (prevAttrName.equalsIgnoreCase(currAttrName)) {
					if (prevAttrValue.equalsIgnoreCase(currAttrValue)) {
						innerFlag = true;
						break;
					}
				}
			}
			if (!innerFlag)
				return false;
		}
		return true;
	}
}
