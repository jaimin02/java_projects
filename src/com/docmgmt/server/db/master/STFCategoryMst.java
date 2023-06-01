package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

import com.docmgmt.dto.DTOSTFCategoryAttrValueMatrix;
import com.docmgmt.dto.DTOSTFCategoryMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;



public class STFCategoryMst {
	
	DataTable dataTable;
	
	public STFCategoryMst()
	{
		dataTable = new DataTable();
	}

public Vector<DTOSTFCategoryMst> getAllSTFCategory() 
{
	Vector<DTOSTFCategoryMst> stfvector = new Vector<DTOSTFCategoryMst>();		
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con,"vCategoryId,vCategoryName,iModifyBy,dModifyOn,cStatusIndi", "stfCategoryMst", "", "");
		while(rs.next())
		{
			DTOSTFCategoryMst dto = new DTOSTFCategoryMst();
			dto.setId(rs.getString("vCategoryId"));//id
			dto.setCategoryName(rs.getString("vCategoryName"));//categoryName
			//dto.setCategory(rs.getString("vCategory"));//category // column deleted
			dto.setModifyBy(rs.getInt("iModifyBy"));//modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));//modifyBy
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			stfvector.addElement(dto);
		}
	}catch(Exception e ){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
		try {if(con!= null){con.close();}} catch (Exception e) {e.printStackTrace();}
	}
	return stfvector;

}


//Added by : Ashmak Shah
//Added On : 16 June 2009
public Vector<DTOSTFCategoryAttrValueMatrix> getSTFCategoryValues(String CategoryId) 
{
	Vector<DTOSTFCategoryAttrValueMatrix> stfCategoryValuesVector = new Vector<DTOSTFCategoryAttrValueMatrix>();		
	Connection con = null;
	ResultSet rs = null;
	try
	{
		con = ConnectionManager.ds.getConnection();
		
		String where = "CategoryId = '"+CategoryId+"'";
		
		rs = dataTable.getDataSet(con,"*","view_STFCategoryAttrValueMatrix",where,"");
		while(rs.next())
		{
			DTOSTFCategoryAttrValueMatrix dto = new DTOSTFCategoryAttrValueMatrix();
			dto.setCategoryAttrValueId(rs.getString("CategoryAttrValueId"));
			dto.setCategoryId(rs.getString("CategoryId"));
			dto.setCategoryName(rs.getString("CategoryName"));
			dto.setAttrName(rs.getString("AttrName"));
			dto.setAttrValue(rs.getString("AttrValue"));
			dto.setCategoryValue(rs.getString("CategoryValue"));
			dto.setModifyBy(rs.getInt("ModifyBy"));
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0));
			stfCategoryValuesVector.addElement(dto);
		}
	
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
		try {if(con!= null){con.close();}} catch (Exception e) {e.printStackTrace();}
	}
	return stfCategoryValuesVector;

}

}//main class ended