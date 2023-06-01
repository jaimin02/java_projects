/*
 * Created on Nov 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOTemplateNodeDetail implements Serializable{

    String templateId;                                 
	int nodeId;                          
	int nodeNo;                     
	String nodeName;                                         
	String nodeDisplayName;                                         
	char nodeTypeIndi;                          
	int parentNodeId;                          
	String folderName;                     
	char requiredFlag;                                         
	char publishFlag;                          
	String remark;                     
	int modifyBy;                     
	Timestamp modifyOn;        
	char statusIndi;                          
	String defaultFileFormat;
	
	ArrayList<DTOTemplateNodeAttrDetail> nodeAttrList;
	ArrayList<DTOTemplateNodeDetail> childNodeList;
	
   
	public ArrayList<DTOTemplateNodeAttrDetail> getNodeAttrList() {
		return nodeAttrList;
	}
	public void setNodeAttrList(ArrayList<DTOTemplateNodeAttrDetail> nodeAttrList) {
		this.nodeAttrList = nodeAttrList;
	}
	public ArrayList<DTOTemplateNodeDetail> getChildNodeList() {
		return childNodeList;
	}
	public void setChildNodeList(ArrayList<DTOTemplateNodeDetail> childNodeList) {
		this.childNodeList = childNodeList;
	}
	/**
     * @return Returns the defaultFileFormat.
     */
    public String getDefaultFileFormat() {
        return defaultFileFormat;
    }
    /**
     * @param defaultFileFormat The defaultFileFormat to set.
     */
    public void setDefaultFileFormat(String defaultFileFormat) {
        this.defaultFileFormat = defaultFileFormat;
    }
    /**
     * @return Returns the folderName.
     */
    public String getFolderName() {
        return folderName;
    }
    /**
     * @param folderName The folderName to set.
     */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
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
     * @return Returns the nodeDisplayName.
     */
    public String getNodeDisplayName() {
        return nodeDisplayName;
    }
    /**
     * @param nodeDisplayName The nodeDisplayName to set.
     */
    public void setNodeDisplayName(String nodeDisplayName) {
        this.nodeDisplayName = nodeDisplayName;
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
     * @return Returns the nodeName.
     */
    public String getNodeName() {
        return nodeName;
    }
    /**
     * @param nodeName The nodeName to set.
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
    /**
     * @return Returns the nodeNo.
     */
    public int getNodeNo() {
        return nodeNo;
    }
    /**
     * @param nodeNo The nodeNo to set.
     */
    public void setNodeNo(int nodeNo) {
        this.nodeNo = nodeNo;
    }
    /**
     * @return Returns the nodeTypeIndi.
     */
    public char getNodeTypeIndi() {
        return nodeTypeIndi;
    }
    /**
     * @param nodeTypeIndi The nodeTypeIndi to set.
     */
    public void setNodeTypeIndi(char nodeTypeIndi) {
        this.nodeTypeIndi = nodeTypeIndi;
    }
    /**
     * @return Returns the parentNodeId.
     */
    public int getParentNodeId() {
        return parentNodeId;
    }
    /**
     * @param parentNodeId The parentNodeId to set.
     */
    public void setParentNodeId(int parentNodeId) {
        this.parentNodeId = parentNodeId;
    }
    /**
     * @return Returns the publishFlag.
     */
    public char getPublishFlag() {
        return publishFlag;
    }
    /**
     * @param publishFlag The publishFlag to set.
     */
    public void setPublishFlag(char publishFlag) {
        this.publishFlag = publishFlag;
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
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
}
