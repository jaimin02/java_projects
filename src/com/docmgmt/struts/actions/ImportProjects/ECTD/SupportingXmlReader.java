package com.docmgmt.struts.actions.ImportProjects.ECTD;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javawebparts.core.org.apache.commons.lang.StringUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.docmgmt.dto.DTOAgencyMst;
import com.docmgmt.dto.DTOAttributeValueMatrix;
import com.docmgmt.dto.DTOSTFNodeMst;
import com.docmgmt.dto.DTOSTFStudyIdentifierMst;
import com.docmgmt.dto.DTOSubmissionInfoEUDtl;
import com.docmgmt.dto.DTOSubmissionInfoEUSubDtl;
import com.docmgmt.dto.DTOSubmissionInfoUSDtl;
import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.dto.DTOTemplateNodeAttrDetail;
import com.docmgmt.dto.DTOTemplateNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeAttrDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.IDTONodeAttr;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.entities.AttributeType;
import com.docmgmt.server.webinterface.entities.Node.NodeTypeIndi;
import com.docmgmt.server.webinterface.entities.Node.RequiredFlag;
import com.docmgmt.struts.resources.MD5;
public class SupportingXmlReader implements IXmlStringValidatePatterns{
	static SupportingXmlReader instance = null;
	static int MAX_FILEPATH_LENGTH = 230;
	MD5 md5;
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	Vector<DTOAttributeValueMatrix> allLeafAttrs; 
	Vector<DTOSTFNodeMst> STFNodeMstList;
	Document templateDoc;
	public SupportingXmlReader() {
		md5 = new MD5();
		allLeafAttrs = docMgmtImpl.getAttributeDetailByType(AttributeType.LEAF_ATTR);
		STFNodeMstList = docMgmtImpl.getAllSTFNodes();
	}
	public static SupportingXmlReader getInstance(){
		if(instance == null){
			instance = new SupportingXmlReader();
		}
		return instance;
	}

	/**
	 * reads eu-regional.xml's <code><eu-envelope></code> node
	 * */
	DTOSubmissionMst readEUM1Envelopes(Node euEnvelopeNode){
		/********************************************************************/
		ArrayList<String> applicationNumbers = new ArrayList<String>(),
			atcList = new ArrayList<String>(),
			inventedNames = new ArrayList<String>(),
			innList = new ArrayList<String>(),
			relatedSequences = new ArrayList<String>();
		ArrayList<DTOSubmissionInfoEUSubDtl> cmsList = new ArrayList<DTOSubmissionInfoEUSubDtl>();
		/*===========================================================================*/
		DTOSubmissionMst submissionMst = new DTOSubmissionMst();
		submissionMst.setCountryRegion("eu");
		DTOSubmissionInfoEUDtl submissionDtl=new DTOSubmissionInfoEUDtl();
		submissionDtl.setRelatedSeqNo("");//Set related Sequence to blank initially.
		/********************************************************************/
		ArrayList<Node> euEnvelopes = getChildNodes(euEnvelopeNode);
		
		for (int i = 0; i < euEnvelopes.size(); i++) {
			DTOSubmissionInfoEUSubDtl cms = new DTOSubmissionInfoEUSubDtl();
			
			Node envelope = euEnvelopes.get(i);
			NamedNodeMap envelopeAttrs= envelope.getAttributes();
			Node country = envelopeAttrs.getNamedItem("country");
			
			String countryCode = country.getNodeValue();
			String countryId =  docMgmtImpl.getCountryId(countryCode);
			 
			ArrayList<Node> envelopeChildren = getChildNodes(envelope);
			for (Node envelopeChild : envelopeChildren) {
				if(envelopeChild.getNodeName().equals("application")){//<application>
					ArrayList<Node> appNos = getChildNodes(envelopeChild);
					for (Node appNumber : appNos) {
						addNodeValueToList(appNumber, applicationNumbers);
					}
				}
				if(envelopeChild.getNodeName().equals("applicant")){//<applicant>
					String applicant = envelopeChild.getNodeValue();
					if(submissionMst.getApplicant() == null){
						submissionMst.setApplicant(applicant);
					}
				}
				if(envelopeChild.getNodeName().equals("agency")){//<agency>
					String agencyName =getNodeAttrValue(envelopeChild, "code");
					String agencyCode =""; 
					DTOAgencyMst agency = docMgmtImpl.getAgencyByName(agencyName);
					if(agency.getAgencyCode() != null){
						agencyCode = agency.getAgencyCode();
					}
					cms.setAgencyName(agencyName);
					cms.setAgencyCode(agencyCode);
					cms.setCountryCode(countryId);
				}
				if(envelopeChild.getNodeName().equals("atc")){//<atc>
					addNodeValueToList(envelopeChild, atcList);
				}
				if(envelopeChild.getNodeName().equals("submission")){//<submission>
					String submissionType = getNodeAttrValue(envelopeChild, "type");
					if(submissionDtl.getSubmissionType() == null){
						submissionDtl.setSubmissionType(submissionType);
					}
				}
				if(envelopeChild.getNodeName().equals("procedure")){//<procedure>
					String procedureType = getNodeAttrValue(envelopeChild, "type");
					if(submissionMst.getProcedureType() == null){
						submissionMst.setProcedureType(procedureType);
					}
				}
				if(envelopeChild.getNodeName().equals("invented-name")){//<invented-name>
					addNodeValueToList(envelopeChild, inventedNames);
				}
				if(envelopeChild.getNodeName().equals("inn")){//<inn>
					addNodeValueToList(envelopeChild, innList);
				}
				if(envelopeChild.getNodeName().equals("sequence")){//<sequence>
					String sequence = envelopeChild.getNodeValue();
					if(submissionDtl.getCurrentSeqNumber() == null){
						submissionDtl.setCurrentSeqNumber(sequence);
						submissionDtl.setLastPublishedVersion(sequence);
					}
				}
				if(envelopeChild.getNodeName().equals("related-sequence")){//<related-sequence>
					addNodeValueToList(envelopeChild, relatedSequences);
				}
				if(envelopeChild.getNodeName().equals("submission-description")){//<submission-description>
					String submissionDescription = envelopeChild.getNodeValue();
					if(submissionMst.getSubmissionDescription() == null){
						submissionMst.setSubmissionDescription(submissionDescription);
					}
				}
			}
			cmsList.add(cms);
		}
		
		submissionMst.setApplicationNo(generateCSV(applicationNumbers));
		submissionMst.setAtc(generateCSV(atcList));
		submissionMst.setInventedName(generateCSV(inventedNames));
		submissionMst.setInn(generateCSV(innList));
		
		submissionDtl.setRelatedSeqNo(generateCSV(relatedSequences));
		submissionDtl.setCmsDtls(cmsList);
		
		submissionMst.setSubmissionInfoEUDtl(submissionDtl);
		return submissionMst;
	}
	void addNodeValueToList(Node xmlNode,List<String> nodeValuelist){
		String nodeValue = xmlNode.getNodeValue();
		if(nodeValuelist != null){
			if(nodeValue != null && !nodeValue.equals("") && !nodeValuelist.contains(nodeValue)){
				nodeValuelist.add(nodeValue);
			}
		}
	}
	String getNodeAttrValue(Node xmlNode,String attrName){
		NamedNodeMap nodeAttrs = xmlNode.getAttributes();
		Node nodeAttr = nodeAttrs.getNamedItem(attrName);
		if(nodeAttr == null){
			return null;
		}
		return nodeAttr.getNodeValue();
	}
	String generateCSV(List<String> list){
		String str = "";
		for (String data : list) {
			if(str.equals("")){
				str = data;
			}
			else{
				str += "," + data;
			}
		}
		return str;
	}
	
