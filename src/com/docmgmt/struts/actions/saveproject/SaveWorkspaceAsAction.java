package com.docmgmt.struts.actions.saveproject;



import java.io.File;
import java.util.ArrayList;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkspaceNodeDocHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;



public class SaveWorkspaceAsAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	private ArrayList<DTOWorkSpaceNodeHistory> workspaceNodeHistory;
	public String htmlContent;
	@Override
	public String execute(){
	
	
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
        String workspaceFolder = propertyInfo.getValue("BaseWorkFolder");
		String publicationFolder= propertyInfo.getValue("BasePublishFolder");
	
		DTOWorkSpaceMst destinationWorkSpace = new DTOWorkSpaceMst();
		destinationWorkSpace.setWorkSpaceId(workSpaceId);// sourceWorkspaceId
		destinationWorkSpace.setWorkSpaceDesc(sourceWorkSpaceDesc );
    	destinationWorkSpace.setLocationCode(locationCode);
    	destinationWorkSpace.setBaseWorkFolder(workspaceFolder);
    	destinationWorkSpace.setBasePublishFolder(publicationFolder);
     	destinationWorkSpace.setDeptCode(deptCode);
    	destinationWorkSpace.setClientCode(clientCode);
    	destinationWorkSpace.setProjectCode(projectCode);
    	destinationWorkSpace.setDocTypeCode("0001");//Directly pass the DocTypeCode because of no use Further
    	destinationWorkSpace.setCreatedBy(userCode);
    	destinationWorkSpace.setLastAccessedBy(userCode);
    	destinationWorkSpace.setRemark(remark);
    	destinationWorkSpace.setProjectType(projectFor.charAt(0));
    	destinationWorkSpace.setModifyBy(userCode);
    	
    	System.out.println("2");
    	boolean exists = docMgmtImpl.insertIntoWorkSpaceForSaveAsProject(destinationWorkSpace);
    	System.out.println("3-"+exists);
    	if(exists)
    	{
    		addActionMessage("Project Name Already Exists!!! Please specify another Project Name...");
    		return "exists";
    	}
    	else
    	{
    		if(sourceWorkSpaceDesc!=null)
    		{
    			addActionMessage(sourceWorkSpaceDesc + " Successfully Created.");
    		}
    	}
    	
    	System.out.println("4");
    	destWsId = docMgmtImpl.getWorkspaceID(sourceWorkSpaceDesc.trim());
    	System.out.println("destination workspaceid::"+destWsId);
    	return SUCCESS;
	}
	public String saveSkeletonProject()
	{
		execute();	
		workspaceNodeHistory=docMgmtImpl.getNodeHistoryStageWiseUserWise(workSpaceId, 0, 100, 0, false);
		
		uploadSourceDocForSkeleton(workspaceNodeHistory);
		
		
		htmlContent="<a href='WorkspaceOpen.do?ws_id=" + destWsId + "'>" + sourceWorkSpaceDesc + "</a> Created Successfully...";
		
		return SUCCESS;
	}
public void uploadSourceDocForSkeleton(ArrayList<DTOWorkSpaceNodeHistory> workspaceNodeHistory) {
		
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String sourceDocPath=propertyInfo.getValue("SOURCE_DOC_FOLDER");
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String userName=ActionContext.getContext().getSession().get("username").toString();
				
			ArrayList<DTOWorkspaceNodeDocHistory> WsNodeDocHistoryList = new ArrayList<DTOWorkspaceNodeDocHistory>();
			for(int indexNode=0;indexNode<workspaceNodeHistory.size();indexNode++){

				DTOWorkspaceNodeDocHistory dto = new DTOWorkspaceNodeDocHistory();
				DTOWorkSpaceNodeHistory  dtoWorkspaceNodeHistory=workspaceNodeHistory.get(indexNode);
					
				//Get new docTranNo and docPath
				String newTranNo = dtoWorkspaceNodeHistory.getFolderName();
				newTranNo = newTranNo.substring(newTranNo.lastIndexOf("/"), newTranNo.length());
				String srcDocPath = "/"+dtoWorkspaceNodeHistory.getNodeId()+"/"+newTranNo;
				String destDocPath = dtoWorkspaceNodeHistory.getBaseWorkFolder()+"/"+dtoWorkspaceNodeHistory.getWorkSpaceId()+srcDocPath+"/"+dtoWorkspaceNodeHistory.getFileName(); 
				int docTranNo = docMgmtImpl.getNewDocTranNo(destWsId);
				
				dto.setWorkspaceId(destWsId);
				dto.setNodeId(dtoWorkspaceNodeHistory.getNodeId());
				dto.setDocTranNo(docTranNo);
				dto.setDocName(dtoWorkspaceNodeHistory.getFileName());
				dto.setDocContentType(dtoWorkspaceNodeHistory.getFileType());
				dto.setDocPath("/"+destWsId +srcDocPath);
				dto.setUploadedBy(userCode);
				dto.setRemark(dtoWorkspaceNodeHistory.getFileName()+" has been uploaded by "+userName);
				dto.setModifyBy(userCode);
				//To copy file in master function, set the below two variables.
				dto.setSrcDoc(new File(destDocPath));
				dto.setBaseSrcDir(sourceDocPath);				
				WsNodeDocHistoryList.add(dto);
			}		
			
			docMgmtImpl.insertNodeDocHistory(WsNodeDocHistoryList,true);
			
			/*
			File sourceLocation = uploadDoc;
			File targetLocation = new File(sourceDocPath+docPath+"/"+uploadDocFileName);
			FileManager fileManager = new FileManager();
			fileManager.copyDirectory(sourceLocation, targetLocation);
			*/	
			
	}
	public String ws_desc;
	public String workSpaceId;
	public String sourceWorkSpaceDesc;
	public String locationCode;
	public String deptCode;
	public String clientCode;
	public String docTypeCode;
	public String projectCode;
	public String projectFor = " ";
	public int userCode;
	public String remark;
	public String destWsId;

	public String getDestWsId() {
		return destWsId;
	}
	public void setDestWsId(String destWsId) {
		this.destWsId = destWsId;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public String getSourceWorkSpaceDesc() {
		return sourceWorkSpaceDesc;
	}
	public void setSourceWorkSpaceDesc(String sourceWorkSpaceDesc) {
		this.sourceWorkSpaceDesc = sourceWorkSpaceDesc;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getDocTypeCode() {
		return docTypeCode;
	}
	public void setDocTypeCode(String docTypeCode) {
		this.docTypeCode = docTypeCode;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectFor() {
		return projectFor;
	}
	public void setProjectFor(String projectFor) {
		this.projectFor = projectFor;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWs_desc() {
		return ws_desc;
	}
	public void setWs_desc(String ws_desc) {
		this.ws_desc = ws_desc;
	}
	
	
}
