package com.docmgmt.struts.actions.hyperlinks;

import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class ShowLinksAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	//private LinkFinder lf;
	
	@Override
	public String execute()
	{
		LinkManager linkmanager = new LinkManager();
		
		if(!getBrokenLinks)
		{
			links = linkmanager.getLinks(fileWithPath,LinkManager.FIND_ALL_LINKS);
		}
		else
		{
			links = linkmanager.findBrokenLinksInFile(fileWithPath,LinkManager.FIND_ALL_BROKEN_LINKS);
		}
			
		for(int i = 0; i < links.size(); i++)
		{
			String[] link = (String[])links.get(i);
			String destinationFilePath =link[1];
			
			while(true)
			{
				if(destinationFilePath.startsWith("../"))
				{
					destinationFilePath = destinationFilePath.substring(3);
				}
				else
					break;
			}
			
			link[1] = destinationFilePath;
			links.setElementAt(link, i);
		}
		return SUCCESS;
	}
	
	/*public String executeBrokenLinks()
	{
		lf = new LinkFinder();
		links = lf.findBrokenLinksInFile(fileWithPath,LinkFinder.FIND_ALL_BROKEN_LINKS);
		return SUCCESS;
	}*/

	
	public String fileWithPath;
	public Vector links;
	public boolean getBrokenLinks = false;

	public String getFileWithPath() {
		return fileWithPath;
	}


	public void setFileWithPath(String fileWithPath) {
		this.fileWithPath = fileWithPath;
	}


	public Vector getLinks() {
		return links;
	}


	public void setLinks(Vector links) {
		this.links = links;
	}
}
