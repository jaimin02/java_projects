/*
 * Created on Dec 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOLabelWorkSpaceNodeDetail implements Serializable {

	String workspaceId;
	String oldNodeDisplayName;
	String labelId;
	String attrValue;
	int nodeId;
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
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
}
