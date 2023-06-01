package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOTimelineDateExclusion;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class TimeDateExcludeMst {
	DataTable datatable;
	public TimeDateExcludeMst()
	{
		 datatable=new DataTable();
	}

public Vector<DTOTimelineDateExclusion> getExcludedDateDetail() {

	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOTimelineDateExclusion> clientVector = new Vector<DTOTimelineDateExclusion>();
	Connection con = null;
	ResultSet rs = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try 
	{		  
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"*","ProjectTimelineDateExclusion" ,"","ID");
		while(rs.next())
		{
			DTOTimelineDateExclusion dto = new DTOTimelineDateExclusion();
			dto.setID(rs.getInt("ID"));
			dto.setExcludedDate(rs.getDate("dExclusionDate"));
			dto.setRemark(rs.getString("vRemark"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
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


public boolean dateExist(String date)
{
	boolean flag = false;
	Connection con = null;
	ResultSet rs = null;
	
	try 
	{
		con = ConnectionManager.ds.getConnection();
		rs=datatable.getDataSet(con,"dExclusionDate","ProjectTimelineDateExclusion","dExclusionDate = '" + date + "'","");
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

public void  DateMstOp(DTOTimelineDateExclusion dto,int Mode,boolean isrevert) throws ParseException
{
	Connection con = null;
	CallableStatement proc = null;
	try
	{	    	
		con=ConnectionManager.ds.getConnection();
		proc = con.prepareCall("{ Call Insert_timelineDateMst(?,?,?,?,?)}");
		//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		//Date excludedDate = dateFormat.parse(dto.getExcludedDate().toString());
		Timestamp ts1 = new Timestamp(dto.getExcludedDate().getTime());
		proc.setTimestamp(1, ts1);
		proc.setString(2,dto.getRemark());
		proc.setInt(3,dto.getModifyBy());
		if(Mode==1)
			proc.setString(4,"N");
		else
			proc.setString(4,"E");
		proc.setInt(5,Mode); 
		proc.executeUpdate();
	}
	catch(SQLException e){
		e.printStackTrace();
	}finally{
   		try{if(proc != null){proc.close();}}catch (Exception e){e.printStackTrace();}
   		try{if(con != null){con.close();}}catch (Exception e){e.printStackTrace();}
   	}
}


public Vector<DTOTimelineDateExclusion> getdateHistory(String date) {
	
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOTimelineDateExclusion> clientDetailHistory = new Vector<DTOTimelineDateExclusion>();
	ResultSet rs = null;
	Connection con = null;
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try {
		con = ConnectionManager.ds.getConnection();

		String Where = "ExcludedDate = '" + date +"'";
		rs = datatable.getDataSet(con, "*","View_ProjectTimelineDateExclusionHistory", Where,	"TranNo");

		while (rs.next()) {
			DTOTimelineDateExclusion dto = new DTOTimelineDateExclusion();
			dto.setExcludedDate(rs.getDate("ExcludedDate"));
			dto.setRemark(rs.getString("Remark"));
			dto.setUserName(rs.getString("UserName")); 
			dto.setModifyBy(rs.getInt("ModifyBy"));
			dto.setModifyOn(rs.getTimestamp("ModifyOn"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyOn(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			clientDetailHistory.addElement(dto);
		}

		rs.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	} 

	return clientDetailHistory;	
}

}
