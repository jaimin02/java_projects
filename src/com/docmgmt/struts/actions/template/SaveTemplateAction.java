package com.docmgmt.struts.actions.template;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOTemplateNodeAttrDetail;
import com.docmgmt.dto.DTOTemplateNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.services.ZipManager;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveTemplateAction extends ActionSupport {//implements Preparable{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public int userCode;
	public String templateName;
	public String templateCode;
	public String statusIndi;
	public File uploadFile;
	public String uploadFileFileName;
	
	private String templateId = "";
	private int nodeId = 0;
		
	HashMap<String, DTOTemplateNodeDetail> templateNodeDetailMap=new HashMap<String,DTOTemplateNodeDetail>();	
	HashMap<Integer,Integer> columnHistory=new HashMap<Integer,Integer>(); 
	ArrayList<String> keyList=new ArrayList<String>();
	
	@Override
	public String execute()
	{
		int fileType=0;
		boolean insertStatus = false;
		String templateDesc = templateName;		
		userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());	
		templateId = docMgmtImpl.insertTemplateDtl(templateDesc, "vRemark", userCode);
		String s = ".zip";
		
		if(uploadFile!= null)
		{
			if(uploadFileFileName.contains(s))
			{
				fileType=1;
			}
			if(uploadFileFileName.contains(".xls"))
			{
				fileType=2;//For xls file
			}
		}
		
		if(templateId!=null)
		{ 
			if (fileType==0) 
			{
				singleNodeEntry(templateId);
			}
			else if(fileType==1)
			{
				templateDetailEntry(templateId);
			}
			else
			{	templateDetailEntry(templateId);					
				readExcelFile("/template/"+templateId+"/"+uploadFileFileName);
			}			
		}
		else
		{
			addActionError("TemplateName already Exist");
			return INPUT;
		}
		
		if(templateId == null)
		{
			insertStatus = false;
		}
		else
		{
			insertStatus=true;
		}
		return SUCCESS;
	}
			
	private void singleNodeEntry(String templateId)
	{
		//insert data in templatenodedetail
		DTOTemplateNodeDetail obj = new DTOTemplateNodeDetail();
		obj.setTemplateId(templateId);
		obj.setNodeId(1);
		obj.setNodeNo(1);
		obj.setNodeName("node-1");
		obj.setNodeDisplayName("node-1");
		obj.setFolderName("node-1");
		obj.setParentNodeId(0);
		obj.setRequiredFlag('N');
		obj.setPublishFlag('N');
		obj.setRemark("This is first node");
		obj.setModifyBy(userCode);
		
		docMgmtImpl.insertTemplateNodeDetail(obj);
		
		Vector<DTOAttributeMst> nodeattribute = docMgmtImpl.getAllAttributeForAttrDtl();
		for(int i=0;i<nodeattribute.size();i++){
			DTOAttributeMst attrMstDTO = nodeattribute.get(i);
			DTOTemplateNodeAttrDetail dto = new DTOTemplateNodeAttrDetail();
			dto.setTemplateId(templateId);
			dto.setNodeId(1);
			dto.setAttrId(attrMstDTO.getAttrId());
			dto.setAttrName(attrMstDTO.getAttrName());
			dto.setAttrValue(attrMstDTO.getAttrValue());
			dto.setAttrForIndiId(attrMstDTO.getAttrForIndiId());
			dto.setModifyBy(userCode);
			docMgmtImpl.insertIntoTemplateNodeAttrDtl(dto,1);
		}
	}
	
	private void templateDetailEntry(String templateId)
	{
		//PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		if(templateId!= null)
		{
			File destPath = new File("/template/"+templateId+"/");
			File zipFile = new File(destPath,uploadFileFileName);
			if(zipFile != null)
			{
				FileManager filemanager = new FileManager(); 
				filemanager.copyDirectory(uploadFile,zipFile);
			}
			if(!uploadFileFileName.contains(".xls")){
				File projectDir = new File(destPath,"unZipped");			
				boolean isUnzipped = ZipManager.unZip(zipFile, projectDir.getPath());
				if(isUnzipped)
				{
					//If unZipped properly
					createTemplateNodes(projectDir, 0);
				}
			}
		}
	}
	
	private void createTemplateNodes(File parentDir,int parentNodeId)
	{
		File[] children = parentDir.listFiles();
		for (int itr = 0; itr < children.length; itr++) 
		{
			DTOTemplateNodeDetail dto = new DTOTemplateNodeDetail();
			dto.setTemplateId(templateId);
			nodeId++;
			dto.setNodeId(nodeId);
			dto.setNodeNo(itr+1);
			dto.setNodeName(children[itr].getName());
			dto.setNodeDisplayName(children[itr].getName());
			dto.setFolderName(children[itr].getName());
			dto.setParentNodeId(parentNodeId);
			dto.setRequiredFlag('N');
			dto.setPublishFlag('N');
			dto.setRemark("");
			dto.setModifyBy(userCode);
			
			docMgmtImpl.insertTemplateNodeDetail(dto);
			if(children[itr].isDirectory())
			{
				createTemplateNodes(children[itr], dto.getNodeId());
			}
		}
	}
	public void readExcelFile(String fileName)
	{
        try{
        /** Creating Input Stream**/
        FileInputStream myInput = new FileInputStream(fileName);
 
        /** Create a POIFSFileSystem object**/
        POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
 
        /** Create a workbook using the File System**/
         HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
 
         /** Get the first sheet from workbook**/
        HSSFSheet mySheet = myWorkBook.getSheetAt(0);
 
        /** We now need something to iterate through the cells.**/       
         Iterator rowIter = mySheet.rowIterator();
         int rowIndex,cellIndex;         
         int NodeId=0;
          while(rowIter.hasNext()){
              HSSFRow myRow = (HSSFRow) rowIter.next();
              rowIndex=myRow.getRowNum();
              Iterator cellIter = myRow.cellIterator();	              
              while(cellIter.hasNext()){            	              	  
                  HSSFCell myCell = (HSSFCell) cellIter.next();
                  cellIndex=myCell.getColumnIndex();
                  NodeId++;                 
                  setColumnHistory(cellIndex,rowIndex);                  
                  setDTOWorkspaceNodeAttrDetail(NodeId,cellIndex,rowIndex,myCell.getStringCellValue());                 
              }
          }                  
          DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
          
          for(int i=0;i<keyList.size();i++){
        	  DTOTemplateNodeDetail dto=templateNodeDetailMap.get(keyList.get(i));
        	  docMgmtImpl.insertTemplateNodeDetail(dto);
        	  //System.out.println("NodeId="+dto.getNodeId()+"	dtoNodeName="+dto.getNodeDisplayName()+"		dtoParentId="+dto.getParentNodeId());        	  
          }
        }catch (Exception e){e.printStackTrace(); }	        
    }
	private void setColumnHistory(int cellIndex,int rowIndex)
	{			
		columnHistory.put(cellIndex,rowIndex);		
	}	
	private void setDTOWorkspaceNodeAttrDetail(int NodeId,int cellIndex,int rowIndex,String cellValue){		
		String rowColumn=rowIndex+"_"+cellIndex;
		DTOTemplateNodeDetail dto=new DTOTemplateNodeDetail();		 
		dto.setTemplateId(templateId);
		dto.setNodeId(NodeId);
		dto.setNodeNo(1);
		dto.setNodeName(cellValue);
		dto.setNodeDisplayName(cellValue);
		dto.setFolderName(cellValue);
		dto.setParentNodeId(getParentNodeIdByColumnIndex(cellIndex));
		dto.setRequiredFlag('N');
		dto.setPublishFlag('N');
		dto.setRemark("");
		dto.setModifyBy(1);
		keyList.add(rowColumn);
		templateNodeDetailMap.put(rowColumn,dto);		
	}
	private int getParentNodeIdByColumnIndex(int columnIndex)
	{
	  int parentNodeId=0;
	  if(templateNodeDetailMap.size()==0)
		  return parentNodeId;	  
	  String key=(columnHistory.get(columnIndex-1))+"_"+(columnIndex-1);	  
	  DTOTemplateNodeDetail dto=templateNodeDetailMap.get(key);
	  parentNodeId=dto.getNodeId();	  	  
	  return parentNodeId;
	}
}