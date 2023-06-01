package com.docmgmt.struts.actions.workspace;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTORoleMst;
import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.dto.DTOWorkspaceNodeReminderDoneStatus;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OpenRecordAction extends ActionSupport{

	public Vector<DTOWorkSpaceNodeAttribute> attrDtl;
	public Vector workspaceUserGroupsDetails;
	public Vector<DTOStageMst> getStageDetail;
	public int attrCount;
	public String nodeName;
	
	public String userTypeCode;
	public boolean isVoidFlag=true;
	public Vector<DTOWorkSpaceNodeHistory> checkVoidFile=null;
	public String projectCode="";
	
	public int nodeId;
	public Vector assignWorkspaceRightsDetails;
	public Vector nodeDetail;
	public Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail;
	public String RightsType="modulewiserights";
	
	public int duration;
	public String[] userCode;
	//public int uCode=2;
	//public int useCode=2;
	public int [] stageIds;
	public int userGroupId;
	
	//public int userGroupId;
	public int userWiseGroupCode;
	public String htmlContent;
	Vector workspaceuserdtl=new Vector();
	DTOUserMst dto = new DTOUserMst();
	public Vector getUserRightsDetailToShow;
	
	public String remark;
	public String userGroupYN;
	public int userGroupCode;
	public int[] userCodeForGrp;
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public String WorkspaceId;
	public String numberOfRepetitions="1";
	public String suffixStart;
	String errorMsg;
	public int repeatNodeId;
	public String docName;
	
	public String docId;
	public int recordId;
	
	public int[] stageIdForChange;
	public String[] userCodeForChange;
	public int nodeIdForChange;
	public String remarkForChange;
	public int uCodeByForChange;
	public Vector moduleUserDetailHistory;
	public String wsId;
	public String docType;
	public String docTypeName;
	public Vector getDeletedNodeDetail;
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	public String lbl_folderName;
	public String lbl_nodeName;
	public int usrCode;
	public Vector<DTORoleMst> roleDtl=new Vector<DTORoleMst>();
	public String roleCode;

	
	public String execute() {
		WorkspaceId =docId;
		//WorkspaceId=ActionContext.getContext().getSession().get("docId").toString();
		int firstLeafNode =  docMgmtImpl.getFirstLeafNodeForDocCR(WorkspaceId);
		//nodeName=docMgmtImpl.getNodeDetail(WorkspaceId,firstLeafNode).get(0).getFolderName();
		attrDtl = docMgmtImpl.getAttributeDetailForDisplay(WorkspaceId,firstLeafNode);
		workspaceUserGroupsDetails=docMgmtImpl.getAllUserGroups();
		getStageDetail = docMgmtImpl.getStageDetailCSV();
		docTypeName = docMgmtImpl.getDocTypeName(docType);
		
		return SUCCESS;
		
	}
	
	public String SaveRecord(){
		//WorkspaceId =docId;
		// Repeat Node Code 
		//WorkspaceId=ActionContext.getContext().getSession().get("docId").toString();
		DTOWorkSpaceNodeDetail sourceNodeDtl = docMgmtImpl.getNodeDetailForEDocSign(WorkspaceId);
		int userId=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int usergrpcode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		
		Vector<DTOWorkSpaceUserMst> wsUserDtl = new Vector<>();
		
		wsUserDtl = docMgmtImpl.getWorkspaceUserDetailForDocSign(WorkspaceId,userId,usergrpcode);
		if(wsUserDtl.size()==0 && userTypeName.equalsIgnoreCase("WA")){
			
			DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
			dto.setWorkSpaceId(WorkspaceId);
			dto.setUserCode(userId);
			dto.setUserGroupCode(usergrpcode);
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
		  if(userTypeName.equalsIgnoreCase("WA")){
			dto.setRightsType("projectwise");
		  }else{
			dto.setRightsType("modulewise");
		  }
		  //docMgmtImpl.insertUsertoWorkspaceUserMstForDocSign(dto);
		  docMgmtImpl.insertUsertoWorkspaceUserMstForDocSignWithRoleCode(dto);
		}
		
		repeatNodeId = sourceNodeDtl.getNodeId();
		int parentNdId = docMgmtImpl.getParentNodeId(WorkspaceId, repeatNodeId);
		int WsNodeDetail=docMgmtImpl.getLeafNodeCount(WorkspaceId,parentNdId);
		suffixStart= String.valueOf(WsNodeDetail);
		int numOfRepetition;
		int startIndex;
		try {
			numOfRepetition = Integer.parseInt(numberOfRepetitions);
			startIndex = Integer.parseInt(suffixStart);
			
			if(numOfRepetition < 1 || startIndex < 1) {
				//System.out.println(numOfRepetition);
				throw new NumberFormatException();
			}
		}
		catch (NumberFormatException e) {
			return SUCCESS;
		}
				
		String nodeDisplayName = sourceNodeDtl.getNodeDisplayName();
		int ind = sourceNodeDtl.getFolderName().lastIndexOf(".");
		String folderName="";
		if(ind != -1){
			folderName = sourceNodeDtl.getFolderName().substring(0, ind);
		}
		else{
			folderName = sourceNodeDtl.getFolderName();
		}
		
		
		int sourceNodeId = repeatNodeId;
		String fileExtension = "";
		if(sourceNodeDtl.getFolderName().lastIndexOf(".") != -1)
			fileExtension = sourceNodeDtl.getFolderName().substring(sourceNodeDtl.getFolderName().lastIndexOf("."));
		
		
		//Fetch All siblings in 'childNodes'
		int parentNodeId = docMgmtImpl.getParentNodeId(WorkspaceId, sourceNodeDtl.getNodeId());
		Vector childNodes = docMgmtImpl.getChildNodeByParent(parentNodeId, WorkspaceId);
		
		 //if selected node is clone node then find its original node
		if(sourceNodeDtl.getCloneFlag() == 'Y') {
			for(int i = 0; i < childNodes.size(); i++) {
				DTOWorkSpaceNodeDetail currentChild = (DTOWorkSpaceNodeDetail)childNodes.get(i);
				if(currentChild.getNodeName().equalsIgnoreCase(sourceNodeDtl.getNodeName())){
					if(currentChild.getCloneFlag() == 'N') {//Original Node of clone found
						nodeDisplayName = currentChild.getNodeDisplayName();
						
						ind = currentChild.getFolderName().lastIndexOf(".");
						if(ind != -1){
							folderName = currentChild.getFolderName().substring(0, ind);
						}
						else{
							folderName = currentChild.getFolderName();
						}
						
						//System.out.println("folderName::"+folderName);
					}
				}
			}
			
		}
		folderName = folderName.replaceAll("-\\d$", "");
		//Search if Node(fileName) already exists...
		for(int i = 0; i < childNodes.size(); i++) {
			DTOWorkSpaceNodeDetail currentChild = (DTOWorkSpaceNodeDetail)childNodes.get(i);					
			
			for(int j = startIndex ; j < startIndex + numOfRepetition ; j++)
			{
				if (currentChild.getFolderName().equals(folderName + "-" + j + fileExtension))
				{
					errorMsg="The FileName (" + currentChild.getFolderName() + ") exists!";
					addActionError(errorMsg);
					return "show";
				}
			}
		}
		//source node has attributes with attrForIndiId='0002'
		String attriVals = "";
		if(nodeDisplayName.contains("(") && nodeDisplayName.endsWith(")")){
			attriVals = nodeDisplayName.substring(nodeDisplayName.indexOf("("));
			nodeDisplayName = nodeDisplayName.substring(0, nodeDisplayName.indexOf("("));
		}
		nodeDisplayName = nodeDisplayName.replaceAll("-\\d$", "");
		for(int i = startIndex ; i < startIndex + numOfRepetition ; i++){
		
			DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
			dto.setWorkspaceId(WorkspaceId);
			dto.setNodeId(sourceNodeId);
			dto.setNodeName(sourceNodeDtl.getNodeName());
			dto.setNodeDisplayName(nodeDisplayName+"-"+i+attriVals);
			dto.setFolderName(folderName+"-"+i+fileExtension);
			dto.setCloneFlag('Y');// Its a clone node
			dto.setNodeTypeIndi(sourceNodeDtl.getNodeTypeIndi());
			dto.setRequiredFlag(sourceNodeDtl.getRequiredFlag());
			dto.setCheckOutBy(0);
			dto.setPublishedFlag(sourceNodeDtl.getPublishedFlag());
			dto.setRemark(sourceNodeDtl.getRemark());
			dto.setModifyBy(userId);
			//dto.setModifyOn();
			
			docMgmtImpl.addChildNodeAfterForEDocSign(dto);
			
			//sourceNodeId = docMgmtImpl.getmaxNodeId(workspaceID);
		}		
		int newRepetedNodeId = docMgmtImpl.getmaxNodeId(WorkspaceId);
		// delete rights from wsuserrightsmst after repeat node except admin 
		docMgmtImpl.RemoveRightsFromWorkspaceUserRightsMst(WorkspaceId,newRepetedNodeId);
		//Save Attribute Code
		HttpServletRequest request = ServletActionContext.getRequest();

		String AttributeValueForNodeDisplayName = "";
		DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) docMgmtImpl
				.getNodeDetail(WorkspaceId, newRepetedNodeId).get(0);
		ArrayList<DTOWorkspaceNodeReminderDoneStatus> dtoList = new ArrayList<DTOWorkspaceNodeReminderDoneStatus>();
		Vector<DTOWorkSpaceNodeAttrDetail> attrList = docMgmtImpl
				.getNodeAttrDetail(WorkspaceId, newRepetedNodeId);
		attrDtl = docMgmtImpl.getAttributeDetailForDisplay(WorkspaceId,newRepetedNodeId);
		attrCount=attrDtl.size();
		int attrIdToMatch;
		
		for (int i = 1; i <= attrCount; i++) {
			String multiSelectionComboValue="";
			String attrValueId = "attrValueId" + i;
			String attrType = "attrType" + i;
			String attrId = "attrId" + i;
			String attrName = "attrName" + i;
			if(request.getParameter(attrType)!=null && request.getParameter(attrType).equals("MultiSelectionCombo")){
				String[] outerArray=request.getParameterValues(attrValueId);
				if (outerArray != null) {
                    for (int j = 0; j < outerArray.length; j++) {
                    	multiSelectionComboValue = multiSelectionComboValue + "," + outerArray[j];
                    }
                }
			}
			try{
				attrIdToMatch=Integer.parseInt(request.getParameter(attrId));
				}
				catch(Exception e){
					System.out.println(e.toString());
					break;
				}
			Integer attributeId = Integer.parseInt(request.getParameter(attrId));
			boolean dupFound = false;
			for (int idxAttr = 0; idxAttr < attrList.size(); idxAttr++) {
				DTOWorkSpaceNodeAttrDetail dtoWorkSpaceNodeAttrDetail = attrList
						.get(idxAttr);
				if (dtoWorkSpaceNodeAttrDetail.getAttrId() == attributeId) {
					if(request.getParameter(attrValueId)!=null){
						if((multiSelectionComboValue != null && multiSelectionComboValue != "" ) && multiSelectionComboValue.substring(1).equalsIgnoreCase(dtoWorkSpaceNodeAttrDetail.getAttrValue()) ){
							dupFound = true;
							break;	
						}else{
							if (request.getParameter(attrValueId).equals(dtoWorkSpaceNodeAttrDetail.getAttrValue())) {
								dupFound = true;
								break;
							}
						}
					}
					else{
						dupFound = true;
						break;
					}
				}
			}

			DTOAttributeMst dtoAttr = docMgmtImpl.getAttribute(attributeId);

			// if the new values of attributes are same as the old ones, don't
			// update in database
			if (dupFound) {
				// for getting the values in display
				if (dtoAttr.getAttrForIndiId().equals("0002")
						&& request.getParameter(attrValueId)!=null && !request.getParameter(attrValueId).equals("")) {
					if (AttributeValueForNodeDisplayName == "")
						AttributeValueForNodeDisplayName = request
								.getParameter(attrValueId);
					else
						AttributeValueForNodeDisplayName += ","
								+ request.getParameter(attrValueId);
				}
				continue;// TODO instead of individual attributes, values should
				// be changed together
			}
			DTOWorkSpaceNodeAttribute wsnadto = new DTOWorkSpaceNodeAttribute();

			wsnadto.setWorkspaceId(WorkspaceId);
			wsnadto.setNodeId(newRepetedNodeId);
			wsnadto.setAttrId(attributeId);
			wsnadto.setAttrName(request.getParameter(attrName));
			//wsnadto.setAttrValue(request.getParameter(attrValueId));
			if(request.getParameter(attrType).equals("MultiSelectionCombo")){
				wsnadto.setAttrValue(multiSelectionComboValue.substring(1));
			}
			else{
				wsnadto.setAttrValue(request.getParameter(attrValueId));		
			}
			wsnadto.setRemark("new");
			
			int ucd = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userid").toString());

			wsnadto.setModifyBy(ucd);

			// ///update workspacenode attribute value
			docMgmtImpl.updateWorkspaceNodeAttributeValue(wsnadto);

			/**
			 * Only attr value of type '0002' will be appended to node display
			 * name. Changed By Ashmak Shah
			 */

			if (dtoAttr.getAttrForIndiId().equals("0002")) {

				if (AttributeValueForNodeDisplayName == "") {
					AttributeValueForNodeDisplayName = request
							.getParameter(attrValueId);
				} else if (!request.getParameter(attrValueId).equals("")) {
					AttributeValueForNodeDisplayName += ","
							+ request.getParameter(attrValueId);
				}

			}
			/*
			 * It will undone the user reminders displayed in welcome page
			 */
			if (request.getParameter(attrType).equals("Date")) {
				DTOWorkspaceNodeReminderDoneStatus dto = new DTOWorkspaceNodeReminderDoneStatus();
				dto.setvWorkspaceId(WorkspaceId);
				dto.setiNodeId(newRepetedNodeId);
				dto.setiAttrId(Integer.parseInt(request.getParameter(attrId)));
				dto.setiUserCode(2);
				dtoList.add(dto);
			}
		}
		docMgmtImpl.markReminderAsUnDone(dtoList);

		// update nodedisplayname of the node
		DTOWorkSpaceNodeDetail wsnd = (DTOWorkSpaceNodeDetail) docMgmtImpl
				.getNodeDetail(WorkspaceId, newRepetedNodeId).get(0);
		/*if (AttributeValueForNodeDisplayName.equals("")) {

			// pattern used for attr values appended at the end of
			// nodeDisplayName between '(' and ')'.
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().replaceAll(
					"\\(.+\\)$", ""));
		} else {
			// pattern used for attr values appended at the end of
			// nodeDisplayName between '(' and ')'.
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().replaceAll(
					"\\(.+\\)$", "")
					+ "(" + AttributeValueForNodeDisplayName + ")");
		}*/
		if (AttributeValueForNodeDisplayName.equals("")) {

			// pattern used for attr values appended at the end of
			// nodeDisplayName between '(' and ')'.
			if(wsnd.getNodeDisplayName().contains("{"))
			{
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().substring(0,wsnd.getNodeDisplayName().indexOf('{')));
			}
			else
			{
				wsnd.setNodeDisplayName(wsnd.getNodeDisplayName());
			}
		} else {
			// pattern used for attr values appended at the end of
			// nodeDisplayName between '(' and ')'.
			if(wsnd.getNodeDisplayName().contains("{"))
			{
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().substring(0,wsnd.getNodeDisplayName().indexOf('{'))
					+ "{" + AttributeValueForNodeDisplayName + "}");
			}
			else
			{
				wsnd.setNodeDisplayName(wsnd.getNodeDisplayName()+"{" + AttributeValueForNodeDisplayName + "}");
			}
		}

		docMgmtImpl.insertWorkspaceNodeDetail(wsnd, 2);// edit mode
		System.out.println(newRepetedNodeId);
		
		//Check Rights in WorkspaceUserRightsMst
		Vector<DTOWorkSpaceUserRightsMst> getRightsDetial;
		getRightsDetial= docMgmtImpl.checkRights(WorkspaceId,newRepetedNodeId,userId,usergrpcode);
		if(userTypeName.equalsIgnoreCase("WA") && getRightsDetial.size()==0){
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(WorkspaceId);
			dto.setUserGroupCode(usergrpcode);
			dto.setUserCode(userId);
			dto.setNodeId(newRepetedNodeId);
			dto.setModifyBy(userId);
			dto.setRemark("New");
			
			docMgmtImpl.insertintoWSUserRightsMst(dto);
		}
		
		//Update File Name in WSNodeDetail & WSNodeDetailHistory
		//String fileName = docName+".pdf";
		docMgmtImpl.UpdateNodeDetail(WorkspaceId,newRepetedNodeId,docName);
		return SUCCESS;
	}
	
	public String GetDocSignRecord(){
		WorkspaceId = docId;
		nodeId = recordId;
		docTypeName = docMgmtImpl.getDocTypeName(docType);
		//WorkspaceId=ActionContext.getContext().getSession().get("docId").toString();
		int userGroupCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		//DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetail(WorkspaceId);
		DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetailWSList(WorkspaceId);
		workspaceUserGroupsDetails=docMgmtImpl.getAllUserGroupsForDocSign();
		projectCode = wsDetail.getProjectCode();
		nodeDetail = docMgmtImpl.getNodeDetail(WorkspaceId, nodeId);
		DTOWorkSpaceNodeDetail dto =(DTOWorkSpaceNodeDetail)nodeDetail.elementAt(0);		
		nodeName = dto.getNodeDisplayName();
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserCode(userCode);
		userMst.setUserGroupCode(userGroupCode);

		assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForCSV(WorkspaceId, nodeId,"modulewise");
		getStageDetail = docMgmtImpl.getStageDetailCSV();
		
		/*int getStage = docMgmtImpl.checkCreateRights(WorkspaceId,nodeId);
		
		if(getStage>0){
			for(int i = 0;i<getStageDetail.size();i++){
				if(getStageDetail.get(i).getStageId() == 10){
					getStageDetail.remove(i);
					break;
				}
			 }
		}*/
		
		checkVoidFile = docMgmtImpl.getVoidFileHistory(WorkspaceId,nodeId);
		if(checkVoidFile.size()>0){
			isVoidFlag=false;
		}
		
		ArrayList<Integer> assignedStageIds = docMgmtImpl.getStageIdsRightsWiseForESignture(WorkspaceId,nodeId);
		
		if(!assignedStageIds.contains(30)){
			for(int i = 0;i<getStageDetail.size();i++){
				if(getStageDetail.get(i).getStageId() == 30){
					getStageDetail.remove(i);
					break;
				}
			}
			
		}
		getUserRightsDetail = docMgmtImpl.getUserRightsDetail(WorkspaceId, nodeId);
		
		
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
		
		WorkspaceUserDetailList=docMgmtImpl.getUserRightsDetailForEsignature(WorkspaceId, nodeId);
		
		for(int i=0;i<getUserRightsDetail.size();i++){
			
		WorkspaceUserHistory=docMgmtImpl.showNodeHistory(WorkspaceId,getUserRightsDetail.get(i).getUserCode(),nodeId,getUserRightsDetail.get(i).getStageId());
		if(WorkspaceUserHistory.size()>0){
			getUserRightsDetail.get(i).setUserFlag('Y');
			//WorkspaceUserDetailList.set(i, WorkspaceUserDetailList.get(i));
			}
			else{
				getUserRightsDetail.get(i).setUserFlag('N');
				//WorkspaceUserDetailList.set(i, WorkspaceUserDetailList.get(i));
				//countForUser="false";
			}
		}
		
		userTypeCode=docMgmtImpl.getUserType(userCode);
		
		return SUCCESS;
	}
	
	public String AssignRecordRights(){
		//WorkspaceId=ActionContext.getContext().getSession().get("docId").toString();
		
	/*	if(userGroupYN.equals("usergroup"))
		{
			ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
			
			
			DTOWorkSpaceUserMst dtoWorkSpaceUserMst = new DTOWorkSpaceUserMst();
			DTOWorkSpaceUserMst deletewsurmdata = new DTOWorkSpaceUserMst();
			DTOWorkSpaceUserRightsMst dtowsurm = new DTOWorkSpaceUserRightsMst();
			
			dtoWorkSpaceUserMst.setWorkSpaceId(WorkspaceId);
			dtowsurm.setWorkSpaceId(WorkspaceId);
			deletewsurmdata.setWorkSpaceId(WorkspaceId);
			deletewsurmdata.setUserGroupCode(userGroupCode);
			
			
			if(userGroupYN.equals("usergroup"))
			{
				Vector<DTOUserMst> workspaceUserDetails=docMgmtImpl.getuserDetailsByUserGrp(userGroupCode);
				
				userCodeForGrp=new int[workspaceUserDetails.size()];
				for(int i =0;i<workspaceUserDetails.size();i++)
				{
					DTOUserMst userMstDTO = workspaceUserDetails.get(i);
					userCodeForGrp[i] = userMstDTO.getUserCode();
				}
				dtoWorkSpaceUserMst.setUserGroupCode(userGroupCode);
				dtowsurm.setUserGroupCode(userGroupCode);
			}
			
			dtoWorkSpaceUserMst.setAdminFlag('N');
			Timestamp ts = new Timestamp(new Date().getTime());
			dtoWorkSpaceUserMst.setLastAccessedOn(ts);
			dtoWorkSpaceUserMst.setRemark(remark);
			int ucd=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			dtoWorkSpaceUserMst.setModifyBy(ucd);
			dtoWorkSpaceUserMst.setModifyOn(ts);			
			Calendar cal = Calendar.getInstance();
			Date today = cal.getTime();
			dtoWorkSpaceUserMst.setFromDt(today);
			cal.add(Calendar.YEAR, 50); // to get previous year add -1
			Date nextYear = cal.getTime();
			dtoWorkSpaceUserMst.setToDt(nextYear);
				dtoWorkSpaceUserMst.setRightsType("modulewise");
				deletewsurmdata.setRightsType("modulewise");
				
				for(int userId=0;userId<userCodeForGrp.length; userId++)
				{
					deletewsurmdata.setUserCode(userCodeForGrp[userId]);
					docMgmtImpl.DeleteProjectlevelRights(deletewsurmdata);
				}
			docMgmtImpl.insertUpdateUsertoWorkspace(dtoWorkSpaceUserMst, userCodeForGrp);
			String stageDesc="";
			
				dtoWorkSpaceUserMst.setStages("-");
			dtoWorkSpaceUserMst.setMode(1);
			docMgmtImpl.insertUpdateUsertoWorkspaceHistory(dtoWorkSpaceUserMst, userCodeForGrp);
			addActionMessage(" Selected User Group is added to the project successfully.");
		}*/
		
		DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String roleCode="";
		Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();

		WsNodeDetail=docMgmtImpl.getModulewiseChildNodes(WorkspaceId,nodeId);
		String nodeIdsCSV = "";
		String selectedUsers [] = userCode;
		for(int i=0;i<WsNodeDetail.size();i++){	
			DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i);
			nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
			
		}
		Vector<DTOWorkSpaceUserMst> getUserRightsForWorksapce=new Vector<>();
		nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
		
		for(int i =0;i<userCode.length;i++){
		getUserRightsForWorksapce=docMgmtImpl.getUserRightsForWorkspace(WorkspaceId, Integer.parseInt(userCode[i]),userWiseGroupCode);
		if(getUserRightsForWorksapce.size()==0){
			
			DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
			dto.setWorkSpaceId(WorkspaceId);
			dto.setUserCode(Integer.parseInt(userCode[i]));
			dto.setUserGroupCode(userWiseGroupCode);
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
			roleCode = docMgmtImpl.getRoleCodeFromUserMst(Integer.parseInt(userCode[i]), userWiseGroupCode);
			dto.setRoleCode(roleCode);

			//docMgmtImpl.insertUsertoWorkspaceUserMstForDocSign(dto);
			
			docMgmtImpl.insertUsertoWorkspaceUserMstForDocSignWithRoleCode(dto);
		}
		
			
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForCSV(WorkspaceId, nodeId,Integer.parseInt(userCode[i]));
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForTimeLine = docMgmtImpl.getUserRightsDetailForCSVforTimeLine(WorkspaceId, nodeId,Integer.parseInt(userCode[i]));
		
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRights(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForCSV(WorkspaceId, Integer.parseInt(userCode[i]), stageIds,nodeIdsCSV);
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(WorkspaceId, Integer.parseInt(userCode[i]), stageIds,nodeIdsCSV);
			
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(WorkspaceId);
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
			  
			  DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(userCode[i]));
			  System.out.println(objUserMst.getUserGroupCode());
			  Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
			  objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
			  objWSUserRightsMstforModuleHistory.setUserCode(Integer.parseInt(userCode[i]));
			  objWSUserRightsMstforModuleHistory.setRemark("test");
			  objWSUserRightsMstforModuleHistory.setMode(1);
			  roleCode = docMgmtImpl.getRoleCodeFromUserMst(Integer.parseInt(userCode[i]), userGroupCode.intValue());
			  objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
			  //docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
			  docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
	
		System.out.println("-----------------------Updating Rights------------------------");
		
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsListForTimeLine = new ArrayList<DTOWorkSpaceUserRightsMst>();
		
			WsNodeDetail.clear();
			WsNodeDetail=docMgmtImpl.getChildNodesModulewise(WorkspaceId,nodeId);
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
			
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForCSV(WorkspaceId, Integer.parseInt(userCode[i]), stageIds,nodeIdsCSV);
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(WorkspaceId, Integer.parseInt(userCode[i]), stageIds,nodeIdsCSV);
			
			for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
				DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
				
					objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(userCode[i]));
					userGroupCode = new Integer(objUserMst.getUserGroupCode());
					DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
					
					objWorkSpaceUserRightsMst.setWorkSpaceId(WorkspaceId);
					objWorkSpaceUserRightsMst.setModifyBy(userId);
					objWorkSpaceUserRightsMst.setUserCode(Integer.parseInt(userCode[i]));
					objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
					objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
				
					objWorkSpaceUserRightsMst.setCanReadFlag('Y');
					objWorkSpaceUserRightsMst.setCanAddFlag('Y');
					objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
					objWorkSpaceUserRightsMst.setCanEditFlag('Y');
					objWorkSpaceUserRightsMst.setAdvancedRights("Y");
					roleCode = docMgmtImpl.getRoleCodeFromUserMst(Integer.parseInt(userCode[i]), userGroupCode.intValue());
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
			
		/*	for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
				DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
				
					objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(userCode[i]));
					userGroupCode = new Integer(objUserMst.getUserGroupCode());
					DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
					
					objWorkSpaceUserRightsMst.setWorkSpaceId(workspaceID);
					objWorkSpaceUserRightsMst.setModifyBy(userId);
					objWorkSpaceUserRightsMst.setUserCode(5);
					objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
					objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
				
					objWorkSpaceUserRightsMst.setCanReadFlag('Y');
					objWorkSpaceUserRightsMst.setCanAddFlag('Y');
					objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
					objWorkSpaceUserRightsMst.setCanEditFlag('Y');
					objWorkSpaceUserRightsMst.setAdvancedRights("Y");
					objWorkSpaceUserRightsMst.setDuration(duration);
					
					for (int istageIds = 0; istageIds < stageIds.length; istageIds++) {
						DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
						dtoClone.setStageId(stageIds[istageIds]);
						userRightsListForTimeLine.add(dtoClone);
					}
			}
			docMgmtImpl.insertFolderSpecificMultipleUserRightsForTimeLine(userRightsListForTimeLine);*/
		}
		return SUCCESS;
		
	}
	
	public String getUserDtl(){

		//WorkspaceId=ActionContext.getContext().getSession().get("docId").toString();
		
		htmlContent = "";
		workspaceuserdtl=docMgmtImpl.getuserDetailsByUserGrpForDocSign(userGroupId);
		//getUserRightsDetailToShow = docMgmtImpl.getUserRightsDetail("0068", nodeId);
		
		assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForDocSign(WorkspaceId, nodeId,userGroupId);
		ArrayList<DTOUserMst> arraylist = new ArrayList<DTOUserMst>(assignWorkspaceRightsDetails);
		
		for (DTOUserMst userList : arraylist) {
			if(!htmlContent.equals("")){
				htmlContent += ","; 
			}
			htmlContent += userList.getUserCode()+"::"+userList.getUserName();
		}		
		return "html";
	}
	
	
	public void DeleteUserRights()
	{
		//WorkspaceId=ActionContext.getContext().getSession().get("docId").toString();
		DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();
		WsNodeDetail=docMgmtImpl.getModulewiseChildNodes(WorkspaceId,nodeId);
		String nodeIdsCSV = "";
		String selectedUsers [] = userCode;
		
		for(int i=0;i<WsNodeDetail.size();i++){	
			DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i);
			nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
		}
		nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
		
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForCSV(WorkspaceId, nodeId,Integer.parseInt(userCode[0]));
		String roleCode = docMgmtImpl.getRoleCodeFromWSUserRightsMst(WorkspaceId,nodeId,Integer.parseInt(userCode[0]));
		docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForCSV(WorkspaceId, Integer.parseInt(userCode[0]), stageIds,nodeIdsCSV);
		docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(WorkspaceId, Integer.parseInt(userCode[0]), stageIds,nodeIdsCSV);
		
		objWSUserRightsMstforModuleHistory.setWorkSpaceId(WorkspaceId);
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
		 
		DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(userCode[0]));
		System.out.println(objUserMst.getUserGroupCode());
		Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
		objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
		objWSUserRightsMstforModuleHistory.setUserCode(Integer.parseInt(userCode[0]));
		objWSUserRightsMstforModuleHistory.setRemark(remark);		
		objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
				
		//docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
		docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
	}
	
	public String getUserRights(){		
	
		int userGroupCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserCode(Integer.parseInt(userCode[0]));
		userMst.setUserGroupCode(userGroupCode);
		lbl_folderName = knetProperties.getValue("ForlderName");
		//assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetail(workspaceID, userMst,"modulewise");
		//assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForCSV(WorkspaceId, nodeId,"modulewise");
		//assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForDocSign(WorkspaceId, nodeId,userGroupId);
		assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForDocSignWithRoleCode(WorkspaceId, nodeId,userGroupId,roleCode);
		
		return SUCCESS;
	}
	
	public String getUserRole(){		
		
		int userGroupCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserCode(Integer.parseInt(userCode[0]));
		userMst.setUserGroupCode(userGroupCode);
		lbl_folderName = knetProperties.getValue("ForlderName");
		usrCode = userMst.getUserCode();
		//assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetail(workspaceID, userMst,"modulewise");
		//assignWorkspaceRightsDetails = docMgmtImpl.getModuleorprojectwiseWiseWorkspaceUserDetailForCSV(WorkspaceId, nodeId,"modulewise");		
		roleDtl = docMgmtImpl.getRoleDtl(roleCode);
		
		return SUCCESS;
	}
	
	public void updateAndDeleteRights(){
		
		DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String rolCode="";
		Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();

		WsNodeDetail=docMgmtImpl.getModulewiseChildNodes(WorkspaceId,nodeIdForChange);
		String nodeIdsCSV = "";
		String selectedUsers [] = userCodeForChange;
		for(int i=0;i<WsNodeDetail.size();i++){	
			DTOWorkSpaceNodeDetail nodeIds = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(i);
			nodeIdsCSV+=Integer.toString(nodeIds.getNodeId())+",";
		}
		Vector<DTOWorkSpaceUserMst> getUserRightsForWorksapce=new Vector<>();
		nodeIdsCSV= nodeIdsCSV.substring(0, nodeIdsCSV.length() - 1);
		
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForCSV(WorkspaceId, nodeIdForChange,Integer.parseInt(userCodeForChange[0]));
		if(getUserRightsDetail.size()>0)
			rolCode=getUserRightsDetail.get(0).getRoleCode();
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetailForTimeLine = docMgmtImpl.getUserRightsDetailForCSVforTimeLine(WorkspaceId, nodeIdForChange,Integer.parseInt(userCodeForChange[0]));
		DTOUserMst getUserDtl=docMgmtImpl.getUserInfo(Integer.parseInt(userCodeForChange[0]));
		
		getUserRightsForWorksapce=docMgmtImpl.getUserRightsForWorkspace(WorkspaceId, uCodeByForChange,getUserDtl.getUserGroupCode());
		if(getUserRightsForWorksapce.size()==0){
			
			DTOWorkSpaceUserMst dto = new DTOWorkSpaceUserMst();
			dto.setWorkSpaceId(WorkspaceId);
			dto.setUserCode(uCodeByForChange);
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
			dto.setRoleCode(rolCode);

		  //docMgmtImpl.insertUsertoWorkspaceUserMstForDocSign(dto);
		  docMgmtImpl.insertUsertoWorkspaceUserMstForDocSignWithRoleCode(dto);
		}
			//docMgmtImpl.RemoveFolderSpecificMultipleUserRights(workspaceID, selectedUsers, stageIds,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForCSV(WorkspaceId, Integer.parseInt(userCodeForChange[0]), stageIdForChange,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(WorkspaceId, Integer.parseInt(userCodeForChange[0]), stageIdForChange,nodeIdsCSV);
			
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(WorkspaceId);
			objWSUserRightsMstforModuleHistory.setNodeId(nodeIdForChange);
			objWSUserRightsMstforModuleHistory.setModifyBy(userId);
			
			String stageDesc="";
			String stage;
			int stageid;
				for(int j=0;j<stageIdForChange.length;j++){
					stageid=stageIdForChange[j];
					stage= docMgmtImpl.getStageDesc(stageid);
					stageDesc+=stage+",";
				}
			System.out.println("StageId="+stageDesc);
			  if (stageDesc != null && stageDesc.length() > 0){
				  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
			  }
			  objWSUserRightsMstforModuleHistory.setStages(stageDesc);
						  
			  DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(userCodeForChange[0]));
			  System.out.println(objUserMst.getUserGroupCode());
			  Integer userGroupCode = new Integer(objUserMst.getUserGroupCode());
			  objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
			  objWSUserRightsMstforModuleHistory.setUserCode(Integer.parseInt(userCodeForChange[0]));
			  objWSUserRightsMstforModuleHistory.setRemark(remarkForChange);
			  objWSUserRightsMstforModuleHistory.setRoleCode(rolCode);
			  objWSUserRightsMstforModuleHistory.setMode(2);
				
				//docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
				docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
	
		System.out.println("-----------------------Updating Rights------------------------");
		
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsListForTimeLine = new ArrayList<DTOWorkSpaceUserRightsMst>();
		
			WsNodeDetail.clear();
			WsNodeDetail=docMgmtImpl.getChildNodesModulewise(WorkspaceId,nodeIdForChange);
			if(nodeIdForChange==1)
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
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForCSV(WorkspaceId, uCodeByForChange, stageIdForChange,nodeIdsCSV);
			docMgmtImpl.RemoveFolderSpecificMultipleUserRightsForTimeLine(WorkspaceId, uCodeByForChange, stageIdForChange,nodeIdsCSV);
			
			for(int wsnd=0;wsnd<WsNodeDetail.size();wsnd++){
				DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) WsNodeDetail.get(wsnd);
				
					objUserMst = docMgmtImpl.getUserInfo(uCodeByForChange);
					userGroupCode = new Integer(objUserMst.getUserGroupCode());
					DTOWorkSpaceUserRightsMst objWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
					
					objWorkSpaceUserRightsMst.setWorkSpaceId(WorkspaceId);
					objWorkSpaceUserRightsMst.setModifyBy(userId);
					objWorkSpaceUserRightsMst.setUserCode(uCodeByForChange);
					objWorkSpaceUserRightsMst.setUserGroupCode(userGroupCode.intValue());
					objWorkSpaceUserRightsMst.setNodeId(nodeDetail.getNodeId());
				
					objWorkSpaceUserRightsMst.setCanReadFlag('Y');
					objWorkSpaceUserRightsMst.setCanAddFlag('Y');
					objWorkSpaceUserRightsMst.setCanDeleteFlag('Y');
					objWorkSpaceUserRightsMst.setCanEditFlag('Y');
					objWorkSpaceUserRightsMst.setAdvancedRights("Y");
					objWorkSpaceUserRightsMst.setRoleCode(rolCode);
					objWorkSpaceUserRightsMst.setMode(1);
					
					for (int istageIds = 0; istageIds < stageIdForChange.length; istageIds++) {
						DTOWorkSpaceUserRightsMst dtoClone = objWorkSpaceUserRightsMst.clone();
						dtoClone.setStageId(stageIdForChange[istageIds]);
						userRightsList.add(dtoClone);
					}
			}
			//docMgmtImpl.insertFolderSpecificMultipleUserRights(userRightsList);
			docMgmtImpl.insertFolderSpecificMultipleUserRightsWithRoleCode(userRightsList);
			
			
			objWSUserRightsMstforModuleHistory.setWorkSpaceId(WorkspaceId);
			objWSUserRightsMstforModuleHistory.setNodeId(nodeIdForChange);
			objWSUserRightsMstforModuleHistory.setModifyBy(userId);
			
			System.out.println("StageId="+stageDesc);
			  /*if (stageDesc != null && stageDesc.length() > 0){
				  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
			  }*/
			  objWSUserRightsMstforModuleHistory.setStages(stageDesc);
			  		
				objUserMst = docMgmtImpl.getUserInfo(uCodeByForChange);
				System.out.println(objUserMst.getUserGroupCode());
				userGroupCode = new Integer(objUserMst.getUserGroupCode());
				objWSUserRightsMstforModuleHistory.setUserGroupCode(userGroupCode.intValue());
				
				objWSUserRightsMstforModuleHistory.setUserCode(uCodeByForChange);
				objWSUserRightsMstforModuleHistory.setRoleCode(rolCode);
				objWSUserRightsMstforModuleHistory.setMode(1);
				
				//docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
				docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
	} 
	
