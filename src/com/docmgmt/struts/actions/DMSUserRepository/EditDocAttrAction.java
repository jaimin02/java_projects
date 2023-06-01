package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOAttrReferenceTableMatrix;
import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTODynamicTable;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.entities.AttributeType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EditDocAttrAction extends ActionSupport {


	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	
	public String workSpaceId;
	public String nodeId;
	public int attrCount;
	public String attrValueId;
	public String attrType;
	public String attrId;
	public String attrName;
	public ArrayList< Object[]> filterAutoCompleterList;
	public ArrayList<Object[]> filterDynamicList;
	public Vector<DTOWorkSpaceNodeAttribute> attrDtl=new Vector<DTOWorkSpaceNodeAttribute>();
	public DTOAttributeMst dtoAttribMst;
	
	@Override
	public String execute()
	{
		attrDtl.addAll(docMgmtImpl.getNodeAttrByAttrForIndiId(workSpaceId, Integer.parseInt(nodeId),AttributeType.USER_DEFINED_ATTR));
		filterAutoCompleterList = filterAttributes(attrDtl,"AutoCompleter");
		filterDynamicList= filterAttributes(attrDtl,"Dynamic");
		return SUCCESS;
	}
	
	public String saveDocAttribute()
	{
		
		HttpServletRequest request = ServletActionContext.getRequest();
	    int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());	
		for(int i=1;i<attrCount;i++)
		{
			DTOWorkSpaceNodeAttribute  wsNodeAttribute = new DTOWorkSpaceNodeAttribute();
			String attrValueIds = "attrValueId"+i;
	    	String attrTypes = "attrType"+i;
	    	String attrIds= "attrId"+i;
	    	String attrNames = "attrName"+i;
	    	Integer attributeId = Integer.parseInt(request.getParameter(attrIds));
	    	wsNodeAttribute.setWorkspaceId(workSpaceId);
			wsNodeAttribute.setNodeId(Integer.parseInt(nodeId));
			wsNodeAttribute.setAttrId(attributeId);
			wsNodeAttribute.setAttrName(request.getParameter(attrNames));
	    	wsNodeAttribute.setAttrValue(request.getParameter(attrValueIds));
	    	wsNodeAttribute.setModifyBy(userId);
	    	docMgmtImpl.updateWorkspaceNodeAttributeValue(wsNodeAttribute);
		}	
		
		return SUCCESS;
	}

	private ArrayList<Object[]> filterAttributes(Vector<DTOWorkSpaceNodeAttribute> attrDtl,String attrType){
		if(attrDtl == null || attrType == null){
			return null;
		}
		
		ArrayList<Object[]> filterAttributeList = new ArrayList<Object[]>();
		for (int i = 0; i < attrDtl.size(); i++) 
		{
			DTOWorkSpaceNodeAttribute dtoWsNdAttr= attrDtl.get(i);
			if(attrType.equalsIgnoreCase("AutoCompleter") && dtoWsNdAttr.getAttrType().equalsIgnoreCase("AutoCompleter"))
			{
				ArrayList<String> attrValueList = new ArrayList<String>();
				for (int j = 0; j < attrDtl.size(); j++) {
					DTOWorkSpaceNodeAttribute dtoWsNdAttribute = attrDtl.get(j);
					if(dtoWsNdAttr.getAttrId() == dtoWsNdAttribute.getAttrId()){
						attrValueList.add(dtoWsNdAttribute.getAttrMatrixValue());
						attrDtl.remove(j--);
					}
				}
				attrDtl.add(i, dtoWsNdAttr);
				filterAttributeList.add(new Object[]{dtoWsNdAttr.getAttrId(),attrValueList});
			}
			
			if(attrType.equalsIgnoreCase("Dynamic") && dtoWsNdAttr.getAttrType().equalsIgnoreCase("Dynamic"))
			{
				ArrayList<String> attrValueList = new ArrayList<String>();
				DTOAttrReferenceTableMatrix dtoAttrReferenceTableMatrix = new DTOAttrReferenceTableMatrix();
				dtoAttrReferenceTableMatrix.setiAttrId(dtoWsNdAttr.getAttrId());
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
						}
					}
				}
				filterAttributeList.add(new Object[]{dtoWsNdAttr.getAttrId(),attrValueList});
			}
			
		}
		
		return filterAttributeList;
	}
}
