package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTODefaultWorkSpaceMst implements Serializable{
	
	int userCode;
	String workSpaceID;
	String remark;         
	int modifyBy;       
	Timestamp modifyOn;  
	char statusIndi;

	public DTODefaultWorkSpaceMst()
	{
		
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public String getWorkSpaceID() {
		return workSpaceID;
	}

	public void setWorkSpaceID(String workSpaceID) {
		this.workSpaceID = workSpaceID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Timestamp getModifyOn() {
		return modifyOn;
	}

	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}

	public char getStatusIndi() {
		return statusIndi;
	}

	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	
	
}
