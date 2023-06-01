package com.docmgmt.struts.actions.reports.PostSubQueryMgmt;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionQueryDtl;
import com.docmgmt.dto.DTOSubmissionQueryMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PostSubQueryMgmtReport extends ActionSupport {
	private static final long serialVersionUID = 8418044334005158992L;
	private DocMgmtImpl docMgmtImpl= new DocMgmtImpl();
	private PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	
	public ArrayList<String> statusValues = new ArrayList<String>();
	public ArrayList<DTOWorkSpaceMst> wsMstList = new ArrayList<DTOWorkSpaceMst>();
	public String workSpaceId;
	public ArrayList<DTOSubmissionQueryMst> submissionQueryMsts;
	//public File subQuery;
	public String subQuery;
	public String status;
	public String fromDate;
	public String toDate;
	public String queryOn;
	
	@Override
	public String execute() throws Exception {
		statusValues.add("New");
		statusValues.add("In Process");
		statusValues.add("Pending");
		statusValues.add("Resolved");
		
		ArrayList<String> wsIdList = docMgmtImpl.getWorkspaceIdForSubmittedQuery();
		int userCode =Integer.parseInt( ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt( ActionContext.getContext().getSession().get("usergroupcode").toString());
		Vector<DTOWorkSpaceMst> workspaceMstList = docMgmtImpl.getUserWorkspace(userGroupCode,userCode);
		
		for (int itrWsIdList = 0; itrWsIdList < wsIdList.size(); itrWsIdList++) 
		{
			String wsId = wsIdList.get(itrWsIdList);
			for (int itrWsMstList = 0; itrWsMstList < workspaceMstList.size(); itrWsMstList++) 
			{
				DTOWorkSpaceMst dtoWsMst = workspaceMstList.get(itrWsMstList);
				if(wsId.equals(dtoWsMst.getWorkSpaceId()))
					wsMstList.add(dtoWsMst);
			}
		}
		return SUCCESS;
	}
	
	public String getReportDetail()
	{
		//subQuery = propertyInfo.getDir("PostSubmissionQueryMgmt");
		subQuery = propertyInfo.getValue("PostSubmissionQueryMgmt");
		if(workSpaceId.equalsIgnoreCase("All"))
			workSpaceId=null;
		submissionQueryMsts = docMgmtImpl.getWorkspaceQueryDtls(workSpaceId);
		Vector<DTOUserMst> userMst = docMgmtImpl.getAllUserDetail();
		for (int itrSubQueryMst = 0; itrSubQueryMst < submissionQueryMsts.size(); itrSubQueryMst++) 
		{
			DTOSubmissionQueryMst dtoSubQueryMst = submissionQueryMsts.get(itrSubQueryMst);
			ArrayList<DTOSubmissionQueryDtl> subQueryDtlList = dtoSubQueryMst.getSubmissionQueryDtls();
			for (int itrSubQueryDtl = 0; itrSubQueryDtl < subQueryDtlList.size() ; itrSubQueryDtl++) 
			{
				DTOSubmissionQueryDtl dtoSubQueryDtl = subQueryDtlList.get(itrSubQueryDtl);
				int modifyBy =dtoSubQueryDtl.getModifyBy();
				int resolvedBy =dtoSubQueryDtl.getResolvedBy();
				if(modifyBy > 0)
				{
					DTOUserMst dtoUserMstModify = getUserDetails(modifyBy,userMst);
					dtoSubQueryDtl.setModifyByName(dtoUserMstModify.getUserName());
				}
				if(resolvedBy > 0)
				{
					DTOUserMst dtoUserMstResolved = getUserDetails(resolvedBy,userMst);
					dtoSubQueryDtl.setResolvedByName(dtoUserMstResolved.getUserName());
				}
			}
		}
		if(status != null && !status.equalsIgnoreCase("All"))
		{
			statusWiseReport();
		}
		
		if (fromDate != null && toDate != null && queryOn != null) 
		{
			dateWiseReport();
		}
		return SUCCESS;
	}
	
	private DTOUserMst getUserDetails(int userCode,Vector<DTOUserMst> userMst)
	{
		DTOUserMst dtoUserMst = new DTOUserMst();
		for (int itrUserMst = 0; itrUserMst < userMst.size(); itrUserMst++) 
		{
			dtoUserMst = userMst.get(itrUserMst);
			if(userCode == dtoUserMst.getUserCode())
			{
				return dtoUserMst;
			}
		}
		return dtoUserMst;
	}

	private void statusWiseReport()
	{
		ArrayList<DTOSubmissionQueryMst> subQueryMsts = submissionQueryMsts;
		submissionQueryMsts = null;
		submissionQueryMsts = new ArrayList<DTOSubmissionQueryMst>();
		String[] statusList = status.split("@@");
		
		for (int itrStatusList = 0; itrStatusList < statusList.length; itrStatusList++) 
		{
			String currStatus = statusList[itrStatusList];
			for (int itrSubQueryMst = 0; itrSubQueryMst < subQueryMsts.size(); itrSubQueryMst++) 
			{
				DTOSubmissionQueryMst dtoSubQueryMst = subQueryMsts.get(itrSubQueryMst);
				ArrayList<DTOSubmissionQueryDtl> subQueryDtlList = dtoSubQueryMst.getSubmissionQueryDtls();
				ArrayList<DTOSubmissionQueryDtl> submissionQueryDtlList = new ArrayList<DTOSubmissionQueryDtl>();
				for (int itrSubQueryDtl = 0; itrSubQueryDtl < subQueryDtlList.size() ; itrSubQueryDtl++) 
				{
					DTOSubmissionQueryDtl dtoSubQueryDtl = subQueryDtlList.get(itrSubQueryDtl);
					String queryStatus = dtoSubQueryDtl.getQueryStatus();
					if(queryStatus.equalsIgnoreCase(currStatus))
					{
						submissionQueryDtlList.add(dtoSubQueryDtl);
					}
				}
				if(submissionQueryDtlList.size() > 0)
				{
					dtoSubQueryMst.setSubmissionQueryDtls(submissionQueryDtlList);
					submissionQueryMsts.add(dtoSubQueryMst);
				}
			}
		}
	}
	
	private void dateWiseReport()
	{
		Timestamp fDate = new Timestamp(new Date().getTime());
		Timestamp tDate = new Timestamp(new Date().getTime());
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
		try {
			fDate= new Timestamp(dateFormat.parse(fromDate).getTime());
			tDate= new Timestamp(dateFormat.parse(toDate).getTime());
			tDate.setHours(23);
			tDate.setMinutes(59);
			tDate.setSeconds(59);
		} catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		ArrayList<DTOSubmissionQueryMst> subQueryMsts = new ArrayList<DTOSubmissionQueryMst>();
		subQueryMsts = submissionQueryMsts;
		submissionQueryMsts = null;
		submissionQueryMsts = new ArrayList<DTOSubmissionQueryMst>();
		
		for (int itrSubQueryMst = 0; itrSubQueryMst < subQueryMsts.size(); itrSubQueryMst++) 
		{
			DTOSubmissionQueryMst dtoSubQueryMst = subQueryMsts.get(itrSubQueryMst);
			if (queryOn.equalsIgnoreCase("startOn")) 
			{
				Timestamp startDate = dtoSubQueryMst.getStartDate();
				if(fDate.getTime() <= startDate.getTime() && startDate.getTime() <= tDate.getTime() )
				{
					submissionQueryMsts.add(dtoSubQueryMst);
					
				}
			}
			if (queryOn.equalsIgnoreCase("endOn")) 
			{
				Timestamp endDate = dtoSubQueryMst.getEndDate();
				if(fDate.getTime() <= endDate.getTime() && endDate.getTime() <= tDate.getTime() )
				{
					submissionQueryMsts.add(dtoSubQueryMst);
				}
			}
			if (queryOn.equalsIgnoreCase("modifyOn")) 
			{
				ArrayList<DTOSubmissionQueryDtl> subQueryDtlList = dtoSubQueryMst.getSubmissionQueryDtls();
				ArrayList<DTOSubmissionQueryDtl> submissionQueryDtlList = new ArrayList<DTOSubmissionQueryDtl>();
				for (int itrSubQueryDtl = 0; itrSubQueryDtl < subQueryDtlList.size() ; itrSubQueryDtl++) 
				{
					DTOSubmissionQueryDtl dtoSubQueryDtl = subQueryDtlList.get(itrSubQueryDtl);
					Timestamp modifyOn = dtoSubQueryDtl.getModifyOn();
					if(fDate.getTime() <= modifyOn.getTime() && modifyOn.getTime() <= tDate.getTime() )
					{
						submissionQueryDtlList.add(dtoSubQueryDtl);
					}
				}
				if(submissionQueryDtlList.size() > 0)
				{
					dtoSubQueryMst.setSubmissionQueryDtls(submissionQueryDtlList);
					submissionQueryMsts.add(dtoSubQueryMst);
				}
			}
		}
	}
}