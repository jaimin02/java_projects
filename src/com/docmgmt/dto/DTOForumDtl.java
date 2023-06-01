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
public class DTOForumDtl implements Serializable{

    String workspaceId;
    int nodeId;
    String userName;
    String userGroupName;
    int userCode;
    int userGroupCode;
       
    String workSpaceDesc;
    String nodeName;
    
    String subjectId;         
	String subjectDesc;         
	int modifyBy;         
	Timestamp modifyOn;       
	char statusIndi;  
	String loginName;
	
	String senderName;
	String receiverName;
	String senderAddress;
	String receiverAddress;
	
	DTOWorkSpaceMst commentedWs;	
	DTOWorkSpaceNodeDetail commentedNode;	
	int receiverGroupCode;
	int receiverUserCode;
	DTOUserMst commentReceiver;
	int senderUserCode;
	DTOUserMst commentSender;
	char readFlag;
	String deletedFlag;
	Timestamp createdOn;
	String typeFlag;
	String refSubjectId;
	String nodeDisplayName;
	String userTypeName;
	String folderName;
	String forumhdrstatusindi;
	
	Timestamp forumHdrModifyOn;
	int forumHdrModifyBy;
	String rootPath;
	String fileName;
	String ResolverName;
	String ResolverFlag;
	String Uuid;
	char LockSeq;
	String ISTDateTime;
	String ESTDateTime;
	
	
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
	
	
	public char getLockSeq() {
		return LockSeq;
	}
	public void setLockSeq(char lockSeq) {
		LockSeq = lockSeq;
	}
	public String getUuid() {
		return Uuid;
	}
	public void setUuid(String uuid) {
		Uuid = uuid;
	}
	public String getResolverFlag() {
		return ResolverFlag;
	}
	public void setResolverFlag(String resolverFlag) {
		ResolverFlag = resolverFlag;
	}
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public Timestamp getForumHdrModifyOn() {
		return forumHdrModifyOn;
	}
	public void setForumHdrModifyOn(Timestamp forumHdrModifyOn) {
		this.forumHdrModifyOn = forumHdrModifyOn;
	}
	public int getForumHdrModifyBy() {
		return forumHdrModifyBy;
	}
	public void setForumHdrModifyBy(int forumHdrModifyBy) {
		this.forumHdrModifyBy = forumHdrModifyBy;
	}
	public String getForumhdrstatusindi() {
		return forumhdrstatusindi;
	}
	public void setForumhdrstatusindi(String forumhdrstatusindi) {
		this.forumhdrstatusindi = forumhdrstatusindi;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	
	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
	public String getuserTypeName() {
		return userTypeName;
	}
	
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
	public DTOWorkSpaceMst getCommentedWs() {
		return commentedWs;
	}
	public void setCommentedWs(DTOWorkSpaceMst commentedWs) {
		this.commentedWs = commentedWs;
	}
	public DTOWorkSpaceNodeDetail getCommentedNode() {
		return commentedNode;
	}
	public void setCommentedNode(DTOWorkSpaceNodeDetail commentedNode) {
		this.commentedNode = commentedNode;
	}
	public int getReceiverGroupCode() {
		return receiverGroupCode;
	}
	public void setReceiverGroupCode(int receiverGroupCode) {
		this.receiverGroupCode = receiverGroupCode;
	}
	public int getReceiverUserCode() {
		return receiverUserCode;
	}
	public void setReceiverUserCode(int receiverUserCode) {
		this.receiverUserCode = receiverUserCode;
	}
	public DTOUserMst getCommentReceiver() {
		return commentReceiver;
	}
	public void setCommentReceiver(DTOUserMst commentReceiver) {
		this.commentReceiver = commentReceiver;
	}
	public int getSenderUserCode() {
		return senderUserCode;
	}
	public void setSenderUserCode(int senderUserCode) {
		this.senderUserCode = senderUserCode;
	}
	public DTOUserMst getCommentSender() {
		return commentSender;
	}
	public void setCommentSender(DTOUserMst commentSender) {
		this.commentSender = commentSender;
	}
	public char getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(char readFlag) {
		this.readFlag = readFlag;
	}
	public String getDeletedFlag() {
		return deletedFlag;
	}
	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	public String getTypeFlag() {
		return typeFlag;
	}
	public void setTypeFlag(String typeFlag) {
		this.typeFlag = typeFlag;
	}
	public String getRefSubjectId() {
		return refSubjectId;
	}
	public void setRefSubjectId(String refSubjectId) {
		this.refSubjectId = refSubjectId;
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
     * @return Returns the modifyOn.
     */
    public Timestamp getModifyOn() {
        return modifyOn;
    }
    /**
     * @param modifyOn The modifyOn to set.
     */
    public void setModifyOn(Timestamp modifyOn) {
        this.modifyOn = modifyOn;
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
     * @return Returns the subjectDesc.
     */
    public String getSubjectDesc() {
        return subjectDesc;
    }
    /**
     * @param subjectDesc The subjectDesc to set.
     */
    public void setSubjectDesc(String subjectDesc) {
        this.subjectDesc = subjectDesc;
    }
    /**
     * @return Returns the subjectId.
     */
    public String getSubjectId() {
        return subjectId;
    }
    /**
     * @param subjectId The subjectId to set.
     */
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
    public int getNodeId() {
        return nodeId;
    }
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }
    public String getNodeName() {
        return nodeName;
    }
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
    public int getUserCode() {
        return userCode;
    }
    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }
    public int getUserGroupCode() {
        return userGroupCode;
    }
    public void setUserGroupCode(int userGroupCode) {
        this.userGroupCode = userGroupCode;
    }
    public String getUserGroupName() {
        return userGroupName;
    }
    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getWorkSpaceDesc() {
        return workSpaceDesc;
    }
    public void setWorkSpaceDesc(String workSpaceDesc) {
        this.workSpaceDesc = workSpaceDesc;
    }
    public String getWorkspaceId() {
        return workspaceId;
    }
    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }
    public String getReceiverAddress() {
        return receiverAddress;
    }
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
    public String getReceiverName() {
        return receiverName;
    }
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    public String getSenderAddress() {
        return senderAddress;
    }
    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }
    public String getSenderName() {
        return senderName;
    }
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getResolverName() {
		return ResolverName;
	}
	public void setResolverName(String resolverName) {
		ResolverName = resolverName;
	}
}
