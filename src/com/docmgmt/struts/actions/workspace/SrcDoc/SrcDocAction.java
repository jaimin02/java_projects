package com.docmgmt.struts.actions.workspace.SrcDoc;

import java.io.File;
import java.util.ArrayList;

import com.docmgmt.dto.DTOWorkspaceNodeDocHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SrcDocAction extends ActionSupport {
	public static final long serialVersionUID = 1;
	
	
	String workspaceId;
	int nodeId;
	String fullDocPath;
	String tempFilePath;
	String btnSub;
	String nodeDisplayName;
	
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	
	
	public String edit(){
		
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		
		//int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String userName = ActionContext.getContext().getSession().get("username").toString();
		File srcDoc = new File(fullDocPath); 
		if(srcDoc.exists()){
			
			/**Copying the src file into temp folder for edit*/
			String tempDir = propertyInfo.getValue("BASE_TEMP_FOLDER");
			String destFileName = "src_"+srcDoc.getName();
			
			File destFile = new File(tempDir+"/"+workspaceId+"/"+nodeId+"/"+destFileName);
			boolean deleted = true;
			System.out.println(destFile.getAbsolutePath());
			if(destFile.exists()){
				deleted = destFile.delete();
			}
			if(deleted){
				FileManager fileManager = new FileManager();
				fileManager.copyDirectory(srcDoc, destFile);
				tempFilePath = destFile.getAbsolutePath();
				
			}else{
				/*May be somebody else is working on the same doc.*/
				tempFilePath = "conflict";
				addActionMessage("May be This Document is being modified by someone else. Please try again later.");
			}
			
		}
		else{
			tempFilePath = "No File";
		}
		
		System.out.println(tempFilePath);
		return SUCCESS;
	}
	
	public String save() {
		
		if(btnSub.equals("Done")){
			PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
			
			int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			String userName = ActionContext.getContext().getSession().get("username").toString();
			
			
			ArrayList<DTOWorkspaceNodeDocHistory> docHistory = docMgmtImpl.getLatestNodeDocHistory(workspaceId, nodeId);
			if(docHistory.size() > 0){
				DTOWorkspaceNodeDocHistory dtoDocHistory = docHistory.get(0);
				
				int newTranNo = docMgmtImpl.getNewDocTranNo(workspaceId);
				String docPath = "/"+workspaceId+"/"+nodeId+"/"+newTranNo;
				
				File tempFile = new File(tempFilePath);
				
				String srcDir = propertyInfo.getValue("SOURCE_DOC_FOLDER");
				File srcDoc = new File(srcDir+docPath+"/"+dtoDocHistory.getDocName());
				
				FileManager fileManager = new FileManager();
				fileManager.copyDirectory(tempFile, srcDoc);
				
				
				dtoDocHistory.setDocName(srcDoc.getName());
				dtoDocHistory.setDocTranNo(newTranNo);
				dtoDocHistory.setDocPath(docPath);
				dtoDocHistory.setModifyBy(userId);
				dtoDocHistory.setUploadedBy(userId);
				dtoDocHistory.setRemark(srcDoc.getName()+" has been Edited by "+userName);
				ArrayList<DTOWorkspaceNodeDocHistory> WsNodeDocHistoryList = new ArrayList<DTOWorkspaceNodeDocHistory>();
				WsNodeDocHistoryList.add(dtoDocHistory);
				docMgmtImpl.insertNodeDocHistory(WsNodeDocHistoryList,false);
				
			}
		}
		else if(btnSub.equals("Undo")){
			System.out.println(tempFilePath);
			
		}
		
		
		return SUCCESS;
		
	}
	
	/*Getters-Setters*/	
	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getFullDocPath() {
		return fullDocPath;
	}

	public void setFullDocPath(String fullDocPath) {
		this.fullDocPath = fullDocPath;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getTempFilePath() {
		return tempFilePath;
	}

	public void setTempFilePath(String tempFilePath) {
		this.tempFilePath = tempFilePath;
	}

	public String getBtnSub() {
		return btnSub;
	}

	public void setBtnSub(String btnSub) {
		this.btnSub = btnSub;
	}

	public String getNodeDisplayName() {
		return nodeDisplayName;
	}

	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}	
}
