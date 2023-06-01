package com.docmgmt.struts.actions.master.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AttachMultipleUsersAction extends ActionSupport
{
	public String usrs; 
	public String rite;
	public String stageIds;
	public String prj;	
	public String frmDate;
	public String toDate;
	DocMgmtImpl docMgmtImpl=new DocMgmtImpl();	

	@Override
	public String execute()
	{		
		ArrayList<DTOWorkSpaceUserRightsMst> userRightsList=new ArrayList<DTOWorkSpaceUserRightsMst>();
		ArrayList<DTOWorkSpaceUserMst> users=new ArrayList<DTOWorkSpaceUserMst>();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
		int ucode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());		
		if (prj.endsWith(","))
		{
			prj.substring(0,prj.length()-1);
		}
		if (usrs.endsWith(","))
		{
			usrs.substring(0,usrs.length()-1);
		}
		String[] usrList=usrs.split(",");		
		String[] prjList=prj.split(",");			
		for (int i=0;i<prjList.length;i++)
		{
			if (prjList[i]!=null && !prjList[i].equals(""))
			{				
				for (int j=0;j<usrList.length;j++)
				{
					if (usrList[j]!=null && !usrList[j].equals(""))
					{
						try 
						{
							DTOWorkSpaceUserMst dto=new DTOWorkSpaceUserMst();
							dto.setWorkSpaceId(prjList[i]);
							dto.setUserGroupCode(new Integer(usrList[j].split("_")[0]).intValue());
							dto.setUserCode(new Integer(usrList[j].split("_")[1]).intValue());
							dto.setAdminFlag('N');
							dto.setRemark("");
							dto.setModifyBy(ucode);												
							Date fromdt = dateFormat.parse(frmDate);
							dto.setFromDt(fromdt);
							Date todt = dateFormat.parse(toDate);	
							todt.setHours(23);
							todt.setMinutes(59);
							dto.setToDt(todt);
							users.add(dto);
						} 
						catch (NumberFormatException e) 
						{
							e.printStackTrace();
						} 
						catch (ParseException e) 
						{
							e.printStackTrace();
						}
						DTOWorkSpaceUserRightsMst dtoWorkspaceUserRightsMst= new DTOWorkSpaceUserRightsMst();		
//						if (rite.equals("canread"))
//						{
							//dtoWorkspaceUserRightsMst.setCanAddFlag('N');
							//dtoWorkspaceUserRightsMst.setCanDeleteFlag('N');
							//dtoWorkspaceUserRightsMst.setCanEditFlag('N');
							//dtoWorkspaceUserRightsMst.setCanReadFlag('Y');		
							//dtoWorkspaceUserRightsMst.setAdvancedRights("N");
//						}
//						else if (rite.equals("canedit"))
//						{
							dtoWorkspaceUserRightsMst.setCanAddFlag('Y');
							dtoWorkspaceUserRightsMst.setCanDeleteFlag('Y');
							dtoWorkspaceUserRightsMst.setCanEditFlag('Y');
							dtoWorkspaceUserRightsMst.setCanReadFlag('Y');
							dtoWorkspaceUserRightsMst.setAdvancedRights("Y");
//						}		
						dtoWorkspaceUserRightsMst.setRemark("");
						dtoWorkspaceUserRightsMst.setModifyBy(ucode);
						dtoWorkspaceUserRightsMst.setWorkSpaceId(prjList[i]);
						dtoWorkspaceUserRightsMst.setUserGroupCode(new Integer(usrList[j].split("_")[0]).intValue());
						dtoWorkspaceUserRightsMst.setUserCode(new Integer(usrList[j].split("_")[1]).intValue());	
						
						for(int iStageids = 0; iStageids < stageIds.split(",").length ; iStageids++ )
						{
							DTOWorkSpaceUserRightsMst dtoWorkspaceUserRightsMstClone = dtoWorkspaceUserRightsMst.clone();
							dtoWorkspaceUserRightsMstClone.setStageId(Integer.parseInt((stageIds.split(","))[iStageids]));
							userRightsList.add(dtoWorkspaceUserRightsMstClone);				
						}
					}
				}
			}
		}		
		if (userRightsList.size()>0)
		{
			docMgmtImpl.insertMultipleUserRights(userRightsList);
		}			
		if (users.size()>0)
		{
			docMgmtImpl.insertUpdateUsertoWorkspace(users);
		}
		return SUCCESS;
	}
}