/**
 * 
 */
package com.docmgmt.struts.actions.ImportProjects.ECTD.validate;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * @author nagesh
 *
 */
public class CommonXmlValidations 
{
	
	//check the hrefs in xml file
	//href should have a actual folder
	public boolean checkPath (String path,File indexXml)
	{		
		if (path.contains("\\"))
		{			
			return false;
		}
		if (path.startsWith("/"))
		{
			return false;
		}
		if (path.matches("[A-Za-z]{1}:{1}.*"))
		{
			return false;
		}		
		File seqFolder = indexXml.getParentFile();
		File[] seqFolderList = seqFolder.listFiles();
		String checkPath=null;
		if (path.split("/")!=null)
			checkPath = path.split("/")[0];
		for (int i=0;i<seqFolderList.length;i++)
		{
			if (seqFolderList[i].getName().equalsIgnoreCase(checkPath))
				return true;
		}
		return false;
	}
	
	public boolean compare(File deliveredDTD,File storedDTD)
	{
		boolean equal=true;
		try 
		{
			FileInputStream fileDeliveredDTD=new FileInputStream(deliveredDTD);
			FileInputStream fileStoredDTD=new FileInputStream(storedDTD);
			int ch1=-1;
			int ch2=-1;
			while (true)
			{
				ch1=fileDeliveredDTD.read();
				ch2=fileStoredDTD.read();
				if (ch1==-1 || ch2==-1)
					break;
				if (ch1!=ch2)
				{
					equal=false;
					break;
				}
			}
			if (ch1==-1 && ch2==-1 && equal)
				equal=true;
				
			if (ch1!=-1 && ch2!=-1 && !equal)
				equal=false;
			
			if (ch1!=ch2 && (ch1==-1 && ch2==-1))
				equal=false;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}		
		return equal;
	}
	
	class ValidateAgainstStoredDTDErrorHandler implements ErrorHandler
	{
		boolean errorFound = false;
		
		public void error(SAXParseException exception) throws SAXException
		{
			errorFound = true;
		}
		public void fatalError(SAXParseException exception) throws SAXException {}
		public void warning(SAXParseException exception) throws SAXException {}
		
		public boolean isErrorFound() {
			return errorFound;
		}
		public void setErrorFound(boolean errorFound) {
			this.errorFound = errorFound;
		}
	}
	
