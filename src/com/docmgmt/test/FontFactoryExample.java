/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

package com.docmgmt.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class FontFactoryExample {

    /** The resulting PDF file. */
    public static final String RESULT
        = "D://font_factory.pdf";

    /**
     * Creates a PDF document.
     * @param filename the path to the new PDF document
     * @throws DocumentException 
     * @throws IOException 
     * @throws    DocumentException 
     * @throws    IOException
     */
    public void createPdf(String filename) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4:
      
       
        URL url = this.getClass().getResource("calibri.ttf");
               
        FontFactory.register(url.getFile(), "my_bold_font");
 
        Font myBoldFont = FontFactory.getFont("my_bold_font",50,1);
           
        PdfPTable table;
		float tablewidth = 500f;
		table = new PdfPTable(1);
		table.setTotalWidth(tablewidth);
		PdfPCell cell = new PdfPCell(
				new Phrase("Testtt", myBoldFont));
		cell.setPadding(20f);
		cell.setLeading(1f, 1.4f);

		cell.setBorderWidthLeft(3f);
		cell.setBorderWidthTop(3.5f);
		cell.setBorderWidthBottom(4.5f);
		cell.setBorderWidthRight(5f);

		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		cell.setUseDescender(true);

		table.addCell(cell);
		table.completeRow();
		
		table.writeSelectedRows(0, -1, (PageSize.A4.getWidth() / 2)
				- (tablewidth / 2), PageSize.A4.getHeight() - 220, writer.getDirectContent());
		
		document.add(table);
        
        document.add(new Paragraph("Temp", myBoldFont));
          
     
        /*
        Font myBoldFont2 = FontFactory.getFont("Garamond vet");
        document.add(new Paragraph("Garamond Vet", myBoldFont2));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Registered fonts:"));
        FontFactory.registerDirectory("resources/fonts");
        for (String f : FontFactory.getRegisteredFonts()) {
            document.add(new Paragraph(f, FontFactory.getFont(f, "", BaseFont.EMBEDDED)));
        }
        document.add(Chunk.NEWLINE);
        Font cmr10 = FontFactory.getFont("cmr10");
        cmr10.getBaseFont().setPostscriptFontName("Computer Modern Regular");
        Font computerModern = FontFactory.getFont("Computer Modern Regular", "", BaseFont.EMBEDDED);
        document.add(new Paragraph("Computer Modern", computerModern));
        document.add(Chunk.NEWLINE);
        FontFactory.registerDirectories();
        for (String f : FontFactory.getRegisteredFamilies()) {
            document.add(new Paragraph(f));
        }
        document.add(Chunk.NEWLINE);
        Font garamond = FontFactory.getFont("garamond", BaseFont.WINANSI, BaseFont.EMBEDDED);
        document.add(new Paragraph("Garamond", garamond));
        Font garamondItalic
            = FontFactory.getFont("Garamond", BaseFont.WINANSI, BaseFont.EMBEDDED, 12, Font.ITALIC);
        document.add(new Paragraph("Garamond-Italic", garamondItalic));
        // step 5
        */
        document.close();
    }


    /**
     * Main method.
     *
     * @param    args    no arguments needed
     * @throws DocumentException 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, DocumentException {
        new FontFactoryExample().createPdf(RESULT);
    }
}
