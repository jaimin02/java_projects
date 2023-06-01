package com.docmgmt.test;

import com.zeonpad.pdfcovertor.GenericConvertor;

public class GenericToPDF {
	
	public static void main(String[] args) throws Exception
    {
		
		/*// Create an Object
		WordToPdf wordToPdf = new WordToPdf();
		// Covert to Pdf
		wordToPdf.convert("D:\\Test\\Files\\sample.docx","D:\\Test\\Files\\sample.pdf");*/
		
		/*// Create an Object
		ExcelToPdf excelToPdf = new ExcelToPdf();
		// Covert to Pdf
		excelToPdf.convert("D:\\Test\\Files\\sample.xlsx","D:\\Test\\Files\\sample.pdf");*/
		
		
	// Create an Object
		//PptToPdf pptToPdf = new PptToPdf();
		// Covert to Pdf
		//pptToPdf.convert("D:\\Test\\Files\\KnowledgeNETeCTD2011.pptx","D:\\Test\\Files\\KnowledgeNETeCTD2011.pdf");
		
		// Create an Object
		GenericConvertor genericConv =new GenericConvertor();
		// Covert to Pdf
		genericConv.convert("\\90.0.0.15\\KnowledgeNET-CSV\\workspace\\0030\\7\3\\Gxp-Assessment-Initial-Impact-Assessment.docx","\\90.0.0.15\\KnowledgeNET-CSV\\DocToPdf\\0030\\7\\13\\Gxp-Assessment-Initial-Impact-Assessment.pdf");
		
    }


}
