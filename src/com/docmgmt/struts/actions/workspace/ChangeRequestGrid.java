package com.docmgmt.struts.actions.workspace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkspaceNodeReminderDoneStatus;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.entities.Workspace.FontTypes;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfIndirectObject;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ChangeRequestGrid extends ActionSupport{

	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public Vector<DTOWorkSpaceNodeDetail>  repeatedNodes;
	public Vector<DTOWorkSpaceNodeAttrDetail> WsNodeAttrDetail;
	public Vector<DTOWorkSpaceNodeAttrDetail> getProjectLevelSearchResult;
	public Vector<Object []> getAttributeDetails;
	public Vector<Object []> getAttributeDetailsForDisplay;
	public List<List<String>> getAttributeValues;
	public List<List<String>> getAttributeValuesForDisplay;
	public int firstLeafNode;
	public String docId;
	public String WorkspaceId;
	int usercode;
	String iscreatedRights;
	public Vector<DTOWorkSpaceNodeAttribute> attrDtl;
	public int recordId;
	public int nodeId;
	public String htmlContent;
	public int attrCount;
	public String remark;
	public String userName;
	public File uploadFile;
	OutputStream out;
	public String DownloadFolderPath;
	public String DownloadFile;
	public InputStream inputStream;
	public String fileName;
    public long contentLength;
	public String baseSrcPath;
	public ArrayList<DTOWorkSpaceNodeHistory> previousHistory=new ArrayList<>();
	ArrayList<DTOWorkSpaceNodeHistory> Created=new ArrayList<>();
	ArrayList<DTOWorkSpaceNodeHistory> Reviewed=new ArrayList<>();
	ArrayList<DTOWorkSpaceNodeHistory> Authorized=new ArrayList<>();
	ArrayList<DTOWorkSpaceNodeHistory> Approved=new ArrayList<>();
	public File uploadedFile;
	public String autoMail;
	public boolean getCompletedNodeStageDetail;
	public String showAttributesInPublishPage;
	public Vector getNodeAttrDetailHistory;
	public String docType;
	public String docTypeName;
	public boolean isFileUplodRights;
	FontTypes ft =  new FontTypes();
	public String OpenFileAndSignOff;
	
	public int pageNo;
	public String uName;
	public String role;
	public String signId;
	public String signdate;
	public Vector <DTOWorkSpaceNodeHistory> dtoWsHistory;
	public String imgFilename="";
	
	public String actionDesc;
	
	public String execute(){
		//WorkspaceId=ActionContext.getContext().getSession().get("docId").toString();
		WorkspaceId =docId;
		int usergrpcode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());
		usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		docTypeName = docMgmtImpl.getDocTypeName(docType);
		repeatedNodes=new Vector<>();
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		//repeatedNodes=docMgmtImpl.getWorkSpaceDetailForChangeRequestGrid(WorkspaceId,usergrpcode,usercode);
		repeatedNodes=docMgmtImpl.getWorkSpaceDetailForChangeRequestGridList(WorkspaceId,usergrpcode,usercode);
		
		getAttributeDetails=new Vector<Object []>();
   		getProjectLevelSearchResult=new Vector<>();
   		WsNodeAttrDetail=new Vector<>(); 		
   		getAttributeDetailsForDisplay=new Vector<Object []>();
   		getAttributeValuesForDisplay=new ArrayList<List<String>>();
   		getAttributeValues=new ArrayList<List<String>>();
		List<String> tableHeader = new ArrayList<String>();
		firstLeafNode =  docMgmtImpl.getFirstLeafNodeForDocCR(WorkspaceId);
		OpenFileAndSignOff = propertyInfo.getValue("OpenFileAndSignOff");
		//maxID=maxID+1;
		if(repeatedNodes.size()>0){
	for(int j=0;j<repeatedNodes.size();j++){
    			
    			WsNodeAttrDetail=docMgmtImpl.getNodeAttrDetailForCSV(WorkspaceId, repeatedNodes.get(j).getNodeId());
	    		
        		String attr1="";
        		if(tableHeader.size()<=0){
        		for(int k=0;k<WsNodeAttrDetail.size();k++){
        			tableHeader.add(WsNodeAttrDetail.get(k).getAttrName());
        			DTOWorkSpaceNodeAttrDetail dto = new DTOWorkSpaceNodeAttrDetail();
        			attr1= WsNodeAttrDetail.get(k).getAttrName();
        			dto.setAttrName(attr1);
        			getProjectLevelSearchResult.add(dto);
        			}
        		}
        		getAttributeDetails=docMgmtImpl.getAttributeDetailsForDocSign(WorkspaceId, repeatedNodes.get(j).getNodeId(),tableHeader);
   			 getAttributeDetailsForDisplay.addAll(getAttributeDetails);
        		
			}
	for(int i=0;i<repeatedNodes.size();i++){
		
		WsNodeAttrDetail=docMgmtImpl.getNodeAttrDetailForCSV(WorkspaceId, repeatedNodes.get(i).getNodeId());
		
		String attr1="";
		if(tableHeader.size()<=0){
		for(int k=0;k<WsNodeAttrDetail.size();k++){
			tableHeader.add(WsNodeAttrDetail.get(k).getAttrName());
			DTOWorkSpaceNodeAttrDetail dto = new DTOWorkSpaceNodeAttrDetail();
			attr1= WsNodeAttrDetail.get(k).getAttrName();
			dto.setAttrName(attr1);
			getProjectLevelSearchResult.add(dto);
			}
		}
		getAttributeValues=docMgmtImpl.getAttributeDetailsForAttValue(WorkspaceId, repeatedNodes.get(i).getNodeId(),tableHeader);
		 getAttributeValuesForDisplay.addAll(getAttributeValues);
		
	}
		}
		
		return SUCCESS;
	}
	
	public String EditAttribute(){
		WorkspaceId =docId;
		nodeId= recordId;
		int usergrpcode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());
		usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		docTypeName = docMgmtImpl.getDocTypeName(docType);
		isFileUplodRights = docMgmtImpl.iscreatedRights(WorkspaceId,nodeId, usercode, usergrpcode);
		attrDtl = docMgmtImpl.getAttributeDetailForDisplay(WorkspaceId,nodeId);
		
		return SUCCESS;
	}
	
	public String SaveAttribute(){
		WorkspaceId =docId;
		nodeId= recordId;
		HttpServletRequest request = ServletActionContext.getRequest();

		String AttributeValueForNodeDisplayName = "";
		DTOWorkSpaceNodeDetail nodeDetail = (DTOWorkSpaceNodeDetail) docMgmtImpl
				.getNodeDetail(WorkspaceId, nodeId).get(0);
		ArrayList<DTOWorkspaceNodeReminderDoneStatus> dtoList = new ArrayList<DTOWorkspaceNodeReminderDoneStatus>();
		Vector<DTOWorkSpaceNodeAttrDetail> attrList = docMgmtImpl
				.getNodeAttrDetail(WorkspaceId, nodeId);
		attrDtl = docMgmtImpl.getAttributeDetailForDisplay(WorkspaceId,nodeId);
		attrCount=attrDtl.size();
		int attrIdToMatch;
		
		for (int i = 1; i <= attrCount; i++) {
			String multiSelectionComboValue="";
			String attrValueId = "attrValueId" + i;
			String attrType = "attrType" + i;
			String attrId = "attrId" + i;
			String attrName = "attrName" + i;
			if(request.getParameter(attrType)!=null && request.getParameter(attrType).equals("MultiSelectionCombo")){
				String[] outerArray=request.getParameterValues(attrValueId);
				if (outerArray != null) {
                    for (int j = 0; j < outerArray.length; j++) {
                    	multiSelectionComboValue = multiSelectionComboValue + "," + outerArray[j];
                    }
                }
			}
			try{
			attrIdToMatch=Integer.parseInt(request.getParameter(attrId));
			}
			catch(Exception e){
				System.out.println(e.toString());
				break;
			}
			
			Integer attributeId = Integer.parseInt(request.getParameter(attrId));
			boolean dupFound = false;
			for (int idxAttr = 0; idxAttr < attrList.size(); idxAttr++) {
				DTOWorkSpaceNodeAttrDetail dtoWorkSpaceNodeAttrDetail = attrList
						.get(idxAttr);
				if (dtoWorkSpaceNodeAttrDetail.getAttrId() == attributeId) {
					if(request.getParameter(attrValueId)!=null){
						if((multiSelectionComboValue != null && multiSelectionComboValue != "" ) && multiSelectionComboValue.substring(1).equalsIgnoreCase(dtoWorkSpaceNodeAttrDetail.getAttrValue()) ){
							dupFound = true;
							break;	
						}else{
							if (request.getParameter(attrValueId).equals(dtoWorkSpaceNodeAttrDetail.getAttrValue())) {
								dupFound = true;
								break;
							}
						}
					}
					else{
						dupFound = true;
						break;
					}
				}
			}

			DTOAttributeMst dtoAttr = docMgmtImpl.getAttribute(attributeId);

			// if the new values of attributes are same as the old ones, don't
			// update in database
			if (dupFound) {
				// for getting the values in display
				if (dtoAttr.getAttrForIndiId().equals("0002")
						&& request.getParameter(attrValueId)!=null && !request.getParameter(attrValueId).equals("")) {
					if (AttributeValueForNodeDisplayName == "")
						AttributeValueForNodeDisplayName = request
								.getParameter(attrValueId);
					else
						AttributeValueForNodeDisplayName += ","
								+ request.getParameter(attrValueId);
				}
				continue;// TODO instead of individual attributes, values should
				// be changed together
			}
			DTOWorkSpaceNodeAttribute wsnadto = new DTOWorkSpaceNodeAttribute();

			wsnadto.setWorkspaceId(WorkspaceId);
			wsnadto.setNodeId(nodeId);
			wsnadto.setAttrId(attributeId);
			wsnadto.setAttrName(request.getParameter(attrName));
			//wsnadto.setAttrValue(request.getParameter(attrValueId));
			if(request.getParameter(attrType).equals("MultiSelectionCombo")){
				wsnadto.setAttrValue(multiSelectionComboValue.substring(1));
			}
			else{
				wsnadto.setAttrValue(request.getParameter(attrValueId));		
			}
			if(remark.isEmpty() || remark==""){
				wsnadto.setRemark("New");
			}else{
				wsnadto.setRemark(remark);	
			}
					
			int ucd = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userid").toString());

			wsnadto.setModifyBy(ucd);

			// ///update workspacenode attribute value
			docMgmtImpl.updateWorkspaceNodeAttributeValue(wsnadto);

			/**
			 * Only attr value of type '0002' will be appended to node display
			 * name. Changed By Ashmak Shah
			 */

			if (dtoAttr.getAttrForIndiId().equals("0002")) {

				if (AttributeValueForNodeDisplayName == "") {
					AttributeValueForNodeDisplayName = request
							.getParameter(attrValueId);
				} else if (!request.getParameter(attrValueId).equals("")) {
					AttributeValueForNodeDisplayName += ","
							+ request.getParameter(attrValueId);
				}

			}
			/*
			 * It will undone the user reminders displayed in welcome page
			 */
			if (request.getParameter(attrType).equals("Date")) {
				DTOWorkspaceNodeReminderDoneStatus dto = new DTOWorkspaceNodeReminderDoneStatus();
				dto.setvWorkspaceId(WorkspaceId);
				dto.setiNodeId(nodeId);
				dto.setiAttrId(Integer.parseInt(request.getParameter(attrId)));
				dto.setiUserCode(2);
				dtoList.add(dto);
			}
		}
		docMgmtImpl.markReminderAsUnDone(dtoList);

		// update nodedisplayname of the node
		DTOWorkSpaceNodeDetail wsnd = (DTOWorkSpaceNodeDetail) docMgmtImpl
				.getNodeDetail(WorkspaceId, nodeId).get(0);
		/*if (AttributeValueForNodeDisplayName.equals("")) {

			// pattern used for attr values appended at the end of
			// nodeDisplayName between '(' and ')'.
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().replaceAll(
					"\\(.+\\)$", ""));
		} else {
			// pattern used for attr values appended at the end of
			// nodeDisplayName between '(' and ')'.
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().replaceAll(
					"\\(.+\\)$", "")
					+ "(" + AttributeValueForNodeDisplayName + ")");
		}*/
		if (AttributeValueForNodeDisplayName.equals("")) {

			// pattern used for attr values appended at the end of
			// nodeDisplayName between '(' and ')'.
			if(wsnd.getNodeDisplayName().contains("{"))
			{
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().substring(0,wsnd.getNodeDisplayName().indexOf('{')));
			}
			else
			{
				wsnd.setNodeDisplayName(wsnd.getNodeDisplayName());
			}
		} else {
			// pattern used for attr values appended at the end of
			// nodeDisplayName between '(' and ')'.
			if(wsnd.getNodeDisplayName().contains("{"))
			{
			wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().substring(0,wsnd.getNodeDisplayName().indexOf('{'))
					+ "{" + AttributeValueForNodeDisplayName + "}");
			}
			else
			{
				wsnd.setNodeDisplayName(wsnd.getNodeDisplayName()+"{" + AttributeValueForNodeDisplayName + "}");
			}
		}

		docMgmtImpl.insertWorkspaceNodeDetail(wsnd, 2);// edit mode
		
		return SUCCESS;
	}
	
	public int DocToPdf(String sourceFile, String destFile){

		String flag="";
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
	public String DownloadZipFolder() throws SQLException, DocumentException, IOException{
			
			File fileToDownload = new File(DownloadFile);
			try {
				inputStream = new FileInputStream(fileToDownload);
			} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fileName = fileToDownload.getName();
			contentLength = fileToDownload.length();
			System.out.println("Downloaded");
		    
			return "SUCCESS";

		}
	  
		public String convertAndDownload() throws SQLException, IOException, DocumentException, ParseException {
			PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
			baseSrcPath = propertyInfo.getValue("DoToPdfPath");
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			String flag="true";
			int exitFlag=0;
			showAttributesInPublishPage = propertyInfo.getValue("AttachAttributesInPublishPage");
			
			int lastTranNo = docMgmtImpl.getMaxTranNo(WorkspaceId, nodeId);
			previousHistory = docMgmtImpl.getWorkspaceNodeHistory(WorkspaceId, nodeId,lastTranNo);
			UUID uuid=UUID.randomUUID();
			

			Vector<Object[]> WsDetail= docMgmtImpl.getWorkspaceDescList(WorkspaceId);
			Object[] nodeRec = (Object[]) WsDetail.get(0);
			String src=(String) nodeRec[9];
			
			String fileName=previousHistory.get(0).getFileName();
			//sString src=previousHistory.get(0).getFolderName();
			//src+="/"+previousHistory.get(0).getWorkSpaceId()+"/"+previousHistory.get(0).getNodeId()+"/"+previousHistory.get(0).getTranNo()+"/"+ previousHistory.get(0).getFileName();
			src+="/"+previousHistory.get(0).getFolderName()+"/"+ previousHistory.get(0).getFileName();
			File fSrc=new File(src);
			File wsFolder = new File(baseSrcPath + "/" + WorkspaceId);
			wsFolder.mkdirs();

			File nodeFolder = new File(baseSrcPath + "/" + WorkspaceId + "/"
					+ nodeId);
			nodeFolder.mkdirs();

			//if (lastTranNo != -1) {
				File tranFolder = new File(baseSrcPath + "/" + WorkspaceId + "/"
						+ nodeId + "/" + uuid);
				tranFolder.mkdirs();
			//}
			
			String ManualSignatureConfig = docMgmtImpl.getAttributeDetailByAttrName(WorkspaceId, 1, "ManualSignature").getAttrValue();
			DTOWorkSpaceMst wsDetail1 =docMgmtImpl.getWorkSpaceDetail(WorkspaceId);
			Vector<DTOWorkSpaceNodeDetail> getNodeDetail1 = new Vector<DTOWorkSpaceNodeDetail>();
			getNodeDetail1 = docMgmtImpl.getNodeDetail(WorkspaceId, nodeId);
			
			char nodeTypeIndi1 = getNodeDetail1.get(0).getNodeTypeIndi();	
			if(!wsDetail1.getProjectCode().equals("0003") && nodeTypeIndi1!='K' && ManualSignatureConfig != null && ManualSignatureConfig.equalsIgnoreCase("Yes")){
			//if(ManualSignatureConfig.equalsIgnoreCase("Yes")){

				//if(ManualSignatureConfig.equalsIgnoreCase("Yes")){
					File inputFilePath = null;
					File outputFilePath;
					String outFile = null;
					try{
					String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
					outFile = tranFolder+"/"+fileNameWithOutExt+"_Sign.pdf";
					String fName = previousHistory.get(0).getFileName();

					File trnFolder = new File(nodeRec[9]+previousHistory.get(0).getFolderName());

					File uploadedFile = new File(trnFolder, fName);

					inputFilePath = new File(uploadedFile.getAbsolutePath()); // Existing file
					outputFilePath = new File(outFile); // New file
					OutputStream fos = new FileOutputStream(outputFilePath);

					
					inputFilePath=new File(trnFolder+"\\"+fileNameWithOutExt+".pdf");
					
					PdfReader pdfReader = new PdfReader(inputFilePath.getAbsolutePath());
					PdfStamper pdfStamper = new PdfStamper(pdfReader, fos);
					String signatureFilePath=propertyInfo.getValue("signatureFile");
					usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
					docMgmtImpl.getUserSignatureDetail(usercode);
					

					Vector<DTOWorkSpaceNodeHistory> userHistory = docMgmtImpl.getProjectSignOffHistory(WorkspaceId, nodeId);
					
					Vector<DTOWorkSpaceNodeHistory> data=new Vector<>();
					Vector<DTOWorkSpaceNodeHistory> tempHistory1 = new Vector<>();
						for(int i=0;i<userHistory.size();i++){
							String style = null;
							String imageFile = null;
							Image image = null;
							float xCord = 0;
							float yCord = 0;
							int pageNo = 0;
							data=docMgmtImpl.getWorkspacenodeHistoryByUserId(WorkspaceId, nodeId, userHistory.get(i).getUserCode());
							tempHistory1 = docMgmtImpl.getUserSignatureDetail(userHistory.get(i).getUserCode());
							
							String arr[]=data.get(0).getCoOrdinates().split(":");
							pageNo=Integer.parseInt(arr[0]);
							xCord=Float.parseFloat(arr[1]);
							yCord=Float.parseFloat(arr[2]);
							
	
							if(tempHistory1.get(0).getFileType()!=null && !tempHistory1.get(0).getFileType().isEmpty()){
								style=tempHistory1.get(0).getStyle();
							}else{
								imageFile =signatureFilePath+"/"+tempHistory1.get(0).getFolderName()+"/"+tempHistory1.get(0).getFileName();
								File f=new File(imageFile);
								if(f.exists())
								{
								image = Image.getInstance(imageFile);
								PdfImage stream = new PdfImage(image, "", null);	
								stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
								PdfIndirectObject ref = pdfStamper.getWriter().addToBody(stream);
								image.setDirectReference(ref.getIndirectReference());
								image.scaleToFit(70f, 80f); //Scale image's width and height
								image.setAbsolutePosition(xCord, yCord-20);
								}
							}
							PdfContentByte pdfContentByte = pdfStamper.getOverContent(pageNo);
							
							DTOUserMst dtouser = docMgmtImpl.getUserByCode(userHistory.get(i).getUserCode());
							dtoWsHistory =docMgmtImpl.getUserSignatureDetail(userHistory.get(i).getUserCode());
							String baseWorkFolder = propertyInfo.getValue("signatureFile");
							if(dtoWsHistory.size()>0){
								imgFilename = dtoWsHistory.get(0).getFileName();
								uName = userHistory.get(i).getUserName();
								role = dtouser.getRoleName();
								//signdate = userHistory.get(i).getModifyOn();
								if(userHistory.get(i).getCountryCode().equalsIgnoreCase("IND")){
									signdate =  userHistory.get(i).getISTDateTime(); 
							     }
							     else{
							    	 signdate =  userHistory.get(i).getESTDateTime();
							    }
								signId = dtoWsHistory.get(0).getUuId();
							}
							//float[] widthsForTable = { 2.5f, 3 };
							float[] widthsForTable = { 7.5f, 10 };
							PdfPTable pdfPTable = new PdfPTable(widthsForTable);
							//pdfPTable.setTotalWidth(82);
							pdfPTable.setTotalWidth(92);
					       //Create cells
					       PdfPCell pdfPCell1 = new PdfPCell();
					       PdfPCell pdfPCell2 = new PdfPCell();
					       //Add cells to table
					       //image = Image.getInstance(imageFile);
					       if(image!= null){   
					    	   image.scaleToFit(38f, 80f); //Scale image's width and height
						       pdfPCell1.addElement(image);
						       pdfPCell1.setFixedHeight(20f);
						       pdfPCell1.setPaddingTop(8f);
						       pdfPCell1.setPaddingLeft(1f);
						       pdfPCell1.setPaddingRight(1f);
						       pdfPCell1.setBorder(Rectangle.NO_BORDER);
						       pdfPCell1.setBorder(Rectangle.NO_BORDER);
						       pdfPCell1.setBackgroundColor(new BaseColor(255,255,255));
						       pdfPTable.addCell(pdfPCell1);
						    }else{
						    	style=tempHistory1.get(0).getFileType();
						    	Font font1 = ft.registerExternalFont(style,ManualSignatureConfig);
						    	if(!style.isEmpty())
						    	font1=ft.registerExternalFont(style,ManualSignatureConfig);
						    	else
						    	font1= FontFactory.getFont("Calibri", 7f, Font.BOLD, BaseColor.BLACK);
						    	
						    	Paragraph p = new Paragraph(tempHistory1.get(0).getUserName(),font1);
						    	pdfPCell1.addElement(p);
						    	pdfPCell1.setFixedHeight(20f);
							    pdfPCell1.setPaddingTop(8f);
							    pdfPCell1.setPaddingLeft(1f);
							    pdfPCell1.setPaddingRight(1f);
							    pdfPCell1.setBorder(Rectangle.NO_BORDER);
							    pdfPCell1.setBackgroundColor(new BaseColor(255,255,255));
						    	pdfPTable.addCell(pdfPCell1);
						    }
					       Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 4.4f, BaseColor.BLACK);
						      Paragraph p=new Paragraph("Name : "+uName,font2);
						      Paragraph paragraph = new Paragraph("Date :"+signdate,font2);
						      pdfPCell2.setVerticalAlignment(Element.ALIGN_TOP);
						      pdfPCell2.addElement(p);
						      Paragraph roll = new Paragraph("Role : "+role,font2);
						      pdfPCell2.addElement(roll);
						      pdfPCell2.addElement(paragraph);
						      pdfPCell2.setFixedHeight(20f);
						      pdfPCell2.setBorder(Rectangle.NO_BORDER);
						      pdfPCell2.setBackgroundColor(new BaseColor(255,255,255));
						  
						      Paragraph SignId = new Paragraph("Sign--Id:"+signId,font2);
						      pdfPCell2.addElement(SignId);
						      
						      if(userHistory.get(i).getCompletedStageId()==10)
						    	  actionDesc="Created";
						      else
						    	  actionDesc=userHistory.get(i).getStageDesc();
						      
						      Phrase phrase = new Phrase();
							  Chunk chunk = new Chunk("\nName : "+uName+" \n"
									  	+ "Date : "+signdate+" \n"
									  	+ "Role : "+role+" \n"
							      		+ "Sign-Id: "+signId+" \n"
							      		+ "Action : "+actionDesc+" \n"
							      		,font2);
							  String appUrl=propertyInfo.getValue("ApplicationUrl");
							  String docId=docMgmtImpl.getDocIdForSign(WorkspaceId,nodeId,userHistory.get(i).getTranNo());
							  chunk.setAnchor(appUrl+"FileOpenFromPDF.do?wsId="+WorkspaceId+"&nodeId="+nodeId+"&tranNo="+data.get(0).getTranNo()
									  +"&userCode="+userHistory.get(i).getUserCode()+"&docId="+docId);
							  //chunk.setAnchor(appUrl+"FileOpenFromPDF.do?wsId="+WorkspaceId+"&nodeId="+nodeId+"&tranNo="+data.get(0).getTranNo()+"&userCode="+userHistory.get(i).getUserCode());
							  phrase.add(chunk);
							  PdfPCell cellOne = new PdfPCell(new Phrase(phrase));
							  cellOne.setBorder(Rectangle.NO_BORDER);
							  cellOne.setBackgroundColor(new BaseColor(255,255,255));
						      //pdfPTable.addCell(pdfPCell2);
							  pdfPTable.addCell(cellOne);
						      //pdfPTable.writeSelectedRows(0, -1, xCord-42, yCord+20, pdfContentByte);
						      pdfPTable.writeSelectedRows(0, -1, xCord-45.9f, yCord+15, pdfContentByte);
						}
						
						String LTRSingleDocFooterFormat=propertyInfo.getValue("LTRSingleDocFooterFormat");
						userName = ActionContext.getContext().getSession().get("username").toString();
						if(LTRSingleDocFooterFormat.equals("Yes")){
						for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
						PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);
						 
							//if(LTRSingleDocFooterFormat.equals("Yes")){
				               pdfContentByte.setFontAndSize(BaseFont.createFont
				                       (BaseFont.TIMES_ROMAN, //Font name
				                         BaseFont.CP1257, //Font encoding
				                        BaseFont.EMBEDDED //Font embedded
				                        ) 
										         , 8); // set font and size
										pdfContentByte.setTextMatrix(200,7);
										pdfContentByte.showText("This is system generated document, hence No Handwritten signature is required");	
							//}
						}

						}	
						
					  pdfStamper.close();
				      pdfReader.close();
					
					}
					catch (Exception e) {
					System.out.println(e.toString());
					htmlContent=e.toString();
					return SUCCESS;
					}
					
					/*
					String LTRSingleDocFooterFormat=propertyInfo.getValue("LTRSingleDocFooterFormat");
					userName = ActionContext.getContext().getSession().get("username").toString();
					if(LTRSingleDocFooterFormat.equals("Yes")){
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
				           //OutputStream os = new FileOutputStream(new File(outputFilePath.toString()));
					    String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
					    //OutputStream os = new FileOutputStream(new File(nodeRec[9]+"/"+previousHistory.get(0).getFolderName()+"/"+ fileNameWithOutExt+"_Copy.pdf"));
					    OutputStream os = new FileOutputStream(new File(inputFilePath.getAbsolutePath()));
				 
				           PdfReader pdfReader = new PdfReader(outputFilePath.toString());
				           PdfStamper pdfStamper = new PdfStamper(pdfReader, os);
				 
				           for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
								 PdfContentByte pdfContentByte = pdfStamper.getOverContent(i);
				 
				                  // Add text in existing PDF
				                  pdfContentByte.beginText();
				                  pdfContentByte.setFontAndSize(BaseFont.createFont
				                                                    (BaseFont.TIMES_ROMAN, //Font name
				                                                      BaseFont.CP1257, //Font encoding
				                                                     BaseFont.EMBEDDED //Font embedded
				                                                     )
				                               , 8); // set font and size
				                  pdfContentByte.setTextMatrix(5,16);
				                  pdfContentByte.showText("Printed By : "+userName); // add the text
				                  
				                  pdfContentByte.setTextMatrix(5,7);
				                  pdfContentByte.showText("Print Date & Time :"+dtf);
				                  
				                  pdfContentByte.setTextMatrix(200,7);
				                  pdfContentByte.showText("This is system generated document, hence No Handwritten signature is required");
				                  pdfContentByte.endText();
				                 
				           }
				 
				           pdfStamper.close(); //close pdfStamper	
					}
					*/
					System.out.println("Uploaded File Path: >> "+ inputFilePath);

					System.out.println("After Add Watermark File Path: >> "+ outFile);
					String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
					DownloadFolderPath=baseSrcPath + "/" + WorkspaceId + "/"
							+ nodeId + "/" + uuid+ "/" + fileNameWithOutExt+"_Sign.pdf";
					/*File original=new File(nodeRec[9]+"/"+previousHistory.get(0).getFolderName()+"/"+ fileNameWithOutExt+".pdf");
					File fileToCopy=new File(outputFilePath.toString());
					Files.copy(fileToCopy.toPath(),original.toPath(), StandardCopyOption.REPLACE_EXISTING);
					original.delete();
					 File oldFileName = new File(nodeRec[9]+"/"+previousHistory.get(0).getFolderName()+"/"+ fileNameWithOutExt+"_Copy.pdf"); 
					 File newFileName = new File(nodeRec[9]+"/"+previousHistory.get(0).getFolderName()+"/"+ fileNameWithOutExt+".pdf"); 
					     
					   if (oldFileName.renameTo(newFileName))  
					       System.out.println("Renamed successfully");         
					   else 
					      System.out.println("Error");*/
					File fileCopy=new File(outFile);
					File srcFile = new File(fileCopy.toString());
					
					String fName = previousHistory.get(0).getFileName();
					File trnFolder = new File(nodeRec[9]+previousHistory.get(0).getFolderName());
					
					File destFile = new File(trnFolder+"\\"+fileNameWithOutExt+"_Sign.pdf");
					//FileUtils.copyFile(srcFile, destFile);
					InputStream is = null;
				    OutputStream os = null;
				    try {
				        is = new FileInputStream(srcFile);
				        os = new FileOutputStream(destFile); 
				        byte[] buffer = new byte[1024];
				        int length;
				        while ((length = is.read(buffer)) > 0) {
				            os.write(buffer, 0, length);
				        }
				    } finally {
				        is.close();
				        os.close();
				    }
	
					
					
				 htmlContent=DownloadFolderPath;
				 File fileToCopy=new File(outputFilePath.toString());
				 if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){ 
				 PdfReader reader = new PdfReader(fileToCopy.toString());
				 inputFilePath = new File(outFile); // Existing file
		         outputFilePath = new File(outFile); // New file
		 
		        /*   OutputStream fos = new FileOutputStream(new File(outputFilePath));
		 
		           PdfStamper pdfStamper = new PdfStamper(reader, fos);
				 int n=reader.getNumberOfPages();
				 
				 
				 for(int i=0;i<n;i++){
					 
				 }*/
				 
		         //Other publishing logic
		         
		         String srcFileExt = outFile.substring(outFile.lastIndexOf(".") +1);
		         File pathForAttribute = null;
					File path=new File(baseSrcPath + "/" + WorkspaceId + "/"
							+ nodeId + "/" + uuid +"/Temp.pdf");
					OutputStream pathfos = new FileOutputStream(path);
					if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
					pathForAttribute=new File(baseSrcPath + "/" + WorkspaceId + "/"
							+ nodeId + "/" + uuid +"/attr.pdf");
					}
				 
				 //for attach attribute list in PDF
				
				 Vector<DTOWorkSpaceNodeAttribute> AttrDtl = null;
				 Vector<DTOWorkSpaceNodeAttribute> AttrDtlForPageSetting = null;
				 
				 if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
					AttrDtl=docMgmtImpl.getAttrDtl(WorkspaceId,nodeId);
			 if(AttrDtl.size()>0){
				  Document d2 = new Document();
				  OutputStream attrfos = new FileOutputStream(pathForAttribute);
					
			      PdfWriter pdfWriter1 = PdfWriter.getInstance(d2, attrfos);
			      Rectangle rectangle1 = new Rectangle(30, 30, 550, 800);
			      pdfWriter1.setBoxSize("rectangle", rectangle1);
			      
			      	d2.open();
					Font font1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
					
					PdfPTable table1 = new PdfPTable(2);
					table1.setWidthPercentage(100);
				
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
					pathfos.close();
			 	}
				 }
			
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
			    userName = ActionContext.getContext().getSession().get("username")
						.toString();
			    Font fontSize_12 =  FontFactory.getFont(FontFactory.TIMES, 10f);
			    boolean c=false;
			    
			    String file1=path.toString();
				String file2=fileToCopy.toString();
				String file3="";
				if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
					file3=pathForAttribute.toString();
				}
				String outs = null;
				if(srcFileExt.equalsIgnoreCase("pdf")){
					System.out.println("Aleready Pdf");
					outs = baseSrcPath + "/" + WorkspaceId + "/"+ nodeId + "/" + uuid+ "/" + "Final.pdf";
				}
				else{
					outs=baseSrcPath + "/" + WorkspaceId + "/"+ nodeId + "/" + uuid+ "/" + fileName +".pdf";
				}
				if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
				
				Document d1 = new Document();
				//Document document = new Document(PageSize.A4, 30, 30, 550, 800);
				OutputStream fos = new FileOutputStream(path);
				
			    PdfWriter pdfWriter = PdfWriter.getInstance(d1, fos);
			    Rectangle rectangle = new Rectangle(30, 30, 550, 800);
			    
			    pdfWriter.setBoxSize("rectangle", rectangle);
			   /* ColumnText.showTextAligned(pdfWriter.getDirectContent(),
			               Element.ALIGN_CENTER, new Phrase("Powered By Mark47"),
			               110, 30, 0);*/
			    d1.open();
				Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
				
				float[] widthsForTable = { 1.3f, 3 };
				PdfPTable table = new PdfPTable(widthsForTable);
				table.setWidthPercentage(100);
				//System.out.println(tempHistory.size()+"\n");
				//String Company="SSPL";
				float[] widths = { 1, 3 };
				PdfPTable Htable = new PdfPTable(widths);
				Htable.setWidthPercentage(100);
				/*Htable.setHorizontalAlignment(0);
				Htable.setTotalWidth(500f);
				Htable.setLockedWidth(true);
				/*float[] widths = new float[] { 20f, 50f, 30f};*/
				//Htable.setWidths(widths);
				/*PdfPCell cell;
				PdfPCell nodeName = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Project Name : "+docMgmtImpl.getWorkSpaceDetail(workspaceID).getWorkSpaceDesc()));*/
				
				PdfPCell HPName = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Project Name"));
				HPName.setPadding(8f);
				Htable.addCell(HPName);
		        PdfPCell PName = new PdfPCell(new Phrase(docMgmtImpl.getWorkSpaceDetail(WorkspaceId).getWorkSpaceDesc()));
		        PName.setPadding(8f);
		        Htable.addCell(PName);
		        
		        PdfPCell HNname = new PdfPCell(new Phrase("Document Name  "));
		        HNname.setPadding(8f);
		        Htable.addCell(HNname);
		        
		        PdfPCell Nname = new PdfPCell(new Phrase(docMgmtImpl.getNodeDetail(WorkspaceId, nodeId).get(0).getNodeName()));
		        Nname.setPadding(8f);
		        Htable.addCell(Nname);
				
		        Vector<DTOWorkSpaceNodeAttribute> AttrDtlForCoverPage = null;
		        AttrDtlForCoverPage=docMgmtImpl.getAttrDtlForCoverPage(WorkspaceId,nodeId);
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
		        d1.add(Htable);
			    d1.add(table);
			    
		        d1.close();
				 fos.close();	
			}
				PdfReader reader1 = null;
				if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
					reader1 = new PdfReader(path.toString());
				}
				
				PdfReader reader2 = new PdfReader(file2);
				PdfReader reader3=null;
				if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
					AttrDtl=docMgmtImpl.getAttrDtl(WorkspaceId,nodeId);
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
				int n=0;
				if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
				n = reader1.getNumberOfPages();
				}
				/*  AttrDtlForPageSetting=docMgmtImpl.getAttrDtlForPageSetting(WorkspaceId,nodeId);
				  String signOffPlacement="";
				  String printPageNo="";
				  if(AttrDtlForPageSetting.size()>0){
					  
					  for(int s=0;s<AttrDtlForPageSetting.size();s++){
							if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Sign Page Placement")){
								signOffPlacement=AttrDtlForPageSetting.get(s).getAttrValue();break;
							}
						}
				  }*/

				  //AttrDtlForPageSetting=docMgmtImpl.getAttrDtlForPageSetting(workspaceID,nodeId);
				 //else{
				if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
					 for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
						    page = copy.getImportedPage(reader1, i);
						    stamp = copy.createPageStamp(page);
						    
						    int n1=reader1.getNumberOfPages();
				     	   ColumnText.showTextAligned(stamp.getOverContent(),
					 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
					 	              300,5,0);
				     	  //if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
						    		12,6,0);
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
						    		470, 6, 0);
				     	  //}
						    stamp.alterContents();
						    copy.addPage(page);
						}
					}
				//String LTRSingleDocFooterFormat=propertyInfo.getValue("LTRSingleDocFooterFormat");
				/*if(LTRSingleDocFooterFormat.equals("Yes")){
					 Rectangle pagesize;
						for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
						    page = copy.getImportedPage(reader2, i);
						    stamp = copy.createPageStamp(page);
						    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
						    pagesize = reader2.getPageSizeWithRotation(i);
						    //if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
						    		12, 6, 0);
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
						    		 pagesize.getRight()-128, 6, 0); 
						    //}
						    stamp.alterContents();
						    copy.addPage(page);
						}
					}*/
				 // }
				  
				  
				
				/*for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
				    page = copy.getImportedPage(reader1, i);
				    stamp = copy.createPageStamp(page);
				    
				    int n1=reader1.getNumberOfPages();
		     	   ColumnText.showTextAligned(stamp.getOverContent(),
			 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
			 	              300,5,0);
				    
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
				    		12,6,0);
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
				    		470, 6, 0);
				    stamp.alterContents();
				    copy.addPage(page);
				}*/
				/*if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
					AttrDtl=docMgmtImpl.getAttrDtl(WorkspaceId,nodeId);
					if(AttrDtl.size()>0){
						for (int i = 1; i <= reader3.getNumberOfPages(); i++) {
							  page = copy.getImportedPage(reader3, i);
							    stamp = copy.createPageStamp(page);
							    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
							    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
							    		12, 6, 0);
							    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
							    		470, 6, 0); 
							    stamp.alterContents();
							    copy.addPage(page);
							}
						}
				}*/
				//Rectangle pagesize;
				/*for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
				    page = copy.getImportedPage(reader2, i);
				    stamp = copy.createPageStamp(page);
				    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
				    pagesize = reader2.getPageSizeWithRotation(i);
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
				    		12, 6, 0);
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
				    		 pagesize.getRight()-128, 6, 0); 
				    stamp.alterContents();
				    copy.addPage(page);
				}*/
				
				document.close();
				if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
				reader1.close();}
				reader2.close();
				fos1.close();
			   
			    //out.flush();
			    //d1.close();
			  
			    //out.close();
		         
		         
		         //Other publishing logic ends
				
				FileManager.deleteFile(path);
				File f=new File(baseSrcPath + "/" + WorkspaceId + "/"+ nodeId + "/" + uuid+ "/" + fileName);
				FileManager.deleteFile(f);
				if(showAttributesInPublishPage.equalsIgnoreCase("Yes"))
					FileManager.deleteFile(pathForAttribute);
		         
				
				File oldName = new File(baseSrcPath + "/" + WorkspaceId + "/"+ nodeId + "/" + uuid+ "/" + "Final.pdf");
			    File newName = new File(baseSrcPath + "/" + WorkspaceId + "/"+ nodeId + "/" + uuid+ "/" + fileName); 
			     
			   if (oldName.renameTo(newName))  
			       System.out.println("Renamed successfully");         
			   else 
			      System.out.println("Error");
			}
				 return "html";
			}
			else{

				//uploadedFile = new File(baseSrcPath + "/" + WorkspaceId + "/"+ nodeId + "/" + uuid + "/" + fileName);
				UUID uid=UUID.randomUUID();
				String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
				String srcFlExt = previousHistory.get(0).getFileName().substring(previousHistory.get(0).getFileName().lastIndexOf(".") + 1);
								
				uploadedFile = new File(baseSrcPath + "/" + WorkspaceId + "/" + nodeId + "/" + uuid + "/" + fileNameWithOutExt+"_"+uid+"."+srcFlExt);
			
				
				File srcDoc = new File(baseSrcPath + previousHistory.get(0).getFolderName()
						+ "/" + previousHistory.get(0).getFileName());
				if(uploadedFile.exists()){
					uploadedFile.delete();
				}
				Files.copy(fSrc.toPath(), uploadedFile.toPath());

				 String Name=previousHistory.get(0).getFileName();
				    int pos = Name.lastIndexOf(".");
				    if (pos > 0) {
				    	//Name = Name.substring(0, pos);
				    	Name = Name.substring(0, pos)+"_Sign";
				    }
				String srcFileExt = previousHistory.get(0).getFileName().substring(previousHistory.get(0).getFileName().lastIndexOf(".") + 1);
				if(srcFileExt.equalsIgnoreCase("pdf")){
					System.out.println("Aleready Pdf");
					out = new FileOutputStream(new File(baseSrcPath + "/" + WorkspaceId + "/"
							+ nodeId + "/" + uuid+ "/" + "Final.pdf"));
					uploadFile=uploadedFile;
					}
				else{
					String pdfFileName=Name+".pdf";
					String baseWorkFolder=propertyInfo.getValue("BaseWorkFolder");
					File check =new File(baseWorkFolder + previousHistory.get(0).getFolderName()+ "/"+ pdfFileName+".pdf");
			 		if(check.exists()){
			 			System.out.println("Aleready converted Pdf exists....");
			 			out = new FileOutputStream(new File(baseSrcPath + "/" + WorkspaceId + "/"+ nodeId + "/" + uuid+ "/" + "Final.pdf"));
			 			uploadFile=check;
			 		}
			 		else{
			 			String response="";
						String srcPath=src;
						String destPath=(baseSrcPath + "/" + WorkspaceId + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Main.pdf");
									
						try{
							
							/*String srcDocPath= srcPath.replace('/', '\\');
							String destDocPath= destPath.replace('/', '\\');
									
							String nSrcPath = " \""+srcDocPath+"\" ";
							String nDestPath = " \""+destDocPath+"\" ";*/
							
							System.out.println("srcDocPath="+srcPath);
							System.out.println("destDocPath="+destPath);
							
							//exitFlag=DocToPdf(nSrcPath,nDestPath);
							//response=docMgmtImpl.convertDoc(srcPath,destPath);
							//response=docMgmtImpl.convertDoc(uploadedFile.getAbsolutePath(),destPath);
							/*response=docMgmtImpl.convertPdfToDoc(srcPath,destPath);
							
							String[] parts = response.split(":");
							if(parts[0].equalsIgnoreCase("error")){
								htmlContent=parts[2];
								return "html";
							}*/
							 response=docMgmtImpl.convertDoc(srcPath,destPath);
							
							 String[] parts = response.split("#");
							if(parts[0].equalsIgnoreCase("False")){
								htmlContent=parts[2];
								return "html";
							}
						
						}catch (Exception e) {
							// TODO: handle exception
							System.out.println(e);
						}
						
						String[] parts = response.split("#");
						if(parts[0].equalsIgnoreCase("False")){
							htmlContent=parts[2];
							return "html";
						}
									
						uploadFile = new File(baseSrcPath + "/" + WorkspaceId + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Main.pdf");
						out = new FileOutputStream(new File(baseSrcPath + "/" + WorkspaceId + "/"+ nodeId + "/" + uuid+ "/" + Name +".pdf"));
						FileManager.deleteFile(uploadedFile);
			 		}
					
					
				}
				
				
				//Creating temp file to write data and later will be merged with new PDF
				
				/*ArrayList<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl
						.getPDFSignOffDocDetail(workspaceID, nodeId);
				*/
				
				Vector<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl.getProjectSignOffHistory(WorkspaceId, nodeId);
				
				DTOWorkSpaceMst wsDetail =docMgmtImpl.getWorkSpaceDetail(WorkspaceId);
				File pathForAttribute = null;
				File path=new File(baseSrcPath + "/" + WorkspaceId + "/"
						+ nodeId + "/" + uuid +"/Temp.pdf");
				if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
				pathForAttribute=new File(baseSrcPath + "/" + WorkspaceId + "/"
						+ nodeId + "/" + uuid +"/attr.pdf");
				}
				
				//Document document=new Document();
				//PdfCopy copy = new PdfCopy(document, new FileOutputStream(path));
				//PdfReader pdfReader;
				//PdfStamper pdfStamper;
				String signatureFilePath=propertyInfo.getValue("signatureFile");
				Document d1 = new Document();
				//Document document = new Document(PageSize.A4, 30, 30, 550, 800);
				OutputStream fos = new FileOutputStream(path);
				
			    PdfWriter pdfWriter = PdfWriter.getInstance(d1, fos);
			    Rectangle rectangle = new Rectangle(30, 30, 550, 800);
			    
			    pdfWriter.setBoxSize("rectangle", rectangle);
			   /* ColumnText.showTextAligned(pdfWriter.getDirectContent(),
			               Element.ALIGN_CENTER, new Phrase("Powered By Mark47"),
			               110, 30, 0);*/
			    d1.open();
				Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
				
				float[] widthsForTable = { 1.3f, 3 };
				PdfPTable table = new PdfPTable(widthsForTable);
				table.setWidthPercentage(100);
				//System.out.println(tempHistory.size()+"\n");
				//String Company="SSPL";
				
				float[] widths = { 1, 3 };
				PdfPTable Htable = new PdfPTable(widths);
				Htable.setWidthPercentage(100);
				/*Htable.setHorizontalAlignment(0);
				Htable.setTotalWidth(500f);
				Htable.setLockedWidth(true);
				/*float[] widths = new float[] { 20f, 50f, 30f};*/
				//Htable.setWidths(widths);
				/*PdfPCell cell;
				PdfPCell nodeName = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Project Name : "+docMgmtImpl.getWorkSpaceDetail(workspaceID).getWorkSpaceDesc()));*/
				
				PdfPCell HPName = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Document Type"));
				HPName.setPadding(8f);
				Htable.addCell(HPName);
		        //PdfPCell PName = new PdfPCell(new Phrase(docMgmtImpl.getWorkSpaceDetail(WorkspaceId).getWorkSpaceDesc()));
		        PdfPCell PName = new PdfPCell(new Phrase(docMgmtImpl.getDocTypeName(docType)));
		        PName.setPadding(8f);
		        Htable.addCell(PName);
		        
		        PdfPCell HNname = new PdfPCell(new Phrase("Document Name  "));
		        HNname.setPadding(8f);
		        Htable.addCell(HNname);
		        
		        PdfPCell Nname = new PdfPCell(new Phrase(docMgmtImpl.getNodeDetail(WorkspaceId, nodeId).get(0).getFolderName()));
		        Nname.setPadding(8f);
		        Htable.addCell(Nname);
		        
		        Vector<DTOWorkSpaceNodeAttribute> AttrDtlForSignOff = null;
		        //AttrDtlForSignOff=docMgmtImpl.getAttrDtForPdfHeader(WorkspaceId,nodeId);
		        AttrDtlForSignOff=docMgmtImpl.getAttrDtlForCoverPage(WorkspaceId,nodeId);
		        if(AttrDtlForSignOff.size()>0){
		        for(int i=0;i<AttrDtlForSignOff.size();i++){
					//System.out.println("Name : "+AttrDtl.get(i).getAttrName()+"     Value : " + AttrDtl.get(i).getAttrValue());
					
					PdfPCell cell5 = new PdfPCell(new Phrase(AttrDtlForSignOff.get(i).getAttrName()));
		            PdfPCell cell6 = new PdfPCell(new Phrase(AttrDtlForSignOff.get(i).getAttrValue()));
		            cell5.setPadding(8f);
		            Htable.addCell(cell5);
		            cell6.setPadding(8f);
		            Htable.addCell(cell6);
		            Htable.completeRow();
		        	}
		        }
				/*PdfPCell nodeName = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Project Name : "+docMgmtImpl.getWorkSpaceDetail(workspaceID).getWorkSpaceDesc()));
				nodeName.setColspan(2);
				nodeName.setPadding(5f);
				nodeName.setLeading(1f, 1.4f);
				nodeName.setBorder(Rectangle.BOX);
				nodeName.setBackgroundColor(new BaseColor(255,255,255));
				nodeName.setHorizontalAlignment(Element.ALIGN_CENTER);
				nodeName.setVerticalAlignment(Element.ALIGN_CENTER);
				Htable.addCell(nodeName);
				//Htable.completeRow();
				
				PdfPCell cell = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Document Name : "+docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0).getNodeName()));
				cell.setColspan(2);
				cell.setPadding(5f);
				cell.setLeading(1f, 1.4f);
				cell.setBorder(Rectangle.BOX);
				cell.setBackgroundColor(new BaseColor(255,255,255));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				Htable.addCell(cell);*/
				//Htable.completeRow();
		        
		        PdfPCell HSpace = new PdfPCell();
		        HSpace.setColspan(2);
		        HSpace.setPadding(8f);
		        HSpace.setLeading(1f, 1.4f);
		        HSpace.setBorder(rectangle.NO_BORDER);
		        Htable.addCell(HSpace);
		        
		        PdfPCell cell1=new PdfPCell();
		      if(!wsDetail.getProjectCode().equals("0003")){
					System.out.println("Project Type : "+wsDetail.getProjectCode());
				cell1 = new PdfPCell(new Phrase(Element.ALIGN_CENTER,"Sign Off"));
				cell1.setColspan(4);
				cell1.setPadding(2f);
				cell1.setLeading(1f, 1.4f);
		        

				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.setBackgroundColor(new BaseColor(255,255,255));
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setVerticalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell1);
			 }
				PdfPCell Space = new PdfPCell();
				Space.setColspan(4);
				Space.setPadding(15f);
				Space.setLeading(1f, 1.4f);
				
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
			if(!wsDetail.getProjectCode().equals("0003")){
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
			        title.setPaddingTop(-5f);
			        title.setPaddingRight(1f);
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
			        String signdate;
			        Phrase phrase = new Phrase();
			        
			        if(Created.get(c).getCountryCode().equalsIgnoreCase("IND")){
			        	signdate = Created.get(c).getISTDateTime();  
				     }
				     else{
				    	 signdate = Created.get(c).getESTDateTime();  
				    }
					  Chunk chunk = new Chunk("\nName : "+Created.get(c).getUserName()+" \n"
							  	+ "Date : "+signdate+" \n"
							  	+ "Role : "+Created.get(c).getRoleName()+" \n"
					      		+ "Sign Id: "+Created.get(c).getSignId()+" \n"
					      		,font2);
					  String appUrl=propertyInfo.getValue("ApplicationUrl");
					  Vector<DTOWorkSpaceNodeHistory> data=docMgmtImpl.getWorkspacenodeHistoryByUserId(WorkspaceId, nodeId, Created.get(c).getUserCode());
					  String docId=docMgmtImpl.getDocIdForSign(WorkspaceId,nodeId,Created.get(c).getTranNo());
					  chunk.setAnchor(appUrl+"FileOpenFromPDF.do?wsId="+WorkspaceId+"&nodeId="+nodeId+"&tranNo="+data.get(0).getTranNo()
							  +"&userCode="+Created.get(c).getUserCode()+"&docId="+docId);
					  phrase.add(chunk);
					  
					  PdfPCell cellOne = new PdfPCell(new Phrase(phrase));
			        cellOne.setHorizontalAlignment(Element.ALIGN_LEFT);  
					  cellOne.setBorderWidthLeft(0f);
					  cellOne.setVerticalAlignment(Element.ALIGN_MIDDLE);
					  cellOne.setFixedHeight(70f);
					  cellOne.setPaddingTop(-5f);
					  cellOne.setPaddingLeft(25f);
					  cellOne.setPaddingRight(1f);
					  table.addCell(cellOne);
			        
			        //table.addCell(title);
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
				    		style=Reviewed.get(r).getStyle();
				    		Font font1 = ft.registerExternalFont(style,"No");
						    if(!style.isEmpty())
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
			        Phrase phrase = new Phrase();
			        String signdate;
			        
			        if(Reviewed.get(r).getCountryCode().equalsIgnoreCase("IND")){
			        	signdate = Reviewed.get(r).getISTDateTime();  
				     }
				     else{
				    	 signdate = Reviewed.get(r).getESTDateTime();  
				    }
					  Chunk chunk = new Chunk("\nName : "+Reviewed.get(r).getUserName()+" \n"
							  	+ "Date : "+signdate+" \n"
							  	+ "Role : "+Reviewed.get(r).getRoleName()+" \n"
					      		+ "Sign Id: "+Reviewed.get(r).getSignId()+" \n"
					      		,font2);
					  String appUrl=propertyInfo.getValue("ApplicationUrl");
					  Vector<DTOWorkSpaceNodeHistory> data=docMgmtImpl.getWorkspacenodeHistoryByUserId(WorkspaceId, nodeId, Reviewed.get(r).getUserCode());
					  String docId=docMgmtImpl.getDocIdForSign(WorkspaceId,nodeId,Reviewed.get(r).getTranNo());
					  chunk.setAnchor(appUrl+"FileOpenFromPDF.do?wsId="+WorkspaceId+"&nodeId="+nodeId+"&tranNo="+data.get(0).getTranNo()
							  +"&userCode="+Reviewed.get(r).getUserCode()+"&docId="+docId);
					  phrase.add(chunk);
					  
					  PdfPCell cellOne = new PdfPCell(new Phrase(phrase));
					  
					  //cellOne.setVerticalAlignment(Element.ALIGN_CENTER);
					  cellOne.setHorizontalAlignment(Element.ALIGN_LEFT);  
					  cellOne.setBorderWidthLeft(0f);
					  cellOne.setVerticalAlignment(Element.ALIGN_MIDDLE);
					  cellOne.setFixedHeight(70f);
					  cellOne.setPaddingTop(-5f);
					  cellOne.setPaddingLeft(25f);
					  cellOne.setPaddingRight(1f);
					  
					  //cellOne.setBorder(Rectangle.NO_BORDER);
					  //cellOne.setBackgroundColor(new BaseColor(255,255,255));
				      //pdfPTable.addCell(pdfPCell2);
					  table.addCell(cellOne);
			        //table.addCell(title);
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
				    		style=Approved.get(a).getStyle();
				    		Font font1 = ft.registerExternalFont(style,"No");
						    if(!style.isEmpty())
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
			        Phrase phrase = new Phrase();
			        String signdate;
			        
			        if(Approved.get(a).getCountryCode().equalsIgnoreCase("IND")){
			        	signdate = Approved.get(a).getISTDateTime();  
				     }
				     else{
				    	 signdate = Approved.get(a).getESTDateTime();  
				    }
					  Chunk chunk = new Chunk("\nName : "+Approved.get(a).getUserName()+" \n"
							  	+ "Date : "+signdate+" \n"
							  	+ "Role : "+Approved.get(a).getRoleName()+" \n"
					      		+ "Sign Id: "+Approved.get(a).getSignId()+" \n"
					      		,font2);
					  String appUrl=propertyInfo.getValue("ApplicationUrl");
					  Vector<DTOWorkSpaceNodeHistory> data=docMgmtImpl.getWorkspacenodeHistoryByUserId(WorkspaceId, nodeId, Approved.get(a).getUserCode());
					  String docId=docMgmtImpl.getDocIdForSign(WorkspaceId,nodeId,Approved.get(a).getTranNo());
					  chunk.setAnchor(appUrl+"FileOpenFromPDF.do?wsId="+WorkspaceId+"&nodeId="+nodeId+"&tranNo="+data.get(0).getTranNo()
							  +"&userCode="+Approved.get(a).getUserCode()+"&docId="+docId);
					  phrase.add(chunk);
					  
					  PdfPCell cellOne = new PdfPCell(new Phrase(phrase));
					  
					  //cellOne.setVerticalAlignment(Element.ALIGN_CENTER);
					  cellOne.setHorizontalAlignment(Element.ALIGN_LEFT);  
					  cellOne.setBorderWidthLeft(0f);
					  cellOne.setVerticalAlignment(Element.ALIGN_MIDDLE);
					  cellOne.setFixedHeight(70f);
					  cellOne.setPaddingTop(-5f);
					  cellOne.setPaddingLeft(25f);
					  cellOne.setPaddingRight(1f);
					  
					  //cellOne.setBorder(Rectangle.NO_BORDER);
					  //cellOne.setBackgroundColor(new BaseColor(255,255,255));
				      //pdfPTable.addCell(pdfPCell2);
					  table.addCell(cellOne);
			        //table.addCell(title);
			        table.completeRow();
			        
			        PdfPCell Space1 = new PdfPCell();
			        Space1.setColspan(4);
			        Space1.setPadding(20f);
			        Space1.setLeading(1f, 1.4f);
			        System.out.println("Approved");
				}
				/*for(int at=0;at<Authorized.size();at++){
					if(at==0){
					PdfPCell cell11 = new PdfPCell(new Phrase(Element.ALIGN_LEFT,"Authorized By : "));
					cell11.setColspan(2);
					cell11.setBorder(0);
					cell11.setPadding(20f);
					cell11.setLeading(1f, 1.4f);
					cell1.setBorder(Rectangle.NO_BORDER);
					cell1.setBackgroundColor(new BaseColor(255,255,255));
					cell11.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell11.setVerticalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell11);	
					
					PdfPCell Dc = new PdfPCell(new Phrase("Name"));
			        PdfPCell Mo = new PdfPCell(new Phrase("Signature"));
			        Dc.setHorizontalAlignment(Element.ALIGN_CENTER);
			        Dc.setBackgroundColor(BaseColor.LIGHT_GRAY);
			        
			        Mo.setHorizontalAlignment(Element.ALIGN_CENTER);
			        Mo.setBackgroundColor(BaseColor.LIGHT_GRAY);
			        table.addCell(Dc);
			        table.addCell(Mo);
					
			        table.completeRow();
					}
					PdfPCell title = new PdfPCell(new Phrase(Authorized.get(at).getUserName()));
			    	
					PdfPCell mOn = new PdfPCell();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		            Date date1 = sdf.parse("1900-01-01 00:00:00.000");
		            Date date2 = sdf.parse(Authorized.get(at).getModifyOn().toString());

		            String imageFile =signatureFilePath+"/"+Authorized.get(at).getFilePath()+"//"+Authorized.get(at).getFileName();
		            //String imageFile = "//90.0.0.15//KnowledgeNET-CSV//userSignature//3//18//sign1.png";
		            File f=new File(imageFile);
		            Image image = null;
		            if(f.exists())
	      	        	{image = Image.getInstance(imageFile);}
	      	        	//image.scaleAbsolute(200, 700);}
		            if(date1.before(date2)){
		            			if(Authorized.get(at).getSignId() != null){
		            				image.scaleToFit(150f, 50f); //Scale image's width and height
		  		                  //image.setAbsolutePosition(200, 700); //Set position for image in PDF
		            	}
					mOn = new PdfPCell(new Phrase(Authorized.get(at).getUserName()+" \n "+Authorized.get(at).getISTDateTime()));}
		            else{
		            	mOn = new PdfPCell(new Phrase(""));
		            }
		            title.setVerticalAlignment(Element.ALIGN_CENTER);
			        title.setHorizontalAlignment(Element.ALIGN_CENTER);
			        mOn.setHorizontalAlignment(Element.ALIGN_CENTER);
			        
			        table.addCell(title);
			       // PdfPCell imgCell = new PdfPCell(image, true);
			        //table.addCell(imgCell);
			        if(Authorized.get(at).getSignId() != null){
			        Paragraph paragraph = new Paragraph(Authorized.get(at).getISTDateTime());
			        Font fontColour_Red =  FontFactory.getFont(Authorized.get(at).getStyle(), 12f, Font.NORMAL, BaseColor.BLACK);
			        Paragraph p=new Paragraph(Authorized.get(at).getUserName(),fontColour_Red);
			        String style=Authorized.get(at).getStyle();
				    Font font1 = null;
				    Paragraph p = null;	      
				    font1=ft.registerExternalFont(style);
				    p=new Paragraph(Authorized.get(at).getUserName(),font1);
			        image.setAlignment(image.ALIGN_CENTER);
			        p.setAlignment(p.ALIGN_CENTER);
			        paragraph.setAlignment(paragraph.ALIGN_CENTER);
			        mOn.addElement(image);
			        mOn.addElement(p);
			        mOn.addElement(paragraph);
			        mOn.setFixedHeight(100);
			        }else{
			        	mOn = new PdfPCell(new Phrase(Authorized.get(at).getUserName()+" \n "+Authorized.get(at).getISTDateTime()));
			        	 mOn.setHorizontalAlignment(Element.ALIGN_CENTER);
			        }
			       
			        table.addCell(mOn);
			        table.completeRow();
			        
			        PdfPCell Space1 = new PdfPCell();
			        Space1.setColspan(4);
			        Space1.setPadding(20f);
			        Space1.setLeading(1f, 1.4f);
				}*/
			}

			    Rectangle rect = pdfWriter.getBoxSize("rectangle");
			    d1.add(Htable);
			    d1.add(table);
			    
				 d1.close();
				 fos.close();
				 
				 //for attach attribute list in PDF
				
				 Vector<DTOWorkSpaceNodeAttribute> AttrDtl = null;
				 Vector<DTOWorkSpaceNodeAttribute> AttrDtlForPageSetting = null;
				 
				 if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
					AttrDtl=docMgmtImpl.getAttrDtl(WorkspaceId,nodeId);
			 if(AttrDtl.size()>0){
				  Document d2 = new Document();
				  OutputStream attrfos = new FileOutputStream(pathForAttribute);
					
			      PdfWriter pdfWriter1 = PdfWriter.getInstance(d2, attrfos);
			      Rectangle rectangle1 = new Rectangle(30, 30, 550, 800);
			      pdfWriter1.setBoxSize("rectangle", rectangle1);
			      
			      	d2.open();
					Font font1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
					
					PdfPTable table1 = new PdfPTable(2);
					table1.setWidthPercentage(100);
				
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
			    userName = ActionContext.getContext().getSession().get("username")
						.toString();
			    Font fontSize_12 =  FontFactory.getFont(FontFactory.TIMES, 10f);
			    boolean c=false;
			    
			    String file1=path.toString();
				String file2=uploadFile.toString();
				String file3="";
				if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
					file3=pathForAttribute.toString();
				}
				String outs = null;
				if(srcFileExt.equalsIgnoreCase("pdf")){
					System.out.println("Aleready Pdf");
					outs = baseSrcPath + "/" + WorkspaceId + "/"+ nodeId + "/" + uuid+ "/" + "Final.pdf";
				}
				else{
					outs=baseSrcPath + "/" + WorkspaceId + "/"+ nodeId + "/" + uuid+ "/" + Name +".pdf";
				}
				PdfReader reader1 = new PdfReader(file1);
				PdfReader reader2 = new PdfReader(file2);
				PdfReader reader3=null;
				if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
					AttrDtl=docMgmtImpl.getAttrDtl(WorkspaceId,nodeId);
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
				int n = reader1.getNumberOfPages();
				
				  AttrDtlForPageSetting=docMgmtImpl.getAttrDtlForPageSetting(WorkspaceId,nodeId);
				  String signOffPlacement="";
				  String printPageNo="";
				  if(AttrDtlForPageSetting.size()>0){
					  
					  for(int s=0;s<AttrDtlForPageSetting.size();s++){
							if(AttrDtlForPageSetting.get(s).getAttrName().equalsIgnoreCase("Sign Page Placement")){
								signOffPlacement=AttrDtlForPageSetting.get(s).getAttrValue();break;
							}
						}
				  }
				

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
											 	              300,5,0);}
								    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
												    		12,6,0);
												    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
												    		470, 6, 0);
								    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
								    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
												    		150,6,0);
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
								    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
								    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
												    		5,6,0);
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
								    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
								    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
												    		320,6,0);
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
								    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
								    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
								    						150,6,0);
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
								    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
								    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
												    		5,6,0);
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
								    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
								    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
												    		320,6,0);
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
								    //if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
								    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
								    		12, 6, 0);
								    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
								    		 pagesize.getRight()-128, 6, 0);
								  /*  ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
				    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
								    		160,6,0);*/
								    //}
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
						     	  //if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
								    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
								    		12,6,0);
								    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
								    		470, 6, 0);
						     	  /*ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
				    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
								    		160,6,0);*/
						     	  //}
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
							    //if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
							    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
							    		12, 6, 0);
							    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
							    		 pagesize.getRight()-128, 6, 0);
							    /*ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
			    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
							    		160,6,0);*/
							    //}
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
									    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
									    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
													    		150,6,0);
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
									    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
									    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
													    		5,6,0);
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
									    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
									    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
													    		320,6,0);
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
									    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
									    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
													    		150,6,0);
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
									    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
									    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
													    		5,6,0);
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
									    				ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
									    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
													    		320,6,0);
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
							     	  //if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
									    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
									    		12,6,0);
									    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
									    		470, 6, 0);
							     	 /* ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
					    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
									    		160,6,0);*/
							     	  //}
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
							    //if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
							    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
							    		12, 6, 0);
							    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
							    		 pagesize.getRight()-128, 6, 0);
							    /*ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
			    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
							    		160,6,0);*/
							    //}
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
						     	  //if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
								    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
								    		12,6,0);
								    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
								    		470, 6, 0);
						     	/*  ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase
				    						("This is system generated document, hence No Handwritten signature is required",fontSize_12),
								    		160,6,0);*/
						     	  //}
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
				     	  //if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
						    		12,6,0);
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
						    		470, 6, 0);
				     	  //}
						    stamp.alterContents();
						    copy.addPage(page);
						}
					 Rectangle pagesize;
						for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
						    page = copy.getImportedPage(reader2, i);
						    stamp = copy.createPageStamp(page);
						    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
						    pagesize = reader2.getPageSizeWithRotation(i);
						    //if(!wsDetail.getProjectCode().equals("0003") && nodeTypeIndi!='K'){
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
						    		12, 6, 0);
						    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
						    		 pagesize.getRight()-128, 6, 0); 
						    //}
						    stamp.alterContents();
						    copy.addPage(page);
						}
					  
				  }
				  
				  
				
				/*for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
				    page = copy.getImportedPage(reader1, i);
				    stamp = copy.createPageStamp(page);
				    
				    int n1=reader1.getNumberOfPages();
		     	   ColumnText.showTextAligned(stamp.getOverContent(),
			 	               Element.ALIGN_CENTER, new Phrase(String.format("Page %s of %s", i, n1),fontSize_12),
			 	              300,5,0);
				    
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
				    		12,6,0);
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
				    		470, 6, 0);
				    stamp.alterContents();
				    copy.addPage(page);
				}*/
				if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
					AttrDtl=docMgmtImpl.getAttrDtl(WorkspaceId,nodeId);
					if(AttrDtl.size()>0){
						for (int i = 1; i <= reader3.getNumberOfPages(); i++) {
							  page = copy.getImportedPage(reader3, i);
							    stamp = copy.createPageStamp(page);
							    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
							  /*  ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
							    		12, 6, 0);
							    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
							    		470, 6, 0); */
							    stamp.alterContents();
							    copy.addPage(page);
							}
						}
				}
				Rectangle pagesize;
				/*for (int i = 1; i <= reader2.getNumberOfPages(); i++) {
				    page = copy.getImportedPage(reader2, i);
				    stamp = copy.createPageStamp(page);
				    //phrase = new Phrase("Printed By : "+userName, fontSize_12);
				    pagesize = reader2.getPageSizeWithRotation(i);
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_LEFT, new Phrase("Printed By : "+userName,fontSize_12),
				    		12, 6, 0);
				    ColumnText.showTextAligned(stamp.getOverContent(), Element.ALIGN_CENTER, new Phrase("Print Date & Time : "+dtf,fontSize_12),
				    		 pagesize.getRight()-128, 6, 0); 
				    stamp.alterContents();
				    copy.addPage(page);
				}*/
				
				document.close();
				reader1.close();
				reader2.close();
				fos1.close();
			   
			    out.flush();
			    d1.close();
			  
			    out.close();
			    
			    Files.delete(Paths.get(path.toString()));
			    if(showAttributesInPublishPage.equalsIgnoreCase("Yes")){
			    	if(AttrDtl.size()>0 ){
			    		Files.delete(Paths.get(pathForAttribute.toString()));
			    	}
			    }
			    Files.delete(Paths.get(uploadFile.toString()));
			    	    
			    File oldName = new File(baseSrcPath + "/" + WorkspaceId + "/"
						+ nodeId + "/" + uuid+ "/" + "Final.pdf"); 
			    File newName = new File(baseSrcPath + "/" + WorkspaceId + "/"
						+ nodeId + "/" + uuid+ "/" + Name +".pdf"); 
			     
			   if (oldName.renameTo(newName))  
			       System.out.println("Renamed successfully");         
			   else 
			      System.out.println("Error"); 
			   src=(String) nodeRec[9];
			   fileName=previousHistory.get(0).getFileName();
				src+="/"+previousHistory.get(0).getFolderName()+"/"+ Name +".pdf";
			   
			   File signOffFile = new File(src);
			   
			   FileUtils.copyFile(newName, signOffFile);
				 //DownloadFolderPath=baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name+".pdf";
			   DownloadFolderPath = signOffFile.toString().replace('\\', '/');
			   
				 //DownloadFolderPath=baseSrcPath + "/" + WorkspaceId + "/"+ nodeId + "/" + uuid+ "/" + Name+".pdf";
 
				 htmlContent=DownloadFolderPath;
				 
				return "html";
			
			}
					}
		
		public String DeleteNode()
		{
			DTOWorkSpaceNodeDetail dto = docMgmtImpl.getNodeDetail(WorkspaceId,nodeId).get(0);
			
			docMgmtImpl.deleteNodeDetail(WorkspaceId, nodeId,remark);
			
			return SUCCESS;
		}
		public String AttributeDetailHistory(){
			
			nodeId= recordId;
			WorkspaceId = docId;
			getNodeAttrDetailHistory = docMgmtImpl.getWsNodeAttrDetailHistoryByAttrId(WorkspaceId,nodeId);
			
			return SUCCESS;
		}

public Vector<DTOWorkSpaceNodeAttrDetail> getGetProjectLevelSearchResult() {
	return getProjectLevelSearchResult;
}

public void setGetProjectLevelSearchResult(Vector<DTOWorkSpaceNodeAttrDetail> getProjectLevelSearchResult) {
	this.getProjectLevelSearchResult = getProjectLevelSearchResult;
}

public Vector<Object[]> getGetAttributeDetailsForDisplay() {
	return getAttributeDetailsForDisplay;
}

public void setGetAttributeDetailsForDisplay(Vector<Object[]> getAttributeDetailsForDisplay) {
	this.getAttributeDetailsForDisplay = getAttributeDetailsForDisplay;
}

public List<List<String>> getGetAttributeValues() {
	return getAttributeValues;
}

public void setGetAttributeValues(List<List<String>> getAttributeValues) {
	this.getAttributeValues = getAttributeValues;
}

public List<List<String>> getGetAttributeValuesForDisplay() {
	return getAttributeValuesForDisplay;
}

public void setGetAttributeValuesForDisplay(List<List<String>> getAttributeValuesForDisplay) {
	this.getAttributeValuesForDisplay = getAttributeValuesForDisplay;
}
	
}
