package com.docmgmt.struts.actions.labelandpublish.PDFPublish;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.docmgmt.dto.DTOPdfPublishDtl;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BadPdfFormatException;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfCopy.PageStamp;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;

public class PdfMergeWithTOC {

	// ModuleWiseTOCPdfPath
	public String wsId;
	private final float SRNO_COLUMN_WIDTH = 25f;
	private final float PAGE_NO_COLUMN_WIDTH = 28f;
	private final int COVER_PAGE_TOP_MARGIN = 100;
	private final String POSITION_CENTER = "1";
	private final String POSITION_LEFT = "2";
	private final String POSITION_RIGHT = "3";

	private final String PAGE_NO_STYLE_123 = "1";
	private final String PAGE_NO_STYLE_1_OF_N = "2";

	private final int HEADER_HEIGHT = 65;
	private final int MAX_LEVEL_IN_TOC = 8;
	///private final int MAX_LEVEL_IN_TOC = 9;
	private final String DEFAULT_FONT_NAME = FontFactory.TIMES_ROMAN;
	private Phrase watermarkText;
	private String extraHtmlCode = "";
	private boolean error=false;

	private PdfPCell cell;
	private PdfPTable table;
	
	public String nodename;
	public String nodeDisplayName ;

	ArrayList<PdfPTable> modulewisetables;
	int moduleCounter = -1;
	PdfPTable moduleTable = null;

	ArrayList<DTOWorkSpaceNodeDetail> tree;
	//Vector<DTOWorkSpaceNodeAttrDetail> nodeAttrdtl; 
	public Vector<DTOWorkSpaceNodeAttrDetail> allnodattrdetail;
	String AttrValue;
	int startpagecounter = 0;

	String externalFontPath;

	DocMgmtImpl docmgmtpub = new DocMgmtImpl();
	// public String previousNodeName = "";
	// public String currentNodeName = "";
	// private Vector<DTOWorkSpaceNodeAttribute> prevNodeAttr = new
	// Vector<DTOWorkSpaceNodeAttribute>();
	// private Vector<DTOWorkSpaceNodeAttribute> wsNodeAttr = new
	// Vector<DTOWorkSpaceNodeAttribute>();

	ArrayList<String> moduletocpath = new ArrayList<String>();

	Font fontBoldItalic;
	Font fontFooter;
	Font whiteFont;
	Font smallfont;

	Font tocboldfont;
	Font nodeTitleFont;
	Font tocLinkfont;
	Font tableOfContentFont;

	private Font tocLinkfontBold;

	
	PdfImportedPage page;
	Chunk chunk;
	PdfReader reader;
	PageStamp stamp;
	String productName, productName2;
	String[] allowHeaderPdfs;
	String[] allowFooterPdfs;
	String[] allowShrinkPdfs;

	private String logoPath;
	private Image logoImg = null;
	float logoWidth;

	List<String> headerSettingList;
	List<String> footerSettingList;
	List<String> tocSettingList;
	boolean addHeader, addFooter;
	int numberOfTocPages = 0;

	String destinationPath = "";

	boolean addTOC;
	ByteArrayOutputStream baos = null;

	ArrayList<Integer> allStartPageNumber;
	ArrayList<Integer> modulewiseallStartPageNumber = new ArrayList<Integer>();
	ArrayList<Integer> modulewisetocpagecounter;
	ArrayList<Integer> modulewiseStartPageNumber;
	private Font coverfont;

	private int totalPages;
	public String tocPath = "";
	private boolean addWaterMark;

	private int moduleWisePageNumCounter = 0;
	private ArrayList<Integer> moduleWiseTotalPages;
	private int currentModule = 0;
	private DTOPdfPublishDtl pdfpublishDTO;
	private PropertyInfo propertyInfo = PropertyInfo.getPropInfo();

	private HashMap<String, Object> bookmarkRoot = null;
	private ArrayList<HashMap<String, Object>> outline = null;

	private String publishTempPath = "";
	

	public PdfMergeWithTOC(DTOPdfPublishDtl pdfpublishDTO, String productName,
			String productName2, String[] allowHeaderPdfs,
			String[] allowFooterPdfs, String[] allowShrinkPdfs,
			String logopath, List<String> headerSettingList,
			List<String> footerSettingList, List<String> tocSettingList) {

		this.pdfpublishDTO = pdfpublishDTO;
		this.productName = productName;
		this.productName2 = productName2;
		this.allowHeaderPdfs = allowHeaderPdfs;
		this.allowFooterPdfs = allowFooterPdfs;
		this.allowShrinkPdfs = allowShrinkPdfs;
		this.logoPath = logopath;

		this.headerSettingList = headerSettingList;
		this.footerSettingList = footerSettingList;
		this.tocSettingList = tocSettingList;
		this.addHeader = pdfpublishDTO.getAddHeader() == null ? false : true;
		this.addFooter = pdfpublishDTO.getAddFooter() == null ? false : true;

		this.allStartPageNumber = new ArrayList<Integer>();
		this.modulewisetocpagecounter = new ArrayList<Integer>();
		this.modulewiseStartPageNumber = new ArrayList<Integer>();

		tocPath = propertyInfo.getValue("TOCPath");
		addWaterMark = propertyInfo.getValue("AddWaterMarkInMergedPdf")
				.equalsIgnoreCase("yes") ? true : false;
		externalFontPath = this.getClass().getResource("fonts").getPath();

		initFonts();

	}

	private void initFonts() {
		// TODO Auto-generated method stub
		fontBoldItalic = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14,
				Font.BOLD, BaseColor.BLACK);
		fontFooter = FontFactory.getFont("Times-Roman", 10, Font.BOLDITALIC,
				BaseColor.GRAY);
		whiteFont = FontFactory.getFont("Times-Roman", 3, Font.BOLDITALIC,
				BaseColor.WHITE);
		smallfont = FontFactory.getFont("Times-Roman", 12, Font.BOLDITALIC,
				BaseColor.WHITE);

