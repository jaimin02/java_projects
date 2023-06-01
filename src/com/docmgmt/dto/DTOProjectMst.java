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
public class DTOProjectMst implements Serializable{

    String projectCode;                                  
	String projectName;                                           
	String remark;                            
	int modifyBy;                               
	Timestamp modifyOn;             
	char statusIndi; 
	
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
     * @return Returns the projectName.
     */
    public String getProjectName() {
        return projectName;
    }
    /**
     * @param projectName The projectName to set.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
