package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;

public class WorkspaceNodeDetail {

	static DataTable dataTable = new DataTable();
	WorkspaceNodeAttrDetail wsnad = new WorkspaceNodeAttrDetail();
	WorkSpaceUserRightsMst wsurmst = new WorkSpaceUserRightsMst();

	public int getmaxNodeId(String vWorkSpaceId) {

		int maxNodeCount = 0;
		ResultSet rs = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();

			rs = dataTable.getDataSet(con, "max(iNodeId) as MaxNodeId",
					"WorkSpaceNodeDetail", "vWorkSpaceId='" + vWorkSpaceId
							+ "'", "");
			if (rs.next()) {
				maxNodeCount = rs.getInt("MaxNodeId");
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maxNodeCount;
	}

	public int getmaxNodeNo(String vWorkSpaceId, int parentId) {

		int maxNodeCount = 0;

		try {
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			String Where = " vWorkspaceId='" + vWorkSpaceId + "'"
					+ " and iParentNodeId=" + parentId;

			rs = dataTable.getDataSet(con, "max(iNodeNo) MaxNodeNo",
					"WorkSpaceNodeDetail", Where, "");
			if (rs.next()) {
				maxNodeCount = rs.getInt(1);
			} else {
				maxNodeCount = 0;
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return maxNodeCount;
	}

	public void updateNodeDisplayNameAccToEctdAttribute(
			DTOWorkSpaceNodeDetail obj, String AttributeValue) {
		try {
			Connection con = ConnectionManager.ds.getConnection();

			CallableStatement cs = con
					.prepareCall("{call Proc_updateNodeDisplayNameEctdAttribute(?,?,?)}");
			cs.setString(1, obj.getWorkspaceId());
			cs.setInt(2, obj.getNodeId());
			cs.setString(3, AttributeValue);

			cs.execute();

			cs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Vector<Integer> getAllChildNodes(String wsId, int nodeId,
			Vector<Integer> allChildNodes) {

		Vector<DTOWorkSpaceNodeDetail> childNodes;
		ResultSet rs = null;
		// StringBuffer query = new StringBuffer();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			childNodes = getChildNodes(wsId, nodeId);

			Integer[] childNodeId = new Integer[childNodes.size()];

			for (int i = 0; i < childNodes.size(); i++) {
				DTOWorkSpaceNodeDetail dto = childNodes.elementAt(i);

				childNodeId[i] = dto.getNodeId();

				Vector<Integer> data1 = new Vector<Integer>();
				int leafFlag;

				String Where = "vworkspaceId='" + wsId + "' and iParentNodeId="
						+ nodeId;
				rs = dataTable.getDataSet(con, "iNodeId",
						"workspacenodedetail", Where, "");

				while (rs.next()) {
					data1.addElement(rs.getInt("iNodeId"));
				}

				if (data1.size() > 0) {
					leafFlag = 0;
				} else {
					leafFlag = 1;
				}
				if (leafFlag == 0) {
					allChildNodes.addElement(childNodeId[i]);

					getAllChildNodes(wsId, childNodeId[i].intValue(),
							allChildNodes);
				} else {

					allChildNodes.addElement(childNodeId[i]);
				}
				rs.close();
			}

			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return allChildNodes;
	}

	public Vector<DTOWorkSpaceNodeDetail> getChildNodes(String wsId, int nodeId) {
		
		Vector<DTOWorkSpaceNodeDetail> leafNodes = new Vector<DTOWorkSpaceNodeDetail>();
		ResultSet rs = null;
		try {

			Connection con = ConnectionManager.ds.getConnection();
			wsId = "'" + wsId + "'";
			rs = dataTable.getDataSet(con, "iNodeId,vNodeDisplayName",
					"Proc_getAllChildNodes(" + wsId + "," + nodeId + ")", "",
					"");
			while (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				leafNodes.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return leafNodes;
	}
	public Vector<DTOWorkSpaceNodeDetail> getChildNodesModulewise(String wsId, int nodeId) {
		Vector<DTOWorkSpaceNodeDetail> leafNodes = new Vector<DTOWorkSpaceNodeDetail>();
		Vector<DTOWorkSpaceNodeDetail> parentleafNodes = new Vector<DTOWorkSpaceNodeDetail>();
		Vector<DTOWorkSpaceNodeDetail> childleafNodes = new Vector<DTOWorkSpaceNodeDetail>();
		
			parentleafNodes=getModulewiseParentNodes(wsId,nodeId);
			if(parentleafNodes.size()>0){
				leafNodes.addAll(parentleafNodes);
			}
			
			childleafNodes=getModulewiseChildNodes(wsId,nodeId);
			if(childleafNodes.size()>0){
				leafNodes.addAll(childleafNodes);
			}
			
			
		return leafNodes;
		}
	
	public Vector<DTOWorkSpaceNodeDetail> getModulewiseParentNodes(String wsId, int nodeId) {


		Vector<DTOWorkSpaceNodeDetail> parentleafNodes = new Vector<DTOWorkSpaceNodeDetail>();
		ResultSet rs = null;
		try {		
			Connection con = ConnectionManager.ds.getConnection();
			wsId = "'" + wsId + "'";
			rs = dataTable.getDataSet(con, "iNodeId,vNodeDisplayName",
					"Proc_getAllParentNodes(" + wsId + "," + nodeId + ")", "",
					"");
			while (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				parentleafNodes.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return parentleafNodes;
	
	}
	public Vector<DTOWorkSpaceNodeDetail> getModulewiseChildNodes(String wsId, int nodeId) {


		Vector<DTOWorkSpaceNodeDetail> childleafNodes = new Vector<DTOWorkSpaceNodeDetail>();
		ResultSet rs = null;
		try {
			DTOWorkSpaceNodeDetail currentnode = new DTOWorkSpaceNodeDetail();
			currentnode.setNodeId(nodeId);
			currentnode.setNodeDisplayName("");
			childleafNodes.addElement(currentnode);
			Connection con = ConnectionManager.ds.getConnection();
			wsId = "'" + wsId + "'";
			rs = dataTable.getDataSet(con, "iNodeId,vNodeDisplayName",
					"Proc_getAllChildNodes(" + wsId + "," + nodeId + ")", "",
					"");
			while (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				childleafNodes.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return childleafNodes;
	
	}
	public void CopyAndPasteWorkSpace(String sourceWorkspaceId,
			int sourceNodeId, String destWorkspaceId, int destNodeId,
			int userCode, String status) {
		try {
			CallableStatement proc = null;
			Connection con = ConnectionManager.ds.getConnection();
			//proc = con.prepareCall("{ Call Proc_CopyPasteWorkspace(?,?,?,?,?,?)}");
			proc = con.prepareCall("{ Call Proc_CopyPasteWorkspaceforeCSVRepeat(?,?,?,?,?,?)}");
			

			proc.setString(1, sourceWorkspaceId);
			proc.setInt(2, sourceNodeId);
			proc.setString(3, destWorkspaceId);
			proc.setInt(4, destNodeId);
			proc.setInt(5, userCode);
			proc.setString(6, status);
			proc.execute();

			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<DTOWorkSpaceNodeDetail> getNodeDetail(String wsId, int nodeId) {

		Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.ds.getConnection();

			String Where = "vWorkspaceId = '" + wsId + "' and iNodeId= "
					+ nodeId;
			rs = dataTable.getDataSet(con, "*", "workspaceNodeDetail", Where,
					"");

			if (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
				dto.setNodeName(rs.getString("vNodeName")); // nodeName
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
				dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
				dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
				dto.setFolderName(rs.getString("vFolderName")); // folderName
				dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
				dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
				dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
				dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
				dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
				dto.setRemark(rs.getString("vRemark")); // remark
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
				dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
				wsNodeDetail.addElement(dto);
				dto = null;
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

		return wsNodeDetail;
	}
	public Vector<DTOWorkSpaceNodeDetail> getTemplateNodeDetail(String wsId, int nodeId) {

		Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.ds.getConnection();

			String Where = "vTemplateId = '" + wsId + "' and iNodeId= "
					+ nodeId;
			rs = dataTable.getDataSet(con, "*", "templateNodedetail", Where,
					"");

			if (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setWorkspaceId(rs.getString("vTemplateId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setNodeNo(rs.getInt("iNodeNo"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
				dto.setParentNodeId(rs.getInt("iParentNodeId"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0));
				dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0));
				dto.setRemark(rs.getString("vRemark"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat"));
				wsNodeDetail.addElement(dto);
				dto = null;
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

		return wsNodeDetail;
	}
	public Vector<DTOWorkSpaceNodeDetail> getWsNodeDetailHistory(String wsId, int nodeId) {
		
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		ResultSet rs = null;
		Connection con = null;
		
		try {
			con = ConnectionManager.ds.getConnection();

			String Where = "vWorkspaceId = '" + wsId + "' and iNodeId= "
					+ nodeId;
			rs = dataTable.getDataSet(con, "*", "View_WorkSpaceNodeDetailHistory", Where,
					"");

			while (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setTranNo(rs.getInt("iTranNo")); // TranNo
				dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
				dto.setNodeName(rs.getString("vNodeName")); // nodeName
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
				dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
				dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
				dto.setFolderName(rs.getString("vFolderName")); // folderName
				dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
				dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
				dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
				dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
				dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
				dto.setRemark(rs.getString("vRemark")); // remark
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
				dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
				dto.setUserName(rs.getString("vUserName")); // UserName 
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
	
	
	public Vector<DTOWorkSpaceNodeDetail> getDeletedNodeDetail(String wsId) {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
		ResultSet rs = null;
		Connection con = null;
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		
		try {
			con = ConnectionManager.ds.getConnection();

			String Where = "vWorkspaceId = '" + wsId + "' and cStatusIndi='D'";
			//String Where = "vWorkspaceId = '" + wsId + "' and cStatusIndi in ('E','D') ";
			rs = dataTable.getDataSet(con, "*", "View_WorkSpaceNodeDetailHistory", Where,
					"dModifyOn ASC");

			while (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
				dto.setNodeName(rs.getString("vNodeName")); // nodeName
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
				dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
				dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
				dto.setFolderName(rs.getString("vFolderName")); // folderName
				dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
				dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
				dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
				dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
				dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
				dto.setRemark(rs.getString("vRemark")); // remark
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
				dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
				dto.setUserName(rs.getString("vUserName")); // UserName 
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

	public char getCloneFlagDetail(DTOWorkSpaceNodeDetail obj) {
		char cloneFlag = 'N';
		ResultSet rs = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();

			String Where = " vWorkspaceId='" + obj.getWorkspaceId()
					+ "' and iNodeId=" + obj.getNodeId();
			rs = dataTable.getDataSet(con, "cCloneFlag", "workspaceNodeDetail",
					Where, "");

			if (rs.next()) {
				cloneFlag = rs.getString("cCloneFlag").charAt(0);
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cloneFlag;
	}

	public int isLeafNodes(String wsId, int nodeId) {
		ResultSet rs = null;
		int leafNodes = 0;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String Where = " vWorkspaceId='" + wsId + "' and iParentNodeId="
					+ nodeId;
			rs = dataTable.getDataSet(con, "count(*) as LeafNodes",
					"workspacenodedetail", Where, "");

			if (rs.next()) {
				leafNodes = rs.getInt("LeafNodes");
			} else {
				leafNodes = 0;
			}
			rs.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (leafNodes >= 1) {
			return 0; // false //parent node
		} else {
			return 1; // true //leaf node
		}
	}

	public void addChildNode(DTOWorkSpaceNodeDetail dto) {
		String wsId = dto.getWorkspaceId();
		int selectedNode = dto.getNodeId();
		int maxNodeId = this.getmaxNodeId(wsId);
		int nodeNo = 0;
		if (isLeafNodes(wsId, selectedNode) == 0) // parent Node
		{
			// System.out.println("selected Node : " + selectedNode);
			nodeNo = getmaxNodeNo(wsId, selectedNode);
			// System.out.println("node no : " + nodeNo);
			nodeNo++;
		} else {
			nodeNo = 1;
		}
		dto.setNodeId(maxNodeId + 1);
		dto.setNodeNo(nodeNo);
		dto.setParentNodeId(selectedNode);
		insertWorkspaceNodeDetail(dto, 1);
		dto.setStatusIndi('N');
		this.insertWorkspaceNodeDetailhistory(dto, 1);
		DTOWorkSpaceNodeAttrDetail dtoworkspacenodeattribute = new DTOWorkSpaceNodeAttrDetail();
		dtoworkspacenodeattribute.setWorkspaceId(wsId);
		dtoworkspacenodeattribute.setParentId(selectedNode);
		dtoworkspacenodeattribute.setNodeId(maxNodeId + 1);
		wsnad
				.insertIntoWorkSpaceNodeAttrDetailForAddChild(dtoworkspacenodeattribute);
		wsurmst.insertNodeintoWorkSpaceUserRights(wsId, maxNodeId + 1);

	}

	public void addChildNodeBefore(DTOWorkSpaceNodeDetail dto) {
		try {
			String wsId = dto.getWorkspaceId();
			int selectedNode = dto.getNodeId();
			int maxNodeId = this.getmaxNodeId(wsId);

			// Get Selected Node's Node No and Parent Node Detail.
			Vector<DTOWorkSpaceNodeDetail> selectedNodeDetail = this
					.getNodeDetail(wsId, selectedNode);
			DTOWorkSpaceNodeDetail dtoWorkSpaceNode = (DTOWorkSpaceNodeDetail) selectedNodeDetail
					.get(0);
			int nodeNo = dtoWorkSpaceNode.getNodeNo();
			int parentNodeId = dtoWorkSpaceNode.getParentNodeId();

			dto.setNodeId(maxNodeId + 1);
			dto.setNodeNo(nodeNo);
			dto.setParentNodeId(parentNodeId);

			// Set Node no of selected node and nodes below it.
			updateNodeNo(wsId, parentNodeId, nodeNo, "addbefore");
			// Add Record in workspace node detail for new record.
			this.insertWorkspaceNodeDetail(dto, 1);
			dto.setStatusIndi('N');
			this.insertWorkspaceNodeDetailhistory(dto, 1);
			
			DTOWorkSpaceNodeAttrDetail dtoworkspacenodeattribute = new DTOWorkSpaceNodeAttrDetail();
			dtoworkspacenodeattribute.setWorkspaceId(wsId);
			dtoworkspacenodeattribute.setParentId(parentNodeId);
			dtoworkspacenodeattribute.setNodeId(maxNodeId + 1);

			// Add Record in workspace node attribute.
			// Add Record in workspace user rights.
			wsnad
					.insertIntoWorkSpaceNodeAttrDetailForAddChild(dtoworkspacenodeattribute);
			wsurmst.insertNodeintoWorkSpaceUserRights(wsId, maxNodeId + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addChildNodeAfter(DTOWorkSpaceNodeDetail dto) {
		try {
			String wsId = dto.getWorkspaceId();
			int selectedNode = dto.getNodeId();
			int maxNodeId = this.getmaxNodeId(wsId);
			// Get Selected Node's Node No and Parent Node Detail.
			Vector<DTOWorkSpaceNodeDetail> selectedNodeDetail = this
					.getNodeDetail(wsId, selectedNode);
			DTOWorkSpaceNodeDetail dtoWorkSpaceNode = (DTOWorkSpaceNodeDetail) selectedNodeDetail
					.get(0);
			int nodeNo = dtoWorkSpaceNode.getNodeNo();
			int parentNodeId = dtoWorkSpaceNode.getParentNodeId();

			dto.setNodeId(maxNodeId + 1);
			dto.setNodeNo(nodeNo + 1);
			dto.setParentNodeId(parentNodeId);

			// Set Node no of selected node and nodes below it.
			this.updateNodeNo(wsId, parentNodeId, nodeNo, "addafter");

			// Add Record in workspace node detail for new record.
			this.insertWorkspaceNodeDetail(dto, 1);
			dto.setStatusIndi('N');
			this.insertWorkspaceNodeDetailhistory(dto, 1);
			
			DTOWorkSpaceNodeAttrDetail dtoworkspacenodeattribute = new DTOWorkSpaceNodeAttrDetail();
			dtoworkspacenodeattribute.setWorkspaceId(wsId);
			dtoworkspacenodeattribute.setParentId(selectedNode);//
			dtoworkspacenodeattribute.setNodeId(maxNodeId + 1);

			// System.out.println("wsId::"+wsId+"---parentNodeId::"+parentNodeId+"---maxNodeId+1::"+(maxNodeId+1));
			// Add Record in workspace node attribute.
			// Add Record in workspace user rights.
			
			//wsnad.insertIntoWorkSpaceNodeAttrDetailForAddChild(dtoworkspacenodeattribute);
			if(!dto.getNodeName().equalsIgnoreCase("FailedScript")){
				wsnad.insertIntoWorkSpaceNodeAttrDetailForAddChildForRepeatSection(dtoworkspacenodeattribute);
			}
			//wsurmst.insertNodeintoWorkSpaceUserRights(wsId, maxNodeId + 1);
			wsurmst.insertNodeintoWorkSpaceUserRightseCSV(wsId, maxNodeId + 1,selectedNode);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void addChildNodeAfterTC(DTOWorkSpaceNodeDetail dto) {
		try {
			String wsId = dto.getWorkspaceId();
			int selectedNode = dto.getNodeId();
			int maxNodeId = this.getmaxNodeId(wsId);
			// Get Selected Node's Node No and Parent Node Detail.
			//Vector<DTOWorkSpaceNodeDetail> selectedNodeDetail = this.getNodeDetail(wsId, selectedNode);
			// for Repeated Node Repeat After this nodeId
			Vector<DTOWorkSpaceNodeDetail> selectedNodeDetail = this.getNodeDetail(wsId, dto.getAfterRepeatNodeId());
			DTOWorkSpaceNodeDetail dtoWorkSpaceNode = (DTOWorkSpaceNodeDetail) selectedNodeDetail
					.get(0);
			int nodeNo = dtoWorkSpaceNode.getNodeNo();
			int parentNodeId = dtoWorkSpaceNode.getParentNodeId();

			dto.setNodeId(maxNodeId + 1);
			dto.setNodeNo(nodeNo + 1);
			dto.setParentNodeId(parentNodeId);

			// Set Node no of selected node and nodes below it.
			this.updateNodeNo(wsId, parentNodeId, nodeNo, "addafter");

			// Add Record in workspace node detail for new record.
			this.insertWorkspaceNodeDetail(dto, 1);
			dto.setStatusIndi('N');
			this.insertWorkspaceNodeDetailhistory(dto, 1);
			
			DTOWorkSpaceNodeAttrDetail dtoworkspacenodeattribute = new DTOWorkSpaceNodeAttrDetail();
			dtoworkspacenodeattribute.setWorkspaceId(wsId);
			dtoworkspacenodeattribute.setParentId(selectedNode);//
			dtoworkspacenodeattribute.setNodeId(maxNodeId + 1);

			// System.out.println("wsId::"+wsId+"---parentNodeId::"+parentNodeId+"---maxNodeId+1::"+(maxNodeId+1));
			// Add Record in workspace node attribute.
			// Add Record in workspace user rights.
			
			//wsnad.insertIntoWorkSpaceNodeAttrDetailForAddChild(dtoworkspacenodeattribute);
			if(!dto.getNodeName().equalsIgnoreCase("FailedScript")){
				wsnad.insertIntoWorkSpaceNodeAttrDetailForAddChildForRepeatSection(dtoworkspacenodeattribute);
			}
			//wsurmst.insertNodeintoWorkSpaceUserRights(wsId, maxNodeId + 1);
			wsurmst.insertNodeintoWorkSpaceUserRightseCSV(wsId, maxNodeId + 1,selectedNode);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Changed By Ashmak Shah for bulk node inserts (This is overloading method)
	 */
	public void insertWorkspaceNodeDetail(
			DTOWorkSpaceNodeDetail workSpaceNodeDetailDTO, int Mode) {
		ArrayList<DTOWorkSpaceNodeDetail> workSpaceNodeDetailList = new ArrayList<DTOWorkSpaceNodeDetail>();
		workSpaceNodeDetailList.add(workSpaceNodeDetailDTO);

		insertWorkspaceNodeDetail(workSpaceNodeDetailList, Mode);
	}
	public void insertWorkspaceNodeDetailhistory(
			DTOWorkSpaceNodeDetail workSpaceNodeDetailDTO, int Mode) {
		ArrayList<DTOWorkSpaceNodeDetail> workSpaceNodeDetailList = new ArrayList<DTOWorkSpaceNodeDetail>();
		workSpaceNodeDetailList.add(workSpaceNodeDetailDTO);

		insertWorkspaceNodeDetailhistory(workSpaceNodeDetailList, Mode);
	}

	/** Added By Ashmak Shah for bulk node inserts (This is overloading method) */
	public void insertWorkspaceNodeDetail(
			ArrayList<DTOWorkSpaceNodeDetail> workSpaceNodeDetailList, int Mode) {
		CallableStatement proc = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			proc = con
					.prepareCall("{call Insert_workspaceNodeDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			for (Iterator<DTOWorkSpaceNodeDetail> iterator = workSpaceNodeDetailList
					.iterator(); iterator.hasNext();) {
				DTOWorkSpaceNodeDetail dtoWorkSpaceNodeDetail = iterator.next();

				proc.setString(1, dtoWorkSpaceNodeDetail.getWorkspaceId());
				proc.setInt(2, dtoWorkSpaceNodeDetail.getNodeId());
				proc.setInt(3, dtoWorkSpaceNodeDetail.getNodeNo());
				proc.setString(4, dtoWorkSpaceNodeDetail.getNodeName());
				proc.setString(5, dtoWorkSpaceNodeDetail.getNodeDisplayName());
				proc.setString(6, Character.toString(dtoWorkSpaceNodeDetail
						.getNodeTypeIndi()));
				proc.setInt(7, dtoWorkSpaceNodeDetail.getParentNodeId());
				proc.setString(8, dtoWorkSpaceNodeDetail.getFolderName());
				proc.setString(9, Character.toString(dtoWorkSpaceNodeDetail
						.getCloneFlag()));
				proc.setString(10, Character.toString(dtoWorkSpaceNodeDetail
						.getRequiredFlag()));
				proc.setInt(11, dtoWorkSpaceNodeDetail.getCheckOutBy());
				proc.setString(12, Character.toString(dtoWorkSpaceNodeDetail
						.getPublishedFlag()));
				proc.setString(13, dtoWorkSpaceNodeDetail.getRemark());
				proc.setInt(14, dtoWorkSpaceNodeDetail.getModifyBy());
				proc.setString(15, dtoWorkSpaceNodeDetail
						.getDefaultFileFormat());
				proc.setInt(16, Mode); // mode 1 for insert and 2 for edit
				proc.execute();
			}
			proc.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/** Added By Virendra Barad for bulk node inserts (This is overloading method) */
	public void insertWorkspaceNodeDetailhistory(
			ArrayList<DTOWorkSpaceNodeDetail> workSpaceNodeDetailList, int Mode) {
		CallableStatement proc = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();
			proc = con
					.prepareCall("{call Insert_workspaceNodeDetailhistory(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

			for (Iterator<DTOWorkSpaceNodeDetail> iterator = workSpaceNodeDetailList
					.iterator(); iterator.hasNext();) {
				DTOWorkSpaceNodeDetail dtoWorkSpaceNodeDetail = iterator.next();

				proc.setString(1, dtoWorkSpaceNodeDetail.getWorkspaceId());
				proc.setInt(2, dtoWorkSpaceNodeDetail.getNodeId());
				proc.setInt(3, dtoWorkSpaceNodeDetail.getNodeNo());
				proc.setString(4, dtoWorkSpaceNodeDetail.getNodeName());
				proc.setString(5, dtoWorkSpaceNodeDetail.getNodeDisplayName());
				proc.setString(6, Character.toString(dtoWorkSpaceNodeDetail
						.getNodeTypeIndi()));
				proc.setInt(7, dtoWorkSpaceNodeDetail.getParentNodeId());
				proc.setString(8, dtoWorkSpaceNodeDetail.getFolderName());
				proc.setString(9, Character.toString(dtoWorkSpaceNodeDetail
						.getCloneFlag()));
				proc.setString(10, Character.toString(dtoWorkSpaceNodeDetail
						.getRequiredFlag()));
				proc.setInt(11, dtoWorkSpaceNodeDetail.getCheckOutBy());
				proc.setString(12, Character.toString(dtoWorkSpaceNodeDetail
						.getPublishedFlag()));
				proc.setString(13, dtoWorkSpaceNodeDetail.getRemark());
				proc.setInt(14, dtoWorkSpaceNodeDetail.getModifyBy());
				proc.setString(15, dtoWorkSpaceNodeDetail
						.getDefaultFileFormat());
				proc.setString(16, Character.toString(dtoWorkSpaceNodeDetail
						.getStatusIndi()));
				proc.setInt(17, Mode); // mode 1 for insert and 2 for update
				proc.execute();
			}
			proc.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updateNodeNo(String wsId, int parentId, int nodeNo, String flag) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement cs = con
					.prepareCall("{call Proc_UpdateNodeNoForCopyPaste(?,?,?,?) }");

			cs.setString(1, wsId);
			cs.setInt(2, nodeNo);
			cs.setInt(3, parentId);
			if (flag.equalsIgnoreCase("addafter")) {
				cs.setInt(4, 1);// set flag 1 for add after
			} else if (flag.equalsIgnoreCase("addbefore")) {
				cs.setInt(4, 2);// set flag 2 for add after
			}

			cs.execute();
			cs.close();

			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<DTOWorkSpaceNodeDetail> getChildNodeByParentForPublishFromM2toM5(
			int parentNodeId, String workSpaceId) {
		Vector<DTOWorkSpaceNodeDetail> childNodesDtl = new Vector<DTOWorkSpaceNodeDetail>();
		try {
			ResultSet rs = null;

			Connection con = ConnectionManager.ds.getConnection();

			String Fields = "vWorkspaceId,iNodeId,vNodeName,vNodeDisplayName,vFolderName,cNodeTypeIndi,vRemark,cRequiredFlag,cCloneFlag,dModifyOn,iModifyBy";
			String Where = "iParentNodeId=" + parentNodeId
					+ " and vWorkspaceId='" + workSpaceId + "' and iNodeNo<> 1";

			rs = dataTable.getDataSet(con, Fields, "workspaceNodeDetail",
					Where, "iNodeNo");
			while (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
				dto.setRemark(rs.getString("vRemark"));
				childNodesDtl.addElement(dto);
				dto = null;
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return childNodesDtl;
	}

	public Vector<DTOWorkSpaceNodeDetail> getChildNodeByParentForPublishForM1(
			int parentNodeId, String workSpaceId) {
		Vector<DTOWorkSpaceNodeDetail> childNodesDtl = new Vector<DTOWorkSpaceNodeDetail>();
		try {
			ResultSet rs = null;
			Connection con = ConnectionManager.ds.getConnection();
			String Fields = "vWorkspaceId,iNodeId,vNodeName,vNodeDisplayName,vFolderName,cNodeTypeIndi,vRemark";
			String Where = "iParentNodeId=" + parentNodeId
					+ " and vWorkspaceId='" + workSpaceId + "' and iNodeNo= 1";

			rs = dataTable.getDataSet(con, Fields, "workspaceNodeDetail",
					Where, "iNodeNo");
			while (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
				dto.setRemark(rs.getString("vRemark"));
				childNodesDtl.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return childNodesDtl;
	}

	public Vector<DTOWorkSpaceNodeDetail> getModuleWiseChildNodeByParentForNeesPublish(
			int parentNodeId, int nodeNo, String workSpaceId) {
		Vector<DTOWorkSpaceNodeDetail> childNodesDtl = new Vector<DTOWorkSpaceNodeDetail>();
		try {
			ResultSet rs = null;
			Connection con = ConnectionManager.ds.getConnection();
			String Fields = "vWorkspaceId,iNodeId,vNodeName,vNodeDisplayName,vFolderName,cNodeTypeIndi,vRemark";
			String Where = "iParentNodeId=" + parentNodeId
					+ " and vWorkspaceId='" + workSpaceId + "' and iNodeNo="
					+ nodeNo;

			rs = dataTable.getDataSet(con, Fields, "workspaceNodeDetail",
					Where, "iNodeNo");
			while (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
				dto.setRemark(rs.getString("vRemark"));
				childNodesDtl.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return childNodesDtl;
	}

	public Vector<DTOWorkSpaceNodeDetail> getChildNodeByParentForPdfPublishForM1(
			int parentNodeId, String workSpaceId) {
		Vector<DTOWorkSpaceNodeDetail> childNodesDtl = new Vector<DTOWorkSpaceNodeDetail>();
		try {
			ResultSet rs = null;
			Connection con = ConnectionManager.ds.getConnection();
			String Fields = "vWorkspaceId,iNodeId,vNodeName,vNodeDisplayName,vFolderName,cNodeTypeIndi,vRemark";
			String Where = "iParentNodeId=" + parentNodeId
					+ " and vWorkspaceId='" + workSpaceId + "' ";

			rs = dataTable.getDataSet(con, Fields, "workspaceNodeDetail",
					Where, "iNodeNo");
			while (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
				dto.setRemark(rs.getString("vRemark"));
				childNodesDtl.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return childNodesDtl;
	}

	public Vector<DTOWorkSpaceNodeDetail> getChildNodeByParent(
			int parentNodeId, String workSpaceId) {
		Vector<DTOWorkSpaceNodeDetail> childNodesDtl = new Vector<DTOWorkSpaceNodeDetail>();
		ResultSet rs = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();

			String FieldNames = "vWorkspaceId,iNodeId,iparentnodeid,iNodeNo,vnodename,vnodedisplayname,vfoldername,cnodetypeindi,vRemark,cRequiredFlag,cCloneFlag,dModifyOn,iModifyBy";
			String Where = "vWorkspaceId = '" + workSpaceId
					+ "' and iParentNodeId =" + parentNodeId
					+ "and cStatusIndi<>'D'";
			rs = dataTable.getDataSet(con, FieldNames, "workspacenodedetail",
					Where, "iNodeNo");

			while (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setParentNodeId(rs.getInt("iparentnodeid"));
				dto.setNodeNo(rs.getInt("iNodeNo"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
				dto.setRemark(rs.getString("vRemark"));
				dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0));
				dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				childNodesDtl.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return childNodesDtl;
	}

	public Vector<DTOWorkSpaceNodeDetail> getParentNode(String wsId, int nodeId) {
		Vector<DTOWorkSpaceNodeDetail> parentNodeVector = new Vector<DTOWorkSpaceNodeDetail>();
		ResultSet rs = null;
		try {
			Connection con = ConnectionManager.ds.getConnection();

			String Where = " vWorkspaceId = '" + wsId + "' and iNodeId= "
					+ nodeId;
			rs = dataTable.getDataSet(con, "iParentNodeId, vNodeDisplayName",
					"workspacenodedetail", Where, "");
			while (rs.next()) {
				DTOWorkSpaceNodeDetail dtowsnd = new DTOWorkSpaceNodeDetail();

				dtowsnd.setParentNodeId(rs.getInt("iParentNodeID"));
				dtowsnd.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				parentNodeVector.addElement(dtowsnd);
				dtowsnd = null;
			}
			rs.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return parentNodeVector;
	}

	/*
	 * public Vector getParentNoAndNodeDisplay(DTOWorkSpaceNodeDetail dto) {
	 * Vector wsNodeDetail = new Vector(); ResultSet rs =null; try{ Connection
	 * con = ConnectionManager.ds.getConnection(); String
	 * FieldNames="iNodeId,inodeNo,vnodeDisplayname,iParentNodeId,vFolderName";
	 * 
	 * String
	 * Where="vWorkspaceId='"+dto.getWorkspaceId()+"' and iNodeNo="+dto.getNodeNo
	 * ()+" and iParentNodeId="+dto.getParentNodeId();
	 * rs=dataTable.getDataSet(con, FieldNames,"workspaceNodeDetail", Where,""
	 * );
	 * 
	 * while(rs.next()) { dto.setNodeId(rs.getInt("iNodeId")); //nodeId
	 * dto.setNodeNo(rs.getInt("iNodeNo")); //nodeNo
	 * dto.setNodeDisplayName(rs.getString
	 * ("vNodeDisplayName"));//nodeDisplayName
	 * dto.setParentNodeId(rs.getInt("iParentNodeId")); //parentNodeId
	 * dto.setFolderName(rs.getString("vFolderName")); //folderName }
	 * rs.close(); con.close();
	 * 
	 * }catch(SQLException e){ e.printStackTrace(); } return wsNodeDetail; }
	 */
	public Vector<DTOWorkSpaceNodeDetail> getAllSTFFirstNodes(String workspaceId) {
		Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
		ResultSet rs = null;
		try {
			String whr = "vWorkspaceId='" + workspaceId
					+ "' and cNodeTypeIndi='T'";
			Connection con = ConnectionManager.ds.getConnection();

			String Fields = "Distinct vWorkspaceId,iNodeId,iNodeNo,vNodeName,vNodeDisplayName,cNodeTypeIndi,iParentNodeId,vFolderName,iModifyBy";
			rs = dataTable.getDataSet(con, Fields,
					"View_CommonWorkspaceDetail", whr, "");
			while (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setNodeNo(rs.getInt("iNodeNo"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
				dto.setParentNodeId(rs.getInt("iParentNodeId"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
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

	public Vector<DTOWorkSpaceNodeDetail> getAllChildSTFNodes(
			String workspaceId, int parentId) {
		Vector<DTOWorkSpaceNodeDetail> childVector = null;

		ResultSet rs = null;
		try {

			childVector = new Vector<DTOWorkSpaceNodeDetail>();
			Connection con = ConnectionManager.ds.getConnection();

			// String
			// Fields="Distinct iNodeId,vWorkspaceId,iNodeNo,iParentNodeId,vNodeName,vNodeCategory,cNodeTypeIndi";
			// String
			// whr=" vWorkspaceId='"+workspaceId+"'"+" and iParentNodeId ="+
			// parentId;

			// Query modified by : Ashmak Shah
			String Fields = "Distinct iNodeId,vWorkspaceId,iNodeNo,iParentNodeId,"
					+ " vNodeName,vNodeDisplayName,vFolderName,vNodeCategory,cNodeTypeIndi, "
					+ " (Case When cNodeTypeIndi = 'F' then '' else vAttrName end) as vAttrName, "
					+ " (Case When cNodeTypeIndi = 'F' then '' else vAttrValue end) as vAttrValue ";

			String whr = " vWorkspaceId='"
					+ workspaceId
					+ "'"
					+ " AND iParentNodeId ="
					+ parentId
					+ " AND ((cNodeTypeIndi= 'S' AND (vattrname= 'operation' OR vattrname='')) OR cNodeTypeIndi = 'F') ";

			rs = dataTable.getDataSet(con, Fields,
					"View_CommonWorkspaceDetail", whr, "iNodeNo");

			while (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setNodeNo(rs.getInt("iNodeNo"));
				dto.setParentNodeId(rs.getInt("iparentNodeId"));
				dto.setNodeName(rs.getString("vNodename"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setStfNodeCategoryName(rs.getString("vNodeCategory"));
				dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));

				// This vAttrValue will always be 'operation' value for STF
				// child node
				dto.setAttrValue(rs.getString("vAttrValue"));

				childVector.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return childVector;
	}

	public int getParentNodeId(String wsId, int nodeId) {
		int parentNodeId = 0;
		ResultSet rs = null;

		try {
			String whr = "vWorkSpaceId ='" + wsId + "'  and inodeid = "
					+ nodeId;
			Connection con = ConnectionManager.ds.getConnection();
			rs = dataTable.getDataSet(con, "iParentNodeId",
					"workspacenodedetail", whr, "");

			while (rs.next()) {
				parentNodeId = rs.getInt("iParentNodeId");
			}
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return parentNodeId;
	}

	// use in publish logic
	public Vector<Integer> getAllNodesFromHistory(String workspaceid,
			int labelNo) {
		Vector<Integer> data = new Vector<Integer>();

		try {

			StringBuffer query = new StringBuffer();

			Connection con = ConnectionManager.ds.getConnection();
			query.append("{call Proc_WorkSpaceNode_Doc(?,?)}");
			CallableStatement proc = con.prepareCall(query.toString());
			proc.setString(1, workspaceid);
			proc.setInt(2, labelNo);

			ResultSet rs = proc.executeQuery();
			while (rs.next()) {
				data.addElement(rs.getInt("iNodeId"));
			}
			rs.close();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return data;
	}

	public Vector<DTOWorkSpaceNodeDetail> getFileDetailByWorkspaceIdAndNodeId(
			String wsId, int nodeId) {
		Vector<DTOWorkSpaceNodeDetail> data = new Vector<DTOWorkSpaceNodeDetail>();

		try {
			ResultSet rs = null;
			Connection con = ConnectionManager.ds.getConnection();

			String Fields = "Distinct vFileName,vNodeName,vPathFolderName,vNodeHistoryUserName,dModifyOnForHistory,vFileVersionId,vWorkspaceId,iNodeId,vBaseWorkFolder,HistoryTranNo,vUserDefineVersionid,vStageDesc";

			String Where = "vWorkspaceId='" + wsId + "'and vFileName<>'No File' and iNodeId=" + nodeId;

			rs = dataTable.getDataSet(con, Fields,
					"View_CommonWorkspaceDetail", Where, "vFileVersionId desc");

			while (rs.next()) {
				DTOWorkSpaceNodeDetail obj = new DTOWorkSpaceNodeDetail();

				if (!rs.getString("vFileName").equals("")) {
					
					obj.setFileName(rs.getString("vFileName"));
					obj.setNodeName(rs.getString("vNodeName"));
					obj.setFolderName(rs.getString("vPathFolderName"));
					obj.setUserName(rs.getString("vNodeHistoryUserName"));
					Timestamp dt = rs.getTimestamp("dModifyOnForHistory");
					if (!rs.wasNull()) {

						obj.setModifyOn(dt);
					} else {
						obj.setModifyOn(null);
					}
					obj.setFileVersionId(rs.getString("vFileVersionId"));
					obj.setAttrValue(null);
					obj.setUserDefineVersionId(rs
							.getString("vUserDefineVersionid"));
					obj.setEffecitveDate(null);
					obj.setTranNo(rs.getInt("HistoryTranNo"));
					obj.setWorkspaceId(rs.getString("vWorkspaceId"));
					obj.setNodeId(rs.getInt("iNodeId"));
					obj.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
					obj.setStageDesc(rs.getString("vStageDesc"));

					dt = null;
					data.addElement(obj);
					obj = null;
				}

			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public Vector<DTOWorkSpaceNodeDetail> getNodeDetailOnAttributeValue(
			String attrId, String attrValue) {

		Vector<DTOWorkSpaceNodeDetail> data = new Vector<DTOWorkSpaceNodeDetail>();
		try {

			ResultSet rs = null;

			String whr = "iAttrId=" + attrId + " and vFileName<>''";

			if (attrValue != null && !attrValue.equals("")) {
				whr = whr + " and vAttrValue like'%" + attrValue + "%'";
			}
			Connection con = ConnectionManager.ds.getConnection();
			String Fields = "Distinct vWorkspaceId,vWorkspaceDesc,iNodeId,vNodeDisplayName,vPathFolderName,vFileName,vUserName,dModifyOn,vFileVersionId,vBaseWorkFolder,HistoryTranNo,vUserDefineVersionid,vAttrValue,vProjectName";
			rs = dataTable.getDataSet(con, Fields,
					"View_CommonWorkspaceDetail", whr, "vWorkspaceId");
			while (rs.next()) {
				DTOWorkSpaceNodeDetail obj = new DTOWorkSpaceNodeDetail();

				obj.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				obj.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				obj.setFileName(rs.getString("vFileName"));
				obj.setFolderName(rs.getString("vPathFolderName"));
				obj.setFileVersionId(rs.getString("vFileVersionId"));
				obj.setTranNo(rs.getInt("HistoryTranNo"));
				obj.setUserName(rs.getString("vUserName"));
				obj.setModifyOn(rs.getTimestamp("dModifyOn"));
				obj.setWorkspaceId(rs.getString("vWorkSpaceId"));
				obj.setNodeId(rs.getInt("iNodeId"));
				obj.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				obj.setProjectName(rs.getString("vProjectName"));
				String fileName = rs.getString("vFileName");
				String wsId = rs.getString("vWorkSpaceId");

				if (fileName != null) {
					if ((!fileName.equalsIgnoreCase("null"))
							&& (fileName.length() > 0)) {
						String[] splitFileName = fileName.split("\\.");
						String fileExt = splitFileName[splitFileName.length - 1];
						obj.setFileExt(fileExt);
					}

				}

				Vector<Object[]> workSpaceData = new Vector<Object[]>();
				WorkspaceMst wsm = new WorkspaceMst();
				workSpaceData = wsm.getWorkspaceDesc(wsId);

				String baseWorkFolder;
				for (int i = 0; i < workSpaceData.size(); i++) {
					Object[] wsDetail = workSpaceData.elementAt(i);
					baseWorkFolder = (String) wsDetail[9];
					obj.setBaseworkfolder(baseWorkFolder);
				}

				data.addElement(obj);
				obj = null;
				workSpaceData = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return data;
	}

	public Vector<DTOWorkSpaceNodeDetail> getNodeDetailByAttributeIdandValue(
			String attrId, String attrValue) {
		Vector<DTOWorkSpaceNodeDetail> data = new Vector<DTOWorkSpaceNodeDetail>();
		try {

			ResultSet rs = null;
			String whr = "iAttrId=" + attrId + " ";

			if (attrValue != null && !attrValue.equals("")) {
				whr = whr + " and vAttrValue like'%" + attrValue + "%'";
			}
			Connection con = ConnectionManager.ds.getConnection();
			String Fields = "vWorkspaceId,vWorkspaceDesc,vTemplateDesc,vBaseWorkFolder,vBasePublishFolder,vLastPublishedVersion,cProjectType,cWorkspaceStatusIndi,vWorkspaceRemark,iNodeId,vNodeName,vNodeDisplayName,vFolderName,vAttrValue,vUserName,dModifyOn";
			rs = dataTable.getDataSet(con, Fields, "View_AttributeSearch", whr,
					"vWorkspaceId");
			while (rs.next()) {
				DTOWorkSpaceNodeDetail obj = new DTOWorkSpaceNodeDetail();
				obj.setWorkSpaceDesc(rs.getString("vWorkspaceDesc"));
				obj.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				obj.setFolderName(rs.getString("vFolderName"));
				obj.setAttrValue(rs.getString("vAttrValue"));
				obj.setUserName(rs.getString("vUserName"));
				obj.setModifyOn(rs.getTimestamp("dModifyOn"));
				obj.setWorkspaceId(rs.getString("vWorkSpaceId"));
				obj.setNodeId(rs.getInt("iNodeId"));
				obj.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				data.addElement(obj);
				obj = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return data;
	}

	/*
	 * Added By: Mansi Shah Description: For Automail Functionality
	 */

	public Vector<DTOWorkSpaceNodeAttrDetail> getWorkSpaceUserDetailByAttribute(
			String dateToCompare) {
		Vector<DTOWorkSpaceNodeAttrDetail> data = new Vector<DTOWorkSpaceNodeAttrDetail>();
		try {

			Connection con = ConnectionManager.ds.getConnection();

			String Fields1 = "iAttrId,vAttrName,vAttrType";
			String whr1 = "vAttrType like '%Date%'";
			ResultSet rs1 = null;
			rs1 = dataTable.getDataSet(con, Fields1, "attributemst", whr1, "");

			while (rs1.next()) {

				int attrId = rs1.getInt("iAttrId");
				ResultSet rs = null;
				String whr = "iAttrId=" + attrId + " ";
				whr = whr + "and vAttrValue='" + dateToCompare + "' ";

				String Fields = "Username,workspaceDesc,vProfileValue,vProfileSubType,iAttrId,vAttrName,vAttrValue";
				rs = dataTable.getDataSet(con, Fields,
						"View_WorkspaceUserDetailByAttribute", whr, "");
				while (rs.next()) {

					DTOWorkSpaceNodeAttrDetail obj = new DTOWorkSpaceNodeAttrDetail();

					obj.setAttrName(rs.getString("vAttrName"));
					obj.setAttrValue(rs.getString("vAttrValue"));
					obj.setUserName(rs.getString("UserName"));
					obj.setWorkSpaceDesc(rs.getString("workspaceDesc"));
					obj.setProfileValue(rs.getString("vProfileValue"));
					obj.setProfileSubType(rs.getString("vProfileSubType"));

					data.addElement(obj);
					obj = null;

				}
				rs.close();

			}
			rs1.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return data;
	}

	// //-----------

	public Vector<DTOWorkSpaceNodeDetail> getNodeDetailForActivityReport(
			String wsId) {

		Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
		try {
			String TableName = "workspaceNodeDetail";
			String where = "vWorkspaceId =" + wsId;
			String Fields = "iNodeId,iNodeNo,vNodeName,vNodeDisplayName,iParentNodeId,vFolderName,cPublishFlag,vRemark,iModifyBy,dModifyOn,cStatusIndi";
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = dataTable.getDataSet(con, Fields, TableName, where,
					"iNodeId");
			while (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
				dto.setNodeName(rs.getString("vNodeName")); // nodeName
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
				dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
				dto.setFolderName(rs.getString("vFolderName")); // folderName
				dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
				dto.setRemark(rs.getString("vRemark")); // remark
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
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

	public void deleteNodeDetail(String wsId, int nodeId, String remark) {
		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con
					.prepareCall("{Call proc_deleteWorkspaceNode(?,?,?,?)}");
			proc.setString(1, wsId);
			proc.setInt(2, nodeId);
			proc.setString(3,remark);
			proc.setInt(4,userId);
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean isNodeExtendable(String wsId, int nodeId) {
		boolean flag = false;

		Vector<DTOWorkSpaceNodeDetail> nodeDetail = getNodeDetail(wsId, nodeId);
		DTOWorkSpaceNodeDetail nodeDetailDTO = new DTOWorkSpaceNodeDetail();
		if (nodeDetail.size() > 0) {
			nodeDetailDTO = nodeDetail.elementAt(0);
			if (nodeDetailDTO.getNodeTypeIndi() == 'E') {
				flag = true;
			} else {
				if (isLeafNodes(wsId, nodeId) == 0) { // parent Node
					flag = false;
				} else {
					// If node is a leaf node then we can extend the node but if
					// the leaf node is a
					// extended node then we cannot extend the node again.
					Vector<DTOWorkSpaceNodeDetail> parentNodeDetail = getNodeDetail(
							wsId, nodeDetailDTO.getParentNodeId());
					nodeDetailDTO = parentNodeDetail.elementAt(0);

					if (nodeDetailDTO.getNodeTypeIndi() == 'E') {
						flag = false;
					} else {
						flag = true;
					}
				}
			}
		}

		return flag;
	}

	public void UpdateDisplayNameForPaperSubmission(String WorkspaceId,
			int NodeId) {
		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con
					.prepareCall("{call Proc_Update_NodeDetailForPaperSubmission(?,?)}");
			proc.setString(1, WorkspaceId);
			proc.setInt(2, NodeId);
			proc.execute();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Vector<Integer> getAllNodesFromHistoryForRevisedSubmission(
			String workspaceid, int labelNo) {
		Vector<Integer> data = new Vector<Integer>();

		try {

			StringBuffer query = new StringBuffer();

			Connection con = ConnectionManager.ds.getConnection();
			query
					.append("{call Proc_WorkSpaceNodeForRevisedSubmission_Doc(?,?)}");
			CallableStatement proc = con.prepareCall(query.toString());
			proc.setString(1, workspaceid);
			proc.setInt(2, labelNo);

			ResultSet rs = proc.executeQuery();
			while (rs.next()) {
				data.addElement(rs.getInt("iNodeId"));
			}
			rs.close();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return data;
	}

	public Vector<DTOWorkSpaceNodeDetail> getNodeForRevisedSubmission(
			String workspaceid, int labelNo) {
		Vector<DTOWorkSpaceNodeDetail> data = new Vector<DTOWorkSpaceNodeDetail>();

		try {

			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();

			Connection con = ConnectionManager.ds.getConnection();

			CallableStatement proc = con
					.prepareCall("{ Call Proc_SubmissionNodeDtl(?,?)}");
			/*
			 * CallableStatement proc =
			 * con.prepareCall("{call Proc_WorkSpaceNodeForRevisedSubmission(?,?)}"
			 * ); CallableStatement proc =
			 * con.prepareCall("{call Proc_SubmissionNodeDtl('0050','10')}");
			 */

			proc.setString(1, workspaceid);
			proc.setInt(2, labelNo);
			ResultSet rs = proc.executeQuery();
			while (rs.next()) {
				dto = new DTOWorkSpaceNodeDetail();
				dto.setNodeId(rs.getInt("iNodeId"));
				data.addElement(dto);
				dto = null;
			}
			rs.close();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return data;
	}

	/*
	 * public void updateWorkspaceNodeDetail(String wsId,int
	 * nodeId,Map<String,String> fieldsWithValue) { try { Connection con =
	 * conMgr.ds.getConnection(); boolean commaRequired = false; String sql =
	 * "";
	 * 
	 * sql = " UPDATE WorkspaceNodeDetail "; sql +=" SET ";
	 * 
	 * for (Iterator<Entry<String,String>> iterator =
	 * fieldsWithValue.entrySet().iterator(); iterator.hasNext();) {
	 * 
	 * Entry<String,String> fieldValuePair = iterator.next(); if(commaRequired){
	 * sql += ", "; } sql +=
	 * fieldValuePair.getKey()+" = '"+fieldValuePair.getValue()+"' ";
	 * commaRequired = true; }
	 * 
	 * sql += " WHERE vworkspaceId = '"+wsId+"' AND iNodeId = "+nodeId;
	 * Statement stmt = con.createStatement(); stmt.executeUpdate(sql);
	 * stmt.close(); con.close();
	 * 
	 * }catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * }
	 */

	/* Used in manual mode publish */
	public Vector<Integer> getWorkspaceTreeNodesForLeafs(String workspaceId,
			int[] leafIds) {

		Vector<Integer> data = new Vector<Integer>();

		String nodeIdsCSV = "";

		for (int i = 0; i < leafIds.length; i++) {
			int nodeId = leafIds[i];

			if (i != 0)
				nodeIdsCSV += "," + nodeId;
			else {
				nodeIdsCSV += nodeId;
			}
		}
		// System.out.println("NodeID: "+nodeIdsCSV);

		try {
			
			System.out.println("Nodes-> "+nodeIdsCSV);
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con
					.prepareCall("{call Proc_WorkSpaceNodeForPdfSubmission_Doc(?,?)}");
			proc.setString(1, workspaceId);
			proc.setString(2, nodeIdsCSV);

			ResultSet rs = proc.executeQuery();
			while (rs.next()) {
				data.addElement(rs.getInt("iNodeId"));
			}
			rs.close();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return data;
	}

	/* Used in pdf publish */
	public Vector<Integer> getSelectedNodeDetailsForPdfPublish(
			String workspaceId, int[] leafIds) {

		Vector<Integer> data = new Vector<Integer>();

		String nodeIdsCSV = "";

		for (int i = 0; i < leafIds.length; i++) {
			int nodeId = leafIds[i];

			if (i != 0)
				nodeIdsCSV += "," + nodeId;
			else {
				nodeIdsCSV += nodeId;
			}
		}
		System.out.println("NodeID: " + nodeIdsCSV);

		try {
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con
					.prepareCall("{call Proc_WorkSpaceTreeNodesForLeafs(?,?)}");

			System.out.println("WorkspaceId-->" + workspaceId);
			proc.setString(1, workspaceId);
			proc.setString(2, nodeIdsCSV);

			ResultSet rs = proc.executeQuery();
			while (rs.next()) {
				data.addElement(rs.getInt("iNodeId"));
			}
			rs.close();
			proc.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return data;
	}

	public ArrayList<DTOWorkSpaceNodeDetail> updateNodeNo(String wsId,
			int nodeId, int setNodeNo) {
		int parentNodeId = -1, originalNodeNo = -1;
		ArrayList<DTOWorkSpaceNodeDetail> wsNodeDetail = new ArrayList<DTOWorkSpaceNodeDetail>();
		Connection con = null;
		Statement stmt = null;

		try {
			con = ConnectionManager.ds.getConnection();
			stmt = con.createStatement();

			ResultSet rs = dataTable.getDataSet(con, "iNodeNo,iParentNodeId",
					"WorkspaceNodeDetail", " vWorkspaceId = '" + wsId
							+ "' AND iNodeId = " + nodeId, "");
			if (rs.next()) {
				parentNodeId = rs.getInt("iParentNodeId");
				originalNodeNo = rs.getInt("iNodeNo");
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			rs = dataTable.getDataSet(con, "iNodeId,iNodeNo",
					"WorkspaceNodeDetail", " vWorkspaceId = '" + wsId
							+ "' AND iParentNodeId = " + parentNodeId,
					"iNodeNo");
			while (rs.next()) {

				int currNodeId = rs.getInt("iNodeId");
				int currNodeNo = rs.getInt("iNodeNo");
				if (originalNodeNo > setNodeNo) {
					if (setNodeNo <= currNodeNo && currNodeNo < originalNodeNo) {
						// Increment of nodeNo by one
						currNodeNo = currNodeNo + 1;
						stmt
								.addBatch("UPDATE WorkspaceNodeDetail SET iNodeNo = "
										+ currNodeNo
										+ " WHERE vWorkspaceId = '"
										+ wsId
										+ "' AND iNodeId = " + currNodeId + " ");
					}
				} else if (originalNodeNo < setNodeNo) {
					if (originalNodeNo < currNodeNo && currNodeNo <= setNodeNo) {
						// Decrement of nodeNo by one
						currNodeNo = currNodeNo - 1;
						stmt
								.addBatch("UPDATE WorkspaceNodeDetail SET iNodeNo = "
										+ currNodeNo
										+ " WHERE vWorkspaceId = '"
										+ wsId
										+ "' AND iNodeId = " + currNodeId + " ");
					}
				}
			}

			stmt.addBatch("UPDATE WorkspaceNodeDetail SET iNodeNo = "
					+ setNodeNo + " WHERE vWorkspaceId = '" + wsId
					+ "' AND iNodeId = " + nodeId + " ");
			stmt.executeBatch();

			stmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return wsNodeDetail;
	}

	public int[] updateNodeNo(Vector<DTOWorkSpaceNodeDetail> nodeList) {
		Connection con = null;
		Statement stmt = null;
		int[] ret = null;
		try {
			con = ConnectionManager.ds.getConnection();
			stmt = con.createStatement();
			for (int i = 0; i < nodeList.size(); i++) {
				String query = "UPDATE WorkspaceNodeDetail SET " + "iNodeNo = "
						+ nodeList.get(i).getNodeNo()
						+ " WHERE vWorkspaceId = '"
						+ nodeList.get(i).getWorkspaceId() + "' AND iNodeId = "
						+ nodeList.get(i).getNodeId() + " ";
				stmt.addBatch(query);
			}
			ret = stmt.executeBatch();
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
			}
		}
		return ret;
	}

	public ArrayList<DTOWorkSpaceNodeDetail> getAllChildNodeForFileuploading(
			String wsID) {
		Connection con = null;
		ResultSet rs = null;
		ArrayList<DTOWorkSpaceNodeDetail> wsNodeDtl = new ArrayList<DTOWorkSpaceNodeDetail>();
		try {
			con = ConnectionManager.ds.getConnection();

			String Where = "vWorkspaceId = '"
					+ wsID
					+ "' and  crequiredflag in ('n','f')  and inodeid in "
					+ "(select inodeid from workspacenodedetail where vworkspaceid = '"
					+ wsID
					+ "' and crequiredflag in ('n','f') "
					+ "and inodeid not in "
					+ "(select iparentnodeid from workspacenodedetail where vworkspaceid = '"
					+ wsID + "' and  crequiredflag in ('n','f'))" + ")";

			rs = dataTable.getDataSet(con, "*", "workspaceNodeDetail", Where,
					"");

			while (rs.next()) {
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
				dto.setNodeName(rs.getString("vNodeName")); // nodeName
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
				dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
				dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
				dto.setFolderName(rs.getString("vFolderName")); // folderName
				dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
				dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
				dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
				dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
				dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
				dto.setRemark(rs.getString("vRemark")); // remark
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
				dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
				wsNodeDtl.add(dto);
				dto = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return wsNodeDtl;

	}

	public ArrayList<DTOWorkSpaceNodeDetail> getAllParentsNodes(String wsId,
			int nodeId) {
		ArrayList<DTOWorkSpaceNodeDetail> wsNodeDtl = new ArrayList<DTOWorkSpaceNodeDetail>();
		try {
			Connection con = ConnectionManager.ds.getConnection();
			String nodeDtlSql = "SELECT * FROM workspaceNodeDetail WHERE vWorkspaceId = '"
					+ wsId + "' and iNodeid = ? ";
			PreparedStatement nodeDtlStmt = con.prepareStatement(nodeDtlSql);
			nodeDtlStmt.setInt(1, nodeId);
			wsNodeDtl = getParentsNodes(nodeDtlStmt, wsNodeDtl);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Collections.reverse(wsNodeDtl);
		return wsNodeDtl;
	}

	public ArrayList<DTOWorkSpaceNodeDetail> getParentsNodes(
			PreparedStatement nodeDtlStmt,
			ArrayList<DTOWorkSpaceNodeDetail> wsNodeDtl) throws SQLException {
		ResultSet rs = nodeDtlStmt.executeQuery();
		DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();

		if (rs.next()) {
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
			dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
			dto.setFolderName(rs.getString("vFolderName")); // folderName
			dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
			dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
			dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
			dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
			dto.setRemark(rs.getString("vRemark")); // remark
			dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
			dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
			wsNodeDtl.add(dto);
			rs.close();
			nodeDtlStmt.setInt(1, dto.getParentNodeId());
			getParentsNodes(nodeDtlStmt, wsNodeDtl);
		}

		return wsNodeDtl;
	}

	public Integer getTotalLeafNodes(ArrayList<String> wsIDs) {
		Integer totalLeafNodes = 0;
		Connection con = null;
		ResultSet rs = null;
		try {

			con = ConnectionManager.ds.getConnection();
			String Where = "";
			String from = "";
			StringBuffer whrBuffer = new StringBuffer();

			for (int i = 0; i < wsIDs.size(); i++) {
				if (i != 0) {
					whrBuffer.append(",");
				}
				whrBuffer.append("'" + wsIDs.get(i) + "'");

			}

			Where += " vWorkspaceId in (" + whrBuffer + ") ";
			from += Where + " and cStatusIndi<>'D' and ";

			from += " crequiredflag in ('n','f') and inodeid not in ( select distinct iparentnodeid from workspacenodedetail where  crequiredflag in ('n','f') ";
			from += " and " + Where;
			from += " ) and inodeid not in(select iNodeid from workspacenodevoid where"+Where+")";
			rs = dataTable.getDataSet(con, "count(*) as count",
					"workspaceNodeDetail ", from, "");
			if (rs.next()) {
				totalLeafNodes = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return totalLeafNodes;
	}

	public boolean reArangeNodeNumbers(String workspaceId, String nodes) {

		try {
			Connection con = ConnectionManager.ds.getConnection();

			CallableStatement cs = con
					.prepareCall("{call Proc_ReArrangeNodeNumber(?,?)}");
			cs.setString(1, workspaceId);
			cs.setString(2, nodes);
			cs.execute();

			cs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		System.out.println("Rearrang");
		return true;
	}

	public Vector<DTOWorkSpaceNodeDetail> getWorkspacenodeHistory(
			String workspaceId, String nodeId, String attrName) {
		Vector<DTOWorkSpaceNodeDetail> dto = new Vector<DTOWorkSpaceNodeDetail>();

		try {
			ResultSet rs = null;
			Connection con = ConnectionManager.ds.getConnection();

			String Fields = "*";
			String blank = "";
			String Where = "vWorkspaceId='" + workspaceId + "' and iNodeId="
					+ nodeId + " and attrName='" + attrName
					+ "' and vCurrentSeqNumber!=''";

			rs = dataTable.getDataSet(con, Fields,
					"view_getSubmittedNodeOperationDetails", Where,
					"vCurrentSeqNumber");

			while (rs.next()) {
				DTOWorkSpaceNodeDetail obj = new DTOWorkSpaceNodeDetail();

				obj.setWorkspaceId(rs.getString("vWorkspaceId"));
				obj.setNodeId(rs.getInt("iNodeId"));
				obj.setBaseworkfolder(rs.getString("vBaseWorkFolder"));
				obj.setFileName(rs.getString("vFileName"));
				obj.setFolderName(rs.getString("vFolderName"));
				obj.setSequenceno(rs.getString("vCurrentSeqNumber"));
				obj.setAttrValue(rs.getString("attrValue"));

				dto.addElement(obj);
				obj = null;
			}

			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Getting info");
		return dto;
	}

	public static void main(String argv[]) {
		new ConnectionManager(new Configuration());
		WorkspaceNodeDetail ws = new WorkspaceNodeDetail();
		ArrayList<DTOWorkSpaceNodeDetail> parentNodeList = ws
				.getAllParentsNodes("0022", 12);
		String nodeIdStr = "";
		for (int i = 0; i < parentNodeList.size(); i++) {
			DTOWorkSpaceNodeDetail dto = parentNodeList.get(i);
			nodeIdStr = nodeIdStr + dto.getParentNodeId() + "/";
		}
		nodeIdStr = nodeIdStr.substring(0, nodeIdStr.length() - 1);
		System.out.println(nodeIdStr);
	}

	public ArrayList<ArrayList<String>> getReleasedDocIdDetails(
			String workspaceid, int parentNodeId) {
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		ArrayList<String> tempList = null;
		Connection con = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.ds.getConnection();
			proc = con.prepareCall("{ Call Proc_GetReleasedDocIdDetails(?,?)}");

			proc.setString(1, workspaceid);
			proc.setInt(2, parentNodeId);
			rs = proc.executeQuery();
			while (rs.next()) {
				if (rs.getString(1).matches("^#.*#$"))// Compare With Values
				// Like '#PROJ#'
				{
					if (tempList != null)// Skip for first iteration
					{
						data.add(tempList);// Add tempLists to Main List
					}
					tempList = new ArrayList<String>();// Create new tempList
				}
				tempList.add(rs.getString(1));// Add values to tempList
			}
			data.add(tempList);// Add Last tempList to Main List
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (proc != null)
					proc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return data;
	}

	public int getIdFromNodeName(int parentId, String nodeName,
			String workspaceId) {
		Connection con;
		int nodeId = 0;
		try {
			con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;

			if (parentId != 0) {
				rs = dataTable.getDataSet(con, "iNodeId",
						"workspacenodedetail", "vWorkSpaceId = '" + workspaceId
								+ "' and iParentNodeId = '" + parentId
								+ "' and vNodeName = '" + nodeName + "'", "");
			}

			else {
				rs = dataTable.getDataSet(con, "iNodeId",
						"workspacenodedetail", "vWorkSpaceId = '" + workspaceId
								+ "' and vNodeName = '" + nodeName + "'", "");
			}

			while (rs.next()) {
				nodeId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nodeId;
	}

	public ArrayList<String> getParentIdFromNodeNameAndWorkspaceId(
			String vWorkspaceId, String nodeName) {
		Connection con;
		ArrayList<String> parentIds = new ArrayList<String>();

		try {
			con = ConnectionManager.ds.getConnection();

			ResultSet rs = dataTable.getDataSet(con, "inodeid",
					"workspacenodedetail", "vWorkSpaceId = '" + vWorkspaceId
							+ "' and vNodeName = 'm3-2-p-drug-product'", "");
			while (rs.next()) {
				parentIds.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parentIds;
	}
	public Vector<Integer> getNodeIdFromNodeName(
			String vWorkspaceId, String nodeName) {
		Connection con;
		Vector<Integer> nodeIds = new Vector<Integer>();

		try {
			con = ConnectionManager.ds.getConnection();

			ResultSet rs = dataTable.getDataSet(con, "inodeid",
					"workspacenodedetail", "vWorkSpaceId = '" + vWorkspaceId + "' and vNodeName ='"+nodeName+"'", "");
							
			while (rs.next()) {
				nodeIds.add(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeIds;
	}
	
	public int getParentNodeIdFromNodeName (
			String vWorkspaceId, String nodeName) {
		Connection con;
		int ParentNodeid=0;
		try {
			con = ConnectionManager.ds.getConnection();
			
			ResultSet rs = dataTable.getDataSet(con, "min(iParentNodeId) as iParentNodeId ",
					"workspacenodedetail", "vWorkSpaceId = '" + vWorkspaceId
							+ "' and vNodeName ='"+nodeName+"'", "");
			while (rs.next()) {
				ParentNodeid=rs.getInt("iParentNodeId");
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ParentNodeid;
	}

	public int getReferenceId(String vWorkspaceId, String nodeName,
			String parentId) {
		Connection con;
		int refId = 0;
		try {
			con = ConnectionManager.ds.getConnection();

			ResultSet rs = dataTable.getDataSet(con, "iNodeId",
					"workspacenodedetail", "vWorkSpaceId = '" + vWorkspaceId
							+ "' and  vNodeName ='" + nodeName
							+ "' and iParentNodeId = " + parentId, "");
			while (rs.next()) {
				refId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return refId;
	}

	public int getRepeatNodeId(String vWorkspaceId, String nodeToRepeat) {
		Connection con;
		int nodeId = 0;
		try {
			con = ConnectionManager.ds.getConnection();

			ResultSet rs = dataTable.getDataSet(con, "inodeid",
					"workspacenodedetail", "vWorkspaceId = '" + vWorkspaceId
							+ "' and vNodeName = '" + nodeToRepeat
							+ "' and vFolderName not like '%.pdf'", "");
			while (rs.next()) {
				nodeId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nodeId;
	}
	public Vector<Object []>  getWorkspaceNodeDetails(String wsId) {

		Vector<Object []> nodeInfo = new Vector<Object []>();
		Connection con = null;
		ResultSet rs = null;
		try  
		{	String TableName = "workspaceNodeDetail";
			String where = "vWorkspaceId =" + wsId;
			String Fields = "iNodeId,iNodeNo,vNodeName,vNodeDisplayName,iParentNodeId,vFolderName,cNodeTypeIndi";
			 con = ConnectionManager.ds.getConnection();
			 rs = dataTable.getDataSet(con, Fields, TableName, where,
					"iParentNodeId,iNodeNo");
			while (rs.next()) {
				Object [] record =  
				{
					new Integer(rs.getInt("iNodeId")),    	// Node id
					rs.getString("vNodeDisplayName"),				// Display Name
					new Integer(rs.getInt("iParentNodeId")),		// Parent Id
					rs.getString("vFolderName"),// Folder Name	
					rs.getString("vNodeName"),
					new Integer(rs.getInt("iNodeNo")),// inodeno
					rs.getString("cNodeTypeIndi").toString(),
				};
				nodeInfo.addElement(record);		
				//dto = null;
			}
			rs.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nodeInfo;
	}

	public int getNodeMaxIdFromNodeName(
			String vWorkspaceId, String nodeName) {
		Connection con;
		int nodeId = 0 ;

		try {
			con = ConnectionManager.ds.getConnection();

			ResultSet rs = dataTable.getDataSet(con, "iNodeid",	"workspacenodedetail", "vWorkSpaceId = '" + vWorkspaceId 
							+ "' and vNodeDisplayName ='"+nodeName+"'", "");
							
			while (rs.next()) {
				nodeId=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeId;
	}
	public DTOWorkSpaceNodeDetail getWorkspaceNodeDetail(String wsId, int nodeId) {

		DTOWorkSpaceNodeDetail dto = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			con = ConnectionManager.ds.getConnection();

			String Where = "vWorkspaceId = '" + wsId + "' and iNodeId= "
					+ nodeId;
			rs = dataTable.getDataSet(con, "*", "workspaceNodeDetail", Where,
					"");

			if (rs.next()) {
				dto = new DTOWorkSpaceNodeDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
				dto.setNodeId(rs.getInt("iNodeId")); // nodeId
				dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
				dto.setNodeName(rs.getString("vNodeName")); // nodeName
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
				dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
				dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
				dto.setFolderName(rs.getString("vFolderName")); // folderName
				dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
				dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
				dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
				dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
				dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
				dto.setRemark(rs.getString("vRemark")); // remark
				dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
				dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
				dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
				dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
			
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

		return dto;
	}
	public Vector<DTOWorkSpaceNodeDetail> isLeafParentForCSV(String wsId, int nodeId) {
		ResultSet rs = null;
		Vector<DTOWorkSpaceNodeDetail> nodeInfo = new Vector<DTOWorkSpaceNodeDetail>();
		Connection con = null;
		try {
			con=ConnectionManager.ds.getConnection();
			String Fields="inodeid, vFolderName";
			String Where="vWorkspaceId='"+wsId+"' and cStatusIndi<>'D' and  crequiredflag in ('n','f') and inodeid not in "
					+ "(select distinct iparentnodeid from workspacenodedetail where  crequiredflag in ('n','f')  and  vWorkspaceId ='"+wsId+"')"
							+ "and iparentnodeid="+nodeId;
			rs=dataTable.getDataSet(con,Fields,"workspaceNodeDetail" , Where,"" ); 
			while (rs.next())    
			{
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setFolderName(rs.getString("vFolderName"));
				nodeInfo.addElement(dto);	
			}
			rs.close();
			con.close();
		} catch(Exception e)  {
			e.printStackTrace();
		}
		
		return nodeInfo;		
	}
	public Vector<DTOWorkSpaceNodeDetail> isLeafParentForAttribute(String wsId, int nodeId,int userCode, int userGroupCode) {
		ResultSet rs = null;
		Vector<DTOWorkSpaceNodeDetail> nodeInfo = new Vector<DTOWorkSpaceNodeDetail>();
		Connection con = null;
		try {
			con=ConnectionManager.ds.getConnection();
			String Fields="distinct vworkspaceid, inodeid, iParentNodeId, vnodename, vnodedisplayname, vFolderName";
			String Where="vWorkspaceId='"+wsId+"' and cStatusIndi<>'D' and  crequiredflag in ('n','f') and inodeid not in "
					+ "(select distinct iparentnodeid from workspacenodedetail where  crequiredflag in ('n','f')  and  vWorkspaceId ='"+wsId+"')"
							+ "and iparentnodeid="+nodeId+" and iUserCode="+userCode+" and iusergroupcode="+userGroupCode;
			rs=dataTable.getDataSet(con,Fields,"View_CommonWorkspaceDetail" , Where,"" );  
			while (rs.next())    
			{
				DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setFolderName(rs.getString("vFolderName"));
				nodeInfo.addElement(dto);	
			}
			rs.close();
			con.close();
		} catch(Exception e)  {
			e.printStackTrace();
		}
		
		return nodeInfo;		
	}
	
	public boolean getfileUploadSeqDetail(String wsId, int nodeId, int parentNodeId) {
		
		ResultSet rs = null;
		boolean fileUploadSeqFlag=true;
		Connection con = null;
		try {
			con=ConnectionManager.ds.getConnection();
			
			/*String Fields="vWorkspaceId,iNodeId,iNodeNo,vNodeName,vFolderName,iParentNodeId,NoOfApprover,NoOfApproved";
			
			String From="workspacenodedetail inner join view_GetApprovedFilesDetailForProject on" 
					+" view_GetApprovedFilesDetailForProject.WorkspaceId = workspacenodedetail.vWorkspaceId "
					+" and view_GetApprovedFilesDetailForProject.NodeId = workspacenodedetail.iNodeId";
			
			String Where="vWorkspaceId='"+wsId+"' and iParentnodeId <="+parentNodeId+" and cStatusIndi<>'D' and crequiredflag in ('n','f')"
					+ " and inodeid not in (select distinct iparentnodeid from workspacenodedetail where  crequiredflag in ('n','f')"
										+" and  vWorkspaceId ='"+wsId+"')"
					+" and inodeid not in (select inodeId from workspacenodedetail where vworkspaceid='"+wsId+"'"
										+" and iparentnodeid="+parentNodeId+" and iNodeNo >=(select iNodeNo from workspacenodedetail"
										+" where vworkspaceid='"+wsId+"' and iparentnodeid="+parentNodeId+" and iNodeId="+nodeId+"))"
					+ " and (NoOfApprover <> NoOfApproved or NoOfApprover = 0)";
			
			rs=dataTable.getDataSet(con,Fields,From , Where,"" );*/
			String Fields="ptpn.vWorkspaceId,ptpn.iNodeId,ptpn.iNodeNo,ptpn.vNodeName,ptpn.vFolderName,ptpn.iParentNodeId,fadt.NoOfApprover,fadt.NoOfApproved";
			
			/*String From="Proc_getProjectTrackingeCSVParentNodes('"+wsId+"',"+nodeId+") as ptpn"+
						" inner join view_GetApprovedFilesDetailForProject as fadt on fadt.WorkspaceId = ptpn.vWorkspaceId"+ 
						" and fadt.NodeId = ptpn.iNodeId";*/
			
			String From="Proc_getProjectTrackingeCSVChildNodesHours('"+wsId+"') as ptpn"+
					" inner join view_GetApprovedFilesDetailForProject as fadt on fadt.WorkspaceId = ptpn.vWorkspaceId"+ 
					" and fadt.NodeId = ptpn.iNodeId";
			
			String Where="vWorkspaceId='"+wsId+"' AND nRID<(select nRID from Proc_getProjectTrackingeCSVChildNodesHours('"+wsId+"')"
					+ " where inodeid="+nodeId+")and (fadt.NoOfApprover <> fadt.NoOfApproved or fadt.NoOfApprover = 0)";
			
			
				rs=dataTable.getDataSet(con,Fields,From , Where,"ptpn.nRID" );
			while (rs.next())    
			{
				fileUploadSeqFlag=false;
			}
			rs.close();
			con.close();
		} catch(Exception e)  {
			e.printStackTrace();
		}
		
		
		return fileUploadSeqFlag;		
	}
public Vector<DTOWorkSpaceNodeDetail> getFileUploadSeqForAll(String wsId, int nodeId, int parentNodeId) {
	
	
	ResultSet rs = null;
	Connection con = null;
			
	Vector<DTOWorkSpaceNodeDetail> fileUploadSeq = new Vector<DTOWorkSpaceNodeDetail>();
	String Fields="";
	try {
		con=ConnectionManager.ds.getConnection();
		
		Fields="ptpn.vWorkspaceId,ptpn.iNodeId,ptpn.iNodeNo,ptpn.vNodeName,ptpn.vFolderName,ptpn.iParentNodeId,fadt.NoOfApprover,fadt.NoOfApproved";
		
		String From="Proc_getProjectTrackingeCSVParentNodes('"+wsId+"',"+nodeId+") as ptpn"+
					" inner join view_GetApprovedFilesDetailForProject as fadt on fadt.WorkspaceId = ptpn.vWorkspaceId"+ 
					" and fadt.NodeId = ptpn.iNodeId";
		
		String Where="vWorkspaceId='"+wsId+"' and (fadt.NoOfApprover <> fadt.NoOfApproved or fadt.NoOfApprover = 0)";
		
		
			rs=dataTable.getDataSet(con,Fields,From , Where,"ptpn.nRID" );
		
		while (rs.next())    
		{
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setNodeId(rs.getInt("iNodeId"));
			
			fileUploadSeq.addElement(dto);	
		}
		rs.close();
		con.close();
	} catch(Exception e)  {
		e.printStackTrace();
	}
	
	return fileUploadSeq;		
}
public Vector<DTOWorkSpaceNodeDetail> getFileUploadSeqForSingle(String wsId, int nodeId, int parentNodeId) {
	
	ResultSet rs = null;
	Connection con = null;
	int noOfApprover,noOfApproved;	
	Vector<DTOWorkSpaceNodeDetail> fileUploadSeq = new Vector<DTOWorkSpaceNodeDetail>();
	String Fields="";
	try {
		con=ConnectionManager.ds.getConnection();
		
			Fields="Top 1 vWorkspaceId,iNodeId,iNodeNo,vNodeName,vFolderName,iParentNodeId,NoOfApprover,NoOfApproved";
		
			String From="workspacenodedetail inner join view_GetApprovedFilesDetailForProject on" 
				+" view_GetApprovedFilesDetailForProject.WorkspaceId = workspacenodedetail.vWorkspaceId "
				+" and view_GetApprovedFilesDetailForProject.NodeId = workspacenodedetail.iNodeId";
		
			String Where="vWorkspaceId='"+wsId+"' and iParentnodeId <="+parentNodeId+" and cStatusIndi<>'D' and crequiredflag in ('n','f')"
						+ " and inodeid not in (select distinct iparentnodeid from workspacenodedetail where  crequiredflag in ('n','f')"
						+" and  vWorkspaceId ='"+wsId+"')"
						+" and inodeid not in (select inodeId from workspacenodedetail where vworkspaceid='"+wsId+"'"
						+" and iparentnodeid="+parentNodeId+" and iNodeNo >=(select iNodeNo from workspacenodedetail"
						+" where vworkspaceid='"+wsId+"' and iparentnodeid="+parentNodeId+" and iNodeId="+nodeId+"))";
		
			rs=dataTable.getDataSet(con,Fields,From , Where,"iParentNodeId Desc" ); 
		
		while (rs.next())    
		{
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setNoOfApprover(rs.getInt("NoOfApprover"));
			dto.setNoOfApproved(rs.getInt("NoOfApproved"));
			noOfApprover = dto.getNoOfApprover();
			noOfApproved = dto.getNoOfApproved();
		 if(noOfApprover==0){
			 fileUploadSeq.addElement(dto);
		 }
		 else if(noOfApprover>0 && noOfApprover!=noOfApproved )
			fileUploadSeq.addElement(dto);	
		}
		rs.close();
		con.close();
	} catch(Exception e)  {
		e.printStackTrace();
	}
	
	return fileUploadSeq;		
}

public Vector<DTOWorkSpaceNodeDetail> getDeviationFile(String wsId, int nodeId){
	
	Vector<DTOWorkSpaceNodeDetail> data = new Vector<DTOWorkSpaceNodeDetail>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = null;
		
		String whr = " vWorkspaceId = '" + wsId + "' AND iNodeId = "+ nodeId;
		
		rs = dataTable.getDataSet(con,"*","workspacenodedeviation", whr,"");
		while (rs.next()) {	
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setRefNodeId(rs.getString("vRefNodeId"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); 
			data.add(dto);
			dto =null;
			
		}
		rs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return data;
}
public void insertWSNodeDeviation(DTOWorkSpaceNodeDetail dto) {
	
	try {
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call Insert_workspacenodeDeviation(?,?,?,?,?,?)}");
		proc.setString(1, dto.getWorkspaceId());
		proc.setInt(2, dto.getNodeId());
		proc.setString(3, dto.getRefNodeId());
		proc.setString(4, dto.getRemark());
		proc.setInt(5, dto.getModifyBy());
		proc.setString(6, Character.toString(dto.getStatusIndi()));
		proc.execute();
		proc.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
public Vector<DTOWorkSpaceNodeDetail> getDeviationFileDetail(String wsId) {

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	ResultSet rs1 = null;
	Connection con = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	
	try {
		con = ConnectionManager.ds.getConnection();

		String where = "vWorkspaceId = '" + wsId + "'";
		rs = dataTable.getDataSet(con, "*", "View_WorkSpaceDeviationNodeDetailHistory", where,"");

		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setRefNodeId(rs.getString("vRefNodeId")); // Ref NodeId
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			dto.setFolderName(rs.getString("vFolderName")); // folderName
			dto.setRemark(rs.getString("vRemark")); // remark
			dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
			dto.setUserName(rs.getString("vUserName")); // UserName 
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
		if(countryCode.equalsIgnoreCase("IND")){
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
		}
		else{
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
			dto.setESTDateTime(time.get(1));
		}
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
			
			Vector <DTOWorkSpaceNodeDetail> refNodeIdFileName = new Vector<DTOWorkSpaceNodeDetail>();
			String where1 = "vWorkspaceId = '" + wsId + "' and iNodeid in ("+dto.getRefNodeId()+")";
			rs1 = dataTable.getDataSet(con, "*", "WorkspaceNodeDetail", where1,"");
			
			while (rs1.next()) {
				DTOWorkSpaceNodeDetail data = new DTOWorkSpaceNodeDetail();
				data.setNodeDisplayName(rs1.getString("vNodeName"));
				data.setFileName(rs1.getString("vFolderName"));
				refNodeIdFileName.add(data);
				data=null;
			}
			dto.setRefNodeIdFileName(refNodeIdFileName);
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
public int isLeafParent(String wsId, int nodeId) {
	ResultSet rs = null;
	Vector<DTOWorkSpaceNodeDetail> nodeInfo = new Vector<DTOWorkSpaceNodeDetail>();
	try {
		Connection con = ConnectionManager.ds.getConnection();
		String Fields="inodeid, vFolderName";
		String Where="vWorkspaceId='"+wsId+"' and cStatusIndi<>'D' and  crequiredflag in ('n','f') and inodeid not in "
				+ "(select distinct iparentnodeid from workspacenodedetail where  crequiredflag in ('n','f')  and  vWorkspaceId ='"+wsId+"')"
						+ "and iparentnodeid="+nodeId;
		rs=dataTable.getDataSet(con,Fields,"workspaceNodeDetail" , Where,"" ); 
		while (rs.next())    
		{
			
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setFolderName(rs.getString("vFolderName"));
			nodeInfo.addElement(dto);	
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	if (nodeInfo.size() >= 1) {
		return 0; // false //parent node
	} else {
		return 1; // true //leaf node
	}
}

public Vector<DTOWorkSpaceNodeDetail> getWSDeviationDetail(String wsId,String nodeIds) {

	Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	Connection con = null;
	
	try {
		con = ConnectionManager.ds.getConnection();
		String where = "WorkspaceId = '" + wsId + "' and Nodeid in ("+nodeIds+")";
		rs = dataTable.getDataSet(con, "*", "view_DeviationPendingFiles", where,"ParentnodeId");

		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("WorkspaceId"));
			dto.setNodeId(rs.getInt("NodeId"));
			dto.setNodeName(rs.getString("NodeName"));
			dto.setFolderName(rs.getString("FolderName"));
			dto.setStageDesc(rs.getString("StageDesc"));
			wsNodeDetail.addElement(dto);
			dto=null;
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	} 
	
	return wsNodeDetail;
}
public Vector<DTOWorkSpaceNodeDetail>  getWorkSpaceDetailForChangeRequestGrid(String wsId,int userGroupCode,int userCode) {
	Vector<DTOWorkSpaceNodeDetail> nodeInfo = new Vector<DTOWorkSpaceNodeDetail>();
	Connection con = null;
	
	try  
	{
		DocMgmtImpl docmgmt=new DocMgmtImpl();
		con=ConnectionManager.ds.getConnection();
		String Fields="Distinct WorkspaceId,usergroupcode,usercode,NodeNo,NodeId,NodeDisplayName,NodeName,ParentNodeId,Foldername," +
		"canReadFlag,canAddFlag,canEditFlag,statusIndi,nodeTypeIndi,requiredFlag,canDeleteFlag,iformNo";
		String Where="WorkspaceId='"+wsId+"'  and Cloneflag='y' and usergroupcode="+userGroupCode+" and usercode="+userCode+" and statusIndi<>'D'";
		ResultSet rs = dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail" , Where,"NodeId desc" ); 
		while (rs.next())    
		{
			
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setNodeId(rs.getInt("NodeId"));
			dto.setNodeDisplayName(rs.getString("NodeDisplayName"));
			dto.setParentNodeId(rs.getInt("ParentNodeId"));
			dto.setFolderName(rs.getString("FolderName"));
			dto.setCanReadFlag(rs.getString("CanReadFlag"));
			dto.setCanAddFlag(rs.getString("canAddFlag"));
			dto.setCanEditFlag(rs.getString("CanEditFlag"));
			dto.setCanDeleteFlag(rs.getString("CanDeleteFlag"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
			dto.setNodeTypeIndi(rs.getString("nodeTypeIndi").charAt(0));
			dto.setRequiredFlag(rs.getString("requiredFlag").charAt(0));
			dto.setIformNo(rs.getInt("iformNo"));
			dto.setNodeNo(rs.getInt("NodeNo"));
			dto.setNodeName(rs.getString("NodeName"));
			dto.setPublishFlag(docmgmt.submittedNodeIdDetail(wsId,rs.getInt("NodeId")));
			dto.setLokedNodeFlag(docmgmt.isCheckOut(wsId, rs.getInt("NodeId"),userCode));
			nodeInfo.addElement(dto);	
		}
		rs.close();
		con.close();
	}
	
	catch(Exception e)  {
		e.printStackTrace();
	}
	
	return nodeInfo;		
			
}
public Vector<DTOWorkSpaceNodeDetail>  getWorkSpaceDetailForChangeRequestGridList(String wsId,int userGroupCode,int userCode) {
	Vector<DTOWorkSpaceNodeDetail> nodeInfo = new Vector<DTOWorkSpaceNodeDetail>();
	Connection con = null;
	
	try  
	{
		DocMgmtImpl docmgmt=new DocMgmtImpl();
		con=ConnectionManager.ds.getConnection();
		String Fields="Distinct WorkspaceId,usergroupcode,usercode,NodeNo,NodeId,NodeDisplayName,NodeName,ParentNodeId,Foldername," +
		"canReadFlag,canAddFlag,canEditFlag,statusIndi,nodeTypeIndi,requiredFlag,canDeleteFlag,iformNo";
		String Where="WorkspaceId='"+wsId+"'  and Cloneflag='y' and usergroupcode="+userGroupCode+" and usercode="+userCode+" and statusIndi<>'D'";
		//String Where="WorkspaceId='"+wsId+"'  and Cloneflag='y' and statusIndi<>'D'";
		ResultSet rs = dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail_WSList" , Where,"NodeId desc" ); 
		while (rs.next())    
		{
			
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setNodeId(rs.getInt("NodeId"));
			dto.setNodeDisplayName(rs.getString("NodeDisplayName"));
			dto.setParentNodeId(rs.getInt("ParentNodeId"));
			dto.setFolderName(rs.getString("FolderName"));
			dto.setCanReadFlag(rs.getString("CanReadFlag"));
			dto.setCanAddFlag(rs.getString("canAddFlag"));
			dto.setCanEditFlag(rs.getString("CanEditFlag"));
			dto.setCanDeleteFlag(rs.getString("CanDeleteFlag"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
			dto.setNodeTypeIndi(rs.getString("nodeTypeIndi").charAt(0));
			dto.setRequiredFlag(rs.getString("requiredFlag").charAt(0));
			dto.setIformNo(rs.getInt("iformNo"));
			dto.setNodeNo(rs.getInt("NodeNo"));
			dto.setNodeName(rs.getString("NodeName"));
			dto.setPublishFlag(docmgmt.submittedNodeIdDetail(wsId,rs.getInt("NodeId")));
			//dto.setLokedNodeFlag(docmgmt.isCheckOut(wsId, rs.getInt("NodeId"),userCode));
			//dto.setPublishFlag(docmgmt.submittedNodeIdDetail(wsId,rs.getInt("NodeId")));
			dto.setLokedNodeFlag(true);
			dto.setPublishFlag(true);
			nodeInfo.addElement(dto);	
		}
		rs.close();
		con.close();
	}
	
	catch(Exception e)  {
		e.printStackTrace();
	}
	
	return nodeInfo;		
			
}
public DTOWorkSpaceNodeDetail getNodeDetailForEDocSign(String wsId) {

	ResultSet rs = null;
	Connection con = null;
	DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "vWorkspaceId = '" + wsId + "' and iNodeId=(Select max(iNodeId) from workspacenodedetail where vWorkspaceid='"+wsId+"')";
		rs = dataTable.getDataSet(con, "*", "workspaceNodeDetail", Where,
				"");

		while (rs.next()) {
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
			dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
			dto.setFolderName(rs.getString("vFolderName")); // folderName
			dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
			dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
			dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
			dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
			dto.setRemark(rs.getString("vRemark")); // remark
			dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
			dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return dto;
}
public void addChildNodeAfterForEDocSign(DTOWorkSpaceNodeDetail dto) {
	try {
		String wsId = dto.getWorkspaceId();
		int selectedNode = dto.getNodeId();
		int maxNodeId = this.getmaxNodeId(wsId);
		// Get Selected Node's Node No and Parent Node Detail.
		Vector<DTOWorkSpaceNodeDetail> selectedNodeDetail = this
				.getNodeDetail(wsId, selectedNode);
		DTOWorkSpaceNodeDetail dtoWorkSpaceNode = (DTOWorkSpaceNodeDetail) selectedNodeDetail
				.get(0);
		int nodeNo = dtoWorkSpaceNode.getNodeNo();
		int parentNodeId = dtoWorkSpaceNode.getParentNodeId();

		dto.setNodeId(maxNodeId + 1);
		dto.setNodeNo(nodeNo + 1);
		dto.setParentNodeId(parentNodeId);

		// Set Node no of selected node and nodes below it.
		this.updateNodeNo(wsId, parentNodeId, nodeNo, "addafter");

		// Add Record in workspace node detail for new record.
		this.insertWorkspaceNodeDetail(dto, 1);
		dto.setStatusIndi('N');
		this.insertWorkspaceNodeDetailhistory(dto, 1);
		
		DTOWorkSpaceNodeAttrDetail dtoworkspacenodeattribute = new DTOWorkSpaceNodeAttrDetail();
		dtoworkspacenodeattribute.setWorkspaceId(wsId);
		dtoworkspacenodeattribute.setParentId(selectedNode);//
		dtoworkspacenodeattribute.setNodeId(maxNodeId + 1);

		// System.out.println("wsId::"+wsId+"---parentNodeId::"+parentNodeId+"---maxNodeId+1::"+(maxNodeId+1));
		// Add Record in workspace node attribute.
		// Add Record in workspace user rights.
		
		//wsnad.insertIntoWorkSpaceNodeAttrDetailForAddChild(dtoworkspacenodeattribute);
		wsnad.insertIntoWorkSpaceNodeAttrDetailForAddChildForRepeatSection(dtoworkspacenodeattribute);
		//wsurmst.insertNodeintoWorkSpaceUserRights(wsId, maxNodeId + 1);
		wsurmst.insertNodeintoWorkSpaceUserRightsForEDocSign(wsId, maxNodeId + 1);

	} catch (Exception e) {
		e.printStackTrace();
	}
}
public int getLeafNodeCount(String wsId, int parentNodeId) {
	ResultSet rs = null;
	int parentNdId=0;

	try {
		String whr = "vWorkSpaceId ='" + wsId + "'  and iParentNodeId = "
				+ parentNodeId;
		Connection con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "count(iNodeId) as totalLeafNode",	"workspacenodedetail", whr, "");

		while (rs.next()) {
			parentNdId = rs.getInt("totalLeafNode");
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();

	}
	return parentNdId;
}
public int getFirstLeafNodeForDocCR(String wsID) {
	int firstLeafNode = 0;
	Connection con = null;
	ResultSet rs = null;
	try {

		con = ConnectionManager.ds.getConnection();
		String Where = "";
		String from = "";

		Where += " vWorkspaceId ='" + wsID + "' ";
		from += Where + " and cStatusIndi<>'D' and ";

		from += " crequiredflag in ('n','f') and inodeid not in ( select distinct iparentnodeid from workspacenodedetail where  crequiredflag in ('n','f') ";
		from += " and " + Where;
		from += " )";
		rs = dataTable.getDataSet(con, "iNodeId",
				"workspaceNodeDetail ", from, "iNodeId");
		if (rs.next()) {
			firstLeafNode = rs.getInt("iNodeId");
		}
		rs.close();
		con.close();
	} catch (Exception e) {
		e.printStackTrace();
	} 
	return firstLeafNode;
}
public void UpdateNodeDetail(String wsId,int nodeId,String folderName) {
	Connection con = null;
	int rs1=0;
	int rs2=0;
	try {
		con = ConnectionManager.ds.getConnection();
		
		String query = "UPDATE WorkspaceNodeDetail SET " + "vFolderName = '"+folderName
						+ "' WHERE vWorkspaceId = '"+ wsId + "' AND iNodeId = "+ nodeId+ " ";
		
		rs1 =dataTable.getDataSet1(con,query);
							
		String query1 = "UPDATE WorkspaceNodeDetailHistory SET " + "vFolderName = '"+folderName
						+ "' WHERE vWorkspaceId = '"+ wsId + "' AND iNodeId = "+ nodeId+ " ";
		
		rs2 =dataTable.getDataSet1(con,query1);
	
		
		con.close();
	}   
	
	catch(SQLException e){
		e.printStackTrace();
	}
}

public Vector <DTOWorkSpaceNodeDetail> getAllLeafNodeIds(String wsID) {
	Vector <DTOWorkSpaceNodeDetail> getAllLeafNodes =new Vector<>();
	Connection con = null;
	ResultSet rs = null;
	try {

		con = ConnectionManager.ds.getConnection();
		String Where = "";
		String from = "";

		Where += " vWorkspaceId ='" + wsID + "' ";
		from += Where + " and cStatusIndi<>'D' and ";

		from += " crequiredflag in ('n','f') and inodeid not in ( select distinct iparentnodeid from workspacenodedetail where  crequiredflag in ('n','f') ";
		from += " and " + Where;
		from += " )";
		rs = dataTable.getDataSet(con, "iNodeId",
				"workspaceNodeDetail ", from, "iNodeId");
		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setNodeID(rs.getInt("iNodeId"));
			getAllLeafNodes.add(dto);
			dto=null;
		}
		rs.close();
		con.close();
	} catch (Exception e) {
		e.printStackTrace();
	} 
	return getAllLeafNodes;
}
public boolean folderNameExist(String workspaceId, String folderName) {
	boolean flag = false;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con, "iNodeId",
				"WorkspaceNodedetail", "vFoldername = '" + folderName
						+ "' And vWorkspaceId='"+workspaceId+"'AND cStatusIndi <> 'D' ", "");
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

public Vector<DTOWorkSpaceNodeDetail> getWSNodeDtl(String wsId) {
	
	Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	Connection con = null;
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "vWorkspaceId = '" + wsId + "' and iParentNodeId in (0,1) and cStatusIndi<>'D'";
		rs = dataTable.getDataSet(con, "*", "workspaceNodeDetail", Where,"iParentNodeId,iNodeNo");

		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
			dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
			dto.setFolderName(rs.getString("vFolderName")); // folderName
			wsNodeDetail.addElement(dto);
			dto = null;
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return wsNodeDetail;
}
/*public Vector<DTOWorkSpaceNodeDetail> getAllNodes(String wsId,int nodeId) {
	
	Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	Connection con = null;
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "vWorkspaceId = '" + wsId + "' and iParentNodeId="+nodeId;
		rs = dataTable.getDataSet(con, "*", "workspaceNodeDetail", Where,
				"");

		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
			dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
			dto.setFolderName(rs.getString("vFolderName")); // folderName
			dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
			dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
			dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
			dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
			dto.setRemark(rs.getString("vRemark")); // remark
			dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
			dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
			wsNodeDetail.addElement(dto);
			dto = null;
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return wsNodeDetail;
}*/
public Vector<DTOWorkSpaceNodeDetail> getFileUploadSeqForSingleUpdated(String wsId, int nodeId, int parentNodeId) {
	
	
	ResultSet rs = null;
	Connection con = null;
			
	Vector<DTOWorkSpaceNodeDetail> fileUploadSeq = new Vector<DTOWorkSpaceNodeDetail>();
	String Fields="";
	try {
		con=ConnectionManager.ds.getConnection();
		
		Fields="top 1 ptpn.vWorkspaceId,ptpn.iNodeId,ptpn.iNodeNo,ptpn.vNodeName,ptpn.vFolderName,ptpn.iParentNodeId,fadt.NoOfApprover,"
				+ "fadt.NoOfApproved, ISNULL(wnh.istageId,'10') as stageId, ISNULL(stagemst.vStageDesc,'Created') as StageDesc";
		
		/*String From="Proc_getProjectTrackingeCSVParentNodes('"+wsId+"',"+nodeId+") as ptpn"+
					" inner join view_GetApprovedFilesDetailForProject as fadt on fadt.WorkspaceId = ptpn.vWorkspaceId"+ 
					" and fadt.NodeId = ptpn.iNodeId left join workspacenodehistory as wnh on wnh.vWorkspaceId = ptpn.vWorkspaceId and "
					+ "wnh.iNodeId = ptpn.iNodeId Left join stagemst on stagemst.istageiD= wnh.istageId "
					+ "inner join workspacenodedetail wsnd on ptpn.vWorkspaceId = wsnd.vWorkspaceId and "
					+ "ptpn.iNodeId = wsnd.iNodeId and wsnd.cNodeTypeIndi<>'K'";*/
		
		/*String From="Proc_getProjectTrackingeCSVChildNodesHours('"+wsId+"') as ptpn"+
				" inner join view_GetApprovedFilesDetailForProject as fadt on fadt.WorkspaceId = ptpn.vWorkspaceId"+ 
				" and fadt.NodeId = ptpn.iNodeId left join workspacenodehistory as wnh on wnh.vWorkspaceId = ptpn.vWorkspaceId and "
				+ "wnh.iNodeId = ptpn.iNodeId Left join stagemst on stagemst.istageiD= wnh.istageId "
				+ "inner join workspacenodedetail wsnd on ptpn.vWorkspaceId = wsnd.vWorkspaceId and "
				+ "ptpn.iNodeId = wsnd.iNodeId and wsnd.cNodeTypeIndi<>'K' AND nRID<(select nRID from "
				+ "Proc_getProjectTrackingeCSVChildNodesHours('"+wsId+"') where inodeid="+nodeId+") ";*/
		
		String From="Proc_getProjectTrackingeCSVChildNodesHours('"+wsId+"') as ptpn"+
				" inner join view_GetApprovedFilesDetailForProject as fadt on fadt.WorkspaceId = ptpn.vWorkspaceId"+ 
				" and fadt.NodeId = ptpn.iNodeId left join workspacenodehistory as wnh on wnh.vWorkspaceId = ptpn.vWorkspaceId and "
				+ "wnh.iNodeId = ptpn.iNodeId Left join stagemst on stagemst.istageiD= wnh.istageId "
				+ "inner join workspacenodedetail wsnd on ptpn.vWorkspaceId = wsnd.vWorkspaceId and "
				+ "ptpn.iNodeId = wsnd.iNodeId and wsnd.cNodeTypeIndi<>'K' AND (fadt.NoOfApprover <> fadt.NoOfApproved or fadt.NoOfApprover = 0)"
				+ " AND nRID<(select nRID from "
				+ "Proc_getProjectTrackingeCSVChildNodesHours('"+wsId+"') where inodeid="+nodeId+") ";
		
		//String Where="vWorkspaceId='"+wsId+"' and (fadt.NoOfApprover <> fadt.NoOfApproved or fadt.NoOfApprover = 0)";
		
			rs=dataTable.getDataSet(con,Fields,From , "","ptpn.nRID desc" );
		
		while (rs.next())    
		{
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setNodeName(rs.getString("vNodeName"));
			dto.setFolderName(rs.getString("vFolderName"));
			dto.setStageDesc(rs.getString("StageDesc"));
			
			fileUploadSeq.addElement(dto);	
		}
		rs.close();
		con.close();
	} catch(Exception e)  {
		e.printStackTrace();
	}
	
	return fileUploadSeq;		
}
public Vector<DTOWorkSpaceNodeDetail> getFileUploadSeqForAllUpdated(String wsId, int nodeId, int parentNodeId) {
	
	
	ResultSet rs = null;
	Connection con = null;
			
	Vector<DTOWorkSpaceNodeDetail> fileUploadSeq = new Vector<DTOWorkSpaceNodeDetail>();
	String Fields="";
	try {
		con=ConnectionManager.ds.getConnection();
		
		Fields="ptpn.vWorkspaceId,ptpn.iNodeId,ptpn.iNodeNo,ptpn.vNodeName,ptpn.vFolderName,ptpn.iParentNodeId,fadt.NoOfApprover,"
				+ "fadt.NoOfApproved, ISNULL(wnh.istageId,'10') as stageId, ISNULL(stagemst.vStageDesc,'Created') as StageDesc";
		
		/*String From="Proc_getProjectTrackingeCSVParentNodes('"+wsId+"',"+nodeId+") as ptpn"+
					" inner join view_GetApprovedFilesDetailForProject as fadt on fadt.WorkspaceId = ptpn.vWorkspaceId"+ 
					" and fadt.NodeId = ptpn.iNodeId left join workspacenodehistory as wnh on wnh.vWorkspaceId = ptpn.vWorkspaceId and "
					+ "wnh.iNodeId = ptpn.iNodeId Left join stagemst on stagemst.istageiD= wnh.istageId "
					+ " inner join workspacenodedetail wsnd on ptpn.vWorkspaceId = wsnd.vWorkspaceId and"
					+ " ptpn.iNodeId = wsnd.iNodeId and wsnd.cNodeTypeIndi<>'K'";*/
		
		/*String From="Proc_getProjectTrackingeCSVChildNodesHours('"+wsId+"') as ptpn"+
				" inner join view_GetApprovedFilesDetailForProject as fadt on fadt.WorkspaceId = ptpn.vWorkspaceId"+ 
				" and fadt.NodeId = ptpn.iNodeId left join workspacenodehistory as wnh on wnh.vWorkspaceId = ptpn.vWorkspaceId and "
				+ "wnh.iNodeId = ptpn.iNodeId Left join stagemst on stagemst.istageiD= wnh.istageId "
				+ " inner join workspacenodedetail wsnd on ptpn.vWorkspaceId = wsnd.vWorkspaceId and"
				+ " ptpn.iNodeId = wsnd.iNodeId and wsnd.cNodeTypeIndi<>'K'  AND nRID<(select nRID from Proc_getProjectTrackingeCSVChildNodesHours"
				+ "('"+wsId+"') where inodeid="+nodeId+") ";*/
		
		String From="Proc_getProjectTrackingeCSVChildNodesHours('"+wsId+"') as ptpn"+
				" inner join view_GetApprovedFilesDetailForProject as fadt on fadt.WorkspaceId = ptpn.vWorkspaceId"+ 
				" and fadt.NodeId = ptpn.iNodeId left join workspacenodehistory as wnh on wnh.vWorkspaceId = ptpn.vWorkspaceId and "
				+ "wnh.iNodeId = ptpn.iNodeId Left join stagemst on stagemst.istageiD= wnh.istageId "
				+ " inner join workspacenodedetail wsnd on ptpn.vWorkspaceId = wsnd.vWorkspaceId and"
				+ " ptpn.iNodeId = wsnd.iNodeId and wsnd.cNodeTypeIndi<>'K' AND (fadt.NoOfApprover <> fadt.NoOfApproved or fadt.NoOfApprover = 0)"
				+ " AND nRID<(select nRID from Proc_getProjectTrackingeCSVChildNodesHours"
				+ "('"+wsId+"') where inodeid="+nodeId+") ";
		
		//String Where="vWorkspaceId='"+wsId+"' and (fadt.NoOfApprover <> fadt.NoOfApproved or fadt.NoOfApprover = 0)";
		
			rs=dataTable.getDataSet(con,Fields,From , "","ptpn.nRID" );
		
		while (rs.next())    
		{
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setNodeName(rs.getString("vNodeName"));
			dto.setFolderName(rs.getString("vFolderName"));
			dto.setStageDesc(rs.getString("StageDesc"));
			
			fileUploadSeq.addElement(dto);	
		}
		rs.close();
		con.close();
	} catch(Exception e)  {
		e.printStackTrace();
	}
	
	return fileUploadSeq;		
}



public Vector<DTOWorkSpaceNodeDetail> getNodeDetailForparent(String wsId, int nodeId) {

	Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	Connection con = null;
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = " vWorkspaceId='" + wsId + "'" + " and iParentNodeId=" + nodeId + " and inodeid="
				+ "(select max(inodeid)from workspacenodedetail where vWorkspaceId='" + wsId + "'" + " and iParentNodeId=" + nodeId +" and cStatusIndi<>'D')" ;

		rs = dataTable.getDataSet(con, "*","WorkSpaceNodeDetail", Where, "");

		if (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
			dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
			dto.setFolderName(rs.getString("vFolderName")); // folderName
			dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
			dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
			dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
			dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
			dto.setRemark(rs.getString("vRemark")); // remark
			dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
			dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
			wsNodeDetail.addElement(dto);
			dto = null;
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

	return wsNodeDetail;
}

public Vector<DTOWorkSpaceNodeDetail> getChildNodesForSectionDeletion(String workSpaceId,int nodeId) {
	Vector<DTOWorkSpaceNodeDetail> childNodesDtl = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	try {
		Connection con = ConnectionManager.ds.getConnection();

		//String FieldNames = "vWorkspaceId,iNodeId,iNodeNo,vnodename,vnodedisplayname,vfoldername,cnodetypeindi,vRemark,cRequiredFlag,cCloneFlag,dModifyOn,iModifyBy";
		//String Where =  " vFolderName like '%.pdf' "; 
		rs = dataTable.getDataSet(con, "*", "Proc_getAllChildNodes('" + workSpaceId + "'," + nodeId+ ")", "cStatusindi<>'D' ", "");

		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setNodeNo(rs.getInt("iNodeNo"));
			dto.setNodeName(rs.getString("vNodeName"));
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
			dto.setFolderName(rs.getString("vFolderName"));
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
			dto.setRemark(rs.getString("vRemark"));
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0));
			dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			childNodesDtl.addElement(dto);
			dto = null;
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return childNodesDtl;
}
public Vector<DTOWorkSpaceNodeDetail> getChildNodesSections(String wsId, int nodeId) {
	
	Vector<DTOWorkSpaceNodeDetail> leafNodes = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	try {

		Connection con = ConnectionManager.ds.getConnection();
		wsId = "'" + wsId + "'";
		rs = dataTable.getDataSet(con, "vworkspaceid,iNodeId,vNodeName,vNodeDisplayName,vFolderName,cRequiredFlag",
				"Proc_getAllChildNodes(" + wsId + "," + nodeId + ")", "",
				"");
		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			/*dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
			dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
*/			dto.setFolderName(rs.getString("vFolderName")); // folderName
			//dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
			/*dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
			dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
			dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
			dto.setRemark(rs.getString("vRemark")); // remark
			dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
			dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
*/			leafNodes.addElement(dto);
			dto = null;
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return leafNodes;
}


public Vector<DTOWorkSpaceNodeDetail> getNodeDetailForparent(String wsId, int nodeId,String nDisplayName) {

	Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	Connection con = null;
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = " vWorkspaceId='" + wsId + "'" + " and iParentNodeId=" + nodeId + " and cStatusIndi<>'D' and "
				+ "vnodedisplayname like '" + nDisplayName + "%'and inodeid="
				+ "(select max(inodeid)from workspacenodedetail where vWorkspaceId='" + wsId + "'" + " and "
				+ "iParentNodeId=" + nodeId +" and vnodedisplayname like '" + nDisplayName + "%'and cStatusIndi<>'D')" ;

		rs = dataTable.getDataSet(con, "*","WorkSpaceNodeDetail", Where, "");

		if (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
			dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
			dto.setFolderName(rs.getString("vFolderName")); // folderName
			dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
			dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
			dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
			dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
			dto.setRemark(rs.getString("vRemark")); // remark
			dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
			dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
			wsNodeDetail.addElement(dto);
			dto = null;
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return wsNodeDetail;
}
public int getNodeDetailForNodename(String wsId, int nodeId,String nDisplayName) {

	ResultSet rs = null;
	Connection con = null;
	int count=0;
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = " vWorkspaceId='" + wsId + "'" + " and iParentNodeId=" + nodeId + " and cStatusIndi<>'D' and "
				+ "vnodename like '" + nDisplayName + "%'";

		rs = dataTable.getDataSet(con, "count(*) as Counts","WorkSpaceNodeDetail", Where, "");

		 while (rs.next()){
			 count=rs.getInt("Counts");
		 }

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return count;
}

public int getPQScriptsCount(String wsId, int nodeId, String nodeName) {

	ResultSet rs = null;
	Connection con = null;
	int count=0;
	try {
		con = ConnectionManager.ds.getConnection();
		String Where;
		if (nodeName.equalsIgnoreCase("PQ Scripts"))
		{
		Where = " vWorkspaceId='" + wsId + "'" + " and iParentNodeId=" + nodeId + " and vNodeName = 'PQ Scripts'";
		}
		else if(nodeName.equalsIgnoreCase("OQ Scripts")){
			Where = " vWorkspaceId='" + wsId + "'" + " and iParentNodeId=" + nodeId + " and vNodeName = 'OQ Scripts'";
		}
		else{
			Where = " vWorkspaceId='" + wsId + "'" + " and iParentNodeId=" + nodeId + " and vNodeName = 'IQ Scripts'";
		}
		rs = dataTable.getDataSet(con, "count(*) as Counts","WorkSpaceNodeDetail", Where, "");

		 while (rs.next()){
			 count=rs.getInt("Counts");
		 }

	} catch (SQLException e) {
		e.printStackTrace();
	} 

	return count;
}
public void insertIntoPQApproval(DTOWorkSpaceNodeDetail dto) {
	CallableStatement proc = null;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		proc = con.prepareCall("{call Insert_PQSTableHeaderMst(?,?,?,?,?,?,?,?,?,?,?)}");

		
			proc.setString(1, dto.getClientCode());
			proc.setString(2, dto.getWorkspaceId());
			proc.setInt(3, dto.getNodeId());
			proc.setInt(4, dto.getTranNo());
			proc.setString(5, dto.getFolderName());
			proc.setString(6, dto.getStepNo());
			proc.setString(7, dto.getStepName());
			proc.setString(8, dto.getExpectedResult());
			proc.setString(9, Character.toString(dto.getIsActive()));
			proc.setString(10, dto.getRemark());
			proc.setInt(11, dto.getModifyBy());
			proc.execute();
			
			proc.close();
			con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public Vector<DTOWorkSpaceNodeDetail> getPQSTableHeaderMst(String wsId, int nodeId, String clientCode, String folderName) {
	Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	Connection con = null;
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = " vWorkspaceId='" + wsId + "'" + " and iNodeId=" + nodeId + " and vClientId='"+clientCode
						+"' and vfolderName='"+folderName+"' and cStatusIndi<>'D'";

		rs = dataTable.getDataSet(con, "*","PQSTableHeaderMst", Where, "");

		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setClientCode(rs.getString("vClientId")); 
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId")); 
			dto.setNodeNo(rs.getInt("iTranNo"));
			dto.setFolderName(rs.getString("vFolderName")); 
			dto.setStepNo(rs.getString("vStepNo"));
			dto.setStepName(rs.getString("vStepName"));
			dto.setExpectedResult(rs.getString("vExpectedResult"));
			dto.setRemark(rs.getString("vRemark")); 
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); 
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			wsNodeDetail.addElement(dto);
			dto = null;
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return wsNodeDetail;
}
public DTOWorkSpaceNodeDetail getFailedScriptNodeDetail(String wsId, int parenNodeId, int nodeNo) {

	DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
	ResultSet rs = null;
	Connection con = null;
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "vWorkspaceId = '" + wsId + "' and iParentNodeId= "+ parenNodeId+" And iNodeNo = "+nodeNo;
		rs = dataTable.getDataSet(con, "*", "workspaceNodeDetail", Where,"");

		if (rs.next()) {
			
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
			dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
			dto.setFolderName(rs.getString("vFolderName")); // folderName
			dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
			dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
			dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
			dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
			dto.setRemark(rs.getString("vRemark")); // remark
			dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
			dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return dto;
}
public DTOWorkSpaceNodeDetail getNextNodeDetail(String wsId, int parenNodeId, int nodeNo) {

	DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
	ResultSet rs = null;
	Connection con = null;
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "vWorkspaceId = '" + wsId + "' and iParentNodeId= "+ parenNodeId+" And iNodeNo >"+nodeNo+" And cStatusIndi<>'D'" ;
		rs = dataTable.getDataSet(con, "*", "workspaceNodeDetail", Where,"iNodeNo");

		if (rs.next()) {
			
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
			dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
			dto.setFolderName(rs.getString("vFolderName")); // folderName
			dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
			dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
			dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
			dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
			dto.setRemark(rs.getString("vRemark")); // remark
			dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
			dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return dto;
}
public Vector<DTOWorkSpaceNodeDetail> getNodeDetailForNodeCountForFailedSctipt(String wsId, int nodeId,String nDisplayName) throws ClassNotFoundException {

	Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	Connection con = null;
	int count=0;
	Vector<DTOWorkSpaceNodeDetail> childNodesDtl = new Vector<DTOWorkSpaceNodeDetail>();
	try {
		//java.lang.Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		//Connection conn = DriverManager.getConnection("jdbc:microsoft:sqlserver://90.0.0.232:1433;DatabaseName=KnowledgeNET-CSV-Timeline-Test","sa","eCSV@2021");
    	//con = DriverManager.getConnection("jdbc:microsoft:sqlserver://90.0.0.15:1433;DatabaseName=DocStack-v1.1.0","sa","Sarjen");
		con = ConnectionManager.ds.getConnection();
	
    	String Where = " vWorkspaceId='" + wsId + "'" + " and iParentNodeId=" + nodeId + " and cStatusIndi<>'D' and "
				+ "vnodename like '" + nDisplayName + "%'";
    	
    	//String SQL = "select * from workspaceNodeDetail where"+Where+" order by iNodeId";
	    //System.out.println(SQL);
		
		rs = dataTable.getDataSet(con, "*","WorkSpaceNodeDetail", Where, "iNodeId");

		 while (rs.next()){
			 //count=rs.getInt("Counts");
			 DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
				dto.setWorkspaceId(rs.getString("vWorkspaceId"));
				dto.setNodeId(rs.getInt("iNodeId"));
				dto.setParentNodeId(rs.getInt("iparentnodeid"));
				dto.setNodeNo(rs.getInt("iNodeNo"));
				dto.setNodeName(rs.getString("vNodeName"));
				dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				dto.setFolderName(rs.getString("vFolderName"));
				dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
				dto.setRemark(rs.getString("vRemark"));
				dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0));
				dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				childNodesDtl.addElement(dto);
				dto = null;
		 }

	} catch (SQLException e) {
		e.printStackTrace();
	}
		//return count;
	return childNodesDtl;
}
public int getFailedScriptData(String wsId, int parenNodeId, String nodeName) {

	int nodeNo = 0;
	ResultSet rs = null;
	Connection con = null;
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "vWorkspaceId = '" + wsId + "' and iParentNodeId= "+ parenNodeId+" And vNodeName = '"+nodeName+"' And cStatusIndi<>'D'";
		rs = dataTable.getDataSet(con, "MAX(iNodeNo) AS iNodeNo", "workspaceNodeDetail", Where,"");

		if (rs.next()) {
			nodeNo =rs.getInt("iNodeNo"); // nodeNo
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return nodeNo;
}
public Vector<DTOWorkSpaceNodeDetail> getNodeDetailByNodeNo(String wsId, int parentNodeId, int nodeNo) {

	Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	Connection con = null;
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "vWorkspaceId = '" + wsId + "' and iParentNodeId ="+parentNodeId+" and iNodeNo< "+ nodeNo+" And cStatusIndi<>'D'";
		rs = dataTable.getDataSet(con, "*", "workspaceNodeDetail", Where,"iNodeNo desc");

		if (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
			dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
			dto.setFolderName(rs.getString("vFolderName")); // folderName
			dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
			dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
			dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
			dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
			dto.setRemark(rs.getString("vRemark")); // remark
			dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
			dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
			wsNodeDetail.addElement(dto);
			dto = null;
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return wsNodeDetail;
}


public Vector<DTOWorkSpaceNodeDetail> getChildNodesForSectionDeletion(String workSpaceId,int nodeId,String status) {
	Vector<DTOWorkSpaceNodeDetail> childNodesDtl = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	try {
		Connection con = ConnectionManager.ds.getConnection();

		//String FieldNames = "vWorkspaceId,iNodeId,iNodeNo,vnodename,vnodedisplayname,vfoldername,cnodetypeindi,vRemark,cRequiredFlag,cCloneFlag,dModifyOn,iModifyBy";
		//String Where =  " vFolderName like '%.pdf' "; 
		if(status.equalsIgnoreCase("D")){
			rs = dataTable.getDataSet(con, "*", "Proc_getAllChildNodes('" + workSpaceId + "'," + nodeId+ ")",
					"cstatusindi<>'D'", "");
		}else{
		rs = dataTable.getDataSet(con, "*", "Proc_getAllChildNodes('" + workSpaceId + "'," + nodeId+ ")",
				"cstatusindi='D'", "");
		}

		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setNodeNo(rs.getInt("iNodeNo"));
			dto.setNodeName(rs.getString("vNodeName"));
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
			dto.setFolderName(rs.getString("vFolderName"));
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
			dto.setRemark(rs.getString("vRemark"));
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0));
			dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));//
			childNodesDtl.addElement(dto);
			dto = null;
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return childNodesDtl;
	}

public Vector<DTOWorkSpaceNodeDetail> getWorkspaceNodeDetailByNodeId(String workspaceID,int nodeId) throws SQLException {
	
	Vector<DTOWorkSpaceNodeDetail> dto = new Vector<DTOWorkSpaceNodeDetail>();
	Connection con = null;
	ResultSet rs = null;
	con = ConnectionManager.ds.getConnection();
	 rs = dataTable.getDataSet(con, "*", "workspacenodedetail","vWorkspaceId = "+workspaceID+ "and inodeid="+nodeId,
			"iParentNodeId,iNodeNo");
	 while (rs.next()) {
		 DTOWorkSpaceNodeDetail data = new DTOWorkSpaceNodeDetail();
		 data.setNodeDisplayName(rs.getString("vnodedisplayname"));
		 data.setNodeName(rs.getString("vnodename"));
		 data.setStatusIndi(rs.getString("cStatusIndi").charAt(0));//
		 dto.addElement(data);
		 data=null;
	 }
	return dto;
}

public Vector<DTOWorkSpaceNodeDetail> getChildNodesForDynaTreeCT(String wsId, int nodeId ,int usergrpcode,int usercode ) {
	
	Vector<DTOWorkSpaceNodeDetail> leafNodes = new Vector<DTOWorkSpaceNodeDetail>();
	Vector<DTOWorkSpaceNodeDetail> nodeInfo = new Vector<DTOWorkSpaceNodeDetail>();
	DocMgmtImpl docmgmt=new DocMgmtImpl();
	ResultSet rs = null;
	ResultSet rs2 = null;
	int NodeId = 0;
	int parentNodeId = 0;
	try {

		Connection con = ConnectionManager.ds.getConnection();
		//wsId = "'" + wsId + "'";
		//String Where="vWorkspaceId="+wsId+" and iNodeId="+nodeId;
		//int parenteIdToPass= docmgmt.getParentNodeId(wsId, parentNodeId);
	 	
	 	//rs=dataTable.getDataSet(con,"*","Proc_getAllParentNodes('" + wsId + "'," + nodeId + ")", "iNodeid in(1,2)","iNodeId" ); 
		rs=dataTable.getDataSet(con,"*","Proc_getAllParentNodes('" + wsId + "'," + nodeId + ")", "","iNodeId" );
				while (rs.next())
				{
					DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
					dto.setWorkspaceId(rs.getString("vWorkspaceId"));
					dto.setNodeId(rs.getInt("iNodeId"));
					NodeId=rs.getInt("iNodeId");
					dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
					dto.setParentNodeId(rs.getInt("iParentNodeId"));
					parentNodeId=rs.getInt("iParentNodeId");
					dto.setFolderName(rs.getString("vFolderName"));
					//dto.setCanReadFlag(rs.getString("CanReadFlag"));
					//dto.setCanAddFlag(rs.getString("canAddFlag"));
					//dto.setCanEditFlag(rs.getString("CanEditFlag"));
					//dto.setCanDeleteFlag(rs.getString("CanDeleteFlag"));
					dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
					dto.setNodeTypeIndi(rs.getString("cnodeTypeIndi").charAt(0));
					dto.setRequiredFlag(rs.getString("crequiredFlag").charAt(0));
					//dto.setIformNo(rs.getInt("iformNo"));
					dto.setNodeNo(rs.getInt("iNodeNo"));
					dto.setNodeName(rs.getString("vNodeName"));
					/*dto.setPublishFlag(docmgmt.submittedNodeIdDetail(wsId,rs.getInt("iNodeId")));
					dto.setLokedNodeFlag(docmgmt.isCheckOut(wsId, rs.getInt("iNodeId"),usercode));*/
					dto.setPublishFlag(true);
					dto.setLokedNodeFlag(true);
					leafNodes.addElement(dto);
					dto = null;	
				}
				
			//	int parenteIdToPass= docmgmt.getParentNodeId(wsId, parentNodeId);
				
				/*rs=dataTable.getDataSet(con,"vWorkspaceId,iNodeId,iNodeNo,vnodename,vnodedisplayname,vfoldername,cnodetypeindi,iParentNodeId,vRemark,cRequiredFlag,cCloneFlag,"
						+ "dModifyOn,iModifyBy","workspacenodedetail","vWorkspaceId='"+wsId+"' and iParentNodeId="+parentNodeId+" and cStatusIndi<>'D'","iNodeId" );*/
				
				String uType=ActionContext.getContext().getSession().get("usertypename").toString();
				String where="";
				if(uType.equalsIgnoreCase("WA")){
					where="vWorkspaceId='"+wsId+"' and iParentNodeId="+parentNodeId+" union Select distinct vWorkspaceId,iNodeId,iNodeNo,vnodename,"
							+ "vnodedisplayname,vfoldername,iParentNodeId,cnodetypeindi,iParentNodeId,cRequiredFlag,cCloneFlag,cstatusindi From Proc_getAllParentNodes('"+wsId+"',"+nodeId+")";
				}
				else{
					where="vWorkspaceId='"+wsId+"' and iParentNodeId="+parentNodeId+" and cStatusIndi<>'D' union Select distinct vWorkspaceId,iNodeId,iNodeNo,vnodename,"
							+ "vnodedisplayname,vfoldername,iParentNodeId,cnodetypeindi,iParentNodeId,cRequiredFlag,cCloneFlag,cstatusindi From Proc_getAllParentNodes('"+wsId+"',"+nodeId+")";
				}
				
				rs=dataTable.getDataSet(con,"distinct vWorkspaceId,iNodeId,iNodeNo,vnodename,"
						+ "vnodedisplayname,vfoldername,iParentNodeId,cnodetypeindi,iParentNodeId,cRequiredFlag,cCloneFlag,cstatusindi","workspacenodedetail",where,"workspacenodedetail.iNodeId" ); 
				 

				while (rs.next()) {
					//rs.last();
					DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
					dto.setWorkspaceId(rs.getString("vWorkspaceId"));
					dto.setNodeId(rs.getInt("iNodeId"));
					dto.setNodeNo(rs.getInt("iNodeNo"));
					dto.setNodeName(rs.getString("vNodeName"));
					dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
					dto.setFolderName(rs.getString("vFolderName"));
					dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
					dto.setParentNodeId(rs.getInt("iParentNodeId"));
					//dto.setRemark(rs.getString("vRemark"));
					dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0));
					dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0));
					dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
					//dto.setModifyBy(rs.getInt("iModifyBy"));
					//dto.setModifyOn(rs.getTimestamp("dModifyOn"));
					nodeInfo.addElement(dto);
					dto = null;
				}
								
				String FolderName;
				/* String Fields="distinct vwnrd.nodeid,vwnrd.NodeDisplayName,vwnrd.WorkspaceId,vwnrd.usergroupcode,vwnrd.usercode,vwnrd.NodeNo,vwnrd.NodeName,vwnrd.ParentNodeId,vwnrd.Foldername,"+
						 "vwnrd.canReadFlag,vwnrd.canAddFlag,vwnrd.canEditFlag,vwnrd.statusIndi,vwnrd.nodeTypeIndi,vwnrd.requiredFlag,vwnrd.canDeleteFlag,vwnrd.iformNo";
						 			String Whr="workspacenodeattrdetail.vWorkspaceId='"+wsId+"' and userGroupCode="+usergrpcode+" and userCode="+usercode+" and (workspacenodeattrdetail.vAttrName='Site Name' Or workspacenodeattrdetail.vAttrName='Site Name'	Or workspacenodedetail.vNodeName in ('a-TMF','project level') ) and statusIndi<>'D'";
						 			if(nodeId>0){
						 				Where="ParentNodeId in(0,1,2,3379,"+nodeId+")";
						 			}
				rs=dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail_WSList as vwnrd inner join  workspacenodeattrdetail on workspacenodeattrdetail.vworkspaceid=vwnrd.workspaceid"+ 
						  " and workspacenodeattrdetail.inodeid=vwnrd.NodeId inner join workspacenodedetail on workspacenodedetail.vworkspaceid=vwnrd.workspaceid"+ 
						  " and workspacenodedetail.inodeid=vwnrd.NodeId" , Whr,"ParentNodeId,NodeNo" );
				while (rs.next())    
				{
					
					DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
					dto.setWorkspaceId(rs.getString("WorkspaceId"));
					dto.setNodeId(rs.getInt("NodeId"));
					dto.setNodeDisplayName(rs.getString("NodeDisplayName"));
					dto.setParentNodeId(rs.getInt("ParentNodeId"));
					parentNodeId=rs.getInt("ParentNodeId");
					dto.setFolderName(rs.getString("FolderName"));
					dto.setCanReadFlag(rs.getString("CanReadFlag"));
					dto.setCanAddFlag(rs.getString("canAddFlag"));
					dto.setCanEditFlag(rs.getString("CanEditFlag"));
					dto.setCanDeleteFlag(rs.getString("CanDeleteFlag"));
					dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
					dto.setNodeTypeIndi(rs.getString("nodeTypeIndi").charAt(0));
					dto.setRequiredFlag(rs.getString("requiredFlag").charAt(0));
					dto.setIformNo(rs.getInt("iformNo"));
					dto.setNodeNo(rs.getInt("NodeNo"));
					dto.setNodeName(rs.getString("NodeName"));
					dto.setPublishFlag(docmgmt.submittedNodeIdDetail(wsId,rs.getInt("NodeId")));
					dto.setLokedNodeFlag(docmgmt.isCheckOut(wsId, rs.getInt("NodeId"),usercode));
					dto.setPublishFlag(true);
					dto.setLokedNodeFlag(true);
					nodeInfo.addElement(dto);
					dto = null;	
				}*/
				String Whr="wnd.vWorkspaceId='"+wsId+"' and wnd.iparentnodeid in(select wnat.inodeid workspaceNodeAttrDetail "
						+ "where wnat.vattrname='Country' and wnat.vWorkspaceId='"+wsId+"') "
						+ "and wsurm.iuserGroupCode="+usergrpcode+" and wsurm.iuserCode="+usercode; 
				
				if(uType.equalsIgnoreCase("WA")){
					rs=dataTable.getDataSet(con,"distinct wnd.vWorkSpaceId,wnd.iNodeId,wnd.iNodeNo,wnd.vNodeName,wnd.vNodeDisplayName,wnd.vFolderName,"
							+"wnd.iParentnodeid,wnd.cNodeTypeIndi,wnd.crequiredFlag, wsurm.iUserCode, wsurm.iUsergroupCode,wnd.cStatusIndi, wsurm.cStatusIndi as wsrightsStatus","workspacenodedetail as wnd "
							+ "left join workspaceNodeAttrDetail as wnat on wnat.vworkspaceid= wnd.vworkspaceid and wnat.inodeid=wnd.iparentnodeid and "
							+ "wnat.vattrname='Country' inner join workspaceusermst as wumst on wumst.vWorkSpaceId=wnd.vWorkSpaceId "+ 
							" inner join workspaceuserrightsmst as wsurm on wsurm.vWorkSpaceId=wnd.vworkspaceid and wsurm.iNodeId=wnd.iNodeId" , Whr,"inodeno" );
				}
				else{
					rs=dataTable.getDataSet(con,"distinct wnd.vWorkSpaceId,wnd.iNodeId,wnd.iNodeNo,wnd.vNodeName,wnd.vNodeDisplayName,wnd.vFolderName,"
							+"wnd.iParentnodeid,wnd.cNodeTypeIndi,wnd.crequiredFlag, wsurm.iUserCode, wsurm.iUsergroupCode,wnd.cStatusIndi, wsurm.cStatusIndi as wsrightsStatus","workspacenodedetail as wnd "
							+ "left join workspaceNodeAttrDetail as wnat on wnat.vworkspaceid= wnd.vworkspaceid and wnat.inodeid=wnd.iparentnodeid and "
							+ "wnat.vattrname='Country' inner join workspaceusermst as wumst on wumst.vWorkSpaceId=wnd.vWorkSpaceId and wumst.cStatusIndi<>'D'"+ 
							" inner join workspaceuserrightsmst as wsurm on wsurm.vWorkSpaceId=wnd.vworkspaceid and wsurm.iNodeId=wnd.iNodeId and "
							+ "wsurm.cStatusIndi<>'D'" , Whr,"inodeno" );	
				}
				
				
				
				
				while (rs.next())    
				{
					
					DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
					NodeId=rs.getInt("iNodeId");
		 			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
		 			dto.setNodeId(rs.getInt("iNodeId"));
		 			dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
		 			dto.setParentNodeId(rs.getInt("iParentNodeId"));
		 			parentNodeId=rs.getInt("iParentNodeId");
		 			dto.setFolderName(rs.getString("vFolderName"));
		 			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
		 			dto.setNodeTypeIndi(rs.getString("cnodeTypeIndi").charAt(0));
		 			dto.setRequiredFlag(rs.getString("crequiredFlag").charAt(0));
		 			dto.setNodeNo(rs.getInt("iNodeNo"));
		 			dto.setNodeName(rs.getString("vNodeName"));
		 			/*dto.setPublishFlag(docmgmt.submittedNodeIdDetail(workspaceID,rs.getInt("NodeId")));
		 			dto.setLokedNodeFlag(docmgmt.isCheckOut(workspaceID, rs.getInt("NodeId"),userCode));*/
		 			dto.setPublishFlag(true);
					dto.setLokedNodeFlag(true);
					
					nodeInfo.addElement(dto);
					 dto = null;						
				}
				
				if(uType.equalsIgnoreCase("WA")){
					rs = dataTable.getDataSet(con, "distinct Proc_getAllChildNodes.vWorkspaceId,Proc_getAllChildNodes.iNodeId,Proc_getAllChildNodes.iNodeNo,Proc_getAllChildNodes.iparentnodeid,"
							+ "Proc_getAllChildNodes.vNodeName,Proc_getAllChildNodes.vNodeDisplayName,Proc_getAllChildNodes.vfolderName,"
							+ "Proc_getAllChildNodes.cPublishFlag,Proc_getAllChildNodes.cNodeTypeIndi,Proc_getAllChildNodes.cRequiredFlag,Proc_getAllChildNodes.cstatusindi",
							"Proc_getAllChildNodes('" + wsId + "'," + nodeId + ") inner join workspaceuserrightsmst on"
									+ " workspaceuserrightsmst.iNodeId=Proc_getAllChildNodes.iNodeId and"
									+" workspaceuserrightsmst.vWorkSpaceId=Proc_getAllChildNodes.vWorkSpaceId", "workspaceuserrightsmst.iUserCode="+usercode+" "
									+ "and workspaceuserrightsmst.iUserGroupCode="+usergrpcode,
							"Proc_getAllChildNodes.iParentNodeId,Proc_getAllChildNodes.iNodeNo");
				}
				else{
					rs = dataTable.getDataSet(con, "distinct Proc_getAllChildNodes.vWorkspaceId,Proc_getAllChildNodes.iNodeId,Proc_getAllChildNodes.iNodeNo,Proc_getAllChildNodes.iparentnodeid,"
							+ "Proc_getAllChildNodes.vNodeName,Proc_getAllChildNodes.vNodeDisplayName,Proc_getAllChildNodes.vfolderName,"
							+ "Proc_getAllChildNodes.cPublishFlag,Proc_getAllChildNodes.cNodeTypeIndi,Proc_getAllChildNodes.cRequiredFlag,Proc_getAllChildNodes.cstatusindi",
							"Proc_getAllChildNodes('" + wsId + "'," + nodeId + ") inner join workspaceuserrightsmst on"
									+ " workspaceuserrightsmst.iNodeId=Proc_getAllChildNodes.iNodeId and"
									+" workspaceuserrightsmst.vWorkSpaceId=Proc_getAllChildNodes.vWorkSpaceId", "Proc_getAllChildNodes.cStatusIndi<>'D' and "
											+ "workspaceuserrightsmst.iUserCode="+usercode+" and workspaceuserrightsmst.iUserGroupCode="+usergrpcode,
							"Proc_getAllChildNodes.iParentNodeId,Proc_getAllChildNodes.iNodeNo");
				}
		
		while (rs.next()) {
			
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setParentNodeId(rs.getInt("iparentnodeid"));
			dto.setNodeName(rs.getString("vNodeName"));
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
			dto.setFolderName(rs.getString("vfolderName"));
			dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0));
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			/*dto.setPublishFlag(docmgmt.submittedNodeIdDetail(wsId,rs.getInt("iNodeId")));
			dto.setLokedNodeFlag(docmgmt.isCheckOut(wsId, rs.getInt("iNodeId"),usercode));*/
			dto.setPublishFlag(true);
			dto.setLokedNodeFlag(true);
			//dto.setPublishedFlag(rs.getString("cRequiredFlag").charAt(0));
			nodeInfo.addElement(dto);
			dto = null;
		}
		
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return nodeInfo;
}


public void activeNodeDetail(String wsId, int nodeId, String remark) {
	int userId = Integer.parseInt(ActionContext.getContext().getSession()
			.get("userid").toString());
	try {
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement proc = con
				.prepareCall("{Call proc_activeWorkspaceNode(?,?,?,?)}");
		proc.setString(1, wsId);
		proc.setInt(2, nodeId);
		proc.setString(3,remark);
		proc.setInt(4,userId);
		proc.execute();
		proc.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public Vector<DTOWorkSpaceNodeDetail> getDeletedNodeDetailForActivity(String wsId) {

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	Connection con = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	
	try {
		con = ConnectionManager.ds.getConnection();

		//String Where = "vWorkspaceId = '" + wsId + "' and cStatusIndi='D'";
		String Where = "vWorkspaceId = '" + wsId + "' and cStatusIndi in ('E','D') ";
		rs = dataTable.getDataSet(con, "*", "View_WorkSpaceNodeDetailHistory", Where,
				"dModifyOn ASC");

		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
			dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
			dto.setFolderName(rs.getString("vFolderName")); // folderName
			dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
			dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
			dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
			dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
			dto.setRemark(rs.getString("vRemark")); // remark
			dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
		if(countryCode.equalsIgnoreCase("IND")){
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
		}
		else{
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
			dto.setESTDateTime(time.get(1));
		}
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
			dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
			dto.setUserName(rs.getString("vUserName")); // UserName 
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

public Vector<DTOWorkSpaceNodeDetail> getDeletedNodeDetailForActiveInactive(String wsId) {

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	Connection con = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	
	try {
		con = ConnectionManager.ds.getConnection();

		//String Where = "vWorkspaceId = '" + wsId + "' and cStatusIndi='D'";
		String Where = "vWorkspaceId = '" + wsId + "' and cStatusIndi in ('E','D') ";
		rs = dataTable.getDataSet(con, "*", "View_WorkSpaceNodeDetailHistory", Where,
				"dModifyOn ASC");

		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
			dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
			dto.setFolderName(rs.getString("vFolderName")); // folderName
			dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
			dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
			dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
			dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
			dto.setRemark(rs.getString("vRemark")); // remark
			dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
		if(countryCode.equalsIgnoreCase("IND")){
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
		}
		else{
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
			dto.setESTDateTime(time.get(1));
		}
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
			dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
			dto.setUserName(rs.getString("vUserName")); // UserName 
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

public void UpdateNodeTypeIndi(String wsId,int nodeId,char nodeTypeIndi) {
	Connection con = null;
	int rs1=0;
	//int rs2=0;
	try {
		con = ConnectionManager.ds.getConnection();
		
		String query = "UPDATE WorkspaceNodeDetail SET " + "cNodeTypeIndi = '"+nodeTypeIndi
						+ "' WHERE vWorkspaceId = '"+ wsId + "' AND iNodeId = "+ nodeId+ " ";
		
		rs1 =dataTable.getDataSet1(con,query);
							
		/*String query1 = "UPDATE WorkspaceNodeDetailHistory SET " + "cNodeTypeIndi = '"+nodeTypeIndi
						+ "' WHERE vWorkspaceId = '"+ wsId + "' AND iNodeId = "+ nodeId+ " ";
		
		rs2 =dataTable.getDataSet1(con,query1);*/
	
		
		con.close();
	}   
	
	catch(SQLException e){
		e.printStackTrace();
	}
}


public void insertIntoTracebelityMatrixDtl(DTOWorkSpaceNodeDetail dto,int mode) {
	CallableStatement proc = null;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		if(mode==1 || mode==2){
		proc = con.prepareCall("{call Insert_TracebelityMatrixDtl(?,?,?,?,?,?,?,?,?,?,?)}");
		
			proc.setString(1, dto.getClientCode());
			proc.setString(2, dto.getWorkspaceId());
			proc.setInt(3, dto.getNodeId());
			proc.setInt(4, dto.getTranNo());
			proc.setString(5, dto.getUploadFlag());
			if(mode==1){
				proc.setString(6, dto.getFRSNo());
				proc.setString(7, dto.getURSNo());
				proc.setString(8, dto.getURSDescription());
				}
			else{
				proc.setString(6, dto.getFRSNo());
				proc.setString(7, dto.getFRSNo());
				proc.setString(8, dto.getURSDescription());}
			
			proc.setString(9, dto.getRemark());
			proc.setInt(10, dto.getModifyBy());
			proc.setInt(11, mode);
			proc.execute();
			
			proc.close();
			con.close();
		}
		else{
			proc = con.prepareCall("{call Insert_TracebelityMatrixDtl(?,?,?,?,?,?,?,?,?,?,?)}");
			
			proc.setString(1, dto.getClientCode());
			proc.setString(2, dto.getWorkspaceId());
			proc.setInt(3, dto.getNodeId());
			proc.setInt(4, dto.getTranNo());
			proc.setString(5, dto.getUploadFlag());
			proc.setString(6, dto.getURSNo());
			proc.setString(7, dto.getFRSNo());
			proc.setString(8, dto.getURSDescription());
			proc.setString(9, dto.getRemark());
			proc.setInt(10, dto.getModifyBy());
			proc.setInt(11, mode);
			proc.execute();
			
			proc.close();
			con.close();
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public Vector<DTOWorkSpaceNodeDetail> getURSTracebelityMatrixDtl(String workspaceId, int nodeId, int tranNoForDtl) {
	
	Vector <DTOWorkSpaceNodeDetail> data = new Vector <DTOWorkSpaceNodeDetail>();
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	//String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	//String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	String basePath = knetProperties.getValue("signatureFile");
	ResultSet rs = null;
	String whr="vWorkSpaceId = '"+workspaceId+"' and inodeid="+nodeId+" and itranNo="+tranNoForDtl+" and vDocType='URS' and cStatusIndi<>'D' ";
	try {

		Connection con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*","TracebelityMatrixDtl",whr,"vURSNo");
		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setParentID(rs.getInt("ID"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setTranNo(rs.getInt("itranNo")); // tranNo
			dto.setFileName(rs.getString("vDocType"));
			dto.setURSNo(rs.getString("vURSNo"));
			dto.setURSDescription(rs.getString("vURSDesc")); 
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


public Vector<DTOWorkSpaceNodeDetail> getFSTracebelityMatrixDtl(String workspaceId, int nodeId, int tranNoForDtl) {
	
	Vector <DTOWorkSpaceNodeDetail> data = new Vector <DTOWorkSpaceNodeDetail>();
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	//String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	//String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	String basePath = knetProperties.getValue("signatureFile");
	ResultSet rs = null;
	String whr="vWorkSpaceId = '"+workspaceId+"' and inodeid="+nodeId+" and itranNo="+tranNoForDtl+" and vDocType='FS' and  cStatusIndi<>'D' ";
	try {

		Connection con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*","TracebelityMatrixDtl",whr,"vURSNo");
		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setParentID(rs.getInt("ID"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setTranNo(rs.getInt("itranNo")); // tranNo
			dto.setFileName(rs.getString("vDocType"));
			dto.setFRSNo(rs.getString("vFSNo"));
			dto.setURSDescription(rs.getString("vFSDesc")); 
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

public Vector<DTOWorkSpaceNodeDetail> getURSTracebelityMatrixDtl(String workspaceId) {
	
	Vector <DTOWorkSpaceNodeDetail> data = new Vector <DTOWorkSpaceNodeDetail>();
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	//String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	//String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	String basePath = knetProperties.getValue("signatureFile");
	ResultSet rs = null;
	String whr="vWorkSpaceId = '"+workspaceId+"' and cStatusIndi<>'D' ";
	try {

		Connection con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*","TracebelityMatrixDtl",whr,"ID");
		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setIDNo(String.valueOf(rs.getInt("ID")));
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setTranNo(rs.getInt("itranNo")); // tranNo
			dto.setFileName(rs.getString("vDocType"));
			dto.setURSNo(rs.getString("vURSNo"));
			dto.setURSDescription(rs.getString("vURSDesc"));
			dto.setFRSNo(rs.getString("vFSNo"));
			dto.setFSDescription(rs.getString("vFSDesc"));
			data.addElement(dto);
			dto = null;
		}
		
		/*whr="vWorkSpaceId = '"+workspaceId+"' and inodeid=cStatusIndi<>'D' ";
		
		for(int i=0;i<data.size();i++){
			whr="vWorkSpaceId = '"+workspaceId+"' and inodeid="+data.get(i).getNodeId()+" and itranNo="+data.get(i).getTranNo()+" and cStatusIndi<>'D' ";
			rs = dataTable.getDataSet(con, "vFileName","workspacenodehistory",whr,"");
			while(rs.next())
			data.get(i).setNodeName(rs.getString("vFileName"));
			
		}*/
		
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return data;
}


public Vector<DTOWorkSpaceNodeDetail> getURSTracebelityMatrixDtlForDocType(String workspaceId,String docType) {
	
	Vector <DTOWorkSpaceNodeDetail> data = new Vector <DTOWorkSpaceNodeDetail>();
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	//String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	//String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	String basePath = knetProperties.getValue("signatureFile");
	ResultSet rs = null;
	String whr="vWorkSpaceId = '"+workspaceId+"' and vDocType='"+docType+"' and cStatusIndi<>'D' ";
	try {

		Connection con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*","TracebelityMatrixDtl",whr,"ID");
		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setParentID(rs.getInt("ID"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setTranNo(rs.getInt("itranNo")); // tranNo
			dto.setFileName(rs.getString("vDocType"));
			dto.setURSNo(rs.getString("vURSNo"));
			dto.setURSDescription(rs.getString("vURSDesc"));
			dto.setFRSNo(rs.getString("vFSNo"));
			dto.setURSDescription(rs.getString("vFSDesc"));
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

public String getTracebelityMatrixDtlForAttributes(String workspaceId,String values) {
	
	//Vector <DTOWorkSpaceNodeDetail> data = new Vector <DTOWorkSpaceNodeDetail>();
	String data = "";
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	//String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	//String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	String basePath = knetProperties.getValue("signatureFile");
	ResultSet rs = null;
	String whr="workspacenodeattrdetail.vWorkSpaceId = '"+workspaceId+"' and workspacenodeattrdetail.iattrid<>-999 and  "
			//+ "TracebelityMatrixDtl.ID in("+values.substring(0,values.length()-1)+") and TracebelityMatrixDtl.cStatusIndi<>'D' ";
			+ "TracebelityMatrixDtl.ID in("+values+") and TracebelityMatrixDtl.cStatusIndi<>'D' ";
	String clientCode = knetProperties.getValue("ClientCode");
	try {

		Connection con = ConnectionManager.ds.getConnection(); 
		rs = dataTable.getDataSet(con, "TracebelityMatrixDtl.ID, TracebelityMatrixDtl.vURSNo,TracebelityMatrixDtl.vURSDesc,"
				+ "TracebelityMatrixDtl.vFSNo,TracebelityMatrixDtl.vFSDesc,workspacenodeattrdetail.vWorkspaceId,"
				+ "workspacenodeattrdetail.iNodeId,workspacenodeattrdetail.vAttrName,workspacenodeattrdetail.vAttrValue",
				"workspacenodeattrdetail inner join TracebelityMatrixDtl on TracebelityMatrixDtl.vWorkSpaceId="
				+ "workspacenodeattrdetail.vWorkspaceId and TracebelityMatrixDtl.iNodeId=workspacenodeattrdetail.iNodeid",
				whr,"TracebelityMatrixDtl.ID");
		
		while (rs.next()) {
			//if(rs.getString("vAttrValue").equalsIgnoreCase("URS")){
			if(rs.getString("vAttrName").equalsIgnoreCase("AutomatedDocumentType")){
					/*dto.setURSNo(rs.getString("vURSNo"));
					dto.setURSDescription(rs.getString("vURSDesc"));*/
				if(rs.getString("vAttrValue").equalsIgnoreCase("URS")){
					data+=rs.getString("vURSNo")+",";
				}
				else{
					if(rs.getString("vAttrValue").equalsIgnoreCase("FS")){
						if(clientCode.equalsIgnoreCase("0002")){
							/*dto.setFRSNo(rs.getString("vFSNo"));
							dto.setFSDescription(rs.getString("vFSDesc"));*/
							data+=rs.getString("vFSNo")+",";
						}
						else{
							data+=rs.getString("vURSNo")+",";
							data+=rs.getString("vFSNo")+",";
						}
					}
				}
			}
					
			/*	}
			else{
				if(clientCode.equalsIgnoreCase("0002")){
					dto.setFRSNo(rs.getString("vFSNo"));
					dto.setFSDescription(rs.getString("vFSDesc"));
					data+=rs.getString("vFSNo")+",";
				}
				else{
					data+=rs.getString("vURSNo")+",";
					data+=rs.getString("vFSNo")+",";
				}
			}*/
			/*DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setParentID(rs.getInt("ID"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setFileName(rs.getString("vAttrValue"));
			dto.setURSNo(rs.getString("vURSNo"));
			dto.setURSDescription(rs.getString("vURSDesc"));
			dto.setFRSNo(rs.getString("vFSNo"));
			dto.setFSDescription(rs.getString("vFSDesc"));
			data.addElement(dto);
			dto = null;*/
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return data;
}


public Vector<DTOWorkSpaceNodeDetail> getTracebelityMatrixDtlForDocTypeHistory(String workspaceId,int nodeId, int tranNoForDtl,String docType) {
	
	Vector <DTOWorkSpaceNodeDetail> data = new Vector <DTOWorkSpaceNodeDetail>();
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	//String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	//String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	String basePath = knetProperties.getValue("signatureFile");
	ResultSet rs = null;
	String whr="vWorkSpaceId = '"+workspaceId+"' and inodeid="+nodeId+" and itranNo="+tranNoForDtl+" and vDocType='"+docType+"' and cStatusIndi<>'D' ";
	try {

		Connection con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*","TracebelityMatrixDtl",whr,"ID");
		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setParentID(rs.getInt("ID"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setTranNo(rs.getInt("itranNo")); // tranNo
			dto.setFileName(rs.getString("vDocType"));
			dto.setURSNo(rs.getString("vURSNo"));
			dto.setURSDescription(rs.getString("vURSDesc"));
			dto.setFRSNo(rs.getString("vFSNo"));
			dto.setURSDescription(rs.getString("vFSDesc"));
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

public Vector<DTOWorkSpaceNodeDetail> getURSTracebelityMatrixDtlToCheck(String workspaceId,String Automated_Doc_Type) {
	
	Vector <DTOWorkSpaceNodeDetail> data = new Vector <DTOWorkSpaceNodeDetail>();
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	//String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	//String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	String basePath = knetProperties.getValue("signatureFile");
	ResultSet rs = null;
	String whr="vWorkSpaceId = '"+workspaceId+"' and vDocType='"+Automated_Doc_Type+"' and cStatusIndi<>'D' ";
	try {

		Connection con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*","TracebelityMatrixDtl",whr,"vURSNo");
		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setParentID(rs.getInt("ID"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setTranNo(rs.getInt("itranNo")); // tranNo
			dto.setFileName(rs.getString("vDocType"));
			dto.setURSNo(rs.getString("vURSNo"));
			dto.setURSDescription(rs.getString("vURSDesc")); 
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


public Vector<DTOWorkSpaceNodeDetail> getFSTracebelityMatrixDtlToCheck(String workspaceId,String Automated_Doc_Type) {
	
	Vector <DTOWorkSpaceNodeDetail> data = new Vector <DTOWorkSpaceNodeDetail>();
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	//String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	//String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	String basePath = knetProperties.getValue("signatureFile");
	ResultSet rs = null;
	String whr="vWorkSpaceId = '"+workspaceId+"' and vDocType='"+Automated_Doc_Type+"' and cStatusIndi<>'D' ";
	try {

		Connection con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*","TracebelityMatrixDtl",whr,"vURSNo");
		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setParentID(rs.getInt("ID"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setTranNo(rs.getInt("itranNo")); // tranNo
			dto.setFileName(rs.getString("vDocType"));
			dto.setURSNo(rs.getString("vURSNo"));
			dto.setURSDescription(rs.getString("vURSDesc")); 
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


public Vector<DTOWorkSpaceNodeDetail>  getWorkSpaceDetailForChangeRequestGridListForESignature(String wsId,int userGroupCode,int userCode) {
	Vector<DTOWorkSpaceNodeDetail> nodeInfo = new Vector<DTOWorkSpaceNodeDetail>();
	Connection con = null;
	
	try  
	{
		/*DocMgmtImpl docmgmt=new DocMgmtImpl();
		con=ConnectionManager.ds.getConnection();
		String Fields="Distinct WorkspaceId,NodeNo,NodeId,NodeDisplayName,NodeName,ParentNodeId,Foldername," +
		"canReadFlag,canAddFlag,canEditFlag,statusIndi,nodeTypeIndi,requiredFlag,canDeleteFlag,iformNo";
		//String Where="WorkspaceId='"+wsId+"'  and Cloneflag='y' and usergroupcode="+userGroupCode+" and usercode="+userCode+" and statusIndi<>'D'";
		String Where="WorkspaceId='"+wsId+"'  and Cloneflag='y' and statusIndi<>'D'";
		ResultSet rs = dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail_WSListForESignature" , Where,"NodeId desc" ); */
		DocMgmtImpl docmgmt=new DocMgmtImpl();
		con=ConnectionManager.ds.getConnection();
		String Fields="distinct wnd.WorkspaceId,wnd.NodeNo,wnd.NodeId,wnd.NodeDisplayName,wnd.NodeName,wnd.ParentNodeId," +
		"wnd.Foldername,wnd.canReadFlag,wnd.canAddFlag,wnd.canEditFlag,wnd.statusIndi,wnd.nodeTypeIndi,wnd.requiredFlag,wnd.canDeleteFlag,wnd.iformNo";
		String Where="wnd.WorkspaceId='"+wsId+"'  and (wnd.usercode="+userCode+" and wnd.usergroupcode="+userGroupCode+" and wnd.Cloneflag='y' "
				+ " and statusIndi<>'D' or wnh.istageid=10 and wnh.iModifyBy="+userCode+" )";
		//String Where="WorkspaceId='"+wsId+"'  and Cloneflag='y' and statusIndi<>'D'";
		ResultSet rs = dataTable.getDataSet(con,Fields,"View_WorkSpaceNodeRightsDetail_WSListForESignature as wnd inner join "
				+ "workspacenodehistory as wnh on wnd.workspaceid=wnh.vworkspaceid and wnd.nodeid=wnh.inodeid" , Where,"wnd.NodeId desc" ); 
		while (rs.next())    
		{
			
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setNodeId(rs.getInt("NodeId"));
			dto.setNodeDisplayName(rs.getString("NodeDisplayName"));
			dto.setParentNodeId(rs.getInt("ParentNodeId"));
			dto.setFolderName(rs.getString("FolderName"));
			dto.setCanReadFlag(rs.getString("CanReadFlag"));
			dto.setCanAddFlag(rs.getString("canAddFlag"));
			dto.setCanEditFlag(rs.getString("CanEditFlag"));
			dto.setCanDeleteFlag(rs.getString("CanDeleteFlag"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
			dto.setNodeTypeIndi(rs.getString("nodeTypeIndi").charAt(0));
			dto.setRequiredFlag(rs.getString("requiredFlag").charAt(0));
			dto.setIformNo(rs.getInt("iformNo"));
			dto.setNodeNo(rs.getInt("NodeNo"));
			dto.setNodeName(rs.getString("NodeName"));
			dto.setPublishFlag(docmgmt.submittedNodeIdDetail(wsId,rs.getInt("NodeId")));
			//dto.setLokedNodeFlag(docmgmt.isCheckOut(wsId, rs.getInt("NodeId"),userCode));
			//dto.setPublishFlag(docmgmt.submittedNodeIdDetail(wsId,rs.getInt("NodeId")));
			dto.setLokedNodeFlag(true);
			dto.setPublishFlag(true);
			nodeInfo.addElement(dto);	
		}
		rs.close();
		con.close();
	}
	
	catch(Exception e)  {
		e.printStackTrace();
	}
	
	return nodeInfo;			
}


public Vector<DTOWorkSpaceNodeDetail> getNodeDetailByNodeDisplayName(String wsId, String nDisplayName) {

	Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	Connection con = null;
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = " vWorkspaceId='" + wsId + "'" + "and cStatusIndi<>'D' and "
				+ "vnodedisplayname like '" + nDisplayName + "%' and crequiredflag='A' ";

		rs = dataTable.getDataSet(con, "*","WorkSpaceNodeDetail", Where, "");

		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
			dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
			dto.setFolderName(rs.getString("vFolderName")); // folderName
			dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
			dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
			dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
			dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
			dto.setRemark(rs.getString("vRemark")); // remark
			dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
			dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
			wsNodeDetail.addElement(dto);
			dto = null;
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return wsNodeDetail;
}

public Vector<DTOWorkSpaceNodeDetail> getNodeDetailByNodeDisplayNameForReplication(String wsId, String nDisplayName) {

	Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
	ResultSet rs = null;
	Connection con = null;
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = " vWorkspaceId='" + wsId + "'" + "and cStatusIndi<>'D' and crequiredflag in ('n','f') and inodeid not in "
				+ "( select distinct iparentnodeid from workspacenodedetail where  crequiredflag in ('n','f')  and  "
				+ "vWorkspaceId = '" + wsId + "' ) and vnodedisplayname ='"+nDisplayName+"' ";

		rs = dataTable.getDataSet(con, "*","WorkSpaceNodeDetail", Where, "");

		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setNodeNo(rs.getInt("iNodeNo")); // nodeNo
			dto.setNodeName(rs.getString("vNodeName")); // nodeName
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName")); // nodeDisplayName
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0)); // nodeTypeIndi
			dto.setParentNodeId(rs.getInt("iParentNodeId")); // parentNodeId
			dto.setFolderName(rs.getString("vFolderName")); // folderName
			dto.setCloneFlag(rs.getString("cCloneFlag").charAt(0)); // cloneFlag
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0)); // requiredFlag
			dto.setCheckOutOn(rs.getTimestamp("dCheckOutOn")); // checkedOutOn
			dto.setCheckOutBy(rs.getInt("iCheckOutBy")); // checkedOutBy
			dto.setPublishedFlag(rs.getString("cPublishFlag").charAt(0)); // publishFlag
			dto.setRemark(rs.getString("vRemark")); // remark
			dto.setModifyBy(rs.getInt("iModifyBy")); // modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn")); // modifyOn
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0)); // statusIndi
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat")); // defaultFileFormat
			dto.setDocTemplatePath(rs.getString("vDocTemplatePath")); // docTemplatePath
			wsNodeDetail.addElement(dto);
			dto = null;
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return wsNodeDetail;
}

public Vector<DTOWorkSpaceNodeDetail> getTracebelityMatrixDtlByID(String workspaceId, String id) {
	
	Vector <DTOWorkSpaceNodeDetail> data = new Vector <DTOWorkSpaceNodeDetail>();
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	//String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	//String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	String basePath = knetProperties.getValue("signatureFile");
	ResultSet rs = null;
	String whr="vWorkSpaceId = '"+workspaceId+"' and ID in("+id+") and cStatusIndi<>'D' ";
	try {

		Connection con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*","TracebelityMatrixDtl",whr,"ID");
		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setParentID(rs.getInt("ID"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setTranNo(rs.getInt("itranNo")); // tranNo
			dto.setFileName(rs.getString("vDocType"));
			dto.setURSNo(rs.getString("vURSNo"));
			dto.setURSDescription(rs.getString("vURSDesc")); 
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


public Vector <DTOWorkSpaceNodeDetail> getTracebelityMatrixDtlForAttributesToShow(String workspaceId,String values) {
	
	Vector <DTOWorkSpaceNodeDetail> data = new Vector <DTOWorkSpaceNodeDetail>();
	//String data = "";
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	//String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	//String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	String basePath = knetProperties.getValue("signatureFile");
	ResultSet rs = null;
	String whr="workspacenodeattrdetail.vWorkSpaceId = '"+workspaceId+"' and workspacenodeattrdetail.iattrid<>-999 and  "
			//+ "TracebelityMatrixDtl.ID in("+values.substring(0,values.length()-1)+") and TracebelityMatrixDtl.cStatusIndi<>'D' ";
			+ "TracebelityMatrixDtl.ID in("+values+") and TracebelityMatrixDtl.cStatusIndi<>'D' ";
	String clientCode = knetProperties.getValue("ClientCode");
	try {

		Connection con = ConnectionManager.ds.getConnection(); 
		rs = dataTable.getDataSet(con, "TracebelityMatrixDtl.ID, TracebelityMatrixDtl.vURSNo,TracebelityMatrixDtl.vURSDesc,"
				+ "TracebelityMatrixDtl.vFSNo,TracebelityMatrixDtl.vFSDesc,workspacenodeattrdetail.vWorkspaceId,"
				+ "workspacenodeattrdetail.iNodeId,workspacenodeattrdetail.vAttrName,workspacenodeattrdetail.vAttrValue",
				"workspacenodeattrdetail inner join TracebelityMatrixDtl on TracebelityMatrixDtl.vWorkSpaceId="
				+ "workspacenodeattrdetail.vWorkspaceId and TracebelityMatrixDtl.iNodeId=workspacenodeattrdetail.iNodeid",
				whr,"TracebelityMatrixDtl.ID");
		
		while (rs.next()) {
			DTOWorkSpaceNodeDetail dto=new DTOWorkSpaceNodeDetail();
			//if(rs.getString("vAttrValue").equalsIgnoreCase("URS")){
			if(rs.getString("vAttrName").equalsIgnoreCase("AutomatedDocumentType")){
					/*dto.setURSNo(rs.getString("vURSNo"));
					dto.setURSDescription(rs.getString("vURSDesc"));*/
				if(rs.getString("vAttrValue").equalsIgnoreCase("URS")){
					/*data+=rs.getString("vURSNo")+",";
					data+=rs.getString("vURSDesc");
				}*/
					dto.setIDNo(rs.getString("ID"));
					dto.setURSNo(rs.getString("vURSNo"));
					dto.setURSDescription(rs.getString("vURSDesc"));
					dto.setFileName(rs.getString("vAttrValue"));
				}
				else{
					if(clientCode.equalsIgnoreCase("0002")){
						dto.setIDNo(rs.getString("ID"));
						dto.setFRSNo(rs.getString("vFSNo"));
						dto.setFSDescription(rs.getString("vFSDesc"));
						dto.setFileName(rs.getString("vAttrValue"));
						//data+=rs.getString("vFSDesc")+",";
					}
					else{
						dto.setIDNo(rs.getString("ID"));
						dto.setURSNo(rs.getString("vURSNo"));
						dto.setURSDescription(rs.getString("vURSDesc"));
						dto.setFRSNo(rs.getString("vFSNo"));
						dto.setFSDescription(rs.getString("vFSDesc"));
						dto.setFileName(rs.getString("vAttrValue"));
					}
				}
				data.addElement(dto);
			}
					
			/*	}
			else{
				if(clientCode.equalsIgnoreCase("0002")){
					dto.setFRSNo(rs.getString("vFSNo"));
					dto.setFSDescription(rs.getString("vFSDesc"));
					data+=rs.getString("vFSNo")+",";
				}
				else{
					data+=rs.getString("vURSNo")+",";
					data+=rs.getString("vFSNo")+",";
				}
			}*/
			/*DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setParentID(rs.getInt("ID"));
			dto.setWorkspaceId(rs.getString("vWorkspaceId")); // workspaceId
			dto.setNodeId(rs.getInt("iNodeId")); // nodeId
			dto.setFileName(rs.getString("vAttrValue"));
			dto.setURSNo(rs.getString("vURSNo"));
			dto.setURSDescription(rs.getString("vURSDesc"));
			dto.setFRSNo(rs.getString("vFSNo"));
			dto.setFSDescription(rs.getString("vFSDesc"));
			data.addElement(dto);
			dto = null;*/
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return data;
}

public String getScriptCodesForTM(String workspaceId,String values) {
	
	String data = "";
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();	
	ResultSet rs = null;
	String Where = " vWorkspaceId='" + workspaceId + "'" + " and vattrvalue like '%" + values + "%' and "
			+ "vattrname<>'FileLastModified' and cstatusindi<>'D'";
	try {

		Connection con = ConnectionManager.ds.getConnection(); 
		rs = dataTable.getDataSet(con, "iNodeId","workspacenodeattrdetail",Where,"");
		
		while (rs.next()) {
			if(data=="")
				data=String.valueOf(rs.getInt("iNodeId"));
			else
				data=data+","+String.valueOf(rs.getInt("iNodeId"));
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return data;
}

}// main class end
