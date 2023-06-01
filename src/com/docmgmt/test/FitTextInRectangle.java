package com.docmgmt.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class FitTextInRectangle {

	public static final String DEST = "D://chunk_in_rectangle.pdf";

	public static void main(String[] args) throws IOException,
			DocumentException {
		File file = new File(DEST);
		file.getParentFile().mkdirs();
		new FitTextInRectangle().createPdf(DEST);
	}

	public void createPdf(String dest) throws IOException, DocumentException {
		Document document = new Document();
		document.setPageSize(new Rectangle(700, 700));
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(dest));
		document.open();
		Font boldfont = FontFactory.getFont("Times New Roman", 35, Font.BOLD,
				BaseColor.BLACK);
		PdfPTable table;

		float tablewidth=500f;
		PdfContentByte canvas = writer.getDirectContent();
		
		  table = new PdfPTable(7);
          table.setTotalWidth(tablewidth);
          
          PdfPCell cell = new PdfPCell(new Phrase("SUBJEC3333333333DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD3\naa",boldfont));
          cell.setColspan(7);
          cell.setPadding(20f);
          cell.setBorderWidthBottom(2f);
          cell.setUseVariableBorders(true);
          
          cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
          cell.setHorizontalAlignment(Element.ALIGN_CENTER);
          
          cell.setBackgroundColor(BaseColor.WHITE);
          cell.setUseDescender(true);
       
       table.addCell(cell);
       table.completeRow();
       // write the table to an absolute position
       float height=table.getRowHeight(0);
       System.out.println(height);
       table.writeSelectedRows(0, -1, (document.getPageSize().getWidth()/2)-(tablewidth/2),(document.getPageSize().getHeight()/2)+(height/2), canvas);
          
       
          

		document.close();
	}
}