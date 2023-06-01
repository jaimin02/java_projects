package com.docmgmt.server.db.master;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionTypeMst;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionTypeMst {
	
	ConnectionManager conMgr;
	DataTable datatable;
	public SubmissionTypeMst()
	{
		 conMgr=new ConnectionManager(new Configuration());
		 datatable=new DataTable();
	}

public Vector getRegionWiseSubmissionTypes(String countryRegion, String regionalDtdVersion)
{		
		Vector data = new Vector();
		try
		{
			if( regionalDtdVersion==null || regionalDtdVersion.trim().equalsIgnoreCase("") ){
				regionalDtdVersion="";
			}
			else if((regionalDtdVersion.equalsIgnoreCase("14") || regionalDtdVersion.equalsIgnoreCase("13")) && countryRegion.equalsIgnoreCase("eu")){
				regionalDtdVersion="20";
			}
		
			Connection con = ConnectionManager.ds.getConnection();
			ResultSet rs = null;
			rs = datatable.getDataSet(con,"*","submissionTypeMst" , "vCountryRegionId='"+countryRegion+"' and vRegionalDTDVersion='"+regionalDtdVersion+"' " , "");
			while(rs.next())
			{
				DTOSubmissionTypeMst dto = new DTOSubmissionTypeMst();
				dto.setSubmissionTypeId(rs.getString("vSubmissionTypeId"));
				dto.setSubmissionType(rs.getString("vSubmissionType"));
				dto.setCountryRegionId(rs.getString("vCountryRegionId"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				dto.setSubmissionTypeIndi(rs.getString("cSubmissionTypeIndi").charAt(0));
				dto.setSubmissionTypeCode(rs.getString("vSubmissionTypeCode"));
				dto.setRegionalDTDVersion(rs.getString("vRegionalDTDVersion"));
				dto.setSubmissionTypeValue(rs.getString("vSubmissionTypeValue"));
				data.addElement(dto);
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
public String getSubmissionTypeCodeForUS23(String SubmissionType) {

	String SubmissionTypeCode = null;
	try {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = null;
		rs = datatable.getDataSet(con,"vSubmissionTypeCode","submissionTypeMst" , "vSubmissionType = '" + SubmissionType + "'" , "");
	
		if (rs.next()) {
			SubmissionTypeCode = rs.getString("vSubmissionTypeCode");
		}
		rs.close();
		con.close();
	} 
	catch(SQLException e){
		e.printStackTrace();
	}
		return SubmissionTypeCode;
}


}//main class ended
