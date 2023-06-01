package com.docmgmt.struts.actions.nodeWiseAttributes;
import java.util.Vector;

import com.docmgmt.dto.DTOAttributeGroupMatrix;
import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;



public class SaveAttributesOnNodeAction extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		 String nodes[]=selectedNodes.trim().split("#");
		    DTOAttributeGroupMatrix attrmatrix= new DTOAttributeGroupMatrix();
		    DTOAttributeMst attrmst= new DTOAttributeMst();
		    for(int nodeCount=0;nodeCount<nodes.length;nodeCount++)
		    {
		    	int nodeId=Integer.parseInt(nodes[nodeCount]);
			    if("attrs".equals(attrOrAttrGroup))
			    {
			    	//System.out.println("inside");
			   		Vector selectedAttrs=new Vector();
			   		Vector allAttrs=docMgmtImpl.getAttrForAttrGroupBySpecifiedAttrType("All");
			   		for(int icount=0;icount<allAttrs.size();icount++){
			    		attrmst=(DTOAttributeMst)allAttrs.get(icount);
			    		for(int jcount=0;jcount<multiCheckAttr.length;jcount++){
			    			
				    		if(attrmst.getAttrId()==(Integer.parseInt(multiCheckAttr[jcount])))
				    		{
				    			attrmatrix=new DTOAttributeGroupMatrix();
				    			attrmatrix.setAttrId(attrmst.getAttrId());
				    			attrmatrix.setNodeId(nodeId);
				    			attrmatrix.setWorkspaceId(sourceWsId);
				    			attrmatrix.setAttrValue(attrmst.getAttrValue());
				    			attrmatrix.setAttrName(attrmst.getAttrName());
				    			attrmatrix.setModifyBy(userId);
				    			attrmatrix.setAttrForIndi(attrmst.getAttrForIndiId());
				    			selectedAttrs.addElement(attrmatrix);
				    			attrmatrix=null;
				    			continue;
				    		}
			    		}
			    		attrmst=null;
			    	}
			    	docMgmtImpl.insertWorkSpaceNodeDetailForAttributeGroup(selectedAttrs,sourceWsId,nodeId);
			    	
			    	addActionMessage("Attributes Attached Successfully");
			    	
			    }
			    else if("attrgroup".equals(attrOrAttrGroup))
			    {
			    	System.out.println("Attaching  Attribute group  on Node");
			    	String attrGroupId[]=multiCheckAttrGroup;
			    	for(int icount=0;icount<attrGroupId.length;icount++)
					{
			    		System.out.println("attrGroupId["+icount+"]"+attrGroupId[icount]);
			    		attrGroupMatrixValues=docMgmtImpl.getAttributeGroupMatrixValues(attrGroupId[icount]);
						if(attrGroupMatrixValues!=null && !attrGroupMatrixValues.isEmpty())
				    	{
				    		docMgmtImpl.insertWorkSpaceNodeDetailForAttributeGroup(attrGroupMatrixValues,sourceWsId,nodeId);
				    	}
					}
			    	
			    	addActionMessage("Attribute Group Attached Successfully");
			    }
		    }
		return SUCCESS;
	}
	
	public Vector attrGroupMatrixValues;
	public String selectedNodes;
	public String sourceWsId;
	public String attrOrAttrGroup;
	public String[] multiCheckAttr;
	public String[] multiCheckAttrGroup;
	
	public String[] getMultiCheckAttrGroup() {
		return multiCheckAttrGroup;
	}
	public void setMultiCheckAttrGroup(String[] multiCheckAttrGroup) {
		this.multiCheckAttrGroup = multiCheckAttrGroup;
	}
	public String[] getMultiCheckAttr() {
		return multiCheckAttr;
	}
	public void setMultiCheckAttr(String[] multiCheckAttr) {
		this.multiCheckAttr = multiCheckAttr;
	}
	
	public String getAttrOrAttrGroup() {
		return attrOrAttrGroup;
	}
	public void setAttrOrAttrGroup(String attrOrAttrGroup) {
		this.attrOrAttrGroup = attrOrAttrGroup;
	}
	public String getSelectedNodes() {
		return selectedNodes;
	}
	public void setSelectedNodes(String selectedNodes) {
		this.selectedNodes = selectedNodes;
	}
	public String getSourceWsId() {
		return sourceWsId;
	}
	public void setSourceWsId(String sourceWsId) {
		this.sourceWsId = sourceWsId;
	}
	public Vector getAttrGroupMatrixValues() {
		return attrGroupMatrixValues;
	}
	public void setAttrGroupMatrixValues(Vector attrGroupMatrixValues) {
		this.attrGroupMatrixValues = attrGroupMatrixValues;
	}

}


