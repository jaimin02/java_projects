package com.docmgmt.struts.actions.master.location;

import com.docmgmt.dto.DTOLocationMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveLocationAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	
	@Override
	public String execute()
	{
		DTOLocationMst dto = new DTOLocationMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setRemark("remark");
		dto.setModifyBy(userId);
	    dto.setLocationName(locationName.trim());
	 
	    boolean locationExist = docMgmtImpl.locationNameExist(locationName.trim());
	    if(!locationExist)
	    	docMgmtImpl.LocationMstOp(dto, 1,false);
	    else
	    	addActionError("Region Name Already Exist");
	    
	    return SUCCESS;
	    
	}
	
	public String UpdateLocation()
	{
		DTOLocationMst dto = docMgmtImpl.getLocationInfo(locationCode);
		dto.setLocationName(locationName.trim());
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setModifyBy(userId);
	    boolean locationExist = docMgmtImpl.locationNameExist(locationName.trim());
	    if(!locationExist)
	    {
	    	docMgmtImpl.LocationMstOp(dto,2,false);
	    }
	    return SUCCESS;
		
	}
		
	public String locationName;
	public String locationCode;
	public String statusIndi;

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
	public String getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}
}
