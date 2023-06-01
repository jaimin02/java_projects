package com.docmgmt.test.parsing;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QualityMaster {

	private final static String REPOSITORY_FILE_PATH = "D://vijay/Learning/xml files/RestRegistry.xml";
	private final static String[] QUALITY_NODES = { "responsetime",
			"throughput", "availability", "accessibility", "successability",
			"servicerecognition", "messagingreliability",
			"transactionintegrity", "collaborability", "informability",
			"observability", "controllability" };

	/**
	 * @param args
	 */
	
	public void updateQuality(JSONArray qualityObjs) {

		XmlParsing xmlObj = new XmlParsing();
		Document dom = xmlObj.getDOMObject(REPOSITORY_FILE_PATH);

		Element docElement = dom.getDocumentElement();

		for (int i = 0; i < qualityObjs.size(); i++) {

			JSONObject obj = (JSONObject) qualityObjs.get(i);
			Node serviceNode = getMainServiceNode(docElement, obj);

			if (serviceNode != null) {
					updateQualityParameter(serviceNode, obj);
			}
		}
		try {
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(dom);
			StreamResult result = new StreamResult(new File(
					REPOSITORY_FILE_PATH));
			transformer.transform(source, result);
		} catch (Exception e) {

		}

	}

	private  void updateQualityParameter(Node serviceNode, JSONObject obj) {
	
		for (int i = 0; i < QUALITY_NODES.length; i++) {
			if (obj.containsKey(QUALITY_NODES[i])) {
				Node nodeToUpdate = findNode(serviceNode, QUALITY_NODES[i], true, true);
				if (nodeToUpdate != null) {
					String newValue = (String) obj.get(QUALITY_NODES[i]);
					if (newValue != null)
						nodeToUpdate.setTextContent(newValue);
				}
			}
			// System.out.println((String) obj.get(QUALITY_NODES[i]));
		}

		// System.out.println("==>"+a.getNodeName());

	}

	private  Node findNode(Node root, String elementName, boolean deep,
			boolean elementsOnly) {
		// Check to see if root has any children if not return null
		if (!(root.hasChildNodes()))
			return null;

		// Root has children, so continue searching for them
		Node matchingNode = null;
		String nodeName = null;
		Node child = null;

		NodeList childNodes = root.getChildNodes();
		int noChildren = childNodes.getLength();
		for (int i = 0; i < noChildren; i++) {
			if (matchingNode == null) {
				child = childNodes.item(i);
				nodeName = child.getNodeName();
				if ((nodeName != null) & (nodeName.equals(elementName)))
					return child;
				if (deep)
					matchingNode = findNode(child, elementName, deep,
							elementsOnly);
			} else
				break;
		}

		if (!elementsOnly) {
			NamedNodeMap childAttrs = root.getAttributes();
			noChildren = childAttrs.getLength();
			for (int i = 0; i < noChildren; i++) {
				if (matchingNode == null) {
					child = childAttrs.item(i);
					nodeName = child.getNodeName();
					if ((nodeName != null) & (nodeName.equals(elementName)))
						return child;
				} else
					break;
			}
		}
		return matchingNode;
	}

	private  Node getMainServiceNode(Element docElement, JSONObject object) {
		NodeList allServiceType = docElement.getElementsByTagName("service");

		for (int i = 0; i < allServiceType.getLength(); i++) {
			Node serviceNode = allServiceType.item(i).getChildNodes().item(1);
			if (serviceNode.getTextContent().trim().equals(object.get("service_type"))) {
				return allServiceType.item(i);
			}

		}
		return null;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JSONArray array = new JSONArray();
		JSONObject obj1 = new JSONObject();

		obj1.put("service_type", "ahmedabad newdelhi");
		obj1.put("servicerecognition", "100");
		obj1.put("responsetime", "20");

		JSONObject obj2 = new JSONObject();

		obj2.put("service_type", "luton_airport goal_airport");
		obj2.put("responsetime", "3");

		array.add(obj1);
		array.add(obj2);
		
		QualityMaster parsing=new QualityMaster();

		parsing.updateQuality(array);

	}


}
