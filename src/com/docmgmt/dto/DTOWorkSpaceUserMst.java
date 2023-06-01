/*
 * Created on Nov 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOWorkSpaceUserMst implements Serializable,Cloneable
{

    String workSpaceId;
    String userGroupName;
	int userGroupCode;                          
	int userCode;                          
	char adminFlag;                                         
	Timestamp lastAccessedOn;                     
	String remark;                     
	int modifyBy;                     
	Timestamp modifyOn;        
	char statusIndi;
	String username;
	String userTypeName;
	String rightsGivenBy;
	String stages;
	int stageId;
	int totalNode;
	int uploadedNode;
	int reviewFile;
	String completed;
	float approvePer;
	float reviewPer;
	float createPer;
	float otherPer;
	String totalPer;
	String roleCode;
	String roleName;
	
	
	
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
	public int getReviewFile() {
		return reviewFile;
	}
	public void setReviewFile(int reviewFile) {
		this.reviewFile = reviewFile;
	}
	public float getApprovePer() {
		return approvePer;
	}
	public void setApprovePer(float approvePer) {
		this.approvePer = approvePer;
	}
	public float getReviewPer() {
		return reviewPer;
	}
	public void setReviewPer(float reviewPer) {
		this.reviewPer = reviewPer;
	}
	public float getCreatePer() {
		return createPer;
	}
	public void setCreatePer(float createPer) {
		this.createPer = createPer;
	}
	public float getOtherPer() {
		return otherPer;
	}
	public void setOtherPer(float otherPer) {
		this.otherPer = otherPer;
	}
	public String getTotalPer() {
		return totalPer;
	}
	public void setTotalPer(String totalPer) {
		this.totalPer = totalPer;
	}
	public int getTotalNode() {
		return totalNode;
	}
	public void setTotalNode(int totalNode) {
		this.totalNode = totalNode;
	}
	public int getUploadedNode() {
		return uploadedNode;
	}
	public void setUploadedNode(int uploadedNode) {
		this.uploadedNode = uploadedNode;
	}
	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	int mode;
	
	String workspacedesc;
	
	Date fromDt;
	Date toDt;
	String RightsType;

	private String ISTDateTime;
	private String ESTDateTime;
	String FromISTDate;
	String FromESTDate;
	String ToISTDate;
	String ToESTDate;
	char userFlag;
	
	
	
	public char getUserFlag() {
		return userFlag;
	}
	public void setUserFlag(char userFlag) {
		this.userFlag = userFlag;
	}
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	
	public String getStages() {
		return stages;
	}
	public void setStages(String stages) {
		this.stages = stages;
	}
	
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public String getFromISTDate() {
		return FromISTDate;
	}
	public void setFromISTDate(String fromISTDate) {
		FromISTDate = fromISTDate;
	}
	
	public String getFromESTDate() {
		return FromESTDate;
	}
	public void setFromESTDate(String fromESTDate) {
		FromESTDate = fromESTDate;
	}
	
	public String getToISTDate() {
		return ToISTDate;
	}
	public void setToISTDate(String toISTDate) {
		ToISTDate = toISTDate;
	}
	
	public String getToESTDate() {
		return ToESTDate;
	}
	public void setToESTDate(String toESTDate) {
		ToESTDate = toESTDate;
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
	public String getUserTypeName() {
		return userTypeName;
	}
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getRightsType() {
		return RightsType;
	}
	public void setRightsType(String rightsType) {
		RightsType = rightsType;
	}
	/**
     * @return Returns the adminFlag.
     */
    public char getAdminFlag() {
        return adminFlag;
    }
    /**
     * @param adminFlag The adminFlag to set.
     */
    public void setAdminFlag(char adminFlag) {
        this.adminFlag = adminFlag;
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
     * @return Returns the workSpaceId.
     */
    public String getWorkSpaceId() {
        return workSpaceId;
    }
    /**
     * @param workSpaceId The workSpaceId to set.
     */
    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }
    /**
     * @return Returns the username.
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return Returns the workspacedesc.
     */
    public String getWorkspacedesc() {
        return workspacedesc;
    }
    /**
     * @param workspacedesc The workspacedesc to set.
     */
    public void setWorkspacedesc(String workspacedesc) {
        this.workspacedesc = workspacedesc;
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
     * @return Returns the usergroupname.
     */
   
	public Timestamp getLastAccessedOn() {
		return lastAccessedOn;
	}
	public void setLastAccessedOn(Timestamp lastAccessedOn) {
		this.lastAccessedOn = lastAccessedOn;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public Date getFromDt() {
		return fromDt;
	}
	public void setFromDt(Date fromDt) {
		this.fromDt = fromDt;
	}
	public Date getToDt() {
		return toDt;
	}
	public void setToDt(Date toDt) {
		this.toDt = toDt;
	}
		
	public String getRightsGivenBy() {
		return rightsGivenBy;
	}
	public void setRightsGivenBy(String rightsGivenBy) {
		this.rightsGivenBy = rightsGivenBy;
	}
	@Override
	public Object clone() throws CloneNotSupportedException 
	{			
		DTOWorkSpaceUserMst dto=new DTOWorkSpaceUserMst();
		dto.setAdminFlag(adminFlag);
		dto.setFromDt(fromDt);
		dto.setLastAccessedOn(lastAccessedOn);
		dto.setModifyBy(modifyBy);
		dto.setModifyOn(modifyOn);
		dto.setRemark(remark);
		dto.setStatusIndi(statusIndi);
		dto.setToDt(toDt);
		dto.setUserCode(userCode);
		dto.setUserGroupCode(userGroupCode);
		dto.setUserGroupName(userGroupName);
		dto.setUsername(username);
		dto.setWorkspacedesc(workspacedesc);
		dto.setWorkSpaceId(workSpaceId);	
		dto.setRightsType(RightsType);
		dto.setStages(stages);
		dto.setRoleCode(roleCode);
		dto.setMode(mode);
		return dto;
	}
}
