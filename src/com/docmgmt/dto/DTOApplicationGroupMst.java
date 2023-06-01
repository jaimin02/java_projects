package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOApplicationGroupMst {
	
	 String applicationCode;
	 String applicationName;
	 String hostingId;
	 String catgoryId;
	 String remark;
	 String userName;
	 int modifyBy;
	 Timestamp modifyOn;            
	 char statusIndi;   	
	 String ISTDateTime;
	 String ESTDateTime;
	 String hostingName;
	 String hostingCode;
	 String categoryName;
	 String categoryCode;
	 String fileName;
	 String attachmentTitle;
	 String attachmentPath;
	 float nSize;
	 int tranNo;
	 double fileSize;

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getHostingId() {
		return hostingId;
	}

	public void setHostingId(String hostingId) {
		this.hostingId = hostingId;
	}

	public String getCatgoryId() {
		return catgoryId;
	}

	public void setCatgoryId(String catgoryId) {
		this.catgoryId = catgoryId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	 public char getStatusIndi() {
	        return statusIndi;
	 }
	 
	 public void setStatusIndi(char statusIndi) {
	        this.statusIndi = statusIndi;
	 }

	public String getHostingName() {
		return hostingName;
	}

	public void setHostingName(String hostingName) {
		this.hostingName = hostingName;
	}

	public String getHostingCode() {
		return hostingCode;
	}

	public void setHostingCode(String hostingCode) {
		this.hostingCode = hostingCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public Timestamp getModifyOn() {
		return modifyOn;
	}

	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAttachmentTitle() {
		return attachmentTitle;
	}

	public void setAttachmentTitle(String attachmentTitle) {
		this.attachmentTitle = attachmentTitle;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public float getnSize() {
		return nSize;
	}
	public void setnSize(float nSize) {
		this.nSize = nSize;
	}

	public int getTranNo() {
		return tranNo;
	}

	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}


}
