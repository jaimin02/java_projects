package com.docmgmt.struts.actions.search.Advanced;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOAdvancedAttrSearch;
import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOContentSearch;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SearchAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public Vector<DTOAttributeMst> attrList ;
	public ArrayList<DTOAdvancedAttrSearch> userSavedAttrList;
	public ArrayList<DTOAttributeMst> dtoAttributeMstList;
	public String savedAttrList;
	public String htmlContent;
	public String queryStringForSend;
	public ArrayList<DTOContentSearch> dtoContentSearch;
	
	
	public String getSavedAttrList() {
		return savedAttrList;
	}
	public void setSavedAttrList(String savedAttrList) {
		this.savedAttrList = savedAttrList;
	}
	public Vector<DTOAttributeMst> getAttrList() {
		return attrList;
	}
	public void setAttrList(Vector<DTOAttributeMst> attrList) {
		this.attrList = attrList;
	}
	
	@Override
	public String execute() throws Exception {
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		userSavedAttrList = docMgmtImpl.getAllSavedAttrList(userId);
		return SUCCESS;
	}
	
	public String ShowAttr(){
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		attrList = docMgmtImpl.getAttrDetailForSearch();
		userSavedAttrList = docMgmtImpl.getAllSavedAttrList(userId);
		for (int itrAttrFilter = 0; itrAttrFilter < attrList.size(); itrAttrFilter++) {
			if(attrList.get(itrAttrFilter).getAttrType().equalsIgnoreCase("File") || attrList.get(itrAttrFilter).getAttrForIndiId().equals("0001")){
				attrList.remove(itrAttrFilter);
				itrAttrFilter--;
			}
		}
		
		if (userSavedAttrList.size()>0){
			for (DTOAttributeMst dtoAttributeMst:attrList){
				for(DTOAdvancedAttrSearch dtoAdvancedAttrSearch:userSavedAttrList){
					if(dtoAdvancedAttrSearch.getAttrId() == dtoAttributeMst.getAttrId()){
						dtoAttributeMst.setSavedUserAttr(true);
						break;
					}
				}
			}
		}
		return SUCCESS;
	}
	
	public String ShowAttrDtl(){
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		ArrayList<Integer> attrIdList = new ArrayList<Integer>();
		userSavedAttrList = docMgmtImpl.getAllSavedAttrList(userId);
		if (userSavedAttrList.size()>0){
			for(DTOAdvancedAttrSearch dtoAdvancedAttrSearch:userSavedAttrList){
					attrIdList.add(dtoAdvancedAttrSearch.getAttrId());
				}
			}
		dtoAttributeMstList = docMgmtImpl.getAttributesFullDetail(attrIdList);
		return SUCCESS;
	}
	
	public String saveUserAttrList() {
		ArrayList<DTOAdvancedAttrSearch> userAttrList = new ArrayList<DTOAdvancedAttrSearch>();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		if(savedAttrList.length()!=0){
			
			String[] attrIdList=null;
			attrIdList = savedAttrList.split(",");
			if(attrIdList.length>0){
				
				htmlContent="";
				for(int itrAttrList=0; itrAttrList < attrIdList.length;itrAttrList++){
					DTOAdvancedAttrSearch dtoAdvancedAttrSearch  = new DTOAdvancedAttrSearch();
					dtoAdvancedAttrSearch.setAttrId(Integer.parseInt(attrIdList[itrAttrList]));
					dtoAdvancedAttrSearch.setUserCode(userId);
					dtoAdvancedAttrSearch.setAttrValue(" ");
					userAttrList.add(dtoAdvancedAttrSearch);
					htmlContent+="<ul>UserCode :::"+dtoAdvancedAttrSearch.getUserCode()+"AttriId:::"+dtoAdvancedAttrSearch.getAttrId()+"AttrValue::::"+dtoAdvancedAttrSearch.getAttrValue()+"</ul>";
				}
				//if dataOpMode ==2 then it will delete all data.
				docMgmtImpl.insertAttrListForAdvanceSearch(userAttrList, 2);
				docMgmtImpl.insertAttrListForAdvanceSearch(userAttrList, 1);
		  	}
		}
		else{ // user had single value selected. if user deselect that value at that time else 
			  //block execute.
			DTOAdvancedAttrSearch dtoAdvancedAttrSearch  = new DTOAdvancedAttrSearch();
			dtoAdvancedAttrSearch.setUserCode(userId);
			dtoAdvancedAttrSearch.setAttrId(0);
			dtoAdvancedAttrSearch.setAttrValue(" ");
			userAttrList.add(dtoAdvancedAttrSearch);
			docMgmtImpl.insertAttrListForAdvanceSearch(userAttrList, 2);
			htmlContent="<ul>No Attribute Selected.</ul>";
		}
		return "redirect";
	}
	
	public String QueryResult(){
		System.out.println("rahul");
		String FinalQueryString = "";
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		// here remove all the unnecessary html tag.
		queryStringForSend = queryStringForSend.replaceAll("</label>", "");
		queryStringForSend = queryStringForSend.replaceAll("<br>", "");
		queryStringForSend = queryStringForSend.replaceAll("<b>", "");
		queryStringForSend = queryStringForSend.replaceAll("</b>", "");
		queryStringForSend = queryStringForSend.replaceAll(">", "");
		queryStringForSend = queryStringForSend.replaceAll("id=\"lblAttrId", "");
		queryStringForSend = queryStringForSend.replaceAll("id=\"lblAttr[a-zA-Z0-9]+", "");
		queryStringForSend = queryStringForSend.replaceAll("\"", "");
		

		String []QueryStringArray=queryStringForSend.split("<label");
		int lenSplitedQueryString = QueryStringArray.length;
		//This is for generate the query using attribute id. here the attribute must have "`~" character.
		for(int ItrSplitId=1;ItrSplitId < lenSplitedQueryString;){
			//System.out.println("query string ::::::::::::::::::::::::::::"+QueryStringArray[ItrSplitId]);
			if(QueryStringArray[ItrSplitId].matches(".+`~`.*")){
				QueryStringArray[ItrSplitId]=QueryStringArray[ItrSplitId].split("'")[0];
				FinalQueryString +=" (iattrId = "+QueryStringArray[ItrSplitId].replaceAll("`~`", "").trim();
				FinalQueryString +=" and vattrvalue ";
				String Operator = QueryStringArray[ItrSplitId+=1].trim();
				if(Operator.equalsIgnoreCase("=")) 
					FinalQueryString +=Operator+ " "+QueryStringArray[ItrSplitId+=1].trim()+")";
				else
					FinalQueryString +=Operator+ " '%"+QueryStringArray[ItrSplitId+=1].trim().substring(1,QueryStringArray[ItrSplitId].trim().length()-1)+"%')";
				    //System.out.println(QueryStringArray[ItrSplitId+=1].trim().substring(1,QueryStringArray[ItrSplitId].trim().length()-1)+":::: Sub stribng ffffffffffffffffffff");
					//the above condition for check that the operator is "=" or "Like". If operator is "Like" then truncat the single quot and add '%String%'. 

			}
			else if(QueryStringArray[ItrSplitId].trim().equals("AND") || QueryStringArray[ItrSplitId].trim().equals("OR") || QueryStringArray[ItrSplitId].trim().equals("(") || QueryStringArray[ItrSplitId].trim().equals(")")){
				FinalQueryString +=" "+ QueryStringArray[ItrSplitId].trim()+" ";
				ItrSplitId++;
			}
			else
				ItrSplitId++;
		}
		
		//System.out.println("Final Query ::::"+FinalQueryString);		
		String AttrIorIndiId = "0001";
		dtoContentSearch = docMgmtImpl.getSearchResult(userId,AttrIorIndiId,FinalQueryString);
		return SUCCESS;
	}
}