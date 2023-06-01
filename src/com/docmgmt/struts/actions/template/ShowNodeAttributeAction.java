package com.docmgmt.struts.actions.template;

import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOAttrReferenceTableMatrix;
import com.docmgmt.dto.DTOAttributeValueMatrix;
import com.docmgmt.dto.DTODynamicTable;
import com.docmgmt.dto.DTOTemplateNodeAttrDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;



public class ShowNodeAttributeAction extends ActionSupport {//implements Preparable{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		getTemplateDtl = new Vector<DTOAttributeValueMatrix>();
		getTemplateDtlForLeafNodes = new Vector<DTOAttributeValueMatrix>();
		
		if(nodeId == 1)
		{
			getTemplateDtl.addAll(docMgmtImpl.getAttributeDetailByType("0000"));
		}
		else
		{
			getTemplateDtl.addAll(docMgmtImpl.getAttributeDetailByType("0002"));
		
			getTemplateDtl.addAll(docMgmtImpl.getAttributeDetailByType("0003"));
			
			//For new developement add by Mahavir
			getTemplateDtlForLeafNodes.addAll(docMgmtImpl.getAttributeDetailByType("0001"));
		}
		
		
		filterAutoCompleterList = filterAttributes(getTemplateDtl, "AutoCompleter");
		
		filterDynamicList = filterAttributes(getTemplateDtl,"Dynamic");
		
		
		String templateId=ActionContext.getContext().getSession().get("templateId").toString();
		DTOTemplateNodeAttrDetail dto=new DTOTemplateNodeAttrDetail();
		
		dto.setTemplateId(templateId);
		dto.setNodeId(nodeId);
		
		Vector templatenodeattrdetail=docMgmtImpl.getTemplateNodeAttrDtl(dto);
		templateAttrib = new Vector();
		templateAttribForLeafNode = new Vector();
		
		int size=templatenodeattrdetail.size();
		for(int i=0;i<size;i++)
		{
			
			dto=new DTOTemplateNodeAttrDetail();
			dto=(DTOTemplateNodeAttrDetail)templatenodeattrdetail.get(i);
			
			
			if(dto.getAttrForIndiId().trim().equals("0001") || dto.getAttrForIndiId().trim().equals("0002") || dto.getAttrForIndiId().trim().equals("0003"))
			{
				
				templateAttrib.addElement(dto);
			}
			if(dto.getAttrForIndiId().trim().equals("0001"))
				templateAttribForLeafNode.addElement(dto);
			
		}
		
		if(displayName==null)
		{
			displayName="Please Select Node";
		}
		return SUCCESS;
	}
	
	private ArrayList<Object[]> filterAttributes(Vector<DTOAttributeValueMatrix> attrDtl,String attrType){
		if(attrDtl == null || attrType == null){
			return null;
		}
		
		ArrayList<Object[]> filterAttributeList = new ArrayList<Object[]>();
		for (int i = 0; i < attrDtl.size(); i++) {
			DTOAttributeValueMatrix dtoAttributeValueMatrix = attrDtl.get(i);
			
			if(attrType.equals("AutoCompleter") && dtoAttributeValueMatrix.getAttrType().equals(attrType)){
				ArrayList<String> attrValueList = new ArrayList<String>();
				for (int j = 0; j < attrDtl.size(); j++) {
					DTOAttributeValueMatrix dtoAttributeValueMatrix_ForValue = attrDtl.get(j);
					if(dtoAttributeValueMatrix.getAttrId() == dtoAttributeValueMatrix_ForValue.getAttrId()){
						attrValueList.add(dtoAttributeValueMatrix_ForValue.getAttrMatrixValue());
						attrDtl.remove(j--);
					}
				}
				attrDtl.add(i, dtoAttributeValueMatrix);
				filterAttributeList.add(new Object[]{dtoAttributeValueMatrix.getAttrId(),attrValueList});
			}
			
			if(attrType.equals("Dynamic") && dtoAttributeValueMatrix.getAttrType().equals(attrType))
			{
				ArrayList<String> attrValueList = new ArrayList<String>();
				DTOAttrReferenceTableMatrix dtoAttrReferenceTableMatrix = new DTOAttrReferenceTableMatrix();
				dtoAttrReferenceTableMatrix.setiAttrId(dtoAttributeValueMatrix.getAttrId());
				ArrayList<DTOAttrReferenceTableMatrix> attrReferenceTableMatrixs = docMgmtImpl.getFromAttrReferenceTableMatrix(dtoAttrReferenceTableMatrix);
				if (attrReferenceTableMatrixs != null && attrReferenceTableMatrixs.size() > 0 && attrReferenceTableMatrixs.size() <= 1)
				{
					DTOAttrReferenceTableMatrix dtoAttrReferenceTableMatrix2 = attrReferenceTableMatrixs.get(0);
					String tableName = dtoAttrReferenceTableMatrix2.getvTableName();
					String tableColumn = dtoAttrReferenceTableMatrix2.getvTableColumn();
					DTODynamicTable dtoDynamicTable = docMgmtImpl.getCompleteTableDetails(tableName);
								
					int idxColumn = dtoDynamicTable.getColumnNames().indexOf(tableColumn);
					
					if (idxColumn >= 0 && idxColumn < dtoDynamicTable.getColumnNames().size())
					{
						for (int indTableRecord = 0 ; indTableRecord < dtoDynamicTable.getTableData().size() ; indTableRecord ++)
						{
							attrValueList.add(dtoDynamicTable.getTableData().get(indTableRecord).get(idxColumn));
							//System.out.println(indTableRecord + " ---- " + dtoDynamicTable.getTableData().get(indTableRecord).get(idxColumn));
						}
					}
				}
				filterAttributeList.add(new Object[]{dtoAttributeValueMatrix.getAttrId(),attrValueList});
			}
		}
		
		return filterAttributeList;
	}
	
	
	public ArrayList< Object[]> filterAutoCompleterList;
	public ArrayList<Object[]> filterDynamicList;
	
	public Vector<DTOAttributeValueMatrix> getTemplateDtl;
	public Vector<DTOAttributeValueMatrix> getTemplateDtlForLeafNodes;
	
	public Vector templateAttribForLeafNode;
	public Vector templateAttrib;
	public int nodeId;
	public String displayName;

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Vector<DTOAttributeValueMatrix> getGetTemplateDtl() {
		return getTemplateDtl;
	}

	public void setGetTemplateDtl(Vector<DTOAttributeValueMatrix> getTemplateDtl) {
		this.getTemplateDtl = getTemplateDtl;
	}
	
	public Vector<DTOAttributeValueMatrix> getGetTemplateDtlForLeafNodes() {
		return getTemplateDtlForLeafNodes;
	}

	public void setGetTemplateDtlForLeafNodes(Vector<DTOAttributeValueMatrix> getTemplateDtlForLeafNodes) {
		this.getTemplateDtlForLeafNodes = getTemplateDtlForLeafNodes;
	}
}