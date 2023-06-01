
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOXmlNodeDtl implements Serializable{

    long xmlNodeDtlId;
	long xmlWorkspaceId;
	int xmlNodeId;
	int nodeNo;
	String xmlNodeName;
	int parentNodeId;
	char repeatable;
	char empty;
	char mandatory;
	String tableName;
	String columnName;
	long PrimaryXmlAttrId;
	String whereClauseColumns;
	Timestamp modifyOn;
	int modifyBy;
	char statusIndi;
	
	String xmlWorkspaceName;
	String xmlFileName;
	String filePath;
	String xmlHeader;
	
	
	
	public String getXmlWorkspaceName() {
		return xmlWorkspaceName;
	}
	public void setXmlWorkspaceName(String xmlWorkspaceName) {
		this.xmlWorkspaceName = xmlWorkspaceName;
	}
	public String getXmlFileName() {
		return xmlFileName;
	}
	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getXmlHeader() {
		return xmlHeader;
	}
	public void setXmlHeader(String xmlHeader) {
		this.xmlHeader = xmlHeader;
	}
	public long getXmlNodeDtlId() {
		return xmlNodeDtlId;
	}
	public void setXmlNodeDtlId(long xmlNodeDtlId) {
		this.xmlNodeDtlId = xmlNodeDtlId;
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
	public int getNodeNo() {
		return nodeNo;
	}
	public void setNodeNo(int nodeNo) {
		this.nodeNo = nodeNo;
	}
	public String getXmlNodeName() {
		return xmlNodeName;
	}
	public void setXmlNodeName(String xmlNodeName) {
		this.xmlNodeName = xmlNodeName;
	}
	public int getParentNodeId() {
		return parentNodeId;
	}
	public void setParentNodeId(int parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	public char getRepeatable() {
		return repeatable;
	}
	public void setRepeatable(char repeatable) {
		this.repeatable = repeatable;
	}
	public char getEmpty() {
		return empty;
	}
	public void setEmpty(char empty) {
		this.empty = empty;
	}
	public char getMandatory() {
		return mandatory;
	}
	public void setMandatory(char mandatory) {
		this.mandatory = mandatory;
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
	
	public long getPrimaryXmlAttrId() {
		return PrimaryXmlAttrId;
	}
	public void setPrimaryXmlAttrId(long primaryXmlAttrId) {
		PrimaryXmlAttrId = primaryXmlAttrId;
	}
	public String getWhereClauseColumns() {
		return whereClauseColumns;
	}
	public void setWhereClauseColumns(String whereClauseColumns) {
		this.whereClauseColumns = whereClauseColumns;
	}
	
	

}