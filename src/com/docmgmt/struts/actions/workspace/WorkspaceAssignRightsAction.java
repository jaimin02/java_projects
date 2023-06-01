package com.docmgmt.struts.actions.workspace;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class WorkspaceAssignRightsAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String htmlContent;
	public String userTypeCode;
	public boolean isVoidFlag=true;
	public Vector<DTOWorkSpaceNodeHistory> checkVoidFile=null;
	public Vector<DTOWorkSpaceNodeAttribute> getAttrDtl=null;
	public String projectCode="";
	public String ProjectTimeLine;
	public boolean showHours=false;
	public String TimelineCalculationBase;
	
	@Override
	public String execute()
	{
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		int userGroupCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		ProjectTimeLine = knetProperties.getValue("ProjectTimeLine");
				
		getAttrDtl = docMgmtImpl.getTimelineAttrDtl(workspaceID);
		
		if(getAttrDtl.size()>0){
			showHours=true;
		}
	
		//DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetail(workspaceID);
		DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetailWSList(workspaceID);
		projectCode = wsDetail.getProjectCode();
		nodeDetail = docMgmtImpl.getNodeDetail(workspaceID, nodeId);
		DTOWorkSpaceNodeDetail dto =(DTOWorkSpaceNodeDetail)nodeDetail.elementAt(0);		
		nodeName = dto.getNodeDisplayName();
		System.out.println("nodeName------"+nodeName);
		String LTRSingleDocFooterFormat=knetProperties.getValue("LTRSingleDocFooterFormat");
		if(LTRSingleDocFooterFormat.equalsIgnoreCase("Yes")){
		if(nodeName.contains("{")){
			nodeName=nodeName.substring(nodeName.indexOf(0)+1, nodeName.indexOf("{"));
		}
			else{
				nodeName=nodeName;
			}
		}
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserCode(userCode);
		userMst.setUserGroupCode(userGroupCode);
	
		//assignWorkspaceRightsDetails = docMgmtImpl.getWorkspaceUserDetail(workspaceID, userMst);
		//assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetail(workspaceID, userMst,"modulewise");
		assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForCSV(workspaceID, nodeId,"modulewise");
		//getStageDetail = docMgmtImpl.getStageDetail();
		getStageDetail = docMgmtImpl.getStageDetailCSV();
		
		int getStage = docMgmtImpl.checkCreateRights(workspaceID,nodeId);
		
		if(getStage>0){
			for(int i = 0;i<getStageDetail.size();i++){
				if(getStageDetail.get(i).getStageId() == 10){
					getStageDetail.remove(i);
					break;
				}
			 }
		}
		
		String wsId = workspaceID;
		getLatestStage = docMgmtImpl.getCurrentStageDesc(wsId,nodeId);
		
		if(getLatestStage.size() > 0 && getLatestStage.get(0).getStageId() == 100){
			for(int i = 0;i<getStageDetail.size();i++){
				if(getStageDetail.get(i).getStageId() == 10 || getStageDetail.get(i).getStageId() == 20){
					getStageDetail.remove(i);
				}
			}
		}
		
		checkVoidFile = docMgmtImpl.getVoidFileHistory(workspaceID,nodeId);
		if(checkVoidFile.size()>0){
			isVoidFlag=false;
		}
		
		ArrayList<Integer> assignedStageIds = docMgmtImpl.getStageIdsRightsWise(workspaceID,nodeId);
		
		if(!assignedStageIds.contains(30)){
			for(int i = 0;i<getStageDetail.size();i++){
				if(getStageDetail.get(i).getStageId() == 30){
					getStageDetail.remove(i);
					break;
				}
			}
			
		}
		if(projectCode.equalsIgnoreCase("0003")){
		if(assignedStageIds.contains(20)){
			for(int i = 0;i<getStageDetail.size();i++){
				if(getStageDetail.get(i).getStageId() == 20){
					getStageDetail.remove(i);
					break;
				}
			 }
			}
		}
		//getUserRightsDetail = docMgmtImpl.getUserRightsDetail(workspaceID, nodeId);
		getUserRightsDetail = docMgmtImpl.getUserRightsDetailForSingleDoc(workspaceID, nodeId);
		/*if(!userTypeName.equalsIgnoreCase("WA")){
			int userId;
		for(int j=0;j<getUserRightsDetail.size();j++){
			userId=getUserRightsDetail.get(j).getUserCode();
			if(userCode!=userId){
				getUserRightsDetail.remove(j);
				j--;
			}
		}
			}*/
		ArrayList<DTOWorkSpaceNodeHistory> WorkspaceUserHistory;
		Vector<DTOWorkSpaceUserRightsMst> WorkspaceUserDetailList;
		
		WorkspaceUserDetailList=docMgmtImpl.getUserRightsDetail(workspaceID, nodeId);
		
		for(int i=0;i<getUserRightsDetail.size();i++){
			
		WorkspaceUserHistory=docMgmtImpl.showNodeHistory(workspaceID,getUserRightsDetail.get(i).getUserCode(),nodeId,getUserRightsDetail.get(i).getStageId());
		System.out.println("WorkspaceUserHistory....."+WorkspaceUserHistory.size());
		if(WorkspaceUserHistory.size()>0){ 
			System.out.println("WorkspaceUserHistory....."+WorkspaceUserHistory.size());
			System.out.println("File Name....."+WorkspaceUserHistory.get(0).getFileName());
			if(WorkspaceUserHistory.size()>0 && !WorkspaceUserHistory.get(0).getFileName().equalsIgnoreCase("No File")){
			getUserRightsDetail.get(i).setUserFlag('Y');
			//WorkspaceUserDetailList.set(i, WorkspaceUserDetailList.get(i));
			}
		}
			else{
				getUserRightsDetail.get(i).setUserFlag('N');
				//WorkspaceUserDetailList.set(i, WorkspaceUserDetailList.get(i));
				//countForUser="false";
			}
		}
		
		userTypeCode=docMgmtImpl.getUserType(userCode);
		TimelineCalculationBase=knetProperties.getValue("TimelineCalculation");
		return SUCCESS;
	}
	public String UsertoAssignRights()
	{
		htmlContent="";
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		int userGroupCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTOUserMst userdata;
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserCode(userCode);
		userMst.setUserGroupCode(userGroupCode);
		if(RightsType!=null && RightsType.equals("modulewise"))
		{
		assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetail(workspaceID, userMst,"modulewise");
		}
		else
		{
			assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetail(workspaceID, userMst,"projectwise");
		}
		
		
		htmlContent += "<select  name=\"multiCheckUser\"  size=\"5%\"  id=\"WorkspaceRights_multiCheckUser\""
				+ "style=\"width:100%\" multiple=\"multiple\">";
		for(int udata=0; udata<assignWorkspaceRightsDetails.size(); udata++)
		{
			userdata = (DTOUserMst) assignWorkspaceRightsDetails.get(udata);
			htmlContent += "<option value=\""+userdata.getUserCode()+"\" title=\""+ userdata.getUserType() + "\">"+userdata.getLoginName();
			htmlContent += "</option>";
		}
		htmlContent +="</select>";
		return "html";
	}

	public int nodeId;
	public Vector assignWorkspaceRightsDetails;
	public Vector nodeDetail;
	public String nodeName;
	public Vector<DTOStageMst> getStageDetail;
	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail;
	public Vector<DTOWorkSpaceNodeHistory> getLatestStage=null;
	public String RightsType;


	public Vector getGetUserRightsDetail() {
		return getUserRightsDetail;
	}


	public void setGetUserRightsDetail(Vector getUserRightsDetail) {
		this.getUserRightsDetail = getUserRightsDetail;
	}


	public Vector getGetStageDetail() {
		return getStageDetail;
	}


	public void setGetStageDetail(Vector getStageDetail) {
		this.getStageDetail = getStageDetail;
	}


	public int getNodeId() {
		return nodeId;
	}


	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}


	public Vector getAssignWorkspaceRightsDetails() {
		return assignWorkspaceRightsDetails;
	}


	public void setAssignWorkspaceRightsDetails(Vector assignWorkspaceRightsDetails) {
		this.assignWorkspaceRightsDetails = assignWorkspaceRightsDetails;
	}


	public Vector getNodeDetail() {
		return nodeDetail;
	}


	public void setNodeDetail(Vector nodeDetail) {
		this.nodeDetail = nodeDetail;
	}


	public String getNodeName() {
		return nodeName;
	}


	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}
