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
public class DTOSubmissionNodeHistory implements Serializable{

    String  submissionId; 
    int  nodeId;       
    String  fileName;    
    String  folderName;
    String attrName;
    int attrId;                          
	String attrValue;                                         
	String remark;   
    int  modifyBy;     
    Timestamp modifyOn;     
    char statusIndi ; 


    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
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
   
    public String getSubmissionId() {
        return submissionId;
    }
    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }
	public int getAttrId() {
		return attrId;
	}
	public void setAttrId(int attrId) {
		this.attrId = attrId;
	}
	public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
}
