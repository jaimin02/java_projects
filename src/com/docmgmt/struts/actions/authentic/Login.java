
package com.docmgmt.struts.actions.authentic;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.docmgmt.dto.DTOLoginHistory;
import com.docmgmt.dto.DTOPasswordPolicyMst;
import com.docmgmt.dto.DTOUserLoginFailureDetails;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.struts.actions.util.CommonUtilMethod;
/**
 * <p> Validate a user login. </p>
 * @author Hardik Shah
 */

public final class Login extends AuthenticSupport  {

   
	public static final String CANCEL = "cancel";
	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public int timeDiff = 0;
	public boolean passExpired;
	public String showReminder="yes";
	public String showComments="yes";
	public static int isOpenOfficeProcessRunning=0;
	public int chklogin=0;
	public String chkIp="";
	DTOUserMst userdtl;
	public String loginname;

	/**
	 * action execute method
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@Override
	public String execute() throws ParseException, IOException   
	{
		
		System.out.println("Before:"+username);
		String strPattern = "[~!'#$%^&*()_+{}\\[\\]:;,<>/?-]";
        
        username = username.replaceAll(strPattern, "");
		System.out.println("After:"+username);
		
		if ((getUsername())==null ) return INPUT;
        if ((getPassword())==null) return INPUT;

        DTOUserMst userCode=docMgmtImpl.getUserNameByCode(getUsername(),userGroupCode);
        int uCode=userCode.getUserCode();
        
        if(isUserBlocked(uCode))
        {
        	addActionError("\""+getUsername()+"\" is blocked. Please contact administrator.");
        	return INPUT;
        }
        
        //If verifying link from email
/*        int usercdForVerification=checkAuthenticForVerification();
        
        if(usercdForVerification!=-2 && usercdForVerification!=-3 && usercdForVerification!=-4){

    		DTOUserMst dto=docMgmtImpl.getUserByCode(usercdForVerification);
    		dto.setUserCode(dto.getUserCode());
    		//dto.setIsVerified('Y');
    		dto.setUserName(dto.getUserName());
//    		dto.setLoginPass("");//Password will not be updated while Update the UserDetails
    		//dto.setLoginId(loginId);
    		dto.setLoginId(dto.getUserName());
    		dto.setLoginName(dto.getLoginName());
    		//dto.setModifyBy(Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString()));
    		//dto.setEMail(emailAddress);
    		dto.setRemark("");
    		dto.setUserGroupCode(dto.getUserGroupCode());
    		dto.setUserType(dto.getUserType());
    		dto.setLocationCode(dto.getLocationCode());
    		dto.setRoleCode(dto.getRoleCode());
    		//docMgmtImpl.UserMstOp(dto, 5, false);
        }*/
        //verification ends
        
        int usercd=checkAuthentic();
        System.out.println("usercd---->>"+ usercd);
        if(usercd==-2)
        {
        	addActionError("Database connection can not open.");
        	return INPUT;
        }
        
        else if(usercd==-3)
        {
        	addActionError("Access denied. Please contact administrator.");
        	return INPUT;
        }
        
        else if(usercd == -4)
        {
        	addActionError("You have AD User rights. Please login with correct AD Username and Password");
    		return INPUT;
        }
        
