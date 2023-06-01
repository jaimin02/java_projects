package com.docmgmt.struts.actions.workspace.SubQueryMgmt;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOSubmissionQueryDtl;
import com.docmgmt.dto.DTOSubmissionQueryMst;
import com.docmgmt.dto.DTOSubmissionQueryUserDtl;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOUserProfile;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.master.SubmissionQueryUserDtl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.beans.WorkspaceDynamicNodeCheckTreeBean;
import com.docmgmt.server.webinterface.services.mail.MailService;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PostSubQueryMgmtAction extends ActionSupport {

	private static final long serialVersionUID = 397040114015865635L;
	public Vector<DTOWorkSpaceMst> workspaces;
	public ArrayList<String> statusValues = new ArrayList<String>();
	public String workSpaceId;
	public String workspaceDesc;
	public String htmlContent;
	public String queryTitle;
	public String queryDate;
	public String queryStatus;
	public File refDocUpload;
	public String refDocUploadFileName;
	public String queryDesc;
	public int nodeId;
	public String attach;
	public Long queryId;
	public String seqNo;
	public int tranNo;
	public String saveTargetDiv;
	public String startDate;
	public String endDate;
	public String mode;
	public DTOSubmissionQueryMst dtoSubQueryMst;
	public String fileUploading;
	public String queryIdString = "";
	public String mi;
	public String refDocFullPath;
	public Vector users = new Vector();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public ArrayList<String> listProfileType;
	public ArrayList<String> listProfileSubType;
	public DTOUserMst dtoUserMSt;
	public ArrayList<DTOUserProfile> userProfileDtl;
	DTOUserProfile dtoUserProfile = new DTOUserProfile();

	public String queryDescription;
	StringBuilder builder = new StringBuilder();
	String mailHtml;
	String nodeHtml;
	public ArrayList<String> userEmailList = new ArrayList<String>();

	public int[] userCodedtl = null;
	String status;

	@Override
	public String execute() {

		if (mode.equalsIgnoreCase("add")) {

			mode = "Add";
			try {
				startDate = endDate = dateFormat.format(new Date());
			} catch (Exception e) {
				e.printStackTrace();
			}
			DTOWorkSpaceMst dtoWsMst = docMgmtImpl
					.getWorkSpaceDetail(workSpaceId);
			seqNo = dtoWsMst.getLastPublishedVersion();
			workspaceDesc = dtoWsMst.getWorkSpaceDesc();

			int usergrpcode = Integer.parseInt(ActionContext.getContext()
					.getSession().get("usergroupcode").toString());
			int usercode = Integer.parseInt(ActionContext.getContext()
					.getSession().get("userid").toString());
			// String
			// userType=ActionContext.getContext().getSession().get("usertypecode").toString();
			DTOUserMst userMstFrom = new DTOUserMst();
			userMstFrom.setUserCode(usercode);
			userMstFrom.setUserGroupCode(usergrpcode);

			users = docMgmtImpl
					.getWorkspaceUserDetail(workSpaceId, userMstFrom);
		}
		if (mode.equalsIgnoreCase("edit")) {
			mode = "Edit";
			dtoSubQueryMst = docMgmtImpl
					.getWorkspaceQueryDtlsByQueryId(queryId);

			// getting userdetails
			SubmissionQueryUserDtl dtl = new SubmissionQueryUserDtl();
			users = dtl.getUserDtlsByQueryId(queryId);

			queryTitle = dtoSubQueryMst.getQueryTitle();
			startDate = dtoSubQueryMst.getStartDate().toString().split(" ")[0]
					.replaceAll("-", "/");
			endDate = dtoSubQueryMst.getEndDate().toString().split(" ")[0]
					.replaceAll("-", "/");
			File subQuery = propertyInfo.getDir("PostSubmissionQueryMgmt");
			refDocFullPath = subQuery + "/" + queryId + "/"
					+ dtoSubQueryMst.getRefDoc();
			refDocFullPath = refDocFullPath.replace("\\", "/");
			queryDesc = dtoSubQueryMst.getQueryDesc();
			for (int i = 0; i < dtoSubQueryMst.getSubmissionQueryDtls().size(); i++) {
				queryIdString = queryIdString
						+ Integer.toString(dtoSubQueryMst
								.getSubmissionQueryDtls().get(i).getNodeId())
						+ "_";
			}
			seqNo = dtoSubQueryMst.getSubmissionQueryDtls().get(0).getSeqNo();

		}

		if (mode.equalsIgnoreCase("ChangeStatus")) {
			mode = "ChangeStatus";
			statusValues.add("--Select--");
			statusValues.add("New");
			statusValues.add("In Process");
			statusValues.add("Pending");
			statusValues.add("Resolved");
			dtoSubQueryMst = docMgmtImpl
					.getWorkspaceQueryDtlsByQueryId(queryId);
			File subQuery = propertyInfo.getDir("PostSubmissionQueryMgmt");
			refDocFullPath = subQuery + "/" + queryId + "/"
					+ dtoSubQueryMst.getRefDoc();
			refDocFullPath = refDocFullPath.replace("\\", "/");
			for (int i = 0; i < dtoSubQueryMst.getSubmissionQueryDtls().size(); i++) {
				queryIdString = queryIdString
						+ Integer.toString(dtoSubQueryMst
								.getSubmissionQueryDtls().get(i).getNodeId())
						+ "_";

			}
			seqNo = dtoSubQueryMst.getSubmissionQueryDtls().get(0).getSeqNo();
			return "CHANGESTATUS";
		}
		return SUCCESS;
	}

	public String getWorkspaceTree() {

		int userCode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());
		String userTypeCode = ActionContext.getContext().getSession().get(
				"usertypecode").toString();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");

		WorkspaceDynamicNodeCheckTreeBean treeBean = new WorkspaceDynamicNodeCheckTreeBean();
		treeBean.setUserType(userTypeCode);
		if (mode != null && mode.equalsIgnoreCase("ChangeStatus")) {
			Vector<DTOUserMst> allUsers = docMgmtImpl.getAllUserDetail();
			HashMap<Integer, String> nodeStatusForTree = new HashMap<Integer, String>();

			DTOSubmissionQueryMst dto = docMgmtImpl
					.getWorkspaceQueryDtlsByQueryId(queryId);
			ArrayList<DTOSubmissionQueryDtl> dtoSubDtl = dto
					.getSubmissionQueryDtls();
			ArrayList<Integer> nodeIdList = new ArrayList<Integer>();
			for (int itrDTO = 0; itrDTO < dtoSubDtl.size(); itrDTO++) {
				DTOSubmissionQueryDtl dtoSubQueryDtl = dtoSubDtl.get(itrDTO);
				int nodeIdVal = dtoSubQueryDtl.getNodeId();

				for (Iterator<DTOUserMst> itrUsers = allUsers.iterator(); itrUsers
						.hasNext();) {
					DTOUserMst dtoUserMst = itrUsers.next();
					if (dtoSubQueryDtl.getModifyBy() == dtoUserMst
							.getUserCode()) {
						dtoSubQueryDtl
								.setModifyByName(dtoUserMst.getUserName());
					}
				}
				String modifyOn = dateFormat.format((dtoSubQueryDtl
						.getModifyOn()));
				String queryStatusVal = "&nbsp;&nbsp;<label title='Modified By:"
						+ dtoSubQueryDtl.getModifyByName()
						+ " Modify On: "
						+ (modifyOn)
						+ "'>["
						+ dtoSubQueryDtl.getQueryStatus()
						+ "]</label>";
				nodeIdList.add(nodeIdVal); // this list is to generate the node
				// wise HTML tree.
				nodeStatusForTree.put(nodeIdVal, queryStatusVal);// This is to
				// show the
				// node
				// status
				// for the
				// selected
				// node
				// within
				// tree.
			}
			treeBean.setHtmlAfterNodes(nodeStatusForTree);
			treeBean.setSelectedNodeTree(true);
			htmlContent = treeBean.getSelectedNodeTreeHtml(workSpaceId,
					userGroupCode, userCode, nodeIdList);

		} else {
			Vector<DTOWorkSpaceNodeHistory> uploadedFileNodes = docMgmtImpl
					.getUploadedFileNodes(workSpaceId);
			treeBean.setChkBoxForAllNodes(true);
			htmlContent = treeBean.getWorkspaceTreeHtml(workSpaceId,
					userGroupCode, userCode, uploadedFileNodes);

		}

		return SUCCESS;
	}

	public String saveQuery() {
		int userCode = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		ArrayList<DTOSubmissionQueryDtl> subQueryDtlList = new ArrayList<DTOSubmissionQueryDtl>();
		ArrayList<DTOSubmissionQueryUserDtl> subQueryUserDtlList = new ArrayList<DTOSubmissionQueryUserDtl>();

		HttpServletRequest request = ServletActionContext.getRequest();

		Enumeration<String> enum1 = request.getParameterNames();

		if (mode.equalsIgnoreCase("Edit") || mode.equalsIgnoreCase("Add")) {

			ArrayList<DTOSubmissionQueryMst> subQueryMstList = new ArrayList<DTOSubmissionQueryMst>();
			Timestamp frmDtForquery = null;
			Timestamp toDtForquery = null;

			DTOSubmissionQueryMst dtosubQueryMstLocal = new DTOSubmissionQueryMst();
			try {
				Date frmDt = dateFormat.parse(startDate);
				Date toDt = dateFormat.parse(endDate);
				frmDtForquery = new Timestamp(frmDt.getTime());
				toDtForquery = new Timestamp(toDt.getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
			String filePath = "";
			dtosubQueryMstLocal.setQueryTitle(queryTitle);
			if (mode.equalsIgnoreCase("Edit")) {

				dtoSubQueryMst = docMgmtImpl
						.getWorkspaceQueryDtlsByQueryId(queryId);
				dtosubQueryMstLocal.setQueryId(queryId);
				if (fileUploading.equalsIgnoreCase("Remove")) {
					dtosubQueryMstLocal.setRefDoc(null);
					File subQuery = propertyInfo
							.getDir("PostSubmissionQueryMgmt");
					filePath = queryId + "/" + refDocUploadFileName;
					FileManager.deleteFile(new File(subQuery, filePath));
				} else if (fileUploading.equalsIgnoreCase("New")) {
					dtosubQueryMstLocal.setRefDoc(refDocUploadFileName);
				} else // if(fileUploading.equalsIgnoreCase("Current"));
				{
					dtosubQueryMstLocal.setRefDoc(dtoSubQueryMst.getRefDoc());
				}
			} else if (mode.equalsIgnoreCase("Add")) {
				dtosubQueryMstLocal.setRefDoc(refDocUploadFileName);
			}

			dtosubQueryMstLocal.setQueryDesc(queryDesc);
			dtosubQueryMstLocal.setRemark("");
			dtosubQueryMstLocal.setModifyBy(userCode);
			dtosubQueryMstLocal.setStartDate(frmDtForquery);
			dtosubQueryMstLocal.setEndDate(toDtForquery);

			subQueryMstList.add(dtosubQueryMstLocal);

			queryDescription = dtosubQueryMstLocal.getQueryDesc();
			// System.out.println("Get Query Description::::::::::::::::::::::"+dtosubQueryMstLocal.getQueryDesc());

			if (mode.equalsIgnoreCase("Edit"))
				docMgmtImpl.insertSubmissionQueryMst(subQueryMstList, 2);
			else if (mode.equalsIgnoreCase("Add"))
				queryId = docMgmtImpl.insertSubmissionQueryMst(subQueryMstList,
						1);

			if (queryId != 0) {
				try {
					if (refDocUpload != null) {
						if ((mode.equalsIgnoreCase("Add"))
								|| (mode.equalsIgnoreCase("Edit") && fileUploading
										.equalsIgnoreCase("New"))) {
							File subQuery = propertyInfo
									.getDir("PostSubmissionQueryMgmt");
							filePath = queryId + "/" + refDocUploadFileName;

							FileManager fileManager = new FileManager();
							fileManager.copyDirectory(refDocUpload, new File(
									subQuery, filePath));
						}
					} else {
						refDocUploadFileName = null;
					}

				} catch (Exception e) {

				}
			}

			for (String paramName = ""; enum1.hasMoreElements();) {
				paramName = enum1.nextElement();
				if (paramName.startsWith("CHK_")) {
					int nodeId = Integer.parseInt(paramName.split("_")[1]);
					DTOSubmissionQueryDtl dtoSubQueryDtl = new DTOSubmissionQueryDtl();
					dtoSubQueryDtl.setQueryId(queryId);
					dtoSubQueryDtl.setWorkSpaceId(workSpaceId);
					dtoSubQueryDtl.setSeqNo(seqNo);
					dtoSubQueryDtl.setNodeId(nodeId);
					dtoSubQueryDtl.setTranNo(tranNo);
					dtoSubQueryDtl.setResolved('N');
					dtoSubQueryDtl.setResolvedBy(0);
					dtoSubQueryDtl.setResolvedDate(null);
					dtoSubQueryDtl.setQueryStatus("New");
					dtoSubQueryDtl.setRemark("");
					dtoSubQueryDtl.setModifyBy(userCode);

					subQueryDtlList.add(dtoSubQueryDtl);
				}
			}
			// Adding following condition for inserting User Details : added on
			// 22/05/2013 by butani vijay
			// Description : Send Query to selected users only. record store in
			// SubmissionQueryUserDtl table.

			if (userCodedtl != null && userCodedtl.length > 0) {

				for (int i = 0; i < userCodedtl.length; i++) {
					DTOSubmissionQueryUserDtl dtoSubQueryUserDtl = new DTOSubmissionQueryUserDtl();
					dtoSubQueryUserDtl.setQueryId(queryId);
					dtoSubQueryUserDtl.setUserid(userCodedtl[i]);

					dtoSubQueryUserDtl.setModifyBy(userCode);
					dtoSubQueryUserDtl.setRemark("");
					dtoSubQueryUserDtl.setStatusIndi('N');

					subQueryUserDtlList.add(dtoSubQueryUserDtl);

					dtoUserMSt = docMgmtImpl.getUserByCode(dtoSubQueryUserDtl
							.getUserid());

					// start getting user profile data

					userProfileDtl = docMgmtImpl
							.getUserProfileDetails(dtoUserMSt.getUserCode());

					System.out.println("User to assign on query :::::::"
							+ dtoUserMSt.getUserCode());
					for (int itrUserProfile = 0; itrUserProfile < userProfileDtl
							.size(); itrUserProfile++) {

						userEmailList.add(userProfileDtl.get(itrUserProfile)
								.getProfilevalue());

					}
					// end getting user profile data
				}
			}
			if (subQueryUserDtlList.size() > 0) {
				if (mode.equalsIgnoreCase("Add")) {
					docMgmtImpl.insertSubmissionQueryUserDtl(
							subQueryUserDtlList, 1);
					// htmlContent = "Query Added Successfully.";
				}
				if (mode.equalsIgnoreCase("Edit")) {
					// First Delete old and Then Add New..mode=3 [3=Delete check
					// in procedure]
					docMgmtImpl.deleteSubmissionQueryUserDtl(queryId);
					docMgmtImpl.insertSubmissionQueryUserDtl(
							subQueryUserDtlList, 1);
					// htmlContent = "Query Added Successfully.";
				}

			} else {
				System.out.println("email is empty");
			}

			if (subQueryDtlList.size() > 0) {
				if (mode.equalsIgnoreCase("Add")) {
					docMgmtImpl.insertSubmissionQueryDtl(subQueryDtlList, 1);
					htmlContent = "Query Added Successfully.";
				} else if (mode.equalsIgnoreCase("Edit")
						|| mode.equalsIgnoreCase("ChangeStatus")) {
					ArrayList<DTOSubmissionQueryDtl> submissionQueryDtlsOld = dtoSubQueryMst
							.getSubmissionQueryDtls();

					ArrayList<DTOSubmissionQueryDtl> submissionQueryDtlsForAdd = new ArrayList<DTOSubmissionQueryDtl>();
					ArrayList<DTOSubmissionQueryDtl> submissionQueryDtlsForDelete = new ArrayList<DTOSubmissionQueryDtl>();

					for (int itrCheckNode = 0; itrCheckNode < submissionQueryDtlsOld
							.size(); itrCheckNode++) {
						DTOSubmissionQueryDtl dtoOld = submissionQueryDtlsOld
								.get(itrCheckNode);
						for (int itrCurrList = 0; itrCurrList < subQueryDtlList
								.size(); itrCurrList++) {

							DTOSubmissionQueryDtl dtoCurr = subQueryDtlList
									.get(itrCurrList);
							if (dtoOld.getNodeId() == dtoCurr.getNodeId()) {
								subQueryDtlList.remove(itrCurrList);
								submissionQueryDtlsOld.remove(itrCheckNode);
								itrCheckNode--;
								itrCurrList--;
								break;
							}
						}
					}
					submissionQueryDtlsForAdd.addAll(subQueryDtlList);
					submissionQueryDtlsForDelete.addAll(submissionQueryDtlsOld);
					docMgmtImpl.insertSubmissionQueryDtl(
							submissionQueryDtlsForAdd, 1);
					docMgmtImpl.insertSubmissionQueryDtl(
							submissionQueryDtlsForDelete, 3);

					htmlContent = "Query Edited Successfully.";
				}
			} else
				htmlContent = "Please select any one node..";
		}

		else if (mode.equalsIgnoreCase("ChangeStatus")) {

			try {

				for (String paramName = ""; enum1.hasMoreElements();) {

					paramName = enum1.nextElement();

					if (paramName.startsWith("CHK_")) {
						int nodeId = Integer.parseInt(paramName.split("_")[1]);
						DTOSubmissionQueryDtl dtoSubQueryDtl = new DTOSubmissionQueryDtl();
						dtoSubQueryDtl.setQueryId(queryId);
						dtoSubQueryDtl.setWorkSpaceId(workSpaceId);
						dtoSubQueryDtl.setSeqNo(seqNo);
						dtoSubQueryDtl.setNodeId(nodeId);
						dtoSubQueryDtl.setTranNo(tranNo);
						if (queryStatus.equalsIgnoreCase("Resolved")) {
							dtoSubQueryDtl.setResolved('Y');
							dtoSubQueryDtl.setResolvedBy(userCode);
							dtoSubQueryDtl.setResolvedDate(new Timestamp(System
									.currentTimeMillis()));
						}
						dtoSubQueryDtl.setQueryStatus(queryStatus);
						dtoSubQueryDtl.setRemark("");
						dtoSubQueryDtl.setModifyBy(userCode);

						subQueryDtlList.add(dtoSubQueryDtl);

						status = dtoSubQueryDtl.getQueryStatus();
					}
				}

			} catch (Exception e) {

				e.printStackTrace();
			}

			if (subQueryDtlList.size() > 0) {
				docMgmtImpl.insertSubmissionQueryDtl(subQueryDtlList, 2);
				htmlContent = "Status Changed Successfully.";

				// getting query management email list
				SubmissionQueryUserDtl dtl = new SubmissionQueryUserDtl();
				Vector<DTOUserMst> users = dtl.getUserDtlsByQueryId(queryId);

				for (int index = 0; index < users.size(); index++) {

					System.out.println("Users code :::::"
							+ users.get(index).getUserCode());
					userProfileDtl = docMgmtImpl.getUserProfileDetails(users
							.get(index).getUserCode());

					for (int itrUserProfile = 0; itrUserProfile < userProfileDtl
							.size(); itrUserProfile++) {

						userEmailList.add(userProfileDtl.get(itrUserProfile)
								.getProfilevalue());

					}

				}

				// end query management email list

				System.out.println("Size of User details vector:::::"
						+ users.size());

				// code for to change status of node and save selected node
				// value added by dharmendra jadav on 21-4-2015
				DTOWorkSpaceMst dtoWsMst = docMgmtImpl
						.getWorkSpaceDetail(workSpaceId);
				workspaceDesc = dtoWsMst.getWorkSpaceDesc();

				dtoSubQueryMst = docMgmtImpl
						.getWorkspaceQueryDtlsByQueryId(queryId);

				queryTitle = dtoSubQueryMst.getQueryTitle();

				startDate = dtoSubQueryMst.getStartDate().toString().split(" ")[0]
						.replaceAll("-", "/");
				endDate = dtoSubQueryMst.getEndDate().toString().split(" ")[0]
						.replaceAll("-", "/");

				int userGroupCode = Integer.parseInt(ActionContext.getContext()
						.getSession().get("usergroupcode").toString());
				String userTypeCode = ActionContext.getContext().getSession()
						.get("usertypecode").toString();

				WorkspaceDynamicNodeCheckTreeBean treeBean = new WorkspaceDynamicNodeCheckTreeBean();
				treeBean.setUserType(userTypeCode);

				Vector<DTOUserMst> allUsers = docMgmtImpl.getAllUserDetail();
				HashMap<Integer, String> nodeStatusForTree = new HashMap<Integer, String>();

				DTOSubmissionQueryMst dto = docMgmtImpl
						.getWorkspaceQueryDtlsByQueryId(queryId);

				ArrayList<DTOSubmissionQueryDtl> dtoSubDtl = dto
						.getSubmissionQueryDtls();
				ArrayList<Integer> nodeIdList = new ArrayList<Integer>();
				for (int itrDTO = 0; itrDTO < dtoSubDtl.size(); itrDTO++) {
					DTOSubmissionQueryDtl dtoSubQueryDtl = dtoSubDtl
							.get(itrDTO);
					int nodeIdVal = dtoSubQueryDtl.getNodeId();

					for (Iterator<DTOUserMst> itrUsers = allUsers.iterator(); itrUsers
							.hasNext();) {
						DTOUserMst dtoUserMst = itrUsers.next();
						if (dtoSubQueryDtl.getModifyBy() == dtoUserMst
								.getUserCode()) {
							dtoSubQueryDtl.setModifyByName(dtoUserMst
									.getUserName());
						}
					}
					String modifyOn = dateFormat.format((dtoSubQueryDtl
							.getModifyOn()));
					String queryStatusVal = "&nbsp;&nbsp;<label title='Modified By:"
							+ dtoSubQueryDtl.getModifyByName()
							+ " Modify On: "
							+ (modifyOn)
							+ "'>["
							+ dtoSubQueryDtl.getQueryStatus() + "]</label>";
					nodeIdList.add(nodeIdVal);
					nodeStatusForTree.put(nodeIdVal, queryStatusVal);

				}
				treeBean.setHtmlAfterNodesData(nodeStatusForTree);
				treeBean.setSelectedNodeTree(true);
				nodeHtml = treeBean.getSelectedNodeTreeHtmlData(workSpaceId,
						userGroupCode, userCode, nodeIdList);
				
				prepareMail();
				sendMail();
				
			}
		}

		// code added by dharmendra k jadav on 21-4-2015

		// start generate html table for add query management

		if (mode.equalsIgnoreCase("Add")) {
			int userGroupCode = Integer.parseInt(ActionContext.getContext()
					.getSession().get("usergroupcode").toString());
			String userTypeCode = ActionContext.getContext().getSession().get(
					"usertypecode").toString();

			WorkspaceDynamicNodeCheckTreeBean treeBean = new WorkspaceDynamicNodeCheckTreeBean();
			treeBean.setUserType(userTypeCode);
			Vector<DTOUserMst> allUsers = docMgmtImpl.getAllUserDetail();
			HashMap<Integer, String> nodeStatusForTree = new HashMap<Integer, String>();

			DTOSubmissionQueryMst dto = docMgmtImpl
					.getWorkspaceQueryDtlsByQueryId(queryId);
			ArrayList<DTOSubmissionQueryDtl> dtoSubDtl = dto
					.getSubmissionQueryDtls();
			ArrayList<Integer> nodeIdList = new ArrayList<Integer>();
			for (int itrDTO = 0; itrDTO < dtoSubDtl.size(); itrDTO++) {
				DTOSubmissionQueryDtl dtoSubQueryDtl = dtoSubDtl.get(itrDTO);
				int nodeIdVal = dtoSubQueryDtl.getNodeId();

				for (Iterator<DTOUserMst> itrUsers = allUsers.iterator(); itrUsers
						.hasNext();) {
					DTOUserMst dtoUserMst = itrUsers.next();
					if (dtoSubQueryDtl.getModifyBy() == dtoUserMst
							.getUserCode()) {
						dtoSubQueryDtl
								.setModifyByName(dtoUserMst.getUserName());
					}
				}
				String modifyOn = dateFormat.format((dtoSubQueryDtl
						.getModifyOn()));
				String queryStatusVal = "&nbsp;&nbsp;<label title='Modified By:"
						+ dtoSubQueryDtl.getModifyByName()
						+ " Modify On: "
						+ (modifyOn)
						+ "'>["
						+ dtoSubQueryDtl.getQueryStatus()
						+ "]</label>";
				nodeIdList.add(nodeIdVal);
				nodeStatusForTree.put(nodeIdVal, queryStatusVal);
			}
			treeBean.setHtmlAfterNodesData(nodeStatusForTree);
			treeBean.setSelectedNodeTree(true);
			nodeHtml = treeBean.getSelectedNodeTreeHtmlData(workSpaceId,
					userGroupCode, userCode, nodeIdList);

			DTOWorkSpaceMst dtoWsMst = docMgmtImpl
					.getWorkSpaceDetail(workSpaceId);
			workspaceDesc = dtoWsMst.getWorkSpaceDesc();
		
			prepareMail();
			sendMail();
			
		}

		// Ending generate html table of added query management
		System.out.println(htmlContent);
		return SUCCESS;
	}
	
	private void prepareMail(){
		
		builder.append("<html>");
		builder
				.append("<head><title>Query Management</title></head>");
		builder.append("<body>");
		builder.append("<div>");
		builder.append("<div id='header'>");
		builder.append("<span><b>Dear KnowledgeNET Users,</b></span>");
		builder.append("</div>");
		builder.append("<br>");
		builder.append("<div id='data'>");
		builder.append("<table border=\"0\">");
		builder.append("<tr>");
		builder.append("<td style='font-size: 14px;'> " + "Project Name"
				+ "</td>" + "<td style='font-size: 14px;'> " + " : <b>"
				+ workspaceDesc + "</b></td>");
		builder.append(" </tr>  ");
		builder.append("<tr>");
		builder.append("<td style='font-size: 14px;'> "
				+ "Last confirmed Seq.No " + "</td>"
				+ "<td style='font-size: 14px;'> " + " : " + seqNo
				+ "</td>");
		builder.append(" </tr>  ");
		builder.append("<tr>");
		builder.append("<td style='font-size: 14px;'> " + "Title" + "</td>"
				+ "<td style='font-size: 14px;'> " + " : " + queryTitle
				+ "</td>");
		builder.append(" </tr>  ");
		builder.append("<tr>");
		builder.append("<td style='font-size: 14px;'> " + "Arrived Date"
				+ "</td>" + "<td style='font-size: 14px;'> " + " : "
				+ startDate + "</td>");
		builder.append(" </tr>  ");
		builder.append("<tr>");
		builder.append("<td style='font-size: 14px;'> " + "Resolve Date"
				+ "</td>" + "<td style='font-size: 14px;'> " + " : "
				+ endDate + "</td>");
		builder.append(" </tr>  ");
		builder.append("<tr>");
		builder.append("<td style='font-size: 14px;'> " + "Description"
				+ "</td>" + "<td style='font-size: 14px;'> " + " : "
				+ queryDesc + "</td>");
		builder.append(" </tr>  ");
		builder.append("<tr>");
		builder
				.append("<td style='font-size: 14px; vertical-align: top;'> "
						+ "Nodes Affected"
						+ "</td>"
						+ "<td style='font-size: 14px;'> "
						+ " : "
						+ nodeHtml + "</td>");
		builder.append(" </tr>  ");
		builder.append("</table>");
		builder.append("</div><br>");
		builder.append("<div id='footer'>");
		builder.append("<span><b>Regards,</b></span><br>");
		builder.append("<span><b>KnowledgeNET</b></span><br>");
		builder
				.append("<span><a href='http://www.knowledgenet.in' target='_blank'>www.knowledgenet.in</a></span><br>");
		builder.append("</div>");
		builder.append("</div></body>");
		builder.append("</html>");
		mailHtml = builder.toString();
		System.out.println(mailHtml);
	
	}
	private void sendMail(){
		
		if (userEmailList != null && userEmailList.size() > 0) {

			String mailList = "";

			for (String value : userEmailList) {

				mailList += value + ",";
			}
			
			PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
			 boolean allowQueryMail = propertyInfo.getValue("SendPostQueryMail")
	        	.equalsIgnoreCase("yes") ? true : false;

			
			if (allowQueryMail) {
				try {
					MailService ms = new MailService();
					ms.sendEmail(mailList, null, null, "Query Management:"
							+ queryTitle, mailHtml, null, true);
					
					System.out.println("Successfully mail send::");
					
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					System.out.println("Adress Exception::" + e.getMessage());
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					System.out
							.println("Messaging Exception::" + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

}
