package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTORoleOperationMatrix;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;



public class RoleOperationMatrixMst {
	
	ConnectionManager conMgr;
	DataTable datatable;
	
	public RoleOperationMatrixMst()
	{
		 conMgr = new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}
	
public void insertIntoRoleOperationMatrix(ArrayList<DTORoleOperationMatrix> roleList)
{
	try
	{	    	
		Connection con =ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call Insert_RoleOperationMatrix(?,?,?,?)}");
	
		for (DTORoleOperationMatrix dto : roleList) {

			proc.setString(1, dto.getOperationCode());
			proc.setString(2, dto.getUserTypeCode() );
			proc.setString(3, Character.toString(dto.getActiveFlag()));
			proc.setInt(4, dto.getModifyBy());
			proc.execute();
		}
				proc.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
}

public Vector getOperationCodeFromRoleOpMatrix(String UserType)
{
	Vector AllRoleOpnMatrixCode = new Vector();
	try
	{	    	
		Connection con =ConnectionManager.ds.getConnection();
		ResultSet rs=datatable.getDataSet(con,"vOperationCode","roleOperationMatrix" ,"vUserTypeCode="+UserType,"iStageId");
		while(rs.next())
		{
			DTORoleOperationMatrix dto = new DTORoleOperationMatrix();
			dto.setOperationCode(rs.getString("operationCode"));
			dto.setActiveFlag(rs.getString("activeFlag").charAt(0));
			dto.setStatusIndi(rs.getString("statusIndi").charAt(0));
			AllRoleOpnMatrixCode.addElement(dto);					

	}
		
		rs.close();
		con.close();
		
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	return AllRoleOpnMatrixCode;
}


}//main class ended
