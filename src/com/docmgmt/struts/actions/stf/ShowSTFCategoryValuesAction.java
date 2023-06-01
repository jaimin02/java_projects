package com.docmgmt.struts.actions.stf;



import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class ShowSTFCategoryValuesAction extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		
		STFCatValues=docMgmtImpl.getSTFCategoryValues(categoryNameId);
		//System.out.println("selectedcategoryid:::"+categoryNameId);
		//System.out.println("vector size:::"+STFCatValues.size());
		return SUCCESS;
	}
	

	public String categoryNameId;
	public Vector STFCatValues;

	public String getCategoryNameId() {
		return categoryNameId;
	}

	public void setCategoryNameId(String categoryNameId) {
		this.categoryNameId = categoryNameId;
	}

	public Vector getSTFCatValues() {
		return STFCatValues;
	}

	public void setSTFCatValues(Vector catValues) {
		STFCatValues = catValues;
	}
	
	
	
	
}
