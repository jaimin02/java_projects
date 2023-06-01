package com.docmgmt.struts.actions.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.server.webinterface.beans.nIndex;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class DocSummaryTableAction extends ActionSupport
{

	private static final long serialVersionUID = 1L;
	DocMgmtImpl docMgmt = new DocMgmtImpl();
	@Override
	public String execute()
	{
		int uCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int uGCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		userTypeName = ActionContext.getContext().getSession().get("usertypename").toString();
		nIndex nIndex=new nIndex();
		
		
		
		 SimpleDateFormat df=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss"); 
		 Date cDate=null;
		 
		
		 if(opvalue.equals("D")) 
		{
			 boolean after=false;
			   if(modifyAfBf.equals("1")) 
			   { 
			      				if(!checkDate.equals(""))
			      				{
			      							after = true;
			      							fieldName=" last modify date after " ;   
											try
											{
				      							cDate = df.parse(checkDate); 
	
											}
											catch (Exception e) 
											{
												e.printStackTrace();
											}
											fieldValue = checkDate;
								}
							
				} 
			   else if(modifyAfBf.equals("2")) 
			   { 
			   				if(!checkDate.equals(""))
			   				{
			   						after = false;
			   						fieldName=" last modify date before " ; 
			   					   try
			   					    {
			   					    	cDate = df.parse(checkDate);
			   					    }
			   					    catch (Exception e)
			   					    {
										e.printStackTrace();
									}
												
			   				    fieldValue = checkDate;
							}
							
								
			   					
			   }
			   Vector temp = nIndex.getTreeNodes(workSpaceId,uGCode,uCode);
			   getDtl = new Vector();
			   for(int i = 0; i<temp.size(); i++ )
			   {
				   Object[] obj =(Object [])temp.get(i);
				   Date tempDate=null;
				   
				   if(!obj[6].toString().equalsIgnoreCase("D") && !obj[6].toString().equalsIgnoreCase("-"))
				   {
				   		try {
				   			tempDate = df.parse(obj[6].toString());
				   		} catch (Exception e) {
				   			e.printStackTrace();
				   		}
				   }
				   if(obj[8].toString().equalsIgnoreCase("C") && tempDate!=null)
				   {	   
				   
					   if(after)
					   {
						   if(tempDate.after(cDate) || tempDate.compareTo(cDate)==0)
						   {
							   getDtl.addElement(obj);
						   }
					   }
					   else 
					   {
						   if(tempDate.before(cDate) || tempDate.compareTo(cDate)==0)
						   {
							   getDtl.addElement(obj);
						   }
					   }
				   }
			   }
			   
		}
		else if(opvalue.equals("U"))
		{    
				
			   		userName = nIndex.getUserName(userCode);
			   		fieldName= " last modify by ";
			   		fieldValue = userName;
			   		DTOUserMst selectedUser = null; 
			   		if(userName.equals("All"))
			   		{
			   			userCode = 0;
			   			//fieldValue="All";
				   	}
			   		else{
			   			selectedUser = docMgmt.getUserByCode(userCode);
			   		}
			   		
			   		//int uGrpCode = dto.getUserGroupCode();
			   		Vector temp = nIndex.getTreeNodes(workSpaceId,uGCode,uCode);
			   		getDtl = new Vector();
			   		//System.out.println(temp.size());
					for(int i = 0; i<temp.size(); i++ )
					{
						Object[] obj =(Object [])temp.get(i);
						
						if(obj[8].toString().equalsIgnoreCase("C"))
						{
							if(userName.equals("All"))
							{
								getDtl.addElement(obj);
							}
							else
							{
								if(selectedUser.getUserName().equals(obj[5].toString()))
								{
									getDtl.addElement(obj);
								}
							}
							
						}
						
					}
					
		}
		else if(opvalue.equals("S"))
		{
			
			fieldName= " status ";
			fieldValue = status;
			Vector temp = nIndex.getTreeNodes(workSpaceId,uGCode,uCode);
			getDtl = new Vector();
			System.out.println(temp.size());
			for(int i = 0; i<temp.size(); i++ )
			{
				Object[] obj =(Object [])temp.get(i);
				
				if(obj[8].toString().equalsIgnoreCase("C"))
				{
					if(fieldValue.equals("All"))
					{
						
						getDtl.addElement(obj);
					}
					else
					{
						if(fieldValue.equals(obj[9].toString()))
						{
							getDtl.addElement(obj);
						}
					}
					
				}
				
			}		
		}
		 
	
			//System.out.println("ucode,ugcode,workspaceid:"+uCode+","+uGCode+","+workSpaceId);
			
		//	System.out.println("size:"+getDtl.size());
			
	
		
		
		return SUCCESS;
	}
	public String DownloadZip()
	{
		System.out.println("**********DOWNLOAD ZIP*************");
		int usercode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		System.out.println("workSpaceId:"+workSpaceId+"nodeIds:"+nodeIds);
		List<String> nodeID = new ArrayList<String>();
		String nodeArray[]=nodeIds.split(",");
		for(int k=0;k<nodeArray.length;k++)
		{
			nodeID.add(nodeArray[k].split(",")[0]);	
		}
		for(int k=0;k<nodeID.size();k++)
		{
			int MaxtranNo = docMgmt.getMaxTranNo(workSpaceId,Integer.valueOf(nodeID.get(k)));
			nodeID.set(k,nodeID.get(k)+"&"+MaxtranNo);
		}
		System.out.println(nodeID);
		List<String> baseWorkFolders = new ArrayList<String>();
		List<String> fileNames = new ArrayList<String>();
		for(int k=0;k<nodeID.size();k++)
		{
			int nodeId = Integer.valueOf(nodeID.get(k).split("&")[0]);
			int transNo = Integer.valueOf(nodeID.get(k).split("&")[1]);
			String baseWorkFolder_FileName = docMgmt.getBaseWorkFolder(workSpaceId,nodeId,transNo);
			baseWorkFolders.add(baseWorkFolder_FileName.split("&")[0]);
			fileNames.add(baseWorkFolder_FileName.split("&")[1]);
		}
		System.out.println("baseWorkFolder:"+baseWorkFolders);
		System.out.println("fileNames:"+fileNames);
		KnetProperties knetProperties = KnetProperties.getPropInfo();
		String baseWorkFolderPath = knetProperties.getValue("BaseWorkFolder");
		System.out.println("baseWorkFolderPath"+baseWorkFolderPath);
		String docSummaryPath=baseWorkFolderPath.split("workspace")[0]+"DocSummary";
		/*File file = new File(docSummaryPath);
		if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
		}*/
		ArrayList<String> filesPath = new ArrayList<String>();
		for(int h=0;h<baseWorkFolders.size();h++)
		{
			String filePath = baseWorkFolderPath+baseWorkFolders.get(h).split("&")[0]+"/"+fileNames.get(h);
			filesPath.add(filePath);
		}
		/*for(int h=0;h<baseWorkFolders.size();h++)
		{
			System.out.println("&&&&&&&&&&");
			String filePath = baseWorkFolderPath+baseWorkFolders.get(h).split("&")[0]+"/"+fileNames.get(h);
			String pathForCopy = docSummaryPath+"/"+nodeID.get(h).split("&")[0]+fileNames.get(h);
			System.out.println("filePath:"+filePath);
			System.out.println("pathForCopy:"+pathForCopy);
			try{
				File sourcePath=new File(filePath);
				File desPath=new File(pathForCopy);
				Path sPath=sourcePath.toPath();
				Path dPath=desPath.toPath();
				Path path = Files.copy(sPath, dPath, StandardCopyOption.REPLACE_EXISTING);
				}
				catch(Exception e){
					System.out.println(e);
				}
		}*/
		 //knetProperties = KnetProperties.getPropInfo();
		 zipFile = knetProperties.getValue("DocSummaryZipPath");
       
		 try {             
	            // create byte buffer
	            byte[] buffer = new byte[1024];	 
	            FileOutputStream fos = new FileOutputStream(zipFile);	 
	            ZipOutputStream zos = new ZipOutputStream(fos);	             
	            for (int i=0; i < filesPath.size(); i++) {	                 
	                File srcFile = new File(filesPath.get(i));	 
	                FileInputStream fis = new FileInputStream(srcFile);	 
	                // begin writing a new ZIP entry, positions the stream to the start of the entry data
	                zos.putNextEntry(new ZipEntry(nodeID.get(i).split("&")[0]+"-"+srcFile.getName()));
	                //zos.putNextEntry(new ZipEntry(srcFile.getName()));
	                int length;	 
	                while ((length = fis.read(buffer)) > 0) {
	                    zos.write(buffer, 0, length);
	                }	 
	                zos.closeEntry();	 	               
	                fis.close();
	                 
	            }	 	           
	            zos.close();
	             
	        }
	        catch (IOException ioe) {
	            System.out.println("Error creating zip file: " + ioe);
	        }
        htmlContent=zipFile;
		return "html";
	}
	private String fieldName; 
	private String fieldValue;
	private String userName;
	private String checkDate;
	public String workSpaceId;
	private String modifyAfBf;
	private String opvalue;
	private String status;
	private String workspaceDesc;
	private int userCode;
	private Vector getDtl;
	private Object dash = "-";
	private Object Approved = "Approved";
	public String nodeIds;
	public String zipFile = "";
	public String htmlContent;
	public String userTypeName;
	public Object getDash() {
		return dash;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getFieldValue() {
		return fieldValue;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public Vector getGetDtl() {
		return getDtl;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	
	public void setWorkSpaceId(String workspaceId) {
		this.workSpaceId = workspaceId;
	}
	public void setModifyAfBf(String modifyAfBf) 
	{
		this.modifyAfBf = modifyAfBf;
	}
	
	public void setOpvalue(String opvalue) {
		this.opvalue = opvalue;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWorkspaceDesc() {
		return workspaceDesc;
	}
	public void setWorkspaceDesc(String workspaceDesc) {
		this.workspaceDesc = workspaceDesc;
	}
	
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public Object getApproved() {
		return Approved;
	}	
	
	
}
