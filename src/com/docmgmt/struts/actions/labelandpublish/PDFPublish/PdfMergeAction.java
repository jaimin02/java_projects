package com.docmgmt.struts.actions.labelandpublish.PDFPublish;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.docmgmt.dto.DTOInternalLabelMst;
import com.docmgmt.dto.DTOPdfPublishDtl;
import com.docmgmt.dto.DTOSubmissionInfoDMSDtl;
import com.docmgmt.dto.DTOSubmittedWorkspaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.beans.WorkspaceDynamicNodeCheckTreeBean;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class PdfMergeAction extends ActionSupport {

	/**
	 * 
	 */
	private int errorcode = 0;
	private final int ERROR_IN_TEMPLATE_NODE = -10;
	private final int ERROR_IN_MERGE = -20;
	private final int ERROR_IN_FILE = -30;

	private static String ERROR_MESSAGE = "";

	private static final long serialVersionUID = 1L;
	public String[] documents;
	public String destinationFilePath;
	public String workSpaceId;
	public String extraHtmlCode = "";
	public int[] nodes;
	
	Vector<DTOSubmissionInfoDMSDtl> detailsfromDMS;

	Vector<DTOSubmissionInfoDMSDtl> detailsfromDMSForPublishPath;
	
	public String[] nodedetails;
	public String[] nodeDisplayName;
	public String[] attributes;

	InputStream inputStream;
	public String isPaging;
	public String isTitle;
	public String addBookmark;
	public String htmlContent ;
	public String subDtlId;
	public String submissionId;
	public String filename;
	
	public DTOWorkSpaceMst dtoWsMstInfo;
	
	public ArrayList<String> nodeDisplayNameList;
	static PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	FileManager fileManager = new FileManager();
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();

	ArrayList<String> logoNameList;
	public String logoFileName;
	String uploadLogoFileName;
	String logoSelection;
	public File uploadLogo;

	public ArrayList<DTOWorkSpaceNodeDetail> allNodesDtl;
	public String allowShrinkPdfs[];

	// Header Parameter
	public String addHeader;
	public String headerSettings[];
	public String allowHeaderPdfs[];
	public String headerTextPosition;
	public String header_logoPosition;
	public String nodeTitlePosition;
	public String headerTextColor;
	public String headerText1 = "", headerText2 = "";

	// Footer Parameter
	public String addFooter;
	public String footerSettings[];
	public String allowFooterPdfs[];
	public String footer_pageNumberStyle;
	public String footer_pageNumberPosition;
	public String footerContentPosition;
	public String footer_version = "";

	// Cover Page Parameter
	public String addCoverPages;
	public String coverpage_productname = "";
	public String coverpage_fontsize;
	public String coverpage_fontstyle;
	public String coverpage_fontname;

	public String coverpage_submittedby = "";
	public String coverpage_fontsizeby;
	public String coverpage_subbyfontname;
	public String coverpage_subbyfontstyle;

	public String coverpage_submittedto = "";
	public String coverpage_fontsizeto;
	public String coverpage_subtofontstyle;
	public String coverpage_subtofontname;

	// TOC Settings
	public String addToc;
	public String tocSettings[];
	public String tocPageNumberStyle;
	public String tocPath = "";
	public String tocFontSize;
	private boolean addWaterMark;

	private ArrayList<Integer> moduleWiseTotalPages = new ArrayList<Integer>();
	DTOSubmissionInfoDMSDtl dtosubmissioninfodmsdtl;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	public String submissionMode;
	public String submissionDesc;
	public String dateOfSubmission;
	public String currentSeqNumber;
	public ArrayList<DTOSubmissionInfoDMSDtl> subInfoDMSDtlList;
	public String remark;

	
	public int lastTranNo;
	DTOWorkSpaceNodeHistory nodeHis;
	@Override
	public String execute() {
		
		int userId= Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		Date date = null;
		
	     Date dt = new Date();
	     long time = dt.getTime();
	     
	     dateOfSubmission = dateOfSubmission+" "+time;
	     System.out.println(dateOfSubmission);
		DTOInternalLabelMst newLabel=createWorkspaceLabel(workSpaceId, userId);
		String newLabelId =newLabel.getLabelId();
		try{
			date = df.parse(dateOfSubmission);
			date.setTime(time);
			System.out.println(date);
		} catch (Exception e){e.printStackTrace();}
		Timestamp ts = new Timestamp(date.getTime());
		
					//Timestamp ts = Timestamp.valueOf(dateOfSubmission);
		DTOSubmissionInfoDMSDtl dtoSubInfoDMSDtl = new DTOSubmissionInfoDMSDtl();
		dtoSubInfoDMSDtl.setWorkspaceid(workSpaceId);
		dtoSubInfoDMSDtl.setCurrentSeqNumber(currentSeqNumber);
		dtoSubInfoDMSDtl.setRelatedSeqNumber(null);
		dtoSubInfoDMSDtl.setLabelId(newLabelId);
		dtoSubInfoDMSDtl.setSubmissionMode(submissionMode);
		dtoSubInfoDMSDtl.setSubmissionType("");
		dtoSubInfoDMSDtl.setSubmissionDesc(submissionDesc);
		dtoSubInfoDMSDtl.setDateOfSubmission(ts);
		dtoSubInfoDMSDtl.setSubmittedOn(new Timestamp(new Date().getTime()));
		dtoSubInfoDMSDtl.setConfirm('N');
		dtoSubInfoDMSDtl.setConfirmBy(0);
		dtoSubInfoDMSDtl.setRemark("");
		dtoSubInfoDMSDtl.setModifyBy(userId);
		//docMgmtImpl.insertSubmissionInfoDMSDtl(dtoSubInfoDMSDtl, 1);
		
		File baseLogoDir = propertyInfo.getDir("LogoFilePath");
		if (baseLogoDir == null) { // return "pathNotFound";
			return SUCCESS;
		}
		headerTextColor = "#002211";
		logoSelection = "N";
		if (logoSelection.equalsIgnoreCase("Y")) {
			if (uploadLogo != null && uploadLogo.exists()) {
				try {
					InputStream input = new FileInputStream(uploadLogo);
					OutputStream output = new FileOutputStream(baseLogoDir
							.getAbsolutePath()
							+ "/" + uploadLogoFileName);
					FileManager.copyStream(input, output);
					input.close();
					output.close();
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			if (!logoFileName.trim().equals("-1"))
				uploadLogoFileName = logoFileName;
			else
				uploadLogoFileName = "";
		}

		String headerText = "";
		headerText = headerText1;
		if (!headerText2.equals("") && !headerText.equals(""))
			headerText += "##" + headerText2; // ## is delimiter for product(s)
		// name
		else if (!headerText2.equals(""))
			headerText = headerText2;

		
		DTOPdfPublishDtl pdfpublishDTO = new DTOPdfPublishDtl();
		pdfpublishDTO.setWorkspaceId(workSpaceId);

		pdfpublishDTO.setAddCoverPages(addCoverPages);

		pdfpublishDTO.setCoverpage_productname(coverpage_productname);
		pdfpublishDTO.setCoverpage_submittedby(coverpage_submittedby);
		pdfpublishDTO.setCoverpage_submittedto(coverpage_submittedto);

		pdfpublishDTO.setCoverpage_fontname(coverpage_fontname);
		pdfpublishDTO.setCoverpage_fontsize(coverpage_fontsize);
		pdfpublishDTO.setCoverpage_fontstyle(coverpage_fontstyle);
		pdfpublishDTO.setCoverpage_subbyfontname(coverpage_subbyfontname);
		pdfpublishDTO.setCoverpage_fontsizeby(coverpage_fontsizeby);
		pdfpublishDTO.setCoverpage_subbyfontstyle(coverpage_subbyfontstyle);
		pdfpublishDTO.setCoverpage_subtofontname(coverpage_subtofontname);
		pdfpublishDTO.setCoverpage_subtofontstyle(coverpage_subtofontstyle);
		pdfpublishDTO.setCoverpage_fontsizeto(coverpage_fontsizeto);
		pdfpublishDTO.setTocFontSize(tocFontSize);
		pdfpublishDTO.setTocPageNumberStyle(tocPageNumberStyle);

		pdfpublishDTO.setAddFooter(addFooter);
		pdfpublishDTO.setAddHeader(addHeader);
		pdfpublishDTO.setFooter_pageNumberStyle(footer_pageNumberStyle);
		pdfpublishDTO.setFooter_pageNumberPosition(footer_pageNumberPosition);
		pdfpublishDTO.setFooterContentPosition(footerContentPosition);

		pdfpublishDTO.setNodeTitlePosition(nodeTitlePosition);
		pdfpublishDTO.setHeaderTextPosition(headerTextPosition);
		pdfpublishDTO.setHeaderTextColor(headerTextColor);
		pdfpublishDTO.setHeader_logoPosition(header_logoPosition);

		pdfpublishDTO.setProductname(headerText);
		pdfpublishDTO.setVersion(footer_version);

		pdfpublishDTO.setModifyBy(userId);

		if (docMgmtImpl.insertPdfPublishDetail(pdfpublishDTO, 1))// 1==insert
			System.out.println("Pdf Publish Detail Inserted..");

		Map<String, PdfReader> filesToMerge;
		filesToMerge = new LinkedHashMap<String, PdfReader>();
		PdfReader pdfReader = null;
		WorkspacePdfPublishService wps = new WorkspacePdfPublishService();
		allNodesDtl = new ArrayList<DTOWorkSpaceNodeDetail>();
		allNodesDtl = wps.workspacePublish(workSpaceId, nodes);
		List<String> headerSettingList = null;
		List<String> footerSettingList = null;
		List<String> tocSettingList = null;
		if (headerSettings != null) {
			headerSettingList = new ArrayList<String>(Arrays
					.asList(headerSettings));
		}
		if (footerSettings != null) {
			footerSettingList = new ArrayList<String>(Arrays
					.asList(footerSettings));
		}
		if (tocSettings != null) {
			tocSettingList = new ArrayList<String>(Arrays.asList(tocSettings));
		}
		if (addToc != null) {
			try {
				setNodeLevel(allNodesDtl.get(0), 0);
				if (errorcode != 0) {
					extraHtmlCode = ERROR_MESSAGE;
					inputStream = new ByteArrayInputStream(extraHtmlCode
							.getBytes());
					return SUCCESS;
				}
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		// TestTreeData test=new TestTreeData(allNodesDtl);
		// test.createTable();

		// allNodesDtl.get(0).getParentNodeId();

		int pdfCounter = 0;
		int totalPages = 0;
		String pdfStatus = "-1#No Error";
		// AllNodesofHistory =
		// docMgmtInt.getSelectedNodeDetailsForPdfPublish(workspaceId,nodes);

		ArrayList<File> pdfFilesToDelete = new ArrayList<File>();
		PropertyInfo propInfo = PropertyInfo.getPropInfo();
		//File baseDir = propInfo.getDir("MergedPDFpath");
		String baseDir = propInfo.getValue("MergedPDFpath");
		String blankpdfpath = propInfo.getValue("BlankPdfPath");
		String logopath = propInfo.getValue("PdfPublishHeaderLogo");
		logopath = baseLogoDir.getAbsolutePath() + "/" + uploadLogoFileName;
		// System.out.println("Upload Logo File Name->"
		// + baseLogoDir.getAbsolutePath() + "/" + uploadLogoFileName);

		ArrayList<DTOWorkSpaceNodeDetail> tocNodeDetails = new ArrayList<DTOWorkSpaceNodeDetail>();

		boolean found;
		int j;
		int modulewisepagecounter = 0;
		if (baseDir != null) {
			try {
				extraHtmlCode="<font size='2px' color='red'>Unable to generate dossier.....Please correct below file(s)</font><table width='100%' style='border:1px solid red;border-collapse: collapse;padding:5px' cellpadding='5'>";
				extraHtmlCode+="<tr style='border:1px solid red'><th>Node Name</th><th>File Name</th><th>Error Message</th></tr>";
				
				if (isTitle != null && isTitle.equalsIgnoreCase("yes")) {
					for (int i = 1; i < allNodesDtl.size(); i++) {

						int nodeId = allNodesDtl.get(i).getNodeId();
						String nodeDisplayName = allNodesDtl.get(i)
								.getNodeDisplayName().toString();
						String nodeName = allNodesDtl.get(i)
								.getNodeDisplayName().toString();

						// String FolderName =
						// allNodesDtl.get(i).getFolderName()
						// .toString();
						
						String attrValue = allNodesDtl.get(i)
								.getAttrValue().toString();
						found = false;
						int nodeLength = nodes.length;
						for (j = 0; j < nodeLength; j++) {
							if (nodes[j] == nodeId) {
								found = true;
								break;
							}
						}
						if (found) {

							// remove comment if want to generate file title
							// page in merged pdf
							/*
							 * filesToMerge.put("FileTitlePage" + (pdfCounter +
							 * 1) + ") " + FolderName, new PdfReader(
							 * blankpdfpath));
							 * 
							 * pdfCounter++; totalPages++;
							 * 
							 * TOCnodeDetails.add(allNodesDtl.get(i));
							 */
							try {
								
								filename = documents[j].substring(documents[j].lastIndexOf("/")+1);
								if(!filename.equalsIgnoreCase("No FIle"))
								{
									//PDDocument  document = PDDocument.load(documents[j]);
									PDDocument document = PDDocument.load(new File(documents[j]));
									    if(document.isEncrypted())
									    {
									    	errorcode=ERROR_IN_FILE;
											
											addErrorMessage(documents[j].substring(documents[j]
															.lastIndexOf("/") + 1), allNodesDtl.get(i)
															.getNodeDisplayName()
															.toString(),"Password Protected/Encrypted File" );
										
											continue;
									    }
									    document.close(); 
									 pdfReader = new PdfReader(documents[j]);
								}
								
								/*if(pdfReader!=null){*/
								if(attrValue.length()<=0 && pdfReader!=null){
										if(pdfReader.isEncrypted()){
											errorcode=ERROR_IN_FILE;
								
											addErrorMessage(documents[j].substring(documents[j]
													.lastIndexOf("/") + 1), allNodesDtl.get(i)
													.getNodeDisplayName()
													.toString(),"Password Protected/Encrypted File" );
								
									continue;
									}
								}
							}
							catch (Exception e) {
							
								errorcode=ERROR_IN_FILE;
								e.printStackTrace();
								addErrorMessage(documents[j].substring(documents[j]
												.lastIndexOf("/") + 1), allNodesDtl.get(i)
												.getNodeDisplayName()
												.toString(), e.getMessage());
								/*
								
								extraHtmlCode = "File( "
										+ documents[j].substring(documents[j]
												.lastIndexOf("/") + 1)
										+ " ) you are using to merge on node <font size='3px' color='red'>"
										+ allNodesDtl.get(i)
												.getNodeDisplayName()
												.toString()
										+ "</font> is either corrupt,not supported or not exist.<br>"
										+ "<font size='2px' color='red'>Error Message : "+e.getMessage()+"</font>";
								*/
								continue;
							}
							
							lastTranNo = docMgmtImpl.getMaxTranNo(workSpaceId, nodeId);
							
							filename = documents[j].substring(documents[j].lastIndexOf("/")+1);
							 nodeHis = docMgmtImpl
										.getWorkspaceNodeHistorybyTranNo(workSpaceId, nodeId, lastTranNo);
							if(!filename.equalsIgnoreCase("No FIle") && nodeHis.getFileName()!=null)
							{
							filesToMerge.put("file" + (pdfCounter + 1) + ") "+"[nodeId="+nodeId+"]"
									+ nodeDisplayName, pdfReader);
							pdfReader.close();

							pdfCounter++;
							totalPages += pdfReader.getNumberOfPages();

							modulewisepagecounter += pdfReader
									.getNumberOfPages();
							
							//modulewisepagecounter++;
							tocNodeDetails.add(allNodesDtl.get(i));
							//modulewisepagecounter++;
							//totalPages++;
							}
							/*else
							{
								tocNodeDetails.add(allNodesDtl.get(i));
								modulewisepagecounter++;
								totalPages++;
							}*/
								//tocNodeDetails.add(allNodesDtl.get(i));
								//totalPages++;
								//modulewisepagecounter++;
						
							continue;
						}
						filesToMerge.put("nodeTitlePage" + (pdfCounter + 1)
								+ ") " +"[nodeId="+nodeId+"]"+ nodeName, new PdfReader(blankpdfpath));
						pdfCounter++;
						modulewisepagecounter++;
						totalPages++;
					
						tocNodeDetails.add(allNodesDtl.get(i));
						if (allNodesDtl.get(i).getNodeLevel() == 1) {
							moduleWiseTotalPages.add(modulewisepagecounter);
							// System.out.println("Pages->"+modulewisepagecounter);
							modulewisepagecounter = 0;
						}
					}
					
					if(errorcode!=0)
					{
						extraHtmlCode+="</table>";
						System.out.println(extraHtmlCode);
						inputStream = new ByteArrayInputStream(
								extraHtmlCode.getBytes());
						return SUCCESS;
					}
					extraHtmlCode="";
					moduleWiseTotalPages.add(modulewisepagecounter + 1);
				} else {
					// if Add Node Title page not selected than only generate
					// File Title Page
					DTOWorkSpaceNodeDetail dtoNodeDetail;
					nodeDisplayNameList = new ArrayList<String>();
					int docLength = documents.length;
					for (int i = 0; i < docLength; i++) {
						dtoNodeDetail = new DTOWorkSpaceNodeDetail();
						dtoNodeDetail.setNodeDisplayName(nodeDisplayName[i]);
						filesToMerge.put("" + (i + 1) + "FileTitlePage)"
								+ nodeDisplayName[i].replace("#", ""),
								new PdfReader(blankpdfpath));
						totalPages++; // Title Page
						tocNodeDetails.add(dtoNodeDetail);
						pdfReader = new PdfReader(documents[i]);
						totalPages += pdfReader.getNumberOfPages();
						filesToMerge.put("" + (i + 1) + ") "
								+ nodeDisplayName[i].replace("#", ""),
								pdfReader);
						nodeDisplayNameList.add(nodeDisplayName[i]);
						tocNodeDetails.add(dtoNodeDetail);
						pdfCounter += 2;
					}
				}
				DTOWorkSpaceMst wsdesc = docMgmtImpl
						.getWorkSpaceDetail(workSpaceId);
				
						
				//detailsfromDMS  = docMgmtImpl.getDetailsforPublishPath(workSpaceId);
				detailsfromDMSForPublishPath  = docMgmtImpl.getmaxsubinfoDMSDtlId();
				long SubInfoDMSDtlId = detailsfromDMSForPublishPath.get(0).getSubInfoDMSDtlId();
				SubInfoDMSDtlId = SubInfoDMSDtlId+1;
				//System.out.println(detailsfromDMS.size());
				
				 //if(detailsfromDMS.size() != 0)
				 //{
					 //dtosubmissioninfodmsdtl = detailsfromDMS.get(0);
					/* destinationFilePath = baseDir + "/" +wsdesc.getLocationCode()+"/"+wsdesc.getTemplateDesc()+"/"+wsdesc.getWorkSpaceId()+"/"+
							+ dtosubmissioninfodmsdtl.getSubInfoDMSDtlId()+"/"+dtosubmissioninfodmsdtl.getCurrentSeqNumber()+"/"+wsdesc.getWorkSpaceDesc()
						+ ".pdf";*/
					 
					 /*destinationFilePath = baseDir +"/"+wsdesc.getTemplateDesc()+"/"+wsdesc.getWorkSpaceId()+"/"+
								+ dtosubmissioninfodmsdtl.getSubInfoDMSDtlId()+"/"+dtosubmissioninfodmsdtl.getCurrentSeqNumber()+"/"+wsdesc.getWorkSpaceDesc()
							+ ".pdf";*/
					 destinationFilePath = baseDir +"/"+wsdesc.getTemplateDesc()+"/"+wsdesc.getWorkSpaceId()+"/"+
								+ SubInfoDMSDtlId+"/"+dtoSubInfoDMSDtl.getCurrentSeqNumber()+"/"+wsdesc.getWorkSpaceDesc()
							+ ".pdf";
					 
									
					 File file = new File(destinationFilePath);
					 file.getParentFile().mkdirs();
					 file.createNewFile();
					 
					    /*dtosubmissioninfodmsdtl.setSubmissionPath(destinationFilePath);
					    dtosubmissioninfodmsdtl.setModifyBy(userId);
					    dtosubmissioninfodmsdtl.setSubInfoDMSDtlId(dtosubmissioninfodmsdtl.getSubInfoDMSDtlId());
						docMgmtImpl.insertSubmissionInfoDMSDtl(dtosubmissioninfodmsdtl, 2); 
						*/			
					PdfMergeWithTOC pdfmerger = new PdfMergeWithTOC(pdfpublishDTO,
							headerText1, headerText2, allowHeaderPdfs,
							allowFooterPdfs, allowShrinkPdfs, logopath,
							headerSettingList, footerSettingList, tocSettingList);
					pdfStatus = pdfmerger.createPdf(destinationFilePath,
							filesToMerge, blankpdfpath, addToc == null ? false
									: true, isPaging == null ? false : true,
							isTitle == null ? false : true,
							addBookmark == null ? false : true,
							nodeDisplayNameList, totalPages, nodes, workSpaceId,
							allNodesDtl, tocNodeDetails, moduleWiseTotalPages);
					//}
			/*	 else
				 {
					 extraHtmlCode += "<B><span style=\"color : red;\">Please Enter Publish And Submit Details...</span></B><br/>";
					return SUCCESS;
				
				 }*/
			
			} catch (Exception e) {
				addActionError("File Not Found - " + e.getMessage());
				// System.out.println("Error..."+e.getMessage());

				e.printStackTrace();
			}
		}

		if (baseDir != null) {
			try {

				/*
				 * for (int i = 0; i < documents.length; i++) {
				 * 
				 * pdfCounter++;
				 * 
				 * String pdfFilePath = documents[i];
				 * 
				 * if (isTitle != null && isTitle.equals("yes")) { pdfs.add(new
				 * FileInputStream(blankpdfpath));
				 * 
				 * if (nodeDisplayName[i].contains("##")) { filesToMerge.put(""
				 * + (i + 1) + "##Adding Node", new PdfReader(blankpdfpath));
				 * pdfs.add(new FileInputStream(blankpdfpath)); pdfCounter++;
				 * totalPages++;
				 * 
				 * } filesToMerge.put("" + (i + 1) + ":titlepage", new
				 * PdfReader(blankpdfpath)); pdfCounter++; totalPages++; }
				 * 
				 * if (isNonPDFFile(pdfFilePath)) { File convertedPdfFile =
				 * FileManager.convertDocument( new File(pdfFilePath), "pdf");
				 * pdfFilesToDelete.add(convertedPdfFile); pdfs.add(new
				 * FileInputStream(convertedPdfFile)); pdfReader = new
				 * PdfReader(convertedPdfFile.getPath()); filesToMerge.put("" +
				 * (i + 1) + ") " + nodeDisplayName[i], pdfReader); totalPages
				 * += pdfReader.getNumberOfPages();
				 * 
				 * } else { pdfReader = new PdfReader(documents[i]);
				 * filesToMerge.put("" + (i + 1) + ") " + nodeDisplayName[i],
				 * pdfReader); pdfs.add(new FileInputStream(pdfFilePath));
				 * totalPages += pdfReader.getNumberOfPages(); }
				 * 
				 * pdfStatus = i; }
				 */
				String status[]=pdfStatus.split("#");
				
				errorcode=Integer.parseInt(status[0]);
				ERROR_MESSAGE=status[1];
				
				if (errorcode == ERROR_IN_MERGE) {
					extraHtmlCode =ERROR_MESSAGE;

				} else if (errorcode == pdfCounter) {
					// addActionMessage("PDF Merged Successfully...To Download file <a href = \"openfile.do?fileWithPath="+destinationFilePath+"&attachment=true\">Click Here</a>");
					String path="";
					dtoSubInfoDMSDtl.setSubmissionPath(destinationFilePath);
					docMgmtImpl.insertSubmissionInfoDMSDtl(dtoSubInfoDMSDtl, 1);
					path = destinationFilePath.replace("\\", "/");
					extraHtmlCode += "<B><span style=\"color : green;\">PDF Merged Successfully...</span></B><br/><br/>To Download file <a href = \"openfile.do?fileWithPath="
							+ path
							+ "&attachment=true\" target=\"_blank\">Click Here</a>.";

					extraHtmlCode += "<BR> OR <BR> Follow This Path : "
							+ destinationFilePath;
				}
				else if(errorcode != pdfCounter)
				{
					 extraHtmlCode += "<B><span style=\"color : red;\">Please Enter Publish And Submit Details...</span></B><br/>";
				}
				else {
					throw new Exception();
				}
			} catch (Exception e) {
				e.printStackTrace();

				String[] ErrorFileStr = documents[errorcode].split("/");
				String errFileName = ErrorFileStr[ErrorFileStr.length - 1];
				String tranNo = ErrorFileStr[ErrorFileStr.length - 2];
				String nodeId = ErrorFileStr[ErrorFileStr.length - 3];
				// addActionError("Unable to generate Full Dossier...Error found in File <a href=\"javascript:void(0);\" onclick=\"showErrorFile('"+nodeId+"');\">"+errFileName+"</a>");
				extraHtmlCode += "<B><span style=\"color : red;\">Unable to generate Full Dossier...</span></B>Error found in File <a href=\"javascript:void(0);\" onclick=\"showErrorFile('"
						+ nodeId + "');\">" + errFileName + "</a>.";
				e.printStackTrace();
			} catch (Error er) {
				er.printStackTrace();
				extraHtmlCode += "<B><span style=\"color : red;\">Unable to generate Full Dossier...</span></B><br/><br/>Please contact System Administrator.Cause:"
						+ er.getMessage() + "</span></B>";
			} finally {
				for (File file : pdfFilesToDelete) {
					// Delete Files which were generated in order to merge PDF
					// files.
					file.delete();
				}
			}
		} else {
			extraHtmlCode += "<B><span style=\"color : red;\">Unable to generate Full Dossier...Path Not Found.";
		}
		
		extraHtmlCode += "<br><input type=\"button\" class=\"button\" value=\"Back\" onclick=\"subinfo();\">";
		inputStream = new ByteArrayInputStream(extraHtmlCode.getBytes());
		return SUCCESS;
	}

	private DTOInternalLabelMst createWorkspaceLabel(String workspaceId,int userId){
		DTOInternalLabelMst maxLabel = docMgmtImpl.getMaxWorkspaceLabel(workspaceId);
		//System.out.println("max label id ::"+maxLabel.getLabelNo());
		String maxLabelId = maxLabel.getLabelId();
		//Increment labelId by 1, e.g. L0001 to L0002
		String newLabelId = maxLabelId.substring(maxLabelId.length()-4);
		int newLabelIdInt = Integer.parseInt(newLabelId);
		newLabelIdInt =newLabelIdInt + 1;
		newLabelId = "000"+newLabelIdInt;
		newLabelId = maxLabelId.substring(0, maxLabelId.length()-4)+newLabelId.substring(newLabelId.length()-4);
		DTOInternalLabelMst dto = new DTOInternalLabelMst();
		dto.setWorkspaceId(workspaceId);
		dto.setLabelId(newLabelId);//labelId
		dto.setRemark("");//labelRemark
		dto.setModifyBy(userId);
		docMgmtImpl.createInternalLabel(dto);
		maxLabel = docMgmtImpl.getMaxWorkspaceLabel(workspaceId);
		//System.out.println("max label id ::"+maxLabel.getLabelNo());
		return maxLabel;
	}
	
	public String confirmDMSPublishedSeq()
	{
		int userId= Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		//here mode 0 = get max label number of that project.
		//and 1 = get max confirm label number.
		int maxConfirmLabelNo = docMgmtImpl.getMaxLabelNoForDMSPublish(workSpaceId, 1);
		ArrayList<DTOWorkSpaceNodeDetail> prevNodeList = docMgmtImpl.getDetailOfSubmission(workSpaceId, maxConfirmLabelNo);
		long subDtlIdLong = Long.parseLong(subDtlId);
		DTOSubmissionInfoDMSDtl dtoSubInfoDMSDtl = docMgmtImpl.getDMSSubmissionInfoBySubId(subDtlIdLong);
		int selectdLabelNo = dtoSubInfoDMSDtl.getLabelNo();
		ArrayList<DTOWorkSpaceNodeDetail> currNodeList = docMgmtImpl.getDetailOfSubmission(workSpaceId, selectdLabelNo);
		//Remove the common nodes from 'currNodeList'  
		nodesToConfirm(prevNodeList, currNodeList);
	
		//if there is no change is previous confirm sequence then current sequence can not be confirm.
		if(currNodeList.size() != 0)
		{
			// Update lastPublishedVersion in workspaceMst
			docMgmtImpl.updatePublishedVersion(workSpaceId);
			//set for confirm the sequence
			dtoSubInfoDMSDtl.setConfirm('Y');
			dtoSubInfoDMSDtl.setConfirmBy(userId);
			dtoSubInfoDMSDtl.setModifyBy(userId);
			docMgmtImpl.insertSubmissionInfoDMSDtl(dtoSubInfoDMSDtl, 2); //Update Published Version
			
			DTOWorkSpaceMst dtoWsMst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
			//Insert into SubmittedWorkspaceNodeDetail
			ArrayList<DTOSubmittedWorkspaceNodeDetail> subNodeDtlLst = new ArrayList<DTOSubmittedWorkspaceNodeDetail>();
			for (Iterator<DTOWorkSpaceNodeDetail> iterator = currNodeList.iterator(); iterator.hasNext();) {
				DTOWorkSpaceNodeDetail nDtl = iterator.next();
				DTOSubmittedWorkspaceNodeDetail dtoSubNodeDtl = new DTOSubmittedWorkspaceNodeDetail();
				dtoSubNodeDtl.setNodeId(nDtl.getNodeId());
				dtoSubNodeDtl.setIndexId("node-"+nDtl.getNodeId());//Setting the index value as per the ID attribute value of LEAF element is given in the publish logic.
				dtoSubNodeDtl.setWorkspaceId(workSpaceId);
				dtoSubNodeDtl.setSubmissionId(subDtlId);
				dtoSubNodeDtl.setLastPublishVersion(dtoWsMst.getLastPublishedVersion());
				subNodeDtlLst.add(dtoSubNodeDtl); 
			}
			docMgmtImpl.insertIntoSubmittedWorkspaceNodeDetail(subNodeDtlLst);
			htmlContent = "Sequence Confirmed Successfully.";
		}
		else{
			htmlContent = "No documents found to Confirm the Sequence.";
		}
		
		// This is to display the changed node
		/*
		for (int i = 0; i < nodesToSubmit.size(); i++) 
		{
			DTOWorkSpaceNodeDetail dto1 = nodesToSubmit.get(i);
			System.out.println(dto1.getNodeId()+"----"+dto1.getTranNo()+"---");
		}
		*/
		return SUCCESS;
	}
	
	public String LockSequence(){
		
		int userId= Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		long subDtlIdLong = Long.parseLong(subDtlId);
		DTOSubmissionInfoDMSDtl dtoSubInfoDMSDtl = docMgmtImpl.getDMSSubmissionInfoBySubId(subDtlIdLong);
		//dtoSubInfoDMSDtl.setStatusIndi('L');
		dtoSubInfoDMSDtl.setRemark(remark);
		dtoSubInfoDMSDtl.setModifyBy(userId);
		docMgmtImpl.insertSubmissionInfoDMSDtl(dtoSubInfoDMSDtl, 3);
		htmlContent = "Sequence Lock Successfully .";
		return SUCCESS;
	}
	
	public String UnLockSequence(){
		
		int userId= Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		long subDtlIdLong = Long.parseLong(subDtlId);
		DTOSubmissionInfoDMSDtl dtoSubInfoDMSDtl = docMgmtImpl.getDMSSubmissionInfoBySubId(subDtlIdLong);
		dtoSubInfoDMSDtl.setRemark(remark);
		dtoSubInfoDMSDtl.setModifyBy(userId);
		docMgmtImpl.insertSubmissionInfoDMSDtl(dtoSubInfoDMSDtl, 4);     //Pass Mode 4 For UnLovk Seq.
		htmlContent = "Sequence UnLock Successfully .";
		return SUCCESS;
	}


	public String getTreeNode()
	{
		
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userTypeCode = ActionContext.getContext().getSession().get("usertypecode").toString();
		
		ArrayList<Integer> nodeIdList = new ArrayList<Integer>();
		
		WorkspaceDynamicNodeCheckTreeBean treeBean = new WorkspaceDynamicNodeCheckTreeBean();
		treeBean.setUserType(userTypeCode);
		
		ArrayList<DTOSubmittedWorkspaceNodeDetail> subWsNodeList = docMgmtImpl.getSubmittedWorkspaceNodeList(workSpaceId, submissionId);
		System.out.println(subWsNodeList.size()+"----------get zero node");
		if(subWsNodeList.size() > 0)
		{
			for (int itrSubList = 0; itrSubList < subWsNodeList.size(); itrSubList++) 
			{
				DTOSubmittedWorkspaceNodeDetail dtoSubWsNodeDtl = subWsNodeList.get(itrSubList); 
				int nodeIdVal = dtoSubWsNodeDtl.getNodeId();
				nodeIdList.add(nodeIdVal); // this list is to generate the node wise HTML tree.
			}
			treeBean.setSelectedNodeTree(true);
			treeBean.setChkBoxForAllNodes(false);
			
			htmlContent = treeBean.getSelectedNodeTreeHtml(workSpaceId,userGroupCode,userCode,nodeIdList);
		}
		else
			htmlContent ="0";
		
		
		System.out.println(htmlContent);
			
		
		return SUCCESS;
	}
	
	private void nodesToConfirm(ArrayList<DTOWorkSpaceNodeDetail> prevNodeList,ArrayList<DTOWorkSpaceNodeDetail> currNodeList)
	{
		//This function check the tran number of the same node. if not change then remove from the confirm list.
		for (int itrPrev = 0; itrPrev < prevNodeList.size(); itrPrev++) 
		{
			DTOWorkSpaceNodeDetail dtoPrevNodeDtl = prevNodeList.get(itrPrev);
			for (int itrCurr = 0; itrCurr < currNodeList.size(); itrCurr++) 
			{
				DTOWorkSpaceNodeDetail dtoCurrNodeDtl = currNodeList.get(itrCurr);
				if(dtoCurrNodeDtl.getNodeId() == dtoPrevNodeDtl.getNodeId())
				{
					if(dtoCurrNodeDtl.getTranNo() == dtoPrevNodeDtl.getTranNo())
					{
						currNodeList.remove(itrCurr);
						itrCurr --;
					}
				}
			}
		}
	}

	public void addErrorMessage(String fileName,String nodeName,String errorMessage){
			
		extraHtmlCode+="<tr style='border:1px solid red'>";
		extraHtmlCode+="<td style='border:1px solid red'>"+nodeName+"</td>";
		extraHtmlCode+="<td style='border:1px solid red'>"+fileName+"</td>";
		extraHtmlCode+="<td style='border:1px solid red'>"+errorMessage+"</td>";
		extraHtmlCode+="</tr>";
	}
	
	public String publishDetailHistory(){
		
		publishDetailHistory = docMgmtImpl.getPublishDetailHistory(workSpaceId,subDtlId,currentSeqNumber);
		return SUCCESS;
		
	}
	
	
	public Vector publishDetailHistory;
	
	public String[] getAllowHeaderPdfs() {
		return allowHeaderPdfs;
	}

	public void setAllowHeaderPdfs(String[] allowHeaderPdfs) {
		this.allowHeaderPdfs = allowHeaderPdfs;
	}

	public String[] getAllowFooterPdfs() {
		return allowFooterPdfs;
	}

	public void setAllowFooterPdfs(String[] allowFooterPdfs) {
		this.allowFooterPdfs = allowFooterPdfs;
	}

	public String[] getAllowShrinkPdfs() {
		return allowShrinkPdfs;
	}

	public void setAllowShrinkPdfs(String[] allowShrinkPdfs) {
		this.allowShrinkPdfs = allowShrinkPdfs;
	}

	private boolean isNonPDFFile(String path) {

		String con = path.substring(path.lastIndexOf(".") + 1, path.length());
		if (con.equals("pdf")) {
			return false;
		} else {
			return true;
		}

	}

	public ArrayList<DTOWorkSpaceNodeDetail> selectChild(int nodeID, int level) {
		ArrayList<DTOWorkSpaceNodeDetail> list = new ArrayList<DTOWorkSpaceNodeDetail>();
		for (int i = 0; i < allNodesDtl.size(); i++) {
			if (allNodesDtl.get(i).getParentID() == nodeID) {

				list.add(allNodesDtl.get(i));
			}
		}
		return list;
	}

	public void setNodeLevel(DTOWorkSpaceNodeDetail dto, int level)
			throws DocumentException {
		// Test_Kenya_toc
		ArrayList<DTOWorkSpaceNodeDetail> childs = selectChild(dto.getNodeId(),
				level);

		dto.setNodeLevel(level);

		if (level > 1) {
			if (!dto.getNodeDisplayName().contains(" ")) {
				errorcode = ERROR_IN_TEMPLATE_NODE;
				ERROR_MESSAGE = "Problem occured while processing dossier. There might be problem in structure at node : <span style=\"color : red;\">"
						+ dto.getNodeDisplayName() + "</style>";
				return;
			}
		}
		for (DTOWorkSpaceNodeDetail obj : childs) {
			setNodeLevel(obj, level + 1);
		}
	}

	static String prefix(int level) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < level; i++) {
			s.append("----");
		}
		return s.toString();
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public String[] getDocuments() {
		return documents;
	}

	public void setDocuments(String[] documents) {
		this.documents = documents;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public static void main(String[] args) {

		PdfReader pdfReader = null;
		try {

			// Document.plainRandomAccess=true;
			// PdfMergeWithTOC.memoryStat();
			// pdfReader = new PdfReader(new
			// RandomAccessFileOrArray("D://big.pdf"),null);
			//		        
			// PdfMergeWithTOC.memoryStat();

		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		} finally {
			if (pdfReader != null)
				pdfReader.close();
		}

	}

	public String getIsPaging() {
		return isPaging;
	}

	public void setIsPaging(String isPaging) {
		this.isPaging = isPaging;
	}

	public File getUploadLogo() {
		return uploadLogo;
	}

	public void setUploadLogo(File uploadLogo) {
		this.uploadLogo = uploadLogo;
	}

	public String getUploadLogoFileName() {
		return uploadLogoFileName;
	}

	public void setUploadLogoFileName(String uploadLogoFileName) {
		this.uploadLogoFileName = uploadLogoFileName;
	}

	public ArrayList<String> getLogoNameList() {
		return logoNameList;
	}

	public void setLogoNameList(ArrayList<String> logoNameList) {
		this.logoNameList = logoNameList;
	}

	public String getLogoFileName() {
		return logoFileName;
	}

	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

	public String getLogoSelection() {
		return logoSelection;
	}

	public void setLogoSelection(String logoSelection) {
		this.logoSelection = logoSelection;
	}

}
