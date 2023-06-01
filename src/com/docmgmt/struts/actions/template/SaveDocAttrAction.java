package com.docmgmt.struts.actions.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.services.pdf.PdfPropUtilities;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

	public class SaveDocAttrAction extends ActionSupport {
		private static final long serialVersionUID = 1L;

		private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		public String templateId;
		public int usergrpcode;
		public int usercode;
		public String userType;
		public String UserName;

		public int nodeId = 1;
		public File uploadFile;
		public String uploadFileContentType; // The content type of the file
		public String uploadFileFileName; // The uploaded file name

		public String keyword;

		public String description;

		public int[] userIdDtl;

		public String versionPrefix="A";

		public String VersionSeperator="-";

		public String versionSuffix="1";

		public int attrCount;

		public char isReplaceFileName;
		
		public char isReplaceFolderName;

		public String nodeFolderName;

		public char deleteFile;
		public char useSourceFile;

		public String remark="";
		public String Drag;
		public String operation;
		public boolean dndflag;

		public String allowAutoCorrectionPdfProp;
		public char autoCorrectPdfProp;
		String nodeOperationValue = "";
		public String passwordProtected;
		public String fileSizeProperty;
		public int fileUploloadingSize;
		public String UplodFileMail;
		String baseWorkFolder="";

		@Override
		public String execute() {

			usergrpcode = Integer.parseInt(ActionContext.getContext().getSession()
					.get("usergroupcode").toString());
			usercode = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userid").toString());
			userType = ActionContext.getContext().getSession().get("usertypecode")
					.toString();
			UserName = ActionContext.getContext().getSession().get("username")
					.toString();
			PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
			allowAutoCorrectionPdfProp = propertyInfo
					.getValue("PdfPropAutoCorrection");
			UplodFileMail = propertyInfo.getValue("UplodFileMail");
			
			passwordProtected = propertyInfo.getValue("PasswordProtected");
			fileSizeProperty = propertyInfo.getValue("FileUploadingSize");
			fileUploloadingSize = Integer.parseInt(fileSizeProperty);
			baseWorkFolder = propertyInfo.getValue("TemplateWorkFolder");
			
			isReplaceFolderName='N';
			
			if(remark.isEmpty() || remark.equals("")){
				remark="New";
			}
			
			String fileName = "";
			// System.out.println(workspaceID +""+usergrpcode+" " +usercode + ":"+UserName);
			
			String tempBaseFolder = baseWorkFolder;

			int TranNo = docMgmtImpl.getNewTranNoForTemplate(templateId);
			File dirPath = new File(tempBaseFolder + "/" + templateId + "/"+ nodeId + "/" + TranNo);
			//checking server is accessible or not
			if (!dirPath.exists() && !dirPath.mkdirs()) {
				addActionMessage("pdfUpload");
				return INPUT;
		    }
			/**
			 * If user wants to use the source file then first convert it to PDF
			 * first and use it
			 * */
			/*if (useSourceFile == 'Y') {
				ArrayList<DTOWorkspaceNodeDocHistory> docHistory = docMgmtImpl
						.getLatestNodeDocHistory(workspaceID, nodeId);
				String baseSrcPath = propertyInfo.getValue("SOURCE_DOC_FOLDER");

				File srcDoc = new File(baseSrcPath + docHistory.get(0).getDocPath()
						+ "/" + docHistory.get(0).getDocName());
				uploadFile = FileManager.convertDocument(srcDoc, "pdf");
				uploadFileFileName = uploadFile.getName();
			}*/
			uploadFileFileName = uploadFileFileName.replace(' ', '_');
			// isReplaceFileName='Y';

			// if isReplaceFileName check box is checked
			nodeFolderName = uploadFileFileName;
			if (isReplaceFileName == 'Y' && uploadFileFileName != null) {
				int dotIndex = nodeFolderName.lastIndexOf(".");
				String fileNameExt = uploadFileFileName
						.substring(uploadFileFileName.lastIndexOf(".") + 1);
				fileNameExt = fileNameExt.toLowerCase();
				String name = "";

				// System.out.println("====>"+nodeFolderName.substring(0,dotIndex));

				if (dotIndex != -1) {
					name = nodeFolderName.substring(0, dotIndex);
				} else {
					name = nodeFolderName;
				}

				// System.out.println("Name="+name);
				fileName = name + "." + fileNameExt;
				// System.out.println("fileNameExt:::"+fileNameExt);
				// System.out.println("fileName::"+fileName);

			} else {// if isReplaceFileName check box is unchecked
				fileName = uploadFileFileName;
			}
			

			byte[] fileData = null;
			int fileSize = 0;
			if (uploadFile != null) {
				fileSize = new Long(uploadFile.length()).intValue();
			
			//int fileSize1 = (fileUploloadingSize/10)+1;
			/*if((fileSize/fileUploloadingSize) >fileSize1) {
				addActionMessage("FileUploadingSize"); 
				return INPUT;
				}*/
			
			long fileSizeInBytes = uploadFile.length();
		
			long fileSizeInKB = fileSizeInBytes / 1024;
		
			long fileSizeInMB = fileSizeInKB / 1024;

			if (fileSizeInMB > fileUploloadingSize) {
				addActionMessage("FileUploadingSize"); 
				return INPUT;
			}
			} 
			
			/*InputStream sourceInputStream = null;
			try {
				if (uploadFile != null) {
					fileData = getBytesFromFile(uploadFile);
					sourceInputStream = new FileInputStream(uploadFile);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			
			InputStream sourceInputStream = null;
			
			boolean flagtemp=false;
			try {
				if (uploadFile != null) {
					
						fileData = getBytesFromFile(uploadFile);
						sourceInputStream = new FileInputStream(uploadFile);
						if(passwordProtected.equalsIgnoreCase("yes")  && (fileName.contains(".pdf") || fileName.contains(".PDF")))
						{
							//PDDocument document = PDDocument.load(uploadFile);
							PDDocument document = PDDocument.load(uploadFile);
		
						    if(document.isEncrypted())
						    {
						    	flagtemp=true;
						    }
						    document.close();
						}
					
				}
				 if(flagtemp==true && passwordProtected.equalsIgnoreCase("yes")){
					  flagtemp = false;
					  addActionMessage("EncryptedFile");
				    	return INPUT;
				 }  
				  
			} catch (IOException e) {
				e.printStackTrace();
			}

			/**
			 * If Source file is used at the node then deleting the converted PDF
			 * file.
			 * 
			 * */
			
			if (useSourceFile == 'Y') {
				FileManager.deleteFile(uploadFile);
			}
			//DTOWorkSpaceNodeDetail wsnd = docMgmtImpl.getNodeDetail(templateId,nodeId).get(0);
			DTOWorkSpaceNodeDetail wsnd = docMgmtImpl.getTemplateNodeDetail(templateId,nodeId).get(0);
			if (isReplaceFolderName == 'Y' && uploadFileFileName != null) {
				
				if(nodeFolderName.contains(".")){
					fileName = uploadFileFileName;
				}else{
					fileName = uploadFileFileName.substring(0, uploadFileFileName.indexOf("."));
				}
					wsnd.setFolderName(fileName);
					docMgmtImpl.insertWorkspaceNodeDetail(wsnd, 2);
			}
			else{
				if(nodeFolderName.contains(".")){
					fileName = uploadFileFileName;
				}else{
					fileName = uploadFileFileName.substring(0, uploadFileFileName.indexOf("."));
				}
			}
			//Added by Harsh Shah for changing the extension only	
				if (fileSize > 0) { // if new file is uploaded

					uploadFileOnNode(templateId, nodeId, baseWorkFolder, fileData,
							fileName, usercode, keyword, description,
							tempBaseFolder, TranNo, fileSize, sourceInputStream,remark);

				} else { // else uploading the previous file with new tranNo

					int lastTranNo = docMgmtImpl.getMaxTranNoForTemplate(templateId, nodeId);
					DTOWorkSpaceNodeHistory previousHistory = docMgmtImpl
							.getWorkspaceNodeHistorybyTranNo(templateId, nodeId,
									lastTranNo);				

						File file = new File(baseWorkFolder
								+ previousHistory.getFolderName() + "/"
								+ previousHistory.getFileName());

						try {
							sourceInputStream = new FileInputStream(file);
							fileSize = new Long(file.length()).intValue();
							fileName = file.getName();
							fileData = getBytesFromFile(file);
							uploadFileOnNode(templateId, nodeId, baseWorkFolder,
									fileData, fileName, usercode, keyword,
									description, tempBaseFolder,
									TranNo, fileSize, sourceInputStream, remark);

						} catch (IOException e) {
							e.printStackTrace();
						}
				

				}
			nodeId = nodeId;
			return SUCCESS;
		}

		private int parseInt(String fileSize2) {
			// TODO Auto-generated method stub
			return 0;
		}

		// //////////////////////////////////

		public static byte[] getBytesFromFile(File file) throws IOException {
			InputStream is = new FileInputStream(file);

			// Get the size of the file
			long length = file.length();

			if (length > Integer.MAX_VALUE) {
				// File is too large
			}

			// Create the byte array to hold the data
			byte[] bytes = new byte[(int) length];

			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}

			// Close the input stream and return bytes
			is.close();
			return bytes;
		}

		public boolean uploadFileOnNode(String wsId, int nodeId,
				String baseWorkFolder, byte[] fileData, String fileName,
				int userCode, String keyword, String description,
				String tempBaseFolder, int tranNo,
				int fileSize, java.io.InputStream sourceInputStream,
				String remark) {
			// System.out.println("Node History ok");
			createFolderStruc(wsId, nodeId, tranNo, baseWorkFolder, fileData,
					fileName, fileSize, sourceInputStream);
			updateNodeHistory(wsId, nodeId, tranNo, fileName, userCode,10,remark);
			//createTempFolderStruc(wsId, nodeId, tranNo, tempBaseFolder, fileData,fileName, sourceInputStream);

			return true;
		}

		private void createFolderStruc(String wsId, int nodeId, int tranNo,
				String baseWorkFolder, byte[] fileData, String fileName,
				int fileSize, InputStream stream) {
			try {

				if (tranNo != -1) {
					// System.out.println("NODE ID="+nodeId);

					File tranFolder = new File(baseWorkFolder + "/" + wsId + "/"
							+ nodeId + "/" + tranNo);
					tranFolder.mkdirs();

					File uploadedFile = new File(tranFolder, fileName);
					// System.out.println("uploadfile path::"+uploadedFile);
					OutputStream bos = new FileOutputStream(uploadedFile);
					int temp = 0;
					while ((temp = stream.read(fileData, 0, fileSize)) != -1) {
						bos.write(fileData, 0, temp);
					}
					bos.close();
					stream.close();
					if (autoCorrectPdfProp == 'Y' && uploadedFile.exists()
							&& (uploadedFile.getAbsolutePath().endsWith(".pdf") || uploadedFile.getAbsolutePath().endsWith(".PDF"))
							&& allowAutoCorrectionPdfProp.equalsIgnoreCase("yes")
							&& !nodeOperationValue.equalsIgnoreCase("delete")) {
						// Auto Correcting Pdf Properties

						final String FileToCorrect = uploadedFile.getAbsolutePath();
						new Thread(new Runnable() {
							public void run() {

								PdfPropUtilities pdfUtil = new PdfPropUtilities();
								pdfUtil.autoCorrectPdfProp(FileToCorrect);
								
							}
						}).start();
						
					}

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		private void updateNodeHistory(String wsId, int nodeId, int tranNo,
				String fileName, int userCode, int stageId, String remark) {

			String folderPath = "/" + wsId + "/" + nodeId + "/" + tranNo;

			DTOWorkSpaceNodeHistory workSpaceNodeHistoryDTO = new DTOWorkSpaceNodeHistory();
			workSpaceNodeHistoryDTO.setWorkSpaceId(wsId);
			workSpaceNodeHistoryDTO.setNodeId(nodeId);
			if (fileName == null) {
				workSpaceNodeHistoryDTO.setFileName("No File");
			} else {
				workSpaceNodeHistoryDTO.setFileName(fileName);
			}
				
			workSpaceNodeHistoryDTO.setFileType("");
			workSpaceNodeHistoryDTO.setFolderName(folderPath);
			if (remark == null)
				workSpaceNodeHistoryDTO.setRemark("");
			else
				workSpaceNodeHistoryDTO.setRemark(remark);
			workSpaceNodeHistoryDTO.setModifyBy(userCode);
			workSpaceNodeHistoryDTO.setStatusIndi('N');
			String tempflder = baseWorkFolder + "/" + folderPath+"/"+fileName;
			workSpaceNodeHistoryDTO.setDefaultFileFormat(tempflder);

			docMgmtImpl.insertTemplateNodeHistory(workSpaceNodeHistoryDTO);

			workSpaceNodeHistoryDTO = null;
		}

		private void createTempFolderStruc(String wsId, int nodeId, int tranNo,
				String baseWorkFolder, byte[] fileData, String fileName,
				java.io.InputStream sourceInputStream) {
			try {
				int newTranNo = tranNo /* + 1 */;
				File wsFolder = new File(baseWorkFolder + "/" + wsId);
				wsFolder.mkdirs();

				File nodeFolder = new File(baseWorkFolder + "/" + wsId + "/"
						+ nodeId);
				nodeFolder.mkdirs();

				if (newTranNo != -1) {
					File tranFolder = new File(baseWorkFolder + "/" + wsId + "/"
							+ nodeId + "/" + newTranNo);
					tranFolder.mkdirs();
				}

				File uploadedFile = new File(baseWorkFolder + "/" + wsId + "/"
						+ nodeId + "/" + newTranNo + "/" + fileName);

				FileOutputStream uploadedFileOutStream = new FileOutputStream(
						uploadedFile);
				uploadedFileOutStream.write(fileData);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		// //////////////////////////////////

		public String getUploadFileContentType() {
			return uploadFileContentType;
		}

		public void setUploadFileContentType(String uploadFileContentType) {
			this.uploadFileContentType = uploadFileContentType;
		}

		public String getUploadFileFileName() {
			return uploadFileFileName;
		}

		public void setUploadFileFileName(String uploadFileFileName) {
			this.uploadFileFileName = uploadFileFileName;
		}

		public String getKeyword() {
			return keyword;
		}

		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int[] getUserIdDtl() {
			return userIdDtl;
		}

		public void setUserIdDtl(int[] userIdDtl) {
			this.userIdDtl = userIdDtl;
		}

		public int getNodeId() {
			return nodeId;
		}

		public void setNodeId(int nodeId) {
			this.nodeId = nodeId;
		}

		public File getUploadFile() {
			return uploadFile;
		}

		public void setUploadFile(File uploadFile) {
			this.uploadFile = uploadFile;
		}

		public int getAttrCount() {
			return attrCount;
		}

		public void setAttrCount(int attrCount) {
			this.attrCount = attrCount;
		}

		public String getVersionPrefix() {
			return versionPrefix;
		}

		public void setVersionPrefix(String versionPrefix) {
			this.versionPrefix = versionPrefix;
		}

		public String getVersionSeperator() {
			return VersionSeperator;
		}

		public void setVersionSeperator(String versionSeperator) {
			VersionSeperator = versionSeperator;
		}

		public String getVersionSuffix() {
			return versionSuffix;
		}

		public void setVersionSuffix(String versionSuffix) {
			this.versionSuffix = versionSuffix;
		}

		public char getIsReplaceFolderName() {
			return isReplaceFolderName;
		}

		public void setIsReplaceFolderName(char isReplaceFolderName) {
			this.isReplaceFolderName = isReplaceFolderName;
		}

		public char getIsReplaceFileName() {
			return isReplaceFileName;
		}

		public void setIsReplaceFileName(char isReplaceFileName) {
			this.isReplaceFileName = isReplaceFileName;
		}

		public String getNodeFolderName() {
			return nodeFolderName;
		}

		public void setNodeFolderName(String nodeFolderName) {
			this.nodeFolderName = nodeFolderName;
		}

		public char getDeleteFile() {
			return deleteFile;
		}

		public void setDeleteFile(char deleteFile) {
			this.deleteFile = deleteFile;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getDrag() {
			return Drag;
		}

		public void setDrag(String drag) {
			Drag = drag;
		}

		/**
		 * @param ar
		 *            [0] = workspaceId ar[1] = file path to upload
		 */
		public static void main(String ar[]) {
			DocMgmtImpl docmgtImpl = new DocMgmtImpl();
			// SaveNodeAttrAction s = new SaveNodeAttrAction();
			ArrayList<DTOWorkSpaceNodeDetail> dtoWsNodeDtlList = new ArrayList<DTOWorkSpaceNodeDetail>();
			// String wsID = s.workspaceID;
			String wsID = ar[0];
			dtoWsNodeDtlList = docmgtImpl.getAllChildNodeForFileuploading(wsID);
			PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
			// File baseImpDir = propertyInfo.getDir("ImportProjectPath");
			// System.out.println(dtoWsNodeDtlList.size());
			// File fileObj =new File("/demo.pdf");
			File fileObj = new File(ar[1]);
			FileManager fileManager = new FileManager();
			String workspaceFolder = propertyInfo.getValue("BaseWorkFolder");
			Vector<DTOWorkSpaceNodeAttrHistory> allwsnah = new Vector<DTOWorkSpaceNodeAttrHistory>();
			for (int i = 0; i < dtoWsNodeDtlList.size(); i++) {
				int newTranNo = docmgtImpl.getNewTranNo(wsID);
				Timestamp ts = new Timestamp(System.currentTimeMillis());
				int inodeid = dtoWsNodeDtlList.get(i).getNodeId();
				DTOWorkSpaceNodeHistory dtowsnh = new DTOWorkSpaceNodeHistory();
				dtowsnh.setWorkSpaceId(wsID);
				dtowsnh.setNodeId(inodeid);
				dtowsnh.setTranNo(newTranNo);
				dtowsnh.setFileName(fileObj.getName());
				dtowsnh.setFileType("");
				dtowsnh.setFolderName("/" + wsID + "/" + inodeid + "/" + newTranNo);
				dtowsnh.setRequiredFlag('Y');
				dtowsnh.setStageId(100);// Stage is by default approved...
				dtowsnh.setRemark("");
				dtowsnh.setModifyBy(1);
				dtowsnh.setStatusIndi('N');
				dtowsnh.setDefaultFileFormat("");
				docmgtImpl.insertNodeHistory(dtowsnh);

				fileManager.copyDirectory(fileObj, new File(workspaceFolder
						+ dtowsnh.getFolderName() + "/" + fileObj.getName()));
				Vector<DTOWorkSpaceNodeAttrDetail> dtoWsNodeAttrDtl = docmgtImpl
						.getNodeAttrDetail(wsID, inodeid);
				DTOWorkSpaceNodeAttrHistory dtowsnah = new DTOWorkSpaceNodeAttrHistory();
				dtowsnah.setWorkSpaceId(wsID);
				dtowsnah.setNodeId(inodeid);
				dtowsnah.setTranNo(newTranNo);
				dtowsnah.setAttrId(-999);
				dtowsnah.setAttrValue(ts.toString());
				dtowsnah.setModifyBy(1);

				for (int j = 0; j < dtoWsNodeAttrDtl.size(); j++) {
					dtowsnah = new DTOWorkSpaceNodeAttrHistory();
					dtowsnah.setWorkSpaceId(wsID);
					dtowsnah.setNodeId(inodeid);
					dtowsnah.setTranNo(newTranNo);
					dtowsnah.setAttrId(dtoWsNodeAttrDtl.get(j).getAttrId());
					dtowsnah.setAttrValue(dtoWsNodeAttrDtl.get(j).getAttrValue());
					dtowsnah.setModifyBy(1);
					allwsnah.add(dtowsnah);
				}

				DTOWorkSpaceNodeVersionHistory dtowsnvh = new DTOWorkSpaceNodeVersionHistory();
				dtowsnvh.setWorkspaceId(wsID);
				dtowsnvh.setNodeId(dtoWsNodeDtlList.get(i).getNodeId());
				dtowsnvh.setTranNo(newTranNo);
				dtowsnvh.setPublished('N');
				dtowsnvh.setDownloaded('N');
				dtowsnvh.setActivityId("");
				dtowsnvh.setModifyBy(1);
				dtowsnvh.setExecutedBy(1);
				dtowsnvh.setUserDefineVersionId("A-1");
				docmgtImpl.insertWorkspaceNodeVersionHistory(dtowsnvh);
			}
			docmgtImpl.InsertUpdateNodeAttrHistory(allwsnah);
		}
	}
