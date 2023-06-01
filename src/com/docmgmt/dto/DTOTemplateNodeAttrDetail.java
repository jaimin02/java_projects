
package com.docmgmt.dto;


import java.sql.Timestamp;


public class DTOTemplateNodeAttrDetail implements IDTONodeAttr{

    String templateId;
    int nodeId;
    int attrId;
    String attrName;
	String attrValue;                                         
	String validValues;                     
	char requiredFlag;                                         
	char editableFlag;                                         
	String attrForIndiId;                         
	String remark;                     
	int modifyBy;                     
	Timestamp modifyOn;        
	char statusIndi;
	int parentNodeId;
	
	
	
	
	public String getAttrForIndiId() {
		return attrForIndiId;
	}
	public void setAttrForIndiId(String attrForIndiId) {
		this.attrForIndiId = attrForIndiId;
	}
	public int getParentNodeId() {
		return parentNodeId;
	}
	public void setParentNodeId(int parentNodeId) {
		this.parentNodeId = parentNodeId;
	}
    
    /**
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
     * @return Returns the attrName.
     */
    public String getAttrName() {
        return attrName;
    }
    /**
     * @param attrName The attrName to set.
     */
    public void setAttrName(String attrName) {
        this.attrName = attrName;
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
     * @return Returns the editableFlag.
     */
    public char getEditableFlag() {
        return editableFlag;
    }
    /**
     * @param editableFlag The editableFlag to set.
     */
    public void setEditableFlag(char editableFlag) {
        this.editableFlag = editableFlag;
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
     * @return Returns the requiredFlag.
     */
    public char getRequiredFlag() {
        return requiredFlag;
    }
    /**
     * @param requiredFlag The requiredFlag to set.
     */
    public void setRequiredFlag(char requiredFlag) {
        this.requiredFlag = requiredFlag;
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
    /**
     * @return Returns the validValues.
     */
    public String getValidValues() {
        return validValues;
    }
    /**
     * @param validValues The validValues to set.
     */
    public void setValidValues(String validValues) {
        this.validValues = validValues;
    }
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
}
