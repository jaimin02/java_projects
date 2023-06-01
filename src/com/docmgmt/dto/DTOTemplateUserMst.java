/*
 * Created on Nov 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOTemplateUserMst implements Serializable{

    String templateId;
    String userGroupName;
	int userGroupCode;                          
	int userCode;                          
	char adminFlag;                                         
	Timestamp lastAccessedOn;                     
	String remark;                     
	int modifyBy;                     
	Timestamp modifyOn;        
	char statusIndi;
	String username;
	
	String templateDesc;
	
	Date fromDt;
	Date toDt;
	
	/**
     * @return Returns the adminFlag.
     */
    public char getAdminFlag() {
        return adminFlag;
    }
    /**
     * @param adminFlag The adminFlag to set.
     */
    public void setAdminFlag(char adminFlag) {
        this.adminFlag = adminFlag;
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
     * @return Returns the userCode.
     */
    public int getUserCode() {
        return userCode;
    }
    /**
     * @param userCode The userCode to set.
     */
    public void setUserCode(int userCode) {
        this.userCode = userCode;
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
    
    public String getUsername() {
        return username;
    }
    /**
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
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
     * @return Returns the usergroupname.
     */
   
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
	public Date getFromDt() {
		return fromDt;
	}
	public void setFromDt(Date fromDt) {
		this.fromDt = fromDt;
	}
	public Date getToDt() {
		return toDt;
	}
	public void setToDt(Date toDt) {
		this.toDt = toDt;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTemplateDesc() {
		return templateDesc;
	}
	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}
}
