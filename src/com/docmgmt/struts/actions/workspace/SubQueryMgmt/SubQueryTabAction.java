package com.docmgmt.struts.actions.workspace.SubQueryMgmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionQueryDtl;
import com.docmgmt.dto.DTOSubmissionQueryMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class SubQueryTabAction extends ActionSupport {
	public ArrayList<HashMap<String, Object>> subqueryMsg = new ArrayList<HashMap<String, Object>>();
	public DocMgmtImpl docMgmtImpl = new DocMgmtImpl();

	@Override
	public String execute() {

		long QueryId;
		String QueryTitle;
		String project;
		String Country;
		int Total;
		int UnResolved;
		HashMap<String, Object> hoQueryDetail;
		ArrayList<DTOSubmissionQueryMst> dtoSubQueryMstList;
		DTOSubmissionQueryMst dtoSubQueryMst;
		DTOSubmissionQueryDtl dtoSubQueryDtl;
		Vector<DTOWorkSpaceMst> workspaces;
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());

		workspaces = docMgmtImpl.getUserWorkspace(userGroupCode, userCode);
		
		for (int itrWsMst = 0; itrWsMst < workspaces.size(); itrWsMst++) 
		{
			DTOWorkSpaceMst dto = workspaces.get(itrWsMst);
			
			dtoSubQueryMstList = docMgmtImpl.getWorkspaceQueryDtlsByUser(dto.getWorkSpaceId(),userCode);
			
			//dtoSubQueryMstList = docMgmtImpl.getWorkspaceQueryDtls(dto.getWorkSpaceId());
			
			for (int i = 0; i < dtoSubQueryMstList.size(); i++) 
			{
				hoQueryDetail = new HashMap<String, Object>();
				dtoSubQueryMst = dtoSubQueryMstList.get(i);
				dtoSubQueryDtl =dtoSubQueryMstList.get(i).getSubmissionQueryDtls().get(0);
				
				hoQueryDetail.put("wid",dto.getWorkSpaceId());
				hoQueryDetail.put("QueryId",dtoSubQueryMst.getQueryId()); 
				hoQueryDetail.put("Project",new String(dtoSubQueryDtl.getWorkSpaceDesc()));		
				hoQueryDetail.put("Country",new String(dtoSubQueryDtl.getLocationName()));
				hoQueryDetail.put("QueryTitle",new String(dtoSubQueryMst.getQueryTitle()));
				Total=dtoSubQueryMst.getSubmissionQueryDtls().size();			 
				hoQueryDetail.put("Total",Total);
				UnResolved=(Total-CountResolvedQuery(dtoSubQueryMstList.get(i).getSubmissionQueryDtls()));			 
				hoQueryDetail.put("UnResolved",UnResolved);		
				setResolvedByName(dtoSubQueryMstList.get(i).getSubmissionQueryDtls());
				hoQueryDetail.put("queryDtl",dtoSubQueryMstList.get(i).getSubmissionQueryDtls());
		 	 	if(UnResolved>0) 
			 		subqueryMsg.add(hoQueryDetail);		
			}
		}
		return SUCCESS;
	}

	private int CountResolvedQuery(ArrayList<DTOSubmissionQueryDtl> submissionQueryDtls) {
		int counter = 0;
		for (int i = 0; i < submissionQueryDtls.size(); i++) {
			if (submissionQueryDtls.get(i).getQueryStatus().equalsIgnoreCase("resolved"))
				counter++;
		}
		return counter;
	}
	
	private void setResolvedByName(ArrayList<DTOSubmissionQueryDtl> submissionQueryDtls) {
		Vector<DTOUserMst> allUsers = docMgmtImpl.getAllUserDetail();
		for (Iterator<DTOUserMst> itrUsers = allUsers.iterator(); itrUsers.hasNext();) {
			DTOUserMst dtoUserMst =  itrUsers.next();
			
			for (Iterator<DTOSubmissionQueryDtl> itrSubQueryDtls = submissionQueryDtls.iterator(); itrSubQueryDtls.hasNext();) {
				DTOSubmissionQueryDtl dtoSubmissionQueryDtl =  itrSubQueryDtls.next();
				if(dtoUserMst.getUserCode() == dtoSubmissionQueryDtl.getResolvedBy()){
					dtoSubmissionQueryDtl.setResolvedByName(dtoUserMst.getUserName());
				}
			}
			
		}
		
	}

}
