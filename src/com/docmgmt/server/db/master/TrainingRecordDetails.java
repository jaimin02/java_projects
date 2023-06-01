package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.docmgmt.dto.DTOEmployeeMst;
import com.docmgmt.dto.DTOTrainingAttendanceMst;
import com.docmgmt.dto.DTOTrainingRecordDetails;
import com.docmgmt.dto.DTOTrainingScheduleingDetails;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class TrainingRecordDetails {
	DataTable datatable;
	public TrainingRecordDetails(){
		datatable = new DataTable();
	}
	
	public void insertTrainingRecordDetails(ArrayList<DTOTrainingRecordDetails> tRDtlList){
		Connection con = null;
		CallableStatement proc = null;
		try{
			con = ConnectionManager.ds.getConnection();
			proc = con.prepareCall("{ Call Insert_TrainingRecordDetails(?,?,?,?,?,?,?,?,?,?)}");
			int dataOpMode = 1;
			if(tRDtlList != null)
			{
				for (DTOTrainingRecordDetails dtoTRDtl : tRDtlList) 
				{
					proc.setInt(1,dtoTRDtl.getTrainingRecordNo());
					proc.setString(2, dtoTRDtl.getTrainingId());
					proc.setString(3, dtoTRDtl.getTrainingHdr());
					proc.setString(4, dtoTRDtl.getTrainingDtl());
					proc.setInt(5,dtoTRDtl.getTotalTrainingDuration());
					proc.setString(6, dtoTRDtl.getRefWorkspaceId());
					proc.setString(7, dtoTRDtl.getRemark());
					proc.setInt(8, dtoTRDtl.getModifyBy());
					proc.setString(9, Character.toString(dtoTRDtl.getStatusIndi()));
					if (dtoTRDtl.getStatusIndi() == 'N') 
						dataOpMode = 1;
					else
						dataOpMode = 2;
					proc.setInt(10, dataOpMode);				
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
	
	public ArrayList<DTOTrainingRecordDetails> getAllTRDetailList(int statusMode){
		ArrayList<DTOTrainingRecordDetails> data = new ArrayList<DTOTrainingRecordDetails>();
		Connection con = null;
		ResultSet rs = null;
		try{
			con = ConnectionManager.ds.getConnection();
			String whereCond = "";
			if (statusMode == 1) 
				whereCond = " cStatusIndi <> 'D' ";
			rs = datatable.getDataSet(con, " nTrainingRecordNo, vTrainingId, vTrainingHdr, vTrainingDtl, nTotalTrainingDuration, vRefWorkspaceId, RefWorkspaceDesc, WsStatusIndi, vRemark, dModifyOn, iModifyBy, modifyByUser, cStatusIndi ", " View_TrainingRecordDetails ",whereCond,"");
			while(rs.next()){
				DTOTrainingRecordDetails dto = new DTOTrainingRecordDetails();
				dto.setTrainingRecordNo(rs.getInt("nTrainingRecordNo"));
				dto.setTrainingId(rs.getString("vTrainingId"));
				dto.setTrainingHdr(rs.getString("vTrainingHdr"));
				dto.setTrainingDtl(rs.getString("vTrainingDtl"));
				dto.setTotalTrainingDuration(rs.getInt("nTotalTrainingDuration"));
				dto.setRefWorkspaceId(rs.getString("vRefWorkspaceId"));
				dto.setRefWorkspaceDesc(rs.getString("RefWorkspaceDesc"));
				dto.setRefWsStatusIndi(rs.getString("WsStatusIndi").charAt(0));
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
	
	public ArrayList<DTOTrainingRecordDetails> getTRNoWiseTRDetailList(int tRNo){
		ArrayList<DTOTrainingRecordDetails> data = new ArrayList<DTOTrainingRecordDetails>();
		Connection con = null;
		ResultSet rs = null;
		try{
			con = ConnectionManager.ds.getConnection();
			String whereCond = "";
			if (tRNo != 0) 
				whereCond = " nTrainingRecordNo = "+ tRNo;
			rs = datatable.getDataSet(con, " nTrainingRecordNo, vTrainingId, vTrainingHdr, vTrainingDtl, nTotalTrainingDuration, vRefWorkspaceId, RefWorkspaceDesc, WsStatusIndi, vRemark, dModifyOn, iModifyBy, modifyByUser, cStatusIndi ", " View_TrainingRecordDetails ",whereCond,"");
			while(rs.next()){
				DTOTrainingRecordDetails dto = new DTOTrainingRecordDetails();
				dto.setTrainingRecordNo(rs.getInt("nTrainingRecordNo"));
				dto.setTrainingId(rs.getString("vTrainingId"));
				dto.setTrainingHdr(rs.getString("vTrainingHdr"));
				dto.setTrainingDtl(rs.getString("vTrainingDtl"));
				dto.setTotalTrainingDuration(rs.getInt("nTotalTrainingDuration"));
				dto.setRefWorkspaceId(rs.getString("vRefWorkspaceId"));
				dto.setRefWorkspaceDesc(rs.getString("RefWorkspaceDesc"));
				dto.setRefWsStatusIndi(rs.getString("WsStatusIndi").charAt(0));
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
	
	public ArrayList<DTOTrainingRecordDetails> getTrainingAttendaceReport(int trainingRecordNo,String trainingStartDate,String trainingEndDate,int conditionOnDate, int conditionOperater)
	{
		ArrayList<DTOTrainingRecordDetails> data = new ArrayList<DTOTrainingRecordDetails>();
		String whereCondition = getWhereCondtition(trainingRecordNo, trainingStartDate, trainingEndDate, conditionOnDate, conditionOperater);
		Connection con = null;
		ResultSet rs = null;
		String selectedFields = " nTrainingRecordNo,nTrainingScheduleNo,vTrainingId,vTrainingHdr,vTrainingDtl,vTrainingScheduleDesc," +
								"dTrainingStartDate,dTrainingEndDate,tTrainingStartTime,tTrainingEndTime,ExpectdDuration,TotalDuration," +
								"nTrAttNo,nEmpNo,cIsTraner,cIsPresent,vEmpCode,vEmpName,vDeptCode,vDeptName,dJOD,vEmailId,vMobileNo ";
		try{
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con,selectedFields, " View_TrainingAttendanceRpt ", whereCondition ," nTrainingRecordNo,nTrainingScheduleNo,nTrAttNo ");
			int prevTRNo = 0;boolean nxtRecordAvailable1 = false;
			nxtRecordAvailable1 = rs.next();
			DTOTrainingRecordDetails dtoTRDtl = new DTOTrainingRecordDetails();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			java.util.Date date  = new Date();
			int diffHour = 0;
			int diffMin = 0;
			while(nxtRecordAvailable1)
			{
				dtoTRDtl = new DTOTrainingRecordDetails();
				dtoTRDtl.setTrainingRecordNo(rs.getInt("nTrainingRecordNo"));
				dtoTRDtl.setTrainingId(rs.getString("vTrainingId"));
				dtoTRDtl.setTrainingHdr(rs.getString("vTrainingHdr"));
				dtoTRDtl.setTrainingDtl(rs.getString("vTrainingDtl"));
				dtoTRDtl.setTotalTrainingDuration(rs.getInt("ExpectdDuration")); //ExpectedDuration
				int prevTSNo = 0;
				prevTRNo = dtoTRDtl.getTrainingRecordNo();
				ArrayList<DTOTrainingScheduleingDetails> tSDtl = new ArrayList<DTOTrainingScheduleingDetails>();
				diffHour = 0;
				diffMin = 0;
				
				do {
					DTOTrainingScheduleingDetails dtoTSDtl = new DTOTrainingScheduleingDetails();
					dtoTSDtl.setTrainingScheduleNo(rs.getInt("nTrainingScheduleNo"));
					dtoTSDtl.setTrainingScheduleDesc(rs.getString("vTrainingScheduleDesc"));
					dtoTSDtl.setTrainingStartDate(rs.getTimestamp("dTrainingStartDate"));
					dtoTSDtl.setTrainingEndDate(rs.getTimestamp("dTrainingEndDate"));
					
					date = sdf.parse(rs.getString("tTrainingStartTime")); 
					dtoTSDtl.setTrainingStartTime(new Time(date.getTime()));
					
					date = sdf.parse(rs.getString("tTrainingEndTime")); 
					dtoTSDtl.setTrainingEndTime(new Time(date.getTime()));

					diffHour = dtoTSDtl.getTrainingEndTime().getHours() - dtoTSDtl.getTrainingStartTime().getHours();
					diffMin = dtoTSDtl.getTrainingEndTime().getMinutes() - dtoTSDtl.getTrainingStartTime().getMinutes();
					
					dtoTSDtl.setDiffTime(new Time(0,diffMin + (diffHour * 60),0));
								
					prevTSNo = dtoTSDtl.getTrainingScheduleNo();
					ArrayList<DTOTrainingAttendanceMst> tAMst = new ArrayList<DTOTrainingAttendanceMst>();
					do {
						DTOTrainingAttendanceMst dtoTADtl = new DTOTrainingAttendanceMst();
						dtoTADtl.setTrAttNo(rs.getInt("nTrAttNo"));
						dtoTADtl.setEmpNo(rs.getInt("nEmpNo"));
						dtoTADtl.setIsTraner(rs.getString("cIsTraner").charAt(0));
						dtoTADtl.setIsPresent(rs.getString("cIsPresent").charAt(0));
						
						DTOEmployeeMst dtoEmpMst = new DTOEmployeeMst();
						dtoEmpMst.setEmpCode(rs.getString("vEmpCode"));
						dtoEmpMst.setEmpName(rs.getString("vEmpName"));
						dtoEmpMst.setDeptCode(rs.getString("vDeptCode"));
						dtoEmpMst.setDeptName(rs.getString("vDeptName"));
						dtoEmpMst.setjOD(rs.getTimestamp("dJOD"));
						dtoEmpMst.setEmailId(rs.getString("vEmailId"));
						dtoEmpMst.setMobileNo(rs.getString("vMobileNo"));
						
						dtoTADtl.setDtoEmpMst(dtoEmpMst);
						tAMst.add(dtoTADtl);
						nxtRecordAvailable1 = rs.next();
					} while (nxtRecordAvailable1 && prevTSNo == rs.getInt("nTrainingScheduleNo"));
					dtoTSDtl.setTrainingAttendanceMst(tAMst);
					
					tSDtl.add(dtoTSDtl);
					
					//nxtRecordAvailable1 = rs.next();
				} while (nxtRecordAvailable1 && prevTRNo == rs.getInt("nTrainingRecordNo"));
				dtoTRDtl.setTrainingScheduleingDetails(tSDtl);
				data.add(dtoTRDtl);
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
	
	private String getWhereCondtition(int trainingRecordNo,String trainingStartDate,String trainingEndDate,int conditionOnDate, int conditionOperater)
	{
		String whereCondition1 = "";
		if (trainingRecordNo > 0)
			whereCondition1 = " nTrainingRecordNo = "+ trainingRecordNo;
		String condition1 = "";
		if (conditionOnDate == 0){
			condition1 = getConditionString(trainingStartDate, trainingEndDate, 1, conditionOperater);
			condition1 = " ( " + condition1 + " ) OR ( " + getConditionString(trainingStartDate, trainingEndDate, 2, conditionOperater) +" ) ";
		}
		else{
			condition1 = " ( " + getConditionString(trainingStartDate, trainingEndDate, conditionOnDate, conditionOperater) + " ) ";
		}
		if (whereCondition1!= null && !whereCondition1.trim().equalsIgnoreCase("")) {
			if (whereCondition1.length() > 0)
				whereCondition1 += " AND ";
		}
		whereCondition1 += " ( " + condition1 + " ) ";
		return whereCondition1;
	}
	
	private String getConditionString(String trainingStartDate,String trainingEndDate,int conditionOnDate, int conditionOperater)
	{
		String con1 = conditionOnDate == 1 ?" dTrainingStartDate ": " dTrainingEndDate ";
		String con2 = "";
		switch (conditionOperater) {
			case 0:
				con2 = " = '" +  (conditionOnDate == 1 ?trainingStartDate:trainingEndDate)  +"'";
			break;
			case 1:
				con2 = " < '" + (conditionOnDate == 1 ?trainingStartDate:trainingEndDate) +"'";
			break;
			case 2:
				con2 = " > '" + (conditionOnDate == 1 ?trainingStartDate:trainingEndDate) +"'";
			break;
			case 3:
				con2 = " BETWEEN '" + trainingStartDate +"' AND '"+trainingEndDate+"'";
			break;

		default:
			con2 = " = '" + (conditionOnDate == 1 ?trainingStartDate:trainingEndDate) +"'";
			break;
		}
		return con1.concat(con2);
	}
	
	public ArrayList<DTOEmployeeMst> getEmployeeWiseTrainingReport(int empNo,String trainingStartDate,String trainingEndDate,int conditionOnDate, int conditionOperater)
	{
		ArrayList<DTOEmployeeMst> data = new ArrayList<DTOEmployeeMst>();
		String whereCondition = getWhereCondtition1(empNo, trainingStartDate, trainingEndDate, conditionOnDate, conditionOperater);
		Connection con = null;
		ResultSet rs = null;
		String selectedFields = " nTrainingRecordNo,nTrainingScheduleNo,vTrainingId,vTrainingHdr,vTrainingDtl,vTrainingScheduleDesc," +
								"dTrainingStartDate,dTrainingEndDate,tTrainingStartTime,tTrainingEndTime,ExpectdDuration,TotalDuration," +
								"nTrAttNo,nEmpNo,cIsTraner,cIsPresent,vEmpCode,vEmpName,vDeptCode,vDeptName,dJOD,vEmailId,vMobileNo ";
		try{
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con,selectedFields, " View_TrainingAttendanceRpt ", whereCondition ," nEmpNo,nTrainingRecordNo,nTrainingScheduleNo ");
			int prevEmpNo = 0;boolean nxtRecordAvailable1 = false;
			nxtRecordAvailable1 = rs.next();
			DTOEmployeeMst dtoEmpMst = new DTOEmployeeMst();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			java.util.Date date  = new Date();
			int diffHour = 0;
			int diffMin = 0;
			while(nxtRecordAvailable1)
			{
				dtoEmpMst = new DTOEmployeeMst();
				dtoEmpMst.setEmpNo(rs.getInt("nEmpNo"));
				dtoEmpMst.setEmpCode(rs.getString("vEmpCode"));
				dtoEmpMst.setEmpName(rs.getString("vEmpName"));
				dtoEmpMst.setDeptCode(rs.getString("vDeptCode"));
				dtoEmpMst.setDeptName(rs.getString("vDeptName"));
				dtoEmpMst.setjOD(rs.getTimestamp("dJOD"));
				dtoEmpMst.setEmailId(rs.getString("vEmailId"));
				dtoEmpMst.setMobileNo(rs.getString("vMobileNo"));
				int prevTRNo = 0;
				prevEmpNo = dtoEmpMst.getEmpNo();
				ArrayList<DTOTrainingRecordDetails> tRDtl = new ArrayList<DTOTrainingRecordDetails>();
				diffHour = 0;
				diffMin = 0;
				
				do {
					DTOTrainingRecordDetails dtoTRDtl = new DTOTrainingRecordDetails();
					dtoTRDtl.setTrainingRecordNo(rs.getInt("nTrainingRecordNo"));
					dtoTRDtl.setTrainingId(rs.getString("vTrainingId"));
					dtoTRDtl.setTrainingHdr(rs.getString("vTrainingHdr"));
					dtoTRDtl.setTrainingDtl(rs.getString("vTrainingDtl"));
					dtoTRDtl.setTotalTrainingDuration(rs.getInt("ExpectdDuration")); //ExpectedDuration

					prevTRNo = dtoTRDtl.getTrainingRecordNo();
					ArrayList<DTOTrainingScheduleingDetails> tSDtl = new ArrayList<DTOTrainingScheduleingDetails>();
					do {
						DTOTrainingScheduleingDetails dtoTSDtl = new DTOTrainingScheduleingDetails();
						dtoTSDtl.setTrainingScheduleNo(rs.getInt("nTrainingScheduleNo"));
						dtoTSDtl.setTrainingScheduleDesc(rs.getString("vTrainingScheduleDesc"));
						dtoTSDtl.setTrainingStartDate(rs.getTimestamp("dTrainingStartDate"));
						dtoTSDtl.setTrainingEndDate(rs.getTimestamp("dTrainingEndDate"));
						
						date = sdf.parse(rs.getString("tTrainingStartTime")); 
						dtoTSDtl.setTrainingStartTime(new Time(date.getTime()));
						
						date = sdf.parse(rs.getString("tTrainingEndTime")); 
						dtoTSDtl.setTrainingEndTime(new Time(date.getTime()));

						diffHour = dtoTSDtl.getTrainingEndTime().getHours() - dtoTSDtl.getTrainingStartTime().getHours();
						diffMin = dtoTSDtl.getTrainingEndTime().getMinutes() - dtoTSDtl.getTrainingStartTime().getMinutes();
						
						dtoTSDtl.setDiffTime(new Time(0,diffMin + (diffHour * 60),0));
						
						DTOTrainingAttendanceMst dtoTADtl = new DTOTrainingAttendanceMst();
						dtoTADtl.setTrAttNo(rs.getInt("nTrAttNo"));
						dtoTADtl.setEmpNo(rs.getInt("nEmpNo"));
						dtoTADtl.setIsTraner(rs.getString("cIsTraner").charAt(0));
						dtoTADtl.setIsPresent(rs.getString("cIsPresent").charAt(0));
						ArrayList<DTOTrainingAttendanceMst> tAList = new ArrayList<DTOTrainingAttendanceMst>();
						tAList.add(dtoTADtl);
						dtoTSDtl.setTrainingAttendanceMst(tAList);
						
						tSDtl.add(dtoTSDtl);
						nxtRecordAvailable1 = rs.next();
					} while (nxtRecordAvailable1 && prevTRNo == rs.getInt("nTrainingRecordNo") && prevEmpNo == rs.getInt("nEmpNo"));
					dtoTRDtl.setTrainingScheduleingDetails(tSDtl);
					
					tRDtl.add(dtoTRDtl);
					
					//nxtRecordAvailable1 = rs.next();
				} while (nxtRecordAvailable1 && prevEmpNo == rs.getInt("nEmpNo"));
				dtoEmpMst.settRDtl(tRDtl);
				data.add(dtoEmpMst);
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
	
	private String getWhereCondtition1(int empNo,String trainingStartDate,String trainingEndDate,int conditionOnDate, int conditionOperater)
	{
		String whereCondition1 = "";
		if (empNo > 0)
			whereCondition1 = " nEmpNo = "+ empNo;
		String condition1 = "";
		if (conditionOnDate == 0){
			condition1 = getConditionString(trainingStartDate, trainingEndDate, 1, conditionOperater);
			condition1 = " ( " + condition1 + " ) OR ( " + getConditionString(trainingStartDate, trainingEndDate, 2, conditionOperater) +" ) ";
		}
		else{
			condition1 = " ( " + getConditionString(trainingStartDate, trainingEndDate, conditionOnDate, conditionOperater) + " ) ";
		}
		if (whereCondition1!= null && !whereCondition1.trim().equalsIgnoreCase("")) {
			if (whereCondition1.length() > 0)
				whereCondition1 += " AND ";
		}
		whereCondition1 += " ( " + condition1 + " ) ";
		return whereCondition1;
	}
}