package com.docmgmt.struts.actions.workspace;

import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class SaveWorkspaceRightsAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String RightsType;
	public String remark;
	public int uCode;
	public int userCode;
	public Vector<DTOUserMst> assignWorkspaceRightsDetails;
	Vector<DTOWorkSpaceUserRightsMst> getSRFlagData=new Vector<>();
	Vector<DTOWorkSpaceUserRightsMst> getAdjustDateData=new Vector<>();
	public int duration;
	public Vector<DTOWorkSpaceNodeDetail> getAllNodeIds;
	
	Vector<DTOWorkSpaceUserRightsMst> hoursData=new Vector<>();
	public String wsId;
	public int  userGrpCode;
	public int stageId;
	public String ProjectTimeLine;
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	DTOWorkSpaceUserRightsMst getdtl = new DTOWorkSpaceUserRightsMst();
	public String htmlContent;
	public String selectUsers;
	public String temp;
	public String workSpaceId;
	public String OpenFileAndSignOff;
	public String redirectAction;
	public String TimelineCalculation;
	public String fromDt;
	public String toDt;
	
	@Override
	public String execute() throws ParseException
	{
		if(buttonName.equalsIgnoreCase("Change Rights"))
		{
			updateAndDeleteRights();
		}
		if(buttonName.equalsIgnoreCase("Assign Rights"))
		{
		insertStages();
		}
		else if(buttonName.equalsIgnoreCase("Remove Rights"))
		{
			DeleteUserRights();
		}
		return SUCCESS;
	}
	public void insertStages() throws ParseException
	{
		
		Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsListForTimeLine = new ArrayList<DTOWorkSpaceUserRightsMst>();
		
		DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
		String roleCode;
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		ProjectTimeLine = knetProperties.getValue("ProjectTimeLine");
		TimelineCalculation =knetProperties.getValue("TimelineCalculation");
		
		String selectedUsers [] = multiCheckUser;
		String nodeIdsCSV = "";
		
		if(stageIds==null || selectedUsers.length==0)
			return;
		if(RightsType!=null && RightsType.equalsIgnoreCase("modulewiserights")){
			WsNodeDetail=docMgmtImpl.getChildNodesModulewise(workspaceID,nodeId);
			if(nodeId==1)
			{
				WsNodeDetail.removeElementAt(1);
			}
			for(int i=0;i<WsNodeDetail.size();i++){	
				DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i);
				nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
			}
			nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRights(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			if(ProjectTimeLine.equalsIgnoreCase("yes")){
			docMgmtImpl.RemoveUserRightsfromTimeline(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			}
			for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
				DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
				for(int k=0;k<selectedUsers.length;k++)
				{
					DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(selectedUsers[k]));
					Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
					DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
					
					
					
					objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
					objWorkSpaceUserRightsMst.setModifyBy(userId);
					objWorkSpaceUserRightsMst.setUserCode(Integer.parseInt(selectedUsers[k]));
					objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
					objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
				
					objWorkSpaceUserRightsMst.setCanReadFlag('Y');
					objWorkSpaceUserRightsMst.setCanAddFlag('Y');
					objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
					objWorkSpaceUserRightsMst.setCanEditFlag('Y');
					objWorkSpaceUserRightsMst.setAdvancedRights("Y");
					roleCode= docMgmtImpl.getRoleCode(workspaceID, Integer.parseInt(selectedUsers[k]));
					objWorkSpaceUserRightsMst.setRoleCode(roleCode);
					objWorkSpaceUserRightsMst.setMode(1);
					
					for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
						DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
						dtoClone.setStageId(stageIds[istageIds]);
						userRightsList.add(dtoClone);
					}
				}
				
			}
			
			//docMgmtImpl.insertFolderSpecificMultipleUserRights(userRightsList);
			docMgmtImpl.insertFolderSpecificMultipleUserRightsWithRoleCode(userRightsList);
			getAllNodeIds = docMgmtImpl.getAllLeafNodeIds(workspaceID);
			
			ArrayList<Integer> nodeIds = new ArrayList();
			{
				for(int k=0;k<getAllNodeIds.size();k++){
					nodeIds.add(getAllNodeIds.get(k).getNodeId());
				}
			}
			boolean showHours=false;
			Vector<DTOWorkSpaceNodeAttribute> getAttrDtl = docMgmtImpl.getTimelineAttrDtl(workspaceID);
			
			if(getAttrDtl.size()>0){
				showHours=true;
			}
			
			if(ProjectTimeLine.equalsIgnoreCase("yes") && showHours==true ){
				boolean skipCurrent=false;
				userRightsListForTimeLine = new ArrayList<DTOWorkSpaceUserRightsMst>();
				Vector<DTOWorkSpaceUserRightsMst> getLastRightsDtl = new Vector<>();
				//int nodeId = userRightsList.get(0).getNodeId();
				int stageId = userRightsList.get(0).getStageId();
				getLastRightsDtl = docMgmtImpl.getLastRightsRecordDtlForAdjustDate(workspaceID,nodeId,stageId);
				if(getLastRightsDtl.size()<=0){
					getLastRightsDtl = docMgmtImpl.UserOnNodeTimelineTracking(workspaceID,nodeId);
					
				}
				DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
				String attrValues=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
				
				if(getLastRightsDtl.size()<=0 && (attrValues != null && !attrValues.equals(""))){
					skipCurrent=true;
					LocalDateTime startDate=null;
					LocalDateTime endDate = null;
					String s[]=attrValues.split("/");
					startDate=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
					endDate=startDate.plusHours(duration*3);
					DayOfWeek dayOfWeek = endDate.getDayOfWeek();
					//System.out.println(dayOfWeek);
					if(dayOfWeek==DayOfWeek.SUNDAY){
						endDate=endDate.plusHours(24);
					}
					dto.setWorkSpaceId(workspaceID);
					dto.setNodeId(nodeId);
					dto.setStartDate(Timestamp.valueOf(startDate));
					dto.setEndDate(Timestamp.valueOf(endDate));
					dto.setAdjustDate(Timestamp.valueOf(endDate));
					
				}
									
				if(getLastRightsDtl.size()>0){
					int lastIndex = getLastRightsDtl.size()-1;
					dto.setWorkSpaceDesc(getLastRightsDtl.get(lastIndex).getWorkSpaceId());
					dto.setNodeId(getLastRightsDtl.get(lastIndex).getNodeId());
					dto.setUserCode(getLastRightsDtl.get(lastIndex).getUserCode());
					dto.setUserGroupCode(getLastRightsDtl.get(lastIndex).getUserGroupCode());
					dto.setStageId(getLastRightsDtl.get(lastIndex).getStageId());
					dto.setHours(getLastRightsDtl.get(lastIndex).getHours());
					if(TimelineCalculation.equalsIgnoreCase("Date")){
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					    Date frmDate = dateFormat.parse(fromDt);
					    Timestamp frmtimestamp = new java.sql.Timestamp(frmDate.getTime());
						dto.setFromDate(frmtimestamp);
						
						Date toDate = dateFormat.parse(toDt);
					    Timestamp totimestamp = new java.sql.Timestamp(toDate.getTime());
						dto.setToDate(totimestamp);
					}
					else{
						dto.setStartDate(getLastRightsDtl.get(lastIndex).getStartDate());
						dto.setEndDate(getLastRightsDtl.get(lastIndex).getEndDate());	
					}
					
					dto.setAdjustDate(getLastRightsDtl.get(lastIndex).getAdjustDate());
					dto.setModifyOn(getLastRightsDtl.get(lastIndex).getModifyOn());
				}
				
				for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
					DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
					if(nodeIds.contains(nodeDetail.getNodeId())){
					for(int k=0;k<selectedUsers.length;k++)
					{
						DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(selectedUsers[k]));
						Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
						DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
						
						objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
						objWorkSpaceUserRightsMst.setModifyBy(userId);
						objWorkSpaceUserRightsMst.setUserCode(Integer.parseInt(selectedUsers[k]));
						objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
						objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
					
						objWorkSpaceUserRightsMst.setCanReadFlag('Y');
						objWorkSpaceUserRightsMst.setCanAddFlag('Y');
						objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
						objWorkSpaceUserRightsMst.setCanEditFlag('Y');
						objWorkSpaceUserRightsMst.setAdvancedRights("Y");
						objWorkSpaceUserRightsMst.setDuration(duration);
						if(TimelineCalculation.equalsIgnoreCase("Date")){
							objWorkSpaceUserRightsMst.setFromDate(dto.getFromDate());
							objWorkSpaceUserRightsMst.setToDate(dto.getToDate());
							objWorkSpaceUserRightsMst.setStartDate(dto.getFromDate());
							objWorkSpaceUserRightsMst.setEndDate(dto.getToDate());
						}
						else{
							objWorkSpaceUserRightsMst.setStartDate(dto.getStartDate());
							objWorkSpaceUserRightsMst.setEndDate(dto.getEndDate());
						}
						objWorkSpaceUserRightsMst.setAdjustDate(dto.getAdjustDate());
						objWorkSpaceUserRightsMst.setMode(1);
						
						for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
							DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
							dtoClone.setStageId(stageIds[istageIds]);
							userRightsListForTimeLine.add(dtoClone);
							}
						}
					}
				}
			
				if(TimelineCalculation.equalsIgnoreCase("Date"))
					docMgmtImpl.AttachUserRightsForTimeLineWithExludedDate(userRightsListForTimeLine);
				else
					docMgmtImpl.AttachUserRightsForTimeLine(userRightsListForTimeLine);
				
				Timestamp signOffDate=dto.getAdjustDate();
				
				getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
				LocalDateTime startDate=null;
				LocalDateTime endDate = null;
				
				if(getSRFlagData.size() >0 && (attrValues != null && !attrValues.equals(""))){				
						int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceID, dto.getNodeId());
						
						getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustHoursUpdate(workspaceID,dto.getNodeId(),parentNodeIdforAdjustDate,dto.getUserCode(),dto.getStageId());
						
						LocalDateTime date=signOffDate.toLocalDateTime();
						int t=0;
						if(skipCurrent){
							t=1;
						}
						for(int k=t;k<getAdjustDateData.size();k++){
							//System.out.println("Hours : "+hoursData.get(k).getHours());
							DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getAdjustDateData
									.get(k);
							int noofhours=dtotimeline.getHours();
							if(startDate==null){
								startDate=date;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}
							else{
								startDate=endDate;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}	
							//dtotimeline.setStartDate(dto.getStartDate());
							//dtotimeline.setEndDate(dto.getEndDate());
							dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
				            
				            
				            docMgmtImpl.updateTimelineAdjustDate(dtotimeline);
						}	
					}
				else{

					String attrValue=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
					
					if(attrValue != null && !attrValue.equals("")){
						hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
						
						String s[]=attrValue.split("/");
						
						LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));

						 startDate=null;
						endDate = null;
						for(int k=0;k<hoursData.size();k++){
							//System.out.println("Hours : "+hoursData.get(k).getHours());
							DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) hoursData
									.get(k);
							int noofhours=dtotimeline.getHours();
							if(startDate==null){
								startDate=date;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}
							else{
								startDate=endDate;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}	
							
							dtotimeline.setStartDate(Timestamp.valueOf(startDate));
						
							dtotimeline.setEndDate(Timestamp.valueOf(endDate));
							dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
							
							docMgmtImpl.updateTimelineDatesValue(dtotimeline);
					}
						}
					}
			}
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(workspaceID);
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
			  objWSUserRightsMstforModuleHistory.setMode(1);
			  
			for(int i=0;i<selectedUsers.length;i++)
			{
				DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(selectedUsers[i]));
				System.out.println(objUserMst.getUserGroupCode());
				Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
				objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
				
				objWSUserRightsMstforModuleHistory.setUserCode(Integer.parseInt(selectedUsers[i]));
				 roleCode= docMgmtImpl.getRoleCode(workspaceID, Integer.parseInt(selectedUsers[i]));
				  objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
				//docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
				docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
			}
			
			
		}else{
			
			for(int i=0;i<selectedUsers.length;i++)
			{
				DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(selectedUsers[i]));
				Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
				DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
				
				objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
				objWorkSpaceUserRightsMst.setUserCode(Integer.parseInt(selectedUsers[i]));
				objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
				objWorkSpaceUserRightsMst.setNodeId(nodeId);
			
				objWorkSpaceUserRightsMst.setCanReadFlag('Y');
				objWorkSpaceUserRightsMst.setCanAddFlag('Y');
				objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
				objWorkSpaceUserRightsMst.setCanEditFlag('Y');
				objWorkSpaceUserRightsMst.setAdvancedRights("Y");
				roleCode= docMgmtImpl.getRoleCode(workspaceID, Integer.parseInt(selectedUsers[i]));
				objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
				objWorkSpaceUserRightsMst.setMode(1);
				
				
				for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
					DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
					dtoClone.setStageId(stageIds[istageIds]);
					userRightsList.add(dtoClone);
				}
				//objWorkSpaceUserRightsMst.setStageId(stageId);
				
				/*if (rights.equalsIgnoreCase("readonly")) {
					objWorkSpaceUserRightsMst.setCanAddFlag('N');
					objWorkSpaceUserRightsMst.setCanDeleteFlag('N');
					objWorkSpaceUserRightsMst.setCanEditFlag('N');`
				}else {*/
					
				//}
				
				//docMgmtImpl.updateWorkSpaceUserRights(objWorkSpaceUserRightsMst);
				//objWorkSpaceUserRightsMst = null;
			}
			//docMgmtImpl.insertMultipleUserRights(userRightsList);
			docMgmtImpl.insertFolderSpecificMultipleUserRightsWithRoleCode(userRightsList);
			
		}
		
		
				
	}
	
	public void DeleteUserRights()
	{
		DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		ProjectTimeLine = knetProperties.getValue("ProjectTimeLine");
		//WsNodeDetail=docMgmtImpl.getChildNodesModulewise(workspaceID,nodeId);
		WsNodeDetail=docMgmtImpl.getModulewiseChildNodes(workspaceID,nodeId);
		String nodeIdsCSV = "";
		String selectedUsers [] = multiCheckUser;
		
		for(int i=0;i<WsNodeDetail.size();i++){	
			DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i);
			nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
		}
		nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
		
		//Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForCSV(workspaceID, nodeId,userCode);
		
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRights(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			String roleCode = docMgmtImpl.getRoleCode(workspaceID,userCode);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForCSV(workspaceID, userCode, stageIds,nodeIdsCSV);
			boolean showHours=false;
			Vector<DTOWorkSpaceNodeAttribute> getAttrDtl = docMgmtImpl.getTimelineAttrDtl(workspaceID);
			
			if(getAttrDtl.size()>0){
				showHours=true;
			}
			if(ProjectTimeLine.equalsIgnoreCase("yes") && showHours==true ){
				int ndId = Integer.parseInt(nodeIdsCSV);
				int stageId = stageIds[0];
				//DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
				//dto = docMgmtImpl.getTimeLineRightsDtl(workspaceID, ndId, userCode, stageId);
				
				docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(workspaceID, userCode, stageIds,nodeIdsCSV);
				Vector<DTOWorkSpaceUserRightsMst> getLastRightsDtl = new Vector<>();
				getLastRightsDtl = docMgmtImpl.getLastRightsRecordDtlForAdjustDate(workspaceID,ndId,stageId);
				DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
				
				if(getLastRightsDtl.size()>0){
					int lastIndex = getLastRightsDtl.size()-1;
					dto.setWorkSpaceDesc(getLastRightsDtl.get(lastIndex).getWorkSpaceId());
					dto.setNodeId(getLastRightsDtl.get(lastIndex).getNodeId());
					dto.setUserCode(getLastRightsDtl.get(lastIndex).getUserCode());
					dto.setUserGroupCode(getLastRightsDtl.get(lastIndex).getUserGroupCode());
					dto.setStageId(getLastRightsDtl.get(lastIndex).getStageId());
					dto.setHours(getLastRightsDtl.get(lastIndex).getHours());
					dto.setStartDate(getLastRightsDtl.get(lastIndex).getStartDate());
					dto.setEndDate(getLastRightsDtl.get(lastIndex).getEndDate());
					dto.setAdjustDate(getLastRightsDtl.get(lastIndex).getAdjustDate());
					dto.setModifyOn(getLastRightsDtl.get(lastIndex).getModifyOn());
				}
				getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
				
				
				if(getSRFlagData.size()>0){
				
					Timestamp attrValue=dto.getAdjustDate();
					
					//String attrVal=attrValue.toLocalDateTime().toLocalDate().toString();
					//attrVal=attrVal.replace("-", "/");
					int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceID, ndId);
					getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustHoursUpdate(workspaceID,ndId,parentNodeIdforAdjustDate,dto.getUserCode(),stageId);
					
					//String s[]=attrVal.split("/");
					
					//LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
					LocalDateTime date=attrValue.toLocalDateTime();
					LocalDateTime startDate=null;
					LocalDateTime endDate = null;
					for(int k=0;k<getAdjustDateData.size();k++){
						//System.out.println("Hours : "+hoursData.get(k).getHours());
						DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getAdjustDateData
								.get(k);
						int noofhours=dtotimeline.getHours();
						if(startDate==null){
							startDate=date;
							endDate=startDate.plusHours(noofhours*3);
							DayOfWeek dayOfWeek = endDate.getDayOfWeek();
							//System.out.println(dayOfWeek);
							if(dayOfWeek==DayOfWeek.SUNDAY){
								endDate=endDate.plusHours(24);
							}
						}
						else{
							startDate=endDate;
							endDate=startDate.plusHours(noofhours*3);
							DayOfWeek dayOfWeek = endDate.getDayOfWeek();
							//System.out.println(dayOfWeek);
							if(dayOfWeek==DayOfWeek.SUNDAY){
								endDate=endDate.plusHours(24);
							}
						}	
						//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
						
						//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
						dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
						
						docMgmtImpl.updateTimelineDatesValue(dtotimeline);
					}	
				
				}else{
					String attrValue=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
					
					if(attrValue != null && !attrValue.equals("")){
						hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
						
						String s[]=attrValue.split("/");
						
						LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));

						LocalDateTime startDate=null;
						LocalDateTime endDate = null;
						for(int k=0;k<hoursData.size();k++){
							//System.out.println("Hours : "+hoursData.get(k).getHours());
							DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) hoursData
									.get(k);
							int noofhours=dtotimeline.getHours();
							if(startDate==null){
								startDate=date;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}
							else{
								startDate=endDate;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}	
							
							dtotimeline.setStartDate(Timestamp.valueOf(startDate));
						
							dtotimeline.setEndDate(Timestamp.valueOf(endDate));
							dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
							
							docMgmtImpl.updateTimelineDatesValue(dtotimeline);
							
						}	
					}
				}
			}
			
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(workspaceID);
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
			  objWSUserRightsMstforModuleHistory.setMode(2);
			  
			  DTOUserMst objUserMst = docMgmtImpl.getUserInfo(userCode);
			  System.out.println(objUserMst.getUserGroupCode());
			  Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
			  objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
			  objWSUserRightsMstforModuleHistory.setUserCode(userCode);
			  objWSUserRightsMstforModuleHistory.setRemark(remark);
			  objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
				
			  //docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
			  docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
	}
	
	public String moduleUserDetailHistory(){
		
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		lbl_folderName = knetProperties.getValue("ForlderName");
   		lbl_nodeName = knetProperties.getValue("NodeName");
		moduleUserDetailHistory = docMgmtImpl.getmoduleUserDetailHistory(workspaceID,nodeId);
		return SUCCESS;
		
	}
	
	public String getUserRights(){
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		int userGroupCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String usertypecode=ActionContext.getContext().getSession().get("usertypecode").toString();
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserCode(userCode);
		userMst.setUserGroupCode(userGroupCode);
		String roleCode = docMgmtImpl.getRoleCode(workspaceID, userCode);
		//assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetail(workspaceID, userMst,"modulewise");
		if(usertypecode.equalsIgnoreCase("0002")){
			assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForCSV(workspaceID, nodeId,"modulewise");
		}
		else{
			assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForCSVForRoleCode(workspaceID, nodeId,roleCode,"modulewise");
		}
		
		return SUCCESS;
	}
	
	public void updateAndDeleteRights(){

		DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		ProjectTimeLine = knetProperties.getValue("ProjectTimeLine");
		
		WsNodeDetail=docMgmtImpl.getModulewiseChildNodes(workspaceID,nodeId);
		String nodeIdsCSV = "";
		String selectedUsers [] = multiCheckUser;
		for(int i=0;i<WsNodeDetail.size();i++){	
			DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i);
			nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
		}
		nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
		
		//Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForCSV(workspaceID, nodeId,userCode);
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForTimeLine = docMgmtImpl.getUserRightsDetailForCSVforTimeLine(workspaceID, nodeId,userCode);
		
			String roleCode = docMgmtImpl.getRoleCode(workspaceID,userCode);
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRights(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForCSV(workspaceID, userCode, stageIds,nodeIdsCSV);
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(workspaceID, userCode, stageIds,nodeIdsCSV);
			
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(workspaceID);
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
			  objWSUserRightsMstforModuleHistory.setMode(2);
			  
			  DTOUserMst objUserMst = docMgmtImpl.getUserInfo(userCode);
			  System.out.println(objUserMst.getUserGroupCode());
			  Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
			  objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
			  objWSUserRightsMstforModuleHistory.setUserCode(userCode);
			  objWSUserRightsMstforModuleHistory.setRemark(remark);
			  objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
				
				//docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
				docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
	
		System.out.println("-----------------------Updating Rights------------------------");
		
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsListForTimeLine = new ArrayList<DTOWorkSpaceUserRightsMst>();
		
		if(RightsType!=null && RightsType.equalsIgnoreCase("modulewiserights")){
			WsNodeDetail.clear();
			WsNodeDetail=docMgmtImpl.getChildNodesModulewise(workspaceID,nodeId);
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
			roleCode = docMgmtImpl.getRoleCode(workspaceID,uCode);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForCSV(workspaceID, uCode, stageIds,nodeIdsCSV);
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(workspaceID, uCode, stageIds,nodeIdsCSV);
			
			for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
				DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
				
					objUserMst = docMgmtImpl.getUserInfo(uCode);
					userGroupCode = new Integer(objUserMst.getUserGroupCode());
					DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
					
					objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
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
			getAllNodeIds = docMgmtImpl.getAllLeafNodeIds(workspaceID);
			
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
					
					objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
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
					objWorkSpaceUserRightsMst.setDuration(0);
					objWorkSpaceUserRightsMst.setMode(2);
					
					for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
						DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
						dtoClone.setStageId(stageIds[istageIds]);
						userRightsListForTimeLine.add(dtoClone);
					}
				}
			}
			if(ProjectTimeLine.equalsIgnoreCase("yes")){
			docMgmtImpl.insertFolderSpecificMultipleUserRightsForTimeLine(userRightsListForTimeLine);
			}
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(workspaceID);
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
				objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
				objWSUserRightsMstforModuleHistory.setMode(1);
				
				//docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
				docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
			
		}		
	}
	
