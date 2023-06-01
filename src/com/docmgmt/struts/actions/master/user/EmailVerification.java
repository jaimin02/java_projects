package com.docmgmt.struts.actions.master.user;

import java.util.Base64;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class EmailVerification extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;

		private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
		public String htmlContent;
		 //If verifying link from email
		@Override
		public String execute()  
		{
		Base64.Decoder decoder = Base64.getUrlDecoder();
		userName = new String(decoder.decode(userName));
        int usercdForVerification=checkAuthenticForVerification();
        DTOUserMst dto=null;
        if(usercdForVerification==-1 || usercdForVerification==-2 || usercdForVerification==-3 || usercdForVerification==-4){
        	htmlContent="<script type='text/javascript'>"
            		+ "alert('User is either deleted or not available.');"
        			+ "var out='Login_input.do';location.href=out;"
        			+ "</script>";
            	return SUCCESS;
        }
        	
        else{
        	dto=docMgmtImpl.getUserByCode(usercdForVerification);
    		//checking if already verified
    		if(dto.getIsVerified()=='Y'){
            	htmlContent="<script type='text/javascript'>"
            		+ "alert('This Email is already verified, Please login');"
        			+ "var out='Login_input.do';location.href=out;"
        			+ "</script>";
            	return SUCCESS;
            }
    		//checking ends
    		
    		
    		dto.setUserCode(dto.getUserCode());
    		dto.setIsVerified('Y');
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
    		docMgmtImpl.UserMstOp(dto, 5, false);	
        }
        //verification ends
        if(dto.getIsVerified()=='Y'){
        	htmlContent="<script type='text/javascript'>"
        			+ "alert('Email verified successfully.');"
        			+ "var out='Login_input.do';location.href=out;"
        			+ "</script>";
        }
		return SUCCESS;
	}
		
	public int checkAuthenticForVerification() 
		{
			int usercode=docMgmtImpl.isValidUserForVerification(getUsername());
			//int usercode=docMgmtImpl.isValidUserThroughUserName(getUsername(),getPassword());
			return usercode;    
	}
	
public String userName;
	
    public String getUsername() {
        return userName;
    }
    public void setUsername(String username) {
        this.userName = username;
    }

}
