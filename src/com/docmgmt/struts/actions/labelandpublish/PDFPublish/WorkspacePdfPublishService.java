package com.docmgmt.struts.actions.labelandpublish.PDFPublish;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;

public class WorkspacePdfPublishService {

	public Vector parentNodes;

	public String attrValue, attrName;
	public Integer nodeId, nodeAttrId;
	public int childNode;
	public int childNodeSize = 0;
	public int iParentId = 1;
	public String wsId;

	public String nodeName, nodeDisplayName;
	public String folderName;
	public String Remark;
	public String AttrValue;

	public Integer userId;

	Vector<Integer> AllNodesofHistory;

	DocMgmtImpl docMgmtInt;

	private int[] leafIds;
	
	public int count=0;
	
	public ArrayList<DTOWorkSpaceNodeHistory> Latesthistoryofnodes;
	public Vector<DTOWorkSpaceNodeAttrDetail> allnodattrdetail;

	public ArrayList<DTOWorkSpaceNodeDetail> allNodesDtl;
	DTOWorkSpaceNodeDetail dtoWsNodeDetail;

	public WorkspacePdfPublishService() {

		allNodesDtl = new ArrayList<DTOWorkSpaceNodeDetail>();
		AllNodesofHistory = new Vector<Integer>();
		docMgmtInt = new DocMgmtImpl();

	}

	public ArrayList<DTOWorkSpaceNodeDetail> workspacePublish(
			String workspaceId, int[] nodes) {
		try {

			wsId = workspaceId;

			folderName = "";

			parentNodes = new Vector();
			userId = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userid").toString());

			AllNodesofHistory = docMgmtInt.getSelectedNodeDetailsForPdfPublish(
					workspaceId, nodes);

			iParentId = 0;

			parentNodes = docMgmtInt.getChildNodeByParentForPdfPublishForM1(
					iParentId, wsId);

			getChildNode(parentNodes, iParentId, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return allNodesDtl;
	}

	/**
	 * This Method recursively create Node for Parent Node
	 * 
	 * @param childNodes
	 * @throws Exception
	 */

	public void getChildNode(Vector childNodes, int parentId, int IdValue)
			throws Exception {
		
		
		if (childNodes.size() == 0) {
			// if file attached at node or its parent node
			if (AllNodesofHistory.contains(nodeId)) {
				dtoWsNodeDetail = new DTOWorkSpaceNodeDetail();

				dtoWsNodeDetail.setNodeId(nodeId);
				dtoWsNodeDetail.setNodeName(nodeName);
				dtoWsNodeDetail.setNodeDisplayName(nodeDisplayName);
				dtoWsNodeDetail.setParentNodeId(parentId);
				dtoWsNodeDetail.setFolderName(folderName);
				//dtoWsNodeDetail.setRemark(Remark);
				//Remark="";
				dtoWsNodeDetail.setAttrValue(AttrValue);
				AttrValue="";
				
				allNodesDtl.add(dtoWsNodeDetail);

			}// if end of history check

		}// if end
		else {
				//Latesthistoryofnodes = docMgmtInt.getNodeLatestDetailsForPdfPublish(wsId);
			
			allnodattrdetail = docMgmtInt.getAllNodeAttrDetail(wsId);
			for (int i = 0; i < childNodes.size(); i++) {
				DTOWorkSpaceNodeDetail dtowsnd = (DTOWorkSpaceNodeDetail) childNodes
						.get(i);

				nodeId = dtowsnd.getNodeId();
				nodeName = dtowsnd.getNodeName();
				nodeDisplayName = dtowsnd.getNodeDisplayName().trim();
				//
				// System.out.println("Folder Name==" + dtowsnd.getFileName()
				// + " == " + dtowsnd.getFolderName());
				folderName = dtowsnd.getFolderName();
				
				for(int nodelatesthis=0; nodelatesthis<allnodattrdetail.size();nodelatesthis++)
				{
					//int nid = Latesthistoryofnodes.get(nodelatesthis).getNodeID();
					int nid = allnodattrdetail.get(nodelatesthis).getNodeId();
					
					if(nid == nodeId)
					{
						//Remark = Latesthistoryofnodes.get(nodelatesthis).getAttrValue();
					//	Remark = allnodattrdetail.get(nodelatesthis).getAttrValue();
						AttrValue = allnodattrdetail.get(nodelatesthis).getAttrValue();
						count+=1;
					}
					/*else
					{
						Remark = "";
					}*/
				}
				
				if(count==0)
				{
					//Remark ="";
					AttrValue="";
					
				}
				
				if (AllNodesofHistory.contains(nodeId)) {

					int isLeaf = docMgmtInt
							.isLeafNodes(wsId, nodeId.intValue());

					if (isLeaf == 0) {// This is a parent node

						dtoWsNodeDetail = new DTOWorkSpaceNodeDetail();

						dtoWsNodeDetail.setNodeId(nodeId);
						dtoWsNodeDetail.setNodeDisplayName(nodeDisplayName);
						dtoWsNodeDetail.setNodeName(nodeName);
						dtoWsNodeDetail.setParentNodeId(parentId);
						dtoWsNodeDetail.setFolderName(folderName);
						//dtoWsNodeDetail.setRemark(Remark);
						//Remark = "";
						dtoWsNodeDetail.setAttrValue(AttrValue);
						AttrValue="";
						allNodesDtl.add(dtoWsNodeDetail);
						parentNodes = docMgmtInt.getChildNodeByParent(nodeId
								.intValue(), wsId);
						getChildNode(parentNodes, nodeId.intValue(), IdValue);

					} else {
						parentNodes = docMgmtInt.getChildNodeByParent(nodeId
								.intValue(), wsId);
						getChildNode(parentNodes, nodeId.intValue(), IdValue);
					}

				}// if end of history vector

			} // for loop end
		}// main else end

	}

	public Vector getAllNodesofHistory() {
		return AllNodesofHistory;
	}

	public void setAllNodesofHistory(Vector allNodesofHistory) {
		AllNodesofHistory = allNodesofHistory;
	}

	public int[] getLeafIds() {
		return leafIds;
	}

	public void setLeafIds(int[] leafIds) {
		this.leafIds = leafIds;
	}

}// main class end
