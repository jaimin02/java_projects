package com.docmgmt.struts.actions.workspace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ProjectDocStatusActionForCSV extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String htmlContent="";
	public String workspaceId;
	public String htmlContentAll="";
	public Vector<DTOWorkSpaceMst> getWorkspaceDetail;
	public String m;
	public boolean allFileApproved = false;
	Vector<Integer> nodeids = new Vector<Integer>();
	/*Vector<DTOWorkSpaceNodeHistory> docDetails;*/
	
	@Override
	public String execute()
	{
		boolean allWs = false;
		
		/*int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());*/
		
		ArrayList<String> workspaceIds= new ArrayList<String>();
		/*if (workspaceId == null || workspaceId.trim().equalsIgnoreCase("0"))
		{
			int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
			
			getWorkspaceDetail = docMgmtImpl.getUserWorkspace(userGroupCode, userId);
			
			for (int itrWsIds = 0; itrWsIds < getWorkspaceDetail.size(); itrWsIds++) 
			{
				DTOWorkSpaceMst dtoWsMst = new DTOWorkSpaceMst();
				 dtoWsMst = getWorkspaceDetail.get(itrWsIds);
				if (dtoWsMst.getLastPublishedVersion().trim().equalsIgnoreCase("-999")) {
					workspaceIds.add(dtoWsMst.getWorkSpaceId());
				}
				
			}
			
			allWs=true;
		}
		else*/
		workspaceId="0033";
		workspaceIds.add(workspaceId);
		int[] nodeIds =null;			
		Integer totalLeafCount =  docMgmtImpl.getTotalLeafNodes(workspaceIds);
		
				
		
			
		/*PdfPublishTreeBean bean = new PdfPublishTreeBean();
		
		docDetails = bean
				.generatePdfTree(workspaceId, userGroupCode, userId, 'N');*/
		
		
		//ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistory = docMgmtImpl.getAllNodesLastHistoryForPDFPublish(workspaceIds, nodeIds);
		ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistory = docMgmtImpl.getAllNodesLastHistoryForPDFPublishForCSV(workspaceId);
		int leafCount = totalLeafCount;
		int historyCount = wsNodeHistory.size();
		float leafNodesWithDocPer= ((float)historyCount/leafCount)*100;
		float rem = 100.00f - leafNodesWithDocPer; 
		float leafNodesWithDocCnt = leafCount - historyCount;
		HashMap<Integer, Integer> stageMap = new HashMap<Integer, Integer>();
		
		htmlContent+= "TotalNodes"+"="+leafCount+"#"+"Nodeswithfiles"+"="+historyCount+","+"PerLeafNodes"+"="+String.format("%.02f",leafNodesWithDocPer)+"#";
		htmlContentAll+= "["  +"'" + "Empty" + "'" +"," +String.format("%.02f",rem) +"," +leafNodesWithDocCnt +"]" +",";
		//"[" +"'" + "TotalNodes" + "'" +"," +leafCount +"]" +"," +"
		Vector<DTOStageMst> allStages = docMgmtImpl.getStageDetail();
		ArrayList<Integer> assignedStageIds = docMgmtImpl.getStageIdsRightsWise(workspaceId,1);
		
		boolean Authorized=false;
		if(assignedStageIds.contains(30)){
			Authorized=true;
		}
		if(Authorized==false){
			for(int k=0;k<allStages.size();k++){
				int temp = allStages.get(k).getStageId();
				if(temp==30){
					allStages.remove(allStages.get(k));
				}		
			}	
		}	
		//fill hashmap with stage count according to unique stage IDs
		for (DTOStageMst dtoStageMst : allStages) {
			int stgCount=0;
			for (int j = 0; j < wsNodeHistory.size(); j++) {				
				if(wsNodeHistory.get(j).getStageId()==dtoStageMst.getStageId())
				{
					stgCount++;	
				}				
			}
			/*if(dtoStageMst.getStageId() == 100){
				if(totalLeafCount == stgCount){
					System.out.println("Hello");
					allFileApproved = true;
				}
			}*/
			stageMap.put(dtoStageMst.getStageId(), stgCount);
		}	
		
		/*
		 * TODO: to set max or last stage value and its percentage details in htmlContent
		 */
	
		//set stage wise details in htmlContent
		Integer[] keyArray = new Integer[stageMap.keySet().size()] ;
		keyArray = stageMap.keySet().toArray(keyArray);
		Arrays.sort(keyArray);
		for (int itrKey = 0; itrKey < keyArray.length; itrKey++) {
		  Integer key = keyArray[itrKey];
		  for(int i=0;i<allStages.size();i++)
		  {
		  	DTOStageMst dtoStageMst = allStages.get(i);
	    	  if(key.equals(dtoStageMst.getStageId()))
	    	  {
	    		  String stgPerStr = "0.0";
	    		  if(historyCount != 0)
	    		  {
	    			  float stgPer = (leafNodesWithDocPer*stageMap.get(key))/historyCount;
	    			  stgPerStr = String.format("%.02f",stgPer);
	    		  }
	    		 // if(!dtoStageMst.getStageDesc().equals("Send back"))
	    		  //{  
		    		  htmlContent+= "Per"+dtoStageMst.getStageDesc()+"="+stgPerStr+",";
		    		  htmlContent+= "cnt"+dtoStageMst.getStageDesc()+"="+stageMap.get(key)+"#";
		    		  htmlContentAll+= "[" + "'" +dtoStageMst.getStageDesc() +"'" +"," +stgPerStr +"," +stageMap.get(key)  +"]" +",";
	    		  //}
	    	  }
	       }		 
		}
	    htmlContent = htmlContent.substring(0, htmlContent.length()-1);
	    htmlContentAll = htmlContentAll.substring(0, htmlContentAll.length()-1);
	    htmlContent += "#allFileApproved="+allFileApproved;
		if (allWs || (m != null && m.trim().equalsIgnoreCase("W"))) 
		{
			return SUCCESS;
		}
		else
		{
			return "projectStatusData";
		}
		
	}
	
	public String getWorkspaceDtl()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		
		getWorkspaceDetail = docMgmtImpl.getUserWorkspace(userGroupCode, userId);
		if(getWorkspaceDetail != null && getWorkspaceDetail.size() > 0 ){
			htmlContent = "<option id=\"0\" value=\"0\" style=\"display: block;\">All Projects</option>";
			for (int itrOption = 0; itrOption < getWorkspaceDetail.size(); itrOption++) 
			{
				DTOWorkSpaceMst dtoWsDtl = getWorkspaceDetail.get(itrOption);
				if (dtoWsDtl.getLastPublishedVersion().trim().equalsIgnoreCase("-999")) {
					htmlContent += "<option id=\""+dtoWsDtl.getWorkSpaceId()+"\" value=\""+dtoWsDtl.getWorkSpaceId()+"\" style=\"display: block;\">";
					htmlContent += dtoWsDtl.getWorkSpaceDesc();
					htmlContent +="</option>";
				}
			}
		}
		return "htmlContent";
		
	}
	public String getWorkSpaceId() {
		return workspaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workspaceId = workSpaceId;
	}
}

