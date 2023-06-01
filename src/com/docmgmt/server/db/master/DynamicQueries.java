package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOAttrReferenceTableMatrix;
import com.docmgmt.dto.DTODynamicTable;
import com.docmgmt.dto.DTOImportedXLSMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class DynamicQueries
{
	DataTable datatable;
	
	public DynamicQueries()
	{
		datatable=new DataTable();
	}
	
	public String generateCreateQuery(String tableName,String[] columnNames) 
	{
		//adding identity column to the imported table
		String createQuery="create table " + tableName + " (rowid int identity,";
		int noOfColumns=columnNames.length;
		for (int idxCol=0;idxCol<noOfColumns;idxCol++)
			createQuery+=columnNames[idxCol] + " varchar(2000),";
		createQuery=createQuery.substring(0,createQuery.length()-1) + ")";
		System.out.println("create query: \n" + createQuery);
		return createQuery;
	}
	
	public String[] generateInsertQueries(Vector<Vector<String>> tableData,int noOfColumns,String tableName)
	{
		String[] insQueries=new String[tableData.size()];
		for(int idxRow=0;idxRow<tableData.size();idxRow++)
		{
			insQueries[idxRow]="insert into " + tableName + " values (";
			Vector<String> rowData=tableData.get(idxRow);
			for (int idxCol=0;idxCol<rowData.size();idxCol++)
			{
				insQueries[idxRow]+="'"+rowData.get(idxCol)+"',";
			}
			insQueries[idxRow]=insQueries[idxRow].substring(0,insQueries[idxRow].length()-1)+")";
		}
		return insQueries;
	}
	
	public boolean importMasterInDB(String tableName,String[] columnNames,Vector<Vector<String>> tableData)
	{
		String createQuery=generateCreateQuery(tableName, columnNames);
		String[] insertQueries=generateInsertQueries(tableData,columnNames.length,tableName); 
		Connection con=null;
		boolean errorFound=false;
		try
		{
			con=ConnectionManager.ds.getConnection();
			datatable.executeDDLQuery(con,createQuery);
			for (int idxInsertQuery=0;idxInsertQuery<insertQueries.length;idxInsertQuery++)
			{
				datatable.executeDDLQuery(con,insertQueries[idxInsertQuery]);
			}
		}
		catch(Exception e)
		{
			errorFound=true;
		}
		finally
		{
			if (con!=null) try{con.close();}catch(Exception e){}
		}
		return errorFound;
	}
	
	public int checkTableExists(String tableName)
	{
		int noOfRecs=0;
		Connection con=null;
		ResultSet rs=null;
		try
		{
			con=ConnectionManager.ds.getConnection();
			rs=datatable.getDataSet(con," * "," sys.tables "," name='" + tableName + "' ","");
			if (rs==null)
				noOfRecs=-1;
			else if (rs.next())
			{
				noOfRecs=1;				
			}
			else
			{
				noOfRecs=0;
			}
		}
		catch(Exception e)
		{
			noOfRecs=-1;
		}
		finally
		{
			if (rs!=null) try{rs.close();}catch(Exception e){}
			if (con!=null) try{con.close();}catch(Exception e){}
		}
		return noOfRecs;
	}

	public void insertIntoXlsMstList(String tableName,String uploadedFilePath)
	{
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String query="INSERT INTO ImportedXLSMst VALUES ('" + tableName + "','" + uploadedFilePath + "',''," + userCode + ",getdate(),'N')";		
		Connection con=null;
		ResultSet rs=null;
		try
		{
			con=ConnectionManager.ds.getConnection();
			datatable.executeDMLQuery(con, query);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (rs!=null) try{rs.close();}catch(Exception e){}
			if (con!=null) try{con.close();}catch(Exception e){}
		}
	}
	
	public ArrayList<DTOImportedXLSMst> getXLSMstList()
	{
		ArrayList<DTOImportedXLSMst> importedXLSMsts=new ArrayList<DTOImportedXLSMst>();
		Connection con=null;
		ResultSet rs=null;		
		try
		{
			con=ConnectionManager.ds.getConnection();
			rs=datatable.getDataSet(con," * "," ImportedXLSMst ","cStatusIndi<>'D'","dModifyOn desc");
			while(rs.next())
			{
				DTOImportedXLSMst dto=new DTOImportedXLSMst();
				dto.setvFileName(rs.getString("vFileName"));
				dto.setvTableName(rs.getString("vTableName"));
				dto.setiModifyBy(rs.getInt("iModifyBy"));
				dto.setdModifyOn(rs.getTimestamp("dModifyOn"));
				importedXLSMsts.add(dto);
			}
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			if (rs!=null) try{rs.close();}catch(Exception e){}
			if (con!=null) try{con.close();}catch(Exception e){}
		}
		return importedXLSMsts;
	}
	
	public DTODynamicTable getTableDetails(String tableName)
	{
		DTODynamicTable dtoDynamicTable=new DTODynamicTable();
		Connection con=null;
		ResultSet rs=null;
		ResultSet rs1=null;
		try
		{
			dtoDynamicTable.setTableName(tableName);
			con=ConnectionManager.ds.getConnection();
			rs=datatable.getDataSet(con,"top 1 * "," " + tableName + " ","","");
			rs1=datatable.getDataSet(con," count(*) "," " + tableName + " ","","");
			if (rs==null || rs1==null)
				return null;
			if (rs1.next())
				dtoDynamicTable.setNoOfRecords(rs1.getInt(1));
			ResultSetMetaData metaData=rs.getMetaData();
			int colcnt=metaData.getColumnCount();
			dtoDynamicTable.setNoOfColumns(colcnt);
			ArrayList<String> colNames=new ArrayList<String>();
			for (int i=1;i<=colcnt;i++)
				colNames.add(metaData.getColumnName(i));
			dtoDynamicTable.setColumnNames(colNames);			
		}
		catch (Exception e) 
		{
			dtoDynamicTable=null;
		}
		finally
		{
			if (rs!=null) try{rs.close();}catch(Exception e){}
			if (rs1!=null) try{rs1.close();}catch(Exception e){}
			if (con!=null) try{con.close();}catch(Exception e){}
		}
		return dtoDynamicTable;
	}
	
	public DTODynamicTable getCompleteTableDetails(String tableName)
	{
		DTODynamicTable dtoDynamicTable=new DTODynamicTable();
		Connection connnection=null;
		ResultSet rsTable=null;
		ResultSet rsRecordCount=null;
		try
		{
			dtoDynamicTable.setTableName(tableName);
			connnection=ConnectionManager.ds.getConnection();
			rsTable=datatable.getDataSet(connnection," * "," " + tableName + " ","","");
			rsRecordCount=datatable.getDataSet(connnection," count(*) "," " + tableName + " ","","");
			if (rsRecordCount.next())
				dtoDynamicTable.setNoOfRecords(rsRecordCount.getInt(1));
			ResultSetMetaData metaData=rsTable.getMetaData();
			int colcnt=metaData.getColumnCount();
			dtoDynamicTable.setNoOfColumns(colcnt);
			ArrayList<String> colNames=new ArrayList<String>();
			for (int i=1;i<=colcnt;i++)
				colNames.add(metaData.getColumnName(i));
			dtoDynamicTable.setColumnNames(colNames);			
			ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
			while(rsTable.next())
			{
				ArrayList<String> recordData=new ArrayList<String>();
				for (int i=1;i<=colcnt;recordData.add(rsTable.getString(i)),i++);
					tableData.add(recordData);
			}		
			dtoDynamicTable.setTableData(tableData);
		}
		catch (Exception e) 
		{
			
		}
		finally
		{
			if (rsTable!=null) try{rsTable.close();}catch(Exception e){}
			if (rsRecordCount!=null) try{rsRecordCount.close();}catch(Exception e){}
			if (connnection!=null) try{connnection.close();}catch(Exception e){}
		}
		return dtoDynamicTable;
	}
	
	public boolean updateRecord(String tableName,String oldValues[],String newValues[])
	{
		boolean errorFound=false;
		DTODynamicTable dtoDynamicTable=getTableDetails(tableName);
		String setString="";
		String whereString="";
		String updQuery="update " + tableName + " set ";
		ArrayList<String> colNames=dtoDynamicTable.getColumnNames();
		Connection connnection=null;
		
		for (int i=0;i<dtoDynamicTable.getNoOfColumns();i++)
		{
			setString += colNames.get(i) + "='" + newValues[i] + "',";
			whereString += colNames.get(i) + "='" + oldValues[i] + "' and ";
		}
		setString=setString.substring(0,setString.length()-1);
		whereString=whereString.substring(0,whereString.length()-5);
		updQuery+=setString;
		updQuery+=" where " + whereString;
		try
		{
			connnection=ConnectionManager.ds.getConnection();
			datatable.executeDMLQuery(connnection, updQuery);
		}
		catch (Exception e) 
		{
			errorFound=true;
		}
		finally
		{
			if (connnection!=null) try{connnection.close();}catch(Exception e){}
		}
		return errorFound;
	}
	
	public boolean addRecord(String tableName,String newValues[])
	{
		boolean errorFound=false;
		String valString="";
		String insQuery="insert into " + tableName + " values(";
		Connection connnection=null;
		
		for (int i=0;i<newValues.length;i++)
			valString += "'" + newValues[i] + "',";			
		valString=valString.substring(0,valString.length()-1);
		insQuery += valString + ")";
		try
		{
			connnection=ConnectionManager.ds.getConnection();
			datatable.executeDMLQuery(connnection, insQuery);
		}
		catch (Exception e) 
		{
			errorFound=true;
		}
		finally
		{
			if (connnection!=null) try{connnection.close();}catch(Exception e){}
		}
		return errorFound;
	}

	public void Insert_AttrReferenceTableMatrix(DTOAttrReferenceTableMatrix dtoAttrReferenceTableMatrix, int mode)
	{
		Connection connection = null;
		CallableStatement statement = null;
		try 
		{
			connection = ConnectionManager.ds.getConnection();
			statement = connection.prepareCall("{ Call Insert_AttrReferenceTableMatrix(?,?,?,?,?,?,?,?,?) }");
			statement.setInt(1,dtoAttrReferenceTableMatrix.getiAttrId());
			statement.setString(2,dtoAttrReferenceTableMatrix.getvTableName());
			statement.setString(3,dtoAttrReferenceTableMatrix.getvTableColumn());
			statement.setString(4,dtoAttrReferenceTableMatrix.getvTableType());
			statement.setString(5,dtoAttrReferenceTableMatrix.getvRemark());
			statement.setInt(6,dtoAttrReferenceTableMatrix.getiModifyBy());
			statement.setTimestamp(7,dtoAttrReferenceTableMatrix.getdModifyOn());
			statement.setString(8,dtoAttrReferenceTableMatrix.getcStatusIndi());
			statement.setInt(9,mode);
			statement.execute();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if (statement != null) try{statement.close();}catch(Exception e){}
			if (connection != null) try{connection.close();}catch(Exception e){}
		}
	}

	public ArrayList<DTOAttrReferenceTableMatrix> getFromAttrReferenceTableMatrix (DTOAttrReferenceTableMatrix dtoAttrReferenceTableMatrix)
	{
		ArrayList<DTOAttrReferenceTableMatrix> attrReferenceTableMatrixs = new ArrayList<DTOAttrReferenceTableMatrix>();
		Connection connection = null;
		ResultSet resultSet = null;
		String whereClause = "";
		if (dtoAttrReferenceTableMatrix.getiAttrId() != 0)
			whereClause += "iAttrId = " + dtoAttrReferenceTableMatrix.getiAttrId();
		
		if (dtoAttrReferenceTableMatrix.getvTableName() != null && !dtoAttrReferenceTableMatrix.getvTableName().equals(""))
			whereClause += " and vTableName = '" + dtoAttrReferenceTableMatrix.getvTableName() + "'";
		
		if (dtoAttrReferenceTableMatrix.getvTableColumn() != null && !dtoAttrReferenceTableMatrix.getvTableColumn().equals(""))
			whereClause += " and vTableColumn = '" + dtoAttrReferenceTableMatrix.getvTableColumn() + "'";
		
		if (dtoAttrReferenceTableMatrix.getvTableType() != null && !dtoAttrReferenceTableMatrix.getvTableType().equals(""))
			whereClause += " and vTableType = '" + dtoAttrReferenceTableMatrix.getvTableType() + "'";
		
		if (dtoAttrReferenceTableMatrix.getiModifyBy() != 0)
			whereClause += " and iModifyBy = " + dtoAttrReferenceTableMatrix.getiModifyBy();
		
		if (whereClause.startsWith(" and "))
			whereClause = whereClause.replaceFirst("and","");
		
		try
		{
			connection = ConnectionManager.ds.getConnection();
			resultSet = datatable.getDataSet(connection, "*", "AttrReferenceTableMatrix", whereClause, "");
			while (resultSet.next())
			{
				DTOAttrReferenceTableMatrix dtoAttrReferenceTableMatrix2 = new DTOAttrReferenceTableMatrix();
				dtoAttrReferenceTableMatrix2.setiAttrId(resultSet.getInt("iAttrId"));
				dtoAttrReferenceTableMatrix2.setvTableName(resultSet.getString("vTableName"));
				dtoAttrReferenceTableMatrix2.setvTableColumn(resultSet.getString("vTableColumn"));
				dtoAttrReferenceTableMatrix2.setvTableType(resultSet.getString("vTableType"));
				dtoAttrReferenceTableMatrix2.setvRemark(resultSet.getString("vRemark"));
				dtoAttrReferenceTableMatrix2.setiModifyBy(resultSet.getInt("iModifyBy"));
				dtoAttrReferenceTableMatrix2.setdModifyOn(resultSet.getTimestamp("dModifyOn"));
				dtoAttrReferenceTableMatrix2.setcStatusIndi(resultSet.getString("cStatusIndi"));
				attrReferenceTableMatrixs.add(dtoAttrReferenceTableMatrix2);
			}
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			if (connection != null)	{try{connection.close();}catch(Exception e){}}
			if (resultSet != null){	try{resultSet.close();}catch(Exception e){}}
		}
		return attrReferenceTableMatrixs;
	}
}