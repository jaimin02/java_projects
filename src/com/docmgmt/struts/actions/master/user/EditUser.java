package com.docmgmt.struts.actions.master.user;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTODepartmentMst;
import com.docmgmt.dto.DTOLocationMst;
import com.docmgmt.dto.DTORoleMst;
import com.docmgmt.dto.DTOUserGroupMst;
import com.docmgmt.dto.DTOUserLoginFailureDetails;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOUserTypeMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class EditUser extends ActionSupport{
	

	private static final long serialVersionUID = 1L;

	DTOUserMst user=new DTOUserMst();
	private Vector usertypelist=new Vector();
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public Vector<DTOLocationMst> locationDtl=new Vector<DTOLocationMst>();
	public Vector<DTORoleMst> roleDtl=new Vector<DTORoleMst>();
	public Vector<DTODepartmentMst> getDeptDtl=new Vector<DTODepartmentMst>();
	public Vector<DTOUserGroupMst> getUserGroupDtl;
	private String currentUserType;
	public String message;	
	public boolean blockFlag;
	public boolean delFlag;
	public Vector<DTOUserMst> userdata=new Vector<DTOUserMst>();
	public boolean showUpdateBtn=false;
	@Override
	public String execute()
	{
		
		usertypelist=docMgmtImpl.getAllUserType();
		locationDtl = docMgmtImpl.getLocationDtl();
		roleDtl = docMgmtImpl.getRoleDtl();
		//user=docMgmtImpl.getUserByCode(Integer.parseInt(userCodeForEdit));
		user=docMgmtImpl.getUserDetailByLoginNameId(loginName).get(0);
		//usergrplist=docMgmtImpl.getAllUserGroupByUserType(user.getUserType());
		getDeptDtl=docMgmtImpl.getDepartmentDetail();
		usergrplist=docMgmtImpl.getUserGroupDetails();
				
		currentUserType = (String)ActionContext.getContext().getSession().get("usertypename");
		if (currentUserType.equalsIgnoreCase("IT")) {
			userdata=docMgmtImpl.getAllUserDetailForITUser(loginName);
		}
		else{
			userdata=docMgmtImpl.getUserDetailByName(loginName);
		}
			
		Vector<DTOUserMst> temp=new Vector<DTOUserMst>();
		for (int i=0;i<userdata.size();i++)
		{
			DTOUserMst dto=userdata.get(i);
			//System.out.println("----------------");
			if (dto.getStatusIndi()!='D' && (!dto.getUserType().equals("SU")))
			{
				//System.out.println("if");
				temp.add(userdata.get(i));
			}
			//System.out.println("outer");
		}				
		//usertypelist=docMgmtImpl.getAllUserType();
		//locationDtl = docMgmtImpl.getLocationDtl();
		//roleDtl = docMgmtImpl.getRoleDtl();
		//getDeptDtl=docMgmtImpl.getDepartmentDetail();
		//getUserGroupDtl=docMgmtImpl.getUserGroupDetails();
		
		// Added on 14-5-2015 by dharmendra jadav 

		// Start coding
		for (int index = 0; index < usertypelist.size(); index++) {

			DTOUserTypeMst userTypeDtl = (DTOUserTypeMst) usertypelist
					.get(index);

			if (currentUserType.equalsIgnoreCase("WA")) {
				
				if (userTypeDtl.getUserTypeName().equalsIgnoreCase("PU")) {

					usertypelist.removeElement(userTypeDtl);
				}
			}

			if (currentUserType.equalsIgnoreCase("PU")) {
				if (userTypeDtl.getUserTypeName().equalsIgnoreCase("PU")) {

					usertypelist.removeElement(userTypeDtl);
				}
			}
		}

		// End coding
						
		//this loop would set the blocked flag according to the database; 
		blockFlag=false;
		delFlag=false;
		for (int idxUserCount=0;idxUserCount<userdata.size();idxUserCount++)
		{
			DTOUserMst dto=userdata.get(idxUserCount);
			/*if(dto.getAdUserName()!=null && dto.getAdUserName().contains("\\")){
				dto.setAdUserName(dto.getAdUserName().split("\\\\")[1]);
			}*/
			if (dto.getStatusIndi()=='D')
			{
				delFlag=true;
			}
			//ArrayList<DTOUserLoginFailureDetails> arrDto=DocMgmtImpl.getFailureUserDetail(dto.getLoginName());
			ArrayList<DTOUserLoginFailureDetails> arrDto=DocMgmtImpl.getFailureDetailForUser(dto.getLoginName(),dto.getUserCode());
			if (arrDto.size()<=0)
			{
				dto.setBlockedFlag(false);
				continue;
			}
			DTOUserLoginFailureDetails dtoUserLoginFailureDetails=arrDto.get(0);
			char bFlag = dtoUserLoginFailureDetails.getBlockedFlag();
			if (bFlag=='B')
			{
				dto.setBlockedFlag(true);
				blockFlag=true;
			}
			else
				dto.setBlockedFlag(false);
			
		}
		
		
		
		return SUCCESS;
	}
	public String editUser(){

		showUpdateBtn=true;
		usertypelist=docMgmtImpl.getAllUserType();
		locationDtl = docMgmtImpl.getLocationDtl();
		roleDtl = docMgmtImpl.getRoleDtl();
		getDeptDtl=docMgmtImpl.getDepartmentDetail();
		user=docMgmtImpl.getUserByCode(Integer.parseInt(userCodeForEdit));
		//usergrplist=docMgmtImpl.getAllUserGroupByUserType(user.getUserType());
		usergrplist=docMgmtImpl.getUserGroupDetails();
		
		
		currentUserType = (String)ActionContext.getContext().getSession().get("usertypename");
		if (currentUserType.equalsIgnoreCase("IT")) {
			userdata=docMgmtImpl.getAllUserDetailForITUser(loginName);
		}
		else{
			userdata=docMgmtImpl.getUserDetailByName(loginName);
		}
			
		Vector<DTOUserMst> temp=new Vector<DTOUserMst>();
		for (int i=0;i<userdata.size();i++)
		{
			DTOUserMst dto=userdata.get(i);
			//System.out.println("----------------");
			if (dto.getStatusIndi()!='D' && (!dto.getUserType().equals("SU")))
			{
				//System.out.println("if");
				temp.add(userdata.get(i));
			}
			//System.out.println("outer");
		}				
		usertypelist=docMgmtImpl.getAllUserType();
		//locationDtl = docMgmtImpl.getLocationDtl();
		//roleDtl = docMgmtImpl.getRoleDtl();
		getDeptDtl=docMgmtImpl.getDepartmentDetail();
		getUserGroupDtl=docMgmtImpl.getUserGroupDetails();
		
		// Added on 14-5-2015 by dharmendra jadav 

		// Start coding
		for (int index = 0; index < usertypelist.size(); index++) {

			DTOUserTypeMst userTypeDtl = (DTOUserTypeMst) usertypelist
					.get(index);

			if (currentUserType.equalsIgnoreCase("WA")) {
				
				if (userTypeDtl.getUserTypeName().equalsIgnoreCase("PU")) {

					usertypelist.removeElement(userTypeDtl);
				}
			}

			if (currentUserType.equalsIgnoreCase("PU")) {
				if (userTypeDtl.getUserTypeName().equalsIgnoreCase("PU")) {

					usertypelist.removeElement(userTypeDtl);
				}
			}
		}

		// End coding
						
		//this loop would set the blocked flag according to the database; 
		blockFlag=false;
		delFlag=false;
		for (int idxUserCount=0;idxUserCount<userdata.size();idxUserCount++)
		{
			DTOUserMst dto=userdata.get(idxUserCount);
			/*if(dto.getAdUserName()!=null && dto.getAdUserName().contains("\\")){
				dto.setAdUserName(dto.getAdUserName().split("\\\\")[1]);
			}*/
			if (dto.getStatusIndi()=='D')
			{
				delFlag=true;
			}
			ArrayList<DTOUserLoginFailureDetails> arrDto=DocMgmtImpl.getFailureUserDetail(dto.getLoginName());
			if (arrDto.size()<=0)
			{
				dto.setBlockedFlag(false);
				continue;
			}
			DTOUserLoginFailureDetails dtoUserLoginFailureDetails=arrDto.get(0);
			char bFlag = dtoUserLoginFailureDetails.getBlockedFlag();
			if (bFlag=='B')
			{
				dto.setBlockedFlag(true);
				blockFlag=true;
			}
			else
				dto.setBlockedFlag(false);
			
		}
		
		
		
		return SUCCESS;
	
	}
	
	public String userDetailHistory(){	
		userDetailHistory = docMgmtImpl.getUserDetailHistory(userCode);
		return SUCCESS;	
	}
	
	public Vector userDetailHistory;
	public int userCode;
	public int userGroupCode;
	public String loginName;
	public String userCodeForEdit;
	public String userGroupCodeForEdit;
	public Vector<DTOUserGroupMst> usergrplist = new Vector<DTOUserGroupMst>();
	
	
	public boolean isShowUpdateBtn() {
		return showUpdateBtn;
	}
	public void setShowUpdateBtn(boolean showUpdateBtn) {
		this.showUpdateBtn = showUpdateBtn;
	}
	public Vector<DTOLocationMst> getLocationDtl() {
		return locationDtl;
	}
	public void setLocationDtl(Vector<DTOLocationMst> locationDtl) {
		this.locationDtl = locationDtl;
	}
	public Vector<DTORoleMst> getRoleDtl() {
		return roleDtl;
	}
	public void setRoleDtl(Vector<DTORoleMst> roleDtl) {
		this.roleDtl = roleDtl;
	}
	public String getCurrentUserType() {
		return currentUserType;
	}
	public void setCurrentUserType(String currentUserType) {
		this.currentUserType = currentUserType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isBlockFlag() {
		return blockFlag;
	}
	public void setBlockFlag(boolean blockFlag) {
		this.blockFlag = blockFlag;
	}
	public boolean isDelFlag() {
		return delFlag;
	}
	public void setDelFlag(boolean delFlag) {
		this.delFlag = delFlag;
	}
	public Vector<DTOUserMst> getUserdata() {
		return userdata;
	}
	public void setUserdata(Vector<DTOUserMst> userdata) {
		this.userdata = userdata;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public int getUserGroupCode() {
		return userGroupCode;
	}

	public void setUserGroupCode(int userGroupCode) {
		this.userGroupCode = userGroupCode;
	}

	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	
	public Vector<DTOUserGroupMst> getUsergrplist() {
		return usergrplist;
	}

	public void setUsergrplist(Vector<DTOUserGroupMst> usergrplist) {
		this.usergrplist = usergrplist;
	}

	public String getUserCodeForEdit() {
		return userCodeForEdit;
	}

	public void setUserCodeForEdit(String userCodeForEdit) {
		this.userCodeForEdit = userCodeForEdit;
	}

	public String getUserGroupCodeForEdit() {
		return userGroupCodeForEdit;
	}

	public void setUserGroupCodeForEdit(String userGroupCodeForEdit) {
		this.userGroupCodeForEdit = userGroupCodeForEdit;
	}

	public DTOUserMst getUser() {
		return user;
	}

	public void setUser(DTOUserMst user) {
		this.user = user;
	}

	
	public Vector getUsertypelist() {
		return usertypelist;
	}

	public void setUsertypelist(Vector usertypelist) {
		this.usertypelist = usertypelist;
	}
	public Vector<DTODepartmentMst> getGetDeptDtl() {
		return getDeptDtl;
	}
	public void setGetDeptDtl(Vector<DTODepartmentMst> getDeptDtl) {
		this.getDeptDtl = getDeptDtl;
	}
	
}
