package com.docmgmt.struts.actions.ImportProjects.ECTD;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;

public class ProjectDB {

	/*Class Constants*/
	public static final short PROJECT_CREATED = 0;
	public static final short PROJECT_DETAILS_NOT_FOUND = 1;
	
	DTOWorkSpaceMst workSpaceMst;
	ArrayList<DTOWorkSpaceNodeDetail> nodeDtlDBList;
	ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrDtlDBList; 
	ArrayList<DTOWorkSpaceNodeHistory> nodeHistoryDBList;
	ArrayList<DTOWorkSpaceNodeAttrHistory> nodeAttrHistoryDBList;
	ArrayList<DTOWorkSpaceNodeVersionHistory> nodeVersionHistoryDBList;
	
	DocMgmtImpl docMgmtImpl;
	public ProjectDB() {
		docMgmtImpl = new DocMgmtImpl(); 
	}
	
	Short createNewProject(DTOWorkSpaceMst newWorkspace){
		
		DTOWorkSpaceNodeDetail rootNodeDtl = newWorkspace.getRootNodeDtl();
		
		//Initialize all Members
		initDBMembers();
		
		//Create New WorkSpace
		if(newWorkspace == null
				|| newWorkspace.getWorkSpaceDesc() == null
				|| newWorkspace.getWorkSpaceDesc().trim().equals("")){
			
			return PROJECT_DETAILS_NOT_FOUND;
		}
		
		//Get the path properties
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String baseWorkFolder = propertyInfo.getValue("BaseWorkFolder");
		String basePublishFolder = propertyInfo.getValue("BasePublishFolder");
		
		//Set workspaceMst Info
		workSpaceMst = newWorkspace;
		workSpaceMst.setBasePublishFolder(basePublishFolder);
		workSpaceMst.setBaseWorkFolder(baseWorkFolder);
		workSpaceMst.setDocTypeCode("0001");//Hard Coded Field
		workSpaceMst.setProjectType('P');//Hard Coded Field
		
		String newWorkspaceId = docMgmtImpl.insertWorkspaceMst(workSpaceMst);
		workSpaceMst.setWorkSpaceId(newWorkspaceId);
		
		nodeDtlDBList.add(rootNodeDtl);
		
		//Fill Up All Children Node Info
		fillUpNodeInfoForDB(rootNodeDtl);
		
		return PROJECT_CREATED;
	}
	void initDBMembers(){
		workSpaceMst = null;
		nodeDtlDBList = new ArrayList<DTOWorkSpaceNodeDetail>();
		nodeAttrDtlDBList = new ArrayList<DTOWorkSpaceNodeAttrDetail>();
		nodeHistoryDBList = new ArrayList<DTOWorkSpaceNodeHistory>();
		nodeAttrHistoryDBList= new ArrayList<DTOWorkSpaceNodeAttrHistory>();
		nodeVersionHistoryDBList = new ArrayList<DTOWorkSpaceNodeVersionHistory>();
		
		//internalLabelMst = new DTOInternalLabelMst();
		
	}
	void fillUpNodeInfoForDB(DTOWorkSpaceNodeDetail currentNodeDtl){
		//Add node
		currentNodeDtl.setWorkspaceId(workSpaceMst.getWorkSpaceId());
		currentNodeDtl.setModifyBy(workSpaceMst.getModifyBy());
		nodeDtlDBList.add(currentNodeDtl);
		
		//Add Default Attribute 'FileLastModified'
		
		//Add node attributes
		ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrDtlList = currentNodeDtl.getNodeAttrList();
		for (DTOWorkSpaceNodeAttrDetail nodeAttrDetail : nodeAttrDtlList) {
			nodeAttrDetail.setWorkspaceId(currentNodeDtl.getWorkspaceId());
			nodeAttrDetail.setModifyBy(workSpaceMst.getModifyBy());
			nodeAttrDetail.setNodeId(currentNodeDtl.getNodeId());
		}
		nodeAttrDtlDBList.addAll(nodeAttrDtlList);
		
		//If there is a file attached to the node insert node history info 
		addNodeHistory(currentNodeDtl,100);//Approved Stage 
		
		//Add Node's children if any (Recursive process)
		if(currentNodeDtl.getChildNodeList() != null){
			for (DTOWorkSpaceNodeDetail childNodeDtl : currentNodeDtl.getChildNodeList()) {
				fillUpNodeInfoForDB(childNodeDtl);
			}
		}
	}
	
