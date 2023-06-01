package com.docmgmt.struts.actions.workspace;

import java.io.File;
import java.util.Vector;


import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.beans.FileCopy;


import com.opensymphony.xwork2.ActionSupport;

public class SaveWorkspaceNodeVersionAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	

	@Override
	public String execute()
	{
		DTOWorkSpaceNodeVersionHistory objwsnodeversionhistory = new DTOWorkSpaceNodeVersionHistory();
		String srv = selectedRadiobtnVersion;
		
		
		objwsnodeversionhistory.setNodeId(nodeId);
		objwsnodeversionhistory.setWorkspaceId(workspaceId);
		objwsnodeversionhistory.setFileVersionId(srv);
		Vector versionhistory = docMgmtImpl.getNodeVersionHistory(objwsnodeversionhistory);
		DTOWorkSpaceNodeVersionHistory workSpaceNodeVersionHistory = new DTOWorkSpaceNodeVersionHistory();
		int versiontranNo=0;
		if (versionhistory != null) 
		{
			for(int i=0;i<versionhistory.size();i++)
			{
				workSpaceNodeVersionHistory=(DTOWorkSpaceNodeVersionHistory)versionhistory.get(i);
				versiontranNo=workSpaceNodeVersionHistory.getTranNo();
			}
		}
		
		Vector lastnodehistory=docMgmtImpl.getLastNodeHistory(workspaceId,nodeId);
		//Vector record = new Vector();
		int tranNo=0;
		if (lastnodehistory.size() > 0)
		{
			for(int i=0;i<lastnodehistory.size();i++)
			{
				DTOWorkSpaceNodeHistory dto = (DTOWorkSpaceNodeHistory)lastnodehistory.get(i);
				tranNo = dto.getTranNo();
				
			}
		
		}
		//System.out.println(workspaceId);
		//System.out.println(nodeId);
		//System.out.println(versiontranNo);
		
		DTOWorkSpaceNodeHistory objwsnodehistory = new DTOWorkSpaceNodeHistory();
		if(docMgmtImpl.getWorkspaceNodeHistorybyTranNo(workspaceId,nodeId,versiontranNo)!=null)
		{
			objwsnodehistory = docMgmtImpl.getWorkspaceNodeHistorybyTranNo(workspaceId,nodeId,versiontranNo);
		
			tranNo=tranNo+1;
			objwsnodehistory.setTranNo(tranNo);
			
		}
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
        String baseWorkFolder = propertyInfo.getValue("BaseWorkFolder");
    	docMgmtImpl.insertNodeHistory(objwsnodehistory);
		createFolder(objwsnodehistory,versiontranNo,baseWorkFolder);
		

//		getting the value row from workspacenodeattrhistory which has to revert
		Vector wsnah = new Vector();
		DTOWorkSpaceNodeAttrHistory objwsnah = new DTOWorkSpaceNodeAttrHistory();
		wsnah =  docMgmtImpl.getWorkspaceNodeAttrHistorybyTranNo(workspaceId,nodeId,versiontranNo);
	
		for(int i=0; i<wsnah.size(); i++) {
			objwsnah = (DTOWorkSpaceNodeAttrHistory)wsnah.get(i);
			
			objwsnah.setTranNo(tranNo);
			Vector data = new Vector();
			data.add(objwsnah);
			docMgmtImpl.insertNodeAttrHistory(data);
		}
		
		docMgmtImpl.setLastClosedFlag(workspaceId, nodeId, versiontranNo, tranNo);
		
		return SUCCESS;

	
	}

	public String workspaceId;
	public int nodeId;
	public String selectedRadiobtnVersion;
		
	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getSelectedRadiobtnVersion() {
		return selectedRadiobtnVersion;
	}

	public void setSelectedRadiobtnVersion(String selectedRadiobtnVersion) {
		this.selectedRadiobtnVersion = selectedRadiobtnVersion;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	private void createFolder(DTOWorkSpaceNodeHistory objwsnodehistory,int oldTranNo,String baseWorkFolder) {
	    	String wsId=objwsnodehistory.getWorkSpaceId();
	    	int nodeId=objwsnodehistory.getNodeId();
	    	int newTranNo=objwsnodehistory.getTranNo();
	    	String fileName=objwsnodehistory.getFileName();
	    	
	    	String sourcePath = baseWorkFolder + "/" + wsId + "/" + nodeId + "/" + oldTranNo + "/" + fileName;
	    	String destPath = baseWorkFolder + "/" + wsId + "/" + nodeId + "/" + newTranNo + "/" + fileName;
	    	
	    //	System.out.println("Source path:   " +  sourcePath);
	    //	System.out.println("Destination Path : " + destPath);
	    	
	    	try{
	    		File sourceFile = new File(sourcePath);
	    		File DestFolder = new File(baseWorkFolder + "/" + wsId + "/" + nodeId + "/" + newTranNo);
	    		DestFolder.mkdir();
	    		File DestFile = new File(destPath);
	    		System.out.println("File Created....");
	    		FileCopy fc = new FileCopy(sourceFile,DestFile);

	    	}catch(Exception e ){
	            e.printStackTrace();
	    	}
 
	    }


	 
	    

}


