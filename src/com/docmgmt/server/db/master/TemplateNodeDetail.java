package com.docmgmt.server.db.master;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOCopyPasteTemplate;
import com.docmgmt.dto.DTOTemplateNodeAttr;
import com.docmgmt.dto.DTOTemplateNodeAttrDetail;
import com.docmgmt.dto.DTOTemplateNodeDetail;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class TemplateNodeDetail {

ConnectionManager conMgr;
DataTable dataTable;	
AttributeMst attributeMst;

	public TemplateNodeDetail()
	{
		ConnectionManager conMgr=new ConnectionManager(new Configuration());
		dataTable = new DataTable();
		attributeMst = new AttributeMst();
	}
	
public void insertTemplateNodeDetail(DTOTemplateNodeDetail dto)
{
	try 
	{
		Connection con = conMgr.ds.getConnection();
		CallableStatement cs=con.prepareCall("{call Insert_templateNodeDetail(?,?,?,?,?,'N',?,?,?,?,?,?,'N',?)}");
		cs.setString(1,dto.getTemplateId());
		cs.setInt(2, dto.getNodeId());
		cs.setInt(3, dto.getNodeNo());
		cs.setString(4, dto.getNodeName());
		cs.setString(5, dto.getNodeDisplayName());
		cs.setInt(6, dto.getParentNodeId());
		cs.setString(7, dto.getFolderName());
		cs.setString(8, Character.toString(dto.getRequiredFlag()));
		cs.setString(9, Character.toString(dto.getPublishFlag()));
		cs.setString(10, dto.getRemark());
		cs.setInt(11, dto.getModifyBy());
		cs.setString(12, "NULL");
		cs.execute();
		con.close();
	}catch(SQLException e){
		e.printStackTrace();
	}
}

public Vector getTemplateNodeDetailByNodeId(String templateId,int nodeId)
{
	Vector templateNodeDetail = new Vector();
	StringBuffer query = new StringBuffer();
	try
	{
		Connection con = conMgr.ds.getConnection();
		String Where = "vTemplateId = '"+templateId+"' and iNodeid ="+nodeId;
		ResultSet rs = dataTable.getDataSet(con, "*", "templateNodeDetail", "vTemplateId = '"+templateId+"' and iNodeid ="+nodeId, "");
		while(rs.next())
		{
			DTOTemplateNodeDetail dto = new DTOTemplateNodeDetail();
			dto.setTemplateId(rs.getString("vTemplateId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setNodeNo(rs.getInt("iNodeNo"));
			dto.setNodeName(rs.getString("vNodeName"));
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
			dto.setParentNodeId(rs.getInt("iParentNodeId"));
			dto.setFolderName(rs.getString("vFolderName"));
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0));
			dto.setPublishFlag(rs.getString("cPublishFlag").charAt(0));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat"));
			templateNodeDetail.addElement(dto);
			dto = null;
		}
		rs.close();
		con.close();
	    
	}catch(SQLException e){
		e.printStackTrace();
	}	
	return templateNodeDetail;
}
public Vector getTemplateChildNodeByParentForPublishForM1(int parentNodeid,String templateId)
{
	Vector templateNodeDetail = new Vector();
	StringBuffer query = new StringBuffer();
	try
	{
		Connection con = conMgr.ds.getConnection();
		String Where = "vTemplateId = '"+templateId+"' and iParentNodeId ="+parentNodeid;
		ResultSet rs = dataTable.getDataSet(con, "*", "templateNodeDetail", "vTemplateId = '"+templateId+"' and iParentNodeId ="+parentNodeid, "iNodeNo");
		while(rs.next())
		{
			DTOTemplateNodeDetail dto = new DTOTemplateNodeDetail();
			dto.setTemplateId(rs.getString("vTemplateId"));
			dto.setNodeId(rs.getInt("iNodeId"));
			dto.setNodeNo(rs.getInt("iNodeNo"));
			dto.setNodeName(rs.getString("vNodeName"));
			dto.setNodeDisplayName(rs.getString("vNodeDisplayName"));
			dto.setNodeTypeIndi(rs.getString("cNodeTypeIndi").charAt(0));
			dto.setParentNodeId(rs.getInt("iParentNodeId"));
			dto.setFolderName(rs.getString("vFolderName"));
			dto.setRequiredFlag(rs.getString("cRequiredFlag").charAt(0));
			dto.setPublishFlag(rs.getString("cPublishFlag").charAt(0));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			dto.setDefaultFileFormat(rs.getString("vDefaultFileFormat"));
			templateNodeDetail.addElement(dto);
			dto = null;
		}
		rs.close();
		con.close();
	    
	}catch(SQLException e){
		e.printStackTrace();
	}	
	return templateNodeDetail;
}

	  	  
public void updateTemplateDetail(DTOTemplateNodeDetail dto)
{
	try
	{
		Connection con = conMgr.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call proc_updateTemplateNodeDetail(?,?,?,?,?,?,?,?,?,?,?)}");
		proc.setString(1, dto.getTemplateId());
		proc.setInt(2, dto.getNodeId());
		proc.setString(3, dto.getNodeDisplayName());
		proc.setString(4, dto.getNodeName());
		proc.setString(5, dto.getFolderName());
		proc.setInt(6, dto.getModifyBy());
		if (dto.getPublishFlag()=='Y')
			proc.setString(7,"Y");
		else
			proc.setString(7,"N");
		proc.setString(8,dto.getRemark());
		proc.setString(9, Character.toString(dto.getNodeTypeIndi()));
		proc.setInt(10, dto.getParentNodeId());
		proc.setString(11, Character.toString(dto.getRequiredFlag()));		
		proc.execute();
		proc.close();
		con.close();
	  	
	}catch(SQLException e){
	  e.printStackTrace();
	}
	
}
public void addChildNodeForStructure(DTOTemplateNodeDetail dto)  
{
	try
	{
		
		String templateId = dto.getTemplateId();
		int selectedNode = dto.getNodeId();
		int maxNodeId = this.getmaxNodeId(templateId);
		int nodeNo  = 0;
		if(isLeafNodes(templateId,selectedNode)==0) // parent Node
		{
			nodeNo = this.getmaxNodeNo(templateId,selectedNode);
			nodeNo++;
		}	
		else
			nodeNo  = 1;
				
		dto.setNodeId(maxNodeId+1);
		dto.setNodeNo(nodeNo);
		dto.setParentNodeId(selectedNode);
		this.insertTemplateNodeDetail(dto);
		DTOTemplateNodeAttrDetail dtotemplatenodeattribute = new DTOTemplateNodeAttrDetail();
		dtotemplatenodeattribute.setTemplateId(templateId);
		dtotemplatenodeattribute.setParentNodeId(selectedNode);
		dtotemplatenodeattribute.setNodeId(maxNodeId+1);
		
		TemplateNodeAttrDetail wsnad = new TemplateNodeAttrDetail();
		boolean flag = wsnad.insertIntoTemplateNodeAttrDetailForAddChild(dtotemplatenodeattribute);
		wsnad = null;
	}
		
	catch(Exception e){
		e.printStackTrace();
	}
}

