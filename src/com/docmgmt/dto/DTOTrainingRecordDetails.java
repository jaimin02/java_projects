package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DTOTrainingRecordDetails implements Serializable  {
	private static final long serialVersionUID = -3198183610597339544L;
	int trainingRecordNo;
	String trainingId;
	String trainingHdr;
	String trainingDtl;
	int totalTrainingDuration;
	String refWorkspaceId;
	String refWorkspaceDesc;
	String wsStatusIndi;
	String remark;
	Timestamp modifyOn;
	int modifyBy;
	char statusIndi;
	String modifyByUser;
	char refWsStatusIndi;
	ArrayList<DTOTrainingScheduleingDetails> trainingScheduleingDetails;
	
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
	public String getWsStatusIndi() {
		return wsStatusIndi;
	}
	public void setWsStatusIndi(String wsStatusIndi) {
		this.wsStatusIndi = wsStatusIndi;
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
	public char getRefWsStatusIndi() {
		return refWsStatusIndi;
	}
	public void setRefWsStatusIndi(char refWsStatusIndi) {
		this.refWsStatusIndi = refWsStatusIndi;
	}
	public ArrayList<DTOTrainingScheduleingDetails> getTrainingScheduleingDetails() {
		return trainingScheduleingDetails;
	}
	public void setTrainingScheduleingDetails(
			ArrayList<DTOTrainingScheduleingDetails> trainingScheduleingDetails) {
		this.trainingScheduleingDetails = trainingScheduleingDetails;
	}
}
