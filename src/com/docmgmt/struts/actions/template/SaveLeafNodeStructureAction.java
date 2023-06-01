package com.docmgmt.struts.actions.template;
import com.docmgmt.dto.DTOTemplateNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveLeafNodeStructureAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	@Override
	public String execute()
	{
		templateId=ActionContext.getContext().getSession().get("templateId").toString();
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	
		DTOTemplateNodeDetail dto = new DTOTemplateNodeDetail();
		dto.setTemplateId(templateId);
		dto.setNodeId(nodeId);
		dto.setNodeName(nodeName);
		dto.setNodeDisplayName(nodeDisplayName);
		dto.setFolderName(folderName);
		dto.setNodeTypeIndi('N');
		dto.setRequiredFlag('N');
		if (publishFlag=='Y')
			dto.setPublishFlag('Y');
		else
			dto.setPublishFlag('N');
		dto.setRemark(remark);
		dto.setModifyBy(userCode);
		
		
	
		if(operation.equals("1")) // add last.
			docMgmtImpl.addChildNodeForStructure(dto); 
		
		else if(operation.equals("2")){ // add before
			docMgmtImpl.addChildNodeBeforeForStructure(dto);
		}	
		
		else if(operation.equals("3")){ // add after
			docMgmtImpl.addChildNodeAfterForStructure(dto);
		}
	
		return SUCCESS;
	}
	
	public String templateId;
	public int nodeId;
	public String operation;
	public String nodeName;
	public String nodeDisplayName;
	public String folderName;
	public String remark;
	public char publishFlag;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
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

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	
}
