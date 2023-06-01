package com.docmgmt.struts.actions.ImportProjects.xls;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Vector;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.docmgmt.dto.DTODynamicTable;
import com.docmgmt.dto.DTOImportedXLSMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.opensymphony.xwork2.ActionSupport;

public class ImportXlsFile extends ActionSupport
{
	public File xlsDoc;
	public String xlsDocFileName;
	public HSSFWorkbook xlsWorkbook;
	public int noOfColumns;
	public String[] columnNames;
	public String createQuery;
	public String tableName;
	public String xlsFolder;
	public Vector<Vector<String>> tableData;
	public String[] insQueries;
	public String pathSepartor;
	public String uploadedFilePath;
	public String errorText;
	public Vector<String> columnNamesVect;
	public String htmlContent;
	public int resultCode;
	public HSSFSheet currentSheet;
	public String stdDatatype;
	public ArrayList<DTOImportedXLSMst> xlsTableList;
	public DTODynamicTable tableDetails;
	public int noOfRecords;
	public String oldValues[];
	public String newValues[];	
	public String addAttr;
	
	private DocMgmtImpl docMgmtImpl;
	
	private final int XLS_IS_OK=0;	
	private final int BLANK_ROW_MISSING=1;
	private final int ROWS_NO_INSUFFICIENT=2;
	private final int DATA_IS_MISSING=3;
	private final int HEADER_ROW_MISSING=4;		
	
	private final int HEADER_ROW=0; 
	private final int BLANK_ROW=1;
	private final int FIRST_DATA_ROW=2;
	
	public void getSheet()
	{
		currentSheet=null;		
		if (xlsWorkbook==null)
		{
			try 
			{
				getWorkBook();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return;
			}			
			if (xlsWorkbook==null)
				return;
		}
		if (currentSheet==null)
			currentSheet = xlsWorkbook.getSheetAt(0);		
	}
	
	public boolean isRowWithoutData(int rowNo)
	{		 
		if (currentSheet==null)
			getSheet();
		boolean blankRow=false;
		HSSFRow hdrRow = currentSheet.getRow(rowNo);
		if (hdrRow==null)
			blankRow=true;
		if (hdrRow!=null)
		{
			int noOfColumns = hdrRow.getPhysicalNumberOfCells();
			if (noOfColumns<=0)
				blankRow=true;
			if (noOfColumns>0)
			{
				boolean errorFound=false;
				for (int idxCell=0;idxCell<noOfColumns;idxCell++)
				{
					HSSFCell currCell = hdrRow.getCell(idxCell);
					if (currCell == null)
					{
						errorFound = true;
						break;
					}
				}
				blankRow = errorFound;
			}			
		}		
		return blankRow;
	}
	
	public void validateXlsFile()
	{
		if (currentSheet==null)
			getSheet();		
		resultCode=-1;
		if (xlsWorkbook==null)
			return;		
		if (currentSheet!=null)
		{
			resultCode=XLS_IS_OK;
			int rows = currentSheet.getPhysicalNumberOfRows();
			if (rows == 0 || rows < 2)
				resultCode=ROWS_NO_INSUFFICIENT;
			else if (isRowWithoutData(HEADER_ROW))
				resultCode=HEADER_ROW_MISSING;
			else if (!isRowWithoutData(BLANK_ROW))
				resultCode=BLANK_ROW_MISSING;
			else if (isRowWithoutData(FIRST_DATA_ROW))
				resultCode=DATA_IS_MISSING;
		}	
	}
	
	public String checkTableExists()
	{
		int noOfRecs=docMgmtImpl.checkTableExists(tableName);
		if (noOfRecs<0 || noOfRecs>0)
			htmlContent="<font color='red'>Error: May be " + tableName + " is not available!</font>";
		else
			htmlContent=tableName + " is available!";
		return SUCCESS;
	}
	
