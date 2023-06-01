package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOAttrReferenceTableMatrix implements Serializable
{
	private static final long serialVersionUID = 5471757797082271900L;
	int iAttrId;
	String vTableName;
	String vTableColumn;
	String vTableType;
	String vRemark;
	int iModifyBy;
	Timestamp dModifyOn;
	String cStatusIndi;
	
	public int getiAttrId() {
		return iAttrId;
	}
	public void setiAttrId(int iAttrId) {
		this.iAttrId = iAttrId;
	}
	public String getvTableName() {
		return vTableName;
	}
	public void setvTableName(String vTableName) {
		this.vTableName = vTableName;
	}
	public String getvTableColumn() {
		return vTableColumn;
	}
	public void setvTableColumn(String vTableColumn) {
		this.vTableColumn = vTableColumn;
	}
	public String getvTableType() {
		return vTableType;
	}
	public void setvTableType(String vTableType) {
		this.vTableType = vTableType;
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
	public String getcStatusIndi() {
		return cStatusIndi;
	}
	public void setcStatusIndi(String cStatusIndi) {
		this.cStatusIndi = cStatusIndi;
	}
}