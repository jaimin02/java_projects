package com.docmgmt.server.db.master;

import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import java.sql.*;
import com.docmgmt.dto.*;


public class NodeStatus 
{
	
	ConnectionManager conMgr;
	
	public NodeStatus()
	{
		conMgr=new ConnectionManager(new Configuration());
	}
	
public boolean insertIntoNodeStatus(DTONodeStatus dto)
{
	try
	{	
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement cs=con.prepareCall("{call Insert_NodeStatus(?,?,?,?,?,?,?)}");
		cs.setString(1, dto.getWorkSpaceId() );
		cs.setInt(2, dto.getNodeId());
		cs.setInt(3, dto.getTranNo());
		cs.setInt(4, dto.getAttrId());
		cs.setString(5, dto.getAttrValue());
		cs.setString(6, dto.getRemark());
		cs.setInt(7, dto.getModifyBy());
		cs.execute();
		cs.close();
		con.close();
				
	}catch(SQLException e){
		e.printStackTrace();
	}

	return true;
}	
		
	
}//main class end
