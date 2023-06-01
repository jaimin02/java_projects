package com.docmgmt.server.db.master;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import com.docmgmt.dto.DTOContentSearch;
import com.docmgmt.dto.DTOCountryMst;
import com.docmgmt.dto.DTOHotsearchDetail;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.dbcp.Configuration;
import com.docmgmt.server.db.dbcp.ConnectionManager;

public class HotSearchDetail {

ConnectionManager conMgr;
DataTable dataTable;
	
	public HotSearchDetail()
	{
		ConnectionManager conMgr=new ConnectionManager(new Configuration());
		dataTable=new DataTable();
	}

public Vector getClientForSearch(int UserId)  
{
   	Vector resultVector = new Vector();
   	try 
   	{
   		DTOHotsearchDetail hotsearchDtl = new DTOHotsearchDetail();
   		hotsearchDtl.setClientId("ALL");
   		hotsearchDtl.setClientName("----ALL----");			
		resultVector.add(hotsearchDtl);
   		Connection con = conMgr.ds.getConnection();
		ResultSet rs=dataTable.getDataSet(con,"distinct ClientCode,ClientName","View_ProjectForSearch","userCode="+UserId,"");
    	while(rs.next()) 
    	{
    		DTOHotsearchDetail hotsearchDetail = new DTOHotsearchDetail();
    		hotsearchDetail.setClientId(rs.getString("ClientCode"));
    		hotsearchDetail.setClientName(rs.getString("ClientName"));
    		resultVector.add(hotsearchDetail);
    		hotsearchDetail = null;
    	}
    	rs.close();
    	con.close();
   	}catch(SQLException e) {
   		e.printStackTrace();
   	}
   	return resultVector;
}

public Vector getLocationForSearch(int UserId) 
{
	Vector resultVector = new Vector();
	try 
	{
		DTOHotsearchDetail hotsearchDtl = new DTOHotsearchDetail();
		hotsearchDtl.setLocationId("ALL");
		hotsearchDtl.setLocationName("----ALL----");
		resultVector.add(hotsearchDtl);			
		hotsearchDtl = null;	
		Connection con = conMgr.ds.getConnection();
		ResultSet rs=dataTable.getDataSet(con,"distinct LocationCode,LocationName","View_ProjectForSearch","userCode="+UserId,"");
		while(rs.next()) 
		{
			DTOHotsearchDetail hotsearchDetail = new DTOHotsearchDetail();
			hotsearchDetail.setLocationId(rs.getString("LocationCode"));
			hotsearchDetail.setLocationName(rs.getString("LocationName"));
			resultVector.add(hotsearchDetail);
			hotsearchDetail = null;
		}

		for(int i = 0; i < resultVector.size(); i++) {
			DTOHotsearchDetail hotsearchDetail = (DTOHotsearchDetail)resultVector.elementAt(i);
		}
		rs.close();
		con.close();
	   
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return resultVector;
}
	 
public Vector getProjectForSearch(int UserId)  
{
   	Vector resultVector = new Vector();
   	try 
   	{
   		DTOHotsearchDetail hotsearchDtl = new DTOHotsearchDetail();
   		hotsearchDtl.setProjectId("ALL");
   		hotsearchDtl.setProjectName("----ALL----");			
   		resultVector.add(hotsearchDtl);
   		hotsearchDtl = null;
   		Connection con = conMgr.ds.getConnection();
	    ResultSet rs=dataTable.getDataSet(con,"distinct ProjectCode,ProjectName","View_ProjectForSearch","userCode="+UserId,"");
	    while(rs.next()) 
	    {
	    	DTOHotsearchDetail hotsearchDetail = new DTOHotsearchDetail();
	    	hotsearchDetail.setProjectId(rs.getString("ProjectCode"));
	    	hotsearchDetail.setProjectName(rs.getString("ProjectName"));
	    	resultVector.add(hotsearchDetail);
	    	hotsearchDetail = null;
	    }
	    rs.close();
	    con.close();
	    
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return resultVector;
}	 
	    
	/* this function is service layer function and used in HotSearchAction */ 
public Vector getUserDetailsByUserGrp(DTOUserMst userMst) 
{
	UserMst userMstService = new UserMst();
	Vector userDetailsByUserGrpDtl = userMstService.getuserDetailsByUserGrp(userMst.getUserGroupCode());
	Vector userDetailsByUserGrpDtlALL= new Vector();
	DTOUserMst userMstNew = new DTOUserMst();
	userMstNew.setUserCode(0);
	userMstNew.setLoginName("---ALL----");
	userMstNew.setUserName("---ALL----");
	userDetailsByUserGrpDtlALL.add(userMstNew);
	userMstNew = null;
	for(int i = 0 ; i < userDetailsByUserGrpDtl.size(); i++)
	{
		DTOUserMst  userMstNew1 = new DTOUserMst();
		userMstNew1 = (DTOUserMst)userDetailsByUserGrpDtl.elementAt(i);
		userDetailsByUserGrpDtlALL.add(userMstNew1);
		userMstNew1 = null;    		
	}
	return userDetailsByUserGrpDtlALL;
	
}
	  
public Vector getGenericSearchResult(String locId,String clientId, String prjId, String wsId, String userId,int limit)   
{
	Vector resultData = new Vector();
	try
	{        
		Connection con = conMgr.ds.getConnection();
		CallableStatement cs=con.prepareCall("{call Proc_genericSearch(?,?,?,?,?)}");
		cs.setString(1,wsId);
		cs.setInt(2,Integer.parseInt(userId));
		cs.setString(3,locId);
		cs.setString(4,prjId);
		cs.setString(5,clientId);
		ResultSet rs = cs.executeQuery();
		while(rs.next()) 
		{
			DTOHotsearchDetail hotsearchDetail = new DTOHotsearchDetail();
			hotsearchDetail.setWorkspaceId(rs.getString("vWorkSpaceId"));
			hotsearchDetail.setWorkspaceDesc(rs.getString("vWorkSpaceDesc"));
			hotsearchDetail.setNodeId(rs.getInt("iNodeId"));
			hotsearchDetail.setTranNo(rs.getInt("iTranNo"));
			hotsearchDetail.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
			hotsearchDetail.setLocationName(rs.getString("vLocationName"));
			hotsearchDetail.setClientName(rs.getString("vClientName"));
			hotsearchDetail.setProjectName(rs.getString("vProjectName"));
			hotsearchDetail.setFileName(rs.getString("vFileName"));
			hotsearchDetail.setModifyBy(rs.getInt("iModifyBy"));
			hotsearchDetail.setModifyOn(rs.getTimestamp("dModifyOn"));
			hotsearchDetail.setLoginName(rs.getString("vLoginName"));
			resultData.add(hotsearchDetail);
			hotsearchDetail = null;            		
	    }
		
		rs.close();
		cs.close();
		con.close();
	    
	}catch(SQLException ex) {
		ex.printStackTrace();
	}
		return resultData;
}
public boolean insertHotSearchDetail(DTOHotsearchDetail hotsearchDetail)
{    
          
	String hotsearchDesc = hotsearchDetail.getHotSearchDesc();
	String projectId = hotsearchDetail.getProjectId();
	String WorkSpaceId = hotsearchDetail.getWorkspaceId();
	String clientId = hotsearchDetail.getClientId();
	String regionId = hotsearchDetail.getLocationId();
	Integer modifyBy = new Integer(hotsearchDetail.getModifyBy());
	Integer lastModifiedBy = new Integer(hotsearchDetail.getUserCode());
	String attrName = "NULL";
	String attrValue = "NULL";
	String relation = "NULL";
	try
	{	
		Connection con=conMgr.ds.getConnection();
		CallableStatement cs=con.prepareCall("{call Insert_HotSearchDetail(?,?,?,?,?,?,?,?,?,?,?,?)}");
		cs.setString(1,""); //not require in insert
		cs.setString(2, hotsearchDesc);
		cs.setString(3, projectId);
		cs.setString(4, WorkSpaceId);
		cs.setString(5, clientId);
		cs.setString(6, regionId);
		cs.setInt(7, lastModifiedBy.intValue());
		cs.setString(8, attrName);
		cs.setString(9, attrValue);
		cs.setString(10, relation);
		cs.setInt(11,modifyBy.intValue());
		cs.setInt(12,1); //datamode 1 for add
		ResultSet rs=cs.executeQuery();
		if(rs.next())
		{
			if(rs.getString(1).equalsIgnoreCase("false"))
				return false;
			else
				return true;
		}
		rs.close();
		cs.close();
		con.close();

	}catch(SQLException e){
		e.printStackTrace();
	}
	return true;
}

public Vector getAllDetailAboutSavedSearch(int userId) 
{
	
	Vector resultVector = new Vector();
	try 
	{
		Connection con = conMgr.ds.getConnection();
	 	ResultSet rs=dataTable.getDataSet(con,"*","view_HotSearch","modifyBy="+userId,"");
	 	while(rs.next())
	 	{
	 		DTOHotsearchDetail hotsearchDetail = new DTOHotsearchDetail();
	 		hotsearchDetail.setHotSearchDesc(rs.getString("hotSearchDesc"));
	 		hotsearchDetail.setHotSearchId(rs.getString("HotSearchId"));
	 		hotsearchDetail.setClientId(rs.getString("clientId"));
	 		hotsearchDetail.setProjectId(rs.getString("projectId"));
	 		hotsearchDetail.setWorkspaceId(rs.getString("workspaceId"));
	 		hotsearchDetail.setLastModifiedBy(rs.getInt("lastModifiedBy"));
	 		hotsearchDetail.setLocationName(rs.getString("locationName"));
	 		hotsearchDetail.setClientName(rs.getString("clientName"));
	 		hotsearchDetail.setProjectName(rs.getString("projectName"));
	 		hotsearchDetail.setWorkspaceDesc(rs.getString("projectName"));
	 		hotsearchDetail.setUserName(rs.getString("userName"));
	 		hotsearchDetail.setLocationId(rs.getString("RegionId"));
	 		resultVector.add(hotsearchDetail);
	 		hotsearchDetail = null;
	 	}
	 	rs.close();
	 	con.close();
	 	
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return resultVector;
}

public void deleteHotSearch(String hotSearchId) 
{
	try
	{
		Connection con = conMgr.ds.getConnection();
		CallableStatement cs=con.prepareCall("{call Insert_HotSearchDetail(?,?,?,?,?,?,?,?,?,?,?,?)}");
		cs.setString(1,hotSearchId); //require in delete
		cs.setString(2, "");
		cs.setString(3,"" );
		cs.setString(4, "");
		cs.setString(5, "");
		cs.setString(6, "");
		cs.setInt(7, 1);
		cs.setString(8,"");
		cs.setString(9,"" );
		cs.setString(10,"");
		cs.setInt(11,1);
		cs.setInt(12,2); //datamode 2 for delete
		cs.execute();
		cs.close();
		con.close();
		
	}catch(SQLException e){
		e.printStackTrace();
	}
}
	   

public Vector<DTOHotsearchDetail> getDMSSearchResult(Map<String, String> attributes ,int userId)   
{
	//Only required attributes should be there in 'attributes' Map 
	
	Vector<DTOHotsearchDetail> resultVector = new Vector<DTOHotsearchDetail>();
	try 
	{
		Connection con = conMgr.ds.getConnection();
	 	String where = "";
	 	boolean orRequired = false;
	 	
	 	where += " iUserCode = " + Integer.toString(userId);
	 	where += " AND (";//block start
	 	
	 	for(Iterator<Entry<String, String>> i = attributes.entrySet().iterator(); i.hasNext();) {
	 		
	 		Entry<String ,String> entry = i.next();
	 		
	 		if(orRequired){where += " OR ";}
	 		
	 		//attribute conditions (=,LIKE,>,< etc...) should be passed within the Entry keys and values
	 		where += " ( vAttrName "+entry.getKey()+" AND vAttrValue "+entry.getValue()+" )";
	 		orRequired = true;
	 	}
	 	
	 	if(where.charAt(where.length()-1) == '('){
			where = where.substring(0, where.length()-1);// removing '()'
		}
		else{
			where += ")"; //Completion of '()'
		}

	 	String fields ="vWorkspaceId,vWorkspaceDesc,iNodeId,vNodeDisplayName,vAttrName,vAttrValue";
		ResultSet rs=dataTable.getDataSet(con,fields,"view_DMSSearch",where,"vAttrName");
	 	while(rs.next())
	 	{
	 		DTOHotsearchDetail hotsearchDetail = new DTOHotsearchDetail();
	 		hotsearchDetail.setWorkspaceId(rs.getString("vWorkspaceId"));
	 		hotsearchDetail.setWorkspaceDesc(rs.getString("vWorkspaceDesc"));
	 		hotsearchDetail.setNodeId(rs.getInt("iNodeId"));
	 		hotsearchDetail.setNodeDisplayName(rs.getString("vNodeDisplayName"));
	 		hotsearchDetail.setAttrName(rs.getString("vAttrName"));
	 		hotsearchDetail.setAttrValue(rs.getString("vAttrValue"));
	 		
	 		resultVector.add(hotsearchDetail);
	 		hotsearchDetail = null;
	 	}
	 	rs.close();
	 	con.close();
	 	
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return resultVector;
}

public Vector<DTOCountryMst> getDMSRegions(){
	
	Vector<DTOCountryMst> regions= new Vector<DTOCountryMst>();
	
	try{
		Connection con = conMgr.ds.getConnection();
		Statement stmt = con.createStatement();
		
		String query = "SELECT DISTINCT wsnad.vAttrValue, ISNULL(cm.vCountryName,'') as vCountryName FROM WorkspaceNodeAttrDetail as wsnad "

					 +" INNER JOIN  (SELECT vWorkspaceId,iNodeId "
					 				+" FROM WorkspaceNodeDetail "
					 				+" WHERE vNodeName = 'Regional-Information' "
					 			+" ) as RegionInfo "
					 +" ON wsnad.vWorkspaceId = RegionInfo.vWorkspaceId " 
					 +" AND wsnad.iNodeId = RegionInfo.iNodeId "
					 
					 +" LEFT JOIN CountryMst cm "
					 +" ON wsnad.vAttrValue = cm.vCountryCode "
					 
					 +" WHERE wsnad.vAttrName = 'Name' AND wsnad.vAttrValue <> '' ";
		
		ResultSet rs = stmt.executeQuery(query);
		
		while(rs.next()){
			
			DTOCountryMst region = new DTOCountryMst();
			region.setCountryCode(rs.getString("vAttrValue"));
			region.setCountryName(rs.getString("vCountryName"));
			
			regions.addElement(region);
		}
		
	}catch(SQLException e){
		e.printStackTrace();
	}
	
	return regions;
}
 
public List<List<String>> getProjectLevelSearchResult(Map<String,String>inputFields, List<String> outputFields)   
{
	List<List<String>> data= new ArrayList<List<String>>();
	
	try 
	{
		Connection con = conMgr.ds.getConnection();
	 	String where = "";
	 	boolean AND_Required = false;
	 	
	 	for (Iterator<Entry<String,String>> iterator = inputFields.entrySet().iterator(); iterator.hasNext();) {
	 		Entry<String,String> curField = iterator.next();
	 		
	 		if(!curField.getValue().equals("")){
		 		if(AND_Required)
		 			where += " AND ";
		 		where += " "+curField.getKey()+" = '"+curField.getValue()+"'";
		 		AND_Required = true;
		 	}
		}
	 	
	 	
	 	String fields = "";
	 	//Get Blank Structure
	 	ResultSet rsStruct=dataTable.getDataSet(con,"*","View_Project_Level_Search"," 1 = 2 ","");
	 	ResultSetMetaData metaData = rsStruct.getMetaData();
	 	int colCnt = metaData.getColumnCount();
	 	//outputFields.add(0,"vWorkSpaceDesc");
	 	//outputFields.add(1,"Country");
	 	//outputFields.add(2,"Generic_Name");
	 	for (int i = 0 ; i < outputFields.size() ; i++){
	 		String fieldName = outputFields.get(i).replaceAll("[\\s-]","_");
	 		boolean colFound = false;
	 		
	 		for (int iCol = 1; iCol <= colCnt; iCol++){
		 		String col_name = metaData.getColumnName(iCol);
		 		if(col_name.equalsIgnoreCase(fieldName)){
		 			colFound = true;
		 			break;
		 		}
		 	}
	 		if (!colFound) {//If column not found then skip 
	 			if(!fieldName.startsWith("string:")){
	 				outputFields.remove(i--);
	 			}
	 			continue;
			}
	 		
	 		if(fieldName.startsWith("string:")){
	 			fieldName = "'"+fieldName.substring(fieldName.indexOf(":")+1)+"'";
	 		}
	 		
	 		if(fields.equals(""))
	 			fields = fieldName;
	 		//In following View, field names contain '_' instead of spaces and '-'.
	 		else 
	 			fields += ","+fieldName;
	 	}
	 	System.out.println("fields:"+fields);
	 	System.out.println("where:"+where);
		
	 	ResultSet rs=dataTable.getDataSet(con,fields,"View_Project_Level_Search",where,"");
	 	while(rs.next())
	 	{
	 		List<String> dataRecord= new ArrayList<String>();
	 		
	 		for(int  j = 0 ; j < outputFields.size(); j++){
	 			String fieldName = outputFields.get(j).replaceAll("[\\s-]","_");
	 			if(fieldName.startsWith("string:")){
	 				dataRecord.add(rs.getString(j+1));
		 		}else{
		 			dataRecord.add(rs.getString(fieldName));
		 		} 			
	 		} 		
	 		data.add(dataRecord);
	 	}
	 	rs.close();
	 	con.close();
	 	
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return data;
}
public ArrayList<String> getProjectLevelAttributeValues(String columnName){
	
		ArrayList<String> attribute = new ArrayList<String>();
	try{
		Connection con = conMgr.ds.getConnection();
		String where = columnName+ " is not null and " +columnName+" <> ''";
		ResultSet rs = dataTable.getDataSet(con, "Distinct Generics", "View_Project_Level_Search", where, columnName);
		
		while(rs.next())
		{
			attribute.add(rs.getString("Generics"));
		}
		rs.close();
		con.close();
	}catch (Exception e) {
		e.printStackTrace();
	}
	return attribute;
}
/**
 * ******DateWiseSearch()******
 * @param userCode
 * @param attributes
 * @param attrIndiid
 * @param firstSearchValue
 * @param secondSearchValue
 * @param mode (1 = OnDate, 2 = BeforeDate, 3 = AfterDate, 4 = From-To Date)
 * @return
 */
public static ArrayList<DTOHotsearchDetail> dateWiseSearch(int userCode,String attributes, String attrIndiid, String firstSearchValue, String secondSearchValue, int mode)   
{
	ArrayList<DTOHotsearchDetail> data = new ArrayList<DTOHotsearchDetail>();
	ResultSet rs = null;
	Connection con = null;
	CallableStatement cs = null;
	try
	{        
		con = ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Proc_DateWise_FullSearch(?,?,?,?,?,?)}");
		cs.setInt(1,userCode);
		cs.setString(2,attributes);
		cs.setString(3,attrIndiid);
		cs.setString(4,firstSearchValue);
		cs.setString(5,secondSearchValue);
		cs.setInt(6,mode);
		rs = cs.executeQuery();
		while(rs.next()) 
		{
			DTOHotsearchDetail searchDetail = new DTOHotsearchDetail();
			searchDetail.setWorkspaceId(rs.getString("vWorkSpaceId"));
			searchDetail.setWorkspaceDesc(rs.getString("vWorkSpaceDesc"));
			searchDetail.setNodeId(rs.getInt("iNodeId"));
			searchDetail.setNodeName(rs.getString("vNodeName"));
			searchDetail.setFolderName(rs.getString("vFolderName"));
			searchDetail.setFileName(rs.getString("vFileName"));
			searchDetail.setModifyBy(rs.getInt("iModifyBy"));
			searchDetail.setAttrName(rs.getString("vattrName"));
			searchDetail.setAttrValue(rs.getString("vattrValue"));
			searchDetail.setAttrforindiid(rs.getString("vAttrForIndiId"));
			searchDetail.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
			searchDetail.setFileFolderName(rs.getString("FileFolderName"));
			searchDetail.setFileversionid(rs.getString("vUserDefineVersionId"));
			data.add(searchDetail);
			searchDetail = null;            		
	    }
	}catch(SQLException ex) {
		ex.printStackTrace();
	}
	finally{
		try{if(cs!=null)cs.close();}catch(Exception e){e.printStackTrace();}
		try{if(rs!=null)rs.close();}catch(Exception e){e.printStackTrace();}
		try{if(con!=null)con.close();}catch(Exception e){e.printStackTrace();}		
				
	}
		return data;
}

/**
 * 
 * @param userCode
 * @param keyValue //Search for the value Entered by User 
 * @param searchBy // Like wise or Equal
 * @param Mode // 1 = Project Name Search, 2 = Nodesearch, 4 = Attribute name or value Search, 3 = Comment 
 * @return
 */
public static ArrayList<DTOContentSearch> contentSearch(int userCode,String keyValue,String searchBy, int Mode)   
{
	ArrayList<DTOContentSearch> data = new ArrayList<DTOContentSearch>();
	ResultSet rs = null;
	Connection con = null;
	CallableStatement cs = null;
	try
	{        
		con = ConnectionManager.ds.getConnection();
		cs=con.prepareCall("{call Proc_Contentwise_FullSearch(?,?,?,?)}");
		cs.setInt(1,userCode);
		cs.setString(2,keyValue);
		cs.setString(3,searchBy);
		cs.setInt(4,Mode);
		rs = cs.executeQuery();
		while(rs.next()) 
		{
			DTOContentSearch searchDetail= new DTOContentSearch();
			searchDetail.setWorkspaceid(rs.getString("vWorkSpaceId"));
			searchDetail.setWorkspaceDesc(rs.getString("vWorkSpaceDesc"));
			if(Mode == 1){ //Project Name
				searchDetail.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
			}
			else if(Mode == 2){// Node Name
				searchDetail.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				searchDetail.setNodeId(rs.getInt("iNodeId"));
				searchDetail.setNodeName(rs.getString("vNodeName"));
				searchDetail.setNodeDisplayName(rs.getString("vNodeDisplayName"));
				searchDetail.setFolderName(rs.getString("vFolderName"));
				searchDetail.setFileName(rs.getString("vFileName"));
				searchDetail.setFileFolderName(rs.getString("FileFolderName"));
				searchDetail.setModifyBy(rs.getString("ModifyBy"));	
				searchDetail.setModifyOn(rs.getTimestamp("dModifyOn"));
			}
			else if(Mode == 3){//Comments
				searchDetail.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));				
				searchDetail.setSubjectDesc(rs.getString("Comments"));
				searchDetail.setSubjectId(rs.getString("vSubjectId"));
				searchDetail.setSenderUserName(rs.getString("Sender"));
				searchDetail.setReceiverUserName(rs.getString("Receiver"));
				searchDetail.setModifyOn(rs.getTimestamp("ModifyOn"));
				searchDetail.setNodeId(rs.getInt("iNodeId"));
				searchDetail.setNodeName(rs.getString("vNodeName"));
				searchDetail.setFileName(rs.getString("vFileName"));
				searchDetail.setFileFolderName(rs.getString("vFolderName"));				

			}
			else if (Mode == 4) {//Attribute
				searchDetail.setNodeId(rs.getInt("iNodeId"));
				searchDetail.setNodeName(rs.getString("vNodeName"));
				searchDetail.setFolderName(rs.getString("vFolderName"));
				searchDetail.setFileName(rs.getString("vFileName"));
				searchDetail.setAttrName(rs.getString("vattrName"));
				searchDetail.setAttrValue(rs.getString("vAttrValue"));
				searchDetail.setBaseWorkFolder(rs.getString("vBaseWorkFolder"));
				searchDetail.setFileFolderName(rs.getString("FileFolderName"));				
			}
			data.add(searchDetail);
	    }
	}catch(SQLException ex) {
		ex.printStackTrace();
	}
	finally{
		try{if(cs!=null)cs.close();}catch(Exception e){e.printStackTrace();}
		try{if(rs!=null)rs.close();}catch(Exception e){e.printStackTrace();}
		try{if(con!=null)con.close();}catch(Exception e){e.printStackTrace();}		
				
	}
		return data;
}
}//main class ended
