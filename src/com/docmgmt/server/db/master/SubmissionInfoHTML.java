package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.docmgmt.dto.DTOSubmissionInfoHTML;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;
import com.opensymphony.xwork2.ActionContext;

public class SubmissionInfoHTML {
	
	ConnectionManager conMgr;
	DataTable datatable;
	
	public SubmissionInfoHTML()
	{
		 conMgr=new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}
	
public long insertSubmissionInfoHTML(DTOSubmissionInfoHTML dto)
{	long subId =0;
	try
	{
		CallableStatement proc = null;
		Connection con = ConnectionManager.ds.getConnection();

			proc = con.prepareCall("{ Call Insert_SubmissionInfoHTML(?,?,?,?,?,?)}");
			
			proc.setString(1,dto.getWorkSpaceId());
			proc.setString(2,dto.getDescription());
			proc.setString(3,dto.getPublishPath());
			proc.setInt(4,dto.getCreatedBy());
			proc.setInt(5,dto.getModifyBy());
			proc.setDouble(6,dto.getPublishFolderSize());		

			ResultSet rs =proc.executeQuery();
			
			if(rs.next()) {
				subId = rs.getLong(1);
			}	
			proc.close();
			con.close();
			
	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return subId;
	
}
	

public ArrayList<DTOSubmissionInfoHTML> getAllSubmissionInfoHTMLDetail(String wsId)
{
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	ArrayList<DTOSubmissionInfoHTML> data = new ArrayList<DTOSubmissionInfoHTML>();
	ArrayList<String> time = new ArrayList<String>();
	String locationName = ActionContext.getContext().getSession().get("locationname").toString();
	String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
	try
	{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = null;
		rs = datatable.getDataSet(con,"*","View_SubmissionInfoHTML" , "vWorkSpaceId ='"+wsId+"'", "");
		while(rs.next())
		{
			DTOSubmissionInfoHTML dto =new DTOSubmissionInfoHTML();
			dto.setWorkSpaceId(rs.getString("vWorkSpaceid"));
			dto.setWorkspacedesc("vWorkspacedesc");
			dto.setSubmissionInfoHtmlID(rs.getLong("iSubmissionInfoHTMLid"));
			dto.setTranNo(rs.getInt("iTranNo"));
			dto.setDescription(rs.getString("vDescription"));
			dto.setPublishPath(rs.getString("vPublishPath"));
			dto.setCreatedBy(rs.getInt("CreatedBy"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyon(rs.getTimestamp("dmodifyon"));
			if(countryCode.equalsIgnoreCase("IND")){
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyon(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
			}
			else{
				time = docMgmtImpl.TimeZoneConversion(dto.getModifyon(),locationName,countryCode);
				dto.setISTDateTime(time.get(0));
				dto.setESTDateTime(time.get(1));
			}
			dto.setUsername(rs.getString("Username"));
			data.add(dto);
			dto = null;
		}
		
		rs.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	
	return data;	
}

public ArrayList<DTOSubmissionInfoHTML> getPublishDetailfromID(long ID){
	
DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
ArrayList<DTOSubmissionInfoHTML> data = new ArrayList<DTOSubmissionInfoHTML>();
ArrayList<String> time = new ArrayList<String>();
String locationName = ActionContext.getContext().getSession().get("locationname").toString();
String countryCode = ActionContext.getContext().getSession().get("countryCode").toString();
try{
	Connection con = ConnectionManager.ds.getConnection();
	ResultSet rs = null;
	String fields = "vWorkSpaceid,iTranNo,vDescription,dmodifyon,Username,vPublishPath";
	rs = datatable.getDataSet(con,fields,"View_SubmissionInfoHTML" , "iSubmissionInfoHTMLid='"+ID+"'", "");
	while(rs.next())
	{
		DTOSubmissionInfoHTML dto =new DTOSubmissionInfoHTML();
		dto.setWorkSpaceId(rs.getString("vWorkSpaceid"));
		dto.setTranNo(rs.getInt("iTranNo"));
		dto.setDescription(rs.getString("vDescription"));
		dto.setModifyon(rs.getTimestamp("dmodifyon"));
		if(countryCode.equalsIgnoreCase("IND")){
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyon(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
		}
		else{
			time = docMgmtImpl.TimeZoneConversion(dto.getModifyon(),locationName,countryCode);
			dto.setISTDateTime(time.get(0));
			dto.setESTDateTime(time.get(1));
		}
		dto.setPublishPath(rs.getString("vPublishPath"));
		dto.setUsername(rs.getString("Username"));
		data.add(dto);
		dto = null;
	}
	
	rs.close();
	con.close();
}
catch(SQLException e){
	e.printStackTrace();
}
return data;
}
public int getNewTranNoForHTMLPublish(String wsId) {
	int tranNo = -1;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		CallableStatement cs = con.prepareCall("{call Proc_newTranNoForHTMLPublish(?,?)}");
		cs.setString(1, wsId);
		cs.registerOutParameter(2, java.sql.Types.INTEGER);
		cs.execute();
		tranNo = cs.getInt(2);
		cs.close();
		con.close();
		return tranNo;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return tranNo;
}


}//main class ended
