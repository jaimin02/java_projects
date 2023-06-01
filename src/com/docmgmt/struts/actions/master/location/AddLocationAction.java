package com.docmgmt.struts.actions.master.location;



import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class AddLocationAction extends ActionSupport implements Preparable{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		
		return SUCCESS;
	}
	
	public  void prepare()throws Exception{
		
		locationDetail=docMgmtImpl.getLocationDtl();
	}
	public String locationName;
	public Vector locationDetail;

	public String getLocationName() {
		return locationName;
	}
	
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Vector getLocationDetail() {
		return locationDetail;
	}

	public void setLocationDetail(Vector locationDetail) {
		this.locationDetail = locationDetail;
	}
	

}
