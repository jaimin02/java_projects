package com.docmgmt.dto;

public class DTOReminder
{
	String attrName;	
	String workspaceDesc;
	String attrValue;
	int nodeId;	
	String nodeName;
	String nodeDisplayName;
	String workspaceId;
	int attrId;
	int userCode;
	boolean isDone;
	boolean isIgnore;
	String attrForIndiId;
		
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	public boolean isIgnore() {
		return isIgnore;
	}
	public void setIgnore(boolean isIgnore) {
		this.isIgnore = isIgnore;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
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
	public String getWorkspaceDesc() {
		return workspaceDesc;
	}
	public void setWorkspaceDesc(String workspaceDesc) {
		this.workspaceDesc = workspaceDesc;
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
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
	public String getAttrForIndiId() {
		return attrForIndiId;
	}
	public void setAttrForIndiId(String attrForIndiId) {
		this.attrForIndiId = attrForIndiId;
	}
}