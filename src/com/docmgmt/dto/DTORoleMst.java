
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTORoleMst implements Serializable{

	String roleCode;	       
	String roleName;
	String remark;
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	String ISTDateTime;
	String ESTDateTime;
	String userName;
	
	
    /**
     * @return Returns the locationCode.
     */

    /**
     * @return Returns the modifyBy.
     */
    public int getModifyBy() {
        return modifyBy;
    }
    public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
     * @return Returns the statusInd.
     */
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	/**
	 * @return the modifyOn
	 */
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	/**
	 * @param modifyOn the modifyOn to set
	 */
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
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

}
