package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOUserMst implements Serializable{
	
	int userCode;
	int userGroupCode;
	String userGroupName;
	String emailAddress;
	String adUserName;
	char isAdUser;
	String userName;
	String loginName;      
	String loginPass;
	String userType;     	
	String remark;         
	int modifyBy;       
	Timestamp modifyOn;  
	char statusIndi;
	String eMail;
	String userTypeCode;
	String loginIP;
	Timestamp loginOn;
	Timestamp loginOut;
	
	String userLocationName;
	String countyCode;
	
	int oldUserGroupCode;
	String likewiseSearch;
	
	boolean blockedFlag;
	private String ISTDateTime;
	private String ESTDateTime;
	String modifyByName;
	String locationCode;
	String locationName;
	String filePath;
	char clsUpload;
	char clsDownload;
	Timestamp dUploadOn;
	Timestamp dDownloadOn;
	
	String ISTUploadOn;
	String ESTUploadOn;
	
	String ISTDownloadOn;
	String ESTDownloadOn;
	String fileName;
	String loginId;
	String roleCode;
	String roleName;
	
	String ISTLogin;
	String ESTLogin;
	String ISTLogOut;
	String ESTLogOut;
	String totalTime;
	char isVerified;
	String deptCode;
	String deptName;

    public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
    public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public char getClsUpload() {
		return clsUpload;
	}
	public void setClsUpload(char clsUpload) {
		this.clsUpload = clsUpload;
	}
	public char getClsDownload() {
		return clsDownload;
	}
	public void setClsDownload(char clsDownload) {
		this.clsDownload = clsDownload;
	}
	public Timestamp getdUploadOn() {
		return dUploadOn;
	}
	public void setdUploadOn(Timestamp dUploadOn) {
		this.dUploadOn = dUploadOn;
	}
	public Timestamp getdDownloadOn() {
		return dDownloadOn;
	}
	public void setdDownloadOn(Timestamp dDownloadOn) {
		this.dDownloadOn = dDownloadOn;
	}
	public String getISTUploadOn() {
		return ISTUploadOn;
	}
	public void setISTUploadOn(String iSTUploadOn) {
		ISTUploadOn = iSTUploadOn;
	}
	public String getESTUploadOn() {
		return ESTUploadOn;
	}
	public void setESTUploadOn(String eSTUploadOn) {
		ESTUploadOn = eSTUploadOn;
	}
	public String getISTDownloadOn() {
		return ISTDownloadOn;
	}
	public void setISTDownloadOn(String iSTDownloadOn) {
		ISTDownloadOn = iSTDownloadOn;
	}
	public String getESTDownloadOn() {
		return ESTDownloadOn;
	}
	public void setESTDownloadOn(String eSTDownloadOn) {
		ESTDownloadOn = eSTDownloadOn;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getModifyByName() {
		return modifyByName;
	}
	public void setModifyByName(String modifyByName) {
		this.modifyByName = modifyByName;
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
	
	
	public Timestamp getLoginOn() {
		return loginOn;
	}


	public void setLoginOn(Timestamp loginOn) {
		this.loginOn = loginOn;
	}


	public Timestamp getLoginOut() {
		return loginOut;
	}


	public void setLoginOut(Timestamp loginOut) {
		this.loginOut = loginOut;
	}


	
	public boolean isBlockedFlag() {
		return blockedFlag;
	}



	public void setBlockedFlag(boolean blockedFlag) {
		this.blockedFlag = blockedFlag;
	}



	public DTOUserMst()
	{
		
	}
	
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	
	public String getUserLocationName() {
		return userLocationName;
	}
	public void setUserLocationName(String userLocationName) {
		this.userLocationName = userLocationName;
	}
	
	
	/**
	 * @return Returns the loginName.
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName The loginName to set.
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @return Returns the loginPass.
	 */
	public String getLoginPass() {
		return loginPass;
	}
	/**
	 * @param loginPass The loginPass to set.
	 */
	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
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
	/**
	 * @return Returns the userGroupCode.
	 */
	public int getUserGroupCode() {
		return userGroupCode;
	}
	/**
	 * @param userGroupCode The userGroupCode to set.
	 */
	public void setUserGroupCode(int userGroupCode) {
		this.userGroupCode = userGroupCode;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return Returns the userType.
	 */
	public String getUserType() {
		return userType;
	}
	/**
	 * @param userType The userType to set.
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
    /**
     * @return Returns the eMail.
     */
    public String getEMail() {
        return eMail;
    }
    /**
     * @param mail The eMail to set.
     */
    public void setEMail(String mail) {
        eMail = mail;
    }
	/**
	 * @return Returns the userGroupName.
	 */
	public String getUserGroupName() {
		return userGroupName;
	}
	/**
	 * @param userGroupName The userGroupName to set.
	 */
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
	/**
	 * @return Returns the emailAddress.
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress The emailAddress to set.
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * @return Returns the oldUserGroupCode.
	 */
	public int getOldUserGroupCode() {
		return oldUserGroupCode;
	}
	/**
	 * @param oldUserGroupCode The oldUserGroupCode to set.
	 */
	public void setOldUserGroupCode(int oldUserGroupCode) {
		this.oldUserGroupCode = oldUserGroupCode;
	}
	/**
	 * @return Returns the likewiseSearch.
	 */
	public String getLikewiseSearch() {
		return likewiseSearch;
	}
	/**
	 * @param likewiseSearch The likewiseSearch to set.
	 */
	public void setLikewiseSearch(String likewiseSearch) {
		this.likewiseSearch = likewiseSearch;
	}
	public String getLoginIP() {
		return loginIP;
	}
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public String getUserTypeCode() {
		return userTypeCode;
	}
	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}
	public String getISTLogin() {
		return ISTLogin;
	}
	public void setISTLogin(String iSTLogin) {
		ISTLogin = iSTLogin;
	}
	public String getESTLogin() {
		return ESTLogin;
	}
	public void setESTLogin(String eSTLogin) {
		ESTLogin = eSTLogin;
	}
	public String getISTLogOut() {
		return ISTLogOut;
	}
	public void setISTLogOut(String iSTLogOut) {
		ISTLogOut = iSTLogOut;
	}
	public String getESTLogOut() {
		return ESTLogOut;
	}
	public void setESTLogOut(String eSTLogOut) {
		ESTLogOut = eSTLogOut;
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	public String getAdUserName() {
		return adUserName;
	}
	public void setAdUserName(String adUserName) {
		this.adUserName = adUserName;
	}
	public char getIsAdUser() {
		return isAdUser;
	}
	public void setIsAdUser(char isAdUser) {
		this.isAdUser = isAdUser;
	}
	public char getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(char cIsVerified) {
		this.isVerified = cIsVerified;
	}
}
