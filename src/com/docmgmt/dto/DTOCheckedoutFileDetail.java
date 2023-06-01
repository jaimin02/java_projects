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
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOCheckedoutFileDetail implements Serializable{

    String workSpaceId;                                
	int nodeId;                               
	int tranNo;                               
	int prevTranNo;                           
	String fileName;                                   
	int modifyBy;                        
	Timestamp modifyOn;           
	char isNodeLocked;                     
	char statusIndi;
	String workSpaceDesc;
	String baseWorkFolder;
	String userName;
	String projectName;
	String clientName;
	String locationName;
	String fileExt;
	String ISTDateTime;
	String ESTDateTime;
	
	
	
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
     * @return Returns the isNodeLocked.
     */
    public char getIsNodeLocked() {
        return isNodeLocked;
    }
    /**
     * @param isNodeLocked The isNodeLocked to set.
     */
    public void setIsNodeLocked(char isNodeLocked) {
        this.isNodeLocked = isNodeLocked;
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
     * @return Returns the prevTranNo.
     */
    public int getPrevTranNo() {
        return prevTranNo;
    }
    /**
     * @param prevTranNo The prevTranNo to set.
     */
    public void setPrevTranNo(int prevTranNo) {
        this.prevTranNo = prevTranNo;
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
     * @return Returns the tranNo.
     */
    public int getTranNo() {
        return tranNo;
    }
    /**
     * @param tranNo The tranNo to set.
     */
    public void setTranNo(int tranNo) {
        this.tranNo = tranNo;
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

	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}
	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
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
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
}
