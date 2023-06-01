
package com.docmgmt.struts.actions.reports;

import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.ProjectUserBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

	public class ShowProjectUserAction extends ActionSupport {

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
				
				int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
				int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
				short reportType = ProjectUserBean.TREE_HTML;
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
					reportType = ProjectUserBean.TABLE_HTML;
					htmlContent = 
							"<div style=\"width:100%; \" align=\"right\"> " +
								"<form id=\"myform\" action=\"ExportToXls.do\" method=\"post\" >"+
									"<input type=\"hidden\" name=\"fileName\" value=\"User_on_Project_report.xls\">"+
									"<textarea rows=\"1\"  cols=\"1\" name=\"tabText\" id=\"tableTextArea\" style=\"visibility: hidden;height: 10px;\"></textarea>"+
									"<img alt=\"Export To Excel\" title = \"Export To Excel\" src=\"images/Common/Excel.png\" onclick=\"getTimeInfo();document.getElementById('tableTextArea').value=document.getElementById('divTabText').innerHTML;document.getElementById('myform').submit()\">&nbsp;"+
									"<img alt=\"Tree View\" title=\"Tree View\" src=\"images/Common/tree_view.png\" onclick=\"getProjectSnapshot('TreeView');\">"+
								"</form>" +
							"</div>";						
				}
				
				ProjectUserBean ProjectUserBean = new ProjectUserBean();
				htmlContent += "<div id=\"divTabText\" style=\"width:100%\">";
				String htmlContentTemp = ProjectUserBean.getWorkspaceHtml(workSpaceId, userGroupCode, userCode,reportType,mode);
				
				if(htmlContentTemp== null || htmlContentTemp.equals("")){
					htmlContentTemp = "No Details Found";
				}
				htmlContent += htmlContentTemp;
				htmlContent += "</div>";
			}
			
			
			return SUCCESS;
		}
		
		public String ShowAttrHistory() {
			
			ProjectUserBean ProjectUserBean = new ProjectUserBean();
			htmlContent = ProjectUserBean.getAttrHistoryTable(workSpaceId, nodeId, attrId);
			
			return SUCCESS;
		}
		
		public String ShowNodeReamrk() {
			
			ProjectUserBean ProjectUserBean = new ProjectUserBean();
			htmlContent = ProjectUserBean.getNodeReamrkTable(workSpaceId, nodeId);
			
			return SUCCESS;
		}
	}

