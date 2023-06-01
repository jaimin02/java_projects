package com.docmgmt.struts.actions.workspace.SubQueryMgmt;

import java.util.ArrayList;

import com.docmgmt.dto.DTOSubmissionQueryMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionSupport;

public class ShowQueryDetailsAction extends ActionSupport {
	private static final long serialVersionUID = -3926571547336126708L;
	public String workSpaceId;
	public ArrayList<DTOSubmissionQueryMst> submissionQueryMsts;
	//public File subQuery;
	public String subQuery;
	PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public String mi;
	
	@Override
	public String execute(){
		submissionQueryMsts = docMgmtImpl.getWorkspaceQueryDtls(workSpaceId);
		//subQuery = propertyInfo.getDir("PostSubmissionQueryMgmt");
		subQuery = propertyInfo.getValue("PostSubmissionQueryMgmt");
		return SUCCESS;
	}

}


 
