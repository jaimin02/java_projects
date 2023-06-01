package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOMenuMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.docmgmt.server.webinterface.services.CryptoLibrary;
import com.opensymphony.xwork2.ActionContext;

public class UserMst {
	private CryptoLibrary encryption = new CryptoLibrary();

	static DataTable dataTable = new DataTable();

	public DTOUserMst getUserByCode(int code) {
		DTOUserMst userobj = new DTOUserMst();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String FieldNames = "UserCode,UserGroupCode,UserName,LoginId,LoginName,AdUserName,IsAdUser,IsVerified,LoginPass,UserTypeCode,ModifyOnForUserMst,"
					+ "StatusIndiForUserMst,UserGroupName,Remark,userLocationName,userMstLocation,RoleCode,RoleName,DeptCode,DeptName,CountryCode";
			ResultSet rs = dataTable.getDataSet(con, FieldNames,
					"view_userDetail", "usercode=" + code, "");
			if (rs.next()) {
				userobj.setUserCode(rs.getInt("userCode"));
				userobj.setUserGroupCode(rs.getInt("UserGroupCode"));
				userobj.setUserName(rs.getString("UserName"));
				userobj.setLoginId(rs.getString("LoginId"));
				userobj.setLoginName(rs.getString("LoginName"));
				userobj.setAdUserName(rs.getString("AdUserName"));
				
				char isAdUser = 0 ;
				if(rs.getString("IsAdUser")!=null){
					isAdUser = rs.getString("IsAdUser").charAt(0);
					userobj.setIsAdUser(isAdUser);
				}
				else{
					userobj.setIsAdUser(isAdUser);
				}
				
				char isVerified = 0 ;
				if(rs.getString("IsVerified")!=null){
					isVerified = rs.getString("IsVerified").charAt(0);
				userobj.setIsVerified(isVerified);}
				else{userobj.setIsVerified(isVerified);}
				
				userobj.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
				userobj.setUserType(rs.getString("UserTypeCode"));
				userobj.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
				userobj.setStatusIndi(rs.getString("StatusIndiForUserMst").charAt(0));
				userobj.setUserGroupName(rs.getString("UserGroupName"));
				userobj.setRemark(rs.getString("Remark"));
				userobj.setLocationCode(rs.getString("userMstLocation"));
				userobj.setUserLocationName(rs.getString("userLocationName"));
				userobj.setCountyCode(rs.getString("CountryCode"));
				userobj.setRoleCode(rs.getString("RoleCode"));
				userobj.setRoleName(rs.getString("RoleName"));
				userobj.setDeptCode(rs.getString("DeptCode"));
				userobj.setDeptName(rs.getString("DeptName"));
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userobj;
	}

	public String generateUserTyeBasedMenu(String userType) {
		StringBuffer menuBuffer = new StringBuffer();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			Vector<DTOMenuMst> menudata = new Vector<DTOMenuMst>();
			String whr = "usertypecode = '" + userType
					+ "' AND parentoperationcode = '-999'";
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet menuRs = dataTable.getDataSet(con, "*",
					"view_generateMenu", whr, "parentoperationcode, iseqno");
			while (menuRs.next()) {
				DTOMenuMst menu = new DTOMenuMst();
				menu.setOpCode(menuRs.getString("operationcode"));
				menu.setOpName(menuRs.getString("operationname"));
				menu.setOpPath(menuRs.getString("operationpath"));
				menu.setOppCode(menuRs.getString("parentoperationcode"));
				menu.setOpActive(menuRs.getString("activeflag"));
				menu.setOpSubMenuStatus(menuRs.getInt("val"));
				menudata.addElement(menu);
			}
			menuRs.close();

			DTOMenuMst menutemp1 = new DTOMenuMst();

			for (int i = 0; i < menudata.size(); i++) {
				DTOMenuMst menutemp = (DTOMenuMst) menudata.get(i);
				if (menutemp.getOppCode().equals("-999")) {
					menuBuffer.append(getMenuItem(con, userType, menutemp,
							request));
				}

			}
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return menuBuffer.toString();

	}

	private StringBuffer getMenuItem(Connection con, String userTypeCode,
			DTOMenuMst currMenuItem, HttpServletRequest request)
			throws SQLException {

		String activeFlag = "";
		StringBuffer menuBuffer = new StringBuffer();
		Vector<DTOMenuMst> menuChildren = new Vector<DTOMenuMst>();
		String whr = "UserTypeCode = " + userTypeCode
				+ " AND ParentOperationCode = '" + currMenuItem.getOpCode()
				+ "'";
		ResultSet menuRs = dataTable.getDataSet(con, "*", "View_GenerateMenu",
				whr, "parentoperationcode, iseqno");
		while (menuRs.next()) {
			DTOMenuMst menu = new DTOMenuMst();
			menu.setOpCode(menuRs.getString("operationcode"));
			menu.setOpName(menuRs.getString("operationname"));
			menu.setOpPath(menuRs.getString("operationpath"));
			menu.setOppCode(menuRs.getString("parentoperationcode"));
			menu.setOpActive(menuRs.getString("activeflag"));
			menu.setOpSubMenuStatus(menuRs.getInt("val"));

			// If child is active then add to list.
			if (menu.getOpActive().equals("Y")) {
				menuChildren.addElement(menu);
			}

		}

		if (menuChildren.size() > 0) {// If parent Menu Item
			menuBuffer.append("<li id="+currMenuItem.opName+">");
			menuBuffer.append("<a class='parent' href='#'><span >"
					+ currMenuItem.opName + "</span></a>");
			menuBuffer.append("<div><ul>");

			StringBuffer childrenBuffer = null;
			for (int j = 0; j < menuChildren.size(); j++) {
				DTOMenuMst childMenuItem = (DTOMenuMst) menuChildren.get(j);
				if (childrenBuffer == null) {
					childrenBuffer = getMenuItem(con, userTypeCode,
							childMenuItem, request);
				} else {
					childrenBuffer.append(getMenuItem(con, userTypeCode,
							childMenuItem, request));
				}
			}
			if (childrenBuffer == null || childrenBuffer.toString().equals("")) {
				// Delete above added menu Item if no children found.
				menuBuffer = new StringBuffer("");
			} else {
				// Add children Items
				menuBuffer.append(childrenBuffer);

				// Complete Menu Item
				menuBuffer.append("</ul></div>");
				menuBuffer.append("</li>");
			}
		}

		// Parent with No children
		else if (currMenuItem.getOpPath() == null
				|| currMenuItem.getOpPath().equals("")) {
			// Do nothing
		} else {// If child Menu Item(Has an Action to call...)
			String strAction = "";
			strAction = request.getContextPath() + "/"
					+ currMenuItem.getOpPath();
			menuBuffer.append("<li id="+currMenuItem.getOpName().trim()+">");
			menuBuffer.append("<a href=\"" + strAction.trim() + "\"" + ">");
			menuBuffer.append("<span>" + currMenuItem.getOpName().trim()
					+ "</span>");
			menuBuffer.append("</a></li>");
		}

		return menuBuffer;
	}

	public String getUserTypeName(int usercode) {
		String userTypeName = "";
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "usertypename",
					"view_userdetail", "userCode=" + usercode, "");
			if (rs.next()) {
				userTypeName = rs.getString("usertypename");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userTypeName;

	}
	public String getUserGroupClientCode(int usergroupcode) {
		String userGroupClientCode = "";
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "ClientCode",
					"view_userdetail", "UserGroupCode=" + usergroupcode, "");
			if (rs.next()) {
				userGroupClientCode = rs.getString("ClientCode");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userGroupClientCode;

	}
	public String getDeptCode(int usercode,int usergroupcode) {
		String deptCode = "";
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "DeptCode",
					"view_userdetail", "usercode=" + usercode+" and UserGroupCode=" + usergroupcode, "");
			if (rs.next()) {
				deptCode = rs.getString("DeptCode");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return deptCode;

	}
	public int checkLoginName(String loginName, int userGroupCode) {
		int valid = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "vLoginName", "userMst", "vLoginName='" + loginName + "' And iUserGroupCode='"+userGroupCode+"' And cStatusIndi<>'D'", "");
			if (rs.next()) {
				valid = 1;
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valid;

	}
	
	public int checkLoginId(String loginName, int userGroupCode) {
		int valid = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "vLoginId", "userMst", "vLoginId='" + loginName + "' And iUserGroupCode='"+userGroupCode+"' And cStatusIndi<>'D'", "");
			if (rs.next()) {
				valid = 1;
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valid;

	}
	
	public int checkLoginNameForAddUser(String loginName) {
		int valid = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "vLoginName", "userMst", "vLoginName='" + loginName + "' And cStatusIndi<>'D'", "");
			if (rs.next()) {
				valid = 1;
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valid;

	}
	
	public int checkLoginIdForAddUser(String loginName) {
		int valid = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "vLoginId", "userMst", "vLoginId='" + loginName + "' And cStatusIndi<>'D'", "");
			if (rs.next()) {
				valid = 1;
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return valid;

	}

	public Vector<DTOUserMst> getAllUserDetailForProjects() {
		
		Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "*", "view_userDetail",
					"usertypename='WU' and StatusIndiForUserMst<>'D'", "");
			while (rs.next()) {
				DTOUserMst dto = new DTOUserMst();
				dto.setUserCode(rs.getInt("userCode"));
				dto.setUserGroupCode(rs.getInt("userGroupCode"));
				dto.setUserName(rs.getString("UserName"));
				dto.setLoginId(rs.getString("LoginId"));
				dto.setLoginName(rs.getString("LoginName"));
				dto.setAdUserName(rs.getString("AdUserName"));
				if(rs.getString("IsAdUser")!=null){
					dto.setIsAdUser(rs.getString("IsAdUser").charAt(0));
				}
				if(rs.getString("IsVerified")!=null){
					dto.setIsVerified(rs.getString("IsVerified").charAt(0));
				}
				dto.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
				dto.setUserType(rs.getString("userTypeName"));
				dto.setRemark(rs.getString("Remark"));
				dto.setModifyBy(rs.getInt("ModifyByForUserMst"));
				dto.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
				dto.setStatusIndi(rs.getString("statusIndiForUserMst")
						.charAt(0));
				dto.setUserGroupName(rs.getString("UserGroupName"));
				dto.setUserTypeCode(rs.getString("usertypecode"));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
				}
				else{					
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
				dto.setLocationName(rs.getString("userLocationName"));
				dto.setRoleName(rs.getString("RoleName"));
				userDbDtl.add(dto);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userDbDtl;
	}
public Vector<DTOUserMst> getAllUserDetailForITUser(String loginName) {
		
		Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "*", "view_userDetail","UserTypeCode<>'0001' And LoginName='"+loginName+"'", "");
			while (rs.next()) {
				DTOUserMst dto = new DTOUserMst();
				dto.setUserCode(rs.getInt("userCode"));
				dto.setUserGroupCode(rs.getInt("userGroupCode"));
				dto.setUserName(rs.getString("UserName"));
				dto.setLoginId(rs.getString("LoginId"));
				dto.setLoginName(rs.getString("LoginName"));
				dto.setAdUserName(rs.getString("AdUserName"));
				if(rs.getString("IsAdUser")!=null){
					dto.setIsAdUser(rs.getString("IsAdUser").charAt(0));
				}
				if(rs.getString("IsVerified")!=null){
					dto.setIsVerified(rs.getString("IsVerified").charAt(0));
				}
				dto.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
				dto.setUserType(rs.getString("userTypeName"));
				dto.setRemark(rs.getString("Remark"));
				dto.setModifyBy(rs.getInt("ModifyByForUserMst"));
				dto.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
				dto.setStatusIndi(rs.getString("statusIndiForUserMst")
						.charAt(0));
				dto.setUserGroupName(rs.getString("UserGroupName"));
				dto.setUserTypeCode(rs.getString("usertypecode"));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
				}
				else{					
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
				dto.setLocationName(rs.getString("userLocationName"));
				dto.setRoleName(rs.getString("RoleName"));
				dto.setDeptName(rs.getString("DeptName"));
				userDbDtl.add(dto);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userDbDtl;
	}
	public Vector<DTOUserMst> getuserDetailsById(int[] userCodes) {

		String where = "usercode in (";
		for(int usercode=0;usercode<userCodes.length;usercode++)
		{
			if(usercode!=0)
			{
				where +=",";
			}
			where +=String.valueOf(userCodes[usercode]);
		}
		where +=")";
		System.out.println("Where: "+ where);
		Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "*", "view_userDetail",
					where, "");
			while (rs.next()) {
				DTOUserMst dto = new DTOUserMst();
				dto.setUserCode(rs.getInt("userCode"));
				dto.setUserGroupCode(rs.getInt("userGroupCode"));
				dto.setUserName(rs.getString("UserName"));
				dto.setLoginName(rs.getString("LoginName"));
				dto.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
				dto.setUserType(rs.getString("userTypeName"));
				dto.setRemark(rs.getString("Remark"));
				dto.setModifyBy(rs.getInt("ModifyByForUserMst"));
				dto.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
				dto.setStatusIndi(rs.getString("statusIndiForUserMst")
						.charAt(0));
				dto.setUserGroupName(rs.getString("UserGroupName"));
				dto.setUserTypeCode(rs.getString("usertypecode"));
				userDbDtl.add(dto);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userDbDtl;
	}
	public Vector<DTOUserMst> getUserStatus(int usercode,int usergroupcode)
	{
		Vector<DTOUserMst> statuslist = new Vector<>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = "iUserCode="+usercode+" and iUserGroupCode="+usergroupcode;
			ResultSet rs = dataTable.getDataSet(con, "*", "view_UserStatusReport",
					where, "");
			while (rs.next()) {
				DTOUserMst dto = new DTOUserMst();
				dto.setUserCode(rs.getInt("iUserCode"));
				dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setUserTypeCode(rs.getString("vUserTypeCode"));
				dto.setLoginOn(rs.getTimestamp("dLoginOn"));
				dto.setLoginOut(rs.getTimestamp("dLoginOut"));
				statuslist.add(dto);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return statuslist;
		
	}
	
	/**
	 * Mode=1 for Insert Mode=2 for Update Mode=4 for Change Password Insert and
	 * Update in UserMst
	 */
	public void UserMstOp(DTOUserMst dto, int Mode, boolean isrevert) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			if (Mode == 4) {
				CallableStatement proc = con
						.prepareCall("{ Call Insert_ChangePassword(?,?,?,?,1)}");
				proc.setLong(1, dto.getUserCode());
				proc.setString(2, encryption.encrypt(dto.getLoginPass()));
				proc.setInt(3, dto.getUserCode());
				proc.setString(4, Character.toString(dto.getStatusIndi()));
				proc.execute();
				proc.close();
			} 
			else if (Mode == 5) {

				CallableStatement proc = con
						.prepareCall("{ Call Proc_UpdateUserVerified(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				proc.setInt(1, dto.getUserCode());
				proc.setInt(2, dto.getUserGroupCode());
				proc.setString(3, dto.getAdUserName());
				char isAdUser = dto.getIsAdUser();
				proc.setString(4, Character.toString(isAdUser));
				proc.setString(5, dto.getUserName());
				proc.setString(6, dto.getLoginId());
				proc.setString(7, dto.getLoginName());
				proc.setString(8, encryption.encrypt(dto.getLoginPass()));
				proc.setString(9, dto.getEMail());
				proc.setString(10, dto.getUserType());
				proc.setString(11, dto.getRemark());
				proc.setInt(12, dto.getModifyBy());
				if (isrevert == false) {
					if (Mode == 1)
						proc.setString(13, "N");
					else
						proc.setString(13, "E");
				} else // if revert is true
				{
					char statusIndi = dto.getStatusIndi();
					if ('D' == statusIndi) {
						statusIndi = 'A';
					} else
						statusIndi = 'D';
					proc.setString(13, Character.toString(statusIndi));

				}
				proc.setString(14, dto.getLocationCode());
				proc.setString(15, dto.getRoleCode());
				char statusIndi = 'Y';
				proc.setString(16, Character.toString(statusIndi));
				proc.executeUpdate();
				proc.close();

			
			}
			else if (Mode == 6) {
				CallableStatement proc = con
						.prepareCall("{ Call Insert_ChangePassword(?,?,?,?,2)}");
				proc.setLong(1, dto.getUserCode());
				proc.setString(2, encryption.encrypt(dto.getLoginPass()));
				proc.setInt(3, dto.getUserCode());
				proc.setString(4, Character.toString(dto.getStatusIndi()));
				proc.execute();
				proc.close();
			} 
			else {
				CallableStatement proc = con
						.prepareCall("{ Call Insert_userMst(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				proc.setInt(1, dto.getUserCode());
				proc.setInt(2, dto.getUserGroupCode());
				proc.setString(3, dto.getAdUserName());
				char isAdUser = dto.getIsAdUser();
				proc.setString(4, Character.toString(isAdUser));
				proc.setString(5, dto.getUserName());
				proc.setString(6, dto.getLoginId());
				proc.setString(7, dto.getLoginName());
				proc.setString(8, encryption.encrypt(dto.getLoginPass()));
				proc.setString(9, dto.getEMail());
				proc.setString(10, dto.getUserType());
				proc.setString(11, dto.getRemark());
				proc.setInt(12, dto.getModifyBy());
				if (isrevert == false) {
					if (Mode == 1)
						proc.setString(13, "N");
					else
						proc.setString(13, "E");
				} else // if revert is true
				{
					char statusIndi = dto.getStatusIndi();
					if ('D' == statusIndi) {
						statusIndi = 'A';
					} else
						statusIndi = 'D';
					proc.setString(13, Character.toString(statusIndi));

				}
				proc.setString(14, dto.getLocationCode());
				proc.setString(15, dto.getRoleCode());
				char isVerified = dto.getIsVerified();
				proc.setString(16, Character.toString(isVerified));
				proc.setString(17, dto.getDeptCode());
				proc.setInt(18, Mode);
				proc.executeUpdate();
				proc.close();

			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<DTOUserMst> getWAUserGrpdetails(int userid) {

		Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
		try {
			String whr = "usertypename='WA' and usercode<>"+ userid;
			Connection con = ConnectionManager.ds.getConnection();
			String fields = "userCode,userName,loginName,loginPass,UserGroupName,StatusIndiForUserMst";
			ResultSet rs = dataTable.getDataSet(con, fields, "view_userDetail",
					whr, "");
			while (rs.next()) {
				DTOUserMst dto = new DTOUserMst();
				dto.setUserCode(rs.getInt("userCode"));
				dto.setUserName(rs.getString("UserName"));
				dto.setLoginName(rs.getString("LoginName"));
				dto.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
				dto.setUserGroupName(rs.getString("UserGroupName"));

				dto.setStatusIndi(rs.getString("StatusIndiForUserMst")
						.charAt(0));
				userDbDtl.addElement(dto);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userDbDtl;
	}
	public Vector<DTOUserMst> getuserDetailsByUserGrp(int userGroupCode) {

		Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
		try {
			String whr = "userGroupCode =" + userGroupCode+" and statusindiforusermst<>'D' ";
			Connection con = ConnectionManager.ds.getConnection();
			String fields = "userCode,usergroupcode,usertypename,userName,loginName,loginPass,UserGroupName,StatusIndiForUserMst,RoleCode";
			ResultSet rs = dataTable.getDataSet(con, fields, "view_userDetail",
					whr, "userName");
			while (rs.next()) {
				DTOUserMst dto = new DTOUserMst();
				dto.setUserCode(rs.getInt("userCode"));
				dto.setUserGroupCode(rs.getInt("usergroupcode"));
				dto.setUserName(rs.getString("UserName"));
				dto.setLoginName(rs.getString("LoginName"));
				dto.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
				dto.setUserType(rs.getString("usertypename"));
				dto.setUserGroupName(rs.getString("UserGroupName"));
				dto.setRoleCode(rs.getString("RoleCode"));
				dto.setStatusIndi(rs.getString("StatusIndiForUserMst").charAt(0));
				userDbDtl.addElement(dto);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userDbDtl;
	}
	public Vector<DTOUserMst> getuserDetailsByusermst(int userGroupCode,String worksapceId) {

		Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
		try {
			String whr = "userGroupCode =" + userGroupCode+"and usercode not in" 
         + " (select iUserCode from workspaceusermst where vWorkSpaceId='"+worksapceId+"')";
			Connection con = ConnectionManager.ds.getConnection();
			String fields = "userCode,usergroupcode,userName,loginName,loginPass,UserGroupName,StatusIndiForUserMst";
			ResultSet rs = dataTable.getDataSet(con, fields, "view_userDetail",
					whr, "");
			while (rs.next()) {
				DTOUserMst dto = new DTOUserMst();
				dto.setUserCode(rs.getInt("userCode"));
				dto.setUserGroupCode(rs.getInt("usergroupcode"));
				dto.setUserName(rs.getString("UserName"));
				dto.setLoginName(rs.getString("LoginName"));
				dto.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
				dto.setUserGroupName(rs.getString("UserGroupName"));

				dto.setStatusIndi(rs.getString("StatusIndiForUserMst")
						.charAt(0));
				userDbDtl.addElement(dto);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userDbDtl;
	}

	public DTOUserMst getUserInfo(int userCode) {

		DTOUserMst dto = new DTOUserMst();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable
					.getDataSet(
							con,
							"usergroupcode,loginname,usertypecode,statusindiForUserMst,username",
							"view_userDetail", "UserCode =" + userCode, "");
			while (rs.next()) {
				dto.setUserGroupCode(rs.getInt("usergroupcode"));
				dto.setLoginName(rs.getString("loginname"));
				dto.setUserType(rs.getString("usertypecode"));
				dto.setStatusIndi(rs.getString("statusindiForUserMst")
						.charAt(0));
				dto.setUserName(rs.getString("userName"));
			}

			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	/*public int isValidUser(String userName, String password, int userGroupCode) {

		int userCode = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs =null;
			boolean adFlag=false;
			boolean userFlag=false;
			//if(userCode == -1){
			
			//Checks if user is normal user
				rs = dataTable.getDataSet(con,
						"usercode,StatusIndiForUserMst,isaduser", "view_userDetail",
						//"loginName = '" + userName + "' ", "");
						"loginName like '%" + userName + "' And userGroupCode="+userGroupCode, "");
				while (rs.next()) {
					if(rs.getString("IsAdUser")!=null){
						char isAdUser=rs.getString("isaduser").charAt(0);
						if(isAdUser=='Y'){
							rs = dataTable.getDataSet(con,
									"usercode,StatusIndiForUserMst,isaduser", "view_userDetail",
									//"loginName = '" + userName + "' ", "");
									"adusername like '%" + userName + "' And userGroupCode="+userGroupCode, "");
							while (rs.next()) {
								adFlag=true;
								if (rs.getString("StatusIndiForUserMst").equals("D")) {
									userCode = -3;
								} 
								char isVerified=0;
								if(rs.getString("IsVerified")!=null){
									isVerified = rs.getString("IsVerified").charAt(0);
								}
								if(isVerified==0)
								{
									userCode = -6;
									return userCode;
								}
								if(isVerified!=0 && isVerified=='Y'){
									userCode = rs.getInt("usercode");// userCode
									rs.close();
									con.close();
									return userCode;
								}
								else {
									userCode = rs.getInt("usercode");// userCode
									rs.close();
									con.close();
									return userCode;
									}
								}
						}else{
							userFlag=true;
							password = encryption.encrypt(password);
						//Connection con = ConnectionManager.ds.getConnection();
						ResultSet rs = dataTable.getDataSet(con,
								"usercode,StatusIndiForUserMst,isverified", "view_userDetail",
								"loginName = '" + userName + "'And userGroupCode="+userGroupCode+" And loginPass = '"
										+ password + "'", "");
						while (rs.next()) {
							if (rs.getString("StatusIndiForUserMst").equals("D")) {
								userCode = -3;
							}
							char isVerified=0;
							if(rs.getString("IsVerified")!=null){
								isVerified = rs.getString("IsVerified").charAt(0);
							}
							if(isVerified==0)
							{
								userCode = -6;
								return userCode;
							}
							if(isVerified!=0 && isVerified=='Y'){
								userCode = rs.getInt("usercode");// userCode
								rs.close();
								con.close();
								return userCode;
							}
							else {
								userCode = rs.getInt("usercode");// userCode
								rs.close();
								con.close();
								return userCode;
								}
							}
						}
					}else{
						userFlag=true;
						password = encryption.encrypt(password);
						//Connection con = ConnectionManager.ds.getConnection();
						ResultSet rs = dataTable.getDataSet(con,
								"usercode,StatusIndiForUserMst", "view_userDetail",
								"loginName = '" + userName + "' And userGroupCode="+userGroupCode+" And loginPass = '"
										+ password + "'", "");
						while (rs.next()) {
							if (rs.getString("StatusIndiForUserMst").equals("D")) {
								userCode = -3;
							} else {
								userCode = rs.getInt("usercode");// userCode
								rs.close();
								con.close();
								return userCode;
								}
							}
					}
					if(userFlag==true){
						rs.close();
						con.close();
						return userCode;
					}
					if(adFlag==false){
						userCode= -4;
						rs.close();
						con.close();
						return userCode;
						}
					
					
					if (rs.getString("StatusIndiForUserMst").equals("D")) {
						userCode = -3;
						rs.close();
						con.close();
						return userCode;
					} else {
						if(rs.getString("IsAdUser")!=null){
						char isAdUser=rs.getString("isaduser").charAt(0);
						if(isAdUser=='Y'){
						userCode = rs.getInt("usercode");// userCode
						password = encryption.encrypt(password);
						rs = dataTable.getDataSet(con,
								"usercode,StatusIndiForUserMst", "view_userDetail",
								"loginName like '%" + userName + "' and loginPass = '"
										+ password + "'", "");
						while (rs.next()) {
							adFlag=true;
							if (rs.getString("StatusIndiForUserMst").equals("D")) {
								userCode = -3;
							} else {
								userCode = rs.getInt("usercode");// userCode
								rs.close();
								con.close();
								return userCode;
								}
							}
						
						}
						else{
							userCode = -1;	
							}
						if(adFlag==false){
							userCode= -4;
							rs.close();
							con.close();
							return userCode;
								}
							}
						}
					}
				//}
			
			//Checks if AD User
				rs = dataTable.getDataSet(con,
						"usercode,StatusIndiForUserMst,isaduser", "view_userDetail",
						//"loginName = '" + userName + "' ", "");
						"adusername like '%" + userName + "' And userGroupCode="+userGroupCode, "");
				while (rs.next()) {
					if (rs.getString("StatusIndiForUserMst").equals("D")) {
						userCode = -3;
						rs.close();
						con.close();
						return userCode;
					} else {
						if(rs.getString("IsAdUser")!=null){
						char isAdUser=rs.getString("isaduser").charAt(0);
						if(isAdUser=='Y'){
						userCode = rs.getInt("usercode");// userCode
						password = encryption.encrypt(password);
						rs = dataTable.getDataSet(con,
								"usercode,StatusIndiForUserMst,IsVerified", "view_userDetail",
								"adusername like '%" + userName + "' and loginPass = '"
										+ password + "'", "");
						rs = dataTable.getDataSet(con,
								"usercode,StatusIndiForUserMst,IsVerified", "view_userDetail",
								"adusername like '%" + userName + "' And userGroupCode="+userGroupCode, "");
						while (rs.next()) {
							adFlag=true;
							if (rs.getString("StatusIndiForUserMst").equals("D")) {
								userCode = -3;
							} else {
								char isVerified=0;
								if(rs.getString("IsVerified")!=null){
									isVerified = rs.getString("IsVerified").charAt(0);
								}
								if(isVerified==0)
								{
									userCode = -6;
									return userCode;
								}
								if(isVerified!=0 && isVerified=='Y'){
									userCode = rs.getInt("usercode");// userCode
									rs.close();
									con.close();
									return userCode;
									}
								else{
									userCode = -6;
									return userCode;
								  }
								}
							}
						
						}
						else{
							userCode = -1;	
							rs.close();
							con.close();
							return userCode;
							}
						if(adFlag==false){
							userCode= -4;
							rs.close();
							con.close();
							return userCode;
								}
							}
						}
					}
				
				
				
			
			
			
			
			rs.close();
			con.close();
		} catch (SQLException e) {
			userCode = -2; // if database connection can not get
			e.printStackTrace();
		}
		return userCode;
	}*/
	
	public int isValidUser(String userName, String password) {////This is replicated method created for Disable the profile selection

		int userCode = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs =null;
			boolean adFlag=false;
			boolean userFlag=false;
			//if(userCode == -1){
			
			//Checks if user is normal user
				rs = dataTable.getDataSet(con,"usercode,StatusIndiForUserMst,isaduser", "view_userDetail",
						//"loginName = '" + userName + "' ", "");
						"loginName like '%" + userName + "'", "");
				while (rs.next()) {
					if(rs.getString("IsAdUser")!=null){
						char isAdUser=rs.getString("isaduser").charAt(0);
						if(isAdUser=='Y'){
							rs = dataTable.getDataSet(con,
									"usercode,StatusIndiForUserMst,isaduser", "view_userDetail",
									//"loginName = '" + userName + "' ", "");
									"adusername like '%" + userName + "' ", "");
							while (rs.next()) {
								adFlag=true;
								if (rs.getString("StatusIndiForUserMst").equals("D")) {
									userCode = -3;
								} 
								char isVerified=0;
								if(rs.getString("IsVerified")!=null){
									isVerified = rs.getString("IsVerified").charAt(0);
								}
								if(isVerified==0)
								{
									userCode = -6;
									return userCode;
								}
								if(isVerified!=0 && isVerified=='Y'){
									userCode = rs.getInt("usercode");// userCode
									rs.close();
									con.close();
									return userCode;
								}
								else {
									userCode = rs.getInt("usercode");// userCode
									rs.close();
									con.close();
									return userCode;
									}
								}
						}else{
							userFlag=true;
							password = encryption.encrypt(password);
						//Connection con = ConnectionManager.ds.getConnection();
						rs = dataTable.getDataSet(con,
								"usercode,StatusIndiForUserMst,isverified", "view_userDetail",
								"loginName = '" + userName + "' And loginPass = '"
										+ password + "'", "");
						while (rs.next()) {
							if (rs.getString("StatusIndiForUserMst").equals("D")) {
								userCode = -3;
								return userCode;
							}
							char isVerified=0;
							if(rs.getString("IsVerified")!=null){
								isVerified = rs.getString("IsVerified").charAt(0);
							}
							if(isVerified==0)
							{
								userCode = -6;
								return userCode;
							}
							if(isVerified!=0 && isVerified=='Y'){
								userCode = rs.getInt("usercode");// userCode
								rs.close();
								con.close();
								return userCode;
							}
							else {
								userCode = rs.getInt("usercode");// userCode
								rs.close();
								con.close();
								return userCode;
								}
							}
						}
					}else{
						userFlag=true;
						password = encryption.encrypt(password);
						//Connection con = ConnectionManager.ds.getConnection();
						rs = dataTable.getDataSet(con,
								"usercode,StatusIndiForUserMst", "view_userDetail",
								"loginName = '" + userName + "'  And loginPass = '"
										+ password + "'", "");
						while (rs.next()) {
							if (rs.getString("StatusIndiForUserMst").equals("D")) {
								userCode = -3;
							} else {
								userCode = rs.getInt("usercode");// userCode
								rs.close();
								con.close();
								return userCode;
								}
							}
					}
					if(userFlag==true){
						rs.close();
						con.close();
						return userCode;
					}
					if(adFlag==false){
						userCode= -4;
						rs.close();
						con.close();
						return userCode;
						}
					
					
					if (rs.getString("StatusIndiForUserMst").equals("D")) {
						userCode = -3;
						rs.close();
						con.close();
						return userCode;
					} else {
						if(rs.getString("IsAdUser")!=null){
						char isAdUser=rs.getString("isaduser").charAt(0);
						if(isAdUser=='Y'){
						userCode = rs.getInt("usercode");// userCode
						password = encryption.encrypt(password);
						rs = dataTable.getDataSet(con,
								"usercode,StatusIndiForUserMst", "view_userDetail",
								"loginName like '%" + userName + "' and loginPass = '"
										+ password + "'", "");
						while (rs.next()) {
							adFlag=true;
							if (rs.getString("StatusIndiForUserMst").equals("D")) {
								userCode = -3;
							} else {
								userCode = rs.getInt("usercode");// userCode
								rs.close();
								con.close();
								return userCode;
								}
							}
						
						}
						else{
							userCode = -1;	
							}
						if(adFlag==false){
							userCode= -4;
							rs.close();
							con.close();
							return userCode;
								}
							}
						}
					}
				//}
			
			//Checks if AD User
				rs = dataTable.getDataSet(con,
						"usercode,StatusIndiForUserMst,isaduser", "view_userDetail",
						//"loginName = '" + userName + "' ", "");
						"adusername like '%" + userName + "' and StatusIndiForUserMst<>'D' ", "");
				while (rs.next()) {
					if (rs.getString("StatusIndiForUserMst").equals("D")) {
						userCode = -3;
						rs.close();
						con.close();
						return userCode;
					} else {
						if(rs.getString("IsAdUser")!=null){
						char isAdUser=rs.getString("isaduser").charAt(0);
						if(isAdUser=='Y'){
						userCode = rs.getInt("usercode");// userCode
						password = encryption.encrypt(password);
						rs = dataTable.getDataSet(con,
								"usercode,StatusIndiForUserMst,IsVerified", "view_userDetail",
								"adusername like '%" + userName + "' and loginPass = '"
										+ password + "'", "");
						rs = dataTable.getDataSet(con,
								"usercode,StatusIndiForUserMst,IsVerified", "view_userDetail",
								"adusername like '%" + userName + "' ", "");
						while (rs.next()) {
							adFlag=true;
							if (rs.getString("StatusIndiForUserMst").equals("D")) {
								userCode = -3;
							} else {
								char isVerified=0;
								if(rs.getString("IsVerified")!=null){
									isVerified = rs.getString("IsVerified").charAt(0);
								}
								if(isVerified==0)
								{
									userCode = -6;
									return userCode;
								}
								if(isVerified!=0 && isVerified=='Y'){
									userCode = rs.getInt("usercode");// userCode
									rs.close();
									con.close();
									return userCode;
									}
								else{
									userCode = -6;
									return userCode;
								  }
								}
							}
						
						}
						else{
							userCode = -1;	
							rs.close();
							con.close();
							return userCode;
							}
						if(adFlag==false){
							userCode= -4;
							rs.close();
							con.close();
							return userCode;
								}
							}
						}
					}
				
				
				
			
			
			
			
			rs.close();
			con.close();
		} catch (SQLException e) {
			userCode = -2; // if database connection can not get
			e.printStackTrace();
		}
		return userCode;
	}

	public boolean EncryptAll(int Mode) {

		Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
		boolean retVal = true;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "*", "UserMst", "", "");
			while (rs.next()) {
				DTOUserMst dto = new DTOUserMst();
				dto.setUserCode(rs.getInt("iuserCode"));
				dto.setUserGroupCode(rs.getInt("iuserGroupCode"));
				dto.setUserTypeCode(rs.getString("vUserTypeCode"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setLoginName(rs.getString("vLoginName"));
				dto.setLoginPass(rs.getString("vLoginPass"));
				dto.setRemark(rs.getString("vRemark"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				userDbDtl.add(dto);
			}
			rs.close();

			if (Mode == 1) {// Encrypt All
				for (DTOUserMst userMst : userDbDtl) {
					userMst.setLoginPass(encryption.encrypt(userMst
							.getLoginPass()));
				}
			} else {
				for (DTOUserMst userMst : userDbDtl) {
					userMst.setLoginPass(encryption.decrypt(userMst
							.getLoginPass()));
				}
			}

			CallableStatement proc = con
					.prepareCall("{ Call Insert_userMst(?,?,?,?,?,?,?,?,?,?,?)}");
			for (DTOUserMst userMst2 : userDbDtl) {
				proc.setInt(1, userMst2.getUserCode());
				proc.setInt(2, userMst2.getUserGroupCode());
				proc.setString(3, userMst2.getUserName());
				proc.setString(4, userMst2.getLoginName());
				proc.setString(5, userMst2.getLoginPass());
				proc.setString(6, userMst2.getEMail());
				proc.setString(7, userMst2.getUserTypeCode());
				proc.setString(8, userMst2.getRemark());
				proc.setInt(9, userMst2.getModifyBy());
				proc.setString(10, "E");
				proc.setInt(11, 2); // Edit mode
				proc.executeUpdate();

			}

			proc.close();
			con.close();

		} catch (Exception e) {
			retVal = false;
			e.printStackTrace();

		}

		return retVal;
	}
	
	//Added by Virendra Barad For Get User Detail History
		public Vector<DTOUserMst> getUserDetailHistory(int userCode) {
			
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			Vector<DTOUserMst> userMstDetailHistory = new Vector<DTOUserMst>();
			ResultSet rs = null;
			Connection con = null;
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			try {
				con = ConnectionManager.ds.getConnection();

				String Where = "userCode= "+ userCode;
				rs =  dataTable.getDataSet(con, "*","view_usermsthistory", Where,"TranNo");

				while (rs.next()) {
					DTOUserMst dto = new DTOUserMst();
					dto.setUserName(rs.getString("UserName")); // UserName
					dto.setUserGroupName(rs.getString("UserGroupName")); // UserGroupName
					dto.setLoginId(rs.getString("LoginId")); // LoginId
					dto.setLoginName(rs.getString("LoginName")); // LoginName
					//dto.setModifyBy(rs.getInt("ModifyBy")); // modifyBy
					dto.setUserType(rs.getString("UserTypeName"));  //UserTypeName
					dto.setModifyByName(rs.getString("ModifyBy")); // ModifyByName
					dto.setRemark(rs.getString("Remark")); // remark
					dto.setModifyOn(rs.getTimestamp("ModifyOn")); // modifyOn
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
					dto.setStatusIndi(rs.getString("StatusIndi").charAt(0)); // statusIndi
					dto.setLocationName(rs.getString("userLocationName"));
					dto.setRoleName(rs.getString("RoleName"));
					userMstDetailHistory.addElement(dto);
					dto = null;
				}
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 

			return userMstDetailHistory;	
		}
		//Added by Virendra Barad For Get User Detail History
		public Vector<DTOUserMst> getUserDetail(String usrName) {
			
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			Vector<DTOUserMst> userMstDetailHistory = new Vector<DTOUserMst>();
			ResultSet rs = null;
			Connection con = null;
			try {
				con = ConnectionManager.ds.getConnection();

				String Where = "vLoginName= '"+ usrName +"'";
				rs =  dataTable.getDataSet(con, "*","usermst", Where,"");

				while (rs.next()) {
					DTOUserMst dto = new DTOUserMst();
					dto.setUserCode(rs.getInt("iUserCode"));
					dto.setUserName(rs.getString("vUserName")); // UserName
					dto.setUserGroupCode(rs.getInt("iUserGroupCode")); // UserGroupName
					dto.setLoginName(rs.getString("vLoginName")); // LoginName
					dto.setUserTypeCode(rs.getString("vUserTypeCode")); 
					//dto.setLocationCode(rs.getString("vLocationCode")); 
					userMstDetailHistory.addElement(dto);
					dto = null;
				}

				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return userMstDetailHistory;
		}
		
public Vector<DTOUserMst> getUserDetailByLoginId(String usrName) {
			
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			Vector<DTOUserMst> userMstDetailHistory = new Vector<DTOUserMst>();
			ResultSet rs = null;
			Connection con = null;
			try {
				con = ConnectionManager.ds.getConnection();

				String Where = "vLoginId= '"+ usrName +"'";
				rs =  dataTable.getDataSet(con, "*","usermst", Where,"");

				while (rs.next()) {
					DTOUserMst dto = new DTOUserMst();
					dto.setUserCode(rs.getInt("iUserCode"));
					dto.setUserName(rs.getString("vUserName")); // UserName
					dto.setUserGroupCode(rs.getInt("iUserGroupCode")); // UserGroupName
					dto.setLoginName(rs.getString("vLoginName")); // LoginName
					dto.setUserTypeCode(rs.getString("vUserTypeCode")); 
					//dto.setLocationCode(rs.getString("vLocationCode")); 
					userMstDetailHistory.addElement(dto);
					dto = null;
				}

				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return userMstDetailHistory;
		}
public Vector<DTOUserMst> getUserDetailToShow(String usrName) {
			
			Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			try {
				Connection con = ConnectionManager.ds.getConnection();
				String Where = "LoginName= '"+ usrName +"'";
				ResultSet rs = dataTable.getDataSet(con, "*", "view_userDetail",
						Where, "");
				while (rs.next()) {
					DTOUserMst dto = new DTOUserMst();
					dto.setUserCode(rs.getInt("userCode"));
					dto.setUserGroupCode(rs.getInt("userGroupCode"));
					dto.setUserName(rs.getString("UserName"));
					dto.setLoginName(rs.getString("LoginName"));
					dto.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
					dto.setUserType(rs.getString("userTypeName"));
					dto.setRemark(rs.getString("Remark"));
					dto.setModifyBy(rs.getInt("ModifyByForUserMst"));
					dto.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
					dto.setStatusIndi(rs.getString("statusIndiForUserMst")
							.charAt(0));
					dto.setUserGroupName(rs.getString("UserGroupName"));
					dto.setUserTypeCode(rs.getString("usertypecode"));
					if(countryCode.equalsIgnoreCase("IND")){
						time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
						dto.setISTDateTime(time.get(0));
					}
					else{					
						time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
						dto.setISTDateTime(time.get(0));
						dto.setESTDateTime(time.get(1));
					}
					dto.setLocationName(rs.getString("userLocationName"));
					userDbDtl.add(dto);
				}
				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return userDbDtl;
		}
public Vector<DTOUserMst> getuserDetailsByUserGrpForDocSign(int userGroupCode) {

	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	try {
		String whr = "userGroupCode =" + userGroupCode+" and statusindiforusermst<>'D' ";
		Connection con = ConnectionManager.ds.getConnection();
		String fields = "userCode,usertypename,userName,loginName,loginPass,UserGroupName,StatusIndiForUserMst";
		ResultSet rs = dataTable.getDataSet(con, fields, "view_userDetail",
				whr, "");
		while (rs.next()) {
			DTOUserMst dto = new DTOUserMst();
			dto.setUserCode(rs.getInt("userCode"));
			dto.setUserName(rs.getString("UserName"));
			dto.setLoginName(rs.getString("LoginName"));
			dto.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
			dto.setUserType(rs.getString("usertypename"));
			dto.setUserGroupName(rs.getString("UserGroupName"));

			dto.setStatusIndi(rs.getString("StatusIndiForUserMst")
					.charAt(0));
			userDbDtl.addElement(dto);
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}

public Vector<DTOUserMst> getUserDetailByLoginName(String loginUserName) {
	
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		String FieldNames = "UserCode,UserGroupCode,UserName,LoginName,LoginPass,UserTypeCode,ModifyOnForUserMst,StatusIndiForUserMst,UserGroupName,Remark,userLocationName,userMstLocation,CountryCode";
		ResultSet rs = dataTable.getDataSet(con, FieldNames,
				"view_userDetail", "Loginid='"+loginUserName+"'  ", "");
		while(rs.next()){
			DTOUserMst userobj = new DTOUserMst();
			userobj.setUserCode(rs.getInt("userCode"));
			userobj.setUserGroupCode(rs.getInt("UserGroupCode"));
			userobj.setUserName(rs.getString("UserName"));
			userobj.setLoginName(rs.getString("LoginName"));
			userobj.setLoginPass(encryption.decrypt(rs
					.getString("LoginPass")));
			userobj.setUserType(rs.getString("UserTypeCode"));
			userobj.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
			userobj.setStatusIndi(rs.getString("StatusIndiForUserMst")
					.charAt(0));
			userobj.setUserGroupName(rs.getString("UserGroupName"));
			userobj.setRemark(rs.getString("Remark"));
			userobj.setLocationCode(rs.getString("userMstLocation"));
			userobj.setUserLocationName(rs.getString("userLocationName"));
			userobj.setCountyCode(rs.getString("CountryCode"));
			userDbDtl.add(userobj);
			userobj=null;
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}
public Vector<DTOUserMst> getUserDetailByLoginNameForResendVerificationMail(String loginUserName) {
	
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		String FieldNames = "UserCode,UserGroupCode,UserName,LoginName,LoginPass,UserTypeCode,ModifyOnForUserMst,StatusIndiForUserMst,UserGroupName,Remark,userLocationName,userMstLocation,CountryCode";
		ResultSet rs = dataTable.getDataSet(con, FieldNames,
				"view_userDetail", "LoginName='"+loginUserName+"'  ", "");
		while(rs.next()){
			DTOUserMst userobj = new DTOUserMst();
			userobj.setUserCode(rs.getInt("userCode"));
			userobj.setUserGroupCode(rs.getInt("UserGroupCode"));
			userobj.setUserName(rs.getString("UserName"));
			userobj.setLoginName(rs.getString("LoginName"));
			userobj.setLoginPass(encryption.decrypt(rs
					.getString("LoginPass")));
			userobj.setUserType(rs.getString("UserTypeCode"));
			userobj.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
			userobj.setStatusIndi(rs.getString("StatusIndiForUserMst")
					.charAt(0));
			userobj.setUserGroupName(rs.getString("UserGroupName"));
			userobj.setRemark(rs.getString("Remark"));
			userobj.setLocationCode(rs.getString("userMstLocation"));
			userobj.setUserLocationName(rs.getString("userLocationName"));
			userobj.setCountyCode(rs.getString("CountryCode"));
			userDbDtl.add(userobj);
			userobj=null;
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}
public Vector<DTOUserMst> getUserDetailByLoginNameId(String loginUserName) {
	
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		String FieldNames = "UserCode,UserGroupCode,UserName,LoginName,LoginId,IsVerified,RoleCode,RoleName,DeptCode,DeptName,LoginPass,AdUserName,"
				+ "UserTypeCode,ModifyOnForUserMst,StatusIndiForUserMst,UserGroupName,Remark,userLocationName,userMstLocation,CountryCode,isAdUser";
		ResultSet rs = dataTable.getDataSet(con, FieldNames,
				"view_userDetail", "LoginName='"+loginUserName+"'", "UserCode");
		while(rs.next()){
			DTOUserMst userobj = new DTOUserMst();
			userobj.setUserCode(rs.getInt("userCode"));
			userobj.setUserGroupCode(rs.getInt("UserGroupCode"));
			userobj.setUserName(rs.getString("UserName"));
			userobj.setLoginId(rs.getString("LoginId"));
			userobj.setLoginName(rs.getString("LoginName"));
			userobj.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
			userobj.setAdUserName(rs.getString("AdUserName"));
			userobj.setUserType(rs.getString("UserTypeCode"));
			userobj.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
			userobj.setStatusIndi(rs.getString("StatusIndiForUserMst").charAt(0));
			userobj.setUserGroupName(rs.getString("UserGroupName"));
			userobj.setRemark(rs.getString("Remark"));
			userobj.setLocationCode(rs.getString("userMstLocation"));
			userobj.setUserLocationName(rs.getString("userLocationName"));
			userobj.setCountyCode(rs.getString("CountryCode"));
			userobj.setRoleCode(rs.getString("RoleCode"));
			userobj.setRoleName(rs.getString("RoleName"));
			userobj.setDeptCode(rs.getString("DeptCode"));
			userobj.setDeptName(rs.getString("DeptName"));
		if(rs.getString("IsAdUser")!=null){
			userobj.setIsAdUser(rs.getString("IsAdUser").charAt(0));
		}
		if(rs.getString("IsVerified")!=null){
			userobj.setIsVerified(rs.getString("IsVerified").charAt(0));
		}
			userDbDtl.add(userobj);
			userobj=null;
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}
public Vector<DTOUserMst> getUserLoginName(String loginUserName) {
	
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		String FieldNames = "UserCode,UserGroupCode,UserName,LoginName,LoginPass,UserTypeCode,ModifyOnForUserMst,StatusIndiForUserMst,UserGroupName,Remark,userLocationName,userMstLocation,CountryCode";
		ResultSet rs = dataTable.getDataSet(con, FieldNames,
				"view_userDetail", "LoginName='"+loginUserName+"'  ", "");
		while(rs.next()){
			DTOUserMst userobj = new DTOUserMst();
			userobj.setUserCode(rs.getInt("userCode"));
			userobj.setUserGroupCode(rs.getInt("UserGroupCode"));
			userobj.setUserName(rs.getString("UserName"));
			userobj.setLoginName(rs.getString("LoginName"));
			userobj.setLoginPass(encryption.decrypt(rs
					.getString("LoginPass")));
			userobj.setUserType(rs.getString("UserTypeCode"));
			userobj.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
			userobj.setStatusIndi(rs.getString("StatusIndiForUserMst")
					.charAt(0));
			userobj.setUserGroupName(rs.getString("UserGroupName"));
			userobj.setRemark(rs.getString("Remark"));
			userobj.setLocationCode(rs.getString("userMstLocation"));
			userobj.setUserLocationName(rs.getString("userLocationName"));
			userobj.setCountyCode(rs.getString("CountryCode"));
			userDbDtl.add(userobj);
			userobj=null;
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}
public Vector<DTOUserMst> getUserLoaction(int userId) {
	DTOUserMst userobj = new DTOUserMst();
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		String from ="usermst inner join locationmst on usermst.vLocationCode = locationmst.vLocationCode "
					+ "inner join TimeZoneMst on locationmst.vLocationname = timezonemst.vlocationname";
		ResultSet rs = dataTable.getDataSet(con, "*", from, "iUserCode="+userId, "");
		while (rs.next()) {
			userobj.setUserCode(rs.getInt("iuserCode"));
			userobj.setUserGroupCode(rs.getInt("iUserGroupCode"));
			userobj.setUserName(rs.getString("vUserName"));
			userobj.setLoginName(rs.getString("vLoginName"));
			userobj.setUserType(rs.getString("vUserTypeCode"));
			userobj.setLocationCode(rs.getString("vLocationCode"));
			userobj.setLocationName(rs.getString("vLocationName"));
			userobj.setCountyCode(rs.getString("vCountryCode"));
			userDbDtl.add(userobj);
			userobj=null;
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}
public int isValidUserThroughUserName(String userName, String password) {

	int userCode = -1;
	try {
		password = encryption.encrypt(password);
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con,
				"usercode,StatusIndiForUserMst", "view_userDetail",
				"LoginId = '" + userName + "' and loginPass = '"
						+ password + "'", "");
		while (rs.next()) {
			if (rs.getString("StatusIndiForUserMst").equals("D")) {
				userCode = -3;
			} else {
				userCode = rs.getInt("usercode");// userCode
			}

		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		userCode = -2; // if database connection can not get
		e.printStackTrace();
	}
	return userCode;
}
public Vector<DTOUserMst> getUserDetailByLogiId(String loginUserName) {
	
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		String FieldNames = "UserCode,UserGroupCode,UserName,LoginName,LoginPass,UserTypeCode,ModifyOnForUserMst,StatusIndiForUserMst,UserGroupName,Remark,userLocationName,userMstLocation,CountryCode";
		ResultSet rs = dataTable.getDataSet(con, FieldNames,
				"view_userDetail", "Loginid='"+loginUserName+"'  ", "");
		while(rs.next()){
			DTOUserMst userobj = new DTOUserMst();
			userobj.setUserCode(rs.getInt("userCode"));
			userobj.setUserGroupCode(rs.getInt("UserGroupCode"));
			userobj.setUserName(rs.getString("UserName"));
			userobj.setLoginName(rs.getString("LoginName"));
			userobj.setLoginPass(encryption.decrypt(rs
					.getString("LoginPass")));
			userobj.setUserType(rs.getString("UserTypeCode"));
			userobj.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
			userobj.setStatusIndi(rs.getString("StatusIndiForUserMst")
					.charAt(0));
			userobj.setUserGroupName(rs.getString("UserGroupName"));
			userobj.setRemark(rs.getString("Remark"));
			userobj.setLocationCode(rs.getString("userMstLocation"));
			userobj.setUserLocationName(rs.getString("userLocationName"));
			userobj.setCountyCode(rs.getString("CountryCode"));
			userDbDtl.add(userobj);
			userobj=null;
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}

public Vector<DTOUserMst> getUserLoginDetail(String searchMode,String userTypeCode,String fromSubmissionDate,String toSubmissionDate) {
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	DataTable dataTable = new DataTable();
	ResultSet rs;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		if(searchMode.equalsIgnoreCase("All_User") && userTypeCode.equalsIgnoreCase("-1")){
			rs = dataTable.getDataSet(con, "*",	"view_LoginUserDetail","userTypeName<>'SU'","");
		}
		else if(searchMode.equalsIgnoreCase("Active") && userTypeCode.equalsIgnoreCase("-1")){
			rs = dataTable.getDataSet(con, "*",	"view_LoginUserDetail","userTypeName<>'SU' and statusindiforusermst<>'D' ","");
		}
		else if(searchMode.equalsIgnoreCase("Deactive") && userTypeCode.equalsIgnoreCase("-1")){
			rs = dataTable.getDataSet(con, "*",	"view_LoginUserDetail","userTypeName<>'SU' and statusindiforusermst='D' ","");
		}
		else if(searchMode.equalsIgnoreCase("Active_Session") && userTypeCode.equalsIgnoreCase("-1")){
			rs = dataTable.getDataSet(con, "*","view_UserLoginDetailForActiveSession","dlogindatetime>CONVERT(VARCHAR(10), getdate(), 111) and dlogindatetime<CONVERT(VARCHAR(10), getdate()+1, 111) and userTypeName<>'SU' ","dloginon desc");	
		}
		else if(searchMode.equalsIgnoreCase("BlockUser") && userTypeCode.equalsIgnoreCase("-1")){
			rs = dataTable.getDataSet(con, "*","view_UserLoginFailureDetails","userTypeName<>'SU' and BlockFlag='B'","");
		}
		else if(searchMode.equalsIgnoreCase("Login") && userTypeCode.equalsIgnoreCase("-1")){
			if(!fromSubmissionDate.equals("") && !toSubmissionDate.equals("")){
				//rs = dataTable.getDataSet(con, "*","view_UserLoginDetail","userTypeName<>'SU' and dLoginOut is not null and cast(dLoginOut as date) >='"+fromSubmissionDate+"' and "+" cast(dLoginOut as date) <= '"+toSubmissionDate+"'","dloginon desc");
				//rs = dataTable.getDataSet(con, "*","view_UserLoginDetail","userTypeName<>'SU' and dLoginOut is not null and cast(dLoginOut as date) >='"+fromSubmissionDate+"' and "+" cast(dLoginOut as date) <= '"+toSubmissionDate+"'","");
				rs = dataTable.getDataSet(con, "*","view_UserLoginDetail","userTypeName<>'SU' and cast(dLoginOn as date) >='"+fromSubmissionDate+"' and "+" cast(dLoginOn as date) <= '"+toSubmissionDate+"'","");
			}else{
				//rs = dataTable.getDataSet(con, "*","view_UserLoginDetail","userTypeName<>'SU' and dLoginOut is not null","dloginon desc");
				//rs = dataTable.getDataSet(con, "*","view_UserLoginDetail","userTypeName<>'SU' and dLoginOut is not null","");
				rs = dataTable.getDataSet(con, "*","view_UserLoginDetail","userTypeName<>'SU'","");
			}
		}
		else if(searchMode.equalsIgnoreCase("All_User")){
			rs = dataTable.getDataSet(con, "*",	"view_LoginUserDetail","userTypeName<>'SU' and userTypeCode='"+userTypeCode+"' ","");
		}
		else if(searchMode.equalsIgnoreCase("Active")){
			rs = dataTable.getDataSet(con, "*",	"view_LoginUserDetail","userTypeName<>'SU' and statusindiforusermst<>'D' and userTypeCode='"+userTypeCode+"' ","");
		}
		else if(searchMode.equalsIgnoreCase("Active_Session")){
			rs = dataTable.getDataSet(con, "*","view_UserLoginDetailForActiveSession","dlogindatetime>CONVERT(VARCHAR(10), getdate(), 111) and dlogindatetime<CONVERT(VARCHAR(10), getdate()+1, 111) and userTypeName<>'SU' and userTypeCode='"+userTypeCode+"' ","");
		}
		else if(searchMode.equalsIgnoreCase("BlockUser")){
			rs = dataTable.getDataSet(con, "*","view_UserLoginFailureDetails","userTypeName<>'SU' and BlockFlag='B' and userTypeCode='"+userTypeCode+"'","");
		}
		else if(searchMode.equalsIgnoreCase("Deactive")){
			rs = dataTable.getDataSet(con, "*",	"view_LoginUserDetail","userTypeName<>'SU' and statusindiforusermst='D' and userTypeCode='"+userTypeCode+"'  ","");
		}
		else if(searchMode.equalsIgnoreCase("Login") && !fromSubmissionDate.equals("") && !toSubmissionDate.equals("")){
			//rs = dataTable.getDataSet(con, "*","view_UserLoginDetail","userTypeName<>'SU' and dLoginOut is not null and userTypeCode='"+userTypeCode+"' and dLoginOut cast(dLoginOut as date) >='"+fromSubmissionDate+"' and '"+" cast(dLoginOut as date) <= '"+toSubmissionDate+"'","dloginon desc");
			 //rs = dataTable.getDataSet(con, "*","view_UserLoginDetail","userTypeName<>'SU' and dLoginOut is not null and userTypeCode='"+userTypeCode+"' and cast(dLoginOut as date) >='"+fromSubmissionDate+"' and cast(dLoginOut as date) <= '"+toSubmissionDate+"'","dloginon desc");
			 //rs = dataTable.getDataSet(con, "*","view_UserLoginDetail","userTypeName<>'SU' and dLoginOut is not null and userTypeCode='"+userTypeCode+"' and cast(dLoginOut as date) >='"+fromSubmissionDate+"' and cast(dLoginOut as date) <= '"+toSubmissionDate+"'","");
			rs = dataTable.getDataSet(con, "*","view_UserLoginDetail","userTypeName<>'SU' and userTypeCode='"+userTypeCode+"' and cast(dLoginOn as date) >='"+fromSubmissionDate+"' and cast(dLoginOn as date) <= '"+toSubmissionDate+"'","");
		}
		
		else{
			//rs = dataTable.getDataSet(con, "*","view_UserLoginDetail","userTypeName<>'SU' and dLoginOut is not null and userTypeCode='"+userTypeCode+"' ","dloginon desc");
			//rs = dataTable.getDataSet(con, "*","view_UserLoginDetail","userTypeName<>'SU' and dLoginOut is not null and userTypeCode='"+userTypeCode+"' ","");
			rs = dataTable.getDataSet(con, "*","view_UserLoginDetail","userTypeName<>'SU' and userTypeCode='"+userTypeCode+"' ","dLoginOn DESC");
		}				
		while (rs.next()) {
			DTOUserMst dto = new DTOUserMst();
			
	/*	if(searchMode.equalsIgnoreCase("BlockUser")){
				dto.setUserCode(rs.getInt("userCode"));
				dto.setUserGroupCode(rs.getInt("userGroupCode"));
				dto.setUserName(rs.getString("UserName"));
				dto.setUserGroupName(rs.getString("UserGroupName"));
				dto.setUserType(rs.getString("userTypeName"));
			if(dto.getUserType().equalsIgnoreCase("WA")){
				dto.setUserType(("Admin User"));
			}
			if(dto.getUserType().equalsIgnoreCase("WU")){
				dto.setUserType(("Project User"));
			}
				dto.setUserTypeCode(rs.getString("usertypecode"));
				dto.setStatusIndi(rs.getString("statusIndi").charAt(0));
				dto.setModifyOn(rs.getTimestamp("ModifyOn"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
					
				
			}else{*/
			
			dto.setUserCode(rs.getInt("userCode"));
			dto.setUserGroupCode(rs.getInt("userGroupCode"));
			dto.setUserName(rs.getString("UserName"));
			dto.setLoginName(rs.getString("LoginName"));
			dto.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
			dto.setUserType(rs.getString("userTypeName"));
		//if(!searchMode.equalsIgnoreCase("Active_Session")){
			dto.setModifyByName(rs.getString("ModifyByName"));
		//}
		if(dto.getUserType().equalsIgnoreCase("WA")){
			dto.setUserType(("Admin User"));
		}
		if(dto.getUserType().equalsIgnoreCase("WU")){
			dto.setUserType(("Project User"));
		}
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyByForUserMst"));
			dto.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
		if(countryCode.equalsIgnoreCase("IND")){
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
		}
		else{
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
			dto.setESTDateTime(time.get(1));
		}
			dto.setStatusIndi(rs.getString("statusIndiForUserMst").charAt(0));
			dto.setUserGroupName(rs.getString("UserGroupName"));
			dto.setUserTypeCode(rs.getString("usertypecode"));
			
		/*if(searchMode.equalsIgnoreCase("Active_Session")){
			dto.setLoginIP(rs.getString("vLoginIP"));
			dto.setLoginOn(rs.getTimestamp("dLoginOn"));
		 if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getLoginOn(),locationName,countryCode);
				dto.setISTLogin(time.get(0));
		 }
		else{					
				time = docMgmtImpl.TimeZoneConversion(dto.getLoginOn(),locationName,countryCode);
				dto.setISTLogin(time.get(0));
				dto.setESTLogin(time.get(1));
			}
			dto.setLoginOut(null);
		 if(countryCode.equalsIgnoreCase("IND")){
				dto.setISTLogOut("-");
		 }
		 else{
				dto.setISTLogOut("-");
				dto.setESTLogOut("-");
		 }
		}*/
			//if(searchMode.equalsIgnoreCase("Login")){
				dto.setLoginIP(rs.getString("vLoginIP"));
				dto.setLoginOn(rs.getTimestamp("dLoginOn"));
				//dto.setTotalTime(rs.getString("TotalTime"));
				if(dto.getLoginOn()==null){
					dto.setISTLogin("-");
					dto.setESTLogin("-");
					}
				else{
					if(countryCode.equalsIgnoreCase("IND")){
						time = docMgmtImpl.TimeZoneConversion(dto.getLoginOn(),locationName,countryCode);
						dto.setISTLogin(time.get(0));
					}
					else{					
						time = docMgmtImpl.TimeZoneConversion(dto.getLoginOn(),locationName,countryCode);
						dto.setISTLogin(time.get(0));
						dto.setESTLogin(time.get(1));
					}
				}
				dto.setLoginOut(rs.getTimestamp("dLoginOut"));
			if(dto.getLoginOut()==null){
					dto.setISTLogOut("-");
					dto.setESTLogOut("-");
					
			}
			else{
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dto.getLoginOut(),locationName,countryCode);
					dto.setISTLogOut(time.get(0));	
				}
				else{					
					time = docMgmtImpl.TimeZoneConversion(dto.getLoginOut(),locationName,countryCode);
					dto.setISTLogOut(time.get(0));
					dto.setESTLogOut(time.get(1));
				}
			}
				
				
			//}
			//if(searchMode.equalsIgnoreCase("Active_Session")){
			/*Object myObject = rs.getTimestamp("dLoginOut");
			if( myObject == null )
			{
				dto.setLoginOn(rs.getTimestamp("dLoginOn"));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dto.getLoginOn(),locationName,countryCode);
					dto.setISTLogin(time.get(0));
				}
				else{					
					time = docMgmtImpl.TimeZoneConversion(dto.getLoginOn(),locationName,countryCode);
					dto.setISTLogin(time.get(0));
					dto.setESTLogin(time.get(1));
				}
				dto.setLoginOut(null);
				if(countryCode.equalsIgnoreCase("IND")){
					dto.setISTLogOut("-");
				}
				else{
					dto.setISTLogOut("-");
					dto.setESTLogOut("-");
				}
			}*/
		//}
			//}
			userDbDtl.add(dto);
		}
		rs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return userDbDtl;
}

public int isValidUserForVerification(String userName) {

	int userCode = -1;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs =null;
		boolean adFlag=false;
		boolean userFlag=false;
		//if(userCode == -1){
		
		//Checks if user is normal user
			rs = dataTable.getDataSet(con,
					"usercode,StatusIndiForUserMst,isaduser", "view_userDetail",
					//"loginName = '" + userName + "' ", "");
					"loginName like '%" + userName + "' ", "");
			while (rs.next()) {
				if(rs.getString("IsAdUser")!=null){
					char isAdUser=rs.getString("isaduser").charAt(0);
					if(isAdUser=='Y'){
						/*rs = dataTable.getDataSet(con,
								"usercode,StatusIndiForUserMst,isaduser", "view_userDetail",
								//"loginName = '" + userName + "' ", "");
								"adusername like '%" + userName + "' ", "");*/
						//while (rs.next()) {
							adFlag=true;
							if (rs.getString("StatusIndiForUserMst").equals("D")) {
								userCode = -3;
							} else {
								userCode = rs.getInt("usercode");// userCode
								rs.close();
								con.close();
								return userCode;
								}
							//}
					}else{
						//password = encryption.encrypt(password);
					//Connection con = ConnectionManager.ds.getConnection();
					/*ResultSet */
						/*rs = dataTable.getDataSet(con,
							"usercode,StatusIndiForUserMst", "view_userDetail",
							"loginName = '" + userName + "' and loginPass = '"
									+ password + "'", "");*/
					rs = dataTable.getDataSet(con,
							"usercode,StatusIndiForUserMst", "view_userDetail",
							"loginName = '" + userName + "' ", "");
					while (rs.next()) {
						if (rs.getString("StatusIndiForUserMst").equals("D")) {
							userCode = -3;
						} else {
							userCode = rs.getInt("usercode");// userCode
							rs.close();
							con.close();
							return userCode;
							}
						}
					}
				}else{
					userFlag=true;
					//password = encryption.encrypt(password);
					//Connection con = ConnectionManager.ds.getConnection();
					/*ResultSet */
					/*rs = dataTable.getDataSet(con,
							"usercode,StatusIndiForUserMst", "view_userDetail",
							"loginName = '" + userName + "' and loginPass = '"
									+ password + "'", "");*/
					rs = dataTable.getDataSet(con,
							"usercode,StatusIndiForUserMst", "view_userDetail",
							"loginName = '" + userName + "' ", "");
					while (rs.next()) {
						if (rs.getString("StatusIndiForUserMst").equals("D")) {
							userCode = -3;
						} else {
							userCode = rs.getInt("usercode");// userCode
							rs.close();
							con.close();
							return userCode;
							}
						}
				}
				if(userFlag==true){
					rs.close();
					con.close();
					return userCode;
				}
				if(adFlag==false){
					userCode= -4;
					rs.close();
					con.close();
					return userCode;
					}
				
				/*
				if (rs.getString("StatusIndiForUserMst").equals("D")) {
					userCode = -3;
					rs.close();
					con.close();
					return userCode;
				} else {
					if(rs.getString("IsAdUser")!=null){
					char isAdUser=rs.getString("isaduser").charAt(0);
					if(isAdUser=='Y'){
					userCode = rs.getInt("usercode");// userCode
					password = encryption.encrypt(password);
					rs = dataTable.getDataSet(con,
							"usercode,StatusIndiForUserMst", "view_userDetail",
							"loginName like '%" + userName + "' and loginPass = '"
									+ password + "'", "");
					while (rs.next()) {
						adFlag=true;
						if (rs.getString("StatusIndiForUserMst").equals("D")) {
							userCode = -3;
						} else {
							userCode = rs.getInt("usercode");// userCode
							rs.close();
							con.close();
							return userCode;
							}
						}
					
					}
					else{
						userCode = -1;	
						}
					if(adFlag==false){
						userCode= -4;
						rs.close();
						con.close();
						return userCode;
							}
						}
					}
				*/}
			//}
		
		//Checks if AD User
			rs = dataTable.getDataSet(con,
					"usercode,StatusIndiForUserMst,isaduser", "view_userDetail",
					//"loginName = '" + userName + "' ", "");
					"adusername like '%" + userName + "' ", "");
			while (rs.next()) {
				if (rs.getString("StatusIndiForUserMst").equals("D")) {
					userCode = -3;
					rs.close();
					con.close();
					return userCode;
				} else {
					if(rs.getString("IsAdUser")!=null){
					char isAdUser=rs.getString("isaduser").charAt(0);
					if(isAdUser=='Y'){
					userCode = rs.getInt("usercode");// userCode
					//password = encryption.encrypt(password);
					/*rs = dataTable.getDataSet(con,
							"usercode,StatusIndiForUserMst,IsVerified", "view_userDetail",
							"adusername like '%" + userName + "' and loginPass = '"
									+ password + "'", "");*/
					rs = dataTable.getDataSet(con,
							"usercode,StatusIndiForUserMst,IsVerified", "view_userDetail",
							"adusername like '%" + userName + "' ", "");
					while (rs.next()) {
						adFlag=true;
						if (rs.getString("StatusIndiForUserMst").equals("D")) {
							userCode = -3;
						} else {
							/*char isVerified=0;
							if(rs.getString("IsVerified")!=null){
								isVerified = rs.getString("IsAdUser").charAt(0);
							}
							if(isVerified==0)
							{
								userCode = -6;
								return userCode;
							}*/
							//else{
								userCode = rs.getInt("usercode");// userCode
								rs.close();
								con.close();
								return userCode;
							//}
							}
						}
					
					}
					else{
						userCode = -1;	
						}
					if(adFlag==false){
						userCode= -4;
						rs.close();
						con.close();
						return userCode;
							}
						}
					}
				}
			
			
			/*int userCode = -1;
			try {
				password = encryption.encrypt(password);
				Connection con = ConnectionManager.ds.getConnection();
				ResultSet rs = dataTable.getDataSet(con,
						"usercode,StatusIndiForUserMst", "view_userDetail",
						"loginName = '" + userName + "' and loginPass = '"
								+ password + "'", "");
				while (rs.next()) {
					if (rs.getString("StatusIndiForUserMst").equals("D")) {
						userCode = -3;
					} else {
						userCode = rs.getInt("usercode");// userCode
					}

				}
				rs.close();
				con.close();
			} catch (SQLException e) {
				userCode = -2; // if database connection can not get
				e.printStackTrace();
			}
			return userCode;*/
		
		
		
		rs.close();
		con.close();
	} catch (SQLException e) {
		userCode = -2; // if database connection can not get
		e.printStackTrace();
	}
	return userCode;
}

public int isValidUserForVerification(String userName, String password) {

	int userCode = -1;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs =null;
		boolean adFlag=false;
		boolean userFlag=false;
		//if(userCode == -1){
		
		//Checks if user is normal user
			rs = dataTable.getDataSet(con,
					"usercode,StatusIndiForUserMst,isaduser", "view_userDetail",
					//"loginName = '" + userName + "' ", "");
					"loginName like '%" + userName + "' ", "");
			while (rs.next()) {
				if(rs.getString("IsAdUser")!=null){
					char isAdUser=rs.getString("isaduser").charAt(0);
					if(isAdUser=='Y'){
						rs = dataTable.getDataSet(con,
								"usercode,StatusIndiForUserMst,isaduser", "view_userDetail",
								//"loginName = '" + userName + "' ", "");
								"adusername like '%" + userName + "' ", "");
						while (rs.next()) {
							adFlag=true;
							if (rs.getString("StatusIndiForUserMst").equals("D")) {
								userCode = -3;
							} else {
								userCode = rs.getInt("usercode");// userCode
								rs.close();
								con.close();
								return userCode;
								}
							}
					}else{password = encryption.encrypt(password);
					//Connection con = ConnectionManager.ds.getConnection();
					/*ResultSet */rs = dataTable.getDataSet(con,
							"usercode,StatusIndiForUserMst", "view_userDetail",
							"loginName = '" + userName + "' and loginPass = '"
									+ password + "'", "");
					while (rs.next()) {
						if (rs.getString("StatusIndiForUserMst").equals("D")) {
							userCode = -3;
						} else {
							userCode = rs.getInt("usercode");// userCode
							rs.close();
							con.close();
							return userCode;
							}
						}
					}
				}else{
					userFlag=true;
					password = encryption.encrypt(password);
					//Connection con = ConnectionManager.ds.getConnection();
					/*ResultSet */rs = dataTable.getDataSet(con,
							"usercode,StatusIndiForUserMst", "view_userDetail",
							"loginName = '" + userName + "' and loginPass = '"
									+ password + "'", "");
					while (rs.next()) {
						if (rs.getString("StatusIndiForUserMst").equals("D")) {
							userCode = -3;
						} else {
							userCode = rs.getInt("usercode");// userCode
							rs.close();
							con.close();
							return userCode;
							}
						}
				}
				if(userFlag==true){
					rs.close();
					con.close();
					return userCode;
				}
				if(adFlag==false){
					userCode= -4;
					rs.close();
					con.close();
					return userCode;
					}
				
				/*
				if (rs.getString("StatusIndiForUserMst").equals("D")) {
					userCode = -3;
					rs.close();
					con.close();
					return userCode;
				} else {
					if(rs.getString("IsAdUser")!=null){
					char isAdUser=rs.getString("isaduser").charAt(0);
					if(isAdUser=='Y'){
					userCode = rs.getInt("usercode");// userCode
					password = encryption.encrypt(password);
					rs = dataTable.getDataSet(con,
							"usercode,StatusIndiForUserMst", "view_userDetail",
							"loginName like '%" + userName + "' and loginPass = '"
									+ password + "'", "");
					while (rs.next()) {
						adFlag=true;
						if (rs.getString("StatusIndiForUserMst").equals("D")) {
							userCode = -3;
						} else {
							userCode = rs.getInt("usercode");// userCode
							rs.close();
							con.close();
							return userCode;
							}
						}
					
					}
					else{
						userCode = -1;	
						}
					if(adFlag==false){
						userCode= -4;
						rs.close();
						con.close();
						return userCode;
							}
						}
					}
				*/}
			//}
		
		//Checks if AD User
			rs = dataTable.getDataSet(con,
					"usercode,StatusIndiForUserMst,isaduser", "view_userDetail",
					//"loginName = '" + userName + "' ", "");
					"adusername like '%" + userName + "' ", "");
			while (rs.next()) {
				if (rs.getString("StatusIndiForUserMst").equals("D")) {
					userCode = -3;
					rs.close();
					con.close();
					return userCode;
				} else {
					if(rs.getString("IsAdUser")!=null){
					char isAdUser=rs.getString("isaduser").charAt(0);
					if(isAdUser=='Y'){
					userCode = rs.getInt("usercode");// userCode
					password = encryption.encrypt(password);
					rs = dataTable.getDataSet(con,
							"usercode,StatusIndiForUserMst,IsVerified", "view_userDetail",
							"adusername like '%" + userName + "' and loginPass = '"
									+ password + "'", "");
					while (rs.next()) {
						adFlag=true;
						if (rs.getString("StatusIndiForUserMst").equals("D")) {
							userCode = -3;
						} else {
							char isVerified=0;
							if(rs.getString("IsVerified")!=null){
								isVerified = rs.getString("IsVerified").charAt(0);
							}
							if(isVerified==0)
							{
								userCode = -6;
								return userCode;
							}
							//else{
								userCode = rs.getInt("usercode");// userCode
								rs.close();
								con.close();
								return userCode;
							//}
							}
						}
					
					}
					else{
						userCode = -1;	
						}
					if(adFlag==false){
						userCode= -4;
						rs.close();
						con.close();
						return userCode;
							}
						}
					}
				}
			
			
			
		
		
		
		
		rs.close();
		con.close();
	} catch (SQLException e) {
		userCode = -2; // if database connection can not get
		e.printStackTrace();
	}
	return userCode;
}
/*public Vector<DTOUserMst> chekIsAdUser(String loginName) {
	
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	ResultSet rs;
	try {
		Connection con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "*", "view_userDetail",
				"(LoginName = '"+loginName+"' OR AdUserName like '%\\"+loginName+"') and StatusIndiForUserMst <> 'D' ", "userCode");

		while (rs.next()) {
			DTOUserMst dto = new DTOUserMst();
			dto.setUserCode(rs.getInt("userCode"));
			dto.setUserGroupCode(rs.getInt("userGroupCode"));
			dto.setUserName(rs.getString("UserName"));
			dto.setLoginId(rs.getString("LoginId"));
			dto.setLoginName(rs.getString("LoginName"));
			dto.setAdUserName(rs.getString("AdUserName"));
		if(rs.getString("IsAdUser")!=null){
			dto.setIsAdUser(rs.getString("IsAdUser").charAt(0));
		}
		if(rs.getString("IsVerified")!=null){
			dto.setIsVerified(rs.getString("IsVerified").charAt(0));
		}
			dto.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
			dto.setUserType(rs.getString("userTypeName"));
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyByForUserMst"));
			dto.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
			dto.setStatusIndi(rs.getString("statusIndiForUserMst").charAt(0));
			dto.setUserGroupName(rs.getString("UserGroupName"));
			dto.setUserTypeCode(rs.getString("usertypecode"));
			dto.setLocationName(rs.getString("userLocationName"));
			dto.setRoleName(rs.getString("RoleName"));
			dto.setDeptName(rs.getString("DeptName"));
			userDbDtl.add(dto);
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}*/

public Vector<DTOUserMst> chekIsAdUser(String loginName) {
	
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	ResultSet rs;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs = con.prepareCall("{call Proc_UserDetail(?,?)}");
		cs.setString(1, loginName);
		cs.setInt(2, 1);
		rs = cs.executeQuery();

		while (rs.next()) {
			DTOUserMst dto = new DTOUserMst();
			dto.setUserCode(rs.getInt("userCode"));
			dto.setUserGroupCode(rs.getInt("userGroupCode"));
			dto.setUserName(rs.getString("UserName"));
			dto.setLoginId(rs.getString("LoginId"));
			dto.setLoginName(rs.getString("LoginName"));
			dto.setAdUserName(rs.getString("AdUserName"));
		if(rs.getString("IsAdUser")!=null){
			dto.setIsAdUser(rs.getString("IsAdUser").charAt(0));
		}
		if(rs.getString("IsVerified")!=null){
			dto.setIsVerified(rs.getString("IsVerified").charAt(0));
		}
			dto.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
			dto.setUserType(rs.getString("userTypeName"));
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyByForUserMst"));
			dto.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
			dto.setStatusIndi(rs.getString("statusIndiForUserMst").charAt(0));
			dto.setUserGroupName(rs.getString("UserGroupName"));
			dto.setUserTypeCode(rs.getString("usertypecode"));
			dto.setLocationName(rs.getString("userLocationName"));
			dto.setRoleName(rs.getString("RoleName"));
			dto.setDeptName(rs.getString("DeptName"));
			userDbDtl.add(dto);
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}
/*public Vector<DTOUserMst> getUserProfileByUserName(String loginUserName) {
	
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con, "distinct UserGroupCode,UserGroupName", "view_userDetail",
				"LoginName='"+loginUserName+"' and StatusIndiForUserMst <> 'D' ", "");
		while (rs.next()) {
			DTOUserMst dto = new DTOUserMst();
			dto.setUserGroupCode(rs.getInt("UserGroupCode"));
			dto.setUserGroupName(rs.getString("UserGroupName"));
			userDbDtl.add(dto);
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}*/

public Vector<DTOUserMst> getUserProfileByUserName(String loginUserName) {
	
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs = con.prepareCall("{call Proc_UserDetail(?,?)}");
		cs.setString(1, loginUserName);
		cs.setInt(2, 2);
		ResultSet rs = cs.executeQuery();
		/*ResultSet rs = dataTable.getDataSet(con, "distinct UserGroupCode,UserGroupName", "view_userDetail",
				"LoginName='"+loginUserName+"' and StatusIndiForUserMst <> 'D' ", "");*/
		while (rs.next()) {
			DTOUserMst dto = new DTOUserMst();
			dto.setUserGroupCode(rs.getInt("UserGroupCode"));
			dto.setUserGroupName(rs.getString("UserGroupName"));
			userDbDtl.add(dto);
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}



public Vector<DTOUserMst> getUserProfileByAdUser(String loginUserName) {
	
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs = con.prepareCall("{call Proc_UserDetail(?,?)}");
		cs.setString(1, loginUserName);
		cs.setInt(2, 3);
		ResultSet rs = cs.executeQuery();
		/*ResultSet rs = dataTable.getDataSet(con, "distinct UserGroupCode,UserGroupName", "view_userDetail",
				"AdUserName like'%\\"+loginUserName+"' and StatusIndiForUserMst <> 'D' ", "");*/
		while (rs.next()) {
			DTOUserMst dto = new DTOUserMst();
			dto.setUserGroupCode(rs.getInt("UserGroupCode"));
			dto.setUserGroupName(rs.getString("UserGroupName"));
			userDbDtl.add(dto);
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}


/*public Vector<DTOUserMst> getUserProfileByAdUser(String loginUserName) {
	
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con, "distinct UserGroupCode,UserGroupName", "view_userDetail",
				"AdUserName like'%\\"+loginUserName+"' and StatusIndiForUserMst <> 'D' ", "");
		while (rs.next()) {
			DTOUserMst dto = new DTOUserMst();
			dto.setUserGroupCode(rs.getInt("UserGroupCode"));
			dto.setUserGroupName(rs.getString("UserGroupName"));
			userDbDtl.add(dto);
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}*/
public Vector<DTOUserMst> getUserDetailByDistinct() {
	
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();	
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con, "DISTINCT UserName, LoginName, LoginId,AdUserName ", "view_userDetail",
				"usertypename<>'SU' ", "");
		while (rs.next()) {
			DTOUserMst dto = new DTOUserMst();
			dto.setUserName(rs.getString("UserName"));
			dto.setLoginId(rs.getString("LoginId"));
			dto.setLoginName(rs.getString("LoginName"));
			dto.setAdUserName(rs.getString("AdUserName"));
			userDbDtl.add(dto);
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}

public Vector<DTOUserMst> getUserDetailByName(String loginName) {
	
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con, "*", "view_userDetail","LoginName='"+loginName+"'", "");
		while (rs.next()) {
			DTOUserMst dto = new DTOUserMst();
			dto.setUserCode(rs.getInt("userCode"));
			dto.setUserGroupCode(rs.getInt("userGroupCode"));
			dto.setUserName(rs.getString("UserName"));
			dto.setLoginId(rs.getString("LoginId"));
			dto.setLoginName(rs.getString("LoginName"));
			dto.setAdUserName(rs.getString("AdUserName"));
			if(rs.getString("IsAdUser")!=null){
				dto.setIsAdUser(rs.getString("IsAdUser").charAt(0));
			}
			if(rs.getString("IsVerified")!=null){
				dto.setIsVerified(rs.getString("IsVerified").charAt(0));
			}
			dto.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
			dto.setUserType(rs.getString("userTypeName"));
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyByForUserMst"));
			dto.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
			dto.setStatusIndi(rs.getString("statusIndiForUserMst")
					.charAt(0));
			dto.setUserGroupName(rs.getString("UserGroupName"));
			dto.setUserTypeCode(rs.getString("usertypecode"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{					
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			dto.setLocationName(rs.getString("userLocationName"));
			dto.setRoleName(rs.getString("RoleName"));
			dto.setDeptName(rs.getString("DeptName"));
			userDbDtl.add(dto);
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}

public DTOUserMst getUserNameByCode(String loginName,int uGroupCode){
	int userCode = -1;
	DTOUserMst userobj = new DTOUserMst();
	try {
				Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con,"*", "usermst",
				"vloginName = '" + loginName + "' and iusergroupcode="+uGroupCode, "");
		if (rs.next()) {
			userobj.setUserCode(rs.getInt("iuserCode"));
			userobj.setUserGroupCode(rs.getInt("iUserGroupCode"));
			userobj.setUserName(rs.getString("vUserName"));
			userobj.setLoginName(rs.getString("vLoginName"));
			userobj.setLoginPass(encryption.decrypt(rs.getString("vLoginPass")));
			userobj.setUserType(rs.getString("vUserTypeCode"));
			userobj.setModifyOn(rs.getTimestamp("dModifyOn"));
			userobj.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		userCode = -2; // if database connection can not get
		e.printStackTrace();
	}
	return userobj;
}
public Vector<DTOUserMst> getUserDetailByUserCode(String usrName,int uCode) {
	
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOUserMst> userMstDetailHistory = new Vector<DTOUserMst>();
	ResultSet rs = null;
	Connection con = null;
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "vLoginName= '"+ usrName +"' and iUserCode="+uCode;
		rs =  dataTable.getDataSet(con, "*","usermst", Where,"");

		while (rs.next()) {
			DTOUserMst dto = new DTOUserMst();
			dto.setUserCode(rs.getInt("iUserCode"));
			dto.setUserName(rs.getString("vUserName")); // UserName
			dto.setUserGroupCode(rs.getInt("iUserGroupCode")); // UserGroupName
			dto.setLoginName(rs.getString("vLoginName")); // LoginName
			dto.setUserTypeCode(rs.getString("vUserTypeCode")); 
			//dto.setLocationCode(rs.getString("vLocationCode")); 
			userMstDetailHistory.addElement(dto);
			dto = null;
		}

		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userMstDetailHistory;
}


public Vector<DTOUserMst> getAllUserDetail() {
	
	Vector<DTOUserMst> userDbDtl = new Vector<DTOUserMst>();
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con, "*", "view_userDetail",
				"", "");
		while (rs.next()) {
			DTOUserMst dto = new DTOUserMst();
			dto.setUserCode(rs.getInt("userCode"));
			dto.setUserGroupCode(rs.getInt("userGroupCode"));
			dto.setUserName(rs.getString("UserName"));
			dto.setLoginId(rs.getString("LoginId"));
			dto.setLoginName(rs.getString("LoginName"));
			dto.setAdUserName(rs.getString("AdUserName"));
			if(rs.getString("IsAdUser")!=null){
				dto.setIsAdUser(rs.getString("IsAdUser").charAt(0));
			}
			if(rs.getString("IsVerified")!=null){
				dto.setIsVerified(rs.getString("IsVerified").charAt(0));
			}
			dto.setLoginPass(encryption.decrypt(rs.getString("LoginPass")));
			dto.setUserType(rs.getString("userTypeName"));
			dto.setRemark(rs.getString("Remark"));
			dto.setModifyBy(rs.getInt("ModifyByForUserMst"));
			dto.setModifyOn(rs.getTimestamp("ModifyOnForUserMst"));
			dto.setStatusIndi(rs.getString("statusIndiForUserMst")
					.charAt(0));
			dto.setUserGroupName(rs.getString("UserGroupName"));
			dto.setUserTypeCode(rs.getString("usertypecode"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{					
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			dto.setLocationName(rs.getString("userLocationName"));
			dto.setRoleName(rs.getString("RoleName"));
			userDbDtl.add(dto);
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return userDbDtl;
}

public void updateRole(DTOUserMst dto) {
	Connection con = null;
	int rs1=0;
	try {
		con = ConnectionManager.ds.getConnection();
		DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
		/*String query = "UPDATE Usermst SET " + "vRoleCode='"+roleCode +"' WHERE iuserCode="+ userId;
		DTOUserMst dto = new DTOUserMst();
		dto = docMgmtImpl.getUserByCode(userId);
		rs1 =dataTable.getDataSet1(con,query);*/
		
		CallableStatement proc = con.prepareCall("{ Call Proc_UpdateUserMst(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		proc.setInt(1, dto.getUserCode());
		proc.setInt(2, dto.getUserGroupCode());
		proc.setString(3, dto.getAdUserName());
		char isAdUser = dto.getIsAdUser();
		proc.setString(4, Character.toString(isAdUser));
		proc.setString(5, dto.getUserName());
		proc.setString(6, dto.getLoginId());
		proc.setString(7, dto.getLoginName());
		proc.setString(8, encryption.encrypt(dto.getLoginPass()));
		proc.setString(9, dto.getEMail());
		proc.setString(10, dto.getUserType());
		proc.setString(11, dto.getRemark());
		proc.setInt(12, dto.getModifyBy());
		proc.setString(13, "E");
		proc.setString(13, Character.toString('N'));
		proc.setString(14, dto.getLocationCode());
		proc.setString(15, dto.getRoleCode());
		char isVerified = dto.getIsVerified();
		proc.setString(16, Character.toString(isVerified));
		proc.setString(17, dto.getDeptCode());
		proc.setInt(18, 1);
		proc.executeUpdate();
		proc.close();
								
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
}

}// /main class ended

