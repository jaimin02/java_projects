package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class DTOWorkSpaceNodeDetail implements Serializable,ITreeNode {

	String workspaceId;
	String oldNodeDisplayName;
	boolean publishFlag;
	String attrValue;
	HashMap<String,String> mapAttrValue;
	int nodeId;
	int itranNo;
	int nodeNo;
	String nodeName;
	String nodeDisplayName;
	char nodeTypeIndi;
	int parentNodeId;
	String folderName;
	char cloneFlag;
	char requiredFlag;
	Timestamp checkOutOn;
	int checkOutBy;
	char publishedFlag;
	String remark;
	int modifyBy;
	Timestamp modifyOn;	
	char statusIndi;
	char statusIndiForTree;
	String defaultFileFormat;
	int userCode;
	int userGroupCode;
	
	String workSpaceDesc;
	int tranNo;
	String fileName;
	String fileExt;
	String baseworkfolder;
	String userName;
	String fileVersionId;
	int pageNumber;
	String baseWorkFolder;
	String projectName;
	
	String docTemplatePath;
	String sortOrder;
	
	String orderByOn;	
	String effecitveDate;
	String userDefineVersionId;
	
	String stageDesc;
	
	int nodeLevel;
	
	Vector<DTOWorkSpaceNodeDetail> childNodes;
	
	String stfNodeCategoryName;
	String stfNodeSiteIdentifier;
	
	ArrayList<DTOWorkSpaceNodeDetail> childNodeList;
	ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrList;
	ArrayList<DTOSTFStudyIdentifierMst> STFStudyDetails;
	
	DTOWorkSpaceNodeHistory latestNodeHistory;
	String nodeValue;
	
	/**************************/
	String CanReadFlag;
	String canAddFlag;
	String CanEditFlag;
	String CanDeleteFlag;
	int iformNo;
	boolean LokedNodeFlag;
	
	String sequenceno;
	String ISTDateTime;
	String ESTDateTime;
	String refNodeId;
	int noOfApprover;
	int noOfApproved;
	char parentFlag;
	String uploadFlag;
	String stepNo;
	String stepName;
	String expectedResult;
	char isActive;
	String clientCode;
	int afterRepeatNodeId;
	
	String IDNo;
	String URSNo;
	String FRSNo;
	String URSDescription;
	String FSDescription;
	//boolean isStepNo;
	
	Vector <DTOWorkSpaceNodeDetail> refNodeIdFileName; 
		
	/*public boolean isStepNo() {
		return isStepNo;
	}
	public void setStepNo(boolean isStepNo) {
		this.isStepNo = isStepNo;
	}*/
	public int getAfterRepeatNodeId() {
		return afterRepeatNodeId;
	}
	public void setAfterRepeatNodeId(int afterRepeatNodeId) {
		this.afterRepeatNodeId = afterRepeatNodeId;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getStepNo() {
		return stepNo;
	}
	public void setStepNo(String stepNo) {
		this.stepNo = stepNo;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getExpectedResult() {
		return expectedResult;
	}
	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}
	public char getIsActive() {
		return isActive;
	}
	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
		
	public String getUploadFlag() {
		return uploadFlag;
	}
	public void setUploadFlag(String uploadFlag) {
		this.uploadFlag = uploadFlag;
	}
	public Vector<DTOWorkSpaceNodeDetail> getRefNodeIdFileName() {
		return refNodeIdFileName;
	}
	public void setRefNodeIdFileName(Vector<DTOWorkSpaceNodeDetail> refNodeIdFileName) {
		this.refNodeIdFileName = refNodeIdFileName;
	}
	public int getNoOfApprover() {
		return noOfApprover;
	}
	public void setNoOfApprover(int noOfApprover) {
		this.noOfApprover = noOfApprover;
	}
	public int getNoOfApproved() {
		return noOfApproved;
	}
	public void setNoOfApproved(int noOfApproved) {
		this.noOfApproved = noOfApproved;
	}
	public String getRefNodeId() {
		return refNodeId;
	}
	public void setRefNodeId(String refNodeId) {
		this.refNodeId = refNodeId;
	}
	public int getItranNo() {
		return itranNo;
	}
	public void setItranNo(int itranNo) {
		this.itranNo = itranNo;
	}
	public String getISTDateTime() {
		return ISTDateTime;
	}
	public void setISTDateTime(String iSTDateTime) {
		ISTDateTime = iSTDateTime;
	}
	public String getESTDateTime() {
		return ESTDateTime;
	}
	public void setESTDateTime(String eSTDateTime) {
		ESTDateTime = eSTDateTime;
	}
	public String getSequenceno() {
		return sequenceno;
	}
	public void setSequenceno(String sequenceno) {
		this.sequenceno = sequenceno;
	}
	/**************************/
	public String getNodeValue() {
		return nodeValue;
	}
	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}
	public ArrayList<DTOWorkSpaceNodeDetail> getChildNodeList() {
		return childNodeList;
	}
	public void setChildNodeList(ArrayList<DTOWorkSpaceNodeDetail> childNodeList) {
		this.childNodeList = childNodeList;
	}
	public ArrayList<DTOWorkSpaceNodeAttrDetail> getNodeAttrList() {
		return nodeAttrList;
	}
	public void setNodeAttrList(ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrList) {
		this.nodeAttrList = nodeAttrList;
	}
	public DTOWorkSpaceNodeHistory getLatestNodeHistory() {
		return latestNodeHistory;
	}
	public void setLatestNodeHistory(DTOWorkSpaceNodeHistory latestNodeHistory) {
		this.latestNodeHistory = latestNodeHistory;
	}
	public String getStfNodeSiteIdentifier() {
		return stfNodeSiteIdentifier;
	}
	public void setStfNodeSiteIdentifier(String stfNodeSiteIdentifier) {
		this.stfNodeSiteIdentifier = stfNodeSiteIdentifier;
	}
	public String getStfNodeCategoryName() {
		return stfNodeCategoryName;
	}
	public void setStfNodeCategoryName(String stfNodeCategoryName) {
		this.stfNodeCategoryName = stfNodeCategoryName;
	}
	public int getCheckOutBy() {
		return checkOutBy;
	}
	public void setCheckOutBy(int checkOutBy) {
		this.checkOutBy = checkOutBy;
	}
	public String getDefaultFileFormat() {
		return defaultFileFormat;
	}
	public void setDefaultFileFormat(String defaultFileFormat) {
		this.defaultFileFormat = defaultFileFormat;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public int getNodeNo() {
		return nodeNo;
	}
	public void setNodeNo(int nodeNo) {
		this.nodeNo = nodeNo;
	}
	public char getNodeTypeIndi() {
		return nodeTypeIndi;
	}
	public void setNodeTypeIndi(char nodeTypeIndi) {
		this.nodeTypeIndi = nodeTypeIndi;
	}
	public int getParentNodeId() {
		return parentNodeId;
	}
	public void setParentNodeId(int parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	public char getPublishedFlag() {
		return publishedFlag;
	}
	public void setPublishedFlag(char publishedFlag) {
		this.publishedFlag = publishedFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public char getRequiredFlag() {
		return requiredFlag;
	}
	public void setRequiredFlag(char requiredFlag) {
		this.requiredFlag = requiredFlag;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public int getUserGroupCode() {
		return userGroupCode;
	}
	public void setUserGroupCode(int userGroupCode) {
		this.userGroupCode = userGroupCode;
	}
	public char getCloneFlag() {
		return cloneFlag;
	}
	public void setCloneFlag(char cloneFlag) {
		this.cloneFlag = cloneFlag;
	}
    public String getBaseworkfolder() {
        return baseworkfolder;
    }
    public void setBaseworkfolder(String baseworkfolder) {
        this.baseworkfolder = baseworkfolder;
    }
    public String getBaseWorkFolder() {
        return baseWorkFolder;
    }
    public void setBaseWorkFolder(String baseWorkFolder) {
        this.baseWorkFolder = baseWorkFolder;
    }
    public String getFileExt() {
        return fileExt;
    }
    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileVersionId() {
        return fileVersionId;
    }
    public void setFileVersionId(String fileVersionId) {
        this.fileVersionId = fileVersionId;
    }
    public int getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public int getTranNo() {
        return tranNo;
    }
    public void setTranNo(int tranNo) {
        this.tranNo = tranNo;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getWorkSpaceDesc() {
        return workSpaceDesc;
    }
    public void setWorkSpaceDesc(String workSpaceDesc) {
        this.workSpaceDesc = workSpaceDesc;
    }
    public String getAttrValue() {
        return attrValue;
    }
    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
    public String getDocTemplatePath() {
        return docTemplatePath;
    }
    public void setDocTemplatePath(String docTemplatePath) {
        this.docTemplatePath = docTemplatePath;
    }
	
	public String getOrderByOn() {
		return orderByOn;
	}
	public void setOrderByOn(String orderByOn) {
		this.orderByOn = orderByOn;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}	
	public String getEffecitveDate() {
		return effecitveDate;
	}
	public void setEffecitveDate(String effecitveDate) {
		this.effecitveDate = effecitveDate;
	}
	public String getUserDefineVersionId() {
		return userDefineVersionId;
	}
	public void setUserDefineVersionId(String userDefineVersionId) {
		this.userDefineVersionId = userDefineVersionId;
	}
	public String getOldNodeDisplayName() {
		return oldNodeDisplayName;
	}
	public void setOldNodeDisplayName(String oldNodeDisplayName) {
		this.oldNodeDisplayName = oldNodeDisplayName;
	}
	public Timestamp getCheckOutOn() {
		return checkOutOn;
	}
	public void setCheckOutOn(Timestamp checkOutOn) {
		this.checkOutOn = checkOutOn;
	}
	public String getStageDesc() {
		return stageDesc;
	}
	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
	}
	public Vector<DTOWorkSpaceNodeDetail> getChildNodes() {
		return childNodes;
	}
	public void setChildNodes(Vector<DTOWorkSpaceNodeDetail> childNodes) {
		this.childNodes = childNodes;
	}
	public String getCanReadFlag() {
		return CanReadFlag;
	}
	public void setCanReadFlag(String canReadFlag) {
		CanReadFlag = canReadFlag;
	}
	public String getCanAddFlag() {
		return canAddFlag;
	}
	public void setCanAddFlag(String canAddFlag) {
		this.canAddFlag = canAddFlag;
	}
	public String getCanEditFlag() {
		return CanEditFlag;
	}
	public void setCanEditFlag(String canEditFlag) {
		CanEditFlag = canEditFlag;
	}
	public String getCanDeleteFlag() {
		return CanDeleteFlag;
	}
	public void setCanDeleteFlag(String canDeleteFlag) {
		CanDeleteFlag = canDeleteFlag;
	}
	public int getIformNo() {
		return iformNo;
	}
	public void setIformNo(int iformNo) {
		this.iformNo = iformNo;
	}
	
	/*******************************/
	/*methods of the interface*/
	/*******************************/
	public ArrayList getChildList() 
	{
		return getChildNodeList();
	}
	public int getNodeID() {
		return getNodeId();
	}
	public String getNodeLabel() {
		if (getChildList()==null || getChildList().size()==0)
			return getFolderName();
		else
			return getNodeName();
	}
	public int getParentID() {
		return getParentNodeId();
	}
	public void setChildList(ArrayList childList) {
		setChildNodeList(childList);
	}
	public void setNodeID(int nodeID) {		
		setNodeId(nodeID);
	}
	public void setNodeLabel(String nodeLabel) {
		setNodeName(nodeLabel);		
	}
	public void setParentID(int parentNodeID) {	
		setParentNodeId(parentNodeID);
	}
	/*******************************/
	@Override
	public String toString() 
	{	
		//return "nodeId:" + nodeId + ";nodeName:" + nodeName + ";nodeDisplayName:" + nodeDisplayName + ";folderName:" + folderName + ";" + ";publishFlag:" + publishFlag + ";nodeTypeIndi:" + nodeTypeIndi + ";requiredFlag:" + requiredFlag +  ";LockedNodeFlag:" + LokedNodeFlag + ";";
		return "nodeId:" + nodeId + ";nodeName:" + nodeName + ";nodeDisplayName:" + nodeDisplayName + ";folderName:" + folderName + ";publishFlag:" + publishFlag + ";nodeTypeIndi:" + nodeTypeIndi + ";requiredFlag:" + requiredFlag +  ";LockedNodeFlag:" + LokedNodeFlag + ";StatusIndi:" + statusIndi;
	}
	
	public ArrayList<DTOSTFStudyIdentifierMst> getSTFStudyDetails() {
		return STFStudyDetails;
	}
	public void setSTFStudyDetails(ArrayList<DTOSTFStudyIdentifierMst> studyDetails) {
		STFStudyDetails = studyDetails;
	}
	public int getNodeLevel() {
		return nodeLevel;
	}
	public void setNodeLevel(int nodeLevel) {
		this.nodeLevel = nodeLevel;
	}
	public boolean isPublishFlag() {
		return publishFlag;
	}
	public void setPublishFlag(boolean publishFlag) {
		this.publishFlag = publishFlag;
	}
	public boolean isLokedNodeFlag() {
		return LokedNodeFlag;
	}
	public void setLokedNodeFlag(boolean lokedNodeFlag) {
		LokedNodeFlag = lokedNodeFlag;
	}
	
	public char getParentFlag() {
		return parentFlag;
	}
	public void setParentFlag(char parentFlag) {
		this.parentFlag = parentFlag;
	}
	@Override
	public String getWsId() {
		return workspaceId;
	}
	@Override
	public void setWsId(String WsId) {
		this.workspaceId = workspaceId;		
	}
	@Override
	public char getStatusIndiForTree() {
		return statusIndiForTree;
	}
	@Override
	public void setStatusIndiForTree(char statusIndiForTree) {
		this.statusIndiForTree=statusIndiForTree;	
	}
	public String getURSNo() {
		return URSNo;
	}
	public void setURSNo(String uRSNo) {
		URSNo = uRSNo;
	}
	public String getURSDescription() {
		return URSDescription;
	}
	public void setURSDescription(String uRSDescription) {
		URSDescription = uRSDescription;
	}
	public String getFRSNo() {
		return FRSNo;
	}
	public void setFRSNo(String fRSNo) {
		FRSNo = fRSNo;
	}
	public String getFSDescription() {
		return FSDescription;
	}
	public void setFSDescription(String fSDescription) {
		FSDescription = fSDescription;
	}
	public String getIDNo() {
		return IDNo;
	}
	public void setIDNo(String iDNo) {
		IDNo = iDNo;
	}
	public HashMap<String, String> getMapAttrValue() {
		return mapAttrValue;
	}
	public void setMapAttrValue(HashMap<String, String> mapAttrValue) {
		this.mapAttrValue = mapAttrValue;
	}
	
}