public void updateRole(){
		
		DTOWorkSpaceUserRightsMst objWSUserRightsMstforModuleHistory = new DTOWorkSpaceUserRightsMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>();
		 ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		  DTOUserMst objUserMst = docMgmtImpl.getUserInfo(Integer.parseInt(userCodeForChange[0]));
		  objUserMst = docMgmtImpl.getUserInfo(uCodeByForChange);
		  System.out.println(objUserMst.getUserGroupCode());
		  userGroupCode = new Integer(objUserMst.getUserGroupCode());
		  WsNodeDetail=docMgmtImpl.getModulewiseChildNodes(WorkspaceId,nodeIdForChange);
			
		objWSUserRightsMstforModuleHistory.setWorkSpaceId(WorkspaceId);
		objWSUserRightsMstforModuleHistory.setNodeId(nodeIdForChange);
		objWSUserRightsMstforModuleHistory.setUserCode(uCodeByForChange);
		objWSUserRightsMstforModuleHistory.setUserGroupCode(objUserMst.getUserGroupCode());
		objWSUserRightsMstforModuleHistory.setModifyBy(userId);
		objWSUserRightsMstforModuleHistory.setRoleCode(roleCode);
			
			String stageDesc="";
			String stage;
			int stageid = 0;
				for(int j=0;j<stageIdForChange.length;j++){
					stageid=stageIdForChange[j];
					stage= docMgmtImpl.getStageDesc(stageid);
					stageDesc+=stage+",";
				}
			  System.out.println("StageId="+stageDesc);
			  if (stageDesc != null && stageDesc.length() > 0){
				  stageDesc = stageDesc.substring(0, stageDesc.length() - 1);
			  }
			  objWSUserRightsMstforModuleHistory.setStageId(stageid);					 
			  objWSUserRightsMstforModuleHistory.setMode(2);
			  userRightsList.add(objWSUserRightsMstforModuleHistory);
			  					
			  //docMgmtImpl.insertFolderSpecificMultipleUserRights(userRightsList);
			  //docMgmtImpl.insertFolderSpecificMultipleUserRightsWithRoleCode(userRightsList);
			  docMgmtImpl.insertFolderSpecificMultipleUserRightsWithRoleCodeForESignature(userRightsList);
			  
			  System.out.println("-----------------------Updating Role------------------------");
			  
			  objWSUserRightsMstforModuleHistory.setWorkSpaceId(WorkspaceId);
			  objWSUserRightsMstforModuleHistory.setNodeId(nodeIdForChange);
			  objWSUserRightsMstforModuleHistory.setModifyBy(userId);
			  System.out.println("StageId="+stageDesc);
			  objWSUserRightsMstforModuleHistory.setStages(stageDesc);
			  System.out.println(objUserMst.getUserGroupCode());
			  objWSUserRightsMstforModuleHistory.setUserGroupCode(objUserMst.getUserGroupCode());
			  objWSUserRightsMstforModuleHistory.setUserCode(uCodeByForChange);
			  objWSUserRightsMstforModuleHistory.setRemark(remarkForChange);
			  objWSUserRightsMstforModuleHistory.setMode(1);
			  //docMgmtImpl.insertModuleWiseUserHistory(objWSUserRightsMstforModuleHistory);
			  docMgmtImpl.insertModuleWiseUserHistoryWithRoleCode(objWSUserRightsMstforModuleHistory);
	}
	
	public String DocNameExists() 
	{	
		boolean docNameExist = docMgmtImpl.folderNameExist(WorkspaceId,docName);
		if (docNameExist== true || docName=="")
		{
			htmlContent = docName+" already exists.";
		}
		else
		{
			htmlContent = "<font color=\"green\">"+docName+" is available. </font>";			
		}
		return SUCCESS;
	}	

	
public String AssignModuleUserDetailHistory(){
		WorkspaceId = docId;
		nodeId = recordId;
		lbl_folderName = knetProperties.getValue("ForlderName");
		lbl_nodeName = knetProperties.getValue("NodeName");
		moduleUserDetailHistory = docMgmtImpl.getmoduleUserDetailHistory(WorkspaceId,nodeId);
		return SUCCESS;
		
	}
public String DeletedNodeDetail(){
	
	getDeletedNodeDetail = docMgmtImpl.getDeletedNodeDetail(WorkspaceId);
	
	lbl_folderName = knetProperties.getValue("ForlderName");
	lbl_nodeName = knetProperties.getValue("NodeName");
	
	return SUCCESS;
}
}
