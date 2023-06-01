package com.docmgmt.struts.actions.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOAttributeValueMatrix;
import com.docmgmt.dto.DTOLocationMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ProjectLevelSearchAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	
	@Override
	public String execute(){
	
		int userGroupCode =  Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		
		
		lstDossierStatusValues = docMgmtImpl.getAttributeValuesFromMatrix("Dossier Status");//This ('Dossier Status') value is copied from database(AttributeMst)
	/*	DTOAttributeValueMatrix dto = new DTOAttributeValueMatrix();
		dto.setAttrName("Dossier Status");
		dto.setAttrMatrixValue("All");
		lstDossierStatusValues.addElement(dto);
	*/
		//ArrayList<String> test = new ArrayList<String>();
		//test= docMgmtImpl.getAttributeValuesForGenerics();
		
		lstCountry= docMgmtImpl.getLocationDtl();
		lstProduct = docMgmtImpl.getProjectLevelAttributeValues("Generics");
		lstSerialNo = docMgmtImpl.getWorkspaceAttributeValues("Serial_No");//This ('Serial No') value is copied from database(AttributeMst)
		
		
		projectLevelAttributes = docMgmtImpl.getAttrForAttrGroupBySpecifiedAttrType("0000");
		System.gc();
		return SUCCESS;
	}
	
	public String showSearchResult(){
	
		if(selectedOutputFields == null || selectedOutputFields.length == 0){
			selectedOutputFields = null;
			
			return SUCCESS;
		}
		
		int userGroupCode =  Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		
		if(searchMode.equals("General_Search")){
			List<String> tableHeader = new ArrayList<String>();
			
			tableHeader.add("Project Id");//Will not be displayed in JSP
			tableHeader.add("Column No");//Will not be displayed in JSP
			tableHeader.add("Project Name");
			tableHeader.add("Country");
			tableHeader.add("Generic Name");
			
			for(int i = 0; i < selectedOutputFields.length ; i++){
				tableHeader.add(selectedOutputFields[i]);
			}
			
			HashMap<String,String> inputFields = new HashMap<String, String>();
			
			inputFields.put("Country", location);
			inputFields.put("Generics", genericName);
			inputFields.put("Dossier_Status", dossierStatus);
			
			
			List<String> lstOutputFields = new ArrayList<String>();
			lstOutputFields.add("vWorkspaceId");//Will not be displayed in JSP
			lstOutputFields.add("string:1");//Will not be displayed in JSP
			lstOutputFields.add("vWorkSpaceDesc");
			lstOutputFields.add("Country");
			lstOutputFields.add("Generics");
			lstOutputFields.addAll(Arrays.asList(selectedOutputFields));
			
			getProjectLevelSearchResult = docMgmtImpl.getProjectLevelSearchResult(inputFields, lstOutputFields);
			
			//For Display of table header in grid
			if(getProjectLevelSearchResult.size() > 0)
				getProjectLevelSearchResult.add(0, tableHeader);
			
		}
		else if(searchMode.equals("Serial_Num_Wise_Search")){
			
			if(serialNo == null || serialNo.equals("")){
				return SUCCESS;
			}
			List<String> tableHeader = new ArrayList<String>();
			tableHeader.add("Project Id");//Will not be displayed in JSP
			tableHeader.add("Column No");//Will not be displayed in JSP
			tableHeader.add("Field");
			tableHeader.add("Value");
			
			List<String> fieldName = new ArrayList<String>();
			//fieldName.add("Project Id");//Will not be displayed in JSP
			//fieldName.add("Column No");//Will not be displayed in JSP
			fieldName.add("Project Name");
			fieldName.add("Country");
			fieldName.add("Generic Name");
			for(int i = 0; i < selectedOutputFields.length ; i++){
				fieldName.add(selectedOutputFields[i]);
			}
			
			HashMap<String,String> inputFields = new HashMap<String, String>();
			inputFields.put("Serial_No", serialNo);
			
			
			List<String> lstOutputFields = new ArrayList<String>();
			lstOutputFields.add("vWorkspaceId");////Will not be displayed in JSP
			lstOutputFields.add("string:2");//Will not be displayed in JSP
			lstOutputFields.add("vWorkSpaceDesc");
			lstOutputFields.add("Country");
			lstOutputFields.add("Generics");
			lstOutputFields.addAll(Arrays.asList(selectedOutputFields));
			
			List<List<String>>result = docMgmtImpl.getProjectLevelSearchResult(inputFields, lstOutputFields);
			getProjectLevelSearchResult = new ArrayList<List<String>>();
			
			
			
			for (Iterator<List<String>> iterator = result.iterator(); iterator.hasNext();) {
				List<String> record = iterator.next();
				String workspaceId = record.remove(0);
				String columnNo = record.remove(0);
				
				for (Iterator<String> iterator2 = record.iterator(),iterator3 = fieldName.iterator(); iterator2.hasNext();) {
					String displayFieldValue = iterator2.next();
					String displayFieldName = iterator3.next();
					
					ArrayList<String> lstDisplay = new ArrayList<String>();
					
					if(displayFieldName.equals("Project Name")){
						lstDisplay.add(workspaceId);
						lstDisplay.add(columnNo);
					}
					else{
						lstDisplay.add("");
						lstDisplay.add("");
					}
					
					lstDisplay.add(displayFieldName);
					lstDisplay.add(displayFieldValue);
					getProjectLevelSearchResult.add(lstDisplay);
				}
			}
			 
			
			//For Display of table header in grid
			if(getProjectLevelSearchResult.size() > 0)
				getProjectLevelSearchResult.add(0, tableHeader);
		}
		
		
		
		return SUCCESS;
	}

	public Vector<DTOAttributeValueMatrix> lstDossierStatusValues;
	public List<List<String>> getProjectLevelSearchResult;
	public Vector<DTOLocationMst> lstCountry;
	public ArrayList<String> lstProduct;
	public ArrayList<DTOWorkSpaceNodeAttribute> lstSerialNo;
	public Vector<DTOAttributeMst> projectLevelAttributes;
	public String location;
	public String genericName;
	public String dossierStatus;
	public String[] selectedOutputFields;
	public String serialNo;
	public String searchMode;
	public String workspaceId;

}

