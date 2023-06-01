package com.docmgmt.struts.actions.workspace.PendingSignOff;

import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ESignatureStatus extends ActionSupport{


	
	public DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public int userCode;
	public int userGrpCode;
	public Vector<DTOWorkSpaceUserMst> userAcknowledge;
	public Vector<DTOWorkSpaceUserMst> acknowledgeUpdate;
	public Vector<DTOWorkSpaceUserRightsMst> eSignStatus;
	public String htmlContent;
	public String workspaceID;
	public String OpenFileAndSignOff;
	KnetProperties knetProperties = KnetProperties.getPropInfo();
	public String ManualSignatureConfig;
	public  Vector<DTOWorkSpaceNodeHistory> nodes;
	public Vector<DTOWorkSpaceNodeHistory> eSignPending; 
	public int getSeqNo;
	public int nodeid;
	public int tranno;
	public boolean data;
	public Vector<DTOWorkSpaceNodeHistory> Esignreminder;
	public Vector<DTOWorkSpaceUserRightsMst> getAssignedUserSeqno;
	public int documentCount=0;
	public String execute() 
	{
		DTOWorkSpaceUserMst wsum = new DTOWorkSpaceUserMst();
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		 
		 userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		 userGrpCode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		 OpenFileAndSignOff = knetProperties.getValue("OpenFileAndSignOff");
		 workspaceID = knetProperties.getValue("EsignatureId");
		 nodes = docMgmtImpl.getnodeId(workspaceID,userCode,userGrpCode);
		 eSignPending = new Vector<DTOWorkSpaceNodeHistory>();
		
		 if(nodes.size()>0){
			 
			 for(int i=0;i<nodes.size();i++){
				 String dto1;
				 DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
				 nodeid = nodes.get(i).getNodeId();
				 ManualSignatureConfig = docMgmtImpl.getAttributeDetailByAttrName(workspaceID, nodeid, "SignOff In Sequence").getAttrValue();
				 if(ManualSignatureConfig.equalsIgnoreCase("Yes")){
					 getAssignedUserSeqno = docMgmtImpl.getSeqNoForSignoffSequence(workspaceID,nodeid,userCode);
					 int seqno; 
					 if(getAssignedUserSeqno.size()>0)
						 seqno = getAssignedUserSeqno.get(0).getSeqNo();
					 else
						 seqno = 0;
					 if(seqno==1){
						 tranno = docMgmtImpl.getTranNoFromNodeId(workspaceID, nodeid, userCode);
						 dto = docMgmtImpl.eSignPendingforyesSeq(workspaceID,userCode,tranno);
							 if(dto.getWorkSpaceDesc() !=null){
								 eSignPending.add(dto); 
							 }
						 
						 dto=null;
					 }
					 else{
						 seqno=seqno-1;
						 if(seqno>0){
							 int prevUser = docMgmtImpl.checkPrevUserForeSign(workspaceID, nodeid,seqno);
							 String isUserSigned;
							 isUserSigned=docMgmtImpl.getNodeHistoryForUserCode(workspaceID,nodeid,prevUser);
								if(isUserSigned.equalsIgnoreCase("true")){
									dto1 = docMgmtImpl.getNodeHistoryForUserCodeForSendBack(workspaceID,nodeid,userCode);
									if(dto1.equalsIgnoreCase("false")){
									 dto = docMgmtImpl.checkeSignData(workspaceID,nodeid,userCode);
									 if(dto.getWorkSpaceDesc() !=null){
										 eSignPending.add(dto); 
									 }
									}
								}
						 	}
							
						/* dto = docMgmtImpl.checkeSign(workspaceID, prevUser, nodeid,seqno);
						 if(dto.getWorkSpaceDesc() ==null){
							 seqno=seqno+1;
							 DTOWorkSpaceNodeHistory NxtUserDto = docMgmtImpl.checkeSignForCurrentUser(workspaceID, userCode, nodeid,seqno);
							 eSignPending.add(NxtUserDto);
						 }*/
						 
							/*if(dto.getWorkSpaceDesc() !=null){
								 eSignPending.add(dto); 
							 }
						  */
						dto=null;
					 }
				 }
				 //System.out.println(nodeid);
				 else{
					  
					 //tranno = docMgmtImpl.getTranNoFromNodeId(workspaceID, nodeid, userCode);
					 dto = docMgmtImpl.Esigndatafornoseq(workspaceID, userCode, nodeid);
					 data = docMgmtImpl.flagCheckforData(workspaceID,userCode,nodeid);
					 if(data==false){
						 if(dto.getWorkSpaceDesc() !=null){
							 eSignPending.add(dto);
						 }
					 }
					 dto=null;
				 /*Esignreminder.addAll(eSignPending);*/
				 } 
			}
		
		 
		 }
		 return SUCCESS;
	}
	
	public String EsignatureCount(){
		
		if(eSignPending.size()>0){
			documentCount = eSignPending.size();
		}
		htmlContent=Integer.valueOf(documentCount).toString();
		return "html";
		
	}
}
	
	
