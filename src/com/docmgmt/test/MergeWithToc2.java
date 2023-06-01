package com.docmgmt.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfCopy.PageStamp;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

public class MergeWithToc2{

	public static final String SRC1 = "D://pdfs/first1.pdf";
	public static final String SRC3 = "D://pdfs/second.pdf";
	public static final String SRC2 = "D://pdfs/third.pdf";
	public static final String SRCBLANK = "D://pdfs/blankpdf.pdf";
	public static final String SRCINDEX = "D://pdfs/index.pdf";
	public static final String DEST = "D://pdfs/merged.pdf";
	
	
	public String addTOC = "yes";
	public String addHeader = "yes";

	public String productName = "Product Name";
	public Map<String, PdfReader> filesToMerge;

	public static void main(String[] args) throws IOException,
			DocumentException {
		File file = new File(DEST);
		file.getParentFile().mkdirs();
		MergeWithToc2 app = new MergeWithToc2();
	//	app.createPdf(DEST);
	}

	public MergeWithToc2() throws IOException {
//		filesToMerge = new LinkedHashMap<String, PdfReader>();
//		// filesToMerge.put("1:titlepage", new PdfReader(SRCBLANK));
//		filesToMerge.put("1.COuntry", new PdfReader(SRC1));
//		// filesToMerge.put("2:titlepage", new PdfReader(SRCBLANK));
//		filesToMerge.put("2.ABCD", new PdfReader(SRC2));
//		// filesToMerge.put("3:titlepage", new PdfReader(SRCBLANK));
//		filesToMerge.put("3.ABCD", new PdfReader(SRC3));
//		// filesToMerge.put("4:titlepage", new PdfReader(SRCBLANK));
//		filesToMerge.put("4.ABCD", new PdfReader(SRC3));
//		// filesToMerge.put("5:titlepage", new PdfReader(SRCBLANK));
//		filesToMerge.put("5.ABCD", new PdfReader(SRC3));

	}

	public int createPdf(String filename,Map<String, PdfReader> filesToMerge) throws IOException,
			DocumentException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Map<Integer, String> toc = new TreeMap<Integer, String>();
		Document document = new Document();
		PdfCopy copy = new PdfCopy(document, baos);
		PageStamp stamp;
		document.open();
		int n;
		int pageNo = 0;
		int pdfCounter=0;
		
		PdfImportedPage page;
		Chunk chunk;
		for (Map.Entry<String, PdfReader> entry : filesToMerge.entrySet()) {
			n = entry.getValue().getNumberOfPages();
			
			toc.put(pageNo + 1, entry.getKey());
			pdfCounter++;
			for (int i = 0; i < n;) {

				String val[] = entry.getKey().toString().split(":");

				pageNo++;
				page = copy.getImportedPage(entry.getValue(), ++i);

				stamp = copy.createPageStamp(page);
				chunk = new Chunk(String.format("Page %d", pageNo));
				if (i == 1)
					chunk.setLocalDestination("p" + pageNo);
				ColumnText.showTextAligned(stamp.getUnderContent(),
						Element.ALIGN_RIGHT, new Phrase(chunk),
						page.getWidth() - 10, page.getHeight() - 10, 0);
				if (addHeader.equals("yes")) {
					if (productName != null && !productName.equals("")) {
						ColumnText.showTextAligned(stamp.getUnderContent(),
								Element.ALIGN_CENTER, new Phrase(productName), page
										.getWidth() / 2, page.getHeight() - 20, 0);
	
					}

				
					PdfContentByte pcb = stamp.getOverContent();
					pcb.setLineWidth(2.0f);
					pcb.setGrayStroke(0.50f); // // 1 = black, 0 = white
					float x = 1f;
					float y = page.getHeight() - 60;
					pcb.moveTo(x, y);
					pcb.lineTo(page.getWidth(), y);
					pcb.stroke();
					Image img = Image.getInstance("D://pdfs/logo.png");
					
					img.setAbsolutePosition(20, page.getHeight() - 50);
					pcb.addImage(img);

				}
				if (val.length >= 2) {
					// if title page adding
					System.out.println("Title page");
					ColumnText.showTextAligned(stamp.getUnderContent(),
							Element.ALIGN_RIGHT, new Phrase("Title"), page
									.getWidth() / 2, page.getHeight() / 2, 0);

				}
				stamp.alterContents();
				copy.addPage(page);
			}
		}
		int indexPageCounter = 1;
		PdfReader reader = new PdfReader(SRCINDEX);
		if (addTOC.equals("yes")) {
			reader = new PdfReader(SRCINDEX);
			page = copy.getImportedPage(reader, 1);
			stamp = copy.createPageStamp(page);
			Paragraph p;
			PdfAction action;
			PdfAnnotation link;
			float y = 770;
			ColumnText ct = new ColumnText(stamp.getOverContent());
			ct.setSimpleColumn(36, 36, 559, y);

			for (Map.Entry<Integer, String> entry : toc.entrySet()) {

				if (y <= 450) {
					indexPageCounter++;
					ct.go();
					stamp.alterContents();
					copy.addPage(page);
					reader = new PdfReader(SRCINDEX);
					page = copy.getImportedPage(reader, 1);
					stamp = copy.createPageStamp(page);
					y = 770;
					ct = new ColumnText(stamp.getOverContent());
					ct.setSimpleColumn(36, 36, 559, y);

				}
				String val[] = entry.getValue().toString().split(":");
				if (val.length >= 2) {
					// if title page
					continue;

				}
				p = new Paragraph(entry.getValue());
				p.add(new Chunk(new DottedLineSeparator()));
				p.add(String.valueOf(entry.getKey()));
				ct.addElement(p);
				ct.go();
				action = PdfAction.gotoLocalPage("p" + entry.getKey(), false);
				link = new PdfAnnotation(copy, 36, ct.getYLine(), 559, y,
						action);
				stamp.addAnnotation(link);
				y = ct.getYLine();

			}

			ct.go();
			stamp.alterContents();

			copy.addPage(page);
		}
		document.close();
		for (PdfReader r : filesToMerge.values()) {
			r.close();
		}
		reader.close();

		reader = new PdfReader(baos.toByteArray());
		if (addTOC.equals("yes")) {
			n = reader.getNumberOfPages();

			reader.selectPages(String.format("%d-%d, 1-%d", n
					- indexPageCounter + 1, n, n - indexPageCounter + 1));

			// for (int i = 0; i < indexPageCounter; i++)
			// reader.selectPages(String.format("%d, 1-%d", n, n - 1));
			// reader.selectPages(String.format("%d, 1-%d", n, n-1));
		}
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
				filename));
		stamper.close();
		return pdfCounter;
	}
	

}