package com.docmgmt.struts.actions.template;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import com.docmgmt.dto.DTOTemplateNodeAttr;
import com.docmgmt.dto.DTOTemplateNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

	public class DocGetDetailForEditStructureAction extends ActionSupport
	{
		private static final long serialVersionUID = 1L;

		private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
		
		public String userTypeName;

		@Override
		public String execute()
		{
			userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
			getNodeDetail=docMgmtImpl.getTemplateNodeDetailByNodeId(templateId,nodeId);
			DTOTemplateNodeAttrDetail tnad = new DTOTemplateNodeAttrDetail();
			tnad.setTemplateId(templateId);
			tnad.setNodeId(nodeId);
			getNodeAttrDetail=docMgmtImpl.getTemplateNodeAttrDtl(tnad);		
			DTOTemplateNodeAttr tna = new DTOTemplateNodeAttr();
			//tna.setTemplateId(templateId);
			//tna.setNodeId(nodeId);
			//getNodeAttr=docMgmtImpl.getTemplateNodeAttribute(tna);
			KnetProperties knetProperties = KnetProperties.getPropInfo();
			templateBasePath = knetProperties.getValue("TemplateWorkFolder");
			isLeafNode = docMgmtImpl.isLeafNode(templateId, nodeId);
			
			ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl.showTemplateNodeHistory(templateId, nodeId);
			
			Collections.reverse(tempHistory);
			for (Iterator<DTOWorkSpaceNodeHistory> iterator = tempHistory
					.iterator(); iterator.hasNext();) {
				DTOWorkSpaceNodeHistory dtoHistory = iterator.next();

				Vector attrHistory = docMgmtImpl
						.getWorkspaceNodeAttrHistorybyTranNo(dtoHistory
								.getWorkSpaceId(), dtoHistory.getNodeId(),
								dtoHistory.getTranNo());
				dtoHistory.setAttrHistory(attrHistory);
			}
			Collections.reverse(tempHistory);
			
			fullNodeHistory = tempHistory;

			for (int i = 0; i < fullNodeHistory.size(); i++) {
				DTOWorkSpaceNodeHistory dto = (DTOWorkSpaceNodeHistory) fullNodeHistory
						.get(i);
				dto.setHistoryDocumentSize(FileManager.getFileSize(dto.getBaseWorkFolder() +"/" + dto.getFolderName() + "/" + dto.getFileName()));
			}
			
			return SUCCESS;
		}
		public String templateBasePath;
		public int isLeafNode;
		public String templateId;
		public String nodeName;
		public int nodeId;
		public Vector getNodeDetail;
		public Vector getNodeAttrDetail;
		public Vector getNodeAttr;
		ArrayList<DTOWorkSpaceNodeHistory> fullNodeHistory;
		
		
		
		public ArrayList<DTOWorkSpaceNodeHistory> getFullNodeHistory() {
			return fullNodeHistory;
		}
		public void setFullNodeHistory(ArrayList<DTOWorkSpaceNodeHistory> fullNodeHistory) {
			this.fullNodeHistory = fullNodeHistory;
		}
		public int getIsLeafNode() {
			return isLeafNode;
		}
		public void setIsLeafNode(int isLeafNode) {
			this.isLeafNode = isLeafNode;
		}
		public String getNodeName() {
			return nodeName;
		}

		public void setNodeName(String nodeName) {
			this.nodeName = nodeName;
		}

		public int getNodeId() {
			return nodeId;
		}

		public void setNodeId(int nodeId) {
			this.nodeId = nodeId;
		}

		public String getTemplateId() {
			return templateId;
		}

		public void setTemplateId(String templateId) {
			this.templateId = templateId;
		}

		public Vector getGetNodeDetail() {
			return getNodeDetail;
		}

		public void setGetNodeDetail(Vector getNodeDetail) {
			this.getNodeDetail = getNodeDetail;
		}

		public Vector getGetNodeAttrDetail() {
			return getNodeAttrDetail;
		}

		public void setGetNodeAttrDetail(Vector getNodeAttrDetail) {
			this.getNodeAttrDetail = getNodeAttrDetail;
		}

		public Vector getGetNodeAttr() {
			return getNodeAttr;
		}

		public void setGetNodeAttr(Vector getNodeAttr) {
			this.getNodeAttr = getNodeAttr;
		}

	}
