package com.docmgmt.struts.actions.stf;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOSTFStudyIdentifierMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AttachSTFNodeAction extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	private String stfSubmit;
	public String getStfSubmit() {
		return stfSubmit;
	}
	public void setStfSubmit(String stfSubmit) {
		this.stfSubmit = stfSubmit;
	}
	
	@Override
	public String execute(){
		//System.out.println("stfSubmit: " + stfSubmit);
		if(removeCategoryNodeId != 0 ) {
			System.out.println(removeCategoryNodeId+"::::"+nodeId);
			if(removeCategory().equals(SUCCESS))
			return "show";
		}
		if(removeNodeId != 0)
		{	
			System.out.println(removeNodeId+"::::"+nodeId);
			if(removeNode().equals(SUCCESS))
			return "show";
		}
		else if(stfSubmit.equalsIgnoreCase("Create STF"))
		{
			System.out.println("Create STF");
			if(createSTFXMLNode().equals(SUCCESS))
			return "show";
		}
		else if(stfSubmit.equalsIgnoreCase("Edit")) {
			System.out.println("Edit");
			if(editSTFDetails().equals(SUCCESS))
			return "show";
		}
		else if(stfSubmit.equalsIgnoreCase("Add")) {
			System.out.println("Add Category");
			if(addSTFCategory().equals(SUCCESS))
			return "show";
		}
		else if(stfSubmit.equalsIgnoreCase("Add STF Node")) {
			System.out.println("Add STF Node");
			if(addSTFNode().equals(SUCCESS))
			return "show";
		}
		else if(stfSubmit.equalsIgnoreCase("Remove STF")) {
			System.out.println("Remove STF");
			if(removeSTF().equals(SUCCESS))
			return "show";
		}
		else if(stfSubmit.equalsIgnoreCase("Done")) {
			System.out.println("Done");
		}

		return SUCCESS;
	}
	
	public String createSTFXMLNode()
	{
	
		if(STFXMLNodeName!=null && !STFXMLNodeName.equals("") 
				&& uniqueStudyIdentifier!=null && !uniqueStudyIdentifier.equals("")){
		
			
			String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
			int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
			String XMLNodeName = STFXMLNodeName;
						
			//updating parent node details where STF is Attached. Set NodeTypeIndi='T' used in publishing logic
			Vector<DTOWorkSpaceNodeDetail> parentNodeDtl=docMgmtImpl.getNodeDetail(workspaceID,nodeId);
			DTOWorkSpaceNodeDetail dtoNodeDetail=new DTOWorkSpaceNodeDetail();
			dtoNodeDetail=parentNodeDtl.get(0);
			//dtoNodeDetail.setWorkspaceId(workspaceID);
			dtoNodeDetail.setNodeTypeIndi('T');
	
			// Set folder name to study id so, stf folder should be published as STF Id 
			dtoNodeDetail.setFolderName(uniqueStudyIdentifier);
			System.out.println("StudyId->"+uniqueStudyIdentifier);
			
			docMgmtImpl.insertWorkspaceNodeDetail(dtoNodeDetail,2);//edit mode
			dtoNodeDetail=null;
			
			int NewSTFXMLNodeId = docMgmtImpl.getmaxNodeId(workspaceID)+1;
			String newSTFFileName ="stf-" + uniqueStudyIdentifier + ".xml";
			//Creating a New Node where STF XML is attached 
			dtoNodeDetail=new DTOWorkSpaceNodeDetail();
			dtoNodeDetail.setWorkspaceId(workspaceID);
			dtoNodeDetail.setNodeId(NewSTFXMLNodeId);
			dtoNodeDetail.setNodeNo(1);
			dtoNodeDetail.setNodeDisplayName(XMLNodeName);
			dtoNodeDetail.setNodeName(XMLNodeName);
			dtoNodeDetail.setFolderName(newSTFFileName);
			dtoNodeDetail.setParentNodeId(nodeId);
			dtoNodeDetail.setCloneFlag('N');
			dtoNodeDetail.setNodeTypeIndi('S');
			dtoNodeDetail.setRequiredFlag('N');
			dtoNodeDetail.setCheckOutBy(0);
			dtoNodeDetail.setPublishedFlag('Y');
			dtoNodeDetail.setRemark("");
			dtoNodeDetail.setModifyBy(userId);
			dtoNodeDetail.setModifyOn(new Timestamp(new Date().getTime()));
			
			docMgmtImpl.insertWorkspaceNodeDetail(dtoNodeDetail,1);//insert mode
			
		
		//Inserting values into STFStudyIdentifierMst	
			DTOSTFStudyIdentifierMst dtoStudyIdentifier=new DTOSTFStudyIdentifierMst();
			dtoStudyIdentifier.setWorkspaceId(workspaceID);
			dtoStudyIdentifier.setNodeId(NewSTFXMLNodeId);
			dtoStudyIdentifier.setModifyBy(userId);
			dtoStudyIdentifier.setModifyOn(new Timestamp(new Date().getTime()));
			dtoStudyIdentifier.setTagId(docMgmtImpl.getMaxTagId(dtoStudyIdentifier.getWorkspaceId(),dtoStudyIdentifier.getNodeId())+1);
			dtoStudyIdentifier.setTagName("title");
			dtoStudyIdentifier.setAttrName("");
			dtoStudyIdentifier.setAttrValue("");
			dtoStudyIdentifier.setNodeContent(studytitle);
			docMgmtImpl.insertIntoSTFStudyIdentifierMst(dtoStudyIdentifier,1);
			dtoStudyIdentifier.setTagId(docMgmtImpl.getMaxTagId(dtoStudyIdentifier.getWorkspaceId(),dtoStudyIdentifier.getNodeId()) +1);
			dtoStudyIdentifier.setTagName("study-id");
			dtoStudyIdentifier.setNodeContent(uniqueStudyIdentifier);
			docMgmtImpl.insertIntoSTFStudyIdentifierMst(dtoStudyIdentifier,1);
			
		
			//	System.out.println("Inserting node history...");
			DTOWorkSpaceNodeHistory nodeHistory=new DTOWorkSpaceNodeHistory();
			nodeHistory.setWorkSpaceId(workspaceID);
			nodeHistory.setNodeId(NewSTFXMLNodeId);
			
			Integer tranNo=docMgmtImpl.getNewTranNo(workspaceID);
			
			nodeHistory.setTranNo(tranNo.intValue());
			nodeHistory.setFileName(newSTFFileName);
			nodeHistory.setFileType("xml");
			nodeHistory.setRequiredFlag('Y');
			nodeHistory.setRemark("");
			nodeHistory.setModifyBy(userId);
			nodeHistory.setStatusIndi('N');
			nodeHistory.setDefaultFileFormat("");
			nodeHistory.setFolderName("");
			nodeHistory.setStageId(10); // created stage
			//System.out.println("......Calling insertNodeHistory......");
			
			docMgmtImpl.insertNodeHistory(nodeHistory);
			
			//inserting workspace node attributes
			Vector<DTOAttributeMst> AttributeSTF=docMgmtImpl.getAllAttributeForSTF();
			Vector<DTOWorkSpaceNodeAttrHistory> attrHistory=new Vector<DTOWorkSpaceNodeAttrHistory>();
			
			for(int i=0;i<AttributeSTF.size();i++){
				
				//inserting workspacenodeattrdetail
				DTOAttributeMst dtoAttr=(DTOAttributeMst)AttributeSTF.get(i);
				DTOWorkSpaceNodeAttrDetail dtoNodeAttr=new DTOWorkSpaceNodeAttrDetail();
				dtoNodeAttr.setWorkspaceId(workspaceID);
				dtoNodeAttr.setNodeId(NewSTFXMLNodeId);
				dtoNodeAttr.setAttrId(dtoAttr.getAttrId());
				dtoNodeAttr.setAttrName(dtoAttr.getAttrName());
				
				if(dtoAttr.getAttrName().equalsIgnoreCase("version")) {
					dtoNodeAttr.setAttrValue("STF Version 2.2");
				}
				else if(dtoAttr.getAttrName().equalsIgnoreCase("application-version")){
					dtoNodeAttr.setAttrValue("");
				}
				else {
					dtoNodeAttr.setAttrValue(dtoAttr.getAttrValue());
				}
				dtoNodeAttr.setStatusIndi('N');
				dtoNodeAttr.setAttrForIndi(dtoAttr.getAttrForIndiId());
				dtoNodeAttr.setRemark(dtoAttr.getRemark());
				dtoNodeAttr.setModifyBy(userId);
				docMgmtImpl.insertWorkspaceNodeAttrDetail(dtoNodeAttr);
				
				
				//Adding values into vector for workspacenodeattrhistory
				DTOWorkSpaceNodeAttrHistory dtoAttrHistory=new DTOWorkSpaceNodeAttrHistory();
				dtoAttrHistory.setWorkSpaceId(workspaceID);
				dtoAttrHistory.setNodeId(NewSTFXMLNodeId);
				dtoAttrHistory.setTranNo(tranNo.intValue());
				dtoAttrHistory.setAttrId(dtoAttr.getAttrId());
				
				if(dtoAttr.getAttrName().equalsIgnoreCase("version")) {
					dtoAttrHistory.setAttrValue("STF Version 2.2");
				}
				else if(dtoAttr.getAttrName().equalsIgnoreCase("application-version")){
					dtoAttrHistory.setAttrValue("");
				}
				else {
					dtoAttrHistory.setAttrValue(dtoAttr.getAttrValue());
				}
				dtoAttrHistory.setModifyBy(userId);
				attrHistory.add(dtoAttrHistory);
				dtoAttrHistory=null;
			
			}//for end
			
			//inserting workspacenodeattrhistory
			docMgmtImpl.InsertUpdateNodeAttrHistory(attrHistory);
		
			//System.out.println("Inserting node version history...");
			DTOWorkSpaceNodeVersionHistory nodeVersionHistory=new DTOWorkSpaceNodeVersionHistory();
			nodeVersionHistory.setWorkspaceId(workspaceID);
			nodeVersionHistory.setNodeId(NewSTFXMLNodeId);
			nodeVersionHistory.setTranNo(tranNo.intValue());
			nodeVersionHistory.setFileVersionId("0001");
			nodeVersionHistory.setPublished('N');
			nodeVersionHistory.setDownloaded('N');
			nodeVersionHistory.setModifyBy(userId);
			nodeVersionHistory.setExecutedBy(userId);
			nodeVersionHistory.setExecutedOn(new Timestamp(new Date().getTime()));
			nodeVersionHistory.setModifyOn(new Timestamp(new Date().getTime()));
			nodeVersionHistory.setUserDefineVersionId("A-1");
			docMgmtImpl.insertWorkspaceNodeVersionHistory(nodeVersionHistory);
						
			// insert into workspaceuserrightsmst 
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setWorkSpaceId(workspaceID);
			dto.setNodeId(NewSTFXMLNodeId);
			dto.setUserGroupCode(userGroupCode);
			dto.setUserCode(userId);
			docMgmtImpl.updateWorkSpaceUserRights(dto);
		}
		
		return SUCCESS;

	}
	
	public String editSTFDetails(){
		
		if(STFXMLNodeName!=null && !STFXMLNodeName.equals("") 
				&& uniqueStudyIdentifier!=null && !uniqueStudyIdentifier.equals("")){
		
			String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
			int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			
			String XMLNodeName = STFXMLNodeName;
			
			String newSTFFileName ="stf-" + uniqueStudyIdentifier + ".xml";
			
			//Editing STF XML Node
			DTOWorkSpaceNodeDetail dtoNodeDetail= (DTOWorkSpaceNodeDetail)docMgmtImpl.getNodeDetail(workspaceID, STFXMLNodeId).get(0);
			
			dtoNodeDetail.setNodeDisplayName(XMLNodeName);
			dtoNodeDetail.setNodeName(XMLNodeName);
			dtoNodeDetail.setFolderName(newSTFFileName);
			dtoNodeDetail.setModifyBy(userId);
			dtoNodeDetail.setModifyOn(new Timestamp(new Date().getTime()));
			
			docMgmtImpl.insertWorkspaceNodeDetail(dtoNodeDetail,2);//edit node
			
			//Editing NodeContent in STFStudyIdentifierMst	
			Vector studyIdentifier = docMgmtImpl.getSTFIdentifierByNodeId(workspaceID, STFXMLNodeId);
			DTOSTFStudyIdentifierMst dtoStudyIdentifier=(DTOSTFStudyIdentifierMst)studyIdentifier.get(0);
			dtoStudyIdentifier.setModifyBy(userId);
			dtoStudyIdentifier.setNodeContent(studytitle);
			docMgmtImpl.insertIntoSTFStudyIdentifierMst(dtoStudyIdentifier,2);//edit
				
			dtoStudyIdentifier=(DTOSTFStudyIdentifierMst)studyIdentifier.get(1);
			
			dtoStudyIdentifier.setModifyBy(userId);
			dtoStudyIdentifier.setNodeContent(uniqueStudyIdentifier);
			docMgmtImpl.insertIntoSTFStudyIdentifierMst(dtoStudyIdentifier,2);//edit
		}	
	
		return SUCCESS;
	}
	
	public String addSTFCategory(){
		
		if(!categoryValue.equals("-1")){
			
			String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
			int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			
			
			DTOSTFStudyIdentifierMst dtoStudyIdentifier=new DTOSTFStudyIdentifierMst();
			dtoStudyIdentifier.setWorkspaceId(workspaceID);
			dtoStudyIdentifier.setNodeId(STFXMLNodeId);
			dtoStudyIdentifier.setModifyBy(userId);
			
			String[] currentCategory = categoryValue.split(",");
			dtoStudyIdentifier.setTagId(docMgmtImpl.getMaxTagId(dtoStudyIdentifier.getWorkspaceId(),dtoStudyIdentifier.getNodeId()) +1);
			dtoStudyIdentifier.setTagName("category");
			dtoStudyIdentifier.setAttrName("name");
			dtoStudyIdentifier.setAttrValue(currentCategory[3]);// Category Name
			dtoStudyIdentifier.setNodeContent(currentCategory[0]);//Category Value
			
			docMgmtImpl.insertIntoSTFStudyIdentifierMst(dtoStudyIdentifier,1);
			
			dtoStudyIdentifier.setAttrName(currentCategory[1]);//Category Attribute Name e.g. "info-type"
			dtoStudyIdentifier.setAttrValue(currentCategory[2]);//Category Attribute Value e.g "ich", "us", etc...
								
			docMgmtImpl.insertIntoSTFStudyIdentifierMst(dtoStudyIdentifier,1);
		}
		return SUCCESS;
	}
	
	public String addSTFNode()
	{
		
		if(!STFNodeId.equals("-1")){
			
			String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
			int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
			
			String[] stfNodeData=STFNodeId.split(",");//STFNodeId=id,nodeCategory(Type),multiple,nodeName
			//String stfNodeId = stfNodeData[0];
			//String nodeType = stfNodeData[1];
			String isMultiple = stfNodeData[2];
			String STFNodeName = stfNodeData[3];
			
			
			if(isMultiple.equals("N")) {//inserting non-multiple type STF Nodes
				//if node already exists then create clone of the same.
				ArrayList<String> fileNames = new ArrayList<String>();
				int fileNameSuffixInt = 0,maxNodeNo = 0;
				String newFileName=STFNodeName,newNodeDisplayName=STFNodeName;
				char cloneFlag ='N';//By default Original Node (Not clone)
				
				Vector<DTOWorkSpaceNodeDetail> originalNodeWithAllClonesList = getOriginalNodeWithAllclones(workspaceID, nodeId,STFNodeName);
				if(originalNodeWithAllClonesList.size() > 0){
					//Get All siblings file names without extensions
					for (DTOWorkSpaceNodeDetail dtoWorkSpaceNodeDetail : originalNodeWithAllClonesList) {
						if(dtoWorkSpaceNodeDetail.getCloneFlag() == 'Y'){
							String fileName = dtoWorkSpaceNodeDetail.getFolderName();
							int lastIndexOfDot = fileName.lastIndexOf(".");
							if(lastIndexOfDot != -1)
								fileName = fileName.substring(0, lastIndexOfDot);
							fileNames.add(fileName);
						}
						//Set nodeNo for adding current node in between of other STF node.
						//System.out.println("dtoWorkSpaceNodeDetail.getNodeNo():::"+dtoWorkSpaceNodeDetail.getNodeNo());
						if(maxNodeNo < dtoWorkSpaceNodeDetail.getNodeNo())
							maxNodeNo = dtoWorkSpaceNodeDetail.getNodeNo();
					}
					//Increment maxNodeNo by One to get new nodeNo
					if(maxNodeNo != 0){
						maxNodeNo = maxNodeNo + 1;
					}
					//Sort File Names
					//Collections.sort(fileNames);
					
					//Get suffix and add 1(one) to it
					for (String filename : fileNames) {
						String[] suffix = filename.split("-");
						String fileNameSuffix = suffix[suffix.length-1];
						try {
							fileNameSuffixInt = Integer.parseInt(fileNameSuffix);
							fileNameSuffixInt++;
						} catch (NumberFormatException e) {}
					}
					//Create New Node Display Name and File Name 
					if(fileNameSuffixInt != 0){
						newFileName = STFNodeName + "-"+fileNameSuffixInt;
						newNodeDisplayName = STFNodeName + " "+fileNameSuffixInt;
						
					}else{
						newFileName = STFNodeName + "-1";
						newNodeDisplayName = STFNodeName + " 1";
					}
					//Set clone flag to 'Y'(true)
					cloneFlag = 'Y';
				}
				int newNodeId = createWorkspaceSTFChildNode(workspaceID, nodeId, STFNodeName, newNodeDisplayName, newFileName,'S', userGroupCode,userId,cloneFlag);
				
				//Re-indexing of newly created node if it is a clone node.
				//System.out.println("workspaceID:::"+workspaceID+", nodeId"+nodeId+", maxNodeNo"+ maxNodeNo);
				if(cloneFlag == 'Y'){
					docMgmtImpl.updateNodeNo(workspaceID, newNodeId, maxNodeNo);
				}
				
			}
			else {//inserting multiple type STF Nodes
				
				String datasetsFolderName="";
				String ChangeFolderName="";
				if(STFNodeName.equalsIgnoreCase("datasets") || STFNodeName.equalsIgnoreCase("data-tabulation-dataset") || STFNodeName.equalsIgnoreCase("data-listing-dataset") || STFNodeName.equalsIgnoreCase("analysis-dataset")
						||STFNodeName.equalsIgnoreCase("data-tabulation-dataset-sdtm") || STFNodeName.equalsIgnoreCase("data-tabulation-dataset-send") ||  STFNodeName.equalsIgnoreCase("analysis-data-definition") 
						|| STFNodeName.equalsIgnoreCase("analysis-dataset-adam") || STFNodeName.equalsIgnoreCase("data-listing-data-definition") ||  STFNodeName.equalsIgnoreCase("data-tabulation-data-definition")){
					/*if(!adamlegacy.equalsIgnoreCase("-1")){
						adamleg=adamlegacy;
					}else if(!analysis.equalsIgnoreCase("-1")){
						analys=analysis;
					}else if(!tabulations.equalsIgnoreCase("-1")){
						tabul=tabulations;
					}*/
					if(tabulations.equalsIgnoreCase("-1")){
						if(!analysis.equalsIgnoreCase("-1")){
							datasetsFolderName+=analysis+"/";
							if(!adamlegacy.equalsIgnoreCase("-1")){
								datasetsFolderName+=adamlegacy+"/";
							}
						}
					}else{
						
						if(!tabulations.equalsIgnoreCase("-1")){
							datasetsFolderName+=tabulations+"/";
						}
					}
					if(!datasetsSel.equalsIgnoreCase("-1")){
						datasetsFolderName = datasetsFolderName.substring(0, datasetsFolderName.length() - 1);
						ChangeFolderName="/"+datasetsSel+"/"+datasetsFolderName;
					}
					System.out.println("ChangeFolderName->"+ChangeFolderName);
					System.out.println("datasetsSel->"+datasetsSel);
					System.out.println("analysis->"+analysis);
					System.out.println("adamlegacy->"+adamlegacy);
					System.out.println("tabulations->"+tabulations);
					System.out.println("uniqueStudyIdentifier:"+uniqueStudyIdentifier);
				}
				
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
				if(!multipleTypeNodeName.equals("") && !numberOfRepetitions.equals("") && !siteId.equals("")){
					
					int STFMultipleTypeParentNodeId = 0;//STF Child NodeId (Multiple Type Parent Node Id) in WorkspaceNodeDetail Table
					
					/*Find STF Child NodeId (Multiple Type Parent Node Id) from WorkspaceNodeDetail Table
					 *'attachedMultipleTypeSTFChildNodeIds' member variable is  a comma-separated-value of nodeIds from workspaceNodeDetail of 
					 * already attached multiple type STF nodes.
					 * e.g. parent node of case-record-forms
					 * It is filled up in AttachSTFAction.java */
					if(!attachedMultipleTypeSTFChildNodeIds.equals("")) {
						
						//if selected STF Node is already there in the STF
						String[] multipleTypeNodeIds = attachedMultipleTypeSTFChildNodeIds.split(",");
						for(int m = 0; m < multipleTypeNodeIds.length; m++) {
							
							DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail)docMgmtImpl.getNodeDetail(workspaceID, Integer.parseInt(multipleTypeNodeIds[m])).get(0);
							
							if(nodeDetail.getNodeName().equalsIgnoreCase(STFNodeName) && (nodeDetail.getNodeName().equalsIgnoreCase("datasets") || nodeDetail.getNodeName().equalsIgnoreCase("data-tabulation-dataset") || nodeDetail.getNodeName().equalsIgnoreCase("data-listing-dataset")
									|| nodeDetail.getNodeName().equalsIgnoreCase("analysis-dataset")|| nodeDetail.getNodeName().equalsIgnoreCase("data-tabulation-dataset-sdtm")|| nodeDetail.getNodeName().equalsIgnoreCase("data-tabulation-dataset-send")
									|| nodeDetail.getNodeName().equalsIgnoreCase("analysis-dataset-adam")|| nodeDetail.getNodeName().equalsIgnoreCase("analysis-data-definition") || nodeDetail.getNodeName().equalsIgnoreCase("data-tabulation-data-definition")
									|| nodeDetail.getNodeName().equalsIgnoreCase("data-listing-data-definition"))) {
								
								//DTOWorkSpaceNodeDetail dtoNodeDetail= new DTOWorkSpaceNodeDetail();
								if(!datasetsSel.equalsIgnoreCase("-1")){
									nodeDetail.setFolderName(STFNodeName+ChangeFolderName);
									nodeDetail.setModifyBy(userId);
									nodeDetail.setModifyOn(new Timestamp(new Date().getTime()));
									
									docMgmtImpl.insertWorkspaceNodeDetail(nodeDetail,2);//edit node
								}
							}
							
							if(nodeDetail.getNodeName().equalsIgnoreCase(STFNodeName)) {
								STFMultipleTypeParentNodeId = nodeDetail.getNodeId();
								break;
							}
						}
					}
					//if selected STF Node is not in the STF then first create it
					if(STFMultipleTypeParentNodeId == 0) {
						String fileName="";
						// Creating Multiple Type Parent Node 
						System.out.println("CRF->"+STFNodeName);
						
						if(STFNodeName.equalsIgnoreCase("case-report-forms")) {// hard-coded FolderName for CRFs
							fileName = "crf";
						}
						else if(STFNodeName.equalsIgnoreCase("datasets")){//hard-coded Foldername for data-tabulation-data
							fileName = "datasets";
						}
						else if(STFNodeName.equalsIgnoreCase("data-tabulation-dataset")){//hard-coded Foldername for data-tabulation-data
							fileName = "datasets";
						}
						else if(STFNodeName.equalsIgnoreCase("analysis-dataset")){//hard-coded Foldername for data-tabulation-data
							fileName = "datasets";
						}
						else if(STFNodeName.equalsIgnoreCase("data-listing-dataset")){//hard-coded Foldername for data-listing-data
							fileName = "datasets";
						}
						else if(STFNodeName.equalsIgnoreCase("data-tabulation-dataset-sdtm")){//hard-coded Foldername for data-listing-data
							fileName = "datasets";
						}
						else if(STFNodeName.equalsIgnoreCase("data-tabulation-dataset-send")){//hard-coded Foldername for data-listing-data
							fileName = "datasets";
						}
						else if(STFNodeName.equalsIgnoreCase("analysis-dataset-adam")){//hard-coded Foldername for data-listing-data
							fileName = "datasets";
						}
						else if(STFNodeName.equalsIgnoreCase("analysis-data-definition")){//hard-coded Foldername for data-listing-data
							fileName = "datasets";
						}
						else if(STFNodeName.equalsIgnoreCase("data-tabulation-data-definition")){//hard-coded Foldername for data-definition
							fileName = "datasets";
						}
						else if(STFNodeName.equalsIgnoreCase("data-listing-data-definition")){//hard-coded Foldername for data-definition
							fileName = "datasets";
						}
						else {
							fileName = STFNodeName;
						}
						if(fileName.equalsIgnoreCase("datasets")){
							STFMultipleTypeParentNodeId = createWorkspaceSTFChildNode(workspaceID, nodeId, STFNodeName, STFNodeName, fileName+ChangeFolderName,'F', userGroupCode,userId,'N');
						}else{
							STFMultipleTypeParentNodeId = createWorkspaceSTFChildNode(workspaceID, nodeId, STFNodeName, STFNodeName, fileName,'F', userGroupCode,userId,'N');
						}
						
					}
					
					Vector<DTOWorkSpaceNodeDetail> stfMultipleTypeChildren=docMgmtImpl.getChildNodeByParent(STFMultipleTypeParentNodeId,workspaceID);					
					
					for(int i= startIndex ; i < startIndex + numOfRepetition ; i++) 
					{
						boolean isChildPresent=false;
						int newChildNodeId=0;
						for (int z=0;z<stfMultipleTypeChildren.size();z++)
						{
							DTOWorkSpaceNodeDetail dtoWorkSpaceNodeDetail=stfMultipleTypeChildren.get(z);
							if (dtoWorkSpaceNodeDetail.getFolderName().equals(multipleTypeNodeName+"-"+(i)))
								isChildPresent=true;
						}
						if (isChildPresent)
							continue;
						else	
							newChildNodeId = createWorkspaceSTFChildNode(workspaceID, STFMultipleTypeParentNodeId, STFNodeName, multipleTypeNodeName+"-"+(i), multipleTypeNodeName+"-"+(i),'S',userGroupCode, userId,'N');
						
						DTOWorkSpaceNodeDetail dtoNodeDtl=new DTOWorkSpaceNodeDetail();
						dtoNodeDtl.setWorkspaceId(workspaceID);
						dtoNodeDtl.setNodeId(newChildNodeId);
						dtoNodeDtl.setFolderName(multipleTypeNodeName+"-"+(i));
						stfMultipleTypeChildren.add(dtoNodeDtl);
						
						DTOSTFStudyIdentifierMst property=new DTOSTFStudyIdentifierMst();
						property.setWorkspaceId(workspaceID);
						property.setNodeId(newChildNodeId);
						property.setModifyBy(userId);
						property.setTagId(docMgmtImpl.getMaxTagId(property.getWorkspaceId(),property.getNodeId()) +1);
						property.setTagName("property");
						property.setAttrName("name");
						property.setAttrValue("site-identifier");
						property.setNodeContent(siteId);//Property Tag Value
						docMgmtImpl.insertIntoSTFStudyIdentifierMst(property,1);
						
						property.setAttrName("info-type");
						property.setAttrValue("us");
						docMgmtImpl.insertIntoSTFStudyIdentifierMst(property,1);											
					}
					
					//this is the new node list which will have all the nodes of 
					//stfMultipleTypeChildren (old list) but in sorted order
					Vector<DTOWorkSpaceNodeDetail> nodeList=new Vector<DTOWorkSpaceNodeDetail>();
					
					//this flag indicates whether a node has been transferred from old to new list
					//this flag is set to false initially
					Vector<Boolean> nodeListFlag=new Vector<Boolean>();
					for (int indSetInitialVal=0;indSetInitialVal<stfMultipleTypeChildren.size();nodeListFlag.add(false),indSetInitialVal++);
					
					//this loop would first add all the nodes of old list which dont have numbers in it
					//or may be they were edited later
					while(true)
					{
						boolean nodeFound=false;
						int indFindNode;
						
						//find a node having folder name without a number
						for (indFindNode=0;indFindNode<stfMultipleTypeChildren.size();indFindNode++)
						{
							if (nodeListFlag.get(indFindNode).booleanValue())
								continue;
							String tmp;
							tmp=stfMultipleTypeChildren.get(indFindNode).getFolderName();							
							tmp=tmp.substring(tmp.lastIndexOf('-')+1).trim();
							try
							{
								Integer.parseInt(tmp);
							}
							catch(Exception e)
							{
								nodeFound=true;
								break;
							}
						}
						if (!nodeFound)
							break;
						
						//node is found, add it to the new list
						DTOWorkSpaceNodeDetail dto=new DTOWorkSpaceNodeDetail();
						dto.setNodeId(stfMultipleTypeChildren.get(indFindNode).getNodeId());
						dto.setWorkspaceId(stfMultipleTypeChildren.get(indFindNode).getWorkspaceId());
						nodeList.add(dto);
						
						//update flag
						nodeListFlag.set(indFindNode,true);
					}
					
					//this loop would add all the nodes of old list which have numbers in it
					//from all such nodes, find the minimum one
					//add to the new list
					//update the flag, so that it is not considered in the next iteration
					while(nodeList.size()<stfMultipleTypeChildren.size())
					{
						int tmpNo=-1;
						int tmpIdx=-1;
						int indFindNode;
						
						//finds the node with minimum no
						//tmpNo has the no
						//tmpIdx has the index of min no
						for (indFindNode=0;indFindNode<stfMultipleTypeChildren.size();indFindNode++)
						{
							if (nodeListFlag.get(indFindNode).booleanValue())
								continue;
							String tmp;
							if (tmpNo<0)
							{
								tmp=stfMultipleTypeChildren.get(indFindNode).getFolderName();							
								tmp=tmp.substring(tmp.lastIndexOf('-')+1).trim();
								tmpNo=Integer.parseInt(tmp);
								tmpIdx=indFindNode;
								continue;
							}
							tmp=stfMultipleTypeChildren.get(indFindNode).getFolderName();							
							tmp=tmp.substring(tmp.lastIndexOf('-')+1).trim();
							if (tmpNo>0 && Integer.parseInt(tmp)<tmpNo)
							{							
								tmpNo=Integer.parseInt(tmp);
								tmpIdx=indFindNode;
							}							
						}

						//min no is found
						if (tmpIdx>=0 && tmpNo>0)
						{
							DTOWorkSpaceNodeDetail dto=new DTOWorkSpaceNodeDetail();
							dto.setNodeId(stfMultipleTypeChildren.get(tmpIdx).getNodeId());
							dto.setWorkspaceId(stfMultipleTypeChildren.get(tmpIdx).getWorkspaceId());
							nodeList.add(dto);
							nodeListFlag.set(tmpIdx,true);
						}
						else
							break;
					}
					
					//now set the proper node no as the nodes are in new list
					for (int indUpdateNodeNo=0;indUpdateNodeNo<nodeList.size();indUpdateNodeNo++)
						nodeList.get(indUpdateNodeNo).setNodeNo(indUpdateNodeNo+1);

					//actual update of node no in database
					docMgmtImpl.updateNodeNo(nodeList);
				}				
			}
		}
		return SUCCESS;
	}
	
	public String removeCategory() {
		
		if(removeCategoryNodeId !=0 && removetagId!=0) {
			
			String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
						
			DTOSTFStudyIdentifierMst dtoStudyIdentifier=new DTOSTFStudyIdentifierMst();
			dtoStudyIdentifier.setWorkspaceId(workspaceID);
			dtoStudyIdentifier.setNodeId(removeCategoryNodeId);
			dtoStudyIdentifier.setTagId(removetagId);
			
			docMgmtImpl.insertIntoSTFStudyIdentifierMst(dtoStudyIdentifier,3);//delete mode
			
		}
		
		return SUCCESS;
	}

	public String removeNode() {
		
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
					
		//Removing child nodes (if any) before removing the node itself
		Vector<DTOWorkSpaceNodeDetail> childNodes = docMgmtImpl.getChildNodeByParent(removeNodeId, workspaceID);
		if(childNodes.size() >0) {
			
			for(int i=0 ; i< childNodes.size();i++) {
				
				DTOWorkSpaceNodeDetail wsnd = childNodes.get(i);
				//docMgmtImpl.deleteNodeDetail(workspaceID, wsnd.getNodeId());
				
				DTOSTFStudyIdentifierMst dtoStudyIdentifier=new DTOSTFStudyIdentifierMst();
				dtoStudyIdentifier.setWorkspaceId(workspaceID);
				dtoStudyIdentifier.setNodeId(wsnd.getNodeId());
				dtoStudyIdentifier.setTagId(1);
				
				docMgmtImpl.insertIntoSTFStudyIdentifierMst(dtoStudyIdentifier,3);//delete mode
				
			}
		}
		
		//Removing the node itself
		docMgmtImpl.deleteNodeDetail(workspaceID, removeNodeId,"");
		
		DTOSTFStudyIdentifierMst dtoStudyIdentifier=new DTOSTFStudyIdentifierMst();
		dtoStudyIdentifier.setWorkspaceId(workspaceID);
		dtoStudyIdentifier.setNodeId(removeNodeId);
		dtoStudyIdentifier.setTagId(1);
		
		docMgmtImpl.insertIntoSTFStudyIdentifierMst(dtoStudyIdentifier,3);//delete mode
		
		return SUCCESS;
	}
	
	public String removeSTF() {
		
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		
		
		//updating parent node where STF is Attached. Set NodeTypeIndi again to 'N'.
		Vector<DTOWorkSpaceNodeDetail> parentNodeDtl=docMgmtImpl.getNodeDetail(workspaceID,nodeId);
		DTOWorkSpaceNodeDetail dtoNodeDetail=new DTOWorkSpaceNodeDetail();
		dtoNodeDetail=parentNodeDtl.get(0);
		//dtoNodeDetail.setWorkspaceId(workspaceID);
		dtoNodeDetail.setNodeTypeIndi('N');
		dtoNodeDetail.setFolderName(dtoNodeDetail.getNodeDisplayName().trim().toLowerCase().replace(" ", "-")+".pdf");
		//
		docMgmtImpl.insertWorkspaceNodeDetail(dtoNodeDetail,2); //edit mode
				
		//STF Remove code starts here
		
		//fetching all STF nodes attached to the parent node
		Vector<DTOWorkSpaceNodeDetail> STFChildNodes = docMgmtImpl.getChildNodeByParent(nodeId, workspaceID);
		
		//removing the first child node i.e. STF XML Node where stf.xml file is attached
		DTOWorkSpaceNodeDetail STFXMLNode = STFChildNodes.remove(0);
		Vector studyIdentifier = docMgmtImpl.getSTFIdentifierByNodeId(workspaceID, STFXMLNode.getNodeId());
		int previousTagId=0;
		
		//delete from STFStudyIdentifierMst
		for(int i = 0; i < studyIdentifier.size(); i++) {
			
			DTOSTFStudyIdentifierMst currentTag = (DTOSTFStudyIdentifierMst)studyIdentifier.get(i);
			if(previousTagId != currentTag.getTagId()) {
				docMgmtImpl.insertIntoSTFStudyIdentifierMst(currentTag,3);//delete mode
			}
			previousTagId = currentTag.getTagId();
		}
		
		//delete STF XML Node from workspace	
		docMgmtImpl.deleteNodeDetail(workspaceID, STFXMLNode.getNodeId(),"");
		
		
		//deleting all other STF Nodes
		for(int i = 0; i < STFChildNodes.size(); i++) {
			
			DTOWorkSpaceNodeDetail currentChildNode = (DTOWorkSpaceNodeDetail)STFChildNodes.get(i);
			
			//if node is parent node (Here, multiple type STF node)
			if(currentChildNode.getNodeTypeIndi()=='F') {
				
				Vector<DTOWorkSpaceNodeDetail> multipleTypeChildNodes = docMgmtImpl.getChildNodeByParent(currentChildNode.getNodeId(), workspaceID);
				
				//deleting property tag from STFStudyIdentifierMst
				for(int j = 0 ; j < multipleTypeChildNodes.size(); j++) {
					
					DTOWorkSpaceNodeDetail currentMultipleTypeChildNode = multipleTypeChildNodes.get(j);
					
					DTOSTFStudyIdentifierMst dtoStudyIdentifier=new DTOSTFStudyIdentifierMst();
					dtoStudyIdentifier.setWorkspaceId(currentMultipleTypeChildNode.getWorkspaceId());
					dtoStudyIdentifier.setNodeId(currentMultipleTypeChildNode.getNodeId());
					dtoStudyIdentifier.setTagId(1);
					
					docMgmtImpl.insertIntoSTFStudyIdentifierMst(dtoStudyIdentifier,3);//delete mode
				}
			}
			
			//deleting currentChildNode and its children from workspace
			docMgmtImpl.deleteNodeDetail(workspaceID, currentChildNode.getNodeId(),"");
		
		}
				
		return SUCCESS;
	}
	
	public int createWorkspaceSTFChildNode(String workspaceID,int parentNodeId, String STFNodeName,String STFNodeDisplayName,String STFNodeFolderName,char nodeTypeIndi,int userGroupCode,int userId,char cloneFlag){
		
		int nodeNo = docMgmtImpl.getmaxNodeNo(workspaceID,parentNodeId);
		nodeNo= nodeNo + 1;
		
		DTOWorkSpaceNodeDetail dtoNodeDtl=new DTOWorkSpaceNodeDetail();
		
		int newNodeId= docMgmtImpl.getmaxNodeId(workspaceID)+ 1;
		
		dtoNodeDtl.setWorkspaceId(workspaceID);
		dtoNodeDtl.setNodeId(newNodeId);
		dtoNodeDtl.setNodeNo(nodeNo);
		dtoNodeDtl.setNodeDisplayName(STFNodeDisplayName);
		dtoNodeDtl.setFolderName(STFNodeFolderName);
		dtoNodeDtl.setNodeName(STFNodeName);
		dtoNodeDtl.setParentNodeId(parentNodeId);
		dtoNodeDtl.setCloneFlag(cloneFlag);
		dtoNodeDtl.setNodeTypeIndi(nodeTypeIndi);
		dtoNodeDtl.setRequiredFlag('N');
		dtoNodeDtl.setCheckOutBy(0);
		dtoNodeDtl.setPublishedFlag('Y');
		dtoNodeDtl.setRemark("");
		dtoNodeDtl.setModifyBy(userId);
		dtoNodeDtl.setModifyOn(new Timestamp(new Date().getTime()));
		
		docMgmtImpl.insertWorkspaceNodeDetail(dtoNodeDtl,1);
		
		//insert into workspacenodeattrdetail
		Vector<DTOAttributeMst> AttributeSTF=docMgmtImpl.getAllAttributeForSTF();
		
		DTOWorkSpaceNodeAttrDetail dtoNodeAttr=new DTOWorkSpaceNodeAttrDetail();
		dtoNodeAttr.setWorkspaceId(workspaceID);
		dtoNodeAttr.setNodeId(newNodeId);
		dtoNodeAttr.setAttrId(-999);
		dtoNodeAttr.setAttrName("FileLastModified");
		dtoNodeAttr.setAttrValue("");
		dtoNodeAttr.setStatusIndi('N');
		dtoNodeAttr.setAttrForIndi("0001");
		dtoNodeAttr.setRemark("");
		dtoNodeAttr.setModifyBy(userId);
		docMgmtImpl.insertWorkspaceNodeAttrDetail(dtoNodeAttr);
		dtoNodeAttr = null;
		
		for(int i=0;i<AttributeSTF.size();i++){
			DTOAttributeMst dtoAttr=(DTOAttributeMst)AttributeSTF.get(i);
			dtoNodeAttr=new DTOWorkSpaceNodeAttrDetail();
			
			dtoNodeAttr.setWorkspaceId(workspaceID);
			dtoNodeAttr.setNodeId(newNodeId);
			dtoNodeAttr.setAttrId(dtoAttr.getAttrId());
			dtoNodeAttr.setAttrName(dtoAttr.getAttrName());
			dtoNodeAttr.setAttrValue(dtoAttr.getAttrValue());
			dtoNodeAttr.setStatusIndi('N');
			dtoNodeAttr.setAttrForIndi(dtoAttr.getAttrForIndiId());
			dtoNodeAttr.setRemark(dtoAttr.getRemark());
			dtoNodeAttr.setModifyBy(userId);
			docMgmtImpl.insertWorkspaceNodeAttrDetail(dtoNodeAttr);
			dtoNodeAttr = null;
		}//for end
		
		
		//Inserting workspace user rights for newly created node
		DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
		dto.setWorkSpaceId(workspaceID);
		dto.setNodeId(newNodeId);
		dto.setUserGroupCode(userGroupCode);
		dto.setUserCode(userId);
		
		docMgmtImpl.updateWorkSpaceUserRights(dto);
		
		
		return newNodeId;
	}
	private Vector<DTOWorkSpaceNodeDetail> getOriginalNodeWithAllclones(String workspaceID,int stfParentNodeId,String STFNodeName) {
		Vector<DTOWorkSpaceNodeDetail> originalNodeWithAllclones = new Vector<DTOWorkSpaceNodeDetail>();
		Vector<DTOWorkSpaceNodeDetail> repeatNodeAndSiblingsDtl = docMgmtImpl.getChildNodeByParent(stfParentNodeId, workspaceID);
		for(int i = 0; i < repeatNodeAndSiblingsDtl.size(); i++) {
			DTOWorkSpaceNodeDetail currentSibling = repeatNodeAndSiblingsDtl.get(i);
			//System.out.println(currentSibling.getNodeName());
			if(currentSibling.getNodeName().equalsIgnoreCase(STFNodeName)) {
				originalNodeWithAllclones.addElement(repeatNodeAndSiblingsDtl.get(i));
			}
		}
		
		return originalNodeWithAllclones;
	}
	
	public String datasetsSel;
	public String analysis;
	public String adamlegacy;
	public String tabulations;
	
	
	public int nodeId;//STF Parent nodeId
	public String STFXMLNodeName;
	public String studytitle;
	public String uniqueStudyIdentifier;
	public String STFNodeName;
	
	public String STFNodeId;
	public int STFXMLNodeId;
	public String categoryValue;
	public String multipleTypeNodeName;
	public String numberOfRepetitions;
	public String siteId;
	public String attachedMultipleTypeSTFChildNodeIds ="";
	public int removeNodeId = 0;
	public int removeCategoryNodeId = 0;
	public int removetagId = 0;
	public String suffixStart;
	
	
	public String getSTFNodeName() {
		return STFNodeName;
	}

	public void setSTFNodeName(String nodeName) {
		STFNodeName = nodeName;
	}

	public String getUniqueStudyIdentifier() {
		return uniqueStudyIdentifier;
	}
	public void setUniqueStudyIdentifier(String uniqueStudyIdentifier) {
		this.uniqueStudyIdentifier = uniqueStudyIdentifier;
	}

	public String getStudytitle() {
		return studytitle;
	}
	public void setStudytitle(String studytitle) {
		this.studytitle = studytitle;
	}
	
	public String getSTFXMLNodeName() {
		return STFXMLNodeName;
	}
	public void setSTFXMLNodeName(String STFXMLNodeName) {
		this.STFXMLNodeName = STFXMLNodeName;
	}

	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getSTFNodeId() {
		return STFNodeId;
	}
	public void setSTFNodeId(String nodeId) {
		STFNodeId = nodeId;
	}
	public int getSTFXMLNodeId() {
		return STFXMLNodeId;
	}
	public void setSTFXMLNodeId(int nodeId) {
		STFXMLNodeId = nodeId;
	}
	public String getCategoryValue() {
		return categoryValue;
	}
	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}
	public String getMultipleTypeNodeName() {
		return multipleTypeNodeName;
	}
	public void setMultipleTypeNodeName(String multipleTypeNodeName) {
		this.multipleTypeNodeName = multipleTypeNodeName;
	}
	public String getNumberOfRepetitions() {
		return numberOfRepetitions;
	}
	public void setNumberOfRepetitions(String numberOfRepetitions) {
		this.numberOfRepetitions = numberOfRepetitions;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getAttachedMultipleTypeSTFChildNodeIds() {
		return attachedMultipleTypeSTFChildNodeIds;
	}
	public void setAttachedMultipleTypeSTFChildNodeIds(
			String attachedMultipleTypeSTFChildNodeIds) {
		this.attachedMultipleTypeSTFChildNodeIds = attachedMultipleTypeSTFChildNodeIds;
	}
	public int getRemoveNodeId() {
		return removeNodeId;
	}
	public void setRemoveNodeId(int removeNodeId) {
		this.removeNodeId = removeNodeId;
	}
	public int getRemoveCategoryNodeId() {
		return removeCategoryNodeId;
	}
	public void setRemoveCategoryNodeId(int removeCategoryNodeId) {
		this.removeCategoryNodeId = removeCategoryNodeId;
	}
	public int getRemovetagId() {
		return removetagId;
	}
	public void setRemovetagId(int removetagId) {
		this.removetagId = removetagId;
	}
	public String getDatasetsSel() {
		return datasetsSel;
	}
	public void setDatasetsSel(String datasetsSel) {
		this.datasetsSel = datasetsSel;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	public String getAdamlegacy() {
		return adamlegacy;
	}
	public void setAdamlegacy(String adamlegacy) {
		this.adamlegacy = adamlegacy;
	}
	public String getTabulations() {
		return tabulations;
	}
	public void setTabulations(String tabulations) {
		this.tabulations = tabulations;
	}
	
		
}//class end
