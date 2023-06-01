package com.docmgmt.struts.actions.labelandpublish.PDFPublish;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.docmgmt.dto.DTOPdfPublishDtl;
import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOSubmissionInfoDMSDtl;
import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.beans.PdfPublishTreeBean;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DisplayDocuments extends ActionSupport {

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();

	String workSpaceId;
	Vector<DTOWorkSpaceNodeHistory> docDetails;

	public String productName = "";
	public String productName2 = "";
	public String coverpage_productname = "";
	public String coverpage_submittedby = "";
	public String coverpage_submittedto = "";
	public String version = "";
	public boolean flag=false;

	static PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	FileManager fileManager = new FileManager();
	ArrayList<String> logoNameList;
	public String logoFileName;
	
	public String currentSeqNumber;
	public DTOWorkSpaceMst dtoWsMstInfo;
	public ArrayList<DTOSubmissionInfoDMSDtl> subInfoDMSDtlList;
	
	public Vector<DTOWorkSpaceMst> wsMstList;
	public String userTypeName;
	public String eTMFCustomization;
	public boolean allFileApproved = false;

	@Override
	public String execute() throws Exception {
		//		
		// String[] extentions = {"doc","docx","pdf","odt"};
		//		
		//		
		// added to arrange file as per structure
		// PDFMergeTemp pm=new PDFMergeTemp();
		// pm.Test(workSpaceId);

		// System.out.println("Geeting WID="+workSpaceId);

		// docDetails =
		// docMgmtImpl.getPDFPublishableFilesAfterLastConfirmSeq(workSpaceId);
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		eTMFCustomization = knetProperties.getValue("ETMFCustomization");
		eTMFCustomization = eTMFCustomization.toLowerCase();
		userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		
		ArrayList<String> workspaceIdList = new ArrayList<String>();
		
		workspaceIdList.add(workSpaceId);
		
		//Check All uploaded File Approved Or Not
				int[] nodeIds = null;
				Integer totalLeafCount =  docMgmtImpl.getTotalLeafNodes(workspaceIdList);	
				//ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistory = docMgmtImpl.getAllNodesLastHistory(workspaceIdList, nodeIds);
				ArrayList<DTOWorkSpaceNodeHistory> wsNodeHistory = docMgmtImpl.getAllNodesLastHistoryForPDFPublish(workspaceIdList, nodeIds);
				int leafCount = totalLeafCount;
				int historyCount = wsNodeHistory.size();
				float leafNodesWithDocPer= ((float)historyCount/leafCount)*100;
				float rem = 100.00f - leafNodesWithDocPer; 
				float leafNodesWithDocCnt = leafCount - historyCount;
				HashMap<Integer, Integer> stageMap = new HashMap<Integer, Integer>();
				Vector<DTOStageMst> allStages = docMgmtImpl.getStageDetail();
				
				//fill hashmap with stage count according to unique stage IDs
				for (DTOStageMst dtoStageMst : allStages) {
					int stgCount=0;
					for (int j = 0; j < wsNodeHistory.size(); j++) {				
						if(wsNodeHistory.get(j).getStageId()==dtoStageMst.getStageId())
						{
							stgCount++;	
						}				
					}
					if(dtoStageMst.getStageId() == 100){
						if(historyCount == stgCount){
							System.out.println("Hello");
							allFileApproved = true;
						}
					}
					stageMap.put(dtoStageMst.getStageId(), stgCount);
				}
		
		
		dtoWsMstInfo = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		if(dtoWsMstInfo.getLastPublishedVersion() != null)
		{
			System.out.println(dtoWsMstInfo.getLastPublishedVersion() + "*******dtoWsMstInfo.getLastPublishedVersion()");
			//For current sequence number (copy from publish and submit action.)
			String lastPublishedVersion = dtoWsMstInfo.getLastPublishedVersion(); 		
			if(lastPublishedVersion.equals("-999"))
			{
				currentSeqNumber = "0000";
			}
			else
			{
				int iSeqNo = Integer.parseInt(lastPublishedVersion);
				iSeqNo++;
				String strSeqNo = "000"+iSeqNo;
				currentSeqNumber = strSeqNo.substring(strSeqNo.length()-4);
			}
			ArrayList<DTOWorkSpaceMst> wsMstArrList=docMgmtImpl.getDMSSubmissionInfo(workspaceIdList);
			if(wsMstArrList.size() > 0)
			{
				DTOWorkSpaceMst dtoWsMst = wsMstArrList.get(0);
				DTOSubmissionMst dtoSubMst = dtoWsMst.getSubmissionMst();
				subInfoDMSDtlList = dtoSubMst.getSubmissionInfoDMSDtlsList();
			}
			else
			{
				subInfoDMSDtlList = new ArrayList<DTOSubmissionInfoDMSDtl>();
			}
		}
	
		File baseLogoDir = propertyInfo.getDir("LogoFilePath");
		baseLogoDir = new File(propertyInfo.getValue("LogoFilePath"));

		// ExternalPolicyMst ext=new ExternalPolicyMst();
		// ext.getExternalPolicy();
		logoNameList = new ArrayList<String>();
		if (baseLogoDir != null) {
			String[] fileNames = baseLogoDir.list();
			if (fileNames != null) {
				System.out.println(fileNames.length);
				for (int i = 0; i < fileNames.length; i++) {
					if (fileNames[i].endsWith(".jpg")
							|| fileNames[i].endsWith(".png")
							|| fileNames[i].endsWith(".ico")
							|| fileNames[i].endsWith(".jpeg")
							|| fileNames[i].endsWith(".bmp"))
						logoNameList.add(fileNames[i]);
				}
			}
		}
		DTOPdfPublishDtl dtopdfpublish = docMgmtImpl
				.getPdfPublishDtlById(workSpaceId);
		if (dtopdfpublish != null) {
			version = dtopdfpublish.getVersion();
			if (!dtopdfpublish.getProductname().equals("")) {
				String[] products = dtopdfpublish.getProductname().toString()
						.split("##");
				if (products.length >= 2) {
					productName = products[0];
					productName2 = products[1];
				} else
					productName = products[0];

			}
			coverpage_productname = dtopdfpublish.getCoverpage_productname();
			coverpage_submittedby = dtopdfpublish.getCoverpage_submittedby();
			coverpage_submittedto = dtopdfpublish.getCoverpage_submittedto();
		}
		PdfPublishTreeBean bean = new PdfPublishTreeBean();
		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		int userGroupId = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());
		docDetails = bean
				.generatePdfTree(workSpaceId, userGroupId, userId, 'N');
		
		if(docDetails.size()>0)
		{
			for(int i=0;i<docDetails.size();i++)
			{
				if(!docDetails.get(i).getFileName().equals("No File"))
				{
					flag = true;
					break;
				}
			}
		}

		// System.out.println("Total Files-"+docDetails.size());
		productName=dtoWsMstInfo.getWorkSpaceDesc();
		return SUCCESS;
	}

	// Getter ..Setter
	public Vector<DTOWorkSpaceNodeHistory> getDocDetails() {
		return docDetails;
	}

	public void setDocDetails(Vector<DTOWorkSpaceNodeHistory> docDetails) {
		this.docDetails = docDetails;
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	//
	// public File getUploadLogo() {
	// return uploadLogo;
	// }
	//
	// public void setUploadLogo(File uploadLogo) {
	// this.uploadLogo = uploadLogo;
	// }
	//
	// public String getUploadLogoFileName() {
	// return uploadLogoFileName;
	// }
	//
	// public void setUploadLogoFileName(String uploadLogoFileName) {
	// this.uploadLogoFileName = uploadLogoFileName;
	// }

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

}
