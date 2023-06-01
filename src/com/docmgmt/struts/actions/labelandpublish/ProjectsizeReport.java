package com.docmgmt.struts.actions.labelandpublish;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Vector;

import com.docmgmt.dto.DTOProjectMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ProjectsizeReport extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public Vector <DTOWorkSpaceNodeHistory> getLeafNode;
	public Vector<DTOWorkSpaceMst> getWorkspaceDetail;
	public int userId; 
	public int userGroupCode; 
	public String workSpaceId;
	public String lbl_folderName;
	public String lbl_nodeName;
	public float totalSize;
	public float getPdfpublishSize;
	public Vector<DTOProjectMst> projectTypes;
	public float sum = 0;
	public ArrayList<String> Workflow = new ArrayList<String>();
	public String htmlContent;
	public String projectIdEsign;
	
	public String execute(){
	
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	projectIdEsign = knetProperties.getValue("EsignatureId");
	userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
	userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
	projectTypes = docMgmtImpl.getProjectTypeForSize();
	//getWorkspaceDetail = docMgmtImpl.getUserWorkspaceForSerachProjectList(userGroupCode, userId);
	
	return SUCCESS;
	}
	
	public String workflowWiseProjects(){
		
		getWorkspaceDetail = docMgmtImpl.getUserWorkspaceForSerachProjectList(userGroupCode, userId);
		htmlContent="";
		for (DTOWorkSpaceMst workspaceList : getWorkspaceDetail) {
			if(!htmlContent.equals("")){
				htmlContent += "&"; 
			}
			htmlContent += workspaceList.getWorkSpaceId()+"::"+workspaceList.getWorkSpaceDesc();
		}
		
		return "html";
	}
	
	public String getProjectwisesizeInfo(){
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		lbl_folderName = knetProperties.getValue("ForlderName");
		lbl_nodeName = knetProperties.getValue("NodeName");
		try{
		getLeafNode =docMgmtImpl.getLeafNode(workSpaceId);
		
		for(int i=0;i<getLeafNode.size();i++)
			sum =(getLeafNode.get(i).getTotalFileSize() + sum);
		
		Formatter formatter = new Formatter();
		formatter.format("%.2f", sum);
		sum = Float.parseFloat(formatter.toString());
		getPdfpublishSize = docMgmtImpl.getPdfpublishSize(workSpaceId);
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
	return SUCCESS;
	}	
	
}