/*
 * Created on Apr 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.docmgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Ravi Pandya
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOOperationMst implements Serializable{
	
	String operationCode;
	String operationName;
	String operationPath;
	String parentOperationCode;
	int modifyBy;
    Date modifyOn;
	char statusIndi;
	char activeFlag;
	String usertype;
	ArrayList<DTOOperationMst> childOperations;
	
	
	public ArrayList<DTOOperationMst> getChildOperations() {
		return childOperations;
	}
	public void setChildOperations(ArrayList<DTOOperationMst> childOperations) {
		this.childOperations = childOperations;
	}
	
	
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getOperationPath() {
		return operationPath;
	}
	public void setOperationPath(String operationPath) {
		this.operationPath = operationPath;
	}
	public String getParentOperationCode() {
		return parentOperationCode;
	}
	public void setParentOperationCode(String parentOperationCode) {
		this.parentOperationCode = parentOperationCode;
	}
	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Date modifyOn) {
		this.modifyOn = modifyOn;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public char getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(char activeFlag) {
		this.activeFlag = activeFlag;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
}
