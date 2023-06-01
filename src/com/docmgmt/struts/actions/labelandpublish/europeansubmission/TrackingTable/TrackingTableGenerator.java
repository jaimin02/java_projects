package com.docmgmt.struts.actions.labelandpublish.europeansubmission.TrackingTable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.docmgmt.dto.DTOCountryMst;
import com.docmgmt.dto.DTOSubmissionInfoEU14Dtl;
import com.docmgmt.dto.DTOSubmissionInfoEU14SubDtl;
import com.docmgmt.dto.DTOSubmissionInfoEU20Dtl;
import com.docmgmt.dto.DTOSubmissionInfoEU20SubDtl;
import com.docmgmt.dto.DTOSubmissionInfoEUDtl;
import com.docmgmt.dto.DTOSubmissionInfoEUSubDtl;
import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.dto.DTOXmlNodeAttrDtl;
import com.docmgmt.dto.DTOXmlNodeDtl;
import com.docmgmt.dto.DTOXmlWorkspaceDtl;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.entities.WorkspaceFile;
import com.docmgmt.server.webinterface.services.xml.NodeContents;
import com.docmgmt.server.webinterface.services.xml.XmlUtilities;
import com.docmgmt.struts.actions.labelandpublish.FileManager;
import com.docmgmt.struts.resources.MD5;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
public class TrackingTableGenerator
{
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	private File destinationDir;
	private String workspaceId;
	private Document doc;
	private String euDTDVersion;// "" means EU v1.3 and "14" means EU v1.4
	//get an instance of factory
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder documentBuilder;
	{
		try {
			//get an instance of builder
			documentBuilder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public File generateXml()
	{
		File trackingTableXml = null;
		try {
			//create an instance of Document
			doc = documentBuilder.newDocument();			
			//Get XML Info
			String ttName =""; 
			if(euDTDVersion.equals("")){
				ttName = WorkspaceFile.TRACKING_TABLE_XML;
			
			}else if(euDTDVersion.equals("14")){
				
				ttName = WorkspaceFile.TRACKING_TABLE_XML_FOR_EU14;
				
			}else if(euDTDVersion.equals("20")){
				
				ttName = WorkspaceFile.TRACKING_TABLE_XML_FOR_EU20;
				
			}
			DTOXmlWorkspaceDtl dtoXmlWs = docMgmtImpl.getXmlWorkspaceDtl(ttName);
			//Adding the XML Headers
			String[] xmlHeaders = dtoXmlWs.getXmlHeader().split(">[\\s]*<");
			for (int i = 0; i < xmlHeaders.length; i++) {
				String xmlHeader = xmlHeaders[i];
				
				if(!xmlHeader.endsWith(">")){
					xmlHeader=xmlHeader + ">";
				}
				if(!xmlHeader.startsWith("<")){
					xmlHeader="<" + xmlHeader;
				}
				
				if(xmlHeader.contains("!DOCTYPE")){
					String systemId = null;
					String publicId = null;
					String qualifiedName = null;
					
					if(xmlHeader.contains(" SYSTEM ")){
						systemId = xmlHeader.substring(xmlHeader.indexOf("\"")+1, xmlHeader.lastIndexOf("\""));
						qualifiedName = xmlHeader.substring(xmlHeader.indexOf("<!DOCTYPE ")+10, xmlHeader.indexOf(" SYSTEM "));
					}
					else if(xmlHeader.contains(" PUBLIC ")){
						publicId = xmlHeader.substring(xmlHeader.indexOf("\"")+1, xmlHeader.lastIndexOf("\""));
						qualifiedName = xmlHeader.substring(xmlHeader.indexOf("<!DOCTYPE ")+10, xmlHeader.indexOf(" PUBLIC "));
					}
					DocumentType doctype = doc.getImplementation().createDocumentType(qualifiedName,publicId,systemId);
					doc.appendChild(doctype);
				}
				else if(xmlHeader.startsWith("<?")){
					String target = xmlHeader.substring(xmlHeader.indexOf("<?")+2, xmlHeader.indexOf(" "));
					String data = xmlHeader.substring(xmlHeader.indexOf(" ")+1, xmlHeader.indexOf("?>"));
					ProcessingInstruction pi = doc.createProcessingInstruction(target, data);
					doc.appendChild(pi);
				}
			}			
			//Create the root element 
			int rootNodeId = 1;
			DTOXmlNodeDtl xmlRootNodeDtl = docMgmtImpl.getXmlNodeDtl(dtoXmlWs.getXmlWorkspaceId(), rootNodeId);			
			Element rootEle = doc.createElement(xmlRootNodeDtl.getXmlNodeName());			
			//Get root node's attributes
			ArrayList<DTOXmlNodeAttrDtl> lstAttr = docMgmtImpl.getXmlNodeAttrDtl(dtoXmlWs.getXmlWorkspaceId(), xmlRootNodeDtl.getXmlNodeId());		
			for (Iterator<DTOXmlNodeAttrDtl> iterator = lstAttr.iterator(); iterator.hasNext();) {
				
				DTOXmlNodeAttrDtl dtoAttr = iterator.next();
				
				if(dtoAttr.getFixed() == 'Y'){
					rootEle.setAttribute(dtoAttr.getAttrName(), dtoAttr.getDefaultAttrValue());
				}
				else if(dtoAttr.getAttrName().equalsIgnoreCase("title")){
					
					DTOSubmissionMst dtosub =  docMgmtImpl.getSubmissionInfo(workspaceId);
					if(dtosub.getProcedureType().equals("mutual-recognition")){
						rootEle.setAttribute(dtoAttr.getAttrName(), "MRP Tracking Table");
					}
					else if(dtosub.getProcedureType().equals("decentralised")){
						rootEle.setAttribute(dtoAttr.getAttrName(), "DCP Tracking Table");
					}
				}
			}
			
			//Add root Element to Document 
			doc.appendChild(rootEle);
			
			//Add all children for 'rootEle' Element.
			ArrayList<DTOXmlNodeDtl> lstXmlRootChildren = docMgmtImpl.getXmlChildNodeDtl(dtoXmlWs.getXmlWorkspaceId(), rootNodeId);
			addChildren(dtoXmlWs.getXmlWorkspaceId(),lstXmlRootChildren,rootEle,new HashMap<String, String>());		
			//print
			OutputFormat format = new OutputFormat(doc);
			format.setIndenting(true);		
			
			destinationDir.mkdirs();
			trackingTableXml = new File(destinationDir.getAbsolutePath()+"/"+dtoXmlWs.getXmlFileName());
			 
			//to generate output to console use this serializer
			//XMLSerializer serializer = new XMLSerializer(System.out, format);
			//to generate a file output use fileoutputstream instead of system.out
			XMLSerializer serializer = new XMLSerializer(new FileOutputStream(trackingTableXml), format);
			serializer.serialize(doc);
		}
		/*catch(IOException ie) {
		    ie.printStackTrace();
		}*/
		catch(Exception e) {
		    e.printStackTrace();
		}
		return trackingTableXml;
	}
	
	
	private void addChildren(long xmlWorkspaceId,ArrayList<DTOXmlNodeDtl> lstXmlNodes,Element parentEle,HashMap<String, String> inputFields){
	
		for (Iterator<DTOXmlNodeDtl> iNode = lstXmlNodes.iterator(); iNode.hasNext();) {
			DTOXmlNodeDtl dtoXmlNode = iNode.next();
			
			String nodeTable = dtoXmlNode.getTableName();
			String nodeColumn = dtoXmlNode.getColumnName();
			
			String primaryAttrTable = null;
			String primaryAttrColumn = null;
			
			ArrayList<String> primaryAttrValues = null;
			
			if(dtoXmlNode.getRepeatable() == 'Y'){
				ArrayList<DTOXmlNodeAttrDtl> nodePrimaryAttr = docMgmtImpl.getXmlAttrDtl(dtoXmlNode.getPrimaryXmlAttrId());
				
				if(nodePrimaryAttr.size()>0){
					primaryAttrTable = nodePrimaryAttr.get(0).getTableName();
					primaryAttrColumn = nodePrimaryAttr.get(0).getColumnName();
					
					primaryAttrValues = docMgmtImpl.getXmlAttrValuesForRepeatableNode( workspaceId, primaryAttrTable,inputFields,primaryAttrColumn);
				}
			}
			else{
				primaryAttrValues = new ArrayList<String>();
				primaryAttrValues.add("");
			}
			
			if(dtoXmlNode.getXmlNodeName().equals("sequence")){
				 Collections.reverse(primaryAttrValues);
			}
			for (Iterator<String> iNodeCount = primaryAttrValues.iterator(); iNodeCount.hasNext();) {
				String primaryAttrValue = iNodeCount.next();
				
				Element nodeEle = doc.createElement(dtoXmlNode.getXmlNodeName());
				
				//Get child node's attributes
				ArrayList<DTOXmlNodeAttrDtl> lstAttr = docMgmtImpl.getXmlNodeAttrDtl(xmlWorkspaceId, dtoXmlNode.getXmlNodeId());
				
				for (Iterator<DTOXmlNodeAttrDtl> iAttr = lstAttr.iterator(); iAttr.hasNext();) {
					DTOXmlNodeAttrDtl dtoXmlNodeAttr = iAttr.next();
					
					if(dtoXmlNodeAttr.getXmlNodeAttrDtlId() == dtoXmlNode.getPrimaryXmlAttrId()){
						if(dtoXmlNodeAttr.getAttrName().equals("code")){
							primaryAttrValue = primaryAttrValue.toUpperCase();
						}
						nodeEle.setAttribute(dtoXmlNodeAttr.getAttrName(), primaryAttrValue);
					}
					else{
						String attrTable = dtoXmlNodeAttr.getTableName();
						String attrColumn = dtoXmlNodeAttr.getColumnName();
						
						if(dtoXmlNodeAttr.getFixed() == 'Y'){
							nodeEle.setAttribute(dtoXmlNodeAttr.getAttrName(), dtoXmlNodeAttr.getDefaultAttrValue());
						}
						else if(attrTable != null && attrColumn != null){
							ArrayList<String> attrValues = docMgmtImpl.getXmlAttrValue(workspaceId, attrTable, inputFields, attrColumn);
							if(attrValues.size()>0){
								String attrValueToWrite = attrValues.get(0);
								
								if(dtoXmlNodeAttr.getAttrName().equals("code")){
									attrValueToWrite = attrValueToWrite.toUpperCase();
								}
								nodeEle.setAttribute(dtoXmlNodeAttr.getAttrName(), attrValueToWrite);
							}
						}
					}
				}
				
				ArrayList<DTOXmlNodeDtl> lstXmlChildren = docMgmtImpl.getXmlChildNodeDtl(xmlWorkspaceId, dtoXmlNode.getXmlNodeId());
				
				if(lstXmlChildren.size() > 0){
					HashMap<String, String> childInputFields = (HashMap<String, String>)inputFields.clone(); 
					childInputFields.put(primaryAttrColumn, primaryAttrValue);
					
					addChildren(xmlWorkspaceId, lstXmlChildren, nodeEle,childInputFields);
				}
				if(nodeTable != null && nodeColumn != null){
					ArrayList<String> nodeValues = docMgmtImpl.getXmlNodeValue(workspaceId, nodeTable, inputFields, nodeColumn);
					
					for (Iterator<String> iterator = nodeValues.iterator(); iterator.hasNext();) {
						String val = iterator.next();
						if(dtoXmlNode.getEmpty() == 'N' && (val != null && !val.equals(""))){
							Text nodeText = doc.createTextNode(val);
							nodeEle.appendChild(nodeText);
							if(dtoXmlNode.getRepeatable() == 'N'){
								break;
							}
						}
					}
				}
				//Add child Element to Parent
				parentEle.appendChild(nodeEle);
			}
		}
	}
	
	
	public static void addTrackingTable(File euRegional,String workspaceId,String submissionId,String euDTDVersion){
		
		File eu = euRegional.getParentFile();
		String relativePath = "10-cover/common";
		
		//Creating tracking table xml
		TrackingTableGenerator trackingTableGenerator = new TrackingTableGenerator();
		/* Set EU version*/
		trackingTableGenerator.euDTDVersion = euDTDVersion;
		
		trackingTableGenerator.workspaceId = workspaceId;
		trackingTableGenerator.destinationDir = new File(eu,relativePath);
		//Main call
		File trackingTableXml = trackingTableGenerator.generateXml();
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		FileManager fileManager = new FileManager();	
		//Copy Util Folder
		File baseWorkDir = propertyInfo.getDir("BaseWorkFolder");
        File sourceLocation = new File(baseWorkDir.getAbsoluteFile()+"/util_tt/util/dtd");
        File targetLocation = new File(eu.getAbsolutePath()+"/util/dtd");
        targetLocation.mkdirs();
        fileManager.copyDirectory(sourceLocation, targetLocation);
		sourceLocation = new File(baseWorkDir.getAbsoluteFile()+"/util_tt/util/style");
        targetLocation = new File(eu.getAbsolutePath()+"/util/style");
        targetLocation.mkdirs();
		fileManager.copyDirectory(sourceLocation, targetLocation);
		//Add current sequence details in newly generated Tracking Table
		trackingTableGenerator.addCurrentSequenceDetails(trackingTableXml,submissionId);		
		/*
		 * conversion code from xml to pdf.
		 * 
		 * */
		
		//below code added on 12/3/2013 for converting xml to pdf
		//for converting xml to pdf uncomment below two lines
		
		XMLToPdfConverter xmltopdfobj=new XMLToPdfConverter();
		xmltopdfobj.xmlToHTML(eu,euDTDVersion); // 
		//complete xml to pdf
		try {
			Document euRegionalDoc = trackingTableGenerator.documentBuilder.parse(euRegional);			
			ArrayList<NodeContents> nodeContentsList=new ArrayList<NodeContents>();
			NodeContents node=new NodeContents("eu:eu-backbone",null,null);
            nodeContentsList.add(node);
            node=new NodeContents("m1-eu",null,null);
            nodeContentsList.add(node);
            node=new NodeContents("m1-0-cover",null,null);
            nodeContentsList.add(node);
            ArrayList<String> attr=new ArrayList<String>();
            attr.add("country=common");
            node=new NodeContents("specific",null,attr);
            nodeContentsList.add(node);
			Node specificCommon = XmlUtilities.createIfNotExists(euRegionalDoc, euRegionalDoc, nodeContentsList);
			
			Element ttLeaf = euRegionalDoc.createElement("leaf");
			ttLeaf.setAttribute("ID", "node-tt");
			//add following line for tracking table in pdf link 
			if(euDTDVersion.equals("20")){
				ttLeaf.setAttribute("xlink:href", relativePath+"/common-tracking.pdf");
			}
			else
			{
				ttLeaf.setAttribute("xlink:href", relativePath+"/common-tracking.pdf");
			}
			
		
			
			//add below line for tracking table in xml link
			//ttLeaf.setAttribute("xlink:href", relativePath+"/"+trackingTableXml.getName());
			
			ttLeaf.setAttribute("operation","new");
			ttLeaf.setAttribute("checksum-type","md5");
			MD5 md5 = new MD5();
			
			//below line for check-sum of xml tracking table  comment as per requered
			//String ttChecksum = md5.getMd5HashCode(trackingTableXml.getAbsolutePath());
			
			//below line for check-sum of pdf tracking table comment it if tracking table in xml format
			
			String ttChecksum="";
			if(euDTDVersion.equals("20")){
				 ttChecksum = md5.getMd5HashCode(trackingTableXml.getParentFile()+"/common-tracking.pdf");
			}
			else
			{
				ttChecksum = md5.getMd5HashCode(trackingTableXml.getParentFile()+"/common-tracking.pdf");
			}
			
//			System.out.println("CheckSum :"+ttChecksum);
//			System.out.println("Xml Path (parent)");
//			
			ttLeaf.setAttribute("checksum", ttChecksum);
			Element title = euRegionalDoc.createElement("title");
			title.setTextContent("Tracking Table in PDF");
			ttLeaf.appendChild(title);		
			specificCommon.appendChild(ttLeaf);
	//		OutputFormat format = new OutputFormat(euRegionalDoc);
			//format.setIndenting(true);
			//			
//			XMLSerializer serializer = new XMLSerializer(new FileOutputStream(euRegional), format);
//			serializer.serialize(euRegionalDoc);
			
			
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			//transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		//	StringWriter writer = new StringWriter();
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "../../util/dtd/eu-regional.dtd");
			transformer.transform(new DOMSource(euRegionalDoc), new StreamResult(euRegional.getAbsolutePath()));
							
			
			
			
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addLinkInEuRegional(File euRegional,String workspaceId,String submissionId,String euDTDVersion){		
		
		
		
		File eu = euRegional.getParentFile();
	
		String relativePath = "10-cover/common";
//		String trackingPath=eu.getAbsolutePath()+"/"+relativePath +"/common-tracking.pdf";
//		File trackingTableFile=new File(trackingPath);
//
//		if(trackingTableFile.exists()){
//			System.out.println("Tracking Table File Already Exist....");
//			return;
//		}
//		
		//Creating tracking table xml
		TrackingTableGenerator trackingTableGenerator = new TrackingTableGenerator();
		/* Set EU version*/
		trackingTableGenerator.euDTDVersion = euDTDVersion;
		trackingTableGenerator.workspaceId = workspaceId;
		trackingTableGenerator.destinationDir = new File(eu,relativePath);
		
		
		//Main call
		File trackingTableXml = trackingTableGenerator.generateXml();
		
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		FileManager fileManager = new FileManager();	
		//Copy Util Folder
		File baseWorkDir = propertyInfo.getDir("BaseWorkFolder");
        File sourceLocation = new File(baseWorkDir.getAbsoluteFile()+"/util_tt/util/dtd");
        File targetLocation = new File(eu.getAbsolutePath()+"/util/dtd");
        targetLocation.mkdirs();
        fileManager.copyDirectory(sourceLocation, targetLocation);
		sourceLocation = new File(baseWorkDir.getAbsoluteFile()+"/util_tt/util/style");
        targetLocation = new File(eu.getAbsolutePath()+"/util/style");
        targetLocation.mkdirs();
		fileManager.copyDirectory(sourceLocation, targetLocation);
		
		
		
		
		//Add current sequence details in newly generated Tracking Table
		trackingTableGenerator.addCurrentSequenceDetails(trackingTableXml,submissionId);
		XMLToPdfConverter xmltopdfobj=new XMLToPdfConverter();
		xmltopdfobj.xmlToHTML(eu,euDTDVersion); 
		try {
			Document euRegionalDoc = trackingTableGenerator.documentBuilder.parse(euRegional);
			ArrayList<NodeContents> nodeContentsList=new ArrayList<NodeContents>();
			NodeContents node=new NodeContents("eu:eu-backbone",null,null);
						
            nodeContentsList.add(node);
            node=new NodeContents("m1-eu",null,null);
            nodeContentsList.add(node);
            node=new NodeContents("m1-0-cover",null,null);
            nodeContentsList.add(node);
            ArrayList<String> attr=new ArrayList<String>();
            attr.add("country=common");
            node=new NodeContents("specific",null,attr);
            nodeContentsList.add(node);
			Node specificCommon = XmlUtilities.createIfNotExists(euRegionalDoc, euRegionalDoc, nodeContentsList);
			
			Element ttLeaf = euRegionalDoc.createElement("leaf");
			ttLeaf.setAttribute("ID", "node-tt");
			//add following line for tracking table in pdf link 
			ttLeaf.setAttribute("xlink:href", relativePath+"/common-tracking.pdf");
			
			//add below line for tracking table in xml link
			//ttLeaf.setAttribute("xlink:href", relativePath+"/"+trackingTableXml.getName());
			
			ttLeaf.setAttribute("operation","new");
			ttLeaf.setAttribute("checksum-type","md5");
			MD5 md5 = new MD5();
			
			//below line for check-sum of xml tracking table  comment as per requered
			//String ttChecksum = md5.getMd5HashCode(trackingTableXml.getAbsolutePath());
			
			//below lines for check-sum of pdf tracking table comment it if tracking table in xml format
			String ttChecksum;
			ttChecksum = md5.getMd5HashCode(trackingTableXml.getParentFile()+"/common-tracking.pdf");	
			ttLeaf.setAttribute("checksum", ttChecksum);
			
			Element title = euRegionalDoc.createElement("title");
			//change title PDF to xml or xml to pdf as per required
			title.setTextContent("Tracking Table in PDF");
			ttLeaf.appendChild(title);
			specificCommon.appendChild(ttLeaf);
			//Write
			OutputFormat format = new OutputFormat(euRegionalDoc);
			//format.setIndenting(true);
			//format.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
		//	format.setOmitXMLDeclaration(true);
		//	format.setOmitDocumentType(true);
			

//			XMLSerializer serializer = new XMLSerializer(new FileOutputStream(euRegional), format);
//			serializer.serialize(euRegionalDoc);
//			
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			//transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		//	StringWriter writer = new StringWriter();
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "../../util/dtd/eu-regional.dtd");
			transformer.transform(new DOMSource(euRegionalDoc), new StreamResult(euRegional.getAbsolutePath()));
			
			
			
			
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
		
			System.gc();
			trackingTableXml.delete();
		}
	}
	private void addCurrentSequenceDetails(File trackingTableXml,String submissionId){
		String submissionDescription = "",currentSeqNumber = "",countryCode="";
		char RMSSubmited = 'N'; 
		java.util.Date dateOfSubmission = null;
		SimpleDateFormat monthFormatter = new SimpleDateFormat("MMM");
		SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
		DTOSubmissionMst submissiondtl = docMgmtImpl.getSubmissionInfoEURegion(workspaceId);
		if(submissiondtl.getEUDtdVersion().equals("13")){
			DTOSubmissionInfoEUDtl submissionInfoEUDtl = docMgmtImpl.getWorkspaceSubmissionInfoEUDtlBySubmissionId(submissionId);
			currentSeqNumber = submissionInfoEUDtl.getCurrentSeqNumber();
			RMSSubmited = submissionInfoEUDtl.getRMSSubmited();
			countryCode = submissionInfoEUDtl.getCountryCode();
			dateOfSubmission = (java.util.Date) submissionInfoEUDtl.getDateOfSubmission();
		}else if(submissiondtl.getEUDtdVersion().equals("14")){
			DTOSubmissionInfoEU14Dtl submissionInfoEU14Dtl = docMgmtImpl.getWorkspaceSubmissionInfoEU14DtlBySubmissionId(submissionId);
			currentSeqNumber = submissionInfoEU14Dtl.getCurrentSeqNumber();
			RMSSubmited = submissionInfoEU14Dtl.getRMSSubmited();
			countryCode = submissionInfoEU14Dtl.getCountryCode();
			dateOfSubmission = (java.util.Date) submissionInfoEU14Dtl.getDateOfSubmission();
		}else if(submissiondtl.getEUDtdVersion().equals("20")){
			DTOSubmissionInfoEU20Dtl submissionInfoEU20Dtl = docMgmtImpl.getWorkspaceSubmissionInfoEU20DtlBySubmissionId(submissionId);
			currentSeqNumber = submissionInfoEU20Dtl.getCurrentSeqNumber();
			RMSSubmited = submissionInfoEU20Dtl.getRMSSubmited();
			countryCode = submissionInfoEU20Dtl.getCountryCode();
			dateOfSubmission = (java.util.Date) submissionInfoEU20Dtl.getDateOfSubmission();
			System.out.println("EU-20 Current seq:"+currentSeqNumber+"dos:"+dateOfSubmission);
		}
		
		ArrayList cmsDtl = null;
		if(submissiondtl.getEUDtdVersion().equals("13")){
			cmsDtl = docMgmtImpl.getWorkspaceCMSSubmissionInfo(submissionId,submissiondtl.getEUDtdVersion());
			if(cmsDtl.size() > 0){
				submissionDescription = ((DTOSubmissionInfoEUSubDtl)cmsDtl.get(0)).getSubmissionDescription();
			}
			else{
				//If no CMS Info found then get RMS Info.
				DTOSubmissionInfoEUSubDtl dtoRms = docMgmtImpl.getWorkspaceRMSSubmissionInfo(submissionId);
				submissionDescription = dtoRms.getSubmissionDescription();
			}
		}else if(submissiondtl.getEUDtdVersion().equals("14")){
			cmsDtl = docMgmtImpl.getWorkspaceCMSSubmissionInfo(submissionId,submissiondtl.getEUDtdVersion());
			if(cmsDtl.size() > 0){
				submissionDescription = ((DTOSubmissionInfoEUSubDtl)cmsDtl.get(0)).getSubmissionDescription();
			}
			else{
				//If no CMS Info found then get RMS Info.
				DTOSubmissionInfoEU14SubDtl dtoRms = docMgmtImpl.getWorkspaceRMSSubmissionInfoEU14(submissionId);
				submissionDescription = dtoRms.getSubmissionDescription();
			}
		}else if(submissiondtl.getEUDtdVersion().equals("20")){
			cmsDtl = docMgmtImpl.getWorkspaceCMSSubmissionInfo(submissionId,submissiondtl.getEUDtdVersion());
			if(cmsDtl.size() > 0){
				submissionDescription = ((DTOSubmissionInfoEUSubDtl)cmsDtl.get(0)).getSubmissionDescription();
				System.out.println("EU-20 CMS Details submissionDescription:"+submissionDescription);
			}
			else{
				//If no CMS Info found then get RMS Info.
				DTOSubmissionInfoEU20SubDtl dtoRms = docMgmtImpl.getWorkspaceRMSSubmissionInfoEU20(submissionId);
				submissionDescription = dtoRms.getSubmissionDescription();
				System.out.println("EU-20 RMS Details submissionDescription:"+submissionDescription);
			}
		}
		try {
			Document trackingXmlDoc = documentBuilder.parse(trackingTableXml);	
			//Create new sequence node 
			Element newSequence = trackingXmlDoc.createElement("sequence");
			newSequence.setAttribute("number", currentSeqNumber);
			
			//Add description
			Element description = trackingXmlDoc.createElement("description");
			description.setTextContent(submissionDescription);
			newSequence.appendChild(description);
			
			//Add RMS (if selected)
			if(RMSSubmited == 'Y'){
				Element rms = trackingXmlDoc.createElement("submission");
				DTOCountryMst rmsCountry = docMgmtImpl.getCountry(countryCode);
				rms.setAttribute("code", rmsCountry.getCountryCode().toUpperCase());
				
				rms.setAttribute("month", monthFormatter.format(dateOfSubmission));
				rms.setAttribute("year", yearFormatter.format(dateOfSubmission));
				newSequence.appendChild(rms);
			}
			if(euDTDVersion.equals("")){
				for (Object obj : cmsDtl) {
					DTOSubmissionInfoEUSubDtl submissionInfoEUSubDtl = (DTOSubmissionInfoEUSubDtl)obj;
					Element cms = trackingXmlDoc.createElement("submission");
					cms.setAttribute("code", submissionInfoEUSubDtl.getCountryCodeName().toUpperCase());
					cms.setAttribute("month", monthFormatter.format((java.util.Date) submissionInfoEUSubDtl.getDateOfSubmission()));
					cms.setAttribute("year", yearFormatter.format((java.util.Date) submissionInfoEUSubDtl.getDateOfSubmission()));
					newSequence.appendChild(cms);
				}
			}
			else if(euDTDVersion.equals("14")){
				for (Object obj : cmsDtl) {
					DTOSubmissionInfoEUSubDtl submissionInfoEUSubDtl = (DTOSubmissionInfoEUSubDtl)obj;
					Element cms = trackingXmlDoc.createElement("submission");
					cms.setAttribute("code", submissionInfoEUSubDtl.getCountryCodeName().toUpperCase());
					cms.setAttribute("month", monthFormatter.format((java.util.Date) submissionInfoEUSubDtl.getDateOfSubmission()));
					cms.setAttribute("year", yearFormatter.format((java.util.Date) submissionInfoEUSubDtl.getDateOfSubmission()));
					newSequence.appendChild(cms);
				}
			}
			else if(euDTDVersion.equals("20")){
				for (Object obj : cmsDtl) {
					DTOSubmissionInfoEUSubDtl submissionInfoEUSubDtl = (DTOSubmissionInfoEUSubDtl)obj;
					Element cms = trackingXmlDoc.createElement("submission");
					cms.setAttribute("code", submissionInfoEUSubDtl.getCountryCodeName().toUpperCase());
					cms.setAttribute("month", monthFormatter.format((java.util.Date) submissionInfoEUSubDtl.getDateOfSubmission()));
					cms.setAttribute("year", yearFormatter.format((java.util.Date) submissionInfoEUSubDtl.getDateOfSubmission()));
					newSequence.appendChild(cms);
				}
			}
		
			//Find proper node position and append the new sequence node
			Node trackingRoot =  XmlUtilities.getFirstChild(trackingXmlDoc);
			Node rootChild =  XmlUtilities.getFirstChild(trackingRoot);
			while(true){
				rootChild = XmlUtilities.getNextSibling(rootChild);
				if((rootChild == null) || (rootChild != null && rootChild.getNodeName().equals("sequence"))){
					break;
				}
			}
			if(rootChild == null){
				trackingRoot.appendChild(newSequence);
			}else{
				trackingRoot.insertBefore(newSequence, rootChild);
			}
			//Write
			OutputFormat format = new OutputFormat(trackingXmlDoc);
			format.setIndenting(true);
			
			XMLSerializer serializer = new XMLSerializer(new FileOutputStream(trackingTableXml), format);
			serializer.serialize(trackingXmlDoc);
			
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// This method walks the document and removes all nodes
    // of the specified type and specified name.
    // If name is null, then the node is removed if the type matches.
    public static void removeAll(Node node, short nodeType, String name) {
        if (node.getNodeType() == nodeType &&
                (name == null || node.getNodeName().equals(name))) {
            node.getParentNode().removeChild(node);
        } else {
            // Visit the children
            NodeList list = node.getChildNodes();
            for (int i=0; i<list.getLength(); i++) {
                removeAll(list.item(i), nodeType, name);
            }
        }
    }

}
