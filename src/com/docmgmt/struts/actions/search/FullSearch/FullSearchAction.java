package com.docmgmt.struts.actions.search.FullSearch;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOContentSearch;
import com.docmgmt.dto.DTOHotsearchDetail;
import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.services.pdf.PDFUtilities;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FullSearchAction extends ActionSupport
{
	
	private static final long serialVersionUID = 1L;
	DocMgmtImpl docmgmtImpl = new DocMgmtImpl();
	public ArrayList<DTOAttributeMst> attributeList;
	
	public String searchWorkspaceID;
	public String searchWorkspaceNodeName;
	public int searchWorkspaceStageID;
	public int[] searchWorkspaceStageIDs;
	public Vector<DTOWorkSpaceMst> workspaceList;
	public Vector<DTOStageMst> stageList;
	
	public String timeLineSearch(){
		searchFor = 'D'; // D for date(TimeLine call)
		attributeList = docmgmtImpl.getAttributesByDisplayType("Date");
		return SUCCESS;
	}
	public String contentSearch(){
		searchFor = 'K'; //K for Content(Keyword)
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		workspaceList = docmgmtImpl.getUserWorkspace(userGroupCode, userId);
		stageList = docmgmtImpl.getStageDetail();
		return SUCCESS;
	}
	
	boolean foundStageId (int stageId)
	{
		for (int indStage = 0 ; indStage < searchWorkspaceStageIDs.length ; indStage ++)
			if (stageId == searchWorkspaceStageIDs[indStage])
				return true;
		return false;
	}
	
	public String getSearchResult()
	{
		String returnType = "";
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		if(searchFor == 'D')
		{
			returnType = "date";
			if(selectedAttributes == null){
				return returnType;
			}
			selectedAttributes = selectedAttributes.replaceAll("\\s", ""); //Remove blank space from Returned string through Selection of the checkboxes 
			if(selectdate == 'Y')
			{
				if(firstDate == null || firstDate.equals("")){
					return returnType;
				}
				if(search == 'O')
				{
					fullSearchData = DocMgmtImpl.dateWiseSearch(userId,selectedAttributes ,"0000", firstDate,"", 1);
					fullSearchData.addAll(DocMgmtImpl.dateWiseSearch(userId,selectedAttributes ,"", firstDate,"", 1));
				}else if(search == 'B')
				{
					fullSearchData = DocMgmtImpl.dateWiseSearch(userId,selectedAttributes ,"0000", firstDate,"", 2);
					fullSearchData.addAll(DocMgmtImpl.dateWiseSearch(userId,selectedAttributes ,"", firstDate,"", 2));
				}else
				{
					fullSearchData = DocMgmtImpl.dateWiseSearch(userId,selectedAttributes ,"0000", firstDate,"", 3);
					fullSearchData.addAll(DocMgmtImpl.dateWiseSearch(userId,selectedAttributes ,"", firstDate,"", 3));
				}	
			}else
			{
				if((secondDate == null || secondDate.equals("")) || (thirdDate == null || thirdDate.equals("")))
				{
					return returnType;
				}
				fullSearchData = DocMgmtImpl.dateWiseSearch(userId,selectedAttributes ,"0000", secondDate, thirdDate, 4);
				fullSearchData.addAll(DocMgmtImpl.dateWiseSearch(userId,selectedAttributes ,"", secondDate, thirdDate, 4));
			}
			
		}else if(searchFor == 'K')
		{
			returnType = "content";
			int Mode=0;
			
			if(keyword == null || keyword.equals("")){
				return returnType;
			}
			
			/*searchWorkspaceID = "0123";
			searchWorkspaceNodeName = "m1";
			searchWorkspaceStageID = 10;*/
			
			System.out.println("searchWorkspaceID" + searchWorkspaceID);
			System.out.println("searchWorkspaceNodeName" + searchWorkspaceNodeName);
			System.out.println("searchWorkspaceStageID" + searchWorkspaceStageID);
			
			if (output == 'F') //search pdf contents
			{
				userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
				int usergroupId = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
				Vector<DTOWorkSpaceMst> userWorkspace = docmgmtImpl.getUserWorkspace(usergroupId, userId);
				for (int indWs = 0 ; indWs < userWorkspace.size() ; indWs ++)
				{
					DTOWorkSpaceMst dtoWorkSpaceMst = userWorkspace.get(indWs);
					if (searchWorkspaceID != null && !searchWorkspaceID.equals("0000") && !searchWorkspaceID.equals(dtoWorkSpaceMst.getWorkSpaceId()))
						continue;
					
					ArrayList<DTOWorkSpaceNodeHistory> allNodesLastHistory = 
						docmgmtImpl.getAllNodesLastHistory (dtoWorkSpaceMst.getWorkSpaceId(),null);
					for (int indNode = 0 ; indNode < allNodesLastHistory.size() ; indNode ++)
					{
						DTOWorkSpaceNodeHistory dtoWorkSpaceNodeHistory = allNodesLastHistory.get(indNode);
						
						if (searchWorkspaceNodeName != null && !searchWorkspaceNodeName.equals("") && !dtoWorkSpaceNodeHistory.getNodeName().toLowerCase().contains(searchWorkspaceNodeName.toLowerCase()))
						{
							continue;
						}
						
						
						if (searchWorkspaceStageID != 0 && dtoWorkSpaceNodeHistory.getStageId() != searchWorkspaceStageID)
						{
							continue;
						}
						
							
						String pdfFilePath = dtoWorkSpaceNodeHistory.getBaseWorkFolder() + dtoWorkSpaceNodeHistory.getFolderName() 
							+ "/" + dtoWorkSpaceNodeHistory.getFileName();
						File pdfFileObj = new File(pdfFilePath);
						if (pdfFileObj.exists() && pdfFilePath.endsWith(".pdf"))
						{
							try
							{
								if (PDFUtilities.searchInPDF(pdfFilePath, keyword))
								{
									DTOContentSearch dtoContentSearch = new DTOContentSearch();
									dtoContentSearch.setWorkspaceid(dtoWorkSpaceNodeHistory.getWorkSpaceId());
									dtoContentSearch.setWorkspaceDesc(dtoWorkSpaceNodeHistory.getWorkSpaceDesc());
									dtoContentSearch.setNodeId(dtoWorkSpaceNodeHistory.getNodeId());
									dtoContentSearch.setNodeName(dtoWorkSpaceNodeHistory.getNodeName());
									dtoContentSearch.setPdfFileName(dtoWorkSpaceNodeHistory.getFileName());
									dtoContentSearch.setPdfFilePath(pdfFilePath);
									contentData.add(dtoContentSearch);
								}
							}
							catch(Throwable e)
							{
								System.out.println("Error Found in File::::::"+pdfFilePath);
								e.printStackTrace();
							}
						}
					}
				}
				return returnType;
			}
			else if(output == 'P')//Search on Project
			{
					Mode = 1;
			}else if (output == 'N')//Search on Nodes 
			{
					Mode = 2;
			}
			else if(output == 'C' && searchBy == null)//Search on Comments
			{
					Mode = 3;
			}
			else if(output == 'A')//Search on Attribute name and value
			{
					Mode = 4;
			}
				
			if (searchBy == null) 
			{
				contentData = DocMgmtImpl.contentSearch(userId, keyword, "Like", Mode);
			}
			else if(searchBy.equals("match"))
			{
				contentData = DocMgmtImpl.contentSearch(userId, keyword, "Equal", Mode);
			}
	}
		return returnType;
	}
	public ArrayList<DTOContentSearch> contentData = new ArrayList<DTOContentSearch>();
	public ArrayList<DTOContentSearch> getContentData() {
		return contentData;
	}
	public void setContentData(ArrayList<DTOContentSearch> contentData) {
		this.contentData = contentData;
	}
	public ArrayList<DTOHotsearchDetail> fullSearchData = new ArrayList<DTOHotsearchDetail>();
	
	public ArrayList<DTOHotsearchDetail> getFullSearchData() {
		return fullSearchData;
	}
	public void setFullSearchData(ArrayList<DTOHotsearchDetail> fullSearchData) {
		this.fullSearchData = fullSearchData;
	}

	public char selectdate;
	public char search;
	public char searchFor;//Search For content or keyword
	public String firstDate;
	public String secondDate;
	public String thirdDate;
	public String keyword;
	public String selectedAttributes;
	public char output;
	public String searchBy;// Like or Equal in Content search
}

