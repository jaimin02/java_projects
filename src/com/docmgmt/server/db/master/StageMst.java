package com.docmgmt.server.db.master;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.docmgmt.server.prop.PropertyInfo;

public class StageMst {

	ConnectionManager conMgr;
	DataTable datatable;
	public StageMst()
	{
		 conMgr=new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}
	
public Vector<DTOStageMst> getStageDetail() 
{
	Vector<DTOStageMst> stageVector = new Vector<DTOStageMst>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=datatable.getDataSet(con,"*","stageMst" ,"cStatusIndi<>'D'","iStageId");
		while(rs.next())
		{
			DTOStageMst dto = new DTOStageMst();
			dto.setStageId(rs.getInt("iStageId"));
			dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setInclusive(rs.getString("cInclusive").charAt(0));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			stageVector.addElement(dto);					
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	
	return stageVector;
}
public Vector<DTOStageMst> getStageDetailCSV() 
{
	Vector<DTOStageMst> stageVector = new Vector<DTOStageMst>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=datatable.getDataSet(con,"*","stageMst" ,"iStageId<>'0' and cStatusIndi<>'D' ","iStageId");
		while(rs.next())
		{
			DTOStageMst dto = new DTOStageMst();
			dto.setStageId(rs.getInt("iStageId"));
			dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setInclusive(rs.getString("cInclusive").charAt(0));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			stageVector.addElement(dto);					
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	
	return stageVector;
}
public void  stageMstOp(DTOStageMst dto,int Mode,boolean isrevert)
{
	try
	{	    	
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call Insert_stageMst(?,?,?,?,?,?)}");
		proc.setInt(1, dto.getStageId());
		proc.setString(2,dto.getStageDesc());
		proc.setString(3,Character.toString(dto.getInclusive()));
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
		proc.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
   }

}
 
public DTOStageMst getStageInfo(int stageId)
{
	DTOStageMst dto = new DTOStageMst();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=datatable.getDataSet(con,"*","stageMst" ,"iStageId="+stageId+"and cStatusIndi<>'D'","");
		if(rs.next())
		{
			dto.setStageId(rs.getInt("iStageId"));
			dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setInclusive(rs.getString("cInclusive").charAt(0));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return dto;
}
public ArrayList<Integer> getNextStageId(int stageId){
	ArrayList<Integer> currentAndNextStageId = new ArrayList<Integer>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		String query="iStageId, nextStageId from (select iStageId,lead(iStageId) over (order by iStageId) as nextStageId from stagemst) AS t";
		ResultSet rs=datatable.getDataSet2(con,query,"iStageId="+stageId,"");
		if(rs.next())
		{
			currentAndNextStageId.add(rs.getInt("iStageId"));
			currentAndNextStageId.add(rs.getInt("nextStageId"));
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return currentAndNextStageId;
}    
public DTOStageMst getNextStageInfo(int stageId)
{
	DTOStageMst dto = new DTOStageMst();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=datatable.getDataSet(con,"TOP 1 *","stageMst" ,"iStageId >"+stageId+" and iStageId <>40 and cStatusIndi<>'D'","");
		if(rs.next())
		{
			dto.setStageId(rs.getInt("iStageId"));
			dto.setStageDesc(rs.getString("vStageDesc"));
			dto.setInclusive(rs.getString("cInclusive").charAt(0));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	return dto;
}    




public Vector<DTOStageMst> geteSignatureStageDetail()
{
	Vector<DTOStageMst> stageVector = new Vector<DTOStageMst>();
	PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	String otsuka = propertyInfo.getValue("OtsukaCustomization");
	ResultSet rs= null;
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		if(otsuka.equalsIgnoreCase("Yes")){
			 rs=datatable.getDataSet(con,"*","eSignatureMst" ,"cStatusIndi<>'D'","vStageDesc");
			}
			else{
			 rs=datatable.getDataSet(con,"*","eSignatureMst" ,"cStatusIndi<>'D'","iId");
			}
		while(rs.next())
		{
			DTOStageMst dto = new DTOStageMst();
			dto.setStageIdForESIgn(rs.getString("vStageId"));
			dto.setStageDesc(rs.getString("vStageDesc"));
			stageVector.addElement(dto);					
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	
	return stageVector;
}


}//main class ended
