package com.docmgmt.server.db.master;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOAttributeValueMatrix;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class AttributeValueMatrix 
{
	
	DataTable dataTable;
	
	public AttributeValueMatrix()
	{
		dataTable=new DataTable();
	}
	
public Vector<DTOAttributeValueMatrix> getAttributeValueByUserType(String vWsId,int iNodeId,String userTypeCode)
{
	Vector<DTOAttributeValueMatrix> data = new Vector<DTOAttributeValueMatrix>();
	try 
	{
		Connection con = ConnectionManager.ds.getConnection();
		String whr="WorkspaceId='"+vWsId+"' and NodeId="+iNodeId+" and userTypeCode='"+userTypeCode+"'";
		ResultSet rs=dataTable.getDataSet(con,"*","view_getAttributeValueByUserType",whr,"");
		while (rs.next())  
		{
			DTOAttributeValueMatrix dtoavm=new DTOAttributeValueMatrix();
			dtoavm.setAttrValue(rs.getString("attrValue"));
			dtoavm.setAttrType(rs.getString("attrType"));
			dtoavm.setAttrValueId(rs.getInt("attrValueId"));
			dtoavm.setAttrMatrixValue(rs.getString("attrMatrixValue"));
			dtoavm.setAttrMatrixDisplayValue(rs.getString("attrDisplayValue"));
			dtoavm.setAttrId(rs.getInt("attrId"));
			dtoavm.setAttrName(rs.getString("attrName"));
			dtoavm.setAttrForIndiId(rs.getString("attrIndiId"));
			dtoavm.setUserTypeCode(rs.getString("userTypeCode"));
			dtoavm.setUserInput(rs.getString("userInput").charAt(0));
			data.addElement(dtoavm);
			dtoavm = null;
		}	
		rs.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	
	return data;
	
}		


//Added By : Ashmak Shah (25-Aug-09)
//Used In DMS Search
public Vector<DTOAttributeValueMatrix> getAttributeValuesFromMatrix(String attributeName){
	
	Vector<DTOAttributeValueMatrix> attrValues = new Vector<DTOAttributeValueMatrix>();
	try{
		Connection con = ConnectionManager.ds.getConnection();
		String fields = "attrId,attrName,attrForIndiId,attrValueId,attrMatrixValue,attrType,attrDisplayValue";
		String where = " attrName = '"+attributeName+"'";
		ResultSet rs = dataTable.getDataSet(con, fields, "view_getAttributeByAttrForIndi", where, "attrMatrixValue");
		
		while(rs.next())
		{
			DTOAttributeValueMatrix dto = new DTOAttributeValueMatrix();
			dto.setAttrId(rs.getInt("attrId"));
			dto.setAttrName(rs.getString("attrName"));
			dto.setAttrForIndiId(rs.getString("attrForIndiId"));
			dto.setAttrValueId(rs.getInt("attrValueId"));
			dto.setAttrMatrixValue(rs.getString("attrMatrixValue"));
			dto.setAttrMatrixDisplayValue(rs.getString("attrDisplayValue"));
			dto.setAttrType(rs.getString("attrType"));
			
			attrValues.addElement(dto);
			
		}
		rs.close();
		con.close();
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	return attrValues;
}
public String getAttrValueByAttrValueId(int attrValueId)
{
	String attValue="";
	Connection con = null ;
	ResultSet rs = null ;
	try{
		String fields = "vattrvalue";
		String where="iattrvalueid="+attrValueId;
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, fields, "attributevaluematrix", where, "");
		while(rs.next())
		{
			attValue=rs.getString("vattrvalue");
			System.out.println(attValue);
		}
	}catch(SQLException e){
		e.printStackTrace();
	}
	finally{
		try{if(rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
		try{if(con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
	return attValue;
}
}//main class ended
