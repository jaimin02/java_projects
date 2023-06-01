package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class EditDocAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	
	public int nodeId;
	public String workSpaceId;
	public int userStageId;
	public String folderName;
	public String userMode="false";
	public String datamode;
	public String nodeName;
	//public Vector<DTOWorkSpaceNodeDetail> workspaceNodeDtls;
	public DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
	public DTOWorkSpaceNodeHistory dtoWsNodeHis = new DTOWorkSpaceNodeHistory();
	public Vector<DTOStageMst> getStages = new Vector<DTOStageMst>();
	//public ArrayList<DTOUserDocStageComments> UserDocStageCommentsList;
	//public Vector<DTOWorkSpaceUserRightsMst> wsUserDetailList;
	
	
	@Override
	public String execute()
	{
		if (userMode!= null && userMode.equalsIgnoreCase("true")) 
		{
			return "User";
		}
		Vector<DTOWorkSpaceNodeDetail> workspaceNodeDtls = docMgmtImpl.getNodeDetail(workSpaceId, nodeId);
		if (workspaceNodeDtls.size() > 0) 
		{
			dto = workspaceNodeDtls.get(workspaceNodeDtls.size()-1);
		}
		int nodeIds[] = new int[1];
		nodeIds[0] = nodeId;
		ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistory = docMgmtImpl.getAllNodesLastHistory(workSpaceId, nodeIds);
		
		if (wsNodeHistory.size() > 0) 
		{
			dtoWsNodeHis = wsNodeHistory.get(wsNodeHistory.size()-1);
		}
		/*
		for (int i = 0; i < workspaceNodeDtls.size(); i++) {
			dto = workspaceNodeDtls.get(i);
		}
		*/
		//getStages = docMgmtImpl.getStageDetail();
		//UserDocStageCommentsList = docMgmtImpl.getStageWiseDocComments(workSpaceId, nodeId, userStageId);
		return SUCCESS;
	}
}