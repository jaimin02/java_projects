package com.docmgmt.server.db.master;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOAttrForIndiMst;
import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOAttributeValueMatrix;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class AttributeMst {
	
	DataTable dataTable;
	public AttributeMst()
	{
		dataTable=new DataTable();
	}
	
	
	public Vector<DTOAttributeMst> atrriblist;
	public DTOAttributeMst attribute;
	
	
public Vector<DTOAttributeMst> getAllAttrib() 
{
		
		Vector<DTOAttributeMst> attribVector = new  Vector<DTOAttributeMst>();
		try 
		{			
			Connection con = ConnectionManager .ds.getConnection();
			ResultSet rs=dataTable.getDataSet(con,"*","view_getAttributeByAttrForIndi","","attrId");
			ResultSet rs1=dataTable.getDataSet(con,"attrId,attrMatrixValue,attrDisplayValue","view_getAttributeByAttrForIndi","","attrValueId");
			Vector<String> tempaddmatrixvalue=new Vector<String>();
			while(rs1.next())
			{
				tempaddmatrixvalue.addElement(Integer.toString(rs1.getInt("attrId"))+":"+rs1.getString("attrMatrixValue"));
				
			}
			rs1.close();
			int prevattrid=-1;
			int size=tempaddmatrixvalue.size();
			
			while(rs.next())
			{
				DTOAttributeMst attrMstDTO=new DTOAttributeMst();
				String temp="";
				Vector<String> attrmatrixtemp=new Vector<String>();
				
					attrMstDTO.setAttrId(rs.getInt("attrId"));
					attrMstDTO.setAttrName(rs.getString("attrName"));
					attrMstDTO.setAttrForIndiId(rs.getString("attrForIndiId"));
					attrMstDTO.setAttrForIndiName(rs.getString("attrForIndiName"));
					attrMstDTO.setRemark(rs.getString("remark"));
					attrMstDTO.setAttrValueId(rs.getInt("attrValueId"));
					attrMstDTO.setAttrValue(rs.getString("attrValue"));
					//attrMstDTO.setAttrMatrixValue(rs.getString("attrMatrixValue"));
					attrMstDTO.setUserTypeName(rs.getString("userTypeName"));
					attrMstDTO.setUserInput(rs.getString("userInput").charAt(0));
					attrMstDTO.setAttrType(rs.getString("attrType"));
					
					if(attrMstDTO.getAttrType().equals("Combo") ||attrMstDTO.getAttrType().equals("AutoCompleter"))	
					{
						for(int i=0;i<size;i++)
						{
						temp=tempaddmatrixvalue.get(i).toString();
						
						
							String temparray[]=temp.split(":");
							if(Integer.parseInt(temparray[0])==attrMstDTO.getAttrId())
							{
								if(temp.endsWith(":")){
									attrmatrixtemp.addElement("");
								}else{
									attrmatrixtemp.addElement(temparray[1]);
								}
							}
							
					
									
						}
				 }
					
				attrMstDTO.setAttrMatrixValueVect(attrmatrixtemp);
				if(prevattrid !=attrMstDTO.getAttrId())
				{
					attribVector.addElement(attrMstDTO);
				}
				prevattrid=attrMstDTO.getAttrId();
			
			}
			rs.close();
			con.close();
	
		}
		catch(SQLException e){
			
		    e.printStackTrace();
		}
				
		return attribVector;
		
		
}

public DTOAttributeMst getAttribute(int Id) {

	DTOAttributeMst attrib=new DTOAttributeMst();
    try 
	{	
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=dataTable.getDataSet(con,"*","attributemst","iAttrID="+Id,"");
		while(rs.next())
		{
			attrib.setAttrId(rs.getInt("iAttrId"));// attributeId
			attrib.setAttrName(rs.getString("vAttrName"));// attributeName
			attrib.setAttrValue(rs.getString("vAttrValue"));// attributeValue
			attrib.setAttrType(rs.getString("vAttrType"));// attributeType
			attrib.setAttrForIndiId(rs.getString("vAttrForIndiId"));//attributeForIndiId
			attrib.setUserInput(rs.getString("cUserInput").charAt(0));//UserInput
			attrib.setUserTypeCode(rs.getString("vUserTypeCode"));//UserTypeCode
			attrib.setRemark(rs.getString("vRemark"));//remark
			attrib.setModifyBy(rs.getInt("iModifyBy"));//modifyBy
			attrib.setStatusIndi(rs.getString("cStatusIndi").charAt(0));//statusIndi
		}
		rs.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}

	return attrib;
}

public int InsertAttribute(DTOAttributeMst obj, int userId)
{
	int attrId=1;
	try
	{	    	
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement proc =con.prepareCall("{ Call Insert_AttributeMst(?,?,?,?,?,?,?,?,?,?)}");
		
		proc.setInt(1,attrId);
		proc.setString(2, obj.getAttrName());
		proc.setString(3,obj.getAttrValue());
		proc.setString(4,obj.getAttrType());
		proc.setString(5,obj.getAttrForIndiId());
		proc.setString(6,Character.toString(obj.getUserInput()));
		proc.setString(7,obj.getUserTypeCode());
		proc.setString(8,obj.getRemark());
		proc.setInt(9,userId);
		proc.registerOutParameter(10,java.sql.Types.INTEGER);
		proc.execute();
		
		attrId=proc.getInt(10);
		proc.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}

	return attrId;
}

public Vector<DTOAttributeValueMatrix> getAttributeValueByAttrId(int attrId) 
{
	Vector<DTOAttributeValueMatrix> data = new Vector<DTOAttributeValueMatrix>();
	try 
	{		  
		String whr="iAttrId="+attrId;
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=dataTable.getDataSet(con,"*","attributeValueMatrix",whr,"iAttrValueId");
		while(rs.next())
		{
			DTOAttributeValueMatrix dto = new DTOAttributeValueMatrix();
			dto.setAttrValueId(rs.getInt("iAttrValueId"));//attrValueId
			dto.setAttrId(rs.getInt("iAttrId"));//attrId
			dto.setAttrValue(rs.getString("vAttrValue"));//attributeValue
			dto.setProjectType(rs.getString("cProjectType").charAt(0));//projectType
			dto.setModifyBy(rs.getInt("iModifyBy"));//modifyBy
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));//modifyOn
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));//statusIndi
			data.addElement(dto);
		}
		rs.close();
		con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}
	
	return data;
}
	