public String updateProjectPlanning(){
	
	getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(wsId);
	
	
	if(getSRFlagData.size()>0){
		
		getdtl=docMgmtImpl.getTimeLineRightsMstdtl(wsId,nodeId,userCode,userGrpCode,stageId);
		
		int oldHrs=getdtl.getHours();
		Timestamp attrValued=getdtl.getAdjustDate();
		
		//String attrVals=attrValued.toLocalDateTime().toLocalDate().toString();
		//attrVals=attrVals.replace("-", "/");
		
		//String olds[]=attrVals.split("/");
		
		if(duration>oldHrs){
			//LocalDateTime olddate=LocalDateTime.of(LocalDate.of(Integer.parseInt(olds[0]),Integer.parseInt(olds[1]),Integer.parseInt(olds[2])), LocalTime.of(0,0));
			LocalDateTime olddate=attrValued.toLocalDateTime();
			LocalDateTime newdate;
			int hrs=duration-oldHrs;
			newdate=olddate.plusHours(hrs*3);
			DayOfWeek dayOfWeek = newdate.getDayOfWeek();
			//System.out.println(dayOfWeek);
			if(dayOfWeek==DayOfWeek.SUNDAY){
				newdate=newdate.plusHours(24);
			}
			Timestamp newAdjustDate=Timestamp.valueOf(newdate);
			getdtl.setHours(duration);
			getdtl.setAdjustDate(newAdjustDate);
			docMgmtImpl.updateTimelineHoursAdjustDate(getdtl);
		}
		
		if(duration<oldHrs){
			//LocalDateTime olddate=LocalDateTime.of(LocalDate.of(Integer.parseInt(olds[0]),Integer.parseInt(olds[1]),Integer.parseInt(olds[2])), LocalTime.of(23,0));
			LocalDateTime olddate=attrValued.toLocalDateTime();
			LocalDateTime newdate;
			int hrs=oldHrs-duration;
			newdate=olddate.minusHours(hrs*3);
			DayOfWeek dayOfWeek = newdate.getDayOfWeek();
			//System.out.println(dayOfWeek);
			if(dayOfWeek==DayOfWeek.SUNDAY){
				newdate=newdate.minusHours(24);
			}
			Timestamp newAdjustDate=Timestamp.valueOf(newdate);
			getdtl.setHours(duration);
			getdtl.setAdjustDate(newAdjustDate);
			docMgmtImpl.updateTimelineHoursAdjustDate(getdtl);
		}
		Timestamp attrValue=docMgmtImpl.getTimeLineRightsMst(wsId,nodeId,userCode,userGrpCode,stageId).get(0).getAdjustDate();
		
		//String attrVal=attrValue.toLocalDateTime().toLocalDate().toString();
		//attrVal=attrVal.replace("-", "/");
		int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(wsId, nodeId);
		getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustHoursUpdate(wsId,nodeId,parentNodeIdforAdjustDate,userCode,stageId);
		
		//String s[]=attrVal.split("/");
		
		//LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
		LocalDateTime date=attrValue.toLocalDateTime();
		LocalDateTime startDate=null;
		LocalDateTime endDate = null;
		for(int k=0;k<getAdjustDateData.size();k++){
			//System.out.println("Hours : "+hoursData.get(k).getHours());
			DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getAdjustDateData
					.get(k);
			int noofhours=dtotimeline.getHours();
			if(startDate==null){
				startDate=date;
				endDate=startDate.plusHours(noofhours*3);
				DayOfWeek dayOfWeek = endDate.getDayOfWeek();
				//System.out.println(dayOfWeek);
				if(dayOfWeek==DayOfWeek.SUNDAY){
					endDate=endDate.plusHours(24);
				}
			}
			else{
				startDate=endDate;
				endDate=startDate.plusHours(noofhours*3);
				DayOfWeek dayOfWeek = endDate.getDayOfWeek();
				//System.out.println(dayOfWeek);
				if(dayOfWeek==DayOfWeek.SUNDAY){
					endDate=endDate.plusHours(24);
				}
			}	
			//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
			
			//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
			dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
			
			docMgmtImpl.updateTimelineDatesValue(dtotimeline);
		}	
	
	}else{
		
		docMgmtImpl.UpdateHoursInTimeWorkspaceRightsMst(wsId,nodeId,userCode,userGrpCode,duration,stageId);
		String attrValue=docMgmtImpl.getAttributeDetailByAttrName(wsId,1,"Project Start Date").getAttrValue();
		
		if(attrValue != null && !attrValue.equals("")){
			hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(wsId);
			
			String s[]=attrValue.split("/");
			
			LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));

			LocalDateTime startDate=null;
			LocalDateTime endDate = null;
			for(int k=0;k<hoursData.size();k++){
				//System.out.println("Hours : "+hoursData.get(k).getHours());
				DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) hoursData
						.get(k);
				int noofhours=dtotimeline.getHours();
				if(startDate==null){
					startDate=date;
					endDate=startDate.plusHours(noofhours*3);
					DayOfWeek dayOfWeek = endDate.getDayOfWeek();
					//System.out.println(dayOfWeek);
					if(dayOfWeek==DayOfWeek.SUNDAY){
						endDate=endDate.plusHours(24);
					}
				}
				else{
					startDate=endDate;
					endDate=startDate.plusHours(noofhours*3);
					DayOfWeek dayOfWeek = endDate.getDayOfWeek();
					//System.out.println(dayOfWeek);
					if(dayOfWeek==DayOfWeek.SUNDAY){
						endDate=endDate.plusHours(24);
					}
				}	
				
				dtotimeline.setStartDate(Timestamp.valueOf(startDate));
			
				dtotimeline.setEndDate(Timestamp.valueOf(endDate));
				dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
				
				docMgmtImpl.updateTimelineDatesValue(dtotimeline);
				
			}	
		}
	}
	
	
	return SUCCESS;
}

