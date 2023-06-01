
package com.docmgmt.struts.actions.workspace;

import java.util.Vector;

import com.docmgmt.dto.DTOClientMst;
import com.docmgmt.dto.DTOLocationMst;
import com.docmgmt.dto.DTOProjectMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class EDocSignWorkspaceSummarySearchPageForUserAction extends ActionSupport implements Preparable {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute(){
		return SUCCESS;
	}

	public Vector<DTOProjectMst> projectTypes;
	public Vector<DTOLocationMst> locations;
	public Vector<DTOClientMst> clients;
	public String mode="";
	public String userTypeName;
	public String eTMFCustomization;
	public void prepare()throws Exception
	{
		int usercode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int usergroupcode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		
		userTypeName=ActionContext.getContext().getSession().get("usertypename").toString();
		
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		eTMFCustomization = knetProperties.getValue("ETMFCustomization");
		eTMFCustomization = eTMFCustomization.toLowerCase();
		
		//projectTypes = docMgmtImpl.getProjectType();
		projectTypes = docMgmtImpl.getProjectTypeForESignDoc();
		locations = docMgmtImpl.getLocationDtl();
		clients = docMgmtImpl.getClientDetail();
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
		
	}	
}

