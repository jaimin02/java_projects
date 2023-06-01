package com.docmgmt.struts.actions.addhyperlinks;

import java.io.File;

import com.docmgmt.dto.DTOSubmissionInfoAUDtl;
import com.docmgmt.dto.DTOSubmissionInfoCADtl;
import com.docmgmt.dto.DTOSubmissionInfoCHDtl;
import com.docmgmt.dto.DTOSubmissionInfoEU14Dtl;
import com.docmgmt.dto.DTOSubmissionInfoEU20Dtl;
import com.docmgmt.dto.DTOSubmissionInfoEUDtl;
import com.docmgmt.dto.DTOSubmissionInfoGCCDtl;
import com.docmgmt.dto.DTOSubmissionInfoNeeSDtl;
import com.docmgmt.dto.DTOSubmissionInfoTHDtl;
import com.docmgmt.dto.DTOSubmissionInfoUS23Dtl;
import com.docmgmt.dto.DTOSubmissionInfoUSDtl;
import com.docmgmt.dto.DTOSubmissionInfoZADtl;
import com.docmgmt.dto.DTOSubmissionInfovNeeSDtl;
import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class AddHyperLink extends ActionSupport {

	private String region;
	private String workSpaceId;
	private String subId;

	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public String client_name;
	public String project_type;
	public String project_name;

	private String sequenceNumber;
	private String dTreeFirst;
	private String dTreeSecond;

	private String publishPath;

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String clientName) {
		client_name = clientName;
	}

	public String getProject_type() {
		return project_type;
	}

	public void setProject_type(String projectType) {
		project_type = projectType;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String projectName) {
		project_name = projectName;
	}

	public String getPublishPath() {
		return publishPath;
	}

	public void setPublishPath(String publishPath) {
		this.publishPath = publishPath;
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	static StringBuffer sbdTreeFirst = new StringBuffer();
	static StringBuffer sbdTreeSecond = new StringBuffer();

	public String getdTreeFirst() {
		return dTreeFirst;
	}

	public void setdTreeFirst(String dTreeFirst) {
		this.dTreeFirst = dTreeFirst;
	}

	public String getdTreeSecond() {
		return dTreeSecond;
	}

	public void setdTreeSecond(String dTreeSecond) {
		this.dTreeSecond = dTreeSecond;
	}

	public static StringBuffer getSbdTreeFirst() {
		return sbdTreeFirst;
	}

	public static void setSbdTreeFirst(StringBuffer sbdTreeFirst) {
		AddHyperLink.sbdTreeFirst = sbdTreeFirst;
	}

	public static StringBuffer getSbdTreeSecond() {
		return sbdTreeSecond;
	}

	public static void setSbdTreeSecond(StringBuffer sbdTreeSecond) {
		AddHyperLink.sbdTreeSecond = sbdTreeSecond;
	}

	public static void visitAllDirsAndFiles(File dir) {

		try {
			sbdTreeFirst.append("<ul>");
			File[] files = dir.listFiles();

			for (File file : files) {

				if ((file.isDirectory())) {

					System.out.println("FILE NAME IS::" + file.getName());
					boolean flagLeftTree = file.getName().equalsIgnoreCase(
							"util");
					if (flagLeftTree != true) {
						sbdTreeFirst.append("<li data=addClass:'parent1'>"
								+ file.getName());
						visitAllDirsAndFiles(file);
					}
				} else {

					if (file.getName().endsWith("pdf")) {

						String path = file.getAbsolutePath();

						String link = "<li data=addClass:'pdf' data=href:'"
								+ path + "'\\> <a href='file:" + path + "'\\>"
								+ file.getName() + "</a>";
						sbdTreeFirst.append(link);

						System.out.println(link);

					}

				}
			}
			sbdTreeFirst.append("</ul>");
		} catch (Exception e) {
		}
	}

	public static void visitAllDirsAndFile(File dirs) {

		try {
			sbdTreeSecond.append("<ul>");
			File[] files = dirs.listFiles();

			for (File file : files) {

				if (file.isDirectory()) {
					boolean flagRighttTree = file.getName().equalsIgnoreCase(
							"util");
					if (flagRighttTree != true) {
						sbdTreeSecond.append("<li data=addClass:'parent1'>"
								+ file.getName());
						visitAllDirsAndFile(file);
					}

				} else {

					if (file.getName().endsWith("pdf")) {

						String path = file.getAbsolutePath();

						String links = "<li data=addClass:'pdf'><a  href='"
								+ path + "'\\>" + file.getName() + "</a>";
						sbdTreeSecond.append(links);

						System.out.println(links);

					}
				}
			}
			sbdTreeSecond.append("</ul>");
		} catch (Exception e) {
		}
	}

	public String execute() throws Exception {

		DocMgmtImpl docmgmt = new DocMgmtImpl();
		// DTOWorkSpaceMst dto = docmgmt.getWorkSpaceDetail(workSpaceId);

		// String publishPath = "";

		System.out.println("workspaceid::"+workSpaceId);
		if (workSpaceId == null || workSpaceId.trim().equals("")
				|| workSpaceId.equals("-1")) {
			return SUCCESS;
		}

		DTOWorkSpaceMst wsdto = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		
		client_name = wsdto.getClientName();
		project_type = wsdto.getProjectName();
		project_name = wsdto.getWorkSpaceDesc();
		
		if(wsdto.getProjectName().equalsIgnoreCase("NeeS")){
				
			DTOSubmissionInfoNeeSDtl subDtl = docmgmt
			.getWorkspaceSubmissionInfoNeeSDtlBySubmissionId(subId);

			publishPath = subDtl.getSubmissionPath();
		}
		else if(wsdto.getProjectName().equalsIgnoreCase("vNeeS")){
			
			DTOSubmissionInfovNeeSDtl subDtl = docmgmt
			.getWorkspaceSubmissionInfovNeeSDtlBySubmissionId(subId);

			publishPath = subDtl.getSubmissionPath();
		}
		else{
			DTOSubmissionMst submission = docMgmtImpl
			.getSubmissionInfo(workSpaceId);
			if (region != null && region.equalsIgnoreCase("eu")) {
				DTOSubmissionMst submissiondtl = docMgmtImpl.getSubmissionInfoEURegion(workSpaceId);
				if (submissiondtl.getEUDtdVersion().equalsIgnoreCase("13")) {
					DTOSubmissionInfoEUDtl subDtl = docmgmt
							.getWorkspaceSubmissionInfoEUDtlBySubmissionId(subId);
	
					publishPath = subDtl.getSubmissionPath();
				}
				else if (submissiondtl.getEUDtdVersion().equalsIgnoreCase("14")) {
					DTOSubmissionInfoEU14Dtl subDtl = docmgmt
							.getWorkspaceSubmissionInfoEU14DtlBySubmissionId(subId);
	
					publishPath = subDtl.getSubmissionPath();
				}
				else if (submissiondtl.getEUDtdVersion().equalsIgnoreCase("20")) {
					DTOSubmissionInfoEU20Dtl subDtl = docmgmt
							.getWorkspaceSubmissionInfoEU20DtlBySubmissionId(subId);
	
					publishPath = subDtl.getSubmissionPath();
				}

			}
			if (region != null && region.equalsIgnoreCase("za")) {

				DTOSubmissionInfoZADtl subDtl = docmgmt
						.getWorkspaceSubmissionInfoZADtlBySubmissionId(subId);

				publishPath = subDtl.getSubmissionPath();

			}
			if (region != null && region.equalsIgnoreCase("ch")) {

				DTOSubmissionInfoCHDtl subDtl = docmgmt
						.getWorkspaceSubmissionInfoCHDtlBySubmissionId(subId);

				publishPath = subDtl.getSubmissionPath();

			}
			if (region != null && region.equalsIgnoreCase("th")) {

				DTOSubmissionInfoTHDtl subDtl = docmgmt
						.getWorkspaceSubmissionInfoTHDtlBySubmissionId(subId);

				publishPath = subDtl.getSubmissionPath();

			}
			if (region != null && region.equalsIgnoreCase("gcc")) {
				DTOSubmissionInfoGCCDtl subDtl = docmgmt
						.getWorkspaceSubmissionInfoGCCDtlBySubmissionId(subId);

				publishPath = subDtl.getSubmissionPath();

			}
			if (region != null && region.equalsIgnoreCase("us")) {

				if (submission.getRegionalDTDVersion().equalsIgnoreCase("23")) {
					DTOSubmissionInfoUS23Dtl subDtl = docmgmt
							.getWorkspaceSubmissionInfoUS23DtlBySubmissionId(subId);
					publishPath = subDtl.getSubmissionPath();

				} else {

					DTOSubmissionInfoUSDtl subDtl = docmgmt
							.getWorkspaceSubmissionInfoUSDtlBySubmissionId(subId);
					publishPath = subDtl.getSubmissionPath();
				}
			}
			if (region != null && region.equalsIgnoreCase("ca")) {
				DTOSubmissionInfoCADtl subDtl = DocMgmtImpl
						.getWorkspaceSubmissionInfoCADtlBySubmissionId(subId);
				publishPath = subDtl.getSubmissionPath();

			}
			if (region != null && region.equalsIgnoreCase("au")) {
				DTOSubmissionInfoAUDtl subDtl = docmgmt
				.getWorkspaceSubmissionInfoAUDtlBySubmissionId(subId);
				publishPath = subDtl.getSubmissionPath();
			}
			
		}
		// DTOSubmissionMst dtoSubmissionMst = docMgmtImpl
		// .getSubmissionInfo(workSpaceId);

		

		System.out.println("tree path:: for left tree" + publishPath + "/" + sequenceNumber);
		System.out.println("tree path:: for right tree" + publishPath );
		File dirdTreeFirst=null;
		if(wsdto.getProjectName().equalsIgnoreCase("vNeeS")){
			 dirdTreeFirst = new File(publishPath);
		}
		else{
			 dirdTreeFirst = new File(publishPath + "/" + sequenceNumber);
		}
		File dirdTreeSecond = new File(publishPath);

		sbdTreeFirst.append("<ul>");
		sbdTreeFirst.append("<li data=addClass:'parent1'>" + sequenceNumber);

		visitAllDirsAndFiles(dirdTreeFirst);
		sbdTreeFirst.append("</ul>");

		//visitAllDirsAndFile(dirdTreeSecond);
		//added below code in 'if' condition only for vNees earlier only else part were present
		if(wsdto.getProjectName().equalsIgnoreCase("vNeeS")){
			sbdTreeSecond.append("<ul>");
			sbdTreeSecond.append("<li data=addClass:'parent1'>" + sequenceNumber);
			visitAllDirsAndFile(dirdTreeSecond);
			sbdTreeSecond.append("</ul>");
			}
		else{
			visitAllDirsAndFile(dirdTreeSecond);
		}
		dTreeFirst = sbdTreeFirst.toString();
		dTreeSecond = sbdTreeSecond.toString();

		sbdTreeFirst.delete(0, sbdTreeFirst.length());
		sbdTreeSecond.delete(0, sbdTreeSecond.length());

		return SUCCESS;

	}
}
