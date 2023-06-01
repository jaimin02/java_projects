package com.docmgmt.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimeStampExample {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

    public static void main(String[] args) {

        //method 1
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);

        //method 2 - via Date
       // Date date = new Date();
        //System.out.println(new Timestamp(date.getTime()));

        //return number of milliseconds since January 1, 1970, 00:00:00 GMT
        //System.out.println(timestamp.getTime());

        //format timestamp
        String str=sdf.format(timestamp);
        System.out.println(str);
        
    }

}