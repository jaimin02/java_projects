package com.docmgmt.struts.actions.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

	public class SignOffOpenFileActionFromSingleDocDashboard extends ActionSupport {
		private static final long serialVersionUID = 1L;

		InputStream inputStream;
		String contentDisposition;
		String contentType;

		String attachment;
		String fileWithPath;
		String fileName;
		String workSpaceId;
		String nodeId;
		String tranNo;
		String baseWorkFolder;
		String fileExt;
		String folderName;
		int usercode;
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();

		@Override
		public String execute() {

			String FilePathName = null;
			File file = null;
			usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			String temp = fileWithPath;
			fileWithPath=null;
			if (fileWithPath != null) {
				FilePathName = fileWithPath;
				file = new File(FilePathName.trim());
				fileName = file.getName();

				System.out.println(fileName);
			} else // if fileWithPath is null use old code
			{
								
				/*DTOWorkSpaceNodeHistory dto = docMgmtImpl
						.getWorkspaceNodeHistorybyTranNo(workSpaceId, Integer
								.parseInt(nodeId), Integer.parseInt(tranNo));*/
				fileName = temp.substring(temp.lastIndexOf("/")+1);
				System.out.println("FileName: "+fileName);
				String name = temp.substring(0,temp.lastIndexOf("/"));
				tranNo = name.substring(name.lastIndexOf("/")+1);
				System.out.println("TranNo: "+tranNo);
				String newname = name.substring(0,name.lastIndexOf("/"));
				System.out.println(newname);
				nodeId = newname.substring(newname.lastIndexOf("/")+1);
				System.out.println("nodeId: "+nodeId);
				String newname1 = newname.substring(0,newname.lastIndexOf("/"));
				System.out.println(newname1);
				workSpaceId = newname1.substring(newname1.lastIndexOf("/")+1);
				System.out.println("workSpaceId: "+workSpaceId);
				baseWorkFolder = newname1.substring(0,newname1.lastIndexOf("//"));
				System.out.println("baseWorkFolder: "+baseWorkFolder);
				
				DTOWorkSpaceNodeHistory dto = docMgmtImpl
						.getWorkspaceNodeHistorybyTranNoOpenAction(workSpaceId, Integer
								.parseInt(nodeId), Integer.parseInt(tranNo));
				folderName = dto.getFolderName();
				FilePathName = baseWorkFolder + folderName + File.separator
						+ fileName;
				file = new File(FilePathName);
			}				

			if (fileName != null) {
				if (fileName.substring(fileName.lastIndexOf(".")).equalsIgnoreCase(
						".pdf")) {
					contentType = "application/pdf";
				}
				else if (fileName.substring(fileName.lastIndexOf(".")).equalsIgnoreCase(
				".doc") || fileName.substring(fileName.lastIndexOf(".")).equalsIgnoreCase(
				".docx")) {
			contentType = "application/msword";
				}	else {
					contentType = "application/octet-stream";
				}
				
			}

			if (attachment != null && attachment.equals("true")) {
				contentDisposition = "attachment;filename=\"" + fileName + "\"";
			} else {
				contentDisposition = "filename=\"" + fileName + "\"";
			}

			if (file.exists()) {
				System.out.println("File Exist...");
				try {
					 FileInputStream fileInputStream =new FileInputStream(file);
//					BufferedInputStream fileInputStream = new BufferedInputStream(
//							new FileInputStream(file));

					System.out.println(fileInputStream.available());
					
					byte[] fileData = new byte[fileInputStream.available()];
					
					fileInputStream.read(fileData);
					fileInputStream.close();
					inputStream = new ByteArrayInputStream(fileData);
					
					DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
					dto.setWorkSpaceId(workSpaceId);
					dto.setNodeId( Integer.parseInt(nodeId));
					dto.setTranNo(0);
					dto.setFileName(fileName);
					dto.setFolderName(folderName);
					dto.setRemark("");
					dto.setModifyBy(usercode);
					dto.setStatusIndi('N');
					dto.setMode(1);
					
					int checkFileOpen = docMgmtImpl.getfileopenforsignHistory(dto);
					
					if(checkFileOpen==0){
					    docMgmtImpl.insertIntofileopenforsign(dto);
					}
			
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("File Not Exist...");
				return ERROR;
			}
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
	}

