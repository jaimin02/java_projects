package com.docmgmt.struts.actions.labelandpublish.PDFPublish;

import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.master.WorkspaceNodeDetail;

public class NodeDetails {
	
	public Vector<DTOWorkSpaceNodeDetail> getNodeDetailsById(String wsId,int nodeid){
		Vector<DTOWorkSpaceNodeDetail> wsNodeDetail = new Vector<DTOWorkSpaceNodeDetail>();
		WorkspaceNodeDetail wnd=new WorkspaceNodeDetail();
		wsNodeDetail=wnd.getParentNode(wsId, nodeid);
		return wsNodeDetail;
		
		
	}
}
