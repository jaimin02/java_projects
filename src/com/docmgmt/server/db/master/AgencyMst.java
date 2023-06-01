package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOAgencyMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class AgencyMst {
	
	DataTable datatable;
	
	public AgencyMst()
	{
		datatable=new DataTable();
	}
	
	
	public Vector<DTOAgencyMst> getAllAgency(){
		
		Vector<DTOAgencyMst> data = new Vector<DTOAgencyMst>();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = datatable.getDataSet(con, "vAgencyCode,vAgencyName,vAgencyFullName,vAgencyRegionCode", "agencyMst", "", "");
			while(rs.next())
			{
				DTOAgencyMst dto = new DTOAgencyMst();
				dto.setAgencyCode(rs.getString("vAgencyCode"));
				dto.setAgencyName(rs.getString("vAgencyName"));
				dto.setAgencyFullName(rs.getString("vAgencyFullName"));
				dto.setAgencyRegionCode(rs.getString("vAgencyRegionCode"));
				data.addElement(dto);
				dto = null;
			}
			rs.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return data;
	}
	
	public ArrayList<DTOAgencyMst> getAgenciesForCountry(String countryId,String dtdVersion){
		
		ArrayList<DTOAgencyMst> data = new ArrayList<DTOAgencyMst>();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs;
			
			if(dtdVersion.trim().equalsIgnoreCase("")) 
			{
				rs = datatable.getDataSet(con, "vAgencyCode,vAgencyName,vAgencyFullName,vAgencyRegionCode", "agencyMst", "vAgencyRegionCode = '"+countryId+"'", "");
			}
			else if(dtdVersion.trim().equalsIgnoreCase("14") || dtdVersion.trim().equalsIgnoreCase("13"))
			{
				 rs = datatable.getDataSet(con, "vAgencyCode,vAgencyName,vAgencyFullName,vAgencyRegionCode", "agencyMst", " vDtdVersion='' and vAgencyRegionCode = '"+countryId+"'", "");
			}
			else
			{
				 rs = datatable.getDataSet(con, "vAgencyCode,vAgencyName,vAgencyFullName,vAgencyRegionCode", "agencyMst", " vDtdVersion='"+ dtdVersion.trim() +"' and vAgencyRegionCode = '"+countryId+"'", "");
				 
			}
			
			
			while(rs.next())
			{
				DTOAgencyMst dto = new DTOAgencyMst();
				dto.setAgencyCode(rs.getString("vAgencyCode"));
				dto.setAgencyName(rs.getString("vAgencyName"));
				dto.setAgencyFullName(rs.getString("vAgencyFullName"));
				dto.setAgencyRegionCode(rs.getString("vAgencyRegionCode"));
				data.add(dto);
			}
			rs.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return data;
	}
	public DTOAgencyMst getAgencyByName(String agencyName){
		DTOAgencyMst dto = new DTOAgencyMst();
		try
		{
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = datatable.getDataSet(con, "vAgencyCode,vAgencyName,vAgencyFullName,vAgencyRegionCode", "agencyMst", " vAgencyName = '"+agencyName+"'", "");
			while(rs.next())
			{
				dto.setAgencyCode(rs.getString("vAgencyCode"));
				dto.setAgencyName(rs.getString("vAgencyName"));
				dto.setAgencyFullName(rs.getString("vAgencyFullName"));
				dto.setAgencyRegionCode(rs.getString("vAgencyRegionCode"));
			}
			rs.close();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return dto;
	}
	
}
