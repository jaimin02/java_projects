package com.docmgmt.struts.actions.template;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOTemplateNodeAttrDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.db.master.AttributeMst;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveTemplateNodeAttrAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	@Override
	public String execute()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String templateId=ActionContext.getContext().getSession().get("templateId").toString();
		
		for(int i=1;i<=attrCount;i++)
    	{
    	    String attrValueId = "attrValueId"+i;
    	    String attrType = "attrType"+i;
    	    String attrId = "attrId"+i;
    	    String cattrId = "cattrId"+i;
    	    String attrName = "attrName"+i;
    	    String remark = "remark"+i;
    	    String coverPage = "coverPage"+i;
    	    
    	   /* System.out.println("Attr Value Id " + request.getParameter(attrValueId));
	        System.out.println("Attr Type     " + request.getParameter(attrType));
	        System.out.println("Attr       Id " + request.getParameter(attrId));
	        System.out.println("CAttr      Id " + request.getParameter(cattrId));
	        System.out.println("Attr Name     " + request.getParameter(attrName));
	        System.out.println("Attr remark   " + request.getParameter(remark));*/
	        
	      
	        //if attribute value is unchecked	
    	    if(request.getParameter(cattrId)==null)
    	    {   
    	    	try
    	    	{
	    	    	Integer atId = new Integer(request.getParameter(attrId));
	    	        DTOTemplateNodeAttrDetail dtotemp = new DTOTemplateNodeAttrDetail();
	    	        dtotemp.setTemplateId(templateId);
	    	        dtotemp.setNodeId(nodeId);
	    	        dtotemp.setAttrId(atId.intValue());
	    	        docMgmtImpl.insertIntoTemplateNodeAttrDtl(dtotemp,3);
    	    	}
    	    	catch (Exception e) {
					// TODO: handle exception
				}
    	    }
    	    else
    	    {
    	    	//if attribute value is checked 
    	    	//System.out.println("inside");
    	    	
    	    	Integer aId = new Integer(request.getParameter(attrId));
	    	    DTOTemplateNodeAttrDetail dto = new DTOTemplateNodeAttrDetail();
	    	    
	    	    dto.setAttrId(aId.intValue());
	    	    dto.setTemplateId(templateId);
	    	    dto.setNodeId(nodeId);
	    	    dto.setAttrValue(request.getParameter(attrValueId));
	    	    dto.setAttrName(request.getParameter(attrName));
	    	    dto.setRemark(request.getParameter(remark));
	    	    dto.setModifyBy(userCode);
	    	    AttributeMst attributeMst = new AttributeMst();
	    	    if(request.getParameter(coverPage)!=null){
	    	    	dto.setValidValues("Cover Page");
	    	    }
	    	    DTOAttributeMst dtoAttribute = attributeMst.getAttribute(aId.intValue());
	    	    dto.setAttrForIndiId(dtoAttribute.getAttrForIndiId());
	    	   // docMgmtImpl.insertIntoTemplateNodeAttribute(dto);
	    	    
	    	    docMgmtImpl.insertIntoTemplateNodeAttrDtl(dto,1);
	    	
    	     }
    	}
	
		return SUCCESS;
	}
	
	public String saveLeafNodeAttribute(){

		HttpServletRequest request = ServletActionContext.getRequest();
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String templateId=ActionContext.getContext().getSession().get("templateId").toString();
		
		for(int i=1;i<=attrCount;i++)
    	{
    	    String attrValueId = "attrValueId"+i;
    	    String attrType = "attrType"+i;
    	    String attrId = "attrId"+i;
    	    String cattrId = "cattrId"+i;
    	    String attrName = "attrName"+i;
    	    String remark = "remark"+i;
    	    
    	   /* System.out.println("Attr Value Id " + request.getParameter(attrValueId));
	        System.out.println("Attr Type     " + request.getParameter(attrType));
	        System.out.println("Attr       Id " + request.getParameter(attrId));
	        System.out.println("CAttr      Id " + request.getParameter(cattrId));
	        System.out.println("Attr Name     " + request.getParameter(attrName));
	        System.out.println("Attr remark   " + request.getParameter(remark));*/
	        
	      
	        //if attribute value is unchecked	
    	    if(request.getParameter(cattrId)==null)
    	    {   
    	    	try
    	    	{
	    	    	Integer atId = new Integer(request.getParameter(attrId));
	    	        DTOTemplateNodeAttrDetail dtotemp = new DTOTemplateNodeAttrDetail();
	    	        dtotemp.setTemplateId(templateId);
	    	        dtotemp.setNodeId(nodeId);
	    	        dtotemp.setAttrId(atId.intValue());
	    	        docMgmtImpl.insertIntoTemplateNodeAttrDtl(dtotemp,3);
    	    	}
    	    	catch (Exception e) {
					// TODO: handle exception
				}
    	    }
    	    else
    	    {
    	    	//if attribute value is checked 
    	    	//System.out.println("inside");
    	    	
    	    	Integer aId = new Integer(request.getParameter(attrId));
	    	    DTOTemplateNodeAttrDetail dto = new DTOTemplateNodeAttrDetail();
	    	    
	    	    dto.setAttrId(aId.intValue());
	    	    dto.setTemplateId(templateId);
	    	    dto.setNodeId(nodeId);
	    	    dto.setAttrValue(request.getParameter(attrValueId));
	    	    dto.setAttrName(request.getParameter(attrName));
	    	    dto.setRemark(request.getParameter(remark));
	    	    dto.setModifyBy(userCode);
	    	    AttributeMst attributeMst = new AttributeMst();
	    	    DTOAttributeMst dtoAttribute = attributeMst.getAttribute(aId.intValue());
	    	    dto.setAttrForIndiId(dtoAttribute.getAttrForIndiId());
	    	   // docMgmtImpl.insertIntoTemplateNodeAttribute(dto);
	    	    
	    	    docMgmtImpl.insertIntoTemplateNodeAttrDtl(dto,1);
	    	
    	     }
    	}
		return SUCCESS;	
	}
	
	
	public int attrCount;
	public int nodeId;

	public int getAttrCount() {
		return attrCount;
	}
	public void setAttrCount(int attrCount) {
		this.attrCount = attrCount;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
}
