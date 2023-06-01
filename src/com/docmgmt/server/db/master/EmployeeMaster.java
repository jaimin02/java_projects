package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.docmgmt.dto.DTOEmployeeMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class EmployeeMaster {
	DataTable datatable;
	public EmployeeMaster(){
		datatable = new DataTable();
	}

	public ArrayList<DTOEmployeeMst> getAllEmployeeList(int statusMode){
		ArrayList<DTOEmployeeMst> data = new ArrayList<DTOEmployeeMst>();
		Connection con = null;
		ResultSet rs = null;
		try{
			con = ConnectionManager.ds.getConnection();
			String whereCond = "";
			if (statusMode == 1) 
				whereCond = " cStatusIndi <> 'D' ";
			rs = datatable.getDataSet(con, " nEmpNo,vEmpCode,vEmpName,vDeptCode,vDeptName,dJOD,vEmailId,vMobileNo,vRemark,dModifyOn,iModifyBy,modifyByUser,cStatusIndi ", " View_EmployeeMst ",whereCond," vEmpCode");
			while(rs.next()){
				DTOEmployeeMst dto = new DTOEmployeeMst();
				dto.setEmpNo(rs.getInt("nEmpNo"));
				dto.setEmpCode(rs.getString("vEmpCode"));
				dto.setEmpName(rs.getString("vEmpName"));
				dto.setDeptCode(rs.getString("vDeptCode"));
				dto.setDeptName(rs.getString("vDeptName"));
				dto.setjOD(rs.getTimestamp("dJOD"));
				dto.setEmailId(rs.getString("vEmailId"));
				dto.setMobileNo(rs.getString("vMobileNo"));
				dto.setRemark(rs.getString("vRemark"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setModifyByUser(rs.getString("modifyByUser"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				data.add(dto);
			}
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		finally{
			try{if (rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
		return data;
	}
	
	public ArrayList<DTOEmployeeMst> getEmployeeList(DTOEmployeeMst dtoEmpMst,int statusMode){
		ArrayList<DTOEmployeeMst> data = new ArrayList<DTOEmployeeMst>();
		int columnNo = dtoEmpMst.getEmpNo() > 0 ? 0 :dtoEmpMst.getEmpCode() != null || dtoEmpMst.getEmpCode().trim() != "" ? 1 :dtoEmpMst.getEmpName() != null || dtoEmpMst.getEmpName().trim() != "" ? 2 :dtoEmpMst.getDeptCode() != null || dtoEmpMst.getDeptCode().trim() != ""? 3 : 4 ;
		data = getEmployeeList(dtoEmpMst,columnNo,statusMode);
		return data;
	}
	
	private ArrayList<DTOEmployeeMst> getEmployeeList(DTOEmployeeMst dtoEmpMst,int columnNo,int statusMode){
		ArrayList<DTOEmployeeMst> data = new ArrayList<DTOEmployeeMst>();
		Connection con = null;
		ResultSet rs = null;
		try{
			con = ConnectionManager.ds.getConnection();
			String whereCond = "";
			String statusCond = "";
			String columnCond = "";
			if (statusMode == 1) 
				statusCond = " cStatusIndi <> 'D' ";
			
			if (columnNo == 0) 
			{
				columnCond = dtoEmpMst.getEmpNo() > 0 ? " nEmpNo = "+dtoEmpMst.getEmpNo() : ""; 
			}
			else if (columnNo == 1)  
			{
				columnCond = dtoEmpMst.getEmpCode() != null || dtoEmpMst.getEmpCode().trim() != "" ? " vEmpCode = "+dtoEmpMst.getEmpCode() : "";
			}
			else if (columnNo == 2)  
			{
				columnCond = dtoEmpMst.getEmpName() != null || dtoEmpMst.getEmpName().trim() != "" ? " vEmpName = "+dtoEmpMst.getEmpName() : "";
			}
			else if (columnNo == 3)  
			{
				columnCond = dtoEmpMst.getDeptCode() != null || dtoEmpMst.getDeptCode().trim() != "" ? " vDeptCode = "+dtoEmpMst.getDeptCode() : "";
			}
			else
			{
				columnCond = "";
			}
			whereCond = columnCond != null || columnCond.trim().length() > 0 ? columnCond:"";
			whereCond += whereCond.trim().length() > 0 ? (statusMode == 1 ? " AND " + statusCond : "") : "";  
			rs = datatable.getDataSet(con, " nEmpNo,vEmpCode,vEmpName,vDeptCode,vDeptName,dJOD,vEmailId,vMobileNo,vRemark,dModifyOn,iModifyBy,modifyByUser,cStatusIndi ", " View_EmployeeMst ",whereCond," vEmpCode");
			while(rs.next()){
				DTOEmployeeMst dto = new DTOEmployeeMst();
				dto.setEmpNo(rs.getInt("nEmpNo"));
				dto.setEmpCode(rs.getString("vEmpCode"));
				dto.setEmpName(rs.getString("vEmpName"));
				dto.setDeptCode(rs.getString("vDeptCode"));
				dto.setDeptName(rs.getString("vDeptName"));
				dto.setjOD(rs.getTimestamp("dJOD"));
				dto.setEmailId(rs.getString("vEmailId"));
				dto.setMobileNo(rs.getString("vMobileNo"));
				dto.setRemark(rs.getString("vRemark"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
				dto.setModifyByUser(rs.getString("modifyByUser"));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				data.add(dto);
			}
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		finally{
			try{if (rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
		return data;
	}
	
	public void insertEmployeeMst(ArrayList<DTOEmployeeMst> empMstList){
		Connection con = null;
		CallableStatement proc = null;
		try{
			con = ConnectionManager.ds.getConnection();
			proc = con.prepareCall("{ Call Insert_EmployeeMst(?,?,?,?,?,?,?,?,?,?,?)}");
			int dataOpMode = 1;
			if(empMstList != null)
			{
				for (DTOEmployeeMst dtoEmpMst : empMstList) 
				{
					proc.setInt(1,dtoEmpMst.getEmpNo());
					proc.setString(2, dtoEmpMst.getEmpCode());
					proc.setString(3, dtoEmpMst.getEmpName());
					proc.setString(4, dtoEmpMst.getDeptCode());
					proc.setTimestamp(5, dtoEmpMst.getjOD());
					proc.setString(6, dtoEmpMst.getEmailId());
					proc.setString(7, dtoEmpMst.getMobileNo());
					proc.setString(8, dtoEmpMst.getRemark());
					proc.setInt(9, dtoEmpMst.getModifyBy());
					proc.setString(10, Character.toString(dtoEmpMst.getStatusIndi()));
					if (dtoEmpMst.getStatusIndi() == 'N') 
						dataOpMode = 1;
					else
						dataOpMode = 2;
					proc.setInt(11, dataOpMode);				
					proc.execute();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{if (proc != null)proc.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
	}
	
	public ArrayList<DTOEmployeeMst> getTrainingEmployeeDtl(int nEmpNo)
	{
		ArrayList<DTOEmployeeMst>trainigAttendanceList = new ArrayList<DTOEmployeeMst>();
		Connection con = null;
		ResultSet rs = null;
		String fielsString =" DISTINCT nEmpNo,vEmpCode,vEmpName,vDeptCode,vDeptName,dJOD,vEmailId,vMobileNo ";
		String whereCond="";
		if (nEmpNo > 0)
			whereCond = " nEmpNo = " + nEmpNo; 
			
		try{
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, fielsString, " View_TrainingAttendanceMst ",whereCond," vEmpCode ");
			while(rs.next()){
				DTOEmployeeMst dtoEMPMst = new DTOEmployeeMst();
				dtoEMPMst.setEmpNo(rs.getInt("nEmpNo"));
				dtoEMPMst.setEmpCode(rs.getString("vEmpCode"));
				dtoEMPMst.setEmpName(rs.getString("vEmpName"));
				dtoEMPMst.setDeptCode(rs.getString("vDeptCode"));
				dtoEMPMst.setDeptName(rs.getString("vDeptName"));
				dtoEMPMst.setjOD(rs.getTimestamp("dJOD"));
				dtoEMPMst.setEmailId(rs.getString("vEmailId"));
				dtoEMPMst.setMobileNo(rs.getString("vMobileNo"));
				trainigAttendanceList.add(dtoEMPMst);
			}
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		finally{
			try{if (rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
		return trainigAttendanceList;
	}
	
}
