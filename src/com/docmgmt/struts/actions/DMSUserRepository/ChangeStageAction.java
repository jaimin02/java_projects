package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.ArrayList;

import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ChangeStageAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public int stageId;
	public String workSpaceId;
	public String nodeId;	
	public ArrayList<DTOWorkSpaceUserRightsMst> wsUserRightsList;
	
	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());		
		int MaxtranNo = docMgmtImpl.getMaxTranNo(workSpaceId, Integer.parseInt(nodeId));
		int tranNo=docMgmtImpl.getNewTranNo(workSpaceId);
		DTOWorkSpaceNodeHistory dtownd = docMgmtImpl.getWorkspaceNodeHistorybyTranNo(workSpaceId, Integer.parseInt(nodeId),MaxtranNo);
		dtownd.setStageId(stageId);
		dtownd.setModifyBy(userId);
		dtownd.setTranNo(tranNo);
		docMgmtImpl.insertNodeHistory(dtownd);
		
		//update tran no in attribute history
		docMgmtImpl.UpdateTranNoForStageInAttrHistory(workSpaceId, Integer.parseInt(nodeId), tranNo);
		
		return SUCCESS;
	}
}