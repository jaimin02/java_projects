package com.docmgmt.struts.actions.authentic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Vector;

import com.docmgmt.dto.DTODefaultWorkSpaceMst;
import com.docmgmt.dto.DTONoticeMst;
import com.docmgmt.dto.DTOPasswordPolicyMst;
import com.docmgmt.dto.DTOReminder;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;

public class Welcome extends AuthenticSupport {

	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();

	// Vector Menu=new Vector();
	public String Menu = "";
	public Vector<DTONoticeMst> allNotices1;
	public Vector<DTONoticeMst> allNotices;
	public String attachmentsFolderPath;
	public ArrayList<DTOReminder> remMsg = new ArrayList<DTOReminder>();
	public String showReminder;
	public String noticeboard;
	public String setproject;
	public String calendar;
	public String reminder;
	public String comments;
	public String subquerymgmt;
	public String dossierstatustab;
	public String projectdocstatus;
	public String upcomingFilesReports;
	public String projectCompletionReport;
	public Vector<DTOWorkSpaceUserMst> latestWspace;
	public String userTypeName;
	public boolean passExpired;
	public int user;
	public String htmlContent;
	public boolean signatureFlag=false;
	public String signPath;
	public String fontStyle="";
	public String usrName;
	public int userCode;
	public String srcDocReminder;
	public String acknowledgementReminder;
	public String eSignatureStatus;
	public String dashBoardURL;
	@Override
	public String execute() throws IOException {
		
		/*ArrayList<DTOPasswordPolicyMst> pwdPolicy = new ArrayList<DTOPasswordPolicyMst>();
		user=Integer.parseInt(session.get("userid").toString());
		pwdPolicy = DocMgmtImpl.getPolicyDetails("ExpiryDays");
    	char actflag='\u0000';
    	if(pwdPolicy.size()>0)
    	{
    		DTOPasswordPolicyMst dto = pwdPolicy.get(0);
    		actflag = dto.getActiveFlag(); 
    	}
    	if(actflag=='Y'){
        	passExpired = DocMgmtImpl.isPasswordValidate(user);        		
    	}
		
    	 if(passExpired){
    		 showReminder= "yes";
			 return "pwdExpire";
    	 }*/

		KnetProperties prop = KnetProperties.getPropInfo("Welcomepage.properties");
		/*noticeboard = prop.getValue("NoticeBoard");
		setproject = prop.getValue("SetProject");
		calendar = prop.getValue("Calendar");
		reminder = prop.getValue("Reminder");
		comments = prop.getValue("Comments");*/
		subquerymgmt = prop.getValue("SubQueryMgmt");
		projectCompletionReport = prop.getValue("ProjectCompletionReport");
		String userTypeCode = session.get("usertypecode").toString();
		//String userGrpCode =ActionContext.getContext().getSession().get("usergroupcode").toString();
		String userGrpCode =session.get("usergroupcode").toString();
		userTypeName=session.get("usertypename").toString();
		String loginId=session.get("loginId").toString();
		String username=session.get("username").toString();
		int uCode = Integer.parseInt(session.get("userid").toString());
		System.out.println(uCode);
		System.out.println("User group code in login java is +: "+ userGrpCode);
		System.out.println(loginId);
		System.out.println("User Type name is +: " + userTypeName);
		System.out.println(username);
		System.out.println(userTypeCode);
		/*dossierstatustab = prop.getValue("DossierStatusTab");
		projectdocstatus = prop.getValue("ProjDocStatus");*/
		upcomingFilesReports = prop.getValue("UpcomingFileReports");
		srcDocReminder = prop.getValue("SrcDocReminder");
		acknowledgementReminder = prop.getValue("AcknowledgmentReminder");
		eSignatureStatus = prop.getValue("eSignStatus");		

		/*
		 * Added on 27-Oct-2010 If Menu is not in session then get menu item. it
		 * is possible while user login first time in application. With help of
		 * Ashmak Shah
		 */
		int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		usrName = ActionContext.getContext().getSession().get("username").toString();
		Vector <DTOWorkSpaceNodeHistory> dtoHistory;
		dtoHistory =docMgmtImpl.getUserSignatureDetail(usercode);
		if(dtoHistory.size()>0){
			signatureFlag = true;
			signPath = dtoHistory.get(0).getFileName();
			fontStyle = dtoHistory.get(0).getFileType();
		}
		
		if (session.get("Menu") == null) {
			Menu = docMgmtImpl.generateUserTyeBasedMenu(userTypeCode);
			session.put("Menu", Menu);
		} else {
			Menu = session.get("Menu").toString();
		}
		/*if (noticeboard != null && noticeboard.equals("Yes")) {
			Calendar today = new GregorianCalendar();
			allNotices1 = docMgmtImpl.getAllNotices(0);
			allNotices = new Vector<DTONoticeMst>();
			attachmentsFolderPath = PropertyInfo.getPropInfo().getValue(
					"AttachmentsFolder");
			for (int i = 0; i < allNotices1.size(); i++) {
				DTONoticeMst dto = allNotices1.get(i);
				String userTypes[] = dto.getUserTypeCode().split(",");
				for (int j = 0; j < userTypes.length; j++) {
					Calendar calendarToday = CalendarUtils
							.dateToCalendar(new Date());
					Calendar clToDate = CalendarUtils.stringToCalendar(dto
							.getEndDate().toString(), "yyyy-MM-dd");
					int diffInDaysTo = CalendarUtils.dateDifferenceInDays(
							calendarToday, clToDate);
					Calendar clFromDate = CalendarUtils.stringToCalendar(dto
							.getStartdate().toString(), "yyyy-MM-dd");
					int diffInDaysFrom = CalendarUtils.dateDifferenceInDays(
							calendarToday, clFromDate);

					if (userTypeCode.equals(userTypes[j])
							&& calendarToday != null && clToDate != null
							&& diffInDaysTo >= 0 && diffInDaysFrom <= 0) {
						DTOUserMst dtoUserMst = docMgmtImpl.getUserByCode(dto
								.getCreatedBy());
						dto.setUserNameOfCreator(dtoUserMst.getUserName());
						allNotices.add(dto);
					}
				}
			}
		}*/
		/*if (showReminder != null  && showReminder.equals("yes") && reminder !=null && reminder.equals("Yes")) {
			showReminder = "yes";
		}*/
		// If setproject is enable on welcome page then set the value of
		// default project in session.
		if (setproject != null && setproject.trim().equalsIgnoreCase("yes")) {

			int userCode = Integer.parseInt(session.get("userid").toString());
			int usergroupcode = Integer.parseInt(session.get("usergroupcode")
					.toString());
			ArrayList<DTODefaultWorkSpaceMst> defaultworkspace;
			defaultworkspace = DocMgmtImpl.getDefaultWorkspaceMst(userCode);
			String DefaultworkspaceID = " ";
			if (defaultworkspace.size() == 1) {
				DefaultworkspaceID = defaultworkspace.get(0).getWorkSpaceID();
			}
			if (DefaultworkspaceID != null && !DefaultworkspaceID.equals("")) {
				Vector<DTOWorkSpaceMst> getWorkSpaceDetail = docMgmtImpl
						.getUserWorkspace(usergroupcode, userCode);
				boolean found = false;
				for (int indWsId = 0; indWsId < getWorkSpaceDetail.size(); indWsId++) {
					if (getWorkSpaceDetail.get(indWsId).getWorkSpaceId()
							.equals(DefaultworkspaceID)) {
						found = true;
						break;
					}
				}
				if (found)
					session.put("defaultWorkspace", DefaultworkspaceID);
				else {
					DTODefaultWorkSpaceMst dto = new DTODefaultWorkSpaceMst();
					dto.setUserCode(userCode);
					dto.setWorkSpaceID(DefaultworkspaceID);
					dto.setRemark("");
					dto.setModifyBy(usergroupcode);
					dto.setStatusIndi('N');
					DocMgmtImpl.insertIntoDefaultWorkspaceMst(dto, 3);
					session.put("defaultWorkspace", "");
				}
			}
		}
		latestWspace = docMgmtImpl.getRecentWorkspacedetails(session.get("userid").toString());

		if (hasErrors()) {
			return ERROR;
		} else {
			return SUCCESS;
		}
	}
	
	public String getLoginTime(){
		//userCode = Integer.parseInt(session.get("userid").toString());
		ArrayList<DTOPasswordPolicyMst> pwdPolicy = new ArrayList<DTOPasswordPolicyMst>();
		pwdPolicy = DocMgmtImpl.getPolicyDetails("MaxLoginMins");
		
		DTOPasswordPolicyMst dto = pwdPolicy.get(0);
		int maxLoginMins = Integer.parseInt(pwdPolicy.get(0).getValue());
		char activeFlag = dto.getActiveFlag(); 
		int timediff=0;
		if(activeFlag=='Y'){
			timediff = DocMgmtImpl.UserLoginDetailsOp(userCode, 4);
			if(timediff<1 || timediff>maxLoginMins){
				timediff=0;
			}
		}
		htmlContent = String.valueOf(timediff);
		
		
		return SUCCESS;
	}
	
	public String openDashBorad(){
		int uCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		PropertyInfo prop = PropertyInfo.getPropInfo();
		dashBoardURL = prop.getValue("DashboardURL");
		String encodeBytes = Base64.getEncoder().encodeToString(("userCode : "+ uCode).getBytes());
		dashBoardURL = dashBoardURL +"?"+ encodeBytes;
		return SUCCESS;
	}
	
}
