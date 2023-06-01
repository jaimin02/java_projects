
package com.docmgmt.dto;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Vector;

/**
 * @author Riddhi Doshi
 */
public class DTOWorkSpaceUserRightsMst implements Serializable{
	String WorkSpaceId;
	int UserCode;
	int UserGroupCode;
	int NodeId;
	int parentNodeId;
	int stageId;
	char CanReadFlag;
	char CanAddFlag;
	char CanEditFlag;
	char CanDeleteFlag;
	String AdvancedRights;
	String Remark;
	int modifyBy;       
	Timestamp modifyOn;  
	char statusIndi;
	
	String stageDesc;
	String userGroupName;
	String userName;
	String userTypeName;
	
	String nodeDisplayName;
	String workSpaceDesc;
	String readRights;
	String editRights;
	String nodeName;
	String sortOn;
	String sortBy;
	String folderName;
	String from;
	String to;
	String stages;
	int mode;
	String ISTDateTime;
	String ESTDateTime;
	String modifyByName;
	char userFlag;
	String userTypeCode;
	public int duration;
	public int existUserCode;
	String fileVersionId;
	int completedStageId;
	int hours;
	Timestamp startDate;
	Timestamp endDate;
	Timestamp adjustDate;
	
	Timestamp fromDate;
	Timestamp toDate;
	
	String fileType;
	String loginName;
	String roleCode;
	String roleName;
	int seqNo;
	int tranNo;
	
	String baseWorkFolder;
	String filePath;
	String fName;
	String userType;
	String coordiNates;
	public String getCoordiNates() {
		return coordiNates;
	}





	public void setCoordiNates(String coordiNates) {
		this.coordiNates = coordiNates;
	}

	public Vector<DTOStageMst> getStageDetail;
	
	@Override
	public DTOWorkSpaceUserRightsMst clone() {
		DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
		Class<?>  dtoWorkSpaceUserRightsMstClass = DTOWorkSpaceUserRightsMst.class;
		try {
			Field[] fields =dtoWorkSpaceUserRightsMstClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Object value = fields[i].get(this);
				fields[i].set(dtoWorkSpaceUserRightsMst, value);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return dtoWorkSpaceUserRightsMst;
	}


	
	
	
	public int getTranNo() {
		return tranNo;
	}

	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}

	public String getBaseWorkFolder() {
		return baseWorkFolder;
	}
	
	public void setBaseWorkFolder(String baseWorkFolder) {
		this.baseWorkFolder = baseWorkFolder;
	}
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}


	public Vector<DTOStageMst> getGetStageDetail() {
		return getStageDetail;
	}

	public void setGetStageDetail(Vector<DTOStageMst> getStageDetail) {
		this.getStageDetail = getStageDetail;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public int getCompletedStageId() {
		return completedStageId;
	}
	public void setCompletedStageId(int completedStageId) {
		this.completedStageId = completedStageId;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}


	public Timestamp getAdjustDate() {
		return adjustDate;
	}
	public void setAdjustDate(Timestamp adjustDate) {
		this.adjustDate = adjustDate;
	}
	
	public String getFileVersionId() {
		return fileVersionId;
	}
	public void setFileVersionId(String fileVersionId) {
		this.fileVersionId = fileVersionId;
	}
	
	public int getExistUserCode() {
		return existUserCode;
	}

	public void setExistUserCode(int existUserCode) {
		this.existUserCode = existUserCode;
	}


	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}


	public String getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public char getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(char userFlag) {
		this.userFlag = userFlag;
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

	public String getModifyByName() {
		return modifyByName;
	}

	public void setModifyByName(String modifyByName) {
		this.modifyByName = modifyByName;
	}
	
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public String getStages() {
		return stages;
	}

	public void setStages(String stages) {
		this.stages = stages;
	}	
	
	public String getWorkSpaceId() {
		return WorkSpaceId;
	}
	public void setWorkSpaceId(String WorkSpaceId) {
		this.WorkSpaceId = WorkSpaceId;
	}

	public int getUserCode() {
		return UserCode;
	}
	public void setUserCode(int UserCode) {
		this.UserCode = UserCode;
	}

	public int getUserGroupCode() {
		return UserGroupCode;
	}
	public void setUserGroupCode(int UserGroupCode) {
		this.UserGroupCode = UserGroupCode;
	}
	
	public int getNodeId() {
		return NodeId;
	}
	public void setNodeId(int NodeId) {
		this.NodeId = NodeId;
	}	
	public int getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(int parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public char getCanReadFlag() {
		return CanReadFlag;
	}
	public void setCanReadFlag(char CanReadFlag) {
		this.CanReadFlag = CanReadFlag;
	}	

	public char getCanAddFlag() {
		return CanAddFlag;
	}
	public void setCanAddFlag(char CanAddFlag) {
		this.CanAddFlag = CanAddFlag;
	}	

	public char getCanEditFlag() {
		return CanEditFlag;
	}
	public void setCanEditFlag(char CanEditFlag) {
		this.CanEditFlag = CanEditFlag;
	}	

	public char getCanDeleteFlag() {
		return CanDeleteFlag;
	}
	public void setCanDeleteFlag(char CanDeleteFlag) {
		this.CanDeleteFlag = CanDeleteFlag;
	}
	
	public String getAdvancedRights() {
		return AdvancedRights;
	}
	public void setAdvancedRights(String AdvancedRights) {
		this.AdvancedRights = AdvancedRights;
	}
	

	public String getRemark() {
		return Remark;
	}
	public void setRemark(String Remark) {
		this.Remark = Remark;
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
	 * @return Returns the workSpaceDesc.
	 */
	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}
	/**
	 * @param workSpaceDesc The workSpaceDesc to set.
	 */
	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}
	/**
	 * @return Returns the editRights.
	 */
	public String getEditRights() {
		return editRights;
	}
	/**
	 * @param editRights The editRights to set.
	 */
	public void setEditRights(String editRights) {
		this.editRights = editRights;
	}
	/**
	 * @return Returns the readRights.
	 */
	public String getReadRights() {
		return readRights;
	}
	/**
	 * @param readRights The readRights to set.
	 */
	public void setReadRights(String readRights) {
		this.readRights = readRights;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getSortBy() {
		return sortBy;
	}
	
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	public String getSortOn() {
		return sortOn;
	}
	
	public void setSortOn(String sortOn) {
		this.sortOn = sortOn;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	public String getStageDesc() {
		return stageDesc;
	}
	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
	}
	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getUserTypeName() {
		return userTypeName;
	}
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	public int getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public Timestamp getFromDate() {
		return fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public Timestamp getToDate() {
		return toDate;
	}

	public void setToDate(Timestamp toDate) {
		this.toDate = toDate;
	}
	
	
	
}