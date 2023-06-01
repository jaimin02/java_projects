package com.docmgmt.struts.actions.labelandpublish;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOInternalLabelMst;
import com.docmgmt.dto.DTOManualModeSeqZipDtl;
import com.docmgmt.dto.DTORegulatoryActivityType;
import com.docmgmt.dto.DTOSequenceDescriptionMst;
import com.docmgmt.dto.DTOSequenceDescriptionMst_CA;
import com.docmgmt.dto.DTOSequenceTypeMst;
import com.docmgmt.dto.DTOSequenceTypeMst_Thai;
import com.docmgmt.dto.DTOSubmissionInfoAUDtl;
import com.docmgmt.dto.DTOSubmissionInfoCADtl;
import com.docmgmt.dto.DTOSubmissionInfoCHDtl;
import com.docmgmt.dto.DTOSubmissionInfoEU14Dtl;
import com.docmgmt.dto.DTOSubmissionInfoEU14SubDtl;
import com.docmgmt.dto.DTOSubmissionInfoEU20Dtl;
import com.docmgmt.dto.DTOSubmissionInfoEU20SubDtl;
import com.docmgmt.dto.DTOSubmissionInfoEUDtl;
import com.docmgmt.dto.DTOSubmissionInfoEUSubDtl;
import com.docmgmt.dto.DTOSubmissionInfoForManualMode;
import com.docmgmt.dto.DTOSubmissionInfoGCCDtl;
import com.docmgmt.dto.DTOSubmissionInfoGCCSubDtl;
import com.docmgmt.dto.DTOSubmissionInfoTHDtl;
import com.docmgmt.dto.DTOSubmissionInfoUS23Dtl;
import com.docmgmt.dto.DTOSubmissionInfoUSDtl;
import com.docmgmt.dto.DTOSubmissionInfoZADtl;
import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.dto.DTOSubmissionTypeMst;
import com.docmgmt.dto.DTOSubmittedWorkspaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkspaceCMSMst;
import com.docmgmt.dto.PublishAttrForm;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.master.ApplicantContactTypeMst;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.services.ZipManager;
import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdError;
import com.docmgmt.struts.actions.ImportProjects.ECTD.EctdXmlReader;
import com.docmgmt.struts.actions.hyperlinks.LinkManager;
import com.docmgmt.struts.actions.labelandpublish.AU.WorkspacePublishService_AU;
import com.docmgmt.struts.actions.labelandpublish.CA.WorkspacePublishService_CA;
import com.docmgmt.struts.actions.labelandpublish.CH.WorkspacePublishService_CH;
import com.docmgmt.struts.actions.labelandpublish.EU14.WorkspacePublishService_EU14;
import com.docmgmt.struts.actions.labelandpublish.EU20.WorkspacePublishService_EU20;
import com.docmgmt.struts.actions.labelandpublish.GCC.WorkspacePublishService_GCC;
import com.docmgmt.struts.actions.labelandpublish.TH.WorkspacePublishService_TH;
import com.docmgmt.struts.actions.labelandpublish.US23.WorkspacePublishServiceUS23;
import com.docmgmt.struts.actions.labelandpublish.ZA.WorkspacePublishService_ZA;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class PublishAndSubmitAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public String editSequence;
	public String workSpaceId;

	public String dos;
	public String client_name;
	public String project_type;
	public String project_name;

	public String extraHtmlCode;

	public Vector<DTOSubmissionMst> workspacesWithSubmissionInfo;
	public Vector<DTOSubmissionTypeMst> getSubmissionTypes;
	public Vector<DTOSequenceTypeMst> getSequenceTypes;
	public Vector<DTOSequenceTypeMst_Thai> getSequenceTypes_Thai;
	public Vector<DTOSequenceDescriptionMst> getSequenceDescriptions;
	
	public Vector<DTORegulatoryActivityType> getRegulatoryActivityType;
	public Vector<DTOSequenceDescriptionMst_CA> getSequenceDescriptions_CA;

	public Vector getApplicantContactTypes;
	public Vector getTelephoneNumberType;
	public Vector getApplicationTypes;
	public Vector getSubmissonSubTypes;
	public Vector getSupplimentEffectiveDateType;

	public Vector getWorkspacePublishInfo;
	public Vector<String> getAllWorkspaceSequences;
	public String lastPublishedVersion;
	public String currentSeqNumber;
	public String subType;
	public String relatedSeqNo = "";
	public String countryRegion;
	public String submissionPath;
	public String subMode;
	public String submissionInfo__DtlId;
	public String lastConfirmedSubmissionPath;
	public Vector filesContainingBrokenLinks;
	public DTOSubmissionMst submissionDetail;
	public ArrayList<DTOWorkspaceCMSMst> lstWorkspaceCMS;
	public String workspaceRMS;
	public char isRMSSelected;
	public int[] selectedCMS;
	public String subDesc;

	/** Added for Manual Mode Publish */
	public char projectPublishType;
	public int[] leafIds;

	/********************************/

	public String dontChkBrknLinks;// valid value :'true'
	public boolean isConfirmLink;

	public ArrayList<EctdError> ectdErrorList;
	public char addTT;
	public char copyRelatedSeq;
	public String regionalDTDVersion;
	public String subVariationMode;
	public String applicationNumber;
	public String highLvlNo;
	public String elecSig;
	public String downloadZipSig;
	public String[] trackCMS;
	public String submissionUnitType;
	
	public String submissionUnitType_eu_301;

	public String[] datatype; // Submission/efficacy
	public String efficacyDescription;
	public String propriateryName;

	public String[] MultipleApplication;
	public String[] GanelicForm;
	public String[] MultiplesubmissionType;
	public String[] MultiplesubmissionTypeZa20;
	public String paragraph13;

	public boolean problemInLoad = false;

	public String[] applicantContactDetails;
	public String subSubType;
	public String supplementEffectiveDateTypeCode;
	public String crossReferenceNumber;
	public String applicationTypeCode;
	
	public String seqTypes;
	public String seqDesc;
	
	public String seqTypes_th;
	public String seqDesc_th;
	public String email;
	public String seqDesc_CA;
	public String regActType;
	public String seqDescFlag;
	
	public String artgNumber;
	public String number;
	public String description;
	public String singledate;
	public String fromdate;
	public String todate;
	public String[] MultipleARTG;
	public String descriptionValue;
	public String prevEUDtdVersion;
	public String manualPrevSeq;
	public boolean recompile = false;
	
	List<String> fileList;
    String OUTPUT_ZIP_FILE = "";
    String SOURCE_FOLDER = "";
    String DownloadFolderPath;
    String sequenceFlag;
    private InputStream inputStreams;
    private String fileName;
    private long contentLength;
    public String htmlContent;
    public String subTypes_za20= "";
	// added to count total node under review,approve...stage

	
	public String execute() throws Exception {
		
		System.out.println("execute start");
		int userId = Integer.parseInt(ActionContext.getContext().getSession()
			.get("userid").toString());
	String defaultWorkSpaceId = ActionContext.getContext().getSession()
			.get("defaultWorkspace").toString();
	int userGroupCode = Integer.parseInt(ActionContext.getContext()
			.getSession().get("usergroupcode").toString());

	if (defaultWorkSpaceId != null && !defaultWorkSpaceId.equals("")) {
		workSpaceId = defaultWorkSpaceId;

	}
	Vector<DTOWorkSpaceMst> getAllWorkspace = docMgmtImpl.getUserWorkspace(
			userGroupCode, userId);
	//System.out.println("getAllWorkspace:"+getAllWorkspace.size());
	Vector<DTOSubmissionMst> wsWithSubInfo = docMgmtImpl
			.getAllWorkspaceSubmissionInfo(userId);
	workspacesWithSubmissionInfo = new Vector<DTOSubmissionMst>();

	for (int i = 0; i < getAllWorkspace.size(); i++) {
		DTOWorkSpaceMst dtoAllWorkSpaceMst = (DTOWorkSpaceMst) getAllWorkspace
				.get(i);
		for (int k = 0; k < wsWithSubInfo.size(); k++) {
			DTOSubmissionMst dtoSubWorkspaceMst = (DTOSubmissionMst) wsWithSubInfo
					.get(k);
			if (dtoAllWorkSpaceMst.getWorkSpaceId().equals(
					dtoSubWorkspaceMst.getWorkspaceId())) {
				workspacesWithSubmissionInfo.add(dtoSubWorkspaceMst);
			}
		}
	}
	problemInLoad = false;
	System.out.println("workspacesWithSubmissionInfo:"+workspacesWithSubmissionInfo.size());
	System.out.println("execute end");
	return SUCCESS;
	
	}

	public String getSubmissionDtl() throws Exception {
	
		if (workSpaceId == null || workSpaceId.trim().equals("") || workSpaceId.equals("-1")) 
		{
			return SUCCESS;
		}
		
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		elecSig = knetProperties.getValue("ElectronicSignature_ConfirmSeq");
		downloadZipSig=knetProperties.getValue("DownloadeCTDDossier");
		DTOWorkSpaceMst wsdto = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		client_name = wsdto.getClientName();
		project_type = wsdto.getProjectName();
		project_name = wsdto.getWorkSpaceDesc();
		lastPublishedVersion = wsdto.getLastPublishedVersion();
		projectPublishType = wsdto.getProjectType();

		DTOSubmissionMst dtoSubmissionMst = docMgmtImpl
				.getSubmissionInfo(workSpaceId);
		DTOSubmissionMst prevSubmissionDtl = docMgmtImpl.getSubmissionInfoEURegion(workSpaceId);
		regionalDTDVersion = dtoSubmissionMst.getRegionalDTDVersion();
		prevEUDtdVersion=prevSubmissionDtl.getEUDtdVersion();
		
		if(wsdto.getProjectType() == 'M'){
			
			String status=docMgmtImpl.CheckConfrimSequenceDtl(workSpaceId);
			System.out.println("status"+status);
			if(status.equalsIgnoreCase("Yes")){
				editSequence="No";
			}else{
				editSequence="Yes";
			}
			String manualPrevSeqPath = PropertyInfo.getPropInfo()
			.getValue("ManualProjectsServerPath");
			String FosunChanges=PropertyInfo.getPropInfo().getValue("FosunCustomization");
			if(FosunChanges.equalsIgnoreCase("yes")){
				manualPrevSeqPath=manualPrevSeqPath +"/"+ client_name;
			}
			String prevSequencePath = manualPrevSeqPath + "/" + workSpaceId;
			File seqPath = new File(prevSequencePath);
			manualPrevSeq="";
			if (!seqPath.isDirectory()){
				seqPath.mkdir();
			}
			manualPrevSeq=prevSequencePath;
			manualPrevSeq=manualPrevSeq.replace('/', '\\');

			System.out.println("Manual Project Previous Sequence Check:"+manualPrevSeq);
		}

		if (wsdto.getProjectType() == 'P' || wsdto.getProjectType() == 'M') {

			if (lastPublishedVersion.equals("-999")) {
				currentSeqNumber = "0000";
			} else {
				int iSeqNo = Integer.parseInt(lastPublishedVersion);
				iSeqNo++;
				String strSeqNo = "000" + iSeqNo;
				currentSeqNumber = strSeqNo.substring(strSeqNo.length() - 4);
			}

			getSubmissionTypes = docMgmtImpl
					.getRegionWiseSubmissionTypes(dtoSubmissionMst
							.getCountryRegion(),regionalDTDVersion);
			
			
			getAllWorkspaceSequences = new Vector<String>();
			applicationNumber = dtoSubmissionMst.getApplicationNo();

			
			if (dtoSubmissionMst.getCountryRegion().equals("us")) {

				if (regionalDTDVersion.equalsIgnoreCase("23")) {

					getApplicantContactTypes = docMgmtImpl
							.getApplicantContactType();
					getTelephoneNumberType = docMgmtImpl
							.getTelephoneNumberType();
					getApplicationTypes = docMgmtImpl.getApplicationType();
					getSubmissonSubTypes = docMgmtImpl.getSubmissionSubType();
					getSupplimentEffectiveDateType = docMgmtImpl
							.getSupplimentEffectiveDateType();

					getWorkspacePublishInfo = docMgmtImpl
							.getWorkspaceSubmissionInfoUS23Dtl(workSpaceId);

					Iterator<DTOSubmissionTypeMst> iter = getSubmissionTypes
							.iterator();
					
					while (iter.hasNext()) {
						DTOSubmissionTypeMst subType1 = iter.next();
						if (subType1.getSubmissionTypeCode() == null
								|| subType1.getSubmissionTypeCode().equals("")) {
							iter.remove();
						}
					}

					for (int i = 0; i < getWorkspacePublishInfo.size(); i++) {
						DTOSubmissionInfoUS23Dtl dto = (DTOSubmissionInfoUS23Dtl) getWorkspacePublishInfo
								.get(i);

						// get all Previous Sequences
						if (!getAllWorkspaceSequences.contains(dto
								.getLastPublishedVersion())
								&& !dto.getLastPublishedVersion()
										.equals("-999"))
							getAllWorkspaceSequences.addElement(dto
									.getLastPublishedVersion());

						// get latest Confirmed Submission Path for copying
						// previous
						// sequences' directories
						if (dto.getConfirm() == 'Y') {
							lastConfirmedSubmissionPath = dto
									.getSubmissionPath();
						}
					}

				} else {
					getWorkspacePublishInfo = docMgmtImpl
							.getWorkspaceSubmissionInfoUSDtl(workSpaceId);

					for (int i = 0; i < getWorkspacePublishInfo.size(); i++) {
						DTOSubmissionInfoUSDtl dto = (DTOSubmissionInfoUSDtl) getWorkspacePublishInfo
								.get(i);

						// get all Previous Sequences
						if (!getAllWorkspaceSequences.contains(dto
								.getLastPublishedVersion())
								&& !dto.getLastPublishedVersion()
										.equals("-999"))
							getAllWorkspaceSequences.addElement(dto
									.getLastPublishedVersion());

						// get latest Confirmed Submission Path for copying
						// previous
						// sequences' directories
						if (dto.getConfirm() == 'Y') {
							lastConfirmedSubmissionPath = dto
									.getSubmissionPath();
						}
					}
				}

			}
			else if (dtoSubmissionMst.getCountryRegion().equals("au")) {
				
				getSequenceTypes=docMgmtImpl.getSequenceTypeDetail();
				getSequenceDescriptions=docMgmtImpl.getSequenceDescriptionDetail();
				
				getWorkspacePublishInfo = docMgmtImpl
				.getWorkspaceSubmissionInfoAUDtl(workSpaceId);

				System.out.println("Size of getWorkspacePublishInfo::::::"+getWorkspacePublishInfo.size());
				applicationNumber = dtoSubmissionMst.geteSubmissionId();
				

		for (int i = 0; i < getWorkspacePublishInfo.size(); i++) {
			DTOSubmissionInfoAUDtl dtoAUDtl = (DTOSubmissionInfoAUDtl) getWorkspacePublishInfo
					.get(i);

			// get all Previous Sequences
			if (!getAllWorkspaceSequences.contains(dtoAUDtl
					.getLastPublishedVersion())
					&& !dtoAUDtl.getLastPublishedVersion().equals(
							"-999"))
				getAllWorkspaceSequences.addElement(dtoAUDtl
						.getLastPublishedVersion());

			// get latest Confirmed Submission Path for copying previous
			// sequences' directories
			if (dtoAUDtl.getConfirm() == 'Y') {
				lastConfirmedSubmissionPath = dtoAUDtl
						.getSubmissionPath();
			}
		}
				
			/*	for(int i=0;i<getSequenceTypes.size();i++){
					
					System.out.println("Sequence type is::::"+ getSequenceTypes.get(i).getSequenceTypeDescription());
				}*/
				
			}
			else if (dtoSubmissionMst.getCountryRegion().equals("th")) {
				
				getSequenceTypes_Thai=docMgmtImpl.getSequenceTypeDetail_Thai();
				
				getWorkspacePublishInfo = docMgmtImpl
				.getWorkspaceSubmissionInfoTHDtl(workSpaceId);

				System.out.println("Size of getWorkspacePublishInfo::::::"+getWorkspacePublishInfo.size());
				applicationNumber = dtoSubmissionMst.geteSubmissionId();
				

		for (int i = 0; i < getWorkspacePublishInfo.size(); i++) {
			DTOSubmissionInfoTHDtl dtoTHDtl = (DTOSubmissionInfoTHDtl) getWorkspacePublishInfo
					.get(i);

			// get all Previous Sequences
			if (!getAllWorkspaceSequences.contains(dtoTHDtl
					.getLastPublishedVersion())
					&& !dtoTHDtl.getLastPublishedVersion().equals(
							"-999"))
				getAllWorkspaceSequences.addElement(dtoTHDtl
						.getLastPublishedVersion());

			// get latest Confirmed Submission Path for copying previous
			// sequences' directories
			if (dtoTHDtl.getConfirm() == 'Y') {
				lastConfirmedSubmissionPath = dtoTHDtl
						.getSubmissionPath();
			}
		}
				
			/*	for(int i=0;i<getSequenceTypes.size();i++){
					
					System.out.println("Sequence type is::::"+ getSequenceTypes.get(i).getSequenceTypeDescription());
				}*/
				
			}
			
			else if (dtoSubmissionMst.getCountryRegion().equals("eu")) {
				// EU v1.3
				
				
				if (prevSubmissionDtl.getEUDtdVersion().equals("13") ) {
					getWorkspacePublishInfo = docMgmtImpl
							.getWorkspaceSubmissionInfoEUDtl(workSpaceId);

					for (int i = 0; i < getWorkspacePublishInfo.size(); i++) {
						DTOSubmissionInfoEUDtl dto = (DTOSubmissionInfoEUDtl) getWorkspacePublishInfo
								.get(i);

						// get all Previous Sequences
						if (!getAllWorkspaceSequences.contains(dto
								.getLastPublishedVersion())
								&& !dto.getLastPublishedVersion()
										.equals("-999"))
							getAllWorkspaceSequences.addElement(dto
									.getLastPublishedVersion());

						// get latest Confirmed Submission Path for copying
						// previous sequences' directories
						if (dto.getConfirm() == 'Y') {
							lastConfirmedSubmissionPath = dto
									.getSubmissionPath();
						}
					}
					if(prevSubmissionDtl.getRegionalDTDVersion().equals("301") && prevSubmissionDtl.getEUDtdVersion().equals("13") && !currentSeqNumber.equalsIgnoreCase("0000")){
						getAllWorkspaceSequences.addElement(currentSeqNumber);
					}
				}
				// EU v1.4
				else if (prevSubmissionDtl.getEUDtdVersion().equals("14")) {
					getWorkspacePublishInfo = docMgmtImpl
							.getWorkspaceSubmissionInfoEU14Dtl(workSpaceId);
					// Only for EU v1.4
					applicationNumber = dtoSubmissionMst.getTrackingNo();
					highLvlNo = dtoSubmissionMst.getHighLvlNo();

					for (int i = 0; i < getWorkspacePublishInfo.size(); i++) {
						DTOSubmissionInfoEU14Dtl dtoEU14Dtl = (DTOSubmissionInfoEU14Dtl) getWorkspacePublishInfo
								.get(i);

						// get all Previous Sequences
						if (!getAllWorkspaceSequences.contains(dtoEU14Dtl
								.getLastPublishedVersion())
								&& !dtoEU14Dtl.getLastPublishedVersion()
										.equals("-999"))
							getAllWorkspaceSequences.addElement(dtoEU14Dtl
									.getLastPublishedVersion());

						// get latest Confirmed Submission Path for copying
						// previous sequences' directories
						if (dtoEU14Dtl.getConfirm() == 'Y') {
							lastConfirmedSubmissionPath = dtoEU14Dtl
									.getSubmissionPath();
						}
					}
					if(prevSubmissionDtl.getRegionalDTDVersion().equals("301") && prevSubmissionDtl.getEUDtdVersion().equals("14") && !currentSeqNumber.equalsIgnoreCase("0000")){
						getAllWorkspaceSequences.addElement(currentSeqNumber);
					}
				}

				// EU v2.0
				else if (prevSubmissionDtl.getEUDtdVersion().equals("20") ) {
					getWorkspacePublishInfo = docMgmtImpl
							.getWorkspaceSubmissionInfoEU20Dtl(workSpaceId);
					// Only for EU v2.0
					applicationNumber = dtoSubmissionMst.getTrackingNo();
					highLvlNo = dtoSubmissionMst.getHighLvlNo();

					for (int i = 0; i < getWorkspacePublishInfo.size(); i++) {
						DTOSubmissionInfoEU20Dtl dtoEU20Dtl = (DTOSubmissionInfoEU20Dtl) getWorkspacePublishInfo
								.get(i);

						// get all Previous Sequences
						if (!getAllWorkspaceSequences.contains(dtoEU20Dtl
								.getLastPublishedVersion())
								&& !dtoEU20Dtl.getLastPublishedVersion()
										.equals("-999"))
							getAllWorkspaceSequences.addElement(dtoEU20Dtl
									.getLastPublishedVersion());
						

						// get latest Confirmed Submission Path for copying
						// previous sequences' directories
						if (dtoEU20Dtl.getConfirm() == 'Y') {
							lastConfirmedSubmissionPath = dtoEU20Dtl
									.getSubmissionPath();
						}
					}
					if(prevSubmissionDtl.getRegionalDTDVersion().equals("301") && prevSubmissionDtl.getEUDtdVersion().equals("20") && !currentSeqNumber.equalsIgnoreCase("0000")){
						getAllWorkspaceSequences.addElement(currentSeqNumber);
					}
				}

				/** code for RMS/CMS /**EU specific code **/
				if (dtoSubmissionMst.getProcedureType().equalsIgnoreCase(
						"decentralised")
						|| dtoSubmissionMst.getProcedureType()
								.equalsIgnoreCase("mutual-recognition")
						|| dtoSubmissionMst.getProcedureType()
								.equalsIgnoreCase("national")) {

					workspaceRMS = dtoSubmissionMst.getCountryName();

					lstWorkspaceCMS = docMgmtImpl
							.getWorkspaceCMSInfo(dtoSubmissionMst
									.getWorkspaceId());
				}
			} else if (dtoSubmissionMst.getCountryRegion().equals("ca")) {
				
				System.out.println("Canada View Call");
				getRegulatoryActivityType=docMgmtImpl.getRegulatoryActivityTypeDetail();
				getSequenceDescriptions_CA=docMgmtImpl.getSequenceDescriptionDetail_CA();
				getWorkspacePublishInfo = DocMgmtImpl
						.getWorkspaceSubmissionInfoCADtl(workSpaceId);

				applicationNumber=dtoSubmissionMst.getDossierIdentifier();
				System.out.println("applicationNumber->"+applicationNumber);
				for (int i = 0; i < getWorkspacePublishInfo.size(); i++) {
					DTOSubmissionInfoCADtl dto = (DTOSubmissionInfoCADtl) getWorkspacePublishInfo
							.get(i);

					// get all Previous Sequences
					if (!getAllWorkspaceSequences.contains(dto
							.getLastPublishedVersion())
							&& !dto.getLastPublishedVersion().equals("-999"))
						getAllWorkspaceSequences.addElement(dto
								.getLastPublishedVersion());

					// get latest Confirmed Submission Path for copying previous
					// sequences' directories
					if (dto.getConfirm() == 'Y') {
						lastConfirmedSubmissionPath = dto.getSubmissionPath();
					}
				}
			} else if (dtoSubmissionMst.getCountryRegion().equals("gcc")) {

				getWorkspacePublishInfo = docMgmtImpl
						.getWorkspaceSubmissionInfoGCCDtl(workSpaceId);

				applicationNumber = dtoSubmissionMst.getTrackingNo();
				highLvlNo = dtoSubmissionMst.getHighLvlNo();

				for (int i = 0; i < getWorkspacePublishInfo.size(); i++) {
					DTOSubmissionInfoGCCDtl dtoGCCDtl = (DTOSubmissionInfoGCCDtl) getWorkspacePublishInfo
							.get(i);

					// get all Previous Sequences
					if (!getAllWorkspaceSequences.contains(dtoGCCDtl
							.getLastPublishedVersion())
							&& !dtoGCCDtl.getLastPublishedVersion().equals(
									"-999"))
						getAllWorkspaceSequences.addElement(dtoGCCDtl
								.getLastPublishedVersion());

					// get latest Confirmed Submission Path for copying previous
					// sequences' directories
					if (dtoGCCDtl.getConfirm() == 'Y') {
						lastConfirmedSubmissionPath = dtoGCCDtl
								.getSubmissionPath();
					}
				}
				/** code for RMS/CMS /**EU specific code **/
				if (dtoSubmissionMst.getProcedureType().equalsIgnoreCase("gcc")) {

					workspaceRMS = dtoSubmissionMst.getCountryName();

					lstWorkspaceCMS = docMgmtImpl
							.getWorkspaceCMSInfo(dtoSubmissionMst
									.getWorkspaceId());
				}

			} else if (dtoSubmissionMst.getCountryRegion().equals("za")) {

				getWorkspacePublishInfo = docMgmtImpl
						.getWorkspaceSubmissionInfoZADtl(workSpaceId);

				applicationNumber = dtoSubmissionMst.getApplicationNo();
				highLvlNo = dtoSubmissionMst.getHighLvlNo();

				for (int i = 0; i < getWorkspacePublishInfo.size(); i++) {
					DTOSubmissionInfoZADtl dtoZADtl = (DTOSubmissionInfoZADtl) getWorkspacePublishInfo
							.get(i);

					// get all Previous Sequences
					if (!getAllWorkspaceSequences.contains(dtoZADtl
							.getLastPublishedVersion())
							&& !dtoZADtl.getLastPublishedVersion().equals(
									"-999"))
						getAllWorkspaceSequences.addElement(dtoZADtl
								.getLastPublishedVersion());

					// get latest Confirmed Submission Path for copying previous
					// sequences' directories
					if (dtoZADtl.getConfirm() == 'Y') {
						lastConfirmedSubmissionPath = dtoZADtl
								.getSubmissionPath();
					}
				}

			} else if (dtoSubmissionMst.getCountryRegion().equals("ch")) {

				getWorkspacePublishInfo = docMgmtImpl
						.getWorkspaceSubmissionInfoCHDtl(workSpaceId);

				applicationNumber = dtoSubmissionMst.getApplicationNo();

				for (int i = 0; i < getWorkspacePublishInfo.size(); i++) {
					DTOSubmissionInfoCHDtl dtoCHDtl = (DTOSubmissionInfoCHDtl) getWorkspacePublishInfo
							.get(i);

					// get all Previous Sequences
					if (!getAllWorkspaceSequences.contains(dtoCHDtl
							.getLastPublishedVersion())
							&& !dtoCHDtl.getLastPublishedVersion().equals(
									"-999"))
						getAllWorkspaceSequences.addElement(dtoCHDtl
								.getLastPublishedVersion());

					// get latest Confirmed Submission Path for copying previous
					// sequences' directories
					if (dtoCHDtl.getConfirm() == 'Y') {
						lastConfirmedSubmissionPath = dtoCHDtl
								.getSubmissionPath();
					}
				}

			}

			else {
				getWorkspacePublishInfo = new Vector();
			}
			Calendar cal = GregorianCalendar.getInstance();
			java.util.Date now = cal.getTime();
			DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
			dos = formater.format(now);
			
			

			countryRegion = dtoSubmissionMst.getCountryRegion();

			System.out.println("Country Region=" + countryRegion);
			/*
			 * If project's Type is 'eCTD (Manual Mode)' (wsdto.getProjectType()
			 * == 'M') then it will allow the user to publish new sequence
			 * manually.
			 */

			/*if (wsdto.getProjectType() == 'M') {
				
				return "manualMode";
			}*/

		}

		/*
		 * If project's Type is 'Document Management' then it will create HTML
		 * publish for non-eCTD projects..
		 */
		else if (wsdto.getProjectType() == 'E') {

			return "htmlPublish";
		}
		System.out.println(wsdto.getProjectType());
		//System.out.println("size of getWorkspacePublishInfo last:::::::"+getWorkspacePublishInfo.size());
		
		return SUCCESS;
	}

	public String saveSubmissionDtl() throws Exception {
		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		/*
		 * HttpServletRequest request1 = ServletActionContext.getRequest();
		 * 
		 * Enumeration parameters = request1.getParameterNames(); for (int count
		 * = 0;parameters.hasMoreElements();) {
		 * System.out.println(++count+"))"); Object paramObj =
		 * parameters.nextElement(); if (paramObj instanceof String) { String
		 * param = (String) paramObj;
		 * System.out.println("--------------------ParamName::"+param
		 * +">>>ParamVal::"+request1.getParameter(param)); } else{
		 * System.out.println("NonStringParam"+paramObj.getClass().getName()); }
		 * }
		 */
		/** Added By Ashmak Shah For Manual Mode */
		if (projectPublishType == 'M') {

			problemInLoad = true; // its Manual Mode Publish thats why we have
			// to
			// get all the required // fields from the session.
			Map<String, Object> manualModeInfo = (Map<String, Object>) ActionContext
					.getContext().getSession().get("manualModeInfo");

			subType = (String) manualModeInfo.get("subType");
			workSpaceId = (String) manualModeInfo.get("workSpaceId");
			currentSeqNumber = (String) manualModeInfo.get("currentSeqNumber");
			lastPublishedVersion = (String) manualModeInfo
					.get("lastPublishedVersion");
			dos = (String) manualModeInfo.get("dos");
			relatedSeqNo = (String) manualModeInfo.get("relatedSeqNo");
			subMode = (String) manualModeInfo.get("subMode");

			isRMSSelected = manualModeInfo.get("isRMSSelected").toString()
					.charAt(0);

			selectedCMS = (int[]) manualModeInfo.get("selectedCMS");
			subDesc = (String) manualModeInfo.get("subDesc");
			lastConfirmedSubmissionPath = (String) manualModeInfo
					.get("lastConfirmedSubmissionPath");

			addTT = (Character) manualModeInfo.get("addTT");
			copyRelatedSeq = (Character) manualModeInfo.get("copyRelatedSeq");
			subVariationMode = (String) manualModeInfo.get("subVariationMode");
			applicationNumber = (String) manualModeInfo
					.get("applicationNumber");
			trackCMS = (String[]) manualModeInfo.get("trackCMS");
			seqDesc_CA = (String) manualModeInfo.get("seqDesc_CA");
			regActType = (String) manualModeInfo.get("regActType");
			description = (String) manualModeInfo.get("description");
			singledate = (String) manualModeInfo.get("singledate");
			fromdate = (String) manualModeInfo.get("fromdate");
			todate = (String) manualModeInfo.get("todate");
			number = (String) manualModeInfo.get("number");
			MultipleARTG = (String[]) manualModeInfo.get("MultipleARTG");
			seqTypes = (String) manualModeInfo.get("seqTypes");
			seqDesc = (String) manualModeInfo.get("seqDesc");
			seqTypes_th = (String) manualModeInfo.get("seqTypes_th");
			seqDesc_th = (String) manualModeInfo.get("seqDesc_th");
			
			datatype = (String[]) manualModeInfo.get("datatype");
			//efficacyDescription = (String) manualModeInfo.get("seqDesc_th");
			//propriateryName = (String) manualModeInfo.get("seqDesc_th");
			efficacyDescription = (String) manualModeInfo.get("efficacyDescription");
			propriateryName = (String) manualModeInfo.get("propriateryName");
			
			MultipleApplication = (String[]) manualModeInfo
					.get("MultipleApplication");
			GanelicForm = (String[]) manualModeInfo.get("GanelicForm");
			MultiplesubmissionType = (String[]) manualModeInfo.get("MultiplesubmissionType");
			//paragraph13 = (String) manualModeInfo.get("seqDesc_th");
			paragraph13 = (String) manualModeInfo.get("paragraph13");
			
			//paragraph13Ch=(String) manualModeInfo.get("paragraph13");	
			applicantContactDetails = (String[]) manualModeInfo
					.get("applicantContactDetails");
			subSubType = (String) manualModeInfo.get("subSubType");
			supplementEffectiveDateTypeCode = (String) manualModeInfo
					.get("supplementEffectiveDateTypeCode");
			crossReferenceNumber = (String) manualModeInfo
					.get("crossReferenceNumber");
			applicationTypeCode = (String) manualModeInfo
					.get("applicationTypeCode");
			submissionUnitType_eu_301=(String) manualModeInfo
			.get("submissionUnitType_eu_301");
			submissionUnitType=(String) manualModeInfo
			.get("submissionUnitType");
			email=(String) manualModeInfo
			.get("email");
			MultiplesubmissionTypeZa20=(String[]) manualModeInfo.get("MultiplesubmissionTypeZa20");
			if (currentSeqNumber.equals("0000")) {
				lastPublishedVersion = "-999";
			} else {
				int iSeqNo = Integer.parseInt(currentSeqNumber);
				iSeqNo--;
				String strSeqNo = "000" + iSeqNo;
				lastPublishedVersion = strSeqNo
						.substring(strSeqNo.length() - 4);
			}

			docMgmtImpl.UpdateLastPublishVersion(workSpaceId,
					lastPublishedVersion);

			ActionContext.getContext().getSession().remove("manualModeInfo");

			if (leafIds == null || leafIds.length == 0) {
				addActionError("No Files Found To Publish Sequence "
						+ currentSeqNumber + ".");

				return "save";
			}
			/*
			 * IMP In case of Manual Mode, 1) leafIds will be fetched from
			 * member variable 'leafIds'. Which is directly used in
			 * publishWorkspaceSubmission() method in this class. 2) RefID and
			 * RefSeqNo are fetched from the request object. (See at Line 956
			 * (HttpServletRequest request=ServletActionContext.getRequest();))
			 */
			/* Rest of the code is at the end of the function */
			// 

		}		
		try
        {
			if(subType==null){
				subType="";
			}
			
			if (subType.equals("-1") && seqTypes.equals("-1") ) 
			{
				return "save";
			}
          
        }catch(NullPointerException e)
        {
             System.out.println(e.getStackTrace());
        }
		
		
		// System.out.println("currentSeqNumber:::"+currentSeqNumber);
		DTOSubmissionMst dtoSubmissionMst = docMgmtImpl
				.getSubmissionInfo(workSpaceId);
		String[] submissionType = subType.split(",");// subType=submissionType,submissionTypeIndi
		String submissionTypeZa="";
		String submissionId = "";
		// For current publishing sequence
		dtoSubmissionMst.setApplicationNo(applicationNumber);// For EU v1.3,za
		// US,ZA,CH
		// & CA
		dtoSubmissionMst.setTrackingNo(applicationNumber);// For EU v1.4 //gcc
		regionalDTDVersion = dtoSubmissionMst.getRegionalDTDVersion();
		dtoSubmissionMst.seteSubmissionId(applicationNumber); //for AU-TGA
		/********************************************************************************************************************************************/
		if (dtoSubmissionMst.getCountryRegion().equals("us")) {
			// Creating label for workspace with PublishType 'P' only
			DTOInternalLabelMst newLabel = null;
			String newLabelId = null;
			/*if (projectPublishType == 'M') {
				newLabel = new DTOInternalLabelMst();
				newLabel.setLabelId("L0000");
				newLabel.setLabelNo(0);
				newLabelId = newLabel.getLabelId();
			} else {
				newLabel = createWorkspaceLabel(workSpaceId, userId);
				newLabelId = newLabel.getLabelId();
			}*/
			newLabel = createWorkspaceLabel(workSpaceId, userId);
			newLabelId = newLabel.getLabelId();
			
			String relatedSeqs = relatedSeqNo.replaceAll(" ", "");
			if (currentSeqNumber.equals("0000")) {
				lastPublishedVersion = "-999";
			} else {
				int iSeqNo = Integer.parseInt(currentSeqNumber);
				iSeqNo--;
				String strSeqNo = "000" + iSeqNo;
				lastPublishedVersion = strSeqNo.substring(strSeqNo
						.length() - 4);
			}
			
			if (dtoSubmissionMst.getRegionalDTDVersion().equals("23")) {

				String applicantContactList = "";
				for (int i = 0; i < applicantContactDetails.length; i++)
					applicantContactList += "$" + applicantContactDetails[i];

				DTOSubmissionInfoUS23Dtl submissionDtl = new DTOSubmissionInfoUS23Dtl();
				//submissionDtl.setApplicantContactList(applicantContactList);
				submissionDtl.setApplicantContactList(applicantContactList.substring(1));
				submissionDtl.setWorkspaceId(workSpaceId);
				submissionDtl.setCountryCode(dtoSubmissionMst.getCountrycode());
				submissionDtl.setCurrentSeqNumber(currentSeqNumber);
				submissionDtl.setLastPublishedVersion(lastPublishedVersion);
				submissionDtl.setSubmissionPath(dtoSubmissionMst
						.getApplicationNo());// Setting

				submissionDtl.setSubSubType(subSubType);

				// only
				// app
				// no
				// for
				// path
				submissionDtl.setSubmitedBy(userId);
				submissionDtl.setSubmissionType(submissionType[0]);

				try {
					DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date parsedUtilDate = formater.parse(dos);
					java.sql.Timestamp sqltDate = new java.sql.Timestamp(
							parsedUtilDate.getTime());

					submissionDtl.setDateOfSubmission(sqltDate);
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}

				submissionDtl.setRelatedSeqNo(relatedSeqs);
				submissionDtl.setConfirm('N');
				submissionDtl.setModifyBy(userId);
				submissionDtl.setLabelId(newLabelId);
				submissionDtl.setSubmissionMode(subMode);
				submissionDtl.setApplicationNo(applicationNumber);

				submissionDtl
						.setSuppEffectiveDateType(supplementEffectiveDateTypeCode
								.equals("-1") ? ""
								: supplementEffectiveDateTypeCode);
				submissionDtl.setCrossRefAppType(applicationTypeCode
						.equals("-1") ? "" : applicationTypeCode);
				submissionDtl.setCrossReferenceNumber(crossReferenceNumber);

				submissionId = docMgmtImpl.insertSubmissionInfoUS23Dtl(
						submissionDtl, 1);// Insert MODE
				// System.out.println("submissionId"+submissionId);

				// Publishing Workspace
				DTOSubmissionInfoUS23Dtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoUS23DtlBySubmissionId(submissionId);
				submissionPath = dto.getSubmissionPath();

			} else {
				DTOSubmissionInfoUSDtl submissionDtl = new DTOSubmissionInfoUSDtl();

				submissionDtl.setWorkspaceId(workSpaceId);
				submissionDtl.setCountryCode(dtoSubmissionMst.getCountrycode());
				submissionDtl.setCurrentSeqNumber(currentSeqNumber);
				submissionDtl.setLastPublishedVersion(lastPublishedVersion);
				submissionDtl.setSubmissionPath(dtoSubmissionMst
						.getApplicationNo());// Setting
				// only
				// app
				// no
				// for
				// path
				submissionDtl.setSubmitedBy(userId);
				submissionDtl.setSubmissionType(submissionType[0]);

				try {
					DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date parsedUtilDate = formater.parse(dos);
					java.sql.Timestamp sqltDate = new java.sql.Timestamp(
							parsedUtilDate.getTime());

					submissionDtl.setDateOfSubmission(sqltDate);
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}

				submissionDtl.setRelatedSeqNo(relatedSeqs);
				submissionDtl.setConfirm('N');
				submissionDtl.setModifyBy(userId);
				submissionDtl.setLabelId(newLabelId);
				submissionDtl.setSubmissionMode(subMode);
				submissionDtl.setApplicationNo(applicationNumber);
				submissionId = docMgmtImpl.insertSubmissionInfoUSDtl(
						submissionDtl, 1);// Insert MODE
				// System.out.println("submissionId"+submissionId);

				// Publishing Workspace
				DTOSubmissionInfoUSDtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoUSDtlBySubmissionId(submissionId);

				submissionPath = dto.getSubmissionPath();

			}
			docMgmtImpl.updatePublishedVersionForNeeS(workSpaceId,
					lastPublishedVersion);
			// Insert into submissionInfoUSMst

			publishWorkspaceSubmission(dtoSubmissionMst, newLabel,
					currentSeqNumber, relatedSeqs, submissionPath,
					lastConfirmedSubmissionPath, submissionType[0]);

			addActionMessage("Project Published Successfully...");

		}
		/*******************************************************************************************************************************/
		else if (dtoSubmissionMst.getCountryRegion().equals("eu")) {
			
			DTOSubmissionMst prevSubmissionDtl = docMgmtImpl.getSubmissionInfoEURegion(workSpaceId);
			// converting 'dos'(DateOfSubmission) from String to Timestamp..
			java.sql.Timestamp sqltDate = null;
			try {
				DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date parsedUtilDate = formater.parse(dos);

				sqltDate = new java.sql.Timestamp(parsedUtilDate.getTime());

			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}

			// Creating label for workspace with PublishType 'P' only
			DTOInternalLabelMst newLabel = null;
			String newLabelId = null;
			/*if (projectPublishType == 'M') {
				newLabel = new DTOInternalLabelMst();
				newLabel.setLabelId("L0000");
				newLabel.setLabelNo(0);
				newLabelId = newLabel.getLabelId();
			} else {
				newLabel = createWorkspaceLabel(workSpaceId, userId);
				newLabelId = newLabel.getLabelId();
			}*/
			newLabel = createWorkspaceLabel(workSpaceId, userId);
			newLabelId = newLabel.getLabelId();

			String relatedSeqs = relatedSeqNo.replaceAll(" ", "");
			// EU v1.3
			if (prevSubmissionDtl.getEUDtdVersion().equals("13")) {
				DTOSubmissionInfoEUDtl submissionDtl = new DTOSubmissionInfoEUDtl();
				
				submissionDtl.setWorkspaceId(workSpaceId);
				submissionDtl.setCountryCode(dtoSubmissionMst.getCountrycode());
				submissionDtl.setCurrentSeqNumber(currentSeqNumber);
				submissionDtl.setLastPublishedVersion(lastPublishedVersion);
				submissionDtl.setSubmissionPath(dtoSubmissionMst
						.getWorkspaceDesc());// Setting only workspace name for
				// path; Not app no.
				submissionDtl.setSubmitedBy(userId);
				submissionDtl.setSubmissionType(submissionType[0]);
				submissionDtl.setDateOfSubmission(sqltDate);
				submissionDtl.setRelatedSeqNo(relatedSeqs);
				submissionDtl.setConfirm('N');
				submissionDtl.setModifyBy(userId);
				submissionDtl.setLabelId(newLabelId);
				submissionDtl.setSubmissionMode(subMode);
				if (subVariationMode.equals("-1")) {
					subVariationMode = "";
				}
				submissionDtl.setSubVariationMode(subVariationMode);
				submissionDtl.setSubmissionUnitType(submissionUnitType_eu_301);
				submissionDtl.setApplicationNo(applicationNumber);
				if (isRMSSelected == 'N') {// For DCP and MRP
					submissionDtl.setRMSSubmited('N');
				} else {
					isRMSSelected = 'Y';// For NP,CP ,DCP and MRP
					submissionDtl.setRMSSubmited('Y');
				}
				submissionId = docMgmtImpl.insertSubmissionInfoEUDtl(
						submissionDtl, 1);// Insert MODE
			}
			// EU v1.4
			else if (prevSubmissionDtl.getEUDtdVersion().equals("14")) {
				DTOSubmissionInfoEU14Dtl submissionDtl = new DTOSubmissionInfoEU14Dtl();
				submissionDtl.setWorkspaceId(workSpaceId);
				submissionDtl.setCountryCode(dtoSubmissionMst.getCountrycode());
				submissionDtl.setCurrentSeqNumber(currentSeqNumber);
				submissionDtl.setLastPublishedVersion(lastPublishedVersion);

				submissionDtl.setSubmissionPath(dtoSubmissionMst
						.getWorkspaceDesc());// Setting only workspace name for
				// path; Not app no.

				submissionDtl.setSubmitedBy(userId);
				submissionDtl.setSubmissionType(submissionType[0]);
				submissionDtl.setDateOfSubmission(sqltDate);
				submissionDtl.setRelatedSeqNo(relatedSeqs);
				submissionDtl.setConfirm('N');
				submissionDtl.setModifyBy(userId);
				submissionDtl.setLabelId(newLabelId);
				submissionDtl.setSubmissionMode(subMode);
				if (subVariationMode.equals("-1")) {
					subVariationMode = "";
				}
				submissionDtl.setSubVariationMode(subVariationMode);
				submissionDtl.setSubmissionUnitType(submissionUnitType_eu_301);
				
				// v1.4
				// TODO if subVariationMode is worksharing or grouping set
				// highleveltrackingno
				submissionDtl.setTrackingNo(applicationNumber);// Changed in
				
				// v1.4
				if (isRMSSelected == 'N') {// For DCP and MRP
					submissionDtl.setRMSSubmited('N');
				} else {
					isRMSSelected = 'Y';// For NP,CP ,DCP and MRP
					submissionDtl.setRMSSubmited('Y');
				}
				submissionId = docMgmtImpl.insertSubmissionInfoEU14Dtl(
						submissionDtl, 1);// Insert MODE
			}
			// EU v2.0
			else if (prevSubmissionDtl.getEUDtdVersion().equals("20")) {
				DTOSubmissionInfoEU20Dtl submissionDtl = new DTOSubmissionInfoEU20Dtl();
				submissionDtl.setWorkspaceId(workSpaceId);
				submissionDtl.setCountryCode(dtoSubmissionMst.getCountrycode());
				submissionDtl.setCurrentSeqNumber(currentSeqNumber);
				submissionDtl.setLastPublishedVersion(lastPublishedVersion);

				submissionDtl.setSubmissionPath(dtoSubmissionMst
						.getWorkspaceDesc());// Setting only workspace name for
				// path; Not app no.

				submissionDtl.setSubmitedBy(userId);
				submissionDtl.setSubmissionType(submissionType[0]);
				submissionDtl.setDateOfSubmission(sqltDate);
				submissionDtl.setRelatedSeqNo(relatedSeqs);
				submissionDtl.setConfirm('N');
				submissionDtl.setModifyBy(userId);
				submissionDtl.setLabelId(newLabelId);
				submissionDtl.setSubmissionMode(subMode);
				submissionDtl.setSubmissionUnitType(submissionUnitType_eu_301);
				if (subVariationMode.equals("-1")) {
					subVariationMode = "";
				}
				submissionDtl.setSubVariationMode(subVariationMode);
				// v1.4
				// TODO if subVariationMode is worksharing or grouping set
				// highleveltrackingno
				submissionDtl.setTrackingNo(applicationNumber);
				if (isRMSSelected == 'N') {// For DCP and MRP
					submissionDtl.setRMSSubmited('N');
				} else {
					isRMSSelected = 'Y';// For NP,CP ,DCP and MRP
					submissionDtl.setRMSSubmited('Y');
				}
				submissionId = docMgmtImpl.insertSubmissionInfoEU20Dtl(
						submissionDtl, 1);// Insert MODE
			}
			// Inserting CMS/RMS info into SubmissionInfoEUSubDtl
			if (selectedCMS == null) {
				// inserting for RMS in case of 'only RMS is selected'
				// Passing '0' for RMS
				selectedCMS = new int[] { 0 };
				trackCMS = new String[] { applicationNumber };
			}
			for (int i = 0; i < selectedCMS.length; i++) {
				// EU v1.3
				if (prevSubmissionDtl.getEUDtdVersion().equals("13")) {
					DTOSubmissionInfoEUSubDtl dto = new DTOSubmissionInfoEUSubDtl();
					dto.setWorkspaceCMSId(selectedCMS[i]);
					dto.setDateOfSubmission(sqltDate);
					dto.setSubmissionDescription(subDesc);
					dto.setModifyBy(userId);
					dto.setSubmissionInfoEUDtlId(submissionId);
					dto.setPublishCMSTrackingNo(trackCMS[i].trim());
					docMgmtImpl.insertSubmissionInfoEUSubDtl(dto, 1);// 'insert'
					// mode
				}
				// EU v1.4
				else if (prevSubmissionDtl.getEUDtdVersion().equals("14")) {
					DTOSubmissionInfoEU14SubDtl dto = new DTOSubmissionInfoEU14SubDtl();
					dto.setWorkspaceCMSId(selectedCMS[i]);
					dto.setDateOfSubmission(sqltDate);
					dto.setSubmissionDescription(subDesc);
					dto.setModifyBy(userId);
					dto.setSubmissionInfoEU14DtlId(submissionId);
					dto.setPublishCMSTrackingNo(trackCMS[i].trim());
					docMgmtImpl.insertSubmissionInfoEU14SubDtl(dto, 1);// 'insert'
					// mode
				}
				else if (prevSubmissionDtl.getEUDtdVersion().equals("20")) {
					DTOSubmissionInfoEU20SubDtl dto = new DTOSubmissionInfoEU20SubDtl();
					dto.setWorkspaceCMSId(selectedCMS[i]);
					dto.setDateOfSubmission(sqltDate);
					dto.setSubmissionDescription(subDesc);
					dto.setModifyBy(userId);
					dto.setSubmissionInfoEU20DtlId(submissionId);
					dto.setPublishCMSTrackingNo(trackCMS[i].trim());
					docMgmtImpl.insertSubmissionInfoEU20SubDtl(dto, 1);// 'insert'
					// mode
				}
			}
			dtoSubmissionMst.setSubmissionDescription(subDesc);// for 'EU'
			// submission
			// only
			dtoSubmissionMst.setSubmissionId(submissionId);

			// Publishing Workspace
			if (prevSubmissionDtl.getEUDtdVersion().equals("13") ) {
				DTOSubmissionInfoEUDtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoEUDtlBySubmissionId(submissionId);
				submissionPath = dto.getSubmissionPath();
				dtoSubmissionMst.setEUDtdVersion("13");
			} else if (prevSubmissionDtl.getEUDtdVersion().equals("14") ) {
				DTOSubmissionInfoEU14Dtl dtoEU14 = docMgmtImpl
						.getWorkspaceSubmissionInfoEU14DtlBySubmissionId(submissionId);
				submissionPath = dtoEU14.getSubmissionPath();
				dtoSubmissionMst.setEUDtdVersion("14");
			} else if (prevSubmissionDtl.getEUDtdVersion().equals("20")) {
				DTOSubmissionInfoEU20Dtl dtoEU20 = docMgmtImpl
						.getWorkspaceSubmissionInfoEU20DtlBySubmissionId(submissionId);
				submissionPath = dtoEU20.getSubmissionPath();
				dtoSubmissionMst.setEUDtdVersion("20");
			}

			publishWorkspaceSubmission(dtoSubmissionMst, newLabel,
					currentSeqNumber, relatedSeqs, submissionPath,
					lastConfirmedSubmissionPath, submissionType[0]);

			addActionMessage("Project Published Successfully...");
		}
		/************************************** GCC Save Details Start Here... ***************************************/
		else if (dtoSubmissionMst.getCountryRegion().equals("gcc")) {

			java.sql.Timestamp sqltDate = null;
			try {
				DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date parsedUtilDate = formater.parse(dos);

				sqltDate = new java.sql.Timestamp(parsedUtilDate.getTime());

			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}

			DTOInternalLabelMst newLabel = null;
			String newLabelId = null;
			/*if (projectPublishType == 'M') {
				newLabel = new DTOInternalLabelMst();
				newLabel.setLabelId("L0000");
				newLabel.setLabelNo(0);
				newLabelId = newLabel.getLabelId();
			} else {
				newLabel = createWorkspaceLabel(workSpaceId, userId);
				newLabelId = newLabel.getLabelId();
			}*/
			newLabel = createWorkspaceLabel(workSpaceId, userId);
			newLabelId = newLabel.getLabelId();

			String relatedSeqs = relatedSeqNo.replaceAll(" ", "");

			DTOSubmissionInfoGCCDtl submissionDtl = new DTOSubmissionInfoGCCDtl();
			submissionDtl.setWorkspaceId(workSpaceId);
			submissionDtl.setCountryCode(dtoSubmissionMst.getCountrycode());
			submissionDtl.setCurrentSeqNumber(currentSeqNumber);
			submissionDtl.setLastPublishedVersion(lastPublishedVersion);
			submissionDtl
					.setSubmissionPath(dtoSubmissionMst.getWorkspaceDesc());// Setting
			// only
			// workspace
			// name
			// for
			// path; Not app no.
			submissionDtl.setSubmitedBy(userId);
			submissionDtl.setSubmissionType(submissionType[0]);
			submissionDtl.setDateOfSubmission(sqltDate);
			submissionDtl.setRelatedSeqNo(relatedSeqs);
			submissionDtl.setConfirm('N');
			submissionDtl.setModifyBy(userId);
			submissionDtl.setLabelId(newLabelId);

			submissionDtl.setSubmissionMode(subMode);
			if (subVariationMode.equals("-1")) {
				subVariationMode = "";
			}
			submissionDtl.setSubVariationMode(subVariationMode);

			// TODO if subVariationMode is worksharing or grouping set
			// highleveltrackingno
			submissionDtl.setApplicationNo(applicationNumber);
			
			System.out.println("regionalDTDVersion in GCC:"+dtoSubmissionMst.getRegionalDTDVersion());
			
			if(dtoSubmissionMst.getRegionalDTDVersion()==null || dtoSubmissionMst.getRegionalDTDVersion().trim().equalsIgnoreCase("")){
				
				submissionUnitType = "";
			
			}
			else if (dtoSubmissionMst.getRegionalDTDVersion().equalsIgnoreCase("12") ) {
				
				submissionUnitType = "";
			}
			else if (dtoSubmissionMst.getRegionalDTDVersion().equalsIgnoreCase("15")) {
				
				submissionDtl.setSubmissionUnitType(submissionUnitType);
				
			}
			

			if (isRMSSelected == 'N') {// For gcc
				submissionDtl.setRMSSubmited('N');
			} else {
				isRMSSelected = 'Y';// For nation,gcc
				submissionDtl.setRMSSubmited('Y');
			}
			submissionId = docMgmtImpl.insertSubmissionInfoGCCDtl(
					submissionDtl, 1);// Insert MODE
			// done above

			if (selectedCMS == null) {
				// inserting for RMS in case of 'only RMS is selected'
				// Passing '0' for RMS
				selectedCMS = new int[] { 0 };
				trackCMS = new String[] { applicationNumber };
			}
			for (int i = 0; i < selectedCMS.length; i++) {

				DTOSubmissionInfoGCCSubDtl dto = new DTOSubmissionInfoGCCSubDtl();
				dto.setWorkspaceCMSId(selectedCMS[i]);
				dto.setDateOfSubmission(sqltDate);
				dto.setSubmissionDescription(subDesc);
				dto.setModifyBy(userId);
				dto.setSubmissionInfoGCCDtlId(submissionId);
				dto.setPublishCMSTrackingNo(trackCMS[i].trim());
				docMgmtImpl.insertSubmissionInfoGCCSubDtl(dto, 1);// 
				// mode

			}

			dtoSubmissionMst.setSubmissionDescription(subDesc);//
			// submission
			// only
			dtoSubmissionMst.setSubmissionId(submissionId);

			DTOSubmissionInfoGCCDtl dtoGCC = docMgmtImpl
					.getWorkspaceSubmissionInfoGCCDtlBySubmissionId(submissionId);
			submissionPath = dtoGCC.getSubmissionPath();

			publishWorkspaceSubmission(dtoSubmissionMst, newLabel,
					currentSeqNumber, relatedSeqs, submissionPath,
					lastConfirmedSubmissionPath, submissionType[0]);

			addActionMessage("Project Published Successfully...");
		}
		/************************************** GCC save Details Completed Here... ************************************************/
		else if (dtoSubmissionMst.getCountryRegion().equals("za")) {

			java.sql.Timestamp sqltDate = null;
			if (regionalDTDVersion.equalsIgnoreCase("10") || regionalDTDVersion.equalsIgnoreCase(""))
			{
				try {
					System.out.println(dos);
					DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date parsedUtilDate = formater.parse(dos);
	
					sqltDate = new java.sql.Timestamp(parsedUtilDate.getTime());
	
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
			}
			DTOInternalLabelMst newLabel = null;
			String newLabelId = null;
			/*if (projectPublishType == 'M') {
				newLabel = new DTOInternalLabelMst();
				newLabel.setLabelId("L0000");
				newLabel.setLabelNo(0);
				newLabelId = newLabel.getLabelId();
			} else {
				newLabel = createWorkspaceLabel(workSpaceId, userId);
				newLabelId = newLabel.getLabelId();
			}*/
			newLabel = createWorkspaceLabel(workSpaceId, userId);
			newLabelId = newLabel.getLabelId();
			String relatedSeqs = relatedSeqNo.replaceAll(" ", "");

			DTOSubmissionInfoZADtl submissionDtl = new DTOSubmissionInfoZADtl();
			submissionDtl.setWorkspaceId(workSpaceId);
			submissionDtl.setCountryCode(dtoSubmissionMst.getCountrycode());
			submissionDtl.setCurrentSeqNumber(currentSeqNumber);
			submissionDtl.setLastPublishedVersion(lastPublishedVersion);
			submissionDtl
					.setSubmissionPath(dtoSubmissionMst.getWorkspaceDesc());// Setting
			// only
			// workspace
			// name
			// for
			// path; Not app no.
			submissionDtl.setSubmitedBy(userId);
			//submissionDtl.setSubmissionType(submissionType[0]);
			submissionDtl.setDateOfSubmission(sqltDate);
			submissionDtl.setRelatedSeqNo(relatedSeqs);
			submissionDtl.setConfirm('N');
			submissionDtl.setModifyBy(userId);
			submissionDtl.setLabelId(newLabelId);

			submissionDtl.setDosageForm(dtoSubmissionMst.getDosageForm());
			
			submissionDtl.setApplicationNo(applicationNumber);
			
			String submissionType_za20="";
			String efficacyDesc="";
			if(regionalDTDVersion.equals("20"))
			{
				
				if (MultiplesubmissionTypeZa20 != null) {
					for (int i = 0; i < MultiplesubmissionTypeZa20.length; i++) {
						subTypes_za20 = subTypes_za20 + ":" + MultiplesubmissionTypeZa20[i];
						subTypes_za20=subTypes_za20.replace("\t", "");
						System.out.println(subTypes_za20);
						subTypes_za20=subTypes_za20.replace("\n", "");
						System.out.println(subTypes_za20);
						subTypes_za20=subTypes_za20.replace("\\s", "");
						System.out.println(subTypes_za20);
					}
					String []getSubmsnType=subTypes_za20.split(":");
					//System.out.println(getSubmsnType[0]);
					for (int j = 0; j < getSubmsnType.length; j++) {
						if(getSubmsnType[j].equals(""))
							continue;
						String []allPropdate=getSubmsnType[j].split(",");
						submissionType_za20+=","+allPropdate[0];
					}
					
					
					
				}
				submissionDtl.setSubmissionType(submissionType_za20.substring(1));
				//submissionTypeZa=subTypes_za20.substring(1);
				submissionTypeZa=subTypes_za20;
				if (MultiplesubmissionTypeZa20 != null) {
					String []getSubType=subTypes_za20.split(",");
					
					for (int i = 0; i < getSubType.length; i++) {
						if(getSubType[i].equals(""))
							continue;
						if( getSubType[i].contains("#")){
							String []efficacyDescp=getSubType[i].split("#");
							efficacyDesc+=","+efficacyDescp[1];
							System.out.println("Efficacy Description " + efficacyDesc.substring(1));
						}
						
					}
					
				}
				
			}
			else{
				submissionDtl.setSubmissionType(submissionType[0]);
				submissionTypeZa=submissionType[0];
			}
			
			
			String efficacy = "";
			if (datatype != null) {
				for (int i = 0; i < datatype.length; i++) {

					efficacy = efficacy + "," + datatype[i];
					// mode

				}
			}
			propriateryName = "";
			if (MultipleApplication != null) {
				for (int i = 0; i < MultipleApplication.length; i++) {

					propriateryName = propriateryName + ","
							+ MultipleApplication[i];
				}
			}
			
			//submissionDtl.setSubTypes_za20(subTypes_za20);
			//subTypes_za20
		//	submissionDtl.setEfficacy(efficacy);
			if(regionalDTDVersion.equals("20")){//efficacyDesc
				submissionDtl.setEfficacy(subTypes_za20.substring(1));
				submissionDtl.setEfficacyDescription(" ");
			}
			else{
				submissionDtl.setEfficacy(efficacy);
				submissionDtl.setEfficacyDescription(efficacyDescription);
			}
			submissionDtl.setPropriateryName(propriateryName);
			submissionId = docMgmtImpl.insertSubmissionInfoZADtl(submissionDtl,
					1);// Insert MODE

			dtoSubmissionMst.setSubmissionId(submissionId);

			//dtoSubmissionMst.setEfficacy(efficacy);
			if(regionalDTDVersion.equals("20")){//efficacyDesc
				dtoSubmissionMst.setEfficacy(subTypes_za20.substring(1));
				dtoSubmissionMst.setEfficacyDescription(" ");
			}
			else{
				dtoSubmissionMst.setEfficacy(efficacy);
				dtoSubmissionMst.setEfficacyDescription(efficacyDescription);
			}
			dtoSubmissionMst.setPropriateryName(propriateryName);
			DTOSubmissionInfoZADtl dtoZA = docMgmtImpl
					.getWorkspaceSubmissionInfoZADtlBySubmissionId(submissionId);
			submissionPath = dtoZA.getSubmissionPath();

			publishWorkspaceSubmission(dtoSubmissionMst, newLabel,
					currentSeqNumber, relatedSeqs, submissionPath,
					lastConfirmedSubmissionPath, submissionTypeZa);

			addActionMessage("Project Published Successfully...");
		}
		/************************************** AU-TGA save Details Completed Here... ************************************************/
		else if (dtoSubmissionMst.getCountryRegion().equals("au")) {
			String relatedSeqs=null;
			
			String singleDate=null;
			String fromDate=null;
			String toDate=null;
			
			descriptionValue=null;

			try {
				DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
				
				if(singledate!=null){
					java.util.Date parsedUtilDate = formater.parse(singledate);
					
					singleDate=formater.format(parsedUtilDate);
					
				}
				else if(fromdate!=null && todate!=null){
					
					java.util.Date parsedUtilDate = formater.parse(fromdate);
					java.util.Date parsedUtilDates = formater.parse(todate);
				
					
					fromDate=formater.format(parsedUtilDate);
					toDate=formater.format(parsedUtilDates);
					
				}

			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			
			if(fromDate!=null && toDate!=null){
				descriptionValue=fromDate+"#"+toDate;	
			}
			else if(singleDate!=null && number!=null){
				descriptionValue=number+"#"+singleDate;
			}
			else if(description!=null){
				descriptionValue=description+"#";
			}
			else if(singleDate!=null){
				descriptionValue=singleDate+"#";
				
			}else{
				descriptionValue="";	
			}
			DTOInternalLabelMst newLabel = null;
			String newLabelId = null;
			/*if (projectPublishType == 'M') {
				newLabel = new DTOInternalLabelMst();
				newLabel.setLabelId("L0000");
				newLabel.setLabelNo(0);
				newLabelId = newLabel.getLabelId();
			} else {
				newLabel = createWorkspaceLabel(workSpaceId, userId);
				newLabelId = newLabel.getLabelId();
			}*/
			newLabel = createWorkspaceLabel(workSpaceId, userId);
			newLabelId = newLabel.getLabelId();

			if(relatedSeqNo==null || relatedSeqNo==""){
				relatedSeqs=currentSeqNumber;
			}
			else{
				relatedSeqs = relatedSeqNo.replaceAll(" ", "");
			}

			DTOSubmissionInfoAUDtl submissionDtl = new DTOSubmissionInfoAUDtl();
			submissionDtl.setWorkspaceId(workSpaceId);
			submissionDtl.setCountryCode(dtoSubmissionMst.getCountrycode());
			submissionDtl.setCurrentSeqNumber(currentSeqNumber);
			submissionDtl.setLastPublishedVersion(lastPublishedVersion);
			submissionDtl
					.setSubmissionPath(dtoSubmissionMst.geteSubmissionId());// Setting
			// only
			// workspace
			// name
			// for
			// path; Not app no.
			submissionDtl.setSubmitedBy(userId);
		
			submissionDtl.setRelatedSeqNo(relatedSeqs);
			submissionDtl.setConfirm('N');
			submissionDtl.setModifyBy(userId);
			submissionDtl.setLabelId(newLabelId);

			if (subVariationMode.equals("-1")) {
				subVariationMode = "";
			}
			submissionDtl.setSubmissionMode(subVariationMode);
			submissionDtl.seteSubmissionId(applicationNumber);
			artgNumber= "";
			if (MultipleARTG != null) {
				for (int i = 0; i < MultipleARTG.length; i++) {

					artgNumber = artgNumber + "#" + MultipleARTG[i];
					// mode

				}
			}
			
			submissionDtl.setSequenceType(seqTypes);
			submissionDtl.setSequenceDescription(seqDesc);
			
			submissionDtl.setSequenceDescriptionValue(descriptionValue);
			submissionDtl.setARTGNumber(artgNumber);
			submissionId = docMgmtImpl.insertSubmissionInfoAUDtl(submissionDtl,1);// Insert MODE
		
			dtoSubmissionMst.setSubmissionId(submissionId);
			

			DTOSubmissionInfoAUDtl dtoAU = docMgmtImpl
					.getWorkspaceSubmissionInfoAUDtlBySubmissionId(submissionId);
			submissionPath = dtoAU.getSubmissionPath();

			
			publishWorkspaceSubmission(dtoSubmissionMst, newLabel,
					currentSeqNumber, relatedSeqs, submissionPath,
					lastConfirmedSubmissionPath, submissionType[0]);

			addActionMessage("Project Published Successfully...");
		}
		
		
		else if (dtoSubmissionMst.getCountryRegion().equals("th")) {

			
			
			DTOInternalLabelMst newLabel = null;
			String newLabelId = null;
			/*if (projectPublishType == 'M') {
				newLabel = new DTOInternalLabelMst();
				newLabel.setLabelId("L0000");
				newLabel.setLabelNo(0);
				newLabelId = newLabel.getLabelId();
			} else {
				newLabel = createWorkspaceLabel(workSpaceId, userId);
				newLabelId = newLabel.getLabelId();
			}*/
			newLabel = createWorkspaceLabel(workSpaceId, userId);
			newLabelId = newLabel.getLabelId();

			
			String relatedSeqs = relatedSeqNo.replaceAll(" ", "");
			

			DTOSubmissionInfoTHDtl submissionDtl = new DTOSubmissionInfoTHDtl();
			submissionDtl.setWorkspaceId(workSpaceId);
			submissionDtl.setCountryCode(dtoSubmissionMst.getCountrycode());
			submissionDtl.setCurrentSeqNumber(currentSeqNumber);
			submissionDtl.setLastPublishedVersion(lastPublishedVersion);
			submissionDtl
					.setSubmissionPath(dtoSubmissionMst.geteSubmissionId());// Setting
			// only
			// workspace
			// name
			// for
			// path; Not app no.
			submissionDtl.setSubmitedBy(userId);

			try {
				DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date parsedUtilDate = formater.parse(dos);
				java.sql.Timestamp sqltDate = new java.sql.Timestamp(
						parsedUtilDate.getTime());

				submissionDtl.setDateOfSubmission(sqltDate);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		
			submissionDtl.setRelatedSeqNo(relatedSeqs);
			submissionDtl.setConfirm('N');
			submissionDtl.setModifyBy(userId);
			submissionDtl.setLabelId(newLabelId);

			submissionDtl.seteSubmissionId(applicationNumber);
			
			
			submissionDtl.setSequenceType(seqTypes_th);
			submissionDtl.setSequenceDescription(seqDesc_th);
			submissionDtl.setEmail(email);
			
			submissionId = docMgmtImpl.insertSubmissionInfoTHDtl(submissionDtl,1);// Insert MODE
		
			dtoSubmissionMst.setSubmissionId(submissionId);
			

			DTOSubmissionInfoTHDtl dtoTH = docMgmtImpl
					.getWorkspaceSubmissionInfoTHDtlBySubmissionId(submissionId);
			submissionPath = dtoTH.getSubmissionPath();

			
			publishWorkspaceSubmission(dtoSubmissionMst, newLabel,
					currentSeqNumber, relatedSeqs, submissionPath,
					lastConfirmedSubmissionPath, submissionType[0]);

			addActionMessage("Project Published Successfully...");
		}

		/************************************************************************************************************************************/
		else if (dtoSubmissionMst.getCountryRegion().equals("ca")) {
			// Creating label for workspace with PublishType 'P' only
			
			
			 seqDescFlag=null;
			String singleDate=null;
			String fromDate=null;
			String toDate=null;
			
			descriptionValue=null;

			try {
				DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
				DateFormat formaters = new SimpleDateFormat(" MMM. dd, yyyy");
				
				if(singledate!=null){
					java.util.Date parsedUtilDate = formater.parse(singledate);
					
					singleDate=formaters.format(parsedUtilDate);
					
				}
				else if(fromdate!=null && todate!=null){
					
					java.util.Date parsedUtilDate = formater.parse(fromdate);
					java.util.Date parsedUtilDates = formater.parse(todate);
				
					
					fromDate=formaters.format(parsedUtilDate);
					toDate=formaters.format(parsedUtilDates);
					
				}

			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			
			if(fromDate!=null && toDate!=null){
				descriptionValue=fromDate+"#"+toDate;	
				seqDescFlag="twoDate";
			}
			else if(singleDate!=null && number!=null){
				descriptionValue=number+"#"+singleDate;
				seqDescFlag="mixDesc";
			}
			else if(description!=null){
				descriptionValue=description+"#";
				seqDescFlag="desc";
			}
			else if(singleDate!=null){
				descriptionValue=singleDate+"#";
				seqDescFlag="singleDate";
			}else{
				descriptionValue="";
				seqDescFlag="";
			}
			
			String[] seqDescTypeCa = seqDesc_CA.split("#");
			
			System.out.println("seqDescTypeCa[1]->"+seqDescTypeCa[1]);
			DTOInternalLabelMst newLabel = null;
			String newLabelId = null;
			/*if (projectPublishType == 'M') {
				newLabel = new DTOInternalLabelMst();
				newLabel.setLabelId("L0000");
				newLabel.setLabelNo(0);
				newLabelId = newLabel.getLabelId();
			} else {
				newLabel = createWorkspaceLabel(workSpaceId, userId);
				newLabelId = newLabel.getLabelId();
			}*/
			newLabel = createWorkspaceLabel(workSpaceId, userId);
			newLabelId = newLabel.getLabelId();

			// Insert into submissionInfoCAMst
			DTOSubmissionInfoCADtl submissionDtl = new DTOSubmissionInfoCADtl();

			submissionDtl.setWorkspaceId(workSpaceId);
			submissionDtl.setCountryCode(dtoSubmissionMst.getCountrycode());
			submissionDtl.setCurrentSeqNumber(currentSeqNumber);
			submissionDtl.setLastPublishedVersion(lastPublishedVersion);
			submissionDtl
					.setSubmissionPath(dtoSubmissionMst.getDossierIdentifier());// Setting
			// only
			// app
			// no
			// for
			// path
			submissionDtl.setSubmitedBy(userId);
			//submissionDtl.setSubmissionType(submissionType[0]);

			
			/*******************************************************/

			String relatedSeqs = relatedSeqNo.replaceAll(" ", "");
			/*******************************************************/

			submissionDtl.setRelatedSeqNo(relatedSeqs);
			submissionDtl.setConfirm('N');
			submissionDtl.setModifyBy(userId);
			submissionDtl.setLabelId(newLabelId);
			submissionDtl.setDossierIdentifier(applicationNumber);
			submissionDtl.setRegulatoryActivityType(regActType);
			submissionDtl.setSequenceDescription(seqDescTypeCa[1]);
			submissionDtl.setSequenceDescriptionFlag(seqDescFlag);
			submissionDtl.setSequenceDescriptionValue(descriptionValue);
			//submissionDtl.setSubmissionMode(subMode);
			//submissionDtl.setApplicationNo(applicationNumber);
			submissionId = docMgmtImpl.insertSubmissionInfoCADtl(submissionDtl,1);// Insert MODE
			// System.out.println("submissionId"+submissionId);

			// Publishing Workspace
			dtoSubmissionMst.setSubmissionId(submissionId);
			DTOSubmissionInfoCADtl dto = DocMgmtImpl
					.getWorkspaceSubmissionInfoCADtlBySubmissionId(submissionId);

			publishWorkspaceSubmission(dtoSubmissionMst, newLabel,
					currentSeqNumber, relatedSeqs, dto.getSubmissionPath(),
					lastConfirmedSubmissionPath, seqDescTypeCa[1]);

			addActionMessage("Project Published Successfully...");

		}
		/********************************************** Swissmedic **********************************************************************************/
		else if (dtoSubmissionMst.getCountryRegion().equals("ch")) {
			// Creating label for workspace with PublishType 'P' only
			DTOInternalLabelMst newLabel = null;
			String newLabelId = null;
			/*if (projectPublishType == 'M') {
				newLabel = new DTOInternalLabelMst();
				newLabel.setLabelId("L0000");
				newLabel.setLabelNo(0);
				newLabelId = newLabel.getLabelId();
			} else {
				newLabel = createWorkspaceLabel(workSpaceId, userId);
				newLabelId = newLabel.getLabelId();
			}*/
			newLabel = createWorkspaceLabel(workSpaceId, userId);
			newLabelId = newLabel.getLabelId();

			// Insert into submissionInfoCAMst

			DTOSubmissionInfoCHDtl submissionDtl = new DTOSubmissionInfoCHDtl();

			submissionDtl.setWorkspaceId(workSpaceId);
			submissionDtl.setCountryCode(dtoSubmissionMst.getCountrycode());
			submissionDtl.setCurrentSeqNumber(currentSeqNumber);
			submissionDtl.setLastPublishedVersion(lastPublishedVersion);
			//submissionDtl.setSubmissionPath(dtoSubmissionMst.getApplicationNo());
			String[] AppNum=dtoSubmissionMst.getApplicationNo().split(",");
			submissionDtl.setSubmissionPath(AppNum[0]);
			// Setting
			// only
			// app
			// no
			// for
			// path

			submissionDtl.setSubmitedBy(userId);
			String subTypes_ch= "";
			if (MultiplesubmissionType != null) {
				for (int i = 0; i < MultiplesubmissionType.length; i++) {

					subTypes_ch = subTypes_ch + "," + MultiplesubmissionType[i];
					

				}
			}
			submissionDtl.setSubmissionType(subTypes_ch.substring(1));
			try {
				DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date parsedUtilDate = formater.parse(dos);
				java.sql.Timestamp sqltDate = new java.sql.Timestamp(
						parsedUtilDate.getTime());

				submissionDtl.setDateOfSubmission(sqltDate);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}

			/*******************************************************/

			String relatedSeqs = relatedSeqNo.replaceAll(" ", "");
			/*******************************************************/

			submissionDtl.setRelatedSeqNo(relatedSeqs);
			submissionDtl.setConfirm('N');
			submissionDtl.setModifyBy(userId);
			submissionDtl.setLabelId(newLabelId);
			submissionDtl.setSubmissionMode(subMode);
			submissionDtl.setApplicationNo(applicationNumber);
			String gForm = "";
			if (GanelicForm != null) {
				for (int i = 0; i < GanelicForm.length; i++) {

					gForm = gForm + "," + GanelicForm[i];
				}
			}

			dtoSubmissionMst.setGanelicForm(gForm);

			dtoSubmissionMst.setParagraph13(paragraph13);

			submissionDtl.setGanelicForm(gForm);
			submissionDtl.setParagraph13(paragraph13);

			submissionId = docMgmtImpl.insertSubmissionInfoCHDtl(submissionDtl,
					1);// Insert MODE
			// System.out.println("submissionId"+submissionId);

			// Publishing Workspace
			DTOSubmissionInfoCHDtl dto = docMgmtImpl
					.getWorkspaceSubmissionInfoCHDtlBySubmissionId(submissionId);

			publishWorkspaceSubmission(dtoSubmissionMst, newLabel,
					currentSeqNumber, relatedSeqs, dto.getSubmissionPath(),
					lastConfirmedSubmissionPath, subTypes_ch.substring(1));

			addActionMessage("Project Published Successfully...");

		}
		/**********************************************************************************************************************************************/
		/** Added For Manual Mode Publish */
		if (projectPublishType == 'M') {
			HttpServletRequest request = ServletActionContext.getRequest();
			DTOWorkSpaceMst workSpaceMst = docMgmtImpl
					.getWorkSpaceDetail(workSpaceId);
			ArrayList<DTOWorkSpaceNodeHistory> leafNodesHistory = docMgmtImpl
					.getAllNodesLastHistory(workSpaceId, leafIds);
			ArrayList<DTOSubmissionInfoForManualMode> subDtl = new ArrayList<DTOSubmissionInfoForManualMode>();

			for (int i = 0; i < leafIds.length; i++) {
				int nodeId = leafIds[i];

				DTOSubmissionInfoForManualMode dto = new DTOSubmissionInfoForManualMode();
				dto.setWorkspaceId(workSpaceId);
				dto.setSubmissionId(submissionId);
				dto.setRegion(dtoSubmissionMst.getCountryRegion());
				dto.setNodeId(nodeId);

				// Set TranNo
				DTOWorkSpaceNodeHistory nodeHistory = null;
				for (Iterator<DTOWorkSpaceNodeHistory> iterator = leafNodesHistory
						.iterator(); iterator.hasNext();) {
					nodeHistory = iterator.next();
					if (nodeHistory.getNodeId() == nodeId) {
						dto.setTranNo(nodeHistory.getTranNo());
						break;
					}
				}
				leafNodesHistory.remove(nodeHistory);

				dto.setOperation(request.getParameter("operation_" + nodeId));
				dto.setRefID(request.getParameter("refID_" + nodeId));
				dto.setRelSeqNo(request.getParameter("refSeq_" + nodeId));
				dto.setModifyBy(userId);
				subDtl.add(dto);
			}
			docMgmtImpl.insertSubmissionInfoForManualMode(subDtl);

			// String relatedSeqs = relatedSeqNo.replaceAll(" ", "");
			System.out.println("relatedSeqNo-->" + relatedSeqNo);
			System.out.println("copyRelatedSeq-->" + copyRelatedSeq);

			if (copyRelatedSeq == 'Y'){
				
				String manualPrevSeqPath = PropertyInfo.getPropInfo()
				.getValue("ManualProjectsServerPath");
				String FosunChanges=PropertyInfo.getPropInfo().getValue("FosunCustomization");
				if(FosunChanges.equalsIgnoreCase("yes")){
					manualPrevSeqPath=manualPrevSeqPath +"/"+ workSpaceMst.getClientName();	
				}
				String prevSequencePath = manualPrevSeqPath + "/" + workSpaceId;
				//copy file logic from manualprevsequcen to current publish project
				System.out.println("prevSequencePath::"+prevSequencePath);
				System.out.println("submissionPath::"+submissionPath);
				if (prevSequencePath != null && !submissionPath.equals("")) {

					FileManager fileManager = new FileManager();
					fileManager.copyDirectory(new File(prevSequencePath), new File(
							submissionPath));

				}
			}
				

			// copyRelatedSeq
		}

		extraHtmlCode = "<input type=\"button\" class=\"button\" value=\"Back\" onclick=\"subinfo();\">";

		return "save";
	}

	public String getSubmissionDataForRecompile() {

		DTOSubmissionMst dtoSubmissionMst = docMgmtImpl
				.getSubmissionInfo(workSpaceId);
		DTOSubmissionMst prevSubmissionDtl = docMgmtImpl.getSubmissionInfoEURegion(workSpaceId);
		DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");

		if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("us")) {

			if (dtoSubmissionMst.getRegionalDTDVersion().equals("23")) {
				DTOSubmissionInfoUS23Dtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoUS23DtlBySubmissionId(submissionInfo__DtlId);
				java.sql.Timestamp dateOfSubmisson = dto.getDateOfSubmission();
				dos = formater.format((java.util.Date) dateOfSubmisson);
			} else {
				DTOSubmissionInfoUSDtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoUSDtlBySubmissionId(submissionInfo__DtlId);
				java.sql.Timestamp dateOfSubmisson = dto.getDateOfSubmission();
				dos = formater.format((java.util.Date) dateOfSubmisson);
			}

		} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("eu")
				&& prevSubmissionDtl.getEUDtdVersion().equals("13")) {
			DTOSubmissionInfoEUDtl dto = docMgmtImpl
					.getWorkspaceSubmissionInfoEUDtlBySubmissionId(submissionInfo__DtlId);
			java.sql.Timestamp dateOfSubmisson = dto.getDateOfSubmission();
			dos = formater.format((java.util.Date) dateOfSubmisson);
		}
		
		// For EU v1.4
		else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("eu")
				&& prevSubmissionDtl.getEUDtdVersion().equals("14")) {
			DTOSubmissionInfoEU14Dtl dto = docMgmtImpl
					.getWorkspaceSubmissionInfoEU14DtlBySubmissionId(submissionInfo__DtlId);
			java.sql.Timestamp dateOfSubmisson = dto.getDateOfSubmission();
			dos = formater.format((java.util.Date) dateOfSubmisson);
		} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("eu")
				&& prevSubmissionDtl.getEUDtdVersion().equals("20")) {
			DTOSubmissionInfoEU20Dtl dto = docMgmtImpl
					.getWorkspaceSubmissionInfoEU20DtlBySubmissionId(submissionInfo__DtlId);
			java.sql.Timestamp dateOfSubmisson = dto.getDateOfSubmission();
			dos = formater.format((java.util.Date) dateOfSubmisson);
		} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("au")) {
			DTOSubmissionInfoAUDtl dto = docMgmtImpl
			.getWorkspaceSubmissionInfoAUDtlBySubmissionId(submissionInfo__DtlId);
	// java.sql.Timestamp dateOfSubmisson = dto.getDateOfSubmission();
	// dos = formater.format((java.util.Date) dateOfSubmisson);

		}
		else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("ca")) {
			DTOSubmissionInfoCADtl dto = DocMgmtImpl
					.getWorkspaceSubmissionInfoCADtlBySubmissionId(submissionInfo__DtlId);
			//java.sql.Timestamp dateOfSubmisson = dto.getDateOfSubmission();
			//dos = formater.format((java.util.Date) dateOfSubmisson);

		} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("gcc")) {
			DTOSubmissionInfoGCCDtl dto = docMgmtImpl
					.getWorkspaceSubmissionInfoGCCDtlBySubmissionId(submissionInfo__DtlId);
			java.sql.Timestamp dateOfSubmisson = dto.getDateOfSubmission();
			dos = formater.format((java.util.Date) dateOfSubmisson);

		} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("za")) {
			DTOSubmissionInfoZADtl dto = docMgmtImpl
					.getWorkspaceSubmissionInfoZADtlBySubmissionId(submissionInfo__DtlId);
			java.sql.Timestamp dateOfSubmisson = dto.getDateOfSubmission();
			dos = formater.format((java.util.Date) dateOfSubmisson);

		} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("ch")) {
			DTOSubmissionInfoCHDtl dto = docMgmtImpl
					.getWorkspaceSubmissionInfoCHDtlBySubmissionId(submissionInfo__DtlId);
			java.sql.Timestamp dateOfSubmisson = dto.getDateOfSubmission();
			dos = formater.format((java.util.Date) dateOfSubmisson);

		}
		else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("th")) {
			DTOSubmissionInfoTHDtl dto = docMgmtImpl
					.getWorkspaceSubmissionInfoTHDtlBySubmissionId(submissionInfo__DtlId);
			java.sql.Timestamp dateOfSubmisson = dto.getDateOfSubmission();
			dos = formater.format((java.util.Date) dateOfSubmisson);

		}
		return SUCCESS;
	}

	public String recompileSubmission() {

		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		String relatedSeqs = "", submissionType = "", lastPublishedVersion = "", currentSeqNumber = "", subPath = "";
		recompile = true;
		// Source IndexXML File Path
		String indexXMLFilePath = submissionPath + File.separator + "index.xml";
		// System.out.println("indexXMLFilePath:::"+indexXMLFilePath);

		// Destination workspace folder Path
		DTOWorkSpaceMst wsmst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		String destinationWorkspaceFolderPath = wsmst.getBaseWorkFolder()
				+ File.separator + workSpaceId;

		// Set stageId and copy files to workspace with all database history
		// entries

		int defaultStageId = 100;// All recompile nodes' files will be Approved
		FileManager fileManager = new FileManager();
		fileManager.copyPublishedFilesToWorkspace(indexXMLFilePath,
				destinationWorkspaceFolderPath, userId, defaultStageId);

		// Creating label for workspace
		DTOInternalLabelMst newLabel = createWorkspaceLabel(workSpaceId, userId);
		String newLabelId = newLabel.getLabelId();

		DTOSubmissionMst dtoSubmissionMst = docMgmtImpl
				.getSubmissionInfo(workSpaceId);
		
		// Updating labelId for recompiled submission
		if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("us")) {
			if (dtoSubmissionMst.getRegionalDTDVersion().equals("23")) {

				DTOSubmissionInfoUS23Dtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoUS23DtlBySubmissionId(submissionInfo__DtlId);
				// dto.setSubmissionInfoUSDtlId(submissionInfo__DtlId);
				dto.setLabelId(newLabelId);
				dto.setSubmitedBy(userId);
				dto.setModifyBy(userId);
				try {
					DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date parsedUtilDate = formater.parse(dos);
					java.sql.Timestamp sqltDate = new java.sql.Timestamp(
							parsedUtilDate.getTime());

					dto.setDateOfSubmission(sqltDate);
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				docMgmtImpl.insertSubmissionInfoUS23Dtl(dto, 2);// Update MODE
				relatedSeqs = dto.getRelatedSeqNo();
				//submissionType = dto.getSubmissionType();
				submissionType=docMgmtImpl.getSubmissionTypeCodeForUS23(dto.getSubmissionType());
				lastPublishedVersion = dto.getLastPublishedVersion();
				currentSeqNumber = dto.getCurrentSeqNumber();
				subPath = dto.getSubmissionPath();

				applicantContactDetails = dto.getApplicantContactList().split(
						"$");
				supplementEffectiveDateTypeCode = dto
						.getSuppEffectiveDateType();
				crossReferenceNumber = dto.getCrossReferenceNumber();
				applicationTypeCode = dto.getCrossRefAppType();
				subSubType = dto.getSubSubType();

			} else {
				DTOSubmissionInfoUSDtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoUSDtlBySubmissionId(submissionInfo__DtlId);
				// dto.setSubmissionInfoUSDtlId(submissionInfo__DtlId);
				dto.setLabelId(newLabelId);
				dto.setSubmitedBy(userId);
				dto.setModifyBy(userId);
				try {
					DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date parsedUtilDate = formater.parse(dos);
					java.sql.Timestamp sqltDate = new java.sql.Timestamp(
							parsedUtilDate.getTime());

					dto.setDateOfSubmission(sqltDate);
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				docMgmtImpl.insertSubmissionInfoUSDtl(dto, 2);// Update MODE
				relatedSeqs = dto.getRelatedSeqNo();
				submissionType = dto.getSubmissionType();
				lastPublishedVersion = dto.getLastPublishedVersion();
				currentSeqNumber = dto.getCurrentSeqNumber();
				subPath = dto.getSubmissionPath();
			}
		} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("au")) {
			DTOSubmissionInfoAUDtl dto = docMgmtImpl
			.getWorkspaceSubmissionInfoAUDtlBySubmissionId(submissionInfo__DtlId);
			
			dto.setLabelId(newLabelId);
			dto.setSubmitedBy(userId);
			dto.setModifyBy(userId);
			
			docMgmtImpl.insertSubmissionInfoAUDtl(dto, 2);// Update MODE
			relatedSeqs = dto.getRelatedSeqNo();
			//submissionType = dto.getSubmissionType();
			lastPublishedVersion = dto.getLastPublishedVersion();
			currentSeqNumber = dto.getCurrentSeqNumber();
			subPath = dto.getSubmissionPath();
			
			
			seqTypes=dto.getSequenceType();
			seqDesc=dto.getSequenceDescription();
			descriptionValue=dto.getSequenceDescriptionValue();
			artgNumber=dto.getARTGNumber();
			

		}
		else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("ca")) {

			DTOSubmissionInfoCADtl dto = DocMgmtImpl
					.getWorkspaceSubmissionInfoCADtlBySubmissionId(submissionInfo__DtlId);
			dto.setLabelId(newLabelId);
			dto.setSubmitedBy(userId);
			dto.setModifyBy(userId);
			
			DocMgmtImpl.insertSubmissionInfoCADtl(dto, 2);// Update MODE
			relatedSeqs = dto.getRelatedSeqNo();
			//submissionType = dto.getSubmissionType();
			lastPublishedVersion = dto.getLastPublishedVersion();
			currentSeqNumber = dto.getCurrentSeqNumber();
			subPath = dto.getSubmissionPath();
			regActType=dto.getRegulatoryActivityType();
			seqDescFlag=dto.getSequenceDescriptionFlag();
			submissionType=dto.getSequenceDescription();
			descriptionValue=dto.getSequenceDescriptionValue();
		}
		else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("th")) {
			DTOSubmissionInfoTHDtl dto = docMgmtImpl
			.getWorkspaceSubmissionInfoTHDtlBySubmissionId(submissionInfo__DtlId);
			
			dto.setLabelId(newLabelId);
			dto.setSubmitedBy(userId);
			dto.setModifyBy(userId);
			try {
				DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date parsedUtilDate = formater.parse(dos);
				java.sql.Timestamp sqltDate = new java.sql.Timestamp(
						parsedUtilDate.getTime());
				dto.setDateOfSubmission(sqltDate);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			docMgmtImpl.insertSubmissionInfoTHDtl(dto, 2);// Update MODE
			relatedSeqs = dto.getRelatedSeqNo();
			//submissionType = dto.getSubmissionType();
			lastPublishedVersion = dto.getLastPublishedVersion();
			currentSeqNumber = dto.getCurrentSeqNumber();
			subPath = dto.getSubmissionPath();
			seqTypes_th=dto.getSequenceType();
			seqDesc_th=dto.getSequenceDescription();
			email=dto.getEmail();
			

		}
		else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("eu")) {
			DTOSubmissionMst prevSubmissionDtl = docMgmtImpl.getSubmissionInfoEURegion(workSpaceId);
			if (prevSubmissionDtl.getEUDtdVersion().equals("13")) {
				DTOSubmissionInfoEUDtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoEUDtlBySubmissionId(submissionInfo__DtlId);
				// dto.setSubmissionInfoEUDtlId(submissionInfo__DtlId);
				dto.setLabelId(newLabelId);
				dto.setSubmitedBy(userId);
				dto.setModifyBy(userId);
				dtoSubmissionMst.setEUDtdVersion("13");
				try {
					DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date parsedUtilDate = formater.parse(dos);
					java.sql.Timestamp sqltDate = new java.sql.Timestamp(
							parsedUtilDate.getTime());
					dto.setDateOfSubmission(sqltDate);
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
				docMgmtImpl.insertSubmissionInfoEUDtl(dto, 2);// Update MODE

				relatedSeqs = dto.getRelatedSeqNo();
				submissionType = dto.getSubmissionType();
				submissionUnitType_eu_301=dto.getSubmissionUnitType();
				lastPublishedVersion = dto.getLastPublishedVersion();
				currentSeqNumber = dto.getCurrentSeqNumber();
				subPath = dto.getSubmissionPath();
				isRMSSelected = dto.getRMSSubmited();
				subVariationMode=dto.getSubVariationMode();
			}
			// For EU v1.4
			else if (prevSubmissionDtl.getEUDtdVersion().equals("14")) {
				DTOSubmissionInfoEU14Dtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoEU14DtlBySubmissionId(submissionInfo__DtlId);
				// dto.setSubmissionInfoEUDtlId(submissionInfo__DtlId);
				dto.setLabelId(newLabelId);
				dto.setSubmitedBy(userId);
				dto.setModifyBy(userId);
				dtoSubmissionMst.setEUDtdVersion("14");
				try {
					DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date parsedUtilDate = formater.parse(dos);
					java.sql.Timestamp sqltDate = new java.sql.Timestamp(
							parsedUtilDate.getTime());
					dto.setDateOfSubmission(sqltDate);
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
				docMgmtImpl.insertSubmissionInfoEU14Dtl(dto, 2);// Update MODE

				relatedSeqs = dto.getRelatedSeqNo();
				submissionType = dto.getSubmissionType();
				submissionUnitType_eu_301=dto.getSubmissionUnitType();
				lastPublishedVersion = dto.getLastPublishedVersion();
				currentSeqNumber = dto.getCurrentSeqNumber();
				subPath = dto.getSubmissionPath();
				isRMSSelected = dto.getRMSSubmited();
				subVariationMode=dto.getSubVariationMode();
			} else if (prevSubmissionDtl.getEUDtdVersion().equals("20")) {
				DTOSubmissionInfoEU20Dtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoEU20DtlBySubmissionId(submissionInfo__DtlId);
				// dto.setSubmissionInfoEUDtlId(submissionInfo__DtlId);
				dto.setLabelId(newLabelId);
				dto.setSubmitedBy(userId);
				dto.setModifyBy(userId);
				dtoSubmissionMst.setEUDtdVersion("20");
				try {
					DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date parsedUtilDate = formater.parse(dos);
					java.sql.Timestamp sqltDate = new java.sql.Timestamp(
							parsedUtilDate.getTime());
					dto.setDateOfSubmission(sqltDate);
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
				docMgmtImpl.insertSubmissionInfoEU20Dtl(dto, 2);// Update MODE

				relatedSeqs = dto.getRelatedSeqNo();
				submissionType = dto.getSubmissionType();
				submissionUnitType_eu_301=dto.getSubmissionUnitType();
				lastPublishedVersion = dto.getLastPublishedVersion();
				currentSeqNumber = dto.getCurrentSeqNumber();
				subPath = dto.getSubmissionPath();
				isRMSSelected = dto.getRMSSubmited();
				subVariationMode=dto.getSubVariationMode();
			}
		}  else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("gcc")) {
			DTOSubmissionInfoGCCDtl dto = docMgmtImpl
					.getWorkspaceSubmissionInfoGCCDtlBySubmissionId(submissionInfo__DtlId);
			// dto.setSubmissionInfoEUDtlId(submissionInfo__DtlId);
			dto.setLabelId(newLabelId);
			dto.setSubmitedBy(userId);
			dto.setModifyBy(userId);
			try {
				DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date parsedUtilDate = formater.parse(dos);
				java.sql.Timestamp sqltDate = new java.sql.Timestamp(
						parsedUtilDate.getTime());
				dto.setDateOfSubmission(sqltDate);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			docMgmtImpl.insertSubmissionInfoGCCDtl(dto, 2);// Update MODE

			relatedSeqs = dto.getRelatedSeqNo();
			submissionType = dto.getSubmissionType();
			submissionUnitType=dto.getSubmissionUnitType();
			lastPublishedVersion = dto.getLastPublishedVersion();
			currentSeqNumber = dto.getCurrentSeqNumber();
			subPath = dto.getSubmissionPath();
			isRMSSelected = dto.getRMSSubmited();
		} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("za")) {
			DTOSubmissionInfoZADtl dto = docMgmtImpl
					.getWorkspaceSubmissionInfoZADtlBySubmissionId(submissionInfo__DtlId);
			regionalDTDVersion = dtoSubmissionMst.getRegionalDTDVersion();
			// dto.setSubmissionInfoEUDtlId(submissionInfo__DtlId);
			dto.setLabelId(newLabelId);
			dto.setSubmitedBy(userId);
			dto.setModifyBy(userId);

			//dtoSubmissionMst.setEfficacy(dto.getEfficacy());
			//dtoSubmissionMst.setEfficacyDescription(dto
				//	.getEfficacyDescription());
			if(regionalDTDVersion.equals("20")){//efficacyDesc
				dtoSubmissionMst.setEfficacy(dto.getEfficacy());
				dtoSubmissionMst.setEfficacyDescription(dto
							.getEfficacyDescription());
			}
			else{
				dtoSubmissionMst.setEfficacy(dto.getEfficacy());
				dtoSubmissionMst.setEfficacyDescription(dto
							.getEfficacyDescription());
			}
			dtoSubmissionMst.setPropriateryName(dto.getPropriateryName());
			if (regionalDTDVersion.equalsIgnoreCase("10"))
			{
				try {
					DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
					java.util.Date parsedUtilDate = formater.parse(dos);
					java.sql.Timestamp sqltDate = new java.sql.Timestamp(
							parsedUtilDate.getTime());
					dto.setDateOfSubmission(sqltDate);
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
			}
			docMgmtImpl.insertSubmissionInfoZADtl(dto, 2);// Update MODE

			relatedSeqs = dto.getRelatedSeqNo();
			submissionType = dto.getSubmissionType();
			lastPublishedVersion = dto.getLastPublishedVersion();
			currentSeqNumber = dto.getCurrentSeqNumber();
			subPath = dto.getSubmissionPath();
			isRMSSelected = dto.getRMSSubmited();
		} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("ch")) {
			DTOSubmissionInfoCHDtl dto = docMgmtImpl
					.getWorkspaceSubmissionInfoCHDtlBySubmissionId(submissionInfo__DtlId);
			// dto.setSubmissionInfoEUDtlId(submissionInfo__DtlId);
			dto.setLabelId(newLabelId);
			dto.setSubmitedBy(userId);
			dto.setModifyBy(userId);

			try {
				DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date parsedUtilDate = formater.parse(dos);
				java.sql.Timestamp sqltDate = new java.sql.Timestamp(
						parsedUtilDate.getTime());
				dto.setDateOfSubmission(sqltDate);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			docMgmtImpl.insertSubmissionInfoCHDtl(dto, 2);// Update MODE

			dtoSubmissionMst.setParagraph13(dto.getParagraph13());
			dtoSubmissionMst.setGanelicForm(dto.getGanelicForm());

			relatedSeqs = dto.getRelatedSeqNo();
			submissionType = dto.getSubmissionType();
			lastPublishedVersion = dto.getLastPublishedVersion();
			currentSeqNumber = dto.getCurrentSeqNumber();
			subPath = dto.getSubmissionPath();
			isRMSSelected = dto.getRMSSubmited();
		}

		dtoSubmissionMst.setSubmissionId(submissionInfo__DtlId);// To get
		// submission
		// desc for EU

		/**********************************************************************************/
		/*
		 * find tracking table xml in already published dossier and if it exists
		 * then again add it to the recompiled dossier.
		 */
		String commonFolderPath = submissionPath + File.separator + "m1"
				+ File.separator + "eu" + File.separator + "10-cover"
				+ File.separator + "common";
		File commonFolder = new File(commonFolderPath);

		if (commonFolder != null && commonFolder.exists()) {
			File commonFolderFiles[] = commonFolder.listFiles();
			for (int i = 0; commonFolderFiles != null
					&& i < commonFolderFiles.length; i++) {
				String commonFile = commonFolderFiles[i].getName();
				if (commonFile != null
						&& commonFile.contains("tracking")
						&& (commonFile.contains(".xml") || commonFile
								.contains(".pdf")))
					addTT = 'N'; // added for recompile problem during adding
				// link in eu-regional.xml for
				// tracking-table.pdf file
			}

		}
		/**********************************************************************************/

		// Publishing Workspace

		publishWorkspaceSubmission(dtoSubmissionMst, newLabel,
				currentSeqNumber, relatedSeqs, subPath,
				lastConfirmedSubmissionPath, submissionType);
		addActionMessage("Submission Recompiled Successfully...");
		extraHtmlCode = "<center><input type=\"button\" class=\"button\" value=\"Close\" onclick=\"self.close();\"></center>";
		problemInLoad = false;
		return "recompile";
	}

	public String confirmSubmission() {
		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		int labelNo = 0;
		String rtnVal = null;
		String currentSubmissionLabelId = "";
		Vector nodesToSubmit = null;
		String result = "";
		problemInLoad = false;

		DTOWorkSpaceMst workSpaceMst = docMgmtImpl
				.getWorkSpaceDetail(workSpaceId);
		projectPublishType=workSpaceMst.getProjectType();
		String manualPrevSeqPath = PropertyInfo.getPropInfo()
		.getValue("ManualProjectsServerPath");
		String FosunChanges=PropertyInfo.getPropInfo().getValue("FosunCustomization");
		if(FosunChanges.equalsIgnoreCase("yes")){
			manualPrevSeqPath=manualPrevSeqPath +"/"+workSpaceMst.getClientName();
		}
		if (dontChkBrknLinks != null && dontChkBrknLinks.equals("true")) {
			// Don't search for broken links and directly confirm the submission
			result = "NoBrokenLinksFound";
			rtnVal = "ignore";
		} else {
			// Search for broken links
			result = findBrokenLinks(submissionPath + "/" + currentSeqNumber);
		}
		if (result.equalsIgnoreCase("brokenLinks")) {
			addActionError("Cannot confirm submission...\n \n Broken Links Found.");
			isConfirmLink = true;
			return "brokenLinks";
		} else if (result.equalsIgnoreCase("pathNotFound")) {
			addActionError("Cannot confirm submission...\n \n Submission path not found.");
			extraHtmlCode = "<input type=\"button\" class=\"button\" value=\"Back\" onclick=\"subinfo();\">";
			return SUCCESS;
		} else if (result.equalsIgnoreCase("NoBrokenLinksFound")) {
			/* If no dead/broken links found then confirm the submission */
			DTOWorkSpaceMst dtoWorkspaceMst = null;
			DTOSubmissionMst dtoSubmissionMst = docMgmtImpl
					.getSubmissionInfo(workSpaceId);
			if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("us")) {
				if (dtoSubmissionMst.getRegionalDTDVersion().equals("23")) {

					DTOSubmissionInfoUS23Dtl dtoUs = docMgmtImpl
							.getWorkspaceSubmissionInfoUS23DtlBySubmissionId(submissionInfo__DtlId);

					if (projectPublishType == 'M') {// In case of Manual Mode
						// Publish Project
						String prevSequencePath = manualPrevSeqPath + "/" + dtoUs.getWorkspaceId()+"/" + dtoUs.getCurrentSeqNumber();
						String currentSeqPath=dtoUs.getSubmissionPath()+"/" + dtoUs.getCurrentSeqNumber();
						File manualSequencePath = new File(prevSequencePath);
						if (!manualSequencePath.isDirectory()){
							manualSequencePath.mkdir();
						}
						if (prevSequencePath != null && !dtoUs.getSubmissionPath().equals("")) {
							
							FileManager fileManager = new FileManager();
							fileManager.copyDirectory(new File(currentSeqPath), manualSequencePath);

						}
						nodesToSubmit = getSubmittedNodesForManualMode(
								workSpaceId, submissionInfo__DtlId);
					} else {
						currentSubmissionLabelId = dtoUs.getLabelId();
						// Get workspace Label No.
						Vector allWorkspaceLabels = docMgmtImpl
								.viewLabelUsingWorkspaceId(workSpaceId);
						for (int i = 0; i < allWorkspaceLabels.size(); i++) {
							DTOInternalLabelMst dtoLabel = (DTOInternalLabelMst) allWorkspaceLabels
									.get(i);
							if (dtoLabel.getLabelId().equalsIgnoreCase(
									currentSubmissionLabelId)) {
								labelNo = dtoLabel.getLabelNo();
							}
						}
						nodesToSubmit = docMgmtImpl
								.getNodeForRevisedSubmission(workSpaceId,
										labelNo);
					}

					/*
					 * Update lastPublishedVersion in workspaceMst IMP NOTE :
					 * 'lastPublishedVersion' should be updated after
					 * 'getNodeForRevisedSubmission' is called
					 */
					docMgmtImpl.updatePublishedVersion(workSpaceId);
					dtoWorkspaceMst = docMgmtImpl
							.getWorkSpaceDetail(workSpaceId);
					dtoUs.setSubmissionInfoUS23DtlId(submissionInfo__DtlId);
					dtoUs.setConfirm('Y');// Confirm Selected Submission
					dtoUs.setLastPublishedVersion(dtoWorkspaceMst
							.getLastPublishedVersion());
					dtoUs.setSubmitedBy(userId);
					dtoUs.setModifyBy(userId);
					docMgmtImpl.insertSubmissionInfoUS23Dtl(dtoUs, 2); // Update
					// Published
					// Version
			
				} else {
					DTOSubmissionInfoUSDtl dtoUs = docMgmtImpl
							.getWorkspaceSubmissionInfoUSDtlBySubmissionId(submissionInfo__DtlId);

					if (projectPublishType == 'M') {// In case of Manual Mode
						// Publish Project
						String prevSequencePath = manualPrevSeqPath + "/" + dtoUs.getWorkspaceId()+"/" + dtoUs.getCurrentSeqNumber();
						String currentSeqPath=dtoUs.getSubmissionPath()+"/" + dtoUs.getCurrentSeqNumber();
						File manualSequencePath = new File(prevSequencePath);
						if (!manualSequencePath.isDirectory()){
							manualSequencePath.mkdir();
						}
						if (prevSequencePath != null && !dtoUs.getSubmissionPath().equals("")) {
							
							FileManager fileManager = new FileManager();
							fileManager.copyDirectory(new File(currentSeqPath), manualSequencePath);

						}
						nodesToSubmit = getSubmittedNodesForManualMode(
								workSpaceId, submissionInfo__DtlId);
					} else {
						currentSubmissionLabelId = dtoUs.getLabelId();
						// Get workspace Label No.
						Vector allWorkspaceLabels = docMgmtImpl
								.viewLabelUsingWorkspaceId(workSpaceId);
						for (int i = 0; i < allWorkspaceLabels.size(); i++) {
							DTOInternalLabelMst dtoLabel = (DTOInternalLabelMst) allWorkspaceLabels
									.get(i);
							if (dtoLabel.getLabelId().equalsIgnoreCase(
									currentSubmissionLabelId)) {
								labelNo = dtoLabel.getLabelNo();
							}
						}
						nodesToSubmit = docMgmtImpl
								.getNodeForRevisedSubmission(workSpaceId,
										labelNo);
					}

					/*
					 * Update lastPublishedVersion in workspaceMst IMP NOTE :
					 * 'lastPublishedVersion' should be updated after
					 * 'getNodeForRevisedSubmission' is called
					 */
					docMgmtImpl.updatePublishedVersion(workSpaceId);
					dtoWorkspaceMst = docMgmtImpl
							.getWorkSpaceDetail(workSpaceId);
					dtoUs.setSubmissionInfoUSDtlId(submissionInfo__DtlId);
					dtoUs.setConfirm('Y');// Confirm Selected Submission
					dtoUs.setLastPublishedVersion(dtoWorkspaceMst
							.getLastPublishedVersion());
					dtoUs.setSubmitedBy(userId);
					dtoUs.setModifyBy(userId);
					docMgmtImpl.insertSubmissionInfoUSDtl(dtoUs, 2); // Update
					// Published
					// Version
				}
			} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase("au")){
				


				DTOSubmissionInfoAUDtl dtoUs = docMgmtImpl
						.getWorkspaceSubmissionInfoAUDtlBySubmissionId(submissionInfo__DtlId);

				if (projectPublishType == 'M') {// In case of Manual Mode
					// Publish Project
					String prevSequencePath = manualPrevSeqPath + "/" + dtoUs.getWorkspaceId()+"/" + dtoUs.getCurrentSeqNumber();
					String currentSeqPath=dtoUs.getSubmissionPath()+"/" + dtoUs.getCurrentSeqNumber();
					File manualSequencePath = new File(prevSequencePath);
					if (!manualSequencePath.isDirectory()){
						manualSequencePath.mkdir();
					}
					if (prevSequencePath != null && !dtoUs.getSubmissionPath().equals("")) {
						
						FileManager fileManager = new FileManager();
						fileManager.copyDirectory(new File(currentSeqPath), manualSequencePath);

					}
					nodesToSubmit = getSubmittedNodesForManualMode(
							workSpaceId, submissionInfo__DtlId);
				} else {
					currentSubmissionLabelId = dtoUs.getLabelId();
					// Get workspace Label No.
					Vector allWorkspaceLabels = docMgmtImpl
							.viewLabelUsingWorkspaceId(workSpaceId);
					for (int i = 0; i < allWorkspaceLabels.size(); i++) {
						DTOInternalLabelMst dtoLabel = (DTOInternalLabelMst) allWorkspaceLabels
								.get(i);
						if (dtoLabel.getLabelId().equalsIgnoreCase(
								currentSubmissionLabelId)) {
							labelNo = dtoLabel.getLabelNo();
						}
					}
					nodesToSubmit = docMgmtImpl
							.getNodeForRevisedSubmission(workSpaceId,
									labelNo);
				}

				/*
				 * Update lastPublishedVersion in workspaceMst IMP NOTE :
				 * 'lastPublishedVersion' should be updated after
				 * 'getNodeForRevisedSubmission' is called
				 */
				docMgmtImpl.updatePublishedVersion(workSpaceId);
				dtoWorkspaceMst = docMgmtImpl
						.getWorkSpaceDetail(workSpaceId);
				dtoUs.setSubmissionInfoAUDtlId(submissionInfo__DtlId);
				dtoUs.setConfirm('Y');// Confirm Selected Submission
				dtoUs.setLastPublishedVersion(dtoWorkspaceMst
						.getLastPublishedVersion());
				dtoUs.setSubmitedBy(userId);
				dtoUs.setModifyBy(userId);
				docMgmtImpl.insertSubmissionInfoAUDtl(dtoUs, 2); // Update
				// Published
				// Version
		
			
				
			}else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase(
					"eu")) {
				DTOSubmissionMst prevSubmissionDtl = docMgmtImpl.getSubmissionInfoEURegion(workSpaceId);
				if (prevSubmissionDtl.getEUDtdVersion().equals("13")) {
					DTOSubmissionInfoEUDtl dtoEu = docMgmtImpl
							.getWorkspaceSubmissionInfoEUDtlBySubmissionId(submissionInfo__DtlId);

					if (projectPublishType == 'M') {// In case of Manual Mode
						// Publish Project
						String prevSequencePath = manualPrevSeqPath + "/" + dtoEu.getWorkspaceId()+"/" + dtoEu.getCurrentSeqNumber();
						String currentSeqPath=dtoEu.getSubmissionPath()+"/" + dtoEu.getCurrentSeqNumber();
						File manualSequencePath = new File(prevSequencePath);
						if (!manualSequencePath.isDirectory()){
							manualSequencePath.mkdir();
						}
						if (prevSequencePath != null && !dtoEu.getSubmissionPath().equals("")) {
							
							FileManager fileManager = new FileManager();
							fileManager.copyDirectory(new File(currentSeqPath), manualSequencePath);

						}
						nodesToSubmit = getSubmittedNodesForManualMode(
								workSpaceId, submissionInfo__DtlId);
					} else {
						currentSubmissionLabelId = dtoEu.getLabelId();
						// Get workspace Label No.
						Vector allWorkspaceLabels = docMgmtImpl
								.viewLabelUsingWorkspaceId(workSpaceId);
						for (int i = 0; i < allWorkspaceLabels.size(); i++) {
							DTOInternalLabelMst dtoLabel = (DTOInternalLabelMst) allWorkspaceLabels
									.get(i);
							if (dtoLabel.getLabelId().equalsIgnoreCase(
									currentSubmissionLabelId)) {
								labelNo = dtoLabel.getLabelNo();
							}
						}
						nodesToSubmit = docMgmtImpl
								.getNodeForRevisedSubmission(workSpaceId,
										labelNo);
					}
					/*
					 * Update lastPublishedVersion in workspaceMst IMP NOTE :
					 * 'lastPublishedVersion' should be updated after
					 * 'getNodeForRevisedSubmission' is called
					 */
					docMgmtImpl.updatePublishedVersion(workSpaceId);
					dtoWorkspaceMst = docMgmtImpl
							.getWorkSpaceDetail(workSpaceId);
					dtoEu.setSubmissionInfoEUDtlId(submissionInfo__DtlId);
					dtoEu.setConfirm('Y');// Confirm Selected Submission
					dtoEu.setLastPublishedVersion(dtoWorkspaceMst
							.getLastPublishedVersion());
					dtoEu.setSubmitedBy(userId);
					dtoEu.setModifyBy(userId);

					docMgmtImpl.insertSubmissionInfoEUDtl(dtoEu, 2); // Update
					// Published
					// Version
				}
				// For EU v1.4
				else if (prevSubmissionDtl.getEUDtdVersion().equals("14")) {
					DTOSubmissionInfoEU14Dtl dtoEu = docMgmtImpl
							.getWorkspaceSubmissionInfoEU14DtlBySubmissionId(submissionInfo__DtlId);

					if (projectPublishType == 'M') {// In case of Manual Mode
						// Publish Project
						String prevSequencePath = manualPrevSeqPath + "/" + dtoEu.getWorkspaceId()+"/" + dtoEu.getCurrentSeqNumber();
						String currentSeqPath=dtoEu.getSubmissionPath()+"/" + dtoEu.getCurrentSeqNumber();
						File manualSequencePath = new File(prevSequencePath);
						if (!manualSequencePath.isDirectory()){
							manualSequencePath.mkdir();
						}
						if (prevSequencePath != null && !dtoEu.getSubmissionPath().equals("")) {
							
							FileManager fileManager = new FileManager();
							fileManager.copyDirectory(new File(currentSeqPath), manualSequencePath);

						}
						nodesToSubmit = getSubmittedNodesForManualMode(
								workSpaceId, submissionInfo__DtlId);
					} else {
						currentSubmissionLabelId = dtoEu.getLabelId();
						// Get workspace Label No.
						Vector allWorkspaceLabels = docMgmtImpl
								.viewLabelUsingWorkspaceId(workSpaceId);
						for (int i = 0; i < allWorkspaceLabels.size(); i++) {
							DTOInternalLabelMst dtoLabel = (DTOInternalLabelMst) allWorkspaceLabels
									.get(i);
							if (dtoLabel.getLabelId().equalsIgnoreCase(
									currentSubmissionLabelId)) {
								labelNo = dtoLabel.getLabelNo();
							}
						}
						nodesToSubmit = docMgmtImpl
								.getNodeForRevisedSubmission(workSpaceId,
										labelNo);
					}
					/*
					 * Update lastPublishedVersion in workspaceMst IMP NOTE :
					 * 'lastPublishedVersion' should be updated after
					 * 'getNodeForRevisedSubmission' is called
					 */
					docMgmtImpl.updatePublishedVersion(workSpaceId);
					dtoWorkspaceMst = docMgmtImpl
							.getWorkSpaceDetail(workSpaceId);
					dtoEu.setSubmissionInfoEU14DtlId(submissionInfo__DtlId);
					dtoEu.setConfirm('Y');// Confirm Selected Submission
					dtoEu.setLastPublishedVersion(dtoWorkspaceMst
							.getLastPublishedVersion());
					dtoEu.setSubmitedBy(userId);
					dtoEu.setModifyBy(userId);
					docMgmtImpl.insertSubmissionInfoEU14Dtl(dtoEu, 2); // Update
					// Published
					// Version
				}
				// For EU v2.0
				else if (prevSubmissionDtl.getEUDtdVersion().equals("20")) {
					DTOSubmissionInfoEU20Dtl dtoEu = docMgmtImpl
							.getWorkspaceSubmissionInfoEU20DtlBySubmissionId(submissionInfo__DtlId);

					if (projectPublishType == 'M') {// In case of Manual Mode
						// Publish Project
						String prevSequencePath = manualPrevSeqPath + "/" + dtoEu.getWorkspaceId()+"/" + dtoEu.getCurrentSeqNumber();
						String currentSeqPath=dtoEu.getSubmissionPath()+"/" + dtoEu.getCurrentSeqNumber();
						File manualSequencePath = new File(prevSequencePath);
						if (!manualSequencePath.isDirectory()){
							manualSequencePath.mkdir();
						}
						if (prevSequencePath != null && !dtoEu.getSubmissionPath().equals("")) {
							
							FileManager fileManager = new FileManager();
							fileManager.copyDirectory(new File(currentSeqPath), manualSequencePath);

						}
						
						nodesToSubmit = docMgmtImpl
								.getNodeForRevisedSubmission(workSpaceId,
										labelNo);
					}
					else {
						currentSubmissionLabelId = dtoEu.getLabelId();
						// Get workspace Label No.
						Vector allWorkspaceLabels = docMgmtImpl
								.viewLabelUsingWorkspaceId(workSpaceId);
						for (int i = 0; i < allWorkspaceLabels.size(); i++) {
							DTOInternalLabelMst dtoLabel = (DTOInternalLabelMst) allWorkspaceLabels
									.get(i);
							if (dtoLabel.getLabelId().equalsIgnoreCase(
									currentSubmissionLabelId)) {
								labelNo = dtoLabel.getLabelNo();
							}
						}
						nodesToSubmit = docMgmtImpl
								.getNodeForRevisedSubmission(workSpaceId,
										labelNo);
					}
					/*
					 * Update lastPublishedVersion in workspaceMst IMP NOTE :
					 * 'lastPublishedVersion' should be updated after
					 * 'getNodeForRevisedSubmission' is called
					 */
					docMgmtImpl.updatePublishedVersion(workSpaceId);
					dtoWorkspaceMst = docMgmtImpl
							.getWorkSpaceDetail(workSpaceId);
					dtoEu.setSubmissionInfoEU20DtlId(submissionInfo__DtlId);
					dtoEu.setConfirm('Y');// Confirm Selected Submission
					dtoEu.setLastPublishedVersion(dtoWorkspaceMst
							.getLastPublishedVersion());
					dtoEu.setSubmitedBy(userId);
					dtoEu.setModifyBy(userId);
					docMgmtImpl.insertSubmissionInfoEU20Dtl(dtoEu, 2); // Update
					// Published
					// Version
				}
			} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase(
					"ca")) {
				DTOSubmissionInfoCADtl dtoCA = DocMgmtImpl
						.getWorkspaceSubmissionInfoCADtlBySubmissionId(submissionInfo__DtlId);

				if (projectPublishType == 'M') {// In case of Manual Mode
					// Publish Project
					String prevSequencePath = manualPrevSeqPath + "/" + dtoCA.getWorkspaceId()+"/" + dtoCA.getCurrentSeqNumber();
					String currentSeqPath=dtoCA.getSubmissionPath()+"/" + dtoCA.getCurrentSeqNumber();
					File manualSequencePath = new File(prevSequencePath);
					if (!manualSequencePath.isDirectory()){
						manualSequencePath.mkdir();
					}
					if (prevSequencePath != null && !dtoCA.getSubmissionPath().equals("")) {
						
						FileManager fileManager = new FileManager();
						fileManager.copyDirectory(new File(currentSeqPath), manualSequencePath);

					}
					nodesToSubmit = getSubmittedNodesForManualMode(workSpaceId,
							submissionInfo__DtlId);
				} else {
					currentSubmissionLabelId = dtoCA.getLabelId();
					// Get workspace Label No.
					Vector allWorkspaceLabels = docMgmtImpl
							.viewLabelUsingWorkspaceId(workSpaceId);
					for (int i = 0; i < allWorkspaceLabels.size(); i++) {
						DTOInternalLabelMst dtoLabel = (DTOInternalLabelMst) allWorkspaceLabels
								.get(i);

						if (dtoLabel.getLabelId().equalsIgnoreCase(
								currentSubmissionLabelId)) {

							labelNo = dtoLabel.getLabelNo();
						}
					}
					nodesToSubmit = docMgmtImpl.getNodeForRevisedSubmission(
							workSpaceId, labelNo);
				}
				/*
				 * Update lastPublishedVersion in workspaceMst IMP NOTE :
				 * 'lastPublishedVersion' should be updated after
				 * 'getNodeForRevisedSubmission' is called
				 */
				docMgmtImpl.updatePublishedVersion(workSpaceId);
				dtoWorkspaceMst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
				dtoCA.setSubmissionInfoCADtlId(submissionInfo__DtlId);
				dtoCA.setConfirm('Y'); // Confirm Selected Submission
				dtoCA.setLastPublishedVersion(dtoWorkspaceMst
						.getLastPublishedVersion());
				dtoCA.setSubmitedBy(userId);
				dtoCA.setModifyBy(userId);
				DocMgmtImpl.insertSubmissionInfoCADtl(dtoCA, 2); // Update
				// Published
				// Version
			} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase(
					"gcc")) {

				DTOSubmissionInfoGCCDtl dtoGCC = docMgmtImpl
						.getWorkspaceSubmissionInfoGCCDtlBySubmissionId(submissionInfo__DtlId);

				if (projectPublishType == 'M') {// In case of Manual Mode
					// Publish Project
					String prevSequencePath = manualPrevSeqPath + "/" + dtoGCC.getWorkspaceId()+"/" + dtoGCC.getCurrentSeqNumber();
					String currentSeqPath=dtoGCC.getSubmissionPath()+"/" + dtoGCC.getCurrentSeqNumber();
					File manualSequencePath = new File(prevSequencePath);
					if (!manualSequencePath.isDirectory()){
						manualSequencePath.mkdir();
					}
					if (prevSequencePath != null && !dtoGCC.getSubmissionPath().equals("")) {
						
						FileManager fileManager = new FileManager();
						fileManager.copyDirectory(new File(currentSeqPath), manualSequencePath);

					}
					nodesToSubmit = getSubmittedNodesForManualMode(workSpaceId,
							submissionInfo__DtlId);
				} else {
					currentSubmissionLabelId = dtoGCC.getLabelId();
					// Get workspace Label No.
					Vector allWorkspaceLabels = docMgmtImpl
							.viewLabelUsingWorkspaceId(workSpaceId);
					for (int i = 0; i < allWorkspaceLabels.size(); i++) {
						DTOInternalLabelMst dtoLabel = (DTOInternalLabelMst) allWorkspaceLabels
								.get(i);
						if (dtoLabel.getLabelId().equalsIgnoreCase(
								currentSubmissionLabelId)) {
							labelNo = dtoLabel.getLabelNo();
						}
					}
					nodesToSubmit = docMgmtImpl.getNodeForRevisedSubmission(
							workSpaceId, labelNo);
				}
				/*
				 * Update lastPublishedVersion in workspaceMst IMP NOTE :
				 * 'lastPublishedVersion' should be updated after
				 * 'getNodeForRevisedSubmission' is called
				 */
				docMgmtImpl.updatePublishedVersion(workSpaceId);
				dtoWorkspaceMst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
				dtoGCC.setSubmissionInfoGCCDtlId(submissionInfo__DtlId);
				dtoGCC.setConfirm('Y');// Confirm Selected Submission
				dtoGCC.setLastPublishedVersion(dtoWorkspaceMst
						.getLastPublishedVersion());
				dtoGCC.setSubmitedBy(userId);
				dtoGCC.setModifyBy(userId);
				docMgmtImpl.insertSubmissionInfoGCCDtl(dtoGCC, 2); // Update
				// Published
				// Version

			} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase(
					"za")) {

				DTOSubmissionInfoZADtl dtoZA = docMgmtImpl
						.getWorkspaceSubmissionInfoZADtlBySubmissionId(submissionInfo__DtlId);

				if (projectPublishType == 'M') {// In case of Manual Mode
					// Publish Project
					String prevSequencePath = manualPrevSeqPath + "/" + dtoZA.getWorkspaceId()+"/" + dtoZA.getCurrentSeqNumber();
					String currentSeqPath=dtoZA.getSubmissionPath()+"/" + dtoZA.getCurrentSeqNumber();
					File manualSequencePath = new File(prevSequencePath);
					if (!manualSequencePath.isDirectory()){
						manualSequencePath.mkdir();
					}
					if (prevSequencePath != null && !dtoZA.getSubmissionPath().equals("")) {
						
						FileManager fileManager = new FileManager();
						fileManager.copyDirectory(new File(currentSeqPath), manualSequencePath);

					}
					nodesToSubmit = getSubmittedNodesForManualMode(workSpaceId,
							submissionInfo__DtlId);
				} else {
					currentSubmissionLabelId = dtoZA.getLabelId();
					// Get workspace Label No.
					Vector allWorkspaceLabels = docMgmtImpl
							.viewLabelUsingWorkspaceId(workSpaceId);
					for (int i = 0; i < allWorkspaceLabels.size(); i++) {
						DTOInternalLabelMst dtoLabel = (DTOInternalLabelMst) allWorkspaceLabels
								.get(i);
						if (dtoLabel.getLabelId().equalsIgnoreCase(
								currentSubmissionLabelId)) {
							labelNo = dtoLabel.getLabelNo();
						}
					}
					nodesToSubmit = docMgmtImpl.getNodeForRevisedSubmission(
							workSpaceId, labelNo);
				}
				/*
				 * Update lastPublishedVersion in workspaceMst IMP NOTE :
				 * 'lastPublishedVersion' should be updated after
				 * 'getNodeForRevisedSubmission' is called
				 */
				docMgmtImpl.updatePublishedVersion(workSpaceId);
				dtoWorkspaceMst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
				dtoZA.setSubmissionInfoZADtlId(submissionInfo__DtlId);
				dtoZA.setConfirm('Y');// Confirm Selected Submission
				dtoZA.setLastPublishedVersion(dtoWorkspaceMst
						.getLastPublishedVersion());
				dtoZA.setSubmitedBy(userId);
				dtoZA.setModifyBy(userId);
				docMgmtImpl.insertSubmissionInfoZADtl(dtoZA, 2); // Update
				// Published
				// Version

			} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase(
					"ch")) {

				DTOSubmissionInfoCHDtl dtoCH = docMgmtImpl
						.getWorkspaceSubmissionInfoCHDtlBySubmissionId(submissionInfo__DtlId);

				if (projectPublishType == 'M') {// In case of Manual Mode
					// Publish Project
					String prevSequencePath = manualPrevSeqPath + "/" + dtoCH.getWorkspaceId()+"/" + dtoCH.getCurrentSeqNumber();
					String currentSeqPath=dtoCH.getSubmissionPath()+"/" + dtoCH.getCurrentSeqNumber();
					File manualSequencePath = new File(prevSequencePath);
					if (!manualSequencePath.isDirectory()){
						manualSequencePath.mkdir();
					}
					if (prevSequencePath != null && !dtoCH.getSubmissionPath().equals("")) {
						
						FileManager fileManager = new FileManager();
						fileManager.copyDirectory(new File(currentSeqPath), manualSequencePath);

					}
					nodesToSubmit = getSubmittedNodesForManualMode(workSpaceId,
							submissionInfo__DtlId);
				} else {
					currentSubmissionLabelId = dtoCH.getLabelId();
					// Get workspace Label No.
					Vector allWorkspaceLabels = docMgmtImpl
							.viewLabelUsingWorkspaceId(workSpaceId);
					for (int i = 0; i < allWorkspaceLabels.size(); i++) {
						DTOInternalLabelMst dtoLabel = (DTOInternalLabelMst) allWorkspaceLabels
								.get(i);
						if (dtoLabel.getLabelId().equalsIgnoreCase(
								currentSubmissionLabelId)) {
							labelNo = dtoLabel.getLabelNo();
						}
					}
					nodesToSubmit = docMgmtImpl.getNodeForRevisedSubmission(
							workSpaceId, labelNo);
				}
				/*
				 * Update lastPublishedVersion in workspaceMst IMP NOTE :
				 * 'lastPublishedVersion' should be updated after
				 * 'getNodeForRevisedSubmission' is called
				 */
				docMgmtImpl.updatePublishedVersion(workSpaceId);
				dtoWorkspaceMst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
				dtoCH.setSubmissionInfoCHDtlId(submissionInfo__DtlId);
				dtoCH.setConfirm('Y');// Confirm Selected Submission
				dtoCH.setLastPublishedVersion(dtoWorkspaceMst
						.getLastPublishedVersion());
				dtoCH.setSubmitedBy(userId);
				dtoCH.setModifyBy(userId);
				docMgmtImpl.insertSubmissionInfoCHDtl(dtoCH, 2); // Update
				// Published
				// Version

			}
			else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase(
					"th")) {
		
				DTOSubmissionInfoTHDtl dtoTH = docMgmtImpl
						.getWorkspaceSubmissionInfoTHDtlBySubmissionId(submissionInfo__DtlId);
		
				if (projectPublishType == 'M') {// In case of Manual Mode
					// Publish Project
					String prevSequencePath = manualPrevSeqPath + "/" + dtoTH.getWorkspaceId()+"/" + dtoTH.getCurrentSeqNumber();
					String currentSeqPath=dtoTH.getSubmissionPath()+"/" + dtoTH.getCurrentSeqNumber();
					File manualSequencePath = new File(prevSequencePath);
					if (!manualSequencePath.isDirectory()){
						manualSequencePath.mkdir();
					}
					if (prevSequencePath != null && !dtoTH.getSubmissionPath().equals("")) {
						
						FileManager fileManager = new FileManager();
						fileManager.copyDirectory(new File(currentSeqPath), manualSequencePath);

					}
					nodesToSubmit = getSubmittedNodesForManualMode(workSpaceId,
							submissionInfo__DtlId);
				} else {
					currentSubmissionLabelId = dtoTH.getLabelId();
					// Get workspace Label No.
					Vector allWorkspaceLabels = docMgmtImpl
							.viewLabelUsingWorkspaceId(workSpaceId);
					for (int i = 0; i < allWorkspaceLabels.size(); i++) {
						DTOInternalLabelMst dtoLabel = (DTOInternalLabelMst) allWorkspaceLabels
								.get(i);
						if (dtoLabel.getLabelId().equalsIgnoreCase(
								currentSubmissionLabelId)) {
							labelNo = dtoLabel.getLabelNo();
						}
					}
					nodesToSubmit = docMgmtImpl.getNodeForRevisedSubmission(
							workSpaceId, labelNo);
				}
				/*
				 * Update lastPublishedVersion in workspaceMst IMP NOTE :
				 * 'lastPublishedVersion' should be updated after
				 * 'getNodeForRevisedSubmission' is called
				 */
				docMgmtImpl.updatePublishedVersion(workSpaceId);
				dtoWorkspaceMst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
				dtoTH.setSubmissionInfoTHDtlId(submissionInfo__DtlId);
				dtoTH.setConfirm('Y');// Confirm Selected Submission
				dtoTH.setLastPublishedVersion(dtoWorkspaceMst
						.getLastPublishedVersion());
				dtoTH.setSubmitedBy(userId);
				dtoTH.setModifyBy(userId);
				docMgmtImpl.insertSubmissionInfoTHDtl(dtoTH, 2); // Update
				// Published
				// Version
		
			}

			// Insert into SubmittedWorkspaceNodeDetail
			ArrayList<DTOSubmittedWorkspaceNodeDetail> subNodeDtlLst = new ArrayList<DTOSubmittedWorkspaceNodeDetail>();

			System.out.println("Confirmed==" + nodesToSubmit.size());	
			if (nodesToSubmit.size() > 0 && nodesToSubmit != null) {

				for (Iterator iterator = nodesToSubmit.iterator(); iterator
						.hasNext();) {
					DTOWorkSpaceNodeDetail nDtl = (DTOWorkSpaceNodeDetail) iterator
							.next();
					DTOSubmittedWorkspaceNodeDetail dtoSubNodeDtl = new DTOSubmittedWorkspaceNodeDetail();
					dtoSubNodeDtl.setNodeId(nDtl.getNodeId());
					dtoSubNodeDtl.setIndexId("node-" + nDtl.getNodeId());// Setting
					// the
					// index
					// value
					// as
					// per
					// the
					// ID
					// attribute
					// value
					// of
					// LEAF
					// element
					// is
					// given
					// in
					// the
					// publish
					// logic.
					dtoSubNodeDtl.setWorkspaceId(workSpaceId);
					dtoSubNodeDtl.setSubmissionId(submissionInfo__DtlId);
					dtoSubNodeDtl.setLastPublishVersion(dtoWorkspaceMst
							.getLastPublishedVersion());
					subNodeDtlLst.add(dtoSubNodeDtl);
				}
				docMgmtImpl
						.insertIntoSubmittedWorkspaceNodeDetail(subNodeDtlLst);
			}
		}
		if (rtnVal != null) {
			return rtnVal;
		}
		addActionMessage("Your Submission Has Been Confirmed...");
		extraHtmlCode = "<input type=\"button\" class=\"button\" value=\"Back\" onclick=\"subinfo();\">";
		return SUCCESS;
	}

	public String viewSubmissionDetails() {
		problemInLoad = false;
		submissionDetail = docMgmtImpl.getSubmissionInfo(workSpaceId);
		if (submissionDetail.getCountryRegion().equalsIgnoreCase("us")) {
			if (submissionDetail.getRegionalDTDVersion().equalsIgnoreCase("23")) {
				DTOSubmissionInfoUS23Dtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoUS23DtlBySubmissionId(submissionInfo__DtlId);
				submissionDetail.setSubmissionId(dto
						.getSubmissionInfoUS23DtlId());
				submissionDetail.setCurrentSequenceNumber(dto
						.getCurrentSeqNumber());
				
				submissionDetail.setCountrycode(dto.getCountryCode());
				submissionDetail.setSubmissionpath(dto.getSubmissionPath());
				submissionDetail.setSubmittedOn(dto.getSubmitedOn());
				submissionDetail.setSubmissionType(dto.getSubmissionType());
				submissionDetail.setDateOfSubmission(new java.sql.Date(dto
						.getDateOfSubmission().getTime()));
				submissionDetail.setReletedSeqNo(dto.getRelatedSeqNo());
				submissionDetail.setConfirm(dto.getConfirm());
				submissionDetail.setApplicationNo(dto.getApplicationNo());
				submissionDetail.setCountryName(dto.getCountryName());
				submissionDetail.setAgencyName(dto.getAgencyName());
				submissionDetail.setSubmissionMode(dto.getSubmissionMode());
				submissionDetail.setLabelId(dto.getLabelId());
			} else {
				DTOSubmissionInfoUSDtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoUSDtlBySubmissionId(submissionInfo__DtlId);
				submissionDetail
						.setSubmissionId(dto.getSubmissionInfoUSDtlId());
				submissionDetail.setCurrentSequenceNumber(dto
						.getCurrentSeqNumber());
				submissionDetail.setCountrycode(dto.getCountryCode());
				submissionDetail.setSubmissionpath(dto.getSubmissionPath());
				submissionDetail.setSubmittedOn(dto.getSubmitedOn());
				submissionDetail.setSubmissionType(dto.getSubmissionType());
				submissionDetail.setDateOfSubmission(new java.sql.Date(dto
						.getDateOfSubmission().getTime()));
				submissionDetail.setReletedSeqNo(dto.getRelatedSeqNo());
				submissionDetail.setConfirm(dto.getConfirm());
				submissionDetail.setApplicationNo(dto.getApplicationNo());
				submissionDetail.setCountryName(dto.getCountryName());
				submissionDetail.setAgencyName(dto.getAgencyName());
				submissionDetail.setSubmissionMode(dto.getSubmissionMode());
				submissionDetail.setLabelId(dto.getLabelId());
			}
		}else if (submissionDetail.getCountryRegion().equalsIgnoreCase("au")) { 
			
			DTOSubmissionInfoAUDtl dto = docMgmtImpl
			.getWorkspaceSubmissionInfoAUDtlBySubmissionId(submissionInfo__DtlId);
				submissionDetail
						.setSubmissionId(dto.getSubmissionInfoAUDtlId());
				submissionDetail.setCurrentSequenceNumber(dto
						.getCurrentSeqNumber());
				submissionDetail.setCountrycode(dto.getCountryCode());
				submissionDetail.setSubmissionpath(dto.getSubmissionPath());
				submissionDetail.setSubmittedOn(dto.getSubmitedOn());
				submissionDetail.setSequenceDescription(dto.getSequenceDescription());
				
				submissionDetail.setReletedSeqNo(dto.getRelatedSeqNo());
				submissionDetail.setConfirm(dto.getConfirm());
				submissionDetail.seteSubmissionId(dto.geteSubmissionId());
				submissionDetail.setCountryName(dto.getCountryName());
				submissionDetail.setAgencyName(dto.getAgencyName());
				submissionDetail.setSubmissionMode(dto.getSubmissionMode());
				submissionDetail.setLabelId(dto.getLabelId());
			
		}
		else if (submissionDetail.getCountryRegion().equalsIgnoreCase("th")) { 
			
			DTOSubmissionInfoTHDtl dto = docMgmtImpl
			.getWorkspaceSubmissionInfoTHDtlBySubmissionId(submissionInfo__DtlId);
				submissionDetail
						.setSubmissionId(dto.getSubmissionInfoTHDtlId());
				submissionDetail.setCurrentSequenceNumber(dto
						.getCurrentSeqNumber());
				submissionDetail.setCountrycode(dto.getCountryCode());
				submissionDetail.setSubmissionpath(dto.getSubmissionPath());
				submissionDetail.setSubmittedOn(dto.getSubmitedOn());
				submissionDetail.setDateOfSubmission(new java.sql.Date(dto
						.getDateOfSubmission().getTime()));
				submissionDetail.setSequenceDescription(dto.getSequenceDescription());
				submissionDetail.setEmail(dto.getEmail());
				submissionDetail.setReletedSeqNo(dto.getRelatedSeqNo());
				submissionDetail.setConfirm(dto.getConfirm());
				submissionDetail.seteSubmissionId(dto.geteSubmissionId());
				submissionDetail.setCountryName(dto.getCountryName());
				submissionDetail.setAgencyName(dto.getAgencyName());
				
				submissionDetail.setLabelId(dto.getLabelId());
			
		}
		else if (submissionDetail.getCountryRegion().equalsIgnoreCase("eu")) {
			DTOSubmissionMst prevSubmissionDtl = docMgmtImpl.getSubmissionInfoEURegion(workSpaceId);
			if (prevSubmissionDtl.getEUDtdVersion().equals("13")) {
				DTOSubmissionInfoEUDtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoEUDtlBySubmissionId(submissionInfo__DtlId);
				submissionDetail
						.setSubmissionId(dto.getSubmissionInfoEUDtlId());
				submissionDetail.setCurrentSequenceNumber(dto
						.getCurrentSeqNumber());
				submissionDetail.setCountrycode(dto.getCountryCode());
				submissionDetail.setSubmissionpath(dto.getSubmissionPath());
				submissionDetail.setSubmittedOn(dto.getSubmitedOn());
				submissionDetail.setSubmissionType(dto.getSubmissionType());
				submissionDetail.setDateOfSubmission(new java.sql.Date(dto
						.getDateOfSubmission().getTime()));
				submissionDetail.setReletedSeqNo(dto.getRelatedSeqNo());
				submissionDetail.setConfirm(dto.getConfirm());
				submissionDetail.setApplicationNo(dto.getApplicationNo());
				submissionDetail.setCountryName(dto.getCountryName());
				submissionDetail.setAgencyName(dto.getAgencyName());
				submissionDetail.setSubmissionMode(dto.getSubmissionMode());
				submissionDetail.setLabelId(dto.getLabelId());

				/*
				 * Do not Take 'Submission Description' from SubmissionInfoEUMst
				 * But from 'SubmissionInfoEUSubDtl'
				 */
				ArrayList<DTOSubmissionInfoEUSubDtl> cmsDtl = docMgmtImpl
						.getWorkspaceCMSSubmissionInfo(submissionInfo__DtlId,"13");
				if (cmsDtl.size() == 0) {
					DTOSubmissionInfoEUSubDtl rmsDtl = docMgmtImpl
							.getWorkspaceRMSSubmissionInfo(submissionInfo__DtlId);
					String submissionDescription = rmsDtl
							.getSubmissionDescription();
					submissionDetail
							.setSubmissionDescription(submissionDescription);
				} else {
					String submissionDescription = cmsDtl.get(0)
							.getSubmissionDescription();
					submissionDetail
							.setSubmissionDescription(submissionDescription);
				}
			} else if (prevSubmissionDtl.getEUDtdVersion().equals("14")) {
				DTOSubmissionInfoEU14Dtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoEU14DtlBySubmissionId(submissionInfo__DtlId);
				submissionDetail.setSubmissionId(dto
						.getSubmissionInfoEU14DtlId());
				submissionDetail.setCurrentSequenceNumber(dto
						.getCurrentSeqNumber());
				submissionDetail.setCountrycode(dto.getCountryCode());
				submissionDetail.setSubmissionpath(dto.getSubmissionPath());
				submissionDetail.setSubmittedOn(dto.getSubmitedOn());
				submissionDetail.setSubmissionType(dto.getSubmissionType());
				submissionDetail.setDateOfSubmission(new java.sql.Date(dto
						.getDateOfSubmission().getTime()));
				submissionDetail.setReletedSeqNo(dto.getRelatedSeqNo());
				submissionDetail.setConfirm(dto.getConfirm());
				submissionDetail.setApplicationNo(dto.getTrackingNo());
				submissionDetail.setCountryName(dto.getCountryName());
				submissionDetail.setAgencyName(dto.getAgencyName());
				submissionDetail.setSubmissionMode(dto.getSubmissionMode());
				submissionDetail.setLabelId(dto.getLabelId());

				/*
				 * Do not Take 'Submission Description' from SubmissionInfoEUMst
				 * But from 'SubmissionInfoEUSubDtl'
				 */
				ArrayList<DTOSubmissionInfoEUSubDtl> cmsDtl = docMgmtImpl
						.getWorkspaceCMSSubmissionInfo(submissionInfo__DtlId,"14");
				if (cmsDtl.size() == 0) {
					DTOSubmissionInfoEU14SubDtl rmsDtl = docMgmtImpl
							.getWorkspaceRMSSubmissionInfoEU14(submissionInfo__DtlId);
					String submissionDescription = rmsDtl
							.getSubmissionDescription();
					submissionDetail
							.setSubmissionDescription(submissionDescription);
				} else {
					String submissionDescription = cmsDtl.get(0)
							.getSubmissionDescription();
					submissionDetail
							.setSubmissionDescription(submissionDescription);
				}
			} else if (prevSubmissionDtl.getEUDtdVersion().equals("20")) {
				DTOSubmissionInfoEU20Dtl dto = docMgmtImpl
						.getWorkspaceSubmissionInfoEU20DtlBySubmissionId(submissionInfo__DtlId);
				submissionDetail.setSubmissionId(dto
						.getSubmissionInfoEU20DtlId());
				submissionDetail.setCurrentSequenceNumber(dto
						.getCurrentSeqNumber());
				submissionDetail.setCountrycode(dto.getCountryCode());
				submissionDetail.setSubmissionpath(dto.getSubmissionPath());
				submissionDetail.setSubmittedOn(dto.getSubmitedOn());
				submissionDetail.setSubmissionType(dto.getSubmissionType());
				submissionDetail.setDateOfSubmission(new java.sql.Date(dto
						.getDateOfSubmission().getTime()));
				submissionDetail.setReletedSeqNo(dto.getRelatedSeqNo());
				submissionDetail.setConfirm(dto.getConfirm());
				submissionDetail.setApplicationNo(dto.getTrackingNo());
				submissionDetail.setCountryName(dto.getCountryName());
				submissionDetail.setAgencyName(dto.getAgencyName());
				submissionDetail.setSubmissionMode(dto.getSubmissionMode());
				submissionDetail.setLabelId(dto.getLabelId());
				submissionDetail.setSubmissionUnitType(dto.getSubmissionUnitType());

				ArrayList<DTOSubmissionInfoEUSubDtl> cmsDtl = docMgmtImpl
						.getWorkspaceCMSSubmissionInfo(submissionInfo__DtlId,"20");
				if (cmsDtl.size() == 0) {
					DTOSubmissionInfoEU20SubDtl rmsDtl = docMgmtImpl
							.getWorkspaceRMSSubmissionInfoEU20(submissionInfo__DtlId);
					String submissionDescription = rmsDtl
							.getSubmissionDescription();
					submissionDetail
							.setSubmissionDescription(submissionDescription);
				} else {
					String submissionDescription = cmsDtl.get(0)
							.getSubmissionDescription();
					submissionDetail
							.setSubmissionDescription(submissionDescription);
				}
			}
		} else if (submissionDetail.getCountryRegion().equalsIgnoreCase("ca")) {
			DTOSubmissionInfoCADtl dto = DocMgmtImpl
					.getWorkspaceSubmissionInfoCADtlBySubmissionId(submissionInfo__DtlId);
			submissionDetail.setSubmissionId(dto.getSubmissionInfoCADtlId());
			submissionDetail
					.setCurrentSequenceNumber(dto.getCurrentSeqNumber());
			submissionDetail.setCountrycode(dto.getCountryCode());
			submissionDetail.setSubmissionpath(dto.getSubmissionPath());
			submissionDetail.setSubmittedOn(dto.getSubmitedOn());
			//submissionDetail.setSubmissionType(dto.getSubmissionType());
			//submissionDetail.setDateOfSubmission(new java.sql.Date(dto.getDateOfSubmission().getTime()));
			submissionDetail.setReletedSeqNo(dto.getRelatedSeqNo());
			submissionDetail.setConfirm(dto.getConfirm());
			//submissionDetail.setApplicationNo(dto.getApplicationNo());
			submissionDetail.setCountryName(dto.getCountryName());
			submissionDetail.setAgencyName(dto.getAgencyName());
			//submissionDetail.setSubmissionMode(dto.getSubmissionMode());
			submissionDetail.setLabelId(dto.getLabelId());
		} else if (submissionDetail.getCountryRegion().equals("gcc")) {
			DTOSubmissionInfoGCCDtl dto = docMgmtImpl
					.getWorkspaceSubmissionInfoGCCDtlBySubmissionId(submissionInfo__DtlId);
			submissionDetail.setSubmissionId(dto.getSubmissionInfoGCCDtlId());
			submissionDetail
					.setCurrentSequenceNumber(dto.getCurrentSeqNumber());
			submissionDetail.setCountrycode(dto.getCountryCode());
			submissionDetail.setSubmissionpath(dto.getSubmissionPath());
			submissionDetail.setSubmittedOn(dto.getSubmitedOn());
			submissionDetail.setSubmissionType(dto.getSubmissionType());
			submissionDetail.setDateOfSubmission(new java.sql.Date(dto
					.getDateOfSubmission().getTime()));
			submissionDetail.setReletedSeqNo(dto.getRelatedSeqNo());
			submissionDetail.setConfirm(dto.getConfirm());
			submissionDetail.setApplicationNo(dto.getApplicationNo());
			submissionDetail.setCountryName(dto.getCountryName());
			submissionDetail.setAgencyName(dto.getAgencyName());
			submissionDetail.setSubmissionMode(dto.getSubmissionMode());
			submissionDetail.setLabelId(dto.getLabelId());
			
			submissionDetail.setSubmissionUnitType(dto.getSubmissionType());

			ArrayList<DTOSubmissionInfoGCCSubDtl> cmsDtl = docMgmtImpl
					.getWorkspaceCMSSubmissionInfoGCC(submissionInfo__DtlId);
			if (cmsDtl.size() == 0) {
				DTOSubmissionInfoGCCSubDtl rmsDtl = docMgmtImpl
						.getWorkspaceRMSSubmissionInfoGCC(submissionInfo__DtlId);
				String submissionDescription = rmsDtl
						.getSubmissionDescription();
				submissionDetail
						.setSubmissionDescription(submissionDescription);
			} else {
				String submissionDescription = cmsDtl.get(0)
						.getSubmissionDescription();
				submissionDetail
						.setSubmissionDescription(submissionDescription);
			}
		} else if (submissionDetail.getCountryRegion().equals("za")) {

			DTOSubmissionInfoZADtl dto = docMgmtImpl
					.getWorkspaceSubmissionInfoZADtlBySubmissionId(submissionInfo__DtlId);
			submissionDetail.setSubmissionId(dto.getSubmissionInfoZADtlId());
			submissionDetail
					.setCurrentSequenceNumber(dto.getCurrentSeqNumber());
			submissionDetail.setCountrycode(dto.getCountryCode());
			submissionDetail.setSubmissionpath(dto.getSubmissionPath());
			submissionDetail.setSubmittedOn(dto.getSubmitedOn());
			submissionDetail.setSubmissionType(dto.getSubmissionType());

			submissionDetail.setDateOfSubmission(new java.sql.Date(dto
					.getDateOfSubmission().getTime()));
			submissionDetail.setReletedSeqNo(dto.getRelatedSeqNo());
			submissionDetail.setConfirm(dto.getConfirm());
			submissionDetail.setApplicationNo(dto.getApplicationNo());
			submissionDetail.setCountryName(dto.getCountryName());
			submissionDetail.setAgencyName(dto.getAgencyName());
			submissionDetail.setSubmissionMode(dto.getSubmissionMode());
			submissionDetail.setLabelId(dto.getLabelId());

		} else if (submissionDetail.getCountryRegion().equals("ch")) {

			DTOSubmissionInfoCHDtl dto = docMgmtImpl
					.getWorkspaceSubmissionInfoCHDtlBySubmissionId(submissionInfo__DtlId);
			submissionDetail.setSubmissionId(dto.getSubmissionInfoCHDtlId());
			submissionDetail
					.setCurrentSequenceNumber(dto.getCurrentSeqNumber());
			submissionDetail.setCountrycode(dto.getCountryCode());
			submissionDetail.setSubmissionpath(dto.getSubmissionPath());
			submissionDetail.setSubmittedOn(dto.getSubmitedOn());
			submissionDetail.setSubmissionType(dto.getSubmissionType());

			submissionDetail.setDateOfSubmission(new java.sql.Date(dto
					.getDateOfSubmission().getTime()));
			submissionDetail.setReletedSeqNo(dto.getRelatedSeqNo());
			submissionDetail.setConfirm(dto.getConfirm());
			submissionDetail.setApplicationNo(dto.getApplicationNo());
			submissionDetail.setCountryName(dto.getCountryName());
			submissionDetail.setAgencyName(dto.getAgencyName());
			submissionDetail.setSubmissionMode(dto.getSubmissionMode());
			submissionDetail.setLabelId(dto.getLabelId());

		}

		return SUCCESS;
	}

	public String checkLinks() {
		String result = findBrokenLinks(submissionPath + "/" + currentSeqNumber);
		if (result.equalsIgnoreCase("brokenLinks")) {
			addActionError("Broken Links Found In Your Submission.");
			return "brokenLinks";
		} else if (result.equalsIgnoreCase("pathNotFound")) {
			addActionError("Submission path not found.");
			extraHtmlCode = "<input type=\"button\" class=\"button\" value=\"Back\" onclick=\"subinfo();\">";
			return SUCCESS;
		} else if (result.equalsIgnoreCase("NoBrokenLinksFound")) {
			addActionMessage("No Broken Links Found In Selected Submission Sequence.");
			extraHtmlCode = "<input type=\"button\" class=\"button\" value=\"Back\" onclick=\"subinfo();\">";
		}
		problemInLoad = false;
		return SUCCESS;

	}

	public String findBrokenLinks(String sequenceNoFolderPath) {
		/* Usage : Finds broken links from the given submission */
		LinkManager linkmanager = new LinkManager();
		// sequenceNoFolderPath = submissionPath +"/"+ currentSeqNumber;
		File dir = new File(sequenceNoFolderPath);
		if (dir.exists()) {
			filesContainingBrokenLinks = linkmanager
					.searchFilesContainingBrokenLinks(sequenceNoFolderPath);
			if (filesContainingBrokenLinks.size() != 0) {
				for (int i = 0; i < filesContainingBrokenLinks.size(); i++) {
					String filePath = (String) filesContainingBrokenLinks
							.get(i);
					filePath = filePath.replace("\\", "/");
					String relativeFilePath = filePath
							.substring(sequenceNoFolderPath.length() + 1);

					String fileName = filePath.substring(filePath
							.lastIndexOf("/") + 1);
					String[] temp = { fileName, relativeFilePath, filePath };
					filesContainingBrokenLinks.setElementAt(temp, i);
				}
				return "brokenLinks";
			}
		} else {
			return "pathNotFound";
		}
		problemInLoad = false;
		return "NoBrokenLinksFound";
	}

	public String extractSeq() {
		String msg = "Extracted";
		ArrayList<DTOManualModeSeqZipDtl> seqZipFiles = DocMgmtImpl
				.getManualModeSeqZipDtl(workSpaceId);
		for (DTOManualModeSeqZipDtl manualModeSeqZipDtl : seqZipFiles) {

			File file = new File(manualModeSeqZipDtl.getZipFilePath() + "/"
					+ manualModeSeqZipDtl.getSeqNo() + "/"
					+ manualModeSeqZipDtl.getZipFileName());
			if (!ZipManager.unZip(file, submissionPath)) {
				msg = "Failed!";
			}
		}
		inputStream = new ByteArrayInputStream(msg.getBytes());
		problemInLoad = false;
		return SUCCESS;
	}

	public String validateEctdSubSeq() {

		extraHtmlCode = "<div align=\"center\"><input type=\"button\" class=\"button\" value=\"Back\" onclick=\"subinfo();\"></div>";
		File indexxml = new File(submissionPath + "/" + currentSeqNumber
				+ "/index.xml");
		EctdXmlReader xmlReader = new EctdXmlReader(indexxml);
		DTOWorkSpaceMst workSpaceMst = xmlReader.readEctdIndexXml(null);
		if (workSpaceMst != null) {
			ectdErrorList = workSpaceMst.getEctdErrorList();
			if (ectdErrorList != null && ectdErrorList.size() > 0) {

				if (countryRegion.equals("za")) // Added for temporary purpose
				{
					addActionMessage("No Errors Found...");
					problemInLoad = false;

				} else {
					addActionError("Following Problems found in the selected submission sequence.");
					return ERROR;
				}
			} else {
				addActionMessage("No Errors Found...");
			}
		} else {
			addActionError("Error occured while validating...");
		}

		problemInLoad = false;
		return SUCCESS;
	}

	public DTOInternalLabelMst createWorkspaceLabel(String workspaceId,
			int userId) {

		DTOInternalLabelMst maxLabel = docMgmtImpl
				.getMaxWorkspaceLabel(workspaceId);
		String maxLabelId = maxLabel.getLabelId();
		// Increment labelId by 1, e.g. L0001 to L0002
		String newLabelId = maxLabelId.substring(maxLabelId.length() - 4);
		int newLabelIdInt = Integer.parseInt(newLabelId);
		newLabelIdInt = newLabelIdInt + 1;
		newLabelId = "000" + newLabelIdInt;

		newLabelId = maxLabelId.substring(0, maxLabelId.length() - 4)
				+ newLabelId.substring(newLabelId.length() - 4);
		// System.out.println(newLabelId);
		DTOInternalLabelMst dto = new DTOInternalLabelMst();
		dto.setWorkspaceId(workspaceId);
		dto.setLabelId(newLabelId);// labelId
		dto.setRemark("");// labelRemark
		dto.setModifyBy(userId);
		docMgmtImpl.createInternalLabel(dto);

		maxLabel = docMgmtImpl.getMaxWorkspaceLabel(workspaceId);

		return maxLabel;

	}

	public void publishWorkspaceSubmission(DTOSubmissionMst dtoSubmissionMst,
			DTOInternalLabelMst dtoLabel, String currentSeqNumber,
			String relatedSeqNo, String publishDestinationPath,
			String sourcePath, String submissionType) {

		PublishAttrForm publishForm = new PublishAttrForm();

		publishForm.setSubmissionId(dtoSubmissionMst.getSubmissionId());// for
		// 'EU'
		// submission
		publishForm.setWsId(workSpaceId);
		publishForm.setLabelNo(dtoLabel.getLabelNo());
		publishForm.setLabelId(dtoLabel.getLabelId());

		publishForm.setSubmissionFlag(dtoSubmissionMst.getCountryRegion());
		publishForm.setSeqNumber(currentSeqNumber);
		publishForm.setPublishDestinationPath(publishDestinationPath);// Changed
		// for
		// New
		// Submission
		// Path
		publishForm.setRecompile(recompile);
		publishForm.setApplicationNumber(dtoSubmissionMst.getApplicationNo());
		publishForm.setAgencyName(dtoSubmissionMst.getAgencyName());
		publishForm.setCompanyName(dtoSubmissionMst.getCompanyName());
		publishForm.setApplicationType(dtoSubmissionMst.getApplicationType());
		// publishForm.setClientName(clientName);
		publishForm.setProcedureType(dtoSubmissionMst.getProcedureType());
		publishForm.setProductType(dtoSubmissionMst.getProductType());

		publishForm.setSubmissionType_us(submissionType);
		publishForm.setSubmissionType(submissionType);

		publishForm.setProdName(dtoSubmissionMst.getProductName());

		publishForm.setRelatedSeqNumber(relatedSeqNo);

		publishForm.setCountry(dtoSubmissionMst.getCountryCodeName()); // For
		// 'eu'
		// submission
		publishForm.setApplicant(dtoSubmissionMst.getApplicant());
		publishForm.setAtc(dtoSubmissionMst.getAtc());
		publishForm.setProcedureType(dtoSubmissionMst.getProcedureType());
		publishForm.setSubmissionType_eu(submissionType);
		publishForm.setInventedName(dtoSubmissionMst.getInventedName());
		publishForm.setInn(dtoSubmissionMst.getInn());
		publishForm.setSubmissionDescription(dtoSubmissionMst
				.getSubmissionDescription());
		
		publishForm.setSubmissionMode(subVariationMode);// For EU v1.4
		publishForm.setHighLvlNo(dtoSubmissionMst.getHighLvlNo());

		publishForm.setAtc(dtoSubmissionMst.getAtc());

		publishForm.setSubmissionType_ca(submissionType);// For 'ca' submission
		publishForm.setApplicant(dtoSubmissionMst.getApplicant());
		publishForm.setProdName(dtoSubmissionMst.getProductName());

		publishForm.setApplicantContactList(applicantContactDetails);
		publishForm.setSubmissionSubType(subSubType);
		publishForm.setApplicationId(dtoSubmissionMst.getApplicationId());
		
		publishForm.seteSubmissionId(dtoSubmissionMst.geteSubmissionId());
		publishForm.setAAN(dtoSubmissionMst.getAAN());
		publishForm.setRegActLead(dtoSubmissionMst.getRegActLead());
		publishForm.setLicensee(dtoSubmissionMst.getLicensee());
		publishForm.setLicenseeName(dtoSubmissionMst.getLicenseeName());
		publishForm.setLicenseeType(dtoSubmissionMst.getLicenseeType());
		publishForm.setDossierIdentifier(dtoSubmissionMst.getDossierIdentifier());
		publishForm.setDossierType(dtoSubmissionMst.getDossierType());
		publishForm.setRegionalDtdVersion(dtoSubmissionMst.getRegionalDTDVersion());
		System.out.println("regionalDTDVersion set in publishForm::"+regionalDTDVersion);
		publishForm.setEUDTDVersion(dtoSubmissionMst.getEUDtdVersion());

		if (isRMSSelected == 'Y' || isRMSSelected == 'N') {
			publishForm.setRMSSelected(isRMSSelected);
			publishForm.setAddTT(addTT);
		}
		
		DTOWorkSpaceMst workSpaceMst = docMgmtImpl
				.getWorkSpaceDetail(workSpaceId);

		char projectType = workSpaceMst.getProjectType();
		String wsDesc = workSpaceMst.getWorkSpaceDesc();
		if (recompile) {
			// leafIds=
			if (projectType == 'M') {
				ArrayList<DTOSubmissionInfoForManualMode> arrLeafIds = docMgmtImpl
						.getLeafIdsForManualMode(workSpaceId, dtoSubmissionMst
								.getSubmissionId());
				if (arrLeafIds.size() > 0) {
					int j = 0;
					leafIds = new int[arrLeafIds.size()];
					for (int i = 0; i < arrLeafIds.size(); i++) {
						leafIds[j++] = arrLeafIds.get(i).getNodeId();
					}
				}
			}
		}
		// WorkspacePublishService workspacePublishService = new
		// WorkspacePublishService();
		HttpServletRequest request = ServletActionContext.getRequest();

		if (projectType == 'P' || projectType == 'M') {

			if (dtoSubmissionMst.getCountryRegion().equals("eu")
					&& dtoSubmissionMst.getRegionalDTDVersion().equals("14")) {

				WorkspacePublishService_EU14 workspacePublishService_EU14 = new WorkspacePublishService_EU14();
				workspacePublishService_EU14.setProjectPublishType(projectType);
				if (projectType == 'M') {
					request.setAttribute("dos", dos);
					workspacePublishService_EU14.setLeafIds(leafIds);
				}
				publishForm.setApplicationNumber(dtoSubmissionMst
						.getTrackingNo());
				workspacePublishService_EU14.workspacePublish(workSpaceId,
						publishForm, request, wsDesc);

			} else if (dtoSubmissionMst.getCountryRegion().equals("eu")
					&& (dtoSubmissionMst.getRegionalDTDVersion().equals("20") || dtoSubmissionMst.getRegionalDTDVersion().equals("301"))) {

				WorkspacePublishService_EU20 workspacePublishService_EU20 = new WorkspacePublishService_EU20();
				workspacePublishService_EU20.setProjectPublishType(projectType);
				if (projectType == 'M') {
					request.setAttribute("dos", dos);
					workspacePublishService_EU20.setLeafIds(leafIds);
				}
				if(dtoSubmissionMst.getRegionalDTDVersion().equals("301")){
					publishForm.setSubmissionUnitType(submissionUnitType_eu_301);
					publishForm.setUuid(dtoSubmissionMst.getUuid());
				}
				
				publishForm.setApplicationNumber(dtoSubmissionMst
						.getTrackingNo());
				workspacePublishService_EU20.workspacePublish(workSpaceId,
						publishForm, request, wsDesc);

			} else if (dtoSubmissionMst.getCountryRegion().equalsIgnoreCase(
					"us")
					&& dtoSubmissionMst.getRegionalDTDVersion().equals("23")) {

				WorkspacePublishServiceUS23 workspacepublishServiceUS23 = new WorkspacePublishServiceUS23();

				/** Set Project Publish Type */
				workspacepublishServiceUS23.setProjectPublishType(projectType);
				if (projectType == 'M') {
					request.setAttribute("dos", dos);
					workspacepublishServiceUS23.setLeafIds(leafIds);
				}
				publishForm.setSubmissionId(currentSeqNumber);
				publishForm.setCrossRefAppType(applicationTypeCode);
				publishForm.setCrossReferenceNumber(crossReferenceNumber);
				publishForm
						.setSuppEffectiveDateType(supplementEffectiveDateTypeCode);

				workspacepublishServiceUS23.workspacePublish(workSpaceId,
						publishForm, request, wsDesc);

			}

			else if (dtoSubmissionMst.getCountryRegion().equals("us")
					|| (dtoSubmissionMst.getCountryRegion().equals("eu") && dtoSubmissionMst
							.getRegionalDTDVersion().equals("13"))) {

				WorkspaceRevisedPublishService workspaceRevisedPublishService = new WorkspaceRevisedPublishService();
				/** Set Project Publish Type */
				workspaceRevisedPublishService
						.setProjectPublishType(projectType);
				if (projectType == 'M') {
					request.setAttribute("dos", dos);
					workspaceRevisedPublishService.setLeafIds(leafIds);
				}
				workspaceRevisedPublishService.workspacePublish(workSpaceId,
						publishForm, request, wsDesc);

			} else if (dtoSubmissionMst.getCountryRegion().equals("ca")) {
				WorkspacePublishService_CA workspacePublishService_ca = new WorkspacePublishService_CA();
				workspacePublishService_ca.setProjectPublishType(projectType);
				if (projectType == 'M') {
					request.setAttribute("dos", dos);

					workspacePublishService_ca.setLeafIds(leafIds);
				}
				publishForm.setRegActType(regActType);
				publishForm.setSeqDescFlag(seqDescFlag);
				publishForm.setSequenceDescription(submissionType);
				publishForm.setSequenceDescriptionValue(descriptionValue);
				
				workspacePublishService_ca.workspacePublish(workSpaceId,
						publishForm, request, wsDesc);
			} else if (dtoSubmissionMst.getCountryRegion().equals("gcc")) {

				WorkspacePublishService_GCC workspacePublishService_GCC = new WorkspacePublishService_GCC();
				workspacePublishService_GCC.setProjectPublishType(projectType);
				if (projectType == 'M') {
					request.setAttribute("dos", dos);
					workspacePublishService_GCC.setLeafIds(leafIds);
				}
				publishForm.setSubmissionUnitType(submissionUnitType);
				publishForm.setApplicationNumber(dtoSubmissionMst
						.getTrackingNo());
				workspacePublishService_GCC.workspacePublish(workSpaceId,
						publishForm, request, wsDesc);

			} else if (dtoSubmissionMst.getCountryRegion().equals("za")) {

				WorkspacePublishService_ZA workspacePublishService_ZA = new WorkspacePublishService_ZA();
				workspacePublishService_ZA.setProjectPublishType(projectType);
				if (projectType == 'M') {
					request.setAttribute("dos", dos);
					workspacePublishService_ZA.setLeafIds(leafIds);
				}
				
				publishForm.setApplicationNumber(dtoSubmissionMst.getApplicationNo());
				publishForm.setDosageForm(dtoSubmissionMst.getDosageForm());

				publishForm.setInventedName(dtoSubmissionMst.getInventedName());
				publishForm.setEfficacy(dtoSubmissionMst.getEfficacy());
				publishForm.setEfficacyDescription(dtoSubmissionMst
						.getEfficacyDescription());

				publishForm.setPropriteryNames(dtoSubmissionMst
						.getPropriateryName());

				workspacePublishService_ZA.workspacePublish(workSpaceId,
						publishForm, request, wsDesc);

			}else if (dtoSubmissionMst.getCountryRegion().equals("au")) {

				WorkspacePublishService_AU workspacePublishService_Au = new WorkspacePublishService_AU();
				workspacePublishService_Au.setProjectPublishType(projectType);
				if (projectType == 'M') {
					request.setAttribute("dos", dos);
					workspacePublishService_Au.setLeafIds(leafIds);
				}
				
				publishForm.setSequenceType(seqTypes);
				publishForm.setSequenceDescription(seqDesc);
				publishForm.setSequenceDescriptionValue(descriptionValue);
				publishForm.setArtgNumber(artgNumber);
				
				workspacePublishService_Au.workspacePublish(workSpaceId,
						publishForm, request, wsDesc);

			} 
			else if (dtoSubmissionMst.getCountryRegion().equals("th")) {

				WorkspacePublishService_TH workspacePublishService_Th = new WorkspacePublishService_TH();
				workspacePublishService_Th.setProjectPublishType(projectType);
				if (projectType == 'M') {
					request.setAttribute("dos", dos);
					workspacePublishService_Th.setLeafIds(leafIds);
				}
				
				publishForm.setSequenceType(seqTypes_th);
				publishForm.setSequenceDescription(seqDesc_th);
				publishForm.setEmail(email);
				workspacePublishService_Th.workspacePublish(workSpaceId,
						publishForm, request, wsDesc);

			} 
			else if (dtoSubmissionMst.getCountryRegion().equals("ch")) {

				WorkspacePublishService_CH workspacePublishService_CH = new WorkspacePublishService_CH();
				workspacePublishService_CH.setProjectPublishType(projectType);
				if (projectType == 'M') {
					request.setAttribute("dos", dos);
					workspacePublishService_CH.setLeafIds(leafIds);
				}
				publishForm.setApplicationNumber(dtoSubmissionMst
						.getApplicationNo());

				publishForm.setInventedName(dtoSubmissionMst.getInventedName());
				publishForm.setGanelicForm(dtoSubmissionMst.getGanelicForm());

				publishForm.setDmfHolder(dtoSubmissionMst.getDmfHolder());
				publishForm.setPmfHolder(dtoSubmissionMst.getPmfHolder());
				publishForm.setDmfNumber(dtoSubmissionMst.getDmfNumber());
				publishForm.setPmfNumber(dtoSubmissionMst.getPmfNumber());
				publishForm.setParagraph13(dtoSubmissionMst.getParagraph13());

				workspacePublishService_CH.workspacePublish(workSpaceId,
						publishForm, request, wsDesc);

			}
			if(projectType != 'M'){
				// Copy Previous Submission Sequence Folders
				if (sourcePath != null && !sourcePath.equals("")) {
	
					FileManager fileManager = new FileManager();
					fileManager.copyDirectory(new File(sourcePath), new File(
							publishDestinationPath));
	
				}
			}
		} else {
			// workspacePublishService.workspaceHtml(workSpaceId,publishForm,wsDesc);
		}
	}
	
	private Vector getSubmittedNodesForManualMode(String wsId,
			String submissionId) {
		ArrayList<DTOSubmissionInfoForManualMode> subNodes = DocMgmtImpl
				.getSubmissionInfoForManualMode(wsId, submissionId);
		Vector nodesToSubmit = new Vector();
		for (DTOSubmissionInfoForManualMode submissionInfoForManualMode : subNodes) {
			DTOWorkSpaceNodeDetail dtoNode = new DTOWorkSpaceNodeDetail();
			dtoNode.setNodeId(submissionInfoForManualMode.getNodeId());
			nodesToSubmit.add(dtoNode);
		}
		return nodesToSubmit;
	}
	
	public String CreateZip(){
	
		String recompileCheck=docMgmtImpl.CheckSequenceRecompile(workSpaceId,submissionInfo__DtlId);
		System.out.println("recompileCheck:"+recompileCheck);
		String seq_zip_path = PropertyInfo.getPropInfo()
		.getValue("SEQ_ZIP_FOLDER_DOWNLOAD");
		String seq_zip = seq_zip_path + "/" + workSpaceId;
		File seqPath = new File(seq_zip);
		if (!seqPath.isDirectory()){
			seqPath.mkdir();
		}
		SOURCE_FOLDER=submissionPath;
		if(sequenceFlag.equalsIgnoreCase("current")){
			SOURCE_FOLDER=submissionPath+"/"+currentSeqNumber;
			OUTPUT_ZIP_FILE=seq_zip+"/"+submissionInfo__DtlId+".zip";
		}
		else if(sequenceFlag.equalsIgnoreCase("related")){
			
			OUTPUT_ZIP_FILE=seq_zip+"/"+submissionInfo__DtlId+"_related.zip";
			
		}
		if(recompileCheck.equalsIgnoreCase("Yes")){
			fileList = new ArrayList<String>();
	    	generateFileList(new File(SOURCE_FOLDER));
	    	zipIt(OUTPUT_ZIP_FILE);
		}
		else{
			File file = new File(OUTPUT_ZIP_FILE);
			if (!file.exists()) {
				fileList = new ArrayList<String>();
		    	generateFileList(new File(SOURCE_FOLDER));
		    	zipIt(OUTPUT_ZIP_FILE);
			}
		}
    	htmlContent=OUTPUT_ZIP_FILE;
		return "html";
	}
	public void zipIt(String zipFile){
		byte[] buffer = new byte[1024];
		try{

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
		    //System.out.println("Output to Zip : " + zipFile);
		    if(sequenceFlag.equalsIgnoreCase("current")){
		    	SOURCE_FOLDER=submissionPath;
		    }
		    for(String file : this.fileList){
		    	//System.out.println("File Added : " + file);
		    	ZipEntry ze= new ZipEntry(file);
		        zos.putNextEntry(ze);
		        FileInputStream in =new FileInputStream(SOURCE_FOLDER + File.separator + file);
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

	/**
	* Traverse a directory and get all files,
	* and add the file into fileList
	* @param node file or directory
	*/
	public void generateFileList(File node){
		//add file only
			
			if(node.isFile()){
				if(sequenceFlag.equalsIgnoreCase("current")){
					fileList.add(currentSeqNumber+"/"+generateZipEntry(node.getAbsoluteFile().toString()));
				}else{
					fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
				}
			}
			if(node.isDirectory()){
				String[] subNote = node.list();
				for(String filename : subNote){
					generateFileList(new File(node, filename));
				}
			}
	}
	/**
	 * Format the file path for zip
	 * @param file file path
	 * @return Formatted file path
	 */
	private String generateZipEntry(String file){
		return file.substring(SOURCE_FOLDER.length()+1, file.length());
	}
	public String DownloadZipFolder(){
		
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
	public long getContentLength() {
		return contentLength;
	}
	public String getFileName() {
		return fileName;
	}
	public InputStream getInputStreams() {
		return inputStreams;
	}  		    
	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public String getDos() {
		return dos;
	}

	public void setDos(String dos) {
		this.dos = dos;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getProject_type() {
		return project_type;
	}

	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getExtraHtmlCode() {
		return extraHtmlCode;
	}

	public void setExtraHtmlCode(String extraHtmlCode) {
		this.extraHtmlCode = extraHtmlCode;
	}

	public Vector getWorkspacesWithSubmissionInfo() {
		return workspacesWithSubmissionInfo;
	}

	public void setWorkspacesWithSubmissionInfo(
			Vector workspacesWithSubmissionInfo) {
		this.workspacesWithSubmissionInfo = workspacesWithSubmissionInfo;
	}

	public Vector getGetSubmissionTypes() {
		return getSubmissionTypes;
	}

	public void setGetSubmissionTypes(Vector getSubmissionTypes) {
		this.getSubmissionTypes = getSubmissionTypes;
	}

	public Vector getGetWorkspacePublishInfo() {
		return getWorkspacePublishInfo;
	}

	public void setGetWorkspacePublishInfo(Vector getWorkspacePublishInfo) {
		this.getWorkspacePublishInfo = getWorkspacePublishInfo;
	}

	public Vector getGetAllWorkspaceSequences() {
		return getAllWorkspaceSequences;
	}

	public void setGetAllWorkspaceSequences(Vector getAllWorkspaceSequences) {
		this.getAllWorkspaceSequences = getAllWorkspaceSequences;
	}

	public String getLastPublishedVersion() {
		return lastPublishedVersion;
	}

	public void setLastPublishedVersion(String lastPublishedVersion) {
		this.lastPublishedVersion = lastPublishedVersion;
	}

	public String getCurrentSeqNumber() {
		return currentSeqNumber;
	}

	public void setCurrentSeqNumber(String currentSeqNumber) {
		this.currentSeqNumber = currentSeqNumber;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSeqTypes() {
		return seqTypes;
	}

	public void setSeqTypes(String seqTypes) {
		this.seqTypes = seqTypes;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getRelatedSeqNo() {
		return relatedSeqNo;
	}

	public void setRelatedSeqNo(String relatedSeqNo) {
		this.relatedSeqNo = relatedSeqNo;
	}

	public String getCountryRegion() {
		return countryRegion;
	}

	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}

	public String getSubmissionPath() {
		return submissionPath;
	}

	public void setSubmissionPath(String submissionPath) {
		this.submissionPath = submissionPath;
	}

	public String getSubMode() {
		return subMode;
	}

	public void setSubMode(String subMode) {
		this.subMode = subMode;
	}

	public String getSubmissionInfo__DtlId() {
		return submissionInfo__DtlId;
	}

	public void setSubmissionInfo__DtlId(String submissionInfo__DtlId) {
		this.submissionInfo__DtlId = submissionInfo__DtlId;
	}

	public String getLastConfirmedSubmissionPath() {
		return lastConfirmedSubmissionPath;
	}

	public void setLastConfirmedSubmissionPath(
			String lastConfirmedSubmissionPath) {
		this.lastConfirmedSubmissionPath = lastConfirmedSubmissionPath;
	}

	InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public ArrayList<EctdError> getEctdErrorList() {
		return ectdErrorList;
	}

	public void setEctdErrorList(ArrayList<EctdError> ectdErrorList) {
		this.ectdErrorList = ectdErrorList;
	}

	public Vector<ApplicantContactTypeMst> getGetApplicantContactTypes() {
		return getApplicantContactTypes;
	}

	public void setGetApplicantContactTypes(
			Vector<ApplicantContactTypeMst> getApplicantContactTypes) {
		this.getApplicantContactTypes = getApplicantContactTypes;
	}
	public Vector<DTOSequenceTypeMst> getGetSequenceTypes() {
		return getSequenceTypes;
	}

	public void setGetSequenceTypes(Vector<DTOSequenceTypeMst> getSequenceTypes) {
		this.getSequenceTypes = getSequenceTypes;
	}
	public Vector<DTOSequenceDescriptionMst> getGetSequenceDescriptions() {
		return getSequenceDescriptions;
	}

	public Vector<DTOSequenceTypeMst_Thai> getGetSequenceTypes_Thai() {
		return getSequenceTypes_Thai;
	}

	public void setGetSequenceTypes_Thai(
			Vector<DTOSequenceTypeMst_Thai> getSequenceTypesThai) {
		getSequenceTypes_Thai = getSequenceTypesThai;
	}

	public void setGetSequenceDescriptions(
			Vector<DTOSequenceDescriptionMst> getSequenceDescriptions) {
		this.getSequenceDescriptions = getSequenceDescriptions;
	}

	public Vector<DTORegulatoryActivityType> getGetRegulatoryActivityType() {
		return getRegulatoryActivityType;
	}

	public void setGetRegulatoryActivityType(
			Vector<DTORegulatoryActivityType> getRegulatoryActivityType) {
		this.getRegulatoryActivityType = getRegulatoryActivityType;
	}

	public Vector<DTOSequenceDescriptionMst_CA> getGetSequenceDescriptions_CA() {
		return getSequenceDescriptions_CA;
	}

	public void setGetSequenceDescriptions_CA(
			Vector<DTOSequenceDescriptionMst_CA> getSequenceDescriptionsCA) {
		getSequenceDescriptions_CA = getSequenceDescriptionsCA;
	}
	public String getEditSequence() {
		return editSequence;
	}

	public void setEditSequence(String editSequence) {
		this.editSequence = editSequence;
	}

	public String getSequenceFlag() {
		return sequenceFlag;
	}

	public void setSequenceFlag(String sequenceFlag) {
		this.sequenceFlag = sequenceFlag;
	}

	public String getDownloadFolderPath() {
		return DownloadFolderPath;
	}

	public void setDownloadFolderPath(String downloadFolderPath) {
		DownloadFolderPath = downloadFolderPath;
	}
	
}// class end
