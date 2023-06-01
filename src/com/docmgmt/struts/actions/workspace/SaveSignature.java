package com.docmgmt.struts.actions.workspace;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

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

	public class SaveSignature extends ActionSupport {
		private static final long serialVersionUID = 1L;

		private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		public String templateId;
		public int usergrpcode;
		public int usercode;
		public String userType;
		public String UserName;

		public File uploadFile;
		public String uploadFileContentType; // The content type of the file
		public String uploadFileName; // The uploaded file name

		public char isReplaceFileName;
		
		public char isReplaceFolderName;

		public String nodeFolderName;
		public String remark="";

		public String allowAutoCorrectionPdfProp;
		public char autoCorrectPdfProp;
		String nodeOperationValue = "";
		public String passwordProtected;
		public String fileSizeProperty;
		public int fileUploloadingSize;
		String baseWorkFolder="";
		public String fontStyle;
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();	
		public String logoFileName;
		String LogoName;
		InputStream logoStream;
		String contentType;
		String contentDisposition;

		
		public String SaveFile(){
			
			usergrpcode = Integer.parseInt(ActionContext.getContext().getSession()
					.get("usergroupcode").toString());
			usercode = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userid").toString());
			userType = ActionContext.getContext().getSession().get("usertypecode")
					.toString();
			UserName = ActionContext.getContext().getSession().get("username")
					.toString();
			allowAutoCorrectionPdfProp = propertyInfo
					.getValue("PdfPropAutoCorrection");
			
			passwordProtected = propertyInfo.getValue("PasswordProtected");
			fileSizeProperty = propertyInfo.getValue("FileUploadingSize");
			fileUploloadingSize = Integer.parseInt(fileSizeProperty);
			baseWorkFolder = propertyInfo.getValue("signatureFile");
			
			isReplaceFolderName='N';
			
			if(remark.isEmpty() || remark.equals("")){
				remark="New";
			}
			
			String fileName = "";
			
			String tempBaseFolder = baseWorkFolder;

			int TranNo = docMgmtImpl.getNewTranNoForUserSignature(usercode);
			File dirPath = new File(tempBaseFolder + "/" + usercode + "/"+ TranNo);
			//checking server is accessible or not
			if (!dirPath.exists() && !dirPath.mkdirs()) {
				addActionMessage("pdfUpload");
				return INPUT;
		    }
			/**
			 * If user wants to use the source file then first convert it to PDF
			 * first and use it
			 * */
			nodeFolderName = uploadFileName;
			
			fileName = uploadFileName;
			

			byte[] fileData = null;
			int fileSize = 0;
			if (uploadFile != null) {
				fileSize = new Long(uploadFile.length()).intValue();
					
			long fileSizeInBytes = uploadFile.length();
		
			long fileSizeInKB = fileSizeInBytes / 1024;
		
			long fileSizeInMB = fileSizeInKB / 1024;

			if (fileSizeInMB > fileUploloadingSize) {
				addActionMessage("FileUploadingSize"); 
				return INPUT;
			}
			} 
			InputStream sourceInputStream = null;
			try {
				if (uploadFile != null) {
					fileData = getBytesFromFile(uploadFile);
					sourceInputStream = new FileInputStream(uploadFile);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		
			if (fileSize > 0) { // if new file is uploaded
				uploadFileOnNode(usercode, baseWorkFolder, fileData,
						fileName, tempBaseFolder, TranNo, fileSize, sourceInputStream,remark);

				}
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

		public boolean uploadFileOnNode(int userCode,String baseWorkFolder,
				byte[] fileData, String fileName, String tempBaseFolder, int tranNo,
				int fileSize, java.io.InputStream sourceInputStream, String remark) {
			// System.out.println("Node History ok");
			createFolderStruc(userCode, tranNo, baseWorkFolder, fileData,
					fileName, fileSize, sourceInputStream);
			updateNodeHistory(userCode, tranNo, fileName,remark);
			return true;
		}

		private void createFolderStruc(int userCode, int tranNo,
				String baseWorkFolder, byte[] fileData, String fileName,
				int fileSize, InputStream stream) {
			try {

				if (tranNo != -1) {
					// System.out.println("NODE ID="+nodeId);

					File tranFolder = new File(baseWorkFolder + "/" + userCode + "/" + tranNo);
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

		private void updateNodeHistory(int userCode, int tranNo, String fileName, String remark) {

			String folderPath = "/" + userCode + "/"  + tranNo;

			DTOWorkSpaceNodeHistory workSpaceNodeHistoryDTO = new DTOWorkSpaceNodeHistory();
			workSpaceNodeHistoryDTO.setUserCode(userCode);
			workSpaceNodeHistoryDTO.setFileName(fileName);
			workSpaceNodeHistoryDTO.setFileType(fontStyle);
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
		// //////////////////////////////////

		public String getUploadFileContentType() {
			return uploadFileContentType;
		}

		public void setUploadFileContentType(String uploadFileContentType) {
			this.uploadFileContentType = uploadFileContentType;
		}

		public String getUploadFileName() {
			return uploadFileName;
		}

		public void setUploadFileName(String uploadFileFileName) {
			this.uploadFileName = uploadFileFileName;
		}

		public File getUploadFile() {
			return uploadFile;
		}

		public void setUploadFile(File uploadFile) {
			this.uploadFile = uploadFile;
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
		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
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