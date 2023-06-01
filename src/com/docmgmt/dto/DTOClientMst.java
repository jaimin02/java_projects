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
public class DTOClientMst implements Serializable{

    String clientCode;                                  
	String clientName;                                           
	String remark;                           
	int modifyBy;                              
	Timestamp modifyOn;            
	char statusIndi;   	
	String ISTDateTime;
	String ESTDateTime;
	String userName;	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
     * @return Returns the clientCode.
     */
    public String getClientCode() {
        return clientCode;
    }
    /**
     * @param clientCode The clientCode to set.
     */
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }
    /**
     * @return Returns the clientName.
     */
    public String getClientName() {
        return clientName;
    }
    /**
     * @param clientName The clientName to set.
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
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
     * @return Returns the modifyOn.
     */
    public Timestamp getModifyOn() {
        return modifyOn;
    }
    /**
     * @param modifyOn The modifyOn to set.
     */
    public void setModifyOn(Timestamp modifyOn) {
        this.modifyOn = modifyOn;
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
}