        else if(usercd == -6)
        {
        	addActionError("Please verify your email before login.");
    		return INPUT;
        }
        
        
        else if(usercd==-1)
        {
        	int status = updateLoginFailureDetails(uCode);
        	switch(status)
        	{
        		case 0:
        			addActionError("Invalid Username or Password.");
        			break;
        		case 1:
        			addActionError("\""+getUsername()+"\" is blocked. Please contact administrator.<br>" +
        					"Invalid Username or Password.");
        			break;
        	}
        	return INPUT;
         }
        else
         {
        	try {
        			KnetProperties knetProperties = KnetProperties.getPropInfo();
					/*String OpenOfficeConnString = knetProperties
							.getValue("OpenOfficeConnString");
					Runtime rt = Runtime.getRuntime();
					Process pSoffice = rt.exec(OpenOfficeConnString);*/
					System.out.println("Open Office Service Started...");
        	}catch(Exception e){
        		 e.printStackTrace();
        	}
        	
        	DTOUserMst user=docMgmtImpl.getUserByCode(usercd);
        	String isADUserAuthenticate="";
        	
        	
        	if(user.getIsAdUser()=='Y'){
        		if(user.getStatusIndi()=='N' || user.getStatusIndi()=='E'){
        			String adUserName=user.getAdUserName();
        			adUserName=adUserName.substring(adUserName.lastIndexOf("\\") + 1);
        			String pass=getPassword();
        			pass = CommonUtilMethod.replaceSpecialCharacters(pass);
        			isADUserAuthenticate=docMgmtImpl.authenticateADUser(adUserName,pass);
        			//isADUserAuthenticate=docMgmtImpl.authenticateADUser(adUserName,getPassword());
        		}
        	
	        	if (isADUserAuthenticate.contains("false")) {
	        		addActionError("You have AD User rights. Please login with correct AD Username and Password");
	        		return INPUT;
	        	}
        	}
        	
        	// code for profile selection
        	userdtl = docMgmtImpl.getUserByCode(usercd);
			loginname = userdtl.getLoginName();
        	Vector<DTOUserMst> UserDetail = docMgmtImpl.getUserProfileByUserName(loginname);
        	if(UserDetail.size()==1){
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
	            		chklogin = DocMgmtImpl.checkUserLoginDetails(user.getUserCode());
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
	                			  
	                			/*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  
	                  		    Date date = new Date();  
	                  		    
	                  		    
	                  		    Vector<DTOUserLoginDetails> dto=docMgmtImpl.getLastActivity(usercd);
	                			
	                  		    
	                			String lastActivityDate = dto.get(0).getLastActivitydate().toString();
	                			String currentDate = formatter.format(date);
	
	                  			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	                  			Date lastAccess = null;
	                  			Date crntTime = null;
	                  			
	                  			lastAccess = format.parse(lastActivityDate);
	                  			crntTime = format.parse(currentDate);
	                			
	                  			long timeDifference = crntTime.getTime() - lastAccess.getTime();
	
	                  			long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference);
	                       			
	                			System.out.print(diffMinutes + " minutes : ");
	                			
	                			//boolean timeFlag=false;
	                			
	                			 getMAxMins = DocMgmtImpl.getPolicyDetails("MaxLoginMins");
	    						 int sessionOut = Integer.parseInt(getMAxMins.get(0).getValue());
	    						 */
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
	    			
	            	
	   
        	}
        	else{
        		/*String loginid=docMgmtImpl.LoginHistoryMstOp(user,1);
        		 session.clear();
	        	 session.put("username",user.getUserName());
	        	 session.put("userid",user.getUserCode());
				 session.put("clientIp", clientIp);
				 session.put("loginId",loginid);
        		 return "ProfileSelect";*/
        		int diff=0;
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
    	        	 session.clear();
    	        	 session.put("username",user.getUserName());
    	        	 session.put("loginusername",user.getLoginName());
    				 session.put("userid",user.getUserCode());
    				 session.put("usergroupcode",user.getUserGroupCode());
    				 session.put("usertypecode",user.getUserType() );
    				 session.put("usertypename",usertypename);
    				 session.put("loginId",loginid);
    				 session.put("password",user.getLoginPass());
    				 session.put("locationname",user.getUserLocationName());
    				 session.put("countryCode",user.getCountyCode());
    				 session.put("clientIp", clientIp);
    				 session.put("lastLoginTime", lastLoginTime);
    				 
    				 
    				 char isAdUser = 0 ;
    				 System.out.println("user.getIsAdUser()---"+user.getIsAdUser());
    				 System.out.println("getPassword() ---" +getPassword());
    				 System.out.println("user.loginaname() ---" +user.getLoginName());
    				 System.out.println("user.username() ---" +user.getUserName());
    				 System.out.println("user.usercode() ---" +user.getUserCode());
    				 System.out.println("user.userGroupcode() ---" +getUserGroupCode());
    					if(user.getIsAdUser()!=0){
    						if(user.getIsAdUser() == 'Y'){
								isAdUser = user.getIsAdUser();
								session.put("adUser", isAdUser);
								session.put("adUserPass", getPassword());
								session.put("adUserName", user.getAdUserName().split("\\\\")[1]);
    						}
    					}
    					else
    						isAdUser=0;
    				 
    			 if(val!=null){
    				 session.put("MaxLogintime",val);
    			}

    				 String Menu="";
    				 /*if(user.getIsAdUser()!='Y'){
    					 if(passExpired){
    						 return "pwdExpire";
    					 }
    				 }*/
    				 
    				
    				 
    				 
    				 /*Menu=docMgmtImpl.generateUserTyeBasedMenu(session.get("usertypecode").toString());
    				 session.put("Menu",Menu);*/
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
    	        		 //return SUCCESS;
    	        		 return "ProfileSelect";
    	        	 }
           	 }
            else{
            	addActionError("User is already logged in please try after "+timeDiff+" minute(s).");
           		return INPUT;
            	//return SUCCESS;
             }
        		
        		
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
	        	 session.clear();
	        	 session.put("username",user.getUserName());
				 session.put("userid",user.getUserCode());
				 session.put("usergroupcode",user.getUserGroupCode());
				 session.put("usertypecode",user.getUserType() );
				 session.put("usertypename",usertypename);
				 session.put("loginId",loginid);
				 session.put("password",user.getLoginPass());
				 session.put("locationname",user.getUserLocationName());
				 session.put("countryCode",user.getCountyCode());
				 session.put("clientIp", clientIp);
				 session.put("lastLoginTime", lastLoginTime);
				 
				 
				 char isAdUser = 0 ;
				 System.out.println("out of else if(timediff < 0)");
				 System.out.println("user.getIsAdUser()---"+user.getIsAdUser());
				 System.out.println("getPassword() ---" +getPassword());
				 System.out.println("user.loginaname() ---" +user.getLoginName());
				 System.out.println("user.username() ---" +user.getUserName());
				 System.out.println("user.usercode() ---" +user.getUserCode());
				 System.out.println("user.userGroupcode() ---" +getUserGroupCode());
				 
