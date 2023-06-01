package com.docmgmt.server.webinterface.beans;

import java.io.File;

import com.docmgmt.server.prop.PropertyInfo;

public class DeleteAllTrees {

	public DeleteAllTrees() {
	}
	
	public void DeleteAllGeneratedTrees(String WorkspaceId){
		
		
		String fileName="tree" + WorkspaceId + ".txt";
        String path="";
        File file;
		PropertyInfo propInfo=PropertyInfo.getPropInfo();
		
		//Paste Tree
		path=propInfo.getValue("AdminFilePathForPaste");
		file=new File(path + File.separator + fileName);
		if(file.exists()){
			file.delete();
		}
		
		path=propInfo.getValue("UserFilePathForPaste");
		file=new File(path + File.separator + fileName);
		if(file.exists()){
			file.delete();
		}
		
		path=propInfo.getValue("SuperUserFilePathForPaste");
		file=new File(path + File.separator + fileName);
		if(file.exists()){
			file.delete();
		}
		
		
		if(file.exists()){
			file.delete();
		}
		
		//Copy Tree
        fileName="tree" + WorkspaceId + ".txt";
        path="";
		propInfo=PropertyInfo.getPropInfo();
		
		path=propInfo.getValue("AdminFilePathForCopy");
		file=new File(path + File.separator + fileName);
		if(file.exists()){
			file.delete();
		}

		path=propInfo.getValue("UserFilePathForCopy");
		file=new File(path + File.separator + fileName);
		if(file.exists()){
			file.delete();
		}

		path=propInfo.getValue("SuperUserFilePathForCopy");
		file=new File(path + File.separator + fileName);
		if(file.exists()){
			file.delete();
		}
		
		
		//Admin,User & Super user Tree Delete 
		fileName="tree" + WorkspaceId + ".txt";
        path="";
		propInfo=PropertyInfo.getPropInfo();
		
		path=propInfo.getValue("AdminFilePath");
		file=new File(path + File.separator + fileName);
		if(file.exists()){
			file.delete();
		}

		path=propInfo.getValue("UserFilePath");
		file=new File(path + File.separator + fileName);
		if(file.exists()){
			file.delete();
		}

		path=propInfo.getValue("SuperUserFilePath");
		file=new File(path + File.separator + fileName);
		if(file.exists()){
			file.delete();
		}
		
		path=propInfo.getValue("WorkspaceEctdAttrTree");
		file=new File(path + File.separator + fileName);
		if(file.exists()){
			file.delete();
		}
		
		
	}
}
