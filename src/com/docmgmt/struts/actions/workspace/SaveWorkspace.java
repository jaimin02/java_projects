package com.docmgmt.struts.actions.workspace;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOTemplateMst;
import com.docmgmt.dto.DTOUserGroupMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.entities.Workspace.ProjectType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveWorkspace extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();	
	
	//added on 29/05/2015 by Dharmendra jadav
	//start
	
	public Vector WorkspaceUserDetailList;
	public Vector getStageDetail;
	private Vector userGroupDtl; 
	public int[] userCodes;
	Date fromDt;
	Date toDt;
	ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
	DTOWorkSpaceUserMst dtoWorkSpaceUserMst = new DTOWorkSpaceUserMst();
	DTOWorkSpaceUserRightsMst dtowsurm = new DTOWorkSpaceUserRightsMst();
	
	//end
	
	@Override
	public String execute()
	{
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		userCode = userId;
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		boolean workspaceexist = docMgmtImpl.workspaceNameExist(workSpaceDesc);
		String clientname = docMgmtImpl.getClientName(clientCode);
		String projectCodeName = propertyInfo.getValue("ProjectCodeName");
		copyRights="Y";
		projectFor="E";
		if(workspaceexist == false)
		{
			DTOWorkSpaceMst dto = new DTOWorkSpaceMst();
			dto.setWorkSpaceDesc(workSpaceDesc);
			//dto.setLocationCode(locationCode);
			dto.setLocationCode("0001");
			dto.setDeptCode(deptCode);
			dto.setClientCode(clientCode);
			dto.setClientName(clientname);
			dto.setProjectCode("0002");
			dto.setDocTypeCode("0001");//Default Project Type has been Commented from JSP because of no use
			dto.setTemplateId(templateId);
			if(remark.isEmpty() || remark.equals("")){
				dto.setRemark("New");
			}else{
			dto.setRemark(remark);
			}
			dto.setProjectCodeName(projectCodeName);
            templateDetail = docMgmtImpl.getTemplateDetailById(templateId);
			DTOTemplateMst dtoTemplateMst = (DTOTemplateMst)templateDetail.get(0);
			dto.setTemplateDesc(dtoTemplateMst.getTemplateDesc());
			dto.setModifyBy(userId);
			dto.setApplicationCode(applicationCode);
			dto.setVerion(version);
		//	templateNodeAttrDetailVector = docMgmtImpl.getTemplateNodeAttr(templateId);
		    //Vector attrmatrix = attrService.getAttributeGroupMatrixValues(attrGroupId);
	        boolean insertStatus=docMgmtImpl.AddUpdateWorkspace(dto, userCode, projectFor, templateId,1);
	        char projectType = ProjectType.DMS_SKELETON;
	        if(insertStatus)
	        {
	        	String workspaceId=docMgmtImpl.getWorkspaceID(dto.getWorkSpaceDesc());
	        	addActionMessage("<a href='WorkspaceOpen.do?ws_id=" + workspaceId + "'>" + workSpaceDesc + "</a> created successfully");
	        	
	        	//added on 29/5/2015 by Dharmendra jadav
	    		//start
	        	
	        	String userType=docMgmtImpl.getUserTypeName(userCode);
	        	
				 boolean enablePowerUser = propertyInfo.getValue("EnablePowerUser")
		        	.equalsIgnoreCase("yes") ? true : false;
	        	//added on 29/5/2015 by Dharmendra jadav
	    		//start
	        	if(!userType.equalsIgnoreCase("PU") && enablePowerUser){
		    		WorkspaceUserDetailList=docMgmtImpl.getWorkspaceUserDetailList(workspaceId);
		    		getStageDetail = docMgmtImpl.getStageDetail();
		    		userGroupDtl=docMgmtImpl.getUserGroupDtl();
		    		
		    		dtoWorkSpaceUserMst.setWorkSpaceId(workspaceId);
		    		dtowsurm.setWorkSpaceId(workspaceId);
		    		
		    		for (int iWorkSpacrUser = 0; iWorkSpacrUser < WorkspaceUserDetailList.size(); iWorkSpacrUser++) {
	
						DTOWorkSpaceUserMst workspaceUserDtl = (DTOWorkSpaceUserMst) WorkspaceUserDetailList.get(iWorkSpacrUser);
						fromDt = workspaceUserDtl.getFromDt();
						toDt = workspaceUserDtl.getToDt();
					
		    		}
						for (int index = 0; index < userGroupDtl.size(); index++) {
		
							DTOUserGroupMst userGroupmst = (DTOUserGroupMst) userGroupDtl.get(index);
							if (userGroupmst.getUserTypeName().equalsIgnoreCase("PU")) {
		
								Vector<DTOUserMst> workspaceUserDetails = docMgmtImpl.getuserDetailsByUserGrp(userGroupmst.getUserGroupCode());
								userCodes = new int[workspaceUserDetails.size()];
								
								for (int i = 0; i < workspaceUserDetails.size(); i++) {
									
									DTOUserMst userMstDTO = workspaceUserDetails.get(i);
									userCodes[i] = userMstDTO.getUserCode();
								}
		
								dtoWorkSpaceUserMst.setUserGroupCode(userGroupmst.getUserGroupCode());
								dtowsurm.setUserGroupCode(userGroupmst.getUserGroupCode());
		
								dtoWorkSpaceUserMst.setAdminFlag('N');
								Timestamp ts = new Timestamp(new Date().getTime());
								dtoWorkSpaceUserMst.setLastAccessedOn(ts);
								dtoWorkSpaceUserMst.setRemark(remark);
								int ucd = Integer.parseInt(ActionContext.getContext()
										.getSession().get("userid").toString());
								dtoWorkSpaceUserMst.setModifyBy(ucd);
								dtoWorkSpaceUserMst.setModifyOn(ts);
								Date fromdt = fromDt;
								dtoWorkSpaceUserMst.setFromDt(fromdt);
								Date todt = toDt;
								dtoWorkSpaceUserMst.setToDt(todt);
								dtoWorkSpaceUserMst.setRightsType("projectwise");
								docMgmtImpl.insertUpdateUsertoWorkspace(dtoWorkSpaceUserMst, userCodes);
		
								dtowsurm.setCanReadFlag('Y');
								dtowsurm.setAdvancedRights("Y");
								dtowsurm.setCanDeleteFlag('Y');
								dtowsurm.setCanAddFlag('Y');
								dtowsurm.setCanEditFlag('Y');
								dtowsurm.setRemark(remark);
								dtowsurm.setModifyBy(ucd);
								dtowsurm.setModifyOn(ts);
								dtowsurm.setStatusIndi('N');
		
								for (int iUserCode = 0; iUserCode < userCodes.length; iUserCode++) {
									for (int stage = 0; stage < getStageDetail.size(); stage++) {
		
										DTOStageMst stageDtl = (DTOStageMst) getStageDetail.get(stage);
										DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMstClone = dtowsurm.clone();
										dtoWorkSpaceUserRightsMstClone.setUserCode(userCodes[iUserCode]);
										dtoWorkSpaceUserRightsMstClone.setStageId(stageDtl.getStageId());
										userRightsList.add(dtoWorkSpaceUserRightsMstClone);
		
									}
								}
								docMgmtImpl.insertMultipleUserRights(userRightsList);
		
							}
						}
	        		}
				//end
	    	
		        if (projectFor.charAt(0)==(projectType)) 
		        	return "SKELETON";
	        }
	        else
	        {
	        	addActionError("Error while creating project.");
	        }
	        
	        if(copyRights.equals("Y"))
	        {
	        	docMgmtImpl.addOrUpdateRights(templateId, userCode);
	        }
	        
	        return SUCCESS;
		}
		else
		{
			addActionError("Project already exists.");
			return INPUT;
		}
			
	}
	
	public Vector templateDetail;
	public Vector templateNodeAttrDetailVector;
