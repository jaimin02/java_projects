package com.docmgmt.test;

import com.docmgmt.server.webinterface.services.pdf.PdfPropUtilities;

public class PDFAutocorrect {
	
	public static void main(String[] args){
		
		PdfPropUtilities pdfUtil = new PdfPropUtilities();
		pdfUtil.autoCorrectPdfProp("D://test//reference-5.pdf");
		
		
	}

}
