package com.docmgmt.server.webinterface.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.entities.Workspace.FontTypes;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionContext;

public class HtmlPublishTreeBean {

	private int userCode;
	private Vector nodeInfoVector;
	private String userType;
	private long nodeSize;
	String htmlSubmissionpath;
	public File uploadedFile;
	public File uploadFile;
	PropertyInfo propInfo = PropertyInfo.getPropInfo();
	OutputStream out; 

	DocMgmtImpl docMgmtImpl;
	ArrayList<DTOWorkSpaceNodeHistory> Created=new ArrayList<>();
	ArrayList<DTOWorkSpaceNodeHistory> Reviewed=new ArrayList<>();
	ArrayList<DTOWorkSpaceNodeHistory> Authorized=new ArrayList<>();
	ArrayList<DTOWorkSpaceNodeHistory> Approved=new ArrayList<>();
	public String userName;


	DTOWorkSpaceMst wsMst;
	
	String AllNodes="";
	FontTypes ft =  new FontTypes();
	
	public HtmlPublishTreeBean() {
		nodeInfoVector = new Vector();
		docMgmtImpl = new DocMgmtImpl();
	}

	public String generateHtmlTree(String workspaceID, int userGroupCode,
			int userCode, char nonPublishableNode,char Publish) throws DocumentException, IOException, ParseException {

		//wsMst = docMgmtImpl.getWorkSpaceDetail(workspaceID);
		wsMst = docMgmtImpl.getWorkSpaceDetailWSList(workspaceID);

		StringBuffer htmlSB = new StringBuffer();

		Vector workspaceTreeVector = getTreeNodes(workspaceID, userGroupCode,userCode, nonPublishableNode);

		int srno = 0;

		if (workspaceTreeVector.size() > 0) {
			htmlSB = new StringBuffer();
			TreeMap childNodeHS = new TreeMap();
			Hashtable nodeDtlHS = new Hashtable();
			Integer firstNode = null;
			nodeSize = workspaceTreeVector.size();
		

			for (int i = 0; i < workspaceTreeVector.size(); i++) {
				Object[] nodeRec = (Object[]) workspaceTreeVector.get(i);
				Integer parentNodeId = (Integer) nodeRec[2];
				Integer currentNodeId = (Integer) nodeRec[0];
	
				if (i == 0) {
					firstNode = currentNodeId;
				}
				if (childNodeHS.containsKey(parentNodeId)) {

					List childList = (List) childNodeHS.get(parentNodeId);
					childList.add(currentNodeId);
					if (!childNodeHS.containsKey(currentNodeId)) {
						childNodeHS.put(currentNodeId, new ArrayList());
					}
				} else {
					List childList = new ArrayList();
					childList.add(currentNodeId);
					childNodeHS.put(parentNodeId, childList);
					if (!childNodeHS.containsKey(currentNodeId)) {
						childNodeHS.put(currentNodeId, new ArrayList());
					}
				}

				nodeDtlHS.put(currentNodeId, new Object[] { nodeRec[1],
						nodeRec[2], nodeRec[3], nodeRec[4], nodeRec[5],
						nodeRec[6], nodeRec[7], nodeRec[8], nodeRec[9],
						nodeRec[10], nodeRec[11] });
			}

			Vector attributes = docMgmtImpl.getNodeAttrDetail(wsMst.getWorkSpaceId(), 1);
			String onMouseOver = "";

			/*
			 * for (int i = 0; i < attributes.size(); i++) {
			 * DTOWorkSpaceNodeAttrDetail dtoAttr
			 * =(DTOWorkSpaceNodeAttrDetail)attributes.get(i);
			 * 
			 * onMouseOver += "NAME="; onMouseOver += dtoAttr.getAttrName();
			 * onMouseOver += ", VALUE="; onMouseOver += dtoAttr.getAttrValue();
			 * 
			 * }
			 */
			// System.out.println(onMouseOver);

			if (firstNode != null) {
				Object[] firstNodeObj = (Object[]) nodeDtlHS.get(firstNode);

				String tempTr = "";
				String onMouseOut = "UnTip();";
				String image_a;
			
				for (int i = 0; i < attributes.size(); i++) {
					DTOWorkSpaceNodeAttrDetail dtoAttr = (DTOWorkSpaceNodeAttrDetail) attributes
							.get(i);
					if (dtoAttr.getAttrId() != -999) {
						tempTr += "<tr>";
						tempTr += "<td style=\\'font-size:8pt;background:#ffffff;\\'>";
						tempTr += dtoAttr.getAttrName();
						tempTr += ":<\\/td>";
						tempTr += "<td style=\\'font-size:8pt;background:#ffffff;\\'>";
						if (dtoAttr.getAttrValue().equals("")) {
							tempTr += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
						} else {
							tempTr += dtoAttr.getAttrValue();
						}
						tempTr += "<\\/td>";
						tempTr += "<\\/tr>";
					}
				}
				if (tempTr.equals("")) {
					onMouseOver = "Tip('<table border=\\'0\\' cellspacing=\\'5\\'><tr><th colspan=\\'2\\' style=\\'font-size:8pt;background:#ffcccc;\\'>No Attributes Found<\\/th><\\/tr>";
					onMouseOver += "<\\/table>')";
				} else {
					onMouseOver = "Tip('<table border=\\'0\\' cellspacing=\\'5\\'><tr><th colspan=\\'2\\' style=\\'font-size:8pt;background:#ffcccc;\\'>Attributes<\\/th><\\/tr>";
					onMouseOver += tempTr;
					onMouseOver += "<\\/table>')";
				}

				if (tempTr.equals("")) {
					image_a = "";
				} else {
					image_a = "<img src=\"util/images/2.jpg\" class=\"img_a\" style=\"cursor:pointer\" onMouseOver=\""
							+ onMouseOver
							+ "\" onMouseOut=\""
							+ onMouseOut
							+ "\"/>";
				}
				htmlSB.append("<ul><li style=\"list-style-type:square;\">"
						+ ((String) firstNodeObj[0]).replaceAll(" ", "&nbsp;")
						//+ "&nbsp;" + image_a + "</li>");
						+ "&nbsp;</li>");
				
			
				

				List childList = (List) childNodeHS.get(firstNode);
				htmlSB.append("<ul>");
				for (int i = 0; i < childList.size(); i++) {

					StringBuffer sb = getChildStructure((Integer) childList
							.get(i), childNodeHS, nodeDtlHS,Publish);
					
				//	AllNodes+="\n"+sb.toString();
					htmlSB.append(sb);
				}
				htmlSB.append("</ul>");
				htmlSB.append("</li></ul>");

			}

		}
		
		
		
		//System.out.println(AllNodes);
		if (htmlSB != null) {

			return htmlSB.toString();
		}

		return null;
	}