	public ImportXlsFile()
	{
		pathSepartor="/";
		KnetProperties knetProperties=KnetProperties.getPropInfo();
		xlsFolder=knetProperties.getValue("XLSFolder");
		if (xlsFolder==null)
			xlsFolder="/home/data/XLSFiles";
		if (xlsFolder.endsWith(pathSepartor))
			xlsFolder=xlsFolder.substring(0,xlsFolder.length()-1);		
		noOfColumns=0;
		columnNamesVect=null;
		errorText="Unknown Error!";	
		stdDatatype="varchar(2000)";
		docMgmtImpl=new DocMgmtImpl();
	}
	
	public void copyUploadedFile()
	{
		uploadedFilePath="";
		if (xlsFolder==null || xlsFolder.equals(""))
			return;
		if (xlsDocFileName==null || xlsDocFileName.equals(""))
			return;
		if (pathSepartor==null || pathSepartor.equals(""))
			return;
		if (xlsDoc==null)
			return;
		uploadedFilePath=xlsFolder + pathSepartor + xlsDocFileName;
		FileManager fileManager=new FileManager();
		fileManager.copyDirectory(xlsDoc,new File(uploadedFilePath));
	}
	
	public void getColumnNames() throws Exception 
	{
		columnNamesVect=null;
		if (xlsWorkbook==null)
			return;
		if (currentSheet==null)
			getSheet();
		if (currentSheet!=null)
		{
			int rows = currentSheet.getPhysicalNumberOfRows();
			if (rows==0)
				return;
			columnNamesVect=new Vector<String>();
			HSSFRow hdrRow = currentSheet.getRow(HEADER_ROW);
			if (hdrRow!=null)
			{
				noOfColumns = hdrRow.getPhysicalNumberOfCells();
				boolean errorFound=false;
				for (int idxCell=0;idxCell<noOfColumns;idxCell++)
				{
					HSSFCell currCell = hdrRow.getCell(idxCell);
					if (currCell!=null)
					{
						if (currCell.getCellType()==Cell.CELL_TYPE_STRING)
							columnNamesVect.add(currCell.getStringCellValue());
						else
						{
							errorFound=true;
							break;
						}
					}
				}
				if (errorFound)
				{
					columnNamesVect=null;
				}				
			}		
		}		
		if (columnNamesVect!=null)
		{
			columnNames=new String[columnNamesVect.size()];			
			for (int idxCol=0;idxCol<columnNamesVect.size();idxCol++)
				columnNames[idxCol]=columnNamesVect.get(idxCol);
		}
	}
	
	public void generateCreateQuery() throws Exception 
	{
		createQuery="";
		if (columnNames==null || columnNames.length==0)
			return;
		if (tableName==null || tableName.length()==0 || tableName.equals(""))
			return;
		if (noOfColumns==0)
		{
			noOfColumns=columnNames.length;
		}
		createQuery="create table " + tableName + " (";
		for (int idxCol=0;idxCol<noOfColumns;idxCol++)
			createQuery+=columnNames[idxCol] + " " + stdDatatype + ",";
		createQuery=createQuery.substring(0,createQuery.length()-1) + ")";
	}
	
	public void generateInsertQueries() throws Exception
	{
		insQueries=null;
		if(xlsWorkbook==null)
			return;
		if (noOfColumns==0)
			return;
		if (tableName==null || tableName.equals(""))
			return;
		if (currentSheet==null)
			getSheet();
		currentSheet = xlsWorkbook.getSheetAt(0);
		int rows = currentSheet.getPhysicalNumberOfRows();
		insQueries=new String[rows-1];
		for (int idxRow=1;rows>1 && idxRow<rows;idxRow++)
		{
			insQueries[idxRow-1]="insert into " + tableName + " values (";
			HSSFRow currRow = currentSheet.getRow(idxRow);
			if (currRow != null) 
			{					
				int cntValidCells=0;
				for (int idxCell=0;idxCell<noOfColumns;idxCell++)
				{
					HSSFCell currCell = currRow.getCell(idxCell);
					if (currCell!=null)
					{
						if (currCell.getCellType()==Cell.CELL_TYPE_STRING)
						{	
							String cellValue=currCell.getStringCellValue();
							insQueries[idxRow-1]+="'"+cellValue+"',";
							cntValidCells++;
						}
						else if (currCell.getCellType()==Cell.CELL_TYPE_NUMERIC)
						{	
							double cellValue=currCell.getNumericCellValue();
							insQueries[idxRow-1]+="'"+new Double(cellValue).toString()+"',";
							cntValidCells++;
						}
						else if (currCell.getCellType()==Cell.CELL_TYPE_BLANK)
							insQueries[idxRow-1]+="'',";
						else if (currCell.getCellType()==Cell.CELL_TYPE_ERROR)
							insQueries[idxRow-1]+="'',";
						else
							insQueries[idxRow-1]+="'',";
					}
					else
						insQueries[idxRow-1]+="'',";
				}
				insQueries[idxRow-1] = insQueries[idxRow-1].substring(0,insQueries[idxRow-1].length()-1) + ")";
			}
		}
	}
	
