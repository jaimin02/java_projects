package com.docmgmt.struts.actions.stf;



import java.util.Vector;

import com.docmgmt.dto.DTOSTFCategoryAttrValueMatrix;
import com.docmgmt.dto.DTOSTFCategoryMst;
import com.docmgmt.dto.DTOSTFNodeMst;
import com.docmgmt.dto.DTOSTFStudyIdentifierMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AttachSTFAction extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		
		getSTFNodes = docMgmtImpl.getAllSTFNodes();
		getSTFCategoryDtl = docMgmtImpl.getAllSTFCategory();
		
		//ActionContext.getContext().getSession().remove("NodeDetail");
		
		DTOWorkSpaceNodeDetail nodeDetail = docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0);
		
		
		STFChildNodes = docMgmtImpl.getAllChildSTFNodes(workspaceID, nodeId);
		
		// if selected node has already an STF Attached under it, then it will be opened in Edit Mode
		if(STFChildNodes.size() > 0 && nodeDetail.getNodeTypeIndi()=='T') { 
			
			STFOperation = "Edit";
			
			DTOWorkSpaceNodeDetail STFXMLNode = STFChildNodes.remove(0);
			STFXMLNodeId = STFXMLNode.getNodeId();
			
			//Get data from STFStudyIdentifierMst 
			Vector<DTOSTFStudyIdentifierMst> studyIdentifier = docMgmtImpl.getSTFIdentifierByNodeId(workspaceID, STFXMLNodeId);
			if(studyIdentifier.size()!=0){
				DTOSTFStudyIdentifierMst dtoStudyIdentifier = studyIdentifier.remove(0);
				//Get STF Study Title
				if(dtoStudyIdentifier.getTagName().equalsIgnoreCase("title")){
					
					//STFXMLNodeId = dtosim.getNodeId();
					studytitle = dtoStudyIdentifier.getNodeContent();
					STFXMLNodeName = dtoStudyIdentifier.getNodeContent();
				}
				//Get STF Study Id
				dtoStudyIdentifier = studyIdentifier.remove(0);
				if(dtoStudyIdentifier.getTagName().equalsIgnoreCase("study-id")){
					
					uniqueStudyIdentifier = dtoStudyIdentifier.getNodeContent();
				}
				//Get STF Categories For Display
				STFCategories = studyIdentifier;
				
			}
			
			//Marking if multiple type nodes are already attached
			for(int i=0; i < STFChildNodes.size();i++){
				
				DTOWorkSpaceNodeDetail STFChildNode = STFChildNodes.get(i);
				
				for(int j =0 ; j <getSTFNodes.size();j++){
					
					DTOSTFNodeMst dtoSTFNode = getSTFNodes.get(j);
					
					if(STFChildNode.getNodeName().equalsIgnoreCase(dtoSTFNode.getNodeName())){					
			
						if(dtoSTFNode.getMultiple()=='Y'){
							if(attachedMultipleTypeSTFChildNodeIds.equals("")){
								attachedMultipleTypeSTFChildNodeIds = Integer.toString(STFChildNode.getNodeId());
							}
							else{
								attachedMultipleTypeSTFChildNodeIds += ","+Integer.toString(STFChildNode.getNodeId());
							}
							
							//Fetching child Nodes For the multiple-type STF nodes
							Vector<DTOWorkSpaceNodeDetail> multipleTypeChildNodes = docMgmtImpl.getChildNodeByParent(STFChildNode.getNodeId(), workspaceID);
							
							//Fetching Site Identifier for the child nodes
							for(int k=0; k < multipleTypeChildNodes.size();k++) {
								
								DTOWorkSpaceNodeDetail nodeDto = multipleTypeChildNodes.get(k);
								Vector<DTOSTFStudyIdentifierMst> siteIdVector = docMgmtImpl.getSTFIdentifierByNodeId(workspaceID, nodeDto.getNodeId());
								
								if(siteIdVector.size() > 0) {
									DTOSTFStudyIdentifierMst siteId = siteIdVector.get(0);
									
									nodeDto.setStfNodeSiteIdentifier(siteId.getNodeContent());
								}
									
							}
							
							STFChildNode.setChildNodes(multipleTypeChildNodes);
							/*STFChildNodes.removeElementAt(i);
							STFChildNodes.add(i, STFChildNode);*/
						}
						break;
					}
				}
			}
			
		}
		// if selected node is a leaf node, then one can attach a new STF under the node
		else if(STFChildNodes.size() == 0){
			STFOperation = "Create";
		}
		else {
			STFOperation = "None";
			System.out.println("none");
		}
			
		return SUCCESS;
	}
	
	public String getCategoryValues() {
		
		STFCatValues=docMgmtImpl.getSTFCategoryValues(categoryNameId);
		//System.out.println("selectedcategoryid:::"+categoryNameId);
		//System.out.println("vector size:::"+STFCatValues.size());
		return SUCCESS;
	}
	
	public String getDatasetsPublishPath(){
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		htmlContent = "";
		STFChildNodes = docMgmtImpl.getAllChildSTFNodes(workspaceID, stfNodeId);
		//STFChildNodes.remove(0);
		for(int i=0; i < STFChildNodes.size();i++){
			
			DTOWorkSpaceNodeDetail stfdtl = STFChildNodes.get(i);
			if(stfdtl.getNodeName().equalsIgnoreCase(stfNodeTag)){
				htmlContent+=stfdtl.getNodeName()+":"+stfdtl.getFolderName();
			}
			
			
			
		}
		return "html";
	}
	public String stfNodeTag;
	public int stfNodeId;
	public String htmlContent;
	public Vector<DTOSTFNodeMst> getSTFNodes;
	public Vector<DTOSTFCategoryMst> getSTFCategoryDtl;
	public int nodeId;
	public String categoryName;
	public String categoryValue;
	public Vector<DTOSTFCategoryAttrValueMatrix> STFCatValues;
	public String categoryNameId;
	public String STFOperation;
	public String studytitle;
	public String STFXMLNodeName;
	public String uniqueStudyIdentifier;
	public Vector<DTOWorkSpaceNodeDetail> STFChildNodes;
	public int STFXMLNodeId;
	public Vector<DTOSTFStudyIdentifierMst> STFCategories;
	public String attachedMultipleTypeSTFChildNodeIds ="";//comma-separated-values

	
	public Vector<DTOSTFCategoryAttrValueMatrix> getSTFCatValues() {
		return STFCatValues;
	}

	public void setSTFCatValues(Vector<DTOSTFCategoryAttrValueMatrix> catValues) {
		STFCatValues = catValues;
	}

	public String getCategoryNameId() {
		return categoryNameId;
	}

	public void setCategoryNameId(String categoryNameId) {
		this.categoryNameId = categoryNameId;
	}

	public String getSTFOperation() {
		return STFOperation;
	}

	public void setSTFOperation(String operation) {
		STFOperation = operation;
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

	public void setSTFXMLNodeName(String nodeName) {
		STFXMLNodeName = nodeName;
	}

	public String getUniqueStudyIdentifier() {
		return uniqueStudyIdentifier;
	}

	public void setUniqueStudyIdentifier(String uniqueStudyIdentifier) {
		this.uniqueStudyIdentifier = uniqueStudyIdentifier;
	}

	public Vector<DTOWorkSpaceNodeDetail> getSTFChildNodes() {
		return STFChildNodes;
	}

	public void setSTFChildNodes(Vector<DTOWorkSpaceNodeDetail> childNodes) {
		STFChildNodes = childNodes;
	}

	public int getSTFXMLNodeId() {
		return STFXMLNodeId;
	}

	public void setSTFXMLNodeId(int nodeId) {
		STFXMLNodeId = nodeId;
	}

	public Vector<DTOSTFStudyIdentifierMst> getSTFCategories() {
		return STFCategories;
	}

	public void setSTFCategories(Vector<DTOSTFStudyIdentifierMst> categories) {
		STFCategories = categories;
	}

	public String getAttachedMultipleTypeSTFChildNodeIds() {
		return attachedMultipleTypeSTFChildNodeIds;
	}

	public void setAttachedMultipleTypeSTFChildNodeIds(
			String attachedMultipleTypeSTFChildNodeIds) {
		this.attachedMultipleTypeSTFChildNodeIds = attachedMultipleTypeSTFChildNodeIds;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public Vector<DTOSTFNodeMst> getGetSTFNodes() {
		return getSTFNodes;
	}

	public void setGetSTFNodes(Vector<DTOSTFNodeMst> getSTFNodes) {
		this.getSTFNodes = getSTFNodes;
	}

	public Vector<DTOSTFCategoryMst> getGetSTFCategoryDtl() {
		return getSTFCategoryDtl;
	}

	public void setGetSTFCategoryDtl(Vector<DTOSTFCategoryMst> getSTFCategoryDtl) {
		this.getSTFCategoryDtl = getSTFCategoryDtl;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryValue() {
		return categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}
	
}
