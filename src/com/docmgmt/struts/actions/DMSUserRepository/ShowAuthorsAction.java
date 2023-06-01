package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import com.docmgmt.dto.DTOUserDocStageComments;
import com.docmgmt.dto.DTOUserTypeMst;
import com.docmgmt.dto.DTOWorkspaceUserAuditTrailMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowAuthorsAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
		
	public String workSpaceId;
	public String nodeId;
	public String mode;
	public String datamode;
	public Vector<DTOUserTypeMst> userTypes;
	public ArrayList<DTOUserDocStageComments> userDocStageCommentsList= new ArrayList<DTOUserDocStageComments>();
	public ArrayList<DTOWorkspaceUserAuditTrailMst> wsUsrAdtTrlLst;
	
	@Override
	public String execute()
	{
		int stage=0;
		if (datamode != null && !datamode.trim().equalsIgnoreCase("") && datamode.trim().equalsIgnoreCase("user")) 
		{
			if (mode != null && !mode.trim().equals(""))
			{
				
				if(mode.equals("Authors"))
					stage=10;
				else if(mode.equals("Reviewers"))
					stage=20;
				else if(mode.equals("Approvers"))
					stage=100;
			}
			int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			userTypes = docMgmtImpl.getAllUserType();
			ArrayList<DTOWorkspaceUserAuditTrailMst> wsUserAuditTrailList = new ArrayList<DTOWorkspaceUserAuditTrailMst>();
			DTOWorkspaceUserAuditTrailMst dtoWsUsrAuditTrailMst = new DTOWorkspaceUserAuditTrailMst();
			dtoWsUsrAuditTrailMst.setWorkspaceId(workSpaceId);
			dtoWsUsrAuditTrailMst.setNodeId(Integer.parseInt(nodeId));
			dtoWsUsrAuditTrailMst.setModifyBy(userId);
			dtoWsUsrAuditTrailMst.setStageId(stage);
			wsUserAuditTrailList.add(dtoWsUsrAuditTrailMst);
			wsUsrAdtTrlLst = docMgmtImpl.getWorkspaceUserAuditTrail(wsUserAuditTrailList , true, true, true, true, false);
			Collections.reverse(wsUsrAdtTrlLst);
			return  SUCCESS;
		}
		else
		{
			if (mode != null && !mode.trim().equals(""))
			{
				
				if(mode.equals("Authors"))
					stage=0;
				else if(mode.equals("Reviewers"))
					stage=10;
				else if(mode.equals("Approvers"))
					stage=20;
			}
			userDocStageCommentsList = docMgmtImpl.getStageWiseDocComments(workSpaceId, Integer.parseInt(nodeId), String.valueOf(stage));
			return SUCCESS;
		}
	}
}