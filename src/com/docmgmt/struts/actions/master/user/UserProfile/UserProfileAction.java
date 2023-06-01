package com.docmgmt.struts.actions.master.user.UserProfile;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOUserProfile;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserProfileAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public ArrayList<DTOUserProfile> listUserProfile;
	public DTOUserMst dtoUserMSt;
	public ArrayList<String> listProfileType;
	public ArrayList<String> listProfileSubType;
	public String mobileno;
	public String officeno;
	public String homeno;
	public String offiectemail;
	public String personalemail;
	public String otheremail;
	public String htmlContent;
	public ArrayList<String> listAlertOnValue = new ArrayList<String>();

	@Override
	public String execute() throws Exception 
	{
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());  
		dtoUserMSt = docMgmtImpl.getUserByCode(userCode);
		listUserProfile = docMgmtImpl.getUserProfileDetails(userCode);
		
		listProfileType = new ArrayList<String>();
		listProfileSubType = new ArrayList<String>();
		listProfileType.add("M");
		listProfileType.add("E");
		listProfileSubType.add("Personal");
		listProfileSubType.add("Office");
		listProfileSubType.add("Other");
		/*
		for(int itrUserProfile = 0;itrUserProfile < listUserProfile.size();itrUserProfile++)
		{
			System.out.println(
			listUserProfile.get(itrUserProfile).getProfileType()+"****"+
			listUserProfile.get(itrUserProfile).getProfileSubType()+"****"+
			listUserProfile.get(itrUserProfile).getProfilevalue()+"****"+
			listUserProfile.get(itrUserProfile).getAlertOn()+"****"+
			listUserProfile.get(itrUserProfile).getRemark()+"****"+
			listUserProfile.get(itrUserProfile).getModifyOn()+"****"+
			listUserProfile.get(itrUserProfile).getModifyBy()+"****"+
			listUserProfile.get(itrUserProfile).getStatusIndi()
			);
			
		}
		*/
		return SUCCESS;
	}
	public String saveUserProfile()
	{
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		HttpServletRequest request = ServletActionContext.getRequest();
		listProfileType = new ArrayList<String>();
		listProfileSubType = new ArrayList<String>();
		listProfileType.add("M");
		listProfileType.add("E");
		listProfileSubType.add("Personal");
		listProfileSubType.add("Office");
		listProfileSubType.add("Other");
		
		listUserProfile = docMgmtImpl.getUserProfileDetails(userCode);
		
		for(int itrProfileType=0; itrProfileType < listProfileType.size(); itrProfileType++)
			for(int itrProfileSubType=0;itrProfileSubType< listProfileSubType.size();itrProfileSubType++)
			{
				String newProfileValue = request.getParameter(listProfileType.get(itrProfileType) + "_" + listProfileSubType.get(itrProfileSubType)).trim();
				Character alertOn='N';
				for (int itrListAlerOnValue = 0; itrListAlerOnValue < listAlertOnValue.size(); itrListAlerOnValue++) 
				{
					if((listProfileType.get(itrProfileType) + "_" + listProfileSubType.get(itrProfileSubType)).equals(listAlertOnValue.get(itrListAlerOnValue)))
					{
						alertOn='Y';
						break;
					}
				}
				DTOUserProfile dtoUserProfile = new DTOUserProfile();
				dtoUserProfile.setUserCode(userCode);
				dtoUserProfile.setProfileType(listProfileType.get(itrProfileType).charAt(0));
				dtoUserProfile.setProfileSubType(listProfileSubType.get(itrProfileSubType));
				dtoUserProfile.setProfilevalue(newProfileValue);
				dtoUserProfile.setAlertOn(alertOn);
				dtoUserProfile.setRemark("");
				dtoUserProfile.setModifyBy(userCode);
				
				if(listUserProfile.size()>0)
				{
					boolean isAvailInDB=false;
				
					for (int itrProfileDetails = 0; itrProfileDetails < listUserProfile.size(); itrProfileDetails++) 
					{
						if(listProfileType.get(itrProfileType).charAt(0) == listUserProfile.get(itrProfileDetails).getProfileType() && 
						   listProfileSubType.get(itrProfileSubType).equals(listUserProfile.get(itrProfileDetails).getProfileSubType()))
						{
							isAvailInDB = true;
							if(newProfileValue != null && !listUserProfile.get(itrProfileDetails).getProfilevalue().equals(newProfileValue))	
							{
								System.out.println("full condition1");
								docMgmtImpl.InserUserProfile(dtoUserProfile,2);
								break;
							}
							if(!listUserProfile.get(itrProfileDetails).getAlertOn().equals(alertOn))
							{
								System.out.println("full condition1......................1");
								docMgmtImpl.InserUserProfile(dtoUserProfile,2);				
							}
						}
					}
					if(!isAvailInDB)
					{
						if(newProfileValue != null && newProfileValue != "")
						{
							System.out.println("full condition2");
							docMgmtImpl.InserUserProfile(dtoUserProfile,1);
						}
					}
				}
				else
				{
					if(newProfileValue != null && newProfileValue != "")
					{
						System.out.println("full condition3");
						docMgmtImpl.InserUserProfile(dtoUserProfile,1);
					}
				}
			}
		listAlertOnValue = null;
		 htmlContent = "Details have been saved successfully";
		 return SUCCESS;
	}
}
