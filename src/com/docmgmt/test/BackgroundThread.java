package com.docmgmt.test;

import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.docmgmt.server.db.DocMgmtImpl;

public class BackgroundThread implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new SomeTask(), 0, 100000, TimeUnit.SECONDS);
        
       // scheduler.sc
    }

    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

}
 class SomeTask implements Runnable {
	 public Vector locationDetail;
	 private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
    public void run() {
        // Do your job here.
    	
    	System.out.println("test0");
    	//locationDetail=docMgmtImpl.getLocationDtl();
    }

}