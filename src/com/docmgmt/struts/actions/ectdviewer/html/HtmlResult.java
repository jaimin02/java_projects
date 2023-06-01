package com.docmgmt.struts.actions.ectdviewer.html;

import java.io.ByteArrayInputStream;

import org.apache.struts2.dispatcher.StreamResult;

import com.opensymphony.xwork2.ActionInvocation;

public class HtmlResult extends StreamResult{
	
	static final long serialVersionUID = -12345L; 
	String htmlContent;

	public HtmlResult() {
		super();
		
	}
	
	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
		super.setContentType("text/html");
		
		String inputStringMsg = (String) invocation.getStack().findValue(conditionalParse(htmlContent, invocation));
		if(inputStringMsg == null){
			inputStringMsg = "No Message Found";
		}
		inputStream = new ByteArrayInputStream((inputStringMsg).getBytes());

		super.doExecute(finalLocation, invocation);
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
}
