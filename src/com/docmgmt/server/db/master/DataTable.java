package com.docmgmt.server.db.master;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataTable 
{
	
	public DataTable()
	{}

	public ResultSet getDataSet(Connection con,String FieldNames,String TableName,String Where,String OrderBy)
	{
		ResultSet rs = null;
		try
		{
			
			StringBuffer sb=new StringBuffer();
			sb.append(" Select ");
			sb.append(FieldNames);
			sb.append(" From ");
			sb.append(TableName);
			
			if(!Where.equals(""))
			{
				sb.append(" Where ");
				sb.append(Where);
			}
			if(!OrderBy.equals(""))
			{
				sb.append(" Order By ");
				sb.append(OrderBy);
			}
			
			PreparedStatement Pstmt=con.prepareStatement(sb.toString());
			System.out.println(sb.toString());
			//Pstmt=con.prepareStatement(sb.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=Pstmt.executeQuery();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	
		return rs;	
	}
	
	public ResultSet getDataSet(Connection con,String FieldNames,String TableName,String Where,String OrderBy,int startRecNo,int lastRecNo,String pageNoOrderBy)
	{
		if (startRecNo<=0 || lastRecNo<=0)
		{
			return getDataSet(con, FieldNames, TableName, Where, pageNoOrderBy);
		}
		ResultSet rs = null;
		try
		{			
			StringBuffer sb=new StringBuffer();
			sb.append(" Select ");
			sb.append(FieldNames);
			sb.append(" From ");
			sb.append("(select row_number() over (order by "+ pageNoOrderBy +") as rowid,* from " + TableName );
			if(!Where.equals(""))
			{
				sb.append(" where ");
				sb.append(Where);
			}
			sb.append(") as tempTable where rowid between " + startRecNo + " and " + lastRecNo);			
			if(!OrderBy.equals(""))
			{
				sb.append(" Order By ");
				sb.append(OrderBy);
			}
			
			PreparedStatement Pstmt=con.prepareStatement(sb.toString());
			System.out.println(sb.toString());
			//Pstmt=con.prepareStatement(sb.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=Pstmt.executeQuery();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	
		return rs;	
	}
	public ResultSet getDataSet(Connection con,String FieldNames,String TableName,String Where,String OrderBy,String pageNoOrderBy,int pageNo,int noOfRecords)
	{
		if (pageNo<=0 || noOfRecords<=0)
		{
			return getDataSet(con, FieldNames, TableName, Where, pageNoOrderBy);
		}
		int lastRecNo=pageNo*noOfRecords;
		int startRecNo=lastRecNo-noOfRecords+1;	
		//System.out.println("startRecNo"+startRecNo);
		System.out.println("lastRecNo"+lastRecNo);
		return getDataSet(con, FieldNames, TableName, Where, OrderBy, startRecNo, lastRecNo, pageNoOrderBy);
	}
	
	public ResultSet getNumberOfPages(Connection con,String TableName,String Where,String OrderBy,String pageNoOrderBy,int noOfRecords)
	{
		String FieldNames="ceiling(max(rowid)/"+noOfRecords+".0) as noOfPages";
		StringBuffer sb=new StringBuffer();
		ResultSet rs=null;
		try
		{
		sb.append(" Select " + FieldNames + " From ");
		sb.append("(select row_number() over (order by "+ pageNoOrderBy +") as rowid,* from " + TableName );
		if(!Where.equals(""))
		{
			sb.append(" where ");
			sb.append(Where);
		}
		sb.append(") as tempTable");
		PreparedStatement Pstmt=con.prepareStatement(sb.toString());
		rs=Pstmt.executeQuery();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public void executeDDLQuery(Connection con,String query) throws SQLException
	{
		Statement statement=null;		
		try 
		{
			statement=con.createStatement();
			statement.executeUpdate(query);
		} 
		catch (SQLException e) 
		{
			throw e;
		}
		finally
		{
			if (statement!=null) try{statement.close();}catch (Exception e2) {}
		}
	}
	
	public void executeDMLQuery(Connection con,String query)
	{
		Statement statement=null;
		try 
		{
			statement=con.createStatement();
			statement.executeUpdate(query);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if (statement!=null) try{statement.close();}catch (Exception e2) {}
		}
	}
	public ResultSet getDataSet2(Connection con,String FieldNames,String Where,String OrderBy)
	{
		ResultSet rs = null;
		try
		{
			
			StringBuffer sb=new StringBuffer();
			sb.append(" Select ");
			sb.append(FieldNames);
			
			if(!Where.equals(""))
			{
				sb.append(" Where ");
				sb.append(Where);
			}
			if(!OrderBy.equals(""))
			{
				sb.append(" Order By ");
				sb.append(OrderBy);
			}
			
			PreparedStatement Pstmt=con.prepareStatement(sb.toString());
			System.out.println(sb.toString());
			//Pstmt=con.prepareStatement(sb.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=Pstmt.executeQuery();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	
		return rs;	
	}
	public int getDataSet1(Connection con,String FieldName)
	{
		int rs = 0;
		try
		{
			PreparedStatement Pstmt=con.prepareStatement(FieldName);
			System.out.println(FieldName);
			//Pstmt=con.prepareStatement(sb.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=Pstmt.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	
		return rs;	
	}
}