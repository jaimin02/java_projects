package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.docmgmt.dto.DTODefaultWorkSpaceMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;



public class DefaultWorkspaceMst {

public static void insertIntoDefaultWorkspaceMst(DTODefaultWorkSpaceMst dto,int Mode)
{
	try 
	{
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement proc=con.prepareCall("{call Insert_DefaultWorkSpacemst(?,?,?,?,?)}");
		
		proc.setInt(1,dto.getUserCode());
		proc.setString(2,dto.getWorkSpaceID());
		proc.setString(3,dto.getRemark() );
		proc.setInt(4,dto.getModifyBy());
		proc.setInt(5, Mode);
		proc.execute();
		proc.close();
		con.close();
		
	}
	catch(SQLException e){
	    e.printStackTrace();
	}
}

public static ArrayList<DTODefaultWorkSpaceMst> getDefaultWorkspaceMst(int Usercode)
{
	ArrayList<DTODefaultWorkSpaceMst> AlldefaultWorkSpacedata = new ArrayList<DTODefaultWorkSpaceMst>();
	try
	{	    	
		Connection con =ConnectionManager.ds.getConnection();
		DataTable datatable  = new DataTable();
		String where = "iUserCode="+Usercode+" AND "+
								"cStatusIndiDefaultWorkSpaceMst <> 'D' AND " +
								"cStatusIndiWorkSpaceMst <> 'D' AND " +
								"cStatusIndiWorkSpaceUserMst <> 'D'";
			
		ResultSet rs=datatable.getDataSet(con,"*","View_DefaultWorkSpaceMst" ,where,"");
		while(rs.next())
		{
			DTODefaultWorkSpaceMst dto = new DTODefaultWorkSpaceMst();
			dto.setUserCode(rs.getInt("iUserCode"));
			dto.setWorkSpaceID(rs.getString("vWorkSpaceid"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			
			AlldefaultWorkSpacedata.add(dto);					
	    }
		
		rs.close();
		con.close();
		
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	return AlldefaultWorkSpacedata;
}


}//main class ended
