package com.docmgmt.struts.actions.master.location;

import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;

public class EditLocationAction extends ActionSupport {//implements Preparable{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	@Override
	public String execute()
	{
		
	    return SUCCESS;
		
		
	}
	
	public String locationCode;
	public String locationName;
	
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	
	
	

}