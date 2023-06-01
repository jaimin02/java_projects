package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.docmgmt.dto.DTOWorkspaceNodeReminderDoneStatus;
import com.docmgmt.dto.DTOWorkspaceNodeReminderIgnoreStatus;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class CalendarAndReminder
{
	public ArrayList<DTOWorkspaceNodeReminderDoneStatus> getReminderDoneStatus()
	{
		ArrayList<DTOWorkspaceNodeReminderDoneStatus> doneList = new ArrayList<DTOWorkspaceNodeReminderDoneStatus>();
		ResultSet rs = null;
		Connection con = null;
		try
		{
			DataTable dataTable=new DataTable();
			con=ConnectionManager.ds.getConnection();
			String fieldNames=" * ";
			String tableName=" WorkspaceNodeReminderDoneStatus ";
			String where=" cDoneFlag='Y' and cStatusIndi<>'D' ";
			String orderBy="";
			rs = dataTable.getDataSet(con, fieldNames, tableName, where, orderBy);
			if (rs!=null)
			{
				while(rs.next())
				{
					DTOWorkspaceNodeReminderDoneStatus dto = new DTOWorkspaceNodeReminderDoneStatus();
					dto.setvWorkspaceId(rs.getString("vWorkspaceId"));
					dto.setiNodeId(rs.getInt("iNodeId"));
					dto.setiUserCode(rs.getInt("iUserCode"));
					dto.setiAttrId(rs.getInt("iAttrId"));
					String str=rs.getString("cDoneFlag");
					if (str!=null || str.length()>0)
						dto.setcDoneFlag(str.charAt(0));
					else
						dto.setcDoneFlag(' ');
					dto.setvRemark(rs.getString("vRemark"));
					dto.setiModifyBy(rs.getInt("iModifyBy"));
					dto.setdModifyOn(rs.getTimestamp("dModifyOn"));
					str=rs.getString("cStatusIndi");
					if (str!=null || str.length()>0)
						dto.setcStatusIndi(str.charAt(0));
					else
						dto.setcStatusIndi(' ');
					doneList.add(dto);
				}
			}
						
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if ( rs!=null) try { rs.close();} catch (SQLException e) {e.printStackTrace();}
			if (con!=null) try {con.close();} catch (SQLException e) {e.printStackTrace();}			
		}
		return doneList;
	}
	
	public ArrayList<DTOWorkspaceNodeReminderIgnoreStatus> getReminderIgnoreStatus(int userCode)
	{
		ArrayList<DTOWorkspaceNodeReminderIgnoreStatus> ignoreList = new ArrayList<DTOWorkspaceNodeReminderIgnoreStatus>();
		ResultSet rs = null;
		Connection con = null;
		try
		{
			DataTable dataTable=new DataTable();
			con=ConnectionManager.ds.getConnection();
			String fieldNames=" * ";
			String tableName=" WorkspaceNodeReminderIgnoreStatus ";
			String where=" iUserCode=" + userCode + " and cStatusIndi<>'D' ";
			String orderBy="";
			rs = dataTable.getDataSet(con, fieldNames, tableName, where, orderBy);
			if (rs!=null)
			{
				while(rs.next())
				{
					DTOWorkspaceNodeReminderIgnoreStatus dto = new DTOWorkspaceNodeReminderIgnoreStatus();
					dto.setvWorkspaceId(rs.getString("vWorkspaceId"));
					dto.setiNodeId(rs.getInt("iNodeId"));
					dto.setiUserCode(rs.getInt("iUserCode"));
					dto.setiAttrId(rs.getInt("iAttrId"));
					dto.setdIgnoreUpto(rs.getTimestamp("dIgnoreUpto"));
					dto.setvRemark(rs.getString("vRemark"));
					dto.setiModifyBy(rs.getInt("iModifyBy"));
					dto.setdModifyOn(rs.getTimestamp("dModifyOn"));
					String str=rs.getString("cStatusIndi");
					if (str!=null || str.length()>0)
						dto.setcStatusIndi(str.charAt(0));
					else
						dto.setcStatusIndi(' ');
					ignoreList.add(dto);
				}
			}
						
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if ( rs!=null) try { rs.close();} catch (SQLException e) {e.printStackTrace();}
			if (con!=null) try {con.close();} catch (SQLException e) {e.printStackTrace();}			
		}
		return ignoreList;
	}

	public boolean markReminderAsDone(String vWorkspaceId,int iNodeId,int iAttrId,int userCode)
	{
		Connection con = null;
		CallableStatement procStatement = null;
		boolean result=true;
		try
		{
			con=ConnectionManager.ds.getConnection();
			procStatement=con.prepareCall("{ Call Insert_WorkspaceNodeReminderDoneStatus(?,?,?,?,?,?,?,?,?,?,?)}");
			procStatement.setString(1,vWorkspaceId);
			procStatement.setInt(2,iNodeId);
			procStatement.setInt(3,iAttrId);
			procStatement.setInt(4,0);
			procStatement.setString(5,"Y");
			procStatement.setString(6,"");
			procStatement.setInt(7,userCode);
			procStatement.setTimestamp(8,null);
			procStatement.setString(9,"Done");
			procStatement.setInt(10,0);
			procStatement.setInt(11,1);
			procStatement.execute();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			result=false;
		}
		finally
		{
			if (procStatement!=null) try {procStatement.close();} catch (SQLException e) {e.printStackTrace();}
			if (con!=null) try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return result;
	}
	public boolean markReminderAsDone(DTOWorkspaceNodeReminderDoneStatus dto)
	{
		Connection con = null;
		CallableStatement procStatement = null;
		boolean result=false;
		try
		{
			con=ConnectionManager.ds.getConnection();
			procStatement=con.prepareCall("{ Call Insert_WorkspaceNodeReminderDoneStatus(?,?,?,?,?,?,?,?,?,?,?)}");
			procStatement.setString(1,dto.getvWorkspaceId());
			procStatement.setInt(2,dto.getiNodeId());
			procStatement.setInt(3,dto.getiAttrId());
			procStatement.setInt(4,0);
			procStatement.setString(5,"Y");
			procStatement.setString(6,"");
			procStatement.setInt(7,dto.getiUserCode());
			procStatement.setTimestamp(8,null);
			procStatement.setString(9,"Done");
			procStatement.setInt(10,0);
			procStatement.setInt(11,1);
			result=procStatement.execute();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if (procStatement!=null) try {procStatement.close();} catch (SQLException e) {e.printStackTrace();}
			if (con!=null) try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return result;
	}	

	public boolean markReminderAsIgnore(String vWorkspaceId,int iNodeId,int iAttrId,int userCode,String ignoreUpto)
	{
		Connection con = null;
		CallableStatement procStatement = null;
		boolean result=true;
		try
		{
			con=ConnectionManager.ds.getConnection();
			procStatement=con.prepareCall("{ Call Insert_WorkspaceNodeReminderIgnoreStatus(?,?,?,?,?,?,?,?,?,?)}");
			procStatement.setString(1,vWorkspaceId);
			procStatement.setInt(2,iNodeId);
			procStatement.setInt(3,iAttrId);
			procStatement.setInt(4,userCode);
			procStatement.setTimestamp(5,new Timestamp(new Date(ignoreUpto).getTime()));
			procStatement.setString(6,"");
			procStatement.setInt(7,userCode);
			procStatement.setTimestamp(8,null);
			procStatement.setString(9,"");			
			procStatement.setInt(10,1);
			procStatement.execute();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			result=false;
		}
		finally
		{
			if (procStatement!=null) try {procStatement.close();} catch (SQLException e) {e.printStackTrace();}
			if (con!=null) try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return result;
	}	
	/*
	public boolean markReminderAsIgnore(DTOWorkspaceNodeReminderIgnoreStatus dto)
	{
		Connection con = null;
		CallableStatement procStatement = null;
		boolean result=false;
		try
		{
			con=ConnectionManager.ds.getConnection();
			procStatement=con.prepareCall("{ Call Insert_WorkspaceNodeReminderIgnoreStatus(?,?,?,?,?,?,?,?,?,?)}");
			procStatement.setString(1,dto.getvWorkspaceId());
			procStatement.setInt(2,dto.getiNodeId());
			procStatement.setInt(3,dto.getiAttrId());
			procStatement.setInt(4,dto.getiUserCode());
			procStatement.setTimestamp(5,dto.getdIgnoreUpto());
			procStatement.setString(6,"");
			procStatement.setInt(7,dto.getiUserCode());
			procStatement.setTimestamp(8,null);
			procStatement.setString(9,"");			
			procStatement.setInt(10,1);
			result=procStatement.execute();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if (procStatement!=null) try {procStatement.close();} catch (SQLException e) {e.printStackTrace();}
			if (con!=null) try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return result;
	}
	*/
	public boolean markReminderAsUnIgnore(String vWorkspaceId,int iNodeId,int iAttrId,int userCode)
	{
		Connection con = null;
		CallableStatement procStatement = null;
		boolean result=true;
		try
		{
			con=ConnectionManager.ds.getConnection();
			procStatement=con.prepareCall("{ Call Insert_WorkspaceNodeReminderIgnoreStatus(?,?,?,?,?,?,?,?,?,?)}");
			procStatement.setString(1,vWorkspaceId);
			procStatement.setInt(2,iNodeId);
			procStatement.setInt(3,iAttrId);
			procStatement.setInt(4,userCode);
			procStatement.setTimestamp(5,null);
			procStatement.setString(6,"");
			procStatement.setInt(7,userCode);
			procStatement.setTimestamp(8,null);
			procStatement.setString(9,"");			
			procStatement.setInt(10,2);
			procStatement.execute();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			result=false;
		}
		finally
		{
			if (procStatement!=null) try {procStatement.close();} catch (SQLException e) {e.printStackTrace();}
			if (con!=null) try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return result;
	}	
	public boolean markReminderAsUnIgnore(DTOWorkspaceNodeReminderIgnoreStatus dto)
	{
		Connection con = null;
		CallableStatement procStatement = null;
		boolean result=false;
		try
		{
			con=ConnectionManager.ds.getConnection();
			procStatement=con.prepareCall("{ Call Insert_WorkspaceNodeReminderIgnoreStatus(?,?,?,?,?,?,?,?,?,?)}");
			procStatement.setString(1,dto.getvWorkspaceId());
			procStatement.setInt(2,dto.getiNodeId());
			procStatement.setInt(3,dto.getiAttrId());
			procStatement.setInt(4,dto.getiUserCode());
			procStatement.setTimestamp(5,null);
			procStatement.setString(6,"");
			procStatement.setInt(7,dto.getiUserCode());
			procStatement.setTimestamp(8,null);
			procStatement.setString(9,"");			
			procStatement.setInt(10,2);
			result=procStatement.execute();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if (procStatement!=null) try {procStatement.close();} catch (SQLException e) {e.printStackTrace();}
			if (con!=null) try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return result;
	}
	/*
	public boolean markReminderAsUnDone(String vWorkspaceId,int iNodeId,int iAttrId,int userCode)
	{
		Connection con = null;
		CallableStatement procStatement = null;
		boolean result=true;
		try
		{
			con=ConnectionManager.ds.getConnection();
			procStatement=con.prepareCall("{ Call Insert_WorkspaceNodeReminderDoneStatus(?,?,?,?,?,?,?,?,?,?,?)}");
			procStatement.setString(1,vWorkspaceId);
			procStatement.setInt(2,iNodeId);
			procStatement.setInt(3,iAttrId);
			procStatement.setInt(4,0);
			procStatement.setString(5,"N");
			procStatement.setString(6,"");
			procStatement.setInt(7,userCode);
			procStatement.setTimestamp(8,null);
			procStatement.setString(9,"");
			procStatement.setInt(10,0);
			procStatement.setInt(11,2);
			procStatement.execute();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			result=false;
		}
		finally
		{
			if (procStatement!=null) try {procStatement.close();} catch (SQLException e) {e.printStackTrace();}
			if (con!=null) try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return result;
	}
	*/
	public boolean markReminderAsUnDone(ArrayList<DTOWorkspaceNodeReminderDoneStatus> dtoList)
	{
		Connection con = null;
		CallableStatement procStatement = null;
		boolean result=false;
		
		try
		{
			for (int i=0;i<dtoList.size();i++)
			{
				DTOWorkspaceNodeReminderDoneStatus dto = dtoList.get(i);
				con=ConnectionManager.ds.getConnection();
				procStatement=con.prepareCall("{ Call Insert_WorkspaceNodeReminderDoneStatus(?,?,?,?,?,?,?,?,?,?,?)}");
				procStatement.setString(1,dto.getvWorkspaceId());
				procStatement.setInt(2,dto.getiNodeId());
				procStatement.setInt(3,dto.getiAttrId());
				procStatement.setInt(4,0);
				procStatement.setString(5,"N");
				procStatement.setString(6,"");
				procStatement.setInt(7,dto.getiUserCode());
				procStatement.setTimestamp(8,null);
				procStatement.setString(9,"");
				procStatement.setInt(10,0);
				procStatement.setInt(11,2);
				result=procStatement.execute();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if (procStatement!=null) try {procStatement.close();} catch (SQLException e) {e.printStackTrace();}
			if (con!=null) try {con.close();} catch (SQLException e) {e.printStackTrace();}
		}
		return result;
	}
}