	public String dbImport() throws Exception 
	{		
		getWorkBook();		
		if (xlsWorkbook==null)
		{
			errorText="Cannot Generate Workbook object!";
			return "error";
		}
		noOfColumns=columnNames.length;
		getTableData(0);
		if (tableData==null)
		{
			errorText="Cannot Generate Table Data!";
			return "error";
		}
		if (docMgmtImpl.importMasterInDB(tableName,columnNames,tableData))
		{
			errorText="Error in creating table/inserting data !!!";
			return "error";
		}
		docMgmtImpl.insertIntoXlsMstList(tableName,uploadedFilePath.substring(uploadedFilePath.lastIndexOf(pathSepartor)+1));
		xlsTableList = docMgmtImpl.getXLSMstList();		
		return SUCCESS;
	}
		
	public String initialUpload() throws Exception
	{
		//copy uploaded file from temporary location to proper location
		copyUploadedFile();
		if (uploadedFilePath.equals(""))
		{
			errorText="Cannot Generate File Path!";
			return "error";
		}
		generateTableName();
		if (tableName==null || tableName.equals(""))
		{
			errorText="Cannot Generate Table Name!";
			return "error";			
		}		
		getWorkBook();		
		if (xlsWorkbook==null)
		{
			errorText="Cannot Generate Table Name!";
			return "error";
		}
		validateXlsFile();
		if (resultCode==-1)
		{
			errorText="Invalid XLS file!";
			return "error";
		}
		if (resultCode!=XLS_IS_OK)
		{
			switch (resultCode) 
			{
				case ROWS_NO_INSUFFICIENT:
					errorText="Insufficient number of rows in XLS file!";
					break;
				case BLANK_ROW_MISSING:
					errorText="Mandatory blank row is missing in XLS file!";
					break;	
				case DATA_IS_MISSING:
					errorText="There is no data in XLS file!";
					break;
				case HEADER_ROW_MISSING:
					errorText="There is no header row in XLS file!";
					break;
				default:			
					errorText="Unknown error in XLS file!";
					break;							
			}
			return "error";
		}
		getColumnNames();
		if (columnNamesVect==null)
		{
			errorText="Cannot Generate Column Names!";
			return "error";	
		}
		getTableData(3);
		if (tableData==null)
		{
			errorText="Cannot Generate Table Data!";
			return "error";
		}
		return "initialUpload";
	}

	public void generateTableName()
	{
		tableName="";
		if (pathSepartor==null || pathSepartor.equals(""))
			return;
		if (xlsDocFileName==null || xlsDocFileName.equals(""))
			return;
		if (uploadedFilePath==null || uploadedFilePath.equals(""))
			return;		
		String str=xlsDocFileName.length()==0?uploadedFilePath:xlsDocFileName;
		tableName=str.substring(str.lastIndexOf(pathSepartor)+1,str.lastIndexOf('.'));
	}
	
	public void getWorkBook() throws Exception
	{
		xlsWorkbook=null;
		if (uploadedFilePath==null || uploadedFilePath.equals(""))
			return;
		if (xlsWorkbook==null)
			xlsWorkbook = new HSSFWorkbook(new FileInputStream(uploadedFilePath));
	}
	
