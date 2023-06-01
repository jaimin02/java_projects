/*
 * Created on Aug 11, 2007
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
public class DTOAttributeGroupMatrix implements Serializable{
	
	int id;
	String attrGroupId;
	String attrName;
	int attrId;
	char statusIndi;
	Timestamp modifyOn;
	String attrGroupName;
	String attrValue;
	String attrForIndi;
	String attrType;
	String workspaceId;
	int nodeId;
	int modifyBy;

	
	
	public String getAttrType() {
		return attrType;
	}
	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}
	public String getAttrGroupId() {
		return attrGroupId;
	}
	public void setAttrGroupId(String attrGroupId) {
		this.attrGroupId = attrGroupId;
	}
	public int getAttrId() {
		return attrId;
	}
	public void setAttrId(int attrId) {
		this.attrId = attrId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public String getAttrGroupName() {
		return attrGroupName;
	}
	public void setAttrGroupName(String attrGroupName) {
		this.attrGroupName = attrGroupName;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getAttrForIndi() {
		return attrForIndi;
	}
	public void setAttrForIndi(String attrForIndi) {
		this.attrForIndi = attrForIndi;
	}
	public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
}
