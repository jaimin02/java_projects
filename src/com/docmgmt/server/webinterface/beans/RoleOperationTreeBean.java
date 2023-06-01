package com.docmgmt.server.webinterface.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.docmgmt.dto.DTOOperationMst;
import com.docmgmt.server.db.DocMgmtImpl;

public class RoleOperationTreeBean {

	private Vector allOperation;
	Vector roleOperationMatrix;
	
	DocMgmtImpl docMgmtImpl;

	public RoleOperationTreeBean(){ 
		 
		docMgmtImpl = new  DocMgmtImpl();
	} 

	public String getOperationTreeHtml(String userType){
		
		allOperation = docMgmtImpl.getAllOperationDetail();
		if(userType == null){
			roleOperationMatrix = new Vector();
		}else{
			roleOperationMatrix = docMgmtImpl.getOperationByUserTypeId(userType);
		}
		
		StringBuffer htmlSB=new StringBuffer();

		int srno=0;
		if(allOperation.size() > 0){
			DTOOperationMst root = new DTOOperationMst();
			ArrayList<DTOOperationMst> allParents = new ArrayList<DTOOperationMst>();
			for(int i=0;i<allOperation.size();i++){
				
				DTOOperationMst parentOpMst = (DTOOperationMst)allOperation.get(i);
				if(parentOpMst.getParentOperationCode().equals("-999")){
					getChildOp(parentOpMst);
					allParents.add(parentOpMst);
				}
				
			}
			root.setOperationName("Operations");
			
			root.setChildOperations(allParents);
			
			if(root != null){
				

				htmlSB.append("<script type=\"text/javascript\">$(function() {$(\"#sTree\").treeview({collapsed: true,animated: \"fast\",control:\"#sidetreecontrolsTree\",persist: \"location\"});})</script>");
				htmlSB.append("<div id=\"sidetreecontrolsTree\"><a href=\"?#\"><img src=\"images/jquery_tree_img/colaps.jpg\" alt=\"Collapse All\" title=\"Collapse All\" border=\"0\"/></a> | <a href=\"?#\"><img src=\"images/jquery_tree_img/exp.jpg\" alt=\"Expand All\" title=\"Expand All\" border=\"0\"/></a></div>");
                htmlSB.append("<ul STYLE='overflow: auto; height: 310;' id=\"sTree\"><li><span class='mainNode' style='color: black;'><label style=' font: bold;'>" + root.getOperationName().replaceAll(" ","&nbsp;"));
               htmlSB.append("</label><ul>");
               
            for (Iterator<DTOOperationMst> iterator = root.getChildOperations().iterator(); iterator.hasNext();){
            	DTOOperationMst rootChild=  iterator.next();
            	//System.out.println("Operation..."+rootChild.getOperationName());
            	StringBuffer sb = getChildStructure(rootChild); 
				htmlSB.append(sb);
			}
			
				htmlSB.append("</ul>");
				htmlSB.append("</span></li></ul>");
									
			}
			
		}
		//System.out.println("Final HTML String"+htmlSB.toString());
		return htmlSB.toString();
		
	}

	
	
	private StringBuffer getChildStructure(DTOOperationMst currOperation) {
		
		String checked = " checked = 'checked' ";
		StringBuffer htmlStringBuffer = new StringBuffer(); 
		
		if(currOperation.getChildOperations().size() > 0){
            
           	htmlStringBuffer.append("<li><span style='color : black; font:bold'>" + currOperation.getOperationName().replaceAll(" ","&nbsp;")+"</span>");
           	htmlStringBuffer.append("<ul>");
			 for (Iterator<DTOOperationMst> iterator = currOperation.getChildOperations().iterator(); iterator.hasNext();){
	            	DTOOperationMst childOp=  iterator.next();
	            	StringBuffer sb = getChildStructure(childOp); 
	            	htmlStringBuffer.append(sb);
				}
			
			htmlStringBuffer.append("</ul></li>");
		}
		else{
			boolean matchFound = false;
			for (Object roleOpObj : roleOperationMatrix) {
				DTOOperationMst roleOp =(DTOOperationMst)roleOpObj;
				if(roleOp.getOperationCode().equals(currOperation.getOperationCode())
						&& roleOp.getActiveFlag()=='Y'){
					matchFound = true;
					break;
				}
			}
			
			htmlStringBuffer.append("<li><a ><input type='checkbox' style='border: none;' name = 'operationIds' value='"+currOperation.getOperationCode()+"' id='op_" +currOperation.getOperationCode()+"' "); 
			
			//If code matches then it will be checked by default.
			if(matchFound){
				htmlStringBuffer.append(checked);
			}
			htmlStringBuffer.append("> <span style='color : black'>" + currOperation.getOperationName().replaceAll(" ","&nbsp;")+"</span></a>");
			if(matchFound){
				htmlStringBuffer.append("<input type='hidden' name = 'prevOperationIds' value='"+currOperation.getOperationCode()+"' >");
			}
			htmlStringBuffer.append("</li>");
		}
		
		return htmlStringBuffer;
		
	}
	private void getChildOp(DTOOperationMst parentOpMst){
		ArrayList<DTOOperationMst> childOperations= new  ArrayList<DTOOperationMst>();
		for(int j=0;j<allOperation.size();j++){
			DTOOperationMst childOpMst = (DTOOperationMst)allOperation.get(j);
			if(parentOpMst.getOperationCode().equals(childOpMst.getParentOperationCode())){
				getChildOp(childOpMst);
				childOperations.add(childOpMst);
			}
		}
		parentOpMst.setChildOperations(childOperations);
	}
	
	public Vector getAllOperation() {
		return allOperation;
	}

	public void setAllOperation(Vector allOperation) {
		this.allOperation = allOperation;
	}


}
