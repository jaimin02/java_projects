package com.docmgmt.struts.actions.template;



import java.util.Vector;

import com.docmgmt.dto.DTOTemplateMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowStructureAction extends ActionSupport {//implements Preparable{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public int TotalRecords;
	public int givenPageNumber;
	public String SortOrder;
	public Vector getTemplateDtl;
	public String Order;
	public Vector pageDtl;
	
	public Vector getPageDtl() {
		return pageDtl;
	}

	public void setPageDtl(Vector pageDtl) {
		this.pageDtl = pageDtl;
	}

	public String getOrder() {
		return Order;
	}

	public void setOrder(String order) {
		Order = order;
	}

	@Override
	public String execute()
	{
		
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());	
		Vector getAllTemplates = docMgmtImpl.getAllTemplates();
		
		int maxCount = docMgmtImpl.getTotalTemplateRecordCount();
		int PageNumber = 0;  
		int pageCount = 0;
		int PageData=0;
		int PageNo=0;
		
		// If Records are present.
		
		if(maxCount > 0)
		{    
		    //page is loaded first time or selected Page Number = 1.
		    //set pageNumber to 0 because record counter start with zero in database.
		    //else get pagenumber as it is.	
			if(givenPageNumber==0 || givenPageNumber==1)
		    	PageNumber = 0;
			else
			    PageNumber = givenPageNumber;
			//set first record pointer.
			PageNumber = (PageNumber*20)-20;
			//////////////////
			 PageData=PageNumber;
			 PageNo=PageData+20;
			//PageNo=PageNo+1;
			 PageData=PageNumber+1;
			///////////////////////////////
			if(PageNumber<=0)
				PageNumber = 0;
			//fill pageNumber Vector for JSP Page.
			pageCount = (int)Math.ceil((float)maxCount/20);
			
			System.out.println("pagecount"+pageCount);
			 pageDtl = new Vector();
			
			
			for(int i=1;i<=pageCount;i++)
			{
			    DTOTemplateMst obj = new  DTOTemplateMst();
			    obj.setPageNumber(i);
			    pageDtl.addElement(obj);
			    obj=null;
			}
		
 		}
		else
		    givenPageNumber=0;
		
	//-----------------------------Logic For Paging Ended-------------------------------------------	
		 Order = null;
		
		if(SortOrder==null)
		    Order = "Desc";
		else
		    Order = SortOrder;
	
		
			if(givenPageNumber==0 || givenPageNumber==1){
			getTemplateDtl = docMgmtImpl.getAllTemplatesDetail(20,PageNumber,givenPageNumber,Order);
			}
			else{
			getTemplateDtl = docMgmtImpl.getAllTemplatesDetail(PageData,PageNo,givenPageNumber,Order);
			}
		
		TotalRecords= maxCount;
		return SUCCESS;
		
	}
	

	public Vector getGetTemplateDtl() {
		return getTemplateDtl;
	}


	public void setGetTemplateDtl(Vector getTemplateDtl) {
		this.getTemplateDtl = getTemplateDtl;
	}


	public String getSortOrder() {
		return SortOrder;
	}


	public void setSortOrder(String sortOrder) {
		SortOrder = sortOrder;
	}



	public int getGivenPageNumber() {
		return givenPageNumber;
	}


	public void setGivenPageNumber(int givenPageNumber) {
		this.givenPageNumber = givenPageNumber;
	}


	public int getTotalRecords() {
		return TotalRecords;
	}


	public void setTotalRecords(int totalRecords) {
		TotalRecords = totalRecords;
	}
	
}