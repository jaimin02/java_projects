/*
 * Created on Nov 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @auor Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOWorkSpaceNodeAttrDetail implements Serializable{

    String workspaceId;
    int nodeId;
    int attrId;
	String attrName;                                         
	String attrValue;                                         
	String validValues;                     
	char requiredFlag;                                         
	char editableFlag;                                         
	String attrForIndi;                          
	String remark;                     
	int modifyBy;                     
	Timestamp modifyOn;        
	char statusIndi;     
	String fileName;
	String folderName;
	String baseWorkFolder;
	int tranNo;
	String nodeDetailName;
	String workSpaceDesc;
	String locationCode;
	String deptCode;
	String nodeName;
	String nodeDisplayName;
	

	///
	
	int UserCode;
	String UserName;
	
	String profileValue;
	String profileSubType;
	String ISTDateTime;
	String ESTDateTime;
	String attr1;
	String attr2;
	String fileExt;
	
	
	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}
	
	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
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
	

	public int getUserCode() {
		return UserCode;
	}

	public void setUserCode(int userCode) {
		UserCode = userCode;
	}
	
	public String getProfileValue() {
		return profileValue;
	}
	public void setProfileValue(String profileValue) {
		this.profileValue = profileValue;
	}
	public String getProfileSubType() {
		return profileSubType;
	}
	public void setProfileSubType(String profileSubType) {
		this.profileSubType = profileSubType;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	
	///

	
	
	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}
	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}
	int pageNumber;
	int parentId; // Only used for add child nodes thru browser.
	
    /**
     * @return Returns the attrForIndi.
     */
    public String getAttrForIndi() {
        return attrForIndi;
    }
    /**
     * @param attrForIndi The attrForIndi to set.
     */
    public void setAttrForIndi(String attrForIndi) {
        this.attrForIndi = attrForIndi;
    }
    /**
     * @return Returns the attrId.
     */
    public int getAttrId() {
        return attrId;
    }
    /**
     * @param attrId The attrId to set.
     */
    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }
    /**
     * @return Returns the attrName.
     */
    public String getAttrName() {
        return attrName;
    }
    /**
     * @param attrName The attrName to set.
     */
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
    /**
     * @return Returns the attrValue.
     */
    public String getAttrValue() {
        return attrValue;
    }
    /**
     * @param attrValue The attrValue to set.
     */
    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
    /**
     * @return Returns the editableFlag.
     */
    public char getEditableFlag() {
        return editableFlag;
    }
    /**
     * @param editableFlag The editableFlag to set.
     */
    public void setEditableFlag(char editableFlag) {
        this.editableFlag = editableFlag;
    }
    /**
     * @return Returns the modifyBy.
     */
    public int getModifyBy() {
        return modifyBy;
    }
    /**
     * @param modifyBy The modifyBy to set.
     */
    public void setModifyBy(int modifyBy) {
        this.modifyBy = modifyBy;
    } 
    /**
     * @return Returns the nodeId.
     */
    public int getNodeId() {
        return nodeId;
    }
    /**
     * @param nodeId The nodeId to set.
     */
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
    /**
     * @return Returns the remark.
     */
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * @return Returns the requiredFlag.
     */
    public char getRequiredFlag() {
        return requiredFlag;
    }
    /**
     * @param requiredFlag The requiredFlag to set.
     */
    public void setRequiredFlag(char requiredFlag) {
        this.requiredFlag = requiredFlag;
    }
    /**
     * @return Returns the statusIndi.
     */
    public char getStatusIndi() {
        return statusIndi;
    }
    /**
     * @param statusIndi The statusIndi to set.
     */
    public void setStatusIndi(char statusIndi) {
        this.statusIndi = statusIndi;
    }
    /**
     * @return Returns the validValues.
     */
    public String getValidValues() {
        return validValues;
    }
    /**
     * @param validValues The validValues to set.
     */
    public void setValidValues(String validValues) {
        this.validValues = validValues;
    }
    /**
     * @return Returns the workspaceId.
     */
    public String getWorkspaceId() {
        return workspaceId;
    }
    /**
     * @param workspaceId The workspaceId to set.
     */
    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	public String getBaseWorkFolder() {
		return baseWorkFolder;
	}
	public void setBaseWorkFolder(String baseWorkFolder) {
		this.baseWorkFolder = baseWorkFolder;
	}
	
	public int getTranNo() {
		return tranNo;
	}
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
    public String getNodeDetailName() {
        return nodeDetailName;
    }
    public void setNodeDetailName(String nodeDetailName) {
        this.nodeDetailName = nodeDetailName;
    }
    public int getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
	/**
	 * @return Returns the parentId.
	 */
	public int getParentId() {
		return parentId;
	}
	/**
	 * @param parentId The parentId to set.
	 */
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getNodeName() {
		return nodeName;
	}
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
}
