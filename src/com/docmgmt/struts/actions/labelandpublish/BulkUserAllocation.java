package com.docmgmt.struts.actions.labelandpublish;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Vector;

import com.docmgmt.dto.DTOLocationMst;
import com.docmgmt.dto.DTORoleMst;
import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOUserTypeMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.struts.actions.reports.StageWiseMailReport;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BulkUserAllocation extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public Vector<DTOWorkSpaceUserRightsMst> userdata=new Vector<DTOWorkSpaceUserRightsMst>();
	private Vector usertypelist=new Vector();
	private String currentUserType;
	public boolean limitOk=true;
	public boolean activateOk=true;
	public String message;	
	public boolean blockFlag;
	public boolean delFlag;
	public String htmlContent;
	public String loginName;
	public String loginId;
	public Vector<DTOLocationMst> locationDtl=new Vector<DTOLocationMst>();
	public Vector<DTORoleMst> roleDtl=new Vector<DTORoleMst>();
	public String html;
	public String userName;
	public int userCode;
	public String OpenFileAndSignOff;
	int nIdForHc,stageIdForHc;
	public Vector<DTOWorkSpaceUserMst> wsList;

	public String lbl_folderName;
	public String lbl_nodeName;
	public String lbl_nodeDisplayName;
	public String workSpaceId;
	public String projectName;
	public String projectCode;
	public String buttonName;
	public int nodeid= 1;
	public Vector<DTOStageMst> getStageDetail;
	
	@Override
	public String execute()
	{
		
		DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetailWSList(workSpaceId);
		projectName = wsDetail.getWorkSpaceDesc();
		
		int usergrpcode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		
		projectCode = wsDetail.getProjectCode();
		currentUserType = (String)ActionContext.getContext().getSession().get("usertypename");
		
		userdata= docMgmtImpl.UserDataForBulkAllocation(workSpaceId);
		Vector<DTOWorkSpaceUserRightsMst> temp=new Vector<DTOWorkSpaceUserRightsMst>();
		
		getStageDetail = docMgmtImpl.getStageDetailCSV();
		
						
		usertypelist=docMgmtImpl.getAllUserType();
		locationDtl = docMgmtImpl.getLocationDtl();
		roleDtl = docMgmtImpl.getRoleDtl();
		
		
		// Added on 14-5-2015 by dharmendra jadav 

		// Start coding
		for (int index = 0; index < usertypelist.size(); index++) {

			DTOUserTypeMst userTypeDtl = (DTOUserTypeMst) usertypelist
					.get(index);

			if (currentUserType.equalsIgnoreCase("WA")) {
				
				if (userTypeDtl.getUserTypeName().equalsIgnoreCase("PU")) {

					usertypelist.removeElement(userTypeDtl);
				}
			}

			if (currentUserType.equalsIgnoreCase("PU")) {
				if (userTypeDtl.getUserTypeName().equalsIgnoreCase("PU")) {

					usertypelist.removeElement(userTypeDtl);
				}
			}
		}

		// End coding
		
		return SUCCESS;
	}

	
	/*public String getUserwiseProjectInfoDetail(){
		
		wsList = new Vector<DTOWorkSpaceUserMst>();
		wsList = docMgmtImpl.getUserWiseWS(String.valueOf(userCode));
		htmlContent="";
		for (DTOWorkSpaceUserMst workspaceList : wsList) {
			if(!htmlContent.equals("")){
				htmlContent += "&"; 
			}
			htmlContent += workspaceList.getWorkSpaceId()+"::"+workspaceList.getWorkspacedesc();
		}
		
		return "html";
	}*/
	
	public String getUserAllocationOnDocuments(){
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		lbl_folderName = knetProperties.getValue("ForlderName");
		lbl_nodeName = knetProperties.getValue("NodeName");	
		lbl_nodeDisplayName = knetProperties.getValue("NodeDisplayName");	
		DTOUserMst userMst = docMgmtImpl.getUserInfo(userCode);
		getUserAllocationDetails = new Vector<DTOWorkSpaceUserRightsMst>();
		getUserAllocationDetails = docMgmtImpl.getUserAllocationDetails(userCode, workSpaceId);
		//getStageDetail = docMgmtImpl.getStageDetailCSV();
		return SUCCESS;
	}
	
	public String BulkuserAssignData(){
		int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int usergrpcode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		Vector<DTOWorkSpaceUserRightsMst> getAdjustDateData=new Vector<>();
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		autoMail = propertyInfo.getValue("AutoMail");
		OpenFileAndSignOff = propertyInfo.getValue("OpenFileAndSignOff");

		System.out.println("workSpaceId:"+workSpaceId+",selectValues:"+temp+",targetDivId="+targetDivId);
		String[] selectValuesArray = temp.split(",");
		for(int i = 0; i<selectValuesArray.length; i++) {			
			
			int nodeId = Integer.parseInt(selectValuesArray[i].split("_")[0].toString());
			nIdForHc = Integer.parseInt(selectValuesArray[i].split("_")[0].toString());
			System.out.println("selectedNodeId->"+nodeId +":::"+"selectValuesArray->"+selectValuesArray[i]);
			
			int stageId = docMgmtImpl.getStageIdfromWSHistory(workSpaceId,nodeId);
			System.out.println("stageId="+stageId);
			if(stageId==0){
				htmlContent="sendback";
				return "html";
			}else{
			
			
			String[] nodeAndStatus = selectValuesArray[i].split("_");
			System.out.println("stageid "+Integer.parseInt(nodeAndStatus[1]));
			stageIdForHc = Integer.parseInt(nodeAndStatus[1]);
			if(Integer.parseInt(nodeAndStatus[0]) == nodeId)
			{
				int MaxtranNo = docMgmtImpl.getMaxTranNo(workSpaceId, nodeId);
				int tranNo=docMgmtImpl.getNewTranNo(workSpaceId);
				/*DTOWorkSpaceNodeHistory dtownd = docMgmtImpl.getWorkspaceNodeHistorybyTranNo(workSpaceId, nodeId,MaxtranNo);
				dtownd.setWorkSpaceId(workSpaceId);
				dtownd.setNodeId(nodeId);
				dtownd.setTranNo(tranNo);
				dtownd.setModifyBy(usercode);
				dtownd.setStageId(Integer.parseInt(nodeAndStatus[1]));*/
				
				String roleCode = docMgmtImpl.getRoleCode(workSpaceId, usercode);
				/*dtownd.setRoleCode(roleCode);
				dtownd.setFileSize(dtownd.getFileSize());
				
				//docMgmtImpl.insertNodeHistory(dtownd);	
				docMgmtImpl.insertNodeHistoryWithRoleCode(dtownd);
				if(eSign.equalsIgnoreCase("Y")){
					Vector <DTOWorkSpaceNodeHistory> dtoHistory;
					dtoHistory =docMgmtImpl.getUserSignatureDetail(usercode);
					if(dtoHistory.size()>0){
						String uuId = dtoHistory.get(0).getUuId();
						docMgmtImpl.UpdateNodeHistoryForESign(workSpaceId,nodeId,uuId);
						roleCode = docMgmtImpl.getRoleCode(workSpaceId, usercode);
						docMgmtImpl.UpdateNodeHistoryForRoleCode(workSpaceId,nodeId,roleCode);
						if(OpenFileAndSignOff.equalsIgnoreCase("Yes")){
						DTOWorkSpaceNodeHistory dto1 = new DTOWorkSpaceNodeHistory();
						dto1 = docMgmtImpl.getNodeHistoryForSignOff(workSpaceId,nodeId);
						dto1.setWorkSpaceId(workSpaceId);
						dto1.setNodeId(nodeId);
						dto1.setTranNo(0);
						dto1.setFileName(dto1.getFileName());
						dto1.setFolderName(dto1.getFolderName());
						dto1.setRemark("");
						dto1.setModifyBy(usercode);
						dto1.setStatusIndi('D');
						dto1.setMode(2);
						docMgmtImpl.insertIntofileopenforsign(dto1);
						}
					}			
				}*/
				
				
				
				docMgmtImpl.insertAssignedNodeRight(workSpaceId,nodeId,tranNo,Integer.parseInt(nodeAndStatus[1]),usercode,1,"");
				docMgmtImpl.UpdateTranNoForStageInAttrHistory(workSpaceId, nodeId, tranNo);
				
				//Code to update Adjust Date
				Timestamp attrValue=docMgmtImpl.getAssignNodeRightsData(workSpaceId,nodeId,Integer.parseInt(nodeAndStatus[1])).get(0).getModifyOn();
				String attrValues=docMgmtImpl.getAttributeDetailByAttrName(workSpaceId,1,"Project Start Date").getAttrValue();
				
				if(attrValues != null && !attrValues.equals("")){
						//hoursData=docMgmtImpl.getProjectTimelineDetailsForHours(workspaceID);
						//getSRFlagData=docMgmtImpl.getProjectTimelineSRFlagData(workspaceID);
						
						int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workSpaceId, nodeId);
						getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustHoursUpdate(workSpaceId,nodeId,parentNodeIdforAdjustDate,usercode,Integer.parseInt(nodeAndStatus[1]));
						
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
				            dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
							docMgmtImpl.updateTimelineAdjustDate(dtotimeline);
						}	
					}
				
				if(autoMail.equalsIgnoreCase("Yes") && Integer.parseInt(nodeAndStatus[1])==20){
					getCompletedNodeStageDetail = docMgmtImpl.getCompletedNodeStageDetail(workSpaceId,nodeId,Integer.parseInt(nodeAndStatus[1]));
					
					if(getCompletedNodeStageDetail==true){
						 StageWiseMailReport stageWiseMail = new StageWiseMailReport();
						 //stageWiseMail.StageWiseMail(workSpaceId,nodeId,"Reviewed",100);
						 stageWiseMail.StageWiseMailNewFormate(workSpaceId,nodeId,"Reviewed",100);
						 System.out.println("Mail Sent....");
					}
					
				}
			}
			
		}

		//BlockChain HashCode
			/*String hashCode=docMgmtImpl.generateHashCode(workSpaceId,nIdForHc,stageIdForHc);*/
		//BlockChain HashCode Ends
		}
		htmlContent="true";
		redirectAction = "ShowBulkuserAllocation_ex.do?workSpaceId="+workSpaceId;
		return SUCCESS;
	}
	
	public String autoMail;
	public boolean getCompletedNodeStageDetail;
	public String temp;
	public String[] selectedNodeId;
	public Vector getNextStageId ;
	public String selectValues;
	public String redirectAction;
	public String targetDivId;
	public String eSign="N";
	public Vector<DTOWorkSpaceUserRightsMst> getUserAllocationDetails;
	
	public Vector<DTOWorkSpaceUserRightsMst> getUserdata() {
		return userdata;
	}


	public void setUserdata(Vector<DTOWorkSpaceUserRightsMst> userdata) {
		this.userdata = userdata;
	}


	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Vector<DTOWorkSpaceUserRightsMst> getUserForProjectDeatil;
	
	public Vector<DTOWorkSpaceUserRightsMst> getGetFailScriptDeatil() {
		return getUserForProjectDeatil;
	}
	public void setGetFailScriptDeatil(Vector<DTOWorkSpaceUserRightsMst> getFailScriptDeatil) {
		this.getUserForProjectDeatil = getFailScriptDeatil;
	}
	
	public String getHtmlContent() {
		return htmlContent;
	}
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public Vector<DTOWorkSpaceUserMst> getWsList() {
		return wsList;
	}
	public void setWsList(Vector<DTOWorkSpaceUserMst> wsList) {
		this.wsList = wsList;
	}
}


