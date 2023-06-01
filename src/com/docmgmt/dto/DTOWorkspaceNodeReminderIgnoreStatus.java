package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOWorkspaceNodeReminderIgnoreStatus
{
	public String vWorkspaceId;
	public int iNodeId;
	public int iAttrId;
	public int iUserCode;
	public Timestamp dIgnoreUpto;
	public String vRemark;
	public int iModifyBy;
	public Timestamp dModifyOn;
	public char cStatusIndi;
	public String getvWorkspaceId() {
		return vWorkspaceId;
	}
	public void setvWorkspaceId(String vWorkspaceId) {
		this.vWorkspaceId = vWorkspaceId;
	}
	public int getiNodeId() {
		return iNodeId;
	}
	public void setiNodeId(int iNodeId) {
		this.iNodeId = iNodeId;
	}
	public int getiAttrId() {
		return iAttrId;
	}
	public void setiAttrId(int iAttrId) {
		this.iAttrId = iAttrId;
	}
	public int getiUserCode() {
		return iUserCode;
	}
	public void setiUserCode(int iUserCode) {
		this.iUserCode = iUserCode;
	}	
	public String getvRemark() {
		return vRemark;
	}
	public void setvRemark(String vRemark) {
		this.vRemark = vRemark;
	}
	public int getiModifyBy() {
		return iModifyBy;
	}
	public void setiModifyBy(int iModifyBy) {
		this.iModifyBy = iModifyBy;
	}
	public Timestamp getdModifyOn() {
		return dModifyOn;
	}
	public void setdModifyOn(Timestamp dModifyOn) {
		this.dModifyOn = dModifyOn;
	}
	public char getcStatusIndi() {
		return cStatusIndi;
	}
	public void setcStatusIndi(char cStatusIndi) {
		this.cStatusIndi = cStatusIndi;
	}
	public Timestamp getdIgnoreUpto() {
		return dIgnoreUpto;
	}
	public void setdIgnoreUpto(Timestamp dIgnoreUpto) {
		this.dIgnoreUpto = dIgnoreUpto;
	}	
}