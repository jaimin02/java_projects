package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;

public class WorkspaceUserMst {
	DataTable dataTable;

	public WorkspaceUserMst() {
		dataTable = new DataTable();
	}

	public Vector<DTOWorkSpaceUserMst> getAllWorkspaceUserDetail(String wsId) {
		Vector<DTOWorkSpaceUserMst> workspaceuserdtl = new Vector<DTOWorkSpaceUserMst>();
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con,
					"distinct usergroupname,UserCode,usergroupcode",
					"view_workspaceUserDetail", "workspaceid='" + wsId + "'"
							+ " and (statusindi='N' or statusindi='E')", "");
			while (rs.next()) {
				DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
				dto.setUserGroupName(rs.getString("usergroupName"));
				dto.setUserCode(rs.getInt("UserCode"));
				dto.setUserGroupCode(rs.getInt("userGroupCode"));
				workspaceuserdtl.addElement(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return workspaceuserdtl;
	}

	public Vector<DTOWorkSpaceUserMst> getWorkspaceUserDetailList(String wsId) {
		Vector<DTOWorkSpaceUserMst> workspaceuserdtl = new Vector<DTOWorkSpaceUserMst>();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "*", "view_workspaceUserDetail",
					"workspaceid='" + wsId
							+ "' and (statusindi='N' or statusindi='E' or statusindi='W' or statusindi='X') and WsStatusIndi<>'D'", "Modifyon Asc");
			while (rs.next()) {
				DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
				dto.setWorkSpaceId(rs.getString("WorkspaceId"));
				dto.setUserGroupCode(rs.getInt("UserGroupCode"));
				dto.setUserCode(rs.getInt("UserCode"));
				dto.setModifyOn(rs.getTimestamp("ModifyOn"));
				dto.setFromDt(rs.getTimestamp("FromDt"));
				Timestamp fromDate=new Timestamp(dto.getFromDt().getTime());
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(fromDate,locationName,countryCode);
					dto.setFromISTDate(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(fromDate,locationName,countryCode);
					dto.setFromISTDate(time.get(0));
					dto.setFromESTDate(time.get(1));
				}
					dto.setToDt(rs.getTimestamp("ToDt"));
					Timestamp toDate=new Timestamp(dto.getToDt().getTime());
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(toDate,locationName,countryCode);
					dto.setToISTDate(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(toDate,locationName,countryCode);
					dto.setToISTDate(time.get(0));
					dto.setToESTDate(time.get(1));
				}
				dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
				dto.setUsername(rs.getString("Username"));
				dto.setRightsGivenBy(rs.getString("RightsGivenBy"));
				dto.setUserGroupName(rs.getString("userGroupname"));
				dto.setWorkspacedesc(rs.getString("WorkspaceDesc"));
				dto.setAdminFlag(rs.getString("AdminFlag").charAt(0));
				dto.setLastAccessedOn(rs.getTimestamp("LastAccessedOn"));
				dto.setRemark(rs.getString("Remark"));
				dto.setModifyBy(rs.getInt("ModifyBy"));
				dto.setRightsType(rs.getString("RIghtsType"));
				dto.setUserTypeName(rs.getString("UserTypeName"));
				dto.setRoleCode(rs.getString("RoleCode"));
				dto.setRoleName(rs.getString("RoleName"));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
				workspaceuserdtl.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return workspaceuserdtl;
	}

	public void inactiveuserfromproject(DTOWorkSpaceUserMst obj) {
		Connection con = null;
		CallableStatement proc = null;
		try {
			con = ConnectionManager.ds.getConnection();
			proc = con
					.prepareCall("{call Insert_workspaceUserMst(?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setString(1, obj.getWorkSpaceId());
			proc.setInt(2, obj.getUserGroupCode());
			proc.setInt(3, obj.getUserCode());
			proc.setString(4, Character.toString(obj.getAdminFlag()));
			proc.setString(5, obj.getRemark());
			proc.setInt(6, obj.getModifyBy());
			proc.setString(7, "D");
			obj.setToDt(new Date());
			obj.setFromDt(new Date());
			long t = obj.getFromDt().getTime();
			proc.setDate(8, new java.sql.Date(t));
			long t1 = obj.getToDt().getTime();
			proc.setDate(9, new java.sql.Date(t1));
			proc.setString(10,obj.getRightsType());
			proc.setString(11,obj.getRoleCode()); 
			proc.setInt(12, 2); // datamode 2
			proc.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (proc != null) {
					proc.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void insertUpdateUsertoWorkspaceForAttachUser(DTOWorkSpaceUserMst obj,
			int[] userCode) {
		ArrayList<DTOWorkSpaceUserMst> users = new ArrayList<DTOWorkSpaceUserMst>();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		for (int i = 0; i < userCode.length; i++) {
			try {
				DTOWorkSpaceUserMst dto = (DTOWorkSpaceUserMst) obj.clone();
				dto.setUserCode(userCode[i]);
				DTOUserMst getuserData = new DTOUserMst();
				getuserData  = docMgmtImpl.getUserByCode(userCode[i]);
				if(!getuserData.getRoleCode().isEmpty()){
					dto.setRoleCode(getuserData.getRoleCode());
				}
				users.add(dto);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		insertUpdateUsertoWorkspace(users);
	}
	
	public void insertUpdateUsertoWorkspaceForAttachUser(DTOWorkSpaceUserMst obj,int userCode) {
		ArrayList<DTOWorkSpaceUserMst> users = new ArrayList<DTOWorkSpaceUserMst>();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		//for (int i = 0; i < userCode.length; i++) {
			try {
				DTOWorkSpaceUserMst dto = (DTOWorkSpaceUserMst) obj.clone();
				dto.setUserCode(userCode);
				DTOUserMst getuserData = new DTOUserMst();
				getuserData  = docMgmtImpl.getUserByCode(userCode);
				if(!getuserData.getRoleCode().isEmpty()){
					dto.setRoleCode(getuserData.getRoleCode());
				}
				users.add(dto);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		//}
		insertUpdateUsertoWorkspace(users);
	}

	public void insertUpdateUsertoWorkspace(DTOWorkSpaceUserMst obj,
			int[] userCode) {
		ArrayList<DTOWorkSpaceUserMst> users = new ArrayList<DTOWorkSpaceUserMst>();
		for (int i = 0; i < userCode.length; i++) {
			try {
				DTOWorkSpaceUserMst dto = (DTOWorkSpaceUserMst) obj.clone();
				dto.setUserCode(userCode[i]);
				users.add(dto);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		insertUpdateUsertoWorkspace(users);
	}

	public void insertUpdateUsertoWorkspace(ArrayList<DTOWorkSpaceUserMst> users) {
		Connection con = null;
		CallableStatement proc = null;
		try {
			con = ConnectionManager.ds.getConnection();
			proc = con
					.prepareCall("{call Insert_workspaceUserMst(?,?,?,?,?,?,?,?,?,?,?,?)}");
			for (int i = 0; i < users.size(); i++) {
				DTOWorkSpaceUserMst obj = users.get(i);
				proc.setString(1, obj.getWorkSpaceId());
				proc.setInt(2, obj.getUserGroupCode());
				proc.setInt(3, obj.getUserCode());
				proc.setString(4, Character.toString(obj.getAdminFlag()));
				proc.setString(5, obj.getRemark());
				proc.setInt(6, obj.getModifyBy());
				proc.setString(7, "E");
				Timestamp ts1 = new Timestamp(obj.getFromDt().getTime());
				proc.setTimestamp(8, ts1);
				Timestamp ts2 = new Timestamp(obj.getToDt().getTime());
				proc.setTimestamp(9, ts2);
				proc.setString(10,obj.getRightsType());
				proc.setString(11, obj.getRoleCode());
				proc.setInt(12, 1); // datamode 1 for insert or (if available
									// then)update
				proc.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (proc != null) {
					proc.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public void DeleteProjectlevelRights(DTOWorkSpaceUserMst obj) {
		Connection con = null;
		CallableStatement proc = null;
		try {
			con = ConnectionManager.ds.getConnection();
			proc = con
					.prepareCall("{call proc_DeleteProjectSpecRights(?,?,?,?)}");
			proc.setString(1, obj.getWorkSpaceId());
			proc.setInt(2, obj.getUserCode());
			proc.setInt(3, obj.getUserGroupCode());
			proc.setString(4, obj.getRightsType());
			proc.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (proc != null) {
					proc.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public void DeletemodulespecRights(DTOWorkSpaceUserMst obj) {
		Connection con = null;
		CallableStatement proc = null;
		try {
			con = ConnectionManager.ds.getConnection();
			proc = con
					.prepareCall("{call proc_DeleteModuleSpecRights(?,?,?)}");
			proc.setString(1, obj.getWorkSpaceId());
			proc.setInt(2, obj.getUserCode());
			proc.setInt(3, obj.getUserGroupCode());
			proc.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (proc != null) {
					proc.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public DTOWorkSpaceUserMst getUserDetail(DTOWorkSpaceUserMst obj) {
		DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
		Connection con = null;
		ResultSet rs = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("WorkspaceId='" + obj.getWorkSpaceId()
					+ "' and UserGroupCode=" + obj.getUserGroupCode()
					+ " and UserCode=" + obj.getUserCode());
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "*", "view_workspaceUserDetail",	query.toString(), "");
			if (rs.next()) {
				dto.setWorkSpaceId(rs.getString("WorkspaceId"));
				dto.setUserGroupCode(rs.getInt("UserGroupCode"));
				dto.setUserCode(rs.getInt("UserCode"));
				dto.setModifyOn(rs.getTimestamp("ModifyOn"));
				dto.setFromDt(rs.getTimestamp("FromDt"));
				dto.setToDt(rs.getTimestamp("ToDt"));
				dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
				dto.setUsername(rs.getString("Username"));
				dto.setUserGroupName(rs.getString("userGroupname"));
				dto.setWorkspacedesc(rs.getString("WorkspaceDesc"));
				dto.setAdminFlag(rs.getString("AdminFlag").charAt(0));
				dto.setLastAccessedOn(rs.getTimestamp("LastAccessedOn"));
				dto.setRemark(rs.getString("Remark"));
				dto.setModifyBy(rs.getInt("ModifyBy"));
				dto.setRightsType(rs.getString("RightsType"));
				dto.setUserTypeName(rs.getString("UserTypeName"));
				dto.setRoleCode(rs.getString("RoleCode"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return dto;
	}

	public Vector<DTOUserMst> getWorkspaceUserDetail(String workspaceID,
			DTOUserMst usermst) {
		Vector<DTOUserMst> userDtlVector = new Vector<DTOUserMst>();
		Connection con = null;
		ResultSet rs = null;
		try {
			String whr = "UserCode <> " + usermst.getUserCode()
					+ " and WorkspaceId='" + workspaceID
					+ "' and statusIndi <> 'D'";
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "userGroupCode,usercode,username,UserTypeName",
					"view_workspaceUserDetail", whr, "");
			while (rs.next()) {
				DTOUserMst userMstDTO = new DTOUserMst();
				userMstDTO.setUserGroupCode(rs.getInt("userGroupCode"));
				userMstDTO.setUserCode(rs.getInt("userCode"));
				userMstDTO.setLoginName(rs.getString("username"));
				userMstDTO.setUserType(rs.getString("UserTypeName"));
				userDtlVector.addElement(userMstDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return userDtlVector;
	}
	public Vector<DTOWorkSpaceUserRightsMst> getWorkspaceuserdetailByNodeId(String workspaceID,
			int NodeId,DTOUserMst usermst) {
		Vector<DTOWorkSpaceUserRightsMst> userDtlVector = new Vector<DTOWorkSpaceUserRightsMst>();
		Connection con = null;
		ResultSet rs = null;
		try {
			String whr = "iUserCode<> " + usermst.getUserCode()
					+ " and vWorkspaceId='" + workspaceID
					+ "' and cStatusIndi <> 'D' and iNodeId="+NodeId;
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "iUserGroupCode,iUserCode,vusername,vUserTypeName",
					"view_WorkspaceuserdetailByNodeId", whr, "vusername");
			while (rs.next()) {
				DTOWorkSpaceUserRightsMst userMstDTO = new DTOWorkSpaceUserRightsMst();
				userMstDTO.setUserGroupCode(rs.getInt("iUserGroupCode"));
				userMstDTO.setUserCode(rs.getInt("iUserCode"));
				userMstDTO.setUserName(rs.getString("vusername"));
				userMstDTO.setUserTypeName(rs.getString("vUserTypeName"));
				userDtlVector.addElement(userMstDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return userDtlVector;
	}
	public Vector<DTOUserMst> getModuleorprojectwiseWiseWorkspaceUserDetail(String workspaceID,
			DTOUserMst usermst,String RightsType) {
		Vector<DTOUserMst> userDtlVector = new Vector<DTOUserMst>();
		Connection con = null;
		ResultSet rs = null;
		String whr = null;
		try {
			if(RightsType.equals("modulewise"))
			{
				whr = "UserTypeName <> 'WA' "
					+ " and WorkspaceId='" + workspaceID
					+ "' and statusIndi <> 'D' and RightsType='modulewise'";
			}
			else
			{
				whr = "UserTypeName <> 'WA' "
				+ " and WorkspaceId='" + workspaceID
				+ "' and statusIndi <> 'D' and (RightsType='projectwise' or RightsType IS null)";
			}
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "userGroupCode,usercode,username,UserTypeName",
					"view_workspaceUserDetail", whr, "username asc");
			while (rs.next()) {
				DTOUserMst userMstDTO = new DTOUserMst();
				userMstDTO.setUserGroupCode(rs.getInt("userGroupCode"));
				userMstDTO.setUserCode(rs.getInt("userCode"));
				userMstDTO.setLoginName(rs.getString("username"));
				userMstDTO.setUserType(rs.getString("UserTypeName"));
				userDtlVector.addElement(userMstDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return userDtlVector;
	}

	public Vector<DTOUserMst> getUserDetailByWorkspaceId(String wsId) {
		Vector<DTOUserMst> userDtlVector = new Vector<DTOUserMst>();
		Connection con = null;
		ResultSet rs = null;

		DTOUserMst userDto = new DTOUserMst();
		userDto.setUserCode(0);
		userDto.setLoginName("All");
		userDtlVector.addElement(userDto);
		try {
			String where = "iuserCode In ( select iUserCode from workspaceusermst where vWorkSpaceId='"
					+ wsId + "' )";
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con,
					"iUserCode,iUserGroupCode,vUserName", "usermst", where, "");
			while (rs.next()) {
				DTOUserMst userMstDTO = new DTOUserMst();
				userMstDTO.setUserCode(rs.getInt("iUserCode"));
				userMstDTO.setUserGroupCode(rs.getInt("iUserGroupCode"));
				userMstDTO.setLoginName(rs.getString("vUserName"));
				userDtlVector.addElement(userMstDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return userDtlVector;
	}

	// call from Welcome.java (To print Last workspace accessed)

	public Vector<DTOWorkSpaceUserMst> getRecentWorkspacedetails(String userCode) {
		// String wDesc=null;
		Vector<DTOWorkSpaceUserMst> data = new Vector<DTOWorkSpaceUserMst>();

		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "Top 3 WorkspaceId,WorkspaceDesc",
					"view_workspaceUserDetail", "usercode='"+ userCode+"'and WsStatusIndi<>'A' and ProjectType<>'D' and StatusIndi<>'D' "
							+ "and StatusIndi<>'V' and StatusIndi<>'L'  and wuStatusIndi<>'D' ", "LastAccessedOn Desc");
			while (rs.next()) {
				DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
				dto.setWorkSpaceId(rs.getString("WorkspaceId"));
				dto.setWorkspacedesc(rs.getString("WorkspaceDesc"));
				data.addElement(dto);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return data;
	}
	// create by Virendra Barad for Insert data in WorkspaceUserMstHisroty
	
	public void insertUpdateUsertoWorkspaceHistoryForAttachUser(DTOWorkSpaceUserMst obj,int[] userCode) {
		ArrayList<DTOWorkSpaceUserMst> users = new ArrayList<DTOWorkSpaceUserMst>();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		for (int i = 0; i < userCode.length; i++) {
			try {
				DTOWorkSpaceUserMst dto = (DTOWorkSpaceUserMst) obj.clone();
				dto.setUserCode(userCode[i]);
				DTOUserMst getuserData = new DTOUserMst();
				getuserData  = docMgmtImpl.getUserByCode(userCode[i]);
				if(!getuserData.getRoleCode().isEmpty()){
					dto.setRoleCode(getuserData.getRoleCode());
				}
				users.add(dto);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		insertUpdateUsertoWorkspaceHistory(users);
	}
	
	public void insertUpdateUsertoWorkspaceHistoryForAttachUser(DTOWorkSpaceUserMst obj,int userCode) {
		ArrayList<DTOWorkSpaceUserMst> users = new ArrayList<DTOWorkSpaceUserMst>();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		//for (int i = 0; i < userCode.length; i++) {
			try {
				DTOWorkSpaceUserMst dto = (DTOWorkSpaceUserMst) obj.clone();
				dto.setUserCode(userCode);
				DTOUserMst getuserData = new DTOUserMst();
				getuserData  = docMgmtImpl.getUserByCode(userCode);
				if(!getuserData.getRoleCode().isEmpty()){
					dto.setRoleCode(getuserData.getRoleCode());
				}
				users.add(dto);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		//}
		insertUpdateUsertoWorkspaceHistory(users);
	}
	
		public void insertUpdateUsertoWorkspaceHistory(DTOWorkSpaceUserMst obj,
				int[] userCode) {
			ArrayList<DTOWorkSpaceUserMst> users = new ArrayList<DTOWorkSpaceUserMst>();
			for (int i = 0; i < userCode.length; i++) {
				try {
					DTOWorkSpaceUserMst dto = (DTOWorkSpaceUserMst) obj.clone();
					dto.setUserCode(userCode[i]);
					users.add(dto);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
			insertUpdateUsertoWorkspaceHistory(users);
		}

		public void insertUpdateUsertoWorkspaceHistory(ArrayList<DTOWorkSpaceUserMst> users) {
			Connection con = null;
			CallableStatement proc = null;
			try {
				con = ConnectionManager.ds.getConnection();
				proc = con
						.prepareCall("{call Insert_workspaceUserMstHistory(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				for (int i = 0; i < users.size(); i++) {
					DTOWorkSpaceUserMst obj = users.get(i);
					proc.setString(1, obj.getWorkSpaceId());
					proc.setInt(2, obj.getUserGroupCode());
					proc.setInt(3, obj.getUserCode());
					proc.setString(4, Character.toString(obj.getAdminFlag()));
					proc.setString(5, obj.getRemark());
					proc.setInt(6, obj.getModifyBy());
					proc.setString(7, "E");
					Timestamp ts1 = new Timestamp(obj.getFromDt().getTime());
					proc.setTimestamp(8, ts1);
					Timestamp ts2 = new Timestamp(obj.getToDt().getTime());
					proc.setTimestamp(9, ts2);
					proc.setString(10, obj.getStages());
					proc.setString(11,obj.getRightsType());
					proc.setString(12,obj.getRoleCode());
					proc.setInt(13, obj.getMode()); // datamode 1 for insert or (if available
										// then)update
					proc.execute();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (proc != null) {
						proc.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				try {
					if (con != null) {
						con.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		//Create by Virendra Barad for check WorkspaceRights of Deleting user
		public Vector checkWorkspacemsthistory(String wsId, int userCode, int userGrpcode) {
			
			Vector workspaceuserdtl = new Vector();
			Connection con = null;
			ResultSet rs = null;
			try {
				String whr = "iUserCode= " + userCode + " and vWorkspaceId='" + wsId + "' and iUserGroupCode="+userGrpcode + " and iNodeId=1";
				con = ConnectionManager.ds.getConnection();
						rs = dataTable.getDataSet(con, "iStageId","workspaceuserrightsmst", whr, "");
				while (rs.next()) {
					workspaceuserdtl.add(rs.getInt("iStageId"));
				}
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				try {
					if (con != null) {
						con.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			return workspaceuserdtl;
		}
		
		//create by Virendra Barad for get WorkspaceUserDetailHhistory
		public Vector<DTOWorkSpaceUserMst> getWorkspaceUserHistory(String wsId, String userCode, String userGroupCode) {
				
				DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
				Vector<DTOWorkSpaceUserMst> wsUserDetail = new Vector<DTOWorkSpaceUserMst>();
				ResultSet rs = null;
				Connection con = null;
				ArrayList<String> time = new ArrayList<String>();
				String locationName = ActionContext.getContext().getSession().get("locationname").toString();
				String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
				try {
					con = ConnectionManager.ds.getConnection();

					String Where = "WorkspaceId = '" + wsId +"' and UserCode='"+ userCode + "' and UserGroupCode='" + userGroupCode+"'";
					rs = dataTable.getDataSet(con, "*", "view_workspaceUserDetailHistory", Where,"TranNo");

					while (rs.next()) {
						DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
						dto.setWorkspacedesc(rs.getString("WorkspaceDesc")); // WorkspaceDesc
						dto.setUserGroupName(rs.getString("userGroupName")); // UserGroupName
						dto.setUsername(rs.getString("userName")); // UserName
						dto.setFromDt(rs.getTimestamp("FromDt")); // fromdate
		                Timestamp fromDate=new Timestamp(dto.getFromDt().getTime()); 
					if(countryCode.equalsIgnoreCase("IND")){
						time = docMgmtImpl.TimeZoneConversion(fromDate,locationName,countryCode);
						dto.setFromISTDate(time.get(0));
					}
					else{
						time = docMgmtImpl.TimeZoneConversion(fromDate,locationName,countryCode);
						dto.setFromISTDate(time.get(0));
						dto.setFromESTDate(time.get(1));
					}
						dto.setToDt(rs.getTimestamp("ToDt")); // To Date
						Timestamp ToDate=new Timestamp(dto.getToDt().getTime());
					if(countryCode.equalsIgnoreCase("IND")){
						time = docMgmtImpl.TimeZoneConversion(ToDate,locationName,countryCode);
						dto.setToISTDate(time.get(0));
					}
					else{
						time = docMgmtImpl.TimeZoneConversion(ToDate,locationName,countryCode);
						dto.setToISTDate(time.get(0));
						dto.setToESTDate(time.get(1));
					}
						dto.setRightsGivenBy(rs.getString("RightsGivenBy")); // RightsGivenBy
						dto.setRightsType(rs.getString("RightsType")); // RightsType
						dto.setModifyBy(rs.getInt("ModifyBy")); // modifyBy
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
						dto.setStages(rs.getString("Stages"));    //Stages
						dto.setRoleCode(rs.getString("RoleCode"));
						dto.setRoleName(rs.getString("RoleName"));
						wsUserDetail.addElement(dto);
						dto = null;
					}

					rs.close();
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
				
				return wsUserDetail;
			}
		// create by Virendra Barad for get deleted user from workspace
		public Vector<DTOWorkSpaceUserMst> getDeletedWorkspaceUserHistory(String wsId) {
			
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			Vector<DTOWorkSpaceUserMst> wsDeletedUserDetail = new Vector<DTOWorkSpaceUserMst>();
			ResultSet rs = null;
			Connection con = null;
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			try {
				con = ConnectionManager.ds.getConnection();

				String Where = "WorkspaceId = '" + wsId +"' and StatusIndi='D'";
				rs = dataTable.getDataSet(con, "*", "view_workspaceUserDetailHistory", Where,"TranNo");

				while (rs.next()) {
					DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
					dto.setWorkspacedesc(rs.getString("WorkspaceDesc")); // WorkspaceDesc
					dto.setUserGroupName(rs.getString("userGroupName")); // UserGroupName
					dto.setUsername(rs.getString("userName")); // UserName
					dto.setFromDt(rs.getTimestamp("FromDt")); // fromdate
		            Timestamp fromDate=new Timestamp(dto.getFromDt().getTime()); 
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(fromDate,locationName,countryCode);
					dto.setFromISTDate(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(fromDate,locationName,countryCode);
					dto.setFromISTDate(time.get(0));
					dto.setFromESTDate(time.get(1));
				}
					dto.setToDt(rs.getTimestamp("ToDt")); // To Date
					Timestamp ToDate=new Timestamp(dto.getToDt().getTime());
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(ToDate,locationName,countryCode);
					dto.setToISTDate(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(ToDate,locationName,countryCode);
					dto.setToISTDate(time.get(0));
					dto.setToESTDate(time.get(1));
				}
					dto.setRightsGivenBy(rs.getString("RightsGivenBy")); // RightsGivenBy
					dto.setRightsType(rs.getString("RightsType")); // RightsType
					dto.setModifyBy(rs.getInt("ModifyBy")); // modifyBy
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
					dto.setStages(rs.getString("Stages"));    //Stages
					dto.setRoleCode(rs.getString("RoleCode"));
					dto.setRoleName(rs.getString("RoleName"));
					wsDeletedUserDetail.addElement(dto);
					dto = null;
				}

				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
			return wsDeletedUserDetail;
		}
		// create by Virendra Barad for get deleted user from workspace
		public Vector<DTOWorkSpaceUserMst> getWorkspaceUserHistoryForReport(String wsId) {
			
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			Vector<DTOWorkSpaceUserMst> wsUserDetail = new Vector<DTOWorkSpaceUserMst>();
			ResultSet rs = null;
			Connection con = null;
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			try {
				con = ConnectionManager.ds.getConnection();

				String Where = "WorkspaceId = '" + wsId+"'";
				rs = dataTable.getDataSet(con, "*", "view_workspaceUserDetailHistory", Where,"TranNo");

				while (rs.next()) {
					DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
					dto.setWorkspacedesc(rs.getString("WorkspaceDesc")); // WorkspaceDesc
					dto.setUserGroupName(rs.getString("userGroupName")); // UserGroupName
					dto.setUsername(rs.getString("userName")); // UserName
					dto.setFromDt(rs.getTimestamp("FromDt")); // fromdate
	                Timestamp fromDate=new Timestamp(dto.getFromDt().getTime()); 
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(fromDate,locationName,countryCode);
					dto.setFromISTDate(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(fromDate,locationName,countryCode);
					dto.setFromISTDate(time.get(0));
					dto.setFromESTDate(time.get(1));
				}
					dto.setToDt(rs.getTimestamp("ToDt")); // To Date
					Timestamp ToDate=new Timestamp(dto.getToDt().getTime());
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(ToDate,locationName,countryCode);
					dto.setToISTDate(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(ToDate,locationName,countryCode);
					dto.setToISTDate(time.get(0));
					dto.setToESTDate(time.get(1));
				}
					dto.setRightsGivenBy(rs.getString("RightsGivenBy")); // RightsGivenBy
					dto.setRightsType(rs.getString("RightsType")); // RightsType
					dto.setModifyBy(rs.getInt("ModifyBy")); // modifyBy
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
					dto.setStages(rs.getString("Stages"));    //Stages
					String temp=dto.getStages();
					if(temp.contains(",")){
						temp = temp.replace(",", ", ");
						dto.setStages(temp);
					}
					wsUserDetail.addElement(dto);
					dto = null;
				}

				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
			return wsUserDetail;
		}

		public Vector<DTOUserMst> getWorkspaceUserDetailCSV(String workspaceID) {
			Vector<DTOUserMst> userDtlVector = new Vector<DTOUserMst>();
			Connection con = null;
			ResultSet rs = null;
			try {
				
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "userGroupCode,usercode,username,UserTypeName",
						"view_workspaceUserDetail", "WorkspaceId='"+workspaceID+"' and statusIndi <> 'D' and UserTypeName='WA'", "");
				while (rs.next()) {
					DTOUserMst userMstDTO = new DTOUserMst();
					userMstDTO.setUserGroupCode(rs.getInt("userGroupCode"));
					userMstDTO.setUserCode(rs.getInt("userCode"));
					userMstDTO.setLoginName(rs.getString("username"));
					userMstDTO.setUserType(rs.getString("UserTypeName"));
					userDtlVector.addElement(userMstDTO);
				}
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return userDtlVector;
		}

		public Vector<DTOUserMst> getModuleorprojectwiseWiseWorkspaceUserDetailForCSV(String workspaceID,
				int nodeId,String RightsType) {
			Vector<DTOUserMst> userDtlVector = new Vector<DTOUserMst>();
			Connection con = null;
			ResultSet rs = null;
			String whr = null;
			try {
				
				whr="UserCode not in (select iusercode from view_userRightsDetail Where vWorkspaceId ='"+workspaceID+"' and iNodeId = "+nodeId+") "
						+ "and UserTypeName <> 'WA' and WorkspaceId='"+workspaceID+"' and WsStatusIndi<>'D' and statusIndi <> 'D' and RightsType ='"+RightsType+"' ";
				
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "WorkspaceId,userGroupCode,usercode,username,UserTypeName",
						"view_workspaceUserDetail", whr, "username asc");
				while (rs.next()) {
					DTOUserMst userMstDTO = new DTOUserMst();
					userMstDTO.setUserGroupCode(rs.getInt("userGroupCode"));
					userMstDTO.setUserCode(rs.getInt("userCode"));
					userMstDTO.setLoginName(rs.getString("username"));
					userMstDTO.setUserType(rs.getString("UserTypeName"));
					userDtlVector.addElement(userMstDTO);
				}
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return userDtlVector;
		}
		public Vector<DTOUserMst> getModuleorprojectwiseWiseWorkspaceUserDetailForCSVForRoleCode(String workspaceID,
				int nodeId,String roleCode,String RightsType) {
			Vector<DTOUserMst> userDtlVector = new Vector<DTOUserMst>();
			Connection con = null;
			ResultSet rs = null;
			String whr = null;
			try {
				
				whr="UserCode not in (select iusercode from view_userRightsDetail Where vWorkspaceId ='"+workspaceID+"' and iNodeId = "+nodeId+") "
						+ "and RoleCode = '"+roleCode+"' "
						+ "and UserTypeCode <> '0002' and WorkspaceId='"+workspaceID+"' and WsStatusIndi<>'D' and statusIndi <> 'D' and RightsType ='"+RightsType+"' ";
				
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "WorkspaceId,userGroupCode,usercode,username,UserTypeName,userTypeCode,RoleCode",
						"view_workspaceUserDetail", whr, "username asc");
				while (rs.next()) {
					DTOUserMst userMstDTO = new DTOUserMst();
					userMstDTO.setUserGroupCode(rs.getInt("userGroupCode"));
					userMstDTO.setUserCode(rs.getInt("userCode"));
					userMstDTO.setLoginName(rs.getString("username"));
					userMstDTO.setUserType(rs.getString("UserTypeName"));
					userMstDTO.setRoleCode(rs.getString("RoleCode"));
					userDtlVector.addElement(userMstDTO);
				}
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return userDtlVector;
		}
		public Vector<DTOWorkSpaceUserMst> getWorkspaceUserDetailForDocSign(String wsId,int userId, int useGroupCode ) {
			Vector<DTOWorkSpaceUserMst> workspaceuserdtl = new Vector<DTOWorkSpaceUserMst>();
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			Connection con = null;
			ResultSet rs = null;
			try {
				con = ConnectionManager.ds.getConnection();
				String where = "workspaceid='" + wsId + "' and (statusindi='N' or statusindi='E')"
						+ " and WsStatusIndi<>'D' and UserGroupCode="+useGroupCode+" and UserCode="+userId;
				rs = dataTable.getDataSet(con, "*", "view_workspaceUserDetail",where, "");
				while (rs.next()) {
					DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
					dto.setWorkSpaceId(rs.getString("WorkspaceId"));
					dto.setUserGroupCode(rs.getInt("UserGroupCode"));
					dto.setUserCode(rs.getInt("UserCode"));
					dto.setModifyOn(rs.getTimestamp("ModifyOn"));
					dto.setFromDt(rs.getTimestamp("FromDt"));
					Timestamp fromDate=new Timestamp(dto.getFromDt().getTime());
					if(countryCode.equalsIgnoreCase("IND")){
						time = docMgmtImpl.TimeZoneConversion(fromDate,locationName,countryCode);
						dto.setFromISTDate(time.get(0));
					}
					else{
						time = docMgmtImpl.TimeZoneConversion(fromDate,locationName,countryCode);
						dto.setFromISTDate(time.get(0));
						dto.setFromESTDate(time.get(1));
					}
						dto.setToDt(rs.getTimestamp("ToDt"));
						Timestamp toDate=new Timestamp(dto.getToDt().getTime());
					if(countryCode.equalsIgnoreCase("IND")){
						time = docMgmtImpl.TimeZoneConversion(toDate,locationName,countryCode);
						dto.setToISTDate(time.get(0));
					}
					else{
						time = docMgmtImpl.TimeZoneConversion(toDate,locationName,countryCode);
						dto.setToISTDate(time.get(0));
						dto.setToESTDate(time.get(1));
					}
					dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
					dto.setUsername(rs.getString("Username"));
					dto.setRightsGivenBy(rs.getString("RightsGivenBy"));
					dto.setUserGroupName(rs.getString("userGroupname"));
					dto.setWorkspacedesc(rs.getString("WorkspaceDesc"));
					dto.setAdminFlag(rs.getString("AdminFlag").charAt(0));
					dto.setLastAccessedOn(rs.getTimestamp("LastAccessedOn"));
					dto.setRemark(rs.getString("Remark"));
					dto.setModifyBy(rs.getInt("ModifyBy"));
					dto.setRightsType(rs.getString("RIghtsType"));
					dto.setUserTypeName(rs.getString("UserTypeName"));
					if(countryCode.equalsIgnoreCase("IND")){
						time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
						dto.setISTDateTime(time.get(0));
					}
					else{
						time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
						dto.setISTDateTime(time.get(0));
						dto.setESTDateTime(time.get(1));
					}
					workspaceuserdtl.add(dto);
				}
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return workspaceuserdtl;
		}
		public void insertUsertoWorkspaceUserMstForDocSign(DTOWorkSpaceUserMst dto) {
			Connection con = null;
			CallableStatement proc = null;
			try {
				con = ConnectionManager.ds.getConnection();
				proc = con
						.prepareCall("{call Insert_workspaceUserMstForDocSign(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				
					proc.setString(1, dto.getWorkSpaceId());
					proc.setInt(2, dto.getUserGroupCode());
					proc.setInt(3, dto.getUserCode());
					proc.setString(4, Character.toString(dto.getAdminFlag()));
					proc.setTimestamp(5, dto.getLastAccessedOn());
					proc.setString(6, dto.getRemark());
					proc.setInt(7, dto.getModifyBy());
					proc.setTimestamp(8,dto.getModifyOn());
					proc.setString(9, "N");
					proc.setTimestamp(10, (Timestamp) dto.getFromDt());
					proc.setTimestamp(11, (Timestamp) dto.getToDt());
					proc.setString(12,dto.getRightsType());
					proc.setInt(13, 1); // datamode 1 for insert or (if available
										// then)update
					proc.execute();
				
					proc.close();
					con.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void insertUsertoWorkspaceUserMstForDocSignWithRoleCode(DTOWorkSpaceUserMst dto) {
			Connection con = null;
			CallableStatement proc = null;
			try {
				con = ConnectionManager.ds.getConnection();
				proc = con
						.prepareCall("{call Insert_workspaceUserMstForDocSignWithRoleCode(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				
					proc.setString(1, dto.getWorkSpaceId());
					proc.setInt(2, dto.getUserGroupCode());
					proc.setInt(3, dto.getUserCode());
					proc.setString(4, Character.toString(dto.getAdminFlag()));
					proc.setTimestamp(5, dto.getLastAccessedOn());
					proc.setString(6, dto.getRemark());
					proc.setInt(7, dto.getModifyBy());
					proc.setTimestamp(8,dto.getModifyOn());
					proc.setString(9, "N");
					proc.setTimestamp(10, (Timestamp) dto.getFromDt());
					proc.setTimestamp(11, (Timestamp) dto.getToDt());
					proc.setString(12,dto.getRightsType());
					proc.setString(13,dto.getRoleCode());
					proc.setInt(14, 1); // datamode 1 for insert or (if available
										// then)update
					proc.execute();
				
					proc.close();
					con.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public Vector<DTOWorkSpaceUserMst> getUserRightsForWorkspace(String wsId,int userCode,int userGroupCode) {
			Vector<DTOWorkSpaceUserMst> workspaceuserdtl = new Vector<DTOWorkSpaceUserMst>();
			Connection con = null;
			ResultSet rs = null;

			try {
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con,
						"*",
						"workspaceusermst", "vworkspaceid='" + wsId + "' and iUserCode="+userCode+" and iuserGroupCode="+userGroupCode
								+ " and (cStatusindi='N' or cStatusindi='E')", "");
				while (rs.next()) {
					DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
					dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
					dto.setUserCode(rs.getInt("iUserCode"));
					dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
					dto.setStatusIndi(rs.getString("cStatusindi").charAt(0));
					workspaceuserdtl.addElement(dto);
				}
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return workspaceuserdtl;
		}
		public Vector<DTOUserMst> getModuleorprojectwiseWiseWorkspaceUserDetailForDocSign(String workspaceID,
				int nodeId,int userGroupCode) {
			Vector<DTOUserMst> userDtlVector = new Vector<DTOUserMst>();
			Connection con = null;
			ResultSet rs = null;
			String whr = null;
			try {
				
				whr="iUserCode not in (select iUserCode from workspaceuserrightsmst where vWorkSpaceId='"+workspaceID+"' and iNodeId="+nodeId
						+" and iUsergroupcode="+userGroupCode+") and vUserTypeCode ='0003' ";
				
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "iusercode,vUserName,iUserGroupCode,vUserTypeCode",
						"usermst", whr, "vUserName asc");
				while (rs.next()) {
					DTOUserMst userMstDTO = new DTOUserMst();
					userMstDTO.setUserGroupCode(rs.getInt("iUserGroupCode"));
					userMstDTO.setUserCode(rs.getInt("iusercode"));
					userMstDTO.setUserName(rs.getString("vUserName"));
					userMstDTO.setUserTypeCode(rs.getString("vUserTypeCode"));
					userDtlVector.addElement(userMstDTO);
				}
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return userDtlVector;
		}
		public Vector<DTOUserMst> getModuleorprojectwiseWiseWorkspaceUserDetailForDocSignWithRoleCode(String workspaceID,
				int nodeId,int userGroupCode,String roleCode) {
			Vector<DTOUserMst> userDtlVector = new Vector<DTOUserMst>();
			Connection con = null;
			ResultSet rs = null;
			String whr = null;
			try {
				
				whr="iUserCode not in (select iUserCode from workspaceuserrightsmst where vWorkSpaceId='"+workspaceID+"' and iNodeId="+nodeId
						+" and iUsergroupcode="+userGroupCode+") and vUserTypeCode ='0003' and vRoleCode='"+roleCode+"'";
				
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "iusercode,vUserName,iUserGroupCode,vUserTypeCode","usermst", whr, "vUserName asc");
				while (rs.next()) {
					DTOUserMst userMstDTO = new DTOUserMst();
					userMstDTO.setUserGroupCode(rs.getInt("iUserGroupCode"));
					userMstDTO.setUserCode(rs.getInt("iusercode"));
					userMstDTO.setUserName(rs.getString("vUserName"));
					userMstDTO.setUserTypeCode(rs.getString("vUserTypeCode"));
					userDtlVector.addElement(userMstDTO);
				}
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return userDtlVector;
		}

		public Vector<DTOUserMst> getModuleorprojectwiseWiseWorkspaceUserDetailForESignatureWithRoleCode(String workspaceID,
				int nodeId,int userGroupCode,String roleCode) {
			Vector<DTOUserMst> userDtlVector = new Vector<DTOUserMst>();
			Connection con = null;
			ResultSet rs = null;
			String whr = null;
			try {
				
				whr="iUserCode not in (select iUserCode from WorkspaceUserRightsMstForESignature where vWorkSpaceId='"+workspaceID+"' and iNodeId="+nodeId
						+" and iUsergroupcode="+userGroupCode+") and vUserTypeCode ='0003' and vRoleCode='"+roleCode+"'";
				
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "iusercode,vUserName,iUserGroupCode,vUserTypeCode","usermst", whr, "vUserName asc");
				while (rs.next()) {
					DTOUserMst userMstDTO = new DTOUserMst();
					userMstDTO.setUserGroupCode(rs.getInt("iUserGroupCode"));
					userMstDTO.setUserCode(rs.getInt("iusercode"));
					userMstDTO.setUserName(rs.getString("vUserName"));
					userMstDTO.setUserTypeCode(rs.getString("vUserTypeCode"));
					userDtlVector.addElement(userMstDTO);
				}
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return userDtlVector;
		}
		
		
		
		public Vector<DTOWorkSpaceMst> getUserWorkspaceDetail(int userGroupCode, int userCode) {
			// String wDesc=null;
			Vector<DTOWorkSpaceMst> data = new Vector<DTOWorkSpaceMst>();

			Connection con = null;
			ResultSet rs = null;

			try {
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "WorkspaceId,WorkspaceDesc",
						"view_workspaceUserDetail", "usercode="+ userCode+" and usergroupCode = "+userGroupCode+" and WsStatusIndi<>'A' and ProjectType<>'D' and StatusIndi<>'D' "
								+ "and StatusIndi<>'V' and StatusIndi<>'L'", "LastAccessedOn Desc");
				while (rs.next()) {
					DTOWorkSpaceMst dto = new DTOWorkSpaceMst();
					dto.setWorkSpaceId(rs.getString("WorkspaceId"));
					dto.setWorkSpaceDesc(rs.getString("WorkspaceDesc"));
					data.addElement(dto);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				try {
					if (con != null) {
						con.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return data;
		}
	
		public Vector<DTOUserMst> getWorkspaceUserDetailForNode(String workspaceID,int nodeId) {
			Vector<DTOUserMst> userDtlVector = new Vector<DTOUserMst>();
			Connection con = null;
			ResultSet rs = null;
			String whr = null;
			
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			
			try {
				
				whr="view_userRightsDetail.vUserTypeCode<>'0001' and view_userRightsDetail.vUserTypeCode<>'0002' and"
						+ "  view_userRightsDetail.vWorkSpaceId='"+workspaceID+"' and view_userRightsDetail.iNodeId="+nodeId+
							" ";
				
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "*",
						"view_userRightsDetail left join workspacenodeofficehistory on "
						+ "workspacenodeofficehistory.vworkspaceid= view_userRightsDetail.vWorkSpaceId and "
						+ "workspacenodeofficehistory.imodifyby= view_userRightsDetail.iusercode and workspacenodeofficehistory.iNodeId= view_userRightsDetail.iNodeId ", 
						   //whr, "dUploadOn desc");
						whr, "istageId,id");
				while (rs.next()) {
					DTOUserMst userMstDTO = new DTOUserMst();
					userMstDTO.setUserGroupCode(rs.getInt("iuserGroupCode"));
					userMstDTO.setUserCode(rs.getInt("iuserCode"));
					userMstDTO.setLoginName(rs.getString("vusername"));
					if(rs.getString("vFileName")==null){userMstDTO.setFileName("-");}
					else{userMstDTO.setFileName(rs.getString("vFileName"));}
					
					if(rs.getString("vFilePath")==null){userMstDTO.setFilePath("-");}
					else{
						String path=rs.getString("vFilePath");
						path = path.replace("\\", "/");
						userMstDTO.setFilePath(path);
					}
					
					if(rs.getString("cIsUpload")==null){userMstDTO.setClsUpload('-');}
					else{userMstDTO.setClsUpload(rs.getString("cIsUpload").charAt(0));}
					
					if(rs.getString("cIsUpload")==null){userMstDTO.setClsDownload('-');}
					else{userMstDTO.setClsDownload(rs.getString("cIsDownload").charAt(0));}
					
					if(rs.getInt("iModifyBy")==0){userMstDTO.setModifyBy(0);}
					else{userMstDTO.setModifyBy(rs.getInt("iModifyBy"));}
					
					if(rs.getString("cStatusIndi")==null){userMstDTO.setStatusIndi('-');}
					else{userMstDTO.setStatusIndi(rs.getString("cStatusIndi").charAt(0));}
					
					
					userMstDTO.setdUploadOn(rs.getTimestamp("dUploadOn"));
					
					if(userMstDTO.getdUploadOn()!=null){
					if(countryCode.equalsIgnoreCase("IND")){
						time = docMgmtImpl.TimeZoneConversion(userMstDTO.getdUploadOn(),locationName,countryCode);
						userMstDTO.setISTUploadOn(time.get(0));
					}
					else{
						time = docMgmtImpl.TimeZoneConversion(userMstDTO.getdUploadOn(),locationName,countryCode);
						userMstDTO.setISTUploadOn(time.get(0));
						userMstDTO.setESTUploadOn(time.get(1));
					}
					}
					else{
						userMstDTO.setISTUploadOn("No");
						userMstDTO.setESTUploadOn("No");
					}
					
					
					userMstDTO.setdDownloadOn(rs.getTimestamp("dDownloadOn"));
					if(userMstDTO.getdDownloadOn()!=null){
					if(countryCode.equalsIgnoreCase("IND")){
						time = docMgmtImpl.TimeZoneConversion(userMstDTO.getdDownloadOn(),locationName,countryCode);
						userMstDTO.setISTDownloadOn(time.get(0));
					}
					else{
						time = docMgmtImpl.TimeZoneConversion(userMstDTO.getdDownloadOn(),locationName,countryCode);
						userMstDTO.setISTDownloadOn(time.get(0));
						userMstDTO.setESTDownloadOn(time.get(1));
					}
				}
					else{
						userMstDTO.setISTDownloadOn("No");
						userMstDTO.setESTDownloadOn("No");
					}
					
					
					
					userDtlVector.addElement(userMstDTO);
				}
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return userDtlVector;
		}
		public String getRoleCode(String wsId, int userCode) {
			// String wDesc=null;
			String roleCode="";

			Connection con = null;
			ResultSet rs = null;

			try {
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "vRoleCode",	"workspaceusermst", "vWorkspaceId='"+wsId+"' and  iusercode="+ userCode, "");
				while (rs.next()) {
				
					roleCode = rs.getString("vRoleCode");

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return roleCode;
		}
		
		
		public String getRoleCodeFromUserMst(int userCode,int userGroupCode) {
			// String wDesc=null;
			String roleCode="";

			Connection con = null;
			ResultSet rs = null;

			try {
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "vRoleCode",	"usermst", "iUserGroupCode="+userGroupCode+" and  iusercode="+ userCode, "");
				while (rs.next()) {
				
					roleCode = rs.getString("vRoleCode");

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return roleCode;
		}
		public String getRoleCodeFromWSUserRightsMst(String WsId,int nodeId,int userCode) {
			// String wDesc=null;
			String roleCode="";

			Connection con = null;
			ResultSet rs = null;

			try {
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "vRoleCode",	"WorkspaceUserRightsMst",
						"vWorkspaceId='"+WsId+"' and iNodeId="+nodeId+" and iusercode="+ userCode
						, "");
				while (rs.next()) {
				
					roleCode = rs.getString("vRoleCode");

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return roleCode;
		}
		
		public Vector<DTOWorkSpaceUserMst> getUserRightsForESignature(String wsId,int userCode,int userGroupCode) {
			Vector<DTOWorkSpaceUserMst> workspaceuserdtl = new Vector<DTOWorkSpaceUserMst>();
			Connection con = null;
			ResultSet rs = null;

			try {
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con,
						"*",
						"WorkspaceUserRightsMstForESignature", "vworkspaceid='" + wsId + "' and iUserCode="+userCode+" and iuserGroupCode="+userGroupCode
								+ " and (cStatusindi='N' or cStatusindi='E')", "");
				while (rs.next()) {
					DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
					dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
					dto.setUserCode(rs.getInt("iUserCode"));
					dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
					dto.setStatusIndi(rs.getString("cStatusindi").charAt(0));
					workspaceuserdtl.addElement(dto);
				}
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return workspaceuserdtl;
		}		
		
		public Vector<DTOUserMst> getModuleorprojectwiseWiseWorkspaceUserDetailForESignature(String workspaceID,int nodeId,String RightsType) {
			Vector<DTOUserMst> userDtlVector = new Vector<DTOUserMst>();
			Connection con = null;
			ResultSet rs = null;
			String whr = null;
			try {
				
				whr="UserCode not in (select iusercode from view_userRightsDetailForESignature Where vWorkspaceId ='"+workspaceID+"' and iNodeId = "+nodeId+") "
						+ "and UserTypeName <> 'WA' and WorkspaceId='"+workspaceID+"' and WsStatusIndi<>'D' and statusIndi <> 'D' and RightsType ='"+RightsType+"' ";
				
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "WorkspaceId,userGroupCode,usercode,username,UserTypeName",
						"view_workspaceUserDetail", whr, "username asc");
				while (rs.next()) {
					DTOUserMst userMstDTO = new DTOUserMst();
					userMstDTO.setUserGroupCode(rs.getInt("userGroupCode"));
					userMstDTO.setUserCode(rs.getInt("userCode"));
					userMstDTO.setLoginName(rs.getString("username"));
					userMstDTO.setUserType(rs.getString("UserTypeName"));
					userDtlVector.addElement(userMstDTO);
				}
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return userDtlVector;
		}
		
		public Vector<DTOUserMst> getModuleorprojectwiseWorkspaceUserDetailForESignature(String workspaceID,int nodeId,int userGroupCode) {
			Vector<DTOUserMst> userDtlVector = new Vector<DTOUserMst>();
			Connection con = null;
			ResultSet rs = null;
			String whr = null;
			try {
				
				/*whr="iUserCode not in (select iUserCode from WorkspaceUserRightsMstForESignature where vWorkSpaceId='"+workspaceID+"' and iNodeId="+nodeId
						+" and iUsergroupcode="+userGroupCode+") and vUserTypeCode ='0003' ";*/
				
				whr="iUserCode not in (select iUserCode from WorkspaceUserRightsMstForESignature where vWorkSpaceId='"+workspaceID+"' and iNodeId="+nodeId
						+") and vUserTypeCode ='0003' and cStatusIndi <> 'D' ";
				
				con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "iusercode,vUserName,iUserGroupCode,vUserTypeCode",
						"usermst", whr, "vUserName asc");
				while (rs.next()) {
					DTOUserMst userMstDTO = new DTOUserMst();
					userMstDTO.setUserGroupCode(rs.getInt("iUserGroupCode"));
					userMstDTO.setUserCode(rs.getInt("iusercode"));
					userMstDTO.setUserName(rs.getString("vUserName"));
					userMstDTO.setUserTypeCode(rs.getString("vUserTypeCode"));
					userDtlVector.addElement(userMstDTO);
				}
				rs.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return userDtlVector;
		}
	
public Vector<DTOWorkSpaceUserMst> getAllWorkspaceUserHistory(String wsId) {
			
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			Vector<DTOWorkSpaceUserMst> wsDeletedUserDetail = new Vector<DTOWorkSpaceUserMst>();
			ResultSet rs = null;
			Connection con = null;
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			try {
				con = ConnectionManager.ds.getConnection();

				String Where = "WorkspaceId = '" + wsId +"'";
				rs = dataTable.getDataSet(con, "*", "view_workspaceUserDetailHistory", Where,"TranNo");

				while (rs.next()) {
					DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
					dto.setWorkspacedesc(rs.getString("WorkspaceDesc")); // WorkspaceDesc
					dto.setUserGroupName(rs.getString("userGroupName")); // UserGroupName
					dto.setUsername(rs.getString("userName")); // UserName
					dto.setFromDt(rs.getTimestamp("FromDt")); // fromdate
		            Timestamp fromDate=new Timestamp(dto.getFromDt().getTime()); 
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(fromDate,locationName,countryCode);
					dto.setFromISTDate(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(fromDate,locationName,countryCode);
					dto.setFromISTDate(time.get(0));
					dto.setFromESTDate(time.get(1));
				}
					dto.setToDt(rs.getTimestamp("ToDt")); // To Date
					Timestamp ToDate=new Timestamp(dto.getToDt().getTime());
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(ToDate,locationName,countryCode);
					dto.setToISTDate(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(ToDate,locationName,countryCode);
					dto.setToISTDate(time.get(0));
					dto.setToESTDate(time.get(1));
				}
					dto.setRightsGivenBy(rs.getString("RightsGivenBy")); // RightsGivenBy
					dto.setRightsType(rs.getString("RightsType")); // RightsType
					dto.setModifyBy(rs.getInt("ModifyBy")); // modifyBy
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
					dto.setStages(rs.getString("Stages"));    //Stages
					dto.setRoleCode(rs.getString("RoleCode"));
					dto.setRoleName(rs.getString("RoleName"));
					wsDeletedUserDetail.addElement(dto);
					dto = null;
				}

				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
			return wsDeletedUserDetail;
		}
		
	public Vector<DTOWorkSpaceUserMst> getUserWiseWS(String userCode) {
	// String wDesc=null;
	Vector<DTOWorkSpaceUserMst> data = new Vector<DTOWorkSpaceUserMst>();

	Connection con = null;
	ResultSet rs = null;

	try {
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "WorkspaceId,WorkspaceDesc",
				"view_workspaceUserDetail", "usercode='"+ userCode+"'and WsStatusIndi<>'A' and ProjectType<>'D' and StatusIndi<>'D' "
						+ "and StatusIndi<>'V' and StatusIndi<>'L'", "LastAccessedOn Desc");
		while (rs.next()) {
			DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
			dto.setWorkSpaceId(rs.getString("WorkspaceId"));
			dto.setWorkspacedesc(rs.getString("WorkspaceDesc"));
			data.addElement(dto);

		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return data;
}
	
	public boolean updateMaintainedSeq(String wsId,int nId,int uCode,int uGrpCode,int tranNo) {
		Connection con = null;
		Statement stmt = null;
		int ret = 0;
		try {
			con = ConnectionManager.ds.getConnection();
			stmt = con.createStatement();
			
			String query = "UPDATE WorkspaceUserRightsMstForESignature SET " + "iSeqNo = "+ tranNo
							+ " WHERE vWorkspaceId = '"+ wsId + "' AND iNodeId = "+ nId +" and iUserCode="+uCode+" and iUserGroupCode="+uGrpCode;
			stmt.addBatch(query);
			
			ret = dataTable.getDataSet1(con,query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public Vector<DTOWorkSpaceUserMst> userAcknowledgement(int userCode) {
		// String wDesc=null;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceUserMst> data = new Vector<DTOWorkSpaceUserMst>();
		
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			String IsUserAcknowledge = PropertyInfo.getPropInfo().getValue("IsUserAcknowledge");
			if(IsUserAcknowledge.equalsIgnoreCase("Yes")){
			rs = dataTable.getDataSet(con, "WorkspaceId,WorkspaceDesc,UserCode",
					"view_workspaceUserDetail", "usercode="+ userCode+" AND StatusIndi<>'D' AND StatusIndi<>'L' AND StatusIndi<>'V' "
							+ "AND StatusIndi<>'W' AND StatusIndi<>'A'  AND ProjectType='E' AND WsStatusIndi<>'D' AND cIsUserAcknowledge='N'","");
			}
			else{
				rs = dataTable.getDataSet(con, "WorkspaceId,WorkspaceDesc,UserCode",
						"view_workspaceUserDetail", "usercode="+ userCode+" AND StatusIndi<>'D' AND StatusIndi<>'L' AND StatusIndi<>'V' "
								+ "AND StatusIndi<>'W' AND StatusIndi<>'A'  AND ProjectType='E' AND WsStatusIndi<>'D' ","");
			}
			while (rs.next()) {
				DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
				dto.setWorkSpaceId(rs.getString("WorkspaceId"));
				dto.setWorkspacedesc(rs.getString("WorkspaceDesc"));
				dto.setUserCode(rs.getInt("userCode"));
				data.addElement(dto);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
		
}// main class ended
