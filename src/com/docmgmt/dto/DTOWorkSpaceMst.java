/*
 * Created on Nov 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdError;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOWorkSpaceMst implements Serializable{

	String workSpaceId;
	String workSpaceDesc;
	String locationCode;
	String locationName;
	String deptCode;
	String deptName;
	String clientCode;
	String clientName;
	String projectCode;
	String projectName;
	String docTypeCode;
	String templateId;
	String templateDesc;
	String baseWorkFolder;
	String basePublishFolder;
	int lastTranNo;
	String lastPublishedVersion;
	int createdBy;
	Timestamp createdOn;             
	int lastAccessedBy;     
	Timestamp lastAccessedOn;
	char projectType;
	String remark;                
	int modifyBy;              
	Timestamp modifyOn;              
	char statusIndi;
	String lastaccessedUserName;
	String lastModifyUserName;
	Timestamp toDate;
	Timestamp fromDate;
	int defaultAdminCode;
	String nodeDisplayName;
	String folderName;
	String fileName;
	String userDefinedVersionId;
	String fileVersionId;
	 String nodeStatusAttributeValue;
	 String workSpaceNodeAttrHistoryAttributeValue;
	 String attributeName;
	 String loginName;
	 int attributeId;
	long fileSizeBytes;
	 double fileSizeKB;
	double fileSizeMB;
	int nodeId;
	String attrValue;
	int maxTranNo;
	char LockSeq;
	int tranNo;
	String userName;
	String ISTDateTime;
	String ESTDateTime;
	String roleCode;
	String projectCodeName;
	public String isEditProject;
	String verion;
	String applicationCode;
	String applicationName;
	
	public String getIsEditProject() {
		return isEditProject;
	}
	public void setIsEditProject(String isEditProject) {
		this.isEditProject = isEditProject;
	}
	
			
	public String getProjectCodeName() {
		return projectCodeName;
	}
	public void setProjectCodeName(String projectCodeName) {
		this.projectCodeName = projectCodeName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
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
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getTranNo() {
		return tranNo;
	}
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
	DTOWorkSpaceNodeDetail rootNodeDtl;
	DTOSubmissionMst submissionMst;
	ArrayList<EctdError> ectdErrorList;
	
	String dossierStatus;
	boolean isdossierStatus;
	public ArrayList<EctdError> getEctdErrorList() {
		return ectdErrorList;
	}
	public void setEctdErrorList(ArrayList<EctdError> ectdErrorList) {
		this.ectdErrorList = ectdErrorList;
	}
	public DTOSubmissionMst getSubmissionMst() {
		return submissionMst;
	}
	public void setSubmissionMst(DTOSubmissionMst submissionMst) {
		this.submissionMst = submissionMst;
	}
	public DTOWorkSpaceNodeDetail getRootNodeDtl() {
		return rootNodeDtl;
	}
	public void setRootNodeDtl(DTOWorkSpaceNodeDetail rootNodeDtl) {
		this.rootNodeDtl = rootNodeDtl;
	}
	/**
	 * @return Returns the maxTranNo.
	 */
	public int getMaxTranNo() {
		return maxTranNo;
	}
	/**
	 * @param maxTranNo The maxTranNo to set.
	 */
	public void setMaxTranNo(int maxTranNo) {
		this.maxTranNo = maxTranNo;
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
	 * @return Returns the workSpaceNodeAttrHistoryAttributeValue.
	 */
	public String getWorkSpaceNodeAttrHistoryAttributeValue() {
		return workSpaceNodeAttrHistoryAttributeValue;
	}
	/**
	 * @param workSpaceNodeAttrHistoryAttributeValue The workSpaceNodeAttrHistoryAttributeValue to set.
	 */
	public void setWorkSpaceNodeAttrHistoryAttributeValue(
			String workSpaceNodeAttrHistoryAttributeValue) {
		this.workSpaceNodeAttrHistoryAttributeValue = workSpaceNodeAttrHistoryAttributeValue;
	}
	/**
	 * @return Returns the attributeName.
	 */
	public String getAttributeName() {
		return attributeName;
	}
	/**
	 * @param attributeName The attributeName to set.
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	 
	/**
	 * @return Returns the loginName.
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName The loginName to set.
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	/**
	 * @return Returns the nodeStatusAttributeValue.
	 */
	public String getNodeStatusAttributeValue() {
		return nodeStatusAttributeValue;
	}
	/**
	 * @param nodeStatusAttributeValue The nodeStatusAttributeValue to set.
	 */
	public void setNodeStatusAttributeValue(String nodeStatusAttributeValue) {
		this.nodeStatusAttributeValue = nodeStatusAttributeValue;
	}
	/**
	 * @return Returns the fileVersionId.
	 */
	public String getFileVersionId() {
		return fileVersionId;
	}
	/**
	 * @param fileVersionId The fileVersionId to set.
	 */
	public void setFileVersionId(String fileVersionId) {
		this.fileVersionId = fileVersionId;
	}
	/**
	 * @return Returns the userDefinedVersionId.
	 */
	public String getUserDefinedVersionId() {
		return userDefinedVersionId;
	}
	/**
	 * @param userDefinedVersionId The userDefinedVersionId to set.
	 */
	public void setUserDefinedVersionId(String userDefinedVersionId) {
		this.userDefinedVersionId = userDefinedVersionId;
	}
	public String getFolderName() {
		return folderName;
	}
	/**
	 * @param folderName The folderName to set.
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public Timestamp getFromDate() {
		return fromDate;
	}
	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}
	public Timestamp getToDate() {
		return toDate;
	}
	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}
    public String getBasePublishFolder() {
        return basePublishFolder;
    }
    /**
     * @param basePublishFolder The basePublishFolder to set.
     */
    public void setBasePublishFolder(String basePublishFolder) {
        this.basePublishFolder = basePublishFolder;
    }
    /**
     * @return Returns the baseWorkFolder.
     */
    public String getBaseWorkFolder() {
        return baseWorkFolder;
    }
    /**
     * @param baseWorkFolder The baseWorkFolder to set.
     */
    public void setBaseWorkFolder(String baseWorkFolder) {
        this.baseWorkFolder = baseWorkFolder;
    }
    /**
     * @return Returns the clientCode.
     */
    public String getClientCode() {
        return clientCode;
    }
    /**
     * @param clientCode The clientCode to set.
     */
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }
    /**
     * @return Returns the createdBy.
     */
    public int getCreatedBy() {
        return createdBy;
    }
    /**
     * @param createdBy The createdBy to set.
     */
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }    
    /**
     * @return Returns the deptCode.
     */
    public String getDeptCode() {
        return deptCode;
    }
    /**
     * @param deptCode The deptCode to set.
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
    /**
     * @return Returns the docTypeCode.
     */
    public String getDocTypeCode() {
        return docTypeCode;
    }
    /**
     * @param docTypeCode The docTypeCode to set.
     */
    public void setDocTypeCode(String docTypeCode) {
        this.docTypeCode = docTypeCode;
    }
    /**
     * @return Returns the lastAccessedBy.
     */
    public int getLastAccessedBy() {
        return lastAccessedBy;
    }
    /**
     * @param lastAccessedBy The lastAccessedBy to set.
     */
    public void setLastAccessedBy(int lastAccessedBy) {
        this.lastAccessedBy = lastAccessedBy;
    }
    /**
     * @return Returns the lastPublishedVersion.
     */
    public String getLastPublishedVersion() {
        return lastPublishedVersion;
    }
    /**
     * @param lastPublishedVersion The lastPublishedVersion to set.
     */
    public void setLastPublishedVersion(String lastPublishedVersion) {
        this.lastPublishedVersion = lastPublishedVersion;
    }
    /**
     * @return Returns the lastTranNo.
     */
    public int getLastTranNo() {
        return lastTranNo;
    }
    /**
     * @param lastTranNo The lastTranNo to set.
     */
    public void setLastTranNo(int lastTranNo) {
        this.lastTranNo = lastTranNo;
    }
    /**
     * @return Returns the locationCode.
     */
    public String getLocationCode() {
        return locationCode;
    }
    /**
     * @param locationCode The locationCode to set.
     */
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
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
     * @return Returns the projectCode.
     */
    public String getProjectCode() {
        return projectCode;
    }
    /**
     * @param projectCode The projectCode to set.
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
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
     * @return Returns the templateDesc.
     */
    public String getTemplateDesc() {
        return templateDesc;
    }
    /**
     * @param templateDesc The templateDesc to set.
     */
    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }
    /**
     * @return Returns the templateId.
     */
    public String getTemplateId() {
        return templateId;
    }
    /**
     * @param templateId The templateId to set.
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
    /**
     * @return Returns the workSpaceDesc.
     */
    public String getWorkSpaceDesc() {
        return workSpaceDesc;
    }
    /**
     * @param workSpaceDesc The workSpaceDesc to set.
     */
    public void setWorkSpaceDesc(String workSpaceDesc) {
        this.workSpaceDesc = workSpaceDesc;
    }
    /**
     * @return Returns the workSpaceId.
     */
    public String getWorkSpaceId() {
        return workSpaceId;
    }
    /**
     * @param workSpaceId The workSpaceId to set.
     */
    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }
	public char getLockSeq() {
		return LockSeq;
	}
	public void setLockSeq(char lockSeq) {
		LockSeq = lockSeq;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getLastaccessedUserName() {
		return lastaccessedUserName;
	}
	public void setLastaccessedUserName(String lastaccessedUserName) {
		this.lastaccessedUserName = lastaccessedUserName;
	}
	public String getLastModifyUserName() {
		return lastModifyUserName;
	}
	public void setLastModifyUserName(String lastModifyUserName) {
		this.lastModifyUserName = lastModifyUserName;
	}
	public char getProjectType() {
		return projectType;
	}
	public void setProjectType(char projectType) {
		this.projectType = projectType;
	}
	/**
	 * @return Returns the defaultAdminCode.
	 */
	public int getDefaultAdminCode() {
		return defaultAdminCode;
	} 
	/**
	 * @param defaultAdminCode The defaultAdminCode to set.
	 */
	public void setDefaultAdminCode(int defaultAdminCode) {
		this.defaultAdminCode = defaultAdminCode;
	}
	
	/**
	 * @return Returns the nodeDisplayName.
	 */
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	/**
	 * @param nodeDisplayName The nodeDisplayName to set.
	 */
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return Returns the attributeId.
	 */
	public int getAttributeId() {
		return attributeId;
	}
	/**
	 * @param attributeId The attributeId to set.
	 */
	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}
	
	/**
	 * @return Returns the fileSizeBytes.
	 */
	public long getFileSizeBytes() {
		return fileSizeBytes;
	}
	/**
	 * @param fileSizeBytes The fileSizeBytes to set.
	 */
	public void setFileSizeBytes(long fileSizeBytes) {
		this.fileSizeBytes = fileSizeBytes;
	}
	/**
	 * @return Returns the fileSizeKB.
	 */
	public double getFileSizeKB() {
		return fileSizeKB;
	}
	/**
	 * @param fileSizeKB The fileSizeKB to set.
	 */
	public void setFileSizeKB(double fileSizeKB) {
		this.fileSizeKB = fileSizeKB;
	}
	/**
	 * @return Returns the fileSizeMB.
	 */
	public double getFileSizeMB() {
		return fileSizeMB;
	}
	/**
	 * @param fileSizeMB The fileSizeMB to set.
	 */
	public void setFileSizeMB(double fileSizeMB) {
		this.fileSizeMB = fileSizeMB;
	}
	public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	public Timestamp getLastAccessedOn() {
		return lastAccessedOn;
	}
	public void setLastAccessedOn(Timestamp lastAccessedOn) {
		this.lastAccessedOn = lastAccessedOn;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public String getDossierStatus() {
		return dossierStatus;
	}
	public void setDossierStatus(String dossierStatus) {
		this.dossierStatus = dossierStatus;
	}
	public boolean isIsdossierStatus() {
		return isdossierStatus;
	}
	public void setIsdossierStatus(boolean isdossierStatus) {
		this.isdossierStatus = isdossierStatus;
	}
	public String getVerion() {
		return verion;
	}
	public void setVerion(String verion) {
		this.verion = verion;
	}
	public String getApplicationCode() {
		return applicationCode;
	}
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
}
