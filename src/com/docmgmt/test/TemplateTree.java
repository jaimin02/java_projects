package com.docmgmt.test;

import java.util.Vector;

import com.docmgmt.dto.DTOTemplateNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;

public class TemplateTree {

	/**
	 * @param args
	 */
	public int iParentId = 1;
	public String templateid="0088";
	DocMgmtImpl docMgmtInt = new DocMgmtImpl();
	public Vector parentNodes;
	public String nodeName, nodeDisplayName;
	public Integer nodeId;
	public StringBuffer folderName;
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		TemplateTree tree=new TemplateTree();
		tree.publish();
		
	}
	
	public void publish() throws Exception
	{
		parentNodes = docMgmtInt.getTemplateChildNodeByParentForPublishForM1(
				iParentId, templateid);
		
		
		
		getChildNode(parentNodes, iParentId);
		
	}
	
	public void getChildNode(Vector childNodes, 
			int parentId)	throws Exception {
		if (childNodes.size() == 0) {
					
				
		}
		else
		{
			for (int i = 0; i < childNodes.size(); i++) {
				DTOTemplateNodeDetail dtowsnd = (DTOTemplateNodeDetail) childNodes
						.get(i);
				
				nodeId = dtowsnd.getNodeId();
				nodeName = dtowsnd.getNodeName();
				nodeDisplayName = dtowsnd.getNodeDisplayName().trim();
				

				int isLeaf = docMgmtInt
						.isTemplateLeafNode(nodeId.intValue(),templateid);
				
				if(isLeaf!=0)
				{
					
				}
				else
					System.out.println(nodeName);
				
				parentNodes = docMgmtInt.getTemplateChildNodeByParentForPublishForM1(nodeId
						.intValue(), templateid);
				getChildNode(parentNodes,nodeId.intValue());
				
				
			}
		}
	
	}

}
