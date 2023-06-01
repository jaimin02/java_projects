package com.docmgmt.struts.actions.nodeWiseAttributes;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;
import java.util.Vector;



public class GetAttrsOrAttrGroupAction extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	@Override
	public String execute()
	{
		System.out.println("first started:");

    	if("attrs".equals(attrOrAttrGroup))
    	{
    		getAttrDtl = docMgmtImpl.getAttrForAttrGroupBySpecifiedAttrType("All");
    	}
    	else if("attrgroup".equals(attrOrAttrGroup))
    	{
    		
    		getAttrGroupDtl = docMgmtImpl.getAllAttributeGroup();
	       
	       /* for(int icount=0;icount<getAttrGroupDtl.size();icount++)
	        {
	        	
	        	String grpId=((DTOAttributeGroupMst)getAttrGroupDtl.get(icount)).getAttrGroupId();
	        	groupWiseAttrs = docMgmtImpl.getAttributeGroupMatrixValues(grpId);
	            
	        	//groupWiseAttrs.addElement(attrs);
	        }*/
	      
    	}
		return SUCCESS;
	}
	public Vector getAttrGroupDtl;
//	public Vector attrs;
	public String attrOrAttrGroup;
	public Vector getAttrDtl;
	public Vector groupWiseAttrs;

	
	public Vector getGetAttrDtl() {
		return getAttrDtl;
	}

	public void setGetAttrDtl(Vector getAttrDtl) {
		this.getAttrDtl = getAttrDtl;
	}

	public Vector getGroupWiseAttrs() {
		return groupWiseAttrs;
	}

	public void setGroupWiseAttrs(Vector groupWiseAttrs) {
		this.groupWiseAttrs = groupWiseAttrs;
	}

	public String getAttrOrAttrGroup() {
		return attrOrAttrGroup;
	}

	public void setAttrOrAttrGroup(String attrOrAttrGroup) {
		this.attrOrAttrGroup = attrOrAttrGroup;
	}

	public Vector getGetAttrGroupDtl() {
		return getAttrGroupDtl;
	}

	public void setGetAttrGroupDtl(Vector getAttrGroupDtl) {
		this.getAttrGroupDtl = getAttrGroupDtl;
	}

/*	public Vector getAttrs() {
		return attrs;
	}

	public void setAttrs(Vector attrs) {
		this.attrs = attrs;
	}*/
}


