package com.docmgmt.server.db.master;

import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class WorkSpaceNodeAttribute 
{
	
	ConnectionManager conMgr;
	DataTable  dataTable;
	public WorkSpaceNodeAttribute()
	{
		ConnectionManager conMgr=new ConnectionManager(new Configuration());
		dataTable=new DataTable();
	}
	
	
 
	 
 
	    	

}//main class end