	private StringBuffer getChildStructure(Integer nodeId, TreeMap childNodeHS,
			Hashtable nodeDtlHS,char Publish) throws DocumentException, IOException, ParseException {

		StringBuffer htmlStringBuffer = new StringBuffer();
		StringBuffer errorStringBuffer = new StringBuffer();
		List childList = (List) childNodeHS.get(nodeId);
		Object[] nodeDtlObj = (Object[]) nodeDtlHS.get(nodeId);
		String displayName = (String) nodeDtlObj[2];
		Integer commentCount = (Integer) nodeDtlObj[8];
		Integer nodeNo = (Integer) nodeDtlObj[9];
		String NodeName = (String) nodeDtlObj[10];
		String FolderName = (String) nodeDtlObj[2];
		String image_a;

/*		if(displayName.contains(".docx") ||displayName.contains(".doc") || displayName.contains(".pdf") || 
			displayName.contains(".xlsx") ||displayName.contains(".xls") ||displayName.contains(".xsl") || 
			displayName.contains(".zip") || displayName.contains(".xml") ||displayName.contains(".ppt")   ){
		String fName=displayName;
		fName=fName.substring(0,fName.lastIndexOf("."));

		displayName=fName+".pdf";
		}*/

		/**
		 * Attr Detail
		 * */

		//System.out.println("Display Name="+displayName);
		
		Vector attributes = docMgmtImpl.getNodeAttrDetail(wsMst.getWorkSpaceId(), nodeId);
		String onMouseOver = "";
		String tempTr = "";
		String onMouseOut = "UnTip();";
		if (nodeId == 1) {
			for (int i = 0; i < attributes.size(); i++) {
				DTOWorkSpaceNodeAttrDetail dtoAttr = (DTOWorkSpaceNodeAttrDetail) attributes.get(i);

				if (dtoAttr.getAttrId() != -999) {
					tempTr += "<tr>";
					tempTr += "<td style=\\'font-size:8pt;background:#ffffff;\\'>";
					tempTr += dtoAttr.getAttrName();
					tempTr += ":<\\/td>";
					tempTr += "<td style=\\'font-size:8pt;background:#ffffff;\\'>";
					if (dtoAttr.getAttrValue().equals("")) {
						tempTr += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					} else {
						tempTr += dtoAttr.getAttrValue();
					}
					tempTr += "<\\/td>";

					tempTr += "<\\/tr>";
				}

			}
		}
		if (tempTr.equals("")) {

			onMouseOver = "Tip('<table border=\\'0\\' cellspacing=\\'5\\'><tr><th colspan=\\'2\\' style=\\'font-size:8pt;background:#ffcccc;\\'>No Attributes Found<\\/th><\\/tr>";

			onMouseOver += "<\\/table>')";
		} else {

			onMouseOver = "Tip('<table border=\\'0\\' cellspacing=\\'5\\'><tr><th colspan=\\'2\\' style=\\'font-size:8pt;background:#ffcccc;\\'>Attributes<\\/th><\\/tr>";
			onMouseOver += tempTr;
			onMouseOver += "<\\/table>')";
		}

		if (tempTr.equals("")) {
			image_a = "";
		} else {
			image_a = "<img src=\"util/images/2.jpg\" class=\"img_a\" style=\"cursor:pointer\" onMouseOver=\""
					+ onMouseOver + "\" onMouseOut=\"" + onMouseOut + "\"/>";
		}
		// System.out.println(onMouseOver);



		if (childList.size() > 0) {

			htmlStringBuffer.append("<li style=\"list-style-type:square;\">"
					+ displayName.replaceAll(" ", "&nbsp;") + "</li>");

			AllNodes+="\n"+displayName;
			//System.out.println(""+displayName);
			
			htmlStringBuffer.append("<ul>");
			
			for (int i = 0; i < childList.size(); i++) {

				htmlStringBuffer.append(getChildStructure((Integer) childList
						.get(i), childNodeHS, nodeDtlHS,Publish));
			}
			htmlStringBuffer.append("</ul>");
		} else {
			int lastTranNo = docMgmtImpl.getMaxTranNo(wsMst.getWorkSpaceId(),nodeId);

			/*DTOWorkSpaceNodeHistory nodeHis = docMgmtImpl
					.getWorkspaceNodeHistorybyTranNo(wsMst.getWorkSpaceId(),
							nodeId, lastTranNo);*/
			/*DTOWorkSpaceNodeHistory nodeHis = docMgmtImpl
					.getWorkspaceNodeHistorybyTranNoForHTMLPublish(wsMst.getWorkSpaceId(),
							nodeId, lastTranNo);*/
			DTOWorkSpaceNodeHistory nodeHis = docMgmtImpl
					.getWorkspaceNodeHistorybyTranNoForHTMLPublishWithTC(wsMst.getWorkSpaceId(),
							nodeId, lastTranNo);
			ArrayList<DTOWorkSpaceNodeDetail> parentNodeList = docMgmtImpl
					.getAllParentsNodes(wsMst.getWorkSpaceId(), nodeId);
			String leafFileDirectories = getParentNodeStr(parentNodeList);

			
			
			String PublishFilePath = "documents" + "/" + leafFileDirectories
					+ "/" + nodeHis.getFileName();

			// If stage=100(max stage) only then publish the node document/file

			// uncomment following comment if want to publish only approved
			// Files

			/*
			 * if(nodeHis.getStageId()!=100){ nodeHis.setFileName(null); }
			 */

			if (nodeHis.getFileName() == null || nodeHis.getFileName().equals("No File") ) {
				
				/*String Name=nodeHis.getFileName();
				int pos = Name.lastIndexOf(".");
				if (pos > 0) {
				   	Name = Name.substring(0, pos);
				   }
				displayName=Name+".pdf";*/
						
				htmlStringBuffer.append("<li style=\"list-style-type:square;\">"
								+ displayName.replaceAll(" ", "&nbsp;")
								+ "&nbsp;" + image_a + "</li>");
				
				;
			} else {
				/*
				 * htmlStringBuffer.append("<li style=\"list-style-type:square;\" ><a href=\"javascript:void(0);\" name=\""
				 * +PublishFilePath+"\" onclick=\"popup(this.name);\">" +
				 * displayName
				 * .replaceAll(" ","&nbsp;")+"</a>&nbsp"+image_a+"</li>");
				 */
				
				System.out.println("INodeId->"+nodeId + "( "+ displayName);
				AllNodes+="\nFinle--"+displayName;
				
				String Name=nodeHis.getFileName();
				int pos = Name.lastIndexOf(".");
				if (pos > 0) {
				   	Name = Name.substring(0, pos);
				   }
				displayName=Name+".pdf";
				htmlStringBuffer
						.append("<li style=\"list-style-type:square;\" ><a href=\"javascript:void(0);\" id=\""
								+ nodeNo
								+ "\" name=\""
								+ "documents" + "/" + leafFileDirectories
								+ "/" +displayName
								+ "\" onclick=\"load(this.id,this.name);\" >"
								+ displayName.replaceAll(" ", "&nbsp;")
								+ "</a>&nbsp" + image_a + "</li>");
				
				
			}

			/* Copy File Logic */

			if (nodeHis.getFileName() != null
					&& !nodeHis.getFileName().equals("")
					&& !nodeHis.getFileName().equals("No File")) {

				File sourceLocation = new File(wsMst.getBaseWorkFolder()
						+ nodeHis.getFolderName() + "/" + nodeHis.getFileName());
				File targetLocation = new File(htmlSubmissionpath + "/"
						+ PublishFilePath);
				File uploadedFile=new File(htmlSubmissionpath + "/"+ "documents" + "/" + leafFileDirectories
						+ "/" + nodeHis.getFileName());
				// System.out.println(targetLocation.getAbsolutePath());
				FileManager fileManager = new FileManager();
				fileManager.copyDirectory(sourceLocation, targetLocation);
			if(Publish== 'Y'){
				
				PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
				String showAttributesInPublishPage = propertyInfo.getValue("AttachAttributesInPublishPage");
				String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
				String Name=nodeHis.getFileName();
				int pos = Name.lastIndexOf(".");
				if (pos > 0) {
				   	Name = Name.substring(0, pos);
				   }
				displayName=Name+".pdf";
				//fName.substring(0,fName.lastIndexOf("."));
				String srcFileExt = FolderName.substring(FolderName.lastIndexOf(".") + 1);
				if(srcFileExt.equalsIgnoreCase("pdf")){
					System.out.println("Aleready Pdf");
					uploadFile=null;
					uploadFile=uploadedFile;
					DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetailWSList(wsMst.getWorkSpaceId());
					Vector<DTOWorkSpaceNodeDetail> getNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
					getNodeDetail = docMgmtImpl.getNodeDetail(wsMst.getWorkSpaceId(), nodeId);
					char nodeTypeIndi = getNodeDetail.get(0).getNodeTypeIndi();
					if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
					out = new FileOutputStream(new File(htmlSubmissionpath+"/documents" + "/" + leafFileDirectories
								+ "/" +"Final.pdf"));
					}
					}
				else{
					//uploadedFile=targetLocation;
					//GenericConvertor genericConv =new GenericConvertor();
					String response="";
					uploadFile=null;
					if(sourceLocation.exists()){
					/*uploadFile = FileManager.convertDocument(sourceLocation, "pdf");
					out = new FileOutputStream(new File(htmlSubmissionpath+"/documents" + "/" + leafFileDirectories
							+ "/" + Name+".pdf"));*/
					//FileManager.deleteFile(uploadedFile);
					String srcPath=wsMst.getBaseWorkFolder()+ nodeHis.getFolderName() + "/" + nodeHis.getFileName();
					/*srcPath=srcPath.replace("\\", "/");*/
					
					String destPath=(htmlSubmissionpath+"/documents" + "/" + leafFileDirectories
							+ "/"  + Name +"_Main.pdf");
					//genericConv.convert(srcPath,destPath);
				/*	String srcDocPath= srcPath.replace('/', '\\');
					String destDocPath= destPath.replace('/', '\\');
					
					String nSrcPath = " \""+srcDocPath+"\" ";
					String nDestPath = " \""+destDocPath+"\" ";
					
					int exitFlag=DocToPdf(nSrcPath,nDestPath);*/
										
					response=docMgmtImpl.convertDoc(srcPath,destPath);
					//response=docMgmtImpl.convertPdfToDoc(srcPath,destPath);
					
					/*String[] parts = response.split(":");
					if(parts[0].equalsIgnoreCase("error")){
						errorStringBuffer.append("<table><tr><td style=\"color:red\">"+displayName+"&nbsp; errorcode=</td>");
						errorStringBuffer.append ("<td style=\"color:red\">"+parts[2]+"</td></tr></table>");
						return errorStringBuffer;
					}*/
					String[] parts = response.split("#");				
					if(parts[0].equalsIgnoreCase("False")){
						errorStringBuffer.append("<table><tr><td style=\"color:red\">"+displayName+"&nbsp; errorcode=</td>");
						errorStringBuffer.append ("<td style=\"color:red\">"+parts[2]+"</td></tr></table>");
						return errorStringBuffer;
					}
					uploadFile = new File(htmlSubmissionpath+"/documents" + "/" + leafFileDirectories
							+ "/"  + Name +"_Main.pdf");
					out = new FileOutputStream(new File(htmlSubmissionpath+"/documents" + "/" + leafFileDirectories
							+ "/"  + Name +".pdf"));
					//FileManager.deleteFile(uploadedFile);
					
					//System.out.print("test");
					}
				}
				
				
				/*ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl
						.getPDFSignOffDocDetail(wsMst.getWorkSpaceId(), nodeId);*/
				
				Vector<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl.getProjectSignOffHistory(wsMst.getWorkSpaceId(), nodeId);
				//DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetail(wsMst.getWorkSpaceId());
				DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetailWSList(wsMst.getWorkSpaceId());
				
				Vector<DTOWorkSpaceNodeDetail> getNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
				
				getNodeDetail = docMgmtImpl.getNodeDetail(wsMst.getWorkSpaceId(), nodeId);
				char nodeTypeIndi = getNodeDetail.get(0).getNodeTypeIndi();
				
				File path = null;
				if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
				path=new File( htmlSubmissionpath+"/documents" + "/" + leafFileDirectories
						+ "/" +"Temp.pdf");}
				//File pathForAttribute=null;
				/*if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
					pathForAttribute=new File(htmlSubmissionpath+"/documents" + "/" + leafFileDirectories
							+ "/" +"/attr.pdf");
					OutputStream attrFile = new FileOutputStream(pathForAttribute);
				}*/

				//Document document=new Document();
				//PdfCopy copy = new PdfCopy(document, new FileOutputStream(path));
				//PdfReader pdfReader;
				//PdfStamper pdfStamper;
				String signatureFilePath=propertyInfo.getValue("signatureFile");
				if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
				Document d1 = new Document();
				OutputStream fos = new FileOutputStream(path);
				
			    PdfWriter pdfWriter = PdfWriter.getInstance(d1, fos);
			    Rectangle rectangle = new Rectangle(30, 30, 550, 800);
			    pdfWriter.setBoxSize("rectangle", rectangle);
				
			    d1.open();
				Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
				
				float[] widthsForTable = { 1.3f, 3 };
				PdfPTable table = new PdfPTable(widthsForTable);
				table.setWidthPercentage(100);
				//System.out.println(tempHistory.size()+"\n");
				String Company="SSPL";
				
				
				float[] widths = { 1, 3 };
				PdfPTable Htable = new PdfPTable(widths);
				Htable.setWidthPercentage(100);
				
				PdfPCell HPName = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Project Name"));
				HPName.setPadding(8f);
				Htable.addCell(HPName);
		        PdfPCell PName = new PdfPCell(new Phrase(docMgmtImpl.getWorkSpaceDetail(wsMst.getWorkSpaceId()).getWorkSpaceDesc()));
		        PName.setPadding(8f);
		        Htable.addCell(PName);
		        
		        PdfPCell HNname = new PdfPCell(new Phrase("Document Name  "));
		        HNname.setPadding(8f);
		        Htable.addCell(HNname);
		        
		        PdfPCell Nname = new PdfPCell(new Phrase(docMgmtImpl.getNodeDetail(wsMst.getWorkSpaceId(), nodeId).get(0).getNodeName()));
		        Nname.setPadding(8f);
		        Htable.addCell(Nname);
		        
		        Vector<DTOWorkSpaceNodeAttribute> AttrDtlForCoverPage = null;
		        AttrDtlForCoverPage=docMgmtImpl.getAttrDtlForCoverPage(wsMst.getWorkSpaceId(),nodeId);
		        if(AttrDtlForCoverPage.size()>0){
		        for(int i=0;i<AttrDtlForCoverPage.size();i++){
					//System.out.println("Name : "+AttrDtl.get(i).getAttrName()+"     Value : " + AttrDtl.get(i).getAttrValue());
					
					PdfPCell cell5 = new PdfPCell(new Phrase(AttrDtlForCoverPage.get(i).getAttrName()));
		            PdfPCell cell6 = new PdfPCell(new Phrase(AttrDtlForCoverPage.get(i).getAttrValue()));
		            cell5.setPadding(8f);
		            Htable.addCell(cell5);
		            cell6.setPadding(8f);
		            Htable.addCell(cell6);
		            Htable.completeRow();
		        	}
		        }
		        
		        
		        PdfPCell HSpace = new PdfPCell();
		        HSpace.setColspan(2);
		        HSpace.setPadding(8f);
		        HSpace.setLeading(1f, 1.4f);
		        HSpace.setBorder(rectangle.NO_BORDER);
		        Htable.addCell(HSpace);

		        // Add cells in table
		       /* table.addCell(HPName);
		        table.addCell(PName);
		        table.addCell(HNname);
		        table.addCell(Nname);*/
				
		        PdfPCell cell1=new PdfPCell();
		     if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
					//System.out.println("Project Type : "+wsDetail.getProjectCode());
				cell1 = new PdfPCell(new Phrase(Element.ALIGN_CENTER,"Sign Off"));
				cell1.setColspan(4);
				cell1.setPadding(2f);
				cell1.setLeading(1f, 1.4f);
		        

				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setBackgroundColor(new BaseColor(255,255,255));
				//cell1.setBorderWidthLeft(2f);
				//cell1.setBorderWidthTop(1.5f);
				//cell1.setBorderWidthBottom(2.5f);
				//cell1.setBorderWidthRight(3f);
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setVerticalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell1);
			}
				PdfPCell Space = new PdfPCell();
				Space.setColspan(4);
				Space.setPadding(15f);
				Space.setLeading(1f, 1.4f);

