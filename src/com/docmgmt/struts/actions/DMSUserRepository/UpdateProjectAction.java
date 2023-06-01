package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateProjectAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String workSpaceId;
	public String nodeName;
	public String folderName;
	public String remark;
	public String templateDtl;
	

	
	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		Vector<DTOWorkSpaceNodeDetail> workspaceNodeDtls = docMgmtImpl.getNodeDetail(workSpaceId, 1);
		DTOWorkSpaceMst dtoWsMst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		dtoWsMst.setWorkSpaceDesc(nodeName);
		dtoWsMst.setModifyBy(userId);
		dtoWsMst.setRemark(remark);
		dtoWsMst.setTemplateId(templateDtl.split("##")[0]);
		dtoWsMst.setTemplateDesc(templateDtl.split("##")[1]);
		dtoWsMst.setLastAccessedBy(userId);
		docMgmtImpl.AddUpdateWorkspace(dtoWsMst,userId,dtoWsMst.getProjectType()+"",dtoWsMst.getTemplateId(),2);
		docMgmtImpl.insertWorkspaceMst(dtoWsMst, 2);
		for (int i = 0; i < workspaceNodeDtls.size(); i++) {
			DTOWorkSpaceNodeDetail dto = workspaceNodeDtls.get(i);
			dto.setNodeName(nodeName);
			dto.setNodeDisplayName(nodeName);
			dto.setFolderName(folderName);
			dto.setRemark(remark);
			docMgmtImpl.insertWorkspaceNodeDetail(dto, 2);
		}
		return SUCCESS;
	}
}