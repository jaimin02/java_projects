package com.docmgmt.struts.actions.WelcomePage.DossierStatus;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOAttributeValueMatrix;
import com.docmgmt.dto.DTOLocationMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DossierStatusTabAction extends ActionSupport 
{
	
	public Vector<DTOLocationMst> getAllRegion;
	public Vector<DTOAttributeValueMatrix> getAllProduct;
	public Vector<DTOAttributeValueMatrix>   getAllDossierStatus;
	public ArrayList<HashMap<String,String>> allStachBarGraphDetail=new ArrayList<HashMap<String,String>>();	
	public String allGraphDetail="";
	public ArrayList<HashMap<String,String>> getAllGraphDetail;
	public String locationCode;
	public String productattrValue;
	public String dossierStatusattrValue;	
	public String graphFlag;
	public String dataNull="no"; 
	public String topMostValue;
	public String allSelected="";
	
	public String wsIdString;
	public ArrayList<DTOWorkSpaceMst> workSpaceDetail;
	public String chartBy;
	public String gridThirdColumn;
	public String othersValue;	
	public String xAxisStr="";		
	public int liTotalStackBarGraph;	
	private int topValue;
	public String NoAttr="";
	DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	private ArrayList<DTOWorkSpaceNodeAttrDetail> userWorkSpaceDetailByAttrName;
	private Vector<DTOWorkSpaceMst> userWorkSpaceList;
	
	//For Stack Bar Chart Property
	private int regionPerChart= 10;
	private int heightOfSingleRegion=30;
	public String heightStackBar;
	public int heightOfLastStackBar=60;
	public int heightOfLegend=80;
	
	public String execute()
	{		
		//set topvalue 		
		if(topMostValue.equalsIgnoreCase("")){
			topValue=0;		
		}else {
			topValue=Integer.parseInt(topMostValue);			
		}					
		if(graphFlag.equalsIgnoreCase("1")){//1 for dissier Status chart means dossier status will all			
			if(!locationCode.equalsIgnoreCase("-1") && !productattrValue.equalsIgnoreCase("-1")){				
				createStringForDossierStatus("bothall");
			}			
			else if(locationCode.equalsIgnoreCase("-1")){				
				createStringForDossierStatus("locationcodeall");				
			}else if(productattrValue.equalsIgnoreCase("-1")){
				createStringForDossierStatus("productall");
			}			
		}else if(graphFlag.equalsIgnoreCase("2")){//2 for region chart means region will all
			createStringForRegion();
		}else if(graphFlag.equalsIgnoreCase("3")){//3 for product(molacule) chart means product will all
			createStringForProduct();
			//last for default graph if graphFlag is not 1,2,3
		}
		return SUCCESS;
	}
	public String defaultCallForGraph()
	{
		long miliStart = System.currentTimeMillis();
	
		
		getAllDossierStatus=docMgmtImpl.getAttributeValuesFromMatrix("Dossier Status");
		getAllRegion=docMgmtImpl.getLocationDtl();
		getAllProduct=docMgmtImpl.getAttributeValuesFromMatrix("Molecule");
		
		if(getAllDossierStatus.size()!=0  && getAllProduct.size()!=0){
				if(allSelected!=null && allSelected.equalsIgnoreCase("yes"))
				{
					createStackBarChart();
					return "AllSelect";
				}
				NoAttr="No";
		}else{
			NoAttr="Yes";								
		}
		
		/*
		for(int i=0;i<15;i++)
		{
			allGraphDetail=allGraphDetail+"['"+"PipeProduct"+"',"+i+"],";
		}
		allGraphDetail=allGraphDetail.substring(0, allGraphDetail.length()-1);
		*/			
				
		 
			return SUCCESS;
	}
	public String workSpaceDetail()
	{
		ArrayList<DTOWorkSpaceNodeAttrDetail> workSpaceDetailByAttrName;		
		String[] str=wsIdString.split(":");				
		ArrayList<String> wsIdList=new ArrayList<String>();
		
		for(int i=0;i<str.length;i++){
			wsIdList.add(str[i]);
		}		
		workSpaceDetail=docMgmtImpl.getWorkSpaceDetail(wsIdList);
		
		if(othersValue.equalsIgnoreCase("others")){
			if(chartBy.equalsIgnoreCase("dossier status")){
				gridThirdColumn="Dossier Status";
				workSpaceDetailByAttrName=docMgmtImpl.getAttrValByNameForWorkSpaceIds(wsIdList, "dossier status");
				workSpaceDetail=mergeAttrValue(workSpaceDetail,workSpaceDetailByAttrName);
			}else if(chartBy.equalsIgnoreCase("region")){
				gridThirdColumn="Region";	
				workSpaceDetail=mergeAttrValueForLocationName(workSpaceDetail);				
			}else{ 
				gridThirdColumn="Product";
				workSpaceDetailByAttrName=docMgmtImpl.getAttrValByNameForWorkSpaceIds(wsIdList, "Molecule");
				workSpaceDetail=mergeAttrValue(workSpaceDetail,workSpaceDetailByAttrName);
			}	
		}else {
			gridThirdColumn="";
		}			                                                                                                                                                   
		return SUCCESS;
	}
	/*Private methods*/
	private void createStringForDossierStatus(String graphBy)
	{		
		ArrayList<HashMap<String,String>> graphDetailBySelectedValue;
		ArrayList<String> cmnworkSpaceIdList;
		ArrayList<DTOWorkSpaceNodeAttrDetail> workSpaceDetailByAttrName;
		
		if(graphBy.equalsIgnoreCase("locationcodeall"))	{
			cmnworkSpaceIdList=workSpaceAttributeValueFilter();
		}else if(graphBy.equalsIgnoreCase("productall"))	{
			cmnworkSpaceIdList=workSpaceRegionFilter();	
		}else{ 
			ArrayList<String> workSpaceIdList1 = workSpaceAttributeValueFilter();
			ArrayList<String> workSpaceIdList2 = workSpaceRegionFilter();					
			cmnworkSpaceIdList=commonWorkSpaceId(workSpaceIdList1,workSpaceIdList2);
		}	
			if(cmnworkSpaceIdList.size()>0){
					workSpaceDetailByAttrName=docMgmtImpl.getAttrValByNameForWorkSpaceIds(cmnworkSpaceIdList, "dossier status");
					graphDetailBySelectedValue=graphDtlForDossierStatus(workSpaceDetailByAttrName);
					//System.out.println("Common="+graphDetailBySelectedValue.size());
					if(graphDetailBySelectedValue.size()>0){						
						allGraphDetail=makeStringFromgraphDetailList(graphDetailBySelectedValue,"dossier status");												
					}else{
						dataNull="yes";
					}
			}else{
				dataNull="yes";
			}
	}
	private void createStringForRegion()
	{
		ArrayList<HashMap<String,String>> graphDetailBySelectedValue;
		ArrayList<DTOAttributeMst > dtoAttMst=new ArrayList<DTOAttributeMst>();
		ArrayList<DTOWorkSpaceMst> workSpaceMstList=new ArrayList<DTOWorkSpaceMst>();
		ArrayList<String> allWorkSpaceIdList=new ArrayList<String>();
		DTOAttributeMst dossierStatus=new DTOAttributeMst();
		DTOAttributeMst molecule=new DTOAttributeMst();
		
		dossierStatus.setAttrName("Dossier Status");						
		dossierStatus.setAttrValue(docMgmtImpl.getAttrValueByAttrValueId(Integer.parseInt(dossierStatusattrValue)));
		
		molecule.setAttrName("Molecule");	
		int attrvalId=Integer.parseInt(productattrValue);		
		molecule.setAttrValue(docMgmtImpl.getAttrValueByAttrValueId(attrvalId));
		
		dtoAttMst.add(dossierStatus);
		dtoAttMst.add(molecule);
		
		allWorkSpaceIdList=docMgmtImpl.getWorkspaceByAttributeValue(dtoAttMst);
					
		//TODO Use arraylist function this one for temp.
		for(int itrWsId=0;itrWsId<allWorkSpaceIdList.size();itrWsId++){
			workSpaceMstList.add(docMgmtImpl.getWorkSpaceDetail(allWorkSpaceIdList.get(itrWsId).toString()));	
		}
		if(workSpaceMstList.size()>0){			
			graphDetailBySelectedValue=graphDtlForRegion(workSpaceMstList);
			//System.out.println("Common="+graphDetailBySelectedValue.size());			
			allGraphDetail=makeStringFromgraphDetailList(graphDetailBySelectedValue,"region");			
		}else{
			dataNull="yes";
		}
	}
	private void createStringForProduct()
	{
		ArrayList<DTOWorkSpaceNodeAttrDetail> workSpaceDetailByAttrName;
		ArrayList<DTOAttributeMst > dtoAttMst=new ArrayList<DTOAttributeMst>();
		ArrayList<HashMap<String,String>> graphDetailBySelectedValue;
		DTOAttributeMst dossierStatus=new DTOAttributeMst();		
		ArrayList<String> workSpaceIdList1;
		ArrayList<String> workSpaceIdList2;
		ArrayList<String> cmoWorkSpaceList;		
		dossierStatus.setAttrName("Dossier Status");						
		dossierStatus.setAttrValue(docMgmtImpl.getAttrValueByAttrValueId(Integer.parseInt(dossierStatusattrValue)));
		dtoAttMst.add(dossierStatus);
		workSpaceIdList1=docMgmtImpl.getWorkspaceByAttributeValue(dtoAttMst);
		workSpaceIdList2=workSpaceRegionFilter();
		cmoWorkSpaceList=commonWorkSpaceId(workSpaceIdList1,workSpaceIdList2);
		if(cmoWorkSpaceList.size()>0)
		{
			workSpaceDetailByAttrName=docMgmtImpl.getAttrValByNameForWorkSpaceIds(cmoWorkSpaceList, "Molecule");			
			graphDetailBySelectedValue=graphDtlForProduct(workSpaceDetailByAttrName);
			//System.out.println("Common="+graphDetailBySelectedValue.size());
			if(graphDetailBySelectedValue.size()>0){
				allGraphDetail=makeStringFromgraphDetailList(graphDetailBySelectedValue,"product");					
			}else{
				dataNull="yes";
			}
		}else{
			dataNull="yes";
		}
	}
	
	//This function for stack-bar chart
	private void createStackBarChart()
	{
		double db=System.currentTimeMillis();
		DTOUserMst userMst=new DTOUserMst();
		ArrayList<String> workSpaceIdList=new ArrayList<String>();		
		ArrayList<HashMap<String, String>> totalPrjInDs=new ArrayList<HashMap<String,String>>();
		ArrayList<String> allStackBarChartString=new ArrayList<String>();
		ArrayList<String> allXAxisStackBarChartString=new ArrayList<String>();
		int totalRegionNotZero=0;
		int regionInlastStackBarChart=0;
		int totalRegionIndex=0;
		
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());		
		String userTypeName =ActionContext.getContext().getSession().get("usertypename").toString();
		userMst.setUserCode(userCode);
		userMst.setUserGroupCode(userGroupCode);
		userMst.setUserType(userTypeName);				
		userWorkSpaceList=docMgmtImpl.getUserWorkspace(userGroupCode, userCode);		
		for(int i = 0; i < userWorkSpaceList.size(); i++){		
				workSpaceIdList.add(userWorkSpaceList.get(i).getWorkSpaceId());
		}		
		userWorkSpaceDetailByAttrName=docMgmtImpl.getAttrValByNameForWorkSpaceIds(workSpaceIdList, "Dossier Status");
		
		
		//count no. of stackbar charts to be generated and no. of region in last stack bar chart
		float lfTotalStackBarGraph=getAllRegion.size()/(float)regionPerChart;
		liTotalStackBarGraph= (int) Math.ceil(lfTotalStackBarGraph);		
		if(liTotalStackBarGraph==1){
			regionInlastStackBarChart=getAllRegion.size();			
			heightOfLastStackBar=heightOfLastStackBar+heightOfLegend+(regionInlastStackBarChart*heightOfSingleRegion);			
			//System.out.println("Height of last Stack Bar="+heightOfLastStackBar);
		}
		else{
			if(((liTotalStackBarGraph* regionPerChart )-getAllRegion.size())==0){								
				heightOfLastStackBar=heightOfLastStackBar+heightOfLegend+(regionPerChart*heightOfSingleRegion);
				heightOfLastStackBar=heightOfLastStackBar-20;
				regionInlastStackBarChart=regionPerChart;
			}
			else{
			regionInlastStackBarChart=getAllRegion.size()-((liTotalStackBarGraph-1)*regionPerChart);
			heightOfLastStackBar=heightOfLastStackBar+heightOfLegend+(regionInlastStackBarChart*heightOfSingleRegion);
			}
		}
		//generate height of one stack bar chart
		heightStackBar=Integer.toString(heightOfLegend+(heightOfSingleRegion*regionPerChart));
				
		//generate all stack bar chart
		for (int indexStackBar= 0; indexStackBar < liTotalStackBarGraph; indexStackBar++){			
			StringBuffer mainStrForStackBar=new StringBuffer();						
				for(int indexDs = 0; indexDs < getAllDossierStatus.size(); indexDs++){
					mainStrForStackBar.append("{ name : '"+getAllDossierStatus.get(indexDs).getAttrMatrixValue()+"',  data : [ ");							
					int iteratorCount=0;
					if(liTotalStackBarGraph==1 || indexStackBar==(liTotalStackBarGraph-1)){
						if(liTotalStackBarGraph!=1 && regionPerChart==regionInlastStackBarChart){							
						iteratorCount=(totalRegionIndex+regionPerChart);
						}else{
							iteratorCount=(totalRegionIndex+regionInlastStackBarChart);
						}													
					}else{
						iteratorCount=((indexStackBar+1)*regionPerChart);						
					}						
					for (int indexRegion = totalRegionIndex; indexRegion < iteratorCount; indexRegion++) {
						DTOLocationMst currLocation=getAllRegion.get(indexRegion);
											
						ArrayList<HashMap<String,String>> wsIdWithWsStrAndCount=countWsIdFromWorkSpaceDetail(currLocation.getLocationCode(),getAllDossierStatus.get(indexDs).getAttrMatrixValue());
						//System.out.println("After count func::"+(System.currentTimeMillis()-miliStart));
						//System.out.println(getAllDossierStatus.get(indexDs).getAttrMatrixValue()+" "+getAllRegion.get(indexRegion).getLocationCode()+" "+countWsId);
						if(wsIdWithWsStrAndCount.size()>0){
							HashMap<String ,String> single=wsIdWithWsStrAndCount.get(0);
							totalRegionNotZero=totalRegionNotZero+1;
							//System.out.println(getAllDossierStatus.get(indexDs).getAttrMatrixValue()+"  "+getAllRegion.get(indexRegion).getLocationName()+"  "+single.get("wsidstr")+"  "+single.get("count"));
							mainStrForStackBar.append(single.get("count")+",");								
						}else{					
							mainStrForStackBar.append("0"+",");											
						}							
					}						
					mainStrForStackBar.deleteCharAt(mainStrForStackBar.length()-1);
					//mainStrForStackBar=mainStrForStackBar.substring(0,mainStrForStackBar.length()-1);
					mainStrForStackBar.append("]},");					
					
				}
				mainStrForStackBar.deleteCharAt(mainStrForStackBar.length()-1);
				//mainStrForStackBar=mainStrForStackBar.substring(0,mainStrForStackBar.length()-1);
				System.out.println("Main String="+mainStrForStackBar);
				totalRegionIndex=(indexStackBar+1)*regionPerChart;								
				allStackBarChartString.add(mainStrForStackBar.toString());
		}				
		
		//count total project in region for all stack bar
		for(int indexDs = 0; indexDs < getAllDossierStatus.size(); indexDs++){		
			HashMap<String ,String> totalProjectByDs=new HashMap<String, String>();					
			for (int indexRegion = 0; indexRegion < getAllRegion.size(); indexRegion++) {
				ArrayList<HashMap<String,String>> wsIdWithWsStrAndCount=countWsIdFromWorkSpaceDetail(getAllRegion.get(indexRegion).getLocationCode(),getAllDossierStatus.get(indexDs).getAttrMatrixValue());
				if(wsIdWithWsStrAndCount.size()>0){
					HashMap<String ,String> single=wsIdWithWsStrAndCount.get(0);					
					totalProjectByDs.put("region"+Integer.toString(indexRegion),single.get("count"));									
				}else{
					totalProjectByDs.put("region"+Integer.toString(indexRegion),"0");				
				}				
			}	
			totalPrjInDs.add(totalProjectByDs);					
		}		
		
		ArrayList<String> totalProjectByRegion=new ArrayList<String>();
		for (int indexRegion = 0; indexRegion < getAllRegion.size(); indexRegion++) {
			int count=0;
			for (int indexPrjInDs = 0; indexPrjInDs < totalPrjInDs.size(); indexPrjInDs++) {
				  HashMap<String ,String> single=totalPrjInDs.get(indexPrjInDs);
				  count=count+Integer.parseInt(single.get("region"+indexRegion));
			}	
			totalProjectByRegion.add(Integer.toString(count));
			//System.out.println("Region"+indexRegion+ " Project="+count);
		}		
				
		//create xAxis string for all stack bar chart
		totalRegionIndex=0;
		for (int indexStackBar= 0; indexStackBar < liTotalStackBarGraph; indexStackBar++){			
				int iteratorCount=0;
				if(liTotalStackBarGraph==1 || indexStackBar==(liTotalStackBarGraph-1)){
					if(liTotalStackBarGraph!=1 && regionPerChart==regionInlastStackBarChart){							
						iteratorCount=(totalRegionIndex+regionPerChart);
						}else{
							iteratorCount=(totalRegionIndex+regionInlastStackBarChart);
						}										
				}else{
					iteratorCount=((indexStackBar+1)*regionPerChart);
				}
				xAxisStr="['";
				for (int indexReg = totalRegionIndex; indexReg < iteratorCount; indexReg++) {
					xAxisStr=xAxisStr+getAllRegion.get(indexReg).getLocationName()+"("+ totalProjectByRegion.get(indexReg) +")" +"','";
				}
				xAxisStr=xAxisStr.substring(0,xAxisStr.length()-2);
				xAxisStr=xAxisStr+"]";
				allXAxisStackBarChartString.add(xAxisStr);				
				totalRegionIndex=(indexStackBar+1)*regionPerChart;				
				xAxisStr="";				
		}
		
		//create arraylist which contain all data for all stack bar chart
		for (int i = 0; i < allStackBarChartString.size() ; i++) {
			HashMap<String,String> chartData=new HashMap<String, String>();
			chartData.put("xaxis",allXAxisStackBarChartString.get(i));
			chartData.put("region",allStackBarChartString.get(i));
			if(i==(allStackBarChartString.size()-1)){
				chartData.put("height",Integer.toString(heightOfLastStackBar));
				System.out.println("heightOfLastStackBar==="+Integer.toString(heightOfLastStackBar));
			}else{
				chartData.put("height",heightStackBar);	
				System.out.println("heightStackBar==="+heightStackBar);
			}							
			allStachBarGraphDetail.add(chartData);
		}

	}
	
	private ArrayList<HashMap<String,String>> countWsIdFromWorkSpaceDetail(String locationCode,String dossierStatus)
	{							
		ArrayList<HashMap<String,String>> wsIdWithWsStrAndCount=new ArrayList<HashMap<String,String>>();
		ArrayList<String> wsIdList=new ArrayList<String>();
		HashMap<String, String> single=new HashMap<String, String>();
		String wsStr="";
		int count=0;
		for (int indexUserWs = 0; indexUserWs < userWorkSpaceList.size(); indexUserWs++) {
			if(userWorkSpaceList.get(indexUserWs).getLocationCode().equalsIgnoreCase(locationCode)){
				wsIdList.add(userWorkSpaceList.get(indexUserWs).getWorkSpaceId());
			}				
		}
		for (int indexWsIdList = 0; indexWsIdList < wsIdList.size(); indexWsIdList++){			
			for (int indexDs = 0; indexDs < userWorkSpaceDetailByAttrName.size(); indexDs++) {				
				if(wsIdList.get(indexWsIdList).equalsIgnoreCase(userWorkSpaceDetailByAttrName.get(indexDs).getWorkspaceId())){
										if(userWorkSpaceDetailByAttrName.get(indexDs).getAttrValue().equalsIgnoreCase(dossierStatus)){
											wsStr=wsStr+userWorkSpaceDetailByAttrName.get(indexDs).getWorkspaceId()+":";
											count++;											
										}
				}									
			}							
		}
		if(count!=0 && wsStr.length()>0){
			single.put("wsidstr", wsStr.substring(0,wsStr.length()-1));
			single.put("count", Integer.toString(count));			
			wsIdWithWsStrAndCount.add(single);
		}
				return wsIdWithWsStrAndCount;				
	}
 		
	private ArrayList<String> workSpaceRegionFilter()
	{
		DTOWorkSpaceMst workSpaceMst=new DTOWorkSpaceMst();
		ArrayList<String> allWorkSpaceIdList=new ArrayList<String>();
		DTOUserMst userMst=new DTOUserMst();

		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userTypeName =ActionContext.getContext().getSession().get("usertypename").toString();
		userMst.setUserCode(userCode);
		userMst.setUserGroupCode(userGroupCode);
		userMst.setUserType(userTypeName);		
		workSpaceMst.setLocationCode(locationCode);
		Vector<DTOWorkSpaceMst> workspacedtl=docMgmtImpl.searchWorkspace(workSpaceMst,userMst);		
		for(int i=0;i<workspacedtl.size();i++)
		{			
			allWorkSpaceIdList.add(workspacedtl.get(i).getWorkSpaceId());
		}
		//System.out.println("allWorkSpaceIdList="+allWorkSpaceIdList.size());
		return allWorkSpaceIdList;
	}
	
	private ArrayList<String> workSpaceAttributeValueFilter()
	{
		ArrayList<DTOAttributeMst > attMstList=new ArrayList<DTOAttributeMst>();
		
		ArrayList<String> allWorkSpaceIdList=new ArrayList<String>();		
		DTOAttributeMst molecule=new DTOAttributeMst(); 		
		molecule.setAttrName("Molecule");	
		int attrvalId=Integer.parseInt(productattrValue);		
		molecule.setAttrValue(docMgmtImpl.getAttrValueByAttrValueId(attrvalId));
		attMstList.add(molecule);			
		/*
		dossierStatus.setAttrName("Dossier Status");
		dossierStatus.setAttrValue(dossierStatusattrValue);
		dtoAttMst.add(dossierStatus);
		*/
		allWorkSpaceIdList=docMgmtImpl.getWorkspaceByAttributeValue(attMstList);				
		return allWorkSpaceIdList;
	}
	private ArrayList<String> commonWorkSpaceId(ArrayList<String> allWorkSpaceList1,ArrayList<String> allWorkSpaceList2)
	{
		ArrayList<String> commonWorkSpaceIdList=new ArrayList<String>();		
		for(int i=0;i<allWorkSpaceList1.size();i++)
		{
			for(int j=0;j<allWorkSpaceList2.size();j++)
			{
				if(allWorkSpaceList1.get(i).toString().equalsIgnoreCase(allWorkSpaceList2.get(j).toString()))
							commonWorkSpaceIdList.add(allWorkSpaceList1.get(i).toString());				
			}
		}
		return commonWorkSpaceIdList;
	}
	private ArrayList<HashMap<String,String>> graphDtlForDossierStatus(ArrayList<DTOWorkSpaceNodeAttrDetail> workSpaceDetailByAttrName)
	{
		Vector<DTOAttributeValueMatrix> getAllDossierStatus;
		ArrayList<HashMap<String,String>> graphDetailData=new  ArrayList<HashMap<String,String>>();		
		getAllDossierStatus=docMgmtImpl.getAttributeValuesFromMatrix("Dossier Status");		
		float totalDossierStatus=workSpaceDetailByAttrName.size();
		int count=0;
		float totalPercenTage=0.0f;		
		String wsstr=new String(); 
		
		for(int i=0;i<getAllDossierStatus.size();i++)
		{
			count=0;
			String str=getAllDossierStatus.get(i).getAttrMatrixValue();
			wsstr="";
			for(int j=0;j<workSpaceDetailByAttrName.size();j++)
			{								
				
					if(str.equalsIgnoreCase(workSpaceDetailByAttrName.get(j).getAttrValue())){										
						count++;
						wsstr=wsstr+workSpaceDetailByAttrName.get(j).getWorkspaceId()+":";
						//System.out.println(workSpaceDetailByAttrName.get(j).getWorkspaceId());
						//System.out.println(count);
					}
			}					
			if(wsstr.length()>0){
				wsstr=wsstr.substring(0, wsstr.length()-1);						
				//System.out.println(wsstr);
			}			
			totalPercenTage=(count/totalDossierStatus)*100;					
			if(count!=0)
				graphDetailData.add(addValueInHashMap("dossier status",getAllDossierStatus.get(i).getAttrMatrixValue(),"percentage",String.format("%.02f",totalPercenTage),"count",Integer.toString(count),"wsidstr",wsstr));
			
		}			
		//Dossier status are sorted by percentage
		graphDetailData=sortByPercantage(graphDetailData);
	
		return graphDetailData;
	}
	private ArrayList<HashMap<String,String>> graphDtlForRegion(ArrayList<DTOWorkSpaceMst> workSpaceDetail)
	{
		ArrayList<HashMap<String,String>> graphDetailData=new  ArrayList<HashMap<String,String>>();
		
		
		float totalLocationCode=workSpaceDetail.size();
		int count=0;
		float totalPercenTage=0.0f;
		ArrayList<String> allRegionList=new ArrayList<String>();
		Vector<DTOLocationMst> allRegion=docMgmtImpl.getLocationDtl();
		ArrayList<String> wsIdList=new ArrayList<String>();
		String wsstr=new String();
		
		for(int itrrgn=0;itrrgn<allRegion.size();itrrgn++)
		{
			DTOLocationMst dtoLocationMst=allRegion.get(itrrgn);
			allRegionList.add(dtoLocationMst.getLocationName());
		}
		
		for(int itrRgn=0;itrRgn<allRegionList.size();itrRgn++)
		{
			count=0;
			String str=allRegionList.get(itrRgn).toString();
			wsstr="";
			for(int itrwrkId=0;itrwrkId<workSpaceDetail.size();itrwrkId++)
			{					
				
					if(str.equalsIgnoreCase(workSpaceDetail.get(itrwrkId).getLocationName())){
					//	System.out.println(str+workSpaceDetail.get(j).getAttrValue());					
						count++;						
						wsstr=wsstr+workSpaceDetail.get(itrwrkId).getWorkSpaceId()+":";						
					}
			}	
			if(wsstr.length()>0){
				wsstr=wsstr.substring(0, wsstr.length()-1);						
				//System.out.println(wsstr);
			}
			totalPercenTage=(count/totalLocationCode)*100;			
			
			if(count!=0)
				graphDetailData.add(addValueInHashMap("region",allRegionList.get(itrRgn).toString(),"percentage",String.format("%.02f",totalPercenTage),"count",Integer.toString(count),"wsidstr",wsstr));			
		}		
		
		
		//Region is sort by percentage
		graphDetailData=sortByPercantage(graphDetailData);
		
		return graphDetailData; 
	}
	private ArrayList<HashMap<String,String>> graphDtlForProduct(ArrayList<DTOWorkSpaceNodeAttrDetail> workSpaceDetail)
	{
		ArrayList<HashMap<String,String>> graphDetailData=new  ArrayList<HashMap<String,String>>();
		Vector<DTOAttributeValueMatrix> getAllProduct;		
		float totalLocationCode=workSpaceDetail.size();
		int count=0;
		float totalPercenTage=0.0f;
		ArrayList<String> allProductList=new ArrayList<String>();
		
		ArrayList<String> wsIdList=new ArrayList<String>();
		String wsstr=new String();
		
		getAllProduct=docMgmtImpl.getAttributeValuesFromMatrix("Molecule");
		
		for(int itrprdct=0;itrprdct<getAllProduct.size();itrprdct++)
		{
			DTOAttributeValueMatrix dtoAttributeValueMatrix=getAllProduct.get(itrprdct);
			allProductList.add(dtoAttributeValueMatrix.getAttrMatrixValue().toString());
		}
		for(int itrPrdt=0;itrPrdt<allProductList.size();itrPrdt++)
		{
			count=0;
			String str=allProductList.get(itrPrdt).toString();
			wsstr="";
			for(int itrwrkId=0;itrwrkId<workSpaceDetail.size();itrwrkId++)
			{									
					if(str.equalsIgnoreCase(workSpaceDetail.get(itrwrkId).getAttrValue())){
						count++;						
						wsstr=wsstr+workSpaceDetail.get(itrwrkId).getWorkspaceId()+":";
					}					
			}							
			if(wsstr.length()>0){
				wsstr=wsstr.substring(0, wsstr.length()-1);						
				//System.out.println(wsstr);
			}
			totalPercenTage=(count/totalLocationCode)*100;
			if(count!=0)
				graphDetailData.add(addValueInHashMap("product",allProductList.get(itrPrdt),"percentage",String.format("%.02f",totalPercenTage),"count",Integer.toString(count),"wsidstr",wsstr));						
		}
		
		//Product is sorted by percentage
		graphDetailData=sortByPercantage(graphDetailData);
		
		return graphDetailData; 
	}
	private HashMap<String, String> addValueInHashMap(String firstKey,String firstValue,String secondetKey,String secondeValue,String thirdKey,String thirdValue,String fourthKey,String fourthValue)
	{
		HashMap<String, String> singleWorkSpaceDtl=new HashMap<String, String>();
		singleWorkSpaceDtl.put(firstKey,firstValue);
		singleWorkSpaceDtl.put(secondetKey,secondeValue);		
		singleWorkSpaceDtl.put(thirdKey,thirdValue);
		singleWorkSpaceDtl.put(fourthKey,fourthValue);
		return singleWorkSpaceDtl;
	}	
	private ArrayList<HashMap<String, String>> sortByPercantage(ArrayList<HashMap<String, String>> graphDetailData)
	{
		Collections.sort(graphDetailData,new Comparator<HashMap<String, String>>(){
			public int compare(HashMap<String, String> arg1,HashMap<String, String> arg2) {		
				Float f1=new Float(arg1.get("percentage"));
				Float f2=new Float(arg2.get("percentage"));
				if(arg1.get("percentage")==arg2.get("percentage"))
					return 0;
				else if(f1>f2)
					return -1;
				else 
					return 1;
			}
			
		});
		return graphDetailData;
	}
	private String makeStringFromgraphDetailList(ArrayList<HashMap<String,String>> graphDetailBySelectedValue,String keyByGraph)
	{
		String graphDetailString="";
		String wdIdsString="";
		float totalPercenTage=0.0f;
		int totalCount=0;
		if(topValue>=graphDetailBySelectedValue.size() || topValue==0){	
			for(int i=0;i<graphDetailBySelectedValue.size();i++)
			{
				HashMap<String,String> workSpaceOne=graphDetailBySelectedValue.get(i);
				graphDetailString=graphDetailString+"['"+workSpaceOne.get(keyByGraph) +"',"+workSpaceOne.get("percentage")+","+workSpaceOne.get("count")+",'"+workSpaceOne.get("wsidstr")+"'],";
			}
			graphDetailString=graphDetailString.substring(0, graphDetailString.length()-1);
			System.out.println("graphDetailString::"+graphDetailString);
		}else{
			for(int i=0;i<topValue;i++)
			{
				HashMap<String,String> workSpaceOne=graphDetailBySelectedValue.get(i);
				graphDetailString=graphDetailString+"['"+workSpaceOne.get(keyByGraph) +"',"+workSpaceOne.get("percentage")+","+workSpaceOne.get("count")+",'"+workSpaceOne.get("wsidstr")+"'],";
			}
			for(int i=topValue;i<graphDetailBySelectedValue.size();i++)
			{
				HashMap<String,String> workSpaceOne=graphDetailBySelectedValue.get(i);
				//graphDetailString=graphDetailString+"['"+workSpaceOne.get(keyByGraph) +"',"+workSpaceOne.get("percentage")+","+workSpaceOne.get("count")+",'"+workSpaceOne.get("wsidstr")+"'],";
				wdIdsString=wdIdsString+workSpaceOne.get("wsidstr")+":";
				totalPercenTage=totalPercenTage+ new Float(workSpaceOne.get("percentage"));
				totalCount=totalCount+new Integer(workSpaceOne.get("count"));
			}
			wdIdsString=wdIdsString.substring(0,wdIdsString.length()-1);
			graphDetailString=graphDetailString+"['Others',"+totalPercenTage+","+totalCount+",'"+wdIdsString+"'],";						
			graphDetailString=graphDetailString.substring(0, graphDetailString.length()-1);
			
		}			
		return graphDetailString;
	}
	private ArrayList<DTOWorkSpaceMst> mergeAttrValue(ArrayList<DTOWorkSpaceMst> workSpaceDetail, ArrayList<DTOWorkSpaceNodeAttrDetail> workSpaceDetailByAttrName)
	{
		for (int dtoIndex = 0; dtoIndex < workSpaceDetail.size(); dtoIndex++) {						
			workSpaceDetail.get(dtoIndex).setAttrValue(workSpaceDetailByAttrName.get(dtoIndex).getAttrValue());
		}	
		return workSpaceDetail;
	}
	private ArrayList<DTOWorkSpaceMst> mergeAttrValueForLocationName(ArrayList<DTOWorkSpaceMst> workSpaceDetail)
	{
		for (int dtoIndex = 0; dtoIndex < workSpaceDetail.size(); dtoIndex++) {						
			workSpaceDetail.get(dtoIndex).setAttrValue(workSpaceDetail.get(dtoIndex).getLocationName());
		}	
		return workSpaceDetail;
	}	
}	