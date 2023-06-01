package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class DTOUserTypeMst implements Serializable {
	
	String userTypeCode;
	String userTypeName;
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
	 * @return Returns the userTypeCode.
	 */
	public String getUserTypeCode() {
		return userTypeCode;
	}
	/**
	 * @param userTypeCode The userTypeCode to set.
	 */
	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}
	/**
	 * @return Returns the userTypeName.
	 */
	public String getUserTypeName() {
		return userTypeName;
	}
	/**
	 * @param userTypeName The userTypeName to set.
	 */
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
}
