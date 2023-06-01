package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOUserDocStageComments implements Serializable {
	private static final long serialVersionUID = 1L;
	
	String workspaceId;
	int nodeId;
	int stageId;
	int tranNo;
	int userCode;
	String userDefineVersionId;
	String userName;
	String userComments;
	String userRefDocPath;
	String userRefDocName;
	String remarks;
	int modifyBy;
	Timestamp ModifyOn;
	char statusIndi;
	String fileName;
	String fileType;
	String folderName;
	String baseWorkFolder;
	String basePublishFolder;
	
	
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	public int getTranNo() {
		return tranNo;
	}
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public String getUserDefineVersionId() {
		return userDefineVersionId;
	}
	public void setUserDefineVersionId(String userDefineVersionId) {
		this.userDefineVersionId = userDefineVersionId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserComments() {
		return userComments;
	}
	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}
	public String getUserRefDocPath() {
		return userRefDocPath;
	}
	public void setUserRefDocPath(String userRefDocPath) {
		this.userRefDocPath = userRefDocPath;
	}
	public String getUserRefDocName() {
		return userRefDocName;
	}
	public void setUserRefDocName(String userRefDocName) {
		this.userRefDocName = userRefDocName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Timestamp getModifyOn() {
		return ModifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		ModifyOn = modifyOn;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public String getFileName() {
		return fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getBaseWorkFolder() {
		return baseWorkFolder;
	}
	public String getBasePublishFolder() {
		return basePublishFolder;
	}
	public void setBaseWorkFolder(String baseWorkFolder) {
		this.baseWorkFolder = baseWorkFolder;
	}
	public void setBasePublishFolder(String basePublishFolder) {
		this.basePublishFolder = basePublishFolder;
	}
}