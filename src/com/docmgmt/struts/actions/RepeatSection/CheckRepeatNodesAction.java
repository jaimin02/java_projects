package com.docmgmt.struts.actions.RepeatSection;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;


/**
 * @author Nagesh Borate
 */

public class CheckRepeatNodesAction extends ActionSupport {

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	private String fileName;
	private int editNodeId;
	private String htmlContent;
	
	
	public String getHtmlContent() {
		return htmlContent;
	}
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getEditNodeId() {
		return editNodeId;
	}
	public void setEditNodeId(int editNodeId) {
		this.editNodeId = editNodeId;
	}

	@Override
	public String execute(){		
		HttpServletRequest request = ServletActionContext.getRequest();
		String workSpaceId=(String)request.getSession(true).getAttribute("ws_id");		
		int parentNodeId;
		Vector<DTOWorkSpaceNodeDetail> nodesByParent;
		
		parentNodeId = docMgmtImpl.getParentNodeId(workSpaceId, editNodeId);
		nodesByParent = docMgmtImpl.getChildNodeByParent(parentNodeId, workSpaceId);
		boolean isRepeat=false;
		for (int i=0;i<nodesByParent.size();i++)
		{
			String fnm=nodesByParent.get(i).getFolderName().trim();
			if (editNodeId!=nodesByParent.get(i).getNodeId() && fileName.equals(fnm))
			{				
				isRepeat=true;
				break;
			}		
		}
			
		htmlContent=isRepeat?"This filename is not available!":"The filename is available... click on save";		
		return "html";
	}	
}
