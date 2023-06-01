package com.docmgmt.struts.actions.labelandpublish.PreValidation;

import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class savaData extends ActionSupport {
	private static final long serialVersionUID = 5212635465958951572L;
	private DocMgmtImpl docMgmtImpl= new DocMgmtImpl();
	
	public String workspaceId;
	public String totalAttr;
	public String htmlContent;
	@Override
	public String execute() throws Exception 
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		int totalAttrVal = Integer.parseInt(totalAttr);
		Vector<DTOWorkSpaceNodeAttrDetail> wsNodeAttrDtlList = new Vector<DTOWorkSpaceNodeAttrDetail>();
		ArrayList<DTOWorkSpaceNodeDetail> workSpaceNodeDetailList = new ArrayList<DTOWorkSpaceNodeDetail>();
		for (int itrParaVal = 1; itrParaVal <= totalAttrVal; itrParaVal++) 
		{
			DTOWorkSpaceNodeAttrDetail dtoWsNodeAttrDtl = new DTOWorkSpaceNodeAttrDetail();
			int attrId =Integer.parseInt(request.getParameter("attrId_"+itrParaVal));
			int nodeId = Integer.parseInt(request.getParameter("nodeId_"+itrParaVal));
			dtoWsNodeAttrDtl.setWorkspaceId(workspaceId);			
			dtoWsNodeAttrDtl.setAttrId(attrId);
			dtoWsNodeAttrDtl.setNodeId(nodeId);
			dtoWsNodeAttrDtl.setAttrValue(request.getParameter("attrValue_"+itrParaVal));
			wsNodeAttrDtlList.add(dtoWsNodeAttrDtl);
		}
		docMgmtImpl.updateNodeAttrDetail(wsNodeAttrDtlList);
		
		ArrayList<Integer> nodeIds= new ArrayList<Integer>();
		for (DTOWorkSpaceNodeAttrDetail dtoWorkSpaceNodeAttrDetail : wsNodeAttrDtlList) {
			if(!nodeIds.contains(dtoWorkSpaceNodeAttrDetail.getNodeId()))
			{
				nodeIds.add(dtoWorkSpaceNodeAttrDetail.getNodeId());
			}
		}
		for (Integer nodeId : nodeIds)
		{
			DTOWorkSpaceNodeDetail wsnd = docMgmtImpl.getNodeDetail(workspaceId, nodeId).get(0);
			String attrNodeDisplayName="";
			for (DTOWorkSpaceNodeAttrDetail dtoWorkSpaceNodeAttrDetail : wsNodeAttrDtlList) 
			{
				if(nodeId==dtoWorkSpaceNodeAttrDetail.getNodeId())
				{
					if(attrNodeDisplayName.equals(""))
					{
						attrNodeDisplayName=dtoWorkSpaceNodeAttrDetail.getAttrValue();
					}
					else if(dtoWorkSpaceNodeAttrDetail.getAttrValue().trim() != "")
					{
						attrNodeDisplayName+=","+dtoWorkSpaceNodeAttrDetail.getAttrValue();
					}
				}
			}
			if(attrNodeDisplayName.equals("")){			    		
	    		//pattern used for attr values appended at the end of nodeDisplayName between '(' and ')'.
	    		wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().replaceAll("\\(.+\\)$",""));
	    	}
	    	else{
	    		//pattern used for attr values appended at the end of nodeDisplayName between '(' and ')'.
	    		wsnd.setNodeDisplayName(wsnd.getNodeDisplayName().replaceAll("\\(.+\\)$","")+"("+attrNodeDisplayName+")");
	    	}
			workSpaceNodeDetailList.add(wsnd);
		}
		docMgmtImpl.insertWorkspaceNodeDetail(workSpaceNodeDetailList, 2);//edit mode
		htmlContent= "Data Updated Successfully";
		return SUCCESS;
	}
}