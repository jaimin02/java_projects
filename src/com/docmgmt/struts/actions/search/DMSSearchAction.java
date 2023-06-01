package com.docmgmt.struts.actions.search;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.docmgmt.dto.DTOAttributeValueMatrix;
import com.docmgmt.dto.DTOCountryMst;
import com.docmgmt.dto.DTOHotsearchDetail;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DMSSearchAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	
	@Override
	public String execute(){
	
		int userGroupCode =  Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		userWorkspace = docMgmtImpl.getUserWorkspace(userGroupCode, userId);
		procedureTypes = docMgmtImpl.getAttributeValuesFromMatrix("Procedure type");//This ('Procedure type') value is copied from database(AttributeMst)
		allDMSRegions = docMgmtImpl.getDMSRegions();
		
		DTOAttributeValueMatrix dto = new DTOAttributeValueMatrix();
		dto.setAttrName("Procedure type");
		dto.setAttrMatrixValue("All Types");
		procedureTypes.addElement(dto);
		
	//	expireDate = new SimpleDateFormat("yyyy/MM/dd").format( new Date(System.currentTimeMillis()));
		
		return SUCCESS;
	}
	
	public String showSearchResult(){
	
		int userGroupCode =  Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		boolean criteriaFound = false;
		Map<String,String> attributes = new HashMap<String, String>(); 
		
		/* If attribute value is not blank than add it to search criteria
		 * 
		 * All Key values (attribute names) added in HashMap 
		 * are copied from database (AttributeMst)
		 */
		
		if(procedureType != null && !procedureType.trim().equals("")){
			
			if(procedureType.equalsIgnoreCase("All Types")){
				attributes.put(" = 'Procedure type'", "LIKE '%%'");//All types will be fetched
			}
			else{
				procedureType = procedureType.replace("'", "''");
				attributes.put(" = 'Procedure type'"," LIKE '%"+ procedureType + "%'");
			}
			
			criteriaFound = true;
		}
		
		if(productName != null && !productName.trim().equals("")){
			productName = productName.replace("'", "''");
			attributes.put(" = 'ProductName'", " LIKE '%"+ productName + "%'");
			criteriaFound = true;
		}
		
		if(drugName != null && !drugName.trim().equals("")){
			drugName = drugName.replace("'", "''");
			attributes.put(" = 'DrugName'", " LIKE '%"+ drugName + "%'");
			criteriaFound = true;
		}
		
		
		if(region != null && !region.trim().equals("")){
			//region = region.replace("'", "''");
			attributes.put(" = 'Name'", " LIKE '%"+ region + "%'");
			criteriaFound = true;
		}
		
		
		if(licenseNo != null && !licenseNo.trim().equals("")){
			licenseNo = licenseNo.replace("'", "''");
			attributes.put(" = 'Manufacturing License Number'", " LIKE '%"+ licenseNo + "%'");
			criteriaFound = true;
		}
		
		
		if(MA_No != null && !MA_No.trim().equals("")){
			MA_No = MA_No.replace("'", "''");
			attributes.put(" = 'MA number'", " LIKE '%"+ MA_No + "%'");
			criteriaFound = true;
		}
		
		if(procedureNo != null && !procedureNo.trim().equals("")){
			procedureNo = procedureNo.replace("'", "''");
			attributes.put(" = 'Procedure number'", " LIKE '%"+ procedureNo + "%'");
			criteriaFound = true;
		}
		
		if(renewalDate != null && !renewalDate.trim().equals("")){
			//'afterOrBefore' = 'After' or 'Before'   :->its compulsory
			String queryCondition = "=";
			if(afterOrBefore.equalsIgnoreCase("After"))
				queryCondition = ">=";
			else if(afterOrBefore.equalsIgnoreCase("After"))
				queryCondition = "<=";
			
			
			attributes.put(" = 'Date of Renewal'", " "+queryCondition+" '"+ renewalDate + "' "); 
			criteriaFound = true;
		}
		
		//If each field is left blank than vector size will be zero 
		if(criteriaFound){
			//getDMSSearchResult = new Vector();
			getDMSSearchResult = docMgmtImpl.getDMSSearchResult(attributes, userId);
		}
		else
		{
			getDMSSearchResult = new Vector<DTOHotsearchDetail>();
		}
		

		return SUCCESS;
	}

	public Vector<DTOAttributeValueMatrix> procedureTypes;
	public Vector userWorkspace;
	public String productName;
	public Vector<DTOHotsearchDetail> getDMSSearchResult;
	public String expireDate;
	public String workSpaceId;
	public String procedureType;
	public String drugName;
	public String region;
	public String licenseNo;
	public String MA_No;
	public String procedureNo;
	public String renewalDate;
	public String afterOrBefore;
	public Vector<DTOCountryMst> allDMSRegions;
	
	
public Vector getUserDetailsByUserGrp(DTOUserMst userMst){
    	
		
    	Vector userDetailsByUserGrpDtl = docMgmtImpl.getuserDetailsByUserGrp(userMst.getUserGroupCode());
    	Vector userDetailsByUserGrpDtlALL= new Vector();
    	
    	DTOUserMst userMstNew = new DTOUserMst();
    	userMstNew.setUserCode(0);
    	userMstNew.setLoginName("---ALL----");
    	userMstNew.setUserName("---ALL----");
    	userDetailsByUserGrpDtlALL.add(userMstNew);
    	userMstNew = null;
    	
    	for(int i = 0 ; i < userDetailsByUserGrpDtl.size(); i++) {
    		
    		DTOUserMst  userMstNew1 = new DTOUserMst();
    		userMstNew1 = (DTOUserMst)userDetailsByUserGrpDtl.elementAt(i);
    		
    		userDetailsByUserGrpDtlALL.add(userMstNew1);
    		userMstNew1 = null;    		
    	}
    	
    	return userDetailsByUserGrpDtlALL;
    	
    }
    
   
}

