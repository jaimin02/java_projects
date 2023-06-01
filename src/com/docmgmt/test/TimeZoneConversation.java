package com.docmgmt.test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
 
public class TimeZoneConversation 
{
	//static DateTimeFormatter globalFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy 'at' hh:mm:ss a z");
    //static DateTimeFormatter etFormat = DateTimeFormatter.ofPattern("dd-MMM-yyyy 'at' hh:mm:ss a 'EST'");
    
  private static final String DATE_FORMAT = "dd-M-yyyy HH:mm";
	static ZoneId istZoneId = ZoneId.of("Asia/Kolkata");
    //static ZoneId etZoneId = ZoneId.of("EST5EDT");
    static ZoneId etZoneId = ZoneId.of("Canada/Eastern");
    
    public static void main(String[] args)   
    {       
    	String dateInString = "28-05-2020 14:48";
    	LocalDateTime currentDateTime = LocalDateTime.parse(dateInString, DateTimeFormatter.ofPattern(DATE_FORMAT));

    	ZonedDateTime currentISTime = currentDateTime.atZone(istZoneId); //India Time
    	ZonedDateTime currentETime = currentISTime.withZoneSameInstant(etZoneId); //EST Time

    	System.out.println(currentISTime.toLocalDate() + "  " + currentISTime.toLocalTime() + " IST");
    	System.out.println(currentETime.toLocalDate() + "  " + currentETime.toLocalTime() + " EST/Canada");

    	// apply getOffset() on ZonedDateTime, not on Instant
    	System.out.println("Offset is " + currentISTime.getOffset() + " " + istZoneId);
    	System.out.println("Offset is " + currentETime.getOffset() + " " + etZoneId);
        
       
    }
}