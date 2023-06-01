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

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOUserGroupMst implements Serializable{

	private int userGroupCode;                          
	private String userGroupName;                                         
	private String locationCode;                                         
	private String deptCode;                                         
	private String clientCode;                                         
	private String projectCode;                                         
	private String docTypeCode;                                         
	private String userGroupType;                                         
	private String remark;                     
	private int modifyBy;                     
	private Timestamp modifyOn;        
	private char statusIndi;
	private String locationName;
	private String DeptName;
	private String clientName;
	private String userTypeCode;
	private String projectName;
	
	private String userTypeName;
	private ArrayList<DTOUserMst> users;
	
	public String getUserTypeName() {
		return userTypeName;
	}
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getDeptName() {
		return DeptName;
	}
	public void setDeptName(String deptName) {
		DeptName = deptName;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
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
     * @return Returns the userGroupCode.
     */
    public int getUserGroupCode() {
        return userGroupCode;
    }
    /**
     * @param userGroupCode The userGroupCode to set.
     */
    public void setUserGroupCode(int userGroupCode) {
        this.userGroupCode = userGroupCode;
    }
    /**
     * @return Returns the userGroupName.
     */
    public String getUserGroupName() {
        return userGroupName;
    }
    /**
     * @param userGroupName The userGroupName to set.
     */
    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }
    /**
     * @return Returns the userGroupType.
     */
    public String getUserGroupType() {
        return userGroupType;
    }
    /**
     * @param userGroupType The userGroupType to set.
     */
    public void setUserGroupType(String userGroupType) {
        this.userGroupType = userGroupType;
    }
	
	public String getUserTypeCode() {
		return userTypeCode;
	}
	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}
	
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public void setUsers(ArrayList<DTOUserMst> users) {
		this.users = users;
	}
	public ArrayList<DTOUserMst> getUsers() {
		return users;
	}
}
