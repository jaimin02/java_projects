package com.docmgmt.server.db.master;

import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import java.sql.*;
import java.util.*;

import com.docmgmt.dto.*;

public class TemplateUserRightsMst 
{
	ConnectionManager conMgr;
	DataTable dataTable;
	
	public TemplateUserRightsMst()
	{
		conMgr=new ConnectionManager(new Configuration());
		dataTable=new DataTable();
	}
	
public void insertTemplateUserRights(DTOTemplateUserRightsMst obj,int[] usercode)
{
	try
	{
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement cs=con.prepareCall("{call Insert_templateUserRights(?,?,?,?,?,?,?,?,?,?,?)}");
		for(int i=0;i<usercode.length;i++)
		{
			cs.setString(1, obj.getTemplateId());
			cs.setInt(2, obj.getUserGroupCode());
			cs.setInt(3, usercode[i]);
			cs.setString(4,Character.toString(obj.getCanReadFlag()));
			cs.setString(5, Character.toString(obj.getCanAddFlag()));
			cs.setString(6, Character.toString(obj.getCanEditFlag()));
			cs.setString(7, Character.toString(obj.getCanDeleteFlag()));
			cs.setString(8, obj.getAdvancedRights());
			cs.setString(9, obj.getRemark());
			cs.setInt(10, obj.getModifyBy());
			cs.setInt(11, obj.getStageId());
			cs.execute();
		}
		cs.close();
		con.close();
			
	}catch (SQLException e) {
		e.printStackTrace();
	}
				
}
	
public Vector getTemplateUserRightsDetail(String templateId, int nodeId)
{
	Vector data = new Vector();
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con, "vTemplateId,iNodeId,vUserName,cCanEditFlag,vStageDesc", "view_templateUserRightsDetail","vTemplateId = '"+templateId+"' and iNodeId ="+nodeId, "");
		while(rs.next())
		{
			DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
			dto.setUserName(rs.getString("vUserName"));
			dto.setCanEditFlag(rs.getString("cCanEditFlag").charAt(0));
			dto.setStageDesc(rs.getString("vStageDesc"));
			data.addElement(dto);
		}
		rs.close();
		con.close();
		
	}catch(SQLException e){
		e.printStackTrace();
	}

	return data;
}
	
public boolean updateTemplateUserRights(DTOTemplateUserRightsMst dto)  
{
	try 
	{
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs=con.prepareCall("{call Proc_updateTemplateUserRights(?,?,?,?,?,?,?,?)}");
		cs.setString(1, dto.getTemplateId());
		cs.setInt(2, dto.getNodeId());
		cs.setInt(3,dto.getUserCode());
		cs.setInt(4, dto.getUserGroupCode());
		cs.setString(5, Character.toString(dto.getCanDeleteFlag()));
		cs.setString(6, Character.toString(dto.getCanAddFlag()));
		cs.setString(7, Character.toString(dto.getCanEditFlag()));
		cs.setInt(8, dto.getStageId());
		cs.execute();	
		cs.close();
		con.close();
		
	}catch(SQLException e){
		e.printStackTrace();
	}

	return true;
}

public boolean updateUserRightsForTemplate(DTOTemplateUserRightsMst dto) 
{
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call proc_updateUserRightsForTemplate(?,?,?,?,?,?,?,?)}");
		proc.setString(1, dto.getTemplateId());
		proc.setInt(2, dto.getNodeId());
		proc.setInt(3, dto.getUserGroupCode());
		proc.setInt(4, dto.getUserCode());
		proc.setString(5,Character.toString(dto.getCanAddFlag()));
		proc.setString(6,Character.toString(dto.getCanEditFlag()));
		proc.setString(7,Character.toString(dto.getCanDeleteFlag()));
		proc.setString(8, dto.getAdvancedRights());
		proc.execute();
		proc.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	
	return true;
}

public Vector getTemplateUserRightsReport(DTOTemplateUserRightsMst dto)
{
	Vector  data = new Vector();
	try  
	{
		StringBuffer query = new StringBuffer();
		query.append("templateId='"+dto.getTemplateId()+"'");
		if(dto.getUserCode() > 0)
			query.append(" and UserCode = " + dto.getUserCode());
		
		Connection con = ConnectionManager.ds.getConnection();
		String Fields="templateId,templatedesc,NodeDisplayName,UserName,userGroupName,canReadFlag," +
						"canEditFlag,UserCode,advancedRights";
		ResultSet rs=dataTable.getDataSet(con,Fields,"View_TemplateNodeRightsDetail",query.toString()," UserCode");
		while (rs.next()) 
		{
			DTOTemplateUserRightsMst dtowurmst = new DTOTemplateUserRightsMst();
			dtowurmst.setTemplateDesc(rs.getString("templatedesc"));			//templatedesc from workspaceMst
			dtowurmst.setNodeDisplayName(rs.getString("NodeDisplayName"));		//nodeDisplayName from workSpaceNodeDetail 
			dtowurmst.setUserName(rs.getString("Username"));					//userName from userMst
			dtowurmst.setUserGroupName(rs.getString("userGroupName"));			//userGroupName from userGroupMst
			dtowurmst.setReadRights(rs.getString("canReadFlag"));				//readRights from workspaceUserRights
			dtowurmst.setEditRights(rs.getString("canEditFlag"));				//editRights from workspaceUserRights
			dtowurmst.setUserCode(rs.getInt("UserCode"));						//userCode from workspaceUserRights
			dtowurmst.setAdvancedRights(rs.getString("advancedRights"));		//advanceRights from workspaceUserRights
			data.add(dtowurmst);
			dtowurmst = null;
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
