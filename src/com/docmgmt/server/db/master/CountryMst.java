package com.docmgmt.server.db.master;


import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

import com.docmgmt.dto.DTOCountryMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;



public class CountryMst {
	
	static DataTable datatable = new DataTable();
	
	
public Vector<DTOCountryMst> getAllCountryDetail()
{
	Vector<DTOCountryMst> data =new Vector<DTOCountryMst>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{
		con = ConnectionManager.ds.getConnection();
		String where = "cStatusIndi != 'D'";
		rs = datatable.getDataSet(con, "*", "CountryMst", where, "");
		while(rs.next())
		{
			DTOCountryMst dto = new DTOCountryMst();
			dto.setCountryId(rs.getString("vCountryId"));
			dto.setCountryName(rs.getString("vCountryName"));
			dto.setRegionId(rs.getString("vRegionId"));
			dto.setCountryCode(rs.getString("vCountryCode"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			data.addElement(dto);
		}
	} catch (Exception e) {
			e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
		try {if(con!= null){con.close();}} catch (Exception e) {e.printStackTrace();}
	}
	return data;
}

/* Method Added By : Ashmak Shah
 * Added On : 20th may 2009
 * Usage : Get CountryId for given CountryCode
 */
public String getCountryId(String countryCode) {
	
	String cId = null;
	Connection con = null;
	ResultSet rs = null;
	try 
	{
		con = ConnectionManager.ds.getConnection();
		rs = datatable.getDataSet(con, "vCountryId", "CountryMst", "vCountryCode='"+countryCode+"'", "");
		if(rs.next())
		{
			cId = rs.getString("vCountryId");
		}
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
		try {if(con!= null){con.close();}} catch (Exception e) {e.printStackTrace();}
	}
	return cId;
}

/* Method Added By : Ashmak Shah
 * Added On : 20th may 2009
 * Usage : Get Country for given CountryId
 */
public DTOCountryMst getCountry(String countryId) {
	
	DTOCountryMst country = new DTOCountryMst();
	Connection con = null;
	ResultSet rs = null;
	
	try 
	{
		con = ConnectionManager.ds.getConnection();
		rs = datatable.getDataSet(con, "*", "CountryMst", "vCountryId='"+countryId+"'", "");
		if(rs.next())
		{
			country.setCountryId(rs.getString("vCountryId"));
			country.setCountryName(rs.getString("vCountryName"));
			country.setRegionId(rs.getString("vRegionId"));
			country.setCountryCode(rs.getString("vCountryCode"));
			country.setModifyBy(rs.getInt("iModifyBy"));
			country.setModifyOn(rs.getTimestamp("dModifyOn"));
			country.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			
			
		}
	
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
		try {if(con!= null){con.close();}} catch (Exception e) {e.printStackTrace();}
	}
	return country;
}

public Vector<DTOCountryMst> getCountriesRegionWise(String region)
{
	Vector<DTOCountryMst> data =new Vector<DTOCountryMst>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{
		con = ConnectionManager.ds.getConnection();
		String whr = " vRegionId = '"+region+"' ";
		rs = datatable.getDataSet(con, "*", "CountryMst", whr, "vCountryName");
		while(rs.next())
		{
			DTOCountryMst dto = new DTOCountryMst();
			dto.setCountryId(rs.getString("vCountryId"));
			dto.setCountryName(rs.getString("vCountryName"));
			dto.setRegionId(rs.getString("vRegionId"));
			dto.setCountryCode(rs.getString("vCountryCode"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			data.addElement(dto);
		}
		
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		try {if(rs != null){rs.close();}} catch (Exception e) {e.printStackTrace();}
		try {if(con!= null){con.close();}} catch (Exception e) {e.printStackTrace();}
	}
	return data;
}
}// main class ended
