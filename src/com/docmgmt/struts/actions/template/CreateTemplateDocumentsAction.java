package com.docmgmt.struts.actions.template;

import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class CreateTemplateDocumentsAction extends ActionSupport implements Preparable {
		
		private static final long serialVersionUID = 1L;

		private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
		
		
		@Override
		public String execute()
		{

			return SUCCESS;
		}	
		
		public void prepare() throws Exception {
			
			templateDetail=docMgmtImpl.getAllTemplatesForDocuments();
		}

	    public Vector templateDetail;
	    public String templateName;


		public Vector getTemplateDetail() {
			return templateDetail;
		}

		public void setTemplateDetail(Vector templateDetail) {
			this.templateDetail = templateDetail;
		}

		public String getTemplateName() {
			return templateName;
		}

		public void setTemplateName(String templateName) {
			this.templateName = templateName;
		}

	}
