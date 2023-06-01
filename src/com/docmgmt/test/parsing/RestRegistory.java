package com.docmgmt.test.parsing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class RestRegistory {
	private final String REPOSITORY_FILE_PATH = "D://vijay/Learning/xml files/RestRegistry.xml";
	private final String TAG_TO_PARSE = "service";
	private final String SERVICE_NAME_TAG = "service_name";
	private static NodeList allServices;

	public NodeList getServices() {
		if (allServices != null) {
			return allServices;
		}
		XmlParsing xmlParsing = new XmlParsing();
		Document repositoryDOM = xmlParsing.getDOMObject(REPOSITORY_FILE_PATH);
		Element docElement = repositoryDOM.getDocumentElement();
		allServices = docElement.getElementsByTagName(TAG_TO_PARSE);

		return allServices;

	}

	public boolean isServiceTypeExist(String ServiceTypeName,
			boolean displayDetailsIFExist) {
		NodeList services = getServices();
		// System.out.println(services.getLength());

		for (int i = 0; i < services.getLength(); i++) {
			NodeList childNodes = services.item(i).getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
					if (childNodes.item(j).getNodeName().trim().equals(
							SERVICE_NAME_TAG)) {
						if (childNodes.item(j).getTextContent().trim().equals(
								ServiceTypeName)) {
							if (displayDetailsIFExist) {
								displayServiceDetails(services.item(i)
										.getChildNodes(), 1);
							}
							return true;
						}
					}
				}
			}

		}

		return false;
	}

	private void displayServiceDetails(NodeList nodeList, int intend) {

		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);

			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

				System.out.print(printSpace(intend));
				System.out.print(tempNode.getNodeName());
				if (tempNode.getTextContent().trim() != null
						&& !hasElementNode(tempNode)) {
					System.out
							.println(" : " + tempNode.getTextContent().trim());
				} else {
					System.out.println("");
				}

				/*
				 * if (tempNode.hasAttributes()) {
				 * 
				 * // get attributes names and values NamedNodeMap nodeMap =
				 * tempNode.getAttributes();
				 * 
				 * for (int i = 0; i < nodeMap.getLength(); i++) {
				 * 
				 * Node node = nodeMap.item(i);
				 * System.out.println("attr name : " +
				 * node.getNodeName().trim());
				 * System.out.println("attr value : " +
				 * node.getNodeValue().trim());
				 * 
				 * }
				 * 
				 * }
				 */
				if (tempNode.hasChildNodes()) {

					// loop again if has child nodes
					displayServiceDetails(tempNode.getChildNodes(), intend + 1);

				}

			}

		}

	}

	private String printSpace(int intend) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < intend; i++) {
			s.append("   ");
		}

		return s.toString();
	}

	private boolean hasElementNode(Node node) {
		NodeList list = node.getChildNodes();

		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
				return true;
			}

		}
		return false;
	}

}
