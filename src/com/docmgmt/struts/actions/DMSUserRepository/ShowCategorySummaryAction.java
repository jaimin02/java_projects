package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowCategorySummaryAction extends ActionSupport {

	private static final long serialVersionUID = 5947674360356156329L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String workSpaceId;
	public Vector<DTOWorkSpaceNodeDetail> workspaceChildNodeDtls = new Vector<DTOWorkSpaceNodeDetail>();
	@Override
	public String execute()
	{
		DTOWorkSpaceNodeHistory dtoWsNodeHis;
		ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistoryList;
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
				DTOWorkSpaceNodeDetail dtoWsNodeDtl = wsChildNodeDtls.get(j);
				if(dtoWsNodeDtl.getNodeId() == dtoWsUserMst.getNodeId())
				{
					workspaceChildNodeDtls.add(dtoWsNodeDtl);
				}
				
			}
		}
		for (int i = 0; i < workspaceChildNodeDtls.size(); i++) 
		{
			DTOWorkSpaceNodeDetail dto = workspaceChildNodeDtls.get(i);
			int[] nodeIds = new int[1];
			nodeIds[0]=dto.getNodeId();
			dtoWsNodeHis = new DTOWorkSpaceNodeHistory();
			wsNodeHistoryList = docMgmtImpl.getRefWorkspaceNodes(workSpaceId,dto.getNodeId());
			for (int itrWsNodeHis = 0; itrWsNodeHis < wsNodeHistoryList.size(); itrWsNodeHis++) 
			{
				dtoWsNodeHis = wsNodeHistoryList.get(itrWsNodeHis);
			}
			dto.setLatestNodeHistory(dtoWsNodeHis);
			dto.setUserName(docMgmtImpl.getUserByCode(dto.getModifyBy()).getUserName());
		}
	return SUCCESS;
	}
}