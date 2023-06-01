package com.docmgmt.struts.actions.reports;

import java.io.File;
import java.util.Vector;



import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;

public class ShowDocumentPropertiesAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public int nodeId;
	public Vector documentProperties;
	public Vector projectDetail;
	public Vector documentSYSProperties;
	public Vector docUserDefinedProp;
	public String created;
	public String approved;
	public String reviewed; 
	
	
	public Vector getDocumentProperties() {
		return documentProperties;
	}

	public void setDocumentProperties(Vector documentProperties) {
		this.documentProperties = documentProperties;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	
	public Vector getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(Vector projectDetail) {
		this.projectDetail = projectDetail;
	}

	public Vector getDocumentSYSProperties() {
		return documentSYSProperties;
	}

	public void setDocumentSYSProperties(Vector documentSYSProperties) {
		this.documentSYSProperties = documentSYSProperties;
	}

	public Vector getDocUserDefinedProp() {
		return docUserDefinedProp;
	}

	public void setDocUserDefinedProp(Vector docUserDefinedProp) {
		this.docUserDefinedProp = docUserDefinedProp;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public String getReviewed() {
		return reviewed;
	}

	public void setReviewed(String reviewed) {
		this.reviewed = reviewed;
	}
	

	@Override
	public String execute()
	{
		
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());  
		String workspaceId=ActionContext.getContext().getSession().get("ws_id").toString();
		
		created = "Not Available"; 
		approved = "Not Available";
		reviewed = "Not Available";
		
		DTOWorkSpaceMst dto=new DTOWorkSpaceMst();
	 	dto.setWorkSpaceId(workspaceId);
	   	dto.setNodeId(nodeId);
	   	int tranNo=docMgmtImpl.getMaxTranNo(dto);
	   	dto.setMaxTranNo(tranNo);
	   	
	   	documentProperties=docMgmtImpl.getDocumentPropertiesForReport(dto);
	   	projectDetail=getProjectDeatilOfDoc(documentProperties);
	   	
	   
    	documentSYSProperties=getSystemPropOfDoc(documentProperties);
    	docUserDefinedProp = getUserDefinedPropOfDoc(documentProperties);
    	
    /*	
    	created = "Not Available"; 
    	approved = "Not Available";
    	reviewed = "Not Available";
	  */	
		return SUCCESS;
		
			
	}
		

	private Vector getProjectDeatilOfDoc(Vector documentProperties){
		
		Vector vector= new Vector();
		DTOWorkSpaceMst dto = null;
		DTOWorkSpaceMst dtopropdetail = null;
		
		if(documentProperties.size() > 0)
		{
			dto=(DTOWorkSpaceMst)documentProperties.get(0);
				
				dtopropdetail = new DTOWorkSpaceMst();
				dtopropdetail.setAttributeName("Project :");
				dtopropdetail.setAttrValue(dto.getWorkSpaceDesc());
				vector.addElement(dtopropdetail);
			    dtopropdetail = null;
			    
			    dtopropdetail = new DTOWorkSpaceMst();
				dtopropdetail.setAttributeName("Location :");
				dtopropdetail.setAttrValue(dto.getLocationName());
				vector.addElement(dtopropdetail);
				dtopropdetail = null;
				
				dtopropdetail = new DTOWorkSpaceMst();
				dtopropdetail.setAttributeName("Department :");
				dtopropdetail.setAttrValue(dto.getDeptName());
				vector.addElement(dtopropdetail);
				dtopropdetail = null;
				
				dtopropdetail = new DTOWorkSpaceMst();
				dtopropdetail.setAttributeName("Client :");
				dtopropdetail.setAttrValue(dto.getClientName());
				vector.addElement(dtopropdetail);
				dtopropdetail = null;
				
			dto = null;
		}
			
		return vector;
	}
	
	private Vector getSystemPropOfDoc(Vector documentProperties){
	
	Vector vector=new Vector();
	long filesize = 0;
	File file = null;
	DTOWorkSpaceMst dto = null;
	DTOWorkSpaceMst dtopropdetail = null;
	String tempstr="";
	if(documentProperties.size() > 0)
	{
		dto=(DTOWorkSpaceMst)documentProperties.get(0);
		file=new File(dto.getBaseWorkFolder()+File.separator+dto.getFolderName()+File.separator+dto.getFileName());
		filesize=file.length();
		dtopropdetail = new DTOWorkSpaceMst();
		
		dtopropdetail = new DTOWorkSpaceMst();
		dtopropdetail.setAttributeName("Acitvity/Node :");
		if(dto.getNodeDisplayName()==null)dtopropdetail.setAttrValue("Not available");
		else dtopropdetail.setAttrValue(dto.getNodeDisplayName());
		vector.addElement(dtopropdetail);
		dtopropdetail = null;
		
		dtopropdetail = new DTOWorkSpaceMst();
		dtopropdetail.setAttributeName("Document Name :");
		if(dto.getFileName()==null)dtopropdetail.setAttrValue("Not available");
		else dtopropdetail.setAttrValue(dto.getNodeDisplayName());
		dtopropdetail.setAttrValue(dto.getFileName());
		vector.addElement(dtopropdetail);
		dtopropdetail = null;
	
		dtopropdetail = new DTOWorkSpaceMst();
		dtopropdetail.setAttributeName("Physical Location :");
		if(file.getAbsolutePath()==null)dtopropdetail.setAttrValue("Not available");
		else dtopropdetail.setAttrValue(file.getAbsolutePath());
		vector.addElement(dtopropdetail);
		dtopropdetail = null;
		
		dtopropdetail = new DTOWorkSpaceMst();
		dtopropdetail.setAttributeName("File Size :");
		dtopropdetail.setAttrValue("In Byte : " + Long.toString(filesize) + "     " + " In KB : " + Long.toString(filesize/1024)+  "     " + "In MB : " + "     " + Long.toString(filesize/(1024*1024)));
		vector.addElement(dtopropdetail);
		dtopropdetail = null;
		
		dtopropdetail = new DTOWorkSpaceMst();
		dtopropdetail.setAttributeName("User Defined Version :");
		if(dto.getUserDefinedVersionId()==null)dtopropdetail.setAttrValue("Not available");
		else dtopropdetail.setAttrValue(dto.getUserDefinedVersionId());
		vector.addElement(dtopropdetail);
		dtopropdetail = null;
		
		dtopropdetail = new DTOWorkSpaceMst();
		dtopropdetail.setAttributeName("System Version :");
		
		if(dto.getFileVersionId()==null)dtopropdetail.setAttrValue("Not available");
		else dtopropdetail.setAttrValue(dto.getFileVersionId());
		vector.addElement(dtopropdetail);
		dtopropdetail = null;
		
		dto = null;
		file = null;
	}	
	return vector;
	}
	
	private Vector getUserDefinedPropOfDoc(Vector documentProperties){
		
		Vector userdefinedprop=new Vector();
		DTOWorkSpaceMst propdto=null;
		DTOWorkSpaceMst passUserDefinedAttr = null;
		String approvedStatus = "";
		String attributeName = "";
		if(documentProperties.size()==0){
			created ="Not Available";
			approved ="Not Available";
			reviewed="Not Available";
			return userdefinedprop;
		}
		for(int icount=0;icount<documentProperties.size();icount++){
			
				propdto = (DTOWorkSpaceMst)documentProperties.get(icount);
				approvedStatus = propdto.getNodeStatusAttributeValue();
				
				if(approvedStatus==null){
					created ="Not Available";
					approved ="Not Available";
					reviewed="Not Available";
					return userdefinedprop;
				}
				
				attributeName = propdto.getAttributeName().trim().toLowerCase();
				if(approvedStatus!=null &&  approvedStatus.equalsIgnoreCase("created"))
				{	
						created = (propdto.getLoginName()==null)?"Not Available":propdto.getLoginName();
						if(attributeName.equalsIgnoreCase("autor")||
							attributeName.equalsIgnoreCase("keywords") ||
							attributeName.equalsIgnoreCase("description") ||
							attributeName.equalsIgnoreCase("processgroup") ||
							attributeName.equalsIgnoreCase("language") ||
							attributeName.equalsIgnoreCase("Renewal in Month") ||
							attributeName.equalsIgnoreCase("Review in Month"))
						{
								passUserDefinedAttr = new DTOWorkSpaceMst();
								passUserDefinedAttr.setAttributeName(propdto.getAttributeName().trim().toLowerCase());
								passUserDefinedAttr.setWorkSpaceNodeAttrHistoryAttributeValue(propdto.getWorkSpaceNodeAttrHistoryAttributeValue().trim().toLowerCase());
								userdefinedprop.addElement(passUserDefinedAttr);
								passUserDefinedAttr = null;
						}	
				}
				
				if(approvedStatus!=null &&  approvedStatus.equalsIgnoreCase("reviewed"))
					reviewed =(propdto.getLoginName()==null)?"Not Available":propdto.getLoginName();
				if(approvedStatus!=null && approvedStatus.equalsIgnoreCase("approved"))
					approved = (propdto.getLoginName()==null)?"Not Available":propdto.getLoginName();
				propdto = null;
			}
		
		return userdefinedprop;
		
	}	
	
	
}	
