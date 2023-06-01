package com.docmgmt.test;

import com.docmgmt.test.dao.TestDAO;
import com.opensymphony.xwork2.ActionSupport;

public class EditLocationTest extends ActionSupport {

	private static final long serialVersionUID = 1L;
	public String LocationCode;
	public String LocationName;
	public String htmlContent="";
	@Override
	public String execute() {
		
		return SUCCESS;
	}
	public String getLocationCode() {
		return LocationCode;
	}
	public void setLocationCode(String locationCode) {
		LocationCode = locationCode;
	}
	public String getLocationName() {
		return LocationName;
	}
	public void setLocationName(String locationName) {
		LocationName = locationName;
	}
	public String update()
	{
		TestDAO tdo=new TestDAO();
		tdo.UpdateCountry(this);
		return SUCCESS;
	}
	public String delete()
	{
		System.out.println("Location Code="+LocationCode);
		TestDAO tdo=new TestDAO();
		tdo.DeleteCountry(this);
		htmlContent = "<center><p style='color:red;'>Location Removed Successfully..</p><a href='ShowLocationTest.do'>BACK</a></center>";
		return SUCCESS;
	}
	public String add()
	{
		System.out.println("Location Name="+LocationName);
		TestDAO tdo=new TestDAO();
		tdo.AddCountry(this);
		htmlContent = "<center><p style='color:red;'>Location Added Successfully..</p><a href='ShowLocationTest.do'>BACK</a></center>";
		return SUCCESS;
	}
	
}