public int getmaxNodeId(String vTemplateId)
{
	int maxNodeCount = 0;
	try
	{
		Connection con = conMgr.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con,"max(iNodeId) nodeId", "templatenodedetail","vTemplateId ='"+vTemplateId+"'" ,"" );
		if(rs.next())
		{
			maxNodeCount = rs.getInt("nodeId");
		}else{
			maxNodeCount=0;
		}
		rs.close();
		con.close();

	}catch(SQLException e){
		e.printStackTrace();
	}		
	return maxNodeCount;
}

public int isLeafNodes(String tempId, int nodeId) {

	Vector data = new Vector();
	try
	{
		Connection con = conMgr.ds.getConnection();
		ResultSet rs= dataTable.getDataSet(con,"iNodeId", "templateNodeDetail", "vTemplateId ='"+tempId+"' and iParentNodeId ="+nodeId, "");
		while(rs.next())
		{
			data.addElement(rs.getInt("iNodeId"));
		}
		rs.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	if(data.size()>0)
	{
		return 0;//false
	}
	else
	{
		return 1;//true
	}
}
	  
public void addChildNodeBeforeForStructure(DTOTemplateNodeDetail dto)
{
	try
	{
		
		String templateId = dto.getTemplateId();
		int selectedNode = dto.getNodeId();
		int maxNodeId = this.getmaxNodeId(templateId);
		
		// Get Selected Node's Node No and Parent Node Detail.
		Vector selectedNodeDetail = this.getTemplateNodeDetailByNodeId(templateId,selectedNode); 
		DTOTemplateNodeDetail dtoTemplateNode = (DTOTemplateNodeDetail)selectedNodeDetail.get(0);
		int nodeNo  = dtoTemplateNode.getNodeNo();
		int parentNodeId = dtoTemplateNode.getParentNodeId();
		dto.setNodeId(maxNodeId+1);
		dto.setNodeNo(nodeNo);
		dto.setParentNodeId(parentNodeId);
		
		// Set Node no of selected node and nodes below it.
		updateNodeNo(templateId,parentNodeId,nodeNo,"addbefore");
		
		// Add Record in workspace node detail for new record.
		insertTemplateNodeDetail(dto);
		DTOTemplateNodeAttrDetail dtotemplatenodeattribute = new DTOTemplateNodeAttrDetail();
		dtotemplatenodeattribute.setTemplateId(templateId);
		dtotemplatenodeattribute.setParentNodeId(selectedNode);
		dtotemplatenodeattribute.setNodeId(maxNodeId+1);
		
		// Add Record in workspace node attribute.
		// Add Record in workspace user rights. 
		TemplateNodeAttrDetail wsnad = new TemplateNodeAttrDetail();
		boolean flag = wsnad.insertIntoTemplateNodeAttrDetailForAddChild(dtotemplatenodeattribute);
		wsnad = null;		
	}
	catch(Exception e){
		e.printStackTrace();
	}
}	
			
public void addChildNodeAfterForStructure(DTOTemplateNodeDetail dto)  
{
	try
	{
		
		String templateId = dto.getTemplateId();
		int selectedNode = dto.getNodeId();
		int maxNodeId = this.getmaxNodeId(templateId);
		
		// Get Selected Node's Node No and Parent Node Detail.
		Vector selectedNodeDetail = this.getTemplateNodeDetailByNodeId(templateId,selectedNode); 
		DTOTemplateNodeDetail dtoTemplateNode = (DTOTemplateNodeDetail)selectedNodeDetail.get(0);
		int nodeNo  = dtoTemplateNode.getNodeNo();
		int parentNodeId = dtoTemplateNode.getParentNodeId();
		dto.setNodeId(maxNodeId+1);
		dto.setNodeNo(nodeNo+1);
		dto.setParentNodeId(parentNodeId);
		
		// Set Node no of selected node and nodes below it.
		updateNodeNo(templateId,parentNodeId,nodeNo,"addafter");
		
		// Add Record in workspace node detail for new record.
		insertTemplateNodeDetail(dto);
		DTOTemplateNodeAttrDetail dtotemplatenodeattribute = new DTOTemplateNodeAttrDetail();
		dtotemplatenodeattribute.setTemplateId(templateId);
		dtotemplatenodeattribute.setParentNodeId(selectedNode);
		dtotemplatenodeattribute.setNodeId(maxNodeId+1);
		
		// Add Record in workspace node attribute.
		// Add Record in workspace user rights. 
		TemplateNodeAttrDetail wsnad = new TemplateNodeAttrDetail();
		boolean flag = wsnad.insertIntoTemplateNodeAttrDetailForAddChild(dtotemplatenodeattribute);
		wsnad = null;
	}
	catch(Exception e){
		e.printStackTrace();
	}
}
		 

public void copySourceParentToDestParent(Vector sourceVector,Vector destVector,DTOCopyPasteTemplate dto,TemplateNodeAttrDetail templateNodeAttrDetail)
{
	try
	{
		String 	sourceTemplateId = dto.getSourceTemplateId();
		String  destTempId = dto.getDestTemplateId();
		int destNodeId = dto.getDestNodeId();
		int userCode = dto.getUserCode();			
		Timestamp ts = new Timestamp(dto.getModifyOn().getTime());
		
		// get attribute detail.
		DTOTemplateNodeAttrDetail dtoTemplateNodeAttrDetail = new DTOTemplateNodeAttrDetail();
		dtoTemplateNodeAttrDetail.setTemplateId(destTempId);
		dtoTemplateNodeAttrDetail.setNodeId(destNodeId);
			
		Vector getDestTemplateNodeAttr =   templateNodeAttrDetail.getTemplateNodeAttrDtl(dtoTemplateNodeAttrDetail);
		dtoTemplateNodeAttrDetail = null;
			
		// get attributes
		DTOTemplateNodeAttr dtoTemplateNodeAttr = new DTOTemplateNodeAttr();
		dtoTemplateNodeAttr.setTemplateId(destTempId);
		dtoTemplateNodeAttr.setNodeId(destNodeId);
			
			//--Hardik(remove templatenodeattribute concept)
			//Vector getDestTempNodeAttr = templateNodeAttributeMst.getTemplateNodeAttribute(dtoTemplateNodeAttr);
		Vector getDestTempNodeAttr=new Vector();
		dtoTemplateNodeAttr = null;		
		for(int i = 0; i<sourceVector.size();i++)
		{	
			int sourceNodeId = Integer.parseInt(((String)sourceVector.get(i)).split("#")[0]);
			int newNodeId    = Integer.parseInt(((String)destVector.get(i)).split("#")[0]);
			int newParentId  = Integer.parseInt(((String)destVector.get(i)).split("#")[1]);
			int newNodeNo  = Integer.parseInt(((String)destVector.get(i)).split("#")[2]);
			
			Vector getSourceTemplateDetail = getTemplateNodeDetailByNodeId(sourceTemplateId,sourceNodeId);
			DTOTemplateNodeDetail dtoSourceTemplate = (DTOTemplateNodeDetail)getSourceTemplateDetail.get(0);
			DTOTemplateNodeDetail dtoNewTemplate    = new DTOTemplateNodeDetail();
			
			dtoNewTemplate.setTemplateId(destTempId);
			dtoNewTemplate.setNodeId(newNodeId);
			dtoNewTemplate.setParentNodeId(newParentId);
			dtoNewTemplate.setNodeNo(newNodeNo);
			dtoNewTemplate.setDefaultFileFormat(dtoSourceTemplate.getDefaultFileFormat());
			dtoNewTemplate.setFolderName(dtoSourceTemplate.getFolderName());
			dtoNewTemplate.setModifyBy(userCode);
			dtoNewTemplate.setModifyOn(ts);
			dtoNewTemplate.setNodeDisplayName(dtoSourceTemplate.getNodeDisplayName());
			dtoNewTemplate.setNodeName(dtoSourceTemplate.getNodeName());
			dtoNewTemplate.setNodeTypeIndi('Y');
			dtoNewTemplate.setPublishFlag(dtoSourceTemplate.getPublishFlag());
			dtoNewTemplate.setRequiredFlag(dtoSourceTemplate.getRequiredFlag());
			dtoNewTemplate.setRemark(dtoSourceTemplate.getRemark());
			dtoNewTemplate.setStatusIndi('N');
			insertTemplateNodeDetail(dtoNewTemplate);
			
			//	System.out.println(" Insert into template node detail complete...." + newNodeId + " " + newParentId + " " + newNodeNo);
			dtoSourceTemplate = null;
			dtoNewTemplate = null;
				
			// 	Insert into template node attribute detail
			for(int iAttrCount=0;iAttrCount<getDestTemplateNodeAttr.size();iAttrCount++)
			{
				dtoTemplateNodeAttrDetail = new DTOTemplateNodeAttrDetail();
				dtoTemplateNodeAttrDetail = (DTOTemplateNodeAttrDetail)getDestTemplateNodeAttr.get(iAttrCount);
				dtoTemplateNodeAttrDetail.setModifyBy(userCode);
				dtoTemplateNodeAttrDetail.setModifyOn(ts);
				dtoTemplateNodeAttrDetail.setNodeId(newNodeId);
				dtoTemplateNodeAttrDetail.setTemplateId(destTempId);
				templateNodeAttrDetail.insertIntoTemplateNodeAttrDtl(dtoTemplateNodeAttrDetail,1);
				dtoTemplateNodeAttrDetail = null;
			}	
			// insert into tempalate node attribute
			for(int iAttr=0;iAttr<getDestTempNodeAttr.size();iAttr++)
			{
				DTOTemplateNodeAttrDetail dtoNewTemplateNodeAttr = new DTOTemplateNodeAttrDetail();
			
				dtoTemplateNodeAttr = new DTOTemplateNodeAttr();
				dtoTemplateNodeAttr = (DTOTemplateNodeAttr)getDestTemplateNodeAttr.get(i);
				
				dtoNewTemplateNodeAttr.setAttrId(dtoTemplateNodeAttr.getAttrId());
				dtoNewTemplateNodeAttr.setAttrName(dtoTemplateNodeAttr.getAttrName());
				dtoNewTemplateNodeAttr.setAttrValue(dtoTemplateNodeAttr.getAttrValue());
				dtoNewTemplateNodeAttr.setModifyBy(userCode);
				dtoNewTemplateNodeAttr.setModifyOn(ts);
				dtoNewTemplateNodeAttr.setNodeId(newNodeId);
				dtoNewTemplateNodeAttr.setStatusIndi(dtoTemplateNodeAttr.getStatusIndi());
				dtoNewTemplateNodeAttr.setTemplateId(destTempId);
				DTOAttributeMst dtoattr = attributeMst.getAttribute(dtoTemplateNodeAttr.getAttrId());
				dtoNewTemplateNodeAttr.setAttrForIndiId(dtoattr.getAttrForIndiId());
				templateNodeAttrDetail.insertIntoTemplateNodeAttrDtl(dtoNewTemplateNodeAttr,1);
				//	System.out.println(" Insert into template node attribute complete...." + iAttr +  "..." + newNodeId + " " + newParentId + " " + newNodeNo);
				dtoNewTemplateNodeAttr = null;
				dtoTemplateNodeAttr = null;
			}		
			
		}	
		templateNodeAttrDetail = null;
	}		
	catch(Exception e){
		e.printStackTrace();
	}	
}
			
public void CopyPasteStructure(String sourceTempId,int sourceNodeId,String destTempId,int destNodeId,int userCode,Date modifyOn)
{
	//Get new values
	int newNodeId = getmaxTemplateNodeId(destTempId) + 1 ;
	int newNodeNo = getmaxNodeNo(destTempId,destNodeId) + 1;
	
	//Copy Current Node
	Vector nodeVector = getTemplateNodeDetailByNodeId(sourceTempId, sourceNodeId);
	DTOTemplateNodeDetail sourceNodeToCopy = (DTOTemplateNodeDetail)nodeVector.get(0);
	sourceNodeToCopy.setTemplateId(destTempId);
	sourceNodeToCopy.setNodeId(newNodeId);
	sourceNodeToCopy.setNodeNo(newNodeNo);
	sourceNodeToCopy.setParentNodeId(destNodeId);
	sourceNodeToCopy.setModifyBy(userCode);
	insertTemplateNodeDetail(sourceNodeToCopy);
	
	//Copy Node Attrs
	DTOTemplateNodeAttrDetail dtoTemplateNodeAttrDetail = new DTOTemplateNodeAttrDetail();
	dtoTemplateNodeAttrDetail.setTemplateId(sourceTempId);
	dtoTemplateNodeAttrDetail.setNodeId(sourceNodeId);
	
	TemplateNodeAttrDetail templateNodeAttrDetail = new TemplateNodeAttrDetail(); 
	Vector srcNodeAttrs = templateNodeAttrDetail.getTemplateNodeAttrDtl(dtoTemplateNodeAttrDetail);
	for (Object nodeAttrObj : srcNodeAttrs) {
		DTOTemplateNodeAttrDetail attrDetail = (DTOTemplateNodeAttrDetail)nodeAttrObj;
		attrDetail.setTemplateId(destTempId);
		attrDetail.setNodeId(newNodeId);
	}
	templateNodeAttrDetail.InsertNodeAttributeFromXml(srcNodeAttrs);
	
	//Get Children for copy
	Vector children =  getChildNodes(sourceTempId, sourceNodeId);
	for (Object childObj : children) {
		DTOTemplateNodeDetail currChild = (DTOTemplateNodeDetail)childObj;
		System.out.println(sourceTempId +":::::"+ currChild.getNodeId());
		CopyPasteStructure(sourceTempId, currChild.getNodeId(), destTempId, newNodeId, userCode, modifyOn);
	}
}
			
public Vector getAllChildNodes(String tempId,int nodeId, Vector allChildNodes)
{
       Vector childNodes = new Vector();
       try
       {
           childNodes = getChildNodes(tempId, nodeId);
           Integer[] childNodeId = new Integer[childNodes.size()];
           Integer[] childParentNodeId = new Integer[childNodes.size()];
           Integer[] childNodeNo = new Integer[childNodes.size()];
           for(int i=0; i<childNodes.size(); i++)
           {
               	DTOTemplateNodeDetail record = (DTOTemplateNodeDetail) childNodes.elementAt(i);
               	childNodeId[i]=record.getNodeId();
               	childParentNodeId[i]=record.getParentNodeId();
               	childNodeNo[i]=record.getNodeNo();
               	record = null;
               	int leafFlag = isLeafNodes(tempId,childNodeId[i].intValue());
               	if(leafFlag==0)
               	{
               		allChildNodes.addElement(childNodeId[i]+"#"+childParentNodeId[i]+"#"+childNodeNo[i]);
               		getAllChildNodes(tempId,childNodeId[i].intValue(),allChildNodes);
                }
               	else
               	{
                   	allChildNodes.addElement(childNodeId[i]+"#"+childParentNodeId[i]+"#"+childNodeNo[i]);
                }
           }
           
           return allChildNodes;
       }
       catch (Exception e) {
           e.printStackTrace();
       }
		
       return allChildNodes;
}			
 
 public Vector getChildNodes(String tempId, int nodeId) {
 	
	 Vector leafNodes = new Vector();
     try 
     {
         Connection con = conMgr.ds.getConnection();
         ResultSet rs = dataTable.getDataSet(con," iNodeId,iParentNodeId,iNodeNo", "templateNodeDetail", "vTemplateId = '"+tempId+"' and iParentNodeId = "+nodeId, "iNodeNo");
         while(rs.next())
         {
         	DTOTemplateNodeDetail dto = new DTOTemplateNodeDetail();
         	dto.setNodeId(rs.getInt("iNodeId")); //nodeId
         	dto.setParentNodeId(rs.getInt("iParentNodeId"));//parentNodeId
         	dto.setNodeNo(rs.getInt("iNodeNo"));//nodeNo
         	leafNodes.addElement(dto);
         	dto = null;
         }
         rs.close();
         con.close();
     }           
     catch(SQLException e)  {
         e.printStackTrace();
     }
     return leafNodes; 
}
 
public void updateNodeNo(String templateId,int parentId, int nodeNo,String flag)  
{
	 try
     {
		 Connection con = conMgr.ds.getConnection();
		 CallableStatement cs=con.prepareCall("{call Proc_UpdateNodeNoForTemplate(?,?,?,?) }");
		 cs.setString(1,templateId);
		 cs.setInt(2,nodeNo);
		 cs.setInt(3,parentId);
		 if(flag.equalsIgnoreCase("addafter"))
		 {
			 cs.setInt(4,1 );//set flag 1 for add after	 
		 }else if(flag.equalsIgnoreCase("addbefore"))
		 {
			 cs.setInt(4,2 );//set flag 2 for add after	 
		 }
		 cs.execute(); 	
		 con.close();
             		
     }catch(SQLException e){
 		e.printStackTrace();
 	}

}
	 
public int isEndNode(String templateId, int nodeId)
{
	Vector leafNodes = new Vector();
	try 
	{
		Connection con = conMgr.ds.getConnection();
		ResultSet rs =dataTable.getDataSet(con,"iNodeId", "templatenodedetail", "vTemplateId = '"+templateId+"' and iParentNodeId = "+nodeId, "");
		while(rs.next())
		{
			DTOTemplateNodeDetail dto = new DTOTemplateNodeDetail();
			dto.setNodeId(rs.getInt("iNodeId"));
			leafNodes.addElement(dto);
			dto = null;
		}
		rs.close();
		con.close();
		if(leafNodes.size()>0){
			return 0;   //false
		}
		else{
			return 1;   //true
		}
	    
	}           
	catch(SQLException e)  {
		e.printStackTrace();
	}        
	
	return -1;  //error condition        
}
	 
public int getmaxTemplateNodeId(String templateId) 
{
	int maxNodeCount = 0;
	try
	{
		Connection con = conMgr.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con,"max(iNodeId) nodeId", "templateNodeDetail", "vTemplateId = '"+templateId+"'", "");
		if(rs.next()){
			maxNodeCount = rs.getInt("nodeId");				
		}
		rs.close();
		con.close();
		
	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return maxNodeCount;
}

public int getmaxNodeNo(String vTemplateId, int parentId) 
{
	int maxNodeCount = 0;
	try
	{
		String Where ="vTemplateId = '"+vTemplateId+"' and iParentNodeId = "+parentId;
		Connection con = conMgr.ds.getConnection();
		ResultSet rs= dataTable.getDataSet(con,"max(iNodeNo) iNodeNo", "templatenodedetail", "vTemplateId = '"+vTemplateId+"' and iParentNodeId = "+parentId, "");
		if(rs.next())
		{
			maxNodeCount = rs.getInt("iNodeNo");
		}
		rs.close();
		con.close();
		
	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return maxNodeCount;
}

public void deleteTemplateNode(String templateId,int nodeId)
{
	try
	{
		Connection con = conMgr.ds.getConnection();
		CallableStatement cs=con.prepareCall("{call proc_deleteTemplateNode(?,?)}");
		cs.setString(1,templateId);
		cs.setInt(2,nodeId);
		cs.executeUpdate();
		cs.close();
		con.close();
	}catch(SQLException e){
		e.printStackTrace();
	}
   
}	 
	 
public void InsertTemplateNodeForXml(Vector NodeData)
{
  	
		CallableStatement cs=null;
		try 
			{
				Connection con = conMgr.ds.getConnection();
					
				cs=con.prepareCall("{call Insert_templateNodeDetail(?,?,?,?,?,?,?,?,?,?,?,?,'N',?)}");	
				
				for(int i=0;i<NodeData.size();i++)
				{
					DTOTemplateNodeDetail dto=(DTOTemplateNodeDetail)NodeData.get(i);
					
					System.out.println(dto.getNodeDisplayName());
					System.out.print("--"+dto.getNodeName());
					
					cs.setString(1,dto.getTemplateId());
					cs.setInt(2, dto.getNodeId());
					cs.setInt(3, dto.getNodeNo());
					cs.setString(4, dto.getNodeName());
					cs.setString(5, dto.getNodeDisplayName());
					cs.setString(6, Character.toString(dto.getNodeTypeIndi()));
					cs.setInt(7, dto.getParentNodeId());
					cs.setString(8, dto.getFolderName());
					cs.setString(9, Character.toString(dto.getRequiredFlag()));
					cs.setString(10, Character.toString(dto.getPublishFlag()));
					cs.setString(11, dto.getRemark());
					cs.setInt(12, dto.getModifyBy());
					cs.setString(13, "NULL");
					
					cs.execute();
			
				}
				
				cs.close();
				con.close();
				
			}catch(SQLException e){
	    		e.printStackTrace();
			}
} 

public static DTOTemplateNodeDetail getTemplateNodeDtlTree(String templateId,int nodeId){
	
	try {
		Connection con = ConnectionManager.ds.getConnection();
		
		PreparedStatement[] nodeStmts  = new PreparedStatement[3];
		String nodeDtlSql = "SELECT * FROM TemplateNodeDetail WHERE vTemplateId = '"+templateId+"' and iNodeid = ? ";
		PreparedStatement nodeDtlStmt = con.prepareStatement(nodeDtlSql);
		nodeDtlStmt.setInt(1, nodeId);
		nodeStmts[0]= nodeDtlStmt;
		
		String nodeAttrDtlSql = "SELECT * FROM TemplateNodeAttrDetail WHERE vTemplateId = '"+templateId+"' and iNodeid = ? ";
		PreparedStatement nodeAttrDtlStmt = con.prepareStatement(nodeAttrDtlSql);
		nodeAttrDtlStmt.setInt(1, nodeId);
		nodeStmts[1]= nodeAttrDtlStmt;
		
		String nodeChildrenSql = "SELECT iNodeId FROM TemplateNodeDetail WHERE vTemplateId = '"+templateId+"' and iParentNodeid = ? ";
		PreparedStatement nodeChildrenStmt = con.prepareStatement(nodeChildrenSql);
		nodeChildrenStmt.setInt(1, nodeId);
		nodeStmts[2]= nodeChildrenStmt;
				
		DTOTemplateNodeDetail dtoTemplateNodeDetail = getTemplateNodeDtlTree(nodeStmts);
		nodeStmts[0].close();
		nodeStmts[1].close();
		nodeStmts[2].close();
		con.close();
		return dtoTemplateNodeDetail;
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	
}
//Recursive Function
private static DTOTemplateNodeDetail getTemplateNodeDtlTree(PreparedStatement[] nodeStmts) throws SQLException{
	PreparedStatement nodeDtlStmt=nodeStmts[0];
	PreparedStatement nodeAttrDtlStmt=nodeStmts[1];
	PreparedStatement nodeChildrenStmt=nodeStmts[2];
	
	DTOTemplateNodeDetail templateNodeDtl = new DTOTemplateNodeDetail();
	ResultSet templateNodeRS = nodeDtlStmt.executeQuery();
	
	if(templateNodeRS.next())
	{
		templateNodeDtl.setTemplateId(templateNodeRS.getString("vTemplateId"));
		templateNodeDtl.setNodeId(templateNodeRS.getInt("iNodeId"));
		templateNodeDtl.setNodeNo(templateNodeRS.getInt("iNodeNo"));
		templateNodeDtl.setNodeName(templateNodeRS.getString("vNodeName"));
		templateNodeDtl.setNodeDisplayName(templateNodeRS.getString("vNodeDisplayName"));
		templateNodeDtl.setNodeTypeIndi(templateNodeRS.getString("cNodeTypeIndi").charAt(0));
		templateNodeDtl.setParentNodeId(templateNodeRS.getInt("iParentNodeId"));
		templateNodeDtl.setFolderName(templateNodeRS.getString("vFolderName"));
		templateNodeDtl.setRequiredFlag(templateNodeRS.getString("cRequiredFlag").charAt(0));
		templateNodeDtl.setPublishFlag(templateNodeRS.getString("cPublishFlag").charAt(0));
		templateNodeDtl.setRemark(templateNodeRS.getString("vRemark"));
		templateNodeDtl.setModifyBy(templateNodeRS.getInt("iModifyBy"));
		templateNodeDtl.setModifyOn(templateNodeRS.getTimestamp("dModifyOn"));
		templateNodeDtl.setStatusIndi(templateNodeRS.getString("cStatusIndi").charAt(0));
		templateNodeDtl.setDefaultFileFormat(templateNodeRS.getString("vDefaultFileFormat"));
	
	}
	else{
		templateNodeDtl = null;
	}
	templateNodeRS.close();
	
	
	if (templateNodeDtl != null) {
		//Attach Node Attributes
		ArrayList<DTOTemplateNodeAttrDetail> attrDtlList = new ArrayList<DTOTemplateNodeAttrDetail>();
		ResultSet nodeAttrDtlRS= nodeAttrDtlStmt.executeQuery();
		while(nodeAttrDtlRS.next())
		{
			DTOTemplateNodeAttrDetail attrDtl = new DTOTemplateNodeAttrDetail();
			attrDtl.setTemplateId(nodeAttrDtlRS.getString("vTemplateId"));//templateId
			attrDtl.setNodeId(nodeAttrDtlRS.getInt("iNodeId"));//nodeId
			attrDtl.setAttrId(nodeAttrDtlRS.getInt("iAttrId"));//attrId
			attrDtl.setAttrName(nodeAttrDtlRS.getString("vAttrName"));//attrName
			attrDtl.setAttrValue(nodeAttrDtlRS.getString("vAttrValue"));//attrValue
			attrDtl.setValidValues(nodeAttrDtlRS.getString("vValidValues"));//validValues
			attrDtl.setRequiredFlag(nodeAttrDtlRS.getString("cRequiredFlag").charAt(0));//requiredFlag
			attrDtl.setEditableFlag(nodeAttrDtlRS.getString("cEditableFlag").charAt(0));//editableFlag
			attrDtl.setAttrForIndiId(nodeAttrDtlRS.getString("vAttrForIndiId"));//attrForIndiId
			attrDtl.setRemark(nodeAttrDtlRS.getString("vRemark"));//remark
			attrDtl.setModifyBy(nodeAttrDtlRS.getInt("iModifyBy"));//modifyBy
			attrDtl.setModifyOn(nodeAttrDtlRS.getTimestamp("dModifyOn"));//modifyOn
			attrDtl.setStatusIndi(nodeAttrDtlRS.getString("cStatusIndi").charAt(0));//statusIndi
			
			attrDtlList.add(attrDtl);
		}
		nodeAttrDtlRS.close();
		
		//Set Node Attributes
		templateNodeDtl.setNodeAttrList(attrDtlList);
		
		/*Attach Child Nodes*/
		ArrayList<DTOTemplateNodeDetail> childNodes = new ArrayList<DTOTemplateNodeDetail>();
		ArrayList<Integer> childNodeIds = new ArrayList<Integer>();
		
		//Fetch child NodeIds
		ResultSet childrenRS= nodeChildrenStmt.executeQuery();
		while(childrenRS.next()){
			int childNodeId = childrenRS.getInt("iNodeId");
			childNodeIds.add(childNodeId);
			
		}
		childrenRS.close();
		//Process On Fetched Ids
		for (Integer childNodeId : childNodeIds) {
			//Updating 'iNodeId' in All Prepared Statements for fetching child Nodes
			for (int i = 0; i < nodeStmts.length; i++) {
				nodeStmts[i].setInt(1, childNodeId);
			}
			//Recursive call to fetch child Nodes
			DTOTemplateNodeDetail childNodeDtl= getTemplateNodeDtlTree(nodeStmts);
			if(childNodeDtl != null){
				childNodes.add(childNodeDtl);
			}
			
		}
		//Set Node Children
		templateNodeDtl.setChildNodeList(childNodes);
	}
	
	return templateNodeDtl;
}
 
public static DTOTemplateNodeDetail getTemplateRootNode(String templateId){
	
	DTOTemplateNodeDetail rootNodeDetail= null;
	//Assuming that root node's parent id must be 0 in database 
	int rootParentId = 0;
	
	Vector rootNode = new TemplateNodeDetail().getChildNodes(templateId,rootParentId);
	
	//There should be only one node with parent id zero.
	if(rootNode.size()== 1){
		rootNodeDetail = (DTOTemplateNodeDetail)rootNode.get(0);
	}
	
	return rootNodeDetail;
}

public static void generateDir(DTOTemplateNodeDetail nodeDtl,File parentDir){
	
	File currDir = new File(parentDir,nodeDtl.getFolderName());
	if(nodeDtl.getChildNodeList() != null && nodeDtl.getChildNodeList().size() != 0)
	currDir.mkdirs();
	
	for (int i = 0; i < nodeDtl.getChildNodeList().size(); i++) {
		generateDir(nodeDtl.getChildNodeList().get(i),currDir);
	}
}

public static void main (String [] arg){
	
	new ConnectionManager(new Configuration());
	generateDir(getTemplateNodeDtlTree("0001", 1),new File("/home"));
	
}

public int isLeafNode(String templateId, int nodeId) {
	ResultSet rs = null;
	int leafNodes = 0;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		String Where = " vTemplateId='" + templateId + "' and iParentNodeId="
				+ nodeId;
		rs = dataTable.getDataSet(con, "count(*) as LeafNodes",
				"templateNodeDetail", Where, "");

		if (rs.next()) {
			leafNodes = rs.getInt("LeafNodes");
		} else {
			leafNodes = 0;
		}
		rs.close();
		con.close();

	} catch (Exception e) {
		e.printStackTrace();
	}
	if (leafNodes >= 1) {
		return 0; // false //parent node
	} else {
		return 1; // true //leaf node
	}
}
}//main class ended



	  
	  