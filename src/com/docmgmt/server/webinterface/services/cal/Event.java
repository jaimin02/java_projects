package com.docmgmt.server.webinterface.services.cal;

import java.util.ArrayList;
import java.util.Calendar;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nagesh
 */
public class Event
{
    Calendar eventDate;
    ArrayList<String> eventTitle;
    ArrayList<String> eventText;

    public Calendar getEventDate() {
        return eventDate;
    }

    public void setEventDate(Calendar eventDate) {
        this.eventDate = eventDate;
    }
    
    public ArrayList<String> getEventText()
    {
        return eventText;
    }

    public void setEventText(ArrayList<String> eventText)
    {
        this.eventText = eventText;
    }

    public ArrayList<String> getEventTitle()
    {
        return eventTitle;
    }

    public void setEventTitle(ArrayList<String> eventTitle)
    {
        this.eventTitle = eventTitle;
    }
    
    public Event()
    {
        eventDate=Calendar.getInstance();
        eventTitle=new ArrayList<String>();
        eventText=new ArrayList<String>();
    }

    public Event(Calendar date,String title,String text)
    {
        this();
        add(date, title, text);
    }

    public Event(Calendar date)
    {
        this();
        eventDate=date;
    }
    
    public void add(Calendar date,String title,String text)
    {
        eventDate=date;
        eventTitle.add(title);
        eventText.add(text);
    }
    
    public void add(String title,String text)
    {        
        eventTitle.add(title);
        eventText.add(text);
    }
}
