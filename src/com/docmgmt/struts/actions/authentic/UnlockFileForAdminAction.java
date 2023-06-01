package com.docmgmt.struts.actions.authentic;


import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import com.docmgmt.dto.DTOCheckedoutFileDetail;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UnlockFileForAdminAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String workspaceId;
	public int nodeId;
	public int tranNo;
	 

	     
	@Override
	public String execute() 
	 {
			int userCode =Integer.parseInt( ActionContext.getContext().getSession().get("userid").toString());
			int userGroupCode = Integer.parseInt( ActionContext.getContext().getSession().get("usergroupcode").toString());
			if(selectedTranNo != null )
			{
				if(btnUnlock.equalsIgnoreCase("Check-In"))
				{
					
					String [] lockedFileNodes = selectedTranNo;
					HashMap lockedFileMap = getLockefFileMap();
					DTOCheckedoutFileDetail dtolockedFileDtls;
					PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
					String tempBaseFolder=propertyInfo.getValue("BASE_TEMP_FOLDER");	
					String tempBaseFolderReplaced = tempBaseFolder;//.replace('/','\\');	
					DTOUserMst dto = new DTOUserMst();
					dto.setUserCode(userCode);
					dto.setUserGroupCode(userGroupCode);
					for(int i = 0; i < lockedFileNodes.length; i++) {			
						String lockedFileTranNo = lockedFileNodes[i];
						String temp [] = lockedFileNodes[i].split("_");
						workspaceId = temp[0];
						nodeId = Integer.parseInt(temp[1]);
						tranNo = Integer.parseInt(temp[2]);
						//dtolockedFileDtls = (DTOCheckedoutFileDetail)lockedFileMap.get(new Integer(lockedFileTranNo));
						dtolockedFileDtls = (DTOCheckedoutFileDetail)lockedFileMap.get(new Integer(tranNo));
						
						//LockedFileDetailBean lockedFileDetailBean = new LockedFileDetailBean();
						if(dtolockedFileDtls != null) {
							unLockNode(dto,tempBaseFolder);				
						}
						
					}	
				
					Vector lockedFileDetail = docMgmtImpl.getLockedFileDetailForAdmin(userCode, userGroupCode);
					Vector userWorkspace = getUserWorkspace(dto);
					Vector userDetailsByUserGrp = getUserDetailsByUserGrp(dto);
				
				}
				
				if(btnUnlock.equalsIgnoreCase("Unlock Without Saving")){
					String [] lockedFileNodes = selectedTranNo;
					HashMap lockedFileMap = getLockefFileMap();
					DTOCheckedoutFileDetail dtolockedFileDtls;
					PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
					String tempBaseFolder=propertyInfo.getValue("BASE_TEMP_FOLDER");	
					String tempBaseFolderReplaced = tempBaseFolder;//.replace('/','\\');	
					//calling the server method to save the changes..
					DTOUserMst dto = new DTOUserMst();
					dto.setUserCode(userCode);
					dto.setUserGroupCode(userGroupCode);
					for(int i = 0; i < lockedFileNodes.length; i++) {			
						String lockedFileTranNo = lockedFileNodes[i];
						dtolockedFileDtls = (DTOCheckedoutFileDetail)lockedFileMap.get(new Integer(lockedFileTranNo));		
						
						unLockNodeWithoutSave(dto,tempBaseFolder);				
					}

					//getting the refresh data and setting in the request scope to be got the form again
					
					Vector lockedFileDetail = docMgmtImpl.getLockedFileDetailForAdmin(userCode, userGroupCode);
					Vector userWorkspace = getUserWorkspace(dto);
					Vector userDetailsByUserGrp = getUserDetailsByUserGrp(dto);
				}
		}
		return SUCCESS;
      }
	/* --------------------------------------------service layer function --------------------------------------------*/
	
		public boolean unLockNode(DTOUserMst userMst,String tempBaseFolder)  
		{
	 	
			//Vector lockedFileDetail = docMgmtImpl.getLockedFileDetailForAdmin(userMst.getUserCode(), userMst.getUserGroupCode());
			Vector lockedFileDetail = docMgmtImpl.getLockedFileDetailForAdminForCSV(workspaceId, nodeId, tranNo,userMst.getUserCode(), userMst.getUserGroupCode());
		 	DTOCheckedoutFileDetail checkedoutFileDetail = (DTOCheckedoutFileDetail)lockedFileDetail.elementAt(0);
		 		 	 	
		 	return unlockFiles(checkedoutFileDetail,tempBaseFolder);
		}
	
		
		private boolean unlockFiles(DTOCheckedoutFileDetail checkedoutFileDetail, String tempBaseFolder)  
		{
		 	
		 	String baseWorkFolder = checkedoutFileDetail.getBaseWorkFolder();
		 	String wsId = checkedoutFileDetail.getWorkSpaceId();
		 	int nodeId = checkedoutFileDetail.getNodeId();
		 	int tranNo = checkedoutFileDetail.getTranNo();
		 	int fileTranNo = checkedoutFileDetail.getPrevTranNo();
		 	String fileName = checkedoutFileDetail.getFileName();	 		 	
		 	
	   	
	        File tempFolder = new File(tempBaseFolder + "//" + wsId + "//" + nodeId + "//" + tranNo);
	       
		    File destFile = new File(baseWorkFolder + "//" + wsId + "//" + nodeId + "//" + fileTranNo + "//" + fileName);
		    File srcFile = new File(tempBaseFolder  + "//" + wsId + "//" + nodeId + "//" + tranNo + "//" + fileName);
		    
		//    System.out.println("-----source path of file-----" + srcFile);
		 //   System.out.println("-----Dest. file path-------" + destFile);
		    
		    
	       //FileCopy fc = new FileCopy(srcFile,destFile);
	       docMgmtImpl.unLockFiles(wsId,nodeId,tranNo);
	       //srcFile.delete();
		   //tempFolder.delete();
		   return true;

	   }
		
		 public Vector getUserWorkspace(DTOUserMst userMst)  {
		    	
		    	
		    	Vector userWorkspaceDtl =  docMgmtImpl.getUserWorkspace(userMst.getUserGroupCode(),userMst.getUserCode());
		    	
		    	Vector userWorkspaceDtlWithALL= new Vector();    	    	
		    	DTOWorkSpaceMst  workSpaceMst = new DTOWorkSpaceMst();
		    	workSpaceMst.setWorkSpaceId("ALL");
		    	workSpaceMst.setWorkSpaceDesc("---ALL----");
		    	
		    	userWorkspaceDtlWithALL.add(workSpaceMst);
		    	workSpaceMst = null;
		    	
		    	for(int i = 0 ; i < userWorkspaceDtl.size(); i++) {
		    		
		    		DTOWorkSpaceMst  workSpaceMstNew = new DTOWorkSpaceMst();
		    		workSpaceMstNew = (DTOWorkSpaceMst)userWorkspaceDtl.elementAt(i);
		    		
		    		userWorkspaceDtlWithALL.add(workSpaceMstNew);
		    		workSpaceMstNew = null;    		
		    	}
		    	
		    	return userWorkspaceDtlWithALL;
		    	
		    }
		 
		 public Vector getUserDetailsByUserGrp(DTOUserMst userMst) {
		    	
		    	Vector userDetailsByUserGrpDtl = docMgmtImpl.getuserDetailsByUserGrp(userMst.getUserGroupCode());
		    	
		    	Vector userDetailsByUserGrpDtlALL= new Vector();
		    	
		    	DTOUserMst userMstNew = new DTOUserMst();
		    	userMstNew.setUserCode(0);
		    	userMstNew.setLoginName("---ALL----");
		    	userMstNew.setUserName("---ALL----");
		    	userDetailsByUserGrpDtlALL.add(userMstNew);
		    	userMstNew = null;
		    	
		    	for(int i = 0 ; i < userDetailsByUserGrpDtl.size(); i++) {
		    		
		    		DTOUserMst  userMstNew1 = new DTOUserMst();
		    		userMstNew1 = (DTOUserMst)userDetailsByUserGrpDtl.elementAt(i);
		    		
		    		userDetailsByUserGrpDtlALL.add(userMstNew1);
		    		userMstNew1 = null;    		
		    	}
		    	
		    	return userDetailsByUserGrpDtlALL;
		    	
		    }
		 
		 public boolean unLockNodeWithoutSave(DTOUserMst userMst,String tempBaseFolder)  {
			 	
			 	int userCode =Integer.parseInt( ActionContext.getContext().getSession().get("userid").toString());
				int userGroupCode = Integer.parseInt( ActionContext.getContext().getSession().get("usergroupcode").toString());
				Vector lockedFileDetail = docMgmtImpl.getLockedFileDetailForAdmin(userCode, userGroupCode);		
				DTOCheckedoutFileDetail checkedoutFileDetail = (DTOCheckedoutFileDetail)lockedFileDetail.elementAt(0);
			 	 	
				return unlockFilesWithoutSave(checkedoutFileDetail,tempBaseFolder);
				
			}
		    
		 
		 private boolean unlockFilesWithoutSave(DTOCheckedoutFileDetail checkedoutFileDetail,String tempBaseFolder){
				
			 	String baseWorkFolder = checkedoutFileDetail.getBaseWorkFolder();
			 	String wsId = checkedoutFileDetail.getWorkSpaceId();
			 	int nodeId = checkedoutFileDetail.getNodeId();
			 	int tranNo = checkedoutFileDetail.getTranNo();
			 	int fileTranNo = checkedoutFileDetail.getPrevTranNo();
			 	String fileName = checkedoutFileDetail.getFileName();	 		 	
			 	
			 	File tempFolder = new File(tempBaseFolder + "//" + wsId + "//" + nodeId + "//" + tranNo);
			    File tempFile = new File(tempBaseFolder  + "//" + wsId + "//" + nodeId + "//" + tranNo + "//" + fileName);
			   	docMgmtImpl.unLockFiles(wsId,nodeId,tranNo);            
			   	tempFile.delete();
			   	tempFolder.delete();
		       
		       return true;
		   }
		
		
		
	/*----------------------------------------------------------------------------------------------------*/
	public String[] selectedTranNo;
	public String btnUnlock;
	

	public String getBtnUnlock() {
		return btnUnlock;
	}

	public void setBtnUnlock(String btnUnlock) {
		this.btnUnlock = btnUnlock;
	}

	public String[] getSelectedTranNo() {
		return selectedTranNo;
	}

	public void setSelectedTranNo(String[] selectedTranNo) {
		this.selectedTranNo = selectedTranNo;
	}
	
	public HashMap getLockefFileMap( )  {
  		
	  	HashMap lockedFileMap = new HashMap();
		DTOCheckedoutFileDetail dtolockedFileDtls = null;	
		int userCode =Integer.parseInt( ActionContext.getContext().getSession().get("userid").toString());
		 int userGroupCode = Integer.parseInt( ActionContext.getContext().getSession().get("usergroupcode").toString());
		
		Vector lockedfileDetails = docMgmtImpl.getLockedFileDetailForAdmin(userCode, userGroupCode);
		if(lockedfileDetails != null ) {
			
			for(int i = 0; i < lockedfileDetails.size(); i++) {				
				dtolockedFileDtls = (DTOCheckedoutFileDetail)lockedfileDetails.elementAt(i);
				lockedFileMap.put(new Integer(dtolockedFileDtls.getTranNo()),dtolockedFileDtls);									
			}
			
		}
		
		return lockedFileMap;
  }
	
}
