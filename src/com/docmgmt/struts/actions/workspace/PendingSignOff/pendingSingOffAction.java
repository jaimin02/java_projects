package com.docmgmt.struts.actions.workspace.PendingSignOff;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class pendingSingOffAction extends ActionSupport {
	public ArrayList<HashMap<String, Object>> subqueryMsg = new ArrayList<HashMap<String, Object>>();
	public DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public int userCode;
	public Vector<DTOUserMst> assignWorkspaceRightsDetails;
	public String workSpaceID;
	public int nodeId;
	public String[] multiCheckUser;
	public int [] stageIds;
	public String remark;
	public String RightsType;
	public int uCode;
	public Vector<DTOWorkSpaceNodeDetail> getAllNodeIds;
	public int documentCount=0;
	public String htmlContent;
	public String lbl_folderName;
	public String lbl_nodeName;
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	public String roleCode;
	public String OpenFileAndSignOff;
	public Vector<DTOWorkSpaceNodeHistory> getPreviewDetail;
	public String userNameForPreview;
	public String signId;
	public String signImg;
	public String signStyle;
	public String roleName;
	ArrayList<String> time = new ArrayList<String>();
	public String dateForPreview;
	public String ManualSignatureConfig;
	public String ApplicationUrl;
	
	@Override
	public String execute() {

		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userName = ActionContext.getContext().getSession().get("username").toString();
		lbl_folderName = knetProperties.getValue("ForlderName");
		lbl_nodeName = knetProperties.getValue("NodeName");
		OpenFileAndSignOff = knetProperties.getValue("OpenFileAndSignOff");
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		String singaturePath = knetProperties.getValue("signatureFile");
		HashMap<String, Object> hoQueryDetail;
		ArrayList<DTOWorkSpaceNodeHistory>  getPendingWorks;
		DTOWorkSpaceNodeHistory dtoSubQueryMst;

		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		ApplicationUrl = propertyInfo.getValue("ApplicationUrl");
		
		Vector<DTOWorkSpaceMst> workspaces;
			
		DTOWorkSpaceNodeHistory dtoNodeHistory = new DTOWorkSpaceNodeHistory();
		DTOWorkSpaceNodeHistory dtoNodeHistoryForNextStage = new DTOWorkSpaceNodeHistory();
		
		//workspaces = docMgmtImpl.getUserWorkspace(userGroupCode, userCode);
		workspaces = docMgmtImpl.getUserWorkspaceList(userGroupCode, userCode);
		userNameForPreview = userName;
		
		
		for (int itrWsMst = 0; itrWsMst < workspaces.size(); itrWsMst++) 
		{
			DTOWorkSpaceMst dto = workspaces.get(itrWsMst);
			
			dtoNodeHistory.setWorkSpaceId(dto.getWorkSpaceId());
			dtoNodeHistory.setNodeID(0);
			dtoNodeHistory.setModifyBy(userCode);
			
			getPendingWorks = docMgmtImpl.getPendingReportSingOff(dtoNodeHistory);		
			
			for (int i = 0; i < getPendingWorks.size(); i++) 
			{
				hoQueryDetail = new HashMap<String, Object>();
				dtoSubQueryMst = getPendingWorks.get(i);
	
				dtoNodeHistoryForNextStage.setWorkSpaceId(dto.getWorkSpaceId());
				dtoNodeHistoryForNextStage.setNodeId(dtoSubQueryMst.getNodeId());
				dtoNodeHistoryForNextStage.setModifyBy(userCode);
				DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetail(dto.getWorkSpaceId());
				char c=wsDetail.getProjectType();  
				String projectType=String.valueOf(c); 
				hoQueryDetail.put("projectType",projectType);
				hoQueryDetail.put("wid",dto.getWorkSpaceId());
				hoQueryDetail.put("Project",dto.getWorkSpaceDesc());	
				hoQueryDetail.put("nodeId",dtoSubQueryMst.getNodeId());
				hoQueryDetail.put ("tranNo",dtoSubQueryMst.getTranNo());
				hoQueryDetail.put("nextStageId",dtoSubQueryMst.getNextStageId()); 
				hoQueryDetail.put("userCode",userCode);
				hoQueryDetail.put("userName",userName);
				hoQueryDetail.put ("baseWorkFolder",dtoSubQueryMst.getBaseWorkFolder());
				hoQueryDetail.put("nodeName",dtoSubQueryMst.getNodeName()); 
				hoQueryDetail.put("FileName",dtoSubQueryMst.getFileName()); 
				hoQueryDetail.put("CurrentStage",dtoSubQueryMst.getStageDesc()); 
				hoQueryDetail.put("nextStageDesc",dtoSubQueryMst.getNextStageDesc());
				hoQueryDetail.put("queryDtl",getPendingWorks.get(i).getNodeSignOffHistory());
				hoQueryDetail.put("manualSignatureConfig",docMgmtImpl.getAttributeDetailByAttrName(dto.getWorkSpaceId(), 1, "ManualSignature").getAttrValue());
				hoQueryDetail.put("applicationUrl",propertyInfo.getValue("ApplicationUrl"));
				
				
				String roleCode = docMgmtImpl.getRoleCode(dto.getWorkSpaceId(),userCode);
				roleName= docMgmtImpl.getRoleName(roleCode);
				Vector <DTOWorkSpaceNodeHistory> getSignDetail = docMgmtImpl.getUserSignatureDetail(userCode);
				if(getSignDetail.size()>0){
				signId = getSignDetail.get(0).getUuId();
				//signImg = singaturePath+getSignDetail.get(0).getFolderName()+"/"+getSignDetail.get(0).getFileName();
				signImg =getSignDetail.get(0).getFileName();
				}
				if((signImg==null || signImg.isEmpty()) && getSignDetail.size()>0){
					signStyle =getSignDetail.get(0).getFileType();
				}
				Timestamp currentTime = new Timestamp(System.currentTimeMillis());
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(currentTime,locationName,countryCode);
					dateForPreview = time.get(0);
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(currentTime,locationName,countryCode);
					dateForPreview = time.get(1);
				}
				hoQueryDetail.put("roleName",roleName);
				hoQueryDetail.put("signId",signId);
				hoQueryDetail.put("signImg",signImg);
				hoQueryDetail.put("signStyle",signStyle);
				hoQueryDetail.put("dateForPreview",dateForPreview);
				subqueryMsg.add(hoQueryDetail);	
			}
		}
		return SUCCESS;
	}
	public String documentCount(){
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userName = ActionContext.getContext().getSession().get("username").toString();
		DTOWorkSpaceNodeHistory dtoNodeHistory = new DTOWorkSpaceNodeHistory();
		HashMap<String, Object> hoQueryDetail;
		ArrayList<DTOWorkSpaceNodeHistory>  getPendingWorks;
		DTOWorkSpaceNodeHistory dtoSubQueryMst;

		Vector<DTOWorkSpaceMst> workspaces;
		DTOWorkSpaceNodeHistory dtoNodeHistoryForNextStage = new DTOWorkSpaceNodeHistory();
		//workspaces = docMgmtImpl.getUserWorkspaceDetail(userGroupCode, userCode);
		workspaces = docMgmtImpl.getUserWorkspaceList(userGroupCode, userCode);
		
		for (int itrWsMst = 0; itrWsMst < workspaces.size(); itrWsMst++) 
		{
			DTOWorkSpaceMst dto = workspaces.get(itrWsMst);
			
			dtoNodeHistory.setWorkSpaceId(dto.getWorkSpaceId());
			dtoNodeHistory.setNodeID(0);
			dtoNodeHistory.setModifyBy(userCode);
			getPendingWorks=docMgmtImpl.getPendingReportSingOff(dtoNodeHistory);
		for (int i = 0; i < getPendingWorks.size(); i++) 
		{
			hoQueryDetail = new HashMap<String, Object>();
			dtoSubQueryMst = getPendingWorks.get(i);

			dtoNodeHistoryForNextStage.setWorkSpaceId(dto.getWorkSpaceId());
			dtoNodeHistoryForNextStage.setNodeId(dtoSubQueryMst.getNodeId());
			dtoNodeHistoryForNextStage.setModifyBy(userCode);
			DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetailWSList(dto.getWorkSpaceId());
			char c=wsDetail.getProjectType();  
			String projectType=String.valueOf(c); 
			hoQueryDetail.put("projectType",projectType);
			hoQueryDetail.put("wid",dto.getWorkSpaceId());
			hoQueryDetail.put("Project",dto.getWorkSpaceDesc());	
			hoQueryDetail.put("nodeId",dtoSubQueryMst.getNodeId());
			hoQueryDetail.put ("tranNo",dtoSubQueryMst.getTranNo());
			hoQueryDetail.put("nextStageId",dtoSubQueryMst.getNextStageId()); 
			hoQueryDetail.put("userCode",userCode);
			hoQueryDetail.put("userName",userName);
			hoQueryDetail.put ("baseWorkFolder",dtoSubQueryMst.getBaseWorkFolder());
			hoQueryDetail.put("nodeName",dtoSubQueryMst.getNodeName()); 
			hoQueryDetail.put("FileName",dtoSubQueryMst.getFileName()); 
			hoQueryDetail.put("CurrentStage",dtoSubQueryMst.getStageDesc()); 
			hoQueryDetail.put("nextStageDesc",dtoSubQueryMst.getNextStageDesc());
			hoQueryDetail.put("queryDtl",getPendingWorks.get(i).getNodeSignOffHistory());
			subqueryMsg.add(hoQueryDetail);	
			}
		}
		if(subqueryMsg.size()>0){
			documentCount = subqueryMsg.size();
		}
		htmlContent=Integer.valueOf(documentCount).toString();
		//return Integer.valueOf(commentCount).toString();
		return "html";
	}
	public String getUserRights(){
		int userGroupCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserCode(userCode);
		userMst.setUserGroupCode(userGroupCode);
		roleCode = docMgmtImpl.getRoleCode(workSpaceID, userCode);
		//assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForCSV(workSpaceID, nodeId,"modulewise");
		assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForCSVForRoleCode(workSpaceID, nodeId,roleCode,"modulewise");
		
		return SUCCESS;
	}

	
	public void updateAndDeleteRights(){

		DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();
		//String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();

		WsNodeDetail=docMgmtImpl.getModulewiseChildNodes(workSpaceID,nodeId);
		String nodeIdsCSV = "";
		String selectedUsers [] = multiCheckUser;
		for(int i=0;i<WsNodeDetail.size();i++){	
			DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i);
			nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
		}
		nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
		
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForCSV(workSpaceID, nodeId,userCode);
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForTimeLine = docMgmtImpl.getUserRightsDetailForCSVforTimeLine(workSpaceID, nodeId,userCode);
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRights(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForCSV(workSpaceID, userCode, stageIds,nodeIdsCSV);
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(workSpaceID);
			objWSUserRightsMstforModuleHistory.setNodeId(nodeId);
			objWSUserRightsMstforModuleHistory.setModifyBy(userId);
			
			String stageDesc="";
			String stage;
			int stageid;
				for(int i=0;i<stageIds.length;i++){
					stageid=stageIds[i];
					stage= docMgmtImpl.getStageDesc(stageid);
					stageDesc+=stage+",";
				}
			System.out.println("StageId="+stageDesc);
			  if (stageDesc != null && stageDesc.length() > 0){
				  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
			  }
			  objWSUserRightsMstforModuleHistory.setStages(stageDesc);			  
			  DTOUserMst objUserMst = docMgmtImpl.getUserInfo(userCode);
			  System.out.println(objUserMst.getUserGroupCode());
			  Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
			  objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
			  objWSUserRightsMstforModuleHistory.setUserCode(userCode);
			  objWSUserRightsMstforModuleHistory.setRemark(remark);
			  objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
			  objWSUserRightsMstforModuleHistory.setMode(1);
			   //docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
			   docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
	
		System.out.println("-----------------------Updating Rights------------------------");
		
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsListForTimeLine = new ArrayList<DTOWorkSpaceUserRightsMst>();
		
		if(RightsType!=null && RightsType.equalsIgnoreCase("modulewiserights")){
			WsNodeDetail.clear();
			WsNodeDetail=docMgmtImpl.getChildNodesModulewise(workSpaceID,nodeId);
			if(nodeId==1)
			{
				WsNodeDetail.removeElementAt(1);
			}
			nodeIdsCSV="";
			for(int i=0;i<WsNodeDetail.size();i++){	
				DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i);
				nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
			}
			nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRights(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForCSV(workSpaceID, uCode, stageIds,nodeIdsCSV);
			
			for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
				DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
				
					objUserMst = docMgmtImpl.getUserInfo(uCode);
					userGroupCode = new Integer(objUserMst.getUserGroupCode());
					DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
					
					objWorkSpaceUserRightsMst.setWorkSpaceId(workSpaceID);
					objWorkSpaceUserRightsMst.setModifyBy(userId);
					objWorkSpaceUserRightsMst.setUserCode(uCode);
					objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
					objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
				
					objWorkSpaceUserRightsMst.setCanReadFlag('Y');
					objWorkSpaceUserRightsMst.setCanAddFlag('Y');
					objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
					objWorkSpaceUserRightsMst.setCanEditFlag('Y');
					objWorkSpaceUserRightsMst.setAdvancedRights("Y");
					objWorkSpaceUserRightsMst.setRoleCode(roleCode);
					objWorkSpaceUserRightsMst.setMode(1);
					
					for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
						DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
						dtoClone.setStageId(stageIds[istageIds]);
						userRightsList.add(dtoClone);
					}
			}
			//docMgmtImpl.insertFolderSpecificMultipleUserRights(userRightsList);
			docMgmtImpl.insertFolderSpecificMultipleUserRightsWithRoleCode(userRightsList);
			
			getAllNodeIds = docMgmtImpl.getAllLeafNodeIds(workSpaceID);
			
			ArrayList<Integer> nodeIds = new ArrayList();
			{
				for(int k=0;k<getAllNodeIds.size();k++){
					nodeIds.add(getAllNodeIds.get(k).getNodeId());
				}
			}
			for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
				DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
				if(nodeIds.contains(nodeDetail.getNodeId())){
					objUserMst = docMgmtImpl.getUserInfo(uCode);
					userGroupCode = new Integer(objUserMst.getUserGroupCode());
					DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
					
					objWorkSpaceUserRightsMst.setWorkSpaceId(workSpaceID);
					objWorkSpaceUserRightsMst.setModifyBy(userId);
					objWorkSpaceUserRightsMst.setUserCode(uCode);
					objWorkSpaceUserRightsMst.setExistUserCode(userCode);
					objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
					objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
				
					objWorkSpaceUserRightsMst.setCanReadFlag('Y');
					objWorkSpaceUserRightsMst.setCanAddFlag('Y');
					objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
					objWorkSpaceUserRightsMst.setCanEditFlag('Y');
					objWorkSpaceUserRightsMst.setAdvancedRights("Y");
					objWorkSpaceUserRightsMst.setDuration(getUserRightsDetailForTimeLine.get(0).getDuration());
					objWorkSpaceUserRightsMst.setMode(2);
					
					for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
						DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
						dtoClone.setStageId(stageIds[istageIds]);
						userRightsListForTimeLine.add(dtoClone);
					}
				}
			}
			docMgmtImpl.insertFolderSpecificMultipleUserRightsForTimeLine(userRightsListForTimeLine);
			
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(workSpaceID);
			objWSUserRightsMstforModuleHistory.setNodeId(nodeId);
			objWSUserRightsMstforModuleHistory.setModifyBy(userId);
			
			System.out.println("StageId="+stageDesc);
			 /* if (stageDesc != null && stageDesc.length() > 0){
				  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
			  }*/
			  objWSUserRightsMstforModuleHistory.setStages(stageDesc);
			  objWSUserRightsMstforModuleHistory.setMode(1);
			  
				objUserMst = docMgmtImpl.getUserInfo(uCode);
				System.out.println(objUserMst.getUserGroupCode());
				userGroupCode = new Integer(objUserMst.getUserGroupCode());
				objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
				
				objWSUserRightsMstforModuleHistory.setUserCode(uCode);
				 objWSUserRightsMstforModuleHistory.setMode(1);
				
				//docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
				docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
		}		
	}	
	
	public String getUserRightsForCr(){
		int userGroupCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserCode(userCode);
		userMst.setUserGroupCode(userGroupCode);
		
		DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetailWSList(workSpaceID);
		char c=wsDetail.getProjectType();  
		String projectType=String.valueOf(c); 
		if(projectType.equals("D")){
		assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForDocSign(workSpaceID, nodeId,userGroupCode);
		}
		else{
			assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForCSV(workSpaceID, nodeId,"modulewise");
		}
		return SUCCESS;
	}
