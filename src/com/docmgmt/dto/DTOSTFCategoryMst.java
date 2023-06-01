/*
 * Created on Nov 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Vector;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOSTFCategoryMst implements Serializable{

	String id;
	String categoryName;
	String category;
	
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	
	int nodeId;
	int nodeNo;
	int parentNodeId;
	String nodeName;
	String workspaceId;
	Vector multipleTypeChildNodes;
	
	
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public int getNodeNo() {
		return nodeNo;
	}
	public void setNodeNo(int nodeNo) {
		this.nodeNo = nodeNo;
	}
	public int getParentNodeId() {
		return parentNodeId;
	}
	public void setParentNodeId(int parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	public Vector getMultipleTypeChildNodes() {
		return multipleTypeChildNodes;
	}
	public void setMultipleTypeChildNodes(Vector multipleTypeChildNodes) {
		this.multipleTypeChildNodes = multipleTypeChildNodes;
	}
}
