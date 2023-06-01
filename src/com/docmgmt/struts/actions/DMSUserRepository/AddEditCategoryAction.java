package com.docmgmt.struts.actions.DMSUserRepository;


import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOAttributeValueMatrix;
import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOUserGroupMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOUserTypeMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.dto.DTOWorkspaceNodeReferenceDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.beans.WorkspaceDynamicNodeCheckTreeBean;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddEditCategoryAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	
	public Vector<DTOUserGroupMst> assignUsers;
	public Vector<DTOAttributeValueMatrix> getAttributeDetail;
	public Vector<DTOUserTypeMst> userTypes;	
	public int nodeId;
	public String workSpaceId;
	public Vector<DTOWorkSpaceNodeDetail> workspaceNodeDtls;
	public Vector<DTOWorkSpaceUserRightsMst> workspaceUserRightsMstList;
	public Vector<DTOWorkSpaceNodeAttrDetail> workspaceNodeAttrList;
	public DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
	public String usrTyp;
	public Vector<DTOUserGroupMst> usrGrps;
	public Vector<DTOStageMst> stages;
	public String frmDate;
	public String toDate;
	public String no;
	public int groupCount;
	public Vector<Integer> grpWiseUserCount;
	public String mode="";
	public String userCodeList="";
	public DTOWorkSpaceNodeHistory dtoWsNodeHis = new DTOWorkSpaceNodeHistory();
	public ArrayList<DTOWorkSpaceNodeHistory> nodeVersionHistory = new ArrayList<DTOWorkSpaceNodeHistory>();
	public String baseWorkFolder;
	public ArrayList<DTOWorkSpaceMst> projectList;
	public String htmlContent;
	public int catStatus = 0; // 0 = no doc, no ref.  ; 1= ref   ;  2 =  doc
	public ArrayList<DTOWorkspaceNodeReferenceDetail> wsNodeRefDtlList = new ArrayList<DTOWorkspaceNodeReferenceDetail>();
	
	@Override
	public String execute()
	{
		//System.out.println(mode+"------------------mode");
		if (mode.trim().equalsIgnoreCase("add")) {
			addCategory();
			return "ADD";
		}
		else {
			editCategory();
			return "EDIT";
		}
		/*
		workspaceUserRightsMstList = new Vector<DTOWorkSpaceUserRightsMst>();
		workspaceNodeDtls = new Vector<DTOWorkSpaceNodeDetail>();
		workspaceNodeAttrList = new Vector<DTOWorkSpaceNodeAttrDetail>();
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		DTOUserMst usermst = new DTOUserMst();
		usermst.setUserCode(userCode);
		usermst.setUserGroupCode(userGroupCode);
		getAttributeDetail = docMgmtImpl.getAttributeDetailByType("0003");
		int prevAttrId = 0;
		for (int iAttr = 0; iAttr < getAttributeDetail.size(); iAttr++) {
			DTOAttributeValueMatrix dto = getAttributeDetail.get(iAttr);
			if(prevAttrId == dto.getAttrId())
			{
				getAttributeDetail.remove(iAttr--);
			}
			else
			{
				prevAttrId = dto.getAttrId();
			}
		}
		userTypes = docMgmtImpl.getAllUserType();
		if(nodeId != 0)
		{
			int[] nodeIds = new int[1]; 
			nodeIds[0]=nodeId;
			ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistoryList = docMgmtImpl.getAllNodesLastHistory(workSpaceId,nodeIds);
			for (int itrWsNodeHis = 0; itrWsNodeHis < wsNodeHistoryList.size(); itrWsNodeHis++) 
			{
				dtoWsNodeHis = wsNodeHistoryList.get(itrWsNodeHis);
			}
			workspaceNodeAttrList = docMgmtImpl.getNodeAttrDetail(workSpaceId, nodeId);
			workspaceNodeDtls = docMgmtImpl.getNodeDetail(workSpaceId, nodeId);
			for (int i = 0; i < workspaceNodeDtls.size(); i++) {
				dto = workspaceNodeDtls.get(i);
			}
		}		
		return SUCCESS;
		*/
	}
	
	private void addCategory()
	{
		DTOWorkSpaceMst dtoWorkspaceMst = docMgmtImpl.getWorkSpaceDetailByWorkspaceId(workSpaceId);
	
		projectList = docMgmtImpl.getWorkSpaceDetailByTemplate(dtoWorkspaceMst.getTemplateId());
		for (int itrProjectList = 0; itrProjectList < projectList.size() ; itrProjectList++) 
		{
			if (projectList.get(itrProjectList).getProjectType() == ProjectType.DMS_DOC_CENTRIC ) 
			{
				projectList.remove(itrProjectList);
				itrProjectList--;
			} 
		}
		
		workspaceUserRightsMstList = new Vector<DTOWorkSpaceUserRightsMst>();
		workspaceNodeDtls = new Vector<DTOWorkSpaceNodeDetail>();
		workspaceNodeAttrList = new Vector<DTOWorkSpaceNodeAttrDetail>();
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		DTOUserMst usermst = new DTOUserMst();
		usermst.setUserCode(userCode);
		usermst.setUserGroupCode(userGroupCode);
		getAttributeDetail = docMgmtImpl.getAttributeDetailByType("0003");
		int prevAttrId = 0;
		for (int iAttr = 0; iAttr < getAttributeDetail.size(); iAttr++) {
			DTOAttributeValueMatrix dto = getAttributeDetail.get(iAttr);
			if(prevAttrId == dto.getAttrId())
			{
				getAttributeDetail.remove(iAttr--);
			}
			else
			{
				prevAttrId = dto.getAttrId();
			}
		}
		userTypes = docMgmtImpl.getAllUserType();
	}
	
	private void editCategory()
	{
		DTOWorkSpaceMst dtoWorkspaceMst = docMgmtImpl.getWorkSpaceDetailByWorkspaceId(workSpaceId);
		
		projectList = docMgmtImpl.getWorkSpaceDetailByTemplate(dtoWorkspaceMst.getTemplateId());
		for (int itrProjectList = 0; itrProjectList < projectList.size() ; itrProjectList++) 
		{
			if (projectList.get(itrProjectList).getProjectType() == ProjectType.DMS_DOC_CENTRIC ) 
			{
				projectList.remove(itrProjectList);
				itrProjectList--;
			} 
		}
		workspaceUserRightsMstList = new Vector<DTOWorkSpaceUserRightsMst>();
		workspaceNodeDtls = new Vector<DTOWorkSpaceNodeDetail>();
		workspaceNodeAttrList = new Vector<DTOWorkSpaceNodeAttrDetail>();
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		DTOUserMst usermst = new DTOUserMst();
		usermst.setUserCode(userCode);
		usermst.setUserGroupCode(userGroupCode);
		getAttributeDetail = docMgmtImpl.getAttributeDetailByType("0003");
		int prevAttrId = 0;
		for (int iAttr = 0; iAttr < getAttributeDetail.size(); iAttr++) {
			DTOAttributeValueMatrix dto = getAttributeDetail.get(iAttr);
			if(prevAttrId == dto.getAttrId())
			{
				getAttributeDetail.remove(iAttr--);
			}
			else
			{
				prevAttrId = dto.getAttrId();
			}
		}
		userTypes = docMgmtImpl.getAllUserType();
		if(nodeId != 0)
		{
			
			ArrayList<DTOWorkspaceNodeReferenceDetail> wsNodeRefList =  docMgmtImpl.getWorkspaceNodeRefereceDtl(workSpaceId, nodeId,true);
			String wsID=workSpaceId;
			int ndId = nodeId;
			if (wsNodeRefList.size() > 0) 
			{
				int index = wsNodeRefList.size()-1;
				wsID = wsNodeRefList.get(index).getRefWorkspaceId();
				ndId = wsNodeRefList.get(index).getRefNodeId();
				catStatus = 1;
			}
			
			int[] nodeIds = new int[1]; 
			nodeIds[0]=ndId;
			ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistoryList = docMgmtImpl.getAllNodesLastHistory(wsID,nodeIds);
			
			for (int itrWsNodeHis = 0; itrWsNodeHis < wsNodeHistoryList.size(); itrWsNodeHis++) 
			{
				dtoWsNodeHis = wsNodeHistoryList.get(itrWsNodeHis);
			}
			
			 // 0 = no doc, no ref.  ; 1= ref   ;  2 =  doc
			
			
			if (wsNodeHistoryList.size() > 0 && dtoWsNodeHis != null && dtoWsNodeHis.getFileName() != null 
					&& !dtoWsNodeHis.getFileName().trim().equalsIgnoreCase("No File")) 
			{
				if (catStatus != 1) 
					catStatus = 2;
			}
			
			if(wsNodeHistoryList.size() == 0  || dtoWsNodeHis == null ||
					(dtoWsNodeHis != null && (dtoWsNodeHis.getFileName() == null ||
												dtoWsNodeHis.getFileName().trim().equalsIgnoreCase("No File"))
					)		
				)
			{
				
				catStatus=0;
			}
				
			
			
			
			
			
			workspaceNodeAttrList = docMgmtImpl.getNodeAttrDetail(workSpaceId, nodeId);
			workspaceNodeDtls = docMgmtImpl.getNodeDetail(workSpaceId, nodeId);
			for (int i = 0; i < workspaceNodeDtls.size(); i++) {
				dto = workspaceNodeDtls.get(i);
			}
		}		
	}
	
	public String getUsers()
	{
		Vector<DTOUserMst> usrList;
		grpWiseUserCount=new Vector<Integer>();
		usrGrps=docMgmtImpl.getAllUserGroupByUserType(usrTyp);
		usrList=docMgmtImpl.getAllUserDetail();
		int ucode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		for (int i=0;i<usrGrps.size();i++)
		{
			DTOUserGroupMst grp=usrGrps.get(i);
			grp.setUsers(new ArrayList<DTOUserMst>());
			int cnt=0;
			for(int j=0;j<usrList.size();j++)
			{
				DTOUserMst usr=usrList.get(j);
				if (grp.getUserGroupCode()==usr.getUserGroupCode() && usr.getStatusIndi()!='D' && usr.getUserCode()!=ucode)
				{
					grp.getUsers().add(usr);
					cnt++;
				}
			}
			grpWiseUserCount.add(cnt);
		}
		for (int i = 0;i < grpWiseUserCount.size();i++)
		{
			//If user group has 0 users, remove that user group from list.
			if(grpWiseUserCount.get(i) == 0)
			{
				usrGrps.removeElementAt(i);
				grpWiseUserCount.removeElementAt(i);
				i--;
			}
		}
		Vector<DTOStageMst> stageMstList=docMgmtImpl.getStageDetail();
		int minStageId=minStageId(stageMstList);				
		workspaceNodeDtls = docMgmtImpl.getNodeDetail(workSpaceId, nodeId);
		DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();		
		dtoWorkSpaceUserRightsMst.setWorkSpaceId(workSpaceId);
		dtoWorkSpaceUserRightsMst.setNodeId(nodeId);				
		workspaceUserRightsMstList = docMgmtImpl.getUserRightsReport(dtoWorkSpaceUserRightsMst, true);		
			for (int indexUser = 0; indexUser < workspaceUserRightsMstList.size() ; indexUser++) 
			{
				DTOWorkSpaceUserRightsMst dto=workspaceUserRightsMstList.get(indexUser);
				if(dto.getStageId()==minStageId)
					userCodeList=userCodeList+ dto.getUserCode()+",";
			}
		if(userCodeList != null && userCodeList.length() > 0)
			userCodeList=userCodeList.substring(0,userCodeList.length()-1);
		if(nodeId == 0) 
			userCodeList="";
		return SUCCESS;
	}
	
	private int minStageId(Vector<DTOStageMst> stageMstList)
	{
		int stagId=stageMstList.get(0).getStageId();
		for (int indexStgMst = 0; indexStgMst < stageMstList.size(); indexStgMst++) {
				DTOStageMst dtoStage=stageMstList.get(indexStgMst);
				if(dtoStage.getStageId()<stagId && dtoStage.getStatusIndi()!='D')
					stagId=dtoStage.getStageId();
		}
		return stagId;
	}
	
	public String getHistory()
	{
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		baseWorkFolder = propertyInfo.getValue("BaseWorkFolder");
		nodeVersionHistory = docMgmtImpl.getNodeDetailedHistory(workSpaceId,nodeId);
		wsNodeRefDtlList =  docMgmtImpl.getWorkspaceNodeRefereceDtl(workSpaceId, nodeId, false);
		return SUCCESS;
	}

	public String getWorkspaceTree(){
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userTypeCode = ActionContext.getContext().getSession().get("usertypecode").toString();
		WorkspaceDynamicNodeCheckTreeBean treeBean = new WorkspaceDynamicNodeCheckTreeBean();
		treeBean.setUserType(userTypeCode);
		Vector<DTOWorkSpaceNodeHistory> uploadedFileNodes = docMgmtImpl.getUploadedFileNodes(workSpaceId);
		treeBean.setChkBoxForAllNodes(false);
		treeBean.isSelectedNodeTree();
		htmlContent = treeBean.getWorkspaceTreeHtml(workSpaceId,userGroupCode,userCode,uploadedFileNodes);
		return SUCCESS;
	}
}