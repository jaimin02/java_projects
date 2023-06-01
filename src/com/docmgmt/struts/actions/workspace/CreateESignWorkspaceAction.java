package com.docmgmt.struts.actions.workspace;

import java.util.Vector;

import com.docmgmt.dto.DTODepartmentMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CreateESignWorkspaceAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();	
	
	@Override
	public String execute(){
		
	
		KnetProperties knetProperties=KnetProperties.getPropInfo();
		String maxProjectsValue=knetProperties.getValue("MaxProjects");
		FosunChanges=knetProperties.getValue("FosunCustomization");
		//System.out.println("maxProjectsValue"+maxProjectsValue);
		int maxProjects=-1;
		if (maxProjectsValue!=null)
		{
			try
			{
				maxProjects=new Integer(maxProjectsValue).intValue();		
			}
			catch(NumberFormatException e)
			{
				System.out.println("Invalid value of maxProjects property in property file.");
				e.printStackTrace();
			}
		}
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String userTypeNam=ActionContext.getContext().getSession().get("usertypename").toString();
		Vector<DTOWorkSpaceMst> allWorkspace=docMgmtImpl.getAllWorkspaceForChangeStatus();
		//System.out.println("allWorkspace"+allWorkspace.size());
		int count=0;
		for (int i=0;i<allWorkspace.size();i++)
		{
			DTOWorkSpaceMst dtoWorkSpaceMst=allWorkspace.get(i);
			if (dtoWorkSpaceMst.getStatusIndi()!='D')
				count++;
		}
		//System.out.println("count"+count);
		//System.out.println("maxProjects"+maxProjects);		
		if (maxProjects>=0 && count>=maxProjects)
		{
			maxLimitExceeded="yes";
			addActionError("Maximum Limit of number of projects exceeded !!!<br>Please <a href='WorkspaceSummay.do'>click here</a> to delete unnecessary projects...");
			return SUCCESS;
		}
		else
			maxLimitExceeded="no";
		if(FosunChanges.equalsIgnoreCase("yes")){
			clientCodeGroup=docMgmtImpl.getUserGroupClientCode(userGroupCode);	
		}
		getLocationDtl = docMgmtImpl.getLocationDtl();
		getDeptDtl = docMgmtImpl.getDepartmentDetail();
		getClientDtl = docMgmtImpl.getClientDetail();
		//getTemplateDtl = docMgmtImpl.getAllTemplates();
		getTemplateDtl = docMgmtImpl.getTemplatesForCR();
		///getDeptDtl = docMgmtImpl.getDepartmentDetail();
		/*if(userTypeNam.equalsIgnoreCase("BD")){
			getTemplateDtl = docMgmtImpl.getAllTemplatesByDept("");
			getDeptDtl = docMgmtImpl.getDepartmentDetailByDeptCode("");
		}
		else {
			getTemplateDtl = docMgmtImpl.getAllTemplatesByDept(docMgmtImpl.getDeptCode(userId,userGroupCode));
			getDeptDtl = docMgmtImpl.getDepartmentDetailByDeptCode(docMgmtImpl.getDeptCode(userId,userGroupCode));	
		}*/
		//getDeptDtl = docMgmtImpl.getDepartmentInfo(docMgmtImpl.getDeptCode(userId,userGroupCode));
		//getProjectDtl = docMgmtImpl.getProjectType();
		getProjectDtl = docMgmtImpl.getProjectTypeForESign();
		//getTemplateDtl = docMgmtImpl.getAllTemplates();
		
		//User will be able to see all the admin users of the same group only...
		getUserDtl = docMgmtImpl.getuserDetailsByUserGrp(userGroupCode);
		//getUserDtl = docMgmtImpl.getWAUserGrpdetails(userId);
		//getDocTypeDtl = docMgmtImpl.getDocTypeDetail();
		//getAttrGroupDtl = docMgmtImpl.getAllAttributeGroup(); //See Below Comments and Code
		
		
		return SUCCESS;
	}
	
	/**********************************************/
	public String clientCodeGroup;
	public Vector getLocationDtl;
	public Vector getClientDtl;
	public Vector<DTODepartmentMst> getDeptDtl;
	public Vector getProjectDtl;
	public Vector getTemplateDtl;
	public Vector getUserDtl;
	public String maxLimitExceeded;
	public String FosunChanges;
//	public Vector getDocTypeDtl;
/*	public Vector getAttrGroupDtl;
	
	public Vector getGetAttrGroupDtl() {
		return getAttrGroupDtl;
	}

	public void setGetAttrGroupDtl(Vector getAttrGroupDtl) {
		this.getAttrGroupDtl = getAttrGroupDtl;
	}
*/
/*	public Vector getGetDocTypeDtl() {
		return getDocTypeDtl;
	}

	public void setGetDocTypeDtl(Vector getDocTypeDtl) {
		this.getDocTypeDtl = getDocTypeDtl;
	}
*/
	public Vector getGetUserDtl() {
		return getUserDtl;
	}

	public void setGetUserDtl(Vector getUserDtl) {
		this.getUserDtl = getUserDtl;
	}

	public Vector getGetTemplateDtl() {
		return getTemplateDtl;
	}

	public void setGetTemplateDtl(Vector getTemplateDtl) {
		this.getTemplateDtl = getTemplateDtl;
	}

	public Vector getGetProjectDtl() {
		return getProjectDtl;
	}

	public void setGetProjectDtl(Vector getProjectDtl) {
		this.getProjectDtl = getProjectDtl;
	}

	public Vector<DTODepartmentMst> getGetDeptDtl() {
		return getDeptDtl;
	}

	public void setGetDeptDtl(Vector<DTODepartmentMst> getDeptDtl) {
		this.getDeptDtl = getDeptDtl;
	}

	public Vector getGetClientDtl() {
		return getClientDtl;
	}

	public void setGetClientDtl(Vector getClientDtl) {
		this.getClientDtl = getClientDtl;
	}

	public Vector getGetLocationDtl() {
		return getLocationDtl;
	}

	public void setGetLocationDtl(Vector getLocationDtl) {
		this.getLocationDtl = getLocationDtl;
	}
 	public String workSpaceDesc;

	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}

	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}
 	
}

