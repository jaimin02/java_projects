/*
 * Created on Apr 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTORoleOperationMatrix implements Serializable{
	char activeFlag;
	int modifyBy;
	Date modifyOn;
	char statusIndi;
	String userTypeCode;
	String operationCode;

	String code;
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return Returns the code.
	 */
	
	/**
	 * @return Returns the activeFlag.
	 */
	public char getActiveFlag() {
		return activeFlag;
	}
	/**
	 * @param activeFlag The activeFlag to set.
	 */
	public void setActiveFlag(char activeFlag) {
		this.activeFlag = activeFlag;
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
	public Date getModifyOn() {
		return modifyOn;
	}
	/**
	 * @param modifyOn The modifyOn to set.
	 */
	public void setModifyOn(Date modifyOn) {
		this.modifyOn = modifyOn;
	}
	/**
	 * @return Returns the operationCode.
	 */
	public String getOperationCode() {
		return operationCode;
	}
	/**
	 * @param operationCode The operationCode to set.
	 */
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
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
}
