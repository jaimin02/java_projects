package com.docmgmt.struts.actions.search;

import java.util.Vector;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class HighLevelSearchAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	
	@Override
	public String execute(){
	
		int userGroupCode =  Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTOUserMst dto = new DTOUserMst();
		dto.setUserCode(userId);
		dto.setUserGroupCode(userGroupCode);
		clientForSearch = docMgmtImpl.getClientForSearch(userId);
		locationForSearch = docMgmtImpl.getLocationForSearch(userId);
		projectForSearch = docMgmtImpl.getProjectForSearch(userId);
		userDetailsByUserGrp = getUserDetailsByUserGrp(dto);
		userWorkspace = docMgmtImpl.getUserWorkspace(userGroupCode, userId);
		return SUCCESS;
	}
	
	public Vector clientForSearch;
	public Vector locationForSearch;
	public Vector projectForSearch;
	public Vector userDetailsByUserGrp;
	public Vector userWorkspace;
	
	public Vector getUserWorkspace() {
		return userWorkspace;
	}
	public void setUserWorkspace(Vector userWorkspace) {
		this.userWorkspace = userWorkspace;
	}
	public Vector getClientForSearch() {
		return clientForSearch;
	}
	public void setClientForSearch(Vector clientForSearch) {
		this.clientForSearch = clientForSearch;
	}
	public Vector getLocationForSearch() {
		return locationForSearch;
	}
	
	public void setLocationForSearch(Vector locationForSearch) {
		this.locationForSearch = locationForSearch;
	}
	public Vector getProjectForSearch() {
		return projectForSearch;
	}
	public void setProjectForSearch(Vector projectForSearch) {
		this.projectForSearch = projectForSearch;
	}
	public Vector getUserDetailsByUserGrp() {
		return userDetailsByUserGrp;
	}
	public void setUserDetailsByUserGrp(Vector userDetailsByUserGrp) {
		this.userDetailsByUserGrp = userDetailsByUserGrp;
	}
	
	
public Vector getUserDetailsByUserGrp(DTOUserMst userMst){
    	
		
    	Vector userDetailsByUserGrpDtl = docMgmtImpl.getuserDetailsByUserGrp(userMst.getUserGroupCode());
    	Vector userDetailsByUserGrpDtlALL= new Vector();
    	
    	DTOUserMst userMstNew = new DTOUserMst();
    	userMstNew.setUserCode(0);
    	userMstNew.setLoginName("---ALL----");
    	userMstNew.setUserName("---ALL----");
    	userDetailsByUserGrpDtlALL.add(userMstNew);
    	userMstNew = null;
    	
    	for(int i = 0 ; i < userDetailsByUserGrpDtl.size(); i++) {
    		
    		DTOUserMst  userMstNew1 = new DTOUserMst();
    		userMstNew1 = (DTOUserMst)userDetailsByUserGrpDtl.elementAt(i);
    		
    		userDetailsByUserGrpDtlALL.add(userMstNew1);
    		userMstNew1 = null;    		
    	}
    	
    	return userDetailsByUserGrpDtlALL;
    	
    }
    
   
}