//	public String attrGroupId;
	public String workSpaceDesc;
	public String version;
	public String templateId;
	public String locationCode;
	public String deptCode;
	public String clientCode;
	public String docTypeCode;
	public String projectCode;
	public String projectFor;
	public String remark;
	public int userCode;
	public String copyRights;
	public String applicationCode;
	
	
/*	public String getAttrGroupId() {
		return attrGroupId;
	}
	public void setAttrGroupId(String attrGroupId) {
		this.attrGroupId = attrGroupId;
	}
*/	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}
	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getDocTypeCode() {
		return docTypeCode;
	}
	public void setDocTypeCode(String docTypeCode) {
		this.docTypeCode = docTypeCode;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getProjectFor() {
		return projectFor;
	}
	public void setProjectFor(String projectFor) {
		this.projectFor = projectFor;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCopyRights() {
		return copyRights;
	}
	public void setCopyRights(String copyRights) {
		this.copyRights = copyRights;
	}
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	
	
	//added on 29/5/2015
	//start
	
	public Vector getWorkspaceUserDetailList() {
		return WorkspaceUserDetailList;
	}

/*	public void setWorkspaceUserDetailList(Vector workspaceUserDetailList) {
		WorkspaceUserDetailList = workspaceUserDetailList;
	}*/
	
	public Vector getGetStageDetail() {
		return getStageDetail;
	}

	/*public void setGetStageDetail(Vector getStageDetail) {
		this.getStageDetail = getStageDetail;
	}
	*/
	
	public int[] getUserCodes() {
		return userCodes;
	}


	public void setUserCode(int[] userCodes) {
		this.userCodes = userCodes;
	}
	
	//end
	
	
}
	
	


