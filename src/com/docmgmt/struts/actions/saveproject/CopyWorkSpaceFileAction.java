package com.docmgmt.struts.actions.saveproject;



import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.dto.DTOSaveProjectAs;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.*;
import java.util.Vector;

public class CopyWorkSpaceFileAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute(){
	
		
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
    	
    	
    	getSelectedNodes = selectedNodes;
    	String selectedNodes[] = getSelectedNodes.split("#");
    	
    	System.out.println(" Copy Files Action  " + sourceWsId + " " + destWsId + " " + selectedNodes.length);
    	
    	Vector allleafnodes = new Vector();
    	
    	for(int i=0;i<selectedNodes.length;i++)
    	{
    		if(selectedNodes[i]!=null)
    		{
    			System.out.println(selectedNodes[i]);
    		}
    		
    	}
    	
    	
    	
    	DTOSaveProjectAs dto = new DTOSaveProjectAs();
    	dto.setSourcWsId(sourceWsId);
    	dto.setDestWsId(destWsId);
    	dto.setIModifyBy(userCode);
    	dto.setSelectedNodes(selectedNodes);
    	
    	noOfFilesCopied = docMgmtImpl.copyFilesForProjectSaveAs(dto);
    	System.out.println("noOfFilesCopied" + noOfFilesCopied);
    	int tranno = docMgmtImpl.getMaxTranNo(sourceWsId,0);
    	System.out.println("tranno" + tranno);
    	if (tranno > 0)
    	{
    		DTOWorkSpaceMst dtoWorkSpaceMst = docMgmtImpl.getWorkSpaceDetail(destWsId);
    		dtoWorkSpaceMst.setLastTranNo(tranno+1);
    		boolean result = docMgmtImpl.AddUpdateWorkspace(dtoWorkSpaceMst, userCode, String.valueOf(dtoWorkSpaceMst.getProjectType()),dtoWorkSpaceMst.getTemplateId(),2);
    		System.out.println("result" + result);
    	}
		return SUCCESS;
		
	}
	public int noOfFilesCopied;
	public String sourceWsId;
	public String  destWsId;
	public String  selectedNodes;
	public String  getSelectedNodes;

	public String getSourceWsId() {
		return sourceWsId;
	}
	public void setSourceWsId(String sourceWsId) {
		this.sourceWsId = sourceWsId;
	}
	public String getDestWsId() {
		return destWsId;
	}
	public void setDestWsId(String destWsId) {
		this.destWsId = destWsId;
	}
	public String getSelectedNodes() {
		return selectedNodes;
	}
	public void setSelectedNodes(String selectedNodes) {
		this.selectedNodes = selectedNodes;
	}
	public String getGetSelectedNodes() {
		return getSelectedNodes;
	}
	public void setGetSelectedNodes(String getSelectedNodes) {
		this.getSelectedNodes = getSelectedNodes;
	}
}
