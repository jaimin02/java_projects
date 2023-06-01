package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.docmgmt.dto.DTOTrainingScheduleingDetails;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class TrainingScheduleDetail {
	DataTable datatable;
	
	public TrainingScheduleDetail() {
		datatable = new DataTable();
	}
	
	public int InsertTrainingScheduleDetail(ArrayList<DTOTrainingScheduleingDetails> tSDtlList)
	{
		int trainingScheduleNo = 0;
		Connection con = null;
		CallableStatement proc = null;
		try{
			con = ConnectionManager.ds.getConnection();
			proc = con.prepareCall("{ Call Insert_TrainingScheduleDetail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			int dataOpMode = 1;
			if(tSDtlList != null)
			{
				for (DTOTrainingScheduleingDetails dtoTSDtl : tSDtlList) 
				{
					proc.setInt(1, dtoTSDtl.getTrainingScheduleNo());
					proc.setInt(2, dtoTSDtl.getTrainingRecordNo());
					proc.setString(3, dtoTSDtl.getTrainingScheduleDesc());
					proc.setTimestamp(4, dtoTSDtl.getTrainingStartDate());
					proc.setTime(5, dtoTSDtl.getTrainingStartTime());
					proc.setTimestamp(6, dtoTSDtl.getTrainingEndDate());
					proc.setTime(7, dtoTSDtl.getTrainingEndTime());
					proc.setString(8, dtoTSDtl.getTrainingRefDocPath());
					proc.setInt(9, dtoTSDtl.getTrainingRefNodeId());
					proc.setInt(10, dtoTSDtl.getTrainingRefTranNo());
					proc.setString(11, dtoTSDtl.getRemark());
					proc.setInt(12, dtoTSDtl.getModifyBy());
					proc.setString(13, Character.toString(dtoTSDtl.getStatusIndi()));
					if (dtoTSDtl.getStatusIndi() == 'N') 
						dataOpMode = 1;
					else
						dataOpMode = 2;
					proc.setInt(14, dataOpMode);
					proc.registerOutParameter(15, java.sql.Types.NUMERIC);
					proc.execute();
					trainingScheduleNo = proc.getInt(15);
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
		return trainingScheduleNo;
	}

	public ArrayList<DTOTrainingScheduleingDetails> getTRNoWiseTSDetailList(int tRNo){
		String whereCond = "";
		if (tRNo != 0) 
			whereCond = " nTrainingRecordNo = "+ tRNo;
		return getDetails(whereCond," dTrainingStartDate,tTrainingStartTime,dTrainingEndDate,tTrainingEndTime");
	}
	
	public ArrayList<DTOTrainingScheduleingDetails> getTSNoWiseTSDetailList(int tSNo){
		String whereCond = "";
		if (tSNo != 0) 
			whereCond = " nTrainingScheduleNo = "+ tSNo;
		return getDetails(whereCond,"");
	}
	
	private ArrayList<DTOTrainingScheduleingDetails> getDetails(String whereCond, String orderBy)
	{
		ArrayList<DTOTrainingScheduleingDetails> data = new ArrayList<DTOTrainingScheduleingDetails>();
		Connection con = null;
		ResultSet rs = null;
		String fielsString = " nTrainingScheduleNo,nTrainingRecordNo,vTrainingId,vTrainingHdr,vTrainingDtl,vTrainingScheduleDesc,dTrainingStartDate,tTrainingStartTime," +
							 "dTrainingEndDate,tTrainingEndTime,nTotalTrainingDuration,vRefWorkspaceId,RefWorkspaceDesc,vTrainingRefDocPath," +
							 "iTrainingRefNodeId,iTrainingRefTranNo,vRemark,dModifyOn,iModifyBy,modifyByUser,cStatusIndi ";
		if (whereCond.trim().length() > 0)
			whereCond = whereCond + " AND cStatusIndi <> 'D'";
		try{
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, fielsString, " View_TrainingScheduleDetail ",whereCond,orderBy);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			java.util.Date date  = new Date();
			int diffHour = 0;
			int diffMin = 0;
			while(rs.next()){
				DTOTrainingScheduleingDetails dto = new DTOTrainingScheduleingDetails();
				dto.setTrainingScheduleNo(rs.getInt("nTrainingScheduleNo"));
				dto.setTrainingRecordNo(rs.getInt("nTrainingRecordNo"));
				dto.setTrainingId(rs.getString("vTrainingId"));
				dto.setTrainingHdr(rs.getString("vTrainingHdr"));
				dto.setTrainingDtl(rs.getString("vTrainingDtl"));
				dto.setTrainingScheduleDesc(rs.getString("vTrainingScheduleDesc"));
				dto.setTrainingStartDate(rs.getTimestamp("dTrainingStartDate"));
				dto.setTrainingEndDate(rs.getTimestamp("dTrainingEndDate"));
				 
				date = sdf.parse(rs.getString("tTrainingStartTime")); 
				dto.setTrainingStartTime(new Time(date.getTime()));

				date = sdf.parse(rs.getString("tTrainingEndTime")); 
				dto.setTrainingEndTime(new Time(date.getTime()));
				
				diffHour = dto.getTrainingEndTime().getHours() - dto.getTrainingStartTime().getHours();
				diffMin = dto.getTrainingEndTime().getMinutes() - dto.getTrainingStartTime().getMinutes();
				
				dto.setDiffTime(new Time(0,diffMin + (diffHour * 60),0));
				dto.setTotalTrainingDuration(rs.getInt("nTotalTrainingDuration"));
				dto.setRefWorkspaceId(rs.getString("vRefWorkspaceId"));
				dto.setRefWorkspaceDesc(rs.getString("RefWorkspaceDesc"));
				dto.setTrainingRefDocPath(rs.getString("vTrainingRefDocPath"));
				dto.setTrainingRefNodeId(rs.getInt("iTrainingRefNodeId"));
				dto.setTrainingRefTranNo(rs.getInt("iTrainingRefTranNo"));
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
}
