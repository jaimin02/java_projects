package com.docmgmt.struts.actions.roleoperation;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTORoleOperationMatrix;
import com.docmgmt.dto.DTOUserTypeMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.RoleOperationTreeBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AssignRoleOperationsAction extends ActionSupport {
	
	public Vector listTemp;
	public Vector allUserType;
	public String userTypeCode;
	public InputStream inputStream;
	public ArrayList<String> operationIds;
	public ArrayList<String> prevOperationIds;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	RoleOperationTreeBean role = new RoleOperationTreeBean();

	String htmlRole;
	private Vector allOperation;
	Vector roleOperationMatrix;

	@Override
	public String execute()
	{
		initialTreeDisplay();
		return SUCCESS;
	}
	
	public String initialTreeDisplay(){
		allUserType = docMgmtImpl.getAllUserType();
		DTOUserTypeMst utypedto=new DTOUserTypeMst();
		utypedto.setUserTypeCode("0001");
		utypedto.setUserTypeName("SU");
		utypedto.setModifyBy(1);
		allUserType.addElement(utypedto);
		
		htmlRole = role.getOperationTreeHtml(userTypeCode);
		return SUCCESS;
	}
	
	public String AfterGetUserTypeTreeDisplay(){
			htmlRole = role.getOperationTreeHtml(userTypeCode);

			inputStream = new ByteArrayInputStream(htmlRole.getBytes()); 

		return SUCCESS;
	}
	
	public String SaveRoleOperation(){
		//ArrayList<String> finalOpIdList = new ArrayList<String>();
		if(userTypeCode.equals("-1")){
			addActionError("No UserType Selected!!");
			return SUCCESS;
		}
		allOperation = docMgmtImpl.getAllOperationDetail();
		//roleOperationMatrix = docMgmtImpl.getOperationByUserTypeId(userTypeCode);
		//System.out.println("Previous Operationid Length : "+prevOperationIds.size());

		/*for (String opIds : prevOperationIds) {
			System.out.println("before:"+opIds);
		}*/
		if(prevOperationIds!=null){
			for (int i = 0; i < prevOperationIds.size(); i++) {
				String prevOpId = prevOperationIds.get(i);
				
				for (int j = 0;j < operationIds.size(); j++) {
					if(operationIds.get(j).equals(prevOpId)){
						operationIds.remove(j);
						prevOperationIds.remove(i--);
						break;
					}
				}
				
			}
		}
		
		int userCode= Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		ArrayList<DTORoleOperationMatrix> addedOpCode = new ArrayList<DTORoleOperationMatrix>();
		if(prevOperationIds!=null){
			for (String string : prevOperationIds) {
				//System.out.println("Removed OperationCode: "+string);
				DTORoleOperationMatrix dtorole = new DTORoleOperationMatrix();
				dtorole.setOperationCode(string);
				dtorole.setUserTypeCode(userTypeCode);
				dtorole.setActiveFlag('N');	// Deleted Operation for User TypeCode
				dtorole.setModifyBy(userCode);
				addedOpCode.add(dtorole);
			}
		}
		for(int i=0;i<operationIds.size();i++){
			DTORoleOperationMatrix dtorole = new DTORoleOperationMatrix();
			//System.out.println("New Added Operation ID.."+operationIds.get(i)+"::::Length of Array.."+operationIds.size());
			dtorole.setOperationCode(operationIds.get(i));
			dtorole.setUserTypeCode(userTypeCode);
			dtorole.setActiveFlag('Y');		// New Added Operation for User TypeCode
			dtorole.setModifyBy(userCode);
			addedOpCode.add(dtorole);
		}

		docMgmtImpl.insertIntoRoleOperationMatrix(addedOpCode);		//Insert-Update into Database
		
		addActionMessage("Operation Save Successfully...");
			return SUCCESS;
	}
	public Vector getListTemp() {
		return listTemp;
	}

	public void setListTemp(Vector listTemp) {
		this.listTemp = listTemp;
	}
	public String getHtmlRole() {
		return htmlRole;
	}
	public void setHtmlRole(String htmlRole) {
		this.htmlRole = htmlRole;
	}
	public Vector getAllUserType() {
		return allUserType;
	}

	public void setAllUserType(Vector allUserType) {
		this.allUserType = allUserType;
	}
	public String getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}


	
}
