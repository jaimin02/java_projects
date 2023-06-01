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
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.UUID;
import java.util.Vector;

import com.docmgmt.dto.DTOAssignNodeRights;
import com.docmgmt.dto.DTOSaveProjectAs;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.dto.DTOWorkspaceNodeReferenceDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;

import javawebparts.core.org.apache.commons.lang.StringUtils;

public class WorkSpaceNodeHistory {
	DataTable dataTable;

	public WorkSpaceNodeHistory() {
		dataTable = new DataTable();
	}

	public Vector<DTOWorkSpaceNodeHistory> getNodeHistory(String wsId, int nodeId) {
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeHistory> dataNodeHis = new Vector<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con.prepareCall("{call Proc_getNodeHistory(?,?)}");
			cs.setString(1, wsId);
			cs.setInt(2, nodeId);
			ResultSet rsNodeHis = cs.executeQuery();
			while (rsNodeHis.next()) {
				DTOWorkSpaceNodeHistory workSpaceNodeHistoryDTO = new DTOWorkSpaceNodeHistory();
				workSpaceNodeHistoryDTO.setWorkSpaceId(rsNodeHis.getString("WorkSpaceId"));
				workSpaceNodeHistoryDTO.setNodeId(rsNodeHis.getInt("NodeId"));
				workSpaceNodeHistoryDTO.setTranNo(rsNodeHis.getInt("TranNo"));
				workSpaceNodeHistoryDTO.setStageId(rsNodeHis.getInt("stageId"));
				workSpaceNodeHistoryDTO.setStageDesc(rsNodeHis.getString("stageDesc"));
				workSpaceNodeHistoryDTO.setFileName(rsNodeHis.getString("FileName"));
				workSpaceNodeHistoryDTO.setFileType(rsNodeHis.getString("FileType"));
				workSpaceNodeHistoryDTO.setFolderName(rsNodeHis.getString("FolderName"));
				workSpaceNodeHistoryDTO.setAttriId(rsNodeHis.getInt("AttrId"));
				workSpaceNodeHistoryDTO.setAttrName(rsNodeHis.getString("AttrName"));
				workSpaceNodeHistoryDTO.setAttrValue(rsNodeHis.getString("AttrValue"));
				workSpaceNodeHistoryDTO.setUserName(rsNodeHis.getString("UserName"));
				workSpaceNodeHistoryDTO.setModifyOn(rsNodeHis.getTimestamp("ModifyOn"));
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(workSpaceNodeHistoryDTO.getModifyOn(), locationName,
							countryCode);
					workSpaceNodeHistoryDTO.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(workSpaceNodeHistoryDTO.getModifyOn(), locationName,
							countryCode);
					workSpaceNodeHistoryDTO.setISTDateTime(time.get(0));
					workSpaceNodeHistoryDTO.setESTDateTime(time.get(1));
				}
				workSpaceNodeHistoryDTO.setBaseWorkFolder(rsNodeHis.getString("BaseWorkFolder"));
				workSpaceNodeHistoryDTO.setWorkSpaceDesc(rsNodeHis.getString("workspaceDesc"));
				if (workSpaceNodeHistoryDTO.getFileName() != null) {
					if ((!workSpaceNodeHistoryDTO.getFileName().equalsIgnoreCase("null"))
							&& (workSpaceNodeHistoryDTO.getFileName().length() > 0)) {
						String[] splitFileName = workSpaceNodeHistoryDTO.getFileName().split("\\.");
						String fileExt = splitFileName[splitFileName.length - 1];
						workSpaceNodeHistoryDTO.setFileExt(fileExt);
						dataNodeHis.addElement(workSpaceNodeHistoryDTO);
					} else {
						dataNodeHis = null;
					}
				}
				workSpaceNodeHistoryDTO.setRoleName(rsNodeHis.getString("vRoleName"));
				workSpaceNodeHistoryDTO.setCertifiedByName(rsNodeHis.getString("CertifiedByName"));
				workSpaceNodeHistoryDTO = null;
			}
			rsNodeHis.close();
			cs.close();
			con.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return dataNodeHis;
	}

	public void insertAssignedNodeRight(String wsId, int nodeId, int tranNo, int iStageId, int userCode, int Mode,
			String flag) {

		// 1-Insert
		// 2-Delete

		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Insert_AssignedNodeRights(?,?,?,?,?,?,?)}");
			proc.setString(1, wsId);
			proc.setInt(2, nodeId);
			proc.setInt(3, tranNo);
			proc.setInt(4, iStageId);
			proc.setInt(5, userCode);
			proc.setString(6, flag);
			proc.setInt(7, Mode);
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void insertNodeHistory(DTOWorkSpaceNodeHistory dto) {
		String folderName = "";
		if (dto.getFolderName().equals("")) {
			folderName = "/" + dto.getWorkSpaceId() + "/" + dto.getNodeId() + "/" + dto.getTranNo();
		} else {
			folderName = dto.getFolderName();
		}
		System.out.println("Folder NBame(NodeId==>" + dto.getParentID());
		// here if the inodeid > total nodeid we get the parent nodeid
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Insert_workspaceNodeHistory(?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			proc.setInt(3, dto.getTranNo());
			proc.setString(4, dto.getFileName());
			proc.setString(5, dto.getFileType());
			proc.setString(6, folderName);
			proc.setString(7, Character.toString(dto.getRequiredFlag()));
			proc.setInt(8, dto.getStageId());
			proc.setString(9, dto.getRemark());
			proc.setInt(10, dto.getModifyBy());
			proc.setString(11, Character.toString(dto.getStatusIndi()));
			proc.setString(12, dto.getDefaultFileFormat());
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertNodeHistoryWithRoleCode(DTOWorkSpaceNodeHistory dto) {
		String folderName = "";
		if (dto.getFolderName().equals("")) {
			folderName = "/" + dto.getWorkSpaceId() + "/" + dto.getNodeId() + "/" + dto.getTranNo();
		} else {
			folderName = dto.getFolderName();
		}
		System.out.println("Folder NBame(NodeId==>" + dto.getParentID());
		// here if the inodeid > total nodeid we get the parent nodeid

		// check if history is available, if yes then no UUID generation, else
		// generate
		UUID uuId = null;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeHistory> getNodeHistory = docMgmtImpl.getMaxNodeHistoryByTranNo(dto.getWorkSpaceId(),
				dto.getNodeId());
		if (getNodeHistory.size() <= 0 || getNodeHistory.get(0).getStageId() == 0) {
			uuId = UUID.randomUUID();
			dto.setUuId(uuId.toString());
		} else {
			if (dto.getStageId() == 10) {
				uuId = UUID.randomUUID();
				dto.setUuId(uuId.toString());
			} else {
				if (getNodeHistory.get(0).getUuId() != null) {
					dto.setUuId(getNodeHistory.get(0).getUuId());
				}
			}
		}
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con
					.prepareCall("{ Call Insert_workspaceNodeHistory(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			proc.setInt(3, dto.getTranNo());
			proc.setString(4, dto.getFileName());
			proc.setString(5, dto.getFileType());
			proc.setString(6, folderName);
			proc.setString(7, Character.toString(dto.getRequiredFlag()));
			proc.setInt(8, dto.getStageId());
			proc.setString(9, dto.getRemark());
			proc.setInt(10, dto.getModifyBy());
			proc.setString(11, Character.toString(dto.getStatusIndi()));
			proc.setString(12, dto.getDefaultFileFormat());
			proc.setString(13, dto.getRoleCode());
			proc.setDouble(14, dto.getFileSize());
			if (dto.getUuId() != null)
				proc.setString(15, dto.getUuId());
			else
				proc.setString(15, "");
			proc.setInt(16, dto.getCertifiedBy());
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertNodeHistoryWithRefFile(DTOWorkSpaceNodeHistory dto) {
		String folderName = "";
		if (dto.getFolderName().equals("")) {
			folderName = "/" + dto.getWorkSpaceId() + "/" + dto.getNodeId() + "/" + dto.getTranNo();
		} else {
			folderName = dto.getFolderName();
		}
		System.out.println("Folder NBame(NodeId==>" + dto.getParentID());
		// here if the inodeid > total nodeid we get the parent nodeid

		// check if history is available, if yes then no UUID generation, else
		// generate
		UUID uuId = null;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeHistory> getNodeHistory = docMgmtImpl.getMaxNodeHistoryByTranNo(dto.getWorkSpaceId(),
				dto.getNodeId());
		if (getNodeHistory.size() <= 0 || getNodeHistory.get(0).getStageId() == 0) {
			uuId = UUID.randomUUID();
			dto.setUuId(uuId.toString());
		} else {
			if (dto.getStageId() == 10) {
				uuId = UUID.randomUUID();
				dto.setUuId(uuId.toString());
			} else {
				if (getNodeHistory.get(0).getUuId() != null) {
					dto.setUuId(getNodeHistory.get(0).getUuId());
				}
			}
		}
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con
					.prepareCall("{ Call Insert_workspaceNodeHistoryWithRefFile(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			proc.setInt(3, dto.getTranNo());
			proc.setString(4, dto.getFileName());
			proc.setString(5, dto.getFileType());
			proc.setString(6, folderName);
			proc.setString(7, Character.toString(dto.getRequiredFlag()));
			proc.setInt(8, dto.getStageId());
			proc.setString(9, dto.getRemark());
			proc.setInt(10, dto.getModifyBy());
			proc.setString(11, Character.toString(dto.getStatusIndi()));
			proc.setString(12, dto.getDefaultFileFormat());
			proc.setString(13, dto.getRoleCode());
			proc.setDouble(14, dto.getFileSize());
			if (dto.getUuId() != null)
				proc.setString(15, dto.getUuId());
			else
				proc.setString(15, "");
			proc.setInt(16, dto.getCertifiedBy());
			proc.setString(17, dto.getRefFileName());
			proc.setString(18, dto.getRefFilePath());
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertRefFileDetail(DTOWorkSpaceNodeHistory dto) {

		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Insert_workspacenodeAttachment(?,?,?,?,?,?,?,?)}");

			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			proc.setString(3, dto.getRefFileName());
			proc.setString(4, dto.getRefFilePath());
			proc.setString(5, dto.getRemark());
			proc.setInt(6, dto.getModifyBy());
			proc.setDouble(7, dto.getFileSize());
			proc.setInt(8, dto.getTranNo());
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// use in publish Logic
	public Vector<DTOWorkSpaceNodeHistory> getFileNameForNodeForPublish(int NodeId, String workSpaceId, int LabelNo) {
		Vector<DTOWorkSpaceNodeHistory> fileName = new Vector<DTOWorkSpaceNodeHistory>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con.prepareCall("{call Proc_FileNameForPublish(?,?,?)}");
			cs.setString(1, workSpaceId);
			cs.setInt(2, NodeId);
			cs.setInt(3, LabelNo);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFolderName(rs.getString("vFolderName"));
				fileName.addElement(dto);
				dto = null;
			}
			cs.close();
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fileName;
	}

	public Vector<DTOWorkSpaceNodeHistory> getFileNameForNode(int NodeId, String workSpaceId) {
		Vector<DTOWorkSpaceNodeHistory> fileName = new Vector<DTOWorkSpaceNodeHistory>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "Distinct vWorkspaceId,iNodeId,vFileName,vPathFolderName",
					"View_CommonWorkspaceDetail", "vWorkspaceId='" + workSpaceId + "' and iNodeId=" + NodeId, "");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFolderName(rs.getString("vPathFolderName"));
				fileName.addElement(dto);
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fileName;
	}

	public Vector<DTOWorkSpaceNodeHistory> getLastNodeHistory(String wsId, int nodeId) {
		Vector<DTOWorkSpaceNodeHistory> nodeHistoryData = new Vector<DTOWorkSpaceNodeHistory>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "Distinct HistoryTranNo,vFileName,vFolderName",
					"View_CommonWorkspaceDetail", "vWorkspaceId='" + wsId + "' and iNodeId=" + nodeId, "");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setTranNo(rs.getInt("HistoryTranNo"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFolderName(rs.getString("vFolderName"));
				nodeHistoryData.addElement(dto);
				dto = null;
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistoryData;
	}

	public DTOWorkSpaceNodeHistory getWorkspaceNodeHistorybyTranNo(String wsId, int nodeId, int tranNo) {
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String fileExt = propertyInfo.getValue("FileExt");
		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		String temp[] = fileExt.split(",");

		String LikeQuerry = "";
		for (int i = 0; i < temp.length; i++) {

			LikeQuerry += "vFilename LIKE ";
			LikeQuerry += "'%." + temp[i] + "'";
			LikeQuerry += " OR ";
		}

		for (int j = 0; j < 4; j++) {
			LikeQuerry = StringUtils.chop(LikeQuerry);
		}
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("vworkspaceId = '" + wsId + "'and inodeId = " + nodeId);
			sb.append(" and iTranNo = " + tranNo);

			sb.append(" AND ( " + LikeQuerry
					+ " ) AND iTranNo >(SELECT ISNULL(MAX(iTranNo),0) FROM InternalLabelHistory WHERE vWorkspaceId = '"
					+ wsId
					+ "' AND iLabelNo =(SELECT ISNULL(MAX(iLabelNo),0) FROM InternalLabelMst WHERE vWorkspaceId = '"
					+ wsId + "'  AND vLabelId IN(SELECT vLabelId FROM View_SubmissionInfoDMSDtl WHERE vWorkspaceId = '"
					+ wsId
					+ "'  AND vCurrentSeqNumber =(SELECT MAX(vCurrentSeqNumber) FROM View_SubmissionInfoDMSDtl where vWorkspaceId = '"
					+ wsId + "' AND cConfirm = 'Y'))))");
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "*", "workspaceNodeHistory", sb.toString(), "");
			if (rs.next()) {

				dto.setWorkSpaceId(rs.getString("vWorkspaceId")); // workspaceId
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setTranNo(rs.getInt("itranNo")); // tranNo
				dto.setFileName(rs.getString("vFileName")); // fileName
				dto.setFileType(rs.getString("vFileType")); // fileType
				dto.setFolderName(rs.getString("vFolderName")); // folderName
				dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
				dto.setRemark(rs.getString("vRemark")); // remark
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
				dto.setStageId(rs.getInt("istageId")); // stageId
				dto.setFileSize(rs.getDouble("nSize")); // stageId

			}
			rs.close();

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public DTOWorkSpaceNodeHistory getWorkspaceNodeHistorybyTranNoOpenAction(String wsId, int nodeId, int tranNo) {

		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String fileExt = propertyInfo.getValue("FileExt");
		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		String temp[] = fileExt.split(",");

		String LikeQuerry = "";
		for (int i = 0; i < temp.length; i++) {

			LikeQuerry += "vFilename LIKE ";
			LikeQuerry += "'%." + temp[i] + "'";
			LikeQuerry += " OR ";
		}

		for (int j = 0; j < 4; j++) {
			LikeQuerry = StringUtils.chop(LikeQuerry);
		}
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("vworkspaceId = '" + wsId + "'and inodeId = " + nodeId);
			sb.append(" and iTranNo = " + tranNo);

			sb.append(" AND ( " + LikeQuerry
					+ ") and iTranNo >=(SELECT ISNULL(MAX(iTranNo),0) FROM InternalLabelHistory WHERE vWorkspaceId = '"
					+ wsId + "and inodeId = " + nodeId
					+ "' AND iLabelNo =(SELECT ISNULL(MAX(iLabelNo),0) FROM InternalLabelMst WHERE vWorkspaceId = '"
					+ wsId + "'  AND vLabelId IN(SELECT vLabelId FROM View_SubmissionInfoDMSDtl WHERE vWorkspaceId = '"
					+ wsId
					+ "'  AND vCurrentSeqNumber =(SELECT MAX(vCurrentSeqNumber) FROM View_SubmissionInfoDMSDtl where vWorkspaceId = '"
					+ wsId + "' AND cConfirm = 'Y'))))");
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "*", "workspaceNodeHistory", sb.toString(), "");
			if (rs.next()) {

				dto.setWorkSpaceId(rs.getString("vWorkspaceId")); // workspaceId
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setTranNo(rs.getInt("itranNo")); // tranNo
				dto.setFileName(rs.getString("vFileName")); // fileName
				dto.setFileType(rs.getString("vFileType")); // fileType
				dto.setFolderName(rs.getString("vFolderName")); // folderName
				dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
				dto.setRemark(rs.getString("vRemark")); // remark
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
				dto.setStageId(rs.getInt("istageId")); // stageId

			}
			rs.close();

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public DTOWorkSpaceNodeHistory getWorkspaceNodeHistoryforAttr(String wsId, int nodeId, int flag) {

		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("vworkspaceId = '" + wsId + "'and inodeId = " + nodeId);

			if (flag == 0) {
				sb.append("and vAttrName='ReferenceText'");
			}

			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "*", "view_NodeSubmissionHistorywithAttrDtl", sb.toString(), "");
			if (rs.next()) {

				dto.setWorkSpaceId(rs.getString("vWorkspaceId")); // workspaceId
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dto.setTranNo(rs.getInt("itranNo")); // tranNo
				dto.setFileName(rs.getString("vFileName")); // fileName
				dto.setFolderName(rs.getString("vFolderName")); // folderName
				dto.setAttriId(rs.getInt("iAttrId"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setAttrName(rs.getString("vAttrName"));
				dto.setAttrValue(rs.getString("vAttrValue"));
				dto.setAttrForIndiId(rs.getString("vAttrForIndiId"));
				// dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0));
				// // requiredFlag
				dto.setRemark(rs.getString("vRemark")); // remark
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				// dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); //
				// statusIndi
				// dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat"));
				// // defaultFileFormat
				dto.setStageId(rs.getInt("istageId")); // stageId

			}
			rs.close();

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public DTOWorkSpaceNodeHistory getWorkspaceNodeHistorybyTranNoforAttribute(String wsId, int nodeId, int tranNo) {

		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("vworkspaceId = '" + wsId + "'and inodeId = " + nodeId);
			sb.append(" and iTranNo = " + tranNo);

			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "*", "workspaceNodeHistory", sb.toString(), "");
			if (rs.next()) {

				dto.setWorkSpaceId(rs.getString("vWorkspaceId")); // workspaceId
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setTranNo(rs.getInt("itranNo")); // tranNo
				dto.setFileName(rs.getString("vFileName")); // fileName
				dto.setFileType(rs.getString("vFileType")); // fileType
				dto.setFolderName(rs.getString("vFolderName")); // folderName
				dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
				dto.setRemark(rs.getString("vRemark")); // remark
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
				dto.setStageId(rs.getInt("istageId")); // stageId

			}
			rs.close();

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public int copyFilesForProjectSaveAs(DTOSaveProjectAs dto) {
		int noOfFiles = 0;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			WorkspaceNodeDetail wsnd = new WorkspaceNodeDetail();

			String swsId = dto.getSourcWsId();
			String dwsId = dto.getDestWsId();
			String selectedNodes[] = dto.getSelectedNodes();
			Vector<String> getLeafNodes = new Vector<String>();
			boolean foundInArray = false;
			int iModifyBy = dto.getIModifyBy();

			// System.out.println(swsId + " " + dwsId + " " + iModifyBy + " " +
			// selectedNodes.length + " " + dto.getSelectedNodes().length);

			Vector<Integer> allChildNodes = new Vector<Integer>();
			int count = 0;
			WorkspaceNodeDetail workSpaceNodeDetail = new WorkspaceNodeDetail();

			for (int i = 0; i < selectedNodes.length; i++) {

				int val = workSpaceNodeDetail.isLeafNodes(swsId, Integer.parseInt(selectedNodes[i]));
				if (val == 0) {
					allChildNodes = workSpaceNodeDetail.getAllChildNodes(swsId, Integer.parseInt(selectedNodes[i]),
							allChildNodes);
					// System.out.println("----------------" +
					// allChildNodes.size() + "-------------------- Of ---" +
					// selectedNodes[i]);
					for (int j = 0; j < allChildNodes.size(); j++) {
						val = workSpaceNodeDetail.isLeafNodes(swsId, Integer.parseInt(allChildNodes.get(j).toString()));
						if (val == 1) {
							for (int k = 0; k < getLeafNodes.size(); k++) {
								if (getLeafNodes.get(k).toString().equals(allChildNodes.get(j).toString())) {
									foundInArray = true;
								}
							}

							if (foundInArray == false) {
								getLeafNodes.add(count, allChildNodes.get(j).toString());
								count++;
							}
							foundInArray = false;
						}
					}
				} else {

					for (int k = 0; k < getLeafNodes.size(); k++) {
						if (getLeafNodes.get(k).toString().equals(selectedNodes[i])) {
							foundInArray = true;
						}
					}

					if (foundInArray == false) {
						getLeafNodes.add(count, selectedNodes[i]);
						count++;
					}
					foundInArray = false;
				}
			}

			System.out.println("T-1");

			CallableStatement cs = con.prepareCall("{call Insert_ProjectSaveAs(?,?,?,?)}");

			System.out.println("T-2");
			for (int i = 0; i < getLeafNodes.size(); i++) {
				// System.out.println(" Selected Node: " + getLeafNodes.get(i));

				System.out.println("T-3");
				int isLeafNode = wsnd.isLeafNodes(swsId, Integer.parseInt(getLeafNodes.get(i).toString()));
				// System.out.println(" Is Leaf Node: " + isLeafNode);

				System.out.println("T-4");
				if (isLeafNode == 1) { // is leafnode true

					cs.setString(1, swsId);
					cs.setString(2, dwsId);
					cs.setInt(3, Integer.parseInt(getLeafNodes.get(i).toString()));
					cs.setInt(4, iModifyBy);

					cs.execute();

					System.out.println("T-5");
					// ************************* To Copy Files
					// *********************************

					// 1. Get Source Path.
					// 2. Get Destination Path.

					// 1.1 Get source BaseWorkSpaceFolder .....

					ResultSet rs = dataTable.getDataSet(con, "vBaseWorkFolder", "workspacemst",
							"vWorkspaceId='" + swsId + "'", "");

					rs.next();
					String sourceBaseWorkspaceFolder = rs.getString("vBaseWorkFolder");

					System.out.println("Source-" + sourceBaseWorkspaceFolder);
					rs.close();
					rs = null;

					// 1.2 Get source FolderName and destFileName.....

					String sourceFolderName = "";
					String sourceFileName = "";

					String WhereCond1 = " vWorkSpaceId = '" + swsId + "' and iNodeId = "
							+ Integer.parseInt(getLeafNodes.get(i).toString())
							+ " and iTranNo = (Select max(iTranNo) from workspacenodehistory where "
							+ " vWorkSpaceId = '" + swsId + "' and iNodeId = "
							+ Integer.parseInt(getLeafNodes.get(i).toString()) + ") ";

					System.out.println("WhereCond1-" + WhereCond1);
					rs = dataTable.getDataSet(con, "vFolderName,vFileName", "workspacenodehistory", WhereCond1, "");

					if (rs.next()) {
						sourceFolderName = rs.getString("vFolderName");

						sourceFileName = rs.getString("vFileName");
					}
					rs.close();
					rs = null;

					// 2.1 Get destBaseWorkSpaceFolder .....
					rs = dataTable.getDataSet(con, "vBaseWorkFolder", "workspacemst", "vWorkspaceId='" + dwsId + "'",
							"");
					String destBaseWorkspaceFolder = "";
					if (rs.next()) {
						destBaseWorkspaceFolder = rs.getString("vBaseWorkFolder");
					}
					rs.close();
					rs = null;

					// 2.2 Get destFolderName and destFileName.....
					String destFolderName = "";
					// String destFileName="";

					String WhereCond = " vWorkSpaceId = '" + dwsId + "' and iNodeId = "
							+ Integer.parseInt(getLeafNodes.get(i).toString())
							+ " and iTranNo = (Select max(iTranNo) from workspacenodehistory where "
							+ " vWorkSpaceId = '" + dwsId + "' and iNodeId = "
							+ Integer.parseInt(getLeafNodes.get(i).toString()) + ") ";

					rs = dataTable.getDataSet(con, "vFolderName,vFileName", "workspacenodehistory", WhereCond, "");
					if (rs == null) {
						// Do nothing
					}
					if (rs.next()) {
						destFolderName = rs.getString("vFolderName");
						// destFileName = rs.getString("vFileName");
					}

					rs.close();
					rs = null;

					// sourceFolderName = sourceFolderName.replace('/','\\');
					// destFolderName = destFolderName.replace('/','\\');

					String basePath = sourceBaseWorkspaceFolder + sourceFolderName + "/" + sourceFileName;
					String destPath = destBaseWorkspaceFolder + destFolderName;

					File mkdestDir = new File(destPath);

					boolean destDirStatus = false;
					if (!mkdestDir.exists()) {
						destDirStatus = mkdestDir.mkdirs();
					}
					if (destDirStatus == true) {
						try {
							File srcFile = new File(basePath);
							File destFile = new File(destPath);
							destFile.mkdirs();
							File destFile1 = new File(destPath + "/" + sourceFileName);
							InputStream stream = new FileInputStream(srcFile);
							byte[] fileData = getBytesFromFile(srcFile);
							OutputStream bos = new FileOutputStream(destFile1);
							int len;
							while ((len = stream.read(fileData)) > 0) {
								bos.write(fileData, 0, len);
							}
							bos.close();
							stream.close();
							con.commit();
							noOfFiles++;
						} catch (Exception e) {
							con.rollback();
						}
					} else {
						con.rollback();
					}

				}
			}

			System.out.println("T-7");
			cs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

		return noOfFiles;
	}

	public static byte[] getBytesFromFile(File file) throws IOException {
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
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	public int getMaxTranNo(String wsId, int nodeId) {
		int tranNo = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			if (nodeId > 0)
				where += " and iNodeId =" + nodeId + " ";
			ResultSet rs = dataTable.getDataSet(con, "max(iTranNo) as iTranNo", "WorkspaceNodeHistory", where, "");
			if (rs.next()) {
				tranNo = rs.getInt("iTranNo");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tranNo;
	}

	public int getMaxTranNoForTemplate(String templateId, int nodeId) {
		int tranNo = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vTemplateId = '" + templateId + "' ";
			if (nodeId > 0)
				where += " and iNodeId =" + nodeId + " ";
			ResultSet rs = dataTable.getDataSet(con, "max(iTranNo) as iTranNo", "templatenodehistory", where, "");
			if (rs.next()) {
				tranNo = rs.getInt("iTranNo");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tranNo;
	}

	public DTOWorkSpaceNodeHistory getdetailstodisplaymergedata(String wsId, int TranNo) {
		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			where += " and iTranNo =" + TranNo + " ";
			ResultSet rs = dataTable.getDataSet(con, "iNodeId,vFileName,iTranNo,vRemark", "View_NodeSubmissionHistory",
					where, "");
			if (rs.next()) {
				dto.setNodeID(rs.getInt("iNodeId"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setRemark(rs.getString("vRemark"));
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public ArrayList<Integer> getNodeIdFromNodeHistory(String wsId) {
		// ArrayList<DTOWorkSpaceNodeHistory> nodeidlist = new
		// ArrayList<DTOWorkSpaceNodeHistory>();
		ArrayList<Integer> nodeidlist = new ArrayList<Integer>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			ResultSet rs = dataTable.getDataSet(con, "distinct iNodeId", "WorkspaceNodeHistory", where, "");
			while (rs.next()) {
				// DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				// dto.setNodeID(rs.getInt("iNodeId"));
				nodeidlist.add(rs.getInt("iNodeId"));
				// dto=null;
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nodeidlist;
	}

	public void updateStageStatus(DTOWorkSpaceNodeHistory dto) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call proc_updateWorkspaceNodeHistoryStageNo(?,?,?)}");
			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			proc.setInt(3, dto.getStageId());
			proc.execute();
			proc.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Vector<DTOWorkSpaceNodeHistory> getMyPendingWorksReport(DTOWorkSpaceNodeHistory dto) {

		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			StringBuffer query = new StringBuffer();
			query.append(" vWorkspaceId = '" + dto.getWorkSpaceId() + "'");
			query.append(" and imodifyby = " + dto.getModifyBy() + "");
			if (dto.getNodeId() != 0) {
				query.append(" and iNodeId = " + dto.getNodeId() + "");
			}
			query.append(" and currentStageId <> 100");
			query.append(" and vFileName <>'No File'"); // For Not show deleted
														// file in Mypendingwork
			ResultSet rs = dataTable.getDataSet(con,
					"distinct iTranNo,vBaseWorkFolder,vworkspaceid,inodeId,vFileName,vNodeName,vNodeDisplayName,currentStageId,currentStageDesc",
					"view_myPendingWork", query.toString(), "vNodeDisplayName");
			while (rs.next()) {
				dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setStageId(rs.getInt("currentStageId"));
				dto.setStageDesc(rs.getString("currentStageDesc"));
				dto.setRemark(rs.getString("vBaseWorkFolder")); // set
				// baseWorkFolder
				// as Remark
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
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

	public Vector<DTOWorkSpaceNodeHistory> getMyPendingWorksReportForNextStage(DTOWorkSpaceNodeHistory dto) {

		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			StringBuffer query = new StringBuffer();
			query.append(" vWorkspaceId = '" + dto.getWorkSpaceId() + "'");
			query.append(" and imodifyby = " + dto.getModifyBy() + "");
			if (dto.getNodeId() != 0) {
				query.append(" and iNodeId = " + dto.getNodeId() + "");
			}
			rs = dataTable.getDataSet(con, "distinct inodeid,nextStageDesc,nextStageId", "view_myPendingWork",
					query.toString(), "inodeid,nextStageId");
			while (rs.next()) {
				dto = new DTOWorkSpaceNodeHistory();
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setNextStageDesc(rs.getString("nextStageDesc"));
				dto.setNextStageId(rs.getInt("nextStageId"));
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
	/*
	 * public Vector<DTOWorkSpaceNodeHistory>
	 * getMyPendingWorksReportForNextStage( DTOWorkSpaceNodeHistory dto) {
	 * 
	 * Vector<DTOWorkSpaceNodeHistory> data = new
	 * Vector<DTOWorkSpaceNodeHistory>(); try { Connection con =
	 * ConnectionManager.ds.getConnection(); ResultSet rs = null; StringBuffer
	 * query = new StringBuffer(); query.append(" vWorkspaceId = '" +
	 * dto.getWorkSpaceId() + "'"); query.append(" and iUserCode = " +
	 * dto.getModifyBy() + ""); if (dto.getNodeId() != 0) {
	 * query.append(" and iNodeId = " + dto.getNodeId() + ""); } rs =
	 * dataTable.getDataSet(con, "iNodeId,vStageDesc,iStageId",
	 * "view_MyPendingWorkActivity", query.toString(), "iNodeId,iStageId");
	 * while (rs.next()) { dto = new DTOWorkSpaceNodeHistory();
	 * dto.setNodeId(rs.getInt("iNodeId"));
	 * dto.setNextStageDesc(rs.getString("vStageDesc"));
	 * dto.setNextStageId(rs.getInt("iStageId")); data.addElement(dto); dto =
	 * null; }
	 * 
	 * rs.close(); con.close();
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } return data; }
	 */

	public boolean copyfilefromsrctodest(String srcWsId, int sourceNodeId) {
		boolean copyFile = false;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ call proc_copyfile(?,?)}");
			proc.setString(1, srcWsId);
			proc.setInt(2, sourceNodeId);
			ResultSet rs = proc.executeQuery();
			if (rs.next()) {
				copyFile = true;
			}
			rs.close();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return copyFile;
	}

	public void CopyFileForRepository(String srcWsId, String destWsId, int srcNodeId, int destNodeId, int userCode,
			int tranNo) {
		try {

			// ************************* To Copy Files
			// *********************************

			// 1. Get Source Path.
			// 2. Get Destination Path.

			// 1.1 Get source BaseWorkSpaceFolder .....

			String destFolderName = "";
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "vBaseWorkFolder", "workspacemst",
					"vWorkspaceId='" + srcWsId + "'", "");
			rs.next();
			String sourceBaseWorkspaceFolder = rs.getString("vBaseWorkFolder");

			rs.close();
			rs = null;

			// 1.2 Get source FolderName and destFileName.....

			String WhereCond1 = " vWorkSpaceId = '" + srcWsId + "' and iNodeId = " + srcNodeId
					+ " and iTranNo = (Select max(iTranNo) from workspacenodehistory where " + " vWorkSpaceId = '"
					+ srcWsId + "' and iNodeId = " + srcNodeId + ") ";

			rs = dataTable.getDataSet(con, "vFolderName,vFileName", "workspacenodehistory", WhereCond1, "");

			rs.next();

			String sourceFolderName = rs.getString("vFolderName");
			String sourceFileName = rs.getString("vFileName");

			rs.close();
			rs = null;

			// 2.1 Get destBaseWorkSpaceFolder .....
			rs = dataTable.getDataSet(con, "vBaseWorkFolder", "workspacemst", "vWorkspaceId='" + destWsId + "'", "");
			rs.next();
			String destBaseWorkspaceFolder = rs.getString("vBaseWorkFolder");
			rs.close();
			rs = null;

			destFolderName = "/" + destWsId + "/" + destNodeId;
			// sourceFolderName = sourceFolderName.replace('/','\\');
			// destFolderName = destFolderName.replace('/','\\');

			// WorkspaceMst workspaceMst = new WorkspaceMst();
			// int newTranNo = workspaceMst.getNewTranNo(destWsId);
			String basePath = sourceBaseWorkspaceFolder + sourceFolderName + "/" + sourceFileName;
			String destPath = destBaseWorkspaceFolder + destFolderName + "/" + tranNo;

			// -------------------- insert into workspaceNodeHistory
			// --------------------------------------

			DTOWorkSpaceNodeHistory workSpaceNodeHistoryDTO = new DTOWorkSpaceNodeHistory();
			workSpaceNodeHistoryDTO.setWorkSpaceId(destWsId);
			workSpaceNodeHistoryDTO.setNodeId(destNodeId);
			workSpaceNodeHistoryDTO.setTranNo(tranNo);
			workSpaceNodeHistoryDTO.setFileName(sourceFileName);
			workSpaceNodeHistoryDTO.setFileType("");
			workSpaceNodeHistoryDTO.setFolderName("/" + destWsId + "/" + destNodeId + "/" + tranNo);
			workSpaceNodeHistoryDTO.setRequiredFlag('Y');
			// stageId
			workSpaceNodeHistoryDTO.setStageId(10);
			workSpaceNodeHistoryDTO.setRemark("");
			workSpaceNodeHistoryDTO.setModifyBy(userCode);
			workSpaceNodeHistoryDTO.setStatusIndi('N');
			workSpaceNodeHistoryDTO.setDefaultFileFormat("");
			insertNodeHistory(workSpaceNodeHistoryDTO);

			File mkdestDir = new File(destPath);
			boolean destDirStatus = false;
			if (!mkdestDir.exists()) {
				destDirStatus = mkdestDir.mkdirs();
			}
			if (destDirStatus == true) {
				try {
					File srcFile = new File(basePath);
					File destFile = new File(destPath);
					destFile.mkdirs();
					File destFile1 = new File(destPath + "/" + sourceFileName);
					InputStream stream = new FileInputStream(srcFile);
					byte[] fileData = getBytesFromFile(srcFile);
					OutputStream bos = new FileOutputStream(destFile1);
					int len;
					while ((len = stream.read(fileData)) > 0) {
						bos.write(fileData, 0, len);
					}
					bos.close();
					stream.close();
					con.commit();
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			// -------------------- insert into workspacenodeversionhistory
			// -Added By Nirav Parmar-------------------------------------
			WorkSpaceNodeVersionHistory wsnwh = new WorkSpaceNodeVersionHistory();
			DTOWorkSpaceNodeVersionHistory workSpaceNodeversionHistoryDTO = new DTOWorkSpaceNodeVersionHistory();
			workSpaceNodeversionHistoryDTO.setWorkspaceId(destWsId);
			workSpaceNodeversionHistoryDTO.setNodeId(destNodeId);
			workSpaceNodeversionHistoryDTO.setTranNo(tranNo);
			workSpaceNodeversionHistoryDTO.setPublished('N');
			workSpaceNodeversionHistoryDTO.setDownloaded('N');
			workSpaceNodeversionHistoryDTO.setActivityId("");
			workSpaceNodeversionHistoryDTO.setModifyBy(userCode);
			workSpaceNodeversionHistoryDTO.setExecutedBy(userCode);
			workSpaceNodeversionHistoryDTO.setUserDefineVersionId("A-1");
			wsnwh.insertWorkspaceNodeVersionHistory(workSpaceNodeversionHistoryDTO);

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	/*
	 * Method Added By : Ashmak Shah Added On : 12th may 2009 Usage : Get all
	 * workspace nodes having files, to find files containing hyper links. Call
	 * : com.docmgmt.struts.actions.hyperlinks.ListFilesAction.execute()
	 */
	public Vector<DTOWorkSpaceNodeHistory> getUploadedFileNodes(String workspaceid) {

		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();
		DTOWorkSpaceNodeHistory dto;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			// StringBuffer query = new StringBuffer();
			// query.append("select iNodeId,itranno,vfoldername,vfilename from
			// View_WorkSpaceNodeHistoryForAllStages");
			// query.append(" where vworkspaceid = '"+workspaceid+"' order by
			// inodeid");
			rs = dataTable.getDataSet(con, "iNodeId,itranno,vfoldername,vfilename",
					"View_WorkSpaceNodeHistoryForAllStages", "vworkspaceid = '" + workspaceid + "'", "inodeid");
			// Statement stmt = con.createStatement();

			// rs = stmt.executeQuery(query.toString());
			while (rs.next()) {
				dto = new DTOWorkSpaceNodeHistory();
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("itranno"));
				dto.setFolderName(rs.getString("vfoldername"));
				dto.setFileName(rs.getString("vfilename"));
				data.addElement(dto);
				// System.out.println("NodeId->"+dto.getNodeId()+":::TranNo->"+dto.getTranNo()+":::FileName->"+dto.getFileName());
				dto = null;
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return data;

	}

	public ArrayList<DTOWorkSpaceNodeHistory> showFullNodeHistory(String workspaceid, int nodeId) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " ";

			rs = dataTable.getDataSet(con, "*", "View_NodeSubmissionHistoryBySequecenumber", whr, "iTranNo");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFileType(rs.getString("vFileType"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}

				dto.setRemark(rs.getString("vRemark"));

				if (rs.getString("vFileVersionId").equals("")) {
					dto.setFileVersionId("");
					dto.setLastClosed('N');
					dto.setUserDefineVersionId("");
					dto.setCurrentSeqNumber("");
					dto.setSubmissionId("");
				} else {
					dto.setFileVersionId(rs.getString("vFileVersionId"));
					dto.setLastClosed(rs.getString("isLastClosed").charAt(0));
					dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
					dto.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
					dto.setSubmissionId(rs.getString("submissionId"));
				}
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat"));

				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;

	}

	public Vector<DTOWorkSpaceNodeHistory> showFullNodeHistoryForLambda(String workspaceid, int nodeId) {

		Vector<DTOWorkSpaceNodeHistory> nodeHistory = new Vector<DTOWorkSpaceNodeHistory>();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " ";

			rs = dataTable.getDataSet(con, "*", "View_NodeSubmissionHistoryBySequecenumber", whr, "iTranNo");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setFileType(rs.getString("vFileType"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setUserTypeName(rs.getString("vUserGroupName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));

				dto.setRemark(rs.getString("vRemark"));

				if (rs.getString("vFileVersionId").equals("")) {
					dto.setFileVersionId("");
					dto.setLastClosed('N');
					dto.setUserDefineVersionId("");
					dto.setCurrentSeqNumber("");
					dto.setSubmissionId("");
				} else {
					dto.setFileVersionId(rs.getString("vFileVersionId"));
					dto.setLastClosed(rs.getString("isLastClosed").charAt(0));
					dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
					dto.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
					dto.setSubmissionId(rs.getString("submissionId"));
				}

				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;

	}

	public DTOWorkSpaceNodeHistory getLatestNodeSubHistory(String workspaceid, int nodeId) {

		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		try {
			Connection con = ConnectionManager.ds.getConnection();

			String sql = " SELECT * FROM View_NodeSubmissionHistory " + " WHERE vWorkspaceId = '" + workspaceid + "' "
					+ " AND iNodeId = " + nodeId + " " + " AND vCurrentSeqNumber = (SELECT MAX(vCurrentSeqNumber) "
					+ "  FROM View_NodeSubmissionHistory " + "  WHERE vWorkspaceId = '" + workspaceid + "' "
					+ " AND iNodeId = " + nodeId + " " + " AND vCurrentSeqNumber <> '') ";

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));

				dto.setFileVersionId(rs.getString("vFileVersionId"));
				dto.setLastClosed(rs.getString("isLastClosed").charAt(0));
				dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
				dto.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
				dto.setSubmissionId(rs.getString("submissionId"));

			}
			stmt.close();
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dto;

	}

	public ArrayList<DTOWorkSpaceNodeHistory> getAllNodesLastHistory(String workspaceid, int[] nodeIds) {

		ArrayList<DTOWorkSpaceNodeHistory> latestNodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String fields = "vWorkspaceId,vWorkSpaceDesc,vBaseWorkFolder,iNodeId,iTranNo,vFileName,vFolderName,"
					+ "iStageId,vStageDesc,iModifyBy,vUserName,dModifyOn,vNodeName,vNodeFolderName,vUserDefineVersionId";

			String whr = " vWorkspaceId = '" + workspaceid + "' ";
			if (nodeIds != null) {
				StringBuffer whrBuffer = new StringBuffer();
				whrBuffer.append(" AND  iNodeId IN(");
				for (int i = 0; i < nodeIds.length; i++) {
					if (i != 0) {
						whrBuffer.append(",");
					}
					whrBuffer.append(nodeIds[i]);

				}
				whrBuffer.append(") ");

				whr += whrBuffer;
			}
			rs = dataTable.getDataSet(con, fields, "View_NodesLatestHistory", whr, "vWorkspaceId,iNodeId");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setNodeFolderName(rs.getString("vNodeFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
				latestNodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return latestNodeHistory;

	}

	public void updateNodeDetailsSerialNo(String wID, int nodeid, int count) {

		System.out.println("NodeId=" + nodeid + ": count=" + count);
		Connection con = null;
		CallableStatement cs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			cs = con.prepareCall("{call Update_NodeSerialNo(?,?,?,?)}");
			cs.setString(1, wID);
			cs.setInt(2, nodeid);
			cs.setInt(3, count);
			cs.setInt(4, 1);

			cs.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cs != null) {
					cs.close();
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

	public ArrayList<DTOWorkSpaceNodeHistory> getAllNodesLastHistory(ArrayList<String> workspaceids, int[] nodeIds) {

		ArrayList<DTOWorkSpaceNodeHistory> latestNodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();

		if (workspaceids != null && workspaceids.size() > 0) {
			try {
				Connection con = ConnectionManager.ds.getConnection();
				ResultSet rs = null;

				String fields = "vWorkspaceId,vWorkSpaceDesc,vBaseWorkFolder,iNodeId,iTranNo,vFileName,vFolderName,"
						+ "iStageId,vStageDesc,iModifyBy,vUserName,dModifyOn,vNodeName,vNodeFolderName,vUserDefineVersionId";

				StringBuffer whrBuffer = new StringBuffer();

				for (int i = 0; i < workspaceids.size(); i++) {
					if (i != 0) {
						whrBuffer.append(",");
					}
					whrBuffer.append("'" + workspaceids.get(i) + "'");

				}
				String whr = " vWorkspaceId in (" + whrBuffer + ") " + "and cStatusIndi<>'D'";
				if (nodeIds != null) {
					whrBuffer = new StringBuffer();
					whrBuffer.append(" AND  iNodeId IN(");
					for (int i = 0; i < nodeIds.length; i++) {
						if (i != 0) {
							whrBuffer.append(",");
						}
						whrBuffer.append(nodeIds[i]);
					}
					whrBuffer.append(") ");

					whr += whrBuffer;
				}
				rs = dataTable.getDataSet(con, fields, "View_NodesLatestHistory", whr, "vWorkspaceId,iNodeId");

				while (rs.next()) {
					DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
					dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
					dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
					dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
					dto.setNodeId(rs.getInt("iNodeId"));
					dto.setTranNo(rs.getInt("iTranNo"));
					dto.setFolderName(rs.getString("vFolderName"));
					dto.setNodeFolderName(rs.getString("vNodeFolderName"));
					dto.setFileName(rs.getString("vFileName"));
					dto.setStageId(rs.getInt("iStageId"));
					dto.setStageDesc(rs.getString("vStageDesc"));
					dto.setModifyBy(rs.getInt("iModifyBy"));
					dto.setUserName(rs.getString("vUserName"));
					dto.setModifyOn(rs.getTimestamp("dModifyOn"));
					dto.setNodeName(rs.getString("vNodeName"));
					dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
					latestNodeHistory.add(dto);
				}

				rs.close();
				con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return latestNodeHistory;

	}

	public Vector<DTOWorkSpaceNodeHistory> getPDFDetails(String wsId, String[] extensions) {
		Vector<DTOWorkSpaceNodeHistory> nodeHistoryData = new Vector<DTOWorkSpaceNodeHistory>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String fields = "vWorkspaceId,iNodeId,vWorkSpaceDesc,vNodeDisplayName,vBaseWorkFolder,vFolderName,vFileName,vStageDesc,vUserDefineVersionId,vUserName,dModifyOn";
			// ResultSet rs=dataTable.getDataSet(con, fields,
			// "View_NodesLatestHistory",
			// "vWorkspaceId='"+wsId+"' and vfilename like '%.pdf'","");
			String ext = "(";
			for (int iExtension = 0; iExtension < extensions.length; iExtension++) {
				ext += " vFilename LIKE '%." + extensions[iExtension] + "' OR ";
			}
			ext = ext.substring(0, ext.length() - 3);// To remove Last 'OR '
			ext += ") ";
			ResultSet rs = dataTable.getDataSet(con, fields, "View_NodesLatestHistoryForPdfPublish",
					"vWorkspaceId='" + wsId + "' AND " + ext, "iNodeNo");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeName(rs.getString("vNodeDisplayName"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				nodeHistoryData.addElement(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistoryData;
	}

	public DTOWorkSpaceNodeHistory getNodeDetailsForPdfPublish(String wsId, Integer nodeid) {
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			String fields = "vWorkspaceId,iNodeId,vWorkSpaceDesc,vNodeDisplayName,vBaseWorkFolder,vFolderName,vFileName,iStageId,vStageDesc,vUserDefineVersionId,vUserName,dModifyOn";
			// ResultSet rs=dataTable.getDataSet(con, fields,
			// "View_NodesLatestHistory",
			// "vWorkspaceId='"+wsId+"' and vfilename like '%.pdf'","");

			ResultSet rs = dataTable.getDataSet(con, fields, "View_NodesLatestHistoryForPdfPublishwithAttr",
					"vWorkspaceId='" + wsId + "'AND iNodeId=" + nodeid, "iNodeNo");
			if (rs.next()) {

				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeName(rs.getString("vNodeDisplayName"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
			} else {
				rs.close();
				con.close();
				return null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dto;
	}

	public Vector<Integer> getNodeDetailsForPdfPublish(String wsId) {

		Vector<Integer> nodeids = new Vector<Integer>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String fields = "iNodeId";
			// ResultSet rs=dataTable.getDataSet(con, fields,
			// "View_NodesLatestHistory",
			// "vWorkspaceId='"+wsId+"' and vfilename like '%.pdf'","");
			String whr = " vWorkspaceId = '" + wsId + "' ";
			ResultSet rs = dataTable.getDataSet(con, fields, "View_NodesLatestHistoryForPdfPublishwithAttr", whr,
					"iNodeNo");

			while (rs.next()) {
				nodeids.add(rs.getInt("iNodeId"));
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeids;
	}

	// This function is get the files those will be publishable in next
	// sequence.
	public ArrayList<DTOWorkSpaceNodeHistory> getPublishableFilesAfterLastConfirmSeq(String wsId) {
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistoryData = new ArrayList<DTOWorkSpaceNodeHistory>();
		try {
			String whrCond = "vWorkSpaceId = '" + wsId + "' AND iStageId = 100 AND iTranNo >" + "("
					+ "SELECT ISNULL(MAX(iTranNo),0) FROM InternalLabelHistory WHERE vWorkspaceId = '" + wsId
					+ "' AND iLabelNo =" + "("
					+ "SELECT ISNULL(MAX(iLabelNo),0) FROM InternalLabelMst WHERE vWorkspaceId = '" + wsId
					+ "'  AND vLabelId IN" + "("
					+ "SELECT vLabelId FROM View_SubmissionInfoDtlForAllRegions WHERE vWorkspaceId = '" + wsId
					+ "' AND cConfirm = 'Y' AND vCurrentSeqNumber =" + "("
					+ "SELECT MAX(vCurrentSeqNumber) FROM View_SubmissionInfoDtlForAllRegions where vWorkspaceId = '"
					+ wsId + "' AND cConfirm = 'Y'" + ")" + ")" + ")" + ")";

			Connection con = ConnectionManager.ds.getConnection();
			String fields = "vWorkspaceId,iNodeId,iTranNo,vFileName,iStageId,vFolderName";
			ResultSet rs = dataTable.getDataSet(con, fields, "WorkspaceNodeHistory", whrCond, "");
			System.out.println("Query=" + whrCond);

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setFolderName(rs.getString("vFolderName"));

				nodeHistoryData.add(dto);
			}

			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nodeHistoryData;
	}

	// added by butani vijay for pdf publish
	// This function is get the files those will be publishable.
	public Vector<DTOWorkSpaceNodeHistory> getPDFPublishableFilesAfterLastConfirmSeq(String wsId) {
		Vector<DTOWorkSpaceNodeHistory> nodeHistoryData = new Vector<DTOWorkSpaceNodeHistory>();
		try {
			// System.out.println("Getting Files...");

			String whr = "vStageDesc='Approved' and vWorkspaceId='" + wsId
					+ "' AND ( vFilename LIKE '%.doc' OR  vFilename LIKE '%.pdf' OR  vFilename LIKE '%.odt' ) and "
					+ "iTranNo >(" + "SELECT ISNULL(MAX(iTranNo),0) FROM InternalLabelHistory WHERE vWorkspaceId = '"
					+ wsId + "' AND iLabelNo =" + "("
					+ "SELECT ISNULL(MAX(iLabelNo),0) FROM InternalLabelMst WHERE vWorkspaceId = '" + wsId
					+ "'  AND vLabelId IN" + "("
					+ "SELECT vLabelId FROM View_SubmissionInfoDMSDtl WHERE vWorkspaceId = '" + wsId
					+ "'  AND vCurrentSeqNumber =" + "("
					+ "SELECT MAX(vCurrentSeqNumber) FROM View_SubmissionInfoDMSDtl where vWorkspaceId = '" + wsId
					+ "' AND cConfirm = 'Y'" + ")" + ")" + ")" + ")";
			Connection con = ConnectionManager.ds.getConnection();
			String fields = "vWorkspaceId,iNodeId,vWorkSpaceDesc,vNodeDisplayName,vBaseWorkFolder,vFolderName,vFileName,vStageDesc,vUserDefineVersionId,vUserName,dModifyOn";

			// String fields = "vWorkspaceId,iNodeId,iTranNo,vFileName";
			ResultSet rs = dataTable.getDataSet(con, fields, "View_NodesLatestHistory", whr, "");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeName(rs.getString("vNodeDisplayName"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				nodeHistoryData.add(dto);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nodeHistoryData;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getNodeHistoryStageWiseUserWise(String wsId, int nodeId, int stageId,
			int userCode, boolean maxTranData) {
		Connection con = null;
		ResultSet rs = null;
		String selected = "*";
		String whrCond = "vUserDefineVersionId <> '' AND vWorkspaceId = '" + wsId + "' ";
		String order = " "; // Ascending order
		if (nodeId != 0) {
			whrCond += " AND iNodeId = " + nodeId;
		}
		if (stageId != 0) {
			whrCond += " AND iStageId = " + stageId;
		}
		if (userCode != 0) {
			whrCond += " AND iModifyBy = " + userCode;

		}
		if (maxTranData) {
			selected = " TOP 1 " + selected;
			order = " DESC ";
		}
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		try {
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, selected, "View_NodeSubmissionHistory", whrCond, "itranno" + order);
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dtoWsNodeHistory = new DTOWorkSpaceNodeHistory();
				dtoWsNodeHistory.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dtoWsNodeHistory.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dtoWsNodeHistory.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dtoWsNodeHistory.setNodeId(rs.getInt("iNodeId"));
				dtoWsNodeHistory.setTranNo(rs.getInt("iTranNo"));
				dtoWsNodeHistory.setFolderName(rs.getString("vFolderName"));
				dtoWsNodeHistory.setFileName(rs.getString("vFileName"));
				dtoWsNodeHistory.setStageId(rs.getInt("iStageId"));
				dtoWsNodeHistory.setStageDesc(rs.getString("vStageDesc"));
				dtoWsNodeHistory.setModifyBy(rs.getInt("iModifyBy"));
				dtoWsNodeHistory.setUserName(rs.getString("vUserName"));
				dtoWsNodeHistory.setModifyOn(rs.getTimestamp("dModifyOn"));
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dtoWsNodeHistory.getModifyOn(), locationName, countryCode);
					dtoWsNodeHistory.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dtoWsNodeHistory.getModifyOn(), locationName, countryCode);
					dtoWsNodeHistory.setISTDateTime(time.get(0));
					dtoWsNodeHistory.setESTDateTime(time.get(1));
				}

				if (rs.getString("vFileVersionId").equals("")) {
					dtoWsNodeHistory.setFileVersionId("");
					dtoWsNodeHistory.setLastClosed('N');
					dtoWsNodeHistory.setUserDefineVersionId("");
					dtoWsNodeHistory.setCurrentSeqNumber("");
					dtoWsNodeHistory.setSubmissionId("");
				} else {
					dtoWsNodeHistory.setFileVersionId(rs.getString("vFileVersionId"));
					dtoWsNodeHistory.setLastClosed(rs.getString("isLastClosed").charAt(0));
					dtoWsNodeHistory.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
					dtoWsNodeHistory.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
					dtoWsNodeHistory.setSubmissionId(rs.getString("submissionId"));
				}
				dtoWsNodeHistory.setRoleName(rs.getString("vRoleName"));

				wsNodeHistory.add(dtoWsNodeHistory);
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
		return wsNodeHistory;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getRefWorkspaceNodes(String workspaceId, int nodeId) {
		Connection con = null;
		ResultSet rs = null;
		String whrCond = " vWorkspaceid = '" + workspaceId + "' AND iNodeId =" + nodeId + " AND cStatusIndi = 'N'";
		ArrayList<DTOWorkspaceNodeReferenceDetail> wsNodeRefList = new ArrayList<DTOWorkspaceNodeReferenceDetail>();
		ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistoryList = new ArrayList<DTOWorkSpaceNodeHistory>();
		try {
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, " * ", "workspacenodereferencedetail", whrCond, " nRefNo");
			while (rs.next()) {
				DTOWorkspaceNodeReferenceDetail dtoWsNdRefDtl = new DTOWorkspaceNodeReferenceDetail();
				dtoWsNdRefDtl.setWorkspaceId(rs.getString("vWorkspaceId"));
				dtoWsNdRefDtl.setNodeId(rs.getInt("iNodeId"));
				dtoWsNdRefDtl.setRefWorkspaceId(rs.getString("vRefWorkspaceId"));
				dtoWsNdRefDtl.setRefNodeId(rs.getInt("iRefNodeId"));
				dtoWsNdRefDtl.setModifyOn(rs.getTimestamp("dModifyOn"));
				dtoWsNdRefDtl.setModifyBy(rs.getInt("iModifyBy"));
				dtoWsNdRefDtl.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				wsNodeRefList.add(dtoWsNdRefDtl);
			}

			int[] nId = new int[1];
			if (wsNodeRefList.size() > 0) {
				nId[0] = nodeId;
				// set workspaceid and nodeid from Max RefNo's workspaceid and
				// nodeid
				wsNodeHistoryList = getAllNodesLastHistory(workspaceId, nId);
				workspaceId = wsNodeRefList.get(wsNodeRefList.size() - 1).getRefWorkspaceId();
				nodeId = wsNodeRefList.get(wsNodeRefList.size() - 1).getRefNodeId();
				// it gets only approved files.
				ArrayList<DTOWorkSpaceNodeHistory> wsNdHisList = getFilePathDtl(workspaceId, nodeId, 100, con, true);
				if (wsNdHisList.size() > 0) {
					DTOWorkSpaceNodeHistory dtoWsNodeHistory = wsNdHisList.get(0);
					if (wsNodeHistoryList.size() > 0) {
						DTOWorkSpaceNodeHistory dtoWsNdHis = wsNodeHistoryList.get(0);
						if (dtoWsNodeHistory != null) {
							dtoWsNdHis.setBaseWorkFolder(dtoWsNodeHistory.getBaseWorkFolder());
							dtoWsNdHis.setFolderName(dtoWsNodeHistory.getFolderName());
							dtoWsNdHis.setFileName(dtoWsNodeHistory.getFileName());
						}
					} else {
						wsNodeHistoryList.add(dtoWsNodeHistory);
					}
				}
			} else {
				nId[0] = nodeId;
				wsNodeHistoryList = getAllNodesLastHistory(workspaceId, nId);
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
		return wsNodeHistoryList;
	}

	private ArrayList<DTOWorkSpaceNodeHistory> getFilePathDtl(String workspaceId, int nodeId, int stageId,
			Connection con, boolean maxTranData) {
		ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
		ResultSet rs = null;
		String selected = "*";
		String whrCond = "vUserDefineVersionId <> '' AND vWorkspaceId = '" + workspaceId + "' ";
		String order = " "; // Ascending order
		if (nodeId != 0) {
			whrCond += " AND iNodeId = " + nodeId;
		}
		if (stageId != 0) {
			whrCond += " AND iStageId = " + stageId;
		}

		if (maxTranData) {
			selected = " TOP 1 " + selected;
			order = " DESC ";
		}

		try {
			rs = dataTable.getDataSet(con, selected, "View_NodeSubmissionHistory", whrCond, "itranno" + order);
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dtoWsNodeHistory = new DTOWorkSpaceNodeHistory();
				dtoWsNodeHistory.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dtoWsNodeHistory.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dtoWsNodeHistory.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dtoWsNodeHistory.setNodeId(rs.getInt("iNodeId"));
				dtoWsNodeHistory.setTranNo(rs.getInt("iTranNo"));
				dtoWsNodeHistory.setFolderName(rs.getString("vFolderName"));
				dtoWsNodeHistory.setFileName(rs.getString("vFileName"));
				dtoWsNodeHistory.setStageId(rs.getInt("iStageId"));
				dtoWsNodeHistory.setStageDesc(rs.getString("vStageDesc"));
				dtoWsNodeHistory.setModifyBy(rs.getInt("iModifyBy"));
				dtoWsNodeHistory.setUserName(rs.getString("vUserName"));
				dtoWsNodeHistory.setModifyOn(rs.getTimestamp("dModifyOn"));

				if (rs.getString("vFileVersionId").equals("")) {
					dtoWsNodeHistory.setFileVersionId("");
					dtoWsNodeHistory.setLastClosed('N');
					dtoWsNodeHistory.setUserDefineVersionId("");
					dtoWsNodeHistory.setCurrentSeqNumber("");
					dtoWsNodeHistory.setSubmissionId("");
				} else {
					dtoWsNodeHistory.setFileVersionId(rs.getString("vFileVersionId"));
					dtoWsNodeHistory.setLastClosed(rs.getString("isLastClosed").charAt(0));
					dtoWsNodeHistory.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
					dtoWsNodeHistory.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
					dtoWsNodeHistory.setSubmissionId(rs.getString("submissionId"));
				}

				wsNodeHistory.add(dtoWsNodeHistory);
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
		}
		return wsNodeHistory;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getNodeDetailedHistory(String wsId, int nodeId) {
		Connection con = null;
		ResultSet rs = null;
		String selected = " * ";
		String whrCond = " vWorkspaceId = '" + wsId + "' ";
		if (nodeId != 0) {
			whrCond += " AND iNodeId = " + nodeId;
		}

		ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
		try {
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, selected, " View_WorkspaceNodeAllHistory ", whrCond, " itranno ");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dtoWsNodeHistory = new DTOWorkSpaceNodeHistory();
				dtoWsNodeHistory.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dtoWsNodeHistory.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dtoWsNodeHistory.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dtoWsNodeHistory.setNodeId(rs.getInt("iNodeId"));
				dtoWsNodeHistory.setTranNo(rs.getInt("iTranNo"));
				dtoWsNodeHistory.setFolderName(rs.getString("vFolderName"));
				dtoWsNodeHistory.setFileName(rs.getString("vFileName"));
				dtoWsNodeHistory.setStageId(rs.getInt("iStageId"));
				dtoWsNodeHistory.setModifyBy(rs.getInt("iModifyBy"));
				dtoWsNodeHistory.setUserName(rs.getString("ModifyByUser"));
				dtoWsNodeHistory.setModifyOn(rs.getTimestamp("dModifyOn"));
				dtoWsNodeHistory.setFileType(rs.getString("vFileType"));
				dtoWsNodeHistory.setRemark(rs.getString("vRemark"));
				dtoWsNodeHistory.setNodeName(rs.getString("vNodeName"));
				dtoWsNodeHistory.setNodeDisplayName(rs.getString("vNodeDisplayName"));

				wsNodeHistory.add(dtoWsNodeHistory);
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
		return wsNodeHistory;
	}

	// Added By Harsh Shah
	public String getBaseWorkFolder(String wsId, int nodeId, int transNo) {
		String baseWorkFolder = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			if (nodeId > 0)
				where += " and iNodeId =" + nodeId + " ";
			if (transNo > 0)
				where += " and iTranNo =" + transNo + " ";
			ResultSet rs = dataTable.getDataSet(con, "vFolderName,vFileName", "WorkspaceNodeHistory", where, "");
			if (rs.next()) {
				baseWorkFolder = rs.getString("vFolderName") + "&" + rs.getString("vFileName");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return baseWorkFolder;
	}

	public int getStageDescription(String wsId, int nodeId, int transNo) {
		int stageId = 0;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			if (nodeId > 0)
				where += " and iNodeId =" + nodeId + " ";
			if (transNo > 0)
				where += " and iTranNo =" + transNo + " ";
			ResultSet rs = dataTable.getDataSet(con, "iStageId", "WorkspaceNodeHistory", where, "");
			if (rs.next()) {
				stageId = rs.getInt("iStageId");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stageId;
	}

	public String getStageDescNCurrentSeq(String workspaceid, int nodeId, int maxTransNo) {

		String stageDescNCurrentSeq = "";

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " " + "AND iTranNo = "
					+ maxTransNo;

			rs = dataTable.getDataSet(con, "vStageDesc,vCurrentSeqNumber", "View_NodeSubmissionHistoryBySequecenumber",
					whr, "iTranNo");

			while (rs.next()) {
				stageDescNCurrentSeq = rs.getString("vStageDesc") + "&" + rs.getString("vCurrentSeqNumber");
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return stageDescNCurrentSeq;

	}

	public DTOWorkSpaceNodeHistory getMyPendingWorksReportForSingleNextStage(String wsId, int userId, int nodeId) {

		DTOWorkSpaceNodeHistory data = new DTOWorkSpaceNodeHistory();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			StringBuffer query = new StringBuffer();
			query.append(" vWorkspaceId = '" + wsId + "'");
			// query.append(" and imodifyby = " + userId + "");
			if (nodeId != 0) {
				query.append(" and iNodeId = " + nodeId + "");
			}
			rs = dataTable.getDataSet(con, "distinct top 1 inodeid,nextStageDesc,nextStageId", "view_myPendingWork",
					query.toString(), "nextStageId");
			while (rs.next()) {

				data.setNodeId(rs.getInt("iNodeId"));
				data.setNextStageDesc(rs.getString("nextStageDesc"));
				data.setNextStageId(rs.getInt("nextStageId"));

			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	// Added By Virendra Barad for find Last Uploaded File
	public int getMaxTranNoByStageId(String wsId, int nodeId) {
		int tranNo = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			if (nodeId > 0)
				where += " and iNodeId =" + nodeId + " AND iStageId=10";
			ResultSet rs = dataTable.getDataSet(con, "max(iTranNo) as iTranNo", "WorkspaceNodeHistory", where, "");
			if (rs.next()) {
				tranNo = rs.getInt("iTranNo");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tranNo;
	}

	// Added By Virendra Barad for find Last Uploaded File

	public ArrayList<DTOWorkSpaceNodeHistory> showFullNodeHistoryByStageId(String workspaceid, int nodeId, int tranNo) {
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " AND iTranNo = " + tranNo
					+ " AND iStageId=10";

			rs = dataTable.getDataSet(con, "*", "View_NodeSubmissionHistoryBySequecenumber", whr, "");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setUserCode(rs.getInt("iUserCode"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setRemark(rs.getString("vRemark"));
				dto.setFileVersionId(rs.getString("vFileVersionId"));
				dto.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
				dto.setSubmissionId(rs.getString("submissionId"));
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat"));
				dto.setCertifiedByName(rs.getString("CertifiedByName"));
				dto.setCertifiedOn(rs.getTimestamp("dCertifiedOn"));

				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;

	}

	public void UpdateWorkspaceNodeHistory(DTOWorkSpaceNodeHistory dto) {

		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{Call Proc_UpdateWorkspaceNodeHistory(?,?,?,?,?)}");
			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			proc.setInt(3, dto.getTranNo());
			proc.setString(4, dto.getDefaultFileFormat());
			proc.setInt(5, dto.getCertifiedBy());
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getFileName(String workspaceId, int nodeId) {

		String fileName = "";
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceId + "' AND iNodeId = " + nodeId + " "
					+ "AND iTranNo = (Select max(iTranNo) from workspacenodehistory where  vWorkSpaceId = '"
					+ workspaceId + "' and iNodeId = " + nodeId + ")";

			rs = dataTable.getDataSet(con, "vFileName", "workspacenodehistory", whr.toString(), "");
			while (rs.next()) {
				fileName = rs.getString("vFileName");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	// Added By Virendra Barad for Publish only PDF Files
	public DTOWorkSpaceNodeHistory getWorkspaceNodeHistorybyTranNoForPDF(String wsId, int nodeId, int tranNo) {

		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("vworkspaceId = '" + wsId + "'and inodeId = " + nodeId);
			sb.append(" and iTranNo = " + tranNo);

			sb.append(
					" AND ( vFilename LIKE '%.pdf' OR  vFilename LIKE '%.PDF') and iTranNo >(SELECT ISNULL(MAX(iTranNo),0) FROM InternalLabelHistory WHERE vWorkspaceId = '"
							+ wsId
							+ "' AND iLabelNo =(SELECT ISNULL(MAX(iLabelNo),0) FROM InternalLabelMst WHERE vWorkspaceId = '"
							+ wsId
							+ "'  AND vLabelId IN(SELECT vLabelId FROM View_SubmissionInfoDMSDtl WHERE vWorkspaceId = '"
							+ wsId
							+ "'  AND vCurrentSeqNumber =(SELECT MAX(vCurrentSeqNumber) FROM View_SubmissionInfoDMSDtl where vWorkspaceId = '"
							+ wsId + "' AND cConfirm = 'Y'))))");
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "*", "workspaceNodeHistory", sb.toString(), "");
			if (rs.next()) {

				dto.setWorkSpaceId(rs.getString("vWorkspaceId")); // workspaceId
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setTranNo(rs.getInt("itranNo")); // tranNo
				dto.setFileName(rs.getString("vFileName")); // fileName
				dto.setFileType(rs.getString("vFileType")); // fileType
				dto.setFolderName(rs.getString("vFolderName")); // folderName
				dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
				dto.setRemark(rs.getString("vRemark")); // remark
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
				dto.setStageId(rs.getInt("istageId")); // stageId

			}
			rs.close();

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getAllNodesLastHistoryForPDFPublish(ArrayList<String> workspaceids,
			int[] nodeIds) {

		ArrayList<DTOWorkSpaceNodeHistory> latestNodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();

		if (workspaceids != null && workspaceids.size() > 0) {
			try {
				Connection con = ConnectionManager.ds.getConnection();
				ResultSet rs = null;

				String fields = "vWorkspaceId,vWorkSpaceDesc,vBaseWorkFolder,iNodeId,iTranNo,vFileName,vFolderName,"
						+ "iStageId,vStageDesc,iModifyBy,vUserName,dModifyOn,vNodeName,vNodeFolderName,vUserDefineVersionId";

				StringBuffer whrBuffer = new StringBuffer();

				for (int i = 0; i < workspaceids.size(); i++) {
					if (i != 0) {
						whrBuffer.append(",");
					}
					whrBuffer.append("'" + workspaceids.get(i) + "'");

				}
				String whr = " vWorkspaceId in (" + whrBuffer + ") " + "and cStatusIndi<>'D' and vFileName <>'No File'"
						+ " and iNodeId not in (Select iNodeId from workspacenodevoid where vWorkspaceId=" + whrBuffer
						+ ")";
				if (nodeIds != null) {
					whrBuffer = new StringBuffer();
					whrBuffer.append(" AND  iNodeId IN(");
					for (int i = 0; i < nodeIds.length; i++) {
						if (i != 0) {
							whrBuffer.append(",");
						}
						whrBuffer.append(nodeIds[i]);
					}
					whrBuffer.append(") ");

					whr += whrBuffer;
				}
				rs = dataTable.getDataSet(con, fields, "View_NodesLatestHistory", whr, "vWorkspaceId,iNodeId");

				while (rs.next()) {
					DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
					dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
					dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
					dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
					dto.setNodeId(rs.getInt("iNodeId"));
					dto.setTranNo(rs.getInt("iTranNo"));
					dto.setFolderName(rs.getString("vFolderName"));
					dto.setNodeFolderName(rs.getString("vNodeFolderName"));
					dto.setFileName(rs.getString("vFileName"));
					dto.setStageId(rs.getInt("iStageId"));
					dto.setStageDesc(rs.getString("vStageDesc"));
					dto.setModifyBy(rs.getInt("iModifyBy"));
					dto.setUserName(rs.getString("vUserName"));
					dto.setModifyOn(rs.getTimestamp("dModifyOn"));
					dto.setNodeName(rs.getString("vNodeName"));
					dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
					latestNodeHistory.add(dto);
				}

				rs.close();
				con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return latestNodeHistory;

	}

	// Create by Virendra Barad for get StageDesc of StageId
	public String getStageDesc(int stageId) {
		String StageDesc = "";
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			String where = " iStageId = '" + stageId + "' ";
			rs = dataTable.getDataSet(con, "vStageDesc", "Stagemst", where, "");
			if (rs.next()) {
				StageDesc = rs.getString("vStageDesc");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return StageDesc;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getLatestNodeHistory(String workspaceid, int nodeId) {
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " ";

			rs = dataTable.getDataSet(con, "TOP 1 *", "View_NodeSubmissionHistoryBySequecenumber", whr, "iTranNo DESC");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
				dto.setRemark(rs.getString("vRemark"));
				if (rs.getString("vFileVersionId").equals("")) {
					dto.setFileVersionId("");
					dto.setLastClosed('N');
					dto.setUserDefineVersionId("");
					dto.setCurrentSeqNumber("");
					dto.setSubmissionId("");
				} else {
					dto.setFileVersionId(rs.getString("vFileVersionId"));
					dto.setLastClosed(rs.getString("isLastClosed").charAt(0));
					dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
					dto.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
					dto.setSubmissionId(rs.getString("submissionId"));
				}
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat"));

				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;

	}

	public int getStageIdfromWSHistory(String wsId, int nodeId) {
		int stageId = 0;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			where += " and iNodeId =" + nodeId + " ";
			where += "and  itranNo =(SELECT MAX(itranNo) FROM workspaceNodeHistory where vworkspaceId = '" + wsId
					+ "'and inodeId =" + nodeId + ")";

			ResultSet rs = dataTable.getDataSet(con, "iStageId", "WorkspaceNodeHistory", where, "");
			if (rs.next()) {
				stageId = rs.getInt("iStageId");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stageId;
	}

	public String getCurrentSeq(String workspaceid, int nodeId) {

		String currentSeq = "";
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId;
			whr += " and iTranNo = (Select max(iTranNo) from View_NodeSubmissionHistoryBySequecenumber Where  vWorkspaceId = '"
					+ workspaceid + "' AND iNodeId =" + nodeId + ")";

			rs = dataTable.getDataSet(con, "vCurrentSeqNumber", "View_NodeSubmissionHistoryBySequecenumber", whr, "");
			if (rs.next()) {
				currentSeq = rs.getString("vCurrentSeqNumber");
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return currentSeq;

	}

	public Vector<DTOAssignNodeRights> getAssignNodeRightsData(String wsId, int nodeId, int stageId) {
		Vector<DTOAssignNodeRights> data = new Vector<DTOAssignNodeRights>();
		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			String where = "vWorkSpaceId = '" + wsId + "' and iNodeId = " + nodeId + " and iStageId = " + stageId;
			rs = dataTable.getDataSet(con, "*", "assignNodeRights", where, "iModifyBy");
			while (rs.next()) {
				DTOAssignNodeRights dto = new DTOAssignNodeRights();
				dto.setWorkspaceId(rs.getString("vWorkSpaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setFlag(rs.getString("vFlag"));
				dto.setModifyOn(rs.getTimestamp("dMOdifyOn"));
				data.addElement(dto);
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

		return data;
	}

	public Vector<DTOAssignNodeRights> getAllDataForNodeId(String wsId, int nodeId) {
		Vector<DTOAssignNodeRights> data = new Vector<DTOAssignNodeRights>();
		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			String where = "vWorkSpaceId = '" + wsId + "' and iNodeId = " + nodeId;
			rs = dataTable.getDataSet(con, "*", "assignNodeRights", where, "iModifyBy");
			while (rs.next()) {
				DTOAssignNodeRights dto = new DTOAssignNodeRights();
				dto.setWorkspaceId(rs.getString("vWorkSpaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setFlag(rs.getString("vFlag"));
				data.addElement(dto);
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

		return data;
	}

	public Vector<DTOAssignNodeRights> getAllDataForNodeIdByUserId(String wsId, int nodeId, int userCode) {
		Vector<DTOAssignNodeRights> data = new Vector<DTOAssignNodeRights>();
		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			String where = "vWorkSpaceId = '" + wsId + "' and iNodeId = " + nodeId + " and iModifyBy = " + userCode;
			rs = dataTable.getDataSet(con, "*", "assignNodeRights", where, "iModifyBy");
			while (rs.next()) {
				DTOAssignNodeRights dto = new DTOAssignNodeRights();
				dto.setWorkspaceId(rs.getString("vWorkSpaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setFlag(rs.getString("vFlag"));
				data.addElement(dto);
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

		return data;
	}

	public DTOAssignNodeRights checkDataExistsForUser(String wsId, int nodeId, int stageId, int userCode) {
		DTOAssignNodeRights dto = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			String where = "vWorkSpaceId = '" + wsId + "' and iNodeId = " + nodeId + " and iStageId = " + stageId
					+ " and iModifyBy = " + userCode;
			rs = dataTable.getDataSet(con, "*", "assignNodeRights", where, "iModifyBy");
			while (rs.next()) {
				dto = new DTOAssignNodeRights();
				dto.setWorkspaceId(rs.getString("vWorkSpaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
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

	public ArrayList<DTOWorkSpaceNodeHistory> getWorkspaceNodeHistory(String wsId, int nodeId, int tranNo)
			throws SQLException {

		ArrayList<DTOWorkSpaceNodeHistory> docHistory = new ArrayList<DTOWorkSpaceNodeHistory>();

		Connection con = ConnectionManager.ds.getConnection();

		String whr = " vWorkspaceId = '" + wsId + "' AND iNodeId = " + nodeId + " and itranno=" + tranNo;
		ResultSet rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whr, "");

		while (rs.next()) {
			DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();

			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setTranNo(rs.getInt("iTranNo"));
			dto.setFileName(rs.getString("vFileName"));
			dto.setFolderName(rs.getString("vFolderName"));
			dto.setCoOrdinates(rs.getString("vCoordinates"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setFileType(rs.getString("vFileType"));
			docHistory.add(dto);
		}
		rs.close();
		con.close();
		return docHistory;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getPDFSignOffDocDetail(String workspaceid, int nodeId) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " ";

			rs = dataTable.getDataSet(con, "*", "View_PDFSingOffDocDetail", whr, "iTranNo");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}

				dto.setRemark(rs.getString("vRemark"));
				dto.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
				dto.setSubmissionId(rs.getString("submissionId"));

				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;

	}

	public ArrayList<DTOWorkSpaceNodeHistory> getPendingReportSingOff(DTOWorkSpaceNodeHistory dto) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOWorkSpaceNodeHistory> data = new ArrayList<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			StringBuffer query = new StringBuffer();
			query.append(" vWorkspaceId = '" + dto.getWorkSpaceId() + "'");
			query.append(" and iUserCode = " + dto.getModifyBy() + "");
			if (dto.getNodeId() != 0) {
				query.append(" and iNodeId = " + dto.getNodeId() + "");
			}
			query.append(" and currentStageId <> ''");
			ResultSet rs = dataTable.getDataSet(con,
					"distinct iTranNo,vBaseWorkFolder,vworkspaceid,inodeId,vFileName,vNodeName,vNodeDisplayName,currentStageId,currentStageDesc,nextStageDesc,nextStageId",
					"view_SignOffPendingWorkActivity", query.toString(), "vNodeDisplayName");
			while (rs.next()) {
				dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setStageId(rs.getInt("currentStageId"));
				dto.setStageDesc(rs.getString("currentStageDesc"));
				dto.setNextStageDesc(rs.getString("nextStageDesc"));
				dto.setNextStageId(rs.getInt("nextStageId"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));

				ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();

				String whr = " vWorkspaceId = '" + dto.getWorkSpaceId() + "' AND iNodeId = " + dto.getNodeId() + " ";

				ResultSet rs1 = dataTable.getDataSet(con, "*", "View_NodeSubmissionHistoryBySequecenumber", whr,
						"iTranNo");

				while (rs1.next()) {
					DTOWorkSpaceNodeHistory getNodeHistory = new DTOWorkSpaceNodeHistory();
					getNodeHistory.setWorkSpaceId(rs1.getString("vWorkspaceId"));
					getNodeHistory.setWorkSpaceDesc(rs1.getString("vWorkSpaceDesc"));
					getNodeHistory.setNodeId(rs1.getInt("iNodeId"));
					getNodeHistory.setTranNo(rs1.getInt("iTranNo"));
					getNodeHistory.setFileName(rs1.getString("vFileName"));
					getNodeHistory.setStagesIds(rs1.getInt("iStageId"));
					getNodeHistory.setStageHistory(rs1.getString("vStageDesc"));
					getNodeHistory.setUserName(rs1.getString("vUserName"));
					getNodeHistory.setModifyOn(rs1.getTimestamp("dModifyOn"));
					getNodeHistory.setRoleName(rs1.getString("vRoleName"));
					if (countryCode.equalsIgnoreCase("IND")) {
						time = docMgmtImpl.TimeZoneConversion(getNodeHistory.getModifyOn(), locationName, countryCode);
						getNodeHistory.setISTDateTime(time.get(0));
					} else {
						time = docMgmtImpl.TimeZoneConversion(getNodeHistory.getModifyOn(), locationName, countryCode);
						getNodeHistory.setISTDateTime(time.get(0));
						getNodeHistory.setESTDateTime(time.get(1));
					}
					nodeHistory.add(getNodeHistory);
				}
				dto.setNodeSignOffHistory(nodeHistory);
				data.add(dto);
				dto = null;
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public Vector<DTOWorkSpaceNodeHistory> getProjectTrackingHistory(String workspaceid, int nodeId) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeHistory> nodeHistory = new Vector<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " ";

			rs = dataTable.getDataSet(con,
					"vWorkspaceId,iNodeId,vUserName,vVoidBy,vStageDesc,iCompletedStageId,vUserGroupName,dModifyOn,vVoidOn,vRoleName,vFlag",
					"view_workspaceUserTrackingDetail", whr, "iStageId,iCompletedStageId desc");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				// dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setCompletedStageId(rs.getInt("iCompletedStageId"));
				dto.setUserTypeName(rs.getString("vUserGroupName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setFileType(rs.getString("vFLag")); // set vFlag as FileType
				dto.setVoidBy(rs.getString("vVoidBy"));
				dto.setVoidModifyOn(rs.getTimestamp("vVoidOn"));
				dto.setRoleName(rs.getString("vRoleName"));
				if (dto.getRoleName() == null) {
					dto.setRoleName("-");
				}

				if (dto.getVoidBy() == null) {
					dto.setVoidBy("-");
				}
				if (dto.getVoidModifyOn() != null) {
					if (countryCode.equalsIgnoreCase("IND")) {
						time = docMgmtImpl.TimeZoneConversion(dto.getVoidModifyOn(), locationName, countryCode);
						dto.setVoidISTDateTime(time.get(0));
					} else {
						time = docMgmtImpl.TimeZoneConversion(dto.getVoidModifyOn(), locationName, countryCode);
						dto.setVoidISTDateTime(time.get(0));
						dto.setVoidESTDateTime(time.get(1));
					}
				} else {
					dto.setVoidISTDateTime("-");
					dto.setVoidESTDateTime("-");
				}
				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;

	}

	public Vector<DTOWorkSpaceNodeHistory> getMypendingWorkNew(String wsId, int userCode) {

		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();
		DTOWorkSpaceNodeHistory dto = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			StringBuffer query = new StringBuffer();
			query.append(" vWorkspaceId = '" + wsId + "'");
			query.append(" and iUserCode = " + userCode + "");
			ResultSet rs = dataTable.getDataSet(con, "*", "view_MyPendingWorkActivity", query.toString(), "");
			while (rs.next()) {
				dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setUserCode(rs.getInt("iUserCode"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setCompletedStageId(rs.getInt("iCompletedStageId"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
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

	public Vector<DTOWorkSpaceNodeHistory> getSendBackFileDetail(DTOWorkSpaceNodeHistory dto) {

		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			StringBuffer query = new StringBuffer();
			query.append(" vWorkspaceId = '" + dto.getWorkSpaceId() + "'");
			query.append(" and imodifyby = " + dto.getModifyBy() + "");
			if (dto.getNodeId() != 0) {
				query.append(" and iNodeId = " + dto.getNodeId() + "");
			}
			query.append(" and currentStageId <> 100");
			query.append(" and vFileName <>'No File'");// For Not show deleted
														// file in Mypendingwork
			query.append(" and currentStageId = 0");
			ResultSet rs = dataTable.getDataSet(con,
					"distinct iTranNo,vBaseWorkFolder,vworkspaceid,inodeId,vFileName,vNodeName,vNodeDisplayName,currentStageId,currentStageDesc",
					"view_myPendingWork", query.toString(), "vNodeDisplayName");
			while (rs.next()) {
				dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setStageId(rs.getInt("currentStageId"));
				dto.setStageDesc(rs.getString("currentStageDesc"));
				dto.setRemark(rs.getString("vBaseWorkFolder")); // set
				// baseWorkFolder
				// as Remark
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
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

	public Vector<DTOWorkSpaceNodeHistory> getMyPendingWorksReportCSV(DTOWorkSpaceNodeHistory dto) {

		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			StringBuffer query = new StringBuffer();
			query.append(" vWorkspaceId = '" + dto.getWorkSpaceId() + "'");
			query.append(" and iUserCode = " + dto.getModifyBy() + "");
			if (dto.getNodeId() != 0) {
				query.append(" and iNodeId = " + dto.getNodeId() + "");
			}
			query.append(" and currentStageId <> ''");

			ResultSet rs = dataTable.getDataSet(con,
					"distinct iTranNo,vBaseWorkFolder,vworkspaceid,inodeId,vFileName,vNodeName,vNodeDisplayName,currentStageId,currentStageDesc,nextStageDesc,nextStageId",
					"view_MyPendingWorkActivityCSV", query.toString(), "vNodeDisplayName");
			while (rs.next()) {
				dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setStageId(rs.getInt("currentStageId"));
				dto.setStageDesc(rs.getString("currentStageDesc"));
				dto.setNextStageDesc(rs.getString("nextStageDesc"));
				dto.setNextStageId(rs.getInt("nextStageId"));
				dto.setRemark(rs.getString("vBaseWorkFolder")); // set
				// baseWorkFolder
				// as Remark
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
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

	public Vector<DTOWorkSpaceNodeHistory> getProjectSignOffHistory(String workspaceid, int nodeId) {
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeHistory> nodeHistory = new Vector<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId
					+ " AND dModifyOn >Convert(datetime, '1900-01-01' ) ";

			rs = dataTable.getDataSet(con,
					"vWorkspaceId,iTranNo,iNodeId,iUserCode,vUserName,vLocationName,vCountryCode,vStageDesc,iCompletedStageId,vUserGroupName,dModifyOn,vFlag,vstyle,"
							+ "vFileName,vFilePath,vSignId,vRoleCode,vRoleName",
					"view_workspaceUserTrackingDetail", whr, "dModifyOn");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				// dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setUserCode(rs.getInt("iUserCode"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setCompletedStageId(rs.getInt("iCompletedStageId"));
				dto.setUserTypeName(rs.getString("vUserGroupName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setFileType(rs.getString("vFLag"));
				dto.setCountryCode(rs.getString("vCountryCode"));
				dto.setLocationName(rs.getString("vLocationName"));
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), dto.getLocationName(), dto.getCountryCode());
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
				dto.setStyle(rs.getString("vStyle"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFilePath(rs.getString("vFilePath"));
				dto.setSignId(rs.getString("vsignid"));
				dto.setRoleName(rs.getString("vRoleName"));
				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;

	}

	public Vector<Integer> getNodeDetailsForPdfPublishForCheck(String wsId) {

		Vector<Integer> nodeids = new Vector<Integer>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String fields = "iNodeId";
			// ResultSet rs=dataTable.getDataSet(con, fields,
			// "View_NodesLatestHistory",
			// "vWorkspaceId='"+wsId+"' and vfilename like '%.pdf'","");
			String whr = " vWorkspaceId = '" + wsId + "' and (iStageId=100 OR (NodeTypeIndi ='K' AND iStageId=10)) ";
			ResultSet rs = dataTable.getDataSet(con, fields, "View_NodesLatestHistoryForPdfPublishwithAttr", whr,
					"iNodeNo");

			while (rs.next()) {
				nodeids.add(rs.getInt("iNodeId"));
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeids;
	}

	public DTOWorkSpaceNodeHistory getWorkspaceNodeHistorybyTranNoForHTMLPublish(String wsId, int nodeId, int tranNo) {

		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String fileExt = propertyInfo.getValue("FileExt");
		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		String temp[] = fileExt.split(",");

		String LikeQuerry = "";
		for (int i = 0; i < temp.length; i++) {

			LikeQuerry += "vFilename LIKE ";
			LikeQuerry += "'%." + temp[i] + "'";
			LikeQuerry += " OR ";
		}

		for (int j = 0; j < 4; j++) {
			LikeQuerry = StringUtils.chop(LikeQuerry);
		}
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("vworkspaceId = '" + wsId + "'and inodeId = " + nodeId);
			sb.append(" and iTranNo = " + tranNo);

			sb.append(" AND ( " + LikeQuerry
					+ " ) and iStageId=100 and iTranNo >(SELECT ISNULL(MAX(iTranNo),0) FROM InternalLabelHistory WHERE vWorkspaceId = '"
					+ wsId
					+ "' AND iLabelNo =(SELECT ISNULL(MAX(iLabelNo),0) FROM InternalLabelMst WHERE vWorkspaceId = '"
					+ wsId + "'  AND vLabelId IN(SELECT vLabelId FROM View_SubmissionInfoDMSDtl WHERE vWorkspaceId = '"
					+ wsId
					+ "'  AND vCurrentSeqNumber =(SELECT MAX(vCurrentSeqNumber) FROM View_SubmissionInfoDMSDtl where vWorkspaceId = '"
					+ wsId + "' AND cConfirm = 'Y'))))");
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "*", "workspaceNodeHistory", sb.toString(), "");
			if (rs.next()) {

				dto.setWorkSpaceId(rs.getString("vWorkspaceId")); // workspaceId
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setTranNo(rs.getInt("itranNo")); // tranNo
				dto.setFileName(rs.getString("vFileName")); // fileName
				dto.setFileType(rs.getString("vFileType")); // fileType
				dto.setFolderName(rs.getString("vFolderName")); // folderName
				dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
				dto.setRemark(rs.getString("vRemark")); // remark
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
				dto.setStageId(rs.getInt("istageId")); // stageId

			}
			rs.close();

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public DTOWorkSpaceNodeHistory getWorkspaceNodeHistorybyTranNoForHTMLPublishWithTC(String wsId, int nodeId,
			int tranNo) {

		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" vworkspaceId = '" + wsId + "'and inodeId = " + nodeId);
			sb.append(" and iTranNo = " + tranNo);

			sb.append(
					" AND ( vFilename LIKE '%.doc' OR  vFilename LIKE '%.docx' OR  vFilename LIKE '%.pdf' OR  vFilename LIKE '%.PDF')"
							+ " and (iStageId=100 OR (iStageId=10 and (select cNodeTypeIndi from workspacenodedetail "
							+ "where vWorkSpaceId='" + wsId + "' and iNodeId=" + nodeId + ") ='K'))"
							+ "and iTranNo >(SELECT ISNULL(MAX(iTranNo),0) FROM InternalLabelHistory WHERE vWorkspaceId = '"
							+ wsId
							+ "' AND iLabelNo =(SELECT ISNULL(MAX(iLabelNo),0) FROM InternalLabelMst WHERE vWorkspaceId = '"
							+ wsId
							+ "'  AND vLabelId IN(SELECT vLabelId FROM View_SubmissionInfoDMSDtl WHERE vWorkspaceId = '"
							+ wsId
							+ "'  AND vCurrentSeqNumber =(SELECT MAX(vCurrentSeqNumber) FROM View_SubmissionInfoDMSDtl where vWorkspaceId = '"
							+ wsId + "' AND cConfirm = 'Y'))))");

			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "*", "workspaceNodeHistory", sb.toString(), "");
			if (rs.next()) {

				dto.setWorkSpaceId(rs.getString("vWorkspaceId")); // workspaceId
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setTranNo(rs.getInt("itranNo")); // tranNo
				dto.setFileName(rs.getString("vFileName")); // fileName
				dto.setFileType(rs.getString("vFileType")); // fileType
				dto.setFolderName(rs.getString("vFolderName")); // folderName
				dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
				dto.setRemark(rs.getString("vRemark")); // remark
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
				dto.setStageId(rs.getInt("istageId")); // stageId

			}
			rs.close();

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> showNodeHistory(String workspaceid, int userCode) {
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' and iModifyBy= " + userCode;

			rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whr, "");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getAllNodesLastHistoryForPDFPublishForCSV(String workspaceid) {

		ArrayList<DTOWorkSpaceNodeHistory> latestNodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " WorkspaceId ='" + workspaceid + "'";
			rs = dataTable.getDataSet(con, "*", "view_GetAllApprovedFilesReport", whr, "NodeId");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("WorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("WorkSpaceDesc"));
				dto.setNodeId(rs.getInt("NodeId"));
				latestNodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return latestNodeHistory;

	}

	public Vector<DTOWorkSpaceNodeHistory> getUserRightsDetailForCSV(String workspaceids, int nodeIds) {

		Vector<DTOWorkSpaceNodeHistory> latestNodeHistory = new Vector<DTOWorkSpaceNodeHistory>();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " WorkspaceId ='" + workspaceids + "'" + "and nodeId = " + nodeIds
					+ "and StatuisIndi<>'D' and FileName <>'No File' and StageId=100";
			rs = dataTable.getDataSet(con, "*", "View_NodesLatestHistoryForCSV", whr, "NodeId");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("WorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("WorkSpaceDesc"));
				dto.setNodeId(rs.getInt("NodeId"));
				dto.setTranNo(rs.getInt("TranNo"));
				dto.setFolderName(rs.getString("FolderName"));
				dto.setFileName(rs.getString("FileName"));
				dto.setStageId(rs.getInt("StageId"));
				dto.setModifyBy(rs.getInt("ModifyBy"));

				latestNodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return latestNodeHistory;

	}

	public ArrayList<DTOWorkSpaceNodeHistory> showNodeHistory(String workspaceid, int userCode, int nodeId,
			int stageId) {
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' and iModifyBy= " + userCode + " and iNodeId = " + nodeId
					+ " and istageId = " + stageId
					+ " and iTranNo=(select MAX(iTranNo) from workspacenodehistory where vWorkSpaceId = '" + workspaceid
					+ "' and iNodeId = " + nodeId + "and iModifyBy= " + userCode + ")";

			rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whr, "");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setFileName(rs.getString("vFileName"));
				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> showNodeHistoryForWs(String workspaceid, int userCode, int stageId) {
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' and iModifyBy= " + userCode + " and istageId = "
					+ stageId;

			rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whr, "");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getUpcomingFileReport(int userCode, int userGroupCode) {

		ArrayList<DTOWorkSpaceNodeHistory> data = new ArrayList<DTOWorkSpaceNodeHistory>();

		try {

			Connection con = ConnectionManager.ds.getConnection();
			// String where="userCode = "+userCode+" And userGroupCode =
			// "+userGroupCode+" And (FileName Is Null OR FileNAme ='No File')";
			String where = "userCode = " + userCode + " And userGroupCode = " + userGroupCode + " And FileName Is Null";
			ResultSet rs = dataTable.getDataSet(con, "*", "view_GetAllUpcomingFile", where, "workspaceid desc");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("WorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("WorkspaceDesc"));
				dto.setNodeId(rs.getInt("NodeId"));
				dto.setNodeName(rs.getString("NodeName"));
				dto.setFolderName(rs.getString("FolderName"));
				dto.setUserCode(rs.getInt("UserCode"));
				dto.setUserGroupCode(rs.getInt("UserGroupCode"));
				dto.setStageId(rs.getInt("stageId"));
				dto.setStageDesc(rs.getString("StageDesc"));
				dto.setFileName(rs.getString("FileName"));
				dto.setProjectType(rs.getString("ProjectType").charAt(0));

				data.add(dto);
				dto = null;
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public Vector<DTOWorkSpaceNodeHistory> getVoidFileHistory(String wsId, int nodeId) {

		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + wsId + "' AND iNodeId = " + nodeId;

			rs = dataTable.getDataSet(con, "*", "workspacenodevoid", whr.toString(), "");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setRemark(rs.getString("vRemark"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				data.add(dto);
				dto = null;

			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public Vector<DTOWorkSpaceNodeHistory> getWorkspaceNodeHistoryForVoid(String wsId, int nodeId) {

		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + wsId + "' AND iNodeId = " + nodeId + " "
					+ "AND iTranNo = (Select max(iTranNo) from workspacenodehistory where  vWorkSpaceId = '" + wsId
					+ "' and iNodeId = " + nodeId + ")";

			rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whr, "");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				data.add(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void insertWSNodeVoidDetail(DTOWorkSpaceNodeHistory dto) {

		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Insert_workspacenodevoid(?,?,?,?,?,?,?,?,?)}");
			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			proc.setInt(3, dto.getTranNo());
			proc.setString(4, dto.getFileName());
			proc.setString(5, dto.getFolderName());
			proc.setInt(6, dto.getStageId());
			proc.setString(7, dto.getRemark());
			proc.setInt(8, dto.getModifyBy());
			proc.setString(9, Character.toString(dto.getStatusIndi()));
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<DTOWorkSpaceNodeHistory> showFullNodeHistoryWithVoidFiles(String workspaceid, int nodeId) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " ";

			rs = dataTable.getDataSet(con, "*", "View_NodeSubmissionHistoryWithVoidHistory", whr, "iTranNo");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));

				String fileExt = dto.getFileName().substring(dto.getFileName().lastIndexOf(".") + 1);
				dto.setFileExt(fileExt);

				dto.setFileType(rs.getString("vFileType"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}

				dto.setRemark(rs.getString("vRemark"));
				dto.setVoidRemark(rs.getString("VoidedRemark"));

				if (rs.getString("vFileVersionId").equals("")) {
					dto.setFileVersionId("");
					dto.setLastClosed('N');
					dto.setUserDefineVersionId("");
					dto.setCurrentSeqNumber("");
					dto.setSubmissionId("");
				} else {
					dto.setFileVersionId(rs.getString("vFileVersionId"));
					dto.setLastClosed(rs.getString("isLastClosed").charAt(0));
					dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
					dto.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
					dto.setSubmissionId(rs.getString("submissionId"));
				}
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat"));
				dto.setVoidBy(rs.getString("vVoidBy"));
				dto.setRoleName(rs.getString("vRoleName"));
				dto.setVoidModifyOn(rs.getTimestamp("dVoidOn"));
				if (dto.getVoidModifyOn() != null) {
					if (countryCode.equalsIgnoreCase("IND")) {
						time = docMgmtImpl.TimeZoneConversion(dto.getVoidModifyOn(), locationName, countryCode);
						dto.setVoidISTDateTime(time.get(0));
					} else {
						time = docMgmtImpl.TimeZoneConversion(dto.getVoidModifyOn(), locationName, countryCode);
						dto.setVoidISTDateTime(time.get(0));
						dto.setVoidESTDateTime(time.get(1));
					}
				} else {
					dto.setVoidISTDateTime("-");
					dto.setVoidESTDateTime("-");
				}
				dto.setCertifiedByName(rs.getString("CertifiedByName"));
				dto.setCertifiedOn(rs.getTimestamp("dCertifiedOn"));
				if (dto.getCertifiedOn() != null) {
					if (countryCode.equalsIgnoreCase("IND")) {
						time = docMgmtImpl.TimeZoneConversion(dto.getCertifiedOn(), locationName, countryCode);
						dto.setISTCertifiedDate(time.get(0));
					} else {
						time = docMgmtImpl.TimeZoneConversion(dto.getCertifiedOn(), locationName, countryCode);
						dto.setISTCertifiedDate(time.get(0));
						dto.setESTCertifiedDate(time.get(1));
					}
				} else {
					dto.setISTCertifiedDate("-");
					dto.setESTCertifiedDate("-");
				}
				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;
	}

	public boolean showNodeHistoryForCSV(String wsId, int[] nodeId) {
		boolean isFileUploadflag = false;
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			DataTable dataTable = new DataTable();

			outer: for (int j = 0; j < nodeId.length; j++) {

				String whr = " vWorkspaceId = '" + wsId + "' and iNodeId = " + nodeId[j] + " ";

				rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whr, "");

				while (rs.next()) {
					isFileUploadflag = true;
					break outer;
				}
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isFileUploadflag;
	}

	public Vector<DTOWorkSpaceNodeHistory> showFullNodeHistoryForCSV(String workspaceid, int nodeId) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeHistory> nodeHistory = new Vector<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " ";

			rs = dataTable.getDataSet(con, "*", "View_NodeSubmissionHistoryWithVoidHistory", whr, "iTranNo");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFileType(rs.getString("vFileType"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setUserTypeName(rs.getString("vUserGroupName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}

				dto.setRemark(rs.getString("vRemark"));
				dto.setVoidRemark(rs.getString("VoidedRemark"));

				if (rs.getString("vFileVersionId").equals("")) {
					dto.setFileVersionId("");
					dto.setLastClosed('N');
					dto.setUserDefineVersionId("");
					dto.setCurrentSeqNumber("");
					dto.setSubmissionId("");
				} else {
					dto.setFileVersionId(rs.getString("vFileVersionId"));
					dto.setLastClosed(rs.getString("isLastClosed").charAt(0));
					dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
					dto.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
					dto.setSubmissionId(rs.getString("submissionId"));
				}
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat"));
				dto.setVoidBy(rs.getString("vVoidBy"));
				dto.setRoleName(rs.getString("vRoleName"));
				if (dto.getVoidBy() == null) {
					dto.setVoidBy("-");
				}
				dto.setVoidModifyOn(rs.getTimestamp("dVoidOn"));
				if (dto.getVoidModifyOn() != null) {
					if (countryCode.equalsIgnoreCase("IND")) {
						time = docMgmtImpl.TimeZoneConversion(dto.getVoidModifyOn(), locationName, countryCode);
						dto.setVoidISTDateTime(time.get(0));
					} else {
						time = docMgmtImpl.TimeZoneConversion(dto.getVoidModifyOn(), locationName, countryCode);
						dto.setVoidISTDateTime(time.get(0));
						dto.setVoidESTDateTime(time.get(1));
					}
				} else {
					dto.setVoidISTDateTime("-");
					dto.setVoidESTDateTime("-");
				}

				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;
	}

	public int getMaxNodeHistory(String wsId, int nodeId) {
		int stageId = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			where += "and iNodeId =" + nodeId;
			where += " and iTranNo = (select max(iTranNo) from workspacenodehistory where vworkspaceid='" + wsId
					+ "' and inodeid=" + nodeId + ") ";
			ResultSet rs = dataTable.getDataSet(con, "istageId", "WorkspaceNodeHistory", where, "");
			if (rs.next()) {
				stageId = rs.getInt("istageId");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stageId;
	}

	public String checkSendForReview(String wsId, int nodeId) {
		String sendForReview = "";
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			where += "and iNodeId =" + nodeId;
			where += " and iTranNo = (select max(iTranNo) from workspacenodehistory where vworkspaceid='" + wsId
					+ "' and inodeid=" + nodeId + ") ";
			ResultSet rs = dataTable.getDataSet(con, "vFileType", "WorkspaceNodeHistory", where, "");
			if (rs.next()) {
				sendForReview = rs.getString("vFileType");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sendForReview;
	}

	public Vector<DTOWorkSpaceNodeHistory> getNodeHistoryForDocSign(String wsId, int nodeId) {

		Vector<DTOWorkSpaceNodeHistory> nodeHistory = new Vector<DTOWorkSpaceNodeHistory>();
		ResultSet rs = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			where += "and iNodeId =" + nodeId;
			where += " and iTranNo = (select max(iTranNo) from workspacenodehistory where vworkspaceid='" + wsId
					+ "' and inodeid=" + nodeId + ") ";
			rs = dataTable.getDataSet(con, "*", "WorkspaceNodeHistory", where, "");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setBaseWorkFolder(rs.getString("vFolderName"));
				nodeHistory.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeHistory;
	}

	public DTOWorkSpaceNodeHistory getNodeHistoryForSignOff(String wsId, int nodeId) {

		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		ResultSet rs = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			where += "and iNodeId =" + nodeId;
			where += " and iTranNo = (select max(iTranNo) from workspacenodehistory where vworkspaceid='" + wsId
					+ "' and inodeid=" + nodeId + ") ";
			rs = dataTable.getDataSet(con, "*", "WorkspaceNodeHistory", where, "");
			while (rs.next()) {
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFolderName(rs.getString("vFolderName"));
			}
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public Vector<DTOWorkSpaceNodeHistory> getProjectTrackingHistoryForTimeLine(String workspaceid, int nodeId) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeHistory> nodeHistory = new Vector<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " And dstartdate<>''";

			rs = dataTable.getDataSet(con,
					"vWorkspaceId,iNodeId,iUserCode,iUserGroupCode,iStageId,vUserName,vVoidBy,vStageDesc,dStartDate,dEndDate,dAdjustDate,iHours,iCompletedStageId,vUserGroupName"
							+ ",dModifyOn,vVoidOn,vFlag,cStatusIndi,vRoleName",
					"view_workspaceTimelineUserRights", whr, "iStageId,iCompletedStageId desc");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				// dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setUserCode(rs.getInt("iUserCode"));
				dto.setUserGroupCode(rs.getInt("iUserGroupCode"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setCompletedStageId(rs.getInt("iCompletedStageId"));
				dto.setUserTypeName(rs.getString("vUserGroupName"));
				dto.setdStartDate(rs.getTimestamp("dStartDate"));
				Timestamp tempStartDate = Timestamp.valueOf("1900-01-01 00:00:00");
				int t1 = dto.getdStartDate().compareTo(tempStartDate);
				if (t1 == 0) {
					dto.setISTStartDate("-");
					dto.setESTStartDate("-");
				} else {
					if (countryCode.equalsIgnoreCase("IND")) {
						time = docMgmtImpl.TimeZoneConversion(dto.getdStartDate(), locationName, countryCode);
						dto.setISTStartDate(time.get(0));
					} else {
						time = docMgmtImpl.TimeZoneConversion(dto.getdStartDate(), locationName, countryCode);
						dto.setISTStartDate(time.get(0));
						dto.setESTStartDate(time.get(1));
					}
				}
				dto.setdEndDate(rs.getTimestamp("dEndDate"));
				Timestamp tempEndtDate = Timestamp.valueOf("1900-01-01 00:00:00");
				int t2 = dto.getdEndDate().compareTo(tempEndtDate);
				if (t2 == 0) {
					dto.setISTEndDate("-");
					dto.setESTEndDate("-");
				} else {
					if (countryCode.equalsIgnoreCase("IND")) {
						time = docMgmtImpl.TimeZoneConversion(dto.getdEndDate(), locationName, countryCode);
						dto.setISTEndDate(time.get(0));
					} else {
						time = docMgmtImpl.TimeZoneConversion(dto.getdEndDate(), locationName, countryCode);
						dto.setISTEndDate(time.get(0));
						dto.setESTEndDate(time.get(1));
					}
				}
				dto.setdAdjustDate(rs.getTimestamp("dAdjustDate"));
				Timestamp tempAdjusttDate = Timestamp.valueOf("1900-01-01 00:00:00");
				int t3 = dto.getdAdjustDate().compareTo(tempAdjusttDate);
				if (t3 == 0) {
					dto.setISTAdjustDate("-");
					dto.setESTAdjustDate("-");
				} else {
					if (countryCode.equalsIgnoreCase("IND")) {
						time = docMgmtImpl.TimeZoneConversion(dto.getdAdjustDate(), locationName, countryCode);
						dto.setISTAdjustDate(time.get(0));
					} else {
						time = docMgmtImpl.TimeZoneConversion(dto.getdAdjustDate(), locationName, countryCode);
						dto.setISTAdjustDate(time.get(0));
						dto.setESTAdjustDate(time.get(1));
					}
				}
				dto.setiHours(rs.getInt("iHours"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setFileType(rs.getString("vFLag")); // set vFlag as FileType
				dto.setVoidBy(rs.getString("vVoidBy"));
				dto.setVoidModifyOn(rs.getTimestamp("vVoidOn"));
				if (dto.getVoidBy() == null) {
					dto.setVoidBy("-");
				}
				if (dto.getVoidModifyOn() != null) {
					if (countryCode.equalsIgnoreCase("IND")) {
						time = docMgmtImpl.TimeZoneConversion(dto.getVoidModifyOn(), locationName, countryCode);
						dto.setVoidISTDateTime(time.get(0));
					} else {
						time = docMgmtImpl.TimeZoneConversion(dto.getVoidModifyOn(), locationName, countryCode);
						dto.setVoidISTDateTime(time.get(0));
						dto.setVoidESTDateTime(time.get(1));
					}
				} else {
					dto.setVoidISTDateTime("-");
					dto.setVoidESTDateTime("-");
				}
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				dto.setRoleName(rs.getString("vRoleName"));
				if (dto.getRoleName() == null) {
					dto.setRoleName("-");
				}
				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;

	}

	public Vector<DTOWorkSpaceNodeHistory> getCurrentStageDesc(String wsId, int nodeId) {

		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();
		try {
			Connection con = ConnectionManager.ds.getConnection();

			String where = " vWorkspaceId = '" + wsId + "' and iNodeId=" + nodeId
					+ "and itranno = (select max(itranno) "
					+ "From View_NodeSubmissionHistoryWithVoidHistory Where  vWorkspaceId = '" + wsId
					+ "' AND iNodeId =" + nodeId + ")";

			ResultSet rs = dataTable.getDataSet(con, "*", " View_NodeSubmissionHistoryWithVoidHistory", where, "");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vStageDesc"));
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

	public Vector<DTOWorkSpaceNodeHistory> getNodeHistoryForSectinDeletion(String wsId, int nodeId) {
		Vector<DTOWorkSpaceNodeHistory> dataNodeHis = new Vector<DTOWorkSpaceNodeHistory>();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			where += " and iNodeId =" + nodeId + " ";
			where += "and  itranNo =(SELECT MAX(itranNo) FROM workspaceNodeHistory where vworkspaceId = '" + wsId
					+ "'and inodeId =" + nodeId + ")";

			ResultSet rs = dataTable.getDataSet(con, "*", "WorkspaceNodeHistory", where, "");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				// dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				// dto.setAttrValue(rs.getString("vAttrValue"));
				// dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileType(rs.getString("vFileType"));
				// dto.setNodeFolderName(rs.getString("vNodeFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setStageId(rs.getInt("iStageId"));
				// dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				// dto.setUserName(rs.getString("vUserName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				// dto.setNodeName(rs.getString("vNodeName"));
				// dto.setUserDefineVersionId(rs.getString("vUserDefineVersionId"));
				dto.setDocTranNo(rs.getInt("iDocHistoryId"));
				dataNodeHis.add(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return dataNodeHis;
	}

	public void insertTemplateNodeHistory(DTOWorkSpaceNodeHistory dto) {
		String folderName = "";
		if (dto.getFolderName().equals("")) {
			folderName = "/" + dto.getWorkSpaceId() + "/" + dto.getNodeId() + "/" + dto.getTranNo();
		} else {
			folderName = dto.getFolderName();
		}
		System.out.println("Folder NBame(NodeId==>" + dto.getParentID());
		// here if the inodeid > total nodeid we get the parent nodeid
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Insert_templateNodeHistory(?,?,?,?,?,?,?,?,?)}");
			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			proc.setString(3, dto.getFileName());
			proc.setString(4, dto.getFileType());
			proc.setString(5, folderName);
			proc.setString(6, dto.getRemark());
			proc.setInt(7, dto.getModifyBy());
			proc.setString(8, Character.toString(dto.getStatusIndi()));
			proc.setString(9, dto.getDefaultFileFormat());
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<DTOWorkSpaceNodeHistory> showTemplateNodeHistory(String templateId, int nodeId) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		String templateBasePath = knetProperties.getValue("TemplateWorkFolder");

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " TemplateId = '" + templateId + "' AND NodeId = " + nodeId + " ";

			rs = dataTable.getDataSet(con, "*", "view_templateNodeHistory", whr, "");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("TemplateId"));
				dto.setNodeId(rs.getInt("NodeId"));
				dto.setTranNo(rs.getInt("TranNo"));
				dto.setBaseWorkFolder(templateBasePath);
				dto.setFolderName(rs.getString("FilePath"));
				dto.setFileName(rs.getString("FileName"));

				String fileExt = dto.getFileName().substring(dto.getFileName().lastIndexOf(".") + 1);
				dto.setFileExt(fileExt);

				dto.setFileType(rs.getString("FileType"));
				dto.setModifyBy(rs.getInt("ModifyBy"));
				dto.setUserName(rs.getString("UserName"));
				dto.setModifyOn(rs.getTimestamp("ModifyOn"));
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}

				dto.setRemark(rs.getString("Remark"));
				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;
	}

	public void insertUserSignature(DTOWorkSpaceNodeHistory dto) {
		String folderName = "";
		if (dto.getFolderName().equals("")) {
			folderName = "/" + dto.getUserCode() + "/" + dto.getTranNo();
		} else {
			folderName = dto.getFolderName();
		}
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Insert_usersignmst(?,?,?,?,?,?,?,?,?)}");
			proc.setInt(1, dto.getUserCode());
			proc.setString(2, dto.getFileType());
			proc.setString(3, dto.getFileName());
			proc.setString(4, folderName);
			proc.setString(5, dto.getRemark());
			proc.setInt(6, dto.getModifyBy());
			proc.setString(7, Character.toString(dto.getStatusIndi()));
			proc.setString(8, dto.getDefaultFileFormat());
			proc.setString(9, Character.toString(dto.getIsAdUser()));
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<DTOWorkSpaceNodeHistory> getUserSignatureDetail(int userId) {

		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		String basePath = knetProperties.getValue("signatureFile");
		ResultSet rs = null;
		try {

			Connection con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "*", "view_usersignmst",
					"TranNo = (Select max(itranno) from usersignmst where iUserId=" + userId + " and cStatusIndi<>'D')",
					"");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setTranNo(rs.getInt("TranNo"));
				dto.setFileName(rs.getString("FileName"));
				dto.setUserCode(rs.getInt("UserId"));
				dto.setFolderName(rs.getString("FilePath"));
				dto.setFileType(rs.getString("Style"));
				dto.setBaseWorkFolder(basePath);
				dto.setUserName(rs.getString("UserName"));
				dto.setModifyBy(rs.getInt("ModifyBy"));
				dto.setRemark(rs.getString("Remark"));
				dto.setModifyOn(rs.getTimestamp("ModifyOn"));
				dto.setUuId(rs.getString("SignId"));
				dto.setSignTranNo(dto.getTranNo());
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
				dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
				data.add(dto);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void UpdateNodeHistoryForESign(String wsId, int nodeId, String signId, int tranNo) {
		Connection con = null;
		int rs1 = 0;
		try {
			con = ConnectionManager.ds.getConnection();

			String query = "UPDATE WorkspaceNodeHistory SET " + "vSignId = '" + signId + "', iSignTranNo=" + tranNo
					+ " WHERE vWorkspaceId = '" + wsId + "'" + " AND iNodeId = " + nodeId
					+ " AND iTranNo= (select max(iTranNo) from WorkspaceNodeHistory" + " WHERE vWorkspaceId = '" + wsId
					+ "'" + " AND iNodeId = " + nodeId + ")";

			rs1 = dataTable.getDataSet1(con, query);

			con.close();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void UpdateNodeHistoryForRoleCode(String wsId, int nodeId, String roleCode) {
		Connection con = null;
		int rs1 = 0;
		try {
			con = ConnectionManager.ds.getConnection();

			String query = "UPDATE WorkspaceNodeHistory SET " + "vRoleCode = '" + roleCode + "' WHERE vWorkspaceId = '"
					+ wsId + "'" + " AND iNodeId = " + nodeId
					+ " AND iTranNo= (select max(iTranNo) from WorkspaceNodeHistory" + " WHERE vWorkspaceId = '" + wsId
					+ "'" + " AND iNodeId = " + nodeId + ")";

			rs1 = dataTable.getDataSet1(con, query);

			con.close();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<DTOWorkSpaceNodeHistory> getAllUserSignatureDetail(int userId) {

		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		String basePath = knetProperties.getValue("signatureFile");
		ResultSet rs = null;
		try {

			Connection con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "*", "view_usersignmst", "UserId=" + userId, "ModifyOn desc");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setTranNo(rs.getInt("TranNo"));
				dto.setFileName(rs.getString("FileName"));
				dto.setUserCode(rs.getInt("UserId"));
				dto.setFolderName(rs.getString("FilePath"));
				dto.setFileType(rs.getString("Style"));
				dto.setBaseWorkFolder(basePath);
				dto.setUserName(rs.getString("UserName"));
				dto.setModifyBy(rs.getInt("ModifyBy"));
				dto.setRemark(rs.getString("Remark"));
				dto.setUuId(rs.getString("SignId"));
				dto.setModifyOn(rs.getTimestamp("ModifyOn"));
				dto.setDefaultFileFormat(
						basePath + "/" + dto.getUserCode() + "/" + dto.getUuId() + "/" + dto.getFileName());
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
				dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
				char isAdUser = 0;
				if (rs.getString("IsAdUser") != null) {
					isAdUser = rs.getString("IsAdUser").charAt(0);
					dto.setIsAdUser(isAdUser);
				}
				data.add(dto);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void insertworkspacenodeofficehistory(DTOWorkSpaceNodeHistory dto, int mode) {

		try {
			Connection con = ConnectionManager.ds.getConnection();
			if (mode == 1) {
				CallableStatement proc = con
						.prepareCall("{ Call Insert_workspacenodeofficehistory(?,?,?,?,?,?,?,?,?,?,?)}");
				proc.setString(1, dto.getWorkSpaceId());
				proc.setInt(2, dto.getNodeId());
				// proc.setInt(3, dto.getTranNo());
				proc.setString(3, dto.getFileName());
				proc.setString(4, dto.getFileType());
				proc.setString(5, dto.getFilePath());
				proc.setString(6, Character.toString(dto.getClsUpload()));
				proc.setString(7, Character.toString(dto.getClsDownload()));
				// proc.setString(6, folderName);
				// proc.setString(7, Character.toString(dto.getRequiredFlag()));
				// proc.setInt(8, dto.getStageId());
				proc.setString(8, dto.getRemark());
				proc.setInt(9, dto.getModifyBy());
				// proc.setString(8,"test");
				// proc.setString(11, Character.toString(dto.getStatusIndi()));
				proc.setDouble(10, dto.getFileSize());
				proc.setInt(11, mode);
				proc.execute();
				proc.close();
			}
			if (mode == 2) {
				CallableStatement proc = con
						.prepareCall("{ Call Insert_workspacenodeofficehistory(?,?,?,?,?,?,?,?,?,?,?)}");
				proc.setString(1, dto.getWorkSpaceId());
				proc.setInt(2, dto.getNodeId());
				// proc.setInt(3, dto.getTranNo());
				proc.setString(3, dto.getFileName());
				proc.setString(4, dto.getFileType());
				proc.setString(5, dto.getFilePath());
				proc.setString(6, Character.toString(dto.getClsUpload()));
				proc.setString(7, Character.toString(dto.getClsDownload()));
				// proc.setString(6, folderName);
				// proc.setString(7, Character.toString(dto.getRequiredFlag()));
				// proc.setInt(8, dto.getStageId());
				proc.setString(8, dto.getRemark());
				proc.setInt(9, dto.getModifyBy());
				// proc.setString(8,"test");
				// proc.setString(11, Character.toString(dto.getStatusIndi()));
				proc.setDouble(10, dto.getFileSize());
				proc.setInt(11, mode);
				proc.execute();
				proc.close();
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertworkspacenodehistoryToUpdate(DTOWorkSpaceNodeHistory dto, int mode) {

		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Insert_workspacenodeofficehistoryToUpdate(?,?,?,?)}");
			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			if (mode == 2) {
				proc.setInt(3, 0);
			} else {
				proc.setInt(3, dto.getDocTranNo());
			}
			proc.setInt(4, mode);
			// proc.setInt(3, dto.getTranNo());

			// proc.setString(8,"test");
			// proc.setString(11, Character.toString(dto.getStatusIndi()));
			// proc.setInt(10, mode);
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<DTOWorkSpaceNodeHistory> getWorkspaceNodeHistoryForOfficeForFlagCheck(String wsId, int nodeId,
			int uCode) {
		Vector<DTOWorkSpaceNodeHistory> dataNodeHis = new Vector<DTOWorkSpaceNodeHistory>();

		try {

			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			where += " and iNodeId ="
					+ nodeId /*
								 * + " and workspacenodeofficehistory.iModifyBy="
								 * +uCode
								 */;
			where += "and  itranNo =(SELECT MAX(itranNo) FROM workspacenodeofficehistory where vworkspaceId = '" + wsId
					+ "'and inodeId =" + nodeId + ")";

			ResultSet rs = dataTable.getDataSet(con,
					"workspacenodeofficehistory.vWorkSpaceId,workspacenodeofficehistory.iNodeId,workspacenodeofficehistory.vFileName,"
							+ "workspacenodeofficehistory.vfilepath,workspacenodeofficehistory.vfiletype,workspacenodeofficehistory.cIsUpload,"
							+ "workspacenodeofficehistory.cIsDownload,workspacenodeofficehistory.iModifyBy,workspacenodeofficehistory.dModifyOn,"
							+ "workspacenodeofficehistory.cStatusIndi,workspacenodeofficehistory.dUploadOn,workspacenodeofficehistory.dDownloadOn,"
							+ "usermst.vUserName,vLoginName ",
					"workspacenodeofficehistory "
							+ "inner join usermst on usermst.iUserCode=workspacenodeofficehistory.imodifyby",
					where, "workspacenodeofficehistory.iTranNo desc");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFilePath(rs.getString("vFilePath"));
				dto.setClsUpload(rs.getString("cIsUpload").charAt(0));
				dto.setClsDownload(rs.getString("cIsDownload").charAt(0));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				dto.setdUploadOn(rs.getTimestamp("dUploadOn"));

				if (dto.getdUploadOn() != null) {
					if (countryCode.equalsIgnoreCase("IND")) {
						time = docMgmtImpl.TimeZoneConversion(dto.getdUploadOn(), locationName, countryCode);
						dto.setISTUploadOn(time.get(0));
					} else {
						time = docMgmtImpl.TimeZoneConversion(dto.getdUploadOn(), locationName, countryCode);
						dto.setISTUploadOn(time.get(0));
						dto.setESTUploadOn(time.get(1));
					}
				} else {
					dto.setISTUploadOn("No");
					dto.setESTUploadOn("No");
				}

				dto.setdDownloadOn(rs.getTimestamp("dDownloadOn"));
				if (dto.getdDownloadOn() != null) {
					if (countryCode.equalsIgnoreCase("IND")) {
						time = docMgmtImpl.TimeZoneConversion(dto.getdDownloadOn(), locationName, countryCode);
						dto.setISTDownloadOn(time.get(0));
					} else {
						time = docMgmtImpl.TimeZoneConversion(dto.getdDownloadOn(), locationName, countryCode);
						dto.setISTDownloadOn(time.get(0));
						dto.setESTDownloadOn(time.get(1));
					}
				} else {
					dto.setISTDownloadOn("No");
					dto.setESTDownloadOn("No");
				}

				dto.setUserName(rs.getString("vUserName"));
				dto.setLoginName(rs.getString("vLoginName"));
				dataNodeHis.add(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return dataNodeHis;
	}

	public Vector<DTOWorkSpaceNodeHistory> getWorkspaceNodeHistoryForOffice(String wsId, int nodeId) {
		Vector<DTOWorkSpaceNodeHistory> dataNodeHis = new Vector<DTOWorkSpaceNodeHistory>();

		try {

			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			where += " and iNodeId =" + nodeId + " ";
			// where+="and itranNo =(SELECT MAX(itranNo) FROM
			// workspaceNodeHistory where vworkspaceId = '"+wsId+"'and inodeId
			// ="+nodeId+")";

			ResultSet rs = dataTable.getDataSet(con,
					"workspacenodeofficehistory.iTranNo,workspacenodeofficehistory.vWorkSpaceId,workspacenodeofficehistory.iNodeId,workspacenodeofficehistory.vFileName,"
							+ "workspacenodeofficehistory.vfilepath,workspacenodeofficehistory.vfiletype,workspacenodeofficehistory.cIsUpload,"
							+ "workspacenodeofficehistory.cIsDownload,workspacenodeofficehistory.iModifyBy,workspacenodeofficehistory.dModifyOn,"
							+ "workspacenodeofficehistory.cStatusIndi,workspacenodeofficehistory.dUploadOn,workspacenodeofficehistory.dDownloadOn,"
							+ "usermst.vUserName,vLoginName ",
					"workspacenodeofficehistory "
							+ "inner join usermst on usermst.iUserCode=workspacenodeofficehistory.imodifyby",
					where, "");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setFileName(rs.getString("vFileName"));
				String path = rs.getString("vFilePath");
				path = path.replace("\\", "/");
				dto.setFilePath(path);
				dto.setFileType(rs.getString("vfiletype"));
				dto.setClsUpload(rs.getString("cIsUpload").charAt(0));
				dto.setClsDownload(rs.getString("cIsDownload").charAt(0));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				dto.setdUploadOn(rs.getTimestamp("dUploadOn"));

				if (dto.getdUploadOn() != null) {
					if (countryCode.equalsIgnoreCase("IND")) {
						time = docMgmtImpl.TimeZoneConversion(dto.getdUploadOn(), locationName, countryCode);
						dto.setISTUploadOn(time.get(0));
					} else {
						time = docMgmtImpl.TimeZoneConversion(dto.getdUploadOn(), locationName, countryCode);
						dto.setISTUploadOn(time.get(0));
						dto.setESTUploadOn(time.get(1));
					}
				} else {
					dto.setISTUploadOn("No");
					dto.setESTUploadOn("No");
				}

				dto.setdDownloadOn(rs.getTimestamp("dDownloadOn"));
				if (dto.getdDownloadOn() != null) {
					if (countryCode.equalsIgnoreCase("IND")) {
						time = docMgmtImpl.TimeZoneConversion(dto.getdDownloadOn(), locationName, countryCode);
						dto.setISTDownloadOn(time.get(0));
					} else {
						time = docMgmtImpl.TimeZoneConversion(dto.getdDownloadOn(), locationName, countryCode);
						dto.setISTDownloadOn(time.get(0));
						dto.setESTDownloadOn(time.get(1));
					}
				} else {
					dto.setISTDownloadOn("No");
					dto.setESTDownloadOn("No");
				}

				dto.setUserName(rs.getString("vUserName"));
				dto.setLoginName(rs.getString("vLoginName"));
				dataNodeHis.add(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return dataNodeHis;
	}

	public void insertsrcDocReminder(DTOWorkSpaceNodeHistory dto, int mode) {

		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Insert_srcDocReminder(?,?,?,?,?,?,?,?,?)}");
			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			proc.setInt(3, dto.getUserCode());
			proc.setInt(4, dto.getNodeId());
			proc.setString(5, dto.getFileName());
			proc.setString(6, dto.getFilePath());
			proc.setString(7, dto.getRemark());
			proc.setInt(8, dto.getModifyBy());
			// proc.setString(9, Character.toString(dto.getStatusIndi()));
			proc.setInt(9, mode);
			proc.execute();
			proc.close();

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getSrcDocReminderList(int userCode, int userGroupCode) {

		ArrayList<DTOWorkSpaceNodeHistory> data = new ArrayList<DTOWorkSpaceNodeHistory>();

		try {

			Connection con = ConnectionManager.ds.getConnection();
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			String where = " sdr.iuserCode = " + userCode
					+ " and sdr.cstatusindi<>'E' and workspacenodedetail.cstatusindi<>'D'";
			String fields = "sdr.vworkspaceid,workspacemst.vworkspacedesc,sdr.iNodeId,sdr.iUserCode,"
					+ "workspacenodedetail.vnodename,workspacenodedetail.vfoldername,sdr.vDocName,sdr.vpath,"
					+ "sdr.imodifyby,usermst.vusername,sdr.dmodifyon,sdr.cstatusindi as status";
			ResultSet rs = dataTable.getDataSet(con, fields,
					"srcDocReminder sdr inner join workspacemst on workspacemst.vWorkSpaceId= sdr.vWorkspaceId"
							+ " inner join usermst on usermst.iusercode= sdr.imodifyby left join workspacenodedetail on "
							+ "workspacenodedetail.vWorkSpaceId= sdr.vWorkspaceId and workspacenodedetail.inodeid= sdr.inodeid",
					where, "dModifyOn desc");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setUserCode(rs.getInt("iUserCode"));
				dto.setNodeName(rs.getString("vnodename"));
				dto.setFolderName(rs.getString("vfoldername"));
				dto.setFileName(rs.getString("vDocName"));
				String temp = dto.getFolderName();
				if (temp.endsWith(".docx")) {
					dto.getFolderName();
				} else {
					String srcFileExt = "docx";
					String newFileName;
					newFileName = temp + "." + srcFileExt;
					dto.setFolderName(newFileName);
				}

				String path = rs.getString("vPath");
				path = path.replace("\\", "/");
				dto.setFilePath(path);
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setUserName(rs.getString("vUsername"));
				;
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
				dto.setStatusIndi(rs.getString("status").charAt(0));
				data.add(dto);
				dto = null;
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void insertIntofileopenforsign(DTOWorkSpaceNodeHistory dto) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Insert_fileopenforsign(?,?,?,?,?,?,?,?,?)}");
			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			proc.setInt(3, dto.getTranNo());
			proc.setString(4, dto.getFileName());
			proc.setString(5, dto.getFolderName());
			proc.setString(6, dto.getRemark());
			proc.setInt(7, dto.getModifyBy());
			proc.setString(8, Character.toString(dto.getStatusIndi()));
			proc.setInt(9, dto.getMode());
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getfileopenforsignHistory(DTOWorkSpaceNodeHistory dto) {
		int tranNo = 0;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + dto.getWorkSpaceId() + "' And iNodeId=" + dto.getNodeId()
					+ " And iModifyBy=" + dto.getModifyBy();
			where += " And vFolderName = '" + dto.getFolderName() + "' And cStatusIndi<>'D'";
			ResultSet rs = dataTable.getDataSet(con, "iNodeId", "fileopenforsign", where, "");
			if (rs.next()) {
				tranNo = rs.getInt("iNodeId");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tranNo;
	}

	public int getfileopenforsignHistoryForSignOff(DTOWorkSpaceNodeHistory dto) {
		int tranNo = 0;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + dto.getWorkSpaceId() + "' And iNodeId=" + dto.getNodeId()
					+ " And iModifyBy=" + dto.getModifyBy();
			where += " And vFolderName = '" + dto.getFolderName() + "' And cStatusIndi<>'D'";
			ResultSet rs = dataTable.getDataSet(con, "iNodeId", "fileopenforsign", where, "");
			if (rs.next()) {
				tranNo = rs.getInt("iNodeId");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tranNo;
	}

	public Vector<DTOWorkSpaceNodeHistory> getNodeHistoryForPQSDocAutoSync() {
		Vector<DTOWorkSpaceNodeHistory> dt = new Vector<DTOWorkSpaceNodeHistory>();
		ResultSet rs = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			// String where = " vWorkspaceId = '" + wsId + "' ";
			// where += "and iNodeId =" + nodeId;
			// where+= " and iTranNo = (select max(iTranNo) from
			// PQSDocAutoSyncMst where vworkspaceid='" + wsId + "' and
			// inodeid="+nodeId+") ";
			rs = dataTable.getDataSet(con, "*", "PQSDocAutoSyncMst", "cStatusindi<>'E'", "");

			System.out.println("-----------------------test in while");
			while (rs.next()) {
				System.out.println("inside while---------------------");

				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFilePath(rs.getString("vFilePath"));

				String temp = rs.getString("vFileName");
				String srcFileExt = temp.substring(temp.lastIndexOf(".") + 1);
				dto.setFileExt(srcFileExt);

				dto.setFileStatus(rs.getString("vFileStatus"));
				dto.setRemark(rs.getString("vRemark"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dt.add(dto);
			}
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dt;
	}

	public void updatePQSDocAutoSyncMst(DTOWorkSpaceNodeHistory dto) {

		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Proc_PQSDocAutoSyncMstToUpdate(?,?,?)}");
			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			proc.setInt(3, dto.getTranNo());
			// proc.setInt(3, dto.getTranNo());
			// proc.setString(8,"test");
			// proc.setString(11, Character.toString(dto.getStatusIndi()));
			// proc.setInt(10, mode);
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<DTOWorkSpaceNodeHistory> getMaxNodeHistoryByTranNo(String wsId, int nodeId) {

		Vector<DTOWorkSpaceNodeHistory> nodeHistory = new Vector<DTOWorkSpaceNodeHistory>();
		ResultSet rs = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			where += "and iNodeId =" + nodeId;
			where += " and iTranNo = (select max(iTranNo) from workspacenodehistory where vworkspaceid='" + wsId
					+ "' and inodeid=" + nodeId + ") ";
			rs = dataTable.getDataSet(con, "*", "WorkspaceNodeHistory", where, "");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setBaseWorkFolder(rs.getString("vFolderName"));
				dto.setCoOrdinates(rs.getString("vCoordinates"));
				dto.setFileType(rs.getString("vFileType"));
				dto.setRoleCode(rs.getString("vRoleCode"));
				dto.setUuId(rs.getString("vDocId"));
				nodeHistory.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeHistory;
	}

	public void insertNodeHistoryWithCoordinates(DTOWorkSpaceNodeHistory dto) {
		String folderName = "";
		if (dto.getFolderName().equals("")) {
			folderName = "/" + dto.getWorkSpaceId() + "/" + dto.getNodeId() + "/" + dto.getTranNo();
		} else {
			folderName = dto.getFolderName();
		}
		System.out.println("Folder NBame(NodeId==>" + dto.getParentID());
		// here if the inodeid > total nodeid we get the parent nodeid
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con
					.prepareCall("{ Call Update_workspaceNodeHistoryForCoordinates(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			proc.setString(1, dto.getWorkSpaceId());
			proc.setInt(2, dto.getNodeId());
			proc.setInt(3, dto.getTranNo());
			proc.setString(4, dto.getFileName());
			proc.setString(5, dto.getFileType());
			proc.setString(6, folderName);
			proc.setString(7, Character.toString(dto.getRequiredFlag()));
			proc.setInt(8, dto.getStageId());
			proc.setString(9, dto.getRemark());
			proc.setInt(10, dto.getModifyBy());
			proc.setString(11, Character.toString(dto.getStatusIndi()));
			proc.setString(12, dto.getDefaultFileFormat());
			proc.setString(13, dto.getRoleCode());
			proc.setDouble(14, dto.getFileSize());
			proc.setString(15, dto.getCoOrdinates());
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<DTOWorkSpaceNodeHistory> getWorkspacenodeHistoryByUserId(String workspaceid, int nodeId, int uId)
			throws SQLException {

		Vector<DTOWorkSpaceNodeHistory> docHistory = new Vector<DTOWorkSpaceNodeHistory>();

		Connection con = ConnectionManager.ds.getConnection();

		String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " and iModifyby=" + uId
				+ " and iTranNo=" + "(select MAX(itranNo) FROM workspaceNodeHistory where vWorkspaceId = '"
				+ workspaceid + "' AND iNodeId = " + nodeId + " and iModifyby=" + uId + ")";
		ResultSet rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whr, "");

		while (rs.next()) {
			DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();

			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setTranNo(rs.getInt("iTranNo"));
			dto.setFileName(rs.getString("vFileName"));
			dto.setFolderName(rs.getString("vFolderName"));
			dto.setCoOrdinates(rs.getString("vCoordinates"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setFileType(rs.getString("vFileType"));
			docHistory.add(dto);
		}
		rs.close();
		con.close();
		return docHistory;
	}

	public Vector<DTOWorkSpaceNodeHistory> getWorkspacenodeHistoryByUserIdForFile(String workspaceid, int nodeId,
			int uId) throws SQLException {

		Vector<DTOWorkSpaceNodeHistory> docHistory = new Vector<DTOWorkSpaceNodeHistory>();

		Connection con = ConnectionManager.ds.getConnection();

		String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " and iModifyby=" + uId
				+ " and iTranNo=" + "(select MAX(itranNo) FROM workspaceNodeHistory where vWorkspaceId = '"
				+ workspaceid + "' AND iNodeId = " + nodeId + " " + "and iModifyby=" + uId
				+ ") and vfiletype='SR' and istageid<>0";
		ResultSet rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whr, "");

		while (rs.next()) {
			DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();

			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setTranNo(rs.getInt("iTranNo"));
			dto.setFileName(rs.getString("vFileName"));
			dto.setFolderName(rs.getString("vFolderName"));
			dto.setCoOrdinates(rs.getString("vCoordinates"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setFileType(rs.getString("vFileType"));
			docHistory.add(dto);
		}
		rs.close();
		con.close();
		return docHistory;
	}

	public Vector<DTOWorkSpaceNodeHistory> getFirstNodeHistoryByTranNo(String wsId, int nodeId) {

		Vector<DTOWorkSpaceNodeHistory> nodeHistory = new Vector<DTOWorkSpaceNodeHistory>();
		ResultSet rs = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			where += "and iNodeId =" + nodeId;
			// where+= " and iTranNo = (select max(iTranNo) from
			// workspacenodehistory where vworkspaceid='" + wsId + "' and
			// inodeid="+nodeId+") ";
			rs = dataTable.getDataSet(con, "*", "WorkspaceNodeHistory", where, "");

			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setBaseWorkFolder(rs.getString("vFolderName"));
				dto.setCoOrdinates(rs.getString("vCoordinates"));
				dto.setFileType(rs.getString("vFileType"));
				dto.setUuId(rs.getString("vDocId"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
				nodeHistory.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeHistory;
	}

	public Vector<DTOWorkSpaceNodeHistory> getMaxNodeHistoryByTranNo(String wsId, int nodeId, int tranNo) {

		Vector<DTOWorkSpaceNodeHistory> nodeHistory = new Vector<DTOWorkSpaceNodeHistory>();
		ResultSet rs = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			where += "and iNodeId =" + nodeId;
			where += " and iTranNo = " + tranNo;
			rs = dataTable.getDataSet(con, "*", "WorkspaceNodeHistory", where, "");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setBaseWorkFolder(rs.getString("vFolderName"));
				dto.setCoOrdinates(rs.getString("vCoordinates"));
				dto.setFileType(rs.getString("vFileType"));
				dto.setRoleCode(rs.getString("vRoleCode"));
				dto.setUuId(rs.getString("vDocId"));
				nodeHistory.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeHistory;
	}

	public Vector<DTOAssignNodeRights> getDataForNodeIdByTranNo(String wsId, int nodeId, int trnNo, int userCode) {
		Vector<DTOAssignNodeRights> data = new Vector<DTOAssignNodeRights>();
		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			String where = "vWorkSpaceId = '" + wsId + "' and iNodeId = " + nodeId + " and iTranNo = " + trnNo
					+ " and iModifyBy=" + userCode;
			rs = dataTable.getDataSet(con, "*", "assignNodeRights", where, "iModifyBy");
			while (rs.next()) {
				DTOAssignNodeRights dto = new DTOAssignNodeRights();
				dto.setWorkspaceId(rs.getString("vWorkSpaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setFlag(rs.getString("vFlag"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				data.addElement(dto);
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

		return data;
	}

	public Vector<DTOWorkSpaceNodeHistory> getFirstNodeHistoryByTranNoAndCountryCode(String wsId, int nodeId,
			String location, String cCode) {

		Vector<DTOWorkSpaceNodeHistory> nodeHistory = new Vector<DTOWorkSpaceNodeHistory>();
		ResultSet rs = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			where += "and iNodeId =" + nodeId;
			// where+= " and iTranNo = (select max(iTranNo) from
			// workspacenodehistory where vworkspaceid='" + wsId + "' and
			// inodeid="+nodeId+") ";
			rs = dataTable.getDataSet(con, "*", "WorkspaceNodeHistory", where, "");

			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			ArrayList<String> time = new ArrayList<String>();
			String locationName = location;
			String countryCode = cCode;
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setBaseWorkFolder(rs.getString("vFolderName"));
				dto.setCoOrdinates(rs.getString("vCoordinates"));
				dto.setFileType(rs.getString("vFileType"));
				dto.setUuId(rs.getString("vDocId"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
				nodeHistory.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeHistory;
	}

	public Vector<DTOWorkSpaceNodeHistory> getUserSignatureDetailByCountry(int userId, String location, String cCode) {

		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<String> time = new ArrayList<String>();
		// String locationName =
		// ActionContext.getContext().getSession().get("locationname").toString();
		// String countryCode =
		// ActionContext.getContext().getSession().get("countryCode").toString();
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		String basePath = knetProperties.getValue("signatureFile");
		ResultSet rs = null;
		try {

			Connection con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "*", "view_usersignmst",
					"TranNo = (Select max(itranno) from usersignmst where iUserId=" + userId + " and cStatusIndi<>'D')",
					"");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setTranNo(rs.getInt("TranNo"));
				dto.setFileName(rs.getString("FileName"));
				dto.setUserCode(rs.getInt("UserId"));
				dto.setFolderName(rs.getString("FilePath"));
				dto.setFileType(rs.getString("Style"));
				dto.setBaseWorkFolder(basePath);
				dto.setUserName(rs.getString("UserName"));
				dto.setModifyBy(rs.getInt("ModifyBy"));
				dto.setRemark(rs.getString("Remark"));
				dto.setModifyOn(rs.getTimestamp("ModifyOn"));
				dto.setUuId(rs.getString("SignId"));
				if (cCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), location, cCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), location, cCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
				dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
				data.add(dto);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public String getDocumentHashChainMst(String wsId, int nId, int tranNo, int uCode) {

		String data = null;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<String> time = new ArrayList<String>();
		// String locationName =
		// ActionContext.getContext().getSession().get("locationname").toString();
		// String countryCode =
		// ActionContext.getContext().getSession().get("countryCode").toString();
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		String basePath = knetProperties.getValue("signatureFile");
		ResultSet rs = null;
		try {

			Connection con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "*", "DocumentHashChainMst",
					"vworkspaceid='" + wsId + "' and inodeid=" + nId + " and itranNo=" + tranNo, "");
			while (rs.next()) {
				data = rs.getString("hash");
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void deleteTraceblilityMatrixDoc(String workspaceID, int nodeId, String Automated_Doc_Type) {

		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Proc_updateTraceblilityMatrixDoc(?,?,?)}");
			proc.setString(1, workspaceID);
			proc.setInt(2, nodeId);
			proc.setString(3, Automated_Doc_Type);
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getStageDescForESignature(int stageId) {
		String StageDesc = "";
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			String where = " vStageId = '" + stageId + "' ";
			rs = dataTable.getDataSet(con, "vStageDesc", "eSignatureMst", where, "");
			if (rs.next()) {
				StageDesc = rs.getString("vStageDesc");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return StageDesc;
	}

	public Vector<DTOWorkSpaceNodeHistory> getProjectSignOffHistoryForESignature(String workspaceid, int nodeId) {
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeHistory> nodeHistory = new Vector<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId
					+ " AND dModifyOn >Convert(datetime, '1900-01-01' ) and iCompletedStageId not in(30,50) ";

			rs = dataTable.getDataSet(con,
					"vWorkspaceId,iNodeId,itranno,iUserCode,vUserName,vLocationName,vCountryCode,vStageDesc,iCompletedStageId,vUserGroupName,dModifyOn,vFlag,vstyle,"
							+ "vFileName,vFilePath,vSignId,vRoleCode,vRoleName",
					"view_workspaceUserTrackingDetailForEsignature", whr, "dModifyOn");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				// dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("itranno"));
				dto.setUserCode(rs.getInt("iUserCode"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setCompletedStageId(rs.getInt("iCompletedStageId"));
				dto.setUserTypeName(rs.getString("vUserGroupName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setFileType(rs.getString("vFLag"));
				dto.setCountryCode(rs.getString("vCountryCode"));
				dto.setLocationName(rs.getString("vLocationName"));
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), dto.getLocationName(), dto.getCountryCode());
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
				dto.setStyle(rs.getString("vStyle"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFilePath(rs.getString("vFilePath"));
				dto.setSignId(rs.getString("vsignid"));
				dto.setRoleName(rs.getString("vRoleName"));
				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;

	}

	public String getNodeHistoryForUserCode(String wsId, int nId, int uCode) {

		String data = "false";
		String proceedFlag = "false";
		int stageId = 0;
		int tranNo = 0;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<String> time = new ArrayList<String>();
		// String locationName =
		// ActionContext.getContext().getSession().get("locationname").toString();
		// String countryCode =
		// ActionContext.getContext().getSession().get("countryCode").toString();
		ResultSet rs = null;

		String whereForTranCheck = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId + " and iStageId=0 ";

		try {
			Connection con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whereForTranCheck, "");
			while (rs.next()) {
				tranNo = rs.getInt("iTranNo");
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (tranNo != 0) {
			String whereForStageCheck = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId
					+ " and iTranNo = (select max(iTranNo) from workspacenodehistory where vworkspaceid='" + wsId + "' "
					+ "and inodeid=" + nId + " and iModifyBy=" + uCode + " and iTranNo>" + tranNo + ") "
					+ " and iModifyBy=" + uCode + " and iStageId<>0 ";

			try {
				Connection con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whereForStageCheck, "");
				while (rs.next()) {
					stageId = rs.getInt("iStageId");
				}
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
			}

			if (stageId == 30 || stageId == 50) {
				data = "true";
				return data;
			}

			if (stageId != 30 || stageId != 50) {

				String WhereCond = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId
						+ " and iTranNo = (select max(iTranNo) from workspacenodehistory where vworkspaceid='" + wsId
						+ "' " + "and inodeid=" + nId + " and iModifyBy=" + uCode + " and iTranNo>" + tranNo + ") "
						+ " and iModifyBy=" + uCode + " and iStageId<>0 and vcoordinates<>'' ";

				try {

					Connection con = ConnectionManager.ds.getConnection();
					rs = dataTable.getDataSet(con, "*", "workspacenodehistory", WhereCond, "");
					while (rs.next()) {
						data = "true";
					}

					rs.close();
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			whereForTranCheck = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId;
			try {
				Connection con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whereForTranCheck, "");
				while (rs.next()) {
					tranNo = rs.getInt("iTranNo");
				}
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
			}

			String whereForStageCheck = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId + " and iTranNo = "
					+ tranNo + " and iModifyBy=" + uCode + " and iStageId<>0 ";

			try {
				Connection con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whereForStageCheck, "");
				while (rs.next()) {
					stageId = rs.getInt("iStageId");
				}
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
			}

			if (stageId == 30 || stageId == 50) {
				data = "true";
				return data;
			}

			if (stageId != 30 || stageId != 50) {

				/*
				 * String WhereCond = " vWorkSpaceId = '"+ wsId+
				 * "' and iNodeId = "+nId + " and iTranNo = "+tranNo +
				 * " and iModifyBy="
				 * +uCode+" and iStageId<>0 and vcoordinates<>'' ";
				 */
				int creatorUserCode = 0;
				String WhereCond;
				try {
					creatorUserCode = getLastCreatorNodeHistory(wsId, nId);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				/*
				 * if(creatorUserCode == uCode){ uCode = creatorUserCode;
				 * WhereCond = " vWorkSpaceId = '"+ wsId+ "' and iNodeId = "+nId
				 * + " and iTranNo = "+tranNo+ " and iModifyBy="
				 * +uCode+" and iStageId<>0 and vcoordinates<>'' "; } else{
				 * WhereCond = " vWorkSpaceId = '"+ wsId+ "' and iNodeId = "+nId
				 * +
				 * " and itranNo=(select max(itranno) from workspacenodehistory "
				 * + "where vWorkspaceId = '"+wsId+"' AND iNodeId = "
				 * +nId+" and iModifyBy="
				 * +uCode+" ) and iStageId<>0 and vcoordinates<>'' "; }
				 */
				Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForEsignature = docMgmtImpl
						.getUserRightsDetailForEsignatureByUsercode(wsId, nId, uCode);
				int seqNo = getUserRightsDetailForEsignature.get(0).getSeqNo();

				if (seqNo == 1) {
					// if(creatorUserCode == uCode){
					// uCode = creatorUserCode;
					/*
					 * WhereCond = " vWorkSpaceId = '"+ wsId+
					 * "' and iNodeId = "+nId + " and iTranNo = "+tranNo+
					 * " and iModifyBy="
					 * +uCode+" and iStageId<>0 and vcoordinates<>'' ";
					 */
					WhereCond = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId
							+ " and itranNo=(select max(itranno) from workspacenodehistory " + "where vWorkspaceId = '"
							+ wsId + "' AND iNodeId = " + nId + " and iModifyBy=" + uCode
							+ " ) and iStageId<>0 and vcoordinates<>'' ";
					/*
					 * } else{ WhereCond = " vWorkSpaceId = '"+ wsId+
					 * "' and iNodeId = "+nId +
					 * " and itranNo=(select max(itranno) from workspacenodehistory "
					 * + "where vWorkspaceId = '"+wsId+"' AND iNodeId = "
					 * +nId+" and iModifyBy="
					 * +uCode+" ) and iStageId<>0 and vcoordinates<>'' "; }
					 */
				}

				else {
					if (creatorUserCode == uCode) {
						uCode = creatorUserCode;
						WhereCond = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId
								+ " and iTranNo = (select max(itranno) from workspacenodehistory "
								+ "where vWorkspaceId = '" + wsId + "' AND iNodeId = " + nId + " and iModifyBy=" + uCode
								+ " ) and iStageId<>0 and vcoordinates<>'' ";
					} else {
						WhereCond = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId
								+ " and itranNo=(select max(itranno) from workspacenodehistory "
								+ "where vWorkspaceId = '" + wsId + "' AND iNodeId = " + nId + " and iModifyBy=" + uCode
								+ " ) and iStageId<>0 and vcoordinates<>'' ";
					}
				}

				try {

					Connection con = ConnectionManager.ds.getConnection();
					rs = dataTable.getDataSet(con, "*", "workspacenodehistory", WhereCond, "");
					while (rs.next()) {
						data = "true";
					}

					rs.close();
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return data;

		/*
		 * 
		 * String data = "false" ; DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		 * ArrayList<String> time = new ArrayList<String>(); //String
		 * locationName =
		 * ActionContext.getContext().getSession().get("locationname").toString(
		 * ); //String countryCode =
		 * ActionContext.getContext().getSession().get("countryCode").toString()
		 * ; String WhereCond = " vWorkSpaceId = '"+ wsId+
		 * "' and iNodeId = "+nId +
		 * " and iTranNo = (select max(iTranNo) from workspacenodehistory where vworkspaceid='"
		 * + wsId + "' and inodeid="+nId+" and iModifyBy="+uCode+") " +
		 * " and iModifyBy="+uCode+" and iStageId<>0 and vcoordinates<>'' ";
		 * 
		 * 
		 * ResultSet rs = null; try {
		 * 
		 * Connection con = ConnectionManager.ds.getConnection(); rs =
		 * dataTable.getDataSet(con, "*","workspacenodehistory",WhereCond,"");
		 * while (rs.next()) { data = "true"; } rs.close(); con.close(); } catch
		 * (SQLException e) { e.printStackTrace(); } return data;
		 */}

	public ArrayList<DTOWorkSpaceNodeHistory> showFullNodeHistoryWithVoidFilesForESignature(String workspaceid,
			int nodeId) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " WorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " ";

			rs = dataTable.getDataSet(con, "*", "View_NodeSubmissionHistoryWithVoidHistoryForESignature", whr,
					"iTranNo");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("WorkspaceId"));
				// dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFileSize(rs.getInt("nSize"));

				String fileExt = dto.getFileName().substring(dto.getFileName().lastIndexOf(".") + 1);
				dto.setFileExt(fileExt);
				dto.setRoleName(rs.getString("vrolename"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vstagedesc"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setModifyBy(rs.getInt("imodifyby"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}

				dto.setRemark(rs.getString("vRemark"));
				dto.setRefFileName(rs.getString("vRefFileName"));
				dto.setRefFilePath(rs.getString("vRefFolderName"));

				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;
	}

	public String checkWSnodeHistory(String wsId) {
		String flag = "true";
		int nodeId;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";

			ResultSet rs = dataTable.getDataSet(con, "iNodeId", "WorkspaceNodeHistory", where, "");
			if (rs.next()) {
				nodeId = rs.getInt("iNodeId");
				flag = "false";
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> showFullNodeHistoryForESignatureByTranNo(String workspaceid, int nodeId,
			int tranNo) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " WorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " and itranno =" + tranNo;

			rs = dataTable.getDataSet(con, "*", "View_NodeSubmissionHistoryWithVoidHistoryForESignature", whr,
					"iTranNo");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("WorkspaceId"));
				// dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFileSize(rs.getInt("nSize"));

				String fileExt = dto.getFileName().substring(dto.getFileName().lastIndexOf(".") + 1);
				dto.setFileExt(fileExt);
				dto.setRoleName(rs.getString("vrolename"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vstagedesc"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setModifyBy(rs.getInt("imodifyby"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if (countryCode.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}

				dto.setRemark(rs.getString("vRemark"));

				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> showFullNodeHistoryForESignatureForUser(String workspaceid, int nodeId,
			int tranNo, String location, String country) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		/*
		 * String locationName =
		 * ActionContext.getContext().getSession().get("locationname").toString(
		 * ); String countryCode =
		 * ActionContext.getContext().getSession().get("countryCode").toString()
		 * ;
		 */

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			// String whr = " WorkspaceId = '" + workspaceid + "' AND iNodeId =
			// "+ nodeId +" and itranno ="+tranNo;
			String whr = " WorkSpaceId = '" + workspaceid + "' and iNodeId = " + nodeId
					+ " and iTranNo = (select max(iTranNo) from workspacenodehistory where vworkspaceid='" + workspaceid
					+ "' and inodeid=" + nodeId + " and iModifyBy=" + tranNo + ") " + " and iModifyBy=" + tranNo + " ";

			rs = dataTable.getDataSet(con, "*", "View_NodeSubmissionHistoryWithVoidHistoryForESignature", whr,
					"iTranNo");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("WorkspaceId"));
				// dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFileSize(rs.getInt("nSize"));

				String fileExt = dto.getFileName().substring(dto.getFileName().lastIndexOf(".") + 1);
				dto.setFileExt(fileExt);
				dto.setRoleName(rs.getString("vrolename"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vstagedesc"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setModifyBy(rs.getInt("imodifyby"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if (country.equalsIgnoreCase("IND")) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), location, country);
					dto.setISTDateTime(time.get(0));
				} else {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), location, country);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}

				dto.setRemark(rs.getString("vRemark"));

				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;
	}

	public int getTranNoForSign(String wsId, int nId, String docId) {
		int tranNo = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' and inodeid=" + nId + " and vdocid='" + docId + "'";

			ResultSet rs = dataTable.getDataSet(con, "itranNo", "WorkspaceNodeHistory", where, "");
			if (rs.next()) {
				tranNo = rs.getInt("itranNo");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tranNo;
	}

	public String getDocIdForSign(String wsId, int nId, int tranNo) {
		String docId = "";
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' and inodeid=" + nId + " and itranNo='" + tranNo + "'";

			ResultSet rs = dataTable.getDataSet(con, "vDocId", "WorkspaceNodeHistory", where, "");
			if (rs.next()) {
				docId = rs.getString("vDocId");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return docId;
	}

	public Vector<DTOWorkSpaceNodeHistory> getLeafNode(String wsId) {
		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		String baseworkpath = knetProperties.getValue("BaseWorkFolder");
		float sum = 0;

		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = "wnd.vWorkspaceId = '" + wsId
					+ "' AND ((wnh.iStageId = 10 AND wnh.vFileName<> 'No File') OR wnh.vFolderName IS NULL)";
			String fields = "wnd.vWorkSpaceId,wsmst.vWorkSpaceDesc, wnd.iNodeId,wnh.vFolderName AS filepath, wnh.vFileName AS filename ,wnh.iStageId , "
					+ "wnd.vNodeName, wnd.vNodeDisplayName, wnd.vFolderName, wnh.nSize";
			String query = "Proc_getProjectTrackingeCSVChildNodesHours('" + wsId + "') "
					+ "AS wnd INNER JOIN workspacemst AS wsmst ON  wsmst.vWorkSpaceId = wnd.vWorkSpaceId"
					+ " Left JOIN workspacenodehistory AS wnh ON  wnh.vWorkSpaceId = wnd.vWorkSpaceId AND wnh.iNodeId = wnd.iNodeId ";
			ResultSet rs = dataTable.getDataSet(con, fields, query, where, "");
			while (rs.next()) {
				float size = 0;

				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dto.setFilePath(rs.getString("filepath"));
				dto.setFileName(rs.getString("filename"));
				float srcDocfileSize = getsourceDocSize(dto.getWorkSpaceId(), dto.getNodeID());
				dto.setnSize(rs.getFloat("nSize") + srcDocfileSize);
				/* System.out.println(dto.getnSize()); */
				Formatter formatter1 = new Formatter();
				formatter1.format("%.2f", dto.getnSize());
				dto.setnSize(Float.parseFloat(formatter1.toString()));
				System.out.println(dto.getnSize());
				if (dto.getFilePath() != null) {
					System.out.println(dto.getFilePath());
					File file = new File(baseworkpath + "/" + dto.getFilePath());
					String[] fileList = file.list();
					for (String name : fileList) {
						if (name.endsWith("_Sign.pdf")) {
							System.out.println(name);
							File path = new File(baseworkpath + "/" + dto.getFilePath() + "/" + name);
							System.out.println(path);
							/*
							 * File path = new File(
							 * "D:/FileServerDoc/workspace/2022/9/8/0210/162/20/User-Requirement-Specification_Sign.pdf"
							 * );
							 */
							float fileSize = getTotalFileSize(path);
							Formatter formatter = new Formatter();
							size = (float) fileSize / (1024 * 1024);
							formatter.format("%.2f", size);
							size = Float.parseFloat(formatter.toString());
						}
					}
				}
				dto.seteSignfileSize(size);
				dto.setTotalFileSize(dto.getnSize() + dto.geteSignfileSize());
				sum = (dto.getTotalFileSize() + sum);
				dto.setSumofTotalSize(sum);

				data.add(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public float getsourceDocSize(String wsId, int nId) {
		float sourceDocSize = 0;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' and inodeid=" + nId
					+ " AND iDocTranNo = (Select MAX(iDocTranNo) From WorkspaceNodeDocHistory"
					+ " Where  vWorkspaceId = '" + wsId + "' AND iNodeId = " + nId + ")";

			ResultSet rs = dataTable.getDataSet(con, "nSize", "WorkspaceNodeDocHistory", where, "");
			while (rs.next()) {

				sourceDocSize = rs.getFloat("nSize");
				System.out.println(nId);
				System.out.println(sourceDocSize);
				Formatter formatter = new Formatter();
				formatter.format("%.2f", sourceDocSize);
				sourceDocSize = Float.parseFloat(formatter.toString());

			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sourceDocSize;
	}

	public long getTotalFileSize(File folder) {
		long length = 0;

		// listFiles() is used to list the
		// contents of the given folder

		/* File[] files = folder.listFiles(); */

		length = folder.length();

		// loop for traversing the directory
		/*
		 * for (int i = 0; i < count; i++) { if (files[i].isFile()) { length +=
		 * files[i].length(); } else { length += getTotalFileSize(files[i]); } }
		 */
		return length;
	}

	public float getPdfpublishSize(String wsId) {

		float pdfPublishDocSize = 0;
		String path;
		float size = 0;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkSpaceId = '" + wsId + "'";

			ResultSet rs = dataTable.getDataSet(con, "vPublishPath", "SubmissionInfoHTML", where, "");
			while (rs.next()) {

				path = rs.getString("vPublishPath");
				System.out.println(path);
				for (int i = 0; i < 1; i++) {

					File file1 = new File(path);

					size = getFolderSize(file1);
					size = (float) size / (1024 * 1024);

					Formatter formatter = new Formatter();
					formatter.format("%.2f", size);
					size = Float.parseFloat(formatter.toString());
					pdfPublishDocSize += size;
					size = 0;

				}

			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pdfPublishDocSize;
	}

	public long getFolderSize(File folder) {
		long length = 0;

		// listFiles() is used to list the
		// contents of the given folder
		File[] files = folder.listFiles();

		int count = files.length;

		// loop for traversing the directory
		for (int i = 0; i < count; i++) {
			if (files[i].isFile()) {
				length += files[i].length();
			} else {
				length += getFolderSize(files[i]);
			}
		}
		return length;
	}

	public Vector<DTOWorkSpaceNodeHistory> getNodeHistoryForESignature(String workspaceid, int nodeId) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeHistory> nodeHistory = new Vector<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId;

			/*
			 * rs = dataTable.getDataSet(con,
			 * "vWorkspaceId,iNodeId,wsFileName,vUserName,vVoidBy,vStageDesc,vRemark,iCompletedStageId,vCompetedStageDesc,vUserGroupName,dModifyOn,vRoleName",
			 * "view_workspaceUserTrackingDetailForEsignature", whr,
			 * "dmodifyon desc");
			 */
			rs = dataTable.getDataSet(con, "*", "view_SignOffHistoryForEsignature", whr, "");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				// dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setFileName(rs.getString("vfilename"));
				dto.setFolderName(rs.getString("vFoldername"));
				dto.setUserName(rs.getString("vusername"));
				// dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setStageDesc(rs.getString("vstagedesc"));
				// dto.setCompletedStageId(rs.getInt("iCompletedStageId"));
				// dto.setUserTypeName(rs.getString("vUserGroupName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if (dto.getModifyOn() != null) {
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
				dto.setRoleName(rs.getString("vRoleName"));
				dto.setRemark(rs.getString("vRemark"));
				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;

		/*
		 * DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		 * Vector<DTOWorkSpaceNodeHistory> nodeHistory = new
		 * Vector<DTOWorkSpaceNodeHistory>(); ArrayList<String> time = new
		 * ArrayList<String>(); String locationName =
		 * ActionContext.getContext().getSession().get("locationname").toString(
		 * ); String countryCode =
		 * ActionContext.getContext().getSession().get("countryCode").toString()
		 * ;
		 * 
		 * try { Connection con = ConnectionManager.ds.getConnection();
		 * ResultSet rs = null;
		 * 
		 * String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = "+
		 * nodeId;
		 * 
		 * rs = dataTable.getDataSet(con,
		 * "vWorkspaceId,iNodeId,wsFileName,vUserName,vVoidBy,vStageDesc,vRemark,iCompletedStageId,vCompetedStageDesc,vUserGroupName,dModifyOn,vRoleName",
		 * "view_workspaceUserTrackingDetailForEsignature", whr,
		 * "dmodifyon desc"); rs = dataTable.getDataSet(con,
		 * "vWorkspaceId,iNodeId,wsFileName,vUserName,vVoidBy,vStageDesc,vRemark,iCompletedStageId,vCompetedStageDesc,vUserGroupName,dModifyOn,vRoleName",
		 * "view_workspaceUserTrackingDetailForEsignature", whr,
		 * "dmodifyon desc"); //rs = dataTable.getDataSet(con,
		 * "*","view_SignOffHistoryForEsignature",whr, "");
		 * 
		 * while (rs.next()) {
		 * 
		 * DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		 * dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
		 * //dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
		 * dto.setNodeId(rs.getInt("iNodeId"));
		 * dto.setFileName(rs.getString("wsFileName"));
		 * dto.setUserName(rs.getString("vUserName"));
		 * //dto.setStageDesc(rs.getString("vStageDesc"));
		 * dto.setStageDesc(rs.getString("vCompetedStageDesc"));
		 * dto.setCompletedStageId(rs.getInt("iCompletedStageId"));
		 * dto.setUserTypeName(rs.getString("vUserGroupName"));
		 * dto.setModifyOn(rs.getTimestamp("dModifyOn")); time =
		 * docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,
		 * countryCode); dto.setISTDateTime(time.get(0));
		 * dto.setESTDateTime(time.get(1));
		 * dto.setRoleName(rs.getString("vRoleName"));
		 * dto.setRemark(rs.getString("vRemark")); nodeHistory.add(dto);
		 * 
		 * 
		 * DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		 * dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
		 * //dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
		 * dto.setNodeId(rs.getInt("iNodeId"));
		 * dto.setFileName(rs.getString("vfilename"));
		 * dto.setFolderName(rs.getString("vFoldername"));
		 * dto.setUserName(rs.getString("vusername"));
		 * //dto.setStageDesc(rs.getString("vStageDesc"));
		 * dto.setStageDesc(rs.getString("vstagedesc"));
		 * //dto.setCompletedStageId(rs.getInt("iCompletedStageId"));
		 * //dto.setUserTypeName(rs.getString("vUserGroupName"));
		 * dto.setModifyOn(rs.getTimestamp("dModifyOn"));
		 * if(dto.getModifyOn()!=null){ time =
		 * docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,
		 * countryCode); dto.setISTDateTime(time.get(0));
		 * dto.setESTDateTime(time.get(1)); }
		 * dto.setRoleName(rs.getString("vRoleName"));
		 * dto.setRemark(rs.getString("vRemark")); nodeHistory.add(dto); }
		 * 
		 * rs.close(); con.close();
		 * 
		 * } catch (SQLException e) { e.printStackTrace(); }
		 * 
		 * return nodeHistory;
		 * 
		 */}

	public Vector<DTOWorkSpaceNodeHistory> getReferenceHistoryForESignature(String workspaceid, int nodeId) {
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeHistory> nodeHistory = new Vector<DTOWorkSpaceNodeHistory>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " wna.vWorkspaceId = '" + workspaceid + "' AND wna.iNodeId = " + nodeId;

			rs = dataTable.getDataSet(con,
					"distinct wna.vworkspaceid as wsid,wna.inodeid as nid,wna.vreffilename,wna.vreffoldername,"
							+ "wna.vremark,um.vusername,rm.vrolename,wna.dmodifyon,wna.iRefTranNo,wna.itranno   ",
					"workspacenodeAttachment as wna inner join usermst as um on wna.imodifyby = um.iusercode inner join "
							+ "WorkspaceNodeHistory as wnh on wna.vworkspaceid = wnh.vworkspaceid "
							+ "and wna.inodeid = wnh.inodeid and wna.ireftranno = wnh.itranno "
							+ "inner join rolemst as rm on wnh.vrolecode = rm.vrolecode "
							+ "and wnh.vrolecode = rm.vrolecode",
					whr, "wna.dmodifyon desc");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("wsid"));
				// dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeId(rs.getInt("nid"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setFileName(rs.getString("vRefFileName"));
				dto.setRefFilePath(rs.getString("vRefFolderName"));
				dto.setRoleName(rs.getString("vRoleName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(), locationName, countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;

	}

	public Vector<DTOWorkSpaceNodeHistory> getnodeId(String WorkspaceId, int userCode, int userGrpCode) {
		// String wDesc=null;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();

		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			String fields = " distinct wnd.NodeId";
			String whr = "wnd.WorkspaceId='" + WorkspaceId + "'  and (wnd.usercode=" + userCode
					+ " and wnd.usergroupcode=" + userGrpCode + " and"
					+ " wnd.Cloneflag='y'  and statusIndi<>'D' or wnh.istageid=10 and wnh.iModifyBy=" + userCode + " )";
			String Table = "View_WorkSpaceNodeRightsDetail_WSListForESignature as wnd inner join workspacenodehistory as"
					+ " wnh on wnd.workspaceid=wnh.vworkspaceid and wnd.nodeid=wnh.inodeid";

			rs = dataTable.getDataSet(con, fields, Table, whr, "wnd.NodeId DESC");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();

				dto.setNodeId(rs.getInt("NodeId"));
				data.add(dto);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public int getTranNoFromNodeId(String wsId, int nId, int uCode) {

		Vector<DTOWorkSpaceNodeHistory> data = new Vector<DTOWorkSpaceNodeHistory>();
		int Tranno = 0;
		// String locationName =
		// ActionContext.getContext().getSession().get("locationname").toString();
		// String countryCode =
		// ActionContext.getContext().getSession().get("countryCode").toString();
		String fields = " MAX(wnh.iTranNo) as iTranno";
		String WhereCond = "wsurm.vWorkSpaceId='" + wsId + "' AND wnh.iNodeId = " + nId + " AND wsurm.iUserCode = "
				+ uCode + "  ";
		String Table = "workspacenodehistory AS wnh INNER JOIN WorkspaceUserRightsMstForESignature wsurm ON wsurm.vWorkSpaceId = wnh.vWorkSpaceId";

		ResultSet rs = null;
		try {

			Connection con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, fields, Table, WhereCond, "");
			while (rs.next()) {
				Tranno = rs.getInt("iTranNo");
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Tranno;
	}

	public DTOWorkSpaceNodeHistory eSignPendingforyesSeq(String WorkspaceId, int userCode, int tranno) {
		// String wDesc=null;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		DTOWorkSpaceNodeHistory data = new DTOWorkSpaceNodeHistory();

		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			String fields = "wsurm.vWorkSpaceId,wsm.vWorkSpaceDesc,wsurm.iNodeId,wsnh.iTranNo"
					+ ",wsnh.vFileName,wnd.vfoldername,wsnh.iStageId,wsm.vBaseWorkFolder,wsnh.vCoordinates";
			String whr = "wsnh.vWorkSpaceId = '" + WorkspaceId + "' AND wsurm.iUserCode = " + userCode
					+ " AND iTranNo = " + tranno + " AND  " + "wsnh.vCoordinates IS NULL AND wsurm.cStatusIndi<>'D'";
			String Table = "dbo.WorkspaceUserRightsMstForESignature wsurm INNER JOIN dbo.workspacenodehistory AS wsnh ON "
					+ "wsurm.vWorkSpaceId = wsnh.vWorkSpaceId AND " + "wsurm.iNodeId = wsnh.iNodeId "
					+ "INNER JOIN dbo.workspacenodedetail AS wnd on wsurm.vWorkSpaceId = wnd.vWorkSpaceId AND wsurm.iNodeId = wnd.iNodeId "
					+ "INNER JOIN dbo.workspacemst AS wsm " + "ON wsm.vWorkSpaceId = wsurm.vWorkSpaceId";

			rs = dataTable.getDataSet(con, fields, Table, whr, "");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));

				data = dto;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public DTOWorkSpaceNodeHistory checkeSign(String WorkspaceId, int userCode, int nodeId, int seqno) {
		// String wDesc=null;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		DTOWorkSpaceNodeHistory data = new DTOWorkSpaceNodeHistory();
		Connection con = null;

		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			String fields = "*";
			String whr = "wnh.vWorkspaceId = '" + WorkspaceId + "' and wnh.iNodeId =" + nodeId + " AND urs.iUserCode = "
					+ userCode + " AND "
					+ "wnh.iTranNo = (select max(iTranNo) from workspacenodehistory where vworkspaceid='" + WorkspaceId
					+ "' and " + "inodeid=" + nodeId + " and iModifyBy=" + userCode + ") AND urs.iseqNo = " + seqno
					+ " AND wnh.vCoordinates IS NULL";
			String Table = "view_userRightsDetailForESignature AS urs INNER JOIN dbo.workspacenodehistory AS wnh "
					+ "ON wnh.vWorkSpaceId = urs.vWorkSpaceId AND wnh.iNodeId = urs.iNodeId";

			rs = dataTable.getDataSet(con, fields, Table, whr, "");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				data = dto;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public DTOWorkSpaceNodeHistory Esigndatafornoseq(String WorkspaceId, int userCode, int nodeid) {
		// String wDesc=null;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		DTOWorkSpaceNodeHistory data = new DTOWorkSpaceNodeHistory();

		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			String fields = "urs.vWorkSpaceId,urs.iNodeId,wnh.iTranNo,wsmst.vWorkSpaceDesc,urs.vUserName,urs.iUserCode,wnh.vFileName,"
					+ "wnd.vfoldername,wsmst.vBaseWorkFolder,urs.iseqNo,wnh.vCoordinates";
			String whr = "urs.vWorkspaceId = '" + WorkspaceId + "' and urs.iNodeId =" + nodeid + " AND urs.iUserCode = "
					+ userCode + "";
			String Table = "view_userRightsDetailForESignature AS urs INNER JOIN dbo.workspacenodehistory AS wnh ON wnh.vWorkSpaceId "
					+ "= urs.vWorkSpaceId AND wnh.iNodeId = urs.iNodeId INNER JOIN dbo.workspacemst AS wsmst ON "
					+ "wnh.vWorkSpaceId = wsmst.vWorkSpaceId INNER JOIN dbo.workspacenodedetail AS wnd on "
					+ "wnh.vWorkSpaceId = wnd.vWorkSpaceId AND wnh.iNodeId = wnd.iNodeId";

			rs = dataTable.getDataSet(con, fields, Table, whr, "");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));

				data = dto;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public boolean flagCheckforData(String WorkspaceId, int userCode, int nodeid) {
		// String wDesc=null;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		DTOWorkSpaceNodeHistory data = new DTOWorkSpaceNodeHistory();
		boolean flag = false;
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			String fields = " DISTINCT wsurm.vWorkSpaceId,wsm.vWorkSpaceDesc,wsurm.iNodeId,wsnh.iTranNo,wsnh.vFileName,wnd.vfoldername,wsnh.iModifyBy,wsnh.iStageId,wsm.vBaseWorkFolder,wsnh.vCoordinates  ";
			String whr = "wsnh.vWorkSpaceId ='" + WorkspaceId + "' AND wsurm.iNodeId = " + nodeid
					+ " AND wsnh.iTranNo = (SELECT MAX(iTranNO) FROM dbo.workspacenodehistory" + " WHERE vWorkSpaceId='"
					+ WorkspaceId + "' AND iNodeId=" + nodeid + " AND iStageId=10)" + " AND wsnh.iModifyBy = "
					+ userCode
					+ " AND  wsnh.vCoordinates IS NOT NULL AND wsnh.iStageId not in (30,50) AND wsurm.cStatusIndi<>'D'";
			String Table = "dbo.WorkspaceUserRightsMstForESignature wsurm "
					+ "Left JOIN dbo.workspacenodehistory AS wsnh ON wsurm.vWorkSpaceId = wsnh.vWorkSpaceId AND wsurm.iNodeId = wsnh.iNodeId "
					+ "INNER JOIN dbo.workspacemst AS wsm ON wsm.vWorkSpaceId = wsurm.vWorkSpaceId "
					+ "INNER JOIN dbo.workspacenodedetail AS wnd on wsm.vWorkSpaceId = wnd.vWorkSpaceId AND wsurm.iNodeId = wnd.iNodeId";

			rs = dataTable.getDataSet(con, fields, Table, whr, "");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				flag = true;
				data = dto;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public int checkPrevUserForeSign(String WorkspaceId, int nodeId, int seqno) {
		// String wDesc=null;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		int data = 0;

		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			String fields = "iusercode";
			String whr = "vWorkspaceId = '" + WorkspaceId + "' and iNodeId =" + nodeId + " and iseqno=" + seqno;
			String Table = "view_userRightsDetailForESignature";

			rs = dataTable.getDataSet(con, fields, Table, whr, "");

			while (rs.next()) {
				data = rs.getInt("iusercode");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public DTOWorkSpaceNodeHistory checkeSignForCurrentUser(String WorkspaceId, int userCode, int nodeId, int seqno) {
		// String wDesc=null;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		DTOWorkSpaceNodeHistory data = new DTOWorkSpaceNodeHistory();
		Connection con = null;

		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			String fields = "*";
			String whr = "wnh.vWorkspaceId = '" + WorkspaceId + "' and wnh.iNodeId =" + nodeId + " AND urs.iUserCode = "
					+ userCode + " " + " AND urs.iseqNo = " + seqno + " AND wnh.vCoordinates IS NULL";
			String Table = "view_userRightsDetailForESignature AS urs INNER JOIN dbo.workspacenodehistory AS wnh "
					+ "ON wnh.vWorkSpaceId = urs.vWorkSpaceId AND wnh.iNodeId = urs.iNodeId";

			rs = dataTable.getDataSet(con, fields, Table, whr, "");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				data = dto;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public DTOWorkSpaceNodeHistory checkeSignData(String WorkspaceId, int nodeid, int userCode) {
		// String wDesc=null;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		DTOWorkSpaceNodeHistory data = new DTOWorkSpaceNodeHistory();
		boolean flag = false;
		Connection con = null;
		ResultSet rs = null;

		try {
			con = ConnectionManager.ds.getConnection();
			String fields = "urs.vworkspaceid,urs.vworkspacedesc,urs.inodeid,wnh.itranno,urs.istageid,wnh.vfilename,urs.vbaseworkfolder,wnd.vfoldername";
			String whr = "wnh.vWorkSpaceId ='" + WorkspaceId + "' AND wnh.iNodeId = " + nodeid
					+ " and wnh.vCoordinates IS NULL and " + " wnh.iStageId not in (30,50) AND urs.iusercode="
					+ userCode + "" + " and wnh.cStatusIndi<>'D'";
			String Table = "view_userRightsDetailForESignature AS urs INNER JOIN dbo.workspacenodehistory AS wnh ON "
					+ "wnh.vWorkSpaceId = urs.vWorkSpaceId AND wnh.iNodeId = urs.iNodeId INNER JOIN dbo.workspacenodedetail AS wnd "
					+ "on wnh.vWorkSpaceId = wnd.vWorkSpaceId AND wnh.iNodeId = wnd.iNodeId";

			rs = dataTable.getDataSet(con, fields, Table, whr, "");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				flag = true;
				data = dto;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> showNodeHistoryForESignature(String workspaceid, int userCode,
			int nodeId) {
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' and iModifyBy= " + userCode + " and iNodeId = " + nodeId
					+ " and istageId in (20,50,100) "
					+ " and iTranNo=(select MAX(iTranNo) from workspacenodehistory where vWorkSpaceId = '" + workspaceid
					+ "' and iNodeId = " + nodeId + "and iModifyBy= " + userCode + ")";

			rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whr, "");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;
	}

	public String getNodeHistoryForUserCodeForSendBack(String wsId, int nId, int uCode) {

		String data = "false";
		String proceedFlag = "false";
		int stageId = 0;
		int tranNo = 0;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<String> time = new ArrayList<String>();
		// String locationName =
		// ActionContext.getContext().getSession().get("locationname").toString();
		// String countryCode =
		// ActionContext.getContext().getSession().get("countryCode").toString();
		ResultSet rs = null;

		String whereForTranCheck = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId + " and iStageId=0 ";

		try {
			Connection con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whereForTranCheck, "");
			while (rs.next()) {
				tranNo = rs.getInt("iTranNo");
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (tranNo != 0) {
			String whereForStageCheck = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId
					+ " and iTranNo = (select max(iTranNo) from workspacenodehistory where vworkspaceid='" + wsId + "' "
					+ "and inodeid=" + nId + " and iModifyBy=" + uCode + " and iTranNo>" + tranNo + ") "
					+ " and iModifyBy=" + uCode + " and iStageId<>0 ";
			/*
			 * String whereForStageCheck = " vWorkSpaceId = '"+ wsId+
			 * "' and iNodeId = "+nId +
			 * " and iTranNo = "+tranNo+" and iModifyBy="
			 * +uCode+" and iStageId<>0 ";
			 */

			try {
				Connection con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whereForStageCheck, "");
				while (rs.next()) {
					stageId = rs.getInt("iStageId");
				}
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
			}

			if (stageId == 30 || stageId == 50) {
				data = "true";
				return data;
			}

			if (stageId != 30 || stageId != 50) {

				String WhereCond = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId
						+ " and iTranNo = (select max(iTranNo) from workspacenodehistory where vworkspaceid='" + wsId
						+ "' " + "and inodeid=" + nId + " and iModifyBy=" + uCode + " and iTranNo>" + tranNo + ") "
						+ " and iModifyBy=" + uCode + " and iStageId<>0 and vcoordinates<>'' ";
				/*
				 * String WhereCond = " vWorkSpaceId = '"+ wsId+
				 * "' and iNodeId = "+nId + " and iTranNo = "+tranNo+
				 * " and iModifyBy="
				 * +uCode+" and iStageId<>0 and vcoordinates<>'' ";
				 */

				try {

					Connection con = ConnectionManager.ds.getConnection();
					rs = dataTable.getDataSet(con, "*", "workspacenodehistory", WhereCond, "");
					while (rs.next()) {
						data = "true";
					}

					rs.close();
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			whereForTranCheck = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId;

			try {
				Connection con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whereForTranCheck, "");
				while (rs.next()) {
					tranNo = rs.getInt("iTranNo");
				}
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			/*
			 * String whereForStageCheck = " vWorkSpaceId = '"+ wsId+
			 * "' and iNodeId = "+nId +
			 * " and iTranNo = (select max(iTranNo) from workspacenodehistory where vworkspaceid='"
			 * + wsId + "' and inodeid="+nId+" and iModifyBy="+uCode+") " +
			 * " and iModifyBy="+uCode+" and iStageId<>0 ";
			 */

			// tranNo=tranNo+1;
			String whereForStageCheck = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId + " and iTranNo = "
					+ tranNo + " and iModifyBy=" + uCode + " and iStageId<>0 ";

			try {
				Connection con = ConnectionManager.ds.getConnection();
				rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whereForStageCheck, "");
				while (rs.next()) {
					stageId = rs.getInt("iStageId");
				}
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
			}

			if (stageId == 30 || stageId == 50) {
				data = "true";
				return data;
			}

			if (stageId != 30 || stageId != 50) {

				/*
				 * String WhereCond = " vWorkSpaceId = '"+ wsId+
				 * "' and iNodeId = "+nId +
				 * " and iTranNo = (select max(iTranNo) from workspacenodehistory where vworkspaceid='"
				 * + wsId + "' and inodeid="+nId+" and iModifyBy="+uCode+") " +
				 * " and iModifyBy="
				 * +uCode+" and iStageId<>0 and vcoordinates<>'' ";
				 */
				int creatorUserCode = 0;
				String WhereCond;
				try {
					creatorUserCode = getLastCreatorNodeHistory(wsId, nId);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForEsignature = docMgmtImpl
						.getUserRightsDetailForEsignatureByUsercode(wsId, nId, uCode);
				int seqNo = getUserRightsDetailForEsignature.get(0).getSeqNo();

				if (seqNo == 1) {
					// if(creatorUserCode == uCode){
					// uCode = creatorUserCode;
					/*
					 * WhereCond = " vWorkSpaceId = '"+ wsId+
					 * "' and iNodeId = "+nId + " and iTranNo = "+tranNo+
					 * " and iModifyBy="
					 * +uCode+" and iStageId<>0 and vcoordinates<>'' ";
					 */
					WhereCond = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId
							+ " and itranNo=(select max(itranno) from workspacenodehistory " + "where vWorkspaceId = '"
							+ wsId + "' AND iNodeId = " + nId + " and iModifyBy=" + uCode
							+ " ) and iStageId<>0 and vcoordinates<>'' ";
					/*
					 * } else{ WhereCond = " vWorkSpaceId = '"+ wsId+
					 * "' and iNodeId = "+nId +
					 * " and itranNo=(select max(itranno) from workspacenodehistory "
					 * + "where vWorkspaceId = '"+wsId+"' AND iNodeId = "
					 * +nId+" and iModifyBy="
					 * +uCode+" ) and iStageId<>0 and vcoordinates<>'' "; }
					 */
				}

				else {
					if (creatorUserCode == uCode) {
						uCode = creatorUserCode;
						WhereCond = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId + " and iTranNo = " + tranNo
								+ " and iModifyBy=" + uCode + " and iStageId<>0 and vcoordinates<>'' ";
					} else {
						WhereCond = " vWorkSpaceId = '" + wsId + "' and iNodeId = " + nId
								+ " and itranNo=(select max(itranno) from workspacenodehistory "
								+ "where vWorkspaceId = '" + wsId + "' AND iNodeId = " + nId + " and iModifyBy=" + uCode
								+ " ) and iStageId<>0 and vcoordinates<>'' ";
					}
				}

				try {

					Connection con = ConnectionManager.ds.getConnection();
					rs = dataTable.getDataSet(con, "*", "workspacenodehistory", WhereCond, "");
					while (rs.next()) {
						data = "true";
					}

					rs.close();
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return data;
	}

	public int getCertifiedFlag(String wsId, int nodeId) {
		int tranNo = -1;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			if (nodeId > 0)
				where += " and iNodeId =" + nodeId + " AND iStageId=100";
			ResultSet rs = dataTable.getDataSet(con, "max(iTranNo) as iTranNo", "WorkspaceNodeHistory", where, "");
			if (rs.next()) {
				tranNo = rs.getInt("iTranNo");
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tranNo;
	}

	public ArrayList<DTOWorkSpaceNodeHistory> getCertifiedNodeHistory(String workspaceid, int nodeId, int tranNo) {
		ArrayList<DTOWorkSpaceNodeHistory> nodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String whr = " vWorkspaceId = '" + workspaceid + "' AND iNodeId = " + nodeId + " AND iTranNo = " + tranNo;

			rs = dataTable.getDataSet(con, "*", "View_NodeSubmissionHistoryBySequecenumber", whr, "");

			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
				dto.setWorkSpaceDesc(rs.getString("vWorkSpaceDesc"));
				dto.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setStageDesc(rs.getString("vStageDesc"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setUserCode(rs.getInt("iUserCode"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setRemark(rs.getString("vRemark"));
				dto.setFileVersionId(rs.getString("vFileVersionId"));
				dto.setCurrentSeqNumber(rs.getString("vCurrentSeqNumber"));
				dto.setSubmissionId(rs.getString("submissionId"));
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat"));
				dto.setCertifiedByName(rs.getString("CertifiedByName"));
				dto.setCertifiedOn(rs.getTimestamp("dCertifiedOn"));

				nodeHistory.add(dto);
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodeHistory;

	}

	public ArrayList<DTOWorkSpaceNodeHistory> getWorkspaceNodeHistory(String wsId, int nodeId) throws SQLException {

		ArrayList<DTOWorkSpaceNodeHistory> docHistory = new ArrayList<DTOWorkSpaceNodeHistory>();

		Connection con = ConnectionManager.ds.getConnection();

		String whr = " vWorkspaceId = '" + wsId + "' AND iNodeId = " + nodeId;
		ResultSet rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whr, "");

		while (rs.next()) {
			DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();

			dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setTranNo(rs.getInt("iTranNo"));
			dto.setFileName(rs.getString("vFileName"));
			dto.setFolderName(rs.getString("vFolderName"));
			dto.setCoOrdinates(rs.getString("vCoordinates"));
			dto.setStageId(rs.getInt("iStageId"));
			dto.setFileType(rs.getString("vFileType"));
			docHistory.add(dto);
		}
		rs.close();
		con.close();
		return docHistory;
	}

	public Vector<DTOWorkSpaceNodeHistory> getNodeHistoryFolderName(String wsId, int nodeId) throws SQLException {

		Vector<DTOWorkSpaceNodeHistory> docHistory = new Vector<DTOWorkSpaceNodeHistory>();

		Connection con = ConnectionManager.ds.getConnection();

		String whr = " wnh.vWorkspaceId = '" + wsId + "' AND wnh.iNodeId = " + nodeId;
		ResultSet rs = dataTable.getDataSet(con, "wnd.vfoldername ",
				"workspacenodehistory as wnh inner join workspacenodedetail as wnd "
						+ " on wnh.vworkspaceid = wnd.vworkspaceid and wnh.inodeid = wnd.inodeid",
				whr, "");

		while (rs.next()) {
			DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();

			/*
			 * dto.setWorkSpaceId(rs.getString("vWorkspaceId"));
			 * dto.setNodeId(rs.getInt("iNodeId"));
			 * dto.setTranNo(rs.getInt("iTranNo"));
			 * dto.setFileName(rs.getString("vFileName"));
			 */
			dto.setFolderName(rs.getString("vFolderName"));
			/*
			 * dto.setCoOrdinates(rs.getString("vCoordinates"));
			 * dto.setStageId(rs.getInt("iStageId"));
			 * dto.setFileType(rs.getString("vFileType"));
			 */
			docHistory.add(dto);
		}
		rs.close();
		con.close();
		return docHistory;
	}

	public Vector<DTOWorkSpaceNodeHistory> getMaxNodeHistoryByTranNoForEsignature(String wsId, int nodeId, int tranNo) {

		Vector<DTOWorkSpaceNodeHistory> nodeHistory = new Vector<DTOWorkSpaceNodeHistory>();
		ResultSet rs = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where = " vWorkspaceId = '" + wsId + "' ";
			where += "and iNodeId =" + nodeId;
			where += " and iTranNo = " + tranNo;
			where += " and istageid not in (30,50)";
			rs = dataTable.getDataSet(con, "*", "WorkspaceNodeHistory", where, "");
			while (rs.next()) {
				DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				dto.setWorkSpaceId(rs.getString("vWorkSpaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setTranNo(rs.getInt("iTranNo"));
				dto.setStageId(rs.getInt("iStageId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setFileName(rs.getString("vFileName"));
				dto.setBaseWorkFolder(rs.getString("vFolderName"));
				dto.setCoOrdinates(rs.getString("vCoordinates"));
				dto.setFileType(rs.getString("vFileType"));
				dto.setRoleCode(rs.getString("vRoleCode"));
				dto.setUuId(rs.getString("vDocId"));
				nodeHistory.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeHistory;
	}

	public int getLastCreatorNodeHistory(String wsId, int nodeId) throws SQLException {

		int userCode = 0;

		Connection con = ConnectionManager.ds.getConnection();

		String whr = " vWorkspaceId = '" + wsId + "' AND iNodeId = " + nodeId
				+ " and itranNo=(select max(itranno) from workspacenodehistory " + "where vWorkspaceId = '" + wsId
				+ "' AND iNodeId = " + nodeId + " and iStageId=10)";
		ResultSet rs = dataTable.getDataSet(con, "*", "workspacenodehistory", whr, "");

		while (rs.next()) {
			userCode = rs.getInt("iModifyBy");
		}
		rs.close();
		con.close();
		return userCode;
	}
}// main class ended
