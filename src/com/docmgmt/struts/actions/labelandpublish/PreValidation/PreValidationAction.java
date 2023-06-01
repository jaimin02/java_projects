package com.docmgmt.struts.actions.labelandpublish.PreValidation;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class PreValidationAction extends ActionSupport {
	private static final long serialVersionUID = -1421723718617899109L;
	private DocMgmtImpl docMgmtImpl= new DocMgmtImpl();
	
	public String workspaceId;
	public boolean isFileForPublish;
	public boolean checkOnlyFiles=false;
	public ArrayList<DTOWorkSpaceNodeAttrDetail> workspaceNodeAttrList= new ArrayList<DTOWorkSpaceNodeAttrDetail>();
	public ArrayList<DTOWorkSpaceNodeHistory> workspaceNodeHistoryList = new ArrayList<DTOWorkSpaceNodeHistory>();
	
	@Override
	public String execute() throws Exception 
	{
		isFileForPublish = getFilesForPublish();
		return SUCCESS;
	}
	private boolean getFilesForPublish()
	{
		workspaceNodeHistoryList = docMgmtImpl.getPublishableFilesAfterLastConfirmSeq(workspaceId); 
		if(workspaceNodeHistoryList.size() > 0)
		{
			isFileForPublish = true;
			if(checkOnlyFiles == false)
				getPublishableNodeStructure();
		}
		else
		{
			isFileForPublish = false;
		}
		return isFileForPublish;
	}
	
	private void getPublishableNodeStructure() 
	{
		
		int[] nodeIds = new int[workspaceNodeHistoryList.size()];
		Vector<Integer> nodeIdList;
		ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrList;
		for (int itrNodeIds = 0; itrNodeIds < workspaceNodeHistoryList.size(); itrNodeIds++) 
		{
			nodeIds[itrNodeIds] = workspaceNodeHistoryList.get(itrNodeIds).getNodeId();			
		}
		
		nodeIdList =docMgmtImpl.getWorkspaceTreeNodesForLeafs(workspaceId,nodeIds);

		nodeAttrList = getNodeLevelAttribute();
		for (int itrNodeIdList = 0; itrNodeIdList < nodeIdList.size(); itrNodeIdList++) 
		{
			int nodeId = nodeIdList.get(itrNodeIdList);
			for (int itrNodeAttrList = 0; itrNodeAttrList < nodeAttrList.size(); itrNodeAttrList++) 
			{
				DTOWorkSpaceNodeAttrDetail dtoWsNodeAttrDtl = nodeAttrList.get(itrNodeAttrList);
				if(dtoWsNodeAttrDtl.getNodeId() == nodeId)
					workspaceNodeAttrList.add(dtoWsNodeAttrDtl);
			}
		}
	}
	
	private ArrayList<DTOWorkSpaceNodeAttrDetail> getNodeLevelAttribute()
	{
		ArrayList<String> workspaceIdList = new ArrayList<String>();
		workspaceIdList.add(workspaceId);
		ArrayList<DTOWorkSpaceNodeAttrDetail> workspaceNodeAttrList = docMgmtImpl.getWorkspaceAttrDtlByAttrType(workspaceIdList);
		ArrayList<DTOWorkSpaceNodeAttrDetail> wsNodeAttrList = new ArrayList<DTOWorkSpaceNodeAttrDetail>();
		ArrayList<DTOAttributeMst> attributeMstList = docMgmtImpl.getAttributesByDisplayType("text");
		for (int itrAttrList = 0; itrAttrList < attributeMstList.size(); itrAttrList++) 
		{
			DTOAttributeMst dtoAttrMst = attributeMstList.get(itrAttrList);
			for (int itrWsNdAttrList = 0; itrWsNdAttrList < workspaceNodeAttrList.size(); itrWsNdAttrList++) 
			{
				DTOWorkSpaceNodeAttrDetail dtoWsNdAttrDtl = workspaceNodeAttrList.get(itrWsNdAttrList);
				if (dtoAttrMst.getAttrId() == dtoWsNdAttrDtl.getAttrId()) 
				{
					wsNodeAttrList.add(dtoWsNdAttrDtl);
				}
			}
		}
		return wsNodeAttrList;
	}
}