	void addNodeHistory(DTOWorkSpaceNodeDetail currentNodeDtl,int stageId){
		if(currentNodeDtl.getLatestNodeHistory() != null 
				&& currentNodeDtl.getLatestNodeHistory().getHistoryDocument() != null
				&& currentNodeDtl.getLatestNodeHistory().getHistoryDocument().exists()){

			//New TranNo
			int newTranNo = docMgmtImpl.getNewTranNo(currentNodeDtl.getWorkspaceId());
			
			//Node History
			DTOWorkSpaceNodeHistory nodeHistory = currentNodeDtl.getLatestNodeHistory();
			nodeHistory.setWorkSpaceId(currentNodeDtl.getWorkspaceId());
			nodeHistory.setNodeId(currentNodeDtl.getNodeId());
			nodeHistory.setTranNo(newTranNo);
			nodeHistory.setFileName(nodeHistory.getHistoryDocument().getName());
			nodeHistory.setFileType("");
			
			String folderPath = "/"+ nodeHistory.getWorkSpaceId() + "/" + nodeHistory.getNodeId() + "/" + nodeHistory.getTranNo();
			nodeHistory.setFolderName(folderPath);
			nodeHistory.setRequiredFlag('Y');
			nodeHistory.setStageId(stageId);
			nodeHistory.setRemark("");
			nodeHistory.setModifyBy(workSpaceMst.getModifyBy());
			nodeHistory.setStatusIndi('N');
			nodeHistory.setDefaultFileFormat("");
			
			nodeHistoryDBList.add(nodeHistory);
			
			//Node Version History
			DTOWorkSpaceNodeVersionHistory nodeVerHistory = new DTOWorkSpaceNodeVersionHistory();
			nodeVerHistory.setWorkspaceId(currentNodeDtl.getWorkspaceId());
			nodeVerHistory.setNodeId(currentNodeDtl.getNodeId());
			nodeVerHistory.setTranNo(newTranNo);
			nodeVerHistory.setPublished('N');
			nodeVerHistory.setDownloaded('N');
			nodeVerHistory.setActivityId("");
			nodeVerHistory.setModifyBy(workSpaceMst.getModifyBy());
			nodeVerHistory.setExecutedBy(workSpaceMst.getModifyBy());
			Timestamp ts = new Timestamp(new Date().getTime());
			nodeVerHistory.setExecutedOn(ts);
			nodeVerHistory.setUserDefineVersionId("A-1");
			
			nodeVersionHistoryDBList.add(nodeVerHistory);
			
			//Node Attribute History
			ArrayList<DTOWorkSpaceNodeAttrHistory> nodeAttrHistoryList = new ArrayList<DTOWorkSpaceNodeAttrHistory>();
			for (DTOWorkSpaceNodeAttrDetail nodeAttrDetail : currentNodeDtl.getNodeAttrList()) {
				DTOWorkSpaceNodeAttrHistory nodeAttrHistory = new DTOWorkSpaceNodeAttrHistory();
				nodeAttrHistory.setWorkSpaceId(nodeAttrDetail.getWorkspaceId());
				nodeAttrHistory.setNodeId(nodeAttrDetail.getNodeId());
				nodeAttrHistory.setAttrId(nodeAttrDetail.getAttrId());
				nodeAttrHistory.setAttrValue(nodeAttrDetail.getAttrValue());
				nodeAttrHistory.setModifyBy(nodeAttrDetail.getModifyBy());
				nodeAttrHistory.setTranNo(newTranNo);
				nodeAttrHistory.setStatusIndi('N');    					
								
				nodeAttrHistoryList.add(nodeAttrHistory);
			}
			nodeAttrHistoryDBList.addAll(nodeAttrHistoryList);
		}
	}
}
