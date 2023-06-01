package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.Vector;

import com.docmgmt.dto.DTOTemplateMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class EditProjectAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String nodeName;
	public String workSpaceId;
	public String remark;
	public String folderName;
	public String defaultFileFormat;
	public String selectedeTemplate;
	public DTOWorkSpaceNodeDetail dtoNodeDetail = new DTOWorkSpaceNodeDetail();
	public Vector<DTOTemplateMst> templateList;
	
	@Override
	public String execute()
	{
		Vector<DTOWorkSpaceNodeDetail> workspaceNodeDtls = docMgmtImpl.getNodeDetail(workSpaceId, 1);
		templateList = docMgmtImpl.getAllTemplates();
		DTOWorkSpaceMst dtoWsMst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		selectedeTemplate = dtoWsMst.getTemplateId()+"##"+dtoWsMst.getTemplateDesc();
		if (workspaceNodeDtls.size() > 0) 
			dtoNodeDetail = workspaceNodeDtls.get(workspaceNodeDtls.size()-1);	
		else 
			dtoNodeDetail = new DTOWorkSpaceNodeDetail();
		
		return SUCCESS;
	}


	public String getWorkSpaceId() {
		return workSpaceId;
	}


	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	
}
