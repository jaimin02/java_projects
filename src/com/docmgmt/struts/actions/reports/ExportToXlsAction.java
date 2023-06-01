/**
 * 
 */
package com.docmgmt.struts.actions.reports;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;

/**
 * This class is used to generate InputStream to return as excel file
 * @author Nagesh
 *
 */
public class ExportToXlsAction extends ActionSupport 
{
	private InputStream xlsStream;
	private String tabText;	
	private String fileName;

	public String getFileName() {
		
		if (fileName == null || fileName.trim().equals("")) {
			fileName = "report.xls";
		}
		else if(fileName.endsWith(".docx"))
			fileName = fileName;
		else if (!fileName.endsWith(".xls"))
			fileName = fileName+ ".xls";
		
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public InputStream getXlsStream() {
		return xlsStream;
	}

	public void setXlsStream(InputStream xlsStream) {
		this.xlsStream = xlsStream;
	}

	public String getTabText() {
		return tabText;
	}

	public void setTabText(String tabText) {
		this.tabText = tabText;
	}

	public ExportToXlsAction()
	{
 		
	}
	
	@Override
	public String execute() throws Exception 
	{
		//System.out.println("tabt:" + getTabText());
		//Remove all the hyper links.
		String strTable = getTabText().replace("\n", "").replace("\r", "");
		strTable =strTable.replaceAll("\\s{2,}", " ").trim();
		//System.out.println("strTable:" + strTable);
		strTable = strTable.replaceAll("href", "");
		//strTable = strTable.replaceAll("<a id=.*?<\\/a>","-");
		//strTable = strTable.replaceAll("<a .*?<\\/a>","-");
		//System.out.println("imageTable:" + strTable);
		strTable = strTable.replaceAll("<td class=\"rmvCol\".*?<\\/td>","");
		strTable = strTable.replaceAll("<TD class=\"rmvCol\".*?<\\/TD>","");
		strTable = strTable.replaceAll("<Td class=\"rmvCol\".*?<\\/Td>","");
		strTable = strTable.replaceAll("<th class=\"rmvCol\".*?<\\/th>","");
		strTable = strTable.replaceAll("<TH class=\"rmvCol\".*?<\\/TH>","");
		strTable = strTable.replaceAll("<Th class=\"rmvCol\".*?<\\/Th>","");
		strTable = strTable.replaceAll("<tr class=\"odd rmvCol\".*?<\\/tr>","");
		strTable = strTable.replaceAll("<tr class=\"even rmvCol\".*?<\\/tr>","");
		//System.out.println("strTable:"+strTable);
		xlsStream = new ByteArrayInputStream(strTable.getBytes());
		
		System.out.println(new ByteArrayInputStream(strTable.getBytes()));
		return SUCCESS;
	}
}