				/*Space.setBorder(Rectangle.NO_BORDER);
				Space.setBackgroundColor(new BaseColor(255,255,255));
				new PdfPCell(new Phrase(" "));
			    table.addCell(Space);
			    table.completeRow();*/
				
			    Created.clear();
			    Reviewed.clear();
			    Authorized.clear();
			    Approved.clear();
			   
				for(int j=0;j<tempHistory.size();j++){
					
					if(tempHistory.get(j).getStageDesc().equalsIgnoreCase("Created")){
						Created.add(tempHistory.get(j));
					}
					else if(tempHistory.get(j).getStageDesc().equalsIgnoreCase("Reviewed")){
						Reviewed.add(tempHistory.get(j));
					}
					else if(tempHistory.get(j).getStageDesc().equalsIgnoreCase("Authorized")){
						Authorized.add(tempHistory.get(j));
					}
					else if(tempHistory.get(j).getStageDesc().equalsIgnoreCase("Approved")){
						Approved.add(tempHistory.get(j));
					}
				}
					/*System.out.println(Created.size());
					System.out.println(Reviewed.size());
					System.out.println(Authorized.size());
					System.out.println(Approved.size());*/
//			if(!wsDetail.getProjectCode().equals("0003")){	
				for(int c=0;c<Created.size();c++){
					if(c==0){
						PdfPCell cell11 = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Created By : "));
						cell11.setColspan(2);
						cell11.setBorder(0);
						cell11.setPadding(20f);
						cell11.setLeading(1f, 1.4f);
						cell1.setBorder(Rectangle.NO_BORDER);
						cell1.setBackgroundColor(new BaseColor(255,255,255));
						cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell11.setVerticalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell11);
						
						//PdfPCell Dc = new PdfPCell(new Phrase("Name"));
				        PdfPCell Mo = new PdfPCell(new Phrase("Signature"));
				       // Dc.setHorizontalAlignment(Element.ALIGN_CENTER);
				       // Dc.setBackgroundColor(BaseColor.LIGHT_GRAY);
				        Mo.setHorizontalAlignment(Element.ALIGN_CENTER);
				        Mo.setBackgroundColor(BaseColor.LIGHT_GRAY);
				        Mo.setFixedHeight(20);
				        //table.addCell(Dc);
				        Mo.setColspan(2);
				        table.addCell(Mo);
						
				        table.completeRow();
					}
						//PdfPCell title = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));
						PdfPCell title = new PdfPCell();
						PdfPCell mOn = new PdfPCell();
						//PdfPCell cell10 = new PdfPCell();      
				    	
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			            Date date1 = sdf.parse("1900-01-01 00:00:00.000");
			            Date date2 = sdf.parse(Created.get(c).getModifyOn().toString());
			            String style = null;
			            String imageFile;
			            Image image = null;
			            if(Created.get(c).getStyle()!=null && !Created.get(c).getStyle().isEmpty()){
			            	style=Created.get(c).getStyle();
			            }else{
			            imageFile =signatureFilePath+"/"+Created.get(c).getFilePath()+"/"+Created.get(c).getFileName();
			            //String imageFile = "//90.0.0.15//KnowledgeNET-CSV//userSignature//3//18//sign1.png";
			            File f=new File(imageFile);
			            
			            if(f.exists())
		      	        	{image = Image.getInstance(imageFile);}
			            }
			            if(date1.before(date2)){
			            	//if(Created.get(c).getSignId() != null){
			            	if(image!= null){
			            		image.scaleToFit(150f, 50f); //Scale image's width and height
			            	}
						/*mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));*/}
			            else{
			            	mOn = new PdfPCell(new Phrase(""));
			            }
			            title.setVerticalAlignment(Element.ALIGN_CENTER);
				        title.setHorizontalAlignment(Element.ALIGN_CENTER);
				        mOn.setHorizontalAlignment(Element.ALIGN_CENTER);
				        
				       // table.addCell(title);
				       // PdfPCell imgCell = new PdfPCell(image, true);
				       // table.addCell(imgCell);   
				    	//if(Created.get(c).getSignId() != null){
				        if(image!= null){
					        Paragraph paragraph;
					        if(Created.get(c).getCountryCode().equalsIgnoreCase("IND")){
					        	paragraph = new Paragraph(Created.get(c).getISTDateTime()); 
						     }
						     else{
						    	 paragraph = new Paragraph(Created.get(c).getESTDateTime());
						    }
					        style=Created.get(c).getStyle();
					        Font font1 = null;
					        Paragraph p = null;
					        			      
					        font1=ft.registerExternalFont(style,"No");
					        /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
					        p=new Paragraph(Created.get(c).getUserName(),font1);
					        image.setAlignment(image.ALIGN_CENTER);
					        p.setAlignment(p.ALIGN_CENTER);
					        paragraph.setAlignment(paragraph.ALIGN_CENTER);
					        mOn.addElement(image);
					    	mOn.setPaddingTop(5f);
					    	mOn.setPaddingLeft(1f);
					    	mOn.setPaddingRight(1f);
					      //  mOn.addElement(p);
					        //mOn.addElement(paragraph);
					        mOn.setFixedHeight(100);}
				    	else{
					    		//mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));
				    		Font font1=null; 
				    		style=Created.get(c).getStyle();
				    		if(style!=null)
				    			font1 = ft.registerExternalFont(style,"No");
						    if(style!=null && !style.isEmpty())
					        	font1=ft.registerExternalFont(style,"No");
					        else
					        	font1= FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);
							    Paragraph p = new Paragraph(Created.get(c).getUserName(),font1);
							    
							    /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
					    		//mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()));
					    		mOn.addElement(p);
					    		mOn.setHorizontalAlignment(Element.ALIGN_CENTER);
					    		mOn.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    		mOn.setPaddingTop(-10f);
							    mOn.setPaddingLeft(10f);
							    mOn.setPaddingRight(1f);
							    
