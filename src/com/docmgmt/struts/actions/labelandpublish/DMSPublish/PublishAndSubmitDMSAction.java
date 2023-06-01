package com.docmgmt.struts.actions.labelandpublish.DMSPublish;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.docmgmt.dto.DTOInternalLabelMst;
import com.docmgmt.dto.DTOSubmissionInfoDMSDtl;
import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.dto.DTOSubmittedWorkspaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.WorkspaceDynamicNodeCheckTreeBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PublishAndSubmitDMSAction extends ActionSupport
{ 	
	private static final long serialVersionUID = -8286843026248470931L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	private SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	
	public Vector<DTOWorkSpaceMst> wsMstList;
	public String WsId;
	public ArrayList<DTOSubmissionInfoDMSDtl> subInfoDMSDtlList;
	public DTOWorkSpaceMst dtoWsMstInfo;
	public String htmlContent ;
	public String currentSeqNumber;
	public String submissionMode;
	public String submissionDesc;
	public String dateOfSubmission;
	public String subDtlId;
	public String currSeqNo;
	public String submissionId;
	
	@Override
	public String execute() throws Exception {
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String defaultWorkSpaceId = ActionContext.getContext().getSession().get("defaultWorkspace").toString();
		if(defaultWorkSpaceId != null && !defaultWorkSpaceId.equals("")){
			WsId = defaultWorkSpaceId;
		}
		wsMstList = docMgmtImpl.getUserWorkspace(userGroupCode, userCode);
		for (int itrWsList = 0; itrWsList < wsMstList.size(); itrWsList++) {
			DTOWorkSpaceMst dto = wsMstList.get(itrWsList);
			if(dto.getProjectType() != 'E')
			{
				wsMstList.remove(itrWsList);
				itrWsList--;
			}
		}
		return SUCCESS;
	}
	
	public String getWorkspaceSubDtl()
	{
		
		ArrayList<String> workspaceIdList = new ArrayList<String>();
		
		workspaceIdList.add(WsId);
		dtoWsMstInfo = docMgmtImpl.getWorkSpaceDetail(WsId);
		if(dtoWsMstInfo.getLastPublishedVersion() != null)
		{
			System.out.println(dtoWsMstInfo.getLastPublishedVersion() + "*******dtoWsMstInfo.getLastPublishedVersion()");
			//For current sequence number (copy from publish and submit action.)
			String lastPublishedVersion = dtoWsMstInfo.getLastPublishedVersion(); 		
			if(lastPublishedVersion.equals("-999"))
			{
				currentSeqNumber = "0000";
			}
			else
			{
				int iSeqNo = Integer.parseInt(lastPublishedVersion);
				iSeqNo++;
				String strSeqNo = "000"+iSeqNo;
				currentSeqNumber = strSeqNo.substring(strSeqNo.length()-4);
			}
			ArrayList<DTOWorkSpaceMst> wsMstArrList=docMgmtImpl.getDMSSubmissionInfo(workspaceIdList);
			if(wsMstArrList.size() > 0)
			{
				DTOWorkSpaceMst dtoWsMst = wsMstArrList.get(0);
				DTOSubmissionMst dtoSubMst = dtoWsMst.getSubmissionMst();
				subInfoDMSDtlList = dtoSubMst.getSubmissionInfoDMSDtlsList();
			}
			else
			{
				subInfoDMSDtlList = new ArrayList<DTOSubmissionInfoDMSDtl>();
			}
		}
		return SUCCESS;
	}

	public String saveSubmissionDtl()
	{
		int userId= Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		Date date = null;
		/* 
		Created By: Rahul Goswami
		Purpose: Create a new label for DMS publish.
		Desc.: copy from publish and submit action. 
		*/
		DTOInternalLabelMst newLabel=createWorkspaceLabel(WsId, userId);
		String newLabelId =newLabel.getLabelId();
		try{
			date = df.parse(dateOfSubmission);
		} catch (Exception e){e.printStackTrace();}
		Timestamp ts = new Timestamp(date.getTime());
		
		DTOSubmissionInfoDMSDtl dtoSubInfoDMSDtl = new DTOSubmissionInfoDMSDtl();
		dtoSubInfoDMSDtl.setWorkspaceid(WsId);
		dtoSubInfoDMSDtl.setCurrentSeqNumber(currentSeqNumber);
		dtoSubInfoDMSDtl.setRelatedSeqNumber(null);
		dtoSubInfoDMSDtl.setLabelId(newLabelId);
		dtoSubInfoDMSDtl.setSubmissionMode(submissionMode);
		dtoSubInfoDMSDtl.setSubmissionType("");
		dtoSubInfoDMSDtl.setSubmissionDesc(submissionDesc);
		dtoSubInfoDMSDtl.setDateOfSubmission(ts);
		dtoSubInfoDMSDtl.setSubmittedOn(new Timestamp(new Date().getTime()));
		dtoSubInfoDMSDtl.setConfirm('N');
		dtoSubInfoDMSDtl.setConfirmBy(0);
		dtoSubInfoDMSDtl.setRemark("");
		dtoSubInfoDMSDtl.setModifyBy(userId);
		docMgmtImpl.insertSubmissionInfoDMSDtl(dtoSubInfoDMSDtl, 1);
		htmlContent="Data Saved Successfully.";
		return SUCCESS;
	}

	public String confirmDMSPublishedSeq()
	{
		int userId= Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		//here mode 0 = get max label number of that project.
		//and 1 = get max confirm label number.
		int maxConfirmLabelNo = docMgmtImpl.getMaxLabelNoForDMSPublish(WsId, 1);
		ArrayList<DTOWorkSpaceNodeDetail> prevNodeList = docMgmtImpl.getDetailOfSubmission(WsId, maxConfirmLabelNo);
		long subDtlIdLong = Long.parseLong(subDtlId);
		DTOSubmissionInfoDMSDtl dtoSubInfoDMSDtl = docMgmtImpl.getDMSSubmissionInfoBySubId(subDtlIdLong);
		int selectdLabelNo = dtoSubInfoDMSDtl.getLabelNo();
		ArrayList<DTOWorkSpaceNodeDetail> currNodeList = docMgmtImpl.getDetailOfSubmission(WsId, selectdLabelNo);
		//Remove the common nodes from 'currNodeList'  
		nodesToConfirm(prevNodeList, currNodeList);
	
		//if there is no change is previous confirm sequence then current sequence can not be confirm.
		if(currNodeList.size() != 0)
		{
			// Update lastPublishedVersion in workspaceMst
			docMgmtImpl.updatePublishedVersion(WsId);
			//set for confirm the sequence
			dtoSubInfoDMSDtl.setConfirm('Y');
			dtoSubInfoDMSDtl.setConfirmBy(userId);
			dtoSubInfoDMSDtl.setModifyBy(userId);
			docMgmtImpl.insertSubmissionInfoDMSDtl(dtoSubInfoDMSDtl, 2); //Update Published Version
			
			DTOWorkSpaceMst dtoWsMst = docMgmtImpl.getWorkSpaceDetail(WsId);
			//Insert into SubmittedWorkspaceNodeDetail
			ArrayList<DTOSubmittedWorkspaceNodeDetail> subNodeDtlLst = new ArrayList<DTOSubmittedWorkspaceNodeDetail>();
			for (Iterator<DTOWorkSpaceNodeDetail> iterator = currNodeList.iterator(); iterator.hasNext();) {
				DTOWorkSpaceNodeDetail nDtl = iterator.next();
				DTOSubmittedWorkspaceNodeDetail dtoSubNodeDtl = new DTOSubmittedWorkspaceNodeDetail();
				dtoSubNodeDtl.setNodeId(nDtl.getNodeId());
				dtoSubNodeDtl.setIndexId("node-"+nDtl.getNodeId());//Setting the index value as per the ID attribute value of LEAF element is given in the publish logic.
				dtoSubNodeDtl.setWorkspaceId(WsId);
				dtoSubNodeDtl.setSubmissionId(subDtlId);
				dtoSubNodeDtl.setLastPublishVersion(dtoWsMst.getLastPublishedVersion());
				subNodeDtlLst.add(dtoSubNodeDtl); 
			}
			docMgmtImpl.insertIntoSubmittedWorkspaceNodeDetail(subNodeDtlLst);
			htmlContent = "Sequence Confirmed Successfully.";
		}
		else{
			htmlContent = "No documents found to Confirm the Sequence.";
		}
		
		// This is to display the changed node
		/*
		for (int i = 0; i < nodesToSubmit.size(); i++) 
		{
			DTOWorkSpaceNodeDetail dto1 = nodesToSubmit.get(i);
			System.out.println(dto1.getNodeId()+"----"+dto1.getTranNo()+"---");
		}
		*/
		return SUCCESS;
	}
	
	public String getTreeNodes()
	{
		
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userTypeCode = ActionContext.getContext().getSession().get("usertypecode").toString();
		
		ArrayList<Integer> nodeIdList = new ArrayList<Integer>();
		
		WorkspaceDynamicNodeCheckTreeBean treeBean = new WorkspaceDynamicNodeCheckTreeBean();
		treeBean.setUserType(userTypeCode);
		
		ArrayList<DTOSubmittedWorkspaceNodeDetail> subWsNodeList = docMgmtImpl.getSubmittedWorkspaceNodeList(WsId, submissionId);
		System.out.println(subWsNodeList.size()+"----------get zero node");
		if(subWsNodeList.size() > 0)
		{
			for (int itrSubList = 0; itrSubList < subWsNodeList.size(); itrSubList++) 
			{
				DTOSubmittedWorkspaceNodeDetail dtoSubWsNodeDtl = subWsNodeList.get(itrSubList); 
				int nodeIdVal = dtoSubWsNodeDtl.getNodeId();
				nodeIdList.add(nodeIdVal); // this list is to generate the node wise HTML tree.
			}
			treeBean.setSelectedNodeTree(true);
			treeBean.setChkBoxForAllNodes(false);
			
			htmlContent = treeBean.getSelectedNodeTreeHtml(WsId,userGroupCode,userCode,nodeIdList);
		}
		else
			htmlContent ="0";
		
		
		System.out.println(htmlContent);
			
		
		return SUCCESS;
	}
	
	/*private functions*/
	private DTOInternalLabelMst createWorkspaceLabel(String workspaceId,int userId){
		DTOInternalLabelMst maxLabel = docMgmtImpl.getMaxWorkspaceLabel(workspaceId);
		//System.out.println("max label id ::"+maxLabel.getLabelNo());
		String maxLabelId = maxLabel.getLabelId();
		//Increment labelId by 1, e.g. L0001 to L0002
		String newLabelId = maxLabelId.substring(maxLabelId.length()-4);
		int newLabelIdInt = Integer.parseInt(newLabelId);
		newLabelIdInt =newLabelIdInt + 1;
		newLabelId = "000"+newLabelIdInt;
		newLabelId = maxLabelId.substring(0, maxLabelId.length()-4)+newLabelId.substring(newLabelId.length()-4);
		DTOInternalLabelMst dto = new DTOInternalLabelMst();
		dto.setWorkspaceId(workspaceId);
		dto.setLabelId(newLabelId);//labelId
		dto.setRemark("");//labelRemark
		dto.setModifyBy(userId);
		docMgmtImpl.createInternalLabel(dto);
		maxLabel = docMgmtImpl.getMaxWorkspaceLabel(workspaceId);
		//System.out.println("max label id ::"+maxLabel.getLabelNo());
		return maxLabel;
	}
	
	private void nodesToConfirm(ArrayList<DTOWorkSpaceNodeDetail> prevNodeList,ArrayList<DTOWorkSpaceNodeDetail> currNodeList)
	{
		//This function check the tran number of the same node. if not change then remove from the confirm list.
		for (int itrPrev = 0; itrPrev < prevNodeList.size(); itrPrev++) 
		{
			DTOWorkSpaceNodeDetail dtoPrevNodeDtl = prevNodeList.get(itrPrev);
			for (int itrCurr = 0; itrCurr < currNodeList.size(); itrCurr++) 
			{
				DTOWorkSpaceNodeDetail dtoCurrNodeDtl = currNodeList.get(itrCurr);
				if(dtoCurrNodeDtl.getNodeId() == dtoPrevNodeDtl.getNodeId())
				{
					if(dtoCurrNodeDtl.getTranNo() == dtoPrevNodeDtl.getTranNo())
					{
						currNodeList.remove(itrCurr);
						itrCurr --;
					}
				}
			}
		}
	}
}