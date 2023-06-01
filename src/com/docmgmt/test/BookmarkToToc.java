package com.docmgmt.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfLiteral;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.SimpleBookmark;

public class BookmarkToToc {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// 1001 toc

		createXml("D://1001 toc.pdf", "D://1001_bookmark.xml");
	}

	public static void createXml(String src, String dest) throws IOException {
		try {
			PdfReader reader = new PdfReader(src);
			List<HashMap<String, Object>> list = SimpleBookmark
					.getBookmark(reader);
			SimpleBookmark.exportToXML(list, new FileOutputStream(dest),
					"ISO8859-1", true);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					"D://1001_new.pdf"));
			stamper.insertPage(1, reader.getPageSize(1));
			PdfContentByte canvas = stamper.getOverContent(1);
	
			Chunk c=new Chunk("Click Me");
			
			PdfAction action = new PdfAction();
			action.put(PdfName.S, PdfName.GOTO);
			//action.put.
			action.put(PdfName.D, new PdfLiteral("[" + (2) + " /XYZ 0 624 .00]"));
		//	canvas.setAction(action, 0, 100, 20, 20);
			c.setAction(action);
			
			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(c), 36, 540, 0);
			
			stamper.close();
			reader.close();
		} catch (Exception de) {
			de.printStackTrace();
		}

	}

	public static void createLink() {

		try {
			int page = 3;
			PdfAction action = new PdfAction();
			action.put(PdfName.S, PdfName.GOTO);
			action.put(PdfName.D, new PdfLiteral("[" + (page - 1) + " /Fit]"));

		} catch (Exception de) {
			de.printStackTrace();
		}

	}

}
