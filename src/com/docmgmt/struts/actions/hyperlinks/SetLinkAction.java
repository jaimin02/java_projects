package com.docmgmt.struts.actions.hyperlinks;

import com.opensymphony.xwork2.ActionSupport;

public class SetLinkAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	//private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		LinkManager linkmanager = new LinkManager();
		//System.out.println(selectedfilepath);
		//System.out.println(selectedLink);
		//System.out.println(sourceNodeId);
		
		String srcFileDepthStr = "";
		int iDepth = Integer.parseInt(selectedFileDepth);
		for(int i = 0; i < iDepth; i++)
		{
			srcFileDepthStr = srcFileDepthStr + "../";
		}
		
		String virtualPath = linkmanager.getVirtualPublishFilePath(workspaceid, Integer.parseInt(sourceNodeId));
		
		
		//System.out.println("Virstual Path-"+virtualPath);
		
		if(linkmanager.isAttrValueNotFound())
		{
			addActionError("Cannot edit hyperlink...");
			
			String[] info = virtualPath.split("&&");
			String[] nodeinfo = info[0].split("=");
			String[] attrinfo = info[1].split("=");
			
			//addActionError("May be Attribute: "+attrinfo[1]+" on Node: "+nodeinfo[1]+" has no value..." );
		
			extraHtmlCode = "<div>" +
							"May be Attribute: <font color='blue'>"+attrinfo[1]+"</font> on Node: <font color='blue'>"+nodeinfo[1]+"</font> has no value..." +
							"</div>" +
							"<br>" +
							"<br>" +
							"<br>" +
							"<a href=\"#\" onclick=\"showFiles();\">Click here to go back...</a>";
		}
		else
		{
			boolean linksetdone = linkmanager.setLink(selectedfilepath, selectedLink, srcFileDepthStr + virtualPath);
			if(linksetdone)
			{
				addActionMessage("Link Edited Successfully...");
			
				extraHtmlCode = "<a href=\"#\" onclick=\"showFiles();\">Click here to continue...</a>";
			}
		}
			
		
		
		return SUCCESS;
	}


	public String selectedLink;
	public String sourceNodeId;
	public String selectedfilepath;
	public String workspaceid;
	public String selectedFileDepth;
	public String extraHtmlCode;


	public String getSelectedFileDepth() {
		return selectedFileDepth;
	}
	public void setSelectedFileDepth(String selectedFileDepth) {
		this.selectedFileDepth = selectedFileDepth;
	}
	public String getWorkspaceid() {
		return workspaceid;
	}
	public void setWorkspaceid(String workspaceid) {
		this.workspaceid = workspaceid;
	}
	public String getSelectedLink() {
		return selectedLink;
	}
	public void setSelectedLink(String selectedLink) {
		this.selectedLink = selectedLink;
	}
	public String getSourceNodeId() {
		return sourceNodeId;
	}
	public void setSourceNodeId(String sourceNodeId) {
		this.sourceNodeId = sourceNodeId;
	}
	public String getSelectedfilepath() {
		return selectedfilepath;
	}
	public void setSelectedfilepath(String selectedfilepath) {
		this.selectedfilepath = selectedfilepath;
	}
	public String getExtraHtmlCode() {
		return extraHtmlCode;
	}
	public void setExtraHtmlCode(String extraHtmlCode) {
		this.extraHtmlCode = extraHtmlCode;
	}
	
}