public String CheckUserRights()
{
	htmlContent="";
	String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
	System.out.println(selectUsers);
	Vector <DTOWorkSpaceUserRightsMst> chekUserRights = docMgmtImpl.checkUserRights(workspaceID, nodeId, selectUsers );
	if(chekUserRights.size()>0){
		htmlContent ="true";
	}
	else{
		htmlContent ="false";
	}
	return "html";
}


public String BulkUserinsertStages() throws ParseException, MalformedURLException
{
	
	Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();
	ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
	ArrayList<DTOWorkSpaceUserRightsMst> userRightsListForTimeLine = new ArrayList<DTOWorkSpaceUserRightsMst>();
	
	DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
	String roleCode;
	String workspaceID=workSpaceId;//ActionContext.getContext().getSession().get("ws_id").toString();
	int userGrpCode=docMgmtImpl.getUserByCode(userCode).getUserGroupCode();
	int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	
	
	
	ProjectTimeLine = knetProperties.getValue("ProjectTimeLine");
	
		
	String[] selectValuesArray = temp.split(",");
	String nodeIdsCSV = "";
	if(selectValuesArray.length==0){
		return "false";
	}
	else{
		
		for(int x = 0; x<selectValuesArray.length; x++) 
		{
			userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
			int nodeId= Integer.parseInt(selectValuesArray[x].split("_")[0].toString());
			stageId= Integer.parseInt(selectValuesArray[x].split("_")[1].toString());
			duration = Integer.parseInt(selectValuesArray[x].split("_")[2].toString());
			WsNodeDetail=docMgmtImpl.getChildNodesModulewise(workspaceID,nodeId);
			if(nodeId==1)
			{
				WsNodeDetail.removeElementAt(1);
			}
			for(int i=0;i<WsNodeDetail.size();i++){	
				DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i);
				nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
			}
			nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForBulkDeletion(workspaceID, userCode, stageId,nodeId);
			if(ProjectTimeLine.equalsIgnoreCase("yes")){
			docMgmtImpl.RemoveUserRightsfromTimelineForBulkDeletion(workspaceID, userCode, stageId,nodeId);
			}
			Integer userGroupCode;
			DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
			/*
			DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
			
			objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
			objWorkSpaceUserRightsMst.setModifyBy(userId);
			objWorkSpaceUserRightsMst.setUserCode(userCode);
			objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
			objWorkSpaceUserRightsMst.setNodeId(nodeId);
		
			objWorkSpaceUserRightsMst.setCanReadFlag('Y');
			objWorkSpaceUserRightsMst.setCanAddFlag('Y');
			objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
			objWorkSpaceUserRightsMst.setCanEditFlag('Y');
			objWorkSpaceUserRightsMst.setAdvancedRights("Y");
			roleCode= docMgmtImpl.getRoleCode(workspaceID, userCode);
			objWorkSpaceUserRightsMst.setRoleCode(roleCode);
			objWorkSpaceUserRightsMst.setMode(1);
			
			//for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
				DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
				dtoClone.setStageId(stageId);
				userRightsList.add(dtoClone);*/
			
			//}
			for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
				DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
				//for(int k=0;k<selectedUsers.length;k++)
				//{
				   objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
					DTOUserMst objUserMst = docMgmtImpl.getUserInfo(userCode);
					userGroupCode = new Integer(objUserMst.getUserGroupCode());			
					
					objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
					objWorkSpaceUserRightsMst.setModifyBy(userId);
					objWorkSpaceUserRightsMst.setUserCode(userCode);
					objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
					objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
				
					objWorkSpaceUserRightsMst.setCanReadFlag('Y');
					objWorkSpaceUserRightsMst.setCanAddFlag('Y');
					objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
					objWorkSpaceUserRightsMst.setCanEditFlag('Y');
					objWorkSpaceUserRightsMst.setAdvancedRights("Y");
					roleCode= docMgmtImpl.getRoleCode(workspaceID, userCode);
					objWorkSpaceUserRightsMst.setRoleCode(roleCode);
					objWorkSpaceUserRightsMst.setStageId(stageId);
					objWorkSpaceUserRightsMst.setMode(1);
					userRightsList.add(objWorkSpaceUserRightsMst);
					/*for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
						DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
						dtoClone.setStageId(stageIds[istageIds]);
						userRightsList.add(dtoClone);
					}*/
				}
			docMgmtImpl.insertFolderSpecificMultipleUserRightsWithRoleCode(userRightsList);
				
			//}
			
			//docMgmtImpl.insertFolderSpecificMultipleUserRights(userRightsList);
			
			getAllNodeIds = docMgmtImpl.getAllLeafNodeIds(workspaceID);
			
			ArrayList<Integer> nodeIds = new ArrayList();
			{
				for(int k=0;k<getAllNodeIds.size();k++){
					nodeIds.add(getAllNodeIds.get(k).getNodeId());
				}
			}
			boolean showHours=false;
			Vector<DTOWorkSpaceNodeAttribute> getAttrDtl = docMgmtImpl.getTimelineAttrDtl(workspaceID);
			
			if(getAttrDtl.size()>0){
				showHours=true;
			}
			
			if(ProjectTimeLine.equalsIgnoreCase("yes") && showHours==true ){
				boolean skipCurrent=false;
				userRightsListForTimeLine =new ArrayList<DTOWorkSpaceUserRightsMst>();
				Vector<DTOWorkSpaceUserRightsMst> getLastRightsDtl = new Vector<>();
				//int nodeId = userRightsList.get(0).getNodeId();
				//int stageId = userRightsList.get(0).getStageId();
				getLastRightsDtl = docMgmtImpl.getLastRightsRecordDtlForAdjustDate(workspaceID,nodeId,stageId);
				if(getLastRightsDtl.size()<=0){
					getLastRightsDtl = docMgmtImpl.UserOnNodeTimelineTracking(workspaceID,nodeId);
					
				}
				DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
				String attrValues=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
				
				if(getLastRightsDtl.size()<=0 && (attrValues != null && !attrValues.equals(""))){
					skipCurrent=true;
					LocalDateTime startDate=null;
					LocalDateTime endDate = null;
					String s[]=attrValues.split("/");
					startDate=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
					endDate=startDate.plusHours(duration*3);
					DayOfWeek dayOfWeek = endDate.getDayOfWeek();
					//System.out.println(dayOfWeek);
					if(dayOfWeek==DayOfWeek.SUNDAY){
						endDate=endDate.plusHours(24);
					}
					dto.setWorkSpaceId(workspaceID);
					dto.setNodeId(nodeId);
					dto.setStartDate(Timestamp.valueOf(startDate));
					dto.setEndDate(Timestamp.valueOf(endDate));
					dto.setAdjustDate(Timestamp.valueOf(endDate));
					
				}
									
				if(getLastRightsDtl.size()>0){
					int lastIndex = getLastRightsDtl.size()-1;
					dto.setWorkSpaceDesc(getLastRightsDtl.get(lastIndex).getWorkSpaceId());
					dto.setNodeId(getLastRightsDtl.get(lastIndex).getNodeId());
					dto.setUserCode(getLastRightsDtl.get(lastIndex).getUserCode());
					dto.setUserGroupCode(getLastRightsDtl.get(lastIndex).getUserGroupCode());
					dto.setStageId(getLastRightsDtl.get(lastIndex).getStageId());
					dto.setHours(getLastRightsDtl.get(lastIndex).getHours());
					dto.setStartDate(getLastRightsDtl.get(lastIndex).getStartDate());
					dto.setEndDate(getLastRightsDtl.get(lastIndex).getEndDate());
					dto.setAdjustDate(getLastRightsDtl.get(lastIndex).getAdjustDate());
					dto.setModifyOn(getLastRightsDtl.get(lastIndex).getModifyOn());
				}
				
				
				for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
					DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
					if(nodeIds.contains(nodeDetail.getNodeId())){
					
				
						DTOUserMst objUserMst = docMgmtImpl.getUserInfo(userCode);
						userGroupCode = new Integer(objUserMst.getUserGroupCode());
						//DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
						objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
						
						objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
						objWorkSpaceUserRightsMst.setModifyBy(userId);
						objWorkSpaceUserRightsMst.setUserCode(userCode);
						objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
						objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
					
						objWorkSpaceUserRightsMst.setCanReadFlag('Y');
						objWorkSpaceUserRightsMst.setCanAddFlag('Y');
						objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
						objWorkSpaceUserRightsMst.setCanEditFlag('Y');
						objWorkSpaceUserRightsMst.setAdvancedRights("Y");
						objWorkSpaceUserRightsMst.setDuration(duration);
						objWorkSpaceUserRightsMst.setStartDate(dto.getStartDate());
						objWorkSpaceUserRightsMst.setEndDate(dto.getEndDate());
						objWorkSpaceUserRightsMst.setAdjustDate(dto.getAdjustDate());
						objWorkSpaceUserRightsMst.setStageId(dto.getStageId());
						objWorkSpaceUserRightsMst.setMode(1);
						userRightsListForTimeLine.add(objWorkSpaceUserRightsMst);
						/*for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
							DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
							dtoClone.setStageId(stageIds[istageIds]);
							userRightsListForTimeLine.add(dtoClone);
						}*/
					}
					}
			
				docMgmtImpl.AttachUserRightsForTimeLine(userRightsListForTimeLine);
				
				Timestamp signOffDate=dto.getAdjustDate();
				
				getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
				LocalDateTime startDate=null;
				LocalDateTime endDate = null;
				
				if(getSRFlagData.size() >0 && (attrValues != null && !attrValues.equals(""))){				
						int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceID, dto.getNodeId());
						
						getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustHoursUpdate(workspaceID,dto.getNodeId(),parentNodeIdforAdjustDate,dto.getUserCode(),dto.getStageId());
						
						LocalDateTime date=signOffDate.toLocalDateTime();
						int t=0;
						if(skipCurrent){
							t=1;
						}
						for(int k=t;k<getAdjustDateData.size();k++){
							//System.out.println("Hours : "+hoursData.get(k).getHours());
							DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getAdjustDateData
									.get(k);
							int noofhours=dtotimeline.getHours();
							if(startDate==null){
								startDate=date;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}
							else{
								startDate=endDate;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}	
							//dtotimeline.setStartDate(dto.getStartDate());
							//dtotimeline.setEndDate(dto.getEndDate());
							dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
				            
				            
				            docMgmtImpl.updateTimelineAdjustDate(dtotimeline);
						}	
					}
				else{
	
					String attrValue=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
					
					if(attrValue != null && !attrValue.equals("")){
						hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
						
						String s[]=attrValue.split("/");
						
						LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
	
						 startDate=null;
						endDate = null;
						for(int k=0;k<hoursData.size();k++){
							//System.out.println("Hours : "+hoursData.get(k).getHours());
							DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) hoursData
									.get(k);
							int noofhours=dtotimeline.getHours();
							if(startDate==null){
								startDate=date;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}
							else{
								startDate=endDate;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}	
							
							dtotimeline.setStartDate(Timestamp.valueOf(startDate));
						
							dtotimeline.setEndDate(Timestamp.valueOf(endDate));
							dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
							
							docMgmtImpl.updateTimelineDatesValue(dtotimeline);
					}
						}
					}
			}
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(workspaceID);
			objWSUserRightsMstforModuleHistory.setNodeId(nodeId);
			objWSUserRightsMstforModuleHistory.setModifyBy(userId);
			
			String stageDesc="";
			String stage;
			int stageid;
				//for(int i=0;i<stageIds.length;i++){
					//stageid=stageIds[i];
					stage= docMgmtImpl.getStageDesc(stageId);
					//stageDesc+=stage+",";
				//}
			System.out.println("StageId="+stage);
			  if (stageDesc != null && stageDesc.length() > 0){
				  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
			  }
			  objWSUserRightsMstforModuleHistory.setStages(stageDesc);
			  objWSUserRightsMstforModuleHistory.setMode(1);
			  
			//for(int i=0;i<selectedUsers.length;i++)
			//{
				/*objUserMst = docMgmtImpl.getUserInfo(userCode);
				System.out.println(objUserMst.getUserGroupCode());
				userGroupCode = new Integer(objUserMst.getUserGroupCode());
				objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
				
				objWSUserRightsMstforModuleHistory.setUserCode(userCode);
				 roleCode= docMgmtImpl.getRoleCode(workspaceID, userCode);
				  objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
				//docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
				docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);*/
			  
			  DTOUserMst objUserMst = docMgmtImpl.getUserInfo(userCode);
				System.out.println(objUserMst.getUserGroupCode());
				userGroupCode = new Integer(objUserMst.getUserGroupCode());
				objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
				
				objWSUserRightsMstforModuleHistory.setUserCode(userCode);
				 roleCode= docMgmtImpl.getRoleCode(workspaceID, userCode);
				  objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
				docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
				//updateProjectPlanningHoursForBulkuser(workspaceID,nodeId,userCode,userGrpCode,stageId,duration);
				//}
		}
	}
	htmlContent="true";
	redirectAction = "BulkUserAllocation.do?workSpaceId="+workSpaceId;
	return "html";			
	}




