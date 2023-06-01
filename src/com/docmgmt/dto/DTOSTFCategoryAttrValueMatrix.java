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
public class DTOSTFCategoryAttrValueMatrix implements Serializable{

	String categoryAttrValueId;
	String categoryId;
	String categoryName;
	String categoryValue;
	String attrName;
	String attrValue;
	String remark;
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	
	
	public String getCategoryAttrValueId() {
		return categoryAttrValueId;
	}
	public void setCategoryAttrValueId(String categoryAttrValueId) {
		this.categoryAttrValueId = categoryAttrValueId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryValue() {
		return categoryValue;
	}
	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}
