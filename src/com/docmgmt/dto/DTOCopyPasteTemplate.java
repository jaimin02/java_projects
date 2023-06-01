/*
 * Created on Nov 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOCopyPasteTemplate implements Serializable{

	String sourceTemplateId;
	String destTemplateId;
	int sourceNodeId;
	int destNodeId;
	int userCode;
	Date modifyOn;
	
	
	/**
	 * @return Returns the destNodeId.
	 */
	public int getDestNodeId() {
		return destNodeId;
	}
	/**
	 * @param destNodeId The destNodeId to set.
	 */
	public void setDestNodeId(int destNodeId) {
		this.destNodeId = destNodeId;
	}
	/**
	 * @return Returns the destTemplateId.
	 */
	public String getDestTemplateId() {
		return destTemplateId;
	}
	/**
	 * @param destTemplateId The destTemplateId to set.
	 */
	public void setDestTemplateId(String destTemplateId) {
		this.destTemplateId = destTemplateId;
	}
	/**
	 * @return Returns the modifyOn.
	 */
	public Date getModifyOn() {
		return modifyOn;
	}
	/**
	 * @param modifyOn The modifyOn to set.
	 */
	public void setModifyOn(Date modifyOn) {
		this.modifyOn = modifyOn;
	}
	/**
	 * @return Returns the sourceNodeId.
	 */
	public int getSourceNodeId() {
		return sourceNodeId;
	}
	/**
	 * @param sourceNodeId The sourceNodeId to set.
	 */
	public void setSourceNodeId(int sourceNodeId) {
		this.sourceNodeId = sourceNodeId;
	}
	/**
	 * @return Returns the sourceTemplateId.
	 */
	public String getSourceTemplateId() {
		return sourceTemplateId;
	}
	/**
	 * @param sourceTemplateId The sourceTemplateId to set.
	 */
	public void setSourceTemplateId(String sourceTemplateId) {
		this.sourceTemplateId = sourceTemplateId;
	}
	/**
	 * @return Returns the userCode.
	 */
	public int getUserCode() {
		return userCode;
	}
	/**
	 * @param userCode The userCode to set.
	 */
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
   }
