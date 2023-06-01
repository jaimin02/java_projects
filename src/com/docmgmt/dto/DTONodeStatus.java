/*
 * Created on Jul 14, 2007
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
public class DTONodeStatus implements Serializable{

	String workSpaceId;
	int nodeId;
	int attrId;
	int tranNo;
	String attrValue;
	String remark;
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	String nodeName;
	String user;	
	String SOPname;
	Timestamp apprevDate;
	String workSpaceDesc;	
	
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
	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public int getTranNo() {
		return tranNo;
	}
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}	
	public String getNodeName() {
		return nodeName;
	}	
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}	
	public String getSOPname() {
		return SOPname;
	}	
	public void setSOPname(String pname) {
		SOPname = pname;
	}	
	public String getUser() {
		return user;
	}	
	public void setUser(String user) {
		this.user = user;
	}
	public Timestamp getApprevDate() {
		return apprevDate; 
	}
	public void setApprevDate(Timestamp apprevDate) {
		this.apprevDate = apprevDate;
	}	
	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}	
	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc; 
	}
}
