package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOApplicationGroupMst;
import com.docmgmt.dto.DTOClientMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class ApplicationGroupMst {

	
	DataTable datatable;
	public ApplicationGroupMst()
	{
		 datatable=new DataTable();
	}

	public Vector<DTOApplicationGroupMst> getApplicationHostingDetail() {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOApplicationGroupMst> clientVector = new Vector<DTOApplicationGroupMst>();
		Connection con = null;
		ResultSet rs = null;
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		try 
		{		  
			con = ConnectionManager.ds.getConnection();
			rs=datatable.getDataSet(con,"*","ApplicationHostMst" ,"","vHostingCode");
			while(rs.next())
			{
				DTOApplicationGroupMst dto = new DTOApplicationGroupMst();
				dto.setHostingCode(rs.getString("vHostingCode"));
				dto.setHostingName(rs.getString("vHostingName"));/*
				dto.setRemark(rs.getString("vRemark"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}*/
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				clientVector.addElement(dto);
			}
		}   
		catch(SQLException e){
			e.printStackTrace();
		}finally{
	   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
	   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
	   	}
		return clientVector;
	}
	    	
	public Vector<DTOApplicationGroupMst> getApplicationCategoryDetail() {

		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		Vector<DTOApplicationGroupMst> clientVector = new Vector<DTOApplicationGroupMst>();
		Connection con = null;
		ResultSet rs = null;
		ArrayList<String> time = new ArrayList<String>();
		String locationName = ActionContext.getContext().getSession().get("locationname").toString();
		String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
		try 
		{		  
			con = ConnectionManager.ds.getConnection();
			rs=datatable.getDataSet(con,"*","ApplicationCategoryMst" ,"","vCategoryCode");
			while(rs.next())
			{
				DTOApplicationGroupMst dto = new DTOApplicationGroupMst();
				dto.setCategoryCode(rs.getString("vCategoryCode"));
				dto.setCategoryName(rs.getString("vCategoryName"));/*
				dto.setRemark(rs.getString("vRemark"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				if(countryCode.equalsIgnoreCase("IND")){
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
				}
				else{
					time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
					dto.setISTDateTime(time.get(0));
					dto.setESTDateTime(time.get(1));
				}*/
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				clientVector.addElement(dto);
			}
		}   
		catch(SQLException e){
			e.printStackTrace();
		}finally{
	   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
	   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
	   	}
		return clientVector;
	}
	
public Vector<DTOApplicationGroupMst> getApplicationDetail() {

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOApplicationGroupMst> clientVector = new Vector<DTOApplicationGroupMst>();
	Connection con = null;
	ResultSet rs = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","View_Applicationmst" ,"","VApplicationCode");
		while(rs.next())
		{
			DTOApplicationGroupMst dto = new DTOApplicationGroupMst();
			dto.setApplicationCode(rs.getString("vApplicationCode"));
			dto.setApplicationName(rs.getString("vApplicationName"));
			dto.setHostingName(rs.getString("vHostingName"));
			dto.setCategoryName(rs.getString("vCategoryName"));
			dto.setHostingCode(rs.getString("vHostingCode"));
			dto.setCategoryCode(rs.getString("vCategoryCode"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			clientVector.addElement(dto);
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return clientVector;
}

public Vector<DTOApplicationGroupMst> getApplicationDetailForProject() {

	Vector<DTOApplicationGroupMst> clientVector = new Vector<DTOApplicationGroupMst>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","Applicationmst" ,"cstatusindi<>'D'","VApplicationCode");
		while(rs.next())
		{
			DTOApplicationGroupMst dto = new DTOApplicationGroupMst();
			dto.setApplicationCode(rs.getString("vApplicationCode"));
			dto.setApplicationName(rs.getString("vApplicationName"));
			clientVector.addElement(dto);
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return clientVector;
}

public DTOApplicationGroupMst getApplicationDetail(String applicationCode) {

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	DTOApplicationGroupMst dto = new DTOApplicationGroupMst();
	Connection con = null;
	ResultSet rs = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","Applicationmst" ,"vapplicationcode = '" + applicationCode + "'","VApplicationCode");
		while(rs.next())
		{
			dto.setApplicationCode(rs.getString("vApplicationCode"));
			dto.setApplicationName(rs.getString("vApplicationName"));
			dto.setHostingCode(rs.getString("vHosting"));
			dto.setCategoryCode(rs.getString("vCategory"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return dto;
}
    
public int getMaxTranForApplicationAttachment(String applicationCode) {

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	int tranNo=0;
	Connection con = null;
	ResultSet rs = null;
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"max(itranno) as iTranNo ","ApplicationAttachment" ,"vapplicationcode = '" + applicationCode + "'","");
		while(rs.next())
		{
			tranNo = rs.getInt("iTranNo");
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return tranNo;
}

public void  ApplicationMstOp(DTOApplicationGroupMst dto,int Mode,boolean isrevert)
{
	Connection con = null;
	CallableStatement proc = null;
	try
	{	    	
		con=ConnectionManager.ds.getConnection();
		proc = con.prepareCall("{ Call Insert_applicationMst(?,?,?,?,?,?,?,?)}");
		proc.setString(1,dto.getApplicationCode());
		proc.setString(2,dto.getApplicationName());
		proc.setString(3,dto.getHostingCode());
		proc.setString(4,dto.getCategoryCode());
		proc.setString(5,dto.getRemark() );
		proc.setInt(6,dto.getModifyBy());
		if(isrevert==false)
		{
			if(Mode==1)
				proc.setString(7,"N");
			else
				proc.setString(7,"E");	
		}
		else //if revert is true
		{
			char statusIndi=dto.getStatusIndi();
			if('D'==statusIndi)
			{
				statusIndi='A';
			}
			else statusIndi='D';
		    	 proc.setString(7,Character.toString(statusIndi));
		}
		proc.setInt(8,Mode); 
		proc.executeUpdate();
	}
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(proc != null){proc.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
}
 
public DTOClientMst getClientInfo(String ClientCode)
{
	DTOClientMst dto = new DTOClientMst();
	Connection con = null;
	ResultSet rs = null;
	
	try 
	{		  
		String whr="vClientCode="+ClientCode;
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","ClientMst" ,whr,"");
		if(rs.next())
		{
			dto.setClientCode(rs.getString("vClientCode"));
			dto.setClientName(rs.getString("vClientName"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return dto;
}
public String getClientName(String ClientCode) {
	String clientName = "";
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = datatable.getDataSet(con, "vClientName",
				"ClientMst", "vClientCode = '" + ClientCode + "'",
				"");
		if (rs.next()) {
			clientName = rs.getString("vClientName");
		}
		rs.close();
		con.close();

	} catch (SQLException e) {
		e.printStackTrace();
	}
	return clientName;
}
public boolean applicationNameExist(String clientName)
{
	boolean flag = false;
	Connection con = null;
	ResultSet rs = null;
	
	try 
	{
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"vapplicationCode","applicationMst","vapplicationName = '" + clientName + "'","");
		if(rs.next())
		{
			flag = true;
		}
		//rs.close();
    	//con.close();
 	}
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
    	
	return flag;
}

//Added by Virendra Barad for get Client Detail History
public Vector<DTOApplicationGroupMst> getApplicationDetailHistory(String applicationCode) {
	
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOApplicationGroupMst> clientDetailHistory = new Vector<DTOApplicationGroupMst>();
	ResultSet rs = null;
	Connection con = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "vapplicationCode = '" + applicationCode +"'";
		rs = datatable.getDataSet(con, "*","View_Applicationmsthistory", Where,	"");

		while (rs.next()) {
			DTOApplicationGroupMst dto = new DTOApplicationGroupMst();
			dto.setApplicationCode(rs.getString("vApplicationCode"));
			dto.setApplicationName(rs.getString("vApplicationName"));
			dto.setHostingCode(rs.getString("vHostingName"));
			dto.setCategoryCode(rs.getString("vCategoryName"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setUserName(rs.getString("username"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			clientDetailHistory.addElement(dto);
		}

		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	} 

	return clientDetailHistory;	
}
public ArrayList<String> getPQSHeader(String clientCode) {
	
	 ArrayList<String> clientDetailHistory = new  ArrayList<String>();
	ResultSet rs = null;
	Connection con = null;

	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "vclientId = '" + clientCode +"'";
		rs = datatable.getDataSet(con, "*","pqstablemapmst", Where,	"");

		while (rs.next()) {
			clientDetailHistory.add(rs.getString("vStepNo"));
			clientDetailHistory.add(rs.getString("vStepName"));
			clientDetailHistory.add(rs.getString("vExpectedResult"));
		}

		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	} 

	return clientDetailHistory;	
}

public ArrayList<String> getTracebilityMatrixHeaders(String clientCode,String Automated_Doc_Type) {
	
	 ArrayList<String> clientDetailHistory = new  ArrayList<String>();
	ResultSet rs = null;
	Connection con = null;

	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "vclientId = '" + clientCode +"'";
		/*rs = datatable.getDataSet(con, "A.vActualColumnName","UrsFsTableMapMst as A INNER JOIN "
				+ "(SELECT vActualColumnName,MIN(iId) as id FROM UrsFsTableMapMst GROUP BY vActualColumnName,vDocType) AS B "
				+ "ON A.vActualColumnName = B.vActualColumnName AND A.iId = B.id and A.vClientId = '" + clientCode +"' "
				+ "and A.vDocType= '" + Automated_Doc_Type +"'", "", "A.iId");*/
		rs = datatable.getDataSet(con, "vActualColumnName,MIN(iId) as id", "UrsFsTableMapMst",
									"vClientId = '" + clientCode +"' and vDocType='" + Automated_Doc_Type +"' Group by vActualColumnName" ,"Id");
			while (rs.next()) {
				clientDetailHistory.add(rs.getString("vActualColumnName"));
			}
		/*
			String array[] = rs.getString("vHeader").split(",");
			for(int i=0;i<array.length;i++){
				clientDetailHistory.add(array[i]);
			}
		}*/

		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	} 

	return clientDetailHistory;	
}

public ArrayList<String> getTracebilityMatrixHeaders(String clientCode) {
	
	 ArrayList<String> clientDetailHistory = new  ArrayList<String>();
	ResultSet rs = null;
	Connection con = null;

	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "vclientId = '" + clientCode +"'";
		/*rs = datatable.getDataSet(con, "distinct A.vActualColumnName","UrsFsTableMapMst as A INNER JOIN "
				+ "(SELECT vActualColumnName,MIN(iId) as id FROM UrsFsTableMapMst GROUP BY vActualColumnName,vDocType) AS B "
				+ "ON A.vActualColumnName = B.vActualColumnName AND A.iId = B.id and A.vClientId = '" + clientCode +"' ", "", "A.vActualColumnName desc");*/
		rs = datatable.getDataSet(con, "vActualColumnName,MIN(iId) as id", "UrsFsTableMapMst",
				"vClientId = '" + clientCode +"' and vDocType='FS' Group by vActualColumnName " ,"Id");
			while (rs.next()) {
				clientDetailHistory.add(rs.getString("vActualColumnName"));
			}
		/*
			String array[] = rs.getString("vHeader").split(",");
			for(int i=0;i<array.length;i++){
				clientDetailHistory.add(array[i]);
			}
		}*/

		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	} 

	return clientDetailHistory;	
}


public ArrayList<String> getTracebilityMatrixHeadersForTM(String clientCode) {
	
	 ArrayList<String> clientDetailHistory = new  ArrayList<String>();
	ResultSet rs = null;
	Connection con = null;

	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "vclientId = '" + clientCode +"'";
		rs = datatable.getDataSet(con, "distinct A.vActualColumnName","UrsFsTableMapMst as A INNER JOIN "
				+ "(SELECT vActualColumnName,MIN(iId) as id FROM UrsFsTableMapMst GROUP BY vActualColumnName,vDocType) AS B "
				+ "ON A.vActualColumnName = B.vActualColumnName AND A.iId = B.id and A.vClientId = '" + clientCode +"' and A.vActualColumnName<>'FRSNo' ", "", "A.vActualColumnName desc");
			while (rs.next()) {
				clientDetailHistory.add(rs.getString("vActualColumnName"));
			}
		/*
			String array[] = rs.getString("vHeader").split(",");
			for(int i=0;i<array.length;i++){
				clientDetailHistory.add(array[i]);
			}
		}*/

		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	} 

	return clientDetailHistory;	
}

public String  ApplicationAttahmentMstOp(DTOApplicationGroupMst dto,int Mode,boolean isrevert)
{
	Connection con = null;
	CallableStatement proc = null;
	String status="";
	try
	{	    	
		con=ConnectionManager.ds.getConnection();
		proc = con.prepareCall("{ Call Insert_ApplicationAttahmentMst(?,?,?,?,?,?,?,?,?,?,?,?)}");
		proc.setString(1,dto.getApplicationCode());
		proc.setString(2,dto.getApplicationName());
		proc.setString(3,dto.getHostingCode());
		proc.setString(4,dto.getCategoryCode());
		proc.setString(5,dto.getAttachmentTitle());
		proc.setString(6,dto.getFileName());
		proc.setString(7,dto.getAttachmentPath());
		proc.setString(8,dto.getRemark());
		proc.setInt(9,dto.getModifyBy());
		proc.setString(10,"N");
		proc.setDouble(11, dto.getFileSize());
		proc.setInt(12,Mode); 
		proc.executeUpdate();
		status="true";
	}
	catch(SQLException e){
		e.printStackTrace();
		status=e.toString();
	}finally{
   		try{if(proc != null){proc.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return status;
}

public Vector<DTOApplicationGroupMst> getAttachmentHistory(String applicationCode) {
	
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOApplicationGroupMst> clientDetailHistory = new Vector<DTOApplicationGroupMst>();
	ResultSet rs = null;
	Connection con = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "vapplicationCode = '" + applicationCode +"' and cstatusindi<>'D'";
		rs = datatable.getDataSet(con, "*","View_ApplicationAttachment", Where,	"");

		while (rs.next()) {
			DTOApplicationGroupMst dto = new DTOApplicationGroupMst();
			dto.setApplicationCode(rs.getString("vApplicationCode"));
			dto.setAttachmentTitle(rs.getString("vDoumentName"));
			dto.setFileName(rs.getString("vFileName"));
			dto.setAttachmentPath(rs.getString("vFilePath"));
			dto.setTranNo(rs.getInt("iTranNo"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setUserName(rs.getString("username"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			clientDetailHistory.addElement(dto);
		}

		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	} 

	return clientDetailHistory;	
}

public DTOApplicationGroupMst getApplicationDetailByCodeAndTranNo(String applicationCode,int tranNo) {

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	DTOApplicationGroupMst dto = new DTOApplicationGroupMst();
	Connection con = null;
	ResultSet rs = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","ApplicationAttachment" ,"vapplicationcode = '" + applicationCode + "' "
				+ "and iTranNo="+tranNo+" and cStatusIndi<>'D' ","VApplicationCode");
		while(rs.next())
		{
			dto.setApplicationCode(rs.getString("vApplicationCode"));
			dto.setAttachmentTitle(rs.getString("vDoumentName"));
			dto.setFileName(rs.getString("vFileName"));
			dto.setAttachmentPath(rs.getString("vFilePath"));
			dto.setTranNo(rs.getInt("iTranNo"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return dto;
}

public DTOApplicationGroupMst getApplicationAttachmentDetailByTranNo(int tranNo) {

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	DTOApplicationGroupMst dto = new DTOApplicationGroupMst();
	Connection con = null;
	ResultSet rs = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","ApplicationAttachment" ,"iTranNo="+tranNo+" and cStatusIndi<>'D' ","VApplicationCode");
		while(rs.next())
		{
			dto.setApplicationCode(rs.getString("vApplicationCode"));
			dto.setAttachmentTitle(rs.getString("vDoumentName"));
			dto.setFileName(rs.getString("vFileName"));
			dto.setAttachmentPath(rs.getString("vFilePath"));
			dto.setTranNo(rs.getInt("iTranNo"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return dto;
}

public void deleteApplicationAttachment(String doumentName,int tranNo,String remark) {
	int userId = Integer.parseInt(ActionContext.getContext().getSession()
			.get("userid").toString());
	try {
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement proc = con
				.prepareCall("{Call proc_deletedeleteApplicationAttachment(?,?,?)}");
		//proc.setString(1, doumentName);
		proc.setInt(1, tranNo);
		proc.setString(2,remark);
		proc.setInt(3,userId);
		proc.execute();
		proc.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public boolean attachmentNameExist(String workspaceId, String folderName) {
	boolean flag = false;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = datatable.getDataSet(con, "itranno",
				"ApplicationAttachment", "vdoumentname = '" + folderName+ "' And vapplicationcode='"+workspaceId+"'"
						+ " AND cStatusIndi <> 'D' ", "");
		if (rs.next()) {
			flag = true;
		}
		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}

	return flag;
}

}// main class ended
