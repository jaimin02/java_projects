package com.docmgmt.struts.actions.master.user;

import java.util.Vector;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOUserTypeMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowUserOnProject extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String projectCode;
	public String clientCode;
	public String locationCode;
	public String alloweTMFCustomization;
	
	Vector workspacesummrydtl=new Vector();
	public Vector<DTOUserTypeMst> userTypes;
	
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	
	@Override
	public String execute()
	{
		DTOUserMst userMst = new DTOUserMst();
		 int usergrpcode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		 int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		 String userType = ActionContext.getContext().getSession().get("usertypename").toString();
		
		 PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	     alloweTMFCustomization = propertyInfo.getValue("ETMFCustomization");
		 alloweTMFCustomization= alloweTMFCustomization.toLowerCase();
		 		 
		 userMst.setUserGroupCode(usergrpcode);
		 userMst.setUserCode(userCode);
		 userMst.setUserType(userType);
		 
		 DTOWorkSpaceMst dto=new DTOWorkSpaceMst();
		 dto.setClientCode(clientCode);
		 dto.setProjectCode(projectCode);
		 //dto.setLocationCode(locationCode);
		 dto.setLocationCode("");
		 dto.setStatusIndi('A');        // for not show Archival Project On UserOnProject Module
		 
		 //workspacesummrydtl=docMgmtImpl.searchWorkspace(dto, userMst);
		 //workspacesummrydtl=docMgmtImpl.searchWorkspaceFromUserOnProject(dto, userMst);
		 workspacesummrydtl=docMgmtImpl.searchWorkspaceFromUserOnProjectList(dto, userMst);
		 userTypes=docMgmtImpl.getAllUserType();
		
		
		return SUCCESS;
	}
	public Vector getWorkspacesummrydtl() {
		return workspacesummrydtl;
	}
	public void setWorkspacesummrydtl(Vector workspacesummrydtl) {
		this.workspacesummrydtl = workspacesummrydtl;
	}
	

}
