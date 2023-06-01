package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOUserDocStageComments;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EditCommentsAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	
	public String stageId;
	public String workSpaceId;
	public String nodeId;
	public String userComments;
	public DTOUserDocStageComments dtoUserDocStageComments;
	public String nextStage;
	
	@Override
	public String execute()
	{
		ArrayList<DTOUserDocStageComments> userDocStageCommentsList = new ArrayList<DTOUserDocStageComments>();
		userDocStageCommentsList = docMgmtImpl.getStageWiseDocComments(workSpaceId, Integer.parseInt(nodeId),stageId);
		if (userDocStageCommentsList.size() > 0) 
		{
			dtoUserDocStageComments = userDocStageCommentsList.get(userDocStageCommentsList.size()-1);
		}
		return SUCCESS;
	}
	public String saveUserComments()
	{ 	
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int tranNo=0;
		int newStageId = 0;
		if (nextStage.equalsIgnoreCase("True")) 
			tranNo=docMgmtImpl.getNewTranNo(workSpaceId);
		else
			tranNo = docMgmtImpl.getMaxTranNo(workSpaceId,Integer.parseInt(nodeId));
		if (nextStage.equalsIgnoreCase("True")) 
		{
			int []nodeIds = {Integer.parseInt(nodeId)};
			ArrayList<DTOWorkSpaceNodeHistory> WsNodeHistoryList = docMgmtImpl.getAllNodesLastHistory(workSpaceId, nodeIds);
			DTOWorkSpaceNodeHistory dtoWsNdhis = new DTOWorkSpaceNodeHistory();
			if (WsNodeHistoryList.size()>0) 
				dtoWsNdhis = WsNodeHistoryList.get(WsNodeHistoryList.size()-1);
			if (Integer.parseInt(stageId) == 10) 
				newStageId = 20;
			if (Integer.parseInt(stageId) == 20)
				newStageId = 100;
			
			DTOWorkSpaceNodeHistory dtoWsNodeHistory = new DTOWorkSpaceNodeHistory();
			dtoWsNodeHistory.setWorkSpaceId(workSpaceId);
			dtoWsNodeHistory.setNodeId(Integer.parseInt(nodeId));
			dtoWsNodeHistory.setFolderName(dtoWsNdhis.getFolderName());
			dtoWsNodeHistory.setTranNo(tranNo);
			dtoWsNodeHistory.setStageId(newStageId);
			dtoWsNodeHistory.setFileName(dtoWsNdhis.getFileName());
			dtoWsNodeHistory.setFileType(dtoWsNdhis.getFileType());
			dtoWsNodeHistory.setRequiredFlag('Y');
			dtoWsNodeHistory.setRemark("");
			dtoWsNodeHistory.setModifyBy(userId);
			dtoWsNodeHistory.setStatusIndi('N');
			dtoWsNodeHistory.setDefaultFileFormat("");
			docMgmtImpl.insertNodeHistory(dtoWsNodeHistory);

			DTOWorkSpaceNodeVersionHistory dtoWsNodeVersionHistory = new DTOWorkSpaceNodeVersionHistory();
			dtoWsNodeVersionHistory.setWorkspaceId(workSpaceId);
			dtoWsNodeVersionHistory.setNodeId(Integer.parseInt(nodeId));
			dtoWsNodeVersionHistory.setTranNo(tranNo);
			dtoWsNodeVersionHistory.setPublished('N');
			dtoWsNodeVersionHistory.setDownloaded('N');
			dtoWsNodeVersionHistory.setModifyBy(userId);
			dtoWsNodeVersionHistory.setExecutedBy(userId);
			dtoWsNodeVersionHistory.setUserDefineVersionId(dtoWsNdhis.getUserDefineVersionId());
			docMgmtImpl.insertWorkspaceNodeVersionHistory(dtoWsNodeVersionHistory, false);
					
			Vector<DTOWorkSpaceNodeAttrDetail> wsNodeAttrDtl = docMgmtImpl.getNodeAttrDetail(workSpaceId, Integer.parseInt(nodeId));
			if(wsNodeAttrDtl.size() > 0)
			{
				Vector<DTOWorkSpaceNodeAttrHistory> wsNodeAttrHistory = new Vector<DTOWorkSpaceNodeAttrHistory>();
				for (int i = 0; i < wsNodeAttrDtl.size(); i++) 
				{
					DTOWorkSpaceNodeAttrHistory dtoWsNodeAttrHistory = new DTOWorkSpaceNodeAttrHistory();
					dtoWsNodeAttrHistory.setWorkSpaceId(workSpaceId);
					dtoWsNodeAttrHistory.setAttrId(wsNodeAttrDtl.get(i).getAttrId());
					dtoWsNodeAttrHistory.setAttrValue(wsNodeAttrDtl.get(i).getAttrValue()== null?"":wsNodeAttrDtl.get(i).getAttrValue());
					dtoWsNodeAttrHistory.setNodeId(Integer.parseInt(nodeId));
					dtoWsNodeAttrHistory.setTranNo(tranNo);
					dtoWsNodeAttrHistory.setRemark("");
					dtoWsNodeAttrHistory.setModifyBy(userId);
					dtoWsNodeAttrHistory.setStatusIndi('N');
					wsNodeAttrHistory.add(dtoWsNodeAttrHistory);
				}
				docMgmtImpl.insertNodeAttrHistory(wsNodeAttrHistory);
			}
		}
		DTOUserDocStageComments dtoDocStageComments = new DTOUserDocStageComments();
		dtoDocStageComments.setWorkspaceId(workSpaceId);
		dtoDocStageComments.setNodeId(Integer.parseInt(nodeId));
		dtoDocStageComments.setStageId(Integer.parseInt(stageId));
		dtoDocStageComments.setTranNo(tranNo);
		dtoDocStageComments.setUserCode(userId);
		dtoDocStageComments.setUserComments(userComments);
		dtoDocStageComments.setRemarks("");
		dtoDocStageComments.setUserRefDocPath("");
		dtoDocStageComments.setUserRefDocName("");
		dtoDocStageComments.setModifyBy(userId);
		dtoDocStageComments.setStatusIndi('N');
		docMgmtImpl.insertUserDocStageComments(dtoDocStageComments, 1);
		return SUCCESS;
	}
}