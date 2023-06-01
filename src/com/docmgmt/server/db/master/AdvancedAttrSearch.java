package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.docmgmt.dto.DTOAdvancedAttrSearch;
import com.docmgmt.dto.DTOContentSearch;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class AdvancedAttrSearch {
	DataTable datatable;

	public AdvancedAttrSearch() {
		datatable = new DataTable();
	}
	
	
	public ArrayList<DTOAdvancedAttrSearch> getAllSavedAttrList(int userId){
		ArrayList<DTOAdvancedAttrSearch> data = new ArrayList<DTOAdvancedAttrSearch>();
		Connection con = null;
		ResultSet rs = null;
		try{
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, "iUserCode,iAttrId,vAttrValue,cStatusIndi,iModifyBy,dModifyOn", "AdvanceSearchMst", "iUserCode="+userId, "");
			while(rs.next()){
				DTOAdvancedAttrSearch dto = new DTOAdvancedAttrSearch();
				dto.setAttrId(rs.getInt("iUserCode"));
				dto.setAttrId(rs.getInt("iAttrId"));
				dto.setAttrValue(rs.getString("vAttrValue"));
				dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
				dto.setModifyBy(rs.getInt("iModifyBy"));
				dto.setModifyOn(rs.getTimestamp("dModifyOn"));
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
	
	public void insertAttrListForAdvanceSearch(ArrayList<DTOAdvancedAttrSearch> userAttrList,int dataOpMode){
		
		Connection con = null;
		CallableStatement proc = null;
		try{
			con = ConnectionManager.ds.getConnection();
			proc = con.prepareCall("{ Call Insert_AdvanceSearchMst(?,?,?,?)}");
			if(userAttrList != null)
			{
				for (DTOAdvancedAttrSearch dtoAdvancedAttrSearch : userAttrList) {
					proc.setInt(1,dtoAdvancedAttrSearch.getUserCode());
					proc.setInt(2, dtoAdvancedAttrSearch.getAttrId());
					proc.setString(3,  dtoAdvancedAttrSearch.getAttrValue());
					proc.setInt(4, dataOpMode);				
					proc.execute();
					//if dataOpMode ==2 then it will delete all data. so it will be execute only ones.
					if (dataOpMode == 2) {
						break;
					}
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
	
	public ArrayList<DTOContentSearch> getSearchResult(int userId,String attrForIndiId,String FinalQueryString){
		ArrayList<DTOContentSearch> dtoContentSearch = new ArrayList<DTOContentSearch>();
		Connection con = null;
		ResultSet rs = null;
		try{
			con = ConnectionManager.ds.getConnection();
			rs = datatable.getDataSet(con, "distinct vworkspaceid,vworkspacedesc,iAttrId,vattrtype,vattrname,vattrvalue,inodeid,vnodename,vnodedisplayname", "view_contentsearch", "iUserCode="+userId+ " and vattrforindiid not like '" + attrForIndiId + "' and ("+ FinalQueryString+")", "");
			while(rs.next()){
				DTOContentSearch dto = new DTOContentSearch();
				dto.setWorkspaceid(rs.getString("vworkspaceid"));
				dto.setWorkspaceDesc(rs.getString("vworkspacedesc"));
				dto.setAttrid(rs.getInt("iAttrId"));
				dto.setAttrtype(rs.getString("vattrtype"));
				dto.setAttrName(rs.getString("vattrname"));
				dto.setAttrValue(rs.getString("vattrvalue"));
			    dto.setNodeId(rs.getInt("inodeid"));
			    dto.setNodeName(rs.getString("vnodename"));
			    dto.setNodeDisplayName(rs.getString("vnodedisplayname"));
				dtoContentSearch.add(dto);
			}
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		finally{
			try{if (rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
			try{if (con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
		
		
		return dtoContentSearch;
	}
	
	
	

}
