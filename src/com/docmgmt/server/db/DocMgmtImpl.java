package com.docmgmt.server.db;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.docmgmt.dto.*;
import com.docmgmt.server.db.master.*;
import com.docmgmt.server.webinterface.entities.ADUserAuthentication;
import com.docmgmt.server.webinterface.entities.ConvertPdfToDoc;
import com.docmgmt.server.webinterface.entities.HashcodeForDocActivity;
import com.docmgmt.struts.actions.util.wordToPdf;

public class DocMgmtImpl {

	AttributeMst attributeMst;
	AttributeValueMatrix atrAttributeValueMatrix;
	AttrForIndiMst attrForIndiMst;
	UserTypeMst userTypeMst;
	LoginHistoryMst loginHistoryMst;
	UserMst userMst;
	UserGroupMst userGroupMst;
	ClientMst clientMst;
	ApplicationGroupMst applicationGrpMst;
	//TimeDateExcludeMst dateExcludeMst;
	DepartmentMst departmentMst;
	LocationMst locationMst;
	RoleMst roleMst;
	ProjectMst projectMst;
	WorkspaceMst workspaceMst;
	WorkspaceUserMst workspaceUserMst;
	WorkSpaceUserRightsMst workSpaceUserRightsMst;
	TemplateNodeAttributeMst templateNodeAttrMst;
	TemplateNodeDetail templateNodeDetail;
	TemplateNodeAttrDetail templateNodeAttrDetail;
	TemplateMst templateMst;
	WorkSpaceNodeAttribute workSpaceNodeAttribute;
	AttributeGroupMst attributeGroupMst;
	DocTypeMst docTypeMst;
	WorkspaceNodeDetail workspaceNodeDetail;
	CheckedOutFileDetails checkedOutFileDetails;
	WorkSpaceNodeHistory workSpaceNodeHistory;
	WorkSpaceNodeVersionHistory workSpaceNodeVersionHistory;
	InternalLabelMst internalLableMst;
	WorkSpaceNodeAttributeHistory workSpaceNodeAttributeHistory;
	WorkspaceNodeAttrDetail workspaceNodeAttrDetail;
	NodeStatus nodeStatus;
	STFNodeMst stfNodeMst;
	STFCategoryMst stfCategoryMst;
	STFStudyIdentifierMst stfStudyIdentifierMst;
	ForumMst forumMst;
	HotSearchDetail hotSearchDetail;
	OperationsMst operationsMst;
	RoleOperationMatrixMst roleOperationMatrixMst;
	AttributeGroupMatrix attributeGroupMatrix;
	SubmissionMst submissionMst;
	CountryMst countryMst;
	AgencyMst agencyMst;
	StageMst stageMst;
	TemplateUserMst templateUserMst;
	TemplateUserRightsMst templateUserRightsMst;
	SubmittedWorkspaceNodeDetailMst submittedWorkspaceNodeDetailMst;
	SubmissionInfoMst submissionInfoMst;
	SubmissionTypeMst submissionTypeMst;
	ApplicantContactTypeMst applicantContactTypeMst;
	TelephoneNumberTypeMst telephoneNumberTypeMst;
	ApplicationTypeMst applicationTypeMst;
	RegulatoryActivityLeadMst regulatoryactivityleadmst;
	RegulatoryActivityType regulatoryactivitytype;
	
	SequenceTypeMst sequencetypemst ;
	SequenceTypeMst_Thai sequencetypemst_thai ;
	
	SequenceDescriptionMst sequencedescriptionmst;
	SequenceDescriptionMst_CA sequencedescriptionmst_ca;
	SubmissionSubTypeMst submissionSubTypeMst;
	SupplementEffectiveDateTypeMst supplimentEffectiveDateTypeMst;
	
	SubmissionInfoEUDtl submissionInfoEUDtl;
	SubmissionInfoEU14Dtl submissionInfoEU14Dtl;
	SubmissionInfoEU20Dtl submissionInfoEU20Dtl;

	SubmissionInfoUSDtl submissionInfoUSDtl;
	SubmissionInfoUS23Dtl submissionInfoUS23Dtl;
	WorkspaceCMSMst workspaceCMSMst;

	SubmissionInfoEUSubDtl submissionInfoEUSubDtl;
	SubmissionInfoEU14SubDtl submissionInfoEU14SubDtl;
	SubmissionInfoEU20SubDtl submissionInfoEU20SubDtl;

	SubmissionInfoGCCDtl submissionInfoGCCDtl;
	SubmissionInfoGCCSubDtl submissionInfoGCCSubDtl;
	SubmissionInfoAUDtl submissionInfoAUDtl;
	SubmissionInfoZADtl submissionInfoZADtl;
	
	SubmissionInfoTHDtl submissionInfoTHDtl;
	
	static SubmissionInfoCHDtl submissionInfoCHDtl;

	XmlNodeDtl xmlNodeDtl;
	XmlWorkspaceMst xmlWorkspaceMst;
	XmlNodeAttrDtl xmlNodeAttrDtl;
	WorkspaceNodeDocHistory workspaceNodeDocHistory;
	SubmissionInfoForManualMode submissionInfoForManualMode;
	SubmissionInfoHTML submissionInfoHTML;
	NoticeMst noticeMst;
	AdvancedAttrSearch advanceAttrSearch;
	CalendarAndReminder calendarAndReminder;
	UserProfileMst userProfileMst;
	DynamicQueries dynamicQueries;
	SubmissionQueryMst submissionQueryMst;
	SubmissionQueryDtl submissionQueryDtl;

	SubmissionQueryUserDtl submissionQueryUserDtl;

	SubmissionInfoDMSDtl submissionInfoDMSDtl;
	UserDocStageComments userDocStageComments;
	WorkspaceUserAuditTrailMst workspaceUserAuditTrailMst;
	DocReleaseTrack docReleaseTrack;
	ReleaseDocMgmt releaseDocMgmt;
	WorkspaceNodeReferenceDetail workspaceNodeReferenceDetail;
	EmployeeMaster employeeMaster;
	TrainingRecordDetails trainingRecordDetails;
	TrainingScheduleDetail trainingScheduleDetail;
	TrainingAttendanceMst trainingAttendanceMst;

	PdfPublishDtl pdfPublishDtl;
	
	ManualModeSeqZipDtl manualModeSeqZipDtl;
	
	
	SubmissionInfoNeeSDtl submissionInfoNeeSDtl;
	SubmissionInfovNeeSDtl submissionInfovNeeSDtl;
	
	WorkspaceApplicationDetailMst workspaceApplicationDetailMst;
	TimeZoneMst timeZoneMst;
	wordToPdf wordToPdf;
	ConvertPdfToDoc cpdfTDoc;
	
	SmartCaptureMst smartCaptureMst;
	ADUserAuthentication adUserAuth;
	HashcodeForDocActivity hashCodeGen;

	public DocMgmtImpl() {
		attributeMst = new AttributeMst();
		atrAttributeValueMatrix = new AttributeValueMatrix();
		attrForIndiMst = new AttrForIndiMst();
		userTypeMst = new UserTypeMst();
		loginHistoryMst = new LoginHistoryMst();
		userMst = new UserMst();
		userGroupMst = new UserGroupMst();
		clientMst = new ClientMst();
		applicationGrpMst = new ApplicationGroupMst();
		//dateExcludeMst = new TimeDateExcludeMst();
		projectMst = new ProjectMst();
		locationMst = new LocationMst();
		roleMst = new RoleMst();
		departmentMst = new DepartmentMst();
		workspaceMst = new WorkspaceMst();
		workspaceUserMst = new WorkspaceUserMst();
		workSpaceUserRightsMst = new WorkSpaceUserRightsMst();
		templateMst = new TemplateMst();
		templateNodeAttrMst = new TemplateNodeAttributeMst();
		templateNodeDetail = new TemplateNodeDetail();
		templateNodeAttrDetail = new TemplateNodeAttrDetail();
		workSpaceNodeAttribute = new WorkSpaceNodeAttribute();
		attributeGroupMst = new AttributeGroupMst();
		docTypeMst = new DocTypeMst();
		workspaceNodeDetail = new WorkspaceNodeDetail();
		checkedOutFileDetails = new CheckedOutFileDetails();
		workSpaceNodeHistory = new WorkSpaceNodeHistory();
		workSpaceNodeVersionHistory = new WorkSpaceNodeVersionHistory();
		internalLableMst = new InternalLabelMst();
		workSpaceNodeAttributeHistory = new WorkSpaceNodeAttributeHistory();
		workspaceNodeAttrDetail = new WorkspaceNodeAttrDetail();
		nodeStatus = new NodeStatus();
		stfNodeMst = new STFNodeMst();
		stfCategoryMst = new STFCategoryMst();
		stfStudyIdentifierMst = new STFStudyIdentifierMst();
		forumMst = new ForumMst();
		hotSearchDetail = new HotSearchDetail();
		operationsMst = new OperationsMst();
		roleOperationMatrixMst = new RoleOperationMatrixMst();
		attributeGroupMatrix = new AttributeGroupMatrix();
		submissionMst = new SubmissionMst();
		countryMst = new CountryMst();
		agencyMst = new AgencyMst();
		stageMst = new StageMst();
		templateUserMst = new TemplateUserMst();
		templateUserRightsMst = new TemplateUserRightsMst();
		submittedWorkspaceNodeDetailMst = new SubmittedWorkspaceNodeDetailMst();
		submissionInfoMst = new SubmissionInfoMst();
		submissionTypeMst = new SubmissionTypeMst();
		applicantContactTypeMst = new ApplicantContactTypeMst();
		telephoneNumberTypeMst=new TelephoneNumberTypeMst();
		applicationTypeMst=new ApplicationTypeMst();
		regulatoryactivityleadmst=new RegulatoryActivityLeadMst();
		regulatoryactivitytype=new RegulatoryActivityType();
		sequencetypemst=new SequenceTypeMst();
		sequencetypemst_thai=new SequenceTypeMst_Thai();
		sequencedescriptionmst=new SequenceDescriptionMst();
		sequencedescriptionmst_ca=new SequenceDescriptionMst_CA();
		submissionSubTypeMst=new SubmissionSubTypeMst();
		supplimentEffectiveDateTypeMst=new SupplementEffectiveDateTypeMst();
		
		
		submissionInfoEUDtl = new SubmissionInfoEUDtl();
		submissionInfoEU14Dtl = new SubmissionInfoEU14Dtl();
		submissionInfoEU20Dtl = new SubmissionInfoEU20Dtl();

		submissionInfoUSDtl = new SubmissionInfoUSDtl();
		submissionInfoUS23Dtl = new SubmissionInfoUS23Dtl();
		workspaceCMSMst = new WorkspaceCMSMst();
		submissionInfoEUSubDtl = new SubmissionInfoEUSubDtl();
		submissionInfoEU14SubDtl = new SubmissionInfoEU14SubDtl();
		submissionInfoEU20SubDtl = new SubmissionInfoEU20SubDtl();

		xmlNodeDtl = new XmlNodeDtl();
		xmlWorkspaceMst = new XmlWorkspaceMst();
		xmlNodeAttrDtl = new XmlNodeAttrDtl();
		workspaceNodeDocHistory = new WorkspaceNodeDocHistory();
		submissionInfoForManualMode = new SubmissionInfoForManualMode();
		submissionInfoHTML = new SubmissionInfoHTML();
		noticeMst = new NoticeMst();
		advanceAttrSearch = new AdvancedAttrSearch();
		calendarAndReminder = new CalendarAndReminder();
		userProfileMst = new UserProfileMst();
		dynamicQueries = new DynamicQueries();
		submissionQueryMst = new SubmissionQueryMst();
		submissionQueryDtl = new SubmissionQueryDtl();

		submissionQueryUserDtl = new SubmissionQueryUserDtl();

		submissionInfoDMSDtl = new SubmissionInfoDMSDtl();
		userDocStageComments = new UserDocStageComments();
		workspaceUserAuditTrailMst = new WorkspaceUserAuditTrailMst();
		docReleaseTrack = new DocReleaseTrack();
		releaseDocMgmt = new ReleaseDocMgmt();
		workspaceNodeReferenceDetail = new WorkspaceNodeReferenceDetail();
		employeeMaster = new EmployeeMaster();
		trainingRecordDetails = new TrainingRecordDetails();
		trainingScheduleDetail = new TrainingScheduleDetail();
		trainingAttendanceMst = new TrainingAttendanceMst();

		submissionInfoGCCDtl = new SubmissionInfoGCCDtl();
		submissionInfoGCCSubDtl = new SubmissionInfoGCCSubDtl();

		submissionInfoZADtl = new SubmissionInfoZADtl();
		
	submissionInfoAUDtl=new SubmissionInfoAUDtl();
	
	submissionInfoTHDtl=new SubmissionInfoTHDtl();
		
		submissionInfoCHDtl = new SubmissionInfoCHDtl();
		pdfPublishDtl = new PdfPublishDtl();
		
		//Advance Manual
		manualModeSeqZipDtl = new ManualModeSeqZipDtl();
		submissionInfoNeeSDtl=new SubmissionInfoNeeSDtl();
		submissionInfovNeeSDtl=new SubmissionInfovNeeSDtl();
		
		workspaceApplicationDetailMst=new WorkspaceApplicationDetailMst();
		timeZoneMst = new TimeZoneMst();
		wordToPdf=new wordToPdf();
		cpdfTDoc = new ConvertPdfToDoc();
		
		smartCaptureMst=new SmartCaptureMst();
		adUserAuth=new ADUserAuthentication();
		hashCodeGen=new HashcodeForDocActivity();

	}

	// ////////////////////Attribute master ///////////////////

	public Vector<DTOAttributeMst> getAllAttrib() {
		return attributeMst.getAllAttrib();
	}

	public Vector<DTOAttributeValueMatrix> getAttributeValueByAttrId(int attrId) {
		return attributeMst.getAttributeValueByAttrId(attrId);
	}

	public DTOAttributeMst getAttribute(int Id) {
		return attributeMst.getAttribute(Id);
	}

	public Vector<DTOAttributeMst> getAttributeDetailsByAttrName(String AttrName) {
		return attributeMst.getAttributeDetailsByAttrName(AttrName);
	}

	public Vector<DTOAttrForIndiMst> getAllAtrrForIndi() {
		return attributeMst.getAllAtrrForIndi();
	}

	public int InsertAttribute(DTOAttributeMst attribmst, int uid) {
		return attributeMst.InsertAttribute(attribmst, uid);
	}

	public void insertAttributeMatrix(DTOAttributeValueMatrix attrmatrix,
			int uid) {
		attributeMst.insertAttributeMatrix(attrmatrix, uid);
	}

	public void editAttributeMatrix(DTOAttributeValueMatrix attrmatrix, int uid) {
		attributeMst.editAttributeMatrix(attrmatrix, uid);
	}

	public Vector<DTOAttributeMst> getAllAttributeForAttrDtl() {
		return attributeMst.getAllAttributeForAttrDtl();
	}

	public Vector<DTOAttributeMst> getAllAttributeForSTF() {
		return attributeMst.getAllAttributeForSTF();
	}

	public Vector<DTOAttributeMst> getAttrDetailById(int attrId) {
		return attributeMst.getAttrDetailById(attrId);
	}

	public Vector<DTOAttributeMst> getAttrDetailForSearch() {
		return attributeMst.getAttrDetailForSearch();
	}
	public Vector<DTOAttributeMst> getSelectedAttrDetail() {
		return attributeMst.getSelectedAttrDetail();
	}

	public Vector<DTOAttributeValueMatrix> getAttributeDetailByType(
			String attrForIndi) {
		return attributeMst.getAttributeDetailByType(attrForIndi);
	}

	public Vector<DTOAttributeMst> getAttrForAttrGroupBySpecifiedAttrType(
			String attrType) {
		return attributeMst.getAttrForAttrGroupBySpecifiedAttrType(attrType);
	}

	public ArrayList<DTOAttributeMst> getAttributesFullDetail(
			ArrayList<Integer> attrId) {
		return attributeMst.getAttributesFullDetail(attrId);
	}

	// ///////////////////Attribute value matrix////////////////////

	public Vector<DTOAttributeValueMatrix> getAttributeValueByUserType(
			String vWsId, int iNodeId, String userTypeCode) {
		return atrAttributeValueMatrix.getAttributeValueByUserType(vWsId,
				iNodeId, userTypeCode);
	}

	public Vector<DTOAttributeValueMatrix> getAttributeValuesFromMatrix(
			String attributeName) {
		return atrAttributeValueMatrix
				.getAttributeValuesFromMatrix(attributeName);
	}

	public String getAttrValueByAttrValueId(int attrValueId) {
		return atrAttributeValueMatrix.getAttrValueByAttrValueId(attrValueId);
	}

	// //////////////User Type Master////////////////////////

	public Vector<DTOUserTypeMst> getAllUserType() {
		return userTypeMst.getAllUserType();
	}

	// ////////////////////////user Master //////////////////
	public DTOUserMst getUserByCode(int usercd) {
		return userMst.getUserByCode(usercd);
	}
	
	public DTOUserMst getUserNameByCode(String loginName,int userGroupCode) {
		return userMst.getUserNameByCode(loginName,userGroupCode);
	}
	
	public Vector<DTOUserMst> getUserProfileByUserName(String loginUserName) {
		return userMst.getUserProfileByUserName(loginUserName);
	}
	
	public Vector<DTOUserMst> getUserProfileByAdUser(String loginUserName) {
		return userMst.getUserProfileByAdUser(loginUserName);
	}
	
	public Vector<DTOUserMst> getUserDetailByLoginName(String loginUserName) {
		return userMst.getUserDetailByLoginName(loginUserName);
	}
	public Vector<DTOUserMst> getUserDetailByLoginNameForResendVerificationMail(String loginUserName) {
		return userMst.getUserDetailByLoginNameForResendVerificationMail(loginUserName);
	}
	public Vector<DTOUserMst> getUserDetailByLoginNameId(String loginUserName) {
		return userMst.getUserDetailByLoginNameId(loginUserName);
	}
	
	public Vector<DTOUserMst> getUserLoginName(String loginUserName) {
		return userMst.getUserLoginName(loginUserName);
	}
	
	public String getUserTypeName(int usercode) {
		return userMst.getUserTypeName(usercode);
	}
	public String getUserGroupClientCode(int usergroupcode) {
		return userMst.getUserGroupClientCode(usergroupcode);
	}
	public String getDeptCode(int usercode,int usergroupcode) {
		return userMst.getDeptCode(usercode,usergroupcode);
	}
	public String generateUserTyeBasedMenu(String usertypecode) {
		return userMst.generateUserTyeBasedMenu(usertypecode);
	}

	public Vector<DTOUserMst> getAllUserDetail() {
		return userMst.getAllUserDetail();
	}
	
	public Vector<DTOUserMst> getAllUserDetailForProjects() {
		return userMst.getAllUserDetailForProjects();
	}
	
	public Vector<DTOUserMst> chekIsAdUser(String loginName) {
		return userMst.chekIsAdUser(loginName);
	}
	
	public Vector<DTOUserMst> getUserDetailByName(String loginName) {
		return userMst.getUserDetailByName(loginName);
	}
	
	public Vector<DTOUserMst> getUserDetailByDistinct() {
		return userMst.getUserDetailByDistinct();
	}
	
	public Vector<DTOUserMst> getAllUserDetailForITUser(String loginName) {
		return userMst.getAllUserDetailForITUser(loginName);
	}

	public Vector<DTOUserMst> getuserDetailsById(int[] userCodes) {
		return userMst.getuserDetailsById(userCodes);
	}
	
	public int checkLoginName(String loginName,int userGroupName) {
		return userMst.checkLoginName(loginName,userGroupName);
	}
	public int checkLoginId(String loginName, int userGroupName) {
		return userMst.checkLoginId(loginName,userGroupName);
	}
	public int checkLoginNameForAddUser(String loginName) {
		return userMst.checkLoginNameForAddUser(loginName);
	}
	public int checkLoginIdForAddUser(String loginName) {
		return userMst.checkLoginIdForAddUser(loginName);
	}

	public void UserMstOp(DTOUserMst dto, int Mode, boolean isrevert) {
		userMst.UserMstOp(dto, Mode, isrevert);
	}

	public Vector<DTOUserMst> getuserDetailsByUserGrp(int userGroupCode) {
		return userMst.getuserDetailsByUserGrp(userGroupCode);
	}
	public Vector<DTOUserMst> getuserDetailsByUserGrpForDocSign(int userGroupCode) {
		return userMst.getuserDetailsByUserGrpForDocSign(userGroupCode);
	}
	
	public Vector<DTOUserMst> getuserDetailsByusermst(int userGroupCode,String WorkspaceId) {
		return userMst.getuserDetailsByusermst(userGroupCode,WorkspaceId);
	}
	public Vector<DTOUserMst> getWAUserGrpdetails(int userId) {
		return userMst.getWAUserGrpdetails(userId);
	}
	public DTOUserMst getUserInfo(int userCode) {
		return userMst.getUserInfo(userCode);
	}

	/*public int isValidUser(String username, String password, int userGroupCode) {
		return userMst.isValidUser(username, password,userGroupCode);
	}*/
	
	////This is replicated method created for Disable the profile selection
	public int isValidUser(String username, String password) {
		return userMst.isValidUser(username, password);
	}
	
	public int isValidUserForVerification(String username) {
		return userMst.isValidUserForVerification(username);
	}
	
	public int isValidUserForVerification(String username,String password) {
		return userMst.isValidUserForVerification(username,password);
	}
	
	public int isValidUserThroughUserName(String username, String password) {
		return userMst.isValidUserThroughUserName(username, password);
	}
	
	public Vector<DTOUserMst> getUserDetailHistory(int userCode) {
		return userMst.getUserDetailHistory(userCode);
	}
	

	public Vector<DTOUserMst> getUserLoginDetail(String searchMode,String userTypeCode,String fromSubmissionDate,String toSubmissionDate) {
		return userMst.getUserLoginDetail(searchMode,userTypeCode,fromSubmissionDate,toSubmissionDate);
	}
	
	
	public Vector<DTOUserMst> getUserDetail(String usrName) {
		return userMst.getUserDetail(usrName);
	}
	
	public Vector<DTOUserMst> getUserDetailByUserCode(String usrName,int uCode) {
		return userMst.getUserDetailByUserCode(usrName,uCode);
	}
	
	public Vector<DTOUserMst> getUserDetailByLoginId(String usrName) {
		return userMst.getUserDetailByLoginId(usrName);
	}
	
	public Vector<DTOUserMst> getUserDetailToShow(String usrName) {
		return userMst.getUserDetailToShow(usrName);
	}
	
	public Vector<DTOUserMst> getUserLoaction(int userCode) {
		return userMst.getUserLoaction(userCode);
	}
	
	public void updateRole(DTOUserMst dto) {
		this.userMst.updateRole(dto);
	}
	
	// ////////////////UserProfileMst///////////////////
	public void InserUserProfile(DTOUserProfile dtoUserProfile, int DataOpMode) {
		userProfileMst.InserUserProfile(dtoUserProfile, DataOpMode);
	}

	public ArrayList<DTOUserProfile> getUserProfileDetails(int UserCode) {
		return userProfileMst.getUserProfileDetails(UserCode);
	}

	public ArrayList<DTOUserProfile> getUserForAlert(Character profiletype) {
		return userProfileMst.getUserForAlert(profiletype);
	}

	public ArrayList<String> getUserDetailForAlert(Character profiletype,
			int usercode) {
		return userProfileMst.getUserDetailForAlert(profiletype, usercode);
	}

	// ////////////////LoginHistoryMaster///////////////////

	public String LoginHistoryMstOp(DTOUserMst userMst, int Mode) {
		return loginHistoryMst.LoginHistoryMstOp(userMst, Mode);
	}
	
	public String checkLoginHistory(int userCode) {                  
		return loginHistoryMst.checkLoginHistory(userCode);
	}
	public ArrayList<DTOLoginHistory> getLastLoginTime(int userCode){
		return loginHistoryMst.getLastLoginTime(userCode);
	}
	

	// /////////////user Group Master////////////////

	public Vector<DTOUserGroupMst> getAllUserGroupByUserType(String userType) {
		return userGroupMst.getAllUserGroupByUserType(userType);
	}
	public Vector<DTOUserGroupMst> getUserGroupDetails() {
		return userGroupMst.getUserGroupDetails();
	}
	public Vector<DTOUserGroupMst> getAllUserGroupByUserTypeandClientCode(String userType,String clientCode) {
		return userGroupMst.getAllUserGroupByUserTypeandClientCode(userType,clientCode);
	}
	public Vector<DTOUserGroupMst> getUserGroupDtl() {
		return userGroupMst.getUserGroupDtl();
	}

	public void UserGroupMstOp(DTOUserGroupMst dto, int Mode, boolean isrevert) {
		userGroupMst.UserGroupMstOp(dto, Mode, isrevert);
	}

	public DTOUserGroupMst getUserGroupDtlByGroupId(String userGroupId) {
		return userGroupMst.getUserGroupDtlByGroupId(userGroupId);
	}

	public Vector<DTOUserGroupMst> getAllUserGroups() {
		return userGroupMst.getAllUserGroups();
	}
	public Vector<DTOUserGroupMst> getAllUserGroupsForDocSign() {
		return userGroupMst.getAllUserGroupsForDocSign();
	}
	public Vector<DTOUserGroupMst> getAllUserGroupsByClientCode(String clientcode) {
		return userGroupMst.getAllUserGroupsByClientCode(clientcode);
	}
	public Vector<DTOUserGroupMst> getAllUserGroupsByDeptCode(String deptcode) {
		return userGroupMst.getAllUserGroupsByDeptCode(deptcode);
	}
	public String getUserType(int userGroupId) {
		return userGroupMst.getUserType(userGroupId);
	}
	
	// /////////////////////role\\\\\\\\\\\\\\\\\\\\\\\\\\\\

		public Vector<DTORoleMst> getRoleDetail() {
			return roleMst.getRoleDtl();
		}
		
		public boolean roleNameExist(String clientName) {
			return roleMst.roleNameExist(clientName);
		}
		
		public DTORoleMst getRoleInfo(String ClientCode) {
			return roleMst.getRoleInfo(ClientCode);
		}
		
		public Vector<DTORoleMst> getRoleDetailHistory(String clientCode) {
			return roleMst.getRoleDetailHistory(clientCode);
		}
		public void RoleMstOp(DTORoleMst dto, int Mode, boolean isrevert) {
			roleMst.RoleMstOp(dto, Mode, isrevert);
		}
	
