/*
 * Created on Nov 29, 2005
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
public class DTOWorkSpaceNodeAttrHistory implements Serializable{

    String workSpaceId;                                
	int nodeId;                          
	int tranNo;                          
	int attrId;                          
	String attrValue;                                         
	String remark;                     
	int modifyBy;                     
	Timestamp modifyOn;        
	char statusIndi; 
	String attrName;
	String attrForIndiId;
	String ISTDateTime;
	String ESTDateTime;
	String voidBy;
	Timestamp voidOn;
	String voidISTDateTime;
	String voidESTDateTime;
	
	
	public String getVoidBy() {
		return voidBy;
	}
	public void setVoidBy(String voidBy) {
		this.voidBy = voidBy;
	}
	public Timestamp getVoidOn() {
		return voidOn;
	}
	public void setVoidOn(Timestamp voidOn) {
		this.voidOn = voidOn;
	}
	public String getVoidISTDateTime() {
		return voidISTDateTime;
	}
	public void setVoidISTDateTime(String voidISTDateTime) {
		this.voidISTDateTime = voidISTDateTime;
	}
	public String getVoidESTDateTime() {
		return voidESTDateTime;
	}
	public void setVoidESTDateTime(String voidESTDateTime) {
		this.voidESTDateTime = voidESTDateTime;
	}
	/**
     * @return Returns IST Time and Date.
     */
	public String getISTDateTime() {
		return ISTDateTime;
	}
	 /**
     * @param IST Date Time The ISTDateTime to set.
     */
	public void setISTDateTime(String iSTDateTime) {
		ISTDateTime = iSTDateTime;
	}
	/**
     * @return Returns EST Time and Date.
     */
	/**
     * @return Returns EST Time and Date.
     */
	public String getESTDateTime() {
		return ESTDateTime;
	}
	 /**
     * @param IST Date Time The ISTDateTime to set.
     */
	public void setESTDateTime(String eSTDateTime) {
		ESTDateTime = eSTDateTime;
	}
	
	/**
     * @return Returns the attrId.
     */
	/**
	 * 
     * @return Returns the attrId.
     */
    public int getAttrId() {
        return attrId;
    }
    /**
     * @param attrId The attrId to set.
     */
    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }
    /**
     * @return Returns the attrValue.
     */
    public String getAttrValue() {
        return attrValue;
    }
    /**
     * @param attrValue The attrValue to set.
     */
    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
    /**
     * @return Returns the modifyBy.
     */
    public int getModifyBy() {
        return modifyBy;
    }
    /**
     * @param modifyBy The modifyBy to set.
     */
    public void setModifyBy(int modifyBy) {
        this.modifyBy = modifyBy;
    }
    /**
     * @return Returns the nodeId.
     */
    public int getNodeId() {
        return nodeId;
    }
    /**
     * @param nodeId The nodeId to set.
     */
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
    /**
     * @return Returns the remark.
     */
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * @return Returns the statusIndi.
     */
    public char getStatusIndi() {
        return statusIndi;
    }
    /**
     * @param statusIndi The statusIndi to set.
     */
    public void setStatusIndi(char statusIndi) {
        this.statusIndi = statusIndi;
    }
    /**
     * @return Returns the tranNo.
     */
    public int getTranNo() {
        return tranNo;
    }
    /**
     * @param tranNo The tranNo to set.
     */
    public void setTranNo(int tranNo) {
        this.tranNo = tranNo;
    }
    /**
     * @return Returns the workSpaceId.
     */
    public String getWorkSpaceId() {
        return workSpaceId;
    }
    /**
     * @param workSpaceId The workSpaceId to set.
     */
    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
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
	public String getAttrForIndiId() {
		return attrForIndiId;
	}
	public void setAttrForIndiId(String attrForIndiId) {
		this.attrForIndiId = attrForIndiId;
	}
}
