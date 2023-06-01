package com.docmgmt.struts.actions.reports;
import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowPendingReportsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String elecSig;	
	public String fileWithPath;
	
	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGrpCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		dto.setModifyBy(userId);
		dto.setWorkSpaceId(workSpaceId);
		dto.setNodeId(0);
		//getPendingWorks = docMgmtImpl.getMyPendingWorksReport(dto);
		getPendingWorks = docMgmtImpl.getMyPendingWorksReportCSV(dto);
		//getPendingWorks = docMgmtImpl.getMypendingWorkNew(workSpaceId, userId);
		//getNextStageId = docMgmtImpl.getMyPendingWorksReportForNextStage(dto);
		userTypeName=docMgmtImpl.getUserTypeName(userId);
		
		KnetProperties knetProperties=KnetProperties.getPropInfo();
		elecSig=knetProperties.getValue("ElectronicSignature");
		//by default electronic signature will not be asked from user, if not specified in property file
		if (elecSig==null)
			elecSig="";
		//System.out.println("elecSig"+elecSig);
		
		return SUCCESS;
	}

	public String pdfDowloadRestrict(){
	
	System.out.println("fileWithPath :" + fileWithPath);
	return SUCCESS;
	}
	public Vector<DTOWorkSpaceNodeHistory> getNextStageId;
	public Vector<DTOWorkSpaceNodeHistory> getPendingWorks;
	public String workSpaceId;
	public String targetDivId;
	public String userTypeName;

	public String getTargetDivId() {
		return targetDivId;
	}
	public void setTargetDivId(String targetDivId) {
		this.targetDivId = targetDivId;
	}
	public Vector<DTOWorkSpaceNodeHistory> getGetPendingWorks() {
		return getPendingWorks;
	}
	public void setGetPendingWorks(Vector<DTOWorkSpaceNodeHistory> getPendingWorks) {
		this.getPendingWorks = getPendingWorks;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public Vector<DTOWorkSpaceNodeHistory> getGetNextStageId() {
		return getNextStageId;
	}
	public void setGetNextStageId(Vector<DTOWorkSpaceNodeHistory> getNextStageId) {
		this.getNextStageId = getNextStageId;
	}
	
}
	
	


