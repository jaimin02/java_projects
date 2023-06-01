package com.docmgmt.struts.actions.reports;

import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CompletePendingWorksAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String OpenFileAndSignOff;
	int nIdForHc,stageIdForHc;
	
	@Override
	public String execute() throws MalformedURLException
	{
		int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
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
				DTOWorkSpaceNodeHistory dtownd = docMgmtImpl.getWorkspaceNodeHistorybyTranNo(workSpaceId, nodeId,MaxtranNo);
				dtownd.setWorkSpaceId(workSpaceId);
				dtownd.setNodeId(nodeId);
				dtownd.setTranNo(tranNo);
				dtownd.setModifyBy(usercode);
				dtownd.setStageId(Integer.parseInt(nodeAndStatus[1]));
				
				String roleCode = docMgmtImpl.getRoleCode(workSpaceId, usercode);
				dtownd.setRoleCode(roleCode);
				dtownd.setFileSize(dtownd.getFileSize());
				
				//docMgmtImpl.insertNodeHistory(dtownd);	
				docMgmtImpl.insertNodeHistoryWithRoleCode(dtownd);
				if(eSign.equalsIgnoreCase("Y")){
					Vector <DTOWorkSpaceNodeHistory> dtoHistory;
					dtoHistory =docMgmtImpl.getUserSignatureDetail(usercode);
					if(dtoHistory.size()>0){
						String uuId = dtoHistory.get(0).getUuId();
						int signtrNo = dtoHistory.get(0).getSignTranNo();
						docMgmtImpl.UpdateNodeHistoryForESign(workSpaceId,nodeId,uuId,signtrNo);
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
				}
				
				
				
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
		
			//Mail on cycle completion
			//get assigned users
			Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForSingleDoc(workSpaceId, nodeId);
							
					//get users who signed
			Vector<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl.getProjectSignOffHistory(workSpaceId, nodeId);
					
			if(autoMail.equalsIgnoreCase("Yes") && getUserRightsDetail.size()==tempHistory.size()){
				StageWiseMailReport stageWiseMail = new StageWiseMailReport();
				//stageWiseMail.StageWiseMail(workspaceID,nodeId,"Reviewed",100);
				stageWiseMail.FinishDocMailNewFormate(workSpaceId, nodeId,"Finished");
				stageWiseMail.completionMailNewFormate(workSpaceId,nodeId,"Created",10);
				System.out.println("Mail Sent....");
			}
			
		//BlockChain HashCode
			String hashCode=docMgmtImpl.generateHashCode(workSpaceId,nIdForHc,stageIdForHc);
		//BlockChain HashCode Ends
		}
		htmlContent="true";
		redirectAction = "ShowPendingWorks.do?workSpaceId="+workSpaceId;
		return SUCCESS;
	}
	public String autoMail;
	public boolean getCompletedNodeStageDetail;
	public String htmlContent;
	public String workSpaceId;
	public String temp;
	public String[] selectedNodeId;
	public Vector getNextStageId ;
	public String selectValues;
	public String redirectAction;
	public String targetDivId;
	public String eSign="N";
	
	public String getSelectValues() {
		return selectValues;
	}
	public void setSelectValues(String selectValues) {
		this.selectValues = selectValues;
	}
	public Vector getGetNextStageId() {
		return getNextStageId;
	}
	public void setGetNextStageId(Vector getNextStageId) {
		this.getNextStageId = getNextStageId;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public String[] getSelectedNodeId() {
		return selectedNodeId;
	}
	public void setSelectedNodeId(String[] selectedNodeId) {
		this.selectedNodeId = selectedNodeId;
	}
}
	
	

