package com.docmgmt.struts.actions.WelcomePage.reminders.AlertServices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import com.docmgmt.dto.DTOReminder;
import com.docmgmt.dto.DTOUserProfile;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.webinterface.services.cal.CalendarUtils;
import com.docmgmt.struts.actions.WelcomePage.comments.ShowComments;
import com.docmgmt.struts.actions.WelcomePage.reminders.ShowReminders;

public class AlertService {
	public DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public ArrayList<DTOReminder> getReminderList(int userCode,char alertType)
	{
		ArrayList<DTOReminder> reminders =new ArrayList<DTOReminder>();
		ShowReminders reminder = new ShowReminders();
		String reminderDayKey = "";
		ArrayList<Integer> reminderDays = new ArrayList<Integer>();
		if(alertType=='E')
			reminderDayKey="Email_Before_Days";
		else if(alertType=='M')
			reminderDayKey="SMS_Before_Days";
		try 
		{
			reminderDays=getReminderDaySequence(reminderDayKey);
			// showwhat = 2 for Active reminmders
			reminder.showWhat="2";
			reminder.daysDiff=reminderDays.get(reminderDays.size()-1);
			reminder.userCode=userCode;
			reminder.execute();
			reminders = reminder.remMsg;
			reminders = filterAlertDataDayWise(reminderDays, reminders);
			/*
			for(int i=0;i<reminders.size();i++)
				System.out.println(reminders.get(i).getAttrName() +" ==== "+reminders.get(i).getAttrValue());
			*/
			reminder.remMsg = new ArrayList<DTOReminder>();
			Collections.sort(reminders,
				new  Comparator<DTOReminder>() 
				{
					public int compare(DTOReminder r1, DTOReminder r2) 
					{
						int cmpAttrName = r1.getAttrName().compareTo(r2.getAttrName());
						if(cmpAttrName == 0)
						{
							int cmpWorkspaceDesc = r1.getWorkspaceDesc().compareTo(r2.getWorkspaceDesc());
							if(cmpWorkspaceDesc == 0 )
								return  r1.getNodeDisplayName().compareTo(r2.getNodeDisplayName());
							else
								return cmpWorkspaceDesc;
						}
						else
							return cmpAttrName;
					}
				}
			);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return reminders;
	}
	
	public ArrayList<Integer> getReminderDaySequence(String reminderDayKey)
	{
		KnetProperties prop = KnetProperties.getPropInfo("Alert.properties");
		String value = prop.getValue(reminderDayKey);
		String continiousDaySymbl = ":";
		String individualDaySymbl = ",";
		String continiousDay = ".*"+continiousDaySymbl+".*";
		String individualDay = ".*"+individualDaySymbl+".*";
		ArrayList<Integer> reminderDays = new ArrayList<Integer>();
		int startValue;
		int endValue;
		int firstValue=0;
		int secondValue=0;
		
		if(value.matches(individualDay))
		{
			String[] sepValu =  value.split(individualDaySymbl);
			for(int i=0;i<sepValu.length;i++)
			{
				if(sepValu[i].matches(continiousDay))
				{
					String[] sepValu12 =  sepValu[i].split(continiousDaySymbl);
					firstValue=Integer.parseInt(sepValu12[0]);
					secondValue=Integer.parseInt(sepValu12[1]);
					if( firstValue <= secondValue)
					{
						startValue = firstValue;
						endValue = secondValue;
					}
					else
					{
						startValue = secondValue;
						endValue = firstValue;
						
					}
					
					for(int j= startValue;j<= endValue;j++)
						reminderDays.add(j);
				}
				else
					reminderDays.add(Integer.parseInt(sepValu[i]));
			}
		}
		else if (!value.matches(individualDay) && value.matches(continiousDay)) 
		{
			String[] sepValu12 =  value.split(continiousDaySymbl);
			firstValue=Integer.parseInt(sepValu12[0]);
			secondValue=Integer.parseInt(sepValu12[1]);
			if( firstValue <= secondValue)
			{
				startValue = firstValue;
				endValue = secondValue;
			}
			else
			{
				startValue = secondValue;
				endValue = firstValue;
			}
			for(int j= startValue;j<= endValue;j++)
				reminderDays.add(j);
		}
		else
		{
			reminderDays.add(Integer.parseInt(value));
		}
		Collections.sort(reminderDays);
		/*
		for(int i = 0;i<reminderDays.size();i++)
			System.out.println("reminder days======="+reminderDays.get(i));
		*/
		return reminderDays;
	}

	public ArrayList<DTOReminder> filterAlertDataDayWise(ArrayList<Integer> reminderDays,ArrayList<DTOReminder> reminders)
	{
		Date dt = new Date();
		DateFormat dataFormat = new SimpleDateFormat("yyyy/MM/dd");
		String curDate = dataFormat.format(dt);
		int itrReminderDays,itrReminderList;

		for(itrReminderDays = 0,itrReminderList=0; itrReminderDays<reminderDays.size();itrReminderDays++)
		{
			while(reminders.size() > itrReminderList)
			{
				int diff=CalendarUtils.dateDifferenceInDays(CalendarUtils.stringToCalendar(curDate),CalendarUtils.stringToCalendar(reminders.get(itrReminderList).getAttrValue()));
				if(reminderDays.get(itrReminderDays) == diff)
					itrReminderList++;
				else if(reminderDays.get(itrReminderDays) < diff)
					break;
				else if(reminderDays.get(itrReminderDays) > diff)
					reminders.remove(itrReminderList);
			}
		}
		return reminders;
	}
	
	public ArrayList<DTOUserProfile> getUserProfile(Character alertType)
	{
		ArrayList<DTOUserProfile> userData = new ArrayList<DTOUserProfile>();
		userData = docMgmtImpl.getUserForAlert(alertType);
		return userData;
	}
	
	public ArrayList<String> getProfileValue(Character alertType,int userCode)
	{
		ArrayList<String> listProfileValue = new ArrayList<String>();
		listProfileValue = docMgmtImpl.getUserDetailForAlert(alertType,userCode);
		return listProfileValue;
	}
	
	public void sendMailAlert()
	{
		ArrayList<DTOUserProfile> userdata = new ArrayList<DTOUserProfile>();
		Character alertType;
		KnetProperties prop = KnetProperties.getPropInfo("Alert.properties");
		String isSingleEmailType;
		EmailSender emailSender = new EmailSender();
		emailSender.mailAuth();

		//Alert type M=Mobile E=Email
		alertType = 'E';
		userdata = getUserProfile(alertType);
		isSingleEmailType = prop.getValue("Email_isSingle");

		if(isSingleEmailType.equalsIgnoreCase("true"))
			sendSingleMail(userdata,alertType,emailSender);
		else
			sendBulkMail(userdata,alertType,emailSender);
	}
	
	public void sendSingleMail(ArrayList<DTOUserProfile> userdata,Character alertType,EmailSender emailSender)
	{
		ArrayList<DTOReminder> arrReminders = new ArrayList<DTOReminder>();
		int userCode;
		String mailSubject = "";
		String mailBody="";
		ArrayList<String> receivers = new ArrayList<String>();
		String mailTo="";
		String tableHdr="";
		String tableFtr="";
		Date dt = new Date();
		DateFormat inDataFormat = new SimpleDateFormat("yyyy/MM/dd");
		String curDate = inDataFormat.format(dt);
		DateFormat outDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String inDate;
		String outDate;
		Date formatedInDate;
		
		HashMap<String, String> nodeStructureMap = new HashMap<String, String>();
		String nodeValue;
		String nodeKey;
		ShowComments showcomments = new ShowComments();
		DTOWorkSpaceMst workspaceDtl;
		int diff;
		
		/*
		KnowledgeNET:
			"CA-Test"
			"Project Completion:23-Nov-2010"
			
			*/
		for (int itrUserList = 0; itrUserList < userdata.size(); itrUserList++) 
		{
			try
			{
				userCode= userdata.get(itrUserList).getUserCode();
				arrReminders = getReminderList(userCode,alertType);
				for (int itrReminderList = 0; itrReminderList < arrReminders.size(); itrReminderList++) 
				{
					workspaceDtl = new DTOWorkSpaceMst();
					workspaceDtl = docMgmtImpl.getWorkSpaceDetail(arrReminders.get(itrReminderList).getWorkspaceId()); 
					mailBody = tableHdr;
					diff=CalendarUtils.dateDifferenceInDays(CalendarUtils.stringToCalendar(curDate),CalendarUtils.stringToCalendar(arrReminders.get(itrReminderList).getAttrValue()));
					//System.out.println(diff+":::::::::::diff Days");
					mailBody += "<br/>" +
							  (diff==0?"<h5 style='color:red;'>Last day for "+arrReminders.get(itrReminderList).getAttrName()+"</h5>":diff < 0? "<h5 style='color:red;'>Due date is over for "+arrReminders.get(itrReminderList).getAttrName()+"</h5>":"<h5>"+diff +"  days remaining for "+arrReminders.get(itrReminderList).getAttrName()+"</h5>");
					
					if(arrReminders.get(itrReminderList).getWorkspaceDesc() != null && arrReminders.get(itrReminderList).getWorkspaceDesc() != "")
						mailBody += "<b>Project :   </b>"+arrReminders.get(itrReminderList).getWorkspaceDesc()+"<br/>";
					
					if(arrReminders.get(itrReminderList).getAttrName() != null && arrReminders.get(itrReminderList).getAttrName() != "" &&
					   arrReminders.get(itrReminderList).getAttrValue() != null && arrReminders.get(itrReminderList).getAttrValue() != "")
					{
						mailBody += "<b>"+arrReminders.get(itrReminderList).getAttrName()+" :   </b>";
						inDate = arrReminders.get(itrReminderList).getAttrValue();
						formatedInDate = inDataFormat.parse(inDate);
						outDate = outDateFormat.format(formatedInDate);
						mailBody += outDate+"<br/>";
					}
					if(!arrReminders.get(itrReminderList).getAttrForIndiId().equalsIgnoreCase("0000"))
					{
						if(arrReminders.get(itrReminderList).getNodeDisplayName() != null && arrReminders.get(itrReminderList).getNodeDisplayName() != "")
							mailBody += "<b>Node :   </b>"+arrReminders.get(itrReminderList).getNodeDisplayName()+"<br/>";
					}
					mailBody += "<br/><b><u>Other Details:</u></b><br/>";
					if(workspaceDtl.getLocationName() != null && workspaceDtl.getLocationName() != "")
						mailBody += "<b>Region :   </b>"+workspaceDtl.getLocationName()+"<br/>";
					if(workspaceDtl.getDeptName() != null && workspaceDtl.getDeptName() != "")
						mailBody += "<b>Department :   </b>"+workspaceDtl.getDeptName()+"<br/>";
					if(!arrReminders.get(itrReminderList).getAttrForIndiId().equalsIgnoreCase("0000"))
					{
						showcomments.workspaceId = arrReminders.get(itrReminderList).getWorkspaceId();
						showcomments.nodeId = arrReminders.get(itrReminderList).getNodeId();
						nodeKey =  arrReminders.get(itrReminderList).getWorkspaceId()+"_"+arrReminders.get(itrReminderList).getNodeId();
						nodeValue = "";
						if(nodeStructureMap.containsKey(nodeKey))
							nodeValue = nodeStructureMap.get(nodeKey);
						else
						{
							showcomments.getNodeStrucure();	
							nodeValue =showcomments.htmlContent; 
							nodeStructureMap.put(nodeKey, nodeValue);
						}
						mailBody += "<b>Node Hierarchy : </b><br/>"+nodeValue+"<br/>";
					}
					mailBody += tableFtr;
					mailSubject="KnowledgeNET: "+
								arrReminders.get(itrReminderList).getAttrName()+
								" for "+
								arrReminders.get(itrReminderList).getWorkspaceDesc();
					receivers = getProfileValue(alertType, userCode);
					mailTo= receivers.toString().substring(1, receivers.toString().length()-1);
					emailSender.sendMail(userCode,mailBody,mailSubject,mailTo);
					System.out.println("===="+mailBody);
				}
				arrReminders = null;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendBulkMail(ArrayList<DTOUserProfile> userdata,Character alertType,EmailSender emailSender)
	{
		ArrayList<DTOReminder> arrReminders = new ArrayList<DTOReminder>();
		int userCode;
		String mailSubject = "";
		String mailBody="";
		boolean isData = false;
		ArrayList<String> receivers = new ArrayList<String>();
		String mailTo="";
		String tableHdr = "<table width='100%' border='1px solid' >" +
		   "<tr align='left'>" +
			   "<th>Event</th>" +
			   "<th>Event Date</th>" +
			   "<th>Project Name</th>" +
			   "<th>Node Name</th>" +
			   "<th>Template Name</th>" +
			   "<th>Region Name</th>" +
			   "<th>Department Name</th>" +
			   "<th>Project Type</th>" +
		   "</tr>";
		String tableFtr = "</table>";
		for (int itrUserList = 0; itrUserList < userdata.size(); itrUserList++) 
		{
			try
			{
				userCode= userdata.get(itrUserList).getUserCode();
				arrReminders = getReminderList(userCode,alertType);
				mailBody = tableHdr;
				for (int itrReminderList = 0; itrReminderList < arrReminders.size(); itrReminderList++) 
				{
					DTOWorkSpaceMst workspaceDtl = new DTOWorkSpaceMst();
					workspaceDtl = docMgmtImpl.getWorkSpaceDetail(arrReminders.get(itrReminderList).getWorkspaceId()); 
					
					mailBody+="<tr>";
					/*
					int nxtCounter=1;
					if(itrReminderList < (arrReminders.size() - 1) && (arrReminders.get(itrReminderList+1).getAttrName().equalsIgnoreCase(arrReminders.get(itrReminderList).getAttrName())))
					{
						//this loop is for generate the row span vlaue in bulk mail table format.
						for(int itrCheck=itrReminderList+1;itrCheck < arrReminders.size();itrCheck++)
						{
							if(arrReminders.get(itrCheck).getAttrName().equalsIgnoreCase(arrReminders.get(itrReminderList).getAttrName()))
							{
								nxtCounter++;
							}
							else
							{
								break;
							}
						}	
					}
					System.out.println("-----------------------------------nxtcon"+nxtCounter);
					 */
					if(arrReminders.get(itrReminderList).getAttrName() != null && arrReminders.get(itrReminderList).getAttrName() != "")
					{/*
						if(!isSingle)
						{
							if(itrReminderList < (arrReminders.size() - 1) && (!arrReminders.get(itrReminderList+1).getAttrName().equalsIgnoreCase(arrReminders.get(itrReminderList).getAttrName())))
							{
								mailBody += "<td rowspan="+nxtCounter+">"+arrReminders.get(itrReminderList).getAttrName()+"</td>";
								nxtCounter--;
							}
							else if(itrReminderList==0 && nxtCounter != 1)
								mailBody += "<td>"+arrReminders.get(itrReminderList).getAttrName()+"</td>";
						}
						else
						*/
							mailBody += "<td>"+arrReminders.get(itrReminderList).getAttrName()+"</td>";
					}
					else
						mailBody += "<td></td>";
					if(arrReminders.get(itrReminderList).getAttrValue() != null && arrReminders.get(itrReminderList).getAttrValue() != "")
						mailBody += "<td>"+arrReminders.get(itrReminderList).getAttrValue()+"</td>";
					else
						mailBody += "<td></td>";
					if(arrReminders.get(itrReminderList).getWorkspaceDesc() != null && arrReminders.get(itrReminderList).getWorkspaceDesc() != "")
						mailBody += "<td>"+arrReminders.get(itrReminderList).getWorkspaceDesc()+"</td>";
					else
						mailBody += "<td></td>";
					if(!arrReminders.get(itrReminderList).getAttrForIndiId().equalsIgnoreCase("0000"))
					{
						if(arrReminders.get(itrReminderList).getNodeDisplayName() != null && arrReminders.get(itrReminderList).getNodeDisplayName() != "")
							mailBody += "<td>"+arrReminders.get(itrReminderList).getNodeDisplayName()+"</td>";
					}
					else
						mailBody += "<td></td>";
					if(workspaceDtl.getTemplateDesc() != null && workspaceDtl.getTemplateDesc() != "")
						mailBody += "<td>"+workspaceDtl.getTemplateDesc()+"</td>";
					else
						mailBody += "<td></td>";
					if(workspaceDtl.getLocationName() != null && workspaceDtl.getLocationName() != "")
						mailBody += "<td>"+workspaceDtl.getLocationName()+"</td>";
					else
						mailBody += "<td></td>";
					if(workspaceDtl.getDeptName() != null && workspaceDtl.getDeptName() != "")
						mailBody += "<td>"+workspaceDtl.getDeptName()+"</td>";
					else
						mailBody += "<td></td>";

					mailBody+="</tr>";
					isData = true;
				}
				if(isData)
				{
					mailBody += tableFtr;
					mailSubject="KnowledgeNET: Project Notificaion";
					receivers = getProfileValue('E', userCode);
					mailTo= receivers.toString().substring(1, receivers.toString().length()-1);
					emailSender.sendMail(userCode,mailBody,mailSubject,mailTo);
				}
				else
					System.out.println("No bulk data to SEND");
				arrReminders = null;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void sendSMSAlert()
	{
		Character alertType = 'M';
		ArrayList<DTOUserProfile> userData = getUserProfile(alertType);
		ArrayList<DTOReminder> arrReminders = new ArrayList<DTOReminder>();
		int userCode;
		String msgBody;
		DateFormat inDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat outDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String inDate;
		String outDate;
		Date formatedInDate;
		KnetProperties prop = KnetProperties.getPropInfo("Alert.properties");
		String className = "com.docmgmt.struts.actions.WelcomePage.reminders.AlertServices.SMSSender"+prop.getValue("SMS_Class");
		ISMSSender smsSender;
		ArrayList<String> smsReceivers;
		
		try 
		{
			Class<?> smsSenderClass = Class.forName(className);
			smsSender = (ISMSSender) smsSenderClass.newInstance();
			smsSender.init();
			for(int itrUserList=0;itrUserList < userData.size();itrUserList++)
			{
				try 
				{
					userCode= userData.get(itrUserList).getUserCode();
					arrReminders = getReminderList(userCode,alertType);
					for(int itrReminderList = 0;itrReminderList < arrReminders.size();itrReminderList++)
					{
						msgBody = "KnowledgeNET:\n";
						if(arrReminders.get(itrReminderList).getWorkspaceDesc() != null && arrReminders.get(itrReminderList).getWorkspaceDesc() != "")
							msgBody += "Project: "+arrReminders.get(itrReminderList).getWorkspaceDesc();
						
						if(!arrReminders.get(itrReminderList).getAttrForIndiId().equalsIgnoreCase("0000"))
							if(arrReminders.get(itrReminderList).getNodeDisplayName() != null && arrReminders.get(itrReminderList).getNodeDisplayName() != "")
								msgBody += "("+arrReminders.get(itrReminderList).getNodeDisplayName()+")";
								
						if(arrReminders.get(itrReminderList).getAttrName() != null && arrReminders.get(itrReminderList).getAttrName() != "")
							msgBody += ",\n"+arrReminders.get(itrReminderList).getAttrName();
						if(arrReminders.get(itrReminderList).getAttrValue() != null && arrReminders.get(itrReminderList).getAttrValue() != "")
						{
							inDate = arrReminders.get(itrReminderList).getAttrValue();
							formatedInDate = inDateFormat.parse(inDate);
							outDate = outDateFormat.format(formatedInDate);
							msgBody += ": "+outDate;
						}
						
						smsReceivers = getProfileValue(alertType, userCode);
						smsSender.sendSMSAlerts(msgBody, smsReceivers);
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		} 
		catch (ClassNotFoundException e1) 
		{
			e1.printStackTrace();
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void Alert()
	{
		KnetProperties prop = KnetProperties.getPropInfo("Alert.properties");
		String isEmailAlert = prop.getValue("Email_Service");
		String isSMSAlert = prop.getValue("SMS_Service");

		if(isEmailAlert.equalsIgnoreCase("true"))
			sendMailAlert();
		if(isSMSAlert.equalsIgnoreCase("true"))
			sendSMSAlert();
	}

	public static void main(String[] args) 
	{
		AlertService alertService = new AlertService();
		alertService.Alert();
	}
}