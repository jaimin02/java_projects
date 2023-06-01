package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOClientMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class ClientMst {

	
	DataTable datatable;
	public ClientMst()
	{
		 datatable=new DataTable();
	}

public Vector<DTOClientMst> getClientDetail() {

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOClientMst> clientVector = new Vector<DTOClientMst>();
	Connection con = null;
	ResultSet rs = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","ClientMst" ,"","VclientName");
		while(rs.next())
		{
			DTOClientMst dto = new DTOClientMst();
			dto.setClientCode(rs.getString("vClientCode"));
			dto.setClientName(rs.getString("vClientName"));
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
    
public void  ClientMstOp(DTOClientMst dto,int Mode,boolean isrevert)
{
	Connection con = null;
	CallableStatement proc = null;
	try
	{	    	
		con=ConnectionManager.ds.getConnection();
		proc = con.prepareCall("{ Call Insert_clientMst(?,?,?,?,?,?)}");
		proc.setString(1,dto.getClientCode());
		proc.setString(2,dto.getClientName());
		proc.setString(3,dto.getRemark() );
		proc.setInt(4,dto.getModifyBy());
		if(isrevert==false)
		{
			if(Mode==1)
				proc.setString(5,"N");
			else
				proc.setString(5,"E");	
		}
		else //if revert is true
		{
			char statusIndi=dto.getStatusIndi();
			if('D'==statusIndi)
			{
				statusIndi='A';
			}
			else statusIndi='D';
		    	 proc.setString(5,Character.toString(statusIndi));
		}
		proc.setInt(6,Mode); 
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
public boolean clientNameExist(String clientName)
{
	boolean flag = false;
	Connection con = null;
	ResultSet rs = null;
	
	try 
	{
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"vClientCode","clientMst","vClientName = '" + clientName + "'","");
		if(rs.next())
		{
			flag = true;
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
    	
	return flag;
}

//Added by Virendra Barad for get Client Detail History
public Vector<DTOClientMst> getClientDetailHistory(String clientCode) {
	
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOClientMst> clientDetailHistory = new Vector<DTOClientMst>();
	ResultSet rs = null;
	Connection con = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "clientCode = '" + clientCode +"'";
		rs = datatable.getDataSet(con, "*","view_clientmsthistory", Where,	"TranNo");

		while (rs.next()) {
			DTOClientMst dto = new DTOClientMst();
			dto.setClientCode(rs.getString("ClientCode")); // ClientCode
			dto.setClientName(rs.getString("ClientName")); // ClientName
			dto.setUserName(rs.getString("UserName")); // UserName
			dto.setModifyBy(rs.getInt("ModifyBy")); // modifyBy
			dto.setRemark(rs.getString("Remark")); // remark
			dto.setModifyOn(rs.getTimestamp("ModifyOn")); // modifyOn
		if(countryCode.equalsIgnoreCase("IND")){
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
		}
		else{
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
			dto.setESTDateTime(time.get(1));
		}
			dto.setStatusIndi(rs.getString("StatusIndi").charAt(0)); // statusIndi
			clientDetailHistory.addElement(dto);
			dto = null;
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



}// main class ended
