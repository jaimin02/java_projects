	package com.docmgmt.struts.actions.workspace;

	import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.entities.Workspace.FontTypes;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.docmgmt.struts.actions.reports.StageWiseMailReport;
import com.itextpdf.text.DocumentException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

	public class ManualSignatureActionForEsignature extends ActionSupport{
		private static final long serialVersionUID = 1L;

		InputStream inputStream;
		String contentDisposition;
		String contentType;
		InputStream logoStream;

		String attachment;
		String fileWithPath;
		String fileName;
		String workSpaceId;
		String nodeId;
		String tranNo;
		String baseWorkFolder;
		String fileExt;
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String srcFileExt;
		
		String cropSignPdfPath;
		public float xCordinates;
		public float yCordinates;
		public float x2Cordinates;
		public float y2Cordinates;
		public float wCordinates;
		public float hCordinates;
		
		public String htmlContent = "";
		public String b64 ="";
		public Vector <DTOWorkSpaceNodeHistory> dtoWsHistory;
		public String userName;
		public int usercode;
		public String imgFilename="";
		public ArrayList<DTOWorkSpaceNodeHistory> previousHistory=new ArrayList<>();
		public File uploadedFile;
		public File uploadFile;
		OutputStream out;
		public int pageNo;
		public String uName;
		public String role;
		public String date;
		public String signId;
		FontTypes ft =  new FontTypes();
		public boolean lockSeqFlag;
		public int stageId;
		public String singleDocFlag;
		public Vector<DTOWorkSpaceNodeHistory> getPreviewDetail;
		public String userNameForPreview;
		public String signIdForPreview;
		public String signImg;
		public String signStyle;
		public String roleName;
		ArrayList<String> time = new ArrayList<String>();
		public String dateForPreview;
		public boolean signatureFlag=false;
		public String signPath;
		public String fontStyle="";
		public String workFolder="";
		public String srFlag="";
		public String remark;
		public String eSign="N";
		Vector<DTOWorkSpaceUserRightsMst> getAdjustDateData=new Vector<>();
		public boolean getCompletedNodeStageDetail;
		public String selectedStage;
		public int useRightStage;
		public String projectType;
		public Vector<DTOStageMst> stageuserdtl;
		
		@Override
		public String execute() throws NumberFormatException, SQLException, IOException {
			
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			
			userName=ActionContext.getContext().getSession().get("username").toString();
			usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			int userGrpCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			//useRightStage=docMgmtImpl.getStageIdsRightsWise(workSpaceId, usercode,userGrpCode,Integer.parseInt(nodeId)).get(0);
			stageuserdtl = docMgmtImpl.geteSignatureStageDetail();
			Vector<DTOWorkSpaceNodeHistory> dto = docMgmtImpl.getMaxNodeHistoryByTranNo(workSpaceId, Integer.parseInt(nodeId));
			projectType=docMgmtImpl.getWorkSpaceDetail(workSpaceId).getProjectCode();
			String baseworkFolder=propertyInfo.getValue("BaseWorkFolder");
			String baseSrcPath = propertyInfo.getValue("DoToPdfPath");
			//fileName=baseworkFolder+dto.get(0).getBaseWorkFolder()+"/"+dto.get(0).getFileName();
			fileName=dto.get(0).getFileName();
			
			Vector <DTOWorkSpaceNodeHistory> dtoHistory;
			dtoHistory =docMgmtImpl.getUserSignatureDetail(usercode);
			workFolder = propertyInfo.getValue("signatureFile");
			if(dtoHistory.size()>0){
				signatureFlag = true;
				signPath = dtoHistory.get(0).getFileName();
				fontStyle = dtoHistory.get(0).getFileType();
			}
			Vector <DTOWorkSpaceNodeHistory> getSignDetail = docMgmtImpl.getUserSignatureDetail(usercode);
			
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
			if(!fileExt.equalsIgnoreCase("pdf")){
				
			int lastTranNo = docMgmtImpl.getMaxTranNo(workSpaceId, Integer.parseInt(nodeId));
			previousHistory = docMgmtImpl.getWorkspaceNodeHistory(workSpaceId, Integer.parseInt(nodeId),lastTranNo);
			UUID uuid=UUID.randomUUID();
			

			Vector<Object[]> WsDetail= docMgmtImpl.getWorkspaceDescList(workSpaceId);
			Object[] nodeRec = (Object[]) WsDetail.get(0);
			String src=(String) nodeRec[9];
			
			String fileName=previousHistory.get(0).getFileName();
			//sString src=previousHistory.get(0).getFolderName();
			//src+="/"+previousHistory.get(0).getWorkSpaceId()+"/"+previousHistory.get(0).getNodeId()+"/"+previousHistory.get(0).getTranNo()+"/"+ previousHistory.get(0).getFileName();
			src+=previousHistory.get(0).getFolderName()+"/"+ previousHistory.get(0).getFileName();
			
			String fNameWithOutExt = FilenameUtils.removeExtension(fileName);
			if(fNameWithOutExt.contains(" "))
				fNameWithOutExt=fNameWithOutExt.replace(' ', '_');
			File check =new File(nodeRec[9]+previousHistory.get(0).getFolderName()+"/"+ fNameWithOutExt+".pdf");
			
			if(check.exists()){
				DTOUserMst dtouser = docMgmtImpl.getUserByCode(usercode);
				dtoWsHistory =docMgmtImpl.getUserSignatureDetail(usercode);
				baseWorkFolder = propertyInfo.getValue("signatureFile");
				if(dtoWsHistory.size()>0){
					imgFilename = dtoWsHistory.get(0).getFileName();
					uName = dtoWsHistory.get(0).getUserName();
					//role = dtoWsHistory.get(0).getRoleName();
					role = dtouser.getRoleName();
					//date = dtoWsHistory.get(0).getModifyOn();
					Timestamp currentTime = new Timestamp(System.currentTimeMillis());
					if(countryCode.equalsIgnoreCase("IND")){
						time = docMgmtImpl.TimeZoneConversion(dtoWsHistory.get(0).getModifyOn(),locationName,countryCode);
						dateForPreview = time.get(0);
					}
					else{
						time = docMgmtImpl.TimeZoneConversion(dtoWsHistory.get(0).getModifyOn(),locationName,countryCode);
						dateForPreview = time.get(1);
					}
					//signId = dtoWsHistory.get(0).getUuId();
					signId = dtoWsHistory.get(0).getFolderName().split("/")[2];
					stageId=previousHistory.get(0).getStageId();
					srFlag=previousHistory.get(0).getFileType();
					if((signImg==null || signImg.isEmpty()) && getSignDetail.size()>0){
						signStyle =getSignDetail.get(0).getFileType();
					}
				}
				
				
				String fname="";
				if(dto.size()>0){
					fname = baseworkFolder+dto.get(0).getBaseWorkFolder()+"/"+fNameWithOutExt +".pdf";
					File file = new File(fname);
					byte[] bytes = null;
					try {
						bytes = Files.readAllBytes(file.toPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				      b64 = Base64.getEncoder().encodeToString(bytes);
				}
				srcFileExt = "pdf";
				stageId=previousHistory.get(0).getStageId();
				srFlag=previousHistory.get(0).getFileType();
				return SUCCESS;
			}
			else{			
				File fSrc=new File(src);
				File wsFolder = new File(baseSrcPath + "/" + workSpaceId);
				wsFolder.mkdirs();

				File nodeFolder = new File(baseSrcPath + "/" + workSpaceId + "/"
						+ nodeId);
				nodeFolder.mkdirs();

				//if (lastTranNo != -1) {
					File tranFolder = new File(baseSrcPath + "/" + workSpaceId + "/"
							+ nodeId + "/" + uuid);
					tranFolder.mkdirs();
				//}
				
				//uploadedFile = new File(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid + "/" + fileName);
					UUID uid=UUID.randomUUID();
					String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
					String srcFlExt = previousHistory.get(0).getFileName().substring(previousHistory.get(0).getFileName().lastIndexOf(".") + 1);
									
					uploadedFile = new File(baseSrcPath + "/" + workSpaceId + "/" + nodeId + "/" + uuid + "/" + fileNameWithOutExt+"_"+uid+"."+srcFlExt);
				
				File srcDoc = new File(baseSrcPath + previousHistory.get(0).getFolderName()
						+ "/" + previousHistory.get(0).getFileName());
				if(uploadedFile.exists()){
					uploadedFile.delete();
				}
				Files.copy(fSrc.toPath(), uploadedFile.toPath());

				 String Name=previousHistory.get(0).getFileName();
				 if(Name.contains(" "))
					 Name=Name.replace(' ', '_');
				 int pos = Name.lastIndexOf(".");
				    if (pos > 0) {
				    	Name = Name.substring(0, pos);
				    }
				String srcFileExt = previousHistory.get(0).getFileName().substring(previousHistory.get(0).getFileName().lastIndexOf(".") + 1);
				if(srcFileExt.equalsIgnoreCase("pdf")){
					System.out.println("Aleready Pdf");
					out = new FileOutputStream(new File(baseSrcPath + "/" + workSpaceId + "/"
							+ nodeId + "/" + uuid+ "/" + "Final.pdf"));
					uploadFile=uploadedFile;
					}
				else{
					String response="";
					//String srcPath=src.replace("\\", "/");
					String srcPath=src;
					//String destPath=(baseSrcPath + "/" + workspaceID + "/"+ nodeId + "/" + uuid+ "/" + Name +"_Main.pdf").replace("\\", "/");
					String destPath=(baseSrcPath + "/" + workSpaceId + "/"+ nodeId + "/" + uuid+ "/" + Name +".pdf");
								
					try{
						System.out.println("srcDocPath="+srcPath);
						System.out.println("destDocPath="+destPath);
						//response=docMgmtImpl.convertDoc(uploadedFile.getAbsolutePath(),destPath);
						response=docMgmtImpl.convertPdfToDoc(srcPath,destPath);
						
						String[] parts = response.split(":");
						if(parts[0].equalsIgnoreCase("error")){
							htmlContent=parts[2];
							return "html";
						}
					}catch (Exception e) {
						System.out.println(e);
					}
					String[] parts = response.split("#");
					if(parts[0].equalsIgnoreCase("False")){
						htmlContent=parts[2];
						return "html";
					}
								
					uploadFile = new File(baseSrcPath + "/" + workSpaceId + "/"+ nodeId + "/" + uuid+ "/" + Name +".pdf");
					//out = new FileOutputStream(new File(baseSrcPath + "/" + workSpaceId + "/"+ nodeId + "/" + uuid+ "/" + Name +".pdf"));
					FileManager.deleteFile(uploadedFile);
					File srcFile=new File(nodeRec[9]+previousHistory.get(0).getFolderName()+"/"+Name +".pdf" );
					Files.copy(uploadFile.toPath(), srcFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
					File f=new File(uploadFile.getParent());
					FileUtils.deleteDirectory(f);
					
					DTOUserMst dtouser = docMgmtImpl.getUserByCode(usercode);
					dtoWsHistory =docMgmtImpl.getUserSignatureDetail(usercode);
					baseWorkFolder = propertyInfo.getValue("signatureFile");
					if(dtoWsHistory.size()>0){
						imgFilename = dtoWsHistory.get(0).getFileName();
						uName = dtoWsHistory.get(0).getUserName();
						//role = dtoWsHistory.get(0).getRoleName();
						role = dtouser.getRoleName();
						//date = dtoWsHistory.get(0).getModifyOn();
						Timestamp currentTime = new Timestamp(System.currentTimeMillis());
						if(countryCode.equalsIgnoreCase("IND")){
							time = docMgmtImpl.TimeZoneConversion(dtoWsHistory.get(0).getModifyOn(),locationName,countryCode);
							dateForPreview = time.get(0);
						}
						else{
							time = docMgmtImpl.TimeZoneConversion(dtoWsHistory.get(0).getModifyOn(),locationName,countryCode);
							dateForPreview = time.get(1);
						}
						//signId = dtoWsHistory.get(0).getUuId();
						signId = dtoWsHistory.get(0).getFolderName().split("/")[2];
					}
					
					
					String fname="";
					if(dto.size()>0){
						fname = baseworkFolder+dto.get(0).getBaseWorkFolder()+"/"+Name +".pdf";
						File file = new File(fname);
						byte[] bytes = null;
						try {
							bytes = Files.readAllBytes(file.toPath());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					      b64 = Base64.getEncoder().encodeToString(bytes);
					}
					srcFileExt = "pdf";
			}
				stageId=previousHistory.get(0).getStageId();
				srFlag=previousHistory.get(0).getFileType();
				if((signImg==null || signImg.isEmpty()) && getSignDetail.size()>0){
					signStyle =getSignDetail.get(0).getFileType();
				}
				}
			}
			else{
			//dto =docMgmtImpl.getAllUserSignatureDetail(usercode);
			int lastTranNo = docMgmtImpl.getMaxTranNo(workSpaceId, Integer.parseInt(nodeId));
			previousHistory = docMgmtImpl.getWorkspaceNodeHistory(workSpaceId, Integer.parseInt(nodeId),lastTranNo);
			DTOUserMst dtouser = docMgmtImpl.getUserByCode(usercode);
			dtoWsHistory =docMgmtImpl.getUserSignatureDetail(usercode);
			baseWorkFolder = propertyInfo.getValue("signatureFile");
			if(dtoWsHistory.size()>0){
				imgFilename = dtoWsHistory.get(0).getFileName();
				uName = dtoWsHistory.get(0).getUserName();
				//role = dtoWsHistory.get(0).getRoleName();
				role = dtouser.getRoleName();
				//date = dtoWsHistory.get(0).getModifyOn();
				Timestamp currentTime = new Timestamp(System.currentTimeMillis());
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dtoWsHistory.get(0).getModifyOn(),locationName,countryCode);
					dateForPreview = time.get(0);
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(dtoWsHistory.get(0).getModifyOn(),locationName,countryCode);
					dateForPreview = time.get(1);
				}
				//signId = dtoWsHistory.get(0).getUuId();
				signId = dtoWsHistory.get(0).getFolderName().split("/")[2];
				if((signImg==null || signImg.isEmpty()) && getSignDetail.size()>0){
					signStyle =getSignDetail.get(0).getFileType();
				}
			}
			
			
			String fname="";
			if(dto.size()>0){
				fname = baseworkFolder+dto.get(0).getBaseWorkFolder()+"/"+fileName;
				File file = new File(fname);
				byte[] bytes = null;
				try {
					bytes = Files.readAllBytes(file.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			      b64 = Base64.getEncoder().encodeToString(bytes);
			}
			srcFileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
			stageId=previousHistory.get(0).getStageId();
			srFlag=previousHistory.get(0).getFileType();
		}
			DTOWorkSpaceMst dtowmst = docMgmtImpl.getWorkSpaceDetailWSList(workSpaceId);
			if(dtowmst.getStatusIndi()=='L'){
				lockSeqFlag=true;
			}
			int lastTranNo = docMgmtImpl.getMaxTranNo(workSpaceId,Integer.parseInt(nodeId));
			ArrayList<DTOWorkSpaceNodeHistory> previousHistory = docMgmtImpl.getWorkspaceNodeHistory(workSpaceId, Integer.parseInt(nodeId),lastTranNo);
			if(previousHistory.size()>0)
				tranNo=String.valueOf(previousHistory.get(0).getTranNo());

			if(selectedStage!=""){
			Vector<DTOWorkSpaceNodeHistory> getActionPerformed = docMgmtImpl.getProjectSignOffHistory(workSpaceId,  Integer.parseInt(nodeId));
			for(int i=0;i<getActionPerformed.size();i++){
				if(usercode==getActionPerformed.get(i).getUserCode()){
					selectedStage=getActionPerformed.get(i).getStageDesc();
					}
				}
			}
			
			return SUCCESS;
		}
		
		public String showPdf() {
			//File baseLogoDir = propertyInfo.getDir("signatureFile");
			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			
			Vector<DTOWorkSpaceNodeHistory> dto = docMgmtImpl.getMaxNodeHistoryByTranNo(workSpaceId, Integer.parseInt(nodeId));
			String baseworkFolder=propertyInfo.getValue("BaseWorkFolder");
			
			//fileName=baseworkFolder+dto.get(0).getBaseWorkFolder()+"/"+dto.get(0).getFileName();
			//fileName="TRADERS_page-0001.jpg";
			//fileName="KNET _ Aarti _ eCTD-Application_Database-FR-22-IQP-01-032022-Production_page-0004.jpg";
			File logoFile;
			String fname="";
			if(dto.size()>0){
				//fname = baseworkFolder + dto.get(0).getFolderName();
				//fname = baseworkFolder+dto.get(0).getBaseWorkFolder()+"/"+dto.get(0).getFileName();
				fname = baseworkFolder+dto.get(0).getBaseWorkFolder();
				File file = new File(fname);
				logoFile = new File(file,fileName);
				byte[] bytes = null;
				try {
					bytes = Files.readAllBytes(file.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			      String b64 = Base64.getEncoder().encodeToString(bytes);
			      System.out.println(b64); //-> "JVBERi..."
				
				
				
			}
			else{
				logoFile = new File(baseworkFolder,fileName);
			}
			try {
				logoStream = new FileInputStream(logoFile);
				contentType = "application/pdf";
				//contentType = "text/plain";
				//contentType = "image/jpg";	
				contentDisposition ="filename=\""+fileName+"\"";
			} catch (Exception e) {
				logoStream = null;
				e.printStackTrace();
			}
			return SUCCESS;
		}
		
		public String CropPdfSign() throws DocumentException, IOException{cropSignPdfPath = propertyInfo.getValue("CropSignPdfPath");
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		xCordinates=xCordinates+60;
		//int tranNo = docMgmtImpl.getMaxTranNoByStageId(workSpaceId,Integer.parseInt(nodeId));
		 Vector<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl.getMaxNodeHistoryByTranNo(workSpaceId, Integer.parseInt(nodeId));

		 DTOWorkSpaceNodeHistory workSpaceNodeHistoryDTO = new DTOWorkSpaceNodeHistory();
			workSpaceNodeHistoryDTO.setWorkSpaceId(tempHistory.get(0).getWorkSpaceId());
			workSpaceNodeHistoryDTO.setNodeId(Integer.parseInt(nodeId));
			workSpaceNodeHistoryDTO.setTranNo(tempHistory.get(0).getTranNo());
			if (tempHistory.get(0).getFileName() == null) {
				workSpaceNodeHistoryDTO.setFileName("No File");
			} else {
				workSpaceNodeHistoryDTO.setFileName(tempHistory.get(0).getFileName());
			}

			workSpaceNodeHistoryDTO.setFileType("");
			workSpaceNodeHistoryDTO.setFolderName(tempHistory.get(0).getBaseWorkFolder());
			workSpaceNodeHistoryDTO.setRequiredFlag('Y');
			workSpaceNodeHistoryDTO.setStageId(tempHistory.get(0).getStageId());
			if (tempHistory.get(0).getRemark() == null)
				workSpaceNodeHistoryDTO.setRemark("");
			else
				workSpaceNodeHistoryDTO.setRemark(tempHistory.get(0).getRemark());
			workSpaceNodeHistoryDTO.setModifyBy(usercode);
			workSpaceNodeHistoryDTO.setStatusIndi('N');
			workSpaceNodeHistoryDTO.setDefaultFileFormat("");
			String roleCode = docMgmtImpl.getRoleCode(workSpaceId, usercode);
			workSpaceNodeHistoryDTO.setRoleCode(roleCode);
			workSpaceNodeHistoryDTO.setFileSize(tempHistory.get(0).getFileSize());
			workSpaceNodeHistoryDTO.setCoOrdinates(pageNo+":"+xCordinates+":"+yCordinates);
			docMgmtImpl.insertNodeHistoryWithCoordinates(workSpaceNodeHistoryDTO);

		/* 
		 
		String fileName = tempHistory.get(0).getFileName();

		String tranFolder = tempHistory.get(0).getBaseWorkFolder() + tempHistory.get(0).getFolderName();

		File uploadedFile = new File(tranFolder, fileName);

		File wsIdpath = new File(cropSignPdfPath + "/" + workSpaceId );
		File ndIdPath;
		File tranfldrPath;

		if(!wsIdpath.isDirectory()){
		wsIdpath.mkdir();

		ndIdPath = new File(wsIdpath+"/"+nodeId);
		if(!ndIdPath.isDirectory()){
		ndIdPath.mkdir();

		tranfldrPath = new File(ndIdPath+"/"+tranNo);
		if(!tranfldrPath.isDirectory()){
		tranfldrPath.mkdir();
		}
		}
		else{
		tranfldrPath = new File(ndIdPath+"/"+tranNo);
		if(!tranfldrPath.isDirectory()){
		tranfldrPath.mkdir();
		}
		}

		}else{
		ndIdPath = new File(wsIdpath+"/"+nodeId);
		if(!ndIdPath.isDirectory()){
		ndIdPath.mkdir();

		tranfldrPath = new File(ndIdPath+"/"+tranNo);
		if(!tranfldrPath.isDirectory()){
		tranfldrPath.mkdir();
		}
		}
		else{
		tranfldrPath = new File(ndIdPath+"/"+tranNo);
		if(!tranfldrPath.isDirectory()){
		tranfldrPath.mkdir();
		}
		}
		}
		File inputFilePath = null;
		File outputFilePath;
		String outFile = null;
		try{
		outFile = wsIdpath+"/"+nodeId+"/"+tranNo+"/"+fileName;

		inputFilePath = new File(uploadedFile.getAbsolutePath()); // Existing file
		outputFilePath = new File(outFile); // New file



		OutputStream fos = new FileOutputStream(outputFilePath);



		PdfReader pdfReader = new PdfReader(inputFilePath.getAbsolutePath());



		PdfStamper pdfStamper = new PdfStamper(pdfReader, fos);
		String signatureFilePath=propertyInfo.getValue("signatureFile");
		
		String style = null;
		String imageFile = null;
		Image image = null;
		usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		docMgmtImpl.getUserSignatureDetail(usercode);
		Vector<DTOWorkSpaceNodeHistory> tempHistory1 = docMgmtImpl.getUserSignatureDetail(usercode);
		
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
			image.setAbsolutePosition(xCordinates, yCordinates-20);
			}
		}
		
		PdfContentByte pdfContentByte = pdfStamper.getOverContent(pageNo);
		
		DTOUserMst dtouser = docMgmtImpl.getUserByCode(usercode);
		dtoWsHistory =docMgmtImpl.getUserSignatureDetail(usercode);
		baseWorkFolder = propertyInfo.getValue("signatureFile");
		if(dtoWsHistory.size()>0){
			imgFilename = dtoWsHistory.get(0).getFileName();
			uName = dtoWsHistory.get(0).getUserName();
			role = dtouser.getRoleName();
			date = dtoWsHistory.get(0).getModifyOn();
			signId = dtoWsHistory.get(0).getUuId();
		}
		  float[] widthsForTable = { 2.5f, 3 };
			PdfPTable pdfPTable = new PdfPTable(widthsForTable);
	       pdfPTable.setTotalWidth(106);
	       //Create cells
	       PdfPCell pdfPCell1 = new PdfPCell();
	       PdfPCell pdfPCell2 = new PdfPCell();
	       //Add cells to table
	       //image = Image.getInstance(imageFile);
	       
	       if(image!= null){   
	    	   image.scaleToFit(45f, 80f); //Scale image's width and height
		       pdfPCell1.addElement(image);
		       pdfPCell1.setFixedHeight(28f);
		       pdfPCell1.setPaddingTop(5f);
		       pdfPCell1.setPaddingLeft(1f);
		       pdfPCell1.setPaddingRight(1f);
		       pdfPCell1.setBorder(Rectangle.NO_BORDER);
		       pdfPCell1.setBackgroundColor(new BaseColor(255,255,45));
		       pdfPCell1.setBorder(Rectangle.NO_BORDER);
		       pdfPCell1.setBackgroundColor(new BaseColor(255,255,255));
		       pdfPTable.addCell(pdfPCell1);
		    }else{
		    	style=tempHistory1.get(0).getFileType();
		    	Font font1 = ft.registerExternalFont(style);
		    	if(!style.isEmpty())
		    	font1 = FontFactory.getFont(style, 7f,Font.BOLD, BaseColor.BLACK);
		    	else
		    	font1= FontFactory.getFont("Calibri", 3f, Font.BOLD, BaseColor.BLACK);
		    	
		    	Paragraph p = new Paragraph(tempHistory1.get(0).getUserName(),font1);
		    	pdfPCell1.addElement(p);
		    	pdfPCell1.setFixedHeight(28f);
			    //pdfPCell1.setPaddingTop(5f);
			    pdfPCell1.setPaddingLeft(1f);
			    pdfPCell1.setPaddingRight(1f);
			    pdfPCell1.setBorder(Rectangle.NO_BORDER);
			    pdfPCell1.setBackgroundColor(new BaseColor(255,255,45));
			    pdfPCell1.setBorder(Rectangle.NO_BORDER);
			    pdfPCell1.setBackgroundColor(new BaseColor(255,255,255));
		    	pdfPTable.addCell(pdfPCell1);
		    }
	      
	      Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 3, BaseColor.BLACK);
	      Paragraph p=new Paragraph("Name : "+uName,font2);
	      Paragraph paragraph = new Paragraph("Date :"+date,font2);
	      pdfPCell2.setVerticalAlignment(Element.ALIGN_TOP);
	      pdfPCell2.addElement(p);
	      Paragraph roll = new Paragraph("Role : "+role,font2);
	      pdfPCell2.addElement(roll);
	      pdfPCell2.addElement(paragraph);
	      pdfPCell2.setFixedHeight(28f);
	      pdfPCell2.setBorder(Rectangle.NO_BORDER);
	      pdfPCell2.setBackgroundColor(new BaseColor(255,255,255));
	  
	      Paragraph SignId = new Paragraph("Sign Id:"+signId,font2);
	      pdfPCell2.addElement(SignId);
	      pdfPTable.addCell(pdfPCell2);
	      pdfPTable.writeSelectedRows(0, -1, xCordinates, yCordinates+15, pdfContentByte);
	      
	      pdfStamper.close();
	      pdfReader.close();

		
		}
		catch (Exception e) {
		System.out.println(e.toString());
		htmlContent=e.toString();
		return SUCCESS;
		}

		System.out.println("Uploaded File Path: >> "+ inputFilePath);

		System.out.println("After Add Watermark File Path: >> "+ outFile);*/
		htmlContent="true";

		return SUCCESS;
	}
		public String sendForReviewToApprove(){

			DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
			String OpenFileAndSignOff = propertyInfo.getValue("OpenFileAndSignOff");
			String autoMail = propertyInfo.getValue("AutoMail");
			
			int stgId = docMgmtImpl.getStageIdfromWSHistory(workSpaceId,Integer.parseInt(nodeId));
			System.out.println("stageId="+stgId);
			String currentSeq = docMgmtImpl.getCurrentSeq(workSpaceId, Integer.parseInt(nodeId));
			System.out.println("currentSeq="+currentSeq);
				
			
			if(stageId==-1 || (stgId==100 && !currentSeq.isEmpty())){
				return SUCCESS;
			}
			
			int MaxtranNo = docMgmtImpl.getMaxTranNo(workSpaceId, Integer.parseInt(nodeId));
			int tranNo = docMgmtImpl.getNewTranNo(workSpaceId);
			DTOWorkSpaceNodeHistory dtownd = docMgmtImpl.getWorkspaceNodeHistorybyTranNo(workSpaceId, Integer.parseInt(nodeId), MaxtranNo);
			dtownd.setStageId(stageId);
			dtownd.setModifyBy(usercode);
			dtownd.setTranNo(tranNo);
		if(!remark.isEmpty() && !remark.equals("")){
			dtownd.setRemark(remark);
		}
		else{
			dtownd.setRemark("");
		}
			dtownd.setDefaultFileFormat("");
			String roleCode = docMgmtImpl.getRoleCode(workSpaceId, usercode);
			dtownd.setRoleCode(roleCode);
			dtownd.setFileSize(dtownd.getFileSize());
			//docMgmtImpl.insertNodeHistory(dtownd);
			
			docMgmtImpl.insertNodeHistoryWithRoleCode(dtownd);
			
			if(stageId==0){
				DTOWorkSpaceNodeHistory wsndForOffice =new DTOWorkSpaceNodeHistory();
				wsndForOffice.setWorkSpaceId(workSpaceId);
				wsndForOffice.setNodeId(Integer.parseInt(nodeId));
				//wsndForOffice.setDocTranNo(nodeDocHistory.get(0).getDocTranNo());
				int mode=2;
				docMgmtImpl.insertworkspacenodehistoryToUpdate(wsndForOffice,mode);
			}
			
			if(stageId!=0){
			if(eSign.equalsIgnoreCase("Y")){
				Vector <DTOWorkSpaceNodeHistory> dto;
				dto =docMgmtImpl.getUserSignatureDetail(usercode);
				if(dto.size()>0){
					String uuId = dto.get(0).getUuId();
					int signtrNo = dto.get(0).getSignTranNo();
					docMgmtImpl.UpdateNodeHistoryForESign(workSpaceId,Integer.parseInt(nodeId),uuId,signtrNo);
					 roleCode = docMgmtImpl.getRoleCode(workSpaceId, usercode);
					docMgmtImpl.UpdateNodeHistoryForRoleCode(workSpaceId,Integer.parseInt(nodeId),roleCode);
				}			
			}
			}
			if(OpenFileAndSignOff.equalsIgnoreCase("Yes")){
			DTOWorkSpaceNodeHistory dto1 = new DTOWorkSpaceNodeHistory();
			dto1 = docMgmtImpl.getNodeHistoryForSignOff(workSpaceId,Integer.parseInt(nodeId));
			dto1.setWorkSpaceId(workSpaceId);
			dto1.setNodeId(Integer.parseInt(nodeId));
			dto1.setTranNo(0);
			dto1.setFileName(dto1.getFileName());
			dto1.setFolderName(dto1.getFolderName());
			dto1.setRemark("");
			dto1.setModifyBy(usercode);
			dto1.setStatusIndi('D');
			dto1.setMode(2);
			docMgmtImpl.insertIntofileopenforsign(dto1);
			}
			//(String wsId,int nodeId,int tranNo,int iStageId,int userCode,int Mode)
			String flag="";
			if(stageId==0){
				docMgmtImpl.insertAssignedNodeRight(workSpaceId,Integer.parseInt(nodeId),tranNo,stageId,usercode,2,flag);
			}
			else
			{
				docMgmtImpl.insertAssignedNodeRight(workSpaceId,Integer.parseInt(nodeId),tranNo,stageId,usercode,1,flag);
				
				Timestamp signOffDate=docMgmtImpl.getAssignNodeRightsData(workSpaceId,Integer.parseInt(nodeId),stageId).get(0).getModifyOn();
				String attrValues=docMgmtImpl.getAttributeDetailByAttrName(workSpaceId,1,"Project Start Date").getAttrValue();
				
				if(attrValues != null && !attrValues.equals("")){				
						int parentNodeIdforAdjustDate=docMgmtImpl.getParentNodeId(workSpaceId, Integer.parseInt(nodeId));
						getAdjustDateData=docMgmtImpl.getProjectTimelineAdjustHoursUpdate(workSpaceId,Integer.parseInt(nodeId),parentNodeIdforAdjustDate,usercode,stageId);
						
						//String s[]=attrVal.split("/");
						
						//LocalDateTime date=LocalDateTime.of(LocalDate.of(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2])), LocalTime.of(0,0));
						LocalDateTime date=signOffDate.toLocalDateTime();
						LocalDateTime startDate=null;
						LocalDateTime endDate = null;
						for(int k=0;k<getAdjustDateData.size();k++){
							//System.out.println("Hours : "+hoursData.get(k).getHours());
							DTOWorkSpaceUserRightsMst dtotimeline = (DTOWorkSpaceUserRightsMst) getAdjustDateData.get(k);
							int noofhours=dtotimeline.getHours();
							if(startDate==null){
								startDate=date;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}
							else{
								startDate=endDate;
								endDate=startDate.plusHours(noofhours*3);
								DayOfWeek dayOfWeek = endDate.getDayOfWeek();
								//System.out.println(dayOfWeek);
								if(dayOfWeek==DayOfWeek.SUNDAY){
									endDate=endDate.plusHours(24);
								}
							}	
				            dtotimeline.setAdjustDate(Timestamp.valueOf(endDate));
							docMgmtImpl.updateTimelineAdjustDate(dtotimeline);
						}	
					}
			}
			// update tran no in attribute history
			docMgmtImpl.UpdateTranNoForStageInAttrHistory(workSpaceId, Integer.parseInt(nodeId),tranNo);
			// /docMgmtImpl.updateStageStatus(dtownd);
			
			if(autoMail.equalsIgnoreCase("Yes") && stageId==0){
				 StageWiseMailReport stageWiseMail = new StageWiseMailReport();
				 //stageWiseMail.StageWiseMail(workspaceID,nodeId,"Send back",10);
				 stageWiseMail.StageWiseMailNewFormate(workSpaceId,Integer.parseInt(nodeId),"Send back",10); 
				 System.out.println("Mail Sent....");
		      }
			
			if(autoMail.equalsIgnoreCase("Yes") && stageId==20){
				
				getCompletedNodeStageDetail = docMgmtImpl.getCompletedNodeStageDetail(workSpaceId,Integer.parseInt(nodeId),stageId);
				
				if(getCompletedNodeStageDetail==true){
					 StageWiseMailReport stageWiseMail = new StageWiseMailReport();
					 //stageWiseMail.StageWiseMail(workspaceID,nodeId,"Reviewed",100);
					 stageWiseMail.StageWiseMailNewFormate(workSpaceId,Integer.parseInt(nodeId),"Reviewed",100);
					 System.out.println("Mail Sent....");
				}
				
			}
			//int tranNo = docMgmtImpl.getMaxTranNoByStageId(workSpaceId,Integer.parseInt(nodeId));
			 Vector<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl.getMaxNodeHistoryByTranNo(workSpaceId, Integer.parseInt(nodeId));

			 DTOWorkSpaceNodeHistory workSpaceNodeHistoryDTO = new DTOWorkSpaceNodeHistory();
				workSpaceNodeHistoryDTO.setWorkSpaceId(tempHistory.get(0).getWorkSpaceId());
				workSpaceNodeHistoryDTO.setNodeId(Integer.parseInt(nodeId));
				workSpaceNodeHistoryDTO.setTranNo(tempHistory.get(0).getTranNo());
				if (tempHistory.get(0).getFileName() == null) {
					workSpaceNodeHistoryDTO.setFileName("No File");
				} else {
					workSpaceNodeHistoryDTO.setFileName(tempHistory.get(0).getFileName());
				}

				workSpaceNodeHistoryDTO.setFileType("");
				workSpaceNodeHistoryDTO.setFolderName(tempHistory.get(0).getBaseWorkFolder());
				workSpaceNodeHistoryDTO.setRequiredFlag('Y');
				workSpaceNodeHistoryDTO.setStageId(tempHistory.get(0).getStageId());
				if (tempHistory.get(0).getRemark() == null)
					workSpaceNodeHistoryDTO.setRemark("");
				else
					workSpaceNodeHistoryDTO.setRemark(tempHistory.get(0).getRemark());
				workSpaceNodeHistoryDTO.setModifyBy(usercode);
				workSpaceNodeHistoryDTO.setStatusIndi('N');
				workSpaceNodeHistoryDTO.setDefaultFileFormat("");
				String roleCode1 = docMgmtImpl.getRoleCode(workSpaceId, usercode);
				workSpaceNodeHistoryDTO.setRoleCode(roleCode1);
				workSpaceNodeHistoryDTO.setFileSize(tempHistory.get(0).getFileSize());
				workSpaceNodeHistoryDTO.setCoOrdinates(pageNo+":"+xCordinates+":"+yCordinates);
				docMgmtImpl.insertNodeHistoryWithCoordinates(workSpaceNodeHistoryDTO);
			htmlContent="true";

			return SUCCESS;
		}
		
		public String getWorkSpaceId() {
			return workSpaceId;
		}

		public void setWorkSpaceId(String workSpaceId) {
			this.workSpaceId = workSpaceId;
		}

		public String getNodeId() {
			return nodeId;
		}

		public void setNodeId(String nodeId) {
			this.nodeId = nodeId;
		}

		public String getTranNo() {
			return tranNo;
		}

		public void setTranNo(String tranNo) {
			this.tranNo = tranNo;
		}

		public String getBaseWorkFolder() {
			return baseWorkFolder;
		}

		public void setBaseWorkFolder(String baseWorkFolder) {
			this.baseWorkFolder = baseWorkFolder;
		}

		public String getFileExt() {
			return fileExt;
		}

		public void setFileExt(String fileExt) {
			this.fileExt = fileExt;
		}

		public String getFileWithPath() {
			return fileWithPath;
		}

		public void setFileWithPath(String fileWithPath) {
			this.fileWithPath = fileWithPath;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public InputStream getInputStream() {
			return inputStream;
		}

		public void setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		public String getContentType() {
			return contentType;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
		}

		public String getAttachment() {
			return attachment;
		}

		public void setAttachment(String attachment) {
			this.attachment = attachment;
		}

		public String getContentDisposition() {
			return contentDisposition;
		}

		public void setContentDisposition(String contentDisposition) {
			this.contentDisposition = contentDisposition;
		}
		public InputStream getLogoStream() {
			return logoStream;
		}

		public void setLogoStream(InputStream logoStream) {
			this.logoStream = logoStream;
		}
}
