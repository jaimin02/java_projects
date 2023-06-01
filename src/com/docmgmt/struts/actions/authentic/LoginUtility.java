package com.docmgmt.struts.actions.authentic;

import java.util.Vector;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class LoginUtility extends ActionSupport{
	
	public String loginUserName;
	public String htmlContent;
	public String userTypeCode;
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String GetProfileNames(){
		
		htmlContent = "";
		Vector<DTOUserMst> dtoProfileDetails = new Vector<DTOUserMst>();
		loginUserName=loginUserName.trim();
		System.out.println("getUserDtl :-------->>");
		Vector <DTOUserMst> getUserDtl = docMgmtImpl.chekIsAdUser(loginUserName);
		System.out.println("getUserDtl.size :-------->>"+getUserDtl.size());
		char isAdUserFlag;
		if(getUserDtl.size()>0){
			System.out.println("if size > 0 :-------->>");
			isAdUserFlag = getUserDtl.get(0).getIsAdUser();
			System.out.println("isAdUserFlag :-------->>"+isAdUserFlag);
			if(isAdUserFlag == 'Y'){
				System.out.println("isAdUserFlag = Y :-------->>");
				 dtoProfileDetails = docMgmtImpl.getUserProfileByAdUser(loginUserName);
				 for (DTOUserMst profileDetail : dtoProfileDetails) {
						if(!htmlContent.equals("")){
							htmlContent += "&"; 
						}
						htmlContent += profileDetail.getUserGroupCode()+"::"+profileDetail.getUserGroupName();
					}
				 if(htmlContent.isEmpty()){
					 htmlContent = "true";
				 }
			}
			else{
				System.out.println("isAdUserFlag = :-------->>"+isAdUserFlag);
				dtoProfileDetails = docMgmtImpl.getUserProfileByUserName(loginUserName);
				
				for (DTOUserMst profileDetail : dtoProfileDetails) {
					if(!htmlContent.equals("")){
						htmlContent += "&"; 
					}
					htmlContent += profileDetail.getUserGroupCode()+"::"+profileDetail.getUserGroupName();
				}
				if(htmlContent.isEmpty()){
					 htmlContent = "false";
				 }
			}
		}else{
			System.out.println("else size < 0 :-------->>");
			dtoProfileDetails = docMgmtImpl.getUserProfileByUserName(loginUserName);
			
			for (DTOUserMst profileDetail : dtoProfileDetails) {
				if(!htmlContent.equals("")){
					htmlContent += "&"; 
				}
				htmlContent += profileDetail.getUserGroupCode()+"::"+profileDetail.getUserGroupName();
			}
			if(htmlContent.isEmpty()){
				 htmlContent = "NotFound";
			 }
		}		
		return "html";
	}
}
