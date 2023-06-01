package com.docmgmt.struts.actions.repository;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;

public class CopyFileAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
    		
    		int TranNo = docMgmtImpl.getNewTranNo(destWsId);
			Vector attrValueId=new Vector();
			Vector attrValueIdForattrHistory=new Vector();
			

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
			
			docMgmtImpl.updateNodeAttrDetail(attrValueId);
			Vector destNodeattrDetail = docMgmtImpl.getNodeAttrDetail(destWsId, destNodeId);
			
			for (Iterator iterator = destNodeattrDetail.iterator(); iterator.hasNext();) {
				DTOWorkSpaceNodeAttrDetail attrdtl = (DTOWorkSpaceNodeAttrDetail) iterator.next();
				
				DTOWorkSpaceNodeAttrHistory wsnahdto=new DTOWorkSpaceNodeAttrHistory();
					wsnahdto.setWorkSpaceId(destWsId);
					wsnahdto.setNodeId(destNodeId);
					wsnahdto.setAttrValue(attrdtl.getAttrValue());
					wsnahdto.setAttrId(attrdtl.getAttrId());
					wsnahdto.setStatusIndi('N');    					
					wsnahdto.setModifyBy(userId);
					wsnahdto.setTranNo(TranNo);
					attrValueIdForattrHistory.addElement(wsnahdto);
			}

			docMgmtImpl.InsertUpdateNodeAttrHistory(attrValueIdForattrHistory);
			docMgmtImpl.CopyFileForRepository(srcWsId,destWsId,srcNodeId,destNodeId,userId,TranNo);
//			docMgmtImpl.copyLeafAttributes(srcWsId,destWsId,srcNodeId,destNodeId,userId,TranNo);
    	
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
	