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
public class DTOSubmissionForumHdr implements Serializable{

    String submissionId;           
    int nodeId;         
    String subjectId;         
    int userGroupCode;         
    int userCode;         
    char statusIndi;
    String subjectDesc;
    Timestamp modifyOn;
    String userName;
    
  
    public int getNodeId() {
        return nodeId;
    }
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
    public char getStatusIndi() {
        return statusIndi;
    }
    public void setStatusIndi(char statusIndi) {
        this.statusIndi = statusIndi;
    }
    public String getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }   
    public int getUserCode() {
        return userCode;
    }
    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }
    public int getUserGroupCode() {
        return userGroupCode;
    }
    public void setUserGroupCode(int userGroupCode) {
        this.userGroupCode = userGroupCode;
    }
    public String getSubmissionId() {
        return submissionId;
    }
    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }
    public String getSubjectDesc() {
        return subjectDesc;
    }
    public void setSubjectDesc(String subjectDesc) {
        this.subjectDesc = subjectDesc;
    }
   
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
}
