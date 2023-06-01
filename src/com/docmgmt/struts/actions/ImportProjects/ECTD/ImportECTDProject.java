package com.docmgmt.struts.actions.ImportProjects.ECTD;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOTemplateMst;
import com.docmgmt.dto.DTOTemplateNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.services.ZipManager;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ImportECTDProject extends ActionSupport {
	static final long serialVersionUID = 1L;
	
	DTOWorkSpaceNodeDetail rootNodeDtl;
	File upload; 
	String uploadFileName;
	String uploadContentType;
	String htmlContent;
	DTOWorkSpaceMst workspace;
	String templateId;
	Vector<DTOTemplateMst> getTemplateDtl;
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<EctdError> ectdErrorList;
	String srcType,path;

	
	@Override
	public String execute() throws Exception {
		getTemplateDtl = docMgmtImpl.getAllTemplates();
		return SUCCESS;
	}
	
	public String upload0000Seq() {
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		///////////////////////////////////////////////////////////////////////////////////////////////////
		//Check if template is selected...
		if(templateId.equals("-1")){
			htmlContent = "No Template Selected...";
			return ERROR;
		}
		if(srcType == null || srcType.equals("")){
			htmlContent = "No File Found...";
			return ERROR;
		}
		else if(srcType.equals("file")){
			//Check if file uploaded... 
			if(upload == null){
				htmlContent = "No File Found...";
				return ERROR;
			}
		}else if(srcType.equals("path")){
			upload = new File(path);
			uploadFileName = upload.getName();
			if(!upload.exists()){
				htmlContent = "No File Found...";
				return ERROR;
			}
		}
		//If base folder exists...
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		File importDir = propertyInfo.getDir("ImportProjectPath");
		if(importDir == null){
			htmlContent="Path Not Found...";
			return ERROR;
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////
		
		//User Directory
		File userDir = new File(importDir,Integer.toString(userId));
		if(userDir.exists()){
			File[] dirChildren = userDir.listFiles();
			for (File file : dirChildren) {
				FileManager.deleteDir(file);
			}
		}else{
			userDir.mkdirs();
		}
		//Copy uploaded file to base folder
		FileManager fileManager = new FileManager();
		File zipFile = new File(userDir,uploadFileName);
		try {
			fileManager.copyDirectory(upload, zipFile);
		} catch (RuntimeException e) {
			//If File not copied successfully... 
			htmlContent="Unable to store given File...";
			e.printStackTrace();
			return ERROR;
		}
		
		//Set regex list to validate zip
		ArrayList<String> entryRegexList = new ArrayList<String>();
		//It must contains the 0000 sequence
		entryRegexList.add("^0000/$");
		//There must be the index.xml file under 0000 directory
		entryRegexList.add("^0000/index\\.xml$");
		
		//Validate zip file and entries within it.
		if(!ZipManager.isValidZip(zipFile, entryRegexList)){
			htmlContent ="Invalid Zip File Uploaded...";
			zipFile.delete();
			return ERROR;
		}
		
		//If zip is valid then extract it.
		ZipManager.unZip(zipFile, userDir.getAbsolutePath());
		//Deleting Uploaded zip file after extracting it.
		zipFile.delete();
		
		//Find index.xml.
		File indexXml = new File(userDir.getAbsolutePath()+"/0000/index.xml");
		if(!indexXml.exists()){
			htmlContent ="Index Xml File Not found...";
			return ERROR;
		}
		
		System.out.println("index.xml exists...");
		
		//Get template's root node
		DTOTemplateNodeDetail rootNode= DocMgmtImpl.getTemplateRootNode(templateId);
		if(rootNode == null || rootNode.getNodeId() == 0){
			//Invalid template found with no root or multiple roots...
			htmlContent ="Unable To Found Template's Root Node...";
			return ERROR;
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Get Template in tree form.
		int rootNodeId = rootNode.getNodeId();
		DTOTemplateNodeDetail rootNodeDtlWithTree = DocMgmtImpl.getTemplateNodeDtlTree(templateId, rootNodeId);
		
		//Read indexXml
		EctdXmlReader ectdXmlReader = new EctdXmlReader(indexXml);
		
		DTOWorkSpaceMst wsMst = ectdXmlReader.readEctdIndexXml(rootNodeDtlWithTree); 
		ectdErrorList = wsMst.getEctdErrorList();
		if(wsMst == null || wsMst.getRootNodeDtl() == null){
			rootNodeDtl = null;
			addActionError("Cannot Import Uploaded ECTD");
		}else{
			rootNodeDtl = wsMst.getRootNodeDtl();		
			//System.out.println("\n"+rootNodeDtl.getChildNodeList().get(0).getNodeName());
		}
		try {
			FileOutputStream out = new FileOutputStream(new File(userDir+"/imp_0000.dat"));
			ObjectOutputStream obOutStrm = new ObjectOutputStream(out);
			obOutStrm.writeObject(wsMst);
			obOutStrm.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//readTree(rootNodeDtl, 1);
		
		return SUCCESS;
	}
	
	public String save(){
		
		System.out.println(workspace.getWorkSpaceDesc());
		System.out.println(workspace.getLocationCode());
		System.out.println(workspace.getClientCode());
		System.out.println(workspace.getProjectCode());
		System.out.println(workspace.getTemplateId());
		System.out.println(workspace.getTemplateDesc());
		
		
		//ProjectDB projectCreator = new ProjectDB();
		//projectCreator.createNewProject(wsMst);
		
		return SUCCESS;
	}
	/*private void readTree(DTOWorkSpaceNodeDetail treeNode,int level){
		
		char [] indents = new char[level];
		Arrays.fill(indents, ' ');
		String indenting = Arrays.toString(indents).replaceAll(", ", "");
		indenting = indenting.substring(1, indenting.length()-1);
		
		System.out.println(indenting+" N"+treeNode.getNodeId()+" P"+treeNode.getParentNodeId()+" #"+treeNode.getNodeNo()+" Node:"+treeNode.getNodeName()+" folder:"+treeNode.getFolderName());
		for (Iterator<DTOWorkSpaceNodeDetail> iterator = treeNode.getChildNodeList().iterator(); iterator.hasNext();) {
			DTOWorkSpaceNodeDetail currNode = iterator.next();
			readTree(currNode, level+1);
		}
	}*/
	
	
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public DTOWorkSpaceNodeDetail getRootNodeDtl() {
		return rootNodeDtl;
	}
	
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public File getUpload() {
		return upload;
	}
	public String getHtmlContent() {
		return htmlContent;
	}
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	public DTOWorkSpaceMst getWorkspace() {
		return workspace;
	}
	public void setWorkspace(DTOWorkSpaceMst workspace) {
		this.workspace = workspace;
	}
	public Vector<DTOTemplateMst> getGetTemplateDtl() {
		return getTemplateDtl;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public void setSrcType(String srcType) {
		this.srcType = srcType;
	}
	public ArrayList<EctdError> getEctdErrorList() {
		return ectdErrorList;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
