package com.docmgmt.struts.actions.workspace;

import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.webinterface.entities.Node.NodeTypeIndi;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EditProjectNodeAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();

	@Override
	public String execute() {

		int userId = Integer.parseInt(ActionContext.getContext().getSession()
				.get("userid").toString());
		workspaceId = ActionContext.getContext().getSession().get("ws_id")
				.toString();
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		eTMFCustomization = knetProperties.getValue("ETMFCustomization");
		System.out.println("eTMFCustomization------"+eTMFCustomization);
		DTOWorkSpaceNodeDetail dto = docMgmtImpl.getNodeDetail(workspaceId,
				Integer.parseInt(nodeId)).get(0);
				
		dto.setWorkspaceId(workspaceId);
		dto.setNodeId(Integer.parseInt(nodeId));
		if(eTMFCustomization.equalsIgnoreCase("yes"))
		{
			dto.setNodeDisplayName(nodeDisplayName);
			//dto.setNodeDisplayName(dto.getNodeDisplayName());
			dto.setNodeName(nodeName);
		}
		dto.setFolderName(folderName);
		dto.setUserCode(userId);
		dto.setModifyBy(userId);
		dto.setRemark(remark);
		dto.setPublishedFlag((isPublishable == null)?'N':'Y');
		if (isOnlyFolder == 'Y')
			dto.setNodeTypeIndi(NodeTypeIndi.FOLDER_NODE);
		else
			dto.setNodeTypeIndi(dto.getNodeTypeIndi());

		docMgmtImpl.insertWorkspaceNodeDetail(dto, 2);
		dto.setStatusIndi('E');
		docMgmtImpl.insertWorkspaceNodeDetailhistory(dto, 1);

		return SUCCESS;
	}

	public char isOnlyFolder;
	public String isPublishable;
	public String workspaceId;
	public String folderName;
	public String nodeDisplayName;
	public String nodeName;
	public String nodeId;
	public String remark;
	public String eTMFCustomization;

}
