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
public class DTODocTypeMst implements Serializable{

    String docTypeCode;                                
	String docTypeName;                                         
	String remark;                     
	int modifyBy;                          
	Timestamp modifyOn;       
	char statusIndi;
	
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
     * @return Returns the docTypeName.
     */
    public String getDocTypeName() {
        return docTypeName;
    }
    /**
     * @param docTypeName The docTypeName to set.
     */
    public void setDocTypeName(String docTypeName) {
        this.docTypeName = docTypeName;
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
}
