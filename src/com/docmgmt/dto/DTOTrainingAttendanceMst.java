package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOTrainingAttendanceMst implements Serializable {
	private static final long serialVersionUID = 540803832799541324L;

	int trAttNo;
	int trainingScheduleNo;
	int empNo;
	char isTraner;
	char isPresent;
	String remark;
	Timestamp modifyOn;
	int modifyBy;
	String modifyByUser;
	char statusIndi;
	DTOEmployeeMst dtoEmpMst = new DTOEmployeeMst();

	
	public int getTrAttNo() {
		return trAttNo;
	}
	public void setTrAttNo(int trAttNo) {
		this.trAttNo = trAttNo;
	}
	public int getTrainingScheduleNo() {
		return trainingScheduleNo;
	}
	public void setTrainingScheduleNo(int trainingScheduleNo) {
		this.trainingScheduleNo = trainingScheduleNo;
	}
	public int getEmpNo() {
		return empNo;
	}
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	public char getIsTraner() {
		return isTraner;
	}
	public void setIsTraner(char isTraner) {
		this.isTraner = isTraner;
	}
	public char getIsPresent() {
		return isPresent;
	}
	public void setIsPresent(char isPresent) {
		this.isPresent = isPresent;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public String getModifyByUser() {
		return modifyByUser;
	}
	public void setModifyByUser(String modifyByUser) {
		this.modifyByUser = modifyByUser;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public DTOEmployeeMst getDtoEmpMst() {
		return dtoEmpMst;
	}
	public void setDtoEmpMst(DTOEmployeeMst dtoEmpMst) {
		this.dtoEmpMst = dtoEmpMst;
	}
}