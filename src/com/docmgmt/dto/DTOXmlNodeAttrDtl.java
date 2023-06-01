
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOXmlNodeAttrDtl implements Serializable{

    long xmlNodeAttrDtlId;
	long xmlWorkspaceId;
	int xmlNodeId;
	int attrId;
	String attrName;
	String defaultAttrValue;
	String tableName;
	String columnName;
	char fixed;
	char required;
	Timestamp modifyOn;
	int modifyBy;
	char statusIndi;
	
	
	public long getXmlNodeAttrDtlId() {
		return xmlNodeAttrDtlId;
	}
	public void setXmlNodeAttrDtlId(long xmlNodeAttrDtlId) {
		this.xmlNodeAttrDtlId = xmlNodeAttrDtlId;
	}
	public long getXmlWorkspaceId() {
		return xmlWorkspaceId;
	}
	public void setXmlWorkspaceId(long xmlWorkspaceId) {
		this.xmlWorkspaceId = xmlWorkspaceId;
	}
	public int getXmlNodeId() {
		return xmlNodeId;
	}
	public void setXmlNodeId(int xmlNodeId) {
		this.xmlNodeId = xmlNodeId;
	}
	public int getAttrId() {
		return attrId;
	}
	public void setAttrId(int attrId) {
		this.attrId = attrId;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public String getDefaultAttrValue() {
		return defaultAttrValue;
	}
	public void setDefaultAttrValue(String defaultAttrValue) {
		this.defaultAttrValue = defaultAttrValue;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public char getRequired() {
		return required;
	}
	public void setRequired(char required) {
		this.required = required;
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
	public char getFixed() {
		return fixed;
	}
	public void setFixed(char fixed) {
		this.fixed = fixed;
	}
	
}