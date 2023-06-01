package com.docmgmt.struts.actions.workspace.ManualMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.FilenameUtils;

import com.docmgmt.dto.DTOManualModeSeqZipDtl;
import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOTemplateMst;
import com.docmgmt.dto.DTOUserGroupMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.docmgmt.struts.actions.labelandpublish.ManualMode.XmlParsingForReferenceNodeId;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CreateProjectAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();

	String workSpaceDesc, deptCode, copyRights, advanceManualProject,
			dossierName;
	String locationCode, clientCode, projectCode;
	String templateId;
	public String remark;
	int userCode = 2;
	String msg;
	File[] upload;
	String[] uploadFileName;

	String[] uploadContentType;
	String workspaceId, startSequence;
	String ReferenceNo, htmlContent;
	String latestSequence = "";
	String submissionId = "";

	public Vector nodelist = new Vector();


	String vParentNodeName = "";
	int parentNodeId = -1;
	String vFileName = "";

	String regionName = "";
	//added on 29/05/2015 by Dharmendra jadav
	//start
	
	public Vector WorkspaceUserDetailList;
	public Vector getStageDetail;
	private Vector userGroupDtl; 
	public int[] userCodes;
	Date fromDt;
	Date toDt;
	ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
	DTOWorkSpaceUserMst dtoWorkSpaceUserMst = new DTOWorkSpaceUserMst();
	DTOWorkSpaceUserRightsMst dtowsurm = new DTOWorkSpaceUserRightsMst();

	boolean allowAdvanceManualProject;
	public String manualPrevSeq;

	public boolean isAllowAdvanceManualProject() {
		return allowAdvanceManualProject;
	}

	public void setAllowAdvanceManualProject(boolean allowAdvanceManualProject) {
		this.allowAdvanceManualProject = allowAdvanceManualProject;
	}

	public String getDossierName() {
		return dossierName;
	}

	public void setDossierName(String dossierName) {
		this.dossierName = dossierName;
	}

	public String getReferenceNo() {
		return ReferenceNo;
	}

	public String getAdvanceManualProject() {
		return advanceManualProject;
	}

	public void setAdvanceManualProject(String advanceManualProject) {
		this.advanceManualProject = advanceManualProject;
	}

	public void setReferenceNo(String referenceNo) {
		ReferenceNo = referenceNo;
	}

	public String getNodetofind() {
		return nodetofind;
	}

	public void setNodetofind(String nodetofind) {
		this.nodetofind = nodetofind;
	}

	public String getInSequenceNo() {
		return inSequenceNo;
	}

	public void setInSequenceNo(String inSequenceNo) {
		this.inSequenceNo = inSequenceNo;
	}
	public Vector getWorkspaceUserDetailList() {
		return WorkspaceUserDetailList;
	}

	public void setWorkspaceUserDetailList(Vector workspaceUserDetailList) {
		WorkspaceUserDetailList = workspaceUserDetailList;
	}

	public Vector getGetStageDetail() {
		return getStageDetail;
	}

	public void setGetStageDetail(Vector getStageDetail) {
		this.getStageDetail = getStageDetail;
	}

	public int[] getUserCodes() {
		return userCodes;
	}

	public void setUserCodes(int[] userCodes) {
		this.userCodes = userCodes;
	}
	String nodetofind, inSequenceNo = "";
	String leafnodetofind;
	public String extraHtmlCode;

	private String basePublishedFolder;

	public String getLeafnodetofind() {
		return leafnodetofind;
	}

	public void setLeafnodetofind(String leafnodetofind) {
		this.leafnodetofind = leafnodetofind;
	}

	/**
	 * This Action is copied from CreateWorkspaceAction.
	 */
	@Override
	public String execute() {
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		String maxProjectsValue = knetProperties.getValue("MaxProjects");
		FosunChanges=knetProperties.getValue("FosunCustomization");
		int maxProjects = -1;
		if (maxProjectsValue != null) {
			try {
				maxProjects = new Integer(maxProjectsValue).intValue();
			} catch (NumberFormatException e) {
				System.out
						.println("Invalid value of maxProjects property in property file.");
				e.printStackTrace();
			}
		}
		Vector<DTOWorkSpaceMst> allWorkspace = docMgmtImpl
				.getAllWorkspaceForChangeStatus();
		// System.out.println("allWorkspace"+allWorkspace.size());
		int count = 0;
		for (int i = 0; i < allWorkspace.size(); i++) {
			DTOWorkSpaceMst dtoWorkSpaceMst = allWorkspace.get(i);
			if (dtoWorkSpaceMst.getStatusIndi() != 'D')
				count++;
		}

		if (maxProjects >= 0 && count >= maxProjects) {
			maxLimitExceeded = "yes";
			addActionError("Maximum Limit of number of projects exceeded !!!<br>Please <a href='WorkspaceSummay.do'>click here</a> to delete unnecessary projects...");
			return SUCCESS;
		} else
			maxLimitExceeded = "no";

		int userGroupCode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());
		if(FosunChanges.equalsIgnoreCase("yes")){
			clientCodeGroup=docMgmtImpl.getUserGroupClientCode(userGroupCode);	
		}
		getTemplateDtl = docMgmtImpl.getAllTemplates();

		getLocationDtl = docMgmtImpl.getLocationDtl();
		getClientDtl = docMgmtImpl.getClientDetail();

		getDeptDtl = docMgmtImpl.getDepartmentDetail();
		getProjectDtl = docMgmtImpl.getProjectType();

		// User will be able to see all the admin users of the same group
		// only...

		getUserDtl = docMgmtImpl.getuserDetailsByUserGrp(userGroupCode);

		if (msg != null && !msg.equals("")) {
			addActionMessage(msg);
		}

		//getDossierNames();
		return SUCCESS;
	}

	/*private void getDossierNames() {
		PropertyInfo objInfo = PropertyInfo.getPropInfo();
		String allowAdvanceManualProjectProperty = objInfo
				.getValue("AllowAdvanceManualProject");
		if (allowAdvanceManualProjectProperty.equalsIgnoreCase("y")) {
			allowAdvanceManualProject = true;
		} else {
			return;
		}

		basePublishedFolder = objInfo.getValue("ManualProjectsServerPath");
		File objFile = new File(basePublishedFolder); // TODO EU & US

		String[] names = objFile.list();
		dossierDtl = new ArrayList<String>();
		for (String name : names) {
			if (new File(basePublishedFolder + "/" + name).isDirectory()) {
				dossierDtl.add(name);
			}
		}
	}*/

	/**
	 * This Action is copied from SaveWorkspace.
	 */
	public String saveManualProject() {
		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("usergroupcode").toString());

		boolean workspaceexist = docMgmtImpl.workspaceNameExist(workSpaceDesc);
		String clientname = docMgmtImpl.getClientName(clientCode);
		if (workspaceexist == false) {
			DTOWorkSpaceMst dto = new DTOWorkSpaceMst();
			dto.setWorkSpaceDesc(workSpaceDesc);
			dto.setLocationCode(locationCode);
			dto.setDeptCode(deptCode);
			dto.setClientCode(clientCode);
			dto.setClientName(clientname);
			dto.setProjectCode(projectCode);
			dto.setDocTypeCode("0001");// Setting Document Type Hard-Coded as
			// its not in use anymore.
			dto.setTemplateId(templateId);
			dto.setRemark(remark);
			Vector templateDetail = docMgmtImpl
					.getTemplateDetailById(templateId);
			DTOTemplateMst dtoTemplateMst = (DTOTemplateMst) templateDetail
					.get(0);
			dto.setTemplateDesc(dtoTemplateMst.getTemplateDesc());
			dto.setModifyBy(userId);

			// Here we are creating Manual Mode Publish Project,
			// Thus setting projectFor = 'M'
			String projectFor = "M";
			boolean insertStatus = docMgmtImpl.AddUpdateWorkspace(dto,
					userCode, projectFor, templateId, 1);

			if (insertStatus) {
				if (copyRights.equals("Y")) {
					docMgmtImpl.addOrUpdateRights(templateId, userCode);
				}
				// Get the newly created workspace's ID by WorkSpaceDesc

				System.out.println("Is Advance Manual Project->"
						+ advanceManualProject);
				workspaceId = docMgmtImpl
						.getWorkspaceID(dto.getWorkSpaceDesc());
				String manualPrevSeqPath = PropertyInfo.getPropInfo()
				.getValue("ManualProjectsServerPath");
				
				String prevSequencePath = manualPrevSeqPath + "/" + workspaceId;
				File seqPath = new File(prevSequencePath);
				manualPrevSeq="";
				if (!seqPath.isDirectory()){
					seqPath.mkdir();
				}
				manualPrevSeq=prevSequencePath;
				manualPrevSeq=manualPrevSeq.replace('/', '\\');

				if (advanceManualProject != null
						&& advanceManualProject.equalsIgnoreCase("Y")) {
				
					if (dossierName != null) {
						ImportManualProject objAdvanceManual = new ImportManualProject();
						objAdvanceManual.createAdvanceManualProject(
								workspaceId, dossierName);
						addActionMessage("<a href='WorkspaceOpen.do?ws_id="+ workspaceId +"'>" +workSpaceDesc + "</a> Created Successfully...");
						extraHtmlCode = "<input type=\"button\" class=\"button\" value=\"Back\" onclick=\"exists();\">";

					//	addActionMessage("<a href='WorkspaceOpen.do?ws_id="+ workspaceId +"'>" +workSpaceDesc + "</a> Created Successfully...");
						return "advanceManual";
						
					}
					
					
				}
				else
					addActionMessage("<a href='WorkspaceOpen.do?ws_id="+ workspaceId +"'>" +workSpaceDesc + "</a> Created Successfully...");
					//addActionMessage(workSpaceDesc + " Created Successfully...");
				// msg = workSpaceDesc+" Created Successfully...";
				String userType=docMgmtImpl.getUserTypeName(userCode);
	        	PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
				 boolean enablePowerUser = propertyInfo.getValue("EnablePowerUser")
		        	.equalsIgnoreCase("yes") ? true : false;
	        	//added on 29/5/2015 by Dharmendra jadav
	    		//start
	        	if(!userType.equalsIgnoreCase("PU") && enablePowerUser){
	        		
		    		WorkspaceUserDetailList=docMgmtImpl.getWorkspaceUserDetailList(workspaceId);
		    		getStageDetail = docMgmtImpl.getStageDetail();
		    		userGroupDtl=docMgmtImpl.getUserGroupDtl();
		    		
		    		dtoWorkSpaceUserMst.setWorkSpaceId(workspaceId);
		    		dtowsurm.setWorkSpaceId(workspaceId);
		    		
		    		for (int iWorkSpacrUser = 0; iWorkSpacrUser < WorkspaceUserDetailList.size(); iWorkSpacrUser++) {
	
						DTOWorkSpaceUserMst workspaceUserDtl = (DTOWorkSpaceUserMst) WorkspaceUserDetailList.get(iWorkSpacrUser);
						fromDt = workspaceUserDtl.getFromDt();
						toDt = workspaceUserDtl.getToDt();
					
		    		}
						for (int index = 0; index < userGroupDtl.size(); index++) {
		
							DTOUserGroupMst userGroupmst = (DTOUserGroupMst) userGroupDtl.get(index);
							if (userGroupmst.getUserTypeName().equalsIgnoreCase("PU")) {
		
								Vector<DTOUserMst> workspaceUserDetails = docMgmtImpl.getuserDetailsByUserGrp(userGroupmst.getUserGroupCode());
								userCodes = new int[workspaceUserDetails.size()];
								
								for (int i = 0; i < workspaceUserDetails.size(); i++) {
									
									DTOUserMst userMstDTO = workspaceUserDetails.get(i);
									userCodes[i] = userMstDTO.getUserCode();
								}
		
								dtoWorkSpaceUserMst.setUserGroupCode(userGroupmst.getUserGroupCode());
								dtowsurm.setUserGroupCode(userGroupmst.getUserGroupCode());
		
								dtoWorkSpaceUserMst.setAdminFlag('N');
								Timestamp ts = new Timestamp(new Date().getTime());
								dtoWorkSpaceUserMst.setLastAccessedOn(ts);
								dtoWorkSpaceUserMst.setRemark(remark);
								int ucd = Integer.parseInt(ActionContext.getContext()
										.getSession().get("userid").toString());
								dtoWorkSpaceUserMst.setModifyBy(ucd);
								dtoWorkSpaceUserMst.setModifyOn(ts);
								Date fromdt = fromDt;
								dtoWorkSpaceUserMst.setFromDt(fromdt);
								Date todt = toDt;
								dtoWorkSpaceUserMst.setToDt(todt);
		
								docMgmtImpl.insertUpdateUsertoWorkspace(dtoWorkSpaceUserMst, userCodes);
		
								dtowsurm.setCanReadFlag('Y');
								dtowsurm.setAdvancedRights("Y");
								dtowsurm.setCanDeleteFlag('Y');
								dtowsurm.setCanAddFlag('Y');
								dtowsurm.setCanEditFlag('Y');
								dtowsurm.setRemark(remark);
								dtowsurm.setModifyBy(ucd);
								dtowsurm.setModifyOn(ts);
								dtowsurm.setStatusIndi('N');
		
								for (int iUserCode = 0; iUserCode < userCodes.length; iUserCode++) {
									for (int stage = 0; stage < getStageDetail.size(); stage++) {
		
										DTOStageMst stageDtl = (DTOStageMst) getStageDetail.get(stage);
										DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMstClone = dtowsurm.clone();
										dtoWorkSpaceUserRightsMstClone.setUserCode(userCodes[iUserCode]);
										dtoWorkSpaceUserRightsMstClone.setStageId(stageDtl.getStageId());
										userRightsList.add(dtoWorkSpaceUserRightsMstClone);
		
									}
								}
								docMgmtImpl.insertMultipleUserRights(userRightsList);
		
							}
						}
					//end
	        	}

			} else {
				addActionError("Error While Creating Project.");
				// msg = "Error While Creating Project";
			}

			return SUCCESS;
		} else {
			addActionError("Project Name Already Exists!");
			// msg = "Project Name Already Exists!";
			extraHtmlCode = "<input type=\"button\" class=\"button\" value=\"Back\" onclick=\"exists();\">";
			return "exists";
		}

	}

	public String saveUploadedFile() throws ZipException, IOException {
		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());

		String currSeq = startSequence;
		int i;
		String seqZipDir = PropertyInfo.getPropInfo()
				.getValue("SEQ_ZIP_FOLDER");
		String zipFilePath = seqZipDir + "/" + workspaceId;

		ArrayList<DTOManualModeSeqZipDtl> seqZipDtlList = new ArrayList<DTOManualModeSeqZipDtl>();
		if (upload != null && workspaceId != null) {
			for (i = 0; i < upload.length; i++) {
				File seqZipFile = upload[i];
				String seqZipFileName = uploadFileName[i];
				String seqZipFileType = uploadContentType[i];
				System.out.println("Absolute Path="
						+ seqZipFile.getAbsolutePath());
				System.out.println(seqZipFileName + ":" + seqZipFileType);

				// Change fileName as per seqNo (e.g '0000.zip')
				String newFileName = currSeq + "."
						+ FilenameUtils.getExtension(seqZipFileName);
				File destSeqZipFile = new File(zipFilePath + "/" + currSeq
						+ "/" + newFileName);

				FileManager fileManager = new FileManager();
				fileManager.copyDirectory(seqZipFile, destSeqZipFile);

				System.out.println("Zip File Location="
						+ destSeqZipFile.getAbsolutePath());

				String unziplocation = zipFilePath + "/" + currSeq;

				unzipManualUploadedFilesInWorkspace(destSeqZipFile, new File(
						unziplocation));

				unzipFiles(unziplocation, destSeqZipFile.getAbsolutePath());
				// UnZip Here

				DTOManualModeSeqZipDtl manualModeSeqZipDtl = new DTOManualModeSeqZipDtl();
				manualModeSeqZipDtl.setWorkspaceId(workspaceId);
				manualModeSeqZipDtl.setSeqNo(currSeq);
				manualModeSeqZipDtl.setZipFileName(newFileName);
				manualModeSeqZipDtl.setZipFilePath(zipFilePath);
				manualModeSeqZipDtl.setUploadedBy(userId);
				manualModeSeqZipDtl.setModifyBy(userId);

				System.out.println("Here..1");

				seqZipDtlList.add(manualModeSeqZipDtl);

				// Increment of currSeq.
				currSeq = updateSeq(currSeq, 1);
			}
			// Insert Into 'ManualModeSeqZipDtl'
			DocMgmtImpl.insertManualModeSeqZipDtl(seqZipDtlList);

			// Here UnZipFiles

			addActionMessage(i + " File(s) Uploaded Successfully...");
			msg = i + " File(s) Uploaded Successfully...";
		} else {
			addActionMessage("No File(s) Found To Upload...");
		}
		extraHtmlCode = "<div align=\"center\"><input type=\"button\" value=\"Back\" class=\"button\" onclick=\"history.go(-1);\"></div>"
				+ "";
		return SUCCESS;

	}

	public void unzipFiles(String source, String target) {
		System.out.println("Source=" + source);
		System.out.println("Destination=" + target);

	}

	public void unzipManualUploadedFilesInWorkspace(File file, File targetDir)
			throws ZipException, IOException {
		targetDir.mkdirs();
		ZipFile zipFile = new ZipFile(file);
		System.out.println("Inside Unzip");

		try {
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				File targetFile = new File(targetDir, entry.getName());
				if (entry.isDirectory()) {
					targetFile.mkdirs();
				} else {
					InputStream input = zipFile.getInputStream(entry);
					try {
						OutputStream output = new FileOutputStream(targetFile);
						try {
							copy(input, output);
						} finally {
							output.close();
						}
					} finally {
						input.close();
					}
				}
			}
		} finally {
			zipFile.close();
		}
	}

	private static void copy(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[4096];
		int size;
		while ((size = input.read(buffer)) != -1)
			output.write(buffer, 0, size);
	}

	private String updateSeq(String seq, int changeVal) {
		int iSeq = Integer.parseInt(seq);
		iSeq = iSeq + changeVal;
		String newSeq = "000" + iSeq;
		return newSeq.substring(newSeq.length() - 4);
	}

	public String getNodeReferenceId() {

		System.out.println("Sequence No...." + inSequenceNo);
		System.out.println(" Root Node...." + nodetofind);
		System.out.println(" Leaf Node Name...." + leafnodetofind);

		System.out.println(" WorkspaceId...." + workspaceId);
		ReferenceNo = inSequenceNo;
		
		String manualPrevSeqPath = PropertyInfo.getPropInfo()
		.getValue("ManualProjectsServerPath");
		
		DTOWorkSpaceMst wsdto = docMgmtImpl.getWorkSpaceDetail(workspaceId);
		// client_name = wsdto.getClientName();
		String stype = wsdto.getLocationName().toLowerCase();
		if(stype.equalsIgnoreCase("GCC")){
			stype="gc";
		}
		String XMLFilePath = "";
		
		
		String wsId=workspaceId.trim();
		XMLFilePath = manualPrevSeqPath+"/"+wsId+"/"+inSequenceNo+"/m1/"+stype+"/"+stype+"-regional.xml";
		
		//	XMLFilePath = folderPath+"\\"+"eu-regional.xml";
		
		//XMLFilePath="//90.0.0.15/docmgmtandpub/eSubmissionExpress/ManualMode/0208/0000/eu-regional.xml";
		
		System.out.println("Path=" + XMLFilePath);
		/*ArrayList<DTOManualModeSeqZipDtl> data = new ArrayList<DTOManualModeSeqZipDtl>();

		data = DocMgmtImpl.getManualModeSeqZipDtlBySequenceNo(workspaceId,
				inSequenceNo);
		
		if (data.size() > 0) {
			System.out.println(data.size());
			XMLFilePath = data.get(0).getZipFilePath() + "/" + inSequenceNo
					+ "/" + inSequenceNo + "/m1/eu/eu-regional.xml";
			System.out.println("Path=" + XMLFilePath);
		}*/
		int flag = 0;
		
		if (XMLFilePath != "") {
			File FileToParse = new File(XMLFilePath);
			if (FileToParse.exists()) {
				System.out.println("File Is Exist");
				FileToParse = null;
				
				 if (nodetofind.equals("cover") || nodetofind.equalsIgnoreCase("cover letter for [country]")) {
						nodetofind = "m1-0-cover";
						flag = 1;
					} else if (nodetofind.equals("Application") || nodetofind.equalsIgnoreCase("Application form for [country]")) {
						nodetofind = "m1-2-form";
						flag = 1;
					} else if (nodetofind.equalsIgnoreCase("[type]") || nodetofind.equalsIgnoreCase("[type] in [xml:lang]")) {
						if(stype.equalsIgnoreCase("gc")){
							nodetofind = "m1-3-1-spc";
						}
						else{
							nodetofind = "m1-3-1-spc-label-pl";
						}
						flag = 1;
					} else if (nodetofind.equals("Mock") || nodetofind.equalsIgnoreCase("Mock Up for [country]")) {
						nodetofind = "m1-3-2-mockup";
						flag = 1;
					}else if (nodetofind.equalsIgnoreCase("Artwork for [country]")) {
						nodetofind = "m1-3-4-mockup";
						flag = 1;
					} else if (nodetofind.equalsIgnoreCase("Samples for [country]")) {
						nodetofind = "m1-3-5-samples";
						flag = 1;
					}else if (nodetofind.equals("Specimen") || nodetofind.equalsIgnoreCase("Specimen for [country]")) {
						nodetofind = "m1-3-3-specimen";
						flag = 1;
					} else if (nodetofind.equals("Consultation") || nodetofind.equalsIgnoreCase("Consultation for [country]")) {
						nodetofind = "m1-3-4-consultation";
						flag = 1;
					} else if (nodetofind.equals("Approved") || nodetofind.equalsIgnoreCase("Approved for [country]")) {
						nodetofind = "m1-3-5-approved";
						flag = 1;
					} else if (nodetofind.equals("Additional") || nodetofind.equalsIgnoreCase("Additional for [country]")) {
						nodetofind = "m1-additional-data";
						flag = 1;
					} else if (nodetofind.equals("responses") || nodetofind.equalsIgnoreCase("responses for [country]")) {
						nodetofind = "m1-responses";
						flag = 1;
					} else if (nodetofind.equals("draft-labeling-text")) {
							nodetofind = "m1-14-1-3-draft-labeling-text";
							flag = 1;
					} else if (nodetofind.equals("form-1571") || nodetofind.equalsIgnoreCase("form-356h")
							|| nodetofind.equalsIgnoreCase("form-3397") || nodetofind.equalsIgnoreCase("form-2252")
							|| nodetofind.equalsIgnoreCase("form-2253") || nodetofind.equalsIgnoreCase("form-2253")
							|| nodetofind.equalsIgnoreCase("form-3674") || nodetofind.equalsIgnoreCase("form-3674")
							|| nodetofind.equalsIgnoreCase("form-3794")
							) {
						nodetofind = "form";
						flag = 1;
					} 


				System.out.println("Node to find Now=" + nodetofind);
				XmlParsingForReferenceNodeId ref = new XmlParsingForReferenceNodeId();
				nodelist = ref.parseXmlFile(XMLFilePath, nodetofind);
				System.out.println("Node Size=" + nodelist.size());

				if (nodelist.size() == 0 && flag == 0) {
					//XMLFilePath = data.get(0).getZipFilePath() + "/"+ inSequenceNo + "/" + inSequenceNo + "/index.xml";
					XMLFilePath = manualPrevSeqPath+"/"+wsId+"/"+inSequenceNo+"/index.xml";
					System.out.println("Path=" + XMLFilePath);
					FileToParse = null;
					FileToParse = new File(XMLFilePath);
					if (FileToParse.exists()) {
						XmlParsingForReferenceNodeId ref1 = new XmlParsingForReferenceNodeId();
						nodelist = ref1.parseXmlFile(XMLFilePath, nodetofind);
					}

				}

			}
		}
		return SUCCESS;
	}
	public String FosunChanges;
	public String clientCodeGroup;
	public Vector getTemplateDtl;
	public Vector getLocationDtl;
	public Vector getClientDtl;
	public Vector getDeptDtl;
	public Vector getProjectDtl;
	public Vector getUserDtl;
	public String maxLimitExceeded;
	public ArrayList<String> dossierDtl;

	public ArrayList<String> getDossierDtl() {
		return dossierDtl;
		// return (List) docMgmtImpl.getDossierDtlByRegion("");
	}

	public void setDossierDtl(ArrayList<String> dossierDtl) {
		this.dossierDtl = dossierDtl;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}

	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getCopyRights() {
		return copyRights;
	}

	public void setCopyRights(String copyRights) {
		this.copyRights = copyRights;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public File[] getUpload() {
		return upload;
	}

	public void setUpload(File[] upload) {
		this.upload = upload;
	}

	public String[] getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String[] getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String[] uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getStartSequence() {
		return startSequence;
	}

	public void setStartSequence(String startSequence) {
		this.startSequence = startSequence;
	}
}
