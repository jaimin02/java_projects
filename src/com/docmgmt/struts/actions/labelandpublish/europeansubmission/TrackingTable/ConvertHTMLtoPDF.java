package com.docmgmt.struts.actions.labelandpublish.europeansubmission.TrackingTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.docmgmt.server.webinterface.services.pdf.PdfPropUtilities;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class ConvertHTMLtoPDF {
	
	public static void main(String[] args)
			throws com.lowagie.text.DocumentException, IOException,
			ParserConfigurationException, SAXException {

	}

	public void createPDFFromHTML(String pdfFilename1, String pdfnewpath,
			String htmlfile) {

		// path for the PDF file to be generated
		String path = pdfFilename1;
		PdfWriter pdfWriter = null;

		// create a new document
		Document document = new Document();

		try {

			// get Instance of the PDFWriter
			pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(
					path));
			// pdfWriter.setPdfVersion(PdfWriter.PDF_VERSION_1_4);
			pdfWriter.setLinearPageMode();
			pdfWriter.setFullCompression();

			// document header attributes
			document.addAuthor("");
			document.addCreationDate();
			document.addProducer();

			document.addTitle("");
			document.setPageSize(PageSize.A4);

			// open document
			document.open();

			HTMLWorker htmlWorker = new HTMLWorker(document);

			String str = "";
			StringBuilder contentBuilder = new StringBuilder();
			BufferedReader in = null;

			try {
				in = new BufferedReader(new FileReader(htmlfile));

				while ((str = in.readLine()) != null) {
					if (str.equals("<small>DTD Version 0.1</small>")) {
						continue;
					} else if (str.equals("<title>Tracking Table</title>")) {
						continue;
					} else if (str.equals("<h3>MRP Tracking Table</h3>")) {
						str = "<p align=center>MRP Tracking Table</p>";
					} else if (str.equals("<h3>DCP Tracking Table</h3>")) {
						str = "<p align=center>DCP Tracking Table</p>";
					}
					/*
					 * else if(str.contains("Submission Description")) {str=
					 * "<th rowspan=2 style=display:inline;>Sequence</th><th><p style=display:inline;>Submission Description</p></th><th>RMS</th><th colspan=2>"
					 * ; }
					 */
					contentBuilder.append(str);
					
				}
			} catch (IOException e) {
				System.out.print("HTML file close problem:" + e.getMessage());
			} finally {

				in.close();
				System.gc();
			}
			String content = contentBuilder.toString();
		
			htmlWorker.parse(new StringReader(content));

			document.close();

			pdfWriter.close();

			setPDFVersion(path, pdfnewpath);
		

		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		PdfPropUtilities pdfUtil = new PdfPropUtilities();
		pdfUtil.autoCorrectPdfProp(pdfnewpath);
		// pdfUtil.linearize(path, pdfnewpath);

	}

	
	public void converPDFtoHTML() throws com.lowagie.text.DocumentException,
			IOException, ParserConfigurationException, SAXException {

	}

	public void setPDFVersion(String path, String pdfnewpath) {
		try {
			
			PdfReader pr = new PdfReader(path);

			PdfStamper ps = new PdfStamper(pr,
					new FileOutputStream(pdfnewpath), PdfWriter.VERSION_1_4);
			ps.close();

		} catch (Exception e) {
			System.out.print("Error!" + e.getMessage());
		}
	}

}
