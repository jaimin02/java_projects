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
public class DTOSTFNodeMst implements Serializable{

	String id;
	String nodeName;
	String nodeCategory;
	String remark;
	
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	char multiple;
	
	public char getMultiple() {
		return multiple;
	}
	public void setMultiple(char multiple) {
		this.multiple = multiple;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getNodeCategory() {
		return nodeCategory;
	}
	public void setNodeCategory(String nodeCategory) {
		this.nodeCategory = nodeCategory;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
}
