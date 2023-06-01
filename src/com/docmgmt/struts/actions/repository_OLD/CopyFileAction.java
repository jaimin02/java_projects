package com.docmgmt.struts.actions.repository_OLD;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CopyFileAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		/*
    		System.out.println("srcNodeId:"+srcNodeId);
    		System.out.println("srcWsId:"+srcWsId);
    		System.out.println("destNodeId:"+destNodeId);
    		System.out.println("destWsId:"+destWsId);
    		System.out.println("attribute count:::"+attrCount);
    	*/	
    		
    		int TranNo = docMgmtImpl.getNewTranNo(destWsId);
			Vector attrValueId=new Vector();
			Vector attrValueIdForattrHistory=new Vector();
			
			DTOWorkSpaceNodeAttrHistory wsnahdto = new DTOWorkSpaceNodeAttrHistory();
			DTOWorkSpaceNodeAttrDetail wsnad = new DTOWorkSpaceNodeAttrDetail();
		
			//FileLastModified attribute value
			Timestamp ts = new Timestamp(new Date().getTime());
			wsnad=new DTOWorkSpaceNodeAttrDetail();
			wsnad.setWorkspaceId(destWsId);
			wsnad.setNodeId(destNodeId);
			wsnad.setAttrValue(ts.toString());
			wsnad.setAttrId(-999);
			wsnad.setStatusIndi('N');    					
			wsnad.setModifyBy(userId);
			wsnad.setTranNo(TranNo);
			attrValueId.addElement(wsnad);
			
			wsnahdto=new DTOWorkSpaceNodeAttrHistory();
			wsnahdto.setWorkSpaceId(destWsId);
			wsnahdto.setNodeId(destNodeId);
			wsnahdto.setAttrValue(ts.toString());
			wsnahdto.setAttrId(-999);
			wsnahdto.setStatusIndi('N');    					
			wsnahdto.setModifyBy(userId);
			wsnahdto.setTranNo(TranNo);
			attrValueIdForattrHistory.addElement(wsnahdto);
			
			
			HttpServletRequest request = ServletActionContext.getRequest();
			
			for(int i=1;i<=attrCount;i++)
			{
				
				String attributeValueId = "attrValueId"+i;
	    	    String attrType = "attrType"+i;
	    	    String attrId = "attrId"+i;
	    	    String attrName="attrName"+i;
	    	    
	    	    if(request.getParameter(attrName).toString().equals("Submission Type"))
	    	    {
	    	    	if(request.getParameter(attributeValueId).toString().equals("Paper Submission"))
	    	    	{
	    	   // 		System.out.println("Inside update for paper submission");
	    	    		docMgmtImpl.UpdateDisplayNameForPaperSubmission(destWsId, destNodeId);
	    	    		
	    	    	}
	    	    	
	    	    }
	    	    
	    	    wsnad=new DTOWorkSpaceNodeAttrDetail();
	    	    wsnad.setWorkspaceId(destWsId);
				wsnad.setNodeId(destNodeId);
				wsnad.setAttrValue(request.getParameter(attributeValueId));
				wsnad.setAttrId(Integer.parseInt(request.getParameter(attrId)));
				wsnad.setStatusIndi('N');    					
				wsnad.setModifyBy(userId);
				wsnad.setTranNo(TranNo);
				attrValueId.addElement(wsnad);
				
				wsnahdto=new DTOWorkSpaceNodeAttrHistory();
				wsnahdto.setWorkSpaceId(destWsId);
				wsnahdto.setNodeId(destNodeId);
				wsnahdto.setAttrValue(request.getParameter(attributeValueId));
				wsnahdto.setAttrId(Integer.parseInt(request.getParameter(attrId)));
				wsnahdto.setStatusIndi('N');    					
				wsnahdto.setModifyBy(userId);
				wsnahdto.setTranNo(TranNo);
				attrValueIdForattrHistory.addElement(wsnahdto);
			}
	
			docMgmtImpl.updateNodeAttrDetail(attrValueId);
			docMgmtImpl.InsertUpdateNodeAttrHistory(attrValueIdForattrHistory);
			docMgmtImpl.CopyFileForRepository(srcWsId,destWsId,srcNodeId,destNodeId,userId,TranNo);
			docMgmtImpl.copyLeafAttributes(srcWsId,destWsId,srcNodeId,destNodeId,userId,TranNo);
    	
		return SUCCESS;
	}
	

	public int srcNodeId;
	public int destNodeId;
	public String srcWsId;
	public String destWsId;
	public int attrCount;

	public int getSrcNodeId() {
		return srcNodeId;
	}
	public void setSrcNodeId(int srcNodeId) {
		this.srcNodeId = srcNodeId;
	}
	public int getDestNodeId() {
		return destNodeId;
	}
	public void setDestNodeId(int destNodeId) {
		this.destNodeId = destNodeId;
	}
	public String getSrcWsId() {
		return srcWsId;
	}
	public void setSrcWsId(String srcWsId) {
		this.srcWsId = srcWsId;
	}
	public String getDestWsId() {
		return destWsId;
	}
	public void setDestWsId(String destWsId) {
		this.destWsId = destWsId;
	}
	public int getAttrCount() {
		return attrCount;
	}
	public void setAttrCount(int attrCount) {
		this.attrCount = attrCount;
	}
	
	
}
	