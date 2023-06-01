package com.docmgmt.struts.actions.template;

import com.docmgmt.dto.DTOTemplateNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.entities.Node.NodeTypeIndi;
import com.docmgmt.server.webinterface.entities.Node.RequiredFlag;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EditStructureNodeAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String  templateId = ActionContext.getContext().getSession().get("templateId").toString();
				
		DTOTemplateNodeDetail dto;
		dto = (DTOTemplateNodeDetail)docMgmtImpl.getTemplateNodeDetailByNodeId(templateId, nodeId).get(0);
	    dto.setTemplateId(templateId);
	    dto.setNodeId(nodeId);
	    dto.setNodeDisplayName(nodeDisplayName);
	    dto.setNodeName(nodeName);
	    dto.setFolderName(folderName);
	    dto.setModifyBy(userCode);
	    dto.setRemark(remark);
	    System.out.println("publishFlag"+publishFlag);
	    if (publishFlag=='Y')
	    	dto.setPublishFlag('Y');
	    else
	    	dto.setPublishFlag('N');
	    if(isOnlyFolder == 'Y')
	    	dto.setNodeTypeIndi(NodeTypeIndi.FOLDER_NODE);
		else
			dto.setNodeTypeIndi(NodeTypeIndi.NORMAL_NODE);
	    dto.setRequiredFlag(RequiredFlag.NORMAL_NODE);  
	    docMgmtImpl.updateTemplateDetail(dto);
		
		return SUCCESS;
	}
	
	public char isOnlyFolder; 
	public int nodeId;
	public String nodeName;
	public String nodeDisplayName;
	public String folderName;
	public String remark;
	public char publishFlag;

	public char getPublishFlag() {
		return publishFlag;
	}
	public void setPublishFlag(char publishFlag) {
		this.publishFlag = publishFlag;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}

}
