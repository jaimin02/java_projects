package com.docmgmt.struts.actions.master.ApplicationGroup;



import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class AddApplicationGroupAction extends ActionSupport implements Preparable {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		return SUCCESS;
	}	
	
	
	public void prepare() throws Exception {
		
		applicationDetail=docMgmtImpl.getApplicationDetail();
		applicationHostDetail=docMgmtImpl.getApplicationHostingDetail();
		applicationCategoryDetail=docMgmtImpl.getApplicationCategoryDetail();
	}

    public Vector applicationDetail;
    public Vector applicationHostDetail;
    public Vector applicationCategoryDetail;
    public String applicationName;

	public Vector getapplicationDetail() {
		return applicationDetail;
	}

	public void setapplicationDetail(Vector applicationDetail) {
		this.applicationDetail = applicationDetail;
	}

	public String getapplicationName() {
		return applicationName;
	}

	public void setapplicationName(String applicationName) {
		this.applicationName = applicationName;
	}


	public Vector getApplicationHostDetail() {
		return applicationHostDetail;
	}


	public void setApplicationHostDetail(Vector applicationHostDetail) {
		this.applicationHostDetail = applicationHostDetail;
	}


	public Vector getApplicationCategoryDetail() {
		return applicationCategoryDetail;
	}


	public void setApplicationCategoryDetail(Vector applicationCategoryDetail) {
		this.applicationCategoryDetail = applicationCategoryDetail;
	}
	
	
	/**********************************************/
	
	
	
}
