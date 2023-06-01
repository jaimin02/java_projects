package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOLocationMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class LocationMst {

	DataTable datatable;
	
	public LocationMst()
	{
		datatable=new DataTable();
	}
	

public Vector<DTOLocationMst> getLocationDtl() 
{
	Vector<DTOLocationMst> data = new Vector<DTOLocationMst>();
	Connection con = null;
	ResultSet rs = null;
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","locationMst" ,"","vLocationName");
		while(rs.next())
		{
			DTOLocationMst dto = new DTOLocationMst();
			dto.setLocationCode(rs.getString("vLocationCode"));
			dto.setLocationName(rs.getString("vLocationName"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			data.addElement(dto);
		}
	}   
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
	return data;
}


public void LocationMstOp(DTOLocationMst dto,int Mode,boolean isrevert)
{
	Connection con = null;
	CallableStatement proc = null;
	try
	{	    	
		con=ConnectionManager.ds.getConnection();
  		proc = con.prepareCall("{ Call Insert_locationMst(?,?,?,?,?,?)}");
  		proc.setString(1,dto.getLocationCode());
  		proc.setString(2,dto.getLocationName());
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
  				statusIndi='E';
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

public DTOLocationMst getLocationInfo(String LocationCode)
{
	DTOLocationMst dto=new DTOLocationMst();
	Connection con = null;
	ResultSet rs = null;
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","locationMst" ,"vLocationcode="+LocationCode,"");
		if(rs.next())
		{
			dto.setLocationCode(rs.getString("vLocationCode"));
			dto.setLocationName(rs.getString("vLocationName"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
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


public boolean locationNameExist(String locationName)
{
	boolean flag = false;
	Connection con = null;
	ResultSet rs = null;
	try 
	{
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"vLocationCode","locationMst","vLocationName = '" + locationName + "'","");
		if(rs.next())
		{
			flag = true;
		}
	}
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(rs != null){rs.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	} 	
    	
	return flag;
}
}//main class ended
