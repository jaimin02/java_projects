package com.docmgmt.struts.actions.DMSUserRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.dto.DTOWorkspaceUserAuditTrailMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveAuthorsAction extends ActionSupport {

	private static final long serialVersionUID = -3677370829183880241L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String workSpaceId;
	public String userDetails;
	public String remark;
	public String modeVal;
	public String nodeId;
	public String selctdUsers;
	public String userType;
	public String htmlContent;
	
	@Override
	public String execute()
	{
		Vector<DTOUserMst> userMstList = docMgmtImpl.getAllUserDetail();
		for (int itrUserMst = 0; itrUserMst < userMstList.size(); itrUserMst++) 
		{
			DTOUserMst dto = userMstList.get(itrUserMst);
			if(!dto.getUserTypeCode().equalsIgnoreCase(userType))
			{
				userMstList.remove(itrUserMst);
				itrUserMst--;
			}
		}
		if(userDetails.endsWith(","))
			userDetails.substring(0, userDetails.length()-1);
		String [] usrsList = userDetails.split(",");
		ArrayList<String> userDetailsList = new ArrayList<String>();
		for (int i = 0; i < usrsList.length; i++) 
		{
			if (!usrsList[i].equals("")) 
				userDetailsList.add(usrsList[i]);
		}
		
		int maxAuditId = docMgmtImpl.getMaxAuditId();
		maxAuditId++;
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		ArrayList<DTOWorkSpaceUserRightsMst> wsUserRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
		ArrayList<DTOWorkSpaceNodeDetail> parentNodeList = docMgmtImpl.getAllParentsNodes(workSpaceId,Integer.parseInt(nodeId));
		if(selctdUsers.length()!=0)
		{
			selctdUsers=selctdUsers.substring(1,selctdUsers.length());
			String [] str = selctdUsers.split(",");
			ArrayList<DTOWorkSpaceUserRightsMst> userArrayList = new ArrayList<DTOWorkSpaceUserRightsMst>();
			ArrayList<Integer> finalUserList = new ArrayList<Integer>(); 
			for (int itrusercodelist = 0; itrusercodelist < userMstList.size(); itrusercodelist++) 
			{
				DTOUserMst dto = userMstList.get(itrusercodelist);
				for (int i = 0; i < str.length; i++) 
				{
					if (dto.getUserCode() == Integer.parseInt(str[i])) 
						finalUserList.add(Integer.parseInt(str[i]));
				}
			}
			for (int i = 0; i < finalUserList.size(); i++) 
			{
				int finaluser = finalUserList.get(i);
				if (finaluser > 0) 
				{
					for (int j = 0; j < userDetailsList.size(); j++) 
					{
						int user =Integer.parseInt(userDetailsList.get(j).split("_")[1]);
						if (user == finaluser) 
						{
							finalUserList.remove(i);
							i--;
							userDetailsList.remove(j);
							j--;
							break;
						}
					}
				}
			}
			ArrayList<DTOWorkspaceUserAuditTrailMst> wsUserAuditTrailMstList = new ArrayList<DTOWorkspaceUserAuditTrailMst>();
			for (int i = 0; i < finalUserList.size(); i++) 
			{
				DTOWorkspaceUserAuditTrailMst dtoWorkspaceUserAuditTrail = new DTOWorkspaceUserAuditTrailMst();
				dtoWorkspaceUserAuditTrail.setAuditId(maxAuditId);
				dtoWorkspaceUserAuditTrail.setWorkspaceId(workSpaceId);
				dtoWorkspaceUserAuditTrail.setNodeId(Integer.parseInt(nodeId));
				dtoWorkspaceUserAuditTrail.setUserCode(finalUserList.get(i));
				if(modeVal.equals("Authors"))
				{
					dtoWorkspaceUserAuditTrail.setStageId(10);
				}
				else if(modeVal.equals("Reviewers"))
				{
					dtoWorkspaceUserAuditTrail.setStageId(20);
				}
				else if(modeVal.equals("Approvers"))
				{
					dtoWorkspaceUserAuditTrail.setStageId(100);
				}
				dtoWorkspaceUserAuditTrail.setStatusIndi('D');
				dtoWorkspaceUserAuditTrail.setModifyBy(userId);
				wsUserAuditTrailMstList.add(dtoWorkspaceUserAuditTrail);
				
			}
			docMgmtImpl.insertWorkspaceUserAuditTrailDetail(wsUserAuditTrailMstList);
			/*
			System.out.println("userdetail list --- for add");
			for (int i = 0; i < userDetailsList.size(); i++) {
				System.out.println(userDetailsList.get(i));
				
			}
			*/
			
			for (int i = 0; i < finalUserList.size(); i++) 
			{
				for (int itrParentList = 0; itrParentList < parentNodeList.size(); itrParentList++) 
				{
					DTOWorkSpaceNodeDetail wsNodeDtl = parentNodeList.get(itrParentList);
					DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
					dto.setWorkSpaceId(wsNodeDtl.getWorkspaceId());
					dto.setNodeId(wsNodeDtl.getNodeId());				
					dto.setUserCode(finalUserList.get(i));
					dto.setCanReadFlag('Y');
					dto.setCanAddFlag('Y');
					dto.setCanEditFlag('Y');
					dto.setCanDeleteFlag('Y');
					dto.setAdvancedRights(null);				
					if(modeVal.equals("Authors"))
					{
						dto.setStageId(10);
					}
					else if(modeVal.equals("Reviewers"))
					{
						dto.setStageId(20);
					}
					else if(modeVal.equals("Approvers"))
					{
						dto.setStageId(100);
					}
					dto.setRemark("");
					dto.setModifyBy(userId);
					userArrayList.add(dto);
				}
			}
			docMgmtImpl.insertUpdateMultipleUserRights(userArrayList, 3);
		}
		
		ArrayList<DTOWorkspaceUserAuditTrailMst> wsUserAuditTrailMstList = new ArrayList<DTOWorkspaceUserAuditTrailMst>();
		for (int i = 0; i < userDetailsList.size(); i++) 
		{
			DTOWorkspaceUserAuditTrailMst dtoWorkspaceUserAuditTrail = new DTOWorkspaceUserAuditTrailMst();
			dtoWorkspaceUserAuditTrail.setAuditId(maxAuditId);
			dtoWorkspaceUserAuditTrail.setWorkspaceId(workSpaceId);
			dtoWorkspaceUserAuditTrail.setNodeId(Integer.parseInt(nodeId));
			dtoWorkspaceUserAuditTrail.setUserCode(new Integer(userDetailsList.get(i).split("_")[1]).intValue());
			if(modeVal.equals("Authors"))
			{
				dtoWorkspaceUserAuditTrail.setStageId(10);
			}
			else if(modeVal.equals("Reviewers"))
			{
				dtoWorkspaceUserAuditTrail.setStageId(20);
			}
			else if(modeVal.equals("Approvers"))
			{
				dtoWorkspaceUserAuditTrail.setStageId(100);
			}
			dtoWorkspaceUserAuditTrail.setStatusIndi('A');
			dtoWorkspaceUserAuditTrail.setModifyBy(userId);
			wsUserAuditTrailMstList.add(dtoWorkspaceUserAuditTrail);
		}
		docMgmtImpl.insertWorkspaceUserAuditTrailDetail(wsUserAuditTrailMstList);
		
		for (int i = 0; i < userDetailsList.size(); i++) 
		{
			for (int itrParentList = 0; itrParentList < parentNodeList.size(); itrParentList++) 
			{
				DTOWorkSpaceNodeDetail wsNodeDtl = parentNodeList.get(itrParentList);
				DTOWorkSpaceUserRightsMst dtoWsUserRightsMst = new DTOWorkSpaceUserRightsMst();
				dtoWsUserRightsMst.setWorkSpaceId(wsNodeDtl.getWorkspaceId());
				dtoWsUserRightsMst.setUserGroupCode(new Integer(userDetailsList.get(i).split("_")[0]).intValue());
				dtoWsUserRightsMst.setUserCode(new Integer(userDetailsList.get(i).split("_")[1]).intValue());
				dtoWsUserRightsMst.setNodeId(wsNodeDtl.getNodeId());
				dtoWsUserRightsMst.setCanReadFlag('Y');
				dtoWsUserRightsMst.setCanEditFlag('Y');
				dtoWsUserRightsMst.setCanAddFlag('Y');
				dtoWsUserRightsMst.setCanDeleteFlag('Y');
				dtoWsUserRightsMst.setAdvancedRights("Y");
				if(modeVal.equals("Authors"))
				{
					dtoWsUserRightsMst.setStageId(10);
				}
				else if(modeVal.equals("Reviewers"))
				{
					dtoWsUserRightsMst.setStageId(20);
				}
				else if(modeVal.equals("Approvers"))
				{
					dtoWsUserRightsMst.setStageId(100);
				}
				dtoWsUserRightsMst.setRemark("");
				dtoWsUserRightsMst.setModifyBy(userId);
				wsUserRightsList.add(dtoWsUserRightsMst);
			}
		}
		docMgmtImpl.insertUpdateMultipleUserRights(wsUserRightsList, 1);
		
		ArrayList<DTOWorkSpaceUserMst> wsUserMstList = new ArrayList<DTOWorkSpaceUserMst>();
		for (int i = 0; i < userDetailsList.size(); i++) 
		{
			DTOWorkSpaceUserMst dtoWsUserMst = new DTOWorkSpaceUserMst();
			dtoWsUserMst.setWorkSpaceId(workSpaceId);
			dtoWsUserMst.setUserGroupCode(new Integer(userDetailsList.get(i).split("_")[0]).intValue());
			dtoWsUserMst.setUserCode(new Integer(userDetailsList.get(i).split("_")[1]).intValue());
			dtoWsUserMst.setRemark(remark);
			dtoWsUserMst.setAdminFlag('N');
			dtoWsUserMst.setModifyBy(userId);
			Calendar c=new GregorianCalendar();		
			int date=c.get(Calendar.DATE);
			String month="0" + (c.get(Calendar.MONTH)+1);
			month=month.substring(month.length()-2);
			int year=c.get(Calendar.YEAR);
			String frmDate=year + "/" + month + "/" + date;
			//hardcode of 50 years
			year+=50;		
			String toDate=year + "/" + month + "/" + date;
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			try {
	            Date frmDt =df.parse(frmDate);            
	            Date toDt = df.parse(toDate);
	            toDt.setSeconds(59);
	            toDt.setMinutes(59);
	            toDt.setHours(23);
	            dtoWsUserMst.setFromDt(frmDt);
	            dtoWsUserMst.setToDt(toDt);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			wsUserMstList.add(dtoWsUserMst);
		}
		docMgmtImpl.insertUpdateUsertoWorkspace(wsUserMstList);
		htmlContent = modeVal+"'s Rights Changed Successfully.";
		return SUCCESS;
	}
}