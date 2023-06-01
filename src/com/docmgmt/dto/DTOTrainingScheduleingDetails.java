package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DTOTrainingScheduleingDetails implements Serializable {
	private static final long serialVersionUID = 42977568210922324L;
	
	int trainingScheduleNo;
	int trainingRecordNo;
	String trainingId;
	String trainingHdr;
	String trainingDtl;
	String trainingScheduleDesc;
	Timestamp trainingStartDate;
	Time trainingStartTime;
	Timestamp trainingEndDate;
	Time trainingEndTime;
	String trainingRefDocPath;
	int trainingRefNodeId;
	int trainingRefTranNo;
	int totalTrainingDuration;
	String refWorkspaceId;
	String refWorkspaceDesc;
	String remark;
	Timestamp modifyOn;
	int modifyBy;
	char statusIndi;
	String modifyByUser;
	Time diffTime;
	ArrayList<DTOTrainingAttendanceMst> trainingAttendanceMst;
	
	public int getTrainingScheduleNo() {
		return trainingScheduleNo;
	}
	public void setTrainingScheduleNo(int trainingScheduleNo) {
		this.trainingScheduleNo = trainingScheduleNo;
	}
	public int getTrainingRecordNo() {
		return trainingRecordNo;
	}
	public void setTrainingRecordNo(int trainingRecordNo) {
		this.trainingRecordNo = trainingRecordNo;
	}
	public String getTrainingId() {
		return trainingId;
	}
	public void setTrainingId(String trainingId) {
		this.trainingId = trainingId;
	}
	public String getTrainingHdr() {
		return trainingHdr;
	}
	public void setTrainingHdr(String trainingHdr) {
		this.trainingHdr = trainingHdr;
	}
	public String getTrainingDtl() {
		return trainingDtl;
	}
	public void setTrainingDtl(String trainingDtl) {
		this.trainingDtl = trainingDtl;
	}
	public String getTrainingScheduleDesc() {
		return trainingScheduleDesc;
	}
	public void setTrainingScheduleDesc(String trainingScheduleDesc) {
		this.trainingScheduleDesc = trainingScheduleDesc;
	}
	public Timestamp getTrainingStartDate() {
		return trainingStartDate;
	}
	public void setTrainingStartDate(Timestamp trainingStartDate) {
		this.trainingStartDate = trainingStartDate;
	}
	public Time getTrainingStartTime() {
		return trainingStartTime;
	}
	public void setTrainingStartTime(Time trainingStartTime) {
		this.trainingStartTime = trainingStartTime;
	}
	public Timestamp getTrainingEndDate() {
		return trainingEndDate;
	}
	public void setTrainingEndDate(Timestamp trainingEndDate) {
		this.trainingEndDate = trainingEndDate;
	}
	public Time getTrainingEndTime() {
		return trainingEndTime;
	}
	public void setTrainingEndTime(Time trainingEndTime) {
		this.trainingEndTime = trainingEndTime;
	}
	public String getTrainingRefDocPath() {
		return trainingRefDocPath;
	}
	public void setTrainingRefDocPath(String trainingRefDocPath) {
		this.trainingRefDocPath = trainingRefDocPath;
	}
	public int getTrainingRefNodeId() {
		return trainingRefNodeId;
	}
	public void setTrainingRefNodeId(int trainingRefNodeId) {
		this.trainingRefNodeId = trainingRefNodeId;
	}
	public int getTrainingRefTranNo() {
		return trainingRefTranNo;
	}
	public void setTrainingRefTranNo(int trainingRefTranNo) {
		this.trainingRefTranNo = trainingRefTranNo;
	}
	public int getTotalTrainingDuration() {
		return totalTrainingDuration;
	}
	public void setTotalTrainingDuration(int totalTrainingDuration) {
		this.totalTrainingDuration = totalTrainingDuration;
	}
	public String getRefWorkspaceId() {
		return refWorkspaceId;
	}
	public void setRefWorkspaceId(String refWorkspaceId) {
		this.refWorkspaceId = refWorkspaceId;
	}
	public String getRefWorkspaceDesc() {
		return refWorkspaceDesc;
	}
	public void setRefWorkspaceDesc(String refWorkspaceDesc) {
		this.refWorkspaceDesc = refWorkspaceDesc;
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
	public String getModifyByUser() {
		return modifyByUser;
	}
	public void setModifyByUser(String modifyByUser) {
		this.modifyByUser = modifyByUser;
	}
	public Time getDiffTime() {
		return diffTime;
	}
	public void setDiffTime(Time diffTime) {
		this.diffTime = diffTime;
	}
	public ArrayList<DTOTrainingAttendanceMst> getTrainingAttendanceMst() {
		return trainingAttendanceMst;
	}
	public void setTrainingAttendanceMst(
			ArrayList<DTOTrainingAttendanceMst> trainingAttendanceMst) {
		this.trainingAttendanceMst = trainingAttendanceMst;
	}
}
