package com.docmgmt.struts.actions.labelandpublish.europeansubmission.TrackingTable.copy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

public class ConvertHTMLtoPDF {
	static String styleFileLocation = "//90.0.0.15\\docmgmtandpub\\PublishDestinationFolder\\0156\\0443\\vijay_test\\0000\\m1\\eu\\util\\style\\tracking-table.xsl";
	static String xmlFileLocation = "//90.0.0.15/docmgmtandpub/PublishDestinationFolder/0156/0443/vijay_test/0000/m1/eu/10-cover/common/common-cover-tracking.xml";
	static String pdfFileLocation = "E:/vijay/eu-regional.pdf";
	static String htmlFileLocation = "E:/vijay/eu-regional.html";
	static File stylesheet;
	static File xmlFile;
	static File htmlFile;

	public static void main(String[] args)
			throws com.lowagie.text.DocumentException, IOException,
			ParserConfigurationException, SAXException {

		String pdfFilename = "";
		ConvertHTMLtoPDF convertHTMLToPDF = new ConvertHTMLtoPDF();

		pdfFilename = "D:/vijay/pdf2.pdf";
		// convertHTMLToPDF.createPDFFromHTML(pdfFilename);

		convertHTMLToPDF.converPDFtoHTML();
	}

	public void createPDFFromHTML(String pdfFilename1,String pdfnewpath,String htmlfile) {

		// path for the PDF file to be generated
		String path = pdfFilename1;
		PdfWriter pdfWriter = null;

		// create a new document
		Document document = new Document();

		try {

			// get Instance of the PDFWriter
			pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(
					path));
			//pdfWriter.setPdfVersion(PdfWriter.PDF_VERSION_1_4);
			pdfWriter.setLinearPageMode();
			pdfWriter.setFullCompression();
			
			
			// document header attributes
			document.addAuthor("");
			document.addCreationDate();
			document.addProducer();
			document.addCreator("aaa");
			document.addTitle("");
			document.setPageSize(PageSize.A4);

			// open document
			document.open();

			HTMLWorker htmlWorker = new HTMLWorker(document);
			
			String str = "";
			StringBuilder contentBuilder = new StringBuilder();
			BufferedReader in = null;

			System.out.println("Html Content :");
			try {
				in = new BufferedReader(new FileReader(
						htmlfile));

				while ((str = in.readLine()) != null) {
					if(str.equals("<small>DTD Version 0.1</small>"))
					{
						continue;
					}
					else if(str.equals("<title>Tracking Table</title>"))
					{
						continue;
					}
					else if(str.equals("<h3>MRP Tracking Table</h3>"))
					{
						str="<p align=center>MRP Tracking Table</p>";
					}
					else if(str.equals("<h3>DCP Tracking Table</h3>"))
					{
						str="<p align=center>DCP Tracking Table</p>";
					}
					/*else if(str.contains("Submission Description"))
					{
						str="<th rowspan=2 style=display:inline;>Sequence</th><th><p style=display:inline;>Submission Description</p></th><th>RMS</th><th colspan=2>";
					}*/
					contentBuilder.append(str);
					System.out.println(str);
				}
			} catch (IOException e) {
				System.out.print("HTML file close problem:" + e.getMessage());
			} finally {

				in.close();
				System.gc();
			}
			String content = contentBuilder.toString();
			//StyleSheet styles = new StyleSheet();
			// styles.loadStyle("td", "color", "red");
			//styles.loadTagStyle("th", "width", "120px");

			//htmlWorker.setStyleSheet(styles);
			htmlWorker.parse(new StringReader(content));

			document.close();
			
			pdfWriter.close();
			
			setPDFVersion(path,pdfnewpath);
			/*
			 * // To convert a HTML file from the filesystem // String
			 * File_To_Convert = "docs/SamplePDF.html"; // FileInputStream fis =
			 * new FileInputStream(File_To_Convert);
			 * 
			 * // URL for HTML page URL myWebPage = new
			 * URL("file:///E:/vijay/eu-regional.html"); InputStreamReader fis =
			 * new InputStreamReader(myWebPage .openStream());
			 * 
			 * // get the XMLWorkerHelper Instance XMLWorkerHelper worker =
			 * XMLWorkerHelper.getInstance(); // convert to PDF
			 * worker.parseXHtml(pdfWriter, document, fis);
			 * 
			 * // close the document document.close(); // close the writer
			 */
			//pdfWriter.setLinearPageMode();
		
			// File fr=new File("E:/vijay/eu-regional.html");
			// fr.delete();

		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	public void converPDFtoHTML() throws com.lowagie.text.DocumentException,
			IOException, ParserConfigurationException, SAXException {

	}

	public void setPDFVersion(String path,String pdfnewpath) {
		try {
			File f=new File(path);
			PdfReader pr = new PdfReader(path);
			System.out.print("PPath :"+path);
			PdfStamper ps = new PdfStamper(pr, new FileOutputStream(pdfnewpath),
					PdfWriter.VERSION_1_4);
		
			//ps.setFullCompression();
		
			
			ps.close();
		} catch (Exception e) {
				System.out.print("Error!"+e.getMessage());
		}
	}

}