	public int getTableData(int numOfRecords)
	{
		tableData=null;
		if(xlsWorkbook==null)
			return -1;
		if (noOfColumns==0)
			return -1;
		tableData=new Vector<Vector<String>>();
		int idxRow=1;
		if (currentSheet==null)
			getSheet();
		int rows = currentSheet.getPhysicalNumberOfRows();
		for (idxRow=FIRST_DATA_ROW;rows>1 && idxRow<=rows && (idxRow<=numOfRecords+1 || numOfRecords==0);idxRow++)
		{				
			if (isRowWithoutData(idxRow))
				continue;
			HSSFRow currRow = currentSheet.getRow(idxRow);
			if (currRow != null) 
			{					
				Vector<String> rowData=new Vector<String>();
				int cntValidCells=0;
				for (int idxCell=0;idxCell<noOfColumns;idxCell++)
				{
					HSSFCell currCell = currRow.getCell(idxCell);
					if (currCell!=null)
					{
						if (currCell.getCellType()==Cell.CELL_TYPE_STRING)
						{	
							String cellValue=currCell.getStringCellValue();
							rowData.add(cellValue);
							cntValidCells++;
						}
						else if (currCell.getCellType()==Cell.CELL_TYPE_NUMERIC)
						{	
							double cellValue=currCell.getNumericCellValue();
							rowData.add(new Double(cellValue).toString());
							cntValidCells++;
						}
						else if (currCell.getCellType()==Cell.CELL_TYPE_BLANK)
							rowData.add("");
						else if (currCell.getCellType()==Cell.CELL_TYPE_ERROR)
							rowData.add("");
						else
							rowData.add("");
					}
					else
						rowData.add("");
				}
				tableData.add(rowData);
			}
		}		
		return idxRow;
	}
	
	@Override
	public String execute() throws Exception 
	{		
		return SUCCESS;
	}
	
	public String xlsTableList() throws Exception
	{
		xlsTableList = docMgmtImpl.getXLSMstList();
		return SUCCESS;
	}
	
	public String showTableDetails() throws Exception
	{
		tableDetails=docMgmtImpl.getTableDetails(tableName);		
		System.out.println("addAttr" + addAttr);
		if (addAttr == null || addAttr.equals("no") || addAttr.equals("No"))
			return SUCCESS;
		if (addAttr != null || addAttr.equals("yes") || addAttr.equals("Yes"))
		{
			htmlContent = "";
			for (int indAttr = 1;indAttr < tableDetails.getColumnNames().size();indAttr++)
				htmlContent += "<input type='radio' name='attrColumn' value='" + tableDetails.getColumnNames().get(indAttr) + "'" +
						((indAttr == 1)? " checked = 'checked'" : "")
						+ ">" + tableDetails.getColumnNames().get(indAttr) + "<br>";
			System.out.println("htmlcontent" + htmlContent);
			return "addAttr";
		}
		return SUCCESS;
	}
	
	public String displayTableData() throws Exception
	{
		tableDetails=docMgmtImpl.getCompleteTableDetails(tableName);
		return SUCCESS;
	}
	
	public String saveTableRecord() throws Exception
	{
		System.out.println(oldValues.length);
		for (int i=0;i<oldValues.length;i++)
			System.out.println(oldValues[i]);
		System.out.println(newValues.length);
		for (int i=0;i<newValues.length;i++)
			System.out.println(newValues[i]);
		htmlContent=(docMgmtImpl.updateRecord(tableName, oldValues, newValues))?"Updation Failed!!!":"Updation Successful!!!";
		return SUCCESS;
	}
	
	public String addTableRecord() throws Exception
	{
		htmlContent=(docMgmtImpl.addRecord(tableName,newValues))?"Addition Failed!!!":"Addition Successful!!!";
		return SUCCESS;
	}
	
	public static void main(String args[])
	{
		ImportXlsFile importXlsFile=new ImportXlsFile();
		importXlsFile.xlsDocFileName="/root/Desktop/test.xls";
		try
		{			
			importXlsFile.execute();
			importXlsFile.getTableData(0);
			for (int i=0;i<importXlsFile.tableData.size();i++)
			{
				Vector<String> v=importXlsFile.tableData.get(i);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}