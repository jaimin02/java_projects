package com.docmgmt.struts.actions.workspace;



import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.*;
import java.util.Vector;

public class ImportStructureAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute(){
	
		getStructDtl = docMgmtImpl.getImportStructureDetail();
		dto = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		
		if(dto !=null)
		{	
			workSpaceDesc = dto.getWorkSpaceDesc();
			
		}
		return SUCCESS;
	}
	
	public DTOWorkSpaceMst dto ;
	public String workSpaceDesc;
	
	public String workSpaceId;
	public Vector getStructDtl;
	
	public Vector getGetStructDtl() {
		return getStructDtl;
	}

	public void setGetStructDtl(Vector getStructDtl) {
		this.getStructDtl = getStructDtl;
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}

	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}

	public DTOWorkSpaceMst getDto() {
		return dto;
	}

	public void setDto(DTOWorkSpaceMst dto) {
		this.dto = dto;
	}
	
	
 	
}
