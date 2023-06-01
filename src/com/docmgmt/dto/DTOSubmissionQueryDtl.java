package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOSubmissionQueryDtl implements Serializable {

	long queryId;
	String workSpaceId;
	String workSpaceDesc;
	String seqNo;
	int nodeId;
	int tranNo;
	char Resolved;
	int resolvedBy;
	Timestamp resolvedDate;
	String queryStatus;
	String remark;
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	
	String locationName;
	String deptName;
	String clientName;
	String templateDesc;
	String workspaceRemark;
	String nodeName;
	String nodeDisplayName;
	char nodeTypeIndi;
	String folderName;
	String resolvedByName;
	String modifyByName;
	
	
	public long getQueryId() {
		return queryId;
	}
	public void setQueryId(long queryId) {
		this.queryId = queryId;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}
	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getTranNo() {
		return tranNo;
	}
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
	public char getResolved() {
		return Resolved;
	}
	public void setResolved(char resolved) {
		Resolved = resolved;
	}
	public int getResolvedBy() {
		return resolvedBy;
	}
	public void setResolvedBy(int resolvedBy) {
		this.resolvedBy = resolvedBy;
	}
	
	public Timestamp getResolvedDate() {
		return resolvedDate;
	}
	public void setResolvedDate(Timestamp resolvedDate) {
		this.resolvedDate = resolvedDate;
	}
	public String getQueryStatus() {
		return queryStatus;
	}
	public void setQueryStatus(String queryStatus) {
		this.queryStatus = queryStatus;
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
	public String getLocationName() {
		return locationName;
	}
	public String getDeptName() {
		return deptName;
	}
	public String getClientName() {
		return clientName;
	}
	public String getTemplateDesc() {
		return templateDesc;
	}
	public String getWorkspaceRemark() {
		return workspaceRemark;
	}
	public String getNodeName() {
		return nodeName;
	}
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	public char getNodeTypeIndi() {
		return nodeTypeIndi;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}
	public void setWorkspaceRemark(String workspaceRemark) {
		this.workspaceRemark = workspaceRemark;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
	public void setNodeTypeIndi(char nodeTypeIndi) {
		this.nodeTypeIndi = nodeTypeIndi;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getResolvedByName() {
		return resolvedByName;
	}
	public void setResolvedByName(String resolvedByName) {
		this.resolvedByName = resolvedByName;
	}
	public String getModifyByName() {
		return modifyByName;
	}
	public void setModifyByName(String modifyByName) {
		this.modifyByName = modifyByName;
	}
}
