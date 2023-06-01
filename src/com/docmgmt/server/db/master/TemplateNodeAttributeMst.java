package com.docmgmt.server.db.master;


import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class TemplateNodeAttributeMst 
{
	DataTable dataTable;
	ConnectionManager conMgr;
	
	public TemplateNodeAttributeMst()
	{
		 dataTable=new DataTable();
		 ConnectionManager conMgr=new ConnectionManager(new Configuration());
	}
	
/*	 public Vector getTemplateNodeAttr(String templateId) 
	 {
	        
			Vector templateNodeVector = new Vector();		
			ResultSet rs = null;
			try
			{
				
				String whr="vTemplateId ="+templateId+" and cStatusIndi = 'N'";
				Connection con = conMgr.ds.getConnection();
				rs=dataTable.getDataSet(con,"*", "templateNodeAttribute",whr, "vAttrName asc");
				while(rs.next())
				{
						DTOTemplateNodeAttr dto = new DTOTemplateNodeAttr();
						dto.setTemplateId(templateId);//templateId
						dto.setNodeId(rs.getInt(2));//nodeId
						dto.setAttrId(rs.getInt(3));//attrId
						dto.setAttrName(rs.getString(4));//attrName
						dto.setAttrValue(rs.getString(5));//attrValue
						dto.setModifyBy(rs.getInt(6));//modifyBy
						dto.setModifyOn(rs.getTimestamp(7));//modifyOn
						dto.setStatusIndi(rs.getString(8).charAt(0));//statusIndi
						templateNodeVector.addElement(dto);
						dto = null;
					}
			
				rs.close();
				con.close();
				
			}catch(SQLException e){
		    		e.printStackTrace();
		    }
			return templateNodeVector;
	    }
	 
	 public Vector getTemplateNodeAttribute(DTOTemplateNodeAttr dto){
			
		 Vector data = new Vector();	     
	     ResultSet rs = null;
	     try {
	    	 	String whr = "vTemplateId = '"+dto.getTemplateId()+"' and iNodeId ="+dto.getNodeId();
	    	 	Connection con = conMgr.ds.getConnection();
	    	 	rs = dataTable.getDataSet(con,"*", "templateNodeAttribute", whr, "");
	    	 	while(rs.next())
	    	 	{
	    	 		dto.setTemplateId(rs.getString(1));//templateId
					dto.setNodeId(rs.getInt(2));//nodeId
					dto.setAttrId(rs.getInt(3));//attrId
					dto.setAttrName(rs.getString(4));//attrName
					dto.setAttrValue(rs.getString(5));//attrValue
					dto.setModifyBy(rs.getInt(6));//modifyBy
					dto.setModifyOn(rs.getTimestamp(7));//modifyOn
					dto.setStatusIndi(rs.getString(8).charAt(0));//statusIndi
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
	 
	 public void insertIntoTemplateNodeAttribute(DTOTemplateNodeAttrDetail dto){
		 	
		 CallableStatement proc = null;
			try
				{
					Connection con = conMgr.ds.getConnection();
					proc = con.prepareCall("{ Call Insert_templateNodeAttribute(?,?,?,?,?,?)}");
					proc.setString(1, dto.getTemplateId());
					proc.setInt(2, dto.getNodeId());
					proc.setInt(3, dto.getAttrId());
					proc.setString(4, dto.getAttrName());
					proc.setString(5, dto.getAttrValue());
					proc.setInt(6, dto.getModifyBy());
					proc.execute();
					proc.close();
					con.close();
				}
				catch(SQLException e)
				{
				  e.printStackTrace();
				}
			
		}*/
		
}//main class end