							    /*mOn.setBorderWidthTop(2f);
							    mOn.setBorderWidthLeft(2f);
							    //mOn.setBorderWidthRight(2f);
						        mOn.setBorderWidthBottom(2f);
							    mOn.setBorderColorLeft(BaseColor.RED);
							    mOn.setBorderColorTop(BaseColor.RED);
							  mOn.setUseBorderPadding(true);
							    mOn.setBorderColorBottom(BaseColor.RED);*/
				    	}
				    	mOn.setBorderWidthRight(0f);
				    	mOn.setFixedHeight(50f);
				    	//mOn.setPaddingBottom(20);
				    	mOn.setHorizontalAlignment(Element.ALIGN_LEFT);
					    //mOn.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    	
				        table.addCell(mOn);
				        
				        //Font font1 = null;
				        Paragraph p = null;
				        String style1 = "Brush Script MT";
				        //if(Created.get(c).getSignId() != null){
				        if(image!= null){
				        	//style=Created.get(c).getStyle();
				        	  image.setAlignment(image.ALIGN_CENTER);
				        } 
				        		      
				        //font1=ft.registerExternalFont(style1);
				        /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
				        //p=new Paragraph(Created.get(c).getUserName(),font1);
				        Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
				        p=new Paragraph("Name: "+Created.get(c).getUserName(),font2);
				      
				        p.setAlignment(p.ALIGN_LEFT);
				        Paragraph paragraph;
				        if(Created.get(c).getCountryCode().equalsIgnoreCase("IND")){
					    	  paragraph = new Paragraph("Date: "+Created.get(c).getISTDateTime(),font2);  
					     }
					     else{
					          paragraph = new Paragraph("Date: "+Created.get(c).getESTDateTime(),font2);  
					    }
				      
				 	        paragraph.setAlignment(paragraph.ALIGN_LEFT);
				        title.setBorderWidthLeft(0f);
				      //  title.setHorizontalAlignment(Element.ALIGN_LEFT);
				        title.setVerticalAlignment(Element.ALIGN_MIDDLE);
				        title.setFixedHeight(60f);
				        title.addElement(p);
				        Paragraph role = new Paragraph("Role: "+Created.get(c).getRoleName(),font2);
				        title.addElement(role);
				        title.addElement(paragraph);
				        Paragraph signId = new Paragraph("Sign Id: "+Created.get(c).getSignId(),font2);
				        title.addElement(signId);
				        
				        /*float[] widthsForTbl = { 200f, 50 };
				        PdfPTable testTable = new PdfPTable(widthsForTbl);
				        PdfPCell c2;
				        //testTable.addCell(p+"\n"+paragraph+"\n"+signId);
				        testTable.addCell("Name: "+Created.get(c).getUserName()+"\n"+"Date: "+Created.get(c).getISTDateTime()+"\n"+"Sign Id: "+Created.get(c).getSignId());
				        testTable.addCell("bbbb");

				        c2 = new PdfPCell (testTable);//this line made the difference
				        c2.setPadding(0);
				        table.addCell(c2);*/
				        /*title.setBorderWidthTop(2f);
				        //title.setBorderWidthLeft(2f);
				        title.setBorderWidthRight(2f);
				        title.setBorderWidthBottom(2f);
				        title.setBorderColorRight(BaseColor.RED);
				        title.setBorderColorTop(BaseColor.RED);
				        title.setBorderColorBottom(BaseColor.RED);*/
				        title.setPaddingTop(-5f);
				        title.setPaddingRight(1f);
				        table.addCell(title);
				        table.completeRow();
				        
				        PdfPCell Space1 = new PdfPCell();
				        Space1.setColspan(4);
				        Space1.setPadding(20f);
				        Space1.setLeading(1f, 1.4f);
				        System.out.println("Created");
					}
				for(int r=0;r<Reviewed.size();r++){
					if(r==0){
						PdfPCell cell11 = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Reviewed By : "));
						cell11.setColspan(2);
						cell11.setBorder(0);
						cell11.setPadding(20f);
						cell11.setLeading(1f, 1.4f);
						cell1.setBorder(Rectangle.NO_BORDER);
						cell1.setBackgroundColor(new BaseColor(255,255,255));
						cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell11.setVerticalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell11);
						
						//PdfPCell Dc = new PdfPCell(new Phrase("Name"));
				        PdfPCell Mo = new PdfPCell(new Phrase("Signature"));
				       // Dc.setHorizontalAlignment(Element.ALIGN_CENTER);
				       // Dc.setBackgroundColor(BaseColor.LIGHT_GRAY);
				        Mo.setHorizontalAlignment(Element.ALIGN_CENTER);
				        Mo.setBackgroundColor(BaseColor.LIGHT_GRAY);
				        Mo.setFixedHeight(20);
				        //table.addCell(Dc);
				        Mo.setColspan(2);
				        table.addCell(Mo);
						
				        table.completeRow();
					}
						//PdfPCell title = new PdfPCell(new Phrase(Reviewed.get(r).getUserName()+" \n "+Reviewed.get(r).getISTDateTime()));
						PdfPCell title = new PdfPCell();
						PdfPCell mOn = new PdfPCell();
						//PdfPCell cell10 = new PdfPCell();      
				    	
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			            Date date1 = sdf.parse("1900-01-01 00:00:00.000");
			            Date date2 = sdf.parse(Reviewed.get(r).getModifyOn().toString());
			            String style = null;
			            String imageFile;
			            Image image = null;
			            if(Reviewed.get(r).getStyle()!=null && !Reviewed.get(r).getStyle().isEmpty()){
			            	style=Reviewed.get(r).getStyle();
			            }else{
			            imageFile =signatureFilePath+"/"+Reviewed.get(r).getFilePath()+"/"+Reviewed.get(r).getFileName();
			            //String imageFile = "//90.0.0.15//KnowledgeNET-CSV//userSignature//3//18//sign1.png";
			            File f=new File(imageFile);
			            
			            if(f.exists())
		      	        	{image = Image.getInstance(imageFile);}
			            }
			            if(date1.before(date2)){
			            	//if(Reviewed.get(r).getSignId() != null){
			            	if(image!= null){
			            		image.scaleToFit(150f, 50f); //Scale image's width and height
			            	}
						/*mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));*/}
			            else{
			            	mOn = new PdfPCell(new Phrase(""));
			            }
			            title.setVerticalAlignment(Element.ALIGN_CENTER);
				        title.setHorizontalAlignment(Element.ALIGN_CENTER);
				        mOn.setHorizontalAlignment(Element.ALIGN_CENTER);
				        
				       // table.addCell(title);
				       // PdfPCell imgCell = new PdfPCell(image, true);
				       // table.addCell(imgCell);   
				    	//if(Reviewed.get(r).getSignId() != null){
				        if(image!= null){
					        Paragraph paragraph;
					        if(Reviewed.get(r).getCountryCode().equalsIgnoreCase("IND")){
					        	paragraph = new Paragraph(Reviewed.get(r).getISTDateTime()); 
						     }
						     else{
						    	 paragraph = new Paragraph(Reviewed.get(r).getESTDateTime());
						    }
					        style=Reviewed.get(r).getStyle();
					        Font font1 = null;
					        Paragraph p = null;
					        			      
					        font1=ft.registerExternalFont(style,"No");
					        /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
					        p=new Paragraph(Reviewed.get(r).getUserName(),font1);
					        image.setAlignment(image.ALIGN_CENTER);
					        p.setAlignment(p.ALIGN_CENTER);
					        paragraph.setAlignment(paragraph.ALIGN_CENTER);
					        mOn.addElement(image);
					    	mOn.setPaddingTop(5f);
					    	mOn.setPaddingLeft(1f);
					    	mOn.setPaddingRight(1f);
					      //  mOn.addElement(p);
					        //mOn.addElement(paragraph);
					        mOn.setFixedHeight(100);}
				    	else{
					    		//mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));
				    		Font font1=null; 
				    		style=Reviewed.get(r).getStyle();
				    		if(style!=null)
				    			font1 = ft.registerExternalFont(style,"No");
						    if(style!=null && !style.isEmpty())
					        	font1=ft.registerExternalFont(style,"No");
					        else
					        	font1= FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);
							    Paragraph p = new Paragraph(Reviewed.get(r).getUserName(),font1);
							    
							    /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
					    		//mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()));
					    		mOn.addElement(p);
					    		mOn.setHorizontalAlignment(Element.ALIGN_CENTER);
					    		mOn.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    		mOn.setPaddingTop(-10f);
							    mOn.setPaddingLeft(10f);
							    mOn.setPaddingRight(1f);
				    	}
				    	mOn.setBorderWidthRight(0f);
				    	mOn.setFixedHeight(50f);
				    	mOn.setHorizontalAlignment(Element.ALIGN_LEFT);
					    //mOn.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    	
				        table.addCell(mOn);
				        
				        //Font font1 = null;
				        Paragraph p = null;
				        String style1 = "Brush Script MT";
				        //if(Reviewed.get(r).getSignId() != null){
				        if(image!= null){
				        	//style=Reviewed.get(r).getStyle();
				            image.setAlignment(image.ALIGN_CENTER);
				        } 
				        
				        			      
				      //font1=ft.registerExternalFont(style1);
				        /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
				        //p=new Paragraph(Created.get(c).getUserName(),font1);
				        Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
				        p=new Paragraph("Name: "+Reviewed.get(r).getUserName(),font2);
				      
				        p.setAlignment(p.ALIGN_LEFT);
				        Paragraph paragraph;
				        if(Reviewed.get(r).getCountryCode().equalsIgnoreCase("IND")){
					    	  paragraph = new Paragraph("Date: "+Reviewed.get(r).getISTDateTime(),font2);  
					     }
					     else{
					          paragraph = new Paragraph("Date: "+Reviewed.get(r).getESTDateTime(),font2);  
					    }
				        paragraph.setAlignment(paragraph.ALIGN_LEFT);
				        title.setBorderWidthLeft(0f);
				      //  title.setHorizontalAlignment(Element.ALIGN_LEFT);
				        title.setVerticalAlignment(Element.ALIGN_MIDDLE);
				        title.setFixedHeight(60f);
				        title.addElement(p);
				        Paragraph role = new Paragraph("Role: "+Reviewed.get(r).getRoleName(),font2);
				        title.addElement(role);
				        title.addElement(paragraph);
				        Paragraph signId = new Paragraph("Sign Id: "+Reviewed.get(r).getSignId(),font2);
				        title.addElement(signId);
				        
				        title.setPaddingTop(-5f);
				        title.setPaddingRight(1f);
				        table.addCell(title);
				        table.completeRow();
				        
				        PdfPCell Space1 = new PdfPCell();
				        Space1.setColspan(4);
				        Space1.setPadding(20f);
				        Space1.setLeading(1f, 1.4f);
				        System.out.println("Reviewed");
					}
				
				for(int a=0;a<Approved.size();a++){
					if(a==0){
						PdfPCell cell11 = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Approved By : "));
						cell11.setColspan(2);
						cell11.setBorder(0);
						cell11.setPadding(20f);
						cell11.setLeading(1f, 1.4f);
						cell1.setBorder(Rectangle.NO_BORDER);
						cell1.setBackgroundColor(new BaseColor(255,255,255));
						cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell11.setVerticalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell11);
						
						//PdfPCell Dc = new PdfPCell(new Phrase("Name"));
				        PdfPCell Mo = new PdfPCell(new Phrase("Signature"));
				       // Dc.setHorizontalAlignment(Element.ALIGN_CENTER);
				       // Dc.setBackgroundColor(BaseColor.LIGHT_GRAY);
				        Mo.setHorizontalAlignment(Element.ALIGN_CENTER);
				        Mo.setBackgroundColor(BaseColor.LIGHT_GRAY);
				        Mo.setFixedHeight(20);
				        //table.addCell(Dc);
				        Mo.setColspan(2);
				        table.addCell(Mo);
						
				        table.completeRow();
					}
						//PdfPCell title = new PdfPCell(new Phrase(Reviewed.get(r).getUserName()+" \n "+Reviewed.get(r).getISTDateTime()));
						PdfPCell title = new PdfPCell();
						PdfPCell mOn = new PdfPCell();
						//PdfPCell cell10 = new PdfPCell();      
				    	
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			            Date date1 = sdf.parse("1900-01-01 00:00:00.000");
			            Date date2 = sdf.parse(Approved.get(a).getModifyOn().toString());
			            String style = null;
			            String imageFile;
			            Image image = null;
			            if(Approved.get(a).getStyle()!=null && !Approved.get(a).getStyle().isEmpty()){
			            	style=Approved.get(a).getStyle();
			            }else{
			            imageFile =signatureFilePath+"/"+Approved.get(a).getFilePath()+"/"+Approved.get(a).getFileName();
			            //String imageFile = "//90.0.0.15//KnowledgeNET-CSV//userSignature//3//18//sign1.png";
			            File f=new File(imageFile);
			            
			            if(f.exists())
		      	        	{image = Image.getInstance(imageFile);}
			            }
			            if(date1.before(date2)){
			            	//if(Reviewed.get(r).getSignId() != null){
			            	if(image!= null){
			            		image.scaleToFit(150f, 50f); //Scale image's width and height
			            	}
						/*mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));*/}
			            else{
			            	mOn = new PdfPCell(new Phrase(""));
			            }
			            title.setVerticalAlignment(Element.ALIGN_CENTER);
				        title.setHorizontalAlignment(Element.ALIGN_CENTER);
				        mOn.setHorizontalAlignment(Element.ALIGN_CENTER);
				        
				       // table.addCell(title);
				       // PdfPCell imgCell = new PdfPCell(image, true);
				       // table.addCell(imgCell);   
				    	//if(Reviewed.get(r).getSignId() != null){
				        if(image!= null){
					        Paragraph paragraph;
					        if(Approved.get(a).getCountryCode().equalsIgnoreCase("IND")){
					        	paragraph = new Paragraph(Approved.get(a).getISTDateTime()); 
						     }
						     else{
						    	 paragraph = new Paragraph(Approved.get(a).getESTDateTime());
						    }
					        style=Approved.get(a).getStyle();
					        Font font1 = null;
					        Paragraph p = null;
					        			      
					        font1=ft.registerExternalFont(style,"No");
					        /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
					        p=new Paragraph(Approved.get(a).getUserName(),font1);
					        image.setAlignment(image.ALIGN_CENTER);
					        p.setAlignment(p.ALIGN_CENTER);
					        paragraph.setAlignment(paragraph.ALIGN_CENTER);
					        mOn.addElement(image);
					    	mOn.setPaddingTop(5f);
					    	mOn.setPaddingLeft(1f);
					    	mOn.setPaddingRight(1f);
					      //  mOn.addElement(p);
					        //mOn.addElement(paragraph);
					        mOn.setFixedHeight(100);}
				    	else{
					    		//mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()+" \n "+Created.get(c).getISTDateTime()));
				    		Font font1=null; 
				    		style=Approved.get(a).getStyle();
				    		if(style!=null)
				    			font1 = ft.registerExternalFont(style,"No");
						    if(style!=null && !style.isEmpty())
					        	font1=ft.registerExternalFont(style,"No");
					        else
					        	font1= FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);
							    Paragraph p = new Paragraph(Approved.get(a).getUserName(),font1);
							    
							    /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
					    		//mOn = new PdfPCell(new Phrase(Created.get(c).getUserName()));
					    		mOn.addElement(p);
					    		mOn.setHorizontalAlignment(Element.ALIGN_CENTER);
					    		mOn.setVerticalAlignment(Element.ALIGN_MIDDLE);
					    		mOn.setPaddingTop(-10f);
							    mOn.setPaddingLeft(10f);
							    mOn.setPaddingRight(1f);
				    	}
				    	mOn.setBorderWidthRight(0f);
				    	mOn.setFixedHeight(50f);
				    	mOn.setHorizontalAlignment(Element.ALIGN_LEFT);
					    //mOn.setVerticalAlignment(Element.ALIGN_MIDDLE);
				    	
				        table.addCell(mOn);
				        
				        //Font font1 = null;
				        Paragraph p = null;
				        String style1 = "Brush Script MT";
				        //if(Reviewed.get(r).getSignId() != null){
				        if(image!= null){
				        	//style=Reviewed.get(r).getStyle();
				            image.setAlignment(image.ALIGN_CENTER);
				        } 
				        
				        			      
				      //font1=ft.registerExternalFont(style1);
				        /*FontFactory.getFont("Calibri", 12f, Font.BOLD, BaseColor.BLACK);*/
				        //p=new Paragraph(Created.get(c).getUserName(),font1);
				        Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
				        p=new Paragraph("Name: "+Approved.get(a).getUserName(),font2);
				      
				        p.setAlignment(p.ALIGN_LEFT);
				        
				      
				        Paragraph paragraph;
				        if(Approved.get(a).getCountryCode().equalsIgnoreCase("IND")){
					    	  paragraph = new Paragraph("Date: "+Approved.get(a).getISTDateTime(),font2);  
					     }
					     else{
					    	  paragraph = new Paragraph("Date: "+Approved.get(a).getESTDateTime(),font2);  
					    }
				        paragraph.setAlignment(paragraph.ALIGN_LEFT);
				        title.setBorderWidthLeft(0f);
				      //  title.setHorizontalAlignment(Element.ALIGN_LEFT);
				        title.setVerticalAlignment(Element.ALIGN_MIDDLE);
				        title.setFixedHeight(60f);
				        title.addElement(p);
				        Paragraph role = new Paragraph("Role: "+Approved.get(a).getRoleName(),font2);
				        title.addElement(role);
				        title.addElement(paragraph);
				        Paragraph signId = new Paragraph("Sign Id: "+Approved.get(a).getSignId(),font2);
				        title.addElement(signId);
				        
				        title.setPaddingTop(-5f);
				        title.setPaddingRight(1f);
				        table.addCell(title);
				        table.completeRow();
				        
				        PdfPCell Space1 = new PdfPCell();
				        Space1.setColspan(4);
				        Space1.setPadding(20f);
				        Space1.setLeading(1f, 1.4f);
				        System.out.println("Approved");
					}
				//}
				 Rectangle rect = pdfWriter.getBoxSize("rectangle");
				 /*ColumnText.showTextAligned(pdfWriter.getDirectContent(),
			               Element.ALIGN_CENTER, new Phrase("BOTTOM RIGHT"),
			               rect.getRight()-10, rect.getBottom(), 0);*/
				  	d1.add(Htable);
				    d1.add(table);
					d1.close();
					fos.close();
			}
					 //for attach attribute list in PDF
					 Vector<DTOWorkSpaceNodeAttribute> AttrDtl=null;
					 Vector<DTOWorkSpaceNodeAttribute> AttrDtlForPageSetting = null;
					 
				if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
						AttrDtl=docMgmtImpl.getAttrDtl(wsMst.getWorkSpaceId(),nodeId);
					if(AttrDtl.size()>0){
						File pathForAttribute=null;
						pathForAttribute=new File(htmlSubmissionpath+"/documents" + "/" + leafFileDirectories + "/" +"/attr.pdf");						
					  Document d2 = new Document();
					  OutputStream attrfos = new FileOutputStream(pathForAttribute);
						
				      PdfWriter pdfWriter1 = PdfWriter.getInstance(d2, attrfos);
				      Rectangle rectangle1 = new Rectangle(30, 30, 550, 800);
				      pdfWriter1.setBoxSize("rectangle", rectangle1);
				      
				      	d2.open();
						Font font1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
						
						PdfPTable table1 = new PdfPTable(2);
						table1.setWidthPercentage(100);
						//System.out.println(AttrDtl.size()+"\n");
						/*PdfPCell cell = new PdfPCell(new Phrase(Element.ALIGN_CENTER,nodeName));
						cell.setColspan(2);
						
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						table1.addCell(cell);*/
						

						PdfPCell cell2 = new PdfPCell(new Phrase(Element.ALIGN_CENTER,"File Attributes"));
						cell2.setColspan(2);
						
						cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell2.setVerticalAlignment(Element.ALIGN_CENTER);
						table1.addCell(cell2);
						
						
						PdfPCell An = new PdfPCell(new Phrase("Attribute Name"));
						PdfPCell Av = new PdfPCell(new Phrase("Attribute Value"));
						Av.setHorizontalAlignment(Element.ALIGN_CENTER);
						An.setHorizontalAlignment(Element.ALIGN_CENTER);
						table1.addCell(An);
						table1.addCell(Av);
						
			        
						table1.completeRow();
						
						for(int i=0;i<AttrDtl.size();i++){
							//System.out.println("Name : "+AttrDtl.get(i).getAttrName()+"     Value : " + AttrDtl.get(i).getAttrValue());
							
							PdfPCell cell5 = new PdfPCell(new Phrase(AttrDtl.get(i).getAttrName()));
				            PdfPCell cell6 = new PdfPCell(new Phrase(AttrDtl.get(i).getAttrValue()));
				            table1.addCell(cell5);
				            table1.addCell(cell6);
				            table1.completeRow();
							
						}
			          
						Rectangle rect1 = pdfWriter1.getBoxSize("rectangle");
			          
						d2.add(table1);
						d2.close();
					}	
				  }
				if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
				
				    Document document1 = new Document();
				    PdfWriter writer = PdfWriter.getInstance(document1, out);
				    document1.open();
				    PdfContentByte cb = writer.getDirectContent();
				    userName = ActionContext.getContext().getSession().get("username")
							.toString();
				    PdfContentByte cb1 = writer.getDirectContent();
				    ArrayList<String> time = new ArrayList<String>();
					String locationName = ActionContext.getContext().getSession().get("locationname").toString();

				    //SimpleDateFormat  dtf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS z");
				    
				    Date date = new Date();
				    Timestamp ts=new Timestamp(date.getTime()); 
				    time = docMgmtImpl.TimeZoneConversion(ts,locationName,countryCode);
				    String dtf = "";
				    if(countryCode.equalsIgnoreCase("IND")){
				     dtf = time.get(0);
				    }else{
				     dtf = time.get(1);
				    }
				    Font fontSize_12 =  FontFactory.getFont(FontFactory.TIMES, 10f);
				    
				    String file1="";
