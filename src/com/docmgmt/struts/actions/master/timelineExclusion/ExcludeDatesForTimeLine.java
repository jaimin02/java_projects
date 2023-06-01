package com.docmgmt.struts.actions.master.timelineExclusion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import com.docmgmt.dto.DTOTimelineDateExclusion;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ExcludeDatesForTimeLine extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{

		//excludedDateDetail=docMgmtImpl.getExcludedDateDetail();	
		return SUCCESS;
	}	
	
	public String Savedate() throws ParseException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		DTOTimelineDateExclusion dto = new DTOTimelineDateExclusion();
		dto.setRemark("New");
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setModifyBy(userId);
		Date dt = dateFormat.parse(date);
	    dto.setExcludedDate(dt);
	    dto.setRemark(remark);
	    dto.setModifyBy(userId);
	    
	   /* boolean dateExist = docMgmtImpl.dateExist(date.trim());
	    if(!dateExist)
	        docMgmtImpl.DateMstOp(dto, 1,false);
		else{
			addActionError("Date Already Exists!");
			return INPUT;
		}*/
	    
		return SUCCESS;
	}
	
	
	public String dateHistory(){
		//excludedDateHistory = docMgmtImpl.getdateHistory(date);
		return SUCCESS;
	}
	
	public String checkExcludingDate(){
		//excludedDateHistory = docMgmtImpl.getdateHistory(date);
		return SUCCESS;
	}
	
	public Vector excludedDateHistory;
	public Vector excludedDateDetail;
	public String date;
	public String remark;
	public String fromDate;
	public String toDate;
	public Vector getClientDetail() {
		return excludedDateDetail;
	}

	public void setClientDetail(Vector excludedDateDetail) {
		this.excludedDateDetail = excludedDateDetail;
	}

	

}
