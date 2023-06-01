/*
 * Created on May 23, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOWorkSpaceNodeAttribute implements Serializable{

    String workspaceId;
    int nodeId;
    int attrId;
	String attrName;                                         
	String attrValue;                                         
	int modifyBy;                     
	Timestamp modifyOn;        
	char statusIndi; 
	String attrType;
	String attrMatrixValue;
	String attrMatrixDisplayValue;
	String attrForIndiId;
	int TranNo;
	char requiredFlag;
	char editableFlag;
	String remark;
	String ISTDateTime;
	String ESTDateTime;
	String userName;
	String fileName;
	String folderName;
	String baseWorkFolder;

	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getbaseWorkFolder() {
		return baseWorkFolder;
	}
	public void setbaseWorkFolder(String baseWorkFolder) {
		this.baseWorkFolder = baseWorkFolder;
	}
	
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	
	public int getTranNo() {
		return TranNo;
	}
	public void setTranNo(int tranNo) {
		TranNo = tranNo;
	}
	public char getRequiredFlag() {
		return requiredFlag;
	}
	public void setRequiredFlag(char requiredFlag) {
		this.requiredFlag = requiredFlag;
	}
	public char getEditableFlag() {
		return editableFlag;
	}
	public void setEditableFlag(char editableFlag) {
		this.editableFlag = editableFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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


	
	public String getAttrForIndiId() {
		return attrForIndiId;
	}
	public void setAttrForIndiId(String attrForIndiId) {
		this.attrForIndiId = attrForIndiId;
	}
	public String getAttrType() {
		return attrType;
	}
	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}
	public int getAttrId() {
		return attrId;
	}
	public void setAttrId(int attrId) {
		this.attrId = attrId;
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
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public String getAttrMatrixValue() {
		return attrMatrixValue;
	}
	public void setAttrMatrixValue(String attrMatrixValue) {
		this.attrMatrixValue = attrMatrixValue;
	}
	public String getAttrMatrixDisplayValue() {
		return attrMatrixDisplayValue;
	}
	public void setAttrMatrixDisplayValue(String attrMatrixDisplayValue) {
		this.attrMatrixDisplayValue = attrMatrixDisplayValue;
	}
}