/*public String BulkUserinsertStages() throws ParseException, MalformedURLException
{
	
	Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();
	ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
	ArrayList<DTOWorkSpaceUserRightsMst> userRightsListForTimeLine = new ArrayList<DTOWorkSpaceUserRightsMst>();
	
	DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
	String roleCode;
	String workspaceID=workSpaceId;//ActionContext.getContext().getSession().get("ws_id").toString();
	int userGrpCode=docMgmtImpl.getUserByCode(userCode).getUserGroupCode();
	int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	
	
	
	ProjectTimeLine = knetProperties.getValue("ProjectTimeLine");
	
		
	String[] selectValuesArray = temp.split(",");
	if(selectValuesArray.length==0){
		return "false";
	}
	else{
		
		for(int x = 0; x<selectValuesArray.length; x++) 
		{
			userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
			int nodeId= Integer.parseInt(selectValuesArray[x].split("_")[0].toString());
			int stageId= Integer.parseInt(selectValuesArray[x].split("_")[1].toString());
			duration = Integer.parseInt(selectValuesArray[x].split("_")[2].toString());
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForBulkDeletion(workspaceID, userCode, stageId,nodeId);
			if(ProjectTimeLine.equalsIgnoreCase("yes")){
			docMgmtImpl.RemoveUserRightsfromTimelineForBulkDeletion(workspaceID, userCode, stageId,nodeId);
			}
		
			DTOUserMst objUserMst = docMgmtImpl.getUserInfo(userCode);
			Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
			DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
			
			objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
			objWorkSpaceUserRightsMst.setModifyBy(userId);
			objWorkSpaceUserRightsMst.setUserCode(userCode);
			objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
			objWorkSpaceUserRightsMst.setNodeId(nodeId);
		
			objWorkSpaceUserRightsMst.setCanReadFlag('Y');
			objWorkSpaceUserRightsMst.setCanAddFlag('Y');
			objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
			objWorkSpaceUserRightsMst.setCanEditFlag('Y');
			objWorkSpaceUserRightsMst.setAdvancedRights("Y");
			roleCode= docMgmtImpl.getRoleCode(workspaceID, userCode);
			objWorkSpaceUserRightsMst.setRoleCode(roleCode);
			objWorkSpaceUserRightsMst.setMode(1);
			
			//for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
				DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
				dtoClone.setStageId(stageId);
				userRightsList.add(dtoClone);
			//}
			
			//docMgmtImpl.insertFolderSpecificMultipleUserRights(userRightsList);
			docMgmtImpl.insertFolderSpecificMultipleUserRightsWithRoleCode(userRightsList);
			getAllNodeIds = docMgmtImpl.getAllLeafNodeIds(workspaceID);
			
			ArrayList<Integer> nodeIds = new ArrayList();
			{
				for(int k=0;k<getAllNodeIds.size();k++){
					nodeIds.add(getAllNodeIds.get(k).getNodeId());
				}
			}
			boolean showHours=false;
			Vector<DTOWorkSpaceNodeAttribute> getAttrDtl = docMgmtImpl.getTimelineAttrDtl(workspaceID);
			
			if(getAttrDtl.size()>0){
				showHours=true;
			}
			
			if(ProjectTimeLine.equalsIgnoreCase("yes") && showHours==true ){
				boolean skipCurrent=false;
				userRightsListForTimeLine =new ArrayList<DTOWorkSpaceUserRightsMst>();
				Vector<DTOWorkSpaceUserRightsMst> getLastRightsDtl = new Vector<>();
				//int nodeId = userRightsList.get(0).getNodeId();
				//int stageId = userRightsList.get(0).getStageId();
				getLastRightsDtl = docMgmtImpl.getLastRightsRecordDtlForAdjustDate(workspaceID,nodeId,stageId);
				if(getLastRightsDtl.size()<=0){
					getLastRightsDtl = docMgmtImpl.UserOnNodeTimelineTracking(workspaceID,nodeId);
					
				}
				DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
				String attrValues=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
				
				if(getLastRightsDtl.size()<=0 && (attrValues != null && !attrValues.equals(""))){
					skipCurrent=true;
					LocalDateTime startDate=null;
					LocalDateTime endDate = null;
					String s[]=attrValues.split("/");
					startDate=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
					endDate=startDate.plusHours(duration*3);
					DayOfWeek dayOfWeek = endDate.getDayOfWeek();
					//System.out.println(dayOfWeek);
					if(dayOfWeek==DayOfWeek.SUNDAY){
						endDate=endDate.plusHours(24);
					}
					dto.setWorkSpaceId(workspaceID);
					dto.setNodeId(nodeId);
					dto.setStartDate(Timestamp.valueOf(startDate));
					dto.setEndDate(Timestamp.valueOf(endDate));
					dto.setAdjustDate(Timestamp.valueOf(endDate));
					
				}
									
				if(getLastRightsDtl.size()>0){
					int lastIndex = getLastRightsDtl.size()-1;
					dto.setWorkSpaceDesc(getLastRightsDtl.get(lastIndex).getWorkSpaceId());
					dto.setNodeId(getLastRightsDtl.get(lastIndex).getNodeId());
					dto.setUserCode(getLastRightsDtl.get(lastIndex).getUserCode());
					dto.setUserGroupCode(getLastRightsDtl.get(lastIndex).getUserGroupCode());
					dto.setStageId(getLastRightsDtl.get(lastIndex).getStageId());
					dto.setHours(getLastRightsDtl.get(lastIndex).getHours());
					dto.setStartDate(getLastRightsDtl.get(lastIndex).getStartDate());
					dto.setEndDate(getLastRightsDtl.get(lastIndex).getEndDate());
					dto.setAdjustDate(getLastRightsDtl.get(lastIndex).getAdjustDate());
					dto.setModifyOn(getLastRightsDtl.get(lastIndex).getModifyOn());
				}
				
				
				WsNodeDetail=docMgmtImpl.getChildNodesModulewise(workspaceID,nodeId);
				for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
					DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
					if(nodeIds.contains(nodeDetail.getNodeId())){
					
						objUserMst = docMgmtImpl.getUserInfo(userCode);
						userGroupCode = new Integer(objUserMst.getUserGroupCode());
						objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
						
						objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
						objWorkSpaceUserRightsMst.setModifyBy(userId);
						objWorkSpaceUserRightsMst.setUserCode(userCode);
						objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
						objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
					
						objWorkSpaceUserRightsMst.setCanReadFlag('Y');
						objWorkSpaceUserRightsMst.setCanAddFlag('Y');
						objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
						objWorkSpaceUserRightsMst.setCanEditFlag('Y');
						objWorkSpaceUserRightsMst.setAdvancedRights("Y");
						objWorkSpaceUserRightsMst.setDuration(duration);
						objWorkSpaceUserRightsMst.setStartDate(dto.getStartDate());
						objWorkSpaceUserRightsMst.setEndDate(dto.getEndDate());
						objWorkSpaceUserRightsMst.setAdjustDate(dto.getAdjustDate());
						objWorkSpaceUserRightsMst.setMode(1);
						
						//for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
							dtoClone = objWorkSpaceUserRightsMst.clone();
							dtoClone.setStageId(stageId);
							userRightsListForTimeLine.add(dtoClone);
						//}
					
					}
				}
			
				docMgmtImpl.AttachUserRightsForTimeLine(userRightsListForTimeLine);
				
				Timestamp signOffDate=dto.getAdjustDate();
				
				getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
				LocalDateTime startDate=null;
				LocalDateTime endDate = null;
				
				if(getSRFlagData.size() >0 && (attrValues != null && !attrValues.equals(""))){				
						int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workspaceID, dto.getNodeId());
						
						getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustHoursUpdate(workspaceID,dto.getNodeId(),parentNodeIdforAdjustDate,dto.getUserCode(),dto.getStageId());
						
						LocalDateTime date=signOffDate.toLocalDateTime();
						int t=0;
						if(skipCurrent){
							t=1;
						}
						for(int k=t;k<getAdjustDateData.size();k++){
							//System.out.println("Hours : "+hoursData.get(k).getHours());
							DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getAdjustDateData
									.get(k);
							int noofhours=dtotimeline.getHours();
							if(startDate==null){
								startDate=date;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}
							else{
								startDate=endDate;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}	
							//dtotimeline.setStartDate(dto.getStartDate());
							//dtotimeline.setEndDate(dto.getEndDate());
							dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
				            
				            
				            docMgmtImpl.updateTimelineAdjustDate(dtotimeline);
						}	
					}
				else{
	
					String attrValue=docMgmtImpl.getAttributeDetailByAttrName(workspaceID,1,"Project Start Date").getAttrValue();
					
					if(attrValue != null && !attrValue.equals("")){
						hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
						
						String s[]=attrValue.split("/");
						
						LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
	
						 startDate=null;
						endDate = null;
						for(int k=0;k<hoursData.size();k++){
							//System.out.println("Hours : "+hoursData.get(k).getHours());
							DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) hoursData
									.get(k);
							int noofhours=dtotimeline.getHours();
							if(startDate==null){
								startDate=date;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}
							else{
								startDate=endDate;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}	
							
							dtotimeline.setStartDate(Timestamp.valueOf(startDate));
						
							dtotimeline.setEndDate(Timestamp.valueOf(endDate));
							dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
							
							docMgmtImpl.updateTimelineDatesValue(dtotimeline);
					}
						}
					}
			}
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(workspaceID);
			objWSUserRightsMstforModuleHistory.setNodeId(nodeId);
			objWSUserRightsMstforModuleHistory.setModifyBy(userId);
			
			String stageDesc="";
			String stage;
			int stageid;
				//for(int i=0;i<stageIds.length;i++){
					//stageid=stageIds[i];
					stage= docMgmtImpl.getStageDesc(stageId);
					//stageDesc+=stage+",";
				//}
			System.out.println("StageId="+stage);
			  if (stageDesc != null && stageDesc.length() > 0){
				  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
			  }
			  objWSUserRightsMstforModuleHistory.setStages(stageDesc);
			  objWSUserRightsMstforModuleHistory.setMode(1);
			  
			//for(int i=0;i<selectedUsers.length;i++)
			//{
				objUserMst = docMgmtImpl.getUserInfo(userCode);
				System.out.println(objUserMst.getUserGroupCode());
				userGroupCode = new Integer(objUserMst.getUserGroupCode());
				objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
				
				objWSUserRightsMstforModuleHistory.setUserCode(userCode);
				 roleCode= docMgmtImpl.getRoleCode(workspaceID, userCode);
				  objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
				//docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
				docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
				updateProjectPlanningHoursForBulkuser(workspaceID,nodeId,userCode,userGrpCode,stageId,duration);
				}//}
		}
	htmlContent="true";
	redirectAction = "BulkUserAllocation.do?workSpaceId="+workSpaceId;
	return "html";			
	}*/
