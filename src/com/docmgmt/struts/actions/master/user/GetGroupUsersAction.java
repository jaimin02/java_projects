package com.docmgmt.struts.actions.master.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOUserGroupMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetGroupUsersAction extends ActionSupport
{
	DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String usrType;
	public Vector<DTOUserGroupMst> usrGrps;
	public Vector<DTOUserMst> usrList;
	public Vector<DTOStageMst> stages;
	public String frmDate;
	public String toDate;
	public String no;
	public int groupCount;
	public Vector<Integer> grpWiseUserCount;
	@Override
	public String execute() throws Exception 
	{		
		grpWiseUserCount=new Vector<Integer>();
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String FosunChanges=PropertyInfo.getPropInfo().getValue("FosunCustomization");
	    if(FosunChanges.equalsIgnoreCase("yes")){
	    	String clientCodeGroup=docMgmtImpl.getUserGroupClientCode(userGroupCode);
	    	usrGrps=docMgmtImpl.getAllUserGroupByUserTypeandClientCode(usrType,clientCodeGroup);
	    }else{
	    	usrGrps=docMgmtImpl.getAllUserGroupByUserType(usrType);
	    }
		
		usrList=docMgmtImpl.getAllUserDetail();
		int ucode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		for (int i=0;i<usrGrps.size();i++)
		{
			DTOUserGroupMst grp=usrGrps.get(i);
			grp.setUsers(new ArrayList<DTOUserMst>());
			int cnt=0;
			for(int j=0;j<usrList.size();j++)
			{
				DTOUserMst usr=usrList.get(j);
				if (grp.getUserGroupCode()==usr.getUserGroupCode() && usr.getStatusIndi()!='D' && usr.getUserCode()!=ucode)
				{
					grp.getUsers().add(usr);
					cnt++;
				}
			}
			grpWiseUserCount.add(cnt);
		}
		for (int i = 0;i < grpWiseUserCount.size();i++)
		{
			//If user group has 0 users, remove that user group from list.
			if(grpWiseUserCount.get(i) == 0)
			{
				usrGrps.removeElementAt(i);
				grpWiseUserCount.removeElementAt(i);
				i--;
			}
		}
		stages=docMgmtImpl.getStageDetail();
		Calendar c=new GregorianCalendar();		
		int date=c.get(Calendar.DATE);
		String month="0" + (c.get(Calendar.MONTH)+1);
		month=month.substring(month.length()-2);
		int year=c.get(Calendar.YEAR);
		frmDate=year + "/" + month + "/" + date;
		//for admin hardcode of 50 years
		if (usrType.equals("0002"))	
			year+=50;		
		toDate=year + "/" + month + "/" + date;
		groupCount=usrGrps.size();			
		return SUCCESS;
	}
}