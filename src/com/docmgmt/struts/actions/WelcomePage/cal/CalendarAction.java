package com.docmgmt.struts.actions.WelcomePage.cal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.services.cal.CalendarUtils;
import com.docmgmt.server.webinterface.services.cal.Event;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Nagesh
 *
 */
public class CalendarAction extends ActionSupport 
{
	
	private static final long serialVersionUID = 1L;
	
	int month=-1;
	int year=-1;
	int pmonth;
	int pyear;
	int nmonth;
	int nyear;	
	int selMonth;
	int selYear;	
	String calendarHTML;	
	public ArrayList<DTOAttributeMst> evtAttrs;	
	public String attrlist;
	public String attrRemlist;
	public String attrChoices;
	public String remnChoices;
	//public String remMsg="";
	public int nextNoOfYears=15;
	public int prevNoOfYears=5;
	public String getAttrlist() {
		return attrlist;
	}

	public void setAttrlist(String attrlist) {
		this.attrlist = attrlist;
	}

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	//WorkspaceNodeAttrDetail docMgmtImpl=new WorkspaceNodeAttrDetail();
	
	public int getSelMonth() {
		return selMonth;
	}

	public void setSelMonth(int selMonth) {
		this.selMonth = selMonth;
	}

	public int getSelYear() {
		return selYear;
	}

	public void setSelYear(int selYear) {
		this.selYear = selYear;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public String getCalendarHTML() {
		return calendarHTML;
	}

	public void setCalendarHTML(String calendarHTML) {
		this.calendarHTML = calendarHTML;
	}
	
	public int getPmonth() {
		return pmonth;
	}

	public void setPmonth(int pmonth) {
		this.pmonth = pmonth;
	}

	public int getPyear() {
		return pyear;
	}

	public void setPyear(int pyear) {
		this.pyear = pyear;
	}

	public int getNmonth() {
		return nmonth;
	}

	public void setNmonth(int nmonth) {
		this.nmonth = nmonth;
	}

	public int getNyear() {
		return nyear;
	}

	public void setNyear(int nyear) {
		this.nyear = nyear;
	}

	@Override
	public String execute() throws Exception 
	{		
		Calendar today=new GregorianCalendar();
		if (month==-1)
			month=today.get(Calendar.MONTH);
		if (year==-1)
			year=today.get(Calendar.YEAR);
		
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		ArrayList<DTOWorkSpaceNodeAttrDetail> evtListFromDB = docMgmtImpl.getCalendarEvents(month+1,year,userCode); 
		
		ArrayList<Event> evtList=new ArrayList<Event>();
				
		for (int i=0;i<evtListFromDB.size();)
		{
			DTOWorkSpaceNodeAttrDetail currElement=evtListFromDB.get(i);
			String currDate=currElement.getAttrValue();
			
			int j;
			for (j=i;j<evtListFromDB.size() && currDate.equals(evtListFromDB.get(j).getAttrValue());)
			{
				String currEvent=evtListFromDB.get(j).getAttrName();
				String extDate=currDate.substring(currDate.length()-2);
									
				int k;		
				String eventText="";
				for (k=j;k<evtListFromDB.size() && currEvent.equals(evtListFromDB.get(k).getAttrName()) && currDate.equals(evtListFromDB.get(k).getAttrValue());k++)
				{
					eventText+=evtListFromDB.get(k).getWorkSpaceDesc()+", ";					
				}			
				if (eventText!=null && !eventText.equals(""))
				{
					Event dateEvent=new Event(new GregorianCalendar(year,month,Integer.parseInt(extDate)));
					eventText=eventText.substring(0,eventText.length()-2);
					dateEvent.add(currEvent,eventText);
					evtList.add(dateEvent);
				}
				j=k;
			}
			i=j;
		}
				      
        calendarHTML=CalendarUtils.getMonthString(month,year,evtList,null);        
        pmonth=month;
        pyear=year;
        pmonth--;
        if (pmonth<0)
        {
            pmonth=11;
            pyear--;
        }        
        nmonth=month;
        nyear=year;
        nmonth++;
        if (nmonth>=12)
        {
            nmonth=0;
            nyear++;
        }        
        
        int cyear=new GregorianCalendar().get(Calendar.YEAR);
        if (nyear>(cyear+nextNoOfYears))
        	nyear=cyear-prevNoOfYears;
        if (pyear<(cyear-prevNoOfYears))
        	pyear=(cyear+nextNoOfYears);
        
        //System.out.println("nyear"+nyear);
        //System.out.println("pyear"+pyear);
        
        selMonth=month;
        selYear=year;
        
        evtAttrs=docMgmtImpl.getCalendarEventAttributes();       
        attrChoices=docMgmtImpl.getCalendarChoices(userCode);        
        remnChoices=docMgmtImpl.getReminderChoices(userCode);
		return SUCCESS;		
	}
	
	public String setEventAttributes()
	{		
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());		
		docMgmtImpl.setEventAttributes(userCode, attrlist);
		return SUCCESS;
	}
	
	public String setReminderAttributes()
	{		
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());		
		docMgmtImpl.setReminderAttributes(userCode, attrRemlist);
		return SUCCESS;
	}
}
