package com.docmgmt.test;

import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class FileDragDemo extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;

	@Override
	@SuppressWarnings("unchecked")
	public String execute() {
		System.out.println("File Found");

		request = ServletActionContext.getRequest();
		try {
			List<FileItem> items = new ServletFileUpload(
					new DiskFileItemFactory()).parseRequest(request);
			
			System.out.println("Size="+items.size());
			for (FileItem item : items) {
				if (item.getFieldName().equals("file")) {
					String filename = FilenameUtils.getName(item.getName());
					InputStream content = item.getInputStream();
					// ... Do your job here.

//					response.setContentType("text/plain");
//					response.setCharacterEncoding("UTF-8");
					System.out.println(filename);
							
					
				}
			}
		} catch (Exception e) {
			try {
				throw new ServletException("Parsing file upload failed.", e);
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return SUCCESS;
	}

}
