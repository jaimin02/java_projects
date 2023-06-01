package com.docmgmt.struts.actions.workspace;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.parser.ParseException;

import com.aspose.words.Document;
import com.docmgmt.dto.DTOAssignNodeRights;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.itextpdf.text.pdf.PdfReader;
import com.opensymphony.xwork2.ActionSupport;

public class FileOpenFromPDFAction extends ActionSupport{
	
	public String wsId;
	public int nodeId;
	public int tranNo;
	public String uuId;
	public String fileName;
	public String fileType;
	public String createdBy;
	public String createdOn;
	public String createdOnIST;
	public String createdOnEST;
	public int totalPages;
	public String baseWorkFolder;
	public int signatories;
	public String status;
	
	public String userSignId;
	public String userSignatureName;
	public String userRoleName;
	public String dateForPreview;
	public String userEmail;
	public String actionPerformed;
	public int userCode ;
	public int stageId;
	public String hashCode; 
	public String docId;
	
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	
	
	public String execute() throws IOException, ParseException{
		
		//int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		//Vector<DTOWorkSpaceNodeHistory> getNodeHistory=docMgmtImpl.getFirstNodeHistoryByTranNo(wsId, nodeId);
		String locationName=docMgmtImpl.getUserByCode(userCode).getUserLocationName();
		String countryCode=docMgmtImpl.getUserByCode(userCode).getCountyCode();
		
		Vector<DTOWorkSpaceNodeHistory> getNodeHistory=docMgmtImpl.getFirstNodeHistoryByTranNoAndCountryCode(wsId, nodeId,locationName,countryCode);
		uuId=getNodeHistory.get(0).getUuId().toString();
		createdBy=docMgmtImpl.getUserByCode(getNodeHistory.get(0).getModifyBy()).getUserName();
		createdOn=getNodeHistory.get(0).getISTDateTime();
		
		createdOnIST=getNodeHistory.get(0).getISTDateTime();
		createdOnEST=getNodeHistory.get(0).getESTDateTime(); 
		
		Vector<DTOWorkSpaceNodeHistory> getMaxNodeHistory=docMgmtImpl.getMaxNodeHistoryByTranNo(wsId, nodeId);
		fileName=getMaxNodeHistory.get(0).getFileName();
		fileType=getMaxNodeHistory.get(0).getFileType();
		baseWorkFolder=propertyInfo.getValue("BaseWorkFolder");
		String filePath=baseWorkFolder+"/"+getMaxNodeHistory.get(0).getBaseWorkFolder()+"/"+getMaxNodeHistory.get(0).getFileName();
	
		String strPath =FilenameUtils.getExtension(filePath);
		System.out.println("filePath"+filePath);
		totalPages=0;
		if(strPath.toLowerCase().equals("docx") || strPath.toLowerCase().equals("doc")){
			String fileNameWithoutExt=getMaxNodeHistory.get(0).getFileName().substring(0, getMaxNodeHistory.get(0).getFileName().lastIndexOf('.'));
	 		String pdfFileName=fileNameWithoutExt+".pdf";
	 		File check =new File(baseWorkFolder+"/"+getMaxNodeHistory.get(0).getBaseWorkFolder()+"/"+pdfFileName);
	 		if(check.exists()){
	 			PdfReader reader1=null;
				try {
					reader1 = new PdfReader(check.toString());
				} catch (IOException e) {
					
					e.printStackTrace();
					totalPages=0;
				}
				totalPages = reader1.getNumberOfPages();
	 		}else{
				try {                  
					Document doc = new Document(filePath);                                         
					totalPages = doc.getPageCount();           
					
				} catch (Exception e) {
					e.printStackTrace();
					totalPages=0;
				}
	 		}
		}else if(strPath.toLowerCase().equals("pdf")){
			PdfReader reader1=null;
			try {
				reader1 = new PdfReader(filePath);
			} catch (IOException e) {
				
				e.printStackTrace();
				totalPages=0;
			}
			totalPages = reader1.getNumberOfPages();
		}else{
			totalPages=0;
		}
		
		//doc.close();
		//Vector<DTOWorkSpaceNodeHistory> userHistory = docMgmtImpl.getProjectSignOffHistory(wsId, nodeId);
		Vector<DTOWorkSpaceUserRightsMst> userHistory=docMgmtImpl.getUserRightsDetail(wsId, nodeId);
		signatories=userHistory.size();
		
		//get assigned users
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForSingleDoc(wsId, nodeId);
		
		//get users who signed
		Vector<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl.getProjectSignOffHistory(wsId, nodeId);
		
		if(getUserRightsDetail.size()==tempHistory.size()){
			status="true";
		}else{
			status="false";
		}
		
		getMaxNodeHistory=docMgmtImpl.getMaxNodeHistoryByTranNo(wsId, nodeId,tranNo);
		Vector <DTOWorkSpaceNodeHistory> dtoWsHistory =docMgmtImpl.getUserSignatureDetailByCountry(userCode,locationName,countryCode);
		//userSignId=dtoWsHistory.get(0).getUuId();
		userSignId=dtoWsHistory.get(0).getFolderName().split("/")[2];
		userSignatureName=dtoWsHistory.get(0).getUserName();
		//userSignatureName=docMgmtImpl.getUserByCode(getMaxNodeHistory.get(0).getModifyBy()).getUserName();
		userRoleName=docMgmtImpl.getRoleName(getMaxNodeHistory.get(0).getRoleCode());
		
		
		ArrayList<String> time = new ArrayList<String>();
		/*String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		*/
		Vector<DTOAssignNodeRights> getDataForNodeId = docMgmtImpl.getDataForNodeIdByTranNo(wsId, nodeId,tranNo,userCode);
		
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		if(countryCode.equalsIgnoreCase("IND")){
			time = docMgmtImpl.TimeZoneConversion(getDataForNodeId.get(0).getModifyOn(),locationName,countryCode);
			dateForPreview = time.get(0);
		}
		else{
			time = docMgmtImpl.TimeZoneConversion(dtoWsHistory.get(0).getModifyOn(),locationName,countryCode);
			dateForPreview = time.get(1);
		}

		Vector<DTOWorkSpaceNodeHistory> getActionPerformed = docMgmtImpl.getProjectSignOffHistory(wsId, nodeId);
		for(int i=0;i<getActionPerformed.size();i++){
			if(userCode==getActionPerformed.get(i).getUserCode()){
				actionPerformed=getActionPerformed.get(i).getStageDesc();
				stageId=getActionPerformed.get(i).getStageId();
			}
		}
		userEmail=docMgmtImpl.getUserByCode(getMaxNodeHistory.get(0).getModifyBy()).getLoginName();
		
		tranNo=docMgmtImpl.getTranNoForSign(wsId,nodeId,uuId);
		System.out.println("WsId:"+wsId+"nodeId:"+nodeId+"docId:"+uuId+"tranNo:"+tranNo+"userCode:"+userCode);
		if(tranNo != -1){
			hashCode = docMgmtImpl.getDocumentHashChainMst(wsId,nodeId,tranNo,userCode);
			System.out.println("hashCode:"+hashCode);
		}
		else
			hashCode = "-";
		
		
		return SUCCESS;
	}
	
