package com.docmgmt.struts.actions.ImportProjects;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.services.ReadDirectory;
import com.docmgmt.server.webinterface.services.ZipManager;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ImportProjectAction extends ActionSupport {

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ReadDirectory readdir = new ReadDirectory();
	ArrayList<Object[]> dirDetails = null;

	@Override
	public String execute() throws Exception {
		importProject();
		return SUCCESS;
	}

	public String importProject() {
			PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
			File baseImpDir = propertyInfo.getDir("ImportProjectPath");
			if(baseImpDir == null){
				return "pathNotFound";
			}
			
			File importFile = new File(baseImpDir,uploadFileFileName);
			
			System.out.println("Import File Path...."+importFile);System.out.println("UploadedFileFilename...."+uploadFileFileName);
			
			FileManager fileManager = new FileManager();
			fileManager.copyDirectory(uploadFile,importFile);
	
			String destPath = baseImpDir.getAbsolutePath()+"/"+"Temp";
			boolean isUnzipped = ZipManager.unZip(importFile, destPath);
			File[] projectRoot = null;
			if(isUnzipped){//If unZipped properly
				File projectDir = new File(destPath);
				projectRoot = projectDir.listFiles();
			}
			
			if(projectRoot!=null && projectRoot.length==1)
			{
				destPath = projectRoot[0].getAbsolutePath();
			}
			else{
				destPath=null;
			}
			
			if(destPath != null){
				dirDetails = readdir.dirRead(destPath);
			}
			else{
				dirDetails = null;
				addActionError("Incorrect File");
			}
			
			// Insert Database Call  
					
			if(dirDetails!=null){
	 
				boolean workspaceexist = docMgmtImpl.workspaceNameExist(workSpaceDesc);
		
				if(workspaceexist == false)
				{
				    String workspaceFolder = propertyInfo.getValue("BaseWorkFolder");
				    String publicationFolder= propertyInfo.getValue("BasePublishFolder");
				    
				    int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
				    DTOWorkSpaceMst dto = new DTOWorkSpaceMst();
					dto.setWorkSpaceDesc(workSpaceDesc);
					dto.setLocationCode(locationCode);
					dto.setDeptCode(deptCode);
					dto.setClientCode(clientCode);
					dto.setProjectCode(projectCode);
					dto.setDocTypeCode("0001");//Passing Hard code 'DocTypeCode' as its discarded from the Import form  
					dto.setTemplateId("");
					dto.setTemplateDesc("");
					dto.setBaseWorkFolder(workspaceFolder);
					dto.setBasePublishFolder(publicationFolder);
					dto.setModifyBy(userId);
					dto.setCreatedBy(userId);
					dto.setLastAccessedBy(userId);
					dto.setProjectType('I');
					dto.setRemark(remark);
					
					String workspaceId=docMgmtImpl.insertWorkspaceMst(dto);
					
					Timestamp ts=new Timestamp(System.currentTimeMillis());
					
					ArrayList<DTOWorkSpaceNodeDetail> allNodesDtl = new ArrayList<DTOWorkSpaceNodeDetail>();
					ArrayList<DTOWorkSpaceNodeAttrDetail> allAttrDtl = new ArrayList<DTOWorkSpaceNodeAttrDetail>();
					Vector<DTOWorkSpaceNodeAttrHistory> allwsnah = new Vector<DTOWorkSpaceNodeAttrHistory>();
					ArrayList<DTOWorkSpaceUserRightsMst> userRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
					Vector<DTOStageMst> allStages = docMgmtImpl.getStageDetail();
					
					for (Object[] fileDtl : dirDetails) {
						Integer[] nodeIds =(Integer[])fileDtl[0];
						File fileObj =(File)fileDtl[1];
						
						// fill Data in WorkspacenodeDetail
						DTOWorkSpaceNodeDetail nodeDtl= new DTOWorkSpaceNodeDetail();
						nodeDtl.setWorkspaceId(workspaceId);
						nodeDtl.setNodeId(nodeIds[0]);
						nodeDtl.setParentNodeId(nodeIds[1]);
						nodeDtl.setNodeNo(nodeIds[2]);
						nodeDtl.setNodeName(fileObj.getName());
						nodeDtl.setNodeDisplayName(fileObj.getName());
						nodeDtl.setFolderName(fileObj.getName());
						nodeDtl.setNodeTypeIndi('N');
						nodeDtl.setCloneFlag('N');
						nodeDtl.setRequiredFlag('N');
						nodeDtl.setCheckOutBy(0);
						nodeDtl.setPublishedFlag('Y');
						nodeDtl.setRemark(remark);
						nodeDtl.setModifyBy(userId);
		
						allNodesDtl.add(nodeDtl);
						
		
					if(fileObj.isFile()){
							
						//Fill Data in WorkspacenodeAttrDetail
						
						DTOWorkSpaceNodeAttrDetail attrDtl = new DTOWorkSpaceNodeAttrDetail();
						attrDtl.setWorkspaceId(workspaceId);
						attrDtl.setNodeId(nodeIds[0]);
						attrDtl.setAttrId(-999);
						attrDtl.setAttrName("FileLastModified");
						attrDtl.setAttrValue(ts.toString());
						attrDtl.setModifyBy(userId);
						attrDtl.setAttrForIndi("0001");
						
						allAttrDtl.add(attrDtl);
						
							int newTranNo = docMgmtImpl.getNewTranNo(workspaceId);
							//Fill Data into workspacenodeHistory
							
							DTOWorkSpaceNodeHistory dtowsnh = new DTOWorkSpaceNodeHistory();
							dtowsnh.setWorkSpaceId(workspaceId);
							dtowsnh.setNodeId(nodeDtl.getNodeId());
							dtowsnh.setTranNo(newTranNo);
							dtowsnh.setFileName(fileObj.getName());
							dtowsnh.setFileType("");
							dtowsnh.setFolderName("/"+workspaceId+"/"+nodeDtl.getNodeId()+"/"+newTranNo);
							dtowsnh.setRequiredFlag('Y');
							dtowsnh.setStageId(100); //Stage is by default approved...
							dtowsnh.setRemark(remark);
							dtowsnh.setModifyBy(userId);
							dtowsnh.setStatusIndi('N');
							dtowsnh.setDefaultFileFormat("");
							docMgmtImpl.insertNodeHistory(dtowsnh);
							fileManager.copyDirectory(fileObj,new File (dto.getBaseWorkFolder()+dtowsnh.getFolderName()+"/"+fileObj.getName()));
		//					System.out.println(".................................File Copied..................................");
							
							//Fill Data into workspacenodeattrHistory					
							DTOWorkSpaceNodeAttrHistory dtowsnah = new DTOWorkSpaceNodeAttrHistory();
							dtowsnah.setWorkSpaceId(workspaceId);
							dtowsnah.setNodeId(nodeDtl.getNodeId());
							dtowsnah.setTranNo(newTranNo);
							dtowsnah.setAttrId(-999);
							dtowsnah.setAttrValue(ts.toString());
							dtowsnah.setModifyBy(userId);
							allwsnah.add(dtowsnah);
		
							//Fill Data into workspacenodeversionHistory
					
							DTOWorkSpaceNodeVersionHistory dtowsnvh = new DTOWorkSpaceNodeVersionHistory();
							dtowsnvh.setWorkspaceId(workspaceId);
							dtowsnvh.setNodeId(nodeDtl.getNodeId());
							dtowsnvh.setTranNo(newTranNo);
							dtowsnvh.setPublished('N');
							dtowsnvh.setDownloaded('N');
							dtowsnvh.setActivityId("");
							dtowsnvh.setModifyBy(userId);
							dtowsnvh.setExecutedBy(userId);
							dtowsnvh.setUserDefineVersionId("A-1");
							
							docMgmtImpl.insertWorkspaceNodeVersionHistory(dtowsnvh);
					}
						
					}
					
					
					docMgmtImpl.insertWorkspaceNodeDetail(allNodesDtl, 1);
					docMgmtImpl.insertWorkspaceNodeAttrDetail(allAttrDtl);
					docMgmtImpl.InsertUpdateNodeAttrHistory(allwsnah);
					
					//Fill Data in workSpaceusermst
					//For Default Admin user & User Group Setup
					 
					DTOUserMst dtoum = docMgmtImpl.getUserInfo(userCode);
					int userGroupCode = dtoum.getUserGroupCode();
					
					DTOWorkSpaceUserMst dtowsum = new DTOWorkSpaceUserMst();
					dtowsum.setWorkSpaceId(workspaceId);
					dtowsum.setUserGroupCode(userGroupCode);
					dtowsum.setUserCode(userId);
					dtowsum.setAdminFlag('N');
					dtowsum.setRemark(remark);
					dtowsum.setModifyBy(userId);
					Date fromdt = ts;
					dtowsum.setFromDt(fromdt);
					Calendar cal = Calendar.getInstance();
					cal.roll(Calendar.YEAR, 50);
					Date todt = cal.getTime();
					System.out.println("From Date : "+fromdt+"->To Date: "+todt);
					dtowsum.setToDt(todt);
		
					docMgmtImpl.insertUpdateUsertoWorkspace(dtowsum, new int [] {userId});
					
					//Fill Data in workSpaceuserrightsmst
					
					DTOWorkSpaceUserRightsMst dtowsurm = new DTOWorkSpaceUserRightsMst();
		
					dtowsurm.setWorkSpaceId(workspaceId);
					dtowsurm.setUserGroupCode(userGroupCode);
					dtowsurm.setUserCode(userCode);
					dtowsurm.setCanReadFlag('Y');
					dtowsurm.setCanAddFlag('Y');
					dtowsurm.setCanEditFlag('Y');
					dtowsurm.setCanDeleteFlag('Y');
					dtowsurm.setAdvancedRights("Y");
					dtowsurm.setRemark(remark);
					dtowsurm.setModifyBy(userId);
					
					//Create stage wise clones for inserting the user rights 
					for (Iterator<DTOStageMst> iterator = allStages.iterator(); iterator.hasNext();) {
						DTOStageMst dtoStageMst = iterator.next();
						DTOWorkSpaceUserRightsMst dtoClone = dtowsurm.clone();
						dtoClone.setStageId(dtoStageMst.getStageId());
						userRightsList.add(dtoClone);
					}
					docMgmtImpl.insertMultipleUserRights(userRightsList);
					addActionMessage("<a href='WorkspaceOpen.do?ws_id=" + workspaceId + "'>" + workSpaceDesc + "</a> Created Successfully...");
					//addActionMessage(workSpaceDesc+" Created Successfully...");
				}
				else{
					addActionError(workSpaceDesc+" Already Exist");
				}
			}
		
			// Delete whole ImportProject Directory 
			FileManager.deleteDir(baseImpDir);

		return SUCCESS;
}
	
	
	public String workSpaceDesc;
	public String locationCode;
	public int userCode;
	public String deptCode;
	public String clientCode;
	public String projectCode;
	public String docTypeCode;
	public String templateId;
	public String remark;
	public File uploadFile;
	public String uploadFileFileName;
	 

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getDocTypeCode() {
		return docTypeCode;
	}

	public void setDocTypeCode(String docTypeCode) {
		this.docTypeCode = docTypeCode;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}


	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}

	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}
	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	
}


