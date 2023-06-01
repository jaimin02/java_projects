/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.docmgmt.server.webinterface.services.pdf;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.print.PrintService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.pdfbox.exceptions.COSVisitorException;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;
import org.pdfbox.pdmodel.common.PDRectangle;
import org.pdfbox.pdmodel.edit.PDPageContentStream;
import org.pdfbox.pdmodel.font.PDFont;
import org.pdfbox.pdmodel.font.PDType1Font;
import org.pdfbox.util.PDFTextStripper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.SimpleBookmark;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;

/**
 * 
 * @author nagesh
 */
public class PDFUtilities {
	public static final short POSITION_TOPLEFT = 1;
	public static final short POSITION_TOPRIGHT = 2;
	public static final short POSITION_TOPCENTER = 3;
	public static final short POSITION_BOTTOMRIGHT =4;
	public static final short POSITION_BOTTOMLEFT =5;
	public static final short POSITION_BOTTOMCENTER = 6;

	public static void main(String[] args) {
		// System.out.println("call::"+checkBookmarksValid("D:/IMP/KnowledgeNETSSPL/ELC (ALL)/ELC Dossiers/16-Mar-10/Escitalopram-Cinfa/0000/m1/eu/10-cover/es/es-cover.pdf"));
		
		PDFUtilities pu=new PDFUtilities();
		String a = "D://vijay/bookmark.pdf";
		pu.getAllPdfProperties(a);
		
		
		// String b =
		// "/home/data/testdossiers/eu/Abc/0000/m1/eu/10-cover/de/de-cover.txt";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*
		try {
			System.out.println("Valid or not->"
					+ PDFUtilities.setInitialZoom(a,"D://Shared/vijay/pdfprop4.pdf"));

		
			 PDFUtilities.setInitialZoom(a, "D://Shared/vijay/pdfprop2.pdf");
			PDFTextStripper stripper = new PDFTextStripper();
			PDDocument document = PDDocument.load(a);
			System.out.println(document.getNumberOfPages());
			// stripper.writeText(document, new OutputStreamWriter(new
			// FileOutputStream(b)));
			stripper.writeText(document, new OutputStreamWriter(baos));
			String s = new String(baos.toByteArray());
		
			// System.out.println(" - " + s.length());
			int v = 0;
			for (int i = 1; i <= document.getNumberOfPages(); i++) {
				stripper.setStartPage(i);
				stripper.setEndPage(i);
				stripper.writeText(document, new OutputStreamWriter(baos));
				s = new String(baos.toByteArray());
				// System.out.println(s);
				// System.out.println(i + " - " + s.length());
				v += s.length();
			}
			
			// System.out.println("v:" + v);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
	}

	public static boolean searchInPDF(String filePath, String searchKeyword)
			throws Exception {
		PDDocument pdDocument = null;
		try {
			PDFTextStripper pdfTextStripper = new PDFTextStripper();
			pdDocument = PDDocument.load(filePath);
			for (int indPageNo = 1; indPageNo <= pdDocument.getNumberOfPages(); indPageNo++) {
				pdfTextStripper.setStartPage(indPageNo);
				pdfTextStripper.setEndPage(indPageNo);
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
						byteArrayOutputStream);
				pdfTextStripper.writeText(pdDocument, outputStreamWriter);
				String pageText = new String(byteArrayOutputStream
						.toByteArray());
				pageText = pageText.toLowerCase();
				searchKeyword = searchKeyword.toLowerCase();
				if (pageText != null && !pageText.equals("")
						&& pageText.contains(searchKeyword)) {
					System.out.println("indPageNo" + indPageNo);
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pdDocument != null)
				pdDocument.close();
		}
		return false;
	}

	public static Vector<Integer> pageNosInPDF(String filePath,
			String searchKeyword) throws Exception {
		PDDocument pdDocument = null;
		Vector<Integer> pgNos = new Vector<Integer>();
		try {
			PDFTextStripper pdfTextStripper = new PDFTextStripper();
			pdDocument = PDDocument.load(filePath);
			for (int indPageNo = 1; indPageNo <= pdDocument.getNumberOfPages(); indPageNo++) {
				pdfTextStripper.setStartPage(indPageNo);
				pdfTextStripper.setEndPage(indPageNo);
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
						byteArrayOutputStream);
				pdfTextStripper.writeText(pdDocument, outputStreamWriter);
				String pageText = new String(byteArrayOutputStream
						.toByteArray());
				if (pageText != null && !pageText.equals("")
						&& pageText.contains(searchKeyword)) {
					System.out.println("indPageNo" + indPageNo);
					pgNos.add(indPageNo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pdDocument != null)
				pdDocument.close();
		}
		return pgNos;
	}

	/**
	 * check pdf version
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String checkPDFversion(String filePath) throws IOException {
		String retString = null;
		PdfReader pp = new PdfReader(filePath);
		retString = "1." + pp.getPdfVersion();
		pp.close();
		return retString;
	}

	/**
	 * change pdf version, as well as remove password protection
	 * 
	 * @param oldFilePath
	 * @param ownerPassword
	 * @param newFilePath
	 * @param newVersion
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static boolean changePDFversion(String oldFilePath,
			String ownerPassword, String newFilePath, String newVersion)
			throws IOException, DocumentException {
		boolean changeOK = false;
		PdfStamper ps = new PdfStamper(new PdfReader(oldFilePath, ownerPassword
				.getBytes()), new FileOutputStream(newFilePath), newVersion
				.charAt(newVersion.length() - 1));
		ps.close();
		changeOK = true;
		return changeOK;
	}

	/**
	 * checks whether the pdf is password protected
	 * 
	 * @param filePath
	 * @return
	 */
	public static short checkAccessible(String filePath) {
		short isAccessible = 2;
		PdfReader testObj = null;
		try {
			testObj = new PdfReader(filePath);
			if (!testObj.isOpenedWithFullPermissions()) {
				isAccessible = 1;
			}
		} catch (Exception e) {
			isAccessible = 0;
		} finally {
			if (testObj != null)
				testObj.close();
		}
		return isAccessible;
	}

	/**
	 * checks pdf for the given password
	 * 
	 * @param filePath
	 * @param password
	 * @return
	 */
	public static boolean checkPasswordCorrect(String filePath, String password) {
		boolean isPasswordCorrect = true;
		PdfReader testObj = null;
		try {
			testObj = new PdfReader(filePath, password.getBytes());
		} catch (Exception e) {
			isPasswordCorrect = false;
		} finally {
			if (testObj != null)
				testObj.close();
		}
		return isPasswordCorrect;
	}

	/**
	 * checks whether bookmarks are valid
	 * 
	 * @param filePath
	 * @return
	 */

	public static boolean checkBookmarksValid(String filePath) {
		boolean retVal = true;
		PdfReader reader = null;
		try {
			reader = new PdfReader(filePath);

			java.util.List<HashMap<String, Object>> listBookmark = hasBookmarks(reader);
			if (listBookmark != null) {
				ByteArrayOutputStream xmlOutStream = new ByteArrayOutputStream();
				SimpleBookmark.exportToXML(listBookmark, xmlOutStream,
						"ISO8859-1", true);
				byte[] xmlBytes = xmlOutStream.toByteArray();

				DocumentBuilderFactory builder = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder parser = builder.newDocumentBuilder();
				Document document = parser.parse(new ByteArrayInputStream(
						xmlBytes));
				NodeList list = document.getElementsByTagName("Title");
				System.out.println("->"+list.getLength());
				
				for (int i = 0; i < list.getLength(); i++) {
					
					
					Node node = list.item(i);
					
					//System.out.println("Name->"+node.getTextContent());
					String action = ((Element) node).getAttribute("Action");
					if (action == null || action.equals("")) {
						System.out.println("Invalid Action");
						
						retVal = false;
						break;
					}
				
				
					if (action.equals("GoTo")) {
						System.out.println("S");
						String namedLoc = ((Element) node)
								.getAttribute("Named");
						
						
						String pageLoc = ((Element) node).getAttribute("Page");
						 System.out.println(pageLoc);
						 System.out.println("->"+namedLoc);
						if ((namedLoc == null || namedLoc.equals(""))
								&& (pageLoc == null || pageLoc.equals(""))) {
							retVal = false;
							
							System.out.println("1");
							break;
						}
						if (namedLoc != null && !namedLoc.equals("")) {
							
							System.out.println("2");
							HashMap<Object, PdfObject> map = reader
									.getNamedDestination();
							if (!map.containsKey(namedLoc)) {
								retVal = false;
								break;
							}
							PdfObject pdfObject = (PdfObject) map.get(namedLoc);
							
					
							String str = pdfObject.toString();
							System.out.println("->"+str);
							if (str.substring(str.indexOf('[') + 1,
									str.indexOf('[') + 5).equals("null")) {
								retVal = false;
								
								System.out.println("Invalid");
								break;
							}
							if (str.contains("XYZ")) {
								String zoomFactor = str.substring(str
										.lastIndexOf(" ") + 1, str.length());
								 System.out.println("zoom: " + zoomFactor);
								if (!zoomFactor.equals("0.0")) {
									retVal = false;
									break;
								}
							} else {
								retVal = false;
								break;
							}
						}
						if (pageLoc != null && !pageLoc.equals("")) {
							
							System.out.println("3");
							if (pageLoc.contains("XYZ")) {
								String zoomFactor = pageLoc
										.substring(
												pageLoc.lastIndexOf(" ") + 1,
												pageLoc.length());
								// System.out.println("zoom: " + zoomFactor);
								if (!zoomFactor.equals("0.0")) {
									retVal = false;
									break;
								}
							} else {
								retVal = false;
								break;
							}
						}
						continue;
					}
					
				}
				System.out.println("ff");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			retVal = false;
			// Logger.getLogger(PDFUtilities.class.getName()).log(Level.SEVERE,
			// null, ex);
		} finally {
			reader.close();
		}

		return retVal;
	}
	
	public static ArrayList<String> getBookmarksErrors(String filePath) {
		boolean retVal = true;
		
		ArrayList<String> bookmarkerrors=new ArrayList<String>();
		PdfReader reader = null;
		try {
			reader = new PdfReader(filePath);

			java.util.List<HashMap<String, Object>> listBookmark = hasBookmarks(reader);
			if (listBookmark != null) {
				ByteArrayOutputStream xmlOutStream = new ByteArrayOutputStream();
				SimpleBookmark.exportToXML(listBookmark, xmlOutStream,
						"ISO8859-1", true);
				byte[] xmlBytes = xmlOutStream.toByteArray();

				DocumentBuilderFactory builder = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder parser = builder.newDocumentBuilder();
				Document document = parser.parse(new ByteArrayInputStream(
						xmlBytes));
				NodeList list = document.getElementsByTagName("Title");
				System.out.println("->"+list.getLength());
				
				for (int i = 0; i < list.getLength(); i++) {
					
					
					Node node = list.item(i);
					
					//System.out.println("Name->"+node.getTextContent());
					String action = ((Element) node).getAttribute("Action");
					if (action == null || action.equals("")) {
						bookmarkerrors.add("Invalid Action Found in Bookmark "+node.getTextContent());
					}
				
				
					if (action.equals("GoTo")) {
						
						String namedLoc = ((Element) node)
								.getAttribute("Named");
						
						
						String pageLoc = ((Element) node).getAttribute("Page");
//						 System.out.println(pageLoc);
//						 System.out.println("->"+namedLoc);
						if ((namedLoc == null || namedLoc.equals(""))
								&& (pageLoc == null || pageLoc.equals(""))) {
							retVal = false;
							bookmarkerrors.add("Invalid Page in Bookmark "+node.getTextContent());
						}
						if (namedLoc != null && !namedLoc.equals("")) {
							
							
							HashMap<Object, PdfObject> map = reader
									.getNamedDestination();
							if (!map.containsKey(namedLoc)) {
								retVal = false;
								bookmarkerrors.add("Invalid Name  "+node.getTextContent());
							}
							PdfObject pdfObject = (PdfObject) map.get(namedLoc);
							String str = pdfObject.toString();
							System.out.println("->"+str);
							if (str.substring(str.indexOf('[') + 1,
									str.indexOf('[') + 5).equals("null")) {
								retVal = false;
								bookmarkerrors.add("Invalid Property in Bookmark : "+node.getTextContent());
							}
							if (str.contains("XYZ")) {
								String zoomFactor = str.substring(str
										.lastIndexOf(" ") + 1, str.length());
								 System.out.println("zoom: " + zoomFactor);
								if (!zoomFactor.equals("0.0")) {
									retVal = false;
									bookmarkerrors.add("Invalid Zoom Level Property in Bookmark : "+node.getTextContent());
								}
							} else {
								bookmarkerrors.add("Invalid Zoom Level Property in Bookmark : "+node.getTextContent());
								retVal = false;
								
							}
						}
						if (pageLoc != null && !pageLoc.equals("")) {
							
							System.out.println("3");
							if (pageLoc.contains("XYZ")) {
								String zoomFactor = pageLoc
										.substring(
												pageLoc.lastIndexOf(" ") + 1,
												pageLoc.length());
								// System.out.println("zoom: " + zoomFactor);
								if (!zoomFactor.equals("0.0")) {
									bookmarkerrors.add("Invalid Zoom Level Property in Bookmark : "+node.getTextContent());
									
								}
							} else {
								retVal = false;
								bookmarkerrors.add("Invalid Zoom Level Property in Bookmark : "+node.getTextContent());
							}
						}
						continue;
					}
					
				}
				System.out.println("ff");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			
			bookmarkerrors.add("Invalid Bookmark(s)");
			retVal = false;
			// Logger.getLogger(PDFUtilities.class.getName()).log(Level.SEVERE,
			// null, ex);
		} finally {
			reader.close();
		}

		return bookmarkerrors;
	}

	public static void changePageLayoutAndMode(String oldFilePath,
			String newFilePath) {
		PdfReader reader = null;
		try {
			reader = new PdfReader(oldFilePath);
			PdfStamper newPDF = new PdfStamper(reader, new FileOutputStream(
					newFilePath));
			newPDF.getWriter().setViewerPreferences(
					PdfWriter.PageModeUseOutlines
							| PdfWriter.PageLayoutSinglePage);
			newPDF.close();
		} catch (Exception e) {
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	public static boolean checkPageLayoutAndMode(String filePath) {
		PdfReader reader = null;
		boolean isPDFok = true;
		System.out
				.println((PdfWriter.PageModeUseNone | PdfWriter.PageLayoutSinglePage));
		System.out
				.println((PdfWriter.PageModeUseNone | PdfWriter.PageLayoutOneColumn));
		System.out
				.println((PdfWriter.PageModeUseOutlines | PdfWriter.PageLayoutSinglePage));
		System.out
				.println((PdfWriter.PageModeUseOutlines | PdfWriter.PageLayoutOneColumn));

		System.out.println(PdfWriter.PageModeUseNone
				| PdfWriter.PageModeUseThumbs);

		try {

			reader = new PdfReader(filePath);

			PdfDictionary loDictionary = reader.getCatalog();
			if (loDictionary != null) {
				 loDictionary = (PdfDictionary) loDictionary.get(PdfName.VIEWERPREFERENCES);
				  if (loDictionary.contains(PdfName.HIDEMENUBAR)) 
				  {
					  System.out.println("Menu Bar is Hiiden"); 
				  }
				  
			}

			List<HashMap<String, Object>> listBookmark = hasBookmarks(reader);
			// If bookmarks found then it should open in 'Bookmarks Panel and
			// Page'
			// else in 'Page Only' mode
			System.out.println("-->" + reader.getSimpleViewerPreferences());

			System.out.println("Size-" + listBookmark.size());
			if (listBookmark != null && listBookmark.size() > 0) {
				if (reader.getSimpleViewerPreferences() != (128))// PdfWriter.PageModeUseOutlines
																	// |
																	// PdfWriter.PageLayoutSinglePage
					isPDFok = false;
			} else {
				System.out.println("-->" + reader.getSimpleViewerPreferences());

				if (reader.getSimpleViewerPreferences() != (64))// PdfWriter.PageModeUseNone
																// |
																// PdfWriter.PageLayoutSinglePage
					isPDFok = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				reader.close();
		}
		return isPDFok;
	}
	
	public static String getPageLayoutAndMode(String filePath) {
		PdfReader reader = null;
		String pdflayout="";
//		System.out
//				.println((PdfWriter.PageModeUseNone | PdfWriter.PageLayoutSinglePage));
//		System.out
//				.println((PdfWriter.PageModeUseNone | PdfWriter.PageLayoutOneColumn));
//		System.out
//				.println((PdfWriter.PageModeUseOutlines | PdfWriter.PageLayoutSinglePage));
//		System.out
//				.println((PdfWriter.PageModeUseOutlines | PdfWriter.PageLayoutOneColumn));
//
//		System.out.println(PdfWriter.PageModeUseNone
//				| PdfWriter.PageModeUseThumbs);

		try {

			reader = new PdfReader(filePath);

			List<HashMap<String, Object>> listBookmark = hasBookmarks(reader);
						
			if (listBookmark != null && listBookmark.size() > 0) {
				pdflayout="1"+":"+reader.getSimpleViewerPreferences(); //should be 128
			} else {
				pdflayout="0"+":"+reader.getSimpleViewerPreferences(); //should be 64 // 0 mean no bookmarks
								
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				reader.close();
		}
		return pdflayout;
	}

	public static boolean setInitialZoom(String oldFilePath, String newFilePath) {
		PdfReader testObj = null;
		boolean isPDFConvOK = true;
		try {
			testObj = new PdfReader(oldFilePath);
			PdfStamper newPDF = new PdfStamper(testObj, new FileOutputStream(
					newFilePath));
			PdfWriter newPDFWriter = newPDF.getWriter();
			PdfAction newPDFAction = PdfAction.gotoLocalPage(1,
					new PdfDestination(PdfDestination.XYZ, 0, 0, 0),
					newPDFWriter);
			newPDFWriter.setOpenAction(newPDFAction);
			newPDF.close();
		} catch (Exception e) {
			e.printStackTrace();
			isPDFConvOK = false;
		} finally {
			if (testObj != null)
				testObj.close();
		}
		return isPDFConvOK;
	}
	
	public static List<HashMap<String, Object>> hasBookmarks(PdfReader reader) {
		return SimpleBookmark.getBookmark(reader);
	}

	public static int mergePDFs(String[] pdfPaths, String newPdfPath) {
		int i = -1;
		try {
			PdfCopyFields copyPdf = new PdfCopyFields(new FileOutputStream(
					newPdfPath));
			for (i = 0; i < pdfPaths.length; i++) {
				try {
					PdfReader reader = new PdfReader(pdfPaths[i]);
					copyPdf.addDocument(reader);
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			copyPdf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (i);
	}

	public static void addStringToPdf(String srcFile, String outfile,
			String msg, float offsetX, float offsetY, int[] pages,
			short position, float X, float Y, float fontSize)
			throws IOException, COSVisitorException {
		PDDocument doc = null;
		try {
			doc = PDDocument.load(srcFile);

			List allPages = doc.getDocumentCatalog().getAllPages();
			PDFont font = PDType1Font.HELVETICA_BOLD;
			if (fontSize <= 0.0f) {
				fontSize = 12.0f;
			}
			if (offsetX <= 0) {
				offsetX = 00;
			}
			if (pages == null) {
				pages = new int[allPages.size()];
				for (int i = 0; i < pages.length; i++) {
					pages[i] = i + 1;
				}
			}
			for (int i = 0; i < pages.length; i++) {

				if (pages[i] > 0 && pages[i] <= allPages.size()) {

					PDPage page = (PDPage) allPages.get(pages[i] - 1);
					PDRectangle pageSize = page.findMediaBox();
					float stringWidth = font.getStringWidth(msg);
					float centeredPosition = ((pageSize.getWidth() - offsetX) - (stringWidth * fontSize) / 1000f) / 2f;
					float rightPosition = ((pageSize.getWidth() - offsetX) - (stringWidth * fontSize) / 1000f)
							/ 2f + centeredPosition;
					float centeredPositionforHeight = (pageSize.getHeight() - (fontSize + offsetY));
					float leftPositionforHeight = (pageSize.getHeight() - (fontSize + offsetY));

					// Set X and Y as per the position
					switch (position) {
					case POSITION_TOPRIGHT:
						X = rightPosition;
						Y = centeredPositionforHeight;
						break;
					case POSITION_TOPLEFT:
						X = offsetX;
						Y = leftPositionforHeight;
						break;
					case POSITION_TOPCENTER:
						X = centeredPosition;
						Y = centeredPositionforHeight;
						break;
					case POSITION_BOTTOMRIGHT:
						X = rightPosition;
						Y = offsetY;
						break;
					case POSITION_BOTTOMLEFT:
						X = offsetX;
						Y = offsetY;
						break;
					case POSITION_BOTTOMCENTER:
						X = centeredPosition;
						Y = offsetY;
						break;

					default:
						// Do nothing (Set X and Y as it is)
						break;
					}

					PDPageContentStream contentStream = new PDPageContentStream(
							doc, page, true, true);
					contentStream.beginText();
					contentStream.setFont(font, fontSize);
					contentStream.setNonStrokingColor(new Color(0, 0, 0));
					contentStream.moveTextPositionByAmount(X, Y);
					contentStream.drawString(msg);
					contentStream.endText();
					contentStream.close();
				}

			}
			doc.save(outfile);
		} finally {
			if (doc != null) {
				doc.close();
			}
		}
	}

	/**
	 * 
	 * This method prints the specified PDF to specified printer under specified
	 * 
	 * job name
	 * 
	 * 
	 * 
	 * @param filePath
	 *            Path of PDF file
	 * 
	 * @param printerName
	 *            Printer name
	 * 
	 * @param jobName
	 *            Print job name
	 * 
	 * @throws IOException
	 * 
	 * @throws PrinterException
	 */

	public void printPDF(String filePath, String printerName, String jobName)
			throws IOException, PrinterException {
		FileInputStream fileInputStream = new FileInputStream(filePath);
		byte[] pdfContent = new byte[fileInputStream.available()];
		fileInputStream.read(pdfContent, 0, fileInputStream.available());
		ByteBuffer buffer = ByteBuffer.wrap(pdfContent);
		final PDFFile pdfFile = new PDFFile(buffer);

		Printable printable = new Printable() {
			public int print(Graphics graphics, PageFormat pageFormat,
					int pageIndex) throws PrinterException {
				int pagenum = pageIndex + 1;
				if ((pagenum >= 1) && (pagenum <= pdfFile.getNumPages())) {
					Graphics2D graphics2D = (Graphics2D) graphics;
					PDFPage page = pdfFile.getPage(pagenum);
					Rectangle imageArea = new Rectangle((int) pageFormat
							.getImageableX(), (int) pageFormat.getImageableY(),
							(int) pageFormat.getImageableWidth(),
							(int) pageFormat.getImageableHeight());
					graphics2D.translate(0, 0);
					PDFRenderer pdfRenderer = new PDFRenderer(page, graphics2D,
							imageArea, null, null);
					try {
						page.waitForFinish();
						pdfRenderer.run();
					} catch (InterruptedException exception) {
						exception.printStackTrace();
					}
					return PAGE_EXISTS;
				} else {
					return NO_SUCH_PAGE;
				}
			}
		};

		PrinterJob printJob = PrinterJob.getPrinterJob();
		PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
		printJob.setJobName(jobName);
		Book book = new Book();
		book.append(printable, pageFormat, pdfFile.getNumPages());
		printJob.setPageable(book);
		Paper paper = new Paper();
		paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
		pageFormat.setPaper(paper);
		PrintService[] printServices = PrinterJob.lookupPrintServices();
		for (int count = 0; count < printServices.length; ++count) {
			if (printerName.equalsIgnoreCase(printServices[count].getName())) {
				printJob.setPrintService(printServices[count]);
				break;
			}
		}
		printJob.print();
	}
	
	// by butani vijay to return pdf properties and errors if any.
	public String getAllPdfProperties(String filename)
	{
		
		String version="";
		
		boolean bookmarkPresent=false;
		boolean isValidLayout=true;
		boolean isValidBookMarks=true;
		ArrayList<String> bookmarkerrors=new ArrayList<String>();
		String prop_show="";
		String prop_pagelayout="";
		String layout[]=getPageLayoutAndMode(filename).split(":");
		try {
			version=PDFUtilities.checkPDFversion(filename);
			if(layout[0].equals("1"))
			{
				bookmarkPresent=true;
				if(!layout[1].equals("128")){
					isValidLayout=false;
				}
				
				
			}
			else if(layout[0].equals("0")) // no book mark
			{
				
				if(!layout[1].equals("64")){
					isValidLayout=false;
				}
				
			}
			
			if(layout[1].equals("64") || layout[1].equals("65") || layout[1].equals("66") || layout[1].equals("96") || layout[1].equals("72"))
			{	
				prop_show="Page Only";
			}
			else if(layout[1].equals("128") || layout[1].equals("129") || layout[1].equals("130") || layout[1].equals("160") || layout[1].equals("136"))
			{	
				prop_show="Bookmark Panel & Page";
			}
			else if(layout[1].equals("256") || layout[1].equals("257") || layout[1].equals("258") || layout[1].equals("288") || layout[1].equals("264"))
			{	
				prop_show="Page Panel & Page";
			}
			else if(layout[1].equals("2048") || layout[1].equals("2049") || layout[1].equals("2050") || layout[1].equals("2080") || layout[1].equals("2056"))
			{	
				prop_show="Attachment Panel & Page";
			}
			else if(layout[1].equals("1024") || layout[1].equals("1025") || layout[1].equals("1026") || layout[1].equals("1056") || layout[1].equals("1032"))
			{	
				prop_show="Layer Panel & Page";
			}	
			
			if(layout[1].equals("64") || layout[1].equals("128") || layout[1].equals("256") || layout[1].equals("2048") || layout[1].equals("1024"))
			{	
				prop_pagelayout="Default";
			}
			else if(layout[1].equals("65") || layout[1].equals("129") || layout[1].equals("257") || layout[1].equals("2049") || layout[1].equals("1025"))
			{	
				prop_pagelayout="Single Page";
			} 
			else if(layout[1].equals("66") || layout[1].equals("130") || layout[1].equals("258") || layout[1].equals("2050") || layout[1].equals("1026"))
			{	
				prop_pagelayout="Continious";
			} 
			else if(layout[1].equals("96") || layout[1].equals("160") || layout[1].equals("258") || layout[1].equals("2080") || layout[1].equals("1056"))
			{	
				prop_pagelayout="Facing";
			} 
			else if(layout[1].equals("72") || layout[1].equals("136") || layout[1].equals("264") || layout[1].equals("2056") || layout[1].equals("1032"))
			{	
				prop_pagelayout="Continue-Facing";
			} 
			
			
			//check Bookmarks
			if(bookmarkPresent)
			{
			
				isValidBookMarks=checkBookmarksValid(filename);
				
				
			}
			
			if(!isValidBookMarks){
				
				bookmarkerrors=getBookmarksErrors(filename);
			}
			
		    if(bookmarkerrors!=null && bookmarkerrors.size()>0)
			{
				for(int i=0;i<bookmarkerrors.size();i++){
					
					System.out.println(bookmarkerrors.get(i).toString());
				}
			}
			else if(bookmarkPresent)
			{
				System.out.println("0 Bookmarking Error Found");
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String table="";
		table="<table border='1'>";
		table+="<tr>";
		table+="<th>Property</th><th>Value</th>";
		table+="<td>Version</td><td>"+version+"</td>";
		table+="<td>Doc Show</td><td>"+prop_show+"</td>";
		table+="<td>Page Layout</td><td>"+prop_pagelayout+"</td>";
	
		if(!isValidLayout){
			System.out.println("InValid Page property");
			
		}
		
		
		
		
		System.out.println(table);
		
		return "";
	}
	
}