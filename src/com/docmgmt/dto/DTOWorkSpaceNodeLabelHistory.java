/*
 * Created on Dec 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Riddhi
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOWorkSpaceNodeLabelHistory implements Serializable{
	
	String workspaceLabelId;
	String fileLocation;
	String workSpaceId;
	int nodeId;
	int tranNo;
	String fileName;
	String fileType;
	String folderName;
	char requiredFlag;
	String remark;
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	String defaultFileFormat;
	int attriId;
	String attrName;
	String attrValue;
	String userName;
	String baseWorkFolder;
	String fileExt;
	String sourceFolderForCopy;

	
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
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public String getWorkspaceLabelId() {
		return workspaceLabelId;
	}
	public void setWorkspaceLabelId(String workspaceLabelId) {
		this.workspaceLabelId = workspaceLabelId;
	}
}
