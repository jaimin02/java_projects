package com.docmgmt.dto;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;
public class DTOWorkSpaceNodeHistory implements Serializable,ITreeNode{
	String workSpaceId;
	int nodeId;
	int tranNo;
	String fileName;
	String fileType;
	String folderName;
	char requiredFlag;
	String remark;
	String voidRemark;
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	char statusIndiForTree;
	String defaultFileFormat;
	int attriId;
	int stageId;
	String stageDesc;
	String attrName;
	String attrValue;
	String userName;
	String userTypeName;
	String baseWorkFolder;
	String fileExt;
	String sourceFolderForCopy;
	String workSpaceDesc;
	int nextStageId;
	String nodeName;
	String nextStageDesc;
	String nodeFolderName;
	/*Used in Node Submission History*/
	String fileVersionId;
	char lastClosed;
	String userDefineVersionId;
	String currentSeqNumber;
	String submissionId;

	String nodeDisplayName;
	String AttrForIndiId;
	int userCode;
	String stageHistory;
	int stagesIds;
	Vector attrHistory;
	
	File historyDocument;
	DTOFileSize historyDocumentSize; 
	ArrayList<DTOWorkSpaceUserRightsMst> wsUsrRightsList;
	
	String attrNodeName;
	
	boolean publishFlag;
	String ISTDateTime;
	String ESTDateTime;
	ArrayList<DTOWorkSpaceNodeHistory> nodeSignOffHistory;
	int completedStageId;
	char userFlag;
	int userGroupCode;
	String voidBy;
	Timestamp voidModifyOn;
	String voidISTDateTime;
	String voidESTDateTime;
	Timestamp dStartDate;
	String ISTStartDate;
	String ESTStartDate;
	Timestamp dEndDate;
	String ISTEndDate;
	String ESTEndDate;
	Timestamp dAdjustDate;
	String ISTAdjustDate;
	String ESTAdjustDate;
	int iHours;
	
	char projectType;
	String style;
	String filePath;
	String signId;
	String uuId;
	char clsUpload;
	char clsDownload;
	String loginName;
	Timestamp dUploadOn;
	Timestamp dDownloadOn;
	int docTranNo;
	
	String ISTUploadOn;
	String ESTUploadOn;
	
	String ISTDownloadOn;
	String ESTDownloadOn;
	String countryCode;
	String locationName;
	String roleCode;
	String roleName;
	double fileSize;
	int mode;
	String fileStatus;
	String coOrdinates;
	char isAdUser;
	int certifiedBy;
	Timestamp certifiedOn;
	String certifiedByName;
	String ISTCertifiedDate;
	String ESTCertifiedDate;
	int signTranNo;
	float nSize;
	float eSignfileSize;
	float totalFileSize;
	float sumofTotalSize;
	String RefFileName;
	String refFilePath;
	
		
	
	public String getRefFileName() {
		return RefFileName;
	}
	public void setRefFileName(String refFileName) {
		RefFileName = refFileName;
	}
	public String getRefFilePath() {
		return refFilePath;
	}
	public void setRefFilePath(String refFilePath) {
		this.refFilePath = refFilePath;
	}
	public float getnSize() {
		return nSize;
	}
	public void setnSize(float nSize) {
		this.nSize = nSize;
	}
	public float geteSignfileSize() {
		return eSignfileSize;
	}
	public void seteSignfileSize(float eSignfileSize) {
		this.eSignfileSize = eSignfileSize;
	}
	public float getTotalFileSize() {
		return totalFileSize;
	}
	public void setTotalFileSize(float totalFileSize) {
		this.totalFileSize = totalFileSize;
	}
	public float getSumofTotalSize() {
		return sumofTotalSize;
	}
	public void setSumofTotalSize(float sumofTotalSize) {
		this.sumofTotalSize = sumofTotalSize;
	}
	
