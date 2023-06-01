package com.docmgmt.struts.actions.master.usergroup;

import java.util.Vector;

import com.docmgmt.dto.DTOClientMst;
import com.docmgmt.dto.DTODepartmentMst;
import com.docmgmt.dto.DTOLocationMst;
import com.docmgmt.dto.DTOProjectMst;
import com.docmgmt.dto.DTOUserGroupMst;
import com.docmgmt.dto.DTOUserTypeMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddUserGroup extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
 	Vector<DTOUserGroupMst> userGroupDtl = new Vector<DTOUserGroupMst>();
	Vector<DTOClientMst> clientDtl=new Vector<DTOClientMst>();
	Vector<DTOLocationMst> locationDtl=new Vector<DTOLocationMst>();
	Vector<DTOUserTypeMst> getAllUserType=new Vector<DTOUserTypeMst>(); 
	Vector<DTODepartmentMst> deptDtl=new Vector<DTODepartmentMst>();
	Vector<DTOProjectMst> projectTypeDtl=new Vector<DTOProjectMst>();
	public String errorMsg;
	
	@Override
	public String execute()
	{
		 userGroupDtl = docMgmtImpl.getUserGroupDtl();
		 clientDtl = docMgmtImpl.getClientDetail();
		 locationDtl = docMgmtImpl.getLocationDtl();
		 deptDtl = docMgmtImpl.getDepartmentDetail();
		 getAllUserType   = docMgmtImpl.getAllUserType();
		 projectTypeDtl = docMgmtImpl.getProjectType();
		 
		 String currentUserType;
		 
			//Added on 14-5-2015 by dharmendra jadav 
			// Start coding
			 currentUserType = (String)ActionContext.getContext().getSession().get("usertypename");
				
				for(int index=0;index<getAllUserType.size();index++){
					
					DTOUserTypeMst userTypeDtl=(DTOUserTypeMst) getAllUserType.get(index);
					
					if(currentUserType.equalsIgnoreCase("WA")){
						
						if(userTypeDtl.getUserTypeName().equalsIgnoreCase("PU")){
							getAllUserType.removeElement(userTypeDtl);
							
						}
					}
		
					if(currentUserType.equalsIgnoreCase("PU")){
						
						if( userTypeDtl.getUserTypeName().equalsIgnoreCase("PU")){
							getAllUserType.removeElement(userTypeDtl);
						}
					}
				}
				
			// End coding
				
		 for (int itrClient = 0; itrClient < clientDtl.size() ; itrClient++) 
		 {
			 DTOClientMst dtoClientMst = clientDtl.get(itrClient);
			 if(dtoClientMst.getStatusIndi() == 'D')
			 {
				 clientDtl.remove(itrClient);
				 itrClient--;
			 }
		 }
		 for (int itrLocation = 0; itrLocation < locationDtl.size() ; itrLocation++) 
		 {
			 DTOLocationMst dtoLocationMst = locationDtl.get(itrLocation);
			 if(dtoLocationMst.getStatusIndi() == 'D')
			 {
				 locationDtl.remove(itrLocation);
				 itrLocation--;
			 }
		 }
		 for (int itrDept = 0; itrDept < deptDtl.size() ; itrDept++) 
		 {
			 DTODepartmentMst dtoDeptMst = deptDtl.get(itrDept);
			 if(dtoDeptMst.getStatusIndi() == 'D')
			 {
				 deptDtl.remove(itrDept);
				 itrDept--;
			 }
		 }
		 for (int itrProjectTypeDtl = 0; itrProjectTypeDtl < projectTypeDtl.size() ; itrProjectTypeDtl++) 
		 {
			 DTOProjectMst dtoProjectType = projectTypeDtl.get(itrProjectTypeDtl);
			 if(dtoProjectType.getStatusIndi() == 'D')
			 {
				 projectTypeDtl.remove(itrProjectTypeDtl);
				 itrProjectTypeDtl--;
			 }
		 }
		return SUCCESS;
	}
	
	public Vector<DTOUserGroupMst> getUserGroupDtl() {
		return userGroupDtl;
	}
	public void setUserGroupDtl(Vector<DTOUserGroupMst> userGroupDtl) {
		this.userGroupDtl = userGroupDtl;
	}
	public Vector<DTOClientMst> getClientDtl() {
		return clientDtl;
	}
	public void setClientDtl(Vector<DTOClientMst> clientDtl) {
		this.clientDtl = clientDtl;
	}
	public Vector<DTOLocationMst> getLocationDtl() {
		return locationDtl;
	}
	public void setLocationDtl(Vector<DTOLocationMst> locationDtl) {
		this.locationDtl = locationDtl;
	}
	public Vector<DTOUserTypeMst> getGetAllUserType() {
		return getAllUserType;
	}
	public void setGetAllUserType(Vector<DTOUserTypeMst> getAllUserType) {
		this.getAllUserType = getAllUserType;
	}
	public Vector<DTODepartmentMst> getDeptDtl() {
		return deptDtl;
	}
	public void setDeptDtl(Vector<DTODepartmentMst> deptDtl) {
		this.deptDtl = deptDtl;
	}
	public Vector<DTOProjectMst> getProjectTypeDtl() {
		return projectTypeDtl;
	}
	public void setProjectTypeDtl(Vector<DTOProjectMst> projectTypeDtl) {
		this.projectTypeDtl = projectTypeDtl;
	}
}
