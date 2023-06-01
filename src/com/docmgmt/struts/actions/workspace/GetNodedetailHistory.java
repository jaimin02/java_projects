package com.docmgmt.struts.actions.workspace;

import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetNodedetailHistory extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String result1;
	public String lbl_folderName;
	public String lbl_nodeName;
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	@Override
	public String execute()
	{
		 workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		 lbl_folderName = knetProperties.getValue("ForlderName");
		 lbl_nodeName = knetProperties.getValue("NodeName");	
		 getWsNodeDetailHistory = docMgmtImpl.getWsNodeDetailHistory(workspaceId,nodeId);
		 
		 for(int i =0;i<getWsNodeDetailHistory.size();i++){
			 String nDisplayName=getWsNodeDetailHistory.get(i).getNodeDisplayName();
			 if(nDisplayName.contains("{")){
					int startingIndex = nDisplayName.indexOf("{");
					int closingIndex = nDisplayName.indexOf("}");
					result1 = nDisplayName.substring(startingIndex , closingIndex+1);
					
					//System.out.println(result1);
					nDisplayName=nDisplayName.replace(result1, "  ");
					getWsNodeDetailHistory.get(i).setNodeDisplayName(nDisplayName);
					//System.out.println(nDisplayName);
					}
		 }
    	    	 
    	 return SUCCESS;
	}
	public String DeletedNodeDetail(){
		
		//workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		lbl_folderName = knetProperties.getValue("ForlderName");
		lbl_nodeName = knetProperties.getValue("NodeName");	
		//getDeletedNodeDetail = docMgmtImpl.getDeletedNodeDetail(workspaceId);
		getDeletedNodeDetail = docMgmtImpl.getDeletedNodeDetailForActiveInactive(workspaceId);
		
		return SUCCESS;
	}
	
	public String NodeAttrDetailHistory(){
		
		workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		lbl_folderName = knetProperties.getValue("ForlderName");
		lbl_nodeName = knetProperties.getValue("NodeName");	
		getNodeAttrDetailHistory = docMgmtImpl.getWsNodeAttrDetailHistoryByAttrId(workspaceId,nodeId);
		
		return SUCCESS;
	}
	
public String SendBackFileDetail(){
		
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		//workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		lbl_folderName = knetProperties.getValue("ForlderName");
		lbl_nodeName = knetProperties.getValue("NodeName");
		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		dto.setModifyBy(userId);
		dto.setWorkSpaceId(workspaceId);
		dto.setNodeId(0);
		
		getPendingWorks = docMgmtImpl.getSendBackFileDetail(dto);
		
		return SUCCESS;
	}

public String DeviationFileDetail(){
	
	//workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
	lbl_folderName = knetProperties.getValue("ForlderName");
	lbl_nodeName = knetProperties.getValue("NodeName");
	getDeviationDetail = docMgmtImpl.getDeviationFileDetail(workspaceId);
	
	return SUCCESS;
}
	
	public Vector<DTOWorkSpaceNodeHistory> getPendingWorks;
	public Vector<DTOWorkSpaceNodeDetail> getDeviationDetail;
	public Vector<DTOWorkSpaceNodeDetail> getWsNodeDetailHistory;
	public Vector getDeletedNodeDetail;
	public Vector getNodeAttrDetailHistory;
	public String workspaceId;
	public int nodeId;	
}