					if(user.getIsAdUser()=='Y'){
						isAdUser = user.getIsAdUser();
						session.put("adUser", isAdUser);
						session.put("adUserPass", getPassword());
					}
					else
						isAdUser=0;
				 
			 if(val!=null){
				 session.put("MaxLogintime",val);
			}

				 String Menu="";
				 System.out.println("Login With Add User------"+user.getIsAdUser());
				 if(user.getIsAdUser()!='Y'){
					 if(passExpired){
						 return "pwdExpire";
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
	        		 return SUCCESS;
	        	 }
       	 }
        else{
        	addActionError("User is already logged in please try after "+timeDiff+" minute(s).");
       		return INPUT;
        	//return SUCCESS;
         }
        	
			//For Single User PasswordPolicy(Part of 21-CFR)
       		
      }
    }
	
	//code ends
        	        	
        	
			//For Single User PasswordPolicy(Part of 21-CFR)
       		/*ArrayList<DTOPasswordPolicyMst> pwdPolicy = new ArrayList<DTOPasswordPolicyMst>();
       		ArrayList<DTOPasswordPolicyMst> getMAxMins = new ArrayList<DTOPasswordPolicyMst>();
        	pwdPolicy = DocMgmtImpl.getPolicyDetails("SingleUser");
        	char activeFlag='\u0000';
        	if(pwdPolicy.size()>0)
        	{
        		DTOPasswordPolicyMst dto = pwdPolicy.get(0);
        		activeFlag = dto.getActiveFlag(); 
        	}
        	if(activeFlag=='Y'){
        		chklogin = DocMgmtImpl.checkUserLoginDetails(user.getUserCode());
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
            			  
            			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  
              		    Date date = new Date();  
              		    
              		    
              		    Vector<DTOUserLoginDetails> dto=docMgmtImpl.getLastActivity(usercd);
            			
              		    
            			String lastActivityDate = dto.get(0).getLastActivitydate().toString();
            			String currentDate = formatter.format(date);

              			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

              			Date lastAccess = null;
              			Date crntTime = null;
              			
              			lastAccess = format.parse(lastActivityDate);
              			crntTime = format.parse(currentDate);
            			
              			long timeDifference = crntTime.getTime() - lastAccess.getTime();

              			long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference);
                   			
            			System.out.print(diffMinutes + " minutes : ");
            			
            			//boolean timeFlag=false;
            			
            			 getMAxMins = DocMgmtImpl.getPolicyDetails("MaxLoginMins");
						 int sessionOut = Integer.parseInt(getMAxMins.get(0).getValue());
						 
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
        	}//-----------------------------------------------------------------------------------------        	
			//For Password Expiry  PasswordPolicy(Part of 21-CFR)        	
        	pwdPolicy = DocMgmtImpl.getPolicyDetails("ExpiryDays");
        	char actflag='\u0000';
        	if(pwdPolicy.size()>0)
        	{
        		DTOPasswordPolicyMst dto = pwdPolicy.get(0);
        		actflag = dto.getActiveFlag(); 
        	}
        	if(actflag=='Y'){
            	passExpired = DocMgmtImpl.isPasswordValidate(user.getUserCode());        		
        	}
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
	        	 session.clear();
	        	 session.put("username",user.getUserName());
				 session.put("userid",user.getUserCode());
				 session.put("usergroupcode",user.getUserGroupCode());
				 session.put("usertypecode",user.getUserType() );
				 session.put("usertypename",usertypename);
				 session.put("loginId",loginid);
				 session.put("password",user.getLoginPass());
				 session.put("locationname",user.getUserLocationName());
				 session.put("countryCode",user.getCountyCode());
				 session.put("clientIp", clientIp);
				 session.put("lastLoginTime", lastLoginTime);
				 
				 
				 char isAdUser = 0 ;
					if(user.getIsAdUser()!=0){
						isAdUser = user.getIsAdUser();
						session.put("adUser", isAdUser);
						session.put("adUserPass", getPassword());
					}
					else
						isAdUser=0;
				 
			 if(val!=null){
				 session.put("MaxLogintime",val);
			}

				 String Menu="";
				 if(user.getIsAdUser()!='Y'){
					 if(passExpired){
						 return "pwdExpire";
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
	        		 return SUCCESS;
	        	 }
       	 }
        else{
        	addActionError("User is already logged in please try after "+timeDiff+" minute(s).");
       		return INPUT;
        	//return SUCCESS;
         }
      }
    }*/
	
	@Override
	public String input()
	{
		
		if(session==null ||session.get("username")==null)
		{
			return INPUT;
		}
		else
		{
			return SUCCESS;
		}
	}
	
	public String cancel() {
		   
		 return CANCEL;
	 }

	/**
	 * pool connection and check authentication for user in database
	 * @return false if user is not validate in database
	 */
	/*public int checkAuthentic() 
	{
		int usercode=docMgmtImpl.isValidUser(getUsername(),getPassword(),getUserGroupCode());
		//int usercode=docMgmtImpl.isValidUserThroughUserName(getUsername(),getPassword());
		return usercode;    
	}*/
	
	public int checkAuthentic() //This is replicated method created for Disable the profile selection
	{
		int usercode=docMgmtImpl.isValidUser(getUsername(),getPassword());
		//int usercode=docMgmtImpl.isValidUserThroughUserName(getUsername(),getPassword());
		return usercode;    
	}
	
	public int checkAuthenticForVerification() 
	{
		int usercode=docMgmtImpl.isValidUserForVerification(getUsername(),getPassword());
		//int usercode=docMgmtImpl.isValidUserThroughUserName(getUsername(),getPassword());
		return usercode;    
	}
	/*
	 * status = 1 -- blocked user
	 * */
	public int updateLoginFailureDetails(int usercd){
		int status = 0;
		ArrayList<DTOUserLoginFailureDetails> failureDtls;
		ArrayList<DTOPasswordPolicyMst> pwddtls = new ArrayList<DTOPasswordPolicyMst>();
		DTOUserLoginFailureDetails dto = new DTOUserLoginFailureDetails();
		//failureDtls = DocMgmtImpl.getFailureUserDetail(getUsername());// DB call
		failureDtls = DocMgmtImpl.getFailureDetailForUser(getUsername(),usercd);// DB call
		char blockFlag;
		int userTimeDiff,lockOutTime=0,policyAttempt=0;
		long noOfAttemt=0;
		
		userTimeDiff = DocMgmtImpl.getTimeDifference(getUsername());// DB call
		pwddtls= DocMgmtImpl.getPolicyDetails("LockOutTime");// DB call
		if(pwddtls.size()>0){
			DTOPasswordPolicyMst dtopwdPolicy = pwddtls.get(0);
			lockOutTime = Integer.parseInt(dtopwdPolicy.getValue());
		}
		if(failureDtls.size()==0 || userTimeDiff>lockOutTime){ // If Record not available in UserLoginFailureDetails then New Entry
			dto.setUserName(getUsername());
			dto.setAttemptCount(1);
			dto.setBlockedFlag('N');
			dto.setUserCode(usercd);
			DocMgmtImpl.insertFailureUserDtls(dto, 1);// DB call
		}else if(failureDtls.size()>0){//if Record Available 
			dto= failureDtls.get(0);
			blockFlag = dto.getBlockedFlag();
			noOfAttemt = dto.getAttemptCount();
			if(blockFlag=='N' && userTimeDiff<lockOutTime){
				pwddtls= DocMgmtImpl.getPolicyDetails("NoOfAttempt");// DB call
				if(pwddtls.size()>0){
					DTOPasswordPolicyMst dtopwdPolicy = pwddtls.get(0);
					policyAttempt = Integer.parseInt(dtopwdPolicy.getValue());

					noOfAttemt++;
					dto.setAttemptCount(noOfAttemt);
					dto.setUserCode(usercd);

				if(noOfAttemt>=policyAttempt){
					dto.setBlockedFlag('B');
					
					String usrName = failureDtls.get(0).getUserName();
					Vector<DTOUserMst> userDetail=new Vector<DTOUserMst>();
					userDetail = docMgmtImpl.getUserDetail(usrName);      //With Login Name
					//userDetail = docMgmtImpl.getUserDetailByLoginId(usrName);  // With Login Id
					
					dto.setUserName(userDetail.get(0).getUserName());
					dto.setUserCode(usercd);
					dto.setUserGroupCode(userDetail.get(0).getUserGroupCode());
					dto.setUserTypeCode(userDetail.get(0).getUserTypeCode());
					dto.setLoginName(userDetail.get(0).getLoginName());
					dto.setModifyBy(userDetail.get(0).getUserCode());
					DTOUserMst dtoUser = docMgmtImpl.getUserByCode(usercd);
					dto.setRoleCode(dtoUser.getRoleCode());
					dto.setLocationCode(dtoUser.getLocationCode());
					status = 1;
				}
				//DocMgmtImpl.insertFailureUserDtls(dto, 2);// DB call
				DocMgmtImpl.insertFailureUserDtlsForBlockUser(dto, 2);// DB call
			}
			}
		}
		return status;
	}
	
	public boolean isUserBlocked(int uCode){
		boolean block = false;
		
		ArrayList<DTOUserLoginFailureDetails> failureDtls;
		ArrayList<DTOPasswordPolicyMst> pwddtls;
		pwddtls = DocMgmtImpl.getPolicyDetails("LockOutTime"); // DB call
		//-------------
		
		int lockOutTime=0;
		int userTimediff=0;
		userTimediff = DocMgmtImpl.getTimeDifference(getUsername());// DB call
		if(pwddtls.size()>0){
			DTOPasswordPolicyMst dto = pwddtls.get(0);
			lockOutTime = Integer.parseInt(dto.getValue());
		}
		
		//----------------
		failureDtls = DocMgmtImpl.getFailureUserDetail(getUsername());// DB call
		//failureDtls = DocMgmtImpl.getFailureDetailForUser(getUsername(),uCode);// DB call
		char blockedFlag='N';
		if(failureDtls.size()>0){
			DTOUserLoginFailureDetails dto = failureDtls.get(0);
			blockedFlag = dto.getBlockedFlag();
		}
		//-----------------------
		pwddtls= DocMgmtImpl.getPolicyDetails("AutoUnlock");// DB call
 
		if(blockedFlag=='B' && pwddtls.size()<=0)
		{		
			block = true;
		}
    	
		if(userTimediff<lockOutTime && failureDtls.size()>0 && blockedFlag=='B'){
			block = true;
		}
		return block;
}

	private String username;
	private int userGroupCode;
	private String userTypeCode;

    public int getUserGroupCode() {
		return userGroupCode;
	}
	public void setUserGroupCode(int userGroupCode) {
		this.userGroupCode = userGroupCode;
	}

	public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
	
    private String userid;
    
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	private String postLoginURL;
	
	public String getPostLoginURL() 
	{
		return postLoginURL;
	}
	public void setPostLoginURL(String postLoginURL) 
	{
		this.postLoginURL = postLoginURL;
	}
	private String clientIp;
	
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;		
	}
	private String Verified;

	public String getVerified() {
		return Verified;
	}
	public void setVerified(String verified) {
		Verified = verified;
	}
}
