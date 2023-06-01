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
public class DTOStageMst implements Serializable{

    int stageId;
    String stageIdForESIgn;
	String stageDesc;                                           
	int modifyBy;                              
	Timestamp modifyOn;            
	char statusIndi;   
	char inclusive;
	int seqNo;
	char userFlag;
	
	
	
	public char getUserFlag() {
		return userFlag;
	}
	public void setUserFlag(char userFlag) {
		this.userFlag = userFlag;
	}	
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	public String getStageDesc() {
		return stageDesc;
	}
	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
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
	public char getInclusive() {
		return inclusive;
	}
	public void setInclusive(char inclusive) {
		this.inclusive = inclusive;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public String getStageIdForESIgn() {
		return stageIdForESIgn;
	}
	public void setStageIdForESIgn(String stageIdForESIgn) {
		this.stageIdForESIgn = stageIdForESIgn;
	}
	
	
}
