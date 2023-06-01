package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOTimeZoneMst implements Serializable{
	
	 String timeZoneMstCode;
	 String timeZoneName;
	 String timeZoneCode;
	 String locationName;
	 String remark;
	 int modifyBy;
	 Timestamp modifyOn;
	 char statusIndi;
	 
	 
	public String getTimeZoneMstCode() {
		return timeZoneMstCode;
	}
	public void setTimeZoneMstCode(String timeZoneMstCode) {
		this.timeZoneMstCode = timeZoneMstCode;
	}
	
	public String getTimeZoneName() {
		return timeZoneName;
	}
	public void setTimeZoneName(String timeZoneName) {
		this.timeZoneName = timeZoneName;
	}
	
	public String getTimeZoneCode() {
		return timeZoneCode;
	}
	public void setTimeZoneCode(String timeZoneCode) {
		this.timeZoneCode = timeZoneCode;
	}
	
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
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
