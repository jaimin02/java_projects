package com.docmgmt.struts.actions.ectdviewer.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;

public class OpenFileAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	InputStream inputStream;
	String contentDisposition;
	String contentType;

	String attachment;
	String fileWithPath;

	String fileName;
	String baseWorkFolder;
	String fileExt;
	String dossier;

	@Override
	public String execute() {

		String FilePathName = null;
		File file = null;

		// try {
		// Process p =
		// Runtime.getRuntime().exec("C:\\Program Files (x86)\\Internet Explorer\\iexplore.exe \"http://www.google.com\"");
		//			
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		if (fileWithPath != null) {
			FilePathName = fileWithPath;
			file = new File(FilePathName.trim());
			fileName = file.getName();
		}

		if (fileName != null) {
			if (fileName.substring(fileName.lastIndexOf(".")).equalsIgnoreCase(
					".pdf")) {
				contentType = "application/pdf";
			} else {
				contentType = "application/octet-stream";
			}
		}

		if (attachment != null && attachment.equals("true")) {
			contentDisposition = "attachment;filename=\"" + fileName + "\"";
		} else {
			contentDisposition = "filename=\"" + fileName + "\"";
		}

		if (file.exists()) {
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				byte[] fileData = new byte[fileInputStream.available()];
				fileInputStream.read(fileData);
				fileInputStream.close();
				inputStream = new ByteArrayInputStream(fileData);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("File Not Exist");
			return ERROR;
		}
		return SUCCESS;
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

	public String getDossier() {
		return dossier;
	}

	public void setDossier(String dossier) {
		this.dossier = dossier;
	}
}