	public boolean validateAgainstStoredDTD(Document xmlDoc,String pathStoredDTD)
	{		
		String tempFileName = "temp.xml";
		boolean parseError = false;
		TransformerFactory transFactory=TransformerFactory.newInstance();		
		Transformer trans;
		try 
		{
			trans=transFactory.newTransformer();
			trans.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,pathStoredDTD);					
			DOMSource source = new DOMSource(xmlDoc);
		    StreamResult result = new StreamResult(new FileWriter(tempFileName));
			trans.transform(source,result);
		}		
		catch (TransformerConfigurationException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (TransformerException e) 
		{
			e.printStackTrace();
		}
		ValidateAgainstStoredDTDErrorHandler errorHandler = new ValidateAgainstStoredDTDErrorHandler();
		try 
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			DocumentBuilder builder=factory.newDocumentBuilder();	
			builder.setErrorHandler(errorHandler);
			builder.parse(new FileInputStream(new File(tempFileName)));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			parseError = true;
		} catch (SAXException e) {
			e.printStackTrace();
			parseError = true;
		} catch (IOException e) {
			e.printStackTrace();
			parseError = true;
		}
		return !(errorHandler.isErrorFound() || parseError);
	}
	
	//file name should have single extension
	public boolean checkFileName(String fileName)
	{
		int len=fileName.split("\\.").length;
		if (len==2)
			return true;
		return false;
	}
	
	public boolean checkNodeExtension(Document xmlDoc)
	{
		NodeList nodeExtList=xmlDoc.getElementsByTagName("node-extension");		
		if (nodeExtList == null || nodeExtList.getLength() == 0)
			return true;		
		return false;
	}
	
	public boolean checkChecksumTypeAttribute(Document xmlDoc)
	{
		NodeList leafList=xmlDoc.getElementsByTagName("leaf");		
		for (int i=0;i<leafList.getLength();i++)
		{
			Element element=(Element)leafList.item(i);
			String chkType=element.getAttribute("checksum-type");
			if (!chkType.equalsIgnoreCase("MD5"))
			{
				return false;
			}
		}
		return true;
	}
	
	//pass root node
	//check whether node starting with m
	//either have a leaf or another same kind of node
	public void checkLeafs(Node node, Vector<String> nodeNames)
	{
		if (node == null)
			return;
		if (node.getNodeName().matches("m\\d{1,2}\\-.*") || node.getNodeName().equals("specific"))
		{
			NodeList nodeList = node.getChildNodes();
			boolean found = false;
			for (int i = 0 ; i < nodeList.getLength() ; i++)
				if (nodeList.item(i).getNodeName().equals("leaf") || 
						nodeList.item(i).getNodeName().matches("m\\d{1,2}\\-.*") || 
						nodeList.item(i).getNodeName().equals("specific"))
				{
					found = true;
					break;
				}
			if (!found)
				nodeNames.add(node.getNodeName() + " doesn't have a leaf.");
		}					
		NodeList nodeList = node.getChildNodes();
		for (int i = 0 ; i < nodeList.getLength() ; i++)
			checkLeafs(nodeList.item(i), nodeNames);
	}
	
	public class HrefError
	{
		int result;
		String leafHref;
		public static final int INTRA_SEQUENCE = 0;
		public static final int INTER_APPLICATION = 1;
		public static final int INTER_SEQUENCE = 2;
		
		public HrefError(
				int result,
				String leafHref) 
		{
			this.result = result;
			this.leafHref = leafHref;
		}

		public int getResult() {
			return result;
		}

		public void setResult(int result) {
			this.result = result;
		}

		public String getLeafHref() {
			return leafHref;
		}

		public void setLeafHref(String leafHref) {
			this.leafHref = leafHref;
		}
	}
	
	/**
	 * 
	 * @param xmlDoc
	 * @param seqFolder
	 */
	public Vector<HrefError> checkHrefs(Document xmlDoc, File seqFolder)
	{
		Vector<HrefError> hrefErrors = new Vector<HrefError>();
		int result = 0;
		String seqFolderPath = seqFolder.getAbsolutePath();
		String appFolderPath = seqFolder.getParentFile().getAbsolutePath();
		NodeList leafList=xmlDoc.getElementsByTagName("leaf");		
		for (int i=0;i<leafList.getLength();i++)
		{
			Element element=(Element)leafList.item(i);
			String fileName=element.getAttribute("xlink:href");
			File file = new File(fileName);
			fileName = file.getAbsolutePath();
			if (fileName.startsWith(appFolderPath)) 
				if (!fileName.startsWith(seqFolderPath))
					result = 2;
			else
				result = 1;
			if (result != 0)
				hrefErrors.add(new HrefError(result, fileName));
		}
		return hrefErrors;
	}
	
	/* CA specific validations */
	public boolean checkSubmissionDate(Document xmlDoc)
	{
		Node node=xmlDoc.getElementsByTagName("submission-date").item(0);
		String date=node.getTextContent();
		return date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}");
	}
	
	public boolean check3011Form(Document xmlDoc)
	{
		NodeList leafList=xmlDoc.getElementsByTagName("m1-2-1-drug-submission-application");
		if (leafList.getLength() != 1)
			return false;
		Element element = (Element)leafList.item(0);
		leafList = element.getChildNodes();
		boolean found=false;
		for (int i=0;i<leafList.getLength();i++)
		{
			Node node = leafList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				String fileName=node.getAttributes().getNamedItem("xlink:href").getTextContent();
				if (fileName.endsWith("hc-sc-3011-fr.pdf") ||
					fileName.endsWith("hc-sc-3011-en.pdf") || 
					fileName.endsWith("hc-sc-3011-fr.xml") || 
					fileName.endsWith("hc-sc-3011-en.xml"))
				{
					found=true;
					break;
				}
			}
		}
		return found;
	}
	
	public static void main(String[] args) 
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      //factory.setValidating(true);
	      DocumentBuilder builder=null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		}
	      builder.setErrorHandler(new ErrorHandler() {
	        //Ignore the fatal errors
	        public void fatalError(SAXParseException exception)throws SAXException { }
	        //Validation errors 
	        public void error(SAXParseException e)throws SAXParseException {
	          System.out.println("Error at " +e.getLineNumber() + " line.");
	          System.out.println(e.getMessage());
	          //System.exit(0);
	        }
	        //Show warnings
	        public void warning(SAXParseException err)throws SAXParseException{
	          System.out.println(err.getMessage());
	          //System.exit(0);
	        }
	      });
	      try {
			Document xmlDocument = builder.parse(new FileInputStream("/home/data/Sample_eCTDs_INGAMERICA/EU/123430/0001/index.xml"));//"D:/nagesh/misc/ELC Published Dossiers/Trimetazidine-Mylan/0000/index.xml"));			
			//System.out.println(new XmlValidations().validateAgainstStoredDTD(xmlDocument,"D:/nagesh/misc/ELC Published Dossiers/Trimetazidine-Mylan/0000/ich-ectd-3-2.dtd"));
			//new XmlValidations().checkLeafs(xmlDocument);
			Vector<String> v =new Vector<String>();
			new CommonXmlValidations().checkLeafs(xmlDocument,v);
			for (Iterator<String> iterator = v.iterator(); iterator.hasNext();) {
				String string = iterator.next();
				System.out.println(string);
			}
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//System.out.println(new XmlValidations().compare(new File("D:/nagesh/misc/ELC Published Dossiers/Trimetazidine-Mylan/0000/util/dtd/ich-ectd-3-2.dtd"),new File("D:/nagesh/misc/ELC Published Dossiers/Trimetazidine-Mylan/0000/ich-ectd-3-2.dtd")));
		//System.out.println(new XmlValidations().checkFileName("abc.doc.pdf"));
	}

}
