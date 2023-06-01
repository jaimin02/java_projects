package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.docmgmt.server.prop.KnetProperties;

public class TimeZoneMst {

	    DataTable datatable;
	    
	    
	
	 	private static final String DATE_FORMAT = "dd-MMM-yyyy HH:mm";
		//static ZoneId istZoneId = ZoneId.of("Asia/Kolkata");
	    //static ZoneId etZoneId = ZoneId.of("EST5EDT");
	    //static ZoneId estZoneId = ZoneId.of("Canada/Eastern");
	   
	   
  public String getTimeZoneDetail( String locationName, String countyrCode)
  {
	  		//DTOTimeZoneMst dto = new DTOTimeZoneMst();
	    	Connection con = null;
	    	ResultSet rs = null;
	    	datatable=new DataTable();
	    	
	    	String currentZone = "";
	    	
	    	/*String locationName = ActionContext.getContext().getSession().get("locationname").toString();*/
	
	    	try 
	    	{		  
	    		String whr="vlocationName='"+locationName+"'"+ "And vcountrycode='"+countyrCode+"'" ;
	    		con = ConnectionManager.ds.getConnection();
	    		rs=datatable.getDataSet(con,"*","Timezonemst" ,whr,"");
	    		if(rs.next())
	    		{
	    			/*dto.setTimeZoneMstCode(rs.getString("vTimeZoneMstCode"));
	    			dto.setTimeZoneName(rs.getString("vTimeZoneName"));
	    			dto.setTimeZoneCode(rs.getString("vTimeZoneCode"));
	    			dto.setRemark(rs.getString("vRemark"));
	    			dto.setModifyBy(rs.getInt("iModifyBy"));
	    			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
	    			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));*/	
	    			
	    			currentZone = rs.getString("vTimeZoneName");
	    		}
	    		rs.close();
	    		con.close();
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    	}
	    	return currentZone;
	 }
	    
	    
  public ArrayList<String> TimeZoneConversion(Timestamp Time, String locationName,String countryCode)
  {  
		/*String LocationName =  ActionContext.getContext().getSession().get("locationname")
				.toString();*/
		ArrayList<String> timearray  = new ArrayList<>();

		String currentlocation = getTimeZoneDetail(locationName,countryCode);
		
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		String currentZone = knetProperties.getValue("TimeStampZoneId");
		
		ZoneId istZoneId = ZoneId.of(currentZone);
		
		ZoneId estZoneId = ZoneId.of(currentlocation);
		
		SimpleDateFormat timeFormate = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
  	
      Timestamp timestamp = Time;
      
      String insertDate = timeFormate.format(timestamp);
               	
  	LocalDateTime currentDateTime = LocalDateTime.parse(insertDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
  	
  	ZonedDateTime currentISTTime = currentDateTime.atZone(istZoneId); //India Time
  	ZonedDateTime currentESTTime = currentISTTime.withZoneSameInstant(estZoneId); //EST Time
	
  	Timestamp timestampIST = Timestamp.valueOf(currentISTTime.toLocalDateTime());
  	Timestamp timestampEST = Timestamp.valueOf(currentESTTime.toLocalDateTime());
  	    		
  	String ISTDate = timeFormate.format(timestampIST);
  	String ESTDate = timeFormate.format(timestampEST);
     	
  	  String finalDateIST =ISTDate+" IST ("+currentISTTime.getOffset().toString()+" GMT)";
      String finalDateEST =ESTDate+" EST ("+currentESTTime.getOffset().toString()+" GMT)";
  	
      //System.out.println("Input Time:"+insertDate);
  	
  	//System.out.println(currentISTTime.toLocalDate() + "  " + currentISTTime.toLocalTime() + " IST");
  	//System.out.println(currentESTTime.toLocalDate() + "  " + currentESTTime.toLocalTime() + " EST/Canada");
   	
  	//System.out.println(finalDateIST);
  	//System.out.println(finalDateEST);
  	
  	// apply getOffset() on ZonedDateTime, not on Instant
  	//System.out.println("Offset is " + currentESTTime.getOffset() + " " + estZoneId);
		
  	//return finalDateIST;
  	
  	timearray.add(finalDateIST);
  	timearray.add(finalDateEST);
   	
  	return timearray;
  }
	
}
