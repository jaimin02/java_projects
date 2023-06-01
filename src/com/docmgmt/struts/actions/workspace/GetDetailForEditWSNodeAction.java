package com.docmgmt.struts.actions.workspace;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class GetDetailForEditWSNodeAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public boolean WorkspaceUserHistory;
	public String lbl_folderName;
	public String lbl_nodeName;
	public String lbl_nodeDisplayName;
	char isRepeatFlag;
	
	
	@Override
	public String execute()
	{
		 workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		 usertypename = ActionContext.getContext().getSession().get("usertypename").toString();
		 KnetProperties knetProperties = KnetProperties.getPropInfo();
		 eTMFCustomization = knetProperties.getValue("ETMFCustomization");
		 lbl_folderName = knetProperties.getValue("ForlderName");
		 lbl_nodeName = knetProperties.getValue("NodeName");
		 lbl_nodeDisplayName = knetProperties.getValue("NodeDisplayName");
		 System.out.println("eTMFCustomization::::"+eTMFCustomization);
		 getNodeDetail = docMgmtImpl.getNodeDetail(workspaceId,nodeId);
    	 getNodeAttrDetail = docMgmtImpl.getNodeAttrDetail(workspaceId,nodeId);
    	 
    	 DTOWorkSpaceMst dto = docMgmtImpl.getWorkSpaceDetail(workspaceId);
    	 //appType = ProjectType.getApplicationType(dto.getProjectType());
    	 appType =dto.getProjectCode();

 		
 		Vector<DTOWorkSpaceNodeDetail> wsNodeDtl = docMgmtImpl.getNodeDetail(workspaceId, nodeId);
 		isRepeatFlag = wsNodeDtl.get(0).getCloneFlag();
 		if (wsNodeDtl.size() > 0) {

			workSpaceNodeDtl = wsNodeDtl.get(0);
 		}
 		isLeafNode = docMgmtImpl.isLeafNodes(workspaceId, nodeId);
 		
    	 	nodeHistory = docMgmtImpl.getNodeHistory(workspaceId, nodeId);
    	 if(nodeHistory.size()>0){
			DTOWorkSpaceNodeHistory lastnodedetail = (DTOWorkSpaceNodeHistory)nodeHistory.get(nodeHistory.size()-1);
			stageId =lastnodedetail.getStageId();
    	 	}
    	 	Vector<DTOWorkSpaceNodeDetail> WsNodeDetail=new Vector<DTOWorkSpaceNodeDetail>(); 	
        	WsNodeDetail=docMgmtImpl.isLeafParentForCSV(workspaceId,nodeId);
        	if(WsNodeDetail.size()>0){
        	int[] intArray = new int[WsNodeDetail.size()]; 
    		
    		for(int i=0;i<WsNodeDetail.size();i++){
    			String senfForReview = docMgmtImpl.checkSendForReview(workspaceId,WsNodeDetail.get(i).getNodeId());
    			if(senfForReview.equalsIgnoreCase("SR")){
    				WorkspaceUserHistory = true;
    			}
    			intArray[i]=WsNodeDetail.get(i).getNodeId();
    		}
    		System.out.println("Array Length = "+intArray.length);
    				
    		//WorkspaceUserHistory=docMgmtImpl.showNodeHistoryForCSV(workspaceId,intArray);
        	 	
    		if(WorkspaceUserHistory==false){
    			System.out.println("\n Show Button");
    		}
    		else{
    			System.out.println("\n Do Not Show Button");
    		}
        }
    	 return SUCCESS;
	}
	Vector nodeHistory;
	public int stageId;
	public String appType;
	public Vector getNodeDetail;
	public Vector getNodeAttrDetail;
	public String workspaceId;
	public int nodeId;
    public String usertypename;
    public String eTMFCustomization;
    public int isLeafNode;
    public DTOWorkSpaceNodeDetail workSpaceNodeDtl;
    
    public char getIsRepeatFlag() {
		return isRepeatFlag;
	}
	public void setIsRepeatFlag(char isRepeatFlag) {
		this.isRepeatFlag = isRepeatFlag;
	}
	public int getIsLeafNode() {
		return isLeafNode;
	}
	public void setIsLeafNode(int isLeafNode) {
		this.isLeafNode = isLeafNode;
	}
}
	
	