public void insertAttributeMatrix(DTOAttributeValueMatrix obj,int userId)  
{
	try
	{	    	
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call Insert_IntoAttributeValueMatrix  (?,?,?,?,?,?)}");
		proc.setInt(1, obj.getAttrId());
		proc.setString(2, obj.getAttrValue());
		proc.setString(3,"");
		proc.setString(4, obj.getAttrMatrixDisplayValue());
		proc.setInt(5, userId);
		proc.setInt(6,1);
		proc.execute();
		proc.close();
		con.close();
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
			   
}

public void editAttributeMatrix(DTOAttributeValueMatrix obj,int userId)  
{
	try
	{	    	
		Connection con=ConnectionManager.ds.getConnection();
		CallableStatement proc = con.prepareCall("{ Call Insert_IntoAttributeValueMatrix  (?,?,?,?,?,?)}");
		proc.setInt(1, obj.getAttrId());
		proc.setString(2, obj.getAttrValue());
		proc.setString(3,obj.getAttrOldValue());
		proc.setString(4,"");
		proc.setInt(5, userId);
		proc.setInt(6,2);
		proc.execute();
		proc.close();
		con.close();
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
			   
}

public Vector<DTOAttrForIndiMst> getAllAtrrForIndi() 
{
	Vector<DTOAttrForIndiMst> data = new Vector<DTOAttrForIndiMst>();
	try
	{	    	
				
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=dataTable.getDataSet(con,"*","attrForIndiMst","","");
		while(rs.next())
		{
			DTOAttrForIndiMst objattrindi=new DTOAttrForIndiMst();
			objattrindi.setAttrForIndiId(rs.getString("vattrForIndiId"));
			objattrindi.setAttrForIndiName(rs.getString("vattrForIndiName"));
			objattrindi.setRemark(rs.getString("vremark"));
			objattrindi.setModifyOn(rs.getTimestamp("dmodifyOn"));
			objattrindi.setModifyBy(rs.getInt("imodifyBy"));
			objattrindi.setStatusIndi((rs.getString("cstatusIndi")).charAt(0));
			data.addElement(objattrindi);
			objattrindi=null;
		}
		rs.close();
		con.close();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	
	return data;
}

public Vector<DTOAttributeMst> getAllAttributeForAttrDtl()
{
    Vector<DTOAttributeMst> data = new Vector<DTOAttributeMst>();
	try 
	{
		String whr=" attrForIndiId = '0001' ";
		Connection con = ConnectionManager.ds.getConnection();
		String FieldNames = "attrId,attrName,attrForIndiId,remark,attrValueId,attrValue,attrMatrixValue";
		ResultSet rs=dataTable.getDataSet(con,FieldNames, "view_getAttributeByAttrForIndi",whr,"AttrId");
		while (rs.next())
		{
		    DTOAttributeMst attrMstDTO = new DTOAttributeMst();    
			attrMstDTO.setAttrId(rs.getInt("attrid"));
			attrMstDTO.setAttrName(rs.getString("attrname"));
			attrMstDTO.setAttrForIndiId(rs.getString("attrForIndiId"));
			attrMstDTO.setRemark(rs.getString("remark"));
			attrMstDTO.setAttrValueId(rs.getInt("attrvalueId"));
			attrMstDTO.setAttrValue(rs.getString("attrvalue"));
			Vector<String> temp = new Vector<String>();
			temp.addElement(rs.getString("attrmatrixvalue"));
			attrMstDTO.setAttrMatrixValueVect(temp);
			data.addElement(attrMstDTO);
			attrMstDTO=null;
		}    	
		rs.close();
		con.close();
		
	}
	catch(SQLException e){
	    e.printStackTrace();
	}
	return data;
}

public Vector<DTOAttributeMst> getAllAttributeForSTF()
{
    Vector<DTOAttributeMst> data = new Vector<DTOAttributeMst>();
	try 
	{
		Connection con = ConnectionManager.ds.getConnection();
		String fieldNames = "attrId,attrName,attrForIndiId,remark,attrValueId,attrValue,attrMatrixValue ";
		ResultSet rs=dataTable.getDataSet(con,fieldNames,"view_getAttributeByAttrForIndi","AttrForIndiId ='0001' OR attrname = 'Submission Type'","attrid");
		while (rs.next())  
		{
		    DTOAttributeMst attrMstDTO = new DTOAttributeMst();    
		    attrMstDTO.setAttrId(rs.getInt("attrId"));
		    attrMstDTO.setAttrName(rs.getString("attrName"));
		    attrMstDTO.setAttrForIndiId(rs.getString("attrForIndiId"));
		    attrMstDTO.setRemark(rs.getString("remark"));
		    attrMstDTO.setAttrValueId(rs.getInt("attrValueId"));
		    attrMstDTO.setAttrMatrixValue(rs.getString("attrMatrixValue"));
		    //attrMstDTO.setAttrMatrixDisplayValue(rs.getString("attrDisplayValue"));
		    attrMstDTO.setAttrValue(rs.getString("attrValue"));
		    data.addElement(attrMstDTO);
		    attrMstDTO=null;
		}  
		rs.close();
		con.close();
	}
	catch(SQLException e){
	    e.printStackTrace();
	}
	
	return data;
}

public Vector<DTOAttributeMst> getAttrDetailById(int attrId) 
{
	
	Vector<DTOAttributeMst> attribVector = new Vector<DTOAttributeMst>();
	try 
	{		  
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs=dataTable.getDataSet(con,"*","attributemst","iAttrId="+attrId ,"" );
		while(rs.next())
		{
			DTOAttributeMst dto=new DTOAttributeMst();
			dto.setAttrId(rs.getInt("iattrid"));
			dto.setAttrName(rs.getString("vattrname"));
			dto.setAttrForIndiId(rs.getString("vattrforIndiId"));
			dto.setAttrType(rs.getString("vAttrType"));
			dto.setRemark(rs.getString("Vremark"));
			dto.setModifyBy(rs.getInt("iModifyBy"));
			dto.setModifyOn(rs.getTimestamp("dModifyOn"));
			dto.setStatusIndi(rs.getString("cStatusIndi").charAt(0));
			attribVector.addElement(dto);
			dto=null;
		}
		 rs.close();
		 con.close();
	}   
	catch(SQLException e){
		e.printStackTrace();
	}

	return attribVector;
}

public Vector<DTOAttributeMst> getAttrDetailForSearch() 
{
	   
    Vector<DTOAttributeMst> data = new Vector<DTOAttributeMst>();
    Connection con = null;
    ResultSet rs = null;
	try 
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con,"*", "attributemst", "", "iAttrId");
		while (rs.next())
		{
		    DTOAttributeMst obj = new DTOAttributeMst();
		    obj.setAttrId(rs.getInt("iAttrId"));
		    obj.setAttrName(rs.getString("vAttrName"));
		    obj.setAttrForIndiId(rs.getString("vAttrForIndiId"));
		    obj.setAttrValue(rs.getString("vAttrValue"));
		    obj.setAttrType(rs.getString("vAttrType"));
		    data.addElement(obj);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	finally{
		try{if(rs!=null)rs.close();}catch (Exception e) {e.printStackTrace();}
		try{if(con!=null)con.close();}catch (Exception e) {e.printStackTrace();}
	}
    return data;
}
public Vector<DTOAttributeMst> getSelectedAttrDetail() 
{
	   
    Vector<DTOAttributeMst> data = new Vector<DTOAttributeMst>();
    Connection con = null;
    ResultSet rs = null;
	try 
	{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con,"*", "attributemst", "vAttrName in('Country','Site Name','Site Number') and cStatusIndi <> 'D'", "iAttrId");
		while (rs.next())
		{
		    DTOAttributeMst obj = new DTOAttributeMst();
		    obj.setAttrId(rs.getInt("iAttrId"));
		    obj.setAttrName(rs.getString("vAttrName"));
		    obj.setAttrForIndiId(rs.getString("vAttrForIndiId"));
		    obj.setAttrValue(rs.getString("vAttrValue"));
		    obj.setAttrType(rs.getString("vAttrType"));
		    data.addElement(obj);
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	finally{
		try{if(rs!=null)rs.close();}catch (Exception e) {e.printStackTrace();}
		try{if(con!=null)con.close();}catch (Exception e) {e.printStackTrace();}
	}
    return data;
}


public Vector<DTOAttributeValueMatrix> getAttributeDetailByType(String attrForIndi)
{
    
    Vector<DTOAttributeValueMatrix> data = new Vector<DTOAttributeValueMatrix>();
    try 
    {
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs =dataTable.getDataSet(con,"*", "view_getAttributeByAttrForIndi", "attrForIndiId = '"+attrForIndi+"'", "");
		while (rs.next())
		{
		    DTOAttributeValueMatrix obj = new DTOAttributeValueMatrix();
		    obj.setAttrId(rs.getInt("attrId"));
		    obj.setAttrName(rs.getString("attrName"));
		    obj.setAttrType(rs.getString("attrType"));
		    obj.setAttrValue(rs.getString("attrMatrixValue"));
		    obj.setAttrMatrixDisplayValue(rs.getString("attrDisplayValue"));
		    obj.setAttrForIndiId(rs.getString("attrForIndiId"));
		    obj.setRemark(rs.getString("remark"));
		    data.addElement(obj);
		}
		rs.close();
		con.close();

    }catch(SQLException e){
		e.printStackTrace();
	}	
    return data;
}

public Vector<DTOAttributeMst> getAttrForAttrGroupBySpecifiedAttrType(String attrType)
{
	Vector<DTOAttributeMst> data = new Vector<DTOAttributeMst>();
	try 
	{
		StringBuffer query = new StringBuffer();
		Connection con = ConnectionManager.ds.getConnection();
		if(attrType!=null && attrType.equalsIgnoreCase("0001"))
			query.append("  vAttrForIndiId = '0001' ");
		else if(attrType!=null && attrType.equalsIgnoreCase("0002"))					
			query.append(" vAttrForIndiId = '0002' ");
		else if(attrType!=null && attrType.equalsIgnoreCase("0003"))
			query.append("  vAttrForIndiId = '0003' ");
		else if(attrType!=null && attrType.equalsIgnoreCase("0000"))//else if block added by Ashmak Shah (For project level Attributes)
			query.append("  vAttrForIndiId = '0000' ");
		else query.append("  vAttrForIndiId IN('0001','0002','0003') ");
			ResultSet rs = dataTable.getDataSet(con, "vAttrName, iAttrId ,vAttrForIndiId,vAttrValue,vAttrType", "attributeMst", query.toString(), "vAttrName");
		while (rs.next())
		{
			DTOAttributeMst attrMstDTO = new DTOAttributeMst();    
			attrMstDTO.setAttrName(rs.getString("vAttrName"));
			attrMstDTO.setAttrId(rs.getInt("iAttrId"));
			attrMstDTO.setAttrForIndiId(rs.getString("vAttrForIndiId"));
			attrMstDTO.setAttrValue(rs.getString("vAttrValue"));
			attrMstDTO.setAttrType(rs.getString("vAttrType"));
			data.addElement(attrMstDTO);
			attrMstDTO=null;
		}    	

		rs.close();
		con.close();
	}
	catch(SQLException e){
	    e.printStackTrace();
	}
	
	return data;
}

public Vector<DTOAttributeMst> getAttributeDetailsByAttrName(String AttrName){
	Vector<DTOAttributeMst> AttrDtl = new Vector<DTOAttributeMst>();
	
	try{
		Connection con = ConnectionManager.ds.getConnection();
		ResultSet rs = dataTable.getDataSet(con, "*", "AttributeMst", " vAttrName= '"+ AttrName +"'", "");
		
		while (rs.next())
		{
			DTOAttributeMst attrMstDTO = new DTOAttributeMst();    
			attrMstDTO.setAttrName(rs.getString("vAttrName"));
			attrMstDTO.setAttrId(rs.getInt("iAttrId"));
			attrMstDTO.setAttrForIndiId(rs.getString("vAttrForIndiId"));
			attrMstDTO.setAttrValue(rs.getString("vAttrValue"));
			attrMstDTO.setAttrType(rs.getString("vAttrType"));
			AttrDtl.addElement(attrMstDTO);
			attrMstDTO=null;
		}    	

		rs.close();
		con.close();
		
	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return AttrDtl;
}
/**
 * Display Type in Terms of the date , text, textArea etc..
 * @param displayType
 * @return
 */
public ArrayList<DTOAttributeMst> getAttributesByDisplayType(String displayType){ 
	ArrayList<DTOAttributeMst> AttrDtl = new ArrayList<DTOAttributeMst>();
	Connection con = null ;
	ResultSet rs = null ;
	try{
		con = ConnectionManager.ds.getConnection();
		rs = dataTable.getDataSet(con, "*", "AttributeMst", " vAttrType= '"+ displayType +"'", "");
		
		while (rs.next())
		{
			DTOAttributeMst attrMstDTO = new DTOAttributeMst();    
			attrMstDTO.setAttrName(rs.getString("vAttrName"));
			attrMstDTO.setAttrId(rs.getInt("iAttrId"));
			attrMstDTO.setAttrForIndiId(rs.getString("vAttrForIndiId"));
			attrMstDTO.setAttrValue(rs.getString("vAttrValue"));
			attrMstDTO.setAttrType(rs.getString("vAttrType"));
			AttrDtl.add(attrMstDTO);
			attrMstDTO=null;
		}
	}catch(SQLException e){
		e.printStackTrace();
	}
	finally{
		try{if(rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
		try{if(con != null)con.close();}catch (Exception e) {e.printStackTrace();}
		}
	return AttrDtl;
}
/**
 * returns all attribute details including attributeMatrixValue values of attribute ids.
 */
	public ArrayList<DTOAttributeMst> getAttributesFullDetail(ArrayList<Integer> attrIds)
	{
	    ArrayList<DTOAttributeMst> data = new ArrayList<DTOAttributeMst>();
	    int prevAttrId=-1;Vector<String> attrValueVector = null;
	    String attIdString = attrIds.toString().replaceAll("\\s", "");
	    attIdString = attIdString.substring(1,attIdString.length()-1);
	    
	    Connection con = null;
	    ResultSet rs = null;
	   
		try 
		{
			String whr = "attrId in ("+attIdString +")";
			con = ConnectionManager.ds.getConnection();
			String FieldNames = "attrId,attrName,attrForIndiId,remark,attrValueId,attrValue,attrMatrixValue,attrType";
			rs=dataTable.getDataSet(con,FieldNames, "view_getAttributeByAttrForIndi",whr,"attrId");
			
			while (rs.next())
			{
			    //if same attribute found then add its matrix value to the value vector 
				if(prevAttrId == rs.getInt("attrid")){
			    	attrValueVector.addElement(rs.getString("attrmatrixvalue"));
			    	continue;
			    }
				DTOAttributeMst attrMstDTO = new DTOAttributeMst();
			    attrMstDTO.setAttrId(rs.getInt("attrid"));
				attrMstDTO.setAttrName(rs.getString("attrname"));
				attrMstDTO.setAttrForIndiId(rs.getString("attrForIndiId"));
				attrMstDTO.setRemark(rs.getString("remark"));
				attrMstDTO.setAttrValueId(rs.getInt("attrvalueId"));
				attrMstDTO.setAttrValue(rs.getString("attrvalue"));
				attrMstDTO.setAttrType(rs.getString("attrType"));
				attrValueVector = new  Vector<String>();
				attrValueVector.addElement(rs.getString("attrmatrixvalue"));
				attrMstDTO.setAttrMatrixValueVect(attrValueVector);
				data.add(attrMstDTO);
				prevAttrId = rs.getInt("attrid");
			}    	
		}
		catch(Exception e){
		    e.printStackTrace();
		}
		finally{
			try{if(rs != null)rs.close();}catch (Exception e) {e.printStackTrace();}
			try{if(con != null)con.close();}catch (Exception e) {e.printStackTrace();}
			}
		return data;
	}

	public int getAttribuuteIdByName(String attributeName) 
	{
		Connection con;
		int attrId = 0;
		
		try
		{
			con = ConnectionManager.ds.getConnection();
		 
			ResultSet rs=dataTable.getDataSet(con,"iAttrId","attributemst", "vAttrName = '" + attributeName + "'","");
			while(rs.next())
			{
				attrId = rs.getInt(1);
			} 
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			}
		return attrId;
	}
}//main class end
