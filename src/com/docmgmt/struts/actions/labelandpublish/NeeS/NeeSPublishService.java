package com.docmgmt.struts.actions.labelandpublish.NeeS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.PublishAttrForm;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.services.pdf.PdfPropUtilities;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfBoolean;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfLiteral;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionContext;

public class NeeSPublishService {

	private final float SRNO_COLUMN_WIDTH = 25f;
	private final int MAX_LEVEL_IN_TOC = 9;

	private static final String MAIN_TOC_NAME = "ctd-toc.pdf";
	private static final String TOC_TITLE = "Table Of Content";

	private static final int TOTAL_MUDULES = 5;

	Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLUE);
	BaseFont bf;

	Document document;

	public Vector<DTOWorkSpaceNodeDetail> parentNodes;
	public Vector<DTOWorkSpaceNodeAttrHistory> childAttrId;

	public Vector<Object[]> folderDtl;

	public String fileName;
	public String folderStructure;
	public String sourceFolderName;
	public String publishDestFolderName;
	public Integer attrId;
	public String attrValue, attrName;
	public Integer nodeId, nodeAttrId;
	public int childNode;
	public int childNodeSize = 0;
	public int iParentId = 1;
	public String wsId;
	public String stype = "";
	public String nodeName, nodeDisplayName;
	public StringBuffer folderName;
	public File folderStruct;
	public File createBaseFolder;
	public String LastPublishedVersion;

	private String relativePathToCreate;
	private String absolutePathToCreate;
	private String nodetypeindi;

	// Added For giving space
	String newPath = "";

	public String workspaceDesc;
	public String baseLocation;
	public String workspaceLabelId;
	public Integer userId;

	public int tranno;
	public String folder;

	Vector<Integer> AllNodesofHistory = new Vector<Integer>();

	DocMgmtImpl docMgmtInt = new DocMgmtImpl();

	HttpServletRequest request;

	private String currentSequenceNumber;

	private char projectPublishType;
	private int[] leafIds;
	FileManager fileManager;

	boolean isTrackingTableUploaded = false;

	private PdfPCell cell;
	private PdfPTable table;

	private PdfPTable maintoctable;
	PdfPropUtilities pdfutil;
	public NeeSPublishService() {
		PropertyInfo propInfo = PropertyInfo.getPropInfo();
		publishDestFolderName = propInfo.getValue("PublishFolder");
		fileManager = new FileManager();
		pdfutil=new PdfPropUtilities();
	}

	public void workspacePublish(String workspaceId,
			PublishAttrForm publishForm, String wsDesc) {
		
		try {

			wsId = workspaceId;
			workspaceDesc = wsDesc;
			folderName = new StringBuffer();
			parentNodes = new Vector<DTOWorkSpaceNodeDetail>();
			childAttrId = new Vector<DTOWorkSpaceNodeAttrHistory>();

			bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252,
					BaseFont.NOT_EMBEDDED);

			int labelNo = publishForm.getLabelNo();

			userId = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userid").toString());

			publishDestFolderName = publishForm.getPublishDestinationPath();
			createBaseFolder(publishDestFolderName, publishForm);

			stype = publishForm.getSubmissionFlag();
			currentSequenceNumber = publishForm.getSeqNumber();

			System.out.println("Current Seq=" + currentSequenceNumber);

			if (projectPublishType == 'N') {
				// get all nodes and its parent nodes where file attached
				AllNodesofHistory = docMgmtInt
						.getAllNodesFromHistoryForRevisedSubmission(wsId,
								labelNo);
			}

			iParentId = 1;

			initMainTOCTable();
			
			Vector<DTOWorkSpaceNodeDetail> childs=docMgmtInt.getChildNodeByParent(1, wsId);
			for (int currModuleNo = 1; currModuleNo < childs.size()+1; currModuleNo++) {

				parentNodes = docMgmtInt
						.getModuleWiseChildNodeByParentForNeesPublish(
								iParentId, childs.get(currModuleNo-1).getNodeNo(), wsId);

				DTOWorkSpaceNodeDetail dto = (DTOWorkSpaceNodeDetail) parentNodes
						.get(0);

				if (parentNodes.size() < 1
						|| !AllNodesofHistory.contains(dto.getNodeId()))
					continue;

				String moduleTOCName = "m" + currModuleNo + "-toc.pdf";

				File moduleFolder = new File(absolutePathToCreate + "/m"
						+ currModuleNo + "/");

				String tocPath = "m" + currModuleNo + "/" + moduleTOCName;

				addCellinMainTOC(dto.getNodeDisplayName(), tocPath);

				moduleFolder.mkdirs();

				createAndOpenDocument(absolutePathToCreate + "/m"
						+ currModuleNo + "/" + moduleTOCName);

				initTable();
				getChildNode(parentNodes, absolutePathToCreate, iParentId, 0,
						stype, publishForm, 0);
				addBlankCell();
				document.add(table);
				document.close();
//				
				
				pdfutil.autoCorrectPdfProp(absolutePathToCreate + "/m"
						+ currModuleNo + "/" + moduleTOCName,true);
			}

			generateMainTOC();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateMainTOC() {
		try {
			document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(
					absolutePathToCreate + "/" + MAIN_TOC_NAME));

			document.setMargins(20, 20, 70, 60);
			document.open();
			document.add(maintoctable);
			document.close();
			pdfutil.autoCorrectPdfProp(absolutePathToCreate + "/" + MAIN_TOC_NAME,true);
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createAndOpenDocument(String docName) throws IOException {
		document = new Document();

		PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(
					docName));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// step 3

		document.setMargins(20, 20, 70, 60);

		document.open();

		PdfContentByte cb = writer.getDirectContent();

		cb.saveState();
		cb.beginText();
		cb.moveText(PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() - 20);

		cb.setFontAndSize(bf, 18);
		cb.showTextAligned(1, TOC_TITLE, PageSize.A4.getWidth() / 2,
				PageSize.A4.getHeight() - 40, 0);
		cb.endText();
		cb.restoreState();

	}

	private void addCellinMainTOC(String nodeDisplayName, String tocPath) {
		Chunk chunk = new Chunk(nodeDisplayName);
		chunk.setFont(font);
		PdfAction action = new PdfAction(tocPath, 1);
		action.put(PdfName.NEWWINDOW, PdfBoolean.PDFTRUE);
		action.put(PdfName.D, new PdfLiteral("[" + (0) + " /XYZ 0 10000]"));
		chunk.setAction(action);

		// chunk.setAction(PdfAction.gotoRemotePage(tocPath,"1", false, true));

		cell = new PdfPCell(new Phrase(chunk));
		cell.setPadding(10);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(MAX_LEVEL_IN_TOC - 1);

		maintoctable.addCell(cell);
	}

	public void initMainTOCTable() throws DocumentException {
		maintoctable = new PdfPTable(1);
		maintoctable.setWidthPercentage(85f);
		maintoctable.getDefaultCell().setUseAscender(true);
		maintoctable.getDefaultCell().setUseDescender(true);
		maintoctable.getDefaultCell().setBorderWidth(1);

		maintoctable.setKeepTogether(true);
		maintoctable.setSplitRows(true);

	}

	public void addBlankCell() {
		cell = new PdfPCell(new Phrase(""));
		cell.setColspan(MAX_LEVEL_IN_TOC);
		cell.setBorder(Rectangle.TOP);

		table.addCell(cell);

	}

	public PdfPTable initTable() throws DocumentException {
		table = new PdfPTable(MAX_LEVEL_IN_TOC);

		table.setWidthPercentage(90f);
		table.getDefaultCell().setUseAscender(true);
		table.getDefaultCell().setUseDescender(true);
		table.getDefaultCell().setBorderWidth(1);

		table.setKeepTogether(true);
		table.setSplitRows(true);

		float[] columnWidths = new float[MAX_LEVEL_IN_TOC];
		columnWidths[0] = SRNO_COLUMN_WIDTH; // Sr No
		for (int i = 1; i < MAX_LEVEL_IN_TOC; i++) {
			columnWidths[i] = 18f;

		}
		columnWidths[MAX_LEVEL_IN_TOC - 1] = 80;

		try {
			table.setWidths(columnWidths);
			table.setSplitRows(true);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return table;
	}

	private void addLinkCell(String nodeDisplayName, int level) {

		int row = 0;
		for (row = 0; row < level; row++) {
			cell = table.getDefaultCell();
			if (row == 0 || row != level - 1) {
				cell.setBorder(Rectangle.LEFT);
			}
			table.addCell(cell);

		}
		Chunk chunk = new Chunk(nodeDisplayName);

		chunk.setFont(font);
		PdfAction action = new PdfAction("../" + attrValue, 1);
		action.put(PdfName.NEWWINDOW, PdfBoolean.PDFTRUE);
		action.put(PdfName.D, new PdfLiteral("[" + (0) + " /XYZ 0 10000]"));
		chunk.setAction(action);

		cell = new PdfPCell(new Phrase(chunk));
		cell.setColspan(MAX_LEVEL_IN_TOC - row);
		table.addCell(cell);

	}

	private void addCell(String nodeDisplayName, int level) {

		int row = 0;
		for (row = 0; row < level; row++) {
			cell = table.getDefaultCell();
			if (row == 0 || row != level - 1) {
				cell.setBorder(Rectangle.LEFT);
			}
			table.addCell(cell);

		}

		try {

			String nodeNumber = nodeDisplayName.substring(0, nodeDisplayName
					.indexOf(' '));

			String nodeName = nodeDisplayName.substring(nodeDisplayName
					.indexOf(' ') + 1);

			Chunk chunk = new Chunk(nodeNumber);
			Chunk chunk1 = new Chunk(nodeName);
			cell = new PdfPCell(new Phrase(chunk));
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(chunk1));
			cell.setColspan(MAX_LEVEL_IN_TOC - row - 1);
			table.addCell(cell);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This Method recursively create Node for Parent Node
	 * 
	 * @param childNodes
	 * @throws Exception
	 */

	public void getChildNode(Vector<DTOWorkSpaceNodeDetail> childNodes,
			String pathToCreate, int parentId, int IdValue, String stype,
			PublishAttrForm publishForm, int level) throws Exception {
		if (childNodes.size() == 0) {
			// if file attached at node or its parent node
			if (AllNodesofHistory.contains(nodeId)) {

				childAttrId = docMgmtInt.getAttributesForNodeForPublish(nodeId
						.intValue(), wsId, publishForm.getLabelNo(), "0001");

				if (childAttrId.size() != 0) {
					for (int i = 0; i < childAttrId.size(); i++) {
						DTOWorkSpaceNodeAttrHistory dtowsnath = (DTOWorkSpaceNodeAttrHistory) childAttrId
								.get(i);
						attrId = dtowsnath.getAttrId();
						attrValue = dtowsnath.getAttrValue();
						attrName = dtowsnath.getAttrName();

						if (attrName.equalsIgnoreCase("xlink:href")) {

							// System.out.println("else - attribute value not delete");
							if (nodetypeindi.trim().equals("I")) {
								String newPath = pathToCreate.substring(0,
										pathToCreate.substring(0,
												pathToCreate.lastIndexOf("/"))
												.lastIndexOf("/"));

								attrValue = copyFileforPublish(publishForm,
										newPath);
							} else
								attrValue = copyFileforPublish(publishForm,
										pathToCreate);

							attrValue = attrValue.toLowerCase();
							attrValue = attrValue.replaceAll("//", "/");

							System.out.println("File Path->" + attrValue);
							addLinkCell(nodeDisplayName, level - 1);

						}
					}
				} else {
					attrValue = copyFileforPublish(publishForm, pathToCreate);
					attrValue = attrValue.toLowerCase();
					attrValue = attrValue.replaceAll("//", "/");
					addLinkCell(nodeDisplayName, level - 1);

				}

			}// if end of history check

		}// if end
		else {

			String MergeAttributeStr = "";

			for (int i = 0; i < childNodes.size(); i++) {
				DTOWorkSpaceNodeDetail dtowsnd = (DTOWorkSpaceNodeDetail) childNodes
						.get(i);

				nodeId = dtowsnd.getNodeId();
				nodeName = dtowsnd.getNodeName();

				nodeDisplayName = dtowsnd.getNodeDisplayName().trim();

				// table

				// System.out.println(level + "-" + nodeDisplayName);

				String filepathelement = dtowsnd.getFolderName();

				if (AllNodesofHistory.contains(nodeId)) {

					int isLeaf = docMgmtInt
							.isLeafNodes(wsId, nodeId.intValue());

					nodetypeindi = Character
							.toString(dtowsnd.getNodeTypeIndi());
					if (isLeaf == 0) {
							
						if(!nodeName.equalsIgnoreCase("specific") && !nodeName.equalsIgnoreCase("pi-doc")){
							
							
							addCell(nodeDisplayName, level);
						}
						

						if (nodetypeindi.equalsIgnoreCase("T")) {
							int intchar = filepathelement.indexOf(".");
							String finalstr;
							if (intchar != -1) {
								finalstr = filepathelement
										.substring(0, intchar);
							} else {
								finalstr = filepathelement;
							}
							createSubFolders(pathToCreate, finalstr);
						} else if (nodetypeindi.equalsIgnoreCase("D")) {

							createSubFolders(pathToCreate, "");
						} else if (nodetypeindi.equalsIgnoreCase("X")
								|| nodetypeindi.trim().equals("B")) {
							// Do Nothing
						} else {

							createSubFolders(pathToCreate, filepathelement);
						}
					}

					if (!nodeName
							.trim()
							.equals(
									"m1-administrative-information-and-prescribing-information")) {
						if (isLeaf == 0) {// This is a parent node
							if (!nodetypeindi.trim().equalsIgnoreCase("T")
									&& !nodetypeindi.trim().equals("F")
									&& !nodetypeindi.trim().equals("B")) {

								/**
								 * For nodeTypeIndi = 'P'
								 */
								String country = "", language = "";

								String PathForAttrFolder = "";

								Vector<DTOWorkSpaceNodeAttribute> nodeAttribute = docMgmtInt
										.getNodeAttributes(wsId, nodeId
												.intValue());
								if (nodeAttribute.size() > 0) {
									for (int k = 0; k < nodeAttribute.size(); k++) {
										DTOWorkSpaceNodeAttribute obj = (DTOWorkSpaceNodeAttribute) nodeAttribute
												.get(k);
										String attrname = obj.getAttrName();
										String attrvalue = obj.getAttrValue();

										/**
										 * For nodeTypeIndi = 'P'
										 */
										if (attrname
												.equalsIgnoreCase("country")) {
											country = attrvalue;
										}
										if (attrname
												.equalsIgnoreCase("xml:lang")) {
											language = attrvalue;
										}
										if (attrvalue != null
												&& !attrvalue.equals("")) {
											//
											// xmlwriter.writeAttribute(attrname,
											// attrvalue);
										}

										// remove space and give - in attribute
										// value
										if (attrvalue.length() > 4
												&& !attrvalue
														.equalsIgnoreCase("common")) {

											System.out.println("attrvalue: "
													+ attrvalue);

											
											attrvalue = attrvalue.trim()
													.replaceAll("\\.", "-");
											attrvalue = attrvalue.trim()
													.replaceAll(",", "-");
											attrvalue = attrvalue.substring(0,
													4).trim();

										} else {
											attrvalue = attrvalue.trim()
													.replaceAll(" ", "-");
										}

										if (k == 0) {
											MergeAttributeStr = attrvalue
													.toLowerCase();
										} else {
											MergeAttributeStr = MergeAttributeStr
													+ "-"
													+ attrvalue.toLowerCase();
										}

									}
									/*
									 * 
									 * Description: If repeated node with same
									 * attribute with different value, then
									 * create the attribute folder other wise
									 * not to required create attribute folder.
									 */

									/**
									 * nodeTypeIndi = 'P' (pi-doc) Description :
									 * Folder Name will be 'country name' Sub
									 * folder Name will be 'language'
									 */

									if (nodetypeindi.trim().equals("P")) {
										PathForAttrFolder = pathToCreate + "/"
												+ country;
										MergeAttributeStr = language;
									}

									/**
									 * nodeTypeIndi = 'D' (No Folder to be
									 * created) Description : Folder will be
									 * created from attribute values But not
									 * from 'vFolderName'
									 */
									else if (nodetypeindi.trim().equals("D")) {
										PathForAttrFolder = pathToCreate;
										System.out
												.println("Status Found() (D)");
										// No change in 'MergeAttributeStr'
									} else if (nodetypeindi.trim().equals("X")) {
										MergeAttributeStr = "";
										PathForAttrFolder = pathToCreate;
									}

									else if (nodetypeindi.trim().equals("C")) {
										if (!isNodeHavingClones(
												nodeName.trim(), nodeId)) {
											MergeAttributeStr = "";
										}
										PathForAttrFolder = pathToCreate + "/"
												+ filepathelement;
									}
									/**
									 * nodeTypeIndi = 'N' (normal node)
									 * Description : Folder will be created from
									 * attribute values But not from
									 * 'vFolderName'
									 */
									else {
										PathForAttrFolder = pathToCreate + "/"
												+ filepathelement;
									}
									// System.out.println("PathForAttrFolder:"+PathForAttrFolder);
									createSubFolders(PathForAttrFolder,
											MergeAttributeStr);
									parentNodes = docMgmtInt
											.getChildNodeByParent(nodeId
													.intValue(), wsId);
									getChildNode(parentNodes,
											relativePathToCreate, nodeId
													.intValue(), IdValue,
											stype, publishForm, level + 1);
									// xmlwriter.endEntity();
								} else {
									parentNodes = docMgmtInt
											.getChildNodeByParent(nodeId
													.intValue(), wsId);
									getChildNode(parentNodes,
											relativePathToCreate, nodeId
													.intValue(), IdValue,
											stype, publishForm, level + 1);
									// xmlwriter.endEntity();
								}
							} else {
								parentNodes = docMgmtInt.getChildNodeByParent(
										nodeId.intValue(), wsId);
								getChildNode(parentNodes, relativePathToCreate,
										nodeId.intValue(), IdValue, stype,
										publishForm, level + 1);
							}
						} else {
							parentNodes = docMgmtInt.getChildNodeByParent(
									nodeId.intValue(), wsId);
							getChildNode(parentNodes, relativePathToCreate,
									nodeId.intValue(), IdValue, stype,
									publishForm, level + 1);
						}
					} else {
						parentNodes = docMgmtInt.getChildNodeByParent(nodeId
								.intValue(), wsId);
						getChildNode(parentNodes, relativePathToCreate, nodeId
								.intValue(), IdValue, stype, publishForm,
								level + 1);
					}

					/* Added by Ashmak Shah */
					/*
					 * STF Nodes After CRFs were copied into the crf folder to
					 * avoid this below line is added.
					 */
					relativePathToCreate = pathToCreate;
				}// if end of history vector

			} // for loop end
		}// main else end

	}

	private void createBaseFolder(String bfoldername,
			PublishAttrForm publishForm) {
		try {
			folderDtl = new Vector<Object[]>();

			folderDtl = docMgmtInt.getFolderByWorkSpaceId(wsId);

			if (folderDtl != null) {
				Object[] record = new Object[folderDtl.size()];
				record = (Object[]) folderDtl.elementAt(0);
				if (record != null) {
					// Source Folder Path
					sourceFolderName = record[0].toString();

					LastPublishedVersion = record[2].toString();

				}
			}

			// Change for New Submission Path
			createBaseFolder = new File(bfoldername + "/"
					+ publishForm.getSeqNumber());

			absolutePathToCreate = bfoldername + "/"
					+ publishForm.getSeqNumber();

			System.out.println("absolutePathToCreate: " + absolutePathToCreate);
			if (!createBaseFolder.exists()) // Check folder already present or
				// not
				createBaseFolder.mkdirs();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String copyFileforPublish(PublishAttrForm publishForm,
			String pathToCreate) throws Exception {

		String absolutePath = "";
		String absolutePathLink = "";
		newPath = "";
		absolutePath = pathToCreate;
		absolutePathLink = pathToCreate.replaceAll(absolutePathToCreate, "");

		Vector<DTOWorkSpaceNodeHistory> fileNameDtl;
		// In case of Manual Mode Publish

		fileNameDtl = docMgmtInt.getFileNameForNodeForPublish(
				nodeId.intValue(), wsId, publishForm.getLabelNo());

		if (fileNameDtl.size() > 0) {
			DTOWorkSpaceNodeHistory dtowsand = fileNameDtl.get(0);

			fileName = dtowsand.getFileName();
			folderStructure = dtowsand.getFolderName();

			folderStruct = new File(sourceFolderName.trim()
					+ folderStructure.trim() + "/" + fileName);

			File destParentDir = new File(absolutePath);
			destParentDir.mkdirs();
			fileManager.copyDirectory(folderStruct, new File(destParentDir,
					fileName));

			if (fileName.contains("common-tracking.pdf")) {
				isTrackingTableUploaded = true;
			}

			// addFiles(folderStruct,folderName);

			return (absolutePathLink.substring(1) + "/" + fileName);
		} else {
			return (absolutePathLink.substring(1));
		}
	}

	/*
	 * Add file at Publish Folder Path folderStruct = Source Folder File
	 * publishFolderStuct = Destination Folder File
	 */
	/*
	 * private void addFiles(File folderStruct,File publishFolderStuct){ try{
	 * 
	 * FileInputStream fin = new
	 * FileInputStream(folderStruct.getCanonicalFile());
	 * if(!publishFolderStuct.exists()) { publishFolderStuct.mkdirs(); }
	 * 
	 * FileOutputStream fout = new FileOutputStream(publishFolderStuct + "/" +
	 * fileName);
	 * 
	 * byte [] buf = new byte[1024 * 10]; int len;
	 * 
	 * while((len = fin.read(buf))> 0){ fout.write(buf,0,len); } fin.close();
	 * fout.close();
	 * 
	 * }catch(Exception e){ e.printStackTrace(); } }
	 */

	private void createSubFolders(String pathToCreate, String folderName) {

		File createsubfolder;
		createsubfolder = new File(pathToCreate + "/" + folderName);
		relativePathToCreate = pathToCreate + "/" + folderName;
		if (!createsubfolder.exists()) {
			createsubfolder.mkdirs();
		}
	}

	public Vector<Integer> getAllNodesofHistory() {
		return AllNodesofHistory;
	}

	public void setAllNodesofHistory(Vector<Integer> allNodesofHistory) {
		AllNodesofHistory = allNodesofHistory;
	}

	public String getCurrentSequenceNumber() {
		return currentSequenceNumber;
	}

	public void setCurrentSequenceNumber(String currentSequenceNumber) {
		this.currentSequenceNumber = currentSequenceNumber;
	}

	public char getProjectPublishType() {
		return projectPublishType;
	}

	public void setProjectPublishType(char projectPublishType) {
		this.projectPublishType = projectPublishType;
	}

	public int[] getLeafIds() {
		return leafIds;
	}

	public void setLeafIds(int[] leafIds) {
		this.leafIds = leafIds;
	}

	public boolean isNodeHavingClones(String nodename, int nodeId) {
		Vector<DTOWorkSpaceNodeDetail> repeatNodeAndSiblingsDtl = docMgmtInt
				.getChildNodeByParent(docMgmtInt.getParentNodeId(wsId, nodeId),
						wsId);
		Vector<DTOWorkSpaceNodeDetail> originalNodeWithAllclones = new Vector<DTOWorkSpaceNodeDetail>();
		for (int i = 0; i < repeatNodeAndSiblingsDtl.size(); i++) {
			DTOWorkSpaceNodeDetail currentSibling = repeatNodeAndSiblingsDtl
					.get(i);
			// System.out.println(currentSibling.getNodeName());
			if (currentSibling.getNodeName().equalsIgnoreCase(nodename)) {
				originalNodeWithAllclones.addElement(repeatNodeAndSiblingsDtl
						.get(i));
				if (originalNodeWithAllclones.size() > 1) {
					return true;
				}
			}
		}
		return false;
	}
}// main class end
