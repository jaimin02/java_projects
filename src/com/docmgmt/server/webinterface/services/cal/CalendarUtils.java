
package com.docmgmt.server.webinterface.services.cal;


import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author nagesh
 */
public class CalendarUtils
{	
    public static Calendar dateToCalendar(Date date)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date calendarToDate(Calendar calendar)
    {
        return calendar.getTime();
    }

    public static ArrayList<ArrayList<Integer>> getMonthMatrix(int month,int year)
    {        
        Calendar inputDate=new GregorianCalendar(year,month,1);
        Calendar tempDate=(Calendar)inputDate.clone();
        ArrayList<ArrayList<Integer>> currentMonth=new ArrayList<ArrayList<Integer>>();
        do
        {
            int i;
            ArrayList<Integer> list=new ArrayList<Integer>();
            for (i=1;i<8;i++)
            {
                if (tempDate.get(Calendar.MONTH)!=inputDate.get(Calendar.MONTH))
                {
                    list.add(new Integer(-1));
                    continue;
                }
                if (tempDate.get(Calendar.DAY_OF_WEEK)==i)
                {
                	Calendar today=new GregorianCalendar();
                    if (today.get(Calendar.DATE)==tempDate.get(Calendar.DATE) && today.get(Calendar.MONTH)==tempDate.get(Calendar.MONTH) && today.get(Calendar.YEAR)==tempDate.get(Calendar.YEAR))
                    {
                        list.add(new Integer(tempDate.get(Calendar.DATE)+100));
                    }
                    else
                    {
                        list.add(new Integer(tempDate.get(Calendar.DATE)));
                    }
                    tempDate.add(Calendar.DAY_OF_MONTH,1);
                }
                else
                    list.add(new Integer(-1));
            }
            currentMonth.add(list);
        }
        while(tempDate.get(Calendar.MONTH)==inputDate.get(Calendar.MONTH));
        return currentMonth;
    }

    public static Document getMonthMatrixDocument(ArrayList<ArrayList<Integer>> currentMonth,ArrayList<Event> events,String heading,int month,int year)
    {
        Element table=null;
        Document doc=null;
        try 
        {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            table = doc.createElement("table");
            table.setAttribute("cellspacing","0");
            table.setAttribute("class","caltable");
            /*Element caption=doc.createElement("caption");
            caption.setTextContent(heading);
            table.appendChild(caption);*/
            
            Element thead = doc.createElement("thead");
            Element theadtr = doc.createElement("tr");

            Element theadth = doc.createElement("th");
            Text theadthtext = doc.createTextNode("Sun");
            theadth.appendChild(theadthtext);
            theadtr.appendChild(theadth);

            theadth = doc.createElement("th");
            theadthtext = doc.createTextNode("Mon");
            theadth.appendChild(theadthtext);
            theadtr.appendChild(theadth);

            theadth = doc.createElement("th");
            theadthtext = doc.createTextNode("Tue");
            theadth.appendChild(theadthtext);
            theadtr.appendChild(theadth);

            theadth = doc.createElement("th");
            theadthtext = doc.createTextNode("Wed");
            theadth.appendChild(theadthtext);
            theadtr.appendChild(theadth);

            theadth = doc.createElement("th");
            theadthtext = doc.createTextNode("Thu");
            theadth.appendChild(theadthtext);
            theadtr.appendChild(theadth);

            theadth = doc.createElement("th");
            theadthtext = doc.createTextNode("Fri");
            theadth.appendChild(theadthtext);
            theadtr.appendChild(theadth);

            theadth = doc.createElement("th");
            theadthtext = doc.createTextNode("Sat");
            theadth.appendChild(theadthtext);
            theadtr.appendChild(theadth);
            thead.appendChild(theadtr);
            table.appendChild(thead);

            Element tbody=doc.createElement("tbody");
            for (int i=0;i<currentMonth.size();i++)
            {
                Element tbodytr=doc.createElement("tr");
                for (int j=0;j<currentMonth.get(i).size();j++)
                {
                    Element tbodytd=doc.createElement("td");
                    if (currentMonth.get(i).get(j)!=-1)
                    {
                        int date=currentMonth.get(i).get(j);
                        if (date>=100)
                        {                        	
                            date-=100;
                            tbodytd.setAttribute("class","today");
                        }
                        Text text=doc.createTextNode(new Integer(date).toString());
                        tbodytd.appendChild(text);
                        
                        Element eventUl=null;
                        for (int k=0;events!=null && events.size()>0 && k<events.size();k++)
                        {
                            if (events.get(k).getEventDate().get(Calendar.DATE)==date && events.get(k).getEventDate().get(Calendar.MONTH)==month && events.get(k).getEventDate().get(Calendar.YEAR)==year)
                            {
                                for (int l=0;l<events.get(k).getEventTitle().size();l++)
                                {
                                    Element eventLi=doc.createElement("li");
                                    Element spanTitle=doc.createElement("span");
                                    spanTitle.setAttribute("class","title");
                                    Element spanDesc=doc.createElement("span");
                                    spanDesc.setAttribute("class","desc");
                                    spanTitle.setTextContent(events.get(k).getEventTitle().get(l));
                                    spanDesc.setTextContent(events.get(k).getEventText().get(l));
                                    eventLi.appendChild(spanTitle);
                                    eventLi.appendChild(spanDesc);
                                    if (eventUl==null)
                                        eventUl=doc.createElement("ul");
                                    eventUl.appendChild(eventLi);
                                }
                            }
                        }
                        if (eventUl!=null)
                        {
                            Element eventsDiv=doc.createElement("div");
                            eventsDiv.setAttribute("class","calevents");
                            eventsDiv.appendChild(eventUl);
                            tbodytd.appendChild(eventsDiv);
                            tbodytd.setAttribute("class","date_has_event");
                        }
                    }
                    tbodytr.appendChild(tbodytd);
                }                
                tbody.appendChild(tbodytr);
            }
            table.appendChild(tbody);
        }
        catch (ParserConfigurationException ex)
        {
            
        }
        doc.appendChild(table);
        return doc;
    }

