package com.docmgmt.test;

import com.zeonpad.pdfcovertor.OutlookToPdf;

public class MSGToPDF {
	
	public static void main(String[] args) throws Exception
    {
		OutlookToPdf outlookToPdf = new OutlookToPdf();
		// Set the ConversionTimeOut Properties
		outlookToPdf.setConversionTimeOut(1);
		// Covert to Pdf
		outlookToPdf.convert("D:\\Test\\Module.msg", "D:\\Test\\out.pdf");
    }

}
