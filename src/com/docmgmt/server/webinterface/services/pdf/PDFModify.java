package com.docmgmt.server.webinterface.services.pdf;

import java.io.FileOutputStream;

import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
public class PDFModify
{
	public static void main(String[] args)
	{
		System.out.println("Usage: PDFmodify file_in file_out");
		try
		{
			// we create a reader for a certain document
		    com.itextpdf.text.pdf.PdfReader reader = new com.itextpdf.text.pdf.PdfReader("D:/test.pdf");
		    PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("D://test1.pdf"));
		    stamper.setEncryption(null, null, ~(PdfWriter.ALLOW_ASSEMBLY | 
		    									PdfWriter.ALLOW_COPY | 
		    									PdfWriter.ALLOW_PRINTING | 
		    									PdfWriter.ALLOW_SCREENREADERS |
		    									PdfWriter.ALLOW_DEGRADED_PRINTING |
		    									PdfWriter.ALLOW_FILL_IN |
		    									PdfWriter.ALLOW_MODIFY_ANNOTATIONS |
		    									PdfWriter.ALLOW_MODIFY_CONTENTS), PdfWriter.ENCRYPTION_AES_128);   

		    //stamper.setEncryption("123".getBytes(), "123".getBytes(),PdfWriter.AllowPrinting, PdfWriter.STRENGTH40BITS);
		    System.out.println("The value of Allow Printing is:..."+com.itextpdf.text.pdf.PdfWriter.ALLOW_PRINTING);
		    stamper.close();

		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		     e.printStackTrace();
		}
}

}

