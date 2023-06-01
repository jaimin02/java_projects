package com.docmgmt.struts.actions.workspace;



import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionInfoDMSDtl;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class DeleteWorkspaceAction extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String htmlContent;
	public String archive;
	public String statusIndi;
	public String remark;
	
	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTOWorkSpaceMst dto = new DTOWorkSpaceMst();
		dto.setWorkSpaceId(workspaceId);
		dto.setModifyBy(userId);
		docMgmtImpl.AddUpdateWorkspace(dto, userId, "", "",3);
		htmlContent = "Project Deleted Successfully.";
		return SUCCESS;
	}

	public String archiveProject(){
		
		Vector<DTOSubmissionInfoDMSDtl> wsMstArrList=docMgmtImpl.getDMSSubmissionInfoforarchive(workspaceId);
		
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		DTOWorkSpaceMst dto = new DTOWorkSpaceMst();
		dto.setWorkSpaceId(workspaceId);
		dto.setModifyBy(userId);
		dto.setRemark(remark);
		
		if(archive.equals("true")){
			System.out.println("Lock Sequence :" + wsMstArrList.size());
			if(wsMstArrList.size()>0){	
				if(statusIndi.equals("A")){
					docMgmtImpl.AddUpdateWorkspace(dto, userId, "", "",5); //for UnArchive Project
					htmlContent = "Project Unarchive Successfully...";
				}
				else{
					docMgmtImpl.AddUpdateWorkspace(dto, userId, "", "",4); //for Archive Project
					htmlContent = "Project Archive Successfully...";
				}
			}
			else{
				htmlContent = "No";
			//htmlContent = "Please Lock At Least One Sequence..!";
			}
		}
		return SUCCESS;
	}
		public String voidProject(){
			
			
			int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			
			DTOWorkSpaceMst dto = new DTOWorkSpaceMst();
			dto.setWorkSpaceId(workspaceId);
			dto.setModifyBy(userId);
			dto.setRemark(remark);
			
			if(statusIndi.equals("V")){
				docMgmtImpl.AddUpdateWorkspace(dto, userId, "", "",7); //for UnVoid Project
				System.out.println("UnVoid Project...");
				htmlContent = "Project UnVoid Successfully...";
			}
			else{
				docMgmtImpl.AddUpdateWorkspace(dto, userId, "", "",6); //for Void Project
				System.out.println("Void Project...");
				htmlContent = "Project Void Successfully...";
			}
	
		return SUCCESS;
	}
		
public String lockProject(){
			
			
			int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			
			DTOWorkSpaceMst dto = new DTOWorkSpaceMst();
			dto.setWorkSpaceId(workspaceId);
			dto.setModifyBy(userId);
			dto.setRemark(remark);
			
			docMgmtImpl.AddUpdateWorkspace(dto, userId, "", "",8); //for Lock Project
			System.out.println("Lock Project...");
			htmlContent = "Project Lcok Successfully...";

	
		return SUCCESS;
	}

public String unLockProject(){
	
	int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	
	DTOWorkSpaceMst dto = new DTOWorkSpaceMst();
	dto.setWorkSpaceId(workspaceId);
	dto.setModifyBy(userId);
	dto.setRemark(remark);
	
	docMgmtImpl.AddUpdateWorkspace(dto, userId, "", "",9); //for UnLock Project
	System.out.println("UnLock Project...");
	htmlContent = "Project UnLock Successfully.";

	return SUCCESS;
}
	
public String checkLockSeq(){
		
		Vector<DTOSubmissionInfoDMSDtl> wsMstArrList=docMgmtImpl.getDMSSubmissionInfoforarchive(workspaceId);
		
			System.out.println("Lock Sequence :" + wsMstArrList.size());
			if(wsMstArrList.size()>0){	
				htmlContent = "Yes";
			}
			else{
				htmlContent = "No";
			}
		return SUCCESS;
	}
	
	public String workspaceId;

	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	
}
