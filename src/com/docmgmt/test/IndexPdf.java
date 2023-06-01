package com.docmgmt.test;

import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;

public class IndexPdf {

	/**
	 * EventListner for Index
	 */
	private class IndexEvent extends PdfPageEventHelper {
		private int page;
		private boolean body;

		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			// set page number on content
			if (body) {
				page++;

				System.out.println("page" + page);
				ColumnText.showTextAligned(writer.getDirectContent(),
						Element.ALIGN_CENTER, new Phrase(page + ""), (document
								.right() + document.left()) / 2, document
								.bottom() - 18, 0);
			}
		}

	}

	public void GenerateToc(ArrayList<Integer> pdfStartPageNumber) {

		try {

			Document document = new Document(PageSize.A4, 50, 50, 50, 50);
			// add index page.
			String path = "D://pdfs/IndexPdf.pdf";
			FileOutputStream os = new FileOutputStream(path);
			PdfWriter writer = PdfWriter.getInstance(document, os);
			IndexEvent indexEvent = new IndexEvent();
			writer.setPageEvent(indexEvent);

			document.open();

			Chapter indexChapter = new Chapter("Table of Content", -1);
			indexChapter.setNumberDepth(-1); // not show number style

			PdfPTable table = new PdfPTable(2);
			int i = 1;

			for (int temp = 0; temp < pdfStartPageNumber.size(); temp++) {
				PdfPCell left = new PdfPCell(new Phrase("test"));
				left.setBorder(Rectangle.NO_BORDER);
				Chunk pageno = new Chunk(""+pdfStartPageNumber.get(temp));
				pageno.setLocalGoto("Page-" + i++);
				PdfPCell right = new PdfPCell(new Phrase(pageno));
				right.setHorizontalAlignment(Element.ALIGN_RIGHT);
				right.setBorder(Rectangle.NO_BORDER);

				table.addCell(left);
				table.addCell(right);

			}

			indexChapter.add(table);
			document.add(indexChapter);
			// add content chapter

			document.close();
			os.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	public void addLink(String path) throws Exception{
		 PdfReader reader = new PdfReader(new RandomAccessFileOrArray(path), null);
          
         PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("D://pdfs/test.pdf"));
     
         PdfImportedPage page = stamper.getImportedPage(reader, 1);
         
         PdfContentByte background;
         int n = reader.getNumberOfPages();
         for (int i = 1; i <= n; i++) {
             background = stamper.getUnderContent(i);
             
             
         	Phrase director = new Phrase();
			
			if(i==1){
				director.add(new Chunk("Test").setLocalGoto("xyzabc"));
				ColumnText.showTextAligned(background,
						Element.ALIGN_CENTER, director,
						page.getWidth() / 2, page.getHeight() / 2, 0);
			}
			if(i==2){
				director.add(new Chunk("GOto").setLocalDestination("xyzabc"));
				ColumnText.showTextAligned(background,
						Element.ALIGN_CENTER, director,
						page.getWidth() / 2, page.getHeight() / 2, 0);
				
			}
             
         }
         // CLose the stamper
         stamper.close();
         reader.close();
         
        
     
    }
		
	
	/*
	 * 
	 * 
        	
            if (i == 1)
            {
                Anchor click = new Anchor("Click to go to Target");
                click.setReference("#target");
                Paragraph p1 = new Paragraph();
                p1.add(click);
                doc.add(p1);
                page = writer.getImportedPage(reader, i);
                doc.newPage();
                int aRotation = reader.getPageRotation(i);
                System.out.println("Rotation-"+aRotation);
                
                pdfContentByte.addTemplate(page, 0, 0);
            }
            else
            {
                if (i == 3)
                {
                    Anchor target = new Anchor("My targer");
                    target.setName("target");
                    Paragraph p3 = new Paragraph();
                    p3.add(target);
                    doc.add(p3);
                }
                page = writer.getImportedPage(reader, i);
                int aRotation = reader.getPageRotation(i);
                System.out.println("Rotation-"+aRotation);
                doc.newPage();
                pdfContentByte.addTemplate(page, 0, 0);
            }
	 * 
	 * */
	 
}		

