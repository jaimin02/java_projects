package Test;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.jdo.annotations.Column;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
public class Excel {
	
	
	public static void main(String[] args) throws InvalidFormatException, FileNotFoundException, IOException
	{
	Workbook wb = WorkbookFactory.create(new FileInputStream("D:\\Samarth\\sample.xls"));
	Sheet sheet = wb.getSheetAt(0);
	Cell cell;
	Column column;
	Row row= sheet.getRow(0);
	int numXolumns = row.getLastCellNum();

	for(int col=0; col< numXolumns; col++)
	{
	       cell = row.getCell(col);

	       int type = cell.getCellType();
	       if (type == HSSFCell.CELL_TYPE_BLANK) 
	       {
	           System.out.println("[" + cell.getRowIndex() + ", "+ cell.getColumnIndex() + "] = BLANK CELL"+ cell.toString());
	           int Delete_column_index=cell.getColumnIndex();
	       }

	  }
	FileOutputStream fileOut = new FileOutputStream("Output_Excel.xls");
	wb.write(fileOut);
	fileOut.close();
	}
	
}
