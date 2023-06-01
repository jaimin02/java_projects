
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOLocationMst implements Serializable{

	String locationCode;	       
	String locationName;
	String remark;
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	
	
    /**
     * @return Returns the locationCode.
     */
    public String getLocationCode() {
        return locationCode;
    }
    /**
     * @param locationCode The locationCode to set.
     */
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
    /**
     * @return Returns the locationName.
     */
    public String getLocationName() {
        return locationName;
    }
    /**
     * @param locationName The locationName to set.
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
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
     * @return Returns the statusInd.
     */
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	/**
	 * @return the modifyOn
	 */
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	/**
	 * @param modifyOn the modifyOn to set
	 */
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}

}
