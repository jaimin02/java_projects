package com.docmgmt.struts.actions.labelandpublish.europeansubmission.TrackingTable.copy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.docmgmt.server.prop.PropertyInfo;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

//import com.pdfcrowd.Client;
//import com.pdfcrowd.PdfcrowdError;
public class XMLToPdfConverter {
	// static String
	// styleFileLocation="//90.0.0.15\\docmgmtandpub\\PublishDestinationFolder\\0156\\0443\\vijay_test\\0000\\m1\\eu\\util\\style\\tracking-table.xsl";
	// static String
	// xmlFileLocation="//90.0.0.15/docmgmtandpub/PublishDestinationFolder/0156/0443/vijay_test/0000/m1/eu/10-cover/common/common-cover-tracking.xml";
	// static String pdfFileLocation="E:/vijay/eu-regional.pdf";
	// static String htmlFileLocation="E:/vijay/eu-regional.html";

	public static void main(String str[]) {

		XMLToPdfConverter hpc = new XMLToPdfConverter();
		XMLToPdfConverter hpc1 = new XMLToPdfConverter();
		// hpc.xmlToHTML(styleFileLocation,xmlFileLocation,"D:/vijay/pdf2.pdf","D:/vijay/pdf3.pdf");
		ConvertHTMLtoPDF chp = new ConvertHTMLtoPDF();
		// chp.createPDFFromHTML("D:/vijay/pdf2.pdf","");

		// hpc.htmltopdfusingCrowd();
		// hpc1.removeFiles();
	}

	public void TestWorker() {
		// Document document = new Document();
		try {
			com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
					"D:/testpdf.pdf"));

			doc.open();
			XMLWorkerHelper.getInstance().parseXHtml(writer, doc,
					new FileInputStream("D:/testhtml.html"));
			doc.close();
			System.out.println("completed---");

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void xmlToHTML(File eu,String euDtdVersion) {

		// TestWorker();
		String xslFilepath = eu.getAbsolutePath()
				+ "/util/style/tracking-table.xsl";
		FileSystemView fsv = new FileSystemView() {

			@Override
			public File createNewFolder(File containingDir) throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
		};
//		System.out.println("Default=" + fsv.getDefaultDirectory()
//				+ "/temp.html");
//
//		System.out.println("Absolute Path=" + fsv.getDefaultDirectory()
//				+ "/temp.html");
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();

		File baseWorkDir = propertyInfo.getDir("BaseWorkFolder");
		System.out.println("AbsolutePath(3)=" + eu.getParentFile() );
		//String htmlFileLocation =eu.getAbsolutePath()+ "\\common-cover-tracking.html";
	
//		
//		File test=new File("C:/temp");
//		if(!test.exists()){
//			test.mkdir();
//			System.out.println("Not Exist");
//		}
//		else
//			System.out.println("Exist");
		
		
	//	String htmlFileLocation ="C:/temp/temp.html";
		
		String htmlFileLocation=fsv.getDefaultDirectory()+ "/temp.html";
	
		String xmlFilePath = eu.getAbsolutePath()
				+ "/10-cover/common/common-cover-tracking.xml";
		String pdfFilePath = eu.getAbsolutePath()
				+ "/10-cover/common/common-cover-tracking1.pdf";
		String pdfNewFilePath;
		if(euDtdVersion.equals("20"))
		{
			pdfNewFilePath = eu.getAbsolutePath()
			+ "/10-cover/common/common-tracking.pdf";
		}
		else
		{
			pdfNewFilePath = eu.getAbsolutePath()
			+ "/10-cover/common/common-tracking.pdf";
		}
		
		
		// String htmlFileLocation = fsv.getDefaultDirectory() + "/temp.html";
	
		try {
			System.gc();
			File stylesheet, xmlFile, htmlFile;
			stylesheet = new File(xslFilepath);
			xmlFile = new File(xmlFilePath);
			htmlFile = new File(htmlFileLocation);

			//htmlFile=File.createTempFile("temp.html", null);
			
			System.out.println("AbsolutePath(4)=" +htmlFile.getAbsolutePath());
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(xmlFile);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory
					.newTransformer(new StreamSource(stylesheet));
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(htmlFile);
			transformer.transform(source, result);

			System.gc();
			htmlFile = null;
			ConvertHTMLtoPDF chp = new ConvertHTMLtoPDF();
			chp.createPDFFromHTML(pdfFilePath, pdfNewFilePath,
							htmlFileLocation);

		} catch (TransformerConfigurationException e) {
			System.err.println("TransformerConfigurationException: "
					+ e.getMessage());
		} catch (TransformerException e) {
			System.err.println("TransformerException: " + e.getMessage());
		} catch (ParserConfigurationException e) {
			System.err.println("TransformerConfigurationException: "
					+ e.getMessage());
		} catch (IOException e) {
			System.err.println("TransformerException(File): " + e.getMessage());
		} catch (SAXException e) {
			System.err.println("TransformerException(SAX): " + e.getMessage());
		} catch (Exception ge) {
			System.err.println("XML to HTML TransformerException: "
					+ ge.getMessage());
		} finally {

			// System.gc();
		}
		// String htmlfile="E:/vijay/eu-regional.html";
		 removeFiles(xmlFilePath, htmlFileLocation, pdfFilePath);
		// test.delete();
	}

	synchronized public void removeFiles(String xmlfile, String htmlfile,
			String pdffile) {
		System.gc();

		try {
			File hFile = new File(htmlfile);
			File xFile = new File(xmlfile);
			File pdfFile = new File(pdffile);
			if (pdfFile.exists()) {
				pdfFile.delete();
			}
			if (hFile.exists()) {
				hFile.delete();
				System.out.println("HTML File Delete :" + xmlfile);
			}

			if (xFile.exists()) {
				xFile.delete();
				System.out.println("xmlFile Delete :" + xmlfile);
			}
		} catch (Exception fe) {
			System.out.println("Removing File Error : " + fe.getMessage());
			fe.printStackTrace();
		}

	}

}
