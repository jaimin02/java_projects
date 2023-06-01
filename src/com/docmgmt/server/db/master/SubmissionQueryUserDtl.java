package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionQueryUserDtl;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class SubmissionQueryUserDtl {
DataTable datatable;
	
	public SubmissionQueryUserDtl(){
		datatable = new DataTable();
	}
	public void insertSubmissionQueryUserDtl(ArrayList<DTOSubmissionQueryUserDtl> subQueryUserDtlList,int opMode){
		Connection con = null;
		CallableStatement cs = null;
		try 
		{
			//System.out.println("DSATA OP MODE ::::::::::::::"+opMode);
			con=ConnectionManager.ds.getConnection();
			cs=con.prepareCall("{call Insert_SubmissionQueryUserDtl(?,?,?,?,?)}");
			for(int i=0;i<subQueryUserDtlList.size();i++)
			{
				DTOSubmissionQueryUserDtl dtosubmissionQueryUserDtl=subQueryUserDtlList.get(i);
				//System.out.println(dtoSubQueryDtl.getQueryId()+"/////"+dtoSubQueryDtl.getWorkSpaceId()+"/////"+dtoSubQueryDtl.getSeqNo()+"//////"+dtoSubQueryDtl.getSeqNo());
				
				System.out.println("Query Id="+dtosubmissionQueryUserDtl.getQueryId());
				System.out.println("User Id="+dtosubmissionQueryUserDtl.getUserid());
		
				
	
				cs.setLong(1,dtosubmissionQueryUserDtl.getQueryId());
				cs.setInt(2, dtosubmissionQueryUserDtl.getUserid());
				cs.setString(3, dtosubmissionQueryUserDtl.getRemark());
				cs.setInt(4, dtosubmissionQueryUserDtl.getModifyBy());
				cs.setInt(5,opMode); //datamode 1 for insert or (if available then)update
				cs.execute();
				
			}	
		}   
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
			try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
		}				
	}
	
	public Vector<DTOUserMst> getUserDtlsByQueryId(long queryId){
		
		
		Vector<DTOUserMst> userDtlVector = new Vector<DTOUserMst>();
		
		 ResultSet rs = null;
		 Connection con = null;
		 try
			{
				con = ConnectionManager.ds.getConnection();
				StringBuffer query = new StringBuffer();
				query.append(" usercode in(select iUserCode from SubmissionQueryUserDtl where iQueryId="+queryId +") ");
						
				//query.append(" and imodifyby = "+dto.getModifyBy()+"");
				rs = datatable.getDataSet(con,"Distinct userGroupCode,usercode,username", "view_workspaceUserDetail",query.toString(),"");
				
				while(rs.next())  
				{
					
					
					DTOUserMst userMstDTO = new DTOUserMst(); 
					userMstDTO.setUserGroupCode(rs.getInt("userGroupCode"));
					userMstDTO.setUserCode(rs.getInt("userCode"));
					userMstDTO.setLoginName(rs.getString("username"));
					userDtlVector.addElement(userMstDTO);
					
					System.out.println("User Code="+rs.getInt("userCode"));
				}
				
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try {if(rs != null){rs.close();}} catch (Exception ex) {ex.printStackTrace();}
				try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
			}
		 return userDtlVector;
	 }
	
	public void deleteSubmissionQueryUserDtl(long queryid){
		Connection con = null;
		CallableStatement cs = null;
		try 
		{
			
			con=ConnectionManager.ds.getConnection();
			cs=con.prepareCall("{call Proc_DeleteSubmissionQueryUsers(?)}");
				
	
			cs.setLong(1,queryid);
			cs.execute();
			System.out.println("Deleted.....");
			
		}   
		
		
		catch(Exception e){
			e.printStackTrace();
		}finally{
			try {if(cs != null){cs.close();}} catch (Exception ex) {ex.printStackTrace();}
			try {if(con != null){con.close();}} catch (Exception ex) {ex.printStackTrace();}
		}				
	}
}
