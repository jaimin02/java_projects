package com.docmgmt.struts.actions.search;

import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class ShowAttributeSearchResultAction extends ActionSupport
{	
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String attrId;
	public String attrValue;
	public Vector<DTOWorkSpaceNodeDetail> nodeDetail;

	
	@Override
	public String execute()
	{
		attrValue=attrValue.replace("'","''");
		//nodeDetail = docMgmtImpl.getNodeDetailOnAttributeValue(attrId,attrValue);	
		nodeDetail = docMgmtImpl.getNodeDetailByAttributeIdandValue(attrId,attrValue);
		return SUCCESS;
	}
}

