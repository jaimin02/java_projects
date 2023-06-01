package com.docmgmt.struts.actions.TRMS;

import java.util.ArrayList;

import com.docmgmt.dto.DTOTrainingRecordDetails;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TRDetailsAction extends ActionSupport {
	private static final long serialVersionUID = -147635989925105767L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String htmlContent = "";
	public String trainingRecordNo;
	public String trainingId;
	public String trainingHdr;
	public String trainingDtl;
	public String totalTrainingDuration;
	public String refWorkspaceId = "-999";
	public String remark;
	public String selectedeWorkspace;
	public boolean update = false;
	public DTOTrainingRecordDetails dtoTrainingRecordDtls = new DTOTrainingRecordDetails();
	public ArrayList<DTOTrainingRecordDetails> trainngRecordDetailsList = new ArrayList<DTOTrainingRecordDetails>();
	public ArrayList<DTOWorkSpaceMst> workspaceMstList = new ArrayList<DTOWorkSpaceMst>();

	@Override
	public String execute() throws Exception 
	{
		//trainngRecordDetailsList = docMgmtImpl.getAllTRDetailList(1);
		workspaceMstList = docMgmtImpl.getWorkSpaceDetailByTemplate(null);
		return SUCCESS;
	}
	
	public String save()
	{
		int userId=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String msg="";
		DTOTrainingRecordDetails dtoTRDtl = new DTOTrainingRecordDetails();
		dtoTRDtl.setTrainingId(trainingId);
		dtoTRDtl.setTrainingHdr(trainingHdr);
		dtoTRDtl.setTrainingDtl(trainingDtl);
		dtoTRDtl.setTotalTrainingDuration(Integer.parseInt(totalTrainingDuration));
		dtoTRDtl.setRefWorkspaceId(refWorkspaceId);
		dtoTRDtl.setRemark(remark);
		dtoTRDtl.setModifyBy(userId);
		if (update)
		{
			dtoTRDtl.setStatusIndi('E');
			dtoTRDtl.setTrainingRecordNo(Integer.parseInt(trainingRecordNo));
			msg = "Training Record Updated successfully.";
		}
		else
		{
			dtoTRDtl.setStatusIndi('N');
			msg = "Training Record Created successfully.";
		}
		ArrayList<DTOTrainingRecordDetails> tRDtlList = new ArrayList<DTOTrainingRecordDetails>();
		tRDtlList.add(dtoTRDtl);
		procedureCall(tRDtlList);
		
		htmlContent = msg;
		return SUCCESS;
	}
	
	public String edit()
	{
		DTOTrainingRecordDetails dtoTRDtl = new DTOTrainingRecordDetails();
		dtoTRDtl.setTrainingRecordNo(Integer.parseInt(trainingRecordNo));
		ArrayList<DTOTrainingRecordDetails> tRDtlList = getTRDtlList(dtoTRDtl);
		dtoTrainingRecordDtls = tRDtlList.get(0);
		workspaceMstList = docMgmtImpl.getWorkSpaceDetailByTemplate(null);
		return SUCCESS;
	}
	
	
	public String delete()
	{
		int userId=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTOTrainingRecordDetails dtoTRDtl = new DTOTrainingRecordDetails();
		dtoTRDtl.setTrainingRecordNo(Integer.parseInt(trainingRecordNo));
		ArrayList<DTOTrainingRecordDetails> tRDtlList = getTRDtlList(dtoTRDtl);
		dtoTRDtl = tRDtlList.get(0);
		dtoTRDtl.setStatusIndi('D');
		dtoTRDtl.setModifyBy(userId);
		tRDtlList = new ArrayList<DTOTrainingRecordDetails>();
		tRDtlList.add(dtoTRDtl);
		procedureCall(tRDtlList);
		htmlContent = "Training Record Deleted successfully.";
		return SUCCESS;
	}
	
	private void procedureCall(ArrayList<DTOTrainingRecordDetails> tRDtlList) 
	{
		//docMgmtImpl.insertTrainingRecordDetails(tRDtlList);	
	}
	
	private ArrayList<DTOTrainingRecordDetails> getTRDtlList(DTOTrainingRecordDetails dtoTRDtl)
	{
		return null;
		//return docMgmtImpl.getTRNoWiseTRDetailList(dtoTRDtl.getTrainingRecordNo());
	}
}