//				    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
				    file1=path.toString();
					String file2=uploadFile.toString();
					String file3="";
					if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
						file3=htmlSubmissionpath+"/documents" + "/" + leafFileDirectories+ "/" +"/attr.pdf";
					}
					
					String outs = "";
					if(srcFileExt.equalsIgnoreCase("pdf")){
						System.out.println("Aleready Pdf");
						if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
						outs = htmlSubmissionpath+"/documents" + "/" + leafFileDirectories+ "/" +"Final.pdf";}
					}
					else{
						outs=htmlSubmissionpath+"/documents" + "/" + leafFileDirectories+ "/"  + Name +".pdf";
					}
					//String outs=htmlSubmissionpath+"/documents" + "/" + leafFileDirectories+ "/"  + Name +".pdf";
					PdfReader reader1=null;
					  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
						  reader1 = new PdfReader(file1);}
					PdfReader reader2 = new PdfReader(file2);
					PdfReader reader3 = null;
					if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
						AttrDtl=docMgmtImpl.getAttrDtl(wsMst.getWorkSpaceId(),nodeId);
						if(AttrDtl.size()>0){
							reader3 = new PdfReader(file3);
						}
					}
					Document document = new Document();
					FileOutputStream fos1 = new FileOutputStream(outs);
					PdfCopy copy = new PdfCopy(document, fos1);
					document.open();
					PdfImportedPage page;
					PdfCopy.PageStamp stamp;
					Phrase phrase;
					BaseFont bf = BaseFont.createFont();
					Font font1 = new Font(bf, 9);
					int n ;
					  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
					n = reader1.getNumberOfPages();}
					  
					  
					  AttrDtlForPageSetting=docMgmtImpl.getAttrDtlForPageSetting(wsMst.getWorkSpaceId(),nodeId);
					  String signOffPlacement="";
					  String printPageNo="";
					  if(AttrDtlForPageSetting.size()>0){
						  
						  for(int s=0;s<AttrDtlForPageSetting.size();s++){
								if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Sign Page Placement")){
									signOffPlacement=AttrDtlForPageSetting.get(s).getAttrValue();break;
								}
							}
					  }
				
					  String LTRSingleDocFooterFormat=propertyInfo.getValue("LTRSingleDocFooterFormat");
					  userName = ActionContext.getContext().getSession().get("username").toString();
					  if(LTRSingleDocFooterFormat.equals("Yes")){
						  time = new ArrayList<String>();
						  locationName = ActionContext.getContext().getSession().get("locationname").toString();
						  //SimpleDateFormat dtf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS z");
	
						  date = new Date();
						  ts=new Timestamp(date.getTime());
						  time = docMgmtImpl.TimeZoneConversion(ts,locationName,countryCode);
						  dtf = "";
						  if(countryCode.equalsIgnoreCase("IND")){
						  dtf = time.get(0);
						  }else{
						  dtf = time.get(1);
						  }
						  OutputStream os = new FileOutputStream(new File(outs.toString()));
						  
						  
						  Rectangle pagesize;
						  for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
							    page = copy.getImportedPage(reader1, i);
							    stamp = copy.createPageStamp(page);
							    
							    int n1=reader1.getNumberOfPages();
					     	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence no signature is required",fontSize_12),
							    		200, 7, 0);}
							    stamp.alterContents();
							    copy.addPage(page);
							}
							for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
							    page = copy.getImportedPage(reader2, i);
							    stamp = copy.createPageStamp(page);
							    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
							    pagesize = reader2.getPageSizeWithRotation(i);
							    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence no signature is required",fontSize_12),
							    		200, 7, 0);}
							    stamp.alterContents();
							    copy.addPage(page);
							}
							 
	