	public int getSignTranNo() {
		return signTranNo;
	}
	public void setSignTranNo(int signTranNo) {
		this.signTranNo = signTranNo;
	}
	public String getISTCertifiedDate() {
		return ISTCertifiedDate;
	}
	public void setISTCertifiedDate(String iSTCertifiedDate) {
		ISTCertifiedDate = iSTCertifiedDate;
	}
	public String getESTCertifiedDate() {
		return ESTCertifiedDate;
	}
	public void setESTCertifiedDate(String eSTCertifiedDate) {
		ESTCertifiedDate = eSTCertifiedDate;
	}
	public String getCertifiedByName() {
		return certifiedByName;
	}
	public void setCertifiedByName(String certifiedByName) {
		this.certifiedByName = certifiedByName;
	}
	public int getCertifiedBy() {
		return certifiedBy;
	}
	public void setCertifiedBy(int certifiedBy) {
		this.certifiedBy = certifiedBy;
	}
	public Timestamp getCertifiedOn() {
		return certifiedOn;
	}
	public void setCertifiedOn(Timestamp certifiedOn) {
		this.certifiedOn = certifiedOn;
	}

	public String getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}	

	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public double getFileSize() {
		return fileSize;
	}
	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public char getClsUpload() {
		return clsUpload;
	}
	public void setClsUpload(char clsUpload) {
		this.clsUpload = clsUpload;
	}
	public char getClsDownload() {
		return clsDownload;
	}
	public void setClsDownload(char clsDownload) {
		this.clsDownload = clsDownload;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Timestamp getdUploadOn() {
		return dUploadOn;
	}
	public void setdUploadOn(Timestamp dUploadOn) {
		this.dUploadOn = dUploadOn;
	}
	public Timestamp getdDownloadOn() {
		return dDownloadOn;
	}
	public void setdDownloadOn(Timestamp dDownloadOn) {
		this.dDownloadOn = dDownloadOn;
	}
	public int getDocTranNo() {
		return docTranNo;
	}
	public void setDocTranNo(int docTranNo) {
		this.docTranNo = docTranNo;
	}
	public String getISTUploadOn() {
		return ISTUploadOn;
	}
	public void setISTUploadOn(String iSTUploadOn) {
		ISTUploadOn = iSTUploadOn;
	}
	public String getESTUploadOn() {
		return ESTUploadOn;
	}
	public void setESTUploadOn(String eSTUploadOn) {
		ESTUploadOn = eSTUploadOn;
	}
	public String getISTDownloadOn() {
		return ISTDownloadOn;
	}
	public void setISTDownloadOn(String iSTDownloadOn) {
		ISTDownloadOn = iSTDownloadOn;
	}
	public String getESTDownloadOn() {
		return ESTDownloadOn;
	}
	public void setESTDownloadOn(String eSTDownloadOn) {
		ESTDownloadOn = eSTDownloadOn;
	}
	public String getUuId() {
		return uuId;
	}
	public void setUuId(String uuId) {
		this.uuId = uuId;
	}
	public String getSignId() {
		return signId;
	}
	public void setSignId(String signId) {
		this.signId = signId;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public char getProjectType() {
		return projectType;
	}
	public void setProjectType(char projectType) {
		this.projectType = projectType;
	}
	
	public String getISTStartDate() {
		return ISTStartDate;
	}
	public void setISTStartDate(String iSTStartDate) {
		ISTStartDate = iSTStartDate;
	}
	public String getESTStartDate() {
		return ESTStartDate;
	}
	public void setESTStartDate(String eSTStartDate) {
		ESTStartDate = eSTStartDate;
	}
	public String getISTEndDate() {
		return ISTEndDate;
	}
	public void setISTEndDate(String iSTEndDate) {
		ISTEndDate = iSTEndDate;
	}
	public String getESTEndDate() {
		return ESTEndDate;
	}
	public void setESTEndDate(String eSTEndDate) {
		ESTEndDate = eSTEndDate;
	}
	public String getISTAdjustDate() {
		return ISTAdjustDate;
	}
	public void setISTAdjustDate(String iSTAdjustDate) {
		ISTAdjustDate = iSTAdjustDate;
	}
	public String getESTAdjustDate() {
		return ESTAdjustDate;
	}
	public void setESTAdjustDate(String eSTAdjustDate) {
		ESTAdjustDate = eSTAdjustDate;
	}
	public Timestamp getdStartDate() {
		return dStartDate;
	}
	public void setdStartDate(Timestamp dStartDate) {
		this.dStartDate = dStartDate;
	}
	
	public Timestamp getdEndDate() {
		return dEndDate;
	}
	public void setdEndDate(Timestamp dEndDate) {
		this.dEndDate = dEndDate;
	}

	public Timestamp getdAdjustDate() {
		return dAdjustDate;
	}
	public void setdAdjustDate(Timestamp dAdjustDate) {
		this.dAdjustDate = dAdjustDate;
	}

	public int getiHours() {
		return iHours;
	}
	public void setiHours(int iHours) {
		this.iHours = iHours;
	}
		
	
	public String getVoidISTDateTime() {
		return voidISTDateTime;
	}
	public void setVoidISTDateTime(String voidISTDateTime) {
		this.voidISTDateTime = voidISTDateTime;
	}
	public String getVoidESTDateTime() {
		return voidESTDateTime;
	}
	public void setVoidESTDateTime(String voidESTDateTime) {
		this.voidESTDateTime = voidESTDateTime;
	}
	public String getVoidBy() {
		return voidBy;
	}
	public void setVoidBy(String voidBy) {
		this.voidBy = voidBy;
	}
	public Timestamp getVoidModifyOn() {
		return voidModifyOn;
	}
	public void setVoidModifyOn(Timestamp voidModifyOn) {
		this.voidModifyOn = voidModifyOn;
	}
	public int getUserGroupCode() {
		return userGroupCode;
	}
	public void setUserGroupCode(int userGroupCode) {
		this.userGroupCode = userGroupCode;
	}
	public char getUserFlag() {
		return userFlag;
	}
	public void setUserFlag(char userFlag) {
		this.userFlag = userFlag;
	}	
	public int getCompletedStageId() {
		return completedStageId;
	}
	public void setCompletedStageId(int completedStageId) {
		this.completedStageId = completedStageId;
	}
	public String getStageHistory() {
		return stageHistory;
	}
	public void setStageHistory(String stageHistory) {
		this.stageHistory = stageHistory;
	}
	public int getStagesIds() {
		return stagesIds;
	}
	public void setStagesIds(int stagesIds) {
		this.stagesIds = stagesIds;
	}	
	public ArrayList<DTOWorkSpaceNodeHistory> getNodeSignOffHistory() {
		return nodeSignOffHistory;
	}
	public void setNodeSignOffHistory(ArrayList<DTOWorkSpaceNodeHistory> nodeSignOffHistory) {
		this.nodeSignOffHistory = nodeSignOffHistory;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public String getAttrForIndiId() {
		return AttrForIndiId;
	}
	public void setAttrForIndiId(String attrForIndiId) {
		AttrForIndiId = attrForIndiId;
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
	public DTOFileSize getHistoryDocumentSize() {
		return historyDocumentSize;
	}
	public void setHistoryDocumentSize(DTOFileSize historyDocumentSize) {
		this.historyDocumentSize = historyDocumentSize;
	}
	public File getHistoryDocument() {
		return historyDocument;
	}
	public void setHistoryDocument(File historyDocument) {
		this.historyDocument = historyDocument;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}
	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}
	public String getDefaultFileFormat() {
		return defaultFileFormat;
	}
	public void setDefaultFileFormat(String defaultFileFormat) {
		this.defaultFileFormat = defaultFileFormat;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
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
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
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
	public int getTranNo() {
		return tranNo;
	}
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public int getAttriId() {
		return attriId;
	}
	public void setAttriId(int attriId) {
		this.attriId = attriId;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserTypeName() {
		return userTypeName;
	}
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
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
	public String getSourceFolderForCopy() {
		return sourceFolderForCopy;
	}
	public void setSourceFolderForCopy(String sourceFolderForCopy) {
		this.sourceFolderForCopy = sourceFolderForCopy;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	public String getStageDesc() {
		return stageDesc;
	}
	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
	}
	public int getNextStageId() {
		return nextStageId;
	}
	public void setNextStageId(int nextStageId) {
		this.nextStageId = nextStageId;
	}
	public String getNextStageDesc() {
		return nextStageDesc;
	}
	public void setNextStageDesc(String nextStageDesc) {
		this.nextStageDesc = nextStageDesc;
	}
	public String getFileVersionId() {
		return fileVersionId;
	}
	public void setFileVersionId(String fileVersionId) {
		this.fileVersionId = fileVersionId;
	}
	public char getLastClosed() {
		return lastClosed;
	}
	public void setLastClosed(char lastClosed) {
		this.lastClosed = lastClosed;
	}
	public String getUserDefineVersionId() {
		return userDefineVersionId;
	}
	public void setUserDefineVersionId(String userDefineVersionId) {
		this.userDefineVersionId = userDefineVersionId;
	}
	public String getCurrentSeqNumber() {
		return currentSeqNumber;
	}
	public void setCurrentSeqNumber(String currentSeqNumber) {
		this.currentSeqNumber = currentSeqNumber;
	}
	public String getSubmissionId() {
		return submissionId;
	}
	public void setSubmissionId(String submissionId) {
		this.submissionId = submissionId;
	}
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
	public Vector getAttrHistory() {
		return attrHistory;
	}
	public void setAttrHistory(Vector attrHistory) {
		this.attrHistory = attrHistory;
	}
	
	/*********************************************/
	/*methods of interface TreeNode*/
	/*********************************************/
	public ArrayList getChildList() {
		return null;
	}
	public int getNodeID()
	{		
		return getNodeId();
	}
	public String getNodeLabel() {
		return null;
	}
	public int getParentID() {
		return 0;
	}
	public void setChildList(ArrayList<ITreeNode> childList) {
	}
	public void setNodeID(int nodeID) {
	}
	public void setNodeLabel(String nodeLabel) {
	}
	public void setParentID(int parentNodeID) {
	}
	/*********************************************/
	public String getNodeFolderName() {
		return nodeFolderName;
	}
	public void setNodeFolderName(String nodeFolderName) {
		this.nodeFolderName = nodeFolderName;
	}
	public ArrayList<DTOWorkSpaceUserRightsMst> getWsUsrRightsList() {
		return wsUsrRightsList;
	}
	public void setWsUsrRightsList(
			ArrayList<DTOWorkSpaceUserRightsMst> wsUsrRightsList) {
		this.wsUsrRightsList = wsUsrRightsList;
	}
	public String getAttrNodeName() {
		return attrNodeName;
	}
	public void setAttrNodeName(String attrNodeName) {
		this.attrNodeName = attrNodeName;
	}
	public boolean isPublishFlag() {
		return publishFlag;
	}
	public void setPublishFlag(boolean publishFlag) {
		this.publishFlag = publishFlag;
	}
	public String getCoOrdinates() {
		return coOrdinates;
	}
	public void setCoOrdinates(String coOrdinates) {
		this.coOrdinates = coOrdinates;
	}
	@Override
	public char getStatusIndiForTree() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getWsId() {
		return workSpaceId;
	}
	@Override
	public void setWsId(String WsId) {
		this.workSpaceId = workSpaceId;		
	}
	@Override
	public void setStatusIndiForTree(char statusIndiForTree) {
		this.statusIndiForTree=statusIndiForTree;
	}
	public char getIsAdUser() {
		return isAdUser;
	}
	public void setIsAdUser(char isAdUser) {
		this.isAdUser = isAdUser;
	}
	public String getVoidRemark() {
		return voidRemark;
	}
	public void setVoidRemark(String voidRemark) {
		this.voidRemark = voidRemark;
	}
}