public void updateAndDeleteRightsForCR(){
		
		DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();

		WsNodeDetail=docMgmtImpl.getModulewiseChildNodes(workSpaceID,nodeId);
		String nodeIdsCSV = "";
		int selectedUsers = userCode;
		for(int i=0;i<WsNodeDetail.size();i++){	
			DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i);
			nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
		}
		Vector<DTOWorkSpaceUserMst> getUserRightsForWorksapce=new Vector<>();
		nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
		
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForCSV(workSpaceID, nodeId,userCode);
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForTimeLine = docMgmtImpl.getUserRightsDetailForCSVforTimeLine(workSpaceID, nodeId,userCode);
		DTOUserMst getUserDtl=docMgmtImpl.getUserInfo(userCode);
		
		getUserRightsForWorksapce=docMgmtImpl.getUserRightsForWorkspace(workSpaceID, uCode,getUserDtl.getUserGroupCode());
		if(getUserRightsForWorksapce.size()==0){
			
			DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
			dto.setWorkSpaceId(workSpaceID);
			dto.setUserCode(uCode);
			dto.setUserGroupCode(getUserDtl.getUserGroupCode());
			dto.setAdminFlag('N');
			
	        Date date = new Date();
	        Timestamp currentDate=new Timestamp(date.getTime());  
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            System.out.println(formatter.format(currentDate));   
	        
            // Convert Date to Calendar
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.add(Calendar.YEAR, 50);
	        
	        Date afterDate = c.getTime();
	        Timestamp toDate=new Timestamp(afterDate.getTime()); 		
			
			dto.setLastAccessedOn(currentDate);
			dto.setRemark("");
			dto.setModifyBy(userId);
			dto.setModifyOn(currentDate);
			dto.setFromDt(currentDate);
			dto.setToDt(toDate);
			dto.setRightsType("modulewise");

		  docMgmtImpl.insertUsertoWorkspaceUserMstForDocSign(dto);
		}
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRights(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForCSV(workSpaceID, userCode, stageIds,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(workSpaceID, userCode, stageIds,nodeIdsCSV);
			
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(workSpaceID);
			objWSUserRightsMstforModuleHistory.setNodeId(nodeId);
			objWSUserRightsMstforModuleHistory.setModifyBy(userId);
			
			String stageDesc="";
			String stage;
			int stageid;
				for(int j=0;j<stageIds.length;j++){
					stageid=stageIds[j];
					stage= docMgmtImpl.getStageDesc(stageid);
					stageDesc+=stage+",";
				}
			System.out.println("StageId="+stageDesc);
			  if (stageDesc != null && stageDesc.length() > 0){
				  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
			  }
			  objWSUserRightsMstforModuleHistory.setStages(stageDesc);
			  objWSUserRightsMstforModuleHistory.setMode(2);
			  
			  DTOUserMst objUserMst = docMgmtImpl.getUserInfo(userCode);
			  System.out.println(objUserMst.getUserGroupCode());
			  Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
			  objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
			  objWSUserRightsMstforModuleHistory.setUserCode(userCode);
			  objWSUserRightsMstforModuleHistory.setRemark("test");
				
				docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
	
		System.out.println("-----------------------Updating Rights------------------------");
		
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsListForTimeLine = new ArrayList<DTOWorkSpaceUserRightsMst>();
		
			WsNodeDetail.clear();
			WsNodeDetail=docMgmtImpl.getChildNodesModulewise(workSpaceID,nodeId);
			if(nodeId==1)
			{
				WsNodeDetail.removeElementAt(1);
			}
			nodeIdsCSV="";
			for(int k=0;k<WsNodeDetail.size();k++){	
				DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(k);
				nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
			}
			nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRights(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForCSV(workSpaceID, uCode, stageIds,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(workSpaceID, uCode, stageIds,nodeIdsCSV);
			
			for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
				DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
				
					objUserMst = docMgmtImpl.getUserInfo(uCode);
					userGroupCode = new Integer(objUserMst.getUserGroupCode());
					DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
					
					objWorkSpaceUserRightsMst.setWorkSpaceId(workSpaceID);
					objWorkSpaceUserRightsMst.setModifyBy(userId);
					objWorkSpaceUserRightsMst.setUserCode(uCode);
					objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
					objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
				
					objWorkSpaceUserRightsMst.setCanReadFlag('Y');
					objWorkSpaceUserRightsMst.setCanAddFlag('Y');
					objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
					objWorkSpaceUserRightsMst.setCanEditFlag('Y');
					objWorkSpaceUserRightsMst.setAdvancedRights("Y");
					
					for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
						DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
						dtoClone.setStageId(stageIds[istageIds]);
						userRightsList.add(dtoClone);
					}
				
				
			}
			docMgmtImpl.insertFolderSpecificMultipleUserRights(userRightsList);
			
			
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(workSpaceID);
			objWSUserRightsMstforModuleHistory.setNodeId(nodeId);
			objWSUserRightsMstforModuleHistory.setModifyBy(userId);
			
			System.out.println("StageId="+stageDesc);
			  /*if (stageDesc != null && stageDesc.length() > 0){
				  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
			  }*/
			  objWSUserRightsMstforModuleHistory.setStages(stageDesc);
			  objWSUserRightsMstforModuleHistory.setMode(1);
			  
			
				objUserMst = docMgmtImpl.getUserInfo(uCode);
				System.out.println(objUserMst.getUserGroupCode());
				userGroupCode = new Integer(objUserMst.getUserGroupCode());
				objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
				
				objWSUserRightsMstforModuleHistory.setUserCode(uCode);
				
				docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);	
	}
}