	/**
	 * reads us-regional.xml's <code><admin></code> node
	 * */
	DTOSubmissionMst readUSM1AdminDtls(Node adminNode){
		
		DTOSubmissionMst submissionMst = new DTOSubmissionMst();
		submissionMst.setCountryRegion("us");
		DTOSubmissionInfoUSDtl submissionDtl=new DTOSubmissionInfoUSDtl();
		submissionDtl.setRelatedSeqNo("");//Set related Sequence to blank initially.
		
		String countryCode = "us";
		String countryId =  docMgmtImpl.getCountryId(countryCode);
		 
		String agencyName ="";
		ArrayList<DTOAgencyMst> countryAgencies = docMgmtImpl.getAgenciesForCountry(countryId,"0");
		if(countryAgencies.size() > 0){
			agencyName =countryAgencies.get(0).getAgencyName();
		}
		 
		submissionMst.setCountrycode(countryId);
		submissionDtl.setCountryCode(countryId);
		submissionMst.setAgencyName(agencyName);
		
		NodeList adminChildren = adminNode.getChildNodes();
		
		for (int i = 0; i < adminChildren.getLength(); i++) {
			Node adminChild = adminChildren.item(i);
			
			if(adminChild.getNodeName().equals("applicant-info")){//<applicant-info>
				NodeList applicantInfoChildren = adminChild.getChildNodes();
				for (int j = 0; j < applicantInfoChildren.getLength(); j++) {
					Node applicantInfoChild = applicantInfoChildren.item(j);
					
					if(applicantInfoChild.getNodeName().equals("company-name")){//<company-name>
						String companyName =  applicantInfoChild.getTextContent();
						submissionMst.setCompanyName(companyName);
					}
					else if(applicantInfoChild.getNodeName().equals("date-of-submission")){//<date-of-submission>
						NodeList dateOfSubmissionChildren = applicantInfoChild.getChildNodes();
						for (int k = 0; k < dateOfSubmissionChildren.getLength(); k++) {
							Node dateOfSubmissionChild = dateOfSubmissionChildren.item(k);
							if(dateOfSubmissionChild.getNodeName().equals("date")){//<date>
								Node format = dateOfSubmissionChild.getAttributes().getNamedItem("format");
								String dateFormat = format.getNodeValue().replace('m', 'M');
								DateFormat dateFormater = new SimpleDateFormat(dateFormat);
								
								String dateVal =  dateOfSubmissionChild.getTextContent();
								Date dateOfSubmission=null;
								try {
									dateOfSubmission = dateFormater.parse(dateVal);
								} catch (ParseException e) {}
								submissionMst.setDateOfSubmission(new java.sql.Date(dateOfSubmission.getTime()));
								submissionDtl.setDateOfSubmission(new java.sql.Timestamp(dateOfSubmission.getTime()));
							}
						}
					}
				}
			}//</applicant-info>
			else if(adminChild.getNodeName().equals("product-description")){//<product-description>
				NodeList productDescriptionChildren = adminChild.getChildNodes();
				for (int k = 0; k < productDescriptionChildren.getLength(); k++) {
					Node productDescriptionChild = productDescriptionChildren.item(k);
					if(productDescriptionChild.getNodeName().equals("application-number")){//<application-number>
						String applicationNo =  productDescriptionChild.getTextContent();
						
						submissionMst.setApplicationNo(applicationNo);
						submissionDtl.setSubmissionPath(applicationNo);//Setting only app no for path
					}
					else if(productDescriptionChild.getNodeName().equals("prod-name")){//<prod-name+>
						Node type = productDescriptionChild.getAttributes().getNamedItem("type");
						String productName =  productDescriptionChild.getTextContent();
						
						submissionMst.setProductName(productName);
						submissionMst.setProductType(type.getNodeValue());
					}
				}
			}//</product-description>
			else if(adminChild.getNodeName().equals("application-information")){//<application-information>
				Node appType = adminChild.getAttributes().getNamedItem("application-type");
				
				submissionMst.setApplicationType(appType.getNodeValue());
				
				NodeList applicationInfoChildren = adminChild.getChildNodes();
				for (int k = 0; k < applicationInfoChildren.getLength(); k++) {
					Node applicationInfoChild = applicationInfoChildren.item(k);
					if(applicationInfoChild.getNodeName().equals("submission")){//<submission>
						Node subType = applicationInfoChild.getAttributes().getNamedItem("submission-type");
						submissionDtl.setSubmissionType(subType.getNodeValue());
						NodeList submissionChildren = applicationInfoChild.getChildNodes();
						for (int l = 0; l < submissionChildren.getLength(); l++) {
							Node submissionChild = submissionChildren.item(l);
							if(submissionChild.getNodeName().equals("sequence-number")){//<sequence-number>
								String currentSeqNumber =  submissionChild.getTextContent();
								
								submissionDtl.setCurrentSeqNumber(currentSeqNumber);
								submissionDtl.setLastPublishedVersion(currentSeqNumber);
							}
							else if(submissionChild.getNodeName().equals("related-sequence-number")){//<related-sequence-number*>
								String relatedSeqNumber =  submissionChild.getTextContent();
								//Set related sequence as CSV. 
								if(submissionDtl.getRelatedSeqNo().equals("")){
									submissionDtl.setRelatedSeqNo(relatedSeqNumber);
								}else{
									submissionDtl.setRelatedSeqNo(submissionDtl.getRelatedSeqNo()+","+relatedSeqNumber);
								}
							}
						}
					}
				}
			}//</application-information>
		}
		submissionMst.setSubmissionInfoUSDtl(submissionDtl);
		return submissionMst; 
	}
	
