package com.docmgmt.struts.actions.ImportProjects.ECTD.validate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.struts.actions.ImportProjects.ECTD.SupportingXmlReader;

public class EUNamingConventions {
	static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	static DocumentBuilder documentBuilder;
	static SupportingXmlReader supportingXmlReader = SupportingXmlReader.getInstance();
	static ArrayList<String> localErrList;
	
	public static ArrayList<String> validateFileNames(Document euM1Doc){
		
		localErrList = new ArrayList<String>();
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String xmlPath= propertyInfo.getValue("ECTD_VALIDATOR_EU_NAMING_CONVENTIONS");
		Document euM1namingConventions = getDocument(xmlPath);
		if (euM1namingConventions != null) {
			
			ArrayList<Node> rootNode = supportingXmlReader.getChildNodes(euM1namingConventions);
			if(rootNode.size() > 0){
				ArrayList<Node> nodesToCompare = supportingXmlReader.getChildNodes(rootNode.get(0));
				for (int i = 0; i < nodesToCompare.size(); i++) {
					Node validatorNode = nodesToCompare.get(i);
					if (isValidate(validatorNode)) {
						NodeList targetNodes = euM1Doc.getElementsByTagName(validatorNode.getNodeName());
						if (targetNodes != null && targetNodes.getLength() > 0) {
							for (int j = 0; j < targetNodes.getLength(); j++) {
								Node targetNode = targetNodes.item(j);
								validateNode(validatorNode, targetNode, new HashMap<String, String>());
							}
						}
					}
				}
			}
		}
		else{
			//Error
		}
		
		return localErrList;
	}
	private static Document getDocument(String xmlFilePath){
		Document document = null;
		try {
			
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(xmlFilePath);
		} catch (ParserConfigurationException e) {		
			e.printStackTrace();
		} catch (SAXException e) {		
			e.printStackTrace();
		} catch (IOException e) {		
			e.printStackTrace();
		}
		return document;
	}
	private static boolean isValidate(Node validatorNode){
		try {
			if(validatorNode.getAttributes().getNamedItem("validate").getNodeValue().equals("true")){
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	private static void validateNode(Node validatorNode,Node targetNode,Map<String,String> nodeVariables){
		
		setNodeVariables(validatorNode,targetNode,nodeVariables);
		
		ArrayList<Node> validChildren = supportingXmlReader.getChildNodes(validatorNode);
		ArrayList<Node> targetChildren = supportingXmlReader.getChildNodes(targetNode);
		for (int i = 0; i < validChildren.size(); i++) {
			Node validChild = validChildren.get(i);
			if(validChild.getNodeName().equals("leaf")){
				String fileName = validChild.getAttributes().getNamedItem("filename").getNodeValue();
				for (Iterator<String> iNodeVariables = nodeVariables.keySet().iterator();iNodeVariables.hasNext();) {
					String nodeVariable = iNodeVariables.next();
					//System.out.println(nodeVariable);
					if(fileName.contains("${"+nodeVariable+"}")){
						fileName = fileName.replaceAll("\\$\\{"+nodeVariable+"\\}", nodeVariables.get(nodeVariable));
					}
				}
				fileName = fileName.replaceAll("\\-\\$\\{VAR\\}", "(\\-([a-z0-9])+)?");
				fileName = fileName.replaceAll("\\$\\{EXT\\}", "([a-z])+");
				
				for (Node targetChild : targetChildren) {
					if(validChild.getNodeName().equals(targetChild.getNodeName())){
						String xlinkHref = targetChild.getAttributes().getNamedItem("xlink:href").getNodeValue();
						String targetFileName = xlinkHref.substring(xlinkHref.lastIndexOf("/")+1);
						if(!targetFileName.matches(fileName)){
							localErrList.add("Invalid file name found::"+xlinkHref);
						}
					}
				}
			}
			else{
				for (Node targetChild : targetChildren){
					if(validChild.getNodeName().equals(targetChild.getNodeName())){
						validateNode(validChild, targetChild, nodeVariables);
					}
				}
			}
		}
	}
	private static void setNodeVariables(Node validatorNode,Node targetNode,Map<String,String> nodeVariables){
		NamedNodeMap validAttrs= validatorNode.getAttributes();
		NamedNodeMap targetAttrs= targetNode.getAttributes();
		for (int i = 0; i < validAttrs.getLength(); i++) {
			Node validAttr = validAttrs.item(i);
			Node targetAttr = targetAttrs.getNamedItem(validAttr.getNodeName());
			if(validAttr.getNodeValue().startsWith("${")){
				String variable = validAttr.getNodeValue().substring(2,validAttr.getNodeValue().length()-1);
				nodeVariables.put(variable, targetAttr.getNodeValue());
			}
		}
	}
	public static void main(String[] args) {
		/*String fileName = "${CC}-cover-${VAR}.${EXT}";
		String trgFileName = "bg-cover-z11z.pdf";
		Map nodeVariables = new HashMap();
		nodeVariables.put("CC", "ff");
		for (Iterator<String> iNodeVariables = nodeVariables.keySet().iterator();iNodeVariables.hasNext();) {
			String nodeVariable = iNodeVariables.next();
			if(fileName.contains("${"+nodeVariable+"}")){
				fileName = fileName.replaceAll("\\$\\{"+nodeVariable+"\\}", nodeVariables.get(nodeVariable).toString());
				System.out.println(fileName);
			}
		}
		fileName = fileName.replaceAll("\\-\\$\\{VAR\\}", "(\\-([a-z0-9])+)?");
		fileName = fileName.replaceAll("\\$\\{EXT\\}", "([a-z])+");
		System.out.println(fileName);
		if(trgFileName.matches(fileName)){
			System.out.println("matched");
			
		}else{
			System.out.println("not matched");
		}
		if(trgFileName.matches("bg\\-cover(\\-([a-z])+)?")){
			System.out.println("matched");
			
		}else{
			System.out.println("not matched");
		}*/
		
		Document euM1Doc = getDocument("D:/temp/ViPharm-Error/m1/eu/eu-regional.xml");
		validateFileNames(euM1Doc);
	}
}
