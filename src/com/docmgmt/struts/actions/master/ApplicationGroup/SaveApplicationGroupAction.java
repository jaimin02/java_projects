package com.docmgmt.struts.actions.master.ApplicationGroup;

import java.util.Vector;

import com.docmgmt.dto.DTOApplicationGroupMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveApplicationGroupAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		DTOApplicationGroupMst dto = new DTOApplicationGroupMst();
		dto.setRemark(remark);
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setModifyBy(userId);
	    dto.setApplicationName(applicationName.trim());
	    dto.setHostingCode(hostingId);
	    dto.setCategoryCode(catgoryId);
	    boolean applicationExist = docMgmtImpl.applicationNameExist(applicationName.trim());
	    if(!applicationExist)
	        docMgmtImpl.ApplicationMstOp(dto, 1,false);
		else{
			addActionError("Application Name Already Exists!");
			return INPUT;
		}
	    return SUCCESS;
	}
	
	public String DeleteApplication()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTOApplicationGroupMst dto =docMgmtImpl.getApplicationDetail(applicationCode);
		dto.setHostingCode(dto.getHostingCode());
		dto.setCategoryCode(dto.getCategoryCode());
		dto.setStatusIndi(statusIndi.charAt(0));
		dto.setRemark(remark);
		dto.setModifyBy(userId);
		docMgmtImpl.ApplicationMstOp(dto, 2,true);
	    return SUCCESS;
	}	
	
	public String applicationDetailHistory(){
		
		applicationDetailHistory = docMgmtImpl.getApplicationDetailHistory(applicationCode);
		return SUCCESS;
		
	}
	
	/*public String UpdateClient()
	{
		DTOClientMst dto = new DTOClientMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setRemark(remark);
		dto.setClientCode(clientCode);
		dto.setClientName(clientName.trim());
		dto.setModifyBy(userId);
	    boolean clientExist = docMgmtImpl.clientNameExist(clientName.trim());
	    if(!clientExist)
	    {
	    	docMgmtImpl.ClientMstOp(dto, 2,false);
	    }
	   	return SUCCESS;
	}*/
	public String applicationCode;
	public String applicationName;
	public String hostingId;
	public String catgoryId;
	public String remark;
	public String fileName;
	public String statusIndi;
	public Vector applicationDetailHistory ;
	
	
	public String getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}
	
	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	
	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getHostingId() {
		return hostingId;
	}

	public void setHostingId(String hostingId) {
		this.hostingId = hostingId;
	}

	public String getCatgoryId() {
		return catgoryId;
	}

	public void setCatgoryId(String catgoryId) {
		this.catgoryId = catgoryId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/*public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
*/

	
	
}
