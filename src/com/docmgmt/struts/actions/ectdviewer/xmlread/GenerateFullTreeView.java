package com.docmgmt.struts.actions.ectdviewer.xmlread;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.opensymphony.xwork2.ActionSupport;

public class GenerateFullTreeView extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final short INDEX_READ_SUCCESSFULLY = 0;
	public static final short FILE_NOT_FOUND = 1;
	public static final short INVALID_XML = 2;

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
	public static final String ECTD_OPERATION_ATTR_NEW = "new";
	public static final String ECTD_OPERATION_ATTR_REPLACE = "replace";
	public static final String ECTD_OPERATION_ATTR_APPEND = "append";
	public static final String ECTD_OPERATION_ATTR_DELETE = "delete";
	public static final String ECTD_EU_ENVELOPE_ROOT = "eu-envelope";
	public static final String ECTD_US_ENVELOPE_ROOT = "admin";

	public String mainProjectPath = "";
	public String nodeId = "";
	public String fileWithPath = "";
	public String readToolTipData = "no";
	public String m1XmlFilePath = "";
	public String ectdNodeId = "", ectdAppVersion = "", ectdCheckSumType = "",
			ectdCheckSum = "", ectdXlinkHref = "", ectdOperation = "";
	DocumentBuilder documentBuilder;
	public String uploadFileFileName;
	public String uploadFileContentType;
	int currNodeId;
	public String deleteFilePath = "";
	private String m1XmlPathFolder = "";
	public StringBuffer treeHtml;

	public StringBuffer envalopeTreeHtml;
	public String treeHtmlM1;
	public String folderPath;
	public String displayM1 = "no";
	public ArrayList<String> sequenceDirectoryName;
	public String sequenceNo = "";
	private String fullIndexPath = "";
	private String sequencePath = "";

	public ArrayList<String> added_specificnode = new ArrayList<String>();
	public NodeList envalopeNodes;

	public String regionName = "";

	public Node Node_DifferentAt = null;
	public int node_DifferntIndex = 0;

	public String sequenceParsingErrorList = "";

	public Document m2m5Document;

	public String execute() {

		// allRegionalXmlPath = new ArrayList<String>();
		sequenceDirectoryName = FileManager.takeSequence(folderPath,
				"index.xml");

		Collections.sort(sequenceDirectoryName);
		if (sequenceDirectoryName.size() > 0) {
			sequenceNo = sequenceDirectoryName.get(0);
			fullIndexPath = folderPath + "/" + sequenceNo + "/" + "index.xml";
			sequencePath = folderPath + "/" + sequenceNo;

			Document document = InitialCallFunction(fullIndexPath);
			String fullM1XmlPath = "";
			String newfolderPath = "";
			if (displayM1.equalsIgnoreCase("all")) {
				NodeList nodeList = document
						.getElementsByTagName(REGIONAL_MODULE_NODE_NAME);
				Node leafNode = readChildNodeFromNodeList(nodeList,
						ECTD_LEAF_NODE);
				String m1XmlPath = readXmlPathFromLeaf(leafNode);
				fullM1XmlPath = fullIndexPath.substring(0, fullIndexPath
						.lastIndexOf("/"))
						+ "/" + m1XmlPath;
				regionName = m1XmlPath.substring(3, 5).toLowerCase();

				Document firstSeqM1Document = InitialCallFunction(fullM1XmlPath);
				NodeList firstDocNodeList = firstSeqM1Document
						.getElementsByTagName(ECTD_LEAF_NODE);
				addAttributeInNodeList(firstDocNodeList, "sequence", "0000");
				addAttributeInNodeList(firstDocNodeList, "m1XmlPath", m1XmlPath);
				addAttributeInNodeList(firstDocNodeList, "sequencePath",
						sequencePath);
				ArrayList<ArrayList<Node>> allLeafNodeData;
				if (sequenceDirectoryName.size() >= 1) {
					nodeList = getAllLeafNodeByDocument(document);
					addAttributeInNodeList(nodeList, "sequence",
							sequenceDirectoryName.get(0));
				}
				if (sequenceDirectoryName.size() > 1) {
					for (int i = 1; i < sequenceDirectoryName.size(); i++) {
						newfolderPath = fullIndexPath.substring(0,
								fullIndexPath.lastIndexOf("/"));
						fullIndexPath = newfolderPath.substring(0,
								newfolderPath.lastIndexOf("/"))
								+ "/"
								+ sequenceDirectoryName.get(i)
								+ "/"
								+ "index.xml";
						sequencePath = folderPath + "/"
								+ sequenceDirectoryName.get(i);
						Document newDocument = InitialCallFunction(fullIndexPath);
						NodeList newNodeList = newDocument
								.getElementsByTagName(REGIONAL_MODULE_NODE_NAME);
						Node newLeafNode = readChildNodeFromNodeList(
								newNodeList, ECTD_LEAF_NODE);
						String newM1XmlPath = readXmlPathFromLeaf(newLeafNode);
						fullM1XmlPath = fullIndexPath.substring(0,
								fullIndexPath.lastIndexOf("/"))
								+ "/" + newM1XmlPath;
						NodeList nodeListForM1 = getAllLeafNodeByXmlPath(fullM1XmlPath);
						addAttributeInNodeList(nodeListForM1, "sequence",
								sequenceDirectoryName.get(i));
						addAttributeInNodeList(nodeListForM1, "m1XmlPath",
								newM1XmlPath);
						addAttributeInNodeList(nodeListForM1, "sequencePath",
								sequencePath);
						allLeafNodeData = getNodeHierarchy(nodeListForM1,
								sequenceDirectoryName.get(i));
						if (i == sequenceDirectoryName.size() - 1) {
							String regionalRootNode = "";
							String envalopeRoot = "";
							if (regionName.equals("eu")) {
								envalopeRoot = ECTD_EU_ENVELOPE_ROOT;
								regionalRootNode = EU_REGIONAL_ROOT_NODE;
							} else if (regionName.equals("us")) {
								envalopeRoot = ECTD_US_ENVELOPE_ROOT;
								regionalRootNode = US_REGIONAL_ROOT_NODE;
							}
							NodeList firstDocRoot = firstSeqM1Document
									.getElementsByTagName(regionalRootNode);
							try {
								for (int m = 0; m < firstDocRoot.getLength(); m++) {
									Node allRegNode = firstDocRoot.item(m);
									NodeList allRegChild = allRegNode
											.getChildNodes();
									for (int r = 0; r < allRegChild.getLength(); r++) {
										Node regNode = allRegChild.item(r);
										if (regNode.getNodeName().equals(
												envalopeRoot)) {
											regNode.getParentNode()
													.removeChild(regNode);
											break;
											// firstSeqM1Document.removeChild(allRegNode);
										}
									}
								}
								Document LastM1Document = InitialCallFunction(fullM1XmlPath);
								NodeList newEnvNodeList = LastM1Document
										.getElementsByTagName(envalopeRoot);
								for (int l = 0; l < newEnvNodeList.getLength(); l++) {
									Node enNode = newEnvNodeList.item(l);
									if (enNode.getNodeName().equals(
											envalopeRoot)) {
										envalopeTreeHtml = new StringBuffer();
										displayLatestEnvelope(enNode);
										break;
									}
								}
							} catch (Exception er) {
								er.printStackTrace();
							}
						}
						createNetViewDocumentM1(firstSeqM1Document,
								allLeafNodeData);
					}
				} else {
					Node xml = readRegionalDocument(firstSeqM1Document,
							getRootNodeFromRegionalXml(firstSeqM1Document));
					displayAdminInfoFromM1(xml);
				}
				treeHtmlM1 = treeHtml.toString();
				treeHtml.replace(0, treeHtml.length(), "");
				generateFullTree();
			} else if (displayM1.equalsIgnoreCase("yes")) {
				added_specificnode = new ArrayList<String>();
				NodeList nodeList = document
						.getElementsByTagName(REGIONAL_MODULE_NODE_NAME);
				Node leafNode = readChildNodeFromNodeList(nodeList,
						ECTD_LEAF_NODE);
				String m1XmlPath = readXmlPathFromLeaf(leafNode);
				fullM1XmlPath = fullIndexPath.substring(0, fullIndexPath
						.lastIndexOf("/"))
						+ "/" + m1XmlPath;
				regionName = m1XmlPath.substring(3, 5).toLowerCase();
				Document firstSeqM1Document = InitialCallFunction(fullM1XmlPath);
				NodeList firstDocNodeList = firstSeqM1Document
						.getElementsByTagName(ECTD_LEAF_NODE);
				addAttributeInNodeList(firstDocNodeList, "sequence", "0000");
				addAttributeInNodeList(firstDocNodeList, "m1XmlPath", m1XmlPath);
				addAttributeInNodeList(firstDocNodeList, "sequencePath",
						sequencePath);
				ArrayList<ArrayList<Node>> allLeafNodeData;
				if (sequenceDirectoryName.size() >= 1) {
					nodeList = getAllLeafNodeByDocument(document);
					addAttributeInNodeList(nodeList, "sequence",
							sequenceDirectoryName.get(0));
				}
				if (sequenceDirectoryName.size() > 1) {
					for (int i = 1; i < sequenceDirectoryName.size(); i++) {
						newfolderPath = fullIndexPath.substring(0,
								fullIndexPath.lastIndexOf("/"));
						fullIndexPath = newfolderPath.substring(0,
								newfolderPath.lastIndexOf("/"))
								+ "/"
								+ sequenceDirectoryName.get(i)
								+ "/"
								+ "index.xml";
						sequencePath = folderPath + "/"
								+ sequenceDirectoryName.get(i);
						Document newDocument = InitialCallFunction(fullIndexPath);
						NodeList newNodeList = newDocument
								.getElementsByTagName(REGIONAL_MODULE_NODE_NAME);
						Node newLeafNode = readChildNodeFromNodeList(
								newNodeList, ECTD_LEAF_NODE);
						String newM1XmlPath = readXmlPathFromLeaf(newLeafNode);
						fullM1XmlPath = fullIndexPath.substring(0,
								fullIndexPath.lastIndexOf("/"))
								+ "/" + newM1XmlPath;
						NodeList nodeListForM1 = getAllLeafNodeByXmlPath(fullM1XmlPath);
						addAttributeInNodeList(nodeListForM1, "sequence",
								sequenceDirectoryName.get(i));
						addAttributeInNodeList(nodeListForM1, "m1XmlPath",
								newM1XmlPath);
						addAttributeInNodeList(nodeListForM1, "sequencePath",
								sequencePath);
						allLeafNodeData = getNodeHierarchy(nodeListForM1,
								sequenceDirectoryName.get(i));
						if (i == sequenceDirectoryName.size() - 1) {
							String regionalRootNode = "";
							String envalopeRoot = "";
							if (regionName.equals("eu")) {
								envalopeRoot = ECTD_EU_ENVELOPE_ROOT;
								regionalRootNode = EU_REGIONAL_ROOT_NODE;
							} else if (regionName.equals("us")) {
								envalopeRoot = ECTD_US_ENVELOPE_ROOT;
								regionalRootNode = US_REGIONAL_ROOT_NODE;
							}
							NodeList firstDocRoot = firstSeqM1Document
									.getElementsByTagName(regionalRootNode);
							try {
								for (int m = 0; m < firstDocRoot.getLength(); m++) {
									Node allRegNode = firstDocRoot.item(m);
									NodeList allRegChild = allRegNode
											.getChildNodes();
									for (int r = 0; r < allRegChild.getLength(); r++) {
										Node regNode = allRegChild.item(r);
										if (regNode.getNodeName().equals(
												envalopeRoot)) {
											regNode.getParentNode()
													.removeChild(regNode);
											break;
										}
									}
								}
								Document LastM1Document = InitialCallFunction(fullM1XmlPath);
								NodeList newEnvNodeList = LastM1Document
										.getElementsByTagName(envalopeRoot);
								for (int l = 0; l < newEnvNodeList.getLength(); l++) {
									Node enNode = newEnvNodeList.item(l);
									if (enNode.getNodeName().equals(
											envalopeRoot)) {
										envalopeTreeHtml = new StringBuffer();									
										displayLatestEnvelope(enNode);
										break;
									}
								}
							} catch (Exception er) {
								er.printStackTrace();
							}
						}
						createNetViewDocumentM1(firstSeqM1Document,
								allLeafNodeData);
					}
				} else {
					Node xml = readRegionalDocument(firstSeqM1Document,
							getRootNodeFromRegionalXml(firstSeqM1Document));
					displayAdminInfoFromM1(xml);
				}
			} else
				generateFullTree();
		} else {
			System.out.println("No Sequence Found");
		}
		if (treeHtmlM1 != null && !treeHtmlM1.equals(""))
			treeHtml.insert(0, treeHtmlM1.toString());
		if (envalopeTreeHtml != null && !envalopeTreeHtml.equals("")) {
			envalopeTreeHtml.insert(0, "<ul><li class=folder>" + regionName
					+ "-envelope");
			envalopeTreeHtml.append("</li></ul>");
			treeHtml.insert(0, envalopeTreeHtml.toString());
		}
//		System.out.println("Tree HTMl=" + treeHtml.toString());
//		System.out.println("Errors=" + sequenceParsingErrorList);
		return SUCCESS;
	}
	// getting merged documents for m1 and M2toM5 for manual project
	public ArrayList<Document> getMergedXMLDocument(String dosierPath) {
		folderPath = dosierPath;
		ArrayList<Document> mergedXMLdocuments = new ArrayList<Document>();
		sequenceDirectoryName = FileManager.takeSequence(folderPath,
				"index.xml");
		Collections.sort(sequenceDirectoryName);
		if (sequenceDirectoryName.size() > 0) {
			sequenceNo = sequenceDirectoryName.get(0);
			fullIndexPath = folderPath + "/" + sequenceNo + "/" + "index.xml";
			sequencePath = folderPath + "/" + sequenceNo;
			Document document = InitialCallFunction(fullIndexPath);
			String fullM1XmlPath = "";
			String newfolderPath = "";
			// added_specificnode = new ArrayList<String>();
			NodeList nodeList = document
					.getElementsByTagName(REGIONAL_MODULE_NODE_NAME);
			Node leafNode = readChildNodeFromNodeList(nodeList, ECTD_LEAF_NODE);
			String m1XmlPath = readXmlPathFromLeaf(leafNode);
			fullM1XmlPath = fullIndexPath.substring(0, fullIndexPath
					.lastIndexOf("/"))
					+ "/" + m1XmlPath;
			// regionName = m1XmlPath.substring(3, 5).toLowerCase();
			Document firstSeqM1Document = InitialCallFunction(fullM1XmlPath);
			NodeList firstDocNodeList = firstSeqM1Document
					.getElementsByTagName(ECTD_LEAF_NODE);
			addAttributeInNodeList(firstDocNodeList, "sequence", "0000");
			addAttributeInNodeList(firstDocNodeList, "m1XmlPath", m1XmlPath);
			addAttributeInNodeList(firstDocNodeList, "sequencePath",
					sequencePath);
			ArrayList<ArrayList<Node>> allLeafNodeData;
			if (sequenceDirectoryName.size() >= 1) {
				nodeList = getAllLeafNodeByDocument(document);
				addAttributeInNodeList(nodeList, "sequence",
						sequenceDirectoryName.get(0));
			}
			if (sequenceDirectoryName.size() > 1) {
				for (int i = 1; i < sequenceDirectoryName.size(); i++) {
					newfolderPath = fullIndexPath.substring(0, fullIndexPath
							.lastIndexOf("/"));
					fullIndexPath = newfolderPath.substring(0, newfolderPath
							.lastIndexOf("/"))
							+ "/"
							+ sequenceDirectoryName.get(i)
							+ "/"
							+ "index.xml";
					sequencePath = folderPath + "/"
							+ sequenceDirectoryName.get(i);
					Document newDocument = InitialCallFunction(fullIndexPath);
					NodeList newNodeList = newDocument
							.getElementsByTagName(REGIONAL_MODULE_NODE_NAME);
					Node newLeafNode = readChildNodeFromNodeList(newNodeList,
							ECTD_LEAF_NODE);
					String newM1XmlPath = readXmlPathFromLeaf(newLeafNode);
					fullM1XmlPath = fullIndexPath.substring(0, fullIndexPath
							.lastIndexOf("/"))
							+ "/" + newM1XmlPath;
					NodeList nodeListForM1 = getAllLeafNodeByXmlPath(fullM1XmlPath);
					addAttributeInNodeList(nodeListForM1, "sequence",
							sequenceDirectoryName.get(i));
					addAttributeInNodeList(nodeListForM1, "m1XmlPath",
							newM1XmlPath);
					addAttributeInNodeList(nodeListForM1, "sequencePath",
							sequencePath);
					allLeafNodeData = getNodeHierarchy(nodeListForM1,
							sequenceDirectoryName.get(i));
					createNetViewDocumentM1(firstSeqM1Document, allLeafNodeData);
				}
			} else {
				Node xml = readRegionalDocument(firstSeqM1Document,
						getRootNodeFromRegionalXml(firstSeqM1Document));
				displayAdminInfoFromM1(xml);
			}
			generateFullTree();
			mergedXMLdocuments.add(firstSeqM1Document);
			mergedXMLdocuments.add(m2m5Document);
		} else {
			System.out.println("No Sequence Found");
		}
		return mergedXMLdocuments;
	}
	private void createNetViewDocumentM1(Document firstDocument,
			ArrayList<ArrayList<Node>> allLeafNodeData) {
		// one iteration for one leaf node
		for (int leafIndex = 0; leafIndex < allLeafNodeData.size(); leafIndex++) {
			ArrayList<Node> leafNodeWithParents = allLeafNodeData
					.get(leafIndex);
			Node leafNode = leafNodeWithParents.get(0);
			NamedNodeMap attributes = leafNode.getAttributes();
			Node operationAttr = attributes.getNamedItem(ECTD_OPERATION_ATTR);
			String referenceLeafId = getReferenceIdFromModifiedFileAttr(leafNode);
			if (operationAttr.getNodeValue().equalsIgnoreCase(
					ECTD_OPERATION_ATTR_NEW)) {
				try {
					for (int parentHierarchiIndex = 1; parentHierarchiIndex < leafNodeWithParents
							.size(); parentHierarchiIndex++) {
						Node node = leafNodeWithParents
								.get(parentHierarchiIndex);
						NodeList nodeList = firstDocument
								.getElementsByTagName(node.getNodeName());
						boolean isNodeAdded = false;
						if (nodeList.getLength() == 0) {
							// addNotExistNode(node.getParentNode(),firstDocument);
							System.out.println("New Node Not Found");
							continue;
						} else if (nodeList.getLength() == 1) {
							Node currentParentNode = nodeList.item(0);
							Node newImpNode = firstDocument.importNode(
									leafNodeWithParents
											.get(parentHierarchiIndex - 1),
									true);
							boolean nodeAddFlag = checkAttr(currentParentNode,
									node);
							boolean advanceattr = advanceAttrCheck(node,
									currentParentNode);
							if (nodeAddFlag && advanceattr) {
								Element newNode_Element = (Element) newImpNode;
								NodeList allLeafOfNewNode = newNode_Element
										.getElementsByTagName("leaf");
								if (allLeafOfNewNode.getLength() >= 2) {
									int checkval = 1;
									while (allLeafOfNewNode.getLength() != checkval) {
										Node ch = allLeafOfNewNode
												.item(checkval);
										NamedNodeMap chAttr = ch
												.getAttributes();
										String operation = chAttr.getNamedItem(
												"operation").getNodeValue();
										if (!operation.equals("new")) {
											checkval++;
											continue;
										}
										if (ch != null
												&& operation.equals("new")) {
											ch.getParentNode().removeChild(ch);
										}
									}
								}
								currentParentNode.appendChild(newImpNode);
								isNodeAdded = true;
								break;
							}
						} else {
							// NodeList
							// nodeListCompare=firstDocument.getElementsByTagName(node.getNodeName());
							isNodeAdded = false;
							for (int nodeListCompateindex = 0; nodeListCompateindex < nodeList
									.getLength(); nodeListCompateindex++) {
								try {
									Node nodeCompare = nodeList
											.item(nodeListCompateindex);
									boolean nodeAddFlag = checkAttr(
											nodeCompare, node);
									boolean advanceattr = advanceAttrCheck(
											node, nodeCompare);
									if (nodeAddFlag && advanceattr) {
										Node supParentNode = leafNodeWithParents
												.get(parentHierarchiIndex + 1);
										Node newImpNode = firstDocument
												.importNode(
														leafNodeWithParents
																.get(parentHierarchiIndex - 1),
														true);
										if (supParentNode
												.getNodeName()
												.equalsIgnoreCase(
														nodeCompare
																.getParentNode()
																.getNodeName())) {
											Element newNode_Element = (Element) newImpNode;
											NodeList allLeafOfNewNode = newNode_Element
													.getElementsByTagName("leaf");
											if (allLeafOfNewNode.getLength() >= 2) {
												int checkval = 1;
												while (allLeafOfNewNode
														.getLength() != checkval) {
													Node ch = allLeafOfNewNode
															.item(checkval);
													NamedNodeMap chAttr = ch
															.getAttributes();
													String operation = chAttr
															.getNamedItem(
																	"operation")
															.getNodeValue();
													if (!operation
															.equals("new")) {
														checkval++;
														continue;
													}
													if (ch != null
															&& operation
																	.equals("new")) {
														ch
																.getParentNode()
																.removeChild(ch);
													}
												}
											}
											nodeCompare.appendChild(newImpNode);
											isNodeAdded = true;
											break;
										}
									}
								} catch (Exception e) {
									System.err.println("Error in "
											+ node.getNodeName() + " node");
									e.printStackTrace();
								}
							}
							if (isNodeAdded)
								break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (operationAttr.getNodeValue().equalsIgnoreCase(
					ECTD_OPERATION_ATTR_REPLACE)) {
				for (int parentHierarchiIndex = 1; parentHierarchiIndex < leafNodeWithParents
						.size(); parentHierarchiIndex++) {
					boolean isNodeReplaced = false;
					Node node = leafNodeWithParents.get(parentHierarchiIndex);
					NodeList nodeList = firstDocument.getElementsByTagName(node
							.getNodeName());
					if (nodeList.getLength() == 0) {
						System.out.println("NotFound-" + node.getNodeName());
						continue;
					} else if (nodeList.getLength() == 1) {
						Node currentParentNode = nodeList.item(0);
						Node newImpNode = firstDocument.importNode(
								leafNodeWithParents
										.get(parentHierarchiIndex - 1), true);
						Element newImpNode_element = (Element) newImpNode;
						String newImpNode_element_modifiedFile = newImpNode_element
								.getAttribute(ECTD_MODIFIEDFILE_ATTR);
						String refSequence = newImpNode_element_modifiedFile
								.substring(9, 13);
						boolean nodeAddFlag = checkAttr(currentParentNode, node);
						nodeAddFlag = true;
						if (nodeAddFlag) {
							NodeList currentChildNodeList = currentParentNode
									.getChildNodes();
							for (int currentChildNodeListIndex = 0; currentChildNodeListIndex < currentChildNodeList
									.getLength(); currentChildNodeListIndex++) {
								Element currentChildNode = (Element) currentChildNodeList
										.item(currentChildNodeListIndex);
								String leafId = currentChildNode
										.getAttribute(ECTD_ID_ATTR);
								String sequence = currentChildNode
										.getAttribute("sequence");
								if (!refSequence.equals(sequence)) {
									continue;
								}
								if (leafId != null
										&& leafId.equals(referenceLeafId)) {
									currentParentNode.appendChild(newImpNode);
									currentParentNode
											.removeChild(currentChildNode);
									isNodeReplaced = true;
									break;
								}
							}
						}
						if (isNodeReplaced)
							break;
					} else {
						Node supParentNode = leafNodeWithParents
								.get(parentHierarchiIndex + 1);
						Node newImpNode = firstDocument.importNode(
								leafNodeWithParents
										.get(parentHierarchiIndex - 1), true);
						Element newImpNode_element = (Element) newImpNode;
						String newImpNode_element_modifiedFile = newImpNode_element
								.getAttribute(ECTD_MODIFIEDFILE_ATTR);
						String refSequence = newImpNode_element_modifiedFile
								.substring(9, 13);
						for (int nodeListCompateindex = 0; nodeListCompateindex < nodeList
								.getLength(); nodeListCompateindex++) {
							Node currentParentNode = nodeList
									.item(nodeListCompateindex);
							// boolean nodeAddFlag =
							// checkAttr(currentParentNode,
							// node);
							boolean nodeAddFlag = true;
							if (nodeAddFlag) {
								NodeList currentChildNodeList = currentParentNode
										.getChildNodes();
								for (int currentChildNodeListIndex = 0; currentChildNodeListIndex < currentChildNodeList
										.getLength(); currentChildNodeListIndex++) {
									Element currentChildNode = (Element) currentChildNodeList
											.item(currentChildNodeListIndex);
									String leafId = currentChildNode
											.getAttribute(ECTD_ID_ATTR);
									String sequence = currentChildNode
											.getAttribute("sequence");
									if (!refSequence.equals(sequence)) {
										continue;
									}
									if (leafId != null
											&& leafId.equals(referenceLeafId)
											&& supParentNode
													.getNodeName()
													.equalsIgnoreCase(
															currentParentNode
																	.getParentNode()
																	.getNodeName())) {
										try {
											removeNodeAttr(currentChildNode);
											NamedNodeMap newleafAttr = newImpNode
													.getAttributes();
											for (int attrIndex = 0; attrIndex < newleafAttr
													.getLength(); attrIndex++) {
												Node newNodeAttr = newleafAttr
														.item(attrIndex);
												currentChildNode
														.setAttribute(
																newNodeAttr
																		.getNodeName(),
																newNodeAttr
																		.getNodeValue());
											}
											if (newImpNode.hasChildNodes()
													&& currentChildNode
															.hasChildNodes()) // changing
												// title
												// node
												currentChildNode
														.replaceChild(
																newImpNode
																		.getFirstChild(),
																currentChildNode
																		.getFirstChild());
											isNodeReplaced = true;
										} catch (Exception e) {
											System.out
													.println("Error Found At Node-"
															+ node
																	.getNodeName());
											e.printStackTrace();
										}
										break;
									}
								}
							}
						}
					}
					break;
				}
			} else if (operationAttr.getNodeValue().equalsIgnoreCase(
					ECTD_OPERATION_ATTR_APPEND)) {
				for (int parentHierarchiIndex = 1; parentHierarchiIndex < leafNodeWithParents
						.size(); parentHierarchiIndex++) {
					boolean isNodeAppend = false;
					Node node = leafNodeWithParents.get(parentHierarchiIndex);
					NodeList nodeList = firstDocument.getElementsByTagName(node
							.getNodeName());
					if (nodeList.getLength() == 0) {
						System.out.println("NotFound-" + node.getNodeName());
						continue;
					} else if (nodeList.getLength() == 1) {
						Node currentParentNode = nodeList.item(0);
						Node newImpNode = firstDocument.importNode(
								leafNodeWithParents
										.get(parentHierarchiIndex - 1), true);
						Element newImpNode_element = (Element) newImpNode;
						String newImpNode_element_modifiedFile = newImpNode_element
								.getAttribute(ECTD_MODIFIEDFILE_ATTR);
						String refSequence = newImpNode_element_modifiedFile
								.substring(9, 13);
						boolean nodeAddFlag = checkAttr(currentParentNode, node);
						nodeAddFlag = true;
						if (nodeAddFlag) {
							NodeList currentChildNodeList = currentParentNode
									.getChildNodes();
							for (int currentChildNodeListIndex = 0; currentChildNodeListIndex < currentChildNodeList
									.getLength(); currentChildNodeListIndex++) {
								Element currentChildNode = (Element) currentChildNodeList
										.item(currentChildNodeListIndex);
								String leafId = currentChildNode
										.getAttribute(ECTD_ID_ATTR);
								String sequence = currentChildNode
										.getAttribute("sequence");
								if (!refSequence.equals(sequence)) {
									continue;
								}
								if (leafId != null
										&& leafId.equals(referenceLeafId)) {
									currentParentNode.appendChild(newImpNode);
									currentParentNode
											.removeChild(currentChildNode);
									isNodeAppend = true;
									break;
								}
							}
						}
						if (isNodeAppend)
							break;
					} else {
						Node supParentNode = leafNodeWithParents
								.get(parentHierarchiIndex + 1);
						Node newImpNode = firstDocument.importNode(
								leafNodeWithParents
										.get(parentHierarchiIndex - 1), true);
						Element newImpNode_element = (Element) newImpNode;
						String newImpNode_element_modifiedFile = newImpNode_element
								.getAttribute(ECTD_MODIFIEDFILE_ATTR);
						String refSequence = newImpNode_element_modifiedFile
								.substring(9, 13);
						for (int nodeListCompateindex = 0; nodeListCompateindex < nodeList
								.getLength(); nodeListCompateindex++) {
							Node currentParentNode = nodeList
									.item(nodeListCompateindex);
							// boolean nodeAddFlag =
							// checkAttr(currentParentNode,
							// node);
							boolean nodeAddFlag = true;
							if (nodeAddFlag) {
								NodeList currentChildNodeList = currentParentNode
										.getChildNodes();
								for (int currentChildNodeListIndex = 0; currentChildNodeListIndex < currentChildNodeList
										.getLength(); currentChildNodeListIndex++) {
									Element currentChildNode = (Element) currentChildNodeList
											.item(currentChildNodeListIndex);
									String leafId = currentChildNode
											.getAttribute(ECTD_ID_ATTR);
									String sequence = currentChildNode
											.getAttribute("sequence");
									if (!refSequence.equals(sequence)) {
										continue;
									}
									if (leafId != null
											&& leafId.equals(referenceLeafId)
											&& supParentNode
													.getNodeName()
													.equalsIgnoreCase(
															currentParentNode
																	.getParentNode()
																	.getNodeName())) {
										try {
											removeNodeAttr(currentChildNode);
											NamedNodeMap newleafAttr = newImpNode
													.getAttributes();
											for (int attrIndex = 0; attrIndex < newleafAttr
													.getLength(); attrIndex++) {
												Node newNodeAttr = newleafAttr
														.item(attrIndex);
												currentChildNode
														.setAttribute(
																newNodeAttr
																		.getNodeName(),
																newNodeAttr
																		.getNodeValue());
											}
											if (newImpNode.hasChildNodes()
													&& currentChildNode
															.hasChildNodes()) // changing
												// title
												// node
												currentChildNode
														.replaceChild(
																newImpNode
																		.getFirstChild(),
																currentChildNode
																		.getFirstChild());
											isNodeAppend = true;
										} catch (Exception e) {
											System.out
													.println("Error Found At Node-"
															+ node
																	.getNodeName());
											e.printStackTrace();
										}
										break;
									}
								}
							}
						}
					}
					break;
				}
			} else if (operationAttr.getNodeValue().equalsIgnoreCase(
					ECTD_OPERATION_ATTR_DELETE)) {
				boolean isdeleted = false;
				for (int parentHierarchiIndex = 1; parentHierarchiIndex < leafNodeWithParents
						.size(); parentHierarchiIndex++) {
					Node node = leafNodeWithParents.get(parentHierarchiIndex);
					NodeList nodeList = firstDocument.getElementsByTagName(node
							.getNodeName());
					if (nodeList.getLength() == 0) {
						break;
					} else if (nodeList.getLength() == 1) {
						Node currentParentNode = nodeList.item(0);
						NodeList currentChildNodeList = currentParentNode
								.getChildNodes();
						Node newImpNode = firstDocument.importNode(
								leafNodeWithParents
										.get(parentHierarchiIndex - 1), true);
						Element newImpNode_element = (Element) newImpNode;
						String newImpNode_element_modifiedFile = newImpNode_element
								.getAttribute(ECTD_MODIFIEDFILE_ATTR);
						String refSequence = newImpNode_element_modifiedFile
								.substring(9, 13);
						for (int currentChildNodeListIndex = 0; currentChildNodeListIndex < currentChildNodeList
								.getLength(); currentChildNodeListIndex++) {
							Element currentChildNode = (Element) currentChildNodeList
									.item(currentChildNodeListIndex);
							String leafId = currentChildNode
									.getAttribute(ECTD_ID_ATTR);
							String sequence = currentChildNode
									.getAttribute("sequence");
							// String refSequence=modifiedFile.substring(3,6);
							if (!refSequence.equals(sequence)) {
								continue;
							}
							if (leafId != null
									&& leafId.equals(referenceLeafId)) {
								currentParentNode.insertBefore(newImpNode,
										currentChildNode);
								currentParentNode.removeChild(currentChildNode);
								isdeleted = true;
								break;
							}
						}
						break;
					} else {
						Node supParentNode = leafNodeWithParents
								.get(parentHierarchiIndex + 1);
						Node newImpNode = firstDocument.importNode(
								leafNodeWithParents
										.get(parentHierarchiIndex - 1), true);
						
						Element newImpNode_element = (Element) newImpNode;
						String newImpNode_element_modifiedFile = newImpNode_element
								.getAttribute(ECTD_MODIFIEDFILE_ATTR);
						String refSequence = newImpNode_element_modifiedFile
								.substring(9, 13);
						for (int nodeListCompateindex = 0; nodeListCompateindex < nodeList
								.getLength(); nodeListCompateindex++) {
							Node currentParentNode = nodeList
									.item(nodeListCompateindex);
							boolean nodeAddFlag = checkAttr(currentParentNode,
									node);
							nodeAddFlag = true;
							if (nodeAddFlag) {
								NodeList currentChildNodeList = currentParentNode
										.getChildNodes();
								for (int currentChildNodeListIndex = 0; currentChildNodeListIndex < currentChildNodeList
										.getLength(); currentChildNodeListIndex++) {
									Element currentChildNode = (Element) currentChildNodeList
											.item(currentChildNodeListIndex);
									String leafId = currentChildNode
											.getAttribute(ECTD_ID_ATTR);
									String sequence = currentChildNode
											.getAttribute("sequence");
									if (!refSequence.equals(sequence)) {
										continue;
									}
									if (leafId != null
											&& leafId.equals(referenceLeafId)
											&& supParentNode
													.getNodeName()
													.equalsIgnoreCase(
															currentParentNode
																	.getParentNode()
																	.getNodeName())) {
										try {
											removeNodeAttr(currentChildNode);
											NamedNodeMap newleafAttr = newImpNode
													.getAttributes();
											for (int attrIndex = 0; attrIndex < newleafAttr
													.getLength(); attrIndex++) {
												Node newNodeAttr = newleafAttr
														.item(attrIndex);
												currentChildNode
														.setAttribute(
																newNodeAttr
																		.getNodeName(),
																newNodeAttr
																		.getNodeValue());
											}
											if (newImpNode.hasChildNodes()
													&& currentChildNode
															.hasChildNodes()) // changing
												// title
												// node
												currentChildNode
														.replaceChild(
																newImpNode
																		.getFirstChild(),
																currentChildNode
																		.getFirstChild());
											isdeleted = true;
										} catch (Exception e) {
											System.out
													.println("Error Found At Node-"
															+ node
																	.getNodeName());
											e.printStackTrace();
										}
										break;
									}
								}
							}
						}
						break;
					}
				}
			}
		}
		// Node xmlRoot = readIndexDocument(firstDocument);
		Node xml = readRegionalDocument(firstDocument,
				getRootNodeFromRegionalXml(firstDocument));
		displayAdminInfoFromM1(xml);
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
				documentBuilder.setErrorHandler(new ErrorHandler() {
					public void warning(SAXParseException exception)
							throws SAXException {
						// TODO Auto-generated method stub
						sequenceParsingErrorList += "<tr>";
						sequenceParsingErrorList += "<td>"
								+ exception.getLineNumber() + " : "
								+ exception.getMessage() + "</td>";
						sequenceParsingErrorList += "</tr>";
						System.out.println("Warning...");
						exception.printStackTrace();
					}
					public void fatalError(SAXParseException exception)
							throws SAXException {
						// TODO Auto-generated method stub
						System.out.println("Fatal Error...");
						exception.printStackTrace();
						sequenceParsingErrorList += "<tr>";
						sequenceParsingErrorList += "<td>"
								+ exception.getLineNumber() + " : "
								+ exception.getMessage() + "</td>";
						sequenceParsingErrorList += "</tr>";
					}
					public void error(SAXParseException exception)
							throws SAXException {
						// TODO Auto-generated method stub
						System.out.println("Error..");
						sequenceParsingErrorList += "<tr>";
						sequenceParsingErrorList += "<td>"
								+ exception.getLineNumber() + " : "
								+ exception.getMessage() + "</td>";
						sequenceParsingErrorList += "</tr>";
						exception.printStackTrace();
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
		// here we hava take main root node
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
		ectdNodeId = elementNode.getAttribute(ECTD_ID_ATTR);
		ectdAppVersion = elementNode.getAttribute(ECTD_APPVERSION_ATTR);
		ectdCheckSumType = elementNode.getAttribute(ECTD_CHECKSUM_TYPE_ATTR);
		ectdCheckSum = elementNode.getAttribute(ECTD_CHKSUM_ATTR);
		ectdXlinkHref = elementNode.getAttribute(ECTD_XLINK_HREF_ATTR);
		ectdOperation = elementNode.getAttribute(ECTD_OPERATION_ATTR);
	}
	private void readElementChildren(Node parentNode) {
		// Now this one is for all child
		NodeList xmlNodeChildren = parentNode.getChildNodes();
		if (xmlNodeChildren != null && xmlNodeChildren.getLength() > 0)
			treeHtml.append("<ul>");
		for (int i = 0; i < xmlNodeChildren.getLength(); i++) {
			Node child = xmlNodeChildren.item(i);
			String nodeName = child.getNodeName();
			// checking whether it is m1 or not
			if (!nodeName.equalsIgnoreCase(REGIONAL_MODULE_NODE_NAME)
					&& (displayM1.equals("all") || displayM1
							.equalsIgnoreCase("no"))) {
				if (child.getNodeType() == Element.ELEMENT_NODE) {
					if (!nodeName.equalsIgnoreCase(ECTD_LEAF_NODE)) {
						if (!child.hasChildNodes())
							continue;
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
							// // Add Node
							// Attributes
						}
						readElementChildren(child);
						treeHtml.append("</li>");
					} else {
						if (!getTitleFromNode(child).equalsIgnoreCase("")) {
							NamedNodeMap xmlAttrs = child.getAttributes();
							String operation = "";
							String fileType = "";
							String sequenceName = xmlAttrs.getNamedItem(
									"sequence").getNodeValue();
							treeHtml.append("<li ");
							treeHtml
									.append("data=\"url: 'openViewerFile_ex.do?");
							String newsequencePath = sequencePath.replace(
									"0000", sequenceName);
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
											+ newsequencePath + "/");
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
											.append("&readToolTipData=yes&displayM1=no&sequenceNo="
													+ sequenceName
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
										.append("&readToolTipData=yes&displayM1=no&sequenceNo="
												+ sequenceName
												+ "&folderPath="
												+ folderPath);
								treeHtml.append("'");
								treeHtml.append(", addClass: '" + fileType
										+ "' \">");
							} else
								treeHtml.append(", addClass: '" + fileType
										+ "' \">");
							treeHtml.append(getTitleFromNode(child));
							treeHtml.append("<label style='color: #202090;'>");
							treeHtml.append(" [" + sequenceName + "]");
							treeHtml.append("</label>");
							treeHtml.append("[");
							if (operation.equalsIgnoreCase("deleted"))
								treeHtml.append("<label style='color: red;'>");
							else
								treeHtml
										.append("<label style='color: green;'>");
							treeHtml.append(operation);
							treeHtml.append("</label>");
							if (!operation.equalsIgnoreCase("new"))
								treeHtml.append("->");
							else
								treeHtml.append("]");
							if (!operation.equalsIgnoreCase("new")) {
								String refSequence = xmlAttrs.getNamedItem(
										ECTD_MODIFIEDFILE_ATTR).getNodeValue()
										.substring(3, 7);
								treeHtml
										.append("<label style='color: #202040;'>");
								treeHtml.append(refSequence + "]");
								treeHtml.append("</label>");
							}
							treeHtml.append("</li>");
						}
					}
				}
			}
		}
		treeHtml.append("</ul>");
	}
	private String createLeafNode(String operation, String sequenceName,
			String fileType, StringBuffer nodeXlinkDetail,
			StringBuffer nodeIdDetail, Node child) {
		StringBuffer leafNodeStr = new StringBuffer("");
		leafNodeStr.append("<li ");
		if (!operation.equalsIgnoreCase("deleted")) {
			leafNodeStr.append("data=\"url: 'openViewerFile_ex.do?");
			nodeXlinkDetail.append("'");
			nodeXlinkDetail.append(" , action : 'openToolTip_ex.do?nodeId=");
			nodeXlinkDetail.append(nodeId);
			nodeXlinkDetail.append("&readToolTipData=yes&sequenceNo="
					+ sequenceName + "&folderPath=" + folderPath);
			nodeXlinkDetail.append("'");
			leafNodeStr.append(nodeIdDetail);
			leafNodeStr.append("&");
			leafNodeStr.append(nodeXlinkDetail);
		} else {
			leafNodeStr.append("data=\" action : 'openToolTip_ex.do?nodeId=");
			leafNodeStr.append(nodeId);
			leafNodeStr.append("&readToolTipData=yes&displayM1=no&sequenceNo="
					+ sequenceName + "&folderPath=" + folderPath);
			leafNodeStr.append("'");
			// treeHtml.append(nodeXlinkDetail);
		}
		leafNodeStr.append(", addClass: '" + fileType + "' \">");
		leafNodeStr.append(getTitleFromNode(child));
		leafNodeStr.append("[");
		if (operation.equalsIgnoreCase("deleted"))
			treeHtml.append("<label style='color: red;'>");
		else
			leafNodeStr.append("<label style='color: green;'>");
		leafNodeStr.append(operation);
		leafNodeStr.append("</label>");
		leafNodeStr.append("]");
		leafNodeStr.append("</li>");
		return leafNodeStr.toString();
	}
	private StringBuffer appendLeafNode(String mainStr, String appendNodeStr) {
		StringBuffer finalStr = new StringBuffer();
		int counter = 0;
		try {
			for (int i = mainStr.lastIndexOf("addClass"); i < mainStr.length(); i++) {
				if (mainStr.charAt(i) == '>') {
					counter++;
					if (counter == 3) {
						String firstHalfStr = mainStr.substring(0, i + 2);
						String secondHalfStr = mainStr.substring(i + 2, mainStr
								.length());
						finalStr.append(firstHalfStr + appendNodeStr
								+ secondHalfStr);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalStr;
	}
	private void displayLatestEnvelope(Node parentNode) {
		// Now this one is for all child
		try {
			NodeList xmlNodeChildren = parentNode.getChildNodes();
			if (xmlNodeChildren != null && xmlNodeChildren.getLength() > 0) {
				envalopeTreeHtml.append("<ul>");
				for (int i = 0; i < xmlNodeChildren.getLength(); i++) {
					Node child = xmlNodeChildren.item(i);
					String nodeName = child.getNodeName();
					if (child.getNodeType() == Element.ELEMENT_NODE) {
						if (!nodeName.equalsIgnoreCase(ECTD_LEAF_NODE)) {
							envalopeTreeHtml.append("<li class=folder>");
							envalopeTreeHtml.append(child.getNodeName());
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
								envalopeTreeHtml
										.append("<label style='color: #202090;'>");
								envalopeTreeHtml.append("[" + nodeAttrValue
										+ "]");
								envalopeTreeHtml.append("</label>");
								// envalopeTreeHtml.append("[" + nodeAttrValue
								// + "]");// Add
								// Node
								// Attributes
							}
							NodeList nodeList = child.getChildNodes();
							if (nodeList.getLength() == 1) {
								Node node = nodeList.item(0);
								if (node.getNodeType() == Element.TEXT_NODE)
									envalopeTreeHtml
											.append("[<label style='color: green;'>"
													+ node.getNodeValue()
													+ "</label>]");
							}
							displayLatestEnvelope(child);
							envalopeTreeHtml.append("</li>");
						} else {
							if (!getTitleFromNode(child).equalsIgnoreCase("")) {
								String fileType = "";
								String operation = "";
								envalopeTreeHtml.append("<li ");
								envalopeTreeHtml
										.append("data=\"url: 'openViewerFile_ex.do?");
								NamedNodeMap xmlAttrs = child.getAttributes();
								String sequenceName = xmlAttrs.getNamedItem(
										"sequence").getNodeValue();
								String newsequencePath = xmlAttrs.getNamedItem(
										"sequencePath").getNodeValue();
								for (int j = 0; j < xmlAttrs.getLength(); j++) {
									Node xmlNodeAttr = xmlAttrs.item(j);
									if (xmlNodeAttr.getNodeName()
											.equalsIgnoreCase("ID")) {
										envalopeTreeHtml.append("nodeId=");
										envalopeTreeHtml.append(xmlNodeAttr
												.getNodeValue());
										nodeId = xmlNodeAttr.getNodeValue();
									} else if (xmlNodeAttr.getNodeName()
											.equalsIgnoreCase(
													ECTD_XLINK_HREF_ATTR)) {
										envalopeTreeHtml
												.append("&fileWithPath="
														+ newsequencePath + "/"
														+ m1XmlPathFolder + "/");
										envalopeTreeHtml.append(xmlNodeAttr
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
										envalopeTreeHtml.append("'");
										envalopeTreeHtml
												.append(" , action : 'openToolTip_ex.do?nodeId=");
										envalopeTreeHtml.append(nodeId);
										envalopeTreeHtml
												.append("&readToolTipData=yes&displayM1=yes&sequenceNo="
														+ sequenceName
														+ "&folderPath="
														+ folderPath);
										envalopeTreeHtml.append("'");
									} else if (xmlNodeAttr.getNodeName()
											.equalsIgnoreCase(
													ECTD_OPERATION_ATTR)) {
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
											envalopeTreeHtml.append("'");
											operation = "DELETED";
											fileType = "delete";
										} else if (operationValue
												.equalsIgnoreCase("new")) {
											operation = "NEW";
										}
									}
								}
								// if(fileType==null || fileType.equals(""))
								envalopeTreeHtml.append(", addClass: '"
										+ fileType + "' \">");
								envalopeTreeHtml
										.append(getTitleFromNode(child));
								envalopeTreeHtml.append("[");
								if (operation.equalsIgnoreCase("deleted"))
									envalopeTreeHtml
											.append("<label style='color: red;'>");
								else
									envalopeTreeHtml
											.append("<label style='color: green;'>");
								envalopeTreeHtml.append(operation);
								envalopeTreeHtml.append("</label>");
								envalopeTreeHtml.append("]");
								envalopeTreeHtml
										.append("<label style='color: #202090;'>");
								envalopeTreeHtml.append(" [" + sequenceName
										+ "]");
								envalopeTreeHtml.append("</label>");
								envalopeTreeHtml.append("</li>");
							}
						}
					}
				}
				envalopeTreeHtml.append("</ul>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void displayAdminInfoFromM1(Node parentNode) {
		// Now this one is for all child
		try {
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
								treeHtml.append("[" + nodeAttrValue + "]");// Add
								// Node
								// Attributes
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
								NamedNodeMap xmlAttrs = child.getAttributes();
								String operation = "";
								String fileType = "";
								String sequenceName = xmlAttrs.getNamedItem(
										"sequence").getNodeValue();
								treeHtml.append("<li ");
								treeHtml
										.append("data=\"url: 'openViewerFile_ex.do?");
								String fullPath = folderPath + "/"
										+ sequenceName + "/" + m1XmlPathFolder;
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
												+ fullPath + "/");
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
												.append("&readToolTipData=yes&displayM1=yes&sequenceNo="
														+ sequenceName
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
											.append("&readToolTipData=yes&displayM1=yes&sequenceNo="
													+ sequenceName
													+ "&folderPath="
													+ folderPath);
									treeHtml.append("'");
									treeHtml.append(", addClass: '" + fileType
											+ "' \">");
								} else
									treeHtml.append(", addClass: '" + fileType
											+ "' \">");
								treeHtml.append(getTitleFromNode(child));
								treeHtml
										.append("<label style='color: #202090;'>");
								treeHtml.append(" [" + sequenceName + "]");
								treeHtml.append("</label>");
								treeHtml.append("[");
								if (operation.equalsIgnoreCase("deleted"))
									treeHtml
											.append("<label style='color: red;'>");
								else
									treeHtml
											.append("<label style='color: green;'>");
								treeHtml.append(operation);
								treeHtml.append("</label>");
								if (!operation.equalsIgnoreCase("new"))
									treeHtml.append("->");
								else
									treeHtml.append("]");
								if (!operation.equalsIgnoreCase("new")) {
									String refSequence = xmlAttrs.getNamedItem(
											ECTD_MODIFIEDFILE_ATTR)
											.getNodeValue().substring(9, 13);
									treeHtml
											.append("<label style='color: #202040;'>");
									treeHtml.append(refSequence + "]");
									treeHtml.append("</label>");
								}
								treeHtml.append("</li>");
							}
						}
					}
				}
				treeHtml.append("</ul>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * Node foundNode=null; private Node getNodeByNodeId(NodeList
	 * nodeList,String nodeId) { Node child=null; for (int i = 0; i <
	 * nodeList.getLength(); i++) { child = nodeList.item(i); String
	 * nodeName=child.getNodeName(); NamedNodeMap xmlAttrs =
	 * child.getAttributes(); for (int j = 0; j < xmlAttrs.getLength() ; j++) {
	 * Node xmlNodeAttr = xmlAttrs.item(j);
	 * if(xmlNodeAttr.getNodeName().equalsIgnoreCase("ID")){ String
	 * cmlNodeId=xmlNodeAttr.getNodeValue();
	 * if(nodeId.equalsIgnoreCase(cmlNodeId)) { return child; } } } }
	 * 
	 * return child; }
	 */
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
		/*
		 * This code also give you root node Value Element
		 * element=doc.getDocumentElement();
		 * System.out.println(element.getNodeName());
		 */
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
	private NodeList getAllLeafNodeBySequence(String folderPath,
			String sequence, String xmlFileName) {
		Document document = InitialCallFunction(folderPath + "/" + sequence
				+ "/" + xmlFileName);
		NodeList nodeList = document.getElementsByTagName(ECTD_LEAF_NODE);
		return nodeList;
	}
	private NodeList getAllLeafNodeByXmlPath(String xmlFilePath) {
		Document document = InitialCallFunction(xmlFilePath);
		NodeList nodeList = document.getElementsByTagName(ECTD_LEAF_NODE);
		return nodeList;
	}
	private NodeList getAllLeafNodeByDocument(Document document) {
		NodeList nodeList = document.getElementsByTagName(ECTD_LEAF_NODE);
		return nodeList;
	}
	private ArrayList<ArrayList<Node>> getNodeHierarchy(NodeList nodeList,
			String seqNo) {
		ArrayList<ArrayList<Node>> allLeafNodeData = new ArrayList<ArrayList<Node>>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			ArrayList<Node> parentNodeList = new ArrayList<Node>();
			while (!(node instanceof Document)) {
				NamedNodeMap nodeAttr = node.getAttributes();
				if (nodeAttr != null && nodeAttr.getLength() != 0) {
					Node innerAttr = nodeAttr.getNamedItem(ECTD_ID_ATTR);
					if (innerAttr != null) {
						innerAttr.setNodeValue(innerAttr.getNodeValue());
					}
				}
				parentNodeList.add(node);
				node = node.getParentNode();
			}
			allLeafNodeData.add(parentNodeList);
		}
		return allLeafNodeData;
	}
	private void createNetViewDocument(Document firstDocument,
			ArrayList<ArrayList<Node>> allLeafNodeData) {
		// one iteration for one leaf node
		for (int leafIndex = 0; leafIndex < allLeafNodeData.size(); leafIndex++) {
			ArrayList<Node> leafNodeWithParents = allLeafNodeData
					.get(leafIndex);
			Node leafNode = leafNodeWithParents.get(0);
			NamedNodeMap attributes = leafNode.getAttributes();
			Node operationAttr = attributes.getNamedItem(ECTD_OPERATION_ATTR);
			String referenceLeafId = getReferenceIdFromModifiedFileAttr(leafNode);
			if (operationAttr.getNodeValue().equalsIgnoreCase(
					ECTD_OPERATION_ATTR_NEW)) {
				try {
					for (int parentHierarchiIndex = 1; parentHierarchiIndex < leafNodeWithParents
							.size(); parentHierarchiIndex++) {
						Node node = leafNodeWithParents
								.get(parentHierarchiIndex);
						NodeList nodeList = firstDocument
								.getElementsByTagName(node.getNodeName());
						boolean isNodeAdded = false;
						if (nodeList.getLength() == 0) {
							// addNotExistNode(node.getParentNode(),firstDocument);
							System.out.println("New Node Not Found");
							continue;
						} else if (nodeList.getLength() == 1) {
							Node currentParentNode = nodeList.item(0);
							Node newImpNode = firstDocument.importNode(
									leafNodeWithParents
											.get(parentHierarchiIndex - 1),
									true);
							boolean nodeAddFlag = checkAttr(currentParentNode,
									node);
							boolean advanceattr = advanceAttrCheck(node,
									currentParentNode);
							if (nodeAddFlag && advanceattr) {
								Element newNode_Element = (Element) newImpNode;
								NodeList allLeafOfNewNode = newNode_Element
										.getElementsByTagName("leaf");
								if (allLeafOfNewNode.getLength() >= 2) {
									int checkval = 1;
									while (allLeafOfNewNode.getLength() != checkval) {
										Node ch = allLeafOfNewNode
												.item(checkval);
										if (ch != null) {
											ch.getParentNode().removeChild(ch);
										}
									}
								}
								if (!newImpNode.getNodeName().equals("leaf")) {
									NodeList nodeList1 = firstDocument
											.getElementsByTagName(newImpNode
													.getNodeName());
									if (nodeList1.getLength() >= 1) {
										try {
											Node refNode = nodeList1.item(0);
											currentParentNode.insertBefore(
													newImpNode, refNode);
											isNodeAdded = true;
											break;
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
								currentParentNode.appendChild(newImpNode);
								isNodeAdded = true;
								break;
							}
						} else {
							// NodeList
							// nodeListCompare=firstDocument.getElementsByTagName(node.getNodeName());
							isNodeAdded = false;
							for (int nodeListCompateindex = 0; nodeListCompateindex < nodeList
									.getLength(); nodeListCompateindex++) {
								try {
									Node nodeCompare = nodeList
											.item(nodeListCompateindex);
									boolean nodeAddFlag = checkAttr(
											nodeCompare, node);
									boolean advanceattr = advanceAttrCheck(
											node, nodeCompare);
									if (nodeAddFlag && advanceattr) {
										Node supParentNode = leafNodeWithParents
												.get(parentHierarchiIndex + 1);
										Node newImpNode = firstDocument
												.importNode(
														leafNodeWithParents
																.get(parentHierarchiIndex - 1),
														true);
										if (supParentNode
												.getNodeName()
												.equalsIgnoreCase(
														nodeCompare
																.getParentNode()
																.getNodeName())) {
											Element newNode_Element = (Element) newImpNode;
											NodeList allLeafOfNewNode = newNode_Element
													.getElementsByTagName("leaf");
											if (allLeafOfNewNode.getLength() >= 2) {
												int checkval = 1;
												while (allLeafOfNewNode
														.getLength() != checkval) {
													Node ch = allLeafOfNewNode
															.item(checkval);
													if (ch != null) {
														ch
																.getParentNode()
																.removeChild(ch);
													}
												}
											}
											if (!newImpNode.getNodeName()
													.equals("leaf")) {
												NodeList nodeList1 = firstDocument
														.getElementsByTagName(newImpNode
																.getNodeName());
												if (nodeList1.getLength() >= 1) {
													try {
														Node refNode = nodeList1
																.item(0);
														nodeCompare
																.insertBefore(
																		newImpNode,
																		refNode);
														isNodeAdded = true;
														break;
													} catch (Exception e) {
														System.out
																.println("Error In Node->"
																		+ newImpNode
																				.getNodeName());
														e.printStackTrace();
													}
												}
											}
											// if reference not not found then
											// need to append in child
											nodeCompare.appendChild(newImpNode);
											isNodeAdded = true;
											break;
										}
									}
								} catch (Exception e) {
									System.err.println("Error in "
											+ node.getNodeName() + " node");
									e.printStackTrace();
								}
							}
							if (isNodeAdded)
								break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (operationAttr.getNodeValue().equalsIgnoreCase(
					ECTD_OPERATION_ATTR_REPLACE)) {
				// If the operation type is replace
				for (int parentHierarchiIndex = 1; parentHierarchiIndex < leafNodeWithParents
						.size(); parentHierarchiIndex++) {
					boolean isNodeReplaced = false;
					Node node = leafNodeWithParents.get(parentHierarchiIndex);
					NodeList nodeList = firstDocument.getElementsByTagName(node
							.getNodeName());
					if (nodeList.getLength() == 0) {
						System.out.println("NotFound-" + node.getNodeName());
						continue;
					} else if (nodeList.getLength() == 1) {
						Node currentParentNode = nodeList.item(0);
						Node newImpNode = firstDocument.importNode(
								leafNodeWithParents
										.get(parentHierarchiIndex - 1), true);
						boolean nodeAddFlag = checkAttr(currentParentNode, node);
						boolean advanceattr = advanceAttrCheck(node,
								currentParentNode);
						if (nodeAddFlag
								&& advanceattr
								&& !newImpNode.getNodeName().equalsIgnoreCase(
										"leaf")) {
							Element newNode_Element = (Element) newImpNode;
							NodeList allLeafOfNewNode = newNode_Element
									.getElementsByTagName("leaf");
							if (allLeafOfNewNode.getLength() >= 2) {
								int checkval = 1;
								while (allLeafOfNewNode.getLength() != checkval) {
									Node ch = allLeafOfNewNode.item(checkval);
									if (ch != null)
										ch.getParentNode().removeChild(ch);
								}
							}
							if (!newImpNode.getNodeName().equals("leaf")) {
								NodeList nodeList1 = firstDocument
										.getElementsByTagName(newImpNode
												.getNodeName());
								if (nodeList1.getLength() >= 1) {
									try {
										Node refNode = nodeList1.item(0);
										currentParentNode.insertBefore(
												newImpNode, refNode);
										isNodeReplaced = true;
										break;
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
							// if reference node not found
							currentParentNode.appendChild(newImpNode);
							isNodeReplaced = true;
							break;
						}
						if (nodeAddFlag && advanceattr) {
							Element newImpNode_element = (Element) newImpNode;
							String newImpNode_element_modifiedFile = newImpNode_element
									.getAttribute(ECTD_MODIFIEDFILE_ATTR);
							String refSequence = newImpNode_element_modifiedFile
									.substring(3, 7);
							NodeList currentChildNodeList = currentParentNode
									.getChildNodes();
							isNodeReplaced = false;
							for (int currentChildNodeListIndex = 0; currentChildNodeListIndex < currentChildNodeList
									.getLength(); currentChildNodeListIndex++) {
								Element currentChildNode = (Element) currentChildNodeList
										.item(currentChildNodeListIndex);
								String leafId = currentChildNode
										.getAttribute(ECTD_ID_ATTR);
								String sequence = currentChildNode
										.getAttribute("sequence");
								if (!refSequence.equals(sequence)) {
									continue;
								}
								if (leafId != null
										&& leafId.equals(referenceLeafId)) {
									currentParentNode.appendChild(newImpNode);
									currentParentNode
											.removeChild(currentChildNode);
									isNodeReplaced = true;
									break;
								}
							}
							if (!isNodeReplaced) {
								currentParentNode.appendChild(newImpNode);
								isNodeReplaced = true;
								break;
							}
						}
						if (isNodeReplaced)
							break;
					} else {
						Node supParentNode = leafNodeWithParents
								.get(parentHierarchiIndex + 1);
						Node newImpNode = firstDocument.importNode(
								leafNodeWithParents
										.get(parentHierarchiIndex - 1), true);
						for (int nodeListCompateindex = 0; nodeListCompateindex < nodeList
								.getLength(); nodeListCompateindex++) {
							Node currentParentNode = nodeList
									.item(nodeListCompateindex);
							boolean nodeAddFlag = checkAttr(currentParentNode,
									node);
							boolean advanceattr = advanceAttrCheck(node,
									currentParentNode);
							if (nodeAddFlag
									&& advanceattr
									&& !newImpNode.getNodeName()
											.equalsIgnoreCase("leaf")) {
								Element newNode_Element = (Element) newImpNode;
								NodeList allLeafOfNewNode = newNode_Element
										.getElementsByTagName("leaf");
								if (allLeafOfNewNode.getLength() >= 2) {
									int checkval = 1;
									while (allLeafOfNewNode.getLength() != checkval) {
										Node ch = allLeafOfNewNode
												.item(checkval);
										// NamedNodeMap chAttr = ch
										// .getAttributes();
										//
										// String operation =
										// chAttr.getNamedItem(
										// "operation").getNodeValue();
										if (ch != null) {
											ch.getParentNode().removeChild(ch);
										}
									}
								}
								if (!newImpNode.getNodeName().equals("leaf")) {
									NodeList nodeList1 = firstDocument
											.getElementsByTagName(newImpNode
													.getNodeName());
									try {
										if (nodeList1.getLength() >= 1) {
											Node refNode = nodeList1.item(0);
											currentParentNode.insertBefore(
													newImpNode, refNode);
											isNodeReplaced = true;
											break;
										}
									} catch (Exception e) {
										System.out.println("Problem Inserting Node-"+ newImpNode.getNodeName());
										e.printStackTrace();
									}
								}
								// if reference node not found
								currentParentNode.appendChild(newImpNode);
								isNodeReplaced = true;
								break;
							}
							if (nodeAddFlag && advanceattr) {
								Element newImpNode_element = (Element) newImpNode;
								String newImpNode_element_modifiedFile = newImpNode_element
										.getAttribute(ECTD_MODIFIEDFILE_ATTR);
								String refSequence = newImpNode_element_modifiedFile
										.substring(3, 7);
								NodeList currentChildNodeList = currentParentNode
										.getChildNodes();
								isNodeReplaced = false;
								for (int currentChildNodeListIndex = 0; currentChildNodeListIndex < currentChildNodeList
										.getLength(); currentChildNodeListIndex++) {
									Element currentChildNode = (Element) currentChildNodeList
											.item(currentChildNodeListIndex);
									String leafId = currentChildNode
											.getAttribute(ECTD_ID_ATTR);
									String sequence = currentChildNode
											.getAttribute("sequence");
									if (!refSequence.equals(sequence)) {
										continue;
									}
									if (leafId != null
											&& leafId.equals(referenceLeafId)
											&& supParentNode
													.getNodeName()
													.equalsIgnoreCase(
															currentParentNode
																	.getParentNode()
																	.getNodeName())) {
										try {
											currentParentNode.insertBefore(
													newImpNode,
													currentChildNode);
											currentParentNode
													.removeChild(currentChildNode);
											isNodeReplaced = true;
											break;
										} catch (Exception e) {
											System.out
													.println("Error Found At Node-"
															+ node
																	.getNodeName());
											e.printStackTrace();
										}
										break;
									}
								}
								if (!isNodeReplaced) {
									currentParentNode.appendChild(newImpNode);
									isNodeReplaced = true;
									break;
								}
							}
						}
					}
					if (isNodeReplaced)
						break;
				}
			} else if (operationAttr.getNodeValue().equalsIgnoreCase(
					ECTD_OPERATION_ATTR_APPEND)) {
				for (int parentHierarchiIndex = 1; parentHierarchiIndex < leafNodeWithParents
						.size(); parentHierarchiIndex++) {
					boolean isNodeAppended = false;
					Node node = leafNodeWithParents.get(parentHierarchiIndex);
					NodeList nodeList = firstDocument.getElementsByTagName(node
							.getNodeName());
					if (nodeList.getLength() == 0) {
						System.out.println("NotFound-" + node.getNodeName());
						continue;
					} else if (nodeList.getLength() == 1) {
						Node currentParentNode = nodeList.item(0);
						Node newImpNode = firstDocument.importNode(
								leafNodeWithParents
										.get(parentHierarchiIndex - 1), true);
						boolean nodeAddFlag = checkAttr(currentParentNode, node);
						boolean advanceattr = advanceAttrCheck(node,
								currentParentNode);
						if (nodeAddFlag
								&& advanceattr
								&& !newImpNode.getNodeName().equalsIgnoreCase(
										"leaf")) {
							Element newNode_Element = (Element) newImpNode;
							NodeList allLeafOfNewNode = newNode_Element
									.getElementsByTagName("leaf");
							if (allLeafOfNewNode.getLength() >= 2) {
								int checkval = 1;
								while (allLeafOfNewNode.getLength() != checkval) {
									Node ch = allLeafOfNewNode.item(checkval);
									// NamedNodeMap chAttr = ch.getAttributes();
									// String operation = chAttr.getNamedItem(
									// "operation").getNodeValue();
									if (ch != null)
										ch.getParentNode().removeChild(ch);
								}
							}
							if (!newImpNode.getNodeName().equals("leaf")) {
								NodeList nodeList1 = firstDocument
										.getElementsByTagName(newImpNode
												.getNodeName());
								if (nodeList1.getLength() >= 1) {
									try {
										Node refNode = nodeList1.item(0);
										currentParentNode.insertBefore(
												newImpNode, refNode);
										isNodeAppended = true;
										break;
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
							currentParentNode.appendChild(newImpNode);
							isNodeAppended = true;
							break;
						}
						if (nodeAddFlag && advanceattr) {
							Element newImpNode_element = (Element) newImpNode;
							String newImpNode_element_modifiedFile = newImpNode_element
									.getAttribute(ECTD_MODIFIEDFILE_ATTR);
							String refSequence = newImpNode_element_modifiedFile
									.substring(3, 7);
							NodeList currentChildNodeList = currentParentNode
									.getChildNodes();
							isNodeAppended = false;
							for (int currentChildNodeListIndex = 0; currentChildNodeListIndex < currentChildNodeList
									.getLength(); currentChildNodeListIndex++) {
								Element currentChildNode = (Element) currentChildNodeList
										.item(currentChildNodeListIndex);
								String leafId = currentChildNode
										.getAttribute(ECTD_ID_ATTR);
								String sequence = currentChildNode
										.getAttribute("sequence");
								if (!refSequence.equals(sequence)) {
									continue;
								}
								if (leafId != null
										&& leafId.equals(referenceLeafId)) {
									currentParentNode.appendChild(newImpNode);
									currentParentNode
											.removeChild(currentChildNode);
									isNodeAppended = true;
									break;
								}
							}
							if (!isNodeAppended) {
								currentParentNode.appendChild(newImpNode);
								isNodeAppended = true;
								break;
							}
						}
						if (isNodeAppended)
							break;
					} else {
						Node supParentNode = leafNodeWithParents
								.get(parentHierarchiIndex + 1);
						Node newImpNode = firstDocument.importNode(
								leafNodeWithParents
										.get(parentHierarchiIndex - 1), true);
						for (int nodeListCompateindex = 0; nodeListCompateindex < nodeList
								.getLength(); nodeListCompateindex++) {
							Node currentParentNode = nodeList
									.item(nodeListCompateindex);
							boolean nodeAddFlag = checkAttr(currentParentNode,
									node);
							boolean advanceattr = advanceAttrCheck(node,
									currentParentNode);
							if (nodeAddFlag
									&& advanceattr
									&& !newImpNode.getNodeName()
											.equalsIgnoreCase("leaf")) {
								Element newNode_Element = (Element) newImpNode;
								NodeList allLeafOfNewNode = newNode_Element
										.getElementsByTagName("leaf");
								if (allLeafOfNewNode.getLength() >= 2) {
									int checkval = 1;
									while (allLeafOfNewNode.getLength() != checkval) {
										Node ch = allLeafOfNewNode
												.item(checkval);
										if (ch != null) {
											ch.getParentNode().removeChild(ch);
										}
									}
								}
								if (!newImpNode.getNodeName().equals("leaf")) {
									NodeList nodeList1 = firstDocument
											.getElementsByTagName(newImpNode
													.getNodeName());
									try {
										if (nodeList1.getLength() >= 1) {
											Node refNode = nodeList1.item(0);
											currentParentNode.insertBefore(
													newImpNode, refNode);
											isNodeAppended = true;
											break;
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								currentParentNode.appendChild(newImpNode);
								isNodeAppended = true;
								break;
							}
							if (nodeAddFlag && advanceattr) {
								Element newImpNode_element = (Element) newImpNode;
								String newImpNode_element_modifiedFile = newImpNode_element
										.getAttribute(ECTD_MODIFIEDFILE_ATTR);
								String refSequence = newImpNode_element_modifiedFile
										.substring(3, 7);
								NodeList currentChildNodeList = currentParentNode
										.getChildNodes();
								isNodeAppended = false;
								for (int currentChildNodeListIndex = 0; currentChildNodeListIndex < currentChildNodeList
										.getLength(); currentChildNodeListIndex++) {
									Element currentChildNode = (Element) currentChildNodeList
											.item(currentChildNodeListIndex);
									String leafId = currentChildNode
											.getAttribute(ECTD_ID_ATTR);
									String sequence = currentChildNode
											.getAttribute("sequence");
									if (!refSequence.equals(sequence)) {
										continue;
									}
									if (leafId != null
											&& leafId.equals(referenceLeafId)
											&& supParentNode
													.getNodeName()
													.equalsIgnoreCase(
															currentParentNode
																	.getParentNode()
																	.getNodeName())) {
										try {
											currentParentNode.insertBefore(
													newImpNode,
													currentChildNode);
											currentParentNode
													.removeChild(currentChildNode);
											isNodeAppended = true;
											break;
										} catch (Exception e) {
											System.out
													.println("Error Found At Node-"
															+ node
																	.getNodeName());
											e.printStackTrace();
										}
										break;
									}
								}
								if (!isNodeAppended) {
									currentParentNode.appendChild(newImpNode);
									isNodeAppended = true;
									break;
								}
							}
						}
					}
					if (isNodeAppended)
						break;
				}
			} else if (operationAttr.getNodeValue().equalsIgnoreCase(
					ECTD_OPERATION_ATTR_DELETE)) {
				for (int parentHierarchiIndex = 1; parentHierarchiIndex < leafNodeWithParents
						.size(); parentHierarchiIndex++) {
					boolean isNodeDeleted = false;
					Node node = leafNodeWithParents.get(parentHierarchiIndex);
					NodeList nodeList = firstDocument.getElementsByTagName(node
							.getNodeName());
					if (nodeList.getLength() == 0) {
						System.out.println("NotFound-" + node.getNodeName());
						continue;
					} else if (nodeList.getLength() == 1) {
						Node currentParentNode = nodeList.item(0);
						Node newImpNode = firstDocument.importNode(
								leafNodeWithParents
										.get(parentHierarchiIndex - 1), true);
						boolean nodeAddFlag = checkAttr(currentParentNode, node);
						boolean advanceattr = advanceAttrCheck(node,
								currentParentNode);
						if (nodeAddFlag
								&& advanceattr
								&& !newImpNode.getNodeName().equalsIgnoreCase(
										"leaf")) {
							Element newNode_Element = (Element) newImpNode;
							NodeList allLeafOfNewNode = newNode_Element
									.getElementsByTagName("leaf");
							if (allLeafOfNewNode.getLength() >= 2) {
								int checkval = 1;
								while (allLeafOfNewNode.getLength() != checkval) {
									Node ch = allLeafOfNewNode.item(checkval);
									if (ch != null)
										ch.getParentNode().removeChild(ch);
								}
							}
							if (!newImpNode.getNodeName().equals("leaf")) {
								NodeList nodeList1 = firstDocument
										.getElementsByTagName(newImpNode
												.getNodeName());
								if (nodeList1.getLength() >= 1) {
									try {
										Node refNode = nodeList1.item(0);
										currentParentNode.insertBefore(
												newImpNode, refNode);
										isNodeDeleted = true;
										break;
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
							currentParentNode.appendChild(newImpNode);
							isNodeDeleted = true;
							break;
						}
						if (nodeAddFlag && advanceattr) {
							Element newImpNode_element = (Element) newImpNode;
							String newImpNode_element_modifiedFile = newImpNode_element
									.getAttribute(ECTD_MODIFIEDFILE_ATTR);
							String refSequence = newImpNode_element_modifiedFile
									.substring(3, 7);
							NodeList currentChildNodeList = currentParentNode
									.getChildNodes();
							isNodeDeleted = false;
							for (int currentChildNodeListIndex = 0; currentChildNodeListIndex < currentChildNodeList
									.getLength(); currentChildNodeListIndex++) {
								Element currentChildNode = (Element) currentChildNodeList
										.item(currentChildNodeListIndex);
								String leafId = currentChildNode
										.getAttribute(ECTD_ID_ATTR);
								String sequence = currentChildNode
										.getAttribute("sequence");
								if (!refSequence.equals(sequence)) {
									continue;
								}
								if (leafId != null
										&& leafId.equals(referenceLeafId)) {
									currentParentNode.appendChild(newImpNode);
									currentParentNode
											.removeChild(currentChildNode);
									isNodeDeleted = true;
									break;
								}
							}
							if (!isNodeDeleted) {
								currentParentNode.appendChild(newImpNode);
								isNodeDeleted = true;
								break;
							}
						}
						if (isNodeDeleted)
							break;
					} else {
						Node supParentNode = leafNodeWithParents
								.get(parentHierarchiIndex + 1);
						Node newImpNode = firstDocument.importNode(
								leafNodeWithParents
										.get(parentHierarchiIndex - 1), true);
						for (int nodeListCompateindex = 0; nodeListCompateindex < nodeList
								.getLength(); nodeListCompateindex++) {
							Node currentParentNode = nodeList
									.item(nodeListCompateindex);
							boolean nodeAddFlag = checkAttr(currentParentNode,
									node);
							boolean advanceattr = advanceAttrCheck(node,
									currentParentNode);
							if (nodeAddFlag
									&& advanceattr
									&& !newImpNode.getNodeName()
											.equalsIgnoreCase("leaf")) {
								Element newNode_Element = (Element) newImpNode;
								NodeList allLeafOfNewNode = newNode_Element
										.getElementsByTagName("leaf");
								if (allLeafOfNewNode.getLength() >= 2) {
									int checkval = 1;
									while (allLeafOfNewNode.getLength() != checkval) {
										Node ch = allLeafOfNewNode
												.item(checkval);
										if (ch != null) {
											ch.getParentNode().removeChild(ch);
										}
									}
								}
								if (!newImpNode.getNodeName().equals("leaf")) {
									NodeList nodeList1 = firstDocument
											.getElementsByTagName(newImpNode
													.getNodeName());
									try {
										if (nodeList1.getLength() >= 1) {
											Node refNode = nodeList1.item(0);
											currentParentNode.insertBefore(
													newImpNode, refNode);
											isNodeDeleted = true;
											break;
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								currentParentNode.appendChild(newImpNode);
								isNodeDeleted = true;
								break;
							}
							if (nodeAddFlag && advanceattr) {
								Element newImpNode_element = (Element) newImpNode;
								String newImpNode_element_modifiedFile = newImpNode_element
										.getAttribute(ECTD_MODIFIEDFILE_ATTR);
								String refSequence = newImpNode_element_modifiedFile
										.substring(3, 7);
								NodeList currentChildNodeList = currentParentNode
										.getChildNodes();
								isNodeDeleted = false;
								for (int currentChildNodeListIndex = 0; currentChildNodeListIndex < currentChildNodeList
										.getLength(); currentChildNodeListIndex++) {
									Element currentChildNode = (Element) currentChildNodeList
											.item(currentChildNodeListIndex);
									String leafId = currentChildNode
											.getAttribute(ECTD_ID_ATTR);
									String sequence = currentChildNode
											.getAttribute("sequence");
									if (!refSequence.equals(sequence)) {
										continue;
									}
									if (leafId != null
											&& leafId.equals(referenceLeafId)
											&& supParentNode
													.getNodeName()
													.equalsIgnoreCase(
															currentParentNode
																	.getParentNode()
																	.getNodeName())) {
										try {
											currentParentNode.insertBefore(
													newImpNode,
													currentChildNode);
											currentParentNode
													.removeChild(currentChildNode);
											isNodeDeleted = true;
											break;
										} catch (Exception e) {
											System.out
													.println("Error Found At Node-"
															+ node
																	.getNodeName());
											e.printStackTrace();
										}
										break;
									}
								}
								if (!isNodeDeleted) {
									currentParentNode.appendChild(newImpNode);
									isNodeDeleted = true;
									break;
								}
							}
						}
					}
					if (isNodeDeleted)
						break;
				}
			}
		}
		Node xmlRoot = readIndexDocument(firstDocument);
		if (displayM1.equalsIgnoreCase("yes")) {
			Node xml = readRegionalDocument(firstDocument,
					getRootNodeFromRegionalXml(firstDocument));
			displayAdminInfoFromM1(xml);
		} else {
			readElementChildren(xmlRoot);
		}
	}
	private void removeNodeAttr(Node currentChildNode) {
		NamedNodeMap oldleafAttr = currentChildNode.getAttributes();
		int counter = 0;
		while (true) {
			if (counter++ == 11) // used to handle unexpected result ( infinite
				// loop)
				break;
			if (oldleafAttr.getLength() == 2)
				break;
			Node removeattr = oldleafAttr.item(0);
			oldleafAttr.removeNamedItem(removeattr.getNodeName());
		}
	}
	private boolean advanceAttrCheck(Node adNode, Node adNodeCompare) {
		node_DifferntIndex = 1;
		boolean canAdd = true;
		while (true) {
			if (adNode == null || adNodeCompare == null) {
				break;
			}
			canAdd = checkAttr(adNode, adNodeCompare);
			node_DifferntIndex++;
			Node_DifferentAt = adNode;
			if (canAdd == false) {
				break;
			}
			if (adNode.getParentNode().getNodeName().equals(
					EU_REGIONAL_ROOT_NODE))
				break;
			if (adNode.getParentNode().getNodeName().equals(
					US_REGIONAL_ROOT_NODE))
				break;
			if (adNode.getParentNode().getNodeType() == Node.DOCUMENT_NODE)
				break;
			adNode = adNode.getParentNode();
			adNodeCompare = adNodeCompare.getParentNode();
		}
		return canAdd;
	}
	private String getReferenceIdFromModifiedFileAttr(Node modifiedLeafNode) {
		String nodeId = "";
		NamedNodeMap allAtt = modifiedLeafNode.getAttributes();
		for (int i = 0; i < allAtt.getLength(); i++) {
			Node attr = allAtt.item(i);
			if (attr.getNodeName().equalsIgnoreCase(ECTD_MODIFIEDFILE_ATTR)) {
				if (attr.getNodeValue() != null) {
					String[] valArr = attr.getNodeValue().split("#");
					if (valArr.length == 2) {
						nodeId = valArr[1];
						break;
					}
				}
			}
		}
		return nodeId;
	}
	private boolean checkAttr(Node firstNode, Node secondeNode) {
		boolean addFlag = true;
		NamedNodeMap firstNodeAttr = firstNode.getAttributes();
		NamedNodeMap secondNodeAttr = secondeNode.getAttributes();
		if (firstNodeAttr.getLength() != secondNodeAttr.getLength())
			return false;
		for (int i = 0; i < firstNodeAttr.getLength(); i++) {
			Node attrOfFirstNode = firstNodeAttr.item(i);
			boolean innerFlag = false;
			for (int j = 0; j < secondNodeAttr.getLength(); j++) {
				Node attrOfSecondNode = secondNodeAttr.item(i);
				if (attrOfSecondNode.getNodeName().equalsIgnoreCase(
						attrOfFirstNode.getNodeName())) {
					if (attrOfSecondNode.getNodeValue().equalsIgnoreCase(
							attrOfFirstNode.getNodeValue())) {
						innerFlag = true;
						break;
					}
				}
			}
			if (!innerFlag)
				return false;
		}
		return addFlag;
	}
	
	private void generateFullTree() {
		System.out.println("Heree");
		sequenceNo = sequenceDirectoryName.get(0);
		fullIndexPath = folderPath + "/" + sequenceNo + "/" + "index.xml";
		sequencePath = folderPath + "/" + sequenceNo;
		Document document = InitialCallFunction(fullIndexPath);
		ArrayList<ArrayList<Node>> allLeafNodeData;
		if (sequenceDirectoryName.size() >= 1) {
			NodeList nodeList = getAllLeafNodeByDocument(document);
			addAttributeInNodeList(nodeList, "sequence", sequenceDirectoryName
					.get(0));
		}
		if (sequenceDirectoryName.size() > 1) {
			for (int i = 1; i < sequenceDirectoryName.size(); i++) {
				NodeList nodeList = getAllLeafNodeBySequence(folderPath,
						sequenceDirectoryName.get(i), "index.xml");
				addAttributeInNodeList(nodeList, "sequence",
						sequenceDirectoryName.get(i));
				allLeafNodeData = getNodeHierarchy(nodeList,
						sequenceDirectoryName.get(i));
				createNetViewDocument(document, allLeafNodeData);
				// allSequenceLeafNode.add(allLeafNodeData);
			}
		} else {
			Node xmlRoot = readIndexDocument(document);
			readElementChildren(xmlRoot);
		}
		m2m5Document = document;
		// generateFullM1();
	}
	private void addAttributeInNodeList(NodeList allNodeList, String attName,
			String attrValue) {
		for (int nodeListIndex = 0; nodeListIndex < allNodeList.getLength(); nodeListIndex++) {
			Node node = allNodeList.item(nodeListIndex);
			Node attNode = node.getOwnerDocument().createAttribute(attName);
			attNode.setNodeValue(attrValue);
			node.getAttributes().setNamedItem(attNode);
		}
	}
	private static Document InitialCallFunctionTest(String xmlFilePath) {
		File currXmlFile = new File(xmlFilePath);
		Document document = null;
		if (currXmlFile.exists()) {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			documentBuilderFactory.setValidating(true);
			documentBuilderFactory.setIgnoringElementContentWhitespace(true);
			try {
				DocumentBuilder documentBuilder;
				documentBuilder = documentBuilderFactory.newDocumentBuilder();
				documentBuilder.setErrorHandler(new ErrorHandler() {
					public void warning(SAXParseException exception)
							throws SAXException {
						// TODO Auto-generated method stub
						System.out.println("Warning...");
						exception.printStackTrace();
					}
					public void fatalError(SAXParseException exception)
							throws SAXException {
						// TODO Auto-generated method stub
						System.out.println("Fatal Error...");
						exception.printStackTrace();
					}
					public void error(SAXParseException exception)
							throws SAXException {
						// TODO Auto-generated method stub
						System.out.println("Error.." + exception.getMessage()
								+ "==>" + exception.getLineNumber());
						exception.printStackTrace();
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
	public static void main(String argv[]) {
		// StringBuffer str = new StringBuffer("<ul><li>Test</li></ul>");
		// str.insert(str.lastIndexOf("</li>"), "<ul><li>MyNewUl</li></ul>");
		// System.out.println(str);
		GenerateFullTreeView gftv = new GenerateFullTreeView();
		ArrayList<Document> mergedDocs = gftv
				.getMergedXMLDocument("//90.0.0.15/docmgmtandpub//ectdviewer/unziped//H0002539");
	
		Document indexDoc, regionalDoc;
		regionalDoc = mergedDocs.get(0);	
		indexDoc = mergedDocs.get(1);
		
		try {
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			Result output = new StreamResult(new File("D://mergedindex.xml"));
			Source input = new DOMSource(regionalDoc);
			transformer.transform(input, output);
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		// String xmlPath =
		// "//90.0.0.15/docmgmtandpub/ectdviewer/unziped/test/0000/index.xml";
		// Document document = InitialCallFunctionTest(xmlPath);
	}
}