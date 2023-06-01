package com.docmgmt.struts.actions.labelandpublish;

import java.io.File;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.struts.actions.hyperlinks.LinkManager;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateWorkspaceSubmission extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String workspaceId;
	
	public String applicationNo;
	
	public int labelno;
	
	public String SubmissionId;
	
	public DTOSubmissionMst submision; 
	
	
	/* Fields Added By : Ashmak Shah
	 * Added On : 12th may 2009
	 * */
	public String labelId;
	
	public String lastPublishedVersion;
	
	public Vector filesContainingBrokenLinks;
	/* Fields end*/
	
	@Override
	public String execute()
	{
		
		  /*
         * Update Publish Version in WorkSpaceMst
         */
		//docMgmtImpl.updatePublishedVersion(workspaceId);
		
		
		  /*
         * Update submissionMst
         */
	/*	DTOSubmissionMst dto=new DTOSubmissionMst();
		
		dto.setWorkspaceId(workspaceId);
		dto.setApplicationNo(applicationNo);
		dto.setLabelNo(labelno);
		dto.setSubmittedBy(Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString()));
		
		docMgmtImpl.insertSubmissionMst(dto,2);
		*/
		submision=docMgmtImpl.getSubmissionDetail(SubmissionId);
        
		
		/* Code Added By : Ashmak Shah
		 * Added On : 12th may 2009
		 * Usage : Finds broken links from the given 
		 * 		   workspaceid, labelid and submission sequence no.
		 * */
		LinkManager linkmanager = new LinkManager();
		
		//DTOWorkSpaceMst dtows = docMgmtImpl.getWorkSpaceDetail(workspaceId);
		
		//String publishFolder = dtows.getBasePublishFolder();
		String publishFolder = submision.getSubmissionpath();
		System.out.println(publishFolder);
		publishFolder=publishFolder.replace("\\" , "/");
		publishFolder=publishFolder.replace("//" , "/");
		publishFolder="/"+publishFolder;
		System.out.println("replaced:::"+publishFolder);
		
		//String sequenceNoFolderPath = publishFolder+"/"+labelId+"/"+applicationNo+"/"+lastPublishedVersion;
		String sequenceNoFolderPath = publishFolder+"/"+lastPublishedVersion;
		File dir = new File(sequenceNoFolderPath);
		System.out.println(dir.getAbsolutePath());
		if(dir.exists())
		{
			filesContainingBrokenLinks = linkmanager.searchFilesContainingBrokenLinks(sequenceNoFolderPath);
			
	        if(filesContainingBrokenLinks.size() != 0)
	        {
	        	addActionError("Broken Links Found...");
	        	
	        	for(int i = 0; i<filesContainingBrokenLinks.size(); i++)
	        	{
	        		String filePath = (String)filesContainingBrokenLinks.get(i);
	        		filePath = filePath.replace("\\", "/");
	        		
	        		String relativeFilePath = filePath.substring(sequenceNoFolderPath.length()+1);
	        		System.out.println(relativeFilePath);
	        		
	        		String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
	        		
	        		String[]  temp = {fileName,relativeFilePath,filePath};
	        		
	        		filesContainingBrokenLinks.setElementAt(temp, i);
	        	}
	        }
			
		}
		else
		{
			addActionError("Base directory not found...");
		}
        /* code end*/
		
		
		return SUCCESS;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getSubmissionId() {
		return SubmissionId;
	}

	public void setSubmissionId(String submissionId) {
		SubmissionId = submissionId;
	}

	public DTOSubmissionMst getSubmision() {
		return submision;
	}

	public void setSubmision(DTOSubmissionMst submision) {
		this.submision = submision;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public Vector getFilesContainingBrokenLinks() {
		return filesContainingBrokenLinks;
	}

	public void setFilesContainingBrokenLinks(Vector filesContainingBrokenLinks) {
		this.filesContainingBrokenLinks = filesContainingBrokenLinks;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getLastPublishedVersion() {
		return lastPublishedVersion;
	}

	public void setLastPublishedVersion(String lastPublishedVersion) {
		this.lastPublishedVersion = lastPublishedVersion;
	}
}
