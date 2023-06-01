package com.docmgmt.struts.actions.labelandpublish;

import java.util.Vector;

import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.DocMgmtImpl;

public class ViewNodeDetailAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		getViewNodeDetail = docMgmtImpl.viewNodeDetailUsingWsIdAndLabelNo(workSpaceId, labelNo);
		//System.out.println("size:"+getViewNodeDetail.size());
		
		return SUCCESS;
	}
	
	public String workSpaceId;
	public int labelNo;
	public Vector getViewNodeDetail;

	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public int getLabelNo() {
		return labelNo;
	}
	public void setLabelNo(int labelNo) {
		this.labelNo = labelNo;
	}
	public Vector getGetViewNodeDetail() {
		return getViewNodeDetail;
	}
	public void setGetViewNodeDetail(Vector getViewNodeDetail) {
		this.getViewNodeDetail = getViewNodeDetail;
	}
}
	
	


