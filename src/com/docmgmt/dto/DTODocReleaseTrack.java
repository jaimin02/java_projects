package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Rahul Goswami 
 **/
public class DTODocReleaseTrack implements Serializable{
	private static final long serialVersionUID = 8021783588709227925L;
	
	int autoID;
	String workspaceId;
	int parentNodeId;
	int qty;
	String startId;
	String endId;
	Timestamp releaseDate;
	int releasedBy;
	String comments;
	String workspaceDesc;
	String remark;
	String nodeName;
	String nodeDisplayName;
	String folderName;
	String releasedByUser;
	String userGroupName;
	public int getAutoID() {
		return autoID;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public int getParentNodeId() {
		return parentNodeId;
	}
	public int getQty() {
		return qty;
	}
	public String getStartId() {
		return startId;
	}
	public String getEndId() {
		return endId;
	}
	public Timestamp getReleaseDate() {
		return releaseDate;
	}
	public int getReleasedBy() {
		return releasedBy;
	}
	public String getComments() {
		return comments;
	}
	public String getWorkspaceDesc() {
		return workspaceDesc;
	}
	public String getRemark() {
		return remark;
	}
	public String getNodeName() {
		return nodeName;
	}
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	public String getFolderName() {
		return folderName;
	}
	public String getReleasedByUser() {
		return releasedByUser;
	}
	public String getUserGroupName() {
		return userGroupName;
	}
	public void setAutoID(int autoID) {
		this.autoID = autoID;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	public void setParentNodeId(int parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public void setStartId(String startId) {
		this.startId = startId;
	}
	public void setEndId(String endId) {
		this.endId = endId;
	}
	public void setReleaseDate(Timestamp releaseDate) {
		this.releaseDate = releaseDate;
	}
	public void setReleasedBy(int releasedBy) {
		this.releasedBy = releasedBy;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setWorkspaceDesc(String workspaceDesc) {
		this.workspaceDesc = workspaceDesc;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public void setReleasedByUser(String releasedByUser) {
		this.releasedByUser = releasedByUser;
	}
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
}