/*
 * Created on Jun 10, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @author administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOTemplateNodeAttr implements Serializable{
    
    String templateId;
    int nodeId;
    int attrId;
    String attrName;                                         
	String attrValue;                                         
	int modifyBy;                     
    Timestamp modifyOn;        
	char statusIndi; 

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
    public String getAttrValue() {
        return attrValue;
    }
    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
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
    public String getTemplateId() {
        return templateId;
    }
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
}
