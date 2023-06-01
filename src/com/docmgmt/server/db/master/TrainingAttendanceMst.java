package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.docmgmt.dto.DTOEmployeeMst;
import com.docmgmt.dto.DTOTrainingAttendanceMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class TrainingAttendanceMst 
{
	DataTable datatable;
	public TrainingAttendanceMst()
	{
		datatable = new DataTable();
	}
	
	public void insert_TrainingAttendanceMst(ArrayList<DTOTrainingAttendanceMst> trainingAttendanceMstList)
	{
		Connection con = null;
		CallableStatement proc = null;
		try{
			con = ConnectionManager.ds.getConnection();
			proc = con.prepareCall("{ Call Insert_TrainingAttendanceMst(?,?,?,?,?,?,?,?,?)}");
			int dataOpMode = 1;
			if(trainingAttendanceMstList != null)
			{
				for (DTOTrainingAttendanceMst dtoTAMst : trainingAttendanceMstList) 
				{
					proc.setInt(1, dtoTAMst.getTrAttNo());
					proc.setInt(2, dtoTAMst.getTrainingScheduleNo());
					proc.setInt(3, dtoTAMst.getEmpNo());
					proc.setString(4, Character.toString(dtoTAMst.getIsTraner()));
					proc.setString(5, Character.toString(dtoTAMst.getIsPresent()));
					proc.setString(6, dtoTAMst.getRemark());
					proc.setInt(7, dtoTAMst.getModifyBy());
					proc.setString(8, Character.toString(dtoTAMst.getStatusIndi()));
					if (dtoTAMst.getStatusIndi() == 'N') 
						dataOpMode = 1;
					else if (dtoTAMst.getStatusIndi() == 'D') 
						dataOpMode = 3;
					else
						dataOpMode = 2;
					proc.setInt(9, dataOpMode);
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
	
	
	public ArrayList<DTOTrainingAttendanceMst> getEMPNoWiseTADetailList(int empNo)
	{
		String whereCond = "";
		if (empNo != 0) 
			whereCond = " nEmpNo = "+ empNo;
		return getDetails(whereCond,"");
	}
	
	public ArrayList<DTOTrainingAttendanceMst> getTSNoWiseTADetailList(int tSNo)
	{
		String whereCond = "";
		if (tSNo != 0) 
			whereCond = " nTrainingScheduleNo = "+ tSNo;
		return getDetails(whereCond," cIsTraner desc");
	}
	
	public ArrayList<DTOTrainingAttendanceMst> getDeptWiseTADetailList(int deptCode)
	{
		String whereCond = "";
		if (deptCode != 0) 
			whereCond = " vDeptCode = '"+ deptCode+"'";
		return getDetails(whereCond,"");
	}
	
	public ArrayList<DTOTrainingAttendanceMst> getTrainerList(int tSNo)
	{
		String whereCond = "";
		if (tSNo != 0) 
			whereCond = " nTrainingScheduleNo = "+ tSNo;
		if (whereCond.trim().length() > 0) 
			whereCond += " AND ";
		
		whereCond += " cIsTraner = 'Y'";
		return getDetails(whereCond,"");
	}
	
	public ArrayList<DTOTrainingAttendanceMst> getTraineeList(int tSNo)
	{
		String whereCond = "";
		if (tSNo != 0) 
			whereCond = " nTrainingScheduleNo = "+ tSNo;
		if (whereCond.trim().length() > 0) 
			whereCond += " AND ";
		
		whereCond += " cIsTraner = 'N'";
		return getDetails(whereCond,"");
	}
	private ArrayList<DTOTrainingAttendanceMst> getDetails(String whereCond, String orderBy)
	{
		ArrayList<DTOTrainingAttendanceMst> trainigAttendanceList = new ArrayList<DTOTrainingAttendanceMst>();
		Connection con = null;
		ResultSet rs = null;
		String fielsString = "nTrAttNo,nTrainingScheduleNo,nEmpNo,cIsTraner,cIsPresent,vRemark,dModifyOn,iModifyBy," +
							 "modifyByUser,cStatusIndi,vEmpCode,vEmpName,vDeptCode,vDeptName,dJOD,vEmailId,vMobileNo";
		if (whereCond.trim().length() > 0)
			whereCond = whereCond + " AND cStatusIndi <> 'D'";
		try{
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, fielsString, " View_TrainingAttendanceMst ",whereCond,orderBy);
			while(rs.next()){
				DTOTrainingAttendanceMst dtoTAM = new DTOTrainingAttendanceMst();
				dtoTAM.setTrAttNo(rs.getInt("nTrAttNo"));
				dtoTAM.setTrainingScheduleNo(rs.getInt("nTrainingScheduleNo"));
				dtoTAM.setEmpNo(rs.getInt("nEmpNo"));
				dtoTAM.setIsTraner(rs.getString("cIsTraner").charAt(0));
				dtoTAM.setIsPresent(rs.getString("cIsPresent").charAt(0));
				dtoTAM.setRemark(rs.getString("vRemark"));
				dtoTAM.setModifyOn(rs.getTimestamp("dModifyOn"));
				dtoTAM.setModifyByUser(rs.getString("modifyByUser"));
				dtoTAM.setModifyBy(rs.getInt("iModifyBy"));
				dtoTAM.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				DTOEmployeeMst dtoEMPMst = new DTOEmployeeMst();
				dtoEMPMst.setEmpNo(rs.getInt("nEmpNo"));
				dtoEMPMst.setEmpCode(rs.getString("vEmpCode"));
				dtoEMPMst.setEmpName(rs.getString("vEmpName"));
				dtoEMPMst.setDeptCode(rs.getString("vDeptCode"));
				dtoEMPMst.setDeptName(rs.getString("vDeptName"));
				dtoEMPMst.setjOD(rs.getTimestamp("dJOD"));
				dtoEMPMst.setEmailId(rs.getString("vEmailId"));
				dtoEMPMst.setMobileNo(rs.getString("vMobileNo"));
				dtoTAM.setDtoEmpMst(dtoEMPMst);
				trainigAttendanceList.add(dtoTAM);
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
