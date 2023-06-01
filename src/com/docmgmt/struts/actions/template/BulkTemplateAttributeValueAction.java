package com.docmgmt.struts.actions.template;

import java.util.Vector;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class BulkTemplateAttributeValueAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public Vector getTempAllAttributes=new Vector();
	
	
	@Override
	public String execute()
	{
		getTemplateDetail = docMgmtImpl.getAllTemplates();
		getAllAttributes = docMgmtImpl.getAttrForAttrGroupBySpecifiedAttrType("");
	//	System.out.println("size of temp vector:::"+getAllAttributes.size());
		DTOAttributeMst dto = new DTOAttributeMst();
		
		for(int i=0;i<getAllAttributes.size();i++)
		{
			
			dto = (DTOAttributeMst)getAllAttributes.get(i);
			
			
			
			if(!dto.getAttrForIndiId().equals("0001"))
			{
				getTempAllAttributes.addElement(dto);
			}
			
			dto= new DTOAttributeMst();
		}
		
	//	System.out.println("size of vector:::"+getTempAllAttributes.size());
	 	return SUCCESS;
	}
	
	public Vector getTemplateDetail;
	public Vector getAllAttributes;
	


	public Vector getGetTemplateDetail() {
		return getTemplateDetail;
	}
	public void setGetTemplateDetail(Vector getTemplateDetail) {
		this.getTemplateDetail = getTemplateDetail;
	}
	public Vector getGetTempAllAttributes() {
		return getTempAllAttributes;
	}
	public void setGetTempAllAttributes(Vector getTempAllAttributes) {
		this.getTempAllAttributes = getTempAllAttributes;
	}
	
	
}
