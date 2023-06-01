//package com.docmgmt.struts.actions.master.user;
package com.docmgmt.struts.actions.authentic;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.docmgmt.dto.DTOLoginHistory;
import com.docmgmt.dto.DTOPasswordPolicyMst;
import com.docmgmt.dto.DTOUserLoginFailureDetails;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;

public class ProfileSelection extends AuthenticSupport implements SessionAware, ApplicationAware{
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	Vector<DTOUserMst> UserProfileDetails;
	DTOUserMst userdtl;
	public String username;
	public String Username;
	public String loginId;
	public String loginname;
	public char isAdUser;
	public String adUserPass;
	public int timeDiff = 0;
	public boolean passExpired;
	public String showReminder="yes";
	public String showComments="yes";
	public static int isOpenOfficeProcessRunning=0;
	public int chklogin=0;
	public String chkIp="";
	public String htmlContent;
	
	private String postLoginURL;
	//protected Map session;
	private String password;
	public int userGroupCode;	

	public String execute()  
	{
		try{
		int usercd = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		System.out.println(usercd);
		if(ActionContext.getContext().getSession().get("adUser")!=null)
			isAdUser = ActionContext.getContext().getSession().get("adUser").toString().charAt(0);
		if(isAdUser != 'Y'){
			username = ActionContext.getContext().getSession().get("loginusername").toString();
			userdtl = docMgmtImpl.getUserByCode(usercd);
			loginname = userdtl.getLoginName(); 
			System.out.println(loginname);
			UserProfileDetails =docMgmtImpl.getUserProfileByUserName(loginname);
		}
		else{
			username = ActionContext.getContext().getSession().get("adUserName").toString();
			userdtl = docMgmtImpl.getUserByCode(usercd);
			loginname = userdtl.getLoginName(); 
			System.out.println(loginname);
			UserProfileDetails =docMgmtImpl.getUserProfileByUserName(loginname);
			adUserPass = ActionContext.getContext().getSession().get("adUserPass").toString();
		}
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		return SUCCESS;
    }
	
	public String GetSelectedProfile(){
		int timeDiff = 0;
		Username = ActionContext.getContext().getSession().get("loginusername").toString();
		if(ActionContext.getContext().getSession().get("adUserName")!=null){
			username = ActionContext.getContext().getSession().get("adUserName").toString();
			isAdUser = ActionContext.getContext().getSession().get("adUser").toString().charAt(0);
		}
		
		System.out.println("UsergrpCode---- "+ userGroupCode);
		//Vector<DTOUserMst> UserDetail = docMgmtImpl.getUserProfileByUserName(Username);
		DTOUserMst userDetailforUserCode = docMgmtImpl.getUserNameByCode(Username,userGroupCode);
		int usercd = userDetailforUserCode.getUserCode();
		System.out.println("userDetailforUserCode.getUserCode()---- "+ userDetailforUserCode.getUserCode());
		DTOUserMst user=docMgmtImpl.getUserByCode(usercd);
		//DTOUserMst user=docMgmtImpl.getUserByCode(usercd);
        		ArrayList<DTOPasswordPolicyMst> pwdPolicy = new ArrayList<DTOPasswordPolicyMst>();
           		ArrayList<DTOPasswordPolicyMst> getMAxMins = new ArrayList<DTOPasswordPolicyMst>();
            	pwdPolicy = DocMgmtImpl.getPolicyDetails("SingleUser");
            	char activeFlag='\u0000';
            	if(pwdPolicy.size()>0)
            	{
            		DTOPasswordPolicyMst dto = pwdPolicy.get(0);
            		activeFlag = dto.getActiveFlag(); 
            	}
            	if(activeFlag=='Y'){
            		int chklogin = DocMgmtImpl.checkUserLoginDetails(user.getUserCode());
            		timeDiff = DocMgmtImpl.UserLoginDetailsOp(user.getUserCode(), 4);
                	if (chklogin==1){
                		
                		//String LoginIP="";
        				//String userCode="";
        				chkIp = docMgmtImpl.checkLoginHistory(usercd);
        				if(chkIp==null || chkIp.equalsIgnoreCase("null") || chkIp.equalsIgnoreCase(""))
        				{
        					timeDiff=0;
        				}
                			
        				else if(chkIp.equals(clientIp)){
                			
                			//addActionError("User already log-in on Ip");
                			timeDiff=0;
                		}else{
                			  
                			boolean timeFlag=false;
                			
                			if(timeDiff<0){
                					
                				timeFlag=true;
                			}
                			if(timeFlag==true){
                				
                				timeDiff=0;
                				
                			}else{
                				
                				if(timeDiff<=0){	
                					getMAxMins = DocMgmtImpl.getPolicyDetails("MaxLoginMins");
                					timeDiff = Integer.parseInt(getMAxMins.get(0).getValue());
        						}
                			//addActionError("User is already Logged in "+ chkIp + " IP Address");
                			//addActionError("User is already Logged in Please try After "+timeDiff+" mins");
                			addActionError("User is already logged in "+ chkIp + " IP address please try after "+timeDiff+" minute(s)");

                			return INPUT;
                			}
                    	}            		
                	}else{
                		timeDiff=0;
                	}
                	//timeDiff = DocMgmtImpl.UserLoginDetailsOp(user.getUserCode(), 1);
                }else{
                	timeDiff=0;
            	}       	
	
            	
   
    	
    	
        
    		
    		
    	
    	//----------------------------------------------------------------------------------------- 
    	int diff=0;
    	 //---------------------------------------------------------------------------------------------      		
  		if(timeDiff<=0){
  			diff= DocMgmtImpl.UserLoginDetailsOp(user.getUserCode(), 1);
        	 String usertypename=docMgmtImpl.getUserTypeName(user.getUserCode());
        	 user.setLoginIP(clientIp);
        	 String loginid=docMgmtImpl.LoginHistoryMstOp(user,1);
        	 
        	 ArrayList<DTOPasswordPolicyMst> pwdPolicytmp = new ArrayList<DTOPasswordPolicyMst>();
        	 pwdPolicytmp = DocMgmtImpl.getAllPolicyDetails("MaxLoginMins");
        	 String val=null;
        	 if(pwdPolicytmp.size()>0){
        	 DTOPasswordPolicyMst dto = pwdPolicytmp.get(0);
        	 val = dto.getValue();
        	 }
        	 ArrayList<DTOLoginHistory> loginHistory = new ArrayList<DTOLoginHistory>();
        	 loginHistory = docMgmtImpl.getLastLoginTime(user.getUserCode());
        	 Timestamp tempTime;
        	 String lastLoginTime = "";
        	 SimpleDateFormat dateFormat;
        	 Date dt;
        	 dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");
        	 if(loginHistory.size()>1){
        		 tempTime =  loginHistory.get(1).getLoginOn();
        		 dt=new Date(tempTime.getTime()); 
        	     System.out.println("Date: "+dateFormat.format(dt));
        	     lastLoginTime = dateFormat.format(dt);
        	 }
        	 else{
        		 Date date = new Date();
        		 lastLoginTime = dateFormat.format(date);
        		 
        	 }
        	 session.put("userid",user.getUserCode());
        	 session.put("usergroupcode",user.getUserGroupCode());
			 session.put("usertypecode",user.getUserType());
			 session.put("usertypename",usertypename);
			 session.put("lastLoginTime", lastLoginTime);
			 
			 
			/* char isAdUser = 0 ;
				if(user.getIsAdUser()!=0){
					isAdUser = user.getIsAdUser();
					session.put("adUser", isAdUser);
					session.put("adUserPass", adUserPass);
				}
				else
					isAdUser=0;*/
			 
		 if(val!=null){
			 session.put("MaxLogintime",val);
		}

			 String Menu="";
			 if(user.getIsAdUser()!='Y'){
				 if(passExpired){
					 htmlContent = "true";
					 /*return "pwdExpire";*/
				 }
			 }
			 Menu=docMgmtImpl.generateUserTyeBasedMenu(session.get("usertypecode").toString());
			 session.put("Menu",Menu);
			 if(postLoginURL != null && !postLoginURL.equals(""))
        	 {
        		 return "redirect";
        	 }
        	 else
        	 {
        		 ArrayList<DTOUserLoginFailureDetails> userLoginFailureDetails=DocMgmtImpl.getFailureUserDetail(username);
        		 if (userLoginFailureDetails != null && userLoginFailureDetails.size() > 0)
        		 {
        			 DTOUserLoginFailureDetails dtoUserLoginFailureDetails = userLoginFailureDetails.get(0);
        			 if (dtoUserLoginFailureDetails != null)
        			 {
	        			 dtoUserLoginFailureDetails.setAttemptCount(0);
	        			 DocMgmtImpl.insertFailureUserDtls(dtoUserLoginFailureDetails,2);
        			 }
        		 }
        		 htmlContent = "false";
        		 return "html";
        	 }
   	 }
    else{
    	addActionError("User is already logged in please try after "+timeDiff+" minute(s).");
   		return INPUT;
    	//return SUCCESS;
     }
    	
		//For Single User PasswordPolicy(Part of 21-CFR)
   		
  }

	

	
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public int getTimeDiff() {
		return timeDiff;
	}

	public void setTimeDiff(int timeDiff) {
		this.timeDiff = timeDiff;
	}

	public boolean isPassExpired() {
		return passExpired;
	}

	public void setPassExpired(boolean passExpired) {
		this.passExpired = passExpired;
	}

	public String getShowReminder() {
		return showReminder;
	}

	public void setShowReminder(String showReminder) {
		this.showReminder = showReminder;
	}

	public String getShowComments() {
		return showComments;
	}

	public void setShowComments(String showComments) {
		this.showComments = showComments;
	}

	public int getChklogin() {
		return chklogin;
	}

	public void setChklogin(int chklogin) {
		this.chklogin = chklogin;
	}

	public String getChkIp() {
		return chkIp;
	}

	public void setChkIp(String chkIp) {
		this.chkIp = chkIp;
	}
	
	private String clientIp;
	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getPostLoginURL() {
		return postLoginURL;
	}

	public void setPostLoginURL(String postLoginURL) {
		this.postLoginURL = postLoginURL;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public DocMgmtImpl getDocMgmtImpl() {
		return docMgmtImpl;
	}

	public void setDocMgmtImpl(DocMgmtImpl docMgmtImpl) {
		this.docMgmtImpl = docMgmtImpl;
	}

	public Vector<DTOUserMst> getUserProfileDetails() {
		return UserProfileDetails;
	}

	public void setUserProfileDetails(Vector<DTOUserMst> userProfileDetails) {
		UserProfileDetails = userProfileDetails;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	
	
	

}
