package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.docmgmt.dto.DTOSubmissionQueryMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionQueryMst {

	DataTable datatable;
	
	public SubmissionQueryMst(){
		datatable = new DataTable();
	}
	
	public long insertSubmissionQueryMst(ArrayList<DTOSubmissionQueryMst> subQueryMstList,int opMode){
		
		Connection con = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		long queryId=0;
		try 
		{
			con=ConnectionManager.ds.getConnection();
			cs=con.prepareCall("{call Insert_SubmissionQueryMst(?,?,?,?,?,?,?,?,?)}");
			for(int i=0;i<subQueryMstList.size();i++)
			{
				DTOSubmissionQueryMst dtoSubQueryMst=subQueryMstList.get(i);
				cs.setLong(1,dtoSubQueryMst.getQueryId());
				cs.setString(2,dtoSubQueryMst.getQueryTitle());
				cs.setTimestamp(3,dtoSubQueryMst.getStartDate());
				cs.setTimestamp(4,dtoSubQueryMst.getEndDate());
				cs.setString(5, dtoSubQueryMst.getRefDoc());
				cs.setString(6, dtoSubQueryMst.getQueryDesc());
				cs.setString(7, dtoSubQueryMst.getRemark());
				cs.setInt(8, dtoSubQueryMst.getModifyBy());
				cs.setInt(9,opMode); //datamode 1 for insert or (if available then)update
				if(opMode == 1){
					rs = cs.executeQuery();
					if(rs.next())
						queryId = rs.getLong(1);
					System.out.println("Query Id = "+queryId);
						
				}
				else
				{
					cs.execute();
				}
			}	
		}   
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
			try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
			try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
		}
		return queryId;
	}

	

}