//						  PdfReader signOffPdf = new PdfReader(file1);
//						PdfReader uploadFilePdf = new PdfReader(file2);
//						  
//						  //PdfReader pdfReader = new PdfReader(file1.toString());
//						  PdfStamper pdfStamper = new PdfStamper(signOffPdf, os);
//						  Rectangle pagesize;
//						  for (int i = 1; i <= signOffPdf.getNumberOfPages(); i++) {
//							    page = copy.getImportedPage(signOffPdf, i);
//							    stamp = copy.createPageStamp(page);
//							    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
//							    	 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence no signature is required",fontSize_12),
//									    		200, 7, 0);				
//							    }							    
//							    stamp.alterContents();
//							    copy.addPage(page);
//							}
//						  
//							for (int i = 1; i <= uploadFilePdf.getNumberOfPages(); i++) {
//							    page = copy.getImportedPage(uploadFilePdf, i);
//							    stamp = copy.createPageStamp(page);
//							    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
//							    	 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("This is system generated document, hence no signature is required",fontSize_12),
//									    		200, 7, 0);				
//							    }
//							    stamp.alterContents();
//							    copy.addPage(page);
//							}
						  
	
						  //pdfStamper.close(); //close pdfStamper
						  //signOffPdf.close();
						  //uploadFilePdf.close();
					  }
					  else{
					  		
					  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
						  

						  if(AttrDtlForPageSetting.size()>0){
							  if(signOffPlacement.equalsIgnoreCase("first")){
								  if(AttrDtlForPageSetting.size()>0){
									  for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
										    page = copy.getImportedPage(reader1, i);
										    stamp = copy.createPageStamp(page);
										    
										    int n1=reader1.getNumberOfPages();
											String pageNoPosition="";
									    	String pageNoFormat="";
										    for(int s=0;s<AttrDtlForPageSetting.size();s++){
										    
										    	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Page No Position")){
										    		pageNoPosition=AttrDtlForPageSetting.get(s).getAttrValue();
										    	}
										    	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Page Number Format")){
										    		pageNoFormat=AttrDtlForPageSetting.get(s).getAttrValue();
										    	}
										    	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Print Page No")){
										    		printPageNo=AttrDtlForPageSetting.get(s).getAttrValue();
										    	}
										    }
										    //	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Page No Position")){
										    		if(pageNoPosition!=null && pageNoPosition!=""){
										    			if(pageNoPosition.equalsIgnoreCase("center") && pageNoFormat.equalsIgnoreCase("1 of n")){
										    				System.out.println("Center and 1 of N");
										    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
										    					ColumnText.showTextAligned(stamp.getOverContent(),
														 	               Element.ALIGN_CENTER, new Phrase(String.format(" %s of %s ", i, n1),fontSize_12),
														 	              300,5,0);
										    				}
										    				/*ColumnText.showTextAligned(stamp.getOverContent(),
													 	               Element.ALIGN_CENTER, new Phrase(String.format(" %s of %s ", i, n1),fontSize_12),
													 	              300,5,0);*/
										    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
														    		12,6,0);
														    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
														    		470, 6, 0);
										    			}
										    			else if(pageNoPosition.equalsIgnoreCase("left") && pageNoFormat.equalsIgnoreCase("1 of n")){
										    				System.out.println("Left and 1 of N");
										    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
										    				ColumnText.showTextAligned(stamp.getOverContent(),
										    						Element.ALIGN_CENTER, new Phrase(String.format(" %s of %s ", i, n1),fontSize_12),
													 	              33,5,0);}
										    				 /*ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
															    		464,18,0);*/
										    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
															    		590, 7, 0);
										    			}
										    			else if(pageNoPosition.equalsIgnoreCase("right") && pageNoFormat.equalsIgnoreCase("1 of n")){
										    				System.out.println("Right and 1 of N");
										    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
										    				ColumnText.showTextAligned(stamp.getOverContent(),
										    						Element.ALIGN_CENTER, new Phrase(String.format(" %s of %s ", i, n1),fontSize_12),
													 	              565,7,0);}
										    				 /*ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
															    		120,18,0);*/
										    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
															    		246, 7, 0);
										    			}
										    			else if(pageNoPosition.equalsIgnoreCase("center") && pageNoFormat.equalsIgnoreCase("1")){
										    				System.out.println("Center and 1");
										    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
										    				ColumnText.showTextAligned(stamp.getOverContent(),
													 	               Element.ALIGN_CENTER, new Phrase(String.format(" %s ", i),fontSize_12),
													 	              300,5,0);}
										    				/*ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
														    		12,6,0);*/
														    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
														    		470, 6, 0);
										    			}
										    			else if(pageNoPosition.equalsIgnoreCase("left") && pageNoFormat.equalsIgnoreCase("1")){
										    				System.out.println("left and 1");
										    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
										    				ColumnText.showTextAligned(stamp.getOverContent(),
													 	               Element.ALIGN_RIGHT, new Phrase(String.format(" %s ", i),fontSize_12),
													 	              33,5,0);}
										    				 /*ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
															    		464,18,0);*/
										    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
															    		590, 7, 0);
										    			}
										    			else if(pageNoPosition.equalsIgnoreCase("right") && pageNoFormat.equalsIgnoreCase("1")){
										    				System.out.println("right and 1");
										    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
										    				ColumnText.showTextAligned(stamp.getOverContent(),
													 	               Element.ALIGN_RIGHT, new Phrase(String.format(" %s ", i),fontSize_12),
													 	              565,7,0);}
										    				 /*ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
															    		120,18,0);*/
										    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
															    		246, 7, 0);
										    			}
										    		}else{
										    			  ColumnText.showTextAligned(stamp.getOverContent(),
												 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s ", i, n1),fontSize_12),
												 	              300,5,0);
										    		}
										    //	}
										    	/*s*/
										  //  }
								     	 
								     	  /*if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
										    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
										    		12,6,0);
										    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
										    		470, 6, 0);
								     	  }*/
										    stamp.alterContents();
										    copy.addPage(page);
										}
									  Rectangle pagesize;
										for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
										    page = copy.getImportedPage(reader2, i);
										    stamp = copy.createPageStamp(page);
										    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
										    pagesize = reader2.getPageSizeWithRotation(i);
										    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
										    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
										    		12, 6, 0);
										    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
										    		 pagesize.getRight()-128, 6, 0); 
										    }
										    stamp.alterContents();
										    copy.addPage(page);
										}
									  
								  }
								  else{
										for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
										    page = copy.getImportedPage(reader1, i);
										    stamp = copy.createPageStamp(page);
										    
										    int n1=reader1.getNumberOfPages();
										  
								     	   ColumnText.showTextAligned(stamp.getOverContent(),
									 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
									 	              300,5,0);
								     	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
										    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
										    		12,6,0);
										    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
										    		470, 6, 0);
								     	  }
										    stamp.alterContents();
										    copy.addPage(page);
										}
								  }
							  }
							  else if(signOffPlacement.equalsIgnoreCase("last")){
								  Rectangle pagesize;
									for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
									    page = copy.getImportedPage(reader2, i);
									    stamp = copy.createPageStamp(page);
									    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
									    pagesize = reader2.getPageSizeWithRotation(i);
									    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
									    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
									    		12, 6, 0);
									    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
									    		 pagesize.getRight()-128, 6, 0); 
									    }
									    stamp.alterContents();
									    copy.addPage(page);
									}
									 if(AttrDtlForPageSetting.size()>0){
										  for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
											    page = copy.getImportedPage(reader1, i);
											    stamp = copy.createPageStamp(page);
											    
											    int n1=reader1.getNumberOfPages();
												String pageNoPosition="";
										    	String pageNoFormat="";
											    for(int s=0;s<AttrDtlForPageSetting.size();s++){
											    
											    	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Page No Position")){
											    		pageNoPosition=AttrDtlForPageSetting.get(s).getAttrValue();
											    	}
											    	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Page Number Format")){
											    		pageNoFormat=AttrDtlForPageSetting.get(s).getAttrValue();
											    	}
											    	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Print Page No")){
											    		printPageNo=AttrDtlForPageSetting.get(s).getAttrValue();
											    	}
											    }
											    //	if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Page No Position")){
											    		if(pageNoPosition!=null && pageNoPosition!=""){
											    			if(pageNoPosition.equalsIgnoreCase("center") && pageNoFormat.equalsIgnoreCase("1 of n")){
											    				System.out.println("Center and 1 of N");
											    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
											    				ColumnText.showTextAligned(stamp.getOverContent(),
														 	               Element.ALIGN_CENTER, new Phrase(String.format(" %s of %s ", i, n1),fontSize_12),
														 	              300,5,0);}
											    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
															    		12,6,0);
															    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
															    		470, 6, 0);
											    			}
											    			else if(pageNoPosition.equalsIgnoreCase("left") && pageNoFormat.equalsIgnoreCase("1 of n")){
											    				System.out.println("Left and 1 of N");
											    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
											    				ColumnText.showTextAligned(stamp.getOverContent(),
											    						Element.ALIGN_CENTER, new Phrase(String.format(" %s of %s ", i, n1),fontSize_12),
														 	              33,5,0);}
											    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
																    		464,18,0);
											    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
																    		590, 7, 0);
											    			}
											    			else if(pageNoPosition.equalsIgnoreCase("right") && pageNoFormat.equalsIgnoreCase("1 of n")){
											    				System.out.println("Right and 1 of N");
											    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
											    				ColumnText.showTextAligned(stamp.getOverContent(),
											    						Element.ALIGN_CENTER, new Phrase(String.format(" %s of %s ", i, n1),fontSize_12),
														 	              565,7,0);}
											    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
																    		120,18,0);
											    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
																    		246, 7, 0);
											    			}
											    			else if(pageNoPosition.equalsIgnoreCase("center") && pageNoFormat.equalsIgnoreCase("1")){
											    				System.out.println("Center and 1");
											    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
											    				ColumnText.showTextAligned(stamp.getOverContent(),
														 	               Element.ALIGN_CENTER, new Phrase(String.format(" %s ", i),fontSize_12),
														 	              300,5,0);}
											    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
															    		12,6,0);
															    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
															    		470, 6, 0);
											    			}
											    			else if(pageNoPosition.equalsIgnoreCase("left") && pageNoFormat.equalsIgnoreCase("1")){
											    				System.out.println("left and 1");
											    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
											    				ColumnText.showTextAligned(stamp.getOverContent(),
														 	               Element.ALIGN_RIGHT, new Phrase(String.format(" %s ", i),fontSize_12),
														 	              33,5,0);}
											    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
																    		464,18,0);
											    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
																    		590, 7, 0);
											    			}
											    			else if(pageNoPosition.equalsIgnoreCase("right") && pageNoFormat.equalsIgnoreCase("1")){
											    				System.out.println("right and 1");
											    				if(printPageNo!=null && printPageNo.equalsIgnoreCase("yes")){
											    				ColumnText.showTextAligned(stamp.getOverContent(),
														 	               Element.ALIGN_RIGHT, new Phrase(String.format(" %s ", i),fontSize_12),
														 	              565,7,0);}
											    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Printed By : "+userName,fontSize_12),
																    		120,18,0);
											    				 ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_RIGHT, new Phrase("Print Date & Time : "+dtf,fontSize_12),
																    		246, 7, 0);
											    			}
											    		}else{
											    			  ColumnText.showTextAligned(stamp.getOverContent(),
													 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s", i, n1),fontSize_12),
													 	              300,5,0);
											    		}
											    //	}
											    	/*s*/
											  //  }
									     	 
									     	  /*if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
											    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
											    		12,6,0);
											    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
											    		470, 6, 0);
									     	  }*/
											    stamp.alterContents();
											    copy.addPage(page);
											}
										  /*Rectangle pagesize;
											for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
											    page = copy.getImportedPage(reader2, i);
											    stamp = copy.createPageStamp(page);
											    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
											    pagesize = reader2.getPageSizeWithRotation(i);
											    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
											    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
											    		12, 6, 0);
											    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
											    		 pagesize.getRight()-128, 6, 0); 
											    }
											    stamp.alterContents();
											    copy.addPage(page);
											}*/
										  
									  }
									  else{
											for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
											    page = copy.getImportedPage(reader1, i);
											    stamp = copy.createPageStamp(page);
											    
											    int n1=reader1.getNumberOfPages();
											  
									     	   ColumnText.showTextAligned(stamp.getOverContent(),
										 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
										 	              300,5,0);
									     	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
											    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
											    		12,6,0);
											    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
											    		470, 6, 0);
									     	  }
											    stamp.alterContents();
											    copy.addPage(page);
											}
									  }
							  }
							  else{
								  Rectangle pagesize;
									for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
									    page = copy.getImportedPage(reader2, i);
									    stamp = copy.createPageStamp(page);
									    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
									    pagesize = reader2.getPageSizeWithRotation(i);
									    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
									    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
									    		12, 6, 0);
									    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
									    		 pagesize.getRight()-128, 6, 0); 
									    }
									    stamp.alterContents();
									    copy.addPage(page);
									}
									 for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
										    page = copy.getImportedPage(reader1, i);
										    stamp = copy.createPageStamp(page);
										    
										    int n1=reader1.getNumberOfPages();
								     	   ColumnText.showTextAligned(stamp.getOverContent(),
									 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
									 	              300,5,0);
								     	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
										    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
										    		12,6,0);
										    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
										    		470, 6, 0);
								     	  }
										    stamp.alterContents();
										    copy.addPage(page);
										}
								  
							  }
						  }
						  //AttrDtlForPageSetting=docMgmtImpl.getAttrDtlForPageSetting(workspaceID,nodeId);
						 else{
							 for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
								    page = copy.getImportedPage(reader1, i);
								    stamp = copy.createPageStamp(page);
								    
								    int n1=reader1.getNumberOfPages();
						     	   ColumnText.showTextAligned(stamp.getOverContent(),
							 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
							 	              300,5,0);
						     	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
								    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
								    		12,6,0);
								    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
								    		470, 6, 0);
						     	  }
								    stamp.alterContents();
								    copy.addPage(page);
								}
							 Rectangle pagesize;
								for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
								    page = copy.getImportedPage(reader2, i);
								    stamp = copy.createPageStamp(page);
								    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
								    pagesize = reader2.getPageSizeWithRotation(i);
								    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
								    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
								    		12, 6, 0);
								    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
								    		 pagesize.getRight()-128, 6, 0); 
								    }
								    stamp.alterContents();
								    copy.addPage(page);
								}
							  
						  }
					  
						  
						  
						  
						  /*
					for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
					    page = copy.getImportedPage(reader1, i);
					    stamp = copy.createPageStamp(page);
					    
					    int n1=reader1.getNumberOfPages();
			     	   ColumnText.showTextAligned(stamp.getOverContent(),
				 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
				 	              300,5,0);
			     	  if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
					    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
					    		12,6,0);
					    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
					    		470, 6, 0);
			     	  }
					    stamp.alterContents();
					    copy.addPage(page);
					}
				*/}
					  }
					if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
						AttrDtl=docMgmtImpl.getAttrDtl(wsMst.getWorkSpaceId(),nodeId);
						if(AttrDtl.size()>0){
							for (int i = 1; i <= reader3.getNumberOfPages(); i++) {
								  page = copy.getImportedPage(reader3, i);
								    stamp = copy.createPageStamp(page);
								    
								    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
								    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
								    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
								    		12, 6, 0);
								    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
								    		470, 6, 0); 
								    }
								    stamp.alterContents();
								    copy.addPage(page);
								}
							}
					}
					
				/*	Rectangle pagesize;
					for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
					    page = copy.getImportedPage(reader2, i);
					    stamp = copy.createPageStamp(page);
					    pagesize = reader2.getPageSizeWithRotation(i);
					    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
					    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
					    		12, 6, 0);
					    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
					    		pagesize.getRight()-128, 6, 0); 
					    }
					    stamp.alterContents();
					    copy.addPage(page);
					}*/
					if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
						document.close();
						reader1.close();  }
						reader2.close();
						fos1.close();
						out.flush();
				   // 	d1.close();
						out.close();
				
				    
				    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
					    Files.delete(Paths.get(path.toString()));}
				 if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
					 File f = new File(htmlSubmissionpath+"/documents" + "/" + leafFileDirectories+ "/" +"/attr.pdf");
				    FileManager.deleteFile(f);
				 }
				    FileManager.deleteFile(uploadFile);
				    
				    if(uploadedFile.exists()){
				    	File myObj = new File(uploadedFile.toString()); 
				    	myObj.delete();
				    }
				    if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
				    File oldName = new File(htmlSubmissionpath+"/documents" + "/" + leafFileDirectories
							+ "/" + "Final.pdf"); 
				    File newName = new File(htmlSubmissionpath+"/documents" + "/" + leafFileDirectories
							+ "/" + Name+".pdf"); 
				    
				   if (oldName.renameTo(newName))  
				       System.out.println("Renamed successfully");         
				   else 
				      System.out.println("Error");
				    }
				    //Files.delete(Paths.get(path.toString()));
				    //Files.delete(Paths.get(uploadFile.toString()));
				}else{
					  System.out.println("Node type indi is K");
					}
				}
			}

		}

		return htmlStringBuffer;
	}

	/*private String getParentNodeStr(
			ArrayList<DTOWorkSpaceNodeDetail> parentNodeList) {
		String nodeIdStr = "";
		// -1 for remove last nodeName
		for (int i = 0; i < (parentNodeList.size() - 1); i++) {
			DTOWorkSpaceNodeDetail dto = parentNodeList.get(i);
			String dirName = "";
			if (dto.getFolderName() != null) {
				// It will replace all characters with '-'
				dirName = dto.getFolderName().replaceAll("[^a-zA-Z0-9-_]", "-");
			}
			nodeIdStr = nodeIdStr + dirName + "/";
		}
		nodeIdStr = nodeIdStr.substring(0, nodeIdStr.length() - 1);
		return nodeIdStr;
	}*/
	private String getParentNodeStr(
			ArrayList<DTOWorkSpaceNodeDetail> parentNodeList) {
		String nodeIdStr = "";
		// -1 for remove last nodeName
		for (int i = 0; i < (parentNodeList.size() - 1); i++) {
			DTOWorkSpaceNodeDetail dto = parentNodeList.get(i);
			
			String dirName = "";
			
			String country = "", language = "",foldername = "";

			String PathForAttrFolder = "";
			
			String MergeAttributeStr = "";
			
			String temp = "";
			
			boolean flag = false;
			
			if(dto.getNodeTypeIndi() !='D'){			// nodeTypeIndi = 'D' for Specific node
			Vector nodeAttribute = docMgmtImpl
					.getNodeAttributes(dto.getWorkspaceId(), dto.getNodeId());
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
							&& !attrvalue.equals("") && !attrname.equalsIgnoreCase("Script Code")) {
					}
					if(attrname.equalsIgnoreCase("Script Code")){
						
						foldername=attrvalue.trim();
						if (foldername != null
								&& !foldername.equals("")) {
							attrvalue=foldername.replaceAll("\\s", "");
							MergeAttributeStr=attrvalue.toLowerCase();
						}
					}
					else{
						
					// remove space and give - in attribute
					// value
							if (attrvalue.length() > 25
									&& !attrvalue
											.equalsIgnoreCase("common")) {

								System.out.println("attrvalue: "
										+ attrvalue);

								attrvalue = attrvalue.trim()
										.replaceAll(" ", "-");
								attrvalue = attrvalue.trim()
										.replaceAll("\\.", "-");
								attrvalue = attrvalue.trim()
										.replaceAll(",", "-");
								attrvalue = attrvalue.substring(0,
										25);

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
								
								System.out.println("MergeAttributeStr->"+MergeAttributeStr);
							}
					}
				}
				if(MergeAttributeStr.equals("-") || MergeAttributeStr.equals("")){
					flag=false;
				}
				else{
				//System.out.println(MergeAttributeStr + "*************************");
				flag =true;
				}
				}
				}
			if (dto.getFolderName() != null) {
				// It will replace all characters with '-'
				dirName = dto.getFolderName().replaceAll("[^a-zA-Z0-9-_]", "-");
			}
			if(flag==false){
				nodeIdStr = nodeIdStr + dirName + "/";
				//System.out.println(nodeIdStr + "********************");
			}
			else{
				nodeIdStr = nodeIdStr + dirName + "/" + MergeAttributeStr+"/" ;
				//System.out.println(nodeIdStr + "********************");
			}
			}
		nodeIdStr = nodeIdStr.substring(0, nodeIdStr.length() - 1);
		return nodeIdStr;
	}

	public Vector getTreeNodes(String workspaceID, int userGroupCode,
			int userCode, char nonPublishableNode) {
		try {

	
			if (nonPublishableNode == 'Y')
				//nodeInfoVector = docMgmtImpl.getNodeAndRightDetail(workspaceID,	userGroupCode, userCode);
				nodeInfoVector = docMgmtImpl.getNodeAndRightDetailList(workspaceID,	userGroupCode, userCode);
			else
				//nodeInfoVector = docMgmtImpl.getUserWisePublishableNodes(workspaceID, userGroupCode, userCode);
				nodeInfoVector = docMgmtImpl.getUserWisePublishableNodesList(workspaceID, userGroupCode, userCode);

			
			return nodeInfoVector;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodeInfoVector;

	}

	
	public int DocToPdf(String sourceFile, String destFile){
		
		int exitVal=0;
		try {
			
			String WordToPDFFile;
			PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
			WordToPDFFile = propertyInfo.getValue("WordToPDFFile");
			String command = WordToPDFFile + " " + sourceFile + " "
					+ destFile + " ";
			
			try {
				Runtime rt = Runtime.getRuntime();
				Process pCD = rt.exec(command);
				InputStream stderr = pCD.getErrorStream();
				InputStreamReader isr = new InputStreamReader(pCD.getInputStream());
				String line = null;

				BufferedReader input = new BufferedReader(new InputStreamReader(pCD
						.getInputStream()));
				while ((line = input.readLine()) != null)
					System.out.println(line);

				exitVal = pCD.waitFor();
				int len;
				if ((len = pCD.getErrorStream().available()) > 0) {
					byte[] buf = new byte[len];
					pCD.getErrorStream().read(buf);
					System.err.println("Command error:\t\"" + new String(buf)
							+ "\"");
				}

				stderr.close();
				isr.close();
				System.gc();
				
			    
				System.out.println("Doc to Pdf Process exitValue: " + exitVal);
				
			} catch (Exception e) {
				System.out.println("Error...");
				e.printStackTrace();
				// TODO: handle exception
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(exitVal>0){
			exitVal=exitVal-1;
		}
		
		return exitVal;
	}
	
	
	
	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int i) {
		userCode = i;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getHtmlSubmissionpath() {
		return htmlSubmissionpath;
	}

	public void setHtmlSubmissionpath(String htmlSubmissionpath) {
		this.htmlSubmissionpath = htmlSubmissionpath;
	}

}