		tocboldfont = FontFactory.getFont(FontFactory.TIMES_ROMAN, Integer
				.parseInt(pdfpublishDTO.getTocFontSize()), Font.BOLD,
				BaseColor.BLACK);
		nodeTitleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14,
				Font.BOLD, BaseColor.BLACK);
		tableOfContentFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16,
				Font.BOLD, BaseColor.BLACK);

		tocLinkfont = FontFactory.getFont(FontFactory.TIMES_ROMAN, Integer
				.parseInt(pdfpublishDTO.getTocFontSize()), Font.NORMAL,
				BaseColor.BLUE);

		tocLinkfontBold = FontFactory.getFont(FontFactory.TIMES_ROMAN, Integer
				.parseInt(pdfpublishDTO.getTocFontSize()), Font.BOLD,
				BaseColor.BLUE);
		coverfont = registerExternalFont(pdfpublishDTO.getCoverpage_fontname()
				.equals("-1") ? DEFAULT_FONT_NAME : pdfpublishDTO
				.getCoverpage_fontname());
		coverfont.setSize(Integer.parseInt(pdfpublishDTO
				.getCoverpage_fontsize()));
		coverfont.setStyle(Integer.parseInt(pdfpublishDTO
				.getCoverpage_fontstyle()) == -1 ? 0 : Integer
				.parseInt(pdfpublishDTO.getCoverpage_fontstyle()));

		if (addWaterMark)
			watermarkText = new Phrase("SAMPLE FILE",
					new Font(FontFamily.HELVETICA, 60, Font.NORMAL,
							BaseColor.LIGHT_GRAY));
	}

	public String createPdf(String destinationPath,
			Map<String, PdfReader> filesToMerge, String SRCINDEX,
			boolean addTOC, boolean Paging, boolean addTitlePage,
			boolean addBookmark, ArrayList<String> nodeDisplayNameList,
			int totalPages, int nodes[], String workSpaceId,
			ArrayList<DTOWorkSpaceNodeDetail> allNodesDtl,
			ArrayList<DTOWorkSpaceNodeDetail> tocNodeDetails,
			ArrayList<Integer> moduleWiseTotalPages) throws IOException,
			DocumentException, InvocationTargetException {
			wsId = workSpaceId;
		// baos = new ByteArrayOutputStream();
		// Map<Integer, String> toc = new TreeMap<Integer, String>();
		Document document = new Document();
		
		PdfReader pdfReader;
	    PdfStamper pdfStamper = null;
		
		// writer.addViewerPreference(PdfName.PRINTSCALING, PdfName.NONE);

		// PdfCopy copy = new PdfCopy(document, baos);
		Document.plainRandomAccess = true;
		
		//document.setPageSize(new Rectangle(30, 30));
		publishTempPath = propertyInfo.getValue("PdfPublishTempPath") + "/"
				+ String.valueOf(System.currentTimeMillis()) + ".pdf";
		PdfCopy copy = new PdfCopy(document, new FileOutputStream(
				publishTempPath));

		// copy.setRotateContents(false);
		this.moduleWiseTotalPages = moduleWiseTotalPages;
		this.addTOC = addTOC;
		this.destinationPath = destinationPath;
		this.totalPages = totalPages;
		document.open();
		int numberOfPages;
		int pageNo = 0;
		int pdfCounter = 0;
		if (headerSettingList != null
				&& headerSettingList.contains("h_addLogo") && logoPath != null) {
			logoImg = Image.getInstance(logoPath);
			// LogoImg.scalePercent(100f, 100f);
			// logoWidth=LogoImg.getWidth();
			logoImg.scaleToFit(100f, 35f);
			// LogoImg.scaleAbsolute(100f, 35f);
		}
		
		// LogoImg.setCompressionLevel(3);
		// LogoImg.scalePercent(60);

		int pdfNodeCounter = 0;
		boolean addHeaderInPDF = true;
		boolean addFooterInPDF = true;
		boolean allowShrinkInPDF = true;
		List<String> listHeaders = null;
		List<String> listFooters = null;
		List<String> listShrinks = null;
		if (allowHeaderPdfs != null) {
			listHeaders = Arrays.<String> asList(allowHeaderPdfs);
			// System.out.println(listHeaders.toString());
		}
		if (allowFooterPdfs != null) {
			listFooters = Arrays.<String> asList(allowFooterPdfs);
		}
		if (allowShrinkPdfs != null) {
			listShrinks = Arrays.<String> asList(allowShrinkPdfs);
		}

		extraHtmlCode="<font size='2px' color='red'>Unable to generate dossier.....Please correct below file(s)</font><table width='100%' style='border:1px solid red;border-collapse: collapse;padding:5px' cellpadding='5'>";
		extraHtmlCode+="<tr style='border:1px solid red'><th>Node Name</th><th>File Name</th><th>Error Message</th></tr>";
		
		for (Map.Entry<String, PdfReader> entry : filesToMerge.entrySet()) {
			try {
				numberOfPages = entry.getValue().getNumberOfPages();
				addHeaderInPDF = true;
				addFooterInPDF = true;
				allowShrinkInPDF = false;
				// Field f = entry.getValue().getClass().getDeclaredField(
				// "encrypted");
				// f.setAccessible(true);
				// f.set(entry.getValue(), false);
				// toc.put(pageNo + 1, entry.getKey());
				pdfCounter++;
				if (!entry.getKey().startsWith("nodeTitlePage")
						&& !entry.getKey().startsWith("FileTitlePage")) {
					addHeaderInPDF = (allowHeaderPdfs != null && listHeaders
							.contains("" + nodes[pdfNodeCounter])) ? true
							: false;
					addFooterInPDF = (allowFooterPdfs != null && listFooters
							.contains("" + nodes[pdfNodeCounter])) ? true
							: false;
					allowShrinkInPDF = (allowShrinkPdfs != null && listShrinks
							.contains("" + nodes[pdfNodeCounter])) ? true
							: false;
					pdfNodeCounter++;
				}
				
				
				
				if(entry.getKey().contains("file")){
					int nodeIds;
					String ndName,nodeDisplayName,attrName;
					ndName=entry.getKey();
					if(entry.getKey().contains("{")){
						pageNo++;
						moduleWisePageNumCounter++;
					int startingIndex = entry.getKey().indexOf(" ");
					int closingIndex = entry.getKey().indexOf("}");
					nodeDisplayName = ndName.substring(startingIndex+1 , closingIndex+1);
					System.out.println(nodeDisplayName);
					
					int start=nodeDisplayName.indexOf("nodeId");
					int end=nodeDisplayName.indexOf("]");
					String test=nodeDisplayName.substring(start, end);
					System.out.println(test);
					String[] ndId = test.split("=");
					//String part1 = parts[0]; // 004
					String tempNdId = ndId[1];
					System.out.println(tempNdId);
					int nodeId = Integer.parseInt(tempNdId);
					System.out.println(nodeId);
					
					
					//nodeIds=docmgmtpub.getNodeMaxIdFromNodeName(workSpaceId,nodeDisplayName);
					
					Vector<DTOWorkSpaceNodeAttribute> AttrDtl;
					AttrDtl=docmgmtpub.getAttrDtl(workSpaceId,nodeId);
					
					String finalattrName=ndName;
					 startingIndex = finalattrName.indexOf("{");
						closingIndex = finalattrName.indexOf("}");
						attrName = finalattrName.substring(startingIndex+1 , closingIndex);
						System.out.println(attrName+"\n");
					
						String attibute[]=attrName.split(",");
					
						for (String a : attibute) 
			            System.out.println(a); 
					/*Vector<DTOWorkSpaceNodeDetail> getNodeDetail;
					getNodeDetail=docmgmtpub.getNodeDetail(workSpaceId, nodeId);*/
					

					try{
					
						/* Document d1 = new Document();
						 PdfWriter.getInstance(d1, new FileOutputStream("F:\\1-Mahavir\\PDFs\\iTextHelloWorld.pdf"));
						*/
						 
						
						 PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
							String workspaceFolder = propertyInfo.getValue("TempFilePath");
						  workspaceFolder=workspaceFolder+"//temp.pdf";
						
						 String pdfFilePath = workspaceFolder;
					      OutputStream fos = new FileOutputStream(new File(pdfFilePath));
					      Document d1 = new Document();
					      PdfWriter pdfWriter = PdfWriter.getInstance(d1, fos);
					      Rectangle rectangle = new Rectangle(30, 30, 550, 800);
					      pdfWriter.setBoxSize("rectangle", rectangle);
							 
					      String nodeName=null;
					      startingIndex = ndName.indexOf(" ");
							closingIndex = ndName.indexOf("{");
							Vector<DTOWorkSpaceNodeDetail> dto=new Vector<DTOWorkSpaceNodeDetail>(); 
							dto=docmgmtpub.getNodeDetail(workSpaceId,nodeId); 
							nodeName = dto.get(0).getFolderName();
							//nodeName=nodeName.split("]")[1];
					      
					      
							d1.open();
							Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
							
							PdfPTable table = new PdfPTable(2);
							table.setWidthPercentage(100);
							System.out.println(AttrDtl.size()+"\n");
							PdfPCell cell = new PdfPCell(new Phrase(Element.ALIGN_CENTER,nodeName));
							cell.setColspan(2);
							
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							table.addCell(cell);
							

							PdfPCell cell1 = new PdfPCell(new Phrase(Element.ALIGN_CENTER,"File Attributes"));
							cell1.setColspan(2);
							
							cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell1.setVerticalAlignment(Element.ALIGN_CENTER);
							table.addCell(cell1);
							
							
							PdfPCell An = new PdfPCell(new Phrase("Attribute Name"));
				            PdfPCell Av = new PdfPCell(new Phrase("Attribute Value"));
				            Av.setHorizontalAlignment(Element.ALIGN_CENTER);
				            An.setHorizontalAlignment(Element.ALIGN_CENTER);
				            table.addCell(An);
				            table.addCell(Av);
							
				          
				            table.completeRow();
							
							for(int i=0;i<AttrDtl.size();i++){
								System.out.println("Name : "+AttrDtl.get(i).getAttrName()+"     Value : " + AttrDtl.get(i).getAttrValue());
								
								PdfPCell cell5 = new PdfPCell(new Phrase(AttrDtl.get(i).getAttrName()));
					            PdfPCell cell6 = new PdfPCell(new Phrase(AttrDtl.get(i).getAttrValue()));
					            table.addCell(cell5);
					            table.addCell(cell6);
					            table.completeRow();
								
							}
				            
				            Rectangle rect = pdfWriter.getBoxSize("rectangle");
				            
				            // BOTTOM MEDIUM
				            ColumnText.showTextAligned(pdfWriter.getDirectContent(),
				                     Element.ALIGN_CENTER, new Phrase("                   "+pageNo),
				                     rect.getRight() / 2, rect.getBottom(), 0);
				            
				            d1.add(table);
							d1.close();
							d1.close();
						
							/*Document d2 = new Document();
							  PdfWriter.getInstance(d1, new FileOutputStream("F:\\1-Mahavir\\PDFs\\iTextHelloWorld.pdf"));
							  d2.open();*/
						pdfReader = new PdfReader(workspaceFolder);	
						 
					    pdfStamper = new PdfStamper(pdfReader,new FileOutputStream(destinationPath,true));
					    
						  int pages = pdfReader.getNumberOfPages();
						  PdfImportedPage importedPage = copy.getImportedPage(pdfReader, 1);
					      copy.addPage(importedPage);
					      copy.freeReader(pdfReader);
					      pdfStamper.close();	
					      pdfReader.close();
					    
					    File myObj = new File(workspaceFolder); 
					    if (myObj.delete()) { 
					      System.out.println("Deleted the file: " + myObj.getName());
					    } else {
					      System.out.println("Failed to delete the file.");
					    } 
					   // d2.close();
					   // PdfImportedPage iPage = copy.getImportedPage(pdfReader, 1);
					  
					    //copy.addPage(copy.getImportedPage(pdfReader, 1));
					}
					
					catch (Exception e) {
					    e.printStackTrace();
					}
					
				}
						}
				for (int currPageNum = 0; currPageNum < numberOfPages;) {
					pageNo++;
					moduleWisePageNumCounter++;
					if (tocNodeDetails.get(pdfCounter - 1).getNodeLevel() == 1) {
						currentModule++;
						//moduleWisePageNumCounter = 1;
					}
					page = null;
					page = copy
							.getImportedPage(entry.getValue(), ++currPageNum);
					// Rectangle rect =
					// entry.getValue().getPageSizeWithRotation(
					// currPageNum);
					// System.out.println("Rect-"+rect.getTop());
					//copy.setPageSize(PageSize.A4);
					stamp = copy.createPageStamp(page);
					if (allowShrinkInPDF)
						shrinkPdf(entry.getValue(), stamp, currPageNum);

					PdfContentByte overContent = stamp.getOverContent();
					int rotation = entry.getValue()
							.getPageRotation(currPageNum);
					if (addHeader && headerSettingList != null
							&& addHeaderInPDF) {
						addheaderInPdf(stamp, page, rotation, tocNodeDetails,
								pageNo, totalPages, pdfCounter, overContent);
					}
					if (addFooter && footerSettingList != null
							&& addFooterInPDF) {
						if (tocSettingList != null
								&& tocSettingList
										.contains("toc_modulewisenumber"))
							addFooterInPdf(stamp, page, rotation,
									moduleWisePageNumCounter, totalPages,
									pdfCounter, overContent, false);
						else
							addFooterInPdf(stamp, page, rotation, pageNo,
									totalPages, pdfCounter, overContent, false);
					}
					if (addWaterMark)
						addWaterMark(stamp.getUnderContent(), page.getWidth(),
								page.getHeight());

					if (addTitlePage
							&& entry.getKey().contains("nodeTitlePage"))
						addNodeTitlePageDetails(pdfCounter, overContent,
								tocNodeDetails, page, allNodesDtl);
					else if (entry.getKey().contains("FileTitlePage"))
						addFileTitlePageDetails(pdfCounter, tocNodeDetails,
								page, overContent);
					chunk = new Chunk(String.format("%d", pageNo));
					chunk.setFont(whiteFont);
					if (currPageNum == 1) {
						if (addBookmark
								&& (entry.getKey().contains("nodeTitlePage"))) {
							// String bookmarkTitle = entry.getKey().substring(
							// entry.getKey().indexOf(')') + 1);
							// Set font and style
							// Chapter chapter = new Chapter(new Paragraph(
							// bookmarkTitle), pageNo);
							//							
							//							
							// chapter.setNumberDepth(0);
							// chapter.setIndentationLeft(currPageNum);
							// document.add(chapter);
						}
						chunk.setLocalDestination("p" + pageNo);
						if (entry.getKey().contains("nodeTitlePage")) {
							allStartPageNumber.add(pageNo);
							modulewiseallStartPageNumber
									.add(moduleWisePageNumCounter);
						}
					}
					ColumnText
							.showTextAligned(overContent, Element.ALIGN_RIGHT,
									new Phrase(chunk), 559, 810, 0);
					stamp.alterContents();
					
					copy.addPage(page);

				}
				copy.freeReader(entry.getValue());
				entry.getValue().close();

				// System.gc();
				// entry.getValue().close();
			} catch (Exception e) {
				e.printStackTrace();
				error=true;
				addErrorMessage(tocNodeDetails.get(pdfCounter-1).getNodeDisplayName(),tocNodeDetails.get(pdfCounter-1).getFolderName(), e.getMessage());
				continue;
			}
			
			
			

		}
		
		if(error){
			extraHtmlCode+="</table>";
			
			System.out.println("Errorcode--"+extraHtmlCode);
			return "-20#" + extraHtmlCode;
		}

		if (addBookmark) {
			bookmarkRoot = new HashMap<String, Object>();
			outline = new ArrayList<HashMap<String, Object>>();

			tree = allNodesDtl;
			// calendar.put("Title", "Calendar");
			outline.add(bookmarkRoot);
			startpagecounter = 0;
			processBookmarkTree(tree.get(0), 0, bookmarkRoot);
			startpagecounter = 0;
			copy.setOutlines(outline);

			copy.setViewerPreferences(PdfWriter.PageModeUseOutlines);
		}

		PdfReader reader = null;
		if (addTOC) {
			modulewisetables = new ArrayList<PdfPTable>();
			generateTOC(reader, copy, allNodesDtl);

			if (tocSettingList != null
					&& tocSettingList.contains("toc_modulewisetoc")) {
				if (moduleTable != null) {
					modulewisetables.add(moduleTable);
				}
				for (int a = 0; a < modulewisetables.size(); a++) {
					Document document1 = new Document(PageSize.A4);
					// step 2
					PdfWriter writer = null;
					try {

						String path = tocPath + "/module" + a + ".pdf";
						moduletocpath.add(path);
						writer = PdfWriter.getInstance(document1,
								new FileOutputStream(path));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// step 3
					document1.setMargins(20, 20, 110, 60);
					document1.open();
					document1.add(modulewisetables.get(a));
					document1.close();
				}
				addModuleTOC(copy);
			}
		}
		try {
			document.close();
			for (PdfReader r : filesToMerge.values()) {
				r.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// System.out.println("Buffer Size->" + baos.size());

			// reader = new PdfReader(baos.toByteArray());

			// reader = new PdfReader("D://big.pdf");

			reader = new PdfReader(
					new RandomAccessFileOrArray(publishTempPath), null);

			if (addTOC) {
				if (tocSettingList != null
						&& tocSettingList.contains("toc_modulewisetoc"))
					shiftModuleTocPages(reader);
				numberOfPages = reader.getNumberOfPages();

				reader.selectPages(String.format("%d-%d, 1-%d", numberOfPages
						- numberOfTocPages + 1, numberOfPages, numberOfPages
						- numberOfTocPages));

				// for (int i = 0; i < indexPageCounter; i++)
				// reader.selectPages(String.format("%d, 1-%d", n, n - 1));
				// reader.selectPages(String.format("%d, 1-%d", n, n-1));
			}

			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					destinationPath));

			// stamper.setOutlines(outline);
			//pdfpublishDTO.setAddCoverPages("yes");
			if (pdfpublishDTO.getAddCoverPages() != null
					&& pdfpublishDTO.getAddCoverPages().equalsIgnoreCase("yes")) {

				for (int coverPageCounter = 2; coverPageCounter >= 0; coverPageCounter--) {
					stamper.insertPage(1, PageSize.A4);
					PdfContentByte canvas = stamper.getOverContent(1);
					generateCoverPage(coverPageCounter + 1, canvas);
				}
			}

			stamper.close();
			reader.close();
		} catch (Exception e) {
			extraHtmlCode=e.getMessage();
			e.printStackTrace();
			return "-20#<font color='red' size='2px'>Unable to generate Dossier...."+e.getMessage();
			
		}
		freeMemory();
		return pdfCounter + "#Successfully Merged";

	}
	
	public void addErrorMessage(String nodeName,String fileName,String errorMessage){
		
		extraHtmlCode+="<tr style='border:1px solid red'>";
		extraHtmlCode+="<td style='border:1px solid red'>"+nodeName+"</td>";
		extraHtmlCode+="<td style='border:1px solid red'>"+fileName+"</td>";
		extraHtmlCode+="<td style='border:1px solid red'>"+errorMessage+"</td>";
		extraHtmlCode+="</tr>";
	}

	private void freeMemory() {
		fontBoldItalic = null;
		fontFooter = null;
		whiteFont = null;
		smallfont = null;

		tocboldfont = null;
		nodeTitleFont = null;
		tableOfContentFont = null;

		tocLinkfont = null;

		tocLinkfontBold = null;
		coverfont = null;
		
		headerSettingList = null;
		footerSettingList = null;
		tocSettingList = null;
		tree = null;
		modulewisetables = null;
		table = null;
		cell = null;
		bookmarkRoot = null;
		externalFontPath = null;
		logoImg = null;
		moduletocpath = null;
		moduleTable = null;
		modulewiseallStartPageNumber = null;
		moduleWiseTotalPages = null;

		System.gc();

	}

	private Font registerExternalFont(String fontName) {
		// String alias=fontName.replace(".ttf", "");
		// alias=alias.replace(".TTF", "");
		Font customFont;
		if (fontName.equalsIgnoreCase("Times")) {
			customFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		} else {
			FontFactory.register(externalFontPath + "/" + fontName,
					"custom_font");
			customFont = FontFactory.getFont("custom_font");
		}

		return customFont;

	}

	private void generateCoverPage(int number, PdfContentByte canvas) {

		// String alias=registerExternalFont(coverpage_fontname);

		// coverfont = FontFactory.getFont(alias, Integer
		// .parseInt(coverpage_fontsize), coverpage_fontstyle,
		// BaseColor.BLACK);
		// System.out.println("CoverPageFontName->"+coverpage_fontname);

		PdfPTable table;
		float tablewidth = 500f;
		table = new PdfPTable(1);
		table.setTotalWidth(tablewidth);
		PdfPCell cell = new PdfPCell(new Phrase(pdfpublishDTO
				.getCoverpage_productname(), coverfont));
		cell.setPadding(20f);
		cell.setLeading(1f, 1.4f);

		cell.setBorderWidthLeft(3f);
		cell.setBorderWidthTop(3.5f);
		cell.setBorderWidthBottom(4.5f);
		cell.setBorderWidthRight(5f);

		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		cell.setUseDescender(true);

		table.addCell(cell);
		table.completeRow();
		// write the table to an absolute position
		float height = table.getRowHeight(0);

		table.writeSelectedRows(0, -1, (PageSize.A4.getWidth() / 2)
				- (tablewidth / 2), PageSize.A4.getHeight()
				- COVER_PAGE_TOP_MARGIN, canvas);

		if (number == 1) {
			Font normalCoverFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,
					10, Font.BOLDITALIC, BaseColor.BLACK);
			table = new PdfPTable(1);
			table.setTotalWidth(tablewidth);
			// JBCPL
//			 cell = new PdfPCell(
//			 new Phrase(
//			 "All and any information contained in this document is to be regarded as a trade secret as it contains unpublished details and results of private research proprietary to J.B. Chemicals and Pharmaceuticals Pvt Ltd, the disclosure of which to its competitors could be disadvantageous.",
//			 normalCoverFont));
			
			// Nirma
			/*cell = new PdfPCell(
					new Phrase(
							"All and any information contained in this document is to be regarded as a trade secret as it contains unpublished details and results of private research proprietary to NIRMA LMITED(Healthcare Division), the disclosure of which to its competitors could be disadvantageous.",
							normalCoverFont));

			cell.setPadding(20f);
			cell.setLeading(3f, 1.4f);

			cell.setBorder(0);
			cell.setBorderWidth(0);

			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

			cell.setUseDescender(true);

			table.addCell(cell);
			*/
			table.completeRow();
			// write the table to an absolute position
			height = table.getRowHeight(0);

			table.writeSelectedRows(0, -1, 40, height + 100, canvas);

		} else if (number == 2) {

			Font normalCoverFont;
			normalCoverFont = registerExternalFont(pdfpublishDTO
					.getCoverpage_subbyfontname());
			normalCoverFont.setSize(Integer.parseInt(pdfpublishDTO
					.getCoverpage_fontsizeby()));
			normalCoverFont.setStyle(Integer.parseInt(pdfpublishDTO
					.getCoverpage_subbyfontstyle()));

			// normalCoverFont = FontFactory.getFont("Times New Roman",
			// Integer.parseInt(coverpage_fontsizeby), Font.BOLD,
			// BaseColor.BLACK);

			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(
					new Chunk("SUBMITTED BY :", normalCoverFont)), 50,
					PageSize.A4.getHeight() - height - 100 - 50, 0);

			float position = PageSize.A4.getHeight() - height - 100 - 45;
			table = new PdfPTable(1);
			table.setTotalWidth(tablewidth);
			cell = new PdfPCell(new Phrase(pdfpublishDTO
					.getCoverpage_submittedby(), normalCoverFont));

			cell.setPadding(10f);
			cell.setLeading(1f, 1.2f);

			cell.setBorder(0);
			cell.setBorderWidth(0);

			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);

			cell.setUseDescender(true);
			table.addCell(cell);
			table.completeRow();

			table.writeSelectedRows(0, -1, 100, position, canvas);

		} else if (number == 3) {

			Font normalCoverFont;
			normalCoverFont = registerExternalFont(pdfpublishDTO
					.getCoverpage_subtofontname());
			normalCoverFont.setSize(Integer.parseInt(pdfpublishDTO
					.getCoverpage_fontsizeto()));
			normalCoverFont.setStyle(Integer.parseInt(pdfpublishDTO
					.getCoverpage_subtofontstyle()));

			// normalCoverFont = FontFactory.getFont("Times New Roman",
			// Integer.parseInt(coverpage_fontsizeto), Font.BOLD,
			// BaseColor.BLACK);

			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, new Phrase(
					new Chunk("SUBMITTED TO :", normalCoverFont)), 50,
					PageSize.A4.getHeight() - height - 100 - 50, 0);

			float position = PageSize.A4.getHeight() - height - 100 - 45;
			table = new PdfPTable(1);
			table.setTotalWidth(tablewidth);
			cell = new PdfPCell(new Phrase(pdfpublishDTO
					.getCoverpage_submittedto(), normalCoverFont));

			cell.setPadding(10f);
			cell.setLeading(1f, 1.2f);

			cell.setBorder(0);
			cell.setBorderWidth(0);

			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);

			cell.setUseDescender(true);
			table.addCell(cell);
			table.completeRow();

			table.writeSelectedRows(0, -1, 100, position, canvas);
		}
	}

	private void shiftModuleTocPages(PdfReader reader2) {
		// TODO Auto-generated method stub
		try {

			int modulestartpagecounter = modulewiseStartPageNumber.size() - 1;
			int numberOfPages = reader2.getNumberOfPages();
			for (int t = modulewisetocpagecounter.size() - 1; t >= 0; t--) {
				if (modulewiseStartPageNumber.get(modulestartpagecounter) != 10000)// Temporary
				// Condition
				{

					reader2.selectPages(String.format("1-%d,%d-%d,%d-%d",
							modulewiseStartPageNumber
									.get(modulestartpagecounter), numberOfPages
									- modulewisetocpagecounter.get(t) + 1,
							numberOfPages, modulewiseStartPageNumber
									.get(modulestartpagecounter),
							numberOfPages,

							modulewiseStartPageNumber
									.get(modulestartpagecounter) + 1,
							(numberOfPages - modulewisetocpagecounter.get(t))));

					// System.out.println("==>"
					// + String.format("1-%d,%d-%d,%d-%d",
					// modulewiseStartPageNumber
					// .get(modulestartpagecounter),
					// numberOfPages
					// - modulewisetocpagecounter.get(t)
					// + 1, numberOfPages,
					// modulewiseStartPageNumber
					// .get(modulestartpagecounter),
					// numberOfPages,
					//
					// modulewiseStartPageNumber
					// .get(modulestartpagecounter) + 1,
					// (numberOfPages - modulewisetocpagecounter
					// .get(t))));

				} else {
					reader2.selectPages(String
							.format("%d-%d, 1-%d", numberOfPages
									- modulewisetocpagecounter.get(t) + 1,
									numberOfPages, numberOfPages
											- modulewisetocpagecounter.get(t)));

				}

				modulestartpagecounter--;
				// reader2.selectPages(String.format("%d-%d, 1-%d",
				// numberOfPages
				// - modulewisetocpagecounter.get(t) + 1, numberOfPages,
				// numberOfPages
				// - modulewisetocpagecounter.get(t) + 1));

				// for (int i = 0; i < indexPageCounter; i++)
				// reader.selectPages(String.format("%d, 1-%d", n, n - 1));
				// reader.selectPages(String.format("%d, 1-%d", n, n-1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void addModuleTOC(PdfCopy copy) {
		PdfReader reader1 = null;
		for (int mCounter = 0; mCounter < moduletocpath.size(); mCounter++) {
			try {
				reader1 = new PdfReader(moduletocpath.get(mCounter));
				int tocPages = 0;
				for (; tocPages < reader1.getNumberOfPages();) {
					page = copy.getImportedPage(reader1, ++tocPages);

					if (addHeader || addFooter || tocPages == 1 || addWaterMark) {
						stamp = copy.createPageStamp(page);
					}

					if (addWaterMark)
						addWaterMark(stamp.getUnderContent(), page.getWidth(),
								page.getHeight());

					if (addHeader) {
						addheaderInPdf(stamp, page, 0, null, 1, 1, 1, stamp
								.getOverContent());
						stamp.alterContents();
					}
					if (addFooter) {
						addFooterInPdf(stamp, page, 0, 0, 0, 0, stamp
								.getOverContent(), true);
						stamp.alterContents();

					}

					if (tocPages == 1) {
						chunk = new Chunk("TABLE OF CONTENT");

						// System.out.println("Table Of Content");
						chunk.setFont(tableOfContentFont);
						ColumnText.showTextAligned(stamp.getOverContent(),
								Element.ALIGN_CENTER, new Phrase(chunk), page
										.getWidth() / 2,
								page.getHeight() - 100, 0);
						stamp.alterContents();
					}
					copy.addPage(page);

				}

				modulewisetocpagecounter.add(tocPages);
			} catch (BadPdfFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (Exception e) {

			}
			reader1.close();
		}

	}

	private int generateTOC(PdfReader reader, PdfCopy copy,
			ArrayList<DTOWorkSpaceNodeDetail> allNodesDtl) {
		tree = allNodesDtl;
		currentModule = 0;
		createTable();
		try {
			reader = new PdfReader(tocPath + "/toc.pdf");

			for (; numberOfTocPages < reader.getNumberOfPages();) {
				page = copy.getImportedPage(reader, ++numberOfTocPages);
				if (addHeader || addFooter || numberOfTocPages == 1
						|| addWaterMark) {
					stamp = copy.createPageStamp(page);
				}
				if (addWaterMark)
					addWaterMark(stamp.getUnderContent(), page.getWidth(), page
							.getHeight());

				if (addHeader) {
					addheaderInPdf(stamp, page, 0, null, 1, 1, 1, stamp
							.getOverContent());
					stamp.alterContents();
				}
				if (addFooter) {
					addFooterInPdf(stamp, page, 0, 0, 0, 0, stamp
							.getOverContent(), true);
					stamp.alterContents();

				}
				if (numberOfTocPages == 1) {
					chunk = new Chunk("TABLE OF CONTENT");
					chunk.setFont(tableOfContentFont);
					// chunk.setFont(fontBoldItalic);
					ColumnText.showTextAligned(stamp.getOverContent(),
							Element.ALIGN_CENTER, new Phrase(chunk), page
									.getWidth() / 2, page.getHeight() - 100, 0);
					stamp.alterContents();
				}
				copy.addPage(page);
			}

		} catch (BadPdfFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {

		}
		reader.close();
		return numberOfTocPages;
	}

	private PdfPTable initTOCTable() {
		PdfPTable initTable = new PdfPTable(MAX_LEVEL_IN_TOC);

		initTable.setWidthPercentage(90f);
		initTable.getDefaultCell().setUseAscender(true);
		initTable.getDefaultCell().setUseDescender(true);
		initTable.getDefaultCell().setBorderWidth(1);

		initTable.setKeepTogether(true);
		initTable.setSplitRows(true);

		float[] columnWidths = new float[MAX_LEVEL_IN_TOC];
		columnWidths[0] = SRNO_COLUMN_WIDTH; // Sr No
		for (int i = 1; i < MAX_LEVEL_IN_TOC - 2; i++) {
			columnWidths[i] = 18f;

		}
		columnWidths[MAX_LEVEL_IN_TOC - 2] = 60;
		if (tocSettingList != null
				&& tocSettingList.contains("toc_addPageNumber")
				&& pdfpublishDTO.getTocPageNumberStyle().equals(
						PAGE_NO_STYLE_123)) {
			columnWidths[MAX_LEVEL_IN_TOC - 1] = PAGE_NO_COLUMN_WIDTH;
		} else
			columnWidths[MAX_LEVEL_IN_TOC - 1] = PAGE_NO_COLUMN_WIDTH + 7;
			//columnWidths[MAX_LEVEL_IN_TOC - 1] = PAGE_NO_COLUMN_WIDTH + 8;
		try {
			initTable.setWidths(columnWidths);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// table.setSpacingBefore(1000);
			initTable.setHeaderRows(2);
			initTable.setFooterRows(1);
			initTable.setSplitRows(true);

			cell = new PdfPCell(new Phrase("SR.\nNO", tocboldfont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			initTable.addCell(cell);

			cell = new PdfPCell(new Phrase("SUBJECT", tocboldfont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			if (tocSettingList != null
					&& tocSettingList.contains("toc_addPageNumber"))
				cell.setColspan(MAX_LEVEL_IN_TOC - 2);
			else
				cell.setColspan(MAX_LEVEL_IN_TOC - 1);

			initTable.addCell(cell);
			if (tocSettingList != null
					&& tocSettingList.contains("toc_addPageNumber")) {
				cell = new PdfPCell(new Phrase("PAGE NO", tocboldfont));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				initTable.addCell(cell);
			}
			cell = new PdfPCell(new Phrase(""));
			cell.setColspan(MAX_LEVEL_IN_TOC);
			cell.setBorder(Rectangle.TOP);

			initTable.addCell(cell);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return initTable;
	}

	private void createTable() {
		// TODO Auto-generated method stub
		Document document = new Document();
		// step 2
		try {
			PdfWriter.getInstance(document, new FileOutputStream(tocPath
					+ "/toc.pdf"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// step 3

		document.setMargins(20, 20, 110, 60);

		document.open();

		table = new PdfPTable(MAX_LEVEL_IN_TOC);

		table.setWidthPercentage(90f);
		table.getDefaultCell().setUseAscender(true);
		table.getDefaultCell().setUseDescender(true);
		table.getDefaultCell().setBorderWidth(1);

		table.setKeepTogether(true);
		table.setSplitRows(true);

		float[] columnWidths = new float[MAX_LEVEL_IN_TOC];
		columnWidths[0] = SRNO_COLUMN_WIDTH; // Sr No
		for (int i = 1; i < MAX_LEVEL_IN_TOC - 2; i++) {
			columnWidths[i] = 18f;

		}
		columnWidths[MAX_LEVEL_IN_TOC - 2] = 60;
		if (tocSettingList != null
				&& tocSettingList.contains("toc_addPageNumber")
				&& pdfpublishDTO.getTocPageNumberStyle().equals(
						PAGE_NO_STYLE_123)) {
			columnWidths[MAX_LEVEL_IN_TOC - 1] = PAGE_NO_COLUMN_WIDTH;
		} else
			columnWidths[MAX_LEVEL_IN_TOC - 1] = PAGE_NO_COLUMN_WIDTH + 7;
			//columnWidths[MAX_LEVEL_IN_TOC - 1] = PAGE_NO_COLUMN_WIDTH + 8;
		try {
			table.setWidths(columnWidths);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// table.setSpacingBefore(1000);
			table.setHeaderRows(2);
			table.setFooterRows(1);
			table.setSplitRows(true);

			cell = new PdfPCell(new Phrase("SR.\nNO", tocboldfont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("SUBJECT", tocboldfont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			if (tocSettingList != null
					&& tocSettingList.contains("toc_addPageNumber"))
				cell.setColspan(MAX_LEVEL_IN_TOC - 2);
			else
				cell.setColspan(MAX_LEVEL_IN_TOC - 1);
			table.addCell(cell);

			if (tocSettingList != null
					&& tocSettingList.contains("toc_addPageNumber")) {
				cell = new PdfPCell(new Phrase("PAGE NO", tocboldfont));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}

			cell = new PdfPCell(new Phrase(""));
			cell.setColspan(MAX_LEVEL_IN_TOC);
			cell.setBorder(Rectangle.TOP);

			table.addCell(cell);

			processTOCTree(tree.get(0), 0);

			// processBookmarkTree(tree.get(0), 0);

			document.add(table);

			document.close();
		} catch (DocumentException e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		}

	}

	private void processBookmarkTree(DTOWorkSpaceNodeDetail dto, int level,
			HashMap<String, Object> root) throws DocumentException {
		if (level == 0)
			root.put("Title", dto.getNodeDisplayName());

		ArrayList<DTOWorkSpaceNodeDetail> childs = selectChild(dto.getNodeId());
		ArrayList<HashMap<String, Object>> days = new ArrayList<HashMap<String, Object>>();
		root.put("Kids", days);
		for (DTOWorkSpaceNodeDetail obj : childs) {
			HashMap<String, Object> day = new HashMap<String, Object>();
			day.put("Title", obj.getNodeDisplayName());
			day.put("Action", "GoTo");
			day.put("Page", String.format("%d FitH 500 0", allStartPageNumber
					.get(startpagecounter++)));

			days.add(day);
			// document.add(new Paragraph(obj.getNodeDisplayName()));

			processBookmarkTree(obj, level + 1, day);

		}

		// calendar.put("Kids", days);
		// document.newPage();

	}

	private void processTOCTree(DTOWorkSpaceNodeDetail dto, int level)
			throws DocumentException {
		// Test_Kenya_toc
		ArrayList<DTOWorkSpaceNodeDetail> childs = selectChild(dto.getNodeId());
				//String nodeDisplayName = dto.getNodeDisplayName();
		
	//	nodeAttrdtl = docmgmtp7ub.getNodeAttrDetailsForPdfPublish(wsId);
		//System.out.println(nodeAttrdtl.get(1).getAttrValue());
		
		/*if(dto.getNodeDisplayName().contains("(") && !dto.getNodeDisplayName().substring(0,6).contains("Module"))
		{
			for(int attrval=0 ; attrval<nodeAttrdtl.size(); attrval++)
			{
				nodeDisplayName="";
				if(dto.getNodeDisplayName().equals(nodeAttrdtl.get(attrval).getNodeDisplayName()))
				{
			
					int index = dto.getNodeDisplayName().lastIndexOf('(');
					nodeDisplayName = dto.getNodeDisplayName().substring(0,index);
					break;
				}
				else
				{
					nodeDisplayName = dto.getNodeDisplayName();
				}
			}
		}
		else{*/
		allnodattrdetail = docmgmtpub.getNodedetailwithAttr(wsId);
		
		for(int attrval=0;attrval<allnodattrdetail.size();attrval++)
		{
			if(dto.getNodeID()== allnodattrdetail.get(attrval).getParentId())
			{
				AttrValue = allnodattrdetail.get(attrval).getAttrValue();
				break;
			}
			else
			{
				AttrValue="";
			}
		}
		

		nodeDisplayName = dto.getNodeDisplayName().toString();
		
	/*	if(AttrValue.contains("-") && !AttrValue.equals(""))
		{
			//System.out.println("Hello");
		String[] fn = AttrValue.split("\\-");
		for(int attr=0;attr<fn.length;attr++)
		{
			nodeDisplayName=nodeDisplayName+"\n  "+fn[attr];
		}
		
	}*/
		if(AttrValue!="" && AttrValue!=null)
		{
		nodeDisplayName=nodeDisplayName+"\n"+AttrValue;
		}
		System.out.println("Hello" + nodeDisplayName);
		
		if(nodeDisplayName.contains("{"))
		{
		nodeDisplayName = nodeDisplayName.replaceAll("\\{", "\\(");
		nodeDisplayName = nodeDisplayName.replaceAll("\\}", "\\)");
		}
		
		//}
		// System.out.println(nodeDisplayName + "->" + level);
		if (level == 1) {
			// used for shifting toc at particular location
			modulewiseStartPageNumber.add(allStartPageNumber
					.get(startpagecounter));

			if (moduleCounter == -1) {
				moduleCounter = 0;
				moduleTable = initTOCTable();

			} else {
				modulewisetables.add(moduleTable);
				moduleCounter++;
				moduleTable = null;
				moduleTable = initTOCTable();
			}

		}
		if (level != 0) {
			int i;
			for (i = 1; i < level - 1; i++) {
				cell = table.getDefaultCell();
				if (i == 1 || i == level - 2) {
					cell.setBorder(Rectangle.LEFT);
				}
				table.addCell(cell);
				moduleTable.addCell(cell);
			}
			Chunk chunk = null;
			if (level == 1) {
				chunk = new Chunk(nodeDisplayName);
				chunk.setFont(tocLinkfontBold);

			} else {
				chunk = new Chunk(nodeDisplayName.substring(0, nodeDisplayName
						.indexOf(' ')));
				chunk.setFont(tocboldfont);
			}
			String linkLocation = "p"
					+ allStartPageNumber.get(startpagecounter);
			PdfAction action = PdfAction.gotoLocalPage(linkLocation, false);

			chunk.setAction(action);
			cell = new PdfPCell(new Phrase(chunk));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			if (level != 1) {
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);

			} else {
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setColspan(MAX_LEVEL_IN_TOC);
			}
			cell.setPadding(4);
			table.addCell(cell);
			moduleTable.addCell(cell);
			if (level != 1) {
				chunk = new Chunk(nodeDisplayName.substring(nodeDisplayName
						.indexOf(' ') + 1));
				nodeDisplayName="";
				chunk.setFont(tocLinkfont);
				chunk.setAction(action);
				cell = new PdfPCell(new Phrase(chunk));
				if (tocSettingList != null
						&& tocSettingList.contains("toc_addPageNumber"))
					cell.setColspan(MAX_LEVEL_IN_TOC - level);
				else
					cell.setColspan(MAX_LEVEL_IN_TOC - level + 1);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
				moduleTable.addCell(cell);
				if (tocSettingList != null
						&& tocSettingList.contains("toc_addPageNumber")) {
					if (pdfpublishDTO.getTocPageNumberStyle().equals(
							PAGE_NO_STYLE_123)) {
						if (tocSettingList.contains("toc_modulewisenumber")) {
							cell = new PdfPCell(new Phrase(""
									+ modulewiseallStartPageNumber
											.get(startpagecounter)));
						} else {
							cell = new PdfPCell(new Phrase(""
									+ allStartPageNumber.get(startpagecounter)));
						}
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					} else {

						if (tocSettingList.contains("toc_modulewisenumber")) {
							cell = new PdfPCell(new Phrase(String
									.format("%d of %d",
											modulewiseallStartPageNumber
													.get(startpagecounter),
											moduleWiseTotalPages
													.get(moduleCounter + 1))));
						} else {
							cell = new PdfPCell(
									new Phrase(String.format("%d of %d",
											allStartPageNumber
													.get(startpagecounter),
											totalPages)));
						}
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					}
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPadding(4);
					table.addCell(cell);
					moduleTable.addCell(cell);
				}
			}
			startpagecounter++;
		}
		for (DTOWorkSpaceNodeDetail obj : childs) {
			processTOCTree(obj, level + 1);
		}
	}

	private ArrayList<DTOWorkSpaceNodeDetail> selectChild(int nodeID) {
		ArrayList<DTOWorkSpaceNodeDetail> list = new ArrayList<DTOWorkSpaceNodeDetail>();
		for (int i = 0; i < tree.size(); i++) {
			if (tree.get(i).getParentID() == nodeID) {
				list.add(tree.get(i));
			}
		}
		return list;
	}

	private String prefix(int level) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < level; i++) {
			s.append("----");
		}
		return s.toString();
	}

	private void addFileTitlePageDetails(int pdfCounter,
			ArrayList<DTOWorkSpaceNodeDetail> TOCnodeDetails,
			PdfImportedPage page, PdfContentByte overContent) {
		// TODO Auto-generated method stub

		// ColumnText.showTextAligned(overContent, Element.ALIGN_CENTER,
		// new Phrase(TOCnodeDetails.get(pdfCounter - 1)
		// .getNodeDisplayName().toString()), page.getWidth() / 2,
		// page.getHeight() / 2 - 25, 0);

		addNodeTitlePageDetails(pdfCounter, overContent, TOCnodeDetails, page,
				null);

	}

	private void addNodeTitlePageDetails(int pdfCounter,
			PdfContentByte overContent,
			ArrayList<DTOWorkSpaceNodeDetail> TOCnodeDetails,
			PdfImportedPage page, ArrayList<DTOWorkSpaceNodeDetail> allNodesDtl) {

		// If Title Node.. get node data from nodeDetail
		// if (pdfCounter == 1) // Root Node
		// {
		// ColumnText.showTextAligned(overContent, Element.ALIGN_CENTER,
		// new Phrase(TOCnodeDetails.get(pdfCounter - 1)
		// .getNodeDisplayName().toString()),
		// page.getWidth() / 2, page.getHeight() / 2, 0);
		//
		// } else {

		// commented part is for displaying parent node name too.
		// String parentNodeName = "";
		// for (int nodeCounter = 0; nodeCounter < allNodesDtl.size();
		// nodeCounter++) {
		//
		// if (allNodesDtl.get(nodeCounter).getNodeId() == TOCnodeDetails
		// .get(pdfCounter - 1).getParentNodeId()) {
		// parentNodeName = allNodesDtl.get(nodeCounter)
		// .getNodeDisplayName();
		// break;
		// }
		// }
		// if (TOCnodeDetails.get(pdfCounter - 1).getParentNodeId() != 1) {
		// ColumnText.showTextAligned(overContent, Element.ALIGN_CENTER,
		// new Phrase(parentNodeName), page.getWidth() / 2, page
		// .getHeight() / 2, 0);
		//
		// }
		//nodeAttrdtl = docmgmtpub.getNodeAttrDetailsForPdfPublish(wsId);
		
		//if(TOCnodeDetails.get(
		//		pdfCounter - 1).getNodeDisplayName().toString().contains("(") && !TOCnodeDetails.get(pdfCounter - 1).getNodeDisplayName().substring(0,6).contains("Module"))
		//{
		//int index = TOCnodeDetails.get(
		//		pdfCounter - 1).getNodeDisplayName().toString().lastIndexOf('(');
		//System.out.println(TOCnodeDetails.get(
		//		pdfCounter - 1).getNodeDisplayName().toString());
		//System.out.println(index);
		
		//nodename =  TOCnodeDetails.get(
		//		pdfCounter - 1).getNodeDisplayName().toString().substring(0,index);
		
		//System.out.println(nodename);
		//for(int attrval=0 ; attrval<nodeAttrdtl.size(); attrval++)
		//{
		//	nodename="";
		//	if(TOCnodeDetails.get(pdfCounter - 1).getNodeDisplayName().equals(nodeAttrdtl.get(attrval).getNodeDisplayName()))
		//	{
		
			//	int index = TOCnodeDetails.get(pdfCounter - 1).getNodeDisplayName().lastIndexOf('(');
			//	nodename = TOCnodeDetails.get(pdfCounter - 1).getNodeDisplayName().substring(0,index);
			//	break;
			//}
			//else
			//{
			//	nodename = TOCnodeDetails.get(pdfCounter - 1).getNodeDisplayName();
			//}
		
		//}
		//}
		//else {
			nodename = TOCnodeDetails.get(
				pdfCounter - 1).getNodeDisplayName().toString();
			if(nodename.contains("{"))
			{
				nodename = nodename.replaceAll("\\{", "\\(");
				nodename = nodename.replaceAll("\\}", "\\)");
			}
		//}
		PdfPTable table;
		PdfPTable table2;

		float tablewidth = 500f;

		table = new PdfPTable(1);
		table2 = new PdfPTable(2);
		table.setTotalWidth(tablewidth);

		//PdfPCell cell = new PdfPCell(new Phrase(TOCnodeDetails.get(
		//		pdfCounter - 1).getNodeDisplayName().toString(), nodeTitleFont));
		PdfPCell cell = new PdfPCell(new Phrase(nodename, nodeTitleFont));
		cell.setPadding(20f);
		cell.setLeading(1f, 1.4f);
		cell.setBorderWidthLeft(2f);
		cell.setBorderWidthTop(2f);
		cell.setBorderWidthBottom(3.5f);
		cell.setBorderWidthRight(4f);

		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		
		
		cell.setUseDescender(false);

		table.addCell(cell);
		table.setSpacingAfter(20);
			
		table.completeRow();
		
		
		PdfPCell blankcell = new PdfPCell(new Phrase("", nodeTitleFont)); 
		blankcell.setBorder(Rectangle.NO_BORDER);
		blankcell.setPaddingTop(20f);
		table.addCell(blankcell);
		for(int attrname=0;attrname<allNodesDtl.size();attrname++)
		{
			//System.out.println(TOCnodeDetails.get(pdfCounter-1).getFolderName());
			if(TOCnodeDetails.get(pdfCounter-1).getNodeName().equalsIgnoreCase(allNodesDtl.get(attrname).getNodeName()))
			{
				
				
					System.out.println("Attribute value:"+allNodesDtl.get(attrname).getAttrValue());
				
				if( !allNodesDtl.get(attrname).getAttrValue().equals(""))
				{
				PdfPCell cell1 = new PdfPCell(new Phrase(allNodesDtl.get(attrname).getAttrValue(), nodeTitleFont));
				
				//cell1.setPadding(20f);
				//cell1.setLeading(1f, 1.4f);
				//cell1.setBorderWidthLeft(2f);
				//cell1.setBorderWidthTop(2f);
				//cell1.setBorderWidthBottom(3.5f);
				//cell1.setBorderWidthRight(4f);
				//blankcell.setBorder(Rectangle.NO_BORDER);
				
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				
				//cell1.setUseDescender(false);

				//table.addCell(cell1);
				
				
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setPaddingTop(20f);
				table.addCell(cell1);
				}	
				
			}
		}
		
		
		float height = table.getRowHeight(0);
		// write the table to an absolute position

		// System.out.println("PageHeight->"+height+"--"+PageSize.A4.getHeight());
		table.writeSelectedRows(0, -1, (PageSize.A4.getWidth() / 2)
				- (tablewidth/2), (PageSize.A4.getHeight() / 2)
				+ (height/2), overContent);

		
	

		// }

	}

	private void addFooterInPdf(PageStamp stamp, PdfImportedPage page,
			int rotation, int pageNo, int totalPages, int pdfCounter,
			PdfContentByte pcb, boolean isTocPage) {

		float x = 1f;
		float y = 40;
		try {

			if (footerSettingList.contains("f_addLine")) {
				pcb.moveTo(x, y);
				if (rotation == 270 || rotation == 90) {

					x = page.getHeight();
				} else {

					x = page.getWidth();
				}
				pcb.setLineWidth(1.0f);
				pcb.setGrayStroke(0.50f); // // 1 = black, 0 = white
				pcb.lineTo(x, y);
				// pcb.setLineDash(2,2,0);
				// pcb.stroke();

				pcb.fillStroke();
			}

			if (rotation == 270 || rotation == 90) {

				x = page.getHeight();
			} else {

				x = page.getWidth();
			}
			if (footerSettingList.contains("f_addVersion")
					&& pdfpublishDTO.getVersion() != null
					&& !pdfpublishDTO.getVersion().equals("")) {
				chunk = new Chunk(pdfpublishDTO.getVersion());
				chunk.setFont(fontFooter);
				if (pdfpublishDTO.getFooterContentPosition().equalsIgnoreCase(
						POSITION_CENTER)) {
					ColumnText.showTextAligned(pcb, Element.ALIGN_CENTER,
							new Phrase(chunk), x / 2, y - 35, 0);
				} else if (pdfpublishDTO.getFooterContentPosition()
						.equalsIgnoreCase(POSITION_LEFT)) {
					ColumnText.showTextAligned(pcb, Element.ALIGN_LEFT,
							new Phrase(chunk), 20, y - 35, 0);
				} else if (pdfpublishDTO.getFooterContentPosition()
						.equalsIgnoreCase(POSITION_RIGHT)) {
					ColumnText.showTextAligned(pcb, Element.ALIGN_RIGHT,
							new Phrase(chunk), x - 20, y - 35, 0);
				}

			}

			if (footerSettingList.contains("f_addProductName")) {
				// adding Product Name
				if (productName != null && !productName.equals("")) {
					chunk = new Chunk(productName.toString());
					chunk.setFont(fontFooter);
					ColumnText.showTextAligned(pcb, Element.ALIGN_LEFT,
							new Phrase(chunk), 150, y - 20, 0);

				}
				if (productName2 != null && !productName2.equals("")) {
					chunk = new Chunk(productName2.toString());
					chunk.setFont(fontFooter);

					ColumnText.showTextAligned(pcb, Element.ALIGN_LEFT,
							new Phrase(chunk), 150, y - 30, 0);

				}
			}
			if (footerSettingList.contains("f_addNodeName")) {
				chunk = new Chunk("Node Display Name");
				chunk.setFont(fontFooter);
				ColumnText.showTextAligned(pcb, Element.ALIGN_RIGHT,
						new Phrase(chunk), page.getWidth() - 10, y - 10, 0);

			}
			if (footerSettingList.contains("f_addPageNumber")) {
				// Add Page Number
				if (!isTocPage) {

					String style = "";
					if (pdfpublishDTO.getFooter_pageNumberStyle()
							.equalsIgnoreCase(PAGE_NO_STYLE_123)) {
						style = String.format("%d", pageNo);
					} else if (pdfpublishDTO.getFooter_pageNumberStyle()
							.equalsIgnoreCase(PAGE_NO_STYLE_1_OF_N)) {
						if (tocSettingList != null
								&& tocSettingList
										.contains("toc_modulewisenumber"))
							style = String.format("%d of %d", pageNo,
									moduleWiseTotalPages.get(currentModule));
						else
							style = String.format("%d of %d", pageNo,
									totalPages);
					}
					chunk = new Chunk(style);
					if (pdfpublishDTO.getFooter_pageNumberPosition()
							.equalsIgnoreCase(POSITION_CENTER)) {
						ColumnText.showTextAligned(pcb, Element.ALIGN_CENTER,
								new Phrase(chunk), x / 2, y - 30, 0);
					} else if (pdfpublishDTO.getFooter_pageNumberPosition()
							.equalsIgnoreCase(POSITION_LEFT)) {
						ColumnText.showTextAligned(pcb, Element.ALIGN_LEFT,
								new Phrase(chunk), 20, y - 30, 0);
					} else if (pdfpublishDTO.getFooter_pageNumberPosition()
							.equalsIgnoreCase(POSITION_RIGHT)) {
						ColumnText.showTextAligned(pcb, Element.ALIGN_RIGHT,
								new Phrase(chunk), x - 20, y - 30, 0);
					}
				}
			}
			if (footerSettingList.contains("f_addLogo")) {
				// Add Logo
				// Image img =
				// Image.getInstance("D://pdfs/logo.png");

				logoImg.setAbsolutePosition(20, 5);
				try {
					pcb.addImage(logoImg);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	private void addheaderInPdf(PageStamp stamp, PdfImportedPage page,
			int rotation, ArrayList<DTOWorkSpaceNodeDetail> TOCnodeDetails,
			int pageNo, int totalPages, int pdfCounter, PdfContentByte pcb) {

		float x = 1f;
		float y = page.getHeight() - HEADER_HEIGHT;

		if (rotation == 270 || rotation == 90) {

			y = page.getWidth() - HEADER_HEIGHT;
			if (headerSettingList.contains("h_addLine"))
				pcb.moveTo(x, y);
			x = page.getHeight();

		} else {
			if (headerSettingList.contains("h_addLine"))
				pcb.moveTo(x, y);
			x = page.getWidth();

		}
		if (headerSettingList.contains("h_addLine")) {

			pcb.setLineWidth(1.0f);
			pcb.setGrayStroke(0.50f); // // 1 = black, 0 = white
			pcb.lineTo(x, y);
			pcb.stroke();
		}
		if (headerSettingList.contains("h_addProductName")) {
			// adding Product Name
			if (productName != null && !productName.equals("")) {
				chunk = new Chunk(productName.toString());
				// 
				//y + 50
				chunk.setFont(fontBoldItalic);
				//System.out.println(chunk.getWidthPoint());
				if (pdfpublishDTO.getHeaderTextPosition().equals(POSITION_LEFT)) {
					ColumnText.showTextAligned(pcb, Element.ALIGN_LEFT,
							new Phrase(chunk), 20, y + 50, 0);
				} else if (pdfpublishDTO.getHeaderTextPosition().equals(
						POSITION_RIGHT)) {
					ColumnText.showTextAligned(pcb, Element.ALIGN_RIGHT,
							new Phrase(chunk), x - 20, y + 50, 0);
				} else {
					ColumnText.showTextAligned(pcb, Element.ALIGN_CENTER,
							new Phrase(chunk), x / 2, y + 50, 0);
				}

			}
			if (productName2 != null && !productName2.equals("")) {
				chunk = new Chunk(productName2.toString());
				chunk.setFont(fontBoldItalic);
				if (pdfpublishDTO.getHeaderTextPosition().equals(POSITION_LEFT)) {
					ColumnText.showTextAligned(pcb, Element.ALIGN_LEFT,
							new Phrase(chunk), 20, y + 30, 0);
				} else if (pdfpublishDTO.getHeaderTextPosition().equals(
						POSITION_RIGHT)) {
					ColumnText.showTextAligned(pcb, Element.ALIGN_RIGHT,
							new Phrase(chunk), x - 20, y + 30, 0);
				} else {
					ColumnText.showTextAligned(pcb, Element.ALIGN_CENTER,
							new Phrase(chunk), x / 2, y + 30, 0);
				}
			}
		}
		if (headerSettingList.contains("h_addNodeName")) {
			String nodeDisplayName="";
			String folderName="";
			//nodeAttrdtl = docmgmtpub.getNodeAttrDetailsForPdfPublish(wsId);
			if (TOCnodeDetails != null) {
				
			/*	if(TOCnodeDetails.get(pdfCounter - 1)
						.getNodeDisplayName().toString().contains("(") && !TOCnodeDetails.get(pdfCounter - 1)
						.getNodeDisplayName().toString().substring(0,6).contains("Module"))
				{
					for(int attrval=0 ; attrval<nodeAttrdtl.size(); attrval++)
					{
						nodeDisplayName="";
						if(TOCnodeDetails.get(pdfCounter - 1).getNodeDisplayName().equals(nodeAttrdtl.get(attrval).getNodeDisplayName()))
						{
					
							int index = TOCnodeDetails.get(pdfCounter - 1).getNodeDisplayName().lastIndexOf('(');
							nodeDisplayName = TOCnodeDetails.get(pdfCounter - 1).getNodeDisplayName().substring(0,index);
							break;
						}
						else
						{
							nodeDisplayName = TOCnodeDetails.get(pdfCounter - 1).getNodeDisplayName();
						}
					}
				}
				else{*/
					nodeDisplayName = TOCnodeDetails.get(pdfCounter - 1)
							.getNodeDisplayName().toString();
					folderName = TOCnodeDetails.get(pdfCounter - 1)
							.getFolderName().toString();
				/*	if(nodeDisplayName.contains("{"))
					{
					nodeDisplayName = nodeDisplayName.substring(0,nodeDisplayName.indexOf('{'));
					}*/
					if(nodeDisplayName.contains("{"))
					{
						nodeDisplayName = nodeDisplayName.replaceAll("\\{", "\\(");
						nodeDisplayName = nodeDisplayName.replaceAll("\\}", "\\)");
						if(folderName.endsWith(".pdf") || folderName.endsWith(".PDF") ){
							int index = nodeDisplayName.indexOf("(");
							nodeDisplayName = nodeDisplayName.substring(0,index);
						}
					}
				//}
				//chunk = new Chunk(TOCnodeDetails.get(pdfCounter - 1)
				//		.getNodeDisplayName().toString());
				
					chunk = new Chunk(nodeDisplayName.toString());
					chunk.setFont(fontBoldItalic);
				
				if (pdfpublishDTO.getNodeTitlePosition().equalsIgnoreCase(
						POSITION_RIGHT)) {
					if (headerSettingList.contains("h_addLogo")
							&& pdfpublishDTO.getHeader_logoPosition()
									.equalsIgnoreCase(POSITION_RIGHT)) {
						ColumnText.showTextAligned(pcb, Element.ALIGN_RIGHT,
								new Phrase(chunk), x - 20, y + 6, 0);
					} else if (headerSettingList.contains("h_addProductName")
							&& pdfpublishDTO.getHeaderTextPosition().equals(
									POSITION_RIGHT)) {
						ColumnText.showTextAligned(pcb, Element.ALIGN_RIGHT,
								new Phrase(chunk), x - 20, y + 6, 0);
					} else {
						ColumnText.showTextAligned(pcb, Element.ALIGN_RIGHT,
								new Phrase(chunk), x - 15, y + 40, 0);
					}

				} else if (pdfpublishDTO.getNodeTitlePosition()
						.equalsIgnoreCase(POSITION_LEFT)) {
					if (headerSettingList.contains("h_addLogo")
							&& pdfpublishDTO.getHeader_logoPosition()
									.equalsIgnoreCase(POSITION_LEFT)) {
						ColumnText.showTextAligned(pcb, Element.ALIGN_LEFT,
								new Phrase(chunk), 20, y + 6, 0);
					} else if (headerSettingList.contains("h_addProductName")
							&& pdfpublishDTO.getHeaderTextPosition()
									.equalsIgnoreCase(POSITION_LEFT)) {
						ColumnText.showTextAligned(pcb, Element.ALIGN_LEFT,
								new Phrase(chunk), 20, y + 6, 0);
					} else {
						ColumnText.showTextAligned(pcb, Element.ALIGN_LEFT,
								new Phrase(chunk), 20, y + 40, 0);
					}

				} else {
					ColumnText.showTextAligned(pcb, Element.ALIGN_CENTER,
							new Phrase(chunk), x - 15, y + 6, 0);
				}
			}
		}
		if (headerSettingList.contains("h_addPageNumber")) {
			// Add Page Number
			chunk = new Chunk(String.format("%d of %d", pageNo, totalPages));
			chunk.setFont(whiteFont);

			// if (i == 1)
			// chunk.setLocalDestination("p" + pageNo);

			ColumnText.showTextAligned(pcb, Element.ALIGN_RIGHT, new Phrase(
					chunk), x / 2, y + 40, 0);
		}
		if (headerSettingList.contains("h_addLogo")) {
			// Add Logo
			try {
				if (logoImg != null) {
					if (pdfpublishDTO.getHeader_logoPosition()
							.equalsIgnoreCase(POSITION_RIGHT)) {
						logoImg.setAbsolutePosition(x - 110, y + 25);
					} else if (pdfpublishDTO.getHeader_logoPosition()
							.equalsIgnoreCase(POSITION_LEFT)) {
						logoImg.setAbsolutePosition(20, y + 25);
					} else {
						logoImg.setAbsolutePosition(x / 2 - 50, y + 25);
					}
					pcb.addImage(logoImg);
				}
			} catch (Exception e) {
			}
		}
	}

	public void shrinkPdf(PdfReader reader, PageStamp stamper, int pageNumber)
			throws Exception {

		float percentage = 0.85f;
		int rotation = reader.getPageRotation(pageNumber);
		float pageWidth = reader.getPageSizeWithRotation(pageNumber).getWidth();
		float pageHeight = reader.getPageSizeWithRotation(pageNumber)
				.getHeight();
		float offsetX;
		float offsetY;
		if (pageWidth > pageHeight)
			percentage = ((130 * 100) / pageHeight) / 100;
		else
			percentage = ((100 * 100) / pageWidth) / 100;

		offsetX = (pageWidth * percentage) / 2;
		offsetY = (pageHeight * percentage) / 2;

		// percentage = 1 - 0.10f;
		percentage = 1 - percentage;
		// System.out.println("Rotation->" + percentage);
		switch (rotation) {
		case 0:
			stamper.getUnderContent().setLiteral(
					String.format("\nq %s 0 0 %s %s %s cm\nq\n", percentage,
							percentage, offsetX, offsetY));
			break;
		case 90:
			stamper.getUnderContent().setLiteral(
					String.format("\nq 0 %s %s 0 %s %s cm\nq\n", -percentage,
							percentage, offsetX, pageHeight - offsetY));
			break;
		case 180:
			stamper.getUnderContent().setLiteral(
					String.format("\nq %s 0 0 %s %s %s cm\nq\n", -percentage,
							-percentage, pageWidth - offsetX, pageHeight
									- offsetY));
			break;
		case 270:
			stamper.getUnderContent().setLiteral(
					String.format("\nq 0 %s %s 0 %s %s cm\nq\n", percentage,
							-percentage, pageWidth - offsetX, offsetY));
			break;
		default:
			throw new Exception(String.format(
					"Unexpected page rotation: [{0}].", rotation));

		}
		stamper.getOverContent().setLiteral("\nQ\nQ\n");

	}

	public void addWaterMark(PdfContentByte canvas, float x, float y) {
		PdfGState gs1 = new PdfGState();
		gs1.setFillOpacity(0.2f);
		canvas.setGState(gs1);
		ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, watermarkText,
				x / 2, y / 2, 45);
	}

	public static void main(String arg[]) {

		// PdfMergeWithTOC t = new PdfMergeWithTOC();
		try {
			// t.shrinkPDFPages(0.1f, 10, 0.99f);

			// t.addFooter();

			// t.links();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** 
	 * Used to convert hexacolor to rgb
	 * 
	 * @param colorStr
	 *            - hexacode ex-#ffff44
	 * @version 1.0
	 * @since 2014-12-24
	 * @see IOException
	 */
	public static Color hex2Rgb(String colorStr) {
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer
				.valueOf(colorStr.substring(3, 5), 16), Integer.valueOf(
				colorStr.substring(5, 7), 16));
	}

	public void links() throws DocumentException, IOException {
		try {
			String[] files = new String[] { "D:/testpdfs/7.pdf" };
			String outputFile = "D:/testpdfs/output.pdf";
			int firstPage = 1;
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(outputFile));
			document.setPageSize(PageSize.A4);
			float W = PageSize.A4.getWidth() / 2;
			float H = PageSize.A4.getHeight() / 2;
			document.open();
			PdfContentByte cb = writer.getDirectContent();
			for (int i = 0; i < files.length; i++) {
				PdfReader currentReader = new PdfReader(files[i]);
				currentReader.consolidateNamedDestinations();
				for (int page = 1; page <= currentReader.getNumberOfPages(); page++) {

					// document.newPage();
					PdfImportedPage importedPage = writer.getImportedPage(
							currentReader, page);
					float a = 0.5f;
					float e = (page % 2 == 0) ? W : 0;
					float f = (page % 4 == 1 || page % 1 == 2) ? H : 0;

					ArrayList<?> links = currentReader.getLinks(page);

					cb.addTemplate(importedPage, a, 0, 0, a, e, f);
					for (int j = 0; j < links.size(); j++) {
						PdfAnnotation.PdfImportedLink link = (PdfAnnotation.PdfImportedLink) links
								.get(j);
						if (link.isInternal()) {
							int dPage = link.getDestinationPage();
							int newDestPage = (dPage - 1) / 1 + firstPage;
							float ee = (dPage % 2 == 0) ? W : 0;
							float ff = (dPage % 4 == 1 || dPage % 1 == 2) ? H
									: 0;
							link.setDestinationPage(newDestPage);
							link.transformDestination(a, 0, 0, a, ee, ff);
						}
						link.transformRect(a, 0, 0, a, e, f);
						writer.addAnnotation(link.createAnnotation(writer));
					}
				}
			}
			// document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void memoryStatCheck() {

		int mb = 1024 * 1024;

		// Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();

		System.out.println("##### Heap utilization statistics [MB] #####");

		// Print used memory
		System.out.println("Used Memory:"
				+ (runtime.totalMemory() - runtime.freeMemory()) / mb);

		// Print free memory
		System.out.println("Free Memory:" + runtime.freeMemory() / mb);

		// Print total available memory
		System.out.println("Total Memory:" + runtime.totalMemory() / mb);

		// Print Maximum available memory
		System.out.println("Max Memory:" + runtime.maxMemory() / mb);
	}

}