    public static String getMonthName(int i,boolean abb)
    {
        switch(i)
        {
            case 0:
                return(!abb?"January":"Jan");
            case 1:
                return(!abb?"February":"Feb");
            case 2:
                return(!abb?"March":"Mar");
            case 3:
                return(!abb?"April":"Apr");
            case 4:
                return(!abb?"May":"May");
            case 5:
                return(!abb?"June":"Jun");
            case 6:
                return(!abb?"July":"Jul");
            case 7:
                return(!abb?"August":"Aug");
            case 8:
                return(!abb?"Septemeber":"Sep");
            case 9:
                return(!abb?"October":"Oct");
            case 10:
                return(!abb?"November":"Nov");
            case 11:
                return(!abb?"December":"Dec");
        }
        return null;
    }

    public static String getMonthString(int month,int year,ArrayList<Event> events,String heading)
    {
        String retString=null;
        try
        {
            if (heading==null)
            {
                heading=""+getMonthName(month, true) + ", " + year;
            }
            ArrayList<ArrayList<Integer>> currentMonth = CalendarUtils.getMonthMatrix(month,year);
            Document doc = CalendarUtils.getMonthMatrixDocument(currentMonth, events, heading,month,year);
            Transformer transfromer = TransformerFactory.newInstance().newTransformer();
            Properties outProperties = new Properties();
            outProperties.setProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transfromer.setOutputProperties(outProperties);
            StringWriter stringWriter = new StringWriter();
            transfromer.transform(new DOMSource(doc), new StreamResult(stringWriter));
            retString=stringWriter.toString();
        } 
        catch (TransformerException ex)
        {
            
        }
        return retString;
    }
    
    public static Calendar stringToCalendar(String date)
    {        
        return stringToCalendar(date,"yyyy/MM/dd");
    }

    public static Calendar stringToCalendar(String date,String format)
    {
        Calendar ctoday=null;
        try
        {
            SimpleDateFormat sdf=new SimpleDateFormat(format);
            Date d=sdf.parse(date);
            ctoday=Calendar.getInstance();
            ctoday.setTime(d);            
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        return ctoday;
    }
    
    public static Calendar timestampToCalendar(Timestamp date)
    {
        Calendar ctoday=null;
        try
        {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d=sdf.parse(date.toString());
            ctoday=Calendar.getInstance();
            ctoday.setTime(d);            
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        return ctoday;
    }
    
    public static int dateDifferenceInDays(Calendar calendar1,Calendar calendar2)
    {
    	if (calendar1 == null || calendar2 ==null)
    		return 0;
        if (calendar1.equals(calendar2))
            return 0;
		long milliseconds1 = calendar1.getTimeInMillis();
		long milliseconds2 = calendar2.getTimeInMillis();
		long diff = milliseconds2 - milliseconds1;
		long diffDays = diff / (24 * 60 * 60 * 1000);     
        return (int)diffDays;
    }
    
    public static void main(String args[])
    {    	
    	String s=new Timestamp(System.currentTimeMillis()).toString();
    	System.out.println("s:"+s);    	
    	Calendar c=CalendarUtils.stringToCalendar(s,"yyyy-MM-dd hh:mm:ss");
    	System.out.println("c"+(c==null));
    	System.out.println("c.toString()"+c.toString());
    }
}
