package com.docmgmt.struts.actions.workspace.UpcomingSignOffReport;

import java.util.ArrayList;
import java.util.HashMap;

import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

	public class UpcomingSignOffReportAction extends ActionSupport {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public ArrayList<HashMap<String, Object>> subqueryMsg = new ArrayList<HashMap<String, Object>>();
		public DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		public ArrayList<DTOWorkSpaceNodeHistory>  getUpcomingFileReport;
		public int documentCount=0;
		public String htmlContent;
		public String lbl_folderName;
		public String lbl_nodeName;
		KnetProperties knetProperties = KnetProperties.getPropInfo();

		@Override
		public String execute() {

			int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());				
			lbl_folderName = knetProperties.getValue("ForlderName");
			lbl_nodeName = knetProperties.getValue("NodeName");		
			getUpcomingFileReport = docMgmtImpl.getUpcomingFileReport(userCode,userGroupCode);
			
			return SUCCESS;
		}
		
		public String documentCount(){
			int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
			int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
			getUpcomingFileReport=docMgmtImpl.getUpcomingFileReport(userCode,userGroupCode);
			
			if(getUpcomingFileReport.size()>0){
				documentCount = getUpcomingFileReport.size();
			}
			htmlContent=Integer.valueOf(documentCount).toString();
			//return Integer.valueOf(commentCount).toString();
			return "html";
		}

	}

