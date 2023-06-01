package com.docmgmt.struts.actions.master.attribute;



import com.docmgmt.dto.DTOAttrReferenceTableMatrix;
import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;



public class SaveAttribute extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	DTOAttributeMst AttribDao;
	public String attributeName;
	public String attributeValue;
	public String attributeType;
	public String attrForIndiId;
	public String userTypeCode;
	public String userInput;
	public String attributeRemark;
	public int iattrid;
	public String tableName;
	public String attrColumn;

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public String getAttrForIndiId() {
		return attrForIndiId;
	}

	public void setAttrForIndiId(String attrForIndiId) {
		this.attrForIndiId = attrForIndiId;
	}

	public String getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public String getUserInput() {
		return userInput;
	}

	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}

	public String getAttributeRemark() {
		return attributeRemark;
	}

	public void setAttributeRemark(String attributeRemark) {
		this.attributeRemark = attributeRemark;
	}
	
		
	
	@Override
	public String execute()
	{
	
		//attriblist=attribservice.getAllAttrib(); 
		
					
		AttribDao=new DTOAttributeMst();
		
		AttribDao.setAttrName(attributeName.trim());
		AttribDao.setAttrValue(attributeValue.trim());
		AttribDao.setAttrType(attributeType);
		AttribDao.setUserTypeCode(userTypeCode);
		AttribDao.setAttrForIndiId(attrForIndiId);
		AttribDao.setUserInput(userInput.charAt(0));
		AttribDao.setRemark(attributeRemark.trim());
		
		
		try
		{
			String uid=ActionContext.getContext().getSession().get("userid").toString();
			iattrid=docMgmtImpl.InsertAttribute(AttribDao,Integer.parseInt(uid));
			
		}
		catch (Exception e) {
			
			System.out.print("Error while executing insert attribute action"+e);
		}
		
		if(AttribDao.getAttrType().equals("Combo") || AttribDao.getAttrType().equals("AutoCompleter"))
		{
			return "ComboForm";
		}
		else if (AttribDao.getAttrType().equals("Dynamic"))
		{
			String uid=ActionContext.getContext().getSession().get("userid").toString();
			DTOAttrReferenceTableMatrix dtoAttrReferenceTableMatrix = new DTOAttrReferenceTableMatrix();
			dtoAttrReferenceTableMatrix.setiAttrId(iattrid);
			dtoAttrReferenceTableMatrix.setvTableName(tableName);
			dtoAttrReferenceTableMatrix.setvTableColumn(attrColumn);
			dtoAttrReferenceTableMatrix.setvTableType("I");
			dtoAttrReferenceTableMatrix.setvRemark(AttribDao.getRemark());
			dtoAttrReferenceTableMatrix.setiModifyBy(Integer.parseInt(uid));
			docMgmtImpl.Insert_AttrReferenceTableMatrix(dtoAttrReferenceTableMatrix,1);
			System.out.println("dynamic attr added!!!");
			return SUCCESS;
		}
		else
		{
			return SUCCESS;
		}
		
	}

	public int getIattrid() {
		return iattrid;
	}

	public void setIattrid(int iattrid) {
		this.iattrid = iattrid;
	}

		
}
