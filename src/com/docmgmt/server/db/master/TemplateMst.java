package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOTemplateMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class TemplateMst{
	
	static DataTable dataTable = new DataTable();;
	
	
 public Vector<DTOTemplateMst> getAllTemplates()
 {
	 Vector<DTOTemplateMst> data = new Vector<DTOTemplateMst>();
	 try
	 {
		 Connection con = ConnectionManager.ds.getConnection();
		 ResultSet rs=dataTable.getDataSet(con,"*", "templatemst", "cStatusIndi<>'D'", "vTemplateDesc");
		 while(rs.next())
		 {
			 DTOTemplateMst dto = new DTOTemplateMst();
			 dto.setTemplateId(rs.getString("vTemplateId"));
			 dto.setTemplateDesc(rs.getString("vTemplateDesc"));
			 dto.setDocTypeCode(rs.getString("vDocTypeCode"));
			 dto.setRemark(rs.getString("vRemark"));
			 dto.setModifyBy(rs.getInt("iModifyBy"));
			 dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			 dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			 data.addElement(dto);
		 }
		 rs.close();
		 con.close();
	 }
	 catch(SQLException e){
		 e.printStackTrace();
	 }
	 return data;
}	
 public Vector<DTOTemplateMst> getTemplates()
 {
	 Vector<DTOTemplateMst> data = new Vector<DTOTemplateMst>();
	 try
	 {
		 Connection con = ConnectionManager.ds.getConnection();
		 ResultSet rs=dataTable.getDataSet(con,"*", "templatemst", "vDeptCode='0001' and cStatusIndi<>'D'", "vTemplateDesc");
		 while(rs.next())
		 {
			 DTOTemplateMst dto = new DTOTemplateMst();
			 dto.setTemplateId(rs.getString("vTemplateId"));
			 dto.setTemplateDesc(rs.getString("vTemplateDesc"));
			 dto.setDocTypeCode(rs.getString("vDocTypeCode"));
			 dto.setRemark(rs.getString("vRemark"));
			 dto.setModifyBy(rs.getInt("iModifyBy"));
			 dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			 dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			 data.addElement(dto);
		 }
		 rs.close();
		 con.close();
	 }
	 catch(SQLException e){
		 e.printStackTrace();
	 }
	 return data;
}
 public Vector<DTOTemplateMst> getTemplatesForCR()
 {
	 Vector<DTOTemplateMst> data = new Vector<DTOTemplateMst>();
	 try
	 {
		 Connection con = ConnectionManager.ds.getConnection();
		 ResultSet rs=dataTable.getDataSet(con,"*", "templatemst", "vDeptCode='0002' and cStatusIndi<>'D'", "vTemplateDesc");
		 while(rs.next())
		 {
			 DTOTemplateMst dto = new DTOTemplateMst();
			 dto.setTemplateId(rs.getString("vTemplateId"));
			 dto.setTemplateDesc(rs.getString("vTemplateDesc"));
			 dto.setDocTypeCode(rs.getString("vDocTypeCode"));
			 dto.setRemark(rs.getString("vRemark"));
			 dto.setModifyBy(rs.getInt("iModifyBy"));
			 dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			 dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			 data.addElement(dto);
		 }
		 rs.close();
		 con.close();
	 }
	 catch(SQLException e){
		 e.printStackTrace();
	 }
	 return data;
}
 public Vector<DTOTemplateMst> getAllTemplatesByDept(String deptcode)
 {
	 Vector<DTOTemplateMst> data = new Vector<DTOTemplateMst>();
	 try
	 {
		 String Wherecond="";
			if(deptcode != null && !deptcode.isEmpty()) {
				Wherecond="vDeptCode='"+deptcode+"'";
			}
		 Connection con = ConnectionManager.ds.getConnection();
		 ResultSet rs=dataTable.getDataSet(con,"*", "templatemst", Wherecond, "vTemplateDesc");
		 while(rs.next())
		 {
			 DTOTemplateMst dto = new DTOTemplateMst();
			 dto.setTemplateId(rs.getString("vTemplateId"));
			 dto.setTemplateDesc(rs.getString("vTemplateDesc"));
			 dto.setDocTypeCode(rs.getString("vDocTypeCode"));
			 dto.setRemark(rs.getString("vRemark"));
			 dto.setModifyBy(rs.getInt("iModifyBy"));
			 dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			 dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			 data.addElement(dto);
		 }
		 rs.close();
		 con.close();
	 }
	 catch(SQLException e){
		 e.printStackTrace();
	 }
	 return data;
}
 public Vector<DTOTemplateMst> PriviewAllTemplates()
 {
	 Vector<DTOTemplateMst> data = new Vector<DTOTemplateMst>();
	 DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	 try
	 {
		 Connection con = ConnectionManager.ds.getConnection();
		 String where = "cStatusIndi<>'D'";
		 ResultSet rs=dataTable.getDataSet(con,"*", "templatemst", where, "vTemplateDesc");
		 while(rs.next())
		 {
			 DTOTemplateMst dto = new DTOTemplateMst();
			 dto.setTemplateId(rs.getString("vTemplateId"));
			 dto.setTemplateDesc(rs.getString("vTemplateDesc"));
			 dto.setDocTypeCode(rs.getString("vDocTypeCode"));
			 dto.setRemark(rs.getString("vRemark"));
			 dto.setModifyBy(rs.getInt("iModifyBy"));
			 dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			 if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			 dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			 data.addElement(dto);
		 }
		 rs.close();
		 con.close();
	 }
	 catch(SQLException e){
		 e.printStackTrace();
	 }
	 return data;
}
 
 
 public Vector<DTOTemplateMst> getTemplateDetailById(String templateCode) 
 {
	 Vector<DTOTemplateMst> data = new Vector<DTOTemplateMst>();
	 try 
	 {		  
		 Connection con = ConnectionManager.ds.getConnection();
		 ResultSet rs=dataTable.getDataSet(con,"*","templatemst", "vTemplateId='"+templateCode+"'", "");
		 while(rs.next())
		 {
			 DTOTemplateMst dto = new DTOTemplateMst();
			 dto.setTemplateId(rs.getString("vTemplateId"));
			 dto.setTemplateDesc(rs.getString("vTemplateDesc"));
			 dto.setDocTypeCode(rs.getString("vDocTypeCode"));
			 dto.setRemark(rs.getString("vRemark"));
			 dto.setModifyBy(rs.getInt("iModifyBy"));
			 dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			 dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			 data.addElement(dto);
		 }
		 rs.close();
		 con.close();
	 }   
	 catch(SQLException e){
		 e.printStackTrace();
	 }	
	
	 return data;
}
	 
 public String insertTemplateDtl(String vTemplateDesc,String vRemark, int userCode)  
 {
	 String templateId="";
	 try
	 {	    	
		 Connection con = ConnectionManager.ds.getConnection();
		 CallableStatement proc = con.prepareCall("{ Call Insert_templateMst(?,?,?,?)}");
		 proc.setString(1,templateId);
		 proc.setString(2,vTemplateDesc);
		 proc.setString(3,vRemark);
		 proc.setInt(4,userCode);
		 ResultSet rs=proc.executeQuery();
		 if(rs.next())
		 {
			 templateId=rs.getString(1);
		 }
		 con.close();
	 }
	 catch(SQLException e){
		 e.printStackTrace();
	 }
	 return templateId;      
}

public Vector<Object []> getTemplateForTreeDisplay(String TemplateId)  
{
	Vector<Object []> data = new Vector<Object []>();
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		String fieldNames= " NodeId,DisplayName,ParentId,NodeNo,Nodename,Foldername,NodeTypeIndi,RequiredFlag,PublishFlag,Remark,ModifyBy,ModifyOn,StatusIndi,DefaultFileFormat";
		ResultSet rs=dataTable.getDataSet(con,fieldNames,"view_TemplateForTreeDisplay","templateId='"+TemplateId+"'","parentId,NodeNo");
		while(rs.next())
		{
			Object [] record =  
			{
				new Integer(rs.getInt("NodeId")),    	
				rs.getString("DisplayName"),			
				new Integer(rs.getInt("ParentId")),		
				new Integer(rs.getInt("NodeNo")),	    
				rs.getString("Nodename"),
				rs.getString("Foldername"),
				new Character(rs.getString("NodeTypeIndi").charAt(0)),
				new Character(rs.getString("RequiredFlag").charAt(0)),
				new Character(rs.getString("PublishFlag").charAt(0)),
				rs.getString("Remark"),
				new Integer(rs.getInt("ModifyBy")),
				rs.getTimestamp("ModifyOn"),
				new Character(rs.getString("StatusIndi").charAt(0)),
				rs.getString("DefaultFileFormat")
			};
			data.addElement(record);
			record=null;
		}
		rs.close();
		con.close();
		
	}catch(SQLException e){
		e.printStackTrace();
	}	
	return data;
}
	 	 
