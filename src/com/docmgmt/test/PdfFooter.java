package com.docmgmt.test;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.opensymphony.xwork2.ActionSupport;

public class PdfFooter extends ActionSupport {

	public static String SRC = "D:/vijay/PDFs samples/cover-letters.pdf";
	public static String DEST = "D:/vijay/PDFs samples/95.pdf";

	public static void main(String arg[]) {

		try {
			PdfFooter.setFooter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String setFooter() throws IOException, DocumentException {

		PdfReader pdfReader = new PdfReader(SRC);
		PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(
				DEST));
		// Image image = Image.getInstance("D:/Vruddhi/a.jpg");

		int n = pdfReader.getNumberOfPages();
		for (int i = 1; i <= n; i++) {
			Rectangle size = pdfReader.getPageSize(i);
			pdfStamper.setRotateContents(false);
			PdfContentByte canvas = pdfStamper.getOverContent(i);
			System.out.println(size.getHeight() + " " + size.getWidth());

			LineSeparator UNDERLINE = new LineSeparator(1, 100, null,
					Element.ALIGN_CENTER, -2);

			// Image img = Image.getInstance("D:/Vruddhi/bdr.png");

			// img.setAbsolutePosition( size.getLeft()+10 , 25);
			// canvas.addImage(img);

			float x = 1f;
			float y = size.getTop();

			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase(new Chunk(UNDERLINE)), x, y-50, 0);

			ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
					new Phrase(String.format("Page %d of %d", i, n)), size
							.getRight() - 50, 10, 0);

		}
		pdfStamper.close();
		return SUCCESS;
	}

}
