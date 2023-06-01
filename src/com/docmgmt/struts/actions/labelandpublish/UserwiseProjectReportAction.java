package com.docmgmt.struts.actions.labelandpublish;

import java.util.Vector;

import com.docmgmt.dto.DTOLocationMst;
import com.docmgmt.dto.DTORoleMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserwiseProjectReportAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public Vector<DTOUserMst> userdata=new Vector<DTOUserMst>();
	private Vector usertypelist=new Vector();
	private String currentUserType;
	public boolean limitOk=true;
	public boolean activateOk=true;
	public String message;	
	public boolean blockFlag;
	public boolean delFlag;
	public String htmlContent;
	public String loginName;
	public String loginId;
	public Vector<DTOLocationMst> locationDtl=new Vector<DTOLocationMst>();
	public Vector<DTORoleMst> roleDtl=new Vector<DTORoleMst>();
	public String html;
	public String userName;
	public int userCode;
	public Vector<DTOWorkSpaceUserMst> wsList;

	public String lbl_folderName;
	public String lbl_nodeName;
	public String lbl_nodeDisplayName;
	public String workSpaceId;
	@Override
	public String execute()
	{
		
		currentUserType = (String)ActionContext.getContext().getSession().get("usertypename");
		
		userdata=docMgmtImpl.getAllUserDetailForProjects();
		return SUCCESS;
	}

	
	public String getUserwiseProjectInfoDetail(){
		
		wsList = new Vector<DTOWorkSpaceUserMst>();
		wsList = docMgmtImpl.getUserWiseWS(String.valueOf(userCode));
		htmlContent="";
		for (DTOWorkSpaceUserMst workspaceList : wsList) {
			if(!htmlContent.equals("")){
				htmlContent += "&"; 
			}
			htmlContent += workspaceList.getWorkSpaceId()+"::"+workspaceList.getWorkspacedesc();
		}
		
		return "html";
	}
	
	public String getUserwiseProjectInfoByUserAndWsID(){
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		lbl_folderName = knetProperties.getValue("ForlderName");
		lbl_nodeName = knetProperties.getValue("NodeName");	
		lbl_nodeDisplayName = knetProperties.getValue("NodeDisplayName");	
		getUserForProjectDeatil = new Vector<DTOWorkSpaceUserRightsMst>();
		Vector<DTOWorkSpaceUserRightsMst> dto = new Vector<DTOWorkSpaceUserRightsMst>();
		DTOUserMst userMst = docMgmtImpl.getUserInfo(userCode);
		
		String wsIds[] = workSpaceId.split(",");
		
		for(int i=0;i<wsIds.length;i++){
			workSpaceId = wsIds[i].trim();
			dto = new Vector<DTOWorkSpaceUserRightsMst>(); 
			dto = docMgmtImpl.getProjectForUserDetail(userCode,userMst.getUserGroupCode(),workSpaceId);
			getUserForProjectDeatil.addAll(dto);
		}
		return SUCCESS;
	}
	
	public Vector<DTOWorkSpaceUserRightsMst> getUserForProjectDeatil;
	
	public Vector<DTOWorkSpaceUserRightsMst> getGetFailScriptDeatil() {
		return getUserForProjectDeatil;
	}
	public void setGetFailScriptDeatil(Vector<DTOWorkSpaceUserRightsMst> getFailScriptDeatil) {
		this.getUserForProjectDeatil = getFailScriptDeatil;
	}
	
	public String getHtmlContent() {
		return htmlContent;
	}
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public Vector<DTOWorkSpaceUserMst> getWsList() {
		return wsList;
	}
	public void setWsList(Vector<DTOWorkSpaceUserMst> wsList) {
		this.wsList = wsList;
	}
	
}