package com.docmgmt.struts.actions.ectdviewer.xmlread;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.opensymphony.xwork2.ActionSupport;

public class XmlReadAction extends ActionSupport {

	public static final short INDEX_READ_SUCCESSFULLY = 0;
	public static final short FILE_NOT_FOUND = 1;
	public static final short INVALID_XML = 2;

	public String mainProjectPath = "";

	// public static final String INDEX_XML_FILENAME = "/12345/0001/index.xml";
	public static final String REGIONAL_MODULE_NODE_NAME = "m1-administrative-information-and-prescribing-information";
	public static final String INDEX_XML_ROOT = "ectd:ectd";

	public static final String ECTD_OPERATION_ATTR = "operation";
	public static final String ECTD_ID_ATTR = "ID";
	public static final String ECTD_APPVERSION_ATTR = "application-version";
	public static final String ECTD_CHECKSUM_TYPE_ATTR = "checksum-type";
	public static final String ECTD_CHKSUM_ATTR = "checksum";
	public static final String ECTD_XLINK_HREF_ATTR = "xlink:href";

	public static final String ECTD_LEAF_NODE = "leaf";
	public static final String ECTD_MODIFIEDFILE_ATTR = "modified-file";
	public static final String ECTD_TITLE_NODE = "title";

	public static final String ECTD_STF_ROOT_NODE = "ectd:study";
	public static final String US_REGIONAL_ROOT_NODE = "fda-regional:fda-regional";
	public static final String EU_REGIONAL_ROOT_NODE = "eu:eu-backbone";

	public StringBuffer treeHtml;
	public String treeHtmlM1;

	public String nodeId = "";
	public String fileWithPath = "";
	public String readToolTipData = "no";
	public String displayM1 = "no";
	public String m1XmlFilePath = "";
	public String ectdNodeId = "", ectdAppVersion = "", ectdCheckSumType = "",
			ectdCheckSum = "", ectdXlinkHref = "", ectdOperation = "";
	DocumentBuilder documentBuilder;

	public ArrayList<String> sequenceDirectoryName;
	public File uploadFile;

	public String uploadFileFileName;
	public String uploadFileContentType;
	int currNodeId;
	public String folderPath = "";
	public String deleteFilePath = "";
	public String sequenceNo = "";
	private String fullIndexPath = "";
	private String sequencePath = "";
	private String m1XmlPathFolder = "";
	public String region = "";
	public String procedure_type = "";
	public String allOperationDetails = "";
	public ArrayList<String> test = null;
	public String errorCode = "";
	public String refLinkPath = "";

	// private static FileManager fm = new FileManager();

