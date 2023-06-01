package com.docmgmt.struts.actions.WelcomePage.reminders;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.docmgmt.dto.DTOReminder;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkspaceNodeReminderDoneStatus;
import com.docmgmt.dto.DTOWorkspaceNodeReminderIgnoreStatus;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.webinterface.services.cal.CalendarUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowReminders extends ActionSupport
{
	public String reminder;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public ArrayList<DTOReminder> remMsg=new ArrayList<DTOReminder>();
	/*
	 * showWhat=1 : All
	 * showWhat=2 : Active
	 * showWhat=3 : Ignored
	 * showWhat=4 : Done
	 * */
	public String showWhat;
	public String showReminder;
	public int userCode=0;
	public int daysDiff=30;
	
	//
	
	
	@Override
	public String execute() throws Exception 
	{
		
		
		if (showWhat==null || showWhat.length() == 0 || showWhat.equals(""))
			showWhat="1";
		KnetProperties prop = KnetProperties.getPropInfo("Welcomepage.properties");
		reminder = prop.getValue("Reminder");
		
		if (reminder!=null || reminder.equals("Yes"))
        {
			if(userCode == 0)
				userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	        ArrayList<DTOWorkSpaceNodeAttrDetail> remListFromDB = docMgmtImpl.getReminderEvents(userCode);
	        ArrayList<DTOWorkspaceNodeReminderDoneStatus> remDoneList = docMgmtImpl.getReminderDoneStatus();
	        ArrayList<DTOWorkspaceNodeReminderIgnoreStatus> remIgnoreList = docMgmtImpl.getReminderIgnoreStatus(userCode);
	        Calendar c=new GregorianCalendar();
	        String mon="0"+ (c.get(Calendar.MONTH)+1);
	        mon=mon.substring(mon.length()-2);
	        String date1="0"+c.get(Calendar.DATE);
	        date1=date1.substring(date1.length()-2);
	        String strtoday=c.get(Calendar.YEAR) + "/" + mon + "/"  + date1;
	        if (remListFromDB!=null && remListFromDB.size()>0)
	        {        	
	        	for (int i=0;i<remListFromDB.size();i++)
	        	{
	        		DTOWorkSpaceNodeAttrDetail dto=remListFromDB.get(i);	        		
	        		int diff=CalendarUtils.dateDifferenceInDays(CalendarUtils.stringToCalendar(strtoday),CalendarUtils.stringToCalendar(dto.getAttrValue()));
	        		if (diff <= daysDiff)
	        		{
	        			boolean dfound=false;
	        			if (remDoneList!=null && remDoneList.size()>0)
	        			{
	        				for (int j=0;j<remDoneList.size();j++)
	        				{
	        					DTOWorkspaceNodeReminderDoneStatus dtoWorkspaceNodeReminderDoneStatus=remDoneList.get(j);
	        					if (dtoWorkspaceNodeReminderDoneStatus.getvWorkspaceId().equals(dto.getWorkspaceId()) 
	        							&& dtoWorkspaceNodeReminderDoneStatus.getiNodeId()==dto.getNodeId() 
	        							&& dtoWorkspaceNodeReminderDoneStatus.getiAttrId()==dto.getAttrId())
	        					{
	        						dfound=true;
	        						break;
	        					}
	        				}
	        			}
	        			boolean ifound=false;
	        			if (remIgnoreList!=null && remIgnoreList.size()>0)
	        			{
	        				for (int j=0;j<remIgnoreList.size();j++)
	        				{
	        					DTOWorkspaceNodeReminderIgnoreStatus dtoWorkspaceNodeReminderIgnoreStatus=remIgnoreList.get(j);
	        					if (dtoWorkspaceNodeReminderIgnoreStatus.getvWorkspaceId().equals(dto.getWorkspaceId()) 
	        							&& dtoWorkspaceNodeReminderIgnoreStatus.getiNodeId()==dto.getNodeId() 
	        							&& dtoWorkspaceNodeReminderIgnoreStatus.getiAttrId()==dto.getAttrId()
	        							&& dtoWorkspaceNodeReminderIgnoreStatus.getiUserCode()==userCode)	        							
	        					{
	        						Calendar curIgnoreUpto=CalendarUtils.timestampToCalendar(dtoWorkspaceNodeReminderIgnoreStatus.getdIgnoreUpto());
	        						int dtDiff=CalendarUtils.dateDifferenceInDays(CalendarUtils.stringToCalendar(strtoday),curIgnoreUpto);
	        						if (dtDiff>=0)
	        						{
	        							ifound=true;
	        							break;
	        						}
	        					}
	        				}
	        			}
	        			if (showWhat.equals("1"))
	        			{
	        				DTOReminder ele=new DTOReminder();
	        				ele.setAttrName(dto.getAttrName());
		        			ele.setWorkspaceDesc(dto.getWorkSpaceDesc());
		        			ele.setAttrValue(dto.getAttrValue());
		        			ele.setNodeId(dto.getNodeId());
		        			ele.setNodeDisplayName(dto.getNodeDetailName());
		        			ele.setAttrId(dto.getAttrId());
		        			ele.setAttrForIndiId(dto.getAttrForIndi());
		        			ele.setWorkspaceId(dto.getWorkspaceId());
		        			ele.setUserCode(userCode);
		        			ele.setDone(dfound);
		        			ele.setIgnore(ifound);
		        			remMsg.add(ele);
	        			}
	        			else if (showWhat.equals("2") && !dfound && !ifound)
	        			{
	        				DTOReminder ele=new DTOReminder();
	        				ele.setAttrName(dto.getAttrName());
		        			ele.setWorkspaceDesc(dto.getWorkSpaceDesc());
		        			ele.setAttrValue(dto.getAttrValue());
		        			ele.setNodeId(dto.getNodeId());
		        			ele.setNodeDisplayName(dto.getNodeDetailName());
		        			ele.setAttrId(dto.getAttrId());
		        			ele.setAttrForIndiId(dto.getAttrForIndi());
		        			ele.setWorkspaceId(dto.getWorkspaceId());
		        			ele.setUserCode(userCode);
		        			ele.setDone(dfound);
		        			ele.setIgnore(ifound);
		        			remMsg.add(ele);
	        			}
	        			else if (showWhat.equals("3") && ifound)	        					
	        			{
	        				DTOReminder ele=new DTOReminder();
	        				ele.setAttrName(dto.getAttrName());
		        			ele.setWorkspaceDesc(dto.getWorkSpaceDesc());
		        			ele.setAttrValue(dto.getAttrValue());
		        			ele.setNodeId(dto.getNodeId());
		        			ele.setNodeDisplayName(dto.getNodeDetailName());
		        			ele.setAttrId(dto.getAttrId());
		        			ele.setAttrForIndiId(dto.getAttrForIndi());
		        			ele.setWorkspaceId(dto.getWorkspaceId());
		        			ele.setUserCode(userCode);
		        			ele.setDone(dfound);
		        			ele.setIgnore(ifound);
		        			remMsg.add(ele);
	        			}
	        			else if (showWhat.equals("4") && dfound)
	        			{
	        				DTOReminder ele=new DTOReminder();
	        				ele.setAttrName(dto.getAttrName());
		        			ele.setWorkspaceDesc(dto.getWorkSpaceDesc());
		        			ele.setAttrValue(dto.getAttrValue());
		        			ele.setNodeId(dto.getNodeId());
		        			ele.setNodeDisplayName(dto.getNodeDetailName());
		        			ele.setAttrId(dto.getAttrId());
		        			ele.setAttrForIndiId(dto.getAttrForIndi());
		        			ele.setWorkspaceId(dto.getWorkspaceId());
		        			ele.setUserCode(userCode);
		        			ele.setDone(dfound);
		        			ele.setIgnore(ifound);
		        			remMsg.add(ele);
	        			}
	        			/*
	        			 * if ((showWhat.equals("1")) || 
	        					(showWhat.equals("2") && !dfound && !ifound) || 
	        					(showWhat.equals("3") && !ifound) || 
	        					(showWhat.equals("4") && !dfound))*/
	        			//remMsg+="\\t" + dto.getAttrName() + " - " + dto.getWorkSpaceDesc() + " (" + dto.getAttrValue() + ")" + "\\r\\n";	        				        	
	        		}
	        	}	        	
	        }
        }
		//setCommentsDetails();
		
		return SUCCESS;
	}
	
}