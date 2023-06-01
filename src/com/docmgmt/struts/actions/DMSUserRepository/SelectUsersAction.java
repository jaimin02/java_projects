package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOUserGroupMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class SelectUsersAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String usrTyp;
	public Vector<DTOUserGroupMst> usrGrps;
	public Vector<DTOUserMst> usrList;
	public Vector<DTOStageMst> stages;
	public String frmDate;
	public String toDate;
	public String no;
	public int groupCount;
	public String mode;
	public String workSpaceId;
	public String nodeId;
	public String[] str;
	public String selectedUsers;
	public ArrayList<String> selUsers;	
	public Vector<Integer> grpWiseUserCount;
	public Vector<DTOWorkSpaceUserRightsMst> wsUserDetailList;
	public ArrayList<String> disableUser;
	
	
	
	public String disableUserList="";
	public String authUsers="";
	public String revUsers="";
	public String approvUsers="";
	
	
	
	
	@Override
	public String execute() throws Exception
	{
		
		DTOWorkSpaceUserRightsMst dtoUserRightsMst;
		DTOWorkSpaceUserRightsMst dtoWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
		dtoWorkSpaceUserRightsMst.setWorkSpaceId(workSpaceId);
		dtoWorkSpaceUserRightsMst.setNodeId(Integer.parseInt(nodeId));
		wsUserDetailList = docMgmtImpl.getUserRightsReport(dtoWorkSpaceUserRightsMst, true);
		Vector<DTOWorkSpaceNodeDetail> wsNodeDtl = new Vector<DTOWorkSpaceNodeDetail>();
		wsNodeDtl = docMgmtImpl.getNodeDetail(workSpaceId, Integer.parseInt(nodeId));
		
		for (int i = 0; i < wsNodeDtl.size(); i++) {
			disableUserList = disableUserList + ","+ wsNodeDtl.get(i).getModifyBy();
			disableUserList=disableUserList.substring(0,disableUserList.length());
		}
		
		for (int i = 0; i < wsUserDetailList.size(); i++) {
			dtoUserRightsMst = wsUserDetailList.get(i);
			
			if (dtoUserRightsMst.getStageId() == 10) {
				authUsers=authUsers+","+dtoUserRightsMst.getUserCode();
			}
			else if (dtoUserRightsMst.getStageId() == 20) {
				revUsers=revUsers+","+dtoUserRightsMst.getUserCode();
			}
			else if (dtoUserRightsMst.getStageId() == 100) {
				approvUsers=approvUsers+","+dtoUserRightsMst.getUserCode();
			}
			authUsers=authUsers.substring(0,authUsers.length());
			revUsers=revUsers.substring(0,revUsers.length());
			approvUsers = approvUsers.substring(0, approvUsers.length());
		}
		
		selUsers = new ArrayList<String>();
		if(mode.equalsIgnoreCase("Authors"))
		{
			selectedUsers = authUsers;
		}
		else if(mode.equalsIgnoreCase("Reviewers"))
		{
			selectedUsers = revUsers;
		}
		else if(mode.equalsIgnoreCase("Approvers"))
		{
			selectedUsers = approvUsers;	
		}
		if (selectedUsers.length()!=0) {
			selectedUsers=selectedUsers.substring(1,selectedUsers.length());
			str = selectedUsers.split(",", selectedUsers.length()-1);
			for (int i = 0; i < str.length; i++) {
				selUsers.add(str[i]);
			}
		}
		/*******************************/
		grpWiseUserCount=new Vector<Integer>();
		usrGrps=docMgmtImpl.getAllUserGroupByUserType(usrTyp);
		usrList=docMgmtImpl.getAllUserDetail();
		
		for (int i=0;i<usrGrps.size();i++)
		{
			DTOUserGroupMst grp=usrGrps.get(i);
			grp.setUsers(new ArrayList<DTOUserMst>());
			int cnt=0;
			for(int j=0;j<usrList.size();j++)
			{
				DTOUserMst usr=usrList.get(j);
				if (grp.getUserGroupCode()==usr.getUserGroupCode() && usr.getStatusIndi()!='D')
				{
					grp.getUsers().add(usr);
					cnt++;
				}
			}
			grpWiseUserCount.add(cnt);
		}
		for (int i = 0;i < grpWiseUserCount.size();i++)
		{
			//If user group has 0 users, remove that user group from list.
			if(grpWiseUserCount.get(i) == 0)
			{
				usrGrps.removeElementAt(i);
				grpWiseUserCount.removeElementAt(i);
				i--;
			}
		}
		
		dtoWorkSpaceUserRightsMst = new DTOWorkSpaceUserRightsMst();
		dtoWorkSpaceUserRightsMst.setWorkSpaceId(workSpaceId);
		dtoWorkSpaceUserRightsMst.setNodeId(Integer.parseInt(nodeId));
		
		
		disableUser = new ArrayList<String>();
		String[] strDisableUsers;
		
		if(disableUserList.length()!=0)
		{
			disableUserList=disableUserList.substring(1, disableUserList.length());
			strDisableUsers = disableUserList.split(",");
			for (int i = 0; i < strDisableUsers.length; i++) {
				disableUser.add(strDisableUsers[i]);
			}
		}
	return SUCCESS;
	}
}