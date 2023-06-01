package com.docmgmt.server.webinterface.beans;

import java.io.File;

import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.db.DocMgmtImpl;

public class GenerateWorkspaceEctdAttrTree { 

	private Object docMgmtLog;
	private int userCode;
	private String userType;
	
	
	public GenerateWorkspaceEctdAttrTree(){ 
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
		//TempTreeBean tree;
		WorkspaceEctdAttributeTree tree;
		String fileName="tree" + wsId + ".txt";
		
		PropertyInfo propInfo=PropertyInfo.getPropInfo();
		String EctdAttributeTreePath=propInfo.getValue("WorkspaceEctdAttrTree");
		
		File EctdAttributeTreeFile=new File(EctdAttributeTreePath + File.separator + fileName);
		
		
		if(EctdAttributeTreeFile.exists()){
			EctdAttributeTreeFile.delete();
		}
		
		
		if(!new File(EctdAttributeTreePath).exists()){
			new File(EctdAttributeTreePath).mkdir();
		}
		//tree=new TempTreeBean();
	
			tree=new WorkspaceEctdAttributeTree();
			tree.setUserType(userType);
			tree.getWorkspaceTreeHtml(wsId,userGroupCode.intValue(),userCode.intValue(),EctdAttributeTreeFile);
	
		
		propInfo=null;
		EctdAttributeTreeFile=null;
		
		tree=null;
	}
	
	public void deleteTree(String wsId){
		String fileName="tree" + wsId + ".txt";
		
		PropertyInfo propInfo=PropertyInfo.getPropInfo();
		String EctdAttributeTreePath=propInfo.getValue("WorkspaceEctdAttrTree");
		
		File EctdAttributeTreeFile=new File(EctdAttributeTreePath + File.separator + fileName);		
		
		if(EctdAttributeTreeFile.exists()){
			EctdAttributeTreeFile.delete();
		}
	}
} 
