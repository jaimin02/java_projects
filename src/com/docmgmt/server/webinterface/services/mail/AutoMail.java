package com.docmgmt.server.webinterface.services.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.master.WorkSpaceNodeHistory;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.struts.actions.reports.StageWiseMailReport;

public class AutoMail extends HttpServlet {

	private PropertyInfo propertyInfo = PropertyInfo.getPropInfo();

	public void init() throws ServletException {

		System.out.println("----------");
		System.out
				.println("---------- Servlet Initialized successfully ----------");
		System.out.println("----------");

		boolean allowAutoMail = propertyInfo.getValue("AutoMail")
				.equalsIgnoreCase("yes") ? true : false;

		System.out.println("AutoMail In SSPLTest :" + allowAutoMail);

		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		System.out.println("Current Time: " + hour + ":" + minute + ":"
				+ second);

		// Calendar1
		Calendar currentCal = Calendar.getInstance();
		currentCal.set(year, month, day, (int) hour, minute, second);

		// System.out.print(cal1.get(Calendar.MONTH));

		// Calendar2
		Calendar setCal = Calendar.getInstance();
		setCal.set(year, month, day, 00, 01, 00);

		// Time Difference Calculations Begin
		long currentCalMilliSec = currentCal.getTimeInMillis();
		long setCalMilliSec = setCal.getTimeInMillis();

		long timeDifInMilliSec;

		timeDifInMilliSec = setCalMilliSec - currentCalMilliSec;

		long timeDifSeconds = timeDifInMilliSec / 1000;
		long timeDifMinutes = timeDifInMilliSec / (60 * 1000);
		float timeDifHours = (float) timeDifInMilliSec / (60 * 60 * 1000);
		long timeDifDays = timeDifInMilliSec / (24 * 60 * 60 * 1000);

		System.out.println("Time differences expressed in various units are given below");
		System.out.println(timeDifInMilliSec + " Milliseconds");
		System.out.println(timeDifSeconds + " Seconds");
		System.out.println(timeDifMinutes + " Minutes");
		System.out.println(timeDifHours + " Hours");
		System.out.println(timeDifDays + " Days");
		
		if (allowAutoMail) {
			if (timeDifInMilliSec >= 0) {
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new projectTimeLineMail(),
						timeDifInMilliSec, 24 * 60 * 60 * 1000);
			}

			if (timeDifInMilliSec < 0) {
				long startTime = (24 * 60 * 60 * 1000) + (timeDifInMilliSec);
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new projectTimeLineMail(),
						startTime, 24 * 60 * 60 * 1000);
			}		    	 
		}
		/* KnetProperties knetProperties = KnetProperties.getPropInfo();
	     int PQSDocAutoSyncTime = Integer.parseInt(knetProperties.getValue("PQSDocAutoSyncTime"));
	     Timer timer = new Timer();
	     timer.schedule(new PQSDocAutoSync(), 0, 1000 * 60 * PQSDocAutoSyncTime);
		*/
	/*	String PQSDocAutoSyncConfig = propertyInfo.getValue("PQSDocAutoSyncConfig"); 
	     if(PQSDocAutoSyncConfig.equalsIgnoreCase("Yes")){
	    	 Long PQSDocAutoSyncTime = Long.parseLong(propertyInfo.getValue("PQSDocAutoSyncTime"));
	    	 System.out.println("I will repeat every " + PQSDocAutoSyncTime +" Minuts.");
	    	 
	    	 Timer timer = new Timer();
		     timer.schedule(new PQSDocAutoSync(), 0, 1000 * 60 * PQSDocAutoSyncTime);*/
	    	 
	 		/*long seconds =Long.parseLong(propertyInfo.getValue("PQSDocAutoSyncTimer"));  
			timer = new Timer();
			timer.scheduleAtFixedRate(new PQSDocAutoSync(),0, seconds);*/
	     //}
		

		
	}
}
class PQSDocAutoSync extends TimerTask {

