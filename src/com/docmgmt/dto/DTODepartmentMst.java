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
public class DTODepartmentMst implements Serializable{

    private String deptCode;                                   
	private String deptName;                                            
	private String remark;                          
	private int modifyBy;                             
	private Timestamp modifyOn;           
	private char statusIndi;                          
	private String ISTDateTime;
	private String ESTDateTime;
	private String userName;
	
	
    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
     * @return Returns the deptName.
     */
    public String getDeptName() {
        return deptName;
    }
    /**
     * @param deptName The deptName to set.
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
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
     * @return Returns the modifyOn.
     */
    public Timestamp getModifyOn() {
        return modifyOn;
    }
    /**
     * @param modifyOn The modifyOn to set.
     */
    public void setModifyOn(Timestamp modifyOn) {
        this.modifyOn = modifyOn;
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
}
