package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.docmgmt.dto.DTOAttrReferenceTableMatrix;
import com.docmgmt.dto.DTODynamicTable;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.entities.AttributeType;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddDocAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String workSpaceId;
	public String workSpaceList;
	public String htmlContent;
	public String nodeId;
	public String parentFolderNm;
	public String nodeFolderNm;
	public String folderName;
	public String mtype="";
	public int attrCount;
	public String pdfFile="";
	public ArrayList<DTOWorkSpaceMst> workspaceMstList;
	public Vector<DTOWorkSpaceNodeDetail> workspaceNodeDtls;
	public Vector<DTOWorkSpaceNodeDetail> workspaceChildNodeDtls;
	public Vector<DTOWorkSpaceUserRightsMst> workspaceUserRightsMstList;
	public DTOWorkSpaceNodeDetail dtoNodeDetail = new DTOWorkSpaceNodeDetail();
	public DTOWorkSpaceNodeHistory dtoWsNodeHis = new DTOWorkSpaceNodeHistory();
	public ArrayList<Object[]> filterDynamicList= new ArrayList<Object[]>();
	public ArrayList< Object[]> filterAutoCompleterList = new ArrayList<Object[]>();
	public Vector<DTOWorkSpaceNodeAttribute> attrList = new Vector<DTOWorkSpaceNodeAttribute>();
	
	@Override
	public String execute()
	{
		DTOWorkSpaceNodeDetail dtoWsNodeDtl = new DTOWorkSpaceNodeDetail();
		if(nodeId != null && !nodeId.equals("") )
		{
			int[] nodeIds = new int[1];
			nodeIds[0]=Integer.parseInt(nodeId);
			ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistoryList;
			
			wsNodeHistoryList = docMgmtImpl.getRefWorkspaceNodes(workSpaceId,Integer.parseInt(nodeId));
			for (int itrWsNodeHis = 0; itrWsNodeHis < wsNodeHistoryList.size(); itrWsNodeHis++) 
			{
				dtoWsNodeHis = wsNodeHistoryList.get(itrWsNodeHis);
			}
			
			/*
			ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistoryList = docMgmtImpl.getAllNodesLastHistory(workSpaceId,nodeIds);
			for (int itrWsNodeHis = 0; itrWsNodeHis < wsNodeHistoryList.size(); itrWsNodeHis++) 
			{
				dtoWsNodeHis = wsNodeHistoryList.get(itrWsNodeHis);
			}
			*/
			
		}
		
		workspaceMstList = new ArrayList<DTOWorkSpaceMst>();
		workspaceNodeDtls = new Vector<DTOWorkSpaceNodeDetail>();
		workspaceChildNodeDtls = new Vector<DTOWorkSpaceNodeDetail>();
		
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		if (workSpaceId == null || workSpaceId.trim().equalsIgnoreCase("")) 
		{
			workspaceMstList = docMgmtImpl.getWorkspaceDtByProjPublishType(userId, userGroupCode, ProjectType.DMS_DOC_CENTRIC);
			for (int i = 0; i < workspaceMstList.size(); i++) {
				DTOWorkSpaceMst dtoWsMst = workspaceMstList.get(i);
				workspaceNodeDtls = docMgmtImpl.getNodeDetail(dtoWsMst.getWorkSpaceId(), 1);
				dtoWsMst.setRootNodeDtl(workspaceNodeDtls.get(0));
			}
			return SUCCESS;
		}
		workspaceChildNodeDtls = docMgmtImpl.getChildNodeByParent(1, workSpaceId);
		for (int i = 0; i < workspaceChildNodeDtls.size(); i++) {
			dtoNodeDetail = workspaceChildNodeDtls.get(i);
		}
		
		Vector<DTOWorkSpaceNodeDetail> wsNodeDtl = new Vector<DTOWorkSpaceNodeDetail>();
		try {
			int nId = Integer.parseInt(nodeId);
			
			if( nId != 0)
			{
				Calendar c=new GregorianCalendar();		
				
				int currYear = c.get(Calendar.YEAR);
			
				Vector<DTOWorkSpaceNodeDetail> wsGetParentFolderNm = docMgmtImpl.getNodeDetail(workSpaceId, 1);
				
				for (int i = 0; i < wsGetParentFolderNm.size(); i++) {
					DTOWorkSpaceNodeDetail dto = wsGetParentFolderNm.get(i);
					parentFolderNm = dto.getFolderName();
				}
				
				wsNodeDtl = docMgmtImpl.getNodeDetail(workSpaceId, nId);
				
				for (int i = 0; i < wsNodeDtl.size(); i++) {
					DTOWorkSpaceNodeDetail dto = wsNodeDtl.get(i);
					nodeFolderNm = dto.getFolderName();
				}
				
				 Vector<DTOWorkSpaceNodeDetail> wsGetChildNode = docMgmtImpl.getChildNodeByParent(nId, workSpaceId);
				
				if(wsGetChildNode.size() == 0)
				{
					folderName = parentFolderNm+"-"+nodeFolderNm+"-"+currYear+"-"+"001";
					dtoWsNodeDtl.setFolderName(folderName);
				}
				else
				{
					String str="";
					String finalStr = "";									
					DTOWorkSpaceNodeDetail dto = wsGetChildNode.get(wsGetChildNode.size()-1);
					String maxFolderNm = dto.getFolderName();
					int maxVal = maxFolderNm.lastIndexOf("-",maxFolderNm.length());					
					str=maxFolderNm.substring(maxVal+1, maxFolderNm.length());									
					finalStr=getDocIdByStr(str);								
					folderName = parentFolderNm+"-"+nodeFolderNm+"-"+currYear+"-"+finalStr;
					dtoWsNodeDtl.setFolderName(folderName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		attrList = docMgmtImpl.getNodeAttrByAttrForIndiId(workSpaceId, Integer.parseInt(nodeId),AttributeType.USER_DEFINED_ATTR);
		filterAutoCompleterList = getAttrDetail(attrList,"AutoCompleter");
		filterDynamicList = getAttrDetail(attrList, "Dynamic");

		if (mtype.trim().equalsIgnoreCase("n")) 
			return SUCCESS;		
		else
			return "RELEASEDOC";
	}
	
	private String getDocIdByStr(String docId)
	{		
		int nZeroValue=Integer.parseInt(docId);
		int finalValue=nZeroValue+1;
		int i=docId.length();
		int j=Integer.toString(finalValue).length();				
		String finalValueStr=Integer.toString(finalValue);
		for(int k=0; k<(i-j); k++)
		{			
			finalValueStr = "0" + finalValueStr;
		}			
		return finalValueStr;
	}
	
	public String ShowCategory()
	{
		DTOWorkSpaceNodeDetail dtoWsNodeDtl = new DTOWorkSpaceNodeDetail();
		workspaceChildNodeDtls = new Vector<DTOWorkSpaceNodeDetail>();
		workspaceUserRightsMstList = new Vector<DTOWorkSpaceUserRightsMst>();
		Vector<DTOWorkSpaceUserRightsMst> workspaceUserRightsMstList;
		Vector<DTOWorkSpaceNodeDetail> wsChildNodeDtls;
		DTOWorkSpaceUserRightsMst dtoUserRightsMst = new DTOWorkSpaceUserRightsMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		dtoUserRightsMst.setWorkSpaceId(workSpaceId);
		dtoUserRightsMst.setParentNodeId(0);
		dtoUserRightsMst.setUserCode(userId);
		workspaceUserRightsMstList  = docMgmtImpl.getUserRightsReport(dtoUserRightsMst, false);
		wsChildNodeDtls = docMgmtImpl.getChildNodeByParent(1, workSpaceId);
		for (int i = 0; i < workspaceUserRightsMstList.size(); i++) 
		{
			DTOWorkSpaceUserRightsMst dtoWsUserMst = workspaceUserRightsMstList.get(i);
			for (int j = 0; j < wsChildNodeDtls.size(); j++) 
			{
				DTOWorkSpaceNodeDetail dtoWsNodeDtl1 = wsChildNodeDtls.get(j);
				if(dtoWsNodeDtl1.getNodeId() == dtoWsUserMst.getNodeId())
				{
					workspaceChildNodeDtls.add(dtoWsNodeDtl1);
				}
			}
		}
		htmlContent = "<option id=\"All##All\" value=\"All##All\" style=\"display: block;\"></option>";
		for (int itrOption = 0; itrOption < workspaceChildNodeDtls.size(); itrOption++) 
		{
			dtoWsNodeDtl = workspaceChildNodeDtls.get(itrOption);
			htmlContent += "<option id=\""+dtoWsNodeDtl.getNodeId()+"\" value=\""+dtoWsNodeDtl.getNodeId()+"\" style=\"display: block;\">";
			htmlContent += dtoWsNodeDtl.getNodeName();
			htmlContent +="</option>";
		}
		
		return "CategoryDetails";
	}

	private ArrayList<Object[]> getAttrDetail(Vector<DTOWorkSpaceNodeAttribute> attrDtl,String attrType)
	{
		if(attrDtl == null || attrType == null){
			return null;
		}
		ArrayList<Object[]> filterAttributeList = new ArrayList<Object[]>();
		for (int i = 0; i < attrDtl.size(); i++) {
			DTOWorkSpaceNodeAttribute dtoWsNdAttribute= attrDtl.get(i);
			if(attrType.equalsIgnoreCase("AutoCompleter") && dtoWsNdAttribute.getAttrType().equalsIgnoreCase("AutoCompleter"))
			{
				ArrayList<String> attrValueList = new ArrayList<String>();
				for (int j = 0; j < attrDtl.size(); j++) {
					DTOWorkSpaceNodeAttribute dtoWsNdAttr = attrDtl.get(j);
					if(dtoWsNdAttr.getAttrId() == dtoWsNdAttr.getAttrId()){
						attrValueList.add(dtoWsNdAttr.getAttrMatrixValue());
						attrDtl.remove(j--);
					}
				}
				attrDtl.add(i, dtoWsNdAttribute);
				filterAttributeList.add(new Object[]{dtoWsNdAttribute.getAttrId(),attrValueList});
			}
			if(attrType.equals("Dynamic") && dtoWsNdAttribute.getAttrType().equals(attrType))
			{
				ArrayList<String> attrValueList = new ArrayList<String>();
				DTOAttrReferenceTableMatrix dtoAttrReferenceTableMatrix = new DTOAttrReferenceTableMatrix();
				dtoAttrReferenceTableMatrix.setiAttrId(dtoWsNdAttribute.getAttrId());
				ArrayList<DTOAttrReferenceTableMatrix> attrReferenceTableMatrixs = docMgmtImpl.getFromAttrReferenceTableMatrix(dtoAttrReferenceTableMatrix);
				if (attrReferenceTableMatrixs != null && attrReferenceTableMatrixs.size() > 0 && attrReferenceTableMatrixs.size() <= 1)
				{
					DTOAttrReferenceTableMatrix dtoAttrReferenceTableMatrix2 = attrReferenceTableMatrixs.get(0);
					String tableName = dtoAttrReferenceTableMatrix2.getvTableName();
					String tableColumn = dtoAttrReferenceTableMatrix2.getvTableColumn();
					DTODynamicTable dtoDynamicTable = docMgmtImpl.getCompleteTableDetails(tableName);
								
					int idxColumn = dtoDynamicTable.getColumnNames().indexOf(tableColumn);
					
					if (idxColumn >= 0 && idxColumn < dtoDynamicTable.getColumnNames().size())
					{
						for (int indTableRecord = 0 ; indTableRecord < dtoDynamicTable.getTableData().size() ; indTableRecord ++)
						{
							attrValueList.add(dtoDynamicTable.getTableData().get(indTableRecord).get(idxColumn));
						}
					}
				}
				filterAttributeList.add(new Object[]{dtoWsNdAttribute.getAttrId(),attrValueList});
			}
		}
		return filterAttributeList;
	}
}