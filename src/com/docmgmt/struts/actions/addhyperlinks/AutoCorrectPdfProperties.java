package com.docmgmt.struts.actions.addhyperlinks;

import com.docmgmt.server.webinterface.services.pdf.PdfPropUtilities;
import com.opensymphony.xwork2.ActionSupport;

public class AutoCorrectPdfProperties extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;
	public String htmlContent;

	public String getPath() {

		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String execute() throws Exception {

		PdfPropUtilities util = new PdfPropUtilities();
		try {
			path = path.replaceAll("\\\\", "/");
			
			System.out.println(path);
			path=path.replace("file:", "");
		} catch (Exception e) {

			e.printStackTrace();
		}
		util.autoCorrectPdfProp(path);
		System.out.println("Selected Pdf file path is::" + path);
		htmlContent = "";

		return SUCCESS;
	}
}
