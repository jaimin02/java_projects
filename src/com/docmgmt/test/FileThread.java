package com.docmgmt.test;

import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;


public class FileThread{
	
	public static void main(String[] a) {
		
		
		int a1='1';
		int b1=1;
		System.out.println(a1);
		
	    Thread first = new Thread(new Runnable() {
	        public void run() {

	        	//Task One
	        	String source = "//90.0.0.15/docmgmtandpub/resu1.pdf";
	    		String Dest = "//90.0.0.15/docmgmtandpub/resu111.pdf";
	    		new LinearizedPdf().linearize(source, Dest);
	        }
	    });

	    Thread second = new MyThread(first);
	   // first.start();
	   // second.start();

	    //continue executing
	}
	
	boolean isLinearized( PdfReader reader ) { 
		final PdfName LINEARIZED = new PdfName("Linearized"); 
		
		  for( int i = 0; i < reader .getXrefSize(); ++i) { 
		     PdfObject testObj = reader .getPdfObject( i ); 
		//getPdfObjectRelease()? 
		     if (testObj.type() == PdfObject.DICTIONARY) { 
		       if (((PdfDictionary)testObj).contains(LINEARIZED)) { 
		         return true; 
		       } 
		     } 
		  
		  }
		  return false;
	}
	
	public static class MyThread extends Thread {

	    private Thread predecessor;

	    public MyThread(Thread predecessor) {
	        this.predecessor = predecessor;
	    }
	    public void run() {
	        if (predecessor != null && predecessor.isAlive()) {
	            try {
	                predecessor.join();
	            } catch (InterruptedException e) {}
	        }
	      //Second Thread    
	    }
	}
}
