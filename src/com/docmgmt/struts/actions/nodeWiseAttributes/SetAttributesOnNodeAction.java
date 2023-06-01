package com.docmgmt.struts.actions.nodeWiseAttributes;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.webinterface.beans.nIndex;
import com.docmgmt.server.db.*;
import java.util.Vector;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;


public class SetAttributesOnNodeAction extends ActionSupport  {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	private String ws_id;
	
	private String workSpaceDesc;
	private String ProjectType;
	private String Client;
	private String Location;
	
	private Vector getDtl;
	private Vector[] spaceArray;
	private String baseworkfolder;
	private int[] tranNo;
	private int totalEmptyNodes = 0;
	private int totalFilledNodes = 0;
	private int total;
	private Object[] symbols = {"-","P","File Not Found"};
	
	public String htmlCode;
	
	
	@Override
	public String execute()
	{
	
		nIndex nindex=new nIndex();
		
		Vector wsDetail=docMgmtImpl.getWorkspaceDesc(ws_id);
    	 if(!wsDetail.isEmpty())
    	 {
    		Object record[]=(Object[])wsDetail.get(0);
    		workSpaceDesc =(String)record[1];
    		Client =(String)record[4];
    		Location=(String)record[2];
    		ProjectType=(String)record[5];
    	
    		
    		int uCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
    		int uGCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
    		
    		getDtl = nindex.getTreeNodes(ws_id,uGCode,uCode);
    		 
    		int size = getDtl.size();
    		spaceArray = new Vector[size];
    		tranNo = new int[size];
    		DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
    		
    		
    		for(int i = 0; i < size; i++){    // to get spaces and max tranno for tree nodes and 
    			 
    			Object[] temp = (Object[])getDtl.get(i);
    			Integer tempInt = (Integer)temp[7];
    			spaceArray[i] = new Vector();
    			 
    			for(int j = 0; j < tempInt.intValue(); j++){
    			 
    				spaceArray[i].addElement(" ");
    			}
    			 
    			if(temp[8] != symbols[1]){	 
    				
    				if(temp[3] != symbols[0]){
    					
    					Integer nodeId = (Integer)temp[0];
    					
    					//dto = docMgmtImpl.getBaseWorkFolderAndTranno(ws_id, nodeId.intValue());
    					//tranNo[i] = dto.getTranNo();
    					//baseworkfolder = dto.getBaseworkfolder();
    					totalFilledNodes++;
	    			
    				}
	    			else{
	    				
	    				totalEmptyNodes++;
	    			
	    			}
	    		
    			}
    			 
    		}// for loop end
    			 
    		
    		total = totalEmptyNodes + totalFilledNodes;
    		 
		  } // if end
    	 
    
		return SUCCESS;
	}
	
	public Vector[] getSpaceArray() {
		return spaceArray;
	}
	
	public int getTotal() {
		return total;
	}
	
	public int[] getTranNo() {
		return tranNo;
	}
	
	public int getTotalFilledNodes() {
		return totalFilledNodes;
	}
	
	public int getTotalEmptyNodes() {
		return totalEmptyNodes;
	}
	
	public Object[] getSymbols() {
		return symbols;
	}
	
	public String getWs_id() {
		return ws_id;
	}
	public void setWs_id(String ws_id) {
		this.ws_id = ws_id;
	}
	
	public String getBaseworkfolder() {
		return baseworkfolder;
	}
	
	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}

	public String getProjectType() {
		return ProjectType;
	}

	public String getClient() {
		return Client;
	}

	public String getLocation() {
		return Location;
	}

	public Vector getGetDtl() {
		return getDtl;
	}

	
}

