package com.docmgmt.struts.actions.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.docmgmt.dto.DTOTemplateMst;
import com.docmgmt.dto.DTOTemplateNodeAttrDetail;
import com.docmgmt.dto.DTOTemplateNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;


public class GenerateStructureXML extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		
		if(buttonName.equals("Export"))
		{
			Vector TempalateDesc =docMgmtImpl.getTemplateDetailById(templateId);
			DTOTemplateMst TemplateDesc=(DTOTemplateMst)TempalateDesc.get(0);
			int maxnodeid=docMgmtImpl.getmaxTemplateNodeId(templateId);
			generateXmlFile(TemplateDesc,maxnodeid);
			return "OpenXML";
		}
		else
		{
			String msg = UseXmlFile();
			addActionMessage(msg);
			//System.out.println("msg::"+msg);
			return SUCCESS;
		}
		
	}
	
	
	public void generateXmlFile(DTOTemplateMst TemplateDesc,int maxnodeid)
	{
		
		Document dom;
		
		//get an instance of factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
		//get an instance of builder
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		
		
		//create an instance of DOM
		dom = db.newDocument();
		
		//create the root element 
		
		
		Element rootEle = dom.createElement(TemplateDesc.getTemplateDesc().replace(" ", ""));
		
		dom.appendChild(rootEle);

		Vector NodeInfo=docMgmtImpl.getTemplateForTreeDisplay(templateId);
		
		
		
		for(int i=0;i<NodeInfo.size();i++)
		{
			Object [] record =(Object [])NodeInfo.get(i);
			
			Element NodeEle = dom.createElement("NODE");
			NodeEle.setAttribute("NodeId",record[0].toString());
			NodeEle.setAttribute("DisplayName",record[1].toString());
			NodeEle.setAttribute("ParentId",record[2].toString());
			NodeEle.setAttribute("NodeNo",record[3].toString());
			NodeEle.setAttribute("NodeName",record[4].toString());
			NodeEle.setAttribute("FolderName",record[5].toString());
			NodeEle.setAttribute("NodeTypeIndi",record[6].toString());
			NodeEle.setAttribute("RequiredFlag",record[7].toString());
			NodeEle.setAttribute("PublishFlag",record[8].toString());
			NodeEle.setAttribute("Remark",record[9].toString());
			
			
			DTOTemplateNodeAttrDetail dtoattrib=new DTOTemplateNodeAttrDetail();
			
			dtoattrib.setTemplateId(templateId);
			dtoattrib.setNodeId(Integer.parseInt(record[0].toString()));
			
			Vector attrlist=docMgmtImpl.getTemplateNodeAttrDtl(dtoattrib);
			
			//System.out.println("Size:"+attrlist.size());
			
			
			for(int j=0;j<attrlist.size();j++)
			{
				//DTOTemplateNodeAttrDetail dtoattributes=new DTOTemplateNodeAttrDetail();
				DTOTemplateNodeAttrDetail dtoattributes=(DTOTemplateNodeAttrDetail)attrlist.get(j);
				
				Element AttrEle=dom.createElement("ATTRIBUTE");
				
				AttrEle.setAttribute("NodeId",Integer.toString(dtoattributes.getNodeId()));
				AttrEle.setAttribute("AttrId",Integer.toString(dtoattributes.getAttrId()));
				AttrEle.setAttribute("AttrName",dtoattributes.getAttrName());
				AttrEle.setAttribute("AttrValue",dtoattributes.getAttrValue());
				AttrEle.setAttribute("AttrForIndiId",dtoattributes.getAttrForIndiId());
				//AttrEle.setAttribute("ValidValues",dtoattributes.getValidValues());
				AttrEle.setAttribute("RequiredFlag",Character.toString(dtoattributes.getRequiredFlag()));
				AttrEle.setAttribute("EditableFlag",Character.toString(dtoattributes.getEditableFlag()));
				AttrEle.setAttribute("Remark",dtoattributes.getRemark());
				
				NodeEle.appendChild(AttrEle);
					
			}
			
			rootEle.appendChild(NodeEle);
		}
		
			
		//print
		OutputFormat format = new OutputFormat(dom);
		format.setIndenting(true);

		//to generate output to console use this serializer
		//XMLSerializer serializer = new XMLSerializer(System.out, format);

		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String tempBaseFolder=propertyInfo.getValue("BASE_TEMP_FOLDER");
		
		File basefolder = new File(tempBaseFolder);
		File[] allfiles = basefolder.listFiles();
		
		for(int i = 0; i < allfiles.length; i++)
		{
			
			if(allfiles[i].getName().endsWith(".xml"))
			{
				System.out.println("deleted file name :::" + allfiles[i].getAbsolutePath());
				if(allfiles[i].exists())
				allfiles[i].delete();
			}
		}
		
		
		//to generate a file output use fileoutputstream instead of system.out
		 fileWithPath=tempBaseFolder+"/"+"Template-"+TemplateDesc.getTemplateDesc()+".xml";
		 attachment = "true";
		 
		 request = ServletActionContext.getRequest();
		 request.setAttribute("fileWithPath", fileWithPath);
		 request.setAttribute("attachment", attachment);
		 
		XMLSerializer serializer = new XMLSerializer(
		new FileOutputStream(new File(fileWithPath)), format);

		serializer.serialize(dom);


		}catch(ParserConfigurationException pce) {
			//dump it
			System.out.println("Error while trying to instantiate DocumentBuilder " + pce);
			
		
		}
		catch(IOException ie) {
		    ie.printStackTrace();
		 
				    
		}
		
		
	}
	
	public String UseXmlFile()
	{
		
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String tempBaseFolder=propertyInfo.getValue("BASE_TEMP_FOLDER");
		
		int userCode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		File testfile=new File(tempBaseFolder+"/"+XmlDataFileName);
		
		 byte[] fileData    = null;
		 int fileSize=0;
		 if(XmlData !=null)
		 {
			 fileSize=new Long(XmlData.length()).intValue();
		 }
	
		 InputStream sourceInputStream=null;
		 try
		 {
			 if(XmlData !=null)
			 {
				 fileData=getBytesFromFile(XmlData);
				 sourceInputStream= new FileInputStream(XmlData);
			 }
		 }
		 catch (IOException e) 
		 {
			e.printStackTrace();
			
			return "File not found...";
		 }
		
		 try{
		 
		 OutputStream bos = new FileOutputStream(testfile);
			
			
			int temp = 0;
			
			while ((temp = sourceInputStream.read(fileData, 0, fileSize)) != -1) {
				bos.write(fileData, 0, temp);
			}
			bos.close();
			
			sourceInputStream.close();
	     
		}catch(Exception ex) {
			ex.printStackTrace();
		}    
		
		
		//read XML				
		Document dom;
		
		//get an instance of factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
		//get an instance of builder
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		dom = db.parse(testfile);
		Element docEle = dom.getDocumentElement();
		
		NodeList nl= docEle.getElementsByTagName("NODE");
		
		if(nl == null || nl.getLength() == 0){
			
			return "Cannot Import Structure. XML File is incompatible.";
		}
		
		String templateId=docMgmtImpl.insertTemplateDtl(templatedesc,"" , userCode);
		if(templateId ==null || templateId.equals("")) {
			
			return "Structure Name Already Exists...";
		}
		
		Vector nodedata=new Vector();
		
		if(nl != null && nl.getLength() > 0) {
		
			for(int i=0;i<nl.getLength();i++)
			{
				//System.out.println("Size"+nl.getLength());
				Element el=(Element)nl.item(i);
				DTOTemplateNodeDetail dtonode=new DTOTemplateNodeDetail();
				dtonode.setTemplateId(templateId);
				dtonode.setNodeId(Integer.parseInt(el.getAttribute("NodeId")));
				dtonode.setNodeDisplayName(el.getAttribute("DisplayName"));
				dtonode.setParentNodeId(Integer.parseInt(el.getAttribute("ParentId")));
				dtonode.setNodeNo(Integer.parseInt(el.getAttribute("NodeNo")));
				dtonode.setNodeName(el.getAttribute("NodeName"));
				dtonode.setFolderName(el.getAttribute("FolderName"));
				dtonode.setNodeTypeIndi(el.getAttribute("NodeTypeIndi").charAt(0));
				System.out.println("Status=>"+el.getAttribute("NodeTypeIndi").charAt(0));
				
				dtonode.setRequiredFlag(el.getAttribute("RequiredFlag").charAt(0));
				dtonode.setPublishFlag(el.getAttribute("PublishFlag").charAt(0));
				dtonode.setRemark(el.getAttribute("Remark"));
				dtonode.setModifyBy(userCode);
				
				nodedata.addElement(dtonode);
				
				
			}
		}
		
		NodeList nl1=docEle.getElementsByTagName("ATTRIBUTE");
		Vector nodeattribute=new Vector();
		
		if(nl1 != null && nl1.getLength() > 0) {
			
			for(int i=0;i<nl1.getLength();i++)
			{
				//System.out.println("Size"+nl.getLength());
				Element el1=(Element)nl1.item(i);
				
				DTOTemplateNodeAttrDetail dtonodeattr=new DTOTemplateNodeAttrDetail();
				dtonodeattr.setTemplateId(templateId);
				dtonodeattr.setNodeId(Integer.parseInt(el1.getAttribute("NodeId")));
				dtonodeattr.setAttrId(Integer.parseInt(el1.getAttribute("AttrId")));
				dtonodeattr.setAttrName(el1.getAttribute("AttrName"));
				dtonodeattr.setAttrValue(el1.getAttribute("AttrValue"));
				dtonodeattr.setAttrForIndiId(el1.getAttribute("AttrForIndiId"));
				dtonodeattr.setRemark(el1.getAttribute("Remark"));
				dtonodeattr.setModifyBy(userCode);
				
			
				nodeattribute.addElement(dtonodeattr);
				dtonodeattr=null;
				
			}
			
		}
	
		//INsert into TemplateNodeDetail
		docMgmtImpl.InsertTemplateNodeForXml(nodedata);
		
		//INsert Into TemplateNodeAttributeDetail
		
		docMgmtImpl.InsertNodeAttributeFromXml(nodeattribute);
		
		//testfile.delete();
		
		
		}
		catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		return "Structure Imported Successfully..";
	}
	
	
	 public static byte[] getBytesFromFile(File file) throws IOException {
	        InputStream is = new FileInputStream(file);
	    
	        // Get the size of the file
	        long length = file.length();
	    
	        if (length > Integer.MAX_VALUE) {
	            // File is too large
	        }
	    
	        // Create the byte array to hold the data
	        byte[] bytes = new byte[(int)length];
	    
	        // Read in the bytes
	        int offset = 0;
	        int numRead = 0;
	        while (offset < bytes.length
	               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	            offset += numRead;
	        }
	    
	        // Ensure all the bytes have been read in
	        if (offset < bytes.length) {
	            throw new IOException("Could not completely read file "+file.getName());
	        }
	    
	        // Close the input stream and return bytes
	        is.close();
	        return bytes;
}
	
	public String buttonName;
	public String templateId;
	public File XmlData;
	public String XmlDataContentType;
	public String XmlDataFileName;
	public String templatedesc;
	public String fileWithPath;
	public String attachment;
	private HttpServletRequest request;

	
	
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public void setServletRequest(HttpServletRequest request){
	    this.request = request;
	  }

	  public HttpServletRequest getServletRequest(){
	    return request;
	  }


	
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getButtonName() {
		return buttonName;
	}
	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}


	public File getXmlData() {
		return XmlData;
	}


	public void setXmlData(File xmlData) {
		XmlData = xmlData;
	}


	public String getXmlDataContentType() {
		return XmlDataContentType;
	}


	public void setXmlDataContentType(String xmlDataContentType) {
		XmlDataContentType = xmlDataContentType;
	}


	public String getXmlDataFileName() {
		return XmlDataFileName;
	}


	public void setXmlDataFileName(String xmlDataFileName) {
		XmlDataFileName = xmlDataFileName;
	}


	public String getTemplatedesc() {
		return templatedesc;
	}


	public void setTemplatedesc(String templatedesc) {
		this.templatedesc = templatedesc;
	}


	public String getFileWithPath() {
		return fileWithPath;
	}


	public void setFileWithPath(String fileWithPath) {
		this.fileWithPath = fileWithPath;
	}
	
	
	

}
