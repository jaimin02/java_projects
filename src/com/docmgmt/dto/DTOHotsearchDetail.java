
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOHotsearchDetail implements Serializable{

	String workspaceId;
	String workspaceDesc;
	int nodeId;
	int tranNo;
	String baseWorkFolder ;
	String locationName;
	String projectName;
	String fileName;
	String locationId;
	String loginName;
	String clientName;
	String hotSearchId;              
	String hotSearchDesc;         
	String projectId;         	         
	String clientId;                  
	int lastModifiedBy;         
	String attrName;         
	String attrValue;         
	String relation;         
	int modifyBy;         
	Timestamp modifyOn;         
	char statusIndi;   
	String userCode;
	String userName;
	int limit;
	String nodeDisplayName;
	String nodeName;
	String folderName;
	String fileFolderName;
	String attrforindiid;
	String fileversionid;
	
	public String getFileversionid() {
		return fileversionid;
	}
	public void setFileversionid(String fileversionid) {
		this.fileversionid = fileversionid;
	}
	public String getAttrforindiid() {
		return attrforindiid;
	}
	public void setAttrforindiid(String attrforindiid) {
		this.attrforindiid = attrforindiid;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getFileFolderName() {
		return fileFolderName;
	}
	public void setFileFolderName(String fileFolderName) {
		this.fileFolderName = fileFolderName;
	}
    public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
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
     * @return Returns the clientId.
     */
    public String getClientId() {
        return clientId;
    }
    /**
     * @param clientId The clientId to set.
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    /**
     * @return Returns the hotSearchDesc.
     */
    public String getHotSearchDesc() {
        return hotSearchDesc;
    }
    /**
     * @param hotSearchDesc The hotSearchDesc to set.
     */
    public void setHotSearchDesc(String hotSearchDesc) {
        this.hotSearchDesc = hotSearchDesc;
    }
    /**
     * @return Returns the hotSearchId.
     */
    public String getHotSearchId() {
        return hotSearchId;
    }
    /**
     * @param hotSearchId The hotSearchId to set.
     */
    public void setHotSearchId(String hotSearchId) {
        this.hotSearchId = hotSearchId;
    }
    /**
     * @return Returns the lastModifiedBy.
     */
    public int getLastModifiedBy() {
        return lastModifiedBy;
    }
    /**
     * @param lastModifiedBy The lastModifiedBy to set.
     */
    public void setLastModifiedBy(int lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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
     * @return Returns the projectId.
     */
    public String getProjectId() {
        return projectId;
    }
    /**
     * @param projectId The projectId to set.
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    
    public String getRelation() {
        return relation;
    }
    /**
     * @param relation The relation to set.
     */
    public void setRelation(String relation) {
        this.relation = relation;
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
	public String getBaseWorkFolder() {
		return baseWorkFolder;
	}
	public void setBaseWorkFolder(String baseWorkFolder) {
		this.baseWorkFolder = baseWorkFolder;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
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
	public String getWorkspaceDesc() {
		return workspaceDesc;
	}
	public void setWorkspaceDesc(String workspaceDesc) {
		this.workspaceDesc = workspaceDesc;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
}
