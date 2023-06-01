package com.docmgmt.struts.actions.hyperlinks;

import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.WorkspaceDynamicNodeCheckTreeBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;




public class ListFilesAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	@Override
	public String execute()
	{
		
			uploadedFileNodes = docMgmtImpl.getUploadedFileNodes(workspaceid);
			
			LinkManager lm = new LinkManager();
			
			filesContainingLinks = lm.getFilesContainingLinks(uploadedFileNodes);
			
			finalDtlOfFiles = new Vector();
			for(int i = 0; i < filesContainingLinks.size(); i++){
				
				DTOWorkSpaceNodeHistory dto = (DTOWorkSpaceNodeHistory)filesContainingLinks.get(i);
				
				int nodeid = dto.getNodeId();
				int parentnodeid = docMgmtImpl.getParentNodeId(workspaceid, nodeid);
				DTOWorkSpaceNodeDetail parent = docMgmtImpl.getNodeDetail(workspaceid, parentnodeid).get(0);
				
				String virtualpath = lm.getVirtualPublishFilePath(workspaceid, nodeid);
				System.out.println("virtualpath:::"+virtualpath);
				String[] temp = virtualpath.split("/");
				
				int depth = temp.length-1;
				//System.out.println("depth:::"+depth);
				String filepath = dto.getFolderName().replace("\\", "/");
				//System.out.println("hi............"+filepath);
				String[] str = {dto.getFileName(),parent.getNodeName(),filepath,Integer.toString(depth),Integer.toString(nodeid)};
				finalDtlOfFiles.addElement(str);
			
			}
			
			
			int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
			String userTypeCode = ActionContext.getContext().getSession().get("usertypecode").toString();
			WorkspaceDynamicNodeCheckTreeBean treeBean = new WorkspaceDynamicNodeCheckTreeBean();
			treeBean.setUserType(userTypeCode);
			htmlCode = treeBean.getWorkspaceTreeHtml(workspaceid,userGroupCode,userCode,uploadedFileNodes);
		
		
		return SUCCESS;
	}	

	
	public Vector uploadedFileNodes;
	public String workspaceid;
	public Vector filesContainingLinks;
	public Vector finalDtlOfFiles;
	public String htmlCode;
	

	public String getWorkspaceid() {
		return workspaceid;
	}


	public void setWorkspaceid(String workspaceid) {
		this.workspaceid = workspaceid;
	}
	public String getHtmlCode() {
		return htmlCode;
	}
	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}
	public Vector getFilesContainingLinks() {
		return filesContainingLinks;
	}
	public void setFilesContainingLinks(Vector filesContainingLinks) {
		this.filesContainingLinks = filesContainingLinks;
	}
	public Vector getFinalDtlOfFiles() {
		return finalDtlOfFiles;
	}
	public void setFinalDtlOfFiles(Vector finalDtlOfFiles) {
		this.finalDtlOfFiles = finalDtlOfFiles;
	}
}
