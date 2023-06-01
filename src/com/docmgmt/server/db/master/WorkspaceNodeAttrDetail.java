package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.docmgmt.dto.DTOAttributeGroupMatrix;
import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;

public class WorkspaceNodeAttrDetail {

	static DataTable dataTable = new DataTable();

	public Vector<DTOWorkSpaceNodeAttrDetail> getAttrForNode(String wsId,
			int nodeId) {
		Vector<DTOWorkSpaceNodeAttrDetail> attrDetails = new Vector<DTOWorkSpaceNodeAttrDetail>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con
					.prepareCall("{call Proc_getAttrForNode(?,?)}");
			cs.setString(1, wsId);
			cs.setInt(2, nodeId);
			ResultSet rs = cs.executeQuery();
			while (rs.next()) {
				DTOWorkSpaceNodeAttrDetail workSpaceNodeAttrDetail = new DTOWorkSpaceNodeAttrDetail();
				workSpaceNodeAttrDetail.setAttrId(rs.getInt("AttrId"));
				workSpaceNodeAttrDetail.setAttrValue(rs.getString("AttrValue"));
				workSpaceNodeAttrDetail.setAttrName(rs.getString("AttrName"));
				workSpaceNodeAttrDetail.setAttrForIndi(rs
						.getString("AttrForIndiId"));
				workSpaceNodeAttrDetail.setModifyBy(rs.getInt("ModifyBy"));
				workSpaceNodeAttrDetail
						.setModifyOn(rs.getTimestamp("ModifyOn"));
				workSpaceNodeAttrDetail.setFileName(rs.getString("FileName"));
				workSpaceNodeAttrDetail.setTranNo(rs.getInt("TranNo"));
				workSpaceNodeAttrDetail.setFolderName(rs
						.getString("FolderName"));
				attrDetails.addElement(workSpaceNodeAttrDetail);
			}
			rs.close();
			cs.close();
			con.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return attrDetails;
	}