	public String execute() {

		sequenceDirectoryName = FileManager.takeSequence(folderPath,
				"index.xml");
		if (sequenceDirectoryName == null || sequenceDirectoryName.size() <= 0) {

			addActionError("No Sequence Found");

		}

		if (sequenceNo.equalsIgnoreCase("")) {
			sequenceNo = sequenceDirectoryName.get(0);
			fullIndexPath = folderPath + "/" + sequenceNo + "/" + "index.xml";
			sequencePath = folderPath + "/" + sequenceNo;
		} else {
			fullIndexPath = folderPath + "/" + sequenceNo + "/" + "index.xml";
			sequencePath = folderPath + "/" + sequenceNo;
		}
		File regionpath = new File(sequencePath + "/m1");
		region = "";
		if (regionpath.exists() && regionpath.isDirectory()) {
			String[] Dirnames = regionpath.list();
			if (Dirnames[0] != null) {
				region = Dirnames[0].toUpperCase();
			}

			if (region.equalsIgnoreCase("eu")) {

				// File regionxml=new
				// File(sequencePath+"/m1/eu/eu-regional.xml");
				Document document = null;
				try {
					document = InitialCallFunction(sequencePath
							+ "/m1/eu/eu-regional.xml");
				} catch (Exception e) {

					e.printStackTrace();
				}

				if (document != null) {
					NodeList nodeList = document
							.getElementsByTagName("procedure");

					if (nodeList != null && nodeList.getLength() != 0) {
						Node proc_node = nodeList.item(0);
						if (proc_node != null) {
							NamedNodeMap attrs = proc_node.getAttributes();
							if (attrs.getLength() != 0)
								procedure_type = attrs.getNamedItem("type")
										.getNodeValue().toString();

						}
					}

				} else {
					System.out.println("File not exist");
				}
			} else {
				System.out.println("Other Region");
			}

		}

		Document document = InitialCallFunction(fullIndexPath);

		Node xmlRoot = readIndexDocument(document);
		String fullM1XmlPath = "";

		if (displayM1.equalsIgnoreCase("all")) {
			NodeList nodeList = document
					.getElementsByTagName(REGIONAL_MODULE_NODE_NAME);
			Node leafNode = readChildNodeFromNodeList(nodeList, ECTD_LEAF_NODE);
			String m1XmlPath = readXmlPathFromLeaf(leafNode);
			fullM1XmlPath = fullIndexPath.substring(0, fullIndexPath
					.lastIndexOf("/"))
					+ "/" + m1XmlPath;
			Document doc = InitialCallFunction(fullM1XmlPath);
			Node xml = readRegionalDocument(doc,
					getRootNodeFromRegionalXml(doc));
			displayAdminInfoFromM1(xml);
			treeHtmlM1 = treeHtml.toString();
			treeHtml.replace(0, treeHtml.length(), "");

			readElementChildren(xmlRoot);

		} else if (displayM1.equalsIgnoreCase("yes")) {

			NodeList nodeList = document
					.getElementsByTagName(REGIONAL_MODULE_NODE_NAME);
			Node leafNode = readChildNodeFromNodeList(nodeList, ECTD_LEAF_NODE);
			String m1XmlPath = readXmlPathFromLeaf(leafNode);
			fullM1XmlPath = fullIndexPath.substring(0, fullIndexPath
					.lastIndexOf("/"))
					+ "/" + m1XmlPath;
			Document doc = InitialCallFunction(fullM1XmlPath);
			Node xml = readRegionalDocument(doc,
					getRootNodeFromRegionalXml(doc));
			displayAdminInfoFromM1(xml);
		} else {
			readElementChildren(xmlRoot);
		}
		// treeHtml.append("<ul><li class=folder>test</li></ul>");

		if (treeHtmlM1 != null && !treeHtmlM1.equals(""))
			treeHtml.insert(0, treeHtmlM1.toString());

		return SUCCESS;

	}

	public String readToolTip() {
		sequenceDirectoryName = FileManager.takeSequence(folderPath,
				"index.xml");
		if (sequenceNo.equalsIgnoreCase("")) {
			sequenceNo = sequenceDirectoryName.get(0);
			fullIndexPath = folderPath + "/" + sequenceNo + "/" + "index.xml";
			sequencePath = folderPath + "/" + sequenceNo;
		} else {
			fullIndexPath = folderPath + "/" + sequenceNo + "/" + "index.xml";
			sequencePath = folderPath + "/" + sequenceNo;
		}

		Document document = InitialCallFunction(fullIndexPath);

		if (displayM1.equalsIgnoreCase("yes")) {

			NodeList nodeList = document
					.getElementsByTagName(REGIONAL_MODULE_NODE_NAME);
			Node m1NodeFromIndex = readChildNodeFromNodeList(nodeList,
					REGIONAL_MODULE_NODE_NAME);
			String m1XmlPath = readXmlPathFromLeaf(m1NodeFromIndex);
			document = InitialCallFunction(sequencePath + "/" + m1XmlPath);
			Element nodeElement = document.getElementById(nodeId);
			readToolTipDataByNodeId(nodeElement);
		} else {
			Element nodeElement = document.getElementById(nodeId);
			readToolTipDataByNodeId(nodeElement);
		}

		return SUCCESS;
	}

	private String readXmlPathFromLeaf(Node leafNode) {
		String xmlPath = "";
		NamedNodeMap leafNodeAttr = leafNode.getAttributes();
		for (int k = 0; k < leafNodeAttr.getLength(); k++) {
			Node nodeAttr = leafNodeAttr.item(k);
			if (nodeAttr.getNodeName().equalsIgnoreCase(ECTD_XLINK_HREF_ATTR)) {
				xmlPath = nodeAttr.getNodeValue();
			}
		}
		m1XmlPathFolder = xmlPath.substring(0, xmlPath.lastIndexOf("/"));
		return xmlPath;
	}

