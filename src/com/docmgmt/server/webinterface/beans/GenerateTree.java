package com.docmgmt.server.webinterface.beans;

import java.io.File;

import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.beans.TempSPLTreeBean;
import com.docmgmt.server.webinterface.beans.TempTreeBean;
import com.docmgmt.server.db.DocMgmtImpl;

public class GenerateTree { 

	private Object docMgmtLog;
	private int userCode;
	private String userType;
	
	
	public GenerateTree(){ 
	} 
/**
 * 
 * @return
 * @throws Exception
 * This methods ges the remote reference by looking up to the registry
 */
DocMgmtImpl docMgmt = new DocMgmtImpl();
	
	public void generateTree(String wsId,String projectType,Integer userCode,Integer userGroupCode,String userType){
		System.out.println("Generating file for workspace " + wsId);
		TempSPLTreeBean splTree;
		TempTreeBean tree;
		
		String fileName="tree" + wsId + ".txt";
		String adminFilePath;
		String userFilePath;
		String filePath;
		
		PropertyInfo propInfo=PropertyInfo.getPropInfo();
		adminFilePath=propInfo.getValue("AdminFilePath");
		userFilePath=propInfo.getValue("UserFilePath");
		String superuserfilepath=propInfo.getValue("SuperUserFilePath");
		
		
		File userFile=new File(userFilePath + File.separator + fileName);
		File adminFile=new File(adminFilePath + File.separator + fileName);
		File superuserFile=new File(superuserfilepath + File.separator + fileName);
		
		if(userFile.exists()){
			userFile.delete();
		}
		if(adminFile.exists()){
			adminFile.delete();
		}
		if(superuserFile.exists()){
			superuserFile.delete();
		}
		
		if(!new File(adminFilePath).exists()){
			new File(adminFilePath).mkdir();
		}
		if(!new File(superuserfilepath).exists()){
			new File(superuserfilepath).mkdir();
		}
		if(!new File(userFilePath).exists()){
			new File(userFilePath).mkdir();
		}
		
		/*if(projectType.charAt(0)=='S'){
			splTree=new TempSPLTreeBean();
			splTree.setUserType(userType);
			if(userType.equalsIgnoreCase("0002")){
				splTree.getWorkspaceTreeHtml(wsId,userGroupCode.intValue(),userCode.intValue(),adminFile);
			}else if(userType.equalsIgnoreCase("0003")){
				splTree.getWorkspaceTreeHtml(wsId,userGroupCode.intValue(),userCode.intValue(),userFile);
			}else if(userType.equalsIgnoreCase("0001")){
				splTree.getWorkspaceTreeHtml(wsId,userGroupCode.intValue(),userCode.intValue(),superuserFile);
			}
		}else{*/
			tree=new TempTreeBean();
			tree.setUserType(userType);
			if(userType.equalsIgnoreCase("0002")){
				tree.getWorkspaceTreeHtml(wsId,userGroupCode.intValue(),userCode.intValue(),adminFile);
			}else if(userType.equalsIgnoreCase("0003")){
				tree.getWorkspaceTreeHtml(wsId,userGroupCode.intValue(),userCode.intValue(),userFile);
			}else if(userType.equalsIgnoreCase("0001")){
				tree.getWorkspaceTreeHtml(wsId,userGroupCode.intValue(),userCode.intValue(),superuserFile);
			}
		//}
		
		propInfo=null;
		adminFile=null;
		splTree=null;
		tree=null;
	}
	
	public void deleteTree(String wsId){
		String fileName="tree" + wsId + ".txt";
		String adminFilePath;
		String userFilePath;
		String filePath;
		
		PropertyInfo propInfo=PropertyInfo.getPropInfo();
		adminFilePath=propInfo.getValue("AdminFilePath");
		userFilePath=propInfo.getValue("UserFilePath");
		String superuserfilepath=propInfo.getValue("SuperUserFilePath");
		
		File userFile=new File(userFilePath + File.separator + fileName);
		File adminFile=new File(adminFilePath + File.separator + fileName);
		File superuserFile=new File(superuserfilepath + File.separator + fileName);
		if(userFile.exists()){
			userFile.delete();
		}
		if(adminFile.exists()){
			adminFile.delete();
		}
		if(superuserFile.exists()){
			superuserFile.delete();
		}
		
	}
} 