public int getTotalTemplateRecordCount()
{
	int totalRecord = 0;
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con,"count(*) count" , "templateMst", "", "");
		if(rs.next()){
			totalRecord = rs.getInt("count");
	 	}
		rs.close();
		con.close();
	 		
	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return totalRecord;
}
	 
public Vector<DTOTemplateMst> getAllTemplatesDetail(int maxCount,int firstRecordCount,int givenPageNumber,String SortOrder)  
{
	Vector<DTOTemplateMst> data = new Vector<DTOTemplateMst>();
	try {
		StringBuffer query = new StringBuffer();
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=null;
		if(SortOrder.equals("Desc"))
		{
			query.append(" dModifyOn desc");
		}
		else
		{
			query.append(" dModifyOn asc");
		}
		if(givenPageNumber==0 || givenPageNumber==1){
		 rs = dataTable.getDataSet(con, "top "+maxCount+" *", "templateMst", "cStatusIndi != 'D'", query.toString());
		}else{
		  rs = dataTable.getDataSet(con, "*", "(SELECT ROW_NUMBER() OVER (ORDER BY vTemplateId) as row,* FROM templatemst where cStatusIndi <> 'D') a", "a.row >= '"+maxCount+"' and a.row <= '"+firstRecordCount+"'", query.toString());
		}
			while(rs.next())
		{
			DTOTemplateMst dto = new DTOTemplateMst();
			dto.setTemplateId(rs.getString("vTemplateId"));
			dto.setTemplateDesc(rs.getString("vTemplateDesc"));
			dto.setDocTypeCode(rs.getString("vDocTypeCode"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			data.addElement(dto);
			dto = null;
		}
		rs.close();
		con.close();

	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return data;
}	 

public void updateTemplateMst(DTOTemplateMst dto) 
{
	try
	{	    	
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call Update_TemplateMst(?,?,?,?,?,?)}");
		proc.setString(1,dto.getTemplateId());
		proc.setString(2,dto.getTemplateDesc());
		proc.setString(3,dto.getDocTypeCode());
		proc.setString(4,dto.getRemark());
		proc.setInt(5,dto.getModifyBy());
		proc.setString(6,Character.toString(dto.getStatusIndi())); 
		proc.executeUpdate();
		proc.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
}	 
public Vector<DTOTemplateMst> getAllTemplatesForDocuments()
{
	 Vector<DTOTemplateMst> data = new Vector<DTOTemplateMst>();
	 try
	 {
		 Connection con = ConnectionManager.ds.getConnection();
		 String deptCode="0001";
		 ResultSet rs=dataTable.getDataSet(con,"*", "templatemst", "vdeptcode='"+deptCode+"' and cStatusIndi<>'D'", "vTemplateDesc");
		 while(rs.next())
		 {
			 DTOTemplateMst dto = new DTOTemplateMst();
			 dto.setTemplateId(rs.getString("vTemplateId"));
			 dto.setTemplateDesc(rs.getString("vTemplateDesc"));
			 dto.setDocTypeCode(rs.getString("vDocTypeCode"));
			 dto.setRemark(rs.getString("vRemark"));
			 dto.setModifyBy(rs.getInt("iModifyBy"));
			 dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			 dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			 data.addElement(dto);
		 }
		 rs.close();
		 con.close();
	 }
	 catch(SQLException e){
		 e.printStackTrace();
	 }
	 return data;
} 
}//main class ended