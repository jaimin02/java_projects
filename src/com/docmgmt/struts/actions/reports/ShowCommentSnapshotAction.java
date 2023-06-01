package com.docmgmt.struts.actions.reports;

import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.CommentSnapShotBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowCommentSnapshotAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	public String workSpaceId;
	public String htmlContent="";
	public boolean isTableView;
	public int nodeId;
	public int attrId;
	public int mode=0;
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	@Override
	public String execute(){
		
		if(workSpaceId != null && !workSpaceId.equals("") && !workSpaceId.equals("-1")){
			isTableView=true;
			int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
			int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			short reportType = CommentSnapShotBean.TABLE_HTML;
			htmlContent ="<div style=\"width:100%;\">"+
				 			 "<div style=\" float:left\" align=\"left\">" +
					 			 "<img src=\"images/jquery_tree_img/exp.jpg\" title=\"Show All Node's Attributes\" onclick=\"showHideAllAttributesTable('show');\">  " +
					 			 "<img src=\"images/jquery_tree_img/colaps.jpg\" title=\"Hide All Node's Attributes\" onclick=\"showHideAllAttributesTable('hide');\">" +
				 			 "</div>"+
				 			 "<div style=\"float:right\" align=\"right\">" +
				 			 	"<img alt=\"Table View\" title=\"Table View\" src=\"images/Common/Table.png\" onclick=\"getProjectSnapshot('TableView');\">" +
				 			 "</div>"+
			 			 "</div><br/><br/>";
			if(isTableView){
				reportType = CommentSnapShotBean.TABLE_HTML;
				
				htmlContent = 
						"<div style=\"width:100%; \" align=\"right\"> " +
							"<form id=\"myform\" action=\"ExportToXls.do\" method=\"post\" >"+
								"<input type=\"hidden\" name=\"fileName\" value=\"Comment_Snapshot_Report.xls\">"+
								"<textarea rows=\"1\"  cols=\"1\" name=\"tabText\" id=\"tableTextArea\" style=\"visibility: hidden;height: 10px;\"></textarea>"+
								"<img alt=\"Print\" title=\"Print\" src=\"images/Common/Print.png\" onclick=\"printPage();\">&nbsp;"+
								"<img alt=\"Export To Excel\" title = \"Export To Excel\" src=\"images/Common/Excel.png\" onclick=\"document.getElementById('tableTextArea').value=document.getElementById('divTabText').innerHTML;document.getElementById('myform').submit()\">&nbsp;"+
							"</form>" +
						"</div>";						
			}
			
			CommentSnapShotBean commentSnapShotBean = new CommentSnapShotBean();
			htmlContent += "<div id=\"divTabText\" style=\"width:100%\">";
			String htmlContentTemp = commentSnapShotBean.getWorkspaceHtml(workSpaceId, userGroupCode, userCode,reportType,mode);
			
			if(htmlContentTemp== null || htmlContentTemp.equals("")){
				htmlContentTemp = "No Details Found";
			}
			htmlContent += htmlContentTemp;
			htmlContent += "</div>";
		}
		
		
		return SUCCESS;
	}
	

}
