package com.docmgmt.test;

import java.io.FileOutputStream;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPTableEvent;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
 
public class TOCDemo extends PdfPageEventHelper implements PdfPTableEvent
{
       Document document;
       PdfWriter writer;
 
       public TOCDemo()
       {
              try
              {
                     document = new Document(PageSize.A4, 15.0f, 15.0f, 8.0f, 25.0f);
 
                     writer = PdfWriter.getInstance(document,
                                  new FileOutputStream("D:/pdfs/test.pdf"));
                     writer.setPageEvent(this);
              }
              catch (Exception ex)
              {
                     System.err.println("Error in PdfExample()");
                     ex.printStackTrace();
              }
       }
 
       public void generatePdf() throws Exception
       {
              System.out.println("Begin writing document");
 
              document.open();
              //createHeader(document);
              document.close();
 
              System.out.println("End of document");
       }
 
       public static void main(String[] args) throws Exception
       {
              TOCDemo pdfEx = new TOCDemo();
              pdfEx.generatePdf();
       }
 
       @Override
	public void onStartPage(PdfWriter writer, Document document)
       {
              super.onStartPage(writer, document);
             
              System.out.println("onStartPage");
              try
              {                   
                     createHeader(document);
              }
              catch (Exception exp)
              {
                     exp.printStackTrace();
                     System.exit(0);
              }
              System.out.println("Outside onStartPage()");
             
       }
 
       @Override
	public void onEndPage(PdfWriter writer, Document document)
       {
              super.onEndPage(writer, document);
              System.out.println("onEndPage");
       }
 
       void createHeader(Document document)
       {
              System.out.println("--- Inside createHeader()");
              try
              {
                     String msg1 = "Message with hyperlink - ";
                     String disptext = "www.mannaicorp.com";
                     String url = "http://www.mannaiinfotech.com/";
                     String capmesg = "Mannai Corporation";
                     String msg2 = " Visit us at ";
 
                     Phrase phrase = null;
                     Chunk chunk = null;
                    
                     // normal message
                     phrase = new Phrase(msg1);
 
                     //the below if condition is for adding CAP  message
 
                     if (capmesg != null && capmesg.length() > 0)
                     {
                           chunk = new Chunk(capmesg);
                           phrase.add(chunk);
                     }
 
                     phrase.add(new Chunk(msg2));
 
                     chunk = new Chunk(disptext);
                     chunk.setAnchor(url);
                     phrase.add(chunk);
                     //com.lowagie.text.pdf.PdfDocument
                     /* Add the message onto the document using a Table structure */
                     Rectangle brdMktMsg = new Rectangle(0f, 0f);
                     brdMktMsg.setBorderWidthTop(0f);
                     brdMktMsg.setBorderWidthRight(0f);
                     brdMktMsg.setBorderWidthBottom(0f);
                     brdMktMsg.setBorderWidthLeft(0f);
 
                     /*
                     Table tbl = new Table(1, 1);
                     tbl.setWidth(500.0f);
                     tbl.setSpacing(2.0f);
                    
                     Cell cell = new Cell(phrase);
                     cell.cloneNonPositionParameters(brdMktMsg);
                     cell.setBackgroundColor(new Color(213, 213, 255));
                    
                     tbl.addCell(cell);
                    
                     document.add(tbl);
                     */
                     PdfPTable tblMktMsg = new PdfPTable(1);
                     tblMktMsg.setWidthPercentage(100);
                     tblMktMsg.setSpacingBefore(10f);
                     tblMktMsg.setSpacingAfter(10f);
 
                     PdfPCell cellMktMsg = new PdfPCell(phrase);
                     cellMktMsg.cloneNonPositionParameters(brdMktMsg);

                    //tblMktMsg.addCell(cellMktMsg);
                     tblMktMsg.addCell(phrase);
                    
                     tblMktMsg.setKeepTogether(true);
                     //Paragraph p = new Paragraph(phrase);
                     //document.add(new Paragraph(p));
                     document.add(tblMktMsg);
                    
              }
              catch (Exception ex)
              {
                     System.err.println("Error in createHeader()");
                     ex.printStackTrace();
              }
              System.out.println("--- Outside createHeader()");
       }
 
       public void tableLayout(PdfPTable table, float[][] width, float[] heights,
                     int headerRows, int rowStart, PdfContentByte[] canvases)
       {
              System.out.println("--- Inside tableLayout()");
              System.out.println("------ Canvas size: " + canvases.length);
 
              // widths of the different cells of the first row
              float widths[] = width[0];
 
              PdfContentByte cb = canvases[PdfPTable.TEXTCANVAS];
              cb.saveState();
              // border for the complete table
              cb.setLineWidth(1);
              cb.setRGBColorStroke(0, 0, 0);
              cb.rectangle(widths[0], heights[heights.length - 1],
                           widths[widths.length - 1] - widths[0], heights[0]
                                         - heights[heights.length - 1]);
              cb.stroke();
              //System.out.println(" Cols :"+ widths.length);
 
              cb = canvases[PdfPTable.BASECANVAS];
              cb.saveState();
              // border for the cells
              cb.setLineWidth(.5f);
              // loop over the rows
              for (int line = 0; line < heights.length - 1; ++line)
              {
                     // loop over the columns
                     for (int col = 0; col < widths.length; ++col)
                     {
                           /*
                            // horizontal borderline
                            cb.moveTo(widths[col], heights[line]);
                            cb.lineTo(widths[col + 1], heights[line]);
                            cb.stroke(); */
                           // vertical borderline
                           if (col <= 2 || col >= 5)
                           {
                                  //System.out.println("Inside if Col :"+col +" width :"+ widths[col]);
                                  if (col == 6)
                                  {
                                         // if(widths[col]==580.0){
                                         widths[col] = 530.0f;
                                         //     }
                                  }
                                  cb.setRGBColorStrokeF(0, 0, 0);
                                  cb.moveTo(widths[col], heights[line]);
                                  cb.lineTo(widths[col], heights[line + 1]);
                                  cb.stroke();
                           }
                           else
                           {
                                  //System.out.println("ELSE Col :"+col +" width :"+ widths[col]);
                           }
                     }
              }
              cb.restoreState();
       }
}


