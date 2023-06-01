package com.docmgmt.struts.actions.Notice;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.docmgmt.dto.DTONoticeMst;
import com.docmgmt.dto.DTOUserTypeMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowNoticesAction extends ActionSupport
{
	DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public Vector<DTONoticeMst> allNotices;
	public Vector<DTOUserTypeMst> userTypes;
	public String attachmentsFolderPath;
	public String startDate;
	public String endDate;
	
	public Vector<DTONoticeMst> getAllNotices() {
		return allNotices;
	}

	public void setAllNotices(Vector<DTONoticeMst> allNotices) {
		this.allNotices = allNotices;
	}

	public String showNotices()
	{		
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());		
		allNotices=docMgmtImpl.getAllNotices(userCode);
		userTypes=docMgmtImpl.getAllUserType();
		attachmentsFolderPath=PropertyInfo.getPropInfo().getValue("AttachmentsFolder");
		
		Calendar c = new GregorianCalendar();
		startDate = endDate = ""+c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DATE);
		return SUCCESS;
	}
}