public String updateProjectPlanningHoursForBulkuser(String wsId,int nodeId,int userCode,int userGrpCode,int stageId,int hours){
	
	getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(wsId);
	
	
	if(getSRFlagData.size()>0){
		
		getdtl=docMgmtImpl.getTimeLineRightsMstdtl(wsId,nodeId,userCode,userGrpCode,stageId);
		
		int oldHrs=getdtl.getHours();
		Timestamp attrValued=getdtl.getAdjustDate();
		
		//String attrVals=attrValued.toLocalDateTime().toLocalDate().toString();
		//attrVals=attrVals.replace("-", "/");
		
		//String olds[]=attrVals.split("/");
			
		if(hours>oldHrs){
			//LocalDateTime olddate=LocalDateTime.of(LocalDate.of(Integer.parseInt(olds[0]),Integer.parseInt(olds[1]),Integer.parseInt(olds[2])), LocalTime.of(0,0));
			LocalDateTime olddate=attrValued.toLocalDateTime();
			LocalDateTime newdate;
			int hrs=hours-oldHrs;
			newdate=olddate.plusHours(hrs*3);
			DayOfWeek dayOfWeek = newdate.getDayOfWeek();
			//System.out.println(dayOfWeek);
			if(dayOfWeek==DayOfWeek.SUNDAY){
				newdate=newdate.plusHours(24);
			}
			Timestamp newAdjustDate=Timestamp.valueOf(newdate);
			getdtl.setHours(hours);
			getdtl.setAdjustDate(newAdjustDate);
			docMgmtImpl.updateTimelineHoursAdjustDate(getdtl);
		}
		
		if(hours<oldHrs){
			//LocalDateTime olddate=LocalDateTime.of(LocalDate.of(Integer.parseInt(olds[0]),Integer.parseInt(olds[1]),Integer.parseInt(olds[2])), LocalTime.of(23,0));
			LocalDateTime olddate=attrValued.toLocalDateTime();
			LocalDateTime newdate;
			int hrs=oldHrs-hours;
			newdate=olddate.minusHours(hrs*3);
			DayOfWeek dayOfWeek = newdate.getDayOfWeek();
			//System.out.println(dayOfWeek);
			if(dayOfWeek==DayOfWeek.SUNDAY){
				newdate=newdate.minusHours(24);
			}
			Timestamp newAdjustDate=Timestamp.valueOf(newdate);
			getdtl.setHours(hours);
			getdtl.setAdjustDate(newAdjustDate);
			docMgmtImpl.updateTimelineHoursAdjustDate(getdtl);
		}
		Timestamp attrValue=docMgmtImpl.getTimeLineRightsMst(wsId,nodeId,userCode,userGrpCode,stageId).get(0).getAdjustDate();
		
		//String attrVal=attrValue.toLocalDateTime().toLocalDate().toString();
		//attrVal=attrVal.replace("-", "/");
		int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(wsId, nodeId);
		getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustHoursUpdate(wsId,nodeId,parentNodeIdforAdjustDate,userCode,stageId);
		
		//String s[]=attrVal.split("/");
		
		//LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
		LocalDateTime date=attrValue.toLocalDateTime();
		LocalDateTime startDate=null;
		LocalDateTime endDate = null;
		for(int k=0;k<getAdjustDateData.size();k++){
			//System.out.println("Hours : "+hoursData.get(k).getHours());
			DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getAdjustDateData
					.get(k);
			int noofhours=dtotimeline.getHours();
			if(startDate==null){
				startDate=date;
				endDate=startDate.plusHours(noofhours*3);
				DayOfWeek dayOfWeek = endDate.getDayOfWeek();
				//System.out.println(dayOfWeek);
				if(dayOfWeek==DayOfWeek.SUNDAY){
					endDate=endDate.plusHours(24);
				}
			}
			else{
				startDate=endDate;
				endDate=startDate.plusHours(noofhours*3);
				DayOfWeek dayOfWeek = endDate.getDayOfWeek();
				//System.out.println(dayOfWeek);
				if(dayOfWeek==DayOfWeek.SUNDAY){
					endDate=endDate.plusHours(24);
				}
			}	
			//dtotimeline.setStartDate(Timestamp.valueOf(startDate));
			
			//dtotimeline.setEndDate(Timestamp.valueOf(endDate));
			dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
			
			docMgmtImpl.updateTimelineDatesValue(dtotimeline);
		}	
	
	}else{
		
		docMgmtImpl.UpdateHoursInTimeWorkspaceRightsMst(wsId,nodeId,userCode,userGrpCode,hours,stageId);
		String attrValue=docMgmtImpl.getAttributeDetailByAttrName(wsId,1,"Project Start Date").getAttrValue();
		
		if(attrValue != null && !attrValue.equals("")){
			hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(wsId);
			
			String s[]=attrValue.split("/");
			
			LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));

			LocalDateTime startDate=null;
			LocalDateTime endDate = null;
			for(int k=0;k<hoursData.size();k++){
				//System.out.println("Hours : "+hoursData.get(k).getHours());
				DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) hoursData
						.get(k);
				int noofhours=dtotimeline.getHours();
				if(startDate==null){
					startDate=date;
					endDate=startDate.plusHours(noofhours*3);
					DayOfWeek dayOfWeek = endDate.getDayOfWeek();
					//System.out.println(dayOfWeek);
					if(dayOfWeek==DayOfWeek.SUNDAY){
						endDate=endDate.plusHours(24);
					}
				}
				else{
					startDate=endDate;
					endDate=startDate.plusHours(noofhours*3);
					DayOfWeek dayOfWeek = endDate.getDayOfWeek();
					//System.out.println(dayOfWeek);
					if(dayOfWeek==DayOfWeek.SUNDAY){
						endDate=endDate.plusHours(24);
					}
				}	
				
				dtotimeline.setStartDate(Timestamp.valueOf(startDate));
			
				dtotimeline.setEndDate(Timestamp.valueOf(endDate));
				dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
				
				docMgmtImpl.updateTimelineDatesValue(dtotimeline);
				
			}	
		}
	}
	
	
	return SUCCESS;
}
	public Vector moduleUserDetailHistory;

	public String lbl_folderName;
	public String lbl_nodeName;
	public int nodeId;
	public String nodeName;
	public String[] multiCheckUser;
	public String rights;
	public int [] stageIds;
	public String buttonName;
	
		
}