	private Node readChildNodeFromNodeList(NodeList nodeList, String nodeName) {
		Node leafNode = null;
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node foundNode = nodeList.item(i);
			NodeList innerNodeList = foundNode.getChildNodes();
			for (int j = 0; j < innerNodeList.getLength(); j++) {
				leafNode = innerNodeList.item(j);
				if (leafNode.getNodeType() == Element.ELEMENT_NODE) {
					if (leafNode.getNodeName().equalsIgnoreCase(nodeName)) {
						return leafNode;
					}
				}
			}
		}
		return leafNode;
	}

	private Document InitialCallFunction(String xmlFilePath) {

		File currXmlFile = new File(xmlFilePath);
		Document document = null;
		if (currXmlFile.exists()) {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			documentBuilderFactory.setValidating(true);
			documentBuilderFactory.setIgnoringElementContentWhitespace(true);

			try {
				documentBuilder = documentBuilderFactory.newDocumentBuilder();
				documentBuilder.setErrorHandler(new org.xml.sax.ErrorHandler() {
					// Ignore the fatal errors
					public void fatalError(SAXParseException e)
							throws SAXException {
					}

					// Validation errors
					public void error(SAXParseException e)
							throws SAXParseException {
						System.out.println("Error at " + e.getLineNumber()
								+ " line.");
						System.out.println(e.getMessage());
						System.out.println(e.getSystemId());
						System.out.println("Parsing Error at line: "
								+ e.getLineNumber() + "::" + e.getMessage()
								+ e.getSystemId());
						// System.exit(0);
					}

					// Show warnings
					public void warning(SAXParseException err)
							throws SAXParseException {
						System.out.println("Warning: " + err.getMessage());
						// System.exit(0);
					}
				});
				document = documentBuilder.parse(currXmlFile);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("index.xml File is Not Found");
		}
		return document;
	}

	private Node readRegionalDocument(Document document, String regionalNode) {
		treeHtml = new StringBuffer();
		// here we have take main root node
		NodeList nodeList = document.getElementsByTagName(regionalNode);
		Node xmlRoot = nodeList.item(0);
		return xmlRoot;
	}

	private Node readIndexDocument(Document document) {

		treeHtml = new StringBuffer();
		// here we hava take main root node
		NodeList nodeList = document.getElementsByTagName(INDEX_XML_ROOT);
		Node xmlRoot = nodeList.item(0);

		return xmlRoot;

	}

	public void readToolTipDataByNodeId(Element elementNode) {
		test = new ArrayList<String>();
		String currSeq = "";
		String prevSeq = "";

		ectdNodeId = elementNode.getAttribute(ECTD_ID_ATTR);
		ectdAppVersion = elementNode.getAttribute(ECTD_APPVERSION_ATTR);
		ectdCheckSumType = elementNode.getAttribute(ECTD_CHECKSUM_TYPE_ATTR);
		ectdCheckSum = elementNode.getAttribute(ECTD_CHKSUM_ATTR);
		ectdXlinkHref = elementNode.getAttribute(ECTD_XLINK_HREF_ATTR);
		ectdOperation = elementNode.getAttribute(ECTD_OPERATION_ATTR);
		String replaceid = "";
		String replaceIndexPath = "";

		if (!ectdOperation.equalsIgnoreCase("new")) {
			String OldOperations = elementNode
					.getAttribute(ECTD_OPERATION_ATTR);
			String modifyatrr[] = elementNode.getAttribute(
					ECTD_MODIFIEDFILE_ATTR).split("#");

			while (OldOperations == null || OldOperations.equals("")
					|| !OldOperations.equals("new")) {
				if (modifyatrr.length != 2)
					break;
				replaceid = modifyatrr[1];

				if (displayM1.equalsIgnoreCase("yes"))
					prevSeq = modifyatrr[0].substring(9, 13);
				else
					prevSeq = modifyatrr[0].substring(3, 7);

				replaceIndexPath = modifyatrr[0];
				fullIndexPath = folderPath + replaceIndexPath.replace("..", "");

				Document document = InitialCallFunction(fullIndexPath);
				if (document == null)
					break;
				Element nodeElement = document.getElementById(replaceid);
				if (nodeElement == null)
					break;
				OldOperations = nodeElement.getAttribute(ECTD_OPERATION_ATTR);
				String xLink = nodeElement.getAttribute(ECTD_XLINK_HREF_ATTR);
				String id = nodeElement.getAttribute(ECTD_ID_ATTR);
				String checksum = nodeElement.getAttribute(ECTD_CHKSUM_ATTR);
				String checksumtype = nodeElement
						.getAttribute(ECTD_CHECKSUM_TYPE_ATTR);
				String tooltiptable = "<table>";
				tooltiptable += "<tr>";
				tooltiptable += "<td>ID</td><td>" + id + "</td>";
				tooltiptable += "</tr>";
				tooltiptable += "<tr>";
				tooltiptable += "<td>CheckSum</td><td>" + checksum + "</td>";
				tooltiptable += "</tr>";
				tooltiptable += "<tr>";
				tooltiptable += "<td>CheckSum Type</td><td>" + checksumtype
						+ "</td>";
				tooltiptable += "</tr>";
				tooltiptable += "</table>";
				String link = "";
				link = "<a class=ref"
						+ id
						+ " onmouseover='refseqdetail(this)' onclick='checkExt(this)' onmouseout='hiderefseqdetail(this)' target='_blank' href='openViewerFile_ex.do?nodeId=node-79&fileWithPath="
						+ folderPath
						+ replaceIndexPath.replace("..", "").substring(0, 6)
						+ xLink + "'>";
				if (displayM1.equalsIgnoreCase("yes")) {
					link = "<a class=ref"
							+ id
							+ " onmouseover='refseqdetail(this)' onclick='checkExt(this)' onmouseout='hiderefseqdetail(this)' target='_blank' href='openViewerFile_ex.do?nodeId=node-79&fileWithPath="
							+ folderPath + replaceIndexPath.substring(8, 20)
							+ xLink + "'>";
				}

				if (displayM1.equalsIgnoreCase("yes")) {
					link = link + replaceIndexPath.subSequence(9, 13) + "["
							+ OldOperations + "]"
							+ "</a><div class='relseqdetail' id=ref" + id + ">"
							+ tooltiptable + "</div>";

				} else {
					link = link + replaceIndexPath.subSequence(3, 7) + "["
							+ OldOperations + "]"
							+ "</a><div class='relseqdetail' id=ref" + id + ">"
							+ tooltiptable + "</div>";

				}

				allOperationDetails = allOperationDetails + ", " + link;

				// folderPath + replaceIndexPath.replace("..", "");

				// System.out.println("=>"+folderPath+replaceIndexPath.replace("..",
				// "").substring(0,6)+xLink);
				if (!OldOperations.equals("new")) {
					modifyatrr = nodeElement.getAttribute(
							ECTD_MODIFIEDFILE_ATTR).split("#");

					if (displayM1.equals("yes"))
						currSeq = modifyatrr[0].substring(9, 13);
					else
						currSeq = modifyatrr[0].substring(3, 7);

					if (prevSeq.equals(currSeq)) {
						break;
					}

					prevSeq = currSeq;

				}

			}
			if (allOperationDetails.length() >= 1)
				allOperationDetails = allOperationDetails.replaceFirst(",", "");
		}

	}

	private void readElementChildren(Node parentNode) {
		// Now this one is for all child
		NodeList xmlNodeChildren = parentNode.getChildNodes();
		if (xmlNodeChildren != null && xmlNodeChildren.getLength() > 0) {
			treeHtml.append("<ul>");
			for (int i = 0; i < xmlNodeChildren.getLength(); i++) {
				Node child = xmlNodeChildren.item(i);
				String nodeName = child.getNodeName();

				// checking whether it is m1 or not
				if (!nodeName.equalsIgnoreCase(REGIONAL_MODULE_NODE_NAME)
						&& (displayM1.equals("all") || displayM1.equals("no"))) {
					if (child.getNodeType() == Element.ELEMENT_NODE) {
						if (!nodeName.equalsIgnoreCase(ECTD_LEAF_NODE)) {
							treeHtml.append("<li class=folder>");
							treeHtml.append(child.getNodeName());
							NamedNodeMap childAttrList = child.getAttributes();
							String nodeAttrValue = "";
							if (childAttrList != null
									&& childAttrList.getLength() > 0) {

								for (int j = 0; j < childAttrList.getLength(); j++) {
									Node node = childAttrList.item(j);
									if (node.getNodeValue() != null
											&& !node.getNodeValue()
													.equalsIgnoreCase("")) {
										nodeAttrValue = nodeAttrValue
												+ node.getNodeValue() + ",";
									}
								}
								if (nodeAttrValue.length() > 0)
									nodeAttrValue = nodeAttrValue.substring(0,
											nodeAttrValue.length() - 1);
							}
							if (nodeAttrValue.length() > 0) {
								treeHtml
										.append("<label style='color: #202090;'>");
								treeHtml.append("[" + nodeAttrValue + "]");
								treeHtml.append("</label>");
							}
							readElementChildren(child);
							treeHtml.append("</li>");
						} else {
							if (!getTitleFromNode(child).equalsIgnoreCase("")) {
								String operation = "";
								String fileType = "";
								treeHtml.append("<li ");
								treeHtml
										.append("data=\"url: 'openViewerFile_ex.do?");
								NamedNodeMap xmlAttrs = child.getAttributes();
								for (int j = 0; j < xmlAttrs.getLength(); j++) {
									Node xmlNodeAttr = xmlAttrs.item(j);
									if (xmlNodeAttr.getNodeName()
											.equalsIgnoreCase("ID")) {
										treeHtml.append("nodeId=");
										treeHtml.append(xmlNodeAttr
												.getNodeValue());
										nodeId = xmlNodeAttr.getNodeValue();
									} else if (xmlNodeAttr.getNodeName()
											.equalsIgnoreCase(
													ECTD_XLINK_HREF_ATTR)
											&& !xmlNodeAttr.getNodeValue()
													.trim().equals("")) {
										treeHtml.append("&fileWithPath="
												+ sequencePath + "/");
										treeHtml.append(xmlNodeAttr
												.getNodeValue());
										fileType = xmlNodeAttr
												.getNodeValue()
												.substring(
														xmlNodeAttr
																.getNodeValue()
																.lastIndexOf(
																		".") + 1,
														xmlNodeAttr
																.getNodeValue()
																.length())
												.trim();
										treeHtml.append("'");
										treeHtml
												.append(" , action : 'openToolTip_ex.do?nodeId=");
										treeHtml.append(nodeId);
										treeHtml
												.append("&readToolTipData=yes&displayM1=no&sequenceNo="
														+ sequenceNo
														+ "&folderPath="
														+ folderPath);
										// treeHtml.append("/123456/0007/"+xmlNodeAttr.getNodeValue());
										treeHtml.append("'");
									} else if (xmlNodeAttr.getNodeName()
											.equalsIgnoreCase(
													ECTD_OPERATION_ATTR)) {
										// System.out.println("Operation="+xmlNodeAttr.getNodeValue());
										String operationValue = xmlNodeAttr
												.getNodeValue();
										if (operationValue
												.equalsIgnoreCase("append")) {
											operation = "APPEND";
										} else if (operationValue
												.equalsIgnoreCase("replace")) {
											operation = "REPLACE";
										} else if (operationValue
												.equalsIgnoreCase("delete")) {
											operation = "DELETED";
										} else if (operationValue
												.equalsIgnoreCase("new")) {
											operation = "NEW";
										}
									}
								}
								if (fileType.equals("")
										|| operation
												.equalsIgnoreCase("DELETED")) {

									treeHtml
											.append("', action : 'openToolTip_ex.do?nodeId=");
									treeHtml.append(nodeId);
									treeHtml
											.append("&readToolTipData=yes&displayM1=no&sequenceNo="
													+ sequenceNo
													+ "&folderPath="
													+ folderPath);
									treeHtml.append("'");

									treeHtml.append(", addClass: '" + fileType
											+ "' \">");
								} else
									treeHtml.append(", addClass: '" + fileType
											+ "' \">");

								treeHtml.append(getTitleFromNode(child));
								treeHtml.append("[");
								if (operation.equalsIgnoreCase("deleted"))
									treeHtml
											.append("<label style='color: red;'>");
								else
									treeHtml
											.append("<label style='color: green;'>");
								treeHtml.append(operation);
								treeHtml.append("</label>");
								treeHtml.append("]");
								treeHtml.append("</li>");
							}
						}
					}
				}
			}
			treeHtml.append("</ul>");
		}
	}

	private void displayAdminInfoFromM1(Node parentNode) {
		// Now this one is for all child
		NodeList xmlNodeChildren = parentNode.getChildNodes();

		if (xmlNodeChildren != null && xmlNodeChildren.getLength() > 0) {
			treeHtml.append("<ul>");
			for (int i = 0; i < xmlNodeChildren.getLength(); i++) {
				Node child = xmlNodeChildren.item(i);
				String nodeName = child.getNodeName();

				if (child.getNodeType() == Element.ELEMENT_NODE) {
					if (!nodeName.equalsIgnoreCase(ECTD_LEAF_NODE)) {
						treeHtml.append("<li class=folder>");
						treeHtml.append(child.getNodeName());
						NamedNodeMap childAttrList = child.getAttributes();
						String nodeAttrValue = "";
						if (childAttrList != null
								&& childAttrList.getLength() > 0) {

							for (int j = 0; j < childAttrList.getLength(); j++) {
								Node node = childAttrList.item(j);
								if (node.getNodeValue() != null
										&& !node.getNodeValue()
												.equalsIgnoreCase("")) {
									nodeAttrValue = nodeAttrValue
											+ node.getNodeValue() + ",";
								}
							}
							if (nodeAttrValue.length() > 0)
								nodeAttrValue = nodeAttrValue.substring(0,
										nodeAttrValue.length() - 1);
						}
						if (nodeAttrValue.length() > 0) {
							treeHtml.append("<label style='color: #202090;'>");
							treeHtml.append("[" + nodeAttrValue + "]");
							treeHtml.append("</label>");

						}
						NodeList nodeList = child.getChildNodes();
						if (nodeList.getLength() == 1) {
							Node node = nodeList.item(0);
							if (node.getNodeType() == Element.TEXT_NODE)
								treeHtml
										.append("[<label style='color: green;'>"
												+ node.getNodeValue()
												+ "</label>]");
						}
						displayAdminInfoFromM1(child);
						treeHtml.append("</li>");
					} else {
						if (!getTitleFromNode(child).equalsIgnoreCase("")) {
							String operation = "";
							String fileType = "";
							treeHtml.append("<li ");
							treeHtml
									.append("data=\"url: 'openViewerFile_ex.do?");
							NamedNodeMap xmlAttrs = child.getAttributes();
							for (int j = 0; j < xmlAttrs.getLength(); j++) {
								Node xmlNodeAttr = xmlAttrs.item(j);
								if (xmlNodeAttr.getNodeName().equalsIgnoreCase(
										"ID")) {
									treeHtml.append("nodeId=");
									treeHtml.append(xmlNodeAttr.getNodeValue());
									nodeId = xmlNodeAttr.getNodeValue();
								} else if (xmlNodeAttr.getNodeName()
										.equalsIgnoreCase(ECTD_XLINK_HREF_ATTR)
										&& !xmlNodeAttr.getNodeValue().trim()
												.equals("")) {
									treeHtml.append("&fileWithPath="
											+ sequencePath + "/"
											+ m1XmlPathFolder + "/");
									treeHtml.append(xmlNodeAttr.getNodeValue());
									fileType = xmlNodeAttr
											.getNodeValue()
											.substring(
													xmlNodeAttr.getNodeValue()
															.lastIndexOf(".") + 1,
													xmlNodeAttr.getNodeValue()
															.length()).trim();
									treeHtml.append("'");
									treeHtml
											.append(" , action : 'openToolTip_ex.do?nodeId=");
									treeHtml.append(nodeId);
									treeHtml
											.append("&readToolTipData=yes&displayM1=yes&sequenceNo="
													+ sequenceNo
													+ "&folderPath="
													+ folderPath);
									// treeHtml.append("/123456/0007/"+xmlNodeAttr.getNodeValue());
									treeHtml.append("'");
								} else if (xmlNodeAttr.getNodeName()
										.equalsIgnoreCase(ECTD_OPERATION_ATTR)) {
									// System.out.println("Operation="+xmlNodeAttr.getNodeValue());
									String operationValue = xmlNodeAttr
											.getNodeValue();
									if (operationValue
											.equalsIgnoreCase("append")) {
										operation = "APPEND";
									} else if (operationValue
											.equalsIgnoreCase("replace")) {
										operation = "REPLACE";
									} else if (operationValue
											.equalsIgnoreCase("delete")) {
										operation = "DELETED";
									} else if (operationValue
											.equalsIgnoreCase("new")) {
										operation = "NEW";
									}
								}
							}
							if (fileType.equals("")
									|| operation.equalsIgnoreCase("DELETED")) {

								treeHtml
										.append("', action : 'openToolTip_ex.do?nodeId=");
								treeHtml.append(nodeId);
								treeHtml
										.append("&readToolTipData=yes&displayM1=yes&sequenceNo="
												+ sequenceNo
												+ "&folderPath="
												+ folderPath);
								treeHtml.append("'");

								treeHtml.append(", addClass: '" + fileType
										+ "' \">");
							} else
								treeHtml.append(", addClass: '" + fileType
										+ "' \">");

							treeHtml.append(getTitleFromNode(child));
							treeHtml.append("[");
							if (operation.equalsIgnoreCase("deleted"))
								treeHtml.append("<label style='color: red;'>");
							else
								treeHtml
										.append("<label style='color: green;'>");
							treeHtml.append(operation);
							treeHtml.append("</label>");
							treeHtml.append("]");
							treeHtml.append("</li>");
						}
					}
				}
			}
			treeHtml.append("</ul>");
		}
	}

	private String getTitleFromNode(Node leafNode) {
		// Get Leaf's title value
		String nodeTitle = "";
		NodeList allLeafChildren = leafNode.getChildNodes();
		for (int k = 0; k < allLeafChildren.getLength(); k++) {
			Node leafChild = allLeafChildren.item(k);
			if (leafChild.getNodeName().trim()
					.equalsIgnoreCase(ECTD_TITLE_NODE)) {
				Node titleNode = leafChild.getFirstChild();
				if (titleNode != null
						&& titleNode.getNodeType() == Element.TEXT_NODE) {
					nodeTitle = titleNode.getNodeValue().trim();
				}
			}
		}
		return nodeTitle;
	}

	private String getRootNodeFromRegionalXml(Document doc) {
		String rootNodeValue = "";
		NodeList nodeList = doc.getChildNodes();
		for (int nodeListIndex = 0; nodeListIndex < nodeList.getLength(); nodeListIndex++) {
			Node node = nodeList.item(nodeListIndex);
			if (node.getNodeType() == Element.ELEMENT_NODE) {
				rootNodeValue = node.getNodeName();
				return rootNodeValue;
			}
		}
		return rootNodeValue;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public static void main(String argvp[]) {
		// XmlReadAction xml = new XmlReadAction();
		// String
		// m1XmlPath="/myproject/Temp/Trimetazidine-Sandoz/0000/m1/eu/eu-regional.xml";
		/*
		 * try{ SmbFile fis = new
		 * SmbFile("smb://ECOM;sarjen:sarjen123@sspl10/shared/123456/0000/index.xml"
		 * ); String str[]=fis.list(); for(int j=0;j<str.length;j++){
		 * System.out.println(str[j]); } InputStream
		 * newFis=fis.getInputStream(); xm.readFile(newFis); }catch(Exception
		 * e){ e.printStackTrace(); } //xm.readFile(fis); //xm.execute();
		 */
	}
}