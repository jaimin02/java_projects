package com.docmgmt.struts.actions.smartCapture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSmartCaptureMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.itextpdf.text.DocumentException;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SmartCaptureAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	public String FileId;
	public InputStream inputStream;
	public String fileName;
    public long contentLength;
    String appPath;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();	
	
	@Override
	public String execute(){
		smartCaptureDetail=docMgmtImpl.getSmartCapDetail();		
		//appPath="E:/FileServerDoc/SmartCaptureApp/SmartCaptureApp_v1.0.0.exe";
		return SUCCESS;
	}
	
	public String DownloadZipFolder() throws SQLException, DocumentException, IOException{
		
		smartCaptureDetailDTO=docMgmtImpl.getSmartCapDetailDTO(FileId);
		File fileToDownload = new File(smartCaptureDetailDTO.getFilePath()+"/"+smartCaptureDetailDTO.getFileName());
		try {
			inputStream = new FileInputStream(fileToDownload);
		} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileName = fileToDownload.getName();
		contentLength = fileToDownload.length();
		System.out.println("Downloaded");
	    int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	    smartCaptureDetailDTO.setModifyBy(userId);
	    smartCaptureDetailDTO.setRemark("");
		docMgmtImpl.insertIntoSmartCapturedownloadhistory(smartCaptureDetailDTO);
		
		return "SUCCESS";

	}
 
public String getAppPath() {
		return appPath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

public Vector getSmartCaptureDetail() {
	return smartCaptureDetail;
}

public void setSmartCaptureDetail(Vector smartCaptureDetail) {
	this.smartCaptureDetail = smartCaptureDetail;
}

public Vector smartCaptureDetail;
public DTOSmartCaptureMst smartCaptureDetailDTO;
}