	public String PDFForESignature(){

		
		//int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		//Vector<DTOWorkSpaceNodeHistory> getNodeHistory=docMgmtImpl.getFirstNodeHistoryByTranNo(wsId, nodeId);
		String locationName=docMgmtImpl.getUserByCode(userCode).getUserLocationName();
		String countryCode=docMgmtImpl.getUserByCode(userCode).getCountyCode();
		
		Vector<DTOWorkSpaceNodeHistory> getNodeHistory=docMgmtImpl.getFirstNodeHistoryByTranNoAndCountryCode(wsId, nodeId,locationName,countryCode);
		uuId=getNodeHistory.get(0).getUuId().toString();
		createdBy=docMgmtImpl.getUserByCode(getNodeHistory.get(0).getModifyBy()).getUserName();
		createdOn=getNodeHistory.get(0).getISTDateTime();
		
		createdOnIST=getNodeHistory.get(0).getISTDateTime();
		createdOnEST=getNodeHistory.get(0).getESTDateTime(); 
		
		Vector<DTOWorkSpaceNodeHistory> getMaxNodeHistory=docMgmtImpl.getMaxNodeHistoryByTranNo(wsId, nodeId);
		fileName=getMaxNodeHistory.get(0).getFileName();
		fileType=getMaxNodeHistory.get(0).getFileType();
		baseWorkFolder=propertyInfo.getValue("BaseWorkFolder");
		String filePath=baseWorkFolder+"/"+getMaxNodeHistory.get(0).getBaseWorkFolder()+"/"+getMaxNodeHistory.get(0).getFileName();
		
		String strPath =FilenameUtils.getExtension(filePath);
		System.out.println("filePath"+filePath);
		totalPages=0;
		if(strPath.toLowerCase().equals("docx") || strPath.toLowerCase().equals("doc")){
			String fileNameWithoutExt=getMaxNodeHistory.get(0).getFileName().substring(0, getMaxNodeHistory.get(0).getFileName().lastIndexOf('.'));
	 		String pdfFileName=fileNameWithoutExt+".pdf";
	 		File check =new File(baseWorkFolder+"/"+getMaxNodeHistory.get(0).getBaseWorkFolder()+"/"+pdfFileName);
	 		if(check.exists()){
	 			PdfReader reader1=null;
				try {
					reader1 = new PdfReader(check.toString());
				} catch (IOException e) {
					
					e.printStackTrace();
					totalPages=0;
				}
				totalPages = reader1.getNumberOfPages();
	 		}else{
				try {                  
					Document doc = new Document(filePath);                                         
					totalPages = doc.getPageCount();           
					
				} catch (Exception e) {
					e.printStackTrace();
					totalPages=0;
				}
	 		}
		}else if(strPath.toLowerCase().equals("pdf")){
			PdfReader reader1=null;
			try {
				reader1 = new PdfReader(filePath);
			} catch (IOException e) {
				
				e.printStackTrace();
				totalPages=0;
			}
			totalPages = reader1.getNumberOfPages();
		}else{
			totalPages=0;
		}
		
		//doc.close();
		//Vector<DTOWorkSpaceNodeHistory> userHistory = docMgmtImpl.getProjectSignOffHistory(wsId, nodeId);
		Vector<DTOWorkSpaceUserRightsMst> userHistory=docMgmtImpl.getUserRightsDetailForEsignature(wsId, nodeId);
		signatories=userHistory.size();
		
		//get assigned users
		Vector<DTOWorkSpaceUserRightsMst> getUserRightsDetail = docMgmtImpl.getUserRightsDetailForEsignature(wsId, nodeId);
		
		//get users who signed
		Vector<DTOWorkSpaceNodeHistory> tempHistory = docMgmtImpl.getProjectSignOffHistoryForESignature(wsId, nodeId);
		
		//if(getUserRightsDetail.size()==tempHistory.size()/3){
		if(getUserRightsDetail.size()==tempHistory.size()){
			status="true";
		}else{
			status="false";
		}
		
		getMaxNodeHistory=docMgmtImpl.getMaxNodeHistoryByTranNo(wsId, nodeId,tranNo);
		Vector <DTOWorkSpaceNodeHistory> dtoWsHistory =docMgmtImpl.getUserSignatureDetailByCountry(userCode,locationName,countryCode);
		//userSignId=dtoWsHistory.get(0).getUuId();
		userSignId=dtoWsHistory.get(0).getFolderName().split("/")[2];
		userSignatureName=dtoWsHistory.get(0).getUserName();
		//userSignatureName=docMgmtImpl.getUserByCode(getMaxNodeHistory.get(0).getModifyBy()).getUserName();
		userRoleName=docMgmtImpl.getRoleName(getMaxNodeHistory.get(0).getRoleCode());
		
		
		ArrayList<String> time = new ArrayList<String>();
		/*String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		*/
		Vector<DTOAssignNodeRights> getDataForNodeId = docMgmtImpl.getDataForNodeIdByTranNo(wsId, nodeId,tranNo,userCode);
		
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		if(countryCode.equalsIgnoreCase("IND")){
			time = docMgmtImpl.TimeZoneConversion(getDataForNodeId.get(0).getModifyOn(),locationName,countryCode);
			dateForPreview = time.get(0);
		}
		else{
			time = docMgmtImpl.TimeZoneConversion(dtoWsHistory.get(0).getModifyOn(),locationName,countryCode);
			dateForPreview = time.get(1);
		}

		ArrayList<DTOWorkSpaceNodeHistory> getActionPerformed = docMgmtImpl.showFullNodeHistoryForESignatureForUser(wsId, nodeId, getMaxNodeHistory.get(0).getModifyBy(),locationName,countryCode);
		for(int i=0;i<getActionPerformed.size();i++){
			if(userCode==getActionPerformed.get(i).getModifyBy()){
				actionPerformed=getActionPerformed.get(i).getStageDesc();
				stageId=getActionPerformed.get(i).getStageId();
			}
		}
		userEmail=docMgmtImpl.getUserByCode(getMaxNodeHistory.get(0).getModifyBy()).getLoginName();
		
		//tranNo=docMgmtImpl.getTranNoForSign(wsId,nodeId,docId);
		tranNo=docMgmtImpl.getTranNoForSign(wsId,nodeId,uuId);
		System.out.println("WsId:"+wsId+"nodeId:"+nodeId+"uuId:"+uuId+"tranNo:"+tranNo+"userCode:"+userCode);
		if(tranNo != -1){
			hashCode = docMgmtImpl.getDocumentHashChainMst(wsId,nodeId,tranNo,userCode);
			System.out.println("hashCode:"+hashCode);
		}
		else
			hashCode = "-";
		
		return SUCCESS;
	
	}
	
}
