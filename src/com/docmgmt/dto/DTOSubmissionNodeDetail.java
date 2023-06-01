/*
 * Created on Jan 10, 2006
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
public class DTOSubmissionNodeDetail implements Serializable{

    String submissionId;         
    int nodeId;         
    int nodeNo;         
    String nodeName;         
    String nodeDisplayName;         
    char nodeTypeIndi;         
    int parentNodeId;         
    String folderName;         
    int modifyBy;         
    Timestamp modifyOn;         
    char statusIndi;   
    
  
    public String getFolderName() {
        return folderName;
    }
    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
    public int getModifyBy() {
        return modifyBy;
    }
    public void setModifyBy(int modifyBy) {
        this.modifyBy = modifyBy;
    }  
    public String getNodeDisplayName() {
        return nodeDisplayName;
    }
    public void setNodeDisplayName(String nodeDisplayName) {
        this.nodeDisplayName = nodeDisplayName;
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
    public char getNodeTypeIndi() {
        return nodeTypeIndi;
    }
    public void setNodeTypeIndi(char nodeTypeIndi) {
        this.nodeTypeIndi = nodeTypeIndi;
    }
    public int getParentNodeId() {
        return parentNodeId;
    }
    public void setParentNodeId(int parentNodeId) {
        this.parentNodeId = parentNodeId;
    }
    public char getStatusIndi() {
        return statusIndi;
    }
    public void setStatusIndi(char statusIndi) {
        this.statusIndi = statusIndi;
    }
    
    public String getSubmissionId() {
        return submissionId;
    }
    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
}