	public void updateNodeAttrDetail(Vector<DTOWorkSpaceNodeAttrDetail> attrDtl) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con
					.prepareCall("{call Insert_workspaceNodeAttrDetail(?,?,?,?,?,?,?,?,?)}");
			for (int i = 0; i < attrDtl.size(); i++) {
				DTOWorkSpaceNodeAttrDetail dto = attrDtl.get(i);
				cs.setString(1, dto.getWorkspaceId());
				cs.setInt(2, dto.getNodeId());
				cs.setInt(3, dto.getAttrId());
				cs.setString(4, ""); // attrname not req for update
				cs.setString(5, dto.getAttrValue());
				cs.setString(6, ""); // not in use
				cs.setInt(7, 1); // not in use
				cs.setString(8, "");
				cs.setInt(9, 2); // datamode 2 for update
				cs.execute();
				dto = null;
			}
			cs.close();
			con.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	public void updateApprovedStatus(String status, String wsId, int iNodeId,
			int iTranNo) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con
					.prepareCall("{call Proc_ApproveStatus(?,?,?)}");
			cs.setString(1, wsId);
			cs.setInt(2, iNodeId);
			cs.setString(3, status);
			cs.execute();
			cs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Vector<DTOWorkSpaceNodeAttrDetail> getNodeAttrDetail(String wsId,
			int nodeId) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeAttrDetail> wsNodeAttrDetail = new Vector<DTOWorkSpaceNodeAttrDetail>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		
		try {
			String Fields = "vWorkspaceId,iNodeId,vattrName,vAttrValue,vAttrForIndiId,iAttrId,iModifyBy,dModifyOn,cStatusIndi,vRemark";
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, Fields,
					"workspacenodeattrdetail", "vWorkSpaceId = '" + wsId
							+ "' and iNodeId =" + nodeId, "");
			while (rs.next()) {
				DTOWorkSpaceNodeAttrDetail dto = new DTOWorkSpaceNodeAttrDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setAttrName(rs.getString("vattrname"));
				String attrValue = rs.getString("vAttrValue");
				if (attrValue != null && attrValue.startsWith("\"")
						&& attrValue.endsWith("\"")) {
					attrValue = attrValue.substring(1, attrValue.length() - 1);
				}
				dto.setAttrValue(attrValue);
				dto.setAttrForIndi(rs.getString("vAttrForIndiId"));
				dto.setAttrId(rs.getInt("iAttrId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
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
				wsNodeAttrDetail.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return wsNodeAttrDetail;
	}
	public Vector<DTOWorkSpaceNodeAttrDetail> getAllNodeAttrDetail(String wsId) {

		Vector<DTOWorkSpaceNodeAttrDetail> wsNodeAttrDetail = new Vector<DTOWorkSpaceNodeAttrDetail>();
		try {
			String Fields = "vWorkspaceId,iNodeId,vattrName,vAttrValue,vAttrForIndiId,iAttrId,iModifyBy,dModifyOn,cStatusIndi";
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, Fields,
					"workspacenodeattrdetail", "vWorkSpaceId = '" + wsId
							+ "' and vAttrForIndiId='0002' and vAttrName='ReferenceText' and vAttrvalue <> ''", "");
			while (rs.next()) {
				DTOWorkSpaceNodeAttrDetail dto = new DTOWorkSpaceNodeAttrDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setAttrName(rs.getString("vattrname"));
				String attrValue = rs.getString("vAttrValue");
				dto.setAttrValue(attrValue);
				dto.setAttrForIndi(rs.getString("vAttrForIndiId"));
				dto.setAttrId(rs.getInt("iAttrId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				wsNodeAttrDetail.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return wsNodeAttrDetail;
	}
	public Vector<DTOWorkSpaceNodeAttrDetail> UserModuleWiseAttributeDetail(String wsId,int attrid,int userid) {

		Vector<DTOWorkSpaceNodeAttrDetail> wsNodeAttrDetail = new Vector<DTOWorkSpaceNodeAttrDetail>();
		try {
			String Fields = "iNodeId,vAttrValue";
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, Fields,
					"View_UserModuleWiseAttributeDetail", "vWorkSpaceId = '" + wsId + "' and iAttrId="+attrid+" and iUserCode="+userid, "iAttrId");
			while (rs.next()) {
				DTOWorkSpaceNodeAttrDetail dto = new DTOWorkSpaceNodeAttrDetail();
				
				dto.setNodeId(rs.getInt("iNodeId"));
				String attrValue = rs.getString("vAttrValue");
				dto.setAttrValue(attrValue);
				wsNodeAttrDetail.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return wsNodeAttrDetail;
	}
	
	public Vector<DTOWorkSpaceNodeAttrDetail> getNodedetailwithAttr(String wsId) {

		Vector<DTOWorkSpaceNodeAttrDetail> wsNodeAttrDetail = new Vector<DTOWorkSpaceNodeAttrDetail>();
		try {
			String Fields = "vWorkspaceId,iNodeId,vNodeDisplayName,iParentNodeId,vFolderName,iAttrId,vAttrName,"
					+ "vAttrValue,vAttrForIndiId,iModifyBy,dModiFyOn,cStatusIndi";
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, Fields,
					"view_AttrdetailswithNodedetails", "vWorkSpaceId = '" + wsId
							+ "' and vAttrForIndiId='0002' and vattrName='Extended Node' and vAttrValue<>''", "");
			while (rs.next()) {
				DTOWorkSpaceNodeAttrDetail dto = new DTOWorkSpaceNodeAttrDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dto.setParentId(rs.getInt("iParentNodeId"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setAttrId(rs.getInt("iAttrId"));
				dto.setAttrName(rs.getString("vattrname"));
				String attrValue = rs.getString("vAttrValue");
				dto.setAttrValue(attrValue);
				dto.setAttrForIndi(rs.getString("vAttrForIndiId"));
				dto.setAttrId(rs.getInt("iAttrId"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				wsNodeAttrDetail.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return wsNodeAttrDetail;
	}

	
	public Vector<DTOWorkSpaceNodeAttrDetail> getNodeAttrDetailsForPdfPublish(String wsId) {

		Vector<DTOWorkSpaceNodeAttrDetail> nodedetail = new Vector<DTOWorkSpaceNodeAttrDetail>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String fields = "iNodeId,vNodeName,vNodeDisplayName,iAttrId,vAttrName,vAttrValue,vAttrForIndiId";
			// ResultSet rs=dataTable.getDataSet(con, fields,
			// "View_NodesLatestHistory",
			// "vWorkspaceId='"+wsId+"' and vfilename like '%.pdf'","");
			String whr = " vWorkspaceId = '" + wsId + "' and (vAttrForIndiId='0002')";
			ResultSet rs = dataTable.getDataSet(con, fields,
					"View_NodesLatestHistoryForPdfPublishwithAttr", whr, "iNodeNo");
			
			while (rs.next()) {
				DTOWorkSpaceNodeAttrDetail dto = new DTOWorkSpaceNodeAttrDetail();
				
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dto.setAttrId(rs.getInt("iAttrId"));
				dto.setAttrName(rs.getString("vAttrName"));
				dto.setAttrValue(rs.getString("vAttrValue"));
				dto.setAttrForIndi(rs.getString("vAttrForIndiId"));
				nodedetail.add(dto);
				dto=null;
				
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nodedetail;
	}

	public boolean insertIntoWorkSpaceNodeAttrDetailForAddChild(
			DTOWorkSpaceNodeAttrDetail dto) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con
					.prepareCall("{call Insert_WorkSpaceNodeAttrDetailAddChild(?,?,?)}");
			cs.setString(1, dto.getWorkspaceId());
			cs.setInt(2, dto.getNodeId());
			cs.setInt(3, dto.getParentId());
			cs.execute();
			cs.close();
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * Changed By Ashmak Shah for bulk node attrs inserts (This is overloading
	 * method)
	 */
	public void insertWorkspaceNodeAttrDetail(DTOWorkSpaceNodeAttrDetail dto) {
		ArrayList<DTOWorkSpaceNodeAttrDetail> workSpaceNodeAttrDetailList = new ArrayList<DTOWorkSpaceNodeAttrDetail>();
		workSpaceNodeAttrDetailList.add(dto);

		insertWorkspaceNodeAttrDetail(workSpaceNodeAttrDetailList);
	}

	/**
	 * Added By Ashmak Shah for bulk node attrs inserts (This is overloading
	 * method)
	 */
	public void insertWorkspaceNodeAttrDetail(
			ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrList) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con
					.prepareCall("{Call Insert_workspaceNodeAttrDetailEntry(?,?,?,?,?,?,?)}");
			for (Iterator<DTOWorkSpaceNodeAttrDetail> iterator = nodeAttrList
					.iterator(); iterator.hasNext();) {
				DTOWorkSpaceNodeAttrDetail dto = iterator.next();

				proc.setString(1, dto.getWorkspaceId());
				proc.setInt(2, dto.getNodeId());
				proc.setInt(3, dto.getAttrId());
				proc.setString(4, dto.getAttrName());
				proc.setString(5, dto.getAttrValue());
				proc.setInt(6, dto.getModifyBy());
				proc.setString(7, dto.getAttrForIndi());
				proc.execute();
			}
			proc.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteWorkspaceNodeAttrDetail(
			ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrList) {
		Connection con = null;
		CallableStatement cs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			cs = con
					.prepareCall("{call Insert_workspaceNodeAttrDetail(?,?,?,?,?,?,?,?,?)}");
			for (int i = 0; i < nodeAttrList.size(); i++) {
				DTOWorkSpaceNodeAttrDetail dto = nodeAttrList.get(i);
				cs.setString(1, dto.getWorkspaceId());
				cs.setInt(2, dto.getNodeId());
				cs.setInt(3, dto.getAttrId());
				cs.setString(4, ""); // attrname not req for update
				cs.setString(5, dto.getAttrValue());
				cs.setString(6, ""); // not in use
				cs.setInt(7, 1); // not in use
				cs.setString(8, "");
				cs.setInt(9, 3); // datamode 3 for delete
				cs.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (cs != null) {
					cs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void insertWorkSpaceNodeDetailForAttributeGroup(
			Vector<DTOAttributeGroupMatrix> attrMatrix, String wsId, int nodeId) {
		try {
			CallableStatement proc = null;
			Connection con = ConnectionManager.ds.getConnection();
			for (int icount = 0; icount < attrMatrix.size(); icount++) {
				DTOAttributeGroupMatrix dto = attrMatrix.get(icount);
				proc = con
						.prepareCall("{Call Insert_workspaceNodeAttrDetailEntry(?,?,?,?,?,?,?)}");
				proc.setString(1, wsId);
				proc.setInt(2, nodeId);
				proc.setInt(3, dto.getAttrId());
				proc.setString(4, dto.getAttrName());
				proc.setString(5, dto.getAttrValue());
				proc.setInt(6, dto.getModifyBy());
				proc.setString(7, dto.getAttrForIndi());
				proc.execute();

			}
			proc.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Take a function from workspacenodeattribute
	public void updateWorkspaceNodeAttributeValue(DTOWorkSpaceNodeAttribute obj) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con
					.prepareCall("{ Call Proc_updateattrval(?,?,?,?,?,?)}");
			proc.setString(1, obj.getWorkspaceId());
			proc.setInt(2, obj.getNodeId());
			proc.setInt(3, obj.getAttrId());
			proc.setString(4, obj.getAttrValue());
			proc.setInt(5, obj.getModifyBy());
			proc.setString(6, obj.getRemark());

			// System.out.println(proc.getString("attrvalue"));

			proc.execute();
			proc.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Vector<DTOWorkSpaceNodeAttribute> getAttributeDetailForDisplay(
			String wsId, int nodeid) {
		Vector<DTOWorkSpaceNodeAttribute> data = new Vector<DTOWorkSpaceNodeAttribute>();
		String location_name = null;
		try {
			/**
			 * When root node of the workspace will be selected it will also
			 * display project level attributes(attrForIndiId='0000')
			 * 
			 * Condition added by Ashmak Shah (Oct-01-2009)
			 * 
			 * */
			String whr = " vWorkspaceId='" + wsId + "' AND iNodeId=" + nodeid
					+ " AND (vAttrforindiid='0002' OR vAttrforindiid='0000') ";
			Connection con = ConnectionManager.ds.getConnection();
			String attrSeqNoCondition = null;

			// First find Node Attributes with Sequence No.
			attrSeqNoCondition = " AND iAttrSeqNo is NOT NULL ";

			ResultSet rs = dataTable.getDataSet(con, "*",
					"view_AttributeDetai"
					+ "lForDisplay", whr + attrSeqNoCondition,
					"iAttrSeqNo,AttrMatrixValue");

			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			//Vector<Object[]> wsDetail = docMgmtImpl.getWorkspaceDesc(wsId);
			Vector<Object[]> wsDetail = docMgmtImpl.getWorkspaceDescList(wsId);

			if (wsDetail != null) {
				Object[] record = wsDetail.elementAt(0);
				if (record != null) {
					location_name = record[2].toString();
				}
			}

			while (rs.next()) {
				if (rs.getString("iNodeId").equals(null)) {
					continue;
				}
				DTOWorkSpaceNodeAttribute dtoWsAttribute = new DTOWorkSpaceNodeAttribute();
				dtoWsAttribute.setWorkspaceId(rs.getString("vWorkspaceId"));
				dtoWsAttribute.setNodeId(rs.getInt("iNodeId"));
				dtoWsAttribute.setAttrId(rs.getInt("iAttrId"));
				dtoWsAttribute.setAttrName(rs.getString("vAttrName"));
				dtoWsAttribute.setAttrType(rs.getString("vAttrType"));
				dtoWsAttribute.setAttrMatrixValue(rs
						.getString("AttrMatrixValue"));
				dtoWsAttribute.setAttrValue(rs.getString("AttrValues"));
				dtoWsAttribute.setModifyOn(rs.getTimestamp("dModifyOn"));
				dtoWsAttribute.setModifyBy(rs.getInt("iModifyBy"));
				dtoWsAttribute.setAttrForIndiId(rs.getString("vAttrForIndiId"));
				if (dtoWsAttribute.getAttrValue() == null
						|| dtoWsAttribute.getAttrValue().equalsIgnoreCase(
								"null")) {
					dtoWsAttribute.setAttrValue("");
				}
				dtoWsAttribute.setAttrMatrixDisplayValue(rs
						.getString("vAttrDisplayValue"));
				if (dtoWsAttribute.getAttrMatrixDisplayValue() == null
						|| dtoWsAttribute.getAttrMatrixDisplayValue()
								.equalsIgnoreCase("null")
						|| dtoWsAttribute.getAttrMatrixDisplayValue()
								.equalsIgnoreCase("")) {
					dtoWsAttribute.setAttrMatrixDisplayValue("");
				}
				data.addElement(dtoWsAttribute);
				dtoWsAttribute = null;
			}
			rs.close();

			// Now combing Node Attributes without Sequence No with the above
			// ones in the same vector object.
			attrSeqNoCondition = " AND iAttrSeqNo is NULL ";

			rs = dataTable.getDataSet(con, "*",
					"view_AttributeDetailForDisplay", whr + attrSeqNoCondition,
					"iSeqNo DESC");

			while (rs.next()) {

				if (rs.getString("iAttrid") == null
						|| rs.getString("iAttrid").equals(null)) {
					continue;
				}

				if (rs.getString("AttrMatrixValue") == null
						|| rs.getString("AttrMatrixValue").equals(null)) {
					// continue;
				}

				if (rs.getString("vAttrName").equals("country")
						&& location_name.equals("GCC")) {
					if (!(rs.getString("AttrMatrixValue").equals("bh")
							|| rs.getString("AttrMatrixValue").equals("kw")
							|| rs.getString("AttrMatrixValue").equals("om")
							|| rs.getString("AttrMatrixValue").equals("qa")
							|| rs.getString("AttrMatrixValue").equals("common")
							|| rs.getString("AttrMatrixValue").equals("ye")
							|| rs.getString("AttrMatrixValue").equals("sa") || rs
							.getString("AttrMatrixValue").equals("ae"))) {
						continue;
					}
				}

				if (rs.getString("vAttrName").equals("xml:lang")
						&& location_name.equals("GCC")) {
					if (!(rs.getString("AttrMatrixValue").equals("en") || rs
							.getString("AttrMatrixValue").equals("ar"))) {
						continue;
					}
				}
				if (rs.getString("vAttrName").equals("type")
						&& location_name.equals("GCC")) {
					if (!(rs.getString("AttrMatrixValue").equals("pil")
							|| rs.getString("AttrMatrixValue").equals("spc") || rs
							.getString("AttrMatrixValue").equals("label"))) {
						continue;
					}
				}

				if (rs.getString("vAttrName").equals("country")
						&& !location_name.equals("GCC")) {
					if (rs.getString("AttrMatrixValue").equals("bh")
							|| rs.getString("AttrMatrixValue").equals("kw")
							|| rs.getString("AttrMatrixValue").equals("om")
							|| rs.getString("AttrMatrixValue").equals("qa")
							|| rs.getString("AttrMatrixValue").equals("ye")
							|| rs.getString("AttrMatrixValue").equals("sa")
							|| rs.getString("AttrMatrixValue").equals("ae")) {
						continue;
					}
				}

				if (rs.getString("vAttrName").equals("xml:lang")
						&& !location_name.equals("GCC")) {
					if (rs.getString("AttrMatrixValue").equals("ar")) {
						continue;
					}
				}
				if (rs.getString("vAttrName").equals("type")
						&& !location_name.equals("GCC")) {
					if (rs.getString("AttrMatrixValue").equals("pil")
							|| rs.getString("AttrMatrixValue").equals("label")) {
						continue;
					}
				}

				DTOWorkSpaceNodeAttribute dtoWsAttribute = new DTOWorkSpaceNodeAttribute();
				dtoWsAttribute.setWorkspaceId(rs.getString("vWorkspaceId"));
				dtoWsAttribute.setNodeId(rs.getInt("iNodeId"));
				dtoWsAttribute.setAttrId(rs.getInt("iAttrId"));
				dtoWsAttribute.setAttrName(rs.getString("vAttrName"));
				dtoWsAttribute.setAttrType(rs.getString("vAttrType"));

				dtoWsAttribute.setAttrMatrixValue(rs
						.getString("AttrMatrixValue"));

				dtoWsAttribute.setAttrValue(rs.getString("AttrValues"));

				if (dtoWsAttribute.getAttrValue() == null
						|| dtoWsAttribute.getAttrValue().equalsIgnoreCase(
								"null")) {
					dtoWsAttribute.setAttrValue("");
				}
				dtoWsAttribute.setAttrMatrixDisplayValue(rs
						.getString("vAttrDisplayValue"));
				if (dtoWsAttribute.getAttrMatrixDisplayValue() == null
						|| dtoWsAttribute.getAttrMatrixDisplayValue()
								.equalsIgnoreCase("null")
						|| dtoWsAttribute.getAttrMatrixDisplayValue()
								.equalsIgnoreCase("")) {
					dtoWsAttribute.setAttrMatrixDisplayValue("");
				}
				data.addElement(dtoWsAttribute);
				dtoWsAttribute = null;
			}
			rs.close();

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	public Vector<DTOWorkSpaceNodeAttribute> getAttributeDetailForDisplayForOpenSign(
			String wsId, int nodeid) {
		Vector<DTOWorkSpaceNodeAttribute> data = new Vector<DTOWorkSpaceNodeAttribute>();
		String location_name = null;
		try {
			/**
			 * When root node of the workspace will be selected it will also
			 * display project level attributes(attrForIndiId='0000')
			 * 
			 * Condition added by Ashmak Shah (Oct-01-2009)
			 * 
			 * */
			String whr = " vWorkspaceId='" + wsId + "' AND iNodeId=" + nodeid
					+ " AND (vAttrforindiid='0002' OR vAttrforindiid='0000') ";
			Connection con = ConnectionManager.ds.getConnection();
			String attrSeqNoCondition = null;

			// First find Node Attributes with Sequence No.
			attrSeqNoCondition = " AND iAttrSeqNo is NOT NULL ";

			ResultSet rs = dataTable.getDataSet(con, "*",
					"view_AttributeDetai"
					+ "lForDisplay", whr + attrSeqNoCondition,
					"iAttrSeqNo,AttrMatrixValue");

			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			//Vector<Object[]> wsDetail = docMgmtImpl.getWorkspaceDesc(wsId);
			Vector<Object[]> wsDetail = docMgmtImpl.getWorkspaceDescList(wsId);

			if (wsDetail != null) {
				Object[] record = wsDetail.elementAt(0);
				if (record != null) {
					location_name = record[2].toString();
				}
			}

			while (rs.next()) {
				if (rs.getString("iNodeId").equals(null)) {
					continue;
				}
				DTOWorkSpaceNodeAttribute dtoWsAttribute = new DTOWorkSpaceNodeAttribute();
				dtoWsAttribute.setWorkspaceId(rs.getString("vWorkspaceId"));
				dtoWsAttribute.setNodeId(rs.getInt("iNodeId"));
				dtoWsAttribute.setAttrId(rs.getInt("iAttrId"));
				dtoWsAttribute.setAttrName(rs.getString("vAttrName"));
				dtoWsAttribute.setAttrType(rs.getString("vAttrType"));
				dtoWsAttribute.setAttrMatrixValue(rs
						.getString("AttrMatrixValue"));
				dtoWsAttribute.setAttrValue(rs.getString("AttrValues"));
				dtoWsAttribute.setModifyOn(rs.getTimestamp("dModifyOn"));
				dtoWsAttribute.setModifyBy(rs.getInt("iModifyBy"));
				dtoWsAttribute.setAttrForIndiId(rs.getString("vAttrForIndiId"));
				if (dtoWsAttribute.getAttrValue() == null
						|| dtoWsAttribute.getAttrValue().equalsIgnoreCase(
								"null")) {
					dtoWsAttribute.setAttrValue("");
				}
				dtoWsAttribute.setAttrMatrixDisplayValue(rs
						.getString("vAttrDisplayValue"));
				if (dtoWsAttribute.getAttrMatrixDisplayValue() == null
						|| dtoWsAttribute.getAttrMatrixDisplayValue()
								.equalsIgnoreCase("null")
						|| dtoWsAttribute.getAttrMatrixDisplayValue()
								.equalsIgnoreCase("")) {
					dtoWsAttribute.setAttrMatrixDisplayValue("");
				}
				data.addElement(dtoWsAttribute);
				dtoWsAttribute = null;
			}
			rs.close();

			// Now combing Node Attributes without Sequence No with the above
			// ones in the same vector object.
			attrSeqNoCondition = " AND iAttrSeqNo is NULL ";

			rs = dataTable.getDataSet(con, "*",
					"view_AttributeDetailForDisplay", whr + attrSeqNoCondition,
					"vAttrName,AttrMatrixValue ASC");

			while (rs.next()) {

				if (rs.getString("iAttrid") == null
						|| rs.getString("iAttrid").equals(null)) {
					continue;
				}

				if (rs.getString("AttrMatrixValue") == null
						|| rs.getString("AttrMatrixValue").equals(null)) {
					// continue;
				}

				if (rs.getString("vAttrName").equals("country")
						&& location_name.equals("GCC")) {
					if (!(rs.getString("AttrMatrixValue").equals("bh")
							|| rs.getString("AttrMatrixValue").equals("kw")
							|| rs.getString("AttrMatrixValue").equals("om")
							|| rs.getString("AttrMatrixValue").equals("qa")
							|| rs.getString("AttrMatrixValue").equals("common")
							|| rs.getString("AttrMatrixValue").equals("ye")
							|| rs.getString("AttrMatrixValue").equals("sa") || rs
							.getString("AttrMatrixValue").equals("ae"))) {
						continue;
					}
				}

				if (rs.getString("vAttrName").equals("xml:lang")
						&& location_name.equals("GCC")) {
					if (!(rs.getString("AttrMatrixValue").equals("en") || rs
							.getString("AttrMatrixValue").equals("ar"))) {
						continue;
					}
				}
				if (rs.getString("vAttrName").equals("type")
						&& location_name.equals("GCC")) {
					if (!(rs.getString("AttrMatrixValue").equals("pil")
							|| rs.getString("AttrMatrixValue").equals("spc") || rs
							.getString("AttrMatrixValue").equals("label"))) {
						continue;
					}
				}

				if (rs.getString("vAttrName").equals("country")
						&& !location_name.equals("GCC")) {
					if (rs.getString("AttrMatrixValue").equals("bh")
							|| rs.getString("AttrMatrixValue").equals("kw")
							|| rs.getString("AttrMatrixValue").equals("om")
							|| rs.getString("AttrMatrixValue").equals("qa")
							|| rs.getString("AttrMatrixValue").equals("ye")
							|| rs.getString("AttrMatrixValue").equals("sa")
							|| rs.getString("AttrMatrixValue").equals("ae")) {
						continue;
					}
				}

				if (rs.getString("vAttrName").equals("xml:lang")
						&& !location_name.equals("GCC")) {
					if (rs.getString("AttrMatrixValue").equals("ar")) {
						continue;
					}
				}
				if (rs.getString("vAttrName").equals("type")
						&& !location_name.equals("GCC")) {
					if (rs.getString("AttrMatrixValue").equals("pil")
							|| rs.getString("AttrMatrixValue").equals("label")) {
						continue;
					}
				}

				DTOWorkSpaceNodeAttribute dtoWsAttribute = new DTOWorkSpaceNodeAttribute();
				dtoWsAttribute.setWorkspaceId(rs.getString("vWorkspaceId"));
				dtoWsAttribute.setNodeId(rs.getInt("iNodeId"));
				dtoWsAttribute.setAttrId(rs.getInt("iAttrId"));
				dtoWsAttribute.setAttrName(rs.getString("vAttrName"));
				dtoWsAttribute.setAttrType(rs.getString("vAttrType"));

				dtoWsAttribute.setAttrMatrixValue(rs
						.getString("AttrMatrixValue"));

				dtoWsAttribute.setAttrValue(rs.getString("AttrValues"));

				if (dtoWsAttribute.getAttrValue() == null
						|| dtoWsAttribute.getAttrValue().equalsIgnoreCase(
								"null")) {
					dtoWsAttribute.setAttrValue("");
				}
				dtoWsAttribute.setAttrMatrixDisplayValue(rs
						.getString("vAttrDisplayValue"));
				if (dtoWsAttribute.getAttrMatrixDisplayValue() == null
						|| dtoWsAttribute.getAttrMatrixDisplayValue()
								.equalsIgnoreCase("null")
						|| dtoWsAttribute.getAttrMatrixDisplayValue()
								.equalsIgnoreCase("")) {
					dtoWsAttribute.setAttrMatrixDisplayValue("");
				}
				data.addElement(dtoWsAttribute);
				dtoWsAttribute = null;
			}
			rs.close();

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/*
	 * public Vector getAttributeDetailForDisplay(String wsId,int nodeid) {
	 * Vector data = new Vector(); try {* When root node of the workspace will
	 * be selected it will also display project level
	 * attributes(attrForIndiId='0000')
	 * 
	 * Condition added by Ashmak Shah (Oct-01-2009)
	 * 
	 * Stringwhr="workspaceid='"+wsId+"' AND nodeId="+nodeid+
	 * " AND (attrforindiid='0002' OR attrforindiid='0000')"; Connection con =
	 * conMgr.ds.getConnection(); ResultSet
	 * rs=dataTable.getDataSet(con,"*","view_AttributeDetailForDisplay",whr,"");
	 * while (rs.next()) { DTOWorkSpaceNodeAttribute obj = new
	 * DTOWorkSpaceNodeAttribute(); obj.setAttrId(rs.getInt("attrId"));
	 * obj.setAttrName(rs.getString("AttrName"));
	 * obj.setAttrType(rs.getString("AttrType"));
	 * obj.setAttrMatrixValue(rs.getString("attrmatrixvalue"));
	 * obj.setAttrValue(rs.getString("attrValues")); if(obj.getAttrValue()==null
	 * || obj.getAttrValue().equalsIgnoreCase("null")) { obj.setAttrValue(""); }
	 * obj.setAttrMatrixDisplayValue(rs.getString("AttrDisplayValue"));
	 * if(obj.getAttrMatrixDisplayValue()==null ||
	 * obj.getAttrMatrixDisplayValue().equalsIgnoreCase("null") ||
	 * obj.getAttrMatrixDisplayValue().equalsIgnoreCase("")) {
	 * obj.setAttrMatrixDisplayValue(""); } data.addElement(obj); obj=null; }
	 * rs.close(); con.close(); }catch(SQLException e){ e.printStackTrace(); }
	 * return data; }
	 */

	public Vector<DTOWorkSpaceNodeAttribute> getNodeAttributes(String wsId,
			int nodeId) {
		Vector<DTOWorkSpaceNodeAttribute> data = new Vector<DTOWorkSpaceNodeAttribute>();
		try {
			String whr = "vWorkspaceId='" + wsId
					+ "' and vAttrForIndiId = '0002'";
			if (nodeId != -1) {
				whr = whr + " and iNodeId=" + nodeId;
			}
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "*",
					"workspaceNodeAttrDetail", whr, "");
			while (rs.next()) {
				DTOWorkSpaceNodeAttribute dto = new DTOWorkSpaceNodeAttribute();
				dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setAttrId(rs.getInt("iAttrId")); // attrId
				dto.setAttrName(rs.getString("vAttrName")); // attrName
				dto.setAttrValue(rs.getString("vAttrValue")); // attrValue
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));// statusIndi
				data.addElement(dto);
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	
	public Vector<DTOWorkSpaceNodeAttribute> getNodeAttributes_wsList(String wsId,int nodeId) {
		Vector<DTOWorkSpaceNodeAttribute> data = new Vector<DTOWorkSpaceNodeAttribute>();
		try {
			String whr=""; 
			String uType=ActionContext.getContext().getSession().get("usertypename").toString();
			
			if(uType.equalsIgnoreCase("WA")){
			whr = "vWorkspaceId='" + wsId + "' and vAttrForIndiId = '0002' and inodeid in(Select distinct iNodeId From workspacenodedetail "
					+ "Where vWorkspaceId='" + wsId + "' and iParentNodeId=2 and cStatusIndi<>'D' union  Select distinct iNodeId From "
					+ "Proc_getAllParentNodes('" + wsId + "'," + -1 + ") )or inodeid in (select wnd.iNodeId from workspacenodedetail as wnd "
					+ "left join workspaceNodeAttrDetail as wnat on wnat.vworkspaceid= wnd.vworkspaceid and wnat.inodeid=wnd.iparentnodeid and "
					+ "wnat.vattrname='Country' Where wnd.vWorkspaceId='" + wsId + "' and wnd.iparentnodeid in"
					+ "(select wnat.inodeid workspaceNodeAttrDetail where wnat.vattrname='Country' and wnat.vWorkspaceId='" + wsId + "'))"
					+ "and vworkspaceid='" + wsId + "' and vAttrForIndiId = '0002'";
			}
			else{
				whr = "vWorkspaceId='" + wsId + "' and vAttrForIndiId = '0002' and inodeid in(Select distinct iNodeId From workspacenodedetail "
						+ "Where vWorkspaceId='" + wsId + "' and iParentNodeId=2 and cStatusIndi<>'D' union  Select distinct iNodeId From "
						+ "Proc_getAllParentNodes('" + wsId + "'," + -1 + ") )or inodeid in (select wnd.iNodeId from workspacenodedetail as wnd "
						+ "left join workspaceNodeAttrDetail as wnat on wnat.vworkspaceid= wnd.vworkspaceid and wnat.inodeid=wnd.iparentnodeid and "
						+ "wnat.vattrname='Country' Where wnd.vWorkspaceId='" + wsId + "' and wnd.cstatusindi<>'D' and wnd.iparentnodeid in"
						+ "(select wnat.inodeid workspaceNodeAttrDetail where wnat.vattrname='Country' and wnat.vWorkspaceId='" + wsId + "'))"
						+ "and vworkspaceid='" + wsId + "' and vAttrForIndiId = '0002'";	
			}
			if (nodeId != -1) {
				whr = whr + " and iNodeId=" + nodeId;
			}
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, "vworkspaceid,inodeid,iAttrId,vattrname,vattrvalue,iModifyBy,dModifyOn,cStatusIndi",
					"workspaceNodeAttrDetail", whr, "");
			while (rs.next()) {
				DTOWorkSpaceNodeAttribute dto = new DTOWorkSpaceNodeAttribute();
				dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setAttrId(rs.getInt("iAttrId")); // attrId
				dto.setAttrName(rs.getString("vAttrName")); // attrName
				dto.setAttrValue(rs.getString("vAttrValue")); // attrValue
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));// statusIndi
				data.addElement(dto);
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public ArrayList<DTOWorkSpaceNodeAttribute> getWorkspaceAttributeValues(
			String attrName) {

		ArrayList<DTOWorkSpaceNodeAttribute> lstAttrValue = new ArrayList<DTOWorkSpaceNodeAttribute>();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, attrName,
					"View_Project_Level_Search", "Serial_NO!=''", " substring("
							+ attrName + ",9,12)");
			while (rs.next()) {
				DTOWorkSpaceNodeAttribute dto = new DTOWorkSpaceNodeAttribute();
				dto.setAttrValue(rs.getString(attrName));
				lstAttrValue.add(dto);
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstAttrValue;
	}

	/**
	 * nagesh: adding a function for getting events 28-apr-2010
	 * 
	 */

	public ArrayList<DTOWorkSpaceNodeAttrDetail> getCalendarEvents(int month,
			int year, int userCode) {
		ArrayList<DTOWorkSpaceNodeAttrDetail> list = new ArrayList<DTOWorkSpaceNodeAttrDetail>();

		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "iattrid", "CalendarEventAttribute",
					"iUserCode=" + userCode, "");
			String attrChoices = null;
			if (rs != null) {
				if (rs.next()) {
					attrChoices = rs.getString("iattrid");
					if (attrChoices == null || attrChoices.equals(""))
						return list;
				} else
					return list;
			} else
				return list;
			String mon = "0" + new Integer(month).toString();
			mon = mon.substring(mon.length() - 2);
			String where = " vattrvalue like '" + year + "/" + mon
					+ "/__' and iusercode=" + userCode + "and iattrid in ("
					+ attrChoices + ")";
			String orderBy = " vattrvalue,vattrname";
			rs = dataTable
					.getDataSet(
							con,
							"DISTINCT vattrname,vattrvalue,vworkspaceid,vworkspacedesc",
							"View_CalendarEvents", where, orderBy);
			while (rs.next()) {
				DTOWorkSpaceNodeAttrDetail dto = new DTOWorkSpaceNodeAttrDetail();
				dto.setAttrName(rs.getString("vattrname"));
				dto.setAttrValue(rs.getString("vattrvalue"));
				dto.setWorkspaceId(rs.getString("vworkspaceid"));
				dto.setWorkSpaceDesc(rs.getString("vworkspacedesc"));
				list.add(dto);
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
		return list;
	}

	public ArrayList<DTOWorkSpaceNodeAttrDetail> getReminderEvents(int userCode) {
		ArrayList<DTOWorkSpaceNodeAttrDetail> list = new ArrayList<DTOWorkSpaceNodeAttrDetail>();

		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "ireminderId",
					"CalendarEventAttribute", "iUserCode=" + userCode, "");
			String attrChoices = null;
			if (rs != null) {
				if (rs.next()) {
					attrChoices = rs.getString("ireminderId");
					if (attrChoices == null || attrChoices.equals(""))
						return list;
				} else
					return list;
			} else
				return list;
			String where = "iusercode="
					+ userCode
					+ " and iattrid in ("
					+ attrChoices
					+ ") group by vworkspaceid,inodeid,iattrid,vAttrForIndiId,iusercode,vattrname,vattrvalue,vworkspacedesc,vnodename,vnodedisplayname";
			String orderBy = " vattrvalue , vworkspacedesc , vworkspaceid,inodeid,iattrid,iusercode,vattrname,vnodename,vnodedisplayname";
			rs = dataTable
					.getDataSet(
							con,
							"vattrname,vattrvalue,vworkspaceid,vworkspacedesc,inodeid,vnodename,vnodedisplayname,iattrid,vAttrForIndiId",
							"View_CalendarEvents", where, orderBy);
			while (rs.next()) {
				DTOWorkSpaceNodeAttrDetail dto = new DTOWorkSpaceNodeAttrDetail();
				dto.setAttrName(rs.getString("vattrname"));
				dto.setAttrValue(rs.getString("vattrvalue"));
				dto.setWorkspaceId(rs.getString("vworkspaceid"));
				dto.setWorkSpaceDesc(rs.getString("vworkspacedesc"));
				dto.setNodeId(rs.getInt("inodeid"));
				dto.setNodeDetailName(rs.getString("vnodedisplayname"));
				dto.setAttrId(rs.getInt("iattrid"));
				dto.setAttrForIndi(rs.getString("vAttrForIndiId"));
				list.add(dto);
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
		return list;
	}

	public ArrayList<DTOAttributeMst> getCalendarEventAttributes() {
		ArrayList<DTOAttributeMst> list = new ArrayList<DTOAttributeMst>();

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable
					.getDataSet(
							con,
							"iattrid,vattrname",
							"attributemst",
							" vattrtype='Date' and cStatusIndi<>'D' and vattrforindiid<>'0001'",
							"");
			while (rs.next()) {
				DTOAttributeMst dto = new DTOAttributeMst();
				dto.setAttrId(rs.getInt("iattrid"));
				dto.setAttrName(rs.getString("vattrname"));
				list.add(dto);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean setEventAttributes(int usercode, String choices) {
		try {
			Connection connection = ConnectionManager.ds.getConnection();
			CallableStatement statement = connection
					.prepareCall("{ call Proc_CalendarEventAttribute(?,?,?,?) }");
			statement.setInt(1, usercode);
			statement.setString(2, choices);
			statement.setString(3, "");
			statement.setInt(4, 1);// 1 means calendar events
			boolean retvalue = statement.execute();
			statement.close();
			connection.close();
			return retvalue;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getCalendarChoices(int usercode) {
		String attrChoices = "";
		try {
			Connection connection = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(connection, "iattrid",
					"CalendarEventAttribute", "iUserCode=" + usercode, "");
			if (rs != null) {
				if (rs.next()) {
					attrChoices = rs.getString("iattrid");
					if (attrChoices == null || attrChoices.equals("")) {
						rs.close();
						connection.close();
						return attrChoices;
					}
				}
				rs.close();
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attrChoices;
	}

	public boolean setReminderAttributes(int usercode, String choices) {
		try {
			Connection connection = ConnectionManager.ds.getConnection();
			CallableStatement statement = connection
					.prepareCall("{ call Proc_CalendarEventAttribute(?,?,?,?) }");
			statement.setInt(1, usercode);
			statement.setString(2, "");
			statement.setString(3, choices);
			statement.setInt(4, 2);// 2 means Reminder events
			boolean retvalue = statement.execute();
			statement.close();
			connection.close();
			return retvalue;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getReminderChoices(int usercode) {
		String attrChoices = "";
		try {
			Connection connection = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(connection, "ireminderId",
					"CalendarEventAttribute", "iUserCode=" + usercode, "");
			if (rs != null) {
				if (rs.next()) {
					attrChoices = rs.getString("ireminderId");
					if (attrChoices == null || attrChoices.equals("")) {
						rs.close();
						connection.close();
						return "";
					}
				}
				rs.close();
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attrChoices;
	}

	public ArrayList<String> getWorkspaceByAttributeValue(
			ArrayList<DTOAttributeMst> attrList) {

		ArrayList<String> workspaceIds = new ArrayList<String>();
		StringBuffer wsIdsForQuery = null;

		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			for (Iterator<DTOAttributeMst> iterator = attrList.iterator(); iterator
					.hasNext();) {
				DTOAttributeMst dtoAttributeMst = iterator.next();
				if (workspaceIds.size() > 0) {
					wsIdsForQuery = new StringBuffer(" AND vWorkspaceId IN ('");
					for (String wsId : workspaceIds) {
						wsIdsForQuery.append(wsId + "','");
					}
					workspaceIds.clear();
					wsIdsForQuery.delete(wsIdsForQuery.length() - 2,
							wsIdsForQuery.length()); // delete last two
					// characters i.e ",'"
					wsIdsForQuery.append(")");
				}

				String where = " vAttrName = '" + dtoAttributeMst.getAttrName()
						+ "' " + " AND vAttrValue LIKE '%"
						+ dtoAttributeMst.getAttrValue() + "%' ";
				if (wsIdsForQuery != null) {
					where += wsIdsForQuery.toString();
				}
				rs = dataTable.getDataSet(con, " DISTINCT vWorkspaceId ",
						"WorkspaceNodeAttrDetail", where, "");
				while (rs.next()) {
					workspaceIds.add(rs.getString("vWorkspaceId"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return workspaceIds;
	}

	public ArrayList<DTOWorkSpaceNodeAttrDetail> getAttrValByNameForWorkSpaceIds(
			ArrayList<String> workSpaceId, String attrName) {
		ArrayList<DTOWorkSpaceNodeAttrDetail> workSpaceDtlByAttrName = new ArrayList<DTOWorkSpaceNodeAttrDetail>();
		DTOWorkSpaceNodeAttrDetail dtoWorkSpaceNodeAttrDetail;
		StringBuffer where = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			where = new StringBuffer("");
			if (workSpaceId != null && workSpaceId.size() > 0) {
				where.append("vWorkspaceId IN ('");
				for (String workSpaceIdStr : workSpaceId) {
					where.append(workSpaceIdStr);
					where.append("','");
				}
				where = where.delete(where.length() - 2, where.length());
				where.append(") AND vAttrForIndiId='0000' AND vAttrName='"
						+ attrName + "'");
			}

			rs = dataTable.getDataSet(con, " vWorkspaceid,vAttrValue",
					"WorkspaceNodeAttrDetail", where.toString(), "");
			while (rs.next()) {
				dtoWorkSpaceNodeAttrDetail = new DTOWorkSpaceNodeAttrDetail();
				dtoWorkSpaceNodeAttrDetail.setWorkspaceId(rs
						.getString("vWorkspaceid"));
				dtoWorkSpaceNodeAttrDetail.setAttrValue(rs
						.getString("vAttrValue"));
				// System.out.println("Recordset Connection="+rs.getString("vWorkspaceid")+rs.getString("vAttrValue"));
				workSpaceDtlByAttrName.add(dtoWorkSpaceNodeAttrDetail);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return workSpaceDtlByAttrName;
	}

	public ArrayList<DTOWorkSpaceNodeAttrDetail> getWorkspaceAttrDtlByAttrType(
			ArrayList<String> workSpaceIdList) {
		ArrayList<DTOWorkSpaceNodeAttrDetail> workSpaceDtlByAttrName = new ArrayList<DTOWorkSpaceNodeAttrDetail>();
		DTOWorkSpaceNodeAttrDetail dtoWorkSpaceNodeAttrDetail;
		StringBuffer where = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			where = new StringBuffer("vWorkspaceId IN ('");
			for (String workSpaceIdStr : workSpaceIdList) {
				where.append(workSpaceIdStr);
				where.append("','");
			}
			where = where.delete(where.length() - 2, where.length());
			where.append(") AND vAttrForIndiId='0002'");
			String selected = "distinct vworkspaceid,vworkspacedesc,inodeid,iattrid,vattrname,"
					+ "vattrvalue,vnodename,vnodedisplayname,vfoldername,vAttrForIndiId";
			rs = dataTable.getDataSet(con, selected,
					"View_WorkspaceNodeAttrDetail", where.toString(), "");
			while (rs.next()) {
				dtoWorkSpaceNodeAttrDetail = new DTOWorkSpaceNodeAttrDetail();
				dtoWorkSpaceNodeAttrDetail.setWorkspaceId(rs
						.getString("vWorkspaceid"));
				dtoWorkSpaceNodeAttrDetail.setWorkSpaceDesc(rs
						.getString("vworkspacedesc"));
				dtoWorkSpaceNodeAttrDetail.setNodeId(rs.getInt("inodeid"));
				dtoWorkSpaceNodeAttrDetail.setAttrId(rs.getInt("iattrid"));
				dtoWorkSpaceNodeAttrDetail.setAttrName(rs
						.getString("vattrname"));
				dtoWorkSpaceNodeAttrDetail.setAttrValue(rs
						.getString("vAttrValue"));
				dtoWorkSpaceNodeAttrDetail.setNodeName(rs
						.getString("vnodename"));
				dtoWorkSpaceNodeAttrDetail.setNodeDisplayName(rs
						.getString("vnodedisplayname"));
				dtoWorkSpaceNodeAttrDetail.setFolderName(rs
						.getString("vfoldername"));
				dtoWorkSpaceNodeAttrDetail.setAttrForIndi(rs
						.getString("vAttrForIndiId"));
				workSpaceDtlByAttrName.add(dtoWorkSpaceNodeAttrDetail);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return workSpaceDtlByAttrName;
	}

	public Vector<DTOWorkSpaceNodeAttribute> getNodeAttrByAttrForIndiId(
			String wsId, int nodeId, String attrForIndiId) {
		Vector<DTOWorkSpaceNodeAttribute> wsNodeAttrib = new Vector<DTOWorkSpaceNodeAttribute>();
		Connection con = null;
		ResultSet rs = null;
		try {

			con = ConnectionManager.ds.getConnection();

			String whr = " vWorkspaceId='" + wsId + "' AND iNodeId=" + nodeId
					+ " AND vAttrforindiid='" + attrForIndiId + "'";
			rs = dataTable.getDataSet(con, "*",
					"view_AttributeDetailForDisplay", whr,
					"iAttrId,AttrMatrixValue");
			while (rs.next()) {
				DTOWorkSpaceNodeAttribute dtoWsAttribute = new DTOWorkSpaceNodeAttribute();
				dtoWsAttribute.setWorkspaceId(rs.getString("vWorkspaceId"));
				dtoWsAttribute.setNodeId(rs.getInt("iNodeId"));
				dtoWsAttribute.setAttrId(rs.getInt("iAttrId"));
				dtoWsAttribute.setAttrName(rs.getString("vAttrName"));
				dtoWsAttribute.setAttrType(rs.getString("vAttrType"));
				dtoWsAttribute.setAttrMatrixValue(rs
						.getString("AttrMatrixValue"));
				dtoWsAttribute.setAttrValue(rs.getString("AttrValues"));
				dtoWsAttribute.setModifyOn(rs.getTimestamp("dModifyOn"));
				dtoWsAttribute.setModifyBy(rs.getInt("iModifyBy"));
				dtoWsAttribute.setAttrForIndiId(rs.getString("vAttrForIndiId"));
				if (dtoWsAttribute.getAttrValue() == null
						|| dtoWsAttribute.getAttrValue().equalsIgnoreCase(
								"null")) {
					dtoWsAttribute.setAttrValue("");
				}
				dtoWsAttribute.setAttrMatrixDisplayValue(rs
						.getString("vAttrDisplayValue"));
				if (dtoWsAttribute.getAttrMatrixDisplayValue() == null
						|| dtoWsAttribute.getAttrMatrixDisplayValue()
								.equalsIgnoreCase("null")
						|| dtoWsAttribute.getAttrMatrixDisplayValue()
								.equalsIgnoreCase("")) {
					dtoWsAttribute.setAttrMatrixDisplayValue("");
				}
				wsNodeAttrib.addElement(dtoWsAttribute);
				dtoWsAttribute = null;
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return wsNodeAttrib;
	}

	public ArrayList<DTOWorkSpaceNodeAttrDetail> getAttributeDetailfromWorkspaceIdAndNodeId(
			String vWorkspaceId, String parentId) {
		Connection con;
		ArrayList<DTOWorkSpaceNodeAttrDetail> DTOWorkSpaceNodeAttrDetail = new ArrayList<DTOWorkSpaceNodeAttrDetail>();
		try {
			con = ConnectionManager.ds.getConnection();

			ResultSet rs = dataTable.getDataSet(con, "*",
					"workspacenodeattrdetail", "vWorkspaceId = '"
							+ vWorkspaceId + "' and iNodeId = '" + parentId
							+ "'" + "and vAttrForIndiId = '0002'", "");
			while (rs.next()) {
				DTOWorkSpaceNodeAttrDetail workSpaceNodeAttrDetail = new DTOWorkSpaceNodeAttrDetail();
				workSpaceNodeAttrDetail.setWorkspaceId(rs.getString(1));
				workSpaceNodeAttrDetail.setNodeId(rs.getInt(2));
				workSpaceNodeAttrDetail.setAttrId(rs.getInt(3));

				workSpaceNodeAttrDetail.setAttrValue(rs.getString(5));
				workSpaceNodeAttrDetail.setAttrName(rs.getString(4));
				workSpaceNodeAttrDetail.setAttrForIndi(rs.getString(9));
				workSpaceNodeAttrDetail.setModifyBy(rs.getInt(11));
				workSpaceNodeAttrDetail.setModifyOn(rs.getTimestamp(12));
 
				DTOWorkSpaceNodeAttrDetail.add(workSpaceNodeAttrDetail);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return DTOWorkSpaceNodeAttrDetail;
	}
	// Create By Virendra Barad For Get NodeAttributeDetailHistory
		public Vector<DTOWorkSpaceNodeAttribute> getWsNodeAttrDetailHistory(String wsId, int nodeId) {

			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			Vector<DTOWorkSpaceNodeAttribute> wsNodeAttrDetailHistory = new Vector<DTOWorkSpaceNodeAttribute>();
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			ResultSet rs = null;
			Connection con = null;
			try {
				con = ConnectionManager.ds.getConnection();

				String Where = "vWorkspaceId = '" + wsId + "' and iNodeId= "
						+ nodeId;
				rs = dataTable.getDataSet(con, "*", "View_WorkSpaceNodeAttrDetailHistory", Where, "itranno");

				while (rs.next()) {
					DTOWorkSpaceNodeAttribute dtoWsNodeAttrDtlhistory = new DTOWorkSpaceNodeAttribute();
					dtoWsNodeAttrDtlhistory.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
					dtoWsNodeAttrDtlhistory.setNodeId(rs.getInt("iNodeId")); // nodeId
					dtoWsNodeAttrDtlhistory.setTranNo(rs.getInt("iTranNo")); // TranNo
					dtoWsNodeAttrDtlhistory.setAttrId(rs.getInt("iAttrId")); // AttrId
					dtoWsNodeAttrDtlhistory.setAttrName(rs.getString("vAttrName")); // AttrName
					dtoWsNodeAttrDtlhistory.setAttrValue(rs.getString("vAttrValue")); // AttrValue
					dtoWsNodeAttrDtlhistory.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // RequiredFlag
					dtoWsNodeAttrDtlhistory.setEditableFlag(rs.getString("cEditableFlag").charAt(0)); // EditableFlag
					dtoWsNodeAttrDtlhistory.setAttrForIndiId(rs.getString("vAttrForIndiId")); //
					dtoWsNodeAttrDtlhistory.setRemark(rs.getString("vRemark")); // Remark
					dtoWsNodeAttrDtlhistory.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
					dtoWsNodeAttrDtlhistory.setUserName(rs.getString("vUserName")); // modifyBy
					dtoWsNodeAttrDtlhistory.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dtoWsNodeAttrDtlhistory.getModifyOn(),locationName,countryCode);
					dtoWsNodeAttrDtlhistory.setISTDateTime(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(dtoWsNodeAttrDtlhistory.getModifyOn(),locationName,countryCode);
					dtoWsNodeAttrDtlhistory.setISTDateTime(time.get(0));
					dtoWsNodeAttrDtlhistory.setESTDateTime(time.get(1));
				}
					dtoWsNodeAttrDtlhistory.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
					wsNodeAttrDetailHistory.addElement(dtoWsNodeAttrDtlhistory);
					dtoWsNodeAttrDtlhistory = null;
				}

				rs.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			
			return wsNodeAttrDetailHistory;
		}

		// Create By Virendra Barad For Get NodeAttributeDetailHistory for Table View in ProjectSnapshot
				public Vector<DTOWorkSpaceNodeAttrDetail> getWsNodeAttrDetailHistoryForTable(String wsId, int nodeId) {

					DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
					Vector<DTOWorkSpaceNodeAttrDetail> wsNodeAttrDetailHistory = new Vector<DTOWorkSpaceNodeAttrDetail>();
					ArrayList<String> time = new ArrayList<String>();
					String locationName = ActionContext.getContext().getSession().get("locationname").toString();
					String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
					ResultSet rs = null;
					Connection con = null;
					try {
						con = ConnectionManager.ds.getConnection();

						String Where = "vWorkspaceId = '" + wsId + "' and iNodeId= "
								+ nodeId;
						rs = dataTable.getDataSet(con, "*", "View_WorkSpaceNodeAttrDetailHistory", Where, "itranno");

						while (rs.next()) {
							DTOWorkSpaceNodeAttrDetail dtoWsNodeAttrDtlhistory = new DTOWorkSpaceNodeAttrDetail();
							dtoWsNodeAttrDtlhistory.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
							dtoWsNodeAttrDtlhistory.setNodeId(rs.getInt("iNodeId")); // nodeId
							dtoWsNodeAttrDtlhistory.setTranNo(rs.getInt("iTranNo")); // TranNo
							dtoWsNodeAttrDtlhistory.setAttrId(rs.getInt("iAttrId")); // AttrId
							dtoWsNodeAttrDtlhistory.setAttrName(rs.getString("vAttrName")); // AttrName
							dtoWsNodeAttrDtlhistory.setAttrValue(rs.getString("vAttrValue")); // AttrValue
							dtoWsNodeAttrDtlhistory.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // RequiredFlag
							dtoWsNodeAttrDtlhistory.setEditableFlag(rs.getString("cEditableFlag").charAt(0)); // EditableFlag
							dtoWsNodeAttrDtlhistory.setAttrForIndi(rs.getString("vAttrForIndiId")); //
							dtoWsNodeAttrDtlhistory.setRemark(rs.getString("vRemark")); // Remark
							dtoWsNodeAttrDtlhistory.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
							dtoWsNodeAttrDtlhistory.setUserName(rs.getString("vUserName")); // modifyBy
							dtoWsNodeAttrDtlhistory.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
							//dtoWsNodeAttrDtlhistory.setFileName(rs.getString("vFileName"));
							//dtoWsNodeAttrDtlhistory.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
							//dtoWsNodeAttrDtlhistory.setFolderName(rs.getString("vFolderName"));
						if(countryCode.equalsIgnoreCase("IND")){
							time = docMgmtImpl.TimeZoneConversion(dtoWsNodeAttrDtlhistory.getModifyOn(),locationName,countryCode);
							dtoWsNodeAttrDtlhistory.setISTDateTime(time.get(0));
						}
						else{
							time = docMgmtImpl.TimeZoneConversion(dtoWsNodeAttrDtlhistory.getModifyOn(),locationName,countryCode);
							dtoWsNodeAttrDtlhistory.setISTDateTime(time.get(0));
							dtoWsNodeAttrDtlhistory.setESTDateTime(time.get(1));
						}
							dtoWsNodeAttrDtlhistory.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
							wsNodeAttrDetailHistory.addElement(dtoWsNodeAttrDtlhistory);
							dtoWsNodeAttrDtlhistory = null;
						}

						rs.close();
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
					
					return wsNodeAttrDetailHistory;
				}

				public boolean insertIntoWorkSpaceNodeAttrDetailForAddChildForRepeatSection(
						DTOWorkSpaceNodeAttrDetail dto) {
					try {
						Connection con = ConnectionManager.ds.getConnection();
						CallableStatement cs = con
								.prepareCall("{call Insert_WorkSpaceNodeAttrDetailAddChildForRepeatSection(?,?,?)}");
						cs.setString(1, dto.getWorkspaceId());
						cs.setInt(2, dto.getNodeId());
						cs.setInt(3, dto.getParentId());
						cs.execute();
						cs.close();
						con.close();
						return true;
					} catch (SQLException e) {
						e.printStackTrace();
					}

					return false;

				}
				
				public ArrayList<List<String>> getAttributeDetails(String workSpaceId, Integer nodeId) {
					DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
					ArrayList<List<String>> data= new ArrayList<List<String>>();
					Vector<DTOWorkSpaceNodeAttrDetail> wsNodeAttrDetailHistory = new Vector<DTOWorkSpaceNodeAttrDetail>();
					ArrayList<String> time = new ArrayList<String>();
					String locationName = ActionContext.getContext().getSession().get("locationname").toString();
					String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
					ResultSet rs = null;
					CallableStatement cs = null;
					Connection con = null;
					try {
						con = ConnectionManager.ds.getConnection();
						cs=con.prepareCall("{call Get_AttributeNodeDetail(?,?)}");
						cs.setString(1,workSpaceId);
						cs.setInt(2,nodeId);
						
						rs = cs.executeQuery();
						
						while (rs.next()) {
							List<String> dataRecord= new ArrayList<String>();
					 	
					 				dataRecord.add(rs.getString("Artifact Location_BABE"));
					 				dataRecord.add(rs.getString("Artifact Type_BABE"));
					 				dataRecord.add(rs.getString("Investigator/CRO Document"));
					 				dataRecord.add(rs.getString("Remarks"));
					 				dataRecord.add(rs.getString("Sponsor Document"));
					 				dataRecord.add(rs.getString("Wet Ink Signature"));
					 				data.add(dataRecord);
					 			}
						
						rs.close();
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
					
					return data;
				}

				public Vector<DTOWorkSpaceNodeAttribute> getAttrDtl(
						String wsId, int nodeId) {
					Vector<DTOWorkSpaceNodeAttribute> wsNodeAttrib = new Vector<DTOWorkSpaceNodeAttribute>();
					Connection con = null;
					ResultSet rs = null;
					try {

						con = ConnectionManager.ds.getConnection();

						String whr = " vWorkspaceId='" + wsId + "' AND iNodeId=" + nodeId + " AND vAttrforindiid= '0002'";
						
						rs = dataTable.getDataSet(con, "vAttrName,vAttrValue", "workspacenodeattrdetail", whr,"iAttrId");
						
						while (rs.next()) {
							
							DTOWorkSpaceNodeAttribute dtoWsAttribute = new DTOWorkSpaceNodeAttribute();
							
							dtoWsAttribute.setAttrName(rs.getString("vAttrName"));
							dtoWsAttribute.setAttrValue(rs.getString("vAttrValue"));
							
							wsNodeAttrib.addElement(dtoWsAttribute);
							dtoWsAttribute = null;
						}
						rs.close();

					} catch (SQLException e) {
						e.printStackTrace();
					} 
			return wsNodeAttrib;
				}
				// Create By Virendra Barad For Get NodeAttributeDetailHistory
				public Vector<DTOWorkSpaceNodeAttribute> getWsNodeAttrDetailHistoryByAttrId(String wsId, int nodeId) {

					DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
					Vector<DTOWorkSpaceNodeAttribute> wsNodeAttrDetailHistory = new Vector<DTOWorkSpaceNodeAttribute>();
					ArrayList<String> time = new ArrayList<String>();
					String locationName = ActionContext.getContext().getSession().get("locationname").toString();
					String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
					ResultSet rs = null;
					Connection con = null;
					try {
						con = ConnectionManager.ds.getConnection();

						String Where = "vWorkspaceId = '" + wsId + "' and iNodeId= "
								+ nodeId+ " AND vAttrForIndiId<>'0001' ";
						rs = dataTable.getDataSet(con, "*", "View_WorkSpaceNodeAttrDetailHistory", Where, "iAttrId");

						while (rs.next()) {
							DTOWorkSpaceNodeAttribute dtoWsNodeAttrDtlhistory = new DTOWorkSpaceNodeAttribute();
							dtoWsNodeAttrDtlhistory.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
							dtoWsNodeAttrDtlhistory.setNodeId(rs.getInt("iNodeId")); // nodeId
							dtoWsNodeAttrDtlhistory.setTranNo(rs.getInt("iTranNo")); // TranNo
							dtoWsNodeAttrDtlhistory.setAttrId(rs.getInt("iAttrId")); // AttrId
							dtoWsNodeAttrDtlhistory.setAttrName(rs.getString("vAttrName")); // AttrName
							dtoWsNodeAttrDtlhistory.setAttrValue(rs.getString("vAttrValue")); // AttrValue
							dtoWsNodeAttrDtlhistory.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // RequiredFlag
							dtoWsNodeAttrDtlhistory.setEditableFlag(rs.getString("cEditableFlag").charAt(0)); // EditableFlag
							dtoWsNodeAttrDtlhistory.setAttrForIndiId(rs.getString("vAttrForIndiId")); //
							dtoWsNodeAttrDtlhistory.setRemark(rs.getString("vRemark")); // Remark
							dtoWsNodeAttrDtlhistory.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
							dtoWsNodeAttrDtlhistory.setUserName(rs.getString("vUserName")); // modifyBy
							dtoWsNodeAttrDtlhistory.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
						if(countryCode.equalsIgnoreCase("IND")){
							time = docMgmtImpl.TimeZoneConversion(dtoWsNodeAttrDtlhistory.getModifyOn(),locationName,countryCode);
							dtoWsNodeAttrDtlhistory.setISTDateTime(time.get(0));
						}
						else{
							time = docMgmtImpl.TimeZoneConversion(dtoWsNodeAttrDtlhistory.getModifyOn(),locationName,countryCode);
							dtoWsNodeAttrDtlhistory.setISTDateTime(time.get(0));
							dtoWsNodeAttrDtlhistory.setESTDateTime(time.get(1));
						}
							dtoWsNodeAttrDtlhistory.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
							wsNodeAttrDetailHistory.addElement(dtoWsNodeAttrDtlhistory);
							dtoWsNodeAttrDtlhistory = null;
						}

						rs.close();
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
					
					return wsNodeAttrDetailHistory;
				}

				public List<List<String>> getAttributeDetailsForCSV(String workSpaceId, Integer nodeId,List<String> tableHeader) {

					List<List<String>> wsNodeAttrDetailHistory = new ArrayList<List<String>>();
					ResultSet rs = null;
					CallableStatement cs = null;
					Connection con = null;
					DocMgmtImpl docmgmt=new DocMgmtImpl();
					boolean flag=true;
					
					try {
						con = ConnectionManager.ds.getConnection();
						cs=con.prepareCall("{call Get_AttributeNodeDetail(?,?)}");
						cs.setString(1,workSpaceId);
						cs.setInt(2,nodeId);
						//System.out.println("exec Get_AttributeNodeDetail('"+workSpaceId+"','"+nodeId+"')");
						rs = cs.executeQuery();
									
						while (rs.next()) {
							
							List<String> dataRecord= new ArrayList<String>();
					 		
					 		for(int  j = 0 ; j < tableHeader.size(); j++){
					 			
					 			String fieldName = tableHeader.get(j);
					 			String nodeName=rs.getString("vFolderName");
					 			
					 			if(flag!=false){
					 				dataRecord.add(nodeName);
					 			}
					 			
					 			if (rs.getString(fieldName).equals("") || rs.getString(fieldName) == null){
					 				dataRecord.add("-");
					 			}else{
					 				dataRecord.add(rs.getString(fieldName));
					 				
					 			}
					 			flag=false;
						 		} 			
					 				
					 		wsNodeAttrDetailHistory.add(dataRecord);
							
							
							/*
							DTOWorkSpaceNodeAttrDetail dtoWsNodeAttrDtlhistory = new DTOWorkSpaceNodeAttrDetail();
							dtoWsNodeAttrDtlhistory.setWorkspaceId(rs.getString("vWorkspaceId")); 
							dtoWsNodeAttrDtlhistory.setNodeId(rs.getInt("iNodeId")); 
							dtoWsNodeAttrDtlhistory.setNodeName(rs.getString("vnodename")); 
							dtoWsNodeAttrDtlhistory.setNodeDisplayName(rs.getString("vnodedisplayname"));
							dtoWsNodeAttrDtlhistory.setFolderName(rs.getString("vFolderName"));
							
							for(int i =0;i<docmgmt.getNodeAttrDetailForCSV(workSpaceId,nodeId).size();i++){
								dtoWsNodeAttrDtlhistory.setAttr1(docmgmt.getNodeAttrDetailForCSV(workSpaceId,nodeId).get(i).getAttrValue());
								i++;
								dtoWsNodeAttrDtlhistory.setAttr2(docmgmt.getNodeAttrDetailForCSV(workSpaceId,nodeId).get(i).getAttrValue());
								break;
							}
							
							wsNodeAttrDetailHistory.add(dtoWsNodeAttrDtlhistory);
						*/}
									
						rs.close();
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
				return wsNodeAttrDetailHistory;
				}
	
	public Vector<DTOWorkSpaceNodeAttrDetail> getNodeAttrDetailForCSV(String wsId,int nodeId) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeAttrDetail> wsNodeAttrDetail = new Vector<DTOWorkSpaceNodeAttrDetail>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
					
		try {
			String Fields = "vWorkspaceId,iNodeId,vattrName,vAttrValue,vAttrForIndiId,iAttrId,iModifyBy,dModifyOn,cStatusIndi,vRemark";
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, Fields,
							"workspacenodeattrdetail", "vWorkSpaceId = '" + wsId
							+ "' and iNodeId =" + nodeId+ " and vattrforindiid<>0001", "");
				while (rs.next()) {
					DTOWorkSpaceNodeAttrDetail dto = new DTOWorkSpaceNodeAttrDetail();
					dto.setWorkspaceId(rs.getString("vWorkspaceId"));
					dto.setNodeId(rs.getInt("iNodeId"));
					dto.setAttrName(rs.getString("vattrname"));
					String attrValue = rs.getString("vAttrValue");
					if (attrValue != null && attrValue.startsWith("\"") && attrValue.endsWith("\"")) {
						attrValue = attrValue.substring(1, attrValue.length() - 1);
					}
					dto.setAttrValue(attrValue);
					dto.setAttrForIndi(rs.getString("vAttrForIndiId"));
					dto.setAttrId(rs.getInt("iAttrId"));
					dto.setModifyBy(rs.getInt("iModifyBy"));
					dto.setModifyOn(rs.getTimestamp("dModifyOn"));
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
					wsNodeAttrDetail.addElement(dto);
					dto = null;
				}
				rs.close();
				con.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		return wsNodeAttrDetail;
	}
	public Vector<Object []> getAttributeDetailsForDocSign(String workSpaceId, Integer nodeId,List<String> tableHeader) {

		//List<List<String>> wsNodeAttrDetailHistory = new ArrayList<List<String>>();
		Vector<Object []> wsNodeAttrDetailHistory = new Vector<Object []>();
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		String baseFolderPath= knetProperties.getValue("BaseWorkFolder");
		ResultSet rs = null;
		CallableStatement cs = null;
		Connection con = null;
		DocMgmtImpl docmgmt=new DocMgmtImpl();
		int usergrpcode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());
		int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		boolean flag=true;
		StringBuffer siteTableHtml = new StringBuffer();
		try {
			con = ConnectionManager.ds.getConnection();
			cs=con.prepareCall("{call Get_AttributeNodeDetail(?,?)}");
			cs.setString(1,workSpaceId);
			cs.setInt(2,nodeId);
			//System.out.println("exec Get_AttributeNodeDetail('"+workSpaceId+"','"+nodeId+"')");
			rs = cs.executeQuery();
						
			while (rs.next()) {
				
				List<String> dataRecord= new ArrayList<String>();
				int objsize=8+tableHeader.size();
				
				Object[] record = new Object[objsize];
				record[0] = workSpaceId;
				record[1] = nodeId;
				String filePath="";
				Vector<DTOWorkSpaceNodeHistory> getPendingWorks;
		 		Vector<DTOWorkSpaceNodeHistory> dto =new Vector<>();
		 		dto = docmgmt.getNodeHistoryForDocSign(workSpaceId, nodeId);
		 		if(dto.size()>0){
		 			filePath = baseFolderPath +"/"+ dto.get(0).getBaseWorkFolder() +"/"+dto.get(0).getFileName();
		 		}
				record[3] = filePath;
		 		boolean iscreatedRights = docmgmt.iscreatedRights(workSpaceId,nodeId, usercode, usergrpcode);
		 		
		 		String isCreteRights=Boolean.toString(iscreatedRights);
		 		dataRecord.add(isCreteRights);
		 		record[4]=isCreteRights;
		 		int tempStageId = docmgmt.getMaxNodeHistory(workSpaceId,nodeId);
		 		String stageId=String.valueOf(tempStageId);
		 		record[5] = stageId;
		 		String senfForReview = docmgmt.checkSendForReview(workSpaceId,nodeId);
		 		record[6] = senfForReview;
		 		String curentStageDesc = "-";
		 		getPendingWorks = docmgmt.getCurrentStageDesc(workSpaceId,nodeId);
		 		if(getPendingWorks.size()>0){
		 		int currentStageId = getPendingWorks.get(0).getStageId();
		 		 if(currentStageId==10 && !senfForReview.equalsIgnoreCase("SR")){
		 			curentStageDesc = "Uploded";
		 		}
		 		 else if(currentStageId==10 && senfForReview.equalsIgnoreCase("SR")){
		 			curentStageDesc = "Created";
		 		}
		 		else if(currentStageId==20 || currentStageId==100){
		 			curentStageDesc = getPendingWorks.get(0).getStageDesc();
		 		}
		 		else if(currentStageId==0){
		 			curentStageDesc = getPendingWorks.get(0).getStageDesc();
		 		}
		 		else{
		 			curentStageDesc = "-";
		 			}	
		 		}
		 		record[7] = curentStageDesc;
		 		for(int  j = 0 ; j < tableHeader.size(); j++){
		 			
		 			String fieldName = tableHeader.get(j);
		 			String nodeName=rs.getString("vFolderName");
		 		
		 			if(flag!=false){
		 				dataRecord.add(nodeName);
		 				record[2]=nodeName;
		 			}
		 			
		 			if (rs.getString(fieldName).equals("") || rs.getString(fieldName) == null){
		 				dataRecord.add("-");
		 				record[j+8]="-";
		 			}else{
		 				dataRecord.add(rs.getString(fieldName));
		 				record[j+8]=rs.getString(fieldName);
		 				
		 			}
		 			flag=false;
			 		} 		
		 		wsNodeAttrDetailHistory.addElement(record);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			if(rs !=null){
			e.printStackTrace();
			}
		} 
	return wsNodeAttrDetailHistory;
	}
	public List<List<String>> getAttributeDetailsForAttValue(String workSpaceId, Integer nodeId,List<String> tableHeader) {

		List<List<String>> wsNodeAttrDetailHistory = new ArrayList<List<String>>();
		ResultSet rs = null;
		CallableStatement cs = null;
		Connection con = null;
		DocMgmtImpl docmgmt=new DocMgmtImpl();
		boolean flag=true;
		StringBuffer siteTableHtml = new StringBuffer();
		try {
			con = ConnectionManager.ds.getConnection();
			cs=con.prepareCall("{call Get_AttributeNodeDetail(?,?)}");
			cs.setString(1,workSpaceId);
			cs.setInt(2,nodeId);
			//System.out.println("exec Get_AttributeNodeDetail('"+workSpaceId+"','"+nodeId+"')");
			rs = cs.executeQuery();
						
			while (rs.next()) {
				
				List<String> dataRecord= new ArrayList<String>();
				
		 		for(int  j = 0 ; j < tableHeader.size(); j++){
		 			
		 			String fieldName = tableHeader.get(j);
		 		 			
		 			if (rs.getString(fieldName).equals("") || rs.getString(fieldName) == null){
		 				dataRecord.add("-");
		 			}else{
		 				dataRecord.add(rs.getString(fieldName));
		 			}
		 			flag=false;
			 		} 	
		 		wsNodeAttrDetailHistory.add(dataRecord);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			if(rs !=null)
			e.printStackTrace();
		} 
	return wsNodeAttrDetailHistory;
	}
	public DTOWorkSpaceNodeAttrDetail getAttributeDetailByAttrName(String vWorkspaceId, int nodeId,String attrName) {
		Connection con;
		 DTOWorkSpaceNodeAttrDetail dto= new DTOWorkSpaceNodeAttrDetail();
		try {
			con = ConnectionManager.ds.getConnection();

			ResultSet rs = dataTable.getDataSet(con, "*",
					"workspacenodeattrdetail", "vWorkspaceId = '"
							+ vWorkspaceId + "' and iNodeId = '" + nodeId
							+ "'" + "and vattrname = '"+attrName+"' ", "");
			while (rs.next()) {
				dto.setWorkspaceId(rs.getString(1));
				dto.setNodeId(rs.getInt(2));
				dto.setAttrId(rs.getInt(3));
				dto.setAttrValue(rs.getString(5));
				dto.setAttrName(rs.getString(4));
				dto.setAttrForIndi(rs.getString(9));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	public Vector<DTOWorkSpaceNodeAttribute> getTimelineAttrDtl(String wsId) {
		Vector<DTOWorkSpaceNodeAttribute> wsNodeAttrib = new Vector<DTOWorkSpaceNodeAttribute>();
		Connection con = null;
		ResultSet rs = null;
		try {

			con = ConnectionManager.ds.getConnection();

			String whr = " vWorkspaceId='" + wsId + "' And vAttrName='Project Start Date'";
			
			rs = dataTable.getDataSet(con, "*", "workspacenodeattrdetail", whr,"iAttrId");
			
			while (rs.next()) {
				
				DTOWorkSpaceNodeAttribute dtoWsAttribute = new DTOWorkSpaceNodeAttribute();
				dtoWsAttribute.setNodeId(rs.getInt("iNodeId"));
				dtoWsAttribute.setAttrName(rs.getString("vAttrName"));
				dtoWsAttribute.setAttrValue(rs.getString("vAttrValue"));
				
				wsNodeAttrib.addElement(dtoWsAttribute);
				dtoWsAttribute = null;
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} 
			return wsNodeAttrib;
	}
	public Vector<DTOWorkSpaceNodeAttribute> getTimelineAttrDtlForTree(String wsId) {
		Vector<DTOWorkSpaceNodeAttribute> wsNodeAttrib = new Vector<DTOWorkSpaceNodeAttribute>();
		Connection con = null;
		ResultSet rs = null;
		try {

			con = ConnectionManager.ds.getConnection();

			String whr = " vWorkspaceId='" + wsId + "' And vAttrName='Project Start Date' And vAttrvalue<>''";
			
			rs = dataTable.getDataSet(con, "*", "workspacenodeattrdetail", whr,"iAttrId");
			
			while (rs.next()) {
				
				DTOWorkSpaceNodeAttribute dtoWsAttribute = new DTOWorkSpaceNodeAttribute();
				dtoWsAttribute.setNodeId(rs.getInt("iNodeId"));
				dtoWsAttribute.setAttrName(rs.getString("vAttrName"));
				dtoWsAttribute.setAttrValue(rs.getString("vAttrValue"));
				
				wsNodeAttrib.addElement(dtoWsAttribute);
				dtoWsAttribute = null;
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} 
			return wsNodeAttrib;
	}
	
	public List<List<String>> getAttributeDetailsForSection(String workSpaceId, Integer nodeId,List<String> tableHeader) {

		List<List<String>> wsNodeAttrDetailHistory = new ArrayList<List<String>>();
		ResultSet rs = null;
		CallableStatement cs = null;
		Connection con = null;
		DocMgmtImpl docmgmt=new DocMgmtImpl();
		boolean flag=true;
		
		try {
			con = ConnectionManager.ds.getConnection();
			cs=con.prepareCall("{call Get_AttributeNodeDetail(?,?)}");
			cs.setString(1,workSpaceId);
			cs.setInt(2,nodeId);
			//System.out.println("exec Get_AttributeNodeDetail('"+workSpaceId+"','"+nodeId+"')");
			rs = cs.executeQuery();
						
			while (rs.next()) {
				
				List<String> dataRecord= new ArrayList<String>();
		 		
		 		for(int  j = 0 ; j < tableHeader.size(); j++){
		 			
		 			String fieldName = tableHeader.get(j);
		 			
		 			if (rs.getString(fieldName) == null || rs.getString(fieldName).equals("") ){
		 				dataRecord.add("-");
		 			}else{
		 				dataRecord.add(rs.getString(fieldName));
		 				
		 			}
		 			flag=false;
			 		} 			
		 				
		 		wsNodeAttrDetailHistory.add(dataRecord);
				
				
				/*
				DTOWorkSpaceNodeAttrDetail dtoWsNodeAttrDtlhistory = new DTOWorkSpaceNodeAttrDetail();
				dtoWsNodeAttrDtlhistory.setWorkspaceId(rs.getString("vWorkspaceId")); 
				dtoWsNodeAttrDtlhistory.setNodeId(rs.getInt("iNodeId")); 
				dtoWsNodeAttrDtlhistory.setNodeName(rs.getString("vnodename")); 
				dtoWsNodeAttrDtlhistory.setNodeDisplayName(rs.getString("vnodedisplayname"));
				dtoWsNodeAttrDtlhistory.setFolderName(rs.getString("vFolderName"));
				
				for(int i =0;i<docmgmt.getNodeAttrDetailForCSV(workSpaceId,nodeId).size();i++){
					dtoWsNodeAttrDtlhistory.setAttr1(docmgmt.getNodeAttrDetailForCSV(workSpaceId,nodeId).get(i).getAttrValue());
					i++;
					dtoWsNodeAttrDtlhistory.setAttr2(docmgmt.getNodeAttrDetailForCSV(workSpaceId,nodeId).get(i).getAttrValue());
					break;
				}
				
				wsNodeAttrDetailHistory.add(dtoWsNodeAttrDtlhistory);
			*/}
						
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	return wsNodeAttrDetailHistory;
	}
	
	public Vector<DTOWorkSpaceNodeAttribute> getAttrDtForPdfHeader(
			String wsId, int nodeId) {
		Vector<DTOWorkSpaceNodeAttribute> wsNodeAttrib = new Vector<DTOWorkSpaceNodeAttribute>();
		Connection con = null;
		ResultSet rs = null;
		try {

			con = ConnectionManager.ds.getConnection();

			String whr = " vWorkspaceId='" + wsId + "' AND iNodeId=" + nodeId + " AND (vAttrforindiid='0002' OR "
					+ "vAttrforindiid='0000')  AND iAttrSeqNo is NULL";
			
			rs = dataTable.getDataSet(con, "vAttrName,AttrValues", "view_AttributeDetailForPDFHeader", whr,"iSeqNo ASC");
			
			while (rs.next()) {
				
				DTOWorkSpaceNodeAttribute dtoWsAttribute = new DTOWorkSpaceNodeAttribute();
				
				dtoWsAttribute.setAttrName(rs.getString("vAttrName"));
				dtoWsAttribute.setAttrValue(rs.getString("AttrValues"));
				
				wsNodeAttrib.addElement(dtoWsAttribute);
				dtoWsAttribute = null;
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} 
return wsNodeAttrib;
	}
	
	
	public Vector<DTOWorkSpaceNodeAttribute> getAttrDtlForCoverPage(String wsId, int nodeId) {
		Vector<DTOWorkSpaceNodeAttribute> wsNodeAttrib = new Vector<DTOWorkSpaceNodeAttribute>();
		Connection con = null;
		ResultSet rs = null;
		try {

			con = ConnectionManager.ds.getConnection();

			String whr = " vWorkspaceId='" + wsId + "' AND iNodeId=" + nodeId + " AND vAttrForIndiId='0002' and iAttrId<>-999 and vValidValues='Cover Page' and cstatusindi<>'D'";
			
			rs = dataTable.getDataSet(con, "vAttrName,vAttrValue", "workspacenodeattrdetail", whr,"iAttrId");
			
			while (rs.next()) {
				
				DTOWorkSpaceNodeAttribute dtoWsAttribute = new DTOWorkSpaceNodeAttribute();
				
				dtoWsAttribute.setAttrName(rs.getString("vAttrName"));
				dtoWsAttribute.setAttrValue(rs.getString("vAttrValue"));
				wsNodeAttrib.addElement(dtoWsAttribute);
				dtoWsAttribute = null;
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} 
return wsNodeAttrib;
	}
	
	public Vector<DTOWorkSpaceNodeAttribute> getAttrDtlForPageSetting(String wsId, int nodeId) {
		Vector<DTOWorkSpaceNodeAttribute> wsNodeAttrib = new Vector<DTOWorkSpaceNodeAttribute>();
		Connection con = null;
		ResultSet rs = null;
		try {

			con = ConnectionManager.ds.getConnection();

			String whr = " vWorkspaceId='" + wsId + "' AND iNodeId=" + nodeId + " AND vAttrForIndiId='0001' and iAttrId<>-999 and cstatusindi<>'D'";
			
			rs = dataTable.getDataSet(con, "vAttrName,vAttrValue", "workspacenodeattrdetail", whr,"iAttrId");
			
			while (rs.next()) {
				
				DTOWorkSpaceNodeAttribute dtoWsAttribute = new DTOWorkSpaceNodeAttribute();
				
				dtoWsAttribute.setAttrName(rs.getString("vAttrName"));
				dtoWsAttribute.setAttrValue(rs.getString("vAttrValue"));
				wsNodeAttrib.addElement(dtoWsAttribute);
				dtoWsAttribute = null;
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} 
return wsNodeAttrib;
	}
	
	
	public Vector<DTOWorkSpaceNodeAttrDetail> getProjectFailScriptDetail(String wsId) {

		Vector<DTOWorkSpaceNodeAttrDetail> wsNodeAttrDetail = new Vector<DTOWorkSpaceNodeAttrDetail>();
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String where="WorkSpaceId = '" + wsId + "' And vAttrValue='Fail'";
			ResultSet rs = dataTable.getDataSet(con, "*","view_FailScriptDetail", where, "");
			while (rs.next()) {
				
				DTOWorkSpaceNodeAttrDetail dto = new DTOWorkSpaceNodeAttrDetail();
				dto.setWorkspaceId(rs.getString("Workspaceid"));
				dto.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				dto.setNodeId(rs.getInt("iParentNodeId"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dto.setAttrName(rs.getString("vAttrName"));
				dto.setAttrValue(rs.getString("vAttrValue"));
				dto.setUserName(rs.getString("vUserName"));
				dto.setValidValues(rs.getString("UploadDocName"));
				dto.setFileName(rs.getString("TestScriptDocument"));
				String fileExt = dto.getFileName().substring(dto.getFileName().lastIndexOf(".")+1);
				dto.setFileExt(fileExt);
				dto.setBaseWorkFolder(rs.getString("BasePath"));
				dto.setFolderName(rs.getString("DocPath"));
				String tranNo = dto.getFolderName().substring(dto.getFolderName().lastIndexOf("/")+1);
				dto.setTranNo(Integer.parseInt(tranNo));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}
				wsNodeAttrDetail.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return wsNodeAttrDetail;
	}
	
	public Vector<Object []> getAttributeDetailsForESignature(String workSpaceId, Integer nodeId,List<String> tableHeader) {

		//List<List<String>> wsNodeAttrDetailHistory = new ArrayList<List<String>>();
		Vector<Object []> wsNodeAttrDetailHistory = new Vector<Object []>();
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		String baseFolderPath= knetProperties.getValue("BaseWorkFolder");
		ResultSet rs = null;
		CallableStatement cs = null;
		Connection con = null;
		DocMgmtImpl docmgmt=new DocMgmtImpl();
		int usergrpcode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());
		int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		boolean flag=true;
		StringBuffer siteTableHtml = new StringBuffer();
		
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		
		try {
			con = ConnectionManager.ds.getConnection();
			cs=con.prepareCall("{call Get_AttributeNodeDetailForESignature(?,?)}");
			cs.setString(1,workSpaceId);
			cs.setInt(2,nodeId);
			//System.out.println("exec Get_AttributeNodeDetail('"+workSpaceId+"','"+nodeId+"')");
			rs = cs.executeQuery();
						
			while (rs.next()) {
				
				List<String> dataRecord= new ArrayList<String>();
				int objsize=9+tableHeader.size();
				
				Object[] record = new Object[objsize];
				record[0] = workSpaceId;
				record[1] = nodeId;
				String filePath="";
				Vector<DTOWorkSpaceNodeHistory> getPendingWorks;
		 		Vector<DTOWorkSpaceNodeHistory> dto =new Vector<>();
		 		dto = docmgmt.getNodeHistoryForDocSign(workSpaceId, nodeId);
		 		if(dto.size()>0){
		 			filePath = baseFolderPath +"/"+ dto.get(0).getBaseWorkFolder() +"/"+dto.get(0).getFileName();
		 		}
				record[3] = filePath;
		 		boolean iscreatedRights = docmgmt.iscreatedRights(workSpaceId,nodeId, usercode, usergrpcode);
		 		
		 		String isCreteRights=Boolean.toString(iscreatedRights);
		 		dataRecord.add(isCreteRights);
		 		record[4]=isCreteRights;
		 		int tempStageId = docmgmt.getMaxNodeHistory(workSpaceId,nodeId);
		 		String stageId=String.valueOf(tempStageId);
		 		record[5] = stageId;
		 		String senfForReview = docmgmt.checkSendForReview(workSpaceId,nodeId);
		 		record[6] = rs.getString("iModifyBy");
		 		String curentStageDesc = "-";
		 		getPendingWorks = docmgmt.getCurrentStageDesc(workSpaceId,nodeId);
		 		if(getPendingWorks.size()>0){
		 		int currentStageId = getPendingWorks.get(0).getStageId();
		 		 if(currentStageId==10 && !senfForReview.equalsIgnoreCase("SR")){
		 			curentStageDesc = "Uploded";
		 		}
		 		 else if(currentStageId==10 && senfForReview.equalsIgnoreCase("SR")){
		 			curentStageDesc = "Created";
		 		}
		 		else if(currentStageId==20 || currentStageId==100){
		 			curentStageDesc = getPendingWorks.get(0).getStageDesc();
		 		}
		 		else if(currentStageId==0){
		 			curentStageDesc = getPendingWorks.get(0).getStageDesc();
		 		}
		 		else{
		 			curentStageDesc = "-";
		 			}	
		 		}
		 		record[5] = curentStageDesc;
		 		record[7] = "false";
		 		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail= docmgmt.getUserRightsDetailForESignautre(workSpaceId, nodeId);
		 		int tranNo=docmgmt.getMaxTranNo(workSpaceId, nodeId);
		 		//Vector<DTOWorkSpaceNodeHistory> tempHistory = docmgmt.getMaxNodeHistoryByTranNo(workSpaceId, nodeId,tranNo);
		 		Vector<DTOWorkSpaceNodeHistory> tempHistory = docmgmt.getMaxNodeHistoryByTranNoForEsignature(workSpaceId, nodeId,tranNo);
		 		Vector<DTOWorkSpaceUserRightsMst> checkUserRights = docmgmt.getUserRightsDetailForESignature(workSpaceId, nodeId,usercode);
				if(checkUserRights.size()>0){
					record[8] = "true";
				}
		 		
		 		if(tempHistory.size()>0){
			 		for(int i=0;i<getUserRightsDetail.size();i++){
			 			//for(int j=0;j<tempHistory.size();j++){
			 				if((getUserRightsDetail.get(i).getUserCode()==tempHistory.get(0).getModifyBy()) 
			 						&&(tempHistory.get(0).getStageId()!=0 && tempHistory.get(0).getStageId()>=10) ){
			 					record[7] = "true";
			 					break;
			 				}
			 			}
			 		}
		 		
		 		/*for(int i=0;i<getUserRightsDetail.size();i++){
		 			//for(int j=0;j<tempHistory.size();j++){
		 				if((getUserRightsDetail.get(i).getUserCode()==tempHistory.get(0).getModifyBy()) 
		 						&&(tempHistory.get(0).getStageId()!=0 && tempHistory.get(0).getStageId()>=10) ){
		 					record[7] = "true";
		 					break;
		 				}
		 			//}
		 		}*/
		 		
//		 		record[7] = curentStageDesc;
		 		for(int  j = 0 ; j < tableHeader.size(); j++){
		 			
		 			String fieldName = tableHeader.get(j);
		 			String nodeName=rs.getString("vFolderName");
		 		
		 			if(flag!=false){
		 				dataRecord.add(nodeName);
		 				record[2]=nodeName;
		 			}
		 			
		 			if (rs.getString(fieldName) == null || rs.getString(fieldName).equals("") ){
		 				dataRecord.add("-");
		 				record[j+9]="-";
		 			}else{
		 				dataRecord.add(rs.getString(fieldName));
		 				DTOWorkSpaceNodeHistory wsndh = new DTOWorkSpaceNodeHistory();
		 				if(fieldName.equalsIgnoreCase("CreatedOn")){
		 				wsndh.setModifyOn(rs.getTimestamp(fieldName));
		 				
		 				
		 				
		 					if(countryCode.equalsIgnoreCase("IND")){
								time = docmgmt.TimeZoneConversion(wsndh.getModifyOn(),locationName,countryCode);
								wsndh.setISTDateTime(time.get(0));
							}
							else{
									time = docmgmt.TimeZoneConversion(wsndh.getModifyOn(),locationName,countryCode);
									wsndh.setISTDateTime(time.get(0));
									wsndh.setESTDateTime(time.get(1));
							}	
		 				}
		 				if(fieldName.equalsIgnoreCase("CreatedOn") && countryCode.equalsIgnoreCase("IND")){
		 				//record[j+8]=rs.getString(fieldName);
		 					record[j+9]=wsndh.getISTDateTime();
		 				}
		 				else{
		 					record[j+9]=rs.getString(fieldName);
		 				}
		 				
		 			}
		 			flag=false;
			 		} 		
		 		wsNodeAttrDetailHistory.addElement(record);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			if(rs !=null){
			e.printStackTrace();
			}
		} 
	return wsNodeAttrDetailHistory;
	}
	
	public Vector<DTOWorkSpaceNodeAttribute> getAttributeDetailForDisplayForESignature(String wsId, int nodeid) {
		Vector<DTOWorkSpaceNodeAttribute> data = new Vector<DTOWorkSpaceNodeAttribute>();
		String location_name = null;
		try {
			/**
			 * When root node of the workspace will be selected it will also
			 * display project level attributes(attrForIndiId='0000')
			 * 
			 * Condition added by Ashmak Shah (Oct-01-2009)
			 * 
			 * */
			String whr = " vWorkspaceId='" + wsId + "' AND iNodeId=" + nodeid
					+ " AND (vAttrforindiid='0002' OR vAttrforindiid='0000' OR vAttrforindiid='0001') ";
			Connection con = ConnectionManager.ds.getConnection();
			String attrSeqNoCondition = null;

			// First find Node Attributes with Sequence No.
			attrSeqNoCondition = " AND iAttrSeqNo is NOT NULL ";

			ResultSet rs = dataTable.getDataSet(con, "*",
					"view_AttributeDetai"
					+ "lForDisplay", whr + attrSeqNoCondition,
					"iAttrSeqNo,AttrMatrixValue");

			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			//Vector<Object[]> wsDetail = docMgmtImpl.getWorkspaceDesc(wsId);
			Vector<Object[]> wsDetail = docMgmtImpl.getWorkspaceDescList(wsId);

			if (wsDetail != null) {
				Object[] record = wsDetail.elementAt(0);
				if (record != null) {
					location_name = record[2].toString();
				}
			}

			while (rs.next()) {
				if (rs.getString("iNodeId").equals(null)) {
					continue;
				}
				DTOWorkSpaceNodeAttribute dtoWsAttribute = new DTOWorkSpaceNodeAttribute();
				dtoWsAttribute.setWorkspaceId(rs.getString("vWorkspaceId"));
				dtoWsAttribute.setNodeId(rs.getInt("iNodeId"));
				dtoWsAttribute.setAttrId(rs.getInt("iAttrId"));
				dtoWsAttribute.setAttrName(rs.getString("vAttrName"));
				dtoWsAttribute.setAttrType(rs.getString("vAttrType"));
				dtoWsAttribute.setAttrMatrixValue(rs
						.getString("AttrMatrixValue"));
				dtoWsAttribute.setAttrValue(rs.getString("AttrValues"));
				dtoWsAttribute.setModifyOn(rs.getTimestamp("dModifyOn"));
				dtoWsAttribute.setModifyBy(rs.getInt("iModifyBy"));
				dtoWsAttribute.setAttrForIndiId(rs.getString("vAttrForIndiId"));
				if (dtoWsAttribute.getAttrValue() == null
						|| dtoWsAttribute.getAttrValue().equalsIgnoreCase(
								"null")) {
					dtoWsAttribute.setAttrValue("");
				}
				dtoWsAttribute.setAttrMatrixDisplayValue(rs
						.getString("vAttrDisplayValue"));
				if (dtoWsAttribute.getAttrMatrixDisplayValue() == null
						|| dtoWsAttribute.getAttrMatrixDisplayValue()
								.equalsIgnoreCase("null")
						|| dtoWsAttribute.getAttrMatrixDisplayValue()
								.equalsIgnoreCase("")) {
					dtoWsAttribute.setAttrMatrixDisplayValue("");
				}
				data.addElement(dtoWsAttribute);
				dtoWsAttribute = null;
			}
			rs.close();

			// Now combing Node Attributes without Sequence No with the above
			// ones in the same vector object.
			attrSeqNoCondition = " AND iAttrSeqNo is NULL ";

			rs = dataTable.getDataSet(con, "*",
					"view_AttributeDetailForDisplay", whr + attrSeqNoCondition,
					"iSeqNo DESC");

			while (rs.next()) {

				if (rs.getString("iAttrid") == null
						|| rs.getString("iAttrid").equals(null)) {
					continue;
				}

				if (rs.getString("AttrMatrixValue") == null
						|| rs.getString("AttrMatrixValue").equals(null)) {
					// continue;
				}

				if (rs.getString("vAttrName").equals("country")
						&& location_name.equals("GCC")) {
					if (!(rs.getString("AttrMatrixValue").equals("bh")
							|| rs.getString("AttrMatrixValue").equals("kw")
							|| rs.getString("AttrMatrixValue").equals("om")
							|| rs.getString("AttrMatrixValue").equals("qa")
							|| rs.getString("AttrMatrixValue").equals("common")
							|| rs.getString("AttrMatrixValue").equals("ye")
							|| rs.getString("AttrMatrixValue").equals("sa") || rs
							.getString("AttrMatrixValue").equals("ae"))) {
						continue;
					}
				}

				if (rs.getString("vAttrName").equals("xml:lang")
						&& location_name.equals("GCC")) {
					if (!(rs.getString("AttrMatrixValue").equals("en") || rs
							.getString("AttrMatrixValue").equals("ar"))) {
						continue;
					}
				}
				if (rs.getString("vAttrName").equals("type")
						&& location_name.equals("GCC")) {
					if (!(rs.getString("AttrMatrixValue").equals("pil")
							|| rs.getString("AttrMatrixValue").equals("spc") || rs
							.getString("AttrMatrixValue").equals("label"))) {
						continue;
					}
				}

				if (rs.getString("vAttrName").equals("country")
						&& !location_name.equals("GCC")) {
					if (rs.getString("AttrMatrixValue").equals("bh")
							|| rs.getString("AttrMatrixValue").equals("kw")
							|| rs.getString("AttrMatrixValue").equals("om")
							|| rs.getString("AttrMatrixValue").equals("qa")
							|| rs.getString("AttrMatrixValue").equals("ye")
							|| rs.getString("AttrMatrixValue").equals("sa")
							|| rs.getString("AttrMatrixValue").equals("ae")) {
						continue;
					}
				}

				if (rs.getString("vAttrName").equals("xml:lang")
						&& !location_name.equals("GCC")) {
					if (rs.getString("AttrMatrixValue").equals("ar")) {
						continue;
					}
				}
				if (rs.getString("vAttrName").equals("type")
						&& !location_name.equals("GCC")) {
					if (rs.getString("AttrMatrixValue").equals("pil")
							|| rs.getString("AttrMatrixValue").equals("label")) {
						continue;
					}
				}

				DTOWorkSpaceNodeAttribute dtoWsAttribute = new DTOWorkSpaceNodeAttribute();
				dtoWsAttribute.setWorkspaceId(rs.getString("vWorkspaceId"));
				dtoWsAttribute.setNodeId(rs.getInt("iNodeId"));
				dtoWsAttribute.setAttrId(rs.getInt("iAttrId"));
				dtoWsAttribute.setAttrName(rs.getString("vAttrName"));
				dtoWsAttribute.setAttrType(rs.getString("vAttrType"));

				dtoWsAttribute.setAttrMatrixValue(rs
						.getString("AttrMatrixValue"));

				dtoWsAttribute.setAttrValue(rs.getString("AttrValues"));

				if (dtoWsAttribute.getAttrValue() == null
						|| dtoWsAttribute.getAttrValue().equalsIgnoreCase(
								"null")) {
					dtoWsAttribute.setAttrValue("");
				}
				dtoWsAttribute.setAttrMatrixDisplayValue(rs
						.getString("vAttrDisplayValue"));
				if (dtoWsAttribute.getAttrMatrixDisplayValue() == null
						|| dtoWsAttribute.getAttrMatrixDisplayValue()
								.equalsIgnoreCase("null")
						|| dtoWsAttribute.getAttrMatrixDisplayValue()
								.equalsIgnoreCase("")) {
					dtoWsAttribute.setAttrMatrixDisplayValue("");
				}
				data.addElement(dtoWsAttribute);
				dtoWsAttribute = null;
			}
			rs.close();

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}	
	
	public void updateNodeAttrDetailForModifiedUser(String wsId,int nodeId,int userId) {
		Connection con = null;
		int rs1=0;
		int rs2=0;
		try {
			con = ConnectionManager.ds.getConnection();
			
			String query = "UPDATE workspacenodeattrdetail SET " + "iModifyBy = "+userId+" WHERE vWorkspaceId = '"+ wsId + "' AND iNodeId = "+ nodeId+ " ";
			
			rs1 =dataTable.getDataSet1(con,query);
			
			con.close();
		}   
		
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public List<List<String>> getAttributeDetailsForAttValueForESignature(String workSpaceId, Integer nodeId,List<String> tableHeader) {

		List<List<String>> wsNodeAttrDetailHistory = new ArrayList<List<String>>();
		ResultSet rs = null;
		CallableStatement cs = null;
		Connection con = null;
		DocMgmtImpl docmgmt=new DocMgmtImpl();
		boolean flag=true;
		StringBuffer siteTableHtml = new StringBuffer();
		try {
			con = ConnectionManager.ds.getConnection();
			cs=con.prepareCall("{call Get_AttributeNodeDetailForESignature(?,?)}");
			cs.setString(1,workSpaceId);
			cs.setInt(2,nodeId);
			//System.out.println("exec Get_AttributeNodeDetail('"+workSpaceId+"','"+nodeId+"')");
			rs = cs.executeQuery();
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			while (rs.next()) {
				
				List<String> dataRecord= new ArrayList<String>();
				
		 		for(int  j = 0 ; j < tableHeader.size(); j++){
		 			
		 			String fieldName = tableHeader.get(j);
		 		 			
		 			if (rs.getString(fieldName) == null || rs.getString(fieldName).equals("") ){
		 				dataRecord.add("-");
		 			}else{
		 				if(!fieldName.equalsIgnoreCase("CreatedOn")){
		 				dataRecord.add(rs.getString(fieldName));}
		 				else{
		 					
		 				DTOWorkSpaceNodeHistory wsndh = new DTOWorkSpaceNodeHistory();
		 				wsndh.setModifyOn(rs.getTimestamp(fieldName));
		 					if(countryCode.equalsIgnoreCase("IND")){
								time = docmgmt.TimeZoneConversion(wsndh.getModifyOn(),locationName,countryCode);
								wsndh.setISTDateTime(time.get(0));
							}
							else{
									time = docmgmt.TimeZoneConversion(wsndh.getModifyOn(),locationName,countryCode);
									wsndh.setISTDateTime(time.get(0));
									wsndh.setESTDateTime(time.get(1));
							}
		 					dataRecord.add(wsndh.getISTDateTime());
		 				}
		 			}
		 			flag=false;
			 		} 	
		 		wsNodeAttrDetailHistory.add(dataRecord);
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			if(rs !=null)
			e.printStackTrace();
		} 
	return wsNodeAttrDetailHistory;
	}
	
	
	public Vector<DTOWorkSpaceNodeAttrDetail> getAttributeDetailHistoryByAttrName(String vWorkspaceId, int nodeId,String attrName) {
		Connection con;
		Vector<DTOWorkSpaceNodeAttrDetail> dto= new Vector<>();
		try {
			con = ConnectionManager.ds.getConnection();

			ResultSet rs = dataTable.getDataSet(con, "*","workspacenodeattrdetail", 
					"vWorkspaceId = '" + vWorkspaceId + "' and iNodeId = '" + nodeId+ "'" + "and vattrname = '"+attrName+"' ", "");
			while (rs.next()) {
				DTOWorkSpaceNodeAttrDetail dtoWsAttribute = new DTOWorkSpaceNodeAttrDetail();
				dtoWsAttribute.setAttrValue(rs.getString("vattrValue"));
				dto.addElement(dtoWsAttribute);
				dtoWsAttribute = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}
	
}// main class ended