	void readCAM1IntroDtls(Node introductionNode){
		
	}
	
	/**
	 * @return STFParentNodeDtl
	 * */
	DTOWorkSpaceNodeDetail readSTF(Node ectdStudyNode,Node stfXmlNode,DTOWorkSpaceNodeDetail stfXmlNodeDtl,ArrayList<EctdError> ectdErrorList,File indexXmlParentDir){
		
		//Set stfXmlParentNodeDtl
		DTOWorkSpaceNodeDetail stfParentNodeDtl = new DTOWorkSpaceNodeDetail();
		stfParentNodeDtl.setNodeTypeIndi(NodeTypeIndi.ECTD_STF_PARENT_NODE);
		//Set NodeId For STF Parent
		stfParentNodeDtl.setNodeId(stfXmlNodeDtl.getNodeId());
		stfParentNodeDtl.setNodeNo(stfXmlNodeDtl.getNodeNo());
		stfParentNodeDtl.setParentNodeId(stfXmlNodeDtl.getParentNodeId());
		
		//List for nodes under current STF
		ArrayList<DTOWorkSpaceNodeDetail> stfNodeList = new ArrayList<DTOWorkSpaceNodeDetail>();
		//List for Study Identifier Info for STF Xml Node
		ArrayList<DTOSTFStudyIdentifierMst> stfXmlNodeStudyDtls = new ArrayList<DTOSTFStudyIdentifierMst>();
		
		Node studyIdentifier = getFirstChild(ectdStudyNode);
			NodeList studyIdentifierChildren = studyIdentifier.getChildNodes();
			for (int i = 0; i < studyIdentifierChildren.getLength(); i++) {
				Node studyIdentifierChild = studyIdentifierChildren.item(i);
				
				if(studyIdentifierChild.getNodeName().equals("title")){
					Node studyTitle = studyIdentifierChild;
					Node studyTitleValue = studyTitle.getFirstChild();

					DTOSTFStudyIdentifierMst dtoStudyTitle=new DTOSTFStudyIdentifierMst();
					dtoStudyTitle.setTagName("title");
					dtoStudyTitle.setAttrName("");
					dtoStudyTitle.setAttrValue("");
					dtoStudyTitle.setNodeContent(studyTitleValue.getNodeValue());
					
					stfXmlNodeStudyDtls.add(dtoStudyTitle);
				}
				else if(studyIdentifierChild.getNodeName().equals("study-id")){
					Node studyId =studyIdentifierChild;
					Node studyIdValue = studyId.getFirstChild();
					
					DTOSTFStudyIdentifierMst dtoStudyId=new DTOSTFStudyIdentifierMst();
					dtoStudyId.setTagName("study-id");
					dtoStudyId.setAttrName("");
					dtoStudyId.setAttrValue("");
					dtoStudyId.setNodeContent(studyIdValue.getNodeValue());
					
					stfXmlNodeStudyDtls.add(dtoStudyId);
				}
				//Find Categories
				else if(studyIdentifierChild.getNodeName().equals("category")){
					Node category = studyIdentifierChildren.item(i);
					//Category Attrs
					NamedNodeMap categoryAttrs = category.getAttributes();
					Node categoryName = categoryAttrs.getNamedItem("name");
					Node categoryInfoType = categoryAttrs.getNamedItem("info-type");
					Node categoryNodeContent = category.getFirstChild();
					
					DTOSTFStudyIdentifierMst dtoStudyCategoryName=new DTOSTFStudyIdentifierMst();
					dtoStudyCategoryName.setTagName("category");
					dtoStudyCategoryName.setAttrName("name");
					dtoStudyCategoryName.setAttrValue(categoryName.getNodeValue());// Category Name
					dtoStudyCategoryName.setNodeContent(categoryNodeContent.getNodeValue());//Category Value
					
					stfXmlNodeStudyDtls.add(dtoStudyCategoryName);
					
					DTOSTFStudyIdentifierMst dtoStudyCategoryInfoType=new DTOSTFStudyIdentifierMst();
					dtoStudyCategoryInfoType.setTagName("category");
					dtoStudyCategoryInfoType.setAttrName("info-type");
					dtoStudyCategoryInfoType.setAttrValue(categoryInfoType.getNodeValue());// Category Info Type
					dtoStudyCategoryInfoType.setNodeContent(categoryNodeContent.getNodeValue());//Info Type Value
					
					stfXmlNodeStudyDtls.add(dtoStudyCategoryInfoType);
				}
			}
		//Attach all STF details to stfXmlNodeDtl
		stfXmlNodeDtl.setSTFStudyDetails(stfXmlNodeStudyDtls);
		//Add stfXmlNodeDtl to stfNodeList
		stfNodeList.add(stfXmlNodeDtl);
		
		/******************************************************************************************************/
		Node studyDocument = getNextSibling(studyIdentifier);
			NodeList studyDocumentChildren = studyDocument.getChildNodes();
			for (int i = 0; i < studyDocumentChildren.getLength(); i++) {
				Node docContent = studyDocumentChildren.item(i);
				
				if(docContent.getNodeName().equals("doc-content")){
					//List for Study Identifier Info for all other STF Nodes
					ArrayList<DTOSTFStudyIdentifierMst> stfNodeStudyDtls = new ArrayList<DTOSTFStudyIdentifierMst>();
					
					NamedNodeMap docContentAttrs = docContent.getAttributes();
					Node xlinkHref = docContentAttrs.getNamedItem("xlink:href");
					//Node xlinkType = docContentAttrs.getNamedItem("xlink:type");
					//Node test = docContentAttrs.getNamedItem("test");
					
					String nodeId=null;
					try {
						String indexXmlNodeId = xlinkHref.getNodeValue();
						nodeId = indexXmlNodeId.split("#")[1];
					} catch (Exception e) {
						ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,"No STF Leaf Found.",docContent.getBaseURI()));
						e.printStackTrace();
						continue;
					}
					
					Node stfChild = stfXmlNode.getOwnerDocument().getElementById(nodeId);
					
					if(stfChild == null){
						ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,"No STF Leaf Found with ID:"+nodeId,docContent.getBaseURI()));
						continue;
					}
					
					//Creating New WorkspaceNodeDetail for STF Node
					DTOWorkSpaceNodeDetail currSTFChildNodeDtl = new DTOWorkSpaceNodeDetail();
					currSTFChildNodeDtl.setNodeTypeIndi(NodeTypeIndi.ECTD_STF_NODE);
					
					setupForLeafNode(stfChild, currSTFChildNodeDtl, indexXmlParentDir, ectdErrorList);
					
					boolean stfNodeNameFound = false;
					boolean isMultipleTypeStfNode = false;
					NodeList docContentChildren = docContent.getChildNodes();
					for (int j = 0; j < docContentChildren.getLength(); j++) {
						Node docContentChild = docContentChildren.item(j);
						if(docContentChild.getNodeName().equals("title")){
							
						}
						else if(docContentChild.getNodeName().equals("property")){
							//property Attrs
							NamedNodeMap propertyAttrs = docContentChild.getAttributes();
							Node propertyName = propertyAttrs.getNamedItem("name");
							Node propertyInfoType = propertyAttrs.getNamedItem("info-type");
							Node propertyNodeContent = docContentChild.getFirstChild();
							
							DTOSTFStudyIdentifierMst dtoStudyCategoryName=new DTOSTFStudyIdentifierMst();
							dtoStudyCategoryName.setTagName("property");
							dtoStudyCategoryName.setAttrName("name");
							dtoStudyCategoryName.setAttrValue(propertyName.getNodeValue());// Category Name
							dtoStudyCategoryName.setNodeContent(propertyNodeContent.getNodeValue());//Category Value
							
							stfNodeStudyDtls.add(dtoStudyCategoryName);
							
							DTOSTFStudyIdentifierMst dtoStudyCategoryInfoType=new DTOSTFStudyIdentifierMst();
							dtoStudyCategoryInfoType.setTagName("property");
							dtoStudyCategoryInfoType.setAttrName("info-type");
							dtoStudyCategoryInfoType.setAttrValue(propertyInfoType.getNodeValue());// Category Info Type
							dtoStudyCategoryInfoType.setNodeContent(propertyNodeContent.getNodeValue());//Info Type Value
							
							stfNodeStudyDtls.add(dtoStudyCategoryInfoType);
							
							//Add STF Details to STF Node
							currSTFChildNodeDtl.setSTFStudyDetails(stfNodeStudyDtls);
						}
						else if(docContentChild.getNodeName().equals("file-tag")){
							NamedNodeMap fileTagAttrs = docContentChild.getAttributes();
							
							if(fileTagAttrs != null){
								Node fileTagName = fileTagAttrs.getNamedItem("name");
								if(fileTagName != null ){
									String fileTagNameVal = fileTagName.getNodeValue();
									if(fileTagNameVal!=null){
										for (DTOSTFNodeMst stfNodeMst : STFNodeMstList) {
											if(fileTagNameVal.equals(stfNodeMst.getNodeName())){
												stfNodeNameFound=true;
												currSTFChildNodeDtl.setNodeName(fileTagNameVal);
												currSTFChildNodeDtl.setNodeDisplayName(fileTagNameVal);
												if(stfNodeMst.getMultiple() == 'Y'){
													isMultipleTypeStfNode = true;
												}
												break;
											}
										}
									}
								}
							}
						}
					}
					
					if (stfNodeNameFound) {
						
						//Add STF Node To Workspace 
						if(isMultipleTypeStfNode){
							boolean createFolderNode = true;
							//Find the Node in current Node list
							for (DTOWorkSpaceNodeDetail nodeDtl : stfNodeList) {
								if(nodeDtl.getNodeName().equals(currSTFChildNodeDtl.getNodeName())){
									nodeDtl.getChildNodeList().add(currSTFChildNodeDtl);
									createFolderNode = false;
									break;
								}
							}
							if(createFolderNode){
								//New children List for multiple type STF Node
								ArrayList<DTOWorkSpaceNodeDetail> childNodeList = new ArrayList<DTOWorkSpaceNodeDetail>();
								childNodeList.add(currSTFChildNodeDtl);
								
								//Creating New WorkspaceNodeDetail for STF Folder Node
								DTOWorkSpaceNodeDetail stfFolderNodeDtl = new DTOWorkSpaceNodeDetail();
								stfFolderNodeDtl.setNodeTypeIndi(NodeTypeIndi.FOLDER_NODE);
								stfFolderNodeDtl.setNodeName(currSTFChildNodeDtl.getNodeName());
								stfFolderNodeDtl.setChildNodeList(childNodeList);
								
								stfNodeList.add(stfFolderNodeDtl);
							}
						}else{
							stfNodeList.add(currSTFChildNodeDtl);
						}
						//Remove from xml tree
						stfChild.getParentNode().removeChild(stfChild);
					}else{
						ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,"Invalid Leaf Referenced In STF.",docContent.getBaseURI()));
					}
				}
			}
			//Set STF child Nodes
			stfParentNodeDtl.setChildNodeList(stfNodeList);
			
		return stfParentNodeDtl;
	}
	void readEUTrackingTable(Node trackingTable){
		
	}
	
	Node getPreviousSibling(Node xmlNode){
		Node previousSibling = xmlNode.getPreviousSibling();
		while (true) {
			//No previous sibling found
			if(previousSibling == null ){break;}
			//Only 'ELEMENT_NODE' type of nodes required.
			if(previousSibling.getNodeType() == Element.ELEMENT_NODE){
				break;
			}
			previousSibling = previousSibling.getPreviousSibling();
		} 
		return previousSibling;
	}
	public Node getNextSibling(Node xmlNode){
		Node nextSibling = xmlNode.getNextSibling();
		while (true) {
			//No next sibling found
			if(nextSibling == null ){break;}
			//Only 'ELEMENT_NODE' type of nodes required.
			if(nextSibling.getNodeType() == Element.ELEMENT_NODE){
				break;
			}
			nextSibling = nextSibling.getNextSibling();
		}
		return nextSibling;
	}
	public Node getFirstChild(Node xmlNode){
		Node firstChild = xmlNode.getFirstChild();
		while (true) {
			//No next sibling found
			if(firstChild == null ){break;}
			//Only 'ELEMENT_NODE' type of nodes required.
			if(firstChild.getNodeType() == Element.ELEMENT_NODE){
				break;
			}
			//Get Next Node
			firstChild = firstChild.getNextSibling();
		}
		return firstChild;
	}
	public ArrayList<Node> getChildNodes(Node xmlNode){
		NodeList childNodes = xmlNode.getChildNodes();
		ArrayList<Node> children = new ArrayList<Node>();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node = childNodes.item(i);
			if(node.getNodeType() == Element.ELEMENT_NODE){
				children.add(node);
			}
		}
		return children;
	}
	
	static DocumentBuilderFactory documentBuilderFactory;
	static DocumentBuilder documentBuilder;
	static {
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {		
			e.printStackTrace();
		}
	}
	
	private static Document getDocument(String xmlFilePath){
		Document document = null;
		try {
			document = documentBuilder.parse(xmlFilePath);
		} catch (SAXException e) {		
			e.printStackTrace();
		} catch (IOException e) {		
			e.printStackTrace();
		}
		return document;
	}
	void setupNodeAttributes(Node currXmlNode,DTOWorkSpaceNodeDetail dtoWorkSpaceNodeDetail,List<? extends IDTONodeAttr> allAttrs,ArrayList<EctdError> ectdErrorList){
		NamedNodeMap xmlAttrs = currXmlNode.getAttributes();
		
		boolean chkAttrVals = true;
		if(xmlAttrs == null || xmlAttrs.getLength()==0){
			chkAttrVals = false;
		}
		ArrayList<DTOWorkSpaceNodeAttrDetail> nodeAttrList = new ArrayList<DTOWorkSpaceNodeAttrDetail>();
		//Find and compare XmlLeafAttr with Database Parent/Leaf Attributes 
		if(allAttrs != null){
			for (int i = 0; i < allAttrs.size(); i++) {
				IDTONodeAttr dtoAttrInfo = allAttrs.get(i);
				DTOWorkSpaceNodeAttrDetail dtoAttrDtl = new DTOWorkSpaceNodeAttrDetail();
				dtoAttrDtl.setNodeId(dtoWorkSpaceNodeDetail.getNodeId());
				dtoAttrDtl.setAttrId(dtoAttrInfo.getAttrId());
				dtoAttrDtl.setAttrName(dtoAttrInfo.getAttrName());
				dtoAttrDtl.setAttrForIndi(dtoAttrInfo.getAttrForIndiId());
			
				if(chkAttrVals){
					Node xmlNodeAttr = xmlAttrs.getNamedItem(dtoAttrInfo.getAttrName());
					if(xmlNodeAttr != null){
						String attrValue = xmlNodeAttr.getNodeValue().trim();
						//If current node is not a leaf node.
						if (!currXmlNode.getNodeName().equals(EctdXmlReader.ECTD_LEAF_NODE)) {
							//If attribute value contains invalid characters
							if (matchString(attrValue, nonDirChars)) {
								ectdErrorList.add(new EctdError(
												EctdErrorType.ECTD_ERROR,
												"Invalid characters(\\,/,:,*,?,\",>,< or |) found.",
												currXmlNode.getBaseURI()));
							}
						}
						dtoAttrDtl.setAttrValue(attrValue);
					}else{
						dtoAttrDtl.setAttrValue("");
					}
				}else{
					dtoAttrDtl.setAttrValue("");
				}
				nodeAttrList.add(dtoAttrDtl);
			}
		}
		
		//Attach attributes to the nodeDtl
		dtoWorkSpaceNodeDetail.setNodeAttrList(nodeAttrList);
	}
	boolean matchString(String str,Pattern pattern){
		Matcher m = pattern.matcher(str);
		return m.matches();
	}
	
	/**Method: Setup Leaf Attributes And Leaf Document/File.*/
	File setupForLeafNode(Node currXmlNode,DTOWorkSpaceNodeDetail currChildNodeDtl,File xmlParentDir,ArrayList<EctdError> ectdErrorList){
		String leafFileRelativePath = null,checksum=null,operation=null,modifiedFile=null;
		
		//Attach attributes to current Node Detail and Fetch leafFilePath
		this.setupNodeAttributes(currXmlNode,currChildNodeDtl,allLeafAttrs,ectdErrorList);
		
		for (DTOWorkSpaceNodeAttrDetail attrDtl : currChildNodeDtl.getNodeAttrList()) {
			if(attrDtl.getAttrName().equals(EctdXmlReader.ECTD_CHKSUM_ATTR)){
				checksum = attrDtl.getAttrValue();
			}
			else if(attrDtl.getAttrName().equals(EctdXmlReader.ECTD_XLINK_HREF_ATTR)){
				leafFileRelativePath = attrDtl.getAttrValue();
			}
			else if(attrDtl.getAttrName().equals(EctdXmlReader.ECTD_OPERATION_ATTR)){
				operation = attrDtl.getAttrValue();
			}
			else if(attrDtl.getAttrName().equals(EctdXmlReader.ECTD_MODIFIEDFILE_ATTR)){
				modifiedFile = attrDtl.getAttrValue();
			}
		}
		
		//Check file path value
		if(leafFileRelativePath == null ||leafFileRelativePath.equals("")){
			//error.....
			//System.out.println("ERROR: No path value found for LEAF :"+currXmlNode.getBaseURI());
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,"No path value found for LEAF. NodeId="+currChildNodeDtl.getNodeId(),currXmlNode.getBaseURI()));
			return null;
		}
		//Get the leaf file object
		File leafFile = new File(xmlParentDir.getAbsolutePath()+"/" + leafFileRelativePath);
		
		//Set Leaf file's name as Node's folder name 
		currChildNodeDtl.setFolderName(leafFile.getName());
		//Set Node Flags
		currChildNodeDtl.setRequiredFlag(RequiredFlag.NORMAL_NODE);
		currChildNodeDtl.setNodeTypeIndi(NodeTypeIndi.NORMAL_NODE);
		currChildNodeDtl.setPublishedFlag('Y');
		//Set blank child List (No further children)
		currChildNodeDtl.setChildNodeList(new ArrayList<DTOWorkSpaceNodeDetail>());
		//Leaf Operation
		checkLeafOperation(operation,modifiedFile,ectdErrorList,leafFile,currXmlNode);
		
		if(!leafFile.exists()){
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,EctdErrorMsg.getErrMsgDesc(EctdErrorMsg.FILE_NOT_FOUND),leafFile.getAbsolutePath()));
			return null;
		}
		if(!leafFile.isFile()){
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,EctdErrorMsg.getErrMsgDesc(EctdErrorMsg.FILE_NOT_FOUND),leafFile.getAbsolutePath()));
			return null;
		}
		
		//Cross-check checksum
		if(checksum != null && !checksum.equals("")){
			String generatedChksum = md5.getMd5HashCode(leafFile.getAbsolutePath());
			if(generatedChksum != null && !generatedChksum.equals("")){
				if (!checksum.equals(generatedChksum)) {
					ectdErrorList.add(new EctdError(EctdErrorType.ECTD_WARNING,EctdErrorMsg.getErrMsgDesc(EctdErrorMsg.CHKSUM_VAL_NOT_MATCHED),leafFile.getAbsolutePath()));
				} 							
			}else{
				ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,EctdErrorMsg.getErrMsgDesc(EctdErrorMsg.UNABLE_TO_GENERATE_CHKSUM),leafFile.getAbsolutePath()));
			}
		}else{
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,EctdErrorMsg.getErrMsgDesc(EctdErrorMsg.CHKSUM_VAL_NOT_FOUND),leafFile.getAbsolutePath()));
		}
		//100 * 1024 * 1024 Bytes = 104857600 Bytes = 100 MB
		if(leafFile.length() >= 104857600){
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,"File is greater than 100 MB.",leafFile.getAbsolutePath()));
		}
	
		if(!chkFilePathLength(leafFile)){
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,"File path length exceeds 180 characters.",leafFile.getAbsolutePath()));
		}
		
		//readNodeDtls for Node History(e.g. .pdf,.doc etc.) 
		short msg = addLeafNodeHistory(currXmlNode, currChildNodeDtl, leafFile);
		if(msg == EctdErrorMsg.LEAF_WITHOUT_TITLE){
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_WARNING,EctdErrorMsg.getErrMsgDesc(EctdErrorMsg.LEAF_WITHOUT_TITLE),leafFile.getAbsolutePath()));
		}
		return leafFile;
	}
	
	private boolean chkFilePathLength(File leafFile){
		String relativePath = "";
		try {
			String leafFilePath =leafFile.getCanonicalPath().replaceAll("\\\\", "/");
			String regex = "/"+"\\d{4}"+"/"+"m\\d"+"/";
			Pattern pattern = Pattern.compile(regex);
			relativePath = pattern.split(leafFilePath)[1];
			//leafFilePath.split(regex)[1];
		} catch (Exception e) {
			e.printStackTrace();
		}
		// '/0000/m2/' is 9 characters long, so adding 9 in relativePath's length   
		if(relativePath.length() + 9 > MAX_FILEPATH_LENGTH){
			return false;
		}
		return true;
	}
	private void checkLeafOperation(String operation, String modifiedFile,ArrayList<EctdError> ectdErrorList,File leafFile,Node currXmlNode){
		if(operation == null || operation.equals("")){
			ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,"No operation found for leaf.",leafFile.getAbsolutePath()));
			return;
		}
		if(!operation.equals("new")){
			if(modifiedFile == null || modifiedFile.equals("")){
				ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,"Modified File value not found for leaf.",leafFile.getAbsolutePath()));
				return;
			}
			
			String[] refLeafData = modifiedFile.split("#");
			if(refLeafData == null || refLeafData.length != 2 || refLeafData[0] == null ||refLeafData[1] == null){
				ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,EctdErrorMsg.getErrMsgDesc(EctdErrorMsg.INVALID_MODIFIED_FILE_ENTRY),leafFile.getAbsolutePath()));
				return;
			}
			
			String refXmlFilePath = refLeafData[0];
			String refLeafID = refLeafData[1];
			File xmlFile = new File(currXmlNode.getBaseURI().replaceFirst("file:", ""));
			File refXmlFile = new File(xmlFile.getParentFile(),refXmlFilePath);
			try {
				Document xmlDoc = getDocument(refXmlFile.getCanonicalPath());
				if(xmlDoc == null){
					ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,EctdErrorMsg.getErrMsgDesc(EctdErrorMsg.INVALID_MODIFIED_FILE_ENTRY),leafFile.getAbsolutePath()));
					return;
				}	
				Element refEle =  xmlDoc.getElementById(refLeafID);
				if(refEle == null || !refEle.getNodeName().equals("leaf")){
					ectdErrorList.add(new EctdError(EctdErrorType.ECTD_ERROR,EctdErrorMsg.getErrMsgDesc(EctdErrorMsg.INVALID_MODIFIED_FILE_ENTRY),leafFile.getAbsolutePath()));
					return;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	String getNodeDisplayName(String nodeName) {
		String nodeDisplayName = nodeName.replaceAll("^m\\d\\-((\\d*|\\w)\\-)*", "");
		String[] nodeNamePartArr =  nodeDisplayName.split("-");
		for (int i = 0; i < nodeNamePartArr.length; i++) {
			String nodeNamePart = nodeNamePartArr[i];
			if(nodeNamePart != null && nodeNamePart.length()>2){
				char firstChar = nodeNamePart.charAt(0);
				char secondChar = nodeNamePart.charAt(1);
				if(Character.isLowerCase(firstChar) && Character.isLowerCase(secondChar)){
					nodeNamePart = nodeNamePart.substring(0,1).toUpperCase() + nodeNamePart.substring(1);
				}
				nodeNamePartArr[i] = nodeNamePart;
			}
		}
		return StringUtils.join(nodeNamePartArr, ' ');
	}
	short addLeafNodeHistory(Node currXmlNode,DTOWorkSpaceNodeDetail currChildNodeDtl,File leafFile){
		short msg=-1;
		String titleValue = null;
		//Get Leaf's title value 
		NodeList allLeafChildren =currXmlNode.getChildNodes();
		for (int k = 0; k < allLeafChildren.getLength(); k++) {
			Node leafChild = allLeafChildren.item(k);
			if(leafChild.getNodeName().trim().equalsIgnoreCase(EctdXmlReader.ECTD_TITLE_NODE)){
				Node titleNode = leafChild.getFirstChild();
				if(titleNode != null && titleNode.getNodeType() == Element.TEXT_NODE){
					titleValue = titleNode.getNodeValue().trim();
				}
			}
		}
		if(titleValue == null || titleValue.equals("")){
			msg = EctdErrorMsg.LEAF_WITHOUT_TITLE;
			titleValue = "Untitled Leaf";
		}
		//Set the Leaf Node Title As Node Display Name 
		currChildNodeDtl.setNodeDisplayName(titleValue);
		
		//Set the File object in node history and add it to node detail
		DTOWorkSpaceNodeHistory latestNodeHistory = new DTOWorkSpaceNodeHistory();
		latestNodeHistory.setHistoryDocument(leafFile);
		latestNodeHistory.setNodeId(currChildNodeDtl.getNodeId());
		latestNodeHistory.setFileName(leafFile.getName());
		
		currChildNodeDtl.setLatestNodeHistory(latestNodeHistory);
	
		return msg;
	}
	DTOWorkSpaceNodeDetail convertTemplateNodeToWorkspaceNode(DTOTemplateNodeDetail templateNodeDtl){
		//Create New WorkSpace Node 
		DTOWorkSpaceNodeDetail wsNodeDtl = new DTOWorkSpaceNodeDetail();
		//Blank child List
		ArrayList<DTOWorkSpaceNodeDetail> childList = new ArrayList<DTOWorkSpaceNodeDetail>();
		//Blank Attribute List
		ArrayList<DTOWorkSpaceNodeAttrDetail> attrList = new ArrayList<DTOWorkSpaceNodeAttrDetail>();
		//System.out.println(templateNodeDtl.getNodeName()+":"+templateNodeDtl.getNodeNo());
		
		wsNodeDtl.setNodeName(templateNodeDtl.getNodeName());
		//wsNodeDtl.setParentNodeId(currParentId);
		wsNodeDtl.setNodeDisplayName(templateNodeDtl.getNodeDisplayName());
		wsNodeDtl.setFolderName(templateNodeDtl.getFolderName());
		wsNodeDtl.setPublishedFlag(templateNodeDtl.getPublishFlag());
		wsNodeDtl.setRequiredFlag(templateNodeDtl.getRequiredFlag());
		wsNodeDtl.setNodeTypeIndi(templateNodeDtl.getNodeTypeIndi());
		wsNodeDtl.setRemark(templateNodeDtl.getRemark());
		
		//Convert Attributes
		for (Iterator<DTOTemplateNodeAttrDetail> iterator = templateNodeDtl.getNodeAttrList().iterator(); iterator.hasNext();) {
			DTOTemplateNodeAttrDetail tempNodeAttr = iterator.next();
			
			DTOWorkSpaceNodeAttrDetail wsNodeAttr = new DTOWorkSpaceNodeAttrDetail();
			wsNodeAttr.setAttrId(tempNodeAttr.getAttrId());
			wsNodeAttr.setAttrName(tempNodeAttr.getAttrName());
			wsNodeAttr.setAttrValue(tempNodeAttr.getAttrValue());
			wsNodeAttr.setValidValues(tempNodeAttr.getValidValues());
			wsNodeAttr.setRequiredFlag(tempNodeAttr.getRequiredFlag());
			wsNodeAttr.setEditableFlag(tempNodeAttr.getEditableFlag());
			wsNodeAttr.setAttrForIndi(tempNodeAttr.getAttrForIndiId());
			wsNodeAttr.setRemark(tempNodeAttr.getRemark());
			
			attrList.add(wsNodeAttr);
		}
		//Set node attrs
		wsNodeDtl.setNodeAttrList(attrList);
		//Get Child Nodes
		if(templateNodeDtl.getChildNodeList() != null){
			for (DTOTemplateNodeDetail tempChildDtl : templateNodeDtl.getChildNodeList()) {
				DTOWorkSpaceNodeDetail wsChildDtl = convertTemplateNodeToWorkspaceNode(tempChildDtl);
				//Add Child To List
				childList.add(wsChildDtl);
			}
		}
		//set child list to parent node
		wsNodeDtl.setChildNodeList(childList);
		
		//Return Node
		return wsNodeDtl;
	}
	public static void main(String[] args) {
		String dos = "2013/07/19";
		DateFormat inputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat outputDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			Date date= inputDateFormat.parse(dos);
			System.out.println(outputDateFormat.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		/*		File file = new File(".." + File.separator + "filename.txt");
	    file = file.getAbsoluteFile();
	    try {
			System.out.println(file.getCanonicalPath());
		} catch (Exception e) {
			e.printStackTrace();
		}

		*/
		/*try {
			FileInputStream fin = new FileInputStream( new File("F:/Temp/temp.dat"));
			ObjectInputStream oin = new ObjectInputStream(fin);
			DTOWorkSpaceMst wsMst=(DTOWorkSpaceMst)oin.readObject();
			SupportingXmlReader reader = new SupportingXmlReader();
			reader.readTree(wsMst.getRootNodeDtl(), 1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/
		
		
	}
	
}
interface IXmlStringValidatePatterns{
	final Pattern nonDirChars = Pattern.compile(".*(\\\\|/|:|\\*|\\?|\"|>|<|\\|).*");
}