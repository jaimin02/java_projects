package com.docmgmt.struts.actions.labelandpublish;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.PublishAttrForm;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class PublishAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	private Integer userId;
	private String clientName;
	private String submissionDesc;
	private WorkspacePublishService workspacePublishService;
	private WorkspaceRevisedPublishService workspaceRevisedPublishService;
	private String labelDesc;

	public String workSpaceId;

	public int labelNo;

	public String labelId;

	public String submissionFlag;

	public String applicationNumber;

	public String relatedSeqNumber;

	public String applicant;

	public String agencyName;

	public String atc;

	public String procedureType;

	public String submissionType_eu;

	public String inventedName;

	public String inn;

	public String submissionDescription;

	public String companyName;

	public String dos;

	public String prodName;

	public String productType;

	public String applicationType;

	public String submissionType_us;

	public String countryId;

	@Override
	public String execute() {

		System.out
				.println("----------------------Publish Action---------------------");
		userId = Integer.parseInt(ActionContext.getContext().getSession().get(
				"userid").toString());

		PublishAttrForm publishForm = new PublishAttrForm();

		publishForm.setWsId(workSpaceId);
		publishForm.setLabelNo(labelNo);

		System.out.println("Workspace Id: " + workSpaceId);

		DTOWorkSpaceMst wmst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		String lastPublishedVersion = wmst.getLastPublishedVersion();

		System.out.println("lastPublishedVersion: " + lastPublishedVersion);

		if (lastPublishedVersion.equals("-999")) {

			publishForm.setSeqNumber("1000");
		} else {
			int subversionInt = Integer.parseInt(lastPublishedVersion);
			subversionInt = subversionInt + 1;
			lastPublishedVersion = "100" + subversionInt;
			lastPublishedVersion = lastPublishedVersion.substring(
					lastPublishedVersion.length() - 4, lastPublishedVersion
							.length());
			publishForm.setSeqNumber(lastPublishedVersion);
		}

		/*
		 * if(relatedSeqNumber.equals("")){ publishForm.setSeqNumber("0000");
		 * 
		 * }else { publishForm.setSeqNumber(relatedSeqNumber); }
		 */
		// publishForm.setSeqNumber(relatedSeqNumber);
		publishForm.setApplicationNumber(applicationNumber);
		publishForm.setAgencyName(agencyName);
		publishForm.setCompanyName(companyName);
		publishForm.setApplicationType(applicationType);
		publishForm.setClientName(clientName);
		publishForm.setProcedureType(procedureType);
		publishForm.setProductType(productType);
		publishForm.setSubmissionFlag(submissionFlag);
		publishForm.setLabelId(labelId);
		publishForm.setSubmissionType_us(submissionType_us);
		publishForm.setProdName(prodName);

		publishForm.setRelatedSeqNumber(relatedSeqNumber);
		// publishForm.setRelatedSeqNumber(lastPublishedVersion);

		publishForm.setCountry(countryId);
		publishForm.setApplicant(applicant);
		publishForm.setAtc(atc);
		publishForm.setProcedureType(procedureType);
		publishForm.setSubmissionType_eu(submissionType_eu);
		publishForm.setInventedName(inventedName);
		publishForm.setInn(inn);
		publishForm.setSubmissionDescription(submissionDescription);

		/* to copy folder */
		String lastLabel = docMgmtImpl.lastLabel(workSpaceId);
		PropertyInfo propInfo = PropertyInfo.getPropInfo();
		// String publishDestFolderName=propInfo.getValue("PublishFolder");

		DTOWorkSpaceMst workSpaceMst = docMgmtImpl
				.getWorkSpaceDetail(workSpaceId);

		char projectType = workSpaceMst.getProjectType();
		String wsDesc = workSpaceMst.getWorkSpaceDesc();
		// String sourcePath = publishDestFolderName + File.separator + wsDesc +
		// File.separator + lastLabel + File.separator + applicationNumber;
		// String sourcePath = publishDestFolderName + File.separator + wsDesc +
		// File.separator + lastLabel + File.separator + applicationNumber;
		String sourcePath = workSpaceMst.getBasePublishFolder()
				+ File.separator + lastLabel + File.separator
				+ applicationNumber;

		workspacePublishService = new WorkspacePublishService();
		workspaceRevisedPublishService = new WorkspaceRevisedPublishService();
		HttpServletRequest request = ServletActionContext.getRequest();

		String lastPublished = wmst.getLastPublishedVersion();
		// System.out.println("Project Type : " + projectType);
		if (projectType == 'P') {

			// if(submissionType_us.equals("original-application"))
			if (lastPublished.equals("-999")) {
				System.out.println("original");
				workspacePublishService.workspacePublish(workSpaceId,
						publishForm, request, wsDesc);
			} else {
				System.out.println("revised");
				workspaceRevisedPublishService.workspacePublish(workSpaceId,
						publishForm, request, wsDesc);
			}

		} else {
			workspacePublishService.workspaceHtml(workSpaceId, publishForm,
					wsDesc);
		}

		// update new tran no
		docMgmtImpl.getNewTranNo(workSpaceId);
		System.out.println("hello");
		// -----------------Submission Logic Start----------

		DTOSubmissionMst submission = new DTOSubmissionMst();
		submission.setWorkspaceId(workSpaceId);
		submission.setApplicationNo(applicationNumber);
		submission.setSubmittedBy(userId);
		submission.setLabelNo(labelNo);

		DTOWorkSpaceMst dtowsmt = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		// String destinationPath = dtowsmt.getBasePublishFolder() +
		// File.separator + wsDesc + File.separator + labelId + File.separator +
		// applicationNumber;
		String destinationPath = dtowsmt.getBasePublishFolder()
				+ File.separator + labelId + File.separator + applicationNumber;

		// submission.setSubmissionpath(dtowsmt.getBasePublishFolder());
		System.out.println("Submission Path: " + destinationPath);
		submission.setSubmissionpath(destinationPath);
		submission.setAgencyName(agencyName);
		submission.setReletedSeqNo(relatedSeqNumber);
		// SubmissionId would be generated at DB level for mode=1 so pass blank
		submission.setSubmissionId("");

		if (submissionFlag.equals("eu")) {
			submission.setCountrycode(countryId);
			submission.setApplicant(applicant);
			submission.setAtc(atc);
			submission.setProcedureType(procedureType);
			submission.setSubmissionType(submissionType_eu);
			submission.setInventedName(inventedName);
			submission.setInn(inn);
			submission.setSubmissionDescription(submissionDescription);

		}
		if (submissionFlag.equals("us")) {
			submission.setCompanyName(companyName);
			submission.setProductName(prodName);
			submission.setProductType(productType);
			submission.setApplicationType(applicationType);
			submission.setSubmissionType(submissionType_us);

			String cid = docMgmtImpl.getCountryId("us");
			if (cid != null) {
				submission.setCountrycode(cid);
			}

			try {
				DateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
				java.util.Date parsedUtilDate = null;
				;
				try {
					parsedUtilDate = formater.parse(dos);
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
				java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate
						.getTime());

				submission.setDateOfSubmission(sqltDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		docMgmtImpl.insertSubmissionMst(submission, 1);

		// String latestLabel= docMgmtImpl.lastLabel(workSpaceId);
		// String destinationPath = publishDestFolderName + File.separator +
		// wsDesc + File.separator + latestLabel + File.separator +
		// applicationNumber;

		File sourceDir = new File(sourcePath);
		File destDir = new File(destinationPath);

		if (!lastLabel.equals("")) {
			copyDirectory(sourceDir, destDir);

		}

		System.out.println("destinationPath:::" + destinationPath);

		// -----------------Submission Logic End ----------

		return SUCCESS;
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSubmissionDesc() {
		return submissionDesc;
	}

	public void setSubmissionDesc(String submissionDesc) {
		this.submissionDesc = submissionDesc;
	}

	public String getLabelDesc() {
		return labelDesc;
	}

	public void setLabelDesc(String labelDesc) {
		this.labelDesc = labelDesc;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getSubmissionFlag() {
		return submissionFlag;
	}

	public void setSubmissionFlag(String submissionFlag) {
		this.submissionFlag = submissionFlag;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getRelatedSeqNumber() {
		return relatedSeqNumber;
	}

	public void setRelatedSeqNumber(String relatedSeqNumber) {
		this.relatedSeqNumber = relatedSeqNumber;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAtc() {
		return atc;
	}

	public void setAtc(String atc) {
		this.atc = atc;
	}

	public String getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(String procedureType) {
		this.procedureType = procedureType;
	}

	public String getSubmissionType_eu() {
		return submissionType_eu;
	}

	public void setSubmissionType_eu(String submissionType_eu) {
		this.submissionType_eu = submissionType_eu;
	}

	public String getInventedName() {
		return inventedName;
	}

	public void setInventedName(String inventedName) {
		this.inventedName = inventedName;
	}

	public String getInn() {
		return inn;
	}

	public void setInn(String inn) {
		this.inn = inn;
	}

	public String getSubmissionDescription() {
		return submissionDescription;
	}

	public void setSubmissionDescription(String submissionDescription) {
		this.submissionDescription = submissionDescription;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getSubmissionType_us() {
		return submissionType_us;
	}

	public void setSubmissionType_us(String submissionType_us) {
		this.submissionType_us = submissionType_us;
	}

	public String getDos() {
		return dos;
	}

	public void setDos(String dos) {
		this.dos = dos;
	}

	public int getLabelNo() {
		return labelNo;
	}

	public void setLabelNo(int labelNo) {
		this.labelNo = labelNo;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public void copyDirectory(File sourceLocation, File targetLocation) {
		try {
			if (sourceLocation.isDirectory()) {
				if (!targetLocation.exists()) {
					targetLocation.mkdir();
				}
				String[] children = sourceLocation.list();
				for (int i = 0; i < children.length; i++) {
					copyDirectory(new File(sourceLocation, children[i]),
							new File(targetLocation, children[i]));
				}
			} else {
				InputStream in = new FileInputStream(sourceLocation);
				OutputStream out = new FileOutputStream(targetLocation);

				// Copy the bits from instream to outstream
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}// main ended
