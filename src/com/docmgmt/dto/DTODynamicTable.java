package com.docmgmt.dto;

import java.util.ArrayList;

public class DTODynamicTable
{
	private String tableName;
	private int noOfColumns;
	private int noOfRecords;
	private ArrayList<String> columnNames;
	private ArrayList<ArrayList<String>> tableData;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public int getNoOfColumns() {
		return noOfColumns;
	}
	public void setNoOfColumns(int noOfColumns) {
		this.noOfColumns = noOfColumns;
	}
	public int getNoOfRecords() {
		return noOfRecords;
	}
	public void setNoOfRecords(int noOfRecords) {
		this.noOfRecords = noOfRecords;
	}
	public ArrayList<String> getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(ArrayList<String> columnNames) {
		this.columnNames = columnNames;
	}
	public ArrayList<ArrayList<String>> getTableData() {
		return tableData;
	}
	public void setTableData(ArrayList<ArrayList<String>> tableData) {
		this.tableData = tableData;
	}	
}