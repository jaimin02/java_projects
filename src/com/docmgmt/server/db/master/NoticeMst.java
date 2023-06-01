package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.docmgmt.dto.DTONoticeMst;
import com.docmgmt.dto.DTOUserTypeMst;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class NoticeMst
{	
	DataTable datatable;
	UserTypeMst userTypeMst=new UserTypeMst();
	
	public NoticeMst()
	{
		datatable=new DataTable();
	}
	
	public boolean insertIntoNoticeMst(DTONoticeMst dto)
	{
		try 
		{
			boolean returnValue;	
			Connection connection=ConnectionManager.ds.getConnection();
			CallableStatement statement=connection.prepareCall("{ call Proc_NoticeMst(?,?,?,?,?,?,?,?,?,?,?) }");						
			statement.setInt(1,0);
			statement.setString(2,dto.getSubject());
			statement.setString(3,dto.getDescription());
			statement.setString(4,dto.getAttachment());
			statement.setTimestamp(5,dto.getStartdate());
			statement.setTimestamp(6,dto.getEndDate());
			statement.setString(7,dto.getUserTypeCode());
			statement.setString(8,String.valueOf(dto.getIsActive()));
			statement.setString(9,dto.getRemark());
			statement.setInt(10,dto.getModifyBy());
			statement.setInt(11,1);			
			returnValue=statement.execute();
			statement.close();
			connection.close();
			return returnValue;
		} 
		catch (SQLException e) 
		{			
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateInNoticeMst(DTONoticeMst dto)
	{
		try 
		{
			boolean returnValue;	
			Connection connection=ConnectionManager.ds.getConnection();
			CallableStatement statement=connection.prepareCall("{ call Proc_NoticeMst(?,?,?,?,?,?,?,?,?,?,?) }");					
			statement.setInt(1,dto.getNoticeNo());
			statement.setString(2,dto.getSubject());
			statement.setString(3,dto.getDescription());
			statement.setString(4,dto.getAttachment());
			statement.setTimestamp(5,dto.getStartdate());
			statement.setTimestamp(6,dto.getEndDate());
			statement.setString(7,dto.getUserTypeCode());
			statement.setString(8,String.valueOf(dto.getIsActive()));			
			statement.setString(9,dto.getRemark());
			statement.setInt(10,dto.getModifyBy());
			statement.setInt(11,2);			
			returnValue=statement.execute();
			statement.close();
			connection.close();
			return returnValue;
		} 
		catch (SQLException e) 
		{			
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteFromNoticeMst(DTONoticeMst dto)
	{
		try 
		{
			boolean returnValue;	
			Connection connection=ConnectionManager.ds.getConnection();
			CallableStatement statement=connection.prepareCall("{ call Proc_NoticeMst(?,?,?,?,?,?,?,?,?,?,?) }");					
			statement.setInt(1,dto.getNoticeNo());
			statement.setString(2,"");
			statement.setString(3,"");
			statement.setString(4,"");
			statement.setDate(5,null);
			statement.setDate(6,null);
			statement.setString(7,"");
			statement.setString(8,"");
			statement.setString(9,"");
			statement.setInt(10,dto.getModifyBy());
			statement.setInt(11,3);			
			returnValue=statement.execute();
			statement.close();
			connection.close();
			return returnValue;
		} 
		catch (SQLException e) 
		{			
			e.printStackTrace();
		}
		return false;
	}
	
	public Vector<DTONoticeMst> getAllNotices (int userCode)
	{
		Vector<DTONoticeMst> allNotices=new Vector<DTONoticeMst>();
		try 
		{
			Connection connection=ConnectionManager.ds.getConnection();
			ResultSet rs;
			if (userCode!=0)
				rs=datatable.getDataSet(connection,"*","NoticeMst","cStatusIndi<>'D' and iCreatedBy=" + userCode,"dModifyOn desc");
			else
				rs=datatable.getDataSet(connection,"*","NoticeMst","cStatusIndi<>'D' and cIsActive='Y'","dModifyOn desc");
			while(rs.next())
			{				
				DTONoticeMst dto=new DTONoticeMst();
				dto.setNoticeNo(rs.getInt(1));
				dto.setSubject(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setAttachment(rs.getString(4));
				dto.setStartdate(rs.getTimestamp(5));
				dto.setEndDate(rs.getTimestamp(6));			
				Vector<DTOUserTypeMst> lstUserTypes=userTypeMst.getAllUserType();
				String dbUsrTypes=rs.getString(7);
				String userType[]=dbUsrTypes.split(",");
				String userTypes="";				
				for (int i=0;i<userType.length;i++)
				{
					for (int j=0;j<lstUserTypes.size();j++)
					{
						if ( (lstUserTypes.get(j)).getUserTypeCode().trim().equals(userType[i]) )
						{
							userTypes+=(lstUserTypes.get(j)).getUserTypeName() + "<br>";
						}
					}									
				}
				if (!userTypes.equals(""))
					userTypes=userTypes.substring(0,userTypes.length()-4);				
				dto.setUserTypeCodeNames(userTypes);
				dto.setUserTypeCode(dbUsrTypes);
				dto.setIsActive(rs.getString(8).charAt(0));
				dto.setCreatedBy(rs.getInt(9));
				dto.setRemark(rs.getString(10));				
				dto.setModifyOn(rs.getTimestamp(11));
				dto.setModifyBy(rs.getInt(12));
				
				allNotices.add(dto);
			}
			rs.close();
			connection.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return allNotices;
	}
	
	public DTONoticeMst getNotice (int noticeNo,int userCode)
	{
		DTONoticeMst dto=new DTONoticeMst();
		try 
		{
			Connection connection=ConnectionManager.ds.getConnection();
			ResultSet rs=datatable.getDataSet(connection,"*","NoticeMst","cStatusIndi<>'D' and iCreatedBy=" + userCode + " and nNoticeNo=" + noticeNo + " and cIsActive='Y'","dModifyOn");
			if(rs.next())
			{								
				dto.setNoticeNo(rs.getInt(1));
				dto.setSubject(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setAttachment(rs.getString(4));
				dto.setStartdate(rs.getTimestamp(5));
				dto.setEndDate(rs.getTimestamp(6));			
				Vector<DTOUserTypeMst> lstUserTypes=userTypeMst.getAllUserType();
				String usrTypes=rs.getString(7);
				String userType[]=usrTypes.split(",");
				String userTypes="";				
				for (int i=0;i<userType.length;i++)
				{
					for (int j=0;j<lstUserTypes.size();j++)
					{
						if ( (lstUserTypes.get(j)).getUserTypeCode().trim().equals(userType[i]) )
						{
							userTypes+=(lstUserTypes.get(j)).getUserTypeName() + "<br>";
						}
					}									
				}
				if (!userTypes.equals(""))
					userTypes=userTypes.substring(0,userTypes.length()-4);				
				dto.setUserTypeCodeNames(userTypes);
				dto.setUserTypeCode(usrTypes);
				dto.setIsActive(rs.getString(8).charAt(0));
				dto.setCreatedBy(rs.getInt(9));
				dto.setRemark(rs.getString(10));				
				dto.setModifyBy(rs.getInt(12));				
			}
			rs.close();
			connection.close();
			return dto;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}