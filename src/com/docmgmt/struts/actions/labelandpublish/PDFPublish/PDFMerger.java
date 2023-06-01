package com.docmgmt.struts.actions.labelandpublish.PDFPublish;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.docmgmt.test.IndexPdf;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

public class PDFMerger {
	public static int BOTTOM_CENTER = 1;
	public static int BOTTOM_LEFT = 2;
	public static int BOTTOM_RIGHT = 3;
	public static int TOP_CENTER = 4;
	public static int TOP_LEFT = 5;
	public static int TOP_RIGHT = 6;

	public static int STYLE_1ofN = 1;
	public static int STYLE_1_N = 2;
	public static int STYLE_123 = 3;
	
	
	public static int concatPDFs(String destinationFilePath,List<InputStream> streamOfPDFFiles,
			OutputStream outputStream, boolean paginate, int[] nodes,
			String wsId, String isTitle, int numPos, int numStyle,String addTOC) {
		IndexPdf index;
		index=new IndexPdf();
		
		int pdfCounter = 0;
		Document document = new Document();
		int indexpages=0;

		try {

			Document PDFJoinInJava = new Document();
			PdfCopy PDFCombiner = new PdfCopy(PDFJoinInJava, outputStream);
			
			PdfCopy.PageStamp stamp;
			
			
			PDFJoinInJava.open();
			PdfReader ReadInputPDF;

			List<InputStream> pdfs = streamOfPDFFiles;
			List<PdfReader> readers = new ArrayList<PdfReader>();
			int totalPages = 0;
			Iterator<InputStream> iteratorPDFs = pdfs.iterator();
			ArrayList<Integer> pdfStartPageNumber=new ArrayList<Integer>();
			
			for (; iteratorPDFs.hasNext(); pdfCounter++) {
				InputStream pdf = iteratorPDFs.next();
				PdfReader pdfReader = new PdfReader(pdf);
				readers.add(pdfReader);
				if(isTitle.equals("yes")){
					if((pdfCounter+1)%2==0)
						pdfStartPageNumber.add(totalPages);
				}
				else
				{
					pdfStartPageNumber.add(totalPages+1);
				}
					
				totalPages += pdfReader.getNumberOfPages();
				pdf.close();
			}
			try {
				if(addTOC.equals("yes")){
					index.GenerateToc(pdfStartPageNumber);
					streamOfPDFFiles.add(0,new FileInputStream("D://pdfs/IndexPdf.pdf"));
					
					
					InputStream IndexPage=new FileInputStream("D://pdfs/IndexPdf.pdf");
					PdfReader pdfReader = new PdfReader(IndexPage);
					readers.add(0,pdfReader);
				
					indexpages=pdfReader.getNumberOfPages();
					totalPages += pdfReader.getNumberOfPages();
					pdfCounter++;
					System.out.println("Total-"+totalPages);
					IndexPage.close();
				
				}
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			int number_of_pages;
			int currentPageNumber = 0;
			int pageOfCurrentReaderPDF = 0;
			
			
			Iterator<PdfReader> iteratorPDFReader = readers.iterator();

			
			PdfImportedPage page;
			// Loop through the PDF files and add to the output.
			int count = 1;
			int nodedetailid = 0;
			final Font BOLD_UNDERLINED = new Font(FontFamily.TIMES_ROMAN, 15,
					Font.BOLD | Font.UNDERLINE);

			String format = "%d of %d";

			if (numStyle == STYLE_1_N) {
				format = "%d - %d";
			}

			
			while (iteratorPDFReader.hasNext()) {
				PdfReader pdfReader = iteratorPDFReader.next();
				pdfReader.consolidateNamedDestinations();
				if(count==1 && addTOC.equals("yes"))
					count=2;
				count++;
				number_of_pages = pdfReader.getNumberOfPages();
				System.out.println("COunts-"+count);
				// Create a new page in the target for each source page.
				for (int pageNum = 0; pageNum < number_of_pages;) {
					
					
					System.out.println("Number of pages-"+number_of_pages);
					currentPageNumber++;
					pageOfCurrentReaderPDF++;
					page = PDFCombiner.getImportedPage(pdfReader, ++pageNum);
					
					stamp = PDFCombiner.createPageStamp(page);
					System.out.println("Join");
					if (count % 2 == 0 && isTitle.equals("yes")) {
						System.out.println("NodeTitle");
//						NodeDetails nd = new NodeDetails();
//						Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
//						wsNodeDetail = nd.getNodeDetailsById(wsId,
//								nodes[nodedetailid++]);

						Phrase director = new Phrase();
//						director.add(new Chunk(wsNodeDetail.get(0)
//								.getNodeDisplayName().toString(),
//								BOLD_UNDERLINED));
						
						director.add(new Chunk("NodeTitle",
								BOLD_UNDERLINED));

						ColumnText.showTextAligned(stamp.getUnderContent(),
								Element.ALIGN_CENTER, director,
								page.getWidth() / 2, page.getHeight() / 2, 0);

						stamp.alterContents();
						// PdfContentByte cb=stamp.getUnderContent();
						// cb.setLineWidth(1f);
						// cb.moveTo(page.getWidth()/2, page.getHeight()/2);
						// cb.lineTo(200,0);
						// cb.stroke();
						//						
						// stamp.alterContents();
						//						
					}
					if (paginate) {

						if(addTOC.equals("yes") && count==3){
							PDFCombiner.addPage(page);
							currentPageNumber--;
							continue;
							
						}
						float x;
						float y;
						if (numPos == BOTTOM_CENTER) {
							x = page.getWidth() / 2;
							y = 15;
						} else if (numPos == BOTTOM_LEFT) {
							x = 30;
							y = 15;
						} else if (numPos == BOTTOM_RIGHT) {
							x = page.getWidth() - 35;
							y = 15;
						} else if (numPos == TOP_CENTER) {
							x = page.getWidth() / 2;
							y = page.getHeight() - 20;
						} else if (numPos == TOP_LEFT) {
							x = 30;
							y = page.getHeight() - 20;
						} else if (numPos == TOP_RIGHT) {
							x = page.getWidth() - 35;
							y = page.getHeight() - 20;
						} else {
							x = page.getWidth() / 2;
							y = 20;
						}
						if (numStyle != STYLE_123){
						
							
							Phrase ph=new Phrase();
							Chunk test=new Chunk(currentPageNumber+" of "+(totalPages-indexpages));
							test.setLocalDestination("Page-"+currentPageNumber);
							ph.add(test);
							
							 ColumnText.showTextAligned(stamp.getUnderContent(),
									Element.ALIGN_RIGHT, ph, x, y, 0);
						}
						else{
							ColumnText.showTextAligned(stamp.getUnderContent(),
									Element.ALIGN_RIGHT, new Phrase(String
											.format("%d", currentPageNumber),new Font(FontFamily.TIMES_ROMAN,3)),
									x, y, 0);
						}
						stamp.alterContents();
					}
				
					PDFCombiner.addPage(page);
				}
			}
			PDFJoinInJava.resetPageCount();
			PDFJoinInJava.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (document.isOpen())
				document.close();
			try {
				if (outputStream != null)
					outputStream.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			System.gc();
		}
		
		
		return pdfCounter;
	}
}