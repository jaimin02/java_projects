package com.docmgmt.server.db.master;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOAttributeGroupMatrix;
import com.docmgmt.server.db.dbcp.ConnectionManager;



public class AttributeGroupMatrix {
	
	DataTable dataTable = new DataTable();

public Vector<DTOAttributeGroupMatrix> getAttributeGroupMatrixValues(String attrGroupId) {
        
        
        Vector<DTOAttributeGroupMatrix> attrDetails = new Vector<DTOAttributeGroupMatrix>();
        try{
        	   
            Connection con = ConnectionManager.ds.getConnection();
            ResultSet attrGroupRs = dataTable.getDataSet(con, "*","view_attributeGroupMatrix" , "attrGroupId = '"+attrGroupId+"'", "");
            while(attrGroupRs.next()) 
              { 
            	  	DTOAttributeGroupMatrix attrGroupDTO = new DTOAttributeGroupMatrix();
               		attrGroupDTO.setAttrName(attrGroupRs.getString("attrName"));
               		attrGroupDTO.setAttrId(attrGroupRs.getInt("attrId"));
                	attrGroupDTO.setAttrGroupId(attrGroupRs.getString("attrGroupId"));
                	attrGroupDTO.setStatusIndi(attrGroupRs.getString("statusIndi").charAt(0));
                	attrGroupDTO.setModifyOn(attrGroupRs.getTimestamp("modifyOn"));
                	attrGroupDTO.setId(attrGroupRs.getInt("id"));
                	attrGroupDTO.setAttrValue(attrGroupRs.getString("attrValue"));
                	attrGroupDTO.setAttrType(attrGroupRs.getString("attrType"));
                	attrGroupDTO.setAttrForIndi(attrGroupRs.getString("attrForIndiId"));
                	attrDetails.addElement(attrGroupDTO);
                	attrGroupDTO = null;
                } 
                	
                attrGroupRs.close();
                con.close();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
        return attrDetails;
    }    
	 

	public void insertIntoAttributeGroupMatrix(DTOAttributeGroupMatrix dto,int dataMode){
		
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call Insert_AttributeGroupMatrix(?,?,?,?,?,?)}");	
			proc.setInt(1, dto.getId());
			proc.setString(2, dto.getAttrGroupId());
			proc.setInt(3, dto.getAttrId());
			proc.setInt(4, dto.getModifyBy());
			proc.setString(5, Character.toString(dto.getStatusIndi()));
			proc.setInt(6, dataMode);
			proc.executeUpdate();
			proc.close();
			con.close();
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
 
}