	// /////////////////////client\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	public Vector<DTOClientMst> getClientDetail() {
		return clientMst.getClientDetail();
	}

	public DTOClientMst getClientInfo(String ClientCode) {
		return clientMst.getClientInfo(ClientCode);
	}

	public void ClientMstOp(DTOClientMst dto, int Mode, boolean isrevert) {
		clientMst.ClientMstOp(dto, Mode, isrevert);
	}

	public boolean clientNameExist(String clientName) {
		return clientMst.clientNameExist(clientName);
	}
	public String getClientName(String ClientCode) {
		return clientMst.getClientName(ClientCode);
	}
	
	public Vector<DTOClientMst> getClientDetailHistory(String clientCode) {
		return clientMst.getClientDetailHistory(clientCode);
	}
	
	public  ArrayList<String> getPQSHeader(String clientCode) {
		return clientMst.getPQSHeader(clientCode);
	}
	
	public  ArrayList<String> getTracebilityMatrixHeaders(String clientCode,String Automated_Doc_Type) {
		return clientMst.getTracebilityMatrixHeaders(clientCode,Automated_Doc_Type);
	}
	
	public  ArrayList<String> getTracebilityMatrixHeaders(String clientCode) {
		return clientMst.getTracebilityMatrixHeaders(clientCode);
	}
	
	public  ArrayList<String> getTracebilityMatrixHeadersForTM(String clientCode) {
		return clientMst.getTracebilityMatrixHeadersForTM(clientCode);
	}
	
	// /////////////////////Application Group\\\\\\\\\\\\\\\\\\\\\\\\\\\\

		public Vector<DTOApplicationGroupMst> getApplicationHostingDetail() {
			return applicationGrpMst.getApplicationHostingDetail();
		}
		
		public Vector<DTOApplicationGroupMst> getApplicationCategoryDetail() {
			return applicationGrpMst.getApplicationCategoryDetail();
		}
		
		public Vector<DTOApplicationGroupMst> getApplicationDetail() {
			return applicationGrpMst.getApplicationDetail();
		}
		public Vector<DTOApplicationGroupMst> getApplicationDetailForProject() {
			return applicationGrpMst.getApplicationDetailForProject();
		}
		
		public DTOApplicationGroupMst getApplicationDetail(String applicationCode) {
			return applicationGrpMst.getApplicationDetail(applicationCode);
		}
		
		public int getMaxTranForApplicationAttachment(String applicationCode) {
			return applicationGrpMst.getMaxTranForApplicationAttachment(applicationCode);
		}
		
		public boolean applicationNameExist(String clientName) {
			return applicationGrpMst.applicationNameExist(clientName);
		}
		
		public void ApplicationMstOp(DTOApplicationGroupMst dto, int Mode, boolean isrevert) {
			applicationGrpMst.ApplicationMstOp(dto, Mode, isrevert);
		}

		public Vector<DTOApplicationGroupMst> getApplicationDetailHistory(String applicationCode) {
			return applicationGrpMst.getApplicationDetailHistory(applicationCode);
		}
		
		public String ApplicationAttahmentMstOp(DTOApplicationGroupMst dto, int Mode, boolean isrevert) {
			return applicationGrpMst.ApplicationAttahmentMstOp(dto, Mode, isrevert);
		}
		
		public Vector getAttachmentHistory(String applicationCode) {
			return applicationGrpMst.getAttachmentHistory(applicationCode);
		}
		
		public DTOApplicationGroupMst getApplicationDetailByCodeAndTranNo(String applicationCode,int tranNo) {
			return applicationGrpMst.getApplicationDetailByCodeAndTranNo(applicationCode,tranNo);
		}
		
		public DTOApplicationGroupMst getApplicationAttachmentDetailByTranNo(int tranNo) {
			return applicationGrpMst.getApplicationAttachmentDetailByTranNo(tranNo);
		}
		
		public boolean attachmentNameExist(String workspaceId, String folderName) {
			return applicationGrpMst.attachmentNameExist(workspaceId,folderName);
		}
		
		public void deleteApplicationAttachment(String doumentName,int tranNo,String remark) {
			applicationGrpMst.deleteApplicationAttachment(doumentName,tranNo,remark);
		}

		
		/*public DTOClientMst getClientInfo(String ClientCode) {
			return clientMst.getClientInfo(ClientCode);
		}

		

		
		public String getClientName(String ClientCode) {
			return clientMst.getClientName(ClientCode);
		}
		
		
		public  ArrayList<String> getPQSHeader(String clientCode) {
			return clientMst.getPQSHeader(clientCode);
		}
		
		public  ArrayList<String> getTracebilityMatrixHeaders(String clientCode,String Automated_Doc_Type) {
			return clientMst.getTracebilityMatrixHeaders(clientCode,Automated_Doc_Type);
		}
		
		public  ArrayList<String> getTracebilityMatrixHeaders(String clientCode) {
			return clientMst.getTracebilityMatrixHeaders(clientCode);
		}
		
		public  ArrayList<String> getTracebilityMatrixHeadersForTM(String clientCode) {
			return clientMst.getTracebilityMatrixHeadersForTM(clientCode);
		}*/
		
	
	// ////////////////////////Timeline Date Exclusion Master ////////////////
	
	/*public Vector<DTOTimelineDateExclusion> getExcludedDateDetail() {
		return dateExcludeMst.getExcludedDateDetail();
	}
	
	public boolean dateExist(String clientName) {
		return dateExcludeMst.dateExist(clientName);
	}
	
	public void DateMstOp(DTOTimelineDateExclusion dto, int Mode, boolean isrevert) throws ParseException {
		dateExcludeMst.DateMstOp(dto, Mode, isrevert);
	}
	
	public Vector<DTOTimelineDateExclusion> getdateHistory(String date) {
		return dateExcludeMst.getdateHistory(date);
	}*/
	
	// ////////////////////////Timeline Date Exclusion Master Ends ////////////////
	
	
	// ////////////////////////Role Master////////////////
	
	public Vector<DTORoleMst> getRoleDtl() {
		return roleMst.getRoleDtl();	
	}
	public Vector<DTORoleMst> getRoleDtl(String roleCode) {
		return roleMst.getRoleDtl(roleCode);
	}
	public String getRoleName(String roleCode) {
		return roleMst.getRoleName(roleCode);
	}
	// ////////////////////////Location Master////////////////

	public Vector<DTOLocationMst> getLocationDtl() {
		return locationMst.getLocationDtl();
		
	}

	public void LocationMstOp(DTOLocationMst dto, int Mode, boolean isrevert) {
		locationMst.LocationMstOp(dto, Mode, isrevert);
	}

	public DTOLocationMst getLocationInfo(String LocationCode) {
		return locationMst.getLocationInfo(LocationCode);
	}

	public boolean locationNameExist(String locationName) {
		return locationMst.locationNameExist(locationName);
	}

	// ///////////////////////Project Master /////////////////////

	public Vector getProjectType() {
		return projectMst.getProjectType();
	}
	
	public Vector getProjectTypeForSize() {
		return projectMst.getProjectTypeForSize();
	}
	
	public Vector getProjectTypeForESignDoc() {
		return projectMst.getProjectTypeForESignDoc();
	}
	
	public Vector getProjectTypeForESign() {
		return projectMst.getProjectTypeForESign();
	}

	public DTOProjectMst getProjectInfo(String ProjectCode) {
		return projectMst.getProjectInfo(ProjectCode);
	}

	public void ProjectMstOp(DTOProjectMst dto, int Mode, boolean isrevert) {
		projectMst.ProjectMstOp(dto, Mode, isrevert);
	}

	public boolean projectNameExist(String projectName) {
		return projectMst.projectNameExist(projectName);
	}

	// /////////////////////Department Master///////////////////

	public Vector<DTODepartmentMst> getDepartmentDetail() {
		return departmentMst.getDepartmentDetail();
	}
	public Vector<DTODepartmentMst> getDepartmentDetailByDeptCode(String deptcode)  {
		return departmentMst.getDepartmentDetailByDeptCode(deptcode);
	}
	
	public Vector<DTODepartmentMst> getDepartmentDetailForEditProject(String deptcode)  {
		return departmentMst.getDepartmentDetailForEditProject(deptcode);
	}
	public DTODepartmentMst getDepartmentInfo(String DeptCode) {
		return departmentMst.getDepartmentInfo(DeptCode);
	}

	public void DepartmentMstOp(DTODepartmentMst dto, int Mode, boolean isrevert) {
		departmentMst.DepartmentMstOp(dto, Mode, isrevert);
	}

	public boolean deptNameExist(String deptName) {
		return departmentMst.deptNameExist(deptName);
	}
	
	public Vector<DTODepartmentMst> getDeptDetailHistory(String depttCode) {
		return departmentMst.getDeptDetailHistory(depttCode);
	}

	// /////////////////WorkSpace Master///////////////////////////

	public String getDossierStatusAttribute(String wsid) {
		return workspaceMst.getDossierStatusAttribute(wsid);
	}

	public boolean checkDossierStatusAttribute() {
		return workspaceMst.checkDossierStatusAttribute();
	}

	public Vector<DTOWorkSpaceMst> searchWorkspace(
			DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst) {
		return workspaceMst.searchWorkspace(workSpaceMst, userMst);
	}
	
	public Vector<DTOWorkSpaceMst> searchWorkspaceList(
			DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst) {
		return workspaceMst.searchWorkspaceList(workSpaceMst, userMst);
	}
	
	public Vector<DTOWorkSpaceMst> searchWorkspaceFromUserOnProject(
			DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst) {
		return workspaceMst.searchWorkspaceFromUserOnProject(workSpaceMst, userMst);
	}
	
	public Vector<DTOWorkSpaceMst> searchWorkspaceFromUserOnProjectList(
			DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst) {
		return workspaceMst.searchWorkspaceFromUserOnProjectList(workSpaceMst, userMst);
	}
	
	public Vector<DTOWorkSpaceMst> searchWorkspaceForESignDoc(
			DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst) {
		return workspaceMst.searchWorkspaceForESignDoc(workSpaceMst, userMst);
	}
	
	public Vector<DTOWorkSpaceMst> searchWorkspaceForESignDocList(
			DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst) {
		return workspaceMst.searchWorkspaceForESignDocList(workSpaceMst, userMst);
	}

	public Vector<DTOWorkSpaceMst> searchWorkspaceByProjectType(
			DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst,
			ArrayList<Character> projectTypeList) {
		return workspaceMst.searchWorkspaceByProjectType(workSpaceMst, userMst,
				projectTypeList);
	}

	public Vector<DTOWorkSpaceMst> getUserWorkspace(int userGroupCode,
			int userCode) {
		return workspaceMst.getUserWorkspace(userGroupCode, userCode);
	}
	
	public Vector<DTOWorkSpaceMst> getUserWorkspaceList(int userGroupCode,
			int userCode) {
		return workspaceMst.getUserWorkspaceList(userGroupCode, userCode);
	}
	public Vector<DTOWorkSpaceMst> getUserWorkspaceForSerachProject(int userGroupCode,
			int userCode) {
		return workspaceMst.getUserWorkspaceForSerachProject(userGroupCode, userCode);
	}
	public Vector<DTOWorkSpaceMst> getUserWorkspaceForSerachProjectList(int userGroupCode,
			int userCode) {
		return workspaceMst.getUserWorkspaceForSerachProjectList(userGroupCode, userCode);
	}
	
	public Vector<DTOWorkSpaceMst> getUserWorkspaceListForRightsReplication(int userGroupCode,int userCode,String wsId) {
		return workspaceMst.getUserWorkspaceListForRightsReplication(userGroupCode, userCode,wsId);
	}
	
	public Vector<DTOWorkSpaceMst> getUserWorkspaceForSerachProjectDropdown(int userGroupCode,
			int userCode) {
		return workspaceMst.getUserWorkspaceForSerachProjectDropdown(userGroupCode, userCode);
	}
	public Vector<DTOWorkSpaceMst> getUserWorkspaceForSerachProjectDropdownWSList(int userGroupCode,
			int userCode) {
		return workspaceMst.getUserWorkspaceForSerachProjectDropdownWSList(userGroupCode, userCode);
	}
	public Vector<DTOWorkSpaceMst> getTimelineUserWorkspace(int userGroupCode,
			int userCode) {
		return workspaceMst.getTimelineUserWorkspace(userGroupCode, userCode);
	}
	public Vector<Object[]> getWorkspaceDesc(String wsId) {
		return workspaceMst.getWorkspaceDesc(wsId);
	}
	
	public Vector<Object[]> getWorkspaceDescList(String wsId) {
		return workspaceMst.getWorkspaceDescList(wsId);
	}

	public DTOWorkSpaceMst getWorkSpaceDetail(String wsId) {
		return workspaceMst.getWorkSpaceDetail(wsId);
	}
	
	public DTOWorkSpaceMst getWorkSpaceDetailWSList(String wsId) {
		return workspaceMst.getWorkSpaceDetailWSList(wsId);
	}

	public int getTotalLeafNode(String wsId) {
		return workspaceMst.getTotalLeafNode(wsId);
	}
	
	public int getTotalLeafNodeCount(String wsId) {
		return workspaceMst.getTotalLeafNodeCount(wsId);
	}
	
	public int gettimeLineTotalLeafCount(String wsId) {
		return workspaceMst.gettimeLineTotalLeafCount(wsId);
	}

	public int[] getAllNodeStatusCount(String wsId) {
		return workspaceMst.getAllNodeStatusCount(wsId);

	}

	public ArrayList<DTOWorkSpaceMst> getWorkSpaceDetail(ArrayList<String> wsId) {
		return workspaceMst.getWorkSpaceDetail(wsId);
	}

	public boolean workspaceNameExist(String WorkspaceDesc) {
		return workspaceMst.workspaceNameExist(WorkspaceDesc);
	}

	public boolean AddUpdateWorkspace(DTOWorkSpaceMst dto, int userCode,
			String typeSelection, String templateId, int Mode) {
		return workspaceMst.AddUpdateWorkspace(dto, userCode, typeSelection,
				templateId, Mode);
	}

	public synchronized int getNewTranNo(String wsId) {
		return workspaceMst.getNewTranNo(wsId);
	}
	
	public synchronized int getNewTranNoForAttachment(String wsId,int nodeId) {
		return workspaceMst.getNewTranNoForAttachment(wsId,nodeId);
	}
	
	public synchronized int getNewTranNoForTemplate(String wsId) {
		return workspaceMst.getNewTranNoForTemplate(wsId);
	}
	
	public synchronized int getNewTranNoForUserSignature(int userCode) {
		return workspaceMst.getNewTranNoForUserSignature(userCode);
	}

	public boolean updatePublishedVersion(String wsId) {
		return workspaceMst.updatePublishedVersion(wsId);
	}
	
	public boolean updatePublishedVersionForNeeS(String wsId,String lastPublishedVersion) {
		return workspaceMst.updatePublishedVersionForNeeS(wsId,lastPublishedVersion);
	}
	public boolean updatePublishedVersionForvNeeS(String wsId,String lastPublishedVersion) {
		return workspaceMst.updatePublishedVersionForvNeeS(wsId,lastPublishedVersion);
	}

	public Vector<Object[]> getFolderByWorkSpaceId(String workSpaceId) {
		return workspaceMst.getFolderByWorkSpaceId(workSpaceId);
	}

	public Vector<DTOWorkSpaceMst> getWorkspaceDetailByProjectType() {
		return workspaceMst.getWorkspaceDetailByProjectType();
	}

	public Vector<DTOWorkSpaceMst> getWorkspaceDetailByLocDept(
			String locationCode, String deptCode) {
		return workspaceMst.getWorkspaceDetailByLocDept(locationCode, deptCode);
	}

	public Vector<DTOWorkSpaceMst> getDocumentPropertiesForReport(
			DTOWorkSpaceMst dto) {
		return workspaceMst.getDocumentPropertiesForReport(dto);
	}

	public Vector<DTOWorkSpaceMst> getAllWorkspaceForChangeStatus() {
		return workspaceMst.getAllWorkspaceForChangeStatus();
	}

	public Boolean insertIntoWorkSpaceForSaveAsProject(DTOWorkSpaceMst dto) {
		return workspaceMst.insertIntoWorkSpaceForSaveAsProject(dto);
	}

	public String getWorkspaceID(String WorkspaceDesc) {
		return workspaceMst.getWorkspaceID(WorkspaceDesc);
	}
	
	public String getWorkspaceName(String WorkspaceId) {
		return workspaceMst.getWorkspaceName(WorkspaceId);
	}

	public void deleteWorkspace(String workspaceId) {
		workspaceMst.deleteWorkspace(workspaceId);
	}

	public Vector<DTOWorkSpaceUserMst> getRecentWorkspacedetails(String userCode) {
		// TODO Auto-generated method stub
		return workspaceUserMst.getRecentWorkspacedetails(userCode);
	}
	
	public Vector<DTOWorkSpaceUserMst> getUserWiseWS(String userCode) {
		// TODO Auto-generated method stub
		return workspaceUserMst.getUserWiseWS(userCode);
	}
	
	public Vector<DTOWorkSpaceMst> getUserWorkspaceDetail(int userGroupCode, int userCode) {
		// TODO Auto-generated method stub
		return workspaceUserMst.getUserWorkspaceDetail(userGroupCode,userCode);
	}
	
	public String getRoleCode(String wsId, int userCode) {
		return workspaceUserMst.getRoleCode(wsId,userCode);
	}
	
	public String getRoleCodeFromUserMst(int userCode, int userGroupCode) {
		return workspaceUserMst.getRoleCodeFromUserMst(userCode,userGroupCode);
	}
	
	public String getRoleCodeFromWSUserRightsMst(String wsId, int nodeId, int userCode) {
		return workspaceUserMst.getRoleCodeFromWSUserRightsMst(wsId, nodeId, userCode);
	}

	public void updateWorkspaceMstAccessedDate(String wsid,int ugrpcode,int ucode) {
		workspaceMst.updateWorkspaceMstAccessedDate(wsid,ugrpcode,ucode);
	}
	
	public Vector<DTOWorkSpaceUserMst> userAcknowledgement(int userCode){
		return workspaceUserMst.userAcknowledgement(userCode);
	}
	
	public String updateUserAcknowledgement(DTOWorkSpaceNodeHistory dto) {
		return workspaceMst.updateUserAcknowledgement(dto);
	}
	/*
	 * Method Added By : Ashmak Shah Added On : 12th may 2009
	 */
	public void copyPublishedStructureToWorkspace(String appNo,
			String workspaceid, int userId) {
		workspaceMst.copyPublishedStructureToWorkspace(appNo, workspaceid,
				userId);
	}

	/*
	 * Method Added By : Ashmak Shah Added On : 12th may 2009
	 */
	public Vector<String> getImportStructureDetail() {
		return workspaceMst.getImportStructureDetail();
	}

	public String insertWorkspaceMst(DTOWorkSpaceMst dto) {
		return workspaceMst.insertWorkspaceMst(dto);
	}

	public String insertWorkspaceMst(DTOWorkSpaceMst dto, int mode) {
		return workspaceMst.insertWorkspaceMst(dto, mode);
	}

	public ArrayList<DTOWorkSpaceMst> getWorkspaceDtByProjPublishType(
			int userCode, int userGroupCode, char projPublishType) {
		return workspaceMst.getWorkspaceDtByProjPublishType(userCode,
				userGroupCode, projPublishType);
	}

	public ArrayList<DTOWorkSpaceMst> getWorkSpaceDetailByTemplate(
			String templateId) {
		return workspaceMst.getWorkSpaceDetailByTemplate(templateId);
	}

	public DTOWorkSpaceMst getWorkSpaceDetailByWorkspaceId(String workspaceId) {
		return workspaceMst.getWorkSpaceDetailByWorkspaceId(workspaceId);
	}
		
	public Vector getTemplateChildNodeByParentForPublishForM1(int parentNodeid,String templateId)
	{
		return  templateNodeDetail.getTemplateChildNodeByParentForPublishForM1(parentNodeid,templateId);
	}
	
	public int isTemplateLeafNode(int nodeid,String templateId)
	{
		return  templateNodeDetail.isLeafNodes(templateId,nodeid);
	}

	// /////////////////////////DocTypeMaster\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	public Vector<DTODocTypeMst> getDocTypeDetail() {
		return docTypeMst.getDocTypeDetail();
	}
	
	public String  getDocTypeName(String docTypeCode) {
		return docTypeMst.getDocTypeName(docTypeCode);
	}

	// ////////////////////////AttributeGroupMst
	// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	public Vector<DTOAttributeGroupMst> getAllAttributeGroup() {
		return attributeGroupMst.getAllAttributeGroup();
	}

	public DTOAttributeGroupMst getAttributeGroupById(String attrGroupId) {
		return attributeGroupMst.getAttributeGroupById(attrGroupId);
	}

	public void InsertAttributeGroupMst(DTOAttributeGroupMst dto, int Mode) {
		attributeGroupMst.InsertAttributeGroupMst(dto, Mode);
	}

	// //////////workspace Node Detail master///////////////////////////

	public int getmaxNodeId(String vWorkSpaceId) {
		return workspaceNodeDetail.getmaxNodeId(vWorkSpaceId);
	}

	public void updateNodeDisplayNameAccToEctdAttribute(
			DTOWorkSpaceNodeDetail obj, String AttributeValue) {
		workspaceNodeDetail.updateNodeDisplayNameAccToEctdAttribute(obj,
				AttributeValue);
	}

	public void CopyAndPasteWorkSpace(String sourceWorkspaceId,
			int sourceNodeId, String destWorkspaceId, int destNodeId,
			int userCode, String status) {
		workspaceNodeDetail.CopyAndPasteWorkSpace(sourceWorkspaceId,
				sourceNodeId, destWorkspaceId, destNodeId, userCode, status);
	}

	public Vector<DTOWorkSpaceNodeDetail> getNodeDetail(String wsId, int nodeId) {
		return workspaceNodeDetail.getNodeDetail(wsId, nodeId);
	}
	public Vector<DTOWorkSpaceNodeDetail> getTemplateNodeDetail(String wsId, int nodeId) {
		return workspaceNodeDetail.getTemplateNodeDetail(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getNodeDetailForNodeCountForFailedSctipt(String wsId, int nodeId,String nDisplayName) throws ClassNotFoundException {
		return workspaceNodeDetail.getNodeDetailForNodeCountForFailedSctipt(wsId, nodeId,nDisplayName);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getNodeDetailByNodeNo(String wsId, int parentNodeId, int nodeNo) {
		return workspaceNodeDetail.getNodeDetailByNodeNo(wsId, parentNodeId, nodeNo);
	}
	
	public int getFailedScriptData(String wsId, int parenNodeId, String nodeName) {
		return workspaceNodeDetail.getFailedScriptData(wsId,parenNodeId,nodeName);
	}
	
	public DTOWorkSpaceNodeDetail getFailedScriptNodeDetail(String wsId, int parenNodeId, int nodeNo) {
		return workspaceNodeDetail.getFailedScriptNodeDetail(wsId,parenNodeId,nodeNo);
	}
	
	public DTOWorkSpaceNodeDetail getNodeDetailForEDocSign(String wsId) {
		return workspaceNodeDetail.getNodeDetailForEDocSign(wsId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getWsNodeDetailHistory(String wsId, int nodeId) {
		return workspaceNodeDetail.getWsNodeDetailHistory(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getDeletedNodeDetail(String wsId) {
		return workspaceNodeDetail.getDeletedNodeDetail(wsId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getDeletedNodeDetailForActiveInactive(String wsId) {
		return workspaceNodeDetail.getDeletedNodeDetailForActiveInactive(wsId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getDeletedNodeDetailForActivity(String wsId) {
		return workspaceNodeDetail.getDeletedNodeDetailForActivity(wsId);
	}
	
	public char getCloneFlagDetail(DTOWorkSpaceNodeDetail obj) {
		return workspaceNodeDetail.getCloneFlagDetail(obj);
	}

	public int isLeafNodes(String wsId, int nodeId) {
		return workspaceNodeDetail.isLeafNodes(wsId, nodeId);
	}
	
	public int isLeafParent(String wsId, int nodeId) {
		return workspaceNodeDetail.isLeafParent(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> isLeafParentForAttribute(String wsId, int nodeId,int userCode, int userGroupCode) {
		return workspaceNodeDetail.isLeafParentForAttribute(wsId, nodeId,userCode,userGroupCode);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> isLeafParentForCSV(String wsId, int nodeId) {
		return workspaceNodeDetail.isLeafParentForCSV(wsId, nodeId);
	}

	public void addChildNode(DTOWorkSpaceNodeDetail dto) {
		workspaceNodeDetail.addChildNode(dto);
	}

	public void addChildNodeBefore(DTOWorkSpaceNodeDetail dto) {
		workspaceNodeDetail.addChildNodeBefore(dto);
	}

	public void addChildNodeAfter(DTOWorkSpaceNodeDetail dto) {
		workspaceNodeDetail.addChildNodeAfter(dto);
	}
	
	public void addChildNodeAfterTC(DTOWorkSpaceNodeDetail dto) {
		workspaceNodeDetail.addChildNodeAfterTC(dto);
	}
	
	public void addChildNodeAfterForEDocSign(DTOWorkSpaceNodeDetail dto) {
		workspaceNodeDetail.addChildNodeAfterForEDocSign(dto);
	}

	public void insertWorkspaceNodeDetail(DTOWorkSpaceNodeDetail workSpaceNodeDetailDTO, int Mode) {
		workspaceNodeDetail.insertWorkspaceNodeDetail(workSpaceNodeDetailDTO,Mode);
	}
	public void insertIntoPQApproval(DTOWorkSpaceNodeDetail workSpaceNodeDetailDTO) {
		workspaceNodeDetail.insertIntoPQApproval(workSpaceNodeDetailDTO);
	}
	
	public void insertIntoTracebelityMatrixDtl(DTOWorkSpaceNodeDetail workSpaceNodeDetailDTO,int mode) {
		workspaceNodeDetail.insertIntoTracebelityMatrixDtl(workSpaceNodeDetailDTO,mode);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getPQSTableHeaderMst(String wsId, int nodeId, String clientCode, String folderName) {
		return workspaceNodeDetail.getPQSTableHeaderMst(wsId,nodeId,clientCode,folderName);
	}
	public void insertWorkspaceNodeDetailhistory(
			DTOWorkSpaceNodeDetail workSpaceNodeDetailDTO, int Mode) {
		workspaceNodeDetail.insertWorkspaceNodeDetailhistory(workSpaceNodeDetailDTO,
				Mode);
	}

	public Vector<DTOWorkSpaceNodeDetail> getChildNodeByParentForPublishFromM2toM5(
			int parentNodeId, String workSpaceId) {
		return workspaceNodeDetail.getChildNodeByParentForPublishFromM2toM5(
				parentNodeId, workSpaceId);
	}

	public Vector<DTOWorkSpaceNodeDetail> getChildNodeByParentForPublishForM1(
			int parentNodeId, String workSpaceId) {
		return workspaceNodeDetail.getChildNodeByParentForPublishForM1(
				parentNodeId, workSpaceId);
	}

	public Vector<DTOWorkSpaceNodeDetail> getModuleWiseChildNodeByParentForNeesPublish(
			int parentNodeId,int nodeNo, String workSpaceId) {
		return workspaceNodeDetail.getModuleWiseChildNodeByParentForNeesPublish(
				parentNodeId, nodeNo,workSpaceId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getChildNodeByParentForPdfPublishForM1(
			int parentNodeId, String workSpaceId) {
		return workspaceNodeDetail.getChildNodeByParentForPdfPublishForM1(
				parentNodeId, workSpaceId);
	}

	public Vector<DTOWorkSpaceNodeDetail> getChildNodeByParent(
			int parentNodeId, String workSpaceId) {
		return workspaceNodeDetail.getChildNodeByParent(parentNodeId,
				workSpaceId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getChildNodesForSectionDeletion(String workSpaceId,int nodeId,String status) {
		return workspaceNodeDetail.getChildNodesForSectionDeletion(workSpaceId,nodeId,status);
	}

	public Vector<DTOWorkSpaceNodeDetail> getParentNode(String wsId, int nodeId) {
		return workspaceNodeDetail.getParentNode(wsId, nodeId);
	}

	/*
	 * public Vector getParentNoAndNodeDisplay(DTOWorkSpaceNodeDetail dto) {
	 * return workspaceNodeDetail.getParentNoAndNodeDisplay(dto); }
	 */
	public Vector<DTOWorkSpaceNodeDetail> getAllSTFFirstNodes(String workspaceId) {
		return workspaceNodeDetail.getAllSTFFirstNodes(workspaceId);
	}

	public Vector<DTOWorkSpaceNodeDetail> getAllChildSTFNodes(
			String workspaceId, int parentId) {
		return workspaceNodeDetail.getAllChildSTFNodes(workspaceId, parentId);
	}

	public Vector<Integer> getAllNodesFromHistory(String workspaceid,
			int labelNo) {
		return workspaceNodeDetail.getAllNodesFromHistory(workspaceid, labelNo);
	}

	public Vector<DTOWorkSpaceNodeDetail> getFileDetailByWorkspaceIdAndNodeId(
			String wsId, int nodeId) {
		return workspaceNodeDetail.getFileDetailByWorkspaceIdAndNodeId(wsId,
				nodeId);
	}

	public Vector<DTOWorkSpaceNodeDetail> getNodeDetailOnAttributeValue(
			String attrId, String attrValue) {
		return workspaceNodeDetail.getNodeDetailOnAttributeValue(attrId,
				attrValue);
	}

	public Vector<DTOWorkSpaceNodeDetail> getNodeDetailByAttributeIdandValue(
			String attrId, String attrValue) {
		return workspaceNodeDetail.getNodeDetailByAttributeIdandValue(attrId,
				attrValue);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getNodeDetailForActivityReport(
			String wsId) {
		return workspaceNodeDetail.getNodeDetailForActivityReport(wsId);
	}

	public void deleteNodeDetail(String wsId, int nodeId,String remark) {
		workspaceNodeDetail.deleteNodeDetail(wsId, nodeId, remark);
	}

	public void activeNodeDetail(String wsId, int nodeId,String remark) {
		workspaceNodeDetail.activeNodeDetail(wsId, nodeId, remark);
	}
	
	public boolean isNodeExtendable(String wsId, int nodeId) {
		return workspaceNodeDetail.isNodeExtendable(wsId, nodeId);
	}

	public void UpdateDisplayNameForPaperSubmission(String WorkspaceId,
			int NodeId) {
		workspaceNodeDetail.UpdateDisplayNameForPaperSubmission(WorkspaceId,
				NodeId);
	}

	public Vector<Integer> getAllNodesFromHistoryForRevisedSubmission(
			String workspaceid, int labelNo) {
		return workspaceNodeDetail.getAllNodesFromHistoryForRevisedSubmission(
				workspaceid, labelNo);
	}

	public Vector<DTOWorkSpaceNodeDetail> getNodeForRevisedSubmission(
			String workspaceid, int labelNo) {
		return workspaceNodeDetail.getNodeForRevisedSubmission(workspaceid,
				labelNo);
	}

	public boolean reArangeNodeNumbers(String workspaceId, String nodes) {
		return workspaceNodeDetail.reArangeNodeNumbers(workspaceId, nodes);
	}

	public Vector<DTOWorkSpaceNodeDetail> getWorkspacenodeHistory(
			String workspaceId, String nodeId, String attrName) {
		return workspaceNodeDetail.getWorkspacenodeHistory(workspaceId, nodeId,
				attrName);
	}

	/*
	 * Method Added By : Ashmak Shah Added On : 12th may 2009
	 */
	public int getParentNodeId(String wsId, int nodeId) {
		return workspaceNodeDetail.getParentNodeId(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getWSNodeDtl(String wsId) {
		return workspaceNodeDetail.getWSNodeDtl(wsId);
	}
	
	
	public int getLeafNodeCount(String wsId, int parenNodeId) {
		return workspaceNodeDetail.getLeafNodeCount(wsId, parenNodeId);
	}

	public int getmaxNodeNo(String vWorkSpaceId, int parentId) {
		return workspaceNodeDetail.getmaxNodeNo(vWorkSpaceId, parentId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getNodeDetailForparent(String wsId, int nodeId,String nDisplayName) {
		return workspaceNodeDetail.getNodeDetailForparent(wsId, nodeId,nDisplayName);
	}
	public int getNodeDetailForNodename(String wsId, int nodeId,String nDisplayName) {
		return workspaceNodeDetail.getNodeDetailForNodename(wsId, nodeId,nDisplayName);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getNodeDetailByNodeDisplayName(String wsId ,String nDisplayName) {
		return workspaceNodeDetail.getNodeDetailByNodeDisplayName(wsId, nDisplayName);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getNodeDetailByNodeDisplayNameForReplication(String wsId ,String nDisplayName) {
		return workspaceNodeDetail.getNodeDetailByNodeDisplayNameForReplication(wsId, nDisplayName);
	}
	
	public int getPQScriptsCount(String wsId, int nodeId, String nodeName) {
		return workspaceNodeDetail.getPQScriptsCount(wsId, nodeId, nodeName);
	}
	public Vector<DTOWorkSpaceNodeDetail> getNodeDetailForparent(String wsId, int nodeId) {
		return workspaceNodeDetail.getNodeDetailForparent(wsId, nodeId);
	}

	public Vector<Integer> getAllChildNodes(String wsId, int nodeId,
			Vector<Integer> allChildNodes) {
		return workspaceNodeDetail
				.getAllChildNodes(wsId, nodeId, allChildNodes);
	}
	public Vector<DTOWorkSpaceNodeDetail> getChildNodesModulewise(String wsId, int nodeId) {
		return workspaceNodeDetail
				.getChildNodesModulewise(wsId, nodeId);
	}
	public Vector<DTOWorkSpaceNodeDetail> getModulewiseChildNodes(String wsId, int nodeId) {
		return workspaceNodeDetail.getModulewiseChildNodes(wsId, nodeId);
	}
	public Vector<Integer> getWorkspaceTreeNodesForLeafs(String workspaceId,
			int[] leafIds) {
		return workspaceNodeDetail.getWorkspaceTreeNodesForLeafs(workspaceId,
				leafIds);
	}

	public Vector<Integer> getSelectedNodeDetailsForPdfPublish(
			String workspaceId, int[] leafIds) {
		return workspaceNodeDetail.getSelectedNodeDetailsForPdfPublish(
				workspaceId, leafIds);
	}

	public void insertWorkspaceNodeDetail(
			ArrayList<DTOWorkSpaceNodeDetail> workSpaceNodeDetailList, int Mode) {
		workspaceNodeDetail.insertWorkspaceNodeDetail(workSpaceNodeDetailList,
				Mode);
	}

	public ArrayList<DTOWorkSpaceNodeDetail> updateNodeNo(String wsId,
			int nodeId, int setNodeNo) {
		return workspaceNodeDetail.updateNodeNo(wsId, nodeId, setNodeNo);
	}

	public int[] updateNodeNo(Vector<DTOWorkSpaceNodeDetail> nodeList) {
		return workspaceNodeDetail.updateNodeNo(nodeList);
	}

	public ArrayList<DTOWorkSpaceNodeDetail> getAllChildNodeForFileuploading(
			String wsID) {
		return workspaceNodeDetail.getAllChildNodeForFileuploading(wsID);
	}

	public ArrayList<DTOWorkSpaceNodeDetail> getAllParentsNodes(String wsId,
			int nodeId) {
		return workspaceNodeDetail.getAllParentsNodes(wsId, nodeId);
	}

	public Integer getTotalLeafNodes(ArrayList<String> wsIDs) {
		return workspaceNodeDetail.getTotalLeafNodes(wsIDs);
	}
	
	public int getFirstLeafNodeForDocCR(String wsId) {
		return workspaceNodeDetail.getFirstLeafNodeForDocCR(wsId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getAllLeafNodeIds(String wsId) {
		return workspaceNodeDetail.getAllLeafNodeIds(wsId);
	}
	
	public ArrayList<ArrayList<String>> getReleasedDocIdDetails(
			String workspaceid, int parentNodeId) {
		return workspaceNodeDetail.getReleasedDocIdDetails(workspaceid,
				parentNodeId);
	}
	
	public int getNodeMaxIdFromNodeName(String vWorkspaceId,
			String nodeName) {
		return workspaceNodeDetail.getNodeMaxIdFromNodeName(vWorkspaceId, nodeName);
	}
	
	public boolean getfileUploadSeqDetail(String wsId, int nodeId,int parentNodeId) {
		return workspaceNodeDetail.getfileUploadSeqDetail(wsId, nodeId,parentNodeId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getFileUploadSeqForSingle(String wsId, int nodeId,int parentNodeId) {
		return workspaceNodeDetail.getFileUploadSeqForSingle(wsId, nodeId,parentNodeId);
	}
	public Vector<DTOWorkSpaceNodeDetail> getFileUploadSeqForSingleUpdated(String wsId, int nodeId,int parentNodeId) {
		return workspaceNodeDetail.getFileUploadSeqForSingleUpdated(wsId, nodeId,parentNodeId);
	}
	public Vector<DTOWorkSpaceNodeDetail> getFileUploadSeqForAll(String wsId, int nodeId,int parentNodeId) {
		return workspaceNodeDetail.getFileUploadSeqForAll(wsId, nodeId,parentNodeId);
	}
	public Vector<DTOWorkSpaceNodeDetail> getFileUploadSeqForAllUpdated(String wsId, int nodeId,int parentNodeId) {
		return workspaceNodeDetail.getFileUploadSeqForAllUpdated(wsId, nodeId,parentNodeId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getDeviationFile(String wsId, int nodeId) {
		return workspaceNodeDetail.getDeviationFile(wsId,nodeId);
	}
	
	public void insertWSNodeDeviation(DTOWorkSpaceNodeDetail dto) {
		this.workspaceNodeDetail.insertWSNodeDeviation(dto);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getDeviationFileDetail(String wsId) {
		return workspaceNodeDetail.getDeviationFileDetail(wsId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getWSDeviationDetail(String wsId,String nodeIds) {
		return workspaceNodeDetail.getWSDeviationDetail(wsId,nodeIds);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getWorkSpaceDetailForChangeRequestGrid(String wsId,int userGroupCode,int userCode) {
		return workspaceNodeDetail.getWorkSpaceDetailForChangeRequestGrid(wsId,userGroupCode,userCode);
	}
	public Vector<DTOWorkSpaceNodeDetail> getWorkSpaceDetailForChangeRequestGridList(String wsId,int userGroupCode,int userCode) {
		return workspaceNodeDetail.getWorkSpaceDetailForChangeRequestGridList(wsId,userGroupCode,userCode);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getWorkSpaceDetailForChangeRequestGridListForESignature(String wsId,int userGroupCode,int userCode) {
		return workspaceNodeDetail.getWorkSpaceDetailForChangeRequestGridListForESignature(wsId,userGroupCode,userCode);
	}
	
	public void UpdateNodeDetail(String wsId,int nodeId,String folderName) {
		this.workspaceNodeDetail.UpdateNodeDetail(wsId,nodeId,folderName);
	}
	
	public void UpdateNodeTypeIndi(String wsId,int nodeId,char nodeTypeIndi) {
		this.workspaceNodeDetail.UpdateNodeTypeIndi(wsId,nodeId,nodeTypeIndi);
	}
	
	public boolean folderNameExist(String workspaceId, String folderName) {
		return workspaceNodeDetail.folderNameExist(workspaceId,folderName);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getChildNodesForSectionDeletion(String workSpaceId,int nodeId) {
		return workspaceNodeDetail.getChildNodesForSectionDeletion(workSpaceId,nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getChildNodesSections(String workSpaceId,int nodeId) {
		return workspaceNodeDetail.getChildNodesSections(workSpaceId,nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getURSTracebelityMatrixDtl(String workspaceId, int nodeId, int tranNoForDtl) {
		return workspaceNodeDetail.getURSTracebelityMatrixDtl(workspaceId,nodeId,tranNoForDtl);
	}
	public Vector<DTOWorkSpaceNodeDetail> getURSTracebelityMatrixDtl(String workspaceId) {
		return workspaceNodeDetail.getURSTracebelityMatrixDtl(workspaceId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getURSTracebelityMatrixDtlForDocType(String workspaceId,String docType) {
		return workspaceNodeDetail.getURSTracebelityMatrixDtlForDocType(workspaceId,docType);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getTracebelityMatrixDtlForDocTypeHistory(String workspaceId,int nodeId, int tranNoForDtl,String docType) {
		return workspaceNodeDetail.getTracebelityMatrixDtlForDocTypeHistory(workspaceId,nodeId,tranNoForDtl,docType);
	}
	
	
	public Vector<DTOWorkSpaceNodeDetail> getFSTracebelityMatrixDtl(String workspaceId, int nodeId, int tranNoForDtl) {
		return workspaceNodeDetail.getFSTracebelityMatrixDtl(workspaceId,nodeId,tranNoForDtl);
	}
	
	public String getTracebelityMatrixDtlForAttributes(String workspaceId,String values) {
		return workspaceNodeDetail.getTracebelityMatrixDtlForAttributes(workspaceId,values);
	}
	
	public  Vector <DTOWorkSpaceNodeDetail>  getTracebelityMatrixDtlForAttributesToShow(String workspaceId,String values) {
		return workspaceNodeDetail.getTracebelityMatrixDtlForAttributesToShow(workspaceId,values);
	}
	
	public  String  getScriptCodesForTM(String workspaceId,String values) {
		return workspaceNodeDetail.getScriptCodesForTM(workspaceId,values);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getURSTracebelityMatrixDtlToCheck(String workspaceId,String Automated_Doc_Type) {
		return workspaceNodeDetail.getURSTracebelityMatrixDtlToCheck(workspaceId,Automated_Doc_Type);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getFSTracebelityMatrixDtlToCheck(String workspaceId,String Automated_Doc_Type) {
		return workspaceNodeDetail.getFSTracebelityMatrixDtlToCheck(workspaceId,Automated_Doc_Type);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getTracebelityMatrixDtlByID(String workspaceId, String id) {
		return workspaceNodeDetail.getTracebelityMatrixDtlByID(workspaceId,id);
	}
	
	// /////////////////workspace user master////////////////////////

	public Vector<DTOWorkSpaceUserMst> getAllWorkspaceUserDetail(String wsId) {
		return workspaceUserMst.getAllWorkspaceUserDetail(wsId);
	}
	
	public Vector<DTOWorkSpaceUserMst> getUserRightsForWorkspace(String wsId,int userCode,int userGroupCode) {
		return workspaceUserMst.getUserRightsForWorkspace(wsId,userCode,userGroupCode);
	}
	
	public Vector<DTOWorkSpaceUserMst> getUserRightsForESignature(String wsId,int userCode,int userGroupCode) {
		return workspaceUserMst.getUserRightsForESignature(wsId,userCode,userGroupCode);
	}

	public Vector<DTOWorkSpaceUserMst> getWorkspaceUserDetailList(String wsId) {
		return workspaceUserMst.getWorkspaceUserDetailList(wsId);
	}
	
	public Vector<DTOWorkSpaceUserMst> getWorkspaceUserDetailForDocSign(String wsId,int userId, int useGroupCode ) {
		return workspaceUserMst.getWorkspaceUserDetailForDocSign(wsId,userId,useGroupCode);
	}

	public void inactiveuserfromproject(DTOWorkSpaceUserMst obj) {
		workspaceUserMst.inactiveuserfromproject(obj);
	}
	
	public Vector checkWorkspacemsthistory(String wsId, int userCode, int userGrpcode) {
		return workspaceUserMst.checkWorkspacemsthistory(wsId,userCode,userGrpcode);
	}

	public void insertUpdateUsertoWorkspace(DTOWorkSpaceUserMst obj,
			int[] userCode) {
		workspaceUserMst.insertUpdateUsertoWorkspace(obj, userCode);
	}
	
	public void insertUpdateUsertoWorkspaceForAttachUser(DTOWorkSpaceUserMst obj,
			int[] userCode) {
		workspaceUserMst.insertUpdateUsertoWorkspaceForAttachUser(obj, userCode);
	}
	
	public void insertUpdateUsertoWorkspaceForAttachUser(DTOWorkSpaceUserMst obj,int userCode) {
		workspaceUserMst.insertUpdateUsertoWorkspaceForAttachUser(obj, userCode);
	}
	
	public void insertUsertoWorkspaceUserMstForDocSign(DTOWorkSpaceUserMst dto) {
		workspaceUserMst.insertUsertoWorkspaceUserMstForDocSign(dto);
	}
	
	public void insertUsertoWorkspaceUserMstForDocSignWithRoleCode(DTOWorkSpaceUserMst dto) {
		workspaceUserMst.insertUsertoWorkspaceUserMstForDocSignWithRoleCode(dto);
	}
	
	public boolean updateMaintainedSeq(String wsId,int nId,int uCode,int uGrpCode,int tranNo) {
		return workspaceUserMst.updateMaintainedSeq(wsId,nId,uCode,uGrpCode,tranNo);
	}
	
	public void insertUpdateUsertoWorkspaceHistory(DTOWorkSpaceUserMst obj,
			int[] userCode) {
		workspaceUserMst.insertUpdateUsertoWorkspaceHistory(obj, userCode);
	}

	public void insertUpdateUsertoWorkspaceHistoryForAttachUser(DTOWorkSpaceUserMst obj,
			int[] userCode) {
		workspaceUserMst.insertUpdateUsertoWorkspaceHistoryForAttachUser(obj, userCode);
	}
	
	public void insertUpdateUsertoWorkspaceHistoryForAttachUser(DTOWorkSpaceUserMst obj,int userCode) {
		workspaceUserMst.insertUpdateUsertoWorkspaceHistoryForAttachUser(obj, userCode);
	}
	
	public void DeleteProjectlevelRights(DTOWorkSpaceUserMst obj) {
		workspaceUserMst.DeleteProjectlevelRights(obj);
	}
	
	public void DeletemodulespecRights(DTOWorkSpaceUserMst obj) {
		workspaceUserMst.DeletemodulespecRights(obj);
	}
	
	public Vector<DTOWorkSpaceUserMst> getWorkspaceUserHistory(String wsId, String userCode, String userGroupId) {
		return workspaceUserMst.getWorkspaceUserHistory(wsId,userCode,userGroupId);
	}
		
	public Vector<DTOWorkSpaceUserMst> getDeletedWorkspaceUserHistory(String wsId) {
		return workspaceUserMst.getDeletedWorkspaceUserHistory(wsId);
	}
	
	public Vector<DTOWorkSpaceUserMst> getAllWorkspaceUserHistory(String wsId) {
		return workspaceUserMst.getAllWorkspaceUserHistory(wsId);
	}
	
	public Vector<DTOWorkSpaceUserMst> getWorkspaceUserHistoryForReport(String wsId) {
		return workspaceUserMst.getWorkspaceUserHistoryForReport(wsId);
	}
	
	public DTOWorkSpaceUserMst getUserDetail(DTOWorkSpaceUserMst obj) {
		return workspaceUserMst.getUserDetail(obj);
	}

	public Vector<DTOUserMst> getWorkspaceUserDetail(String workspaceID,
			DTOUserMst usermst) {
		return workspaceUserMst.getWorkspaceUserDetail(workspaceID, usermst);
	}
	public Vector<DTOUserMst> getWorkspaceUserDetailCSV(String workspaceID) {
		return workspaceUserMst.getWorkspaceUserDetailCSV(workspaceID);
	}
	public Vector<DTOWorkSpaceUserRightsMst> getWorkspaceuserdetailByNodeId(String workspaceID,
			int nodeId,DTOUserMst usermst) {
		return workspaceUserMst.getWorkspaceuserdetailByNodeId(workspaceID,nodeId, usermst);
	}
	
	public Vector<DTOUserMst> getModuleorprojectwiseWiseWorkspaceUserDetail(String workspaceID,
			DTOUserMst usermst,String RightsType) {
		return workspaceUserMst.getModuleorprojectwiseWiseWorkspaceUserDetail(workspaceID, usermst,RightsType);
	}
	
	public Vector<DTOUserMst> getModuleorprojectwiseWiseWorkspaceUserDetailForCSV(String workspaceID,
			int nodeId,String RightsType) {
		return workspaceUserMst.getModuleorprojectwiseWiseWorkspaceUserDetailForCSV(workspaceID, nodeId,RightsType);
	}
	
	public Vector<DTOUserMst> getModuleorprojectwiseWiseWorkspaceUserDetailForESignature(String workspaceID,
			int nodeId,String RightsType) {
		return workspaceUserMst.getModuleorprojectwiseWiseWorkspaceUserDetailForESignature(workspaceID, nodeId,RightsType);
	}
	
	public Vector<DTOUserMst> getModuleorprojectwiseWiseWorkspaceUserDetailForCSVForRoleCode(String workspaceID,
			int nodeId,String roleCode,String RightsType) {
		return workspaceUserMst.getModuleorprojectwiseWiseWorkspaceUserDetailForCSVForRoleCode(workspaceID, nodeId,roleCode,RightsType);
	}
	
	public Vector<DTOUserMst> getWorkspaceUserDetailForNode(String workspaceID,int nodeId) {
		return workspaceUserMst.getWorkspaceUserDetailForNode(workspaceID, nodeId);
	}
	
	public Vector<DTOUserMst> getModuleorprojectwiseWiseWorkspaceUserDetailForDocSign(String workspaceID,
			int nodeId,int userWiseGroupCode) {
		return workspaceUserMst.getModuleorprojectwiseWiseWorkspaceUserDetailForDocSign(workspaceID, nodeId,userWiseGroupCode);
	}
	
	public Vector<DTOUserMst> getModuleorprojectwiseWorkspaceUserDetailForESignature(String workspaceID,int nodeId,int userWiseGroupCode) {
		return workspaceUserMst.getModuleorprojectwiseWorkspaceUserDetailForESignature(workspaceID, nodeId,userWiseGroupCode);
	}
	
	
	public Vector<DTOUserMst> getModuleorprojectwiseWiseWorkspaceUserDetailForDocSignWithRoleCode(String workspaceID,
			int nodeId,int userWiseGroupCode,String roleCode) {
		return workspaceUserMst.getModuleorprojectwiseWiseWorkspaceUserDetailForDocSignWithRoleCode(workspaceID, nodeId,userWiseGroupCode,roleCode);
	}
	
	public Vector<DTOUserMst> getModuleorprojectwiseWiseWorkspaceUserDetailForESignatureWithRoleCode(String workspaceID,
			int nodeId,int userWiseGroupCode,String roleCode) {
		return workspaceUserMst.getModuleorprojectwiseWiseWorkspaceUserDetailForESignatureWithRoleCode(workspaceID, nodeId,userWiseGroupCode,roleCode);
	}
	
	
	public Vector<DTOWorkSpaceMst> getAllWorkspace() {
		return workspaceMst.getAllWorkspace();
	}

	public Vector<DTOUserMst> getUserDetailByWorkspaceId(String wsId) {
		return workspaceUserMst.getUserDetailByWorkspaceId(wsId);
	}

	public void insertUpdateUsertoWorkspace(ArrayList<DTOWorkSpaceUserMst> users) {
		workspaceUserMst.insertUpdateUsertoWorkspace(users);
	}
	public Vector<DTOWorkSpaceMst> getProjectDetailHistory(String wsId) {
		return workspaceMst.getProjectDetailHistory(wsId);
	}
	
	public ArrayList<DTOWorkSpaceUserMst> getCompelteWSDetail(int userCode, int userGroupCode) {
		return workspaceMst.getCompelteWSDetail(userCode,userGroupCode);
	}
	
	

	// //////workspace user right master///////////////////////////

	public Vector<DTOWorkSpaceUserRightsMst> getWorkspaceNodeRightsDetail(
			DTOWorkSpaceUserRightsMst obj) {
		return workSpaceUserRightsMst.getWorkspaceNodeRightsDetail(obj);
	}
	
	public Vector<DTOTimelineWorkspaceUserRightsMst> getTimelineDataForSectionRemove(String vWorkspaceId, int nodeId) 
	{
		return workSpaceUserRightsMst.getTimelineDataForSectionRemove(vWorkspaceId,nodeId); 
	}
	
	public Vector<DTOTimelineWorkspaceUserRightsMst> getTimelineDataForRemove(String vWorkspaceId, int nodeId) 
	{
		return workSpaceUserRightsMst.getTimelineDataForRemove(vWorkspaceId,nodeId); 
	}

	public Vector<Object[]> getNodeAndRightDetail(String workspaceID,
			int userGroupCode, int userCode) {
		return workSpaceUserRightsMst.getNodeAndRightDetail(workspaceID,
				userGroupCode, userCode);
	}
	
	public Vector<Object[]> getNodeAndRightDetailList(String workspaceID,
			int userGroupCode, int userCode) {
		return workSpaceUserRightsMst.getNodeAndRightDetailList(workspaceID,
				userGroupCode, userCode);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineData(String wsId, int nodeId,int stageId){
		return workSpaceUserRightsMst.getProjectTimelineData(wsId,nodeId,stageId);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineDetails(String wsId){
		return workSpaceUserRightsMst.getProjectTimelineDetails(wsId);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineDetailsForHours(String wsId){
		return workSpaceUserRightsMst.getProjectTimelineDetailsForHours(wsId);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineSRFlagData(String wsId){
		return workSpaceUserRightsMst.getProjectTimelineSRFlagData(wsId);
	}
	
	public Vector<DTOTimelineWorkspaceUserRightsMst> getTimelineDataForRepeatSection(String vWorkspaceId, int nodeId) 
	{
		return workSpaceUserRightsMst.getTimelineDataForRepeatSection(vWorkspaceId,nodeId); 
	}
		
	public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineAdjustDateInfo(String wsId,int nodeId,int parentNodeId,int userCode,int stageId){
		return workSpaceUserRightsMst.getProjectTimelineAdjustDateInfo(wsId,nodeId,parentNodeId,userCode,stageId);
	}
	public DTOWorkSpaceUserRightsMst getTimeLineRightsMstdtl(String wsId,int nodeId,int userCode,int userGrpCode, int stageId){
		return workSpaceUserRightsMst.getTimeLineRightsMstdtl(wsId,nodeId,userCode,userGrpCode,stageId);
	}
	public DTOWorkSpaceUserRightsMst getTimeLineRightsDtl(String wsId,int nodeId,int userCode, int stageId){
		return workSpaceUserRightsMst.getTimeLineRightsDtl(wsId,nodeId,userCode,stageId);
	}
	public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineAdjustHoursUpdate(String wsId,int nodeId,int parentNodeId,int userCode,int stageId){
		return workSpaceUserRightsMst.getProjectTimelineAdjustHoursUpdate(wsId,nodeId,parentNodeId,userCode,stageId);
	}
	public Vector<DTOWorkSpaceUserRightsMst> UserOnNodeTimelineTracking(String wsId,int nodeId){
		return workSpaceUserRightsMst.UserOnNodeTimelineTracking(wsId,nodeId);
	}
	public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineAdjustDateRepeatNodeandChildNodeInfo(String wsId,int nodeId,int parentNodeId){
		return workSpaceUserRightsMst.getProjectTimelineAdjustDateRepeatNodeandChildNodeInfo(wsId,nodeId,parentNodeId);
	}
	public Vector<DTOWorkSpaceUserRightsMst> getProjectTimelineAdjustDateRepeatNodeInfo(String wsId,int nodeId,int parentNodeId){
		return workSpaceUserRightsMst.getProjectTimelineAdjustDateRepeatNodeInfo(wsId,nodeId,parentNodeId);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getTotalUsersByStage(String wsId, int nodeId,int stageId){
		return workSpaceUserRightsMst.getTotalUsersByStage(wsId,nodeId,stageId);
	}
	public Vector<DTOWorkSpaceUserRightsMst> getAllRightsUserWise(String WorkspaceId,int NodeId,int usercode,int usergroupcode){
		return workSpaceUserRightsMst.getAllRightsUserWise(WorkspaceId,NodeId,usercode,usergroupcode);
	}
	
	// added on 8-5-2015 by Dharmendra Jadav
	//start
	
	public Vector<Object[]> getNodeDetails(String workspaceID) {
		return workSpaceUserRightsMst.getNodeDetails(workspaceID);
	}
	
	//end

	/************************************/

	public Vector<DTOWorkSpaceNodeDetail> getNodeAndRightDetailNewTree(
			String workspaceID, Integer userGroupCode, Integer userCode) {
		return workSpaceUserRightsMst.getNodeAndRightDetailNewTree(workspaceID,
				userGroupCode.intValue(), userCode.intValue());
	}
	public Vector<DTOWorkSpaceNodeDetail> getNodeAndRightDetailNewTreeList(
			String workspaceID, Integer userGroupCode, Integer userCode) {
		return workSpaceUserRightsMst.getNodeAndRightDetailNewTreeList(workspaceID,
				userGroupCode.intValue(), userCode.intValue());
	}

	/************************************/

	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsReport(
			DTOWorkSpaceUserRightsMst dto) {
		return workSpaceUserRightsMst.getUserRightsReport(dto);
	}

	public boolean updateWorkSpaceUserRights(
			DTOWorkSpaceUserRightsMst objWorkSpaceUserRights) {
		return workSpaceUserRightsMst
				.updateWorkSpaceUserRights(objWorkSpaceUserRights);
	}
	
	public void updateTimelineDatesValue(
			DTOWorkSpaceUserRightsMst objWorkSpaceUserRights) {
		workSpaceUserRightsMst.updateTimelineDatesValue(objWorkSpaceUserRights);
	}
	public void updateTimelineHoursAdjustDate(
			DTOWorkSpaceUserRightsMst objWorkSpaceUserRights) {
		workSpaceUserRightsMst.updateTimelineHoursAdjustDate(objWorkSpaceUserRights);
	}
	public void updateTimelineAdjustDate(
			DTOWorkSpaceUserRightsMst objWorkSpaceUserRights) {
		workSpaceUserRightsMst.updateTimelineAdjustDate(objWorkSpaceUserRights);
	}

	public Vector<Object[]> getNodeDetailForDTD(String workspaceID,
			int userGroupCode, int userCode) {
		return workSpaceUserRightsMst.getNodeDetailForDTD(workspaceID,
				userGroupCode, userCode);
	}

	public Vector<Object[]> getNodeAndRightDetailForIndexView(
			String workspaceID, int userGroupCode, int userCode) {
		return workSpaceUserRightsMst.getNodeAndRightDetailForIndexView(
				workspaceID, userGroupCode, userCode);
	}

	public Vector<DTOWorkSpaceUserRightsMst> getStageUserDetail(
			String WorkspaceId, int NodeId, int usercode, int usergroupcode) {
		return workSpaceUserRightsMst.getStageUserDetail(WorkspaceId, NodeId,
				usercode, usergroupcode);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getStageUserDetailForDocSign(
			String WorkspaceId, int NodeId, int usercode) {
		return workSpaceUserRightsMst.getStageUserDetailForDocSign(WorkspaceId, NodeId,
				usercode);
	}
	
	
	
	
	
	public Vector<DTOWorkSpaceUserRightsMst> getNewStageId(
			String WorkspaceId, int NodeId, int usercode, int usergroupcode) {
		return workSpaceUserRightsMst.getNewStageId(WorkspaceId, NodeId,
				usercode, usergroupcode);
	}
	public Vector<DTOWorkSpaceUserRightsMst> getAllStageIdsForAdmin(String WorkspaceId,int NodeId,int usercode,int usergroupcode){
		return workSpaceUserRightsMst.getAllStageIdsForAdmin(WorkspaceId, NodeId,
				usercode, usergroupcode);
	}
	public Vector<DTOWorkSpaceUserRightsMst> getStageIds(String WorkspaceId,int NodeId,int usercode,int usergroupcode,int stageId)
	{
		return workSpaceUserRightsMst.getStageIds(WorkspaceId, NodeId,
				usercode, usergroupcode,stageId);
	}
	public Vector<DTOWorkSpaceUserRightsMst> getNextStageIds(String WorkspaceId,int NodeId,int usercode,int usergroupcode){
		return workSpaceUserRightsMst.getNextStageIds(WorkspaceId, NodeId,
				usercode, usergroupcode);
	}
	public Vector<DTOWorkSpaceUserRightsMst> getNextStageRightsWise(String WorkspaceId,int NodeId,int usercode,int usergroupcode,int stageId,int mode){
		return workSpaceUserRightsMst.getNextStageRightsWise(WorkspaceId,NodeId,usercode,usergroupcode,stageId,mode);
	}

	public boolean iscreatedRights(String WorkspaceId, int NodeId,
			int usercode, int usergroupcode) {
		return workSpaceUserRightsMst.iscreatedRights(WorkspaceId, NodeId,
				usercode, usergroupcode);
	}

	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail(String wsId,int nodeId) {
		return workSpaceUserRightsMst.getUserRightsDetail(wsId, nodeId);
	}
	
	public String getUserRightsDetailForNextStageUser(String wsId,int nodeId,int stageId) {
		return workSpaceUserRightsMst.getUserRightsDetailForNextStageUser(wsId, nodeId,stageId);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForEsignatureByUsercode(String wsId,int nodeId,int usercode) {
		return workSpaceUserRightsMst.getUserRightsDetailForEsignatureByUsercode(wsId, nodeId,usercode);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getUserFromSeqNo(String wsId,int nodeId,int seqNo) {
		return workSpaceUserRightsMst.getUserFromSeqNo(wsId, nodeId,seqNo);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForEsignature(String wsId,int nodeId) {
		return workSpaceUserRightsMst.getUserRightsDetailForEsignature(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForESignautre(String wsId,int nodeId) {
		return workSpaceUserRightsMst.getUserRightsDetailForESignautre(wsId, nodeId);
	}
	
	public int checkCreateRights(String wsId, int nodeId) {
		return workSpaceUserRightsMst.checkCreateRights(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForSingleDoc(String wsId,
			int nodeId) {
		return workSpaceUserRightsMst.getUserRightsDetailForSingleDoc(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailDraft(String wsId,int nodeId) {
		return workSpaceUserRightsMst.getUserRightsDetailDraft(wsId, nodeId);
	}
	
	
	
	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForCSV(String wsId,int nodeId,int selectedUsers) {
		return workSpaceUserRightsMst.getUserRightsDetailForCSV(wsId, nodeId,selectedUsers);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForESignature(String wsId,int nodeId,int selectedUsers) {
		return workSpaceUserRightsMst.getUserRightsDetailForESignature(wsId, nodeId,selectedUsers);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getSeqNoForSignoffSequence(String wsId, int nodeId,int usercode) {
		return workSpaceUserRightsMst.getSeqNoForSignoffSequence(wsId, nodeId,usercode);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForCSVforTimeLine(String wsId,
			int nodeId,int selectedUsers) {
		return workSpaceUserRightsMst.getUserRightsDetailForCSVforTimeLine(wsId, nodeId,selectedUsers);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getUserNodeRightsDetail(String wsId,
			int nodeId) {
		return workSpaceUserRightsMst.getUserNodeRightsDetail(wsId, nodeId);
	}
	
	public void addOrUpdateRights(String templateId, int userCode) {
		workSpaceUserRightsMst.addOrUpdateRights(templateId, userCode);
	}

	public int getMaxStageId(String wsId, int nodeId, int userCode) {
		return workSpaceUserRightsMst.getMaxStageId(wsId, nodeId, userCode);
	}

	public Vector<Object[]> getUserWisePublishableNodes(String workspaceID,
			int userGroupCode, int userCode) {
		return workSpaceUserRightsMst.getUserWisePublishableNodes(workspaceID,
				userGroupCode, userCode);
	}
	public Vector<Object[]> getUserWisePublishableNodesList(String workspaceID,
			int userGroupCode, int userCode) {
		return workSpaceUserRightsMst.getUserWisePublishableNodesList(workspaceID,
				userGroupCode, userCode);
	}

	public void insertMultipleUserRights(
			ArrayList<DTOWorkSpaceUserRightsMst> userRightsList) {
		workSpaceUserRightsMst.insertMultipleUserRights(userRightsList);
	}
	
	public void insertMultipleUserRightsForCSV(
			ArrayList<DTOWorkSpaceUserRightsMst> userRightsList) {
		workSpaceUserRightsMst.insertMultipleUserRightsForCSV(userRightsList);
	}
	
	public void insertFolderSpecificMultipleUserRights(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList) {
		workSpaceUserRightsMst.insertFolderSpecificMultipleUserRights(userRightsList);
	}
	
	public void insertFolderSpecificMultipleUserRightsForESignature(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList) {
		workSpaceUserRightsMst.insertFolderSpecificMultipleUserRightsForESignature(userRightsList);
	}
	
	public void insertFolderSpecificMultipleUserRightsWithRoleCode(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList) {
		workSpaceUserRightsMst.insertFolderSpecificMultipleUserRightsWithRoleCode(userRightsList);
	}
	
	public void insertFolderSpecificMultipleUserRightsWithRoleCodeForESignature(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList) {
		workSpaceUserRightsMst.insertFolderSpecificMultipleUserRightsWithRoleCodeForESignature(userRightsList);
	}
	
	
	public Vector<DTOWorkSpaceUserRightsMst> getLastRightsRecordDtlForAdjustDate(String wsId,int nodeId,int stageId){
		return workSpaceUserRightsMst.getLastRightsRecordDtlForAdjustDate(wsId,nodeId,stageId);
	}
	
	public void insertFolderSpecificMultipleUserRightsForTimeLine(
			ArrayList<DTOWorkSpaceUserRightsMst> userRightsList) {
		workSpaceUserRightsMst.insertFolderSpecificMultipleUserRightsForTimeLine(userRightsList);
	}
	public void AttachUserRightsForTimeLine(
			ArrayList<DTOWorkSpaceUserRightsMst> userRightsList) {
		workSpaceUserRightsMst.AttachUserRightsForTimeLine(userRightsList);
	}
	
	public void AttachUserRightsForTimeLineWithExludedDate(ArrayList<DTOWorkSpaceUserRightsMst> userRightsList) {
		workSpaceUserRightsMst.AttachUserRightsForTimeLineWithExludedDate(userRightsList);
	}
	
	public void insertModuleWiseUserHistory(
			DTOWorkSpaceUserRightsMst userRightsForModuleHistory) {
		workSpaceUserRightsMst.insertModuleWiseUserHistory(userRightsForModuleHistory);
	}
	
	public void insertModuleWiseUserHistoryForESignature(DTOWorkSpaceUserRightsMst userRightsForModuleHistory) {
		workSpaceUserRightsMst.insertModuleWiseUserHistoryForESignature(userRightsForModuleHistory);
	}
	
	public void insertModuleWiseUserHistoryWithRoleCode(DTOWorkSpaceUserRightsMst userRightsForModuleHistory) {
		workSpaceUserRightsMst.insertModuleWiseUserHistoryWithRoleCode(userRightsForModuleHistory);
	}
	
	public void UpdateHoursInTimeWorkspaceRightsMst(String wsId,int nodeId,int userCode,int userGrpCode,int duration,int stageId) {
		this.workSpaceUserRightsMst.UpdateHoursInTimeWorkspaceRightsMst(wsId,nodeId,userCode,userGrpCode,duration,stageId);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getTimeLineRightsMst(String wsId,int nodeId,int userCode,int userGrpCode, int stageId){
		return workSpaceUserRightsMst.getTimeLineRightsMst(wsId,nodeId,userCode,userGrpCode,stageId);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getmoduleUserDetailHistory(String wsId, int nodeId) {
		return workSpaceUserRightsMst.getmoduleUserDetailHistory(wsId,nodeId);
	}
	
	public boolean RemoveFolderSpecificMultipleUserRights(String wsId,String[] users,int[] stageId,String nodeIdsCSV) {
		return workSpaceUserRightsMst.RemoveFolderSpecificMultipleUserRights(wsId,users,stageId,nodeIdsCSV);
	}
	public boolean RemoveFolderSpecificMultipleUserRightsForBulkDeletion(String wsId,int user,int stageId,int nodeIdsCSV) {
		return workSpaceUserRightsMst.RemoveFolderSpecificMultipleUserRightsForBulkDeletion(wsId,user,stageId,nodeIdsCSV);
	}
	
	public boolean RemoveUserRightsfromTimeline(String wsId,String[] users,int[] stageId,String nodeIdsCSV) {
		return workSpaceUserRightsMst.RemoveUserRightsfromTimeline(wsId,users,stageId,nodeIdsCSV);
	}
	
	public boolean RemoveUserRightsfromTimelineForBulkDeletion(String wsId,int user,int stageId,int nodeIdsCSV) {
		return workSpaceUserRightsMst.RemoveUserRightsfromTimelineForBulkDeletion(wsId,user,stageId,nodeIdsCSV);
	}
	public boolean RemoveFolderSpecificMultipleUserRightsForCSV(String wsId,int users,int[] stageId,String nodeIdsCSV) {
		return workSpaceUserRightsMst.RemoveFolderSpecificMultipleUserRightsForCSV(wsId,users,stageId,nodeIdsCSV);
	}
	
	public boolean RemoveFolderSpecificMultipleUserRightsForESignature(String wsId,int users,int[] stageId,String nodeIdsCSV) {
		return workSpaceUserRightsMst.RemoveFolderSpecificMultipleUserRightsForESignature(wsId,users,stageId,nodeIdsCSV);
	}
	
	public boolean RemoveRightsFromWorkspaceUserRightsMst(String wsId,int nodeId) {
		return workSpaceUserRightsMst.RemoveRightsFromWorkspaceUserRightsMst(wsId,nodeId);
	}
	public boolean RemoveFolderSpecificMultipleUserRightsForTimeLine(String wsId,int users,int[] stageId,String nodeIdsCSV) {
		return workSpaceUserRightsMst.RemoveFolderSpecificMultipleUserRightsForTimeLine(wsId,users,stageId,nodeIdsCSV);
	}
	public ArrayList<DTOStageMst> getUserStageDetail(String wsId, int userId) {
		return workSpaceUserRightsMst.getUserStageDetail(wsId, userId);
	}

	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsReport(
			DTOWorkSpaceUserRightsMst dto, boolean includeStageId) {
		return workSpaceUserRightsMst.getUserRightsReport(dto, includeStageId);
	}

	public void insertUpdateMultipleUserRights(
			ArrayList<DTOWorkSpaceUserRightsMst> userRightsList, int mode) {
		workSpaceUserRightsMst.insertUpdateMultipleUserRights(userRightsList,
				mode);
	}
	public ArrayList<Integer> getStageIdsRightsWise(String wsId,int userCode,int userGrpCode,int nodeId){
		return workSpaceUserRightsMst.getStageIdsRightsWise(wsId,userCode,userGrpCode,nodeId);
	}
	
	public ArrayList<Integer> getStageIdsRightsWise(String wsId,int nodeId){
		return workSpaceUserRightsMst.getStageIdsRightsWise(wsId,nodeId);
	}
	
	public ArrayList<Integer> getStageIdsRightsWiseForESignture(String wsId,int nodeId){
		return workSpaceUserRightsMst.getStageIdsRightsWiseForESignture(wsId,nodeId);
	}
	
	public ArrayList<DTOStageWiseMailReport> getNodeStageDetail(String wsId,int nodeId,int stageId) {
		return workSpaceUserRightsMst.getNodeStageDetail(wsId,nodeId,stageId);
	}
	
	public ArrayList<DTOStageWiseMailReport> getNodeStageDetailFoESignature(String wsId,int nodeId,int stageId) {
		return workSpaceUserRightsMst.getNodeStageDetailFoESignature(wsId,nodeId,stageId);
	}
	
	public ArrayList<DTOStageWiseMailReport> getNodeStageDetailForESignatureNextUser(String wsId,int nodeId,int stageId) {
		return workSpaceUserRightsMst.getNodeStageDetailForESignatureNextUser(wsId,nodeId,stageId);
	}
	
	public ArrayList<DTOTimelineWorkspaceUserRightsMst> getProjectStageDetail() {
		return workSpaceUserRightsMst.getProjectStageDetail();
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getAttachedUserList(String wsId, int nodeId, int userCode) {
		return workSpaceUserRightsMst.getAttachedUserList(wsId, nodeId,userCode);
	}
	
	public boolean getCompletedNodeStageDetail(String wsId,int nodeId,int stageId) {
		return workSpaceUserRightsMst.getCompletedNodeStageDetail(wsId,nodeId,stageId);
	}
	public boolean getNextStageFlag(String wsId, int nodeId,int userCode){
		return workSpaceUserRightsMst.getNextStageFlag(wsId,nodeId,userCode);
	} 
	public boolean getNextStageFlagByUser(String wsId, int nodeId,int userCode){
		return workSpaceUserRightsMst.getNextStageFlagByUser(wsId,nodeId,userCode);
	}
	public Vector<DTOWorkSpaceUserRightsMst> checkRights(String wsId, int nodeId,int userId,int userGroupCode) {
		return workSpaceUserRightsMst.checkRights(wsId,nodeId,userId,userGroupCode);
	}
	public void insertintoWSUserRightsMst(DTOWorkSpaceUserRightsMst dto) {
		workSpaceUserRightsMst.insertintoWSUserRightsMst(dto);
	}
	public Vector<DTOWorkSpaceUserRightsMst> checkUserRights(String wsId, int nodeId, String Users) {
		return workSpaceUserRightsMst.checkUserRights(wsId,nodeId, Users);
	}
	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForsrcDocRmd(String wsId,int nodeId,int userCode) {
		return workSpaceUserRightsMst.getUserRightsDetailForsrcDocRmd(wsId, nodeId,userCode);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst>getProjectForUserDetail(int userCode,int UserGroupCode,String wsId) {
		return workSpaceUserRightsMst.getProjectForUserDetail(userCode,UserGroupCode,wsId);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> UserDataForBulkAllocation(String wsId){
		return workSpaceUserRightsMst.UserDataForBulkAllocation(wsId);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst>getUserAllocationDetails(int userCode,String wsId) {
		return workSpaceUserRightsMst.getUserAllocationDetails(userCode, wsId);
	}
	
	public Vector<DTOWorkSpaceUserRightsMst>getUserlistForReplicateRights(String wsId,int nodeId) {
		return workSpaceUserRightsMst.getUserlistForReplicateRights(wsId,nodeId);
	}
	
	
	// /////////////////////Template Master///////////////////

	public Vector<DTOTemplateMst> getAllTemplates() {
		return templateMst.getAllTemplates();
	}
	public Vector<DTOTemplateMst> getTemplates() {
		return templateMst.getTemplates();
	}
	public Vector<DTOTemplateMst> getTemplatesForCR() {
		return templateMst.getTemplatesForCR();
	}
	public Vector<DTOTemplateMst> getAllTemplatesForDocuments() {
		return templateMst.getAllTemplatesForDocuments();
	}
	public Vector<DTOTemplateMst> getAllTemplatesByDept(String deptcode) {
		return templateMst.getAllTemplatesByDept(deptcode);
	}
	
	public Vector<DTOTemplateMst> PriviewAllTemplates() {
		return templateMst.PriviewAllTemplates();
	}

	public Vector<DTOTemplateMst> getTemplateDetailById(String templateCode) {
		return templateMst.getTemplateDetailById(templateCode);
	}

	public String insertTemplateDtl(String vTemplateDesc, String vRemark,
			int userCode) {
		return templateMst.insertTemplateDtl(vTemplateDesc, vRemark, userCode);
	}

	public Vector<Object[]> getTemplateForTreeDisplay(String TemplateId) {
		return templateMst.getTemplateForTreeDisplay(TemplateId);
	}

	public int getTotalTemplateRecordCount() {
		return templateMst.getTotalTemplateRecordCount();

	}

	public Vector<DTOTemplateMst> getAllTemplatesDetail(int maxCount,
			int firstRecordCount,int givenPageNumber,String SortOrder) {
		return templateMst.getAllTemplatesDetail(maxCount, firstRecordCount,
				givenPageNumber,SortOrder);
	}

	public void updateTemplateMst(DTOTemplateMst dto) {
		templateMst.updateTemplateMst(dto);
	}

	// /////////////templateNodeDetail Master\\\\\\\\\\\\\\\\\\\\\\\\\\

	public void insertTemplateNodeDetail(DTOTemplateNodeDetail dto) {
		templateNodeDetail.insertTemplateNodeDetail(dto);
	}

	public Vector getTemplateNodeDetailByNodeId(String templateId, int nodeId) {
		return templateNodeDetail.getTemplateNodeDetailByNodeId(templateId,
				nodeId);
	}

	public void updateTemplateDetail(DTOTemplateNodeDetail dto) {
		templateNodeDetail.updateTemplateDetail(dto);
	}

	public void addChildNodeForStructure(DTOTemplateNodeDetail dto) {
		templateNodeDetail.addChildNodeForStructure(dto);
	}

	public void addChildNodeBeforeForStructure(DTOTemplateNodeDetail dto) {
		templateNodeDetail.addChildNodeBeforeForStructure(dto);
	}

	public void addChildNodeAfterForStructure(DTOTemplateNodeDetail dto) {
		templateNodeDetail.addChildNodeAfterForStructure(dto);
	}

	public void CopyPasteStructure(String sourceTempId, int sourceNodeId,
			String destTempId, int destNodeId, int userCode, Date modifyOn) {
		templateNodeDetail.CopyPasteStructure(sourceTempId, sourceNodeId,
				destTempId, destNodeId, userCode, modifyOn);
	}

	public void deleteTemplateNode(String templateId, int nodeId) {
		templateNodeDetail.deleteTemplateNode(templateId, nodeId);
	}

	public int getmaxTemplateNodeId(String templateId) {
		return templateNodeDetail.getmaxTemplateNodeId(templateId);
	}

	public void InsertTemplateNodeForXml(Vector NodeData) {
		templateNodeDetail.InsertTemplateNodeForXml(NodeData);
	}

	public static DTOTemplateNodeDetail getTemplateNodeDtlTree(
			String templateId, int nodeId) {
		return TemplateNodeDetail.getTemplateNodeDtlTree(templateId, nodeId);
	}

	public static DTOTemplateNodeDetail getTemplateRootNode(String templateId) {
		return TemplateNodeDetail.getTemplateRootNode(templateId);
	}
	
	public int isLeafNode(String templateId, int nodeId) {
		return templateNodeDetail.isLeafNode(templateId, nodeId);
	}

	// ///////////////////////templateNodeAttrDetail
	// Master\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public void insertIntoTemplateNodeAttrDtl(DTOTemplateNodeAttrDetail dto,
			int mode) {
		templateNodeAttrDetail.insertIntoTemplateNodeAttrDtl(dto, mode);
	}

	public Vector getTemplateNodeAttrDtl(DTOTemplateNodeAttrDetail dto) {
		return templateNodeAttrDetail.getTemplateNodeAttrDtl(dto);
	}

	public Vector setBulkAttributeValueOnTemplate(String templateId, int attrId) {
		return templateNodeAttrDetail.setBulkAttributeValueOnTemplate(
				templateId, attrId);
	}

	public void updateTemplateNodeAttrValue(DTOTemplateNodeAttrDetail dto) {
		templateNodeAttrDetail.updateTemplateNodeAttrValue(dto);
	}

	public void InsertNodeAttributeFromXml(Vector NodeAttrData) {
		templateNodeAttrDetail.InsertNodeAttributeFromXml(NodeAttrData);
	}

	// ////////////////WorkSpace Node Attribute Detail/////////////////

	public Vector<DTOWorkSpaceNodeAttrDetail> getAttrForNode(String wsId,
			int nodeId) {
		return workspaceNodeAttrDetail.getAttrForNode(wsId, nodeId);
	}

	public void updateNodeAttrDetail(Vector<DTOWorkSpaceNodeAttrDetail> attrDtl) {
		workspaceNodeAttrDetail.updateNodeAttrDetail(attrDtl);
	}
	
	public void updateNodeAttrDetailForModifiedUser(String wsId,int nodeId,int userId) {
		workspaceNodeAttrDetail.updateNodeAttrDetailForModifiedUser(wsId,nodeId,userId);
	}

	public void updateApprovedStatus(String status, String wsId, int iNodeId,
			int iTranNo) {
		workspaceNodeAttrDetail.updateApprovedStatus(status, wsId, iNodeId,
				iTranNo);
	}

	public Vector<DTOWorkSpaceNodeAttrDetail> getNodeAttrDetail(String wsId,
			int nodeId) {
		return workspaceNodeAttrDetail.getNodeAttrDetail(wsId, nodeId);
	}

	public Vector<DTOWorkSpaceNodeAttrDetail> getWsNodeAttrDetailHistoryForTable(String wsId,
			int nodeId) {
		return workspaceNodeAttrDetail.getWsNodeAttrDetailHistoryForTable(wsId, nodeId);
	}
	public ArrayList<List<String>> getAttributeDetails(String workSpaceId, Integer nodeId) {
		return workspaceNodeAttrDetail.getAttributeDetails(workSpaceId, nodeId); 
	}
	
	public List<List<String>> getAttributeDetailsForCSV(String workSpaceId, Integer nodeId,List<String> tableHeader) {
		return workspaceNodeAttrDetail.getAttributeDetailsForCSV(workSpaceId, nodeId,tableHeader); 
	}
	
	public List<List<String>> getAttributeDetailsForSection(String workSpaceId, Integer nodeId,List<String> tableHeader) {
		return workspaceNodeAttrDetail.getAttributeDetailsForSection(workSpaceId, nodeId,tableHeader); 
	}
	
	public Vector<Object []> getAttributeDetailsForDocSign(String workSpaceId, Integer nodeId,List<String> tableHeader) {
		return workspaceNodeAttrDetail.getAttributeDetailsForDocSign(workSpaceId, nodeId,tableHeader); 
	}
	
	public Vector<Object []> getAttributeDetailsForESignature(String workSpaceId, Integer nodeId,List<String> tableHeader) {
		return workspaceNodeAttrDetail.getAttributeDetailsForESignature(workSpaceId, nodeId,tableHeader); 
	}
	
	public List<List<String>> getAttributeDetailsForAttValue(String workSpaceId, Integer nodeId,List<String> tableHeader) {
		return workspaceNodeAttrDetail.getAttributeDetailsForAttValue(workSpaceId, nodeId,tableHeader); 
	}
	
	public List<List<String>> getAttributeDetailsForAttValueForESignature(String workSpaceId, Integer nodeId,List<String> tableHeader) {
		return workspaceNodeAttrDetail.getAttributeDetailsForAttValueForESignature(workSpaceId, nodeId,tableHeader); 
	}
	
	public Vector<DTOWorkSpaceNodeAttrDetail> getNodeAttrDetailForCSV(String wsId,
			int nodeId) {
		return workspaceNodeAttrDetail.getNodeAttrDetailForCSV(wsId, nodeId);
	}
		
	public void insertWorkspaceNodeAttrDetail(DTOWorkSpaceNodeAttrDetail dto) {
		workspaceNodeAttrDetail.insertWorkspaceNodeAttrDetail(dto);
	}

	public void updateWorkspaceNodeAttributeValue(DTOWorkSpaceNodeAttribute obj) {
		workspaceNodeAttrDetail.updateWorkspaceNodeAttributeValue(obj);
	}
	
	public Vector<DTOWorkSpaceNodeAttribute> getWsNodeAttrDetailHistory(String wsId,int nodeId) {
		return workspaceNodeAttrDetail.getWsNodeAttrDetailHistory(wsId,nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeAttribute> getWsNodeAttrDetailHistoryByAttrId(String wsId,int nodeId) {
		return workspaceNodeAttrDetail.getWsNodeAttrDetailHistoryByAttrId(wsId,nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeAttribute> getAttributeDetailForDisplay(
			String wsId, int nodeid) {
		return workspaceNodeAttrDetail.getAttributeDetailForDisplay(wsId,
				nodeid);
	}
	
	public Vector<DTOWorkSpaceNodeAttribute> getAttributeDetailForDisplayForOpenSign(
			String wsId, int nodeid) {
		return workspaceNodeAttrDetail.getAttributeDetailForDisplayForOpenSign(wsId,
				nodeid);
	}
	
	public Vector<DTOWorkSpaceNodeAttribute> getAttributeDetailForDisplayForESignature(String wsId, int nodeid) {
		return workspaceNodeAttrDetail.getAttributeDetailForDisplayForESignature(wsId,nodeid);
	}

	public Vector<DTOWorkSpaceNodeAttribute> getNodeAttributes(String wsId,
			int nodeId) {
		return workspaceNodeAttrDetail.getNodeAttributes(wsId, nodeId);
	}

	public Vector<DTOWorkSpaceNodeAttribute> getNodeAttributes_wsList(String wsId,int nodeId) {
		return workspaceNodeAttrDetail.getNodeAttributes_wsList(wsId, nodeId);
	}
	

	public void insertWorkSpaceNodeDetailForAttributeGroup(
			Vector<DTOAttributeGroupMatrix> attrMatrix, String wsId, int nodeId) {
		workspaceNodeAttrDetail.insertWorkSpaceNodeDetailForAttributeGroup(
				attrMatrix, wsId, nodeId);
	}

	public ArrayList<DTOWorkSpaceNodeAttribute> getWorkspaceAttributeValues(
			String attrName) {
		return workspaceNodeAttrDetail.getWorkspaceAttributeValues(attrName);
	}

	public void insertWorkspaceNodeAttrDetail(
			ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrList) {
		workspaceNodeAttrDetail.insertWorkspaceNodeAttrDetail(nodeAttrList);
	}

	public ArrayList<DTOWorkSpaceNodeAttrDetail> getAttrValByNameForWorkSpaceIds(
			ArrayList<String> workSpaceIds, String attrName) {
		return workspaceNodeAttrDetail.getAttrValByNameForWorkSpaceIds(
				workSpaceIds, attrName);
	}

	public ArrayList<String> getWorkspaceByAttributeValue(
			ArrayList<DTOAttributeMst> attrList) {
		return workspaceNodeAttrDetail.getWorkspaceByAttributeValue(attrList);
	}

	public ArrayList<DTOWorkSpaceNodeAttrDetail> getWorkspaceAttrDtlByAttrType(
			ArrayList<String> workSpaceIdList) {
		return workspaceNodeAttrDetail
				.getWorkspaceAttrDtlByAttrType(workSpaceIdList);
	}

	public Vector<DTOWorkSpaceNodeAttribute> getNodeAttrByAttrForIndiId(
			String wsId, int nodeId, String attrForIndiId) {
		return workspaceNodeAttrDetail.getNodeAttrByAttrForIndiId(wsId, nodeId,
				attrForIndiId);
	}

	public void deleteWorkspaceNodeAttrDetail(
			ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrList) {
		workspaceNodeAttrDetail.deleteWorkspaceNodeAttrDetail(nodeAttrList);
	}

	// ////////////////////checked out file details/////////////////////////

	public Vector<DTOCheckedoutFileDetail> getLockedFileDetail(String wsId,
			int nodeId, int userId) {
		return checkedOutFileDetails.getLockedFileDetail(wsId, nodeId, userId);

	}

	public Vector<DTOCheckedoutFileDetail> nodeCheckedOutBy(String wsId,
			int nodeId) {
		return checkedOutFileDetails.nodeCheckedOutBy(wsId, nodeId);
	}

	public int checkInOutNode(DTOCheckedoutFileDetail dtocheck) {
		int tranNo = workspaceMst.getTranNo(dtocheck.getWorkSpaceId());

		// checkedOutFileDetails.insertUpdateLockedFileDetail(wsId, nodeId,
		// tranNo, fileName, userId, fileTranNo-1);

		// procedure for insert so
		// Mode=1,StatusIndi= N,IsNodeLocked=Y

		CheckedOutFiledetailOp(dtocheck, 1);
		return tranNo;
	}

	public void unLockFiles(String wsId, int nodeId, int tranNo) {
		checkedOutFileDetails.unLockFiles(wsId, nodeId, tranNo);

		// CheckedOutFiledetailOp(wsId, nodeId, tranNo, fileName, userId,
		// fileTranNo,'Y','N' ,1);
	}

	// public void insertLockedFileDetail(String wsId, int nodeId, String
	// fileName, int userId, int fileTranNo)
	public void insertLockedFileDetail(DTOCheckedoutFileDetail dto) {
		int tranNo = workspaceMst.getTranNo(dto.getWorkSpaceId());
		dto.setTranNo(tranNo);
		checkedOutFileDetails.insertLockedFileDetail(dto);
	}

	public Vector<DTOCheckedoutFileDetail> getLockedFileDetailForAdmin(
			int userId, int userGroupId) {
		return checkedOutFileDetails.getLockedFileDetailForAdmin(userId,
				userGroupId);
	}

	public Vector<DTOCheckedoutFileDetail>getLockedFileDetailForAdminForCSV(String wsId,int nodeId,int tranNo,int userId,int userGroupId) {
		return checkedOutFileDetails.getLockedFileDetailForAdminForCSV(wsId,nodeId,tranNo,userId,userGroupId);
	}
	public void CheckedOutFiledetailOp(DTOCheckedoutFileDetail dto, int Mode) {
		checkedOutFileDetails.CheckedOutFiledetailOp(dto, Mode);
	}

	public Vector<DTOCheckedoutFileDetail> getLockedFileDetailForUser(int userId) {
		return checkedOutFileDetails.getLockedFileDetailForUser(userId);
	}

	public boolean isCheckOut(String wsId, int nodeId, int userId) {
		return checkedOutFileDetails.isCheckOut(wsId, nodeId, userId);
	}
	public Vector<DTOCheckedoutFileDetail> getMaxcheckOutFileDetail(String wsId,int nodeId) {
		return checkedOutFileDetails.getMaxcheckOutFileDetail(wsId, nodeId);
	}

	// //////////////////////////Workspacenode History//////////////////////
	public Vector<DTOWorkSpaceNodeHistory> getNodeHistory(String wsId,
			int nodeId) {
		return this.workSpaceNodeHistory.getNodeHistory(wsId, nodeId);
	}
	
	public String getNodeHistoryForUserCode(String wsId, int nodeId,int uCode) {
		return workSpaceNodeHistory.getNodeHistoryForUserCode(wsId, nodeId,uCode);
	}
	
	public Vector<DTOWorkSpaceNodeHistory> getNodeHistoryFolderName(String wsId, int nodeId) throws SQLException {
		return workSpaceNodeHistory.getNodeHistoryFolderName(wsId, nodeId);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> getCertifiedNodeHistory(String workspaceid, int nodeId, int tranNo) {
		return workSpaceNodeHistory.getCertifiedNodeHistory(workspaceid, nodeId,tranNo);
	}
	
	public int getCertifiedFlag(String workspaceid, int nodeId) {
		return workSpaceNodeHistory.getCertifiedFlag(workspaceid, nodeId);
	}
	
	public String getNodeHistoryForUserCodeForSendBack(String wsId, int nodeId,int uCode) {
		return workSpaceNodeHistory.getNodeHistoryForUserCodeForSendBack(wsId, nodeId,uCode);
	}
	
	public int getTranNoFromNodeId(String wsId, int nodeId,int uCode) {
		return workSpaceNodeHistory.getTranNoFromNodeId(wsId, nodeId,uCode);
	}
	
	public int getStageIdfromWSHistory(String wsId, int nodeId) {
		return workSpaceNodeHistory.getStageIdfromWSHistory(wsId, nodeId);
	}

	public void insertNodeHistory(DTOWorkSpaceNodeHistory workSpaceNodeHistory) {
		this.workSpaceNodeHistory.insertNodeHistory(workSpaceNodeHistory);
	}
	public void insertNodeHistoryWithRoleCode(DTOWorkSpaceNodeHistory workSpaceNodeHistory) {
		this.workSpaceNodeHistory.insertNodeHistoryWithRoleCode(workSpaceNodeHistory);
	}
	
	public void insertNodeHistoryWithRefFile(DTOWorkSpaceNodeHistory workSpaceNodeHistory) {
		this.workSpaceNodeHistory.insertNodeHistoryWithRefFile(workSpaceNodeHistory);
	}
	
	public void insertRefFileDetail(DTOWorkSpaceNodeHistory workSpaceNodeHistory) {
		this.workSpaceNodeHistory.insertRefFileDetail(workSpaceNodeHistory);
	}
	
	public void insertNodeHistoryWithCoordinates(DTOWorkSpaceNodeHistory workSpaceNodeHistory) {
		this.workSpaceNodeHistory.insertNodeHistoryWithCoordinates(workSpaceNodeHistory);
	}
	
	public void insertIntofileopenforsign(DTOWorkSpaceNodeHistory workSpaceNodeHistory) {
		this.workSpaceNodeHistory.insertIntofileopenforsign(workSpaceNodeHistory);
	}
	public int getfileopenforsignHistory(DTOWorkSpaceNodeHistory workSpaceNodeHistory) {
		return this.workSpaceNodeHistory.getfileopenforsignHistory(workSpaceNodeHistory);
	}
	public int getfileopenforsignHistoryForSignOff(DTOWorkSpaceNodeHistory workSpaceNodeHistory) {
		return this.workSpaceNodeHistory.getfileopenforsignHistoryForSignOff(workSpaceNodeHistory);
	}
	public void insertworkspacenodeofficehistory(
			DTOWorkSpaceNodeHistory workSpaceNodeHistory, int Mode) {
		this.workSpaceNodeHistory.insertworkspacenodeofficehistory(workSpaceNodeHistory,Mode);
	}
	public void insertsrcDocReminder(DTOWorkSpaceNodeHistory workspaceUserDetailList, int Mode) {
		this.workSpaceNodeHistory.insertsrcDocReminder(workspaceUserDetailList,Mode);
	}
	public void insertworkspacenodehistoryToUpdate(DTOWorkSpaceNodeHistory workSpaceNodeHistory,int mode) {
		this.workSpaceNodeHistory.insertworkspacenodehistoryToUpdate(workSpaceNodeHistory,mode);
	}
	
	public void updatePQSDocAutoSyncMst(DTOWorkSpaceNodeHistory workSpaceNodeHistory) {
		this.workSpaceNodeHistory.updatePQSDocAutoSyncMst(workSpaceNodeHistory);
	}
	
	public void deleteTraceblilityMatrixDoc(String workspaceID,int nodeId,String Automated_Doc_Type) {
		this.workSpaceNodeHistory.deleteTraceblilityMatrixDoc(workspaceID,nodeId,Automated_Doc_Type);
	}
	
	public Vector<DTOWorkSpaceNodeHistory> getWorkspaceNodeHistoryForOffice(String wsId,
			int nodeId) {
		return this.workSpaceNodeHistory.getWorkspaceNodeHistoryForOffice(wsId, nodeId);
	}
	public Vector<DTOWorkSpaceNodeHistory> getWorkspaceNodeHistoryForOfficeForFlagCheck(String wsId,
			int nodeId,int uCode) {
		return this.workSpaceNodeHistory.getWorkspaceNodeHistoryForOfficeForFlagCheck(wsId, nodeId,uCode);
	}
	public void insertTemplateNodeHistory(DTOWorkSpaceNodeHistory workSpaceNodeHistory) {
		this.workSpaceNodeHistory.insertTemplateNodeHistory(workSpaceNodeHistory);
	}
	public void insertUserSignature(DTOWorkSpaceNodeHistory workSpaceNodeHistory) {
		this.workSpaceNodeHistory.insertUserSignature(workSpaceNodeHistory);
	}
	public void insertAssignedNodeRight(String wsId,int nodeId,int tranNo,int iStageId,int userCode,int Mode,String flag) {
		this.workSpaceNodeHistory.insertAssignedNodeRight(wsId,nodeId,tranNo,iStageId,userCode,Mode,flag);
	}
	
	public void UpdateNodeHistoryForESign(String wsId,int nodeId, String signId,int tranNo) {
		this.workSpaceNodeHistory.UpdateNodeHistoryForESign(wsId,nodeId,signId,tranNo);
	}
	
	public void UpdateNodeHistoryForRoleCode(String wsId,int nodeId, String roleCode) {
		this.workSpaceNodeHistory.UpdateNodeHistoryForRoleCode(wsId,nodeId,roleCode);
	}
		
	public void UpdateWorkspaceNodeHistory(DTOWorkSpaceNodeHistory workSpaceNodeHistory) {
		this.workSpaceNodeHistory.UpdateWorkspaceNodeHistory(workSpaceNodeHistory);
	}

	public Vector<DTOWorkSpaceNodeHistory> getFileNameForNodeForPublish(
			int NodeId, String workSpaceId, int LabelNo) {
		return this.workSpaceNodeHistory.getFileNameForNodeForPublish(NodeId,
				workSpaceId, LabelNo);
	}
	
	public Vector<DTOAssignNodeRights> getAssignNodeRightsData(String wsId,int nodeId,int stageId) {
		return this.workSpaceNodeHistory.getAssignNodeRightsData(wsId,nodeId,stageId);
	}
	public Vector<DTOAssignNodeRights> getAllDataForNodeId(String wsId,int nodeId) {
		return this.workSpaceNodeHistory.getAllDataForNodeId(wsId,nodeId);
	}
	
	public Vector<DTOAssignNodeRights> getDataForNodeIdByTranNo(String wsId,int nodeId,int tranNo,int userCode) {
		return this.workSpaceNodeHistory.getDataForNodeIdByTranNo(wsId,nodeId,tranNo,userCode);
	}
	
	public Vector<DTOAssignNodeRights> getAllDataForNodeIdByUserId(String wsId,int nodeId,int userCode) {
		return this.workSpaceNodeHistory.getAllDataForNodeIdByUserId(wsId,nodeId,userCode);
	}
	//
	public DTOAssignNodeRights checkDataExistsForUser(String wsId,int nodeId,int stageId,int userCode){
		return this.workSpaceNodeHistory.checkDataExistsForUser(wsId,nodeId,stageId,userCode);
	}
	public Vector<DTOWorkSpaceNodeHistory> getFileNameForNode(int NodeId,
			String workSpaceId) {
		return workSpaceNodeHistory.getFileNameForNode(NodeId, workSpaceId);
	}

	public Vector<DTOWorkSpaceNodeHistory> getLastNodeHistory(String wsId,
			int nodeId) {
		return workSpaceNodeHistory.getLastNodeHistory(wsId, nodeId);
	}

	public DTOWorkSpaceNodeHistory getWorkspaceNodeHistorybyTranNoforAttribute(String wsId,
			int nodeId, int tranNo) {
		return workSpaceNodeHistory.getWorkspaceNodeHistorybyTranNoforAttribute(wsId,
				nodeId, tranNo);
	}
	
	public DTOWorkSpaceNodeHistory getWorkspaceNodeHistorybyTranNo(String wsId,
			int nodeId, int tranNo) {
		return workSpaceNodeHistory.getWorkspaceNodeHistorybyTranNo(wsId,
				nodeId, tranNo);
	}
	public DTOWorkSpaceNodeHistory getWorkspaceNodeHistorybyTranNoForHTMLPublish(String wsId,
			int nodeId, int tranNo) {
		return workSpaceNodeHistory.getWorkspaceNodeHistorybyTranNoForHTMLPublish(wsId,
				nodeId, tranNo);
	}
	public DTOWorkSpaceNodeHistory getWorkspaceNodeHistorybyTranNoForHTMLPublishWithTC(String wsId,
			int nodeId, int tranNo) {
		return workSpaceNodeHistory.getWorkspaceNodeHistorybyTranNoForHTMLPublishWithTC(wsId,
				nodeId, tranNo);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> getWorkspaceNodeHistory(String wsId,
			int nodeId, int tranNo) throws SQLException {
		return workSpaceNodeHistory.getWorkspaceNodeHistory(wsId,
				nodeId, tranNo);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> getWorkspaceNodeHistory(String wsId,int nodeId) throws SQLException {
		return workSpaceNodeHistory.getWorkspaceNodeHistory(wsId,nodeId);
	}
	
	public DTOWorkSpaceNodeHistory getWorkspaceNodeHistorybyTranNoForPDF(String wsId,
			int nodeId, int tranNo) {
		return workSpaceNodeHistory.getWorkspaceNodeHistorybyTranNoForPDF(wsId,
				nodeId, tranNo);
	}

	public DTOWorkSpaceNodeHistory getWorkspaceNodeHistorybyTranNoOpenAction(String wsId,
			int nodeId, int tranNo) {
		return workSpaceNodeHistory.getWorkspaceNodeHistorybyTranNoOpenAction(wsId,
				nodeId, tranNo);
	}

	public DTOWorkSpaceNodeHistory getWorkspaceNodeHistoryforAttr(String wsId,
			int nodeId,int flag) {
		return workSpaceNodeHistory.getWorkspaceNodeHistoryforAttr(wsId, nodeId, flag);
	}
	
	public int copyFilesForProjectSaveAs(DTOSaveProjectAs dto) {
		return workSpaceNodeHistory.copyFilesForProjectSaveAs(dto);
	}

	public int getMaxTranNo(String wsId, int nodeId) {
		return workSpaceNodeHistory.getMaxTranNo(wsId, nodeId);
	}
	public String checkWSnodeHistory(String wsId) {
		return workSpaceNodeHistory.checkWSnodeHistory(wsId);
	}
	public int getMaxTranNoForTemplate(String templateId, int nodeId) {
		return workSpaceNodeHistory.getMaxTranNoForTemplate(templateId, nodeId);
	}
	
	public int getMaxNodeHistory(String wsId, int nodeId) {
		return workSpaceNodeHistory.getMaxNodeHistory(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeHistory> getMaxNodeHistoryByTranNo(String wsId, int nodeId) {
		return workSpaceNodeHistory.getMaxNodeHistoryByTranNo(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeHistory> getMaxNodeHistoryByTranNo(String wsId, int nodeId,int tranNo) {
		return workSpaceNodeHistory.getMaxNodeHistoryByTranNo(wsId, nodeId,tranNo);
	}

	public Vector<DTOWorkSpaceNodeHistory> getMaxNodeHistoryByTranNoForEsignature(String wsId, int nodeId,int tranNo) {
		return workSpaceNodeHistory.getMaxNodeHistoryByTranNoForEsignature(wsId, nodeId,tranNo);
	}
	
	public Vector<DTOWorkSpaceNodeHistory> getFirstNodeHistoryByTranNo(String wsId, int nodeId) {
		return workSpaceNodeHistory.getFirstNodeHistoryByTranNo(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeHistory> getFirstNodeHistoryByTranNoAndCountryCode(String wsId, int nodeId,String location,String cCode) {
		return workSpaceNodeHistory.getFirstNodeHistoryByTranNoAndCountryCode(wsId, nodeId,location,cCode);
	}	
	
	public String checkSendForReview(String wsId, int nodeId) {
		return workSpaceNodeHistory.checkSendForReview(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeHistory> getNodeHistoryForDocSign(String wsId, int nodeId) {
		return workSpaceNodeHistory.getNodeHistoryForDocSign(wsId, nodeId);
	}
	
	public DTOWorkSpaceNodeHistory getNodeHistoryForSignOff(String wsId, int nodeId) {
		return workSpaceNodeHistory.getNodeHistoryForSignOff(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeHistory> getNodeHistoryForPQSDocAutoSync() {
		return workSpaceNodeHistory.getNodeHistoryForPQSDocAutoSync();
	}
	
	public int getMaxTranNoByStageId(String wsId, int nodeId) {
		return workSpaceNodeHistory.getMaxTranNoByStageId(wsId, nodeId);
	}

	public ArrayList<Integer> getNodeIdFromNodeHistory(String wsId) {
		return  workSpaceNodeHistory.getNodeIdFromNodeHistory(wsId);
	}

	
	public void updateStageStatus(DTOWorkSpaceNodeHistory dto) {
		workSpaceNodeHistory.updateStageStatus(dto);
	}

	public Vector<DTOWorkSpaceNodeHistory> getMyPendingWorksReport(
			DTOWorkSpaceNodeHistory dto) {
		return workSpaceNodeHistory.getMyPendingWorksReport(dto);
	}
	public Vector<DTOWorkSpaceNodeHistory> getMyPendingWorksReportCSV(
			DTOWorkSpaceNodeHistory dto) {
		return workSpaceNodeHistory.getMyPendingWorksReportCSV(dto);
	}
	public Vector<DTOWorkSpaceNodeHistory> getCurrentStageDesc(String wsId, int nodeId) {
		return workSpaceNodeHistory.getCurrentStageDesc(wsId,nodeId);
	}
	public Vector<DTOWorkSpaceNodeHistory> getSendBackFileDetail(
			DTOWorkSpaceNodeHistory dto) {
		return workSpaceNodeHistory.getSendBackFileDetail(dto);
	}
	
	public Vector<DTOWorkSpaceNodeHistory> getMypendingWorkNew(String wsId,int userCode) {
		return workSpaceNodeHistory.getMypendingWorkNew(wsId,userCode);
	}
	public ArrayList<DTOWorkSpaceNodeHistory> getPendingReportSingOff(
			DTOWorkSpaceNodeHistory dto) {
		return workSpaceNodeHistory.getPendingReportSingOff(dto);
	}
	public ArrayList<DTOWorkSpaceNodeHistory> getUpcomingFileReport(int userCode, int userGroupCode) {
		return workSpaceNodeHistory.getUpcomingFileReport(userCode,userGroupCode);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> getSrcDocReminderList(int userCode, int userGroupCode) {
		return workSpaceNodeHistory.getSrcDocReminderList(userCode,userGroupCode);
	}
	public Vector<DTOWorkSpaceNodeHistory> getMyPendingWorksReportForNextStage(
			DTOWorkSpaceNodeHistory dto) {
		return workSpaceNodeHistory.getMyPendingWorksReportForNextStage(dto);
	}

	public boolean copyfilefromsrctodest(String srcWsId, int sourceNodeId) {
		return workSpaceNodeHistory
				.copyfilefromsrctodest(srcWsId, sourceNodeId);
	}

	public void CopyFileForRepository(String srcWsId, String destWsId,
			int srcNodeId, int destNodeId, int userCode, int tranNo) {
		workSpaceNodeHistory.CopyFileForRepository(srcWsId, destWsId,
				srcNodeId, destNodeId, userCode, tranNo);
	}
	
	//Method added by Harsh Shah for downloading zip
		public String getBaseWorkFolder(String wsId, int nodeId,int transNo){
			return workSpaceNodeHistory.getBaseWorkFolder(wsId,nodeId,transNo);
		} 

	/*
	 * Method Added By : Ashmak Shah Added On : 12th may 2009
	 */
	public Vector<DTOWorkSpaceNodeHistory> getUploadedFileNodes(
			String workspaceid) {
		return workSpaceNodeHistory.getUploadedFileNodes(workspaceid);
	}

	public ArrayList<DTOWorkSpaceNodeHistory> showFullNodeHistory(
			String workspaceid, int nodeId) {
		return workSpaceNodeHistory.showFullNodeHistory(workspaceid, nodeId);
	}
	public ArrayList<DTOWorkSpaceNodeHistory> showFullNodeHistoryWithVoidFiles(String workspaceid, int nodeId) {
		return workSpaceNodeHistory.showFullNodeHistoryWithVoidFiles(workspaceid, nodeId);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> showFullNodeHistoryWithVoidFilesForESignature(String workspaceid, int nodeId) {
		return workSpaceNodeHistory.showFullNodeHistoryWithVoidFilesForESignature(workspaceid, nodeId);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> showFullNodeHistoryForESignatureByTranNo(String workspaceid, int nodeId,int tranNo) {
		return workSpaceNodeHistory.showFullNodeHistoryForESignatureByTranNo(workspaceid, nodeId,tranNo);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> showFullNodeHistoryForESignatureForUser(String workspaceid, int nodeId,int tranNo,String location,String country) {
		return workSpaceNodeHistory.showFullNodeHistoryForESignatureForUser(workspaceid, nodeId,tranNo,location,country);
	}

	public Vector<DTOWorkSpaceNodeHistory> getNodeHistoryForESignature(String workspaceid, int nodeId) {
		return workSpaceNodeHistory.getNodeHistoryForESignature(workspaceid, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeHistory> getReferenceHistoryForESignature(String workspaceid, int nodeId) {
		return workSpaceNodeHistory.getReferenceHistoryForESignature(workspaceid, nodeId);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> showTemplateNodeHistory(String templateId, int nodeId) {
		return workSpaceNodeHistory.showTemplateNodeHistory(templateId, nodeId);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> showNodeHistory(
			String workspaceid,int userCode,int nodeId,int stageId) {
		return workSpaceNodeHistory.showNodeHistory(workspaceid,userCode,nodeId,stageId);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> showNodeHistoryForESignature(String workspaceid,int userCode,int nodeId) {
		return workSpaceNodeHistory.showNodeHistoryForESignature(workspaceid,userCode,nodeId);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> showNodeHistoryForWs(
			String workspaceid,int userCode,int stageId) {
		return workSpaceNodeHistory.showNodeHistoryForWs(workspaceid,userCode,stageId);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> showNodeHistory(
			String workspaceid,int userCode) {
		return workSpaceNodeHistory.showNodeHistory(workspaceid,userCode);
	}
	
	public boolean showNodeHistoryForCSV(
			String wsId, int[] nodeId) {
		return workSpaceNodeHistory.showNodeHistoryForCSV(wsId, nodeId);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> getPDFSignOffDocDetail(
			String workspaceid, int nodeId) {
		return workSpaceNodeHistory.getPDFSignOffDocDetail(workspaceid, nodeId);
	}
	
	public String getCurrentSeq(String workspaceID, int nodeId){
		return workSpaceNodeHistory.getCurrentSeq(workspaceID,nodeId);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> getLatestNodeHistory(
			String workspaceid, int nodeId) {
		return workSpaceNodeHistory.getLatestNodeHistory(workspaceid, nodeId);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> showFullNodeHistoryByStageId(
			String workspaceid, int nodeId, int tranNo) {
		return workSpaceNodeHistory.showFullNodeHistoryByStageId(workspaceid, nodeId,tranNo);
	}
	
	public Vector<DTOWorkSpaceNodeHistory>showFullNodeHistoryForLambda(
			String workspaceid, int nodeId) {
		return workSpaceNodeHistory.showFullNodeHistoryForLambda(workspaceid, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeHistory>showFullNodeHistoryForCSV(
			String workspaceid, int nodeId) {
		return workSpaceNodeHistory.showFullNodeHistoryForCSV(workspaceid, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeHistory>getProjectTrackingHistory(
			String workspaceid, int nodeId) {
		return workSpaceNodeHistory.getProjectTrackingHistory(workspaceid, nodeId);
	}

	public Vector<DTOWorkSpaceNodeHistory>getProjectTrackingHistoryForTimeLine(
			String workspaceid, int nodeId) {
		return workSpaceNodeHistory.getProjectTrackingHistoryForTimeLine(workspaceid, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeHistory>getProjectSignOffHistory(String workspaceid, int nodeId) {
		return workSpaceNodeHistory.getProjectSignOffHistory(workspaceid, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeHistory>getProjectSignOffHistoryForESignature(String workspaceid, int nodeId) {
		return workSpaceNodeHistory.getProjectSignOffHistoryForESignature(workspaceid, nodeId);
	}

	public Vector<DTOWorkSpaceNodeHistory>getWorkspacenodeHistoryByUserId(String workspaceid, int nodeId,int uId) throws SQLException {
		return workSpaceNodeHistory.getWorkspacenodeHistoryByUserId(workspaceid, nodeId,uId);
	}
	
	public Vector<DTOWorkSpaceNodeHistory>getWorkspacenodeHistoryByUserIdForFile(String workspaceid, int nodeId,int uId) throws SQLException {
		return workSpaceNodeHistory.getWorkspacenodeHistoryByUserIdForFile(workspaceid, nodeId,uId);
	}
	
	public DTOWorkSpaceNodeHistory getLatestNodeSubHistory(String workspaceid,
			int nodeId) {
		return workSpaceNodeHistory
				.getLatestNodeSubHistory(workspaceid, nodeId);
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getAllNodesLastHistory(
			String workspaceid, int[] nodeIds) {
		return workSpaceNodeHistory
				.getAllNodesLastHistory(workspaceid, nodeIds);
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getAllNodesLastHistory(
			ArrayList<String> workspaceIds, int[] nodeIds) {
		return workSpaceNodeHistory.getAllNodesLastHistory(workspaceIds,
				nodeIds);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> getAllNodesLastHistoryForPDFPublish(
			ArrayList<String> workspaceIds, int[] nodeIds) {
		return workSpaceNodeHistory.getAllNodesLastHistoryForPDFPublish(workspaceIds,
				nodeIds);
	}
	
	public ArrayList<DTOWorkSpaceNodeHistory> getAllNodesLastHistoryForPDFPublishForCSV(
			String workspaceId) {
		return workSpaceNodeHistory.getAllNodesLastHistoryForPDFPublishForCSV(workspaceId);
	}
	
	public Vector<DTOWorkSpaceNodeHistory> getUserRightsDetailForCSV(
			String workspaceId, int nodeIds) {
		return workSpaceNodeHistory.getUserRightsDetailForCSV(workspaceId,
				nodeIds);
	}


	public Vector<DTOWorkSpaceNodeHistory> getPDFDetails(String wsId,
			String[] extensions) {
		return workSpaceNodeHistory.getPDFDetails(wsId, extensions);
	}

	public Vector<DTOWorkSpaceNodeHistory> getPDFPublishableFilesAfterLastConfirmSeq(
			String wsId) {
		return workSpaceNodeHistory
				.getPDFPublishableFilesAfterLastConfirmSeq(wsId);
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getPublishableFilesAfterLastConfirmSeq(
			String wsId) {
		return workSpaceNodeHistory
				.getPublishableFilesAfterLastConfirmSeq(wsId);
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getNodeHistoryStageWiseUserWise(
			String wsId, int nodeId, int stageId, int userCode,
			boolean maxTranData) {
		return workSpaceNodeHistory.getNodeHistoryStageWiseUserWise(wsId,
				nodeId, stageId, userCode, maxTranData);
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getRefWorkspaceNodes(
			String workspaceId, int nodeId) {
		return workSpaceNodeHistory.getRefWorkspaceNodes(workspaceId, nodeId);
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getNodeDetailedHistory(
			String wsId, int nodeId) {
		return workSpaceNodeHistory.getNodeDetailedHistory(wsId, nodeId);
	}
	
	public int getStageDescription(String wsId, int nodeId,int transNo)
	{
		return workSpaceNodeHistory.getStageDescription(wsId, nodeId,transNo);
	}
	
	public String getStageDesc(int stageId)
	{
		return workSpaceNodeHistory.getStageDesc(stageId);
	}
	
	public String getStageDescForESignature(int stageId)
	{
		return workSpaceNodeHistory.getStageDescForESignature(stageId);
	}
	
	public String getStageDescNCurrentSeq(
			String workspaceid, int nodeId,int maxTransNo){
		return workSpaceNodeHistory.getStageDescNCurrentSeq(workspaceid,nodeId,maxTransNo);
	}
	public DTOWorkSpaceNodeHistory getMyPendingWorksReportForSingleNextStage(
			String wsId,int userId,int nodeId){
		return workSpaceNodeHistory.getMyPendingWorksReportForSingleNextStage(
				 wsId,userId,nodeId);
	}
	
	public String getFileName(String wsId,int nodeId){
		return workSpaceNodeHistory.getFileName(wsId,nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeHistory> getVoidFileHistory(String wsId, int nodeId) {
		return workSpaceNodeHistory.getVoidFileHistory(wsId,nodeId);
	}
	
	public Vector <DTOWorkSpaceNodeHistory> getWorkspaceNodeHistoryForVoid(String wsId, int nodeId) {
		return workSpaceNodeHistory.getWorkspaceNodeHistoryForVoid(wsId,nodeId);
	}
	
	public void insertWSNodeVoidDetail(DTOWorkSpaceNodeHistory dto) {
		this.workSpaceNodeHistory.insertWSNodeVoidDetail(dto);
	}
	public Vector<DTOWorkSpaceNodeHistory> getNodeHistoryForSectinDeletion(String wsId,
			int nodeId) {
		return this.workSpaceNodeHistory.getNodeHistoryForSectinDeletion(wsId, nodeId);
	}
	public Vector <DTOWorkSpaceNodeHistory> getUserSignatureDetail(int userId) {
		return workSpaceNodeHistory.getUserSignatureDetail(userId);
	}
	
	public Vector <DTOWorkSpaceNodeHistory> getUserSignatureDetailByCountry(int userId,String location,String cCode) {
		return workSpaceNodeHistory.getUserSignatureDetailByCountry(userId,location,cCode);
	}
	
	public Vector <DTOWorkSpaceNodeHistory> getAllUserSignatureDetail(int userId) {
		return workSpaceNodeHistory.getAllUserSignatureDetail(userId);
	}
	
	public String getDocumentHashChainMst(String wsId,int nId,int tranNo,int userCode) {
		return workSpaceNodeHistory.getDocumentHashChainMst(wsId,nId,tranNo,userCode);
	}
	
	public int getTranNoForSign(String wsId,int nId,String docId) {
		return workSpaceNodeHistory.getTranNoForSign(wsId,nId,docId);
	}
	public String getDocIdForSign(String wsId,int nId,int tranNo) {
		return workSpaceNodeHistory.getDocIdForSign(wsId,nId,tranNo);
	}
	
	public Vector <DTOWorkSpaceNodeHistory> getLeafNode(String wsId) {
		return workSpaceNodeHistory.getLeafNode(wsId);
	}
	
	public float getPdfpublishSize(String wsId) {
		return workSpaceNodeHistory.getPdfpublishSize(wsId);
	}
	
	public Vector<DTOWorkSpaceNodeHistory> getnodeId (String WorkspaceId,int userCode,int userGrpCode){
		return workSpaceNodeHistory.getnodeId(WorkspaceId,userCode,userGrpCode);
	}
	
	public DTOWorkSpaceNodeHistory eSignPendingforyesSeq(String WorkspaceId,int userCode,int tranno){
		return workSpaceNodeHistory.eSignPendingforyesSeq(WorkspaceId,userCode,tranno);
	}
	
	public DTOWorkSpaceNodeHistory checkeSign(String WorkspaceId,int userCode,int nodeId,int seqno){
		return workSpaceNodeHistory.checkeSign(WorkspaceId,userCode,nodeId,seqno);
	}
	
	public DTOWorkSpaceNodeHistory checkeSignForCurrentUser(String WorkspaceId,int userCode,int nodeId,int seqno){
		return workSpaceNodeHistory.checkeSignForCurrentUser(WorkspaceId,userCode,nodeId,seqno);
	}
	
	public int checkPrevUserForeSign(String WorkspaceId,int nodeId,int seqno){
		return workSpaceNodeHistory.checkPrevUserForeSign(WorkspaceId,nodeId,seqno);
	}
	
	public DTOWorkSpaceNodeHistory checkeSignData(String WorkspaceId,int nodeId,int seqno){
		return workSpaceNodeHistory.checkeSignData(WorkspaceId,nodeId,seqno);
	}
	
	
	public DTOWorkSpaceNodeHistory Esigndatafornoseq(String WorkspaceId,int userCode,int nodeid){
		return workSpaceNodeHistory.Esigndatafornoseq(WorkspaceId,userCode,nodeid);
	}
	
	public boolean flagCheckforData(String WorkspaceId,int userCode,int nodeid){
		return workSpaceNodeHistory.flagCheckforData(WorkspaceId,userCode,nodeid);
	}
	
	
	
	// //////////////////////workspacenodeversion history////////////////
	public Vector<DTOWorkSpaceNodeVersionHistory> getMaxWsNodeVersionDetail(
			DTOWorkSpaceNodeVersionHistory objWSNodeVersionHistory) {
		return workSpaceNodeVersionHistory
				.getMaxWsNodeVersionDetail(objWSNodeVersionHistory);
	}

	public void insertWorkspaceNodeVersionHistory(
			DTOWorkSpaceNodeVersionHistory objwsnodeversionhistory) {
		workSpaceNodeVersionHistory
				.insertWorkspaceNodeVersionHistory(objwsnodeversionhistory);
	}

	public void insertWorkspaceNodeVersionHistory(
			DTOWorkSpaceNodeVersionHistory dto, boolean autoVersion) {
		workSpaceNodeVersionHistory.insertWorkspaceNodeVersionHistory(dto,
				autoVersion);
	}

	public int getMaxTranNo(DTOWorkSpaceMst dto) {
		return workSpaceNodeVersionHistory.getMaxTranNo(dto);
	}

	public Vector<DTOWorkSpaceNodeVersionHistory> getFileVersionAndCommentReport(
			DTOWorkSpaceNodeDetail objWorkSpaceNodeDetail) {
		return workSpaceNodeVersionHistory
				.getFileVersionAndCommentReport(objWorkSpaceNodeDetail);
	}

	public Vector<DTOWorkSpaceNodeVersionHistory> getAuditTrailReport(
			DTOWorkSpaceNodeDetail objWorkSpaceNodeDetail) {
		return workSpaceNodeVersionHistory
				.getAuditTrailReport(objWorkSpaceNodeDetail);
	}

	public Vector<DTOWorkSpaceNodeVersionHistory> getWorkSpaceNodeVersionDetail(
			DTOWorkSpaceNodeVersionHistory objWSNodeVersionHistory) {
		return workSpaceNodeVersionHistory
				.getWorkSpaceNodeVersionDetail(objWSNodeVersionHistory);
	}

	public Vector<DTOWorkSpaceNodeVersionHistory> getNodeVersionHistory(
			DTOWorkSpaceNodeVersionHistory dto) {
		return workSpaceNodeVersionHistory.getNodeVersionHistory(dto);
	}

	public void setLastClosedFlag(String workspaceId, int nodeId,
			int versiontranNo, int tranNo) {
		workSpaceNodeVersionHistory.setLastClosedFlag(workspaceId, nodeId,
				versiontranNo, tranNo);
	}

	public DTOWorkSpaceNodeVersionHistory getNodeVersionHistoryByTranNo(
			String workspaceId, int nodeId, int tranNo) {
		return workSpaceNodeVersionHistory.getNodeVersionHistoryByTranNo(
				workspaceId, nodeId, tranNo);
	}	

	// /////////////////workspacenode attribute history///////////////////////

	public Vector<DTOWorkSpaceNodeAttrHistory> getUserDefinedWorkspaceNodeAttrHistory(
			String workspaceId, int NodeId) {
		return workSpaceNodeAttributeHistory
				.getUserDefinedWorkspaceNodeAttrHistory(workspaceId, NodeId);
	}
	
	public Vector<DTOWorkSpaceNodeAttrHistory> getUserDefinedNodeAttrHistory(
			String workspaceId, int NodeId) {
		return workSpaceNodeAttributeHistory
				.getUserDefinedNodeAttrHistory(workspaceId, NodeId);
	}

	public void InsertUpdateNodeAttrHistory(
			Vector<DTOWorkSpaceNodeAttrHistory> attrHistoryVect) {
		workSpaceNodeAttributeHistory
				.InsertUpdateNodeAttrHistory(attrHistoryVect);
	}

	public void updateSTFVersion(DTOWorkSpaceNodeAttrHistory dto) {
		workSpaceNodeAttributeHistory.updateSTFVersion(dto);
	}

	public Vector<DTOWorkSpaceNodeAttrHistory> getAttributesForNodeForPublish(
			int NodeId, String workSpaceId, int LabelNo, String AttrForIndiId) {
		return workSpaceNodeAttributeHistory.getAttributesForNodeForPublish(
				NodeId, workSpaceId, LabelNo, AttrForIndiId);
	}

	public Vector<DTOWorkSpaceNodeAttrHistory> getWorkspaceNodeAttrHistorybyTranNo(
			String workspaceId, int nodeId, int tranNo) {
		return workSpaceNodeAttributeHistory
				.getWorkspaceNodeAttrHistorybyTranNo(workspaceId, nodeId,
						tranNo);
	}

	public Vector<DTOWorkSpaceNodeAttrDetail> getAllNodeAttrDetail(String wsId) {
		return workspaceNodeAttrDetail.getAllNodeAttrDetail(wsId);
	}
	
	public Vector<DTOWorkSpaceNodeAttrDetail> getNodedetailwithAttr(String wsId) {
		return workspaceNodeAttrDetail.getNodedetailwithAttr(wsId);
	}
	public Vector<DTOWorkSpaceNodeAttrDetail>UserModuleWiseAttributeDetail(String wsId,int attrid,int userid) {
		return workspaceNodeAttrDetail.UserModuleWiseAttributeDetail( wsId,attrid,userid);
	}
	public Vector<DTOWorkSpaceNodeAttrDetail>getProjectFailScriptDetail(String wsId) {
		return workspaceNodeAttrDetail.getProjectFailScriptDetail( wsId);
	}
	
	public void insertNodeAttrHistory(
			Vector<DTOWorkSpaceNodeAttrHistory> attrHistoryVect) {
		workSpaceNodeAttributeHistory.insertNodeAttrHistory(attrHistoryVect);
	}

	public void UpdateTranNoForStageInAttrHistory(String WorkspaceId,
			int NodeId, int TranNo) {
		workSpaceNodeAttributeHistory.UpdateTranNoForStageInAttrHistory(
				WorkspaceId, NodeId, TranNo);
	}

	public void copyLeafAttributes(String srcWsId, String destWsId,
			int srcNodeId, int destNodeId, int userId, int TranNo) {
		workSpaceNodeAttributeHistory.copyLeafAttributes(srcWsId, destWsId,
				srcNodeId, destNodeId, userId, TranNo);
	}

	public Vector<DTOWorkSpaceNodeAttrHistory> getLatestNodeAttrHistory(
			String workspaceId, int nodeId, String AttrForIndiId) {
		return workSpaceNodeAttributeHistory.getLatestNodeAttrHistory(
				workspaceId, nodeId, AttrForIndiId);
	}

	// //////Internal Label master///////////////////////////
	public Vector<DTOInternalLabelMst> viewLabelUsingWorkspaceId(String workspaceId) {
		return internalLableMst.viewLabelUsingWorkspaceId(workspaceId);
	}

	public void createInternalLabel(DTOInternalLabelMst dto) {
		internalLableMst.createInternalLabel(dto);
	}

	public Vector viewNodeDetailUsingWsIdAndLabelNo(String workspaceId,
			int labelNo) {
		return internalLableMst.viewNodeDetailUsingWsIdAndLabelNo(workspaceId,
				labelNo);
	}

	public ArrayList<DTOWorkSpaceNodeDetail> getDetailOfSubmission(String wsId,
			int labelNo) {
		return internalLableMst.getDetailOfSubmission(wsId, labelNo);
	}

	public String lastLabel(String wsId) {
		return internalLableMst.lastLabel(wsId);
	}

	public boolean labelNameExist(String workspaceId, String labelId) {
		return internalLableMst.labelNameExist(workspaceId, labelId);
	}

	public DTOInternalLabelMst getMaxWorkspaceLabel(String workspaceId) {
		return internalLableMst.getMaxWorkspaceLabel(workspaceId);
	}

	// /////////////////////Node Status Master////////////////////
	public boolean insertIntoNodeStatus(DTONodeStatus dto) {
		return nodeStatus.insertIntoNodeStatus(dto);
	}

	// //////STFCategory Master///////////////////////////

	public Vector<DTOSTFCategoryMst> getAllSTFCategory() {
		return stfCategoryMst.getAllSTFCategory();
	}

	// Added by : Ashmak Shah
	// Added On : 16 June 2009
	public Vector<DTOSTFCategoryAttrValueMatrix> getSTFCategoryValues(
			String CategoryId) {
		return stfCategoryMst.getSTFCategoryValues(CategoryId);
	}

	// //////STFStudyIdentifierMaster///////////////////////////

	// Modified by : Ashmak Shah
	// Modified On : 17 June 2009
	public int getMaxTagId(String vWorkSpaceId, int iNodeId) {
		return stfStudyIdentifierMst.getMaxTagId(vWorkSpaceId, iNodeId);
	}

	public void insertIntoSTFStudyIdentifierMst(DTOSTFStudyIdentifierMst dto,
			int MODE) {
		stfStudyIdentifierMst.insertIntoSTFStudyIdentifierMst(dto, MODE);
	}

	public Vector<DTOSTFStudyIdentifierMst> getSTFIdentifierByNodeId(
			String wsId, int iNodeId) {
		return stfStudyIdentifierMst.getSTFIdentifierByNodeId(wsId, iNodeId);
	}

	public DTOSTFStudyIdentifierMst getSTFIdentifierOfNodeByTagNameAndAttrName(
			String wsId, int iNodeId, String tagName, String attrName) {
		return stfStudyIdentifierMst
				.getSTFIdentifierOfNodeByTagNameAndAttrName(wsId, iNodeId,
						tagName, attrName);
	}

	// //////STFNode Master///////////////////////////
	public Vector<DTOSTFNodeMst> getAllSTFNodes() {
		return stfNodeMst.getAllSTFNodes();
	}

	public DTOSTFNodeMst getSTFNodeById(String STFNodeId) {
		return stfNodeMst.getSTFNodeById(STFNodeId);
	}

	public Vector<DTOSTFNodeMst> getMulipleFlagedSTFNodes() {
		return stfNodeMst.getMulipleFlagedSTFNodes();
	}

	public void insertIntoSTFNodeMst(DTOSTFNodeMst stfnodemst) {
		stfNodeMst.insertIntoSTFNodeMst(stfnodemst);
	}

	// //////////forum master //////////////////////
	public String insertForumComment(
			DTOWorkSpaceNodeDetail objWorkSpaceNodeDetail, String mess,
			int modifyBy) {
		return forumMst.insertForumComment(objWorkSpaceNodeDetail, mess,
				modifyBy);
	}

	public Vector<DTOForumDtl> showNodeComments(DTOWorkSpaceNodeDetail dto) {
		return forumMst.showNodeComments(dto);
	}
	
	public Vector<DTOForumDtl>showFullCommentHistoryForLambda(
			String workspaceid, int nodeId) {
		return forumMst.showFullCommentHistoryForLambda(workspaceid, nodeId);
	}
	
	public Vector<DTOForumDtl> getActivityCommentsReport(
			DTOWorkSpaceNodeDetail objWorkSpaceNodeDetail) {
		return forumMst.getActivityCommentsReport(objWorkSpaceNodeDetail);
	}

	public ArrayList<DTOForumDtl> getComments(int iReceiverUserCode) {
		return forumMst.getComments(iReceiverUserCode);
	}

	public ArrayList<DTOForumDtl> getComments(int iReceiverUserCode,
			int pageNo, int noOfRecords,int nodeId,String workspaceId) {
		return forumMst.getComments(iReceiverUserCode, pageNo, noOfRecords,nodeId,workspaceId);
	}

	public int getNumOfComments(int iReceiverUserCode, int noOfRecords,int nodeId,String workspaceId) {
		return forumMst.getNumOfComments(iReceiverUserCode, noOfRecords,nodeId,workspaceId);
	}
	/*public ArrayList<Integer> checkResolvedComments(int nodeId,String workspaceId){
		return forumMst.checkResolvedComments(nodeId,workspaceId);
	}
*/
	// TODO
	/*
	 * public int getNumOfSentComments(int iReceiverUserCode,int noOfRecords) {
	 * return forumMst. }
	 */
	public ArrayList<DTOForumDtl> getSentComments(int iReceiverUserCode) {
		return forumMst.getSentComments(iReceiverUserCode);
	}

	public ArrayList<DTOForumDtl> getDeletedComments(int iReceiverUserCode) {
		return forumMst.getDeletedComments(iReceiverUserCode);
	}

	public String insertForumComment(String workspaceId, int nodeId,
			int rUserCode, String message, String replyFlag, String refSubId,String Uuid) {
		return forumMst.insertForumComment(workspaceId, nodeId, rUserCode,
				message, replyFlag, refSubId,Uuid);
	}
	public String getupdatedNodedetails(int nodeId,String WorksapceId,String SubjectId)
	{
		return forumMst.getupdatedNodedetails(nodeId,WorksapceId,SubjectId);
	}
	public void updateForumDetails(String SubjectId,String workspaceId,int nodeId,int usercode) {
		forumMst.updateForumDetails(SubjectId,workspaceId,nodeId,usercode);
	}

	public String insertForumDetails(DTOForumDtl comment, int mode) {
		return forumMst.insertForumDetails(comment, mode);
	}

	public ArrayList<DTOForumDtl> getSentComments(int userCode,
			String workspaceID, int nodeId) {
		return forumMst.getSentComments(userCode, workspaceID, nodeId);
	}
	public ArrayList<DTOForumDtl> getAllComments(String workspaceID, int nodeId) {
		return forumMst.getAllComments(workspaceID, nodeId);
	}
	public int getCommentsCount(int iReceiverUserCode){
		  return forumMst.getCommentsCount(iReceiverUserCode);	
		}
	// /////////////////// HotSerachDetail
	// Master\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	public Vector getClientForSearch(int UserId) {
		return hotSearchDetail.getClientForSearch(UserId);
	}

	public Vector getLocationForSearch(int UserId) {
		return hotSearchDetail.getLocationForSearch(UserId);
	}

	public Vector getProjectForSearch(int UserId) {
		return hotSearchDetail.getProjectForSearch(UserId);
	}

	public Vector getUserDetailsByUserGrp(DTOUserMst userMst) {
		return hotSearchDetail.getUserDetailsByUserGrp(userMst);
	}

	public Vector getGenericSearchResult(String locId, String clientId,
			String prjId, String wsId, String userId, int limit) {
		return hotSearchDetail.getGenericSearchResult(locId, clientId, prjId,
				wsId, userId, limit);
	}

	public boolean insertHotSearchDetail(DTOHotsearchDetail hotsearchDetail) {
		return hotSearchDetail.insertHotSearchDetail(hotsearchDetail);
	}

	public Vector getAllDetailAboutSavedSearch(int userId) {
		return hotSearchDetail.getAllDetailAboutSavedSearch(userId);
	}

	public void deleteHotSearch(String hotSearchId) {
		hotSearchDetail.deleteHotSearch(hotSearchId);
	}

	public Vector<DTOHotsearchDetail> getDMSSearchResult(
			Map<String, String> attributes, int userId) {
		return hotSearchDetail.getDMSSearchResult(attributes, userId);
	}

	public Vector<DTOCountryMst> getDMSRegions() {
		return hotSearchDetail.getDMSRegions();
	}

	public List<List<String>> getProjectLevelSearchResult(
			Map<String, String> inputFields, List<String> outputFields) {
		return hotSearchDetail.getProjectLevelSearchResult(inputFields,
				outputFields);
	}

	// /////////////////// Operation
	// Master\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public Vector getOperationParentDetailByParentCode(
			String operationParentCode) {
		return operationsMst
				.getOperationParentDetailByParentCode(operationParentCode);
	}

	public Vector getOperationByUserTypeId(String userType) {
		return operationsMst.getOperationByUserTypeId(userType);
	}

	public Vector getAllOperationDetail() {
		return operationsMst.getAllOperationDetail();
	}

	public void InsertIntoOperationMst(DTOOperationMst dto, int Mode) {
		operationsMst.InsertIntoOperationMst(dto, Mode);
	}

	// /////////////////// Role Operation Matrix
	// Master\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	public void insertIntoRoleOperationMatrix(
			ArrayList<DTORoleOperationMatrix> roleList) {
		roleOperationMatrixMst.insertIntoRoleOperationMatrix(roleList);
	}

	// /////// Attribute Group Matrix /////////////////////////////////
	public Vector<DTOAttributeGroupMatrix> getAttributeGroupMatrixValues(
			String attrGroupId) {
		return attributeGroupMatrix.getAttributeGroupMatrixValues(attrGroupId);
	}

	public void insertIntoAttributeGroupMatrix(DTOAttributeGroupMatrix dto,
			int mode) {
		attributeGroupMatrix.insertIntoAttributeGroupMatrix(dto, mode);
	}

	// /////// AttrForIndi Master /////////////////////////////////
	public Vector<DTOAttrForIndiMst> getAttrForIndiDetails() {
		return attrForIndiMst.getAttrForIndiDetails();
	}

	// /////// Submission Master /////////////////////////////////
	public void insertSubmissionMst(DTOSubmissionMst dto, int Mode) {
		submissionMst.insertSubmissionMst(dto, Mode);
	}

	public Vector getAllSubmissionDetail(String wsId) {
		return submissionMst.getAllSubmissionDetail(wsId);
	}

	public DTOSubmissionMst getSubmissionDetail(String submissionId) {
		return submissionMst.getSubmissionDetail(submissionId);
	}

	public DTOSubmissionMst getOriginalSubmissionDetail(String wsId) {
		return submissionMst.getOriginalSubmissionDetail(wsId);
	}

	// ///////////country Master///////////////////////
	public Vector<DTOCountryMst> getAllCountryDetail() {
		return countryMst.getAllCountryDetail();
	}

	/*
	 * Methods Added By : Ashmak Shah Added On : 20th may 2009
	 */
	public String getCountryId(String countryCode) {
		return countryMst.getCountryId(countryCode);
	}

	public DTOCountryMst getCountry(String countryId) {
		return countryMst.getCountry(countryId);
	}

	public Vector<DTOCountryMst> getCountriesRegionWise(String region) {
		return countryMst.getCountriesRegionWise(region);
	}

	// ///////////Agency Master///////////////////////

	public Vector<DTOAgencyMst> getAllAgency() {
		return agencyMst.getAllAgency();

	}

	public ArrayList<DTOAgencyMst> getAgenciesForCountry(String countryId,
			String dtdVersion) {
		return agencyMst.getAgenciesForCountry(countryId, dtdVersion);
	}

	public DTOAgencyMst getAgencyByName(String agencyName) {
		return agencyMst.getAgencyByName(agencyName);
	}

	// ////////Stage Master///////////////////////

	public Vector<DTOStageMst> getStageDetail() {
		return stageMst.getStageDetail();
	}
	public Vector<DTOStageMst> getStageDetailCSV() {
		return stageMst.getStageDetailCSV();
	}

	public void stageMstOp(DTOStageMst dto, int Mode, boolean isrevert) {
		stageMst.stageMstOp(dto, Mode, isrevert);
	}

	public DTOStageMst getStageInfo(int stageId) {
		return stageMst.getStageInfo(stageId);
	}
	public ArrayList<Integer> getNextStageId(int stageId){
		return stageMst.getNextStageId(stageId);
	}
	public DTOStageMst getNextStageInfo(int stageId) {
		return stageMst.getNextStageInfo(stageId);
	}
	
	public Vector<DTOStageMst> geteSignatureStageDetail() {
		return stageMst.geteSignatureStageDetail();
	}

	// ////////////////TemplateUserMst \\\\\\\\\\\\\\\\\\\\\\\\\\\\

	public Vector getTemplateUserDetailList(String templateId) {
		return templateUserMst.getTemplateUserDetailList(templateId);
	}

	public void insertUpdateUsertoTemplate(DTOTemplateUserMst obj,
			int[] userCode) {
		templateUserMst.insertUpdateUsertoTemplate(obj, userCode);
	}

	public Vector getTemplateUserDetail(String templateId, DTOUserMst usermst) {
		return templateUserMst.getTemplateUserDetail(templateId, usermst);
	}

	public DTOTemplateUserMst getUserDetails(DTOTemplateUserMst obj) {
		return templateUserMst.getUserDetails(obj);
	}

	public void inActiveUserFromTemplate(DTOTemplateUserMst obj) {
		templateUserMst.inActiveUserFromTemplate(obj);
	}

	// //////////////// TemplateUserRightsMst \\\\\\\\\\\\\\\\\\\\\\\\\\
	public void insertTemplateUserRights(DTOTemplateUserRightsMst obj,
			int[] usercode) {
		templateUserRightsMst.insertTemplateUserRights(obj, usercode);
	}

	public Vector getTemplateUserRightsDetail(String templateId, int nodeId) {
		return templateUserRightsMst.getTemplateUserRightsDetail(templateId,
				nodeId);
	}

	public boolean updateTemplateUserRights(
			DTOTemplateUserRightsMst objTemplateUserRights) {
		return templateUserRightsMst
				.updateTemplateUserRights(objTemplateUserRights);
	}

	public boolean updateUserRightsForTemplate(DTOTemplateUserRightsMst dto) {
		return templateUserRightsMst.updateUserRightsForTemplate(dto);
	}

	public Vector getTemplateUserRightsReport(DTOTemplateUserRightsMst dto) {
		return templateUserRightsMst.getTemplateUserRightsReport(dto);
	}

	public Vector<DTOUserMst> getUserStatus(int usercode,int usergroupcode)
	{
		return userMst.getUserStatus(usercode, usergroupcode);
	}
	// //////////////// SubmittedWorkspaceNodeDetailMst
	// \\\\\\\\\\\\\\\\\\\\\\\\\\

	public void insertIntoSubmittedWorkspaceNodeDetail(
			ArrayList<DTOSubmittedWorkspaceNodeDetail> subNodeDtlLst) {
		submittedWorkspaceNodeDetailMst
				.insertIntoSubmittedWorkspaceNodeDetail(subNodeDtlLst);
	}

	public Vector getAttributeValueOfModifiedFile(String wsId, int nodeId) {
		return submittedWorkspaceNodeDetailMst.getAttributeValueOfModifiedFile(
				wsId, nodeId);
	}

	public boolean submittedNodeIdDetail(String wsId, int nodeId) {
		return submittedWorkspaceNodeDetailMst.submittedNodeIdDetail(wsId,
				nodeId);
	}

	public String checkDeleteOperation(String wsId, int nodeId) {
		return workSpaceNodeAttributeHistory.checkDeleteOperation(wsId, nodeId);
	}

	public ArrayList<DTOSubmittedWorkspaceNodeDetail> getSubmittedWorkspaceNodeList(
			String workspaceId, String submissionId) {
		return submittedWorkspaceNodeDetailMst.getSubmittedWorkspaceNodeList(
				workspaceId, submissionId);
	}

	// ////////////////////// SubmissionInfoMst \\\\\\\\\\\\\\\\\\\\\\\\\\

	public void insertSubmissionInfoEUMst(DTOSubmissionMst dto) {
		submissionInfoMst.insertSubmissionInfoEUMst(dto);
	}

	public void insertSubmissionInfoEU14Mst(DTOSubmissionMst dto) {
		submissionInfoMst.insertSubmissionInfoEU14Mst(dto);
	}

	public void insertSubmissionInfoEU20Mst(DTOSubmissionMst dto) {
		submissionInfoMst.insertSubmissionInfoEU20Mst(dto);
	}
	public void insertSubmissionInfoAUMst(DTOSubmissionMst dto) {
		submissionInfoMst.insertSubmissionInfoAUMst(dto);
	}
	
	public void insertSubmissionInfoTHMst(DTOSubmissionMst dto) {
		submissionInfoMst.insertSubmissionInfoTHMst(dto);
	}

	// added on 29-03-2013 for gcc region
	public void insertSubmissionInfoGCCMst(DTOSubmissionMst dto) {
		submissionInfoMst.insertSubmissionInfoGCCMst(dto);
	}

	public void insertSubmissionInfoZAMst(DTOSubmissionMst dto) {
		submissionInfoMst.insertSubmissionInfoZAMst(dto);
	}

	public void insertSubmissionInfoCHMst(DTOSubmissionMst dto) {
		submissionInfoMst.insertSubmissionInfoCHMst(dto);
	}

	public void insertSubmissionInfoUSMst(DTOSubmissionMst dto) {
		submissionInfoMst.insertSubmissionInfoUSMst(dto);
	}
	
	public Vector<DTOSubmissionInfoDMSDtl> getDetailsforPublishPath(String workspaceId){
		return  submissionInfoDMSDtl.getDetailsforPublishPath(workspaceId);
	}
	public Vector<DTOSubmissionInfoDMSDtl> getmaxsubinfoDMSDtlId(){
		return  submissionInfoDMSDtl.getmaxsubinfoDMSDtlId();
	}
	public int getCountForArchivleSequence(String wsId){
		return  submissionInfoDMSDtl.getCountForArchivleSequence(wsId);
	}
	
	public void insertSubmissionInfoUS23Mst(DTOSubmissionMst dto) {
		submissionInfoMst.insertSubmissionInfoUS23Mst(dto);
	}

	public Vector<DTOSubmissionMst> getAllWorkspaceSubmissionInfo(int userId) {
		return submissionInfoMst.getAllWorkspaceSubmissionInfo(userId);
	}

	public DTOSubmissionMst getSubmissionInfo(String workspaceId) {
		return submissionInfoMst.getSubmissionInfo(workspaceId);
	}
	public DTOSubmissionMst getSubmissionInfoEURegion(String workspaceId) {
		return submissionInfoMst.getSubmissionInfoEURegion(workspaceId);
	}
	public DTOSubmissionMst getSubmissionInfoGCC(String workspaceId) {
		return submissionInfoMst.getSubmissionInfoGCC(workspaceId);
	}

	public ArrayList<DTOSubmissionMst> getSubmissionInfoForWorkspace(
			ArrayList<String> wsIdsList) {
		return submissionInfoMst.getSubmissionInfoForWorkspace(wsIdsList);
	}
	public void updateSubmissionInfoForEU(String workspaceId,String reginoalDTDVersion) {
		submissionInfoMst.updateSubmissionInfoForEU(workspaceId,reginoalDTDVersion);
	}
	

	// //////////////////////SubmissionTypeMst \\\\\\\\\\\\\\\\\\\\\\\\\\
	public Vector getRegionWiseSubmissionTypes(String countryRegion,String regionalDtdVersion) {
		return submissionTypeMst.getRegionWiseSubmissionTypes(countryRegion,regionalDtdVersion);
	}
	
	public String getSubmissionTypeCodeForUS23(String SubmissionType){
		return submissionTypeMst.getSubmissionTypeCodeForUS23(SubmissionType);
	}
	
	// //////////////////////Applicant Contact Type \\\\\\\\\\\\\\\\\\\\\\\\\\
	public Vector getApplicantContactType() {
		return applicantContactTypeMst.getApplicantContactTypeDetail();
	}
	
	// //////////////////////Telephone Number Type  \\\\\\\\\\\\\\\\\\\\\\\\\\
	public Vector getTelephoneNumberType() {
		return telephoneNumberTypeMst.getTelephoneNumberTypeDetail();
	}
	/////////////////////////// Regulatory Activity Lead //////////////////
	
	public Vector<DTORegulatoryActivityLeadMst> getRegulatoryActivityLeadDetail(){
		
		return regulatoryactivityleadmst.getRegulatoryActivityLeadDetail();
	}
	
	public DTORegulatoryActivityLeadMst getRegulatoryActivityLeadByCode(String code){
		return regulatoryactivityleadmst.getRegulatoryActivityLeadByCode(code);
	}
	
/////////////////////////// Regulatory Activity Lead Type //////////////////
	public Vector<DTORegulatoryActivityType> getRegulatoryActivityTypeDetail(){
		
		return regulatoryactivitytype.getRegulatoryActivityTypeDetail();
	}
	public DTORegulatoryActivityType getRegulatoryActivityLeadTypeByCode(String code){
		return regulatoryactivitytype.getRegulatoryActivityTypeByCode(code);
	}
	
	/////////////////////////SequenceTypeMst/////////////////////////////
	
	public Vector<DTOSequenceTypeMst>getSequenceTypeDetail(){
		
		return sequencetypemst.getSequenceTypeDetail();
	}
	public DTOSequenceTypeMst getSequenceTypeByCode(String code){
		
		return sequencetypemst.getSequenceTypeByCode(code);
	}
	
/////////////////////////SequenceTypeMst_Thai/////////////////////////////
	
	public Vector<DTOSequenceTypeMst_Thai>getSequenceTypeDetail_Thai(){
		
		return sequencetypemst_thai.getSequenceTypeDetail_Thai();
	}
	public DTOSequenceTypeMst_Thai getSequenceTypeByCode_Thai(String code){
		
		return sequencetypemst_thai.getSequenceTypeByCode_Thai(code);
	}
	////////////////////////// SequenceDescriptionMst////////////////////////
	
	public Vector<DTOSequenceDescriptionMst>getSequenceDescriptionDetail(){
		
		return sequencedescriptionmst.getSequenceDescriptionDetail();
	}
	public Vector<DTOSequenceDescriptionMst> getSequenceDescriptionByCode(String code){
		
		return sequencedescriptionmst.getSequenceDescriptionByCode(code);
	}
	
////////////////////////// SequenceDescriptionMst_CA////////////////////////
	
	public Vector<DTOSequenceDescriptionMst_CA>getSequenceDescriptionDetail_CA(){
		
		return sequencedescriptionmst_ca.getSequenceDescriptionDetail_CA();
	}
	public Vector<DTOSequenceDescriptionMst_CA> getSequenceDescriptionByCode_CA(String code){
		
		return sequencedescriptionmst_ca.getSequenceDescriptionByCode_CA(code);
	}
	
	// //////////////////////Application Type  \\\\\\\\\\\\\\\\\\\\\\\\\\
	public Vector<DTOApplicationTypeMst> getApplicationType() {
		return applicationTypeMst.getApplicationTypeDetail();
	}

	public DTOApplicationTypeMst getApplicationTypeByCode(String code) {
		return applicationTypeMst.getApplicationTypeByCode(code);
	}

	// //////////////////////Submission-Sub Type  \\\\\\\\\\\\\\\\\\\\\\\\\\
	public Vector getSubmissionSubType() {
		return submissionSubTypeMst.getSubmissionSubTypeDetail();
	}

	// //////////////////////Suppliment Effective Date Type  \\\\\\\\\\\\\\\\\\\\\\\\\\
	public Vector getSupplimentEffectiveDateType() {
		return supplimentEffectiveDateTypeMst.getSupplementEffectiveDateTypeDetail();
	}

	// //////////////////////SubmissionInfoEUDtl \\\\\\\\\\\\\\\\\\\\\\\\\\
	public String insertSubmissionInfoEUDtl(DTOSubmissionInfoEUDtl dto,
			int DATAOPMODE) {
		return submissionInfoEUDtl.insertSubmissionInfoEUDtl(dto, DATAOPMODE);
	}

	public Vector<DTOSubmissionInfoEUDtl> getWorkspaceSubmissionInfoEUDtl(
			String workspaceId) {
		return submissionInfoEUDtl.getWorkspaceSubmissionInfoEUDtl(workspaceId);
	}

	public DTOSubmissionInfoEUDtl getWorkspaceSubmissionInfoEUDtlBySubmissionId(
			String submissionId) {
		return submissionInfoEUDtl
				.getWorkspaceSubmissionInfoEUDtlBySubmissionId(submissionId);
	}

	// //////////////////////SubmissionInfoEU14Dtl \\\\\\\\\\\\\\\\\\\\\\\\\\
	public String insertSubmissionInfoEU14Dtl(DTOSubmissionInfoEU14Dtl dto,
			int DATAOPMODE) {
		return submissionInfoEU14Dtl.insertSubmissionInfoEU14Dtl(dto,
				DATAOPMODE);
	}

	public Vector<DTOSubmissionInfoEU14Dtl> getWorkspaceSubmissionInfoEU14Dtl(
			String workspaceId) {
		return submissionInfoEU14Dtl
				.getWorkspaceSubmissionInfoEU14Dtl(workspaceId);
	}

	public DTOSubmissionInfoEU14Dtl getWorkspaceSubmissionInfoEU14DtlBySubmissionId(
			String submissionId) {
		return submissionInfoEU14Dtl
				.getWorkspaceSubmissionInfoEU14DtlBySubmissionId(submissionId);
	}

	// //////////////////////SubmissionInfoEU20Dtl \\\\\\\\\\\\\\\\\\\\\\\\\\
	public String insertSubmissionInfoEU20Dtl(DTOSubmissionInfoEU20Dtl dto,
			int DATAOPMODE) {
		return submissionInfoEU20Dtl.insertSubmissionInfoEU20Dtl(dto,
				DATAOPMODE);
	}

	public Vector<DTOSubmissionInfoEU20Dtl> getWorkspaceSubmissionInfoEU20Dtl(
			String workspaceId) {
		return submissionInfoEU20Dtl
				.getWorkspaceSubmissionInfoEU20Dtl(workspaceId);
	}

	public DTOSubmissionInfoEU20Dtl getWorkspaceSubmissionInfoEU20DtlBySubmissionId(
			String submissionId) {
		return submissionInfoEU20Dtl
				.getWorkspaceSubmissionInfoEU20DtlBySubmissionId(submissionId);
	}

	// /////////////////// NeeS Submission  \\\\\\\\\\\\\\\\\\
	
	public String insertSubmissionInfoNeeSDtl(DTOSubmissionInfoNeeSDtl dto,
			int DATAOPMODE) {
		return submissionInfoNeeSDtl.insertSubmissionInfoNeeSDtl(dto,
				DATAOPMODE);
	}

	public Vector<DTOSubmissionInfoNeeSDtl> getWorkspaceSubmissionInfoNeeSDtl(
			String workspaceId) {
		return submissionInfoNeeSDtl
				.getWorkspaceSubmissionInfoNeeSDtl(workspaceId);
	}
	
	public Vector<DTOSubmissionInfoNeeSDtl> getWorkspaceSubmissionInfoNeeSDtlForConfirmSeq(
			String workspaceId) {
		return submissionInfoNeeSDtl
				.getWorkspaceSubmissionInfoNeeSDtlForConfirmSeq(workspaceId);
	}
	

	public DTOSubmissionInfoNeeSDtl getWorkspaceSubmissionInfoNeeSDtlBySubmissionId(
			String submissionId) {
		return submissionInfoNeeSDtl
				.getWorkspaceSubmissionInfoNeeSDtlBySubmissionId(submissionId);
	}
	
	
	
// /////////////////// vNeeS Submission  \\\\\\\\\\\\\\\\\\
	
	public String insertSubmissionInfovNeeSDtl(DTOSubmissionInfovNeeSDtl dto,
			int DATAOPMODE) {
		return submissionInfovNeeSDtl.insertSubmissionInfovNeeSDtl(dto,
				DATAOPMODE);
	}

	public Vector<DTOSubmissionInfovNeeSDtl> getWorkspaceSubmissionInfovNeeSDtl(
			String workspaceId) {
		return submissionInfovNeeSDtl
				.getWorkspaceSubmissionInfovNeeSDtl(workspaceId);
	}
	
	public Vector<DTOSubmissionInfovNeeSDtl> getWorkspaceSubmissionInfovNeeSDtlForConfirmSeq(
			String workspaceId) {
		return submissionInfovNeeSDtl
				.getWorkspaceSubmissionInfovNeeSDtlForConfirmSeq(workspaceId);
	}
	

	public DTOSubmissionInfovNeeSDtl getWorkspaceSubmissionInfovNeeSDtlBySubmissionId(
			String submissionId) {
		return submissionInfovNeeSDtl
				.getWorkspaceSubmissionInfovNeeSDtlBySubmissionId(submissionId);
	}
	
	// //////////////////////SubmissionInfoGCCDtl \\\\\\\\\\\\\\\\\\\\\\\\\\
	// /added on 03-Apr-2013 for gcc region
	public String insertSubmissionInfoGCCDtl(DTOSubmissionInfoGCCDtl dto,
			int DATAOPMODE) {
		return submissionInfoGCCDtl.insertSubmissionInfoGCCDtl(dto, DATAOPMODE);
	}

	public Vector<DTOSubmissionInfoGCCDtl> getWorkspaceSubmissionInfoGCCDtl(
			String workspaceId) {

		return submissionInfoGCCDtl
				.getWorkspaceSubmissionInfoGCCDtl(workspaceId);
	}

	public DTOSubmissionInfoGCCDtl getWorkspaceSubmissionInfoGCCDtlBySubmissionId(
			String submissionId) {
		return submissionInfoGCCDtl
				.getWorkspaceSubmissionInfoGCCDtlBySubmissionId(submissionId);
	}

	// Added on 24-06-2013 for ZA region

	public String insertSubmissionInfoZADtl(DTOSubmissionInfoZADtl dto,
			int DATAOPMODE) {
		return submissionInfoZADtl.insertSubmissionInfoZADtl(dto, DATAOPMODE);
	}

	public Vector<DTOSubmissionInfoZADtl> getWorkspaceSubmissionInfoZADtl(
			String workspaceId) {

		return submissionInfoZADtl.getWorkspaceSubmissionInfoZADtl(workspaceId);
	}

	public DTOSubmissionInfoZADtl getWorkspaceSubmissionInfoZADtlBySubmissionId(
			String submissionId) {
		return submissionInfoZADtl
				.getWorkspaceSubmissionInfoZADtlBySubmissionId(submissionId);
	}
	
	
	// Added on 06-08-2015 for AU-TGA v3.0 region

	public String insertSubmissionInfoAUDtl(DTOSubmissionInfoAUDtl dto,
			int DATAOPMODE) {
		return submissionInfoAUDtl.insertSubmissionInfoAUDtl(dto, DATAOPMODE);
	}

	public Vector<DTOSubmissionInfoAUDtl> getWorkspaceSubmissionInfoAUDtl(
			String workspaceId) {

		return submissionInfoAUDtl.getWorkspaceSubmissionInfoAUDtl(workspaceId);
	}

	public DTOSubmissionInfoAUDtl getWorkspaceSubmissionInfoAUDtlBySubmissionId(
			String submissionId) {
		return submissionInfoAUDtl
				.getWorkspaceSubmissionInfoAUDtlBySubmissionId(submissionId);
	}
	
	// Added on 06-08-2015 for TH-FDA v0.92 region

	public String insertSubmissionInfoTHDtl(DTOSubmissionInfoTHDtl dto,
			int DATAOPMODE) {
		return submissionInfoTHDtl.insertSubmissionInfoTHDtl(dto, DATAOPMODE);
	}

	public Vector<DTOSubmissionInfoTHDtl> getWorkspaceSubmissionInfoTHDtl(
			String workspaceId) {

		return submissionInfoTHDtl.getWorkspaceSubmissionInfoTHDtl(workspaceId);
	}

	public DTOSubmissionInfoTHDtl getWorkspaceSubmissionInfoTHDtlBySubmissionId(
			String submissionId) {
		return submissionInfoTHDtl
				.getWorkspaceSubmissionInfoTHDtlBySubmissionId(submissionId);
	}

	// Added on 30-07-2013 for CH ( swissmedic) region
	public String insertSubmissionInfoCHDtl(DTOSubmissionInfoCHDtl dto,
			int DATAOPMODE) {
		return submissionInfoCHDtl.insertSubmissionInfoCHDtl(dto, DATAOPMODE);
	}

	public Vector<DTOSubmissionInfoCHDtl> getWorkspaceSubmissionInfoCHDtl(
			String workspaceId) {

		return submissionInfoCHDtl.getWorkspaceSubmissionInfoCHDtl(workspaceId);
	}

	public DTOSubmissionInfoCHDtl getWorkspaceSubmissionInfoCHDtlBySubmissionId(
			String submissionId) {
		return submissionInfoCHDtl
				.getWorkspaceSubmissionInfoCHDtlBySubmissionId(submissionId);
	}

	// //////////////////////SubmissionInfoUSDtl \\\\\\\\\\\\\\\\\\\\\\\\\\
	public String insertSubmissionInfoUSDtl(DTOSubmissionInfoUSDtl dto,
			int DATAOPMODE) {
		return submissionInfoUSDtl.insertSubmissionInfoUSDtl(dto, DATAOPMODE);
	}

	public Vector getWorkspaceSubmissionInfoUSDtl(String workspaceId) {
		return submissionInfoUSDtl.getWorkspaceSubmissionInfoUSDtl(workspaceId);
	}

	public DTOSubmissionInfoUSDtl getWorkspaceSubmissionInfoUSDtlBySubmissionId(
			String submissionId) {
		return submissionInfoUSDtl
				.getWorkspaceSubmissionInfoUSDtlBySubmissionId(submissionId);
	}
	
	
	// //////////////////////SubmissionInfoUS23Dtl \\\\\\\\\\\\\\\\\\\\\\\\\\
	public String insertSubmissionInfoUS23Dtl(DTOSubmissionInfoUS23Dtl dto,
			int DATAOPMODE) {
		return submissionInfoUS23Dtl.insertSubmissionInfoUS23Dtl(dto, DATAOPMODE);
	}

	public Vector getWorkspaceSubmissionInfoUS23Dtl(String workspaceId) {
		return submissionInfoUS23Dtl.getWorkspaceSubmissionInfoUS23Dtl(workspaceId);
	}

	public DTOSubmissionInfoUS23Dtl getWorkspaceSubmissionInfoUS23DtlBySubmissionId(
			String submissionId) {
		return submissionInfoUS23Dtl
				.getWorkspaceSubmissionInfoUS23DtlBySubmissionId(submissionId);
	}
	
	public Vector<DTOWorkspaceApplicationDetail> getWorkspaceApplicationDetail(String workspaceId) 
	{
		return workspaceApplicationDetailMst.getWorkspaceApplicationDetail(workspaceId);
		
	}
	public void insertWorkspaceApplicationDetail(DTOWorkspaceApplicationDetail dto, int Mode) {
		workspaceApplicationDetailMst.insertWorkspaceApplicationDetail(dto, Mode);
	}
	

	// //////////////////////WorkspaceCMSMst \\\\\\\\\\\\\\\\\\\\\\\\\\
	public void insertWorkspaceCMS(DTOWorkspaceCMSMst dto, int Mode) {
		workspaceCMSMst.insertWorkspaceCMS(dto, Mode);
	}

	public ArrayList<DTOWorkspaceCMSMst> getWorkspaceCMSInfo(String workspaceId) {
		return workspaceCMSMst.getWorkspaceCMSInfo(workspaceId);
	}

	// //////////////////////SubmissionInfoEUSubDtl \\\\\\\\\\\\\\\\\\\\\\\\\\
	public void insertSubmissionInfoEUSubDtl(DTOSubmissionInfoEUSubDtl dto,
			int Mode) {
		submissionInfoEUSubDtl.insertSubmissionInfoEUSubDtl(dto, Mode);
	}

	public ArrayList<DTOSubmissionInfoEUSubDtl> getWorkspaceCMSSubmissionInfo(
			String submissionId) {
		return submissionInfoEUSubDtl
				.getWorkspaceCMSSubmissionInfo(submissionId);
	}
	public ArrayList<DTOSubmissionInfoEUSubDtl> getWorkspaceCMSSubmissionInfo(
			String submissionId,String dtdversion) {
		return submissionInfoEUSubDtl
				.getWorkspaceCMSSubmissionInfo(submissionId,dtdversion);
	}

	public DTOSubmissionInfoEUSubDtl getWorkspaceRMSSubmissionInfo(
			String submissionId) {
		return submissionInfoEUSubDtl
				.getWorkspaceRMSSubmissionInfo(submissionId);
	}

	// //////////////////////SubmissionInfoEU14SubDtl \\\\\\\\\\\\\\\\\\\\\\\\\\
	public void insertSubmissionInfoEU14SubDtl(DTOSubmissionInfoEU14SubDtl dto,
			int Mode) {
		submissionInfoEU14SubDtl.insertSubmissionInfoEU14SubDtl(dto, Mode);
	}

	public ArrayList<DTOSubmissionInfoEU14SubDtl> getWorkspaceCMSSubmissionInfoEU14(
			String submissionId) {
		return submissionInfoEU14SubDtl
				.getWorkspaceCMSSubmissionInfoEU14(submissionId);
	}

	public DTOSubmissionInfoEU14SubDtl getWorkspaceRMSSubmissionInfoEU14(
			String submissionId) {
		return submissionInfoEU14SubDtl
				.getWorkspaceRMSSubmissionInfoEU14(submissionId);
	}

	// //////////////////////SubmissionInfoEU20SubDtl \\\\\\\\\\\\\\\\\\\\\\\\\\
	public void insertSubmissionInfoEU20SubDtl(DTOSubmissionInfoEU20SubDtl dto,
			int Mode) {
		submissionInfoEU20SubDtl.insertSubmissionInfoEU20SubDtl(dto, Mode);
	}

	public ArrayList<DTOSubmissionInfoEU20SubDtl> getWorkspaceCMSSubmissionInfoEU20(
			String submissionId) {
		return submissionInfoEU20SubDtl
				.getWorkspaceCMSSubmissionInfoEU20(submissionId);
	}

	public DTOSubmissionInfoEU20SubDtl getWorkspaceRMSSubmissionInfoEU20(
			String submissionId) {
		return submissionInfoEU20SubDtl
				.getWorkspaceRMSSubmissionInfoEU20(submissionId);
	}

	// //////////////////////SubmissionInfoGCCSubDtl \\\\\\\\\\\\\\\\\\\\\\\\\\
	public void insertSubmissionInfoGCCSubDtl(DTOSubmissionInfoGCCSubDtl dto,
			int Mode) {
		submissionInfoGCCSubDtl.insertSubmissionInfoGCCSubDtl(dto, Mode);
	}

	public ArrayList<DTOSubmissionInfoGCCSubDtl> getWorkspaceCMSSubmissionInfoGCC(
			String submissionId) {
		return submissionInfoGCCSubDtl
				.getWorkspaceCMSSubmissionInfoGCC(submissionId);
	}

	public DTOSubmissionInfoGCCSubDtl getWorkspaceRMSSubmissionInfoGCC(
			String submissionId) {
		return submissionInfoGCCSubDtl
				.getWorkspaceRMSSubmissionInfoGCC(submissionId);
	}

	// /////////////////////////XmlNodeDtl \\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public DTOXmlNodeDtl getXmlNodeDtl(long xmlWorkspaceId, int xmlNodeId) {
		return xmlNodeDtl.getXmlNodeDtl(xmlWorkspaceId, xmlNodeId);
	}

	public ArrayList<DTOXmlNodeDtl> getXmlChildNodeDtl(long xmlWorkspaceId,
			int xmlParentNodeId) {
		return xmlNodeDtl.getXmlChildNodeDtl(xmlWorkspaceId, xmlParentNodeId);
	}

	public ArrayList<String> getXmlNodeValue(String workspaceId,
			String tablename, HashMap<String, String> inputFields,
			String outputField) {
		return xmlNodeDtl.getXmlNodeValue(workspaceId, tablename, inputFields,
				outputField);
	}

	public ArrayList<String> getXmlAttrValuesForRepeatableNode(
			String workspaceId, String tablename,
			HashMap<String, String> inputFields, String outputField) {
		return xmlNodeDtl.getXmlAttrValuesForRepeatableNode(workspaceId,
				tablename, inputFields, outputField);
	}

	// /////////////////////////XmlWorkspaceMst\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public DTOXmlWorkspaceDtl getXmlWorkspaceDtl(String XMLWorkspaceName) {
		return xmlWorkspaceMst.getXmlWorkspaceDtl(XMLWorkspaceName);
	}

	// /////////////////////////XmlNodeAttrDtl \\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public ArrayList<DTOXmlNodeAttrDtl> getXmlNodeAttrDtl(long xmlWorkspaceId,
			int xmlNodeId) {
		return xmlNodeAttrDtl.getXmlNodeAttrDtl(xmlWorkspaceId, xmlNodeId);
	}

	public ArrayList<String> getXmlAttrValue(String workspaceId,
			String tablename, HashMap<String, String> inputFields,
			String outputField) {
		return xmlNodeAttrDtl.getXmlAttrValue(workspaceId, tablename,
				inputFields, outputField);
	}

	public ArrayList<DTOXmlNodeAttrDtl> getXmlAttrDtl(long xmlNodeAttrDtlId) {
		return xmlNodeAttrDtl.getXmlAttrDtl(xmlNodeAttrDtlId);
	}

	// /////////////////////////WorkspaceNodeDocHistory
	// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public int getNewDocTranNo(String workspaceId) {
		return workspaceNodeDocHistory.getNewDocTranNo(workspaceId);
	}

	public void insertNodeDocHistory(
			ArrayList<DTOWorkspaceNodeDocHistory> WsNodeDocHistoryList,
			boolean copyFile) {
		workspaceNodeDocHistory.insertNodeDocHistory(WsNodeDocHistoryList,
				copyFile);
	}

	public ArrayList<DTOWorkspaceNodeDocHistory> getLatestNodeDocHistory(
			String workspaceId, int nodeid) {
		return workspaceNodeDocHistory.getLatestNodeDocHistory(workspaceId,
				nodeid);
	}

	public ArrayList<DTOWorkspaceNodeDocHistory> getFullNodeDocHistory(
			String workspaceId, int nodeid) {
		return workspaceNodeDocHistory.getFullNodeDocHistory(workspaceId,
				nodeid);
	}
	public int getMaxDocTranNo(String wsId, int nodeId) {
		return workspaceNodeDocHistory.getMaxDocTranNo(wsId, nodeId);
	}
	public ArrayList<DTOWorkspaceNodeDocHistory> getWorkspaceNodeDocHistory(
			String workspaceId, int nodeid, int tranNo) {
		return workspaceNodeDocHistory.getWorkspaceNodeDocHistory(workspaceId,
				nodeid,tranNo);
	}
	public void insertNodeDocHistoryForSaveAndSendUpload(ArrayList<DTOWorkspaceNodeDocHistory> WsNodeDocHistoryList,
			int userId) {
		workspaceNodeDocHistory.insertNodeDocHistoryForSaveAndSendUpload(WsNodeDocHistoryList,userId);
	}

	public String getFileNameForSrcDocUpload(String wsId,int nodeId){
		return workspaceNodeDocHistory.getFileNameForSrcDocUpload(wsId,nodeId);
	}
	
	// ////////////////////////////SubmissionInfoForManualMode\\\\\\\\\\\\\\\\\\\\\\

	public void insertSubmissionInfoForManualMode(
			ArrayList<DTOSubmissionInfoForManualMode> subDtl) {
		submissionInfoForManualMode.insertSubmissionInfoForManualMode(subDtl);
	}

	public static ArrayList<DTOSubmissionInfoForManualMode> getSubmissionInfoForManualMode(
			String workspaceId, String submissionId) {
		return SubmissionInfoForManualMode.getSubmissionInfoForManualMode(
				workspaceId, submissionId);
	}

	// ////////////////////////////ManualModeSeqZipDtl\\\\\\\\\\\\\\\\\\\\\\
	public static void insertManualModeSeqZipDtl(
			ArrayList<DTOManualModeSeqZipDtl> seqZipDtlList) {
		ManualModeSeqZipDtl.insertManualModeSeqZipDtl(seqZipDtlList);
	}

	public static ArrayList<DTOManualModeSeqZipDtl> getManualModeSeqZipDtl(
			String workspaceId) {
		return ManualModeSeqZipDtl.getManualModeSeqZipDtl(workspaceId);
	}

	public static ArrayList<DTOManualModeSeqZipDtl> getManualModeSeqZipDtlBySequenceNo(
			String workspaceId, String seqNo) {
		return ManualModeSeqZipDtl.getManualModeSeqZipDtlBySequenceNo(
				workspaceId, seqNo);
	}

	// //////////////////////////SubmissionInfoHTML\\\\\\\\\\\\\\\\\\\\\\

	public long insertSubmissionInfoHTML(DTOSubmissionInfoHTML dto) {
		return submissionInfoHTML.insertSubmissionInfoHTML(dto);
	}
	
	public int getNewTranNoForHTMLPublish(String wsId) {
		return submissionInfoHTML.getNewTranNoForHTMLPublish(wsId);
	}

	public ArrayList<DTOSubmissionInfoHTML> getAllSubmissionInfoHTMLDetail(
			String wsId) {
		return submissionInfoHTML.getAllSubmissionInfoHTMLDetail(wsId);
	}

	public ArrayList<DTOSubmissionInfoHTML> getPublishDetailfromID(long ID) {
		return submissionInfoHTML.getPublishDetailfromID(ID);
	}

	// //////////////////////////21-CFRChanges\\\\\\\\\\\\\\\\\\\\\\
	public static int UserLoginDetailsOp(int UserId, int Mode) {
		return UserLoginDetails.UserLoginDetailsOp(UserId, Mode);
	}

	public static ArrayList<DTOPasswordPolicyMst> getPolicyDetails(String policy) {
		return PasswordPolicyMst.getPolicyDetails(policy);
	}

	public static boolean isPasswordValidate(int userID) {
		return PasswordPolicyMst.isPasswordValidate(userID);
	}

	public static ArrayList<DTOPasswordHistory> getPasswordHistoryDtls(
			int UserID) {
		return PasswordHistoryMst.getPasswordHistoryDtls(UserID);
	}
	
	public static ArrayList<DTOPasswordHistory> getPasswordHistoryDtlsForPasswordMatch(
			int userID,int matchHistory) {
		return PasswordHistoryMst.getPasswordHistoryDtlsForPasswordMatch(userID,matchHistory);
	}

	public static boolean insertFailureUserDtls(DTOUserLoginFailureDetails dto,
			int Mode) {
		return UserLoginFailureDetails.insertFailureUserDtls(dto, Mode);
	}
	
	public static ArrayList<DTOUserLoginFailureDetails> getFailureDetailForUser(
			String username,int userCode) {
		return UserLoginFailureDetails.getFailureDetailForUser(username,userCode);
	}

	public static boolean insertFailureUserDtlsForBlockUser(DTOUserLoginFailureDetails dto,
			int Mode) {
		return UserLoginFailureDetails.insertFailureUserDtlsForBlockUser(dto, Mode);
	}
	
	public static ArrayList<DTOUserLoginFailureDetails> getFailureUserDetail(
			String username) {
		return UserLoginFailureDetails.getFailureUserDetail(username);
	}


	public static int getTimeDifference(String UserName) {
		return UserLoginFailureDetails.getTimeDifference(UserName);
	}

	public boolean EncryptAll(int Mode) {
		return userMst.EncryptAll(Mode);
	}
	
	public static int checkUserLoginDetails(int UserId) {             
        return UserLoginDetails.checkUserloginDetails(UserId);
	}

	public static ArrayList<DTOPasswordPolicyMst> getAllPolicyDetails(String policy) {    
		return PasswordPolicyMst.getAllPolicyDetails(policy);
	}
	
	public static Vector<DTOUserLoginDetails> getLastActivity(int userCode) {
		return UserLoginDetails.getLastActivity(userCode);
	}

	// //////////////////////SubmissionInfoCADtl \\\\\\\\\\\\\\\\\\\\\\\\\\
	public static String insertSubmissionInfoCADtl(DTOSubmissionInfoCADtl dto,
			int DATAOPMODE) {
		return SubmissionInfoCADtl.insertSubmissionInfoCADtl(dto, DATAOPMODE);
	}

	public static Vector<DTOSubmissionInfoCADtl> getWorkspaceSubmissionInfoCADtl(
			String workspaceId) {
		return SubmissionInfoCADtl.getWorkspaceSubmissionInfoCADtl(workspaceId);
	}

	public static DTOSubmissionInfoCADtl getWorkspaceSubmissionInfoCADtlBySubmissionId(
			String submissionId) {
		return SubmissionInfoCADtl
				.getWorkspaceSubmissionInfoCADtlBySubmissionId(submissionId);
	}

	public void insertSubmissionInfoCAMst(DTOSubmissionMst dto) {
		submissionInfoMst.insertSubmissionInfoCAMst(dto);
	}

	public static void insertIntoDefaultWorkspaceMst(
			DTODefaultWorkSpaceMst dto, int Mode) {
		DefaultWorkspaceMst.insertIntoDefaultWorkspaceMst(dto, Mode);
	}

	public static ArrayList<DTODefaultWorkSpaceMst> getDefaultWorkspaceMst(
			int Usercode) {
		return DefaultWorkspaceMst.getDefaultWorkspaceMst(Usercode);
	}

	// //////////////////////////NoticeMst////////////////////////////

	public boolean insertIntoNoticeMst(DTONoticeMst dto) {
		return noticeMst.insertIntoNoticeMst(dto);
	}

	public boolean updateInNoticeMst(DTONoticeMst dto) {
		return noticeMst.updateInNoticeMst(dto);
	}

	public boolean deleteFromNoticeMst(DTONoticeMst dto) {
		return noticeMst.deleteFromNoticeMst(dto);
	}

	public Vector<DTONoticeMst> getAllNotices(int userCode) {
		return noticeMst.getAllNotices(userCode);
	}

	public DTONoticeMst getNotice(int noticeNo, int userCode) {
		return noticeMst.getNotice(noticeNo, userCode);
	}

	// //////////////////////Calendar////////////////////////////////////////

	public ArrayList<DTOWorkSpaceNodeAttrDetail> getCalendarEvents(int month,
			int year, int userCode) {
		return workspaceNodeAttrDetail.getCalendarEvents(month, year, userCode);
	}

	public ArrayList<DTOWorkSpaceNodeAttrDetail> getReminderEvents(int userCode) {
		return workspaceNodeAttrDetail.getReminderEvents(userCode);
	}

	public ArrayList<DTOAttributeMst> getCalendarEventAttributes() {
		return workspaceNodeAttrDetail.getCalendarEventAttributes();
	}

	public boolean setEventAttributes(int usercode, String choices) {
		return workspaceNodeAttrDetail.setEventAttributes(usercode, choices);
	}

	public String getCalendarChoices(int usercode) {
		return workspaceNodeAttrDetail.getCalendarChoices(usercode);
	}

	public boolean setReminderAttributes(int usercode, String choices) {
		return workspaceNodeAttrDetail.setReminderAttributes(usercode, choices);
	}

	public String getReminderChoices(int usercode) {
		return workspaceNodeAttrDetail.getReminderChoices(usercode);
	}

	public ArrayList<String> getProjectLevelAttributeValues(String columnName) {
		return hotSearchDetail.getProjectLevelAttributeValues(columnName);
	}

	public static ArrayList<DTOHotsearchDetail> dateWiseSearch(int userCode,
			String attributes, String attrIndiid, String firstSearchValue,
			String secondSearchValue, int mode) {
		return HotSearchDetail.dateWiseSearch(userCode, attributes, attrIndiid,
				firstSearchValue, secondSearchValue, mode);
	}

	public ArrayList<DTOAttributeMst> getAttributesByDisplayType(
			String displayType) {
		return attributeMst.getAttributesByDisplayType(displayType);
	}

	public static ArrayList<DTOContentSearch> contentSearch(int userCode,
			String keyValue, String searchBy, int Mode) {
		return HotSearchDetail
				.contentSearch(userCode, keyValue, searchBy, Mode);
	}

	// ///////////////// Advanced Attribute Search ///////////
	public void insertAttrListForAdvanceSearch(
			ArrayList<DTOAdvancedAttrSearch> userAttrList, int DataOpMode) {
		advanceAttrSearch.insertAttrListForAdvanceSearch(userAttrList,
				DataOpMode);
	}

	public ArrayList<DTOAdvancedAttrSearch> getAllSavedAttrList(int userId) {
		return advanceAttrSearch.getAllSavedAttrList(userId);
	}

	public ArrayList<DTOContentSearch> getSearchResult(int userId,
			String AttrIorIndiId, String FinalQueryString) {
		return advanceAttrSearch.getSearchResult(userId, AttrIorIndiId,
				FinalQueryString);
	}

	// //////////////////CalendarAndReminder////////////////////
	public ArrayList<DTOWorkspaceNodeReminderDoneStatus> getReminderDoneStatus() {
		return calendarAndReminder.getReminderDoneStatus();
	}

	public ArrayList<DTOWorkspaceNodeReminderIgnoreStatus> getReminderIgnoreStatus(
			int userCode) {
		return calendarAndReminder.getReminderIgnoreStatus(userCode);
	}

	public boolean markReminderAsDone(String vWorkspaceId, int iNodeId,
			int iAttrId, int userCode) {
		return calendarAndReminder.markReminderAsDone(vWorkspaceId, iNodeId,
				iAttrId, userCode);
	}

	public boolean markReminderAsIgnore(String vWorkspaceId, int iNodeId,
			int iAttrId, int userCode, String ignoreUpto) {
		return calendarAndReminder.markReminderAsIgnore(vWorkspaceId, iNodeId,
				iAttrId, userCode, ignoreUpto);
	}

	public boolean markReminderAsUnIgnore(String vWorkspaceId, int iNodeId,
			int iAttrId, int userCode) {
		return calendarAndReminder.markReminderAsUnIgnore(vWorkspaceId,
				iNodeId, iAttrId, userCode);
	}

	public boolean markReminderAsUnDone(
			ArrayList<DTOWorkspaceNodeReminderDoneStatus> dtoList) {
		return calendarAndReminder.markReminderAsUnDone(dtoList);
	}

	// //////////////////DynamicQueries////////////////////
	public int checkTableExists(String tableName) {
		return dynamicQueries.checkTableExists(tableName);
	}

	public boolean importMasterInDB(String tableName, String[] columnNames,
			Vector<Vector<String>> tableData) {
		return dynamicQueries.importMasterInDB(tableName, columnNames,
				tableData);
	}

	public void insertIntoXlsMstList(String tableName, String uploadedFilePath) {
		dynamicQueries.insertIntoXlsMstList(tableName, uploadedFilePath);
	}

	public ArrayList<DTOImportedXLSMst> getXLSMstList() {
		return dynamicQueries.getXLSMstList();
	}

	public DTODynamicTable getTableDetails(String tableName) {
		return dynamicQueries.getTableDetails(tableName);
	}

	public DTODynamicTable getCompleteTableDetails(String tableName) {
		return dynamicQueries.getCompleteTableDetails(tableName);
	}

	public boolean updateRecord(String tableName, String oldValues[],
			String newValues[]) {
		return dynamicQueries.updateRecord(tableName, oldValues, newValues);
	}

	public boolean addRecord(String tableName, String newValues[]) {
		return dynamicQueries.addRecord(tableName, newValues);
	}

	public void Insert_AttrReferenceTableMatrix(
			DTOAttrReferenceTableMatrix dtoAttrReferenceTableMatrix, int mode) {
		dynamicQueries.Insert_AttrReferenceTableMatrix(
				dtoAttrReferenceTableMatrix, mode);
	}

	public ArrayList<DTOAttrReferenceTableMatrix> getFromAttrReferenceTableMatrix(
			DTOAttrReferenceTableMatrix dtoAttrReferenceTableMatrix) {
		return dynamicQueries
				.getFromAttrReferenceTableMatrix(dtoAttrReferenceTableMatrix);
	}

	// ////////////////////////////SubmissionQueryMst\\\\\\\\\\\\\\\\\\\\\\
	public long insertSubmissionQueryMst(
			ArrayList<DTOSubmissionQueryMst> subQueryMstList, int opMode) {
		return submissionQueryMst.insertSubmissionQueryMst(subQueryMstList,
				opMode);
	}

	// //////////////////////////// SubmissionQueryUserDtl -- on 22/05/2013 by
	// butani vijay\\\\\\\\\\\\\\\\\\\\\\\

	public void insertSubmissionQueryUserDtl(
			ArrayList<DTOSubmissionQueryUserDtl> subQueryDtlList, int opMode) {
		submissionQueryUserDtl.insertSubmissionQueryUserDtl(subQueryDtlList,
				opMode);
	}

	public void deleteSubmissionQueryUserDtl(long iQueryId) {
		submissionQueryUserDtl.deleteSubmissionQueryUserDtl(iQueryId);
	}

	// ////////////////////////////SubmissionQueryDtl\\\\\\\\\\\\\\\\\\\\\\

	public void insertSubmissionQueryDtl(
			ArrayList<DTOSubmissionQueryDtl> subQueryDtlList, int opMode) {
		submissionQueryDtl.insertSubmissionQueryDtl(subQueryDtlList, opMode);
	}

	public ArrayList<DTOSubmissionQueryMst> getWorkspaceQueryDtls(
			String workspaceId) {
		return submissionQueryDtl.getWorkspaceQueryDtls(workspaceId);
	}

	public ArrayList<DTOSubmissionQueryMst> getWorkspaceQueryDtlsByUser(
			String workspaceId, long usercode) {
		return submissionQueryDtl.getWorkspaceQueryDtlsByUser(workspaceId,
				usercode);
	}

	public DTOSubmissionQueryMst getWorkspaceQueryDtlsByQueryId(long queryId) {
		return submissionQueryDtl.getWorkspaceQueryDtlsByQueryId(queryId);
	}

	// added for getting query for particular user by butani vijay

	public ArrayList<String> getWorkspaceIdForSubmittedQuery() {
		return submissionQueryDtl.getWorkspaceIdForSubmittedQuery();
	}

	// ///////////////////////////////SubmissionInfoDMSDtl\\\\\\\\\\\\\\\\\\\\\\\\\\
	public ArrayList<DTOWorkSpaceMst> getDMSSubmissionInfo(
			ArrayList<String> workspaceIdList) {
		return submissionInfoDMSDtl.getDMSSubmissionInfo(workspaceIdList);
	}

	public Vector<DTOSubmissionInfoDMSDtl> getDMSSubmissionInfoforarchive(String workspaceId) {
		return submissionInfoDMSDtl.getDMSSubmissionInfoforarchive(workspaceId);
	}
	
	public void insertSubmissionInfoDMSDtl(DTOSubmissionInfoDMSDtl dto,
			int opMode) {
		submissionInfoDMSDtl.insertSubmissionInfoDMSDtl(dto, opMode);
	}

	public DTOSubmissionInfoDMSDtl getDMSSubmissionInfoBySubId(long subDtlId) {
		return submissionInfoDMSDtl.getDMSSubmissionInfoBySubId(subDtlId);
	}

	public int getMaxLabelNoForDMSPublish(String wsId, int mode) {
		return submissionInfoDMSDtl.getMaxLabelNoForDMSPublish(wsId, mode);
	}

	public Vector<DTOSubmissionInfoDMSDtl> getPublishDetailHistory(String wsId, String subDtlId, String currSeqNo) {
		return submissionInfoDMSDtl.getPublishDetailHistory(wsId,subDtlId,currSeqNo);
	}
	
	// ///////////////////////////////UserDocStageComments\\\\\\\\\\\\\\\\\\\\\\\\\\

	public boolean insertUserDocStageComments(DTOUserDocStageComments dto,
			int mode) {
		return userDocStageComments.insertUserDocStageComments(dto, mode);
	}

	public ArrayList<DTOUserDocStageComments> getStageWiseDocComments(
			String workspaceId, int nodeId, String stageId) {
		return userDocStageComments.getStageWiseDocComments(workspaceId,
				nodeId, stageId);
	}

	// /////////////////////////////workspaceUserAuditTrailMst\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	public void insertWorkspaceUserAuditTrailDetail(
			ArrayList<DTOWorkspaceUserAuditTrailMst> userAuditTrailList) {
		workspaceUserAuditTrailMst
				.insertWorkspaceUserAuditTrailDetail(userAuditTrailList);
	}

	public ArrayList<DTOWorkspaceUserAuditTrailMst> getWorkspaceUserAuditTrail(
			ArrayList<DTOWorkspaceUserAuditTrailMst> wsUserAuditTrailList,
			boolean wsId, boolean nodeId, boolean stageId, boolean assignedBy,
			boolean assignedTo) {
		return workspaceUserAuditTrailMst.getWorkspaceUserAuditTrail(
				wsUserAuditTrailList, wsId, nodeId, stageId, assignedBy,
				assignedTo);
	}

	public int getMaxAuditId() {
		return workspaceUserAuditTrailMst.getMaxAuditId();
	}

	// ////////////////////////////// DocReleaseTrack
	// ////////////////////////////////
	public void insertDocReleaseTrack(
			ArrayList<DTODocReleaseTrack> docReleaseTrackList) {
		docReleaseTrack.insertDocReleaseTrack(docReleaseTrackList);
	}

	public ArrayList<DTODocReleaseTrack> getDocReleaseTrack(String workspaceId,
			int nodeId) {
		return docReleaseTrack.getDocReleaseTrack(workspaceId, nodeId);
	}

	// ///////////////////////// ReleaseDocMgmt /////////////////////////////
	public void insertReleaseDocMgmt(
			ArrayList<DTOReleaseDocMgmt> releaseDocMgmtList) {
		releaseDocMgmt.insertReleaseDocMgmt(releaseDocMgmtList);
	}

	public ArrayList<DTOReleaseDocMgmt> getReleaseDocDtl(String workspaceId,
			ArrayList<Integer> nodeIdList) {
		return releaseDocMgmt.getReleaseDocDtl(workspaceId, nodeIdList);
	}

	// ////////////////////// Workspace Node Reference Detail
	// \\\\\\\\\\\\\\\\\\\\\\\\\
	public void insertWorkspaceNodeReferenceDetail(
			ArrayList<DTOWorkspaceNodeReferenceDetail> workspaceNodeReferenceList,
			int DATAOPMODE) {
		workspaceNodeReferenceDetail.insertWorkspaceNodeReferenceDetail(
				workspaceNodeReferenceList, DATAOPMODE);
	}

	public ArrayList<DTOWorkspaceNodeReferenceDetail> getWorkspaceNodeRefereceDtl(
			String workspaceId, int nodeId, boolean isActive) {
		return workspaceNodeReferenceDetail.getWorkspaceNodeRefereceDtl(
				workspaceId, nodeId, isActive);
	}

	// /////////////////////////////////////// Employee Master
	// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public ArrayList<DTOEmployeeMst> getAllEmployeeList(int statusMode) {
		return employeeMaster.getAllEmployeeList(statusMode);
	}

	public ArrayList<DTOEmployeeMst> getEmployeeList(DTOEmployeeMst dtoEmpMst,
			int statusMode) {
		return employeeMaster.getEmployeeList(dtoEmpMst, statusMode);
	}

	public void insertEmployeeMst(ArrayList<DTOEmployeeMst> empMstList) {
		employeeMaster.insertEmployeeMst(empMstList);
	}

	public ArrayList<DTOEmployeeMst> getTrainingEmployeeDtl(int nEmpNo) {
		return employeeMaster.getTrainingEmployeeDtl(nEmpNo);
	}

	// /////////////////////////////////// Training Record Details
	// \\\\\\\\\\\\\\\\\\\\\\\\\
	public void insertTrainingRecordDetails(
			ArrayList<DTOTrainingRecordDetails> tRDtlList) {
		trainingRecordDetails.insertTrainingRecordDetails(tRDtlList);
	}

	public ArrayList<DTOTrainingRecordDetails> getAllTRDetailList(int statusMode) {
		return trainingRecordDetails.getAllTRDetailList(statusMode);
	}

	public ArrayList<DTOTrainingRecordDetails> getTRNoWiseTRDetailList(int tRNo) {
		return trainingRecordDetails.getTRNoWiseTRDetailList(tRNo);
	}

	public ArrayList<DTOTrainingRecordDetails> getTrainingAttendaceReport(
			int trainingRecordNo, String trainingStartDate,
			String trainingEndDate, int conditionOnDate, int conditionOperater) {
		return trainingRecordDetails.getTrainingAttendaceReport(
				trainingRecordNo, trainingStartDate, trainingEndDate,
				conditionOnDate, conditionOperater);
	}

	public ArrayList<DTOEmployeeMst> getEmployeeWiseTrainingReport(int empNo,
			String trainingStartDate, String trainingEndDate,
			int conditionOnDate, int conditionOperater) {
		return trainingRecordDetails.getEmployeeWiseTrainingReport(empNo,
				trainingStartDate, trainingEndDate, conditionOnDate,
				conditionOperater);
	}

	// /////////////////////////////// Training Schedule Details
	// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public int InsertTrainingScheduleDetail(
			ArrayList<DTOTrainingScheduleingDetails> tSDtlList) {
		return trainingScheduleDetail.InsertTrainingScheduleDetail(tSDtlList);
	}

	public ArrayList<DTOTrainingScheduleingDetails> getTRNoWiseTSDetailList(
			int tRNo) {
		return trainingScheduleDetail.getTRNoWiseTSDetailList(tRNo);
	}

	public ArrayList<DTOTrainingScheduleingDetails> getTSNoWiseTSDetailList(
			int tSNo) {
		return trainingScheduleDetail.getTSNoWiseTSDetailList(tSNo);
	}

	// ///////////////////////////// Training Attendance Master
	// \\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public void insert_TrainingAttendanceMst(
			ArrayList<DTOTrainingAttendanceMst> trainingAttendanceMstList) {
		trainingAttendanceMst
				.insert_TrainingAttendanceMst(trainingAttendanceMstList);
	}

	public ArrayList<DTOTrainingAttendanceMst> getEMPNoWiseTADetailList(
			int empNo) {
		return trainingAttendanceMst.getEMPNoWiseTADetailList(empNo);
	}

	public ArrayList<DTOTrainingAttendanceMst> getTSNoWiseTADetailList(int tSNo) {
		return trainingAttendanceMst.getTSNoWiseTADetailList(tSNo);
	}

	public ArrayList<DTOTrainingAttendanceMst> getDeptWiseTADetailList(
			int deptCode) {
		return trainingAttendanceMst.getDeptWiseTADetailList(deptCode);
	}

	public ArrayList<DTOTrainingAttendanceMst> getTrainerList(int tSNo) {
		return trainingAttendanceMst.getTrainerList(tSNo);
	}

	public ArrayList<DTOTrainingAttendanceMst> getTraineeList(int tSNo) {
		return trainingAttendanceMst.getTraineeList(tSNo);
	}

	// pdf publish
	public boolean insertPdfPublishDetail(DTOPdfPublishDtl pdfpublishDTO,
			int DATAOPMODE) {
		return pdfPublishDtl.insertIntoPdfPublishDtl(pdfpublishDTO, DATAOPMODE);
	}

	public DTOPdfPublishDtl getPdfPublishDtlById(String workspaceId) {
		return pdfPublishDtl.getPdfPublishDtlById(workspaceId);
	}
	
	//Advance Manual Project Module
	public String getLocationCode(String workspaceId) 
	{
		return workspaceMst.getLocationCodeById(workspaceId);
	}

	public void updateLastPublishedVersion(String workspaceId,String latestSequence) 
	{
		workspaceMst.updateLastPublishedVersion(workspaceId,latestSequence);
	}

	public void insertInternalLableMst(String vWorkspaceId, int iUserId)
	{
		internalLableMst.insertInsertInternalLableMst(vWorkspaceId, iUserId);
	}

	public int getRepeatNodeId(String vWorkspaceId, String nodeToRepeat) 
	{
		return workspaceNodeDetail.getRepeatNodeId(vWorkspaceId, nodeToRepeat);
	}

	public int getAttribuuteIdByName(String attributeName) 
	{
		return attributeMst.getAttribuuteIdByName(attributeName);
	}

	public int getIdFromNodeName(int parentId, String nodeName, String workspaceId)
	{
		return workspaceNodeDetail.getIdFromNodeName(parentId, nodeName, workspaceId);
	}

	public Vector<Integer> getNodeIdFromNodeName(String vWorkspaceId,
			String nodeName) {
		return workspaceNodeDetail.getNodeIdFromNodeName(vWorkspaceId, nodeName);
	}
	public ArrayList<String> getParentIdFromNodeNameAndWorkspaceId(String vWorkspaceId,
			String nodeName) {
		return workspaceNodeDetail.getParentIdFromNodeNameAndWorkspaceId(vWorkspaceId, nodeName);
	}
	public int getParentNodeIdFromNodeName(String vWorkspaceId,
			String nodeName) {
		return workspaceNodeDetail.getParentNodeIdFromNodeName(vWorkspaceId, nodeName);
	}
	public ArrayList<DTOWorkSpaceNodeAttrDetail> getAttributeDetailfromWorkspaceIdAndNodeId(
			String vWorkspaceId, String parentId) 
	{
		return workspaceNodeAttrDetail.getAttributeDetailfromWorkspaceIdAndNodeId(vWorkspaceId,parentId); 
	}
	public DTOWorkSpaceNodeAttrDetail getAttributeDetailByAttrName(String vWorkspaceId, int nodeId,String attrName) 
	{
		return workspaceNodeAttrDetail.getAttributeDetailByAttrName(vWorkspaceId,nodeId,attrName); 
	}
	
	public Vector<DTOWorkSpaceNodeAttrDetail> getAttributeDetailHistoryByAttrName(String vWorkspaceId, int nodeId,String attrName) 
	{
		return workspaceNodeAttrDetail.getAttributeDetailHistoryByAttrName(vWorkspaceId,nodeId,attrName); 
	}
	
	public Vector<DTOWorkSpaceNodeAttribute> getAttrDtl(
			String wsId, int nodeId) {
		return workspaceNodeAttrDetail.getAttrDtl(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeAttribute> getAttrDtlForPageSetting(
			String wsId, int nodeId) {
		return workspaceNodeAttrDetail.getAttrDtlForPageSetting(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeAttribute> getAttrDtlForCoverPage(
			String wsId, int nodeId) {
		return workspaceNodeAttrDetail.getAttrDtlForCoverPage(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeAttribute> getAttrDtForPdfHeader(
			String wsId, int nodeId) {
		return workspaceNodeAttrDetail.getAttrDtForPdfHeader(wsId, nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeAttribute> getTimelineAttrDtl(String wsId) {
		return workspaceNodeAttrDetail.getTimelineAttrDtl(wsId);
	}
	public Vector<DTOWorkSpaceNodeAttribute> getTimelineAttrDtlForTree(String wsId) {
		return workspaceNodeAttrDetail.getTimelineAttrDtlForTree(wsId);
	}
	public int getReferenceId(String vWorkspaceId, String nodeName,
			String parentId) 
	{
		return workspaceNodeDetail.getReferenceId(vWorkspaceId,nodeName,parentId);
	}

	public void insertSubmissionInfoEU20dtl(DTOSubmissionInfoEU20Dtl dto) 
	{
		submissionInfoEU20Dtl.insertSubmissionInfoEU20Dtl(dto, 1);
	}

	public void createManualProject(
			DTOCreateManualProject objDtoCreateManualProject) {
		manualModeSeqZipDtl.createManualProject(objDtoCreateManualProject);
	}
	///For AutoMail Fire Functionality

	
	public Vector<DTOWorkSpaceNodeAttrDetail> getWorkSpaceUserDetailByAttribute(
			String dateToCompare) {
		return workspaceNodeDetail.getWorkSpaceUserDetailByAttribute(dateToCompare);
	}
	

	public boolean UpdateLastPublishVersion(String wsId,String lastPublishedVersion) {
		return workspaceMst.UpdateLastPublishVersion(wsId,lastPublishedVersion);
	}
	public Vector<Object[]> getWorkspaceNodeDetails(String workspaceID) {
		return workspaceNodeDetail.getWorkspaceNodeDetails(workspaceID);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getWorkspaceNodeDetailByNodeId(String workspaceID,int nodeId) throws SQLException {
		return workspaceNodeDetail.getWorkspaceNodeDetailByNodeId(workspaceID,nodeId);
	}
	
	public Vector<DTOWorkSpaceNodeDetail> getChildNodesForDynaTreeCT(String wsId, int nodeId,int usergrpcode,int usercode) {
		return workspaceNodeDetail.getChildNodesForDynaTreeCT(wsId,nodeId,usergrpcode,usercode);
	}
	
	public DTOWorkSpaceNodeDetail getWorkspaceNodeDetail(String wsId, int nodeId) {
		return workspaceNodeDetail.getWorkspaceNodeDetail(wsId,nodeId);
	}
	public  ArrayList<DTOSubmissionInfoForManualMode> getLeafIdsForManualMode(
			String workspaceId, String submissionId) {
		return submissionInfoForManualMode.getLeafIdsForManualMode(
				workspaceId, submissionId);
	}
	public DTOSubmissionInfoForManualMode getWorkspaceManualDetail(String workspaeId,String submissionId,int nodeId) {
		return submissionInfoForManualMode.getWorkspaceManualDetail(workspaeId,submissionId,nodeId);
	}
	public String CheckConfrimSequenceDtl(String workspaceId) {
		return workspaceMst.CheckConfrimSequenceDtl(workspaceId);
	}
	public String CheckSequenceRecompile(String workspaceId,String submissionInfoDtlId) {
		return workspaceMst.CheckSequenceRecompile(workspaceId,submissionInfoDtlId);
	}
	public ArrayList<String> TimeZoneConversion(Timestamp timestamp, String locationName,String countryCode) {
		return timeZoneMst. TimeZoneConversion(timestamp, locationName, countryCode);
	}
	public String convertDoc(String srcPath,String destPath){
		return wordToPdf.convertDoc(srcPath,destPath);
	}
	
	
	///////////////////////////// ConvertPdfToDoc //////////////////////////
	
	public String convertPdfToDoc(String srcPath,String destPath){
		return cpdfTDoc.convertPdfToDoc(srcPath,destPath);
	}
	
	
	// /////////////////////Smarrt Capture\\\\\\\\\\\\\\\\\\\\\\\\\\\\

		public Vector<DTOSmartCaptureMst> getSmartCapDetail() {
			return smartCaptureMst.getSmartCapDetail();
		}
		
		public DTOSmartCaptureMst getSmartCapDetailDTO(String smartcaptureVersionID) {
			return smartCaptureMst.getSmartCapDetailDTO(smartcaptureVersionID);
		}
			
		public void insertIntoSmartCapturedownloadhistory(DTOSmartCaptureMst dto) {
			smartCaptureMst.insertIntoSmartCapturedownloadhistory(dto);
		}
	
	////////////////////// AD User Authentication \\\\\\\\\\\\\\\\\\\
		public String authenticateADUser(String userName, String userPass) throws IOException {
			return adUserAuth.authenticateADUser(userName,userPass);
		}
		
	////////////////////// Hashcode Generation \\\\\\\\\\\\\\\\\\\
		public String generateHashCode(String workspaceID,int nodeId,int stageId ) throws MalformedURLException{
			return hashCodeGen.generateHashCode(workspaceID,nodeId,stageId);
		}
		
		public String generateHashCodeForVoid(String workspaceID,int nodeId,int stageId ) throws MalformedURLException{
			return hashCodeGen.generateHashCodeForVoid(workspaceID,nodeId,stageId);
		}

		
		
		
	
}