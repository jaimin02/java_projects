package com.docmgmt.server.db.master;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.docmgmt.server.webinterface.services.cal.CalendarUtils;
import com.opensymphony.xwork2.ActionContext;

public class WorkspaceMst {

	static DataTable dataTable = new DataTable();;
	public Vector<DTOWorkSpaceMst> searchWorkspace(
			DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst) {
			ArrayList<Character> projectTypeList = new ArrayList<Character>();
			projectTypeList.add(ProjectType.DMS_IMPORTED);
			projectTypeList.add(ProjectType.DMS_STANDARD);
			projectTypeList.add(ProjectType.ECTD_MANUAL);
			projectTypeList.add(ProjectType.ECTD_STANDARD);
			projectTypeList.add(ProjectType.NEES_STANDARD);
			projectTypeList.add(ProjectType.VNEES_STANDARD);
			return searchWorkspaceByProjectType(workSpaceMst, userMst,
					projectTypeList);
		}

		public Vector<DTOWorkSpaceMst> searchWorkspaceByProjectType(
				DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst,
				ArrayList<Character> projectTypeList) {
			
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			
			String userTypeName;
			userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
			
			String whr="";
			
			Vector<DTOWorkSpaceMst> userWorkspaceDetail = new Vector<DTOWorkSpaceMst>();
			String locationCode = workSpaceMst.getLocationCode();
			String projectCode = workSpaceMst.getProjectCode();
			String clientCode = workSpaceMst.getClientCode();
			String deptCode = workSpaceMst.getDeptCode();
			String workSpaceDesc = workSpaceMst.getWorkSpaceDesc();
			int userCode = userMst.getUserCode();
			int userGroupCode = userMst.getUserGroupCode();
			String userType = userMst.getUserType();
			char str = workSpaceMst.getStatusIndi(); // Set from ShowUserOnProject Module
			String statusindi = String.valueOf(str);
			ResultSet rs = null;
			try {
				// for show Archival Project On Manage Project Module only For Archivist
				if(userTypeName.equalsIgnoreCase("Archivist") && !statusindi.equalsIgnoreCase("A")){      
				whr = "iuserCode=" + userCode + " and iuserGroupCode=" + userGroupCode
						+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cLockSeq='L'";
				
				}
				// for not show Archival Project On UserOnProject Module
				else if(userTypeName.equalsIgnoreCase("Archivist") && statusindi.equalsIgnoreCase("A")){
					whr = "iuserCode=" + userCode + " and iuserGroupCode=" + userGroupCode
							+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A'";
				}
				// for show Projects with Void/UnVoid On UserOnProject Module
				else if(userTypeName.equalsIgnoreCase("WA")){
					whr = "iuserCode=" + userCode + " and iuserGroupCode=" + userGroupCode
							+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A'";
				}
				// for not show Archival Project On Manage Project Module except Archivist
				else
				{
					whr = "iuserCode=" + userCode + " and iuserGroupCode="	+ userGroupCode
							+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A' and cStatusIndi<>'V'" 
							+ " and vProjectCode in ('0002','0003')";
				}
				Connection con = ConnectionManager.ds.getConnection();
				StringBuffer sb = new StringBuffer(whr);
				if (locationCode != null && !locationCode.equals("-1")
						&& !locationCode.equals("")) {
					sb.append(" and vLocationCode='" + locationCode + "'");
				}
				if (clientCode != null && !clientCode.equals("-1")
						&& !clientCode.equals("")) {
					sb.append(" and vClientCode='" + clientCode + "'");
				}
				if (projectCode != null && !projectCode.equals("-1")
						&& !projectCode.equals("")) {
					sb.append(" and vProjectCode='" + projectCode + "'");
				}
				if (deptCode != null && !deptCode.equals("-1")
						&& !deptCode.equals("")) {
					sb.append(" and vDeptCode='" + deptCode + "'");
				}
				if (projectTypeList != null && projectTypeList.size() > 0) {
					sb.append(" and cProjectType in (");
					for (int idxProjectType = 0; idxProjectType < projectTypeList
							.size(); idxProjectType++) {
						sb.append("'"
								+ projectTypeList.get(idxProjectType).charValue()
								+ "',");
					}
					sb.deleteCharAt(sb.length() - 1);
					sb.append(") ");
				}
				if (workSpaceDesc != null) {
					if (!workSpaceDesc.equals(""))
						sb.append(" and vWorkspaceDesc like '%" + workSpaceDesc
								+ "%'");

				}
				String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
						+ "vProjectCode,vProjectName,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,vBaseWorkFolder,"
						+ "vBasePublishFolder,cProjectType,cStatusIndi,dLastAccessedOn,iModifyBy,dToDt,dFromdt,vWorkSpaceRemark,cLockSeq";
				rs = dataTable.getDataSet(con, Fields,
						"View_CommonWorkspaceDetail", sb.toString(), "dCreatedOn Desc");
				while (rs.next()) {
					DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
					workSpaceMstDTO.setWorkSpaceId(rs.getString("vworkspaceid"));
					workSpaceMstDTO.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
					workSpaceMstDTO.setLocationCode(rs.getString("vLocationCode"));
					workSpaceMstDTO.setLocationName(rs.getString("vLocationName"));
					workSpaceMstDTO.setDeptCode(rs.getString("vDeptCode"));
					workSpaceMstDTO.setDeptName(rs.getString("vDeptName"));
					workSpaceMstDTO.setClientCode(rs.getString("vClientCode"));
					workSpaceMstDTO.setClientName(rs.getString("vClientName"));
					workSpaceMstDTO.setProjectCode(rs.getString("vProjectCode"));
					workSpaceMstDTO.setProjectName(rs.getString("vProjectName"));
					workSpaceMstDTO.setLastPublishedVersion(rs.getString("vlastPublishedVersion"));
					workSpaceMstDTO.setCreatedOn(rs.getTimestamp("dcreatedOn"));
					workSpaceMstDTO.setLastAccessedBy(rs.getInt("ilastAccessedBy"));
					workSpaceMstDTO.setLastaccessedUserName(rs.getString("vusername"));
					workSpaceMstDTO.setLastAccessedOn(rs.getTimestamp("dLastAccessedOn"));
					workSpaceMstDTO.setModifyBy(rs.getInt("iModifyBy"));
					workSpaceMstDTO.setLastModifyUserName(rs.getString("vusername"));
					workSpaceMstDTO.setModifyOn(rs.getTimestamp("dmodifyon"));
					workSpaceMstDTO.setBaseWorkFolder(rs.getString("vbaseWorkFolder"));
					workSpaceMstDTO.setBasePublishFolder(rs.getString("vbasePublishFolder"));
					workSpaceMstDTO.setProjectType(rs.getString("cProjectType").charAt(0));
					workSpaceMstDTO.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
					workSpaceMstDTO.setToDate(rs.getTimestamp("dToDt"));
					workSpaceMstDTO.setFromDate(rs.getTimestamp("dFromdt"));
					workSpaceMstDTO.setRemark(rs.getString("vWorkSpaceRemark"));
					workSpaceMstDTO.setLockSeq(rs.getString("cLockSeq").charAt(0));
					if(countryCode.equalsIgnoreCase("IND")){
						time = docMgmtImpl.TimeZoneConversion(workSpaceMstDTO.getModifyOn(),locationName,countryCode);
						workSpaceMstDTO.setISTDateTime(time.get(0));
					}
					else{					
						time = docMgmtImpl.TimeZoneConversion(workSpaceMstDTO.getModifyOn(),locationName,countryCode);
						workSpaceMstDTO.setISTDateTime(time.get(0));
						workSpaceMstDTO.setESTDateTime(time.get(1));
					}
					if (userType == null || userType.equals("")
							|| userType.equals("WA"))
						userWorkspaceDetail.addElement(workSpaceMstDTO);
					else {
						Calendar calendarToday = CalendarUtils
								.dateToCalendar(new Date());

						Calendar clToDate = CalendarUtils.stringToCalendar(
								workSpaceMstDTO.getToDate().toString(),
								"yyyy-MM-dd");
						int diffInDaysTo = CalendarUtils.dateDifferenceInDays(
								calendarToday, clToDate);

						Calendar clFromDate = CalendarUtils.stringToCalendar(
								workSpaceMstDTO.getFromDate().toString(),
								"yyyy-MM-dd");
						int diffInDaysFrom = CalendarUtils.dateDifferenceInDays(
								calendarToday, clFromDate);

						if (calendarToday != null && clToDate != null
								&& diffInDaysTo >= 0 && diffInDaysFrom <= 0) {
							userWorkspaceDetail.addElement(workSpaceMstDTO);
						}
					}
					workSpaceMstDTO = null;
				}

				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return userWorkspaceDetail;
		}
	public Vector<DTOWorkSpaceMst> searchWorkspaceList(
		DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst) {
		ArrayList<Character> projectTypeList = new ArrayList<Character>();
		projectTypeList.add(ProjectType.DMS_IMPORTED);
		projectTypeList.add(ProjectType.DMS_STANDARD);
		projectTypeList.add(ProjectType.ECTD_MANUAL);
		projectTypeList.add(ProjectType.ECTD_STANDARD);
		projectTypeList.add(ProjectType.NEES_STANDARD);
		projectTypeList.add(ProjectType.VNEES_STANDARD);
		return searchWorkspaceByProjectTypeList(workSpaceMst, userMst,
				projectTypeList);
	}

	public Vector<DTOWorkSpaceMst> searchWorkspaceByProjectTypeList(
			DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst,
			ArrayList<Character> projectTypeList) {
		
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		
		String userTypeName;
		userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		String editProject = knetProperties.getValue("isEditProject");
		
		String whr="";
		
		Vector<DTOWorkSpaceMst> userWorkspaceDetail = new Vector<DTOWorkSpaceMst>();
		String locationCode = workSpaceMst.getLocationCode();
		String projectCode = workSpaceMst.getProjectCode();
		String clientCode = workSpaceMst.getClientCode();
		String applicationCode = workSpaceMst.getApplicationCode();
		String deptCode = workSpaceMst.getDeptCode();
		String workSpaceDesc = workSpaceMst.getWorkSpaceDesc();
		int userCode = userMst.getUserCode();
		int userGroupCode = userMst.getUserGroupCode();
		String userType = userMst.getUserType();
		char str = workSpaceMst.getStatusIndi(); // Set from ShowUserOnProject Module
		String statusindi = String.valueOf(str);
		ResultSet rs = null;
		try {
			// for show Archival Project On Manage Project Module only For Archivist
			if(userTypeName.equalsIgnoreCase("Archivist") && !statusindi.equalsIgnoreCase("A")){      
			whr = "iuserCode=" + userCode + " and iuserGroupCode=" + userGroupCode
					+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cLockSeq='L'";
			
			}
			// for not show Archival Project On UserOnProject Module
			else if(userTypeName.equalsIgnoreCase("Archivist") && statusindi.equalsIgnoreCase("A")){
				whr = "iuserCode=" + userCode + " and iuserGroupCode=" + userGroupCode
						+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A'";
			}
			// for show Projects with Void/UnVoid On UserOnProject Module
			else if(userTypeName.equalsIgnoreCase("WA")){
				whr = "iuserCode=" + userCode + " and iuserGroupCode=" + userGroupCode
						+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A'";
			}
			// for not show Archival Project On Manage Project Module except Archivist
			else
			{
				whr = "iuserCode=" + userCode + " and iuserGroupCode="	+ userGroupCode
						+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A' and cStatusIndi<>'V'" 
						+ " and vProjectCode in ('0002','0003')";
			}
			Connection con = ConnectionManager.ds.getConnection();
			StringBuffer sb = new StringBuffer(whr);
			if (locationCode != null && !locationCode.equals("-1")
					&& !locationCode.equals("")) {
				sb.append(" and vLocationCode='" + locationCode + "'");
			}
			if (clientCode != null && !clientCode.equals("-1")
					&& !clientCode.equals("")) {
				sb.append(" and vClientCode='" + clientCode + "'");
			}
			if (projectCode != null && !projectCode.equals("-1")
					&& !projectCode.equals("")) {
				sb.append(" and vProjectCode='" + projectCode + "'");
			}
			if (applicationCode != null && !applicationCode.equals("-1")
					&& !projectCode.equals("")) {
				sb.append(" and vApplicationCode='" + applicationCode + "'");
			}
			
			
			
			if (deptCode != null && !deptCode.equals("-1")
					&& !deptCode.equals("")) {
				sb.append(" and vDeptCode='" + deptCode + "'");
			}
			if (projectTypeList != null && projectTypeList.size() > 0) {
				sb.append(" and cProjectType in (");
				for (int idxProjectType = 0; idxProjectType < projectTypeList
						.size(); idxProjectType++) {
					sb.append("'"
							+ projectTypeList.get(idxProjectType).charValue()
							+ "',");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(") ");
			}
			if (workSpaceDesc != null) {
				if (!workSpaceDesc.equals(""))
					sb.append(" and vWorkspaceDesc like '%" + workSpaceDesc
							+ "%'");

			}
			String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vapplicationName,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
					+ "vProjectCode,vProjectName,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,vBaseWorkFolder,"
					+ "vBasePublishFolder,cProjectType,cStatusIndi,dLastAccessedOn,iModifyBy,dToDt,dFromdt,vWorkSpaceRemark,cLockSeq,vProjectCodeName";
			rs = dataTable.getDataSet(con, Fields,
					"View_CommonWorkspaceDetail_WSList", sb.toString(), "dCreatedOn Desc");
			while (rs.next()) {
				DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
				workSpaceMstDTO.setWorkSpaceId(rs.getString("vworkspaceid"));
			if(editProject.equalsIgnoreCase("No")){
				 String isEditProject = docMgmtImpl.checkWSnodeHistory(workSpaceMstDTO.getWorkSpaceId());	
				 workSpaceMstDTO.setIsEditProject(isEditProject);
			}
				workSpaceMstDTO.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				workSpaceMstDTO.setLocationCode(rs.getString("vLocationCode"));
				workSpaceMstDTO.setLocationName(rs.getString("vLocationName"));
				workSpaceMstDTO.setDeptCode(rs.getString("vDeptCode"));
				workSpaceMstDTO.setDeptName(rs.getString("vDeptName"));
				workSpaceMstDTO.setClientCode(rs.getString("vClientCode"));
				workSpaceMstDTO.setClientName(rs.getString("vClientName"));
				workSpaceMstDTO.setProjectCode(rs.getString("vProjectCode"));
				workSpaceMstDTO.setProjectName(rs.getString("vProjectName"));
				workSpaceMstDTO.setApplicationName(rs.getString("vapplicationName"));
				workSpaceMstDTO.setLastPublishedVersion(rs.getString("vlastPublishedVersion"));
				workSpaceMstDTO.setCreatedOn(rs.getTimestamp("dcreatedOn"));
				workSpaceMstDTO.setLastAccessedBy(rs.getInt("ilastAccessedBy"));
				workSpaceMstDTO.setLastaccessedUserName(rs.getString("vusername"));
				workSpaceMstDTO.setLastAccessedOn(rs.getTimestamp("dLastAccessedOn"));
				workSpaceMstDTO.setModifyBy(rs.getInt("iModifyBy"));
				workSpaceMstDTO.setLastModifyUserName(rs.getString("vusername"));
				workSpaceMstDTO.setModifyOn(rs.getTimestamp("dmodifyon"));
				workSpaceMstDTO.setBaseWorkFolder(rs.getString("vbaseWorkFolder"));
				workSpaceMstDTO.setBasePublishFolder(rs.getString("vbasePublishFolder"));
				workSpaceMstDTO.setProjectType(rs.getString("cProjectType").charAt(0));
				workSpaceMstDTO.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				workSpaceMstDTO.setToDate(rs.getTimestamp("dToDt"));
				workSpaceMstDTO.setFromDate(rs.getTimestamp("dFromdt"));
				workSpaceMstDTO.setRemark(rs.getString("vWorkSpaceRemark"));
				workSpaceMstDTO.setLockSeq(rs.getString("cLockSeq").charAt(0));
				workSpaceMstDTO.setProjectCodeName(rs.getString("vProjectCodeName"));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(workSpaceMstDTO.getModifyOn(),locationName,countryCode);
					workSpaceMstDTO.setISTDateTime(time.get(0));
				}
				else{					
					time = docMgmtImpl.TimeZoneConversion(workSpaceMstDTO.getModifyOn(),locationName,countryCode);
					workSpaceMstDTO.setISTDateTime(time.get(0));
					workSpaceMstDTO.setESTDateTime(time.get(1));
				}
				if (userType == null || userType.equals("")
						|| userType.equals("WA"))
					userWorkspaceDetail.addElement(workSpaceMstDTO);
				else {
					Calendar calendarToday = CalendarUtils
							.dateToCalendar(new Date());

					Calendar clToDate = CalendarUtils.stringToCalendar(
							workSpaceMstDTO.getToDate().toString(),
							"yyyy-MM-dd");
					int diffInDaysTo = CalendarUtils.dateDifferenceInDays(
							calendarToday, clToDate);

					Calendar clFromDate = CalendarUtils.stringToCalendar(
							workSpaceMstDTO.getFromDate().toString(),
							"yyyy-MM-dd");
					int diffInDaysFrom = CalendarUtils.dateDifferenceInDays(
							calendarToday, clFromDate);

					if (calendarToday != null && clToDate != null
							&& diffInDaysTo >= 0 && diffInDaysFrom <= 0) {
						userWorkspaceDetail.addElement(workSpaceMstDTO);
					}
				}
				workSpaceMstDTO = null;
			}

			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userWorkspaceDetail;
	}

	/*
	 * private boolean matchFound(String workspacedesc,String likewiseSearch) {
	 * boolean bool=false; if(likewiseSearch==null || likewiseSearch.length()==0
	 * )return true; if(likewiseSearch.indexOf("~")!=-1) { String
	 * array[]=likewiseSearch.toLowerCase().split("~");
	 * bool=workspacedesc.toLowerCase().startsWith(array[0]); if(bool) { for(int
	 * i=1;i<array.length;i++) {
	 * bool=(workspacedesc.toLowerCase().indexOf(array[
	 * i])>workspacedesc.toLowerCase().indexOf(array[i-1])); if(!bool) { return
	 * false; } } } } else
	 * bool=(workspacedesc.toLowerCase().startsWith(likewiseSearch
	 * .toLowerCase()));
	 * 
	 * return bool; }
	 */
	public Vector<DTOWorkSpaceMst> getUserWorkspace(int userGroupCode,
			int userCode) {
		Vector<DTOWorkSpaceMst> userWorkspaceDetail = new Vector<DTOWorkSpaceMst>();

		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
					+ "vProjectCode,vProjectName,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,dLastAccessedOn,vBaseWorkFolder,"
					+ "vBasePublishFolder,cProjectType,iModifyBy,dToDt,vWorkSpaceRemark";
			String Where = " iuserCode=" + userCode	+ " and iUserGroupCode=" + userGroupCode
					+ " and cstatusIndi<>'D' and cStatusIndi<>'A' and cstatusIndi<>'V' and cProjectType<>'D' and cStatusIndi<>'L' "
					+ "and WorkspaceUserStatusIndi<>'D' and getdate()>=dfromdt and getdate()<=dtodt ";
			
			rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail", Where, "");
			while (rs.next()) {
				DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
				workSpaceMstDTO.setWorkSpaceId(rs.getString("vWorkspaceId"));
				workSpaceMstDTO.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				workSpaceMstDTO.setLocationCode(rs.getString("vLocationCode"));
				workSpaceMstDTO.setLocationName(rs.getString("vLocationName"));
				workSpaceMstDTO.setDeptCode(rs.getString("vDeptCode"));
				workSpaceMstDTO.setDeptName(rs.getString("vDeptName"));
				workSpaceMstDTO.setClientCode(rs.getString("vClientCode"));
				workSpaceMstDTO.setClientName(rs.getString("vClientName"));
				workSpaceMstDTO.setProjectName(rs.getString("vProjectName"));
				workSpaceMstDTO.setProjectCode(rs.getString("vProjectCode"));
				workSpaceMstDTO.setLastPublishedVersion(rs.getString("vlastPublishedVersion"));
				workSpaceMstDTO.setCreatedOn(rs.getTimestamp("dcreatedOn"));
				workSpaceMstDTO.setLastAccessedBy(rs.getInt("ilastAccessedBy"));
				workSpaceMstDTO.setLastaccessedUserName(rs.getString("vusername"));
				workSpaceMstDTO.setLastAccessedOn(rs.getTimestamp("dlastaccessedon"));
				workSpaceMstDTO.setModifyBy(rs.getInt("iModifyBy"));
				workSpaceMstDTO.setLastModifyUserName(rs.getString("vusername"));
				workSpaceMstDTO.setModifyOn(rs.getTimestamp("dmodifyon"));
				workSpaceMstDTO.setBaseWorkFolder(rs.getString("vbaseWorkFolder"));
				workSpaceMstDTO.setBasePublishFolder(rs.getString("vbasePublishFolder"));
				workSpaceMstDTO.setProjectType(rs.getString("cProjectType").charAt(0));
				workSpaceMstDTO.setToDate(rs.getTimestamp("dToDt"));
				workSpaceMstDTO.setFromDate(new Timestamp(new java.util.Date().getTime()));
				workSpaceMstDTO.setRemark(rs.getString("vWorkSpaceRemark"));
				userWorkspaceDetail.addElement(workSpaceMstDTO);
				workSpaceMstDTO = null;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return userWorkspaceDetail;
	}
	
	public Vector<DTOWorkSpaceMst> getUserWorkspaceList(int userGroupCode,int userCode) {
		Vector<DTOWorkSpaceMst> userWorkspaceDetail = new Vector<DTOWorkSpaceMst>();

		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
					+ "vProjectCode,vProjectName,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,dLastAccessedOn,vBaseWorkFolder,"
					+ "vBasePublishFolder,cProjectType,iModifyBy,dToDt,vWorkSpaceRemark";
			String Where = " iuserCode=" + userCode	+ " and iUserGroupCode=" + userGroupCode
					+ " and cstatusIndi<>'D' and cStatusIndi<>'A' and cstatusIndi<>'V' and cProjectType<>'D' and cStatusIndi<>'L' "
					+ "and WorkspaceUserStatusIndi<>'D' and getdate()>=dfromdt and getdate()<=dtodt ";
			
			rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail_WSList", Where, "dModifyOn desc");
			while (rs.next()) {
				DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
				workSpaceMstDTO.setWorkSpaceId(rs.getString("vWorkspaceId"));
				workSpaceMstDTO.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				workSpaceMstDTO.setLocationCode(rs.getString("vLocationCode"));
				workSpaceMstDTO.setLocationName(rs.getString("vLocationName"));
				workSpaceMstDTO.setDeptCode(rs.getString("vDeptCode"));
				workSpaceMstDTO.setDeptName(rs.getString("vDeptName"));
				workSpaceMstDTO.setClientCode(rs.getString("vClientCode"));
				workSpaceMstDTO.setClientName(rs.getString("vClientName"));
				workSpaceMstDTO.setProjectName(rs.getString("vProjectName"));
				workSpaceMstDTO.setProjectCode(rs.getString("vProjectCode"));
				workSpaceMstDTO.setLastPublishedVersion(rs.getString("vlastPublishedVersion"));
				workSpaceMstDTO.setCreatedOn(rs.getTimestamp("dcreatedOn"));
				workSpaceMstDTO.setLastAccessedBy(rs.getInt("ilastAccessedBy"));
				workSpaceMstDTO.setLastaccessedUserName(rs.getString("vusername"));
				workSpaceMstDTO.setLastAccessedOn(rs.getTimestamp("dlastaccessedon"));
				workSpaceMstDTO.setModifyBy(rs.getInt("iModifyBy"));
				workSpaceMstDTO.setLastModifyUserName(rs.getString("vusername"));
				workSpaceMstDTO.setModifyOn(rs.getTimestamp("dmodifyon"));
				workSpaceMstDTO.setBaseWorkFolder(rs.getString("vbaseWorkFolder"));
				workSpaceMstDTO.setBasePublishFolder(rs.getString("vbasePublishFolder"));
				workSpaceMstDTO.setProjectType(rs.getString("cProjectType").charAt(0));
				workSpaceMstDTO.setToDate(rs.getTimestamp("dToDt"));
				workSpaceMstDTO.setFromDate(new Timestamp(new java.util.Date().getTime()));
				workSpaceMstDTO.setRemark(rs.getString("vWorkSpaceRemark"));
				userWorkspaceDetail.addElement(workSpaceMstDTO);
				workSpaceMstDTO = null;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return userWorkspaceDetail;
	}
	

	public Vector<DTOWorkSpaceMst> getTimelineUserWorkspace(int userGroupCode,
			int userCode) {
		Vector<DTOWorkSpaceMst> userWorkspaceDetail = new Vector<DTOWorkSpaceMst>();

		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			String Fields = "Distinct wud.WorkspaceId,wud.WorkspaceDesc,wud.CreatedOn";
			String from="view_workspaceUserDetail as wud inner join workspacenodeattrdetail as wndad on wud.WorkspaceId = wndad.vWorkspaceId and wndad.iNodeId=1 and wndad.vAttrName='Project Start Date'";
			String Where = " wud.userCode=" + userCode	+ " and wud.userGroupcode="	+ userGroupCode
					+ " and wud.wsstatusindi<>'D' and wud.ProjectType <> 'D' and wud.statusindi<>'D' and wud.statusindi<>'V' and"
					+ " getdate()>=wud.fromdt and getdate()<=wud.todt";
			
			rs = dataTable.getDataSet(con, Fields,from, Where, "wud.CreatedOn desc");
			while (rs.next()) {
				DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
				workSpaceMstDTO.setWorkSpaceId(rs.getString("WorkspaceId"));
				workSpaceMstDTO.setWorkSpaceDesc(rs.getString("WorkspaceDesc"));
				
				userWorkspaceDetail.addElement(workSpaceMstDTO);
				workSpaceMstDTO = null;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return userWorkspaceDetail;
	}
	public Vector<DTOWorkSpaceMst> getAllWorkspace() {
		Vector<DTOWorkSpaceMst> WorkspaceDetail = new Vector<DTOWorkSpaceMst>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
					+ "vProjectCode,vProjectName,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,dLastAccessedOn,vBaseWorkFolder,"
					+ "vBasePublishFolder,cProjectType,iModifyBy,dToDt,vWorkSpaceRemark,cStatusIndi";
			
			ResultSet rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail", "cStatusIndi<>'D'", "");
			while (rs.next()) {
				DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
				workSpaceMstDTO.setWorkSpaceId(rs.getString("vWorkspaceId"));
				workSpaceMstDTO.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				workSpaceMstDTO.setLocationCode(rs.getString("vLocationCode"));
				workSpaceMstDTO.setLocationName(rs.getString("vLocationName"));
				workSpaceMstDTO.setDeptCode(rs.getString("vDeptCode"));
				workSpaceMstDTO.setDeptName(rs.getString("vDeptName"));
				workSpaceMstDTO.setClientCode(rs.getString("vClientCode"));
				workSpaceMstDTO.setClientName(rs.getString("vClientName"));
				workSpaceMstDTO.setProjectName(rs.getString("vProjectName"));
				workSpaceMstDTO.setProjectCode(rs.getString("vProjectCode"));
				workSpaceMstDTO.setLastPublishedVersion(rs.getString("vlastPublishedVersion"));
				workSpaceMstDTO.setCreatedOn(rs.getTimestamp("dcreatedOn"));
				workSpaceMstDTO.setLastAccessedBy(rs.getInt("ilastAccessedBy"));
				workSpaceMstDTO.setLastaccessedUserName(rs.getString("vusername"));
				workSpaceMstDTO.setLastAccessedOn(rs.getTimestamp("dlastaccessedon"));
				workSpaceMstDTO.setModifyBy(rs.getInt("iModifyBy"));
				workSpaceMstDTO.setLastModifyUserName(rs.getString("vusername"));
				workSpaceMstDTO.setModifyOn(rs.getTimestamp("dmodifyon"));
				workSpaceMstDTO.setBaseWorkFolder(rs.getString("vbaseWorkFolder"));
				workSpaceMstDTO.setBasePublishFolder(rs.getString("vbasePublishFolder"));
				workSpaceMstDTO.setProjectType(rs.getString("cProjectType").charAt(0));
				workSpaceMstDTO.setToDate(rs.getTimestamp("dToDt"));
				workSpaceMstDTO.setFromDate(new Timestamp(new java.util.Date().getTime()));
				workSpaceMstDTO.setRemark(rs.getString("vWorkSpaceRemark"));
				workSpaceMstDTO.setStatusIndi(rs.getString("cStatusIndi").trim().charAt(0));

				WorkspaceDetail.addElement(workSpaceMstDTO);
				workSpaceMstDTO = null;

			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return WorkspaceDetail;
	}

	public Vector<Object[]> getWorkspaceDesc(String wsId) {
		Vector<Object[]> workspaceVector = new Vector<Object[]>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationName,vDeptName,vClientName,vProjectName,vdoctypename,vtemplateid,"
							+ "vtemplatedesc,vbaseWorkFolder,vbasepublishFolder,ilasttranno";
			
			ResultSet rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail","vworkspaceid='" + wsId + "'", "");
			while (rs.next()) {
				Object[] record = { rs.getString("vWorkspaceId"), // vWorkSpaceId
						rs.getString("vWorkspaceDesc"), // vWorkSpaceDesc
						rs.getString("vLocationName"), // vLocationName
						rs.getString("vDeptName"), // vDeptName
						rs.getString("vClientName"), // vClientName
						rs.getString("vProjectName"), // vProjectName
						rs.getString("vdoctypename"), // vDocTypeName
						rs.getString("vtemplateid"), // vTemplateId
						rs.getString("vtemplatedesc"), // vTemplateDesc
						rs.getString("vbaseWorkFolder"), // vBaseWorkFolder
						rs.getString("vbasepublishFolder"), // vBasePublishFolder
						rs.getString("ilasttranno"), // iLastTranNo

				};
				workspaceVector.addElement(record);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return workspaceVector;
	}
	public Vector<Object[]> getWorkspaceDescList(String wsId) {
		Vector<Object[]> workspaceVector = new Vector<Object[]>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationName,vDeptName,vClientName,vProjectName,vdoctypename,vtemplateid,"
							+ "vtemplatedesc,vbaseWorkFolder,vbasepublishFolder,ilasttranno";
			
			ResultSet rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail_WSList","vworkspaceid='" + wsId + "'", "");
			while (rs.next()) {
				Object[] record = { rs.getString("vWorkspaceId"), // vWorkSpaceId
						rs.getString("vWorkspaceDesc"), // vWorkSpaceDesc
						rs.getString("vLocationName"), // vLocationName
						rs.getString("vDeptName"), // vDeptName
						rs.getString("vClientName"), // vClientName
						rs.getString("vProjectName"), // vProjectName
						rs.getString("vdoctypename"), // vDocTypeName
						rs.getString("vtemplateid"), // vTemplateId
						rs.getString("vtemplatedesc"), // vTemplateDesc
						rs.getString("vbaseWorkFolder"), // vBaseWorkFolder
						rs.getString("vbasepublishFolder"), // vBasePublishFolder
						rs.getString("ilasttranno"), // iLastTranNo

				};
				workspaceVector.addElement(record);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return workspaceVector;
	}
	public Vector<DTOWorkSpaceMst> getProjectDetailHistory(String wsId) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceMst> wsNodeDetail = new Vector<DTOWorkSpaceMst>();
		ResultSet rs = null;
		Connection con = null;
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		try {
			con = ConnectionManager.ds.getConnection();

			String Where = "vWorkspaceId = " + wsId;
			rs = dataTable.getDataSet(con, "*", "View_workspaceMstHistory", Where,"");

			while (rs.next()) {
				DTOWorkSpaceMst dto = new DTOWorkSpaceMst();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId")); 
				dto.setTranNo(rs.getInt("iTranNo")); 
				dto.setWorkSpaceDesc(rs.getString("vWorkspaceDesc")); 
				dto.setApplicationName(rs.getString("vapplicationName"));
				dto.setLocationCode(rs.getString("vLocationCode"));
				dto.setLocationName(rs.getString("vLocationName"));
				dto.setDeptCode(rs.getString("vDeptCode"));
				dto.setDeptName(rs.getString("vDeptName"));
				dto.setClientCode(rs.getString("vClientCode"));
				dto.setClientName(rs.getString("vClientName"));
				dto.setProjectCode(rs.getString("vProjectCode"));
				dto.setProjectName(rs.getString("vProjectName"));
				dto.setDocTypeCode(rs.getString("vDoctypeCode")); 
				dto.setTemplateId(rs.getString("vTemplateId"));
				dto.setTemplateDesc(rs.getString("vTemplateDesc"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder")); 
				dto.setBasePublishFolder(rs.getString("vBasePublishFolder"));
				dto.setLastTranNo(rs.getInt("iLastTranNo"));
				dto.setLastPublishedVersion(rs.getString("vLastPublishedVersion"));
				dto.setCreatedBy(rs.getInt("iCreatedBy"));
				dto.setCreatedOn(rs.getTimestamp("dCreatedOn"));
				dto.setLastAccessedBy(rs.getInt("ilastaccessedBy"));
				dto.setLastAccessedOn(rs.getTimestamp("dLastAccessedOn")); 
				dto.setProjectType(rs.getString("cProjectType").charAt(0));
				//dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setUserName(rs.getString("vLoginName"));
				dto.setLoginName(rs.getString("vLoginId"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setProjectCodeName(rs.getString("vProjectCodeName"));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); 
				dto.setRemark(rs.getString("vRemark"));
				wsNodeDetail.addElement(dto);
				dto = null;
			}

			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return wsNodeDetail;
	}

	public boolean workspaceNameExist(String WorkspaceDesc) {
		boolean flag = false;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "vWorkspaceId","workspacemst", "vWorkspaceDesc = '" + WorkspaceDesc
							+ "' AND cStatusIndi <> 'D' ", "");
			if (rs.next()) {
				flag = true;
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 
	 * @param dto
	 * @param userCode
	 * @param typeSelection
	 * @param templateId
	 * @param Mode
	 *            1 -Insert , 2 - Update , 3- Delete(Update statusIndi)
	 * @return
	 */
	public boolean AddUpdateWorkspace(DTOWorkSpaceMst dto, int userCode,
			String typeSelection, String templateId, int Mode) {
		boolean ret = false;

		// Set Data For Insert Mode
		if (Mode == 1) {
			int tranNo = 1;
			String lastPublishedVersion = "-999";
			PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
			String workspaceFolder = propertyInfo.getValue("BaseWorkFolder");
			String publicationFolder = propertyInfo.getValue("BasePublishFolder");
			
		
			String NeeSpublicationFolder = propertyInfo.getValue("NeeSBasePublishFolder");
			String vNeeSpublicationFolder = propertyInfo.getValue("vNeeSBasePublishFolder");
			String FosunChanges=propertyInfo.getValue("FosunCustomization");
			dto.setLastTranNo(tranNo);
			dto.setLastPublishedVersion(lastPublishedVersion);
			dto.setBaseWorkFolder(workspaceFolder.trim());
			
			/*if(dto.getProjectCode().equalsIgnoreCase("0004")){
				
				System.out.println("NeeS Call");
			
				dto.setBasePublishFolder(NeeSpublicationFolder.trim());	
			}
			else if(dto.getProjectCode().equalsIgnoreCase("0005")){
				
				System.out.println("vNeeS Call");
			
				dto.setBasePublishFolder(vNeeSpublicationFolder.trim());	
			}
			else{
				System.out.println("NeeS Not Call");
				if(FosunChanges.equalsIgnoreCase("yes")){
					dto.setBasePublishFolder(publicationFolder.trim()+"/"+dto.getClientName());
				}
				else{*/
					dto.setBasePublishFolder(publicationFolder.trim());
			//	}
				
		//	}

		}
		UserMst userMst = new UserMst();
		DTOUserMst projectUser = userMst.getUserByCode(userCode);
		int waUsrGrpCode = projectUser.getUserGroupCode();
		// int modifyBy = userCode;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con.prepareCall("{call Insert_Workspace(?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,? ,?,?,?,?,?)}");

			cs.setString(1, dto.getWorkSpaceId());
			cs.setString(2, dto.getWorkSpaceDesc());
			cs.setString(3, dto.getLocationCode());
			cs.setString(4, dto.getDeptCode());
			cs.setString(5, dto.getClientCode());
			cs.setString(6, dto.getProjectCode());
			cs.setString(7, dto.getDocTypeCode());
			cs.setString(8, dto.getTemplateId());
			cs.setString(9, dto.getTemplateDesc());
			cs.setString(10, dto.getBaseWorkFolder());
			cs.setString(11, dto.getBasePublishFolder());
			cs.setInt(12, dto.getLastTranNo());
			cs.setString(13, dto.getLastPublishedVersion());
			cs.setInt(14, dto.getModifyBy());
			cs.setInt(15, dto.getModifyBy());
			cs.setString(16, typeSelection);
			cs.setInt(17, dto.getModifyBy());
			cs.setString(18, Character.toString('N'));
			cs.setInt(19, waUsrGrpCode); // for user group code entry
			cs.setInt(20, userCode);// Created For (Parameter Added By Ashmak Shah)
			cs.setString(21, dto.getProjectCodeName());
			cs.setInt(22, Mode); // Mode 1:Insert, Mode 2:Update, Mode 3:Update Mode 4:Archive Mode 5:UnArchive
			// StatusIndi to 'D'
			if (dto.getRemark() == null)
				cs.setString(23, "");
			else
				cs.setString(23, dto.getRemark());
			
			cs.setString(24, dto.getApplicationCode());
			cs.setString(25, dto.getVerion());
			cs.execute();

			cs.close();
			con.close();
			ret = true;
		} catch (SQLException e) {
			ret = false;
			e.printStackTrace();
		}
		return ret;

	}

	// Added on 15/05/2013 by butani vijay for counting leaf Node
	public int getTotalLeafNode(String wsId) {
		int leafnode = 0;
		try {
			Connection con = ConnectionManager.ds.getConnection();

			ResultSet rs = dataTable
					.getDataSet(
							con,
							"count(iNodeId) as leafNode",
							"workspacenodedetail",
							"vWorkSpaceId="
									+ wsId
									+ " and  iNodeId Not  in (select iParentNodeId from workspacenodedetail where vWorkSpaceId="
									+ wsId + ")", "");
			if (rs.next()) {
				leafnode = rs.getInt(1);
				System.out.println("Total Leaf Node="
						+ rs.getString("leafNode"));

			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return leafnode;
	}

	public int[] getAllNodeStatusCount(String wsId) {
		int nodehistory[] = new int[4];

		int underreviewed = 0;
		int approved = 0, created = 0, authorized = 0;
		ResultSet rs1 = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();

			ResultSet rs = dataTable.getDataSet(con, "Distinct iNodeId",
					"View_WorkspaceNodeAllHistory", "vWorkSpaceId=" + wsId, "");
			while (rs.next()) {
				rs1 = dataTable
						.getDataSet(
								con,
								"iStageId",
								"View_WorkspaceNodeAllHistory",
								"vWorkSpaceId="
										+ wsId
										+ " and iNodeId="
										+ rs.getInt("iNodeId")
										+ " and iTranNo=(select MAX(iTranNo) from View_WorkspaceNodeAllHistory where vWorkSpaceId="
										+ wsId + " and iNodeId="
										+ rs.getInt("iNodeId") + " ) ", "");
				while (rs1.next()) {

					// 10= created
					// 20 = review
					// 30 = authorized
					// 100= approved;

					if (rs1.getInt("iStageId") == 10) {
						created++;
					} else if (rs1.getInt("iStageId") == 20) {
						underreviewed++;
					} else if (rs1.getInt("iStageId") == 30) {
						authorized++;
					} else if (rs1.getInt("iStageId") == 100) {
						approved++;
					}
					System.out.println("Node Found-" + rs1.getInt("iStageId"));
				}
				// leafnode[count++]=rs.getInt("iStageId");

			}
			rs.close();
			rs1.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		nodehistory[0] = created;
		nodehistory[1] = underreviewed;
		nodehistory[2] = authorized;
		nodehistory[3] = approved;

		return nodehistory;
	}

	public DTOWorkSpaceMst getWorkSpaceDetail(String wsId) {
		DTOWorkSpaceMst workSpaceMst = new DTOWorkSpaceMst();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
					+ "vProjectCode,vProjectName,vDoctypeCode,vdoctypename,vTemplateId,vTemplateDesc,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,dLastAccessedOn,vBaseWorkFolder,"
					+ "vBasePublishFolder,cProjectType,iLastTranNo,iCreatedBy,iModifyBy,cStatusIndi,vWorkSpaceRemark";

			ResultSet rs = dataTable.getDataSet(con, Fields,
					"View_CommonWorkspaceDetail", "vWorkspaceId=" + wsId, "");
			if (rs.next()) {
				workSpaceMst.setWorkSpaceId(rs.getString("vWorkspaceId"));
				workSpaceMst.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				workSpaceMst.setLocationCode(rs.getString("vLocationCode"));
				workSpaceMst.setDeptCode(rs.getString("vDeptCode"));
				workSpaceMst.setClientCode(rs.getString("vClientCode"));
				workSpaceMst.setProjectCode(rs.getString("vProjectCode"));
				workSpaceMst.setDocTypeCode(rs.getString("vDoctypeCode"));
				workSpaceMst.setTemplateId(rs.getString("vTemplateId"));
				workSpaceMst.setTemplateDesc(rs.getString("vTemplateDesc"));
				workSpaceMst.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				workSpaceMst.setBasePublishFolder(rs.getString("vBasePublishFolder"));
				workSpaceMst.setLastTranNo(rs.getInt("iLastTranNo"));
				workSpaceMst.setLastPublishedVersion(rs.getString("vLastPublishedVersion"));
				workSpaceMst.setCreatedBy(rs.getInt("iCreatedBy"));
				workSpaceMst.setCreatedOn(rs.getTimestamp("dCreatedOn"));
				workSpaceMst.setLastAccessedBy(rs.getInt("ilastaccessedBy"));
				workSpaceMst.setLastAccessedOn(rs.getTimestamp("dLastAccessedOn"));		
				workSpaceMst.setProjectType(rs.getString("cProjectType").charAt(0));
				workSpaceMst.setModifyBy(rs.getInt("iModifyBy"));
				workSpaceMst.setModifyOn(rs.getTimestamp("dModifyOn"));
				workSpaceMst.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				workSpaceMst.setLocationName(rs.getString("vLocationName"));
				workSpaceMst.setDeptName(rs.getString("vDeptName"));
				workSpaceMst.setClientName(rs.getString("vClientName"));
				workSpaceMst.setProjectName(rs.getString("vProjectName"));
				workSpaceMst.setRemark(rs.getString("vWorkSpaceRemark"));

			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return workSpaceMst;
	}
	public DTOWorkSpaceMst getWorkSpaceDetailWSList(String wsId) {
		DTOWorkSpaceMst workSpaceMst = new DTOWorkSpaceMst();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
					+ "vProjectCode,vProjectName,vDoctypeCode,vdoctypename,vTemplateId,vTemplateDesc,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,dLastAccessedOn,vBaseWorkFolder,"
					+ "vBasePublishFolder,cProjectType,iLastTranNo,iCreatedBy,iModifyBy,cStatusIndi,vWorkSpaceRemark";

			ResultSet rs = dataTable.getDataSet(con, Fields,
					"View_CommonWorkspaceDetail_WSList", "vWorkspaceId=" + wsId, "");
			if (rs.next()) {
				workSpaceMst.setWorkSpaceId(rs.getString("vWorkspaceId"));
				workSpaceMst.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				workSpaceMst.setLocationCode(rs.getString("vLocationCode"));
				workSpaceMst.setDeptCode(rs.getString("vDeptCode"));
				workSpaceMst.setClientCode(rs.getString("vClientCode"));
				workSpaceMst.setProjectCode(rs.getString("vProjectCode"));
				workSpaceMst.setDocTypeCode(rs.getString("vDoctypeCode"));
				workSpaceMst.setTemplateId(rs.getString("vTemplateId"));
				workSpaceMst.setTemplateDesc(rs.getString("vTemplateDesc"));
				workSpaceMst.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				workSpaceMst.setBasePublishFolder(rs.getString("vBasePublishFolder"));
				workSpaceMst.setLastTranNo(rs.getInt("iLastTranNo"));
				workSpaceMst.setLastPublishedVersion(rs.getString("vLastPublishedVersion"));
				workSpaceMst.setCreatedBy(rs.getInt("iCreatedBy"));
				workSpaceMst.setCreatedOn(rs.getTimestamp("dCreatedOn"));
				workSpaceMst.setLastAccessedBy(rs.getInt("ilastaccessedBy"));
				workSpaceMst.setLastAccessedOn(rs.getTimestamp("dLastAccessedOn"));		
				workSpaceMst.setProjectType(rs.getString("cProjectType").charAt(0));
				workSpaceMst.setModifyBy(rs.getInt("iModifyBy"));
				workSpaceMst.setModifyOn(rs.getTimestamp("dModifyOn"));
				workSpaceMst.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				workSpaceMst.setLocationName(rs.getString("vLocationName"));
				workSpaceMst.setDeptName(rs.getString("vDeptName"));
				workSpaceMst.setClientName(rs.getString("vClientName"));
				workSpaceMst.setProjectName(rs.getString("vProjectName"));
				workSpaceMst.setRemark(rs.getString("vWorkSpaceRemark"));

			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return workSpaceMst;
	}

	public int getTranNo(String wsId) {
		int tranNo = 0;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "iLastTranNo",
					"workspaceMst", "vWorkspaceId =" + wsId, "");
			if (rs.next()) {
				tranNo = rs.getInt("iLastTranNo");// lastTranNo
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tranNo;
	}

	public synchronized int getNewTranNo(String wsId) {
		int tranNo = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con.prepareCall("{call Proc_newTranNo(?,?)}");
			cs.setString(1, wsId);
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.execute();
			tranNo = cs.getInt(2);
			cs.close();
			con.close();
			return tranNo;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tranNo;
	}

	public synchronized int getNewTranNoForAttachment(String wsId,int nodeId) {
		int tranNo = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con.prepareCall("{call Proc_newTranNoForAttachment(?,?,?)}");
			cs.setString(1, wsId);
			cs.setInt(2, nodeId);
			cs.registerOutParameter(3, java.sql.Types.INTEGER);
			cs.execute();
			tranNo = cs.getInt(3);
			cs.close();
			con.close();
			return tranNo;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tranNo;
	}
	
	public boolean updatePublishedVersion(String wsId) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con.prepareCall("{call Proc_PublishVersion(?)}");
			cs.setString(1, wsId);
			cs.execute();
			cs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean updatePublishedVersionForNeeS(String wsId,String lastPublishedVersion) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con.prepareCall("{call Proc_PublishVersionForNeeS(?,?)}");
			cs.setString(1, wsId);
			cs.setString(2,lastPublishedVersion);
			cs.execute();
			cs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
}
	public boolean updatePublishedVersionForvNeeS(String wsId,String lastPublishedVersion) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con.prepareCall("{call Proc_PublishVersionForvNeeS(?,?)}");
			cs.setString(1, wsId);
			cs.setString(2,lastPublishedVersion);
			cs.execute();
			cs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
}

	public Vector<Object[]> getFolderByWorkSpaceId(String workSpaceId) {
		Vector<Object[]> workSpaceFolder = new Vector<Object[]>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con,"Distinct vBaseWorkFolder,vBasePublishFolder,vLastPublishedVersion",
									"View_CommonWorkspaceDetail", "vworkspaceid='" + workSpaceId + "'", "");
			while (rs.next()) {
				Object[] record = { 
						rs.getString("vbaseWorkFolder"),
						rs.getString("vbasepublishFolder"),
						rs.getString("vlastPublishedVersion") };

					workSpaceFolder.addElement(record);
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return workSpaceFolder;
	}

	public Vector<DTOWorkSpaceMst> getWorkspaceDetailByProjectType() {

		Vector<DTOWorkSpaceMst> bpmProjVector = new Vector<DTOWorkSpaceMst>();
		int iuserCode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("userid").toString());
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable
					.getDataSet(
							con,
							"Distinct vWorkspaceId,vWorkspaceDesc,vlocationcode,vclientcode,cprojectType,dfromdt,dtodt",
							"view_Commonworkspacedetail",
							"(cprojectType = 'E' or cProjectType = 'P' or cProjectType = 'M') and cStatusIndi<>'D' and WorkspaceUserStatusIndi<>'D' and iUserCode="
									+ iuserCode, "");
			while (rs.next()) {
				DTOWorkSpaceMst dto = new DTOWorkSpaceMst();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				dto.setLocationCode(rs.getString("vlocationcode"));
				dto.setClientCode(rs.getString("vclientcode"));
				dto.setProjectType(rs.getString("cprojectType").charAt(0));
				dto.setFromDate(rs.getTimestamp("dfromdt"));
				dto.setToDate(rs.getTimestamp("dtodt"));
				bpmProjVector.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bpmProjVector;
	}

	public Vector<DTOWorkSpaceMst> getWorkspaceDetailByLocDept(
			String locationCode, String deptCode) {

		Vector<DTOWorkSpaceMst> data = new Vector<DTOWorkSpaceMst>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con,
					"vWorkspaceId, vWorkspaceDesc", "WorkspaceMst",
					"vLocationCode = '" + locationCode + "' and vDeptCode= '"
							+ deptCode + "' and cStatusIndi<>'D'", "");
			while (rs.next()) {
				DTOWorkSpaceMst dto = new DTOWorkSpaceMst();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));// workspaceId
				dto.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));// workspaceName
				data.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public Vector<DTOWorkSpaceMst> getDocumentPropertiesForReport(
			DTOWorkSpaceMst dto) {

		Vector<DTOWorkSpaceMst> data = new Vector<DTOWorkSpaceMst>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call proc_GetDocumentPropertiesForReport(?,?,?)}");
			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			proc.setInt(3, dto.getMaxTranNo());
			ResultSet rs = proc.executeQuery();
			if (!rs.next()) {
				// Do nothing.
			}

			while (rs.next()) {

				DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
				workSpaceMstDTO.setWorkSpaceDesc(rs.getString("workspacedesc"));
				workSpaceMstDTO.setLocationName(rs.getString("locationName"));
				workSpaceMstDTO.setClientName(rs.getString("clientName"));
				workSpaceMstDTO.setDeptName(rs.getString("deptName"));
				workSpaceMstDTO.setNodeDisplayName(rs.getString("nodeDisplayName"));
				workSpaceMstDTO.setBaseWorkFolder(rs.getString("baseWorkFolder"));
				workSpaceMstDTO.setFolderName(rs.getString("folderName"));
				workSpaceMstDTO.setFileName(rs.getString("fileName"));
				workSpaceMstDTO.setUserDefinedVersionId(rs.getString("userDefineVersionId"));
				workSpaceMstDTO.setFileVersionId(rs.getString("fileVersionId"));
				workSpaceMstDTO.setNodeStatusAttributeValue(rs.getString("nodestatus"));
				workSpaceMstDTO.setLoginName(rs.getString("loginName"));
				workSpaceMstDTO.setAttributeId(rs.getInt("attrId"));
				workSpaceMstDTO.setAttributeName(rs.getString("attrName"));
				workSpaceMstDTO.setWorkSpaceNodeAttrHistoryAttributeValue(rs.getString("attrvaluefrommst"));
				data.addElement(workSpaceMstDTO);
				workSpaceMstDTO = null;

			}
			rs.close();
			proc.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public Vector<DTOWorkSpaceMst> getAllWorkspaceForChangeStatus() {

		Vector<DTOWorkSpaceMst> data = new Vector<DTOWorkSpaceMst>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con,"vWorkspaceId,vWorkspaceDesc,cStatusIndi", "WorkspaceMst","", "vWorkspaceDesc");
			while (rs.next()) {
				DTOWorkSpaceMst dto = new DTOWorkSpaceMst();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				data.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public Boolean insertIntoWorkSpaceForSaveAsProject(DTOWorkSpaceMst dto) {

		boolean exist = false;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Insert_SaveWorkspaceAs(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setString(1, dto.getWorkSpaceId());
			proc.setString(2, dto.getWorkSpaceDesc());
			proc.setString(3, dto.getLocationCode());
			proc.setString(4, dto.getDeptCode());
			proc.setString(5, dto.getClientCode());
			proc.setString(6, dto.getProjectCode());
			proc.setString(7, dto.getDocTypeCode());
			proc.setInt(8, dto.getCreatedBy());
			proc.setInt(9, dto.getLastAccessedBy());
			proc.setString(10, dto.getRemark());
			proc.setString(11, Character.toString(dto.getProjectType()));
			proc.setInt(12, dto.getModifyBy());
			proc.setString(13, dto.getBaseWorkFolder());
			proc.setString(14, dto.getBasePublishFolder());
			ResultSet rs = proc.executeQuery();
			if (rs.next()) {
				exist = true;
			}
			rs.close();
			proc.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return exist;
	}

	public String getWorkspaceID(String WorkspaceDesc) {
		String wsId = "";
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "vWorkspaceId","WorkspaceMst", "vWorkspaceDesc = '" + WorkspaceDesc + "'","");
			if (rs.next()) {
				wsId = rs.getString("vWorkspaceId");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wsId;
	}

	public void deleteWorkspace(String workspaceId) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call proc_DeleteWorkspace(?)}");
			proc.setString(1, workspaceId);
			proc.execute();
			proc.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void copyPublishedStructureToWorkspace(String appNo,
			String workspaceid, int userId) {

		// System.out.println("userid:::"+userId);
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String workspaceFolder = propertyInfo.getValue("BaseWorkFolder");
		String importFolder = propertyInfo.getValue("BASE_IMPORT_FOLDER");

		File indexXml = new File(importFolder + "/" + appNo + "/0000/index.xml");

		Object[] wsDetail = (Object[]) getWorkspaceDesc(workspaceid).get(0);
		File wsFolder = new File(workspaceFolder + File.separator
				+ wsDetail[1].toString() + File.separator + workspaceid);
		wsFolder.mkdirs();
		System.out.println("Folder Created... :- " + wsFolder.getAbsolutePath());

		readXml(indexXml, wsFolder, userId);

	}

	public void readXml(File xmlfile, File wsFolder, int userId) {

		// System.out.println("readXml called.....");

		// System.out.println(userId);

		Document dom;

		// get an instance of factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// get an instance of builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			dom = db.parse(xmlfile);
			Element docEle = dom.getDocumentElement();

			NodeList nl = docEle.getElementsByTagName("leaf");

			if (nl.getLength() != 0) {

				if (nl != null && nl.getLength() > 0) {

					for (int i = 0; i < nl.getLength(); i++) {

						Element el = (Element) nl.item(i);

						String filepath = el.getAttribute("xlink:href");

						String[] nodeid = el.getAttribute("ID").split("-");

						if (filepath.endsWith(".xml")) {
							// if(filepath.equals("m1/us/us-regional.xml"))
							// {
							readXml(new File(xmlfile.getParent()
									+ File.separator + filepath), wsFolder,
									userId);
							// }
						} else {

							// System.out.println("Copying file :- "+filepath+"  for node :- "
							// + nodeid[1]);

							File nodeFile = new File(xmlfile.getParent()
									+ File.separator + filepath);

							if (nodeFile.exists()) {

								int tranNo = createFolderStruc(wsFolder
										.getAbsolutePath(), Integer
										.parseInt(nodeid[1]), nodeFile,
										wsFolder.getName());
								if (tranNo != -1) {
									// allLeafNodesCounter++;
									// insertnodehistory

									String folderPath = "/" + wsFolder.getName() + "/" + nodeid[1] + "/" + tranNo;

									DTOWorkSpaceNodeHistory workSpaceNodeHistoryDTO = new DTOWorkSpaceNodeHistory();
									workSpaceNodeHistoryDTO.setWorkSpaceId(wsFolder.getName());
									workSpaceNodeHistoryDTO.setNodeId(Integer.parseInt(nodeid[1]));
									workSpaceNodeHistoryDTO.setTranNo(tranNo);
									workSpaceNodeHistoryDTO.setFileName(nodeFile.getName());
									workSpaceNodeHistoryDTO.setFileType("");
									workSpaceNodeHistoryDTO.setFolderName(folderPath);
									workSpaceNodeHistoryDTO.setRequiredFlag('Y');
									// System.out.println("FileName:-----"+nodeFile.getName());

									// stageId
									workSpaceNodeHistoryDTO.setStageId(10);

									workSpaceNodeHistoryDTO.setRemark("");
									workSpaceNodeHistoryDTO.setModifyBy(userId);
									workSpaceNodeHistoryDTO.setStatusIndi('N');
									workSpaceNodeHistoryDTO.setDefaultFileFormat("");

									WorkSpaceNodeHistory wsnh = new WorkSpaceNodeHistory();
									wsnh.insertNodeHistory(workSpaceNodeHistoryDTO);

									updateNodeAttrHistory(wsFolder.getName(),Integer.parseInt(nodeid[1]),tranNo, userId);
									// System.out.println("File copied successfully... for node :-"+
									// nodeid[1]);

								}

							}

						}

					}

				}

			}

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public int createFolderStruc(String wsPath, int nodeId, File nodeFile,
			String workspaceid) {
		// System.out.println("createFolderStruc called.....");
		int tranNo = -1;

		try {

			File nodeFolder = new File(wsPath + File.separator + nodeId);

			if (!nodeFolder.exists()) {
				byte[] fileData = null;
				int fileSize = 0;
				if (nodeFile != null && nodeFile.exists()) {
					fileSize = new Long(nodeFile.length()).intValue();

					InputStream sourceInputStream = null;
					sourceInputStream = new FileInputStream(nodeFile);

					fileData = getBytesFromFile(nodeFile);

					nodeFolder.mkdirs();

					tranNo = getNewTranNo(workspaceid);
					if (tranNo != -1) {
						File tranFolder = new File(wsPath + File.separator
								+ nodeId + File.separator + tranNo);
						tranFolder.mkdirs();
					}

					File destiFile = new File(wsPath + File.separator + nodeId
							+ File.separator + tranNo + File.separator
							+ nodeFile.getName());
					OutputStream bos = new FileOutputStream(destiFile);

					int temp = 0;

					while ((temp = sourceInputStream
							.read(fileData, 0, fileSize)) != -1) {
						bos.write(fileData, 0, temp);
					}

					bos.close();

					sourceInputStream.close();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tranNo;
	}

	public byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	private void updateNodeAttrHistory(String wsId, int nodeId, int tranNo,
			int userCode) {

		Vector<DTOWorkSpaceNodeAttrHistory> saveFileAttr = new Vector<DTOWorkSpaceNodeAttrHistory>();

		WorkspaceNodeAttrDetail wnad = new WorkspaceNodeAttrDetail();
		Vector<DTOWorkSpaceNodeAttrDetail> fileAttr = wnad.getNodeAttrDetail(
				wsId, nodeId);
		for (int i = 0; i < fileAttr.size(); i++) {

			DTOWorkSpaceNodeAttrDetail workSpaceNodeAttrDetail = fileAttr.get(i);

			DTOWorkSpaceNodeAttrHistory workSpaceNodeAttrHistoryDTO = new DTOWorkSpaceNodeAttrHistory();

			workSpaceNodeAttrHistoryDTO.setWorkSpaceId(wsId);
			workSpaceNodeAttrHistoryDTO.setNodeId(nodeId);
			workSpaceNodeAttrHistoryDTO.setTranNo(tranNo);
			workSpaceNodeAttrHistoryDTO.setAttrId(workSpaceNodeAttrDetail.getAttrId());
			workSpaceNodeAttrHistoryDTO.setAttrValue(workSpaceNodeAttrDetail.getAttrValue());
			workSpaceNodeAttrHistoryDTO.setRemark(workSpaceNodeAttrDetail.getRemark());
			workSpaceNodeAttrHistoryDTO.setStatusIndi('N');
			workSpaceNodeAttrHistoryDTO.setModifyBy(userCode);

			saveFileAttr.add(workSpaceNodeAttrHistoryDTO);
			workSpaceNodeAttrHistoryDTO = null;
		}
		WorkSpaceNodeAttributeHistory wnah = new WorkSpaceNodeAttributeHistory();
		wnah.InsertUpdateNodeAttrHistory(saveFileAttr);

		saveFileAttr = null;

	}

	public Vector<String> getImportStructureDetail() {

		Vector<String> appNos = new Vector<String>();

		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String importFolderPath = propertyInfo.getValue("BASE_IMPORT_FOLDER");
		File impFolder = new File(importFolderPath);
		File[] list = impFolder.listFiles();
		for (int i = 0; i < list.length; i++) {
			if (list[i].isDirectory()) {
				appNos.addElement(list[i].getName());
			}
		}
		return appNos;
	}

	public String insertWorkspaceMst(DTOWorkSpaceMst dto) {
		return insertWorkspaceMst(dto, 1);
	}

	public String insertWorkspaceMst(DTOWorkSpaceMst dto, int mode) {
		String workspaceId = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Insert_WorkspaceMst(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setString(1, dto.getWorkSpaceId());
			proc.setString(2, dto.getWorkSpaceDesc());
			proc.setString(3, dto.getLocationCode());
			proc.setString(4, dto.getDeptCode());
			proc.setString(5, dto.getClientCode());
			proc.setString(6, dto.getProjectCode());
			proc.setString(7, dto.getDocTypeCode());
			proc.setString(8, dto.getTemplateId());
			proc.setString(9, dto.getTemplateDesc());
			proc.setString(10, dto.getBaseWorkFolder());
			proc.setString(11, dto.getBasePublishFolder());
			proc.setInt(12, 1);// lastTranNo
			proc.setString(13, "-999");
			proc.setInt(14, dto.getCreatedBy());
			proc.setInt(15, dto.getCreatedBy());// LastAccessedBy
			proc.setString(16, Character.toString(dto.getProjectType()));
			proc.setString(17, dto.getRemark());
			proc.setInt(18, dto.getCreatedBy());// ModifyBy
			proc.setString(19, Character.toString('N'));// StatusIndi
			proc.setInt(20, mode);// DATAOPMODE = 1 for Insert and 2 for update

			// Setting @vWorkSpaceId as OUT Parameter
			proc.registerOutParameter(1, Types.VARCHAR);
			proc.execute();
			// Get New WorkspaceId
			workspaceId = proc.getString(1);

			proc.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return workspaceId;
	}

	public String getDossierStatusAttribute(String wsid) {
		String retString = "";
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "vattrvalue","workspacenodeattrdetail",
							"vattrname='Dossier Status' and vworkspaceid='" + wsid + "'", "");
			if (rs.next())
				retString = rs.getString("vattrvalue");
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retString;
	}

	public boolean checkDossierStatusAttribute() {
		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "*", "attributemst","vattrname='Dossier Status' and vattrforindiid='0000'", "");
			if (rs.next())
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return false;
	}

	public ArrayList<DTOWorkSpaceMst> getWorkSpaceDetail(
			ArrayList<String> wsIdsList) {
		ArrayList<DTOWorkSpaceMst> workspaceMstList = new ArrayList<DTOWorkSpaceMst>();
		DTOWorkSpaceMst workSpaceMst;
		Connection con = null;
		ResultSet rs = null;
		String whrCond = "";

		if (wsIdsList.size() != 0) {
			whrCond = "vWorkspaceId IN ";
			String wsid = wsIdsList.toString();
			wsid = wsid.replaceAll(" ", "");
			wsid = wsid.replaceAll(",", "','");
			wsid = "('" + wsid.substring(1, wsid.length() - 1) + "')";
			whrCond += wsid;
		}

		System.out.println("Where -" + whrCond);

		try {
			con = ConnectionManager.ds.getConnection();
			String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
					+ "vProjectCode,vProjectName,vDoctypeCode,vdoctypename,vTemplateId,vTemplateDesc,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,dLastAccessedOn,vBaseWorkFolder,"
					+ "vBasePublishFolder,cProjectType,iLastTranNo,iCreatedBy,iModifyBy,cStatusIndi,vWorkSpaceRemark";

			rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail", whrCond, "");
			while (rs.next()) {
				workSpaceMst = new DTOWorkSpaceMst();
				workSpaceMst.setWorkSpaceId(rs.getString("vWorkspaceId"));
				workSpaceMst.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				workSpaceMst.setLocationCode(rs.getString("vLocationCode"));
				workSpaceMst.setDeptCode(rs.getString("vDeptCode"));
				workSpaceMst.setClientCode(rs.getString("vClientCode"));
				workSpaceMst.setProjectCode(rs.getString("vProjectCode"));
				workSpaceMst.setDocTypeCode(rs.getString("vDoctypeCode"));
				workSpaceMst.setTemplateId(rs.getString("vTemplateId"));
				workSpaceMst.setTemplateDesc(rs.getString("vTemplateDesc"));
				workSpaceMst.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				workSpaceMst.setBasePublishFolder(rs.getString("vBasePublishFolder"));
				workSpaceMst.setLastTranNo(rs.getInt("iLastTranNo"));
				workSpaceMst.setLastPublishedVersion(rs.getString("vLastPublishedVersion"));
				workSpaceMst.setCreatedBy(rs.getInt("iCreatedBy"));
				workSpaceMst.setCreatedOn(rs.getTimestamp("dCreatedOn"));
				workSpaceMst.setLastAccessedBy(rs.getInt("ilastaccessedBy"));
				workSpaceMst.setLastAccessedOn(rs.getTimestamp("dLastAccessedOn"));
				workSpaceMst.setProjectType(rs.getString("cProjectType").charAt(0));
				workSpaceMst.setModifyBy(rs.getInt("iModifyBy"));
				workSpaceMst.setModifyOn(rs.getTimestamp("dModifyOn"));
				workSpaceMst.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				workSpaceMst.setLocationName(rs.getString("vLocationName"));
				workSpaceMst.setDeptName(rs.getString("vDeptName"));
				workSpaceMst.setClientName(rs.getString("vClientName"));
				workSpaceMst.setProjectName(rs.getString("vProjectName"));
				workSpaceMst.setRemark(rs.getString("vWorkSpaceRemark"));
				workspaceMstList.add(workSpaceMst);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return workspaceMstList;
	}

	public ArrayList<DTOWorkSpaceMst> getWorkspaceDtByProjPublishType(
			int userCode, int userGroupCode, char projPublishType) {
		ArrayList<DTOWorkSpaceMst> workspaceMstList = new ArrayList<DTOWorkSpaceMst>();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceMst> workspaceDtl = docMgmtImpl.getUserWorkspace(
				userGroupCode, userCode);

		for (int iUserWorkspaces = 0; iUserWorkspaces < workspaceDtl.size(); iUserWorkspaces++) {
			DTOWorkSpaceMst dtoWsMst = workspaceDtl.get(iUserWorkspaces);

			if (dtoWsMst.getProjectType() == projPublishType) {
				workspaceMstList.add(dtoWsMst);
			}
		}

		return workspaceMstList;

	}

	public ArrayList<DTOWorkSpaceMst> getWorkSpaceDetailByTemplate(
			String templateId) {
		ArrayList<DTOWorkSpaceMst> workspaceMstList = new ArrayList<DTOWorkSpaceMst>();
		DTOWorkSpaceMst workSpaceMst;
		Connection con = null;
		ResultSet rs = null;
		String whrCond = "";

		if (templateId != null && templateId.trim().length() != 0) {
			whrCond = "vTemplateId = '" + templateId + "' AND ";
		}
		whrCond += "cStatusIndi <> 'D'";
		try {
			con = ConnectionManager.ds.getConnection();

			String Fields = "DISTINCT vWorkspaceId,vWorkspaceDesc,vTemplateId,vTemplateDesc,"
					+ "vBaseWorkFolder,vBasePublishFolder,iLastTranNo,vLastPublishedVersion,"
					+ "iCreatedBy,dCreatedOn,iLastAccessedBy,dLastAccessedOn,cProjectType,"
					+ "vRemark,iModifyBy,dModifyOn,cStatusIndi";

			rs = dataTable.getDataSet(con, Fields, " WorkspaceMst ", whrCond,"");
			while (rs.next()) {
				workSpaceMst = new DTOWorkSpaceMst();
				workSpaceMst.setWorkSpaceId(rs.getString("vWorkspaceId"));
				workSpaceMst.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				workSpaceMst.setTemplateId(rs.getString("vTemplateId"));
				workSpaceMst.setTemplateDesc(rs.getString("vTemplateDesc"));
				workSpaceMst.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				workSpaceMst.setBasePublishFolder(rs.getString("vBasePublishFolder"));
				workSpaceMst.setLastTranNo(rs.getInt("iLastTranNo"));
				workSpaceMst.setLastPublishedVersion(rs.getString("vLastPublishedVersion"));
				workSpaceMst.setCreatedBy(rs.getInt("iCreatedBy"));
				workSpaceMst.setCreatedOn(rs.getTimestamp("dCreatedOn"));
				workSpaceMst.setLastAccessedBy(rs.getInt("ilastaccessedBy"));
				workSpaceMst.setLastAccessedOn(rs.getTimestamp("dLastAccessedOn"));
				workSpaceMst.setProjectType(rs.getString("cProjectType").charAt(0));
				workSpaceMst.setRemark(rs.getString("vRemark"));
				workSpaceMst.setModifyBy(rs.getInt("iModifyBy"));
				workSpaceMst.setModifyOn(rs.getTimestamp("dModifyOn"));
				workSpaceMst.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				workspaceMstList.add(workSpaceMst);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return workspaceMstList;
	}

	public DTOWorkSpaceMst getWorkSpaceDetailByWorkspaceId(String workspaceId) {
		DTOWorkSpaceMst workSpaceMst = new DTOWorkSpaceMst();
		Connection con = null;
		ResultSet rs = null;
		String whrCond = "";

		if (workspaceId != null && workspaceId.trim().length() != 0) {
			whrCond = "vWorkSpaceId = '" + workspaceId + "'";
		}

		try {
			con = ConnectionManager.ds.getConnection();

			String Fields = "DISTINCT vWorkspaceId,vWorkspaceDesc,vTemplateId,vTemplateDesc,"
					+ "vBaseWorkFolder,vBasePublishFolder,iLastTranNo,vLastPublishedVersion,"
					+ "iCreatedBy,dCreatedOn,iLastAccessedBy,dLastAccessedOn,cProjectType,"
					+ "vRemark,iModifyBy,dModifyOn,cStatusIndi";

			rs = dataTable.getDataSet(con, Fields, " WorkspaceMst ", whrCond,"");
			while (rs.next()) {
				workSpaceMst.setWorkSpaceId(rs.getString("vWorkspaceId"));
				workSpaceMst.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				workSpaceMst.setTemplateId(rs.getString("vTemplateId"));
				workSpaceMst.setTemplateDesc(rs.getString("vTemplateDesc"));
				workSpaceMst.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				workSpaceMst.setBasePublishFolder(rs.getString("vBasePublishFolder"));
				workSpaceMst.setLastTranNo(rs.getInt("iLastTranNo"));
				workSpaceMst.setLastPublishedVersion(rs.getString("vLastPublishedVersion"));
				workSpaceMst.setCreatedBy(rs.getInt("iCreatedBy"));
				workSpaceMst.setCreatedOn(rs.getTimestamp("dCreatedOn"));
				workSpaceMst.setLastAccessedBy(rs.getInt("ilastaccessedBy"));
				workSpaceMst.setLastAccessedOn(rs.getTimestamp("dLastAccessedOn"));
				workSpaceMst.setProjectType(rs.getString("cProjectType").charAt(0));
				workSpaceMst.setRemark(rs.getString("vRemark"));
				workSpaceMst.setModifyBy(rs.getInt("iModifyBy"));
				workSpaceMst.setModifyOn(rs.getTimestamp("dModifyOn"));
				workSpaceMst.setStatusIndi(rs.getString("cStatusIndi")
						.charAt(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return workSpaceMst;
	}

	public String getLocationCodeById(String workspaceId) {
		Connection con;
		String countryCode = "";
		try {
			con = ConnectionManager.ds.getConnection();

			ResultSet rs = dataTable.getDataSet(con, "vLocationCode",
					"WorkspaceMst", "vWorkSpaceId = '" + workspaceId + "'", "");
			while (rs.next()) {
				countryCode = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return countryCode;
	}

	public void updateLastPublishedVersion(String workspaceId,
			String latestSequence) {
		DataTable dataTable = new DataTable();
		Connection con;

		try {
			con = ConnectionManager.ds.getConnection();
			dataTable.executeDMLQuery(con,"Update workspacemst set vLastPublishedVersion = '" + latestSequence + "' where vWorkSpaceId = '"
										+ workspaceId + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateWorkspaceMstAccessedDate(String wsid, int ugrpcode,
			int ucode) {

		Connection con = null;
		CallableStatement proc = null;
		try {
			con = ConnectionManager.ds.getConnection();
			proc = con.prepareCall("{call Proc_UpdateWorkspaceLastAccessedDate (?,?,?)}");
			proc.setString(1, wsid);
			proc.setInt(2, ugrpcode);
			proc.setInt(3, ucode);
			proc.execute();

		}

		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (proc != null)
				try {
					proc.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}
	public boolean UpdateLastPublishVersion(String wsId,String lastPublishedVersion) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con.prepareCall("{call Proc_UpdateLastPublishVersion(?,?)}");
			cs.setString(1, wsId);
			cs.setString(2,lastPublishedVersion);
			cs.execute();
			cs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	public String CheckConfrimSequenceDtl(String workspaceId){
		String str="No";
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = dataTable.getDataSet(con,"*","View_SubmissionInfoDtlForAllRegions" , "vWorkspaceId='"+workspaceId+"' AND cConfirm='Y'", "");
			while(rs.next())
			{
				str="Yes";
				
			}
			
			rs.close();
			con.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		
		return str;
	}
	public String CheckSequenceRecompile(String workspaceId,String submissionInfoDtlId){
		String str="No";
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			
			rs = dataTable.getDataSet(con,"*","View_SubmissionInfoDtlForAllRegions" , "vWorkspaceId='"+workspaceId+"' AND SubmissionInfoDtlId='"+submissionInfoDtlId+"' AND cStatusIndi<>'N'  AND cConfirm<>'Y'", "");
			while(rs.next())
			{
				str="Yes";
				
			}
			
			rs.close();
			con.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		
		return str;
	}

	public ArrayList<DTOWorkSpaceUserMst> getCompelteWSDetail(int userCode, int userGroupCode) {

		ArrayList<DTOWorkSpaceUserMst> wsDetail = new ArrayList<DTOWorkSpaceUserMst>();
		ResultSet rs = null;
		ResultSet rs1 = null;
		Connection con = null;		
		try {
			con = ConnectionManager.ds.getConnection();
			String from ="WorkspaceUserMst inner join workspacemst on WorkspaceUserMst.vWorkspaceId = workspacemst.vWorkspaceId";
			String Where = "WorkspaceUserMst.iUserCode = "+userCode+" And WorkspaceUserMst.iUserGroupCode= "+userGroupCode+" And workspacemst.cStatusIndi <> 'D' "
						+ "and workspacemst.cStatusIndi <> 'V'";
			rs = dataTable.getDataSet(con, "WorkspaceUserMst.vWorkspaceId", from, Where,"WorkspaceUserMst.vWorkspaceId desc");

			while (rs.next()) {
				DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId")); 
		
				String select="";
				/*select = "workspaceDesc, WorkspaceId, totalNode, count (WorkspaceId) as UploadedFile,"
				         + "convert(numeric(10,2),(count (NodeId)* 100.0/SUM(totalNode) OVER())) as Completion";

				String whr = " WorkspaceId = '" + dto.getWorkSpaceId()+"' group by workspaceDesc, WorkspaceId, totalNode";*/
				
				select = "workspaceDesc, WorkspaceId, totalNode, ISNULL((select convert(numeric(10,2),(count (stageId) * 100.0/SUM(totalNode) OVER())) "
						+ "as ReviewCompletion from view_GetAllApprovedFilesReport where WorkspaceId = '"+dto.getWorkSpaceId()+"' and stageid=20 group by stageid,"
						+ "totalnode),0) as ReviewPer,"
						+ "(Select count(Workspaceid) from view_GetAllApprovedFilesReport where WorkspaceId = '"+dto.getWorkSpaceId()+"' and stageid=20 "
						+ "group by stageid,totalnode) as ReviewFiles, count(Workspaceid) as ApprovedFiles, "
						+ "ISNULL(convert(numeric(10,2),(count (Workspaceid)* 100.0/SUM(totalNode) OVER())),0) as ApprovedPer";

				String whr = " WorkspaceId = '" + dto.getWorkSpaceId()+"' and stageid=100  group by workspaceDesc, WorkspaceId, totalNode, stageId";
				
				rs1 = dataTable.getDataSet(con, select,"view_GetAllApprovedFilesReport",whr, "");
				while (rs1.next()) {
				
				DTOWorkSpaceUserMst data = new DTOWorkSpaceUserMst();
				data.setWorkspacedesc(rs1.getString("workspaceDesc"));
				data.setWorkSpaceId(rs1.getString("WorkspaceId"));
				data.setTotalNode(rs1.getInt("totalNode"));
				data.setUploadedNode(rs1.getInt("ApprovedFiles"));
				data.setReviewFile(rs1.getInt("ReviewFiles"));
				data.setReviewPer(rs1.getFloat("ReviewPer"));
				data.setApprovePer(rs1.getFloat("ApprovedPer"));
				float temp= data.getReviewPer() + data.getApprovePer();
				data.setTotalPer(String.format("%.2f", temp));
				
				wsDetail.add(data);
				data = null;
				
				}
				dto = null;
			}

			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return wsDetail;
	}

	public Vector<DTOWorkSpaceMst> searchWorkspaceForESignDoc(
			DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst) {
			ArrayList<Character> projectTypeList = new ArrayList<Character>();
			projectTypeList.add(ProjectType.DMS_DOC_CENTRIC);
			return searchWorkspaceByProjectTypeForESignDoc(workSpaceMst, userMst,
					projectTypeList);
		}

		public Vector<DTOWorkSpaceMst> searchWorkspaceByProjectTypeForESignDoc(
				DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst,
				ArrayList<Character> projectTypeList) {
			
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			
			String userTypeName;
			userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
			
			String whr="";
			
			Vector<DTOWorkSpaceMst> userWorkspaceDetail = new Vector<DTOWorkSpaceMst>();
			String locationCode = workSpaceMst.getLocationCode();
			String projectCode = workSpaceMst.getProjectCode();
			String clientCode = workSpaceMst.getClientCode();
			String deptCode = workSpaceMst.getDeptCode();
			String workSpaceDesc = workSpaceMst.getWorkSpaceDesc();
			int userCode = userMst.getUserCode();
			int userGroupCode = userMst.getUserGroupCode();
			String userType = userMst.getUserType();
			char str = workSpaceMst.getStatusIndi(); // Set from ShowUserOnProject Module
			String statusindi = String.valueOf(str);
			ResultSet rs = null;
			try {
				// for show Archival Project On Manage Project Module only For Archivist
				if(userTypeName.equalsIgnoreCase("Archivist") && !statusindi.equalsIgnoreCase("A")){      
				whr = "iuserCode=" + userCode + " and iuserGroupCode="
						+ userGroupCode	+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cLockSeq='L'";
				
				}
				// for not show Archival Project On UserOnProject Module
				else if(userTypeName.equalsIgnoreCase("Archivist") && statusindi.equalsIgnoreCase("A")){
					whr = "iuserCode=" + userCode + " and iuserGroupCode="
							+ userGroupCode	+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A'";
				}
				// for not show Archival Project On Manage Project Module except Archivist
				else
				{
					whr = "iuserCode=" + userCode + " and iuserGroupCode="
							+ userGroupCode	+ " and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A'"
							+ " and vProjectCode in ('0004','0005')";
				}
				Connection con = ConnectionManager.ds.getConnection();
				StringBuffer sb = new StringBuffer(whr);
				if (locationCode != null && !locationCode.equals("-1")
						&& !locationCode.equals("")) {
					sb.append(" and vLocationCode='" + locationCode + "'");
				}
				if (clientCode != null && !clientCode.equals("-1")
						&& !clientCode.equals("")) {
					sb.append(" and vClientCode='" + clientCode + "'");
				}
				if (projectCode != null && !projectCode.equals("-1")
						&& !projectCode.equals("")) {
					sb.append(" and vProjectCode='" + projectCode + "'");
				}
				if (deptCode != null && !deptCode.equals("-1")
						&& !deptCode.equals("")) {
					sb.append(" and vDeptCode='" + deptCode + "'");
				}
				if (projectTypeList != null && projectTypeList.size() > 0) {
					sb.append(" and cProjectType in (");
					for (int idxProjectType = 0; idxProjectType < projectTypeList
							.size(); idxProjectType++) {
						sb.append("'"
								+ projectTypeList.get(idxProjectType).charValue()
								+ "',");
					}
					sb.deleteCharAt(sb.length() - 1);
					sb.append(") ");
				}
				if (workSpaceDesc != null) {
					if (!workSpaceDesc.equals(""))
						sb.append(" and vWorkspaceDesc like '%" + workSpaceDesc
								+ "%'");

				}
				String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
						+ "vProjectCode,vProjectName,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,vBaseWorkFolder,"
						+ "vBasePublishFolder,cProjectType,cStatusIndi,dLastAccessedOn,iModifyBy,dToDt,dFromdt,vWorkSpaceRemark,cLockSeq";
				
				rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail", sb.toString(), "dCreatedOn");
				while (rs.next()) {
					DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
					workSpaceMstDTO.setWorkSpaceId(rs.getString("vworkspaceid"));
					workSpaceMstDTO.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
					workSpaceMstDTO.setLocationCode(rs.getString("vLocationCode"));
					workSpaceMstDTO.setLocationName(rs.getString("vLocationName"));
					workSpaceMstDTO.setDeptCode(rs.getString("vDeptCode"));
					workSpaceMstDTO.setDeptName(rs.getString("vDeptName"));
					workSpaceMstDTO.setClientCode(rs.getString("vClientCode"));
					workSpaceMstDTO.setClientName(rs.getString("vClientName"));
					workSpaceMstDTO.setProjectCode(rs.getString("vProjectCode"));
					workSpaceMstDTO.setProjectName(rs.getString("vProjectName"));
					workSpaceMstDTO.setLastPublishedVersion(rs.getString("vlastPublishedVersion"));
					workSpaceMstDTO.setCreatedOn(rs.getTimestamp("dcreatedOn"));
					workSpaceMstDTO.setLastAccessedBy(rs.getInt("ilastAccessedBy"));
					workSpaceMstDTO.setLastaccessedUserName(rs.getString("vusername"));
					workSpaceMstDTO.setLastAccessedOn(rs.getTimestamp("dLastAccessedOn"));
					workSpaceMstDTO.setModifyBy(rs.getInt("iModifyBy"));
					workSpaceMstDTO.setLastModifyUserName(rs.getString("vusername"));
					workSpaceMstDTO.setModifyOn(rs.getTimestamp("dmodifyon"));
					workSpaceMstDTO.setBaseWorkFolder(rs.getString("vbaseWorkFolder"));
					workSpaceMstDTO.setBasePublishFolder(rs.getString("vbasePublishFolder"));
					workSpaceMstDTO.setProjectType(rs.getString("cProjectType").charAt(0));
					workSpaceMstDTO.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
					workSpaceMstDTO.setToDate(rs.getTimestamp("dToDt"));
					workSpaceMstDTO.setFromDate(rs.getTimestamp("dFromdt"));
					workSpaceMstDTO.setRemark(rs.getString("vWorkSpaceRemark"));
					workSpaceMstDTO.setLockSeq(rs.getString("cLockSeq").charAt(0));
					if(countryCode.equalsIgnoreCase("IND")){
						time = docMgmtImpl.TimeZoneConversion(workSpaceMstDTO.getModifyOn(),locationName,countryCode);
						workSpaceMstDTO.setISTDateTime(time.get(0));
					}
					else{					
						time = docMgmtImpl.TimeZoneConversion(workSpaceMstDTO.getModifyOn(),locationName,countryCode);
						workSpaceMstDTO.setISTDateTime(time.get(0));
						workSpaceMstDTO.setESTDateTime(time.get(1));
					}
					if (userType == null || userType.equals("")
							|| userType.equals("WA"))
						userWorkspaceDetail.addElement(workSpaceMstDTO);
					else {
						Calendar calendarToday = CalendarUtils
								.dateToCalendar(new Date());

						Calendar clToDate = CalendarUtils.stringToCalendar(
								workSpaceMstDTO.getToDate().toString(),
								"yyyy-MM-dd");
						int diffInDaysTo = CalendarUtils.dateDifferenceInDays(
								calendarToday, clToDate);

						Calendar clFromDate = CalendarUtils.stringToCalendar(
								workSpaceMstDTO.getFromDate().toString(),
								"yyyy-MM-dd");
						int diffInDaysFrom = CalendarUtils.dateDifferenceInDays(
								calendarToday, clFromDate);

						if (calendarToday != null && clToDate != null
								&& diffInDaysTo >= 0 && diffInDaysFrom <= 0) {
							userWorkspaceDetail.addElement(workSpaceMstDTO);
						}
					}
					workSpaceMstDTO = null;
				}

				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return userWorkspaceDetail;
		}
		public Vector<DTOWorkSpaceMst> searchWorkspaceForESignDocList(
				DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst) {
				ArrayList<Character> projectTypeList = new ArrayList<Character>();
				projectTypeList.add(ProjectType.DMS_DOC_CENTRIC);
				return searchWorkspaceByProjectTypeForESignDocList(workSpaceMst, userMst,
						projectTypeList);
			}

			public Vector<DTOWorkSpaceMst> searchWorkspaceByProjectTypeForESignDocList(
					DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst,
					ArrayList<Character> projectTypeList) {
				
				DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
				ArrayList<String> time = new ArrayList<String>();
				String locationName = ActionContext.getContext().getSession().get("locationname").toString();
				String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
				
				String userTypeName;
				userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
				
				String whr="";
				
				Vector<DTOWorkSpaceMst> userWorkspaceDetail = new Vector<DTOWorkSpaceMst>();
				String locationCode = workSpaceMst.getLocationCode();
				String projectCode = workSpaceMst.getProjectCode();
				String clientCode = workSpaceMst.getClientCode();
				String deptCode = workSpaceMst.getDeptCode();
				String workSpaceDesc = workSpaceMst.getWorkSpaceDesc();
				int userCode = userMst.getUserCode();
				int userGroupCode = userMst.getUserGroupCode();
				String userType = userMst.getUserType();
				char str = workSpaceMst.getStatusIndi(); // Set from ShowUserOnProject Module
				String statusindi = String.valueOf(str);
				ResultSet rs = null;
				try {
					// for show Archival Project On Manage Project Module only For Archivist
					if(userTypeName.equalsIgnoreCase("Archivist") && !statusindi.equalsIgnoreCase("A")){      
					whr = "iuserCode=" + userCode + " and iuserGroupCode="
							+ userGroupCode	+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cLockSeq='L'";
					
					}
					// for not show Archival Project On UserOnProject Module
					else if(userTypeName.equalsIgnoreCase("Archivist") && statusindi.equalsIgnoreCase("A")){
						whr = "iuserCode=" + userCode + " and iuserGroupCode="
								+ userGroupCode	+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A'";
					}
					// for not show Archival Project On Manage Project Module except Archivist
					else
					{
						whr = "iuserCode=" + userCode + " and iuserGroupCode="
								+ userGroupCode	+ " and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A'"
								+ " and vProjectCode in ('0004','0005')";
					}
					Connection con = ConnectionManager.ds.getConnection();
					StringBuffer sb = new StringBuffer(whr);
					if (locationCode != null && !locationCode.equals("-1")
							&& !locationCode.equals("")) {
						sb.append(" and vLocationCode='" + locationCode + "'");
					}
					if (clientCode != null && !clientCode.equals("-1")
							&& !clientCode.equals("")) {
						sb.append(" and vClientCode='" + clientCode + "'");
					}
					if (projectCode != null && !projectCode.equals("-1")
							&& !projectCode.equals("")) {
						sb.append(" and vProjectCode='" + projectCode + "'");
					}
					if (deptCode != null && !deptCode.equals("-1")
							&& !deptCode.equals("")) {
						sb.append(" and vDeptCode='" + deptCode + "'");
					}
					if (projectTypeList != null && projectTypeList.size() > 0) {
						sb.append(" and cProjectType in (");
						for (int idxProjectType = 0; idxProjectType < projectTypeList
								.size(); idxProjectType++) {
							sb.append("'"
									+ projectTypeList.get(idxProjectType).charValue()
									+ "',");
						}
						sb.deleteCharAt(sb.length() - 1);
						sb.append(") ");
					}
					if (workSpaceDesc != null) {
						if (!workSpaceDesc.equals(""))
							sb.append(" and vWorkspaceDesc like '%" + workSpaceDesc
									+ "%'");

					}
					String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
							+ "vProjectCode,vProjectName,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,vBaseWorkFolder,"
							+ "vBasePublishFolder,cProjectType,cStatusIndi,dLastAccessedOn,iModifyBy,dToDt,dFromdt,vWorkSpaceRemark,cLockSeq";
					
					rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail_WSList", sb.toString(), "dCreatedOn");
					while (rs.next()) {
						DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
						workSpaceMstDTO.setWorkSpaceId(rs.getString("vworkspaceid"));
						workSpaceMstDTO.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
						workSpaceMstDTO.setLocationCode(rs.getString("vLocationCode"));
						workSpaceMstDTO.setLocationName(rs.getString("vLocationName"));
						workSpaceMstDTO.setDeptCode(rs.getString("vDeptCode"));
						workSpaceMstDTO.setDeptName(rs.getString("vDeptName"));
						workSpaceMstDTO.setClientCode(rs.getString("vClientCode"));
						workSpaceMstDTO.setClientName(rs.getString("vClientName"));
						workSpaceMstDTO.setProjectCode(rs.getString("vProjectCode"));
						workSpaceMstDTO.setProjectName(rs.getString("vProjectName"));
						workSpaceMstDTO.setLastPublishedVersion(rs.getString("vlastPublishedVersion"));
						workSpaceMstDTO.setCreatedOn(rs.getTimestamp("dcreatedOn"));
						workSpaceMstDTO.setLastAccessedBy(rs.getInt("ilastAccessedBy"));
						workSpaceMstDTO.setLastaccessedUserName(rs.getString("vusername"));
						workSpaceMstDTO.setLastAccessedOn(rs.getTimestamp("dLastAccessedOn"));
						workSpaceMstDTO.setModifyBy(rs.getInt("iModifyBy"));
						workSpaceMstDTO.setLastModifyUserName(rs.getString("vusername"));
						workSpaceMstDTO.setModifyOn(rs.getTimestamp("dmodifyon"));
						workSpaceMstDTO.setBaseWorkFolder(rs.getString("vbaseWorkFolder"));
						workSpaceMstDTO.setBasePublishFolder(rs.getString("vbasePublishFolder"));
						workSpaceMstDTO.setProjectType(rs.getString("cProjectType").charAt(0));
						workSpaceMstDTO.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
						workSpaceMstDTO.setToDate(rs.getTimestamp("dToDt"));
						workSpaceMstDTO.setFromDate(rs.getTimestamp("dFromdt"));
						workSpaceMstDTO.setRemark(rs.getString("vWorkSpaceRemark"));
						workSpaceMstDTO.setLockSeq(rs.getString("cLockSeq").charAt(0));
						if(countryCode.equalsIgnoreCase("IND")){
							time = docMgmtImpl.TimeZoneConversion(workSpaceMstDTO.getModifyOn(),locationName,countryCode);
							workSpaceMstDTO.setISTDateTime(time.get(0));
						}
						else{					
							time = docMgmtImpl.TimeZoneConversion(workSpaceMstDTO.getModifyOn(),locationName,countryCode);
							workSpaceMstDTO.setISTDateTime(time.get(0));
							workSpaceMstDTO.setESTDateTime(time.get(1));
						}
						if (userType == null || userType.equals("")
								|| userType.equals("WA"))
							userWorkspaceDetail.addElement(workSpaceMstDTO);
						else {
							Calendar calendarToday = CalendarUtils
									.dateToCalendar(new Date());

							Calendar clToDate = CalendarUtils.stringToCalendar(
									workSpaceMstDTO.getToDate().toString(),
									"yyyy-MM-dd");
							int diffInDaysTo = CalendarUtils.dateDifferenceInDays(
									calendarToday, clToDate);

							Calendar clFromDate = CalendarUtils.stringToCalendar(
									workSpaceMstDTO.getFromDate().toString(),
									"yyyy-MM-dd");
							int diffInDaysFrom = CalendarUtils.dateDifferenceInDays(
									calendarToday, clFromDate);

							if (calendarToday != null && clToDate != null
									&& diffInDaysTo >= 0 && diffInDaysFrom <= 0) {
								userWorkspaceDetail.addElement(workSpaceMstDTO);
							}
						}
						workSpaceMstDTO = null;
					}

					rs.close();
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				return userWorkspaceDetail;
			}
public int getTotalLeafNodeCount(String wsId) {
	int leafnode = 0;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		String where= "vWorkspaceId ='"+wsId+"' and cStatusIndi<>'D' and  crequiredflag in ('n','f') "
				+ " and inodeid not in ( select distinct iparentnodeid from workspacenodedetail where  crequiredflag in ('n','f')"
				+ " and  vWorkspaceId='"+wsId+"')";
		

		ResultSet rs = dataTable.getDataSet(con,"count(iNodeId) as totalLeafNode","workspacenodedetail",where, "");
				if (rs.next()) {
					leafnode = rs.getInt("totalLeafNode");
				}
				rs.close();
				con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return leafnode;
		}
public int gettimeLineTotalLeafCount(String wsId) {
	int leafnode = 0;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		String where= "timelineworkspaceuserrightsmst.ihours >0 and timelineworkspaceuserrightsmst.cStatusIndi<>'D'" 
					 +" and workspaceNodeDetail.vWorkspaceId ='"+wsId+"' and workspaceNodeDetail.cStatusIndi<>'D' "
					 + "and  workspaceNodeDetail.crequiredflag in ('n','f') and workspaceNodeDetail.inodeid "
					 + "not in ( select distinct iparentnodeid from workspacenodedetail where  crequiredflag in ('n','f')  "
					 + "and  vWorkspaceId='"+wsId+"')";
		
		String temp=" inner join timelineworkspaceuserrightsmst on workspaceNodeDetail.vworkspaceid=timelineworkspaceuserrightsmst.vworkspaceid" 
					+" and workspaceNodeDetail.inodeid=timelineworkspaceuserrightsmst.inodeid";
		
		String from = "workspacenodedetail"+temp;
		
		ResultSet rs = dataTable.getDataSet(con,"count(distinct workspaceNodeDetail.iNodeId) as totalLeafNode",from,where, "");
				if (rs.next()) {
					leafnode = rs.getInt("totalLeafNode");
				}
				rs.close();
				con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return leafnode;
		}
public String getWorkspaceName(String workspaceId) {
	String wsDesc = "";
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con, "vWorkspaceDesc","WorkspaceMst", "vWorkspaceId = '" + workspaceId + "'","");
		if (rs.next()) {
			wsDesc = rs.getString("vWorkspaceDesc");
		}
		rs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return wsDesc;
}
public Vector<DTOWorkSpaceMst> getUserWorkspaceForSerachProject(int userGroupCode,
		int userCode) {
	Vector<DTOWorkSpaceMst> userWorkspaceDetail = new Vector<DTOWorkSpaceMst>();

	Connection con = null;
	ResultSet rs = null;
	try {
		con = ConnectionManager.ds.getConnection();
		String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
				+ "vProjectCode,vProjectName,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,dLastAccessedOn,vBaseWorkFolder,"
				+ "vBasePublishFolder,cProjectType,iModifyBy,dToDt,vWorkSpaceRemark";
		String Where = " iuserCode=" + userCode	+ " and iUserGroupCode=" + userGroupCode
						+ " and cstatusIndi<>'D' and cStatusIndi<>'A' and cstatusIndi<>'V' and cProjectType<>'D'"
						+ " and WorkspaceUserStatusIndi<>'D' and getdate()>=dfromdt and getdate()<=dtodt ";
		
		rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail", Where, "vWorkspaceId desc");
		while (rs.next()) {
			DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
			workSpaceMstDTO.setWorkSpaceId(rs.getString("vWorkspaceId"));
			workSpaceMstDTO.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
			workSpaceMstDTO.setLocationCode(rs.getString("vLocationCode"));
			workSpaceMstDTO.setLocationName(rs.getString("vLocationName"));
			workSpaceMstDTO.setDeptCode(rs.getString("vDeptCode"));
			workSpaceMstDTO.setDeptName(rs.getString("vDeptName"));
			workSpaceMstDTO.setClientCode(rs.getString("vClientCode"));
			workSpaceMstDTO.setClientName(rs.getString("vClientName"));
			workSpaceMstDTO.setProjectName(rs.getString("vProjectName"));
			workSpaceMstDTO.setProjectCode(rs.getString("vProjectCode"));
			workSpaceMstDTO.setLastPublishedVersion(rs.getString("vlastPublishedVersion"));
			workSpaceMstDTO.setCreatedOn(rs.getTimestamp("dcreatedOn"));
			workSpaceMstDTO.setLastAccessedBy(rs.getInt("ilastAccessedBy"));
			workSpaceMstDTO.setLastaccessedUserName(rs.getString("vusername"));
			workSpaceMstDTO.setLastAccessedOn(rs.getTimestamp("dlastaccessedon"));
			workSpaceMstDTO.setModifyBy(rs.getInt("iModifyBy"));
			workSpaceMstDTO.setLastModifyUserName(rs.getString("vusername"));
			workSpaceMstDTO.setModifyOn(rs.getTimestamp("dmodifyon"));
			workSpaceMstDTO.setBaseWorkFolder(rs.getString("vbaseWorkFolder"));
			workSpaceMstDTO.setBasePublishFolder(rs.getString("vbasePublishFolder"));
			workSpaceMstDTO.setProjectType(rs.getString("cProjectType").charAt(0));
			workSpaceMstDTO.setToDate(rs.getTimestamp("dToDt"));
			workSpaceMstDTO.setFromDate(new Timestamp(new java.util.Date().getTime()));
			workSpaceMstDTO.setRemark(rs.getString("vWorkSpaceRemark"));
			userWorkspaceDetail.addElement(workSpaceMstDTO);
			workSpaceMstDTO = null;

		}

	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	return userWorkspaceDetail;
}
public Vector<DTOWorkSpaceMst> getUserWorkspaceForSerachProjectList(int userGroupCode,
		int userCode) {
	Vector<DTOWorkSpaceMst> userWorkspaceDetail = new Vector<DTOWorkSpaceMst>();

	Connection con = null;
	ResultSet rs = null;
	try {
		con = ConnectionManager.ds.getConnection();
		String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
				+ "vProjectCode,vProjectName,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,dLastAccessedOn,vBaseWorkFolder,"
				+ "vBasePublishFolder,cProjectType,iModifyBy,dToDt,vWorkSpaceRemark";
		String Where = " iuserCode=" + userCode	+ " and iUserGroupCode=" + userGroupCode
						+ " and cstatusIndi<>'D' and cStatusIndi<>'A' and cstatusIndi<>'V' and cProjectType<>'D'"
						+ " and WorkspaceUserStatusIndi<>'D' and getdate()>=dfromdt and getdate()<=dtodt ";
		
		rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail_WSList", Where, "vWorkspaceId desc");
		while (rs.next()) {
			DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
			workSpaceMstDTO.setWorkSpaceId(rs.getString("vWorkspaceId"));
			workSpaceMstDTO.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
			workSpaceMstDTO.setLocationCode(rs.getString("vLocationCode"));
			workSpaceMstDTO.setLocationName(rs.getString("vLocationName"));
			workSpaceMstDTO.setDeptCode(rs.getString("vDeptCode"));
			workSpaceMstDTO.setDeptName(rs.getString("vDeptName"));
			workSpaceMstDTO.setClientCode(rs.getString("vClientCode"));
			workSpaceMstDTO.setClientName(rs.getString("vClientName"));
			workSpaceMstDTO.setProjectName(rs.getString("vProjectName"));
			workSpaceMstDTO.setProjectCode(rs.getString("vProjectCode"));
			workSpaceMstDTO.setLastPublishedVersion(rs.getString("vlastPublishedVersion"));
			workSpaceMstDTO.setCreatedOn(rs.getTimestamp("dcreatedOn"));
			workSpaceMstDTO.setLastAccessedBy(rs.getInt("ilastAccessedBy"));
			workSpaceMstDTO.setLastaccessedUserName(rs.getString("vusername"));
			workSpaceMstDTO.setLastAccessedOn(rs.getTimestamp("dlastaccessedon"));
			workSpaceMstDTO.setModifyBy(rs.getInt("iModifyBy"));
			workSpaceMstDTO.setLastModifyUserName(rs.getString("vusername"));
			workSpaceMstDTO.setModifyOn(rs.getTimestamp("dmodifyon"));
			workSpaceMstDTO.setBaseWorkFolder(rs.getString("vbaseWorkFolder"));
			workSpaceMstDTO.setBasePublishFolder(rs.getString("vbasePublishFolder"));
			workSpaceMstDTO.setProjectType(rs.getString("cProjectType").charAt(0));
			workSpaceMstDTO.setToDate(rs.getTimestamp("dToDt"));
			workSpaceMstDTO.setFromDate(new Timestamp(new java.util.Date().getTime()));
			workSpaceMstDTO.setRemark(rs.getString("vWorkSpaceRemark"));
			userWorkspaceDetail.addElement(workSpaceMstDTO);
			workSpaceMstDTO = null;

		}

	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	return userWorkspaceDetail;
}

public Vector<DTOWorkSpaceMst> getUserWorkspaceListForRightsReplication(int userGroupCode,int userCode,String wsId) {
	Vector<DTOWorkSpaceMst> userWorkspaceDetail = new Vector<DTOWorkSpaceMst>();

	Connection con = null;
	ResultSet rs = null;
	try {
		con = ConnectionManager.ds.getConnection();
		String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
				+ "vProjectCode,vProjectName,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,dLastAccessedOn,vBaseWorkFolder,"
				+ "vBasePublishFolder,cProjectType,iModifyBy,dToDt,vWorkSpaceRemark";
		String Where = " iuserCode=" + userCode	+ " and iUserGroupCode=" + userGroupCode
						+ " and cstatusIndi<>'D' and cStatusIndi<>'A' and cstatusIndi<>'V' and cProjectType<>'D'"
						+ " and WorkspaceUserStatusIndi<>'D' and getdate()>=dfromdt and getdate()<=dtodt and vworkspaceid <> '" + wsId + "' ";
		
		rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail_WSList", Where, "vWorkspaceId desc");
		while (rs.next()) {
			DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
			workSpaceMstDTO.setWorkSpaceId(rs.getString("vWorkspaceId"));
			workSpaceMstDTO.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
			workSpaceMstDTO.setLocationCode(rs.getString("vLocationCode"));
			workSpaceMstDTO.setLocationName(rs.getString("vLocationName"));
			workSpaceMstDTO.setDeptCode(rs.getString("vDeptCode"));
			workSpaceMstDTO.setDeptName(rs.getString("vDeptName"));
			workSpaceMstDTO.setClientCode(rs.getString("vClientCode"));
			workSpaceMstDTO.setClientName(rs.getString("vClientName"));
			workSpaceMstDTO.setProjectName(rs.getString("vProjectName"));
			workSpaceMstDTO.setProjectCode(rs.getString("vProjectCode"));
			workSpaceMstDTO.setLastPublishedVersion(rs.getString("vlastPublishedVersion"));
			workSpaceMstDTO.setCreatedOn(rs.getTimestamp("dcreatedOn"));
			workSpaceMstDTO.setLastAccessedBy(rs.getInt("ilastAccessedBy"));
			workSpaceMstDTO.setLastaccessedUserName(rs.getString("vusername"));
			workSpaceMstDTO.setLastAccessedOn(rs.getTimestamp("dlastaccessedon"));
			workSpaceMstDTO.setModifyBy(rs.getInt("iModifyBy"));
			workSpaceMstDTO.setLastModifyUserName(rs.getString("vusername"));
			workSpaceMstDTO.setModifyOn(rs.getTimestamp("dmodifyon"));
			workSpaceMstDTO.setBaseWorkFolder(rs.getString("vbaseWorkFolder"));
			workSpaceMstDTO.setBasePublishFolder(rs.getString("vbasePublishFolder"));
			workSpaceMstDTO.setProjectType(rs.getString("cProjectType").charAt(0));
			workSpaceMstDTO.setToDate(rs.getTimestamp("dToDt"));
			workSpaceMstDTO.setFromDate(new Timestamp(new java.util.Date().getTime()));
			workSpaceMstDTO.setRemark(rs.getString("vWorkSpaceRemark"));
			userWorkspaceDetail.addElement(workSpaceMstDTO);
			workSpaceMstDTO = null;

		}

	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	return userWorkspaceDetail;
}

public Vector<DTOWorkSpaceMst> getUserWorkspaceForSerachProjectDropdown(int userGroupCode,
		int userCode) {
	Vector<DTOWorkSpaceMst> userWorkspaceDetail = new Vector<DTOWorkSpaceMst>();
	String userTypeName;
	userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
	Connection con = null;
	ResultSet rs = null;
	String Where="";
	try {
		con = ConnectionManager.ds.getConnection();
		String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
				+ "vProjectCode,vProjectName,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,dLastAccessedOn,vBaseWorkFolder,"
				+ "vBasePublishFolder,cProjectType,iModifyBy,dToDt,vWorkSpaceRemark";
		if(userTypeName.equalsIgnoreCase("WA")){
			 Where = " iuserCode=" + userCode + " and iUserGroupCode=" + userGroupCode
						+ " and cstatusIndi<>'D' and cStatusIndi<>'A' and cProjectType<>'D'"
						+ " and WorkspaceUserStatusIndi<>'D' and getdate()>=dfromdt and getdate()<=dtodt ";
		}else{
		 Where = " iuserCode="+ userCode + " and iUserGroupCode=" + userGroupCode
				 + " and cstatusIndi<>'D' and cStatusIndi<>'A' and cStatusIndi<>'V' and cProjectType<>'D'"
				 + " and WorkspaceUserStatusIndi<>'D' and getdate()>=dfromdt and getdate()<=dtodt ";
		}
		rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail", Where, "dCreatedOn desc");
		while (rs.next()) {
			DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
			workSpaceMstDTO.setWorkSpaceId(rs.getString("vWorkspaceId"));
			workSpaceMstDTO.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
			workSpaceMstDTO.setLocationCode(rs.getString("vLocationCode"));
			workSpaceMstDTO.setLocationName(rs.getString("vLocationName"));
			workSpaceMstDTO.setDeptCode(rs.getString("vDeptCode"));
			workSpaceMstDTO.setDeptName(rs.getString("vDeptName"));
			workSpaceMstDTO.setClientCode(rs.getString("vClientCode"));
			workSpaceMstDTO.setClientName(rs.getString("vClientName"));
			workSpaceMstDTO.setProjectName(rs.getString("vProjectName"));
			workSpaceMstDTO.setProjectCode(rs.getString("vProjectCode"));
			workSpaceMstDTO.setLastPublishedVersion(rs.getString("vlastPublishedVersion"));
			workSpaceMstDTO.setCreatedOn(rs.getTimestamp("dcreatedOn"));
			workSpaceMstDTO.setLastAccessedBy(rs.getInt("ilastAccessedBy"));
			workSpaceMstDTO.setLastaccessedUserName(rs.getString("vusername"));
			workSpaceMstDTO.setLastAccessedOn(rs.getTimestamp("dlastaccessedon"));
			workSpaceMstDTO.setModifyBy(rs.getInt("iModifyBy"));
			workSpaceMstDTO.setLastModifyUserName(rs.getString("vusername"));
			workSpaceMstDTO.setModifyOn(rs.getTimestamp("dmodifyon"));
			workSpaceMstDTO.setBaseWorkFolder(rs.getString("vbaseWorkFolder"));
			workSpaceMstDTO.setBasePublishFolder(rs.getString("vbasePublishFolder"));
			workSpaceMstDTO.setProjectType(rs.getString("cProjectType").charAt(0));
			workSpaceMstDTO.setToDate(rs.getTimestamp("dToDt"));
			workSpaceMstDTO.setFromDate(new Timestamp(new java.util.Date().getTime()));
			workSpaceMstDTO.setRemark(rs.getString("vWorkSpaceRemark"));
			userWorkspaceDetail.addElement(workSpaceMstDTO);
			workSpaceMstDTO = null;

		}

	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	return userWorkspaceDetail;
}
public Vector<DTOWorkSpaceMst> getUserWorkspaceForSerachProjectDropdownWSList(int userGroupCode,
		int userCode) {
	Vector<DTOWorkSpaceMst> userWorkspaceDetail = new Vector<DTOWorkSpaceMst>();
	String userTypeName;
	userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
	Connection con = null;
	ResultSet rs = null;
	String Where="";
	try {
		con = ConnectionManager.ds.getConnection();
		String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
				+ "vProjectCode,vProjectName,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,dLastAccessedOn,vBaseWorkFolder,"
				+ "vBasePublishFolder,cProjectType,iModifyBy,dToDt,vWorkSpaceRemark";
		if(userTypeName.equalsIgnoreCase("WA")){
			 Where = " iuserCode=" + userCode + " and iUserGroupCode=" + userGroupCode
						+ " and cstatusIndi<>'D' and cStatusIndi<>'A' and cProjectType<>'D'"
						+ " and WorkspaceUserStatusIndi<>'D' and getdate()>=dfromdt and getdate()<=dtodt ";
		}else{
		 Where = " iuserCode="+ userCode + " and iUserGroupCode=" + userGroupCode
				 + " and cstatusIndi<>'D' and cStatusIndi<>'A' and cStatusIndi<>'V' and cProjectType<>'D'"
				 + " and WorkspaceUserStatusIndi<>'D' and getdate()>=dfromdt and getdate()<=dtodt ";
		}
		rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail_WSList", Where, "dCreatedOn desc");
		while (rs.next()) {
			DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
			workSpaceMstDTO.setWorkSpaceId(rs.getString("vWorkspaceId"));
			workSpaceMstDTO.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
			workSpaceMstDTO.setLocationCode(rs.getString("vLocationCode"));
			workSpaceMstDTO.setLocationName(rs.getString("vLocationName"));
			workSpaceMstDTO.setDeptCode(rs.getString("vDeptCode"));
			workSpaceMstDTO.setDeptName(rs.getString("vDeptName"));
			workSpaceMstDTO.setClientCode(rs.getString("vClientCode"));
			workSpaceMstDTO.setClientName(rs.getString("vClientName"));
			workSpaceMstDTO.setProjectName(rs.getString("vProjectName"));
			workSpaceMstDTO.setProjectCode(rs.getString("vProjectCode"));
			workSpaceMstDTO.setLastPublishedVersion(rs.getString("vlastPublishedVersion"));
			workSpaceMstDTO.setCreatedOn(rs.getTimestamp("dcreatedOn"));
			workSpaceMstDTO.setLastAccessedBy(rs.getInt("ilastAccessedBy"));
			workSpaceMstDTO.setLastaccessedUserName(rs.getString("vusername"));
			workSpaceMstDTO.setLastAccessedOn(rs.getTimestamp("dlastaccessedon"));
			workSpaceMstDTO.setModifyBy(rs.getInt("iModifyBy"));
			workSpaceMstDTO.setLastModifyUserName(rs.getString("vusername"));
			workSpaceMstDTO.setModifyOn(rs.getTimestamp("dmodifyon"));
			workSpaceMstDTO.setBaseWorkFolder(rs.getString("vbaseWorkFolder"));
			workSpaceMstDTO.setBasePublishFolder(rs.getString("vbasePublishFolder"));
			workSpaceMstDTO.setProjectType(rs.getString("cProjectType").charAt(0));
			workSpaceMstDTO.setToDate(rs.getTimestamp("dToDt"));
			workSpaceMstDTO.setFromDate(new Timestamp(new java.util.Date().getTime()));
			workSpaceMstDTO.setRemark(rs.getString("vWorkSpaceRemark"));
			userWorkspaceDetail.addElement(workSpaceMstDTO);
			workSpaceMstDTO = null;

		}

	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	return userWorkspaceDetail;
}
public Vector<DTOWorkSpaceMst> searchWorkspaceFromUserOnProject(
		DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst) {
		ArrayList<Character> projectTypeList = new ArrayList<Character>();
		projectTypeList.add(ProjectType.DMS_IMPORTED);
		projectTypeList.add(ProjectType.DMS_STANDARD);
		projectTypeList.add(ProjectType.ECTD_MANUAL);
		projectTypeList.add(ProjectType.ECTD_STANDARD);
		projectTypeList.add(ProjectType.NEES_STANDARD);
		projectTypeList.add(ProjectType.VNEES_STANDARD);
		return searchWorkspaceByProjectTypeFromUserOnProject(workSpaceMst, userMst,
				projectTypeList);
	}

	public Vector<DTOWorkSpaceMst> searchWorkspaceByProjectTypeFromUserOnProject(
			DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst,
			ArrayList<Character> projectTypeList) {
		
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		
		String userTypeName;
		userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		
		String whr="";
		
		Vector<DTOWorkSpaceMst> userWorkspaceDetail = new Vector<DTOWorkSpaceMst>();
		String locationCode = workSpaceMst.getLocationCode();
		String projectCode = workSpaceMst.getProjectCode();
		String clientCode = workSpaceMst.getClientCode();
		String deptCode = workSpaceMst.getDeptCode();
		String workSpaceDesc = workSpaceMst.getWorkSpaceDesc();
		int userCode = userMst.getUserCode();
		int userGroupCode = userMst.getUserGroupCode();
		String userType = userMst.getUserType();
		char str = workSpaceMst.getStatusIndi(); // Set from ShowUserOnProject Module
		String statusindi = String.valueOf(str);
		ResultSet rs = null;
		try {
			// for show Archival Project On Manage Project Module only For Archivist
			if(userTypeName.equalsIgnoreCase("Archivist") && !statusindi.equalsIgnoreCase("A")){      
			whr = "iuserCode=" + userCode + " and iuserGroupCode="
					+ userGroupCode	+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cLockSeq='L'";
			
			}
			// for not show Archival Project On UserOnProject Module
			else if(userTypeName.equalsIgnoreCase("Archivist") && statusindi.equalsIgnoreCase("A")){
				whr = "iuserCode=" + userCode + " and iuserGroupCode="
						+ userGroupCode	+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A' and cStatusIndi<>'V'";
			}
			// for show Projects with Void/UnVoid On UserOnProject Module
			else if(userTypeName.equalsIgnoreCase("WA")){
				whr = "iuserCode=" + userCode + " and iuserGroupCode="
						+ userGroupCode	+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A'";
			}
			// for not show Archival Project On Manage Project Module except Archivist
			else
			{
				whr = "iuserCode=" + userCode + " and iuserGroupCode="
						+ userGroupCode	+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A' and cStatusIndi<>'V'" 
						+ " and vProjectCode in ('0002','0003')";
			}
			Connection con = ConnectionManager.ds.getConnection();
			StringBuffer sb = new StringBuffer(whr);
			if (locationCode != null && !locationCode.equals("-1")
					&& !locationCode.equals("")) {
				sb.append(" and vLocationCode='" + locationCode + "'");
			}
			if (clientCode != null && !clientCode.equals("-1")
					&& !clientCode.equals("")) {
				sb.append(" and vClientCode='" + clientCode + "'");
			}
			if (projectCode != null && !projectCode.equals("-1")
					&& !projectCode.equals("")) {
				sb.append(" and vProjectCode='" + projectCode + "'");
			}
			if (deptCode != null && !deptCode.equals("-1")
					&& !deptCode.equals("")) {
				sb.append(" and vDeptCode='" + deptCode + "'");
			}
			if (projectTypeList != null && projectTypeList.size() > 0) {
				sb.append(" and cProjectType in (");
				for (int idxProjectType = 0; idxProjectType < projectTypeList
						.size(); idxProjectType++) {
					sb.append("'"
							+ projectTypeList.get(idxProjectType).charValue()
							+ "',");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(") ");
			}
			if (workSpaceDesc != null) {
				if (!workSpaceDesc.equals(""))
					sb.append(" and vWorkspaceDesc like '%" + workSpaceDesc
							+ "%'");

			}
			String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
					+ "vProjectCode,vProjectName,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,vBaseWorkFolder,"
					+ "vBasePublishFolder,cProjectType,cStatusIndi,dLastAccessedOn,iModifyBy,dToDt,dFromdt,vWorkSpaceRemark,cLockSeq";
			
			rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail", sb.toString(), "dCreatedOn Desc");
			while (rs.next()) {
				DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
				workSpaceMstDTO.setWorkSpaceId(rs.getString("vworkspaceid"));
				workSpaceMstDTO.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				workSpaceMstDTO.setLocationCode(rs.getString("vLocationCode"));
				workSpaceMstDTO.setLocationName(rs.getString("vLocationName"));
				workSpaceMstDTO.setDeptCode(rs.getString("vDeptCode"));
				workSpaceMstDTO.setDeptName(rs.getString("vDeptName"));
				workSpaceMstDTO.setClientCode(rs.getString("vClientCode"));
				workSpaceMstDTO.setClientName(rs.getString("vClientName"));
				workSpaceMstDTO.setProjectCode(rs.getString("vProjectCode"));
				workSpaceMstDTO.setProjectName(rs.getString("vProjectName"));
				workSpaceMstDTO.setLastPublishedVersion(rs.getString("vlastPublishedVersion"));
				workSpaceMstDTO.setCreatedOn(rs.getTimestamp("dcreatedOn"));
				workSpaceMstDTO.setLastAccessedBy(rs.getInt("ilastAccessedBy"));
				workSpaceMstDTO.setLastaccessedUserName(rs.getString("vusername"));
				workSpaceMstDTO.setLastAccessedOn(rs.getTimestamp("dLastAccessedOn"));
				workSpaceMstDTO.setModifyBy(rs.getInt("iModifyBy"));
				workSpaceMstDTO.setLastModifyUserName(rs.getString("vusername"));
				workSpaceMstDTO.setModifyOn(rs.getTimestamp("dmodifyon"));
				workSpaceMstDTO.setBaseWorkFolder(rs.getString("vbaseWorkFolder"));
				workSpaceMstDTO.setBasePublishFolder(rs.getString("vbasePublishFolder"));
				workSpaceMstDTO.setProjectType(rs.getString("cProjectType").charAt(0));
				workSpaceMstDTO.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				workSpaceMstDTO.setToDate(rs.getTimestamp("dToDt"));
				workSpaceMstDTO.setFromDate(rs.getTimestamp("dFromdt"));
				workSpaceMstDTO.setRemark(rs.getString("vWorkSpaceRemark"));
				workSpaceMstDTO.setLockSeq(rs.getString("cLockSeq").charAt(0));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(workSpaceMstDTO.getModifyOn(),locationName,countryCode);
					workSpaceMstDTO.setISTDateTime(time.get(0));
				}
				else{					
					time = docMgmtImpl.TimeZoneConversion(workSpaceMstDTO.getModifyOn(),locationName,countryCode);
					workSpaceMstDTO.setISTDateTime(time.get(0));
					workSpaceMstDTO.setESTDateTime(time.get(1));
				}
				if (userType == null || userType.equals("")
						|| userType.equals("WA"))
					userWorkspaceDetail.addElement(workSpaceMstDTO);
				else {
					Calendar calendarToday = CalendarUtils
							.dateToCalendar(new Date());

					Calendar clToDate = CalendarUtils.stringToCalendar(
							workSpaceMstDTO.getToDate().toString(),
							"yyyy-MM-dd");
					int diffInDaysTo = CalendarUtils.dateDifferenceInDays(
							calendarToday, clToDate);

					Calendar clFromDate = CalendarUtils.stringToCalendar(
							workSpaceMstDTO.getFromDate().toString(),
							"yyyy-MM-dd");
					int diffInDaysFrom = CalendarUtils.dateDifferenceInDays(
							calendarToday, clFromDate);

					if (calendarToday != null && clToDate != null
							&& diffInDaysTo >= 0 && diffInDaysFrom <= 0) {
						userWorkspaceDetail.addElement(workSpaceMstDTO);
					}
				}
				workSpaceMstDTO = null;
			}

			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userWorkspaceDetail;
	}
public Vector<DTOWorkSpaceMst> searchWorkspaceFromUserOnProjectList(
		DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst) {
		ArrayList<Character> projectTypeList = new ArrayList<Character>();
		projectTypeList.add(ProjectType.DMS_IMPORTED);
		projectTypeList.add(ProjectType.DMS_STANDARD);
		projectTypeList.add(ProjectType.ECTD_MANUAL);
		projectTypeList.add(ProjectType.ECTD_STANDARD);
		projectTypeList.add(ProjectType.NEES_STANDARD);
		projectTypeList.add(ProjectType.VNEES_STANDARD);
		return searchWorkspaceByProjectTypeFromUserOnProjectList(workSpaceMst, userMst,
				projectTypeList);
	}

	public Vector<DTOWorkSpaceMst> searchWorkspaceByProjectTypeFromUserOnProjectList(
			DTOWorkSpaceMst workSpaceMst, DTOUserMst userMst,
			ArrayList<Character> projectTypeList) {
		
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		
		String userTypeName;
		userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		
		String whr="";
		
		Vector<DTOWorkSpaceMst> userWorkspaceDetail = new Vector<DTOWorkSpaceMst>();
		String locationCode = workSpaceMst.getLocationCode();
		String projectCode = workSpaceMst.getProjectCode();
		String clientCode = workSpaceMst.getClientCode();
		String deptCode = workSpaceMst.getDeptCode();
		String workSpaceDesc = workSpaceMst.getWorkSpaceDesc();
		int userCode = userMst.getUserCode();
		int userGroupCode = userMst.getUserGroupCode();
		String userType = userMst.getUserType();
		char str = workSpaceMst.getStatusIndi(); // Set from ShowUserOnProject Module
		String statusindi = String.valueOf(str);
		ResultSet rs = null;
		try {
			// for show Archival Project On Manage Project Module only For Archivist
			if(userTypeName.equalsIgnoreCase("Archivist") && !statusindi.equalsIgnoreCase("A")){      
			whr = "iuserCode=" + userCode + " and iuserGroupCode="
					+ userGroupCode	+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cLockSeq='L'";
			
			}
			// for not show Archival Project On UserOnProject Module
			else if(userTypeName.equalsIgnoreCase("Archivist") && statusindi.equalsIgnoreCase("A")){
				whr = "iuserCode=" + userCode + " and iuserGroupCode="
						+ userGroupCode	+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A' and cStatusIndi<>'V'";
			}
			// for show Projects with Void/UnVoid On UserOnProject Module
			else if(userTypeName.equalsIgnoreCase("WA")){
				whr = "iuserCode=" + userCode + " and iuserGroupCode="
						+ userGroupCode	+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A'";
			}
			// for not show Archival Project On Manage Project Module except Archivist
			else
			{
				whr = "iuserCode=" + userCode + " and iuserGroupCode="
						+ userGroupCode	+ "  and WorkspaceUserStatusIndi<>'D' and cStatusIndi<>'D' and cStatusIndi<>'A' and cStatusIndi<>'V'" 
						+ " and vProjectCode in ('0002','0003')";
			}
			Connection con = ConnectionManager.ds.getConnection();
			StringBuffer sb = new StringBuffer(whr);
			if (locationCode != null && !locationCode.equals("-1")
					&& !locationCode.equals("")) {
				sb.append(" and vLocationCode='" + locationCode + "'");
			}
			if (clientCode != null && !clientCode.equals("-1")
					&& !clientCode.equals("")) {
				sb.append(" and vClientCode='" + clientCode + "'");
			}
			if (projectCode != null && !projectCode.equals("-1")
					&& !projectCode.equals("")) {
				sb.append(" and vProjectCode='" + projectCode + "'");
			}
			if (deptCode != null && !deptCode.equals("-1")
					&& !deptCode.equals("")) {
				sb.append(" and vDeptCode='" + deptCode + "'");
			}
			if (projectTypeList != null && projectTypeList.size() > 0) {
				sb.append(" and cProjectType in (");
				for (int idxProjectType = 0; idxProjectType < projectTypeList
						.size(); idxProjectType++) {
					sb.append("'"
							+ projectTypeList.get(idxProjectType).charValue()
							+ "',");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(") ");
			}
			if (workSpaceDesc != null) {
				if (!workSpaceDesc.equals(""))
					sb.append(" and vWorkspaceDesc like '%" + workSpaceDesc
							+ "%'");

			}
			String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,vLocationCode,vLocationName,vDeptCode,vDeptName,vClientCode,vClientName,"
					+ "vProjectCode,vProjectName,vLastPublishedVersion,dCreatedOn,ilastaccessedBy,vusername,dModifyOn,vBaseWorkFolder,"
					+ "vBasePublishFolder,cProjectType,cStatusIndi,dLastAccessedOn,iModifyBy,dToDt,dFromdt,vWorkSpaceRemark,cLockSeq";
			
			rs = dataTable.getDataSet(con, Fields,"View_CommonWorkspaceDetail_WSList", sb.toString(), "dCreatedOn Desc");
			while (rs.next()) {
				DTOWorkSpaceMst workSpaceMstDTO = new DTOWorkSpaceMst();
				workSpaceMstDTO.setWorkSpaceId(rs.getString("vworkspaceid"));
				workSpaceMstDTO.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				workSpaceMstDTO.setLocationCode(rs.getString("vLocationCode"));
				workSpaceMstDTO.setLocationName(rs.getString("vLocationName"));
				workSpaceMstDTO.setDeptCode(rs.getString("vDeptCode"));
				workSpaceMstDTO.setDeptName(rs.getString("vDeptName"));
				workSpaceMstDTO.setClientCode(rs.getString("vClientCode"));
				workSpaceMstDTO.setClientName(rs.getString("vClientName"));
				workSpaceMstDTO.setProjectCode(rs.getString("vProjectCode"));
				workSpaceMstDTO.setProjectName(rs.getString("vProjectName"));
				workSpaceMstDTO.setLastPublishedVersion(rs.getString("vlastPublishedVersion"));
				workSpaceMstDTO.setCreatedOn(rs.getTimestamp("dcreatedOn"));
				workSpaceMstDTO.setLastAccessedBy(rs.getInt("ilastAccessedBy"));
				workSpaceMstDTO.setLastaccessedUserName(rs.getString("vusername"));
				workSpaceMstDTO.setLastAccessedOn(rs.getTimestamp("dLastAccessedOn"));
				workSpaceMstDTO.setModifyBy(rs.getInt("iModifyBy"));
				workSpaceMstDTO.setLastModifyUserName(rs.getString("vusername"));
				workSpaceMstDTO.setModifyOn(rs.getTimestamp("dmodifyon"));
				workSpaceMstDTO.setBaseWorkFolder(rs.getString("vbaseWorkFolder"));
				workSpaceMstDTO.setBasePublishFolder(rs.getString("vbasePublishFolder"));
				workSpaceMstDTO.setProjectType(rs.getString("cProjectType").charAt(0));
				workSpaceMstDTO.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				workSpaceMstDTO.setToDate(rs.getTimestamp("dToDt"));
				workSpaceMstDTO.setFromDate(rs.getTimestamp("dFromdt"));
				workSpaceMstDTO.setRemark(rs.getString("vWorkSpaceRemark"));
				workSpaceMstDTO.setLockSeq(rs.getString("cLockSeq").charAt(0));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(workSpaceMstDTO.getModifyOn(),locationName,countryCode);
					workSpaceMstDTO.setISTDateTime(time.get(0));
				}
				else{					
					time = docMgmtImpl.TimeZoneConversion(workSpaceMstDTO.getModifyOn(),locationName,countryCode);
					workSpaceMstDTO.setISTDateTime(time.get(0));
					workSpaceMstDTO.setESTDateTime(time.get(1));
				}
				if (userType == null || userType.equals("")
						|| userType.equals("WA"))
					userWorkspaceDetail.addElement(workSpaceMstDTO);
				else {
					Calendar calendarToday = CalendarUtils
							.dateToCalendar(new Date());

					Calendar clToDate = CalendarUtils.stringToCalendar(
							workSpaceMstDTO.getToDate().toString(),
							"yyyy-MM-dd");
					int diffInDaysTo = CalendarUtils.dateDifferenceInDays(
							calendarToday, clToDate);

					Calendar clFromDate = CalendarUtils.stringToCalendar(
							workSpaceMstDTO.getFromDate().toString(),
							"yyyy-MM-dd");
					int diffInDaysFrom = CalendarUtils.dateDifferenceInDays(
							calendarToday, clFromDate);

					if (calendarToday != null && clToDate != null
							&& diffInDaysTo >= 0 && diffInDaysFrom <= 0) {
						userWorkspaceDetail.addElement(workSpaceMstDTO);
					}
				}
				workSpaceMstDTO = null;
			}

			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userWorkspaceDetail;
	}
	public synchronized int getNewTranNoForTemplate(String wsId) {
		int tranNo = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con.prepareCall("{call Proc_newTranNoForTemplate(?,?)}");
			cs.setString(1, wsId);
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.execute();
			tranNo = cs.getInt(2);
			cs.close();
			con.close();
			return tranNo;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tranNo;
	}
	
	public synchronized int getNewTranNoForUserSignature(int userCode) {
		int tranNo = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con.prepareCall("{call Proc_newTranNoForSignature(?,?)}");
			cs.setInt(1, userCode);
			cs.registerOutParameter(2, java.sql.Types.INTEGER);
			cs.execute();
			tranNo = cs.getInt(2);
			cs.close();
			con.close();
			return tranNo;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tranNo;
	}
	
	public String updateUserAcknowledgement(DTOWorkSpaceNodeHistory dto) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con.prepareCall("{call Proc_UpdateWorkspaceUserAcknowledge(?,?,?)}");
			cs.setInt(1, dto.getUserCode());
			cs.setInt(2,dto.getUserGroupCode());
			cs.setString(3,dto.getWorkSpaceId());
			cs.execute();
			cs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return "false";
		}
		return "true";
}
}// main class ended
