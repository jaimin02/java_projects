package com.docmgmt.test.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.docmgmt.server.db.master.DataTable;
import com.docmgmt.test.CountryList;
import com.docmgmt.test.EditLocationTest;

public class TestDAO {

	
	public Vector<CountryList> getCountryList()
	{
		Vector<CountryList> vList;
		CountryList cl;
		vList=new Vector<CountryList>();
		Connection con = null;
		ResultSet rs = null;
		DataTable datatable;
		try{
			datatable=new DataTable();
			con = ConnectionManager.ds.getConnection();
			rs=datatable.getDataSet(con,"*","View_getLocationTemp" ,"","CountryId");
			while(rs.next()){
				cl=new CountryList();
				cl.setCountryCode(rs.getString("CountryId"));
				cl.setCountryName(rs.getString("Name"));
				vList.add(cl);
			}
		}catch(Exception e){
			
		}
		finally{
			try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
	   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}

		}
	//AddCountry();
		return vList;
		
	}
	public void AddCountry(EditLocationTest elt){
		ConnectionManager conMgr=new ConnectionManager(new Configuration());
		
		try
		{	    
			Connection con =ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call InsertLocationTemp(?,?,?)}");
			proc.setString(1, "id");
			proc.setString(2, elt.getLocationName());
			proc.setInt(3, 1); //1 mode mean add
			
		//	proc.setInt(5, mode);
			
			proc.execute();
			proc.close();
			con.close();
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
			
	}
	public void UpdateCountry(EditLocationTest elt){
		ConnectionManager conMgr=new ConnectionManager(new Configuration());
		
		try
		{	    
			Connection con =ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call InsertLocationTemp(?,?,?)}");
			proc.setString(1, elt.getLocationCode());
			proc.setString(2, elt.getLocationName());
			
			proc.setInt(3, 2); //2=update
			
			proc.execute();
			proc.close();
			con.close();
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
			
	}
	public void DeleteCountry(EditLocationTest elt){
		ConnectionManager conMgr=new ConnectionManager(new Configuration());
		
		try
		{	    
			Connection con =ConnectionManager.ds.getConnection();
			CallableStatement proc = con.prepareCall("{ Call InsertLocationTemp(?,?,?)}");
			proc.setString(1, elt.getLocationCode());
			proc.setString(2, elt.getLocationName());
			
			proc.setInt(3, 3); //2=delete
			
			proc.execute();
			proc.close();
			con.close();
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		System.out.println("Deleted..."+elt.getLocationCode());
			
	}

}
