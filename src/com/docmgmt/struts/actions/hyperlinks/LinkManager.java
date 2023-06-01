package com.docmgmt.struts.actions.hyperlinks;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Vector;

import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;
import org.pdfbox.pdmodel.common.PDRectangle;
import org.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.pdfbox.pdmodel.common.filespecification.PDFileSpecification;
import org.pdfbox.pdmodel.interactive.action.type.PDAction;
import org.pdfbox.pdmodel.interactive.action.type.PDActionLaunch;
import org.pdfbox.pdmodel.interactive.action.type.PDActionURI;
import org.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeAttribute;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;


public class LinkManager {
	private static final long serialVersionUID = 1L;
	public static final int FIND_FIRST_LINK = 0;
	public static final int FIND_ALL_LINKS = 1;
	public static final int FIND_FIRST_BROKEN_LINK = 2;
	public static final int FIND_ALL_BROKEN_LINKS = 3;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	private Vector filesContainingBrokenLinks;
	private boolean attrValueNotFound = false;
	
	public boolean isAttrValueNotFound() {
		return attrValueNotFound;
	}
	
	public Vector getFilesContainingLinks(Vector allFiles) {
		String workspaceFolder = null;
		DTOWorkSpaceMst dtows = null;
		Vector filesContainingLinks = new Vector();
		for(int i = 0; i < allFiles.size(); i++){
			DTOWorkSpaceNodeHistory dto = (DTOWorkSpaceNodeHistory)allFiles.get(i);
			String path = dto.getFolderName();
			String fileName = dto.getFileName();
			if(workspaceFolder == null)
			{
				String[] temp = path.split("/");
				System.out.println(temp[1]);
				
				dtows = docMgmtImpl.getWorkSpaceDetail(temp[1]);
				workspaceFolder = dtows.getBaseWorkFolder();
				
				workspaceFolder=workspaceFolder.replace("\\" , "/");
				while(true)
				{
					if(workspaceFolder.contains("//"))
					{
						workspaceFolder=workspaceFolder.replace("//" , "/");
					}
					else
						break;
				}
				
				workspaceFolder="/"+workspaceFolder;
			}
			path =workspaceFolder + path +"/" + fileName;
			
			if(getLinks(path, FIND_FIRST_LINK).size() > 0)
			{
				dto.setFolderName(path);
				filesContainingLinks.addElement(dto);
			}
		}
		
		
		return filesContainingLinks;
		
	} // getFilesContainingLinks end
	
	
	
	/*public String isContainingLinks(String filewithpath) {
		System.out.println("path:::"+filewithpath);
			
		File file = new File(filewithpath);
		System.out.println(file.getAbsolutePath());
		
		
		PDDocument doc = null;
		if(file != null && file.exists())
		{
			try {
				System.out.println(file.getAbsolutePath());
				 PDFParser parser = new PDFParser(new FileInputStream(file));
			        parser.parse();
			        
			        doc = parser.getPDDocument();
			        
			        
	               
			        List pages = null;
			            pages = doc.getDocumentCatalog().getAllPages();
			            
			        if (!(pages == null || pages.isEmpty()))
			        {
			        	
				        Vector foundLinks = new Vector();
				        for (int i = 0 ; i<pages.size(); i++) {
				            PDPage page = (PDPage) pages.get(i);
				            List annotations = page.getAnnotations();
				            
				            
				            for (int j = 0 ; j<annotations.size(); j++) {
				                PDAnnotation annot = (PDAnnotation) annotations.get(j);
				              
				                
			                    if (annot instanceof PDAnnotationLink) {
				                    PDAnnotationLink link = (PDAnnotationLink) annot;
				                    PDAction action = link.getAction();
				  
				                    if (action instanceof PDActionURI) {
				                       
				                    	return file.getAbsolutePath();
				                    }
				                   
				                    if (action instanceof PDActionLaunch) {
				                    	
				                    	return file.getAbsolutePath();
				                    }
				                   
				                }
			                    
			                    
				            }
				       
				        }
				       
			        }
			        
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally
			{
				try {
					doc.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	
		return null;
	}*/
	
	public Vector getLinks(String fileWithPath,int mode) {
		
		File f = new File(fileWithPath);
		return getLinks(f,mode);
	}
	
