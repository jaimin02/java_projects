package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

	public class ShowDocAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public ArrayList<DTOWorkSpaceMst> workspaceMstList= new ArrayList<DTOWorkSpaceMst>();
	public Vector<DTOWorkSpaceNodeDetail> workspaceChildNodeDtls = new Vector<DTOWorkSpaceNodeDetail>();
	public Vector<DTOWorkSpaceUserRightsMst> workspaceUserRightsMstList = new Vector<DTOWorkSpaceUserRightsMst>();
	public String workSpaceId;
	public int nodeId;
	public String htmlContent;
	public String userStageId="";
	public String [] str;
	public DTOWorkSpaceNodeDetail dtoWsNodeDtl;
	public ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
	public ArrayList<String> projList = new ArrayList<String>();
	public ArrayList<String> categoryList = new ArrayList<String>();
	public ArrayList<String> yearList = new ArrayList<String>();
	public String fromId;
	public String toId;
	public String folderName;
	
	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		if (workSpaceId == null || workSpaceId.trim().equalsIgnoreCase("")) 
		{
			int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
			workspaceMstList = docMgmtImpl.getWorkspaceDtByProjPublishType(userId, userGroupCode, ProjectType.DMS_DOC_CENTRIC);
			return SUCCESS;
			
		}
		
		DTOWorkSpaceUserRightsMst dtoWsUserRightMst = new DTOWorkSpaceUserRightsMst();
		dtoWsUserRightMst.setWorkSpaceId(workSpaceId);
		dtoWsUserRightMst.setNodeId(0);
		dtoWsUserRightMst.setUserCode(userId);
		dtoWsUserRightMst.setParentNodeId(nodeId);
		dtoWsUserRightMst.setFolderName(folderName);
		dtoWsUserRightMst.setFrom(fromId);
		dtoWsUserRightMst.setTo(toId);
		Vector<DTOWorkSpaceUserRightsMst> wsUserRightsMstList = docMgmtImpl.getUserRightsReport(dtoWsUserRightMst, true);

		int []nodeIds = new int[wsUserRightsMstList.size()];
		
		for (int itrwsNdDtlList = 0; itrwsNdDtlList < wsUserRightsMstList.size(); itrwsNdDtlList++) 
			nodeIds[itrwsNdDtlList]= wsUserRightsMstList.get(itrwsNdDtlList).getNodeId();
		
		if(nodeIds.length > 0)
		{
			wsNodeHistory = docMgmtImpl.getAllNodesLastHistory(workSpaceId,nodeIds);
			for (int itrWsNodeHis = 0; itrWsNodeHis < wsNodeHistory.size(); itrWsNodeHis++) 
			{
				DTOWorkSpaceNodeHistory dtoWsNodeHis = wsNodeHistory.get(itrWsNodeHis);
				ArrayList<DTOWorkSpaceUserRightsMst> wsURMstList = new ArrayList<DTOWorkSpaceUserRightsMst>();
				for (int iteWsUserRights = 0; iteWsUserRights < wsUserRightsMstList.size(); iteWsUserRights++) 
				{
					DTOWorkSpaceUserRightsMst dtoWsUserRightsMst = wsUserRightsMstList.get(iteWsUserRights);
					if (dtoWsNodeHis.getNodeId() == dtoWsUserRightsMst.getNodeId())
						wsURMstList.add(dtoWsUserRightsMst);
				}
				dtoWsNodeHis.setWsUsrRightsList(wsURMstList);
			}
		}
		return SUCCESS;
	}

	public String showDocDtls()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		Vector<DTOWorkSpaceUserRightsMst> wsUserRightsMstList = new Vector<DTOWorkSpaceUserRightsMst>();
		DTOWorkSpaceUserRightsMst dtoUserRightsMst = new DTOWorkSpaceUserRightsMst();
		dtoUserRightsMst.setWorkSpaceId(workSpaceId);
		dtoUserRightsMst.setNodeId(0);
		dtoUserRightsMst.setParentNodeId(1);
		dtoUserRightsMst.setUserCode(userId);
		wsUserRightsMstList  = docMgmtImpl.getUserRightsReport(dtoUserRightsMst, false);
		
/*		Vector<DTOWorkSpaceNodeDetail> wsChildNodeDtls = docMgmtImpl.getChildNodeByParent(1, workSpaceId);
		for (int i = 0; i < wsUserRightsMstList.size(); i++) 
		{
			DTOWorkSpaceUserRightsMst dtoWsUserMst = wsUserRightsMstList.get(i);
			for (int j = 0; j < wsChildNodeDtls.size(); j++) 
			{
				DTOWorkSpaceNodeDetail dtoWsNodeDtl = wsChildNodeDtls.get(j);
				if(dtoWsNodeDtl.getNodeId() == dtoWsUserMst.getNodeId())
				{
					if(workspaceChildNodeDtls.size() > 0)
					{
						for (int k = 0; k < workspaceChildNodeDtls.size(); k++) 
						{
							if(workspaceChildNodeDtls.get(k).getNodeId() != dtoWsUserMst.getNodeId())
							{
								workspaceChildNodeDtls.add(dtoWsNodeDtl);
							}
						}
					}
					else
						workspaceChildNodeDtls.add(dtoWsNodeDtl);
				}
				
			}
		}
*/		
		DTOWorkSpaceUserRightsMst dtoWsNodeDtl = new DTOWorkSpaceUserRightsMst();  
		htmlContent = "<option id=\"All##All\" value=\"All##All\" style=\"display: block;\"></option>";	
		for (int itrOption = 0; itrOption < wsUserRightsMstList.size(); itrOption++) 
		{
			dtoWsNodeDtl= wsUserRightsMstList.get(itrOption);
			htmlContent += "<option id=\""+dtoWsNodeDtl.getNodeId()+"##"+dtoWsNodeDtl.getWorkSpaceId()+"\" value=\""+dtoWsNodeDtl.getNodeId()+"##"+dtoWsNodeDtl.getWorkSpaceId()+"\" style=\"display: block;\">";
			htmlContent += dtoWsNodeDtl.getNodeName();
			htmlContent +="</option>";
		}
		/*workspaceChildNodeDtls = new Vector<DTOWorkSpaceNodeDetail>();
		workspaceChildNodeDtls = docMgmtImpl.getChildNodeByParent(1, workSpaceId);
		htmlContent = "<option id=\"All##All\" value=\"All##All\" style=\"display: block;\">All Categories</option>";
		for (int itrOption = 0; itrOption < workspaceChildNodeDtls.size(); itrOption++) 
		{
			dtoWsNodeDtl = workspaceChildNodeDtls.get(itrOption);
			htmlContent += "<option id=\""+dtoWsNodeDtl.getNodeId()+"##"+dtoWsNodeDtl.getWorkspaceId()+"\" value=\""+dtoWsNodeDtl.getNodeId()+"##"+dtoWsNodeDtl.getWorkspaceId()+"\" style=\"display: block;\">";
			htmlContent += dtoWsNodeDtl.getNodeName();
			htmlContent +="</option>";
		}
		*/
		return "CategoryDtls";
	}
	
	public String getDocIdDtls()
	{
		ArrayList<ArrayList<String>> docIdDtls = docMgmtImpl.getReleasedDocIdDetails(workSpaceId, nodeId);
		
		projList.addAll(docIdDtls.get(0));
		projList.remove(0);
		
		categoryList.addAll(docIdDtls.get(1));
		categoryList.remove(0);
		
		yearList.addAll(docIdDtls.get(2));
		yearList.remove(0);
		
		return SUCCESS;
	}
}