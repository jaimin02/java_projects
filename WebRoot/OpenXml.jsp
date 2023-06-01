
<%@ page language="java"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.io.File.*"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="com.docmgmt.server.webinterface.beans.*"%>


<%@ page import="java.util.logging.*"%>


<%!
	File fFile = null;
	String stFileName = "";
	String folderName = "";
	String fileName = "";


int ibit = 256;
%>


<%	
String fileName = (String)request.getParameter("filePath");
		
%>


<%	
String char1="\\";
String char2="\\\\";
fileName=fileName.replace(char1,char2);
System.out.println("FilePathName: " + fileName);
	fFile = new File (fileName);
%>


<%	
	if(fileName !=null)
	{	
		if(fileName.endsWith(".pdf")){
			
			response.setContentType ("application/pdf");
		}else{
			response.setContentType ("application/octet-stream");
		}
	}
	response.setHeader ("Content-Disposition", "filename=\""+fileName+"\"");
	InputStream isStream = null;
	ServletOutputStream sosStream = null;
  	try
  	{
		isStream = new FileInputStream(fFile);
		sosStream = response.getOutputStream();				
		
		 int availableLength = isStream.available();
         byte[] totalBytes = new byte[availableLength];
         int bytedata = isStream.read(totalBytes);
         sosStream.write(totalBytes);

	}
	catch (IOException ioeException)
	{
    }
	if(sosStream != null){
		sosStream.flush();
		sosStream.close();
	}
	if(isStream != null){
	    isStream.close();
	}
    
    
%>






