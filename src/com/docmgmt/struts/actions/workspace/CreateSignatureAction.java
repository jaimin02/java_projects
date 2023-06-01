package com.docmgmt.struts.actions.workspace;

import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

import org.json.simple.parser.ParseException;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.docmgmt.struts.actions.util.CommonUtilMethod;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

	public class CreateSignatureAction extends ActionSupport implements Preparable {
		
		private static final long serialVersionUID = 1L;

		private static final Graphics Graphics = null;

		private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
		public int usergrpcode;
		public int usercode;
		public String userType;
		public String UserName;

		public File uploadFile;
		public String uploadFileName; // The uploaded file name

		public String nodeFolderName;
		public String remark="";
		public String fileSizeProperty;
		public int fileUploloadingSize;
		String baseWorkFolder="";
		public String fontStyle="";
		String name = "";
		Vector <DTOWorkSpaceNodeHistory> dto;
		public String userName;
		public String fname="";
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();	
		public String ISTCurrentTime;
		public String ESTCurrentTime;
		
		@Override
		public String execute()
		{
			return SUCCESS;
		}	
		
		
		public void prepare() throws Exception {
			
			userName=ActionContext.getContext().getSession().get("username").toString();
			
			usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			
			dto =docMgmtImpl.getAllUserSignatureDetail(usercode);
			//dto =docMgmtImpl.getUserSignatureDetail(usercode);
			baseWorkFolder = propertyInfo.getValue("signatureFile");
			if(dto.size()>0){
				fname = dto.get(0).getFileName();
			}
			ArrayList<String> time = new ArrayList<String>();
			String locationName = ActionContext.getContext().getSession().get("locationname").toString();
			String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
			 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(timestamp,locationName,countryCode);
				ISTCurrentTime = time.get(0);
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(timestamp,locationName,countryCode);
				ISTCurrentTime = time.get(0);
				ESTCurrentTime = time.get(1);
			}
			
			//else
			//fname = "sign.png";	
		}
		
		public String SaveFile() throws IOException, ParseException{
			
			usergrpcode = Integer.parseInt(ActionContext.getContext().getSession()
					.get("usergroupcode").toString());
			usercode = Integer.parseInt(ActionContext.getContext().getSession()
					.get("userid").toString());
			userType = ActionContext.getContext().getSession().get("usertypecode")
					.toString();
			UserName = ActionContext.getContext().getSession().get("username")
					.toString();
					
			fileSizeProperty = propertyInfo.getValue("FileUploadingSize");
			fileUploloadingSize = Integer.parseInt(fileSizeProperty);
			baseWorkFolder = propertyInfo.getValue("signatureFile");
						
			if(remark.isEmpty() || remark.equals("")){
				remark="New";
			}
			/*Graphics g = Graphics;
			Font myFont = new Font("Calibri", Font.BOLD,14);
			g.setFont(myFont);
			g.drawString(UserName, 10, 50);
			myFont = new Font("Calibri", Font.ITALIC ,14);*/
						
			
			String fileName = "";
			
			String tempBaseFolder = baseWorkFolder;
			
			String uuid=null;
			UUID uuId=null;
			String uId=null;
			File dirPath=null;
			
			DTOUserMst user=docMgmtImpl.getUserByCode(usercode);
        	String isADUserAuthenticate="";
        	String objectGUID="";
			
			//AD sign starts
        	
			if(user.getIsAdUser()=='Y'){
				Vector <DTOWorkSpaceNodeHistory> dtoWsHistory =docMgmtImpl.getUserSignatureDetail(usercode);
        		if(user.getStatusIndi()=='N' || user.getStatusIndi()=='E'){
        			
        			if(dtoWsHistory.size()<=0){
        				String adPassword=ActionContext.getContext().getSession().get("adUserPass").toString();
            			String adUserName=user.getAdUserName();
            			adUserName=adUserName.substring(adUserName.lastIndexOf("\\") + 1);
            			//isADUserAuthenticate=docMgmtImpl.authenticateADUser(adUserName,adPassword);
            			adPassword = CommonUtilMethod.replaceSpecialCharacters(adPassword);
            			isADUserAuthenticate=docMgmtImpl.authenticateADUser(adUserName,adPassword);
        			}
        			else{
        				isADUserAuthenticate=dtoWsHistory.get(0).getFolderName().split("/")[2];
        			}
        			
        		}
        	
	        	if (isADUserAuthenticate.contains("false")) {
	        		addActionError("You have AD User rights. Please login with correct AD Username and Password");
	        		return INPUT;
	        	}
	        	
	        	if(dtoWsHistory.size()<=0){
		        	String result=isADUserAuthenticate.split(":")[2];
		        	int startingIndex = result.indexOf("{");
		        	int closingIndex = result.indexOf("}");
		        	objectGUID = result.substring(startingIndex + 1, closingIndex);
		        	System.out.println(objectGUID);
	        	}
	        	
	        	//Vector <DTOWorkSpaceNodeHistory> dtoWsHistory =docMgmtImpl.getUserSignatureDetail(usercode);
				if(dtoWsHistory.size()<=0){
					//uuId=objectGUID;
					uId = objectGUID;
					int TranNo = docMgmtImpl.getNewTranNoForUserSignature(usercode);
					dirPath = new File(tempBaseFolder + "/" + usercode + "/"+ uId);
				}
				else{
					uuid=dtoWsHistory.get(0).getFolderName().split("/")[2];
					uId = uuid.toString();
					dirPath = new File(tempBaseFolder + "/" + usercode + "/"+ dtoWsHistory.get(0).getFolderName().split("/")[2]);
				}
				
				if (!dirPath.exists() && !dirPath.mkdirs()) {
					addActionMessage("pdfUpload");
					return INPUT;
			    }
				/**
				 * If user wants to use the source file then first convert it to PDF
				 * first and use it
				 * */
				if(!uploadFileName.isEmpty()){
				nodeFolderName = uploadFileName;
				
				int dotIndex = nodeFolderName.lastIndexOf(".");
				String fileNameExt = uploadFileName
						.substring(uploadFileName.lastIndexOf(".") + 1);
				fileNameExt = fileNameExt.toLowerCase();
				

				// System.out.println("====>"+nodeFolderName.substring(0,dotIndex));

				if (dotIndex != -1) {
					name = nodeFolderName.substring(0, dotIndex);
				} else {
					name = nodeFolderName;
				}
				
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
					uploadFileOnNode(usercode,uId, baseWorkFolder, fileData,
							fileName, fileSize, sourceInputStream,remark);

					}
				
				}
				else{
					updateNodeHistory(usercode, uId, fileName,remark);
				}
	        	return SUCCESS;
        	}
			
			//AD sign ends
			
			
			Vector <DTOWorkSpaceNodeHistory> dtoWsHistory =docMgmtImpl.getUserSignatureDetail(usercode);
			if(dtoWsHistory.size()<=0){
				uuId=UUID.randomUUID();
				uId = uuId.toString();
				int TranNo = docMgmtImpl.getNewTranNoForUserSignature(usercode);
				dirPath = new File(tempBaseFolder + "/" + usercode + "/"+ uId);
			}
			else{
				uuid=dtoWsHistory.get(0).getFolderName().split("/")[2];
				uId = uuid.toString();
				dirPath = new File(tempBaseFolder + "/" + usercode + "/"+ dtoWsHistory.get(0).getFolderName().split("/")[2]);
			}
			
			//UUID uuid=UUID.randomUUID();
			
			//File dirPath = new File(tempBaseFolder + "/" + usercode + "/"+ uId);
			//int TranNo = docMgmtImpl.getNewTranNoForUserSignature(usercode);
			//File dirPath = new File(tempBaseFolder + "/" + usercode + "/"+ uId);
			//checking server is accessible or not
			if (!dirPath.exists() && !dirPath.mkdirs()) {
				addActionMessage("pdfUpload");
				return INPUT;
		    }
			/**
			 * If user wants to use the source file then first convert it to PDF
			 * first and use it
			 * */
			if(!uploadFileName.isEmpty()){
			nodeFolderName = uploadFileName;
			
			int dotIndex = nodeFolderName.lastIndexOf(".");
			String fileNameExt = uploadFileName
					.substring(uploadFileName.lastIndexOf(".") + 1);
			fileNameExt = fileNameExt.toLowerCase();
			

			// System.out.println("====>"+nodeFolderName.substring(0,dotIndex));

			if (dotIndex != -1) {
				name = nodeFolderName.substring(0, dotIndex);
			} else {
				name = nodeFolderName;
			}
			
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
				uploadFileOnNode(usercode,uId, baseWorkFolder, fileData,
						fileName, fileSize, sourceInputStream,remark);

				}
			
			}
			else{
				updateNodeHistory(usercode, uId, fileName,remark);
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

		public boolean uploadFileOnNode(int userCode,String uId, String baseWorkFolder,
				byte[] fileData, String fileName, int fileSize, 
				java.io.InputStream sourceInputStream, String remark) {
			// System.out.println("Node History ok");
			createFolderStruc(userCode, uId, baseWorkFolder, fileData,
					fileName, fileSize, sourceInputStream);
			updateNodeHistory(userCode, uId, fileName,remark);
			return true;
		}

		private void createFolderStruc(int userCode, String uId,
				String baseWorkFolder, byte[] fileData, String fileName,
				int fileSize, InputStream stream) {
			try {

				if (uId != "") {
					// System.out.println("NODE ID="+nodeId);

					File tranFolder = new File(baseWorkFolder + "/" + userCode + "/" + uId);
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
										
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		private void updateNodeHistory(int userCode, String uId, String fileName, String remark) {

			String folderPath = "/" + userCode + "/"  + uId;

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
			Vector <DTOWorkSpaceNodeHistory> dtoWsHistory =docMgmtImpl.getUserSignatureDetail(usercode);
			if(dtoWsHistory.size()<=0)
				workSpaceNodeHistoryDTO.setDefaultFileFormat(uId);
			else
				workSpaceNodeHistoryDTO.setDefaultFileFormat(dtoWsHistory.get(0).getFolderName().split("/")[2]);
			workSpaceNodeHistoryDTO.setStatusIndi('N');
			String adUser="";
			if(ActionContext.getContext().getSession().get("adUser")!=null)
				adUser=ActionContext.getContext().getSession().get("adUser").toString();
			if(adUser.equalsIgnoreCase("Y"))
				workSpaceNodeHistoryDTO.setIsAdUser('Y');
			docMgmtImpl.insertUserSignature(workSpaceNodeHistoryDTO);

			workSpaceNodeHistoryDTO = null;
		}
		// //////////////////////////////////

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
		

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}	
		
		public int getUsercode() {
			return usercode;
		}

		public void setUsercode(int usercode) {
			this.usercode = usercode;
		}

		public String getFontStyle() {
			return fontStyle;
		}

		public void setFontStyle(String fontStyle) {
			this.fontStyle = fontStyle;
		}

		public Vector<DTOWorkSpaceNodeHistory> getDto() {
			return dto;
		}

		public void setDto(Vector<DTOWorkSpaceNodeHistory> dto) {
			this.dto = dto;
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
