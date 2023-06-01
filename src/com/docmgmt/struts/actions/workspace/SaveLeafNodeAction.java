package com.docmgmt.struts.actions.workspace;



import java.sql.Timestamp;
import java.util.Date;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.GenerateTree;
import com.docmgmt.server.webinterface.entities.Node.NodeTypeIndi;
import com.docmgmt.server.webinterface.entities.Node.RequiredFlag;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveLeafNodeAction extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userTypeCode =ActionContext.getContext().getSession().get("usertypecode").toString();
		Timestamp ts = new Timestamp(new Date().getTime());
		
		DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
		dto.setWorkspaceId(workspaceId);
		dto.setNodeId(nodeId);
		dto.setNodeName(nodeName);
		dto.setNodeDisplayName(nodeDisplayName);
		dto.setFolderName(folderName);
		dto.setCloneFlag('N');
	
		if(isOnlyFolder == 'Y')
			dto.setNodeTypeIndi(NodeTypeIndi.FOLDER_NODE);
		else
			dto.setNodeTypeIndi(NodeTypeIndi.NORMAL_NODE);
	
		dto.setRequiredFlag(RequiredFlag.NORMAL_NODE);
		
		if (isPublishable == 'Y')
			dto.setPublishedFlag('Y');
		else
			dto.setPublishedFlag('N');
		
		dto.setRemark(remark);
		dto.setModifyBy(userId);
		dto.setModifyOn(ts);
		
		if(operation.equals("1")) // add last.
			docMgmtImpl.addChildNode(dto); 
		else if(operation.equals("2")) // add before
			docMgmtImpl.addChildNodeBefore(dto);
		else if(operation.equals("3")) // add after
			docMgmtImpl.addChildNodeAfter(dto);
		
		GenerateTree tree= new GenerateTree();
        tree.generateTree(workspaceId,project_type,userId,userGroupCode,userTypeCode);
        tree=null;
		
		
		return SUCCESS;
	}
	
	public int nodeId;
	public String workspaceId;
	public String STFNode;
	public String project_type;
	public String operation;
	public String nodeName;
	public String nodeDisplayName;
	public String folderName;
	public String remark;
	public char isPublishable;
	public char isOnlyFolder;
	
}