	public void run() {
        PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		 String DocStackAPIUrl = propertyInfo.getValue("DocStackAPIUrl");
		 WorkSpaceNodeHistory nodeHistory = new WorkSpaceNodeHistory();
    	  String PQSDocAutoSyncFolderPath="";
      try {
  		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
  		LocalDateTime now = LocalDateTime.now();  
  		System.out.println(dtf.format(now));
	    	Vector<DTOWorkSpaceNodeHistory> wsNodeHistory=nodeHistory.getNodeHistoryForPQSDocAutoSync();
	    	System.out.println("Before PQSDocAutoSync ");
	    	System.out.println("wsNodeHistory size ------------"+wsNodeHistory.size());
	    	
	    	if(wsNodeHistory.size()>0){
	    		
	    		System.out.println("****************Inside IfPQSDocAutoSync ");
	    		
	    		PQSDocAutoSyncFolderPath=propertyInfo.getValue("PQSDocAutoSyncFolder");
		    		for(int i=0;i<wsNodeHistory.size();i++){
		    			
	    			String s=wsNodeHistory.get(i).getFilePath();
	    			 String fl=s.substring(s.lastIndexOf("/"));
	    			 System.out.println("Docstack file :***"+fl);
	    				//File tranFolder = new File(baseWorkFolder + "/" + year + "/"+ month+"/"+ date+"/" + wsId + "/"
	    					//	+ nodeId + "/" + tranNo);
	    			
		    		File f=new File(PQSDocAutoSyncFolderPath+"/"+fl);
		    		//File f=new File("D:/KnowledgeNET-CSV/PQSDocAutoSync/DocStack_Test/TC.docx");
		    		System.out.println("DocStack File Path"+f);
		    		if(f.exists()){

		    			String str=wsNodeHistory.get(i).getFilePath();
		    		    //int index=str.lastIndexOf('/');
		    		    String filePath=str.substring(str.lastIndexOf("/"));
		    		    
		    		    URL url = new URL(DocStackAPIUrl+"repeatNode/getRepeatNodeDtl?WsId="+
		    		    		wsNodeHistory.get(i).getWorkSpaceId()+"&NodeId="+wsNodeHistory.get(i).getNodeId()+"&userId="+wsNodeHistory.get(i).getModifyBy()+
		    		    		"&status="+wsNodeHistory.get(i).getFileStatus()+"&ext="+wsNodeHistory.get(i).getFileExt()+"&uploadDoc="+PQSDocAutoSyncFolderPath+"/"+filePath);
		    		    		
		    					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    					conn.setRequestMethod("POST");
		    					conn.setRequestProperty("Accept", "application/json");
		    	
		    					if (conn.getResponseCode() != 200) {
		    						System.out.println("Failed : HTTP error code : "
		    								+ conn.getResponseCode());
		    					}
		    					BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		    	
		    					String output;
		    					System.out.println("Output from Server ....");
		    					while ((output = br.readLine()) != null) {
		    						System.out.println(output);
		    					}
		    					conn.disconnect();
		    					
		    					nodeHistory.updatePQSDocAutoSyncMst(wsNodeHistory.get(i));
		    			
		    			
		    		}
		    		else{
		    			System.out.println("File not found... API not called...");
		    			continue;
	    			}
		    	}
	    	
		    	}
	    	
	    	else{
	    		System.out.println("****************ELSE Size 0 ");
	    		System.out.println("No Data Found.");
	    	}
	    	
			  } catch (MalformedURLException e) {

				  System.out.println("Something is wrong in URL");

			 } catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.toString());
			}	      
	  }
		
}
class projectTimeLineMail extends TimerTask {

	public void run() {
      
	      KnetProperties knetProperties = KnetProperties.getPropInfo();
	      String ShedulerMail = knetProperties.getValue("ShedulerMail");
	      
	      if(ShedulerMail.equalsIgnoreCase("Yes")){
	    	   		StageWiseMailReport timeLineMail = new StageWiseMailReport();
	    	   		//timeLineMail.ProjectTimeLineMail();
	    	   		timeLineMail.ProjectTimeLineMailNewFormate();
	      }
	      System.out.println(new Date() + " -> I will repeat every 24 Hours & this is time line scheduler....");
	  }
		
}
class checkingForDateTypeAttributes extends TimerTask {

	public Vector<DTOWorkSpaceNodeAttrDetail> userDetail;
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();

	public void run() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		String year = Integer.toString(cal.get(Calendar.YEAR));
		String month = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));

		System.out.println("Curren Date: Year:" + year + "month:" + month
				+ "day:" + day);

		cal.add(Calendar.MONTH, 3);

		System.out.println("date after 3 months : " + cal.get(Calendar.YEAR)
				+ "/" + (cal.get(Calendar.MONTH) + 1) + "/"
				+ cal.get(Calendar.DATE));

		month = Integer.toString(cal.get(Calendar.MONTH) + 1);

		if (month.length() == 1) {
			month = "0" + month;
			// System.out.println(month1);
		}

		if (day.length() == 1) {
			day = "0" + day;
			// System.out.println(month1);
		}

		String dateToCompare = year + "/" + month + "/" + day;
		System.out.println(dateToCompare);

		userDetail = docMgmtImpl
				.getWorkSpaceUserDetailByAttribute(dateToCompare);

		MailService mail = new MailService();

		if (userDetail != null) {
			for (DTOWorkSpaceNodeAttrDetail obj : userDetail) {

				String emailId = obj.getProfileValue();
				if (emailId == null || emailId.equals("")) {
					continue;

				}

				String workspace = obj.getWorkSpaceDesc();
				String Username = obj.getUserName();
				String attrName = obj.getAttrName();
				String attrValue = obj.getAttrValue();
				System.out.println(" ");
				System.out.println("Workspace:-" + workspace
						+ ",ProfileValue:-" + emailId + ",UserName:-"
						+ Username + ",attrName:-" + attrName + ",attrValue:-"
						+ attrValue);
				System.out.println(" ");

				String html = "<html>"
						+ "<head></head>"
						+ "<h4>Dear "
						+ Username
						+ ",</h4>"
						+ "<div  style='height:1px; width:400px;background:black'><span style='background-color:white;color:white'>-</span></div>"
						+ "<table cellpadding='10'> "
						+

						"<tr>"
						+ "<td><b>Project</b></td>"
						+ "<td>: "
						+ workspace
						+ "</td>"
						+ "</tr>"
						+ "<tr><td><b>"
						+ attrName
						+ "</b></td><td>: "
						+ attrValue
						+ "</td></tr></table>"
						+ "</br>"
						+ "<span><b>Regards,</b></span></br><span><b>KnowledgeNET</b></span>"
						+ "</br>"
						+ "<a href='http://www.knowledgenet.in/'>www.knowledgenet.in</a>"
						+ "</html>";

				System.out.println(html);

				try {
					mail.sendEmail(emailId, null, null, "Reminder : "
							+ workspace, html, null, true);
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		System.out.println(new Date() + " ->"
				+ " I will repeat every 24 Hours.");

	}

}
