package com.docmgmt.struts.actions.labelandpublish.HTMLPublish;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.docmgmt.dto.DTOSubmissionInfoHTML;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.master.WorkSpaceNodeHistory;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.beans.HtmlPublishTreeBean;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.itextpdf.text.DocumentException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class HTMLPublishAndSubmitAction extends ActionSupport{
	public static final long serialVersionUID=1L;
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	
	
	String workSpaceId;
	public File uploadLogo;
	String uploadLogoFileName;
	String logoSelection;
	DTOWorkSpaceMst dtows;
	ArrayList<DTOSubmissionInfoHTML> htmlSubDtl;
	ArrayList<DTOSubmissionInfoHTML> htmlSubDtlwithId;
	String subDesc;
	String extraHtmlCode;
	ArrayList<String> logoNameList;
	public String logoFileName;
	String LogoName;
	InputStream logoStream;
	String contentType;
	String contentDisposition;
	public char nonPublishableNode;
	public char Publish;
	public Vector<Integer> docDetails;
	public String uuId;
	
	static PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	FileManager fileManager=new FileManager();
	
	public String DownloadFile;
	public String DownloadFolderPath;
	List<String> fileList;
	String OUTPUT_ZIP_FILE = "";
	public InputStream inputStream;
	public String fileName;
    public long contentLength;
    public String htmlContent;

	
	public String getSubmissionDetails()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		
		//dtows = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		dtows = docMgmtImpl.getWorkSpaceDetailWSList(workSpaceId);
		htmlSubDtl = docMgmtImpl.getAllSubmissionInfoHTMLDetail(workSpaceId);
		
		WorkSpaceNodeHistory wnh = new WorkSpaceNodeHistory();
		docDetails =wnh.getNodeDetailsForPdfPublishForCheck(workSpaceId);

		File baseLogoDir = propertyInfo.getDir("LogoFilePath");		
		baseLogoDir=new File(propertyInfo.getValue("LogoFilePath"));
		
		logoNameList = new ArrayList<String>();
		if(baseLogoDir != null){
			String[] fileNames = baseLogoDir.list();
			for (int i = 0; i < fileNames.length; i++) {
				logoNameList.add(fileNames[i]);
			}
		}
		
		
		return SUCCESS;
	}
	public String showLogo() {
		File baseLogoDir = propertyInfo.getDir("LogoFilePath");
		File logoFile = new File(baseLogoDir,logoFileName);

		try {
			logoStream = new FileInputStream(logoFile);
			contentType = "image/jpeg";
			contentDisposition ="filename=\""+logoFileName+"\"";
		} catch (Exception e) {
			logoStream = null;
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String showImage() {
		File baseLogoDir = propertyInfo.getDir("signatureFile");
		Vector <DTOWorkSpaceNodeHistory> dto;
		int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto =docMgmtImpl.getUserSignatureDetail(usercode);		
		File logoFile;
		String fname="";
		if(dto.size()>0){
			fname = baseLogoDir + dto.get(0).getFolderName();
			File file = new File(fname);
			logoFile = new File(file,logoFileName);
		}
		else{
			logoFile = new File(baseLogoDir,logoFileName);
		}
		try {
			logoStream = new FileInputStream(logoFile);
			contentType = "image/jpeg";
			contentDisposition ="filename=\""+logoFileName+"\"";
		} catch (Exception e) {
			logoStream = null;
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String showImageHistory() {
		File baseLogoDir = propertyInfo.getDir("signatureFile");
		int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		File logoFile;
		String fname="";
		/*String temp = logoFileName.split("_")[0];
		String temp1=logoFileName.split("_")[1];
		tranNo = Integer.parseInt(temp1);
		logoFileName = temp;*/
		fname = baseLogoDir + "/"+ usercode + "/" + uuId;
		File file = new File(fname);
		logoFile = new File(file,logoFileName);
		
		try {
			logoStream = new FileInputStream(logoFile);
			contentType = "image/jpeg";
			contentDisposition ="filename=\""+logoFileName+"\"";
		} catch (Exception e) {
			logoStream = null;
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String saveSubmissionDetails() throws DocumentException, IOException, ParseException{
		
		File baseLogoDir = propertyInfo.getDir("LogoFilePath");
		if(baseLogoDir == null){
			//return "pathNotFound";
			return SUCCESS;
		}
		logoSelection = "Y";
		/*if (logoSelection.equals("Y"))
		{
			if (uploadLogo.exists())
			{
				try {
					InputStream input=new FileInputStream(uploadLogo);
					OutputStream output=new FileOutputStream(baseLogoDir.getAbsolutePath() + "/" + uploadLogoFileName);
					FileManager.copyStream(input,output);
					input.close();
					output.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else
		{
			if (!logoFileName.trim().equals("-1"))
				uploadLogoFileName=logoFileName;
			else
				uploadLogoFileName="";
		}*/
				
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupId = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());		
		
		Date toDaydate= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(toDaydate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int date = cal.get(Calendar.DAY_OF_MONTH);
		
		//DTOWorkSpaceMst ws= docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		
		DTOWorkSpaceMst ws= docMgmtImpl.getWorkSpaceDetailWSList(workSpaceId);
		
		int tranNo = docMgmtImpl.getNewTranNoForHTMLPublish(workSpaceId);
		String publishPath =ws.getBasePublishFolder()+"/"+year+"/"+month+"/"+date+"/"+workSpaceId+"/"+tranNo;
		
		DTOSubmissionInfoHTML dto = new DTOSubmissionInfoHTML();
		dto.setWorkSpaceId(workSpaceId);
		dto.setCreatedBy(userId);
		dto.setModifyBy(userId);
		dto.setDescription(subDesc);
		dto.setPublishPath(publishPath);
		//long submissionId = docMgmtImpl.insertSubmissionInfoHTML(dto);
		
		//htmlSubDtlwithId = docMgmtImpl.getPublishDetailfromID(submissionId);
		//if(htmlSubDtlwithId.size()>0){
		//DTOSubmissionInfoHTML latestSubdto = htmlSubDtlwithId.get(0); 
		String fullSubpath = publishPath;//latestSubdto.getPublishPath();
		
		
      	
		String temp = generateHTMLPublish(fullSubpath,userGroupId,userId,ws,baseLogoDir);	
		
		if(temp.equalsIgnoreCase("success")){
		File file1 = new File(fullSubpath);
		 
        double size = getFolderSize(file1);
        double fileSizeInKB = size / 1024;

        double fileSizeInMB = (fileSizeInKB / 1024);
        DecimalFormat df2 = new DecimalFormat("#.##");
        df2.setRoundingMode(RoundingMode.UP);
        System.out.println("String : " + df2.format(fileSizeInMB)); 
        fileSizeInMB= Double.parseDouble(df2.format(fileSizeInMB));
        dto.setPublishFolderSize(fileSizeInMB);
		
        long submissionId = docMgmtImpl.insertSubmissionInfoHTML(dto);
		}
		else{
			temp = "Error ocuure during Publish.";
			return temp;
		}
		//}
		return SUCCESS;
	}
	public static double getFolderSize(File folder)
    {
		double length = 0;
        File[] files = folder.listFiles();
 
        int count = files.length;
 
        // loop for traversing the directory
        for (int i = 0; i < count; i++) {
            if (files[i].isFile()) {
                length += files[i].length();
            }
            else {
                length += getFolderSize(files[i]);
            }
        }
        return length;
    }
	public String generateHTMLPublish(String fullSubpath ,int userGroupId,int userId,DTOWorkSpaceMst wsmst,File baseLogoDir) throws DocumentException, IOException, ParseException{		
		//****Generate the Final index.html file with external CSS and js file Start ********************// 					
		HtmlPublishTreeBean bean = new HtmlPublishTreeBean();
		bean.setHtmlSubmissionpath(fullSubpath);
		
		String treeHtml = bean.generateHtmlTree(workSpaceId, userGroupId, userId,nonPublishableNode,Publish);
				
		/*String finalHtml = " <html><head><link type=\"text/css\" rel=\"stylesheet\" href=\"util/css/style.css\"></style>";
		finalHtml+="<script language=\"javascript\" src=\"util/js/popup.js\"></script>";
		finalHtml+="</head><body>";
		finalHtml+="<script language=\"javascript\" src=\"util/js/wz_tooltip.js\"></script>";
		finalHtml += treeHtml;
		finalHtml +="</body></html>";
		*/
		String finalHtml = " <html><head><link type=\"text/css\" rel=\"stylesheet\" href=\"util/css/style.css\">";
		finalHtml+="<script language=\"javascript\" src=\"util/js/popup.js\"></script>";
		finalHtml+="</head><body bottommargin=\"30\" leftmargin=\"100\" topmargin=\"30\" rightmargin=\"0\"><script language=\"javascript\" src=\"util/js/wz_tooltip.js\"></script>";
		finalHtml+="<div id=\"container\"><div id=\"header\"><div id=\"topleft\"><img src=\"util/images/logo.png\" class=\"img\"/></div><div id=\"toptitle\"><font id=\"fntheader\">"+wsmst.getWorkSpaceDesc()+"</font></div><div id=\"topright\"><table border=\"0\" cellspacing=\"3\"><tr><td><font id=\"fntans\">Project Type:</font></td><td><font id=\"fnttitle\">&nbsp;"+wsmst.getProjectName()+"</font></td></tr><tr><td><font id=\"fntans\">Client Name:</font></td><td><font id=\"fnttitle\">&nbsp;"+wsmst.getClientName()+"</font></td></tr></table></div></div>";
		finalHtml+="<div id=\"content\">";
		finalHtml += treeHtml;
		finalHtml +=" </div></div></body></html>";

		/*String finalcss=".ul,li{font:Vardana;font-size:16px;list-style-type:squre;}a:link{text-decoration:none;}a:visited{color:#0000FF;text-decoration:none;}a:hover{color:#0099FF;text-decoration:none;}";
		
		File cssFile =new File("//sspl34/AccordDMS/docMgmtAndPub/PublishDestinationFolder/Vietnam/css/style.css");
		
		File cssParent = cssFile.getParentFile();
		
		if(!cssParent.exists()){
			cssParent.mkdirs();
		}
			try{
					Writer cssoutput = new BufferedWriter(new FileWriter(cssFile));
					cssoutput.write(finalcss);
					cssoutput.close();
					System.out.println("css File "+ cssFile +"  created");
				}catch(Exception e){
					System.out.println("Erro while creating The File: " + e);
			}
		
				
		String finaljs="function popup(url){var width=800;var height=500;var left = (screen.width-width)/2;var top = (screen.height-height)/2;var params = 'width='+width+', height='+height;params += ', top='+top+', left='+left;params += ', directories=no';params += ', location=no';params += ', menubar=no';params += ', resizable=no';params += ', scrollbars=no';params += ', status=no';params += ', toolbar=no';new_win = window.open(url,'windwofram5',params);if (window.focus) {new_win.focus()}return false;}";		
		File jsFile =new File("//sspl34/AccordDMS/docMgmtAndPub/PublishDestinationFolder/Vietnam/js/popup.js");
		
		File jsParent = jsFile.getParentFile();
		
		if(!jsParent.exists()){
			jsParent.mkdirs();
		}
			try{
					Writer cssoutput = new BufferedWriter(new FileWriter(jsFile));
					cssoutput.write(finaljs);
					cssoutput.close();
					System.out.println("Javascript File "+ jsFile +"  created");
				}catch(Exception e){
					System.out.println("Erro while creating The File: " + e);
			}
		*/
		
		File htmlFile = new  File(fullSubpath+"/index.html");
		//System.out.println(htmlFile.getAbsolutePath());
		File parent =htmlFile.getParentFile();
		
		if(!parent.exists()){
			parent.mkdirs();
			
		}
		
		
			try{
				Writer output=new BufferedWriter(new FileWriter(htmlFile));
				output.write(finalHtml);
				output.close();
				//System.out.println("File  " + htmlFile + " created");
			}catch(IOException e){
				System.out.println("Exception while creating file : " + e);
			}
			
			/*Copy util folder from workspace dir*/
			PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
			String basefolder = propertyInfo.getValue("BaseWorkFolder");			
			File htmlutil = new File(basefolder + "/util_html");
			File destutil = new File(fullSubpath);
			FileManager filemgr = new FileManager();
			filemgr.copyDirectory(htmlutil, destutil);
			addActionMessage("Publish successFully for project: "+wsmst.getWorkSpaceDesc());
			extraHtmlCode = "<input type=\"button\" class=\"button\" value=\"Back\" onclick=\"subinfo();\">";
			uploadLogoFileName="logo.png";
			
			if (!uploadLogoFileName.equals(""))
			{
				try {
					FileManager.deleteFile(new File(fullSubpath+"/util/images/logo.png"));
					OutputStream output=new FileOutputStream(fullSubpath+"/util/images/logo.png");
					InputStream input=new FileInputStream(baseLogoDir.getAbsolutePath() + "/" + uploadLogoFileName);
					FileManager.copyStream(input,output);
					output.close();
					input.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}	
			
			return SUCCESS;
		

	}
	
	public void openHtmlFile()
	{
		try {
			System.out.println("html file");
			
			
			Runtime.getRuntime().exec("cmd /c start firefox.exe //sspl137/test/zoo.html");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
public String CreateZipFolderForHTML() throws SQLException, DocumentException, IOException, ZipException{
		
		DownloadFile=DownloadFile.replace('/', '\\');
		int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		String baseDir = propertyInfo.getValue("PublishZipDir");
		//File fl=new File(DownloadFile+"\\"+"PublishDossier.zip");
		 File directoryToBeZipped = new File(DownloadFile);
		
		File fl=new File(baseDir+"\\"+workSpaceId+"\\"+usercode);
		File tempFile=new File(baseDir+"\\"+workSpaceId+"\\"+usercode+"\\PublishDossier.zip");
		File zipFile;
		if(tempFile.exists()){
			tempFile.delete();
			OUTPUT_ZIP_FILE=fl.toString()+"\\PublishDossier.zip";
		}
		else{
			zipFile=new File(fl.toString()+"\\PublishDossier.zip");
			fl.mkdirs();
			OUTPUT_ZIP_FILE=zipFile.toString();
			}
		 
		fileList = new ArrayList<String>();
		generateFileList(new File(DownloadFile));
		//OUTPUT_ZIP_FILE=DownloadFile+"\\"+"PublishDossier.zip";
		
		//zipIt(OUTPUT_ZIP_FILE);
		zipFile = new File(OUTPUT_ZIP_FILE);
		
		net.lingala.zip4j.core.ZipFile zip = new net.lingala.zip4j.core.ZipFile(zipFile);

		  // Adding the list of files and directories to be zipped to a list
		  ArrayList<File> fileList = new ArrayList<>();
		  Arrays.stream(directoryToBeZipped.listFiles()).forEach((File file) -> {fileList.add(file);});

		  ZipParameters parameters = new ZipParameters();
		  parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		  parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_FASTEST);
		  parameters.setEncryptFiles(false);

		  fileList.stream().forEach((File f) -> {
				try
				{
					if(f.isDirectory())
					{
						zip.addFolder(f, parameters);
					}
					else
					{
						zip.addFile(f, parameters);
					}
				}
				catch(ZipException zipExceptio)
				{
					//LOGGER.log(Level.SEVERE, "ZIP Exception while creating encrypted zips.", zipExceptio);
				}
			});
		/*
		File fileToDownload = new File(OUTPUT_ZIP_FILE);
		try {
			inputStream = new FileInputStream(fileToDownload);
		} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileName = fileToDownload.getName();
		contentLength = fileToDownload.length();*/
		System.out.println("Downloaded");
		OUTPUT_ZIP_FILE=OUTPUT_ZIP_FILE.replace('\\', '/');
	    htmlContent=OUTPUT_ZIP_FILE;
		
		return "html";
	}
	
public String DownloadZipFolderForHTML(){
	DownloadFolderPath=DownloadFolderPath.replace('/', '\\');
        File fileToDownload = new File(DownloadFolderPath);
        try {
			inputStream = new FileInputStream(fileToDownload);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        fileName = fileToDownload.getName();
        contentLength = fileToDownload.length();
        return SUCCESS;
    }


public void generateFileList(File node){
	//add file only
		
		if(node.isFile()){
				fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
		}
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				generateFileList(new File(node, filename));
			}
		}
}

private String generateZipEntry(String file){
	return file.substring(DownloadFile.length()+1, file.length());
}

public void zipIt(String zipFile){
	byte[] buffer = new byte[1024];
	try{

		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
	    //System.out.println("Output to Zip : " + zipFile);
	    
	    for(String file : this.fileList){
	    	//System.out.println("File Added : " + file);
	    	ZipEntry ze= new ZipEntry(file);
	        zos.putNextEntry(ze);
	        FileInputStream in =new FileInputStream(DownloadFile + File.separator + file);
	        int len;
	        while ((len = in.read(buffer)) > 0) {
	        	zos.write(buffer, 0, len);
	        }
	        in.close();
	    }
	    
	    zos.closeEntry();
	    //remember close it
	    zos.close();
	    System.out.println("Done");
	}catch(IOException ex){
	       ex.printStackTrace();
	}
}
	
	/*Getters-Setters*/
	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public DTOWorkSpaceMst getDtows() {
		return dtows;
	}

	public void setDtows(DTOWorkSpaceMst dtows) {
		this.dtows = dtows;
	}

	public ArrayList<DTOSubmissionInfoHTML> getHtmlSubDtl() {
		return htmlSubDtl;
	}

	public void setHtmlSubDtl(ArrayList<DTOSubmissionInfoHTML> htmlSubDtl) {
		this.htmlSubDtl = htmlSubDtl;
	}
	public ArrayList<DTOSubmissionInfoHTML> getHtmlSubDtlwithId() {
		return htmlSubDtlwithId;
	}

	public void setHtmlSubDtlwithId(
			ArrayList<DTOSubmissionInfoHTML> htmlSubDtlwithId) {
		this.htmlSubDtlwithId = htmlSubDtlwithId;
	}
	
	public String getSubDesc() {
		return subDesc;
	}

	public void setSubDesc(String subDesc) {
		this.subDesc = subDesc;
	}
	public String getExtraHtmlCode() {
		return extraHtmlCode;
	}

	public void setExtraHtmlCode(String extraHtmlCode) {
		this.extraHtmlCode = extraHtmlCode;
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

	public InputStream getLogoStream() {
		return logoStream;
	}

	public void setLogoStream(InputStream logoStream) {
		this.logoStream = logoStream;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}
	public String getLogoName() {
		return LogoName;
	}
	public void setLogoName(String logoName) {
		LogoName = logoName;
	}
	public String getLogoSelection() {
		return logoSelection;
	}
	public void setLogoSelection(String logoSelection) {
		this.logoSelection = logoSelection;
	}
	public Vector<Integer> getDocDetails() {
		return docDetails;
	}
	public void setDocDetails(Vector<Integer> docDetails) {
		this.docDetails = docDetails;
	}

}