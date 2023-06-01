package com.docmgmt.struts.actions.master.user;

import java.util.Vector;

import com.docmgmt.dto.DTOClientMst;
import com.docmgmt.dto.DTOLocationMst;
import com.docmgmt.dto.DTOProjectMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddUserToProject extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	 Vector<DTOProjectMst> projectTypes=new Vector<DTOProjectMst>();
	 Vector<DTOClientMst> clients=new Vector();
	 Vector<DTOLocationMst> locations=new Vector();
	 public String clientCodeGroup;
	 public String FosunChanges;
	@Override
	public String execute()
	{
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		KnetProperties knetProperties=KnetProperties.getPropInfo();
		FosunChanges=knetProperties.getValue("FosunCustomization");
		if(FosunChanges.equalsIgnoreCase("yes")){
			clientCodeGroup=docMgmtImpl.getUserGroupClientCode(userGroupCode);
		}
		projectTypes=docMgmtImpl.getProjectType();
		clients=docMgmtImpl.getClientDetail();
		locations=docMgmtImpl.getLocationDtl();
		for (int itrClient = 0; itrClient < clients.size() ; itrClient++) 
		 {
			 DTOClientMst dtoClientMst = clients.get(itrClient);
			 if(dtoClientMst.getStatusIndi() == 'D')
			 {
				 clients.remove(itrClient);
				 itrClient--;
			 }
		 }
		 for (int itrLocation = 0; itrLocation < locations.size() ; itrLocation++) 
		 {
			 DTOLocationMst dtoLocationMst = locations.get(itrLocation);
			 if(dtoLocationMst.getStatusIndi() == 'D')
			 {
				 locations.remove(itrLocation);
				 itrLocation--;
			 }
		 }
		 for (int itrProjectTypeDtl = 0; itrProjectTypeDtl < projectTypes.size() ; itrProjectTypeDtl++) 
		 {
			 DTOProjectMst dtoProjectType = projectTypes.get(itrProjectTypeDtl);
			 if(dtoProjectType.getStatusIndi() == 'D')
			 {
				 projectTypes.remove(itrProjectTypeDtl);
				 itrProjectTypeDtl--;
			 }
		 }
		return SUCCESS;
	}

	public Vector<DTOProjectMst> getProjectTypes() {
		return projectTypes;
	}
	public void setProjectTypes(Vector<DTOProjectMst> projectTypes) {
		this.projectTypes = projectTypes;
	}
	public Vector<DTOClientMst> getClients() {
		return clients;
	}
	public void setClients(Vector<DTOClientMst> clients) {
		this.clients = clients;
	}
	public Vector<DTOLocationMst> getLocations() {
		return locations;
	}
	public void setLocations(Vector<DTOLocationMst> locations) {
		this.locations = locations;
	}
}