	public Vector getLinks(File f,int mode) {
		//System.out.println(f.getAbsolutePath());
		Vector foundLinks = new Vector();
		
		try {
			 PDFParser parser = new PDFParser(new FileInputStream(f));
		        parser.parse();
		        
		        PDDocument doc = parser.getPDDocument();
		        List pages = null;
		            pages = doc.getDocumentCatalog().getAllPages();
		            
		        if (!(pages == null || pages.isEmpty()))
		        {
		        	
			        
			        for (int i = 0 ; i<pages.size(); i++) {
			            PDPage page = (PDPage) pages.get(i);
			            List annotations = page.getAnnotations();
			            
			            Vector pageLinks = new Vector();
			            
			            String pageno = "" + (i+1);
			            
			            for (int j = 0 ; j<annotations.size(); j++) {
			               PDAnnotation annot = (PDAnnotation) annotations.get(j);
			               if (annot instanceof PDAnnotationLink) {
			                    PDAnnotationLink link = (PDAnnotationLink) annot;
			                    PDAction action = link.getAction();
			                    PDRectangle rect = link.getRectangle();
	                            //need to reposition link rectangle to match text space
	                            float x = rect.getLowerLeftX();
	                            float y = rect.getUpperRightY();
	                            float width = rect.getWidth();
	                            float height = rect.getHeight();	
	                            
	                            
	                            int rotation = page.findRotation();
	                            if( rotation == 0 )
	                            {
	                                PDRectangle pageSize = page.findMediaBox();
	                                y = pageSize.getHeight() - y;
	                            }
	                            else if( rotation == 90 )
	                            {
	                                //do nothing
	                            }
	                            
	                            //System.out.println("x::"+x+"y::"+y+"width::"+width+"height::"+height);
	                            
			                    if(action instanceof PDActionLaunch || action instanceof PDActionURI)
			                    {
			                    	String[] linkdetail = new String[5];
				                   
				                    if (action instanceof PDActionLaunch) {
				                    	final PDActionLaunch launch = (PDActionLaunch) action;
					                    PDFileSpecification file = launch.getFile();
					                    String strfile = file.getFile();
				                        //System.out.println("Link Reference File->"+strfile);
				                        linkdetail[1] = strfile;   //destination file path
				                        
				                    }
				                    else
				                    {
				                        final PDActionURI uri = (PDActionURI) action;
				                        final String strURI = uri.getURI();
				                       
				                        //System.out.println("Link Reference File->"+strURI);
				                        linkdetail[1] = strURI;   //destination file path
				                    }
				                    
				                    linkdetail[0] = pageno;       //page no
				                    linkdetail[2] = "" + x;       //x co-ordinate
			                        linkdetail[3] = "" + y;       //y co-ordinate
			                        linkdetail[4] = "" + (j+1);   //original sequence no of link
				                    
			                        pageLinks.addElement(linkdetail);
				                    
			                        if(mode == FIND_FIRST_LINK)
			                        {
			                        	doc.close();
			                        	return pageLinks;
			                        }
				                    
			                    }
			                   
			                }
			            }
			            //System.out.println("pagelinks size :::"+pageLinks.size());
			            
			            //int pageLinksSize = pageLinks.size();
			            for(int k = 0; k < pageLinks.size(); k++)
			            {
			            	
			            	String[] strK = (String[])pageLinks.get(k);
			            	float xK = Float.parseFloat(strK[2]);
			            	float yK = Float.parseFloat(strK[3]);
			            	//System.out.println("loop k :::"+k+":::xK->"+xK+":::yK->"+yK);
			            	for(int l = k+1; l < pageLinks.size(); l++)
			            	{
			            		
			            			String[] strL = (String[])pageLinks.get(l);
			            			float xL = Float.parseFloat(strL[2]);
					            	float yL = Float.parseFloat(strL[3]);
					            	//System.out.println("loop l :::"+l+":::xL->"+xL+":::yL->"+yL);
					            	if(yK > yL) 
					            	{
					            		pageLinks.setElementAt(strL, k);  // swapping of elements
					            		pageLinks.setElementAt(strK, l);  //
					            		strK = strL;
					            		xK = xL;
					            		yK = yL;
					            	}
					            	else if(yK == yL)
					            	{
					            		if(xK > xL)
						            	{
						            		pageLinks.setElementAt(strL, k); // swapping of elements
						            		pageLinks.setElementAt(strK, l); //
						            		strK = strL;
						            		xK = xL;
						            	}
					            		
					            	}
			            		
			            	}
			            }
			            for(int a= 0; a<pageLinks.size(); a++)
			            {
			            	String[] b = (String[])pageLinks.get(a);
			            	//System.out.println(":::"+b[2]+":::"+b[3]+":::"+b[1]);
			            }
			            
			            //System.out.println();
			            for(int m = 0; m < pageLinks.size(); m++)
			            {
			            	String[] strM = (String[])pageLinks.get(m);
			            	float xM = Float.parseFloat(strM[2]);
			            	float yM = Float.parseFloat(strM[3]);
			            	
				            if(m != pageLinks.size()-1)
				            {
			            		String[] strM1 = (String[])pageLinks.get(m+1);
				            	float xM1 = Float.parseFloat(strM1[2]);
				            	float yM1 = Float.parseFloat(strM1[3]);
				            	
				            	if((xM == xM1) && (yM == yM1))
				            	{
				            		pageLinks.removeElementAt(m);
				            	}
				            }
			            	
			            	String[] temp = (String[])pageLinks.get(m);
			            	/*for(int f1 = 0; f1 < temp.length; f1++)
			            	{
			            		System.out.print(":::"+temp[f1]);
			            	}*/
			            	//System.out.println();
			            	foundLinks.addElement(pageLinks.get(m));
			            }
			        }
			       // System.out.println("foundLinks.size:::"+foundLinks.size());
			        
		        }
		        doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return foundLinks;
		
	} // getLinks end
	
	public boolean setLink(String fileWithPath,String pageAndLink,String destPath) {
		System.out.println(fileWithPath);
		
		String newfile = null;
		
		String[] data = pageAndLink.split("&&");
		int setpageno = Integer.parseInt(data[0]);
		int setlink = Integer.parseInt(data[1]);
		try {
			 PDFParser parser = new PDFParser(new FileInputStream(new File(fileWithPath)));
		        parser.parse();
		        PDDocument doc = parser.getPDDocument();
		        List pages = null;
		            pages = doc.getDocumentCatalog().getAllPages();
		        if (!(pages == null || pages.isEmpty()))
		        {
			        for (int i = 0 ; i<pages.size(); i++) 
			        {
			        	if(i == setpageno-1)
			        	{
			        		//System.out.println("Page ::: "+(i+1));
			        		PDPage page = (PDPage) pages.get(i);
				            List annotations = page.getAnnotations();
				            Vector pageLinks = new Vector();
				            String pageno = "" + (i+1);
				            
				            for (int j = 0 ; j<annotations.size(); j++)
				            {
				            	if(j == setlink-1)
				            	{
				            	//	System.out.println("Link ::: "+(j+1));
				            		PDAnnotation annot = (PDAnnotation) annotations.get(j);
				            		if (annot instanceof PDAnnotationLink) 
				            		{
				            			PDAnnotationLink link = (PDAnnotationLink) annot;
				            			PDAction action = link.getAction();
					                  
				            			if(action instanceof PDActionLaunch || action instanceof PDActionURI)
				            			{
				            				if (action instanceof PDActionLaunch)
				            				{
				            					final PDActionLaunch launch = (PDActionLaunch) action;
				            					PDComplexFileSpecification file = (PDComplexFileSpecification)launch.getFile();
				            					String strfile = file.getFile();
				            					// System.out.println("File->"+strfile);
						                      
							               //     System.out.println("destPath:::"+destPath);
				            					file.setFile(destPath);
				            					launch.setFile(file);
						                      
							                        
				            				}
				            				if (action instanceof PDActionURI)
				            				{
				            					final PDActionURI uri = (PDActionURI) action;
				            					final String strURI = uri.getURI();
				            					uri.setURI(destPath);
							                     
				            				}
							            }
						            }
						        }
				            }
				        }
			        }
			        int ind = fileWithPath.lastIndexOf("/");
			        String filename = fileWithPath.substring(ind+1);
			        filename = "__"+filename;
			        String filepath = fileWithPath.substring(0, ind+1);
			        
			       // System.out.println(filepath+filename);
			        newfile = filepath+filename;
			        doc.save(newfile);
		        }
		        
		        doc.close();
		        
		        File file = new File(fileWithPath);
		        file.delete();
		        
		        File nfile = new File(newfile);
		        nfile.renameTo(new File(fileWithPath));
		        
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	} // setLink end
	
	public String getVirtualPublishFilePath(String workspaceID,int nodeId) {
		
		Vector getFolderNames = new Vector();
		//int nodeId1= new Integer(nodeId).intValue();
		int nodeId1=nodeId;
		while(nodeId1>1)
		{
			//System.out.println("nodeId1:::"+nodeId1);
			Vector parentNodeId = new Vector();
			parentNodeId= docMgmtImpl.getNodeDetail(workspaceID,nodeId1);
		//	System.out.println("size:::"+parentNodeId.size());
			DTOWorkSpaceNodeDetail dto = (DTOWorkSpaceNodeDetail)parentNodeId.get(0);
			int newParentNodeId = dto.getParentNodeId();
		
							/* With Attribute Value */	
			Vector nodeAttribute = docMgmtImpl.getNodeAttributes(workspaceID,nodeId1);
	    	String MergeAttributeStr = "";
	       	if(nodeAttribute.size()>0) 
	       	{
	   			for(int k=0;k<nodeAttribute.size();k++) 
	   			{
	   				DTOWorkSpaceNodeAttribute obj = (DTOWorkSpaceNodeAttribute)nodeAttribute.get(k);
	   				String attrvalue = obj.getAttrValue();
	   			
	   				if(attrvalue == null ||attrvalue.equals(""))
	   				{
	   					attrValueNotFound = true;
	   					
	   					return "node="+dto.getNodeName()+"&&attrname="+obj.getAttrName();
	   				}
	   				if(attrvalue.length() > 4){
	   					attrvalue=attrvalue.trim().substring(0, 4);
	   				}
	   				//System.out.println("attrvalue-"+attrvalue);
	   				
	   			//  for displaying attrvalue as "-" where there is a space
	   				attrvalue=attrvalue.replaceAll(" ", "-");
	   				attrvalue=attrvalue.replaceAll(",", "-");
	   			//	attrvalue=attrvalue.replaceAll(".", "-");
	   				attrvalue=attrvalue.replaceAll(":", "-");
	   		//		System.out.println("attrvalue1-"+attrvalue);
	   				if(k==0){
	   					MergeAttributeStr = attrvalue.trim();
	   				}else{
	   					MergeAttributeStr = MergeAttributeStr +"-"+ attrvalue.trim();
	   				}
	   				
	   			}
	   			dto.setFolderName(dto.getFolderName() +"/"+MergeAttributeStr);
	       	}
	       	if(dto.getNodeTypeIndi()=='D' && dto.getRequiredFlag()=='N' && dto.getCloneFlag()=='Y')
	       	{
	       		
	       	}
	       	else
	       		    getFolderNames.addElement(dto.getFolderName());
 			nodeId1 =newParentNodeId;   
		}
	//	System.out.println("size of Vector:::::"+getFolderNames.size());
		
		
		Vector filename = docMgmtImpl.getFileNameForNode(nodeId, workspaceID);
		//System.out.println("filenamesize:::"+filename.size());
		DTOWorkSpaceNodeHistory dto = (DTOWorkSpaceNodeHistory)filename.get(0);
		getFolderNames.setElementAt(dto.getFileName(),0);
		StringBuffer sb = new StringBuffer();
		for(int j=getFolderNames.size()-1;j>=0;j--)
		{
			sb.append(getFolderNames.get(j)).toString();
			if(j>0)
			{
				sb.append("/");
			}
		}
		
		return sb.toString();
	}

	public Vector searchFilesContainingBrokenLinks(String sequenceNoFolderPath) {
		
		filesContainingBrokenLinks = new Vector();
		
		try {
				System.out.println("fffff");
				File sequenceNoFolder = new File(sequenceNoFolderPath);
				//System.out.println(sequenceNoFolder.getAbsolutePath());
				
				searchSubDirectoriesAndFilesForHyperlinks(sequenceNoFolder);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return filesContainingBrokenLinks;
	}
	
	private void searchSubDirectoriesAndFilesForHyperlinks(File parentDirectory)throws Exception {
		
		File[] children =  parentDirectory.listFiles();
		
		for(int i = 0; i < children.length; i++)
		{
			if(children[i].isDirectory())
			{
				//System.out.println(children[i].getAbsolutePath());
				searchSubDirectoriesAndFilesForHyperlinks(children[i]);
			}
			else
			{
				//System.out.println(children[i].getName());
				
				if(children[i].getName().endsWith(".pdf"))
				{
					Vector brokenLinks = findBrokenLinksInFile(children[i],FIND_FIRST_BROKEN_LINK);
					
					if(brokenLinks.size() > 0)
					{
						
						filesContainingBrokenLinks.addElement(children[i].getAbsolutePath());
					}
				}
				
			}
			
		}// for end
		
	}
	
	public Vector findBrokenLinksInFile(String filewithpath,int mode) {
		
		File file = new File(filewithpath);
		return findBrokenLinksInFile(file,mode);
	}
	
	public Vector findBrokenLinksInFile(File file,int mode) {
		
		Vector brokenLinks = new Vector();
		
		Vector fileLinks = getLinks(file,FIND_ALL_LINKS);
		
		for(int k =0; k < fileLinks.size(); k++)
		{
			String[] link = (String[])fileLinks.get(k);
			StringBuffer linkReferencePath = new StringBuffer(link[1]);
			
			//int depth = 0;
			File refFileParent = file.getParentFile();
			boolean isFileReference = false;
			while(true)// find the base directory for the reference file path in the link
			{
				if(linkReferencePath.substring(0,3).equals("../"))
				{
					//depth++;
					isFileReference = true;
					
					refFileParent = refFileParent.getParentFile();
					linkReferencePath = linkReferencePath.delete(0,3);
				
					//System.out.println(depth);
					//System.out.println(linkReferencePath);
				}
				else
				break;
			
			}
			if(!isFileReference) // to check whether it is a web reference...
			{
				StringBuffer temp = new StringBuffer(linkReferencePath);
				
				
				//System.out.println("temp::::::"+temp);
				if(!temp.toString().contains("http:") && !temp.toString().contains("www.") && !temp.toString().contains(".com")
						&& !temp.toString().contains(".gov") && !temp.toString().contains(".net") && !temp.toString().contains(".org"))
				{
					
					temp = temp.delete(temp.lastIndexOf("."), temp.length());
					if(temp.indexOf(".") == -1)
					{
						isFileReference = true;
					}
				}
			}
			//refFileParent = findParentDirectoryForReferencePath(file.getParentFile(), depth);
			//System.out.println("Reference Path ::: "+refFileParent.getAbsolutePath()+" ::: "+linkReferencePath.toString());
			if(isFileReference)
			{
				File refrenceFile = new File(refFileParent.getAbsolutePath()+"/"+linkReferencePath.toString());
				
				if(!refrenceFile.exists())
				{
					//System.out.println("File Does Not Exists ::: "+refrenceFile.getAbsolutePath());
					brokenLinks.addElement(link);
					
					if(mode == FIND_FIRST_BROKEN_LINK)
					{
						return brokenLinks;
					}
					
				}
			}
		}// for end	
		return brokenLinks;
	}



	
	/*public File findParentDirectoryForReferencePath(File directory,int depth) {
		
		if(depth > 0)
		{
			File newParent = directory.getParentFile();
			return findParentDirectoryForReferencePath(newParent, depth-1);
		}
		else if (depth == 0)
		{
			return directory;
		}
		
		return null;
	}
	
	*/
	
	public static void main(String args[])
	{
		//LinkManager linkManager = new LinkManager();
		/*linkManager.searchFilesContainingBrokenLinks("/home/untitled folder 3");
		System.out.println(linkManager.filesContainingBrokenLinks.size());
		for (int i=0;i<linkManager.filesContainingBrokenLinks.size();i++)
		{
			System.out.println(linkManager.filesContainingBrokenLinks.get(i));
			Vector v = linkManager.findBrokenLinksInFile((String)linkManager.filesContainingBrokenLinks.get(i),LinkManager.FIND_ALL_BROKEN_LINKS);		
			System.out.println("v.size()" + v.size());
			for (int j=0;j<v.size();j++)
			{
				System.out.println("((String[])v.get(j)).length" + ((String[])v.get(j)).length);
				System.out.println("\t" + ((String[])v.get(j))[0]);
				System.out.println("\t" + ((String[])v.get(j))[1]);
				System.out.println("\t" + ((String[])v.get(j))[4]);
			}
		}*/
		
	/*	Vector v = linkManager.getLinks(new File("/home/untitled folder 3/Test hyperlinks.pdf"), linkManager.FIND_ALL_LINKS);
		System.out.println("v.size()" + v.size());
		for (int j=0;j<v.size();j++)
		{
			System.out.println("((String[])v.get(j)).length" + ((String[])v.get(j)).length);
			System.out.println("\t" + ((String[])v.get(j))[0]);
			System.out.println("\t" + ((String[])v.get(j))[1]);
			System.out.println("\t" + ((String[])v.get(j))[4]);
		}*/
		System.out.println("Paracetamol 1.5 mg".substring(0, 4));
	}
}// class end
