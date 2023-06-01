package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DTOEmployeeMst implements Serializable {
	private static final long serialVersionUID = 1L;
	
	int empNo;
	String empCode;
	String empName;
	String deptCode;
	Timestamp jOD;
	String remark;
	Timestamp modifyOn;
	int modifyBy;
	char statusIndi;
	String deptName;	
	String emailId;
	String mobileNo;
	String modifyByUser;
	ArrayList<DTOTrainingRecordDetails> tRDtl;
	
	public int getEmpNo() {
		return empNo;
	}
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public Timestamp getjOD() {
		return jOD;
	}
	public void setjOD(Timestamp jOD) {
		this.jOD = jOD;
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
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getModifyByUser() {
		return modifyByUser;
	}
	public void setModifyByUser(String modifyByUser) {
		this.modifyByUser = modifyByUser;
	}
	public ArrayList<DTOTrainingRecordDetails> gettRDtl() {
		return tRDtl;
	}
	public void settRDtl(ArrayList<DTOTrainingRecordDetails> tRDtl) {
		this.tRDtl = tRDtl;
	}
}
