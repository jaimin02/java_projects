package com.docmgmt.server.webinterface.tld;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import com.docmgmt.server.prop.KnetProperties;

public class KnetTitle extends HttpServlet implements Tag {
	private PageContext pagecontext;
	private Tag parent;
	
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		 try{
			HttpSession session = pagecontext.getSession();
			String param = (String) session.getAttribute("ApplicationType");
		        if (param != null)
		        {
		        	 if (param.equals("eCTD"))
					 {
						 pagecontext.getOut().write("KnowledgeNET - eCTD Submission & Life Cycle Management");
					 }
					 else if (param.equals("DMS"))
					 {
						 pagecontext.getOut().write("KnowledgeNET - Document Management System");
					 }
					 else if (param.equals("eTMF"))
					 {
						 pagecontext.getOut().write("KnowledgeNET - eTMF Submission & Life Cycle Management");
					 }
					 else if (param.equals("DocStack"))
					 {
						 pagecontext.getOut().write("DoQStack Document & Life Cycle Management");
					 }
					 else//default
					 {
						 pagecontext.getOut().write("DoQStack Document & Life Cycle Management");
						 
					 }
		        }
		        else 
		        {
		        	KnetProperties knetProperties = KnetProperties.getPropInfo();
		    		String appType = knetProperties.getValue("Application");
		            session.setAttribute("ApplicationType", appType);
		            if (appType.equals("eCTD"))
					 {
						 pagecontext.getOut().write("KnowledgeNET - eCTD Submission & Life Cycle Management");
						
					 }
					 else if (appType.equals("DMS"))
					 {
						 pagecontext.getOut().write("KnowledgeNET - Document Management System");
					 }
					 else if (appType.equals("eTMF"))
					 {
						 pagecontext.getOut().write("KnowledgeNET - eTMF Submission & Life Cycle Management");
					 }
					 else if (appType.equals("DocStack"))
					 {
						 pagecontext.getOut().write("DoQStack Document & Life Cycle Management");
					 }
					 else//default
					 {
						 pagecontext.getOut().write("KnowledgeNET");
					 }
		        }
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 System.out.println("Error :- " + e);
		}
		return 0;
	}

	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Tag getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	public void release() {
		// TODO Auto-generated method stub
	}

	public void setPageContext(PageContext p) {
		// TODO Auto-generated method stub
		pagecontext = p;
	}

	public void setParent(Tag t) {
		// TODO Auto-generated method stub
		parent = t;
	}
} 