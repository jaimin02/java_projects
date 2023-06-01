package com.docmgmt.struts.actions.master.user;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Vector;

import com.docmgmt.dto.DTOPasswordHistory;
import com.docmgmt.dto.DTOPasswordPolicyMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.services.CryptoLibrary;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class ChangePasswordAction extends ActionSupport  {
   
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public boolean passExpired;
	public int minLength;
	public String showReminder;
	public String flag="false";
	public String loginUserName;
	public String htmlContent;
	CryptoLibrary encrypt = new CryptoLibrary();
	public String isAdUser; 
	
	
	@Override
	public String execute()  
	{
		ArrayList<DTOPasswordPolicyMst> pwdLength;
		int usercd = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		isAdUser="";
		if(ActionContext.getContext().getSession().get("adUser")==null){
			isAdUser = "" ;
		}
		else{
			isAdUser = ActionContext.getContext().getSession().get("adUser").toString();
		}
		
		DTOUserMst user= docMgmtImpl.getUserByCode(usercd);
		dbusername = user.getLoginName();
		//dbpassword = user.getLoginPass();
		pwdLength = DocMgmtImpl.getPolicyDetails("MinLength");
		for (int i = 0; i < pwdLength.size(); i++) {
			DTOPasswordPolicyMst dto = pwdLength.get(i);
			minLength = Integer.parseInt(dto.getValue());
		}
		return SUCCESS;
    }
	
	public String updatePassword() {
		//int usercd = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTOUserMst user1=new DTOUserMst();
		System.out.println("flag--------"+flag);
		if(!flag.equals("true")){
			int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			user1= docMgmtImpl.getUserByCode(userId);
		}else{
			
			Base64.Decoder decoder = Base64.getUrlDecoder();  
		        // Decoding URl  
			try{
				loginUserName = new String(decoder.decode(loginUserName));
				System.out.println("loginUserName--------"+loginUserName);
			Vector<DTOUserMst> dtoProfileDetails = docMgmtImpl.getUserLoginName(loginUserName);
			user1= docMgmtImpl.getUserByCode(dtoProfileDetails.get(0).getUserCode());
			}
			catch(Exception e){
				htmlContent="Invalid Username.";
				return "html";
			}
		}
		String emailId = user1.getLoginName();
		Vector<DTOUserMst> getUserDetail = docMgmtImpl.getUserDetailByLoginNameId(emailId);
	//for(int k=0;k<getUserDetail.size();k++){
		user1= docMgmtImpl.getUserByCode(getUserDetail.get(0).getUserCode());
		isAdUser="";
		if(ActionContext.getContext().getSession().get("adUser")==null){
			isAdUser = "" ;
		}
		else{
			isAdUser = ActionContext.getContext().getSession().get("adUser").toString();
		}
		
		/*if(!dbpassword.equals(getUserDetail.get(k).getLoginPass()))
		{
			addActionError("Your password does not match with the current password, Please enter correct password.");
			//addActionMessage("Please enter correct password");
			if(flag.equals("true")){
				htmlContent="Your password does not match with the current password, please enter correct password.";
				return "html";
			}
			return INPUT;
		}*/
		boolean changePassFlag=false;
		//if(usercd == getUserDetail.get(0).getUserCode()){
		if(!validatePasswordWithHistory(getUserDetail.get(0).getUserCode())){
			changePassFlag=true;
			int passwordMatchCount=0;
			ArrayList<DTOPasswordPolicyMst> policyDtls;
			ArrayList<DTOPasswordHistory> pwdHistory;
			//int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			
			int matchHistory = -1;
			policyDtls = DocMgmtImpl.getPolicyDetails("MatchHistory");
			matchHistory = Integer.parseInt(policyDtls.get(0).getValue());
			
			pwdHistory = DocMgmtImpl.getPasswordHistoryDtlsForPasswordMatch(getUserDetail.get(0).getUserCode(),matchHistory);
			
			int matchChar=-1;
			policyDtls = DocMgmtImpl.getPolicyDetails("MatchChar");
			for(int i =0 ; i<policyDtls.size();i++){
				DTOPasswordPolicyMst dto = policyDtls.get(i);
				matchChar = Integer.parseInt(dto.getValue());
			}
			
			for(int i1=0;i1<pwdHistory.size();i1++){
					DTOPasswordHistory dto = pwdHistory.get(i1);
						//System.out.println("Pass.."+i1+".."+dto.getPassword());
						String subStrPass = dto.getPassword();
						String subStrNewPass = newpassword;
					if(matchChar > 0){
						if(dto.getPassword().length() >= matchChar){
							subStrPass = dto.getPassword().substring(0, matchChar);
							//System.out.println("First Condition:::"+subStrPass);
						}
						if(newpassword.length() >= matchChar){
							subStrNewPass= newpassword.substring(0, matchChar);
							//System.out.println("Second Condition:::"+subStrNewPass);
						}
					}
					if(subStrPass.compareToIgnoreCase(subStrNewPass)==0){
						passwordMatchCount++;
					}
				}
				
			policyDtls = DocMgmtImpl.getPolicyDetails("MatchHistory");
			if(passwordMatchCount>0)
				addActionError("Your password matches with the previous "+ matchHistory +" password(s), Please enter another one.");
			else
				addActionError("Your password matches with the previous password(s), Please enter another one.");
			//addActionMessage("Please enter another one");
			if(flag.equals("true")){
				htmlContent="Your password matches with the previous "+ matchHistory +" password(s), please enter another one.";
				//htmlContent="Your password matches with the previous password(s), please enter another one";
				return "html";
			}
			changePassFlag=true;
			return INPUT;
		}
		
		if(changePassFlag=true){
			for(int k=0;k<getUserDetail.size();k++){
				user1=new DTOUserMst();
				user1=getUserDetail.get(k);
				user1.setLoginPass(newpassword);
				docMgmtImpl.UserMstOp(user1, 4, false);
			}
		
		}
		//adding password to the session
		ActionContext.getContext().getSession().put("password",newpassword);	
		if(flag.equals("true")){
			htmlContent="true";
			return "html";
		}
		//}
	//}
		
	return SUCCESS;
	}
	public boolean validatePasswordWithHistory(int usercd){
		ArrayList<DTOPasswordPolicyMst> policyDtls;
		ArrayList<DTOPasswordHistory> pwdHistory;
		
		int matchHistory = -1;
		policyDtls = DocMgmtImpl.getPolicyDetails("MatchHistory");
		for(int i =0 ; i<policyDtls.size();i++){
			DTOPasswordPolicyMst dto = policyDtls.get(i);
			matchHistory = Integer.parseInt(dto.getValue());
		}
		int matchChar=-1;
		policyDtls = DocMgmtImpl.getPolicyDetails("MatchChar");
		for(int i =0 ; i<policyDtls.size();i++){
			DTOPasswordPolicyMst dto = policyDtls.get(i);
			matchChar = Integer.parseInt(dto.getValue());
		}
		/*int usercd;
		if(!flag.equals("true")){
			usercd = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		}
		else{
			Vector<DTOUserMst> dtoProfileDetails = docMgmtImpl.getUserLoginName(loginUserName);
			usercd=dtoProfileDetails.get(0).getUserCode();
		}*/
		pwdHistory = DocMgmtImpl.getPasswordHistoryDtls(usercd);
		if(pwdHistory.size() >= matchHistory ){
			Collections.reverse(pwdHistory);
			for(int i=0;i<matchHistory;i++){
				DTOPasswordHistory dto = pwdHistory.get(i);
					//System.out.println("I.."+i+".."+dto.getPassword());
					String subStrPass = dto.getPassword();
					String subStrNewPass = newpassword;
				if(matchChar > 0){
					if(dto.getPassword().length() >= matchChar){
						subStrPass = dto.getPassword().substring(0, matchChar);
						//System.out.println("First Condition:::"+subStrPass);
						}
					if(newpassword.length() >= matchChar){
						subStrNewPass= newpassword.substring(0, matchChar);
						//System.out.println("Second Condition:::"+subStrNewPass);
					}
				}
				if(subStrPass.compareToIgnoreCase(subStrNewPass)==0){
					return false;
				
				}
			}
		}
		else if(pwdHistory.size()>0){
			Collections.reverse(pwdHistory);
			if(matchHistory > pwdHistory.size())
			{
				matchHistory = pwdHistory.size();//if pwdhistory < matchHistory then matchhistory size is same as pwdhistory size 
				
				for(int i=0;i<matchHistory;i++)
				{
					DTOPasswordHistory dto = pwdHistory.get(i);
						//System.out.println("I.."+i+".."+dto.getPassword());
						String subStrPass = dto.getPassword();
						String subStrNewPass = newpassword;
					if(matchChar > 0)
					{
						if(dto.getPassword().length() >= matchChar){
							subStrPass = dto.getPassword().substring(0, matchChar);
							//System.out.println("First Condition:::"+subStrPass);
							}
						if(newpassword.length() >= matchChar){
							subStrNewPass= newpassword.substring(0, matchChar);
							//System.out.println("Second Condition:::"+subStrNewPass);
						}
					}
					if(subStrPass.compareToIgnoreCase(subStrNewPass)==0){
						return false;
					
					}
				}			
			}
		}
		return true;
		
	}

	
	
	public String username;
	public String dbusername;
	public String dbpassword;
	public String oldpassword;
	public String newpassword;
	public String userid;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDbusername() {
		return dbusername;
	}

	public void setDbusername(String dbusername) {
		this.dbusername = dbusername;
	}

	public String getDbpassword() {
		return dbpassword;
	}

	public void setDbpassword(String dbpassword) {
		this.dbpassword = dbpassword;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	public boolean getPassExpired() {
		return passExpired;
	}

	public void setPassExpired(boolean passExpired) {
		this.passExpired = passExpired;
	}
	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

}
