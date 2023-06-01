package com.docmgmt.struts.actions.replication;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.DeleteAllTrees;
import com.docmgmt.server.webinterface.beans.GenerateWorkspaceEctdAttrTree;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class SaveReplicatedWorkspaceNodeAttributeAction extends ActionSupport
{	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public int attrCount;
	
	 public int WsnodeId;
	 
	 public String workspaceID;
	 
	 
	 
	 
	@Override
	public String execute()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		DTOWorkSpaceNodeAttribute wsnadto = new DTOWorkSpaceNodeAttribute();
		String AttributeValueForNodeDisplayName="";
		int ucd=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		int  newNodeId = docMgmtImpl.getmaxNodeId(workspaceID)+1;//Changed by : Ashmak
    	docMgmtImpl.CopyAndPasteWorkSpace(workspaceID,WsnodeId,workspaceID,WsnodeId,ucd,"2");
    
    	for(int i=1;i<attrCount;i++)
		{
			 String attrValueId = "attrValueId"+i;
	    	    String attrType = "attrType"+i;
	    	    String attrId = "attrId"+i;
	    	    String attrName = "attrName"+i;
	    	
	    Integer atrribId=Integer.parseInt(request.getParameter(attrId));
	    wsnadto.setWorkspaceId(workspaceID);
	    wsnadto.setNodeId(newNodeId);//Changed by : Ashmak
	    wsnadto.setAttrId(atrribId);
	    wsnadto.setAttrName(request.getParameter(attrName));
	    wsnadto.setAttrValue(request.getParameter(attrValueId));
	    wsnadto.setModifyBy(ucd);
	
	    
	    docMgmtImpl.updateWorkspaceNodeAttributeValue(wsnadto);
		
	    if(AttributeValueForNodeDisplayName==""){
		    	AttributeValueForNodeDisplayName =request.getParameter(attrValueId);
		 }else{
		    	AttributeValueForNodeDisplayName=AttributeValueForNodeDisplayName+","+request.getParameter(attrValueId);
		    }
	    	    
		}
	
    	 
		DTOWorkSpaceNodeDetail wsnd = new DTOWorkSpaceNodeDetail();
		wsnd.setWorkspaceId(workspaceID);
		wsnd.setNodeId(newNodeId);
		
		docMgmtImpl.updateNodeDisplayNameAccToEctdAttribute(wsnd, AttributeValueForNodeDisplayName);
		
		
		///Generate ECTD tree
		GenerateWorkspaceEctdAttrTree tree= new GenerateWorkspaceEctdAttrTree();
	    
		Integer usergrpcode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		Integer usercode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		String userType=ActionContext.getContext().getSession().get("usertypecode").toString();
		
		DTOWorkSpaceMst workSpaceMst=docMgmtImpl.getWorkSpaceDetail(workspaceID);
		
		//generate tree text file 
        tree.generateTree(workspaceID,workSpaceMst.getProjectCode(),usercode,usergrpcode,userType);
        tree=null;
	    DeleteAllTrees objDeleteTrees = new DeleteAllTrees();  
    	objDeleteTrees.DeleteAllGeneratedTrees(workspaceID);
    	
        
        
		 
		return SUCCESS;
	}
	
	
	
	public int getAttrCount() {
		return attrCount;
	}


	public void setAttrCount(int attrCount) {
		this.attrCount = attrCount;
	}
	
	public int getWsnodeId() {
		return WsnodeId;
	}

	public void setWsnodeId(int wsnodeId) {
		WsnodeId = wsnodeId;
	}

	public String getWorkspaceID() {
		return workspaceID;
	}

	public void setWorkspaceID(String workspaceID) {
		this.workspaceID = workspaceID;
	}



	
	
}
