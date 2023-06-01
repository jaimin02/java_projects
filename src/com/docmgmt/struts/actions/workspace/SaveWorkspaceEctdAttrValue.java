package com.docmgmt.struts.actions.workspace;


import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.GenerateWorkspaceEctdAttrTree;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;


public class SaveWorkspaceEctdAttrValue extends ActionSupport
{	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public int attrCount;
	
	 public String WsnodeId;
	 
	 public String workspaceID;
	 
	 
	 
	 
	@Override
	public String execute()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		
		String AttributeValueForNodeDisplayName="";
		
		
		for(int i=1;i<attrCount;i++)
		{
			 String attrValueId = "attrValueId"+i;
	    	    //String attrType = "attrType"+i;
	    	    String attrId = "attrId"+i;
	    	    String attrName = "attrName"+i;
	    	
	    	    
	   
	 	    	    
	     Integer atrribId=Integer.parseInt(request.getParameter(attrId));
	    
	    DTOWorkSpaceNodeAttribute wsnadto = new DTOWorkSpaceNodeAttribute();
	    
	    wsnadto.setWorkspaceId(workspaceID);
	    wsnadto.setNodeId(Integer.parseInt(WsnodeId));
	    wsnadto.setAttrId(atrribId);
	    wsnadto.setAttrName(request.getParameter(attrName));
	    wsnadto.setAttrValue(request.getParameter(attrValueId));
	    
	    int ucd=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	    	
	    wsnadto.setModifyBy(ucd);
	    
	    /////update workspacenode attribute value
	    docMgmtImpl.updateWorkspaceNodeAttributeValue(wsnadto);
	    
	    
	 
	    if(AttributeValueForNodeDisplayName==""){
	    	AttributeValueForNodeDisplayName =request.getParameter(attrValueId);
	    }else{
	    	AttributeValueForNodeDisplayName=AttributeValueForNodeDisplayName+","+request.getParameter(attrValueId);
	    }
	    
	    
	    	    
		}
		
		
		 //update nodedisplayname of the node under NodeId. If nodeid is leaf node then change its
	    //nodedisplayname
		DTOWorkSpaceNodeDetail wsnd = new DTOWorkSpaceNodeDetail();
		wsnd.setWorkspaceId(workspaceID);
		wsnd.setNodeId(Integer.parseInt(WsnodeId));
		
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
	    
		 
		return SUCCESS;
	}
	
	
	
	public int getAttrCount() {
		return attrCount;
	}


	public void setAttrCount(int attrCount) {
		this.attrCount = attrCount;
	}
	
	 
	public String getWsnodeId() {
		return WsnodeId;
	}



	public void setWsnodeId(String wsnodeId) {
		WsnodeId = wsnodeId;
	}



	public String getWorkspaceID() {
		return workspaceID;
	}



	public void setWorkspaceID(String workspaceID) {
		this.workspaceID = workspaceID;
	}



	
	
}
