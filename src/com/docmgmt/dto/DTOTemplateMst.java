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
public class DTOTemplateMst implements Serializable{

    String templateId;                                    
	String templateDesc;  
	String docTypeCode;                                            
	String remark;                             
	int modifyBy;                           
	Timestamp modifyOn;              
	char statusIndi;
	int pageNumber;
	private String ISTDateTime;
	private String ESTDateTime;
	
	
    
    public String getISTDateTime() {
		return ISTDateTime;
	}
	public void setISTDateTime(String iSTDateTime) {
		ISTDateTime = iSTDateTime;
	}
	public String getESTDateTime() {
		return ESTDateTime;
	}
	public void setESTDateTime(String eSTDateTime) {
		ESTDateTime = eSTDateTime;
	}
	
    
    /**
     * @return Returns the docTypeCode.
     */
    public String getDocTypeCode() {
        return docTypeCode;
    }
    /**
     * @param docTypeCode The docTypeCode to set.
     */
    public void setDocTypeCode(String docTypeCode) {
        this.docTypeCode = docTypeCode;
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
     * @return Returns the templateDesc.
     */
    public String getTemplateDesc() {
        return templateDesc;
    }
    /**
     * @param templateDesc The templateDesc to set.
     */
    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }
    /**
     * @return Returns the templateId.
     */
    public String getTemplateId() {
        return templateId;
    }
    /**
     * @param templateId The templateId to set.
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
    public int getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
}
