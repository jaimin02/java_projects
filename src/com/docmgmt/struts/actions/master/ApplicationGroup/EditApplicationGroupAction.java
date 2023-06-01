package com.docmgmt.struts.actions.master.ApplicationGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.docmgmt.dto.DTOApplicationGroupMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.services.pdf.PdfPropUtilities;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EditApplicationGroupAction  extends ActionSupport {//implements Preparable{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		applicationHostDetail=docMgmtImpl.getApplicationHostingDetail();
		applicationCategoryDetail=docMgmtImpl.getApplicationCategoryDetail();
		attachmentHistory=docMgmtImpl.getAttachmentHistory(applicationCode);
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		baseWorkFolder = propertyInfo.getValue("ApplicationAttachment"); 
	    return SUCCESS;
	}
	
	public String SaveApplicationAction(){
		
		DTOApplicationGroupMst data = docMgmtImpl.getApplicationDetail(applicationCode);
		if(data.getHostingCode().equalsIgnoreCase(hostingId) && data.getCategoryCode().equalsIgnoreCase(catgoryId) && uploadFile==null){
			htmlContent="Please Update Hosting or Category value.";
			return "html";
		}
		
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		String inserFlag;
		byte[] fileData = null;
		String ApplicationAttachment = knetProperties.getValue("ApplicationAttachment");
		InputStream sourceInputStream = null;
		int fileSize = 0;
		if (uploadFile != null) {
			fileSize = new Long(uploadFile.length()).intValue();
			double fileSizeInBytes = uploadFile.length();
			
			double fileSizeInKB = fileSizeInBytes / 1024;
	
	         fileSizeInMB = (fileSizeInKB / 1024);
	         DecimalFormat df2 = new DecimalFormat("#.##");
	         df2.setRoundingMode(RoundingMode.UP);
	         System.out.println("String : " + df2.format(fileSizeInMB)); 
	         fileSizeInMB= Double.parseDouble(df2.format(fileSizeInMB));  
	         
	 		
	 		boolean flagtemp=false;
	 		try {
	 			if (uploadFile != null) {
	 				fileData = getBytesFromFile(uploadFile);
	 				sourceInputStream = new FileInputStream(uploadFile);
	 			}
	 		}
	 			 catch (IOException e) {
	 				e.printStackTrace();
	 			}
	 					
		}
		
		DTOApplicationGroupMst dto = new DTOApplicationGroupMst();
		dto.setRemark(remark);
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setApplicationCode(applicationCode);
		dto.setApplicationName(applicationName);
	    dto.setHostingCode(hostingId);
	    dto.setCategoryCode(catgoryId);
	    dto.setModifyBy(userId);
	    if(uploadFile != null) {
	    int tranNo = docMgmtImpl.getMaxTranForApplicationAttachment(applicationCode);
		tranNo = tranNo+1;
	    uploadRefFileOnNode(applicationCode,ApplicationAttachment, fileData,
	    		uploadingfileName, fileSize, sourceInputStream,tranNo);
	    dto.setAttachmentTitle(fileName);
	    dto.setFileName(uploadingfileName);
	    dto.setFileSize(fileSizeInMB);
	    dto.setAttachmentPath( year + "/"+ month+"/"+ date+"/" + applicationCode + "/" + tranNo);
	    inserFlag = docMgmtImpl.ApplicationAttahmentMstOp(dto, 2,false);
		    if(inserFlag=="true"){
		    	htmlContent="true";
				return "html";
		    }
			else{
				htmlContent=inserFlag;
				return INPUT;
			}
	    }
	    inserFlag = docMgmtImpl.ApplicationAttahmentMstOp(dto, 1,false);
	    if(inserFlag=="true"){
	    	htmlContent="true";
			return "html";
	    }
		else{
			htmlContent=inserFlag;
			return INPUT;
		}
		
		
	}
 		
 		
