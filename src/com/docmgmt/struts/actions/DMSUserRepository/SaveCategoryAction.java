package com.docmgmt.struts.actions.DMSUserRepository;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceNodeVersionHistory;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.dto.DTOWorkspaceNodeReferenceDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveCategoryAction extends ActionSupport {

private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	
	public String nodeName;
	public String folderName;
	public String workSpaceId;
	public String remark;
	public String selAttributes;
	public int attrId;
	public String attrName;
	public String userDetails;
	public String frmDate;
	public String toDate;
	public int nodeId=0;
	public String userCodeList;
	public File refDocUpload;	
	public String refDocUploadFileName;
	public String htmlContent;
	public String usrTyp;
	public String refDocUploadContentType;
	public String fileUploading;
	public String workspaceDtl;
	public String docType;
	
	public String execute()
	{
		if(nodeId == 0)
			addCategory();
		else
			editCategory();
		
		/*int newNodeId = docMgmtImpl.getmaxNodeId(workSpaceId) +1;
		int userId =Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		if(nodeId==0 )
			fileUploading="New";
		if(refDocUpload == null)
			refDocUploadContentType=refDocUploadFileName="";
		
		int newTranNo=docMgmtImpl.getNewTranNo(workSpaceId);
		*//**
		 * Insert node history [start]
		 * *//*
			if (nodeId == 0 || !fileUploading.equalsIgnoreCase("Current")) 
			{
				DTOWorkSpaceNodeHistory newNodeHistory=new DTOWorkSpaceNodeHistory();
				newNodeHistory.setWorkSpaceId(workSpaceId);
				if(nodeId== 0)
						newNodeHistory.setNodeId(newNodeId);
				else
					newNodeHistory.setNodeId(nodeId);
				
				newNodeHistory.setTranNo(newTranNo);
				if(fileUploading.equalsIgnoreCase("Remove") || refDocUpload == null )
				{
					newNodeHistory.setFileName("No File");
					newNodeHistory.setFileType("");
				}
				else
				{
					newNodeHistory.setFileName(refDocUploadFileName);
					String fileExt = "";
					if (refDocUploadFileName.contains(".")) {
						fileExt =refDocUploadFileName.substring(refDocUploadFileName.indexOf(".")+1);
					}
					newNodeHistory.setFileType(fileExt);
				}
				newNodeHistory.setRequiredFlag('Y');
				newNodeHistory.setRemark("");
				newNodeHistory.setModifyBy(userId);
				newNodeHistory.setStatusIndi('N');
				newNodeHistory.setDefaultFileFormat("");
				newNodeHistory.setFolderName("/"+workSpaceId+"/"+newNodeHistory.getNodeId()+"/"+newTranNo);
				newNodeHistory.setStageId(0);
				if(!(nodeId==0 && refDocUpload == null))
					docMgmtImpl.insertNodeHistory(newNodeHistory);
				//Logic For File Upload
				if(refDocUpload != null)
				{
					DTOWorkSpaceMst dtoWsMst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
					String baseImpDir = dtoWsMst.getBaseWorkFolder()+"/"+workSpaceId+"/"+newNodeHistory.getNodeId()+"/"+newTranNo+"/";;
					File importFile = new File(baseImpDir,refDocUploadFileName);
					FileManager fileManager = new FileManager();
					fileManager.copyDirectory(refDocUpload,importFile);
				}
				//File Upload End
			}
		*//**
		 * Insert node history [end]
		 **//*
			
		*//**
		 * Insert node Version history[end]
		 * *//*
			if (nodeId == 0 || !fileUploading.equalsIgnoreCase("Current") ) 
			{
				DTOWorkSpaceNodeVersionHistory dtoWsNodeVersionHistory = new DTOWorkSpaceNodeVersionHistory();
				dtoWsNodeVersionHistory.setWorkspaceId(workSpaceId);
				dtoWsNodeVersionHistory.setTranNo(newTranNo);	
				dtoWsNodeVersionHistory.setPublished('N');
				dtoWsNodeVersionHistory.setDownloaded('N');
				dtoWsNodeVersionHistory.setModifyBy(userId);
				dtoWsNodeVersionHistory.setExecutedBy(userId);
				String version="";
				if (nodeId == 0) 
				{
					dtoWsNodeVersionHistory.setNodeId(newNodeId);
					version = "1.000";// start from 1.001 default value.
				}
				else if (!fileUploading.equalsIgnoreCase("Current"))
				{
					dtoWsNodeVersionHistory.setNodeId(nodeId);
					DTOWorkSpaceNodeVersionHistory dtoWsNdVerHis = new DTOWorkSpaceNodeVersionHistory();
					dtoWsNdVerHis.setWorkspaceId(workSpaceId);
					dtoWsNdVerHis.setNodeId(nodeId);
					Vector<DTOWorkSpaceNodeVersionHistory> wsNdVerHistoryList = docMgmtImpl.getWorkSpaceNodeVersionDetail(dtoWsNdVerHis);
					DTOWorkSpaceNodeVersionHistory dtoWsHistory = new DTOWorkSpaceNodeVersionHistory();
					if (wsNdVerHistoryList.size() > 0) {
					dtoWsHistory = wsNdVerHistoryList.get(wsNdVerHistoryList.size()-1);
					}
					version = dtoWsHistory.getUserDefineVersionId();
					float t=1.0f;
					if(version != null)
					{
						t  = Float.parseFloat(version);
						t = t+0.001f;
					}
					version= String.format("%5.3f", t);
				}
				dtoWsNodeVersionHistory.setUserDefineVersionId(version); 
				docMgmtImpl.insertWorkspaceNodeVersionHistory(dtoWsNodeVersionHistory,false);
			}
		
		*//**
		 * Insert node Version history[end]
		 * *//*
			
			
		*//**
		 * Insert node details [start]
		 * *//*
			int maxNodeNo,currNodeId=0 ;boolean addMode = false;
			String defaultFileFormat="";
			
			int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
			ArrayList<DTOWorkSpaceNodeDetail> wsNodeDtlList = new ArrayList<DTOWorkSpaceNodeDetail>();
			
			if (nodeId == 0) {//If this is Add Catagory Mode
				currNodeId = newNodeId;
				addMode = true;
			}
			else{//If this is Edit Catagory Mode
				addMode = false;
				currNodeId = nodeId;
			}
			
			DTOWorkSpaceNodeDetail dtoWsNodeDtl = new DTOWorkSpaceNodeDetail();
			dtoWsNodeDtl.setWorkspaceId(workSpaceId);
			
			if (addMode) {
				dtoWsNodeDtl.setNodeId(currNodeId);
				maxNodeNo = docMgmtImpl.getmaxNodeNo(workSpaceId, currNodeId);
				dtoWsNodeDtl.setNodeNo(maxNodeNo+1);
				int parentNodeId = 1;
				dtoWsNodeDtl.setParentNodeId(parentNodeId);
			}
			else//If this is Edit Catagory Mode
			{
				Vector<DTOWorkSpaceNodeDetail> wsNodeDtl = docMgmtImpl.getNodeDetail(workSpaceId, currNodeId);
				for (int i = 0; i < wsNodeDtl.size(); i++) {
					dtoWsNodeDtl.setNodeId(wsNodeDtl.get(i).getNodeId());
					dtoWsNodeDtl.setNodeNo(wsNodeDtl.get(i).getNodeNo());
					dtoWsNodeDtl.setParentNodeId(wsNodeDtl.get(i).getParentNodeId());
				}
			}
			dtoWsNodeDtl.setNodeName(nodeName);
			dtoWsNodeDtl.setNodeDisplayName(nodeName);
			dtoWsNodeDtl.setNodeTypeIndi('N');
			dtoWsNodeDtl.setFolderName(folderName);
			dtoWsNodeDtl.setCloneFlag('N');
			dtoWsNodeDtl.setRequiredFlag('N');
			dtoWsNodeDtl.setCheckOutBy(0);
			dtoWsNodeDtl.setPublishedFlag('Y');
			dtoWsNodeDtl.setRemark(remark);
			dtoWsNodeDtl.setModifyBy(userId);
			dtoWsNodeDtl.setDefaultFileFormat(defaultFileFormat);
			wsNodeDtlList.add(dtoWsNodeDtl);
			if(addMode)
				docMgmtImpl.insertWorkspaceNodeDetail(wsNodeDtlList, 1);
			else
				docMgmtImpl.insertWorkspaceNodeDetail(wsNodeDtlList, 2);
		*//**
		 * Insert node details [end]
		 * *//*
			
		*//**
		 * Check Attribute and insert [start]
		 * *//*
			ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrList = new ArrayList<DTOWorkSpaceNodeAttrDetail>();
			if (selAttributes != null ) 
			{
				Vector<DTOWorkSpaceNodeAttrDetail>  WorkSpaceNodeAttrDetail=docMgmtImpl.getNodeAttrDetail(workSpaceId,nodeId);
				if( selAttributes.trim().length() > 0)
				{
					if(selAttributes.endsWith(","))
					{
						selAttributes.substring(0, selAttributes.length()-1);
					}		
					String [] attrList = selAttributes.split(",");
					for (int i = 0; i < attrList.length; i++) 
					{
						DTOWorkSpaceNodeAttrDetail dtoWsNodeAttrDtl = new DTOWorkSpaceNodeAttrDetail();
						dtoWsNodeAttrDtl.setWorkspaceId(workSpaceId);
						int a = Integer.parseInt(attrList[i].trim());
						dtoWsNodeAttrDtl.setAttrId(a);
						DTOAttributeMst dtoAttrMst = docMgmtImpl.getAttribute(Integer.parseInt(attrList[i].trim()));
						dtoWsNodeAttrDtl.setAttrName(dtoAttrMst.getAttrName());
						dtoWsNodeAttrDtl.setNodeId(currNodeId);
						dtoWsNodeAttrDtl.setAttrForIndi(dtoAttrMst.getAttrForIndiId());
						if(dtoAttrMst.getAttrValue() == "")
							dtoWsNodeAttrDtl.setAttrValue("");
						else
							dtoWsNodeAttrDtl.setAttrValue(dtoAttrMst.getAttrValue());
						dtoWsNodeAttrDtl.setModifyBy(userId);
						nodeAttrList.add(dtoWsNodeAttrDtl);
					}
					if(!addMode){//if edit mode then first delete all attribute and then insert
						
						ArrayList<DTOWorkSpaceNodeAttrDetail> WorkSpaceNodeAttrDetailList=new ArrayList<DTOWorkSpaceNodeAttrDetail>();
						WorkSpaceNodeAttrDetailList=convertToVector(WorkSpaceNodeAttrDetail);						
						docMgmtImpl.deleteWorkspaceNodeAttrDetail(WorkSpaceNodeAttrDetailList);																			
					}
					docMgmtImpl.insertWorkspaceNodeAttrDetail(nodeAttrList);
				}
				else
				{
					ArrayList<DTOWorkSpaceNodeAttrDetail> WorkSpaceNodeAttrDetailList=new ArrayList<DTOWorkSpaceNodeAttrDetail>();
					WorkSpaceNodeAttrDetailList=convertToVector(WorkSpaceNodeAttrDetail);						
					docMgmtImpl.deleteWorkspaceNodeAttrDetail(WorkSpaceNodeAttrDetailList);																			
				}
				Vector<DTOWorkSpaceNodeAttrHistory> wsNodeAttrHistory = new Vector<DTOWorkSpaceNodeAttrHistory>();
				for (int i = 0; i < nodeAttrList.size(); i++) 
				{
					DTOWorkSpaceNodeAttrHistory dtoWsNodeAttrHistory = new DTOWorkSpaceNodeAttrHistory();
					dtoWsNodeAttrHistory.setWorkSpaceId(workSpaceId);
					dtoWsNodeAttrHistory.setNodeId(currNodeId);
					dtoWsNodeAttrHistory.setTranNo(newTranNo);
					dtoWsNodeAttrHistory.setAttrId(nodeAttrList.get(i).getAttrId());
					dtoWsNodeAttrHistory.setAttrValue(nodeAttrList.get(i).getAttrValue());
					dtoWsNodeAttrHistory.setRemark("");
					dtoWsNodeAttrHistory.setModifyBy(userId);
					dtoWsNodeAttrHistory.setStatusIndi('N');
					wsNodeAttrHistory.add(dtoWsNodeAttrHistory);
				}
				docMgmtImpl.insertNodeAttrHistory(wsNodeAttrHistory);
			}
			
			
			
		*//**
		 * Check Attribute and insert [end]
		 * *//*
		
		*//**
		 * Manage Users On Category [start]
		 * *//*
			ArrayList<DTOWorkSpaceUserMst> wsUsersList = new ArrayList<DTOWorkSpaceUserMst>();
			ArrayList<DTOWorkSpaceUserRightsMst> wsUserRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
			//First delete current users from category
			if(userDetails.endsWith(","))
			{
				userDetails+=userGroupCode+"_"+userId;
			}
			else
				userDetails+=","+userGroupCode+"_"+userId;
			String [] usrsList = userDetails.split(",");
			ArrayList<String> userDetailsList = new ArrayList<String>();
			for (int i = 0; i < usrsList.length; i++) 
			{
				if (!usrsList[i].equals("")) 
					userDetailsList.add(usrsList[i]);
			}
			if (userCodeList != null && userCodeList.trim().length() > 0) 
			{
				String[] sUserCodeList= userCodeList.split(",");	
				ArrayList<Integer> finalUserList = new ArrayList<Integer>(); 
				Vector<DTOUserMst> userMstList = docMgmtImpl.getAllUserDetail();
				for (int itrUserMst = 0; itrUserMst < userMstList.size(); itrUserMst++) 
				{
					DTOUserMst dto = userMstList.get(itrUserMst);
					if(!dto.getUserTypeCode().equalsIgnoreCase(usrTyp))
					{
						userMstList.remove(itrUserMst);
						itrUserMst--;
					}
				}
				
				for (int itrusercodelist = 0; itrusercodelist < userMstList.size(); itrusercodelist++) 
				{
					DTOUserMst dto = userMstList.get(itrusercodelist);
					for (int i = 0; i < sUserCodeList.length; i++) 
					{
						if (sUserCodeList[i].trim().length() > 0 && dto.getUserCode() == Integer.parseInt(sUserCodeList[i].trim())) 
						{
							finalUserList.add(Integer.parseInt(sUserCodeList[i].trim()));
						}
					}
				}
				
				System.out.println("userdetail list --- for add");
				for (int i = 0; i < userDetailsList.size(); i++) 
					System.out.println(userDetailsList.get(i));
				System.out.println("selecteduser --- final user-- for delete");
				for (int i = 0; i < finalUserList.size(); i++) 
					System.out.println(finalUserList.get(i));
				System.out.println(userDetailsList.size()+"====filter==="+finalUserList.size());
				for (int i = 0; i < finalUserList.size(); i++) 
				{
					int finaluser = finalUserList.get(i);
					if (finaluser > 0) 
					{
						for (int j = 0; j < userDetailsList.size(); j++) 
						{
							int user =Integer.parseInt(userDetailsList.get(j).split("_")[1]);
							if (user == finaluser) 
							{
								finalUserList.remove(i);
								i--;
								userDetailsList.remove(j);
								j--;
								break;
							}
						}
					}
				}
				
				for (int i = 0; i < finalUserList.size(); i++) 
				{	
					  	DTOWorkSpaceUserRightsMst dtoWsUserRightsMst = new DTOWorkSpaceUserRightsMst();
				        dtoWsUserRightsMst.setWorkSpaceId(workSpaceId);
				        dtoWsUserRightsMst.setUserGroupCode(userGroupCode);			        
						dtoWsUserRightsMst.setUserCode(finalUserList.get(i));
						dtoWsUserRightsMst.setStageId(0);							
						dtoWsUserRightsMst.setNodeId(currNodeId);					
						dtoWsUserRightsMst.setCanReadFlag('Y');
						dtoWsUserRightsMst.setCanAddFlag('Y');
						dtoWsUserRightsMst.setCanEditFlag('Y');
						dtoWsUserRightsMst.setCanDeleteFlag('Y');
						dtoWsUserRightsMst.setAdvancedRights(null);
						dtoWsUserRightsMst.setRemark("");
						dtoWsUserRightsMst.setModifyBy(userId);					
						wsUserRightsList.add(dtoWsUserRightsMst);							
				}
				docMgmtImpl.insertUpdateMultipleUserRights(wsUserRightsList, 3);
			}	
			
			wsUserRightsList=new ArrayList<DTOWorkSpaceUserRightsMst>();
			for (int i = 0; i < userDetailsList.size(); i++) 
			{
				//if (usrsList[i].trim().length()>0) 
				{
					DTOWorkSpaceUserMst dtoWsUserMst = new DTOWorkSpaceUserMst();
					dtoWsUserMst.setWorkSpaceId(workSpaceId);
					dtoWsUserMst.setUserGroupCode(new Integer(userDetailsList.get(i).split("_")[0]).intValue());
					dtoWsUserMst.setUserCode(new Integer(userDetailsList.get(i).split("_")[1]).intValue());
					dtoWsUserMst.setAdminFlag('N');
					dtoWsUserMst.setRemark("");
					dtoWsUserMst.setModifyBy(userId);
					Calendar c=new GregorianCalendar();		
					int date=c.get(Calendar.DATE);
					String month="0" + (c.get(Calendar.MONTH)+1);
					month=month.substring(month.length()-2);
					int year=c.get(Calendar.YEAR);
					frmDate=year + "/" + month + "/" + date;
					//hardcode of 50 years
					year+=50;		
					toDate=year + "/" + month + "/" + date;
					SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
					 
			        try {
			            Date frmDt =df.parse(frmDate);            
			            Date toDt = df.parse(toDate);
			            toDt.setSeconds(59);
			            toDt.setMinutes(59);
			            toDt.setHours(23);
			            dtoWsUserMst.setFromDt(frmDt);
			            dtoWsUserMst.setToDt(toDt);
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			        
			        wsUsersList.add(dtoWsUserMst);
			        
			        DTOWorkSpaceUserRightsMst dtoWsUserRightsMst = new DTOWorkSpaceUserRightsMst();
			        dtoWsUserRightsMst.setWorkSpaceId(workSpaceId);
					dtoWsUserRightsMst.setUserGroupCode(new Integer(userDetailsList.get(i).split("_")[0]).intValue());
					dtoWsUserRightsMst.setUserCode(new Integer(userDetailsList.get(i).split("_")[1]).intValue());
					dtoWsUserRightsMst.setCanReadFlag('Y');
					dtoWsUserRightsMst.setCanEditFlag('Y');
					dtoWsUserRightsMst.setCanAddFlag('Y');
					dtoWsUserRightsMst.setCanDeleteFlag('Y');
					dtoWsUserRightsMst.setAdvancedRights("Y");
					dtoWsUserRightsMst.setStageId(0);
					dtoWsUserRightsMst.setRemark("");
					dtoWsUserRightsMst.setModifyBy(userId);
					dtoWsUserRightsMst.setNodeId(currNodeId);
					wsUserRightsList.add(dtoWsUserRightsMst);
				}
			}		
			docMgmtImpl.insertUpdateMultipleUserRights(wsUserRightsList, 1);		
			docMgmtImpl.insertUpdateUsertoWorkspace(wsUsersList);
		*//**
		 * Manage Users On Category [end]
		 * */
		if (nodeId == 0) 
			htmlContent = "Category Created Successfully";
		else
			htmlContent= "Category Details Updated Successfully";
		return SUCCESS;
	}
		
	private void addCategory()
	{
		int newNodeId = docMgmtImpl.getmaxNodeId(workSpaceId) +1;
		int userId =Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int newTranNo=docMgmtImpl.getNewTranNo(workSpaceId);
		
/*		
 		starts-----------0
		ends-----------
		null---------refDocUpload
		Reference---docType
				
		starts-----------0
		ends-----------
		null---------refDocUpload
		FileUpload---docType
*/
		// Reference node selected then do this.
		if (docType.trim().equalsIgnoreCase("Reference")) 
		{
			HttpServletRequest request = ServletActionContext.getRequest();
			Enumeration<String> enum1 = request.getParameterNames();
			int refNdId = 0;
			for (String paramName =""; enum1.hasMoreElements(); ) 
			{
				paramName = enum1.nextElement();
				if(paramName.startsWith("CHK_"))
				{
					// selected nodeid from selected tree.........
					refNdId = Integer.parseInt(paramName.split("_")[1]);
				}
			}
			DTOWorkspaceNodeReferenceDetail dtoWorkspaceNodeRefDtl = new DTOWorkspaceNodeReferenceDetail();
			dtoWorkspaceNodeRefDtl.setRefNo(0);
			dtoWorkspaceNodeRefDtl.setWorkspaceId(workSpaceId);
			dtoWorkspaceNodeRefDtl.setNodeId(newNodeId);
			dtoWorkspaceNodeRefDtl.setRefWorkspaceId(workspaceDtl);
			dtoWorkspaceNodeRefDtl.setRefNodeId(refNdId);
			dtoWorkspaceNodeRefDtl.setModifyBy(userId);
			dtoWorkspaceNodeRefDtl.setStatusIndi('N');
			ArrayList<DTOWorkspaceNodeReferenceDetail> workspaceNodeReferenceList = new ArrayList<DTOWorkspaceNodeReferenceDetail>();
			workspaceNodeReferenceList.add(dtoWorkspaceNodeRefDtl);
			docMgmtImpl.insertWorkspaceNodeReferenceDetail(workspaceNodeReferenceList,1);
			
		}
		// if file uploading selected then do this.
		else
		{
			if (refDocUpload != null) 
			{
				DTOWorkSpaceNodeHistory newNodeHistory=new DTOWorkSpaceNodeHistory();
				newNodeHistory.setWorkSpaceId(workSpaceId);
				newNodeHistory.setNodeId(newNodeId);
				newNodeHistory.setTranNo(newTranNo);
				newNodeHistory.setFileName(refDocUploadFileName);
				String fileExt = "";
				if (refDocUploadFileName.contains(".")) 
					fileExt =refDocUploadFileName.substring(refDocUploadFileName.indexOf(".")+1);
				
				newNodeHistory.setFileType(fileExt);
				newNodeHistory.setRequiredFlag('Y');
				newNodeHistory.setRemark("");
				newNodeHistory.setModifyBy(userId);
				newNodeHistory.setStatusIndi('N');
				newNodeHistory.setDefaultFileFormat("");
				newNodeHistory.setFolderName("/"+workSpaceId+"/"+newNodeHistory.getNodeId()+"/"+newTranNo);
				newNodeHistory.setStageId(0);
				docMgmtImpl.insertNodeHistory(newNodeHistory);
				//Logic For File Upload
				DTOWorkSpaceMst dtoWsMst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
				String baseImpDir = dtoWsMst.getBaseWorkFolder()+"/"+workSpaceId+"/"+newNodeHistory.getNodeId()+"/"+newTranNo+"/";;
				File importFile = new File(baseImpDir,refDocUploadFileName);
				FileManager fileManager = new FileManager();
				fileManager.copyDirectory(refDocUpload,importFile);
				
				DTOWorkSpaceNodeVersionHistory dtoWsNodeVersionHistory = new DTOWorkSpaceNodeVersionHistory();
				dtoWsNodeVersionHistory.setWorkspaceId(workSpaceId);
				dtoWsNodeVersionHistory.setTranNo(newTranNo);	
				dtoWsNodeVersionHistory.setPublished('N');
				dtoWsNodeVersionHistory.setDownloaded('N');
				dtoWsNodeVersionHistory.setModifyBy(userId);
				dtoWsNodeVersionHistory.setExecutedBy(userId);
				dtoWsNodeVersionHistory.setNodeId(newNodeId);
				dtoWsNodeVersionHistory.setUserDefineVersionId("1.000");// start from 1.001 default value. 
				docMgmtImpl.insertWorkspaceNodeVersionHistory(dtoWsNodeVersionHistory,false);
			}
		}
		
		
		 /**
		 * Insert node details [start]
		 **/
			int maxNodeNo=0 ;boolean addMode = false;
			String defaultFileFormat="";
			
			int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
			ArrayList<DTOWorkSpaceNodeDetail> wsNodeDtlList = new ArrayList<DTOWorkSpaceNodeDetail>();
			DTOWorkSpaceNodeDetail dtoWsNodeDtl = new DTOWorkSpaceNodeDetail();
			dtoWsNodeDtl.setWorkspaceId(workSpaceId);
			dtoWsNodeDtl.setNodeId(newNodeId);
			maxNodeNo = docMgmtImpl.getmaxNodeNo(workSpaceId, newNodeId);
			dtoWsNodeDtl.setNodeNo(maxNodeNo+1);
			dtoWsNodeDtl.setParentNodeId(1);
			dtoWsNodeDtl.setNodeName(nodeName);
			dtoWsNodeDtl.setNodeDisplayName(nodeName);
			dtoWsNodeDtl.setNodeTypeIndi('N');
			dtoWsNodeDtl.setFolderName(folderName);
			dtoWsNodeDtl.setCloneFlag('N');
			dtoWsNodeDtl.setRequiredFlag('N');
			dtoWsNodeDtl.setCheckOutBy(0);
			dtoWsNodeDtl.setPublishedFlag('Y');
			dtoWsNodeDtl.setRemark(remark);
			dtoWsNodeDtl.setModifyBy(userId);
			dtoWsNodeDtl.setDefaultFileFormat(defaultFileFormat);
			wsNodeDtlList.add(dtoWsNodeDtl);
			docMgmtImpl.insertWorkspaceNodeDetail(wsNodeDtlList, 1);
			
		 /**
		 * Insert node details [end]
		 **/
			
			
		/**
		 * Check Attribute and insert [start]
		 * */
			ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrList = new ArrayList<DTOWorkSpaceNodeAttrDetail>();
			if (selAttributes != null ) 
			{
				//Vector<DTOWorkSpaceNodeAttrDetail>  WorkSpaceNodeAttrDetail=docMgmtImpl.getNodeAttrDetail(workSpaceId,nodeId);
				if( selAttributes.trim().length() > 0)
				{
					if(selAttributes.endsWith(","))
					{
						selAttributes.substring(0, selAttributes.length()-1);
					}		
					String [] attrList = selAttributes.split(",");
					for (int i = 0; i < attrList.length; i++) 
					{
						DTOWorkSpaceNodeAttrDetail dtoWsNodeAttrDtl = new DTOWorkSpaceNodeAttrDetail();
						dtoWsNodeAttrDtl.setWorkspaceId(workSpaceId);
						int a = Integer.parseInt(attrList[i].trim());
						dtoWsNodeAttrDtl.setAttrId(a);
						DTOAttributeMst dtoAttrMst = docMgmtImpl.getAttribute(Integer.parseInt(attrList[i].trim()));
						dtoWsNodeAttrDtl.setAttrName(dtoAttrMst.getAttrName());
						dtoWsNodeAttrDtl.setNodeId(newNodeId);
						dtoWsNodeAttrDtl.setAttrForIndi(dtoAttrMst.getAttrForIndiId());
						if(dtoAttrMst.getAttrValue() == "")
							dtoWsNodeAttrDtl.setAttrValue("");
						else
							dtoWsNodeAttrDtl.setAttrValue(dtoAttrMst.getAttrValue());
						dtoWsNodeAttrDtl.setModifyBy(userId);
						nodeAttrList.add(dtoWsNodeAttrDtl);
					}
					docMgmtImpl.insertWorkspaceNodeAttrDetail(nodeAttrList);
				}
				Vector<DTOWorkSpaceNodeAttrHistory> wsNodeAttrHistory = new Vector<DTOWorkSpaceNodeAttrHistory>();
				for (int i = 0; i < nodeAttrList.size(); i++) 
				{
					DTOWorkSpaceNodeAttrHistory dtoWsNodeAttrHistory = new DTOWorkSpaceNodeAttrHistory();
					dtoWsNodeAttrHistory.setWorkSpaceId(workSpaceId);
					dtoWsNodeAttrHistory.setNodeId(newNodeId);
					dtoWsNodeAttrHistory.setTranNo(newTranNo);
					dtoWsNodeAttrHistory.setAttrId(nodeAttrList.get(i).getAttrId());
					dtoWsNodeAttrHistory.setAttrValue(nodeAttrList.get(i).getAttrValue());
					dtoWsNodeAttrHistory.setRemark("");
					dtoWsNodeAttrHistory.setModifyBy(userId);
					dtoWsNodeAttrHistory.setStatusIndi('N');
					wsNodeAttrHistory.add(dtoWsNodeAttrHistory);
				}
				docMgmtImpl.insertNodeAttrHistory(wsNodeAttrHistory);
			}
			
		/**
		 * Check Attribute and insert [end]
		 **/
			
		/**
		 * Manage Users On Category [start]
		 * */
			ArrayList<DTOWorkSpaceUserMst> wsUsersList = new ArrayList<DTOWorkSpaceUserMst>();
			ArrayList<DTOWorkSpaceUserRightsMst> wsUserRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
			//First delete current users from category
			if(userDetails.endsWith(","))
			{
				userDetails+=userGroupCode+"_"+userId;
			}
			else
				userDetails+=","+userGroupCode+"_"+userId;
			String [] usrsList = userDetails.split(",");
			ArrayList<String> userDetailsList = new ArrayList<String>();
			for (int i = 0; i < usrsList.length; i++) 
			{
				if (!usrsList[i].equals("")) 
					userDetailsList.add(usrsList[i]);
			}
				
			
			wsUserRightsList=new ArrayList<DTOWorkSpaceUserRightsMst>();
			for (int i = 0; i < userDetailsList.size(); i++) 
			{
				//if (usrsList[i].trim().length()>0) 
				{
					DTOWorkSpaceUserMst dtoWsUserMst = new DTOWorkSpaceUserMst();
					dtoWsUserMst.setWorkSpaceId(workSpaceId);
					dtoWsUserMst.setUserGroupCode(new Integer(userDetailsList.get(i).split("_")[0]).intValue());
					dtoWsUserMst.setUserCode(new Integer(userDetailsList.get(i).split("_")[1]).intValue());
					dtoWsUserMst.setAdminFlag('N');
					dtoWsUserMst.setRemark("");
					dtoWsUserMst.setModifyBy(userId);
					Calendar c=new GregorianCalendar();		
					int date=c.get(Calendar.DATE);
					String month="0" + (c.get(Calendar.MONTH)+1);
					month=month.substring(month.length()-2);
					int year=c.get(Calendar.YEAR);
					frmDate=year + "/" + month + "/" + date;
					//hardcode of 50 years
					year+=50;		
					toDate=year + "/" + month + "/" + date;
					SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
					 
			        try {
			        	Date frmDt =df.parse(frmDate);            
			        	Date toDt = df.parse(toDate);
			            toDt.setSeconds(59);
			            toDt.setMinutes(59);
			            toDt.setHours(23);
			            dtoWsUserMst.setFromDt(frmDt);
			            dtoWsUserMst.setToDt(toDt);
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			        
			        wsUsersList.add(dtoWsUserMst);
			        
			        DTOWorkSpaceUserRightsMst dtoWsUserRightsMst = new DTOWorkSpaceUserRightsMst();
			        dtoWsUserRightsMst.setWorkSpaceId(workSpaceId);
					dtoWsUserRightsMst.setUserGroupCode(new Integer(userDetailsList.get(i).split("_")[0]).intValue());
					dtoWsUserRightsMst.setUserCode(new Integer(userDetailsList.get(i).split("_")[1]).intValue());
					dtoWsUserRightsMst.setCanReadFlag('Y');
					dtoWsUserRightsMst.setCanEditFlag('Y');
					dtoWsUserRightsMst.setCanAddFlag('Y');
					dtoWsUserRightsMst.setCanDeleteFlag('Y');
					dtoWsUserRightsMst.setAdvancedRights("Y");
					dtoWsUserRightsMst.setStageId(0);
					dtoWsUserRightsMst.setRemark("");
					dtoWsUserRightsMst.setModifyBy(userId);
					dtoWsUserRightsMst.setNodeId(newNodeId);
					wsUserRightsList.add(dtoWsUserRightsMst);
				}
			}		
			docMgmtImpl.insertUpdateMultipleUserRights(wsUserRightsList, 1);		
			docMgmtImpl.insertUpdateUsertoWorkspace(wsUsersList);
		/**
		 * Manage Users On Category [end]
		 **/
		htmlContent = "Category Created Successfully";
		
	}
	
	private void editCategory()
	{
		int newNodeId = docMgmtImpl.getmaxNodeId(workSpaceId) +1;
		int userId =Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int newTranNo=docMgmtImpl.getNewTranNo(workSpaceId);
		
		/*
		  if docType = KeepCurrent then
		  { 
		  	do nothing
		  }
		  if docType = NewDoc then
		  {
		  	remove all reference.
		  	insert into workspacenodehistory with new file name
		  	insert into workspacenodeversionhistory
		  }
		  if docType = NewRef then
		  {
		  	remove all reference
		  	insert new reference
		  	insert into workspacenodehistory with "NO FILE"
		  	insert into workspacenodeversionhistory
		  }
		  if docType = RemoveAll then
		  {
		  	remove all reference
		  	insert into workspacenodehistory with "NO FILE"
		  	insert into workspacenodeversionhistory
		  }
		*/
		
		if (docType != null && docType.trim().equalsIgnoreCase("NewDoc")) 
		{
			removeAllReference(workSpaceId, nodeId);
			insertNodeHistory(newTranNo,userId,"NewFile");
			insertNodeVersionHistory(newTranNo,userId);
			
		}
		
		if (docType != null && docType.trim().equalsIgnoreCase("NewRef")) 
		{
			removeAllReference(workSpaceId, nodeId);
			HttpServletRequest request = ServletActionContext.getRequest();
			Enumeration<String> enum1 = request.getParameterNames();
			int refNdId = 0;
			for (String paramName =""; enum1.hasMoreElements(); ) 
			{
				paramName = enum1.nextElement();
				if(paramName.startsWith("CHK_"))
				{
					// selected nodeid from selected tree.........
					refNdId = Integer.parseInt(paramName.split("_")[1]);
				}
			}
			DTOWorkspaceNodeReferenceDetail dtoWorkspaceNodeRefDtl = new DTOWorkspaceNodeReferenceDetail();
			dtoWorkspaceNodeRefDtl.setRefNo(0);
			dtoWorkspaceNodeRefDtl.setWorkspaceId(workSpaceId);
			dtoWorkspaceNodeRefDtl.setNodeId(nodeId);
			dtoWorkspaceNodeRefDtl.setRefWorkspaceId(workspaceDtl);
			dtoWorkspaceNodeRefDtl.setRefNodeId(refNdId);
			dtoWorkspaceNodeRefDtl.setModifyBy(userId);
			dtoWorkspaceNodeRefDtl.setStatusIndi('N');
			ArrayList<DTOWorkspaceNodeReferenceDetail> workspaceNodeReferenceList = new ArrayList<DTOWorkspaceNodeReferenceDetail>();
			workspaceNodeReferenceList.add(dtoWorkspaceNodeRefDtl);
			docMgmtImpl.insertWorkspaceNodeReferenceDetail(workspaceNodeReferenceList,1);
			
			
			insertNodeHistory(newTranNo,userId,"NoFile");
			insertNodeVersionHistory(newTranNo,userId);
		}

		if (docType != null && docType.trim().equalsIgnoreCase("RemoveAll")) 
		{
			removeAllReference(workSpaceId, nodeId);
			insertNodeHistory(newTranNo,userId,"NoFile");
			insertNodeVersionHistory(newTranNo,userId);
		}
		
		
		//if(nodeId==0 )
			//fileUploading="New";
		if(refDocUpload == null)
			refDocUploadContentType=refDocUploadFileName="";
		
		/**
		 * Insert node details [start]
		 * */
			int maxNodeNo ;boolean addMode = false;
			String defaultFileFormat="";
			
			int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
			ArrayList<DTOWorkSpaceNodeDetail> wsNodeDtlList = new ArrayList<DTOWorkSpaceNodeDetail>();
			
			addMode = false;
			
			DTOWorkSpaceNodeDetail dtoWsNodeDtl = new DTOWorkSpaceNodeDetail();
			dtoWsNodeDtl.setWorkspaceId(workSpaceId);
			Vector<DTOWorkSpaceNodeDetail> wsNodeDtl = docMgmtImpl.getNodeDetail(workSpaceId, nodeId);
			for (int i = 0; i < wsNodeDtl.size(); i++) 
			{
				dtoWsNodeDtl.setNodeId(wsNodeDtl.get(i).getNodeId());
				dtoWsNodeDtl.setNodeNo(wsNodeDtl.get(i).getNodeNo());
				dtoWsNodeDtl.setParentNodeId(wsNodeDtl.get(i).getParentNodeId());
			}
			dtoWsNodeDtl.setNodeName(nodeName);
			dtoWsNodeDtl.setNodeDisplayName(nodeName);
			dtoWsNodeDtl.setNodeTypeIndi('N');
			dtoWsNodeDtl.setFolderName(folderName);
			dtoWsNodeDtl.setCloneFlag('N');
			dtoWsNodeDtl.setRequiredFlag('N');
			dtoWsNodeDtl.setCheckOutBy(0);
			dtoWsNodeDtl.setPublishedFlag('Y');
			dtoWsNodeDtl.setRemark(remark);
			dtoWsNodeDtl.setModifyBy(userId);
			dtoWsNodeDtl.setDefaultFileFormat(defaultFileFormat);
			wsNodeDtlList.add(dtoWsNodeDtl);
			docMgmtImpl.insertWorkspaceNodeDetail(wsNodeDtlList, 2);
		/**
		 * Insert node details [end]
		 * */
			
		/**
		 * Check Attribute and insert [start]
		 * */
			ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrList = new ArrayList<DTOWorkSpaceNodeAttrDetail>();
			if (selAttributes != null ) 
			{
				Vector<DTOWorkSpaceNodeAttrDetail>  WorkSpaceNodeAttrDetail=docMgmtImpl.getNodeAttrDetail(workSpaceId,nodeId);
				if( selAttributes.trim().length() > 0)
				{
					if(selAttributes.endsWith(","))
					{
						selAttributes.substring(0, selAttributes.length()-1);
					}		
					String [] attrList = selAttributes.split(",");
					for (int i = 0; i < attrList.length; i++) 
					{
						DTOWorkSpaceNodeAttrDetail dtoWsNodeAttrDtl = new DTOWorkSpaceNodeAttrDetail();
						dtoWsNodeAttrDtl.setWorkspaceId(workSpaceId);
						int a = Integer.parseInt(attrList[i].trim());
						dtoWsNodeAttrDtl.setAttrId(a);
						DTOAttributeMst dtoAttrMst = docMgmtImpl.getAttribute(Integer.parseInt(attrList[i].trim()));
						dtoWsNodeAttrDtl.setAttrName(dtoAttrMst.getAttrName());
						dtoWsNodeAttrDtl.setNodeId(nodeId);
						dtoWsNodeAttrDtl.setAttrForIndi(dtoAttrMst.getAttrForIndiId());
						if(dtoAttrMst.getAttrValue() == "")
							dtoWsNodeAttrDtl.setAttrValue("");
						else
							dtoWsNodeAttrDtl.setAttrValue(dtoAttrMst.getAttrValue());
						dtoWsNodeAttrDtl.setModifyBy(userId);
						nodeAttrList.add(dtoWsNodeAttrDtl);
					}
					//if edit mode then first delete all attribute and then insert
						
					ArrayList<DTOWorkSpaceNodeAttrDetail> WorkSpaceNodeAttrDetailList=new ArrayList<DTOWorkSpaceNodeAttrDetail>();
					WorkSpaceNodeAttrDetailList=convertToVector(WorkSpaceNodeAttrDetail);						
					docMgmtImpl.deleteWorkspaceNodeAttrDetail(WorkSpaceNodeAttrDetailList);																			
					docMgmtImpl.insertWorkspaceNodeAttrDetail(nodeAttrList);
				}
				else
				{
					ArrayList<DTOWorkSpaceNodeAttrDetail> WorkSpaceNodeAttrDetailList=new ArrayList<DTOWorkSpaceNodeAttrDetail>();
					WorkSpaceNodeAttrDetailList=convertToVector(WorkSpaceNodeAttrDetail);						
					docMgmtImpl.deleteWorkspaceNodeAttrDetail(WorkSpaceNodeAttrDetailList);																			
				}
				Vector<DTOWorkSpaceNodeAttrHistory> wsNodeAttrHistory = new Vector<DTOWorkSpaceNodeAttrHistory>();
				for (int i = 0; i < nodeAttrList.size(); i++) 
				{
					DTOWorkSpaceNodeAttrHistory dtoWsNodeAttrHistory = new DTOWorkSpaceNodeAttrHistory();
					dtoWsNodeAttrHistory.setWorkSpaceId(workSpaceId);
					dtoWsNodeAttrHistory.setNodeId(nodeId);
					dtoWsNodeAttrHistory.setTranNo(newTranNo);
					dtoWsNodeAttrHistory.setAttrId(nodeAttrList.get(i).getAttrId());
					dtoWsNodeAttrHistory.setAttrValue(nodeAttrList.get(i).getAttrValue());
					dtoWsNodeAttrHistory.setRemark("");
					dtoWsNodeAttrHistory.setModifyBy(userId);
					dtoWsNodeAttrHistory.setStatusIndi('N');
					wsNodeAttrHistory.add(dtoWsNodeAttrHistory);
				}
				docMgmtImpl.insertNodeAttrHistory(wsNodeAttrHistory);
			}
			
		/**
		 * Check Attribute and insert [end]
		 * */
		
		/**
		 * Manage Users On Category [start]
		 * */
			ArrayList<DTOWorkSpaceUserMst> wsUsersList = new ArrayList<DTOWorkSpaceUserMst>();
			ArrayList<DTOWorkSpaceUserRightsMst> wsUserRightsList = new ArrayList<DTOWorkSpaceUserRightsMst>();
			//First delete current users from category
			if(userDetails.endsWith(","))
			{
				userDetails+=userGroupCode+"_"+userId;
			}
			else
				userDetails+=","+userGroupCode+"_"+userId;
			String [] usrsList = userDetails.split(",");
			ArrayList<String> userDetailsList = new ArrayList<String>();
			for (int i = 0; i < usrsList.length; i++) 
			{
				if (!usrsList[i].equals("")) 
					userDetailsList.add(usrsList[i]);
			}
			if (userCodeList != null && userCodeList.trim().length() > 0) 
			{
				String[] sUserCodeList= userCodeList.split(",");	
				ArrayList<Integer> finalUserList = new ArrayList<Integer>(); 
				Vector<DTOUserMst> userMstList = docMgmtImpl.getAllUserDetail();
				for (int itrUserMst = 0; itrUserMst < userMstList.size(); itrUserMst++) 
				{
					DTOUserMst dto = userMstList.get(itrUserMst);
					if(!dto.getUserTypeCode().equalsIgnoreCase(usrTyp))
					{
						userMstList.remove(itrUserMst);
						itrUserMst--;
					}
				}
				
				for (int itrusercodelist = 0; itrusercodelist < userMstList.size(); itrusercodelist++) 
				{
					DTOUserMst dto = userMstList.get(itrusercodelist);
					for (int i = 0; i < sUserCodeList.length; i++) 
					{
						if (sUserCodeList[i].trim().length() > 0 && dto.getUserCode() == Integer.parseInt(sUserCodeList[i].trim())) 
						{
							finalUserList.add(Integer.parseInt(sUserCodeList[i].trim()));
						}
					}
				}
				
				/*for (int i = 0; i < userDetailsList.size(); i++) 
					System.out.println(userDetailsList.get(i));
				System.out.println("selecteduser --- final user-- for delete");
				for (int i = 0; i < finalUserList.size(); i++) 
					System.out.println(finalUserList.get(i));
				System.out.println(userDetailsList.size()+"====filter==="+finalUserList.size());
				*/
				for (int i = 0; i < finalUserList.size(); i++) 
				{
					int finaluser = finalUserList.get(i);
					if (finaluser > 0) 
					{
						for (int j = 0; j < userDetailsList.size(); j++) 
						{
							int user =Integer.parseInt(userDetailsList.get(j).split("_")[1]);
							if (user == finaluser) 
							{
								finalUserList.remove(i);
								i--;
								userDetailsList.remove(j);
								j--;
								break;
							}
						}
					}
				}
				
				for (int i = 0; i < finalUserList.size(); i++) 
				{	
					  	DTOWorkSpaceUserRightsMst dtoWsUserRightsMst = new DTOWorkSpaceUserRightsMst();
				        dtoWsUserRightsMst.setWorkSpaceId(workSpaceId);
				        dtoWsUserRightsMst.setUserGroupCode(userGroupCode);			        
						dtoWsUserRightsMst.setUserCode(finalUserList.get(i));
						dtoWsUserRightsMst.setStageId(0);							
						dtoWsUserRightsMst.setNodeId(nodeId);					
						dtoWsUserRightsMst.setCanReadFlag('Y');
						dtoWsUserRightsMst.setCanAddFlag('Y');
						dtoWsUserRightsMst.setCanEditFlag('Y');
						dtoWsUserRightsMst.setCanDeleteFlag('Y');
						dtoWsUserRightsMst.setAdvancedRights(null);
						dtoWsUserRightsMst.setRemark("");
						dtoWsUserRightsMst.setModifyBy(userId);					
						wsUserRightsList.add(dtoWsUserRightsMst);							
				}
				docMgmtImpl.insertUpdateMultipleUserRights(wsUserRightsList, 3);
			}	
			
			wsUserRightsList=new ArrayList<DTOWorkSpaceUserRightsMst>();
			for (int i = 0; i < userDetailsList.size(); i++) 
			{
				//if (usrsList[i].trim().length()>0) 
				{
					DTOWorkSpaceUserMst dtoWsUserMst = new DTOWorkSpaceUserMst();
					dtoWsUserMst.setWorkSpaceId(workSpaceId);
					dtoWsUserMst.setUserGroupCode(new Integer(userDetailsList.get(i).split("_")[0]).intValue());
					dtoWsUserMst.setUserCode(new Integer(userDetailsList.get(i).split("_")[1]).intValue());
					dtoWsUserMst.setAdminFlag('N');
					dtoWsUserMst.setRemark("");
					dtoWsUserMst.setModifyBy(userId);
					Calendar c=new GregorianCalendar();		
					int date=c.get(Calendar.DATE);
					String month="0" + (c.get(Calendar.MONTH)+1);
					month=month.substring(month.length()-2);
					int year=c.get(Calendar.YEAR);
					frmDate=year + "/" + month + "/" + date;
					//hardcode of 50 years
					year+=50;		
					toDate=year + "/" + month + "/" + date;
					SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
					 
			        try {
			            Date frmDt =df.parse(frmDate);            
			            Date toDt = df.parse(toDate);
			            toDt.setSeconds(59);
			            toDt.setMinutes(59);
			            toDt.setHours(23);
			            dtoWsUserMst.setFromDt(frmDt);
			            dtoWsUserMst.setToDt(toDt);
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			        
			        wsUsersList.add(dtoWsUserMst);
			        
			        DTOWorkSpaceUserRightsMst dtoWsUserRightsMst = new DTOWorkSpaceUserRightsMst();
			        dtoWsUserRightsMst.setWorkSpaceId(workSpaceId);
					dtoWsUserRightsMst.setUserGroupCode(new Integer(userDetailsList.get(i).split("_")[0]).intValue());
					dtoWsUserRightsMst.setUserCode(new Integer(userDetailsList.get(i).split("_")[1]).intValue());
					dtoWsUserRightsMst.setCanReadFlag('Y');
					dtoWsUserRightsMst.setCanEditFlag('Y');
					dtoWsUserRightsMst.setCanAddFlag('Y');
					dtoWsUserRightsMst.setCanDeleteFlag('Y');
					dtoWsUserRightsMst.setAdvancedRights("Y");
					dtoWsUserRightsMst.setStageId(0);
					dtoWsUserRightsMst.setRemark("");
					dtoWsUserRightsMst.setModifyBy(userId);
					dtoWsUserRightsMst.setNodeId(nodeId);
					wsUserRightsList.add(dtoWsUserRightsMst);
				}
			}		
			docMgmtImpl.insertUpdateMultipleUserRights(wsUserRightsList, 1);		
			docMgmtImpl.insertUpdateUsertoWorkspace(wsUsersList);
		/**
		 * Manage Users On Category [end]
		 * */
	}
	
	private <T> ArrayList<T> convertToVector(Vector<T> l) {
		ArrayList<T> al = new ArrayList<T>();
		for (Iterator<T> iterator = l.iterator(); iterator.hasNext();) {
			T t = iterator.next();
			al.add(t);
		}
		return al;
	}
	
	private void removeAllReference(String wsId, int ndId)
	{
		ArrayList<DTOWorkspaceNodeReferenceDetail> wsNodeRefDtlList = docMgmtImpl.getWorkspaceNodeRefereceDtl(wsId, ndId, true);
		for (int i = 0; i < wsNodeRefDtlList.size(); i++) 
		{
			wsNodeRefDtlList.get(i).setStatusIndi('D');
		}
		docMgmtImpl.insertWorkspaceNodeReferenceDetail(wsNodeRefDtlList,2);
		
	}

	private void insertNodeHistory(int newTranNo,int userId,String type)
	{
		/**
		 * Insert node history [start]
		 * */
				DTOWorkSpaceNodeHistory newNodeHistory=new DTOWorkSpaceNodeHistory();
				newNodeHistory.setWorkSpaceId(workSpaceId);
				newNodeHistory.setNodeId(nodeId);
				
				newNodeHistory.setTranNo(newTranNo);
				if (type != null && type.trim().equalsIgnoreCase("nofile")) 
				{
					newNodeHistory.setFileName("No File");
					newNodeHistory.setFileType("");
				}
				else
				{
					newNodeHistory.setFileName(refDocUploadFileName);
					String fileExt = "";
					if (refDocUploadFileName.contains(".")) {
						fileExt =refDocUploadFileName.substring(refDocUploadFileName.indexOf(".")+1);
					}
					newNodeHistory.setFileType(fileExt);
				}
				newNodeHistory.setRequiredFlag('Y');
				newNodeHistory.setRemark("");
				newNodeHistory.setModifyBy(userId);
				newNodeHistory.setStatusIndi('N');
				newNodeHistory.setDefaultFileFormat("");
				newNodeHistory.setFolderName("/"+workSpaceId+"/"+newNodeHistory.getNodeId()+"/"+newTranNo);
				newNodeHistory.setStageId(0);
				docMgmtImpl.insertNodeHistory(newNodeHistory);
				if(refDocUpload != null)
				{
					//Logic For File Upload
					DTOWorkSpaceMst dtoWsMst = docMgmtImpl.getWorkSpaceDetail(workSpaceId);
					String baseImpDir = dtoWsMst.getBaseWorkFolder()+"/"+workSpaceId+"/"+newNodeHistory.getNodeId()+"/"+newTranNo+"/";;
					File importFile = new File(baseImpDir,refDocUploadFileName);
					FileManager fileManager = new FileManager();
					fileManager.copyDirectory(refDocUpload,importFile);
				}
				//File Upload End
		/**
		 * Insert node history [end]
		 **/
	}
	
	private void insertNodeVersionHistory(int newTranNo,int userId)
	{
		/**
		 * Insert node Version history[end]
		 * */
				DTOWorkSpaceNodeVersionHistory dtoWsNodeVersionHistory = new DTOWorkSpaceNodeVersionHistory();
				dtoWsNodeVersionHistory.setWorkspaceId(workSpaceId);
				dtoWsNodeVersionHistory.setTranNo(newTranNo);	
				dtoWsNodeVersionHistory.setPublished('N');
				dtoWsNodeVersionHistory.setDownloaded('N');
				dtoWsNodeVersionHistory.setModifyBy(userId);
				dtoWsNodeVersionHistory.setExecutedBy(userId);
				String version="";
				dtoWsNodeVersionHistory.setNodeId(nodeId);
				DTOWorkSpaceNodeVersionHistory dtoWsNdVerHis = new DTOWorkSpaceNodeVersionHistory();
				dtoWsNdVerHis.setWorkspaceId(workSpaceId);
				dtoWsNdVerHis.setNodeId(nodeId);
				Vector<DTOWorkSpaceNodeVersionHistory> wsNdVerHistoryList = docMgmtImpl.getWorkSpaceNodeVersionDetail(dtoWsNdVerHis);
				DTOWorkSpaceNodeVersionHistory dtoWsHistory = new DTOWorkSpaceNodeVersionHistory();
				if (wsNdVerHistoryList.size() > 0) {
				dtoWsHistory = wsNdVerHistoryList.get(wsNdVerHistoryList.size()-1);
				}
				version = dtoWsHistory.getUserDefineVersionId();
				float t=1.0f;
				if(version != null)
				{
					t  = Float.parseFloat(version);
					t = t+0.001f;
				}
				version= String.format("%5.3f", t);
				dtoWsNodeVersionHistory.setUserDefineVersionId(version); 
				docMgmtImpl.insertWorkspaceNodeVersionHistory(dtoWsNodeVersionHistory,false);
		
		/**
		 * Insert node Version history[end]
		 * */
		
	}

}