package com.docmgmt.struts.actions.search;

import java.util.Vector;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class GetAttributeValueAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		
		attrValueDtl = docMgmtImpl.getAttrDetailById(attrId);
		if(attrValueDtl.size() != 0 )
		 {
             for(int k = 0 ; k < attrValueDtl.size();k++ )
             {
                 DTOAttributeMst dto = (DTOAttributeMst)attrValueDtl.get(k);
                 type = dto.getAttrType();
             }
	    } 
		attrValueMatrixDtl = docMgmtImpl.getAttributeValueByAttrId(attrId);
		return SUCCESS;
	}
	
	public int attrId;
	public String type;
	public Vector attrValueDtl;
	public Vector attrValueMatrixDtl;
	
	
	
	public Vector getAttrValueMatrixDtl() {
		return attrValueMatrixDtl;
	}
	public void setAttrValueMatrixDtl(Vector attrValueMatrixDtl) {
		this.attrValueMatrixDtl = attrValueMatrixDtl;
	}
	public int getAttrId() {
		return attrId;
	}
	public void setAttrId(int attrId) {
		this.attrId = attrId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Vector getAttrValueDtl() {
		return attrValueDtl;
	}
	public void setAttrValueDtl(Vector attrValueDtl) {
		this.attrValueDtl = attrValueDtl;
	}
	
}