private void uploadRefFileOnNode(String applicationCode, String applicationAttachment, byte[] fileData, String fileName2,
		int fileSizeInMB2, InputStream sourceInputStream,int tranNo) {
	try {
		Calendar cal = Calendar.getInstance();
		Date toDaydate= new Date();
		cal.setTime(toDaydate);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH)+1;
		date = cal.get(Calendar.DAY_OF_MONTH);
			
					//if (tranNo != -1) {
		// System.out.println("NODE ID="+nodeId);
	
		/*File tranFolder = new File(baseWorkFolder + "/" + wsId + "/"
				+ nodeId + "/" + tranNo);*/
		
		File tranFolder = new File(applicationAttachment + "/" + year + "/"+ month+"/"+ date+"/" + applicationCode + "/" + tranNo);
		tranFolder.mkdirs();
	
		File uploadedFile = new File(tranFolder, uploadingfileName);
		// System.out.println("uploadfile path::"+uploadedFile);
		OutputStream bos = new FileOutputStream(uploadedFile);
		int temp = 0;
		while ((temp = sourceInputStream.read(fileData, 0, fileSizeInMB2)) != -1) {
			bos.write(fileData, 0, temp);
		}
		bos.close();
		sourceInputStream.close();
		
			// Auto Correcting Pdf Properties
	
			final String FileToCorrect = uploadedFile.getAbsolutePath();
			new Thread(new Runnable() {
				public void run() {
	
					PdfPropUtilities pdfUtil = new PdfPropUtilities();
					pdfUtil.autoCorrectPdfProp(FileToCorrect);
					
				}
			}).start();
	//}
	} catch (Exception ex) {
					ex.printStackTrace();
			}
	
 		}

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
 		
		public String DeleteApplicationAttachment()
		{
			DTOApplicationGroupMst dto = docMgmtImpl.getApplicationAttachmentDetailByTranNo(tranNo);
			
			docMgmtImpl.deleteApplicationAttachment(dto.getAttachmentTitle(),tranNo,remark);
			applicationCode=applicationCode;
			applicationName=applicationName;
			hostingId=hostingId;
			catgoryId=catgoryId;
			DTOApplicationGroupMst getApplicationDetail = docMgmtImpl.getApplicationDetail(applicationCode);
			remark=getApplicationDetail.getRemark();
			return SUCCESS;
		}
 		
		public String AttachmentNameExists() 
		{	
			boolean docNameExist = docMgmtImpl.attachmentNameExist(applicationCode,fileName);
			if (docNameExist== true || fileName=="")
			{
				htmlContent = fileName+" already exists.";
			}
			else
			{
				htmlContent = "<font color=\"green\">"+fileName+" is available. </font>";			
			}
			return SUCCESS;
		}	
		
	
	public String applicationCode;
	public String applicationName;
	public String hostingId;
	public String catgoryId;
	public String remark;
	public String fileName;
	public String uploadingfileName;
	public File uploadFile;
	public String statusIndi;
	public Vector applicationHostDetail;
	public Vector applicationCategoryDetail;
	public Vector attachmentHistory;
	public String htmlContent = "";
	public double fileSizeInMB;
	int year;
	int month;
	int date;
	String baseWorkFolder;
	int tranNo;
	
	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	
	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getHostingId() {
		return hostingId;
	}

	public void setHostingId(String hostingId) {
		this.hostingId = hostingId;
	}

	public String getCatgoryId() {
		return catgoryId;
	}

	public void setCatgoryId(String catgoryId) {
		this.catgoryId = catgoryId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getuploadingFileName() {
		return uploadingfileName;
	}

	public void setuploadingFileName(String uploadingfileName) {
		this.uploadingfileName = uploadingfileName;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public Vector getAttachmentHistory() {
		return attachmentHistory;
	}

	public void setAttachmentHistory(Vector attachmentHistory) {
		this.attachmentHistory = attachmentHistory;
	}

	public String getBaseWorkFolder() {
		return baseWorkFolder;
	}

	public void setBaseWorkFolder(String baseWorkFolder) {
		this.baseWorkFolder = baseWorkFolder;
	}

	public int getTranNo() {
		return tranNo;
	}

	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
	
}
