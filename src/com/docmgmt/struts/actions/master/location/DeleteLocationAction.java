package com.docmgmt.struts.actions.master.location;

import com.docmgmt.dto.DTOLocationMst;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;

public class DeleteLocationAction extends ActionSupport   {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		
		DTOLocationMst dto = docMgmtImpl.getLocationInfo(locationCode);
		dto.setStatusIndi(statusIndi.charAt(0));
		docMgmtImpl.LocationMstOp(dto,2,true) ;
	    return SUCCESS;
	}
	
	public String statusIndi;
	public String locationCode;


	public String getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	
	/**********************************************/
	
}
